package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ApiResponseDto;
import com.app.dto.AuthRequest;
import com.app.dto.AuthResp;
import com.app.dto.SignupRequest;
import com.app.dto.SignupResp;
import com.app.dto.TeacherDTO;
import com.app.dto.UserDTO;
import com.app.entities.Department;
import com.app.entities.IsValidUser;
import com.app.entities.Role;
import com.app.entities.Users;
import com.app.repository.DepartmentRepository;
import com.app.repository.IsValidUserRepo;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService,UserDetailsService {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private DepartmentRepository deptRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private IsValidUserRepo isValidUser;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		String role = user.getRole().getRole();
		//List<SimpleGrantedAuthority> authorities = List.of( new SimpleGrantedAuthority(role) );
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);
		User authenticatedUser = new User(user.getEmail(), 
						user.getPassword(), 
						authorities);
		return authenticatedUser;
	}

	public SignupResp registerUser(SignupRequest request) {
		IsValidUser user = mapper.map(request, IsValidUser.class);
		IsValidUser user2 = isValidUser.save(user);
		return mapper.map(user2, SignupResp.class);
	}

	public SignupResp validUser(Long userId, Long roleId) {
		IsValidUser validUser = isValidUser.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid userId"));
		validUser.setRoleId(roleId);
		Department dept = deptRepo.findById(validUser.getDeptId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid dept ID"));
		Role role = roleRepo.findById(validUser.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid role id"));
		Users user = mapper.map(validUser, Users.class);
		deleteNotValidUser(userId);
		dept.addUser(user);
		role.addUser(user);

		// Users persistentUser = userRepo.save(user);
		return mapper.map(user, SignupResp.class);
	}

	public AuthResp authenticateUser(AuthRequest request) {

		Users user = userRepo.findByEmailAndPassword(request.getEmail(), request.getPassword())
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));

		return new AuthResp(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getDept().getDeptId(), user.getRole().getRoleId());

	}

	public String deleteNotValidUser(Long userId) {

		IsValidUser user = isValidUser.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid userId"));

		isValidUser.delete(user);
		return " non valid user Deleted";
	}

	public SignupResp addUserDetails(SignupRequest user) {
		Department dept = deptRepo.findById(user.getDeptId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid dept Id"));
		// System.out.println(dept);
		Users user1 = userRepo.findById(user.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid user Id"));
		// Users user1 = mapper.map(user, Users.class);
		mapper.map(user, user1);
		dept.addUser(user1);
		// Users user2 = userRepo.save(user1);
		return mapper.map(user1, SignupResp.class);
	}

	public List<IsValidUser> getAllIsValidUser() {
		return isValidUser.findAll();

	}

	public void updateRoleId(Long userId, Long roleId) {
		IsValidUser user = isValidUser.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("invalid user id"));
		user.setRoleId(roleId);
	}

	public List<TeacherDTO> getAllTeachers(Long roleId) {
		Role role = roleRepo.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("invalid role id"));
		List<TeacherDTO> teacherList = new ArrayList<TeacherDTO>();
		List<Users> teachers = userRepo.findByRole(role);
		for (Users teacher : teachers) {
			TeacherDTO teacherDto = mapper.map(teacher, TeacherDTO.class);
			teacherList.add(teacherDto);
		}
		return teacherList;
	}

	@Override
	public String updatePassword(String email, String password) {
		Users user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Invalid user Id"));

		user.setPassword(password);
		userRepo.save(user);
		return ("password updated successfully");
	}

	public ApiResponseDto editUserDetails(UserDTO user) {
		Department dept = deptRepo.findById(user.getDeptId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Department id!"));
		Users userToEdit = userRepo.findById(user.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		userToEdit.setFirstName(user.getFirstName());
		userToEdit.setLastName(user.getLastName());
		userToEdit.setEmail(user.getEmail());
		userToEdit.setMobileNo(user.getMobileNo());
		userToEdit.setPassword(user.getPassword());
		userToEdit.setDept(dept);

		Users updateUser = userRepo.save(userToEdit);

		UserDTO updateUserDTO = mapper.map(updateUser, UserDTO.class);

		return new ApiResponseDto("User details updated successfully");
	}

	@Override
	public UserDTO getUserById(Long userId) {
		Users user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid User Id"));
		UserDTO userDto = mapper.map(user, UserDTO.class);
		userDto.setDeptId(user.getDept().getDeptId());
		return userDto;
	}
}
