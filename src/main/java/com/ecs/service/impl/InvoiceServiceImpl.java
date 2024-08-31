package com.ecs.service.impl;

import com.ecs.dto.InvoiceDto;
import com.ecs.dto.InvoiceProductDto;
import com.ecs.dto.ProductDto;
import com.ecs.entity.Invoice;
import com.ecs.entity.Product;
import com.ecs.enums.InvoiceStatus;
import com.ecs.enums.InvoiceType;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.InvoiceRepository;
import com.ecs.service.InvoiceProductService;
import com.ecs.service.InvoiceService;
import com.ecs.service.ProductService;
import com.ecs.service.SecurityService;
import jdk.jfr.Percentage;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final MapperUtil mapperUtil;
    private final InvoiceRepository invoiceRepository;
    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;


    public InvoiceServiceImpl(MapperUtil mapperUtil, InvoiceRepository invoiceRepository, SecurityService securityService, InvoiceProductService invoiceProductService, ProductService productService) {
        this.mapperUtil = mapperUtil;
        this.invoiceRepository = invoiceRepository;
        this.securityService = securityService;
        this.invoiceProductService = invoiceProductService;

        this.productService = productService;
    }

    private static final BigDecimal PERCENTAGE_DIVISOR = BigDecimal.valueOf(100);

    @Override
    public List<InvoiceDto> listAllInvoices() {

        return invoiceRepository.findAllByCompanyId(securityService.getLoggedInUserCompanyId())
                .stream()
                .map(invoice -> mapperUtil.convert(invoice,InvoiceDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<InvoiceDto> listAllByCompanyIdAndInvoiceType(Long companyId, InvoiceType invoiceType) {

        List<InvoiceDto> invoiceDtoList = invoiceRepository.findAllByCompanyIdAndInvoiceTypeOrderByInvoiceNoDesc(companyId,invoiceType)
                .stream()
                .map(invoice->mapperUtil.convert(invoice, InvoiceDto.class))
                .collect(Collectors.toList());

                    invoiceDtoList.stream()
                    .map(invoice -> {

                        List<InvoiceProductDto> productsOfInvoice = invoiceProductService.findByInvoiceId(invoice.getId());

                        BigDecimal totalPriceWithoutTax = calculateTotalWithoutTax(productsOfInvoice);
                        BigDecimal taxAmount = calculateTax(productsOfInvoice);
//                        BigDecimal totalPriceWithTax = calculateTotalWithTax(productsOfInvoice);

                        invoice.setCompanyDto(securityService.getLoggedInUser().getCompany());
                        invoice.setPrice(totalPriceWithoutTax);
                        invoice.setTax(taxAmount);
                        BigDecimal total = totalPriceWithoutTax.add(taxAmount);
                        invoice.setTotal(total);
//                        invoice.setTotal(totalPriceWithTax);

                        return invoice;
                    })
                    .collect(Collectors.toList());

        return invoiceDtoList;
    }


    @Override
    public InvoiceDto findById(Long id) {

        Invoice invoice = invoiceRepository.findById(id).get();
        InvoiceDto invoiceDto = mapperUtil.convert(invoice,InvoiceDto.class);

        List<InvoiceProductDto> invoiceProductDtoList = invoiceProductService.findByInvoiceId(invoiceDto.getId());

        BigDecimal priceWithoutTax = calculateTotalWithoutTax(invoiceProductDtoList);
        BigDecimal tax = calculateTax(invoiceProductDtoList);
        BigDecimal total = calculateTotalWithoutTax(invoiceProductDtoList);

        invoiceDto.setPrice(priceWithoutTax);
        invoiceDto.setTax(tax);
        invoiceDto.setTotal(total);

        return invoiceDto;
    }

    @Override
    public void save(InvoiceDto invoiceDto) {

        invoiceRepository.save(mapperUtil.convert(invoiceDto, Invoice.class));

    }

    @Override
    public void update(InvoiceDto foundInvoiceDtoToBeUpdated, InvoiceDto invoiceWithNewFeatures) {

        invoiceWithNewFeatures.setId(foundInvoiceDtoToBeUpdated.getId());
        invoiceWithNewFeatures.setInvoiceType(foundInvoiceDtoToBeUpdated.getInvoiceType());
        invoiceWithNewFeatures.setInvoiceStatus(foundInvoiceDtoToBeUpdated.getInvoiceStatus());
        invoiceWithNewFeatures.setCompanyDto(securityService.getLoggedInUser().getCompany());
        //todo company should come from found invoice

        invoiceRepository.save(mapperUtil.convert(invoiceWithNewFeatures, Invoice.class));

    }

    @Override
    public void deleteById(Long id) {

        Invoice invoiceToBeDeleted =invoiceRepository.findById(id).get();
        invoiceToBeDeleted.setIsDeleted(true);

        invoiceProductService.deleteByInvoice(mapperUtil.convert(invoiceToBeDeleted,InvoiceDto.class));

        invoiceRepository.save(invoiceToBeDeleted);
    }

    @Override
    public BigDecimal calculateTaxForProduct(InvoiceProductDto invoiceProductDto) {

        BigDecimal productPrice = invoiceProductDto.getPrice();
        BigDecimal productTaxPercentage = BigDecimal.valueOf(invoiceProductDto.getTax());
        Integer quantity = invoiceProductDto.getQuantity();

        return productPrice.multiply(productTaxPercentage)
                .divide(PERCENTAGE_DIVISOR, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public BigDecimal calculateTax(List<InvoiceProductDto> invoiceProductDtoList) {

        BigDecimal taxSum = invoiceProductDtoList.stream()
                        .map(this::calculateTaxForProduct)
                        .reduce(BigDecimal.ZERO,BigDecimal::add);

                if (taxSum.compareTo(BigDecimal.ZERO) < 0){
                    throw new IllegalArgumentException("Tax Can not be negative!");
                }
        return taxSum;
    }

    @Override
    public BigDecimal calculateTotalWithoutTax(List<InvoiceProductDto> invoiceProductDtoList) {

        BigDecimal totalWithoutTax = invoiceProductDtoList
                .stream()
                .map(invoiceProductDto -> {
                    BigDecimal price = invoiceProductDto.getPrice();
                    Integer quantity = invoiceProductDto.getQuantity();

                    return price.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        return totalWithoutTax;
    }

//    @Override
//    public BigDecimal calculateTotalWithTax(List<InvoiceProductDto> invoiceProductDtoList) {
//        return calculateTotalWithoutTax(invoiceProductDtoList).add(calculateTax(invoiceProductDtoList));
//    }

    @Override
    public String generateNextInvoiceNumber(Invoice lastInvoice, InvoiceType invoiceType) {

        char invoicePrefix = invoiceType.getValue().charAt(0);
        int invoiceNumber = 1;
        String nextInvoiceNumberByType = String.format("%s-%03d",invoicePrefix,invoiceNumber);

            if (lastInvoice==null) {
                return nextInvoiceNumberByType;
            }else {

                String lastInvoiceNumber = lastInvoice.getInvoiceNo();

                String[] invoiceChars = lastInvoiceNumber.split("-");

                if (invoiceChars.length==2 && invoiceChars[0].charAt(0) == invoicePrefix){
                    invoiceNumber = Integer.parseInt(invoiceChars[1])+1;
                }
            }
        return String.format("%s-%03d",invoicePrefix,invoiceNumber);
    }

    @Override
    public Invoice getTheLatestInvoiceByType(InvoiceType invoiceType) {

        Long companyId = securityService.getLoggedInUserCompanyId();

        return invoiceRepository.findTopByCompanyIdAndInvoiceTypeOrderByInvoiceNoDesc(companyId,invoiceType).get();

    }

    @Override
    public InvoiceDto generateNewInvoice(InvoiceType invoiceType) {

        Long companyId = securityService.getLoggedInUserCompanyId();

        Optional<Invoice> lastInvoice = invoiceRepository.findTopByCompanyIdAndInvoiceTypeOrderByInvoiceNoDesc(companyId,invoiceType);

        String nextInvoiceNumber = generateNextInvoiceNumber(lastInvoice.get(),invoiceType);

        InvoiceDto newInvoice = new InvoiceDto();
            newInvoice.setInvoiceNo(nextInvoiceNumber);
            newInvoice.setDate(LocalDate.now());
            newInvoice.setInvoiceType(invoiceType);

            return newInvoice;
    }

    @Override
    public InvoiceDto create(InvoiceDto invoiceDto, InvoiceType invoiceType) {

        invoiceDto.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoiceDto.setCompanyDto(securityService.getLoggedInUser().getCompany());
        invoiceDto.setInvoiceType(invoiceType);

        Invoice invoiceToCreate = mapperUtil.convert(invoiceDto, Invoice.class);
        Invoice savedInvoice = invoiceRepository.save(invoiceToCreate);

        return mapperUtil.convert(savedInvoice, InvoiceDto.class);
    }

    @Override
    public void approveInvoice(Long invoiceId) {
        Invoice invoiceToApprove = invoiceRepository.findById(invoiceId).get();

        List<InvoiceProductDto> productsOfInvoice = invoiceProductService.findByInvoiceId(invoiceId);

            if (invoiceToApprove.getInvoiceType() == InvoiceType.PURCHASE){

                productsOfInvoice.forEach(invoiceProductDto -> productService.increaseProductRemainingQuantity(invoiceProductDto.getProduct().getId(),invoiceProductDto.getQuantity()));
            }

            if (invoiceToApprove.getInvoiceType() == InvoiceType.SALES){
                productsOfInvoice.forEach(invoiceProductDto -> productService.decreaseProductRemainingQuantity(invoiceProductDto.getProduct().getId(),invoiceProductDto.getQuantity()));
            }

        invoiceToApprove.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoiceToApprove.setDate(LocalDate.now());

        invoiceRepository.save(invoiceToApprove);
    }

    @Override
    public boolean existsByClientVendorId(Long clientVendorId) {
        return invoiceRepository.existsByClientVendorId(clientVendorId);
    }
}
