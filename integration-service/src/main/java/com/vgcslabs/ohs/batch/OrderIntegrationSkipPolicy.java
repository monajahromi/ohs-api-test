package com.vgcslabs.ohs.batch;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

@Slf4j
public class OrderIntegrationSkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        log.error("Skipping record due to exception: ", t);
        return true;
    }
}