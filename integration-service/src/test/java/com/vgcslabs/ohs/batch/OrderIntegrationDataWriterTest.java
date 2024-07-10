package com.vgcslabs.ohs.batch;

import com.vgcslabs.ohs.config.BatchJobConfig;
import com.vgcslabs.ohs.dto.OrderBatchJobResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("local")
class OrderIntegrationDataWriterTest {


    @Autowired
    private BatchJobConfig config;

    @Autowired
    @Qualifier("orderIntegrationJsonItemWriter")
    private JsonFileItemWriter<OrderBatchJobResponseDto> writer;


    @Test
    void testJsonFileItemWriterSuccess() throws Exception {
        OrderBatchJobResponseDto responseDto = new OrderBatchJobResponseDto();
        String orderId= "677a3add5c04" ;
        String supplierPid= "4b99-8b15" ;
        String userPid= "2d3e20d32716" ;


        responseDto.setOrderId(orderId);
        responseDto.setSupplierPid(supplierPid);
        responseDto.setUserPid(userPid);

        Chunk<OrderBatchJobResponseDto> chunk = new Chunk<>();
        chunk.add(responseDto);

        String outputFileLocation = "src/test/resources/output.json";
        writer.setResource(new FileSystemResource(outputFileLocation));

        writer.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());
        writer.write(chunk);
        writer.close();

        // check if the output file exists and contains the same data
        File outputFile = new FileSystemResource(config.getOrderIntegrationOutputFile()).getFile();
        assertTrue(outputFile.exists());

        String content = Files.readString(Path.of(outputFile.getPath()));
        assertTrue(content.contains("\"orderId\":\""+orderId+"\""));
        assertTrue(content.contains("\"userPid\":\""+userPid+"\""));
        assertTrue(content.contains("\"supplierPid\":\""+supplierPid+"\""));

    }
}