package com.school.SBA.ResponseDTO;

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
public class SchoolResponse {

	private int schoolId;
	private String schoolName;
	private String schoolContactNo;
	private String schoolEmail;
	private String schoolAddress;
	
}
