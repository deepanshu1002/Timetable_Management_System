package com.app.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class LeaveApplicationPkId implements Serializable {
	private int teacherId;
	private LocalDate fromDate;
}
