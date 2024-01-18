package com.school.SBA.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.SBA.RequestDTO.AcademicProgramRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.Service.AcademicProgramService;
import com.school.SBA.Utility.ResponseStructure;

@RestController
public class AcademicProgramController {

	@Autowired
	private AcademicProgramService service;
	
	@PostMapping("/schools/{schoolId}/lAcademicProgram")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(@PathVariable int schoolId, AcademicProgramRequest academicProgramRequest){
		return service.saveAcademicProgram(schoolId, academicProgramRequest);
	}
	
}
