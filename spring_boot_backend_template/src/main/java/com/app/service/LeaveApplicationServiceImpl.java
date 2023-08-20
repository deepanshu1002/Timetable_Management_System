package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.ApiResponseDto;
import com.app.dto.LeaveApplicationDTO;
import com.app.entities.LeaveApplication;
import com.app.entities.Status;
import com.app.entities.Users;
import com.app.repository.LeaveApplicationRepository;
import com.app.repository.UserRepository;

@Service
@Transactional
public class LeaveApplicationServiceImpl implements LeaveApplicationService {
	@Autowired
	private LeaveApplicationRepository leaveRep;

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepository userRepo;

	@Override
	public List<LeaveApplicationDTO> getAllLeaveApp() {
		List<LeaveApplication> leaveList = leaveRep.findAll();
		return leaveList.stream().map(leaveApplication -> mapper.map(leaveApplication, LeaveApplicationDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ApiResponseDto addLeaveAppDetails(LeaveApplicationDTO leaveAppDetail) {
		Users user = userRepo.findById(leaveAppDetail.getUserId()).orElseThrow(null);
		System.out.println(user);
		LeaveApplication leaveApp = mapper.map(leaveAppDetail, LeaveApplication.class);
		user.addLeaveApplication(leaveApp);
		LeaveApplication leaveApp2 = leaveRep.save(leaveApp);
		return new ApiResponseDto("Leave Application Submitted Successfull...");
	}

	@Override
	public List<LeaveApplicationDTO> getAllPendingLeaveApp() {

		List<LeaveApplication> leaveList = leaveRep.retrieveAll();

		List<LeaveApplicationDTO> leaveDto = new ArrayList<LeaveApplicationDTO>();

		for (LeaveApplication la : leaveList) {
			leaveDto.add(new LeaveApplicationDTO(la.getUser().getUserId(), la.getUser().getFirstName(),
					la.getLeaveApplicationId(), la.getFromDate(), la.getToDate(), la.getStatus().toString(),
					la.getReason()));
		}
		return leaveDto;
	}

	@Override
	public ApiResponseDto getLeaveApp(Long leavApplicationId, String status) {
		LeaveApplication leaveapp = leaveRep.findById(leavApplicationId).orElseThrow(null);
		System.out.println(status);
		if(status.equals("approved")) {
		leaveapp.setStatus(Status.APPROVED);
		return new ApiResponseDto("Leave Application Approved");
		}
		else if(status.equals("rejected")) {
		leaveapp.setStatus(Status.REJECTED);
		return new ApiResponseDto("Leave Application Rejected");
		}
		System.out.println(leaveapp);
		return new ApiResponseDto("Leave Application Still Pending");
	}
	
	

}
