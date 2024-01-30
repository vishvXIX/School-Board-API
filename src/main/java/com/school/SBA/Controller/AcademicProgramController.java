package com.school.SBA.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.SBA.Entity.User;
import com.school.SBA.RequestDTO.AcademicProgramRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.Service.AcademicProgramService;
import com.school.SBA.Utility.ResponseStructure;

@RestController
public class AcademicProgramController {

	@Autowired
	private AcademicProgramService service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/schools/{schoolId}/academicprogram")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(@PathVariable int schoolId,@RequestBody AcademicProgramRequest academicProgramRequest){
		return service.saveAcademicProgram(schoolId, academicProgramRequest);
	}
	
	@GetMapping("/schools/{schoolId}/academicprogram")
	public List<AcademicProgramResponse> findallAcademicPrograms(@PathVariable int schoolId) {
		return service.findallAcademicPrograms(schoolId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/users/{userId}/academic-programs/{programId}")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> assignUserToAcademicProgramm(@PathVariable int programId, @PathVariable int userId ) {
		return service.assignUserToAcademicProgramm(programId,userId);
	}
	
	@GetMapping("/academic-programs/{programId}/user-roles/{role}/users")
	public ResponseEntity<ResponseStructure<List<User>>> fetchUsersByRoleInAcademicProgram(@PathVariable int programId,@PathVariable String role){
		return service.fetchUsersByRoleInAcademicProgram(programId, role);
	}
	
}
