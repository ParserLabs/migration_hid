package com.migration.migration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.migration.migration.entity.UserEntity;

public interface UserEntityDataRepository extends JpaRepository<UserEntity, String> {

//	@Query(value = "select a.health_id_number,h.phr_address as health_id,a.password,\n"
//			+ "a.name as full_name,a.first_name,a.last_name,a.middle_name,\n"
//			+ "a.mobile,a.email,a.gender,a.month_of_birth,a.day_of_birth,\n"
//			+ "a.year_of_birth,a.profile_photo,a.kyc_photo, a.status,h.created_date,\n"
//			+ "h.created_by,h.last_modified_date as updated_date,\n"
//			+ "h.last_modified_by as updated_by,a.state_code, a.district_code, a.address, auth.auth_methods , a.cm_migrated , a.phr_migrated  from accounts a "
//			+ "inner join hid_phr_address h on a.health_id_number = h.health_id_number "
//			+ "inner join (SELECT health_id_number, string_agg(auth_methods, ',') as auth_methods FROM account_auth_methods group by health_id_number) auth on a.health_id_number = auth.health_id_number"
//			+ " where h.status = 'ACTIVE'\n"
//			+ "and h.preferred = 1 and h.phr_address  IS NOT NULL  and ( a.cm_migrated is NULL or a.cm_migrated='N'\n"
//			+ " or a.phr_migrated is NULL or a.cm_migrated='N')order by h.created_date asc OFFSET :offset limit :limit", nativeQuery = true)


	@Query(value = "select a.health_id_number,h.phr_address as health_id,a.password,\n"
			+ "a.name as full_name,a.first_name,a.last_name,a.middle_name,\n"
			+ "a.mobile,a.email,a.gender,a.month_of_birth,a.day_of_birth,\n"
			+ "a.year_of_birth,a.profile_photo,a.kyc_photo, a.status,h.created_date,\n"
			+ "h.created_by,h.last_modified_date as updated_date,\n"
			+ "h.last_modified_by as updated_by,a.state_code, a.district_code, a.address, auth.auth_methods , a.cm_migrated , a.phr_migrated  from accounts a "
			+ "inner join hid_phr_address h on a.health_id_number = h.health_id_number "
			+ "inner join (SELECT health_id_number, string_agg(auth_methods, ',') as auth_methods FROM account_auth_methods group by health_id_number) auth on a.health_id_number = auth.health_id_number"
			+ " where h.status = 'ACTIVE'\n"
			+ "and h.preferred = 1 and h.phr_address  IS NOT NULL  and \n"
			+ " (a.phr_migrated is NULL or a.phr_migrated='N') order by h.created_date asc OFFSET :offset limit :limit", nativeQuery = true)
	List<Object> findAbhaAccounts(@Param("offset") long offset, @Param("limit") int limit);

	@Query(value = "select id ,phr_address , health_id_number , migrated from dto_profile_photo2 where migrated = 0 order by id  OFFSET :offset limit :limit" ,nativeQuery = true)
	List<Object> findHealthIdNumberAndPhrAddress(@Param("offset") long offset, @Param("limit") int limit);
	
	@Query(value = "select a.health_id_number, a.profile_photo , a.kyc_photo , a.profile_photo_compressed   from accounts a where health_id_number=?1 ",nativeQuery = true)
	Object getProfilePhoto(@Param("healthIdNumber") String healthIdNumber);
	
	@Transactional
	@Modifying
	@Query(value = "update accounts set cm_migrated=?1 where health_id_number=?2", nativeQuery = true)
	int updateAbhaAccoutsForCM(@Param("cmMigrated") String cmMigrated, 
			@Param("healthIdNumber") String healthIdNumber);

	
	@Transactional
	@Modifying
	@Query(value = "update accounts set  phr_migrated=?1 where health_id_number=?2", nativeQuery = true)
	int updateAbhaAccoutsForPHR(@Param("phrMigrated") String phrMigrated,
			@Param("healthIdNumber") String healthIdNumber);

	@Transactional
	@Modifying
	@Query(value = "update dto_profile_photo2 set  migrated=?1 where health_id_number=?2", nativeQuery = true)
	int update_dto_profile_photo(@Param("phrMigrated") Integer phrMigrated,
			@Param("healthIdNumber") String healthIdNumber);

	
	@Query(value = "select count(*) from accounts a inner join hid_phr_address h on a.health_id_number = h.health_id_number where h.status = 'ACTIVE' and h.preferred = 1 and h.phr_address IS NOT NULL and ( a.cm_migrated is NULL or a.cm_migrated='N' or a.phr_migrated is NULL or a.cm_migrated='N' )",nativeQuery = true)
	long countABHA();
}
