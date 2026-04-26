package com.cricket.broadcaster;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBException;
import com.cricket.containers.BattingCardFF;
import com.cricket.containers.BowlingFF;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Event;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.OverByOverData;
import com.cricket.model.Player;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EVEREST_NEPAL_T20 extends Scene{

	public String broadcaster = "EVEREST_NEPAL_T20";
	public String status;
	public String slashOrDash = "-";
	public String logo_path = "IMAGE*/Default/Nepal_T20/Logos/";
	public String logo_path_ns = "C:\\\\Images\\\\NEPAL_T20\\\\Logos\\\\Sponsor\\\\";
	public String photo_path = "C:\\\\Images\\\\NEPAL_T20\\\\Photos\\\\";
	public String icons_path = "C:\\\\Images\\\\NEPAL_T20\\\\Icons\\\\";
	public Infobar infobar = new Infobar(); 
	public String which_graphic_on_screen = "";
	public BattingCardFF bcf = new BattingCardFF();
	public BowlingFF bocf = new BowlingFF();
	
	public EVEREST_NEPAL_T20() {
		super();
	}

	public EVEREST_NEPAL_T20(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics) throws InterruptedException, ParseException, JAXBException, IllegalAccessException, InvocationTargetException, IOException
	{
	
		switch (whatToProcess) {
		case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP":
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
		case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-LINEUP":
		case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-LTMANHATTAN": case "ANIMATE-OUT-POWERPLAY": case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-PLAYOFFS": case "ANIMATE-IN-BATGRIFF":
		case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-BALL_PERFORMER": case "TICKER_LT_OUT": case "TICKER_LT_IN": case "ANIMATE-IN-MOST": case "ANIMATE-IN-INN_BUILDER":
			
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP":
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PLAYERPROFILE": 
			case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-POSITION_LANDMARK": 
			case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE":
			case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM":
			case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET":
			case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-BUGPARTNERSHIP":
			case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-PLAYOFFS": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-BALL_PERFORMER": case "ANIMATE-IN-MOST":
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
			case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-LINEUP": case "ANIMATE-IN-LTMANHATTAN": case "ANIMATE-IN-INN_BUILDER":
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
			case "ANIMATE-IN-BUG":
				processAnimation(print_writer, "In", "START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "BUG";
				break;
			case "ANIMATE-IN-BUG-DISMISSAL":
				processAnimation(print_writer, "In", "START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "BUG-DISMISSAL";
				break;
			case "ANIMATE-IN-BUG_POWERPLAY":
				processAnimation(print_writer, "In", "START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "BUG_POWERPLAY";
				break;
			case "ANIMATE-IN-BUG_PARTNERSHIP":
				processAnimation(print_writer, "In", "START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "BUG_PARTNERSHIP";
				break;
			case "ANIMATE-IN-BUG_HIGHLIGHT":
				processAnimation(print_writer, "In", "START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "BUG_HIGHLIGHT";
				break;
			case "ANIMATE-IN-BUG-BOWLER":
				processAnimation(print_writer, "In", "START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "BUG-BOWLER";
				break;
			case "ANIMATE-IN-MULTI_PARTNERSHIP":
				processAnimation(print_writer, "In","START", "EVEREST_NEPAL_T20");
//				AnimateInGraphics(print_writer, "MULTI-PARTNERSHIP");
				which_graphic_on_screen = "MULTI-PARTNERSHIP";
				break;
			case "ANIMATE-IN-MOST":
				processAnimation(print_writer, "In","START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "MOST";
				break;
			case "ANIMATE-IN-BUG-DB":
				processAnimation(print_writer, "In","START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "BUG-DB";
				break;
			case "ANIMATE-IN-MATCHID":
				processAnimation(print_writer, "In","START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "MATCHID";
				break;
			case "ANIMATE-IN-WORM":
				processAnimation(print_writer, "In","START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "WORM";
				break;
			case "ANIMATE-IN-NAMESUPER":
				processAnimation(print_writer, "In", "START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "NAMESUPER";
				break;
			case "ANIMATE-IN-NAMESUPER-PLAYER":
				processAnimation(print_writer, "In", "START", "EVEREST_NEPAL_T20");
				which_graphic_on_screen = "NAMESUPER-PLAYER";
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
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/Nepal_T20/ScoreBug\0");
		           	
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
				if(infobar.getLast_bottom_right_section() != null && !infobar.getLast_bottom_right_section().isEmpty()) {
					processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
					processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
					processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
				}
				//System.out.println(which_graphic_on_screen);
				switch(which_graphic_on_screen) {
				case "MULTI-PARTNERSHIP":
					AnimateOutGraphics(print_writer, "MULTI-PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "NAMESUPER-PLAYER":
					AnimateOutGraphics(print_writer, "NAMESUPER-PLAYER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG-DISMISSAL":
					AnimateOutGraphics(print_writer, "BUG-DISMISSAL");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG":
					AnimateOutGraphics(print_writer, "BUG");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG-BOWLER":
					AnimateOutGraphics(print_writer, "BUG-BOWLER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG-DB":
					AnimateOutGraphics(print_writer, "BUG-DB");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "NAMESUPER":
					AnimateOutGraphics(print_writer, "NAMESUPER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "WORM":
					AnimateOutGraphics(print_writer, "WORM");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "MOST":
					AnimateOutGraphics(print_writer, "MOST");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "MATCHID":
					AnimateOutGraphics(print_writer, "MATCHID");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG_HIGHLIGHT":
					AnimateOutGraphics(print_writer, "BUG_HIGHLIGHT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG_PARTNERSHIP":
					AnimateOutGraphics(print_writer, "BUG_PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG_POWERPLAY":
					AnimateOutGraphics(print_writer, "BUG_POWERPLAY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				}
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
		case "MOST_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getTeams()).toString();
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
		
		case "POPULATE-BUG_POWERPLAY": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-BUG_PARTNERSHIP": case "POPULATE-MULTI_PARTNERSHIP":
		case "POPULATE-WORM": case "POPULATE-MOST_RUNS": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-BUG": case "POPULATE-L3-BUG-BOWLER":
		case "POPULATE-L3-BUG-DB": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-FF-MATCHID":
			
			if(which_graphic_on_screen == "SCOREBUG" || which_graphic_on_screen == "IDENT") {
			}else if(which_graphic_on_screen != "") {
				AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
			}

			//System.out.println("which_graphic_on_screen = " + which_graphic_on_screen);
			//System.out.println("valueToProcess = " + valueToProcess);
			//System.out.println("broadcaster = " + broadcaster);
			//System.out.println("whatToProcess = " + whatToProcess);
			
			switch(whatToProcess.toUpperCase()) {
			case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-DIRECTOR": 
			case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-RIGHT": case "POPULATE-BT-POWERPLAY":
				break;
			case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
					break;
				}else {
					scenes.get(0).scene_load(print_writer, broadcaster);
				}
				break;
			default:
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,broadcaster);
//					print_writer.println("-1 RENDERER*STAGE SHOW 0.0 \0");
				break;
			}
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-BUG_POWERPLAY":
				populateBugPowerPLay(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-LT-BUG_HIGHLIGHT":
				populateBugHighlight(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-MULTI_PARTNERSHIP":
				populateBugMultipartnership(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
				break;
			case "POPULATE-BUG_PARTNERSHIP":
				populateBugPartnership(print_writer, valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-WORM":
				populateWorm(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-MOST_RUNS":
				populateMostRunsTeam(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1],valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster);
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
			case "POPULATE-L3-NAMESUPER":
				for(NameSuper ns : cricketService.getNameSupers()) {
				  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
					  populateNameSuper(print_writer, valueToProcess.split(",")[0], ns, match, broadcaster);
				  }
				}
				break;
			case "POPULATE-L3-NAMESUPER-PLAYER":
				populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;		
			case "POPULATE-BUGPARTNERSHIP":
				populateBugPartnership(print_writer, valueToProcess.split(",")[0],match, broadcaster);
				break;
			case "POPULATE-FF-MATCHID":
				populateMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;	
			}
		}
			//return JSONObject.fromObject(this_doad).toString();			
			return null;
	}

	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic)
	{
		switch(whichGraphic) {
		case "FF_IN":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + "FF_In" + " " + "START" + ";");
			break;
		}	
	}	
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException
	{
		switch(whichGraphic) {
		case "MULTI-PARTNERSHIP": case "NAMESUPER-PLAYER": case "BUG-DISMISSAL": case "BUG": case "BUG-BOWLER": case "BUG-DB": case "NAMESUPER": case "WORM": 
		case "BUG_HIGHLIGHT": case "BUG_PARTNERSHIP": case "BUG_POWERPLAY": case "MATCHID": case "MOST": 
			processAnimation(print_writer, "Out", "START", broadcaster);
			TimeUnit.SECONDS.sleep(1);
			break;
		}	
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
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
	public String batBowlSummaryPointsTableLogoAnim(PrintWriter print_writer,String which_scene) throws InterruptedException {
		
		switch(which_scene.toUpperCase()) {
		case "BATTING":
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$BatLeftLogoGrp$BatLeftLogo*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$BallLeftLogoGrp$BallLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp$PointsLeftLogo*ACTIVE SET 0 \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$BatRightLogoGrp$BatRightLogo*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$BallRightLogoGrp$BallRightLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$PointsLeftLogoGrp$PointsLeftLogo*ACTIVE SET 0 \0");
			
			break;
		case "BOWLING":
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$BatLeftLogoGrp$BatLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$BallLeftLogoGrp$BallLeftLogo*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp$PointsLeftLogo*ACTIVE SET 0 \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$BatRightLogoGrp$BatRightLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$BallRightLogoGrp$BallRightLogo*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$PointsLeftLogoGrp$PointsLeftLogo*ACTIVE SET 0 \0");
			
			break;
		case "SUMMARY":
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$BatLeftLogoGrp$BatLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$BallLeftLogoGrp$BallLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp$PointsLeftLogo*ACTIVE SET 0 \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$BatRightLogoGrp$BatRightLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$BallRightLogoGrp$BallRightLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$PointsLeftLogoGrp$PointsLeftLogo*ACTIVE SET 0 \0");
			
			break;
		case "POINTS_TABLE":
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$BatLeftLogoGrp$BatLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$BallLeftLogoGrp$BallLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp$PointsLeftLogo*ACTIVE SET 1 \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$BatRightLogoGrp$BatRightLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$BallRightLogoGrp$BallRightLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$RightTeamLogos$PointsLeftLogoGrp$PointsLeftLogo*ACTIVE SET 1 \0");
			
			break;
		}
	return "";
}
	
	
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
	
	public void populateBugDismissal(PrintWriter print_writer, String viz_scene, int whichInning, String statsType,
			int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {

				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BATSMAN:
							for (BattingCard bc : inn.getBattingCard()) {
								if (bc.getPlayerId() == playerId) {

									print_writer.println(
											"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
													+ bc.getPlayer().getTicker_name().toUpperCase() + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
													+ bc.getHowOutText() + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
													+ bc.getRuns() + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
													+ bc.getBalls() + ";");
								}
							}
							break;
						}
					}
				}

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}

	public void populateBug(PrintWriter print_writer, String viz_scene, int whichInning, String statsType, int playerId,
			MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {

				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BATSMAN:
							for (BattingCard bc : inn.getBattingCard()) {
								if (bc.getPlayerId() == playerId) {
									print_writer.println(
											"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
													+ bc.getPlayer().getTicker_name().toUpperCase() + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
													+ "4s:" + bc.getFours() + " 6s:" + bc.getSixes() + ";");

									if (bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println(
												"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
														+ bc.getRuns() + "*" + ";");
									} else {
										print_writer.println(
												"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
														+ bc.getRuns() + ";");
									}
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
													+ bc.getBalls() + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}

	public void populateBugBowler(PrintWriter print_writer, String viz_scene, int whichInning, String statsType,
			int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BOWLER:
							for (BowlingCard boc : inn.getBowlingCard()) {
								if (boc.getPlayerId() == playerId) {
									print_writer.println(
											"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
													+ boc.getPlayer().getTicker_name().toUpperCase() + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
													+ "ECON: " + boc.getEconomyRate() + ";");

									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
													+ boc.getWickets() + slashOrDash + boc.getRuns() + ";");

									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
													+ CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
								}
							}
							break;
						}

					}

				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}

	public void populateBugPowerPLay(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "POWERPLAY" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
							+ getPowerPlayScore(match,inn, whichInning, match.getEventFile().getEvents()) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + "" + ";");

					if (whichInning == 1) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamName2() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamName2().toUpperCase() + ";");
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	
	public void populateBugPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						String Left_Batsman ="",Right_Batsman="";
						
						for (Player hs : match.getSetup().getHomeSquad()) {
							if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								Left_Batsman = hs.getTicker_name();
							}
							if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								Right_Batsman = hs.getTicker_name();
							}
						}
						for (Player as : match.getSetup().getAwaySquad()) {
							if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								Left_Batsman = as.getTicker_name();
							}
							if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								Right_Batsman = as.getTicker_name();
							}
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + " & " + Right_Batsman + ";");
						if(inn.getTotalWickets() == 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + ";");
						}else if(inn.getTotalWickets() == 1) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + ";");
						}else if(inn.getTotalWickets() == 2) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ";");
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateBugMultipartnership(PrintWriter print_writer, String viz_scene,int whichinning, int partnership, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == whichinning) {
						String Left_Batsman ="",Right_Batsman="";
						
						for (Player hs : match.getSetup().getHomeSquad()) {
							if(hs.getPlayerId() == inn.getPartnerships().get(partnership - 1).getFirstBatterNo()) {
								Left_Batsman = hs.getTicker_name();
							}
							if(hs.getPlayerId() == inn.getPartnerships().get(partnership - 1).getSecondBatterNo()) {
								Right_Batsman = hs.getTicker_name();
							}
						}
						for (Player as : match.getSetup().getAwaySquad()) {
							if(as.getPlayerId() == inn.getPartnerships().get(partnership - 1).getFirstBatterNo()) {
								Left_Batsman = as.getTicker_name();
							}
							if(as.getPlayerId() == inn.getPartnerships().get(partnership - 1).getSecondBatterNo()) {
								Right_Batsman = as.getTicker_name();
							}
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + " & " + Right_Batsman + ";");
						if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 1) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "st WICKET PARTNERSHIP" + ";");
						}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 2) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "nd WICKET PARTNERSHIP" + ";");
						}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 3) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "rd WICKET PARTNERSHIP" + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "th WICKET PARTNERSHIP" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
								inn.getPartnerships().get(partnership - 1).getTotalRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + 
								inn.getPartnerships().get(partnership - 1).getTotalBalls() + ";");
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateWorm(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if(whichInning == 0) {
				this.status = "ERROR: Inning is null";
			}else {
				
				int maxRuns = 0,runsIncr = 0,wicketcountHome=0,wicketcountAway=0;
				//long lngth = 0;
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 "
						+ "" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 "
						+ match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader "
						+ match.getSetup().getTournament() + ";");
								
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + match.getMatch().getInning().get(0).getBatting_team().getTeamName2() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamScore " + 
							match.getMatch().getInning().get(0).getTotalRuns() + "-" + match.getMatch().getInning().get(0).getTotalWickets() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamOvers " + 
							CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(), match.getMatch().getInning().get(0).getTotalBalls()) + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + match.getMatch().getInning().get(1).getBatting_team().getTeamName2() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamScore " + 
							match.getMatch().getInning().get(1).getTotalRuns() + "-" + match.getMatch().getInning().get(1).getTotalWickets() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamOvers " + 
							CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), match.getMatch().getInning().get(1).getTotalBalls()) + ";");
					}
				}
				
				List<String> overByOverRuns = new ArrayList<String>();
				for(int inn_count = 1; inn_count <= whichInning; inn_count++)
				{
					overByOverRuns.clear();
					for(OverByOverData Over : CricketFunctions.getOverByOverData(match,inn_count ,"WORM" ,match.getEventFile().getEvents())) {
						overByOverRuns.add(String.valueOf(Over.getOverTotalRuns()));
					}
					
					//String cumm_runs = String.valueOf("0") + "_" + String.join("_", overByOverRuns); // Store Per Overs Runs
					String cumm_runs = String.join("_", overByOverRuns); // Store Per Overs Runs

					if(match.getMatch().getInning().get(0).getTotalRuns() > match.getMatch().getInning().get(1).getTotalRuns()) {
						maxRuns = match.getMatch().getInning().get(0).getTotalRuns();
						if(maxRuns % 4 == 0) {
							maxRuns = maxRuns + 1;
						}
					}
					else {
						maxRuns = match.getMatch().getInning().get(1).getTotalRuns();
						if(maxRuns % 4 == 0) {
							maxRuns = maxRuns + 1;
						}
					}
					
					
					while (maxRuns % 4 != 0) {     // 5 label in y-axis
						maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
					}
					
					for(int i =0; i < 4;i++) {
						runsIncr = maxRuns / 4; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TxtScore" + (4 - i) + " " + runsIncr*(i+1) + ";");
					}
					
					if(inn_count == 1) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Linechart*GEOMETRY*LINE_CHART SET MAX_VALUE " + maxRuns + ";");

						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Linechart*GEOMETRY*LINE_CHART SET DATA " + cumm_runs + ";");
						
						for(int j=0; j<=CricketFunctions.getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).size()-1;j++) {
							if(match.getMatch().getInning().get(inn_count-1).getTotalWickets() > 0) {
								if(CricketFunctions.getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
									wicketcountHome = wicketcountHome + 1;
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets*CONTAINER SET ACTIVE 1;");

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets$Group" + (wicketcountHome - 1) + "$Group" + 
											(wicketcountHome - 1) + "_XPos*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets$Group" + (wicketcountHome - 1) + "$Group" + 
											(wicketcountHome - 1) + "_XPos*FUNCTION*TAG_POSITION SET MAX_VALUE " + match.getSetup().getMaxOvers() + ";");

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHomeWickets " + (wicketcountHome-1) + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets$Group" + (wicketcountHome - 1) + "*FUNCTION*TAG_POSITION SET MAX_VALUE " + maxRuns + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET yHomePos" + (wicketcountHome-1) + " " + 
											CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalRuns() + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET xHomePos" + (wicketcountHome - 1) + " " + (j - 0.5) + ";");
									
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selHomeGroup"+ (wicketcountHome - 1) + " " + (CricketFunctions.
											getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalWickets()-1) + ";");
									
