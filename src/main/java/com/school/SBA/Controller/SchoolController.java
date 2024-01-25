package com.school.SBA.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.SBA.RequestDTO.SchoolRequest;
import com.school.SBA.ResponseDTO.SchoolResponse;
import com.school.SBA.Service.SchoolService;
import com.school.SBA.Utility.ResponseStructure;

@RestController
public class SchoolController {
	
	@Autowired
	private SchoolService service;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/schools")
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(@RequestBody SchoolRequest request) {
		return service.saveSchool(request);
	}
	
	@GetMapping("/users/{schoolId}/schools")
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchool(@PathVariable int schoolId){
		return service.findSchool(schoolId);
	}
	
	
	
}
