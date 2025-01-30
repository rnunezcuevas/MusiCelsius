package com.chillapps.musicelsius.Repository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.chillapps.musicelsius.Entity.ServiceCall;

@Repository
public interface ServiceCallRepository extends JpaRepository<ServiceCall, Integer>{

	@Procedure
	void insert_service_call(String genre, String method_used, LocalDateTime time_of_call,
			double temperature, String location);
	
	@Query(nativeQuery = true, value = "select genre, count(*) as number_of_calls "
			+ "from call_history "
			+ "group by genre "
			+ "order by number_of_calls desc")
	List<Object[]> findTopGenre(Pageable pageable);
	
	@Query(nativeQuery = true, value = "select location, count(*) number_of_calls "
			+ "from call_history "
			+ "group by location "
			+ "order by number_of_calls desc")
	List<Object[]> findTopLocation(Pageable pageable);
	
}