//									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selGroup"+ wicketcount + " " + CricketFunctions.
//											getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(j).getOverTotalWickets() + ";");
//									System.out.println("Over : " + CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEvents()).get(j).getOverTotalWickets()
//											+ " - Runs : " + CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEvents()).get(j).getOverTotalRuns());
								}
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets*CONTAINER SET ACTIVE 0;");
							}
						}
					}
					if(inn_count == 2) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Linechart*GEOMETRY*LINE_CHART SET MAX_VALUE " + maxRuns + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Linechart*GEOMETRY*LINE_CHART SET DATA " + cumm_runs + ";");
						
						for(int j=0; j<=CricketFunctions.getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).size()-1;j++) {
							if(match.getMatch().getInning().get(inn_count-1).getTotalWickets() > 0) {
								if(CricketFunctions.getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
									wicketcountAway = wicketcountAway + 1;
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets*CONTAINER SET ACTIVE 1;");

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets$Group" + (wicketcountAway - 1) + "$Group" + (wicketcountAway-1) + "_XPos*CONTAINER SET ACTIVE 1;");

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets$Group" + (wicketcountAway - 1) + 
											"$Group" + (wicketcountAway-1) + "_XPos*FUNCTION*TAG_POSITION SET MAX_VALUE " + match.getSetup().getMaxOvers() + ";");

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAwayWickets " + (wicketcountAway-1) + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets$Group" + (wicketcountAway - 1) + "*FUNCTION*TAG_POSITION SET MAX_VALUE " + maxRuns + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET yAwayPos" + (wicketcountAway -1) + " " + 
											CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalRuns() + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET xAwayPos" + (wicketcountAway - 1) + " " + (j - 0.5) + ";");
									
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selAwayGroup"+ (wicketcountAway - 1) + " " + (CricketFunctions.
											getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalWickets()-1) + ";");
									
