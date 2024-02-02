package com.school.SBA.ServiceIMPL;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.Schedule;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Repository.ScheduleRepository;
import com.school.SBA.Repository.SchoolRepository;
import com.school.SBA.RequestDTO.ScheduleRequest;
import com.school.SBA.ResponseDTO.ScheduleResponse;
import com.school.SBA.ResponseDTO.SchoolResponse;
import com.school.SBA.Service.ScheduleService;
import com.school.SBA.Utility.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class ScheduleServiceIMPL implements ScheduleService {

	@Autowired
	private ScheduleRepository repository;

	@Autowired
	private SchoolRepository schoolRepository;

	@Autowired
	private ResponseStructure<ScheduleResponse> structure;

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> createSchedule(int schoolId,ScheduleRequest schedulerequest) {
		return schoolRepository.findById(schoolId).map(s->{
			if(s.getSchedule() == null) {
				Schedule schedule = mapToSchedule(schedulerequest);
				schedule= repository.save(schedule);
				s.setSchedule(schedule);
				schoolRepository.save(s);
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setMessage("schedule object created Sucessfully");
				structure.setData(mapToScheduleResponse(schedule,false));
				return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure,HttpStatus.CREATED);
			}else 
				throw new IllagalRequestException("Schedule object is alredy present");
		}).orElseThrow(()->new IllagalRequestException("School has only one school id that is of ADMINS"));

	}
	
	
	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateScheduleById(int scheduleId,
			ScheduleRequest scheduleldRequest) {
		// TODO Auto-generated method stub
		return null;
	}
//	@Override
//	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateScheduleById(int scheduleldId,
//			ScheduleRequest scheduleldRequest) {
//		return scheduleRepository.findById(scheduleldId).map(scheduleld->{
//			scheduleRepository.save(mapToUpdate(scheduleldId, scheduleld.getSchool(), scheduleldRequest));
//			
//			ResponseStructure<ScheduleResponse> responseStructure = new ResponseStructure<ScheduleResponse>();
//			
//			responseStructure.setStatus(HttpStatus.OK.value());
//			responseStructure.setMessage("Scheduleld Updated successfully!!!!");
//			responseStructure.setData(mapToScheduleResponse(scheduleld,false));
//			
//			return new ResponseEntity<ResponseStructure<ScheduleResponse>>(responseStructure, HttpStatus.CREATED);
//		}).orElseThrow(()->new IllegalArgumentException("Scheduleld Does Not Exist!!!"));
//	}
	

	
	private Schedule mapToSchedule(@Valid ScheduleRequest scheduleRequest)
	{
		return Schedule.builder()
				.openAt(scheduleRequest.getOpenAt())
				.closeAt(scheduleRequest.getCloseAt())
				.classHourPerDay(scheduleRequest.getClassHourPerDay())
				.classHourLengthInMinutes(Duration.ofMinutes(scheduleRequest.getClassHourLengthInMinutes()))
				.breakTime(scheduleRequest.getBreakTime())
				.breakLengthInMinutes(Duration.ofMinutes(scheduleRequest.getBreakLengthInMinutes()))
				.lunchTime(scheduleRequest.getLunchTime())
				.lunchLengthInMinutes(Duration.ofMinutes(scheduleRequest.getLunchLengthInMinutes()))
				.build();
	}

	private ScheduleResponse mapToScheduleResponse(Schedule schedule, boolean isDeleted)
	{
		return ScheduleResponse.builder()
				.ScheduleId(schedule.getScheduleId())
				.openAt(schedule.getOpenAt())
				.closeAt(schedule.getCloseAt())
				.classHourPerDay(schedule.getClassHourPerDay())
				.classHourLengthInMinutes((int)schedule.getClassHourLengthInMinutes().toMinutes())
				.breakTime(schedule.getBreakTime())
				.breakLengthInMinutes((int)schedule.getBreakLengthInMinutes().toMinutes())
				.lunchTime(schedule.getLunchTime())
				.lunchLengthInMinutes((int) schedule.getLunchLengthInMinutes().toMinutes())
				.isDeleted(schedule.isDeleted())
				.build();
	}

	@Override
	public List<SchoolResponse> findSchedule(int schoolId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	


}
