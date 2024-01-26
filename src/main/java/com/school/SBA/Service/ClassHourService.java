package com.school.SBA.Service;

import org.springframework.http.ResponseEntity;

import com.school.SBA.Utility.ResponseStructure;

public interface ClassHourService {

	ResponseEntity<ResponseStructure<String>> generateClassHourForAcademicProgram(int programId);

}
