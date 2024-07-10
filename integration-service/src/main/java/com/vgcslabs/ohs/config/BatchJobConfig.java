package com.vgcslabs.ohs.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
//@ConfigurationProperties(prefix = "order.integration")
public class BatchJobConfig {

    @Value("${order.integration.input.file}")
    private String orderIntegrationInputFile;

    @Value("${order.integration.output.file}")
    private String orderIntegrationOutputFile;

    @Value("${order.integration.csv.read.linesToSkip::#{1}}")
    private int linesToSkip;
}
