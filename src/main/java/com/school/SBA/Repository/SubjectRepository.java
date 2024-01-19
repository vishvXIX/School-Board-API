package com.school.SBA.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.SBA.Entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{

	public Optional<Subject> findBySubjectName(String subjectNames);

}
