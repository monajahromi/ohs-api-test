package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.config.BatchJobConfig;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("local")
class OrderIntegrationDataReaderTest {

    @Autowired
    private BatchJobConfig config;

    @Autowired
    @Qualifier("orderIntegrationFlatFileItemReader")
    private FlatFileItemReader<OrderIntegrationDto> reader;

    @Test
    void testFlatFileItemReaderSuccess() throws Exception {

        String outputFileLocation = "src/test/resources/input.csv";
        reader.setResource(new FileSystemResource(outputFileLocation));
        reader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());
        OrderIntegrationDto item = reader.read();

        reader.close();

        assertNotNull(item);
        assertEquals("1", item.getId());
        assertEquals("John", item.getFirstName());
        assertEquals("Doe", item.getLastName());
        assertEquals("john.doe@example.com", item.getEmail());
        assertEquals("supplier-123", item.getSupplierPid());
        assertEquals("4111111111111111", item.getCreditCardNumber());
        assertEquals("VISA", item.getCreditCardType());
        assertEquals("order-123", item.getOrderId());
        assertEquals("product-456", item.getProductPid());
        assertEquals("123 Main St", item.getShippingAddress());
        assertEquals("USA", item.getCountry());
        assertEquals("2023-07-01", item.getDateCreated());
        assertEquals("1", item.getQuantity());
        assertEquals("John Doe", item.getFullName());
        assertEquals(1, item.getOrderStatus());

    }
}