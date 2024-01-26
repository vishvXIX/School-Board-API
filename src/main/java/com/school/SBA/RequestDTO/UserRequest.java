package com.school.SBA.RequestDTO;

import com.school.SBA.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
public class UserRequest {
	
	@NotEmpty
	private String username;
//	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
//	@Pattern(regexp = "^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
//			+ " contain at least one letter, one number, one special character")
	private String password;
	private String firstName;
	private String lastName;
	private String contactNo;
	private boolean isDelete;
	@NotEmpty(message = "Email cannot be blank/empty")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String email;
	private UserRole userRole;
	
}
