package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.util.regex.Pattern;

public class OrderIntegrationFieldSetMapper extends BeanWrapperFieldSetMapper<OrderIntegrationDto> {
    private static final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public OrderIntegrationFieldSetMapper() {
        setTargetType(OrderIntegrationDto.class);
    }

    @Override
    public OrderIntegrationDto mapFieldSet(FieldSet fieldSet) throws BindException {
        OrderIntegrationDto dto = super.mapFieldSet(fieldSet);
        validateDto(dto);
        return dto;
    }

    // Required field could be added here later!
    private void validateDto(OrderIntegrationDto dto) throws BindException {
        if (isNullOrEmpty(dto.getId()) ||
                isNullOrEmpty(dto.getEmail()) ||
                !emailPattern.matcher(dto.getEmail()).matches() ||
                isNullOrEmpty(dto.getSupplierPid()) ||
                isNullOrEmpty(dto.getOrderId()) ||
                isNullOrEmpty(dto.getProductPid()) ||
                dto.getOrderStatus() == null) {
            throw new BindException(dto, "orderIntegrationDto");
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}