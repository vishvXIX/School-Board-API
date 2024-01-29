package com.school.SBA.ServiceIMPL;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.ClassHour;
import com.school.SBA.Entity.Schedule;
import com.school.SBA.Entity.School;
import com.school.SBA.Entity.Subject;
import com.school.SBA.Entity.User;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Repository.AcademicProgramRepository;
import com.school.SBA.Repository.ClassHourRepository;
import com.school.SBA.Repository.SubjectRepository;
import com.school.SBA.Repository.UserRepository;
import com.school.SBA.RequestDTO.ClassHourDTOs;
import com.school.SBA.ResponseDTO.ClassHourResponse;
import com.school.SBA.Service.ClassHourService;
import com.school.SBA.Utility.ResponseStructure;
import com.school.SBA.enums.ClassStatus;
import com.school.SBA.enums.UserRole;

@Service
public class ClassHourServiceIMPL implements ClassHourService {

	@Autowired
	private ClassHourRepository repository;

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	private boolean isBreakTime(LocalDateTime currentTime , Schedule schedule)
	{
		LocalTime breakTimeStart = schedule.getBreakTime();
		LocalTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLengthInMinutes().toMinutes());
		
		return (currentTime.toLocalTime().isAfter(breakTimeStart) && currentTime.toLocalTime().isBefore(breakTimeEnd));

	}
	
	private boolean isLunchTime(LocalDateTime currentTime , Schedule schedule)
	{
		LocalTime lunchTimeStart = schedule.getLunchTime();
		LocalTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLengthInMinutes().toMinutes());
		
		return (currentTime.toLocalTime().isAfter(lunchTimeStart) && currentTime.toLocalTime().isBefore(lunchTimeEnd));

	}
	
	@Override
	public ResponseEntity<ResponseStructure<String>> generateClassHourForAcademicProgram(int programId) {
	    return academicProgramRepository.findById(programId)
	            .map(academicProgram -> {
	                School school = academicProgram.getSchool();
	                Schedule schedule = school.getSchedule();
	                if (schedule != null) {
	                    int classHourPerDay = schedule.getClassHourPerDay();
	                    int classHourLength = (int) schedule.getClassHourLengthInMinutes().toMinutes();

	                    LocalDateTime currentTime = LocalDateTime.now().with(schedule.getOpenAt());

	                    LocalTime lunchTimeStart = schedule.getLunchTime();
	                    LocalTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLengthInMinutes().toMinutes());
	                    LocalTime breakTimeStart = schedule.getBreakTime();
	                    LocalTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLengthInMinutes().toMinutes());

	                    for (int day = 1; day <= 6; day++) {
	                        for (int hour = 0; hour < classHourPerDay + 2; hour++) {
	                            ClassHour classHour = new ClassHour();

	                            if (!currentTime.toLocalTime().equals(lunchTimeStart) && !isLunchTime(currentTime, schedule)) {
	                                if (!currentTime.toLocalTime().equals(breakTimeStart) && !isBreakTime(currentTime, schedule)) {
	                                    LocalDateTime beginsAt = currentTime;
	                                    LocalDateTime endsAt = beginsAt.plusMinutes(classHourLength);

	                                    classHour.setBeginsAt(beginsAt);
	                                    classHour.setEndsAt(endsAt);
	                                    classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);

	                                    currentTime = endsAt;
	                                } else {
	                                    classHour.setBeginsAt(currentTime);
	                                    classHour.setEndsAt(currentTime.plusMinutes(schedule.getBreakLengthInMinutes().toMinutes()));
	                                    classHour.setClassStatus(ClassStatus.BREAK_TIME);
	                                    currentTime = currentTime.plusMinutes(schedule.getBreakLengthInMinutes().toMinutes());
	                                }
	                            } else {
	                                classHour.setBeginsAt(currentTime);
	                                classHour.setEndsAt(currentTime.plusMinutes(schedule.getLunchLengthInMinutes().toMinutes()));
	                                classHour.setClassStatus(ClassStatus.LUNCH_TIME);
	                                currentTime = currentTime.plusMinutes(schedule.getLunchLengthInMinutes().toMinutes());
	                            }
	                            classHour.setAcademicProgram(academicProgram);
	                            repository.save(classHour);
	                        }
	                        currentTime = currentTime.plusDays(1).with(schedule.getOpenAt());
	                    }
	                } else {
	                    throw new IllagalRequestException("The school does not contain any schedule, please provide a schedule to the school");
	                }

	                return ResponseEntity.ok(ResponseStructure.<String>builder()
	                        .status(HttpStatus.CREATED.value())
	                        .message("ClassHour generated successfully for the academic program")
	                        .data("Class Hour generated for the current week successfully")
	                        .build());
	            })
	            .orElseThrow(() -> new IllagalRequestException("Invalid Program Id"));
	}
	
	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(List<ClassHourDTOs> classHourDtoList) {
		List<ClassHourResponse> updatedClassHourResponses = new ArrayList<>();

		classHourDtoList.forEach(classHourDTO -> {
			ClassHour existingClassHour =repository.findById(classHourDTO.getClassHourId()).get();
			Subject subject=subjectRepository.findById(classHourDTO.getSubjectId()).get();
			User teacher=userRepository.findById(classHourDTO.getTeacherId()).get();
			if(existingClassHour!=null&&subject!=null&&teacher!=null&&teacher.getUserRole().equals(UserRole.TEACHER)) {
				
				if((teacher.getSubject()).equals(subject))
				existingClassHour.setSubject(subject);
				else
					throw new IllagalRequestException("The Teacher is Not Teaching That Subject");
				existingClassHour.setUser(teacher);
				existingClassHour.setRoomNo(classHourDTO.getRoomNo());
				LocalDateTime currentTime = LocalDateTime.now();
				
				if (existingClassHour.getBeginsAt().isBefore(currentTime) && existingClassHour.getEndsAt().isAfter(currentTime)) {
				    existingClassHour.setClassStatus(ClassStatus.ONGOING);
				} else if (existingClassHour.getEndsAt().isBefore(currentTime)) {
				    existingClassHour.setClassStatus(ClassStatus.COMPLETED);
				} else {
				    existingClassHour.setClassStatus(ClassStatus.UPCOMING);
				}
				
				existingClassHour=repository.save(existingClassHour);
 
				ClassHourResponse classHourResponse = new ClassHourResponse();
				classHourResponse.setBeginsAt(existingClassHour.getBeginsAt());
				classHourResponse.setEndsAt(existingClassHour.getEndsAt());
				classHourResponse.setClassStatus(existingClassHour.getClassStatus());
				classHourResponse.setRoomNo(existingClassHour.getRoomNo());
				updatedClassHourResponses.add(classHourResponse);
				
			} 
			else {
				throw new IllagalRequestException("Invalid ClassHourID or Invalid User or Invalid Subject");
			}
		});
		ResponseStructure<List<ClassHourResponse>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("ClassHours updated successfully!!!!");
		responseStructure.setData(updatedClassHourResponses);

		return new ResponseEntity<ResponseStructure<List<ClassHourResponse>>>(responseStructure, HttpStatus.CREATED);
	}
	
	
}