package com.school.SBA.ServiceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.School;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Exception.UserNotFoundByIdException;
import com.school.SBA.Repository.SchoolRepository;
import com.school.SBA.Repository.UserRepository;
import com.school.SBA.RequestDTO.SchoolRequest;
import com.school.SBA.ResponseDTO.SchoolResponse;
import com.school.SBA.Service.SchoolService;
import com.school.SBA.Utility.ResponseStructure;
import com.school.SBA.enums.UserRole;

@Service
public class SchoolServiceIMPL implements SchoolService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SchoolRepository schoolRepository;
	
	@Autowired
	private ResponseStructure<SchoolResponse> structure;

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(SchoolRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRepository.findByuserName(username).map(u -> {
			if (u.getUserRole().equals(UserRole.ADMIN)) {
				if(u.getSchool()== null) {
					School school = mapToSchool(request);
					school = schoolRepository.save(school);
					u.setSchool(school);
					userRepository.save(u);
					structure.setStatus(HttpStatus.CREATED.value());
					structure.setMessage("School saved Successfully");
					structure.setData(mapToSchoolResponse(school));
					return new ResponseEntity<ResponseStructure<SchoolResponse>> (structure, HttpStatus.CREATED);
					
				}else
					throw new IllagalRequestException("School object is already present");
			}else
				throw new IllagalRequestException("Only ADMIN can create School");
			
		}).orElseThrow(()->new UserNotFoundByIdException("Failed to save School"));
		
	}
	
	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchool(int schoolId) {
		return schoolRepository.findById(schoolId)
				.map(school->{
					structure.setStatus(HttpStatus.FOUND.value());
					structure.setMessage("school fatch susscessfully");
					structure.setData(mapToSchoolResponse(school));
					return new ResponseEntity<>(structure,HttpStatus.FOUND);
				})
				.orElseThrow(()-> new UserNotFoundByIdException("School not found by id"));
	}
	
	private School mapToSchool(SchoolRequest request) {

		School school = new School();
		school.setSchoolName(request.getSchoolName());
		school.setSchoolEmail(request.getSchoolEmail());
		school.setSchoolContactNo(request.getSchoolContactNo());
		school.setSchoolAddress(request.getSchoolAddress());
		
		return school;
		
	}

	private SchoolResponse mapToSchoolResponse (School school) {
		
		SchoolResponse response = new SchoolResponse ();
		response.setSchoolName(school.getSchoolName());
		response.setSchoolEmail(school.getSchoolEmail());
		response.setSchoolContactNo(school.getSchoolContactNo());
		response.setSchoolAddress(school.getSchoolAddress());
		
		return response;
		
		
	}

	
	
}
