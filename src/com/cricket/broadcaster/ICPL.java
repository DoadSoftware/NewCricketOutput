package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import jakarta.xml.bind.JAXBException;
import com.cricket.containers.Infobar;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBContext;
import com.cricket.containers.ContainerData;
import com.cricket.containers.Scene;
import com.cricket.model.MatchAllData;
import com.cricket.model.Statistics;
import com.cricket.service.CricketService;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Configuration;
import com.cricket.model.Event;
import com.cricket.model.FallOfWicket;
import com.cricket.model.Fixture;
import com.cricket.model.InfobarStats;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.LeagueTeam;
import com.cricket.model.NameSuper;
import com.cricket.model.OverByOverData;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ICPL extends Scene{

	public String broadcaster = "ICPL";
	public Infobar infobar = new Infobar();
	public String which_graphics_onscreen = "";
	private String slashOrDash = "-";
	public String status;
	public String director;
	private String logo_path = "C:\\Images\\ICPL\\LOGOS\\";
	private String photo_path = "C:\\Images\\ICPL\\Photos\\";
	private String local_photo_path = "\\\\c\\\\Images\\\\ICPL\\\\Photos\\\\";
	
	boolean ident_on_screen = false;

	private boolean is_powerplay_on_screen = false;
	
	public boolean isIs_powerplay_on_screen() {
		return is_powerplay_on_screen;
	}

	public void setIs_powerplay_on_screen(boolean is_powerplay_on_screen) {
		this.is_powerplay_on_screen = is_powerplay_on_screen;
	}
	
	public ICPL() {
		super();
	}
	public ICPL(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	public String resetInfobarAnimation(PrintWriter print_writer,String which_broadcaster) throws InterruptedException {
		String status = "";
		
		switch(which_broadcaster.toUpperCase()) {
		case "ICPL":
			if(infobar.isInfobar_on_screen() == true) {
				AnimateOutGraphics(print_writer, "FF_OUT");
				which_graphics_onscreen = "SCOREBUG";
			}
			break;
		}
		
		return status;
	}
	public Infobar updateInfobar(List<Scene> scenes, MatchAllData match, PrintWriter print_writer) throws InterruptedException
	{
		if(infobar.isInfobar_on_screen() == true) {
			populateInfobarTeamScore(true, print_writer, match, broadcaster);
			infobar = processInfobarPowerplay(infobar, print_writer, broadcaster, match);
			infobar = populateInfobarMiddleSection(infobar, true, print_writer, match, broadcaster, null);
			infobar = populateInfobarBottomRight(infobar, true,print_writer, match, broadcaster);
			infobar = populateBottomRightBottom(infobar, true, print_writer, match, broadcaster);
			
		}
		return infobar;
	}
	public Object processGraphics(String whatToProcess, String valueToProcess, MatchAllData match, List<MatchAllData> tournament_matches, 
			List<Scene> scenes,List<Statistics> statistics, CricketService cricketService, PrintWriter print_writer, Configuration config) 
			throws JAXBException, InterruptedException, NumberFormatException, ParseException, IllegalAccessException, InvocationTargetException, IOException
	{
		switch (whatToProcess.toUpperCase()) {
		case "BUG_GRAPHICS-OPTIONS": case "HOWOUT_GRAPHICS-OPTIONS": case "BATSMANSTATS_GRAPHICS-OPTIONS": case "BOWLERSTATS_GRAPHICS-OPTIONS": case "NAMESUPER_PLAYER_GRAPHICS-OPTIONS": 
		case "L3PLAYERPROFILE_GRAPHICS-OPTIONS": case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "BOTTOMLEFT_GRAPHICS-OPTIONS": case "BOTTOMRIGHT_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": 
		case "COMPARISION-GRAPHICS-OPTIONS": case "BOTTOM_GRAPHICS-OPTIONS": case "ANIMATE_PLAYINGXI-OPTIONS": case "PROJECTED_GRAPHICS-OPTIONS": case "TARGET_GRAPHICS-OPTIONS": case "GRIFF_GRAPHICS-OPTIONS":
		case "PLAYERSUMMARY_GRAPHICS-OPTIONS": case "BUG_DISMISSAL_GRAPHICS-OPTIONS": case "TOP_GRAPHICS-OPTIONS": case "BUG_BOWLER_GRAPHICS-OPTIONS": case "HOWOUT_WITHOUT_FIELDER_GRAPHICS-OPTIONS":
		case "BOWLERDETAILS_GRAPHICS-OPTIONS":	case "NEXTTOBAT_GRAPHICS-OPTIONS": case "BOWLERSUMMARY_GRAPHICS-OPTIONS": case "LANDMARK_GRAPHICS-OPTIONS": case "EQUATION_GRAPHICS-OPTIONS": case "BALL_GRIFF_GRAPHICS-OPTIONS":
		case "POSITION_LANDMARK_GRAPHICS-OPTIONS": case "BATSMAN_THIS_MATCH_GRAPHICS-OPTIONS": case "BOWLER_THIS_MATCH_GRAPHICS-OPTIONS": case "PLAYERS_GRAPHICS-OPTIONS": case "FFTHIS_SERIES_GRAPHICS-OPTIONS":
		case "BATSMAN_STYLE_GRAPHICS-OPTIONS": case "GENERIC_GRAPHICS-OPTIONS": case "IDENT_GRAPHICS-OPTIONS": case "RIGHT_GRAPHICS-OPTIONS": case "SQUAD_GRAPHICS-OPTIONS": case "LTTHIS_SERIES_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(match).toString();
		case "NAMESUPER_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "LTMATCH-PROMO_GRAPHICS-OPTIONS":
		case "PLAYOFF_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
			
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
			
		case "BUG_DB_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getBugs()).toString();
			
		case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-L3-BUG":  case "POPULATE-L3-HOWOUT": case "POPULATE-L3MATCH_PROMO":
		case "POPULATE-L3-BATSMANSTATS": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-FF-DOUBLETEAMS": case "POPULATE-L3-INFOBAR": case "POPULATE-FF_THIS-SERIES":
		case "POPULATE-FF-LEADERBOARD": case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-FF-MATCHID": case "POPULATE-FF-PLAYINGXI": case "POPULATE-L3-THISSERIES":
		case "POPULATE-LT-PROJECTED": case "POPULATE-L3-TARGET": case "POPULATE-L3-TEAMSUMMARY": case "POPULATE-L3-PLAYERSUMMARY": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-L3-FALLOFWICKET": case "POPULATE-POWERPLAY":
		case "POPULATE-L3-COMPARISION": case "POPULATE-INFOBAR-PROMPT": case "POPULATE-LT-MATCHID": case "POPULATE-L3-BOWLERSTATS": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-SPLIT": //case "CLEAR-ALL":
		case "POPULATE-L3-BUG-DB": case "POPULATE-INFOBAR-TOP": case "POPULATE-L3-BUG-BOWLER": case "POPULATE-LT-PARTNERSHIP": case "POPULATE-L3-BUGTARGET": case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER": case "POPULATE-HOWOUT_QUICK":
		case "POPULATE-L3-BOWLERSUMMARY": case "POPULATE-L3-NEXT_TO_BAT": case "POPULATE-L3-BOWLERDETAILS": case "POPULATE-LT-POWERPLAY": case "POPULATE-FF-LANDMARK": case "POPULATE-PREVIOUS_SUMMARY": case "POPULATE-FF-SQUAD":
		case "POPULATE-LT-EQUATION": case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-L3-BATSMAN_THIS_MATCH": case "POPULATE-L3-BOWLER_THIS_MATCH": case "POPULATE-POINTS_TABLE": case "POPULATE-INFOBAR-IDENT": 
		case "POPULATE-LTPOINTS_TABLE":	case "POPULATE-BOWLER_STYLE": case "POPULATE-BATSMAN_STYLE": case "POPULATE-MANHATTAN": case "POPULATE-MATCH_PROMO": case "POPULATE-FF-TEAMS_LOGO": case "POPULATE-INFOBAR-RIGHT":
		case "POPULATE-TIEID-DOUBLE": case "POPULATE-L3-GENERIC": case "POPULATE-MOSTRUNS": case "POPULATE-MOSTWICKETS": case "POPULATE-MOSTFOURS": case "POPULATE-MOSTSIXES": case "POPULATE-HIGHESTSCORE": case "POPULATE-DIRECTOR":
		case "POPULATE-MINI-BATTINGCARD": case "POPULATE-MINI-BOWLINGCARD": case "POPULATE-WORM": case "POPULATE-BUG_POWERPLAY": case "LOAD_MANUAL_XML_SCENE": case "POPULATE-FF-BATGRIFF": case "POPULATE-FF-BALLGRIFF":
		case "POPULATE-PLAYOFF":	
			
			if(which_graphics_onscreen == "SCOREBUG" || which_graphics_onscreen == "IDENT") {
				
			}else if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
					 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") || 
					
					 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") || 
					 
					 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
					 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 
					 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD")) {
				//AnimateOutGraphics(print_writer, which_graphics_onscreen.toUpperCase());
			}/*else if(which_graphics_onscreen == "FFPLAYERPROFILE"  && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PLAYERPROFILE") ) {
				
			}*/else if(which_graphics_onscreen != "") {
				AnimateOutGraphics(print_writer, which_graphics_onscreen.toUpperCase());
			}
			switch(whatToProcess.toUpperCase()) {
			case "LOAD_MANUAL_XML_SCENE":
				scenes.set(2, new Scene("/Default/ICPL/" + valueToProcess.replace(".xml", ""),"MIDDLE_LAYER"));
				//scenes.get(1).scene_load(print_writer,broadcaster);
				break;
			case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-POWERPLAY":
			case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-RIGHT": case "POPULATE-DIRECTOR": case "POPULATE-INFOBAR-PROMPT":
			case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-IDENT":
				break;
				/*
				 * case "POPULATE-L3-INFOBAR": case "POPULATE-IDENT":
				 * if(infobar.isInfobar_on_screen() == true) { break; }
				 
				break;*/
			default:
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
				 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") || 
				
				 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") || 
				 
				 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				 
				 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphics_onscreen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD")) {
				}else {
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
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
					populateBatGriff(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[3]),
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),cricketService.getTeams(),
							match,broadcaster);
					break;
				case "POPULATE-FF-BALLGRIFF":
					populateBallGriff(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[3]),
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),cricketService.getTeams(),
							match,broadcaster);
					break;
				case "POPULATE-HIGHESTSCORE":
					populateHighestScore(print_writer, valueToProcess.split(",")[0],
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
							match,broadcaster);
					break;
				case "POPULATE-MINI-BATTINGCARD":
					populateMiniBattingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-MINI-BOWLINGCARD":
					populateMiniBowlingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				/*case "POPULATE-FF-SCORECARD":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					populateScorecard(print_writer, viz_scene_path, whichInning, match, broadcaster);
					
					PreviewFullFrame(print_writer, viz_scene_path, which_graphics_onscreen, "SCORECARD");
					if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" || which_graphics_onscreen == "POINTSTABLE") {	
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn SHOW 0.0 \0");
					}
					break;
					
				case "POPULATE-FF-BOWLINGCARD":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					populateBowlingcard(print_writer, viz_scene_path, false, whichInning, match, broadcaster);
					
					PreviewFullFrame(print_writer, viz_scene_path, which_graphics_onscreen, "BOWLINGCARD");
					if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" || which_graphics_onscreen == "POINTSTABLE") {	
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn SHOW 0.0 \0");
					}
					break;*/
				case "POPULATE-FF-SCORECARD":
					populateScorecard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" || which_graphics_onscreen == "POINTSTABLE") {	
						
						if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 BattingCardIn 1.676 BowlingCardOut 0.700 \0");
						}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 BattingCardIn 1.676 SummaryOut 0.700 \0");
						}else if(which_graphics_onscreen == "POINTSTABLE") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 BattingCardIn 1.676 PointsTableOut 0.700 \0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					}
					break;
					
				case "POPULATE-FF-BOWLINGCARD":
					//AnimateInGraphics(print_writer, "RESET");
					populateBowlingcard(print_writer, valueToProcess.split(",")[0], false, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" || which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" || which_graphics_onscreen == "POINTSTABLE") {	
						print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
						
						if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 BowlingCardIn 1.740 BattingCardOut 0.700 \0");
						}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 BowlingCardIn 1.740 SummaryOut 0.700 \0");
						}else if(which_graphics_onscreen == "POINTSTABLE") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 BowlingCardIn 1.740 PointsTableOut 0.700 \0");
						}
					}
					break;
				case "POPULATE-BUG_POWERPLAY":
					populateBugPowerPLay(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-FF-PARTNERSHIP":
					populatePartnership(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				/*case "POPULATE-FF-MATCHSUMMARY":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					populateMatchsummary(print_writer, viz_scene_path, whichInning, match, broadcaster);
					
					PreviewFullFrame(print_writer, viz_scene_path, which_graphics_onscreen, "MATCHSUMMARY");
					if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY" || which_graphics_onscreen == "POINTSTABLE") {	
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn SHOW 0.0 \0");
					}
					break;*/
				case "POPULATE-FF-MATCHSUMMARY":
					//AnimateInGraphics(print_writer, "RESET");
					populateMatchsummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" || which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphics_onscreen == "POINTSTABLE") {	
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
						
						if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 SummaryIn 1.316 BattingCardOut 0.700 \0");
						}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 SummaryIn 1.316 BowlingCardOut 0.700 \0");
						}else if(which_graphics_onscreen == "POINTSTABLE") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 SummaryIn 1.316 PointsTableOut 0.700 \0");
						}
					}
					
					break;
				case "POPULATE-FF-LEADERBOARD":
					populateLeaderBoard(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
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
				case "POPULATE-L3-HOWOUT":
					//populateHowout(print_writer, viz_scene_path, match, broadcaster);

					populateHowout(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
					break;
				case "POPULATE-HOWOUT_QUICK":
					//whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					//stats_type = valueToProcess.split(",")[2];
					//player_id = Integer.valueOf(valueToProcess.split(",")[3]);
					populateQuickHowout(print_writer, valueToProcess.split(",")[0], match, broadcaster);

					//populateHowout(print_writer, viz_scene_path,whichInning, stats_type, player_id, match, broadcaster);
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
				case "POPULATE-L3-NAMESUPER-PLAYER":
					populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
					break;
				case "POPULATE-FF-MATCHID":
					populateMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-LT-MATCHID":
					populateLTMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-L3MATCH_PROMO":
					populateLtMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(),match , broadcaster);
					break;
				case "POPULATE-MATCH_PROMO":
					populateMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(),match , broadcaster);
					break;
				case "POPULATE-PLAYOFF":
					populatePlayOff(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(),match , broadcaster);
					break;
				case "POPULATE-L3-COMPARISION":
					populateComparision(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-LT-PARTNERSHIP":
					populateLTPartnership(print_writer, valueToProcess.split(",")[0], match, broadcaster);
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
					//System.out.println(valueToProcess.split(",")[1]);
					populateTieIdDouble(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1],cricketService.getFixtures(), match, broadcaster);
					break;
				case "POPULATE-L3-NEXT_TO_BAT":
					populateLtNextToBat(print_writer, valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-LT-PROJECTED":
					populateProjectedScore(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-L3-PLAYERPROFILE":
					for(Statistics stats : statistics) {
						if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
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
				case "POPULATE-FF-PLAYERPROFILE":
						for(Statistics stats : statistics) {
							if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
								stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
								stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
								stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
								if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[3])) {
									populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
											valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
								}
							}
						}
					break;
				case "POPULATE-FF_THIS-SERIES":
					populateThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
							,match, broadcaster);
					break;
				case "POPULATE-L3-THISSERIES":
					populateLTThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
							,match, broadcaster);
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
				case "POPULATE-FF-LANDMARK":
					populateLandMark(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
							Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster, config);
					break;
				case "POPULATE-LT-EQUATION":
					populateLtEquation(print_writer,valueToProcess.split(",")[0], match, broadcaster);
					break;
				case "POPULATE-FF-POSITION_LANDMARK":
					populateFFLandMark(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
					break;
				case "POPULATE-POINTS_TABLE":
					LeagueTable league_table = null;
					if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
						league_table = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
								new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
					}
					
					populatePointsTable(print_writer, valueToProcess.split(",")[0],league_table.getLeagueTeams(),broadcaster,match);
					if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD" || which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {	
						print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
						
						if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.400 BattingCardOut 0.700 \0");
						}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.400 BowlingCardOut 0.700 \0");
						}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
							print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.400 SummaryOut 0.700 \0");
						}
					}
					break;
				case "POPULATE-LTPOINTS_TABLE":
					LeagueTable ltleague_table = null;
					if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
						ltleague_table = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
								new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
					}
					populateLtPointsTable(print_writer, valueToProcess.split(",")[0], ltleague_table.getLeagueTeams(),match,broadcaster);
					break;
				case "POPULATE-BOWLER_STYLE":
					populateBowlerStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(), cricketService.getTeams(), match, broadcaster);
					break;
				case "POPULATE-BATSMAN_STYLE":
					populateBatsmanStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), cricketService.getAllPlayer(), cricketService.getTeams(),match, broadcaster);
					break;
				case "POPULATE-FF-TEAMS_LOGO":
					populateTeamsLogo(print_writer, valueToProcess.split(",")[0],cricketService.getTeams(),match, broadcaster);
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
//						cricket_match = (CricketFunctions.populateMatchVariables(cricketService,(MatchAllData) JAXBContext.newInstance(MatchAllData.class).createUnmarshaller().unmarshal(
//										new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + file.getName()))));
							for(Fixture fx : cricketService.getFixtures()) {
								//System.out.println(valueToProcess.split(",")[1]);
								if(fx.getMatchnumber() == Integer.valueOf(valueToProcess.split(",")[1])) {
									if(Integer.valueOf(cricket_match.getSetup().getMatchIdent().split(" ")[1]) == (fx.getMatchnumber()) 
											&& cricket_match.getSetup().getHomeTeam().getTeamId() == fx.getHometeamid() 
											&& cricket_match.getSetup().getAwayTeam().getTeamId() == fx.getAwayteamid())
									{
										cricket_matches.add(cricket_match);
									}
								}
							}
					}
					populatePreviousSummary(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricket_matches,cricketService.getFixtures(), 
							match, broadcaster);
					break;
				case "POPULATE-MANHATTAN":
					populateManhattan(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-WORM":
					populateWorm(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
					break;
				case "POPULATE-INFOBAR-IDENT":
					populateInfobarIdent(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1] , match, broadcaster);
					TimeUnit.SECONDS.sleep(2);
					//isIdent_on_screen() = true;
					break;
				case "POPULATE-L3-INFOBAR":
//					AnimateInGraphics(print_writer, "RESET");
					//AnimateInGraphics(print_writer, "RESET");
					infobar.setPowerplay_on_screen(false);
					infobar.setMiddle_section(valueToProcess.split(",")[1]);
					infobar.setBottom_right_section(valueToProcess.split(",")[2]);
					
					infobar = populateInfobar(infobar, print_writer, match, broadcaster);
					TimeUnit.SECONDS.sleep(2);
					which_graphics_onscreen = "SCOREBUG";

					break;
				case "POPULATE-DIRECTOR":
					director = valueToProcess;
					populateInfobarDirector(print_writer,valueToProcess,broadcaster);
					break;
				case "POPULATE-POWERPLAY":
					populateInfobarPowerPlay(print_writer,valueToProcess,broadcaster);
					break;
				case "POPULATE-INFOBAR-PROMPT":
					for(InfobarStats ibs : cricketService.getInfobarStats() ) {
					  if(ibs.getOrder() == Integer.valueOf(valueToProcess)) {
						  infobar.setMiddle_section("FREE_TEXT");
						  infobar = populateInfobarMiddleSection(infobar, false, print_writer, 
								  match, broadcaster, ibs);
					  }
					}
					break;	
				case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOM":
					
					infobar.setMiddle_section(valueToProcess);
				    infobar = populateInfobarMiddleSection(infobar, false, print_writer, 
							  match, broadcaster, null);
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
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
		case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-INFOBAR":  
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-MINI-BOWLINGCARD":
		case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-SQUAD":
		case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-OUT": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-MINI-BATTINGCARD":
		case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUGTARGET": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY":
		case "ANIMATE-IN-LANDMARK":	case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH": case "ANIMATE-OUT-BOTTOM":
		case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-OUT-SECTION4_N_5":
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-GENERIC":
		case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-L3MATCH_PROMO": case "CLEAR-ALL":
		case "ANIMATE-IN-IDENT": case "ANIMATE-OUT-DIRECTOR": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-OUT-POWERPLAY": case "ANIMATE-IN-FF_THIS-SERIES": case "ANIMATE-IN-THISSERIES":
		case "ANIMATE-IN-WORM":	case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-MANUAL_GRAPHIC": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-PLAYOFF":
		
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-INFOBAR":  case "ANIMATE-IN-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
//					AnimateInGraphics(print_writer, "FF_IN");
					TimeUnit.MILLISECONDS.sleep(200);
					//TimeUnit.SECONDS.sleep(2);
				}
				break;
				
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": case "ANIMATE-MINI-BOWLINGCARD":
			case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-FF_THIS-SERIES":
			case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-MINI-BATTINGCARD":
			case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-SQUAD":
			case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-HOWOUT_QUICK":
			case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BUGTARGET": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY":
			case "ANIMATE-IN-LANDMARK":	case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH":
			case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-IN-THISSERIES":
			case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-GENERIC":
			case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-L3MATCH_PROMO": case "CLEAR-ALL":
			case "ANIMATE-IN-WORM":	case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-MANUAL_GRAPHIC": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-IN-PLAYOFF":
				if(infobar.isInfobar_on_screen() == true && which_graphics_onscreen == "SCOREBUG") {
					AnimateInGraphics(print_writer, "FF_IN");
					TimeUnit.MILLISECONDS.sleep(200);
					//TimeUnit.SECONDS.sleep(2);
				}
				break;
			
			}
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-INFOBAR");
					TimeUnit.MILLISECONDS.sleep(800);
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
			case "ANIMATE-IN-PLAYOFF":
				AnimateInGraphics(print_writer, "PLAYOFF");
				which_graphics_onscreen = "PLAYOFF";
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
			case "ANIMATE-MINI-BOWLINGCARD":
				AnimateInGraphics(print_writer, "MINI-BOWLINGCARD");
				which_graphics_onscreen = "MINI-BOWLINGCARD";
				break;
			case "ANIMATE-IN-SCORECARD":
				if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					
				}else {
					AnimateInGraphics(print_writer, "SCORECARD");
				}
				which_graphics_onscreen = "BATBALLSUMMARY_SCORECARD";
				break;
			case "ANIMATE-IN-BOWLINGCARD":
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					
				}else {
					AnimateInGraphics(print_writer, "BOWLINGCARD");
				}
				which_graphics_onscreen = "BATBALLSUMMARY_BOWLINGCARD";
				break;
			/*case "ANIMATE-IN-SCORECARD":
				if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					setStatus(CricketUtil.SUCCESSFUL);
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					setStatus(CricketUtil.SUCCESSFUL);
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					setStatus(CricketUtil.SUCCESSFUL);
				}else {
					AnimateInGraphics(print_writer, "SCORECARD");
				}
				if(getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
					which_graphics_onscreen = "BATBALLSUMMARY_SCORECARD";
				}
				break;
			case "ANIMATE-IN-BOWLINGCARD":
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					setStatus(CricketUtil.SUCCESSFUL);
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					setStatus(CricketUtil.SUCCESSFUL);
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					setStatus(CricketUtil.SUCCESSFUL);
				}else {
					AnimateInGraphics(print_writer, "BOWLINGCARD");
				}
				
				if(getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
					which_graphics_onscreen = "BATBALLSUMMARY_BOWLINGCARD";
				}
				break;*/
			case "ANIMATE-IN-PARTNERSHIP":
				AnimateInGraphics(print_writer, "PARTNERSHIP");
				which_graphics_onscreen = "PARTNERSHIP";
				break;
			case "ANIMATE-IN-MATCHSUMARRY":
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					
				}else {
					AnimateInGraphics(print_writer, "MATCHSUMMARY");
				}
				which_graphics_onscreen = "BATBALLSUMMARY_MATCHSUMMARY";
				break;
			/*case "ANIMATE-IN-MATCHSUMARRY":
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					setStatus(CricketUtil.SUCCESSFUL);
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					setStatus(CricketUtil.SUCCESSFUL);
				}else if(which_graphics_onscreen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					setStatus(CricketUtil.SUCCESSFUL);
				}else {
					AnimateInGraphics(print_writer, "MATCHSUMMARY");
				}
				if(getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
					which_graphics_onscreen = "BATBALLSUMMARY_MATCHSUMMARY";
				}
				break;*/
			case "ANIMATE-IN-PREVIOUS_SUMMARY":
				AnimateInGraphics(print_writer, "PREVIOUS_SUMMARY");
				which_graphics_onscreen = "PREVIOUS_SUMMARY";
				break;
			case "ANIMATE-IN-BUG-DISMISSAL":
				AnimateInGraphics(print_writer, "BUG-DISMISSAL");
				which_graphics_onscreen = "BUG-DISMISSAL";
				break;
			case "ANIMATE-IN-BUG":
				AnimateInGraphics(print_writer, "BUG");
				which_graphics_onscreen = "BUG";
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
			case "ANIMATE-IN-FF_THIS-SERIES":
				AnimateInGraphics(print_writer, "FF_THIS-SERIES");
				which_graphics_onscreen = "FF_THIS-SERIES";
				break;
			case "ANIMATE-IN-THISSERIES":
				AnimateInGraphics(print_writer, "LT_THIS-SERIES");
				which_graphics_onscreen = "LT_THIS-SERIES";
				break;
			case "ANIMATE-IN-PLAYERPROFILE":
				/*if(which_graphics_onscreen == "FFPLAYERPROFILE") {
					AnimateOutGraphics(print_writer, "FFPLAYERPROFILE");
					TimeUnit.MILLISECONDS.sleep(2000);
					for(Statistics stats : session_statistics) {
						if(stats.getPlayer_id() == player_id) {
							stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
							stats = CricketFunctions.updateTournamentDataWithStats(stats, type_of_profile, tournament_matches, match);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, type_of_profile);
							if(stats.getStats_type().getStats_short_name().equalsIgnoreCase(stats_type)) {
								populatePlayerProfile(print_writer,viz_scene_path,player_id,
										stats_type,type_of_profile,stats,match, broadcaster);
							}
						}
					}
					AnimateInGraphics(print_writer, "FFPLAYERPROFILE");
					if(getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
						which_graphics_onscreen = "FFPLAYERPROFILE";
					}
				}else {*/
					AnimateInGraphics(print_writer, "FFPLAYERPROFILE");
					which_graphics_onscreen = "FFPLAYERPROFILE";
				//}
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
			case "ANIMATE-IN-POINTSTABLE":
				if(which_graphics_onscreen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
					
				}else if(which_graphics_onscreen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
					
				}else {
					AnimateInGraphics(print_writer, "POINTSTABLE");
				}
				which_graphics_onscreen = "POINTSTABLE";
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
					TimeUnit.MILLISECONDS.sleep(800);
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
               
               print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/ICPL/ScoreBug\0");
           	
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
               
				break;
			case "ANIMATE-OUT-SECTION4_N_5":
				//resetAnimation(print_writer, broadcaster.toUpperCase(), which_director_on_infobar.toUpperCase());
				AnimateOutGraphics(print_writer, "RIGHT");
				break;
			case "ANIMATE-OUT-BOTTOM":
				//resetAnimation(print_writer, broadcaster.toUpperCase(), info_bar_bottom.toUpperCase());
				break;
			case "ANIMATE-OUT":
				switch(which_graphics_onscreen) {
				case "PLAYOFF":
					AnimateOutGraphics(print_writer, "PLAYOFF");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BALLGRIFF":
					AnimateOutGraphics(print_writer, "BALLGRIFF");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BATGRIFF":
					AnimateOutGraphics(print_writer, "BATGRIFF");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BUG_POWERPLAY":
					AnimateOutGraphics(print_writer, "BUG_POWERPLAY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "MINI-SCORECARD":
					AnimateOutGraphics(print_writer, "MINI-SCORECARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "MINI-BOWLINGCARD":
					AnimateOutGraphics(print_writer, "MINI-BOWLINGCARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BATBALLSUMMARY_SCORECARD":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_SCORECARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BATBALLSUMMARY_BOWLINGCARD":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_BOWLINGCARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BATBALLSUMMARY_MATCHSUMMARY":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_MATCHSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "PREVIOUS_SUMMARY":
					AnimateOutGraphics(print_writer, "PREVIOUS_SUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BUG-DISMISSAL":
					AnimateOutGraphics(print_writer, "BUG-DISMISSAL");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BUG":
					AnimateOutGraphics(print_writer, "BUG");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "MANUAL":
					AnimateOutGraphics(print_writer, "MANUAL");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "SQUAD":
					AnimateOutGraphics(print_writer, "SQUAD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BUGBOWLER":
					AnimateOutGraphics(print_writer, "BUGBOWLER");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BUG-DB":
					AnimateOutGraphics(print_writer, "BUG-DB");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "TIEID-DOUBLE":
					AnimateOutGraphics(print_writer, "TIEID-DOUBLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "HOWOUT":
					AnimateOutGraphics(print_writer, "HOWOUT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "QUICK_HOWOUT":
					AnimateOutGraphics(print_writer, "QUICK_HOWOUT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "HOWOUT_WITHOUT":
					AnimateOutGraphics(print_writer, "HOWOUT_WITHOUT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "NAMESUPER":
					AnimateOutGraphics(print_writer, "NAMESUPER");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "NAMESUPER-PLAYER":
					AnimateOutGraphics(print_writer, "NAMESUPER-PLAYER");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "SCOREBUG":
					AnimateOutGraphics(print_writer, "SCOREBUG");
					AnimateInGraphics(print_writer, "RESET");
					which_graphics_onscreen = "";
					infobar = new Infobar();
					infobar.setInfobar_on_screen(false);
					
					break;
				case"IDENT":
					AnimateOutGraphics(print_writer, "IDENT");
					which_graphics_onscreen = "";
					which_graphics_onscreen = "";
					infobar.setInfobar_on_screen(false);
					break;
				case "FALLOFWICKET":
					AnimateOutGraphics(print_writer, "FALLOFWICKET");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "LEADERBOARD":
					AnimateOutGraphics(print_writer, "LEADERBOARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "L3MATCHID":
					AnimateOutGraphics(print_writer, "L3MATCHID");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "MATCH_PROMO":
					AnimateOutGraphics(print_writer, "MATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "LTMATCH_PROMO":
					AnimateOutGraphics(print_writer, "LTMATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "TARGET":
					AnimateOutGraphics(print_writer, "TARGET");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BUGTARGET":
					AnimateOutGraphics(print_writer, "BUGTARGET");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "COMPARISION":
					AnimateOutGraphics(print_writer, "COMPARISION");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "LTPARTNERSHIP":
					AnimateOutGraphics(print_writer, "LTPARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "PARTNERSHIP":
					AnimateOutGraphics(print_writer, "PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "SPLIT":
					AnimateOutGraphics(print_writer, "SPLIT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BATSMANSTATS":
					AnimateOutGraphics(print_writer, "BATSMANSTATS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BOWLERSTATS":
					AnimateOutGraphics(print_writer, "BOWLERSTATS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BOWLERSUMMARY":
					AnimateOutGraphics(print_writer, "BOWLERSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "PLAYERSUMMARY":
					AnimateOutGraphics(print_writer, "PLAYERSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "TEAMSUMMARY":
					AnimateOutGraphics(print_writer, "TEAMSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "NEXTTOBAT":
					AnimateOutGraphics(print_writer, "NEXTTOBAT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "PROJECTED":
					AnimateOutGraphics(print_writer, "PROJECTED");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BOWLERDETAILS":
					AnimateOutGraphics(print_writer, "BOWLERDETAILS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "LTPOWERPLAY":
					AnimateOutGraphics(print_writer, "LTPOWERPLAY");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "MATCHID":
					AnimateOutGraphics(print_writer, "MATCHID");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "L3PLAYERPROFILE":
					AnimateOutGraphics(print_writer, "L3PLAYERPROFILE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "FFPLAYERPROFILE":
					AnimateOutGraphics(print_writer, "FFPLAYERPROFILE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "LT_THIS-SERIES":
					AnimateOutGraphics(print_writer, "LT_THIS-SERIES");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "FF_THIS-SERIES":
					AnimateOutGraphics(print_writer, "FF_THIS-SERIES");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "TEAMLINEUP":
					AnimateOutGraphics(print_writer, "TEAMLINEUP");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "DOUBLETEAMS":
					AnimateOutGraphics(print_writer, "DOUBLETEAMS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "LANDMARK":
					AnimateOutGraphics(print_writer, "LANDMARK");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "EQUATION":
					AnimateOutGraphics(print_writer, "EQUATION");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "POSITION_LANDMARK":
					AnimateOutGraphics(print_writer, "POSITION_LANDMARK");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BATSMAN_THIS_MATCH":
					AnimateOutGraphics(print_writer, "BATSMAN_THIS_MATCH");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BOWLER_THIS_MATCH":
					AnimateOutGraphics(print_writer, "BOWLER_THIS_MATCH");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "POINTSTABLE":
					AnimateOutGraphics(print_writer, "POINTSTABLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "LTPOINTSTABLE":
					AnimateOutGraphics(print_writer, "LTPOINTSTABLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BOWLER_STYLE":
					AnimateOutGraphics(print_writer, "BOWLER_STYLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "BATSMAN_STYLE":
					AnimateOutGraphics(print_writer, "BATSMAN_STYLE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "TEAMS_LOGO":
					AnimateOutGraphics(print_writer, "TEAMS_LOGO");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				
				case "MANHATTAN":
					AnimateOutGraphics(print_writer, "MANHATTAN");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "WORM":
					AnimateOutGraphics(print_writer, "WORM");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "MOSTRUNS":
					AnimateOutGraphics(print_writer, "MOSTRUNS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "MOSTWICKETS":
					AnimateOutGraphics(print_writer, "MOSTWICKETS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "MOSTFOURS":
					AnimateOutGraphics(print_writer, "MOSTFOURS");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "MOSTSIXES":
					AnimateOutGraphics(print_writer, "MOSTSIXES");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
					break;
				case "HIGHESTSCORE":
					AnimateOutGraphics(print_writer, "HIGHESTSCORE");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "";
					resetInfobarAnimation(print_writer,broadcaster);
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
		//case "POPULATE-SELECT-PLAYER": 
			//return new ObjectMapper().writeValueAsString(match).toString();
	}
		return null;
}
	public void populateScorecard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		int row_id = 0, omo_num = 0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$ExtrasGrp$Extras*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatSponsor" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + " " + "\0");

					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase()+ "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$MaxSize$BatHeader*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$MaxSize$BatHeader*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					Collections.sort(inn.getBattingCard());
					for (BattingCard bc : inn.getBattingCard()) {
						
						row_id = row_id + 1;
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
							if(bc.getHowOut() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo*FUNCTION*Omo*vis_con SET 0 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$LeftPlayerName$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$WicketPlayerName*GEOM*TEXT SET " + "retired hurt" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
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
							
							if(bc.getHowOut() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$RowAll*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
							}
							else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$RowAll*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row"+row_id+"$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
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
											if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "  ( " + bc.getHowOutPartTwo().split(" ")[0] + " )" + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
											
											}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "  ( " + bc.getHowOutPartTwo().split(" ")[0] + " )" + "\0");
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
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									}
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getStatus() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + " " + "\0");		
							}
						}
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
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 BattingCardIn 1.676 \0");
			break;
		}
		
	}
	public void populateMiniBattingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
				int row_id = 0, omo_num = 0,batting_size=0;
				String cont_name= "";
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$TopPart$LogoMotion*ACTIVE SET 0 \0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBatting_team().getLastname().toUpperCase() + "\0");
	
						
						Collections.sort(inn.getBattingCard());
						
						for (BattingCard bc : inn.getBattingCard()) {
							
							row_id = row_id + 1;
							switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 1;
									cont_name = "$Dehighlight";
									batting_size = batting_size + 1;
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 2;
									cont_name = "$Highlight";
									batting_size = batting_size + 1;
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
									break;
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
										"$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
										"$RowAni$RowOmo" + cont_name + "$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
								
								//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
										//"$RowAnimation$BatOmo$Dehighlight$ScoreGrp*ACTIVE SET 1 \0");
								
								
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
										"$RowAni$RowOmo" + cont_name + "$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
										"$RowAni$RowOmo" + cont_name + "$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
											"$RowAni$RowOmo" + cont_name + "$Star*ACTIVE SET 0 \0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
											"$RowAni$RowOmo" + cont_name + "$Star*ACTIVE SET 1 \0");
								}
						}
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.481 BatDataIn 1.180 \0");
			break;
		}
	}
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene,boolean is_this_updating, int whichInning,   MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		int row_id = 0; 
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "ICPL":
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$OversGrp$Overs*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallSponsor" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + " " + "\0");
	
						if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$MaxSize$BallHeader*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$MaxSize$BallHeader*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo*FUNCTION*Omo*vis_con SET " + inn.getBowlingCard().size()+"\0");
	
	
						for (BowlingCard boc : inn.getBowlingCard()) {
							row_id = row_id + 1;
							if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard$DataAll$Highlight*FUNCTION*Omo*vis_con SET " + row_id +"\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard$DataAll$Highlight*FUNCTION*Omo*vis_con SET " + "0" +"\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$BowlerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$OversValue*GEOM*TEXT SET " +  CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls())  + "\0");
							if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10) 
									|| match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Row0$Highlight$ScoreGrp$MaidensHead*GEOM*TEXT SET " + "DOTS" +"\0");
								if(boc.getDots() < 0) {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + "0" +"\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + boc.getDots() +"\0");
								}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Row0$Highlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + "MAIDENS" +"\0");
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
	
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
						}
					}
				}
				
				//GraphicsPreview(print_writer, "BOWLINGCARD", which_graphic_on_screen, viz_scene);
				//which_graphic_on_screen = "BOWLINGCARD";
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 BowlingCardIn 1.740 \0");
				
			break;
		}
	}
	public void populateMiniBowlingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				int row_id = 0, omo_num = 0;
				String cont_name= "";
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$TopPart$LogoMotion*ACTIVE SET 0 \0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + inn.getBowling_team().getTeamName1().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBatting_team().getLastname().toUpperCase() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBowlingCard().size() + "\0");
						
						
						for (BowlingCard boc : inn.getBowlingCard()) {
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
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + 
									row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");
		
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
									"$RowAni$RowOmo" + cont_name + "$BatsmanName*GEOM*TEXT SET  " + boc.getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
									"$RowAni$RowOmo" + cont_name + "$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + 
									"$RowAni$RowOmo" + cont_name + "$ScoreGrp$noname$BatsmanBall*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");
	
						}
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.241 \0");
				//this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populatePartnership(PrintWriter print_writer, String viz_scene,int whichInning,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				int row_id = 0, omo_num = 0,Top_Score = 50;
				float Mult = 322, ScaleFac1 = 0, ScaleFac2 = 0;
				String cont_name= "",Left_Batsman = "",Right_Batsman="";
	
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "PARTNERSHIPS" + "\0");
	
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
	
					//if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						
						if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
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
						if(inn.getTotalWickets() >= 9) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + (inn.getBattingCard().size() - 1) + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + inn.getBattingCard().size() + "\0");
						}
	
						for (Partnership ps : inn.getPartnerships()) {
							
							row_id = row_id + 1;
							Left_Batsman ="" ; Right_Batsman="";
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId() == ps.getFirstBatterNo()) {
									Left_Batsman = bc.getPlayer().getTicker_name();
								}
								else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
									Right_Batsman = bc.getPlayer().getTicker_name();
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
							
	
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + "$LeftPlayerName*GEOM*TEXT SET " + Left_Batsman + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + "$RightPlayerName*GEOM*TEXT SET " + Right_Batsman + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + "$Bar*FUNCTION*BarValues*Bar_Value__1 SET " + ScaleFac1 + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + "$Bar*FUNCTION*BarValues*Bar_Value__2 SET " + ScaleFac2 + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + "$ScoreGrp$PartnershipRun*GEOM*TEXT SET " + ps.getTotalRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + cont_name + "$ScoreGrp$PartnershipBalls*GEOM*TEXT SET " + ps.getTotalBalls() + "\0");		
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
										print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + " \0");
										if(match.getMatch().getInning().get(whichInning - 1).getTotalOvers() == match.getSetup().getMaxOvers() 
												|| match.getMatch().getInning().get(whichInning - 1).getTotalWickets() >= 10 ) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
										}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || CricketFunctions.getWicketsLeft(match,whichInning) <= 0) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "STILL TO BAT" +" \0");
										}
									}
									else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
										row_id = row_id + 1;
										print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + " \0");
										print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + bc.getPlayer().getTicker_name().toUpperCase()+" \0");
									}	
								}
								else {
									break;
								}
							}
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BottomInfo$BottomInfoGrp$BottomInfoAll$ExtrasGrp$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$PartnershipAll$Data$BottomInfo$BottomInfoGrp$BottomInfoAll$noname$OversGrp$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET " + inn.getTotalRuns() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 DataIn 1.640 BallOffsetIn 1.830 ManDataIn 0.460 DataIn 1.570 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
			int row_id = 0, max_Strap = 0, total_inn = 0;
				String teamname = "",teamname_logo=""; 
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumSponsor" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumSponsor" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$Shriram_Logo*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + "\\" + "ICPL.png" +" \0");
	
	
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
					
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$SummaryHeader*GEOM*TEXT SET " + "SUMMARY" + "\0");
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						teamname = match.getSetup().getHomeTeam().getTeamName1();
						teamname_logo  = match.getSetup().getHomeTeam().getTeamName3().toUpperCase();
					} else {
						teamname = match.getSetup().getAwayTeam().getTeamName1();
						teamname_logo = match.getSetup().getAwayTeam().getTeamName3().toUpperCase();
					}
					
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumTeamBadge" + i + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + teamname_logo + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
					
					if(match.getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + match.getMatch().getInning().get(i-1).getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + match.getMatch().getInning().get(i-1).getTotalRuns() + slashOrDash 
								+ String.valueOf(match.getMatch().getInning().get(i-1).getTotalWickets()) + "\0");	
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Overs*GEOM*TEXT SET " + 
							CricketFunctions.OverBalls(match.getMatch().getInning().get(i-1).getTotalOvers(),match.getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
					
					if(match.getMatch().getInning().get(i-1).getBattingCard() != null) {
						Collections.sort(match.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
						
						for(BattingCard bc : match.getMatch().getInning().get(i-1).getBattingCard()) {
							if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								row_id = row_id + 1;
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$InnOne$Row"+row_id+"$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + " \0");
	
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
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
								
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$BowlerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
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
					
					for(int j = row_id + 1; j <= max_Strap; j++) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + j + "$RowAni$RowOmo$Dehighlight$Bowler*ACTIVE SET 0 \0");
					}
				}
				if(match.getMatch().getMatchResult() != null) {
					if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
					}else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + "MATCH TIED" + " \0");
					}
					else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + 
								CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + match.getMatchStatus().toUpperCase() + ";");
					}
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + 
							CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + match.getMatchStatus().toUpperCase() + ";");
					
					if(match.getSetup().getTargetType() != null) {
						if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + 
									CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " (VJD)" + " \0");
						}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + 
									CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " (DLS)" + " \0");
						}
					}
				}
				
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 SummaryIn 1.316 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;	
			
			}
			break;
		}
		
	}
	public void populateTeamsLogo(PrintWriter print_writer,String viz_scene, List<Team> teams ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "ICPL":
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + " " + "\0");
	
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "TEAMS" + "\0");
	
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo1" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + teams.get(1).getTeamName3().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName01" + " SET " + teams.get(1).getTeamName1().toUpperCase() + "\0");
	
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo2" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + teams.get(2).getTeamName3().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName02" + " SET " + teams.get(2).getTeamName1().toUpperCase() + "\0");
	
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo3" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + teams.get(3).getTeamName3().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName03" + " SET " + teams.get(3).getTeamName1().toUpperCase() + "\0");
	
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo4" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + teams.get(4).getTeamName3().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName04" + " SET " + teams.get(4).getTeamName1().toUpperCase() + "\0");
	
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo5" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + teams.get(5).getTeamName3().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName05" + " SET " + teams.get(5).getTeamName1().toUpperCase() + "\0");
	
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo6" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + teams.get(6).getTeamName3().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName06" + " SET " + teams.get(6).getTeamName1().toUpperCase() + "\0");
			
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateBugDismissal(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getTicker_name().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
									
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bc.getHowOutText()  + "" + "\0");
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
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBug(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "S/R " + bc.getStrikeRate() + "\0");
									
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bc.getPlayer().getFirstname() + "\0");
										if(bc.getPlayer().getSurname() != null) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getSurname() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bc.getPlayer().getFirstname() + "\0");
										if(bc.getPlayer().getSurname() != null) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getSurname() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
										}
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
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}	
	public void populateBugBowler(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + " " + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + boc.getPlayer().getTicker_name().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + " " + "\0");
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBugPowerPLay(PrintWriter print_writer, String viz_scene,int whichInning,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "POWERPLAY" + "\0");
					
					if(whichInning == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + match.getMatch().getInning().get(0).getBatting_team().getTeamName4().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + 
					CricketFunctions.getPowerPlayScore(inn, whichInning, "-", match) + "\0");
	
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714\0");
				}
				//this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateHowout(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");							
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
														+ inn.getBatting_team().getTeamName3().toUpperCase() + "\0");								
	
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
								
								
								if (bc.getHowOutText().trim().equalsIgnoreCase("")){
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + " " + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatHead01*GEOM*TEXT SET " + "4s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatValue01*GEOM*TEXT SET " + bc.getFours() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatHead02*GEOM*TEXT SET " + "6s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatValue02*GEOM*TEXT SET " + bc.getSixes() + "\0");
								if(bc.getStrikeRate() == null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + "-" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");
								}
							}
						}
					}
				}
							
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateQuickHowout(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");							
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
												+ inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
						for(BattingCard bc : inn.getBattingCard()) {
							if(inn.getFallsOfWickets().size() > 0) {
								if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
																		
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Balls*GEOM*TEXT SET "+ bc.getBalls() + "\0");
									
									if (bc.getHowOutText().trim().equalsIgnoreCase("")){
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + " " + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									}
									
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatHead01*GEOM*TEXT SET " + "4s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatValue01*GEOM*TEXT SET " + bc.getFours() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatHead02*GEOM*TEXT SET " + "6s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatValue02*GEOM*TEXT SET " + bc.getSixes() + "\0");
									if(bc.getStrikeRate() == null) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + "-" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");
									}
								}
							}
						}
					}
				}
							
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateHowoutWithoutFielder(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
										+ inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Balls*GEOM*TEXT SET " + ( bc.getBalls() + 1 ) + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatHead01*GEOM*TEXT SET " + "4s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatValue01*GEOM*TEXT SET " + bc.getFours() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatHead02*GEOM*TEXT SET " + "6s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatValue02*GEOM*TEXT SET " + bc.getSixes() + "\0");
								if(bc.getStrikeRate() == null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + "-" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + " " + "\0");
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}	
	public void populateBatsmanstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + bc.getBalls() + "\0");
	
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
											+ inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourHead" + " SET " + "4s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourValue" + " SET " + bc.getFours() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixHead" + " SET " + "6s" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixValue" + " SET " + bc.getSixes() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSRHead" + " SET " + "S/R" + "\0");
									if(bc.getStrikeRate() == null) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSRValue" + " SET "+ "-" + "\0");	
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSRValue" + " SET "+ bc.getStrikeRate() + "\0");
									}
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}	
	public void populateBowlerstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId,List<Team> team, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BOWLER:
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==playerId) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + boc.getPlayer().getFull_name().toUpperCase() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + boc.getPlayer().getFull_name().toUpperCase() + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + " " + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + " " + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
											+ inn.getBowling_team().getTeamName3().toUpperCase() + "\0");
									
									if(match.getSetup().getMatchType().toUpperCase().equalsIgnoreCase(CricketUtil.DT20) 
											|| match.getSetup().getMatchType().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "DOTS" + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + boc.getDots() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "MAIDEN" + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + boc.getMaidens() + "\0");
									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
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
	
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateBugsDB(PrintWriter print_writer,String viz_scene, Bugs bug ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				
				if(bug.getText1() != null && bug.getText2() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bug.getText1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
				}else if(bug.getText1() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}	
	public void populateNameSuper(PrintWriter print_writer,String viz_scene, NameSuper ns ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
						+ "ICPL" + "\0");
				
				if(ns.getFirstname() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + ns.getSurname().toUpperCase() + "\0");
				}
				else if(ns.getSurname() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + ns.getFirstname().toUpperCase() + "\0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + ns.getFirstname().toUpperCase()
							+ " " + ns.getSurname().toUpperCase() + "\0");
							
				}
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + ns.getSubLine().toUpperCase() + "\0");
				
				//print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp*ACTIVE SET 0" + "\0");
					
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}	
	public void populateNameSuperPlayer(PrintWriter print_writer,String viz_scene, int TeamId, String captainWicketKeeper, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				String Home_or_Away="";
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");
				if(TeamId == match.getSetup().getHomeTeamId()) {
					for(Player hs : match.getSetup().getHomeSquad()) {
						if(playerId == hs.getPlayerId()) {
							Home_or_Away = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
									+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");							
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + hs.getFull_name().toUpperCase() + "\0");
						}
					}
				}
				else {
					for(Player as : match.getSetup().getAwaySquad()) {
						if(playerId == as.getPlayerId()) {
							Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
									+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + as.getFull_name().toUpperCase() + "\0");
						}
					}
				}
				
				switch(captainWicketKeeper.toUpperCase())
				{
				case CricketUtil.CAPTAIN:
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + ", " + Home_or_Away + "\0");
					break;
				case CricketUtil.WICKET_KEEPER:
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "WICKET KEEPER" + ", " + Home_or_Away + "\0");
					break;
				case "PLAYER OF THE MATCH":
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "PLAYER OF THE MATCH" + "\0");
					break;
				case CricketUtil.PLAYER:
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + Home_or_Away + "\0");
					break;
				case "CAPTAIN-WICKETKEEPER":
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "CAPTAIN & WICKET KEEPER" + ", " + Home_or_Away + "\0");
					break;
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}	
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String TypeofProfile,String Profile,Statistics stats, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		
		double strike_rate = 0 , economy_rate=0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSponsor" + " SET " + "IMAGE*/Default/ICPL2023/Logos/"  + "" + "\0");
	
			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
						+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + plyr.getFirstname() + "\0");
				if(plyr.getSurname() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + "" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + plyr.getPhoto() + ".png" + "\0");
	
			}
			else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
						+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + plyr.getFirstname() + "\0");
				if(plyr.getSurname() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + "" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + plyr.getPhoto() + ".png" + "\0");
	
			}
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row1$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row2$RowAnimation$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
	
				if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + "-" + "\0");
				}else {
					strike_rate = stats.getRuns() * 100;
					strike_rate = strike_rate/stats.getBalls_faced();
					DecimalFormat df = new DecimalFormat("0.0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
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
					economy_rate = stats.getRunsConceded() / stats.getBallsBowled();
					economy_rate = economy_rate *6 ;
					DecimalFormat df_b = new DecimalFormat("0.00");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
				}
				break;
			}
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "T20I" + " CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "T20" + " CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
			//this.status = CricketUtil.SUCCESSFUL;
	
			}
			break; 
		}
		
	}
	public void populateLTPlayerProfile(PrintWriter print_writer,String viz_scene,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		double strike_rate = 0 , economy_rate=0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Sponsor$Sponsor*ACTIVE SET 0 \0");	
	
				Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
				if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
							+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + plyr.getFull_name() + "\0");	
	
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
							+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + plyr.getFull_name() + "\0");	
	
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
				case CricketUtil.BOWLER:
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
					
					DecimalFormat df_s = new DecimalFormat("0.00");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "S/R" + "\0");
					if(stats.getWickets() == 0 || stats.getBallsBowled() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df_s.format(stats.getBallsBowled()/stats.getWickets()) + "\0");
					}
					
					economy_rate = stats.getRunsConceded() / stats.getBallsBowled();
					economy_rate = economy_rate * 6;
					DecimalFormat df_b = new DecimalFormat("0.00");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "ECONOMY" + "\0");
					if(stats.getRunsConceded() == 0 && stats.getBallsBowled() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + df_b.format(economy_rate) + "\0");
					}
					break;
				}
				if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "T20I" + " CAREER" + "\0");
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "T20" + " CAREER" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;
	
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
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				String cont = "";
				int row_id = 0, omo = 0;
				for(int i = 1; i <= 2 ; i++) {
					if(i == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$noname$TeamName*GEOM*TEXT SET " + " " + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$noname$TeamName*GEOM*TEXT SET " + 
								match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$Dream11Logo*ACTIVE SET " + "1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSponsor" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
								+ "ICPL" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge1" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
								+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
						/*print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$Rectangle*TEXTURE*IMAGE SET " + 
								"IMAGEDefault/ICPL/Logos/" + "ICPL" + ".png" + "\0");*/
													
						for(Player hs : match.getSetup().getHomeSquad()) {
							row_id = row_id + 1;
							omo = 0;
							cont = "Dehighlight";
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
	
							if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name() + " (C) " + " \0");
							}
							else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name() + " (WK) " + " \0");
							}
							else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name() + " (C & WK) " + " \0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ hs.getFull_name() + " \0");
							}
							
						}
					} else {
						row_id = 0;
						
						/*print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$noname$TeamName*GEOM*TEXT SET " + 
								match.getAwayTeam().getTeamName1().toUpperCase() + "\0");*/
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " + match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge2" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
								+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
						/*print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$TeamNameGrp$RowAni$Highlight$Rectangle*TEXTURE*IMAGE SET " + 
								logo_path + match.getAwayTeam().getTeamName4() + ".png" + "\0");*/
						
						for(Player as : match.getSetup().getAwaySquad()) {
							row_id = row_id + 1;
							omo = 0;
							cont = "Dehighlight";
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo + " \0");
	
							if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name() + " (C) " + " \0");
							}
							else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name() + " (WK) " + " \0");
							}
							else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name() + " (C & WK) " + " \0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll2$PlayerNameGrp$Row" + row_id + "$RowAni$RowOmo$" + cont + "$Batsman$BatsmanName*GEOM*TEXT SET "+ as.getFull_name() + " \0");
							}
						}
					}
				}
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$botombase$BottomInfo$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
							" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$botombase$BottomInfo$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
							" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateSquad(PrintWriter print_writer,String viz_scene, int TeamId,   MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				int row_id = 0,omo = 0;
				String cont = "";
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$HeaderDesign$MaxSize$HeaderText*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$Sponsor*ACTIVE SET 0 \0");
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
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$DataAll$TeamAll1$TeamNameGrp$RowAni$Highlight$noname$TeamName*GEOM*TEXT SET "+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
							
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
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + " \0");
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
	
				//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	public void populateInfobarIdent(PrintWriter print_writer,String viz_scene, String Ident, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			switch(Ident.toUpperCase()) {
			case "TOSS":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName1*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName2*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
				
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
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName1*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName2*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getSetup().getVenueName().toUpperCase() + "\0");
				
				ident_on_screen = true;
				break;
			case "TOURNAMENT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName1*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName2*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				ident_on_screen = true;
				break;
			case "TARGET":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName1*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName2*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
				
				if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + match.getSetup().getMaxOvers()*6 + " BALLS" + "\0");
				}else {
					if(Double.valueOf(match.getSetup().getTargetOvers()) == 1) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + Double.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS" + "\0");
					}
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + Double.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS (VJD)" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + Double.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS (DLS)" + "\0");
					}
				}
				
				ident_on_screen = true;
				break;
			case "RESULT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$InfoGrp$Equation*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName1*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Ident$TopGrp$TeamName2*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
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
									CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " (VJD)" + " \0");
						}
					}
				}
				ident_on_screen = true;
				break;
			}
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
		
	}
	public Infobar populateInfobar(Infobar infobar, PrintWriter print_writer, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		switch (broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				populateInfobarTeamScore(false, print_writer, match, broadcaster);
				infobar = processInfobarPowerplay(infobar, print_writer, broadcaster, match);
				infobar = populateInfobarMiddleSection(infobar, false, print_writer, match, broadcaster, null);
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
	public void populateInfobarTeamScore(boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster)
	{
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBowling_team().getTeamName3().toUpperCase() + "\0");
					}
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll*ACTIVE SET 1 \0");
	
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$TeamName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$TeamScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					}
					else{
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$TeamScore*GEOM*TEXT SET " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
					}
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$TeamOvers*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
					
					if(match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.DLS) || match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.VJD)) {
				    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + 
				    			match.getSetup().getTargetOvers() + " (" + match.getSetup().getTargetType().toUpperCase() + ")" + "\0");
				    }else if (match.getSetup().getTargetType() == "") {
						 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + match.getSetup().getTargetOvers() + "\0");
				    }else {
				    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + " " + "\0");
				    }
					
					/*if(Double.valueOf(match.getTargetOvers()) != 0 && match.getTargetType() != "") {
						 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + match.getTargetOvers() + " (" + match.getTargetType().toUpperCase() + ")" + "\0");
					}else if(Double.valueOf(match.getTargetOvers()) != 0 && match.getTargetType() == "") {
						 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + match.getTargetOvers() + "\0");
					}else {
						 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + "" + "\0");
					}*/
					
				}
			}
			break;
		}
	}
	public Infobar processInfobarPowerplay(Infobar infobar, PrintWriter print_writer, String which_broadcaster, MatchAllData match) {
		
		switch(which_broadcaster.toUpperCase()) {
		case "ICPL":
			//if(Double.valueOf(match.getTargetOvers()) == 1) {
		    	//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$PowerPlay*ACTIVE SET 0 \0");
		    	//infobar.setPowerplay_on_screen(false);
		    //}else {
		    	//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$ScoreGrp$ScoreGrpAll$PowerPlay*ACTIVE SET 1 \0");
		    	if(!CricketFunctions.processPowerPlay(CricketUtil.MINI,match).isEmpty()) {
					 if(infobar.isPowerplay_on_screen() == true) {
						 break;
			         }
			         else {
			        	 infobar.setPowerplay_on_screen(true);
			        	 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn SHOW  \0");
						 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPowerPlay" + " SET " + "P" + "\0");
			         }
				}
				else {
					if(infobar.isPowerplay_on_screen() == true) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayOut START \0");
						infobar.setPowerplay_on_screen(false);
			         }
				}
		   // }
			break;
		}
		
		return infobar;
	}
	public Infobar populateInfobarMiddleSection(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, 
			MatchAllData match, String broadcaster, InfobarStats infobar_stats) throws InterruptedException
	{
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
		case "ICPL":
	
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
					case"FREE_TEXT":
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
					}
					infobar.setLast_middle_section("");
					TimeUnit.MILLISECONDS.sleep(500);
				}
			}
			switch(infobar.getMiddle_section().toUpperCase()) {
			case "EXTRAS":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tExtrasValue" + " SET " + "NB " + inn.getTotalNoBalls() + ", WD " 
											+ inn.getTotalWides() + ", B " + inn.getTotalByes() + ", LB " + inn.getTotalLegByes() + ", PN "+ inn.getTotalPenalties() + "\0");
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
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$TossGrp$TopGrp$TossTeam*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$TossGrp$TossResult*GEOM*TEXT SET " + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$TossGrp$TopGrp$TossTeam*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$TossGrp$TossResult*GEOM*TEXT SET " + "WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
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
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName1" + " SET " + current_batsmen.get(0).getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore1" + " SET " + current_batsmen.get(0).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall1" + " SET " + current_batsmen.get(0).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman1In START \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman1Highlight SHOW 0.6 \0");
						}else {
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
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName2" + " SET " + current_batsmen.get(1).getPlayer().getTicker_name().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore2" + " SET " + current_batsmen.get(1).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall2" + " SET " + current_batsmen.get(1).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2-4$Section2$Batsman2In START \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman2Highlight SHOW 0.6 \0");
						}else {
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
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallSinceValue" + " SET " 
									+ CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber()) + "\0");
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
												CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
												CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");									
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
														CricketFunctions.GetTargetData(match).getRemaningRuns() + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
														CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + " (VJD)" + "\0");											
											}else {
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED " + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedSubHead" + " SET " + " TO WIN FROM " + "\0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
														CricketFunctions.GetTargetData(match).getRemaningRuns() + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
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
					}
				}
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$NeedIn START \0");
				}
				break;
			case "TO_WIN":
				if ((CricketFunctions.GetTargetData(match).getRemaningRuns() > 0) && (CricketFunctions.GetTargetData(match).getRemaningBall() > 0) 
			    		&& (CricketFunctions.getWicketsLeft(match,2) > 0)) {
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
				}else if (CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || CricketFunctions.getWicketsLeft(match,2) <= 0) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$LastXAll*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$ExtrasHead*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$RequiredRunRateAll*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ToWin$RequiredRunRateText*GEOM*TEXT SET " + 
					CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
				}
				
	
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$ToWinIn START \0");
				}
				break;
			case "COMPARISION":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase("NO")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ComparisonGrp$MaxSize$ComparisonHead*GEOM*TEXT SET " 
								+ "AT THIS STAGE " + match.getMatch().getInning().get(0).getBatting_team().getTeamName4().toUpperCase() + " WERE: " + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$ComparisonGrp$MaxSize$ComparisonRuns*GEOM*TEXT SET " 
								+ CricketFunctions.compareInningData(match,"/", 1 , match.getEventFile().getEvents()) + "\0");
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
						if(((inn.getTotalOvers()*6) + inn.getTotalBalls()) > 16) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " + "15" + "\0");
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
								    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 0 \0");
										break;
								    case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 1 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
										break;
								    case CricketUtil.FOUR: case CricketUtil.SIX: 
								    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 2 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
										break;
								    case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY: 
								    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
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
												(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + this_ball_data.toUpperCase() + "\0");
										break;
								    case CricketUtil.LOG_WICKET: 
								    	if (match.getEventFile().getEvents().get(i).getEventRuns() > 0) {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
													String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns()) + "+W" + "\0");
								      } else {
								    	  print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + "W" + "\0");
								      }
								      break;
								    case CricketUtil.LOG_ANY_BALL:
								    	if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
								    		this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "Pn";
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
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
									    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
														+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
									    	}else {
									    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
														+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
												print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
									    	}
								    	}	
								    }
									break;
								}
									
							    if(ball_count >= 15) {
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
				
				
			/*case "TIMELINE":
				String this_ball_data="";
				int ball_count=0;
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$Timeline4In START \0");
				}
				
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						if(((inn.getTotalOvers()*6) + inn.getTotalBalls()) >= 15) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " + "15" + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " 
												+ ((inn.getTotalOvers()*6) + inn.getTotalBalls()) + "\0");
						}
						if ((match.getEvents() != null) && (match.getEvents().size() > 0)) {
							  for (int i = match.getEvents().size() - 1; i >= 0; i--)
							  {  
								
								switch(match.getEvents().get(i).getEventType()) {
								case CricketUtil.CHANGE_BOWLER: case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
								case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: 
								case CricketUtil.PENALTY: case CricketUtil.LOG_WICKET: case CricketUtil.LOG_ANY_BALL:
									ball_count = ball_count + 1;
									switch (match.getEvents().get(i).getEventType())
								    {
								    case CricketUtil.CHANGE_BOWLER:
								    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 0 \0");
										break;
								    case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 1 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + match.getEvents().get(i).getEventRuns() + "\0");
										break;
								    case CricketUtil.FOUR: case CricketUtil.SIX: 
								    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 2 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + match.getEvents().get(i).getEventRuns() + "\0");
										break;
								    case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY: 
								    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
								    	if(match.getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.WIDE)) {
								    		this_ball_data = "WD";
								    	}else if(match.getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.NO_BALL)) {
								    		this_ball_data = "NB";
								    	}else if(match.getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.LEG_BYE)) {
								    		this_ball_data = "LB";
								    	}else if(match.getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.BYE)) {
								    		this_ball_data = "B";
								    	}else if(match.getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.PENALTY)) {
								    		this_ball_data = "P";
								    	}
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
												(match.getEvents().get(i).getEventRuns() + match.getEvents().get(i).getEventSubExtraRuns()) + this_ball_data.toUpperCase() + "\0");
										break;
								    case CricketUtil.LOG_WICKET: 
								    	if (match.getEvents().get(i).getEventRuns() > 0) {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
													String.valueOf(match.getEvents().get(i).getEventRuns()) + "+W" + "\0");
								      } else {
								    	  print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + "W" + "\0");
								      }
								      break;
								    case CricketUtil.LOG_ANY_BALL:
								    	if(match.getEvents().get(i).getEventExtra() != null) {
								    		if(match.getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
								    			if(match.getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
								    				this_ball_data = String.valueOf(match.getEvents().get(i).getEventRuns() + match.getEvents().get(i).getEventExtraRuns()+
									    					match.getEvents().get(i).getEventSubExtraRuns()) + "WD";
								    			}else {
								    				this_ball_data = String.valueOf(match.getEvents().get(i).getEventRuns() + match.getEvents().get(i).getEventExtraRuns()) + "WD";
								    			}
								    		}
								    		else if(match.getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
							    				if(match.getEvents().get(i).getEventRuns()>0) {
							    					this_ball_data = "NB" + "+" + match.getEvents().get(i).getEventRuns() ;
							    				}else {
							    					this_ball_data = "NB" ;
							    				}
							    			}
								    	}
								    	
							    		if(match.getEvents().get(i).getEventSubExtra() != null && match.getEvents().get(i).getEventSubExtraRuns()>0) {
							    			if(this_ball_data.isEmpty()) {
							    				this_ball_data = String.valueOf(match.getEvents().get(i).getEventSubExtraRuns());
							    			}else {
							    				if(!match.getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
							    					this_ball_data = this_ball_data + "+" + match.getEvents().get(i).getEventSubExtraRuns();
							    				}
							    			}
							    			if(match.getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
								    			this_ball_data = this_ball_data + "NB";
								    		}else if(match.getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.LEG_BYE)) {
								    			this_ball_data = this_ball_data + "LB";
								    		}else if(match.getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.BYE)) {
								    			this_ball_data = this_ball_data + "B";
								    		}else if(match.getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
								    			this_ball_data = this_ball_data + "P";
								    		}
							    		}
								    	if (match.getEvents().get(i).getEventHowOut() != null && !match.getEvents().get(i).getEventHowOut().isEmpty()) {
								    		this_ball_data = this_ball_data + "+W";
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
								    	}else {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$Timeline4$Timeline4Grp$TimelineGrp$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
								    	}
								    }
									break;
								}
									
							    if(ball_count >= 15) {
							    	break;
							    }
							  }
							}
					}
				}
				break;*/
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
		case "ICPL":
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
					case "BOWLING_END":
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
				if(infobar.getLast_bowler() != null && bowler != null) {
					if(infobar.getLast_bowler().getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$BowlerIn CONTINUE REVERSE \0");
						TimeUnit.MILLISECONDS.sleep(500);
					}
				}
				
				if(bowler != null) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BowlerPos$BowlerName*GEOM*TEXT SET " + 
											bowler.getPlayer().getTicker_name().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BowlerPos$FigureGrp$Figure*GEOM*TEXT SET " + 
											bowler.getWickets() + "-" + bowler.getRuns() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BowlerPos$FigureGrp$Overs*GEOM*TEXT SET " + 
											CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + "\0");
					
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
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
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
			case "LAST_WICKET":
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section3$FreeTextSmallIn START \0");
				}
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$FreeTextSmall$FreeTextSmallGrp$FreeTextSmall_Text*GEOM*TEXT SET " + "LAST WICKET : " + CricketFunctions.getLastWicket(match) + "\0");				
				break;
			}
			if(bowler != null) {
				infobar.setLast_bowler(bowler);
			}
			infobar.setLast_bottom_right_section(infobar.getBottom_right_section().toUpperCase());
			break;
		}
		return infobar;
	}	
	public void populateInfobarPrompt(boolean is_this_updating, PrintWriter print_writer, InfobarStats ibs, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				
				if(ibs.getText2() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + ";");
				}else if(ibs.getText1() == null)  {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText2() + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + "-" + ibs.getText2()+ ";");
				}
			}
			break;
			
			
	/*		case "ICPL":
			if(is_this_updating == false) {
				if(is_gaphic_on_bottom == false) {
					resetAnimation(print_writer, session_selected_broadcaster, which_director_on_BottomLeft);
				}
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn START \0");
				resetAnimation(print_writer, session_selected_broadcaster, which_director_on_Bottom);
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section4$FreeTextBigIn START \0");
				which_director_on_Bottom = "STATISTICS";
				is_gaphic_on_bottom = true;
			}
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Double*GEOM*TEXT SET " + "" + "\0");				
	
			if(ibs.getText1() != null && ibs.getText2() != null) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Single*GEOM*TEXT SET " + ibs.getText1() + "  " + ibs.getText2() + "\0");
			}else if(ibs.getText1() != null) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Single*GEOM*TEXT SET " + ibs.getText1() + "\0");				
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section4$FreeTextBigGrp$MaxSize$FreeTextBig_Single*GEOM*TEXT SET " + ibs.getText2() + "\0");				
			}
			break; */
		}
	}
	public void populateInfobarDirector(PrintWriter print_writer,String Dir_value,String session_selected_broadcaster) {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
	public void populateInfobarPowerPlay(PrintWriter print_writer,String Dir_value,String session_selected_broadcaster) {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			switch (Dir_value.toUpperCase()) {
			case "POWERPLAY":
				 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPowerPlay" + " SET " + "P" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START \0");
				break;
			}
			break;
		}
	}
	public void populateMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				//String NewDate = "";
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Shriram_Logo*ACTIVE SET 0 \0");
	
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$AllTeamBadges$T10Logo*TEXTURE*IMAGE SET "+ "C:\\\\Images\\\\ICPL\\\\LOGOS\\\\ICPL.png" + " \0");
				/*if(fix.get(match_number - 1).getMatchnumber() == 22) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + "SEMI FINAL 1" + " \0");
				}else if(fix.get(match_number - 1).getMatchnumber() == 23) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + "SEMI FINAL 2" + " \0");
				}else if(fix.get(match_number - 1).getMatchnumber() == 24) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + "FINAL" + " \0");
				}*/
				//print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET "+ "MATCH " + fix.get(match_number - 1).getMatchnumber() + " \0");
				for(Team TM : team) {
					if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName01" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + TM.getTeamName3().toUpperCase() + "\0");
					}
					if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName02" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + TM.getTeamName3().toUpperCase() + "\0");
					}
				}
				
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, +1);
				if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					//NewDate = new SimpleDateFormat("dd-MMMMM-yyyy").format(cal.getTime());
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + NewDate + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET "+ "TOMORROW" + " \0");

