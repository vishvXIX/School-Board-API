package com.school.SBA.ServiceIMPL;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.Subject;
import com.school.SBA.Entity.User;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Repository.AcademicProgramRepository;
import com.school.SBA.Repository.SubjectRepository;
import com.school.SBA.Repository.UserRepository;
import com.school.SBA.RequestDTO.SubjectRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.ResponseDTO.UserResponse;
import com.school.SBA.Service.SubjectService;
import com.school.SBA.Utility.ResponseStructure;
import com.school.SBA.enums.UserRole;

@Service
public class SubjectServiceIMPL implements SubjectService {

	@Autowired
	private SubjectRepository repository;

	@Autowired
	private ResponseStructure<AcademicProgramResponse> structure;
	
	

	@Autowired
	private AcademicProgramServiceIMPL academicProgramServiceIMPL;

	@Autowired
	private UserServiceIMPL userServiceIMPL;
	
	@Autowired
	private AcademicProgramRepository academicProgramRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(int programId, SubjectRequest subjectRequest) {
	    return academicProgramRepository.findById(programId)
	            .map(program -> {
	                List<Subject> subjects = (program.getListSubjects() != null) ? program.getListSubjects() : new ArrayList<>();

	                // Add new subjects specified by the client
	                subjectRequest.getSubjectName().forEach(name -> {
	                    boolean isPresent = subjects.stream().anyMatch(subject -> name.equalsIgnoreCase(subject.getSubjectName()));
	                    if (!isPresent) {
	                        subjects.add(repository.findBySubjectName(name)
	                                .orElseGet(() -> {
	                                	Subject subject = new Subject();
										subject.setSubjectName(name);
										return repository.save(subject);
									}));
	                    }
	                });

	                // Remove subjects that are not specified by the client
	                List<Subject> toBeRemoved = new ArrayList<>();
	                subjects.forEach(subject -> {
	                    boolean isPresent = subjectRequest.getSubjectName().stream()
	                            .anyMatch(name -> subject.getSubjectName().equalsIgnoreCase(name));
	                    if (!isPresent) {
	                        toBeRemoved.add(subject);
	                    }
	                });
	                subjects.removeAll(toBeRemoved);

	                program.setListSubjects(subjects);
	                academicProgramRepository.save(program);

	                structure.setStatus(HttpStatus.CREATED.value());
	                structure.setMessage("Created the subject list for the Academic Program");
	                structure.setData(academicProgramServiceIMPL.mapToAcademicProgramResponse(program));
	                return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
	            })
	            .orElseThrow(() -> new IllagalRequestException("Academic Program not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> assignSujectToTeacher(int subjectId, int userId) {
		Subject subject = repository.findById(subjectId)
				.orElseThrow(() -> new IllagalRequestException("Subject not found with ID"));

		// Validate if the user exists
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllagalRequestException("User not found with ID"));

		// Check if the user has the role "ADMIN" or "STUDENT"
		if (user.getUserRole() == UserRole.ADMIN || user.getUserRole() == UserRole.STUDENT) {
			throw new IllagalRequestException("Cannot assign subject to ADMIN or STUDENT");
		}

		user.setSubject(subject);

		userRepository.save(user);
		ResponseStructure<UserResponse> structure = new ResponseStructure<UserResponse>();
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Subject is assigned to the user");
		structure.setData(userServiceIMPL.mapToUserResponse(user,false));
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.OK);
	}
}
