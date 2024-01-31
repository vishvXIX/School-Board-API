package com.school.SBA.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.school.SBA.Entity.Subject;
import com.school.SBA.enums.ProgramType;

import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AcademicProgramResponse {

	private int programId;
	private ProgramType programType;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	private boolean isDeleted;
	
	@ManyToMany
	private List<Subject> listSubjects;
	
}

