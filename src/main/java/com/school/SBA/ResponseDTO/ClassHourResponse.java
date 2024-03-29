package com.school.SBA.ResponseDTO;

import java.time.LocalDateTime;

import com.school.SBA.enums.ClassStatus;

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
public class ClassHourResponse {

	private LocalDateTime beginsAt;
	private LocalDateTime endsAt;
	private int roomNo;
	private ClassStatus classStatus;
	
	private int userId;
    private String username;
    private String email;
}
