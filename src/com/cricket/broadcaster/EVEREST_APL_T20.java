package com.cricket.broadcaster;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBException;
import com.cricket.containers.BattingCardFF;
import com.cricket.containers.BowlingFF;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Configuration;
import com.cricket.model.Event;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.Player;
import com.cricket.model.Split;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EVEREST_APL_T20 extends Scene{

	public String broadcaster = "EVEREST_APL_T20";
	public String status;
	public String slashOrDash = "-";
	//public String logo_path_ns = "C:\\\\Images\\\\APL\\\\Logos\\\\";
	public String photo_path = "C:\\Images\\APL\\Photos\\";
	private String local_photo_path = "c\\Images\\APL\\Photos\\";
	public String logo_path = "C:\\Images\\APL\\Logos\\";
	//public String base_path = "C:\\EVEREST_APL2023\\Textures\\Color01.png";
	public Infobar infobar = new Infobar(); 
	public String which_graphic_on_screen = "";
	public BattingCardFF bcf = new BattingCardFF();
	public BowlingFF bocf = new BowlingFF();
	
	public EVEREST_APL_T20() {
		super();
	}

	public EVEREST_APL_T20(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics, Configuration config) throws InterruptedException, ParseException, JAXBException, IllegalAccessException, InvocationTargetException, IOException
	{
	
		switch (whatToProcess) {
		case "ANIMATE-IN-SPLIT-DB": case "ANIMATE-FF_GRAPHICS": case "ANIMATE-LT_GRAPHICS":
		case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP": 
		case "ANIMATE-IN-LTMATCH_IDENT": case "ANIMATE-IN-FINAL_MATCH_SUMMARY":
		case "ANIMATE-IN-INFOBAR": case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": 
		case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
		case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER_SINGLE": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": 
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY":
		case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-IDENT":
		case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BUG-BOWLER":
		case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUGTARGET": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY":
		case "ANIMATE-IN-LANDMARK":	case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH": case "ANIMATE-IN-L3MATCH_PROMO":
		case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-OUT-DIRECTOR":
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-GENERIC":
		case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM": case "ANIMATE-OUT-SECTION2":
		case "ANIMATE-OUT-SECTION4_N_5": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-LAST_YEAR_STANDING": case "ANIMATE-IN-FF_ROWS_CAPTAINS":
		case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-THISSERIES":
		case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-LINEUP":
		case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-LTMANHATTAN": case "ANIMATE-OUT-POWERPLAY": case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-PLAYOFFS": case "ANIMATE-IN-BATGRIFF":
		case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-BALL_PERFORMER": case "TICKER_LT_OUT": case "TICKER_LT_IN": case "ANIMATE-IN-MOST": case "ANIMATE-IN-INN_BUILDER": 	
		case"ANIMATE-IN-BUG-TOSS": case "ANIMATE-IN-BUG-RESULT": case "ANIMATE-IN-BUG-TARGET":
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-SPLIT-DB": case "ANIMATE-FF_GRAPHICS": case "ANIMATE-LT_GRAPHICS":
			case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP":
			case "ANIMATE-IN-FINAL_MATCH_SUMMARY":
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PLAYERPROFILE": 
			case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-POSITION_LANDMARK": 
			case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE":
			case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM":
			case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET":
			case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-LAST_YEAR_STANDING": case "ANIMATE-IN-FF_ROWS_CAPTAINS":
			case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-BUGPARTNERSHIP":
			case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-PLAYOFFS":  case "ANIMATE-IN-BUG-RESULT": case "ANIMATE-IN-BUG-TARGET":
				case"ANIMATE-IN-BUG-TOSS": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-BALL_PERFORMER": case "ANIMATE-IN-MOST":
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					AnimateInGraphics(print_writer, "FF_IN");
					TimeUnit.MILLISECONDS.sleep(200);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					AnimateInGraphics(print_writer, "FF_IN");
				}
				break;
			
			case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS": case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER_SINGLE": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED": 
			case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": 
			case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT":   case "ANIMATE-IN-BOWLERSUMMARY": 
			case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY": case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-BATSMAN_THIS_MATCH": 
			case "ANIMATE-IN-BOWLER_THIS_MATCH": case "ANIMATE-IN-L3MATCH_PROMO": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": 
			case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-GENERIC": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-THISSERIES":
			case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-LINEUP": case "ANIMATE-IN-LTMANHATTAN": case "ANIMATE-IN-INN_BUILDER": case "ANIMATE-IN-LTMATCH_IDENT":
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
			case "ANIMATE-FF_GRAPHICS":
				 processAnimation(print_writer, "In", "START", broadcaster);
				 which_graphic_on_screen = "FF_GRAPHICS";
				 break;
			case "ANIMATE-LT_GRAPHICS":
				 processAnimation(print_writer, "In", "START", broadcaster);
				 which_graphic_on_screen = "LT_GRAPHICS";
				 break;	 
			case "ANIMATE-IN-FFTHISSERIES":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "FFTHISSERIES";
				break;
			case "ANIMATE-IN-SCHEDULE":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "SCHEDULE";
				break;
			case "ANIMATE-IN-BUG-RESULT":
				processAnimation(print_writer, "In", "START", "EVEREST_APL_T20");
				which_graphic_on_screen = "BUG-RESULT";
				break;
			case "ANIMATE-IN-BUG-TARGET":
				processAnimation(print_writer, "In", "START", "EVEREST_APL_T20");
				which_graphic_on_screen = "BUG-TARGET";
				break;
			case "ANIMATE-IN-BUG-TOSS":
				processAnimation(print_writer, "In", "START", "EVEREST_APL_T20");
				which_graphic_on_screen = "BUG-TOSS";
				break;
			case "ANIMATE-IN-LAST_YEAR_STANDING":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "LAST_YEAR_STANDING";
				break;
			case "ANIMATE-IN-FF_ROWS_CAPTAINS":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "FF_ROWS_CAPTAINS";
				break;
			case "ANIMATE-IN-BUG":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "BUG";
				break;
			case "ANIMATE-IN-BUG-DISMISSAL":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "BUG-DISMISSAL";
				break;
			case "ANIMATE-IN-SPLIT-DB":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "SPLIT-DB";
				break;
			case "ANIMATE-IN-BUG_POWERPLAY":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "BUG_POWERPLAY";
				break;
			case "ANIMATE-IN-BUG_PARTNERSHIP":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "BUG_PARTNERSHIP";
				break;
			case "ANIMATE-IN-BUG_HIGHLIGHT":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "BUG_HIGHLIGHT";
				break;
			case "ANIMATE-IN-BUG-BOWLER":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "BUG-BOWLER";
				break;
			case "ANIMATE-IN-MULTI_PARTNERSHIP":
				processAnimation(print_writer, "In","START", broadcaster);
//				AnimateInGraphics(print_writer, "MULTI-PARTNERSHIP");
				which_graphic_on_screen = "MULTI-PARTNERSHIP";
				break;
			case "ANIMATE-IN-PLAYERPROFILE":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "PLAYERPROFILE";
				break;
			case "ANIMATE-IN-LEADERBOARD":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "LEADERBOARD";
				break;
			case "ANIMATE-IN-MOST":
				processAnimation(print_writer, "In","START", broadcaster);
				which_graphic_on_screen = "MOST";
				break;
			case "ANIMATE-IN-BUG-DB":
				processAnimation(print_writer, "In","START", broadcaster);
				which_graphic_on_screen = "BUG-DB";
				break;
			case "ANIMATE-IN-MATCHID":
				processAnimation(print_writer, "In","START", broadcaster);
				which_graphic_on_screen = "MATCHID";
				break;
			case "ANIMATE-IN-WORM":
				processAnimation(print_writer, "In","START", broadcaster);
				which_graphic_on_screen = "WORM";
				break;
			case "ANIMATE-IN-LTMATCH_IDENT":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "LTMATCH_IDENT";
				break;
			case "ANIMATE-IN-FINAL_MATCH_SUMMARY":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "FINAL_MATCH_SUMMARY";
				break;
			case "ANIMATE-IN-NAMESUPER_SINGLE":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "NAMESUPER_SINGLE";
				break;
			case "ANIMATE-IN-NAMESUPER":
				processAnimation(print_writer, "In", "START", broadcaster);
				which_graphic_on_screen = "NAMESUPER";
				break;
			case "ANIMATE-IN-NAMESUPER-PLAYER":
				processAnimation(print_writer, "In", "START", broadcaster);
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
				case "FF_GRAPHICS":
					AnimateOutGraphics(print_writer, "FF_GRAPHICS");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "LT_GRAPHICS":
			 		AnimateOutGraphics(print_writer, "LT_GRAPHICS");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "LEADERBOARD":
					AnimateOutGraphics(print_writer, "LEADERBOARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "PLAYERPROFILE":
					AnimateOutGraphics(print_writer, "PLAYERPROFILE");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "SCHEDULE":
					AnimateOutGraphics(print_writer, "SCHEDULE");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "LAST_YEAR_STANDING":
					AnimateOutGraphics(print_writer, "LAST_YEAR_STANDING");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "FF_ROWS_CAPTAINS":
					AnimateOutGraphics(print_writer, "FF_ROWS_CAPTAINS");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "MULTI-PARTNERSHIP":
					AnimateOutGraphics(print_writer, "MULTI-PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "NAMESUPER_SINGLE":
					AnimateOutGraphics(print_writer, "NAMESUPER_SINGLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "NAMESUPER-PLAYER":
					AnimateOutGraphics(print_writer, "NAMESUPER-PLAYER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG-TOSS":
					AnimateOutGraphics(print_writer, "BUG-TOSS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG-TARGET":
					AnimateOutGraphics(print_writer, "BUG-TARGET");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG-RESULT":
					AnimateOutGraphics(print_writer, "BUG-RESULT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "LTMATCH_IDENT":
					AnimateOutGraphics(print_writer, "LTMATCH_IDENT");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "FINAL_MATCH_SUMMARY":
					AnimateOutGraphics(print_writer, "FINAL_MATCH_SUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "BUG-DISMISSAL":
					AnimateOutGraphics(print_writer, "BUG-DISMISSAL");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "FFTHISSERIES":
					AnimateOutGraphics(print_writer, "FFTHISSERIES");
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
				case "SPLIT-DB":
					AnimateOutGraphics(print_writer, "SPLIT-DB");
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
		case "EXCEL_FF_GRAPHICS_OPTION":
			return new ObjectMapper().writeValueAsString(CricketFunctions.ReadExcel(CricketUtil.FF_EXCEL).keySet()).toString();
		case "EXCEL_LT_GRAPHICS_OPTION":
			return new ObjectMapper().writeValueAsString(CricketFunctions.ReadExcel(CricketUtil.LT_EXCEL).keySet()).toString();	
			
		case "BUG_GRAPHICS-OPTIONS": case "HOWOUT_GRAPHICS-OPTIONS": case "BATSMANSTATS_GRAPHICS-OPTIONS": case "BOWLERSTATS_GRAPHICS-OPTIONS": case "NAMESUPER_PLAYER_GRAPHICS-OPTIONS": 
		case "L3PLAYERPROFILE_GRAPHICS-OPTIONS": case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "BOTTOMLEFT_GRAPHICS-OPTIONS": case "BOTTOMRIGHT_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": 
		case "COMPARISION-GRAPHICS-OPTIONS": case "BOTTOM_GRAPHICS-OPTIONS": case "ANIMATE_PLAYINGXI-OPTIONS": case "PROJECTED_GRAPHICS-OPTIONS": case "TARGET_GRAPHICS-OPTIONS": 
		case "PLAYERSUMMARY_GRAPHICS-OPTIONS": case "BUG_DISMISSAL_GRAPHICS-OPTIONS": case "TOP_GRAPHICS-OPTIONS": case "BUG_BOWLER_GRAPHICS-OPTIONS": case "HOWOUT_WITHOUT_FIELDER_GRAPHICS-OPTIONS":
		case "BOWLERDETAILS_GRAPHICS-OPTIONS":	case "NEXTTOBAT_GRAPHICS-OPTIONS": case "BOWLERSUMMARY_GRAPHICS-OPTIONS": case "LANDMARK_GRAPHICS-OPTIONS": case "EQUATION_GRAPHICS-OPTIONS":
		case "POSITION_LANDMARK_GRAPHICS-OPTIONS": case "BATSMAN_THIS_MATCH_GRAPHICS-OPTIONS": case "BOWLER_THIS_MATCH_GRAPHICS-OPTIONS": case "PLAYERS_GRAPHICS-OPTIONS":
		case "BATSMAN_STYLE_GRAPHICS-OPTIONS": case "RIGHT_GRAPHICS-OPTIONS": case "THISSERIES-STATS_GRAPHICS-OPTIONS": case "FF_THISSERIES-STATS_GRAPHICS-OPTIONS": 
			return match;
		case "SPLIT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getSplit()).toString();
		case "MOST_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getTeams()).toString();
		case "NAMESUPER_GRAPHICS-OPTIONS": case "NAMESUPER_GRAPHICS_SINGLELINE-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "L3_MATCH-PROMO_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
		case "BUG_DB_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getBugs()).toString();
		case "BUG_DB2_GRAPHICS-OPTIONS":
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
			for (Tournament t : tourn_stats) {
			    System.out.println(
			        "ID=" + t.getPlayerId() +
			        " PLAYER=" + t.getPlayer()
			    );
			}

			String json = new ObjectMapper().writeValueAsString(tourn_stats);
			System.out.println(json);

			return json;
		
		case "POPULATE-BUG_POWERPLAY": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-BUG_PARTNERSHIP": case "POPULATE-MULTI_PARTNERSHIP":
		case "POPULATE-WORM": case "POPULATE-MOST_RUNS": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-BUG": case "POPULATE-L3-BUG-BOWLER":
		case "POPULATE-L3-BUG-DB": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-FF-MATCHID":
		case "POPULATE-LTMATCH_IDENT": case "POPULATE-SCHEDULE": case "POPULATE-FF-LEADERBOARD": case "POPULATE-FF-PLAYERPROFILE":
		case "POPULATE-FF-THISSERIES": case "POPULATE-FINAL_MATCH_SUMMARY": case "POPULATE-LAST_YEAR_STANDING": case "POPULATE-FF_ROWS_CAPTAINS":
		case "POPULATE-L3-NAMESUPER-SINGLE": case "POPULATE-L3-SPLIT-DB": case "POPULATE-FF_GRAPHICS": case "POPULATE-LT_GRAPHICS":
		case"POPULATE-L3-BUG-TOSS": case "POPULATE-L3-BUG-RESULT": case "POPULATE-L3-BUG-TARGET":
			
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
			case "POPULATE-FF-THISSERIES":
				populateFFThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
						,match, broadcaster);
				break;
			case "POPULATE-LAST_YEAR_STANDING": case "POPULATE-FF_ROWS_CAPTAINS":
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 143.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				break;
			case "POPULATE-SCHEDULE":
				populateSchedule(print_writer, valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-SPLIT-DB":
				for(Split split : cricketService.getSplit()) {
					  if(split.getSplitId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateSplitDB(print_writer, valueToProcess.split(",")[0], split, match, broadcaster);
					  }
					}
				break;
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
//			case "POPULATE-BUGPARTNERSHIP":
//				populateBugPartnership(print_writer, valueToProcess.split(",")[0],match, broadcaster);
//				break;	
			case "POPULATE-WORM":
				//populateWorm(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
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
			case "POPULATE-L3-BUG-TOSS":
				populateBugToss(print_writer,valueToProcess.split(",")[0],valueToProcess.split(",")[1],valueToProcess.split(",")[2], match, broadcaster);
				break;
			case "POPULATE-L3-BUG-TARGET":
				populateBugTarget(print_writer, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-L3-BUG-RESULT":
				populateBugResult(print_writer, match, broadcaster);
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
			case "POPULATE-FF_GRAPHICS":
				 populateFF(whatToProcess,valueToProcess.substring(valueToProcess.lastIndexOf(",")+1),print_writer,cricketService.getAllPlayer(),config);
				 break;
			case "POPULATE-LT_GRAPHICS":
				 populateLT(whatToProcess,valueToProcess.substring(valueToProcess.lastIndexOf(",")+1),print_writer,config);
				 break;	 
			case "POPULATE-L3-NAMESUPER-SINGLE":
				for(NameSuper ns : cricketService.getNameSupers()) {
					  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateNameSuperSingle(print_writer, valueToProcess.split(",")[0], ns, match, broadcaster);
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
				System.out.println("valuetoprocess" + valueToProcess);
				populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster,cricketService);
				break;
			case "POPULATE-FF-LEADERBOARD":
				populateLeaderBoard(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster, config);
				break;
			case "POPULATE-FF-PLAYERPROFILE":
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
						}
					}
				}
				break;	
			case "POPULATE-FF-MATCHID":
				populateMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-LTMATCH_IDENT":
				populateLtMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster,cricketService.getVariousTexts());
				break;
			case "POPULATE-FINAL_MATCH_SUMMARY":
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 144.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
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
		case "BUG_HIGHLIGHT": case "BUG_PARTNERSHIP": case "BUG_POWERPLAY": case "MATCHID": case "MOST": case "LTMATCH_IDENT": case "SCHEDULE": case "PLAYERPROFILE":
		case "LEADERBOARD":	case "FFTHISSERIES": case "FINAL_MATCH_SUMMARY": case "LAST_YEAR_STANDING": case "FF_ROWS_CAPTAINS": case "NAMESUPER_SINGLE": case "SPLIT-DB":
		case "FF_GRAPHICS": case "LT_GRAPHICS":	case"BUG-RESULT": case"BUG-TOSS": case"BUG-TARGET":
			processAnimation(print_writer, "Out", "START", broadcaster);
			TimeUnit.SECONDS.sleep(1);
			break;
		}	
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20": 
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
	
	private void populateFF(String whatToProcess, String ValueToProcess, PrintWriter printWriter, List<Player> allPlayer, Configuration config) throws InterruptedException {
        Map<String, Object> rowData = CricketFunctions.ReadExcel(CricketUtil.FF_EXCEL).get(ValueToProcess);

        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 " + 
    	        (rowData.get("HEADER") != null ? rowData.get("HEADER").toString().trim() : "") + ";");
        
        if ((rowData.get("IS NAME?") != null ? rowData.get("IS NAME?").toString().trim() : "").toString().equalsIgnoreCase("Y")) {
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " +  (rowData.get("SUB HEADER") != null ? rowData.get("SUB HEADER").toString().trim() : "") + ";");
        } else {
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02  ;");
        }
        
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader  ;");

//        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$LeftLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
//				+ "Videos/Logos/Left/" + (rowData.get("LOGO") != null ? rowData.get("LOGO").toString().trim() : "") +"/000000.dds;");
        
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + (rowData.get("LOGO") != null ? rowData.get("LOGO").toString().trim() : "")
        		+ CricketUtil.PNG_EXTENSION + ";");
		
//		if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
//            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + kpi_photo_path +
//            		(rowData.get("PHOTO") != null ? rowData.get("PHOTO").toString().trim() : "") + CricketUtil.PNG_EXTENSION + ";");
//        } else {
////            if (!new File("\\\\" + config.getPrimaryIpAddress() + CricketUtil.DOUBLE_BACKSLASH + kpi_photo_path + 
////            		(rowData.get("PHOTO") != null ? rowData.get("PHOTO").toString().trim() : "") + CricketUtil.PNG_EXTENSION).exists()) {
////                this.status = CricketUtil.UNSUCCESSFUL;
////            }
//            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\" + config.getPrimaryIpAddress() + CricketUtil.DOUBLE_BACKSLASH + kpi_photo_path.replace("C:", "c") +
//            		CricketUtil.DOUBLE_BACKSLASH + (rowData.get("PHOTO") != null ? rowData.get("PHOTO").toString().trim() : "") + CricketUtil.PNG_EXTENSION + ";");
//        }
		
		int cols = rowData.get("COLS") != null ? (rowData.get("COLS") instanceof Number ? ((Number) rowData.get("COLS")).intValue() : Integer.parseInt(rowData.get("COLS").toString().trim())) : 0;
        int rows = rowData.get("ROWS") != null ? (rowData.get("ROWS") instanceof Number ? ((Number) rowData.get("ROWS")).intValue() : Integer.parseInt(rowData.get("ROWS").toString().trim())) : 0;
        
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows " + cols + " ;");
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + rows + " ;");

        // Header of table
        for (int j = 1; j <= cols; j++) {
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead0" + j + " " + 
            		(rowData.get("H" + j) != null ? rowData.get("H" + j).toString().trim() : "") + ";");
        }

        // Body of table
        for (int i = 1; i <= rows; i++) {
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "A " + (rowData.get("R" + i + ".1") != null ? rowData.get("R" + i + ".1").toString().trim() : "") + ";");
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "B " + (rowData.get("R" + i + ".2") != null ? rowData.get("R" + i + ".2").toString().trim() : "") + ";");
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "C " + (rowData.get("R" + i + ".3") != null ? rowData.get("R" + i + ".3").toString().trim() : "") + ";");
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "D " + (rowData.get("R" + i + ".4") != null ? rowData.get("R" + i + ".3").toString().trim() : "") + ";");
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "E " + (rowData.get("R" + i + ".5") != null ? rowData.get("R" + i + ".3").toString().trim() : "") + ";");

        }
        
	  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 160.0;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
	  printWriter.
	  println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
	  printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
	  TimeUnit.SECONDS.sleep(1);
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
	  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

}
	
	private void populateLT(String whatToProcess, String ValueToProcess, PrintWriter printWriter, Configuration config) throws InterruptedException {
        Map<String, Object> rowData =  CricketFunctions.ReadExcel(CricketUtil.LT_EXCEL).get(ValueToProcess);
        System.out.println(rowData.toString());
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName " + 
    	        (rowData.get("HEADER") != null ? rowData.get("HEADER").toString().trim() : "") + ";");
        
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 " + 
    	        (rowData.get("SUB HEADER") != null ? rowData.get("SUB HEADER").toString().trim() : "") + ";");	
        
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore ;");	
        
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers ;");	
        
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + (rowData.get("LOGO") != null ? rowData.get("LOGO").toString().trim() : "")
        		+ CricketUtil.PNG_EXTENSION + ";");
        
        int cols = rowData.get("COLS") != null ? (rowData.get("COLS") instanceof Number ? ((Number) rowData.get("COLS")).intValue() : Integer.parseInt(rowData.get("COLS").toString().trim())) : 0;
        int rows = rowData.get("ROWS") != null ? (rowData.get("ROWS") instanceof Number ? ((Number) rowData.get("ROWS")).intValue() : Integer.parseInt(rowData.get("ROWS").toString().trim())) : 0;
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows " + cols + " ;");
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + rows + " ;");

        // Header of table
        
        for (int j = 1; j <= cols; j++) {
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead0" + j + " " + 
            		(rowData.get("H" + j) != null ? rowData.get("H" + j).toString().trim() : "") + ";");
        }

        // Body of table
        
        for (int i = 1; i <= rows; i++) {
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "A " + (rowData.get("R" + i + ".1") != null ? rowData.get("R" + i + ".1").toString().trim() : "") + ";");
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "B " + (rowData.get("R" + i + ".2") != null ? rowData.get("R" + i + ".2").toString().trim() : "") + ";");
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "C " + (rowData.get("R" + i + ".3") != null ? rowData.get("R" + i + ".3").toString().trim() : "") + ";");
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "D " + (rowData.get("R" + i + ".4") != null ? rowData.get("R" + i + ".4").toString().trim() : "") + ";");
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "E " + (rowData.get("R" + i + ".5") != null ? rowData.get("R" + i + ".5").toString().trim() : "") + ";");

        }
        
	  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 150.0;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
	  printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
	  printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
	  TimeUnit.SECONDS.sleep(1);
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
	  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
	  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
	}
	
	public void populateBugDismissal(PrintWriter print_writer, String viz_scene, int whichInning, String statsType,
			int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");

				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BATSMAN:
							for (BattingCard bc : inn.getBattingCard()) {
								if (bc.getPlayerId() == playerId) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path + 
											inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");

									print_writer.println(
											"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "
													+ bc.getPlayer().getTicker_name() + "       " + bc.getHowOutText() + "  " + bc.getRuns() + " (" + bc.getBalls() + ")" + ";");
//									print_writer
//											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
//													+ bc.getHowOutText() + ";");
//									print_writer
//											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
//													+ "  " + bc.getRuns() + " (" + bc.getBalls() + ")" + ";");
//									print_writer
//											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
//													+ "" + ";");
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
	
	public void populateBugResult(PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				
				if(!CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "", session_selected_broadcaster, false).getTargetOrResult().toUpperCase().contains(" TIED")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + CricketFunctions.GenerateMatchSummaryStatus(2, match, 
							CricketUtil.FULL, "", session_selected_broadcaster, false).getTargetOrResult().toUpperCase() + ";");
				}else {
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + "SUPER OVER TIED - ANOTHER SUPER OVER TO FOLLOW" + ";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + "MATCH TIED - WINNER WILL BE DECIDED BY SUPER OVER" + ";");
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 29.0;");
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
	
	public void populateBugTarget(PrintWriter print_writer, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				
				String data = "", teamNameAsCity="";
				
				if(whichInning == 2) {
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getInningNumber() == whichInning) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + 
									inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
							teamNameAsCity = inn.getBatting_team().getTeamName1();
						}
					}
					
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && match.getSetup().getMaxOvers() == 1) {
						data = teamNameAsCity + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN THE SUPER OVER";
					}else {
						if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
							data = teamNameAsCity + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM " + 
									CricketFunctions.GetTargetData(match).getTargetOvers()+ " OVERS";
						}else {
							data = teamNameAsCity + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM " + 
									CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" + 
									(match.getSetup().getTargetType().equalsIgnoreCase("VJD")?" (VJD)":" (DLS)");
						}
					}
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + data + ";");
				}
				
				
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 44.0;");
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
	
	public void populateBugToss(PrintWriter print_writer, String string, String teamid,String text, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		 case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				
				String data = "",teamName = "";
				System.out.println("teamid = " + teamid);
				if(match.getSetup().getHomeTeamId()== Integer.valueOf(teamid)) {
					data = match.getSetup().getHomeTeam().getTeamName1() + " WON THE TOSS & ";
					teamName = match.getSetup().getHomeTeam().getTeamBadge();
				}else {
					data = match.getSetup().getAwayTeam().getTeamName1() + " WON THE TOSS & ";
					teamName = match.getSetup().getAwayTeam().getTeamBadge();
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + teamName + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + data + text + ";");
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 44.0;");
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
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");

				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BATSMAN:
							for (BattingCard bc : inn.getBattingCard()) {
								if (bc.getPlayerId() == playerId) {
//									print_writer.println(
//											"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "
//													+ bc.getPlayer().getTicker_name() + "       " + ";");
//									print_writer
//											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
//													+ "4s : " + bc.getFours() + " 6s : " + bc.getSixes() + ";");

									if (bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println(
												"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "
														+ bc.getPlayer().getTicker_name() + "       "  + "  " + bc.getRuns() + "* (" + bc.getBalls() + ")" + ";");
									} else {
										print_writer.println(
												"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "
														+ bc.getPlayer().getTicker_name() + "       "  + "  " + bc.getRuns() + " (" + bc.getBalls() + ")" + ";");
									}
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
													+ "S/R : " + bc.getStrikeRate() + ";");
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
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BOWLER:
							for (BowlingCard boc : inn.getBowlingCard()) {
								if (boc.getPlayerId() == playerId) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path + 
											inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
									print_writer.println(
											"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "
													+ boc.getPlayer().getTicker_name() + "       " + boc.getWickets() + slashOrDash + boc.getRuns() + " " + "(" + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) +")" + ";");
//									print_writer
//											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
//													+ CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " Overs" + ";");
//
//									print_writer
//											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
//													+ boc.getWickets() + slashOrDash + boc.getRuns() + ";");
//									print_writer
//									.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
//											+ "" + ";");
									
//									print_writer
//											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
//													+ "Over" + CricketFunctions.Plural(Integer.valueOf(CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()))) + ";");
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
	public void populateSplitDB(PrintWriter print_writer, String viz_scene, Split Split, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				if(Split.getText1() != null && Split.getText2() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "+ Split.getText1() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName02 "+ Split.getText2() + ";");
					
				}

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 35.0;");
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
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
				for (Inning inn : match.getMatch().getInning()) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + "POWERPLAY" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
							+ getPowerPlayScore(match,inn, whichInning, match.getEventFile().getEvents()) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + "" + ";");

					if (whichInning == 1) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamName3() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + ";");
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
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						String Left_Batsman ="",Right_Batsman="";
						
						Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getTicker_name();
						Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getTicker_name();
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + "CURRENT PARTNERSHIP" + "       " + ";");

						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + " & " + Right_Batsman + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + " (" +
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + 
								Left_Batsman + " " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + " (" + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ")" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + 
								Right_Batsman + " " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + " (" + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ")" + ";");
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
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == whichinning) {
						String Left_Batsman ="",Right_Batsman="";
						
						Left_Batsman = inn.getPartnerships().get(partnership - 1).getFirstPlayer().getTicker_name();
						Right_Batsman = inn.getPartnerships().get(partnership - 1).getSecondPlayer().getTicker_name();
						
						if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 1) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + 
									(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "st WICKET PARTNERSHIP" + ";");
						}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 2) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + 
									(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "nd WICKET PARTNERSHIP" + ";");
						}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 3) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + 
									(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "rd WICKET PARTNERSHIP" + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + 
									(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "th WICKET PARTNERSHIP" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
								inn.getPartnerships().get(partnership - 1).getTotalRuns() + " (" +  inn.getPartnerships().get(partnership - 1).getTotalBalls() + ")" + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + Left_Batsman + " " + 
								inn.getPartnerships().get(partnership - 1).getFirstBatterRuns() + " (" +  inn.getPartnerships().get(partnership - 1).getFirstBatterBalls() + ")" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + Right_Batsman + " " + 
								inn.getPartnerships().get(partnership - 1).getSecondBatterRuns() + " (" +  inn.getPartnerships().get(partnership - 1).getSecondBatterBalls() + ")" + ";");
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
	public void populateBugHighlight(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");

				print_writer
						.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + "HIGHLIGHTS" + "       " + ";");

				if (whichInning == 1) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
							+ match.getMatch().getInning().get(0).getBatting_team().getTeamName3().toUpperCase() + ";");
					if (match.getMatch().getInning().get(0).getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
								+ match.getMatch().getInning().get(0).getTotalRuns() + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(),
										match.getMatch().getInning().get(0).getTotalBalls()) + ")" + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
								+ match.getMatch().getInning().get(0).getTotalRuns() + " - "
								+ match.getMatch().getInning().get(0).getTotalWickets() + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(),
										match.getMatch().getInning().get(0).getTotalBalls()) + ")" + ";");
					}
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
							+ match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + ";");
					if (match.getMatch().getInning().get(1).getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
								+ match.getMatch().getInning().get(1).getTotalRuns() + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(),
										match.getMatch().getInning().get(1).getTotalBalls()) + ")" + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
								+ match.getMatch().getInning().get(1).getTotalRuns() + " - "
								+ match.getMatch().getInning().get(1).getTotalWickets() + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(),
										match.getMatch().getInning().get(1).getTotalBalls()) + ")" + ";");
					}
					
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + "" + ";");

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
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
			switch(Type.toUpperCase()) {
			case "RUNS":
				int row_no=0;
				double strike_rate = 0;
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
//						+ StatType.toUpperCase() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
						+ "MOST RUNS" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
						+ match.getSetup().getTournament() + ";");
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows 4;");
				
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 ;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 MATCHES;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 RUNS;");
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 S/R;");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamId() == Integer.valueOf(StatType)) {
						if(tournament.get(i).getRuns() > 0) {
							row_no = row_no + 1;
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (row_no-1) + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + 
									CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + row_no + ";");
							strike_rate = tournament.get(i).getRuns() * 100;
							strike_rate = strike_rate/tournament.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							if(row_no<10) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getPlayer().getTicker_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + tournament.get(i).getMatches() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "C " + tournament.get(i).getRuns() + ";");
								if(tournament.get(i).getBallsFaced() == 0 || tournament.get(i).getRuns() == 0) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "D " + "-" + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "D " + df.format(strike_rate) + ";");
								}
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getPlayer().getTicker_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + tournament.get(i).getMatches() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "C " + tournament.get(i).getRuns() + ";");
								if(tournament.get(i).getBallsFaced() == 0 || tournament.get(i).getRuns() == 0) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row_no + "D " + "-" + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row_no + "D " + df.format(strike_rate) + ";");
								}
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
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
//						+ StatType.toUpperCase() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
						+ "MOST WICKETS" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
						+ match.getSetup().getTournament() + ";");
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows 4;");

				Collections.sort(tournament, new CricketFunctions.BowlerWicketsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 PLAYERS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 MATCHES;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 WICKETS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 ECONOMY;");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1().equalsIgnoreCase(StatType.toUpperCase())) {
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
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");

//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 "
//						+ "" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
						+ match.getSetup().getMatchIdent().toUpperCase() + ";");
				// print_writer.println("LAYER1*EVEREST*TREEVIEW*Subheader1*CONTAINER SET ACTIVE
				// 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
						+ match.getSetup().getTournament() + ";");
				/*
				 * print_writer.
				 * println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " +
				 * logo_path_ns + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				 */
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeLogo " + logo_path
						+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
						match.getSetup().getHomeTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayLogo " + logo_path
						+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "LIVE FROM "
						+ match.getSetup().getVenueName().toUpperCase() + ";");
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");

				
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
	public void populateSchedule(PrintWriter print_writer, String viz_scene, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
//						+ "SHER-E-PUNJAB T20 CUP" + ";");
//				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader "
//						+ "SCHEDULE" + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData01 "
//						+ "LEAGUE STAGE - 13th JULY to 27th JULY" + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData02 " + 
//						"PLAYOFFS - 28th JULY" + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData03 " + 
//						"FINAL TO BE PLAYED ON 30th JULY" + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path_ns + "TLogo" + CricketUtil.PNG_EXTENSION + ";");

				
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 90.0;");
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
	public void populateLtMatchId(PrintWriter print_writer, String viz_scene, MatchAllData match,
			String session_selected_broadcaster,List<VariousText> vartxt) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
						+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 " + 
						match.getSetup().getHomeTeam().getTeamName2() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 " + 
						match.getSetup().getHomeTeam().getTeamName3() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName02 " + 
						match.getSetup().getAwayTeam().getTeamName2() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName02 " + 
						match.getSetup().getAwayTeam().getTeamName3() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path
						+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 "
						+ match.getSetup().getMatchIdent() + ";");
				for(VariousText vtext : vartxt) {
					if(vtext.getVariousType().equalsIgnoreCase("LT_MATCH_ID") && vtext.getUseThis().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + vtext.getVariousText() + ";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "LIVE FROM "
								+ match.getSetup().getVenueName().toUpperCase() + ";");
					}
				}

				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 67.0;");
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
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
				
				if (bug.getText1() != null && bug.getText2() != null && bug.getText3() != null && bug.getText4() != null) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
									+ bug.getText1().toUpperCase() + "       " + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
									+ bug.getText2().toUpperCase() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
									+ bug.getText3().toUpperCase() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
									+ bug.getText4().toUpperCase() + ";");
				} else if (bug.getText1() != null && bug.getText2() != null && bug.getText3() != null) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
									+ bug.getText1().toUpperCase() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
									+ bug.getText2().toUpperCase() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
									+ bug.getText3().toUpperCase() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
									+ "" + ";");
				} else if(bug.getText1() != null && bug.getText2() != null) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
									+ bug.getText1().toUpperCase() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
									+ bug.getText2().toUpperCase() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
									+ "" + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
									+ "" + ";");
				}else if(bug.getText1() != null) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
									+ bug.getText1().toUpperCase() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
									+ "" + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
									+ "" + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
									+ "" + ";");
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
	public void populateNameSuperSingle(PrintWriter print_writer, String viz_scene, NameSuper ns, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
				
				if(ns.getSponsor() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
							+ ns.getSponsor().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
							+ "TLogo" + CricketUtil.PNG_EXTENSION + ";");
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
	
	public void populateNameSuper(PrintWriter print_writer, String viz_scene, NameSuper ns, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
				
				if(ns.getSponsor() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
							+ ns.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
							+ "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				}
				
				if (ns.getFirstname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 "
							+ "" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 "
							+ ns.getSurname() + ";");
				} else if (ns.getSurname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 "
							+ ns.getFirstname() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 "
							+ "" + ";");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 "
							+ ns.getFirstname() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 "
							+ ns.getSurname() + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
						+ "" + ";");
				
				if(ns.getSubLine() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ ns.getSubLine().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo ;");
				}

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

	public void populateNameSuperPlayer(PrintWriter print_writer, String viz_scene,String captainWicketKeeper, int playerId, MatchAllData match, 
			String session_selected_broadcaster,CricketService cricketService)
			throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
				String Home_or_Away = "";
				Player player = cricketService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == playerId).findAny().orElse(null);
				Team team = cricketService.getTeams().stream().filter(tm -> tm.getTeamId() == player.getTeamId()).findAny().orElse(null);

				if (team.getTeamId() == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
							+ match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					Home_or_Away = match.getSetup().getHomeTeam().getTeamName1();
					
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
							+ match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 "
						+ player.getFirstname() + ";");
				if (player.getSurname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 "
							+ "" + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 "
							+ player.getSurname() + ";");
				}
				if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
				}

				switch (captainWicketKeeper.toUpperCase()) {
				case CricketUtil.CAPTAIN:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ captainWicketKeeper.toUpperCase() + " , " + Home_or_Away + ";");
					break;
				case "PLAYER OF THE MATCH":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ captainWicketKeeper.toUpperCase() + ";");
					break;
				case "PLAYER OF THE TOURNAMENT":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ captainWicketKeeper.toUpperCase() + ";");
				break;
				case CricketUtil.WICKET_KEEPER:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ "WICKET KEEPER" + " , " + Home_or_Away + ";");
					break;
				case "CAPTAIN-WICKETKEEPER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ "CAPTAIN & WICKET KEEPER" + " , " + Home_or_Away + ";");
					break;
				}
				
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
	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
			this.status = CricketUtil.SUCCESSFUL;
			int row_no=0;
			
