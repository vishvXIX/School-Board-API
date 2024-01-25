package com.school.SBA.Service;

import org.springframework.http.ResponseEntity;

import com.school.SBA.ResponseDTO.ClassHourResponse;
import com.school.SBA.Utility.ResponseStructure;

public interface ClassHourService {

	ResponseEntity<ResponseStructure<ClassHourResponse>> saveClassHour(int programId);

}
