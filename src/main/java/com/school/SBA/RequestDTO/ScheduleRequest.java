package com.school.SBA.RequestDTO;

import java.time.LocalTime;

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
public class ScheduleRequest {

	private LocalTime openAt;
	private LocalTime closeAt;
	private int classHourPerDay;
	private int classHourLengthInMinutes;
	private LocalTime breakTime;
	private int breakLengthInMinutes; 
	private LocalTime lunchTime;
	private int lunchLengthInMinutes;
	

}
