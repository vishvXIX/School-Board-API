package com.school.SBA.ServiceIMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.AcademicProgram;
import com.school.SBA.Entity.School;
import com.school.SBA.Entity.User;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Exception.UserNotFoundByIdException;
import com.school.SBA.Repository.AcademicProgramRepository;
import com.school.SBA.Repository.ClassHourRepository;
import com.school.SBA.Repository.SchoolRepository;
import com.school.SBA.Repository.UserRepository;
import com.school.SBA.RequestDTO.SchoolRequest;
import com.school.SBA.ResponseDTO.SchoolResponse;
import com.school.SBA.Service.SchoolService;
import com.school.SBA.Utility.ResponseStructure;
import com.school.SBA.enums.UserRole;

import jakarta.transaction.Transactional;

@Service
public class SchoolServiceIMPL implements SchoolService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SchoolRepository schoolRepository;
	
	@Autowired
	private ClassHourRepository classHourRepository;
	
	@Autowired
	private ResponseStructure<SchoolResponse> structure;
	
	@Autowired
	private AcademicProgramRepository academicProgramRepository;

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
					structure.setData(mapToSchoolResponse(school,false));
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
					structure.setData(mapToSchoolResponse(school,false));
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

	private SchoolResponse mapToSchoolResponse (School school, boolean isDeleted) {
		
		SchoolResponse response = new SchoolResponse ();
		response.setSchoolName(school.getSchoolName());
		response.setSchoolEmail(school.getSchoolEmail());
		response.setSchoolContactNo(school.getSchoolContactNo());
		response.setSchoolAddress(school.getSchoolAddress());
		response.setDeleted(school.isDeleted());
		
		return response;
		
		
	}

	
	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(int schoolId) {

		return schoolRepository.findById(schoolId)
				.map(school -> {
					school.setDeleted(true);
					//	repository.deleteById(schoolId);
					schoolRepository.save(school);
					structure.setStatus(HttpStatus.OK.value());
					structure.setMessage("School deleted successfully");
					structure.setData(mapToSchoolResponse(school,true));

					return new ResponseEntity<>(structure, HttpStatus.OK);
				})
				.orElseThrow(() -> new IllagalRequestException("School not found by id"));
	}
	
	
//	@Transactional
//	public void deleteSchoolIfDeleted() {
//		 List<School> schools = schoolRepository.findByIsDeleted(true);
//		 schools.forEach(school->{
//			 
//			academicProgramRepository.deleteAll(school.getLAcademicProgram());
//			List<User> users = userRepository.findBySchool(school);
//			users.forEach(user->{
//				if(user.getUserRole().equals(UserRole.ADMIN)) {
//				user.setSchool(null);
//				userRepository.save(user);
//				}else {
//					userRepository.delete(user);
//				}
//			});
//			schoolRepository.delete(school);
//		});
//	}	
	
	@Transactional
	public void deleteSchoolIfDeleted() {
	    List<School> schools = schoolRepository.findByIsDeleted(true);
	    schools.forEach(school -> {
	        List<AcademicProgram> academicPrograms = school.getLAcademicProgram();

	        // Delete associated classHour 
	        for (AcademicProgram academicProgram : academicPrograms) {
	            classHourRepository.deleteByAcademicProgramProgramId(academicProgram.getProgramId());
	        }

	        // delete the academic_programs
	        academicProgramRepository.deleteAll(academicPrograms);

	        List<User> users = userRepository.findBySchool(school);
			users.forEach(user->{
				if(user.getUserRole().equals(UserRole.ADMIN)) {
				user.setSchool(null);
				userRepository.save(user);
				}else {
					userRepository.delete(user);
				}
			});
			schoolRepository.delete(school);
			System.err.println("School is Deleted Successfully !!");
	    });
	}

}
