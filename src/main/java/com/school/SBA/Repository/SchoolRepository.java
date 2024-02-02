package com.school.SBA.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.SBA.Entity.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer>{

	public List<School> findByIsDeleted(boolean isDeleted);

}
