package com.cricket.broadcaster;
 
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.cricket.containers.BattingCardFF;
import com.cricket.containers.BowlingFF;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Configuration;
import com.cricket.model.Event;
import com.cricket.model.EverestBugs;
import com.cricket.model.Fixture;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EVEREST_LEGENDS_90 extends Scene{

	public String broadcaster = "EVEREST_LEGENDS_90";
	public String status;
	public String slashOrDash = "-";
	public String photo_path = "C:\\\\Images\\\\LEGENDS\\\\Photos\\\\";
	public String Sponsor_path = "C:\\\\Images\\\\LEGENDS\\\\Sponsor\\\\";
	public String logo_path = "D:\\DOAD_In_House_Everest\\Everest_Cricket\\Everest_Barodaleague_2025\\Logos\\";
	public String base_path = "D:\\DOAD_In_House_Everest\\Everest_Cricket\\Everest_Barodaleague_2025\\Textures\\Base\\";
	public String Logo_BW_path = "D:\\DOAD_In_House_Everest\\Everest_Cricket\\Everest_Barodaleague_2025\\Logo_BW\\";
	public String Logo_Grey_path = "D:\\DOAD_In_House_Everest\\Everest_Cricket\\Everest_Barodaleague_2025\\Logo_Grey\\";
	public String icons_path = "C:\\\\Images\\\\LEGENDS\\\\Icons\\\\";
	public Infobar infobar = new Infobar(); 
	public String which_graphic_on_screen = "";
	public BattingCardFF bcf = new BattingCardFF();
	public BowlingFF bocf = new BowlingFF();
	public static List<Map<String, String>> KeyPlayer = new ArrayList<Map<String,String>>();
	public static List<BestStats> top_ten_beststat = new ArrayList<BestStats>();
	public EVEREST_LEGENDS_90() {
		super();
	}

	public EVEREST_LEGENDS_90(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics, Configuration config, 
			List<HeadToHeadPlayer> headToHead, List<Tournament> past_tournament_stats) throws NumberFormatException, Exception
	{
	System.out.println(whatToProcess);
	//CricketFunctions.readExcelToMap().toString()
		switch (whatToProcess) {
		 case "ANIMATE-IN-BugTARGET": case"ANIMATE-IN-BUG-RESULT":case "ANIMATE-IN-LTMATCH_IDENT":case "ANIMATE-IN-L3MATCH_PROMO":
		 case "ANIMATE-IN-LEADERBOARD":case "ANIMATE-IN-HIGHEST_SCORE":case "ANIMATE-IN-BEST_FIGURES":case "ANIMATE-IN-POWERPLAY_LT_RULES":
			 if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
				AnimateInGraphics(print_writer, "FF_IN");
				TimeUnit.MILLISECONDS.sleep(200);
			}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
				AnimateOutGraphics(print_writer, "IDENT");
				AnimateInGraphics(print_writer, "FF_IN");
			}
			 processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
			 which_graphic_on_screen = "BugTARGET";
			 break;
		case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP":
		case "ANIMATE-IN-INFOBAR": case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
		case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": 
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY":
		case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-IDENT":
		case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BUG-DB":  case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BUG-BOWLER":
		case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY":
		case "ANIMATE-IN-LANDMARK":	case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH":
		case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-OUT-DIRECTOR":
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-GENERIC":
		case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM": case "ANIMATE-OUT-SECTION2":
		case "ANIMATE-OUT-SECTION4_N_5": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-SCHEDULE": case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-THISSERIES":
		case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-LINEUP":
		case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-LTMANHATTAN": case "ANIMATE-OUT-POWERPLAY": case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-PLAYOFFS": case "ANIMATE-IN-BATGRIFF":
		case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-BALL_PERFORMER": case "TICKER_LT_OUT": case "TICKER_LT_IN": case "ANIMATE-IN-MOST": case "ANIMATE-IN-INN_BUILDER":
	    case "ANIMATE-IN-NAMESUPER_SINGLE":case "ANIMATE-FF_KEYPLAYER_GRAPHICS":case "ANIMATE-IN-BUG-TOSS":case "ANIMATE-IN-LT_RESULT": case "ANIMATE-IN-SUPER_OVER":case "ANIMATE-IN-TOURNAMENT_RULES":
		case "ANIMATE-IN-TIMEOUT_LT_RULES":
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP":
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PLAYERPROFILE": 
			case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI":  case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-POSITION_LANDMARK": 
			case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE":
			case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM":
			case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER":
			case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-BUGPARTNERSHIP":
			case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-PLAYOFFS": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-BALL_PERFORMER": case "ANIMATE-IN-MOST":
			case "ANIMATE-FF_KEYPLAYER_GRAPHICS":	case "ANIMATE-IN-BUG-TOSS":case "ANIMATE-IN-LT_RESULT": case "ANIMATE-IN-SUPER_OVER":case "ANIMATE-IN-TOURNAMENT_RULES":case "ANIMATE-IN-TIMEOUT_LT_RULES":
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
			case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-LINEUP": case "ANIMATE-IN-LTMANHATTAN": case "ANIMATE-IN-INN_BUILDER": case "ANIMATE-IN-NAMESUPER_SINGLE":
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
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "BUG";
				break;
			 case "ANIMATE-IN-BUG-DISMISSAL":
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "BUG-DISMISSAL";
				break;
			 case "ANIMATE-IN-SUPER_OVER":
				 processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "SUPER_OVER";
				break;
			 case "ANIMATE-IN-TOURNAMENT_RULES":case "ANIMATE-IN-TIMEOUT_LT_RULES":
				 processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "TOURNAMENT_RULES";
				break;
			case "ANIMATE-FF_KEYPLAYER_GRAPHICS":
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "FF_KEYPLAYER";
				break;
			case "ANIMATE-IN-TEAMS_LOGO":
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "TEAMS_LOGO";
				break;
			case "ANIMATE-IN-BUG-TOSS": case "ANIMATE-IN-LT_RESULT":
				 processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
					which_graphic_on_screen = "BUG-TOSS";
				 break;
			 case "ANIMATE-IN-TARGET":
				 processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
					which_graphic_on_screen = "TARGET";
				 break;
			case "ANIMATE-IN-BUG_POWERPLAY":
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "BUG_POWERPLAY";
				break;
			case "ANIMATE-IN-BUG_PARTNERSHIP":
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "BUG_PARTNERSHIP";
				break;
			case "ANIMATE-IN-BUG_HIGHLIGHT":
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "BUG_HIGHLIGHT";
				break;
			case "ANIMATE-IN-BUG-BOWLER":
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "BUG-BOWLER";
				break;
			case "ANIMATE-IN-MULTI_PARTNERSHIP":
				processAnimation(print_writer, "In","START", "EVEREST_LEGENDS_90");
//				AnimateInGraphics(print_writer, "MULTI-PARTNERSHIP");
				which_graphic_on_screen = "MULTI-PARTNERSHIP";
				break;
			case "ANIMATE-IN-MOST":
				processAnimation(print_writer, "In","START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "MOST";
				break;
			case "ANIMATE-IN-BUG-DB":
				processAnimation(print_writer, "In","START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "BUG-DB";
				break;
			case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-MATCH_PROMO":
				processAnimation(print_writer, "In","START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "MATCHID";
				break;
			case "ANIMATE-IN-WORM":
				processAnimation(print_writer, "In","START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "WORM";
				break;
			case "ANIMATE-IN-NAMESUPER":
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "NAMESUPER";
				break;
			 case "ANIMATE-IN-NAMESUPER_SINGLE":
				 processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				 which_graphic_on_screen = "NAMESUPER_SINGLE";
				 break;
			case "ANIMATE-IN-NAMESUPER-PLAYER":
				processAnimation(print_writer, "In", "START", "EVEREST_LEGENDS_90");
				which_graphic_on_screen = "NAMESUPER-PLAYER";
				break;
			case "CLEAR-ALL":
				   print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
	               print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
	               print_writer.println("LAYER3*EVEREST*SINGLE_SCENE CLEAR;");
	               
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
				case "FF_KEYPLAYER":
					AnimateOutGraphics(print_writer, "FF_KEYPLAYER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					break;
				case "TEAMS_LOGO":case"BUG-TOSS":case"TARGET":case "BugTARGET":
				case "SUPER_OVER": case "TOURNAMENT_RULES":
					AnimateOutGraphics(print_writer, "TEAMS_LOGO");
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
				case "NAMESUPER_SINGLE":
					AnimateOutGraphics(print_writer, "NAMESUPER_SINGLE");
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
		case "NAMESUPER_GRAPHICS-OPTIONS": case "NAMESUPER_GRAPHICS_SINGLELINE-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "L3_MATCH-PROMO_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
		case "BUG_DB_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getEverestBugs()).toString();
		case "PROMPT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getInfobarStats()).toString();
		case "EXCEL_FF_KEY_PLAYER_GRAPHICS_OPTION":
			 KeyPlayer = CricketFunctions.readExcelToMap();
			 return new ObjectMapper().writeValueAsString(KeyPlayer).toString();
			 
		case "HEIGHEST_INDIVIDUAL_SCORE_GRAPHICS-OPTIONS":case "BEST_FIGURES_GRAPHICS-OPTIONS":
			 List<Tournament> tournaments = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead,
						cricketService, match, past_tournament_stats);
			 top_ten_beststat = new ArrayList<BestStats>();
	        for(Tournament tourn : tournaments) {
				switch (whatToProcess) {
				case "HEIGHEST_INDIVIDUAL_SCORE_GRAPHICS-OPTIONS":
		            for(BestStats bs : tourn.getBatsman_best_Stats()) {
		            	top_ten_beststat.add(CricketFunctions.getProcessedBatsmanBestStats(bs));
		            }
					Collections.sort(top_ten_beststat,new CricketFunctions.BatsmanBestStatsComparator());
					break;
				case "BEST_FIGURES_GRAPHICS-OPTIONS":
					
		            for(BestStats bs : tourn.getBowler_best_Stats()) {
		            	top_ten_beststat.add(CricketFunctions.getProcessedBowlerBestStats(bs));
		            }
					Collections.sort(top_ten_beststat,new CricketFunctions.BowlerBestStatsComparator());
					break;
				}
	        }       
		 return  new ObjectMapper().writeValueAsString(top_ten_beststat).toString();
		case "LEADERBOARD_GRAPHICS-OPTIONS": case "WICKETS_GRAPHICS-OPTIONS": case "FOURS_GRAPHICS-OPTIONS": case "SIXES_GRAPHICS-OPTIONS":
			List<Tournament> tourn_stats = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead,
					cricketService, match, past_tournament_stats);
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
		case "POPULATE-L3-NAMESUPER-SINGLE":case "POPULATE-FF_KEYPLAYER_GRAPHICS":	case "POPULATE-FF-TEAMS_LOGO":case "POPULATE-L3-BUG-TOSS":
		case "POPULATE-L3-TARGET":case "POPULATE-L3-Result":case "POPULATE-MATCH_PROMO":case "POPULATE-SUPER_OVER": case "POPULATE-TOURNAMENT_RULES":
		case "POPULATE-BUG-TARGET":case "POPULATE-L3-BUG-RESULT":case "POPULATE-LTMATCH_IDENT":case "POPULATE-TIMEOUT_LT_RULES":case "POPULATE-L3MATCH_PROMO":
		case "POPULATE-FF-LEADERBOARD":case "POPULATE-HIGHEST_SCORE":case "POPULATE-BEST_FIGURES":case "POPULATE-POWERPLAY_LT_RULES":
			if(which_graphic_on_screen == "SCOREBUG" || which_graphic_on_screen == "IDENT") {
			}else if(which_graphic_on_screen != "") {
				AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
				  print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");	
			}
			switch(whatToProcess.toUpperCase()) {
			case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-DIRECTOR": 
			case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-RIGHT": case "POPULATE-BT-POWERPLAY":
				break;
			case "POPULATE-L3-BUG-RESULT":
				scenes.get(0).setScene_path("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_Barodaleague_2025/Scenes/Result_Bug.sum");
				scenes.get(0).scene_load(print_writer,broadcaster);
				break;
			case "POPULATE-L3-BUG-DB":
				 scenes.get(0).setScene_path("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_Barodaleague_2025/Scenes/Bug_SingleLine.sum");
		         scenes.get(0).scene_load(print_writer,broadcaster);
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
			case "POPULATE-L3-BUG-DISMISSAL":
				populateBugDismissal(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-FF_KEYPLAYER_GRAPHICS":	
				populateKeyPlayerDismissal(print_writer, valueToProcess, match, broadcaster,cricketService);
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
				for(EverestBugs bug : cricketService.getEverestBugs()) {
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
			case "POPULATE-L3-BUG-TOSS":
				populateToss(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], match, broadcaster);
				break;
			case "POPULATE-L3-TARGET":
				populateTarget(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-RESULT":case "POPULATE-L3-BUG-RESULT":
				if (whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-L3-RESULT")) {
					populateResult(print_writer,valueToProcess.split(",")[0], match, broadcaster,"");

				}else {
					populateResult(print_writer,valueToProcess.split(",")[0], match, broadcaster,"BUG-RESULT");
				}
				break;
			case "POPULATE-L3-NAMESUPER-SINGLE":
				for(NameSuper ns : cricketService.getNameSupers()) {
					  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateNameSuperSingle(print_writer, ns, match, broadcaster);
					  }
					}
				break;
			case "POPULATE-BUG-TARGET":
				populateBugTarget(print_writer,valueToProcess.split(",")[0], match, broadcaster);
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
			case "POPULATE-MATCH_PROMO":
				populateMatchPromo(false,print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
						cricketService.getFixtures(),match , broadcaster);				
			break;
			case "POPULATE-L3MATCH_PROMO":
				populateLtMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
						cricketService.getFixtures(),match , broadcaster);				
			break;
			case "POPULATE-LTMATCH_IDENT":
				populateLtMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster,cricketService.getVariousTexts());
				break;
			case "POPULATE-TIMEOUT_LT_RULES":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				preview(print_writer);
				break;
			case "POPULATE-SUPER_OVER": case "POPULATE-TOURNAMENT_RULES":case "POPULATE-FF-TEAMS_LOGO":case "POPULATE-POWERPLAY_LT_RULES":
				preview(print_writer);
				break;
			case "POPULATE-FF-LEADERBOARD":
				populateLeaderBoard(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
						CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead,
								cricketService, match, past_tournament_stats),
						cricketService.getTeams(),match, broadcaster,config );
				break;
			case "POPULATE-HIGHEST_SCORE":case "POPULATE-BEST_FIGURES":
				populateLeaderBoardScore(print_writer, valueToProcess.split(",")[0], whatToProcess, Integer.valueOf(valueToProcess.split(",")[1]),
						top_ten_beststat,cricketService.getTeams(),match, broadcaster,config );
				break;
			}
		}
			return null;
	}
	
	public void populateLeaderBoardScore(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<BestStats> top_ten_beststat,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_no=0;
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$Logo*CONTAINER SET ACTIVE 0;");

			switch(StatType.toUpperCase()) {
			case "POPULATE-HIGHEST_SCORE":
				Collections.sort(top_ten_beststat,new CricketFunctions.BatsmanBestStatsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "HIGHEST INDIVIDUAL SCORE" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= top_ten_beststat.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(top_ten_beststat.get(i).getPlayerId() == playerid) {
						  print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
							team.get(top_ten_beststat.get(i).getPlayer().getTeamId() -1 ).getTeamBadge() +"\\\\"+ 
							top_ten_beststat.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						  print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " v " + team.get(top_ten_beststat.get(i).getOpponentTeam().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + top_ten_beststat.get(i).getBestEquation() / 2 +(top_ten_beststat.get(i).getStatus().equalsIgnoreCase("NOT OUT")?"*":"")+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
					}
				}
				break;
			case "POPULATE-BEST_FIGURES":
				Collections.sort(top_ten_beststat,new CricketFunctions.BowlerBestStatsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "BEST FIGURES " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= top_ten_beststat.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(top_ten_beststat.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(top_ten_beststat.get(i).getPlayer().getTeamId() -1 ).getTeamBadge() +"\\\\"+ 
									top_ten_beststat.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " v " + team.get(top_ten_beststat.get(i).getOpponentTeam().getTeamId() - 1).getTeamName1() + ";");
						
						if(top_ten_beststat.get(i).getBestEquation() % 1000 > 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " 
									+ ((top_ten_beststat.get(i).getBestEquation() / 1000) +1) + "-" + (1000 - (top_ten_beststat.get(i).getBestEquation() % 1000)) + ";");

						}
						else if(top_ten_beststat.get(i).getBestEquation() % 1000 < 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " 
									+ (top_ten_beststat.get(i).getBestEquation() / 1000) + "-" + Math.abs(top_ten_beststat.get(i).getBestEquation()) + ";");
						}
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
	

	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_no=0;
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$Logo*CONTAINER SET ACTIVE 0;");

			switch(StatType.toUpperCase()) {
			case "MOST_RUNS_DATA":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST RUNS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamBadge() +"\\\\"+ 
									tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_RUNS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST RUNS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
						  print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
							team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamBadge() +"\\\\"+ 
							tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						  print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_WICKETS":
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST WICKETS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamBadge() +"\\\\"+ 
									tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getWickets() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_FOURS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST FOURS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamBadge() +"\\\\"+ 
									tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + tournament.get(i).getFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_SIXES":
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST SIXES " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamBadge() +"\\\\"+ 
									tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
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
	
	private void populateLtMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,
			List<Team> team,List<Fixture> fix,MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				String match_name="";
				for(Team TM : team) {
					if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
								+ (TM.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : TM.getTeamBadge().toUpperCase())+ CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 "+ TM.getTeamName2()+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 " +TM.getTeamName3() + ";");
						
					}
					if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path
								+ (TM.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : TM.getTeamBadge().toUpperCase())+ CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName02 "+ TM.getTeamName2()+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName02 " +TM.getTeamName3() + ";");
					}
				}
				
				if(match_number < 10) {
					match_name = "MATCH " + match_number;
				}else {
					match_name = fix.get(match_number - 1).getMatchfilename().toUpperCase();
				}
				
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, +1);
				if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "TOMORROW - " + match_name +
							" - AT " +fix.get(match_number-1).getLocalTime()+"- FROM " + match.getSetup().getVenueName().split(",")[1]+ ";");
				}else {
					cal.add(Calendar.DATE, -1);
					if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "UP NEXT - " + match_name + 
								" - AT " +fix.get(match_number-1).getLocalTime()+"- FROM " +match.getSetup().getVenueName().split(",")[1]+ ";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
					LocalDate.parse(fix.get(match_number - 1).getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")).
					format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase()+" - " 
								+ match_name + " - AT " +fix.get(match_number-1).getLocalTime()+" - FROM " + match.getSetup().getVenueName().split(",")[1] + ";");				
					}
				 }
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 150;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				  TimeUnit.SECONDS.sleep(1);
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				 

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}

	public void populateMatchPromo(boolean is_this_updating, PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchPromo's inning is null";
		} else {
			
			String match_name="";
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeLogo " + logo_path
							+ (TM.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : TM.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeBase " + base_path
							+ (TM.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : TM.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeLogoBW " + Logo_BW_path
							+(TM.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : TM.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
					
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayLogo " + logo_path
							+ (TM.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : TM.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayBase " + base_path
							+ (TM.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : TM.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayLogoBW " + Logo_BW_path
							+ (TM.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : TM.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				}
			}
			
			if(match_number < 10) {
				match_name = "MATCH " + match_number;
			}else {
				match_name = fix.get(match_number - 1).getMatchfilename().toUpperCase();
			}
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "TOMORROW - " + fix.get(match_number-1).getLocalTime() +" IST "+ ";");	
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader FROM " + 
						fix.get(match_number-1).getVenue()+ ";");

			}else {
				cal.add(Calendar.DATE, -1);
				if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "UP NEXT - " + fix.get(match_number-1).getLocalTime() +" IST "+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader FROM " + fix.get(match_number-1).getVenue()+ ";");

				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " +
				LocalDate.parse(fix.get(match_number - 1).getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")).
				format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase()+" - " 
							+ fix.get(match_number-1).getLocalTime()+" IST " + ";");	
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader FROM " +
							fix.get(match_number-1).getVenue() + ";");

				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 200;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;
			}	
		}
	}

	private void populateLtMatchId(PrintWriter print_writer, String string, MatchAllData match,
			String session_selected_broadcaster,List<VariousText> vartxt) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
						+ match.getSetup().getMatchIdent().toUpperCase()+" , "+match.getSetup().getTournament() + ";");

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path
						+ (match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ? "GSA" : 
							match.getSetup().getHomeTeam().getTeamBadge().toUpperCase())+ CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path
						+ (match.getSetup().getAwayTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ? "GSA" : 
							match.getSetup().getAwayTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName01 " 
						+ match.getSetup().getHomeTeam().getTeamName2()+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName01 " 
						+ match.getSetup().getHomeTeam().getTeamName3() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName02 " 
						+  match.getSetup().getAwayTeam().getTeamName2() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName02 " 
						+  match.getSetup().getAwayTeam().getTeamName3() + ";");
				
				for(VariousText vtext : vartxt) {
					if(vtext.getVariousType().equalsIgnoreCase("LT_MATCH_ID") && vtext.getUseThis().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + vtext.getVariousText() + ";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "LIVE FROM "
								+ match.getSetup().getVenueName().split(",")[1] + ";");
					}
				}
				
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 150;");
				 // print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				  TimeUnit.SECONDS.sleep(1);
				  //print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				 

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}

	}
	

	private void populateResult(PrintWriter print_writer, String string, MatchAllData match, String broadcaster, String type) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Target's inning is null";
		} else {
			String summary = CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.TEAMNAME_3, "","",true).getTargetOrResult().toUpperCase()
				    .replace(match.getSetup().getHomeTeam().getTeamName1(), match.getSetup().getHomeTeam().getTeamName3())
				    .replace(match.getSetup().getAwayTeam().getTeamName1(), match.getSetup().getAwayTeam().getTeamName3());

			if(type.equalsIgnoreCase("BUG-RESULT")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "+ summary + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + summary + ";");
				if(CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.SHORT, "","",true)
						.getTargetOrResult().toUpperCase().contains(match.getMatch().getInning().get(0).getBatting_team().getTeamName4())) {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ (match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
								match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBaseTeamColor " + base_path
//							+ match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ (match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
								match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBaseTeamColor " + base_path
//							+ match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				}
			}
		}
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 300.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
	    TimeUnit.SECONDS.sleep(2);
	    print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
	    print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
	    print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		
		this.status = CricketUtil.SUCCESSFUL;
		
	}

	private void populateBugTarget(PrintWriter print_writer, String string, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+
//						CricketFunctions.GetTargetData(match).getTargetRuns()+ ";");
				String result = "";

				if (match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {

				    if (CricketFunctions.GetTargetData(match).getTargetOvers().equalsIgnoreCase("1")) {
				        result = match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
				                CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS";
				    } else {
				    	result = match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
			                    CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS";
				    }
				} else {
				    result = match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
			                CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS (" + 
			                match.getSetup().getTargetType().toUpperCase() + ")";
				}
				if(result.contains(match.getMatch().getInning().get(0).getBatting_team().getTeamName3())) {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ (match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
								match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ (match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
								match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+result+ ";");

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 60.0;");
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

	private void populateKeyPlayerDismissal(PrintWriter print_writer, String valueToProcess, MatchAllData match,
			String session_selected_broadcaster, CricketService cricketService) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				int homeID=0,awayID=0;
				String homeTeam="",awayTeam="";
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
						+ KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("Header")+ ";");
				for(Team tm: cricketService.getTeams()) {
					if(tm.getTeamName1().equalsIgnoreCase(KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("HomeTeam"))) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeLogo " + logo_path
								+ (tm.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : tm.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeBase " + base_path
								+ (tm.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : tm.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeLogoBW " + Logo_BW_path
								+ (tm.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : tm.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
						homeID=tm.getTeamId();
						homeTeam = tm.getTeamBadge();
					}else if(tm.getTeamName1().equalsIgnoreCase(KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("AwayTeam"))) {
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayLogo " + logo_path
								+ (tm.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : tm.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayBase " + base_path
								+ (tm.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : tm.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayLogoBW " + Logo_BW_path
								+ (tm.getTeamBadge().equalsIgnoreCase("GS") ? "GSA" : tm.getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
						awayID=tm.getTeamId();
						awayTeam =tm.getTeamBadge();
					}
				}
				for(Player tm: cricketService.getAllPlayer()) {
					if(tm.getFull_name().equalsIgnoreCase(KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("HomePlayer1"))&&
							homeID == tm.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomePlayerName01 "
										+ tm.getTicker_name().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomePlayerDesig01 "
								+KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("HomePlayer1Stat") + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomePlayer01 " + photo_path + 
								homeTeam +"\\\\"+ tm.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomePlayerRole01 " + icons_path + 
								getPlayerIconName(tm) + CricketUtil.PNG_EXTENSION + ";");
					}
					if(tm.getFull_name().equalsIgnoreCase(KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("HomePlayer2"))&&
							homeID == tm.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomePlayerName02 "
										+ tm.getTicker_name().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomePlayerDesig02 "
								+KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("HomePlayer2Stat") + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomePlayer02 " + photo_path + 
								homeTeam +"\\\\"+ tm.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomePlayerRole02 " + icons_path + 
								getPlayerIconName(tm) + CricketUtil.PNG_EXTENSION + ";");
					}
					if(tm.getFull_name().equalsIgnoreCase(KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("HomePlayer3"))&&
							homeID == tm.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomePlayerName03 "
								+ tm.getTicker_name().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomePlayerDesig03 "
								+KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("HomePlayer3Stat") + ";");
				
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomePlayer03 " + photo_path + 
								homeTeam +"\\\\"+ tm.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomePlayerRole03 " + icons_path + 
								getPlayerIconName(tm) + CricketUtil.PNG_EXTENSION + ";");
					}
					if(tm.getFull_name().equalsIgnoreCase(KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("AwayPlayer1"))&&
							awayID == tm.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayPlayerName01 "
										+ tm.getTicker_name().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayPlayerDesig01 "
								+KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("AwayPlayer1Stat") + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayPlayer01 " + photo_path + 
								awayTeam +"\\\\"+ tm.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayPlayerRole01 " + icons_path + 
								getPlayerIconName(tm) + CricketUtil.PNG_EXTENSION + ";");
					}
					if(tm.getFull_name().equalsIgnoreCase(KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("AwayPlayer2"))&&
							awayID == tm.getTeamId()) {
						print_writer.println(
								"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayPlayerName02 "
										+ tm.getTicker_name().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayPlayerDesig02 "
								+KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("AwayPlayer2Stat") + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayPlayer02 " + photo_path + 
								awayTeam +"\\\\"+ tm.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayPlayerRole02 " + icons_path + 
								getPlayerIconName(tm) + CricketUtil.PNG_EXTENSION + ";");
					}
					if(tm.getFull_name().equalsIgnoreCase(KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("AwayPlayer3"))&&
							awayID == tm.getTeamId()) {
						print_writer.println(
								"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayPlayerName03 "
										+ tm.getTicker_name().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayPlayerDesig03 "
								+KeyPlayer.get(Integer.valueOf(valueToProcess.split(",")[1])).get("AwayPlayer3Stat") + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayPlayer03 " + photo_path + 
								awayTeam +"\\\\"+ tm.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayPlayerRole03 " + icons_path + 
								getPlayerIconName(tm) + CricketUtil.PNG_EXTENSION + ";");
					}
				}
				
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
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
		case "BUG_HIGHLIGHT": case "BUG_PARTNERSHIP": case "BUG_POWERPLAY": case "MATCHID": case "MOST": case "NAMESUPER_SINGLE":case "FF_KEYPLAYER":
		case "TEAMS_LOGO":
			processAnimation(print_writer, "Out", "START", broadcaster);
			TimeUnit.SECONDS.sleep(1);
			break;
		}	
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
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
		case "EVEREST_LEGENDS_90":
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
													+ (bc.getHowOutText()== null ? "" : bc.getHowOutText()) + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
													+ bc.getRuns() + " ;");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
													+ bc.getBalls() + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 62.0;");
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
		case "EVEREST_LEGENDS_90":
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
													+ "4s: " + bc.getFours() + "  6s: " + bc.getSixes() + "  ;");

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
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C  "
													+ bc.getBalls() + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 62.0;");
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
		case "EVEREST_LEGENDS_90":
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
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
													+ boc.getPlayer().getTicker_name().toUpperCase() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A  "
													+ "ECON: " + boc.getEconomyRate() + ";");

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B  "
													+ boc.getWickets() + slashOrDash + boc.getRuns() + ";");

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C  "
													+  CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + ";");
								}
							}
							break;
						}

					}

				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 62.0;");
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
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "POWERPLAY" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
							+ getPowerPlayScore(match ,inn, whichInning, match.getEventFile().getEvents()) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + "" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 1;");
					if (whichInning == 1) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamName3() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + ";");
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 62.0;");
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
	
	public void populateBugPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match,String session_selected_broadcaster) throws Exception {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						String Left_Batsman ="",Right_Batsman="";
						List<Partnership > part = CricketFunctions.ConcussedPartnership(match.getMatch(), inn.getInningNumber());

						for (BattingCard as : inn.getBattingCard()) {
							if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								Left_Batsman = as.getPlayer().getTicker_name();
							}
							if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								Right_Batsman = as.getPlayer().getTicker_name();
							}
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + " & " + Right_Batsman + ";");
						
						if(part.get(part.size()-1).getPartnershipNumber()==0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "PARTNERSHIP" + ";");
						}else {
							if(part.get(part.size()-1).getPartnershipNumber() == 1) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (part.get(part.size()-1).getPartnershipNumber()) + "st WICKET PARTNERSHIP" + ";");
							}else if(part.get(part.size()-1).getPartnershipNumber() == 2) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (part.get(part.size()-1).getPartnershipNumber()) + "nd WICKET PARTNERSHIP" + ";");
							}else if(part.get(part.size()-1).getPartnershipNumber() == 3) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (part.get(part.size()-1).getPartnershipNumber()) + "rd WICKET PARTNERSHIP" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (part.get(part.size()-1).getPartnershipNumber()) + "th WICKET PARTNERSHIP" + ";");
							}
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() +"* ("+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls()+");");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C ;");
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 52.0;");
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
	public void populateBugMultipartnership(PrintWriter print_writer, String viz_scene,int whichinning, int partnership, MatchAllData match,String session_selected_broadcaster) throws Exception {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == whichinning) {
						String Left_Batsman ="",Right_Batsman="";
						List<Partnership > part = CricketFunctions.ConcussedPartnership(match.getMatch(), inn.getInningNumber());
						for (BattingCard hs : inn.getBattingCard()) {
							if(hs.getPlayerId() == inn.getPartnerships().get(partnership - 1).getFirstBatterNo()) {
								Left_Batsman = hs.getPlayer().getTicker_name();
							}
							if(hs.getPlayerId() == inn.getPartnerships().get(partnership - 1).getSecondBatterNo()) {
								Right_Batsman = hs.getPlayer().getTicker_name();
							}
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + " & " + Right_Batsman + ";");
						if(part.get(partnership - 1).getPartnershipNumber() == 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "PARTNERSHIP" + ";");
						}else {
							if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 1) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "st WICKET PARTNERSHIP" + ";");
							}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 2) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "nd WICKET PARTNERSHIP" + ";");
							}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 3) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "rd WICKET PARTNERSHIP" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "th WICKET PARTNERSHIP" + ";");
							}
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
								inn.getPartnerships().get(partnership - 1).getTotalRuns() +" ("+inn.getPartnerships().get(partnership - 1).getTotalBalls()+ ");");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C ;");
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 42.0;");
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
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "HIGHLIGHTS" + ";");

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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C   "
							+ CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(),
									match.getMatch().getInning().get(0).getTotalBalls())+ ";");
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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C   "
							+ CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(),
									match.getMatch().getInning().get(1).getTotalBalls())+ ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 1;");

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 42.0;");
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
	
	public void populateMatchId(PrintWriter print_writer, String viz_scene, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
						+ match.getSetup().getMatchIdent().toUpperCase()+ ";");

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeLogo " + logo_path
						+ (match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ?
								"GSA" : match.getSetup().getHomeTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeBase " + base_path
						+ (match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ? 
								"GSA" : match.getSetup().getHomeTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeLogoBW " + Logo_BW_path
						+ (match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ? 
								"GSA" : match.getSetup().getHomeTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayLogo " + logo_path
						+ (match.getSetup().getAwayTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ? "GSA" : 
							match.getSetup().getAwayTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayBase " + base_path
						+ (match.getSetup().getAwayTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ? "GSA" : 
							match.getSetup().getAwayTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayLogoBW " + Logo_BW_path
						+ (match.getSetup().getAwayTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ? "GSA" : 
							match.getSetup().getAwayTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "LIVE FROM "
						+ match.getSetup().getVenueName().toUpperCase() + ";");
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
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
	public void populateBugsDB(PrintWriter print_writer, String viz_scene, EverestBugs bug, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {

				if (bug.getText1() != null && bug.getText2() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ bug.getText1().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
							+ bug.getText2().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 0;");
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
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 62.0;");
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
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				if(ns.getSponsor() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + Sponsor_path
							+ ns.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIMG_BASE " + base_path
						+ "ASA" + CricketUtil.PNG_EXTENSION + ";");
				if (ns.getFirstname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
							+ ns.getSurname() + ";");
				} else if (ns.getSurname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
							+ ns.getFirstname() + ";");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
							+ ns.getFirstname() +" "+ns.getSurname() + ";");
				}
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
	private void populateToss(PrintWriter print_writer, String string, Integer teamid, String text, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				if(match.getSetup().getHomeTeamId()==teamid) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "
							+ match.getSetup().getHomeTeam().getTeamName3()+ " WON TOSS ;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
							+ ( match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
								 match.getSetup().getHomeTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "
							+ match.getSetup().getAwayTeam().getTeamName3()+ " WON TOSS ;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + logo_path
							+ ( match.getSetup().getAwayTeam().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
							 match.getSetup().getAwayTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "+text+ ";");

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 60.0;");
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
	private void populateTarget(PrintWriter print_writer, String string, MatchAllData match, String broadcaster2) throws InterruptedException {
		
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				String result = "";
				String badge = "";
				if (match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {

				    if (CricketFunctions.GetTargetData(match).getTargetOvers().equalsIgnoreCase("1")) {
				        result = match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
				                CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + (Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6)+ " BALLS";
				    } else {
				    	result = match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
			                    CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " +  CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS";
				    }
				} else {
				    result = match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
			                CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " +  CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS (" + 
			                match.getSetup().getTargetType().toUpperCase() + ")";
				}
				if(result.contains(match.getMatch().getInning().get(0).getBatting_team().getTeamName4())) {
					 badge = match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase();
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ (match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
								match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				}else {
					 badge = match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase();
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ (match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
								match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				}
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+result+ ";");
//			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBaseTeamColor " + base_path
//					+ badge + CricketUtil.PNG_EXTENSION + ";");
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 159.0;");
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
	
	private void populateNameSuperSingle(PrintWriter print_writer,NameSuper ns, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				if(ns.getSponsor() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + Sponsor_path
						 + ns.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIMG_BASE " + base_path
						+ "ASA" + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " 
					    + (ns.getFirstname() != null ? ns.getFirstname() + 
				(ns.getSurname() != null ? " " + ns.getSurname() : "") : (ns.getSurname() != null ? ns.getSurname() : "")) + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + ";");
				
				  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 115;");
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
		case "EVEREST_LEGENDS_90":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				String Home_or_Away = "";

				if (TeamId == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ (match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ? "GSA" :
								match.getSetup().getHomeTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
					Home_or_Away = match.getSetup().getHomeTeam().getTeamName3();
					for (Player hs : match.getSetup().getHomeSquad()) {
						if (playerId == hs.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
									+ hs.getFull_name() + ";");
						}
						if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
									+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + ";");
						}
					}
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ (match.getSetup().getAwayTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("GS") ? "GSA" :
								match.getSetup().getAwayTeam().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
					
					Home_or_Away = match.getSetup().getAwayTeam().getTeamName3().toUpperCase();
					for (Player as : match.getSetup().getAwaySquad()) {
						if (playerId == as.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
									+ as.getFull_name() + ";");
						}
						if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
									+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + ";");
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIMG_BASE " + base_path
						+ "ASA" + CricketUtil.PNG_EXTENSION + ";");
				
				switch (captainWicketKeeper.toUpperCase()) {
				case CricketUtil.CAPTAIN:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
							+ captainWicketKeeper.toUpperCase() + " , " + Home_or_Away + ";");
					break;
				case "PLAYER OF THE MATCH":
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
	public static String getPlayerIconName(Player as) {
	    String playerIcon = "";

	    if (as.getRole()!=null && (as.getRole().equalsIgnoreCase("WICKET_KEEPER")||
	    		as.getRole().equalsIgnoreCase("WICKET-KEEPER"))) {
	        playerIcon = "Keeper";  
	    }else if (as.getRole().equalsIgnoreCase("BATSMAN")) {
	        if (as.getBattingStyle().equalsIgnoreCase("RHB")) {
	            playerIcon = "Batsman";
	        } else if (as.getBattingStyle().equalsIgnoreCase("LHB")) {
	            playerIcon = "LeftHandBatsman";
	        }
	    } else if (as.getRole().equalsIgnoreCase("BOWLER")) {
	        if (as.getBowlingStyle() == null) {
	            playerIcon = "FastBowler";
	        } else {
	            switch (as.getBowlingStyle()) {
	                case "RF": case "RFM": case "RMF": case "RM": case "RSM":
	                case "LF": case "LFM": case "LMF": case "LM":
	                    playerIcon = "FastBowler";
	                    break;
	                case "ROB": case "RLB": case "LSL": case "WSL":
	                case "LCH": case "RLG": case "WSR": case "LSO":
	                    playerIcon = "SpinBowler";
	                    break;
	            }
	        }
	    } else if (as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
	        if (as.getBowlingStyle() == null) {
	            playerIcon = "FastBowlerAllrounder";
	        } else {
	            switch (as.getBowlingStyle()) {
	                case "RF": case "RFM": case "RMF": case "RM": case "RSM":
	                case "LF": case "LFM": case "LMF": case "LM":
	                    playerIcon = "FastBowlerAllrounder";
	                    break;
	                case "ROB": case "RLB": case "LSL": case "WSL":
	                case "LCH": case "RLG": case "WSR": case "LSO":
	                    playerIcon = "SpinBowlerAllrounder";
	                    break;
	            }
	        }
	    }

	    return playerIcon;  
	}
	public static void preview(PrintWriter print_writer ) throws InterruptedException {
	  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
	  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
	  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
	  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 200;");
	  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
	  print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
	  TimeUnit.SECONDS.sleep(1);
	  print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
	  print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	public static String ordinal(int i) {
	    int mod100 = i % 100;
	    int mod10 = i % 10;
	    if(mod10 == 1 && mod100 != 11) {
	        return i + "st";
	    } else if(mod10 == 2 && mod100 != 12) {
	        return i + "nd";
	    } else if(mod10 == 3 && mod100 != 13) {
	        return i + "rd";
	    } else {
	        return i + "th";
	    }
	}
}

	