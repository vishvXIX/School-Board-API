package com.school.SBA.ServiceIMPL;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.Subject;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Repository.AcademicProgramRepository;
import com.school.SBA.Repository.SubjectRepository;
import com.school.SBA.RequestDTO.SubjectRequest;
import com.school.SBA.ResponseDTO.AcademicProgramResponse;
import com.school.SBA.Service.SubjectService;
import com.school.SBA.Utility.ResponseStructure;

@Service
public class SubjectServiceIMPL implements SubjectService {

	@Autowired
	private SubjectRepository repository;

	@Autowired
	private ResponseStructure<AcademicProgramResponse> structure;

	@Autowired
	private AcademicProgramServiceIMPL academicProgramServiceIMPL;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(int programId, SubjectRequest subjectRequest) {
		return academicProgramRepository.findById(programId)
				.map(program -> {
					List<Subject> subjects = new ArrayList<>();

					subjectRequest.getListString().forEach(name -> {

						Subject subject = repository.findBySubjectName(name)
								.map(existingSubject -> existingSubject)
								.orElseGet(() -> {
									Subject subject2 = new Subject();
									subject2.setSubjects(name);
									repository.save(subject2);
									
									return subject2;
								});

						subjects.add(subject);
					});

				    program.setListSubjects(subjects);
					academicProgramRepository.save(program);

					structure.setStatus(HttpStatus.CREATED.value());
					structure.setMessage("Updated the subject list to Academic Program");
					structure.setData(academicProgramServiceIMPL.mapToAcademicProgramResponse(program));
					return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
				})
				.orElseThrow(() -> new IllagalRequestException("Academic Program not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSujects(int programId) {
		// TODO Auto-generated method stub
		return null;
	}

}
