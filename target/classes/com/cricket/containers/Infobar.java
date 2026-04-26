package com.cricket.containers;

import java.util.List;

import com.Ae_Third_Party_Xml.AE_Batsman;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;

public class Infobar {
	
	private boolean infobar_on_screen;
	private boolean scorebug_on_screen;
	private boolean result_on_screen;
	private boolean sponsor_on_screen;
	private boolean infobar_down;
	private boolean powerplay_on_screen;
	private boolean watermark_on_screen;
	private boolean infobar_loaded;
	private boolean lt_loaded;
	private boolean ff_loaded;
	private boolean ff2_loaded;
	private boolean scoreboard_on_screen;
	private boolean Thisover ,OverPlayed,overBallGreaterThen9,overBallequalto0,overballLessThan9;
	private String scorebug_last_value;
	private boolean FieldPlotter_on_screen;
	private boolean show_winner = false;
	private boolean is_ticker_shrink = false;
	private boolean forced_powerplay_out = false;
	private String directer_section;
	private String middle_section;
	private String full_section;
	private String last_full_section;
	private String bottom_right_top_section;
	private String bottom_right_bottom_section;
	private String bottom_right_section;
	private String top_section;
	private String last_middle_section;
	private String last_bottom_right_top_section;
	private String last_bottom_right_bottom_section;
	private String last_bottom_right_section;
	private String last_top_section;
	private String ident_section;
	private String last_ident_section;
	private String last_speed_value;
	private String last_x_ball_value;
	
	
	private String bottom_left_section;
	private String last_bottom_left_section;
	private String top_right_section;
	private String last_top_right_section;
	private String bottom_section;
	private String last_bottom_section;
	private int player_id;
	
	private String last_wide_value;
	private String last_noball_value;
	private String last_ball_value;
	
	private String which_team;
	private String last_which_team;
	
	List<BattingCard> last_batsmen;
	List<AE_Batsman> last_ae_batsmen;

	BowlingCard last_bowler;
	
