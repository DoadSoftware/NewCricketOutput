package com.cricket.broadcaster;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBException;
import com.cricket.containers.BattingCardFF;
import com.cricket.containers.BowlingFF;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.controller.IndexController;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Configuration;
import com.cricket.model.Event;
import com.cricket.model.Fixture;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.OverByOverData;
import com.cricket.model.Player;
import com.cricket.model.Pointers;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EVEREST_MPL_T20 extends Scene{

	public String broadcaster = "EVEREST_MPL_T20";
	public String status;
	public String slashOrDash = "-",logocategory = "";
	public String logo_path_ns = "C:\\\\Images\\\\NEPAL_T20\\\\Logos\\\\Sponsor\\\\";
	public String photo_path = "C:\\\\Images\\\\MPL\\\\Photos\\\\";
	private String local_photo_path = "c\\Images\\MPL\\Photos\\";
	public String logo_path = "C:\\\\Images\\\\MPL\\\\Logos\\\\";
	public Infobar infobar = new Infobar(); 
	public String which_graphic_on_screen = "";
	public BattingCardFF bcf = new BattingCardFF();
	public BowlingFF bocf = new BowlingFF();
	
	public EVEREST_MPL_T20() {
		super();
	}

	public EVEREST_MPL_T20(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics, Configuration config,List<HeadToHeadPlayer> head_to_head,
			List<Tournament> past_tournament_stats) throws InterruptedException, ParseException, JAXBException, IllegalAccessException, InvocationTargetException, IOException
	{
		switch (whatToProcess) {
		case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP": case "ANIMATE-IN-LTMATCH_IDENT":
		case "ANIMATE-IN-INFOBAR": case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
		case "ANIMATE-IN-BATSMANSTATS": case "ANIMATE-IN-NAMESUPER_SINGLE":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-DOUBLETEAMS": 
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY":
		case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-IDENT":
		case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BUG-BOWLER":
		case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUGTARGET": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY":
		case "ANIMATE-IN-LANDMARK":	case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH": case "ANIMATE-IN-L3MATCH_PROMO":
		case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-OUT-DIRECTOR":
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-GENERIC":
		case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM": case "ANIMATE-OUT-SECTION2":
		case "ANIMATE-OUT-SECTION4_N_5": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-SCHEDULE": case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-THISSERIES":
		case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FFTHISSERIES_BALL": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBAT":  case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-LINEUP":
		case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-LTMANHATTAN": case "ANIMATE-OUT-POWERPLAY": case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-PLAYOFFS": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-FF_PLAY":
		case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-BALL_PERFORMER": case "TICKER_LT_OUT": case "TICKER_LT_IN": case "ANIMATE-IN-MOST": case "ANIMATE-IN-INN_BUILDER": case "ANIMATE-IN-FF-RULES": case "ANIMATE-FF_SUMMARY_GRAPHICS":
		case "ANIMATE-IN-BUG-RESULT": case "ANIMATE-IN-BUG-TOSS": case "ANIMATE-IN-BUG-TARGET": case "ANIMATE-IN-POINTERS": case "ANIMATE-IN-LT_PHASE_COMP":
			
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP": case "ANIMATE-IN-FF-RULES": case "ANIMATE-FF_SUMMARY_GRAPHICS":
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PLAYERPROFILE":  case "ANIMATE-IN-PLAYERPROFILEBALL":
			case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-POSITION_LANDMARK": 
			case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE":
			case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM":
			case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUG":  case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET":
			case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FFTHISSERIES_BALL": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-BUGPARTNERSHIP":
			case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-PLAYOFFS": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-BALL_PERFORMER": case "ANIMATE-IN-MOST":
			case "ANIMATE-IN-FF_PLAY": case "ANIMATE-IN-BUG-RESULT": case "ANIMATE-IN-BUG-TOSS": case "ANIMATE-IN-BUG-TARGET": case "ANIMATE-IN-POINTERS": case "ANIMATE-IN-LT_PHASE_COMP":
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					AnimateInGraphics(print_writer, "FF_IN");
					TimeUnit.MILLISECONDS.sleep(200);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					AnimateInGraphics(print_writer, "FF_IN");
				}
				break;
			
			case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS": case "ANIMATE-IN-NAMESUPER_SINGLE": case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED": 
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
			case "ANIMATE-IN-FFTHISSERIES":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "FFTHISSERIES";
				break;
			case "ANIMATE-IN-FFTHISSERIES_BALL":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "FFTHISSERIES_BALL";
				break;
			case "ANIMATE-IN-SCHEDULE":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "SCHEDULE";
				break;
			case "ANIMATE-IN-BUG":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG";
				break;
			case "ANIMATE-IN-BUG-DISMISSAL":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG-DISMISSAL";
				break;
			case "ANIMATE-IN-BUG_POWERPLAY":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG_POWERPLAY";
				break;
			case "ANIMATE-IN-BUG-RESULT":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG-RESULT";
				break;
			case "ANIMATE-IN-BUG-TOSS":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG-TOSS";
				break;
			case "ANIMATE-IN-BUG-TARGET":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG-TARGET";
				break;
			case "ANIMATE-IN-LT_PHASE_COMP":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "LT_PHASE_COMP";
				break;
			case "ANIMATE-IN-POINTERS":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "POINTERS";
				break;
			case "ANIMATE-IN-FF_PLAY":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "FF_PLAY";
				break;
			case "ANIMATE-IN-BUG_PARTNERSHIP":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG_PARTNERSHIP";
				break;
			case "ANIMATE-IN-BUG_HIGHLIGHT":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG_HIGHLIGHT";
				break;
			case "ANIMATE-IN-FF-RULES":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "FF-RULES";
				break;
			case "ANIMATE-FF_SUMMARY_GRAPHICS":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "FF_SUMMARY_GRAPHICS";
				break;
			case "ANIMATE-IN-BUG-BOWLER":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG-BOWLER";
				break;
			case "ANIMATE-IN-MULTI_PARTNERSHIP":
				processAnimation(print_writer, "In","START", "EVEREST_MPL_T20");
//				AnimateInGraphics(print_writer, "MULTI-PARTNERSHIP");
				which_graphic_on_screen = "MULTI-PARTNERSHIP";
				break;
			case "ANIMATE-IN-PLAYERPROFILE": 
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "PLAYERPROFILE";
				break;
			case "ANIMATE-IN-PLAYERPROFILEBALL":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "PLAYERPROFILEBALL";
				break;
			case "ANIMATE-IN-LEADERBOARD":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "LEADERBOARD";
				break;
			case "ANIMATE-IN-MOST":
				processAnimation(print_writer, "In","START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "MOST";
				break;
			case "ANIMATE-IN-BUG-DB":
				processAnimation(print_writer, "In","START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "BUG-DB";
				break;
			case "ANIMATE-IN-MATCHID":
				processAnimation(print_writer, "In","START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "MATCHID";
				break;
			case "ANIMATE-IN-L3MATCH_PROMO":
				processAnimation(print_writer, "In","START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "L3MATCH_PROMO";
				break;
			case "ANIMATE-IN-MATCH_PROMO":
				processAnimation(print_writer, "In","START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "MATCH_PROMO";
				break;
			case "ANIMATE-IN-WORM":
				processAnimation(print_writer, "In","START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "WORM";
				break;
			case "ANIMATE-IN-LTMATCH_IDENT":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "LTMATCH_IDENT";
				break;
			case "ANIMATE-IN-NAMESUPER":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "NAMESUPER";
				break;
			case "ANIMATE-IN-NAMESUPER_SINGLE":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "NAMESUPER-SINGLE";
				break;
			case "ANIMATE-IN-NAMESUPER-PLAYER":
				processAnimation(print_writer, "In", "START", "EVEREST_MPL_T20");
				which_graphic_on_screen = "NAMESUPER-PLAYER";
				break;
			case "CLEAR-ALL":
				
				print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
				
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
				case "PLAYERPROFILEBALL":
					AnimateOutGraphics(print_writer, "PLAYERPROFILEBALL");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "SCHEDULE":
					AnimateOutGraphics(print_writer, "SCHEDULE");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
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
				case "LTMATCH_IDENT":
					AnimateOutGraphics(print_writer, "LTMATCH_IDENT");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					break;
				case "FF_PLAY":
					AnimateOutGraphics(print_writer, "FF_PLAY");
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
				case "FFTHISSERIES_BALL":
					AnimateOutGraphics(print_writer, "FFTHISSERIES_BALL");
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
				case "NAMESUPER-SINGLE":
					AnimateOutGraphics(print_writer, "NAMESUPER-SINGLE");
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
				case "L3MATCH_PROMO":
					AnimateOutGraphics(print_writer, "L3MATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "MATCH_PROMO":
					AnimateOutGraphics(print_writer, "MATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "BUG_HIGHLIGHT":
					AnimateOutGraphics(print_writer, "BUG_HIGHLIGHT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "FF-RULES":
					AnimateOutGraphics(print_writer, "FF-RULES");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "FF_SUMMARY_GRAPHICS":
					AnimateOutGraphics(print_writer, "FF_SUMMARY_GRAPHICS");
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
				case "BUG-RESULT":
					AnimateOutGraphics(print_writer, "BUG-RESULT");
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
				case "LT_PHASE_COMP":
					AnimateOutGraphics(print_writer, "LT_PHASE_COMP");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "POINTERS":
					AnimateOutGraphics(print_writer, "POINTERS");
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
		case "LT_POINTERS_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getPointers()).toString();
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
			return new ObjectMapper().writeValueAsString(tourn_stats).toString();
		
		case "POPULATE-BUG_POWERPLAY": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-BUG_PARTNERSHIP": case "POPULATE-MULTI_PARTNERSHIP":
		case "POPULATE-WORM": case "POPULATE-MOST_RUNS": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-BUG": case "POPULATE-L3-BUG-BOWLER":
		case "POPULATE-L3-BUG-DB": case "POPULATE-L3-NAMESUPER-SINGLE": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-FF-MATCHID":
		case "POPULATE-LTMATCH_IDENT": case "POPULATE-SCHEDULE": case "POPULATE-FF-LEADERBOARD": case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-FF-PLAYERPROFILEBALL":
		case "POPULATE-FF-THISSERIES": case "POPULATE-FF-THISSERIES_BALL": case "POPULATE-FF-RULES": case "POPULATE-L3-BUG-RESULT": case "POPULATE-L3-BUG-TOSS":
		case "POPULATE-FF_SUMMARY_GRAPHICS": case "POPULATE-FF_PLAY": case "POPULATE-L3-BUG-TARGET": case "POPULATE-L3-POINTERS": case "POPULATE-L3MATCH_PROMO":
		case "ANIMATE_IN_SPEED_SECOND_BROADCASTER": case "POPULATE-MATCH_PROMO": case "POPULATE-L3-PHASE_COMP":
			
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
			case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
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
			case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BowlerGrp$Section3$Section3All$BallSpeed$img_Text$txt_Data1*GEOM*TEXT SET " + 
						"BALL SPEED - " + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BowlerGrp$Section3$Section3All$BallSpeed$img_Text$txt_Data2*GEOM*TEXT SET " + 
						valueToProcess + " KPH" + "\0");
				
				processAnimation(print_writer, "Section3$BallSpeedIn", "START", "MPL");
				
				File file = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SPEED_DIRECTORY + CricketUtil.SPEED_TXT);
		        File parentDir = file.getParentFile();;
		        // Create parent directories if they do not exist
		        if (!parentDir.exists()) {
		            parentDir.mkdirs();
		        }
		        // Write content to file
		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
		            writer.write(valueToProcess);
		        }
				break;
			case "POPULATE-FF_SUMMARY_GRAPHICS":
				
				if(config.getCategory().equalsIgnoreCase("MEN")) {
					logocategory = "M";
				}else {
					logocategory = "W";
				}
				
				populateFFSummary(print_writer, valueToProcess.substring(valueToProcess.lastIndexOf(",")+1), 
						cricketService.getAllPlayer(),broadcaster);
				break;
			case "POPULATE-FF-THISSERIES": case "POPULATE-FF-THISSERIES_BALL":
				populateFFThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA",false, head_to_head, cricketService,match,past_tournament_stats)
						,match, broadcaster, config);
				break;
			case "POPULATE-SCHEDULE":
				populateSchedule(print_writer, valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-BUG_POWERPLAY":
				populateBugPowerPLay(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-FF_PLAY":
				populateFFPlay(print_writer,broadcaster);
				break;
			case "POPULATE-L3-BUG-RESULT":
				populateBugResult(print_writer, match, broadcaster);
				break;
			case "POPULATE-L3-BUG-TOSS":
				populateBugToss(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], match, broadcaster);
				break;
			case "POPULATE-L3-BUG-TARGET":
				populateBugTarget(print_writer, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-L3-PHASE_COMP":
				populatePhaseComp(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
				
			case "POPULATE-L3-POINTERS":
				for(Pointers PT : cricketService.getPointers()) {
					  if(PT.getPointersId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populatePointers(print_writer, valueToProcess.split(",")[0], PT, match, broadcaster);
					  }
					}
				break;
			case "POPULATE-L3MATCH_PROMO":
				for(Fixture fix : CricketFunctions.processAllFixtures(cricketService)) {
					if(fix.getMatchnumber() == Integer.valueOf(valueToProcess.split(",")[1])) {
						populateLtMatchPromo(print_writer, valueToProcess.split(",")[0], fix, match, broadcaster);
					}
				}
				break;
			case "POPULATE-MATCH_PROMO":
				for(Fixture fix : CricketFunctions.processAllFixtures(cricketService)) {
					if(fix.getMatchnumber() == Integer.valueOf(valueToProcess.split(",")[1])) {
						populateFFMatchPromo(print_writer, valueToProcess.split(",")[0], fix, match, broadcaster);
					}
				}
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
				populateWorm(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-MOST_RUNS":
				populateMostRunsTeam(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1],valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-L3-BUG-DISMISSAL":
				System.out.println("valueToProcess - " + valueToProcess);
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
			case "POPULATE-L3-NAMESUPER-SINGLE":
				
				if(config.getCategory().equalsIgnoreCase("MEN")) {
					logocategory = "M";
				}else {
					logocategory = "W";
				}
				
				for(NameSuper ns : cricketService.getNameSupers()) {
					  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateNameSuperSingle(print_writer, valueToProcess.split(",")[0], ns, match, broadcaster);
					  }
					}
				break;
			case "POPULATE-L3-NAMESUPER": 
				
				if(config.getCategory().equalsIgnoreCase("MEN")) {
					logocategory = "M";
				}else {
					logocategory = "W";
				}
				
				for(NameSuper ns : cricketService.getNameSupers()) {
				  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
					  populateNameSuper(print_writer, valueToProcess.split(",")[0], ns, match, broadcaster);
				  }
				}
				break;
			case "POPULATE-L3-NAMESUPER-PLAYER":
				
				if(config.getCategory().equalsIgnoreCase("MEN")) {
					logocategory = "M";
				}else {
					logocategory = "W";
				}
				
				populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
						match, broadcaster,cricketService);
				break;
			case "POPULATE-FF-LEADERBOARD":
				populateLeaderBoard(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster, config);
				break;
			case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-FF-PLAYERPROFILEBALL":
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = CricketFunctions.updateTournamentWithH2h(stats, head_to_head, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster, config);
						}
					}
				}
				break;	
			case "POPULATE-FF-MATCHID":
				
				if(config.getCategory().equalsIgnoreCase("MEN")) {
					logocategory = "M";
				}else {
					logocategory = "W";
				}
				
				populateMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-LTMATCH_IDENT":
				
				if(config.getCategory().equalsIgnoreCase("MEN")) {
					logocategory = "M";
				}else {
					logocategory = "W";
				}
				
				populateLtMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster,cricketService.getVariousTexts());
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
		case "LEADERBOARD":	case "FFTHISSERIES": case "FFTHISSERIES_BALL": case "NAMESUPER-SINGLE": case "FF-RULES": case "FF_SUMMARY_GRAPHICS": case "FF_PLAY":
		case "BUG-RESULT": case "BUG-TOSS": case "BUG-TARGET": case "POINTERS": case "L3MATCH_PROMO": case "MATCH_PROMO": case "LT_PHASE_COMP":
			processAnimation(print_writer, "Out", "START", broadcaster);
			TimeUnit.SECONDS.sleep(1);
			break;
		}	
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "MPL":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*"+ animationName + " " + animationCommand +" \0");
			break;
		case "EVEREST_MPL_T20":
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
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {

				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BATSMAN:
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
									+ inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
							
							for (BattingCard bc : inn.getBattingCard()) {
								if (bc.getPlayerId() == playerId) {
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
											(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT) ? bc.getRuns()+"*" : bc.getRuns()) + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + bc.getBalls() + ";");
									
									if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "run out " + 
													" (sub - " + bc.getHowOutFielder().getTicker_name() + ")" + ";");
										} else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "run out (" + 
													bc.getHowOutFielder().getTicker_name() + ")" + ";");
										}
									}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.STUMPED)) {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "st" +  
													" (sub - " + bc.getHowOutFielder().getTicker_name() + ")  b " + bc.getHowOutBowler().getTicker_name() + ";");
										} else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "st " + 
													bc.getHowOutFielder().getTicker_name() + "  b " + bc.getHowOutBowler().getTicker_name() + ";");
										}
									}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "c" +  
													" (sub - " + bc.getHowOutFielder().getTicker_name() + ")  b " + bc.getHowOutBowler().getTicker_name() + ";");
										} else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "c " + 
													bc.getHowOutFielder().getTicker_name() + "  b " + bc.getHowOutBowler().getTicker_name() + ";");
										}
									}else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + bc.getHowOutText() + ";");
									}
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
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {

				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BATSMAN:
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
									+ inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");						
							for (BattingCard bc : inn.getBattingCard()) {
								if (bc.getPlayerId() == playerId) {
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
									(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT) ? bc.getRuns()+"*" : bc.getRuns()) + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + bc.getBalls() + ";");
									
									if(bc.getSixes() != 0 && bc.getFours() != 0) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "FOURS: " + bc.getFours() 
											+ " SIXES: " + bc.getSixes() + ";");
									}else if(bc.getFours() != 0) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "FOURS: " + bc.getFours() + ";");
									}else if(bc.getSixes() != 0) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "SIXES: " + bc.getSixes() + ";");
									}else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "S/R : " + 
												CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 1) + ";");
									}
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
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BOWLER:
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
									+ inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
							for (BowlingCard boc : inn.getBowlingCard()) {
								if (boc.getPlayerId() == playerId) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + boc.getPlayer().getTicker_name() + " | " 
											+ boc.getWickets() + slashOrDash + boc.getRuns() + " (" + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ");");
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

	public void populateFFPlay(PrintWriter print_writer,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			
			print_writer.println(
					"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader MPL CONTEST ALERT;");
			
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
			break;
		}
	}
	
	public void populateBugPowerPLay(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + "POWERPLAY" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
							+ getPowerPlayScore(match,inn, whichInning, match.getEventFile().getEvents()) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + "" + ";");

					if (whichInning == 1) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamName1() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() + ";");
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
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
								+ inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						String Left_Batsman ="",Right_Batsman="";
						
						Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getTicker_name();
						Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getTicker_name();
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 CURRENT PARTNERSHIP;");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "* (" +
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
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == whichinning) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
								+ inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
						String Left_Batsman ="",Right_Batsman="";
						
						Left_Batsman = inn.getPartnerships().get(partnership - 1).getFirstPlayer().getTicker_name();
						Right_Batsman = inn.getPartnerships().get(partnership - 1).getSecondPlayer().getTicker_name();
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + Left_Batsman + " " + 
								inn.getPartnerships().get(partnership - 1).getFirstBatterRuns() + " (" + 
								inn.getPartnerships().get(partnership - 1).getFirstBatterBalls() + ")" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + Right_Batsman + " " + 
								inn.getPartnerships().get(partnership - 1).getSecondBatterRuns() + " (" + 
								inn.getPartnerships().get(partnership - 1).getSecondBatterBalls() + ")" + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + CricketFunctions.ordinal(inn.getPartnerships().
								get(partnership - 1).getPartnershipNumber()) + " WICKET PARTNERSHIP" + ";");
						
						if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getPartnershipNumber()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
									inn.getPartnerships().get(partnership - 1).getTotalRuns() + "* (" +  inn.getPartnerships().get(partnership - 1).getTotalBalls() + ")" + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
									inn.getPartnerships().get(partnership - 1).getTotalRuns() + " (" +  inn.getPartnerships().get(partnership - 1).getTotalBalls() + ")" + ";");
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
	public void populateWorm(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
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
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + "HIGHLIGHTS" + "       " + ";");

				if (whichInning == 1) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
							+ match.getMatch().getInning().get(0).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
							+ match.getMatch().getInning().get(0).getBatting_team().getTeamName1().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
							+ CricketFunctions.getTeamScore(match.getMatch().getInning().get(0), "-", false) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + 
							CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(),
							match.getMatch().getInning().get(0).getTotalBalls()) + ";");
					
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
							+ match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
							+ match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
							+ CricketFunctions.getTeamScore(match.getMatch().getInning().get(1), "-", false) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + 
							CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(),
							match.getMatch().getInning().get(1).getTotalBalls()) + ";");
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
		case "EVEREST_MPL_T20":
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
		case "EVEREST_MPL_T20":
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
							teamNameAsCity = inn.getBatting_team().getTeamName3();
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
	
	public void populateBugToss(PrintWriter print_writer, String string, Integer teamid, String text, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				
				String data = "",teamName = "";
				
				if(match.getSetup().getHomeTeamId()==teamid) {
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
	
	public void populatePointers(PrintWriter printWriter,String viz_scene, Pointers Pt ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + Pt.getHeader() + ";");
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "" + ";");
			
			if(Pt.getTeam() != null){
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + Pt.getTeam() + CricketUtil.PNG_EXTENSION + ";");
			}else {
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + "TLogo.png" + ";");
			}
			
			if(Pt.getRows() == 3) {
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + Pt.getText1() + ";");
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo02 " + Pt.getText2() + ";");
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo03 " + Pt.getText3() + ";");
			}else if(Pt.getRows() == 2) {
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + Pt.getText1() + ";");
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo02 " + Pt.getText2() + ";");
			}else if(Pt.getRows() == 1) {
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + Pt.getText1() + ";");
			}
			break;
		}
		
		printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 147.0;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	public void populatePhaseComp(PrintWriter printWriter,String viz_scene, int whichInning ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			String Team1="",Team2="";
			
			String phaseWiseScore =IndexController.matchstats.getHomeFirstPowerPlay().getTotalRuns()+","+IndexController.matchstats.getHomeFirstPowerPlay().getTotalWickets()+"_"+
					 IndexController.matchstats.getHomeSecondPowerPlay().getTotalRuns()+","+IndexController.matchstats.getHomeSecondPowerPlay().getTotalWickets()+"_"
					 +IndexController.matchstats.getHomeThirdPowerPlay().getTotalRuns()+","+IndexController.matchstats.getHomeThirdPowerPlay().getTotalWickets();	
			
			String phaseWiseScore2 = IndexController.matchstats.getAwayFirstPowerPlay().getTotalRuns()+","+IndexController.matchstats.getAwayFirstPowerPlay().getTotalWickets()+"_"+
					 IndexController.matchstats.getAwaySecondPowerPlay().getTotalRuns()+","+IndexController.matchstats.getAwaySecondPowerPlay().getTotalWickets()+"_"
					 +IndexController.matchstats.getAwayThirdPowerPlay().getTotalRuns()+","+IndexController.matchstats.getAwayThirdPowerPlay().getTotalWickets();
			
			String PP1 ="-",PP2="-",PP3="-";
			if(Integer.valueOf(phaseWiseScore.split("_")[0].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore.split("_")[0].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(), 
						match.getMatch().getInning().get(0).getTotalBalls())) > 0.0) {
					PP1 = "0-0";
				}
			}else {
				PP1 = phaseWiseScore.split("_")[0].split(",")[0]+"-"+phaseWiseScore.split("_")[0].split(",")[1];
			}
			if(Integer.valueOf(phaseWiseScore.split("_")[1].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore.split("_")[1].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(), 
						match.getMatch().getInning().get(0).getTotalBalls())) > 4.0) {
					PP2 = "0-0";
				}
			}else {
				PP2 = phaseWiseScore.split("_")[1].split(",")[0]+"-"+phaseWiseScore.split("_")[1].split(",")[1];
			}
			if(Integer.valueOf(phaseWiseScore.split("_")[2].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore.split("_")[2].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(), 
						match.getMatch().getInning().get(0).getTotalBalls())) > 10.0) {
					PP3 = "0-0";
				}
			}else {
				PP3 = phaseWiseScore.split("_")[2].split(",")[0]+"-"+phaseWiseScore.split("_")[2].split(",")[1];
			}
			//2nd inning
			String PP21 ="-",PP22="-",PP23="-";
			if(Integer.valueOf(phaseWiseScore2.split("_")[0].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore2.split("_")[0].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), 
						match.getMatch().getInning().get(1).getTotalBalls())) > 0.0) {
					PP21 = "0-0";
				}
			}else {
				PP21 = phaseWiseScore2.split("_")[0].split(",")[0]+"-"+phaseWiseScore2.split("_")[0].split(",")[1];
			}
			if(Integer.valueOf(phaseWiseScore2.split("_")[1].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore2.split("_")[1].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), 
						match.getMatch().getInning().get(1).getTotalBalls())) > 4.0) {
					PP22 = "0-0";
				}
			}else {
				PP22 = phaseWiseScore2.split("_")[1].split(",")[0]+"-"+phaseWiseScore2.split("_")[1].split(",")[1];
			}
			if(Integer.valueOf(phaseWiseScore2.split("_")[2].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore2.split("_")[2].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), 
						match.getMatch().getInning().get(1).getTotalBalls())) > 10.0) {
					PP23 = "0-0";
				}
			}else {
				PP23 = phaseWiseScore2.split("_")[2].split(",")[0]+"-"+phaseWiseScore2.split("_")[2].split(",")[1];
			}
			
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "PHASE WISE COMPARISON" + ";");
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "" + ";");
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + "TLogo.png" + ";");
			printWriter.println(String.format("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo %-28s %-25s %-25s %-25s;",
				    "","OVERS 1-6", "OVERS 7-15", "OVERS 16-20"));
			
			String team1 = match.getMatch().getInning().get(0).getBatting_team().getTeamName4();
			String team2 = match.getMatch().getInning().get(1).getBatting_team().getTeamName4();

			printWriter.println(String.format("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo02 %-" + 
					((team1.length() == 2) ? 27 : 25) + "s %-30s %-30s %-30s;",team1, PP1, PP2, PP3));
			printWriter.println(String.format("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo03 %-" + 
					((team2.length() == 2) ? 27 : 25) + "s %-30s %-30s %-30s;",team2, PP21, PP22, PP23));
			break;
		}
		
		printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 147.0;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateLtMatchPromo(PrintWriter print_writer,String viz_scene, Fixture Fix ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			
			String match_name="",bottom_data="";
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
					+ Fix.getHome_Team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 " + 
					Fix.getHome_Team().getTeamName2() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 " + 
					Fix.getHome_Team().getTeamName3() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName02 " + 
					Fix.getAway_Team().getTeamName2() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName02 " + 
					Fix.getAway_Team().getTeamName3() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path
					+ Fix.getAway_Team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			if(Fix.getMatchnumber() < 10) {
				match_name = "MATCH " + Fix.getMatchnumber();
			}else {
				match_name = Fix.getMatchfilename();
			}
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(Fix.getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				bottom_data = match_name + " - TOMORROW FROM " + Fix.getVenue();
			}else {
				cal.add(Calendar.DATE, -1);
				if(Fix.getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					if(Fix.getLocalTime() == null) {
						bottom_data = "UP NEXT - " + match_name;
					}else {
						bottom_data = "UP NEXT - " + match_name + " - " + Fix.getLocalTime();
					}
				}else {
					bottom_data = match_name + " - " + CricketFunctions.ordinal(Integer.valueOf(Fix.getDate().split("-")[0])) + " " +
							Month.of(Integer.parseInt(Fix.getDate().split("-")[1])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
							.toUpperCase() + " FROM " + Fix.getVenue();
				}
			}
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + bottom_data + ";");
			
			break;
		}
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 148.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
	}
	public void populateFFMatchPromo(PrintWriter print_writer,String viz_scene, Fixture Fix ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			
			String match_name="",bottom_data="";
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeLogo " + logo_path
					+ Fix.getHome_Team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
					Fix.getHome_Team().getTeamName1() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
					Fix.getAway_Team().getTeamName1() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayLogo " + logo_path
					+Fix.getAway_Team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			if(Fix.getMatchnumber() < 10) {
				match_name = "MATCH " + Fix.getMatchnumber();
			}else {
				match_name = Fix.getMatchfilename();
			}
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match_name + ";");
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(Fix.getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				bottom_data = "TOMORROW FROM " + Fix.getVenue();
			}else {
				cal.add(Calendar.DATE, -1);
				if(Fix.getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					if(Fix.getLocalTime() == null) {
						bottom_data = "UP NEXT" + match_name;
					}else {
						bottom_data = "UP NEXT" + " - " + Fix.getLocalTime();
					}
				}else {
					bottom_data = CricketFunctions.ordinal(Integer.valueOf(Fix.getDate().split("-")[0])) + " " +
							Month.of(Integer.parseInt(Fix.getDate().split("-")[1])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
							.toUpperCase() + " FROM " + Fix.getVenue();
				}
			}
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + bottom_data + ";");
			
			break;
		}
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 186.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
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
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + row_no + ";");

				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 PLAYERS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 MATCHES;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 RUNS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 S/R;");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamId() == Integer.valueOf(StatType)) {
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
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + row + ";");

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
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 "
//						+ "" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
						+ match.getSetup().getMatchIdent().toUpperCase() + ";");
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader "
//						+ match.getSetup().getTournament() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeLogo " + logo_path
						+ match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
						match.getSetup().getHomeTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayLogo " + logo_path
						+ match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "LIVE FROM "
						+ match.getSetup().getVenueName().toUpperCase() + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "\\TLogo" + CricketUtil.PNG_EXTENSION + ";");

				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 186.0;");
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
	public void populateSchedule(PrintWriter print_writer, String viz_scene, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
						+ "SHER-E-PUNJAB T20 CUP" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader "
						+ "SCHEDULE" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData01 "
						+ "LEAGUE STAGE - 13th JULY to 27th JULY" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData02 " + 
						"PLAYOFFS - 28th JULY" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData03 " + 
						"FINAL TO BE PLAYED ON 30th JULY" + ";");
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
	public void populateLtMatchId(PrintWriter print_writer, String viz_scene, MatchAllData match,
			String session_selected_broadcaster,List<VariousText> vartxt) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
						+ match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 " + 
						match.getSetup().getHomeTeam().getTeamName2() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 " + 
						match.getSetup().getHomeTeam().getTeamName3() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName02 " + 
						match.getSetup().getAwayTeam().getTeamName2() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName02 " + 
						match.getSetup().getAwayTeam().getTeamName3() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path
						+ match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 "
