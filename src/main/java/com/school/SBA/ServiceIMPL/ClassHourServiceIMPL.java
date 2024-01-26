package com.school.SBA.ServiceIMPL;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.ClassHour;
import com.school.SBA.Entity.Schedule;
import com.school.SBA.Entity.School;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Repository.AcademicProgramRepository;
import com.school.SBA.Repository.ClassHourRepository;
import com.school.SBA.Service.ClassHourService;
import com.school.SBA.Utility.ResponseStructure;
import com.school.SBA.enums.ClassStatus;

@Service
public class ClassHourServiceIMPL implements ClassHourService {

	@Autowired
	private ClassHourRepository repository;

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
	
	
}