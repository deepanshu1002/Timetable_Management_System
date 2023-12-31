package com.app.dto;

import com.app.entities.Department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupResp {
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNo;
	private String password;
	private Long deptId;

}
