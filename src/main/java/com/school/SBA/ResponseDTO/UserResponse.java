package com.school.SBA.ResponseDTO;

import com.school.SBA.enums.UserRole;

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
public class UserResponse {
	

	private int userId;
	private String username;
	private String firstName;
	private String lastName;
	private String contactNo;
	private String email;
	private UserRole userRole;
	private boolean isDeleted;
	

}