	public boolean isInfobar_on_screen() {
		return infobar_on_screen;
	}
	public void setInfobar_on_screen(boolean infobar_on_screen) {
		this.infobar_on_screen = infobar_on_screen;
	}
	public boolean isPowerplay_on_screen() {
		return powerplay_on_screen;
	}
	public void setPowerplay_on_screen(boolean powerplay_on_screen) {
		this.powerplay_on_screen = powerplay_on_screen;
	}
	public boolean isForced_powerplay_out() {
		return forced_powerplay_out;
	}
	public void setForced_powerplay_out(boolean forced_powerplay_out) {
		this.forced_powerplay_out = forced_powerplay_out;
	}
	public String getMiddle_section() {
		return middle_section;
	}
	public void setMiddle_section(String middle_section) {
		this.middle_section = middle_section;
	}
	public String getBottom_right_top_section() {
		return bottom_right_top_section;
	}
	public void setBottom_right_top_section(String bottom_right_top_section) {
		this.bottom_right_top_section = bottom_right_top_section;
	}
	public String getBottom_right_bottom_section() {
		return bottom_right_bottom_section;
	}
	public void setBottom_right_bottom_section(String bottom_right_bottom_section) {
		this.bottom_right_bottom_section = bottom_right_bottom_section;
	}
	public String getBottom_right_section() {
		return bottom_right_section;
	}
	public void setBottom_right_section(String bottom_right_section) {
		this.bottom_right_section = bottom_right_section;
	}
	public String getTop_section() {
		return top_section;
	}
	public void setTop_section(String top_section) {
		this.top_section = top_section;
	}
	public String getLast_middle_section() {
		return last_middle_section;
	}
	public void setLast_middle_section(String last_middle_section) {
		this.last_middle_section = last_middle_section;
	}
	public String getLast_bottom_right_top_section() {
		return last_bottom_right_top_section;
	}
	public void setLast_bottom_right_top_section(String last_bottom_right_top_section) {
		this.last_bottom_right_top_section = last_bottom_right_top_section;
	}
	public String getLast_bottom_right_bottom_section() {
		return last_bottom_right_bottom_section;
	}
	public void setLast_bottom_right_bottom_section(String last_bottom_right_bottom_section) {
		this.last_bottom_right_bottom_section = last_bottom_right_bottom_section;
	}
	public String getLast_bottom_right_section() {
		return last_bottom_right_section;
	}
	public void setLast_bottom_right_section(String last_bottom_right_section) {
		this.last_bottom_right_section = last_bottom_right_section;
	}
	public String getLast_top_section() {
		return last_top_section;
	}
	public void setLast_top_section(String last_top_section) {
		this.last_top_section = last_top_section;
	}
	public List<BattingCard> getLast_batsmen() {
		return last_batsmen;
	}
	public void setLast_batsmen(List<BattingCard> last_batsmen) {
		this.last_batsmen = last_batsmen;
	}
	public BowlingCard getLast_bowler() {
		return last_bowler;
	}
	public void setLast_bowler(BowlingCard last_bowler) {
		this.last_bowler = last_bowler;
	}
	public String getIdent_section() {
		return ident_section;
	}
	public void setIdent_section(String ident_section) {
		this.ident_section = ident_section;
	}
	public String getLast_ident_section() {
		return last_ident_section;
	}
	public void setLast_ident_section(String last_ident_section) {
		this.last_ident_section = last_ident_section;
	}
	public String getBottom_left_section() {
		return bottom_left_section;
	}
	public void setBottom_left_section(String bottom_left_section) {
		this.bottom_left_section = bottom_left_section;
	}
	public String getTop_right_section() {
		return top_right_section;
	}
	public void setTop_right_section(String top_right_section) {
		this.top_right_section = top_right_section;
	}
	public String getLast_bottom_left_section() {
		return last_bottom_left_section;
	}
	public void setLast_bottom_left_section(String last_bottom_left_section) {
		this.last_bottom_left_section = last_bottom_left_section;
	}
	public String getLast_top_right_section() {
		return last_top_right_section;
	}
	public void setLast_top_right_section(String last_top_right_section) {
		this.last_top_right_section = last_top_right_section;
	}
	public String getBottom_section() {
		return bottom_section;
	}
	public void setBottom_section(String bottom_section) {
		this.bottom_section = bottom_section;
	}
	public String getLast_bottom_section() {
		return last_bottom_section;
	}
	public void setLast_bottom_section(String last_bottom_section) {
		this.last_bottom_section = last_bottom_section;
	}
	public String getDirecter_section() {
		return directer_section;
	}
	public void setDirecter_section(String directer_section) {
		this.directer_section = directer_section;
	}
	public String getFull_section() {
		return full_section;
	}
	public void setFull_section(String full_section) {
		this.full_section = full_section;
	}
	public String getLast_full_section() {
		return last_full_section;
	}
	public void setLast_full_section(String last_full_section) {
		this.last_full_section = last_full_section;
	}
	public String getLast_speed_value() {
		return last_speed_value;
	}
	public void setLast_speed_value(String last_speed_value) {
		this.last_speed_value = last_speed_value;
	}
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public String getLast_x_ball_value() {
		return last_x_ball_value;
	}
	public void setLast_x_ball_value(String last_x_ball_value) {
		this.last_x_ball_value = last_x_ball_value;
	}
	public boolean isInfobar_loaded() {
		return infobar_loaded;
	}
	public void setInfobar_loaded(boolean infobar_loaded) {
		this.infobar_loaded = infobar_loaded;
	}
	public boolean isLt_loaded() {
		return lt_loaded;
	}
	public void setLt_loaded(boolean lt_loaded) {
		this.lt_loaded = lt_loaded;
	}
	public boolean isFf_loaded() {
		return ff_loaded;
	}
	public void setFf_loaded(boolean ff_loaded) {
		this.ff_loaded = ff_loaded;
	}
	public boolean isFf2_loaded() {
		return ff2_loaded;
	}
	public void setFf2_loaded(boolean ff2_loaded) {
		this.ff2_loaded = ff2_loaded;
	}
	public String getLast_wide_value() {
		return last_wide_value;
	}
	public void setLast_wide_value(String last_wide_value) {
		this.last_wide_value = last_wide_value;
	}
	public String getLast_noball_value() {
		return last_noball_value;
	}
	public void setLast_noball_value(String last_noball_value) {
		this.last_noball_value = last_noball_value;
	}
	public String getLast_ball_value() {
		return last_ball_value;
	}
	public void setLast_ball_value(String last_ball_value) {
		this.last_ball_value = last_ball_value;
	}
	public boolean isFieldPlotter_on_screen() {
		return FieldPlotter_on_screen;
	}
	public void setFieldPlotter_on_screen(boolean fieldPlotter_on_screen) {
		FieldPlotter_on_screen = fieldPlotter_on_screen;
	}
	public String getWhich_team() {
		return which_team;
	}
	public void setWhich_team(String which_team) {
		this.which_team = which_team;
	}
	public String getLast_which_team() {
		return last_which_team;
	}
	public void setLast_which_team(String last_which_team) {
		this.last_which_team = last_which_team;
	}
	public boolean isShow_winner() {
		return show_winner;
	}
	public void setShow_winner(boolean show_winner) {
		this.show_winner = show_winner;
	}
	public boolean isIs_ticker_shrink() {
		return is_ticker_shrink;
	}
	public void setIs_ticker_shrink(boolean is_ticker_shrink) {
		this.is_ticker_shrink = is_ticker_shrink;
	}
	public boolean isInfobar_down() {
		return infobar_down;
	}
	public void setInfobar_down(boolean infobar_down) {
		this.infobar_down = infobar_down;
	}
	public boolean isSponsor_on_screen() {
		return sponsor_on_screen;
	}
	public void setSponsor_on_screen(boolean sponsor_on_screen) {
		this.sponsor_on_screen = sponsor_on_screen;
	}
	public boolean isResult_on_screen() {
		return result_on_screen;
	}
	public void setResult_on_screen(boolean result_on_screen) {
		this.result_on_screen = result_on_screen;
	}
	public boolean isScorebug_on_screen() {
		return scorebug_on_screen;
	}
	public void setScorebug_on_screen(boolean scorebug_on_screen) {
		this.scorebug_on_screen = scorebug_on_screen;
	}
	public boolean isScoreboard_on_screen() {
		return scoreboard_on_screen;
	}
	public void setScoreboard_on_screen(boolean scoreboard_on_screen) {
		this.scoreboard_on_screen = scoreboard_on_screen;
	}
	public String getScorebug_last_value() {
		return scorebug_last_value;
	}
	public void setScorebug_last_value(String scorebug_last_value) {
		this.scorebug_last_value = scorebug_last_value;
	}
	public List<AE_Batsman> getLast_ae_batsmen() {
		return last_ae_batsmen;
	}
	public void setLast_ae_batsmen(List<AE_Batsman> last_ae_batsmen) {
		this.last_ae_batsmen = last_ae_batsmen;
	}
	public boolean isThisover() {
		return Thisover;
	}
	public void setThisover(boolean thisover) {
		Thisover = thisover;
	}
	public boolean isOverBallGreaterThen9() {
		return overBallGreaterThen9;
	}
	public void setOverBallGreaterThen9(boolean overBallGreaterThen9) {
		this.overBallGreaterThen9 = overBallGreaterThen9;
	}
	public boolean isOverBallequalto0() {
		return overBallequalto0;
	}
	public void setOverBallequalto0(boolean overBallequalto0) {
		this.overBallequalto0 = overBallequalto0;
	}
	public boolean isOverballLessThan9() {
		return overballLessThan9;
	}
	public void setOverballLessThan9(boolean overballLessThan9) {
		this.overballLessThan9 = overballLessThan9;
	}
	public boolean isOverPlayed() {
		return OverPlayed;
	}
	public void setOverPlayed(boolean overPlayed) {
		OverPlayed = overPlayed;
	}
	public boolean isWatermark_on_screen() {
		return watermark_on_screen;
	}
	public void setWatermark_on_screen(boolean watermark_on_screen) {
		this.watermark_on_screen = watermark_on_screen;
	}
	
}
