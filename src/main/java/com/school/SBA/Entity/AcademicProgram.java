package com.school.SBA.Entity;

import java.time.LocalDate;
import java.util.List;

import com.school.SBA.enums.ProgramType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity
public class AcademicProgram {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int programId;
	private ProgramType programType;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	private boolean isDeleted;
	
	@ManyToMany
	private List<Subject> listSubjects;
	
	@ManyToOne
	private School school;
	
	@ManyToMany
	private List<User> listUsers;

	@ManyToMany
	private List<Subject> subject;
	
	@OneToMany(mappedBy = "academicProgram")
	private List<ClassHour> listClassHours;
	
	
}
