package com.school.SBA.Service;

import org.springframework.http.ResponseEntity;

import com.school.SBA.RequestDTO.AcademicProgramRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.Utility.ResponseStructure;

public interface AcademicProgramService {

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(int schoolId,
			AcademicProgramRequest academicProgramRequest);

}
