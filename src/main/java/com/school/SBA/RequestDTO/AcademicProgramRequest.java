package com.school.SBA.RequestDTO;

import java.time.LocalDate;

import com.school.SBA.enums.ProgramType;

public class AcademicProgramRequest {

	private ProgramType programType;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	
	
	
	public AcademicProgramRequest() {
		super();
	}

	public AcademicProgramRequest(ProgramType programType, String programName, LocalDate beginsAt, LocalDate endsAt) {
		super();
		this.programType = programType;
		this.programName = programName;
		this.beginsAt = beginsAt;
		this.endsAt = endsAt;
	}

	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public LocalDate getBeginsAt() {
		return beginsAt;
	}

	public void setBeginsAt(LocalDate beginsAt) {
		this.beginsAt = beginsAt;
	}

	public LocalDate getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(LocalDate endsAt) {
		this.endsAt = endsAt;
	}
	
	
	
}
