package com.vgcslabs.ohs.batch;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class BatchJobProperties {

    @Value("${order.integration.input.file}")
    private String orderIntegrationInputFile;

    @Value("${order.integration.output.success.file}")
    private String orderIntegrationOutputSucessFile;

    @Value("${order.integration.output.fail.file}")
    private String orderIntegrationOutputFailFile;

    @Value("${order.integration.csv.read.linesToSkip::#{1}}")
    private int linesToSkip;

}
