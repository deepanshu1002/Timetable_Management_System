package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ApiResponseDto;
import com.app.entities.LeaveApplication;
import com.app.service.LeaveApplicationService;


@RestController
@RequestMapping("/leaveapp")
public class LeaveApplicationController {
	@Autowired
	private LeaveApplicationService leaveService;
	
	@GetMapping
	public List<LeaveApplication> listAllLeavesApp(){
		return leaveService.getAllLeaveApp();
	}
	
	@PostMapping
	public ApiResponseDto addLeaveAppDetails(@RequestBody LeaveApplication userId) {
		return leaveService.addLeaveAppDetails(userId);
	}
	
	
}
