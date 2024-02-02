package com.school.SBA.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.SBA.RequestDTO.ScheduleRequest;
import com.school.SBA.ResponseDTO.ScheduleResponse;
import com.school.SBA.ResponseDTO.SchoolResponse;
import com.school.SBA.Service.ScheduleService;
import com.school.SBA.Utility.ResponseStructure;

@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> createSchedule(@PathVariable int schoolId, @RequestBody ScheduleRequest scheduleRequest) {
		return service.createSchedule(schoolId,scheduleRequest);
	}
	
	@PutMapping("/schools/{schoolId}/schedules")
	public List<SchoolResponse> findSchedule(@PathVariable int schoolId){
		return service.findSchedule(schoolId);
	}
	
	@PutMapping("/schedules/{scheduleId}")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateScheduleById(@PathVariable int scheduleId,
		@RequestBody ScheduleRequest scheduleldRequest){
		return service.updateScheduleById(scheduleId,scheduleldRequest);
	}
}
