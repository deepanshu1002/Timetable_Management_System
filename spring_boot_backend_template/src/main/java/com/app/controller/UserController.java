package com.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponseDto;
import com.app.dto.AuthRequest;
import com.app.dto.SignupRequest;
import com.app.dto.UserDTO;
import com.app.dto.userEmailPasswordDTO;
import com.app.entities.IsValidUser;
import com.app.security.JwtUtil;
import com.app.service.ImageHandlingService;
import com.app.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageHandlingService imgService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest request) {
		return ResponseEntity.ok(userService.registerUser(request));
	}

	@PostMapping("/signIn")
	public ResponseEntity<?> authenticateUser(@RequestBody @Valid AuthRequest request) {
		try {
			// authenticate user with authentication manager
			Authentication auth = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
			System.out.println("BEFORE AUTH: " + auth);
			auth = authManager.authenticate(auth);
			System.out.println("AFTER AUTH: " + auth);
			// after authentication, create JWT token and return.
			String token = jwtUtil.createToken(auth);
			return ResponseEntity.ok(token);
		} 
		catch (BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@DeleteMapping("/deleteuser/{userId}")
	public String deleteNotValidUser(@PathVariable Long userId) {
		return userService.deleteNotValidUser(userId);
	}

	@GetMapping("/validuser/{userId}/{roleId}")
	public ResponseEntity<?> validUser(@PathVariable Long userId, @PathVariable Long roleId) {
		System.out.println("yoyo");
		return ResponseEntity.ok(userService.validUser(userId, roleId));
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUserDetails(@RequestBody @Valid SignupRequest user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUserDetails(user));
	}
	
	
	@GetMapping("/validuser")
	public List<IsValidUser> getAllIsValidUsers() {
		return userService.getAllIsValidUser();
	}

	@PutMapping("/updateroleid/{userId}/{roleId}")
	public void updateRoleID(@PathVariable Long userId, @PathVariable Long roleId) {
		userService.updateRoleId(userId, roleId);
	}

	@GetMapping("/{roleId}")
	public ResponseEntity<?> getAllTeachers(@PathVariable Long roleId) {
		return ResponseEntity.ok(userService.getAllTeachers(roleId));
	}

	
	@PutMapping("/set-password")
	public void updatePassword(@RequestBody userEmailPasswordDTO dto)
	{
		System.out.println(dto);
		userService.updatePassword(dto.getEmail(),dto.getPassword());
	}
	
	@PutMapping("/editUser")
	public ResponseEntity<ApiResponseDto> editUserDetails(@RequestBody @Valid UserDTO userDTO) 
	{
		System.out.println(userDTO);
        ApiResponseDto response = userService.editUserDetails(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping("editUser/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Long userId) {
		
		UserDTO user= userService.getUserById(userId);
		
		return ResponseEntity.ok(user);
	}
	
//	@PostMapping(value = "/uploadImage/{userId}", consumes = "multipart/form-data")
//	public ResponseEntity<?> uploadImage(@PathVariable Long userId, @RequestParam("file") MultipartFile imageFile) throws IOException{
//		System.out.println("in uploading image"+ userId);
//		return ResponseEntity.status(HttpStatus.CREATED).body(imgService.uploadImage(userId, imageFile));
//	}
	
	@PostMapping(value = "/uploadImage/{userId}", consumes = "multipart/form-data")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long userId) {
		try {
			imgService.uploadFile(file, userId);

			return ResponseEntity.ok("File uploaded successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
		}
	}
	
//	@GetMapping(value = "/image/{userId}",produces = {"image/gif","image/jpeg","image/png"})
//	public ResponseEntity<?> serverUserImage(@PathVariable Long userId) throws IOException {
//		System.out.println("in download img " + userId);
//		return ResponseEntity.ok(imgService.downloadImage(userId));
//	}
	
	 @GetMapping("/getImage/{userId}")
	    public ResponseEntity<byte[]> getImage(@PathVariable Long userId) {
	        try {
	            System.out.println("inside Get Image");
	            Path imageFilePath = Paths.get(imgService.downloadImage(userId));
	            byte[] imageBytes = Files.readAllBytes(imageFilePath);
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type
	            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	        } catch (IOException e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

}
