package com.school.SBA.ServiceIMPL;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.AcademicProgram;
import com.school.SBA.Entity.ClassHour;
import com.school.SBA.Entity.Schedule;
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
	private ClassHourRepository classHourRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;
	

	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> generateClassHourForAcademicProgram(int programId) {
		
			AcademicProgram program = academicProgramRepository.findById(programId).orElse(null);

			if (program == null)
				throw new IllegalArgumentException("Program Does Not Exist!!!");

			LocalDate recordStartDate = (program.getBeginsAt().isAfter(LocalDate.now()))? program.getBeginsAt() : LocalDate.now();
	        
			List<ClassHour> generatedClassHours = generateClassHoursForWeek(program, recordStartDate);
			List<ClassHour> savedClassHours = repository.saveAll(generatedClassHours);

			List<ClassHourResponse> classHourResponses = savedClassHours.stream().map(this::mapToResponse)
					.collect(Collectors.toList());

			ResponseStructure<List<ClassHourResponse>> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("ClassHours created successfully!!!!");
			responseStructure.setData(classHourResponses);

			return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		}
	

	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(List<ClassHourDTOs> classHourDtoList) {
		List<ClassHourResponse> updatedClassHourResponses = new ArrayList<>();

		classHourDtoList.forEach(classHourDTO -> {
			ClassHour existingClassHour = repository.findById(classHourDTO.getClassHourId()).get();
			Subject subject=subjectRepository.findById(classHourDTO.getSubjectId()).get();
			User teacher=userRepository.findById(classHourDTO.getTeacherId()).get();

			if(existingClassHour != null && subject != null && teacher != null && teacher.getUserRole().equals(UserRole.TEACHER)) {

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
				throw new IllagalRequestException("Invalid Teacher Id.");
			}
		});
		ResponseStructure<List<ClassHourResponse>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("ClassHours updated successfully!!!!");
		responseStructure.setData(updatedClassHourResponses);


		return new ResponseEntity<ResponseStructure<List<ClassHourResponse>>>(responseStructure, HttpStatus.CREATED);
	}
	

	
	public void deletePrograms() {
		List<AcademicProgram> programs =academicProgramRepository.findByIsDeleted(true);
		programs.forEach(program -> {
		    classHourRepository.deleteAll(program.getListClassHours());
		    academicProgramRepository.deleteAll(programs);
		});

	}

	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> autoGenerateClassHourForNextWeek() {

		
		ClassHour lastRecord = repository.findTopByOrderByClassHourIdDesc().get();
	        LocalDate endDate =lastRecord.getBeginsAt().toLocalDate();
			AcademicProgram academicProgram = lastRecord.getAcademicProgram();
			List<ClassHour> previousWeekClassHours = repository.findByAcademicProgramAndBeginsAtBetween(academicProgram, endDate.minusDays(5).atStartOfDay(), endDate.atStartOfDay().plusDays(1));
	        if (previousWeekClassHours.isEmpty()) {
	             throw new IllegalArgumentException("The classHour Is Empty");
	        }
	        List<ClassHour> savedClassHours = new ArrayList<>();
	        List<ClassHour> nextWeekClassHours = generateClassHoursForWeek(previousWeekClassHours.get(0).getAcademicProgram(), endDate);
	        for (ClassHour nextWeek : nextWeekClassHours) {
	            for (ClassHour previous : previousWeekClassHours) {
	               if(nextWeek.getBeginsAt().getDayOfWeek()==previous.getBeginsAt().getDayOfWeek()&&nextWeek.getBeginsAt().toLocalTime().equals(previous.getBeginsAt().toLocalTime())) {
	            	   if(previous.getClassStatus()!=ClassStatus.NOT_SCHEDULED) {
	            		   nextWeek.setRoomNo(previous.getRoomNo());
	            		   nextWeek.setClassStatus(ClassStatus.UPCOMING);
	            		   nextWeek.setUser(previous.getUser());
	            		   nextWeek.setSubject(previous.getSubject());
	            		   
	            	   }
	            	   ClassHour classHour=repository.save(nextWeek);
	        		   savedClassHours.add(classHour);
	               }
	            }
	        }
	      
	        
	        List<ClassHourResponse> classHourResponses = savedClassHours.stream().map(this::mapToResponse).collect(Collectors.toList());

	        
	        ResponseStructure<List<ClassHourResponse>> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("ClassHours updated successfully!!!!");
			responseStructure.setData(classHourResponses);


			return new ResponseEntity<ResponseStructure<List<ClassHourResponse>>>(responseStructure, HttpStatus.CREATED);
		}
		

	private List<ClassHour> generateClassHoursForWeek(AcademicProgram program, LocalDate startingDate) {
	    List<ClassHour> classHours = new ArrayList<>();
	    Schedule schedule = program.getSchool().getSchedule();
	    Duration classDuration = schedule.getClassHourLengthInMinutes();
	    Duration lunchDuration = schedule.getLunchLengthInMinutes();
	    Duration breakDuration = schedule.getBreakLengthInMinutes();
	    LocalTime breakTime = schedule.getBreakTime();
	    LocalTime lunchTime = schedule.getLunchTime();
	    Duration topUp = Duration.ofMinutes(2);

	    // Find the next Monday from the starting date
	    LocalDate nextMonday = startingDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

	    for (int dayOfWeek = 0; dayOfWeek < 6; dayOfWeek++) { // Iterate from Monday to Saturday

	        LocalDate currentDate = nextMonday.plusDays(dayOfWeek);
	        LocalTime opensAt = schedule.getOpenAt();
	        LocalTime endsAt = opensAt.plus(classDuration);
	        ClassStatus status = ClassStatus.NOT_SCHEDULED;

	        for (int classPerDay = schedule.getClassHourPerDay(); classPerDay > 0; classPerDay--) {
	            ClassHour classHour = ClassHour.builder()
	                    .beginsAt(LocalDateTime.of(currentDate, opensAt))
	                    .endsAt(LocalDateTime.of(currentDate, endsAt))
	                    .roomNo(0)
	                    .classStatus(status)
	                    .academicProgram(program)
	                    .build();

	            classHours.add(classHour);

	            if (breakTime.isAfter(opensAt.minus(topUp)) && breakTime.isBefore(endsAt.plus(topUp))) {
	                opensAt = opensAt.plus(breakDuration);
	                endsAt = endsAt.plus(breakDuration);
	                ClassHour classHour2 = ClassHour.builder()
		                    .beginsAt(LocalDateTime.of(currentDate, opensAt.minus(breakDuration)))
		                    .endsAt(LocalDateTime.of(currentDate, opensAt))
		                    .roomNo(0)
		                    .classStatus(ClassStatus.BREAK_TIME)
		                    .academicProgram(program)
		                    .build();

		            classHours.add(classHour2);
	                
	            } else if (lunchTime.isAfter(opensAt.minus(topUp)) && lunchTime.isBefore(endsAt.plus(topUp))) {
	                opensAt = opensAt.plus(lunchDuration);
	                endsAt = endsAt.plus(lunchDuration);
	                ClassHour classHour2 = ClassHour.builder()
		                    .beginsAt(LocalDateTime.of(currentDate, opensAt.minus(lunchDuration )))
		                    .endsAt(LocalDateTime.of(currentDate, opensAt))
		                    .roomNo(0)
		                    .classStatus(ClassStatus.LUNCH_TIME)
		                    .academicProgram(program)
		                    .build();

		            classHours.add(classHour2);
	            }

	            opensAt = endsAt;
	            endsAt = opensAt.plus(classDuration);
	        }
	    }

	    return classHours;
	}
	
	private ClassHourResponse mapToResponse(ClassHour classHour) {
		return ClassHourResponse.builder().beginsAt(classHour.getBeginsAt())
				.endsAt(classHour.getEndsAt()).roomNo(classHour.getRoomNo()).classStatus(classHour.getClassStatus())
				.build();
	}

}