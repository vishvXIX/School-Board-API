package com.school.SBA.Service;

import org.springframework.http.ResponseEntity;

import com.school.SBA.RequestDTO.SchoolRequest;
import com.school.SBA.ResponseDTO.SchoolResponse;
import com.school.SBA.Utility.ResponseStructure;

public interface SchoolService {

	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(int userId, SchoolRequest request);

}
