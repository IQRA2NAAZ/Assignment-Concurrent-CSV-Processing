package com.assignment.csvprocessing.repository;

import com.assignment.csvprocessing.entity.FileProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileStatusRepository extends JpaRepository<FileProcessing, Long> {

    Optional<FileProcessing> findByFileName(String fileName);

}