package com.cricket.containers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Pitch {

    @JsonProperty("MatchInfo")
    private List<MatchInfo> matchInfo;
    
    @JsonProperty("Title")
    private String title;
    
    @JsonProperty("SubTitle")
    private String subTitle;
    
    @JsonProperty("Legend")
    private List<Legend> legend;
    
    @JsonProperty("LengthDisplay")
    private List<LengthDisplay> lengthDisplay;

    @JsonProperty("BallData")
    private List<BallData> ballData;

    public List<MatchInfo> getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(List<MatchInfo> matchInfo) {
        this.matchInfo = matchInfo;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public List<Legend> getLegend() {
		return legend;
	}

	public void setLegend(List<Legend> legend) {
		this.legend = legend;
	}

	public List<LengthDisplay> getLengthDisplay() {
		return lengthDisplay;
	}

	public void setLengthDisplay(List<LengthDisplay> lengthDisplay) {
		this.lengthDisplay = lengthDisplay;
	}

	public List<BallData> getBallData() {
        return ballData;
    }

    public void setBallData(List<BallData> ballData) {
        this.ballData = ballData;
    }

    public static class MatchInfo {
    	@JsonProperty("MatchName")
        private String matchName;

        @JsonProperty("Venue")
        private String venue;

        @JsonProperty("Date")
        private String date;

		public String getMatchName() {
			return matchName;
		}

		public void setMatchName(String matchName) {
			this.matchName = matchName;
		}

		public String getVenue() {
			return venue;
		}

		public void setVenue(String venue) {
			this.venue = venue;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		@Override
		public String toString() {
			return "MatchInfo [matchName=" + matchName + ", venue=" + venue + ", date=" + date + "]";
		}
    }
    
    public static class Legend {
    	@JsonProperty("Label")
        private String label;

        @JsonProperty("ColorCode")
        private String colorCode;
        
        @JsonProperty("NoOfBalls")
        private String noOfBalls;

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getColorCode() {
			return colorCode;
		}

		public void setColorCode(String colorCode) {
			this.colorCode = colorCode;
		}

		public String getNoOfBalls() {
			return noOfBalls;
		}

		public void setNoOfBalls(String noOfBalls) {
			this.noOfBalls = noOfBalls;
		}
		
    }
    
    public static class LengthDisplay {

        @JsonProperty("Title")
        private String title;

        @JsonProperty("Values")
        private List<LengthValue> values;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<LengthValue> getValues() {
            return values;
        }

        public void setValues(List<LengthValue> values) {
            this.values = values;
        }
    }

    public static class LengthValue {

        @JsonProperty("Range")
        private String range;

        @JsonProperty("Value")
        private String value;

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class BallData {
    	@JsonProperty("OverNo")
        private double overNo;

        @JsonProperty("OutCome")
        private String outCome;

        @JsonProperty("LengthX_CM")
        private int lengthX_CM;

        @JsonProperty("LengthY_CM")
        private int lengthY_CM;

        @JsonProperty("Extratype")
        private String extratype;
        
        @JsonProperty("ColorCode")
        private String colorCode;

		public double getOverNo() {
			return overNo;
		}

		public void setOverNo(double overNo) {
			this.overNo = overNo;
		}

		public String getOutCome() {
			return outCome;
		}

		public void setOutCome(String outCome) {
			this.outCome = outCome;
		}

		public int getLengthX_CM() {
			return lengthX_CM;
		}

		public void setLengthX_CM(int lengthX_CM) {
			this.lengthX_CM = lengthX_CM;
		}

		public int getLengthY_CM() {
			return lengthY_CM;
		}

		public void setLengthY_CM(int lengthY_CM) {
			this.lengthY_CM = lengthY_CM;
		}

		public String getExtratype() {
			return extratype;
		}

		public void setExtratype(String extratype) {
			this.extratype = extratype;
		}
		
		public String getColorCode() {
			return colorCode;
		}

		public void setColorCode(String colorCode) {
			this.colorCode = colorCode;
		}

		@Override
		public String toString() {
			return "BallData [overNo=" + overNo + ", outCome=" + outCome + ", lengthX_CM=" + lengthX_CM
					+ ", lengthY_CM=" + lengthY_CM + ", extratype=" + extratype + ", colorCode=" + colorCode + "]";
		}

    }

    @Override
	public String toString() {
		return "Pitch [matchInfo=" + matchInfo + ", ballData=" + ballData + "]";
	}
}
