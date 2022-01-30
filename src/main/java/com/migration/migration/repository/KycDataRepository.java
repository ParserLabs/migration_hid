package com.migration.migration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.migration.migration.entity.KycData;

public interface KycDataRepository extends JpaRepository<KycData, String> {

}
