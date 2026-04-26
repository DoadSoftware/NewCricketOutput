package com.cricket.containers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WagonData {
	List<Ball> ball;

	public WagonData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Ball> getBall() {
		return ball;
	}

	public void setBall(List<Ball> ball) {
		this.ball = ball;
	}
	
}
