package com.vgcslabs.ohs.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderBatchJobResponseDto {


    private String supplierPid;
    private String orderId;
    private String userPid;



}