//						+ match.getSetup().getMatchIdent() + ";");
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
			  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 148.0;");
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
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				if(bug.getText2() != null && !bug.getText2().isEmpty()) {
					if(bug.getSponsor() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path + bug.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
					}
					
					if (bug.getText1() != null && bug.getText2() != null && bug.getText3() != null && bug.getText4() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bug.getText1().toUpperCase() + "       " + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + bug.getText2().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + bug.getText3().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + bug.getText4().toUpperCase() + ";");
					} else if (bug.getText1() != null && bug.getText2() != null && bug.getText3() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "+ bug.getText1().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "+ bug.getText3().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "+ bug.getText2().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "+ "" + ";");
					} else if(bug.getText1() != null && bug.getText2() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bug.getText1().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + bug.getText2().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + "" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + "" + ";");
					}else if(bug.getText1() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bug.getText1().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + "" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + "" + ";");
					}
				}else {
					if(bug.getSponsor() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + bug.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
					}
					
					if(bug.getText1() != null) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + bug.getText1().toUpperCase() + ";");
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
	
	public void populateNameSuperSingle(PrintWriter print_writer, String viz_scene, NameSuper ns, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				if(ns.getSponsor() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
							+ ns.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lglgHomeTeamLogoLogo " + logo_path
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
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
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
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 "
						+ "" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
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

	public void populateNameSuperPlayer(PrintWriter print_writer, String viz_scene,String captainWicketKeeper, int playerId, 
			MatchAllData match, String session_selected_broadcaster,CricketService cricketService)
			throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				String Home_or_Away = "";
				
				Player player = cricketService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == playerId).findAny().orElse(null);
				Team team = cricketService.getTeams().stream().filter(tm -> tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
				
				if (team.getTeamId() == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
							+ match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					Home_or_Away = match.getSetup().getHomeTeam().getTeamName1();
					for (Player hs : match.getSetup().getHomeSquad()) {
						if (playerId == hs.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 " + hs.getFirstname() + ";");
							if (hs.getSurname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 "+ "" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 "+ hs.getSurname() + ";");
							}
							
							if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
										+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
							}
						}

					}
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
							+ match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
					for (Player as : match.getSetup().getAwaySquad()) {
						if (playerId == as.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 " + as.getFirstname() + ";");
							if (as.getSurname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 " + "" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 " + as.getSurname() + ";");
							}
							if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
										+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
							}
						}
					}
				}

				switch (captainWicketKeeper.toUpperCase()) {
				case CricketUtil.CAPTAIN:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ captainWicketKeeper.toUpperCase() + ", " + Home_or_Away + ";");
					break;
				case "PLAYER OF THE MATCH":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ captainWicketKeeper.toUpperCase() + ";");
					break;
				case CricketUtil.WICKET_KEEPER:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ "WICKET KEEPER" + ", " + Home_or_Away + ";");
					break;
				case "CAPTAIN-WICKETKEEPER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ "CAPTAIN & WICKET KEEPER" + ", " + Home_or_Away + ";");
					break;
				case "PLAYER OF THE TOURNAMENT":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ "PLAYER OF THE TOURNAMENT" + ";");
					break;
				case "PLAYER OF THE SERIES":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ "PLAYER OF THE SERIES" + ";");
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
	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_no=0;
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Logo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
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
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+ CricketUtil.DOUBLE_BACKSLASH + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
							
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + " " + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_RUNS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST RUNS" + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "RUNS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + row_no + ";");
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm" + row_no + " 0" + ";");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+ CricketUtil.DOUBLE_BACKSLASH + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
						