//			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + 
//					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Logo02 " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			
			switch(StatType.toUpperCase()) {
			case "MOST_RUNS_DATA":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST RUNS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\APL\\Photos\\" +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+"\\\\"+ tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\APL\\Photos\\" + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
							
							
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + " " + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName01" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_RUNS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST RUNS " + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 " + "MOST " + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "RUNS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\APL\\Photos\\" +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+"\\\\"+ tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
//						if(tournament.get(i).getPlayer().getSurname() != null) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
//						}else {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + "" + ";");
//						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName01" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getRuns() + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_WICKETS":
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST WICKETS " + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 " + "MOST " + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "WICKETS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\APL\\Photos\\" +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+"\\\\"+ tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
//						if(tournament.get(i).getPlayer().getSurname() != null) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
//						}else {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + "" + ";");
//						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName01" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getWickets() + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_FOURS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST FOURS " + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 " + "MOST " + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "FOURS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\APL\\Photos\\" +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+"\\\\"+ tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
//						if(tournament.get(i).getPlayer().getSurname() != null) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
//						}else {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + "" + ";");
//						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName01" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getFours() + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_SIXES":
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST SIXES " + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "MOST " + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "SIXES " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\APL\\Photos\\" +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+"\\\\"+ tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
//						if(tournament.get(i).getPlayer().getSurname() != null) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
//						}else {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + "" + ";");
//						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName01" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getSixes() + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
					
		}
	}
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
			
		case "EVEREST_APL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path + ";");
			//System.out.println("Fours = " + stats.getTournament_fours() + " Sixes = " + stats.getTournament_sixes());
			double strike_rate = 0 , economy_rate=0;
			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + "PLAYER PROFILE" + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + match.getSetup().getHomeTeam().getTeamName4()+"\\\\"+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFistName " + plyr.getFirstname() + ";");
				if(plyr.getSurname() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
				}
			}
			else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + match.getSetup().getAwayTeam().getTeamName4()+"\\\\"+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFistName " + plyr.getFirstname() + ";");
				if(plyr.getSurname() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
				}
			}
			
			if(Profile.equalsIgnoreCase("DT20")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerHand "+ "T20" + " CAREER" + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerHand "+ Profile + " CAREER" + ";");
			}
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge "+ " " + ";");
			
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam01 "+ playerStyle(TypeofProfile.toUpperCase(), plyr.getBattingStyle())+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 "+"MATCHES"+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 "+stats.getMatches()+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 "+"RUNS"+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + stats.getRuns()+ ";");
				
				strike_rate = stats.getRuns() * 100;
				strike_rate = strike_rate/stats.getBalls_faced();
				DecimalFormat df = new DecimalFormat("0.0");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 "+"STRIKE RATE"+";");
				if(stats.getRuns()== 0 && stats.getBalls_faced() == 0) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + "-" +";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + df.format(strike_rate) +";");
				}
				
				break;
			case CricketUtil.BOWLER:
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam01 "+ playerStyle(TypeofProfile.toUpperCase(), plyr.getBowlingStyle())+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 "+"MATCHES"+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 "+stats.getMatches()+ ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 "+"WICKETS"+";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 "+stats.getWickets() + ";");
				
				economy_rate = stats.getRuns_conceded() / stats.getBalls_bowled();
				DecimalFormat df_b = new DecimalFormat("0.00");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 "+"ECONOMY"+";");
				if(stats.getRuns_conceded() == 0 && stats.getBalls_bowled() == 0) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 "+ "-" +";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 "+ df_b.format(economy_rate) +";");
				}
				
				break;
			}
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tEventName " + stats.getStats_type().getStats_short_name() + ";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

			this.status = CricketUtil.SUCCESSFUL;

			}
			break;
		}
		
	}
	public void populateFFThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {

			double strike_rate = 0 , economy_rate=0;
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + "" + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerHand "+ "THIS SERIES" + ";");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + match.getSetup().getHomeTeam().getTeamName4()
								+"\\\\"+ this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + match.getSetup().getAwayTeam().getTeamName4()
								+"\\\\"+ this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
					}
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFistName " + this_series.get(i).getPlayer().getFirstname() + ";");
					if(this_series.get(i).getPlayer().getSurname() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + this_series.get(i).getPlayer().getSurname() + ";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
					}
					
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						
						if(this_series.get(i).getPlayer().getBattingStyle() != null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam01 " + 
									CricketFunctions.getbattingstyle(this_series.get(i).getPlayer().getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase()+ ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam01 " + "" + ";");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "MATCHES" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + this_series.get(i).getMatches() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "RUNS"+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + this_series.get(i).getRuns() + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 "+"STRIKE RATE"+";");
						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + "-" +";");
						}else {
							strike_rate = this_series.get(i).getRuns() * 100;
							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + df.format(strike_rate) +";");
						}
						
						break;
					case CricketUtil.BOWLER:
						
						if(this_series.get(i).getPlayer().getBowlingStyle() != null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam01 " + 
									CricketFunctions.getbowlingstyle(this_series.get(i).getPlayer().getBowlingStyle()).toUpperCase() + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam01 " + "" + ";");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "MATCHES" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + this_series.get(i).getMatches() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "WICKETS"+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + this_series.get(i).getWickets() + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 "+"ECONOMY"+";");
						
						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + "-" +";");
						}else {
							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + df_b.format(economy_rate) +";");
						}
						break;
					}
					
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			

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
}

	