//									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selGroup"+ wicketcount + " " + CricketFunctions.
//											getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(j).getOverTotalWickets() + ";");
//									System.out.println("Over : " + CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEvents()).get(j).getOverTotalWickets()
//											+ " - Runs : " + CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEvents()).get(j).getOverTotalRuns());
								}
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets*CONTAINER SET ACTIVE 0;");
							}
						}
					}
				}
//				for(int i=0; i<(CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).size()-1);i++) {
//					overByOverRuns.add(String.valueOf(CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(i).getOverTotalRuns()));
//					//System.out.println("Runs : " + i + " - " + String.valueOf(CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(i).getOverTotalRuns()));
//				}
//				
//				String cumm_runs = String.valueOf("0") + "_" + String.join("_", overByOverRuns); // Store Per Overs Runs
//				//System.out.println("Runs : " + cumm_runs);
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Linechart*GEOMETRY*LINE_CHART SET DATA " + cumm_runs + ";");
//
//				
//				if(match.getInning().get(0).getTotalRuns() > match.getInning().get(1).getTotalRuns()) {
//					maxRuns = match.getInning().get(0).getTotalRuns();
//				}
//				else {
//					maxRuns = match.getInning().get(1).getTotalRuns();
//				}
//				while (maxRuns % 4 != 0) {     // 5 label in y-axis
//					maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
//				}
//				
//				for(int i =0; i < 4;i++) {
//					runsIncr = maxRuns / 4; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36	
//					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TxtScore" + (4 - i) + " " + runsIncr*(i+1) + ";");
//				}
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$DataAll$BAtting$Worms$Graph$Linechart*GEOMETRY*LINE_CHART SET MAX_VALUE " + maxRuns + ";");
				
