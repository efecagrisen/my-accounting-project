package com.ecs.converter;

import com.ecs.dto.CompanyDto;
import com.ecs.service.CompanyService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyDtoConverter implements Converter<String, CompanyDto> {

    private final CompanyService companyService;

    public CompanyDtoConverter(CompanyService companyService) {
        this.companyService = companyService;
    }


    @Override
    public CompanyDto convert(String source) {
        if (source==null || source.equals("")) return null;

        return companyService.findById(Long.parseLong(source));
    }
}
