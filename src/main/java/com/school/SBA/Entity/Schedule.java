package com.school.SBA.Entity;

import java.time.Duration;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ScheduleId;
	private LocalTime openAt;
	private LocalTime closeAt;
	private int classHourPerDay;
	private Duration classHourLengthInMinutes;
	private LocalTime breakTime;
	private Duration breakLengthInMinutes; 
	private LocalTime lunchTime;
	private Duration lunchLengthInMinutes;

	
}
