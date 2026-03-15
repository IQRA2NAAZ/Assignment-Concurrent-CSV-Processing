package com.assignment.csvprocessing.repository;

import com.assignment.csvprocessing.entity.CsvRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvRecordRepository extends JpaRepository<CsvRecord, Long> {
}