package com.migration.migration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.migration.migration.entity.UserEntity;

public interface UserEntityDataRepository extends JpaRepository<UserEntity, String> {

	@Query(value = "select\n" + "a.health_id_number,\n" + "h.phr_address as health_id,\n" + "a.password,\n"
			+ "a.name as full_name,\n" + "a.first_name,\n" + "a.last_name,\n" + "a.middle_name,\n" + "a.mobile,\n"
			+ "a.email,\n" + "a.gender,\n" + "a.month_of_birth,\n" + "a.day_of_birth,\n" + "a.year_of_birth,\n"
			+ "a.profile_photo,\n" + "a.status,\n" + "h.created_date,\n" + "h.created_by,\n"
			+ "h.last_modified_date as updated_date,\n" + "h.last_modified_by as updated_by\n" + "\n"
			+ "from accounts a\n" + "inner join hid_phr_address h\n" + "on a.health_id_number = h.health_id_number\n"
			+ "where\n" + "h.status = 'ACTIVE'\n"
			+ "and h.preferred = 1 and h.phr_address IS NOT NULL;", nativeQuery = true)
	List<UserEntity> findPhrAddress();

}
