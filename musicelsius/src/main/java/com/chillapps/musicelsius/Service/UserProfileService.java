package com.chillapps.musicelsius.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chillapps.musicelsius.Entity.UserDetails;
import com.chillapps.musicelsius.Entity.UserDetailsRepository;

import se.michaelthelin.spotify.model_objects.specification.User;

@Service
public class UserProfileService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public UserDetails insertOrUpdateUserDetails(User user, String accessToken, String refreshToken) {
		return null;
	}
}