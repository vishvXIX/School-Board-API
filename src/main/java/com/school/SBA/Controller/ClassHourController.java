package com.school.SBA.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.SBA.ResponseDTO.ClassHourResponse;
import com.school.SBA.Service.ClassHourService;
import com.school.SBA.Utility.ResponseStructure;

@RestController
public class ClassHourController {

	@Autowired
	private ClassHourService service;
	
	@PostMapping("/academic-program/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<ClassHourResponse>> saveClassHour(@PathVariable int programId) {
		return service.saveClassHour(programId);
	}
	
}
