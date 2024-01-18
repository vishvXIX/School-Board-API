package com.school.SBA.ServiceIMPL;

import java.time.Duration;

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
import com.school.SBA.Service.ScheduleService;
import com.school.SBA.Utility.ResponseStructure;

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
				structure.setData(mapToScheduleResponse(schedule));
				return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure,HttpStatus.CREATED);
			}else 
				throw new IllagalRequestException("Schedule object is alredy present");
		}).orElseThrow(()->new IllagalRequestException("School has only one school id that is of ADMINS"));

	}

	private Schedule mapToSchedule(ScheduleRequest request) {

		Schedule schedule = new Schedule();
		schedule.setOpenAt(request.getOpenAt());
		schedule.setCloseAt(request.getCloseAt());
		schedule.setClassHourPerDay(request.getClassHourPerDay());
		schedule.setClassHourLengthInMinutes(Duration.ofMinutes(request.getClassHourLengthInMinutes()));
		schedule.setBreakTime(request.getBreakTime());
		schedule.setBreakLengthInMinutes(Duration.ofMinutes(request.getBreakLengthInMinutes()));
		schedule.setLunchTime(request.getLunchTime());
		schedule.setLunchLengthInMinutes(Duration.ofMinutes(request.getLunchLengthInMinutes()));

		return schedule;
	}


	public ScheduleResponse mapToScheduleResponse(Schedule schedule) {

		ScheduleResponse response = new ScheduleResponse();
		response.setOpenAt(schedule.getOpenAt());
		response.setCloseAt(schedule.getCloseAt());
		response.setClassHourPerDay(schedule.getClassHourPerDay());
		response.setClassHourLengthInMinutes(schedule.getClassHourLengthInMinutes());
		response.setBreakTime(schedule.getBreakTime());
		response.setBreakLengthInMinutes(schedule.getBreakLengthInMinutes());
		response.setLunchTime(schedule.getLunchTime());
		response.setLunchLengthInMinutes(schedule.getLunchLengthInMinutes());

		return response;
	}


}
