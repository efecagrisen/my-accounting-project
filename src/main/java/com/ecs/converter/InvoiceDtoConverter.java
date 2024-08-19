package com.ecs.converter;

import com.ecs.dto.InvoiceDto;
import com.ecs.service.InvoiceService;
import org.springframework.core.convert.converter.Converter;

public class InvoiceDtoConverter implements Converter<String, InvoiceDto> {

    private final InvoiceService invoiceService;

    public InvoiceDtoConverter(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceDto convert(String source) {

        if (source == null || source.equals("")){
            return null;
        }

        return invoiceService.findById(Long.parseLong(source));
    }
}
