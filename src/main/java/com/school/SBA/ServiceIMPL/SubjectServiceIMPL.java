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

//	@Override
//	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(int programId, SubjectRequest subjectRequest) {
//		return academicProgramRepository.findById(programId)
//				.map(program -> {
//					List<Subject> subjects = (program.getListSubjects() != null) ? program.getListSubjects(): new ArrayList<Subject>();
//
//					// add new subjects
//					subjectRequest.getListString().forEach(subjectName -> {
//						boolean isPresent = false; 
//						for (Subject subject:subjects) {
//							isPresent = (subjectName.equalsIgnoreCase(subject.getSubjectName())) ? true:false;
//							if (isPresent)break;
//						}
//						if (isPresent)subjects.add(repository.findBySubjectName(subjectName)
//								.orElseGet(()-> {
//									Subject subject = new Subject();
//									subject.setSubjectName(subjectName);
//									return repository.save(subject);
//								}));
//					});
//					
//					// remove irrelevent subjects
//					List<Subject> toBeRemoved = new ArrayList<Subject>();
//					subjects.forEach(subject -> {
//						boolean isPresent = false;
//						for (String name : subjectRequest.getListString()) {
//							isPresent = (subject.getSubjectName().equalsIgnoreCase(name))? true:false;
//							if(!isPresent)break;
//						}
//						if (!isPresent)toBeRemoved.add(subject);
//					});
//					subjects.removeAll(toBeRemoved);
//					
//					program.setListSubjects(subjects);
//					academicProgramRepository.save(program);
//
//					structure.setStatus(HttpStatus.CREATED.value());
//					structure.setMessage("Updated the subject list to Academic Program");
//					structure.setData(academicProgramServiceIMPL.mapToAcademicProgramResponse(program));
//					return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
//					
//				}).orElseThrow(() -> new IllagalRequestException("Academic Program not found"));
//
//	}
	
	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(int programId, SubjectRequest subjectRequest) {
	    return academicProgramRepository.findById(programId)
	            .map(program -> {
	                List<Subject> subjects = (program.getListSubjects() != null) ? program.getListSubjects() : new ArrayList<>();

	                // Add new subjects specified by the client
	                subjectRequest.getsubjectName().forEach(name -> {
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
	                    boolean isPresent = subjectRequest.getsubjectName().stream()
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
	
}
