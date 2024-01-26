package com.school.SBA.Entity;

import java.util.List;

import com.school.SBA.enums.UserRole;

import jakarta.persistence.Column;
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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(unique = true)
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String contactNo;
	@Column(unique = true)
	private String email;
	private UserRole userRole;
	private boolean isDeleted;

	@ManyToOne
	private School school;

	@ManyToMany(mappedBy = "listUsers")
	private List<AcademicProgram> listAcademicPrograms;

	@ManyToOne
	private Subject subject;
	
	@OneToMany(mappedBy = "user")
	private List<ClassHour> listClassHours;

}