//						if(tournament.get(i).getPlayer().getSurname() != null) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
//						}else {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + "" + ";");
//						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_WICKETS":
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST WICKETS" + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "WICKETS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + row_no + ";");
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm" + row_no + " 0" + ";");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+ CricketUtil.DOUBLE_BACKSLASH + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
//						if(tournament.get(i).getPlayer().getSurname() != null) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
//						}else {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + "" + ";");
//						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getWickets() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_FOURS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 " + "MOST FOURS" + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "FOURS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + row_no + ";");
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect0" + row_no + " 0" + ";");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+ CricketUtil.DOUBLE_BACKSLASH + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
//						if(tournament.get(i).getPlayer().getSurname() != null) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
//						}else {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + "" + ";");
//						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_SIXES":
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 " + "MOST SIXES" + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "SIXES " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm" + row_no + " 1" + ";");
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect0" + row_no + " 0" + ";");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
										+ CricketUtil.DOUBLE_BACKSLASH + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
//						if(tournament.get(i).getPlayer().getSurname() != null) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
//						}else {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + "" + ";");
//						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getSixes() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
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
	
	public void populateFFSummary(PrintWriter print_writer, String ValueToProcess,  List<Player> allPlayer ,String broadcaster) throws InterruptedException {
        Map<String, Object> rowData = CricketFunctions.ReadExcel("C:\\Sports\\Cricket\\Summary.xlsx").get(ValueToProcess);
        
		//Tournament Logo
        
        print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo01 " + logo_path + 
        		(rowData.get("LOGOS") != null ? rowData.get("LOGOS").toString().split(",")[0].trim() : "") + CricketUtil.PNG_EXTENSION + ";");
        print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo02 " + logo_path + 
        		(rowData.get("LOGOS") != null ? rowData.get("LOGOS").toString().split(",")[1].trim() : "") + CricketUtil.PNG_EXTENSION + ";");
        
		//Hide The Tag of Batsman and Bowler
		for(int i=1;i<=9;i++) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect0" + i + " 0;");
		}
		
		//Show The Tag of Batsman of Inning1 and Data 
		for(int j=1;j<=Integer.valueOf((rowData.get("FIRST INN BAT/BALL") != null ? rowData.get("FIRST INN BAT/BALL").toString().split(",")[0].trim() : ""));j++) {
			
//			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSelect0" + i + " SET " + 0 + "\0");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect0" + j + " 1;");
			
			if(j==1) {
				if((rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsman0" + j + " " + 
						(rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns0" + j + " " + 
						(rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 1;");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 0;");
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + j + " " + 
						(rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==2) {
				if((rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 1;");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 0;");
				}
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsman0" + j + " " + 
						(rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns0" + j + " " + 
						(rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 1;");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 0;");
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + j + " " + 
						(rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==3) {
				if((rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 1;");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 0;");
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsman0" + j + " " + 
						(rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns0" + j + " " + 
						(rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + ";");
				
				
				if((rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 1;");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 0;");
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + j + " " + 
						(rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==4) {
				if((rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 1;");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 0;");
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsman0" + j + " " + 
						(rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns0" + j + " " + 
						(rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 1;");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 0;");
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + j + " " + 
						(rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
			}
		}
		
		//Show The Tag of Bowler of Inning1 and Data
		for(int j=1;j<=Integer.valueOf((rowData.get("FIRST INN BAT/BALL") != null ? rowData.get("FIRST INN BAT/BALL").toString().split(",")[1].trim() : ""));j++) {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect0" + j + " 2;");
			
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Fullframes"
//					+ "$MatchSummary$SummaryDataOut$SummaryData$Innings1$Row" + j
//					+ "$Out$In$SummaryDataAll$BowlerGrp*ACTIVE SET 1 \0");
			
			if(j==1) {
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlFig0" + j + " " + 
						(rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOver0" + j + " " + 
						(rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==2) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlFig0" + j + " " + 
						(rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOver0" + j + " " + 
						(rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==3) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlFig0" + j + " " + 
						(rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOver0" + j + " " + 
						(rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==4) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlFig0" + j + " " + 
						(rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOver0" + j + " " + 
						(rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + ";");
				
			}
		}
		
		//Show The Tag of Batsman of Inning2 and Data
		for(int j=1;j<=Integer.valueOf((rowData.get("SECOND INN BAT/BALL") != null ? rowData.get("SECOND INN BAT/BALL").toString().split(",")[0].trim() : ""));j++) {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect0" + j + " 1;");
			
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Fullframes"
//					+ "$MatchSummary$SummaryDataOut$SummaryData$Innings2$Row" + j
//					+ "$Out$In$SummaryDataAll$BatterGrp*ACTIVE SET 1 \0");
			
			if(j==1) {
				if((rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsman0" + j + " " + 
						(rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns0" + j + " " + 
						(rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 1;");
					
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + j + " " + 
						(rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==2) {
				if((rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {

					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsman0" + j + " " + 
						(rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns0" + j + " " + 
						(rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 1;");
					
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + j + " " + 
						(rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==3) {
				if((rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsman0" + j + " " + 
						(rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns0" + j + " " + 
						(rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 1;");
					
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + j + " " + 
						(rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==4) {
				if((rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBatImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsman0" + j + " " + 
						(rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns0" + j + " " + 
						(rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 1;");
					
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoutOut0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + j + " " + 
						(rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}
		}
		
		//Show The Tag of Bowler of Inning2 and Data
		for(int j=1;j<=Integer.valueOf((rowData.get("SECOND INN BAT/BALL") != null ? rowData.get("SECOND INN BAT/BALL").toString().split(",")[1].trim() : ""));j++) {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect0" + j + " 2;");
			
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Fullframes"
//					+ "$MatchSummary$SummaryDataOut$SummaryData$Innings2$Row" + j
//					+ "$Out$In$SummaryDataAll$BowlerGrp*ACTIVE SET 1 \0");
			
			if(j==1) {
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==2) {
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==3) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}else if(j==4) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + ";");
				
				if((rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 1;");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VBowlImpact0" + j + " 0;");
					
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler0" + j + " " + 
						(rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + ";");
				
			}
		}
		
		//Header-SubHeader-Footer
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + 
				(rowData.get("TOURNAMENT") != null ? rowData.get("TOURNAMENT").toString().trim() : "") + "-" + 
				(rowData.get("MATCHNAME") != null ? rowData.get("MATCHNAME").toString().trim() : "")+ ";");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader SUMMARY;");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + 
				(rowData.get("FOOTER") != null ? rowData.get("FOOTER").toString().trim() : "") + ";");
		
		//TOSS
		if(Integer.valueOf((rowData.get("TOSS") != null ? rowData.get("TOSS").toString().trim() : "")) == 1) {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VToss01 1;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VToss02 0;");
			
		}else if(Integer.valueOf((rowData.get("TOSS") != null ? rowData.get("TOSS").toString().trim() : "")) == 2){
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VToss01 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VToss02 1;");
			
		}
		
		//BOTH TEAMS NAME/RUNS/OVERS
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName01 " + 
				(rowData.get("FIRST INN NAME/RUNS/OVERS") != null ? 
						rowData.get("FIRST INN NAME/RUNS/OVERS").toString().split(",")[0].trim() : "") + ";");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName02 " + 
				(rowData.get("SECOND INN NAME/RUNS/OVERS") != null ? 
						rowData.get("SECOND INN NAME/RUNS/OVERS").toString().split(",")[0].trim() : "") + ";");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore01 " + 
				(rowData.get("FIRST INN NAME/RUNS/OVERS") != null ? 
						rowData.get("FIRST INN NAME/RUNS/OVERS").toString().split(",")[1].trim() : "") + ";");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers01 " + 
				(rowData.get("FIRST INN NAME/RUNS/OVERS") != null ? 
						rowData.get("FIRST INN NAME/RUNS/OVERS").toString().split(",")[2].trim() : "") + ";");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore02 " + 
				(rowData.get("SECOND INN NAME/RUNS/OVERS") != null ? 
						rowData.get("SECOND INN NAME/RUNS/OVERS").toString().split(",")[1].trim() : "") + ";");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers02 " + 
				(rowData.get("SECOND INN NAME/RUNS/OVERS") != null ? 
						rowData.get("SECOND INN NAME/RUNS/OVERS").toString().split(",")[2].trim() : "") + ";");
		
//        print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 \0");
	}
	
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
			
		case "EVEREST_MPL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				this.status = CricketUtil.SUCCESSFUL;
			//System.out.println("Fours = " + stats.getTournament_fours() + " Sixes = " + stats.getTournament_sixes());
			double strike_rate = 0;
			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + "" + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + match.getSetup().getHomeTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getHomeTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + match.getSetup().getHomeTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFistName " + plyr.getFirstname() + ";");
				if(plyr.getSurname() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
				}
			}
			else {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + match.getSetup().getAwayTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getAwayTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getAwayTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}
				
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
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 "+ CricketFunctions.getEconomy(stats.getRuns_conceded(), stats.getBalls_bowled(), 1, "-") +";");
				
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

			

			}
			break;
		}
		
	}
	public void populateFFThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			double strike_rate = 0 , economy_rate=0;
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + "" + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerHand "+ "THIS SERIES" + ";");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + match.getSetup().getHomeTeam().getTeamName4()
									+ CricketUtil.DOUBLE_BACKSLASH + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +  match.getSetup().getHomeTeam().getTeamName4()
									+ CricketUtil.DOUBLE_BACKSLASH + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getHomeTeam().getTeamName4()
									+ CricketUtil.DOUBLE_BACKSLASH + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
					}else {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + match.getSetup().getAwayTeam().getTeamName4()
									+ CricketUtil.DOUBLE_BACKSLASH + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +   match.getSetup().getAwayTeam().getTeamName4()
									+ CricketUtil.DOUBLE_BACKSLASH + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getAwayTeam().getTeamName4()
									+ CricketUtil.DOUBLE_BACKSLASH + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}
						
						
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

	