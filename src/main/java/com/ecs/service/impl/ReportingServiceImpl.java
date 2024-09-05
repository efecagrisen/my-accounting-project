package com.ecs.service.impl;

import com.ecs.dto.InvoiceProductDto;
import com.ecs.entity.InvoiceProduct;
import com.ecs.enums.InvoiceStatus;
import com.ecs.enums.InvoiceType;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.InvoiceProductRepository;
import com.ecs.service.InvoiceProductService;
import com.ecs.service.InvoiceService;
import com.ecs.service.ReportingService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final InvoiceProductRepository invoiceProductRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    public ReportingServiceImpl(InvoiceService invoiceService, InvoiceProductService invoiceProductService, InvoiceProductRepository invoiceProductRepository, SecurityService securityService, MapperUtil mapperUtil) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.invoiceProductRepository = invoiceProductRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<InvoiceProductDto> listAllApprovedCompanyInvoiceProducts() {

        return invoiceProductService.listAllCompanyInvoiceProducts();
    }

    @Override
    public List<Map.Entry<String, BigDecimal>> getMonthlyProfitLossListAsMapEntries() {
        
        LocalDate companySubscriptionDate = securityService.getLoggedInUser().getCompany().getInsertDateTime().toLocalDate();
        
        List<LocalDate> keysOfMap = mapKeyGenerator(companySubscriptionDate);
        
        return getMonthlyProfitLossListByKeysOfMap(keysOfMap,securityService.getLoggedInUserCompanyId());
    }

    private List<Map.Entry<String,BigDecimal>> getMonthlyProfitLossListByKeysOfMap(List<LocalDate> keys, Long companyId){

        List<Map.Entry<String,BigDecimal>> monthlyProfitLossEntries = new ArrayList<>();

        for (LocalDate currentKey : keys){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM").withLocale(Locale.ENGLISH); // to ensure the dates are in English instead of the language of the system.
            String yearAndMonth = currentKey.format(formatter).toUpperCase();

            BigDecimal profitLossOfTheMonth = invoiceProductService.getProfitLossBasedOnOneMonth(currentKey.getYear(),currentKey.getMonthValue(), securityService.getLoggedInUserCompanyId(), InvoiceType.SALES);

            monthlyProfitLossEntries.add(Map.entry(yearAndMonth,profitLossOfTheMonth));
        }
        return monthlyProfitLossEntries;
    }
    
    private List<LocalDate> mapKeyGenerator(LocalDate subscriptionDate){

        int subscriptionYear = subscriptionDate.getYear();
        Month subscriptionMonth = subscriptionDate.getMonth();
        int subscriptionDay = subscriptionDate.getDayOfMonth();

        LocalDate companySubscriptionDate = LocalDate.of(subscriptionYear,subscriptionMonth,subscriptionDay);
        
        List<LocalDate> keysForMap = new ArrayList<>();
        
        LocalDate currentDate = LocalDate.now();
        
        while (companySubscriptionDate.isBefore(currentDate)){
            keysForMap.add(currentDate);
            currentDate = currentDate.minusMonths(1);
        }
        return keysForMap;
    }
    
}