//				for(int j=0; j<=CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).size()-1;j++) {
//					if(match.getInning().get(whichInning-1).getTotalWickets() > 0) {
//						if(CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(j).getOverTotalWickets() > 0) {
//							wicketcount = wicketcount + 1;
//							
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vWickets " + wicketcount + ";");
//							
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Group" + wicketcount + "*FUNCTION*TAG_POSITION SET MAX_VALUE " + maxRuns + ";");
//							
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET yPos" + wicketcount + " " + 
//									CricketFunctions.getOverByOverData(match, whichInning,"WORM", match.getEvents()).get(j).getOverTotalRuns() + ";");
//							
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET xPos" + wicketcount + " " + j + ";");
//							
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selGroup"+ wicketcount + " " + CricketFunctions.
//									getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(j).getOverTotalWickets() + ";");
//							System.out.println("Over : " + CricketFunctions.getOverByOverData(match, whichInning,"WORM", match.getEvents()).get(j).getOverNumber()
//									+ " - Runs : " + CricketFunctions.getOverByOverData(match, whichInning,"WORM", match.getEvents()).get(j).getOverTotalRuns());
//						}
//					}
//				}	
			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 140;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
			
	}
	public void populateBugHighlight(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {

				print_writer
						.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "HIGHLIGHTS" + ";");

				if (whichInning == 1) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ match.getMatch().getInning().get(0).getBatting_team().getTeamName3().toUpperCase() + ";");
					if (match.getMatch().getInning().get(0).getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
								+ match.getMatch().getInning().get(0).getTotalRuns() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
								+ match.getMatch().getInning().get(0).getTotalRuns() + "-"
								+ match.getMatch().getInning().get(0).getTotalWickets() + ";");
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
							+ CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(),
									match.getMatch().getInning().get(0).getTotalBalls())
							+ ";");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + ";");
					if (match.getMatch().getInning().get(1).getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
								+ match.getMatch().getInning().get(1).getTotalRuns() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
								+ match.getMatch().getInning().get(1).getTotalRuns() + "-"
								+ match.getMatch().getInning().get(1).getTotalWickets() + ";");
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
							+ CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(),
									match.getMatch().getInning().get(1).getTotalBalls())
							+ ";");
				}

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	
	public void populateMostRunsTeam(PrintWriter print_writer,String viz_scene,String StatType,String Type,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MostRuns inning is null";
		} else {
			switch(Type.toUpperCase()) {
			case "RUNS":
				int row_no=0;
				double strike_rate = 0;
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 "
						+ StatType.toUpperCase() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 "
						+ "MOST RUNS" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader "
						+ match.getSetup().getTournament() + ";");
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows 4;");

				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 PLAYERS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 MATCHES;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 RUNS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 S/R;");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName2().equalsIgnoreCase(StatType.toUpperCase())) {
						if(tournament.get(i).getRuns() > 0) {
							row_no = row_no + 1;
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + 
									CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + row_no + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getPlayer().getTicker_name() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + tournament.get(i).getMatches() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "C " + tournament.get(i).getRuns() + ";");
							
							strike_rate = tournament.get(i).getRuns() * 100;
							strike_rate = strike_rate/tournament.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							
							if(tournament.get(i).getBallsFaced() == 0 || tournament.get(i).getRuns() == 0) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "D " + "-" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "D " + df.format(strike_rate) + ";");
							}
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
				break;
				
			case "WICKETS":
				int row = 0;
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 "
						+ StatType.toUpperCase() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 "
						+ "MOST WICKETS" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader "
						+ match.getSetup().getTournament() + ";");
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows 4;");

				Collections.sort(tournament, new CricketFunctions.BowlerWicketsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 PLAYERS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 MATCHES;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 WICKETS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 ECONOMY;");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName2().equalsIgnoreCase(StatType.toUpperCase())) {
						if(tournament.get(i).getWickets() > 0) {
							row = row + 1;
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + 
									CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + row + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row + "A " + tournament.get(i).getPlayer().getTicker_name() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row + "B " + tournament.get(i).getMatches() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row + "C " + tournament.get(i).getWickets() + ";");
							
							if (tournament.get(i).getBallsBowled() >= 1) {
								DecimalFormat df_b = new DecimalFormat("0.00");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row + "D " + df_b.format(((double) tournament.get(i).getRunsConceded()
										/ (double) tournament.get(i).getBallsBowled()) * 6) + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row + "D " + "-" + ";");
							}
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
				break;
			}
		}
	}
	public void populateMatchId(PrintWriter print_writer, String viz_scene, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 "
						+ "" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 "
						+ match.getSetup().getMatchIdent().toUpperCase() + ";");
				// print_writer.println("LAYER1*EVEREST*TREEVIEW*Subheader1*CONTAINER SET ACTIVE
				// 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader "
						+ match.getSetup().getTournament() + ";");
				/*
				 * print_writer.
				 * println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " +
				 * logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				 */
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
						+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
						match.getSetup().getHomeTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path
						+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "LIVE FROM "
						+ match.getSetup().getVenueName().toUpperCase() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "\\TLogo" + CricketUtil.PNG_EXTENSION + ";");

				
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 117.0;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  print_writer.
				  println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				  TimeUnit.SECONDS.sleep(1);
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				 

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}

	}
	public void populateBugsDB(PrintWriter print_writer, String viz_scene, Bugs bug, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {

				if (bug.getText1() != null && bug.getText2() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ bug.getText1().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
							+ bug.getText2().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 0;");
				} else if (bug.getText1() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ bug.getText1().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 0;");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ bug.getText2().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 0;");
				}

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}

	}
	public void populateNameSuper(PrintWriter print_writer, String viz_scene, NameSuper ns, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				if(ns.getSponsor() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ "Sponsor//" + ns.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
				}
				
				if (ns.getFirstname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName "
							+ "" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
							+ ns.getSurname() + ";");
				} else if (ns.getSurname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName "
							+ ns.getFirstname() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
							+ "" + ";");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
							+ ns.getFirstname() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName "
							+ ns.getSurname() + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 "
						+ "" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
						+ ns.getSubLine().toUpperCase() + ";");

				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 67.0;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				  TimeUnit.SECONDS.sleep(1);
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				 

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;

		}
	}

	public void populateNameSuperPlayer(PrintWriter print_writer, String viz_scene, int TeamId,String captainWicketKeeper, int playerId, MatchAllData match, String session_selected_broadcaster)
			throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_NEPAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				String Home_or_Away = "";

				if (TeamId == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					Home_or_Away = match.getSetup().getHomeTeam().getTeamName3();
					for (Player hs : match.getSetup().getHomeSquad()) {
						if (playerId == hs.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
									+ hs.getFirstname() + ";");
							if (hs.getSurname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName "
										+ "" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName "
										+ hs.getSurname() + ";");
							}
							
							if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
										+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + ";");
							}
						}

					}
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					Home_or_Away = match.getSetup().getAwayTeam().getTeamName3().toUpperCase();
					for (Player as : match.getSetup().getAwaySquad()) {
						if (playerId == as.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
									+ as.getFirstname() + ";");
							if (as.getSurname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName "
										+ "" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName "
										+ as.getSurname() + ";");
							}
							if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
										+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + ";");
							}
						}
					}
				}

				switch (captainWicketKeeper.toUpperCase()) {
				case CricketUtil.CAPTAIN:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
							+ captainWicketKeeper.toUpperCase() + " , " + Home_or_Away + ";");
					break;
				case "PLAYER OF THE MATCH":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
							+ captainWicketKeeper.toUpperCase() + ";");
					break;
				case CricketUtil.WICKET_KEEPER:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
							+ "WICKET KEEPER" + " , " + Home_or_Away + ";");
					break;
				case "CAPTAIN-WICKETKEEPER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
							+ "CAPTAIN & WICKET KEEPER" + " , " + Home_or_Away + ";");
					break;
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 "
						+ "" + ";");
				
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 67.0;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				  TimeUnit.SECONDS.sleep(1);
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				 

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;

		}
	}
	public static String getPowerPlayScore(MatchAllData match,Inning inning, int inn_num, List<Event> events) {
		int total_run_PP = 0, total_wickets_PP = 0;
		if ((events != null) && (events.size() > 0)) {
			for (Event evnt : events) {
				if (evnt.getEventInningNumber() == inn_num) {
					int Event_overs = ((evnt.getEventOverNo() * 6) + evnt.getEventBallNo());
					if ((Event_overs) <= CricketFunctions.getBallCountStartAndEndRange(match, inning).get(1)) {
						switch (evnt.getEventType()) {
						case CricketUtil.ONE:
						case CricketUtil.TWO:
						case CricketUtil.THREE:
						case CricketUtil.FIVE:
						case CricketUtil.DOT:
						case CricketUtil.FOUR:
						case CricketUtil.SIX:
							total_run_PP += evnt.getEventRuns();
							break;

						case CricketUtil.WIDE:
						case CricketUtil.NO_BALL:
						case CricketUtil.BYE:
						case CricketUtil.LEG_BYE:
						case CricketUtil.PENALTY:
							total_run_PP += evnt.getEventRuns();
							break;

						case CricketUtil.LOG_WICKET:
							total_wickets_PP += 1;
							break;

						case CricketUtil.LOG_ANY_BALL:
							total_run_PP += evnt.getEventRuns();
							if (evnt.getEventExtra() != null) {
								total_run_PP += evnt.getEventExtraRuns();
							}
							if (evnt.getEventSubExtra() != null) {
								total_run_PP += evnt.getEventSubExtraRuns();
							}
							if (evnt.getEventHowOut() != null && !evnt.getEventHowOut().isEmpty()) {
								total_wickets_PP += 1;
							}
							break;
						}
					}
				}
			}
		}
		return String.valueOf(total_run_PP) + "-" + String.valueOf(total_wickets_PP);
	}
}

	