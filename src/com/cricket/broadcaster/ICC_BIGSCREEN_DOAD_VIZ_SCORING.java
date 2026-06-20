package com.cricket.broadcaster;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import jakarta.xml.bind.JAXBContext;
import com.cricket.model.Statistics;
import com.cricket.model.StatsType;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.service.CricketService;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Configuration;
import com.cricket.model.DuckWorthLewis;
import com.cricket.model.Event;
import com.cricket.model.FallOfWicket;
import com.cricket.model.FantasyImages;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.LeagueTeam;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.Player;
import com.cricket.model.Review;
import com.cricket.model.Setup;
import com.cricket.model.Sponsor;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.controller.IndexController;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ICC_BIGSCREEN_DOAD_VIZ_SCORING extends Scene{

	private String status;
	public Infobar infobar = new Infobar();
	public List<String> this_data_str = new ArrayList<String>();
	public String session_selected_broadcaster = "ICC_BIGSCREEN_DOAD_VIZ_SCORING";
	public String which_graphics_onscreen = "",which_graphics_onscreen1 = "";
	public String team = "",text = "";
	public Statistics stats;
	public StatsType statsType;
	private String logo_path = "IMAGE*/Default/Essentials/Flags/";
	private String local_photo_path  = "C:\\Images\\ICC\\MensT20WorldCup2026\\Player_Images\\";
	private String photo_path = "\\\\c\\\\Images\\\\ICC\\\\MensT20WorldCup2026\\\\Player_Images\\\\";
	private String sponsor_path  = "C:\\Images\\ICC\\MensT20WorldCup2026\\Sponsor\\";
	public String icon_path = "IMAGE*/Default/Essentials/Icons/";
	public String previousData = "";
	public List<Integer> PlayerId, PlayerIdIn;
	public List<Tournament> tournamentData;
	
	public int which_side = 1,current_layer=0,count = 1,loop_value = 0,video_count = 0,video_layer = 1,inning_no = 0,row_id=0;
	public boolean is_video_onScreen = false;
	
	public ICC_BIGSCREEN_DOAD_VIZ_SCORING() {
		super();
	}

	public ICC_BIGSCREEN_DOAD_VIZ_SCORING(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Infobar updateInfobar(Scene scene, MatchAllData match, boolean show_speed, List<PrintWriter> print_writer, CricketService cricketService, 
			Configuration config,List<DuckWorthLewis> dls) throws InterruptedException, IOException
	{
		
		if(which_graphics_onscreen.equalsIgnoreCase("INFO") && infobar.isInfobar_on_screen() == true) {
			infobar = populateInfo(infobar, print_writer,true,match, session_selected_broadcaster);
			infobar = populateVizInfobarMiddle(infobar, true, print_writer, match, session_selected_broadcaster);
		}//getScorebug_last_value
		
		if(which_graphics_onscreen.equalsIgnoreCase("SCOREBUG") && infobar.isInfobar_on_screen() == true) {
			infobar = populateScorebug(print_writer.get(0), true, match, session_selected_broadcaster);
			infobar = populateScorebugChangeOn(print_writer.get(0), infobar.getScorebug_last_value(), match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("ICC_MATCHSUMMARY")) {
			populateMatchSummary(print_writer, true, inning_no, match, cricketService.getAllPlayer(), session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("EQUATION_ICC")) {
			if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 1 && (CricketFunctions.getWicketsLeft(match, 2) <= 0 
					|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0)) {
				
				which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
				popualteResult(print_writer, match, session_selected_broadcaster);
				
				ChangeOn(print_writer, "ANIMATE-IN-RESULTS");
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				popualteResult(print_writer, match, session_selected_broadcaster);
				CutBack(print_writer, "ANIMATE-IN-RESULTS");
				
				which_graphics_onscreen = "RESULTS";
				
			}else if (CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || CricketFunctions.getWicketsLeft(match, 2) <= 0 
					|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
				which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
				popualteResult(print_writer, match, session_selected_broadcaster);
				
				ChangeOn(print_writer, "ANIMATE-IN-RESULTS");
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				popualteResult(print_writer, match, session_selected_broadcaster);
				CutBack(print_writer, "ANIMATE-IN-RESULTS");
				
				which_graphics_onscreen = "RESULTS";
			}else {
				populateEquation(print_writer,true,match, session_selected_broadcaster);
			}
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("EQUATION_IMG")) {
			if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 1 && (CricketFunctions.getWicketsLeft(match, 2) <= 0 
					|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0)) {
				
				which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
				popualteResult(print_writer, match, session_selected_broadcaster);
				
				ChangeOn(print_writer, "ANIMATE-IN-RESULTS");
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				popualteResult(print_writer, match, session_selected_broadcaster);
				CutBack(print_writer, "ANIMATE-IN-RESULTS");
				
				which_graphics_onscreen = "RESULTS";
				
			}else if (CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || CricketFunctions.getWicketsLeft(match, 2) <= 0 
					|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
				which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
				popualteResult(print_writer, match, session_selected_broadcaster);
				
				ChangeOn(print_writer, "ANIMATE-IN-RESULTS");
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				popualteResult(print_writer, match, session_selected_broadcaster);
				CutBack(print_writer, "ANIMATE-IN-RESULTS");
				
				which_graphics_onscreen = "RESULTS";
				
			}else {
				populateEquationWithImgBs(print_writer,true,match,cricketService.getAllPlayer(),cricketService.getTeams(),
						session_selected_broadcaster,config);
			}
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("EQUATIONSHORT_ICC")) {
			if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 1 && (CricketFunctions.getWicketsLeft(match, 2) <= 0 
					|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0)) {
				
				which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
				popualteResult(print_writer, match, session_selected_broadcaster);
				
				ChangeOn(print_writer, "ANIMATE-IN-RESULTS");
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				popualteResult(print_writer, match, session_selected_broadcaster);
				CutBack(print_writer, "ANIMATE-IN-RESULTS");
				
				which_graphics_onscreen = "RESULTS";
				
			}else if (CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || CricketFunctions.getWicketsLeft(match, 2) <= 0 
					|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
				which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
				popualteResult(print_writer, match, session_selected_broadcaster);
				
				ChangeOn(print_writer, "ANIMATE-IN-RESULTS");
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				popualteResult(print_writer, match, session_selected_broadcaster);
				CutBack(print_writer, "ANIMATE-IN-RESULTS");
				
				which_graphics_onscreen = "RESULTS";
				
			}else {
				populateEquationShort(print_writer, true, match, session_selected_broadcaster);
			}
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("PROJECTED_BS")) {
			populateProjectedBs(print_writer,true,match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("COMPARISON_ICC")) {
			populateComparison(print_writer,true,match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("PARTNERSHIP_ICC")) {
			populatePartnership(print_writer, true, cricketService.getAllPlayer(), cricketService.getTeams(), match, 
					session_selected_broadcaster,config);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("THIS_OVER_ICC")) {
			populateThisOver(print_writer, true, match, session_selected_broadcaster, config);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("EXTRAS_ICC")) {
			populateExtras(print_writer, match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("DLS")) {
			populateDlsParScore(print_writer, match, dls);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("ICC_TEAM-BOUNDARY")) {
			populateTeamBoundary(print_writer,false,Integer.valueOf(previousData.split(",")[0]),match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("ICC_BATSMAN-STATS")) {	
			populateBatsmanStats(print_writer, true, Integer.valueOf(previousData.split(",")[0]), Integer.valueOf(previousData.split(",")[1]),
					cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster, config);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("ICC_BOWLER-STATS")) {
			populateBowlerStats(print_writer, true, Integer.valueOf(previousData.split(",")[0]), Integer.valueOf(previousData.split(",")[1]),
					cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster, config);
		}
		
//		CricketFunctions.getInteractive(match);
		return infobar;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<PrintWriter> print_writer, List<Scene> scenes, 
			String valueToProcess, List<Statistics> statistics, List<Tournament> past_tournament_stats, List<HeadToHeadPlayer> head_to_head, Configuration config, 
			List<DuckWorthLewis> dls) throws Exception{
		
		switch (whatToProcess.toUpperCase()) {
		
		case "FREETEXT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "IMAGEDROPDOWN-ICC_GRAPHICS-OPTIONS":	
			return new ObjectMapper().writeValueAsString(cricketService.getSponsor()).toString();
		case "FANTASYDROPDOWN-ICC_GRAPHICS-OPTIONS":	
			return new ObjectMapper().writeValueAsString(cricketService.getFantasyImages()).toString();
		case "SCOREBUG_CHANGEON_GRAPHICS-OPTIONS":
			int inn_number = 0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					inn_number = inn.getInningNumber();
				}
			}
			
			return Integer.toString(inn_number);
			
		case "POPULATE-DECISION": case "POPULATE-OUT_NOT_DECISION": case "POPULATE-MATCH_IDENT": case "POPULATE-INFO": case "POPULATE-TARGET_BS": 
		case "POPULATE-COMPARISON_BS": case "POPULATE-FREE_BS": case "POPULATE-BOUNDARIES_BS": case "POPULATE-PROJECTED_BS": case "POPULATE-EQUATION_BS":
		case "POPULATE-FF-PLAYERPROFILE_BS": case "POPULATE-FF-PLAYERPROFILE_BALL_BS": case "POPULATE-PLAYERMILE_BS": case "POPULATE-START_BS": case "POPULATE-COUNTDOWN_BS":
		case "POPULATE-HOWOUT_BS": case "POPULATE-BOWLERFIG_BS": case "POPULATE-QUICKHOWOUT_BS": case "POPULATE-MATCH_RESULT":
			
		case "POPULATE-LINEUPLONG_ICC": case "POPULATE-LONGLINEUP_ICC": case "POPULATE-ICC_MATCHSUMMARY": case "POPULATE-MILESTONE_ICC": case "POPULATE-SCOREBUG_CHANGEON_ICC": 
		case "POPULATE-INFOBAR_ICC": case "POPULATE-SCOREBUG_ICC": case "POPULATE-LINEUP_ICC": case "POPULATE-FOUR_ICC": case "POPULATE-WIDE_ICC": case "POPULATE-DUCK_ICC": 
		case "POPULATE-WICKET_ICC": case "POPULATE-LINEUPIMAGE_ICC": case "POPULATE-SIX_ICC": case "POPULATE-HAT_TRICK":case "POPULATE-HAT_TRICK_BALL": case "POPULATE-WEATHER_ICC":
		case "POPULATE-FREEHIT_ICC": case "POPULATE-HUNDRED_ICC": case "POPULATE-FIFTY_ICC": case "POPULATE-CATCH_ICC": case "POPULATE-MATCHID_ICC": case"POPULATE-MATCHID_WITH_IMG_ICC": 
		case"POPULATE-GROUP_ICC": case "POPULATE-REVIEW_ICC": case "POPULATE-ICC_INTRO-STATS": case "POPULATE-BOUNDARY_ICC": case "POPULATE-EXTRAS_ICC": case "POPULATE-LINE2FREE_ICC":
		case "POPULATE-IMG_LINE2FREE_ICC": case "POPULATE-FREETEXT_ICC": case "POPULATE-ICC_TEAM-BOUNDARY": case "POPULATE-RUNRATE_ICC": case "POPULATE-COMPARISON_ICC": case "POPULATE-TOSS_ICC": 
		case "POPULATE-TEAMNAME_ICC": case "POPULATE-PLAYERFREETEXT_ICC": case "POPULATE-ICC_QUICKHOWOUT": case "POPULATE-BS_HOWOUT":	case "POPULATE-TARGETFULL_ICC": case "POPULATE-TARGET_ICC":
		case "POPULATE-TARGET_WITH_IMG_ICC": case "POPULATE-EQUATION_ICC": case "POPULATE-EQUATIONSHORT_ICC": case "POPULATE-EQUATION_WITH_IMG_ICC": case "POPULATE-BS_BATSCORE": 
		case "POPULATE-ICC_BOWLER-FIG": case "POPULATE-PARTNERSHIP_ICC": case "POPULATE-SIX_DISTANCE_ICC": case "POPULATE-GROUP_PTSTBLE_ICC": case "POPULATE-BUKH-POINTSTABLE":	
		case "POPULATE-ICC_BATSMAN-STATS": case "POPULATE-ICC_BOWLER-STATS": case "POPULATE-DLS": case "POPULATE-PHASESCORE_ICC":case "POPULATE_ICC_PP1":case "POPULATE_ICC_PP2":case "POPULATE_ICC_PP3":
		case "POPULATE-BATSMAN_STYLE": case "POPULATE-BOWLER_STYLE": case "POPULATE-SPEED": case "POPULATE-RESULTS": case "POPULATE-PHASE_COMPARISON_ICC":
			
		case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-THIS_OVER_ICC":
			
		case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateSpeed(print_writer,valueToProcess.split(",")[0]);
					
					ChangeOn(print_writer, whatToProcess);
					TimeUnit.MILLISECONDS.sleep(2000);
					which_side = 1;
					populateSpeed(print_writer, valueToProcess.split(",")[0]);
					CutBack(print_writer, whatToProcess);
					
					which_graphics_onscreen = "SPEED";
					break;
				case "POPULATE-SPEED":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populateSpeed(print_writer, valueToProcess.split(",")[0]);
					break;
				case"POPULATE-GROUP_PTSTBLE_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					String fileName = "";

					LeagueTable Groups = null;
					if(valueToProcess.contains("SUPER")) {
						fileName = "Group" + valueToProcess.split(",")[1].split(" ")[3] + ".xml";
					}else {
						fileName = "Group" + valueToProcess.split(",")[1].split(" ")[1] + ".xml";
					}
					
					if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + fileName).exists()) {
						Groups = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
								new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + fileName));
					}
					populateGroupPointsTable(print_writer, Groups.getLeagueTeams(), session_selected_broadcaster, match, valueToProcess.split(",")[1], 
							cricketService.getTeams());
					break;
				case "POPULATE-RESULTS":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					popualteResult(print_writer, match, session_selected_broadcaster);
					break;
					
				case "POPULATE-BUKH-POINTSTABLE":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					LeagueTable group = null;
					String groups = "";
//					if(valueToProcess.split(",")[1].equalsIgnoreCase("A")) {
//						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupA.xml").exists()) {
//							group = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
//									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupA.xml"));
//							groups = "GROUP A";
//						}
//					}else if(valueToProcess.split(",")[1].equalsIgnoreCase("B")) {
//						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupB.xml").exists()) {
//							group = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
//									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupB.xml"));
//							groups = "GROUP B";
//						}
//					}
					
					group = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
							new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "LeagueTable.xml"));
					
					populatePointsTable(print_writer, group.getLeagueTeams(), session_selected_broadcaster, match, groups, cricketService.getTeams());
					break;	
				case "POPULATE-LINEUP_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populateLineup(print_writer,Integer.valueOf(valueToProcess.split(",")[0]),match, session_selected_broadcaster);
					break;
				case "POPULATE-LINEUPIMAGE_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					System.out.println(valueToProcess);
					populateLineupImage(print_writer, false, Integer.valueOf(valueToProcess.split(",")[0]), valueToProcess.split(",")[1], 
							match, session_selected_broadcaster, config, statistics, head_to_head, cricketService);
					break;	
				case "POPULATE-SCOREBUG_CHANGEON_ICC":
					populateScorebugChangeOn(print_writer.get(0), valueToProcess,match, session_selected_broadcaster);
					break;
				case "POPULATE-SCOREBUG_ICC":
					populateScorebug(print_writer.get(0),false,match, session_selected_broadcaster);
					infobar.setScorebug_last_value("");
					break;
				case "POPULATE-INFOBAR_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateInfobar(infobar, print_writer, false, match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGETFULL_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateTargetFull(print_writer,false,match, session_selected_broadcaster,config);
					break;
				case "POPULATE-TARGET_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateTarget(print_writer, false, match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGET_WITH_IMG_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateTargetWithImgBs(print_writer, false, match, cricketService.getAllPlayer(), cricketService.getTeams(),
							session_selected_broadcaster, config);
					break;
				case "POPULATE-EQUATIONSHORT_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateEquationShort(print_writer, false, match, session_selected_broadcaster);
					break;
				case "POPULATE-EQUATION_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateEquation(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-EQUATION_WITH_IMG_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateEquationWithImgBs(print_writer, false, match, cricketService.getAllPlayer(), cricketService.getTeams(),
							session_selected_broadcaster, config);
					break;
				case "POPULATE-BS_BATSCORE":
					populateBatterScore(print_writer.get(0),false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,config);
					break;
				case "POPULATE-ICC_BOWLER-FIG":
					populateBowlerFig(print_writer.get(0),false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,config);
					break;
				case "POPULATE-ICC_BATSMAN-STATS":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populateBatsmanStats(print_writer, false, Integer.valueOf(valueToProcess.split(",")[0]), Integer.valueOf(valueToProcess.split(",")[1]),
							cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster, config);
					break;
				case "POPULATE-ICC_BOWLER-STATS":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populateBowlerStats(print_writer, false, Integer.valueOf(valueToProcess.split(",")[0]), Integer.valueOf(valueToProcess.split(",")[1]),
							cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster, config);
					break;
				case "POPULATE-DLS":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateDlsParScore(print_writer, match, dls);
					break;
				case "POPULATE_ICC_PP1":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populatePowerplay1(print_writer, match);
					break;
				case "POPULATE_ICC_PP2":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populatePowerplay2(print_writer);
					break;
				case "POPULATE_ICC_PP3":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populatePowerplay3(print_writer);
					break;	
				case "POPULATE-PHASESCORE_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populatePhaseBy(print_writer, Integer.valueOf(valueToProcess.split(",")[0]), match, session_selected_broadcaster);
					break;
					
				case "POPULATE-PARTNERSHIP_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populatePartnership(print_writer, false, cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster, config);
					break;
				case "POPULATE-SIX_DISTANCE_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populateSixDistance(print_writer,false,valueToProcess.split(",")[0] , session_selected_broadcaster);
					break;	
				case "POPULATE-ICC_QUICKHOWOUT":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateQuickHowOut(print_writer, false, match, config);
					break;
				case "POPULATE-BS_HOWOUT":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populateFallOfWickets(print_writer, false, Integer.valueOf(valueToProcess.split(",")[0]), Integer.valueOf(valueToProcess.split(",")[1]), 
							cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster, config);
					break;	
				case "POPULATE-PLAYERFREETEXT_ICC":
					populatePlayerfreeText(print_writer.get(0),false,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],valueToProcess.split(",")[3],
							Integer.valueOf(valueToProcess.split(",")[4]),match,cricketService.getAllPlayer(),cricketService.getTeams(), session_selected_broadcaster,config);
					break;
					
				case "POPULATE-BATSMAN_STYLE":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populatePlayerBatAndBowlStyle(print_writer, Integer.valueOf(valueToProcess.split(",")[0]), Integer.valueOf(valueToProcess.split(",")[1]),"BAT",
							match, session_selected_broadcaster, config);
					break;
				 case "POPULATE-BOWLER_STYLE":
					 which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					System.out.println(valueToProcess);
					populatePlayerBatAndBowlStyle(print_writer, Integer.valueOf(valueToProcess.split(",")[0]), Integer.valueOf(valueToProcess.split(",")[1]),"BOWL",
							match,session_selected_broadcaster,config);
					break;
					
				case "POPULATE-ICC_TEAM-BOUNDARY":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					
					previousData = valueToProcess;
					populateTeamBoundary(print_writer,false,Integer.valueOf(valueToProcess.split(",")[0]),match, session_selected_broadcaster);
					break;
				case "POPULATE-RUNRATE_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateRunRate(print_writer,false,match, session_selected_broadcaster);
					break;	
				case "POPULATE-BOUNDARY_ICC":
					populateBoundary(print_writer.get(0),false,valueToProcess.split(",")[0],valueToProcess.split(",")[1] , session_selected_broadcaster);
					break;
				case "POPULATE-EXTRAS_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateExtras(print_writer,match, session_selected_broadcaster);
					break;
				case "POPULATE-REVIEW_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateReview(print_writer, false, match, session_selected_broadcaster);
					break;
				case "POPULATE-COMPARISON_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateComparison(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-PHASE_COMPARISON_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populatePhaseComparison(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-TEAMNAME_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populateTeamName(print_writer,false,valueToProcess.split(",")[0],match, session_selected_broadcaster);
					break;
				case "POPULATE-TOSS_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateToss(print_writer,false,match, session_selected_broadcaster);
					break;	
				case "POPULATE-LINE2FREE_ICC":
					previousData = valueToProcess;
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateline2FreeText(print_writer,false,valueToProcess.split(",")[0],valueToProcess.split(",")[1] , session_selected_broadcaster);
					break;
				case "POPULATE-IMG_LINE2FREE_ICC":
					previousData = valueToProcess;
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateImgline2FreeText(print_writer,false,valueToProcess.split(",")[0],valueToProcess.split(",")[1],
							valueToProcess.split(",")[2] ,cricketService.getTeams(),  session_selected_broadcaster);
					break;
				case "POPULATE-FREETEXT_ICC":
					previousData = valueToProcess;
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					for(NameSuper ns : cricketService.getNameSupers()) {
						  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[0])) {
							  populateFreeText(print_writer,false,ns , session_selected_broadcaster);
						  }
					}
					break;	
				case "POPULATE-MATCHID_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateMatchID(print_writer,match, session_selected_broadcaster);
					break;
				case"POPULATE-MATCHID_WITH_IMG_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateMatchIDWithImgBs(print_writer,false,match,cricketService.getAllPlayer(),cricketService.getTeams(),session_selected_broadcaster,config);
					break;
				case"POPULATE-GROUP_ICC":
					populateGroup(print_writer.get(0),false,valueToProcess.split(",")[1], session_selected_broadcaster,
							cricketService.getTeams().stream().filter(tm->tm.getTeamGroup().equalsIgnoreCase(valueToProcess.split(",")[1])).collect(Collectors.toList()));
					break;	
				case "POPULATE-QUICKHOWOUT_BS":
					populateQuickHowout(print_writer.get(0),false, match, session_selected_broadcaster);
					break;
				case "POPULATE-BOWLERFIG_BS":
					populateBugBowler(print_writer.get(0),false, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-HOWOUT_BS":
					populateHowout(print_writer.get(0),false, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
							Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-START_BS":
					populateCountdown(print_writer.get(0),false,valueToProcess.split(",")[0],match, session_selected_broadcaster);
					break;
				case "POPULATE-COUNTDOWN_BS":
//					populateCountdown(print_writer,valueToProcess.split(",")[1],match, session_selected_broadcaster);
					break;
				case "POPULATE-PLAYERMILE_BS":
					populatePlayerMileStoneBs(print_writer.get(0),false,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							valueToProcess.split(",")[3],match, session_selected_broadcaster);
					break;
				case "POPULATE-ICC_INTRO-STATS":
					populatePlayerIntroStats(print_writer.get(0),false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1])
							,cricketService.getAllPlayer(),cricketService.getTeams(),match, session_selected_broadcaster);
					break;	
				case "POPULATE-EQUATION_BS":
					populateEquationBs(print_writer.get(0),false,match, session_selected_broadcaster);
					break;
				case "POPULATE-PROJECTED_BS":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateProjectedBs(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-BOUNDARIES_BS":
					populateBoundariesBs(print_writer.get(0),false,match, session_selected_broadcaster);
					break;
				case "POPULATE-FREE_BS":
					populateFreeBs(print_writer.get(0),false,valueToProcess.split(",")[1],match, session_selected_broadcaster);
					break;
				case "POPULATE-COMPARISON_BS":
					populateComparisonBs(print_writer.get(0),false,match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGET_BS":
					populateTargetBs(print_writer.get(0),false,match, session_selected_broadcaster);
					break;
				case "POPULATE-INFO":
					populateInfo(print_writer.get(0),false,match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCH_IDENT":
					populateIdentMatch(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCH_RESULT":
					populateMatchResult(print_writer.get(0),false,match, session_selected_broadcaster);
					break;
				case "POPULATE-OUT_NOT_DECISION":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populateOutNotDecision(print_writer,valueToProcess.split(",")[0], session_selected_broadcaster);
					break;
				case "POPULATE-DECISION":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateDecision(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-FOUR_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateFour(print_writer);
					break;
				case "POPULATE-WIDE_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateWide(print_writer);
					break;
				case "POPULATE-DUCK_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateDuck(print_writer);
					break;	
				case "POPULATE-WICKET_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateWicket(print_writer);
					break;
				case "POPULATE-SIX_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateSix(print_writer);
					break;
				case "POPULATE-HAT_TRICK":case "POPULATE-HAT_TRICK_BALL":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					if(whatToProcess.equalsIgnoreCase("POPULATE-HAT_TRICK")) {
						text = "HAT-TRICK";
					}else {
						text = "HAT-TRICK BALL";
					}
					previousData = text;
					populateHatTrick(print_writer,text);
					break;
				case "POPULATE-WEATHER_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					populateWeather(print_writer,false,valueToProcess.split(",")[0],valueToProcess.split(",")[1],valueToProcess.split(",")[2],
							match,session_selected_broadcaster);
					break;
				case "POPULATE-HUNDRED_ICC":case "POPULATE-FIFTY_ICC":case "POPULATE-CATCH_ICC":
					 populateExtraBoundries(print_writer.get(0),false,whatToProcess.replace("POPULATE-", "").replace("_ICC", ""),match, session_selected_broadcaster);
					break;
				case "POPULATE-FREEHIT_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateFreeHit(print_writer);
					break;	
				case "POPULATE-LINEUPLONG_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					
					populateLineupLong(print_writer, false, Integer.valueOf(valueToProcess.split(",")[0]),match, session_selected_broadcaster, 
							config, statistics, head_to_head, cricketService);
					break;
				case "POPULATE-LONGLINEUP_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					
					populateLongLineup(print_writer,false,Integer.valueOf(valueToProcess.split(",")[0]),match, session_selected_broadcaster, config);
					break;
				case "POPULATE-ICC_MATCHSUMMARY":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					inning_no = Integer.valueOf(valueToProcess.split(",")[0]);
					populateMatchSummary(print_writer, false, inning_no, match, cricketService.getAllPlayer(), session_selected_broadcaster);
					break;
				case "POPULATE-MILESTONE_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					previousData = valueToProcess;
					
					populateMileStone(print_writer,false,Integer.valueOf(valueToProcess.split(",")[0]),valueToProcess.split(",")[1],valueToProcess.split(",")[2],
							valueToProcess.split(",")[3],Integer.valueOf(valueToProcess.split(",")[4]),match,cricketService.getAllPlayer(),cricketService.getTeams(), 
							session_selected_broadcaster,config);
					break;
				case "POPULATE-THIS_OVER_ICC":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					populateThisOver(print_writer, false, match, session_selected_broadcaster, config);
					break;	
				case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-L3-PLAYERPROFILE":
					which_side = (!which_graphics_onscreen.isEmpty() ? 2 : 1);
					
					this.status = "NODATABASE";
					
					previousData = valueToProcess;
					
					if(valueToProcess.split(",")[1].equalsIgnoreCase("THISSERIES")) {
						this.status = CricketUtil.SUCCESSFUL;
						tournamentData = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats);
						
						populatePlayerProfileBat(print_writer, false, Integer.valueOf(valueToProcess.split(",")[0]), valueToProcess.split(",")[1], valueToProcess.split(",")[2], 
								tournamentData, null, cricketService, match, session_selected_broadcaster, config);
					}else {
						if(valueToProcess.split(",")[1].equalsIgnoreCase("IT20")) {
							this.status = CricketUtil.SUCCESSFUL;
							statsType = cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[1])).findAny().orElse(null);
							if(statsType == null) {
								this.status = "NODATABASE";
							}

							stats = statistics.stream().filter(st -> st.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[0]).intValue() && 
									statsType.getStatsId() == st.getStatsTypeId()).findAny().orElse(null);
							if(stats == null) {
								this.status = "NODATABASE";
							}
							
							stats.setStats_type(statsType);
							stats = CricketFunctions.updateTournamentWithH2h(stats, head_to_head, match, CricketUtil.FULL);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						}
						else if(valueToProcess.split(",")[1].equalsIgnoreCase("DT20")) {
							this.status = CricketUtil.SUCCESSFUL;
							statsType = cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[1])).findAny().orElse(null);
							if(statsType == null) {
								this.status = "NODATABASE";
							}

							stats = statistics.stream().filter(st -> st.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[0]).intValue() && 
									statsType.getStatsId() == st.getStatsTypeId()).findAny().orElse(null);
							if(stats == null) {
								this.status = "NODATABASE";
							}
							
							
							StatsType statTypes =  cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase("IT20")).findAny().orElse(null);
							if(statTypes == null) {
								this.status = "NODATABASE";
							}
							
							stats.setStats_type(statTypes);
							stats = CricketFunctions.updateTournamentWithH2h(stats, head_to_head, match, CricketUtil.FULL);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
							
							stats.setStats_type(statsType);
						}
						else {
							this.status = CricketUtil.SUCCESSFUL;
							statsType = cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[1])).findAny().orElse(null);
							if(statsType == null) {
								this.status = "NODATABASE";
							}
							
							stats = statistics.stream().filter(st -> st.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[0]).intValue() && 
									statsType.getStatsId() == st.getStatsTypeId()).findAny().orElse(null);
							if(stats == null) {
								this.status = "NODATABASE";
							}
							stats.setStats_type(statsType);
						}
						
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[1])) {
							this.status = CricketUtil.SUCCESSFUL;
							
							populatePlayerProfileBat(print_writer, false, Integer.valueOf(valueToProcess.split(",")[0]), valueToProcess.split(",")[1], valueToProcess.split(",")[2], 
									null, stats, cricketService, match, session_selected_broadcaster, config);
						}
					}
					break;
				}
				Previews(print_writer, whatToProcess);
				//return JSONObject.fromObject(this_doad).toString();
			}
		
		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-OUT-SCOREBUG": case "ANIMATE-IN-CANCEL":
		case "ANIMATE-IN-DECISION": case "ANIMATE-IN-OUT_NOT_DECISION": case "ANIMATE-IN-FREE_BS": case "ANIMATE-IN-MATCH_IDENT": case "ANIMATE-IN-INFO": 
		case "ANIMATE-IN-WICKET":case "ANIMATE-IN-TARGET_BS": case "ANIMATE-IN-COMPARISON_BS": case "ANIMATE-IN-BOUNDARIES_BS": case "ANIMATE-IN-PROJECTED_BS": 
		case "ANIMATE-IN-EQUATION_BS": case "ANIMATE-IN-LTPLAYERPROFILEBAT": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-PLAYERMILE_BS": case "ANIMATE-IN-START_BS": 
		case "ANIMATE-IN-COUNTDOWN_BS": case "ANIMATE-IN-HOWOUT_BS": case "ANIMATE-IN-BOWLERFIG_BS": case "ANIMATE-IN-QUICKHOWOUT_BS":case "ANIMATE-BS_BATTER_SCORE":
		case "ANIMATE-IN-SIX_DISTANCE":	case "ANIMATE-IN-INFOBAR_ICC": case "ANIMATE-IN-SCOREBUG_ICC": case "ANIMATE-IN-PARTNERSHIP_ICC": case "ANIMATE-IN-MATCHID_ICC": 
		case "ANIMATE-IN-FREEHIT_ICC": case "ANIMATE-IN-SCOREBOARD_ICC": case "ANIMATE-IN-FREETEXT_ICC": case "ANIMATE-IN-BOUNDARY_ICC":	case "ANIMATE-IN-LINE2FREE_ICC": 
		case "ANIMATE-IN-EXTRAS_ICC": case "ANIMATE-IN-ICC_BATSMAN-STATS": case "ANIMATE-IN-TARGET_ICC": case "ANIMATE-IN-EQUATION_ICC": case "ANIMATE-IN-RUNRATE_ICC":
		case "ANIMATE-IN-ICC_TEAM-BOUNDARY": case "ANIMATE-IN-TOSS_ICC": case "ANIMATE-IN-ICC_BOWLER-STATS": case "ANIMATE-IN-TEAMNAME_ICC": case "ANIMATE-IN-COMPARISON_ICC": 
		case "ANIMATE-IN-ICC_MATCHSUMMARY": case "ANIMATE-IN-FOUR_ICC": case "ANIMATE-IN-SIX_ICC": case "ANIMATE-IN-MILESTONE_ICC": case "ANIMATE-IN-TARGET_WITH_IMG_ICC":	
		case "ANIMATE-IN-ICC_BALL-SPEED": case "ANIMATE-IN-PLAYERFREETEXT_ICC": case "ANIMATE-IN-THIS_OVER_ICC": case "ANIMATE-IN-LINEUP_ICC": case "ANIMATE-IN-LINEUPIMAGE_ICC": 
		case "ANIMATE-IN-ICC_BOWLER-FIG": case "ANIMATE-IN-PLAYERNAME_ICC": case "CHANGEON_PLAYER_ICC": case "ANIMATE-IN-REVIEW": case "ANIMATE-IN-TARGETFULL_ICC":
		case "ANIMATE-IN-EQUATIONSHORT_ICC": case "ANIMATE-IN-ICC_WAGON": case "ANIMATE-IN-LINEUPLONG_ICC": case "ANIMATE-IN-ICC_IMAGE16_9": case "ANIMATE-IN-ICC_IMAGE4_3": 
		case "ANIMATE-IN-ICC_IMAGELOOP": case "ANIMATE-IN-ICC_WICKET": case "ANIMATE-IN-ICC_WIDE": case "ANIMATE-IN-ICC_DUCK": case "ANIMATE-IN-IMAGEDROPDOWN": 
		case "ANIMATE-IN-ICC_QUICKHOWOUT":  case "ANIMATE-IN-WEATHER_ICC":	case "CHANGEON_VIDEO_ICC": case "ANIMATE-IN-PLAYERVIDEO_ICC": case "ANIMATE-IN-LONGLINEUP_ICC":
		case "ANIMATE-IN-ICC_BALL-DISTANCE": case "ANIMATE-IN-FANTASYDROPDOWN":case "ANIMATE-IN-ICC_CATCH":case "ANIMATE-IN-ICC_FIFTY":case "ANIMATE-IN-ICC_HUNDRED":
		case "ANIMATE-IN-PHASESCORE_ICC": case "ANIMATE-IN-IMG_LINE2FREE_ICC": case "ANIMATE-IN-GROUP_PTSTBLE_ICC": case "ANIMATE-IN-ICC_INTRO-STATS": case "ANIMATE-IN-DLS": 
		case "CHANGEON_INTRO_ICC":	case "ANIMATE-IN-EQUATION_WITH_IMG_ICC": case"ANIMATE-IN-GROUP_ICC": case"ANIMATE-IN-MATCHID_WITH_IMG_ICC":case"ANIMATE-IN-H2H_ICC": 
		case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-HAT_TRICK_BALL":case"ANIMATE-HAT_TRICK":case "ANIMATE-PP1":case "ANIMATE-PP2":case "ANIMATE-PP3": 
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-SPEED": case "ANIMATE-IN-RESULTS": case "ANIMATE-IN-PHASE_COMPARISON_ICC":
			
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-CANCEL":
				current_layer = 5-current_layer;
				break;
			case "ANIMATE-PP1":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populatePowerplay1(print_writer, match);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "PP1";
				break;
			case "ANIMATE-PP2":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populatePowerplay2(print_writer);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "PP2";
				break;
			case "ANIMATE-PP3":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populatePowerplay3(print_writer);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "PP3";
				break;	
			case "ANIMATE-IN-TEAMNAME_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateTeamName(print_writer,false,previousData.split(",")[0],match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "TEAMNAME_ICC";
				break;
			case "ANIMATE-IN-TOSS_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateToss(print_writer,false,match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "TOSS_ICC";
				break;
			case "ANIMATE-IN-SIX_DISTANCE":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateSixDistance(print_writer,false,previousData.split(",")[0] , session_selected_broadcaster);
				
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "SIX_DISTANCE";
				break;
			case "ANIMATE-IN-RUNRATE_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateRunRate(print_writer,false,match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "RUNRATE_ICC";
				break;
			case "ANIMATE-IN-COMPARISON_ICC": 
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateComparison(print_writer,false,match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "COMPARISON_ICC";
				break;
			case "ANIMATE-IN-PHASE_COMPARISON_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populatePhaseComparison(print_writer, false, match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "PHASE_COMPARISON_ICC";
				break;
			case "ANIMATE-IN-PROJECTED_BS":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateProjectedBs(print_writer,false,match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "PROJECTED_BS";
				break;
			case "ANIMATE-IN-MATCHID_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateMatchID(print_writer, match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "MATCHID_ICC";
				break;
			case "ANIMATE-IN-LTPLAYERPROFILEBAT": case "ANIMATE-IN-L3PLAYERPROFILE":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				
				if(previousData.split(",")[1].equalsIgnoreCase("THISSERIES")) {
					populatePlayerProfileBat(print_writer, false, Integer.valueOf(previousData.split(",")[0]), previousData.split(",")[1], previousData.split(",")[2],
							tournamentData, null, cricketService, match, session_selected_broadcaster, config);
				}else {
					populatePlayerProfileBat(print_writer, false, Integer.valueOf(previousData.split(",")[0]), previousData.split(",")[1], previousData.split(",")[2], 
							null, stats, cricketService, match, session_selected_broadcaster, config);
				}
				
				CutBack(print_writer, whatToProcess);
				
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-LTPLAYERPROFILEBAT": 
					which_graphics_onscreen = "LTPLAYERPROFILEBAT";
					break;
				case "ANIMATE-IN-L3PLAYERPROFILE":
					which_graphics_onscreen = "L3PLAYERPROFILE";
					break;
				}
				break;
			case "ANIMATE-IN-LINEUP_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateLineup(print_writer,Integer.valueOf(previousData.split(",")[0]),match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "LINEUP_ICC";
				break;
			case "ANIMATE-IN-EXTRAS_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateExtras(print_writer, match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "EXTRAS_ICC";
				break;
			case "ANIMATE-IN-ICC_TEAM-BOUNDARY":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateTeamBoundary(print_writer,false,Integer.valueOf(previousData.split(",")[0]),match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "ICC_TEAM-BOUNDARY";
				break;
			case "ANIMATE-IN-TARGETFULL_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateTargetFull(print_writer,false,match, session_selected_broadcaster,config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "TARGETFULL_ICC";
				break;
			case "ANIMATE-IN-EQUATION_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateEquation(print_writer,false,match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "EQUATION_ICC";
				break;
			case "ANIMATE-IN-SPEED":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateSpeed(print_writer, previousData.split(",")[0]);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "SPEED";
				break;
			case "ANIMATE-IN-INFOBAR_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateInfobar(infobar, print_writer, false, match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "INFO";
				infobar.setInfobar_on_screen(true);
				break;
			case "ANIMATE-IN-LINEUPLONG_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateLineupLong(print_writer, false, Integer.valueOf(previousData.split(",")[0]), match, session_selected_broadcaster,
						config, statistics, head_to_head, cricketService);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "LINEUPLONG_ICC";
				break;
			case "ANIMATE-IN-DECISION":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateDecision(print_writer, false, match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "DECISION";
				break;
			case "ANIMATE-IN-OUT_NOT_DECISION":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateOutNotDecision(print_writer,previousData.split(",")[0], session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "OUT_NOT_DECISION";
				break;
			case "ANIMATE-IN-DLS":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateDlsParScore(print_writer, match, dls);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "DLS";
				break;
			case "ANIMATE-IN-MILESTONE_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateMileStone(print_writer,false,Integer.valueOf(previousData.split(",")[0]),previousData.split(",")[1],previousData.split(",")[2],
						previousData.split(",")[3],Integer.valueOf(previousData.split(",")[4]),match,cricketService.getAllPlayer(),cricketService.getTeams(), 
						session_selected_broadcaster,config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "MILESTONE_ICC";
				break;
			case "ANIMATE-IN-ICC_BATSMAN-STATS":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateBatsmanStats(print_writer, false, Integer.valueOf(previousData.split(",")[0]), Integer.valueOf(previousData.split(",")[1]),
						cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "ICC_BATSMAN-STATS";
				break;
			case "ANIMATE-IN-ICC_BOWLER-STATS":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateBowlerStats(print_writer, false, Integer.valueOf(previousData.split(",")[0]), Integer.valueOf(previousData.split(",")[1]),
						cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "ICC_BOWLER-STATS";
				break;
			case "ANIMATE-IN-WICKET":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateFallOfWickets(print_writer, false, Integer.valueOf(previousData.split(",")[0]), Integer.valueOf(previousData.split(",")[1]), 
						cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "MATCH_WICKET";
				break;
			case "ANIMATE-IN-ICC_QUICKHOWOUT":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateQuickHowOut(print_writer, false, match, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "ICC_QUICKHOWOUT";
				break;
			case "ANIMATE-IN-ICC_MATCHSUMMARY":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateMatchSummary(print_writer, false, inning_no, match, cricketService.getAllPlayer(), session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "ICC_MATCHSUMMARY";
				break;
			case "ANIMATE-IN-LONGLINEUP_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateLongLineup(print_writer,false,Integer.valueOf(previousData.split(",")[0]),match, session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "LONGLINEUP_ICC";
				break;
			case "ANIMATE-IN-LINEUPIMAGE_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateLineupImage(print_writer, false, Integer.valueOf(previousData.split(",")[0]), previousData.split(",")[1], match, 
						session_selected_broadcaster, config, statistics, head_to_head, cricketService);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "LINEUPIMAGE_ICC";
				break;
			case "ANIMATE-IN-TARGET_WITH_IMG_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateTargetWithImgBs(print_writer, false, match, cricketService.getAllPlayer(), cricketService.getTeams(),
						session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "TARGET_IMG";
				break;
			case "ANIMATE-IN-EQUATION_WITH_IMG_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateEquationWithImgBs(print_writer, false, match, cricketService.getAllPlayer(), cricketService.getTeams(),
						session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "EQUATION_IMG";
				break;
			case "ANIMATE-IN-FOUR_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateFour(print_writer);
				CutBack(print_writer, whatToProcess);
				which_graphics_onscreen = "FOUR_ICC";
				break;
			case "ANIMATE-IN-SIX_ICC":	
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateSix(print_writer);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "SIX_ICC";
				break;
			case "ANIMATE-IN-ICC_WICKET":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateWicket(print_writer);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "ICC_WICKET";
				break;
			case "ANIMATE-IN-ICC_WIDE":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateWide(print_writer);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "ICC_WIDE";
				break;
			case "ANIMATE-IN-ICC_DUCK":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateDuck(print_writer);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "ICC_DUCK";
				break;
			case "ANIMATE-HAT_TRICK_BALL":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateHatTrick(print_writer,previousData);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "HAT_TRICK_BALL";
				break;
			case"ANIMATE-HAT_TRICK":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateHatTrick(print_writer,previousData);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "HAT_TRICK";
				break;
			case "ANIMATE-IN-FREEHIT_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateFreeHit(print_writer);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "FREEHIT_ICC";
				break;
			case "ANIMATE-IN-IMG_LINE2FREE_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateImgline2FreeText(print_writer,false,previousData.split(",")[0],previousData.split(",")[1],
						previousData.split(",")[2] ,cricketService.getTeams(),  session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "IMG_LINE2FREE_ICC";
				break;	
			case "ANIMATE-IN-LINE2FREE_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateline2FreeText(print_writer,false,previousData.split(",")[0],previousData.split(",")[1] , session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "LINE2FREE_ICC";
				break;
			case "ANIMATE-IN-FREETEXT_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				for(NameSuper ns : cricketService.getNameSupers()) {
					  if(ns.getNamesuperId() == Integer.valueOf(previousData.split(",")[0])) {
						  populateFreeText(print_writer,false,ns , session_selected_broadcaster);
					  }
				}
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "FREETEXT_ICC";
				break;
			case"ANIMATE-IN-MATCHID_WITH_IMG_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateMatchIDWithImgBs(print_writer,false,match,cricketService.getAllPlayer(),cricketService.getTeams(),
						session_selected_broadcaster,config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "MATCHID_WITH_IMG_ICC";
				break;
			case "ANIMATE-IN-REVIEW":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateReview(print_writer, false, match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "REVIEW";
				break;
			case "ANIMATE-IN-PARTNERSHIP_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populatePartnership(print_writer, false, cricketService.getAllPlayer(), cricketService.getTeams(), match, 
						session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "PARTNERSHIP_ICC";
				break;
			case "ANIMATE-IN-THIS_OVER_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateThisOver(print_writer, false, match, session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "THIS_OVER_ICC";
				break;
			case "ANIMATE-IN-WEATHER_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateWeather(print_writer, false, previousData.split(",")[0], previousData.split(",")[1], previousData.split(",")[2],
						match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "WEATHER_ICC";
				break;
			case "ANIMATE-IN-EQUATIONSHORT_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateEquationShort(print_writer, false, match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "EQUATIONSHORT_ICC";
				break;
			case "ANIMATE-IN-TARGET_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populateTarget(print_writer, false, match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "TARGET_ICC";
				break;
			case "ANIMATE-IN-PHASESCORE_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populatePhaseBy(print_writer, Integer.valueOf(previousData.split(",")[0]), match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "PHASESCORE_ICC";
				break;
			case "ANIMATE-IN-BATSMAN_STYLE":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populatePlayerBatAndBowlStyle(print_writer, Integer.valueOf(previousData.split(",")[0]), Integer.valueOf(previousData.split(",")[1]),
						"BAT", match, session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "BATSMAN_STYLE";
				break;
			case "ANIMATE-IN-BOWLER_STYLE":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				populatePlayerBatAndBowlStyle(print_writer, Integer.valueOf(previousData.split(",")[0]), Integer.valueOf(previousData.split(",")[1]),
						"BOWL", match, session_selected_broadcaster, config);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "BOWLER_STYLE";
				break;
			case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-GROUP_PTSTBLE_ICC":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				
				LeagueTable group = null;
				String groups = "";
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-POINTSTABLE":
					group = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
							new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "LeagueTable.xml"));
					
					populatePointsTable(print_writer, group.getLeagueTeams(), session_selected_broadcaster, match, groups, cricketService.getTeams());
					break;
				case "ANIMATE-IN-GROUP_PTSTBLE_ICC":
					String fileName = "";
					if(previousData.contains("SUPER")) {
						fileName = "Group" + previousData.split(",")[1].split(" ")[3] + ".xml";
					}else {
						fileName = "Group" + previousData.split(",")[1].split(" ")[1] + ".xml";
					}
					
					group = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
							new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + fileName));
					
					populateGroupPointsTable(print_writer, group.getLeagueTeams(), session_selected_broadcaster, match, previousData.split(",")[1], 
							cricketService.getTeams());
					break;
				}
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "POINTSTABLE";
				break;
			case "ANIMATE-IN-RESULTS":
				ChangeOn(print_writer, whatToProcess);
				TimeUnit.MILLISECONDS.sleep(3000);
				which_side = 1;
				popualteResult(print_writer, match, session_selected_broadcaster);
				CutBack(print_writer, whatToProcess);
				
				which_graphics_onscreen = "RESULTS";
				break;
				
				
				
			case "CLEAR-ALL":
				processAnimation("FRONT", print_writer, "Loop", "SHOW 0.0");
				processAnimation("FRONT", print_writer, "LogoAnimation", "SHOW 0.0");
				processAnimation("FRONT", print_writer, "anim_BigScreen", "SHOW 0.0");
				
				populateLogo(print_writer, which_side);
				
				TimeUnit.MILLISECONDS.sleep(1000);
				
				processAnimation("FRONT", print_writer, "anim_BigScreen$In_Out", "START");
				processAnimation("FRONT", print_writer, "Loop", "START");
				processAnimation("FRONT", print_writer, "LogoAnimation", "START");

				which_graphics_onscreen = "LOGO";
				break;
			case "ANIMATE-OUT-SCOREBUG":
				print_writer.get(0).println("LAYER1*EVEREST*STAGE*DIRECTOR*Out START;");
				which_graphics_onscreen1 = "";
				infobar.setScorebug_on_screen(false);
				break;	
			
			case "ANIMATE-OUT":
				switch(which_graphics_onscreen) {
					case "SCOREBUG":
						print_writer.get(0).println("LAYER1*EVEREST*STAGE*DIRECTOR*Out START;");
						which_graphics_onscreen1 = "";
						infobar.setScorebug_on_screen(false);
						break;
	
					case "COUNTDOWN_BS": case "ICC_DUCK":case "ICC_CATCH":case "ICC_FIFTY":case "ICC_HUNDRED": case "POINTSTABLE": case "BATTER_BS":case "COMPARISON_BS":
					case "TARGET_BS":case "DECISION":case "FANTASYDROPDOWN": case "MATCH_IDENT":case "FREE_BS":case "BOUNDARIES_BS":case "PROJECTED_BS":case "EQUATION_BS":
					case "PLAYERPROFILE_BALL_BS":case "PLAYERPROFILE_BS":case "PLAYERMILE_BS":case "HOWOUT_BS": case "ICC_WIDE":case "IMAGEDROPDOWN":case "ICC_QUICKHOWOUT":
					case "PLAYERVIDEO_ICC":case "WEATHER_ICC": case "LONGLINEUP_ICC":case "ICC_BALL-DISTANCE":case "BOWLERFIG_BS":case "MATCH_WICKET":case "QUICKHOWOUT_BS":
					case "PARTNERSHIP_ICC":case "MATCHID_ICC":case "FREEHIT_ICC":case "BOUNDARY_ICC":case "FREETEXT_ICC":case "LINE2FREE_ICC": case "EXTRAS_ICC": 
					case "ICC_BATSMAN-STATS":case "TARGET_ICC":case "EQUATION_ICC":case "RUNRATE_ICC":case "ICC_TEAM-BOUNDARY": case "TOSS_ICC":case "ICC_BOWLER-STATS":
					case "TEAMNAME_ICC":case "COMPARISON_ICC":case "ICC_MATCHSUMMARY":case"GROUP_PTSTBLE_ICC": case "FOUR_ICC":case "SIX_ICC":case "MILESTONE_ICC":
					case "BALL-SPEED":case "TARGET_IMG":case "PLAYERFREETEXT_ICC": case "THIS_OVER_ICC":case "LINEUP_ICC":case "LINEUPIMAGE_ICC":case "ICC_BOWLER-FIG": 
					case "PLAYERNAME_ICC":case "REVIEW": case "TARGETFULL_ICC":case "EQUATIONSHORT_ICC":case "ICC_IMAGE4_3":case "ICC_IMAGE16_9":case "LINEUPLONG_ICC":
					case "ICC_WAGON": case "ICC_WICKET":case "SIX_DISTANCE":case "INFO":case "ICC_INTRO-STATS":case "EQUATION_IMG":case"MATCHID_IMG": case "GROUP_ICC": 
					case "DLS":case "H2H_ICC":case "PHASESCORE_ICC": case "IMG_LINE2FREE_ICC":case "HAT_TRICK_BALL":case "HAT_TRICK": case "BATSMAN_STYLE": case "BOWLER_STYLE":
					case "LTPLAYERPROFILEBAT": case "L3PLAYERPROFILE": case "OUT_NOT_DECISION": case "SPEED": case "MATCHID_WITH_IMG_ICC": case "PP1": case "PP2": case "PP3":
					case "RESULTS": case "PHASE_COMPARISON_ICC":
						
						if(!which_graphics_onscreen.equalsIgnoreCase("LOGO")) {
							processAnimation("FRONT", print_writer, "LogoAnimation", "SHOW 0.0");
							processAnimation("FRONT", print_writer, "LogoAnimation", "START");
						}
						
						populateLogo(print_writer, 2);
						ChangeOn(print_writer, "LOGO");
						TimeUnit.MILLISECONDS.sleep(1500);
						processAnimation("FRONT", print_writer, "anim_BigScreen$In_Out$Main", "SHOW 0.0");
						which_side = 1;
						populateLogo(print_writer, 1);
						CutBack(print_writer, "LOGO");
						
						which_graphics_onscreen = "LOGO";
						break;
				}
			}
		}
		return null;
}
	
	public void processAnimation(String whichLayer, List<PrintWriter> print_writers,String animationDirectorName, String animationCommand)
	{
		if(!whichLayer.isEmpty()) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*" + whichLayer + "_LAYER*STAGE*DIRECTOR*"
				+ animationDirectorName + " " + animationCommand +"\0", print_writers);
		} else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*STAGE*DIRECTOR*"
				+ animationDirectorName + " " + animationCommand +"\0", print_writers);
		}
	}
	
	public void populateLogo(List<PrintWriter> print_writers, int which_side) throws InterruptedException
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$TopLogo$Side" + which_side + "$Select_TopLogo"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side1*ACTIVE SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side2*ACTIVE SET 1\0", print_writers);
		
	}
	public void AnimateLogo(List<PrintWriter> print_writers, int which_side) throws InterruptedException
	{
		populateLogo(print_writers, which_side);
		
		processAnimation("FRONT", print_writers, "Loop", "SHOW 0.0");
		processAnimation("FRONT", print_writers, "LogoAnimation", "SHOW 0.0");
		processAnimation("FRONT", print_writers, "anim_BigScreen", "SHOW 0.0");
		
		TimeUnit.MILLISECONDS.sleep(1000);
		
		processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out", "START");
		processAnimation("FRONT", print_writers, "Loop", "START");
		processAnimation("FRONT", print_writers, "LogoAnimation", "START");
		
		which_graphics_onscreen = "LOGO";
	}
	
	public void Previews(List<PrintWriter> print_writers, String whatToProcess) throws InterruptedException
	{
		System.out.println("whatToProcess" + " "+ whatToProcess);
		System.out.println("which_graphics_onscreen" + " " + which_graphics_onscreen);
		String previewCommand = "";
		
		if(which_side == 1) {
			
		}else if(which_side == 2) {
			previewCommand = "anim_BigScreen$Change 1.000 anim_BigScreen$Change$Change_Out 1.000 anim_BigScreen$Change$Change_Out$Essentials$Out 0.400 "
					+ "anim_BigScreen$Change$Change_Out$TopLogo$Out 0.400 anim_BigScreen$Change$Change_Out$Header$Out 0.400 anim_BigScreen$Change$Change_Out$Main$Out 0.400 "
					+ "anim_BigScreen$Change$Change_Out$Footer$Out 0.400 anim_BigScreen$Change$Change_Out$Wipe$Out 1.000 anim_BigScreen$Change$Change_In 1.000 "
					+ "anim_BigScreen$Change$Change_In$Essentials$In 0.400 anim_BigScreen$Change$Change_In$Header$In 0.400 anim_BigScreen$Change$Change_In$TopLogo$In 0.400 "
					+ "anim_BigScreen$Change$Change_In$Main$In 0.400 anim_BigScreen$Change$Change_In$Footer$In 0.400";
			
//			previewCommand = "anim_Change$Header 1.720 anim_Change$Header$Header 1.720 anim_Change$Header$Header$Change_Out 0.760 "
//					+ "anim_Change$Header$Header$Change_In 1.720 anim_Change$Footer 0.900 anim_Change$Footer$Change_Out 0.500 anim_Change$Footer$Change_In 0.900";
//			
//			switch (which_graphics_onscreen) {
//			case "MATCHID_ICC":
//				previewCommand = previewCommand + " anim_Change$Ident 1.800 anim_Change$Ident$Change_Out 0.800 anim_Change$Ident$Change_In 1.800";
//				break;
//			case "LTPLAYERPROFILEBAT": case "L3PLAYERPROFILE":
//				previewCommand = previewCommand + " anim_Change$Profile 1.800 anim_Change$Profile$Change_Out 0.800 anim_Change$Profile$Change_In 1.800";
//				break;	
//			case "LINEUP_ICC":
//				if(!whatToProcess.contains(which_graphics_onscreen)) {
//					previewCommand = previewCommand + " anim_Change$Team 1.800 anim_Change$Team$Change_Out 0.800 anim_Change$Team$Change_In 1.900";
//				}
//				break;
//			case "EXTRAS_ICC":
//				previewCommand = previewCommand + " anim_Change$Extras 1.800 anim_Change$Extras$Change_Out 0.800 anim_Change$Extras$Change_In 1.800";
//				break;
//			case "INFO":
//				previewCommand = previewCommand + " anim_Change$InningsSummary 1.800 anim_Change$InningsSummary$Change_Out 0.800 anim_Change$InningsSummary$Change_In 1.800";
//				break;
//			case "LINEUPLONG_ICC": case "LONGLINEUP_ICC":
//				previewCommand = previewCommand + " anim_Change$ScoreCard 2.320 anim_Change$ScoreCard$Change_Out 0.800 anim_Change$ScoreCard$Change_In 2.300";
//				break;
//			case "DECISION":
//				previewCommand = previewCommand + " anim_Change$DecisionPending 1.600 anim_Change$DecisionPending$Change_Out 0.600 anim_Change$DecisionPending$Change_In 1.600";
//				break;
//			case "OUT_NOT_DECISION":
//				previewCommand = previewCommand + " anim_Change$Decision 1.800 anim_Change$Decision$Change_Out 0.600 anim_Change$Decision$Change_In 1.800";
//				break;
//			case "DLS":
//				previewCommand = previewCommand + " anim_Change$DLS_ParScore 1.800 anim_Change$DLS_ParScore$Change_Out 0.800 anim_Change$DLS_ParScore$Change_In 1.800";
//				break;
//			case "MILESTONE_ICC":
//				previewCommand = previewCommand + " anim_Change$Milestone 1.800 anim_Change$Milestone$Change_Out 0.600 anim_Change$Milestone$Change_In 1.800";
//				break;
//			case "LOGO":
//				previewCommand = previewCommand + " anim_Change$Logo 1.800 anim_Change$Logo$Change_Out 0.600 anim_Change$Logo$Change_In 1.800";
//				break;
//			case "EQUATION_ICC": case "TARGETFULL_ICC":
//				previewCommand = previewCommand + " anim_Change$Equation 1.800 anim_Change$Equation$Change_Out 0.800 anim_Change$Equation$Change_In 1.800";
//				break;
//			case "TARGET_IMG": case "EQUATION_IMG":
//				previewCommand = previewCommand + " anim_Change$Target 1.800 anim_Change$Target$Change_Out 0.800 anim_Change$Target$Change_In 1.800";
//				break;
//			case "ICC_TEAM-BOUNDARY":
//				previewCommand = previewCommand + " anim_Change$Boundaries 1.800 anim_Change$Boundaries$Change_Out 0.800 anim_Change$Boundaries$Change_In 1.800";
//				break;
//			case "ICC_QUICKHOWOUT":  case "MATCH_WICKET":
//				previewCommand = previewCommand + " anim_Change$BatsmanOut 1.800 anim_Change$BatsmanOut$Change_Out 0.800 anim_Change$BatsmanOut$Change_In 1.800";
//				break;
//			case "ICC_BATSMAN-STATS": case "ICC_BOWLER-STATS":
//				previewCommand = previewCommand + " anim_Change$PlayerStat 1.800 anim_Change$PlayerStat$Change_Out 0.800 anim_Change$PlayerStat$Change_In 1.800";
//				break;
//			case "ICC_MATCHSUMMARY":
//				previewCommand = previewCommand + " anim_Change$InningsSummary 1.800 anim_Change$InningsSummary$Change_Out 0.800 anim_Change$InningsSummary$Change_In 1.800";
//				break;
//			case "FOUR_ICC": case "SIX_ICC": case "ICC_WICKET": case "ICC_WIDE": case "ICC_DUCK": 
//			case "HAT_TRICK_BALL": case "HAT_TRICK": case "FREEHIT_ICC":
//				previewCommand = previewCommand + " anim_Change$Hattrick 1.800 anim_Change$Hattrick$Change_Out 0.800 anim_Change$Hattrick$Change_In 1.800";
//				break;
//			case "IMG_LINE2FREE_ICC": case "LINE2FREE_ICC": case "FREETEXT_ICC":
//				previewCommand = previewCommand + " anim_Change$FreeText 1.800 anim_Change$FreeText$Change_Out 0.800 anim_Change$FreeText$Change_In 1.800";
//				break;
//			case "MATCHID_WITH_IMG_ICC":
//				previewCommand = previewCommand + " anim_Change$IdentImage 1.800 anim_Change$IdentImage$Change_Out 0.800 anim_Change$IdentImage$Change_In 1.800";
//				break;	
//			case "TEAMNAME_ICC":
//				previewCommand = previewCommand + " anim_Change$TeamName 1.800 anim_Change$TeamName$Change_Out 0.800 anim_Change$TeamName$Change_In 1.800";
//				break;
//			case "TOSS_ICC":
//				previewCommand = previewCommand + " anim_Change$Toss 1.800 anim_Change$Toss$Change_Out 0.800 anim_Change$Toss$Change_In 1.800";
//				break;
//			case "SIX_DISTANCE":
//				previewCommand = previewCommand + " anim_Change$SixDistance 1.800 anim_Change$SixDistance$Change_Out 0.800 anim_Change$SixDistance$Change_In 1.800";
//				break;
//			case "RUNRATE_ICC":
//				previewCommand = previewCommand + " anim_Change$RunRates 1.800 anim_Change$RunRates$Change_Out 0.800 anim_Change$RunRates$Change_In 1.800";
//				break;
//			case "COMPARISON_ICC":
//				previewCommand = previewCommand + " anim_Change$Comparison 1.800 anim_Change$Comparison$Change_Out 0.800 anim_Change$Comparison$Change_In 1.800";
//				break;
//			case "PROJECTED_BS":
//				previewCommand = previewCommand + " anim_Change$ProjectedScore 1.800 anim_Change$ProjectedScore$Change_Out 0.800 anim_Change$ProjectedScore$Change_In 1.800";
//				break;
//			case "PP1": case "PP2": case "PP3":
//				previewCommand = previewCommand + " anim_Change$PowerPlayField 1.800 anim_Change$PowerPlayField$Change_Out 0.800 anim_Change$PowerPlayField$Change_In 1.800";
//				break;
//			case "THIS_OVER_ICC":
//				previewCommand = previewCommand + " anim_Change$ThisOver 1.800 anim_Change$ThisOver$Change_Out 0.800 anim_Change$ThisOver$Change_In 1.800";
//				break;
//			case "WEATHER_ICC":
//				previewCommand = previewCommand + " anim_Change$Weather 1.800 anim_Change$Weather$Change_Out 0.800 anim_Change$Weather$Change_In 1.800";
//				break;
//			case "EQUATIONSHORT_ICC": case "TARGET_ICC":
//				previewCommand = previewCommand + " anim_Change$BigEquation 1.800 anim_Change$BigEquation$Change_Out 0.800 anim_Change$BigEquation$Change_In 1.800";
//				break;
//			case "PHASESCORE_ICC":
//				previewCommand = previewCommand + " anim_Change$PhaseScore 1.800 anim_Change$PhaseScore$Change_Out 0.800 anim_Change$PhaseScore$Change_In 1.800";
//				break;
//			case "BATSMAN_STYLE": case "BOWLER_STYLE":
//				previewCommand = previewCommand + " anim_Change$PlayerStyle 1.800 anim_Change$PlayerStyle$Change_Out 0.800 anim_Change$PlayerStyle$Change_In 1.800";
//				break;
//			case "POINTSTABLE":
//				previewCommand = previewCommand + " anim_Change$Standings 1.800 anim_Change$Standings$Change_Out 0.800 anim_Change$Standings$Change_In 1.800";
//				break;
//			case "RESULTS":
//				previewCommand = previewCommand + " anim_Change$Results 1.800 anim_Change$Results$Change_Out 0.800 anim_Change$Results$Change_In 1.800";
//				break;
//			case "LINEUPIMAGE_ICC":
//				previewCommand = previewCommand + " anim_Change$LineUp 2.420 anim_Change$LineUp$Change_Out 0.680 anim_Change$LineUp$Change_In 2.420";
//				break;
//			}
//			
//			switch (whatToProcess) {
//			case "POPULATE-MATCHID_ICC":
//				previewCommand = previewCommand + " anim_Change$Ident 1.800 anim_Change$Ident$Change_Out 0.800 anim_Change$Ident$Change_In 1.800";
//				break;
//			case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-L3-PLAYERPROFILE":
//				if(!which_graphics_onscreen.equalsIgnoreCase("LTPLAYERPROFILEBAT") && !which_graphics_onscreen.equalsIgnoreCase("L3PLAYERPROFILE")){
//					previewCommand = previewCommand + " anim_Change$Profile 1.800 anim_Change$Profile$Change_Out 0.800 anim_Change$Profile$Change_In 1.800";
//				}
//				break;	
//			case "POPULATE-LINEUP_ICC":
//				previewCommand = previewCommand + " anim_Change$Team 1.800 anim_Change$Team$Change_Out 0.800 anim_Change$Team$Change_In 1.900";
//				break;
//			case "POPULATE-EXTRAS_ICC":
//				previewCommand = previewCommand + " anim_Change$Extras 1.800 anim_Change$Extras$Change_Out 0.800 anim_Change$Extras$Change_In 1.800";
//				break;
//			case "POPULATE-INFOBAR_ICC":
//				previewCommand = previewCommand + " anim_Change$InningsSummary 1.800 anim_Change$InningsSummary$Change_Out 0.800 anim_Change$InningsSummary$Change_In 1.800";
//				break;
//			case "POPULATE-LINEUPLONG_ICC": case "POPULATE-LONGLINEUP_ICC":
//				previewCommand = previewCommand + " anim_Change$ScoreCard 2.320 anim_Change$ScoreCard$Change_Out 0.800 anim_Change$ScoreCard$Change_In 2.320";
//				break;
//			case "POPULATE-DECISION":
//				previewCommand = previewCommand + " anim_Change$DecisionPending 1.600 anim_Change$DecisionPending$Change_Out 0.600 anim_Change$DecisionPending$Change_In 1.600";
//				break;
//			case "POPULATE-OUT_NOT_DECISION":
//				previewCommand = previewCommand + " anim_Change$Decision 1.800 anim_Change$Decision$Change_Out 0.600 anim_Change$Decision$Change_In 1.800";
//				break;
//			case "POPULATE-DLS":
//				previewCommand = previewCommand + " anim_Change$DLS_ParScore 1.800 anim_Change$DLS_ParScore$Change_Out 0.800 anim_Change$DLS_ParScore$Change_In 1.800";
//				break;
//			case "POPULATE-MILESTONE_ICC":
//				previewCommand = previewCommand + " anim_Change$Milestone 1.800 anim_Change$Milestone$Change_Out 0.600 anim_Change$Milestone$Change_In 1.800";
//				break;
//			case "POPULATE-EQUATION_ICC": case "POPULATE-TARGETFULL_ICC":
//				previewCommand = previewCommand + " anim_Change$Equation 1.800 anim_Change$Equation$Change_Out 0.800 anim_Change$Equation$Change_In 1.800";
//				break;
//			case "POPULATE-TARGET_WITH_IMG_ICC": case "POPULATE-EQUATION_WITH_IMG_ICC":
//				previewCommand = previewCommand + " anim_Change$Target 1.800 anim_Change$Target$Change_Out 0.800 anim_Change$Target$Change_In 1.800";
//				break;
//			case "POPULATE-ICC_TEAM-BOUNDARY":
//				previewCommand = previewCommand + " anim_Change$Boundaries 1.800 anim_Change$Boundaries$Change_Out 0.800 anim_Change$Boundaries$Change_In 1.800";
//				break;
//			case "POPULATE-ICC_MATCHSUMMARY":
//				previewCommand = previewCommand + " anim_Change$InningsSummary 1.800 anim_Change$InningsSummary$Change_Out 0.800 anim_Change$InningsSummary$Change_In 1.800";
//				break;
//			case "POPULATE-BS_HOWOUT": case "POPULATE-ICC_QUICKHOWOUT":
//				previewCommand = previewCommand + " anim_Change$BatsmanOut 1.800 anim_Change$BatsmanOut$Change_Out 0.800 anim_Change$BatsmanOut$Change_In 1.800";
//				break;
//			case "POPULATE-ICC_BATSMAN-STATS": case "POPULATE-ICC_BOWLER-STATS":
//				previewCommand = previewCommand + " anim_Change$PlayerStat 1.800 anim_Change$PlayerStat$Change_Out 0.800 anim_Change$PlayerStat$Change_In 1.800";
//				break;
//			case "POPULATE-FOUR_ICC": case "POPULATE-WIDE_ICC": case "POPULATE-DUCK_ICC": case "POPULATE-WICKET_ICC":
//			case "POPULATE-SIX_ICC": case "POPULATE-HAT_TRICK":case "POPULATE-HAT_TRICK_BALL": case "POPULATE-FREEHIT_ICC":
//				previewCommand = previewCommand + " anim_Change$Hattrick 1.800 anim_Change$Hattrick$Change_Out 0.800 anim_Change$Hattrick$Change_In 1.800";
//				break;
//			case "POPULATE-LINE2FREE_ICC": case "POPULATE-IMG_LINE2FREE_ICC": case "POPULATE-FREETEXT_ICC":
//				previewCommand = previewCommand + " anim_Change$FreeText 1.800 anim_Change$FreeText$Change_Out 0.800 anim_Change$FreeText$Change_In 1.800";
//				break;
//			case"POPULATE-MATCHID_WITH_IMG_ICC":
//				previewCommand = previewCommand + " anim_Change$IdentImage 1.800 anim_Change$IdentImage$Change_Out 0.800 anim_Change$IdentImage$Change_In 1.800";
//				break;
//			case"POPULATE-SIX_DISTANCE_ICC":
//				previewCommand = previewCommand + " anim_Change$SixDistance 1.800 anim_Change$SixDistance$Change_Out 0.800 anim_Change$SixDistance$Change_In 1.800";
//				break;
//			case"POPULATE-TEAMNAME_ICC":
//				previewCommand = previewCommand + " anim_Change$TeamName 1.800 anim_Change$TeamName$Change_Out 0.800 anim_Change$TeamName$Change_In 1.800";
//				break;
//			case"POPULATE-TOSS_ICC":
//				previewCommand = previewCommand + " anim_Change$Toss 1.800 anim_Change$Toss$Change_Out 0.800 anim_Change$Toss$Change_In 1.800";
//				break;
//			case"POPULATE-RUNRATE_ICC":
//				previewCommand = previewCommand + " anim_Change$RunRates 1.800 anim_Change$RunRates$Change_Out 0.800 anim_Change$RunRates$Change_In 1.800";
//				break;
//			case"POPULATE-COMPARISON_ICC":
//				previewCommand = previewCommand + " anim_Change$Comparison 1.800 anim_Change$Comparison$Change_Out 0.800 anim_Change$Comparison$Change_In 1.800";
//				break;
//			case"POPULATE-PROJECTED_BS":
//				previewCommand = previewCommand + " anim_Change$ProjectedScore 1.800 anim_Change$ProjectedScore$Change_Out 0.800 anim_Change$ProjectedScore$Change_In 1.800";
//				break;
//			case "POPULATE_ICC_PP1": case "POPULATE_ICC_PP2": case "POPULATE_ICC_PP3":
//				previewCommand = previewCommand + " anim_Change$PowerPlayField 1.800 anim_Change$PowerPlayField$Change_Out 0.800 anim_Change$PowerPlayField$Change_In 1.800";
//				break;
//			case "POPULATE-THIS_OVER_ICC":
//				previewCommand = previewCommand + " anim_Change$ThisOver 1.800 anim_Change$ThisOver$Change_Out 0.800 anim_Change$ThisOver$Change_In 1.800";
//				break;
//			case "POPULATE-WEATHER_ICC":
//				previewCommand = previewCommand + " anim_Change$Weather 1.800 anim_Change$Weather$Change_Out 0.800 anim_Change$Weather$Change_In 1.800";
//				break;
//			case "POPULATE-EQUATIONSHORT_ICC": case "POPULATE-TARGET_ICC":
//				previewCommand = previewCommand + " anim_Change$BigEquation 1.800 anim_Change$BigEquation$Change_Out 0.800 anim_Change$BigEquation$Change_In 1.800";
//				break;
//			case "POPULATE-PHASESCORE_ICC":
//				previewCommand = previewCommand + " anim_Change$PhaseScore 1.800 anim_Change$PhaseScore$Change_Out 0.800 anim_Change$PhaseScore$Change_In 1.800";
//				break;
//			case "POPULATE-BATSMAN_STYLE": case "POPULATE-BOWLER_STYLE":
//				previewCommand = previewCommand + " anim_Change$PlayerStyle 1.800 anim_Change$PlayerStyle$Change_Out 0.800 anim_Change$PlayerStyle$Change_In 1.800";
//				break;
//			case "POPULATE-BUKH-POINTSTABLE":
//				previewCommand = previewCommand + " anim_Change$Standings 1.800 anim_Change$Standings$Change_Out 0.800 anim_Change$Standings$Change_In 1.800";
//				break;
//			case "POPULATE-RESULTS":
//				previewCommand = previewCommand + " anim_Change$Results 1.800 anim_Change$Results$Change_Out 0.800 anim_Change$Results$Change_In 1.800";
//				break;
//			case "POPULATE-LINEUPIMAGE_ICC":
//				previewCommand = previewCommand + " anim_Change$LineUp 2.420 anim_Change$LineUp$Change_Out 0.680 anim_Change$LineUp$Change_In 2.420";
//				break;
//			}
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/gfx_BigScreen " + "C:/Temp/Preview.tga " + previewCommand + "\0", print_writers);
			
		}
	}
	
	public void ChangeOn(List<PrintWriter> print_writers, String whatToProcess) throws InterruptedException
	{
		//processAnimation("FRONT", print_writers, "anim_Change$Header", "START");
		//processAnimation("FRONT", print_writers, "anim_Change$Footer", "START");
		//processAnimation("FRONT", print_writers, "Loop", "START");
		
		processAnimation("FRONT", print_writers, "anim_BigScreen$Change", "START");
		
//		switch (which_graphics_onscreen) {
//		case "LOGO":
//			processAnimation("FRONT", print_writers, "anim_Change$Logo", "START");
//			break;
//		case "MATCHID_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Ident", "START");
//			break;
//		case "LTPLAYERPROFILEBAT": case "L3PLAYERPROFILE":
//			processAnimation("FRONT", print_writers, "anim_Change$Profile", "START");
//			break;	
//		case "LINEUP_ICC":
//			if(!whatToProcess.contains(which_graphics_onscreen)) {
//				processAnimation("FRONT", print_writers, "anim_Change$Team", "START");
//			}
//			break;
//		case "EXTRAS_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Extras", "START");
//			break;
//		case "INFO":
//			processAnimation("FRONT", print_writers, "anim_Change$InningsSummary", "START");
//			break;
//		case "LINEUPLONG_ICC": case "LONGLINEUP_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$ScoreCard", "START");
//			break;
//		case "DECISION":
//			processAnimation("FRONT", print_writers, "anim_Change$DecisionPending", "START");
//			break;
//		case "OUT_NOT_DECISION":
//			processAnimation("FRONT", print_writers, "anim_Change$Decision", "START");
//			break;
//		case "DLS":
//			processAnimation("FRONT", print_writers, "anim_Change$DLS_ParScore", "START");
//			break;
//		case "MILESTONE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Milestone", "START");
//			break;
//		case "ICC_TEAM-BOUNDARY":
//			processAnimation("FRONT", print_writers, "anim_Change$Boundaries", "START");
//			break;
//		case "SPEED":
//			processAnimation("FRONT", print_writers, "anim_Change$BallSpeed", "START");
//			break;
//		case "TARGET_IMG": case "EQUATION_IMG":
//			processAnimation("FRONT", print_writers, "anim_Change$Target", "START");
//			break;
//		case "EQUATION_ICC": case "TARGETFULL_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Equation", "START");
//			break;
//		case "ICC_BATSMAN-STATS": case "ICC_BOWLER-STATS":
//			processAnimation("FRONT", print_writers, "anim_Change$PlayerStat", "START");
//			break;
//		case "MATCH_WICKET": case "ICC_QUICKHOWOUT":
//			processAnimation("FRONT", print_writers, "anim_Change$BatsmanOut", "START");
//			break;
//		case "ICC_MATCHSUMMARY":
//			processAnimation("FRONT", print_writers, "anim_Change$MatchSummary", "START");
//			break;
//		case "FOUR_ICC": case "SIX_ICC": case "ICC_WICKET": case "ICC_WIDE": case "ICC_DUCK": 
//		case "HAT_TRICK_BALL": case "HAT_TRICK": case "FREEHIT_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Hattrick", "START");
//			break;
//		case "IMG_LINE2FREE_ICC": case "LINE2FREE_ICC": case "FREETEXT_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$FreeText", "START");
//			break;
//		case "MATCHID_WITH_IMG_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$IdentImage", "START");
//			break;
//		case "REVIEW":
//			processAnimation("FRONT", print_writers, "anim_Change$Reviews", "START");
//			break;
//		case "PARTNERSHIP_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Partnership", "START");
//			break;
//		case "TEAMNAME_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$TeamName", "START");
//			break;
//		case "TOSS_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Toss", "START");
//			break;
//		case "SIX_DISTANCE":
//			processAnimation("FRONT", print_writers, "anim_Change$SixDistance", "START");
//			break;
//		case "RUNRATE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$RunRates", "START");
//			break;
//		case "COMPARISON_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Comparison", "START");
//			break;
//		case "PROJECTED_BS":
//			processAnimation("FRONT", print_writers, "anim_Change$ProjectedScore", "START");
//			break;
//		case "PP1": case "PP2": case "PP3":
//			processAnimation("FRONT", print_writers, "anim_Change$PowerPlayField", "START");
//			break;
//		case "THIS_OVER_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$ThisOver", "START");
//			break;
//		case "WEATHER_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Weather", "START");
//			break;
//		case "EQUATIONSHORT_ICC": case "TARGET_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$BigEquation", "START");
//			break;
//		case "PHASESCORE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$PhaseScore", "START");
//			break;
//		case "BATSMAN_STYLE": case "BOWLER_STYLE":
//			processAnimation("FRONT", print_writers, "anim_Change$PlayerStyle", "START");
//			break;
//		case "POINTSTABLE":
//			processAnimation("FRONT", print_writers, "anim_Change$Standings", "START");
//			break;
//		case "RESULTS":
//			processAnimation("FRONT", print_writers, "anim_Change$Results", "START");
//			break;
//		case "LINEUPIMAGE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$LineUp", "START");
//			break;
//		}
		
//		switch (whatToProcess) {
//		case "ANIMATE-IN-MATCHID_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Ident", "START");
//			break;
//		case "ANIMATE-IN-LTPLAYERPROFILEBAT": case "ANIMATE-IN-L3PLAYERPROFILE":
//			processAnimation("FRONT", print_writers, "anim_Change$Profile", "START");
//			break;	
//		case "ANIMATE-IN-LINEUP_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Team", "START");
//			break;
//		case "ANIMATE-IN-EXTRAS_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Extras", "START");
//			break;
//		case "ANIMATE-IN-INFOBAR_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$InningsSummary", "START");
//			break;
//		case "ANIMATE-IN-LINEUPLONG_ICC": case "ANIMATE-IN-LONGLINEUP_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$ScoreCard", "START");
//			break;
//		case "ANIMATE-IN-DECISION":
//			processAnimation("FRONT", print_writers, "anim_Change$DecisionPending", "START");
//			break;
//		case "ANIMATE-IN-OUT_NOT_DECISION":
//			processAnimation("FRONT", print_writers, "anim_Change$Decision", "START");
//			break;
//		case "ANIMATE-IN-DLS":
//			processAnimation("FRONT", print_writers, "anim_Change$DLS_ParScore", "START");
//			break;
//		case "ANIMATE-IN-MILESTONE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Milestone", "START");
//			break;
//		case "ANIMATE-IN-ICC_TEAM-BOUNDARY":
//			processAnimation("FRONT", print_writers, "anim_Change$Boundaries", "START");
//			break;
//		case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
//			processAnimation("FRONT", print_writers, "anim_Change$BallSpeed", "START");
//			break;
//		case "ANIMATE-IN-TARGET_WITH_IMG_ICC": case "ANIMATE-IN-EQUATION_WITH_IMG_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Target", "START");
//			break;
//		case "ANIMATE-IN-EQUATION_ICC": case "ANIMATE-IN-TARGETFULL_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Equation", "START");
//			break;
//		case "ANIMATE-IN-ICC_BATSMAN-STATS": case "ANIMATE-IN-ICC_BOWLER-STATS":
//			processAnimation("FRONT", print_writers, "anim_Change$PlayerStat", "START");
//			break;
//		case "ANIMATE-IN-WICKET": case "ANIMATE-IN-ICC_QUICKHOWOUT":
//			processAnimation("FRONT", print_writers, "anim_Change$BatsmanOut", "START");
//			break;
//		case "ANIMATE-IN-ICC_MATCHSUMMARY":
//			processAnimation("FRONT", print_writers, "anim_Change$MatchSummary", "START");
//			break;
//		case "ANIMATE-IN-FOUR_ICC": case "ANIMATE-IN-SIX_ICC": case "ANIMATE-IN-ICC_WICKET": case "ANIMATE-IN-ICC_WIDE": case "ANIMATE-IN-ICC_DUCK": 
//		case "ANIMATE-HAT_TRICK_BALL": case "ANIMATE-HAT_TRICK": case "ANIMATE-IN-FREEHIT_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Hattrick", "START");
//			break;
//		case "ANIMATE-IN-IMG_LINE2FREE_ICC": case "ANIMATE-IN-LINE2FREE_ICC": case "ANIMATE-IN-FREETEXT_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$FreeText", "START");
//			break;
//		case "ANIMATE-IN-MATCHID_WITH_IMG_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$IdentImage", "START");
//			break;
//		case "ANIMATE-IN-REVIEW":
//			processAnimation("FRONT", print_writers, "anim_Change$Reviews", "START");
//			break;
//		case "ANIMATE-IN-PARTNERSHIP_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Partnership", "START");
//			break;
//		case "ANIMATE-IN-TEAMNAME_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$TeamName", "START");
//			break;
//		case "ANIMATE-IN-TOSS_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Toss", "START");
//			break;
//		case "ANIMATE-IN-SIX_DISTANCE":
//			processAnimation("FRONT", print_writers, "anim_Change$SixDistance", "START");
//			break;
//		case "ANIMATE-IN-RUNRATE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$RunRates", "START");
//			break;
//		case "ANIMATE-IN-COMPARISON_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Comparison", "START");
//			break;
//		case "ANIMATE-IN-PROJECTED_BS":
//			processAnimation("FRONT", print_writers, "anim_Change$ProjectedScore", "START");
//			break;
//		case "ANIMATE-PP1": case "ANIMATE-PP2": case "ANIMATE-PP3":
//			processAnimation("FRONT", print_writers, "anim_Change$PowerPlayField", "START");
//			break;
//		case "ANIMATE-IN-THIS_OVER_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$ThisOver", "START");
//			break;
//		case "ANIMATE-IN-WEATHER_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Weather", "START");
//			break;
//		case "ANIMATE-IN-EQUATIONSHORT_ICC": case "ANIMATE-IN-TARGET_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$BigEquation", "START");
//			break;
//		case "ANIMATE-IN-PHASESCORE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$PhaseScore", "START");
//			break;
//		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-BOWLER_STYLE":
//			processAnimation("FRONT", print_writers, "anim_Change$PlayerStyle", "START");
//			break;
//		case "ANIMATE-IN-POINTSTABLE":
//			processAnimation("FRONT", print_writers, "anim_Change$Standings", "START");
//			break;
//		case "ANIMATE-IN-RESULTS":
//			processAnimation("FRONT", print_writers, "anim_Change$Results", "START");
//			break;
//		case "ANIMATE-IN-LINEUPIMAGE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$LineUp", "START");
//			break;
//		case "LOGO":
//			processAnimation("FRONT", print_writers, "anim_Change$Logo", "START");
//			break;
//		}
	}
	
	public void CutBack(List<PrintWriter> print_writers, String whatToProcess) throws InterruptedException
	{
		//processAnimation("FRONT", print_writers, "anim_Change$Header", "SHOW 0.0");
		//processAnimation("FRONT", print_writers, "anim_Change$Footer", "SHOW 0.0");
		
		processAnimation("FRONT", print_writers, "anim_BigScreen$Change", "SHOW 0.0");
		
//		switch (which_graphics_onscreen) {
//		case "LOGO":
//			processAnimation("FRONT", print_writers, "anim_Change$Logo", "SHOW 0.0");
//			break;
//		case "MATCHID_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Ident", "SHOW 0.0");
//			break;
//		case "LINEUP_ICC":
//			if(!whatToProcess.contains(which_graphics_onscreen)) {
//				processAnimation("FRONT", print_writers, "anim_Change$Team", "SHOW 0.0");
//			}
//			break;
//		case "EXTRAS_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Extras", "SHOW 0.0");
//			break;
//		case "INFO":
//			processAnimation("FRONT", print_writers, "anim_Change$InningsSummary", "SHOW 0.0");
//			break;
//		case "LINEUPLONG_ICC": case "LONGLINEUP_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$ScoreCard", "SHOW 0.0");
//			break;
//		case "LTPLAYERPROFILEBAT": case "L3PLAYERPROFILE":
//			processAnimation("FRONT", print_writers, "anim_Change$Profile", "SHOW 0.0");
//			break;
//		case "DECISION":
//			processAnimation("FRONT", print_writers, "anim_Change$DecisionPending", "SHOW 0.0");
//			break;
//		case "OUT_NOT_DECISION":
//			processAnimation("FRONT", print_writers, "anim_Change$Decision", "SHOW 0.0");
//			break;
//		case "DLS":
//			processAnimation("FRONT", print_writers, "anim_Change$DLS_ParScore", "SHOW 0.0");
//			break;
//		case "MILESTONE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Milestone", "SHOW 0.0");
//			break;
//		case "ICC_TEAM-BOUNDARY":
//			processAnimation("FRONT", print_writers, "anim_Change$Boundaries", "SHOW 0.0");
//			break;
//		case "SPEED":
//			processAnimation("FRONT", print_writers, "anim_Change$BallSpeed", "SHOW 0.0");
//			break;
//		case "TARGET_IMG": case "EQUATION_IMG":
//			processAnimation("FRONT", print_writers, "anim_Change$Target", "SHOW 0.0");
//			break;
//		case "EQUATION_ICC": case "TARGETFULL_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Equation", "SHOW 0.0");
//			break;
//		case "ICC_BATSMAN-STATS": case "ICC_BOWLER-STATS":
//			processAnimation("FRONT", print_writers, "anim_Change$PlayerStat", "SHOW 0.0");
//			break;
//		case "MATCH_WICKET": case "ICC_QUICKHOWOUT":
//			processAnimation("FRONT", print_writers, "anim_Change$BatsmanOut", "SHOW 0.0");
//			break;
//		case "ICC_MATCHSUMMARY":
//			processAnimation("FRONT", print_writers, "anim_Change$MatchSummary", "SHOW 0.0");
//			break;
//		case "FOUR_ICC": case "SIX_ICC": case "ICC_WICKET": case "ICC_WIDE": case "ICC_DUCK": 
//		case "HAT_TRICK_BALL": case "HAT_TRICK": case "FREEHIT_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Hattrick", "SHOW 0.0");
//			break;
//		case "IMG_LINE2FREE_ICC": case "LINE2FREE_ICC": case "FREETEXT_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$FreeText", "SHOW 0.0");
//			break;
//		case "MATCHID_WITH_IMG_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$IdentImage", "SHOW 0.0");
//			break;
//		case "REVIEW":
//			processAnimation("FRONT", print_writers, "anim_Change$Reviews", "SHOW 0.0");
//			break;
//		case "PARTNERSHIP_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Partnership", "SHOW 0.0");
//			break;
//		case "TEAMNAME_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$TeamName", "SHOW 0.0");
//			break;
//		case "TOSS_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Toss", "SHOW 0.0");
//			break;
//		case "SIX_DISTANCE":
//			processAnimation("FRONT", print_writers, "anim_Change$SixDistance", "SHOW 0.0");
//			break;
//		case "RUNRATE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$RunRates", "SHOW 0.0");
//			break;
//		case "COMPARISON_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Comparison", "SHOW 0.0");
//			break;
//		case "PROJECTED_BS":
//			processAnimation("FRONT", print_writers, "anim_Change$ProjectedScore", "SHOW 0.0");
//			break;
//		case "PP1": case "PP2": case "PP3":
//			processAnimation("FRONT", print_writers, "anim_Change$PowerPlayField", "SHOW 0.0");
//			break;
//		case "THIS_OVER_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$ThisOver", "SHOW 0.0");
//			break;
//		case "WEATHER_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$Weather", "SHOW 0.0");
//			break;
//		case "EQUATIONSHORT_ICC": case "TARGET_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$BigEquation", "SHOW 0.0");
//			break;
//		case "PHASESCORE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$PhaseScore", "SHOW 0.0");
//			break;
//		case "BATSMAN_STYLE": case "BOWLER_STYLE":
//			processAnimation("FRONT", print_writers, "anim_Change$PlayerStyle", "SHOW 0.0");
//			break;
//		case "POINTSTABLE":
//			processAnimation("FRONT", print_writers, "anim_Change$Standings", "SHOW 0.0");
//			break;
//		case "RESULTS":
//			processAnimation("FRONT", print_writers, "anim_Change$Results", "SHOW 0.0");
//			break;
//		case "LINEUPIMAGE_ICC":
//			processAnimation("FRONT", print_writers, "anim_Change$LineUp", "SHOW 0.0");
//			break;
//		}
		
//		switch (whatToProcess) {
//		case "ANIMATE-IN-MATCHID_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Ident", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Ident", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-LTPLAYERPROFILEBAT": case "ANIMATE-IN-L3PLAYERPROFILE":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Profile", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Profile$In", "SHOW 1.800");
//			processAnimation("FRONT", print_writers, "anim_Change$Profile", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-LINEUP_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Team", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Team", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-EXTRAS_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Extras", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Extras", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-INFOBAR_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$InningsSummary", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$InningsSummary", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-LINEUPLONG_ICC": case "ANIMATE-IN-LONGLINEUP_ICC": 
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$ScoreCard", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$ScoreCard", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-DECISION":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$DecisionPending", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$DecisionPending", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-OUT_NOT_DECISION":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Decision", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Decision", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-DLS":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$DLS_ParScore", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$DLS_ParScore", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-MILESTONE_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Milestone", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Milestone", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-ICC_TEAM-BOUNDARY":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Boundaries", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Boundaries", "SHOW 0.0");
//			break;
//		case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$BallSpeed", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$BallSpeed", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-TARGET_WITH_IMG_ICC": case "ANIMATE-IN-EQUATION_WITH_IMG_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Target", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Target", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-EQUATION_ICC": case "ANIMATE-IN-TARGETFULL_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Equation", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Equation", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-ICC_BATSMAN-STATS": case "ANIMATE-IN-ICC_BOWLER-STATS":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$PlayerStat", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$PlayerStat", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-WICKET": case "ANIMATE-IN-ICC_QUICKHOWOUT":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$BatsmanOut", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$BatsmanOut", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-ICC_MATCHSUMMARY":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$MatchSummary", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$MatchSummary", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-FOUR_ICC": case "ANIMATE-IN-SIX_ICC": case "ANIMATE-IN-ICC_WICKET": case "ANIMATE-IN-ICC_WIDE": case "ANIMATE-IN-ICC_DUCK": 
//		case "ANIMATE-HAT_TRICK_BALL": case "ANIMATE-HAT_TRICK": case "ANIMATE-IN-FREEHIT_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Hattrick", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Hattrick", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-IMG_LINE2FREE_ICC": case "ANIMATE-IN-LINE2FREE_ICC": case "ANIMATE-IN-FREETEXT_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$FreeText", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$FreeText", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-MATCHID_WITH_IMG_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$IdentImage", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$IdentImage", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-REVIEW":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Reviews", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Reviews", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-PARTNERSHIP_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Partnership", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Partnership", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-TEAMNAME_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$TeamName", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$TeamName", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-TOSS_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Toss", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Toss", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-SIX_DISTANCE":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$SixDistance", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$SixDistance", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-RUNRATE_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$RunRates", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$RunRates", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-COMPARISON_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Comparison", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Comparison", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-PROJECTED_BS":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$ProjectedScore", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$ProjectedScore", "SHOW 0.0");
//			break;
//		case "ANIMATE-PP1": case "ANIMATE-PP2": case "ANIMATE-PP3":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$PowerPlayField", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$PowerPlayField", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-THIS_OVER_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$ThisOver", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$ThisOver", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-WEATHER_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Weather", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Weather", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-EQUATIONSHORT_ICC": case "ANIMATE-IN-TARGET_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$BigEquation", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$BigEquation", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-PHASESCORE_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$PhaseScore", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$PhaseScore", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-BOWLER_STYLE":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$PlayerStyle", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$PlayerStyle", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-POINTSTABLE":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Standings", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Standings", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-RESULTS":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Results", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Results", "SHOW 0.0");
//			break;
//		case "ANIMATE-IN-LINEUPIMAGE_ICC":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$LineUp", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$LineUp", "SHOW 0.0");
//			break;
//		case "LOGO":
//			processAnimation("FRONT", print_writers, "anim_BigScreen$In_Out$Main$Logo", "SHOW 2.500");
//			processAnimation("FRONT", print_writers, "anim_Change$Logo", "SHOW 0.0");
//			break;
//		}
	}
	
	public static Statistics updateTournamentDataWithStats(Statistics stat,List<MatchAllData> tournament_matches,MatchAllData currentMatch) 
	{
		boolean player_found = false;
		for(MatchAllData match : tournament_matches) {
			if(!match.getMatch().getMatchFileName().equalsIgnoreCase(currentMatch.getMatch().getMatchFileName())) {
				if(stat.getStats_type().getStatsShortName().equalsIgnoreCase("PR")) {
					for(Inning inn : match.getMatch().getInning()) {
						for(BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == stat.getPlayerID()) {
								player_found = true;
								if(bc.getBatsmanInningStarted() != null && bc.getBatsmanInningStarted().equalsIgnoreCase(CricketUtil.YES)) {
									stat.setInnings(stat.getInnings() + 1);
								}
								stat.setRuns(stat.getRuns() + bc.getRuns());
								stat.setFours(stat.getFours() + bc.getFours());
								stat.setSixes(stat.getSixes() + bc.getSixes());
								stat.setBallsFaced(stat.getBallsFaced() + bc.getBalls());
								
								if(bc.getRuns() < 50 && bc.getRuns() >= 30) {
									stat.setThirties(stat.getThirties() + 1);
								}else if(bc.getRuns() < 100 && bc.getRuns() >= 50) {
									stat.setFifties(stat.getFifties() + 1);
								}else if(bc.getRuns() >= 100){
									stat.setHundreds(stat.getHundreds() + 1);
								}
								
							}
						}
						if(inn.getBowlingCard() != null && inn.getBowlingCard().size()>0) {
							for(BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId() == stat.getPlayerID()) {
									stat.setWickets(stat.getWickets() + boc.getWickets());
									stat.setRunsConceded(stat.getRunsConceded() + boc.getRuns());
									stat.setBallsBowled(stat.getBallsBowled() + (boc.getOvers()*6 + boc.getBalls()));
									stat.setDotBowled(stat.getDotBowled() + boc.getDots());
									if(boc.getWickets() < 5 && boc.getWickets() >= 3) {
										stat.setPlus3(stat.getPlus3() + 1);
									}	
									else if(boc.getWickets() >= 5){
										stat.setPlus5(stat.getPlus5() + 1);
									}
								}
							}							
						}
					}
					player_found = false;
					for(Player hs : match.getSetup().getHomeSquad()) {
						if(stat.getPlayerID() == hs.getPlayerId()) {
							player_found = true;
						}
					}
					for(Player as : match.getSetup().getAwaySquad()) {
						if(stat.getPlayerID() == as.getPlayerId()) {
							player_found = true;
						}
					}
					if(player_found == true){
						stat.setMatches(stat.getMatches() + 1);
					}
				}
			}
		}
		return stat;
	}
	
	public static Statistics updateStatisticsWithMatchData(Statistics stat, MatchAllData match)
	{
		boolean player_found = false;
		
		if(stat.getStats_type().getStatsShortName().equalsIgnoreCase("PR")) {
			stat.setTournament_fours(stat.getTournament_fours() + match.getMatch().getInning().get(0).getTotalFours());
			stat.setTournament_fours(stat.getTournament_fours() + match.getMatch().getInning().get(1).getTotalFours());
			for(Inning inn : match.getMatch().getInning()) {
				for(BattingCard bc : inn.getBattingCard()) {
					if(bc.getPlayerId() == stat.getPlayerID()) {
						player_found = true;
						if(bc.getBatsmanInningStarted() == null) {
						}
						else if(bc.getBatsmanInningStarted().equalsIgnoreCase(CricketUtil.YES)) {
							stat.setInnings(stat.getInnings() + 1);
						}
						
						stat.setRuns(stat.getRuns() + bc.getRuns());
						stat.setFours(stat.getFours() + bc.getFours());
						stat.setSixes(stat.getSixes() + bc.getSixes());
						stat.setBallsFaced(stat.getBallsFaced() + bc.getBalls());
				
						if(bc.getRuns() < 50 && bc.getRuns() >= 30) {
							stat.setThirties(stat.getThirties() + 1);
						}else if(bc.getRuns() < 100 && bc.getRuns() >= 50) {
							stat.setFifties(stat.getFifties() + 1);
						}else if(bc.getRuns() >= 100){
							stat.setHundreds(stat.getHundreds() + 1);
						}
					}
				}
				if(inn.getBowlingCard() != null && inn.getBowlingCard().size()>0) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getPlayerId() == stat.getPlayerID()) {
							player_found = true;
							stat.setWickets(stat.getWickets() + boc.getWickets());
							stat.setRunsConceded(stat.getRunsConceded() + boc.getRuns());
							stat.setBallsBowled(stat.getBallsBowled() + (boc.getOvers()*6 + boc.getBalls()));
							stat.setDotBowled(stat.getDotBowled() + boc.getDots());
							//System.out.println(boc.getWickets());
							if(boc.getWickets() >= 3 && boc.getWickets() < 5) {
								stat.setPlus3(stat.getPlus3() + 1);
							}else if(boc.getWickets() >= 5){
								stat.setPlus5(stat.getPlus5() + 1);
							}
						}
					}							
				}
			}
			player_found = false;
			for(Player hs : match.getSetup().getHomeSquad()) {
				if(stat.getPlayerID() == hs.getPlayerId()) {
					player_found = true;
				}
			}
			for(Player as : match.getSetup().getAwaySquad()) {
				if(stat.getPlayerID() == as.getPlayerId()) {
					player_found = true;
				}
			}
			if(player_found == true){
				stat.setMatches(stat.getMatches() + 1);
			}
		}
		return stat;
	}
	
	public Infobar populateScorebug(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					if(is_this_updating == false) {
//						System.out.println("Overs = " + match.getCurrentPosition().getCurrentOversBowled() + "  " + match.getCurrentPosition().getCurrentOddBallsBowled());
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamNameSB " + inn.getBatting_team().getTeamName4().toUpperCase() + ";");
						if(inn.getBatting_team().getTeamBadge().toUpperCase().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
								inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreSB " + inn.getTotalRuns() + ";");
					}
					else{
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreSB " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversSB " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");
					if (inn.getInningNumber() == 1) {
						if(infobar.getScorebug_last_value() != null && infobar.getScorebug_last_value() != "" && !infobar.getScorebug_last_value().isEmpty()) {
							
						}else {
							if (match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " +  
										match.getSetup().getHomeTeam().getTeamName4().toUpperCase()
										+ " WON THE TOSS & ELECTED TO "
										+ match.getSetup().getTossWinningDecision().toUpperCase() + ";");
							} else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " +  
										match.getSetup().getAwayTeam().getTeamName4().toUpperCase()
										+ " WON THE TOSS & ELECTED TO "
										+ match.getSetup().getTossWinningDecision().toUpperCase() + ";");
							}
							infobar.setScorebug_last_value("TOSS");
						}
						
					}else {
						if(infobar.getScorebug_last_value() != null && infobar.getScorebug_last_value() != "" && !infobar.getScorebug_last_value().isEmpty()) {
							
						}else {
							
							if (inn.getInningNumber() == 2
									& inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
								if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + 
											"TARGET " + CricketFunctions.GetTargetData(match).getTargetRuns() + " (VJD)" + ";");
								} else if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + 
											"TARGET " + CricketFunctions.GetTargetData(match).getTargetRuns() + " (DLS)" + ";");
								} else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + 
											"TARGET " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								}
							}
							infobar.setScorebug_last_value("TARGET");
						}
					}
				}
			}
		}
		return infobar;
	}
	
	public Infobar populateScorebugChangeOn(PrintWriter print_writer,String value,MatchAllData match, String broadcaster) throws InterruptedException, IOException 
	{
		switch (value.toUpperCase()) {
		case "TOSS":
			if (match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " +  
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase()
						+ " WON THE TOSS & ELECTED TO "
						+ match.getSetup().getTossWinningDecision().toUpperCase() + ";");
			} else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " +  
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase()
						+ " WON THE TOSS & ELECTED TO "
						+ match.getSetup().getTossWinningDecision().toUpperCase() + ";");
			}
			break;
		case "CURRENT_RUN_RATE":
			for(Inning inn : match.getMatch().getInning()) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + "CURRENT RUN RATE " + inn.getRunRate() + ";");
			}
			break;
		case "REQUIRED_RUN_RATE":
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + 
					"REQUIRED RUN RATE " + CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0,
							CricketFunctions.GetTargetData(match).getRemaningBall(), 2, match) + ";");
			break;
		case "TARGET":
			
			for (Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == 2
						& inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + "TARGET " + 
								CricketFunctions.GetTargetData(match).getTargetRuns() + " (VJD)" + ";");
					} else if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + "TARGET " + 
								CricketFunctions.GetTargetData(match).getTargetRuns() + " (DLS)" + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + "TARGET " + 
								CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
					}
				}
			}
			break;	
		}
		infobar.setScorebug_last_value(value);
		return infobar;
	}
	
	public Infobar populateInfobar(Infobar infobar, List<PrintWriter> print_writer,boolean is_this_updating,MatchAllData match, String broadcaster) throws InterruptedException, IOException 
	{
		infobar = populateInfo(infobar, print_writer, false, match, broadcaster);
		infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, broadcaster);

		return infobar;
	}
	
	public void populateLineupLong(List<PrintWriter> print_writers, boolean is_this_updating, int whichInning, MatchAllData match, String session_selected_broadcaster, 
			Configuration config, List<Statistics> statistics, List<HeadToHeadPlayer> head_to_head, CricketService cricketService) throws InterruptedException, IOException {
		switch(session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			int row = 0, captainId = 0,omo=0;
			String which_role = "",container_Name="";
			Statistics stat = null;
			
			Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning).findAny().
					orElseThrow(()->new RuntimeException("Inning not found"));
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 5\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 10\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$select_InfoStyle"
					+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$Style2$select_Status"
//					+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$Style2$select_Info"
//					+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$PlayersAll"
					+ "$select_PlayerNumbers*FUNCTION*Omo*vis_con SET " + inning.getBattingCard().size() + "\0", print_writers);
			
			for(int i=1;i<=10;i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$FOW$Wickets$"
						+ i + "$txt_Wicket*GEOM*TEXT SET " + i + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$FOW$Runs$"
						+ i + "$txt_Run*GEOM*TEXT SET \0", print_writers);
			}
			
			if(inning.getFallsOfWickets() != null) {
				for(int i=0;i<=inning.getFallsOfWickets().size()-1;i++) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$FOW$Runs$"
							+ (i+1) + "$txt_Run*GEOM*TEXT SET " + inning.getFallsOfWickets().get(i).getFowRuns() + "\0", print_writers);
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$FOW$Style2"
					+ "$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inning, "-", false) + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5"
					+ "$txt_TeamName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			
			if(inning.getBatting_team().getTeamId() == match.getSetup().getHomeTeamId()) {
				for(Player plyr : match.getSetup().getHomeSquad()) {
					if(plyr.getCaptainWicketKeeper() != null && (plyr.getCaptainWicketKeeper().equalsIgnoreCase("captain") || 
							plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER"))) {
						captainId = plyr.getPlayerId();
					}
				}
				if(captainId == 0) {
					if(match.getSetup().getHomeSubstitutes() != null && !match.getSetup().getHomeSubstitutes().isEmpty()) {
						for(Player plyr : match.getSetup().getHomeSubstitutes()) {
							if(plyr.getCaptainWicketKeeper() != null && (plyr.getCaptainWicketKeeper().equalsIgnoreCase("captain") || 
									plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER"))) {
								captainId = plyr.getPlayerId();
							}
						}
					}
				}
			}else {
				for(Player plyr : match.getSetup().getAwaySquad()) {
					if(plyr.getCaptainWicketKeeper() != null && (plyr.getCaptainWicketKeeper().equalsIgnoreCase("captain") || 
							plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER"))) {
						captainId = plyr.getPlayerId();
					}
				}
				if(captainId == 0) {
					if(match.getSetup().getAwaySubstitutes() != null && !match.getSetup().getAwaySubstitutes().isEmpty()) {
						for(Player plyr : match.getSetup().getAwaySubstitutes()) {
							if(plyr.getCaptainWicketKeeper() != null && (plyr.getCaptainWicketKeeper().equalsIgnoreCase("captain") || 
									plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER"))) {
								captainId = plyr.getPlayerId();
							}
						}
					}
				}
			}
			Collections.sort(inning.getBattingCard());
			for(BattingCard bc : inning.getBattingCard()) {
				row++;
				
				stat = statistics.stream().filter(st -> st.getPlayerID() == bc.getPlayerId() && st.getStatsTypeId() == 3).findAny().orElse(null);				
				if(stat != null) {
					stat.setStats_type(cricketService.getStatsType(stat.getStatsTypeId()));
					if(stat.getStats_type().getStatsShortName().equalsIgnoreCase("IT20")) {
						stat = CricketFunctions.updateTournamentWithH2h(stat, head_to_head, match, CricketUtil.FULL);
						stat = CricketFunctions.updateStatisticsWithMatchData(stat, match, CricketUtil.FULL);
					}
				}
				
				if(bc.getPlayer().getRole().equalsIgnoreCase("BATSMAN") || bc.getPlayer().getRole().equalsIgnoreCase("BAT/KEEPER")) {
					if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("RHB")) {
						which_role = "Batsman";
					}else if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
						which_role = "Batsman_Lefthand";
					}
				}else if(bc.getPlayer().getRole().equalsIgnoreCase("BOWLER")) {
					if(bc.getPlayer().getBowlingStyle() == null) {
						which_role = "Bowler";
					}else {
						switch(bc.getPlayer().getBowlingStyle()) {
						case "RFM": case "RF": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
							which_role = "FastBowler";
							break;
						case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
							which_role = "SpinBowler";
							break;
						case "ROB":
							which_role = "Off_Spin";
							break;
						case "RLB":
							which_role = "Leg_Spin";
							break;
						}
					}
				}else if(bc.getPlayer().getRole().equalsIgnoreCase("ALL-ROUNDER")) {
					if(bc.getPlayer().getBowlingStyle() == null) {
						if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
							which_role = "Off_Spin_Allrounder_Left";
						}else {
							which_role = "FastBowlerAllrounder";
						}
					}else {
						switch (bc.getPlayer().getBowlingStyle().toUpperCase()) {
						case "RFM": case "RF": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
							if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
								which_role = "Pace_BowlerAllrounerLeftHand";
							}else {
								which_role = "FastBowlerAllrounder";
							}
							break;

						case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
							if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
								which_role = "Off_Spin_Allrounder_Left";
							}else {
								which_role = "Off_Spin_Bat";
							}
							break;
						}
					}
				}
			
				switch(bc.getStatus().toUpperCase()) {
				case CricketUtil.STILL_TO_BAT:
					if(bc.getHowOut() != null) {
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$select_Highlights*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$select_Status*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$select_DataStyle*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
									+ row + "$Dehighlight$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inning.getBatting_team().getTeamBadge() + "\\\\Left_2048\\\\" 
									+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" + row + 
									"$Dehighlight$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + photo_path + inning.getBatting_team().getTeamBadge() 
									+ "\\\\Left_2048\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}

						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$txt_FirstName*GEOM*TEXT SET \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$txt_LastName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0", print_writers);
						
//						if(bc.getPlayerId() == captainId) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
//									+ row + "$Dehighlight$select_Status$select_Captain*FUNCTION*Omo*vis_con SET 1\0", print_writers);
//						}else {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
//									+ row + "$Dehighlight$select_Status$select_Captain*FUNCTION*Omo*vis_con SET 0\0", print_writers);
//						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$select_Status$select_Captain*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$Score$txt_Score*GEOM*TEXT SET " + bc.getRuns() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$Score$txt_Balls*GEOM*TEXT SET OFF " + bc.getBalls() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$img_Role*TEXTURE*IMAGE SET " + icon_path + which_role + "\0", print_writers);
					}else {
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$select_Highlights*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$select_Status*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$select_DataStyle*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
									+ row + "$Dehighlight$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inning.getBatting_team().getTeamBadge() + "\\\\Left_2048\\\\" 
									+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" + row + 
									"$Dehighlight$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + photo_path + inning.getBatting_team().getTeamBadge() 
									+ "\\\\Left_2048\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}

						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$txt_FirstName*GEOM*TEXT SET \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$txt_LastName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0", print_writers);
						
//						if(bc.getPlayerId() == captainId) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
//									+ row + "$Dehighlight$select_Status$select_Captain*FUNCTION*Omo*vis_con SET 1\0", print_writers);
//						}else {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
//									+ row + "$Dehighlight$select_Status$select_Captain*FUNCTION*Omo*vis_con SET 0\0", print_writers);
//						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$Stat$txt_StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$Stat$txt_StatValue*GEOM*TEXT SET " + (stat != null ? CricketFunctions.generateStrikeRate(stat.getRuns(), 
								stat.getBallsFaced(), 0):"-") + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + "$Dehighlight$img_Role*TEXTURE*IMAGE SET " + icon_path + which_role + "\0", print_writers);
					}
					break;
				default:
					switch (bc.getStatus().toUpperCase()) {
					case CricketUtil.OUT:
						omo = 0;
						container_Name = "$Dehighlight";
						break;
					case CricketUtil.NOT_OUT:
						omo = 1;
						container_Name = "$Highlight";
						break;
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
							+ row + "$select_Highlights*FUNCTION*Omo*vis_con SET " + omo + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
							+ row + container_Name + "$select_Status*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
							+ row + container_Name + "$select_DataStyle*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
								+ row + container_Name + "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inning.getBatting_team().getTeamBadge() + "\\\\Left_2048\\\\" 
								+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" + row + 
								container_Name + "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + photo_path + inning.getBatting_team().getTeamBadge() 
								+ "\\\\Left_2048\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
							+ row + container_Name + "$txt_FirstName*GEOM*TEXT SET \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
							+ row + container_Name + "$txt_LastName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0", print_writers);
					
