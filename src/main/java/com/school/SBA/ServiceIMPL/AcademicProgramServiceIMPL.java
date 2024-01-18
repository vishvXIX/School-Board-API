package com.school.SBA.ServiceIMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.AcademicProgram;
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
			AcademicProgramRequest academicProgramRequest) {
		
		return schoolRepository.findById(schoolId).map(a -> {
			{
				List<AcademicProgram> academicProgram = mapToAcademicProgram(academicProgramRequest);
				academicProgram = repository.saveAll(academicProgram);
				a.setAcademicProgram(academicProgram);
				schoolRepository.save(a);
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setMessage("AcademicProgram object created Sucessfully");
				structure.setData(mapToAcademicProgramResponse(academicProgram));
				
				return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure,HttpStatus.CREATED);
			}
		})
				.orElseThrow(()-> new IllagalRequestException(""));
	}
	
//	@SuppressWarnings("unchecked")
	public List<AcademicProgram> mapToAcademicProgram(AcademicProgramRequest request) {
		
		AcademicProgram academicProgram = new AcademicProgram();
		academicProgram.setProgramName(request.getProgramName());
		academicProgram.setProgramType(request.getProgramType());
		academicProgram.setBeginsAt(request.getBeginsAt());
		academicProgram.setEndsAt(request.getEndsAt());
		
		return (List<AcademicProgram>) academicProgram;
	}
	
	public AcademicProgramResponse mapToAcademicProgramResponse (List<AcademicProgram> academicProgram) {
		AcademicProgramResponse academicProgramResponse = new AcademicProgramResponse();
		academicProgramResponse.setProgramName(((AcademicProgram) academicProgram).getProgramName());
		academicProgramResponse.setProgramType(((AcademicProgram) academicProgram).getProgramType());
		academicProgramResponse.setBeginsAt(((AcademicProgram) academicProgram).getBeginsAt());
		academicProgramResponse.setEndsAt(((AcademicProgram) academicProgram).getEndsAt());
		
		return academicProgramResponse;
	}
	
}
