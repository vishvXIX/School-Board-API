package com.school.SBA.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.SBA.RequestDTO.SubjectRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.Service.SubjectService;
import com.school.SBA.Utility.ResponseStructure;

@RestController
public class SubjectController {

	@Autowired
	private SubjectService service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/academic-programs/{programId}/subjects")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(@PathVariable int programId, @RequestBody SubjectRequest request){
		return service.saveSubject(programId,request);
	}
	
}
