package com.school.SBA.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.SBA.RequestDTO.ClassHourDTOs;
import com.school.SBA.ResponseDTO.ClassHourResponse;
import com.school.SBA.Utility.ResponseStructure;

public interface ClassHourService {

	ResponseEntity<ResponseStructure<String>> generateClassHourForAcademicProgram(int programId);

	ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(
			List<ClassHourDTOs> classHourDtoList);


}