//					if(bc.getPlayerId() == captainId) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
//								+ row + container_Name + "$select_Status$select_Captain*FUNCTION*Omo*vis_con SET 1\0", print_writers);
//					}else {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
//								+ row + container_Name + "$select_Status$select_Captain*FUNCTION*Omo*vis_con SET 0\0", print_writers);
//					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
							+ row + container_Name + "$Score$txt_Score*GEOM*TEXT SET " + bc.getRuns() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
							+ row + container_Name + "$Score$txt_Balls*GEOM*TEXT SET OFF " + bc.getBalls() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$11_Players$" 
							+ row + container_Name + "$img_Role*TEXTURE*IMAGE SET " + icon_path + which_role + "\0", print_writers);
				}
			}
			break;
		}
	}
	
	public void populateLongLineup(List<PrintWriter> print_writers,boolean is_this_updating, int whichInning, MatchAllData match, String session_selected_broadcaster,
			Configuration config) throws InterruptedException, IOException {
		
		int row = 0, omo=0;
		String which_role="",container_Name ="",container_Name2="";
		
		Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning).findAny().
				orElseThrow(()-> new RuntimeException("Inning not found"));
		
		this.status = CricketUtil.UNSUCCESSFUL;
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 5\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 10\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$select_InfoStyle"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$Style2$select_Status"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$Style2$select_Info"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$PlayersAll"
				+ "$select_PlayerNumbers*FUNCTION*Omo*vis_con SET " + inning.getBowlingCard().size() + "\0", print_writers);
		
		for(int i=1;i<=10;i++) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$FOW$Wickets$"
					+ i + "$txt_Wicket*GEOM*TEXT SET " + i + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$FOW$Runs$"
					+ i + "$txt_Run*GEOM*TEXT SET \0", print_writers);
		}
		
		if(inning.getFallsOfWickets() != null) {
			for(int i=0;i<=inning.getFallsOfWickets().size()-1;i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$FOW$Runs$"
						+ (i+1) + "$txt_Run*GEOM*TEXT SET " + inning.getFallsOfWickets().get(i).getFowRuns() + "\0", print_writers);
			}
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$FOW$Style2"
				+ "$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inning, "-", false) + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5"
				+ "$txt_TeamName*GEOM*TEXT SET " + inning.getBowling_team().getTeamName1() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
		
		if(inning.getBowlingCard() != null && inning.getBowlingCard().size()>=3) {
			for(BowlingCard boc : inning.getBowlingCard()) {
				row++;
				
				if(boc.getPlayer().getRole().equalsIgnoreCase("BATSMAN") || boc.getPlayer().getRole().equalsIgnoreCase("BAT/KEEPER")) {
					if(boc.getPlayer().getBattingStyle().equalsIgnoreCase("RHB")) {
						which_role = "Batsman";
					}else if(boc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
						which_role = "Batsman_Lefthand";
					}
				}else if(boc.getPlayer().getRole().equalsIgnoreCase("BOWLER")) {
					if(boc.getPlayer().getBowlingStyle() == null) {
						which_role = "Bowler";
					}else {
						switch(boc.getPlayer().getBowlingStyle()) {
						case "RFM": case "RF": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
							which_role = "FastBowler";
							break;
						case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
							which_role = "SpinBowler";
							break;
						case "ROB":
							which_role = "Off_Spin";
							break;
						case "RLB":
							which_role = "Leg_Spin";
							break;
						}
					}
				}else if(boc.getPlayer().getRole().equalsIgnoreCase("ALL-ROUNDER")) {
					if(boc.getPlayer().getBowlingStyle() == null) {
						if(boc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
							which_role = "Off_Spin_Allrounder_Left";
						}else {
							which_role = "FastBowlerAllrounder";
						}
					}else {
						switch (boc.getPlayer().getBowlingStyle().toUpperCase()) {
						case "RFM": case "RF": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
							if(boc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
								which_role = "Pace_BowlerAllrounerLeftHand";
							}else {
								which_role = "FastBowlerAllrounder";
							}
							break;

						case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
							if(boc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
								which_role = "Off_Spin_Allrounder_Left";
							}else {
								which_role = "Off_Spin_Bat";
							}
							break;
						}
					}
				}
				
				switch(inning.getBowlingCard().size()) {
				case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10: case 11:
					container_Name = inning.getBowlingCard().size() + "_Players$";
					break;
				}
				switch (boc.getStatus().toUpperCase()) {
				case (CricketUtil.OTHER + CricketUtil.BOWLER): case (CricketUtil.LAST + CricketUtil.BOWLER):
					omo = 0;
					container_Name2 = "$Dehighlight";
					break;
				case (CricketUtil.CURRENT + CricketUtil.BOWLER):
					omo = 1;
					container_Name2 = "$Highlight";
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name
						+ row + "$select_Highlights*FUNCTION*Omo*vis_con SET " + omo + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name
						+ row + container_Name2 + "$select_Status*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name
						+ row + container_Name2 + "$select_DataStyle*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name 
							+ row + container_Name2 + "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inning.getBowling_team().getTeamBadge() + "\\\\Left_2048\\\\" 
							+ boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name + row 
							+ container_Name2 + "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + photo_path + inning.getBowling_team().getTeamBadge() 
							+ "\\\\Left_2048\\\\" + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name 
						+ row + container_Name2 + "$txt_FirstName*GEOM*TEXT SET \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name
						+ row + container_Name2 + "$txt_LastName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name + row 
						+ container_Name2 + "$Score$txt_Score*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name + row 
						+ container_Name2 + "$Score$txt_Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) 
						+ (boc.getOvers() == 1 && boc.getBalls() == 0 ? " OVER":" OVERS")+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ScoreCard$" + container_Name + row 
						+ container_Name2 + "$img_Role*TEXTURE*IMAGE SET " + icon_path + which_role + "\0", print_writers);
				
			}
			this.status = CricketUtil.SUCCESSFUL;
		}
		
		if(this.status.equalsIgnoreCase(CricketUtil.UNSUCCESSFUL)) {
			current_layer = 5- current_layer;
		}
	}
	
	public void populateMatchSummary(List<PrintWriter> print_writers, boolean is_this_updating, int whichInning, MatchAllData match, List<Player> allPlayer, 
			String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		int row = 0;
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 20\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		if(whichInning == 1) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$"
					+ "select_Innings*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings$Title"
					+ "$txt_CountryName*GEOM*TEXT SET " + match.getMatch().getInning().get(0).getBatting_team().getTeamName1() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings$Title"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getMatch().getInning().get(0).getBatting_team().getTeamBadge() + "\0", print_writers);
			
			if(match.getSetup().getTossWinningTeam() == match.getMatch().getInning().get(0).getBattingTeamId()) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
						+ "$Title$select_Toss*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
						+ "$Title$select_Toss*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings$Title"
					+ "$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(match.getMatch().getInning().get(0), "-", false) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings$Title"
					+ "$txt_Over*GEOM*TEXT SET " + CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(), 
							match.getMatch().getInning().get(0).getTotalBalls()) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings$Title"
					+ "$txt_OverHead*GEOM*TEXT SET " + (match.getMatch().getInning().get(0).getTotalOvers() == 1 && 
					match.getMatch().getInning().get(0).getTotalBalls() == 0 ? "OVER" : "OVERS") + "\0", print_writers);
			
			if(match.getMatch().getInning().get(0).getBattingCard() != null) {
				Collections.sort(match.getMatch().getInning().get(0).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
				for(BattingCard bc : match.getMatch().getInning().get(0).getBattingCard()) {
					if(row >= 2) break;
					if(bc.getRuns() > 0) {
						row ++;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
								+ "$Row" + row + "$Batter*ACTIVE SET 1\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
								+ "$Row" + row + "$Batter$txt_Name*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
								+ "$Row" + row + "$Batter$txt_Runs*GEOM*TEXT SET " + bc.getRuns() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
								+ "$Row" + row + "$Batter$txt_NotOutStar*GEOM*TEXT SET " + (bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT) ? "*" : "") + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
								+ "$Row" + row + "$Batter$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + "\0", print_writers);
					}
				}
				if(row == 1) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
							+ "$Row2$Batter*ACTIVE SET 0\0", print_writers);
				}
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
						+ "$Row1$Batter*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
						+ "$Row2$Batter*ACTIVE SET 0\0", print_writers);
			}
			
			if(match.getMatch().getInning().get(0).getBowlingCard() != null) {
				row =0;
				Collections.sort(match.getMatch().getInning().get(0).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
				for(BowlingCard boc : match.getMatch().getInning().get(0).getBowlingCard()) {
					if(row >=2) break;
					if (boc.getWickets() > 0) {
						row++;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
								+ "$Row" + row + "$Bowler*ACTIVE SET 1\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
								+ "$Row" + row + "$Bowler$txt_Name*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
								+ "$Row" + row + "$Bowler$txt_Figures*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
								+ "$Row" + row + "$Bowler$txt_Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0", print_writers);
					}else {
						if(row == 0) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
									+ "$Row1$Bowler*ACTIVE SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
									+ "$Row2$Bowler*ACTIVE SET 0\0", print_writers);
						}else if(row == 1) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
									+ "$Row2$Bowler*ACTIVE SET 0\0", print_writers);
						}
					}
				}
				if(row == 1) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
							+ "$Row2$Bowler*ACTIVE SET 0\0", print_writers);
				}
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
						+ "$Row1$Bowler*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$FirstInnings"
						+ "$Row2$Bowler*ACTIVE SET 0\0", print_writers);
			}
		}else if(whichInning == 2) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$"
					+ "select_Innings*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			
			for(int i=1;i<=whichInning;i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i 
						+ "$Title$txt_CountryName*GEOM*TEXT SET " + match.getMatch().getInning().get(i-1).getBatting_team().getTeamName1() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i 
						+ "$Title$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getMatch().getInning().get(i-1).getBatting_team().getTeamBadge() + "\0", print_writers);
				
				if(match.getSetup().getTossWinningTeam() == match.getMatch().getInning().get(i-1).getBattingTeamId()) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" 
							+ i + "$Title$select_Toss*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team"
							+ i + "$Title$select_Toss*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i
						+ "$Title$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(match.getMatch().getInning().get(i-1), "-", false) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i 
						+ "$Title$txt_Over*GEOM*TEXT SET " + CricketFunctions.OverBalls(match.getMatch().getInning().get(i-1).getTotalOvers(), 
								match.getMatch().getInning().get(i-1).getTotalBalls()) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i 
						+ "$Title$txt_OverHead*GEOM*TEXT SET " + (match.getMatch().getInning().get(i-1).getTotalOvers() == 1 && 
							match.getMatch().getInning().get(i-1).getTotalBalls() == 0 ? "OVER" : "OVERS") + "\0", print_writers);
				
				if(match.getMatch().getInning().get(i-1).getBattingCard() != null) {
					row =0;
					Collections.sort(match.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					for(BattingCard bc : match.getMatch().getInning().get(i-1).getBattingCard()) {
						if(row >= 1) break;
						if(bc.getRuns() > 0) {
							row ++;
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings"
									+ "$Team" + i + "$Row" + row + "$Batter*ACTIVE SET 1\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings"
									+ "$Team" + i + "$Row" + row + "$Batter$txt_Name*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings"
									+ "$Team" + i + "$Row" + row + "$Batter$txt_Runs*GEOM*TEXT SET " + bc.getRuns() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings"
									+ "$Team" + i + "$Row" + row + "$Batter$txt_NotOutStar*GEOM*TEXT SET " + (bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)?"*":"") 
									+ "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings"
									+ "$Team" + i + "$Row" + row + "$Batter$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + "\0", print_writers);
						}
					}
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings"
							+ "$Team" + i + "$Row1$Batter*ACTIVE SET 0\0", print_writers);
				}
				
				if(match.getMatch().getInning().get(i-1).getBowlingCard() != null) {
					row =0;
					Collections.sort(match.getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
					for(BowlingCard boc : match.getMatch().getInning().get(i-1).getBowlingCard()) {
						if(row >=1) break;
						if (boc.getWickets() > 0) {
							row++;
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i + 
									"$Row" + row + "$Bowler*ACTIVE SET 1\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i + 
									"$Row" + row + "$Bowler$txt_Name*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i + 
									"$Row" + row + "$Bowler$txt_Runs*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i + 
									"$Row" + row + "$Bowler$txt_NotOutStar*GEOM*TEXT SET \0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i + 
									"$Row" + row + "$Bowler$txt_Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0", print_writers);
						}else {
							if(row == 0) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i + 
										"$Row1$Bowler*ACTIVE SET 0\0", print_writers);
							}
						}
					}
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$MatchSummary$SecondInnings$Team" + i + 
							"$Row1$Bowler*ACTIVE SET 0\0", print_writers);
				}
			}
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer*GEOM*TEXT SET \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer02*GEOM*TEXT SET " + 
				CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.SHORT, "|",session_selected_broadcaster,false).getTargetOrResult().toUpperCase() + "\0", print_writers);
	}
	
	public Infobar populateInfo(Infobar infobar, List<PrintWriter> print_writers,boolean is_this_updating,MatchAllData curr_match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		for(Inning inn : curr_match.getMatch().getInning()) {
			if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
				if(is_this_updating == false) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
							+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
							+ "*FUNCTION*Omo*vis_con SET 7\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
							+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$TeamLogo"
							+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName4() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$TeamLogo"
							+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$Score"
						+ "$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, "-", false) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$Overs"
						+ "$txt_OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$Overs"
						+ "$txt_Overs*GEOM*TEXT SET " + (inn.getTotalOvers()==1 && inn.getTotalBalls()==0 ? "OVER" : "OVERS") + "\0", print_writers);
				
				if (inn.getInningNumber() == 1) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style2"
							+ "$txt_Footer*GEOM*TEXT SET " + "CURRENT RUN RATE " + inn.getRunRate() + "\0", print_writers);
				}else {
					
					if(CricketFunctions.GetTargetData(curr_match).getRemaningRuns() <= 0 || curr_match.getMatch().getInning().get(1).getTotalWickets() >= 10 
							|| CricketFunctions.GetTargetData(curr_match).getRemaningBall() == 0) {
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style2"
								+ "$txt_Footer*GEOM*TEXT SET " + CricketFunctions.GenerateMatchSummaryStatus(2, curr_match, CricketUtil.SHORT, 
										"|",session_selected_broadcaster,false).getTargetOrResult().toUpperCase() + "\0", print_writers);
					}
					else if (CricketFunctions.GetTargetData(curr_match).getRemaningRuns() == 1 && (CricketFunctions.GetTargetData(curr_match).getRemaningBall() <= 0 
				    		|| CricketFunctions.getWicketsLeft(curr_match, curr_match.getMatch().getInning().get(1).getInningNumber()) <= 0)) {
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style2"
								+ "$txt_Footer*GEOM*TEXT SET " + "MATCH TIED - WINNER WILL BE DECIDED BY SUPER OVER" + "\0", print_writers);
					}
					else {
						if(CricketFunctions.GetTargetData(curr_match).getRemaningRuns() == 1) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style2"
									+ "$txt_Footer*GEOM*TEXT SET " + "SCORES ARE LEVEL" + "\0", print_writers);
						}else {
							if(CricketFunctions.GetTargetData(curr_match).getRemaningBall() >= 100) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style2"
										+ "$txt_Footer*GEOM*TEXT SET " + "NEED " + CricketFunctions.GetTargetData(curr_match).getRemaningRuns() + " RUN" 
										+ CricketFunctions.Plural(CricketFunctions.GetTargetData(curr_match).getRemaningRuns()).toUpperCase() + " OFF " 
										+ CricketFunctions.OverBalls(0, CricketFunctions.GetTargetData(curr_match).getRemaningBall()) + " OVERS" 
										+ (curr_match.getSetup().getTargetType() != null && !curr_match.getSetup().getTargetType().isEmpty() ? 
										" (" + curr_match.getSetup().getTargetType().toUpperCase() + ")" :"") + "\0", print_writers);
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style2"
										+ "$txt_Footer*GEOM*TEXT SET " + "NEED " + CricketFunctions.GetTargetData(curr_match).getRemaningRuns() + " RUN" 
										+ CricketFunctions.Plural(CricketFunctions.GetTargetData(curr_match).getRemaningRuns()).toUpperCase() + " OFF " 
										+ CricketFunctions.GetTargetData(curr_match).getRemaningBall() + " BALL" 
										+ CricketFunctions.Plural(CricketFunctions.GetTargetData(curr_match).getRemaningBall()).toUpperCase() 
										+ (curr_match.getSetup().getTargetType() != null && !curr_match.getSetup().getTargetType().isEmpty() ? 
										" (" + curr_match.getSetup().getTargetType().toUpperCase() + ")" :"") + "\0", print_writers);
							}
						}
					}
				}
			}
		}
		return infobar;
	}

	public Infobar populateVizInfobarMiddle(Infobar infobar, boolean is_this_updating, List<PrintWriter> print_writer,MatchAllData cricketMatch, String broadcaster) throws InterruptedException
		{ 
			List<BattingCard> current_batsmen = new ArrayList<BattingCard>();
				for(Inning inn : cricketMatch.getMatch().getInning()) {
					if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						for (BattingCard bc : inn.getBattingCard()) {
							if (inn.getPartnerships() != null && inn.getPartnerships().size() > 0) {
								if (bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1)
										.getFirstBatterNo()) {
									current_batsmen.add(bc);
								} else if (bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1)
										.getSecondBatterNo()) {
									current_batsmen.add(bc);
								}
							}
						}
						
						if(infobar.getLast_batsmen() == null || infobar.getLast_batsmen().size() <= 0) {
							infobar.setLast_batsmen(current_batsmen);
						}
						populateCurrentBatsmen(infobar,print_writer, cricketMatch, broadcaster,current_batsmen);
					}
				}
			return infobar;
		}
	public Infobar populateCurrentBatsmen(Infobar infobar, List<PrintWriter> print_writers, MatchAllData match, String broadcaster,List<BattingCard> current_batsmen) throws InterruptedException
		{
			for (Inning inn : match.getMatch().getInning()) {

				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					
					if(current_batsmen != null && current_batsmen.size() >= 1) {
						if (infobar.getLast_batsmen() != null && infobar.getLast_batsmen().size() >= 1) {
							if (infobar.getLast_batsmen().get(0).getPlayerId() != current_batsmen.get(0).getPlayerId()) {
								TimeUnit.MILLISECONDS.sleep(800);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$1"
										+ "$txt_Name*GEOM*TEXT SET " + current_batsmen.get(0).getPlayer().getTicker_name().toUpperCase() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$1"
										+ "$txt_Runs*GEOM*TEXT SET " + current_batsmen.get(0).getRuns() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$1"
										+ "$txt_Balls*GEOM*TEXT SET " + current_batsmen.get(0).getBalls() + "\0", print_writers);
								
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$1"
										+ "$txt_Name*GEOM*TEXT SET " + current_batsmen.get(0).getPlayer().getTicker_name().toUpperCase() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$1"
										+ "$txt_Runs*GEOM*TEXT SET " + current_batsmen.get(0).getRuns() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$1"
										+ "$txt_Balls*GEOM*TEXT SET " + current_batsmen.get(0).getBalls() + "\0", print_writers);
								
//								if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) { 
//									processAnimation(print_writer, "Batsman1_Dehighlight", "SHOW 10.0", broadcaster);
//								}else if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
//									processAnimation(print_writer, "Batsman1_Dehighlight", "SHOW 0.0", broadcaster);
//								}
							}
							
							if (infobar.getLast_batsmen().get(1).getPlayerId() != current_batsmen.get(1).getPlayerId()) {
//								processAnimation(print_writer, "Batsman2Out", "START", broadcaster);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$2"
										+ "$txt_Name*GEOM*TEXT SET " + current_batsmen.get(1).getPlayer().getTicker_name().toUpperCase() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$2"
										+ "$txt_Runs*GEOM*TEXT SET " + current_batsmen.get(1).getRuns() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$2"
										+ "$txt_Balls*GEOM*TEXT SET " + current_batsmen.get(1).getBalls() + "\0", print_writers);
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$2"
										+ "$txt_Name*GEOM*TEXT SET " + current_batsmen.get(1).getPlayer().getTicker_name().toUpperCase() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$2"
										+ "$txt_Runs*GEOM*TEXT SET " + current_batsmen.get(1).getRuns() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp$2"
										+ "$txt_Balls*GEOM*TEXT SET " + current_batsmen.get(1).getBalls() + "\0", print_writers);
								
//								if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) { 
//									processAnimation(print_writer, "Batsman2_Dehighlight", "SHOW 10.0", broadcaster);
//								}else if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
//									processAnimation(print_writer, "Batsman2_Dehighlight", "SHOW 0.0", broadcaster);
//								}
							}
						}
						if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT) && current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							if(current_batsmen.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp"
										+ "$OnStrike*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp"
										+ "$OnStrike*FUNCTION*Omo*vis_con SET 2\0", print_writers);
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$InningsSummary$PlayerGrp"
									+ "$OnStrike*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
					}
				}
			}
				
			infobar.setLast_batsmen(current_batsmen);
			return infobar;
		}
	public void populateOutNotDecision(List<PrintWriter> print_writers,String decision, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 12\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		switch (decision.toUpperCase()) {
		case "OUT":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$BottomLine"
					+ "$select_Decision*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$select_TopData*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$txt_Info1*GEOM*TEXT SET \0", print_writers);
			break;
		case "NOTOUT":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$BottomLine"
					+ "$select_Decision*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$select_TopData*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$txt_Info1*GEOM*TEXT SET \0", print_writers);
			break;
		case "REVERSEDNOTOUT":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$BottomLine"
					+ "$select_Decision*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$select_TopData*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$txt_Info1*GEOM*TEXT SET DECISION REVERSED\0", print_writers);
			break;
		case "STANDNOTOUT":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$BottomLine"
					+ "$select_Decision*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$select_TopData*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$txt_Info1*GEOM*TEXT SET DECISION STANDS\0", print_writers);
			break;
		case "REVERSEDOUT":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$BottomLine"
					+ "$select_Decision*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$select_TopData*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$txt_Info1*GEOM*TEXT SET DECISION REVERSED\0", print_writers);
			break;
		case "STANDOUT":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$BottomLine"
					+ "$select_Decision*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$select_TopData*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Decision$Topline"
					+ "$txt_Info1*GEOM*TEXT SET DECISION STANDS\0", print_writers);
			break;	
		}	
	}
	
	public void populateDecision(List<PrintWriter> print_writers,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 11\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DecisionPending$Info"
				+ "$txt_Info*GEOM*TEXT SET DECISION PENDING\0", print_writers);
	}
	
	public void populateIdentMatch(List<PrintWriter> print_writers,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$MatchNumber"
				+ "$txt_MatchNumber*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams$Team1"
				+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams$Team2"
				+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName4() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams$Team1"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams$Team2"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer"
				+ "*GEOM*TEXT SET \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer02"
				+ "*GEOM*TEXT SET " + match.getSetup().getVenueName() + "\0", print_writers);
	}
	
	public void populateMatchResult(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*group$Main$ThirdEmpire$Decision$Main$Data$base2*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*group$Main$ThirdEmpire$Decision$Main$Data$BAse2*CONTAINER SET ACTIVE 0;");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
					match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
					match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + match.getSetup().getMatchIdent() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL,
					"|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			
			break;
		}
	}
	
	public void populateTargetBs(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + ".png" +";");

			int requiredRuns = match.getMatch().getInning().get(0).getTotalRuns() + 1;
			
			if(match.getSetup().getTargetRuns() != 0) {
				requiredRuns = match.getSetup().getTargetRuns();
			}
			
			if(requiredRuns <= 0) {
				requiredRuns = 0;
			}
			
			int requiredBalls = 0;
			if(match.getSetup().getTargetOvers() != null && !match.getSetup().getTargetOvers().trim().isEmpty()) {
				if(match.getSetup().getTargetOvers().contains(".")) {
					requiredBalls = ((Integer.valueOf(match.getSetup().getTargetOvers().split(".")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split(".")[1]));
				} else {
					requiredBalls = ((Integer.valueOf(match.getSetup().getTargetOvers()) * 6));
				}
			}else {
				requiredBalls = ((match.getSetup().getMaxOvers()) * 6);
			}
			
			if(requiredBalls <= 0) {
				requiredBalls = 0;
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHeadTarget " + "REQUIRED RUN RATE " + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRTarget " + CricketFunctions.generateRunRate
					(requiredRuns, 0, requiredBalls, 2,match) + ";");
			System.out.println("TARGET = " + CricketFunctions.GetTargetData(match).getTargetRuns());
			if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
				if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS " +";");
				}else {
					if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" +";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS"  +";");
					}
				}
			}else {
				if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" + 
					" ("+ match.getSetup().getTargetType().toUpperCase() +")"  +";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS" + 
							" ("+ match.getSetup().getTargetType().toUpperCase() +")"  +";");
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			
			break;
		}
	}
	public void populateComparisonBs(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$BaseTeam1*TEXTURE1 SET TEXTURE_PATH " 
//							+ base1_path + match.getMatch().getInning().get(0).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$BaseTeam2*TEXTURE1 SET TEXTURE_PATH " 
//							+ base1_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$TeamA*TEXTURE2 SET TEXTURE_PATH " 
//							+ text1_path + match.getMatch().getInning().get(0).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$TeamB*TEXTURE2 SET TEXTURE_PATH " 
//							+ text1_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
							match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION +";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
							match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION +";");

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreA " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreB " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + match.getMatch().getInning().get(0).getBatting_team().getTeamName3().toUpperCase() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamB " + match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + ";");
					
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			
			break;
		}
	}
	public void populateInfo(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + inn.getBatting_team().getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamB " + inn.getBowling_team().getTeamName4().toUpperCase() + ";");
					}
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
					}
					else{
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversHead " + "OVERS" + ";");
					
					
					for(BattingCard bc : inn.getBattingCard()) {
						if(inn.getPartnerships() != null && inn.getPartnerships().size() > 0) {
							if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName1 " + 
										bc.getPlayer().getTicker_name() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRuns1 " + bc.getRuns() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBalls1 " + bc.getBalls() + ";");
								
								if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vStrike " + "0" + ";");
								}
								
							} else if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName2 " + 
										bc.getPlayer().getTicker_name() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRuns2 " + bc.getRuns() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBalls2 " + bc.getBalls() + ";");
								
								if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vStrike " + "1" + ";");
								}
							}
						}
					}
					
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") 
								|| boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
									boc.getPlayer().getTicker_name() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerRuns " + 
									boc.getRuns() + "-" + boc.getWickets() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOvers " + 
									CricketFunctions.getOvers(boc.getOvers(), boc.getBalls()) + ";");
							
						}
					}
					
					if(inn.getInningNumber() == 1) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHead " + "CURRENT RUN RATE " + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + inn.getRunRate() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHead " + "REQUIRED RUN RATE " + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + 
								CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
					}
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			break;
		}
	}
	
	public void populateHowout(PrintWriter print_writer,boolean is_this_updating,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOut's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if(inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " Overs" + ";");

						if(inn.getInningNumber() == 1) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "CRR" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + inn.getRunRate() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "TARGET" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
						}
						
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == playerId) {
								
								if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
										CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
														bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + ";");
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + bc.getHowOutText() + ";");
								}
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUNS" + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + bc.getRuns()+ ";");

								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + bc.getBalls() + ";");

								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "FOURS" + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + bc.getFours() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "SIXES" + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + bc.getSixes() + ";");
							}
						}
					}
				}
			}

		Player plyr = getPlayerFromMatchData(playerId, match);
		
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
					"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
					match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");
		}
		else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
					"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
					match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");
		}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead5 " + " " + ";");
		}
		
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	public void populateMatchID(List<PrintWriter> print_writers,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams"
				+ "$Separator*GEOM*TEXT SET V\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$MatchNumber"
				+ "$txt_MatchNumber*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams$Team1"
				+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams$Team2"
				+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName4() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams$Team1"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams$Team2"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer"
				+ "*GEOM*TEXT SET \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer02"
				+ "*GEOM*TEXT SET \0", print_writers);
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer02"
//				+ "*GEOM*TEXT SET " + match.getSetup().getVenueName() + "\0", print_writers);
	}
	
	private void populateMatchIDWithImgBs(List<PrintWriter> print_writers,boolean is_this_updating, MatchAllData mtch,
			List<Player> allPlayers, List<Team> teams, String session_selected_broadcaster,Configuration config) throws InterruptedException {
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 19\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$MatchNumber"
				+ "$txt_MatchNumber*GEOM*TEXT SET " + mtch.getSetup().getMatchIdent().toUpperCase() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$Teams"
				+ "$Separator*GEOM*TEXT SET V\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$Teams$Team1"
				+ "$txt_CountryName*GEOM*TEXT SET " + mtch.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$Teams$Team2"
				+ "$txt_CountryName*GEOM*TEXT SET " + mtch.getSetup().getAwayTeam().getTeamName4() + "\0", print_writers);
		
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$Teams$Team1"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + mtch.getSetup().getHomeTeam().getTeamBadge() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$Teams$Team2"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + mtch.getSetup().getAwayTeam().getTeamBadge() + "\0", print_writers);
		
		
		for (Player hs : mtch.getSetup().getHomeSquad()) {
			if(hs.getCaptainWicketKeeper() != null) {
				if (hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
						|| hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$Teams$Team1"
								+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + 
								mtch.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\Left_2048\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$Teams$Team1"
								+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + photo_path + mtch.getSetup().getHomeTeam().getTeamName4().toUpperCase() 
								+ "\\\\Left_2048\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
				}
			}
		}
		
		for (Player as : mtch.getSetup().getAwaySquad()) {
			if(as.getCaptainWicketKeeper() != null) {
				if (as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
						|| as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$Teams$Team2"
								+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + 
								mtch.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\Right_2048\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$IdentImage$Teams$Team2"
								+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + photo_path + mtch.getSetup().getAwayTeam().getTeamName4().toUpperCase() 
								+ "\\\\Right_2048\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
				}
			}
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer*GEOM*TEXT SET \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer02*GEOM*TEXT SET \0", print_writers);
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer02*GEOM*TEXT SET " 
//				+ mtch.getSetup().getVenueName() + "\0", print_writers);
		
	}

	private void populateGroup(PrintWriter print_writer,boolean is_this_updating, String group,String session_selected_broadcaster, List<Team> team) throws InterruptedException {
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " +
				group.toUpperCase()+ ";");
		
			if(!team.isEmpty()) {
				for(int i=1;i<=team.size();i++) {
					if(team.get(i-1).getTeamBadge().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0"+ i +" 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0"+ i +" 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag"+ i +" "+ logo_path +
							team.get(i-1).getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
					if(team.get(i-1).getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName" + i + " " + 
								team.get(i-1).getTeamName4().toUpperCase()+ ";");
					}else if(team.get(i-1).getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName" + i + " " + 
								team.get(i-1).getTeamName4().toUpperCase()+ ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName" + i + " " + 
								team.get(i-1).getTeamName4().toUpperCase()+ ";");
					}
				
				}
			
			}	
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
	}
	
	private void populatePlayerIntroStats(PrintWriter print_writer,boolean is_this_updating, String string, Integer team_id,
			List<Player> allPlayer, List<Team> allTeams, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			if(allTeams.get(team_id-1).getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " +
						allTeams.get(team_id-1).getTeamName4().toUpperCase() + ";");
			}else if(allTeams.get(team_id-1).getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " +
						allTeams.get(team_id-1).getTeamName4().toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " +
						allTeams.get(team_id-1).getTeamName4().toUpperCase() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamNameFull " +
						allTeams.get(team_id-1).getTeamName4().toUpperCase() + ";");
			}
			
			
			if(match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
				allTeams.get(team_id-1).getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			if(team_id == match.getSetup().getHomeTeamId()) {
				for(int i = 0; i <= match.getSetup().getHomeSquad().size() -1 ; i++) {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + (i+1) + " " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4() + "\\Right_2048\\" + match.getSetup().getHomeSquad().get(i).getPhoto() + CricketUtil.PNG_EXTENSION + ";");

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + (i+1) + " " + match.getSetup().getHomeSquad().get(i).getFirstname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + (i+1) + " " + match.getSetup().getHomeSquad().get(i).getSurname() + ";");

				}
			}else {
				for(int i = 0; i <= match.getSetup().getAwaySquad().size() -1 ; i++) {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + (i+1) + " " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4() + "\\Right_2048\\" + match.getSetup().getAwaySquad().get(i).getPhoto() + CricketUtil.PNG_EXTENSION + ";");

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + (i+1) + " " + match.getSetup().getAwaySquad().get(i).getFirstname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + (i+1) + " " + match.getSetup().getAwaySquad().get(i).getSurname() + ";");

				}
			}
			
			this.status = CricketUtil.SUCCESSFUL;
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Change STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 260.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Change SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
		}
	}
	
	public void populateQuickHowout(PrintWriter print_writer,boolean is_this_updating,MatchAllData match, String broadcaster) throws InterruptedException 
	{	
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOutQuick's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " Overs" + ";");

					if(inn.getInningNumber() == 1) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "CRR" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + inn.getRunRate() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "TARGET" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
					}
					
					for (BattingCard bc : inn.getBattingCard()) {
						if(inn.getFallsOfWickets().size() > 0) {
							if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {

								Player plyr = getPlayerFromMatchData(bc.getPlayerId(), match);
								
								if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
											"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
											match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");
								}
								else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
											"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
											match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");
								}
								
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead05 " + " " + ";");
								
									if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
											CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
															bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + ";");
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + bc.getHowOutText() + ";");
									}
			
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUNS" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + bc.getRuns()+ ";");

									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + bc.getBalls() + ";");

									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "FOURS" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + bc.getFours() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "SIXES" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + bc.getSixes() + ";");
							}
						}						
					}
				}
			}
		}
		
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	public void populateBugBowler(PrintWriter print_writer,boolean is_this_updating,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: BugBowler's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " Overs" + ";");

					if(inn.getInningNumber() == 1) {

						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "CRR" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + inn.getRunRate() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "TARGET" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
					}
					for (BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getPlayerId()==playerId) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "" + ";");								

							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "OVERS" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls())+ ";");

							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "WICKETS" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + boc.getWickets() + ";");

							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "RUNS" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + boc.getRuns() + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "DOTS" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + boc.getDots() + ";");
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + " " + ";");
						}
					}
				}
			}
			
			Player plyr = getPlayerFromMatchData(playerId, match);
			
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
						"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
						match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");
			}
			else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
						"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
						match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");
			}	
		}
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	public void populateEquationBs(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tofruns FROM;");
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + 
								inn.getBatting_team().getTeamName4().toUpperCase() + ";");
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
								inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						
						if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
							if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + 
										CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScorBalls " + 
										 CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tballs " + " BALL" +
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
							}else {
								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + 
											CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScorBalls " + 
											CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget RUN" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tballs " + " OVERS" + ";");
									
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + 
											CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScorBalls " + 
											CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget RUN" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tballs " + " BALL" +
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
								}
							}
						}else {
							if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScorBalls " + 
										Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) + 
										CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + 
										" (" + match.getSetup().getTargetType().toUpperCase() + ")" + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tballs " + " OVERS" + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + 
										CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScorBalls " + 
										CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tballs " + " BALL" +
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " ("+ match.getSetup().getTargetType().toUpperCase() +")" + ";");
							}
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRTarget " + 
								CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, 
								CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
					}
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
	}
	
	public void populateFreeBs(PrintWriter print_writer,boolean is_this_updating,String Type, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			switch (Type.toUpperCase()) {
			case "FOUR":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 0;");
				break;
			case "SIX":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 1;");
				break;
			case "FREE_HIT":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 2;");
				break;
			case "WICKET":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 5;");
				break;
			case "WIDE":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 4;");
				break;
			case "NO_BALL":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 3;");
				break;
			case "TOSS":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 6;");
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossTeam " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossTeam " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + ";");
				}
				break;
			case "WINNER":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 7;");
				if(match.getMatch().getMatchResult() != null) {
					if(Integer.valueOf(match.getMatch().getMatchResult().split(",")[0]) == match.getSetup().getHomeTeamId()) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossTeam " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossTeam " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + ";");
					}
					
					if(match.getMatch().getMatchResult().split(",")[2].equalsIgnoreCase(CricketUtil.WICKET)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + "BY " + match.getMatch().getMatchResult().split(",")[1] + " WICKET" + 
										CricketFunctions.Plural(CricketFunctions.getWicketsLeft(match,2)).toUpperCase() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + "BY " + match.getMatch().getMatchResult().split(",")[1] + " RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
					}
				}
				break;
			case "FREE":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 8;");
				String text_to_return = "";
				
				File free_file = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.FREE_TXT);
				if(free_file.exists() == true) {
					text_to_return = Files.newBufferedReader(Paths.get(CricketUtil.CRICKET_DIRECTORY 
							+ CricketUtil.FREE_TXT), StandardCharsets.UTF_8).lines().limit(1).collect(Collectors.toList()).get(0);
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeText " + text_to_return + ";");
				break;
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			break;
		}
	}
	
	public void populateBoundariesBs(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + inn.getTotalFours() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + inn.getTotalSixes() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
								inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");

					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
					}
					else{
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " overs" + ";");
					if(inn.getInningNumber() == 1) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "CRR " + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + inn.getRunRate() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "RRR " + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + 
								CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
					}
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			break;
		}
	}
	
	public void populateProjectedBs(List<PrintWriter> print_writers,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 24\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Data$PlayerInfo"
					+ "$txt_Years*GEOM*TEXT SET \0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style4$txt_Header"
					+ "*GEOM*TEXT SET " + "PROJECTED SCORES" + "\0", print_writers);
			
			String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
		    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
		    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
	        }
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 1 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Score"
							+ "$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, "-", false) + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Overs"
							+ "$txt_OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$TeamLogo"
							+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName4() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$TeamLogo"
							+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Data$1"
							+ "$txt_StatHead*GEOM*TEXT SET " + "@"+ proj_score_rate[0] +" (CRR)" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Data$1"
							+ "$txt_StatValue*GEOM*TEXT SET " + proj_score_rate[1] + "\0", print_writers);
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Data$2"
							+ "$txt_StatHead*GEOM*TEXT SET " + "@" + proj_score_rate[2] +" RPO" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Data$2"
							+ "$txt_StatValue*GEOM*TEXT SET " + proj_score_rate[3] + "\0", print_writers);
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Data$3"
							+ "$txt_StatHead*GEOM*TEXT SET " + "@" + proj_score_rate[4] +" RPO" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Data$3"
							+ "$txt_StatValue*GEOM*TEXT SET " + proj_score_rate[5] + "\0", print_writers);
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Data$4"
							+ "$txt_StatHead*GEOM*TEXT SET " + "@" + proj_score_rate[6] +" RPO" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ProjectedScore$Data$4"
							+ "$txt_StatValue*GEOM*TEXT SET " + proj_score_rate[7] + "\0", print_writers);
				}
			}
			break;
		}
	}
	
	public void populateCountdown(PrintWriter print_writer,boolean is_this_updating,String data, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			switch (data.toUpperCase()) {
			case "START":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$Time*FUNCTION*TIMER SET START INVOKE;");
				break;
			case "PAUSE":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$Time*FUNCTION*TIMER SET PAUSE INVOKE;");
				break;
			case "CONTINUE":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$Time*FUNCTION*TIMER SET CONTINUE INVOKE;");
				break;
			case "STOP":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$Time*FUNCTION*TIMER SET STOP INVOKE;");
				break;	
			}
			break;
		}
	}
	
	public void populatePlayerMileStoneBs(PrintWriter print_writer,boolean is_this_updating,int playerId,String runs, String data, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			Player plyr = getPlayerFromMatchData(playerId, match);
			
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
						"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
						match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");
			}
			else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
						"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
						match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");

			}
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " Overs" + ";");

					if(inn.getInningNumber() == 1) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "CRR" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + inn.getRunRate() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "TARGET" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
					}
				}
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + runs + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead01 " + data.toUpperCase() + ";");
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			break;
		}
	}
	
	public void populatePlayerProfileBs(PrintWriter print_writer,boolean is_this_updating,String viz_scene,String Profile,String TypeofProfile,Statistics stats,List<Player> plyer, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		double average = 0;
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "T20 CAREER" + ";");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "T20-I CAREER" + ";");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "FIRST-CLASS CAREER" + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + ";");
			}

			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " Overs" + ";");

					if(inn.getInningNumber() == 1) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "CRR" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + inn.getRunRate() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetHead " + "TARGET" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTarget " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
					}
				}
			}
		Player plyr = getPlayerFromMatchData(stats.getPlayerID(), match);
		
		
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
					match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
					match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");
		}
		else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
					match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path +
					match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + plyr.getFull_name() + ";");

		}
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BOWLER:
			if(plyer.get(plyr.getPlayerId()-1).getBowlingStyle() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 " + 
						CricketFunctions.getbowlingstyle(plyer.get(plyr.getPlayerId()-1).getBowlingStyle()).toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + " " + ";");
			}
			
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20) || stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20) ||
					stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("PR")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "MATCHES" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + stats.getMatches() + ";");

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "WICKETS" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + stats.getWickets() + ";");

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "AVERAGE" + ";");
				if(stats.getRunsConceded() == 0) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + "-" + ";");
				}else {
					average = stats.getRunsConceded()/stats.getWickets();
					DecimalFormat df_bo = new DecimalFormat("0.00");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + df_bo.format(average) + ";");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "BEST" + ";");
				if(stats.getBestFigures().equalsIgnoreCase("0")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + "-" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + stats.getBestFigures() + ";");
				}
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.TEST)) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "MATCHES" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + stats.getMatches() + ";");

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "INNINGS" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + stats.getInnings() + ";");

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "WICKETS" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + stats.getWickets() + ";");

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "AVERAGE" + ";");
				if(stats.getRunsConceded() == 0) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + "-" + ";");
				}else {
					average = stats.getRunsConceded()/stats.getWickets();
					DecimalFormat df_bo = new DecimalFormat("0.00");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + df_bo.format(average) + ";");
				}
				
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.ODI)) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "MATCHES" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + stats.getMatches() + ";");

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "WICKETS" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + stats.getWickets() + ";");

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "AVERAGE" + ";");
				if(stats.getRunsConceded() == 0) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + "-" + ";");
				}else {
					average = stats.getRunsConceded()/stats.getWickets();
					DecimalFormat df_bo = new DecimalFormat("0.00");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + df_bo.format(average) + ";");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "BEST" + ";");
				if(stats.getBestFigures().equalsIgnoreCase("0")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + "-" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + stats.getBestFigures() + ";");
				}
			}
			
			break;	
		}
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 68.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

		}

	}
	public void populatePlayerProfileBat(List<PrintWriter> print_writers,boolean is_this_updating,Integer playerId, String Profile,
			String TypeofProfile, List<Tournament> this_series,Statistics stats, CricketService cricketService, MatchAllData match, 
			String session_selected_broadcaster,Configuration config) throws InterruptedException 
	{
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
				+ "$txt_Years*GEOM*TEXT SET \0", print_writers);
		
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Ident$Teams$Team1"
//				+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			if(Profile.equalsIgnoreCase("THISSERIES")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
						+ "$txt_Age*GEOM*TEXT SET " + "THIS SERIES" + "\0", print_writers);
			}else {
				if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
							+ "$txt_Age*GEOM*TEXT SET " + "T20 CAREER" + "\0", print_writers);
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
							+ "$txt_Age*GEOM*TEXT SET " + "T20I CAREER" + "\0", print_writers);
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
							+ "$txt_Age*GEOM*TEXT SET " + "T20 CAREER" + "\0", print_writers);
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
							+ "$txt_Age*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
							+ "$txt_Age*GEOM*TEXT SET " + stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0", print_writers);
					
				}
			}
			
			for(int i=1;i<=4;i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$" 
						+ i + "$select_Highlight*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
			
			

		Player plyr = getPlayerFromMatchData(playerId, match);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$FirstName"
				+ "$txt_FirstName*GEOM*TEXT SET " + plyr.getFirstname() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$LastName"
				+ "$txt_LastName*GEOM*TEXT SET " + (plyr.getSurname() != null ? plyr.getSurname() : "") + "\0", print_writers);
		
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$Team"
					+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1() + "\0", print_writers);
			
			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$PlayerImage"
						+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\" + "Left_2048\\" 
						+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$PlayerImage"
						+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\" + "Left_2048\\" 
						+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				
			} else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$PlayerImage"
						+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path 
						+ match.getSetup().getHomeTeam().getTeamName4() + "\\" + "Left_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$PlayerImage"
						+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path 
						+ match.getSetup().getHomeTeam().getTeamName4() + "\\" + "Left_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$Team"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0", print_writers);
			
			for(Player hs : match.getSetup().getHomeSquad()) {
				if(hs.getPlayerId() == plyr.getPlayerId()) {
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						if(hs.getBattingStyle().equalsIgnoreCase("LHB")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
									+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Batsman_Lefthand" + "\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
									+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Bat_Icon" + "\0", print_writers);
						}
						break;
					case CricketUtil.BOWLER:
						if(hs.getBowlingStyle() == null) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
									+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Bowler" + "\0", print_writers);
						}else {
							switch(hs.getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
										+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "FastBowler" + "\0", print_writers);
								break;
							case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
										+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "SpinBowler" + "\0", print_writers);
								break;
							case "ROB":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
										+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Off_Spin" + "\0", print_writers);
								break;
							case "RLB":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
										+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Leg_Spin" + "\0", print_writers);
								break;	
							}
						}
						break;
					}
				}
			}
		}
		else {
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$Team"
					+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1() + "\0", print_writers);
			
			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$PlayerImage"
						+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\" + "Left_2048\\" 
						+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$PlayerImage"
						+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\" + "Left_2048\\" 
						+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			} else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$PlayerImage"
						+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path +
						match.getSetup().getAwayTeam().getTeamName4() + "\\" + "Left_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$PlayerImage"
						+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path +
						match.getSetup().getAwayTeam().getTeamName4() + "\\" + "Left_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$Team"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0", print_writers);
			
			for(Player as : match.getSetup().getAwaySquad()) {
				if(as.getPlayerId() == plyr.getPlayerId()) {
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						if(as.getBattingStyle().equalsIgnoreCase("LHB")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
									+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Batsman_Lefthand" + "\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
									+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Bat_Icon" + "\0", print_writers);
						}
						break;
					case CricketUtil.BOWLER:
						if(as.getBowlingStyle() == null) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
									+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Bowler" + "\0", print_writers);
						}else {
							switch(as.getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
										+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "FastBowler" + "\0", print_writers);
								break;
							case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
										+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "SpinBowler" + "\0", print_writers);
								break;
							case "ROB":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
										+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Off_Spin" + "\0", print_writers);
								break;
							case "RLB":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
										+ "$img_Icon*TEXTURE*IMAGE SET " + icon_path + "Leg_Spin" + "\0", print_writers);
								break;	
							}
						}
						break;
					}
				}
			}
		}

		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			
			if(plyr.getBattingStyle() != null) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
						+ "$txt_BattingStyle*GEOM*TEXT SET " + plyr.getBattingStyle() + "\0", print_writers);	
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
						+ "$txt_BattingStyle*GEOM*TEXT SET \0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$1$Dehighlight"
					+ "$txt_StatHead*GEOM*TEXT SET " + "MATCHES" + "\0", print_writers);	
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$2$Dehighlight"
					+ "$txt_StatHead*GEOM*TEXT SET " + "RUNS" + "\0", print_writers);
			
			if(Profile.equalsIgnoreCase(CricketUtil.IT20)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
						+ "$txt_StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
			}else if(Profile.equalsIgnoreCase(CricketUtil.ODI) || Profile.equalsIgnoreCase(CricketUtil.TEST)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
						+ "$txt_StatHead*GEOM*TEXT SET " + "AVERAGE" + "\0", print_writers);
			}else if(Profile.equalsIgnoreCase("THISSERIES")) {
				if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
							+ "$txt_StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
				}else if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.ODI)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
							+ "$txt_StatHead*GEOM*TEXT SET " + "AVERAGE" + "\0", print_writers);
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
					+ "$txt_StatHead*GEOM*TEXT SET " + "BEST" + "\0", print_writers);	
			
			if(Profile.equalsIgnoreCase("THISSERIES")) {
				List<BestStats> top_batsman_beststats = new ArrayList<BestStats>();
				for (Tournament tourn : this_series) {
					for (BestStats bs : tourn.getBatsman_best_Stats()) {
						top_batsman_beststats.add(bs);
					}
				}
				Collections.sort(top_batsman_beststats, new CricketFunctions.PlayerBestStatsComparator());
				
				Tournament this_series_data = this_series.stream().filter(ts -> ts.getPlayerId() == playerId).findAny().orElse(null);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$1$Dehighlight"
						+ "$txt_StatValue*GEOM*TEXT SET " + this_series_data.getMatches() + "\0", print_writers);	
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$2$Dehighlight"
						+ "$txt_StatValue*GEOM*TEXT SET " + this_series_data.getRuns() + "\0", print_writers);
				
				if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
							+ "$txt_StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
					
					if(this_series_data.getBallsFaced() == 0 || this_series_data.getRuns()== 0) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
								+ "$txt_StatValue*GEOM*TEXT SET -\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
								+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(this_series_data.getRuns(), this_series_data.getBallsFaced(), 2) + "\0", print_writers);
					}
				}else if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.ODI)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
							+ "$txt_StatHead*GEOM*TEXT SET " + "AVERAGE" + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
							+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.getAverage(this_series_data.getInnings(), this_series_data.getNot_out(), this_series_data.getRuns(), 1, "-") + "\0", print_writers);
				}
				
				for (int j = 0; j <= top_batsman_beststats.size() - 1; j++) {
					if (top_batsman_beststats.get(j).getPlayerId() == playerId) {
						if (top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
									+ "$txt_StatValue*GEOM*TEXT SET " + top_batsman_beststats.get(j).getBestEquation() / 2 + "\0", print_writers);
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
									+ "$txt_StatValue*GEOM*TEXT SET " + (top_batsman_beststats.get(j).getBestEquation() - 1) / 2 + "*" + "\0", print_writers);
						}
						break;
					} else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
								+ "$txt_StatValue*GEOM*TEXT SET -\0", print_writers);
					}
				}
				
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$1$Dehighlight"
						+ "$txt_StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$2$Dehighlight"
						+ "$txt_StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0", print_writers);
				
				if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
					if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
								+ "$txt_StatValue*GEOM*TEXT SET -\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
								+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(stats.getRuns(), stats.getBallsFaced(), 0) + "\0", print_writers);
					}
				}else if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.ODI)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
							+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.getAverage(stats.getInnings(), stats.getNotOut(), stats.getRuns(), 1, "-") + "\0", print_writers);
				}
				
				if(stats.getBestScore().equalsIgnoreCase("0")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
							+ "$txt_StatValue*GEOM*TEXT SET -\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
							+ "$txt_StatValue*GEOM*TEXT SET " + stats.getBestScore() + "\0", print_writers);
				}
			}
			break;
		case CricketUtil.BOWLER:
			if(plyr.getBowlingStyle() != null) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
						+ "$txt_BattingStyle*GEOM*TEXT SET " + plyr.getBowlingStyle() + "\0", print_writers);	
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$PlayerInfo"
						+ "$txt_BattingStyle*GEOM*TEXT SET \0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$1$Dehighlight"
					+ "$txt_StatHead*GEOM*TEXT SET " + "MATCHES" + "\0", print_writers);	
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$2$Dehighlight"
					+ "$txt_StatHead*GEOM*TEXT SET " + "WICKETS" + "\0", print_writers);	
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
					+ "$txt_StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0", print_writers);	
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
					+ "$txt_StatHead*GEOM*TEXT SET " + "BEST" + "\0", print_writers);
			
			if(Profile.equalsIgnoreCase("THISSERIES")) {
				List<BestStats> top_bowler_beststats = new ArrayList<BestStats>();
				for (Tournament tourn : this_series) {
					for (BestStats bfig : tourn.getBowler_best_Stats()) {
						top_bowler_beststats.add(bfig);
					}
				}
				Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
				
				Tournament this_series_data = this_series.stream().filter(ts -> ts.getPlayerId() == playerId).findAny().orElse(null);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$1$Dehighlight"
						+ "$txt_StatValue*GEOM*TEXT SET " + this_series_data.getMatches() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$2$Dehighlight"
						+ "$txt_StatValue*GEOM*TEXT SET " + this_series_data.getWickets() + "\0", print_writers);
				
				if(this_series_data.getBallsBowled() == 0 && this_series_data.getRunsConceded() == 0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
							+ "$txt_StatValue*GEOM*TEXT SET " + "-" + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
							+ "$txt_StatValue*GEOM*TEXT SET " + 
							CricketFunctions.getEconomy(this_series_data.getRunsConceded(), this_series_data.getBallsBowled(), 1, "-") + "\0", print_writers);
				}
				
				for (int j = 0; j <= top_bowler_beststats.size() - 1; j++) {
					if (top_bowler_beststats.get(j).getPlayerId() == playerId) {
						if (top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
									+ "$txt_StatValue*GEOM*TEXT SET " + 
									((top_bowler_beststats.get(j).getBestEquation() / 1000) + 1) + "-"+ (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0", print_writers);
							
						} else if (top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
									+ "$txt_StatValue*GEOM*TEXT SET " + 
									(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-"+ Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0", print_writers);
							
						}
						break;
					} else if (top_bowler_beststats.get(j).getPlayerId() != playerId) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
								+ "$txt_StatValue*GEOM*TEXT SET " + "-" + "\0", print_writers);
					}
				}
			}else {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$1$Dehighlight"
						+ "$txt_StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$2$Dehighlight"
						+ "$txt_StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$3$Dehighlight"
						+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.getEconomy(stats.getRunsConceded(), stats.getBallsBowled(), 1, "-") + "\0", print_writers);
				if(stats.getBestFigures().equalsIgnoreCase("0")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
							+ "$txt_StatValue*GEOM*TEXT SET -\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Profile$Data$StatsGrp$4$Dehighlight"
							+ "$txt_StatValue*GEOM*TEXT SET " + stats.getBestFigures() + "\0", print_writers);
				}
			}
			break;
		}
		this.status = CricketUtil.SUCCESSFUL;
		}

	}
	public void populateThisOver(List<PrintWriter> print_writers, boolean is_this_updating, MatchAllData match, String session_selected_broadcaster, 
			Configuration config) throws InterruptedException {
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 31\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		if(is_this_updating == false) {
			for(int i=1;i<=9;i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + i + 
						"$Select_DataType*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + i + 
						"$Runs$select_LineNumber*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style4$txt_Header"
				+ "*GEOM*TEXT SET " + "THIS OVER" + "\0", print_writers);
		for(Inning inn : match.getMatch().getInning()) {
			if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$TotalScore$txt_ThisOverFigure"
						+ "*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, "-", false) + "\0", print_writers);
			}
		}
		
		this_data_str = new ArrayList<String>();
		this_data_str.add(String.join(",", 
			    new ArrayList<>(Arrays.asList(IndexController.matchstats.getOverData().getThisOverTxt().split(","))).stream()
		        .map(s -> s.replace("WIDE", "WD").replace("NO_BALL", "NB").replace("LEG_BYE", "LB").replace("BYE", "B")
		                   .replace("PENALTY", "PN").replace("LOG_WICKET", "W").replace("WICKET", "W"))
		        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {Collections.reverse(list); return list;}))
		        .toArray(new String[0])));
		int totalOverSize = 6;
		
		System.out.println(this_data_str);
		
		if(this_data_str.get(this_data_str.size()-1).split(",").length <= 9) {
			for(int iBall = 0; iBall < this_data_str.get(this_data_str.size()-1).split(",").length; iBall++) {
				switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
				case CricketUtil.DOT: case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE: 
				case CricketUtil.FOUR:case CricketUtil.SIX: case "W":
					switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
					case CricketUtil.DOT:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Select_DataType*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Runs$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Runs$1_Line$txt_Figures1*GEOM*TEXT SET 0\0", print_writers);
						break;
					case "W":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Select_DataType*FUNCTION*Omo*vis_con SET 3\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Wicket$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Wicket$1_Line$txt_Figures1*GEOM*TEXT SET W\0", print_writers);
						break;
					default:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Select_DataType*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Runs$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Runs$1_Line$txt_Figures1*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).split(",")[iBall] + "\0", print_writers);
						break;
					}
					break;
				default:
					if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().contains("BOUNDARY")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Select_DataType*FUNCTION*Omo*vis_con SET 2\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Boundaries$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Boundaries$1_Line$txt_Figures1*GEOM*TEXT SET " + (this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("4")?"4":"6") + 
								"\0", print_writers);
					}else if(!this_data_str.get(this_data_str.size()-1).isEmpty()) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
								"$Select_DataType*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						
						if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("+")) {
							String line1 = "",line2 = "";
							String[] Timeparts = this_data_str.get(this_data_str.size()-1).split(",")[iBall].split("\\+");
							if (Timeparts.length == 2) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" 
										+ (iBall+1) + "$Runs$2_Line$txt_Figures1*GEOM*TEXT SET " + Timeparts[0] + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" 
										+ (iBall+1) + "$Runs$2_Line$txt_Figures2*GEOM*TEXT SET " + Timeparts[1] + "\0", print_writers);
						    } else if (Timeparts.length == 3) {
						    	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" 
										+ (iBall+1) + "$Runs$2_Line$txt_Figures1*GEOM*TEXT SET " + Timeparts[0] + "\0", print_writers);
						    	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" 
										+ (iBall+1) + "$Runs$2_Line$txt_Figures2*GEOM*TEXT SET " + Timeparts[1] + "+" + Timeparts[2] + "\0", print_writers);
						    }
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" 
									+ (iBall+1) + "$Runs$select_LineNumber*FUNCTION*Omo*vis_con SET 2", print_writers);
							

							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" 
									+ (iBall+1) + "$Runs$2_Line$txt_Figures2*GEOM*TEXT SET " + line2.trim().toUpperCase() + "\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
									"$Runs$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							
							if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("PN")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" 
										+ (iBall+1) + "$Runs$1_Line$txt_Figures1*GEOM*TEXT SET " + "5PN" + "\0", print_writers);
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$Ball_" + (iBall+1) + 
										"$Runs$1_Line$txt_Figures1*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).split(",")[iBall] + "\0", print_writers);
							}
						}
						switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
						case "1B": case "2B": case "3B": case "4B": case "5B": case "6B":
						case "1LB": case "2LB": case "3LB": case "4LB": case "5LB": case "6LB":
							
							break;

						default:
							if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("NB") || 
									this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("WD")||
									this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("PN_Y")) {
								totalOverSize++;
							}
							break;
						}
					}
					break;
				}
			}
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$ThisOver$select_BallNumber"
				+ "*FUNCTION*Omo*vis_con SET " + totalOverSize + "\0", print_writers);
	}
	
	public void populatePlayerfreeText(PrintWriter print_writer,boolean is_this_updating,int team_id,String data1,String data2,int player_id , MatchAllData match,List<Player> allPlayer,
			List<Team> allTeams, String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":

			if(allTeams.get(allPlayer.get(player_id -1).getTeamId() - 1).getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName "+
						allTeams.get(allPlayer.get(player_id -1).getTeamId() - 1).getTeamName4().toUpperCase()+ ";");
			}else if(allTeams.get(team_id-1).getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName "+
						allTeams.get(allPlayer.get(player_id -1).getTeamId() - 1).getTeamName4().toUpperCase()+ ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName "+
						allTeams.get(allPlayer.get(player_id -1).getTeamId() - 1).getTeamName4().toUpperCase()+ ";");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamNameFull "+
					allTeams.get(allPlayer.get(player_id -1).getTeamId() - 1).getTeamName4().toUpperCase()+ ";");

			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
						allTeams.get(allPlayer.get(player_id-1).getTeamId() - 1).getTeamName4() + "\\Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				
			} else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ "\\\\" + config.getPrimaryIpAddress() + "\\\\" + local_photo_path
						+ allTeams.get(allPlayer.get(player_id-1).getTeamId() - 1).getTeamName4() + "\\"
						+ "Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			}
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
//					allTeams.get(allPlayer.get(player_id -1).getTeamId() - 1).getTeamName4() + "\\Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			if(allTeams.get(allPlayer.get(player_id -1).getTeamId() - 1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					allTeams.get(allPlayer.get(player_id -1).getTeamId() - 1).getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + data1 + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + data2 + ";");
//			if(data1 != null && data2 != null) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + data1 + ";");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + data2 + ";");
//			}else {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + " " + ";");
//			}
//			
//			if(data2 != null) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + data2 + ";");
//			}else {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
//			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			break;
		}
	}
	public void populatePlayerBatAndBowlStyle(List<PrintWriter> print_writers, int inning, int player_id, String whichType,MatchAllData match,String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			String which_role = "";
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 36\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			Player plyr = getPlayerFromMatchData(player_id, match);
			
			switch(whichType) {
			case "BAT":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == inning) {
						
						if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$PlayerImage"
									+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\Left_2048\\\\" 
									+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$PlayerImage"
									+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\Left_2048\\\\" 
									+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$PlayerImage"
									+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
									+ "\\\\Left_2048\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$PlayerImage"
									+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
									+ "\\\\Left_2048\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$FirstName"
								+ "$txt_FirstName*GEOM*TEXT SET " + plyr.getFirstname() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$LastName"
								+ "$txt_LastName*GEOM*TEXT SET " + (plyr.getSurname() != null ? plyr.getSurname() : "") + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$Data$Team"
								+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$Data$Team"
								+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1() + "\0", print_writers);
						
						if(plyr.getBattingStyle().equalsIgnoreCase("RHB")) {
							which_role = "Batsman";
						}else if(plyr.getBattingStyle().equalsIgnoreCase("LHB")) {
							which_role = "Batsman_Lefthand";
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$Data$"
								+ "PlayerStyle$img_Icon*TEXTURE*IMAGE SET " + icon_path + which_role + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$Data$"
								+ "PlayerStyle$txt_PlayerStyle*GEOM*TEXT SET " + CricketFunctions.getbattingstyle(plyr.getBattingStyle(),CricketUtil.FULL, 
										true, false).toUpperCase() + "\0", print_writers);
					}
				}
				break;
			case "BOWL":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == inning) {
						if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$PlayerImage"
									+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inn.getBowling_team().getTeamName4() + "\\\\Left_2048\\\\" 
									+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$PlayerImage"
									+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + local_photo_path + inn.getBowling_team().getTeamName4() + "\\\\Left_2048\\\\" 
									+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$PlayerImage"
									+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBowling_team().getTeamName4() 
									+ "\\\\Left_2048\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$PlayerImage"
									+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBowling_team().getTeamName4() 
									+ "\\\\Left_2048\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$FirstName"
								+ "$txt_FirstName*GEOM*TEXT SET " + plyr.getFirstname() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$LastName"
								+ "$txt_LastName*GEOM*TEXT SET " + (plyr.getSurname() != null ? plyr.getSurname() : "") + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$Data$Team"
								+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBowling_team().getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$Data$Team"
								+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBowling_team().getTeamName1() + "\0", print_writers);
						
						if(plyr.getBowlingStyle() == null) {
							which_role = "Bowler";
						}else {
							switch(plyr.getBowlingStyle()) {
							case "RFM": case "RF": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								which_role = "FastBowler";
								break;
							case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								which_role = "SpinBowler";
								break;
							case "ROB":
								which_role = "Off_Spin";
								break;
							case "RLB":
								which_role = "Leg_Spin";
								break;
							}
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$Data$"
								+ "PlayerStyle$img_Icon*TEXTURE*IMAGE SET " + icon_path + which_role + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStyle$Data$"
								+ "PlayerStyle$txt_PlayerStyle*GEOM*TEXT SET " + CricketFunctions.getbowlingstyle(plyr.getBowlingStyle()).toUpperCase() + "\0", print_writers);
					}
				}
				break;
			}
			break;
		}
	}
	
	public void populateWeather(List<PrintWriter> print_writers,boolean is_this_updating,String data1,String data2,String data3,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 32\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style4$txt_Header"
				+ "*GEOM*TEXT SET " + "WEATHER" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Weather$Temperature"
				+ "$txt_TeamperatureHead*GEOM*TEXT SET " + "TEMPERATURE" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Weather$Rain"
				+ "$txt_RainHead*GEOM*TEXT SET " + "CHANCES OF RAIN" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Weather$Humidity"
				+ "$txt_HumidityHead*GEOM*TEXT SET " + "HUMIDITY" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Weather$Temperature"
				+ "$txt_TeamperatureValue*GEOM*TEXT SET " + data1 + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Weather$Rain"
				+ "$txt_RainValue*GEOM*TEXT SET " + data2 + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Weather$Humidity"
				+ "$txt_HumidityValue*GEOM*TEXT SET " + data3 + "\0", print_writers);
	}
	
	public void populateExtraBoundries(PrintWriter print_writer,boolean is_this_updating, String text,
			MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (text.toUpperCase()) {
			case "FREETEXT":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "7" + ";");
				break;
			case "HUNDRED":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "3" + ";");
				break;	
			case "FIFTY":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "2" + ";");
				break;
			case "CATCH":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "4" + ";");
				break;
		}
		
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	public void populateImagefourThree(PrintWriter print_writer,boolean is_this_updating, String teamName, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
//		switch (teamName.toUpperCase()) {
//		case "ICC1":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "ICC SHOP  Static 1.1" + ".jpg" + ";");
//			break;
//		case "ICC2":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "ICC SHOP  Static 1.2 copy" + ".jpg" + ";");
//			break;
//		case "ICC3":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "ICC SHOP Static 1.2" + ".jpg" + ";");
//			break;
//		case "ROYAL_STAG":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Royal Stag" + ".jpg" + ";");
//			break;
//		case "JACOB":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Jacobs Greek Static - 1920x1080 px" + ".jpg" + ";");
//			break;
//		case "THUMSUP":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Thums-Up-Logo-(1)" + ".png" + ";");
//			break;	
//		case "ARAMCO_BLUE":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Aramco Blue 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "ARAMCO_GREEN":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Aramco Green 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "BOOKING":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Booking.com 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "DP_WORLD":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "DP World 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "INDUSLAND":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Induslnd Bank 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "MASTER_CARD":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "MasterCard 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "MRF_ZAPPER":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "MRF Zapper C1 Replay Screen Static  1536 X 1152" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "MRF_ZLF":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "MRF ZLX Replay Screen 1536 X 1152 Static" + ".jpg" + ";");
//			break;
//		case "NIUM":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "NIUM  1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "UPSTOCK":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Replay Screen_1536X1152" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "POLYCAB":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "REPLAY-SCREEN-1536-x-1152-px" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "NISSAN":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Nissan 4x3" + ".png" + ";");
//			break;
//		case "EMIRATES":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"4_3" + "\\" + "Emirates Fly Better 4x3" + ".png" + ";");
//			break;	
//		}
		
		
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	public void populateImagesixteenNine(PrintWriter print_writer,boolean is_this_updating, String teamName, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
//		switch (teamName.toUpperCase()) {
//		case "NISSAN":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Nissan" + ".png" + ";");
//			break;
//		case "EMIRATES":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Emirates Fly Better" + ".png" + ";");
//			break;
//		case "BIRA":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "BIRA" + ".png" + ";");
//			break;	
//		case "ICC1":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "ICC SHOP  Static 1.1" + ".jpg" + ";");
//			break;
//		case "ICC2":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "ICC SHOP  Static 1.2 copy" + ".jpg" + ";");
//			break;
//		case "ICC3":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "ICC SHOP Static 1.2" + ".jpg" + ";");
//			break;
//		case "ROYAL_STAG":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Royal Stag" + ".jpg" + ";");
//			break;
//		case "JACOB":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Jacobs Greek Static - 1920x1080 px" + ".jpg" + ";");
//			break;
//		case "THUMSUP":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Thums-Up-Logo-(1)" + ".png" + ";");
//			break;	
//		case "ARAMCO_BLUE":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Aramco Blue 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "ARAMCO_GREEN":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Aramco Green 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "BOOKING":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Booking.com 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "DP_WORLD":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "DP World 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "INDUSLAND":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Induslnd Bank 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "MASTER_CARD":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "MasterCard 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "MRF_ZAPPER":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "MRF Zapper C1 Replay Screen Static  1920 X 1080" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "MRF_ZLF":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "MRF ZLX Replay Screen 1920 X 1080 Static" + ".jpg" + ";");
//			break;
//		case "NIUM":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "NIUM  1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "UPSTOCK":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "Replay Screen_1920x1080" + CricketUtil.PNG_EXTENSION + ";");
//			break;
//		case "POLYCAB":
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + "REPLAY-SCREEN-1920-x-1080-px" + CricketUtil.PNG_EXTENSION + ";");
//			break;	
//		}
		
		
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateImageAuto(PrintWriter print_writer) throws InterruptedException, IOException {
		
//		loop_value = 0;
//		
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//				"16_9" + "\\" + "MRF ZLX Replay Screen 1920 X 1080 Static" + ".jpg" + ";");
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateSponsor(PrintWriter print_writer,String viz_scene, Sponsor ns ,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
//					"16_9" + "\\" + ns.getImagename() + ";");
				
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateFantasy(PrintWriter print_writer,String viz_scene, FantasyImages ns ,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + fantasy_path + 
//					 ns.getImagename() + ";");
				
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateFreeHit(List<PrintWriter> print_writers) throws InterruptedException, IOException {
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 18\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info1*GEOM*TEXT SET FREE-HIT\0", print_writers);
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
//				+ "$txt_Info2*GEOM*TEXT SET FREEHIT\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info2*GEOM*TEXT SET \0", print_writers);
	}
	
	private void populateHatTrick(List<PrintWriter> print_writers,String text) {
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 18\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		if(text.contains("BALL")) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
					+ "$txt_Info1*GEOM*TEXT SET " + "HAT-TRICK\r\n" + "BALL" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
					+ "$txt_Info2*GEOM*TEXT SET \0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
					+ "$txt_Info1*GEOM*TEXT SET " + "HAT-TRICK" + "\0", print_writers);
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
//					+ "$txt_Info2*GEOM*TEXT SET " + "HAT-TRICK" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
					+ "$txt_Info2*GEOM*TEXT SET \0", print_writers);
		}
	}
	
	public void populateFreeHit(PrintWriter print_writer,boolean is_this_updating,String scene, String data, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (data.toUpperCase()) {
		case "FOUR":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "FOUR" + ";");
			break;
		case "SIX":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "SIX" + ";");
			break;
		case "FREEHIT":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "FREE-HIT" + ";");
			break;
		case "DUCK":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "DUCK" + ";");
			break;	
		}
	
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	public void populateFour(List<PrintWriter> print_writers) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 18\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info1*GEOM*TEXT SET FOUR\0", print_writers);
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
//				+ "$txt_Info2*GEOM*TEXT SET FOUR\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info2*GEOM*TEXT SET \0", print_writers);
	}
	
	public void populateSix(List<PrintWriter> print_writers) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 18\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info1*GEOM*TEXT SET SIX\0", print_writers);
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
//				+ "$txt_Info2*GEOM*TEXT SET SIX\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info2*GEOM*TEXT SET \0", print_writers);
	}
	
	public void populateWicket(List<PrintWriter> print_writers) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 18\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info1*GEOM*TEXT SET WICKET\0", print_writers);
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
//				+ "$txt_Info2*GEOM*TEXT SET WICKET\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info2*GEOM*TEXT SET \0", print_writers);
	}
	
	public void populateWide(List<PrintWriter> print_writers) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 18\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info1*GEOM*TEXT SET WIDE\0", print_writers);
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
//				+ "$txt_Info2*GEOM*TEXT SET WIDE\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info2*GEOM*TEXT SET \0", print_writers);
	}
	
	public void populateDuck(List<PrintWriter> print_writers) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 18\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info1*GEOM*TEXT SET DUCK\0", print_writers);
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
//				+ "$txt_Info2*GEOM*TEXT SET DUCK\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Hattrick"
				+ "$txt_Info2*GEOM*TEXT SET \0", print_writers);
	}
	
	public void populateExtras(List<PrintWriter> print_writers, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 16\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		for(Inning inn : match.getMatch().getInning()) {
			if (inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$TeamLogo"
						+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$TeamLogo"
						+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$ExtrasHead"
						+ "$txt_ExtrasHead*GEOM*TEXT SET " + "EXTRAS" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$ExtrasValue"
						+ "$txt_ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$1"
						+ "$txt_StatHead*GEOM*TEXT SET " + "WIDE" + CricketFunctions.Plural(inn.getTotalWides()).toUpperCase() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$1"
						+ "$txt_StatValue*GEOM*TEXT SET " + inn.getTotalWides() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$2"
						+ "$txt_StatHead*GEOM*TEXT SET " + "NO BALL" + CricketFunctions.Plural(inn.getTotalNoBalls()).toUpperCase() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$2"
						+ "$txt_StatValue*GEOM*TEXT SET " + inn.getTotalNoBalls() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$3"
						+ "$txt_StatHead*GEOM*TEXT SET " + "BYE" + CricketFunctions.Plural(inn.getTotalByes()).toUpperCase() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$3"
						+ "$txt_StatValue*GEOM*TEXT SET " + inn.getTotalByes() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$4"
						+ "$txt_StatHead*GEOM*TEXT SET " + "LEG BYE" + CricketFunctions.Plural(inn.getTotalLegByes()).toUpperCase() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$4"
						+ "$txt_StatValue*GEOM*TEXT SET " + inn.getTotalLegByes() + "\0", print_writers);
				
				if(inn.getTotalPenalties() != 0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data"
							+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$5"
							+ "$txt_StatHead*GEOM*TEXT SET " + "PENALTIES" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data$5"
							+ "$txt_StatValue*GEOM*TEXT SET " + inn.getTotalPenalties() + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Extras$Data"
							+ "*FUNCTION*Omo*vis_con SET 3\0", print_writers);

				}
			}
		}
	}
	
	public void populateReview(List<PrintWriter> print_writers, boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws Exception 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			Review reviewRemaining = CricketFunctions.getReviewRemaining(match);
			String[] parts = reviewRemaining.getReviewStatus().split(",");
			
//			String text_to_return = "";
//			int lineIndex1 = 1;
//		    boolean found1 = false;
//			BufferedReader br = new BufferedReader(new FileReader(CricketUtil.CRICKET_DIRECTORY + "ICC_Reviews.txt"));
//		
//		    while( (text_to_return = br.readLine()) != null) {
//		        if(lineIndex1 == 1) {
//		        	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Reviews$Teams$1"
//							+ "$txt_ReviewData*GEOM*TEXT SET " + text_to_return.split(" ")[0] + "\0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Reviews$Teams$2"
//							+ "$txt_ReviewData*GEOM*TEXT SET " + text_to_return.split(" ")[1] + "\0", print_writers);
//					
//		            found1 = true;
//		            break;
//		        }
//		        lineIndex1++;
//		    }
//		    if(!found1) {
//		    	//System.out.println("Line Not There");
//		    }
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 26\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style4$txt_Header"
					+ "*GEOM*TEXT SET " + "REVIEWS REMAINING" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Reviews$Teams$1"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Reviews$Teams$2"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Reviews$Teams"
					+ "$Separator*GEOM*TEXT SET \0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Reviews$Teams$1"
					+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Reviews$Teams$2"
					+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName4() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Reviews$Teams$1"
					+ "$txt_ReviewData*GEOM*TEXT SET " + Integer.parseInt(parts[0]) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Reviews$Teams$2"
					+ "$txt_ReviewData*GEOM*TEXT SET " + Integer.parseInt(parts[1]) + "\0", print_writers);
			
			break;
		}
	}
	
	public void populateFreeText(List<PrintWriter> print_writers,boolean is_this_updating, NameSuper ns, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 17\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText$Flag"
					+ "*ACTIVE SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText"
					+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText$select_LineNumber"
					+ "$1$txt_Info*GEOM*TEXT SET " + ns.getFirstname() + "\0", print_writers);
			
			break;
		}
	}
	
	public void populateline2FreeText(List<PrintWriter> print_writers,boolean is_this_updating, String data1, String data2, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 17\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText$Flag"
					+ "*ACTIVE SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText"
					+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText$select_LineNumber"
					+ "$1$txt_Info*GEOM*TEXT SET " + data1 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText$select_LineNumber"
					+ "$2$txt_Info*GEOM*TEXT SET " + data2 + "\0", print_writers);
			break;
		}
	}
	public void populateImgline2FreeText(List<PrintWriter> print_writers,boolean is_this_updating, String data1, String data2,String data3,List<Team> team, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 17\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText$Flag"
					+ "*ACTIVE SET 1\0", print_writers);
			
			for(Team tm : team) {
				if(tm.getTeamId() == Integer.valueOf(data1)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText"
							+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + tm.getTeamBadge().toUpperCase() + "\0", print_writers);
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText"
					+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText$select_LineNumber"
					+ "$1$txt_Info*GEOM*TEXT SET " + data2 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$FreeText$select_LineNumber"
					+ "$2$txt_Info*GEOM*TEXT SET " + data3 + "\0", print_writers);
			
			break;
		}
	}
	
	public void populateBoundary(PrintWriter print_writer,boolean is_this_updating,String scene, String data, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (data.toUpperCase()) {
		case "FOUR":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "4" + ";");
			break;
		case "SIX":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "6" + ";");
			break;	
		}
	
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	public void populateTeamBoundary(List<PrintWriter> print_writers,boolean is_this_updating,int inning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 9\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == inning) {
					if(inn.getBatting_team().getTeamName4().equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName4())){
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Boundaries$TeamLogo"
								+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Boundaries$TeamLogo"
								+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName4() + "\0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Boundaries$PlayerGrp$1"
							+ "$txt_Name*GEOM*TEXT SET " + "FOURS" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Boundaries$PlayerGrp$1"
							+ "$txt_Runs*GEOM*TEXT SET " + inn.getTotalFours() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Boundaries$PlayerGrp$2"
							+ "$txt_Name*GEOM*TEXT SET " + "SIXES" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Boundaries$PlayerGrp$2"
							+ "$txt_Runs*GEOM*TEXT SET " + inn.getTotalSixes() + "\0", print_writers);
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Boundaries$Score"
							+ "$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, "-", false) + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Boundaries$Overs"
							+ "$txt_OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Boundaries$TeamLogo"
							+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
				}
			}
			break;
		}
	}
	
	public void populateRunRate(List<PrintWriter> print_writers,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 25\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase("YES") && inn.getInningNumber() == 2) {
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$RunRates$TeamLogo"
							+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamBadge() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$RunRates$TeamLogo"
							+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName4().toUpperCase() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$RunRates$Score"
							+ "$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, "-", false) + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$RunRates$Overs"
							+ "$txt_OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$RunRates$Data$1"
							+ "$txt_StatHead*GEOM*TEXT SET " + "CURRENT RUN RATE" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$RunRates$Data$1"
							+ "$txt_StatValue*GEOM*TEXT SET " + inn.getRunRate() + "\0", print_writers);
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$RunRates$Data$2"
							+ "$txt_StatHead*GEOM*TEXT SET " + "REQUIRED RUN RATE" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$RunRates$Data$2"
							+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0,
									CricketFunctions.GetTargetData(match).getRemaningBall(), 2, match) + "\0", print_writers);
					
				}
			}
			
			break;
		}
	}
	
	public void populateComparison(List<PrintWriter> print_writers,boolean is_this_updating ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 27\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			for (Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Comparison$Teams$1"
							+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBowling_team().getTeamBadge().toUpperCase() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Comparison$Teams$2"
							+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge().toUpperCase() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style4$txt_Header*GEOM*TEXT SET " 
							+ "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Comparison$Teams$1"
							+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBowling_team().getTeamName4().toUpperCase() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Comparison$Teams$2"
							+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName4().toUpperCase() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Comparison$Teams"
							+ "$Separator*GEOM*TEXT SET \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Comparison$Teams$1"
							+ "$txt_ReviewData*GEOM*TEXT SET " + CricketFunctions.compareInningData(match, "-", 1,match.getEventFile().getEvents()) + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Comparison$Teams$2"
							+ "$txt_ReviewData*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, "-", false) + "\0", print_writers);
				}
			}
			break;
		}
	}
	
	public void populatePhaseComparison(List<PrintWriter> print_writers,boolean is_this_updating ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			String phaseWiseScoreInn1 = "",In1_PP1 ="-",In1_PP2="-",In1_PP3="-", phaseWiseScoreInn2 = "",In2_PP1 ="-",In2_PP2="-",In2_PP3="-";
			Inning Inn1 = null, Inn2 = null;
			
			Inn1 = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == 1).findAny().orElse(null);
			Inn2 = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == 2).findAny().orElse(null);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 39\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			
			phaseWiseScoreInn1 = IndexController.matchstats.getHomeFirstPowerPlay().getTotalRuns()+","+IndexController.matchstats.getHomeFirstPowerPlay().getTotalWickets()+"_"+
					 IndexController.matchstats.getHomeSecondPowerPlay().getTotalRuns()+","+IndexController.matchstats.getHomeSecondPowerPlay().getTotalWickets()+"_"
					 +IndexController.matchstats.getHomeThirdPowerPlay().getTotalRuns()+","+IndexController.matchstats.getHomeThirdPowerPlay().getTotalWickets();
			
			if(Integer.valueOf(phaseWiseScoreInn1.split("_")[0].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScoreInn1.split("_")[0].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(Inn1.getTotalOvers(), Inn1.getTotalBalls())) > 0.0) {
					In1_PP1 = "0-0";
				}
			}else {
				In1_PP1 = phaseWiseScoreInn1.split("_")[0].split(",")[0]+"-"+phaseWiseScoreInn1.split("_")[0].split(",")[1];
			}
			if(Integer.valueOf(phaseWiseScoreInn1.split("_")[1].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScoreInn1.split("_")[1].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(Inn1.getTotalOvers(), Inn1.getTotalBalls())) > 6.0) {
					In1_PP2 = "0-0";
				}
			}else {
				In1_PP2 = phaseWiseScoreInn1.split("_")[1].split(",")[0]+"-"+phaseWiseScoreInn1.split("_")[1].split(",")[1];
			}
			if(Integer.valueOf(phaseWiseScoreInn1.split("_")[2].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScoreInn1.split("_")[2].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(Inn1.getTotalOvers(), Inn1.getTotalBalls())) > 15.0) {
					In1_PP3 = "0-0";
				}
			}else {
				In1_PP3 = phaseWiseScoreInn1.split("_")[2].split(",")[0]+"-"+phaseWiseScoreInn1.split("_")[2].split(",")[1];
			}
			
			
			phaseWiseScoreInn2 = IndexController.matchstats.getAwayFirstPowerPlay().getTotalRuns()+","+IndexController.matchstats.getAwayFirstPowerPlay().getTotalWickets()+"_"+
					 IndexController.matchstats.getAwaySecondPowerPlay().getTotalRuns()+","+IndexController.matchstats.getAwaySecondPowerPlay().getTotalWickets()+"_"
					 +IndexController.matchstats.getAwayThirdPowerPlay().getTotalRuns()+","+IndexController.matchstats.getAwayThirdPowerPlay().getTotalWickets();
			
			if(Integer.valueOf(phaseWiseScoreInn2.split("_")[0].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScoreInn2.split("_")[0].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(Inn2.getTotalOvers(), Inn2.getTotalBalls())) > 0.0) {
					In2_PP1 = "0-0";
				}
			}else {
				In2_PP1 = phaseWiseScoreInn2.split("_")[0].split(",")[0]+"-"+phaseWiseScoreInn2.split("_")[0].split(",")[1];
			}
			if(Integer.valueOf(phaseWiseScoreInn2.split("_")[1].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScoreInn2.split("_")[1].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(Inn2.getTotalOvers(), Inn2.getTotalBalls())) > 6.0) {
					In2_PP2 = "0-0";
				}
			}else {
				In2_PP2 = phaseWiseScoreInn2.split("_")[1].split(",")[0]+"-"+phaseWiseScoreInn2.split("_")[1].split(",")[1];
			}
			if(Integer.valueOf(phaseWiseScoreInn2.split("_")[2].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScoreInn2.split("_")[2].split(",")[1]) == 0) {
				if(Float.valueOf(CricketFunctions.OverBalls(Inn2.getTotalOvers(), Inn2.getTotalBalls())) > 15.0) {
					In2_PP3 = "0-0";
				}
			}else {
				In2_PP3 = phaseWiseScoreInn2.split("_")[2].split(",")[0]+"-"+phaseWiseScoreInn2.split("_")[2].split(",")[1];
			}
			
			
			
			switch (match.getSetup().getMatchType()) {
			case CricketUtil.ODI:
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$1"
						+ "$txt_StatHead*GEOM*TEXT SET " + "1-10 OVERS" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$2"
						+ "$txt_StatHead*GEOM*TEXT SET " + "11-40 OVERS" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$3"
						+ "$txt_StatHead*GEOM*TEXT SET " + "41-50 OVERS" + "\0", print_writers);
				break;
			default:
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$1"
						+ "$txt_StatHead*GEOM*TEXT SET " + "1-6 OVERS" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$2"
						+ "$txt_StatHead*GEOM*TEXT SET " + "7-15 OVERS" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$3"
						+ "$txt_StatHead*GEOM*TEXT SET " + "16-20 OVERS" + "\0", print_writers);
				break;
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$1"
					+ "$txt_StatValue*GEOM*TEXT SET " + In1_PP1 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$2"
					+ "$txt_StatValue*GEOM*TEXT SET " + In1_PP2 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$3"
					+ "$txt_StatValue*GEOM*TEXT SET " + In1_PP3 + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$1"
					+ "$txt_StatValue02*GEOM*TEXT SET " + In2_PP1 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$2"
					+ "$txt_StatValue02*GEOM*TEXT SET " + In2_PP2 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$Data$3"
					+ "$txt_StatValue02*GEOM*TEXT SET " + In2_PP3 + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$HomeTeam_Data$TeamLogo$"
					+ "Flag$img_Flag*TEXTURE*IMAGE SET " + logo_path + Inn1.getBatting_team().getTeamBadge().toUpperCase() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$HomeTeam_Data$TeamLogo$"
					+ "CountryName$txt_CountryName*GEOM*TEXT SET " + Inn1.getBatting_team().getTeamName4().toUpperCase() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$HomeTeam_Data$Score$"
					+ "txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(Inn1, "-", false) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$HomeTeam_Data$Overs$"
					+ "txt_OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(Inn1.getTotalOvers(), Inn1.getTotalBalls()) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$HomeTeam_Data$Overs$"
					+ "txt_Overs*GEOM*TEXT SET " + "OVERS" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$AwayTeam_Data$TeamLogo$"
					+ "Flag$img_Flag*TEXTURE*IMAGE SET " + logo_path + Inn2.getBatting_team().getTeamBadge().toUpperCase() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$AwayTeam_Data$TeamLogo$"
					+ "CountryName$txt_CountryName*GEOM*TEXT SET " + Inn2.getBatting_team().getTeamName4().toUpperCase() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$AwayTeam_Data$Score$"
					+ "txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(Inn2, "-", false) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$AwayTeam_Data$Overs$"
					+ "txt_OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(Inn2.getTotalOvers(), Inn2.getTotalBalls()) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore_Comparison$AwayTeam_Data$Overs$"
					+ "txt_Overs*GEOM*TEXT SET " + "OVERS" + "\0", print_writers);
			break;
		}
	}
	
	public void populateToss(List<PrintWriter> print_writers,boolean is_this_updating,MatchAllData session_match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 30\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			if(session_match.getSetup().getTossWinningTeam() == session_match.getSetup().getHomeTeamId()) {

				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Toss$Team"
						+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path +
						session_match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Toss$Team"
						+ "$txt_CountryName*GEOM*TEXT SET " + session_match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Toss$Info"
						+ "$txt_Info1*GEOM*TEXT SET " + "WON THE TOSS AND" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Toss$Info"
						+ "$txt_Info2*GEOM*TEXT SET " + "ELECTED TO " + session_match.getSetup().getTossWinningDecision().toUpperCase() + "\0", print_writers);
				
			}else {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Toss$Team"
						+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path +
						session_match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Toss$Team"
						+ "$txt_CountryName*GEOM*TEXT SET " + session_match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Toss$Info"
						+ "$txt_Info1*GEOM*TEXT SET " + "WON THE TOSS AND" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Toss$Info"
						+ "$txt_Info2*GEOM*TEXT SET " + "ELECTED TO " + session_match.getSetup().getTossWinningDecision().toUpperCase() + "\0", print_writers);
				
			}
			break;
		}
	}
	
	public void populateTeamName(List<PrintWriter> print_writers,boolean is_this_updating,String name, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 28\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			if(name.equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName4())){
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$TeamName$Team"
						+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$TeamName$Team"
						+ "$txt_CountryName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$TeamName$Team"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + name.toUpperCase() + "\0", print_writers);
			
			break;
		}
	}
	
	private void populateFallOfWickets(List<PrintWriter> print_writers,boolean is_this_updating, int team_id, int player_id, List<Player> allPlayer,List<Team> allTeams,
			MatchAllData match, String session_selected_broadcaster2, Configuration config) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOutQuick's inning is null";
		} else {
			this.status = "STILL";
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 6\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			for (Inning inn : match.getMatch().getInning()) {
				if (inn.getBattingTeamId() == team_id) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$Team"
							+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$Team"
							+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1() + "\0", print_writers);
					for (BattingCard bc : inn.getBattingCard()) {
						if (bc.getPlayerId() == player_id) {
							
							if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$PlayerImage"
										+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\Left_2048\\\\" 
										+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$PlayerImage"
										+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\Left_2048\\\\" 
										+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
								
							} else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$PlayerImage"
										+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
										+ "\\\\Left_2048\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$PlayerImage"
										+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
										+ "\\\\Left_2048\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
							}
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$FirstName"
									+ "$txt_FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$LastName"
									+ "$txt_LastName*GEOM*TEXT SET " + (bc.getPlayer().getSurname() != null ? bc.getPlayer().getSurname() : "") + "\0", print_writers);
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$Score"
									+ "$txt_Score*GEOM*TEXT SET " + bc.getRuns() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$Score"
									+ "$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + "\0", print_writers);
							
							if(bc.getHowOutText() != null && !bc.getHowOutText().isEmpty()) {
								if (bc.getHowOut().equalsIgnoreCase(CricketUtil.LBW) || bc.getHowOut().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutText() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
									
									if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data"
												+ "$FOW$txt_FOW*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data"
												+ "$FOW$txt_FOW_Runs*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + "\0", print_writers);
									}
									this.status = CricketUtil.SUCCESSFUL;
								}else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT) || bc.getHowOut().equalsIgnoreCase(CricketUtil.MANKAD)) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
									
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + "run out"+" (sub - "+ bc.getHowOutFielder().getTicker_name()+") " + "\0", print_writers);
										this.status = CricketUtil.SUCCESSFUL;
									}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")){
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + "run out"+" (sub)" + "\0", print_writers);
										this.status = CricketUtil.SUCCESSFUL;
									}else {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutText() + "\0", print_writers);
										this.status = CricketUtil.SUCCESSFUL;
									}

								} else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 2\0", print_writers);
									
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + "c (sub - "+bc.getHowOutFielder().getTicker_name()+") " + "\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut2*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0", print_writers);
										this.status = CricketUtil.SUCCESSFUL;
									}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + "c (sub)" + "\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
										this.status = CricketUtil.SUCCESSFUL;
									}else {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut2*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0", print_writers);
										this.status = CricketUtil.SUCCESSFUL;
									}
								} else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut2*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0", print_writers);
									this.status = CricketUtil.SUCCESSFUL;
								} else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut2*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0", print_writers);
									this.status = CricketUtil.SUCCESSFUL;
								}
							}else {
								if(bc.getHowOut() != null && !bc.getHowOut().isEmpty()) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
									
									if(bc.getHowOut().equalsIgnoreCase("timed_out")) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + "timed out" + "\0", print_writers);
									}else if(bc.getHowOut().equalsIgnoreCase("retired_hurt")) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + "RETIRED HURT" + "\0", print_writers);
									}else {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " +bc.getHowOut() + "\0", print_writers);
									}
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 2\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut1*GEOM*TEXT SET \0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
											+ "$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data"
										+ "$FOW$txt_FOW*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data"
										+ "$FOW$txt_FOW_Runs*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + "\0", print_writers);
								this.status = CricketUtil.SUCCESSFUL;
							}
							
							if (inn.getFallsOfWickets().size() > 0) {
								for(FallOfWicket fow : inn.getFallsOfWickets()) {
									if (fow.getFowPlayerID() == player_id) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data"
												+ "$FOW$txt_FOW*GEOM*TEXT SET " + "FALL OF WICKET" + "\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data"
												+ "$FOW$txt_FOW_Runs*GEOM*TEXT SET " + fow.getFowRuns() + "-" + fow.getFowNumber() + "\0", print_writers);
										break;
									}
								}	
							}
						}
					}
				}
			}
		}
	}
	public void populateQuickHowOut(List<PrintWriter> print_writers,boolean is_this_updating, MatchAllData match, Configuration config) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOutQuick's inning is null";
		} else {
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 6\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);

			for (Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$Team"
							+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$Team"
							+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1() + "\0", print_writers);

					for (BattingCard bc : inn.getBattingCard()) {
						if (inn.getFallsOfWickets().size() > 0) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data"
									+ "$FOW$txt_FOW*GEOM*TEXT SET " + "FALL OF WICKET" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data"
									+ "$FOW$txt_FOW_Runs*GEOM*TEXT SET " + inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowRuns() + 
										(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowNumber() < 10 ? "-" + 
											inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowNumber() : "") + "\0", print_writers);
							
							if (inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
								
								if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$PlayerImage"
											+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\Left_2048\\\\" 
											+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$PlayerImage"
											+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\Left_2048\\\\" 
											+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
								} else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$PlayerImage"
											+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
											+ "\\\\Left_2048\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$PlayerImage"
											+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
											+ "\\\\Left_2048\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$FirstName"
										+ "$txt_FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$LastName"
										+ "$txt_LastName*GEOM*TEXT SET " + (bc.getPlayer().getSurname() != null ? bc.getPlayer().getSurname() : "") + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$Score"
										+ "$txt_Score*GEOM*TEXT SET " + bc.getRuns() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$Score"
										+ "$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + "\0", print_writers);
								
								if(bc.getHowOutText() != null && !bc.getHowOutText().isEmpty()) {
									if (bc.getHowOut().equalsIgnoreCase(CricketUtil.LBW)) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutText() + "\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
										this.status = CricketUtil.SUCCESSFUL;
									}else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT) || bc.getHowOut().equalsIgnoreCase(CricketUtil.MANKAD)) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 1\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
										
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
													+ "$txt_HowOut1*GEOM*TEXT SET " + "run out"+" (sub - "+ bc.getHowOutFielder().getTicker_name()+") " + "\0", print_writers);
											this.status = CricketUtil.SUCCESSFUL;
										}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")){
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
													+ "$txt_HowOut1*GEOM*TEXT SET " + "run out"+" (sub)" + "\0", print_writers);
											this.status = CricketUtil.SUCCESSFUL;
										}else {
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
													+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutText() + "\0", print_writers);
											this.status = CricketUtil.SUCCESSFUL;
										}

									} else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$select_LineNumber*FUNCTION*Omo*vis_con SET 2\0", print_writers);
										
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
													+ "$txt_HowOut1*GEOM*TEXT SET " + "c (sub - "+bc.getHowOutFielder().getTicker_name()+") " + "\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
													+ "$txt_HowOut2*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0", print_writers);
											this.status = CricketUtil.SUCCESSFUL;
										}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")) {
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
													+ "$txt_HowOut1*GEOM*TEXT SET " + "c (sub)" + "\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
													+ "$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
											this.status = CricketUtil.SUCCESSFUL;
										}else {
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
													+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
													+ "$txt_HowOut2*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0", print_writers);
											this.status = CricketUtil.SUCCESSFUL;
										}
									} else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut2*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0", print_writers);
										this.status = CricketUtil.SUCCESSFUL;
									} else {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut1*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BatsmanOut$Data$HowOut"
												+ "$txt_HowOut2*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0", print_writers);
										this.status = CricketUtil.SUCCESSFUL;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void populateTargetFull(List<PrintWriter> print_writers,boolean is_this_updating, MatchAllData match, 
			String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 14\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$TeamLogo"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$TeamLogo"
					+ "$txt_CountryName*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$1"
					+ "$txt_StatHead*GEOM*TEXT SET " + "NEED" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$1"
					+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
					+ "$txt_Info*GEOM*TEXT SET " + "RUNS TO WIN" + "\0", print_writers);
			
			if (match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && match.getSetup().getMaxOvers() == 1) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
						+ "$txt_StatValue*GEOM*TEXT SET " + (match.getSetup().getMaxOvers() * 6) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
						+ "$txt_Info2*GEOM*TEXT SET " + "BALLS" + "\0", print_writers);
			} else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
						+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetOvers() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
						+ "$txt_Info2*GEOM*TEXT SET " + "OVERS" + (match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().isEmpty() ? 
						" (" + match.getSetup().getTargetType().toUpperCase() + ")": "") + "\0", print_writers);
			}
			break;
		}
	}
	
	public void populateTarget(List<PrintWriter> print_writers, boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 5\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 34\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			
			for (Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 1 && inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5$img_Flag"
							+ "*TEXTURE*IMAGE SET " + logo_path + inn.getBowling_team().getTeamName4() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5$txt_TeamName"
							+ "*GEOM*TEXT SET " + inn.getBowling_team().getTeamName1() + "\0", print_writers);
				}else if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5$img_Flag"
							+ "*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamName4() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5$txt_TeamName"
							+ "*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1() + "\0", print_writers);
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$1"
					+ "$txt_StatHead1*GEOM*TEXT SET " + "NEED" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$1"
					+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$1"
					+ "$txt_StatHead2*GEOM*TEXT SET " + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getTargetRuns())
					.toUpperCase() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$2"
					+ "$txt_StatHead1*GEOM*TEXT SET " + "FROM" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$2"
					+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetOvers() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$2"
					+ "$txt_StatHead2*GEOM*TEXT SET " + "OVERS" + (match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().isEmpty() ? 
							" ("+ match.getSetup().getTargetType().toUpperCase()+")" : "") + "\0", print_writers);
			
			int requiredRuns = match.getMatch().getInning().get(0).getTotalRuns() + 1;
			if (match.getSetup().getTargetRuns() != 0) {
				requiredRuns = match.getSetup().getTargetRuns();
			}

			if (requiredRuns <= 0) {
				requiredRuns = 0;
			}
			int requiredBalls = 0;
			if (match.getSetup().getTargetOvers() != null && !match.getSetup().getTargetOvers().trim().isEmpty()) {
				if (match.getSetup().getTargetOvers().contains(".")) {
					requiredBalls = ((Integer.valueOf(match.getSetup().getTargetOvers().split(".")[0]) * 6)
							+ Integer.valueOf(match.getSetup().getTargetOvers().split(".")[1]));
				} else {
					requiredBalls = ((Integer.valueOf(match.getSetup().getTargetOvers()) * 6));
				}
			} else {
				requiredBalls = ((match.getSetup().getMaxOvers()) * 6);
			}
			if (requiredBalls <= 0) {
				requiredBalls = 0;
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer*GEOM*TEXT SET \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer02*GEOM*TEXT SET " + "@" + 
					CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2, match) + " RUNS PER OVER \0", print_writers);
			break;
		}
	}
	
	public void populateTargetWithImgBs(List<PrintWriter> print_writers, boolean is_this_updating, MatchAllData match, List<Player>allPlayers, List<Team> teams,
			String session_selected_broadcaster, Configuration config) throws Exception {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 15\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			for(Player plyr : match.getSetup().getHomeSquad()) {
				if(plyr.getCaptainWicketKeeper() != null) {
					if(plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) || plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						
						if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$PlayerImage$1"
									+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\" + "Left_2048\\" 
									+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$PlayerImage$1"
									+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path 
									+ match.getSetup().getHomeTeam().getTeamName4() + "\\" + "Left_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}
					}
				}
			}
			
			for(Player plyr : match.getSetup().getAwaySquad()) {
				if(plyr.getCaptainWicketKeeper() != null) {
					if(plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) || plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						
						if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$PlayerImage$2"
									+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\" + "Right_2048\\" 
									+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$PlayerImage$2"
									+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path 
									+ match.getSetup().getAwayTeam().getTeamName4() + "\\" + "Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}
					}
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Team"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Team"
					+ "$txt_CountryName*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info1"
					+ "$txt_Info*GEOM*TEXT SET NEED\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$RunsGrp"
					+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info2"
					+ "$txt_Info*GEOM*TEXT SET " + "RUNS TO WIN" + "\0", print_writers);
			
			if (match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && match.getSetup().getMaxOvers() == 1) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info3"
						+ "$txt_Info*GEOM*TEXT SET " + "FROM " + (match.getSetup().getMaxOvers() * 6) + " BALLS" + "\0", print_writers);
			} else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info3"
						+ "$txt_Info*GEOM*TEXT SET " + "FROM " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" 
						+ (match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().isEmpty() ? " (" 
						+ match.getSetup().getTargetType().toUpperCase() + ")" : "") + "\0", print_writers);
			}
			
			int requiredRuns = CricketFunctions.GetTargetData(match).getTargetRuns();
			
			if (requiredRuns <= 0) {
				requiredRuns = 0;
			}
			int requiredBalls = Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers());
			
			if (requiredBalls <= 0) {
				requiredBalls = 0;
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info4"
					+ "$select_Info*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info4"
					+ "$txt_Info*GEOM*TEXT SET @" + CricketFunctions.generateRunRate(requiredRuns, requiredBalls, 0, 2, match) + " RUNS PER OVER" + "\0", print_writers);
			break;
		}
	}
	
	public void populateEquation(List<PrintWriter> print_writers,boolean is_this_updating, MatchAllData match, String broadcaster)
			throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 14\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$TeamLogo"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$TeamLogo"
					+ "$txt_CountryName*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$1"
					+ "$txt_StatHead*GEOM*TEXT SET " + "NEED" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$1"
					+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
					+ "$txt_Info*GEOM*TEXT SET " + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() 
					+ " TO WIN" + "\0", print_writers);
			
			if(match.getSetup().getTargetOvers() != null && match.getSetup().getTargetRuns() != 0) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
						+ "$txt_StatValue*GEOM*TEXT SET " + (CricketFunctions.GetTargetData(match).getRemaningBall() < 100 ? 
						CricketFunctions.GetTargetData(match).getRemaningBall(): CricketFunctions.OverBalls(0, CricketFunctions.GetTargetData(match).getRemaningBall())) 
						+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
						+ "$txt_Info2*GEOM*TEXT SET " + (CricketFunctions.GetTargetData(match).getRemaningBall() < 100 ? "BALL" + CricketFunctions.Plural(Integer.
							valueOf(CricketFunctions.GetTargetData(match).getRemaningBall())).toUpperCase(): "OVERS") + (match.getSetup().getTargetType() != null ? 
									" (" + match.getSetup().getTargetType().toUpperCase() + ")":"") + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
						+ "$txt_StatValue*GEOM*TEXT SET " + (CricketFunctions.GetTargetData(match).getRemaningBall() < 100 ? 
						CricketFunctions.GetTargetData(match).getRemaningBall(): CricketFunctions.OverBalls(0, CricketFunctions.GetTargetData(match).getRemaningBall())) 
						+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Equation$Data$2"
						+ "$txt_Info2*GEOM*TEXT SET " + (CricketFunctions.GetTargetData(match).getRemaningBall() < 100 ? "BALL" + CricketFunctions.Plural(Integer.
							valueOf(CricketFunctions.GetTargetData(match).getRemaningBall())).toUpperCase(): "OVERS") + "\0", print_writers);
			}
		}
	}
	
	public void populateEquationShort(List<PrintWriter> print_writers, boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 5\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 34\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5$img_Flag"
				+ "*TEXTURE*IMAGE SET " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5$txt_TeamName"
				+ "*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$1"
				+ "$txt_StatHead1*GEOM*TEXT SET " + "NEED" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$1"
				+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$1"
				+ "$txt_StatHead2*GEOM*TEXT SET " + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns())
				.toUpperCase() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$2"
				+ "$txt_StatHead1*GEOM*TEXT SET " + "FROM" + "\0", print_writers);
		if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$2"
					+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(0, CricketFunctions.GetTargetData(match).getRemaningBall()) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$2"
					+ "$txt_StatHead2*GEOM*TEXT SET " + "OVERS" + (match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().isEmpty() ? 
							" ("+ match.getSetup().getTargetType().toUpperCase()+")" : "") + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$2"
					+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getRemaningBall() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BigEquation$Data$2"
					+ "$txt_StatHead2*GEOM*TEXT SET " + "BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() 
					+ (match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().isEmpty()?" ("+ match.getSetup().getTargetType().toUpperCase()
					+")":"") + "\0", print_writers);
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer*GEOM*TEXT SET \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$Style1$txt_Footer02*GEOM*TEXT SET " + "@" + 
				CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 
						2, match) + " RUNS PER OVER \0", print_writers);

	}
	
	private void populateEquationWithImgBs(List<PrintWriter> print_writers, boolean is_this_updating, MatchAllData match, List<Player> allPlayers, List<Team> teams, 
			String broadcaster,Configuration config) throws InterruptedException {
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 15\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		if(is_this_updating == false) {
			for(Player plyr : match.getSetup().getHomeSquad()) {
				if(plyr.getCaptainWicketKeeper() != null) {
					if(plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) || plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						
						if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$PlayerImage$1"
									+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\" + "Left_2048\\" 
									+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
							
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$PlayerImage$1"
									+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path 
									+ match.getSetup().getHomeTeam().getTeamName4() + "\\" + "Left_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}
					}
				}
			}
			
			for(Player plyr : match.getSetup().getAwaySquad()) {
				if(plyr.getCaptainWicketKeeper() != null) {
					if(plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) || plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						
						if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$PlayerImage$2"
									+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\" + "Right_2048\\" 
									+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$PlayerImage$2"
									+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path 
									+ match.getSetup().getAwayTeam().getTeamName4() + "\\" + "Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}
					}
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Team"
					+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + "\0", print_writers);
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Team"
				+ "$txt_CountryName*GEOM*TEXT SET " + match.getMatch().getInning().get(1).getBatting_team().getTeamName1() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info1"
				+ "$txt_Info*GEOM*TEXT SET NEED\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$RunsGrp"
				+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info2"
				+ "$txt_Info*GEOM*TEXT SET " + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() 
				+ " TO WIN" + "\0", print_writers);
		
		if(match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().isEmpty()) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info3"
					+ "$txt_Info*GEOM*TEXT SET " + "FROM " + (CricketFunctions.GetTargetData(match).getRemaningBall() < 100 ? 
					CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
					CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase():
					CricketFunctions.OverBalls(0, CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS") + " (" 
					+ match.getSetup().getTargetType().toUpperCase() + ")" + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info3"
					+ "$txt_Info*GEOM*TEXT SET " + "FROM " + (CricketFunctions.GetTargetData(match).getRemaningBall() < 100 ? 
					CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
					CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase():
					CricketFunctions.OverBalls(0, CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS") + "\0", print_writers);
		}
		
		int requiredRuns = CricketFunctions.GetTargetData(match).getRemaningRuns();
		
		if (requiredRuns <= 0) {
			requiredRuns = 0;
		}
		int requiredBalls = CricketFunctions.GetTargetData(match).getRemaningBall();
		
		if (requiredBalls <= 0) {
			requiredBalls = 0;
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info4"
				+ "$select_Info*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Target$Data$Info4"
				+ "$txt_Info*GEOM*TEXT SET @" + CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2, match) + " RUNS PER OVER" + "\0", print_writers);
	}
	
	public void populateBowlerFig(PrintWriter print_writer,boolean is_this_updating, String string, Integer inning, int player_id,
			List<Player> allPlayer, List<Team> allTeams, MatchAllData match, String session_selected_broadcaster2,Configuration config) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {			
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "" + ";");

			
			for (Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == inning) {
					print_writer.println(
							"-1 RENDERER*TREE*$Main$Bug_ALL$Select$Single$All$Logo$TeamBageGrp$Select_BadgeType$img_Badges*TEXTURE*IMAGE SET "
									+ logo_path + inn.getBowling_team().getTeamBadge() + "\0");
					print_writer.println(
							"-1 RENDERER*TREE*$Main$Bug_ALL$Select$Single$All$Logo$TeamBageGrp$img_Badges*TEXTURE*IMAGE SET "
									+ logo_path + inn.getBowling_team().getTeamBadge() + "\0");

					if(inn.getBowling_team().getTeamBadge().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							inn.getBowling_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						if (boc.getPlayerId() == player_id) {
							
							if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										inn.getBowling_team().getTeamName4().toUpperCase() + "\\" + "Right_2048\\" + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							} else {
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\" + config.getPrimaryIpAddress()
												+ "\\\\" + local_photo_path
												+ inn.getBowling_team().getTeamName4().toUpperCase()
												+ "\\\\" + "Right_2048\\\\" + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
							
							if(boc.getPlayer().getFirstname() != null) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + boc.getPlayer().getFirstname() + ";");
								
								if(boc.getPlayer().getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + boc.getPlayer().getSurname() + ";");
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}else {
								if(boc.getPlayer().getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + boc.getPlayer().getSurname() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
									
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + boc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPScore " + boc.getWickets() + "-" + boc.getRuns() + ";");

							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + 
									(boc.getOvers() == 1 && boc.getBalls()==0 ? " OVER" : " OVERS")+ ";");
						}
					}
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
		}
	}
	public void populateBatterScore(PrintWriter print_writer,boolean is_this_updating, String string, Integer inning, Integer player_id,
			List<Player> allPlayer, List<Team> allTeams, MatchAllData match, String session_selected_broadcaster2,Configuration config) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = "STILL";
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "" + ";");	

			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getBattingTeamId() == inning) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					for(BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId() == player_id) {
							
							if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										inn.getBatting_team().getTeamName4() + "\\" + "Right_2048\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							} else {
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\" + 
										config.getPrimaryIpAddress() + "\\\\" + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + "Right_2048\\\\" 
										+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
							
							if(bc.getPlayer().getFirstname() != null) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFirstname() + ";");
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + bc.getPlayer().getSurname() + ";");
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}else {
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getSurname() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
									
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPScore " + bc.getRuns() +"*"+ ";");
							}
							else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPScore " + bc.getRuns() + ";");	
							}
							this.status = CricketUtil.SUCCESSFUL;
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + "OFF " + bc.getBalls() 
								+ " BALL" + CricketFunctions.Plural(bc.getBalls()).toUpperCase() + ";");
						}
					}
					
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			if(this.status.equalsIgnoreCase("STILL")) {
				print_writer.println("LAYER" + (current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				current_layer = 5- current_layer;
			}
		}
	}
	public void populateBowlerStats(List<PrintWriter> print_writers, boolean is_this_updating, Integer inning, Integer player_id, List<Player> allPlayer, 
			List<Team> allTeams, MatchAllData match, String session_selected_broadcaster2, Configuration config) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = "STILL";
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == inning) {
					
					if(is_this_updating == false) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$Team"
								+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBowling_team().getTeamBadge() + "\0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$Team"
							+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBowling_team().getTeamName1() + "\0", print_writers);
					
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getPlayerId() == player_id) {
							
							if(is_this_updating == false) {
								if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$PlayerImage"
											+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inn.getBowling_team().getTeamName4() + "\\\\Left_2048\\\\" 
											+ boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$PlayerImage"
											+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + local_photo_path + inn.getBowling_team().getTeamName4() + "\\\\Left_2048\\\\" 
											+ boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									
								} else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$PlayerImage"
											+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBowling_team().getTeamName4() 
											+ "\\\\Left_2048\\\\" + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$PlayerImage"
											+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBowling_team().getTeamName4() 
											+ "\\\\Left_2048\\\\" + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$FirstName"
										+ "$txt_FirstName*GEOM*TEXT SET " + boc.getPlayer().getFirstname() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$LastName"
										+ "$txt_LastName*GEOM*TEXT SET " + (boc.getPlayer().getSurname() != null ? boc.getPlayer().getSurname() : "") + "\0", print_writers);
							}
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$1"
									+ "$txt_StatHead*GEOM*TEXT SET " + (boc.getOvers() == 1 && boc.getBalls()==0 ? "OVER" : "OVERS") + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$2"
									+ "$txt_StatHead*GEOM*TEXT SET " + "WICKET" + CricketFunctions.Plural(boc.getWickets()).toUpperCase() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$3"
									+ "$txt_StatHead*GEOM*TEXT SET " + "RUN" + CricketFunctions.Plural(boc.getRuns()).toUpperCase() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$4"
									+ "$txt_StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$1"
									+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$2"
									+ "$txt_StatValue*GEOM*TEXT SET " + boc.getWickets() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$3"
									+ "$txt_StatValue*GEOM*TEXT SET " + boc.getRuns() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$4"
									+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.getEconomy(boc.getRuns(), ((boc.getOvers()*6)+boc.getBalls()), 2, "-") 
									+ "\0", print_writers);
							
							this.status = CricketUtil.SUCCESSFUL;
						}
					}
				}
			}
			
			if(this.status.equalsIgnoreCase("STILL")) {
				current_layer = 5-current_layer;
			}
		}
	}
	public void populateBatsmanStats(List<PrintWriter> print_writers,boolean is_this_updating,Integer team_id,Integer player_id,List<Player> allPlayer, 
			List<Team> allTeams,MatchAllData match,String session_selected_broadcaster2,Configuration config) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = "STILL";
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getBattingTeamId() == team_id) {
					
					if(is_this_updating == false) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$Team"
								+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$Team"
							+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1() + "\0", print_writers);
					
					for(BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId() == player_id) {
							
							if(is_this_updating == false) {
								if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$PlayerImage"
											+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\Left_2048\\\\" 
											+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$PlayerImage"
											+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\Left_2048\\\\" 
											+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									
								} else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$PlayerImage"
											+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
											+ "\\\\Left_2048\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$PlayerImage"
											+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
											+ "\\\\Left_2048\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$FirstName"
										+ "$txt_FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$LastName"
										+ "$txt_LastName*GEOM*TEXT SET " + (bc.getPlayer().getSurname() != null ? bc.getPlayer().getSurname() : "") + "\0", print_writers);
							}
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$1"
									+ "$txt_StatHead*GEOM*TEXT SET " + "RUN" + CricketFunctions.Plural(bc.getRuns()).toUpperCase() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$2"
									+ "$txt_StatHead*GEOM*TEXT SET " + "BALL" + CricketFunctions.Plural(bc.getBalls()).toUpperCase() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$3"
									+ "$txt_StatHead*GEOM*TEXT SET " + "4s/6s" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$4"
									+ "$txt_StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$1"
									+ "$txt_StatValue*GEOM*TEXT SET " + (bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT) ? bc.getRuns() + "*" : bc.getRuns()) 
									+ "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$2"
									+ "$txt_StatValue*GEOM*TEXT SET " + bc.getBalls() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$3"
									+ "$txt_StatValue*GEOM*TEXT SET " + bc.getFours() + "/" + bc.getSixes() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PlayerStat$Data$StatsGrp$4"
									+ "$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + "\0", print_writers);
							
							this.status = CricketUtil.SUCCESSFUL;
						}
					}
				}
			}
			
			if(this.status.equalsIgnoreCase("STILL")) {
				current_layer = 5-current_layer;
			}
		}
	}
	public void populatePartnership(List<PrintWriter> print_writers, boolean is_this_updating, List<Player> allPlayer, List<Team> allTeams, MatchAllData match,
			String session_selected_broadcaster, Configuration config) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 21\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		for (Inning inn : match.getMatch().getInning()) {
			if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$Team"
						+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style4$txt_Header"
						+ "*GEOM*TEXT SET " + CricketFunctions.ordinal(inn.getPartnerships().get(inn.getPartnerships().size() - 1).
							getPartnershipNumber()) + " WICKET PARTNERSHIP" + "\0", print_writers);
				
				if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerImage$1"
							+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\" + "Left_2048\\" 
							+ inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					
				} else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerImage$1"
							+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
							+ "\\" + "Left_2048\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION 
							+ "\0", print_writers);
				}
				
				if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerImage$2"
							+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + inn.getBatting_team().getTeamName4() + "\\" + "Right_2048\\" 
							+ inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				} else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerImage$2"
							+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + inn.getBatting_team().getTeamName4() 
							+ "\\" + "Right_2048\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION 
							+ "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerScore$1$"
						+ "txt_PlayerName*GEOM*TEXT SET " + inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getTicker_name() 
						+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerScore$2$"
						+ "txt_PlayerName*GEOM*TEXT SET " + inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getTicker_name() 
						+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PartnershipScore$"
						+ "txt_Runs*GEOM*TEXT SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "*" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PartnershipScore$"
						+ "txt_Balls*GEOM*TEXT SET " + "OFF " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerScore$1$"
						+ "txt_Runs*GEOM*TEXT SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerScore$1$"
						+ "txt_Balls*GEOM*TEXT SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerScore$2$"
						+ "txt_Runs*GEOM*TEXT SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Partnership$PlayerScore$2$"
						+ "txt_Balls*GEOM*TEXT SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + "\0", print_writers);
			}
		}
	}
	
	public void populateDlsParScore(List<PrintWriter> print_writers, MatchAllData match, List<DuckWorthLewis> dls) throws InterruptedException, IOException 
	{
		int balls = 0, overs = 0;
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 13\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		
		for(Inning inn : match.getMatch().getInning()) {
			if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES) && inn.getInningNumber() == 2) {
				overs = inn.getTotalOvers();
				balls = inn.getTotalBalls();
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$TeamLogo"
						+ "$txt_CountryName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$TeamLogo"
						+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inn.getBatting_team().getTeamBadge() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$Score" + 
						"$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, "-", false) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$Overs" + 
						"$txt_OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(overs, balls) + "\0", print_writers);
			}
		}
		
		this_data_str = new ArrayList<String>();
		for(int i = 0; i<= dls.size() -1;i++) {
			if(dls.get(i).getOver_left().split("\\.")[0].equalsIgnoreCase(String.valueOf(overs))) {
				for(int j=0;j<6;j++) {
					if(balls == j) {
						this_data_str.add(dls.get(i+j).getWkts_down());
						break;
					}
				}
				break;
			}
		}
		
		this_data_str.add(CricketFunctions.populateDls(match, CricketUtil.SHORT, Integer.valueOf(this_data_str.get(0))));
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$DLS_Score" + 
				"$txt_StatValue*GEOM*TEXT SET " + this_data_str.get(0) + "\0", print_writers);
		
		if(this_data_str.get(1).toUpperCase().contains("-")) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$Info"
					+ "$select_Info*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$Info" + 
					"$txt_Info1*GEOM*TEXT SET " + this_data_str.get(1).split("-")[0].toUpperCase() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$Info" + 
					"$txt_Info2*GEOM*TEXT SET " + this_data_str.get(1).split("-")[1].toUpperCase() + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$Info"
					+ "$select_Info*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$DLS_ParScore$Info" + 
					"$txt_Info1*GEOM*TEXT SET " + this_data_str.get(1).toUpperCase() + "\0", print_writers);
		}
	}
	public void populatePowerplay1(List<PrintWriter> print_writers, MatchAllData match) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 35\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$$PowerPlayField$FieldAll"
				+ "$select_FieldPlacing*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		if(match.getSetup().getMatchType().equalsIgnoreCase("ODI")) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PowerPlayField$Data$1"
					+ "$txt_Title*GEOM*TEXT SET PP1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PowerPlayField$Data$1"
					+ "$txt_Info*GEOM*TEXT SET OVERS 1 TO 10\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PowerPlayField$Data$1"
					+ "$txt_Title*GEOM*TEXT SET PP\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PowerPlayField$Data$1"
					+ "$txt_Info*GEOM*TEXT SET OVERS 1 TO 6\0", print_writers);
		}
	}
	public void populatePowerplay2(List<PrintWriter> print_writers) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 35\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$$PowerPlayField$FieldAll"
				+ "$select_FieldPlacing*FUNCTION*Omo*vis_con SET 2\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PowerPlayField$Data$1"
				+ "$txt_Title*GEOM*TEXT SET PP2\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PowerPlayField$Data$1"
				+ "$txt_Info*GEOM*TEXT SET OVERS 11 TO 40\0", print_writers);
	}
	public void populatePowerplay3(List<PrintWriter> print_writers) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 35\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$$PowerPlayField$FieldAll"
				+ "$select_FieldPlacing*FUNCTION*Omo*vis_con SET 3\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PowerPlayField$Data$1"
				+ "$txt_Title*GEOM*TEXT SET PP3\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PowerPlayField$Data$1"
				+ "$txt_Info*GEOM*TEXT SET OVERS 41 TO 50\0", print_writers);
	}
	private void populatePhaseBy(List<PrintWriter> print_writers, Integer inning_num, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		
		String phaseWiseScore = "",PP1 ="-",PP2="-",PP3="-";
		Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == inning_num).findAny().orElse(null);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 22\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		if(inning.getInningNumber() == 1) {
			phaseWiseScore = IndexController.matchstats.getHomeFirstPowerPlay().getTotalRuns()+","+IndexController.matchstats.getHomeFirstPowerPlay().getTotalWickets()+"_"+
							 IndexController.matchstats.getHomeSecondPowerPlay().getTotalRuns()+","+IndexController.matchstats.getHomeSecondPowerPlay().getTotalWickets()+"_"
							 +IndexController.matchstats.getHomeThirdPowerPlay().getTotalRuns()+","+IndexController.matchstats.getHomeThirdPowerPlay().getTotalWickets();
		}else if(inning.getInningNumber() == 2) {
			phaseWiseScore = IndexController.matchstats.getAwayFirstPowerPlay().getTotalRuns()+","+IndexController.matchstats.getAwayFirstPowerPlay().getTotalWickets()+"_"+
					 IndexController.matchstats.getAwaySecondPowerPlay().getTotalRuns()+","+IndexController.matchstats.getAwaySecondPowerPlay().getTotalWickets()+"_"
					 +IndexController.matchstats.getAwayThirdPowerPlay().getTotalRuns()+","+IndexController.matchstats.getAwayThirdPowerPlay().getTotalWickets();
		}
		
		if(Integer.valueOf(phaseWiseScore.split("_")[0].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore.split("_")[0].split(",")[1]) == 0) {
			if(Float.valueOf(CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls())) > 0.0) {
				PP1 = "0-0";
			}
		}else {
			PP1 = phaseWiseScore.split("_")[0].split(",")[0]+"-"+phaseWiseScore.split("_")[0].split(",")[1];
		}
		if(Integer.valueOf(phaseWiseScore.split("_")[1].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore.split("_")[1].split(",")[1]) == 0) {
			if(Float.valueOf(CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls())) > 6.0) {
				PP2 = "0-0";
			}
		}else {
			PP2 = phaseWiseScore.split("_")[1].split(",")[0]+"-"+phaseWiseScore.split("_")[1].split(",")[1];
		}
		if(Integer.valueOf(phaseWiseScore.split("_")[2].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore.split("_")[2].split(",")[1]) == 0) {
			if(Float.valueOf(CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls())) > 15.0) {
				PP3 = "0-0";
			}
		}else {
			PP3 = phaseWiseScore.split("_")[2].split(",")[0]+"-"+phaseWiseScore.split("_")[2].split(",")[1];
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$TeamLogo"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$TeamLogo"
				+ "$txt_CountryName*GEOM*TEXT SET " + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Score"
				+ "$txt_Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inning, "-", false) + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Overs"
				+ "$txt_Overs*GEOM*TEXT SET " + (inning.getTotalOvers() == 1 && inning.getTotalBalls() == 0?"OVER":"OVERS") + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Overs"
				+ "$txt_OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) + "\0", print_writers);
		
		switch (match.getSetup().getMatchType()) {
		case CricketUtil.ODI:
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Data$1"
					+ "$txt_StatHead*GEOM*TEXT SET " + "1-10 OVERS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Data$2"
					+ "$txt_StatHead*GEOM*TEXT SET " + "11-40 OVERS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Data$3"
					+ "$txt_StatHead*GEOM*TEXT SET " + "41-50 OVERS" + "\0", print_writers);
			break;
		default:
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Data$1"
					+ "$txt_StatHead*GEOM*TEXT SET " + "1-6 OVERS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Data$2"
					+ "$txt_StatHead*GEOM*TEXT SET " + "7-15 OVERS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Data$3"
					+ "$txt_StatHead*GEOM*TEXT SET " + "16-20 OVERS" + "\0", print_writers);
			break;
		}
	
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Data$1"
				+ "$txt_StatValue*GEOM*TEXT SET " + PP1 + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Data$2"
				+ "$txt_StatValue*GEOM*TEXT SET " + PP2 + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$PhaseScore$Data$3"
				+ "$txt_StatValue*GEOM*TEXT SET " + PP3 + "\0", print_writers);
			
	}
	
	public void populateSixDistance(List<PrintWriter> print_writers,boolean is_this_updating, String data, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
					+ "*FUNCTION*Omo*vis_con SET 3\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
					+ "*FUNCTION*Omo*vis_con SET 29\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style3$txt_Header"
					+ "*GEOM*TEXT SET " + "SIX DISTANCE" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$SixDistance$txt_DistanceValue"
					+ "*GEOM*TEXT SET " + data + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$SixDistance$txt_DistanceUnit"
					+ "*GEOM*TEXT SET METERS\0", print_writers);
			break;
		}
	}
	
	public void populateMileStone(List<PrintWriter> print_writers,boolean is_this_updating,int team_id,String data1,String data2,String data3,int player_id , 
			MatchAllData match,List<Player> allPlayer,List<Team> allTeams, String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 5\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		Player player = allPlayer.stream().filter(plyr -> plyr.getPlayerId() == player_id).findAny().orElse(null);
		Team team = allTeams.stream().filter(tm -> tm.getTeamId() == team_id).findAny().orElse(null);
		
		if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Milestone$PlayerImage"
					+ "$img_Player*TEXTURE*IMAGE SET " + local_photo_path + team.getTeamName4() + "\\\\Left_2048\\\\" + player.getPhoto() 
					+ CricketUtil.PNG_EXTENSION + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Milestone$PlayerImage"
					+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + local_photo_path + team.getTeamName4() + "\\\\Left_2048\\\\" + player.getPhoto() 
					+ CricketUtil.PNG_EXTENSION + "\0", print_writers);
			
		} else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Milestone$PlayerImage"
					+ "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + team.getTeamName4() + "\\\\Left_2048\\\\" 
					+ player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Milestone$PlayerImage"
					+ "$img_Player_Shadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + photo_path + team.getTeamName4() 
					+ "\\\\Left_2048\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$FirstName"
				+ "$txt_FirstName*GEOM*TEXT SET " + player.getFirstname() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style2$LastName"
				+ "$txt_LastName*GEOM*TEXT SET " + (player.getSurname() != null ? player.getSurname() : "") + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Milestone$Data$Team"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + team.getTeamBadge() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Milestone$Data$Team"
				+ "$txt_CountryName*GEOM*TEXT SET " + team.getTeamName1() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Milestone$Data$Info"
				+ "$txt_Info*GEOM*TEXT SET " + (data3 != null ? data3 : "") + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Milestone$Data$Score"
				+ "$txt_Score*GEOM*TEXT SET " + (data1 != null ? data1 : "") + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Milestone$Data$Balls"
				+ "$txt_Balls*GEOM*TEXT SET " + (data2 != null ? data2 : "") + "\0", print_writers);
		
	}
	
	public void populateSpeed(List<PrintWriter> print_writers, String speed) throws InterruptedException, IOException 
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 3\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 8\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style3$txt_Header"
				+ "*GEOM*TEXT SET " + "LAST BALL SPEED" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BallSpeed$txt_SpeedValue"
				+ "*GEOM*TEXT SET " + speed + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$BallSpeed$txt_Unit"
				+ "*GEOM*TEXT SET KPH\0", print_writers);
	}
	public void populateLineup(List<PrintWriter> print_writers,int teamID,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		List<Player> player = (match.getSetup().getHomeTeamId() == teamID ? match.getSetup().getHomeSquad() : match.getSetup().getAwaySquad());
		Team team = (match.getSetup().getHomeTeamId() == teamID ? match.getSetup().getHomeTeam() : match.getSetup().getAwayTeam());
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 3\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style1$txt_Header"
				+ "*GEOM*TEXT SET PLAYING XI\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$TeamLogo"
				+ "$txt_CountryName*GEOM*TEXT SET " + team.getTeamName4() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$TeamLogo"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + team.getTeamBadge() + "\0", print_writers);
		
		row_id = 0;
		for(Player plyr : player) {
			row_id ++;
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$Rows$" + row_id + 
					"$txt_Name*GEOM*TEXT SET " + plyr.getFull_name() + "\0", print_writers);
			
			if(plyr.getRole().equalsIgnoreCase("Batsman") || plyr.getRole().equalsIgnoreCase("Bat/Keeper")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$Rows$" + row_id + 
						"$txt_Role*GEOM*TEXT SET " + "BATTER" + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$Rows$" + row_id + 
						"$txt_Role*GEOM*TEXT SET " + plyr.getRole().toUpperCase() + "\0", print_writers);
			}
			
			
			if(plyr.getCaptainWicketKeeper() != null && !plyr.getCaptainWicketKeeper().isEmpty()) {
				if(plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$Rows$" + row_id + 
							"$txt_Captain*GEOM*TEXT SET " + "(C)" + "\0", print_writers);
				}else if(plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$Rows$" + row_id + 
							"$txt_Captain*GEOM*TEXT SET " + "(C)" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$Rows$" + row_id + 
							"$txt_Role*GEOM*TEXT SET " + "KEEPER" + "\0", print_writers);
					
				}else if(plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)){
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$Rows$" + row_id + 
							"$txt_Captain*GEOM*TEXT SET \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$Rows$" + row_id + 
							"$txt_Role*GEOM*TEXT SET " + "KEEPER" + "\0", print_writers);
				}
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Team$Rows$" + row_id + 
						"$txt_Captain*GEOM*TEXT SET \0", print_writers);
			}
		}
	}
	
	public void populateLineupImage(List<PrintWriter> print_writers, boolean is_this_updating, int teamID, String Data, MatchAllData match, String session_selected_broadcaster,
			Configuration config, List<Statistics> statistics, List<HeadToHeadPlayer> head_to_head, CricketService cricketService) throws InterruptedException, IOException 
	{
		int row=0;
		String which_role = "",outPlayerName="";
		Statistics stat = null;
		
		List<Player> player = (match.getSetup().getHomeTeamId() == teamID ? match.getSetup().getHomeSquad() : match.getSetup().getAwaySquad());
		Team team = (match.getSetup().getHomeTeamId() == teamID ? match.getSetup().getHomeTeam() : match.getSetup().getAwayTeam());
		
		String MatchFileName = null;
		PlayerId = new ArrayList<Integer>();
		PlayerIdIn = new ArrayList<Integer>();
		System.out.println(head_to_head.size());
		if(head_to_head.size() > 1) {
			for (int i = head_to_head.size() - 1; i >= 0; i--) {
			    if (head_to_head.get(i).getTeam().getTeamId() == teamID) {

			    	if (MatchFileName == null) {
			    		MatchFileName = head_to_head.get(i).getMatchFileName(); 
			        }
			        if (!head_to_head.get(i).getMatchFileName().equalsIgnoreCase(MatchFileName)) {
			            break;
			        }
			    }
			}
		}else {
			MatchFileName = match.getMatch().getMatchFileName();
		}
		if(MatchFileName != null) {
			Setup setup = new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + 
					MatchFileName), Setup.class);
			
			for(Player headToHead : (setup.getHomeTeamId()==teamID ? setup.getHomeSquad():setup.getAwaySquad())) {
				boolean playerFound = false;
				for (Player ply : player) {
		    	    if(ply.getPlayerId() == headToHead.getPlayerId()) {
		    	    	playerFound = true;
		    	    	break;
		    	    }
		    	}
		        if (!playerFound) {
		        	PlayerId.add(headToHead.getPlayerId());
		        }else {
		        	PlayerIdIn.add(headToHead.getPlayerId());
		        }
			}
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 5\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 38\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5"
				+ "$txt_TeamName*GEOM*TEXT SET " + team.getTeamName1() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style5"
				+ "$img_Flag*TEXTURE*IMAGE SET " + logo_path + team.getTeamBadge() + "\0", print_writers);
	
		outPlayerName = cricketService.getAllPlayer().stream().filter(plyr -> PlayerId.contains(plyr.getPlayerId())).map(Player::getTicker_name)
				.collect(Collectors.joining(", "));
		System.out.println("outPlayerName-" + outPlayerName+"0");
		
		if(!outPlayerName.trim().isEmpty()) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$Footer$Out_Arrow"
					+ "*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$Footer$txt_Info"
					+ "*GEOM*TEXT SET " + outPlayerName + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$Footer$Out_Arrow"
					+ "*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$Footer$txt_Info"
					+ "*GEOM*TEXT SET " + CricketFunctions.generateTossResult(match, CricketUtil.FULL, CricketUtil.FIELD, CricketUtil.FULL, 
							CricketUtil.CHOSE).toUpperCase() + "\0", print_writers);
		}
		
		for(Player plyr : player) {
			row ++;
			
			stat = statistics.stream().filter(st -> st.getPlayerID() == plyr.getPlayerId() && st.getStatsTypeId() == 3).findAny().orElse(null);				
			if(stat != null) {
				stat.setStats_type(cricketService.getStatsType(stat.getStatsTypeId()));
				if(stat.getStats_type().getStatsShortName().equalsIgnoreCase("IT20")) {
					stat = CricketFunctions.updateTournamentWithH2h(stat, head_to_head, match, CricketUtil.FULL);
					stat = CricketFunctions.updateStatisticsWithMatchData(stat, match, CricketUtil.FULL);
				}
			}
			
			if(!PlayerIdIn.isEmpty()) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$Status$select_Arrow*FUNCTION*Omo*vis_con SET " + (!PlayerIdIn.contains(plyr.getPlayerId())?"1":"0") + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$Status$select_Arrow*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
			
			if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$img_Player*TEXTURE*IMAGE SET " + local_photo_path + team.getTeamBadge() + "\\\\Left_2048\\\\" + plyr.getPhoto() + 
						CricketUtil.PNG_EXTENSION + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + photo_path + team.getTeamBadge() + "\\\\Left_2048\\\\" + 
						plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			}

			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
					"$DataAll$txt_Name*GEOM*TEXT SET " + plyr.getTicker_name() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
					"$DataAll$Stat$txt_StatHead*GEOM*TEXT SET " + Data + ":\0", print_writers);
			switch (Data) {
			case "IN AT":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$Stat$txt_StatValue*GEOM*TEXT SET " + row + "\0", print_writers);
				break;
			case "MATCHES":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$Stat$txt_StatValue*GEOM*TEXT SET " + (stat.getMatches() > 0 ? stat.getMatches():"-") + "\0", print_writers);
				break;
			case "RUNS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$Stat$txt_StatValue*GEOM*TEXT SET " + (stat.getRuns() > 0 ? stat.getRuns():"-") + "\0", print_writers);	
				break;
			case "STRIKE RATE":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$Stat$txt_StatValue*GEOM*TEXT SET " + (stat != null ? CricketFunctions.generateStrikeRate(stat.getRuns(), 
						stat.getBallsFaced(), 0):"-") + "\0", print_writers);
				break;
			case "WICKETS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$Stat$txt_StatValue*GEOM*TEXT SET " + (stat.getWickets() > 0 ? stat.getWickets():"-") + "\0", print_writers);	
				break;
			case "ECONOMY":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$Stat$txt_StatValue*GEOM*TEXT SET " + (stat != null ? CricketFunctions.getEconomy(stat.getRunsConceded(), 
								stat.getBallsBowled(), 2, "-"):"-") + "\0", print_writers);	
				break;
			}
			
			if(plyr.getRole().equalsIgnoreCase("BATSMAN") || plyr.getRole().equalsIgnoreCase("BAT/KEEPER")) {
				if(plyr.getBattingStyle().equalsIgnoreCase("RHB")) {
					which_role = "Batsman";
				}else if(plyr.getBattingStyle().equalsIgnoreCase("LHB")) {
					which_role = "Batsman_Lefthand";
				}
			}else if(plyr.getRole().equalsIgnoreCase("BOWLER")) {
				if(plyr.getBowlingStyle() == null) {
					which_role = "Bowler";
				}else {
					switch(plyr.getBowlingStyle()) {
					case "RFM": case "RF": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
						which_role = "FastBowler";
						break;
					case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
						which_role = "SpinBowler";
						break;
					case "ROB":
						which_role = "Off_Spin";
						break;
					case "RLB":
						which_role = "Leg_Spin";
						break;
					}
				}
			}else if(plyr.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
				if(plyr.getBowlingStyle() == null) {
					if(plyr.getBattingStyle().equalsIgnoreCase("LHB")) {
						which_role = "Off_Spin_Allrounder_Left";
					}else {
						which_role = "FastBowlerAllrounder";
					}
				}else {
					switch (plyr.getBowlingStyle().toUpperCase()) {
					case "RFM": case "RF": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
						if(plyr.getBattingStyle().equalsIgnoreCase("LHB")) {
							which_role = "Pace_BowlerAllrounerLeftHand";
						}else {
							which_role = "FastBowlerAllrounder";
						}
						break;

					case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
						if(plyr.getBattingStyle().equalsIgnoreCase("LHB")) {
							which_role = "Off_Spin_Allrounder_Left";
						}else {
							which_role = "Off_Spin_Bat";
						}
						break;
					}
				}
			}
			
			if(plyr.getCaptainWicketKeeper() != null && !plyr.getCaptainWicketKeeper().isEmpty()) {
				if(plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
							"$DataAll$Status$select_Captain*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				}else if(plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
							"$DataAll$Status$select_Captain*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					which_role = "Keeper";
					
				}else if(plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
							"$DataAll$Status$select_Captain*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					which_role = "Keeper";
				}
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
						"$DataAll$Status$select_Captain*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$LineUp$PlayersAll$" + row + 
					"$DataAll$img_Role*TEXTURE*IMAGE SET " + icon_path + which_role + "\0", print_writers);	
		}
		
	}
	
	public void popualteResult(List<PrintWriter> print_writers, MatchAllData match, String broadcaster) {
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 37\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		String TeamName = "",Result = "";
		this.status = CricketUtil.UNSUCCESSFUL;
		
		if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 1 && (match.getMatch().getInning().get(1).getTotalWickets() >= 10
				|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0)) {
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Team$img_Flag"
					+ "*ACTIVE SET 0\0", print_writers);
			
			if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$CountryName"
						+ "$txt_CountryName*GEOM*TEXT SET " + CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "", broadcaster, 
								false).getTargetOrResult().toUpperCase() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Info$txt_Info1"
						+ "*GEOM*TEXT SET \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Info$txt_Info2"
						+ "*GEOM*TEXT SET \0", print_writers);
			}else {
				TeamName = CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "",broadcaster,false).getTargetOrResult().split("-")[0];
				Result = CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "",broadcaster,false).getTargetOrResult().split("-")[1];
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$CountryName"
						+ "$txt_CountryName*GEOM*TEXT SET " + TeamName.toUpperCase() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Info$txt_Info1"
						+ "*GEOM*TEXT SET " + Result.split("by")[0].toUpperCase() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Info$txt_Info2"
						+ "*GEOM*TEXT SET " + "BY" + Result.split("by")[1].toUpperCase() + "\0", print_writers);
			}
			
			this.status = CricketUtil.SUCCESSFUL;
			
		}else if (CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10
				|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
			
			if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
				TeamName = CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,false).getTargetOrResult().split("win")[0];
				Result = CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,false).getTargetOrResult().split("win")[1];
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Team$img_Flag"
						+ "*ACTIVE SET 1\0", print_writers);
				
				if(match.getSetup().getHomeTeam().getTeamName1().contains(TeamName.trim())) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Team$img_Flag"
							+ "*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0", print_writers);
				}
				else if(match.getSetup().getAwayTeam().getTeamName1().contains(TeamName.trim())) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Team$img_Flag"
							+ "*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$CountryName"
						+ "$txt_CountryName*GEOM*TEXT SET " + TeamName.toUpperCase() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Info$txt_Info1"
						+ "*GEOM*TEXT SET " + "WIN" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Info$txt_Info2"
						+ "*GEOM*TEXT SET " + Result.toUpperCase() + "\0", print_writers);
			}else {
				TeamName = CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,false).getTargetOrResult().split("win by")[0];
				Result = CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,false).getTargetOrResult().split("win by")[1];
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Team$img_Flag"
						+ "*ACTIVE SET 1\0", print_writers);
				
				if(match.getSetup().getHomeTeam().getTeamName1().contains(TeamName.trim())) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Team$img_Flag"
							+ "*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getHomeTeam().getTeamBadge() + "\0", print_writers);
				}
				else if(match.getSetup().getAwayTeam().getTeamName1().contains(TeamName.trim())) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Team$img_Flag"
							+ "*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getAwayTeam().getTeamBadge() + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$CountryName"
						+ "$txt_CountryName*GEOM*TEXT SET " + TeamName.toUpperCase() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Info$txt_Info1"
						+ "*GEOM*TEXT SET " + "WIN BY" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Results$Info$txt_Info2"
						+ "*GEOM*TEXT SET " + Result.toUpperCase() + "\0", print_writers);
			}
			
			this.status = CricketUtil.SUCCESSFUL;
		}
	}
	
	public void populatePointsTable(List<PrintWriter> print_writers, List<LeagueTeam> league, String session_selected_broadcaster,
			MatchAllData match, String grp, List<Team> tm) throws InterruptedException 
	{
		int row_no=1;
		String container_Name = "";
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 23\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style4$txt_Header"
				+ "*GEOM*TEXT SET STANDINGS\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$select_DataStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_Played"
				+ "*GEOM*TEXT SET P\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_Won"
				+ "*GEOM*TEXT SET W\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_Lost"
				+ "*GEOM*TEXT SET L\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_NoResult"
				+ "*GEOM*TEXT SET NR\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_Points"
				+ "*GEOM*TEXT SET PTS\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_NRR"
				+ "*GEOM*TEXT SET NRR\0", print_writers);
		
		for(int i = 0; i <= league.size() - 1 ; i++) {
			row_no = row_no + 1;
			
			if(match.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase(league.get(i).getTeamName().toUpperCase()) || 
					match.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase(league.get(i).getTeamName().toUpperCase())) {
				container_Name = "$Highlight";
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no
						+ "$select_DataStyle*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			}else {
				container_Name = "$Dehighlight";
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no
						+ "$select_DataStyle*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no + container_Name 
					+ "$txt_Rank*GEOM*TEXT SET " + (!league.get(i).getQualifiedStatus().trim().isEmpty() ? "Q" : "") + "\0", print_writers);
			for(Team teams : tm) {
				if(teams.getTeamName4().equalsIgnoreCase(league.get(i).getTeamName())) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
							+ container_Name + "$img_Flag*TEXTURE*IMAGE SET " + logo_path + teams.getTeamBadge() + "\0", print_writers);	
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
							+ container_Name + "$txt_CountryName*GEOM*TEXT SET " + teams.getTeamName1() + "\0", print_writers);
					break;
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_Played*GEOM*TEXT SET " + league.get(i).getPlayed() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_Won*GEOM*TEXT SET " + league.get(i).getWon() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_Lost*GEOM*TEXT SET " + league.get(i).getLost() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_NoResult*GEOM*TEXT SET " + league.get(i).getNoResult() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_Points*GEOM*TEXT SET " + league.get(i).getPoints() + "\0", print_writers);
			
			DecimalFormat df = new DecimalFormat("0.00");
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_NRR*GEOM*TEXT SET " + df.format(league.get(i).getNetRunRate()) + "\0", print_writers);
			
			
		}
	}
	
	private void populateGroupPointsTable(List<PrintWriter> print_writers, List<LeagueTeam> league, String session_selected_broadcaster, MatchAllData match, 
			String grp, List<Team> tm) throws InterruptedException {
		
		int row_no=1;
		String container_Name = "";
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$select_HeaderStyle"
				+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$select_Graphics"
				+ "*FUNCTION*Omo*vis_con SET 23\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Footer$Side" + which_side + "$select_FooterType"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		
		if(grp.contains("SUPER")) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$6*ACTIVE SET 0\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$6*ACTIVE SET 1\0", print_writers);
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Header$Side" + which_side + "$Style4$txt_Header"
				+ "*GEOM*TEXT SET " + grp + " STANDINGS\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$select_DataStyle"
				+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_Played"
				+ "*GEOM*TEXT SET P\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_Won"
				+ "*GEOM*TEXT SET W\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_Lost"
				+ "*GEOM*TEXT SET L\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_NoResult"
				+ "*GEOM*TEXT SET NR\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_Points"
				+ "*GEOM*TEXT SET PTS\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$1$Title$txt_NRR"
				+ "*GEOM*TEXT SET NRR\0", print_writers);
		
		for(int i = 0; i <= league.size() - 1 ; i++) {
			row_no = row_no + 1;
			
			if(match.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase(league.get(i).getTeamName().toUpperCase()) || 
					match.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase(league.get(i).getTeamName().toUpperCase())) {
				container_Name = "$Highlight";
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no
						+ "$select_DataStyle*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			}else {
				container_Name = "$Dehighlight";
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no
						+ "$select_DataStyle*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no + container_Name 
					+ "$txt_Rank*GEOM*TEXT SET " + (!league.get(i).getQualifiedStatus().trim().isEmpty() ? "Q" : "") + "\0", print_writers);
			for(Team teams : tm) {
				if(teams.getTeamName4().equalsIgnoreCase(league.get(i).getTeamName())) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
							+ container_Name + "$img_Flag*TEXTURE*IMAGE SET " + logo_path + teams.getTeamBadge() + "\0", print_writers);	
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
							+ container_Name + "$txt_CountryName*GEOM*TEXT SET " + teams.getTeamName1() + "\0", print_writers);
					break;
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_Played*GEOM*TEXT SET " + league.get(i).getPlayed() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_Won*GEOM*TEXT SET " + league.get(i).getWon() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_Lost*GEOM*TEXT SET " + league.get(i).getLost() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_NoResult*GEOM*TEXT SET " + league.get(i).getNoResult() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_Points*GEOM*TEXT SET " + league.get(i).getPoints() + "\0", print_writers);
			
			DecimalFormat df = new DecimalFormat("0.00");
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BigScreen$Main$Side" + which_side + "$Standings$" + row_no 
					+ container_Name + "$txt_NRR*GEOM*TEXT SET " + df.format(league.get(i).getNetRunRate()) + "\0", print_writers);
			
			
		}
		
	}
	
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster,int which_layer)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			switch(which_layer) {
			case 1:
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
				
			case 2:
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				//print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");	
				break;
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
		case "INFOBAR":
			processAnimation(print_writer, "Out", "START", session_selected_broadcaster,1);
			break;
		
		case "SCORECARD": 
			processAnimation(print_writer, "BattingCardOut", "START", session_selected_broadcaster,2);
			TimeUnit.SECONDS.sleep(1);
			break;
		case "BOWLINGCARD":
			processAnimation(print_writer, "BowlingCardOut", "START", session_selected_broadcaster,2);
			TimeUnit.SECONDS.sleep(1);
			break;
		case "SUMARRY": case "PREVIOUS_SUMARRY":
			processAnimation(print_writer, "SummaryOut", "START", session_selected_broadcaster,2);
			TimeUnit.SECONDS.sleep(1);
			break;
		case "POINTSTABLE":
			processAnimation(print_writer, "PointsTableOut", "START", session_selected_broadcaster,2);
			TimeUnit.SECONDS.sleep(1);
			break;
		
		case "BUG": case "HOWOUT": case "BATSMANSTATS": case "BOWLERSTATS": case "BUG-DB": case "NAMESUPER": case "NAMESUPER-PLAYER": case "DOUBLETEAMS": 
		case "MATCHID": case "L3MATCHID": case "PLAYINGXI": case "TARGET": case "TEAMSUMMARY": case "EQUATION":case "PLAYERSUMMARY": case "L3PLAYERPROFILE": 
		case "FALLOFWICKET": case "SPLIT": case "COMPARISION": case "BUG-DISMISSAL": case "HOWOUT_WITHOUT_FIELDER": case "BATSMAN_STYLE": case "BUG-BOWLER": 
		case "MATCH_PROMO": case "TEAMS_LOGO": case "BOWLER_STYLE": case "TIEID-DOUBLE": case "GENERIC": case "MOSTRUNS": case "MOSTWICKETS": 
		case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "MANHATTAN": case "PARTNERSHIP": case "PROJECTED": case "FF_TARGET": case "THISOVER":
		case "L3HOWOUT": case "CURRENT_PARTNERSHIP": case "WORM": case "PLAYERPROFILE": case "MATCHSTATUS": case "HOWOUT_BOTH": case "BATSMANSTATS_BOTH":
		case "THIS_SESSION": case "SESSION": case "FF_EQUATION": case "BUG-TOSS": case "BOWLERDETAILS":
			processAnimation(print_writer, "Out", "START", session_selected_broadcaster,2);
			TimeUnit.SECONDS.sleep(1);
			break;
			
		 case "LEADERBOARD":
			processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster,1);
			break;
			
			
		
		case "FF_OUT":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + "FF_Out" + " START" + ";");
			//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_Out START \0");
			break;
		}	
	}

	public static String getPhaseWiseScore(int start_over, int end_over, int inn_num, List<Event> events) {
	    int total_run_PP = 0, total_wickets_PP = 0,Fours = 0, Sixes = 0, Dots = 0, Nines = 0;
	    int current_over = 0, current_ball = 0;

	    if (events != null && !events.isEmpty()) {
	        for (Event event : events) {
	            if (event.getEventInningNumber() == inn_num) {
	                switch (event.getEventType()) {
	                    case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE: case CricketUtil.DOT:
	                    case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.NINE: case CricketUtil.BYE: case CricketUtil.LEG_BYE:
	                        current_ball++;
	                        break;
	                    case CricketUtil.LOG_WICKET:
	                        if (!event.getEventHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT) &&
	                            !event.getEventHowOut().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
	                            current_ball++;
	                        }
	                        break;
	                    case CricketUtil.END_OVER:
	                        current_over++;
	                        current_ball = 0; // Reset ball count at the end of the over
	                        break;
	                    case CricketUtil.CHANGE_BOWLER:
	                        if (current_ball == 6) { 
	                            current_over++; 
	                            current_ball = 0; // Ensure we track overs correctly
	                        }
	                        break;
	                }

	                if (current_over >= (start_over-1) && current_over < end_over) {
	                    switch (event.getEventType()) {
	                        case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE: case CricketUtil.DOT:
	                        case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.NINE:
	                            total_run_PP += event.getEventRuns();
	                            if (event.getEventType() == CricketUtil.FOUR && "YES".equalsIgnoreCase(event.getEventWasABoundary())) {
	                                Fours++;
	                            } else if (event.getEventType() == CricketUtil.SIX && "YES".equalsIgnoreCase(event.getEventWasABoundary())) {
	                                Sixes++;
	                            } else if (event.getEventType() == CricketUtil.NINE && "YES".equalsIgnoreCase(event.getEventWasABoundary())) {
	                                Nines++;
	                            } else if (event.getEventType() == CricketUtil.DOT) {
	                                Dots++;
	                            }
	                            break;

	                        case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: 
	                        case CricketUtil.LEG_BYE: case CricketUtil.PENALTY:
	                            total_run_PP += event.getEventRuns();
	                            break;

	                        case CricketUtil.LOG_WICKET:
	                            if (event.getEventRuns() > 0) {
	                                total_run_PP += event.getEventRuns();
	                            } else {
	                                Dots++;
	                            }
	                            if (!event.getEventHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT) &&
	                                !event.getEventHowOut().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
	                                total_wickets_PP++;
	                            }
	                            break;

	                        case CricketUtil.LOG_ANY_BALL:
	                            total_run_PP += event.getEventRuns();
	                            if (event.getEventExtra() != null) {
	                                total_run_PP += event.getEventExtraRuns();
	                            }
	                            if (event.getEventSubExtra() != null) {
	                                total_run_PP += event.getEventSubExtraRuns();
	                            }
	                            if (event.getEventHowOut() != null && !event.getEventHowOut().isEmpty()) {
	                                total_wickets_PP++;
	                            }
	                            if (event.getEventType().equalsIgnoreCase(CricketUtil.FOUR) && 
	                                "YES".equalsIgnoreCase(event.getEventWasABoundary())) {
	                                Fours++;
	                            }
	                            if (event.getEventType().equalsIgnoreCase(CricketUtil.SIX) && 
	                                "YES".equalsIgnoreCase(event.getEventWasABoundary())) {
	                                Sixes++;
	                            }
	                            break;
	                    }
	                }
	            }
	        }
	    }

	    return total_run_PP + "-" + total_wickets_PP + "," + Fours + "," + Sixes + "," + Dots + "," + Nines;
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
}