package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import jakarta.xml.bind.JAXBException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.cricket.containers.Infobar;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBContext;
import com.cricket.containers.ContainerData;
import com.cricket.containers.DuckWorthLewis;
import com.cricket.containers.Scene;
import com.cricket.model.MatchAllData;
import com.cricket.model.Statistics;
import com.cricket.service.CricketService;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Commentator;
import com.cricket.model.Configuration;
import com.cricket.model.EventFile;
import com.cricket.model.FallOfWicket;
import com.cricket.model.FieldersData;
import com.cricket.model.Fixture;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.ImpactData;
import com.cricket.model.InfobarStats;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.LeagueTeam;
import com.cricket.model.Match;
import com.cricket.model.NameSuper;
import com.cricket.model.OverByOverData;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Pointers;
import com.cricket.model.Setup;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AFGHANISTAN_T20 extends Scene{

	public String broadcaster = "AFGHANISTAN_T20";
	public Infobar infobar = new Infobar();
	public String which_graphics_onscreen = "";
	private String slashOrDash = "-";
	public String status;
	public String director;
	public int lb_count=1;
	private String logo_path = "IMAGE*/Default/AfghanitanT20_2024/TeamBadges/";
	private String sponsor_path = "IMAGE*/Default/AfghanitanT20_2024/Sponsors/";
	private String photo_path = "C:\\\\Images\\\\AFGHANISTAN_T20\\\\Photos\\\\";
	private String local_photo_path = "\\\\c\\\\Images\\\\AFGHANISTAN_T20\\\\Photos\\\\";
	private int lastXBalls = 0;
	public String diectoryPath = "";
	private String infobarFreeText = "", commentatorsID;
	private String isAudioOn = "true";
	
	boolean ident_on_screen = false;

	private boolean is_powerplay_on_screen = false, free_hit_on_screen = false, isTickerShrinked = false, powerPlayDirectorOnScreen = false;
	
	public boolean isIs_powerplay_on_screen() {
		return is_powerplay_on_screen;
	}

	public void setIs_powerplay_on_screen(boolean is_powerplay_on_screen) {
		this.is_powerplay_on_screen = is_powerplay_on_screen;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AFGHANISTAN_T20() {
		super();
	}
	public AFGHANISTAN_T20(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	
	public Infobar updateFieldPlotter(List<Scene> scenes, MatchAllData match, List<MatchAllData> tournament_matches,
			boolean show_speed, List<PrintWriter> print_writer, String filename)
			throws InterruptedException, IOException {
		if (infobar.isFieldPlotter_on_screen() == true) {
			FieldersData fielderFormation = new FieldersData();
			fielderFormation = CricketFunctions
					.getFielderFormation(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + filename);

			if (fielderFormation.isCheckbox() == true) {
				populateFieldPlotter(print_writer.get(0), scenes.get(1).getScene_path(), match, broadcaster, filename);
			}
		}
		return infobar;
	}
	
	public String resetInfobarAnimation(PrintWriter print_writer,String which_broadcaster,String SceneType) throws InterruptedException {
		String status = "";
		
		switch(which_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			switch(SceneType.toUpperCase()) {
			case "FF":
				if(!infobar.isInfobar_down()) {
					if(infobar.isInfobar_on_screen() == true) {
						AnimateOutGraphics(print_writer, "FF_OUT");
					}
				}
				which_graphics_onscreen = "SCOREBUG";
				break;
			case "LT": case "MINI":
				if(!isTickerShrinked) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MiniOut START \0");
				}
				which_graphics_onscreen = "SCOREBUG";
				break;
			case "BUG":
				if(!infobar.isInfobar_down()) {
					if(infobar.isInfobar_on_screen() == true) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_LT_Out START \0");
					}
				}
				which_graphics_onscreen = "SCOREBUG";
				break;
			}
			break;
		}
		
		return status;
	}
	public Infobar updateInfobar(List<Scene> scenes, MatchAllData match, PrintWriter print_writer, String session_directoryPath) throws InterruptedException
	{
		System.out.println("HEY");
		if (CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10
				|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
			if(infobar.isInfobar_on_screen()==true) {
				if (infobar.isResult_on_screen() == false) {

					infobar.setIdent_section("RESULT");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + " " + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + 
							logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " + 
							logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName1*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName2*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + CricketFunctions.
							GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
					
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-INFOBAR");
					TimeUnit.MILLISECONDS.sleep(200);
					AnimateInGraphics(print_writer, "IDENT");
					TimeUnit.MILLISECONDS.sleep(200);
					which_graphics_onscreen = "IDENT";
					infobar.setInfobar_on_screen(true);
					infobar.setResult_on_screen(true);
				}
			}
		}else if(infobar.isInfobar_on_screen() == true) {
				if(infobar.getIdent_section() != null && !infobar.getIdent_section().trim().isEmpty()) {
					//infobar = populateInfobarIdent(infobar, print_writer, match, broadcaster);
				}else {
					System.out.println("2");
					populateInfobarTeamScore(true, print_writer, match, broadcaster);
					infobar = processInfobarPowerplay(infobar, print_writer, broadcaster, match);
					if(infobar.getMiddle_section() != null && !infobar.getMiddle_section().trim().isEmpty()) {
						infobar = populateInfobarMiddleSection(infobar, true, print_writer, match, broadcaster, null, null, session_directoryPath);
					}
					infobar = populateInfobarBottomRight(infobar, true,print_writer, match, broadcaster);
					infobar = populateBottomRightBottom(infobar, true, print_writer, match, broadcaster);
				}
			
		}
		return infobar;
	}
	public Object processGraphics(String whatToProcess, String valueToProcess, MatchAllData match, List<MatchAllData> tournament_matches, List<Tournament> past_tournament_stats,
			List<Scene> scenes,List<Statistics> statistics, CricketService cricketService, PrintWriter print_writer, Configuration config, List<HeadToHeadPlayer> head_to_head,String plotterData,String session_directoryPath) 
			throws JAXBException, InterruptedException, NumberFormatException, ParseException, IllegalAccessException, InvocationTargetException, IOException, URISyntaxException
	{
		diectoryPath = session_directoryPath;
		System.out.println(whatToProcess.toUpperCase());
		switch (whatToProcess.toUpperCase()) {
		case "TURN_ON_OR_OFF_AUDIO":
			isAudioOn = valueToProcess;
			return null;
		case "BUG_GRAPHICS-OPTIONS": case "HOWOUT_GRAPHICS-OPTIONS": case "BATSMANSTATS_GRAPHICS-OPTIONS": case "BOWLERSTATS_GRAPHICS-OPTIONS":
		case "NAMESUPER_PLAYER_GRAPHICS-OPTIONS": case "L3PLAYERPROFILE_GRAPHICS-OPTIONS": case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "BOTTOMLEFT_GRAPHICS-OPTIONS": 
		case "BOTTOMRIGHT_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": case "COMPARISION-GRAPHICS-OPTIONS": case "BOTTOM_GRAPHICS-OPTIONS":
		case "ANIMATE_PLAYINGXI-OPTIONS": case "PROJECTED_GRAPHICS-OPTIONS": case "TARGET_GRAPHICS-OPTIONS": case "GRIFF_GRAPHICS-OPTIONS": 
		case "PLAYERSUMMARY_GRAPHICS-OPTIONS": case "BUG_DISMISSAL_GRAPHICS-OPTIONS": case "TOP_GRAPHICS-OPTIONS": case "BUG_BOWLER_GRAPHICS-OPTIONS": 
		case "HOWOUT_WITHOUT_FIELDER_GRAPHICS-OPTIONS": case "BOWLERDETAILS_GRAPHICS-OPTIONS":	case "NEXTTOBAT_GRAPHICS-OPTIONS": case "BOWLERSUMMARY_GRAPHICS-OPTIONS":
		case "LANDMARK_GRAPHICS-OPTIONS": case "EQUATION_GRAPHICS-OPTIONS": case "BALL_GRIFF_GRAPHICS-OPTIONS": case "POSITION_LANDMARK_GRAPHICS-OPTIONS": 
		case "BATSMAN_THIS_MATCH_GRAPHICS-OPTIONS": case "BOWLER_THIS_MATCH_GRAPHICS-OPTIONS": case "PLAYERS_GRAPHICS-OPTIONS": case "FFTHIS_SERIES_GRAPHICS-OPTIONS":
		case "BATSMAN_STYLE_GRAPHICS-OPTIONS": case "GENERIC_GRAPHICS-OPTIONS": case "IDENT_GRAPHICS-OPTIONS": case "RIGHT_GRAPHICS-OPTIONS": 
		case "SQUAD_GRAPHICS-OPTIONS": case "LTTHIS_SERIES_GRAPHICS-OPTIONS": 
			return new ObjectMapper().writeValueAsString(match).toString();
		case "LT_POINTERS_GRAPHICS-OPTIONS": case "FF_POINTERS_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getPointers()).toString();
		case "TEAM_FIXTURES_GRAPHICS-OPTIONS": case "TEAM_SQUAD_GRAPHICS-OPTIONS": case "MOST_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getTeams()).toString();
		case "NAMESUPER_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "LTMATCH-PROMO_GRAPHICS-OPTIONS":
		case "PLAYOFF_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
			
		case "MOST_LEADERBOARD_GRAPHICS-OPTIONS": case "MOST1_GRAPHICS-OPTIONS": case "MOST1_WICKETS_GRAPHICS-OPTIONS":
			List<Tournament> tourna_stats = CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, null);
//			List<Tournament> tourna_stats = CricketFunctions.extractTournamentStats("CURRENT_MATCH_DATA",false, tournament_matches, cricketService, match,past_tournament_stats);
			switch (whatToProcess) {
			case "MOST_LEADERBOARD_GRAPHICS-OPTIONS": case "MOST1_GRAPHICS-OPTIONS":
				Collections.sort(tourna_stats,new CricketFunctions.BatsmenMostRunComparator());
				break;
			case "MOST1_WICKETS_GRAPHICS-OPTIONS":
				Collections.sort(tourna_stats,new CricketFunctions.BowlerWicketsComparator());
				break;
			}
			return new ObjectMapper().writeValueAsString(tourna_stats).toString();
			
		case "PROMPT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getInfobarStats()).toString();
			
		case "MOST_TOP5_TEAM_GRAPHICS-OPTIONS":
		case "LEADERBOARD_GRAPHICS-OPTIONS": case "WICKETS_GRAPHICS-OPTIONS": case "FOURS_GRAPHICS-OPTIONS": case "SIXES_GRAPHICS-OPTIONS":
			List<Tournament> tourn_stats = CricketFunctions.extractTournamentStats("CURRENT_MATCH_DATA",false, tournament_matches, cricketService, match,past_tournament_stats);
			switch (whatToProcess) {
			case "MOST_TOP5_TEAM_GRAPHICS-OPTIONS":
				Collections.sort(tourn_stats,new CricketFunctions.BatsmenMostRunComparator());
				break;
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
			
		case "BUG_DB_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getBugs()).toString();
		
		case "POPULATE-LEADERBOARD_CHANGEON_1": case "POPULATE-LEADERBOARD_CHANGEON_2": case "POPULATE-LEADERBOARD_CHANGEON_3": case "POPULATE-LEADERBOARD_CHANGEON_4":
		case "POPULATE-LEADERBOARD_CHANGEON_5":
			if(which_graphics_onscreen == "LEADERBOARD" || which_graphics_onscreen == "MOST_LEADERBOARD") {
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*HighlightAll$Highlight" + lb_count + "_Out START \0");
				TimeUnit.MILLISECONDS.sleep(500);
				lb_count = Integer.valueOf(whatToProcess.split("_")[2]);
				TimeUnit.MILLISECONDS.sleep(500);
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*HighlightAll$Highlight" + lb_count + "_In START \0");
			}
			break;
		
		//ScoreBug
		case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-INFOBAR-PROMPT":
		case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-IDENT": case "POPULATE-INFOBAR-RIGHT": case "POPULATE-DIRECTOR": case "POPULATE-INFOBAR_IDENT_DATA":
		case "POPULATE-INFOBAR-LAST_X_BALLS": case "POPULATE-INFOBAR-FREE_TEXT": case "POPULATE-COMMENTATORS":
		
		//FF
		case "POPULATE-MINI-BATTINGCARD": case "POPULATE-MINI-BOWLINGCARD": case "POPULATE-MINI-BATSMAN_VS_ALLBOWLERS":
		case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-FF-PLAYERPROFILE":
		case "POPULATE-FF-PLAYERPROFILEBALL": case "POPULATE-FF-DOUBLETEAMS": case "POPULATE-FF-THISSERIES": case "POPULATE-FF-LEADERBOARD": case "POPULATE-FF-MATCHID": 
		case "POPULATE-FF-PLAYINGXI": case "POPULATE-LT-PARTNERSHIP": case "POPULATE-FF-LANDMARK": case "POPULATE-FF-LANDMARK_BALL": case "POPULATE-PREVIOUS_SUMMARY": case "POPULATE-FF-SQUAD":
		case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-POINTS_TABLE": case "POPULATE-MANHATTAN": case "POPULATE-MATCH_PROMO": case "POPULATE-FF-TEAMS_LOGO":
		case "POPULATE-TIEID-DOUBLE": case "POPULATE-MOSTRUNS": case "POPULATE-MOSTWICKETS": case "POPULATE-MOSTFOURS": case "POPULATE-MOSTSIXES": case "POPULATE-HIGHESTSCORE":
		case "POPULATE-WORM": case "POPULATE-FF-BATGRIFF": case "POPULATE-FF-BALLGRIFF": case "POPULATE-FF-POINTERS": case "POPULATE-FF-FIXTURES": case "POPULATE-FF-FIXTURES_TEAM":
		case "POPULATE-FF-TEAM_SQUAD": case "POPULATE-INN_BUILDER": case "POPULATE-RICHEIS": case "POPULATE-MOST_RUNS": case "POPULATE-MOST_LEADERBOARD":
		case "POPULATE-FF-THISSERIES_BALL":	
		
		//Bug
		case "POPULATE-L3-BUG": case "POPULATE-L3-BUG-DB": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-BUG-BOWLER": case "POPULATE-L3-BUGTARGET":
		case "POPULATE-BUG_POWERPLAY": case "POPULATE-L3-BUG-TOSS": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-MULTI_PARTNERSHIP": case "POPULATE-BUGPARTNERSHIP":
		case "POPULATE-BAT-POPUP": case "POPULATE-BOWL-POPUP":
			
		//LT
		case "POPULATE-IMPACT": case "CHANGE_ON-IMPACT": case "POPULATE-PHASE-COMPARISON":
		case "POPULATE-L3-POINTERS": case "POPULATE-PHASE": case "POPULATE-LT-LINEUP": case "POPULATE-L3-THISSERIES_BALL": case "POPULATE-L3-CAPTAIN-PLAYER":
		case "POPULATE-L3-HOWOUT": case "POPULATE-L3MATCH_PROMO": case "POPULATE-L3-BATSMANSTATS": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER":  
		case "POPULATE-L3-THISSERIES": case "POPULATE-LT-PROJECTED": case "POPULATE-L3-TARGET": case "POPULATE-L3-TEAMSUMMARY": case "POPULATE-L3-PLAYERSUMMARY":
		case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-L3-FALLOFWICKET": case "POPULATE-POWERPLAY": case "POPULATE-L3-COMPARISION":
		case "POPULATE-LT-MATCHID": case "POPULATE-L3-BOWLERSTATS": case "POPULATE-L3-SPLIT": case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER": case "POPULATE-HOWOUT_QUICK":
		case "POPULATE-L3-BOWLERSUMMARY": case "POPULATE-L3-NEXT_TO_BAT": case "POPULATE-L3-BOWLERDETAILS": case "POPULATE-LT-POWERPLAY": case "POPULATE-LT-EQUATION": 
		case "POPULATE-L3-BATSMAN_THIS_MATCH": case "POPULATE-L3-BOWLER_THIS_MATCH":case "POPULATE-LTPOINTS_TABLE":	case "POPULATE-BOWLER_STYLE": case "POPULATE-BATSMAN_STYLE": 
		case "POPULATE-L3-GENERIC":  case "POPULATE-DLS": case "POPULATE-DLS-EQUATION": case "POPULATE-NEXT_TO_BAT": case "POPULATE-LT-WEATHER":
		   
		case "LOAD_MANUAL_XML_SCENE": case "POPULATE-PLAYOFFS":
			
		case "POPULATE-FIELD_PLOTTER_USPL":
		 
			
			if(which_graphics_onscreen == "SCOREBUG" || which_graphics_onscreen == "IDENT" || which_graphics_onscreen == "PLOTTER_ICC") {
				
			}else if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
					 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
					
					 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
					 
					 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
					 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
					 
					 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD")||
					 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP")||
					 
					 which_graphics_onscreen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphics_onscreen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
					 which_graphics_onscreen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY")||
					 which_graphics_onscreen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 which_graphics_onscreen == "IMPACT"){
				//AnimateOutGraphics(print_writer, which_graphics_onscreen.toUpperCase());
			}/*else if(which_graphics_onscreen == "FFPLAYERPROFILE"  && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PLAYERPROFILE") ) {
				
			}*/else if(which_graphics_onscreen != "") {
				this.status = "FF_AND_LT";
				return null;
//				AnimateOutGraphics(print_writer, which_graphics_onscreen.toUpperCase());
			}
			switch(whatToProcess.toUpperCase()) {
			case "LOAD_MANUAL_XML_SCENE":
				scenes.set(2, new Scene("/Default/AFGHANISTAN_T20/" + valueToProcess.replace(".xml", ""),"MIDDLE_LAYER"));
				//scenes.get(1).scene_load(print_writer,broadcaster);
				break;
			case "CHANGE_ON-IMPACT":
			case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-POWERPLAY":
			case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-RIGHT": case "POPULATE-DIRECTOR": case "POPULATE-INFOBAR-PROMPT":
			case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-IDENT": case "POPULATE-INFOBAR_IDENT_DATA": case "POPULATE-INFOBAR-LAST_X_BALLS":
			case "POPULATE-INFOBAR-FREE_TEXT": case "POPULATE-COMMENTATORS":
				break;
			/*case "POPULATE-L3-INFOBAR": case "POPULATE-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
					break;
				}else {
					scenes.get(0).scene_load(print_writer, broadcaster);
				}
				break;*/
			default:
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
						 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
						 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						
						 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
						 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						 
						 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
						 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
						 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						 
						 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD")||
						 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP")||
						 
						 which_graphics_onscreen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphics_onscreen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
						 which_graphics_onscreen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY")||
						 which_graphics_onscreen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE")) {
				}else {
					if(!valueToProcess.contains(",")) {
						scenes.get(1).setScene_path(valueToProcess);
						scenes.get(1).setWhich_layer("MIDDLE_LAYER");
					}else {
						scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
						scenes.get(1).setWhich_layer("MIDDLE_LAYER");
					}
					scenes.get(1).scene_load(print_writer, broadcaster);
					print_writer.println("-1 RENDERER*STAGE SHOW 0.0 \0");
 
					}
					
					break;
				}
				switch(whatToProcess.toUpperCase()) {
				case "LOAD_MANUAL_XML_SCENE":
					ContainerData this_Data = (ContainerData) JAXBContext.newInstance(ContainerData.class).createUnmarshaller().unmarshal(
							new File(CricketUtil.CRICKET_DIRECTORY + "Manual/Data/" + valueToProcess));
					
					for(int i = 0; i < this_Data.getContainers().size() ; i++) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " 
								+ this_Data.getContainers().get(i).getContainer_id() + " SET " 
								+ this_Data.getContainers().get(i).getContainer_value() + " \0");
					}
					
					which_graphics_onscreen = "MANUAL";
					return new ObjectMapper().writeValueAsString(this_Data).toString();
				}
				
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-FF-BATGRIFF":
					populateBatGriff(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[3]),head_to_head, cricketService, match, broadcaster, config);
					
//					populateBatGriff(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[3]),
//							CricketFunctions.extractTournamentStats("PAST_MATCHES_DATA",false, tournament_matches, cricketService, match,null),cricketService.getTeams(),match,broadcaster,config);
					break;
				case "POPULATE-FF-BALLGRIFF":
					populateBallGriff(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[3]),head_to_head, cricketService, match, broadcaster, config);
					
//					populateBallGriff(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[3]),
//							CricketFunctions.extractTournamentStats("PAST_MATCHES_DATA",false, tournament_matches, cricketService, match,null),cricketService.getTeams(),match,broadcaster,config);
					break;
				case "POPULATE-HIGHESTSCORE":
					populateHighestScore(print_writer, valueToProcess.split(",")[0],
							CricketFunctions.extractTournamentStats("CURRENT_MATCH_DATA",false, tournament_matches, cricketService, match,past_tournament_stats),
							match,broadcaster);
					break;
				case "POPULATE-MINI-BATSMAN_VS_ALLBOWLERS":
					populateBatsmanVsAllBowlers(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), match, cricketService, broadcaster);
					break;
				case "POPULATE-MINI-BATTINGCARD":
					populateMiniBattingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-MINI-BOWLINGCARD":
					populateMiniBowlingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				
				case "POPULATE-FF-SCORECARD":
					populateScorecard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match,cricketService, broadcaster);
					break;
					
				case "POPULATE-FF-BOWLINGCARD":
					//AnimateInGraphics(print_writer, "RESET");
					populateBowlingcard(print_writer, valueToProcess.split(",")[0], false, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-BUG_POWERPLAY":
					populateBugPowerPLay(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-FF-PARTNERSHIP":
					populatePartnership(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, cricketService, broadcaster);
					break;
				
				case "POPULATE-FF-MATCHSUMMARY":
					//AnimateInGraphics(print_writer, "RESET");
					populateMatchsummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getVariousTexts(),
							match, broadcaster);
					break;
				case "POPULATE-POINTS_TABLE":
					LeagueTable league_table = null;
					
					if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
						league_table = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().
								unmarshal(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
					}
					
					populatePointsTable(print_writer, valueToProcess.split(",")[0],league_table.getLeagueTeams(),cricketService.getTeams(), cricketService.getVariousTexts(),broadcaster,match);
					break;
				case "POPULATE-LTPOINTS_TABLE":
					LeagueTable ltleague_table = null;
					
					if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
						ltleague_table = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().
								unmarshal(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
					}
					
					populateLtPointsTable(print_writer, valueToProcess.split(",")[0],ltleague_table.getLeagueTeams(),
							cricketService.getTeams(),match,broadcaster);
					break;
				case "POPULATE-FIELD_PLOTTER_USPL": 
					  infobar.setFieldPlotter_on_screen(true);
					  populateFieldPlotter(print_writer, valueToProcess.split(",")[0],match,
					  broadcaster,plotterData);
					  break;	
				case "POPULATE-MOST_LEADERBOARD":
					populateTeamLeaderBoard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]),
							CricketFunctions.extractTournamentStats("CURRENT_MATCH_DATA",false, tournament_matches, cricketService, match,past_tournament_stats),
							cricketService.getTeams(),match, broadcaster, config);
					break;
					
				case "POPULATE-MOST_RUNS":
					populateMostRunsTeam(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA",false, head_to_head, cricketService, match,past_tournament_stats),
							cricketService.getTeams(),match, broadcaster, config);
					
					//System.out.println(CricketFunctions.extractTournamentStats("PAST_MATCHES_DATA",false, tournament_matches, cricketService, match,null));
					break;
				
				case "POPULATE-FF-LEADERBOARD":
					populateLeaderBoard(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
							CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats),
							cricketService.getTeams(),match, broadcaster, config);
					break;
				case "POPULATE-BOWL-POPUP":
					populateBowlPopUp(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match,cricketService, broadcaster, config);
					break;
				case "POPULATE-BAT-POPUP":
					populateBatPopUp(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match,cricketService, broadcaster, config);
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
				case "POPULATE-L3-POINTERS":
					for(Pointers PT : cricketService.getPointers()) {
					  if(PT.getPointersId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populatePointers(print_writer, valueToProcess.split(",")[0], PT, match, broadcaster);
					  }
					}
					break;
				case "POPULATE-FF-POINTERS":
					for(Pointers PT : cricketService.getPointers()) {
						if(PT.getPointersId() == Integer.valueOf(valueToProcess.split(",")[1])) {
							populateFFPointers(print_writer, valueToProcess.split(",")[0], PT, match, broadcaster, config);
						}
					}
					break;
				case "CHANGE_ON-IMPACT":
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Change START \0");
					break;
				case "POPULATE-IMPACT":
					populateImpactPlayer(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]) , match, cricketService, broadcaster, config);
					break;
				case "POPULATE-FF-FIXTURES":
					populateFFFixtures(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1], cricketService.getFixtures(),cricketService.getTeams(),
							match, broadcaster);
					break;
				case "POPULATE-FF-FIXTURES_TEAM":
					populateFFFixturesTeams(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), cricketService.getFixtures(),
							cricketService.getTeams(),match, broadcaster);
					break;
				case "POPULATE-FF-TEAM_SQUAD":
					populateFFTeamSquad(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), cricketService.getAllPlayer(),
							cricketService.getTeams(),match, broadcaster);
					break;
				case "POPULATE-INN_BUILDER":
					populateFFInnBuilder(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]), cricketService.getAllPlayer(),cricketService.getTeams(),match,broadcaster, config);
					break;
				case "POPULATE-L3-BUG-TOSS":
					populateBugToss(print_writer,valueToProcess.split(",")[0],match,broadcaster);
					break;
				case "POPULATE-LT-BUG_HIGHLIGHT":
					populateBugHighlight(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-BUGPARTNERSHIP":
					populateBugPartnership(print_writer, valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-MULTI_PARTNERSHIP":
					populateBugMultipartnership(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
					break;
					
				case "POPULATE-L3-HOWOUT":
					populateHowout(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
					break;
				case "POPULATE-HOWOUT_QUICK":
					populateQuickHowout(print_writer, valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER":
					populateHowoutWithoutFielder(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
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
				case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-L3-CAPTAIN-PLAYER":
					populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0],
							valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(), match, broadcaster);
					break;
				case "POPULATE-FF-MATCHID":
					populateMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-LT-MATCHID":
					populateLTMatchId(print_writer,valueToProcess.split(",")[0],cricketService.getVariousTexts(), match, broadcaster);
					break;
				case "POPULATE-L3MATCH_PROMO":
					populateLtMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(),match , broadcaster);
					break;
				case "POPULATE-MATCH_PROMO":
					populateMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(),cricketService.getVariousTexts(),match , broadcaster);
					break;
				case "POPULATE-PLAYOFFS":
					populatePlayOff(print_writer, valueToProcess.split(",")[0] , cricketService ,match , broadcaster);
					break;
				case "POPULATE-L3-COMPARISION":
					populateComparision(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-LT-PARTNERSHIP":
					populateLTPartnership(print_writer, valueToProcess.split(",")[0], match, broadcaster, config);
					break;
				case "POPULATE-L3-SPLIT":
					populateSplit(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
					break;
				case "POPULATE-L3-BATSMANSTATS":
					populateBatsmanstats(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
					break;
				case "POPULATE-L3-BOWLERSTATS":
					populateBowlerstats(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), cricketService.getTeams(), match, broadcaster);
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
					populateTeamSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							match, broadcaster);
					break;
				case "POPULATE-TIEID-DOUBLE":
					populateTieIdDouble(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1],CricketFunctions.processAllFixtures(cricketService)
							, match, broadcaster);
					break;
				case "POPULATE-NEXT_TO_BAT":
					populateLtNextToBat(print_writer, valueToProcess.split(",")[0],cricketService.getAllPlayer(),cricketService.getAllStats(), match, broadcaster, config);
					break;
				case "POPULATE-LT-PROJECTED":
					populateProjectedScore(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-L3-PLAYERPROFILEBAT":					
						for(Statistics stats : statistics) {
							if(stats.getPlayerID().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
								stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
								if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[3])){
//									stats = CricketFunctions.updateH2h(stats, head_to_head, match);
//									stats = CricketFunctions.updateMatchData(stats, match);
									populateLTPlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[3],
											valueToProcess.split(",")[4],valueToProcess.split(",")[2],stats,match, broadcaster,cricketService,config,Integer.valueOf(valueToProcess.substring(valueToProcess.lastIndexOf(",") + 1)));
								}
							}
						}
					break;
				case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-FF-PLAYERPROFILEBALL":
					 for(Statistics stats : statistics) {
						 System.out.println(stats.getPlayerID());
						if(stats.getPlayerID().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
							stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[3])){
//								stats = CricketFunctions.updateH2h(stats, head_to_head, match);
//								stats = CricketFunctions.updateMatchData(stats, match);

								populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
										valueToProcess.split(",")[3],valueToProcess.split(",")[4],stats,match, broadcaster,cricketService,config);
							}
						}
					}
					break;
				case "POPULATE-FF-THISSERIES": case "POPULATE-FF-THISSERIES_BALL":
					Statistics statsSeason1 = null, statsSeason2 = null;
					int count = 0;
					if (valueToProcess.split(",")[3].toUpperCase().equalsIgnoreCase("Afghanitancareer")) {
						for (Statistics stats : cricketService.getAllStats()) {
							if(stats.getStatsTypeId() == 11 || stats.getStatsTypeId() == 12) {
								if(stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
									if(stats.getStatsTypeId() == 11) {
										count++;
										statsSeason1 = stats;
									}
									if(stats.getStatsTypeId() == 12) {
										count++;
										statsSeason2 = stats;
									}
									if(statsSeason1 == null && count == 1) {
										stats.setMatches(statsSeason1.getMatches());
										stats.setRuns(statsSeason1.getRuns());
										stats.setBallsFaced(statsSeason1.getBallsFaced());
										stats.setWickets(statsSeason1.getWickets());
										stats.setRunsConceded(statsSeason1.getRunsConceded());
										stats.setBallsBowled(statsSeason1.getBallsBowled());
									}else if(statsSeason1 != null && statsSeason2 != null && count == 2){
										stats.setMatches(statsSeason1.getMatches()+statsSeason2.getMatches());
										stats.setRuns(statsSeason1.getRuns()+statsSeason2.getRuns());
										stats.setBallsFaced(statsSeason1.getBallsFaced()+statsSeason2.getBallsFaced());
										stats.setWickets(statsSeason1.getWickets()+statsSeason2.getWickets());
										stats.setRunsConceded(statsSeason1.getRunsConceded()+statsSeason2.getRunsConceded());
										stats.setBallsBowled(statsSeason1.getBallsBowled()+statsSeason2.getBallsBowled());
									}
									
									if(statsSeason1 == null && count == 1 || statsSeason1 != null && statsSeason2 != null && count == 2) {
										stats = CricketFunctions.updateH2h(stats, head_to_head, match, CricketUtil.FULL);
										stats = CricketFunctions.updateMatchData(stats, match, CricketUtil.FULL);
										populateThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], valueToProcess.split(",")[3],
												CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats)
												,match, broadcaster,stats,cricketService, config);
									}
								}
							}
						}
					}else if(valueToProcess.split(",")[3].toUpperCase().equalsIgnoreCase("Afghanitanseason2")) {
						for (Statistics stats : cricketService.getAllStats()) {
							stats.setStats_type(cricketService.getStatsType(12));
							if (stats.getStatsTypeId() == 12) {
								if (stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
									populateThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], valueToProcess.split(",")[3],
											CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats)
											,match, broadcaster,stats,cricketService, config);
								}
							}
						}
					} else if (valueToProcess.split(",")[3].toUpperCase().equalsIgnoreCase("Afghanitanseason1")) {
						for (Statistics stats : cricketService.getAllStats()) {
							stats.setStats_type(cricketService.getStatsType(11));
							if (stats.getStatsTypeId() == 11) {
								if (stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
									populateThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], valueToProcess.split(",")[3],
											CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats)
											,match, broadcaster,stats,cricketService, config);
								}
							}
						}
					} else if(valueToProcess.split(",")[3].toUpperCase().equalsIgnoreCase("THISSERIES")) {
						populateThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], valueToProcess.split(",")[3],
								CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats)
								,match, broadcaster,null,cricketService, config);
					}
					break;
				case "POPULATE-L3-THISSERIES": case "POPULATE-L3-THISSERIES_BALL":
					Statistics statsSeason1LT = null, statsSeason2LT = null;
					int countLT = 0;
					System.out.println("VALUE "+valueToProcess.split(",")[3]);
					if (valueToProcess.split(",")[3].equalsIgnoreCase("Afghanitancareer")) {
						for (Statistics stats : cricketService.getAllStats()) {
							if(stats.getStatsTypeId() == 11 || stats.getStatsTypeId() == 12) {
								if(stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
									if(stats.getStatsTypeId() == 11) {
										countLT++;
										statsSeason1LT = stats;
									}
									if(stats.getStatsTypeId() == 12) {
										countLT++;
										statsSeason2LT = stats;
									}

									if(statsSeason1LT == null && countLT == 1) {
										stats.setMatches(statsSeason2LT.getMatches());
										stats.setRuns(statsSeason2LT.getRuns());
										stats.setBallsFaced(statsSeason2LT.getBallsFaced());
										stats.setWickets(statsSeason2LT.getWickets());
										stats.setRunsConceded(statsSeason2LT.getRunsConceded());
										stats.setBallsBowled(statsSeason2LT.getBallsBowled());
										stats.setBestScore(statsSeason2LT.getBestScore());
										stats.setBestFigures(statsSeason2LT.getBestFigures());
									}else if(statsSeason1LT != null && statsSeason2LT != null && countLT == 2){
										int bestSeason1 = 0, bestSeason2 = 0, bestFigSeason1wkt = 0, bestFigSeason2wkt = 0, bestFigSeason1Runs = 0,bestFigSeason2Runs = 0;
										boolean season1Notout = false, season2Notout = false;
										stats.setMatches(statsSeason1LT.getMatches()+statsSeason2LT.getMatches());
										stats.setRuns(statsSeason1LT.getRuns()+statsSeason2LT.getRuns());
										stats.setBallsFaced(statsSeason1LT.getBallsFaced()+statsSeason2LT.getBallsFaced());
										stats.setWickets(statsSeason1LT.getWickets()+statsSeason2LT.getWickets());
										stats.setRunsConceded(statsSeason1LT.getRunsConceded()+statsSeason2LT.getRunsConceded());
										stats.setBallsBowled(statsSeason1LT.getBallsBowled()+statsSeason2LT.getBallsBowled());
										if(statsSeason1LT.getBestScore().contains("*")) {
											bestSeason1 = Integer.valueOf(statsSeason1LT.getBestScore().replace("*", ""));
											season1Notout = true;
										}else {
											bestSeason1 = Integer.valueOf(statsSeason1LT.getBestScore());
										}
										if(statsSeason2LT.getBestScore().contains("*")) {
											bestSeason2 = Integer.valueOf(statsSeason2LT.getBestScore().replace("*", ""));
											season2Notout = true;
										}else {
											bestSeason2 = Integer.valueOf(statsSeason2LT.getBestScore());
										}
										
										if(statsSeason1LT.getBestFigures().contains("-")) {
											bestFigSeason1wkt = Integer.valueOf(statsSeason1LT.getBestFigures().split("-")[0]);
											bestFigSeason1Runs = Integer.valueOf(statsSeason1LT.getBestFigures().split("-")[1]);
										}
										if(statsSeason2LT.getBestFigures().contains("-")) {
											bestFigSeason2wkt = Integer.valueOf(statsSeason2LT.getBestFigures().split("-")[0]);
											bestFigSeason2Runs = Integer.valueOf(statsSeason2LT.getBestFigures().split("-")[1]);
										}
										if(bestFigSeason1wkt>bestFigSeason2wkt) {
											stats.setBestFigures((bestFigSeason1wkt+"-"+bestFigSeason1Runs));
										}else {
											stats.setBestFigures((bestFigSeason2wkt+"-"+bestFigSeason2Runs));
										}
										
										if(bestSeason1>bestSeason2) {
											if(season1Notout) {
												stats.setBestScore(bestSeason1+"*");
											}else {
												stats.setBestScore(String.valueOf(bestSeason1));
											}
										}else {
											if(season2Notout) {
												stats.setBestScore(bestSeason2+"*");
											}else {
												stats.setBestScore(String.valueOf(bestSeason2));
											}
										}
									}
									
									if(statsSeason1LT == null && countLT == 1 || statsSeason1LT != null && statsSeason2LT != null && countLT == 2) {
										stats = CricketFunctions.updateH2h(stats, head_to_head, match, CricketUtil.FULL);
										stats = CricketFunctions.updateMatchData(stats, match, CricketUtil.FULL);
										populateLTThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],valueToProcess.split(",")[3],
												CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats)
												,match, broadcaster, stats, cricketService);
									}
								}
							}
						}
					}else if(valueToProcess.split(",")[3].equalsIgnoreCase("Afghanitanseason2")) {
						for (Statistics stats : cricketService.getAllStats()) {
							stats.setStats_type(cricketService.getStatsType(12));
							if (stats.getStatsTypeId() == 12) {
								if (stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
									populateLTThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],valueToProcess.split(",")[3],
											CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats)
											,match, broadcaster, stats, cricketService);
									
								}
							}
						}
					} else if (valueToProcess.split(",")[3].equalsIgnoreCase("Afghanitanseason1")) {
						System.out.println("HELLO");
						for (Statistics stats : cricketService.getAllStats()) {
							stats.setStats_type(cricketService.getStatsType(11));
							if (stats.getStatsTypeId() == 11) {
								if (stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
									populateLTThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],valueToProcess.split(",")[3],
											CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats)
											,match, broadcaster, stats, cricketService);
									
								}
							}
						}
					} else if (valueToProcess.split(",")[3].equalsIgnoreCase("THISSERIES")) {
						populateLTThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],valueToProcess.split(",")[3],
								CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats)
								,match, broadcaster, null, cricketService);
					}
					break;
				case "POPULATE-FF-PLAYINGXI":
					populatePlayingXI(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							match, broadcaster, config);
					break;
				case "POPULATE-FF-SQUAD":
					populateSquad(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							match, broadcaster);
					break;
				case "POPULATE-FF-DOUBLETEAMS":
					populateDoubleteams(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-FF-LANDMARK": case "POPULATE-FF-LANDMARK_BALL":
					populateLandMark(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster, config);
					break;
				case "POPULATE-LT-EQUATION":
					populateLtEquation(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-FF-POSITION_LANDMARK":
					populateFFLandMark(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster, config);
					break;
				
				case "POPULATE-BOWLER_STYLE":
					populateBowlerStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(), cricketService.getTeams(), match, broadcaster);
					break;
				case "POPULATE-BATSMAN_STYLE":
					populateBatsmanStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), cricketService.getAllPlayer(), cricketService.getTeams(),match, broadcaster);
					break;
				case "POPULATE-FF-TEAMS_LOGO":
					populateTeamsLogo(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1],cricketService.getTeams(),match, broadcaster);
					break;
				case "POPULATE-PREVIOUS_SUMMARY":
					
					MatchAllData cricket_matches = new MatchAllData();
					
					for(Fixture fx : cricketService.getFixtures()) {
						if(fx.getMatchnumber() == Integer.valueOf(valueToProcess.split(",")[1])) {
							System.out.println(fx.getMatchfilename());
							if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + fx.getMatchfilename() + ".json").exists()) {
								System.out.println(fx.getMatchfilename());
								
								cricket_matches.setSetup(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + 
										fx.getMatchfilename() + ".json"), Setup.class));
								
								cricket_matches.setMatch(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + 
										fx.getMatchfilename() + ".json"), Match.class));
							}
							if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + fx.getMatchfilename() + ".json").exists()) {
								cricket_matches.setEventFile(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + 
										fx.getMatchfilename() + ".json"), EventFile.class));
							}
//							cricket_matches = CricketFunctions.populateMatchVariables(cricketService, CricketFunctions.readOrSaveMatchFile(CricketUtil.READ,CricketUtil.SETUP + "," + 
//									CricketUtil.MATCH + "," + CricketUtil.EVENT,  cricket_matches,true));
						}
					}
					populatePreviousSummary(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricket_matches,
							cricketService.getFixtures(), match, broadcaster);
					break;
				case "POPULATE-LT-WEATHER":
					populateLtWeather(print_writer, valueToProcess.split(",")[0], cricketService);
					break;
				case "POPULATE-PHASE":
					populateLtPhaseByScore(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),match, broadcaster);
					break;
				case "POPULATE-PHASE-COMPARISON":
					populateLtPhaseByComparison(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),match, broadcaster);
					break;
				case "POPULATE-LT-LINEUP":
					populateLineup(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							cricketService,cricketService.getTeams(),cricketService.getAllPlayer(),match, broadcaster, config);
					break;
				case "POPULATE-MANHATTAN":
					populateManhattan(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-WORM":
					populateWorm(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-RICHEIS":
					populateRicheis(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-DLS":
					populateDuckWorthLewis(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1], match, broadcaster);
					break;
				case "POPULATE-DLS-EQUATION":
					populateDuckWorthLewisEquation(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1], match, broadcaster, session_directoryPath);
					break;
					
					
				case "POPULATE-INFOBAR-IDENT":
					infobar.setIdent_section(valueToProcess.split(",")[1]);
					AnimateInGraphics(print_writer, "RESET");
					infobar = populateInfobarIdent(infobar,print_writer, match, broadcaster);
					TimeUnit.SECONDS.sleep(2);
					//isIdent_on_screen() = true;
					break;
				case "POPULATE-L3-INFOBAR":
					//AnimateInGraphics(print_writer, "RESET");
					//AnimateInGraphics(print_writer, "RESET");
					infobar.setPowerplay_on_screen(false);
					infobar.setMiddle_section(valueToProcess.split(",")[1]);
					infobar.setBottom_right_section(valueToProcess.split(",")[2]);
					
					infobar = populateInfobar(infobar, print_writer, match, cricketService, broadcaster, session_directoryPath);
					infobar.setIdent_section("");
					TimeUnit.SECONDS.sleep(2);
					which_graphics_onscreen = "SCOREBUG";

					break;
				case "POPULATE-DIRECTOR":
					director = valueToProcess;
					populateInfobarDirector(print_writer,valueToProcess,broadcaster);
					break;
				case "POPULATE-INFOBAR_IDENT_DATA":
					if(infobar.isInfobar_on_screen() == true && which_graphics_onscreen == "IDENT") {
						if(infobar.getIdent_section() != null) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoOut START \0");
							TimeUnit.MILLISECONDS.sleep(500);
							infobar.setIdent_section(valueToProcess);
							populateInfobarIdent(infobar,print_writer, match, broadcaster);
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoIn START \0");
						}
					}
					break;
				case "POPULATE-POWERPLAY":
					populateInfobarPowerPlay(print_writer,valueToProcess,match,broadcaster);
					break;
				case "POPULATE-COMMENTATORS":
					infobar.setMiddle_section("COMMENTATORS");
					System.out.println("VALUE : "+valueToProcess);
					commentatorsID = valueToProcess;
					infobar = populateInfobarMiddleSection(infobar, false, print_writer, 
							  match, broadcaster, null, cricketService, session_directoryPath);
					break;
				case "POPULATE-INFOBAR-FREE_TEXT":
					infobarFreeText = "";
					infobarFreeText = valueToProcess;
					infobar.setMiddle_section("INFOBAR_FREE_TEXT");
					  infobar = populateInfobarMiddleSection(infobar, false, print_writer, 
							  match, broadcaster, null, cricketService, session_directoryPath);
					break;
				case "POPULATE-INFOBAR-LAST_X_BALLS":
					lastXBalls = Integer.valueOf(valueToProcess);
					infobar.setMiddle_section("LAST_X_BALLS");
					  infobar = populateInfobarMiddleSection(infobar, false, print_writer, 
							  match, broadcaster, null, cricketService, session_directoryPath);
					break;
				case "POPULATE-INFOBAR-PROMPT":
					for(InfobarStats ibs : cricketService.getInfobarStats() ) {
					  if(ibs.getOrder() == Integer.valueOf(valueToProcess)) {
						  infobar.setMiddle_section("FREE_TEXT");
						  infobar = populateInfobarMiddleSection(infobar, false, print_writer, 
								  match, broadcaster, ibs, cricketService, session_directoryPath);
					  }
					}
					break;	
				case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOM":
					infobar.setMiddle_section(valueToProcess);
				    infobar = populateInfobarMiddleSection(infobar, false, print_writer, match, broadcaster, null, cricketService, session_directoryPath);
					break;
					
				case "POPULATE-INFOBAR-BOTTOMRIGHT": 

					infobar.setBottom_right_section(valueToProcess);
					infobar = populateInfobarBottomRight(infobar, false,print_writer, match, broadcaster);
					break;
				case "POPULATE-INFOBAR-RIGHT":
					infobar.setBottom_right_bottom_section(valueToProcess);
					infobar = populateBottomRightBottom(infobar, false, print_writer, match, broadcaster);
					break;
				}
				return null;
				//return new ObjectMapper().writeValueAsString(this_doad).toString();
		//ScoreBug
		case "ANIMATE-IN-INFOBAR": case "ANIMATE-IN-IDENT": case "ANIMATE-SHRINK_IN": case "ANIMATE-SHRINK_OUT": case "TICKER_LT_IN": case "TICKER_LT_OUT":
		case "ANIMATE-OUT": case "ANIMATE-OUT-BOTTOM": case "ANIMATE-OUT-SECTION4_N_5": case "CLEAR-ALL":  case "ANIMATE-OUT-DIRECTOR":
		case "ANIMATE-IN-MANUAL_GRAPHIC": case "ANIMATE-SIX_DIRECTOR": case "ANIMATE-FOUR_DIRECTOR": case "ANIMATE-FREEHIT_DIRECTOR": case "ANIMATE-WICKET_DIRECTOR":
		case "ANIMATE-IN-POWERPLAY_DIRECTOR": case "ANIMATE-HATTRICK_DIRECTOR": case "ANIMATE-IN-PLOTTER_ICC":case "ANIMATE-OUT-PLOTTER":
				
		//FF
		case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BATSMAN_VS_ALLBOWLERS":
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PLAYERPROFILE":
		case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD":
		case "ANIMATE-IN-SQUAD": case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-LANDMARK_BALL": case "ANIMATE-IN-POINTSTABLE":
		case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE":
		case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE":
		case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-WORM": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-FF_POINTERS":
		case "ANIMATE-IN-FIXTURES": case "ANIMATE-IN-FIXTURES_TEAM": case "ANIMATE-IN-TEAM_SQUAD": case "ANIMATE-IN-INN_BUILDER": case "ANIMATE-IN-RICHEIS":
		case "ANIMATE-IN-MOST": case "ANIMATE-IN-LEADERBOARD_MOST": case "ANIMATE-IN-FFTHISSERIES_BALL": case "ANIMATE-IN-PLAYOFFS":
		
		//Bug
		case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET":
		case "ANIMATE-IN-BUG-TOSS": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-MULTI_PARTNERSHIP": case "ANIMATE-IN-BAT-POPUP":
		case "ANIMATE-IN-BOWL-POPUP":
			
		//LT
		case "ANIMATE-IN-LTPOINTSTABLE": case "ANIMATE-IN-PHASE": case "ANIMATE-IN-LINEUP": case "ANIMATE-IN-IMPACT": case "ANIMATE-IN-PHASE-COMPARISON":
		case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED":
		case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-LTPLAYERPROFILEBAT":
		case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": 
		case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY": case "ANIMATE-IN-EQUATION": 
		case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH": case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-POINTERS":
		case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-GENERIC": case "ANIMATE-IN-L3MATCH_PROMO": case "ANIMATE-IN-HOWOUT_QUICK": 
		case "ANIMATE-OUT-POWERPLAY":  case "ANIMATE-IN-THISSERIES": case "ANIMATE-IN-THISSERIES_BALL": case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-DLS": 
		case "ANIMATE-IN-DLS-EQUATION": case "ANIMATE-IN-WEATHER":  
		
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-BUG":  case "ANIMATE-IN-BUG-TOSS": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-MULTI_PARTNERSHIP":
			case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET": case "ANIMATE-IN-BUG_POWERPLAY":
			case "ANIMATE-IN-BAT-POPUP": case "ANIMATE-IN-BOWL-POPUP":
				
				if(!infobar.isInfobar_down()) {
					if(infobar.isInfobar_on_screen() == true && which_graphics_onscreen == "SCOREBUG") {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_LT_In START \0");
						TimeUnit.MILLISECONDS.sleep(200);
						//TimeUnit.SECONDS.sleep(2);
					}
				}
				break;
			
			case "CLEAR-ALL": case "ANIMATE-IN-MANUAL_GRAPHIC": case "ANIMATE-IN-PLAYOFFS":
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PLAYERPROFILE":
			case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI":
			case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-LANDMARK_BALL":  case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN":
			case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-MOSTRUNS": 
			case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM":
			case "ANIMATE-IN-FF_POINTERS": case "ANIMATE-IN-FIXTURES": case "ANIMATE-IN-FIXTURES_TEAM":
			case "ANIMATE-IN-TEAM_SQUAD": case "ANIMATE-IN-INN_BUILDER": case "ANIMATE-IN-RICHEIS": case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-LINEUP":
			case "ANIMATE-IN-MOST": case "ANIMATE-IN-LEADERBOARD_MOST": case "ANIMATE-IN-FFTHISSERIES_BALL": case "ANIMATE-IN-COMPARISION":
				if(!infobar.isInfobar_down()) {
					if(infobar.isInfobar_on_screen() == true && which_graphics_onscreen == "SCOREBUG") {
						AnimateInGraphics(print_writer, "FF_IN");
						TimeUnit.MILLISECONDS.sleep(200);
						//TimeUnit.SECONDS.sleep(2);
					}
				}
				
				break;
			
			case "ANIMATE-IN-LTPOINTSTABLE": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BATSMAN_VS_ALLBOWLERS":
			case "ANIMATE-IN-IMPACT": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF":
			case "ANIMATE-IN-POINTERS": case "ANIMATE-IN-PHASE": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-PHASE-COMPARISON":
			case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED":
			case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY":  case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-LTPLAYERPROFILEBAT": case "ANIMATE-IN-FALLOFWICKET":
			case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-SQUAD": case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": 
			case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS":
			case "ANIMATE-IN-LTPOWERPLAY": case "ANIMATE-IN-EQUATION":  case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH":
			case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-IN-THISSERIES": case "ANIMATE-IN-THISSERIES_BALL": case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-GENERIC": 
			case "ANIMATE-IN-L3MATCH_PROMO": case "ANIMATE-IN-DLS": case "ANIMATE-IN-DLS-EQUATION": case "ANIMATE-IN-WEATHER":
				if(!isTickerShrinked) {
					if(infobar.isInfobar_on_screen() == true && which_graphics_onscreen == "SCOREBUG") {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MiniIn START \0");
						TimeUnit.MILLISECONDS.sleep(200);
						//TimeUnit.SECONDS.sleep(2);
					}
				}
				
				break;
				
			}
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-HATTRICK_DIRECTOR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*HattrickIn START \0");
				break;
			case "ANIMATE-IN-POWERPLAY_DIRECTOR":
				if(powerPlayDirectorOnScreen) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayOut START \0");
					powerPlayDirectorOnScreen = false;
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START \0");
					powerPlayDirectorOnScreen = true;
				}
				break;
			case "ANIMATE-SIX_DIRECTOR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SixIn START \0");
				break;
			case "ANIMATE-FOUR_DIRECTOR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FoursIn START \0");
				break;
			case "ANIMATE-FREEHIT_DIRECTOR":
				if(free_hit_on_screen) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHit_Out START \0");
					free_hit_on_screen = false;
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHit START \0");
					free_hit_on_screen = true;
				}
				
				break;
			case "ANIMATE-WICKET_DIRECTOR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*WicketsIn START \0");
				break;
			case "TICKER_LT_IN": 
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_LT_In START \0");
				infobar.setInfobar_down(true);
				break;
			case "TICKER_LT_OUT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_LT_Out START \0");
				infobar.setInfobar_down(false);
				break;
			case "ANIMATE-SHRINK_IN":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MiniIn START \0");
				isTickerShrinked = true;
				break;
			case "ANIMATE-SHRINK_OUT":
				if(isTickerShrinked) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MiniOut START \0");
					isTickerShrinked = false;
				}
				break;
			case "ANIMATE-IN-PLOTTER_ICC":
				System.out.println("STATUS WHILE PLOT IN "+status);
				AnimateInGraphics(print_writer, "PLOTTER_ICC");
				which_graphics_onscreen = "PLOTTER_ICC";
				break;	
			
			case "ANIMATE-IN-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-INFOBAR");
					AnimateInGraphics(print_writer, "IDENT");
					which_graphics_onscreen = "IDENT";
					infobar.setInfobar_on_screen(true);
				}else {
					AnimateInGraphics(print_writer, "IN");
					AnimateInGraphics(print_writer, "IDENT");
					which_graphics_onscreen = "IDENT";
					infobar.setInfobar_on_screen(true);
				}
				
				break;
			case "ANIMATE-OUT-PLOTTER":
				if(which_graphics_onscreen.equalsIgnoreCase("PLOTTER_ICC")) {
					AnimateOutGraphics(print_writer, "PLOTTER_ICC");
					which_graphics_onscreen = "SCOREBUG";
				}
				break;
			case "ANIMATE-IN-PLAYOFFS":
				AnimateInGraphics(print_writer, "PLAYOFF");
				which_graphics_onscreen = "PLAYOFF";
				break;
			case "ANIMATE-MINI-BATSMAN_VS_ALLBOWLERS":
				AnimateInGraphics(print_writer, "MINI-BATSMAN_VS_ALLBOWLERS");
				which_graphics_onscreen = "MINI-BATSMAN_VS_ALLBOWLERS";
				break;
			case "ANIMATE-IN-DLS":
				AnimateInGraphics(print_writer, "DLS_TARGET");
				which_graphics_onscreen = "DLS_TARGET";
				break;
			case "ANIMATE-IN-DLS-EQUATION":
				AnimateInGraphics(print_writer, "DLS_EQUATION");
				which_graphics_onscreen = "DLS_EQUATION";
				break;
			case "ANIMATE-IN-BATGRIFF":
				AnimateInGraphics(print_writer, "BATGRIFF");
				which_graphics_onscreen = "BATGRIFF";
				break;
			case "ANIMATE-IN-BALLGRIFF":
				AnimateInGraphics(print_writer, "BALLGRIFF");
				which_graphics_onscreen = "BALLGRIFF";
				break;
			case "ANIMATE-IN-BUG_POWERPLAY":
				AnimateInGraphics(print_writer, "BUG_POWERPLAY");
				which_graphics_onscreen = "BUG_POWERPLAY";
				break;
			case "ANIMATE-MINI-BATTINGCARD":
				AnimateInGraphics(print_writer, "MINI-SCORECARD");
				which_graphics_onscreen = "MINI-SCORECARD";
				break;
			case "ANIMATE-IN-LANDMARK_BALL":
				AnimateInGraphics(print_writer, "BALL_LANDMARK");
				which_graphics_onscreen = "BALL_LANDMARK";
				break;
			case "ANIMATE-MINI-BOWLINGCARD":
				AnimateInGraphics(print_writer, "MINI-BOWLINGCARD");
				which_graphics_onscreen = "MINI-BOWLINGCARD";
				break;
			case "ANIMATE-IN-SCORECARD":
				if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					
				}else if(which_graphics_onscreen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut START \0");
					TimeUnit.MILLISECONDS.sleep(400);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					AnimateInGraphics(print_writer, "SCORECARD");
				}
				print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$Header$BatTeamLogo*ACTIVE SET 0 \0");
				which_graphics_onscreen = "BATBALLSUMMARY_SCORECARD";
				break;
			case "ANIMATE-IN-BOWLINGCARD":
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					
				}else if(which_graphics_onscreen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut START \0");
					TimeUnit.MILLISECONDS.sleep(400);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					AnimateInGraphics(print_writer, "BOWLINGCARD");
				}
				which_graphics_onscreen = "BATBALLSUMMARY_BOWLINGCARD";
				print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$Header$BatTeamLogo*ACTIVE SET 0 \0");
				break;
			
			case "ANIMATE-IN-PARTNERSHIP":
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
					
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
					
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "1" + "\0");
					AnimateInGraphics(print_writer, "PARTNERSHIP");
				}
				print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$Header$BatTeamLogo*ACTIVE SET 1 \0");
				which_graphics_onscreen = "PARTNERSHIP";
				break;
			case "ANIMATE-IN-MATCHSUMARRY":
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					
				}else if(which_graphics_onscreen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut START \0");
					TimeUnit.MILLISECONDS.sleep(400);
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					AnimateInGraphics(print_writer, "MATCHSUMMARY");
				}
				print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$Header$BatTeamLogo*ACTIVE SET 0 \0");
				which_graphics_onscreen = "BATBALLSUMMARY_MATCHSUMMARY";
				break;
			case "ANIMATE-IN-POINTSTABLE":
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
					
				}else if(which_graphics_onscreen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut START \0");
					TimeUnit.MILLISECONDS.sleep(400);
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
					
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
					AnimateInGraphics(print_writer, "POINTSTABLE");
				}
				print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$Header$BatTeamLogo*ACTIVE SET 0 \0");
				which_graphics_onscreen = "POINTSTABLE";
				break;
			
			case "ANIMATE-IN-PREVIOUS_SUMMARY":
				print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$Header$BatTeamLogo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "0" + "\0");
				AnimateInGraphics(print_writer, "PREVIOUS_SUMMARY");
				which_graphics_onscreen = "PREVIOUS_SUMMARY";
				break;
			case "ANIMATE-IN-BOWL-POPUP":
				AnimateInGraphics(print_writer, "BOWL-POPUP");
				which_graphics_onscreen = "BOWL-POPUP";
				break;
			case "ANIMATE-IN-BAT-POPUP":
				AnimateInGraphics(print_writer, "BAT-POPUP");
				which_graphics_onscreen = "BAT-POPUP";
				break;
			case "ANIMATE-IN-BUG-DISMISSAL":
				AnimateInGraphics(print_writer, "BUG-DISMISSAL");
				which_graphics_onscreen = "BUG-DISMISSAL";
				break;
			case "ANIMATE-IN-BUG":
				AnimateInGraphics(print_writer, "BUG");
				which_graphics_onscreen = "BUG";
				break;
			case "ANIMATE-IN-BUG-TOSS":
				AnimateInGraphics(print_writer, "BUG-TOSS");
				which_graphics_onscreen = "BUG-TOSS";
				break;
			case "ANIMATE-IN-BUG_HIGHLIGHT":
				AnimateInGraphics(print_writer, "BUG_HIGHLIGHT");
				which_graphics_onscreen = "BUG_HIGHLIGHT";
				break;
			case "ANIMATE-IN-BUGPARTNERSHIP":
				AnimateInGraphics(print_writer, "BUG_PARTNERSHIP");
				which_graphics_onscreen = "BUG_PARTNERSHIP";
				break;
			case "ANIMATE-IN-MULTI_PARTNERSHIP":
				AnimateInGraphics(print_writer, "MULTI_PARTNERSHIP");
				which_graphics_onscreen = "MULTI_PARTNERSHIP";
				break;
			case "ANIMATE-IN-SQUAD":
				AnimateInGraphics(print_writer, "SQUAD");
				which_graphics_onscreen = "SQUAD";
				break;
			case "ANIMATE-IN-BUG-BOWLER":
				AnimateInGraphics(print_writer, "BUGBOWLER");
				which_graphics_onscreen = "BUGBOWLER";
				break;
			case "ANIMATE-IN-BUG-DB":
				AnimateInGraphics(print_writer, "BUG-DB");
				which_graphics_onscreen = "BUG-DB";
				break;
			case "ANIMATE-IN-POINTERS":
				AnimateInGraphics(print_writer, "LT_POINTERS");
				which_graphics_onscreen = "LT_POINTERS";
				break;
			case "ANIMATE-IN-FF_POINTERS":
				AnimateInGraphics(print_writer, "FF_POINTERS");
				which_graphics_onscreen = "FF_POINTERS";
				break;
			case "ANIMATE-IN-FIXTURES":
				AnimateInGraphics(print_writer, "FF_FIXTURES");
				which_graphics_onscreen = "FF_FIXTURES";
				break;
			case "ANIMATE-IN-FIXTURES_TEAM":
				AnimateInGraphics(print_writer, "FIXTURES_TEAM");
				which_graphics_onscreen = "FIXTURES_TEAM";
				break;
			case "ANIMATE-IN-TEAM_SQUAD":
				AnimateInGraphics(print_writer, "TEAM_SQUAD");
				which_graphics_onscreen = "TEAM_SQUAD";
				break;
			case "ANIMATE-IN-INN_BUILDER":
				AnimateInGraphics(print_writer, "INN_BUILDER");
				which_graphics_onscreen = "INN_BUILDER";
				break;
			case "ANIMATE-IN-MANUAL_GRAPHIC":
				AnimateInGraphics(print_writer, "MANUAL");
				which_graphics_onscreen = "MANUAL";
				break;
			case "ANIMATE-IN-TIEID-DOUBLE":
				AnimateInGraphics(print_writer, "TIEID-DOUBLE");
				which_graphics_onscreen = "TIEID-DOUBLE";
				break;
			case "ANIMATE-IN-HOWOUT":
				AnimateInGraphics(print_writer, "HOWOUT");
				which_graphics_onscreen = "HOWOUT";
				break;
			case "ANIMATE-IN-HOWOUT_QUICK":
				AnimateInGraphics(print_writer, "QUICK_HOWOUT");
				which_graphics_onscreen = "QUICK_HOWOUT";
				break;
			case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER":
				AnimateInGraphics(print_writer, "HOWOUT_WITHOUT");
				which_graphics_onscreen = "HOWOUT_WITHOUT";
				break;
			case "ANIMATE-IN-NAMESUPER":
				AnimateInGraphics(print_writer, "NAMESUPER");
				which_graphics_onscreen = "NAMESUPER";
				break;
			case "ANIMATE-IN-NAMESUPER-PLAYER":
				AnimateInGraphics(print_writer, "NAMESUPER-PLAYER");
				which_graphics_onscreen = "NAMESUPER-PLAYER";
				break;
			case "ANIMATE-IN-FALLOFWICKET":
				AnimateInGraphics(print_writer, "FALLOFWICKET");
				which_graphics_onscreen = "FALLOFWICKET";
				break;
			case "ANIMATE-IN-LEADERBOARD":
				AnimateInGraphics(print_writer, "LEADERBOARD");
				which_graphics_onscreen = "LEADERBOARD";
				break;
			case "ANIMATE-IN-L3MATCHID":
				AnimateInGraphics(print_writer, "L3MATCHID");
				which_graphics_onscreen = "L3MATCHID";
				break;
			case "ANIMATE-IN-MATCH_PROMO":
				AnimateInGraphics(print_writer, "MATCH_PROMO");
				which_graphics_onscreen = "MATCH_PROMO";
				break;
			case "ANIMATE-IN-L3MATCH_PROMO":
				AnimateInGraphics(print_writer, "LTMATCH_PROMO");
				which_graphics_onscreen = "LTMATCH_PROMO";
				break;
			case "ANIMATE-IN-TARGET":
				AnimateInGraphics(print_writer, "TARGET");
				which_graphics_onscreen = "TARGET";
				break;
			case "ANIMATE-IN-BUGTARGET":
				AnimateInGraphics(print_writer, "BUGTARGET");
				which_graphics_onscreen = "BUGTARGET";
				break;
			case "ANIMATE-IN-COMPARISION":
				AnimateInGraphics(print_writer, "COMPARISION");
				which_graphics_onscreen = "COMPARISION";
				break;
			case "ANIMATE-IN-LTPARTNERSHIP":
				AnimateInGraphics(print_writer, "LTPARTNERSHIP");
				which_graphics_onscreen = "LTPARTNERSHIP";
				break;
			case "ANIMATE-IN-SPLIT":
				AnimateInGraphics(print_writer, "SPLIT");
				which_graphics_onscreen = "SPLIT";
				break;
			case "ANIMATE-IN-BATSMANSTATS":
				AnimateInGraphics(print_writer, "BATSMANSTATS");
				which_graphics_onscreen = "BATSMANSTATS";
				break;
			case "ANIMATE-IN-BOWLERSTATS":
				AnimateInGraphics(print_writer, "BOWLERSTATS");
				which_graphics_onscreen = "BOWLERSTATS";
				break;
			case "ANIMATE-IN-BOWLERSUMMARY":
				AnimateInGraphics(print_writer, "BOWLERSUMMARY");
				which_graphics_onscreen = "BOWLERSUMMARY";
				break;
			case "ANIMATE-IN-PLAYERSUMMARY":
				AnimateInGraphics(print_writer, "PLAYERSUMMARY");
				which_graphics_onscreen = "PLAYERSUMMARY";
				break;
			case "ANIMATE-IN-TEAMSUMMARY":
				AnimateInGraphics(print_writer, "TEAMSUMMARY");
				which_graphics_onscreen = "TEAMSUMMARY";
				break;
			case "ANIMATE-IN-NEXT_TO_BAT":
				AnimateInGraphics(print_writer, "NEXTTOBAT");
				which_graphics_onscreen = "NEXTTOBAT";
				break;
			case "ANIMATE-IN-PROJECTED":
				AnimateInGraphics(print_writer, "PROJECTED");
				which_graphics_onscreen = "PROJECTED";
				break;
			case "ANIMATE-IN-BOWLERDETAILS":
				AnimateInGraphics(print_writer, "BOWLERDETAILS");
				which_graphics_onscreen = "BOWLERDETAILS";
				break;
			case "ANIMATE-IN-LTPOWERPLAY":
				AnimateInGraphics(print_writer, "LTPOWERPLAY");
				which_graphics_onscreen = "LTPOWERPLAY";
				break;
			case "ANIMATE-IN-MATCHID":
				AnimateInGraphics(print_writer, "MATCHID");
				which_graphics_onscreen = "MATCHID";
				break;
			case "ANIMATE-IN-L3PLAYERPROFILE":
				AnimateInGraphics(print_writer, "L3PLAYERPROFILE");
				which_graphics_onscreen = "L3PLAYERPROFILE";
				break;
			case "ANIMATE-IN-LTPLAYERPROFILEBAT":
				AnimateInGraphics(print_writer, "LTPLAYERPROFILEBAT");
				which_graphics_onscreen = "LTPLAYERPROFILEBAT";
				break;
			case "ANIMATE-IN-FFTHISSERIES":
				AnimateInGraphics(print_writer, "FF_THIS-SERIES");
				which_graphics_onscreen = "FF_THIS-SERIES";
				break;
			case "ANIMATE-IN-FFTHISSERIES_BALL":
				AnimateInGraphics(print_writer, "FFTHISSERIES_BALL");
				which_graphics_onscreen = "FFTHISSERIES_BALL";
				break;
			case "ANIMATE-IN-THISSERIES":
				AnimateInGraphics(print_writer, "LT_THIS-SERIES");
				which_graphics_onscreen = "LT_THIS-SERIES";
				break;
			case "ANIMATE-IN-THISSERIES_BALL":
				AnimateInGraphics(print_writer, "LT_THISSERIES_BALL");
				which_graphics_onscreen = "LT_THISSERIES_BALL";
				break;
			case "ANIMATE-IN-PLAYERPROFILE":
				AnimateInGraphics(print_writer, "FFPLAYERPROFILE");
				which_graphics_onscreen = "FFPLAYERPROFILE";
				break;
			case "ANIMATE-IN-PLAYERPROFILEBALL":
				AnimateInGraphics(print_writer, "PLAYERPROFILEBALL");
				which_graphics_onscreen = "PLAYERPROFILEBALL";
				break;
			case "ANIMATE-IN-PLAYINGXI":
				AnimateInGraphics(print_writer, "TEAMLINEUP");
				which_graphics_onscreen = "TEAMLINEUP";
				break;
			case "ANIMATE-IN-DOUBLETEAMS":
				AnimateInGraphics(print_writer, "DOUBLETEAMS");
				which_graphics_onscreen = "DOUBLETEAMS";
				break;
			case "ANIMATE-IN-LANDMARK":
				AnimateInGraphics(print_writer, "LANDMARK");
				which_graphics_onscreen = "LANDMARK";
				break;
			case "ANIMATE-IN-EQUATION":
				AnimateInGraphics(print_writer, "EQUATION");
				which_graphics_onscreen = "EQUATION";
				break;
			case "ANIMATE-IN-POSITION_LANDMARK":
				AnimateInGraphics(print_writer, "POSITION_LANDMARK");
				which_graphics_onscreen = "POSITION_LANDMARK";
				break;
			case "ANIMATE-IN-BATSMAN_THIS_MATCH":
				AnimateInGraphics(print_writer, "BATSMAN_THIS_MATCH");
				which_graphics_onscreen = "BATSMAN_THIS_MATCH";
				break;
			case "ANIMATE-IN-BOWLER_THIS_MATCH":
				AnimateInGraphics(print_writer, "BOWLER_THIS_MATCH");
				which_graphics_onscreen = "BOWLER_THIS_MATCH";
				break;
			case "ANIMATE-IN-LTPOINTSTABLE":
				AnimateInGraphics(print_writer, "LTPOINTSTABLE");
				which_graphics_onscreen = "LTPOINTSTABLE";
				break;
			case "ANIMATE-IN-BOWLER_STYLE":
				AnimateInGraphics(print_writer, "BOWLER_STYLE");
				which_graphics_onscreen = "BOWLER_STYLE";
				break;
			case "ANIMATE-IN-BATSMAN_STYLE":
				AnimateInGraphics(print_writer, "BATSMAN_STYLE");
				which_graphics_onscreen = "BATSMAN_STYLE";
				break;
			case "ANIMATE-IN-TEAMS_LOGO":
				AnimateInGraphics(print_writer, "TEAMS_LOGO");
				which_graphics_onscreen = "TEAMS_LOGO";
				break;
			case "ANIMATE-IN-MANHATTAN":
				AnimateInGraphics(print_writer, "MANHATTAN");
				which_graphics_onscreen = "MANHATTAN";
				break;
			case "ANIMATE-IN-WORM":
				AnimateInGraphics(print_writer, "WORM");
				which_graphics_onscreen = "WORM";
				break;
			case "ANIMATE-IN-RICHEIS":
				AnimateInGraphics(print_writer, "RICHEIS");
				which_graphics_onscreen = "RICHEIS";
				break;
			case "ANIMATE-IN-WEATHER":
				AnimateInGraphics(print_writer, "WEATHER");
				which_graphics_onscreen = "WEATHER";
				break;
			case "ANIMATE-IN-IMPACT":
				AnimateInGraphics(print_writer, "IMPACT");
				which_graphics_onscreen = "IMPACT";
				break;
			case "ANIMATE-IN-PHASE-COMPARISON":
				AnimateInGraphics(print_writer, "PHASE-COMPARISON");
				which_graphics_onscreen = "PHASE-COMPARISON";
				break;	
			case "ANIMATE-IN-PHASE":
				AnimateInGraphics(print_writer, "PHASE_BY_SCORE");
				which_graphics_onscreen = "PHASE_BY_SCORE";
				break;
			case "ANIMATE-IN-LINEUP":
				AnimateInGraphics(print_writer, "LINEUP");
				which_graphics_onscreen = "LINEUP";
				break;
			case "ANIMATE-IN-LEADERBOARD_MOST":
				AnimateInGraphics(print_writer, "MOST_LEADERBOARD");
				which_graphics_onscreen = "MOST_LEADERBOARD";
				break;
			case "ANIMATE-IN-MOST":
				AnimateInGraphics(print_writer, "MOST");
				which_graphics_onscreen = "MOST";
				break;
			case "ANIMATE-IN-HIGHESTSCORE":
				AnimateInGraphics(print_writer, "HIGHESTSCORE");
				which_graphics_onscreen = "HIGHESTSCORE";
				break;
			/*case "ANIMATE-IN-INFOBAR":
				AnimateInGraphics(print_writer, "SCOREBUG");
				if(getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
					which_graphics_onscreen = "SCOREBUG";
				}
				is_Infobar_on_Screen = true;
				break;*/
			case "ANIMATE-IN-INFOBAR":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-IDENT");
					//AnimateInGraphics(print_writer, "IN");
					AnimateInGraphics(print_writer, "SCOREBUG");
					if(infobar.isPowerplay_on_screen() == true) {
			        	 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn SHOW 0.500 \0");
					}
//						if(infobar.isPowerplay_on_screen() == true) {
//			        	 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn SHOW  \0");
//					}
					which_graphics_onscreen = "SCOREBUG";
					infobar.setInfobar_on_screen(true);
					
				}else {
					
					AnimateInGraphics(print_writer, "SCOREBUG");
					if(infobar.isPowerplay_on_screen() == true) {
			        	 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn SHOW 0.500 \0");
					}
					infobar.setInfobar_on_screen(true);
					which_graphics_onscreen = "SCOREBUG";
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
               
               print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/AfghanitanT20_2024/ScoreBug\0");
           	
               print_writer.println("-1 RENDERER*FRONT_LAYER INITIALIZE\0");
               print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE\0");
               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 0\0");
               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0\0");
               
               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 1\0");
               
               print_writer.println("-1 SCENE CLEANUP\0");
               print_writer.println("-1 IMAGE CLEANUP\0");
               print_writer.println("-1 GEOM CLEANUP\0");
               print_writer.println("-1 FONT CLEANUP\0");
               
               which_graphics_onscreen = "";
               infobar = new Infobar();
               infobar.setInfobar_on_screen(false);
               this.status = "";
               
				break;
			case "ANIMATE-OUT-SECTION4_N_5":
				//resetAnimation(print_writer, broadcaster.toUpperCase(), which_director_on_infobar.toUpperCase());
				AnimateOutGraphics(print_writer, "RIGHT");
				break;
			case "ANIMATE-OUT-BOTTOM":
				//resetAnimation(print_writer, broadcaster.toUpperCase(), info_bar_bottom.toUpperCase());
				break;
			case "ANIMATE-OUT":
				System.out.println("WHICH GFX : "+which_graphics_onscreen);
				switch(which_graphics_onscreen) {
				case "PLOTTER_ICC":
					AnimateOutGraphics(print_writer, "PLOTTER_ICC");
					which_graphics_onscreen = "SCOREBUG";
					break;
				case "BALL_LANDMARK":
					AnimateOutGraphics(print_writer, "BALL_LANDMARK");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "MINI-BATSMAN_VS_ALLBOWLERS":
					AnimateOutGraphics(print_writer, "MINI-BATSMAN_VS_ALLBOWLERS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"MINI");
					break;
				case "PLAYOFF":
					AnimateOutGraphics(print_writer, "PLAYOFF");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "DLS_TARGET":
					AnimateOutGraphics(print_writer, "DLS_TARGET");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "DLS_EQUATION":
					AnimateOutGraphics(print_writer, "DLS_EQUATION");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BALLGRIFF":
					AnimateOutGraphics(print_writer, "BALLGRIFF");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BATGRIFF":
					AnimateOutGraphics(print_writer, "BATGRIFF");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BUG_POWERPLAY":
					AnimateOutGraphics(print_writer, "BUG_POWERPLAY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "MINI-SCORECARD":
					AnimateOutGraphics(print_writer, "MINI-SCORECARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"MINI");
					break;
				case "MINI-BOWLINGCARD":
					AnimateOutGraphics(print_writer, "MINI-BOWLINGCARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"MINI");
					break;
				case "BATBALLSUMMARY_SCORECARD":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_SCORECARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "BATBALLSUMMARY_BOWLINGCARD":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_BOWLINGCARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "BATBALLSUMMARY_MATCHSUMMARY":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_MATCHSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "PREVIOUS_SUMMARY":
					AnimateOutGraphics(print_writer, "PREVIOUS_SUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "BUG-TOSS":
					AnimateOutGraphics(print_writer, "BUG-TOSS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "BUG_HIGHLIGHT":
					AnimateOutGraphics(print_writer, "BUG_HIGHLIGHT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "BUG_PARTNERSHIP":
					AnimateOutGraphics(print_writer, "BUG_PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "MULTI_PARTNERSHIP":
					AnimateOutGraphics(print_writer, "MULTI_PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "BOWL-POPUP":
					AnimateOutGraphics(print_writer, "BOWL-POPUP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "BAT-POPUP":
					AnimateOutGraphics(print_writer, "BAT-POPUP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "BUG-DISMISSAL":
					AnimateOutGraphics(print_writer, "BUG-DISMISSAL");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "BUG":
					AnimateOutGraphics(print_writer, "BUG");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "MANUAL":
					AnimateOutGraphics(print_writer, "MANUAL");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "SQUAD":
					AnimateOutGraphics(print_writer, "SQUAD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "BUGBOWLER":
					AnimateOutGraphics(print_writer, "BUGBOWLER");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "BUG-DB":
					AnimateOutGraphics(print_writer, "BUG-DB");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "LT_POINTERS":
					AnimateOutGraphics(print_writer, "LT_POINTERS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "FF_POINTERS":
					AnimateOutGraphics(print_writer, "FF_POINTERS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "FF_FIXTURES":
					AnimateOutGraphics(print_writer, "FF_FIXTURES");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "FIXTURES_TEAM":
					AnimateOutGraphics(print_writer, "FIXTURES_TEAM");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "TEAM_SQUAD":
					AnimateOutGraphics(print_writer, "TEAM_SQUAD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "INN_BUILDER":
					AnimateOutGraphics(print_writer, "INN_BUILDER");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "TIEID-DOUBLE":
					AnimateOutGraphics(print_writer, "TIEID-DOUBLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "HOWOUT":
					AnimateOutGraphics(print_writer, "HOWOUT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "QUICK_HOWOUT":
					AnimateOutGraphics(print_writer, "QUICK_HOWOUT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "HOWOUT_WITHOUT":
					AnimateOutGraphics(print_writer, "HOWOUT_WITHOUT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "NAMESUPER":
					AnimateOutGraphics(print_writer, "NAMESUPER");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "NAMESUPER-PLAYER":
					AnimateOutGraphics(print_writer, "NAMESUPER-PLAYER");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "SCOREBUG":
					AnimateOutGraphics(print_writer, "SCOREBUG");
					AnimateInGraphics(print_writer, "RESET");
					which_graphics_onscreen = "";
					infobar = new Infobar();
					infobar.setInfobar_on_screen(false);
					
					break;
				case"IDENT":
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-IDENT");
					which_graphics_onscreen = "";
					which_graphics_onscreen = "";
					infobar.setInfobar_on_screen(false);
					break;
				case "FALLOFWICKET":
					AnimateOutGraphics(print_writer, "FALLOFWICKET");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "LEADERBOARD":
					AnimateOutGraphics(print_writer, "LEADERBOARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "L3MATCHID":
					AnimateOutGraphics(print_writer, "L3MATCHID");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "MATCH_PROMO":
					AnimateOutGraphics(print_writer, "MATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "LTMATCH_PROMO":
					AnimateOutGraphics(print_writer, "LTMATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "TARGET":
					AnimateOutGraphics(print_writer, "TARGET");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BUGTARGET":
					AnimateOutGraphics(print_writer, "BUGTARGET");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "SCOREBUG";
					resetInfobarAnimation(print_writer,broadcaster,"BUG");
					break;
				case "COMPARISION":
					AnimateOutGraphics(print_writer, "COMPARISION");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "LTPARTNERSHIP":
					AnimateOutGraphics(print_writer, "LTPARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "PARTNERSHIP":
					AnimateOutGraphics(print_writer, "PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "SPLIT":
					AnimateOutGraphics(print_writer, "SPLIT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BATSMANSTATS":
					AnimateOutGraphics(print_writer, "BATSMANSTATS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BOWLERSTATS":
					AnimateOutGraphics(print_writer, "BOWLERSTATS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BOWLERSUMMARY":
					AnimateOutGraphics(print_writer, "BOWLERSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "PLAYERSUMMARY":
					AnimateOutGraphics(print_writer, "PLAYERSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "TEAMSUMMARY":
					AnimateOutGraphics(print_writer, "TEAMSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "NEXTTOBAT":
					AnimateOutGraphics(print_writer, "NEXTTOBAT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "PROJECTED":
					AnimateOutGraphics(print_writer, "PROJECTED");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BOWLERDETAILS":
					AnimateOutGraphics(print_writer, "BOWLERDETAILS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "LTPOWERPLAY":
					AnimateOutGraphics(print_writer, "LTPOWERPLAY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "MATCHID":
					AnimateOutGraphics(print_writer, "MATCHID");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "L3PLAYERPROFILE":
					AnimateOutGraphics(print_writer, "L3PLAYERPROFILE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "LTPLAYERPROFILEBAT":
					AnimateOutGraphics(print_writer, "LTPLAYERPROFILEBAT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "FFPLAYERPROFILE":
					AnimateOutGraphics(print_writer, "FFPLAYERPROFILE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "PLAYERPROFILEBALL":
					AnimateOutGraphics(print_writer, "PLAYERPROFILEBALL");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "LT_THIS-SERIES":
					AnimateOutGraphics(print_writer, "LT_THIS-SERIES");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "LT_THISSERIES_BALL":
					AnimateOutGraphics(print_writer, "LT_THISSERIES_BALL");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;	
				case "FF_THIS-SERIES":
					AnimateOutGraphics(print_writer, "FF_THIS-SERIES");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "FFTHISSERIES_BALL":
					AnimateOutGraphics(print_writer, "FFTHISSERIES_BALL");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "TEAMLINEUP":
					AnimateOutGraphics(print_writer, "TEAMLINEUP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "MOST_LEADERBOARD":
					AnimateOutGraphics(print_writer, "MOST_LEADERBOARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "MOST":
					AnimateOutGraphics(print_writer, "MOST");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "DOUBLETEAMS":
					AnimateOutGraphics(print_writer, "DOUBLETEAMS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "LANDMARK":
					AnimateOutGraphics(print_writer, "LANDMARK");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "EQUATION":
					AnimateOutGraphics(print_writer, "EQUATION");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "POSITION_LANDMARK":
					AnimateOutGraphics(print_writer, "POSITION_LANDMARK");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "BATSMAN_THIS_MATCH":
					AnimateOutGraphics(print_writer, "BATSMAN_THIS_MATCH");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BOWLER_THIS_MATCH":
					AnimateOutGraphics(print_writer, "BOWLER_THIS_MATCH");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "POINTSTABLE":
					AnimateOutGraphics(print_writer, "POINTSTABLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "LTPOINTSTABLE":
					AnimateOutGraphics(print_writer, "LTPOINTSTABLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"MINI");
					break;
				case "BOWLER_STYLE":
					AnimateOutGraphics(print_writer, "BOWLER_STYLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "BATSMAN_STYLE":
					AnimateOutGraphics(print_writer, "BATSMAN_STYLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "TEAMS_LOGO":
					AnimateOutGraphics(print_writer, "TEAMS_LOGO");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				
				case "MANHATTAN":
					AnimateOutGraphics(print_writer, "MANHATTAN");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "WORM":
					AnimateOutGraphics(print_writer, "WORM");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "RICHEIS":
					AnimateOutGraphics(print_writer, "RICHEIS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "WEATHER":
					AnimateOutGraphics(print_writer, "WEATHER");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "IMPACT":
					AnimateOutGraphics(print_writer, "IMPACT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "PHASE-COMPARISON":
					AnimateOutGraphics(print_writer, "PHASE-COMPARISON");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;	
				case "PHASE_BY_SCORE":
					AnimateOutGraphics(print_writer, "PHASE_BY_SCORE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"LT");
					break;
				case "LINEUP":
					AnimateOutGraphics(print_writer, "LINEUP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "MOSTRUNS":
					AnimateOutGraphics(print_writer, "MOSTRUNS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "MOSTWICKETS":
					AnimateOutGraphics(print_writer, "MOSTWICKETS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "MOSTFOURS":
					AnimateOutGraphics(print_writer, "MOSTFOURS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "MOSTSIXES":
					AnimateOutGraphics(print_writer, "MOSTSIXES");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
				case "HIGHESTSCORE":
					AnimateOutGraphics(print_writer, "HIGHESTSCORE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster,"FF");
					break;
					
				}
				break;
			case "ANIMATE-OUT-POWERPLAY":
				resetAnimation(print_writer, broadcaster, "POWERPLAY");
				break;
			case "ANIMATE-OUT-DIRECTOR":
				resetAnimation(print_writer, broadcaster, director.toUpperCase());
				break;
			//}
			//return new ObjectMapper().writeValueAsString(this_doad).toString();
		}
			break;
		//case "POPULATE-SELECT-PLAYER": 
			//return new ObjectMapper().writeValueAsString(match).toString();
	}
		return null;
}
	public void populateScorecard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, CricketService cricketService,  String session_selected_broadcaster) throws InterruptedException 
	{
		int row_id = 0, omo_num = 0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			
			String cont_name= "",stillToBatImpactName = "";
			boolean impactInThisInning = false, isReplacePlayerStillToBat = false, isImpactPlayerStillToBat = false, impactPlayerDataFilled = false;
//			print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$Header$BatTeamLogo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 0"+"\0");
			
			for(int i=1; i<=13; i++) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + i + "$RowAni$Star*ACTIVE SET 0"+"\0");
			}
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$ExtrasGrp$Extras*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatSponsor" + " SET " + logo_path + " " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path + "TLogo05" + "\0");

					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamBadge()+ "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$MaxSize$BatHeader*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$MaxSize$BatHeader*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					ImpactData[] impactData= CricketFunctions.getImpactPlayerList(match, cricketService);
					Collections.sort(inn.getBattingCard());
					for (BattingCard bc : inn.getBattingCard()) {
						row_id = row_id + 1;
						if(impactData != null) {
							if(impactData[0] != null) {
								if(inn.getBatting_team().getTeamId() == impactData[0].getTeamId()) {
									if(bc.getPlayerId() == impactData[0].getOutPlayerId()) {
										if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
											isReplacePlayerStillToBat = true;
										}
										if(!inn.getBattingCard().stream().filter(btc -> btc.getPlayerId() == impactData[0].getInPlayerId()).findFirst().isPresent()) {
											stillToBatImpactName = cricketService.getAllPlayer().get(impactData[0].getInPlayerId()-1).getTicker_name();
											if(isReplacePlayerStillToBat == true) {
												isImpactPlayerStillToBat = true;
											}
										}
										if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && isImpactPlayerStillToBat == false) {
											row_id--;
											continue;
										}
									}
									
								}
							}
							if(impactData[1] != null) {
								if(inn.getBatting_team().getTeamId() == impactData[1].getTeamId()) {
									if(bc.getPlayerId() == impactData[1].getOutPlayerId()) {
										if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
											isReplacePlayerStillToBat = true;
										}
										if(!inn.getBattingCard().stream().filter(btc -> btc.getPlayerId() == impactData[1].getInPlayerId()).findFirst().isPresent()) {
											stillToBatImpactName = cricketService.getAllPlayer().get(impactData[1].getInPlayerId()-1).getTicker_name();
											if(isReplacePlayerStillToBat == true) {
												isImpactPlayerStillToBat = true;
											}
										}
										if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && isImpactPlayerStillToBat == false) {
											row_id--;
											continue;
										}
									}
								}
							}
						}
						print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$RowAll*FUNCTION*Omo*vis_con SET " + row_id + " \0");
						if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							impactInThisInning = true;
							print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + row_id + "$RowAni$Star*ACTIVE SET 1"+"\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + row_id + "$RowAni$Star*ACTIVE SET 0"+"\0");
						}
						
//						if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//							print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + row_id + "$RowAni$Star*ACTIVE SET 1"+"\0");
//						}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//							print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + row_id + "$RowAni$Star*ACTIVE SET 1"+"\0");
//						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + row_id + "$RowAni$Star*ACTIVE SET 0"+"\0");
//						}
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
							//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + row_id + "$RowAni$Star*ACTIVE SET 0"+"\0");
							if(bc.getHowOut() == null) {
								if(isImpactPlayerStillToBat) {
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo*FUNCTION*Omo*vis_con SET 0 \0");
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$LeftPlayerName$BatsmanName*GEOM*TEXT SET " + stillToBatImpactName + "\0");
									impactPlayerDataFilled = true;
									isImpactPlayerStillToBat = false;
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo*FUNCTION*Omo*vis_con SET 0 \0");
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$LeftPlayerName$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
								}
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$WicketPlayerName*GEOM*TEXT SET " + "retired hurt" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$WicketPlayerName*GEOM*TEXT SET " + "absent hurt" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
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
							
//							if(bc.getHowOut() == null) {
//								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$RowAll*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
//							}
//							else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
//								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$RowAll*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
//							}
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row"+row_id+"$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
//							if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//								print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + row_id + "$RowAni$Star*ACTIVE SET 1"+"\0");
//							}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//								print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + row_id + "$RowAni$Star*ACTIVE SET 1"+"\0");
//							}else {
//								print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$RowAll$Row" + row_id + "$RowAni$Star*ACTIVE SET 0"+"\0");
//							}
							
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
								if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
									}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase("timed_out")){
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + "timed out" + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)){
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
											}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
											
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + " (sub - " + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
											}
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
										}
									}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)){
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)){
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)) {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne().replace("(SUB)", "") + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
												
											}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne().replace("(SUB)", "") + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
												
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
											}
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getStatus() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + " " + "\0");		
							}
						}
					}
					if(impactInThisInning) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$ImpactLegend*ACTIVE SET 1"+"\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard$DataAll$ImpactLegend*ACTIVE SET 0"+"\0");
					}
					if(impactPlayerDataFilled == false && !stillToBatImpactName.isEmpty()) {
						if(isImpactPlayerStillToBat) {
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$RowAll*FUNCTION*Omo*vis_con SET " + row_id + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + (row_id+1) + "$RowOmo*FUNCTION*Omo*vis_con SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + (row_id+1) + "$RowOmo$LeftPlayerName$BatsmanName*GEOM*TEXT SET " + stillToBatImpactName + "\0");
						}
						impactPlayerDataFilled = true;
					}
					print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$ExtrasGrp$Extras*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$ExtrasGrp$Extras*GEOM*TEXT SET " + "WD:" + inn.getTotalWides() + " NB:" + inn.getTotalNoBalls() + " B:" + inn.getTotalByes() + " LB:" + inn.getTotalLegByes()  + " Pen:" + inn.getTotalPenalties() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$OversGrp$Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
					print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					} else {
					print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}
			if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" || 
					which_graphics_onscreen == "POINTSTABLE" || which_graphics_onscreen == "PARTNERSHIP") {	
				if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BattingCardIn 2.076 BowlingCardIn 0.0 \0");
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BattingCardIn 2.076 SummaryIn 0.0 \0");
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BattingCardIn 2.076 PointsTableIn 0.0 \0");
				}else if(which_graphics_onscreen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BattingCardIn 2.076 PartnershipAllIn 0.0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 1"+"\0");
				}
				print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
			}else {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BattingCardIn 2.076 \0");
			}
			TimeUnit.MILLISECONDS.sleep(1000);
			break;
		}
		
	}
	public void populateMiniBattingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
				int row_id = 0, omo_num = 0,batting_size=0;
				String cont_name= "";
				boolean impactInThisInning = false;
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						this.status = CricketUtil.SUCCESSFUL;
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$TopPart$LogoMotion*ACTIVE SET 0 \0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBatting_team().getLastname().toUpperCase() + "\0");
	
						
						Collections.sort(inn.getBattingCard());
						for (BattingCard bc : inn.getBattingCard()) {
							row_id = row_id + 1;
							switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.STILL_TO_BAT:
								if(bc.getHowOut() != null) {
									if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT) || bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
										batting_size+=1;
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAni$Star*ACTIVE SET 1"+"\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAni$Star*ACTIVE SET 0"+"\0");
										}
										
										print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
												"$RowAni$BatsmanIcon*ACTIVE SET 0\0");
										
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + batting_size + "*ACTIVE SET 1"+"\0");
										
										print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
												"$RowAni$RowOmo*FUNCTION*Omo*vis_con SET 1 \0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
												"$RowAni$Dehighlight$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
												"$RowAni$RowOmo$Dehighlight$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
										
										print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
												"$RowAni$RowOmo$Dehighlight$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
									}
								}
								break;
							default:
								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 1;
									cont_name = "$Dehighlight";
									batting_size = batting_size + 1;
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + batting_size + "*ACTIVE SET 1"+"\0");

									break;
								case CricketUtil.NOT_OUT:
									omo_num = 2;
									cont_name = "$Highlight";
									batting_size = batting_size + 1;
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + batting_size + "*ACTIVE SET 1"+"\0");

									break;
								}

								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
										"$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
										"$RowAni$RowOmo" + cont_name + "$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
										"$RowAni$RowOmo" + cont_name + "$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
								
								if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									impactInThisInning = true;
									print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAni$Star*ACTIVE SET 1"+"\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAni$Star*ACTIVE SET 0"+"\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
										"$RowAni$BatsmanIcon*ACTIVE SET 0"+"\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
										"$RowAni$RowOmo" + cont_name + "$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								break;
							}
						}
						if(impactInThisInning) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$Star_Legend*ACTIVE SET " + "1" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$Star_Legend*ACTIVE SET " + "0" + "\0");
						}
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.500\0");
				TimeUnit.MILLISECONDS.sleep(1000);
			break;
		}
	}
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene,boolean is_this_updating, int whichInning,   MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		int row_id = 0,len=0; 
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			boolean impactInThisInning = false;
			print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 0"+"\0");
			
			for(int i=1; i<=10; i++) {
				print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + i + "$Star*ACTIVE SET 0"+"\0");
			}
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$OversGrp$Overs*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallSponsor" + " SET " + logo_path + " " + "\0");
						
						if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + logo_path + 
									match.getSetup().getHomeTeam().getTeamBadge() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$MaxSize$BallHeader*GEOM*TEXT SET " + 
									match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + logo_path + 
									match.getSetup().getAwayTeam().getTeamBadge() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$MaxSize$BallHeader*GEOM*TEXT SET " + 
									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard$DataAll$Highlight*FUNCTION*Omo*vis_con SET " + "0" +"\0");
						
						for (BowlingCard boc : inn.getBowlingCard()) {
							
							if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls() > 0)){
								len = len + 1;
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo*FUNCTION*Omo*vis_con SET " + len +"\0");
								
								row_id = row_id + 1;
								
								if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
										CricketFunctions.GetTargetData(match).getRemaningBall()  == 0) {
									
									print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard$DataAll$Highlight*FUNCTION*Omo*vis_con SET " + "0" +"\0");
									
								}else {
									if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
										if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard$DataAll$Highlight*FUNCTION*Omo*vis_con SET 0\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard$DataAll$Highlight*FUNCTION*Omo*vis_con SET " + row_id +"\0");
										}
									}
								}
								if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									impactInThisInning = true;
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Star*ACTIVE SET 1"+"\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Star*ACTIVE SET 0"+"\0");
								}
								
//								if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$Star*ACTIVE SET 1"+"\0");
//								}else if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$Star*ACTIVE SET 1"+"\0");
//								}else {
//									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$Star*ACTIVE SET 0"+"\0");
//								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$BowlerName*GEOM*TEXT SET " + 
										boc.getPlayer().getTicker_name() +"\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$OversValue*GEOM*TEXT SET " + 
										CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) +"\0");
								
								if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10) 
										|| match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Row0$Highlight$ScoreGrp$MaidensHead*GEOM*TEXT SET " + "DOTS" +"\0");
									if(boc.getDots() < 0) {
										print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + "0" +"\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + boc.getDots() +"\0");
									}
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Row0$Highlight$ScoreGrp$MaidensHead*GEOM*TEXT SET " + "MAIDENS" +"\0");
									if(boc.getMaidens() < 0) {
										print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + "0" +"\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + boc.getMaidens() +"\0");
									}
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$RunsValue*GEOM*TEXT SET " +  boc.getRuns()+"\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$WicketsValue*GEOM*TEXT SET " + boc.getWickets() +"\0");
								if(boc.getEconomyRate() == null) {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$EconomyValue*GEOM*TEXT SET " + "-" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$EconomyValue*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
								}
							}
								
						}
							
							
						if(inn.getBowlingCard().size()<=7) {
							if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp*ACTIVE SET 0"+"\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp*ACTIVE SET 1" + "\0");
								for(FallOfWicket fow : inn.getFallsOfWickets()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard$DataAll$FowGrp$Row9$RowAni$ScoreGrp$" + fow.getFowNumber() + "*ACTIVE SET 1" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard$DataAll$FowGrp$Row10$RowAni$ScoreGrp$" + fow.getFowNumber() + "*ACTIVE SET 1" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp$Row10$ScoreGrp$" + fow.getFowNumber() + "*GEOM*TEXT SET "+ fow.getFowRuns()+"\0");
								}
								for(int fow_id = inn.getFallsOfWickets().size() + 1;fow_id <= 10;fow_id++) {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp$Row9$ScoreGrp$" + fow_id + "*ACTIVE SET 0" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp$Row10$ScoreGrp$" + fow_id + "*ACTIVE SET 0" + "\0");
									
								}
							}
						}
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$OversGrp$Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$ExtrasGrp$Extras*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$ExtrasGrp$Extras*GEOM*TEXT SET " + "WD:" + inn.getTotalWides() + " NB:" + inn.getTotalNoBalls() + " B:" + inn.getTotalByes() + " LB:" + inn.getTotalLegByes()  + " Pen:" + inn.getTotalPenalties() + "\0");
	
						if(impactInThisInning) {
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$ImpactLegend*ACTIVE SET 1" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$ImpactLegend*ACTIVE SET 0" + "\0");
						}
						
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
						}
					}
				}
				
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" || which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphics_onscreen == "POINTSTABLE" ||which_graphics_onscreen == "PARTNERSHIP") {
					
					if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BowlingCardIn 2.140 BattingCardIn 0.0\0");
					}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BowlingCardIn 2.140 SummaryIn 0.0 \0");
					}else if(which_graphics_onscreen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BowlingCardIn 2.140 PointsTableIn 0.0 \0");
					}else if(which_graphics_onscreen == "PARTNERSHIP") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BowlingCardIn 2.140 PartnershipAllIn 0.0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 1"+"\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET 0 \0");
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 BowlingCardIn 2.140 \0");
				}
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateMiniBowlingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				boolean impactInThisInning = false;
				int row_id = 0, omo_num = 0,len=0;
				String cont_name= "";
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$TopPart$LogoMotion*ACTIVE SET 0 \0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + inn.getTeamName4().getTeamName4().toUpperCase() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + inn.getBowling_team().getTeamName3().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBatting_team().getLastname().toUpperCase() + "\0");
						
						for (BowlingCard boc : inn.getBowlingCard()) {
							
							if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
								len=len+1;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + len + "\0");
							}
							
							switch (boc.getStatus().toUpperCase()) {
							case (CricketUtil.OTHER + CricketUtil.BOWLER):
								omo_num = 1;
								cont_name = "$Dehighlight";
								break;
							case (CricketUtil.LAST + CricketUtil.BOWLER):
								omo_num = 1;
								cont_name = "$Dehighlight";
								break;
							case (CricketUtil.CURRENT + CricketUtil.BOWLER):
								omo_num = 2;
								cont_name = "$Highlight";
								break;
							}
							
							row_id = row_id + 1;
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
									"$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");
							
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								impactInThisInning  = true;
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAni$Star*ACTIVE SET 1"+"\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAni$Star*ACTIVE SET 0"+"\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
									"$RowAni$RowOmo" + cont_name + "$BatsmanName*GEOM*TEXT SET  " + boc.getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
									"$RowAni$RowOmo" + cont_name + "$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
									"$RowAni$RowOmo" + cont_name + "$ScoreGrp$noname$BatsmanBall*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");
	
						}
					}
				}
				if(impactInThisInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$Star_Legend*ACTIVE SET " + "1" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$Star_Legend*ACTIVE SET " + "0" + "\0");
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.350\0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populatePartnership(PrintWriter print_writer, String viz_scene,int whichInning,MatchAllData match, CricketService cricketService, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				boolean impactInThisInning = false, isImpactPlayerStillToBat = false, impactPlayerDataFilled = false;
				int row_id = 0, omo_num = 0,Top_Score = 50, row_size = 0;
				float Mult = 322, ScaleFac1 = 0, ScaleFac2 = 0;
				String cont_name= "",Left_Batsman = "",Right_Batsman="",stillToBatImpactName="";
				for(int i=1; i<=13; i++) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + i + "$RowAnimation$Star*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + i + "$RowAnimation$Star2*ACTIVE SET 0"+"\0");
				}
	
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartTeamName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$Header$BatTeamLogo*ACTIVE SET 0 \0");
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
	
					//if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartTeamName" + " SET " + inn.getBatting_team().getTeamName1()+ "\0");
						if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPartTeamLogo" + " SET " + 
									match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPartTeamLogo" + " SET " + logo_path + 
									match.getSetup().getHomeTeam().getTeamBadge() + "\0");
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPartTeamLogo" + " SET " + 
									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPartTeamLogo" + " SET " + logo_path + 
									match.getSetup().getAwayTeam().getTeamBadge() + "\0");
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
						//System.out.println(inn.getBattingCard().size());
						
	
						ImpactData[] impactData = CricketFunctions.getImpactPlayerList(match, cricketService);
						for (Partnership ps : inn.getPartnerships()) {
							
							row_id = row_id + 1;
							Left_Batsman ="" ; Right_Batsman="";
							for (BattingCard bc : inn.getBattingCard()) {
								if(impactData != null) {
									if(impactData[0] != null) {
										if(inn.getBatting_team().getTeamId() == impactData[0].getTeamId()) {
											if(bc.getPlayerId() == impactData[0].getOutPlayerId()) {
												if(!inn.getBattingCard().stream().filter(btc -> btc.getPlayerId() == impactData[0].getInPlayerId()).findFirst().isPresent()) {
													stillToBatImpactName = cricketService.getAllPlayer().get(impactData[0].getInPlayerId()-1).getTicker_name();
													isImpactPlayerStillToBat = true;
												}
												if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && isImpactPlayerStillToBat == false) {
													row_size = (inn.getBattingCard().size() - 1);
													continue;
												}
											}
											
										}
									}
									if(impactData[1] != null) {
										if(inn.getBatting_team().getTeamId() == impactData[1].getTeamId()) {
											if(bc.getPlayerId() == impactData[1].getOutPlayerId()) {
												if(!inn.getBattingCard().stream().filter(btc -> btc.getPlayerId() == impactData[1].getInPlayerId()).findFirst().isPresent()) {
													stillToBatImpactName = cricketService.getAllPlayer().get(impactData[1].getInPlayerId()-1).getTicker_name();
													isImpactPlayerStillToBat = true;
												}
												if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && isImpactPlayerStillToBat == false) {
													row_size = (inn.getBattingCard().size() - 1);
													continue;
												}
											}
										}
									}
								}
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
									cont_name = "$Highlight";
								}
							}
							else {
								if(ps.getPartnershipNumber()<inn.getPartnerships().size()) {
									omo_num = 3;
									cont_name = "$Highlight";
								}
								else if(ps.getPartnershipNumber() >= inn.getPartnerships().size()) {
									omo_num = 2;
									cont_name = "$Dehighlight";
								}
							}
							
							ScaleFac1 = ((ps.getFirstBatterRuns())*(Mult/Top_Score)) ;
							ScaleFac2 = ((ps.getSecondBatterRuns())*(Mult/Top_Score)) ;
							
							System.out.println("ROW SIZE : "+inn.getBattingCard().size());
							if(inn.getTotalWickets() >= 9) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + inn.getPartnerships().size() + "\0");
							}else {
								if(row_size != 0) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_size + "\0");
								}else {
									if(inn.getBattingCard().size() == 13) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + "12" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + inn.getBattingCard().size() + "\0");
									}
									
								}
							}
							
	
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " 
									+ String.valueOf(omo_num) + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRightPlayerImpact" + " SET " + inn.getPartnerships().size() + "\0");
							
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), ps.getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
								impactInThisInning = true;
								print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id + "$RowAnimation$Star*ACTIVE SET 1"+"\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id + "$RowAnimation$Star*ACTIVE SET 0"+"\0");
							}
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), ps.getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
								impactInThisInning = true;
								print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id + "$RowAnimation$Star2*ACTIVE SET 1"+"\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id + "$RowAnimation$Star2*ACTIVE SET 0"+"\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + 
									"$LeftPlayerName*GEOM*TEXT SET " + Left_Batsman + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + 
									"$RightPlayerName*GEOM*TEXT SET " + Right_Batsman + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + 
									"$Bar*FUNCTION*BarValues*Bar_Value__1 SET " + ScaleFac1 + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + 
									"$Bar*FUNCTION*BarValues*Bar_Value__2 SET " + ScaleFac2 + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + 
									"$ScoreGrp$PartnershipRun*GEOM*TEXT SET " + ps.getTotalRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + 
									"$ScoreGrp$PartnershipBalls*GEOM*TEXT SET " + ps.getTotalBalls() + "\0");		
						}
						if(inn.getPartnerships().size() >= 10) {
							row_id = row_id + 1;
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + " \0");
						}
						else {
							for (BattingCard bc : inn.getBattingCard()) {
								if(row_id < inn.getBattingCard().size()) {
									if(row_id == inn.getPartnerships().size()) {
										row_id = row_id + 1;
										print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
												"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + " \0");					
										
										if(match.getSetup().getTargetOvers() != "") {
											if(match.getMatch().getInning().get(whichInning - 1).getTotalOvers() == Integer.valueOf(match.getSetup().getTargetOvers()) || 
													match.getMatch().getInning().get(whichInning - 1).getTotalWickets() >= 10 ) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
														"$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
											}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || 
													CricketFunctions.getWicketsLeft(match,whichInning) <= 0) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
														"$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
														"$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "STILL TO BAT" +" \0");
											}
										}else {
											if(match.getMatch().getInning().get(whichInning - 1).getTotalOvers() == match.getSetup().getMaxOvers() || 
													match.getMatch().getInning().get(whichInning - 1).getTotalWickets() >= 10 ) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
														"$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
											}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || 
													CricketFunctions.getWicketsLeft(match,whichInning) <= 0) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
														"$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
														"$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "STILL TO BAT" +" \0");
											}
										}
										
									}
									else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
										row_id = row_id + 1;
										print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
												"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + " \0");
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
											impactInThisInning = true;
											print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id + "$RowAnimation$Star*ACTIVE SET 1"+"\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id + "$RowAnimation$Star*ACTIVE SET 0"+"\0");
										}
										
										print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + 
												bc.getPlayer().getTicker_name().toUpperCase()+" \0");
										if(impactData[0] != null) {
											if(inn.getBatting_team().getTeamId() == impactData[0].getTeamId()) {
												if(isImpactPlayerStillToBat && impactData[0].getOutPlayerId() == bc.getPlayerId()) {
													print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id + "$RowAnimation$Star*ACTIVE SET 1"+"\0");
													print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + 
															stillToBatImpactName+" \0");
													isImpactPlayerStillToBat = false;
													impactPlayerDataFilled = true;
													
												}
											}
										}
										if(impactData[1] != null) {
											if(inn.getBatting_team().getTeamId() == impactData[1].getTeamId()) {
												if(isImpactPlayerStillToBat && impactData[1].getOutPlayerId() == bc.getPlayerId()) {
													print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id + "$RowAnimation$Star*ACTIVE SET 1"+"\0");
													print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + 
															stillToBatImpactName+" \0");
													isImpactPlayerStillToBat = false;
													impactPlayerDataFilled = true;
												}
											}
										}
									}	
								}
								else {
									break;
								}
							}
							if(impactPlayerDataFilled == false && !stillToBatImpactName.isEmpty()) {
								row_id = row_id + 1;
								print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id + "$RowAnimation$Star*ACTIVE SET 1"+"\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + 
										stillToBatImpactName+" \0");
								
								impactPlayerDataFilled = true;
							}
						}
						if(impactInThisInning) {
							print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$BottomInfo$ImpactLegend*ACTIVE SET 1" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$BottomInfo$ImpactLegend*ACTIVE SET 0" + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BottomInfo$BottomInfoGrp$BottomInfoAll$ExtrasGrp$ExtrasValue*GEOM*TEXT SET " + 
								inn.getTotalExtras() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BottomInfo$BottomInfoGrp$BottomInfoAll$noname$OversGrp$OversValue*GEOM*TEXT SET " + 
								CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET " + inn.getTotalRuns() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
						}
					}
				}
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" || which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || 
						which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" || which_graphics_onscreen == "POINTSTABLE") {	
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 1"+"\0");
					if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 PartnershipAllIn 1.830 PartnershipAllIn$DataIn 2.060 "
								+ "PartnershipAllIn$BallOffsetIn 2.250 PartnershipAllIn$ManDataIn 0.880 PartnershipAllIn$DataIn 1.990 BattingCardIn 0.0 \0");
					}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 PartnershipAllIn 1.830 PartnershipAllIn$DataIn 2.060 "
								+ "PartnershipAllIn$BallOffsetIn 2.250 PartnershipAllIn$ManDataIn 0.880 PartnershipAllIn$DataIn 1.990 BowlingCardIn 0.0 \0");
					}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 PartnershipAllIn 1.830 PartnershipAllIn$DataIn 2.060 "
								+ "PartnershipAllIn$BallOffsetIn 2.250 PartnershipAllIn$ManDataIn 0.880 PartnershipAllIn$DataIn 1.990 SummaryIn 0.0 \0");
					}else if(which_graphics_onscreen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 PartnershipAllIn 1.830 PartnershipAllIn$DataIn 2.060 "
								+ "PartnershipAllIn$BallOffsetIn 2.250 PartnershipAllIn$ManDataIn 0.880 PartnershipAllIn$DataIn 1.990 PointsTableIn 0.0 \0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 0"+"\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 PartnershipAllIn 1.830 PartnershipAllIn$DataIn 2.060 "
							+ "PartnershipAllIn$BallOffsetIn 2.250 PartnershipAllIn$ManDataIn 0.880 PartnershipAllIn$DataIn 1.990 \0");
					
//					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 0"+"\0");
				}
				TimeUnit.MILLISECONDS.sleep(1000);
			}
			break;
		}
		
	}
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning,List<VariousText> vt,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
			print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 0"+"\0");
			int row_id = 0, max_Strap = 0, total_inn = 0;
			boolean impactInSummary = false;
				String teamname = "",teamname_logo=""; 
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				
				for(int i=2; i<=4; i++) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + i + 
							"$RowAni$Star*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + i + 
							"$RowAni$Star2*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + (6+(i-2)) + 
							"$RowAni$Star2*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + (6+(i-2)) + 
							"$RowAni$Star*ACTIVE SET 0"+"\0");
				}
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
//				print_writer.println("-1 RENDERER*TREE*$Main$PartnershipAll$Header$BatTeamLogo*ACTIVE SET 0 \0");
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumSponsor" + " SET " + logo_path + " " + "\0");
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumSponsor" + " SET " + logo_path + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$Shriram_Logo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + "TLogo05" +" \0");
	
	
				for(int i = 1; i <= whichInning ; i++) {
	
					if(i == 1) {
						row_id = 0;
						max_Strap = 4;
						if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*GEOM*TEXT SET " + "TOSS" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*ACTIVE SET 0 \0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row6*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row7*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row8*ACTIVE SET 0 \0");
						
					} else {
						row_id = 4;
						max_Strap = 8;
						if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*GEOM*TEXT SET " + "TOSS" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*ACTIVE SET 0 \0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row6*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row7*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row8*ACTIVE SET 1 \0");
					}
					
					row_id = row_id + 1;
					
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$SummaryHeader*GEOM*TEXT SET " + "SUMMARY - " + match.getSetup().getMatchIdent() + "\0");
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						teamname = match.getSetup().getHomeTeam().getTeamName1();
						teamname_logo  = match.getSetup().getHomeTeam().getTeamBadge();
					}else if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getAwayTeamId()) {
						teamname = match.getSetup().getAwayTeam().getTeamName1();
						teamname_logo = match.getSetup().getAwayTeam().getTeamBadge();
					}
					
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumTeamBadge" + i + " SET " + logo_path + teamname_logo + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
					
					if(match.getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + match.getMatch().getInning().get(i-1).getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + match.getMatch().getInning().get(i-1).getTotalRuns() + slashOrDash + String.valueOf(match.getMatch().getInning().get(i-1).getTotalWickets()) + "\0");	
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Overs*GEOM*TEXT SET " + 
							CricketFunctions.OverBalls(match.getMatch().getInning().get(i-1).getTotalOvers(),match.getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
					
					if(match.getMatch().getInning().get(i-1).getBattingCard() != null) {
						Collections.sort(match.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
						
						for(BattingCard bc : match.getMatch().getInning().get(i-1).getBattingCard()) {
							if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								row_id = row_id + 1;
								if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									impactInSummary = true;
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
											"$RowAni$Star2*ACTIVE SET 1"+"\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
											"$RowAni$Star2*ACTIVE SET 0"+"\0");
								}
								
//								if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
//											"$RowAni$RowOmo$Dehighlight$Batsman$Star*ACTIVE SET 1"+"\0");
//								}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
//											"$RowAni$RowOmo$Dehighlight$Batsman$Star*ACTIVE SET 1"+"\0");
//								}else {
//									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
//											"$RowAni$RowOmo$Dehighlight$Batsman$Star*ACTIVE SET 0"+"\0");
//								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row"+row_id+"$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + " \0");
	
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
								
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$NotOut*ACTIVE SET 1 \0");
								} else {
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$NotOut*ACTIVE SET 0 \0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								
								if(i == 1 && row_id >= 4) {
									break;
								}else if(i == 2 && row_id >= 8) {
									break;
								}
							}
						}
					}
	
					for(int j = row_id + 1; j <= max_Strap; j++) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + j + "$RowAni$RowOmo$Dehighlight$Batsman*ACTIVE SET 0 \0");
					}
					
					if(i == 1) {
						row_id = 1;
					}
					else {
						row_id = 5;
					}
	
					if(match.getMatch().getInning().get(i-1).getBowlingCard() != null) {
						
						Collections.sort(match.getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
	
						for(BowlingCard boc : match.getMatch().getInning().get(i-1).getBowlingCard()) {
							if(boc.getWickets() > 0 ) {
								row_id = row_id + 1;
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler*ACTIVE SET 1 \0");
								
								if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									impactInSummary = true;
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
											"$RowAni$Star*ACTIVE SET 1"+"\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
											"$RowAni$Star*ACTIVE SET 0"+"\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$BowlerName*GEOM*TEXT SET " + 
									boc.getPlayer().getTicker_name().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$ScoreGrp$BowlerFigure*GEOM*TEXT SET " + 
									boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$ScoreGrp$BowlerOvers*GEOM*TEXT SET " + 
									CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								
								if(i == 1 && row_id >= 4) {
									break;
								}
								else if(i == 2 && row_id >= 8) {
									break;
								}
							}
						}
					}
					
					for(int j = row_id + 1; j <= max_Strap; j++) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + j + "$RowAni$RowOmo$Dehighlight$Bowler*ACTIVE SET 0 \0");
					}
				}
				if(impactInSummary) {
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$ImpactLegend*ACTIVE SET 1" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$ImpactLegend*ACTIVE SET 0" + "\0");
				}
				
				for(VariousText vartext : vt) {
					if(vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER") && vartext.getUseThis().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
								+ vartext.getVariousText() + " \0");
						}else if(vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER") && vartext.getUseThis().equalsIgnoreCase(CricketUtil.NO)) {
							
							if(match.getMatch().getMatchResult() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
												+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.TEAMNAME_3, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
								}else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + "MATCH TIED" + " \0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + " \0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + 
											CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.TEAMNAME_3, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
									//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + match.getMatchStatus().toUpperCase() + ";");
								}
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + 
										CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.TEAMNAME_3, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + match.getMatchStatus().toUpperCase() + ";");
								
								if(match.getSetup().getTargetType() != null) {
									if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
										print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + 
												CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.TEAMNAME_3, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
									}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + 
												CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.TEAMNAME_3, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
									}
								}
							}
						}
					}
				
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" || which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || 
						which_graphics_onscreen == "POINTSTABLE" || which_graphics_onscreen == "PARTNERSHIP") {
					if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 SummaryIn 1.716 BattingCardIn 0.0 \0");
					}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 SummaryIn 1.716 BowlingCardIn 0.0 \0");
					}else if(which_graphics_onscreen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 SummaryIn 1.716 PointsTableIn 0.0 \0");
					}else if(which_graphics_onscreen == "PARTNERSHIP") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 SummaryIn 1.716 PartnershipAllIn 0.0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 1"+"\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 SummaryIn 1.716 \0");
				}
				TimeUnit.MILLISECONDS.sleep(1000);
			}
			break;
		}
		
	}
	public void populateTeamsLogo(PrintWriter print_writer,String viz_scene, String headerData, List<Team> teams ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		System.out.println("HEADER : "+headerData);
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + headerData.toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo05" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "" + "\0");
			
			for(int i=1;i<=teams.size();i++) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo" + i + " SET " + logo_path + teams.get(i-1).getTeamBadge() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName0" + i + " SET " + teams.get(i-1).getCaptains() + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(800);
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	
	public void populateImpactPlayer(PrintWriter print_writer, String viz_scene, int playerOutId, int playerInId, MatchAllData match, CricketService cricketService,String session_selected_broadcaster, Configuration config) {
		
		Player outPlayer = cricketService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == playerOutId).findAny().orElse(null);
		Player inPlayer = cricketService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == playerInId).findAny().orElse(null);
		
		if(outPlayer.getSurname() != null) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName1" + " SET " + outPlayer.getFirstname() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName1" + " SET " + outPlayer.getSurname() + "\0");
		}else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName1" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName1" + " SET " + outPlayer.getFirstname() + "\0");
		}
		
		if(inPlayer.getSurname() != null) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName2" + " SET " + inPlayer.getFirstname() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName2" + " SET " + inPlayer.getSurname() + "\0");
		}else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName2" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName2" + " SET " + inPlayer.getFirstname() + "\0");
		}
		
//		print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-PlayerName01" + " SET " + outPlayer.getFull_name() + "\0");
//		print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-PlayerName02" + " SET " + inPlayer.getFull_name() + "\0");
		
		if(inPlayer.getTeamId() == match.getSetup().getHomeTeamId()) {
			if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2"+ " SET " + photo_path + 
						match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + inPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + 
						match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + inPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}
			
			if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1"+ " SET " + photo_path + 
						match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + outPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + 
						match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + outPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgHomeTeamLogo" + " SET " + logo_path 
					+match.getSetup().getHomeTeam().getTeamBadge()+ "\0");
		}else {
			if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2"+ " SET " + photo_path + 
						match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + inPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + 
						match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + inPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}
			if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1"+ " SET " + photo_path + 
						match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + outPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + 
						match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + outPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgHomeTeamLogo" + " SET " + logo_path 
					+match.getSetup().getAwayTeam().getTeamBadge()+ "\0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	}
	
	public void populateFFFixtures(PrintWriter print_writer,String viz_scene,String Grp,List<Fixture> fixtures,List<Team> teams ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "AFGHANISTAN_T20":
			
			int row_id=0;
			String newDate = "",match_name="";
			
			String[] dateSuffix = {
					"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
					
					"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
					
					"th", "st", "nd", "rd", "th", "th", "th", "th", "th","th",
					
					"th", "st"
			};
			
			print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-HEADER" + " SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-HEADER" + " SET " + "FIXTURES - GROUP " + Grp.toUpperCase() + "\0");
			
			for(int i=0;i<=fixtures.size()-1;i++) {
				if(fixtures.get(i).getTeamgroup().equalsIgnoreCase(Grp)) {
					row_id = row_id + 1;
					if(match.getSetup().getHomeTeamId() == fixtures.get(i).getHometeamid() && match.getSetup().getAwayTeamId() == fixtures.get(i).getAwayteamid()) {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "vHighlightSelection" + row_id + " SET " + "1" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "vHighlightSelection" + row_id + " SET " + "0" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + row_id + " SET " + 
							teams.get(fixtures.get(i).getHometeamid() - 1).getTeamName1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + row_id + " SET " + 
							teams.get(fixtures.get(i).getAwayteamid() - 1).getTeamName1() + "\0");
			
					if(fixtures.get(i).getMatchnumber() < 10) {
						match_name = "MATCH " + fixtures.get(i).getMatchnumber();
					}else {
						match_name = fixtures.get(i).getMatchfilename().toUpperCase();
					}
					
					newDate = fixtures.get(i).getDate().split("-")[0];
					if(Integer.valueOf(newDate) < 10) {
						newDate = newDate.replaceFirst("0", "");
					}
					
					if(fixtures.get(i).getWinnerteam() != null) {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "tInfo" + row_id + " SET " + fixtures.get(i).getWinnerteam() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "tInfo" + row_id + " SET " + match_name + " - LIVE AT " + 
							fixtures.get(i).getGmtTime() + " ON " + newDate + dateSuffix[Integer.valueOf(newDate)] + " AUG FROM " + fixtures.get(i).getVenue() + "\0");
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
			break;
		}
	}
	public void populateFFFixturesTeams(PrintWriter print_writer,String viz_scene,int teamid,List<Fixture> fixtures,List<Team> teams ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "AFGHANISTAN_T20":
			
			int row_id=0;
			String newDate = "",match_name="";
			
			String[] dateSuffix = {
					"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
					
					"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
					
					"th", "st", "nd", "rd", "th", "th", "th", "th", "th","th",
					
					"th", "st"
			};
			
			print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-HEADER" + " SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-HEADER" + " SET " + teams.get(teamid - 1).getTeamName1() + "\0");
			
			
			
			for(int i=0;i<=fixtures.size()-1;i++) {
				if(fixtures.get(i).getHometeamid() == teamid || fixtures.get(i).getAwayteamid() == teamid) {
					row_id = row_id + 1;
					
					print_writer.println("-1 RENDERER*TREE*$object$noname$RowNumber*FUNCTION*Omo*vis_con SET " + row_id + " \0");
					
					if(match.getSetup().getHomeTeamId() == fixtures.get(i).getHometeamid() && match.getSetup().getAwayTeamId() == fixtures.get(i).getAwayteamid()) {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "vHighlightSelection" + row_id + " SET " + "1" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "vHighlightSelection" + row_id + " SET " + "0" + "\0");
					}
					
					if(fixtures.get(i).getHometeamid() == teamid) {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + row_id + " SET " + 
								teams.get(fixtures.get(i).getAwayteamid() - 1).getTeamName1() + "\0");
					}else if(fixtures.get(i).getAwayteamid() == teamid) {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + row_id + " SET " + 
								teams.get(fixtures.get(i).getHometeamid() - 1).getTeamName1() + "\0");
					}
					
					if(fixtures.get(i).getMatchnumber() < 10) {
						match_name = "MATCH " + fixtures.get(i).getMatchnumber();
					}else {
						match_name = fixtures.get(i).getMatchfilename().toUpperCase();
					}
					
					newDate = fixtures.get(i).getDate().split("-")[0];
					if(Integer.valueOf(newDate) < 10) {
						newDate = newDate.replaceFirst("0", "");
					}
					
					if(fixtures.get(i).getWinnerteam() != null) {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "tInfo" + row_id + " SET " + fixtures.get(i).getWinnerteam() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "tInfo" + row_id + " SET " + match_name + " - LIVE AT " + 
							fixtures.get(i).getGmtTime() + " ON " + newDate + dateSuffix[Integer.valueOf(newDate)] + " AUG FROM " + fixtures.get(i).getVenue() + "\0");
					}
				}
			}
			
			if(row_id <= 3) {
				print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-SUB-HEADER" + " SET " + "LEAGUE MATCHES" + "\0");
			}else if(row_id > 3 && row_id <= 4) {
				print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-SUB-HEADER" + " SET " + "ROAD TO SEMI-FINAL" + "\0");
			}else if(row_id > 4 && row_id <= 5) {
				print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-SUB-HEADER" + " SET " + "ROAD TO FINAL" + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
			break;
		}
	}
	public void populateFFTeamSquad(PrintWriter print_writer,String viz_scene,int teamid,List<Player> players,List<Team> teams ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "AFGHANISTAN_T20":
			
			int row_id=0,A_row_id=0,B_row_id=0;
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$Dream11Logo*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge1" + " SET " + logo_path + 
					teams.get(teamid - 1).getTeamBadge() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " + teams.get(teamid - 1).getTeamName1() + "\0");
			
			for(Player plyr : players) {
				if(plyr.getTeamId() == teamid) {
					
					row_id = row_id + 1;
					if(row_id <= 11) {
						A_row_id = A_row_id + 1;
					}else {
						B_row_id = B_row_id + 1;
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$DataAll$TeamAll1$PlayerNameGrp*FUNCTION*Grid*num_row SET " + A_row_id + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$DataAll$TeamAll2$PlayerNameGrp*FUNCTION*Grid*num_row SET " + B_row_id + " \0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTeam1RowOmo" + row_id + " SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeam1PlayerName" + row_id + " SET " + plyr.getFull_name() + "\0");
				}
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "" + "\0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
			break;
		}
	}
	public void populateLineup(PrintWriter print_writer,String viz_scene,int team_id,String icon_data,CricketService cricketService,List<Team> team,List<Player> plyr,MatchAllData match, 
			String broadcaster, Configuration config) throws InterruptedException {
		
		int row_id = 0;
		this.status = CricketUtil.SUCCESSFUL;
		switch(icon_data.toUpperCase()) {
		case "ICON":
			if(team_id == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + 
						match.getSetup().getHomeTeam().getTeamBadge() + "\0");
				
				for(Player hs : match.getSetup().getHomeSquad()) {
					row_id = row_id + 1;
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Playing11$DataGrpAll$" + row_id + "$HomeNameGrp$Star*ACTIVE SET 0"+"\0");
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage0" + row_id + " SET " + photo_path + 
								match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage0" + row_id + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + 
								match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName0" + row_id + " SET " + hs.getTicker_name() + "\0");

					
					if(hs.getRole().equalsIgnoreCase(CricketUtil.BATSMAN) || hs.getRole().equalsIgnoreCase("Bat/Keeper")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "BATTER" + "\0");
					}else if(hs.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + CricketUtil.BOWLER + "\0");
					}else if(hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "ALL-ROUNDER" + "\0");
					}
					
					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "1" + "\0");
					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "KEEPER" + "\0");
					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "KEEPER" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "0" + "\0");
					}
					
				}
			}else if(team_id == match.getSetup().getAwayTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + 
						match.getSetup().getAwayTeam().getTeamBadge() + "\0");
				for(Player as : match.getSetup().getAwaySquad()) {
					row_id = row_id + 1;
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage0" + row_id + " SET " + photo_path + 
								match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage0" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + 
								match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName0" + row_id + " SET " + as.getTicker_name() + "\0");

					
					if(as.getRole().equalsIgnoreCase(CricketUtil.BATSMAN) || as.getRole().equalsIgnoreCase("Bat/Keeper")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "BATTER" + "\0");
					}else if(as.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + CricketUtil.BOWLER + "\0");
					}else if(as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "ALL-ROUNDER" + "\0");
					}
					
					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id + " SET " + "1" + "\0");
					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "KEEPER" + "\0");
					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "KEEPER" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "0" + "\0");
					}
				}
			}
			break;
		case "BATTING_CARD":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getBattingTeamId() == team_id) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + 
							inn.getBatting_team().getTeamBadge() + "\0");
					Collections.sort(inn.getBattingCard());
					for(BattingCard bc : inn.getBattingCard()) {
						row_id = row_id + 1;
						
						if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
							for(Player hs : match.getSetup().getHomeSquad()) {
								if(hs.getPlayerId() == bc.getPlayerId()) {
									if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "1" + "\0");
									}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "1" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "0" + "\0");
									}
								}
							}
						}else if(inn.getBattingTeamId() == match.getSetup().getAwayTeamId()) {
							for(Player as : match.getSetup().getAwaySquad()) {
								if(as.getPlayerId() == bc.getPlayerId()) {
									if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "1" + "\0");
									}else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "1" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain0" + row_id  + " SET " + "0" + "\0");
									}
								}
							}
						}
						
						if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Playing11$DataGrpAll$" + row_id + "$HomeNameGrp$Star*ACTIVE SET 1"+"\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Playing11$DataGrpAll$" + row_id + "$HomeNameGrp$Star*ACTIVE SET 0"+"\0");
						}
						
//						if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Playing11$DataGrpAll$" + row_id + "$HomeNameGrp$Star*ACTIVE SET 1"+"\0");
//						}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Playing11$DataGrpAll$" + row_id + "$HomeNameGrp$Star*ACTIVE SET 1"+"\0");
//						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Playing11$DataGrpAll$" + row_id + "$HomeNameGrp$Star*ACTIVE SET 0"+"\0");
//						}
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage0" + row_id + " SET " + photo_path + 
									inn.getBatting_team().getTeamName4() + "\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage0" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path  + 
									inn.getBatting_team().getTeamName4() + "\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName0" + row_id + " SET " + 
								bc.getPlayer().getTicker_name() + "\0");
						
						if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.START)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "IN AT " + row_id + "\0");
								
							}else if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + "DNB" + "\0");
							}
							
						}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT) || bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + 
									bc.getRuns() + "* (" + bc.getBalls() + ")" + "\0");
							}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo0" + row_id + " SET " + 
									bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
							}
						}
					}
				}
			}
			
			break;
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
		TimeUnit.SECONDS.sleep(1);
	}
	
	public void populateBugDismissal(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getSurname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
									
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
										if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
											if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
		
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bc.getHowOutText()  + "" + "\0");
											}
										}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
											if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
													bc.getHowOutPartOne().replace("(SUB)", "") + " " + bc.getHowOutPartTwo() + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bc.getHowOutText()  + "" + "\0");
											}
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bc.getHowOutText()  + "" + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "" + "\0");
									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBowlPopUp(PrintWriter print_writer,String viz_scene, int whichInning, String strikeRateOrRuns, int playerId, MatchAllData match,CricketService cricketService, String session_selected_broadcaster, Configuration config) {
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning).findAny().orElse(null);
			Player player = cricketService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == playerId).findAny().orElse(null);
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET "+logo_path + inning.getBowling_team().getTeamBadge() + "\0");
			if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + photo_path 
						+ inning.getBowling_team().getTeamName4() + "\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path  
						+ inning.getBowling_team().getTeamName4() + "\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}
			for(BowlingCard boc : inning.getBowlingCard()) {
				if(boc.getPlayerId() == playerId) {
					switch (strikeRateOrRuns.toUpperCase()) {
					case "ECONOMY":
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "ECONOMY"+ "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + CricketFunctions.getEconomy(boc.getRuns(), (boc.getOvers()*6)+(boc.getBalls()), 2, "-") + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + ""+ "\0");
						break;

					case "FIGURES":
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + ""+ "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Name$SubHead*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + boc.getWickets()+"-"+boc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET "+"(" +CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls())+")"+ "\0");
						break;
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + player.getTicker_name() + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.100 \0");
		}
	}
	
	public void populateBatPopUp(PrintWriter print_writer,String viz_scene, int whichInning, String strikeRateOrRuns, int playerId, MatchAllData match,CricketService cricketService, String session_selected_broadcaster, Configuration config) {
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning).findAny().orElse(null);
			Player player = cricketService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == playerId).findAny().orElse(null);
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET "+logo_path + inning.getBatting_team().getTeamBadge() + "\0");
			if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + photo_path 
						+ inning.getBatting_team().getTeamName4() + "\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path  
						+ inning.getBatting_team().getTeamName4() + "\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}
			for(BattingCard bc : inning.getBattingCard()) {
				if(bc.getPlayerId() == playerId) {
					switch (strikeRateOrRuns.toUpperCase()) {
					case "STRIKERATE":
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "STRIKE RATE"+ "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + ""+ "\0");
						break;

					case "RUNS":
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + ""+ "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + bc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + "OFF " +bc.getBalls()+ "\0");
						break;
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + player.getTicker_name() + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.100 \0");
		}
	}
	public void populateBug(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + " " + "\0");
									
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bc.getRuns() + "*" + " (" + bc.getBalls() + ")" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
									}
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "S/R " + 
											CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + "\0");
									
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "4s: " + bc.getFours()  + " 6s: "  + bc.getSixes() + "\0");									
								}
							}
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714\0");
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
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			TimeUnit.MILLISECONDS.sleep(1000);
			break;
		}
	}	
	public void populateBugBowler(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case "BOWLER":
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==playerId) {
									
									if(boc.getPlayer().getSurname() != null) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + boc.getPlayer().getFirstname() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + boc.getPlayer().getSurname() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + boc.getPlayer().getFirstname() + "\0");
									}
									
									if(boc.getOvers() <= 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
												CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " OVER" + "\0");
									}else if(boc.getOvers() == 1 && boc.getBalls() == 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
												CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " OVER" + "\0");
									}else if(boc.getOvers() == 1 && boc.getBalls() >= 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
												CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " OVERS" + "\0");
									}else if(boc.getOvers() > 1 && boc.getBalls() >= 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
												CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " OVERS" + "\0");
									}
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
//											CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " Overs" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
									
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBugPowerPLay(PrintWriter print_writer, String viz_scene,int whichInning,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "POWERPLAY" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
					
					if(whichInning == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
								match.getMatch().getInning().get(0).getBatting_team().getTeamName3().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
								match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + CricketFunctions.
							getPowerPlayScore(inn, whichInning, "-", match) + "\0");
	
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714\0");
					TimeUnit.MILLISECONDS.sleep(1000);
				}
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateBugToss(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
				
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
							match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + 
							match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
							" WON THE TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
							match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + 
							match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
							" WON THE TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + "\0");
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714\0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBugHighlight(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Powerplay's inning is null";
		} else {
			
			String Value = "";
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "HIGHLIGHTS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
					match.getMatch().getInning().get(whichInning-1).getBatting_team().getTeamName3() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
			
			
			if (match.getMatch().getInning().get(whichInning-1).getTotalWickets() >= 10) {
				Value = String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalRuns());
			} else {
				Value = String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalRuns()) + " - " + 
						String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalWickets());
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + Value + " (" +
					CricketFunctions.OverBalls(match.getMatch().getInning().get(whichInning-1).getTotalOvers(),
							match.getMatch().getInning().get(whichInning-1).getTotalBalls()) + ")" + "\0");
		}
		this.status = CricketUtil.SUCCESSFUL;
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
		TimeUnit.MILLISECONDS.sleep(500);		
	}
	public void populateBugMultipartnership(PrintWriter print_writer, String viz_scene,int whichinning, int partnership, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership inning is null";
		} else {
			for (Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == whichinning) {
					String Left_Batsman ="",Right_Batsman="";
					
					Left_Batsman = inn.getPartnerships().get(partnership - 1).getFirstPlayer().getTicker_name();
					Right_Batsman = inn.getPartnerships().get(partnership - 1).getSecondPlayer().getTicker_name();
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "PARTNERSHIP" + "\0");
					
					if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "st WICKET" + "\0");
						
					}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 2) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "nd WICKET" + "\0");
						
					}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 3) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "rd WICKET" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "th WICKET" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + inn.getPartnerships().get(partnership - 1).getTotalRuns() + 
							" (" + inn.getPartnerships().get(partnership - 1).getTotalBalls() + ")" + "\0");
					
					
					for(BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId() == inn.getPartnerships().get(partnership - 1).getFirstBatterNo()) {
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + Left_Batsman + " " + 
											inn.getPartnerships().get(partnership - 1).getFirstBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getFirstBatterBalls() + ")" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + Left_Batsman + " " + 
											inn.getPartnerships().get(partnership - 1).getFirstBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getFirstBatterBalls() + ")" + "\0");
								}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + Left_Batsman + " " + 
										inn.getPartnerships().get(partnership - 1).getFirstBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getFirstBatterBalls() + ")" + "\0");
							}
						}
						
						if(bc.getPlayerId() == inn.getPartnerships().get(partnership - 1).getSecondBatterNo()) {
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + Right_Batsman + "  " + 
											inn.getPartnerships().get(partnership - 1).getSecondBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getSecondBatterBalls() + ")" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + Right_Batsman + "  " + 
											inn.getPartnerships().get(partnership - 1).getSecondBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getSecondBatterBalls() + ")" + "\0");
								}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + Right_Batsman + "  " + 
										inn.getPartnerships().get(partnership - 1).getSecondBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getSecondBatterBalls() + ")" + "\0");
							}
						}
					}
				}
			}
		}
		this.status = CricketUtil.SUCCESSFUL;
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
		TimeUnit.MILLISECONDS.sleep(500);
	}
	public void populateBugPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					String Left_Batsman ="",Right_Batsman="";
					
					Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getTicker_name();
					Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getTicker_name();
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "CURRENT" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "PARTNERSHIP" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns()+"*" 
							+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + "\0");
					
					
					for(BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
											Left_Batsman + " " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() 
											+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ")" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
											Left_Batsman + " " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns()
											+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ")" + "\0");
								}
							}
						}
						
						if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + 
											Right_Batsman + "  " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + 
											" (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ")" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + 
											Right_Batsman + "  " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns()
											+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ")" + "\0");
								}
							}
						}
					}
				}
			}
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000\0");
			TimeUnit.MILLISECONDS.sleep(500);
		}
	}
	public void populateBugsDB(PrintWriter print_writer,String viz_scene, Bugs bug ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");

				if(bug.getText1() != null && bug.getText2() != null && bug.getText3() != null && bug.getText4() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bug.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText4() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bug.getText3() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
				}else if(bug.getText1() != null && bug.getText2() != null && bug.getText3() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bug.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText3() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");

				}else if(bug.getText1() != null && bug.getText2() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bug.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");

				}else if(bug.getText1() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bug.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");

				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				TimeUnit.MILLISECONDS.sleep(500);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public void populateBatsmanVsAllBowlers(PrintWriter print_writer, String viz_scene, int whichInning, int batter_id, MatchAllData match, CricketService cricketService, String broadcaster) {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: bat vs all bowler card inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0;
			List<Player> playerList = cricketService.getAllPlayer();
			List<Team> teamList = cricketService.getTeams();
			
			Player player = playerList.stream().filter(plyr -> plyr.getPlayerId() == batter_id).findAny().orElse(null);
			Team team = teamList.stream().filter(tm->tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
			
			ArrayList<BestStats> batter_data = CricketFunctions.getBatsmanRunsVsAllBowlers(batter_id, whichInning, playerList, match);
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$TopPart$LogoMotion*ACTIVE SET 0 \0");
			for(int i=0; i<2; i++) {
				for(BattingCard bc : match.getMatch().getInning().get(i).getBattingCard()) {
					if(bc.getPlayerId() == batter_id) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + player.getTicker_name() +"  "+
								bc.getRuns()+" ("+bc.getBalls()+")" + "\0");
					}
				}
			}
			
			int maxRuns = 0, minBalls = 0, omo_num = 0;
			String cont_name = "";
			for(BestStats bs : batter_data) {
				if(bs.getRuns()>maxRuns) {
					maxRuns = bs.getRuns();
					minBalls = bs.getBalls();
				}else if(bs.getRuns() == maxRuns) {
					if(minBalls>bs.getBalls()) {
						minBalls = bs.getBalls();
					}
				}
			}
			print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$MiniBase$Star_Legend*ACTIVE SET 0"+"\0");
			for(BestStats bs : batter_data) {
				row_id++;
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAni$Star*ACTIVE SET 0"+"\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
						"$RowAni$BatsmanIcon*ACTIVE SET 0\0");
				
				if(bs.getRuns() == maxRuns && bs.getBalls() == minBalls) {
					omo_num = 2;
					cont_name = "$Highlight";
				}else {
					omo_num = 1;
					cont_name = "$Dehighlight";
				}
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
						"$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
						"$RowAni"+cont_name+"$BatsmanName*GEOM*TEXT SET " + "v  "+bs.getPlayer().getTicker_name() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
						"$RowAni$RowOmo"+cont_name+"$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bs.getRuns() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
						"$RowAni$RowOmo"+cont_name+"$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + bs.getBalls() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batter_data.size() + "\0");
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.500\0");
		}
	}
	
	public void populatePointers(PrintWriter print_writer,String viz_scene, Pointers Pt ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");

				if(Pt.getText1() != null && Pt.getText2() != null && Pt.getText3() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-TeamName" + " SET " + Pt.getHeader() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-FIRST-POINTER" + " SET " + Pt.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "005-2nd-POINTER" + " SET " + Pt.getText2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "006-3RD-POINTER" + " SET " + Pt.getText3() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Data$1st*ACTIVE SET 1 \0");	
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Data$2nd*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Data$3rd*ACTIVE SET 1 \0");

				}else if(Pt.getText1() != null && Pt.getText2() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-TeamName" + " SET " + Pt.getHeader() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-FIRST-POINTER" + " SET " + Pt.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "005-2nd-POINTER" + " SET " + Pt.getText2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "006-3RD-POINTER" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Data$1st*ACTIVE SET 1 \0");	
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Data$2nd*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Data$3rd*ACTIVE SET 0 \0");
					
				}else if(Pt.getText1() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-TeamName" + " SET " + Pt.getHeader() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-FIRST-POINTER" + " SET " + Pt.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "005-2nd-POINTER" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "006-3RD-POINTER" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Data$1st*ACTIVE SET 1 \0");	
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Data$2nd*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Data$3rd*ACTIVE SET 0 \0");
					
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(800);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateFFPointers(PrintWriter print_writer,String viz_scene, Pointers Pt ,MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				
				if(Pt.getTeam() != null && Pt.getPlayer() != null) {
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Image" + " SET " + photo_path 
								+ Pt.getTeam() + "\\\\\\" + Pt.getPlayer() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\AFGHANISTAN_T20\\Photos\\" + Pt.getTeam() + "\\" + Pt.getPlayer() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Image" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path  
								+ Pt.getTeam() + "\\\\" + Pt.getPlayer() + CricketUtil.PNG_EXTENSION + "\0");
					}
				}else if(Pt.getTeam() != null){
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Image" + " SET " + logo_path + Pt.getTeam() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Image" + " SET " + logo_path + "TLogo05" + "\0");
				}
				
				

				if(Pt.getText1() != null && Pt.getText2() != null && Pt.getText3() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-HEADER" + " SET " + Pt.getHeader() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-FIRST-POINTER" + " SET " + Pt.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "005-2ND-POINTER" + " SET " + Pt.getText2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "006-3RD-POINTER" + " SET " + Pt.getText3() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$noname$Data$fIRST*ACTIVE SET 1 \0");	
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$noname$Data$Second*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$noname$Data$Third*ACTIVE SET 1 \0");	

				}else if(Pt.getText1() != null && Pt.getText2() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-HEADER" + " SET " + Pt.getHeader() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-FIRST-POINTER" + " SET " + Pt.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "005-2ND-POINTER" + " SET " + Pt.getText2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "006-3RD-POINTER" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$noname$Data$fIRST*ACTIVE SET 1 \0");	
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$noname$Data$Second*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$noname$Data$Third*ACTIVE SET 0 \0");	
					
				}else if(Pt.getText1() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-HEADER" + " SET " + Pt.getHeader() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-FIRST-POINTER" + " SET " + Pt.getText1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "005-2ND-POINTER" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "006-3RD-POINTER" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$noname$Data$fIRST*ACTIVE SET 1 \0");	
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$noname$Data$Second*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$noname$Data$Third*ACTIVE SET 0 \0");	
					
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(800);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public void populateHowout(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, playerId).equalsIgnoreCase(CricketUtil.YES)) {
					 print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 1\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 0\0");

				}
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");							
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path 
														+ inn.getBatting_team().getTeamBadge() + "\0");								
	
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name().toUpperCase() + "\0");	
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									if (bc.getHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + "retired hurt" + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
									} else if (bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + "absent hurt" + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
									}
								}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									if (bc.getHowOutText() == null || bc.getHowOutText().trim().equalsIgnoreCase("")){
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
									}else {
										if(bc.getHowOut()!=null && bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
											if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + 
														bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											}
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
										}else if(bc.getHowOut()!=null && bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
											if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + 
														bc.getHowOutPartOne().replace("(SUB)", "") + " " + bc.getHowOutPartTwo() + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											}
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns()  + "\0");
										}
									}
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatHead01*GEOM*TEXT SET " + "4s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatValue01*GEOM*TEXT SET " + bc.getFours() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatHead02*GEOM*TEXT SET " + "6s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatValue02*GEOM*TEXT SET " + bc.getSixes() + "\0");
								if(bc.getStrikeRate() == null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + "-" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + "\0");
								}
							}
						}
					}
				}
							
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateQuickHowout(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");							
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path 
												+ inn.getBatting_team().getTeamBadge() + "\0");
						for(BattingCard bc : inn.getBattingCard()) {
							if(inn.getFallsOfWickets().size() > 0) {
								if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
										 print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 1\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 0\0");

									}
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name().toUpperCase() + "\0");								
																		
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Balls*GEOM*TEXT SET "+ bc.getBalls() + "\0");
									
									if (bc.getHowOutText().trim().equalsIgnoreCase("")){
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + " " + "\0");
									}else {
										if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
											if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + 
														bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											}
										}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
											if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + 
														bc.getHowOutPartOne().replace("(SUB)", "") + " " + bc.getHowOutPartTwo() + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											}
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										}
									}
									
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatHead01*GEOM*TEXT SET " + "4s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatValue01*GEOM*TEXT SET " + bc.getFours() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatHead02*GEOM*TEXT SET " + "6s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatValue02*GEOM*TEXT SET " + bc.getSixes() + "\0");
									if(bc.getStrikeRate() == null) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + "-" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + "\0");
									}
								}
							}
						}
					}
				}
							
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateHowoutWithoutFielder(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, playerId).equalsIgnoreCase(CricketUtil.YES)) {
					 print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 1\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 0\0");

				}
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path 
										+ inn.getBatting_team().getTeamBadge() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name().toUpperCase() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Balls*GEOM*TEXT SET " + ( bc.getBalls() + 1 ) + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatHead01*GEOM*TEXT SET " + "4s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatValue01*GEOM*TEXT SET " + bc.getFours() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatHead02*GEOM*TEXT SET " + "6s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatValue02*GEOM*TEXT SET " + bc.getSixes() + "\0");
								if(bc.getStrikeRate() == null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + "-" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(bc.getRuns(), (bc.getBalls()+1), 0) + "\0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + " " + "\0");
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}	
	public void populateBatsmanstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, playerId).equalsIgnoreCase(CricketUtil.YES)) {
					 print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 1\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 0\0");

				}
				int total_inn = 0;
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");								
	
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
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + bc.getPlayer().getFull_name().toUpperCase() + "\0");
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + bc.getRuns() + "*" + "\0");
									}
									else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + bc.getRuns() + "\0");
									}
									
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, playerId).equalsIgnoreCase(CricketUtil.YES)) {
										 print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Impact*ACTIVE SET 1\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Impact*ACTIVE SET 0\0");

									}
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + bc.getBalls() + "\0");
	
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path 
											+ inn.getBatting_team().getTeamBadge() + "\0");
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourHead" + " SET " + "4s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourValue" + " SET " + bc.getFours() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixHead" + " SET " + "6s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixValue" + " SET " + bc.getSixes() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSRHead" + " SET " + "S/R" + "\0");
									if(bc.getStrikeRate() == null) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSRValue" + " SET "+ "-" + "\0");	
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSRValue" + " SET "+ 
												CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0)+ "\0");
									}
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}	
	public void populateBowlerstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId,List<Team> team, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				int total_inn = 0;
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, playerId).equalsIgnoreCase(CricketUtil.YES)) {
					 print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 1\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 0\0");

				}
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + " " + "\0");
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BOWLER:
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==playerId) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + 
											boc.getPlayer().getFull_name().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + 
											boc.getPlayer().getFull_name().toUpperCase() + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + " " + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + " " + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path 
											+ inn.getBowling_team().getTeamBadge() + "\0");
									
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, playerId).equalsIgnoreCase(CricketUtil.YES)) {
										 print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Impact*ACTIVE SET 1\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Impact*ACTIVE SET 0\0");

									}
									
									if(match.getSetup().getMatchType().toUpperCase().equalsIgnoreCase(CricketUtil.DT20) || 
											match.getSetup().getMatchType().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + 
											"DOT" + CricketFunctions.Plural(boc.getDots()).toUpperCase() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + boc.getDots() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "MAIDEN" + 
												CricketFunctions.Plural(boc.getMaidens()).toUpperCase() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + boc.getMaidens() + "\0");
									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + 
											CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + boc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + boc.getWickets() + "\0");
									if(boc.getEconomyRate() == null) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5" + " SET " + "-" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5" + " SET " + boc.getEconomyRate() + "\0");
									}
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
		
	public void populateNameSuper(PrintWriter print_writer,String viz_scene, NameSuper ns ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");
				if(ns.getSponsor() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
							+ ns.getSponsor() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
							+ "TLogo05" + "\0");
				}
				
				if(ns.getFirstname() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + ns.getSurname() + "\0");
				}
				else if(ns.getSurname() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + ns.getFirstname() + "\0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + ns.getFirstname()
							+ " " + ns.getSurname() + "\0");
							
				}
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + ns.getSubLine() + "\0");
					
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}	
	public void populateNameSuperPlayer(PrintWriter print_writer,String viz_scene, String captainWicketKeeper, int playerId,List<Player> Plyrs,
			MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				String Home_or_Away="";
				Player player;
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");
				
				player = Plyrs.stream().filter(plyr -> plyr.getPlayerId() == playerId).findAny().orElse(null);
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + player.getFull_name().toUpperCase() + "\0");
				
				if(player.getTeamId() == match.getSetup().getHomeTeamId()) {
					Home_or_Away = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path +
							match.getSetup().getHomeTeam().getTeamBadge() + "\0");
				}
				else {
					Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path +
							match.getSetup().getAwayTeam().getTeamBadge() + "\0");
				}
				
				System.out.println("CATAIN OR : "+captainWicketKeeper.toUpperCase());
				
				switch(captainWicketKeeper.toUpperCase())
				{
				case CricketUtil.CAPTAIN:
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + " - " + Home_or_Away + "\0");
					break;
				case CricketUtil.WICKET_KEEPER:
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "WICKET-KEEPER" + " - " + Home_or_Away + "\0");
					break;
				case "PLAYER OF THE MATCH":
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "PLAYER OF THE MATCH" + "\0");
					break;
				case "PLAYER OF THE TOURNAMENT":
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "PLAYER OF THE TOURNAMENT" + "\0");
					break;
				case "PLAYER OF THE SERIES":
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "PLAYER OF THE SERIES" + "\0");
					break;
				case CricketUtil.PLAYER:
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + Home_or_Away + "\0");
					break;
				case "CAPTAIN-WICKETKEEPER":
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "CAPTAIN & WICKET-KEEPER" + " - " + Home_or_Away + "\0");
					break;
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(500);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String TypeofProfile,String Profile,String Value,Statistics stats, MatchAllData match, String session_selected_broadcaster,
			CricketService cricketservice, Configuration config) throws InterruptedException 
	{
		double strike_rate = 0,avg = 0;
		int dismissal_count=0;		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSponsor" + " SET " + sponsor_path  + "ShariefBhai" + "\0");
	
			Player plyr = cricketservice.getAllPlayer().stream().filter(player -> player.getPlayerId() == playerId).findAny().orElse(null);
			Team team = cricketservice.getTeams().stream().filter(tm -> tm.getTeamId() == plyr.getTeamId()).findAny().orElse(null);
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
					+ team.getTeamBadge() + "\0");
			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + plyr.getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " +plyr.getFirstname().toUpperCase() + "\0");
			}
			if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + photo_path + 
						team.getTeamName4() + "\\\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "\\\\\\\\" + 
						config.getPrimaryIpAddress() + local_photo_path + team.getTeamName4() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
			}
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");
				
				if(Value.equalsIgnoreCase("SR")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
					if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + "-" + "\0");
					}else {
						strike_rate = stats.getRuns() * 100;
						strike_rate = strike_rate/stats.getBallsFaced();
						DecimalFormat df = new DecimalFormat("0.0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(stats.getRuns(), stats.getBallsFaced(), 0) + "\0");
					}
				}else if(Value.equalsIgnoreCase("AVG")) {
					
					DecimalFormat df_avg = new DecimalFormat("#.00");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "AVERAGE" + "\0");
					if(stats.getRuns()== 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + "-" + "\0");
					}else {
						dismissal_count = (stats.getInnings() - stats.getNotOut());
						avg = (stats.getRuns()/(double)dismissal_count);
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + df_avg.format(avg) + "\0");
					}
					
				}
				break;
			case CricketUtil.BOWLER:
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + CricketFunctions.getEconomy(stats.getRunsConceded(), stats.getBallsBowled(), 2, slashOrDash) + "\0");
				break;
			}
			if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), 1, playerId).equalsIgnoreCase(CricketUtil.YES)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Impact*ACTIVE SET 1 " + "\0");
			}else if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), 2, playerId).equalsIgnoreCase(CricketUtil.YES)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Impact*ACTIVE SET 1 " + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Impact*ACTIVE SET 0 " + "\0");
			}
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "T20I" + " CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("OD")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "LIST A" + " CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("IPL")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "IPL CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("DT20")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "T20" + " CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
	
			}
			break;
		}
		
	}
	public void populateLTPlayerProfile(PrintWriter print_writer,String viz_scene,int playerId,String Profile,String Value,String TypeofProfile,Statistics stats, MatchAllData match, String session_selected_broadcaster,
			CricketService cricketservice,Configuration config, Integer whichInning) throws InterruptedException 
	{
		double strike_rate = 0,avg = 0;
		int dismissal_count=0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, playerId).equalsIgnoreCase(CricketUtil.YES)) {
					 print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 1\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 0\0");

				}
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSponsor" + " SET " + sponsor_path  + "ShariefBhai" + "\0");
	
				Player plyr = cricketservice.getAllPlayer().stream().filter(player -> player.getPlayerId() == playerId).findAny().orElse(null);				
				if(plyr!=null && plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path 
							+ match.getSetup().getHomeTeam().getTeamBadge() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + plyr.getFull_name().toUpperCase() + "\0");	
	
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path 
							+ match.getSetup().getAwayTeam().getTeamBadge() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + plyr.getFull_name().toUpperCase() + "\0");	
	
				}
				switch(TypeofProfile.toUpperCase()) {
				case CricketUtil.BATSMAN:					
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
		
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "50s/100s" + "\0");
						if(stats.getFifties() == null &&  stats.getHundreds() == null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "0/0" + "\0");
							
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + stats.getFifties()+"/"+stats.getHundreds() + "\0");						
						}
						
						if(Value.equalsIgnoreCase("SR")) {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "STRIKE RATE" + "\0");
							if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + CricketFunctions.generateStrikeRate(stats.getRuns(), stats.getBallsFaced(), 0) + "\0");
							}
						}else if(Value.equalsIgnoreCase("AVG")) {
							DecimalFormat df_avg = new DecimalFormat("#.00");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "AVERAGE" + "\0");
							if(stats.getRuns()== 0) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}else {
								dismissal_count = (stats.getInnings() - stats.getNotOut());
								avg = (stats.getRuns()/(double)dismissal_count);
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + df_avg.format(avg) + "\0");
							}
						}
					break;
				case CricketUtil.BOWLER:
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "BEST" + "\0");
						if(stats.getBestFigures() == "0") {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + stats.getBestFigures() + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "ECONOMY" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + CricketFunctions.getEconomy(stats.getRunsConceded(), stats.getBallsBowled(), 2, slashOrDash) + "\0");
					
					break;
				}
				if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "T20I" + " CAREER" + "\0");
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("OD")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "LIST A" + " CAREER" + "\0");
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("IPL")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "IPL CAREER" + "\0");
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("DT20")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "T20" + " CAREER" + "\0");
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("ODI")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "SEASON 1" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
				}
				
				if(Profile.equalsIgnoreCase("AFGHANISTAN_T20_ALL")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "AFGHANISTAN TROPHY CAREER" + "\0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;
	
			}
			break;
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
	public void populateDoubleteams(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$Dream11Logo*ACTIVE SET " + "0" + "\0");
				
				String cont = "";
				int row_id = 0, omo = 0;
				for(int i = 1; i <= 2 ; i++) {
					if(i == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$noname$TeamName*GEOM*TEXT SET " + " " + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$noname$TeamName*GEOM*TEXT SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSponsor" + " SET " + sponsor_path 
								+ "Fancode" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge1" + " SET " + logo_path 
								+ match.getSetup().getHomeTeam().getTeamBadge() + "\0");
											
						for(Player hs : match.getSetup().getHomeSquad()) {
							row_id = row_id + 1;
							omo = 0;
							cont = "Dehighlight";
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
	
							if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name().toUpperCase() + " (C) " + " \0");
							}
							else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name().toUpperCase() + " (WK) " + " \0");
							}
							else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name().toUpperCase() + " (C & WK) " + " \0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name().toUpperCase() + " \0");
							}
							
						}
						for(int j=7; j<=11; j++) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$HomeTeamSubs$Row" + j + "*ACTIVE SET 0" + " \0");
						}
						int row_num =7;
						for(Player hsub : match.getSetup().getHomeSubstitutes()) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$HomeTeamSubs$Row" + row_num + "*ACTIVE SET 1" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$HomeTeamSubs$Row" + row_num + "$BatsmanName*GEOM*TEXT SET " + hsub.getFull_name() +" \0");
							
							row_num++;
						}
					} else {
						row_id = 0;
						
						/*print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$noname$TeamName*GEOM*TEXT SET " + 
								match.getAwayTeam().getTeamName1().toUpperCase() + "\0");*/
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge2" + " SET " + logo_path 
								+ match.getSetup().getAwayTeam().getTeamBadge() + "\0");
						/*print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$TeamNameGrp$RowAni$Highlight$Rectangle*TEXTURE*IMAGE SET " + 
								logo_path + match.getAwayTeam().getTeamName4() + ".png" + "\0");*/
						
						for(Player as : match.getSetup().getAwaySquad()) {
							row_id = row_id + 1;
							omo = 0;
							cont = "Dehighlight";
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
	
							if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name().toUpperCase() + " (C) " + " \0");
							}
							else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name().toUpperCase() + " (WK) " + " \0");
							}
							else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name().toUpperCase() + " (C & WK) " + " \0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name().toUpperCase() + " \0");
							}
						}
						for(int j=7; j<=11; j++) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$HomeTeamSubs$Row" + j + "*ACTIVE SET 0" + " \0");
						}
						int row_num =7;
						for(Player hsub : match.getSetup().getAwaySubstitutes()) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$HomeTeamSubs$Row" + row_num + "*ACTIVE SET 1" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$HomeTeamSubs$Row" + row_num + "$BatsmanName*GEOM*TEXT SET " + hsub.getFull_name() +" \0");
							row_num++;
						}
					}
				}
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$botombase$BatsmanScore*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
							" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$botombase$BatsmanScore*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
							" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateSquad(PrintWriter print_writer,String viz_scene, int TeamId,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				int row_id = 0,omo = 0;
				String cont = "";
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$HeaderDesign$MaxSize$HeaderText*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$Dream11Logo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row1$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row2$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row3$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row4$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row5$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row6$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row7$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row8$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row9$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row10$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row11$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*ACTIVE SET "+ "0" + " \0");
				if(TeamId == match.getSetup().getHomeTeamId()) {
					for(int i = 1; i <= 2 ; i++) {
						if(i == 1) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge1" + " SET " + logo_path + 
									match.getSetup().getHomeTeam().getTeamBadge() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$noname$TeamName*GEOM*TEXT SET "+ 
									match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
							
							for(Player hs : match.getSetup().getHomeSquad()) {
								row_id = row_id + 1;
								omo = 0;
								cont = "Dehighlight";
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
	
								if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name().toUpperCase() + " (C) " + " \0");
								}
								else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name().toUpperCase() + " (WK) " + " \0");
								}
								else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name().toUpperCase() + " (C & WK) " + " \0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name().toUpperCase() + " \0");
								}
								
							}
						}else {
							row_id = 0;
	
							for(Player hos : match.getSetup().getHomeOtherSquad()) {
								row_id = row_id + 1;
								omo = 0;
								cont = "Dehighlight";
								
								for(int fow_id = 1; fow_id <= match.getSetup().getHomeOtherSquad().size(); fow_id++) {
									//fow_id = fow_id +1 ;
									//if(fow_id <= match.getHomeOtherSquad().size()) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*ACTIVE SET "+ "1" + " \0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hos.getFull_name().toUpperCase() + " \0");
									//}else {
										//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + fow_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
										//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + fow_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ "" + " \0");
									//}
								}
							}
						}
					}
				}else {
					for(int i = 1; i <= 2 ; i++) {
						if(i == 1) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge1" + " SET " + logo_path + 
									match.getSetup().getAwayTeam().getTeamBadge() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$noname$TeamName*GEOM*TEXT SET "+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " \0");
							
							for(Player as : match.getSetup().getAwaySquad()) {
								row_id = row_id + 1;
								omo = 0;
								cont = "Dehighlight";
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
	
								if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name().toUpperCase() + " (C) " + " \0");
								}
								else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name().toUpperCase() + " (WK) " + " \0");
								}
								else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name().toUpperCase() + " (C & WK) " + " \0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name().toUpperCase() + " \0");
								}
								
							}
						}else {
							row_id = 0;
							
							
							for(Player aos : match.getSetup().getAwayOtherSquad()) {
								row_id = row_id + 1;
								omo = 0;
								cont = "Dehighlight";
								
								for(int fow_id = 1; fow_id <= match.getSetup().getAwayOtherSquad().size(); fow_id++) {
									//fow_id = fow_id +1 ;
									//if(fow_id <= match.getHomeOtherSquad().size()) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*ACTIVE SET "+ "1" + " \0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ aos.getFull_name().toUpperCase() + " \0");
									//}else {
										//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + fow_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
										//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + fow_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ "" + " \0");
									//}
								}
								/*for(int j = 1; j <= 11; j++) {
									//fow_id = fow_id +1 ;
									if(j <= match.getAwayOtherSquad().size()) {
										//j = j + 1 ;
										
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*ACTIVE SET "+ "1" + " \0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ aos.getFull_name().toUpperCase() + " \0");
									}else {
										j = j + 2 ;
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + j + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + j + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ "" + " \0");
									}
								}*/
							}
						}
					}
					
				}
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getSetup().getTournament().toUpperCase() + " \0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	
	public Infobar populateInfobarIdent(Infobar infobar,PrintWriter print_writer,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		
		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + " " + "\0");
		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + 
				logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0");
		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " + 
				logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0");
		
		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName1*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName2*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
		
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			switch(infobar.getIdent_section().toUpperCase()) {
			case "TOSS":
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
							" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
							" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
				}
				ident_on_screen = true;
				break;
			case "VENUE":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getSetup().getVenueName().toUpperCase() + "\0");
				ident_on_screen = true;
				break;
			case "TOURNAMENT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				ident_on_screen = true;
				break;
			case "SUPER_OVER":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET SUPER OVER\0");
				ident_on_screen = true;
				break;	
			case "TARGET":
				if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + match.getSetup().getMaxOvers()*6 + " BALLS" + "\0");
				}else {
					if(Double.valueOf(match.getSetup().getTargetOvers()) == 1) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS" + "\0");
					}
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS (VJD)" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS (DLS)" + "\0");
					}
				}
				
				ident_on_screen = true;
				break;
			case "RESULT":
				if(match.getMatch().getMatchResult() != null) {
					if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + "MATCH TIED" + " \0");
					}
					else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + 
								CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
					}
				}
				else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");					
					if(match.getSetup().getTargetType() != null) {
						if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + 
									CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
						}
					}
				}
				ident_on_screen = true;
				break;
			}
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		return infobar;
		
	}
	public Infobar populateInfobar(Infobar infobar, PrintWriter print_writer, MatchAllData match, CricketService cricketService, String broadcaster, String session_directoryPath) throws InterruptedException 
	{
		switch (broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				populateInfobarTeamScore(false, print_writer, match, broadcaster);
				infobar = processInfobarPowerplay(infobar, print_writer, broadcaster, match);
				infobar = populateInfobarMiddleSection(infobar, false, print_writer, match, broadcaster, null, cricketService, session_directoryPath);
				infobar = populateInfobarBottomRight(infobar,false, print_writer,match, broadcaster);
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		/*case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Infobar's inning is null";
			} else {
				
				
				populateInfobarTeamScore(false, print_writer, match, session_selected_broadcaster);
				populateInfobarTopRight(false, print_writer, TopRightStats, match, session_selected_broadcaster,last_bowler);
				populateInfobarBottomLeft(false, print_writer, BottomLeftStats, match, session_selected_broadcaster);
				populateInfobarBottomRight(false, print_writer, BottomRightStats, match, session_selected_broadcaster,ground);
				populateInfobarTopLeft(false, print_writer, TopLeftStats, match, session_selected_broadcaster, infobar_batsman);
	
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;*/
		}
		
		return infobar;
	}
	public void populateFieldPlotter(PrintWriter print_writer, String viz_scene, MatchAllData match, String broadcaster,
			String fileName) throws InterruptedException, IOException {
		
		FieldersData fielderFormation = new FieldersData();
		fielderFormation = CricketFunctions
				.getFielderFormation(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + fileName);
		if (fielderFormation.getStyle().equalsIgnoreCase("RHB")) {
			print_writer
					.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp1$Geom_Side$SelectSide"
							+ "*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer
					.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp1$Geom_Side$SelectSide"
							+ "$Off*GEOM*TEXT SET " + "OFF" + "\0");
			print_writer
					.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp2$Geom_Side$SelectSide"
							+ "*FUNCTION*Omo*vis_con SET 1 \0");
			print_writer
					.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp2$Geom_Side$SelectSide"
							+ "$Leg*GEOM*TEXT SET " + "LEG" + "\0");
		} else {
			print_writer
					.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp1$Geom_Side$SelectSide"
							+ "*FUNCTION*Omo*vis_con SET 1 \0");
			print_writer
					.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp1$Geom_Side$SelectSide"
							+ "$Leg*GEOM*TEXT SET " + "LEG" + "\0");
			print_writer
					.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp2$Geom_Side$SelectSide"
							+ "*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer
					.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp2$Geom_Side$SelectSide"
							+ "$Off*GEOM*TEXT SET " + "OFF" + "\0");
		}
		for (int i = 0; i <= fielderFormation.getFielders().size() - 1; i++) {
			double ScaleX = 0, ScaleY = 0;
			ScaleX = ((-186) + (341 * ((fielderFormation.getFielders().get(i).getLeftLocation() - 10) / 457.0)));
			ScaleY = ((-186) + (341 * ((fielderFormation.getFielders().get(i).getTopLocation() - 50) / 427.0)))+10;

			 System.out.println("ScaleX  " + i + " = " + ScaleX);
			 System.out.println("ScaleY  " + i + " = " + ScaleY);

			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll" + (i + 1)
					+ "*TRANSFORMATION" + "*POSITION*X SET " + ScaleX + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll" + (i + 1)
					+ "*TRANSFORMATION" + "*POSITION*Z SET " + ScaleY + "\0");
			if (fielderFormation.getFielders().get(i).getFielderhighlight().equalsIgnoreCase("YES")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll"
						+ (i + 1) + "$PositionY$PositionX$SelectPlayer*FUNCTION*Omo*vis_con SET 1 \0");
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll"
						+ (i + 1) + "$PositionY$PositionX$SelectPlayer*FUNCTION*Omo*vis_con SET 0 \0");
			}

		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png Plotter 1.000 \0");
		TimeUnit.MILLISECONDS.sleep(100);
	}
	public void populateInfobarTeamScore(boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster)
	{
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + 
							logo_path + inn.getBatting_team().getTeamBadge() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + 
							logo_path + inn.getBatting_team().getTeamBadge() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + 
							logo_path + inn.getBowling_team().getTeamBadge() + "\0");
					}
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll*ACTIVE SET 1 \0");
	
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$TeamName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$TeamScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					}
					else{
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$TeamScore*GEOM*TEXT SET " + 
								inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
					}
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$TeamOvers*GEOM*TEXT SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
					
					if(match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.DLS) || match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.VJD)) {
				    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + 
				    			match.getSetup().getTargetOvers() + " (" + match.getSetup().getTargetType().toUpperCase() + ")" + "\0");
				    }else if (match.getSetup().getTargetType() == "" && match.getSetup().getTargetOvers() != "") {
						 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + 
								 " (" + match.getSetup().getTargetOvers() + ")" + "\0");
				    }else {
				    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + " " + "\0");
				    }
					
					
				}
			}
			break;
		}
	}
	public Infobar processInfobarPowerplay(Infobar infobar, PrintWriter print_writer, String which_broadcaster, MatchAllData match) {
		
		switch(which_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && match.getSetup().getMaxOvers() == 1){
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn SHOW 0.0 \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPowerPlay" + " SET " + "" + "\0");
			}else {
				if(!CricketFunctions.processPowerPlay(CricketUtil.MINI,match).isEmpty()) {
					 if(infobar.isPowerplay_on_screen() == true) {
						 break;
			         }
			         else {
			        	 infobar.setPowerplay_on_screen(true);
			        	 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START\0");
			        	 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPowerPlay" + " SET " + 
								 "P" + "\0");
			         }
				}else {
					if(infobar.isPowerplay_on_screen() == true) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayOut START \0");
						infobar.setPowerplay_on_screen(false);
			         }
				}
			}
			break;
		}
		
		return infobar;
	}
	public Infobar populateInfobarMiddleSection(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, 
			MatchAllData match, String broadcaster, InfobarStats infobar_stats, CricketService cricketService, String session_directoryPath) throws InterruptedException
	{
		Inning inning = match.getMatch().getInning().stream().filter(inn->inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
		List<BattingCard> current_batsmen = new ArrayList<BattingCard>();
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
			}
		}
		switch (broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
	
			if(is_this_updating == false) {
				if(infobar.getLast_middle_section() != null && !infobar.getLast_middle_section().trim().isEmpty()) {
					switch(infobar.getLast_middle_section().toUpperCase()) {
					case"BATSMAN":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$Batsman1In CONTINUE REVERSE \0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$Batsman2In CONTINUE REVERSE \0");
						break;
					case"EQUATION":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$NeedOut START \0");
						break;
					case "CURRENT_RUN_RATE":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$CurrentRunRateOut START \0");
						break;
					case"REQUIRED_RUN_RATE":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$RequiredRunRateOut START \0");
						break;
					case"FREE_TEXT": case "DLS_TARGET": case "DLS_EQUATION": case "COMMENTATORS": case "INFOBAR_FREE_TEXT":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigOut START \0");
						break;
					case "BALL_SINCE": // Fill this up
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$BallSinceOut START \0");
						break;
					case"PROJECTED_SCORE":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ProjectedOut START \0");
						break;
					case"BOUNDARIES":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$BoundariesOut START \0");
						break;
					case"PARTNERSHIP":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$PartnershipOut START \0");
						break;
					case "TO_WIN":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ToWinOut START \0");
						break;
					case "COMPARISION":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ComparisonOut START \0");
						break;
					case "TIMELINE":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$Timeline4Out START \0");
						break;
					case "INNING_DOT_COUNTER":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$CounterOut START \0");
						break;
					case "INNING_FOURS_COUNTER":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$CounterOut START \0");
						break;
					case "INNING_SIX_COUNTER":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$CounterOut START \0");
						break;
					case "LAST_WICKET": 
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigOut START \0");
						break;
					case "TOSS":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$TossOut START \0");
						break;
					case "EXTRAS":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ExtrasOut START \0");
						break;
					case "LAST_X_BALLS":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$LastXBallsOut START \0");
						break;
					}
					
//					if(infobar.getLast_middle_section().equalsIgnoreCase("BATSMAN")) {
//						
//						switch(infobar.getMiddle_section().toUpperCase()) {
//						case "EXTRAS": case "TOSS": case "BALL_SINCE": case"BOUNDARIES": case"PARTNERSHIP": case"PROJECTED_SCORE":
//						case "LAST_WICKET": case "INNING_SIX_COUNTER":	case "INNING_DOT_COUNTER": case "INNING_FOURS_COUNTER": 
//						case "TIMELINE": case "COMPARISION": case "TO_WIN": case"FREE_TEXT": case "DLS_TARGET": case "DLS_EQUATION":
//						case"EQUATION": case"REQUIRED_RUN_RATE": case "CURRENT_RUN_RATE":
//							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$Base3Out START \0");
//							break;
//						}
//					}else if(infobar.getLast_middle_section().equalsIgnoreCase("EXTRAS") || 
//							infobar.getLast_middle_section().equalsIgnoreCase("BOUNDARIES") ||
//							infobar.getLast_middle_section().equalsIgnoreCase("TOSS") || 
//							infobar.getLast_middle_section().equalsIgnoreCase("BALL_SINCE") ||
//							infobar.getLast_middle_section().equalsIgnoreCase("PARTNERSHIP") || 
//							infobar.getLast_middle_section().equalsIgnoreCase("PROJECTED_SCORE") ||
//							infobar.getLast_middle_section().equalsIgnoreCase("LAST_WICKET") || 
//							infobar.getLast_middle_section().equalsIgnoreCase("INNING_SIX_COUNTER") ||
//							infobar.getLast_middle_section().equalsIgnoreCase("INNING_DOT_COUNTER") || 
//							infobar.getLast_middle_section().equalsIgnoreCase("INNING_FOURS_COUNTER") ||
//							infobar.getLast_middle_section().equalsIgnoreCase("TIMELINE") || 
//							infobar.getLast_middle_section().equalsIgnoreCase("COMPARISION") ||
//							infobar.getLast_middle_section().equalsIgnoreCase("TO_WIN") || 
//							infobar.getLast_middle_section().equalsIgnoreCase("FREE_TEXT") ||
//							infobar.getLast_middle_section().equalsIgnoreCase("DLS_TARGET") || 
//							infobar.getLast_middle_section().equalsIgnoreCase("DLS_EQUATION") ||
//							infobar.getLast_middle_section().equalsIgnoreCase("EQUATION") || 
//							infobar.getLast_middle_section().equalsIgnoreCase("REQUIRED_RUN_RATE") ||
//							infobar.getLast_middle_section().equalsIgnoreCase("CURRENT_RUN_RATE")) {
//						
//						switch(infobar.getMiddle_section().toUpperCase()) {
//						case "BATSMAN":
//							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$Base3In START \0");
//							break;
//						}
//					}
					
					infobar.setLast_middle_section("");
					TimeUnit.MILLISECONDS.sleep(500);
				}
			}
			switch(infobar.getMiddle_section().toUpperCase()) {
			case "COMMENTATORS":
				if(is_this_updating == false) {
					String comm1Name = "",comm2Name = "",comm3Name = "";
					int comm1 = Integer.valueOf(commentatorsID.split(",")[0]);
					int comm2 = Integer.valueOf(commentatorsID.split(",")[1]);
					int comm3 = Integer.valueOf(commentatorsID.split(",")[2]);
					
					for(Commentator comm : cricketService.getCommentator()) {
						if(comm.getCommentatorId() > 0) {
							if(comm.getCommentatorId() == comm1) {
								comm1Name = comm.getCommentatorName();
							}else if(comm.getCommentatorId() == comm2) {
								comm2Name = comm.getCommentatorName();
							}else if(comm.getCommentatorId() == comm3) {
								comm3Name = comm.getCommentatorName();
							}
						}
					}
					
					if(comm1 > 0 && comm2 > 0 && comm3 > 0) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextBigSingle" + " SET " + "COMMENTATORS : "+comm1Name+",  "+comm2Name+",  "+comm3Name + "\0");
					}else if(comm1 > 0 && comm2 > 0 && comm3 == 0) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextBigSingle" + " SET " + "COMMENTATORS : "+comm1Name+",  "+comm2Name + "\0");
					}else if(comm1 > 0 && comm2 == 0 && comm3 == 0) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextBigSingle" + " SET " + "COMMENTATORS : "+comm1Name + "\0");
					}
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn START \0");
				}
				break;
			case "INFOBAR_FREE_TEXT":
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextBigSingle" + " SET " + infobarFreeText + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn START \0");
				}
				break;
			case "LAST_X_BALLS":
				if(is_this_updating == false) {
					List<String> this_data_str = new ArrayList<String>();
					this_data_str.add(CricketFunctions.getlastthirtyballsdata(match, slashOrDash, match.getEventFile().getEvents(), lastXBalls));
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastXBallsRunsHead" + " SET " + "RUN"
							+CricketFunctions.Plural(Integer.valueOf(this_data_str.get(this_data_str.size()-1).split(slashOrDash)[0])).toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastXBallsWicketHead" + " SET " + "WICKET"
							+CricketFunctions.Plural(Integer.valueOf(this_data_str.get(this_data_str.size()-1).split(slashOrDash)[1])).toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastXBallsHead" + " SET " + "LAST "+lastXBalls+ " BALLS :" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastXBallsRuns" + " SET " + this_data_str.get(this_data_str.size()-1).split(slashOrDash)[0] + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastXBallsWicket" + " SET " + this_data_str.get(this_data_str.size()-1).split(slashOrDash)[1] + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$LastXBallsIn START \0");
				}
				break;
			case "EXTRAS":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(inn.getTotalPenalties() > 0) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tExtrasValue" + " SET " + "NB " + inn.getTotalNoBalls() + ", WD " 
									+ inn.getTotalWides() + ", B " + inn.getTotalByes() + ", LB " + inn.getTotalLegByes() + ", PN "+ inn.getTotalPenalties() + "\0");
						}else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tExtrasValue" + " SET " + "NB " + inn.getTotalNoBalls() + ", WD " 
									+ inn.getTotalWides() + ", B " + inn.getTotalByes() + ", LB " + inn.getTotalLegByes() + "\0");
						}
						
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ExtrasIn START \0");
				}
				break;
			case "TOSS":
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$TossIn START \0");
				}
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$TossGrp$TopGrp$TossTeam*GEOM*TEXT SET " + 
							match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$TossGrp$TossResult*GEOM*TEXT SET " + 
							"ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$TossGrp$TopGrp$TossTeam*GEOM*TEXT SET " + 
							match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$TossGrp$TossResult*GEOM*TEXT SET " + 
							"ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
				}
				break;
			case "FREE_TEXT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Double*ACTIVE SET 0 \0");
				if(infobar_stats.getText1() != null && infobar_stats.getText2() != null) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Single*GEOM*TEXT SET " + infobar_stats.getText1() + "  " + infobar_stats.getText2() + "\0");
				}else if(infobar_stats.getText1() != null) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Single*GEOM*TEXT SET " + infobar_stats.getText1() + "\0");				
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Single*GEOM*TEXT SET " + infobar_stats.getText2() + "\0");				
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn START \0");
				}
				break;
			
			case "BATSMAN":
				if(infobar.getLast_batsmen() == null || infobar.getLast_batsmen().size() <= 0) {
					infobar.setLast_batsmen(current_batsmen);
				}
				
				if(current_batsmen != null && current_batsmen.size() >= 1) {
	
					if(infobar.getLast_batsmen() != null && infobar.getLast_batsmen().size() >= 1) {
						if(infobar.getLast_batsmen().get(0).getPlayerId() != current_batsmen.get(0).getPlayerId()) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman1In CONTINUE REVERSE \0");
							TimeUnit.MILLISECONDS.sleep(800);
							if (CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),inning.getInningNumber(), current_batsmen.get(0).getPlayerId())
									.equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$BatsmanAll$BatsmanGrp1$Impact*ACTIVE SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$BatsmanAll$BatsmanGrp1$Impact*ACTIVE SET " + "0" + "\0");
							}
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName1" + " SET " + current_batsmen.get(0).getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore1" + " SET " + current_batsmen.get(0).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall1" + " SET " + current_batsmen.get(0).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman1In START \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman1Highlight SHOW 0.6 \0");
						}else {
							if (CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),inning.getInningNumber(), current_batsmen.get(0).getPlayerId())
									.equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$BatsmanAll$BatsmanGrp1$Impact*ACTIVE SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$BatsmanAll$BatsmanGrp1$Impact*ACTIVE SET " + "0" + "\0");
							}
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName1" + " SET " + current_batsmen.get(0).getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore1" + " SET " + current_batsmen.get(0).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall1" + " SET " + current_batsmen.get(0).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman1Highlight SHOW 0.6 \0");
							if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT) 
									|| current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) { 
								print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman1Dehighlight SHOW 0.5 \0");
							}
						}
						if(infobar.getLast_batsmen().get(1).getPlayerId() != current_batsmen.get(1).getPlayerId()) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman2In CONTINUE REVERSE \0");
							TimeUnit.MILLISECONDS.sleep(800);
							if (CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),inning.getInningNumber(), current_batsmen.get(1).getPlayerId())
									.equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$BatsmanAll$BatsmanGrp2$Impact*ACTIVE SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$BatsmanAll$BatsmanGrp2$Impact*ACTIVE SET " + "0" + "\0");
							}
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName2" + " SET " + current_batsmen.get(1).getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore2" + " SET " + current_batsmen.get(1).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall2" + " SET " + current_batsmen.get(1).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman2In START \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman2Highlight SHOW 0.6 \0");
						}else {
							if (CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),inning.getInningNumber(), current_batsmen.get(1).getPlayerId())
									.equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$BatsmanAll$BatsmanGrp2$Impact*ACTIVE SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$BatsmanAll$BatsmanGrp2$Impact*ACTIVE SET " + "0" + "\0");
							}
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName2" + " SET " + current_batsmen.get(1).getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore2" + " SET " + current_batsmen.get(1).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall2" + " SET " + current_batsmen.get(1).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman2Highlight SHOW 0.6 \0");
							if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT) 
									|| current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) { 
								print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman2Dehighlight SHOW 0.5 \0");
							}
						}
					}
					
					if(current_batsmen.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanPos1$BatsmanGrp1$BatIcon1*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanGrp2$BatIcon2*ACTIVE SET 0 \0");
						}
					}
					if(current_batsmen.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanPos1$BatsmanGrp1$BatIcon1*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanGrp2$BatIcon2*ACTIVE SET 1 \0");
						}	
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman1In START \0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman2In START \0");
				}
				break;
			case "CURRENT_RUN_RATE":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$CurrentRunRateGrp$CurrentRunRateAll$CurrentRunRate*GEOM*TEXT SET " + 
												inn.getRunRate() + "\0");
						//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$CurrentRunRateGrp$LastXAll*ACTIVE SET 0 \0");
					}
				}
				String[] proj_score = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score[i] = CricketFunctions.projectedScore(match).get(i);
		        }
				    
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$CurrentRunRateGrp$LastXAll$LastXBalls*GEOM*TEXT SET " + "PROJECTED SCORE" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$CurrentRunRateGrp$LastXAll$LastXRuns*GEOM*TEXT SET " + proj_score[1] + " @" +"CRR" + "\0");
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$CurrentRunRateIn START \0");
				}
				break;
			case"REQUIRED_RUN_RATE":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$RequiredRunRateGrp$LastXAll$LastXBalls*GEOM*TEXT SET " + "CURRENT RUN RATE" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$RequiredRunRateGrp$LastXAll$LastXRuns*GEOM*TEXT SET " + 
												inn.getRunRate() + "\0");
						//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$CurrentRunRateGrp$LastXAll*ACTIVE SET 0 \0");
					}
				}
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$RequiredRunRateGrp$RequiredRunRateAll$RequiredRunRateText*GEOM*TEXT SET " + 
						"REQUIRED RUN RATE" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$RequiredRunRateGrp$RequiredRunRateAll$RequiredRunRate*GEOM*TEXT SET " + 
						CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$RequiredRunRateGrp$LastXAll*ACTIVE SET 0 \0");
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$RequiredRunRateIn START \0");
				}
				break;
			case "BALL_SINCE":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallSinceValue" + " SET " + CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber()) + "\0");
					}
				}
				
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$BallSinceIn START \0");
				}
				break;
			case"BOUNDARIES":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$BoundariesGrp$RequiredRunRateAll$noname$FoursValue*GEOM*TEXT SET " 
												+ inn.getTotalFours() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$BoundariesGrp$LastXAll$noname$SixValue*GEOM*TEXT SET " 
								+ inn.getTotalSixes() + "\0");
						if(is_this_updating == false) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$BoundariesIn START \0");
						}
						
					}
				}
				break;
			case"PARTNERSHIP":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(inn.getTotalWickets() == 0) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$PartnershipGrp$MaxSize$PartnershipHead*GEOM*TEXT SET " 
									+ (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
						}else if(inn.getTotalWickets() == 1) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$PartnershipGrp$MaxSize$PartnershipHead*GEOM*TEXT SET " 
									+ (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
						}else if(inn.getTotalWickets() == 2) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$PartnershipGrp$MaxSize$PartnershipHead*GEOM*TEXT SET " 
									+ (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
						}else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$PartnershipGrp$MaxSize$PartnershipHead*GEOM*TEXT SET " 
									+ (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
						}
						
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$PartnershipGrp$MaxSize$PartnershipRuns*GEOM*TEXT SET " 
								+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "*" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$PartnershipGrp$MaxSize$PartnershipBalls*GEOM*TEXT SET " 
								+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$PartnershipIn START \0");
				}
				
				break;
			
			case"PROJECTED_SCORE":
				
			    String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
			    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatHead_GRP$StatAll1$StatHead01*GEOM*TEXT SET " 
						+ "PROJECTED" + "\0");
			    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatHead_GRP$StatAll1$StatHead02*GEOM*TEXT SET " 
						+ "SCORES" + "\0");
			    
			    
			    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatValues_GRP$StatValue1$StatValue1A*GEOM*TEXT SET " 
						+ "@" + proj_score_rate[0] +" (CRR)" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " 
						+ proj_score_rate[1] + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatValues_GRP$StatValue2$StatValue1A*GEOM*TEXT SET " 
						+ "@" + proj_score_rate[2] + " RPO" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " 
						+ proj_score_rate[3] + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatValues_GRP$StatValue3$StatValue1A*GEOM*TEXT SET " 
						+ "@" + proj_score_rate[4] + " RPO" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " 
						+ proj_score_rate[5] + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatValues_GRP$StatValue4$StatValue1A*GEOM*TEXT SET " 
						+ "@" + proj_score_rate[6] + " RPO" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ProjectedGrp$Projected$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " 
						+ proj_score_rate[7] + "\0");
				
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ProjectedIn START \0");
				}
				break;
			
			case"EQUATION":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
							if ((CricketFunctions.GetTargetData(match).getRemaningRuns() > 0) && (CricketFunctions.GetTargetData(match).getRemaningBall() > 0) 
						    		&& (CricketFunctions.getWicketsLeft(match,2) > 0)) {
//								print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$NeedIn SHOW 1.000 \0");
//								print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn SHOW 0.000 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$NedGrp*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp*ACTIVE SET 0 \0");
								if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
									if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
										if(match.getMatch().getMatchStatus() != null) {
											if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED" + "\0");
											}
											else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED - " + 
														match.getMatch().getMatchStatus().toUpperCase() + "\0");
											}
											else {
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
														match.getMatch().getMatchStatus().toUpperCase() + "\0");
											}
										}
										
									}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
											|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
										
										if(match.getMatch().getMatchStatus() != null) {
											if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED" + "\0");
											}
											else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED - " + 
														match.getMatch().getMatchStatus().toUpperCase() + "\0");
											}
											else {
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
														match.getMatch().getMatchStatus().toUpperCase() + "\0");
											}
										}
									}
									
									else{
										if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
													CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
													CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + "\0");
										}else {
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
													CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
													CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
										}
										
									}
								}else {
									if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
										if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
											if(match.getMatch().getMatchStatus() != null) {
												if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED" + "\0");
												}
												else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
												}
												else {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
												}
											}
											
										}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
												|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
											
											if(match.getMatch().getMatchStatus() != null) {
												if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED" + "\0");
												}
												else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
												}
												else {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
												}
											}
										}
										
										else{
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
													CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
													CricketFunctions.GetTargetData(match).getRemaningBall() + "\0");									
										}
									}
									else {
										if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
											if(match.getMatch().getMatchStatus() != null) {
												if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED" + "\0");
												}
												else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
												}
												else {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
												}
											}
										}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
												|| match.getMatch().getInning().get(1).getTotalOvers() >= Double.valueOf(match.getSetup().getTargetOvers())) {
											if(match.getMatch().getMatchStatus() != null) {
												if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + "MATCH TIED" + "\0");											
												}
												else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");						
												}
												else {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + "" + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + "" + "\0");
		
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
												}
											}
										}
										else{
											if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
												if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
															CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
															CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + " (VJD)" + "\0");											
												}else {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
															CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
															CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (VJD)" + "\0");
												}
											}
											else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
												if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
															CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
															CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + " (DLS)" + "\0");
												}else {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
															CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
															CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (DLS)" + "\0");
												}
											}
											else {
												if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
																CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
																CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + "\0");
												}else {
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
															CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
													print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
															CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
												}
											}
										}
									}
								}
								
							}
//							else if (CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || CricketFunctions.getWicketsLeft(match,2) <= 0) {
//								print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$NeedIn SHOW 0.000 \0");
//								print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn SHOW 1.000 \0");
//								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$NedGrp*ACTIVE SET 0 \0");
//								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp*ACTIVE SET 1 \0");
//								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$FreeTextBig_Double*GEOM*TEXT SET " + "" + "\0");
//								if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
//									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$FreeTextBig_Single*GEOM*TEXT SET " + 
//											match.getMatch().getInning().get(1).getBowling_team().getTeamName1().toUpperCase() + " WIN BY SUPER OVER" + "\0");
//								}else {
//									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$FreeTextBig_Single*GEOM*TEXT SET " + 
//											CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, broadcaster).toUpperCase() + "\0");
//								}
//							}
						}
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$NeedIn START \0");
				}
				break;
			case "TO_WIN":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$LastXAll*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$ExtrasHead*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$RequiredRunRateAll*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$RequiredRunRateText*GEOM*TEXT SET " + "TO WIN" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$ExtrasHead*GEOM*TEXT SET OFF \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$LastXAll$SixText*GEOM*TEXT SET " 
						+ "BALL"+ CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$RequiredRunRateAll$FoursText*GEOM*TEXT SET " 
						+ "RUN"+ CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + CricketFunctions.GetTargetData(match).getRemaningBall() + "\0");
//				if ((CricketFunctions.GetTargetData(match).getRemaningRuns() > 0) && (CricketFunctions.GetTargetData(match).getRemaningBall() > 0) 
//			    		&& (CricketFunctions.getWicketsLeft(match,2) > 0)) {
//					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ToWinIn SHOW 1.000 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn SHOW 0.000 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$LastXAll*ACTIVE SET 1 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$ExtrasHead*ACTIVE SET 1 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$RequiredRunRateAll*ACTIVE SET 1 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$RequiredRunRateText*GEOM*TEXT SET " + "TO WIN" + "\0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$ExtrasHead*GEOM*TEXT SET OFF \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$LastXAll$SixText*GEOM*TEXT SET " 
//							+ "BALL"+ CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$RequiredRunRateAll$FoursText*GEOM*TEXT SET " 
//							+ "RUN"+ CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + CricketFunctions.GetTargetData(match).getRemaningBall() + "\0");
//				}else if (CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || CricketFunctions.getWicketsLeft(match,2) <= 0) {
//					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ToWinIn SHOW 0.000 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn SHOW 1.000 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$LastXAll*ACTIVE SET 0 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$ExtrasHead*ACTIVE SET 0 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$RequiredRunRateAll*ACTIVE SET 0 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$FreeTextBig_Double*GEOM*TEXT SET " + "" + "\0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$FreeTextBig_Single*GEOM*TEXT SET " + 
//					CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, broadcaster).toUpperCase() + "\0");
//				}
				
	
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ToWinIn START \0");
				}
				break;
			case "COMPARISION":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase("NO")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ComparisonGrp$MaxSize$ComparisonHead*GEOM*TEXT SET " 
								+ "AT THIS STAGE " + match.getMatch().getInning().get(0).getBatting_team().getTeamName3().toUpperCase() + " WERE: " + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ComparisonGrp$MaxSize$ComparisonRuns*GEOM*TEXT SET " 
								+ CricketFunctions.compareInningData(match,"-", 1 , match.getEventFile().getEvents()) + "\0");
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ComparisonIn START \0");
				}
				break;
				
			case CricketUtil.TIMELINE:
				
				String this_ball_data="";
				int ball_count=0;
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						if(((inn.getTotalOvers()*6) + inn.getTotalBalls()) > 21) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " + "21" + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " 
												+ ((inn.getTotalOvers()*6) + inn.getTotalBalls()) + "\0");
						}
							
						if ((match.getEventFile().getEvents() != null) && (match.getEventFile().getEvents().size() > 0)) {
							  for (int i=match.getEventFile().getEvents().size() - 1; i>=0; i--)
							  {  
								
								switch(match.getEventFile().getEvents().get(i).getEventType()) {

								case CricketUtil.LOG_WICKET: 
							    	
							    	if(match.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
								    	  break;
								    }else {
								    	ball_count = ball_count + 1;
								    	if (match.getEventFile().getEvents().get(i).getEventRuns() > 0) {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
													String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns()) + "+W" + "\0");
								      } else {
								    	  print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + "W" + "\0");
								      }
								   }
							      break;
							      
								case CricketUtil.CHANGE_BOWLER: case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
								case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: 
								case CricketUtil.PENALTY: case CricketUtil.LOG_ANY_BALL: //case CricketUtil.LOG_WICKET:
									ball_count = ball_count + 1;
									switch (match.getEventFile().getEvents().get(i).getEventType())
								    {
								    case CricketUtil.CHANGE_BOWLER:
								    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 0 \0");
										break;
								    case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
								    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 1 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
												match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
										break;
								    case CricketUtil.FOUR: case CricketUtil.SIX:
								    	if(match.getEventFile().getEvents().get(i).getEventWasABoundary() != null && 
								    		match.getEventFile().getEvents().get(i).getEventWasABoundary().equalsIgnoreCase(CricketUtil.YES)) {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 2 \0");
								    	}else {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 1 \0");
								    	}
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
												match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
										break;
								    case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY: 
								    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
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
												(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + 
													this_ball_data.toUpperCase() + "\0");
										break;
								    
								    case CricketUtil.LOG_ANY_BALL:
								    	if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
								    		this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "Pn";
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
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
									    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
														+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
									    	}else {
									    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$Ball" 
														+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
//									    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
//														+ ball_count + "*FUNCTION*Omo*vis_con SET 5 \0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
									    	}
								    	}
								    	break;
								    }
									break;
								}
									
							    if(ball_count >= 21) {
							    	break;
							    }
							  }
							}
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$Timeline4In START \0");
					//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$TimelineIn START \0");
					//processAnimation(print_writer, "Section2$TimelineIn", "START", broadcaster);
				}
				break;	
			case "INNING_DOT_COUNTER":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, inn.getInningNumber(), 0, ",", match.getEventFile().getEvents()).split(",");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSponsoredCounterHead" + " SET " + "DOTS" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSponsoredCounterSubHead" + " SET " + "THIS INNINGS" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSponsoredCounterValue" + " SET " + Count[0] + "\0");
	
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$CounterIn START \0");
				}
				break;
			case "INNING_FOURS_COUNTER":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						//String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, inn.getInningNumber(), 0, ",", match.getEvents()).split(",");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSponsoredCounterHead" + " SET " + "FOURS" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSponsoredCounterSubHead" + " SET " + "THIS INNINGS" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSponsoredCounterValue" + " SET " + inn.getTotalFours() + "\0");
	
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$CounterIn START \0");
				}
				break;
			case "INNING_SIX_COUNTER":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						//String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, inn.getInningNumber(), 0, ",", match.getEvents()).split(",");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSponsoredCounterHead" + " SET " + "SIXES" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSponsoredCounterSubHead" + " SET " + "THIS INNINGS" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSponsoredCounterValue" + " SET " + inn.getTotalSixes() + "\0");
	
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$CounterIn START \0");
				}
				break;
			case "DLS_TARGET": case "DLS_EQUATION":
				int runs = 0,total_runs=0;
				String team = "",ahead_behind = "",balls="";
				 
				Document htmlFile = null; 
				try { 
					for(Inning inn : match.getMatch().getInning()) {
						if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
							htmlFile = Jsoup.parse(new File("C:\\Sports\\ParScores BB.html"), "ISO-8859-1");
							balls = CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls());
						}
					}
				} catch (IOException e) {  
					e.printStackTrace(); 
				} 
				
				List<DuckWorthLewis> this_dls = new ArrayList<DuckWorthLewis>();
				for(int i=14; i<htmlFile.body().getElementsByTag("font").size() - 1;i++) {
					if(htmlFile.body().getElementsByTag("font").get(i).text().contains("TableID")) {
						i = i + 15;
						if(i > htmlFile.body().getElementsByTag("font").size()) {
							break;
						}
					}
					
					for(Inning inn : match.getMatch().getInning()) {
						if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
							total_runs = inn.getTotalRuns();
							team = inn.getBatting_team().getTeamName3().toUpperCase();
							this_dls.add(new DuckWorthLewis(htmlFile.body().getElementsByTag("font").get(i).text(),
									htmlFile.body().getElementsByTag("font").get(i+(2+(inn.getTotalWickets()))).text()));
						}
					}
					i = i +11;
					
				}
				
				for(int i = 0; i<= this_dls.size() -1;i++) {
					if(this_dls.get(i).getOver_left().equalsIgnoreCase(balls)) {
						switch(infobar.getMiddle_section().toUpperCase()) {
						case "DLS_TARGET":
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Single"
									+ "*GEOM*TEXT SET " + "" + "\0");				
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Double"
									+ "*GEOM*TEXT SET " + "DLS TARGET AFTER " + balls + " OVERS : " + (Integer.valueOf(this_dls.get(i).getWkts_down()) + 1) + "\0");
							break;
						case "DLS_EQUATION":
							runs = (total_runs) - Integer.valueOf((CricketFunctions.populateDuckWorthLewis(match, session_directoryPath).get(i).getWkts_down()));

	                        if(runs < 0){
	                            ahead_behind = " | " + team + " ARE " + (Math.abs(runs)) + " RUNS BEHIND";
	                        }else if (runs > 0){
	                            ahead_behind = " | " + team + " ARE " + runs + " RUNS AHEAD";
	                        }else if(runs == 0) {
	                        	ahead_behind = "SCORES ARE LEVEL";
	                        }
							
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Single"
									+ "*GEOM*TEXT SET " + "" + "\0");				
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Double"
									+ "*GEOM*TEXT SET " + "DLS PAR SCORE : " + (Integer.valueOf(this_dls.get(i).getWkts_down())) + ahead_behind.toUpperCase() + "\0");
							break;
						}				
					}
				}
				
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn START \0");
				}
				break;
			case "LAST_WICKET":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Single*GEOM*TEXT SET " + "LAST WICKET : " + CricketFunctions.getLastWicket(match) + "\0");				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Double*GEOM*TEXT SET " + "" + "\0");				
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn START \0");
				}
				break;
			}
			break;
		}
		if(current_batsmen != null && current_batsmen.size() >= 2) {
			infobar.setLast_batsmen(current_batsmen);
		}
		infobar.setLast_middle_section(infobar.getMiddle_section().toUpperCase());
		return infobar;
	}
	public Infobar populateInfobarBottomRight(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster) throws InterruptedException
	{
		BowlingCard bowler = null;
		Inning inning = match.getMatch().getInning().stream().filter(inn->inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
		for(Inning inn : match.getMatch().getInning()) {
			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				for(BowlingCard boc : inn.getBowlingCard()) {
					if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") 
							|| boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
						bowler = boc;
					}
				}
			}
		}
		switch (broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if(is_this_updating == false) {
				if(infobar.getLast_bottom_right_section() != null && !infobar.getLast_bottom_right_section().trim().isEmpty()) {
					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case "TARGET":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$TargetOut START \0");
						break;
					case "BOWLER":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$BowlerIn CONTINUE REVERSE \0");
						break;
					case "PROJECTED":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$ProjectedSmallOut START \0");
						break;
					case "PARTNERSHIP":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$PartnershipSmallOut START \0");
						break;
					case "BOWLING_END": case "ECONOMY":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$FreeTextSmallOut START \0");
						break;
					case "LAST_WICKET":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$FreeTextSmallOut START \0");
						break;
					}
					TimeUnit.MILLISECONDS.sleep(500);
				}
			}
			
			switch(infobar.getBottom_right_section().toUpperCase()) {
			case "TARGET":
				if(match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().trim().isEmpty()) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$TargetAll$TargetScoreGrp$Score*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + " (" + match.getSetup().getTargetType().toUpperCase() + ")" + "\0");
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$TargetAll$TargetScoreGrp$Score*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$TargetIn START \0");
				}
				
				break;
			case "BOWLER":
				System.out.println("HELLO");
				if(infobar.getLast_bowler() != null && bowler != null) {
					if(infobar.getLast_bowler().getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$BowlerOut START \0");
						TimeUnit.MILLISECONDS.sleep(1000);
					}
				}
				
				if(bowler != null) {
					if (CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),inning.getInningNumber(), bowler.getPlayerId())
							.equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$Section3$Bowler_Grp$Impact*ACTIVE SET " + "1" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MiniIn$Section3$Bowler_Grp$Impact*ACTIVE SET " + "0" + "\0");
					}
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BowlerPos$BowlerName*GEOM*TEXT SET " + 
											bowler.getPlayer().getTicker_name().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BowlerPos$FigureGrp$Figure*GEOM*TEXT SET " + 
											bowler.getWickets() + "-" + bowler.getRuns() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BowlerPos$FigureGrp$Overs*GEOM*TEXT SET " + 
											CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + "\0");
					
//					if(infobar.getBottom_right_bottom_section() != null) {
//						infobar = populateBottomRightBottom(infobar, true, print_writer, match, broadcaster);
//					}
					
				}
				
				if(infobar.getLast_bowler() != null && bowler != null) {
					if(infobar.getLast_bowler().getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$BowlerIn START \0");
						TimeUnit.MILLISECONDS.sleep(500);
					}
				}
				
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$BowlerIn START \0");
				}
				
				if(infobar.getBottom_right_bottom_section() != null) {
					infobar = populateBottomRightBottom(infobar, true, print_writer, match, broadcaster);
				}
				break;
			case "PROJECTED":
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
				    
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$ProjectedSmall$Score_RR$score*GEOM*TEXT SET " + proj_score_rate[1] + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$ProjectedSmall$Score_RR$CURR*GEOM*TEXT SET " + "@" + proj_score_rate[0] +" (CRR)" + "\0");
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$ProjectedSmallIn START \0");
				}
				
				break;
				
			case "PARTNERSHIP":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$PartnershipSmall$RunsBallsGrp$Runs*GEOM*TEXT SET " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns()+"*" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$PartnershipSmall$RunsBallsGrp$Balls*GEOM*TEXT SET " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$PartnershipSmallIn START \0");
				}
				
				break;
			case "BOWLING_END":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
								if(boc.getBowling_end() == 1) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$FreeTextSmall$FreeTextSmallGrp$FreeTextSmall_Text*GEOM*TEXT SET " + "BOWLING FROM " + match.getSetup().getGround().getFirst_bowling_end() + " END" + "\0");
								}else if(boc.getBowling_end() == 2) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$FreeTextSmall$FreeTextSmallGrp$FreeTextSmall_Text*GEOM*TEXT SET " + "BOWLING FROM " + match.getSetup().getGround().getSecond_bowling_end() + " END" + "\0");
								}
							}else if(boc.getStatus().equalsIgnoreCase(CricketUtil.LAST + CricketUtil.BOWLER)) {
								if(boc.getBowling_end() == 1) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$FreeTextSmall$FreeTextSmallGrp$FreeTextSmall_Text*GEOM*TEXT SET " + "BOWLING FROM " + match.getSetup().getGround().getFirst_bowling_end() + " END" + "\0");
								}else if(boc.getBowling_end() == 2) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$FreeTextSmall$FreeTextSmallGrp$FreeTextSmall_Text*GEOM*TEXT SET " + "BOWLING FROM " + match.getSetup().getGround().getSecond_bowling_end() + " END" + "\0");
								}
							}
						}
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$FreeTextSmallIn START \0");
				}
				break;
			case "ECONOMY":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
								if(boc.getEconomyRate() == null) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$FreeTextSmall$FreeTextSmallGrp$FreeTextSmall_Text*GEOM*TEXT SET " + "ECONOMY " + " - " + "\0");
								}else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$FreeTextSmall$FreeTextSmallGrp$FreeTextSmall_Text*GEOM*TEXT SET " + "ECONOMY " + boc.getEconomyRate() + "\0");
								}
							}
						}
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$FreeTextSmallIn START \0");
				}
				break;	
			case "LAST_WICKET":
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$FreeTextSmallIn START \0");
				}
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$FreeTextSmall$FreeTextSmallGrp$FreeTextSmall_Text*GEOM*TEXT SET " + "LAST WICKET : " + CricketFunctions.getLastWicket(match) + "\0");				
				break;
			}
			
			TimeUnit.MILLISECONDS.sleep(500);
			
			if(bowler != null) {
				infobar.setLast_bowler(bowler);
			}
			infobar.setLast_bottom_right_section(infobar.getBottom_right_section().toUpperCase());
			break;
		}
		return infobar;
	}
	public void populateInfobarDirector(PrintWriter print_writer,String Dir_value,String session_selected_broadcaster) {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			switch (Dir_value.toUpperCase()) {
			case "FOURS":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FoursIn START \0");
				break;
	
			case "SIXES":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SixIn START \0");
				break;
			
			case "WICKETS":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*WicketsIn START \0");
				break;
				
			case "NO_BALL":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*NoBallIn START \0");
				break;
	
			case "FREE-HIT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHit START \0");
				break;
				
			case "WIDE":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*WideIn START \0");
				break;
			}
			break;
		}
	}
	public void populateInfobarPowerPlay(PrintWriter print_writer,String Dir_value,MatchAllData match,String session_selected_broadcaster) {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			switch (Dir_value.toUpperCase()) {
			case "POWERPLAY":
				if(!CricketFunctions.processPowerPlay(CricketUtil.MINI,match).isEmpty()) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPowerPlay" + " SET " + 
							 "P" + "\0");
		        	 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START \0");
				}
//				 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPowerPlay" + " SET " + "P" + "\0");
//				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START \0");
				break;
			}
			break;
		}
	}
	
	public void populateMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,List<VariousText> variousText,
			MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				String match_name="",newDate = "",Date = "",date_data = "";
				
				String[] dateSuffix = {
						"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
						
						"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
						
						"th", "st", "nd", "rd", "th", "th", "th", "th", "th","th",
						
						"th", "st"
				};
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Shriram_Logo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$AllTeamBadges$T10Logo*TEXTURE*IMAGE SET "+ logo_path + "TLogo05" + " \0");
				
				for(Team TM : team) {
					if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName01" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamBadge" + " SET " + logo_path + TM.getTeamBadge() + "\0");
					}
					if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName02" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamBadge" + " SET " + logo_path + TM.getTeamBadge() + "\0");
					}
				}
				
				if(match_number < 10) {
					match_name = "MATCH " + match_number;
				}else {
					match_name = fix.get(match_number - 1).getMatchfilename().toUpperCase();
				}
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET "+ match_name + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Info*GEOM*TEXT SET "+ "LIVE FROM " + fix.get(match_number - 1).getVenue() + " \0");
				
				for(VariousText vt : variousText) {
					if(vt.getVariousType().equalsIgnoreCase("FF_MATCH_PROMO_HEADER") && vt.getUseThis().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + vt.getVariousText() + "\0");
						break;
					}else {
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.DATE, +1);
						if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
							date_data = "TOMORROW - ";
							
						}else {
							cal.add(Calendar.DATE, -1);
							Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
							if(fix.get(match_number-1).getDate().equalsIgnoreCase(Date)) {
								date_data = "UP NEXT - ";
							}else {
								newDate = fix.get(match_number-1).getDate().split("-")[0];
								if(Integer.valueOf(newDate) < 10) {
									newDate = newDate.replaceFirst("0", "");
								}
								date_data = newDate + dateSuffix[Integer.valueOf(newDate)] + " " + 
								Month.of(Integer.valueOf(fix.get(match_number-1).getDate().split("-")[1]));
							}
						}
					}
				}
				
				for(VariousText vt : variousText) {
					if(vt.getVariousType().equalsIgnoreCase("FF_MATCH_PROMO_FOOTER") && vt.getUseThis().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Info*GEOM*TEXT SET "+ vt.getVariousText() + " \0");
						break;
					}else {
						if(fix.get(match_number-1).getLocalTime() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Info*GEOM*TEXT SET " + date_data + " AT " +fix.get(match_number-1).getLocalTime()
									+ " FROM " + fix.get(match_number - 1).getVenue() + " \0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Info*GEOM*TEXT SET " + date_data + " FROM " + fix.get(match_number - 1).getVenue() + " \0");
						}
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 In$BG 1.100\0");
				TimeUnit.MILLISECONDS.sleep(800);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateLtMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				String newDate = "",Date = "";
				
				String[] dateSuffix = {
						"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
						
						"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
						
						"th", "st", "nd", "rd", "th", "th", "th", "th", "th","th",
						
						"th", "st"
				};
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " " + "\0");
				
				for(Team TM : team) {
					if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + TM.getTeamBadge() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + TM.getTeamName2().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + TM.getTeamName3().toUpperCase() + "\0");
					}
					if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + logo_path + TM.getTeamBadge() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + TM.getTeamName2().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + TM.getTeamName3().toUpperCase() + "\0");
					}
				}
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "MATCH " + fix.get(match_number - 1).getMatchnumber() + "\0");
	
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, +1);
				if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					if(fix.get(match_number-1).getLocalTime() != null) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH " + 
								fix.get(match_number - 1).getMatchnumber() + " - " + "TOMORROW - " + fix.get(match_number - 1).getLocalTime() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH " + 
								fix.get(match_number - 1).getMatchnumber() + " - " + "TOMORROW"+ "\0");
					}
					
				}else {
					cal.add(Calendar.DATE, -1);
					Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
					if(fix.get(match_number-1).getDate().equalsIgnoreCase(Date)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "UP NEXT " + "- MATCH " + 
							fix.get(match_number - 1).getMatchnumber() + "\0");
					}else {
						newDate = fix.get(match_number-1).getDate().split("-")[0];
						if(Integer.valueOf(newDate) < 10) {
							newDate = newDate.replaceFirst("0", "");
						}
						if(fix.get(match_number-1).getLocalTime() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH " + fix.get(match_number - 1).getMatchnumber()
									+ " - " + newDate + dateSuffix[Integer.valueOf(newDate)] + " " + 
									Month.of(Integer.valueOf(fix.get(match_number-1).getDate().split("-")[1])) + " - " + fix.get(match_number - 1).getLocalTime() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH " + fix.get(match_number - 1).getMatchnumber()
									+ " - " + newDate + dateSuffix[Integer.valueOf(newDate)] + " " + Month.of(Integer.valueOf(fix.get(match_number-1).getDate().split("-")[1])) + "\0");
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populatePlayOff(PrintWriter print_writer,String viz_sence_path, CricketService cricketService,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, ParseException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "
						+ logo_path + "TLogo" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-Header" + " SET "
						+ "QUALIFIER 1" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-Header" + " SET "
						+ "ELIMINATOR" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-Header" + " SET "
						+ "QUALIFIER 2" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-Header" + " SET "
						+ "THE FINAL" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamA-Alpha" + " SET "
						+ "100" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamB-Alpha" + " SET "
						+ "100" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA-Alpha" + " SET "
						+ "100" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB-Alpha" + " SET "
						+ "100" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Alpha" + " SET "
						+ "100" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Alpha" + " SET "
						+ "100" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Alpha" + " SET "
						+ "100" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Alpha" + " SET "
						+ "100" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamA" + " SET "
						+ cricketService.getPlayOff().get(0).getTeam1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamB" + " SET "
						+ cricketService.getPlayOff().get(0).getTeam2().toUpperCase() + "\0");
		
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA" + " SET "
						+ cricketService.getPlayOff().get(1).getTeam1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB" + " SET "
						+ cricketService.getPlayOff().get(1).getTeam2().toUpperCase() + "\0");
		
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA" + " SET "
						+ cricketService.getPlayOff().get(2).getTeam1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB" + " SET "
						+ cricketService.getPlayOff().get(2).getTeam2().toUpperCase() + "\0");
		
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA" + " SET "
						+ cricketService.getPlayOff().get(3).getTeam1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB" + " SET "
						+ cricketService.getPlayOff().get(3).getTeam2().toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$A$TeamLogo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$B$TeamLogo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_A$TeamLogo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_B$TeamLogo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamA$TeamLogo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamB$TeamLogo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 0 \0");
				
				for (int i = 0; i <= cricketService.getTeams().size() - 1; i++) {
					if (cricketService.getTeams().get(i).getTeamName4().equalsIgnoreCase(cricketService.getPlayOff().get(0).getTeam1())) {
						print_writer.println(
								"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$A$TeamLogo*ACTIVE SET 1 \0");
						print_writer.println(
								"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamA-Logo" + " SET "
										+ logo_path + cricketService.getTeams().get(i).getTeamName4().toUpperCase() + "\0");
					}
					if (cricketService.getTeams().get(i).getTeamName4().equalsIgnoreCase(cricketService.getPlayOff().get(0).getTeam2())) {
						print_writer.println(
								"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$B$TeamLogo*ACTIVE SET 1 \0");
						print_writer.println(
								"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamB-Logo" + " SET "
										+ logo_path + cricketService.getTeams().get(i).getTeamName4().toUpperCase() + "\0");
					}
					if (cricketService.getTeams().get(i).getTeamName4().equalsIgnoreCase(cricketService.getPlayOff().get(1).getTeam1())) {
						print_writer.println(
								"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_A$TeamLogo*ACTIVE SET 1 \0");
						print_writer.println(
								"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-TeamA-Logo" + " SET "
										+ logo_path + cricketService.getTeams().get(i).getTeamName4().toUpperCase() + "\0");
					}
					if (cricketService.getTeams().get(i).getTeamName4().equalsIgnoreCase(cricketService.getPlayOff().get(1).getTeam2())) {
						print_writer.println(
								"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_B$TeamLogo*ACTIVE SET 1 \0");
						print_writer.println(
								"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-TeamB-Logo" + " SET "
										+ logo_path + cricketService.getTeams().get(i).getTeamName4().toUpperCase() + "\0");
					}
					if (cricketService.getTeams().get(i).getTeamName4().equalsIgnoreCase(cricketService.getPlayOff().get(2).getTeam1())) {
						print_writer.println(
								"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamA$TeamLogo*ACTIVE SET 1 \0");
						print_writer.println(
								"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Logo" + " SET "
										+ logo_path + cricketService.getTeams().get(i).getTeamName4().toUpperCase() + "\0");
					}
					if (cricketService.getTeams().get(i).getTeamName4().equalsIgnoreCase(cricketService.getPlayOff().get(2).getTeam2())) {
						print_writer.println(
								"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamB$TeamLogo*ACTIVE SET 1 \0");
						print_writer.println(
								"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Logo" + " SET "
										+ logo_path + cricketService.getTeams().get(i).getTeamName4().toUpperCase() + "\0");
					}
					if (cricketService.getTeams().get(i).getTeamName4().equalsIgnoreCase(cricketService.getPlayOff().get(3).getTeam1())) {
						print_writer.println(
								"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 1 \0");
						print_writer.println(
								"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Logo" + " SET "
										+ logo_path + cricketService.getTeams().get(i).getTeamName4().toUpperCase() + "\0");
					}
					if (cricketService.getTeams().get(i).getTeamName4().equalsIgnoreCase(cricketService.getPlayOff().get(3).getTeam2())) {
						print_writer.println(
								"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 1 \0");
						print_writer.println(
								"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Logo" + " SET "
										+ logo_path + cricketService.getTeams().get(i).getTeamName4().toUpperCase() + "\0");
					}
				}
				
				if (cricketService.getPlayOff().get(0).getWinner() != null) {
					if (cricketService.getPlayOff().get(0).getWinner().equalsIgnoreCase(cricketService.getPlayOff().get(0).getTeam1())) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamB-Alpha"
								+ " SET " + "50" + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamA-Alpha"
								+ " SET " + "50" + "\0");
					}
				}
		
				if (cricketService.getPlayOff().get(1).getWinner() != null) {
					if (cricketService.getPlayOff().get(1).getWinner().equalsIgnoreCase(cricketService.getPlayOff().get(1).getTeam1())) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB-Alpha"
								+ " SET " + "50" + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA-Alpha"
								+ " SET " + "50" + "\0");	
					}
				}
				if (cricketService.getPlayOff().get(2).getWinner() != null) {
					
					if (cricketService.getPlayOff().get(2).getWinner().equalsIgnoreCase(cricketService.getPlayOff().get(2).getTeam1())) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Alpha"
								+ " SET " + "50" + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Alpha"
								+ " SET " + "50" + "\0");
					}
				}
				if (cricketService.getPlayOff().get(3).getWinner() != null) {
					if (cricketService.getPlayOff().get(3).getWinner().equalsIgnoreCase(cricketService.getPlayOff().get(3).getTeam1())) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Alpha"
								+ " SET " + "50" + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Alpha"
								+ " SET " + "50" + "\0");
					}
				}
				print_writer.println(
						"-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.100 In$ManDataIn 0.931 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
		}
		
	}
	public void populateMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$MatchId*GEOM*TEXT SET "+ " " + " \0");
	
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Shriram_Logo*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$AllTeamBadges$T10Logo*TEXTURE*IMAGE SET "+ logo_path + "TLogo05" + " \0");
	
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName01" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName02" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamBadge" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamBadge" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0");
	
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchId" + " SET " + match.getSetup().getMatchIdent() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$Info*GEOM*TEXT SET "+ "LIVE FROM "+ match.getSetup().getVenueName().toUpperCase() + " \0");
	
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 \0");
				TimeUnit.MILLISECONDS.sleep(500);
	
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateLTMatchId(PrintWriter print_writer,String viz_scene,List<VariousText> varioustext,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HomeTeamName$HomeTeamFirstName*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path 
						+ match.getSetup().getHomeTeam().getTeamBadge() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + logo_path 
						+ match.getSetup().getAwayTeam().getTeamBadge() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
				for(VariousText vt : varioustext) {
					System.out.println(vt.getVariousType() +" "+vt.getUseThis());
					if(vt.getVariousType().equalsIgnoreCase("MATCHIDFOOTER") && vt.getUseThis().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET "+ vt.getVariousText() + " \0");
						break;
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET "+ "LIVE FROM " + 
								match.getSetup().getVenueName().toUpperCase() + " \0");
					}
				}
//				VariousText various = varioustext.stream().filter(vt->vt.getVariousType().equalsIgnoreCase("LT_MATCH_ID") 
//							&& vt.getUseThis().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
//				if(various!=null) {
//					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET "+ various.getVariousText() + " \0");
//				}else {
//					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET "+ "LIVE FROM " + 
//							match.getSetup().getVenueName().toUpperCase() + " \0");
//				}
				
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(500);
	
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public void populatePlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,   MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				int row_id = 0;
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$HeaderDesign$MaxSize$HeaderText*GEOM*TEXT SET "+ " " + " \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$1*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$2*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$3*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$4*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$5*ACTIVE SET 0 \0");
				
				//print_writer.println("-1 RENDERER*TREE*$Main$All$Data$Sponsor*ACTIVE SET 0 \0");
				if(TeamId == match.getSetup().getHomeTeamId()) {
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo05" + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
							match.getSetup().getHomeTeam().getTeamBadge() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Data$HeaderDesign$MaxSize$HeaderText*GEOM*TEXT SET "+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
	
					for(Player hs : match.getSetup().getHomeSquad()) {
						row_id = row_id + 1;
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + photo_path + 
									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path  + 
									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
						}
						
//						print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ hs.getTicker_name().toUpperCase() + " \0");
						
						TimeUnit.MICROSECONDS.sleep(200);
						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ hs.getTicker_name().toUpperCase() + " (C)" + " \0");
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRole0" + row_id + " SET " + "2" + "\0");
//							if(hs.getRole().equalsIgnoreCase(CricketUtil.BATSMAN) || hs.getRole().equalsIgnoreCase("Bat/Keeper")) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "BatsmanIcon" + " \0");
//							}else if(hs.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "BowlerIcon" + " \0");
//							}else if(hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "AllRounderIcon" + " \0");
//							}
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ hs.getTicker_name().toUpperCase() + " (WK) " + " \0");

//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRole0" + row_id + " SET " + "1" + "\0");
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "WicketKeeperIcon" + " \0");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ hs.getTicker_name().toUpperCase() + " (C & WK)" + " \0");

//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRole0" + row_id + " SET " + "2" + "\0");
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "WicketKeeperIcon" + " \0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ hs.getTicker_name().toUpperCase() + " \0");

//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRole0" + row_id + " SET " + "1" + "\0");
//							if(hs.getRole().equalsIgnoreCase(CricketUtil.BATSMAN) || hs.getRole().equalsIgnoreCase("Bat/Keeper")) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "BatsmanIcon" + " \0");
//							}else if(hs.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "BowlerIcon" + " \0");
//							}else if(hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "AllRounderIcon" + " \0");
//							}
						}
						TimeUnit.MICROSECONDS.sleep(300);
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All$Data$subs$Row6*ACTIVE SET 1 \0");
					for(int i=7; i<=11; i++) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$Data$subs$Row" + (i) + "*ACTIVE SET 0 \0");
					}
					int row_num = 0;
					for(Player plyr : match.getSetup().getHomeSubstitutes()) {
						row_num++;
						print_writer.println("-1 RENDERER*TREE*$Main$All$Data$subs$Row" + (row_num+6) + "*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeam1SubsName" + row_num + " SET " + 
							plyr.getTicker_name() + "\0");
					}
//					for(int i=0;i<=match.getSetup().getHomeSubstitutes().size()-1;i++) {
//						print_writer.println("-1 RENDERER*TREE*$Main$All$Data$subs$Row" + (i+7) + "*ACTIVE SET 1 \0");
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeam1SubsName" + (i+1) + " SET " + 
//							match.getSetup().getHomeSubstitutes().get(i).getTicker_name() + "\0");
//					}
				}
				else {
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo05" + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Data$HeaderDesign$MaxSize$HeaderText*GEOM*TEXT SET "+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " \0");
					for(Player as : match.getSetup().getAwaySquad()) {
						row_id = row_id + 1;
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + photo_path + 
									match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + 
									match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
						}
						
//						print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ as.getTicker_name().toUpperCase() + " \0");
						
						TimeUnit.MICROSECONDS.sleep(200);
						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ as.getTicker_name().toUpperCase() + " (C)" + " \0");

//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRole0" + row_id + " SET " + "2" + "\0");
//							if(as.getRole().equalsIgnoreCase(CricketUtil.BATSMAN) || as.getRole().equalsIgnoreCase("Bat/Keeper")) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "BatsmanIcon" + " \0");
//							}else if(as.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "BowlerIcon" + " \0");
//							}else if(as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "AllRounderIcon" + " \0");
//							}
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ as.getTicker_name().toUpperCase() + " (WK)" + " \0");

//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRole0" + row_id + " SET " + "1" + "\0");
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "WicketKeeperIcon" + " \0");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ as.getTicker_name().toUpperCase() + " (C & WK)" + " \0");

//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRole0" + row_id + " SET " + "2" + "\0");
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "WicketKeeperIcon" + " \0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ as.getTicker_name().toUpperCase() + " \0");

//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRole0" + row_id + " SET " + "1" + "\0");
//							if(as.getRole().equalsIgnoreCase(CricketUtil.BATSMAN) || as.getRole().equalsIgnoreCase("Bat/Keeper")) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "BatsmanIcon" + " \0");
//							}else if(as.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "BowlerIcon" + " \0");
//							}else if(as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole0" + row_id + " SET " + icon_path + "AllRounderIcon" + " \0");
//							}
						}
						TimeUnit.MICROSECONDS.sleep(300);
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All$Data$subs$Row6*ACTIVE SET 1 \0");
					for(int i=7; i<=11; i++) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$Data$subs$Row" + (i) + "*ACTIVE SET 0 \0");
					}
					int row = 0;
					for(Player plyr : match.getSetup().getAwaySubstitutes()) {
						row++;
						print_writer.println("-1 RENDERER*TREE*$Main$All$Data$subs$Row" + (row+6) + "*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeam1SubsName" + (row) + " SET " + 
								plyr.getTicker_name() + "\0");
					}
//					for(int i=0;i<=match.getSetup().getAwaySubstitutes().size()-1;i++) {
//						
//						System.out.println(match.getSetup().getAwaySubstitutes().size());
//						System.out.println(i);
//						
//						System.out.println("after : "+i);
////						if(match.getSetup().getAwaySubstitutes().size() == 1) {
////							if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 12) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$" + (i+1) + "*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName0" + (i+1) + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}
////						}else if(match.getSetup().getAwaySubstitutes().size() == 2) {
////							if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 12) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$1*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName01" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 13) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$2*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName02" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}
////						}else if(match.getSetup().getAwaySubstitutes().size() == 3) {
////							if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 12) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$1*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName01" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 13) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$2*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName02" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 14) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$3*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName03" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}
////						}else if(match.getSetup().getAwaySubstitutes().size() == 4) {
////							if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 12) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$1*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName01" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 13) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$2*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName02" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 14) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$3*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName03" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 15) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$" + "4" + "*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName04" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}
////						}else if(match.getSetup().getAwaySubstitutes().size() == 5) {
////							if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 12) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$1*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName01" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 13) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$2*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName02" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 14) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$3*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName03" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 15) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$" + "4" + "*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName04" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}else if(match.getSetup().getAwaySubstitutes().get(i).getPlayerPosition() == 16) {
////								print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Sudstitue$" + "5" + "*ACTIVE SET 1 \0");
////								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubsName05" + " SET " + 
////									match.getSetup().getAwaySubstitutes().get(i).getTicker_name() + "\0");
////							}
////						}
//						
//					}
					
				}
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
							" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
							" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				}
			}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	public void populateProjectedScore(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$StatHead01*GEOM*TEXT SET "+ "RATE" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$StatHead02*GEOM*TEXT SET "+ "RUNS" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ inn.getBatting_team().getTeamName1().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHeader*GEOM*TEXT SET "+ "PROJECTED SCORES" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1A*GEOM*TEXT SET "+ "@"+ proj_score_rate[0] +" (CRR)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET "+ proj_score_rate[1] + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1A*GEOM*TEXT SET "+ "@" + proj_score_rate[2] +" RPO" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET "+ proj_score_rate[3] + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1A*GEOM*TEXT SET "+ "@" + proj_score_rate[4] +" RPO" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET "+ proj_score_rate[5] + " \0");
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + "" + "\0");
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0");
						
						if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && match.getSetup().getMaxOvers() == 1) {
							if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + 
										match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " - SUPER OVER" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + 
										match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " - SUPER OVER" + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + (match.getSetup().getMaxOvers()*6) + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$balls*GEOM*TEXT SET " + "BALLS" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS FROM" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + "" + "\0");
							
						}else {
							if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
							}
							
							if(match.getSetup().getTargetOvers() == "" || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + match.getSetup().getMaxOvers() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$balls*GEOM*TEXT SET " + "OVERS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS FROM" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + "" + "\0");
							}else {
								if(match.getSetup().getTargetOvers() != "") {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + match.getSetup().getTargetOvers() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS FROM" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$balls*GEOM*TEXT SET " + "OVERS" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + "" + "\0");
								}
								if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + match.getSetup().getTargetOvers() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS FROM" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$balls*GEOM*TEXT SET " + "OVERS" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + " (VJD)" + "\0");
								}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + match.getSetup().getTargetOvers() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS FROM" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$balls*GEOM*TEXT SET " + "OVERS" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + " (DLS)" + "\0");
								}
							}
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
	
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
		
	}
	public void populateBugTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Runs*GEOM*TEXT SET "+ " " + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Runs*GEOM*TEXT SET "+ CricketFunctions.GetTargetData(match).getRemaningRuns() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Balls*GEOM*TEXT SET "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " \0");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
						
					}
				}
			}
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
		
	}
	public void populateTeamSummary(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");								
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, whichInning, 0, ",", match.getEventFile().getEvents()).split(",");
						
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");								
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + "\0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "" + "\0");
	
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "0s" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "1s" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "2s" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "3s" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5A" + " SET " + "4s" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue6A" + " SET " + "6s" + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + Count[1] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + Count[2] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + Count[3] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5B" + " SET " + Count[4] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue6B" + " SET " + Count[6] + "\0");
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateLtBattingSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "AFGHANISTAN_T20":
				if (match == null) {
					System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
				} else if (match.getMatch().getInning() == null) {
					System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");								
					for(Inning inn : match.getMatch().getInning()) {
						for(BattingCard bc : inn.getBattingCard()) {
							if (inn.getInningNumber() == whichInning) {
								String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0");
								if(PlayerId == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name().toUpperCase() + "\0");								
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns()+"*" + "\0");
									}
									else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns()+ "\0");
									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + bc.getBalls() + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "" + "\0");
	
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "0s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "1s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "2s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "3s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5A" + " SET " + "4s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue6A" + " SET " + "6s" + "\0");
	
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + Count[1] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + Count[2] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + Count[3] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5B" + " SET " + Count[4] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue6B" + " SET " + Count[6] + "\0");
								}
							}
						}
					}
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
					TimeUnit.MILLISECONDS.sleep(1000);
	
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtBatsmanThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "AFGHANISTAN_T20":
				if (match == null) {
					System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
				} else if (match.getMatch().getInning() == null) {
					System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
				} else {
					
					for(Inning inn : match.getMatch().getInning()) {
						for(BattingCard bc : inn.getBattingCard()) {
							if (inn.getInningNumber() == whichInning) {
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
								if(PlayerId == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								
	
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name().toUpperCase() + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + " " + "\0");								
									/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
									} else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
									}*/
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead02*GEOM*TEXT SET " + "RUNS" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead03*GEOM*TEXT SET " + bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + "BALLS" + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1c*GEOM*TEXT SET " + bc.getBalls() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + "S/R" + "\0");	
									if(bc.getStrikeRate() != null) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " +CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + "-" + "\0");
									}
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
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtBowlerThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "AFGHANISTAN_T20":
				if (match == null) {
					System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
				} else if (match.getMatch().getInning() == null) {
					System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
				} else {
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");
					for(Inning inn : match.getMatch().getInning()) {
						if (inn.getInningNumber() == whichInning) {
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==PlayerId) {
									/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
								} else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
								}*/
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name().toUpperCase() + "\0");
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
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtBowlerSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "AFGHANISTAN_T20":
				if (match == null) {
					System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
				} else if (match.getMatch().getInning() == null) {
					System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
				} else {
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");								
					for(Inning inn : match.getMatch().getInning()) {
						
						if (inn.getInningNumber() == whichInning) {
						
							for(BowlingCard boc : inn.getBowlingCard()) {
								String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BOWLER,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + inn.getBowling_team().getTeamBadge() + "\0");
								if(PlayerId == boc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name().toUpperCase() + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + "" + "\0");
									
									if(boc.getOvers() <= 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + 
												CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " OVER " + "\0");
									}else if(boc.getOvers() == 1 && boc.getBalls() == 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + 
												CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " OVER " + "\0");
									}else if(boc.getOvers() == 1 && boc.getBalls() >= 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + 
												CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " OVERS " + "\0");
									}else if(boc.getOvers() > 1 && boc.getBalls() >= 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + 
												CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " OVERS " + "\0");
									}
									
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
//	
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "" + "\0");
	
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "0s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "1s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "2s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "3s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5A" + " SET " + "4s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue6A" + " SET " + "6s" + "\0");
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + Count[1] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + Count[2] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + Count[3] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5B" + " SET " + Count[4] + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue6B" + " SET " + Count[6] + "\0");
								}
							}
						}
					}
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
					TimeUnit.MILLISECONDS.sleep(1000);
	
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtNextToBat(PrintWriter print_writer, String viz_scene,List<Player> plyr,List<Statistics> stats, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				int row_id=0;
				double strike_rate = 0;
			
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + 
								logo_path + inn.getBatting_team().getTeamBadge() + "\0");
		
						for(int b=1;b<=inn.getBattingCard().size();b++) {
							if(inn.getBattingCard().get(b-1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && 
									inn.getBattingCard().get(b-1).getHowOut() == null) {
								row_id = row_id + 1;
								if(row_id <= 3) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPosition" + row_id + " SET " + b + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_id + " SET " + 
											inn.getBattingCard().get(b-1).getPlayer().getTicker_name() + "\0");
									
									for(Statistics st : stats) {
										if(st.getPlayerID()==inn.getBattingCard().get(b-1).getPlayerId() && st.getStatsTypeId() == 2) {
											if(st.getBallsFaced() == 0 || st.getRuns()== 0) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + "-" + "\0");
											}else {
												strike_rate = st.getRuns() * 100;
												strike_rate = strike_rate/st.getBallsFaced();
												DecimalFormat df = new DecimalFormat("0.0");
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + CricketFunctions.generateStrikeRate(st.getRuns(), st.getBallsFaced(), 0) + "\0");
											}
										}
									}
//									for(Statistics st : stats) {
//										if(st.getPlayerID()==inn.getBattingCard().get(b-1).getPlayerId() && st.getStatsTypeId() == 8) {
//											if(st.getBallsFaced() == 0 || st.getRuns()== 0) {
//												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + "-" + "\0");
//											}else {
//												strike_rate = st.getRuns() * 100;
//												strike_rate = strike_rate/st.getBallsFaced();
//												DecimalFormat df = new DecimalFormat("0.0");
//												
//												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + df.format(strike_rate) + "\0");
//											}
//										}
//									}
									if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + photo_path + 
												inn.getBatting_team().getTeamName4() + "\\\\\\" + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path  + 
												inn.getBatting_team().getTeamName4() + "\\\\" + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										this.status = CricketUtil.SUCCESSFUL;
									}
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + photo_path + 
//											inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									
								}
							}
						}
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500\0");
				TimeUnit.MILLISECONDS.sleep(800);	
			}
			break;
		}
		
	}
	public void populateLtBowlerDetails(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "AFGHANISTAN_T20":
				if (match == null) {
					System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
				} else if (match.getMatch().getInning() == null) {
					System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
				} else {
					int total_inn = 0;
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");								
	
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
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name().toUpperCase() + "\0");								
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
					this.status = CricketUtil.SUCCESSFUL;	
				}
				break;
		}
		
	}
	public void populateFallofWicket(PrintWriter print_writer,String viz_scene,int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Header$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
						}
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + "\0");
						}
						
						
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP*ACTIVE SET 0" + " \0");
						}
						else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
							for(FallOfWicket fow : inn.getFallsOfWickets()) {								
								if(inn.getTotalWickets() >= 0 && inn.getTotalWickets() <= 10) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP*ACTIVE SET 1" + " \0");
									for(int fow_id = 0; fow_id <= 10; fow_id++) {
										if(fow_id <= inn.getFallsOfWickets().size()) {
											//System.out.println("Runs = " + fow.getFowRuns());
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$Stat" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$Stat" + fow.getFowNumber() + "$StatValue" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$Stat" + fow.getFowNumber() + "$StatValue" + fow.getFowNumber() + "$StatValue1A*ACTIVE SET 1 \0");
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$Stat" + fow.getFowNumber() + "$StatValue" + fow.getFowNumber() + "$StatValue1B*ACTIVE SET 1 \0");
	
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$Stat" + fow.getFowNumber() + "$StatValue" + fow.getFowNumber() + "$StatValue1B*GEOM*TEXT SET "+ fow.getFowRuns() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$Stat" + fow_id + "*ACTIVE SET 0 \0");
										}
									}	
								}		
							}
						}
						
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateSplit(PrintWriter print_writer,String viz_scene,int whichInning,int splitValue, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHeader*GEOM*TEXT SET "+ "" + " \0");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 30 || inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 50) {
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHeader*GEOM*TEXT SET "+ "BALLS PER " + splitValue + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + 
									match.getSetup().getAwayTeam().getTeamBadge() + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead01*GEOM*TEXT SET "+ splitValue + 
									CricketFunctions.Plural(splitValue) + " \0");
						
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHeader*GEOM*TEXT SET "+ "BALLS PER " + splitValue + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + 
									match.getSetup().getHomeTeam().getTeamBadge() + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead01*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + "\0");
							
						}
						for(int j=2;j<=7;j++) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead" + j + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + j + " SET " + "" + "\0");
						}
						
						String[] Splitballs = new String[CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size()];
					    for (int i = 0; i < CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size(); i++) {
					    	Splitballs[i] = CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).get(i);
					    	
					    	int row_id = i + 2;
					    	if(i <= 7) {
					    		
					    		if(row_id == 2) {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead" + row_id + " SET " + (i+1) + "st" + "\0");
					    		}else if(row_id == 3) {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead" + row_id + " SET " + (i+1) + "nd" + "\0");
					    		}else if(row_id == 4) {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead" + row_id + " SET " + (i+1) + "rd" + "\0");
					    		}else {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead" + row_id + " SET " + (i+1) + "th" + "\0");
					    		}
					    		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + " SET " + Splitballs[i] + "\0");	
					    	}
				        }
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}	
	}	
	public void populateComparision(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Header*GEOM*TEXT SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + 
								inn.getBowling_team().getTeamBadge() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + logo_path + 
								inn.getBatting_team().getTeamBadge() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Header*GEOM*TEXT SET " + "AFTER " + 
								CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$HomeTeamData$HomeTeamLastName*GEOM*TEXT SET " + 
								inn.getBowling_team().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$HomeTeamData$HomeTeamScore*GEOM*TEXT SET " + 
								CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$AwayTeamData$AwayTeamLastName*GEOM*TEXT SET " + 
								inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$AwayTeamData$AwayTeamScore*GEOM*TEXT SET " + 
								CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + "\0");
						
						if(match.getMatch().getInning().get(0).getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET " + 
									inn.getBowling_team().getTeamName1().toUpperCase() + " TOTAL : " + match.getMatch().getInning().get(0).getTotalRuns() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET " + 
									inn.getBowling_team().getTeamName1().toUpperCase() + " TOTAL : " + match.getMatch().getInning().get(0).getTotalRuns() + "-" + 
									match.getMatch().getInning().get(0).getTotalWickets() + "\0");
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateLTPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All$ImageAll$ImageGrp1$noname$Image1*TEXTURE*IMAGE SET " + " " + "\0");
	
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					String Left_Batsman ="",Right_Batsman="",teamname = "";
					if(match.getSetup().getHomeTeamId() == inn.getBattingTeamId()) {
						teamname = match.getSetup().getHomeTeam().getTeamBadge();
					}
					else {
						teamname = match.getSetup().getAwayTeam().getTeamBadge();
					}
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo05" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + teamname + "\0");
					
					Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getFull_name();
					Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getFull_name();
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
								inn.getBatting_team().getTeamName4() + "\\\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + ".png" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
								inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + photo_path + 
								inn.getBatting_team().getTeamName4() + "\\\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + ".png" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
								inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					
//					if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), 
//							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
//						print_writer.println("-1 RENDERER*TREE*$Main$All$TeamNameGrp$RowAni$Highlight1$Star*ACTIVE SET 1"+"\0");
//					}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), 
//							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
//						print_writer.println("-1 RENDERER*TREE*$Main$All$TeamNameGrp$RowAni$Highlight1$Star*ACTIVE SET 1"+"\0");
//					}else {
//						print_writer.println("-1 RENDERER*TREE*$Main$All$TeamNameGrp$RowAni$Highlight1$Star*ACTIVE SET 0"+"\0");
//					}
//					
//					if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), 
//							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
//						print_writer.println("-1 RENDERER*TREE*$Main$All$TeamNameGrp$RowAni$Highlight2$Star*ACTIVE SET 1"+"\0");
//					}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), 
//							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
//						print_writer.println("-1 RENDERER*TREE*$Main$All$TeamNameGrp$RowAni$Highlight2$Star*ACTIVE SET 1"+"\0");
//					}else {
//						print_writer.println("-1 RENDERER*TREE*$Main$All$TeamNameGrp$RowAni$Highlight2$Star*ACTIVE SET 0"+"\0");
//					}
					if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$ImageAll$ImageGrp1$ImpatStar*ACTIVE SET 0"+"\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$ImageAll$ImageGrp1$ImpatStar*ACTIVE SET 0"+"\0");
					}
					if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$ImageAll$ImageGrp2$ImpatStar*ACTIVE SET 0"+"\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$ImageAll$ImageGrp2$ImpatStar*ACTIVE SET 0"+"\0");
					}
					
	
					print_writer.println("-1 RENDERER*TREE*$Main$All$Header$MaxSize$BatHeader*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns()+"*" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName1" + " SET " + Left_Batsman.toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName2" + " SET " + Right_Batsman.toUpperCase() + "\0");
	
					if(inn.getTotalWickets() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 2) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionRuns1" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionBalls1" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + "\0");
	
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionRuns2" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionBalls2" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + "\0");	
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
	
			//this.status = CricketUtil.SUCCESSFUL;
		}
	}
	
	public void populateLtPowerPlay(PrintWriter print_writer, String viz_scene, MatchAllData match, String session_selected_broadcaster) 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$PartHeader$MatchId$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				//String[] PowerPlay_Over = getPowerPlayScore(match, 1, match.getEvents());
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
			
			this.status = CricketUtil.SUCCESSFUL;
		}
	}
	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<Tournament> tournament,List<Team> team ,
			MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			int row_no=0;
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Header$spon$Sponsor*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$BG_In$SponsorAll$Sponsor*ACTIVE SET 1 \0");
		
			switch (session_selected_broadcaster.toUpperCase()) {
			case "AFGHANISTAN_T20":
				switch(StatType.toUpperCase()) {
				case "MOST_RUNS":
					
					Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCap"+ " SET " + "0" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "MOST RUNS - THIS SEASON" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
					
					System.out.println("SIZE "+tournament.size());
					for(int i = 0; i <= tournament.size() - 1 ; i++) {
						System.out.println("TEAM ID : "+team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamId());
						row_no = row_no + 1;
						if(row_no < 6) {
							if(tournament.get(i).getPlayerId() == playerid) {
								lb_count = row_no;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 0 + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
									tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
									team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + "" + "\0");
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
						}else {
							break;
						}
					}
					break;
				case "MOST_WICKETS":
					
					Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCap"+ " SET " + "0" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "MOST WICKETS - THIS SEASON" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
					
					for(int i = 0; i <= tournament.size() - 1 ; i++) {
						row_no = row_no + 1;
						if(row_no < 6) {	
							if(tournament.get(i).getPlayerId() == playerid) {
								lb_count = row_no;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 0 + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
									tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
									team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getWickets() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
						}else {
							break;
						}
					}
					break;
				case "MOST_FOURS":
					
					Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCap"+ " SET " + "0" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "MOST FOURS - THIS SEASON" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
					
					for(int i = 0; i <= tournament.size() - 1 ; i++) {
						row_no = row_no + 1;
						if(row_no < 6) {	
							if(tournament.get(i).getPlayerId() == playerid) {
								lb_count = row_no;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 0 + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
									tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
									team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getFours() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
						}else {
							break;
						}
					}
					break;
				case "MOST_SIXES":
					Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCap"+ " SET " + "0" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "MOST SIXES - THIS SEASON" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
					
					for(int i = 0; i <= tournament.size() - 1 ; i++) {
						row_no = row_no + 1;
						if(row_no < 6) {	
							if(tournament.get(i).getPlayerId() == playerid) {
								lb_count = row_no;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 0 + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
									tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
									team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getSixes() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
						}else {
							break;
						}
					}
					break;
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 HighlightAll$Highlight" + lb_count + "_In 1.000 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;
				break;
	
			}
		}
	}
	public void populateLandMark(PrintWriter print_writer,String viz_scene, int whichInning, String statType, int playerId, MatchAllData match, String session_selected_broadcaster, Configuration config)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				//String Home_or_Away="";
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
						switch(statType.toUpperCase()) {
						case "BATSMAN":
							for(BattingCard bc : inn.getBattingCard()) {
								if(playerId == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + bc.getPlayer().getFirstname().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
	
									if(bc.getStatus().equals(CricketUtil.OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Runs*GEOM*TEXT SET "+ bc.getRuns() + " \0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Runs*GEOM*TEXT SET "+ bc.getRuns() + "*" + " \0");
									}
									
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Balls*GEOM*TEXT SET "+ bc.getBalls() + " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatHead*GEOM*TEXT SET "+ "S/R " + " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + " \0");
									if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
														+ match.getSetup().getHomeTeam().getTeamBadge() + "\0");
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
												+ match.getSetup().getAwayTeam().getTeamBadge() + "\0");
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
									}
								}
							}
							
							break;
						case "BOWLER":
							for(BowlingCard boc : inn.getBowlingCard()) {
								if(playerId == boc.getPlayerId()) {
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + " " + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + boc.getPlayer().getFirstname().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + boc.getPlayer().getSurname().toUpperCase() + "\0");
									
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Runs*GEOM*TEXT SET "+ boc.getRuns()+"-"+boc.getWickets() + " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Balls*GEOM*TEXT SET "+ "("+CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls())+")" + " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatHead*GEOM*TEXT SET "+ "ECON " + " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ boc.getEconomyRate() + " \0");
									
									if(inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
														+ match.getSetup().getHomeTeam().getTeamBadge() + "\0");
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\\\" + boc.getPlayer().getPhoto() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path  + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + boc.getPlayer().getPhoto() + "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
												+ match.getSetup().getAwayTeam().getTeamBadge() + "\0");
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\\\" + boc.getPlayer().getPhoto() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + boc.getPlayer().getPhoto() + "\0");
										}
									}
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}
	public void populateMostRunsTeam(PrintWriter print_writer,String viz_scene,int teamId,String Type,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MostRuns inning is null";
		} else {
			String teamName="";
			int row_no=0;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Header$spon$Sponsor*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$BG_In$SponsorAll$Sponsor*ACTIVE SET 0 \0");
			Team tm = team.stream().filter(team1 -> team1.getTeamId() == teamId).findAny().orElse(null);
		
			
			switch(Type.toUpperCase()) {
			case "RUNS":
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCap"+ " SET " + "0" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + tm.getTeamName1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "MOST RUNS - THIS SEASON" + "\0");
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				row_no = 0;
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamId() == teamId) {
						row_no = row_no + 1;
						if(row_no < 6) {
							
							if(row_no == 1) {
								lb_count = row_no;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											photo_path + tm.getTeamName4() + "\\\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + tm.getTeamName4() + "\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 0 + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
									tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
									"STRIKE RATE : "+CricketFunctions.generateStrikeRate(tournament.get(i).getRuns(), tournament.get(i).getBallsFaced(), 0) + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + "" + "\0");
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
						}else {
							break;
						}
					}
				}
				this.status = CricketUtil.SUCCESSFUL;
				break;
				
			case "WICKETS":
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCap"+ " SET " + "0" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + tm.getTeamName1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET "+"MOST WICKETS - THIS SEASON" + "\0");
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				row_no = 0;
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamId() == teamId) {
						row_no = row_no + 1;
						if(row_no < 6) {	
							if(row_no == 1) {
								lb_count = row_no;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											photo_path + tm.getTeamName4() + "\\\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + 
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + tm.getTeamName4() + "\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 0 + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
									tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
									"ECONOMY : "+ CricketFunctions.getEconomy(tournament.get(i).getRunsConceded(), tournament.get(i).getBallsBowled(), 2, "-") + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getWickets() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
						}else {
							break;
						}
					}
				}
				this.status = CricketUtil.SUCCESSFUL;
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 HighlightAll$Highlight" + lb_count + "_In 1.000 \0");
		}
	}
	
	public void populateTeamLeaderBoard(PrintWriter print_writer,String viz_scene,int TeamId,String Type,int playerId,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MostRuns inning is null";
		} else {
			//int team_i = 0;
			this.status = CricketUtil.SUCCESSFUL;
			String teamName= "";
//			print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "" + "\0");
//			
//			print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "010" + " SET " + "4" + "\0");
//			
//			print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "HighlightSelection" + " SET " + "0" + "\0");
//			
//			print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "002" + " SET " + "PLAYERS" + "\0");
//			print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "003" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Header$spon$Sponsor*ACTIVE SET 0 \0");
			for(Team tm : team) {
				if(tm.getTeamId() == TeamId) {
					//team_i = tm.getTeamId();
					teamName = tm.getTeamName1();
					//print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + logo_path + tm.getTeamName4() + "\0");
					
				}
			}
			
			switch(Type.toUpperCase()) {
			case "RUNS":
				int row_no=0;
				double strike_rate = 0;
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + teamName + " - MOST RUNS" + "\0");
				
//				print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-HEADER" + " SET " + teamName + " - " + "MOST RUNS" + "\0");
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
//				print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "004" + " SET " + "RUNS" + "\0");
//				print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "005" + " SET " + "S/R" + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(tournament.get(i).getPlayer().getTeamId() == TeamId) {
						if(tournament.get(i).getRuns() > 0) {
							if(row_no < 5) {
								row_no = row_no + 1;
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgEventLogo" + " SET " + 
										logo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamBadge() + "\0");
								
								if(tournament.get(i).getPlayerId() == playerId) {
									lb_count = row_no;
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
									
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 0 + "\0");
								}
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_no + " SET " + 
											photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_no + " SET " + 
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
										tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
								
								strike_rate = tournament.get(i).getRuns() * 100;
								strike_rate = strike_rate/tournament.get(i).getBallsFaced();
								DecimalFormat df = new DecimalFormat("0.0");
								
								if(tournament.get(i).getBallsFaced() == 0 || tournament.get(i).getRuns() == 0) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
											"S/R : -" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
											 "S/R : " + CricketFunctions.generateStrikeRate(tournament.get(i).getRuns(), tournament.get(i).getBallsFaced(), 0) + "\0");
								}
								
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + "" + "\0");
								
								
								//------------------------------------------------------------
								
								
//								print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "009" + " SET " + (row_no - 1) + "\0");
//								print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row_no + 
//										"$RowAni$RowOmo$Dehighlight$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
//								
//								print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row_no + 
//										"$RowAni$RowOmo$Dehighlight$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
//								print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row_no + 
//										"$RowAni$RowOmo$Dehighlight$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getRuns() + "\0");
//								
//								strike_rate = tournament.get(i).getRuns() * 100;
//								strike_rate = strike_rate/tournament.get(i).getBallsFaced();
//								DecimalFormat df = new DecimalFormat("0.0");
//								
//								if(tournament.get(i).getBallsFaced() == 0 || tournament.get(i).getRuns() == 0) {
//									print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row_no + 
//											"$RowAni$RowOmo$Dehighlight$StatValueGrp$StatValue3*GEOM*TEXT SET " + "-" + "\0");
//								}else {
//									print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row_no + 
//											"$RowAni$RowOmo$Dehighlight$StatValueGrp$StatValue3*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
//								}
							}
						}
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 HighlightAll$Highlight" + lb_count + "_In 1.000 \0");
				this.status = CricketUtil.SUCCESSFUL;
				break;
				
			case "WICKETS":
				int row = 0;
				
//				print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "001-HEADER" + " SET " + teamName + " - " + "MOST WICKETS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + teamName + " - MOST WICKETS" + "\0");
				
				Collections.sort(tournament, new CricketFunctions.BowlerWicketsComparator());
//				print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "004" + " SET " + "WICKETS" + "\0");
//				print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "005" + " SET " + "ECONOMY" + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(tournament.get(i).getPlayer().getTeamId() == TeamId) {
						if(tournament.get(i).getWickets() > 0) {
							if(row < 5) {
								row = row + 1;
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgEventLogo" + " SET " + 
										logo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamBadge() + "\0");
								
								if(tournament.get(i).getPlayerId() == playerId) {
									lb_count = row;
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row + " SET " + 1 + "\0");
									
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row + " SET " + 0 + "\0");
								}
								
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row + " SET " + 
											photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row + " SET " + 
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
												tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row + " SET " + 
										tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
								
								if (tournament.get(i).getBallsBowled() >= 1) {
									DecimalFormat df_b = new DecimalFormat("0.00");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row + " SET " + 
											"ECON : " + df_b.format(((double) tournament.get(i).getRunsConceded()
													/ (double) tournament.get(i).getBallsBowled()) * 6) + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row + " SET " + 
											"ECON : -" + "\0");
								}
									
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row + " SET " + tournament.get(i).getWickets() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row + " SET " + " " + "\0");
								
								
								//--------------------------------------------------------
								
								
//								print_writer.println("-1 RENDERER*TREE*$object*FUNCTION*ControlObject*in SET ON " + "009" + " SET " + (row - 1) + "\0");
//								print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row + 
//										"$RowAni$RowOmo$Dehighlight$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
//								
//								print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row + 
//										"$RowAni$RowOmo$Dehighlight$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
//								print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row + 
//										"$RowAni$RowOmo$Dehighlight$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getWickets() + "\0");
//								
//								if (tournament.get(i).getBallsBowled() >= 1) {
//									DecimalFormat df_b = new DecimalFormat("0.00");
//									print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row + "$RowAni$RowOmo$"
//											+ "Dehighlight$StatValueGrp$StatValue3*GEOM*TEXT SET " + df_b.format(((double) tournament.get(i).getRunsConceded()
//													/ (double) tournament.get(i).getBallsBowled()) * 6) + "\0");
//								}else {
//									print_writer.println("-1 RENDERER*TREE*$object$All$noname$FF_row_col$RowOmo$4ColGrp$4Col$Row" + row + 
//											"$RowAni$RowOmo$Dehighlight$StatValueGrp$StatValue3*GEOM*TEXT SET " + "-" + "\0");
//								}
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 HighlightAll$Highlight" + lb_count + "_In 1.000 \0");
				this.status = CricketUtil.SUCCESSFUL;
				break;
			}
		}
	}
	
	public void populateFFLandMark(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				//String Home_or_Away="";
				print_writer.println("-1 RENDERER*TREE*$Main$All$MaxSize$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$text$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ (Integer.valueOf(inn.getPartnerships().size()) + 2) + " \0");
						
						for(BattingCard bc : inn.getBattingCard()) {
							if(playerId == bc.getPlayerId()) {
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$MaxSize$PlayerName*GEOM*TEXT SET "+ bc.getPlayer().getFirstname().toUpperCase() + " \0");
								
								if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
												+ match.getSetup().getHomeTeam().getTeamBadge() + "\0");
									for(Player hs : match.getSetup().getHomeSquad()) {
										if(hs.getPlayerId() == playerId) {
											if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
														+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
														+ "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path  + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}
										}
									}
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
											+ match.getSetup().getAwayTeam().getTeamBadge() + "\0");
									for(Player as : match.getSetup().getAwaySquad()) {
										if(as.getPlayerId() == playerId) {
											if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
														+ photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
														+ "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}
										}
									}
								}
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}
	public void populateLtEquation(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {	
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ inn.getBatting_team().getTeamName1().toUpperCase() + " \0");
						
						if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED" + " \0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + " \0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ match.getMatch().getMatchStatus().toUpperCase() + " \0");
									}
								}
								
							}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
								
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED" + " \0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + " \0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ match.getMatch().getMatchStatus().toUpperCase() + " \0");
									}
								}
							}
							
							else{
								if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " 
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + " \0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " \0");
								}
								
							}
						}else {
							if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
								if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED" + " \0");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + " \0");
										}
										else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ match.getMatch().getMatchStatus().toUpperCase() + " \0");
										}
									}
									
								}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
										|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
									
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED" + " \0");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + " \0");
										}
										else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ match.getMatch().getMatchStatus().toUpperCase() + " \0");
										}
									}
								}
								
								else{
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " \0");									
								}
							}
							else {
								if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED" + " \0");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + " \0");
										}
										else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ match.getMatch().getMatchStatus().toUpperCase() + " \0");
										}
									}
								}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
										|| match.getMatch().getInning().get(1).getTotalOvers() >= Double.valueOf(match.getSetup().getTargetOvers())) {
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ "MATCH TIED" + " \0");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ match.getMatch().getMatchStatus().toUpperCase() + " \0");
										}
										else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "+ match.getMatch().getMatchStatus().toUpperCase() + " \0");
										}
									}
								}
								else{
									if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
										if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "
													+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
													" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + " (VJD)" + " \0");											
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "
													+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
													" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (VJD)" + " \0");
										}
									}
									else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "
													+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
													" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + " (DLS)" + " \0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "
													+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
													" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (DLS)" + " \0");
										}
									}
									else {
										if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "
													+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
													" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + " \0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET "
													+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
													" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " \0");
										}
									}
								}
							}
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
	
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	public void populatePointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> point_table,List<Team> teams,List<VariousText> variousText, String session_selected_broadcaster,MatchAllData match) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			int row_id=0,omo_num=0;
			DecimalFormat df = new DecimalFormat("0.000");
			print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 0"+"\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row1$RowAni$Data$BowlerName*GEOM*TEXT SET " + "" + " \0");
			//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPointsSponsor" + " SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPointsLogo" + " SET " + logo_path + "TLogo05" + "\0");
	
			for(int i = 0; i <= point_table.size() - 1 ; i++) {
				row_id = row_id + 1;
				if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())  
						|| match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) 
						|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
						|| match.getSetup().getAwayTeam().getTeamBadge().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
					omo_num = 1;
				}else {
					omo_num = 0;
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Highlight"
						+ "*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				
				
				for(Team team_logo : teams) {
					if(team_logo.getTeamBadge().equalsIgnoreCase(point_table.get(i).getTeamName())
							|| team_logo.getTeamName1().equalsIgnoreCase(point_table.get(i).getTeamName())) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTeam" + row_id + " SET " + 
								team_logo.getTeamName3() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPointsTeamBadge" + row_id + " SET " + logo_path +
								team_logo.getTeamBadge() + "\0");
					}
				}
				
				if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 0 \0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 1 \0");
				}

				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayedValue" + row_id + " SET " + point_table.get(i).getPlayed() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tWonValue" + row_id + " SET " + point_table.get(i).getWon() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLostValue" + row_id + " SET " + point_table.get(i).getLost() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNRValue" + row_id + " SET " + point_table.get(i).getNoResult() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsValue" + row_id + " SET " + point_table.get(i).getPoints() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNRRValue" + row_id + " SET " + df.format(point_table.get(i).getNetRunRate()) + "\0");
	
			}
			
			
			for(VariousText vt : variousText) {
				if(vt.getVariousType().equalsIgnoreCase("POINTSTABLEHEADER") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead"+ " SET " + vt.getVariousText() + "\0");
				}else if(vt.getVariousType().equalsIgnoreCase("POINTSTABLEHEADER") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead"+ " SET " + "STANDINGS" + "\0");
				}
			}
			
			if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" || which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || 
					which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" || which_graphics_onscreen == "PARTNERSHIP") {	
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.729 BattingCardIn 0.0 \0");
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.729 BowlingCardIn 0.0 \0");
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.729 SummaryIn 0.0 \0");
				}else if(which_graphics_onscreen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.729 PartnershipAllIn 0.0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 1"+"\0");
				}
			}else {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.729 \0");
			}
			this.status = CricketUtil.SUCCESSFUL;
			TimeUnit.MILLISECONDS.sleep(1000);
			break;
		}
		
	}
	public void populateLtPointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> point_table, List<Team> teams,MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		int row_id=0;
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "STANDINGS" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Row0$RowAni$OversHead*GEOM*TEXT SET "+ "STANDINGS" + " \0");
		for(int i = 0; i <= point_table.size()-1; i++) {
			row_id = row_id + 1;
			//System.out.println(point_table.size());
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 0 \0");
			}
			else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 1 \0");
			}
			
			if(match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())   
					|| match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getAwayTeam().getTeamBadge().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*FUNCTION*Omo*vis_con SET 1 \0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*FUNCTION*Omo*vis_con SET 0 \0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*ACTIVE SET 1 \0");
			
			for(Team team : teams) {
				if(team.getTeamBadge().equalsIgnoreCase(point_table.get(i).getTeamName()) || team.getTeamName1().equalsIgnoreCase(point_table.get(i).getTeamName())) {
					System.out.println(team.getTeamName3());
					print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$BowlerName*GEOM*TEXT SET " + 
							team.getTeamName3().toUpperCase() + " \0");
				}
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$OversValue*GEOM*TEXT SET " + 
					point_table.get(i).getPlayed() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$MaidensValue*GEOM*TEXT SET " + 
					point_table.get(i).getWon() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$RunsValue*GEOM*TEXT SET " + 
					point_table.get(i).getLost() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$EconomyValue*GEOM*TEXT SET " + 
					point_table.get(i).getPoints() + " \0");
	
		}
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 1.460 \0");
		TimeUnit.MILLISECONDS.sleep(1000);
	
		this.status = CricketUtil.SUCCESSFUL;	
	}
	public void populateBowlerStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId,List<Player> plyr, List<Team> team, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ plyr.get(playerId - 1).getFull_name().toUpperCase() + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path + 
						team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamBadge() + "\0");
				
				if(plyr.get(playerId - 1).getBowlingStyle() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + 
							CricketFunctions.getbowlingstyle(plyr.get(playerId - 1).getBowlingStyle()).toUpperCase() + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + " " + " \0");
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
	
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}	
	public void populateTieIdDouble(PrintWriter print_writer,String viz_sence_path,String day,List<Fixture> fix,MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			int row_id = 0;
			String newDate = "",Date = "";
			
			String[] dateSuffix = {
					"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
					
					"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
					
					"th", "st", "nd", "rd", "th", "th", "th", "th", "th","th",
					
					"th", "st"
			};
			
			Calendar cal = Calendar.getInstance();
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "" + "\0");
			System.out.println("DAY "+day);
			if(day.toUpperCase().equalsIgnoreCase("TODAY")) {
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "TODAY'S MATCHES " + "\0");
			}else if(day.toUpperCase().equalsIgnoreCase("TOMORROW")) {
				cal.add(Calendar.DATE, +1);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "TOMORROW'S MATCHES " + "\0");
			}else if(day.toUpperCase().equalsIgnoreCase("DAY_AFTER_TOMORROW")) {
				cal.add(Calendar.DATE, +2);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				
				newDate = Date.split("-")[0];
				if(Integer.valueOf(newDate) < 10) {
					newDate = newDate.replaceFirst("0", "");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + newDate + 
						dateSuffix[Integer.valueOf(newDate)] + " " + Month.of(Integer.valueOf(Date.split("-")[1])) + "\0");
			}
			
			for(int i = 0; i <= fix.size()-1; i++) {
				if(fix.get(i).getDate().equalsIgnoreCase(Date)) {
					row_id++;
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamBadge" + row_id + " SET " + logo_path + 
							fix.get(i).getHome_Team().getTeamBadge() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + row_id + " SET " + 
							fix.get(i).getHome_Team().getTeamName1().toUpperCase() + "\0");
					 
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchId" + row_id + " SET " + "MATCH " + 
							fix.get(i).getMatchnumber() + "\0");
					
					if(fix.get(i).getLocalTime() != null) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tTime" + row_id + " SET " + "COVERAGE STARTS AT " + fix.get(i).getLocalTime() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tTime" + row_id + " SET " + "" + "\0");
					}
					
	
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamBadge" + row_id + " SET " + logo_path + 
							fix.get(i).getAway_Team().getTeamBadge() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + row_id + " SET " + 
							fix.get(i).getAway_Team().getTeamName1().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "FROM " + fix.get(i).getVenue() + "\0");
	
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	public void populatePreviousSummary(PrintWriter print_writer, String viz_scene, int match_number,MatchAllData mtch,List<Fixture> fix, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
//				for(int j = 0; j <= mtch.size() - 1; j++) {
//				print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET 0"+"\0");
					boolean impactInSummary = false;
					int row_id = 0, max_Strap = 0;
					String teamname = "",teamname_logo="";
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$Shriram_Logo*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + "TLogo05" +" \0");
					
					for(int i=2; i<=4; i++) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + i + 
								"$RowAni$Star*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + i + 
								"$RowAni$Star2*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + (6+(i-2)) + 
								"$RowAni$Star2*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + (6+(i-2)) + 
								"$RowAni$Star*ACTIVE SET 0"+"\0");
					}
					for(int i = 1; i <= 2 ; i++) {
	
						if(i == 1) {
							row_id = 0;
							max_Strap = 4;
							if(mtch.getMatch().getInning().get(i-1).getBattingTeamId() == mtch.getSetup().getTossWinningTeam()) {
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*GEOM*TEXT SET " + "TOSS" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*ACTIVE SET 0 \0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row6*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row7*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row8*ACTIVE SET 0 \0");
							
						} else {
							row_id = 4;
							max_Strap = 8;
							if(mtch.getMatch().getInning().get(i-1).getBattingTeamId() == mtch.getSetup().getTossWinningTeam()) {
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*GEOM*TEXT SET " + "TOSS" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + (row_id + 1) + "$RowAni$Highlight$TOSS*ACTIVE SET 0 \0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row6*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row7*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row8*ACTIVE SET 1 \0");
						}
						row_id = row_id + 1;
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$SummaryHeader*GEOM*TEXT SET " + mtch.getSetup().getMatchIdent() + "\0");
						
						if(mtch.getMatch().getInning().get(i-1).getBattingTeamId() == mtch.getSetup().getHomeTeamId()) {
							teamname = mtch.getSetup().getHomeTeam().getTeamName1();
							teamname_logo  = mtch.getSetup().getHomeTeam().getTeamBadge();
						} else {
							teamname = mtch.getSetup().getAwayTeam().getTeamName1();
							teamname_logo = mtch.getSetup().getAwayTeam().getTeamBadge();
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumTeamBadge" + i + " SET " + logo_path + teamname_logo + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
						
						if(mtch.getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + mtch.getMatch().getInning().get(i-1).getTotalRuns() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + mtch.getMatch().getInning().get(i-1).getTotalRuns() + slashOrDash + 
									String.valueOf(mtch.getMatch().getInning().get(i-1).getTotalWickets()) + "\0");	
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Overs*GEOM*TEXT SET " + 
								CricketFunctions.OverBalls(mtch.getMatch().getInning().get(i-1).getTotalOvers(),mtch.getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
						
						if(mtch.getMatch().getInning().get(i-1).getBattingCard() != null) {
							Collections.sort(mtch.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
							
							for(BattingCard bc : mtch.getMatch().getInning().get(i-1).getBattingCard()) {
								if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row"+row_id+"$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + " \0");
									
									if(CricketFunctions.isImpactPlayer(mtch.getEventFile().getEvents(), 0, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
										impactInSummary = true;
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
												"$RowAni$Star2*ACTIVE SET 1"+"\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
												"$RowAni$Star2*ACTIVE SET 0"+"\0");
									}
									
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman*ACTIVE SET 1 \0");
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
									
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$NotOut*ACTIVE SET 1 \0");
									} else {
										print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$NotOut*ACTIVE SET 0 \0");
									}
									
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
									
									if(i == 1 && row_id >= 4) {
										break;
									}else if(i == 2 && row_id >= 8) {
										break;
									}
								}
							}
						}
	
						for(int k = row_id + 1; k <= max_Strap; k++) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + k + "$RowAni$RowOmo$Dehighlight$Batsman*ACTIVE SET 0 \0");
						}
						
						if(i == 1) {
							row_id = 1;
						}
						else {
							row_id = 5;
						}
	
						if(mtch.getMatch().getInning().get(i-1).getBowlingCard() != null) {
							
							Collections.sort(mtch.getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
	
							for(BowlingCard boc : mtch.getMatch().getInning().get(i-1).getBowlingCard()) {
								if(boc.getWickets() > 0 ) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler*ACTIVE SET 1 \0");
									
									if(CricketFunctions.isImpactPlayer(mtch.getEventFile().getEvents(), 0, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
										impactInSummary = true;
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
												"$RowAni$Star*ACTIVE SET 1"+"\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row" + row_id + 
												"$RowAni$Star*ACTIVE SET 0"+"\0");
									}
									
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$BowlerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$ScoreGrp$BowlerFigure*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$ScoreGrp$BowlerOvers*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
									
									if(i == 1 && row_id >= 4) {
										break;
									}
									else if(i == 2 && row_id >= 8) {
										break;
									}
								}
							}
						}
						
						for(int k = row_id + 1; k <= max_Strap; k++) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + k + "$RowAni$RowOmo$Dehighlight$Bowler*ACTIVE SET 0 \0");
						}
					}
					if(impactInSummary) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$ImpactLegend*ACTIVE SET 1" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$ImpactLegend*ACTIVE SET 0" + "\0");
					}
					if(mtch.getMatch().getMatchResult() != null) {
						if(mtch.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.TEAMNAME_3, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
						}
						else if(mtch.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ "MATCH TIED" + "\0");
						}
						else if(mtch.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ "MATCH TIED - " + mtch.getMatch().getMatchStatus().replace("win", "won").toUpperCase() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ mtch.getMatch().getMatchStatus().replace("win", "won").toUpperCase() + "\0");
						}
					}
					else {
						if(mtch.getSetup().getTargetType() == "") {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ mtch.getMatch().getMatchStatus().replace("win", "won").toUpperCase() + "\0");
						}
						else if(mtch.getSetup().getTargetType() != null) {
							if(mtch.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
										+ mtch.getMatch().getMatchStatus().replace("win", "won").toUpperCase() + " (VJD)" + "\0");
							}
							else if(mtch.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
										+ mtch.getMatch().getMatchStatus().replace("win", "won").toUpperCase() + " (DLS)" + "\0");
							}
						}
						//.trim().equalsIgnoreCase("")
						
					}
//				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 SummaryIn 1.716 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public void populateVizInfobarTop(boolean is_this_updating, PrintWriter print_writer, String TopStats, MatchAllData match, String session_selected_broadcaster)
	{
		//System.out.println("TopStats " + TopStats);
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			switch(TopStats.toUpperCase()) {
			case "CRR":
				for(Inning inn : match.getMatch().getInning()) {
					if(is_this_updating == false) {
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "2" + ";");
					}
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$CurRunRateIn START \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR$CurRunRate$CURRValue*GEOM*TEXT SET " + inn.getRunRate() + "\0");
					}
				}
				break;
			case "VS_BOWLING_TEAM":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
						}
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$BowlingTeamIn START \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp$TeamName2*GEOM*TEXT SET " + inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
					}
				}
				break;
			case"CRR_RRR":
				if(is_this_updating == false) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "3" + ";");
				}
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*CRR_RRRIn START \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$CURRValue*GEOM*TEXT SET " + inn.getRunRate() + "\0");
					}
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$REQRValue*GEOM*TEXT SET " + 
				                       CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + "\0");
				
				break;
			case "TOSS_WINNING":
				if(is_this_updating == false) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "0" + ";");
				}
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Team*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName4() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Result*GEOM*TEXT SET " + match.getSetup().getTossWinningDecision() + "\0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Team*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName4() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Result*GEOM*TEXT SET " + match.getSetup().getTossWinningDecision() + "\0");
				}
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$TossIn START \0");
				break;
			case "EQUATION":
				if(is_this_updating == false) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "3" + ";");
				}
				if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText$FreeTextSmall$FreeTextSmall*GEOM*TEXT SET " + 
											CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase()  + "\0");
					
				}
				else{
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours$1$NEEDRUNS*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours$2$NEEDBALLS*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getRemaningBall() + "\0");
				}
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$EquationIn START \0");
				break;
			case "LAST_WICKET":
				if(is_this_updating == false) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$LastWicketIn START \0");
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
						for(BattingCard bc : inn.getBattingCard()){
							if(inn.getFallsOfWickets().size() > 0){
								if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket$LastWicket$LastWicketPlayer*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket$LastWicket$LastWicketBowler*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket$LastWicket$LastWicketPlayer$noname$LastWicketGrp$Ball*GEOM*TEXT SET " + bc.getBalls() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket$LastWicket$LastWicketPlayer$noname$LastWicketGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								}
							}								
						}
					}
				}				
				break;
			}
			break;
		}
		
		
	}
	public Infobar populateBottomRightBottom(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		//System.out.println("TopStats " + BottomRightStats);
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			boolean isVisited = true;
			if(is_this_updating == false) {
				if(infobar.getBottom_right_bottom_section() != null && infobar.getBottom_right_bottom_section().trim().isEmpty()) {
					switch (infobar.getBottom_right_bottom_section().toUpperCase()) {
					case "THIS_OVER":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$ThisOverOut START \0");
						break;
					}
					TimeUnit.MILLISECONDS.sleep(500);
				}
			}
			if(infobar.getBottom_right_bottom_section() != null) {
				switch(infobar.getBottom_right_bottom_section().toUpperCase()) {
				case "THIS_OVER":
					if(is_this_updating == false) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$BottomPartIn START \0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$ThisOverIn START \0");
					}
					int Player_id=0;
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){						
							
							for(BowlingCard boc : inn.getBowlingCard()) {
								switch(boc.getStatus().toUpperCase()) {
								case CricketUtil.CURRENT + CricketUtil.BOWLER: case CricketUtil.LAST + CricketUtil.BOWLER:
									Player_id = boc.getPlayerId();
									
									break;
								}
							}
							
							String[] this_over = CricketFunctions.getEventsText(CricketUtil.OVER,Player_id,",", match.getEventFile().getEvents(),0).split(",");
							
							System.out.println(this_over.length+" len : arr "+this_over[0]);
							if(this_over.length<8) {
								if(this_over.length==1 && this_over[0] == "") {
									int bowlerNum = -1;
									int totalRuns = 0;
									for (int i = match.getEventFile().getEvents().size()-1; i >= 0; i--) {	
										if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.NEW_BATSMAN)) {
											continue;
										}
										if(bowlerNum != -1 && match.getEventFile().getEvents().get(i).getEventBowlerNo() != bowlerNum) {
											break;
										}
										if(match.getEventFile().getEvents().get(i).getEventBowlerNo() == bowlerNum) {
											switch (match.getEventFile().getEvents().get(i).getEventType()) {
											case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  
											case CricketUtil.FIVE : case CricketUtil.DOT:
												totalRuns += match.getEventFile().getEvents().get(i).getEventRuns();
												break;
											case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY:
												totalRuns += match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
												break;
											 case CricketUtil.LOG_WICKET:
												 totalRuns += match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns();
												 break;
											 case CricketUtil.LOG_ANY_BALL:
												 totalRuns += match.getEventFile().getEvents().get(i).getEventRuns();
										          if (match.getEventFile().getEvents().get(i).getEventExtra() != null) {
										        	  totalRuns += match.getEventFile().getEvents().get(i).getEventExtraRuns();
										          }
										          if (match.getEventFile().getEvents().get(i).getEventSubExtra() != null) {
										        	  totalRuns += match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
										          }
												 break;
											}
										}else if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.END_OVER)) {
											bowlerNum = match.getEventFile().getEvents().get(i).getEventBowlerNo();
										}
									}
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vThisOver" + " SET " + "1" + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$"
											+ "ThisOver$ThisOverHead*GEOM*TEXT SET " + "LAST OVER :" + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
											+ 1 + "*FUNCTION*Omo*vis_con SET 1 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + 1 + " SET " + 
											totalRuns +" RUN"+CricketFunctions.Plural(totalRuns).toUpperCase() + "\0");
									
								}else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$"
											+ "ThisOver$ThisOverHead*GEOM*TEXT SET " + "THIS OVER :" + "\0");
									if(CricketFunctions.getEventsText(CricketUtil.OVER,Player_id,",", match.getEventFile().getEvents(),0).length() == 0) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vThisOver" + " SET " + "0" + "\0");
									}else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vThisOver" + " SET " + this_over.length + "\0");
									}
									for(int i=0;i < this_over.length;i++) {
										
										if(this_over[i].toUpperCase().equalsIgnoreCase("WD+W")) {
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
													+ (i+1) + "*FUNCTION*Omo*vis_con SET 2 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + "WD+W" + "\0");
										}
										else if(this_over[i].toUpperCase().equalsIgnoreCase("W") || this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.FOUR) || 
												this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.SIX) || this_over[i].toUpperCase().contains("BOUNDARY")) {
											
											if(this_over[i].toUpperCase().contains("BOUNDARY")) {
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
														+ (i+1) + "*FUNCTION*Omo*vis_con SET 4 \0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i].replace("BOUNDARY", "") + "\0");
											}else {
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
														+ (i+1) + "*FUNCTION*Omo*vis_con SET 4 \0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
											}
										}
										else if(this_over[i].toUpperCase().equalsIgnoreCase("WD") || this_over[i].toUpperCase().equalsIgnoreCase("NB")
												 || this_over[i].toUpperCase().contains("B") || this_over[i].toUpperCase().contains("LB")) {
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
													+ (i+1) + "*FUNCTION*Omo*vis_con SET 2 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
										}else if(this_over[i].toUpperCase().contains("Pn")) {
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
													+ (i+1) + "*FUNCTION*Omo*vis_con SET 2 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET P\0");
										} else {
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
													+ (i+1) + "*FUNCTION*Omo*vis_con SET 1 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
										}
									}
								}
								
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vThisOver" + " SET " + "1" + "\0");
								for (BowlingCard boc : inn.getBowlingCard()) {
									switch (boc.getStatus().toUpperCase()) {
									case CricketUtil.CURRENT + CricketUtil.BOWLER:
									case CricketUtil.LAST + CricketUtil.BOWLER:
										if (boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
											
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$"
													+ "ThisOver$ThisOverHead*GEOM*TEXT SET " + "THIS OVER :" + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
													+ 1 + "*FUNCTION*Omo*vis_con SET 1 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + 1 + " SET " + 
													CricketFunctions.processThisOverRunsCount(boc.getPlayerId(),match.getEventFile().getEvents()).split("-")[0]+" RUNS" + "\0");
											
										}else if(boc.getStatus().equalsIgnoreCase(CricketUtil.LAST + CricketUtil.BOWLER)) {
											
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$"
													+ "ThisOver$ThisOverHead*GEOM*TEXT SET " + "LAST OVER :" + "\0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
													+ 1 + "*FUNCTION*Omo*vis_con SET 1 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + 1 + " SET " + 
													CricketFunctions.processThisOverRunsCount(boc.getPlayerId(),match.getEventFile().getEvents()).split("-")[0]+" RUNS" + "\0");
											
										}
										break;
									}
								}
							}
						}
					}
					break;
				}
			}
			infobar.setLast_bottom_right_bottom_section(infobar.getBottom_right_bottom_section());
			break;
		}
		return infobar;
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			
			break;
		}
		
	}
	public void populateBatsmanStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, List<Player> plyr, List<Team> team, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				//String Home_or_Away="";
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ plyr.get(playerId - 1).getFull_name().toUpperCase() + " \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path + 
						team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamBadge() + "\0");
	
				if(plyr.get(playerId - 1).getBattingStyle().equalsIgnoreCase("RHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT-HAND BATTER "  + " \0");
				}else if(plyr.get(playerId - 1).getBattingStyle().equalsIgnoreCase("LHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT-HAND BATTER "+ " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + " " + " \0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
	
			this.status = CricketUtil.SUCCESSFUL;	
			break;
		}
			
	}
	public void populateGeneric(PrintWriter print_writer,String viz_scene,String Stats, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				switch(Stats.toUpperCase()) {
				case "BOUNDARIES":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamBadge() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
							}
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "BOUNDARIES : " + inn.getTotalFours() + " FOURS " + inn.getTotalSixes() + " SIXES" + ";");
						}
					}
					break;
				case "RUNS_BALLS":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamBadge() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
								
								
							}
							if(match.getMatch().getMatchResult() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "MATCH TIED" + ";");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + ";");
								}
								else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
											CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + ";");
									//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + match.getMatchStatus().toUpperCase() + ";");
								}
							}
							else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
										CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + ";");
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + match.getMatchStatus().toUpperCase() + ";");
								
								if(match.getSetup().getTargetType() != null) {
									if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
										
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
											CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " (VJD)" + ";");
									}
								}
							}
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "BOUNDARIES " + inn.getTotalFours() + "FOURS " + inn.getTotalSixes() + " SIXES" + ";");
						}
					}
					break;
				case "PARTNERSHIP":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamBadge()+ ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "CURRENT PARTNERSHIP : "   
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + " RUNS OFF "  
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + " BALLS" + ";");
						}
					}
					break;
				case "CURRENT_RUN_RATE":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamBadge() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}	
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "CURRENT RUN RATE : " + inn.getRunRate() + ";");
						}
					}
					break;
				case "REQUIRED_RUN_RATE":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamBadge() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}	
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "REQUIRED RUN RATE : " + 
												CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
						}
					}
					break;
				case "COMPARISION":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamBadge() +  ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}	
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "AT THIS STAGE : " + match.getMatch().getInning().get(0).getBatting_team().getTeamName1().toUpperCase() + 
												 " " + CricketFunctions.compareInningData(match,"/", 1 , match.getEventFile().getEvents()) + ";");
						}
					}
					break;
				case "LAST_WICKET":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamBadge() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getFull_name().toUpperCase()+ ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getFull_name().toUpperCase() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "LAST WICKET : " + CricketFunctions.getLastWicket(match) + ";");
						}
					}
					break;
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 39.0;");
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
	public void populateManhattan(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				int maxRuns = 0,runsIncr = 0;
				double lngth = 0;
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ " " + " \0");				
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
								inn.getBatting_team().getTeamBadge() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + inn.getBatting_team().getTeamName1().
								toUpperCase() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRunRate" + " SET " + "RUN RATE: "  + inn.getRunRate() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + "OVERS "  + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + inn.getTotalRuns() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
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
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns6" + " SET " + "" + "\0");
				for(int i = 0; i < 5;i++) {
					runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + (5 - i) + " SET " + runsIncr*(i+1) + "\0");
				}
				
				for(int j=1; j <= match.getSetup().getMaxOvers(); j++) {
					if((j*6) <= CricketFunctions.getBallCountStartAndEndRange(match, match.getMatch().getInning().get(whichInning-1)).get(1)) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBarColour" + j + " SET " + "P1" + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBarColour" + j + " SET " + "NP" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$TeamAll1$BarGrp$BarAll$Bar" + (j) + "*ACTIVE SET 0" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vWkt" + (j) + " SET " + "0" + "\0");
					//System.out.println(Integer.valueOf(
									//CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEvents()).get(j).getOverTotalWickets()));
					if(j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size()) {
						lngth = ((32.1 *Integer.valueOf(
								CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns())) / maxRuns); // 32 is max value of each bar
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$TeamAll1$BarGrp$BarAll$Bar" + (j) + "*ACTIVE SET 1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$TeamAll1$BarGrp$BarAll*FUNCTION*BarValues*Bar_Value__" + (j) + " SET " + lngth + "\0");
						
						if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vWkt" + (j) + " SET " + Integer.valueOf(
									CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + "\0");
	
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vWkt" + (j) + " SET " + "0" + "\0");
						}
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$TeamAll1$BarGrp$BarAll$Bar" + (j) + "*ACTIVE SET 0" + "\0");
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100\0");
			TimeUnit.MILLISECONDS.sleep(1000);
	
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
			
	}
	public void populateWorm(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				String teamname = "";
				int maxRuns = 0,runsIncr = 0,row_id = 0;
				double Lngth = 0;
				double total_overs = 0;
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$Header$MaxSize$BatHeader*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Header$MaxSize$BatHeader*GEOM*TEXT SET "+ "COMPARISON" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo05" + "\0");
				
				if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 
						|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
				}else {
					if(((match.getMatch().getInning().get(0).getTotalOvers()*6) + match.getMatch().getInning().get(0).getTotalBalls()) > 
					((match.getMatch().getInning().get(1).getTotalOvers()*6) + match.getMatch().getInning().get(1).getTotalBalls())) {
						for(Inning inn : match.getMatch().getInning()) {
							if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase("NO")) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ 
									"AT THIS STAGE " + match.getMatch().getInning().get(0).getBatting_team().getTeamName1().toUpperCase() + " WERE: " 
										+ CricketFunctions.compareInningData(match,"-", 1 , match.getEventFile().getEvents()) + " \0");
							}
						}
					}
				}
				
				List<String> overByOverRuns = new ArrayList<String>();
				for(int inn_count = 1; inn_count <= whichInning; inn_count++)
				{
					overByOverRuns.clear();
					for(OverByOverData Over : CricketFunctions.getOverByOverData(match,inn_count ,"WORM" ,match.getEventFile().getEvents())) {
						overByOverRuns.add(String.valueOf(Over.getOverTotalRuns()));
					}
					
					String cumm_runs = String.join(",", overByOverRuns); // Store Per Overs Runs
					
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
						teamname = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
						
					} else {
						teamname = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
					}
					
					for(int k = 0; k < 5; k++) {           // For Y-Axis Value 
						runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36	
					 	print_writer.println("-1 RENDERER*TREE*$Main$$All$Worm$WormAll$WormGrp$Man20$group$PlayerNameGrp$Row" + (5 - k) + "$RowAni$Runs*GEOM*TEXT SET " + 
					 			runsIncr *  (k + 1) + "\0");
					}
					
					row_id = row_id + 1;
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$TeamName*GEOM*TEXT SET " + 
						teamname + " \0");
					
					if(match.getMatch().getInning().get(inn_count-1).getTotalWickets() < 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Score*GEOM*TEXT SET "+ 
								match.getMatch().getInning().get(inn_count-1).getTotalRuns() + "-" + match.getMatch().getInning().get(inn_count-1).getTotalWickets() + " \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Score*GEOM*TEXT SET "+ 
								match.getMatch().getInning().get(inn_count-1).getTotalRuns() + " \0");
					}
					
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Overs*GEOM*TEXT SET "+ 
						CricketFunctions.OverBalls(match.getMatch().getInning().get(inn_count-1).getTotalOvers(), match.getMatch().getInning().get(inn_count-1).getTotalBalls()) + " \0");
					
//					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXFit SET 0.965 \0");
//					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXFit SET 0.965 \0");
//	
//					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXOffset SET 1 \0");
//					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXOffset SET 1 \0");
//	
//					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
//							"$group*FUNCTION*ControlParameter*input SET " + (Math.floor(Lngth * 1e1) / 1e1) + " \0");
					
//					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "*GEOM*DataY SET " + 
//							cumm_runs.replaceFirst("0,", "") + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "*GEOM*DataY SET " + 
							cumm_runs + " \0");
					
					if(inn_count == 1) {
						if(match.getMatch().getInning().get(0).getTotalBalls() > 0) {
							total_overs = ((match.getMatch().getInning().get(0).getTotalOvers() + 1) / 2);
							total_overs = (total_overs * 0.1);
						}else {
							total_overs = match.getMatch().getInning().get(0).getTotalOvers() / 2;
							total_overs = (total_overs * 0.1);
						}
						Lngth =  (83.7 / maxRuns); // 100 is max value of each bar DataXOffset SET 9.0
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vDataScaleY" + " SET " + Lngth + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "DataXScale SET" + " SET " + total_overs + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 0 \0");
					}else {
						if(match.getMatch().getInning().get(1).getTotalBalls() > 0) {
							total_overs = ((match.getMatch().getInning().get(1).getTotalOvers() + 1) / 2);
							total_overs = (total_overs * 0.1);
						}else {
							total_overs = match.getMatch().getInning().get(1).getTotalOvers() / 2;
							total_overs = (total_overs * 0.1);
						}
						Lngth =  (83.7 / maxRuns); // 100 is max value of each bar
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vDataScaleY" + " SET " + Lngth + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "DataXScale SET" + " SET " + total_overs + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 1 \0");
					}
					
					for (int j = 1; j <= match.getSetup().getMaxOvers(); j++) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$Wkt" + (j) + 
								"*FUNCTION*Omo*vis_con SET " + "0" + " \0");
						if(j < CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).size()) {
							if(CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$Wkt" + (j) + 
									"*FUNCTION*Omo*vis_con SET " + Integer.valueOf(CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,
											match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
								
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$Wkt" + (j) + 
									"*FUNCTION*Omo*vis_con SET " + "0" + " \0");
							}
						}
					}
				}	
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$DataIn 1.780 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
	
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
			
	}
	public void populateHighestScore(PrintWriter print_writer,String viz_scene,List<Tournament> tournament_high_score,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException 
	{
		int row_no = 0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			
			int omo_num = 0;
			String cont_name = "";
			
			List<BestStats> top_ten_beststat = new ArrayList<BestStats>();
			for(Tournament tourn : tournament_high_score) {
				for(BestStats bs : tourn.getBatsman_best_Stats()) {
					top_ten_beststat.add(bs);
				}
			}
			
			Collections.sort(top_ten_beststat, new CricketFunctions.PlayerBestStatsComparator());
	
			for(BestStats Top_ten_bs : top_ten_beststat) {
				System.out.println("Best Stats : " + Top_ten_bs);
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "HIGHEST INDIVIDUAL SCORE" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "HIGHEST INDIVIDUAL SCORE" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo" + " SET " + logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	
	
	 		print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL*FUNCTION*Omo*vis_con SET " + "3" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");
	
	
	 		print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "SCORE" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "BALLS" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "OPPONENT TEAM" + "\0");
	 		
			
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
	
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");
	
			 		print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + top_ten_beststat.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
			 		
			 		if(top_ten_beststat.get(i).getBestEquation() % 2 == 0) {
			 			print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
									+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + top_ten_beststat.get(i).getBestEquation() / 2  + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
									+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + top_ten_beststat.get(i).getBestEquation() / 2 + "*" + "\0");
					}
			 		print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
											+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + top_ten_beststat.get(i).getBalls() + "\0");
			 		
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$ROWCOLNEW$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatValueGrp$StatValue3*GEOM*TEXT SET " + top_ten_beststat.get(i).getOpponentTeam().getTeamName4().toUpperCase() + "\0");
				}	
			}
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateBatGriff(PrintWriter print_writer,String viz_scene,int whichinning, int PlayerId, List<HeadToHeadPlayer> headToHead,CricketService cricketService,
			MatchAllData match ,String session_selected_broadcaster, Configuration config) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			this.status = CricketUtil.SUCCESSFUL;
			int omo_num = 0,runs = 0;
			int bat_row_no = 0;
			
			//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumSponsor" + " SET " + sponsor_path + "" + "\0");
			
			Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichinning).findAny().orElse(null);
			Player player = cricketService.getAllPlayer().stream().filter(plyr ->plyr.getPlayerId() == PlayerId).findAny().orElse(null);
			Team team = cricketService.getTeams().stream().filter(tm->tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
			
			if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inning.getInningNumber(), PlayerId).equalsIgnoreCase(CricketUtil.YES)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Griffnew$Header$Impact$*ACTIVE SET 1"+"\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Griffnew$Header$Impact$*ACTIVE SET 0"+"\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Griffnew$noname*ACTIVE SET " + "0" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + 
					player.getFull_name() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + 
					team.getTeamName1() + "\0");
				
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + photo_path + 
							team.getTeamName4() + "\\\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + "\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + 
							team.getTeamName4()+ "\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
//				
				boolean playerFound = false;
				int count = 0,row_id = 0;
				String MatchName = "";
				
				for(HeadToHeadPlayer h2h : headToHead) {
//					System.out.println("HOME : " + h2h.getTeam() + " - OTHER : " + h2h.getOpponentTeam());
					if(h2h.getPlayerId() == PlayerId && h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
						row_id++;
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + h2h.getOpponentTeam().getTeamName1() + "\0");
						MatchName = h2h.getMatchFileName();
						if(h2h.getInningStarted().contains("Y")) {
							if(h2h.getDismissed().contains("N")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + h2h.getRuns()+"*" + "\0");
							}else if(h2h.getDismissed().contains("Y")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + h2h.getRuns() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + h2h.getBallsFaced() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + "DNB" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + "" + "\0");
						}
						count = 0;
					}else if(h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
						if(!MatchName.equalsIgnoreCase(h2h.getMatchFileName()) && count <= 11) {
							MatchName = h2h.getMatchFileName();
							count = 1;
						}else if(count == 11) {
								row_id++;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + h2h.getOpponentTeam().getTeamName1() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + "DNP" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + "" + "\0");
								count = 0;
						}else {
							count++;
						}
					}
				}
				for(BattingCard bc : inning.getBattingCard()) {
					if(bc.getPlayerId() == PlayerId) {
						row_id++;
						playerFound = true;
						if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + inning.getBowling_team().getTeamName1() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + "DNB" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + "" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + inning.getBowling_team().getTeamName1() + "\0");
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + bc.getRuns() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + bc.getRuns()+"*" + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + bc.getBalls() + "\0");
						}
					}
				}
				if(!playerFound) {
					row_id++;
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + 
							inning.getBowling_team().getTeamName4() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + "DNP" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + "" + "\0");
				}
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "ttStatValue3" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vInfoOmo" + " SET " + "0" + "\0");
			
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.920 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
			break;
		}
	}
	public void populateBallGriff(PrintWriter print_writer,String viz_scene,int whichinning, int PlayerId, List<HeadToHeadPlayer> headToHead,CricketService cricketService,
			MatchAllData match ,String session_selected_broadcaster,Configuration config) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			boolean playerFound = false;
			int count = 0, row_id = 0;
			String MatchName = "";
			Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichinning).findAny().orElse(null);
			Player player = cricketService.getAllPlayer().stream().filter(plyr ->plyr.getPlayerId() == PlayerId).findAny().orElse(null);
			Team team = cricketService.getTeams().stream().filter(tm->tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
			if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inning.getInningNumber(), PlayerId).equalsIgnoreCase(CricketUtil.YES)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Griffnew$Header$Impact$*ACTIVE SET 1"+"\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Griffnew$Header$Impact$*ACTIVE SET 0"+"\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Griffnew$noname*ACTIVE SET " + "0" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + 
					player.getFull_name() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + 
					team.getTeamName1() + "\0");
				
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + photo_path + 
							team.getTeamName4() + "\\\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage SET " + "\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + 
							team.getTeamName4()+ "\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
			
			for(HeadToHeadPlayer h2h : headToHead) {
				if(h2h.getPlayerId() == PlayerId && h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
					row_id++;
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + h2h.getOpponentTeam().getTeamName1() + "\0");
					if(h2h.getBallsBowled() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + "DNB" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + "" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + h2h.getWickets() +"-"+h2h.getRunsConceded() + "\0");
						if(h2h.getBallsBowled()%6 == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + (h2h.getBallsBowled()/6) + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + (h2h.getBallsBowled()/6)+"."+h2h.getBallsBowled()%6 + "\0");
						}
					}
					count = 0;
				}else if(h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
					if(count == 11) {
						row_id++;
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + h2h.getOpponentTeam().getTeamName1() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + "DNP" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + "" + "\0");
						count = 0;
					}else if(!MatchName.equalsIgnoreCase(h2h.getMatchFileName()) && count < 11) {
						MatchName = h2h.getMatchFileName();
						count = 1;
					}else {
						count++;
					}
				}
			}
			
			boolean playerIsInBoc = false;
			if(inning.getBowlingCard() != null) {
				for(BowlingCard boc : inning.getBowlingCard()) {
					if(boc.getPlayerId() == PlayerId) {
						playerIsInBoc = true;
						row_id++;
						if(boc.getWickets() == 0 && boc.getRuns()==0 && (boc.getBalls()==0 && boc.getOvers() ==0)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + inning.getBatting_team().getTeamName1() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + "DNB" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + "" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + inning.getBatting_team().getTeamName1() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + boc.getWickets()+"-"+ boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
						}
						break;
					}else {
						playerIsInBoc = false;
					}
				}
			}
			
			if(!playerIsInBoc) {
				row_id++;
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_id + " SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_id + " SET " + "v " + inning.getBatting_team().getTeamName1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_id + " SET " + "DNB" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_id + " SET " + "" + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "ttStatValue3" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vInfoOmo" + " SET " + "0" + "\0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.920 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,String thisSeriesOrAllSeason,List<Tournament> this_series,
			MatchAllData match, String session_selected_broadcaster, Statistics stats,CricketService cricketService, Configuration config) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				double strike_rate = 0 , economy_rate=0;
				DecimalFormat df = new DecimalFormat("0.00");
				Player player = cricketService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == Playerid).findAny().orElse(null);
				Team team = cricketService.getTeams().stream().filter(tm -> tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
				print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSponsor" + " SET " + sponsor_path  + "ShariefBhai" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path 
						+ team.getTeamBadge() + "\0");
				
				if(player.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + player.getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + player.getSurname().toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + player.getFirstname().toUpperCase() + "\0");
				}
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + photo_path 
							+ team.getTeamName4() + "\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path 
							+ team.getTeamName4() + "\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), 1, Playerid).equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Impact*ACTIVE SET 1 " + "\0");
				}else if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), 2, Playerid).equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Impact*ACTIVE SET 1 " + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Impact*ACTIVE SET 0 " + "\0");
				}
				switch (thisSeriesOrAllSeason.toUpperCase()) {
				case "AFGHANISTANSEASON1": case "AFGHANISTANSEASON2":
					if(thisSeriesOrAllSeason.equalsIgnoreCase("AFGHANISTANSEASON1")) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "AFGHANISTAN TROPHY 2022" + "\0");
					}else if(thisSeriesOrAllSeason.equalsIgnoreCase("AFGHANISTANSEASON2")) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "AFGHANISTAN TROPHY 2023" + "\0");
					}
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");

						if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + "-" + "\0");

						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBallsFaced();
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(stats.getRuns(), stats.getBallsFaced(), 0) + "\0");
						}
						 
						break;
					case CricketUtil.BOWLER:
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");

						if(stats.getBallsBowled() == 0 || stats.getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRunsConceded()*1.00) /stats.getBallsBowled();
							economy_rate = economy_rate * 6;
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + df.format(economy_rate) + "\0");
						}
						break;
					}
					break;
				case "AFGHANISTANCAREER":
					print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "Afghanitan TROPHY CAREER" + "\0");
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");

						if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + "-" + "\0");

						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBallsFaced();
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(stats.getRuns(), stats.getBallsFaced(), 0) + "\0");
						}
						 
						break;
					case CricketUtil.BOWLER:
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");

						if(stats.getBallsBowled() == 0 || stats.getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRunsConceded()*1.00) /stats.getBallsBowled();
							economy_rate = economy_rate * 6;
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + df.format(economy_rate) + "\0");
						}
						break;
					}
					break;

				case "THISSERIES":
					print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "THIS SEASON" + "\0");	
					for(int i = 0; i <= this_series.size() - 1 ; i++) {
						if(this_series.get(i).getPlayerId() == Playerid) {
							switch(TypeofProfile.toUpperCase()) {
							case CricketUtil.BATSMAN:
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatValue*GEOM*TEXT SET " + this_series.get(i).getRuns() + "\0");

								if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + "-" + "\0");

								}else {
									strike_rate = this_series.get(i).getRuns() * 100;
									strike_rate = strike_rate/this_series.get(i).getBallsFaced();
									
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(this_series.get(i).getRuns(), this_series.get(i).getBallsFaced(), 0) + "\0");
								}
								 
								break;
							case CricketUtil.BOWLER:
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatValue*GEOM*TEXT SET " + this_series.get(i).getWickets() + "\0");

								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");

								if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + "-" + "\0");
								}else {
									economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
									economy_rate = economy_rate * 6;
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + df.format(economy_rate) + "\0");
								}
								break;
							}
							break;
						}
					}
					break;
				}		
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(200);
	
			}
			break;
		}
	}
	public void populateLTThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile, String whichSeason,List<Tournament> this_series,
			MatchAllData match, String session_selected_broadcaster, Statistics stats, CricketService cricketService) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				double strike_rate = 0 , economy_rate=0;
				int k=0;
				DecimalFormat df = new DecimalFormat("0.00");
				boolean playerFound = false;
				
				Player player = cricketService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == Playerid).findAny().orElse(null);
				Team team = cricketService.getTeams().stream().filter(tm -> tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
			
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
				
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSponsor" + " SET " + sponsor_path  + "ShariefBhai" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path 
						+ team.getTeamBadge() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + player.getFull_name() + "\0");
				if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), 1, Playerid).equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 1 " + "\0");
				}else if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), 2, Playerid).equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 1 " + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$All_Name$Impact*ACTIVE SET 0 " + "\0");
				}
				System.out.println(whichSeason);
				switch (whichSeason.toUpperCase()) {
				case "AFGHANISTANSEASON1": case "AFGHANISTANSEASON2":
					if(whichSeason.equalsIgnoreCase("AFGHANISTANSEASON1")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "AFGHANISTAN TROPHY 2022" + "\0");
					}else if(whichSeason.equalsIgnoreCase("AFGHANISTANSEASON2")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "Afghanitan TROPHY 2023" + "\0");
					}
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "STRIKE RATE" + "\0");
						if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " +CricketFunctions.generateStrikeRate(stats.getRuns(), stats.getBallsFaced(), 0)+ "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
						if(stats.getBestScore() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
									stats.getBestScore() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
									"-" + "\0");
						}
						 
						break;
					case CricketUtil.BOWLER:
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON" + "\0");

						if(stats.getBallsBowled() == 0 || stats.getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRunsConceded()*1.00) /stats.getBallsBowled();
							economy_rate = economy_rate * 6;
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
						if(stats.getBestFigures() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestFigures() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
						}
						
						break;
					}
					break;
				case "AFGHANISTANCAREER":
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "AFGHANISTAN TROPHY CAREER" + "\0");
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "STRIKE RATE" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
						if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " +CricketFunctions.generateStrikeRate(stats.getRuns(), stats.getBallsFaced(), 0)+ "\0");
						}
						playerFound = false;
						for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
							
							if(top_batsman_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									playerFound = true;
									k += 1;
									if(top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
										if(stats.getBestScore().contains("*")) {
											if(Integer.valueOf(stats.getBestScore().replace("*", ""))>(top_batsman_beststats.get(j).getBestEquation()/2)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestScore() + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + top_batsman_beststats.get(j).getBestEquation()/2 + "\0");
											}
										}else {
											if(Integer.valueOf(stats.getBestScore())>(top_batsman_beststats.get(j).getBestEquation()/2)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestScore() + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + top_batsman_beststats.get(j).getBestEquation()/2 + "\0");
											}
										}
									}else {
										if(stats.getBestScore().contains("*")) {
											if(Integer.valueOf(stats.getBestScore().replace("*", ""))>(top_batsman_beststats.get(j).getBestEquation()/2)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestScore()+ "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
														(top_batsman_beststats.get(j).getBestEquation()-1)/2 + "*" + "\0");
											}
										}else {
											if(Integer.valueOf(stats.getBestScore())>(top_batsman_beststats.get(j).getBestEquation()/2)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
														stats.getBestScore() + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
														(top_batsman_beststats.get(j).getBestEquation()-1) / 2 + "*" + "\0");
											}
										}
									}
									break;
								}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}
						}
						if(playerFound == false) {
							if(stats.getBestScore() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestScore() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}
						}
						 
						break;
					case CricketUtil.BOWLER:
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
						if(stats.getBallsBowled() == 0 || stats.getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRunsConceded()*1.00) /stats.getBallsBowled();
							economy_rate = economy_rate * 6;
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
						}
						playerFound = false;
						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
							if(top_bowler_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									playerFound = true;
									k += 1;
									if(top_bowler_beststats.get(j).getBestEquation() > 0) {
										if(top_bowler_beststats.get(j).getBestEquation() % 1000 >= 0) {
											if(stats.getBestFigures().contains("-")) {
												if(Integer.valueOf(stats.getBestFigures().split("-")[0])>((top_bowler_beststats.get(j).getBestEquation() / 1000) +1)){
													print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestFigures().split("-")[0]+"-" +stats.getBestFigures().split("-")[1]+ "\0");
												}else if(Integer.valueOf(stats.getBestFigures().split("-")[1]) == (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000))) {
													System.out.println(stats.getBestFigures().split("-")[1]+" : "+(1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)));
													if(Integer.valueOf(stats.getBestFigures().split("-")[1]) > (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000))) {
														print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
																stats.getBestFigures().split("-")[0]+"-"+stats.getBestFigures().split("-")[1] + "\0");
													}else {
														print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
																((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
													}
												}else {
													print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
															((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
												}
											}else {
												System.out.println("HI1");
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
											}
											
										}
										else if(top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
											if(stats.getBestFigures().contains("-")) {
												if(Integer.valueOf(stats.getBestFigures().split("-")[0])>((top_bowler_beststats.get(j).getBestEquation() / 1000))){
													print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestFigures().split("-")[0]+"-"+stats.getBestFigures().split("-")[1] + "\0");
												}else if(Integer.valueOf(stats.getBestFigures().split("-")[0]) == ((top_bowler_beststats.get(j).getBestEquation() / 1000))) {
													if(Integer.valueOf(stats.getBestFigures().split("-")[1]) > Math.abs(top_bowler_beststats.get(j).getBestEquation())) {
														print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
																stats.getBestFigures().split("-")[0]+"-"+stats.getBestFigures().split("-")[1] + "\0");
													}else {
														print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
																((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
													}
												}else {
													print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
															(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
												}
											}else {
												System.out.println("HI2");
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
											}
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestFigures().split("-")[0]+"-" +stats.getBestFigures().split("-")[1]+ "\0");
									}
									break;
								}
								break;
							}
						}
						if(playerFound == false) {
							if(stats.getBestFigures() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestFigures() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}
						}
						break;
					}
					break;

				case "THISSERIES":
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "THIS SEASON" + "\0");	
					for(int i = 0; i <= this_series.size() - 1 ; i++) {
						if(this_series.get(i).getPlayerId() == Playerid) {
							switch(TypeofProfile.toUpperCase()) {
							case CricketUtil.BATSMAN:
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "STRIKE RATE" + "\0");
								if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");	
								}else {
									strike_rate = this_series.get(i).getRuns() * 100;
									strike_rate = strike_rate/this_series.get(i).getBallsFaced();
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + CricketFunctions.generateStrikeRate(this_series.get(i).getRuns(), this_series.get(i).getBallsFaced(), 0) + "\0");
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
								if(this_series.get(i).getWickets() == 0) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + "-" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getWickets() + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON" + "\0");
		
								if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
								}else {
									economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
									economy_rate = economy_rate * 6;
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
											}else {
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
							break;
						}
					}
					break;
				}		
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.status = CricketUtil.SUCCESSFUL;
	
			}
			break;
		}
	}
	public void populateDuckWorthLewis(PrintWriter print_writer,String viz_scene,String balls,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "AFGHANISTAN_T20":
				Document htmlFile = null; 
					
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1()
						+ " " + match.getMatch().getInning().get(1).getTotalRuns() + "-" + match.getMatch().getInning().get(1).getTotalWickets() + "\0");
				
				try { 
					for(Inning inn : match.getMatch().getInning()) {
						if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
							htmlFile = Jsoup.parse(new File("C:\\Sports\\ParScores BB.html"), "ISO-8859-1");
						}
					}
				} catch (IOException e) {  
					e.printStackTrace(); 
				} 
				
				List<DuckWorthLewis> this_dls = new ArrayList<DuckWorthLewis>();
				for(int i=14; i<htmlFile.body().getElementsByTag("font").size() - 1;i++) {
					if(htmlFile.body().getElementsByTag("font").get(i).text().contains("TableID")) {
						i = i + 15;
						if(i > htmlFile.body().getElementsByTag("font").size()) {
							break;
						}
					}
					
					for(Inning inn : match.getMatch().getInning()) {
						if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
							this_dls.add(new DuckWorthLewis(htmlFile.body().getElementsByTag("font").get(i).text(),
									htmlFile.body().getElementsByTag("font").get(i+(2+(inn.getTotalWickets()))).text()));
						}
					}
					i = i +11;
					
				}
				
				for(int i = 0; i<= this_dls.size() -1;i++) {
					if(this_dls.get(i).getOver_left().equalsIgnoreCase(balls)) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "CURRENT DLS PAR SCORE AFTER "
								+ balls + " OVERS: " + (Integer.valueOf(this_dls.get(i).getWkts_down()) + 1) + "\0");			
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				
				break;
		}
	}
	public void populateDuckWorthLewisEquation(PrintWriter print_writer,String viz_scene,String balls,MatchAllData match,String session_selected_broadcaster, String session_directoryPath) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "AFGHANISTAN_T20":
				int runs = 0,total_runs=0;
				String ahead_behind = "";
				Document htmlFile = null;
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1()
						+ " : " + match.getMatch().getInning().get(1).getTotalRuns() + "-" + match.getMatch().getInning().get(1).getTotalWickets() + "\0");
				
				try { 
					for(Inning inn : match.getMatch().getInning()) {
						if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
							htmlFile = Jsoup.parse(new File("C:\\Sports\\ParScores BB.html"), "ISO-8859-1");
						}
					}
				} catch (IOException e) {  
					e.printStackTrace(); 
				}
				List<DuckWorthLewis> this_dls = new ArrayList<DuckWorthLewis>();
				for(int i=14; i<htmlFile.body().getElementsByTag("font").size() - 1;i++) {
					if(htmlFile.body().getElementsByTag("font").get(i).text().contains("TableID")) {
						i = i + 15;
						if(i > htmlFile.body().getElementsByTag("font").size()) {
							break;
						}
					}
					
					for(Inning inn : match.getMatch().getInning()) {
						if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
							total_runs = inn.getTotalRuns();
							this_dls.add(new DuckWorthLewis(htmlFile.body().getElementsByTag("font").get(i).text(),
									htmlFile.body().getElementsByTag("font").get(i+(2+(inn.getTotalWickets()))).text()));
						}
					}
					i = i +11;
					
				}
				for(int i = 0; i<= this_dls.size() -1;i++) {
					if(this_dls.get(i).getOver_left().equalsIgnoreCase(balls)) {
						runs = (total_runs) - Integer.valueOf((CricketFunctions.populateDuckWorthLewis(match, session_directoryPath).get(i).getWkts_down()));
                        if(runs < 0){
                            ahead_behind = " (" + (Math.abs(runs)) + " runs behind)";
                        }else if (runs > 0){
                            ahead_behind = " (" + runs + " runs ahead)";
                        }else if(runs == 0) {
                        	ahead_behind = " (SCORES ARE LEVEL)";
                        }
						
                        print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "DLS PAR SCORE AFTER "
								+ balls + " OVERS : " + (Integer.valueOf(this_dls.get(i).getWkts_down())) + ahead_behind.toUpperCase() + "\0");		
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				TimeUnit.MILLISECONDS.sleep(1000);
				break;
		}
	}
	
	public void populateFFInnBuilder(PrintWriter print_writer,String viz_scene,int whichInning,int playerId,List<Player> players,List<Team> teams, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id=0,total_balls=0,maxRuns=0,runsIncr=0;
			double lngth = 0;
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "INNINGS BUILDER" + "\0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					for(BattingCard bc : inn.getBattingCard()) {
						if(playerId == bc.getPlayerId()) {
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + logo_path + 
									teams.get(bc.getPlayer().getTeamId()-1).getTeamBadge() + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + photo_path +
										teams.get(bc.getPlayer().getTeamId()-1).getTeamName4().toUpperCase() + "\\\\\\" + bc.getPlayer().getPhoto() + 
										CricketUtil.PNG_EXTENSION + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path +
										teams.get(bc.getPlayer().getTeamId()-1).getTeamName4().toUpperCase() + "\\\\" + bc.getPlayer().getPhoto() + 
										CricketUtil.PNG_EXTENSION + "\0");
							}
							
							
							if(bc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + bc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + bc.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + bc.getPlayer().getFirstname() + "\0");
							}
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + bc.getRuns() + "*" + "\0");
							}else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + bc.getRuns() + "\0");
							}
								
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + bc.getBalls() + "\0");
							
							maxRuns = bc.getRuns();
							total_balls = bc.getBalls();
							
							while (maxRuns % 5 != 0) {     // 5 label in y-axis
						 		maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
							}
						}
					}

					for(int i = 0; i < 5;i++) {
						runsIncr = maxRuns / 5;
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + (i+1) + " SET " + ((runsIncr*i)+1) + " - " + 
								runsIncr*(i+1) + "\0");
					}
					
					for (int i = 0; i < CricketFunctions.getPlayerSplit(whichInning,playerId, runsIncr,total_balls,match,match.getEventFile().getEvents()).size(); i++) {
						row_id = 5 - i;
						if(row_id <= 5) {
							lngth = ((110 * Integer.valueOf(CricketFunctions.getPlayerSplit(whichInning,playerId,runsIncr,total_balls,match,match.getEventFile().
									getEvents()).get(i))) / (double)maxRuns);
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$BarAll$Bar$BarGrp$BarAll1*FUNCTION*BarValues*Bar_Value__" + (row_id) + " SET " + lngth + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$BarAll$Bar$BarGrp$BarAll2*FUNCTION*BarValues*Bar_Value__" + (row_id) + " SET " + 
									CricketFunctions.getPlayerSplit(whichInning,playerId,runsIncr,total_balls,match,match.getEventFile().getEvents()).get(i) + "\0");
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$BG 1.100 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
				
		}
	}
	public void populateRicheis(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				String teamname = "";
				int maxRuns = 0,max_overs=0,runsIncr = 0,row_id = 0;
				double lngth = 0;
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "COMPARISON" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo05" + "\0");
				
				if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 
						|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					
				}else {
					if(((match.getMatch().getInning().get(0).getTotalOvers()*6) + match.getMatch().getInning().get(0).getTotalBalls()) > 
					((match.getMatch().getInning().get(1).getTotalOvers()*6) + match.getMatch().getInning().get(1).getTotalBalls())) {
						for(Inning inn : match.getMatch().getInning()) {
							if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase("NO")) {
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "AT THIS STAGE " + 
									match.getMatch().getInning().get(0).getBatting_team().getTeamName1().toUpperCase() + " WERE: " + 
										CricketFunctions.compareInningData(match,"-", 1 , match.getEventFile().getEvents()) + "\0");
							}
						}
					}
				}
				
				List<String> overByOverRuns = new ArrayList<String>();
				for(int inn_count = 1; inn_count <= whichInning; inn_count++)
				{
					row_id = row_id + 1;
					
					if(match.getMatch().getInning().get(inn_count-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {            
						teamname = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
						
					} else {
						teamname = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_id + " SET " + teamname + "\0");
					
					if(match.getMatch().getInning().get(inn_count-1).getTotalWickets() < 10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + row_id + " SET " + 
								match.getMatch().getInning().get(inn_count-1).getTotalRuns() + "-" + match.getMatch().getInning().get(inn_count-1).getTotalWickets() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + row_id + " SET " + 
								match.getMatch().getInning().get(inn_count-1).getTotalRuns() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamOvers" + row_id + " SET " + 
						CricketFunctions.OverBalls(match.getMatch().getInning().get(inn_count-1).getTotalOvers(), match.getMatch().getInning().get(inn_count-1).getTotalBalls()) + "\0");
					
					overByOverRuns.clear();
					for(int b=1;b<=5;b++) {
						if(b >= 5) {
							max_overs = match.getSetup().getMaxOvers();
						}else {
							max_overs = (b * 10);
						}
						
						int score=0;
						int wicket=0;
						for (int brk = max_overs - 9;brk <= max_overs ; brk++) {
							if(brk < CricketFunctions.getOverByOverData(match, inn_count,"MANHATTAN" ,match.getEventFile().getEvents()).size()){
								score = score + Integer.valueOf(CricketFunctions.getOverByOverData(match, inn_count,"MANHATTAN" ,match.getEventFile().
										getEvents()).get(brk).getOverTotalRuns());
								wicket = wicket + Integer.valueOf(CricketFunctions.getOverByOverData(match, inn_count,"MANHATTAN" ,match.getEventFile().
										getEvents()).get(brk).getOverTotalWickets()) ;
							}
						}
						overByOverRuns.add(score + "-" + wicket);
					}
					
					for(String OverRuns : overByOverRuns) {
						if(Integer.valueOf(OverRuns.split("-")[0]) > maxRuns) {
							maxRuns = Integer.valueOf(OverRuns.split("-")[0]);
							
							while (maxRuns % 5 != 0) {     // 5 label in y-axis
						 		maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
							}
						}
					}
					for(int i = 0; i < 5;i++) {
						runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + (5 - i) + " SET " + runsIncr*(i+1) + "\0");
					}
					
					for(int j=0;j<=overByOverRuns.size()-1;j++) {
						lngth = ((110 * Integer.valueOf(overByOverRuns.get(j).split("-")[0]) / maxRuns));
						print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$Bar$BarGrp$BarAll" + inn_count + 
								"*FUNCTION*BarValues*Bar_Value__" + (j+1) + " SET " + lngth + "\0");
					}
					
				}	
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.780 In$DataIn 1.780 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
	
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}	
	}
	public void populateLtWeather(PrintWriter print_writer, String viz_scene, CricketService cricketService) {
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTemperature" + " SET " + cricketService.getWeather().get(0).getCurrentTemp() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHumidity" + " SET " + cricketService.getWeather().get(0).getHumidity() + "\0");
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	}
	
	public void populateLtPhaseByComparison(PrintWriter print_writer,String viz_scene, int inning_number, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			int oneToSixRuns1 = 0, sevenToFifteenRuns1 = 0, sixteenToTweentyRuns1 = 0,oneToSixfWkt1 = 0, sevenToFifteenWkt1 = 0, sixteenToTweentyWkt1 = 0;
			int oneToSixRuns2 = 0, sevenToFifteenRuns2 = 0, sixteenToTweentyRuns2 = 0,oneToSixfWkt2 = 0, sevenToFifteenWkt2 = 0, sixteenToTweentyWkt2 = 0, currentOver = 0;
			
			currentOver = match.getMatch().getInning().get(1).getTotalOvers();
			List<OverByOverData> overByOverData1 = CricketFunctions.getOverByOverData(match, 1, "MANHATTAN", match.getEventFile().getEvents());
			List<OverByOverData> overByOverData2 = CricketFunctions.getOverByOverData(match, 2, "MANHATTAN", match.getEventFile().getEvents());
			
			for(int j=1; j<=overByOverData1.size()-1; j++) {
				if(j>0 && j<=6) {
					oneToSixRuns1+= overByOverData1.get(j).getOverTotalRuns();
					oneToSixfWkt1+=overByOverData1.get(j).getOverTotalWickets();
				}
				if(j>6 && j<=15) {
					sevenToFifteenRuns1+= overByOverData1.get(j).getOverTotalRuns();
					sevenToFifteenWkt1+=overByOverData1.get(j).getOverTotalWickets();
				}
				if(j>15 && j<=20) {
					sixteenToTweentyRuns1+= overByOverData1.get(j).getOverTotalRuns();
					sixteenToTweentyWkt1+=overByOverData1.get(j).getOverTotalWickets();
				}
			}
			for(int j=1; j<=overByOverData2.size()-1; j++) {
				if(j>0 && j<=6) {
					oneToSixRuns2+= overByOverData2.get(j).getOverTotalRuns();
					oneToSixfWkt2+=overByOverData2.get(j).getOverTotalWickets();
				}
				if(j>6 && j<=15) {
					sevenToFifteenRuns2+= overByOverData2.get(j).getOverTotalRuns();
					sevenToFifteenWkt2+=overByOverData2.get(j).getOverTotalWickets();
				}
				if(j>15 && j<=20) {
					sixteenToTweentyRuns2+= overByOverData2.get(j).getOverTotalRuns();
					sixteenToTweentyWkt2+=overByOverData2.get(j).getOverTotalWickets();
				}
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "PHASE-WISE SCORES COMPARISON" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + "TLogo05" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " + match.getMatch().getInning().get(0).getBatting_team().getTeamName1() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "1-6" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "7-15" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "16-20" + "\0");
			
			if(oneToSixRuns1 == 0 && oneToSixfWkt1 == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore1A" + " SET " + "-" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore1A" + " SET " + oneToSixRuns1+"-"+oneToSixfWkt1 + "\0");
			}
			if(sevenToFifteenRuns1 == 0 && sevenToFifteenWkt1 == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore1B" + " SET " + "-" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore1B" + " SET " + sevenToFifteenRuns1+"-"+sevenToFifteenWkt1 + "\0");
			}
			if(sixteenToTweentyRuns1 == 0 && sixteenToTweentyWkt1 == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore1C" + " SET " + "-" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore1C" + " SET " + sixteenToTweentyRuns1+"-"+sixteenToTweentyWkt1 + "\0");
			}
			
			if(oneToSixRuns2 == 0 && oneToSixfWkt2 == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore2A" + " SET " + "-" + "\0");
			}else {
				if(currentOver>=0 && currentOver<=6) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore2A" + " SET " + oneToSixRuns2+"-"+oneToSixfWkt2+"*" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore2A" + " SET " + oneToSixRuns2+"-"+oneToSixfWkt2 + "\0");
				}
				
			}
			if(sevenToFifteenRuns2 == 0 && sevenToFifteenWkt2 == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore2B" + " SET " + "-" + "\0");
			}else {
				if(currentOver>=7 && currentOver<=15) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore2B" + " SET " + sevenToFifteenRuns2+"-"+sevenToFifteenWkt2+"*" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore2B" + " SET " + sevenToFifteenRuns2+"-"+sevenToFifteenWkt2 + "\0");
				}
				
			}
			if(sixteenToTweentyRuns2 == 0 && sixteenToTweentyWkt2 == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore2C" + " SET " + "-" + "\0");
			}else {
				if(currentOver>=16 && currentOver<=20) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore2C" + " SET " + sixteenToTweentyRuns2+"-"+sixteenToTweentyWkt2+"*" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore2C" + " SET " + sixteenToTweentyRuns2+"-"+sixteenToTweentyWkt2 + "\0");
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.780 \0");
		TimeUnit.MILLISECONDS.sleep(1000);
	}
	
	public void populateLtPhaseByScore(PrintWriter print_writer,String viz_scene, int inning_number, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				Inning inning = match.getMatch().getInning().stream().filter(inn->inn.getInningNumber() == inning_number).findAny().orElse(null);
				int oneToSixRuns = 0, sevenToFifteenRuns = 0, sixteenToTweentyRuns = 0,oneToSixfWkt = 0, sevenToFifteenWkt = 0, sixteenToTweentyWkt = 0, currentOver = 0;
				List<OverByOverData> overByOverData = CricketFunctions.getOverByOverData(match, inning_number, "MANHATTAN", match.getEventFile().getEvents());
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$StatHead01*GEOM*TEXT SET "+ "OVERS" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$StatHead02*GEOM*TEXT SET "+ "SCORE" + " \0");
				
				currentOver = inning.getTotalOvers();
				for(int i=0; i<2; i++) {
					if(match.getMatch().getInning().get(i).getInningNumber() == inning_number) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + match.getMatch().getInning().get(i).getBatting_team().getTeamBadge() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ match.getMatch().getInning().get(i).getBatting_team().getTeamName1().toUpperCase() + " \0");
						
						for(int j=1; j<=overByOverData.size()-1; j++) {
							if(j>0 && j<=6) {
								oneToSixRuns+= overByOverData.get(j).getOverTotalRuns();
								oneToSixfWkt+=overByOverData.get(j).getOverTotalWickets();
							}
							if(j>6 && j<=15) {
								sevenToFifteenRuns+= overByOverData.get(j).getOverTotalRuns();
								sevenToFifteenWkt+=overByOverData.get(j).getOverTotalWickets();
							}
							if(j>15 && j<=20) {
								sixteenToTweentyRuns+= overByOverData.get(j).getOverTotalRuns();
								sixteenToTweentyWkt+=overByOverData.get(j).getOverTotalWickets();
							}
						}
					}
				}
				if(inning.getTotalWickets()>9) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inning.getTotalRuns() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inning.getTotalRuns()+"-"+inning.getTotalWickets() + "\0");
				}
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1A*GEOM*TEXT SET "+ "1-6" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1A*GEOM*TEXT SET "+ "7-15" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1A*GEOM*TEXT SET "+ "16-20" + " \0");
				
				if(oneToSixRuns == 0 && oneToSixfWkt == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET "+ "-" + " \0");
				}else {
					if(currentOver>=0 && currentOver<=6) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET "+ oneToSixRuns+"-"+oneToSixfWkt+"*" + " \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET "+ oneToSixRuns+"-"+oneToSixfWkt + " \0");
					}
				}
				if(sevenToFifteenRuns == 0 && sevenToFifteenWkt == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET "+ "-" + " \0");
				}else {
					if(currentOver>=7 && currentOver<=15) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET "+ sevenToFifteenRuns+"-"+sevenToFifteenWkt+"*" + " \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET "+ sevenToFifteenRuns+"-"+sevenToFifteenWkt + " \0");
					}
					
				}
				if(sixteenToTweentyRuns == 0 && sixteenToTweentyWkt == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET "+ "-" + " \0");
				}else {
					if(currentOver>=16 && currentOver<=20) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET "+ sixteenToTweentyRuns+"-"+sixteenToTweentyWkt+"*" + " \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET "+ sixteenToTweentyRuns +"-"+sixteenToTweentyWkt + " \0");
					}
					
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
			TimeUnit.MILLISECONDS.sleep(1000);
		}
	}


	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException
	{
		if(isAudioOn.equalsIgnoreCase("TRUE")) {
			print_writer.println("-1 RENDERER*TREE*$Audio1*AUDIO*VOLUME SET 100 \0");
			print_writer.println("-1 RENDERER*TREE*$Aiudio2*AUDIO*VOLUME SET 100 \0");
		}else {
			print_writer.println("-1 RENDERER*TREE*$Audio1*AUDIO*VOLUME SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Aiudio2*AUDIO*VOLUME SET 0 \0");
		}
		
		switch(whichGraphic) {
		case "PLOTTER_ICC":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Plotter START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "RESET":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentIn START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoIn START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Base2-4In START \0");
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman1In START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman2In START \0");
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$Base3In START \0");
			//TimeUnit.SECONDS.sleep(1);
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$BowlerIn START \0");
			
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "SCORECARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BOWLINGCARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "MATCHSUMMARY":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "PARTNERSHIP":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
			TimeUnit.MILLISECONDS.sleep(200);
			print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll*ACTIVE SET " + "1" + "\0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "PREVIOUS_SUMMARY":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "POINTSTABLE":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BOWLER":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$BowlerIn START \0");
			break;
		case "THIS_OVER":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$ThisOverIn START \0");
			break;
		case "TIMELINE":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$TimelineIn START \0");
			break;
		case "LEADERBOARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*HighlightAll$Highlight" + lb_count + "_In START \0");
			break;
		case "MOST_LEADERBOARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*HighlightAll$Highlight" + lb_count + "_In START \0");
			break;
		case "BAT-POPUP": case "BOWL-POPUP":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Change_Out SHOW 0.0 \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;
			
		case "BUG_PARTNERSHIP": case "MULTI_PARTNERSHIP": case "BUG_HIGHLIGHT": case "BUG-TOSS": case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB":
		case "BUGTARGET": case "BUG_POWERPLAY": 
		
		case "HOWOUT": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET": case "LTMATCH_PROMO": case "L3MATCHID": case "TARGET":  case "COMPARISION":
		case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS": case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": 
		case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "LT_THIS-SERIES": case "L3PLAYERPROFILE": case "LTPLAYERPROFILEBAT": case "EQUATION":
		case "BATSMAN_THIS_MATCH": case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE": case "BOWLER_STYLE": case "BATSMAN_STYLE": case "HOWOUT_WITHOUT": case "QUICK_HOWOUT":
		case "DLS_TARGET": case "DLS_EQUATION": case "LT_POINTERS": case "MOST": case "LT_THISSERIES_BALL":
		
		case "TEAMS_LOGO":case "MATCHID": case "FFPLAYERPROFILE": case "PLAYERPROFILEBALL": case "TEAMLINEUP": case "DOUBLETEAMS": case "LANDMARK": case "POSITION_LANDMARK":
		case "MANHATTAN":   case "MATCH_PROMO": case "MOSTRUNS": case "MOSTWICKETS": case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM": case "SQUAD": 
		case "TIEID-DOUBLE": case "FF_THIS-SERIES": case "FF_POINTERS": case "FF_FIXTURES": case "FIXTURES_TEAM": case "TEAM_SQUAD": case "INN_BUILDER": case "RICHEIS":
		case "PHASE_BY_SCORE": case "LINEUP": case "FFTHISSERIES_BALL": case "BALL_LANDMARK": case "WEATHER": case "IMPACT": case "PHASE-COMPARISON":
		
		case "MINI-SCORECARD": case "MINI-BOWLINGCARD": case "MANUAL": case "BATGRIFF": case "BALLGRIFF": case "PLAYOFF": case "MINI-BATSMAN_VS_ALLBOWLERS":
			
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "IN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "FF_IN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_LT_In START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}	
	}	
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException
	{
		switch(whichGraphic.toUpperCase()) {
		case "PLOTTER_ICC":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Plotter CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop CONTINUE \0");
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			TimeUnit.MILLISECONDS.sleep(1000);
			break;
		case "BATBALLSUMMARY_SCORECARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BATBALLSUMMARY_BOWLINGCARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BATBALLSUMMARY_MATCHSUMMARY":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "PREVIOUS_SUMMARY":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "POINTSTABLE":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "PARTNERSHIP":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "LEADERBOARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*HighlightAll$Highlight" + lb_count + "_Out START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "MOST_LEADERBOARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*HighlightAll$Highlight" + lb_count + "_Out START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "BAT-POPUP": case "BOWL-POPUP":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Change_Out SHOW 0.0 \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "BUG_PARTNERSHIP": case "MULTI_PARTNERSHIP": case "BUG_HIGHLIGHT": case "BUG-TOSS": case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET": case "MATCH_PROMO":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS": case "TEAMS_LOGO":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "MATCHID":
		case "LT_THIS-SERIES": case "L3PLAYERPROFILE": case "LTPLAYERPROFILEBAT": case "FFPLAYERPROFILE": case "PLAYERPROFILEBALL": case "TEAMLINEUP": case "DOUBLETEAMS":
		case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH": case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE": case "BOWLER_STYLE":
		case "BATSMAN_STYLE": case "MANHATTAN": case "HOWOUT_WITHOUT": case "SQUAD": case "FF_THIS-SERIES": case "MOSTRUNS": case "MOSTWICKETS":
		case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM": case "LTMATCH_PROMO": case "QUICK_HOWOUT": case "TIEID-DOUBLE": case "MINI-SCORECARD": case "MINI-BATSMAN_VS_ALLBOWLERS":
		case "MINI-BOWLINGCARD": case "BUG_POWERPLAY": case "MANUAL": case "BATGRIFF": case "BALLGRIFF": case "PLAYOFF": case "DLS_TARGET":
		case "DLS_EQUATION": case "LT_POINTERS": case "FF_POINTERS": case "FF_FIXTURES": case "FIXTURES_TEAM": case "TEAM_SQUAD": case "INN_BUILDER": case "RICHEIS":
		case "PHASE_BY_SCORE": case "LINEUP": case "MOST": case "LT_THISSERIES_BALL": case "FFTHISSERIES_BALL": case "BALL_LANDMARK": case "WEATHER": case "IMPACT":
		case "PHASE-COMPARISON":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "RIGHT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*BottomPartOut START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "FF_OUT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_LT_Out START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoOut START \0");
//			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			ident_on_screen = false;
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "ANIMATE-OUT-INFOBAR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "ANIMATE-OUT-IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}	
		this.status = "";
	}
	public String resetAnimation(PrintWriter print_writer,String which_broadcaster, String which_director) {
		String status = "";
		
		switch(which_broadcaster.toUpperCase()) {
		case "AFGHANISTAN_T20":
			switch(which_director.toUpperCase()) {
			case "FOURS":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FoursOut START \0");
				break;
	
			case "SIXES":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SixOut START \0");
				break;
			
			case "WICKETS":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*WicketsOut START \0");
				break;
				
			case "NO_BALL":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*NoBallOut START \0");
				break;
	
			case "FREE-HIT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHit_Out START \0");
				break;
				
			case "WIDE":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*WideOut START \0");
				break;
			case "POWERPLAY":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayOut START \0");
				break;
			}
			break;
		}
		
		return status;
	}
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
	public void PreviewFullFrame(PrintWriter print_writer,String viz_scene,String previous_gfx, String current_gfx)
	{
		String previewAnim = "";
		if(previous_gfx == "")
		{
			switch(current_gfx){
			case "SCORECARD":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 BattingCardIn 1.676 \0");
				break;
			case "BOWLINGCARD":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 BowlingCardIn 1.740 \0");
				break;
			case "MATCHSUMMARY":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 SummaryIn 1.316 \0");
				break;
			case "POINTSTABLE":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.400 \0");
				break;
			}		
		} else {
			switch(previous_gfx){
			case "BATBALLSUMMARY_SCORECARD":
				previewAnim = "BattingCardOut 0.828";
				//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn SHOW 0.0 \0");
				break;
			case "BATBALLSUMMARY_BOWLINGCARD":
				previewAnim = "BowlingCardOut 0.700";
				//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn SHOW 0.0 \0");
				
				break;
			case "BATBALLSUMMARY_MATCHSUMMARY":
				previewAnim = "SummaryOut 0.700";
				//print_writer.println("-1 RENDERER*STAGE SHOW 0.0\0");
				break;
			case "POINTSTABLE":
				previewAnim = "PointsTableOut 0.700";
				//print_writer.println("-1 RENDERER*STAGE SHOW 0.0\0");
				break;
			}
			switch(current_gfx){
			case "SCORECARD":
				previewAnim = previewAnim + " BattingCardIn 1.676";
				break;
			case "BOWLINGCARD":
				previewAnim = previewAnim + " BowlingCardIn 1.740";
				break;
			case "MATCHSUMMARY":
				previewAnim = previewAnim + " SummaryIn 1.316";
				break;
			case "POINTSTABLE":
				previewAnim = previewAnim + " PointsTableIn 1.400";
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png " + previewAnim + " \0");
		}
	}
}
	