package com.chillapps.musicelsius.Repository;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.chillapps.musicelsius.Entity.ServiceCall;

@Repository
public interface ServiceCallRepository extends JpaRepository<ServiceCall, Integer>{

	@Procedure
	void insert_service_call(String genre, String method_used, LocalDateTime time_of_call,
			double temperature, String location);
}
