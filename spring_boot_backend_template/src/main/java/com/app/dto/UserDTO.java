package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
	
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNo;
	private String password;
	private Long deptId;
	private String imagePath;
}
