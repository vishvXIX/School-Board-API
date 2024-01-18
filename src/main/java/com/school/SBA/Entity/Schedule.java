package com.school.SBA.Entity;

import java.time.Duration;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ScheduleId;
	private LocalTime openAt;
	private LocalTime closeAt;
	private int classHourPerDay;
	private Duration classHourLengthInMinutes;
	private LocalTime breakTime;
	private Duration breakLengthInMinutes; 
	private LocalTime lunchTime;
	private Duration lunchLengthInMinutes;

	public Schedule() {
		super();
	}

	public Schedule(int scheduleId, LocalTime openAt, LocalTime closeAt, int classHourPerDay, Duration classHourLengthInMinutes,
			LocalTime breakTime, Duration breakLengthInMinutes, LocalTime lunchTime, Duration lunchLengthInMinutes) {
		super();
		ScheduleId = scheduleId;
		this.openAt = openAt;
		this.closeAt = closeAt;
		this.classHourPerDay = classHourPerDay;
		this.classHourLengthInMinutes = classHourLengthInMinutes;
		this.breakTime = breakTime;
		this.breakLengthInMinutes = breakLengthInMinutes;
		this.lunchTime = lunchTime;
		this.lunchLengthInMinutes = lunchLengthInMinutes;
	}

	public int getScheduleId() {
		return ScheduleId;
	}

	public void setScheduleId(int scheduleId) {
		ScheduleId = scheduleId;
	}

	public LocalTime getOpenAt() {
		return openAt;
	}

	public void setOpenAt(LocalTime openAt) {
		this.openAt = openAt;
	}

	public LocalTime getCloseAt() {
		return closeAt;
	}

	public void setCloseAt(LocalTime closeAt) {
		this.closeAt = closeAt;
	}

	public int getClassHourPerDay() {
		return classHourPerDay;
	}

	public void setClassHourPerDay(int classHourPerDay) {
		this.classHourPerDay = classHourPerDay;
	}

//	public Duration getClassHourLengthInMinutes() {
//		return classHourLengthInMinutes;
//	}

	public void setClassHourLengthInMinutes(Duration classHourLengthInMinutes) {
		this.classHourLengthInMinutes = classHourLengthInMinutes;
	}

	public LocalTime getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(LocalTime breakTime) {
		this.breakTime = breakTime;
	}

//	public Duration getBreakLengthInMinutes() {
//		return breakLengthInMinutes;
//	}

	public void setBreakLengthInMinutes(Duration breakLengthInMinutes) {
		this.breakLengthInMinutes = breakLengthInMinutes;
	}

	public LocalTime getLunchTime() {
		return lunchTime;
	}

	public void setLunchTime(LocalTime lunchTime) {
		this.lunchTime = lunchTime;
	}

//	public Duration getLunchLengthInMinutes() {
//		return lunchLengthInMinutes;
//	}

	public void setLunchLengthInMinutes(Duration lunchLengthInMinutes) {
		this.lunchLengthInMinutes = lunchLengthInMinutes;
	}


	public int getClassHourLengthInMinutes() {
        return (int) classHourLengthInMinutes.toMinutes();
    }

    public int getBreakLengthInMinutes() {
        return (int) breakLengthInMinutes.toMinutes();
    }

    public int getLunchLengthInMinutes() {
        return (int) lunchLengthInMinutes.toMinutes();
    }












}
