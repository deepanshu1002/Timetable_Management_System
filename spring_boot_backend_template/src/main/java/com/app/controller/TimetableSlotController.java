package com.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AddTimetableSlotDTO;
import com.app.dto.SubjectDTO;
import com.app.dto.TimetableSlotRespoDTO;
import com.app.entities.TimetableSlot;
import com.app.service.TimetableSlotService;

@RestController
@RequestMapping("/timetableSlot")
@CrossOrigin(origins = "*")
public class TimetableSlotController {
	
	@Autowired
	private TimetableSlotService timetableSlotService;
	
	@PostMapping
	public ResponseEntity<?> addTimetableSlot(@RequestBody AddTimetableSlotDTO dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).
				body(timetableSlotService.addNewTimetableSlot(dto));
	}
	
	@GetMapping("/{slotId}")
	public ResponseEntity<?> getTimetableSlotDetailsById(@PathVariable Long slotId) {
	
		TimetableSlotRespoDTO timetableSlotDTO = timetableSlotService.getTimetableSlotDetailsById(slotId);
		
		return ResponseEntity.ok(timetableSlotDTO);
	}
	
	@GetMapping("/{date}/{deptId}")
	public ResponseEntity<?> getLectureData(@PathVariable String date, @PathVariable Long deptId) {
		System.out.println(date +""+deptId);
		 LocalDate date1 = LocalDate.parse(date);
		
		 
		List<TimetableSlotRespoDTO> timetableSlotDetails = timetableSlotService.getLectureDetails(date1, deptId);
		System.out.println("timetableSlotDetails= " +timetableSlotDetails);
		if (timetableSlotDetails.size()==0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
		return ResponseEntity.ok(timetableSlotDetails);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateSubjectSlotDetails(@RequestBody TimetableSlotRespoDTO slot) {
	System.out.println("slot = "+slot);
		return ResponseEntity.status(HttpStatus.OK).body(timetableSlotService.updateSubjectSlot(slot));
	}
	
	@GetMapping("/{date}/{deptId}/{teacherId}")
	public ResponseEntity<?> getLectureDataOfTeacher(@PathVariable String date, @PathVariable Long deptId,@PathVariable Long teacherId) {
		System.out.println(date +""+deptId);
		 LocalDate date1 = LocalDate.parse(date);
		
		List<TimetableSlotRespoDTO> timetableSlotDetails = timetableSlotService.getTeacherLectureDetails(date1, deptId,teacherId);
		System.out.println("timetableSlotDetails= " +timetableSlotDetails);
		if (timetableSlotDetails.size()==0) {
			System.out.println("inside");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
			
		
		return ResponseEntity.ok(timetableSlotDetails);
	}

}
