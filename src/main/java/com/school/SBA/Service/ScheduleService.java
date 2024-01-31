package com.school.SBA.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.SBA.RequestDTO.ScheduleRequest;
import com.school.SBA.ResponseDTO.ScheduleResponse;
import com.school.SBA.ResponseDTO.SchoolResponse;
import com.school.SBA.Utility.ResponseStructure;

public interface ScheduleService {

	public ResponseEntity<ResponseStructure<ScheduleResponse>> createSchedule(int schoolId, ScheduleRequest scheduleRequest);

	public List<SchoolResponse> findSchedule(int schoolId);

	public ResponseEntity<ResponseStructure<ScheduleResponse>> deleteById(int scheduleId) throws Exception;

}
