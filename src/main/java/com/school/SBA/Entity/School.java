package com.school.SBA.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class School {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int schoolId;
	private String schoolName;
	private String schoolContactNo;
	private String schoolEmail;
	private String schoolAddress;
	private boolean isDeleted;
	
	@OneToOne
	private Schedule schedule;
	
	@OneToMany(mappedBy = "school")
	private List<AcademicProgram> lAcademicProgram;

	
}
