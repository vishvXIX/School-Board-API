package com.school.SBA.ResponseDTO;

import java.time.LocalTime;

public class ScheduleResponse {

	private int ScheduleId;
	private LocalTime openAt;
	private LocalTime closeAt;
	private int classHourPerDay;
	private int classHourLengthInMinutes;
	private LocalTime breakTime;
	private int breakLengthInMinutes; 
	private LocalTime lunchTime;
	private int lunchLengthInMinutes;
	
	public ScheduleResponse() {
		super();
	}

	public ScheduleResponse(int scheduleId, LocalTime openAt, LocalTime closeAt, int classHourPerDay,
			int classHourLengthInMinutes, LocalTime breakTime, int breakLengthInMinutes, LocalTime lunchTime,
			int lunchLengthInMinutes) {
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

	public int getClassHourLengthInMinutes() {
		return classHourLengthInMinutes;
	}

	public void setClassHourLengthInMinutes(int classHourLengthInMinutes) {
		this.classHourLengthInMinutes = classHourLengthInMinutes;
	}

	public LocalTime getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(LocalTime breakTime) {
		this.breakTime = breakTime;
	}

	public int getBreakLengthInMinutes() {
		return breakLengthInMinutes;
	}

	public void setBreakLengthInMinutes(int breakLengthInMinutes) {
		this.breakLengthInMinutes = breakLengthInMinutes;
	}

	public LocalTime getLunchTime() {
		return lunchTime;
	}

	public void setLunchTime(LocalTime lunchTime) {
		this.lunchTime = lunchTime;
	}

	public int getLunchLengthInMinutes() {
		return lunchLengthInMinutes;
	}

	public void setLunchLengthInMinutes(int lunchLengthInMinutes) {
		this.lunchLengthInMinutes = lunchLengthInMinutes;
	}
	
	
	
	
}
