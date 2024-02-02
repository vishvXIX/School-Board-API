package com.school.SBA.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.SBA.Entity.AcademicProgram;
import com.school.SBA.Entity.ClassHour;

@Repository
public interface ClassHourRepository extends JpaRepository<ClassHour, Integer> {

	void deleteByAcademicProgramProgramId(int programId);

	List<ClassHour> findByAcademicProgramAndBeginsAtBetween(AcademicProgram academicProgram, LocalDateTime atStartOfDay,
			LocalDateTime atStartOfDay2);


	Optional<ClassHour> findTopByOrderByClassHourIdDesc();



}
