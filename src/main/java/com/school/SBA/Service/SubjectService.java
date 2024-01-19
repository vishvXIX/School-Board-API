package com.school.SBA.Service;

import org.springframework.http.ResponseEntity;

import com.school.SBA.RequestDTO.SubjectRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.Utility.ResponseStructure;

public interface SubjectService {

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(int programId, SubjectRequest request);

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSujects(int programId);

	
	
}
