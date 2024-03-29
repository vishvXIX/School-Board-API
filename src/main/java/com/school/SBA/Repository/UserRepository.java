package com.school.SBA.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.SBA.Entity.AcademicProgram;
import com.school.SBA.Entity.School;
import com.school.SBA.Entity.User;
import com.school.SBA.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByuserName(String username);

	Optional<User> findUserByUserRole(UserRole admin);

	List<User> findByUserRoleAndListAcademicPrograms(UserRole valueOf, AcademicProgram program);

	List<User> findByIsDeleted(boolean isDeleted);


	List<User> findByIsDeletedAndUserRoleNotIn(boolean isDelete, List<UserRole> rolesToExclude);

	List<User> findBySchool(School school);



}