//					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "TOMORROW" + "\0");
//					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET "+ "MATCH " + fix.get(match_number - 1).getMatchnumber() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Info*GEOM*TEXT SET "+ "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET "+ "UP NEXT" + " \0");

//					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "UP NEXT" + "\0");
//					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET "+ "MATCH " + fix.get(match_number - 1).getMatchnumber() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Info*GEOM*TEXT SET "+"LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + " \0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populatePlayOff(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, ParseException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				String NewDate = "";
				String[] dayNumberSuffix =
						  //    0     1     2     3     4     5     6     7     8     9
						     { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
						  //    10    11    12    13    14    15    16    17    18    19
						       "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
						  //    20    21    22    23    24    25    26    27    28    29
						       "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
						  //    30    31
						       "th", "st" };
	
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, +2);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Shriram_Logo*ACTIVE SET 0 \0");
	
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$AllTeamBadges$T10Logo*TEXTURE*IMAGE SET "+ "C:\\\\Images\\\\ICPL\\\\LOGOS\\\\ICPL.png" + " \0");
				if(fix.get(match_number - 1).getMatchnumber() == 22) {
					if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
						NewDate = new SimpleDateFormat(" d'" + dayNumberSuffix[day] + "' MMMMM").format(cal.getTime());
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "SEMI-FINAL 1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + NewDate + " AT 9:00 AM" + " \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "SEMI-FINAL 1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + "MATCH " + fix.get(match_number - 1).getMatchnumber() + " \0");
					}
				}else if(fix.get(match_number - 1).getMatchnumber() == 23) {
					if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
						NewDate = new SimpleDateFormat(" d'" + dayNumberSuffix[day] + "' MMMMM").format(cal.getTime());
						print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + NewDate + " AT 1:30 PM" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "SEMI-FINAL 2" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "SEMI-FINAL 2" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + "UP NEXT" + " \0");
					}
				}else if(fix.get(match_number - 1).getMatchnumber() == 24) {
					if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
						NewDate = new SimpleDateFormat(" d'" + dayNumberSuffix[day] + "' MMMMM").format(cal.getTime());
						print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + NewDate + " AT 1:30 PM" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "FINAL" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "FINAL" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + "15th OCTOBER AT 1:30 PM" + " \0");
					}
				}
				for(Team TM : team) {
					if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName01" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + TM.getTeamName4().toUpperCase() + "\0");
					}
					if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName02" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + TM.getTeamName4().toUpperCase() + "\0");
					}
				}
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Info*GEOM*TEXT SET "+ "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + " \0");
	
				//Calendar cal = Calendar.getInstance();
				//cal.add(Calendar.DATE, +1);
				//int day = cal.get(Calendar.DAY_OF_MONTH);
				
				
				/*if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					NewDate = new SimpleDateFormat(" d'" + dayNumberSuffix[day] + "' MMMMM").format(cal.getTime());
					// DateFormat dateFormat = new SimpleDateFormat(" d'" + dayNumberSuffix + "' MMMM yyyy");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + NewDate + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Info*GEOM*TEXT SET "+ "LIVE FROM " + match.getVenueName().toUpperCase() + " \0");
				}else {
					NewDate = new SimpleDateFormat(" d'" + dayNumberSuffix[day] + "' MMMMM").format(cal.getTime());
					// DateFormat dateFormat = new SimpleDateFormat(" d'" + dayNumberSuffix + "' MMMM yyyy");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$MatchId*GEOM*TEXT SET " + NewDate + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Info*GEOM*TEXT SET "+"LIVE FROM " + match.getVenueName().toUpperCase() + " \0");
				}*/
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$MatchId*GEOM*TEXT SET "+ " " + " \0");
	
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$Shriram_Logo*ACTIVE SET "+ "0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$noname$AllTeamBadges$T10Logo*TEXTURE*IMAGE SET "+ logo_path + "ICPL" + CricketUtil.PNG_EXTENSION + " \0");
	
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName01" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName02" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
	
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchId" + " SET " + match.getSetup().getMatchIdent() + "\0");
//				print_writer.println("-1 RENDERER*TREE*$Main$All$MatchId*GEOM*TEXT SET "+ "" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Info*GEOM*TEXT SET "+ "LIVE FROM "+ match.getSetup().getVenueName().toUpperCase() + " \0");
	
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateLTMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HomeTeamName$HomeTeamFirstName*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
						+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
						+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
				
				if(match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().trim().isEmpty()) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET "+ CricketFunctions.GenerateMatchSummaryStatus(2, 
							match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " (" + match.getSetup().getTargetType().toUpperCase() + ")" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET "+ CricketFunctions.GenerateMatchSummaryStatus(2, 
							match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populatePlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,   MatchAllData match, String session_selected_broadcaster, Configuration config) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				int row_id = 0;
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$HeaderDesign$MaxSize$HeaderText*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Data$Sponsor*ACTIVE SET 0 \0");
				if(TeamId == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Data$HeaderDesign$MaxSize$HeaderText*GEOM*TEXT SET "+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
	
					for(Player hs : match.getSetup().getHomeSquad()) {
						row_id = row_id + 1;
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$PlayerImage*TEXTURE*IMAGE SET "+ photo_path 
									+ match.getSetup().getHomeTeam().getTeamName4() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\ICPL\\Photos\\" +match.getSetup().getHomeTeam().getTeamName4() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$PlayerImage*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()
								+ "\\c\\Images\\ICPL\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
						}
						
						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ hs.getTicker_name().toUpperCase() + " (C)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerDetail*GEOM*TEXT SET "+ hs.getRole() + " (C)"  + " \0");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ hs.getTicker_name().toUpperCase() + " (WK)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerDetail*GEOM*TEXT SET "+ hs.getRole() + " (WK)"  + " \0");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ hs.getTicker_name().toUpperCase() + " (C & WK)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerDetail*GEOM*TEXT SET "+ hs.getRole() + " (C & WK)"  + " \0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ hs.getTicker_name().toUpperCase() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerDetail*GEOM*TEXT SET "+ hs.getRole() + " \0");
						}
						
					}
				}
				
				else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Data$HeaderDesign$MaxSize$HeaderText*GEOM*TEXT SET "+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " \0");
					for(Player as : match.getSetup().getAwaySquad()) {
						row_id = row_id + 1;
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$PlayerImage*TEXTURE*IMAGE SET "+ photo_path 
									+ match.getSetup().getAwayTeam().getTeamName4() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\ICPL\\Photos\\" + match.getSetup().getAwayTeam().getTeamName4() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$PlayerImage*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path 
									+ match.getSetup().getAwayTeam().getTeamName4() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
						}
						
						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ as.getTicker_name().toUpperCase() + " (C)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerDetail*GEOM*TEXT SET "+ as.getRole() + " (C)"  + " \0");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ as.getTicker_name().toUpperCase() + " (WK)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerDetail*GEOM*TEXT SET "+ as.getRole() + " (WK)"  + " \0");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ as.getTicker_name().toUpperCase() + " (C & WK)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerDetail*GEOM*TEXT SET "+ as.getRole() + " (C & WK)"  + " \0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerName*GEOM*TEXT SET "+ as.getTicker_name().toUpperCase() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Data$ImageAll$Image" + row_id + "$NameGrp$PlayerDetail*GEOM*TEXT SET "+ as.getRole() + " \0");
						}
					}
					
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
	
				//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	public void populateProjectedScore(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ inn.getBatting_team().getTeamName1().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1A*GEOM*TEXT SET "+ "@"+ proj_score_rate[0] +" (CRR)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET "+ proj_score_rate[1] + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1A*GEOM*TEXT SET "+ "@" + proj_score_rate[2] +" RPO" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET "+ proj_score_rate[3] + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1A*GEOM*TEXT SET "+ "@" + proj_score_rate[4] +" RPO" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET "+ proj_score_rate[5] + " \0");
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + "" + "\0");
				for(Inning inn : match.getMatch().getInning()) {
//					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
						if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
	
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						}
						
						if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + match.getSetup().getMaxOvers()*6 + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS TO WIN FROM" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + "" + "\0");
						}else {
							if(Double.valueOf(match.getSetup().getTargetOvers()) == 1) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + Integer.valueOf(match.getSetup().getTargetOvers())*6 + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS TO WIN FROM" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + "" + "\0");
							}
							if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + Integer.valueOf(match.getSetup().getTargetOvers())*6 + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS TO WIN FROM" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + " (VJD)" + "\0");
							}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + Integer.valueOf(match.getSetup().getTargetOvers())*6 + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS TO WIN FROM" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + " (DLS)" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + Integer.valueOf(match.getSetup().getTargetOvers())*6 + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$from*GEOM*TEXT SET " + "RUNS TO WIN FROM" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSText" + " SET " + " " + "\0");
							}
						}
