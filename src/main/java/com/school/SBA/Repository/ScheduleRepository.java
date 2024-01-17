package com.school.SBA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.SBA.Entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}
