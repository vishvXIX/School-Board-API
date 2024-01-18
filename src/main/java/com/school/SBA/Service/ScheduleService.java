package com.school.SBA.Service;

import org.springframework.http.ResponseEntity;

import com.school.SBA.RequestDTO.ScheduleRequest;
import com.school.SBA.ResponseDTO.ScheduleResponse;
import com.school.SBA.Utility.ResponseStructure;

public interface ScheduleService {

	public ResponseEntity<ResponseStructure<ScheduleResponse>> createSchedule(int schoolId, ScheduleRequest scheduleRequest);

}
