package com.school.SBA.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.school.SBA.Repository.SchoolRepository;
import com.school.SBA.Service.SchoolService;
import com.school.SBA.ServiceIMPL.ClassHourServiceIMPL;
import com.school.SBA.ServiceIMPL.SchoolServiceIMPL;
import com.school.SBA.ServiceIMPL.UserServiceIMPL;

import jakarta.transaction.Transactional;
import lombok.experimental.PackagePrivate;

@Component
public class ScheduledJobs {

	@Autowired
	private UserServiceIMPL userServiceIMPL;
	
	@Autowired
	private ClassHourServiceIMPL classHourServiceIMPL;
	
	@Autowired
	private SchoolServiceIMPL schoolServiceIMPL;
	
//	@Scheduled(fixedDelay = 1000l)
//	public void test() {
//		System.out.println("Test Method..");
//	}
	
//	1000l*60 = 1 mint
//	1000L*60*60 = 1 hour
	
	
	@Transactional
	@Scheduled(fixedDelay = 2000l*60)
	public void deleteUser() {
		userServiceIMPL.deleteUserIfDeleted();
	}
	
	@Transactional
	@Scheduled(fixedDelay = 2000l*60)
	public void deleteprograms() {
		classHourServiceIMPL.deletePrograms();
	}
	
	@Transactional
	@Scheduled(fixedDelay = 2000l*60)
	public void deleteSchool() {
		schoolServiceIMPL.deleteSchoolIfDeleted();
	}
	
}
