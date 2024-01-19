package com.school.SBA.ServiceIMPL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.AcademicProgram;
import com.school.SBA.Entity.School;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Repository.AcademicProgramRepository;
import com.school.SBA.Repository.SchoolRepository;
import com.school.SBA.RequestDTO.AcademicProgramRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.Service.AcademicProgramService;
import com.school.SBA.Utility.ResponseStructure;

@Service
public class AcademicProgramServiceIMPL implements AcademicProgramService {

	@Autowired
	private AcademicProgramRepository repository;

	@Autowired
	private SchoolRepository schoolRepository;

	@Autowired
	private ResponseStructure<AcademicProgramResponse> structure;

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(int schoolId,
	        AcademicProgramRequest academicprogramrequest) {
	    return schoolRepository.findById(schoolId).map(s -> {
	        AcademicProgram academicProgram = mapToAcademicProgram(academicprogramrequest);
	        academicProgram.setSchool(s); // Set the school for the program
	        academicProgram = repository.save(academicProgram);
	        structure.setStatus(HttpStatus.CREATED.value());
	        structure.setMessage("Academic Program object created Successfully");
	        structure.setData(mapToAcademicProgramResponse(academicProgram));
	        return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
	    }).orElseThrow(() -> new IllagalRequestException("School not found"));
	}

	@Override
	public List<AcademicProgramResponse> findallAcademicPrograms(int schoolId) {
	    Optional<School> optionalSchool = schoolRepository.findById(schoolId);
	    if (optionalSchool.isPresent()) {
	        School school = optionalSchool.get();
	        List<AcademicProgram> academicPrograms = school.getAcademicProgram();
	        List<AcademicProgramResponse> responses = new ArrayList<>();
	        for (AcademicProgram academicProgram : academicPrograms) {
	            responses.add(mapToAcademicProgramResponse(academicProgram));
	        }
	        return responses;
	    } else {
	        return Collections.emptyList();
	    }
	}

	public AcademicProgram mapToAcademicProgram(AcademicProgramRequest request) {

		AcademicProgram academicProgram = new AcademicProgram();
		academicProgram.setProgramName(request.getProgramName());
		academicProgram.setProgramType(request.getProgramType());
		academicProgram.setBeginsAt(request.getBeginsAt());
		academicProgram.setEndsAt(request.getEndsAt());

		return academicProgram;
	}

	public AcademicProgramResponse mapToAcademicProgramResponse (AcademicProgram academicProgram) {
//		Subject subject = new Subject();
//		subject.setSubjects(academicProgram.getListSubjects());
		AcademicProgramResponse academicProgramResponse = new AcademicProgramResponse();
		academicProgramResponse.setProgramName(academicProgram.getProgramName());
		academicProgramResponse.setProgramType(academicProgram.getProgramType());
		academicProgramResponse.setBeginsAt (academicProgram.getBeginsAt());
		academicProgramResponse.setEndsAt(academicProgram.getEndsAt());
//		academicProgramResponse.setListSubject(academicProgram.getListSubjects());

		return academicProgramResponse;
	}

}
