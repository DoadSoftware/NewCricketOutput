package com.cricket.containers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ball {
	@JsonProperty("MatchID")
	long matchId;
	@JsonProperty("InningsID")
	int inningsId;
	@JsonProperty("StrikerName")
	String strikerName;
	@JsonProperty("Runs")
	int runs;
	@JsonProperty("Extras")
	int extras;
	@JsonProperty("WWRegion")
	int wwRegion;
	
	
	
	public Ball() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getMatchId() {
		return matchId;
	}
	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}
	public int getInningsId() {
		return inningsId;
	}
	public void setInningsId(int inningsId) {
		this.inningsId = inningsId;
	}
	public String getStrikerName() {
		return strikerName;
	}
	public void setStrikerName(String strikerName) {
		this.strikerName = strikerName;
	}
	public int getRuns() {
		return runs;
	}
	public void setRuns(int runs) {
		this.runs = runs;
	}
	public int getExtras() {
		return extras;
	}
	public void setExtras(int extras) {
		this.extras = extras;
	}
	public int getWwRegion() {
		return wwRegion;
	}
	public void setWwRegion(int wwRegion) {
		this.wwRegion = wwRegion;
	}
	@Override
	public String toString() {
		return "Ball [matchId=" + matchId + ", inningsId=" + inningsId + ", strikerName=" + strikerName + ", runs="
				+ runs + ", extras=" + extras + ", wwRegion=" + wwRegion + "]";
	}
	
}
