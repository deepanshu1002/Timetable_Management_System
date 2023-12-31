package com.app.service;

import java.util.List;

import com.app.dto.ApiResponseDto;
import com.app.dto.AuthRequest;
import com.app.dto.AuthResp;
import com.app.dto.SignupRequest;
import com.app.dto.SignupResp;
import com.app.dto.TeacherDTO;
import com.app.dto.UserDTO;
import com.app.entities.IsValidUser;

public interface UserService {
	public SignupResp validUser(Long userId, Long roleId);

	public AuthResp authenticateUser(AuthRequest request);

	public SignupResp registerUser(SignupRequest request);

	public String deleteNotValidUser(Long userId);

	public SignupResp addUserDetails(SignupRequest user);

	public List<IsValidUser> getAllIsValidUser();

	public void updateRoleId(Long userId, Long roleId);

	public List<TeacherDTO> getAllTeachers(Long roleId);

	public String updatePassword(String email, String password);

	// edit user details
	public ApiResponseDto editUserDetails(UserDTO user);

	// get user by id
	public UserDTO getUserById(Long userId);

}
