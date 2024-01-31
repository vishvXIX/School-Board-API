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
import com.school.SBA.Entity.Subject;
import com.school.SBA.Entity.User;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Exception.UserNotFoundByIdException;
import com.school.SBA.Repository.AcademicProgramRepository;
import com.school.SBA.Repository.SchoolRepository;
import com.school.SBA.Repository.UserRepository;
import com.school.SBA.RequestDTO.AcademicProgramRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.Service.AcademicProgramService;
import com.school.SBA.Utility.ResponseStructure;
import com.school.SBA.enums.UserRole;

@Service
public class AcademicProgramServiceIMPL implements AcademicProgramService {

	@Autowired
	private AcademicProgramRepository repository;

	@Autowired
	private SchoolRepository schoolRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResponseStructure<AcademicProgramResponse> structure;

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(int schoolId,
			AcademicProgramRequest academicprogramrequest) {
		User user = new User();
		return schoolRepository.findById(schoolId).map(s -> {
			AcademicProgram academicProgram = mapToAcademicProgram(academicprogramrequest);
			academicProgram.setSchool(s); // Set the school for the program
			if (user.getUserRole() == UserRole.ADMIN) {
				throw new IllegalArgumentException("ADMIN user cannot be associated with any Academic Program.");
			}

			// Validate teacher's subject before adding to the academic program
			if (user.getUserRole() == UserRole.TEACHER && !isValidTeacherSubject(user.getSubject(),academicProgram)) {
				throw new IllegalArgumentException("The teacher has an irrelevant subject to the academic program.");
			}

			academicProgram = repository.save(academicProgram);
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setMessage("Academic Program object created Successfully");
			structure.setData(mapToAcademicProgramResponse(academicProgram,false));
			return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
		}).orElseThrow(() -> new IllagalRequestException("School not found"));
	}


	private boolean isValidTeacherSubject(Subject teacherSubject, AcademicProgram academicProgram) {
		List<Subject> relevantSubjects = academicProgram.getListSubjects();

		return relevantSubjects.contains(teacherSubject);
	}

	@Override
	public List<AcademicProgramResponse> findallAcademicPrograms(int schoolId) {
		Optional<School> optionalSchool = schoolRepository.findById(schoolId);
		if (optionalSchool.isPresent()) {
			School school = optionalSchool.get();
			List<AcademicProgram> academicPrograms = school.getLAcademicProgram();
			List<AcademicProgramResponse> responses = new ArrayList<>();
			for (AcademicProgram academicProgram : academicPrograms) {
				responses.add(mapToAcademicProgramResponse(academicProgram,false));
			}
			return responses;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> assignUserToAcademicProgramm(int programId,
			int userId) {

		AcademicProgram academicProgram = repository.findById(programId)
				.orElseThrow(() -> new IllagalRequestException("Academic Program not found"));

		// Validate the user
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundByIdException("User not found"));

		// Check if the user is an ADMIN
		if (user.getUserRole() == UserRole.ADMIN) {
			throw new IllagalRequestException("Admin cannot be associated with any Academic Program");
		}
		if (user.getUserRole() != UserRole.TEACHER && user.getUserRole() != UserRole.STUDENT) {
			throw new IllagalRequestException("User must have role TEACHER or STUDENT.");
		}

		// Add the user to the academic program
		if (!academicProgram.getListUsers().contains(user)) {
			academicProgram.getListUsers().add(user);
			repository.save(academicProgram);

			// Return the response
			//			ResponseStructure<AcademicProgramResponse> structure = new ResponseStructure<>();
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setMessage("User assigned to Academic Program successfully");
			structure.setData(mapToAcademicProgramResponse(academicProgram,false));
			return ResponseEntity.ok(structure);
		} else {
			throw new IllagalRequestException("User is already associated with the academic program");
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

	public AcademicProgramResponse mapToAcademicProgramResponse (AcademicProgram academicProgram, boolean isDeleted) {
		AcademicProgramResponse academicProgramResponse = new AcademicProgramResponse();
		academicProgramResponse.setProgramName(academicProgram.getProgramName());
		academicProgramResponse.setProgramType(academicProgram.getProgramType());
		academicProgramResponse.setBeginsAt (academicProgram.getBeginsAt());
		academicProgramResponse.setEndsAt(academicProgram.getEndsAt());
		academicProgramResponse.setListSubjects(academicProgram.getListSubjects());
		academicProgramResponse.setDeleted(academicProgram.isDeleted());

		return academicProgramResponse;
	}


	@Override
	public ResponseEntity<ResponseStructure<List<User>>> fetchUsersByRoleInAcademicProgram (int programId,String role) {

		List<User> users = repository.findById(programId).map(program -> userRepository.findByUserRoleAndListAcademicPrograms(UserRole.valueOf(role.toUpperCase()), program))
		.orElseThrow();
		
		ResponseStructure<List<User>> responseStructure = new ResponseStructure<List<User>>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("Fetched successfully!!!");
		responseStructure.setData (users);

		return new ResponseEntity<ResponseStructure<List<User>>> (responseStructure, HttpStatus.OK);

	}


	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> deleteById(int academicProgramId) {

		return repository.findById(academicProgramId)
				.map(academicProgram -> {
					academicProgram.setDeleted(true);
					//					repository.deleteById(academicProgramId);
					repository.save(academicProgram);
					structure.setStatus(HttpStatus.OK.value());
					structure.setMessage("Academic Program deleted successfully");
					structure.setData(mapToAcademicProgramResponse(academicProgram,true));

					return new ResponseEntity<>(structure, HttpStatus.OK);
				})
				.orElseThrow(() -> new IllagalRequestException("Academic Program not found by id"));
	}


}



