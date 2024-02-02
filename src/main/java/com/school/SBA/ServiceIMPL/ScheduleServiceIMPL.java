package com.school.SBA.ServiceIMPL;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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
				validateSchedule(schedule);
				validateBreaksAndLunch(schedule);
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
	public ResponseEntity<ResponseStructure<ScheduleResponse>> findSchedule(int schoolId) {
		return schoolRepository.findById(schoolId).map(school->{
			ResponseStructure<ScheduleResponse> responseStructure = new ResponseStructure<ScheduleResponse>();
			responseStructure.setStatus(HttpStatus.FOUND.value());
			responseStructure.setMessage("Scheduleld Fetched successfully!!!!");
			responseStructure.setData(mapToScheduleResponse(school.getSchedule(), false));
			return new ResponseEntity<ResponseStructure<ScheduleResponse>>(responseStructure, HttpStatus.FOUND);
		}).orElseThrow(()->new IllagalRequestException("School Does Not Exist!!!"));
	}

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateScheduleById(int scheduleId,
			ScheduleRequest scheduleldRequest) {

			return repository.findById(scheduleId).map(scheduled->{
				Schedule schedule= mapToUpdate(scheduleId, scheduleldRequest);
				validateSchedule(schedule);
				validateBreaksAndLunch(schedule);
				repository.save(schedule);
				ResponseStructure<ScheduleResponse> responseStructure = new ResponseStructure<ScheduleResponse>();
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Scheduleld Updated successfully!!!!");
				responseStructure.setData(mapToScheduleResponse(schedule,false));
				return new ResponseEntity<ResponseStructure<ScheduleResponse>>(responseStructure, HttpStatus.CREATED);
			}).orElseThrow(()->new IllegalArgumentException("Scheduleld Does Not Exist!!!"));
		}
	
	private Schedule mapToUpdate(int scheduleId, ScheduleRequest scheduleldRequest) {
		return Schedule.builder()
				.ScheduleId(scheduleId)
				.breakLengthInMinutes(Duration.ofMinutes(scheduleldRequest.getBreakLengthInMinutes()))
				.breakTime(scheduleldRequest.getBreakTime())
				.classHourLengthInMinutes(Duration.ofMinutes(scheduleldRequest.getClassHourLengthInMinutes()))
				.classHourPerDay(scheduleldRequest.getClassHourPerDay())
				.closeAt(scheduleldRequest.getCloseAt())
				.openAt(scheduleldRequest.getOpenAt())
				.lunchLengthInMinutes(Duration.ofMinutes(scheduleldRequest.getLunchLengthInMinutes()))
				.lunchTime(scheduleldRequest.getLunchTime())
				.build();
	}


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

	

	
	private void validateSchedule(Schedule schedule) {
		LocalTime opensAt = schedule.getOpenAt();
		LocalTime closesAt = schedule.getCloseAt();

		long totalMinutes = opensAt.until(closesAt, ChronoUnit.MINUTES);
		long expectedTotalMinutes = (schedule.getClassHourPerDay() * schedule.getClassHourLengthInMinutes().toMinutes()) +
				schedule.getBreakLengthInMinutes().toMinutes() +
				schedule.getLunchLengthInMinutes().toMinutes();

		if (totalMinutes != expectedTotalMinutes) {
			throw new IllegalArgumentException("Total hours do not add up correctly");
		}
	}

	private void validateBreaksAndLunch(Schedule schedule) {
		LocalTime breakTime = schedule.getBreakTime();
		LocalTime lunchTime = schedule.getLunchTime();
		LocalTime opensAt = schedule.getOpenAt();
		Duration classTime=schedule.getClassHourLengthInMinutes();
		long totalBreakMinutes = opensAt.until(breakTime, ChronoUnit.MINUTES);
		long totalLunchMinutes = opensAt.until(lunchTime, ChronoUnit.MINUTES);
		totalLunchMinutes=totalBreakMinutes-schedule.getLunchLengthInMinutes().toMinutes();
		if(!(totalBreakMinutes%(classTime.toMinutes())== 0 && totalLunchMinutes%classTime.toMinutes() == 0)) {
			throw new IllegalArgumentException("Break and lunch should start after a class hour ends");
		}
	}


	



	
}
