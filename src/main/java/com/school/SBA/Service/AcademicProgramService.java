package com.school.SBA.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.SBA.Entity.User;
import com.school.SBA.RequestDTO.AcademicProgramRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.Utility.ResponseStructure;

public interface AcademicProgramService {

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(int schoolId,
			AcademicProgramRequest academicProgramRequest);

	public List<AcademicProgramResponse> findallAcademicPrograms(int schoolId);

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> assignUserToAcademicProgramm(int programId,
			int userId);

	ResponseEntity<ResponseStructure<List<User>>> fetchUsersByRoleInAcademicProgram(int programId, String role);

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> deleteById(int academicProgramId);

}
