package com.cricket.broadcaster;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Configuration;
import com.cricket.model.FallOfWicket;
import com.cricket.model.Fixture;
import com.cricket.model.Ground;
import com.cricket.model.InfobarStats;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.LeagueTeam;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.OverByOverData;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GCPL extends Scene{

	public String broadcaster = "GPCL"; // put this in Cricket Util
	public String status; // DJ -> Remove status if NOT needed.
	public String slashOrDash = "-";
	public String logo_path = "IMAGE*/Default/GPCL/Logos/";
	public String photo_path = "C:\\\\Images\\\\GPCL\\\\Photos\\\\";
	private String local_photo_path = "\\\\c\\\\Images\\\\GPCL\\\\Photos\\\\";
	public Infobar infobar = new Infobar(); 
	public String which_graphic_on_screen = "";
	
	public GCPL() {
		super();
	}

	public GCPL(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Infobar updateInfobar(List<Scene> scenes, MatchAllData match, PrintWriter print_writer) throws InterruptedException
	{
		if(infobar.isInfobar_on_screen() == true) {
			if(infobar.getIdent_section() != null && !infobar.getIdent_section().trim().isEmpty()) {
				infobar = populateInfobarIdent(infobar,true, scenes.get(0).getScene_path(), 
						print_writer, match, broadcaster);
			}else {
				infobar = populateInfobarTeamScore(infobar,true, print_writer, match, broadcaster);
				infobar = populateVizInfobarMiddle(infobar, true, print_writer, match, broadcaster);
				if(infobar.getBottom_right_section() != null && !infobar.getBottom_right_section().trim().isEmpty()) {
					infobar = populateVizInfobarRight(infobar, true,print_writer, match, broadcaster);
					if(CricketFunctions.getCurrentInningCurrentBowler(match) != null) {
						infobar.setLast_bowler(CricketFunctions.getCurrentInningCurrentBowler(match));
					}
				}else {
					infobar = populateVizInfobarRightTop(infobar, true, print_writer, match, broadcaster);
					infobar = populateVizInfobarRightBottom(infobar, true, print_writer, match, broadcaster);
				}
				if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
					infobar = populateVizInfobarTop(infobar, true, print_writer, match, broadcaster);
				}
			}
		}
		return infobar;
	}
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics, Configuration config) throws InterruptedException, ParseException, JAXBException, IllegalAccessException, InvocationTargetException, IOException{
	
		switch (whatToProcess) {
		case "ANIMATE-IN-INFOBAR": case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
		case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": 
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY":
		case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-IDENT":
		case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BUG-BOWLER":
		case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUGTARGET": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY":
		case "ANIMATE-IN-LANDMARK":	case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH": case "ANIMATE-IN-L3MATCH_PROMO":
		case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-OUT-DIRECTOR":
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-GENERIC":
		case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM": case "ANIMATE-OUT-SECTION2":
		case "ANIMATE-OUT-SECTION4_N_5": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-SCHEDULE": case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-THISSERIES":
		case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-PLAYERPROFILEBALL":
			
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PLAYERPROFILE": 
			case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-POSITION_LANDMARK": 
			case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE":
			case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM":
			case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET":
			case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBALL":
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					AnimateInGraphics(print_writer, "FF_IN");
					TimeUnit.MILLISECONDS.sleep(200);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					AnimateInGraphics(print_writer, "FF_IN");
				}
				break;
			case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS": case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED": 
			case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": 
			case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT":   case "ANIMATE-IN-BOWLERSUMMARY": 
			case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY": case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-BATSMAN_THIS_MATCH": 
			case "ANIMATE-IN-BOWLER_THIS_MATCH": case "ANIMATE-IN-L3MATCH_PROMO": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": 
			case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-GENERIC": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-THISSERIES":
			case "ANIMATE-IN-PLAYERPROFILEBAT":
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
						switch(infobar.getLast_top_section().toUpperCase()) {
						case CricketUtil.TOSS:
							processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
							break;
						case "CRR":
							processAnimation(print_writer, "Section2$CurRunRateOut", "START", broadcaster);
							break;
						case "CRR_RRR":
							processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
							break;
						case "FIRST_INNING_SCORE":
							processAnimation(print_writer, "Section2$FistInnScoreOut", "START", broadcaster);
							break;
						case CricketUtil.BOUNDARY:
							processAnimation(print_writer, "Section2$BallsSinceLastBoundaryOut", "START", broadcaster);
							break;
						case "TARGET":
							processAnimation(print_writer, "Section2$DLSTargetOut", "START", broadcaster);
							break;
						case "EXTRAS":
							processAnimation(print_writer, "Section2$ExtrasOut", "START", broadcaster);
							break;
						case "EQUATION":
							processAnimation(print_writer, "Section2$EquationOut", "START", broadcaster);
							break;
						case "PROJECTED":
							processAnimation(print_writer, "Section2$ProjectedOut", "START", broadcaster);
							break;
						case "BOUNDARIES":
							processAnimation(print_writer, "Section2$BoundariesOut", "START", broadcaster);
							break;
						case "PARTNERSHIP":
							processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
							break;
						case "LAST_WICKET":
							processAnimation(print_writer, "Section2$LastWicketOut", "START", broadcaster);
							break;
						case CricketUtil.TIMELINE:
							processAnimation(print_writer, "Section2$TimelineOut", "START", broadcaster);
							break;
						case "STATISTICS":
							processAnimation(print_writer, "Section2$FreeTextSmallOut", "START", broadcaster);
							break;
						}
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
					}
					infobar.setLast_top_section("");infobar.setTop_section("");
					AnimateInGraphics(print_writer, "LT_IN");
					TimeUnit.MILLISECONDS.sleep(200);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					AnimateInGraphics(print_writer, "LT_IN");
				}
				break;
			}
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-INFOBAR");
					AnimateInGraphics(print_writer, "IDENT");
					which_graphic_on_screen = "IDENT";
					infobar.setInfobar_on_screen(true);
				}else {
					AnimateInGraphics(print_writer, "IN");
					AnimateInGraphics(print_writer, "IDENT");
					which_graphic_on_screen = "IDENT";
					infobar.setInfobar_on_screen(true);
				}
				
				break;
			case "ANIMATE-IN-SCORECARD":
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else {
					AnimateInGraphics(print_writer, "SCORECARD");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD";
				break;
			case "ANIMATE-MINI-BATTINGCARD":
				AnimateInGraphics(print_writer, "MINI_BATTINGCARD");
				which_graphic_on_screen = "MINI_BATTINGCARD";
				break;
			case "ANIMATE-MINI-BOWLINGCARD":
				AnimateInGraphics(print_writer, "MINI_BOWLINGCARD");
				which_graphic_on_screen = "MINI_BOWLINGCARD";
				break;
			case "ANIMATE-IN-BOWLINGCARD":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else {
					AnimateInGraphics(print_writer, "BOWLINGCARD");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_BOWLINGCARD";
				break;
			case "ANIMATE-IN-PARTNERSHIP":
				AnimateInGraphics(print_writer, "PARTNERSHIP");
				which_graphic_on_screen = "PARTNERSHIP";
				break;
			case "ANIMATE-IN-TIEID-DOUBLE":
				AnimateInGraphics(print_writer, "TIEID-DOUBLE");
				which_graphic_on_screen = "TIEID-DOUBLE";
				break;
			case "ANIMATE-IN-MATCHSUMARRY":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
				}else {
					AnimateInGraphics(print_writer, "MATCHSUMMARY");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_MATCHSUMMARY";
				break;
			case "ANIMATE-IN-BUG-DISMISSAL":
				AnimateInGraphics(print_writer, "BUG-DISMISSAL");
				which_graphic_on_screen = "BUG-DISMISSAL";
				break;
			case "ANIMATE-IN-BUG":
				AnimateInGraphics(print_writer, "BUG");
				which_graphic_on_screen = "BUG";
				break;
			case "ANIMATE-IN-BUG-BOWLER":
				AnimateInGraphics(print_writer, "BUGBOWLER");
				which_graphic_on_screen = "BUGBOWLER";
				break;
			case "ANIMATE-IN-BUG-DB":
				AnimateInGraphics(print_writer, "BUG-DB");
				which_graphic_on_screen = "BUG-DB";
				break;
			case "ANIMATE-IN-HOWOUT":
				AnimateInGraphics(print_writer, "HOWOUT");
				which_graphic_on_screen = "HOWOUT";
				break;
			case "ANIMATE-IN-HOWOUT_QUICK":
				AnimateInGraphics(print_writer, "HOWOUT_QUICK");
				which_graphic_on_screen = "HOWOUT_QUICK";
				break;
			case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER":
				AnimateInGraphics(print_writer, "HOWOUT_WITHOUT");
				which_graphic_on_screen = "HOWOUT_WITHOUT";
				break;
			case "ANIMATE-IN-SCHEDULE":
				AnimateInGraphics(print_writer, "SCHEDULE");
				which_graphic_on_screen = "SCHEDULE";
				break;
			case "ANIMATE-IN-NAMESUPER":
				AnimateInGraphics(print_writer, "NAMESUPER");
				which_graphic_on_screen = "NAMESUPER";
				break;
			case "ANIMATE-IN-NAMESUPER-PLAYER":
				AnimateInGraphics(print_writer, "NAMESUPER-PLAYER");
				which_graphic_on_screen = "NAMESUPER-PLAYER";
				break;
			case "ANIMATE-IN-FALLOFWICKET":
				AnimateInGraphics(print_writer, "FALLOFWICKET");
				which_graphic_on_screen = "FALLOFWICKET";
				break;
			case "ANIMATE-IN-TEAMS_LOGO":
				AnimateInGraphics(print_writer, "TEAMS_LOGO");
				which_graphic_on_screen = "TEAMS_LOGO";
				break;
			case "ANIMATE-IN-L3MATCHID":
				AnimateInGraphics(print_writer, "L3MATCHID");
				which_graphic_on_screen = "L3MATCHID";
				break;
			case "ANIMATE-IN-MATCH_PROMO":
				AnimateInGraphics(print_writer, "MATCH_PROMO");
				which_graphic_on_screen = "MATCH_PROMO";
				break;
			case "ANIMATE-IN-L3MATCH_PROMO":
				AnimateInGraphics(print_writer, "L3MATCH_PROMO");
				which_graphic_on_screen = "L3MATCH_PROMO";
				break;
			case "ANIMATE-IN-PREVIOUS_SUMMARY":
				AnimateInGraphics(print_writer, "PREVIOUS_SUMMARY");
				which_graphic_on_screen = "PREVIOUS_SUMMARY";
				break;
			case "ANIMATE-IN-TARGET":
				AnimateInGraphics(print_writer, "TARGET");
				which_graphic_on_screen = "TARGET";
				break;
			case "ANIMATE-IN-BUGTARGET":
				AnimateInGraphics(print_writer, "BUGTARGET");
				which_graphic_on_screen = "BUGTARGET";
				break;
			case "ANIMATE-IN-COMPARISION":
				AnimateInGraphics(print_writer, "COMPARISION");
				which_graphic_on_screen = "COMPARISION";
				break;
			case "ANIMATE-IN-LTPARTNERSHIP":
				AnimateInGraphics(print_writer, "LTPARTNERSHIP");
				which_graphic_on_screen = "LTPARTNERSHIP";
				break;
			case "ANIMATE-IN-SPLIT":
				AnimateInGraphics(print_writer, "SPLIT");
				which_graphic_on_screen = "SPLIT";
				break;
			case "ANIMATE-IN-BATSMANSTATS":
				AnimateInGraphics(print_writer, "BATSMANSTATS");
				which_graphic_on_screen = "BATSMANSTATS";
				break;
			case "ANIMATE-IN-BOWLERSTATS":
				AnimateInGraphics(print_writer, "BOWLERSTATS");
				which_graphic_on_screen = "BOWLERSTATS";
				break;
			case "ANIMATE-IN-BOWLERSUMMARY":
				AnimateInGraphics(print_writer, "BOWLERSUMMARY");
				which_graphic_on_screen = "BOWLERSUMMARY";
				break;
			case "ANIMATE-IN-PLAYERSUMMARY":
				AnimateInGraphics(print_writer, "PLAYERSUMMARY");
				which_graphic_on_screen = "PLAYERSUMMARY";
				break;
			case "ANIMATE-IN-TEAMSUMMARY":
				AnimateInGraphics(print_writer, "TEAMSUMMARY");
				which_graphic_on_screen = "TEAMSUMMARY";
				break;
			case "ANIMATE-IN-NEXT_TO_BAT":
				AnimateInGraphics(print_writer, "NEXTTOBAT");
				which_graphic_on_screen = "NEXTTOBAT";
				break;
			case "ANIMATE-IN-PROJECTED":
				AnimateInGraphics(print_writer, "PROJECTED");
				which_graphic_on_screen = "PROJECTED";
				break;
			case "ANIMATE-IN-BOWLERDETAILS":
				AnimateInGraphics(print_writer, "BOWLERDETAILS");
				which_graphic_on_screen = "BOWLERDETAILS";
				break;
			case "ANIMATE-IN-LTPOWERPLAY":
				AnimateInGraphics(print_writer, "LTPOWERPLAY");
				which_graphic_on_screen = "LTPOWERPLAY";
				break;
			case "ANIMATE-IN-MATCHID":
				AnimateInGraphics(print_writer, "MATCHID");
				which_graphic_on_screen = "MATCHID";
				break;
			case "ANIMATE-IN-L3PLAYERPROFILE":
				AnimateInGraphics(print_writer, "L3PLAYERPROFILE");
				which_graphic_on_screen = "L3PLAYERPROFILE";
				break;
			case "ANIMATE-IN-PLAYERPROFILEBALL":
				AnimateInGraphics(print_writer, "PLAYERPROFILEBALL");
				which_graphic_on_screen = "PLAYERPROFILEBALL";
				break;
			case "ANIMATE-IN-THISSERIES":
				AnimateInGraphics(print_writer, "THISSERIES");
				which_graphic_on_screen = "THISSERIES";
				break;
			case "ANIMATE-IN-FFTHISSERIES":
				AnimateInGraphics(print_writer, "FF-THISSERIES");
				which_graphic_on_screen = "FF-THISSERIES";
				break;
			case "ANIMATE-IN-PLAYERPROFILE":
				AnimateInGraphics(print_writer, "FFPLAYERPROFILE");
				which_graphic_on_screen = "FFPLAYERPROFILE";
				break;
			case "ANIMATE-IN-PLAYERPROFILEBAT":
				AnimateInGraphics(print_writer, "PLAYERPROFILEBAT");
				which_graphic_on_screen = "PLAYERPROFILEBAT";
				break;
			case "ANIMATE-IN-PLAYINGXI":
				AnimateInGraphics(print_writer, "TEAMLINEUP");
				which_graphic_on_screen = "TEAMLINEUP";
				break;
			case "ANIMATE-IN-DOUBLETEAMS":
				AnimateInGraphics(print_writer, "DOUBLETEAMS");
				which_graphic_on_screen = "DOUBLETEAMS";
				break;
			case "ANIMATE-IN-LANDMARK":
				AnimateInGraphics(print_writer, "LANDMARK");
				which_graphic_on_screen = "LANDMARK";
				break;
			case "ANIMATE-IN-EQUATION":
				AnimateInGraphics(print_writer, "EQUATION");
				which_graphic_on_screen = "EQUATION";
				break;
			case "ANIMATE-IN-POSITION_LANDMARK":
				AnimateInGraphics(print_writer, "POSITION_LANDMARK");
				which_graphic_on_screen = "POSITION_LANDMARK";
				break;
			case "ANIMATE-IN-BATSMAN_THIS_MATCH":
				AnimateInGraphics(print_writer, "BATSMAN_THIS_MATCH");
				which_graphic_on_screen = "BATSMAN_THIS_MATCH";
				break;
			case "ANIMATE-IN-BOWLER_THIS_MATCH":
				AnimateInGraphics(print_writer, "BOWLER_THIS_MATCH");
				which_graphic_on_screen = "BOWLER_THIS_MATCH";
				break;
			case "ANIMATE-IN-POINTSTABLE":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else {
					AnimateInGraphics(print_writer, "POINTSTABLE");
				}
				which_graphic_on_screen = "POINTSTABLE";
				break;
			case "ANIMATE-IN-LTPOINTSTABLE":
				AnimateInGraphics(print_writer, "LTPOINTSTABLE");
				which_graphic_on_screen = "LTPOINTSTABLE";
				break;
			case "ANIMATE-IN-BOWLER_STYLE":
				AnimateInGraphics(print_writer, "BOWLER_STYLE");
				which_graphic_on_screen = "BOWLER_STYLE";
				break;
			case "ANIMATE-IN-BATSMAN_STYLE":
				AnimateInGraphics(print_writer, "BATSMAN_STYLE");
				which_graphic_on_screen = "BATSMAN_STYLE";
				break;
			case "ANIMATE-IN-MANHATTAN":
				AnimateInGraphics(print_writer, "MANHATTAN");
				which_graphic_on_screen = "MANHATTAN";
				break;
			case "ANIMATE-IN-WORM":
				AnimateInGraphics(print_writer, "WORM");
				which_graphic_on_screen = "WORM";
				break;
			case "ANIMATE-IN-LEADERBOARD":
				AnimateInGraphics(print_writer, "LEADERBOARD");
				which_graphic_on_screen = "LEADERBOARD";
				break;
			case "ANIMATE-IN-FF_STATS":
				AnimateInGraphics(print_writer, "FF_STATS");
				which_graphic_on_screen = "FF_STATS";
				break;
			case "ANIMATE-IN-INFOBAR":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-IDENT");
					AnimateInGraphics(print_writer, "MAIN");
					which_graphic_on_screen = "SCOREBUG";
					infobar.setInfobar_on_screen(true);
					
				}else {
					AnimateInGraphics(print_writer, "SCOREBUG");
					which_graphic_on_screen = "SCOREBUG";
					infobar.setInfobar_on_screen(true);
				}
				
				break;
			case "CLEAR-ALL":
				
				   print_writer.println("-1 SCENE CLEANUP\0");
	               print_writer.println("-1 IMAGE CLEANUP\0");
	               print_writer.println("-1 GEOM CLEANUP\0");
	               print_writer.println("-1 FONT CLEANUP\0");
	               
	               print_writer.println("-1 IMAGE INFO\0");
	               print_writer.println("-1 RENDERER SET_OBJECT SCENE*" + valueToProcess.split(",")[0] + "\0");
	
	               print_writer.println("-1 RENDERER INITIALIZE\0");
	               print_writer.println("-1 RENDERER*SCENE_DATA INITIALIZE\0");
	               print_writer.println("-1 RENDERER*UPDATE SET 0\0");
	               print_writer.println("-1 RENDERER*STAGE SHOW 0.0\0");
	               
	               print_writer.println("-1 RENDERER*UPDATE SET 1\0");
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/GPCL/ScoreBug\0");
		           	
	               print_writer.println("-1 RENDERER*FRONT_LAYER INITIALIZE\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 0\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 1\0");
	               
	               print_writer.println("-1 SCENE CLEANUP\0");
	               print_writer.println("-1 IMAGE CLEANUP\0");
	               print_writer.println("-1 GEOM CLEANUP\0");
	               print_writer.println("-1 FONT CLEANUP\0");
	               
	               infobar.setInfobar_on_screen(false);
	               infobar = new Infobar();
	               which_graphic_on_screen = "";
					break;
			case "ANIMATE-OUT":
				switch(which_graphic_on_screen) {
				case "IDENT":
					AnimateOutGraphics(print_writer, "IDENT");
					which_graphic_on_screen = "";
					infobar.setInfobar_on_screen(false);
					break;
				case "BATBALLSUMMARY_SCORECARD":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_SCORECARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "MINI_BATTINGCARD":
					AnimateOutGraphics(print_writer, "MINI_BATTINGCARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "MINI_BOWLINGCARD":
					AnimateOutGraphics(print_writer, "MINI_BOWLINGCARD");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BATBALLSUMMARY_BOWLINGCARD":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_BOWLINGCARD");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BATBALLSUMMARY_MATCHSUMMARY":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_MATCHSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "PARTNERSHIP":
					AnimateOutGraphics(print_writer, "PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "TIEID-DOUBLE":
					AnimateOutGraphics(print_writer, "TIEID-DOUBLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG-DISMISSAL":
					AnimateOutGraphics(print_writer, "BUG-DISMISSAL");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG":
					AnimateOutGraphics(print_writer, "BUG");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUGBOWLER":
					AnimateOutGraphics(print_writer, "BUGBOWLER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG-DB":
					AnimateOutGraphics(print_writer, "BUG-DB");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "HOWOUT":
					AnimateOutGraphics(print_writer, "HOWOUT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "HOWOUT_QUICK":
					AnimateOutGraphics(print_writer, "HOWOUT_QUICK");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "HOWOUT_WITHOUT":
					AnimateOutGraphics(print_writer, "HOWOUT_WITHOUT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "SCHEDULE":
					AnimateOutGraphics(print_writer, "SCHEDULE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "NAMESUPER":
					AnimateOutGraphics(print_writer, "NAMESUPER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "NAMESUPER-PLAYER":
					AnimateOutGraphics(print_writer, "NAMESUPER-PLAYER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "SCOREBUG":
					AnimateOutGraphics(print_writer, "SCOREBUG");
					infobar.setInfobar_on_screen(false);
					infobar = new Infobar();
					break;
				
				case "FALLOFWICKET":
					AnimateOutGraphics(print_writer, "FALLOFWICKET");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "TEAMS_LOGO":
					AnimateOutGraphics(print_writer, "TEAMS_LOGO");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "L3MATCHID":
					AnimateOutGraphics(print_writer, "L3MATCHID");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "PLAYERPROFILEBALL":
					AnimateOutGraphics(print_writer, "PLAYERPROFILEBALL");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "PLAYERPROFILEBAT":
					AnimateOutGraphics(print_writer, "PLAYERPROFILEBAT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;	
				case "PREVIOUS_SUMMARY":
					AnimateOutGraphics(print_writer, "PREVIOUS_SUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "MATCH_PROMO":
					AnimateOutGraphics(print_writer, "MATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "L3MATCH_PROMO":
					AnimateOutGraphics(print_writer, "L3MATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "TARGET":
					AnimateOutGraphics(print_writer, "TARGET");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BUGTARGET":
					AnimateOutGraphics(print_writer, "BUGTARGET");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "COMPARISION":
					AnimateOutGraphics(print_writer, "COMPARISION");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LTPARTNERSHIP":
					AnimateOutGraphics(print_writer, "LTPARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "SPLIT":
					AnimateOutGraphics(print_writer, "SPLIT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BATSMANSTATS":
					AnimateOutGraphics(print_writer, "BATSMANSTATS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLERSTATS":
					AnimateOutGraphics(print_writer, "BOWLERSTATS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLERSUMMARY":
					AnimateOutGraphics(print_writer, "BOWLERSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "PLAYERSUMMARY":
					AnimateOutGraphics(print_writer, "PLAYERSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "TEAMSUMMARY":
					AnimateOutGraphics(print_writer, "TEAMSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "NEXTTOBAT":
					AnimateOutGraphics(print_writer, "NEXTTOBAT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "PROJECTED":
					AnimateOutGraphics(print_writer, "PROJECTED");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLERDETAILS":
					AnimateOutGraphics(print_writer, "BOWLERDETAILS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LTPOWERPLAY":
					AnimateOutGraphics(print_writer, "LTPOWERPLAY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "MATCHID":
					AnimateOutGraphics(print_writer, "MATCHID");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "L3PLAYERPROFILE":
					AnimateOutGraphics(print_writer, "L3PLAYERPROFILE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "THISSERIES":
					AnimateOutGraphics(print_writer, "THISSERIES");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "FF-THISSERIES":
					AnimateOutGraphics(print_writer, "FF-THISSERIES");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "FFPLAYERPROFILE":
					AnimateOutGraphics(print_writer, "FFPLAYERPROFILE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "TEAMLINEUP":
					AnimateOutGraphics(print_writer, "TEAMLINEUP");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "DOUBLETEAMS":
					AnimateOutGraphics(print_writer, "DOUBLETEAMS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "LANDMARK":
					AnimateOutGraphics(print_writer, "LANDMARK");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "EQUATION":
					AnimateOutGraphics(print_writer, "EQUATION");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "POSITION_LANDMARK":
					AnimateOutGraphics(print_writer, "POSITION_LANDMARK");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BATSMAN_THIS_MATCH":
					AnimateOutGraphics(print_writer, "BATSMAN_THIS_MATCH");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLER_THIS_MATCH":
					AnimateOutGraphics(print_writer, "BOWLER_THIS_MATCH");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "POINTSTABLE":
					AnimateOutGraphics(print_writer, "POINTSTABLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "LTPOINTSTABLE":
					AnimateOutGraphics(print_writer, "LTPOINTSTABLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLER_STYLE":
					AnimateOutGraphics(print_writer, "BOWLER_STYLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BATSMAN_STYLE":
					AnimateOutGraphics(print_writer, "BATSMAN_STYLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "MANHATTAN":
					AnimateOutGraphics(print_writer, "MANHATTAN");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "WORM":
					AnimateOutGraphics(print_writer, "WORM");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "LEADERBOARD":
					AnimateOutGraphics(print_writer, "LEADERBOARD");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "FF_STATS":
					AnimateOutGraphics(print_writer, "FF_STATS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				}
				break;
			case "ANIMATE-OUT-SECTION2":
				if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
					switch(infobar.getLast_top_section().toUpperCase()) {
					case CricketUtil.TOSS:
						processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
						break;
					case "CRR":
						processAnimation(print_writer, "Section2$CurRunRateOut", "START", broadcaster);
						break;
					case "CRR_RRR":
						processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
						break;
					case "FIRST_INNING_SCORE":
						processAnimation(print_writer, "Section2$FistInnScoreOut", "START", broadcaster);
						break;
					case CricketUtil.BOUNDARY:
						processAnimation(print_writer, "Section2$BallsSinceLastBoundaryOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section2$DLSTargetOut", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section2$ExtrasOut", "START", broadcaster);
						break;
					case "EQUATION":
						processAnimation(print_writer, "Section2$EquationOut", "START", broadcaster);
						break;
					case "PROJECTED":
						processAnimation(print_writer, "Section2$ProjectedOut", "START", broadcaster);
						break;
					case "BOUNDARIES":
						processAnimation(print_writer, "Section2$BoundariesOut", "START", broadcaster);
						break;
					case "PARTNERSHIP":
						processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
						break;
					case "LAST_WICKET":
						processAnimation(print_writer, "Section2$LastWicketOut", "START", broadcaster);
						break;
					case CricketUtil.TIMELINE:
						processAnimation(print_writer, "Section2$TimelineOut", "START", broadcaster);
						break;
					case "STATISTICS":
						processAnimation(print_writer, "Section2$FreeTextSmallOut", "START", broadcaster);
						break;
					}
					processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
				}
				infobar.setLast_top_section("");infobar.setTop_section("");
				break;
			case "ANIMATE-OUT-SECTION4_N_5":
				if(infobar.getLast_bottom_right_section() != null && infobar.getLast_bottom_right_section() != "") {
					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonOut", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					}
				}
				processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
				processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
				processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
				
				infobar.setBottom_right_bottom_section(CricketUtil.OVER);
				infobar = populateVizInfobarRightBottom(infobar, false,print_writer, match, broadcaster);
				
				infobar.setBottom_right_top_section(CricketUtil.BOWLER);
				infobar = populateVizInfobarRightTop(infobar, false,print_writer, match, broadcaster);
				
				processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
				processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
				processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
				
				infobar.setLast_bottom_right_section("");infobar.setBottom_right_section("");
				break;
				
			case "ANIMATE-OUT-DIRECTOR":
				AnimateOutGraphics(print_writer, "DIRECTOR");
				break;
			
			}
			break;
			
		case "BUG_GRAPHICS-OPTIONS": case "HOWOUT_GRAPHICS-OPTIONS": case "BATSMANSTATS_GRAPHICS-OPTIONS": case "BOWLERSTATS_GRAPHICS-OPTIONS": case "NAMESUPER_PLAYER_GRAPHICS-OPTIONS": 
		case "L3PLAYERPROFILE_GRAPHICS-OPTIONS": case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "BOTTOMLEFT_GRAPHICS-OPTIONS": case "BOTTOMRIGHT_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": 
		case "COMPARISION-GRAPHICS-OPTIONS": case "BOTTOM_GRAPHICS-OPTIONS": case "ANIMATE_PLAYINGXI-OPTIONS": case "PROJECTED_GRAPHICS-OPTIONS": case "TARGET_GRAPHICS-OPTIONS": 
		case "PLAYERSUMMARY_GRAPHICS-OPTIONS": case "BUG_DISMISSAL_GRAPHICS-OPTIONS": case "TOP_GRAPHICS-OPTIONS": case "BUG_BOWLER_GRAPHICS-OPTIONS": case "HOWOUT_WITHOUT_FIELDER_GRAPHICS-OPTIONS":
		case "BOWLERDETAILS_GRAPHICS-OPTIONS":	case "NEXTTOBAT_GRAPHICS-OPTIONS": case "BOWLERSUMMARY_GRAPHICS-OPTIONS": case "LANDMARK_GRAPHICS-OPTIONS": case "EQUATION_GRAPHICS-OPTIONS":
		case "POSITION_LANDMARK_GRAPHICS-OPTIONS": case "BATSMAN_THIS_MATCH_GRAPHICS-OPTIONS": case "BOWLER_THIS_MATCH_GRAPHICS-OPTIONS": case "PLAYERS_GRAPHICS-OPTIONS":
		case "BATSMAN_STYLE_GRAPHICS-OPTIONS": case "RIGHT_GRAPHICS-OPTIONS": case "THISSERIES-STATS_GRAPHICS-OPTIONS": case "FF_THISSERIES-STATS_GRAPHICS-OPTIONS":
			return match;
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "L3_MATCH-PROMO_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
		case "BUG_DB_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getBugs()).toString();
		case "PROMPT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getInfobarStats()).toString();
		case "LEADERBOARD_GRAPHICS-OPTIONS": case "WICKETS_GRAPHICS-OPTIONS": case "FOURS_GRAPHICS-OPTIONS": case "SIXES_GRAPHICS-OPTIONS":
			List<Tournament> tourn_stats = CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService, match,null);
			switch (whatToProcess) {
			case "LEADERBOARD_GRAPHICS-OPTIONS": 
				Collections.sort(tourn_stats,new CricketFunctions.BatsmenMostRunComparator());
				break;
			case "WICKETS_GRAPHICS-OPTIONS": 
				Collections.sort(tourn_stats,new CricketFunctions.BowlerWicketsComparator());
				break;
			case "FOURS_GRAPHICS-OPTIONS": 
				Collections.sort(tourn_stats,new CricketFunctions.BatsmanFoursComparator());
				break;
			case "SIXES_GRAPHICS-OPTIONS":
				Collections.sort(tourn_stats,new CricketFunctions.BatsmanSixesComparator());
				break;
			}
			return new ObjectMapper().writeValueAsString(tourn_stats).toString();
		
		case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-L3-BUG":  case "POPULATE-L3-HOWOUT":
		case "POPULATE-L3-BATSMANSTATS": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-FF-DOUBLETEAMS": case "POPULATE-L3-INFOBAR": 
		case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-FF-MATCHID": case "POPULATE-FF-PLAYINGXI": case "POPULATE-LT-PROJECTED": case "POPULATE-L3-TARGET": 
		case "POPULATE-L3-TEAMSUMMARY": case "POPULATE-L3-PLAYERSUMMARY": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-L3-FALLOFWICKET": case "POPULATE-L3-COMPARISION": case "POPULATE-INFOBAR-PROMPT": 
		case "POPULATE-LT-MATCHID": case "POPULATE-L3-BOWLERSTATS": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-SPLIT": case "POPULATE-L3-BUG-DB": case "POPULATE-L3-BUG-BOWLER": 
		case "POPULATE-LT-PARTNERSHIP": case "POPULATE-L3-BUGTARGET": case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER":case "POPULATE-L3-BOWLERSUMMARY": case "POPULATE-L3-NEXT_TO_BAT": case "POPULATE-L3-BOWLERDETAILS": 
		case "POPULATE-LT-POWERPLAY": case "POPULATE-FF-LANDMARK": case "POPULATE-PREVIOUS_SUMMARY": case "POPULATE-LT-EQUATION": case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-L3-BATSMAN_THIS_MATCH": 
		case "POPULATE-L3-BOWLER_THIS_MATCH": case "POPULATE-POINTS_TABLE": case "POPULATE-INFOBAR-IDENT": case "POPULATE-LTPOINTS_TABLE":	case "POPULATE-BOWLER_STYLE": case "POPULATE-BATSMAN_STYLE": 
		case "POPULATE-MANHATTAN": case "POPULATE-MATCH_PROMO": case "POPULATE-FF-TEAMS_LOGO": case "POPULATE-L3MATCH_PROMO": case "POPULATE-TIEID-DOUBLE": case "POPULATE-MOSTRUNS": case "POPULATE-MOSTWICKETS": 
		case "POPULATE-MOSTFOURS": case "POPULATE-MOSTSIXES": case "POPULATE-HIGHESTSCORE": case "POPULATE-WORM": case "POPULATE-HOWOUT_QUICK": case "POPULATE-FF-SCHEDULE": case "POPULATE-MINI-BATTINGCARD": 
		case "POPULATE-MINI-BOWLINGCARD": case "POPULATE-L3-THISSERIES": case "POPULATE-FF-THISSERIES": case "POPULATE-FF-LEADERBOARD": case "POPULATE-FF-STATS": case "POPULATE-L3-PLAYERPROFILEBAT": 
		case "POPULATE-FF-PLAYERPROFILEBALL":
			
			if(which_graphic_on_screen == "SCOREBUG" || which_graphic_on_screen == "IDENT") {
			}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") || 
				
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") || 
				 
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				 
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD")) {
			}
			else if(which_graphic_on_screen != "") {
				AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
			}

			switch(whatToProcess.toUpperCase()) {
			case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-DIRECTOR": 
			case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-RIGHT":
				break;
			case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
					break;
				}else {
					scenes.get(0).scene_load(print_writer, broadcaster);
				}
				break;
			default:
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") || 
					
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") || 
					 
					 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 
					 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD")) {
					//AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
				}else {
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,broadcaster);
					print_writer.println("-1 RENDERER*STAGE SHOW 0.0 \0");
				}
				break;
			}
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-FF-LEADERBOARD":
				populateLeaderBoard(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster, config);
				break;
			case "POPULATE-FF-STATS":
				populateFFstats(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-FF-SCORECARD":
				populateScorecard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE") {	
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 BowlingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 SummaryOut 0.500 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PointsTableOut 0.500 \0");
					}
				}
				TimeUnit.SECONDS.sleep(2);
				break;
				
			case "POPULATE-FF-BOWLINGCARD":
				populateBowlingcard(print_writer, valueToProcess.split(",")[0], false, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE") {	
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 BattingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 SummaryOut 0.500 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 PointsTableOut 0.500 \0");
					}
				}
				break;
			
			case "POPULATE-MINI-BATTINGCARD":
				populateMiniBattingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-MINI-BOWLINGCARD":
				populateMiniBowlingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			
			case "POPULATE-FF-PARTNERSHIP":
				populatePartnership(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
				
			case "POPULATE-FF-MATCHSUMMARY":
				populateMatchsummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || 
						which_graphic_on_screen == "POINTSTABLE") {	
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 BattingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 BowlingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 PointsTableOut 0.500 \0");
					}
				}
				break;
			case "POPULATE-L3-BUG-DISMISSAL":
				populateBugDismissal(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-BUG":
				populateBug(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-BUG-BOWLER":
				populateBugBowler(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-BUG-DB":
				for(Bugs bug : cricketService.getBugs()) {
					  if(bug.getBugId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateBugsDB(print_writer, valueToProcess.split(",")[0], bug, match, broadcaster);
					  }
					}
					break;
			case "POPULATE-L3-HOWOUT":
				populateHowout(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-HOWOUT_QUICK":
				populateHowoutquick(print_writer, valueToProcess.split(",")[0],match, broadcaster);
				break;
			case "POPULATE-FF-SCHEDULE":
				populateSchedule(print_writer, valueToProcess.split(",")[0],cricketService.getFixtures(),cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER":
				populateHowoutWithoutFielder(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-NAMESUPER":
				for(NameSuper ns : cricketService.getNameSupers()) {
				  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
					  populateNameSuper(print_writer, valueToProcess.split(",")[0], ns, match, broadcaster);
				  }
				}
				break;
			case "POPULATE-L3-FALLOFWICKET":
				populateFallofWicket(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-L3-TARGET":
				populateTarget(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-LT-POWERPLAY":
				populateLtPowerPlay(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-BUGTARGET":
				populateBugTarget(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-NAMESUPER-PLAYER":
				populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]),cricketService.getAllPlayer(), match, broadcaster);
				break;
			case "POPULATE-FF-MATCHID":
				populateMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-MATCH_PROMO":
				populateMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
						cricketService.getFixtures(),match , broadcaster);
				break;
			case "POPULATE-L3MATCH_PROMO":
				populateLtMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
						cricketService.getFixtures(),match , broadcaster);
				break;
			case "POPULATE-LT-MATCHID":
				//System.out.println(valueToProcess.split(",")[0]);
				populateLTMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-FF-TEAMS_LOGO":
				populateTeamsLogo(print_writer, valueToProcess.split(",")[0],cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-L3-COMPARISION":
				populateComparision(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-LT-PARTNERSHIP":
				populateLTPartnership(print_writer, valueToProcess.split(",")[0], match, broadcaster, config);
				break;
			case "POPULATE-L3-SPLIT":
				populateSplit(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
				break;
			case "POPULATE-L3-BATSMANSTATS":
				populateBatsmanstats(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-BOWLERSTATS":
				populateBowlerstats(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]), cricketService.getTeams(), match, broadcaster);
				break;
			case "POPULATE-L3-PLAYERSUMMARY":
				populateLtBattingSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-BATSMAN_THIS_MATCH":
				populateLtBatsmanThisMatch(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-BOWLER_THIS_MATCH":
				populateLtBowlerThisMatch(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-BOWLERDETAILS":
				populateLtBowlerSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-BOWLERSUMMARY":
				populateLtBowlerDetails(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-TEAMSUMMARY":
				populateTeamSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),match, broadcaster);
				break;
			case "POPULATE-L3-NEXT_TO_BAT":
				populateLtNextToBat(print_writer, valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-LT-PROJECTED":
				populateProjectedScore(print_writer,valueToProcess, match, broadcaster);
				break;
			case "POPULATE-L3-THISSERIES":
				populateThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
						,match, broadcaster);
				break;
			case "POPULATE-FF-THISSERIES":
				populateFFThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
						,match, broadcaster);
				break;
			case "POPULATE-L3-PLAYERPROFILE":
				//System.out.println("valueToProcess = " + valueToProcess);
				for(Statistics stats : cricketService.getAllStats()) {
					//System.out.println("player id = " + stats.getPlayer_id());
					if(stats.getPlayer_id().intValue()== Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						//System.out.println("Match Found");
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populateLTPlayerProfile(print_writer,valueToProcess.split(",")[0],
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
						}
					}
				}
				break;
			case "POPULATE-L3-PLAYERPROFILEBAT":
				
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populateLTPlayerProfileBat(print_writer,valueToProcess.split(",")[0],
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYERPROFILE":					
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,cricketService.getAllPlayer(),match, broadcaster, config);
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYERPROFILEBALL":
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populatePlayerProfileBall(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,cricketService.getAllPlayer(),match, broadcaster, config);
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYINGXI":
				populatePlayingXI(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						match, broadcaster, config);
				break;
			case "POPULATE-FF-DOUBLETEAMS":
				populateDoubleteams(print_writer,valueToProcess, match, broadcaster);
				break;
			case "POPULATE-FF-LANDMARK":
				populateLandMark(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster, config);
				break;
			case "POPULATE-LT-EQUATION":
				populateLtEquation(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-FF-POSITION_LANDMARK":
				populateFFLandMark(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster, config);
				break;
			case "POPULATE-POINTS_TABLE":
				LeagueTable league_table = null;
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
					league_table = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
							new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
				}
				
				populatePointsTable(print_writer, valueToProcess.split(",")[0],league_table.getLeagueTeams(),broadcaster,match);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || 
						which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {	
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsoffsetInIn 1.330 BattingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsoffsetInIn 1.330 BowlingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsoffsetInIn 1.330 SummaryOut 0.500 \0");
					}
				}
				break;
			case "POPULATE-LTPOINTS_TABLE":
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
					//league_table = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
							//new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
				}
				//populateLtPointsTable(print_writer, valueToProcess.split(",")[0], league_table.getLeagueTeams(),match,broadcaster);
				break;
			case "POPULATE-BOWLER_STYLE":
				populateBowlerStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(), cricketService.getTeams(), cricketService.getGrounds(), match, broadcaster);
				break;
			case "POPULATE-BATSMAN_STYLE":
				populateBatsmanStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						Integer.valueOf(valueToProcess.split(",")[2]), cricketService.getAllPlayer(), cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-MANHATTAN":
				populateManhattan(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-WORM":
				populateWorm(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-PREVIOUS_SUMMARY":
				List<MatchAllData> cricket_matches = new ArrayList<MatchAllData>();
				cricket_matches.clear();
				MatchAllData cricket_match = new MatchAllData();
				for(File file :  new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
					@Override
				    public boolean accept(File pathname) {
				        String name = pathname.getName().toLowerCase();
				        return name.endsWith(".xml") && pathname.isFile();
				    }
				})) {
//					cricket_match = (CricketFunctions.populateMatchVariables(cricketService,(MatchAllData) JAXBContext.newInstance(MatchAllData.class).createUnmarshaller().unmarshal(
//									new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + file.getName()))));
						for(Fixture fx : cricketService.getFixtures()) {
							//System.out.println("vtp =" + valueToProcess.split(",")[1]);
							if(fx.getMatchnumber() == Integer.valueOf(valueToProcess.split(",")[1])) {
								if(cricket_match.getMatch().getMatchFileName().replace(".xml", "").equalsIgnoreCase(fx.getMatchfilename()) 
										&& cricket_match.getSetup().getHomeTeam().getTeamId() == fx.getHometeamid() 
										&& cricket_match.getSetup().getAwayTeam().getTeamId() == fx.getAwayteamid())
								{
									//System.out.println("match = " + cricket_match.getMatchFileName().replace(".xml", ""));
									cricket_matches.add(cricket_match);
								}
							}
						}
				}
				populatePreviousSummary(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricket_matches,cricketService.getFixtures(), 
						match, broadcaster);
				
				/*
				 * List<Match> cricket_matches = new ArrayList<Match>(); for(Fixture fx :
				 * cricketService.getFixtures()) { if(fx.getMatchnumber() ==
				 * Integer.valueOf(valueToProcess.split(",")[1])) {
				 * if(match.getMatchIdent().toUpperCase().equalsIgnoreCase(fx.getMatchfilename()
				 * .toUpperCase()) && match.getHomeTeam().getTeamId() == fx.getHometeamid() &&
				 * match.getAwayTeam().getTeamId() == fx.getAwayteamid()) {
				 * cricket_matches.add(match); } } } populatePreviousSummary(print_writer,
				 * valueToProcess.split(",")[0],
				 * Integer.valueOf(valueToProcess.split(",")[1]),cricket_matches,
				 * cricketService.getFixtures(), match, broadcaster);
				 */
				break;
			case "POPULATE-TIEID-DOUBLE":
				populateTieIdDouble(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1],cricketService.getFixtures(),cricketService.getTeams(), 
						match, broadcaster);
				break;
			case "POPULATE-L3-INFOBAR":
				
				infobar.setMiddle_section(valueToProcess.split(",")[1]);
				infobar.setBottom_right_top_section(valueToProcess.split(",")[2]);
				infobar.setBottom_right_bottom_section(valueToProcess.split(",")[3]);
				
				populateInfobar(infobar, print_writer, valueToProcess.split(",")[0],match, broadcaster);
				
				switch(infobar.getBottom_right_bottom_section().toUpperCase()){
				case CricketUtil.OVER:
					processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
					break;
				case "ECONOMY":
					processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
					break;
				case "BOWLINGEND":
					processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
					break;
				}
				infobar.setIdent_section("");
				break;
				//return infobar;
				
			case "POPULATE-INFOBAR-IDENT":
				infobar.setIdent_section(valueToProcess.split(",")[1]);
				populateInfobarIdent(infobar, false, valueToProcess.split(",")[0],print_writer, match, broadcaster);
				//return infobar;
				break;
				
			case "POPULATE-INFOBAR-PROMPT":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						for(InfobarStats ibs : cricketService.getInfobarStats())
	                          if(ibs.getOrder() == Integer.valueOf(valueToProcess)) {
	                        	  infobar.setTop_section("STATISTICS");
	                              populateInfobarFreeText(infobar,false,print_writer, ibs, match, broadcaster);
	                              processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseIn", "START", broadcaster);
	                              processAnimation(print_writer, "Section2$FreeTextSmallIn", "START", broadcaster);
	                          }
	                    }
					}
				}
				break;
					
			case "POPULATE-DIRECTOR":
					populateInfobarDirector(print_writer,valueToProcess,broadcaster);
					break;

			case "POPULATE-INFOBAR-BOTTOMRIGHT":
				if(infobar.getLast_bottom_right_section() != null 
					&& !infobar.getLast_bottom_right_section().trim().isEmpty()) { // Full section to 2 section change on

					infobar.setBottom_right_bottom_section(valueToProcess);
					infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
					processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
					processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
					processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
					infobar.setBottom_right_top_section(CricketUtil.BOWLER);
					infobar = populateVizInfobarRightTop(infobar, false,print_writer, match, broadcaster);

					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonOut", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(200);
					processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
					processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
					switch(infobar.getBottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
						break;
					case "BOWLINGEND":
						processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						break;
					}
				} else if(infobar.getLast_bottom_right_top_section() != null && infobar.getLast_bottom_right_bottom_section() != null 
						&& !infobar.getLast_bottom_right_top_section().trim().isEmpty() 
						&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // Normal change on

					switch(infobar.getLast_bottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
						break;
					case "BOWLINGEND":
						processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setBottom_right_bottom_section(valueToProcess);
					infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
					switch(infobar.getBottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
						break;
					case "BOWLINGEND":
						processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						break;
					}
				}
				infobar.setBottom_right_section("");infobar.setLast_bottom_right_section("");
				break;
			
			case "POPULATE-INFOBAR-RIGHT":
				if(infobar.getLast_bottom_right_section() != null 
					&& !infobar.getLast_bottom_right_section().trim().isEmpty()) { // Normal change on
					
					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonOut", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					}

					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setBottom_right_section(valueToProcess);
					infobar = populateVizInfobarRight(infobar, false,print_writer, match, broadcaster);
					
					switch (infobar.getBottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallIn", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonIn", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					}
					
				} else if(infobar.getLast_bottom_right_top_section() != null && infobar.getLast_bottom_right_bottom_section() != null 
						&& !infobar.getLast_bottom_right_top_section().trim().isEmpty() 
						&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // 2 section to full section change on

					infobar.setBottom_right_section(valueToProcess);
					infobar = populateVizInfobarRight(infobar, false,print_writer, match, broadcaster);
					
					processAnimation(print_writer, "ALL_SECTION$Section4Out", "START", broadcaster);
					processAnimation(print_writer, "ALL_SECTION$Section5Out", "START", broadcaster);
					TimeUnit.SECONDS.sleep(1);

					switch (infobar.getBottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallIn", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonIn", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					}
				}
				infobar.setBottom_right_top_section("");infobar.setLast_bottom_right_top_section("");
				infobar.setBottom_right_bottom_section("");infobar.setLast_bottom_right_bottom_section("");
				break;
				
			case "POPULATE-INFOBAR-TOP":
				if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
					switch(infobar.getLast_top_section().toUpperCase()) {
					case CricketUtil.TOSS:
						processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
						break;
					case "CRR":
						processAnimation(print_writer, "Section2$CurRunRateOut", "START", broadcaster);
						break;
					case "CRR_RRR":
						processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
						break;
					case "FIRST_INNING_SCORE":
						processAnimation(print_writer, "Section2$FistInnScoreOut", "START", broadcaster);
						break;
					case CricketUtil.BOUNDARY:
						processAnimation(print_writer, "Section2$BallsSinceLastBoundaryOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section2$DLSTargetOut", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section2$ExtrasOut", "START", broadcaster);
						break;
					case "EQUATION":
						processAnimation(print_writer, "Section2$EquationOut", "START", broadcaster);
						break;
					case "PROJECTED":
						processAnimation(print_writer, "Section2$ProjectedOut", "START", broadcaster);
						break;
					case "BOUNDARIES":
						processAnimation(print_writer, "Section2$BoundariesOut", "START", broadcaster);
						break;
					case "PARTNERSHIP":
						processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
						break;
					case "LAST_WICKET":
						processAnimation(print_writer, "Section2$LastWicketOut", "START", broadcaster);
						break;
					case CricketUtil.TIMELINE:
						processAnimation(print_writer, "Section2$TimelineOut", "START", broadcaster);
						break;
					case "STATISTICS":
						processAnimation(print_writer, "Section2$FreeTextSmallOut", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setTop_section(valueToProcess);
					populateVizInfobarTop(infobar, false, print_writer, match, broadcaster);
				}else {
					infobar.setTop_section(valueToProcess);
					processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseIn", "START", broadcaster);
					populateVizInfobarTop(infobar, false, print_writer, match, broadcaster);
				}
				break;
			}
			//return JSONObject.fromObject(this_doad).toString();			
			return null;
	}

	
	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException
	{
		
		switch(whichGraphic) {
		case "RESET":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
			break;
		case "SECTION4":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section4In START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section5In START \0");
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainIn START \0");
			break;
		case "SCORECARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
			break;
		case "BOWLINGCARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
			break;
		case "MATCHSUMMARY": case "PREVIOUS_SUMMARY":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
			break;
		case "POINTSTABLE":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
			break;
		case "SECTION2":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section2$Section2BaseIn START \0");
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentIn START \0");
			break;
		case "IN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "MAIN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainIn START \0");
			break;
		case "FF_IN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_In START \0");
			break;
		case "LT_IN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_In START \0");
			break;
		case "SCORE":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_Out START \0");
			break;
		case "BATSMAN_1_HIGHLIGHT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman1Highlight START \0");
			break;
		case "BATSMAN_2_HIGHLIGHT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman2Highlight START \0");
			break;
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "HOWOUT_QUICK": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "MATCHID":
		case "L3PLAYERPROFILE": case "FFPLAYERPROFILE":	case "TEAMLINEUP": case "DOUBLETEAMS": case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH":
		case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE": case "BOWLER_STYLE": case "BATSMAN_STYLE": case "MANHATTAN": case "HOWOUT_WITHOUT": case "MOSTRUNS": case "MOSTWICKETS":
		case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM":  case "MATCH_PROMO": case "PARTNERSHIP": case "L3MATCH_PROMO": case "TEAMS_LOGO": case "TIEID-DOUBLE":
		case "SCHEDULE": case "MINI_BATTINGCARD": case "MINI_BOWLINGCARD": case "THISSERIES": case "FF-THISSERIES": case "LEADERBOARD": case "FF_STATS": case "PLAYERPROFILEBALL":
		case "PLAYERPROFILEBAT":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;
		/*case "SCOREBUG":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*MainIn START \0");
			
			break;*/
		}	
	}
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic) {
		switch(whichGraphic) {
		case "BATBALLSUMMARY_SCORECARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut CONTINUE \0");
			break;
		case "BATBALLSUMMARY_BOWLINGCARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut CONTINUE \0");
			break;
		case "BATBALLSUMMARY_MATCHSUMMARY": case "PREVIOUS_SUMMARY":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut CONTINUE \0");
			break;
		case "POINTSTABLE": 
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut CONTINUE \0");
			break;
		case "ANIMATE-OUT-INFOBAR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainOut START \0");
			break;
		case "ANIMATE-OUT-IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "DIRECTOR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitOut START \0");
			break;
		case "BATSMAN_1_DEHIGHLIGHT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman1Dehighlight START \0");
			break;
		case "BATSMAN_2_DEHIGHLIGHT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman2Dehighlight START \0");
			break;
		case "FF_OUT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_Out START \0");
			break;
		case "LT_OUT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_Out START \0");
			break;
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "HOWOUT_QUICK": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "MATCHID":
		case "L3PLAYERPROFILE": case "FFPLAYERPROFILE":	case "TEAMLINEUP": case "DOUBLETEAMS": case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH":
		case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE": case "BOWLER_STYLE": case "BATSMAN_STYLE": case "MANHATTAN": case "HOWOUT_WITHOUT": case "MOSTRUNS": case "MOSTWICKETS":
		case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM": case "PARTNERSHIP":  case "MATCH_PROMO": case "L3MATCH_PROMO": case "TEAMS_LOGO": case "TIEID-DOUBLE":
		case "SCHEDULE": case "MINI_BATTINGCARD": case "MINI_BOWLINGCARD": case "THISSERIES": case "FF-THISSERIES": case "LEADERBOARD": case "FF_STATS":
		case "PLAYERPROFILEBALL": case "PLAYERPROFILEBAT":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			break;
		}
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_VIZ": case "GPCL":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*"+ animationName + " " + animationCommand +" \0");
			break;
		case "DOAD_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			
			break;
		}
		
	}
	public String resetInfobarAnimation(PrintWriter print_writer,String which_frame) throws InterruptedException {
		
			switch(which_frame.toUpperCase()) {
			case "FF_FRAME":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "FF_OUT");
					which_graphic_on_screen = "SCOREBUG";
				}
				break;
			case "LT_FRAME":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "LT_OUT");
					which_graphic_on_screen = "SCOREBUG";
				}
				break;
			}
		return "";
	}
	
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
	
	public void populateScorecard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {

			int row_id = 0, omo_num = 0;
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
										+ " " + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 1 \0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
							+ match.getSetup().getMatchIdent() + "\0");
					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								//match.getHomeTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
						//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								//match.getAwayTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getTeamName1() + "\0");
					}
					
					Collections.sort(inn.getBattingCard());
					for (BattingCard bc : inn.getBattingCard()) {
						row_id = row_id + 1;
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
						if(bc.getHowOut() == null) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$LeftPlayerName*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$LeftPlayerName$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "retired hurt" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "retired hurt" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
						}
						break;
						default:
	
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.OUT:
							omo_num = 1;
							cont_name = "$Dehighlight";
							break;
						case CricketUtil.NOT_OUT:
							omo_num = 2;
							cont_name = "$Highlight";
							break;
						}
						
						if(bc.getHowOut() == null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow"+
									//row_id+"$BatOmo*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
						}
						else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow"+row_id+
									//"$BatOmo*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
					
						if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*ACTIVE SET 1 \0");
							if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + " (sub - " + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									}
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)){
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)){
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase("timed_out")) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "timed out" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");	
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
								}
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getStatus() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
						}
					}
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll$ExtrasGrp$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll$OversGrp$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
				if(inn.getTotalWickets() >= 10) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
				}
			}
		}
			
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 \0");
		
	}
}
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene,boolean is_this_updating, int whichInning,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateBowlingcard -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBowlingcard -> inning is null");
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
					+ " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType*FUNCTION*Omo*vis_con SET 1 \0");

			//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$BallHeader$MatchId$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
			int row_id = 0, omo_num = 0;
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
							+ match.getSetup().getMatchIdent() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament().toUpperCase() + "\0");
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp*FUNCTION*Omo*vis_con SET " + inn.getBowlingCard().size()+"\0");
					
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						switch (boc.getStatus().toUpperCase()) {
						case (CricketUtil.OTHER + CricketUtil.BOWLER):
							omo_num = 0;
							cont_name = "$Dehighlight";
							break;
						case (CricketUtil.LAST + CricketUtil.BOWLER):
							omo_num = 0;
							cont_name = "$Dehighlight";
							break;
						case (CricketUtil.CURRENT + CricketUtil.BOWLER):
							omo_num = 1;
							cont_name = "$Highlight";
							break;
						}
						
						row_id = row_id + 1;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
								"$RowAnimation$BallOmo*FUNCTION*Omo*vis_con SET " + omo_num +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
								"$RowAnimation$BallOmo" + cont_name +"$BallPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
								"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallOverValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");

						if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow0"
									+ "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Dots" + "\0");
							if(boc.getDots() < 0) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getDots() + "\0");
							}
							
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow0"
									+ "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Maidens" + "\0");
							
							if(boc.getMaidens() < 0) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getMaidens() + "\0");
							}
							
						}
						//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$BallDataGrp$BallRow0$RowAnimation$BallDetailData$BallExtraHead*GEOM*TEXT SET " + "Run" + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$BallDataGrp$BallRow0$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "MAIDENS" + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + "$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallExtraValue*GEOM*TEXT SET " + (boc.getNoBalls() + boc.getWides()) + "\0");
						if(boc.getEconomyRate() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
									"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
									"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + "-" + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + "$RowAnimation$BallOmo" + cont_name +"$ScoreGrp$Figure*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
						
					}
					if(inn.getBowlingCard().size()<=7) {
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$FowGrp*ACTIVE SET 0 \0");
						}
						else{
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$FowGrp*ACTIVE SET 1 \0");
							for(FallOfWicket fow : inn.getFallsOfWickets()) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$"+ fow.getFowNumber() +"*GEOM*TEXT SET "+ fow.getFowRuns() + " \0");
							
							}
							for(int fow_id=inn.getFallsOfWickets().size()+1;fow_id<=10;fow_id++) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow_id + "*ACTIVE SET 0 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow_id + "*ACTIVE SET 0 \0");
							}		
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + " SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BottomInfoGrp$BottomInfoAll$noname$OversGrp$OversValue*GEOM*TEXT SET "+ CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + " \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BottomInfoGrp$BottomInfoAll$noname$ExtrasGrp$ExtrasValue*GEOM*TEXT SET "+ inn.getTotalExtras() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtrasValue" + " SET " + inn.getTotalExtras() + "\0");

					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallTotalScore" + " SET " + inn.getTotalRuns() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BottomInfoGrp$BottomInfoAll$noname$TotalScore*GEOM*TEXT SET "+ inn.getTotalRuns() + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallTotalScore" + " SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BottomInfoGrp$BottomInfoAll$noname$TotalScore*GEOM*TEXT SET "+ inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + " \0");
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 \0");
			TimeUnit.SECONDS.sleep(2);
		}
	}
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateMatchsummary -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateMatchsummary -> inning is null");
		} else {
		int row_id = 0, max_Strap = 0, total_inn = 0;
			String teamname = "";//,teamname_logo=""; 
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			/*for(int i = 1; i <= 4 ; i++) {
				if(i == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 0 \0");
				}
			}*/

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$Bands$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
			
			for(int i = 1; i <= whichInning ; i++) {

				if(i == 1) {
					row_id = 0;
					max_Strap = 5;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 0 \0");
					
				} else {
					row_id = 5;
					max_Strap = 10;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 1 \0");
				}
				row_id = row_id + 1;
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {
					teamname = match.getSetup().getHomeTeam().getTeamName1();
					//teamname_logo  = match.getHomeTeam().getTeamName4();
				} else {
					teamname = match.getSetup().getAwayTeam().getTeamName1();
					//teamname_logo = match.getAwayTeam().getTeamName4();
				}
				
				//print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Rectangle*TEXTURE*IMAGE SET " + logo_path + 
						//teamname_logo + CricketUtil.PNG_EXTENSION + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamFirstName*ACTIVE SET " + "0" + "\0");
				//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						//"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamFirstName*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamLastName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
				
				if(match.getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$TeamNameAll$SumTeamRuns*GEOM*TEXT SET " + match.getMatch().getInning().get(i-1).getTotalRuns() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$TeamNameAll$SumTeamRuns*GEOM*TEXT SET " + match.getMatch().getInning().get(i-1).getTotalRuns() + slashOrDash 
							+ String.valueOf(match.getMatch().getInning().get(i-1).getTotalWickets()) + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$TeamNameAll$OversGrp$SumTeamOvers*GEOM*TEXT SET " 
								+ CricketFunctions.OverBalls(match.getMatch().getInning().get(i-1).getTotalOvers(),match.getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
				
				if(match.getMatch().getInning().get(i-1).getBattingCard() != null) {
					Collections.sort(match.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					
					for(BattingCard bc : match.getMatch().getInning().get(i-1).getBattingCard()) {
						if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							row_id = row_id + 1;
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + bc.getRuns() + "\0");
							
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
							} else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
							if(i == 1 && row_id >= 5) {
								break;
							}else if(i == 2 && row_id >= 10) {
								break;
							}
						}
					}
				}

				for(int j = row_id + 1; j <= max_Strap; j++) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + j + "$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
				}
				
				if(i == 1) {
					row_id = 1;
				}
				else {
					row_id = 6;
				}

				if(match.getMatch().getInning().get(i-1).getBowlingCard() != null) {
					
					Collections.sort(match.getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

					for(BowlingCard boc : match.getMatch().getInning().get(i-1).getBowlingCard()) {
						
						if(boc.getWickets() > 0) {
							row_id = row_id + 1;
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							
							if(i == 1 && row_id >= 5) {
								break;
							}
							else if(i == 2 && row_id >= 10) {
								break;
							}
						}
						
					}
				}
				
				for(int j = row_id + 1; j <= max_Strap; j++) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + j + "$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
				}
			}
			
			if(match.getMatch().getMatchResult() != null) {
				if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
				}
				else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ "MATCH TIED" + "\0");
				}
				else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
						 + match.getMatch().getMatchStatus().toUpperCase() + "\0");
				}
				else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
				}
			}
			else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
						+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
				
				if(match.getSetup().getTargetType() != null) {
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " (VJD)" + "\0");
						
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " (DLS)" + "\0");
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 \0");

				
		
		}
	}
	public void populatePartnership(PrintWriter print_writer, String viz_scene,int whichInning,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populatePartnership -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populatePartnership -> inning is null");
		} else {
			
			int row_id = 0, omo_num = 0,Top_Score = 50;
			float Mult = 322, ScaleFac1 = 0, ScaleFac2 = 0;
			String cont_name= "",Left_Batsman = "",Right_Batsman="";

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$Header$SubHeader*GEOM*TEXT SET " + " " + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {

				//if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					for(int a = 1; a <= inn.getPartnerships().size(); a++){
						ScaleFac1=0;ScaleFac2=0;
						if(inn.getPartnerships().get(a-1).getFirstBatterRuns() > Top_Score) {
							Top_Score = inn.getPartnerships().get(a-1).getFirstBatterRuns();
						}
						if(inn.getPartnerships().get(a-1).getSecondBatterRuns() > Top_Score) {
							Top_Score = inn.getPartnerships().get(a-1).getSecondBatterRuns();
						}
					}

					for (Partnership ps : inn.getPartnerships()) {
						
						row_id = row_id + 1;
						Left_Batsman ="" ; Right_Batsman="";
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == ps.getFirstBatterNo()) {
								Left_Batsman = bc.getPlayer().getTicker_name().toUpperCase();
							}
							else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
								Right_Batsman = bc.getPlayer().getTicker_name().toUpperCase();
							}
						}
						
						if(inn.getPartnerships().size() >= 10) {
							if(ps.getPartnershipNumber()<=inn.getPartnerships().size()) {
								omo_num = 3;
								cont_name = "Highlight";
							}
						}
						else {
							if(ps.getPartnershipNumber()<inn.getPartnerships().size()) {
								omo_num = 3;
								cont_name = "Highlight";
							}
							else if(ps.getPartnershipNumber() >= inn.getPartnerships().size()) {
								omo_num = 2;
								cont_name = "Dehighlight";
							}
						}
						
						ScaleFac1 = ((ps.getFirstBatterRuns())*(Mult/Top_Score)) ;
						ScaleFac2 = ((ps.getSecondBatterRuns())*(Mult/Top_Score)) ;
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + (inn.getBattingCard().size() - 1) + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + inn.getBattingCard().size() + "\0");
						}

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + "$LeftPlayerName*GEOM*TEXT SET " + Left_Batsman + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + "$RightPlayerName*GEOM*TEXT SET " + Right_Batsman + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + "$Bar*FUNCTION*BarValues*Bar_Value__1 SET " + ScaleFac1 + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + "$Bar*FUNCTION*BarValues*Bar_Value__2 SET " + ScaleFac2 + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + "$ScoreGrp$PartnershipRun*GEOM*TEXT SET " + ps.getTotalRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + "$ScoreGrp$PartnershipBalls*GEOM*TEXT SET " + ps.getTotalBalls() + "\0");		
					}
					if(inn.getPartnerships().size() >= 10) {
						row_id = row_id + 1;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + " \0");
					}
					else {
						for (BattingCard bc : inn.getBattingCard()) {
							if(row_id < inn.getBattingCard().size()) {
								if(row_id == inn.getPartnerships().size()) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + " \0");
									if(match.getMatch().getInning().get(whichInning - 1).getTotalOvers() == match.getSetup().getMaxOvers() || match.getMatch().getInning().get(whichInning - 1).getTotalWickets() >= 10 ) {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
									}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || CricketFunctions.getWicketsLeft(match,whichInning) <= 0) {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "STILL TO BAT" +" \0");
									}
								}
								else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase()+" \0");
								}	
							}
							else {
								break;
							}
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BottomInfo$BottomInfoGrp$BottomInfoAll$ExtrasGrp$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BottomInfo$BottomInfoGrp$BottomInfoAll$noname$OversGrp$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET " + inn.getTotalRuns() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 In$DataIn 1.640 In$BallOffsetIn 1.830 In$ManDataIn 0.931 \0");
			
		}
	}
	public void populateTeamsLogo(PrintWriter print_writer,String viz_scene, List<Team> teams ,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "TEAMS" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader1" + " SET " + "POOL A" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader2" + " SET " + "POOL B" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo1" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "INDS" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName01" + " SET " + "INDIAN SAPPHIRES" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo2" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "ENGR" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName02" + " SET " + "ENGLISH REDS" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo3" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "SCOM" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName03" + " SET " + "SCOTTISH MULBERRIES" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo4" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "AMI" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName04" + " SET " + "AMERICAN INDIGOS" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo5" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "AUSG" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName05" + " SET " + "AUSTRALIAN GOLDS" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo6" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "SAE" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName06" + " SET " + "SOUTH AFRICAN EMERALDS" + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo7" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "SLV" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName07" + " SET " + "SRI LANKAN VIOLETS" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo8" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "IRO" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName08" + " SET " + "IRISH OLIVES" + "\0");
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.520  \0");
			
	}
	public void populateBugDismissal(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateBugDismissal -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBugDismissal -> inning is null");
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + " " + "\0");

								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
								}
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + 
											bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + " " + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + 
											bc.getRuns() + "*" + " (" + bc.getBalls() + ")" + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "" + "\0");

								/*if(bc.getStrikeRate() == null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "S/R : " + "-" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "S/R : " + bc.getStrikeRate() + "\0");
								}*/
								
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				
		}
	}
	public void populateBug(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateBug -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBug -> inning is null");
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + " " + "\0");
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + bc.getRuns() +"* "+ "(" + bc.getBalls() + ")" + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
								}
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + "4s : " + bc.getFours() + " 6s : " + bc.getSixes() + "\0");

								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "S/R : " + bc.getStrikeRate() + "\0");
							}
						}
						break;
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + "ECON: " + boc.getEconomyRate() + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + " " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info04*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				
		}
	}	
	public void populateBugBowler(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Bug's inning is null";
		} else {
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + boc.getPlayer().getFirstname().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info03*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				
		}
	}	
	public void populateHowout(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOut's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId()==playerId) {
							
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
							if(bc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
							}
							
							if(bc.getHowOutText() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + " " + "\0");								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");	
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");							
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
	}
	public void populateHowoutquick(PrintWriter print_writer,String viz_scene,MatchAllData match, String broadcaster) 
	{	
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOut's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					
					for (BattingCard bc : inn.getBattingCard()) {
						if(inn.getFallsOfWickets().size() > 0) {
							if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
								}
								
								if (bc.getHowOutText().trim().equalsIgnoreCase("")){
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + " " + "\0");								
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");								
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");	
							}
						}						
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");	
		}
	}
	public void populateHowoutWithoutFielder(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOut's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$All$Out$LogoGrp$LogoAll$LeftBlueBase*TEXTURE*IMAGE SET " + logo_path + match.getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$All$Out$LogoGrp$LogoAll$LeftBlueBase*TEXTURE*IMAGE SET " + logo_path + match.getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId()==playerId) {
							
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
							if(bc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "4s: " + bc.getFours() + " 6s: " + bc.getSixes() + "\0");								
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + (bc.getBalls() + 1) + "\0");							
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
	}	
	public void populateBatsmanstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerStats's inning is null";
		} else {
			int total_inn = 0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, playerId,",", match.getEventFile().getEvents()).split(",");
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
										 inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
								}								
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "*" + "\0");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
								}
								
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + bc.getBalls() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "Dots" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");

								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "4s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + bc.getFours() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "6s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + bc.getSixes() + "\0");

								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "S/R" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + bc.getStrikeRate() + "\0");
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.420 \0");
				
		}	
	}	
	public void populateBowlerstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId,List<Team> team, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerStats's inning is null";
		} else {
			int total_inn = 0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
							 inn.getBowling_team().getTeamName4().toUpperCase() + "\0");

					switch(statsType.toUpperCase()) {
					case CricketUtil.BOWLER:
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								
								if(boc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + boc.getPlayer().getFirstname().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + boc.getPlayer().getSurname().toUpperCase() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + boc.getPlayer().getFirstname().toUpperCase() + "\0");
								}								
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + " " + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "Overs" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");

								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "Dots" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + boc.getDots() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "Extras" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + (boc.getNoBalls() + boc.getWides()) + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "Economy" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + boc.getEconomyRate() + "\0");			
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.420 \0");
				
		}
	}
	public void populateBugsDB(PrintWriter print_writer,String viz_scene, Bugs bug ,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");

			if(bug.getText1() != null && bug.getText2() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + " " + bug.getText2().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");

			}else if(bug.getText1() != null && bug.getText2() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");

			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText2().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				
		}
	}	
	public void populateNameSuper(PrintWriter print_writer,String viz_scene, NameSuper ns ,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + "TLogo" + "\0");
			
			if(ns.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + ns.getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + ns.getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + ns.getFirstname().toUpperCase() + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + ns.getSubLine().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp*ACTIVE SET 0" + "\0");
				
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
	}	
	public void populateNameSuperPlayer(PrintWriter print_writer,String viz_scene, int TeamId, String captainWicketKeeper, int playerId, List<Player> plyr, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			String Home_or_Away="";
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			if(TeamId == match.getSetup().getHomeTeamId()) {
				for(Player hs : match.getSetup().getHomeSquad()) {
					if(playerId == hs.getPlayerId()) {
						Home_or_Away = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/GPCL/Logos/" +
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						if(hs.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + hs.getFirstname().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + hs.getSurname().toUpperCase() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + hs.getFirstname().toUpperCase() + "\0");
						}
					}
				}
			}
			else {
				for(Player as : match.getSetup().getAwaySquad()) {
					if(playerId == as.getPlayerId()) {
						Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/GPCL/Logos/" +
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						if(as.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + as.getFirstname().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + as.getSurname().toUpperCase() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + as.getFirstname().toUpperCase() + "\0");
						}
					}
				}
			}
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + " " + "\0");
			
			switch(captainWicketKeeper.toUpperCase())
			{
			case CricketUtil.CAPTAIN: case CricketUtil.WICKET_KEEPER:
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + ", " + Home_or_Away + "\0");
				break;
			case "PLAYER OF THE MATCH":
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + "\0");
				break;
			case CricketUtil.PLAYER:
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + Home_or_Away + "\0");
				break;
			case "CAPTAIN-WICKETKEEPER":
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "CAPTAIN & WICKETKEEPER" + ", " + Home_or_Away + "\0");
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");	
		}
	}
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats,List<Player> plyer, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		double strike_rate = 0;
		int omo_num = 0;
		String cont_name = "";
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20-I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			

			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");

			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname().toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname().toUpperCase() + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");

			}
			else {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				

				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname().toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname().toUpperCase() + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");

			}
			
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				
				cont_name = "$Dehighlight";
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

				if(plyer.get(plyr.getPlayerId()-1).getBattingStyle() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
							CricketFunctions.getbattingstyle(plyer.get(plyr.getPlayerId()-1).getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");
				
				strike_rate = stats.getRuns() * 100;
				strike_rate = strike_rate/stats.getBalls_faced();
				DecimalFormat df = new DecimalFormat("0.0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
				if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
				}
				
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.700 \0");
			

		} 

	}
	public void populatePlayerProfileBall(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats,List<Player> plyer, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException {
		
		double economy_rate=0;
		int omo_num = 0;
		String cont_name = "";
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20-I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			

			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");

			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\"  + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname().toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname().toUpperCase() + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");

			}
			else {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				

				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname().toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname().toUpperCase() + "\0");
				}

				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");

			}
			
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BOWLER:
				
				cont_name = "$Dehighlight";
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

				
				if(plyer.get(plyr.getPlayerId()-1).getBowlingStyle() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + 
							CricketFunctions.getbowlingstyle(plyer.get(plyr.getPlayerId()-1).getBowlingStyle().toUpperCase())+ "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + " " + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");
														
				economy_rate = stats.getRuns_conceded() / stats.getBalls_bowled();
				economy_rate = economy_rate * 6;
				DecimalFormat df_b = new DecimalFormat("0.00");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
				if(stats.getRuns_conceded() == 0 && stats.getBalls_bowled() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
				}
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.700 \0");
			

		} 

	}
	public static String playerStyle(String ProfileType,String bat_ball_style) {
		String return_value="";
		
		switch(ProfileType) {
		case CricketUtil.BATSMAN:
			if(bat_ball_style.equalsIgnoreCase("RHB")) {
				return_value= "RIGHT HAND BATTER" ;
			}else if(bat_ball_style.equalsIgnoreCase("LHB")) {
				return_value= "LEFT HAND BATTER" ;
			}
			break;
		
		case CricketUtil.BOWLER:
			
			if(bat_ball_style.equalsIgnoreCase("RF")) {
				return_value = "RIGHT ARM FAST" ;
			}else if(bat_ball_style.equalsIgnoreCase("RFM")) {
				return_value= "RIGHT ARM FAST MEDIUM" ;
			}else if(bat_ball_style.equalsIgnoreCase("RMF")) {
				return_value= "RIGHT ARM MEDIUM FAST" ;
			}else if(bat_ball_style.equalsIgnoreCase("RM")) {
				return_value= "RIGHT ARM MEDIUM" ;
			}else if(bat_ball_style.equalsIgnoreCase("RSM")) {
				return_value= "RIGHT ARM SLOW MEDIUM" ;
			}else if(bat_ball_style.equalsIgnoreCase("ROB")) {
				return_value= "RIGHT ARM OFF-BREAK" ;
			}else if(bat_ball_style.equalsIgnoreCase("RLB")) {
				return_value= "RIGHT ARM LEG-BREAK" ;
			}
			else if(bat_ball_style.equalsIgnoreCase("RAB")) {
				return_value= "RIGHT ARM BOWLER" ;
			}
			else if(bat_ball_style.equalsIgnoreCase("LAB")) {
				return_value= "LEFT ARM BOWLER";
			}
			else if(bat_ball_style.equalsIgnoreCase("LF")) {
				return_value= "LEFT ARM FAST" ;
			}else if(bat_ball_style.equalsIgnoreCase("LFM")) {
				return_value= "LEFT ARM FAST MEDIUM" ;
			}else if(bat_ball_style.equalsIgnoreCase("LMF")) {
				return_value= "LEFT ARM MEDIUM FAST" ;
			}else if(bat_ball_style.equalsIgnoreCase("LM")) {
				return_value= "LEFT ARM MEDIUM" ;
			}else if(bat_ball_style.equalsIgnoreCase("LSL")) {
				return_value= "SLOW LEFT ARM" ;
			}else if(bat_ball_style.equalsIgnoreCase("WSL")) {
				return_value= "LEFT ARM WRIST SPIN" ;
			}else if(bat_ball_style.equalsIgnoreCase("LCH")) {
				return_value= "LEFT ARM CHINAMAN" ;
			}else if(bat_ball_style.equalsIgnoreCase("RLG")) {
				return_value= "RIGHT ARM LEG-BREAK" ;
			}else if(bat_ball_style.equalsIgnoreCase("WSR")) {
				return_value= "RIGHT ARM WRIST SPIN" ;
			}else if(bat_ball_style.equalsIgnoreCase("LSO")) {
				return_value= "LEFT ARM ORTHODOX" ;
			}
			break;
		}
		return return_value ;
	}
	public void populateLTPlayerProfile(PrintWriter print_writer,String viz_scene,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		double economy_rate=0,bowler_strike_rate=0;
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20-I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			

		Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
			}
			
		}
		else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
			}
		}
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BOWLER:
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
			
			economy_rate = stats.getRuns_conceded() / stats.getBalls_bowled();
			economy_rate = economy_rate * 6;
			DecimalFormat df_b = new DecimalFormat("0.00");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON" + "\0");

			if(stats.getRuns_conceded() == 0 && stats.getBalls_bowled() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df_b.format(economy_rate) + "\0");
			}
			
			bowler_strike_rate = stats.getBalls_bowled() / stats.getWickets();
			DecimalFormat df_bs = new DecimalFormat("0.0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "S/R" + "\0");
			if(stats.getWickets() == 0 || stats.getBalls_bowled() == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + df_bs.format(bowler_strike_rate) + "\0");
			}
			break;	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");	
		

		}

	}
	public void populateLTPlayerProfileBat(PrintWriter print_writer,String viz_scene,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		double strike_rate = 0;
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20-I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			

		Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
			}
		}
		else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname().toUpperCase() + "\0");
			}
		}
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
			
			if(stats.getThirties() == null &&  stats.getFifties() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "30s/50s" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "0/0" + "\0");

			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "30s/50s" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + stats.getThirties()+"/"+stats.getFifties() + "\0");
			}
			
			strike_rate = stats.getRuns() * 100;
			strike_rate = strike_rate/stats.getBalls_faced();
			DecimalFormat df = new DecimalFormat("0.0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "S/R" + "\0");
			if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");

			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + df.format(strike_rate) + "\0");
			}
			
			break;	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");	
		

		}

	}
	public Player getPlayerFromMatchData(int plyr_id, MatchAllData match)
	{
		for(Player plyr : match.getSetup().getHomeSquad()) {
			if(plyr_id == plyr.getPlayerId()) { 
				return plyr;
			}
		}
		for(Player plyr : match.getSetup().getAwaySquad()) {
			if(plyr_id == plyr.getPlayerId()) { 
				return plyr;
			}
		}
		for(Player plyr : match.getSetup().getHomeOtherSquad()) {
			if(plyr_id == plyr.getPlayerId()) { 
				return plyr;
			}
		}
		for(Player plyr : match.getSetup().getAwayOtherSquad()) {
			if(plyr_id == plyr.getPlayerId()) { 
				return plyr;
			}
		}
		return null;
	}
	public void populateDoubleteams(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateDoubleteams -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateDoubleteams -> inning is null");
		} else {
			System.out.println("IN populateDoubleteams");
			String cont = "";
			int row_id = 0, omo = 0;
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "TEAMS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$noname$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
			
			for(int i = 1; i <= 2 ; i++) {
				if(i == 1) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamNameGrp1$RowAnimation$TeamNameGrp$NameAll$TeamFirstName*GEOM*TEXT SET " 
							+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName1" + " SET " + " " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamRefName" + " SET " + logo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
							+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
					
					for(Player hs : match.getSetup().getHomeSquad()) {
						row_id = row_id + 1;
						omo = 0;
						cont = "Dehighlight";
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo + "\0");

						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + " " + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getFull_name().toUpperCase() + " (WK)" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getFull_name().toUpperCase() + " (C & WK) " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");
						}
					}
				} else {
					row_id = 0;
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamNameGrp2$RowAnimation$TeamNameGrp$NameAll$TeamFirstName*GEOM*TEXT SET " 
											+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName2" + " SET " + " " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
							+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");

					for(Player as : match.getSetup().getAwaySquad()) {
						row_id = row_id + 1;
						omo = 0;
						cont = "Dehighlight";
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo + "\0");

						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getFull_name().toUpperCase() + " (WK) " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getFull_name().toUpperCase() + " (C & WK) " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + " " + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
					}
				}
			}
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
						" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
						" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.590 \0");	
		}
	}
	public void hideAndShowContainer(String broadcaster, String which_graphics, PrintWriter print_writer) {
		
		switch (which_graphics.toUpperCase()) {
		case "INFOBAR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0" + "\0");
			break;
		}
	}
	public Infobar populateInfobarIdent(Infobar infobar, boolean is_this_updating, String viz_scene,PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster)
	{
	    switch(infobar.getIdent_section().toUpperCase()) {
	    case CricketUtil.TOSS:
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "\0");
	    	
	    	if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
	    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
						+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
						+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
			}
	    	
	    	
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
									+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");

	    	break;
	    
	    case "RESULT":
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "\0");
	    	//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
					//+ match.getMatchResult().toUpperCase() + "\0");
	    	
	    	for(Inning inn : match.getMatch().getInning()) {
	    		if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
	    			if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
							}
							
						}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
								|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
							}
						}
						else{
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
								" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
							
						}
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
								
							}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
								
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
									}
								}
							}
							
							else{
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");									
							}
						}
						else {
							if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
							}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| Double.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), match.getMatch().getInning().get(1).getTotalBalls())) 
									>= Double.valueOf(match.getSetup().getTargetOvers())) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");											
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");						
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
							}
							else{
								if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (VJD)" + "\0");
								}
								else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (DLS)" + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
								}
							}
						}
					}
	    		}
	    	}
	    	
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    
	    case "TARGET":
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "\0");

	    	for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
								+ "TARGET" + " " + CricketFunctions.GetTargetData(match).getTargetRuns() + " (VJD)" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
								+ "TARGET" + " " + CricketFunctions.GetTargetData(match).getTargetRuns() + " (DLS)" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
								+ "TARGET" + " " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
					}
				}
			}
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    
	    case "VENUE":
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "\0");
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
					+ match.getSetup().getVenueName().toUpperCase() + "\0");
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    
	    case "TOURNAMENT":
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "\0");
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
					+ match.getSetup().getTournament().toUpperCase() + "\0");
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    }
	    
		return infobar;
	}
	public Infobar populateInfobar(Infobar infobar, PrintWriter print_writer,String scene, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Infobar's inning is null";
		} else {
			
			infobar = populateInfobarTeamScore(infobar,false, print_writer, match, broadcaster);
			infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, broadcaster);
			infobar = populateVizInfobarRightTop(infobar, false, print_writer, match, broadcaster);
			infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
		}
		return infobar;
	}
	public Infobar populateInfobarTeamScore(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster)
	{
	    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + " " + "\0");
    	
		for(Inning inn : match.getMatch().getInning()) {
			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET "  + "IMAGE*/Default/GPCL/Logos/" + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET "  + "IMAGE*/Default/GPCL/Logos/" + 
							inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatTeamName" + " SET " + inn.getBatting_team().getTeamName2().toUpperCase() + "\0");
				}
			    
				if(inn.getTotalWickets() >= 10) {
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + " SET " + inn.getTotalRuns() + "\0");
				}
				else{
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + " SET " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + "\0");
				}
			    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamOvers" + " SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
			    
			    if(match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.DLS) || match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.VJD)) {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + 
			    			"(" + match.getSetup().getTargetOvers() + ") " + match.getSetup().getTargetType().toUpperCase() + "\0");
			    }else {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + " " + "\0");
			    }
			    
			    if(!match.getSetup().getTargetOvers().isEmpty() && Double.valueOf(match.getSetup().getTargetOvers()) == 1) {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$BatTeamNameAndScoreGrp$noname$PowerPlay*ACTIVE SET 0 \0");
			    }else {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$BatTeamNameAndScoreGrp$noname$PowerPlay*ACTIVE SET 1 \0");
			    	if(!CricketFunctions.processPowerPlay(CricketUtil.MINI,match).isEmpty()) {
						 if(infobar.isPowerplay_on_screen() == true) {
							 break;
				         }
				         else {
				        	 infobar.setPowerplay_on_screen(true);
				        	 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START \0");
				         }
					}
					else {
						if(infobar.isPowerplay_on_screen() == true) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayOut START \0");
							infobar.setPowerplay_on_screen(false);
				         }
					}
			    }
			}
		}
			
		return infobar;
	}
	public Infobar populateVizInfobarMiddle(Infobar infobar, boolean is_this_updating, PrintWriter print_writer,MatchAllData match, String broadcaster)
	{ 
		List<BattingCard> current_batsmen = new ArrayList<BattingCard>();
		switch (infobar.getMiddle_section().toUpperCase()) {
		case CricketUtil.BATSMAN:
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					for (BattingCard bc : inn.getBattingCard()) {
						if(inn.getPartnerships() != null && inn.getPartnerships().size() > 0) {
							if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								current_batsmen.add(bc);
							} else if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								current_batsmen.add(bc);
							}
						}
					}
					
					populateCurrentBatsmen(infobar,print_writer, match, broadcaster,current_batsmen);
					
					if(current_batsmen != null && current_batsmen.size() >= 1) {
						infobar.setLast_batsmen(current_batsmen);
					}
				}
			}
			break;
		}
		return infobar;
	}
	public Infobar populateCurrentBatsmen(Infobar infobar, PrintWriter print_writer, MatchAllData match, String broadcaster,List<BattingCard> current_batsmen)
	{
		for(Inning inn : match.getMatch().getInning()) {
			
			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				
				if(current_batsmen != null && current_batsmen.size() >= 2) {
		
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName1 SET " + current_batsmen.get(0).getPlayer().getTicker_name().toUpperCase() + "\0");
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanScore1 SET " + current_batsmen.get(0).getRuns() + "\0");
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanBall1 SET " + current_batsmen.get(0).getBalls() + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName2 SET " + current_batsmen.get(1).getPlayer().getTicker_name().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanScore2 SET " + current_batsmen.get(1).getRuns() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanBall2 SET " + current_batsmen.get(1).getBalls() + "\0");
					
					if(current_batsmen.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike*FUNCTION*Omo*vis_con SET " + "1" + " \0");
						}
					}
					if(current_batsmen.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike*FUNCTION*Omo*vis_con SET " + "2" + " \0");
						}	
					}
					if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
						processAnimation(print_writer, "Batsman1Dehighlight", "SHOW 0.260", broadcaster);
					} else if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						processAnimation(print_writer, "Batsman1Highlight", "SHOW 0.160", broadcaster);
					}
					if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
						processAnimation(print_writer, "Batsman2Dehighlight", "SHOW 0.260", broadcaster);
					} else if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						processAnimation(print_writer, "Batsman2Highlight", "SHOW 0.160", broadcaster);
					}
				}
			}
		}
			
		infobar.setLast_batsmen(current_batsmen);
		return infobar;
	}
	public Infobar populateVizInfobarRight(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster) 
	{
		
		switch(infobar.getBottom_right_section().toUpperCase()) {
		case CricketUtil.DOT:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDotBallHead" + " SET " + "DOTS THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDotBallCounter" + " SET " + 
					CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, inn.getInningNumber(), 0, ",", match.getEventFile().getEvents()).split(",")[0] + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.DOT);	
			break;
		case CricketUtil.FOUR:
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterHead" + " SET " + "FOURS THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterCounter" + " SET " + inn.getTotalFours() + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.FOUR);
			break;
		case CricketUtil.SIX:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterHead" + " SET " + "SIXES THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterCounter" + " SET " + inn.getTotalSixes() + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.SIX);
			break;
		case CricketUtil.COMPARE:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tComparisonTeam" + " SET " + 
							inn.getBowling_team().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tComparisonRuns" + " SET " +
							CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.COMPARE);
			break;
		case "TARGET_2":
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Section4_N_5$Target02$Target$TargetHead*ACTIVE SET 1 \0");
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase(CricketUtil.VJD)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + " (" + CricketUtil.VJD + ")" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase(CricketUtil.DLS)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + " (" + CricketUtil.DLS + ")" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
					}
				}
			}
			infobar.setLast_bottom_right_section("TARGET_2");
			break;
		case "TOURNAMENT-NAME":	

			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Section4_N_5$Target02$Target$TargetHead*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + "GPCL 2022" + "\0");
					
			infobar.setLast_bottom_right_section("TOURNAMENT-NAME");
			break;
		}		
			
		return infobar;
	}
	public Infobar populateVizInfobarRightTop(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, 
			MatchAllData match, String broadcaster) throws InterruptedException
	{
		
		switch(infobar.getBottom_right_top_section().toUpperCase()) {
		case CricketUtil.BOWLER:
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BowlerbaseGrp$TopLIne*ACTIVE SET " + "0" + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") 
								|| boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
								processAnimation(print_writer, "ALL_SECTION$Section4In", "CONTINUE", broadcaster);
								processAnimation(print_writer, "ALL_SECTION$Section5In", "CONTINUE", broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBowlerName SET " + boc.getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBowlerFigure SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBowlerOvers SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
								processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
								processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
							}
							infobar.setLast_bowler(boc);
							infobar.setLast_bottom_right_top_section(CricketUtil.BOWLER);
						}
					}
				}
			}
			break;	
		}
		return infobar;
	}
	public Infobar populateVizInfobarRightBottom(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, 
			MatchAllData match, String broadcaster)
	{
		switch(infobar.getBottom_right_bottom_section().toUpperCase()) {
		case CricketUtil.OVER:
			int Player_id=0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){						
					
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)) {
							Player_id = boc.getPlayerId();
						}
					}
					
					String[] this_over = CricketFunctions.getEventsText(CricketUtil.OVER,Player_id,",", match.getEventFile().getEvents(),0).split(",");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vThisOver" + " SET " + this_over.length + "\0");

					for(int i=0;i < this_over.length;i++) {

						if(this_over[i].toUpperCase().equalsIgnoreCase("WD+W") || this_over[i].toUpperCase().equalsIgnoreCase("W") 
								|| this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.FOUR) || this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.SIX)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
									+ (i+1) + "*FUNCTION*Omo*vis_con SET 3 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
						}else if(this_over[i].toUpperCase().equalsIgnoreCase("WD") || this_over[i].toUpperCase().equalsIgnoreCase("NB")
								 || this_over[i].toUpperCase().contains("B") || this_over[i].toUpperCase().contains("LB") || this_over[i].toUpperCase().contains("Pn")) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
									+ (i+1) + "*FUNCTION*Omo*vis_con SET 5 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
						} else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
									+ (i+1) + "*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
						}
					}
				}
			}
			infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
			break;
		case "ECONOMY":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
							if(boc.getEconomyRate() == null) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerEconomy" + " SET " + "-" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerEconomy" + " SET " + boc.getEconomyRate() + "\0");
							}
						}
					}
				}
			}
			infobar.setLast_bottom_right_bottom_section("ECONOMY");
			break;
		case "BOWLINGEND":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER) || (boc.getStatus().equalsIgnoreCase(CricketUtil.LAST + CricketUtil.BOWLER))) {
							if(boc.getBowling_end() == 1) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlingEnd" + " SET " + 
										match.getSetup().getGround().getFirst_bowling_end().toUpperCase() + " END"+ "\0");
							}else if(boc.getBowling_end() == 2) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlingEnd" + " SET " + 
										match.getSetup().getGround().getSecond_bowling_end().toUpperCase() + " END" + "\0");
							}
						}
					}
				}
			}
			infobar.setLast_bottom_right_bottom_section("BOWLINGEND");
			break;
		}
		return infobar;
	}
	public Infobar populateVizInfobarTop(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster)
	{		
		if(is_this_updating == false) {
			hideAndShowContainer(broadcaster, "INFOBAR", print_writer);
		}
		
		switch(infobar.getTop_section().toUpperCase()) {
		case "CRR":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR$CurRunRate$CURRHead*GEOM*TEXT SET " + 
							"CURRENT RUN RATE" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR$CurRunRate$CURRValue*GEOM*TEXT SET " + 
							inn.getRunRate() + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 1"+"\0");
				processAnimation(print_writer, "Section2$CurRunRateIn", "START", broadcaster);
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$CurRunRateIn START \0");
			}
			break;
		case "VS_BOWLING_TEAM":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$BowlingTeamIn START \0");
					}
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp$TeamName2*GEOM*TEXT SET " + 
							inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
				}
			}
			break;
		case "CRR_RRR":
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$CURRHead*GEOM*TEXT SET " + 
							"CURRENT RUN RATE" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$CURRValue*GEOM*TEXT SET " + 
							inn.getRunRate() + "\0");
				}
			}
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$REQRValue*GEOM*TEXT SET " + 
			                       CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, 
			                    		   CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + "\0");
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 1"+"\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*CRR_RRRIn START \0");
				processAnimation(print_writer, "Section2$CRR_RRRIn", "START", broadcaster);
			}
			
			break;
		case "EXTRAS":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tExtrasHead" + " SET " + "EXTRAS - " + 
							inn.getTotalExtras() + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tWide" + " SET " + inn.getTotalWides() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNoBalls" + " SET " + inn.getTotalNoBalls() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBye" + " SET " +  inn.getTotalByes() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLegBye" + " SET " + inn.getTotalLegByes() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPenalties" + " SET " + inn.getTotalPenalties() + "\0");
					
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ExtrasIn START \0");
				processAnimation(print_writer, "Section2$ExtrasIn", "START", broadcaster);
			}
			break;
		case "FIRST_INNING_SCORE":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 1) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistInnTeam" + " SET " + 
							inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistInnScore" + " SET " +
							inn.getTotalRuns() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistInnOvers" + " SET " + 
							"(" + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ")" + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FistInnScoreIn START \0");
				processAnimation(print_writer, "Section2$FistInnScoreIn", "START", broadcaster);
			}
			break;
		
		case CricketUtil.BOUNDARY:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallSinceLastBoundary" + " SET " + 
							CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber()) + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*BallsSinceLastBoundaryIn START \0");
				processAnimation(print_writer, "Section2$BallsSinceLastBoundaryIn", "START", broadcaster);
			}
			break;
			
		case CricketUtil.TOSS:
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_HEAD*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
						" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$LeftBlueBase*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Team*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$Saperator*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Result*ACTIVE SET " + "0" + "\0");

			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_HEAD*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
						" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$LeftBlueBase*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Team*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$Saperator*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Result*ACTIVE SET " + "0" + "\0");
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$TossIn START \0");
				processAnimation(print_writer, "Section2$TossIn", "START", broadcaster);
			}
			break;
		case "TARGET":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + " (VJD)" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + " (DLS)" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + " " + "\0");
					}
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 1" + "\0");
				processAnimation(print_writer, "Section2$DLSTargetIn", "START", broadcaster);
			}
			break;
		case "EQUATION":
			if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours*ACTIVE SET 0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + 
					CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
			}
			else{
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours*ACTIVE SET 1" + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED" + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours$1$NEEDRUNS*GEOM*TEXT SET " + 
						CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRunHead" + " SET " + "RUN" +
						CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
				if(!match.getSetup().getTargetOvers().equalsIgnoreCase("")) {
				//if(!match.getTargetOvers().equalsIgnoreCase("") || Double.valueOf(match.getTargetOvers()) > 0) {
					if(match.getSetup().getTargetOvers().contains(".")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
								((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1])-
										(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
								CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1])-
										(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
								((Integer.valueOf(match.getSetup().getTargetOvers())*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
								CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers())*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + "\0");
					}
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
							((match.getSetup().getMaxOvers()*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
							CricketFunctions.Plural(((match.getSetup().getMaxOvers()*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + "\0");
				}
				
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 1"+"\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$EquationIn START \0");
				processAnimation(print_writer, "Section2$EquationIn", "START", broadcaster);
			}

			break;
		case "PROJECTED":
			String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
		    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
		    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
	        }
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedHead1" + " SET " + "@" + proj_score_rate[0] +" (CRR)" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedValue1" + " SET " + proj_score_rate[1] + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedHead2" + " SET " + "@" + proj_score_rate[2] + " RPO" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedValue2" + " SET " + proj_score_rate[3] + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedHead3" + " SET " + "@" + proj_score_rate[4] + " RPO" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedValue3" + " SET " + proj_score_rate[5] + "\0");
			
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$ProjectedIn START \0");
				processAnimation(print_writer, "Section2$ProjectedIn", "START", broadcaster);
			}
			
			break;
		case "BOUNDARIES":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFoursValue" + " SET " + inn.getTotalFours() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixValue" + " SET " + inn.getTotalSixes() + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$BoundariesIn START \0");
				processAnimation(print_writer, "Section2$BoundariesIn", "START", broadcaster);
			}
			
			break;
		case "PARTNERSHIP":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartnershipSmallRuns" + " SET " 
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartnershipSmallBalls" + " SET " 
										+ "(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartFoursValue" + " SET " 
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalFours() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartSixValue" + " SET " 
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalSixes() + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$PartnershipIn START \0");
				processAnimation(print_writer, "Section2$PartnershipIn", "START", broadcaster);
			}
			
			break;
		case "LAST_WICKET":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
					for(BattingCard bc : inn.getBattingCard()){
						if(inn.getFallsOfWickets().size() > 0){
							if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketPlayerName" + " SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketBalls" + " SET " + "(" + bc.getBalls() + ")" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketRuns" + " SET " + bc.getRuns() + "\0");
							}
						}								
					}
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 1"+"\0");
				processAnimation(print_writer, "Section2$LastWicketIn", "START", broadcaster);
			}
			
			break;
		case CricketUtil.TIMELINE:
			String this_ball_data="";
			int ball_count=0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(((inn.getTotalOvers()*6) + inn.getTotalBalls()) > 22) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " + "22" + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " 
											+ ((inn.getTotalOvers()*6) + inn.getTotalBalls()) + "\0");
					}
						
					if ((match.getEventFile().getEvents() != null) && (match.getEventFile().getEvents().size() > 0)) {
						  for (int i=match.getEventFile().getEvents().size() - 1; i>=0; i--)
						  {  
							
							switch(match.getEventFile().getEvents().get(i).getEventType()) {
							case CricketUtil.CHANGE_BOWLER: case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
							case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: 
							case CricketUtil.PENALTY: case CricketUtil.LOG_WICKET: case CricketUtil.LOG_ANY_BALL:
								ball_count = ball_count + 1;
								switch (match.getEventFile().getEvents().get(i).getEventType())
							    {
							    case CricketUtil.CHANGE_BOWLER:
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 0 \0");
									break;
							    case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 2 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
									break;
							    case CricketUtil.FOUR: case CricketUtil.SIX: 
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
									break;
							    case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY: 
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 5 \0");
							    	if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.WIDE)) {
							    		this_ball_data = "WD";
							    	}else if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.NO_BALL)) {
							    		this_ball_data = "NB";
							    	}else if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.LEG_BYE)) {
							    		this_ball_data = "LB";
							    	}else if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.BYE)) {
							    		this_ball_data = "B";
							    	}else if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.PENALTY)) {
							    		this_ball_data = "P";
							    	}
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
											(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + this_ball_data.toUpperCase() + "\0");
									break;
							    case CricketUtil.LOG_WICKET: 
							    	if (match.getEventFile().getEvents().get(i).getEventRuns() > 0) {
							    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 15 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
												String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns()) + "+W" + "\0");
							      } else {
							    	  print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 15 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + "W" + "\0");
							      }
							      break;
							    case CricketUtil.LOG_ANY_BALL:
							    	if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
							    		this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "Pn";
							    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 5 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
							    	}else {
							    		if(match.getEventFile().getEvents().get(i).getEventExtra() != null) {
								    		if(match.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.WIDE)){
								    			if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns()+
									    					match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "WD";
								    			}else {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns()) + "WD";
								    			}
								    		}
								    		else if(match.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
							    				if(match.getEventFile().getEvents().get(i).getEventRuns()>0) {
							    					this_ball_data = "NB" + "+" + match.getEventFile().getEvents().get(i).getEventRuns() ;
							    				}else {
							    					this_ball_data = "NB" ;
							    				}
							    			}else {
							    				if(match.getEventFile().getEvents().get(i).getEventRuns()>0) {
							    					this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns());
							    				}
							    			}
								    	}
							    		
							    		if(match.getEventFile().getEvents().get(i).getEventSubExtra() != null && match.getEventFile().getEvents().get(i).getEventSubExtraRuns()>0) {
								    		if(!match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
								    			if(this_ball_data.isEmpty()) {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventSubExtraRuns());
								    			}else {
								    				this_ball_data = this_ball_data + "+" + match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
								    			}
								    			if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
									    			this_ball_data = this_ball_data + "NB";
									    		}else if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.LEG_BYE)) {
									    			this_ball_data = this_ball_data + "LB";
									    		}else if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.BYE)) {
									    			this_ball_data = this_ball_data + "B";
									    		}//else if(match.getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
									    			//System.out.println("HELLO8");
									    			//this_ball_data = this_ball_data + "Pn";
									    		//}
								    		}
							    		}
							    		if (match.getEventFile().getEvents().get(i).getEventHowOut() != null && !match.getEventFile().getEvents().get(i).getEventHowOut().isEmpty()) {
								    		this_ball_data = this_ball_data + "+W";
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 15 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
								    	}else {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 5 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
								    	}
							    	}	
							    }
								break;
							}
								
						    if(ball_count >= 22) {
						    	break;
						    }
						  }
						}
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 1" + "\0");
				processAnimation(print_writer, "Section2$TimelineIn", "START", broadcaster);
			}
			break;
		}
			
		infobar.setLast_top_section(infobar.getTop_section());
		return infobar;
	}
	public Infobar populateInfobarFreeText(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, InfobarStats ibs, MatchAllData match, String broadcaster)
	{	
		if(is_this_updating == false) {
			hideAndShowContainer(broadcaster, "INFOBAR", print_writer);
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 1" + "\0");
		}

		if(ibs.getText1() != null && ibs.getText2() != null) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " + 
					ibs.getText1() + "-" + ibs.getText2() + "\0");
		}else if(ibs.getText1() != null) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " + ibs.getText1() + "\0");
		}else {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " + ibs.getText2() + "\0");
		}
		
		infobar.setLast_top_section(infobar.getTop_section().toUpperCase());
			
		return infobar;
	}
	public void populateInfobarDirector(PrintWriter print_writer,String Dir_value,String session_selected_broadcaster) {
		
		switch (Dir_value.toUpperCase()) {
		case "FOURS":
			processAnimation(print_writer, "FourIn", "START", session_selected_broadcaster);
			break;

		case "SIXES":
			processAnimation(print_writer, "SixIn", "START", session_selected_broadcaster);
			break;
		
		case "WICKETS":
			processAnimation(print_writer, "WicketIn", "START", session_selected_broadcaster);
			break;

		case "FREE-HIT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitIn START \0");
			break;
		}
	}
	
	public void populateMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET "+ " " + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
			
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ 
					fix.get(match_number - 1).getMatchfilename().toUpperCase() + " \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ "LIVE FROM "+ match.getSetup().getVenueName().toUpperCase() + " \0");
			
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$HomeTeamName_Grp$FirstName*GEOM*TEXT SET " + TM.getTeamName1().toUpperCase() + " \0");
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$AwayLogoGrp$AwayTeamName_Grp$FirstName*GEOM*TEXT SET " + TM.getTeamName1().toUpperCase() + " \0");
				}
			}
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ "TOMORROW" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ "TOMORROW " + "- LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + " \0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ "UP NEXT" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + " \0");
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 2.474 \0");
				
		}
	}
	public void populateMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET "+ " " + " \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$HomeTeamName_Grp$FirstName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$AwayLogoGrp$AwayTeamName_Grp$FirstName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ "" + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getMatchIdent() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ "LIVE FROM "+ match.getSetup().getVenueName().toUpperCase() + " \0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 \0");
				
		}
		
	}
	public void populateLTMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + match.getSetup().getMatchIdent() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.120 \0");
				
		}
	}
	public void populateLtMatchPromo(PrintWriter print_writer,String viz_scene, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + TM.getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + TM.getTeamName3().toUpperCase() + "\0");
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + TM.getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + TM.getTeamName3().toUpperCase() + "\0");
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "TOMORROW - " + 
							 fix.get(match_number - 1).getMatchfilename().toUpperCase() + "\0");
				
				
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "UP NEXT - " + 
						fix.get(match_number - 1).getMatchfilename().toUpperCase() + "\0");
			
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.120 \0");
				
		}
	}
	public void populatePlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,MatchAllData match, String broadcaster, Configuration config) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0,omo=0;
			String cont = "";
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
					+ " " + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
					+ match.getSetup().getMatchIdent() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$SubHeader*GEOM*TEXT SET " 
					+ match.getSetup().getTournament().toUpperCase() + " \0");
			
			if(TeamId == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

				for(Player hs : match.getSetup().getHomeSquad()) {
					row_id = row_id + 1;
					omo = 0;
					cont = "Dehighlight";
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$BottomData$StrikeRate*GEOM*TEXT SET " 
											+ " " + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
							+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET "+ omo + " \0");

					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
					}
					
					
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$ImageGrp$PlayerImage*TEXTURE*IMAGE SET "+ photo_path 
										//+ match.getHomeTeam().getTeamName4().toUpperCase() + "\\" + hs.getFirstname() + CricketUtil.PNG_EXTENSION + " \0");

					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ hs.getTicker_name().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 1 + " \0");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ hs.getTicker_name().toUpperCase() + "(WK)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ hs.getTicker_name().toUpperCase() + "(C & WK)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ hs.getTicker_name().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					
				}
			}
			
			else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

				for(Player as : match.getSetup().getAwaySquad()) {
					row_id = row_id + 1;
					omo = 0;
					cont = "Dehighlight";
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
							"$BottomData$StrikeRate*GEOM*TEXT SET " + " " + " \0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
							+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET "+ omo + " \0");

					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
					}
					
					
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$ImageGrp$PlayerImage*TEXTURE*IMAGE SET "+ 
							//photo_path + match.getHomeTeam().getTeamName4().toUpperCase() + "\\" + as.getFirstname() + CricketUtil.PNG_EXTENSION + " \0");

					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ as.getTicker_name().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 1 + " \0");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ as.getTicker_name().toUpperCase() + " (WK)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ as.getTicker_name().toUpperCase() + "(C & WK)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ as.getTicker_name().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
				}
				
			}
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BottomInfoGrp$BottomInfo$Equations*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
						" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BottomInfoGrp$BottomInfo$Equations*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
						" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$Images_In 1.800 \0");
		
		
	}
	
	public void populateBugTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster)
	{
		switch (broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + " " + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + CricketFunctions.GetTargetData(match).getRemaningBall() + "\0");						
					}
				}
			}
			
			break;
		}
	}
	public void populateLtPowerPlay(PrintWriter print_writer, String viz_scene, MatchAllData match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					if(inn.getTotalWickets() < 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					}
					if(CricketFunctions.getBallCountStartAndEndRange(match, inn).get(1) >= (inn.getTotalOvers()*6)) {
						
						/*print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$PP1$noname$StatValue1B*GEOM*TEXT SET " +  + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$PP1$noname$StatValue1B*GEOM*TEXT SET " +  + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$PP1$noname$StatValue1B*GEOM*TEXT SET " +  + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$PP1$noname$StatValue1B*GEOM*TEXT SET " +  + "\0");*/
					}
					else if ((CricketFunctions.getBallCountStartAndEndRange(match, inn).get(3) >= (inn.getTotalOvers()*6)) || 
							(CricketFunctions.getBallCountStartAndEndRange(match, inn).get(2) <= (inn.getTotalOvers()*6))) {
						
					}
					
				}
			}
			
			
		}
	}
	public void populateLtBatsmanThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{

		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				for(BattingCard bc : inn.getBattingCard()) {
					if (inn.getInningNumber() == whichInning) {
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
						if(PlayerId == bc.getPlayerId()) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								

							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + " " + "\0");								
							/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
							} else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
							}*/
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead02*GEOM*TEXT SET " + "RUNS" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead03*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + "BALLS" + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1c*GEOM*TEXT SET " + bc.getBalls() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + "S/R" + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + "FOURS" + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + bc.getFours() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " + "SIXES" + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " + bc.getSixes() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue5$StatValue1B*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue5$StatValue1B*GEOM*TEXT SET " + " " + "\0");
						}
					}
				}
			}
			
		}
		
	}
	public void populateLtBowlerThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					for (BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getPlayerId()==PlayerId) {
							/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
						}*/
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead02*GEOM*TEXT SET " + boc.getOvers() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1A*GEOM*TEXT SET " + "DOTS" + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + boc.getDots() + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + boc.getRuns() + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getWickets() + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
						}
					}	
				}
			}
			
		}
	}
	public void populateLtNextToBat(PrintWriter print_writer, String viz_scene, MatchAllData match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					for(BattingCard bc : inn.getBattingCard()) {
						if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							//System.out.println(inn.getBowlingCard().get(inn.getBattingCard().size() -1).getPlayer().getFirstname());
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$Player1FirstName*GEOM*TEXT SET " + inn.getBowlingCard().get(inn.getBattingCard().size() -1).getPlayer().getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$Player1FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$Player1LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$AT1*GEOM*TEXT SET " + bc.getBatterPosition() + "\0");

							//print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$SRPlayer1*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");

							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$Player1FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$Player1FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$Player1LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$AT2*GEOM*TEXT SET " + bc.getBatterPosition() + "\0");
						}
							//print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$SRPlayer2*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");
					}
				}
				
			}
			
		}
	}
	public void populateLtBowlerDetails(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerStats's inning is null";
		} else {
			int total_inn = 0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==PlayerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead02*GEOM*TEXT SET " + boc.getOvers() + "\0");								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + boc.getDots() + "\0");								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + boc.getRuns() + "\0");								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getWickets() + "\0");								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
							}
						}
						break;
				}
			}
				
		}
	}
	public void populateLandMark(PrintWriter print_writer,String viz_scene, int whichInning, String statType, int playerId, MatchAllData match, String session_selected_broadcaster, Configuration config)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			//String Home_or_Away="";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					switch(statType.toUpperCase()) {
					case "BATSMAN":
						for(BattingCard bc : inn.getBattingCard()) {
							if(playerId == bc.getPlayerId()) {
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$FirstName*GEOM*TEXT SET "+ bc.getPlayer().getFirstname().toUpperCase() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$FirstName*GEOM*TEXT SET "+ bc.getPlayer().getFirstname().toUpperCase() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$LastName*GEOM*TEXT SET "+ bc.getPlayer().getSurname().toUpperCase() + " \0");
								if(bc.getStatus().equals(CricketUtil.OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Runs*GEOM*TEXT SET "+ bc.getRuns() + " \0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Runs*GEOM*TEXT SET "+ bc.getRuns() + "*" + " \0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Balls*GEOM*TEXT SET "+ bc.getBalls() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatHead*GEOM*TEXT SET "+ "S/R " + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ bc.getStrikeRate() + " \0");
								if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
													match.getSetup().getHomeTeam().getTeamName1() + ".png" + "\0");
									
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
													match.getSetup().getAwayTeam().getTeamName1() + ".png" + "\0");
								}
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Player_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
											photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + bc.getPlayer().getFirstname()+ CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Player_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
											"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
								}
								
							}
						}
						
						break;
					case "BOWLER":
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(playerId == boc.getPlayerId()) {
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$FirstName*GEOM*TEXT SET "+ boc.getPlayer().getFirstname().toUpperCase() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$FirstName*GEOM*TEXT SET "+ boc.getPlayer().getFirstname().toUpperCase() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$LastName*GEOM*TEXT SET "+ boc.getPlayer().getSurname().toUpperCase() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Runs*GEOM*TEXT SET "+ boc.getRuns() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Balls*GEOM*TEXT SET "+ boc.getBalls() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatHead*GEOM*TEXT SET "+ "ECO " + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ boc.getEconomyRate() + " \0");
								if(inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
													match.getSetup().getHomeTeam().getTeamName1() + ".png" + "\0");
									
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
													match.getSetup().getAwayTeam().getTeamName1() + ".png" + "\0");
								}
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Player_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
											photo_path + boc.getPlayer().getFirstname() + ".png" + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + boc.getPlayer().getFirstname()+ CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Player_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
											"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + boc.getPlayer().getFirstname() + ".png" + "\0");
								}
							}
						}
						break;
					}
				}
			}
				
		}
	}
	public void populateFFLandMark(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, MatchAllData match, String session_selected_broadcaster, Configuration config)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			//String Home_or_Away="";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					for(BattingCard bc : inn.getBattingCard()) {
						if(playerId == bc.getPlayerId()) {
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$MaxSize$PlayerName*GEOM*TEXT SET "+ bc.getPlayer().getFirstname().toUpperCase() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$text$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ bc.getBatterPosition() + " \0");
							
							if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
												match.getSetup().getHomeTeam().getTeamName1() + ".png" + "\0");
								
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
												match.getSetup().getAwayTeam().getTeamName1() + ".png" + "\0");
							}
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Plaer_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
										photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + bc.getPlayer().getFirstname()+ CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Plaer_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
										"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
							}
							
						}
					}
				}
			}
				
		}
	}
	public void populateLtPointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> point_table, MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		int row_id=0;
		for(int i = 0; i <= point_table.size()-1; i++) {
			row_id = row_id + 1;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row1$RowAni$Data$BowlerName*GEOM*TEXT SET "+ point_table.get(0).getTeamName().toUpperCase() + " \0");
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 0 \0");
			}
			else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 1 \0");
			}
			if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) || 
					match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*FUNCTION*Omo*vis_con SET 1 \0");
			}
			else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*FUNCTION*Omo*vis_con SET 0 \0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$BowlerName*GEOM*TEXT SET "+ point_table.get(i).getTeamName().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$OversValue*GEOM*TEXT SET "+ point_table.get(i).getPlayed() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$MaidensValue*GEOM*TEXT SET "+ point_table.get(i).getWon() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$RunsValue*GEOM*TEXT SET "+ point_table.get(i).getLost() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$EconomyValue*GEOM*TEXT SET "+ point_table.get(i).getPoints() + " \0");

		}
			
	}
	
	
	public void populateProjectedScore(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: ProjectedScore's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");
			
			String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
		    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
		    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
	        }
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ inn.getBatting_team().getTeamName2().toUpperCase() + " \0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "@"+ proj_score_rate[0] +" (CRR)" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + proj_score_rate[1] + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "@" + proj_score_rate[2] +" RPO" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + proj_score_rate[3] + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "@" + proj_score_rate[4] +" RPO" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + proj_score_rate[5] + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");
			
		}
		
	}
	public void populateTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Target's inning is null";
		} else {
			for(Inning inn : match.getMatch().getInning()) {
				

				//if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/GPCL/Logos/" + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
							 // " TO WIN FROM " +  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + "\0");
					
					if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(match.getSetup().getMaxOvers()*6 >= 100) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
									 " TO WIN FROM "+  match.getSetup().getMaxOvers() + " OVERS" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
									 " TO WIN FROM "+  match.getSetup().getMaxOvers()*6 + " BALLS" + "\0");
						}
						
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) != 0) {
							if(match.getSetup().getTargetOvers().contains(".")) {
								if((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  (Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) + " BALLS" + "\0");
								}
							}else {
								if(Double.valueOf(match.getSetup().getTargetOvers())*6 >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS" + "\0");
								}
							}
						}
						if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
							if(match.getSetup().getTargetOvers().contains(".")) {
								if((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (VJD)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  (Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) + " BALLS (VJD)" + "\0");
								}
							}else {
								if(Double.valueOf(match.getSetup().getTargetOvers())*6 >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (VJD)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS (VJD)" + "\0");
								}
							}
						}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
							if(match.getSetup().getTargetOvers().contains(".")) {
								if((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (DLS)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  (Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) + " BALLS (DLS)" + "\0");
								}
							}else {
								if(Double.valueOf(match.getSetup().getTargetOvers())*6 >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (DLS)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS (DLS)" + "\0");
								}
							}
						}
					}	
					
				//}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
		
			
	}
	public void populateTeamSummary(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + " " + "\0");								

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, whichInning, 0, ",", match.getEventFile().getEvents()).split(",");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");								
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$noname$FOW*ACTIVE SET " + "0" + "\0");								

						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + "\0");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1A SET " + "0s" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1B SET " + Count[0] + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2B SET " + Count[1] + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3B SET " + Count[2] + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4B SET " + Count[3] + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue5B SET " + inn.getTotalFours() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue6B SET " + inn.getTotalSixes() + "\0");

				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.380 \0");
			
		}
	}
	public void populateLtBattingSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + " " + "\0");								

			for(Inning inn : match.getMatch().getInning()) {
				for(BattingCard bc : inn.getBattingCard()) {
					if (inn.getInningNumber() == whichInning) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

							if(PlayerId == bc.getPlayerId()) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFull_name().toUpperCase() + "\0");								
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$noname$FOW*GEOM*TEXT SET " + "BATTING SUMMARY" + "\0");								

								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "*" + "\0");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + bc.getBalls() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1A SET " + "0s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1B SET " + Count[0] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2B SET " + Count[1] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3B SET " + Count[2] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4B SET " + Count[3] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue5B SET " + Count[4] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue6B SET " + Count[6] + "\0");
							}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.380 \0");
			
		}
	}
	public void populateLtBowlerSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + " " + "\0");								
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "BOWLER SUMMARY" + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BOWLER,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBowling_team().getTeamName4().toUpperCase() + "\0");

							if(PlayerId == boc.getPlayerId()) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + boc.getPlayer().getFull_name().toUpperCase() + "\0");								
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1A SET " + "0s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1B SET " + Count[0] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2B SET " + Count[1] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3B SET " + Count[2] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4B SET " + Count[3] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue5B SET " + Count[4] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue6B SET " + Count[6] + "\0");
							}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.380 \0");
			
		}
	}
	public void populateFallofWicket(PrintWriter print_writer,String viz_scene,int whichInning, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");
	
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET "+ inn.getTotalRuns() + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET "+ inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + " \0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET "+ CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " \0");
	
					if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$FOW$BottomLine$noname*ACTIVE SET 0" + " \0");
					}
					else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
						for(FallOfWicket fow : inn.getFallsOfWickets()) {								
							if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$FOW$BottomLine$noname*ACTIVE SET 1" + " \0");
								for(int fow_id=1;fow_id<=10;fow_id++) {
									if(fow_id <= inn.getFallsOfWickets().size()) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow.getFowNumber() + "A" + " SET " + fow.getFowNumber() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow.getFowNumber() + "B" + " SET " + fow.getFowRuns() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow_id + "A" + " SET " + " " + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow_id + "B" + " SET " + " " + "\0");
									}
								}	
							}		
						}
					}
					
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.600 \0");
			
		}
	
	}
	public void populateSplit(PrintWriter print_writer,String viz_scene,int whichInning,int splitValue, MatchAllData match, String broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/GPCL/Logos/" + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					if(inn.getTotalWickets() >=10) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET "+ inn.getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET "+ inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET "+ CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");

					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 30 || inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 50) {
						if(splitValue == 30) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER THIRTY" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER FIFTY" + "\0");
						}

						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$HeadValue1$Dehiglight$StatHead1*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + " \0");
					
					} else {
						if(splitValue == 30) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER THIRTY" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER FIFTY" + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$HeadValue1$Dehiglight$StatHead1*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + "\0");
						
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$noname*ACTIVE SET 0" + "\0");
					
				    for (int i = 0; i < CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size(); i++) {
				    	
				    	int row_id = i + 1;
				    	for(int split_id=1;split_id<=6;split_id++) {
					    	if(split_id <= CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size()) {
					    		print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$noname*ACTIVE SET 1" + "\0");
					    		if(row_id==1) {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"st"+ "\0");
					    		}else if(row_id==2){
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"nd"+ "\0");
					    		}else if(row_id==3) {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"rd"+ "\0");
					    		}else {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"th"+ "\0");
					    		}
							
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "B" + " SET " + 
										CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).get(i) + "\0");
					    	}
					    	else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + split_id + "A" + " SET " + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + split_id + "B" + " SET " + " " + "\0");
					    	}
				    	}
			        }
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.600 \0");
				
		}
		
	}	
	public void populateComparision(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Target's inning is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamRefName" + " SET " + logo_path + inn.getBowling_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + inn.getBowling_team().getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + inn.getBowling_team().getTeamName3().toUpperCase()  + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + inn.getBatting_team().getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamScore" + " SET " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamScore" + " SET " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + "\0");

				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.120 \0");
			
		}	
	}
	public void populateLTPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String broadcaster, Configuration config) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					
					String Left_Batsman ="",Right_Batsman="";
					
					for (Player hs : match.getSetup().getHomeSquad()) {
						if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
							Left_Batsman = hs.getFull_name().toUpperCase();
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
							}
							
						}
						if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
							Right_Batsman = hs.getFull_name().toUpperCase();
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + photo_path + 
										inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
							}
							
						}
					}
					
					for (Player as : match.getSetup().getAwaySquad()) {
						if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
							Left_Batsman = as.getFull_name().toUpperCase();
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path  + 
										inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
							}
						}
						if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
							Right_Batsman = as.getFull_name().toUpperCase();
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + photo_path + 
										inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path  + 
										inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
							}
						}
					}

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
							+ inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$PartnershipData$Runs$Alignment$PartnershipScore*GEOM*TEXT SET " 
											+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$PartnershipData$Balls$Alignment$PartnershipBalls*GEOM*TEXT SET " 
											+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row1$RowAnimation$RowData$PlayerName1*GEOM*TEXT SET " + Left_Batsman + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row1$RowAnimation$RowData$PlayerName2*GEOM*TEXT SET " + Right_Batsman + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore1$PlayerContributionRuns1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore1$PlayerContributionBalls1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore2$PartnershipRun*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore2$PartnershipBalls*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row3$RowAnimation$Highlight$Alignment$Fours$PlayerBalls1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalFours() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row3$RowAnimation$Highlight$Alignment$Sixes$PlayerBalls1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalSixes() + "\0");

					if(inn.getTotalWickets() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
												+ (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
												+ (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 2) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
												+ (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
												+ (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$PartDataIn 1.800 In$PartDataIn$DataIn 1.800 \0");
			
		}
	}
	
	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_no=0;

			switch(StatType.toUpperCase()) {
			case "MOST_RUNS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST RUNS " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {
						
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
							
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			case "MOST_WICKETS":
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST WICKETS " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getWickets() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			case "MOST_FOURS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST FOURS " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getFours() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			case "MOST_SIXES":
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST SIXES " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\GPCL\\Photos\\" + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getSixes() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.180 \0");
			
		}
	}
	
	public void populateLtEquation(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + " " + "\0");
			
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/GPCL/Logos/" + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					
					if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
							}
							
						}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
								|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
							
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
							}
						}
						
						else{
							if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET "  
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
							}
							
						}
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
								
							}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
								
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
									}
								}
							}
							
							else{
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");									
							}
						}
						else {
							if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
							}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| Double.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), match.getMatch().getInning().get(1).getTotalBalls())) 
									>= Double.valueOf(match.getSetup().getTargetOvers())) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");											
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");						
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
							}
							else{
								if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
												+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
												" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (VJD)" + "\0");
								}
								else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET "  
												+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
												" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (DLS)" + "\0");
								}
								else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
												+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
												" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
								}
							}
						}
					}
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
		
	}	
	public void populatePointsTable(PrintWriter print_writer,String viz_scene,List<LeagueTeam> point_table, String broadcaster,MatchAllData match) throws InterruptedException 
	{
		int row_id=0,omo_num = 0;
		String cont_name = "";
		DecimalFormat df = new DecimalFormat("0.000");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead1" + " SET " + " " + "\0");
		//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead1" + " SET " + "GROUP - " + point_table.get(0).getPool().trim() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead2" + " SET " + "POINTS TABLE" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$BottomInfoGrp$RowAnimation$PointsInfo*GEOM*TEXT SET "+ 
				" " + " \0");

		for(int i = 0; i <= point_table.size() - 1 ; i++) {
			row_id = row_id + 1;
			
			if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())  
					|| match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) 
					|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
				omo_num = 1;
				cont_name = "$Highlight";
			}else {
				omo_num = 0;
				cont_name = "$Dehighlight";
			}
			//System.out.println(point_table.get(i).getTeamName().toUpperCase());
			//System.out.println(omo_num);
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + 
					"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
			
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + 
						"$RowAnimation$Dehighlight$Qualified*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + 
						"$RowAnimation$Highlight$Qualified*ACTIVE SET 0 \0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + 
						"$RowAnimation$Dehighlight$Qualified*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + 
						"$RowAnimation$Highlight$Qualified*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tQualified" + row_id + " SET " + "Q" + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
					+ cont_name + "$TextAll$Rank*GEOM*TEXT SET "+ (i+1) + "." + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$PoinTeamName*GEOM*TEXT SET "+ point_table.get(i).getTeamName().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$PlayedValue*GEOM*TEXT SET "+ point_table.get(i).getPlayed() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$WinValue*GEOM*TEXT SET "+ point_table.get(i).getWon() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$LossValue*GEOM*TEXT SET "+ point_table.get(i).getLost() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$NrValue*GEOM*TEXT SET "+ point_table.get(i).getNoResult() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$PointsValue*GEOM*TEXT SET "+ point_table.get(i).getPoints() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$NRRValue*GEOM*TEXT SET "+ df.format(point_table.get(i).getNetRunRate()) + " \0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsOffsetIn 1.330 \0");
		
	}
	public void populateBowlerStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId,List<Player> plyr, List<Team> team,List<Ground> ground, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			//String Home_or_Away="";
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName4().toUpperCase() + "\0");
			
			if(plyr.get(playerId - 1).getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.get(playerId - 1).getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getFirstname().toUpperCase() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + team.get(plyr.get(playerId-1).getTeamId()-1).getTeamName1() + "\0");
			
			if(plyr.get(playerId - 1).getBowlingStyle() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + 
						CricketFunctions.getbowlingstyle(plyr.get(playerId - 1).getBowlingStyle()).toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
			
	}	
	public void populateTieIdDouble(PrintWriter print_writer,String viz_sence_path,String day,List<Fixture> fix,List<Team>team,MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			int row_id = 1;
			String Date = "";
			Calendar cal = Calendar.getInstance();
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + "" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + match.getSetup().getTournament() + "\0");
			if(day.toUpperCase().equalsIgnoreCase("TODAY")) {
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "TODAY'S MATCHES " + "\0");
			}
			else if(day.toUpperCase().equalsIgnoreCase("TOMORROW")) {
				cal.add(Calendar.DATE, +1);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "TOMORROW'S MATCHES " + "\0");
			}
			
			for(int i = 0; i <= fix.size()-1; i++) {
				if(fix.get(i).getDate().equalsIgnoreCase(Date)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + row_id + " SET " + logo_path + team.get(fix.get(i).getHometeamid()-1).getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + row_id + " SET " + team.get(fix.get(i).getHometeamid()-1).getTeamName1().toUpperCase() + "\0");
					
					if(fix.get(i).getMatchnumber() == 13) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfoMatch" + row_id + " SET " + "SEMI-FINAL 1" + "\0");
					}else if(fix.get(i).getMatchnumber() == 14) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfoMatch" + row_id + " SET " + "SEMI-FINAL 2" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfoMatch" + row_id + " SET " + "MATCH " + 
								fix.get(i).getMatchnumber() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + row_id + " SET " + logo_path + team.get(fix.get(i).getAwayteamid()-1).getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + row_id + " SET " + team.get(fix.get(i).getAwayteamid()-1).getTeamName1().toUpperCase() + "\0");

					row_id = row_id +1;
				}
			}
			
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.100 \0");
			
		}
		
	}
	public void populatePreviousSummary(PrintWriter print_writer, String viz_scene, int match_number,List<MatchAllData> mtch,List<Fixture> fix, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Match Summary's inning is null";
		} else {
		 
			for(int j = 0; j <= mtch.size() - 1; j++) {
				int row_id = 0, max_Strap = 0;
				String teamname = "";
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
						+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$Bands$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
						+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
				
				for(int i = 1; i <= 2 ; i++) {

					if(i == 1) {
						row_id = 0;
						max_Strap = 5;
						
						if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
									"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
									"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 0 \0");
						
					} else {
						row_id = 5;
						max_Strap = 10;
						
						if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
									"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
									"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 1 \0");
					}
					row_id = row_id + 1;
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + mtch.get(j).getSetup().getMatchIdent() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$SubHeader*GEOM*TEXT SET " + mtch.get(j).getSetup().getTournament().toUpperCase() + "\0");
					
					if(mtch.get(j).getMatch().getInning().get(i-1).getBattingTeamId() == mtch.get(j).getSetup().getHomeTeamId()) {
						teamname = mtch.get(j).getSetup().getHomeTeam().getTeamName1();
						//teamname_logo  = match.getHomeTeam().getTeamName4();
					} else {
						teamname = mtch.get(j).getSetup().getAwayTeam().getTeamName1();
						//teamname_logo = match.getAwayTeam().getTeamName4();
					}
					
					//print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Rectangle*TEXTURE*IMAGE SET " + logo_path + 
							//teamname_logo + CricketUtil.PNG_EXTENSION + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
							"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamFirstName*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
							"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamLastName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
					
					if(mtch.get(j).getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$TeamNameAll$SumTeamRuns*GEOM*TEXT SET " + mtch.get(j).getMatch().getInning().get(i-1).getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$TeamNameAll$SumTeamRuns*GEOM*TEXT SET " + mtch.get(j).getMatch().getInning().get(i-1).getTotalRuns() + slashOrDash 
								+ String.valueOf(mtch.get(j).getMatch().getInning().get(i-1).getTotalWickets()) + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$TeamNameAll$OversGrp$SumTeamOvers*GEOM*TEXT SET " 
									+ CricketFunctions.OverBalls(mtch.get(j).getMatch().getInning().get(i-1).getTotalOvers(),mtch.get(j).getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
					
					if(mtch.get(j).getMatch().getInning().get(i-1).getBattingCard() != null) {
						Collections.sort(mtch.get(j).getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
						
						for(BattingCard bc : mtch.get(j).getMatch().getInning().get(i-1).getBattingCard()) {
							if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								row_id = row_id + 1;
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + bc.getRuns() + "\0");
								
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
								} else {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								
								if(i == 1 && row_id >= 5) {
									break;
								}else if(i == 2 && row_id >= 10) {
									break;
								}
							}
						}
					}

					for(int k = row_id + 1; k <= max_Strap; k++) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + k + "$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
					}
					
					if(i == 1) {
						row_id = 1;
					}
					else {
						row_id = 6;
					}

					if(mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard() != null) {
						
						Collections.sort(mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

						for(BowlingCard boc : mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard()) {
							
							if(boc.getWickets() > 0) {
								row_id = row_id + 1;
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + "$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								
								if(i == 1 && row_id >= 5) {
									break;
								}
								else if(i == 2 && row_id >= 10) {
									break;
								}
							}
						}
					}
					
					for(int k = row_id + 1; k <= max_Strap; k++) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + k + "$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
					}
				}
				if(mtch.get(j).getMatch().getMatchResult() != null) {
					if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
					}
					else if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								+ "MATCH TIED" + "\0");
					}
					else if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								+ mtch.get(j).getMatch().getMatchStatus().toUpperCase() + "\0");
					}
					else if(mtch.get(j).getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								+ "MATCH TIED - " + CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
					}
				}
				else {
					if(mtch.get(j).getSetup().getTargetType() == "") {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
					}
					else if(mtch.get(j).getSetup().getTargetType() != null) {
						if(mtch.get(j).getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + " (VJD)" + "\0");
						}
						else if(mtch.get(j).getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + " (DLS)" + "\0");
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 \0");
			}
				
		}
	}
	public void populateBatsmanStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, List<Player> plyr, List<Team> team, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/GPCL/Logos/" + 
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName4().toUpperCase() + "\0");

			if(plyr.get(playerId - 1).getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.get(playerId - 1).getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getFirstname().toUpperCase() + "\0");
			}
			
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + team.get(plyr.get(playerId-1).getTeamId()-1).getTeamName1() + "\0");
			
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + team.get(plyr.get(playerId - 1).getTeamId() - 1).
					//getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
			
			if(plyr.get(playerId - 1).getBattingStyle() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + 
						CricketFunctions.getbattingstyle(plyr.get(playerId - 1).getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			}
			
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");	
			
	}
	public void populateManhattan(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		}else if(whichInning == 0) {
			this.status = "ERROR: Inning is null";
		}else {
			
			int maxRuns = 0,runsIncr = 0;
			long lngth = 0;
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ " " + " \0");				
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ match.getSetup().getMatchIdent() + " \0");				

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ inn.getBatting_team().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tExtrasValue" + " SET "  + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOversValue" + " SET "  + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET "  + inn.getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET "  + inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
					}
				}
			}
			
			for (int j = 0; j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size(); j++) {
				if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getInningNumber() == whichInning) {
					if(Integer.valueOf(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns()) > maxRuns){
						maxRuns = Integer.valueOf(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns()); // 33 runs came off 34th over
					}
					
				 	while (maxRuns % 5 != 0) {     // 5 label in y-axis
				 		maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
					}
				}
			}
				
			for(int i =0; i < 5;i++) {
				runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36
		 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$lines$PlayerNameGrp$Row" + (5 - i) + "$RowAni$Runs*GEOM*TEXT SET " + runsIncr*(i+1) + "\0");
			}
			
			for(int j = 0; j <= match.getSetup().getMaxOvers(); j++) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll$Bar" + (j+1) + "*ACTIVE SET 0" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$Wickets$Wkt" + (j+1) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");

				//if(CricketFunctions.getOverByOverData(match, whichInning,match.getEvents()).get(j).getInningNumber() == whichInning) {
					
				if(j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size()) {
					lngth = ((35 *Integer.valueOf(
							CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns())) / maxRuns); // 32 is max value of each bar
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll$Bar" + (j+1) + "*ACTIVE SET 1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll*FUNCTION*BarValues*Bar_Value__" + (j+1) + " SET " + lngth + "\0");
				
					if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$Wickets$Wkt" + (j+1) + "*FUNCTION*Omo*vis_con SET " + Integer.valueOf(
								CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$Wickets$Wkt" + (j+1) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
					}
				
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll$Bar" + (j+1) + "*ACTIVE SET 0" + "\0");
				}
					
				//}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.700 In$BallOffsetIn 1.830 In$ManDataIn 2.700 In$ManDataIn$DataIn 1.576 \0");
		
			
	}
	public void populateWorm(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		}else if(whichInning == 0) {
			this.status = "ERROR: Inning is null";
		}
		else {
			
			String teamname = "";
			int maxRuns = 0,runsIncr = 0,row_id = 0;
			double Lngth = 0;
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ " " + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getMatchIdent() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ 
					CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "TLogo" + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp1$Band*MATERIAL*COLOR SET 1.0 0.227450980392 0.0549019607843 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2$Band*MATERIAL*COLOR SET 1.0 0.827450980392 0.0 \0");
			
			List<String> overByOverRuns = new ArrayList<String>();
			for(int inn_count = 1; inn_count <= whichInning; inn_count++)
			{
				overByOverRuns.clear();
				for(OverByOverData Over : CricketFunctions.getOverByOverData(match,inn_count ,"WORM" ,match.getEventFile().getEvents())) {
					overByOverRuns.add(String.valueOf(Over.getOverTotalRuns()));
				}
				//System.out.println(overByOverRuns);
				String cumm_runs = String.valueOf(0) + "," + String.join(",", overByOverRuns); // Store Per Overs Runs
				
				
				if(match.getMatch().getInning().get(0).getTotalRuns() > match.getMatch().getInning().get(1).getTotalRuns()) {
					maxRuns = match.getMatch().getInning().get(0).getTotalRuns();
				}
				else {
					maxRuns = match.getMatch().getInning().get(1).getTotalRuns();
				}
				if(maxRuns % 5 == 0) {
					maxRuns = maxRuns + 1;
				}
				while (maxRuns % 5 != 0) {     // 5 label in y-axis
					maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
				}
				
				if(match.getMatch().getInning().get(inn_count-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {            
					teamname = match.getSetup().getHomeTeam().getTeamName4().toUpperCase();
					
				} else {
					teamname = match.getSetup().getAwayTeam().getTeamName4().toUpperCase();
				}
				
				for(int k = 0; k < 5; k++) {           // For Y-Axis Value 
					runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36	
				 	print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$group$PlayerNameGrp$Row" + (5 - k) + 
				 			"$RowAni$Runs*GEOM*TEXT SET " + runsIncr *  (k + 1) + "\0");
				}
				
				row_id = row_id + 1;
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + 
						"$TextAll$TeamName*GEOM*TEXT SET "+ teamname + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Score*GEOM*TEXT SET "+ 
									match.getMatch().getInning().get(inn_count-1).getTotalRuns() + "-" + match.getMatch().getInning().get(inn_count-1).getTotalWickets() + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Overs*GEOM*TEXT SET "+ 
									CricketFunctions.OverBalls(match.getMatch().getInning().get(inn_count-1).getTotalOvers(), match.getMatch().getInning().get(inn_count-1).getTotalBalls()) + " \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXFit SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXFit SET 1 \0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXOffset SET 11.5 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXOffset SET 11.5 \0");
				
				Lngth =  (80.62 / maxRuns); // 100 is max value of each bar
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXScale" + " SET " + "0.8" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXScale" + " SET " + "0.8" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vDataScaleY" + " SET " + Lngth + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
						"*GEOM*DataY SET " + cumm_runs.replaceFirst("0,", "") + " \0");
				//System.out.println(cumm_runs);
				if(inn_count == 1) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 0 \0");
				}
				else {						
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 1 \0");
				}
				
				for (int j = 0; j <= match.getSetup().getMaxOvers() - 1; j++) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$Wkt" + 
							(j+1) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");

					if(j < CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).size()) {
						if(CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
									"$Wkt" + (j+1) + "*FUNCTION*Omo*vis_con SET " + Integer.valueOf(CricketFunctions.getOverByOverData
											(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
									"$Wkt" + (j+1) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
						}
					}
				}
			}	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.780 \0");
		
			
	}
	public void populateSchedule(PrintWriter print_writer,String viz_scene,List<Fixture> fixture,List<Team> team,MatchAllData match ,String broadcaster) throws ParseException {
		
		int row_id = 0,omo_num=0;
		String Date = "",cont_name="";
		Calendar cal = Calendar.getInstance();
		Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + " " +"\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "SCHEDULE" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
		
		for(int i=0;i < fixture.size();i++ ) {
			row_id = row_id + 1;
			if(fixture.get(i).getDate().equalsIgnoreCase(Date)) {
				omo_num=1;
				cont_name="$Highlight";		
			}else {
				omo_num=0;
				cont_name="$Dehighlight";
			}

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
			if(fixture.get(i).getMatchnumber() % 2 == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
						"$RowAnimation$RowOmo" + cont_name + "$DateAll$TimeOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
						"$RowAnimation$RowOmo" + cont_name + "$DateAll$TimeOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo" + cont_name + "$DateAll$DateText*GEOM*TEXT SET " + fixture.get(i).getDate() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo" + cont_name + "$TextAll$NameAll$TeamName1*GEOM*TEXT SET " + team.get(fixture.get(i).getHometeamid()-1).getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo" + cont_name + "$TextAll$NameAll$TeamName2*GEOM*TEXT SET " + team.get(fixture.get(i).getAwayteamid()-1).getTeamName1().toUpperCase() + "\0");
			
			if(fixture.get(i).getWinnerteam() != null) {
				if(fixture.get(i).getWinnerteam().toUpperCase().equalsIgnoreCase(team.get(fixture.get(i).getHometeamid()-1).getTeamName4().toUpperCase())) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
							"$RowAnimation$RowOmo" + cont_name + "$TextAll$ResultOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
				}else if(fixture.get(i).getWinnerteam().toUpperCase().equalsIgnoreCase(team.get(fixture.get(i).getAwayteamid()-1).getTeamName4().toUpperCase())) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
							"$RowAnimation$RowOmo" + cont_name + "$TextAll$ResultOmo*FUNCTION*Omo*vis_con SET " + "2" + "\0");
				}
			}	
			else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
						"$RowAnimation$RowOmo" + cont_name + "$TextAll$ResultOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
			}
		}
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "TOP FOUR TEAMS QUALIFY FOR THE SEMIS" + "\0");
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.750 \0");
		
	}
	
	public void populateMiniBattingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: mini batting card inning is null";
		} else {

			int row_id = 0, omo_num = 0,batting_size=0;
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + inn.getBatting_team().getTeamName2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBatting_team().getTeamName3() + "\0");

					
					Collections.sort(inn.getBattingCard());
					
					for (BattingCard bc : inn.getBattingCard()) {
						
						row_id = row_id + 1;
						switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.OUT:
								omo_num = 0;
								cont_name = "$Dehighlight";
								batting_size = batting_size + 1;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
								break;
							case CricketUtil.NOT_OUT:
								omo_num = 1;
								cont_name = "$Highlight";
								batting_size = batting_size + 1;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
								break;
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo$Dehighlight$ScoreGrp*ACTIVE SET 1 \0");
							
							
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
							}
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 BatDataIn 1.180 \0");
			
		}
	}
	public void populateMiniBowlingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Bowlingcard's inning is null";
		} else {
			
			int row_id = 0, omo_num = 0;
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + inn.getBowling_team().getTeamName2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBowling_team().getTeamName3() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBowlingCard().size() + "\0");
					
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						switch (boc.getStatus().toUpperCase()) {
						case (CricketUtil.OTHER + CricketUtil.BOWLER):
							omo_num = 0;
							cont_name = "$Dehighlight";
							break;
						case (CricketUtil.LAST + CricketUtil.BOWLER):
							omo_num = 0;
							cont_name = "$Dehighlight";
							break;
						case (CricketUtil.CURRENT + CricketUtil.BOWLER):
							omo_num = 1;
							cont_name = "$Highlight";
							break;
						}
						
						row_id = row_id + 1;
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + 
								row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET  " + boc.getPlayer().getTicker_name().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");

					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 BatDataIn 1.180 \0");
			
		}
			
	}
	public void populateThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,
			MatchAllData match, String broadcaster) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			double strike_rate = 0 , economy_rate=0;
			int k=0;
		
			List<BestStats> top_batsman_beststats = new ArrayList<BestStats>();
			List<BestStats> top_bowler_beststats = new ArrayList<BestStats>();
			for(Tournament tourn : this_series) {
				for(BestStats bs : tourn.getBatsman_best_Stats()) {
					top_batsman_beststats.add(bs);
				}
				for(BestStats bfig : tourn.getBowler_best_Stats()) {
					top_bowler_beststats.add(bfig);
				}
			}
			
			Collections.sort(top_batsman_beststats, new CricketFunctions.PlayerBestStatsComparator());
			Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "THIS SERIES" + "\0");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/GPCL/Logos/" + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/GPCL/Logos/" + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getSurname().toUpperCase() + "\0");
					
					
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getRuns() + "\0");
						
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "S/R" + "\0");			
						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							strike_rate = this_series.get(i).getRuns() * 100;
							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
						}
						 
						for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
							if(top_batsman_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									k += 1;
									if(top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												top_batsman_beststats.get(j).getBestEquation()/2 + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												(top_batsman_beststats.get(j).getBestEquation()-1) / 2 + "*" + "\0");
									}
									break;
								}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}
						}
						break;
					case CricketUtil.BOWLER:
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getWickets() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON." + "\0");
						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df = new DecimalFormat("0.00");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
						}
						
						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
							if(top_bowler_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									k += 1;
									if(top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
									}
									else if(top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
									}
									break;
								}
							}else if(top_bowler_beststats.get(j).getPlayerId() != Playerid) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}
						}
						break;
					}
					
				}

			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");	
			

		}
	}
	public void populateFFThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {

			double strike_rate = 0 , economy_rate=0;
			int omo_num = 0;
			String cont_name = "";
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SERIES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");

			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "C:\\\\Images\\\\GPCL\\\\Photos\\\\" 
								+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ "IMAGE*/Default/GPCL/Logos/" + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "C:\\\\Images\\\\GPCL\\\\Photos\\\\" 
								+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getSurname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + 
							this_series.get(i).getPlayer().getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
							this_series.get(i).getPlayer().getSurname().toUpperCase() + "\0");
					
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						
						cont_name = "$Dehighlight";
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

						
						if(this_series.get(i).getPlayer().getBattingStyle() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
									CricketFunctions.getbattingstyle(this_series.get(i).getPlayer().getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getRuns() + "\0");
						
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							strike_rate = this_series.get(i).getRuns() * 100;
							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
						}
						
						break;
					case CricketUtil.BOWLER:
						
						cont_name = "$Dehighlight";
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

						if(this_series.get(i).getPlayer().getBowlingStyle() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + 
									CricketFunctions.getbowlingstyle(this_series.get(i).getPlayer().getBowlingStyle().toUpperCase())+ "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + " " + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getWickets() + "\0");
																
						
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
						}
						break;
					}
					
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.700 \0");
			

			}
	}
	
	public void populateFFstats(PrintWriter print_writer,String viz_scene,String StatType,List<Tournament> tournament,List<Team> team ,MatchAllData match, String broadcaster) {
		
		int row_no=0;
		int omo_num = 0;
		String cont_name = "";
		switch(StatType.toUpperCase()) {
		case "MOST_RUNS":
			//int omo_num = 0;
			//String cont_name = "";
			Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "RUNS" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "RUNS" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "S/R" + "\0");
			
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament.get(i).getPlayer().getFull_name().toUpperCase() + " - Runs -" + tournament.get(i).getRuns());
				if(row_no < 10) {
					row_no = row_no + 1;
					for(Inning inn : match.getMatch().getInning()) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(tournament.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(bc.getPlayer().getFull_name().toUpperCase())) {
								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}
					
					
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getRuns() + "\0");
			 		
					if(tournament.get(i).getBallsFaced() >= 1) {
						DecimalFormat df = new DecimalFormat("0.0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 					+ cont_name + "$StatValueGrp$StatValue3*GEOM*TEXT SET " + df.format((100 * (double)tournament.get(i).getRuns()) / (double)tournament.get(i).getBallsFaced()) + "\0");
					}
				}else {
					break;
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
		case "MOST_WICKETS":
			
			Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "WICKETS" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "WICKETS" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "ECONOMY" + "\0");
			
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament.get(i).getPlayer().getFull_name().toUpperCase() + " - Runs -" + tournament.get(i).getRuns());
				if(row_no < 10) {
					row_no = row_no + 1;
					for(Inning inn : match.getMatch().getInning()) {
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(tournament.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(boc.getPlayer().getFull_name().toUpperCase())) {
								switch (boc.getStatus().toUpperCase()) {
								case CricketUtil.OTHER + CricketUtil.BOWLER: case CricketUtil.LAST + CricketUtil.BOWLER:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.CURRENT + CricketUtil.BOWLER:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}
					
					
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getWickets() + "\0");
			 		
					if(tournament.get(i).getBallsBowled() >= 1) {
						
						DecimalFormat df_b = new DecimalFormat("0.00");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 			+ cont_name + "$StatValueGrp$StatValue3*GEOM*TEXT SET " + df_b.format(((double)tournament.get(i).getRunsConceded() / (double)tournament.get(i).getBallsBowled())*6) + "\0");
					}
				}else {
					break;
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
		case "MOST_FOURS":
			Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "FOURS" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "3" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "FOURS" + "\0");
			
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament.get(i).getPlayer().getFull_name().toUpperCase() + " - Runs -" + tournament.get(i).getRuns());
				if(row_no < 10) {
					row_no = row_no + 1;
					for(Inning inn : match.getMatch().getInning()) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(tournament.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(bc.getPlayer().getFull_name().toUpperCase())) {
								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}
					
					
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getFours() + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
			
		case "MOST_SIXES":
			Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "SIXES" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "3" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "SIXES" + "\0");
			
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament.get(i).getPlayer().getFull_name().toUpperCase() + " - Runs -" + tournament.get(i).getRuns());
				if(row_no < 10) {
					row_no = row_no + 1;
					for(Inning inn : match.getMatch().getInning()) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(tournament.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(bc.getPlayer().getFull_name().toUpperCase())) {
								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}
					
					
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getFours() + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
		case "HIGHEST_SCORE":
			List<BestStats> top_ten_beststat = new ArrayList<BestStats>();
			for(Tournament tourn : tournament) {
				for(BestStats bs : tourn.getBatsman_best_Stats()) {
					top_ten_beststat.add(bs);
				}
			}
			
			Collections.sort(top_ten_beststat, new CricketFunctions.PlayerBestStatsComparator());

			for(BestStats Top_ten_bs : top_ten_beststat) {
				System.out.println("Best Stats : " + Top_ten_bs);
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/GPCL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "HIGHEST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "INDIVIDUAL SCORE" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "SCORE" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "BALLS" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "OPPONENT" + "\0");
			
			for(int i = 0; i <= top_ten_beststat.size() - 1 ; i++) {
				if(row_no < 10) {
					row_no = row_no + 1;
					
					for(Inning inn : match.getMatch().getInning()) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(top_ten_beststat.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(bc.getPlayer().getFull_name().toUpperCase())) {
								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 							+ cont_name + "$StatHead*GEOM*TEXT SET " + top_ten_beststat.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
					if(top_ten_beststat.get(i).getBestEquation() % 2 == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
									+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + top_ten_beststat.get(i).getBestEquation() / 2 + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
									+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + top_ten_beststat.get(i).getBestEquation() / 2 + "*" + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
								+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + top_ten_beststat.get(i).getBalls() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
		 					+ cont_name + "$StatValueGrp$StatValue3*GEOM*TEXT SET " + top_ten_beststat.get(i).getOpponentTeam().getTeamName3().toUpperCase() + "\0");
					
				}	
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			break;
		}
	}
	
}

	