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
	private Duration classHourLength;
	private LocalTime breakTime;
	private Duration breakLength; 
	private LocalTime lunchTime;
	private Duration lunchLength;
	
	public Schedule() {
		super();
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

	public Duration getClassHourLength() {
		return classHourLength;
	}

	public void setClassHourLength(Duration classHourLength) {
		this.classHourLength = classHourLength;
	}

	public LocalTime getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(LocalTime breakTime) {
		this.breakTime = breakTime;
	}

	public Duration getBreakLength() {
		return breakLength;
	}

	public void setBreakLength(Duration breakLength) {
		this.breakLength = breakLength;
	}

	public LocalTime getLunchTime() {
		return lunchTime;
	}

	public void setLunchTime(LocalTime lunchTime) {
		this.lunchTime = lunchTime;
	}

	public Duration getLunchLength() {
		return lunchLength;
	}

	public void setLunchLength(Duration lunchLength) {
		this.lunchLength = lunchLength;
	}

	public Schedule(int scheduleId, LocalTime openAt, LocalTime closeAt, int classHourPerDay, Duration classHourLength,
			LocalTime breakTime, Duration breakLength, LocalTime lunchTime, Duration lunchLength) {
		super();
		ScheduleId = scheduleId;
		this.openAt = openAt;
		this.closeAt = closeAt;
		this.classHourPerDay = classHourPerDay;
		this.classHourLength = classHourLength;
		this.breakTime = breakTime;
		this.breakLength = breakLength;
		this.lunchTime = lunchTime;
		this.lunchLength = lunchLength;
	}
	
	

	
	
	
	
	
}