//					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
			//this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
		
	}
	public void populateBugTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
			
			//this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
		
	}
	public void populateTeamSummary(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + " " + "\0");								
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, whichInning, 0, ",", match.getEventFile().getEvents()).split(",");
						
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");								
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + inn.getTotalRuns() + "\0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
	
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
	
				//this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateLtBattingSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "ICPL":
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
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
								if(PlayerId == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name().toUpperCase() + "\0");								
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + bc.getRuns() + "*" + "\0");
									}
									else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + bc.getRuns() + "\0");
									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getBalls() + "\0");
	
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
	
					//this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtBatsmanThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "ICPL":
				if (match == null) {
					System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
				} else if (match.getMatch().getInning() == null) {
					System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
				} else {
					
					for(Inning inn : match.getMatch().getInning()) {
						for(BattingCard bc : inn.getBattingCard()) {
							if (inn.getInningNumber() == whichInning) {
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
								if(PlayerId == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								
	
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name().toUpperCase() + "\0");								
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
					//this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtBowlerThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "ICPL":
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
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
								} else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
					//this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtBowlerSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "ICPL":
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
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBowling_team().getTeamName3().toUpperCase() + "\0");
								if(PlayerId == boc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name().toUpperCase() + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
	
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
	
					//this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtNextToBat(PrintWriter print_writer, String viz_scene, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
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
				//this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateLtBowlerDetails(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "ICPL":
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
					//this.status = CricketUtil.SUCCESSFUL;	
				}
				break;
		}
		
	}
	public void populateFallofWicket(PrintWriter print_writer,String viz_scene,int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Header$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
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
	
				//this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateSplit(PrintWriter print_writer,String viz_scene,int whichInning,int splitValue, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead01*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + " \0");
						
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHeader*GEOM*TEXT SET "+ "BALLS PER " + splitValue + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
	
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead01*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + "\0");
							
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1*ACTIVE SET 0" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2*ACTIVE SET 0" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3*ACTIVE SET 0" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4*ACTIVE SET 0" + " \0");
						
						
						String[] Splitballs = new String[getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size()];
					    for (int i = 0; i < getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size(); i++) {
					    	Splitballs[i] = getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).get(i);
					    	
					    	int row_id = i + 1;
					    	if(i <= 3) {
					    		print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue"+row_id+"*ACTIVE SET 1" + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue"+row_id+"$StatValue1B*GEOM*TEXT SET "+ Splitballs[i] + "\0");
	
					    	}
					    	/*else {
					    		print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue"+row_id+"*ACTIVE SET 0" + " \0");
					    	}*/
					    	
					    	//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+(row_id+1)+"*CONTAINER SET ACTIVE 1;");
				        }
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}	
	}	
	public void populateComparision(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Header*GEOM*TEXT SET " + " " + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Header*GEOM*TEXT SET " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$HomeTeamData$HomeTeamLastName*GEOM*TEXT SET " + inn.getBowling_team().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$HomeTeamData$HomeTeamScore*GEOM*TEXT SET " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$AwayTeamData$AwayTeamLastName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$AwayTeamData$AwayTeamScore*GEOM*TEXT SET " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET " + inn.getBowling_team().getTeamName1().toUpperCase() + " TOTAL " + match.getMatch().getInning().get(0).getTotalRuns() 
									+ "-" + match.getMatch().getInning().get(0).getTotalWickets() + "\0");
						
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateLTPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String session_selected_broadcaster) 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$ImageAll$ImageGrp1$noname$Image1*TEXTURE*IMAGE SET " + " " + "\0");
	
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					String Left_Batsman ="",Right_Batsman="",teamname = "",teamname_logo = "";
					if(match.getSetup().getHomeTeamId() == inn.getBattingTeamId()) {
						teamname = match.getSetup().getHomeTeam().getTeamName4().toUpperCase();
						teamname_logo = match.getSetup().getHomeTeam().getTeamName3().toUpperCase();
					}
					else {
						teamname = match.getSetup().getAwayTeam().getTeamName4().toUpperCase();
						teamname_logo = match.getSetup().getAwayTeam().getTeamName3().toUpperCase();
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + teamname_logo + "\0");
	
					for (Player hs : match.getSetup().getHomeSquad()) {
						if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
							Left_Batsman = hs.getFull_name().toUpperCase();
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + teamname + "\\\\" + hs.getPhoto() + ".png" + "\0");
						}
						if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
							Right_Batsman = hs.getFull_name().toUpperCase();
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + teamname + "\\\\" + hs.getPhoto() + ".png" + "\0");
						}
					}
					
					for (Player as : match.getSetup().getAwaySquad()) {
						if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
							Left_Batsman = as.getFull_name().toUpperCase();
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + teamname + "\\\\" + as.getPhoto() + ".png" + "\0");
						}
						if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
							Right_Batsman = as.getFull_name().toUpperCase();
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + teamname + "\\\\" + as.getPhoto() + ".png" + "\0");
						}
					}
	
					print_writer.println("-1 RENDERER*TREE*$Main$All$Header$MaxSize$BatHeader*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
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
			//this.status = CricketUtil.SUCCESSFUL;
		}
	}
	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<Tournament> tournament,List<Team> team ,
			MatchAllData match, String session_selected_broadcaster) 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			int row_no=0;
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case "ICPL":
				switch(StatType.toUpperCase()) {
				case "MOST_RUNS":
					
					Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "MOST RUNS " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
					
					for(int i = 0; i <= tournament.size() - 1 ; i++) {
						row_no = row_no + 1;
						if(row_no < 6) {
							
							if(tournament.get(i).getPlayerId() == playerid) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
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
						}
					}
					break;
				case "MOST_WICKETS":
					
					Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "MOST WICKETS " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
					
					for(int i = 0; i <= tournament.size() - 1 ; i++) {
						row_no = row_no + 1;
						if(row_no < 6) {	
							if(tournament.get(i).getPlayerId() == playerid) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
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
						}
					}
					break;
				case "MOST_FOURS":
					
					Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "MOST FOURS " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
					
					for(int i = 0; i <= tournament.size() - 1 ; i++) {
						row_no = row_no + 1;
						if(row_no < 6) {	
							if(tournament.get(i).getPlayerId() == playerid) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
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
						}
					}
					break;
				case "MOST_SIXES":
					Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "MOST SIXES " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
					
					for(int i = 0; i <= tournament.size() - 1 ; i++) {
						row_no = row_no + 1;
						if(row_no < 6) {	
							if(tournament.get(i).getPlayerId() == playerid) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + 1 + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
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
						}
					}
					break;
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.180 \0");
				//this.status = CricketUtil.SUCCESSFUL;
				break;
	
			}
		}
	}
	public void populateLandMark(PrintWriter print_writer,String viz_scene, int whichInning, String statType, int playerId, MatchAllData match, String session_selected_broadcaster, Configuration config)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + bc.getPlayer().getFirstname() + "\0");
									if(bc.getPlayer().getSurname() == null) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + "" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + bc.getPlayer().getSurname() + "\0");
									}
	
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
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
														+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + bc.getPlayer().getFirstname().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\ICPL\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + bc.getPlayer().getFirstname().toUpperCase() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\ICPL\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + bc.getPlayer().getFirstname().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
												+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + bc.getPlayer().getFirstname().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\ICPL\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + bc.getPlayer().getFirstname().toUpperCase() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + bc.getPlayer().getFirstname().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
										}
									}
								}
							}
							
							break;
						case "BOWLER":
							for(BowlingCard boc : inn.getBowlingCard()) {
								if(playerId == boc.getPlayerId()) {
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + " " + "\0");
	
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + boc.getPlayer().getFirstname() + "\0");
									if(boc.getPlayer().getSurname() == null) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + "" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + boc.getPlayer().getSurname() + "\0");
									}
									
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Runs*GEOM*TEXT SET "+ boc.getRuns() + " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$Balls*GEOM*TEXT SET "+ boc.getBalls() + " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatHead*GEOM*TEXT SET "+ "ECON " + " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ boc.getEconomyRate() + " \0");
									
									if(inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
														+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + boc.getPlayer().getFirstname().toUpperCase() + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\ICPL\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + boc.getPlayer().getFirstname().toUpperCase() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + boc.getPlayer().getFirstname().toUpperCase() + "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
												+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + boc.getPlayer().getFirstname().toUpperCase() + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\ICPL\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + boc.getPlayer().getFirstname().toUpperCase() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + boc.getPlayer().getFirstname().toUpperCase() + "\0");
										}
									}
								}
							}
							break;
						}
					}
				}
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}
	public void populateFFLandMark(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				//String Home_or_Away="";
				print_writer.println("-1 RENDERER*TREE*$Main$All$MaxSize$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
						for(BattingCard bc : inn.getBattingCard()) {
							if(playerId == bc.getPlayerId()) {
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$MaxSize$PlayerName*GEOM*TEXT SET "+ bc.getPlayer().getFirstname().toUpperCase() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$text$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ bc.getBatterPosition() + " \0");
								
								if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
												+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
									for(Player hs : match.getSetup().getHomeSquad()) {
										if(hs.getPlayerId() == playerId) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
									}
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
											+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
									for(Player as : match.getSetup().getAwaySquad()) {
										if(as.getPlayerId() == playerId) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " 
													+ "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
									}
								}
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}
	public void populateLtEquation(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {	
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
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
	
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	public void populatePointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> point_table, String session_selected_broadcaster,MatchAllData match) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			int row_id=0,omo_num=0;
			DecimalFormat df = new DecimalFormat("0.000");
			print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row1$RowAni$Data$BowlerName*GEOM*TEXT SET "+ point_table.get(0).getTeamName().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPointsSponsor" + " SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPointsLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
	
			for(int i = 0; i <= point_table.size() - 1 ; i++) {
				row_id = row_id + 1;
				if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())  
						|| match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) 
						|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
						|| match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
					omo_num = 1;
				}else {
					omo_num = 0;
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Highlight*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 0 \0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$PointsTable$DataAll$$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 1 \0");
				}
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPointsTeamBadge" + row_id + " SET " + " " + "\0");
	
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPointsTeamBadge" + row_id + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + point_table.get(i).getTeamLogoName().toUpperCase().trim() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTeam" + row_id + " SET " + point_table.get(i).getTeamName().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayedValue" + row_id + " SET " + point_table.get(i).getPlayed() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tWonValue" + row_id + " SET " + point_table.get(i).getWon() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLostValue" + row_id + " SET " + point_table.get(i).getLost() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNRValue" + row_id + " SET " + point_table.get(i).getNoResult() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsValue" + row_id + " SET " + point_table.get(i).getPoints() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNRRValue" + row_id + " SET " + df.format(point_table.get(i).getNetRunRate()) + "\0");
	
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 PointsTableIn 1.400 \0");
	
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	public void populateLtPointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> point_table, MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		int row_id=0;
		for(int i = 0; i <= point_table.size()-1; i++) {
			row_id = row_id + 1;
			//System.out.println(point_table.size());
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row1$RowAni$Data$BowlerName*GEOM*TEXT SET "+ point_table.get(0).getTeamName().toUpperCase() + " \0");
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 0 \0");
			}
			else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Qualified*FUNCTION*Omo*vis_con SET 1 \0");
			}
			//System.out.println(match.getHomeTeam().getTeamName4().toUpperCase());
			//System.out.println(match.getAwayTeam().getTeamName4().toUpperCase());
			if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())  
					|| match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) 
					|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*FUNCTION*Omo*vis_con SET 1 \0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*FUNCTION*Omo*vis_con SET 0 \0");
			}
			/*if(match.getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) || 
					match.getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*FUNCTION*Omo*vis_con SET 1 \0");
			}
			else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*FUNCTION*Omo*vis_con SET 0 \0");
			}*/
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Highlight*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$BowlerName*GEOM*TEXT SET "+ point_table.get(i).getTeamName().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$OversValue*GEOM*TEXT SET "+ point_table.get(i).getPlayed() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$MaidensValue*GEOM*TEXT SET "+ point_table.get(i).getWon() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$RunsValue*GEOM*TEXT SET "+ point_table.get(i).getLost() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$Points_table$RowAll$Table$Row" + row_id + "$RowAni$Data$ScoreGrp$EconomyValue*GEOM*TEXT SET "+ point_table.get(i).getPoints() + " \0");
	
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "A " + point_table.get(i).getTeamName().toUpperCase() + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "B " + point_table.get(i).getPlayed() + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "C " + point_table.get(i).getWon() + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "D " + point_table.get(i).getLost() + ";");
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "H " + point_table.get(i).getPoints() + ";");
	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 \0");
	
		//this.status = CricketUtil.SUCCESSFUL;	
	}
	public void populateBowlerStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId,List<Player> plyr, List<Team> team, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ plyr.get(playerId - 1).getFull_name().toUpperCase() + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName3().toUpperCase() + "\0");
	
				if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("RF")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM FAST" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("RFM")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM FAST MEDIUM" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("RMF")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM MEDIUM FAST" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("RM")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM MEDIUM" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("RSM")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM SLOW MEDIUM" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("ROB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM OFF-BREAK" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("RLB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM LEG-BREAK" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("RAB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM BOWLER" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("LAB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT ARM BOWLER" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("LF")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT ARM FAST" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("LFM")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT ARM FAST MEDIUM" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("LMF")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT ARM MEDIUM FAST" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("LM")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT ARM MEDIUM" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("LSL")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "SLOW LEFT ARM" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("WSL")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT ARM WRIST SPIN" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("LCH")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT ARM CHINAMAN" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("RLG")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM LEG-BREAK" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("WSR")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT ARM WRIST SPIN" + " \0");
				}else if(plyr.get(playerId - 1).getBowlingStyle().equalsIgnoreCase("LSO")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT ARM ORTHODOX" + " \0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + " " + ";");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;	
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
			int row_id = 1;
			String Date = "";
			//String NewDate = "";
			Calendar cal = Calendar.getInstance();
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "" + "\0");
			if(day.toUpperCase().equalsIgnoreCase("TODAY")) {
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "TODAY'S MATCHES " + "\0");
			}
			else if(day.toUpperCase().equalsIgnoreCase("TOMORROW")) {
				cal.add(Calendar.DATE, +1);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "TOMORROW'S MATCHES " + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tTime1 SET AT 9:00 AM \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tTime2 SET AT 1:30 PM \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "LIVE FROM " + match.getSetup().getVenueName() + "\0");
	
			//System.out.println(day.compareTo(fix.get(0).getDate()) + 1); // want it to check which day match is this
			for(int i = 0; i <= fix.size()-1; i++) {
				if(fix.get(i).getDate().equalsIgnoreCase(Date)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamBadge" + row_id + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + fix.get(i).getHome_Team().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + row_id + " SET " + fix.get(i).getHome_Team().getTeamName1().toUpperCase() + "\0");
					/*if(fix.get(i).getMatchnumber() == 22) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchId" + row_id + " SET " + "SEMI FINAL 1" + "\0");
					}else if(fix.get(i).getMatchnumber() == 23) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchId" + row_id + " SET " + "SEMI FINAL 2" + "\0");
					}*/ 
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchId" + row_id + " SET " + "MATCH " + fix.get(i).getMatchnumber() + "\0");
	
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamBadge" + row_id + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + fix.get(i).getAway_Team().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + row_id + " SET " + fix.get(i).getAway_Team().getTeamName1().toUpperCase() + "\0");
	
					row_id = row_id +1;
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 \0");
			//this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	public void populatePreviousSummary(PrintWriter print_writer, String viz_scene, int match_number,List<MatchAllData> mtch,List<Fixture> fix, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				for(int j = 0; j <= mtch.size() - 1; j++) {
					int row_id = 0, max_Strap = 0;
					String teamname = "",teamname_logo="";
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$Shriram_Logo*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + "\\" + "ICPL.png" +" \0");
					
					for(int i = 1; i <= 2 ; i++) {
	
						if(i == 1) {
							row_id = 0;
							max_Strap = 4;
							if(mtch.get(j).getMatch().getInning().get(i-1).getBattingTeamId() == mtch.get(j).getSetup().getTossWinningTeam()) {
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
							if(mtch.get(j).getMatch().getInning().get(i-1).getBattingTeamId() == mtch.get(j).getSetup().getTossWinningTeam()) {
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
						print_writer.println("-1 RENDERER*TREE*$Main$All$Summary$SummaryHeader*GEOM*TEXT SET " + "SUMMARY - " + "MATCH " + fix.get(match_number - 1).getMatchnumber() + "\0");
						
						
						if(mtch.get(j).getMatch().getInning().get(i-1).getBattingTeamId() == mtch.get(j).getSetup().getHomeTeamId()) {
							teamname = mtch.get(j).getSetup().getHomeTeam().getTeamName1();
							teamname_logo  = mtch.get(j).getSetup().getHomeTeam().getTeamName3().toUpperCase() ;
						} else {
							teamname = mtch.get(j).getSetup().getAwayTeam().getTeamName1();
							teamname_logo = mtch.get(j).getSetup().getAwayTeam().getTeamName3().toUpperCase() ;
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumTeamBadge" + i + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + teamname_logo + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
						
						if(mtch.get(j).getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + mtch.get(j).getMatch().getInning().get(i-1).getTotalRuns() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + mtch.get(j).getMatch().getInning().get(i-1).getTotalRuns() 
									+ slashOrDash + String.valueOf(mtch.get(j).getMatch().getInning().get(i-1).getTotalWickets()) + "\0");	
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Overs*GEOM*TEXT SET " + 
								CricketFunctions.OverBalls(mtch.get(j).getMatch().getInning().get(i-1).getTotalOvers(),mtch.get(j).getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
						
						if(mtch.get(j).getMatch().getInning().get(i-1).getBattingCard() != null) {
							Collections.sort(mtch.get(j).getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
							
							for(BattingCard bc : mtch.get(j).getMatch().getInning().get(i-1).getBattingCard()) {
								if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
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
	
						for(int k = row_id + 1; k <= max_Strap; k++) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + k + "$RowAni$RowOmo$Dehighlight$Batsman*ACTIVE SET 0 \0");
						}
						
						if(i == 1) {
							row_id = 1;
						}
						else {
							row_id = 5;
						}
	
						if(mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard() != null) {
							
							Collections.sort(mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
	
							for(BowlingCard boc : mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard()) {
								if(boc.getWickets() > 0 ) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler*ACTIVE SET 1 \0");
									
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
					if(mtch.get(j).getMatch().getMatchResult() != null) {
						if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
						}
						else if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ "MATCH TIED" + "\0");
						}
						else if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ mtch.get(j).getMatch().getMatchStatus().toUpperCase() + "\0");
						}
						else if(mtch.get(j).getMatch().getMatchResult().split(",")[2].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ "MATCH TIED - " + CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
						}
					}
					else {
						if(mtch.get(j).getSetup().getTargetType() == "") {
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
						}
						else if(mtch.get(j).getSetup().getTargetType() != null) {
							if(mtch.get(j).getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
										+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
							}
							else if(mtch.get(j).getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " 
										+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 SummaryIn 1.316 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateLtMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " " + "\0");
				
				for(Team TM : team) {
					if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + TM.getTeamName3().toUpperCase() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + "" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
					}
					if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + TM.getTeamName3().toUpperCase() + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + "" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
					}
				}
				//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "MATCH " + fix.get(match_number - 1).getMatchnumber() + "\0");
	
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, +1);
				if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "TOMORROW " + "- MATCH " + fix.get(match_number - 1).getMatchnumber() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "UP NEXT " + "- MATCH " + fix.get(match_number - 1).getMatchnumber() + "\0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.500 \0");
	
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateVizInfobarTop(boolean is_this_updating, PrintWriter print_writer, String TopStats, MatchAllData match, String session_selected_broadcaster)
	{
		//System.out.println("TopStats " + TopStats);
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
		case "ICPL":
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
								if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)) {
									Player_id = boc.getPlayerId();
								}
							}
							
							String[] this_over = CricketFunctions.getEventsText(CricketUtil.OVER,Player_id,",", match.getEventFile().getEvents(),0).split(",");
							if(CricketFunctions.getEventsText(CricketUtil.OVER,Player_id,",", match.getEventFile().getEvents(),0).length() == 0) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vThisOver" + " SET " + "0" + "\0");
			
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vThisOver" + " SET " + this_over.length + "\0");
							}
							
							for(int i=0;i < this_over.length;i++) {
	
								if(this_over[i].toUpperCase().equalsIgnoreCase("WD+W") || this_over[i].toUpperCase().equalsIgnoreCase("W") 
										|| this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.FOUR) || this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.SIX)) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
											+ (i+1) + "*FUNCTION*Omo*vis_con SET 2 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
								}else if(this_over[i].toUpperCase().equalsIgnoreCase("WD") || this_over[i].toUpperCase().equalsIgnoreCase("NB")
										 || this_over[i].toUpperCase().contains("B") || this_over[i].toUpperCase().contains("LB") || this_over[i].toUpperCase().contains("Pn")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
											+ (i+1) + "*FUNCTION*Omo*vis_con SET 4 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
								} else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BottomPart$thisover$ThisOver$ThisOver$Ball" 
											+ (i+1) + "*FUNCTION*Omo*vis_con SET 1 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
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
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				//String Home_or_Away="";
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ " " + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET "+ plyr.get(playerId - 1).getFull_name().toUpperCase() + " \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName3().toUpperCase() + "\0");
	
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Brushes$noname$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
						team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName1() + ".png" + "\0");
				
				if(plyr.get(playerId - 1).getBattingStyle().equalsIgnoreCase("RHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "RIGHT HAND BATTER" + " \0");
				}else if(plyr.get(playerId - 1).getBattingStyle().equalsIgnoreCase("LHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "LEFT HAND BATTER" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + " " + " \0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
			//this.status = CricketUtil.SUCCESSFUL;	
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
				
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
		
	}
	public void populateManhattan(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				int maxRuns = 0,runsIncr = 0;
				double lngth = 0;
				Inning inning = null;
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ " " + " \0");				
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
	
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo02" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
	
						
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
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$TeamAll1$BarGrp$BarAll*FUNCTION*BarValues*Bar_Value__" + ( j ) + " SET " + lngth + "\0");
					
						if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vWkt" + (j ) + " SET " + Integer.valueOf(
									CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + "\0");
	
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vWkt" + (j) + " SET " + "0" + "\0");
						}
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$TeamAll1$BarGrp$BarAll$Bar" + (j ) + "*ACTIVE SET 0" + "\0");
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
	
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
			
	}
	public void populateWorm(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
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
				if(((match.getMatch().getInning().get(0).getTotalOvers()*6) + match.getMatch().getInning().get(0).getTotalBalls()) > ((match.getMatch().getInning().get(1).getTotalOvers()*6) 
						+ match.getMatch().getInning().get(1).getTotalBalls())) {
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase("NO")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ "AT THIS STAGE " 
									+ match.getMatch().getInning().get(0).getBatting_team().getTeamName1().toUpperCase() + " WERE: " + CricketFunctions.compareInningData(match,"-", 1 , match.getEventFile().getEvents()) + " \0");
						}
					}
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + 
					CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
				}
				
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$Header$MaxSize$BatHeader*GEOM*TEXT SET "+ "COMPARISON" + " \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/ICPL/Logos/ICPL" + "\0");
	
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
					 	print_writer.println("-1 RENDERER*TREE*$Main$$All$Worm$WormAll$WormGrp$Man20$group$PlayerNameGrp$Row" + (5 - k) + "$RowAni$Runs*GEOM*TEXT SET " + runsIncr *  (k + 1) + "\0");
					}
					
					row_id = row_id + 1;
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp1$Band*MATERIAL*COLOR SET 0.83137254902 0.2 0.560784313725 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2$Band*MATERIAL*COLOR SET 0.274509803922 0.0235294117647 0.356862745098 \0");
	
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$TeamName*GEOM*TEXT SET "+ teamname + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Score*GEOM*TEXT SET "+ 
										match.getMatch().getInning().get(inn_count-1).getTotalRuns() + "-" + match.getMatch().getInning().get(inn_count-1).getTotalWickets() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Overs*GEOM*TEXT SET "+ 
										CricketFunctions.OverBalls(match.getMatch().getInning().get(inn_count-1).getTotalOvers(), match.getMatch().getInning().get(inn_count-1).getTotalBalls()) + " \0");
					
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXFit SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXFit SET 1 \0");
	
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXOffset SET 10 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXOffset SET 10 \0");
	
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$group*FUNCTION*ControlParameter*input SET " + (Math.floor(Lngth * 1e1) / 1e1) + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "*GEOM*DataY SET " + cumm_runs.replaceFirst("0,", "") + " \0");
					
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
					}
					else {
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
						print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
						if(j < CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).size()) {
							if(CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + Integer.valueOf(
										CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
								
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Worm$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
							}
						}
					}
				}	
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 In$DataIn 1.780 \0");
	
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
			
	}
	public void populateHighestScore(PrintWriter print_writer,String viz_scene,List<Tournament> tournament_high_score,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException 
	{
		int row_no = 0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			
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
	 		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + "ICPL" + "\0");
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
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateBatGriff(PrintWriter print_writer,String viz_scene,int whichinning, int PlayerId, List<Tournament> tournament_high_score,List<Team> team,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException 
	{
		int row_no = 0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			
			int omo_num = 0,runs = 0;
			
			List<BestStats> top_ten_beststat = new ArrayList<BestStats>();
			for(Tournament tourn : tournament_high_score) {
				for(BestStats bs : tourn.getBatsman_best_Stats()) {
					top_ten_beststat.add(bs);
				}
			}
			
			
			for(int i = 0; i <= top_ten_beststat.size() - 1 ; i++) {
				if(top_ten_beststat.get(i).getPlayer().getPlayerId() == PlayerId) {
					row_no = row_no + 1;
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_no + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + 
							team.get(top_ten_beststat.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
							top_ten_beststat.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + 
							team.get(top_ten_beststat.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + "\0");
	
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_no + " SET " + "v " + top_ten_beststat.get(i).getOpponentTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + top_ten_beststat.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "MATCHES" + "\0");
					//System.out.println(top_ten_beststat.get(i).getBestEquation());
					//System.out.println();
					if(top_ten_beststat.get(i).getBestEquation() % 2 == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_no + " SET " + top_ten_beststat.get(i).getBestEquation() / 2 + "\0");
						runs = runs + top_ten_beststat.get(i).getBestEquation() / 2;
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_no + " SET " + top_ten_beststat.get(i).getBestEquation() / 2 + "*" + "\0");
						runs = runs + top_ten_beststat.get(i).getBestEquation() / 2;
					}
					//runs = runs ;
					if(top_ten_beststat.get(i).getBestEquation() == 0 && top_ten_beststat.get(i).getBalls() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_no + " SET " + "DNB" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_no + " SET " + "" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_no + " SET " + top_ten_beststat.get(i).getBalls() + "\0");
					}
				}
			}
			for(Inning inn : match.getMatch().getInning()) {
				for (BattingCard bc : inn.getBattingCard()) {
					if(bc.getPlayerId() == PlayerId) {
						omo_num = 1;
					}
				}
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "EXTRAS: " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "ttStatValue3" + " SET " + runs + "\0");
	
					/*if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "ttStatValue3" + " SET " + runs + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "ttStatValue3" + " SET " + inn.getTotalRuns() + "-" + inn.getTotalRuns() + "\0");
					}*/
				}
			}
			if(omo_num == 1) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + "1" + "\0");
				omo_num = omo_num + 1;
	
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + "0" + "\0");
	
			}
	
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateBallGriff(PrintWriter print_writer,String viz_scene,int whichinning, int PlayerId, List<Tournament> tournament_high_score,List<Team> team,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException 
	{
		int row_no = 0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			
			int omo_num = 0,wickets = 0;
			List<BestStats> top_bowler_beststats = new ArrayList<BestStats>();
			for(Tournament tourn : tournament_high_score) {
				for(BestStats bfig : tourn.getBowler_best_Stats()) {
					top_bowler_beststats.add(bfig);
				}
			}
			for(int i = 0; i <= top_bowler_beststats.size() - 1 ; i++) {
				if(top_bowler_beststats.get(i).getPlayer().getPlayerId() == PlayerId) {
					row_no = row_no + 1;
					//System.out.println(top_bowler_beststats.get(i).getBestEquation());
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_no + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" + 
							team.get(top_bowler_beststats.get(i).getPlayer().getTeamId() - 1).getTeamName4().toUpperCase() + "\\\\" + 
							top_bowler_beststats.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + 
							team.get(top_bowler_beststats.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tVersusTeamName" + row_no + " SET " + "v " + top_bowler_beststats.get(i).getOpponentTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + " SET " + top_bowler_beststats.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "MATCHES" + "\0");
					
					if(top_bowler_beststats.get(i).getBestEquation() % 1000 > 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_no + " SET " + 
								((top_bowler_beststats.get(i).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(i).getBestEquation() % 1000)) + "\0");
						wickets = wickets + ((top_bowler_beststats.get(i).getBestEquation() / 1000) + 1 );
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_no + " SET " + 
								(top_bowler_beststats.get(i).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(i).getBestEquation()) + "\0");
						wickets = wickets + (top_bowler_beststats.get(i).getBestEquation() / 1000);
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + row_no + " SET " + (top_bowler_beststats.get(i).getBalls()/6) + "." + (top_bowler_beststats.get(i).getBalls()%6) + "\0");
				}/*else if(top_bowler_beststats.get(i).getPlayerId() != PlayerId) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + row_no + " SET " + "-" + "\0");
				}*/
				
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "ttStatValue3" + " SET " + wickets + "\0");
	
			for(Inning inn : match.getMatch().getInning()) {
				for (BowlingCard boc : inn.getBowlingCard()) {
					if(boc.getPlayerId() == PlayerId) {
						omo_num = 1;
					}
				}
				/*if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "EXTRAS: " + inn.getTotalExtras() + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "ttStatValue3" + " SET " + wickets + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "ttStatValue3" + " SET " + inn.getTotalRuns() + "-" + inn.getTotalRuns() + "\0");
					}
				}*/
			}
			
			if(omo_num == 1) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + "1" + "\0");
				omo_num = omo_num + 1;
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow" + row_no + " SET " + "0" + "\0");
			}
			
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,
			MatchAllData match, String session_selected_broadcaster) {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				double strike_rate = 0 , economy_rate=0;
				DecimalFormat df = new DecimalFormat("0.00");
	
				print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSponsor" + " SET " + "IMAGE*/Default/ICPL2023/Logos/"  + "" + "\0");
								
				for(int i = 0; i <= this_series.size() - 1 ; i++) {
					if(this_series.get(i).getPlayerId() == Playerid) {
						
						if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + 
								match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" 
									+ match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + ".png" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamBadge" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" + 
								match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + " SET " + "C:\\\\Images\\\\ICPL\\\\Photos\\\\" 
									+ match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + ".png" + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + " SET " + this_series.get(i).getPlayer().getFirstname() + "\0");
						if(this_series.get(i).getPlayer().getSurname() == null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + "" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + this_series.get(i).getPlayer().getSurname() + "\0");
						}
	
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
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Row3$RowAnimation$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
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
					}
				}
				print_writer.println("-1 RENDERER*TREE*$Main$All$BottomInfo$Bottom_Info*GEOM*TEXT SET " + "THIS SERIES" + "\0");			
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				
				//this.status = CricketUtil.SUCCESSFUL;
	
			}
			break;
		}
	}
	public void populateLTThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,
			MatchAllData match, String session_selected_broadcaster) {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICPL":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				
				double strike_rate = 0 , economy_rate=0;
				int k=0;
				DecimalFormat df = new DecimalFormat("0.00");
			
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
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Sponsor$Sponsor*ACTIVE SET 0 \0");	
				
				for(int i = 0; i <= this_series.size() - 1 ; i++) {
					if(this_series.get(i).getPlayerId() == Playerid) {
						
						if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
									+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/ICPL2023/Logos/" 
									+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + this_series.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
						
						switch(TypeofProfile.toUpperCase()) {
						case CricketUtil.BATSMAN:
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getRuns() + "\0");
							
							if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "S/R" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");	
							}else {
								strike_rate = this_series.get(i).getRuns() * 100;
								strike_rate = strike_rate/this_series.get(i).getBallsFaced();
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "S/R" + "\0");
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
						
					}
	
				}
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "THIS SERIES" + "\0");
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.500 \0");
				//this.status = CricketUtil.SUCCESSFUL;
	
			}
			break;
		}
	}

	public static List<String> getThisOverRunsAndWickets(String which_data,int inn_num,MatchAllData match,List<Event> events) {
		List<String> this_over_run = new ArrayList<String>();
		int total_runs=0, total_wickets = 0,wickets_count = 0;
		switch(which_data.toUpperCase()){
		case CricketUtil.RUN:
			if((events != null) && (events.size() > 0)) {
			for(int i = 0; i <= events.size() - 1; i++) {
				if(events.get(i).getEventInningNumber() == inn_num) {
					switch(events.get(i).getEventType()) {
					case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
			        case CricketUtil.FOUR: case CricketUtil.SIX: 
			        	total_runs += events.get(i).getEventRuns();
			          break;
			          
			        case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY:
			        	total_runs += events.get(i).getEventRuns();
			        	break;
			        
			        case CricketUtil.LOG_ANY_BALL:
			        	total_runs += events.get(i).getEventRuns();
				          if (events.get(i).getEventExtra() != null) {
				        	 total_runs += events.get(i).getEventExtraRuns();
				          }
				          if (events.get(i).getEventSubExtra() != null) {
				        	 total_runs += events.get(i).getEventSubExtraRuns();
				          }
				          break;
					}
					if ((events.get(i).getEventType().equalsIgnoreCase(CricketUtil.END_OVER) || wickets_count >= 10 || 
							events.get(i).getEventOverNo() == match.getSetup().getMaxOvers())) {
						this_over_run.add(String.valueOf(total_runs));
						//System.out.println(over_val);
						total_wickets = 0;
						total_runs=0;
						continue;
					}
				}
			}
		}
			break;
		case CricketUtil.WICKET:
			if((events != null) && (events.size() > 0)) {
			for(int i = 0; i <= events.size() - 1; i++) {
				if(events.get(i).getEventInningNumber() == inn_num) {
					switch(events.get(i).getEventType()) {
			        case CricketUtil.LOG_WICKET:
			        	if(!events.get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
			        		total_wickets = total_wickets + 1 ;
				        	wickets_count = wickets_count + 1 ;
			        	}
			        	
			        	break;
					}
					if((events.get(i).getEventType().equalsIgnoreCase(CricketUtil.END_OVER) || wickets_count >= 10 || 
							events.get(i).getEventOverNo() == match.getSetup().getMaxOvers())) {
						this_over_run.add(String.valueOf(total_wickets));
						total_wickets = 0;
						total_runs=0;
						continue;
					}
				}
			}
		}
			break;
		}
		
		return this_over_run;
		//return String.valueOf(total_runs);
	}
	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException
	{
		
		switch(whichGraphic) {
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			TimeUnit.SECONDS.sleep(1);
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
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET": case "LTMATCH_PROMO":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS": case "TEAMS_LOGO":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "MATCHID": case "LT_THIS-SERIES":
		case "L3PLAYERPROFILE": case "FFPLAYERPROFILE":	case "TEAMLINEUP": case "DOUBLETEAMS": case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH":
		case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE": case "BOWLER_STYLE": case "BATSMAN_STYLE": case "MANHATTAN": case "PARTNERSHIP": case "HOWOUT_WITHOUT": case "MATCH_PROMO":
		case "MOSTRUNS": case "MOSTWICKETS": case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM": case "QUICK_HOWOUT": case "SQUAD": case "TIEID-DOUBLE": case "FF_THIS-SERIES":
		case "MINI-SCORECARD": case "MINI-BOWLINGCARD": case "BUG_POWERPLAY": case "LEADERBOARD": case "MANUAL": case "BATGRIFF": case "BALLGRIFF": case "PLAYOFF":
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
		case "RESET":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentIn START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		/*case "SCOREBUG":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*MainIn START \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;*/
		}	
	}	
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic)
	{
		switch(whichGraphic.toUpperCase()) {
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
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET": case "MATCH_PROMO":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS": case "TEAMS_LOGO":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "MATCHID": case "LT_THIS-SERIES":
		case "L3PLAYERPROFILE": case "FFPLAYERPROFILE":	case "TEAMLINEUP": case "DOUBLETEAMS": case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH":
		case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE": case "BOWLER_STYLE": case "BATSMAN_STYLE": case "MANHATTAN": case "PARTNERSHIP": case "HOWOUT_WITHOUT": case "SQUAD": case "FF_THIS-SERIES":
		case "MOSTRUNS": case "MOSTWICKETS": case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM": case "LTMATCH_PROMO": case "QUICK_HOWOUT": case "TIEID-DOUBLE":
		case "MINI-SCORECARD": case "MINI-BOWLINGCARD": case "BUG_POWERPLAY": case "LEADERBOARD": case "MANUAL": case "BATGRIFF": case "BALLGRIFF": case "PLAYOFF":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
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
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			ident_on_screen = false;
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "ANIMATE-OUT-INFOBAR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		case "ANIMATE-OUT-IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}	
	}
	
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
	public static String getPowerPlayScore(MatchAllData match,int inn_num,List<Event> events) {
		
		int total_run_PP=0, total_wickets_PP=0,powerplay_over = 0;
		for(Inning inn : match.getMatch().getInning()) {
			if((CricketFunctions.getBallCountStartAndEndRange(match, inn).get(1) >= (inn.getTotalOvers()*6))) {
				powerplay_over = CricketFunctions.getBallCountStartAndEndRange(match, inn).get(1);
			}
			else {
				powerplay_over = CricketFunctions.getBallCountStartAndEndRange(match, inn).get(3);
			}
			if((events != null) && (events.size() > 0)) {
				for(Event evnt : events) {
					if(evnt.getEventInningNumber() == inn_num) {
						int Event_overs = ((evnt.getEventOverNo()*6)+evnt.getEventBallNo());
						if((Event_overs) <= (powerplay_over*6)) {
							switch(evnt.getEventType()) {
							case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
							case CricketUtil.FOUR: case CricketUtil.SIX: 
								total_run_PP += evnt.getEventRuns();
								break;
				          
							case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY:
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
		}
		
		return String.valueOf(total_run_PP)+"-"+String.valueOf(total_wickets_PP);
	}	
	public static List<String> getSplit(int inning_number, int splitvalue, MatchAllData match,List<Event> events) {
		int total_runs = 0, total_balls = 0 ;
		List<String> Balls = new ArrayList<String>();
		if((events != null) && (events.size() > 0)) {
			for (Event evnt : events) {
				if(evnt.getEventInningNumber() == inning_number) {
					//System.out.println("Inn Number" + inning_number);
					int max_balls = (match.getSetup().getMaxOvers() * 6);
					int count_balls = ((match.getMatch().getInning().get(inning_number-1).getTotalOvers() * 6) + match.getMatch().getInning().get(inning_number-1).getTotalBalls());
					
					switch (evnt.getEventType()) {
					case CricketUtil.DOT: case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FOUR:  case CricketUtil.FIVE: case CricketUtil.SIX: 
					case CricketUtil.LEG_BYE: case CricketUtil.BYE: case CricketUtil.LOG_WICKET:
						total_balls = total_balls + 1 ;
						total_runs = total_runs + evnt.getEventRuns();
						break;
					
					case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.PENALTY:
						total_runs = total_runs + evnt.getEventRuns();
						break;
					
					case CricketUtil.LOG_ANY_BALL:
						total_runs += evnt.getEventRuns();
			          if (evnt.getEventExtra() != null) {
			        	 total_runs += evnt.getEventExtraRuns();
			          }
			          if (evnt.getEventSubExtra() != null) {
			        	 total_runs += evnt.getEventSubExtraRuns();
			          }
			          break;
					}
					
					if(count_balls <= max_balls && total_runs >= splitvalue) {
						Balls.add(String.valueOf(total_balls));
						total_runs = total_runs - splitvalue;
						total_balls = 0;
						
						continue;
					}
				}
			}
		}
		return Balls ;
	}
	public String resetAnimation(PrintWriter print_writer,String which_broadcaster, String which_director) {
		String status = "";
		
		switch(which_broadcaster.toUpperCase()) {
		case "ICPL":
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
	public static String getPowerPlayScore(MatchAllData match,Inning inning,int inn_num,List<Event> events) {
		int total_run_PP=0, total_wickets_PP=0;
		if((events != null) && (events.size() > 0)) {
			for(Event evnt : events) {
				if(evnt.getEventInningNumber() == inn_num) {
					int Event_overs = ((evnt.getEventOverNo()*6)+evnt.getEventBallNo());
					if((Event_overs) <= (CricketFunctions.getBallCountStartAndEndRange(match, inning).get(1))) {
						switch(evnt.getEventType()) {
						case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
						case CricketUtil.FOUR: case CricketUtil.SIX: 
							total_run_PP += evnt.getEventRuns();
							break;
			          
						case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY:
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
		return String.valueOf(total_run_PP)+"-"+String.valueOf(total_wickets_PP);
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
	