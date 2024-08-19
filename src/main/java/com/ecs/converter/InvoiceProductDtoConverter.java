package com.ecs.converter;

import com.ecs.dto.InvoiceProductDto;
import com.ecs.service.InvoiceProductService;
import org.springframework.core.convert.converter.Converter;

public class InvoiceProductDtoConverter implements Converter<String, InvoiceProductDto> {

    private final InvoiceProductService invoiceProductService;

    public InvoiceProductDtoConverter(InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public InvoiceProductDto convert(String source) {

        if (source==null || source.equals("")){
            return null;
        }
        return invoiceProductService.findById(Long.parseLong(source));
    }
}
