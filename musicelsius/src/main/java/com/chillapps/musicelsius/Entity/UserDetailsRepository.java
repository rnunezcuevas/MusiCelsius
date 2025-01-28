package com.chillapps.musicelsius.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
	
	UserDetails findByRefId(String refId);
}