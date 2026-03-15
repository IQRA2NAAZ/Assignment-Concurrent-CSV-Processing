package com.assignment.csvprocessing.service;

import com.assignment.csvprocessing.entity.CsvRecord;
import com.assignment.csvprocessing.repository.CsvRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordProcessingService {

    private final CsvRecordRepository csvRecordRepository;
    private final ProcessingStatsService statsService;

    @Qualifier("recordExecutor")
    private final Executor recordExecutor;

    public void processRecord(Long csvId, String name, String email, Double amount) {

        recordExecutor.execute(() -> {

            try {

                if (!isValidEmail(email)) {
                    log.warn("Invalid email skipped: {}", email);
                    return;
                }

                Thread.sleep(1000);

                CsvRecord record = CsvRecord.builder()
                        .csvId(csvId)
                        .name(name)
                        .email(email)
                        .amount(amount)
                        .build();

                csvRecordRepository.save(record);

                statsService.incrementRecordsProcessed();

                log.info(
                        "Record {} processed by {}",
                        csvId,
                        Thread.currentThread().getName()
                );

            } catch (Exception e) {

                log.error("Error processing record {}", csvId, e);

            }
        });
    }

    private boolean isValidEmail(String email) {

        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}