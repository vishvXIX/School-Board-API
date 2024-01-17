package com.school.SBA.ServiceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Repository.ScheduleRepository;
import com.school.SBA.ResponseDTO.ScheduleResponse;
import com.school.SBA.Service.ScheduleService;
import com.school.SBA.Utility.ResponseStructure;

@Service
public class ScheduleServiceIMPL implements ScheduleService {

	@Autowired
	private ScheduleRepository repository;

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> createSchedule(int schoolId,
			ScheduleResponse scheduleResponse) {
		
		repository.findById(schoolId);
		
		return null;
	}
	
}
