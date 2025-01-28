package com.chillapps.musicelsius.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_DETAILS")
@Getter
@Setter
public class UserDetails implements Serializable {
	private static final long serialVersionUID = 3937414011943770889L;

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Getter
	@Setter
	@Column(name = "ACCESS_TOKEN")
	private String accessToken;

	@Getter
	@Setter
	@Column(name = "REFRESH_TOKEN")
	private String refreshToken;

	@Getter
	@Setter
	@Column(name = "REF_ID")
	private String refId;
	
	// More information as per your need

}