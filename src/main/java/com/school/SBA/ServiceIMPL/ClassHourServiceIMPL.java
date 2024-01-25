package com.school.SBA.ServiceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Repository.ClassHourRepository;
import com.school.SBA.ResponseDTO.ClassHourResponse;
import com.school.SBA.Service.ClassHourService;
import com.school.SBA.Utility.ResponseStructure;

@Service
public class ClassHourServiceIMPL implements ClassHourService {

	@Autowired
	private ClassHourRepository repository;

	@Override
	public ResponseEntity<ResponseStructure<ClassHourResponse>> saveClassHour(int programId) {
		
		
		
		return null;
	}
	
}
