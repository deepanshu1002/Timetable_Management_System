package com.app.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Leave_Application_tbl")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class LeaveApplication{
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	@Id
	private Long leaveApplicationId;
	private LocalDate fromDate;
	private LocalDate toDate;
	private Boolean status;
	private String reason;
	
}
