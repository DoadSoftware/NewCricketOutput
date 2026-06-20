package com.cricket.broadcaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import jakarta.xml.bind.JAXBContext;
import com.cricket.model.Statistics;
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
import com.cricket.model.HeadToHead;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.LeagueTeam;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.Player;
import com.cricket.model.Sponsor;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ICC_BIGSCREEN_DOAD_SCORING extends Scene{

	private String status;
	private String data;
	public Infobar infobar = new Infobar();
	public List<String> this_data_str = new ArrayList<String>();
	public String session_selected_broadcaster = "ICC_BIGSCREEN_DOAD_SCORING";
	public String which_graphics_onscreen = "",which_graphics_onscreen1 = "";
	public String team = "";
	public Statistics stats;
	private String logo_path = "C:\\Images\\ICC\\ChampionsTrophy\\Flags\\";
	private String photo_path  = "C:\\Images\\ICC\\ChampionsTrophy\\Player_Images\\";
	private String local_photo_path = "\\\\c\\\\Images\\\\ICC\\\\ChampionsTrophy\\\\Player_Images\\\\";
	private String sponsor_path  = "C:\\Images\\ICC\\ChampionsTrophy\\Sponsor\\";
//	private String fantasy_path  = "C:\\Images\\ICC\\ChampionsTrophy\\Fantasy\\";
	public String text_path = "IMAGE*/Default/Essentials/Text";
	public String icon_path = "C:\\Images\\ICC\\ChampionsTrophy\\Icons\\";
	
	private String base1_path  = "D:/DOAD_In_House_Everest/Everest_Cricket/Everest_LLC_Franchise_2023/Textures/Base1/";
//	private String base2_path  = "D:/DOAD_In_House_Everest/Everest_Cricket/Everest_LLC_Franchise_2023/Textures/Base2/";
	
	private String text1_path  = "D:/DOAD_In_House_Everest/Everest_Cricket/Everest_LLC_Franchise_2023/Textures/Text1/";
//	private String text2_path  = "D:/DOAD_In_House_Everest/Everest_Cricket/Everest_LLC_Franchise_2023/Textures/Text2/";
	
	public int current_layer = 3,count = 1,loop_value = 0,video_count = 0,video_layer = 1,inning_no = 0;
	public boolean is_video_onScreen = false;
	
	public ICC_BIGSCREEN_DOAD_SCORING() {
		super();
	}

	public ICC_BIGSCREEN_DOAD_SCORING(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Infobar updateInfobar(Scene scene, MatchAllData match,boolean show_speed, PrintWriter print_writer,CricketService cricketService,Configuration config) throws InterruptedException, IOException
	{
		
		if(which_graphics_onscreen.equalsIgnoreCase("INFO") && infobar.isInfobar_on_screen() == true) {
			infobar = populateInfo(infobar, print_writer,true,match, session_selected_broadcaster);
			infobar = populateVizInfobarMiddle(infobar, true, print_writer, match, session_selected_broadcaster);
		}//getScorebug_last_value
		
		if(which_graphics_onscreen.equalsIgnoreCase("SCOREBUG") && infobar.isInfobar_on_screen() == true) {
			infobar = populateScorebug(print_writer, true, match, session_selected_broadcaster);
			infobar = populateScorebugChangeOn(print_writer, infobar.getScorebug_last_value(), match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("ICC_MATCHSUMMARY")) {
			populateMatchSummary(print_writer,true,inning_no,
					match,cricketService.getAllPlayer(), session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("EQUATION_ICC")) {
			populateEquation(print_writer,true,match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("EQUATION_IMG")) {
			populateEquationWithImgBs(print_writer,true,match,cricketService.getAllPlayer(),cricketService.getTeams(),
					session_selected_broadcaster,config);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("EQUATIONSHORT_ICC")) {
			populateEquationShort(print_writer,true,match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("PROJECTED_BS")) {
			populateProjectedBs(print_writer,true,match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("COMPARISON_ICC")) {
			populateComparison(print_writer,true,match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("PARTNERSHIP_ICC")) {
			populatePartnership(print_writer,true,cricketService.getAllPlayer(),cricketService.getTeams(),match, 
					session_selected_broadcaster,config);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("THIS_OVER_ICC")) {
			populateThisOver(print_writer, true, match, session_selected_broadcaster, config);
		}
		
//		CricketFunctions.getInteractive(match);
		return infobar;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, PrintWriter print_writer, List<Scene> scenes, 
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
			
		case "POPULATE-LINEUPLONG_ICC": case "POPULATE-LONGLINEUP_ICC": case "POPULATE-ICC_MATCHSUMMARY": case "POPULATE-MILESTONE_ICC":
		case "POPULATE-SCOREBUG_CHANGEON_ICC": case "POPULATE-INFOBAR_ICC": case "POPULATE-SCOREBUG_ICC": case "POPULATE-LINEUP_ICC":
		case "POPULATE-FOUR_ICC": case "POPULATE-WIDE_ICC": case "POPULATE-DUCK_ICC": case "POPULATE-WICKET_ICC": case "POPULATE-LINEUPIMAGE_ICC":
		case "POPULATE-SIX_ICC": case "POPULATE-HAT_TRICK":case "POPULATE-HAT_TRICK_BALL": case "POPULATE-WEATHER_ICC":
		case "POPULATE-FREEHIT_ICC": case "POPULATE-HUNDRED_ICC": case "POPULATE-FIFTY_ICC": case "POPULATE-CATCH_ICC":
		case "POPULATE-MATCHID_ICC": case"POPULATE-MATCHID_WITH_IMG_ICC": case"POPULATE-GROUP_ICC": case "POPULATE-REVIEW_ICC":
		case "POPULATE-ICC_INTRO-STATS": case "POPULATE-BOUNDARY_ICC": case "POPULATE-EXTRAS_ICC": case "POPULATE-LINE2FREE_ICC":
		case "POPULATE-IMG_LINE2FREE_ICC": case "POPULATE-FREETEXT_ICC": case "POPULATE-ICC_TEAM-BOUNDARY": case "POPULATE-RUNRATE_ICC":
		case "POPULATE-COMPARISON_ICC": case "POPULATE-TOSS_ICC": case "POPULATE-TEAMNAME_ICC": case "POPULATE-PLAYERFREETEXT_ICC":
		case "POPULATE-ICC_QUICKHOWOUT": case "POPULATE-BS_HOWOUT":	case "POPULATE-TARGETFULL_ICC": case "POPULATE-TARGET_ICC":
		case "POPULATE-TARGET_WITH_IMG_ICC": case "POPULATE-EQUATION_ICC": case "POPULATE-EQUATIONSHORT_ICC": case "POPULATE-EQUATION_WITH_IMG_ICC":
		case "POPULATE-BS_BATSCORE": case "POPULATE-ICC_BOWLER-FIG": case "POPULATE-PARTNERSHIP_ICC": case "POPULATE-SIX_DISTANCE_ICC":
		case "POPULATE-GROUP_PTSTBLE_ICC": case "POPULATE-BUKH-POINTSTABLE":	case "POPULATE-ICC_BATSMAN-STATS": case "POPULATE-ICC_BOWLER-STATS":
		case "POPULATE-DLS": case "POPULATE-PHASESCORE_ICC":case "POPULATE_ICC_PP1":case "POPULATE_ICC_PP2":case "POPULATE_ICC_PP3":
		case "POPULATE-BATSMAN_STYLE": case "POPULATE-BOWLER_STYLE":
			
		case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-THIS_OVER_ICC":
			
		case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case "ICC_BIGSCREEN_DOAD_SCORING":
				switch(whatToProcess.toUpperCase()) {
				case "POPULATE-OUT_NOT_DECISION": case "POPULATE-START_BS": case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
				case "POPULATE-SCOREBUG_CHANGEON_ICC":	
					break;
				case "POPULATE-SCOREBUG_ICC": case "POPULATE-SCOREBOARD_ICC":
					scenes.get(0).setWhich_layer(String.valueOf(1));
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,session_selected_broadcaster);
					break;
				case "POPULATE-L3-INFOBAR":
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,session_selected_broadcaster);
					break;
				case "POPULATE-PLAYERVIDEO_ICC":
					scenes.get(0).setWhich_layer(String.valueOf(video_layer));
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,session_selected_broadcaster);
					break;	
				default:
					current_layer = 5 - current_layer;
//					scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_LLC_2023/Scenes/BG.sum"
//							,"3"));
//					scenes.get(0).scene_load(print_writer, session_selected_broadcaster);
					scenes.get(1).setWhich_layer(String.valueOf(current_layer));
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,session_selected_broadcaster);
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BowlerGrp$Section3$Section3All$BallSpeed$img_Text$txt_Data1*GEOM*TEXT SET " + 
							"BALL SPEED - " + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BowlerGrp$Section3$Section3All$BallSpeed$img_Text$txt_Data2*GEOM*TEXT SET " + 
							valueToProcess + " KPH" + "\0");	
					
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
//							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BowlerGrp$Section3$Section3All$BallSpeed$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + 
//									inn.getBowling_team().getTeamName3().toLowerCase() +" \0");
						}
					}
					processAnimation(print_writer, "Section3$BallSpeedIn", "START", "DOAD_LLC");
					break;
				case"POPULATE-GROUP_PTSTBLE_ICC":
					LeagueTable Groups = null;
					if(valueToProcess.split(",")[1].equalsIgnoreCase("GROUP A")) {
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupA.xml").exists()) {
							Groups = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupA.xml"));
						}
					}else if(valueToProcess.split(",")[1].equalsIgnoreCase("GROUP B")) {
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupB.xml").exists()) {
							Groups = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupB.xml"));
						}
					}
					
					populateGroupPointsTable(print_writer, valueToProcess.split(",")[0],Groups.getLeagueTeams(),session_selected_broadcaster,
							match,valueToProcess.split(",")[1],cricketService.getTeams());
					break;
				case "POPULATE-BUKH-POINTSTABLE":
					LeagueTable group = null;
					String groups = "";
					if(valueToProcess.split(",")[1].equalsIgnoreCase("A")) {
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupA.xml").exists()) {
							group = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupA.xml"));
							groups = "GROUP A";
						}
					}else if(valueToProcess.split(",")[1].equalsIgnoreCase("B")) {
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupB.xml").exists()) {
							group = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupB.xml"));
							groups = "GROUP B";
						}
					}
					
					populatePointsTable(print_writer, valueToProcess.split(",")[0],group.getLeagueTeams(),session_selected_broadcaster,
							match,groups,cricketService.getTeams());
					break;	
				case "POPULATE-LINEUP_ICC":
					populateLineup(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),match, session_selected_broadcaster);
					break;
				case "POPULATE-LINEUPIMAGE_ICC":
					populateLineupImage(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),match, session_selected_broadcaster,config);
					break;	
				case "POPULATE-SCOREBUG_CHANGEON_ICC":
					populateScorebugChangeOn(print_writer, valueToProcess,match, session_selected_broadcaster);
					break;
				case "POPULATE-SCOREBUG_ICC":
					populateScorebug(print_writer,false,match, session_selected_broadcaster);
					infobar.setScorebug_last_value("");
					break;
				case "POPULATE-INFOBAR_ICC":
					populateInfobar(infobar, print_writer,false, valueToProcess,match, session_selected_broadcaster);
					infobar = populateVizInfobarMiddle(infobar, false, print_writer,match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGETFULL_ICC":
					populateTargetFull(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGET_ICC":
					populateTarget(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGET_WITH_IMG_ICC":
					populateTargetWithImgBs(print_writer,false,match,cricketService.getAllPlayer(),cricketService.getTeams(),session_selected_broadcaster,config);
					break;
				case "POPULATE-EQUATIONSHORT_ICC":
					populateEquationShort(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-EQUATION_ICC":
					populateEquation(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-EQUATION_WITH_IMG_ICC":
					populateEquationWithImgBs(print_writer,false,match,cricketService.getAllPlayer(),cricketService.getTeams(),session_selected_broadcaster,config);
					break;
				case "POPULATE-BS_BATSCORE":
					populateBatterScore(print_writer,false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,config);
					break;
				case "POPULATE-ICC_BOWLER-FIG":
					populateBowlerFig(print_writer,false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,config);
					break;
				case "POPULATE-ICC_BATSMAN-STATS":
					populateBatsmanStats(print_writer,false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							,session_selected_broadcaster,config);
					break;
				case "POPULATE-ICC_BOWLER-STATS":
					populateBowlerStats(print_writer,false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,config);
					break;
				case "POPULATE-DLS":
					populateDlsParScore(print_writer,match,dls);
					break;
				case "POPULATE_ICC_PP1":case "POPULATE_ICC_PP2":case "POPULATE_ICC_PP3":
					
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
					print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
					print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
					TimeUnit.SECONDS.sleep(1);
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
					break;
				case "POPULATE-PHASESCORE_ICC":
					populatePhaseBy(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),match, session_selected_broadcaster);
					break;
					
				case "POPULATE-PARTNERSHIP_ICC":
					populatePartnership(print_writer,false,cricketService.getAllPlayer(),cricketService.getTeams(),match, session_selected_broadcaster,config);
					break;
				case "POPULATE-SIX_DISTANCE_ICC":
					populateSixDistance(print_writer,false,valueToProcess.split(",")[0],valueToProcess.split(",")[1] , session_selected_broadcaster);
					break;	
				case "POPULATE-ICC_QUICKHOWOUT":
					populateQuickHowOut(print_writer,false,match);
					break;
				case "POPULATE-BS_HOWOUT":
					populateFallOfWickets(print_writer,false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster);
					break;	
				case "POPULATE-PLAYERFREETEXT_ICC":
					populatePlayerfreeText(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],valueToProcess.split(",")[3],
							Integer.valueOf(valueToProcess.split(",")[4]),match,cricketService.getAllPlayer(),cricketService.getTeams(), session_selected_broadcaster,config);
					break;
					
				case "POPULATE-BATSMAN_STYLE":
					populatePlayerBatAndBowlStyle(print_writer, Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]),"BAT",
							match,session_selected_broadcaster,config);
					break;
				 case "POPULATE-BOWLER_STYLE":
						populatePlayerBatAndBowlStyle(print_writer, Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]),"BOWL",
								match,session_selected_broadcaster,config);
						break;
					
				case "POPULATE-ICC_TEAM-BOUNDARY":
					populateTeamBoundary(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),match, session_selected_broadcaster);
					break;
				case "POPULATE-RUNRATE_ICC":
					populateRunRate(print_writer,false,match, session_selected_broadcaster);
					break;	
				case "POPULATE-BOUNDARY_ICC":
					populateBoundary(print_writer,false,valueToProcess.split(",")[0],valueToProcess.split(",")[1] , session_selected_broadcaster);
					break;
				case "POPULATE-EXTRAS_ICC":
					populateExtras(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-REVIEW_ICC":
					populateReview(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-COMPARISON_ICC":
					populateComparison(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-TEAMNAME_ICC":
					populateTeamName(print_writer,false,valueToProcess.split(",")[1],match, session_selected_broadcaster);
					break;
				case "POPULATE-TOSS_ICC":
					populateToss(print_writer,false,match, session_selected_broadcaster);
					break;	
				case "POPULATE-LINE2FREE_ICC":
					populateline2FreeText(print_writer,false,valueToProcess.split(",")[0],valueToProcess.split(",")[1],valueToProcess.split(",")[2] , session_selected_broadcaster);
					break;
				case "POPULATE-IMG_LINE2FREE_ICC":
					populateImgline2FreeText(print_writer,false,valueToProcess.split(",")[0],valueToProcess.split(",")[1],valueToProcess.split(",")[2],
							valueToProcess.split(",")[3] ,cricketService.getTeams(),  session_selected_broadcaster);
					break;
				case "POPULATE-FREETEXT_ICC":
					for(NameSuper ns : cricketService.getNameSupers()) {
						  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
								populateFreeText(print_writer,false,valueToProcess.split(",")[0],ns , session_selected_broadcaster);
						  }
					}
					break;	
				case "POPULATE-MATCHID_ICC":
					populateMatchID(print_writer,false,cricketService.getAllPlayer(),cricketService.getTeams(),match, session_selected_broadcaster);
					break;
				case"POPULATE-MATCHID_WITH_IMG_ICC":
					populateMatchIDWithImgBs(print_writer,false,match,cricketService.getAllPlayer(),cricketService.getTeams(),session_selected_broadcaster);
					break;
				case"POPULATE-GROUP_ICC":
					populateGroup(print_writer,false,valueToProcess.split(",")[1], session_selected_broadcaster,
							cricketService.getTeams().stream().filter(tm->tm.getTeamGroup().equalsIgnoreCase(valueToProcess.split(",")[1])).collect(Collectors.toList()));
					break;	
				case "POPULATE-QUICKHOWOUT_BS":
					populateQuickHowout(print_writer,false, match, session_selected_broadcaster);
					break;
				case "POPULATE-BOWLERFIG_BS":
					populateBugBowler(print_writer,false, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-HOWOUT_BS":
					populateHowout(print_writer,false, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
							Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-START_BS":
					populateCountdown(print_writer,false,valueToProcess.split(",")[0],match, session_selected_broadcaster);
					break;
				case "POPULATE-COUNTDOWN_BS":
//					populateCountdown(print_writer,valueToProcess.split(",")[1],match, session_selected_broadcaster);
					break;
				case "POPULATE-PLAYERMILE_BS":
					populatePlayerMileStoneBs(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							valueToProcess.split(",")[3],match, session_selected_broadcaster);
					break;
				case "POPULATE-ICC_INTRO-STATS":
					populatePlayerIntroStats(print_writer,false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1])
							,cricketService.getAllPlayer(),cricketService.getTeams(),match, session_selected_broadcaster);
					break;	
				case "POPULATE-EQUATION_BS":
					populateEquationBs(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-PROJECTED_BS":
					populateProjectedBs(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-BOUNDARIES_BS":
					populateBoundariesBs(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-FREE_BS":
					populateFreeBs(print_writer,false,valueToProcess.split(",")[1],match, session_selected_broadcaster);
					break;
				case "POPULATE-COMPARISON_BS":
					populateComparisonBs(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGET_BS":
					populateTargetBs(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-INFO":
					populateInfo(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCH_IDENT":
					populateIdentMatch(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCH_RESULT":
					populateMatchResult(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-OUT_NOT_DECISION":
					populateOutNotDecision(print_writer,valueToProcess.split(",")[0], session_selected_broadcaster);
					break;
				case "POPULATE-DECISION":
					populateDecision(print_writer,false,match, session_selected_broadcaster);
					break;
				case "POPULATE-FOUR_ICC":
					populateFour(print_writer,false,valueToProcess.split(",")[0], session_selected_broadcaster);
					break;
				case "POPULATE-WIDE_ICC":
					populateWide(print_writer,false,valueToProcess.split(",")[0], session_selected_broadcaster);
					break;
				case "POPULATE-DUCK_ICC":
					populateDuck(print_writer,false,valueToProcess.split(",")[0], session_selected_broadcaster);
					break;	
				case "POPULATE-WICKET_ICC":
					populateWicket(print_writer,false,valueToProcess.split(",")[0], session_selected_broadcaster);
					break;
				case "POPULATE-SIX_ICC":
					populateSix(print_writer,false,valueToProcess.split(",")[0], session_selected_broadcaster);
					break;
				case "POPULATE-HAT_TRICK":case "POPULATE-HAT_TRICK_BALL":
					populateHatTrick(print_writer,false, session_selected_broadcaster);
					break;
				case "POPULATE-WEATHER_ICC":
					populateWeather(print_writer,false,valueToProcess.split(",")[1],valueToProcess.split(",")[2],valueToProcess.split(",")[3],
							match,session_selected_broadcaster);
					break;
				case "POPULATE-HUNDRED_ICC":case "POPULATE-FIFTY_ICC":case "POPULATE-CATCH_ICC":
					 populateExtraBoundries(print_writer,false,whatToProcess.replace("POPULATE-", "").replace("_ICC", ""),match, session_selected_broadcaster);
					break;
				case "POPULATE-FREEHIT_ICC":
					populateFreeHit(print_writer,false,match, session_selected_broadcaster);
					break;	
				case "POPULATE-LINEUPLONG_ICC":
					populateLineupLong(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),match, session_selected_broadcaster);
					break;
				case "POPULATE-LONGLINEUP_ICC":
					populateLongLineup(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),match, session_selected_broadcaster);
					break;
				case "POPULATE-ICC_MATCHSUMMARY":
					inning_no = Integer.valueOf(valueToProcess.split(",")[1]);
					populateMatchSummary(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),match,cricketService.getAllPlayer(), session_selected_broadcaster);
					break;
				case "POPULATE-MILESTONE_ICC":
					populateMileStone(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],valueToProcess.split(",")[3],
							valueToProcess.split(",")[4],Integer.valueOf(valueToProcess.split(",")[5]),match,cricketService.getAllPlayer(),cricketService.getTeams(), 
							session_selected_broadcaster,config);
					break;
				case "POPULATE-THIS_OVER_ICC":
					populateThisOver(print_writer, false, match, session_selected_broadcaster, config);
					break;	
				case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-L3-PLAYERPROFILE":
					this.status = "NODATABASE";
					
					if(valueToProcess.split(",")[2].equalsIgnoreCase("THISSERIES")) {
						this.status = CricketUtil.SUCCESSFUL;
						populatePlayerProfileBat(print_writer,false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
								valueToProcess.split(",")[3],CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, 
								past_tournament_stats), null,cricketService,match, session_selected_broadcaster,config);
					}else {
						stats = statistics.stream().filter(st -> st.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()).findAny().orElse(null);
						stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase("ODI")) {
							stats = CricketFunctions.updateH2h(stats, head_to_head, match, CricketUtil.FULL);
							stats = CricketFunctions.updateMatchData(stats, match, CricketUtil.FULL);
						}
						
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							this.status = CricketUtil.SUCCESSFUL;
							
							populatePlayerProfileBat(print_writer,false,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],null, stats,cricketService,match, session_selected_broadcaster,config);
						}
					}
					
					if(this.status.equalsIgnoreCase("NODATABASE")){
						current_layer = 5 - current_layer;
					}
					break;
				}
				//return JSONObject.fromObject(this_doad).toString();
			}
		
		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-DECISION": case "ANIMATE-IN-OUT_NOT_DECISION": case "ANIMATE-IN-FREE_BS":
		case "ANIMATE-IN-MATCH_IDENT": case "ANIMATE-IN-INFO": case "ANIMATE-IN-WICKET":case "ANIMATE-IN-TARGET_BS": case "ANIMATE-IN-COMPARISON_BS":
		case "ANIMATE-IN-BOUNDARIES_BS": case "ANIMATE-IN-PROJECTED_BS": case "ANIMATE-IN-EQUATION_BS": case "ANIMATE-IN-LTPLAYERPROFILEBAT":
		case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-PLAYERMILE_BS": case "ANIMATE-IN-START_BS": case "ANIMATE-IN-COUNTDOWN_BS":
		case "ANIMATE-IN-HOWOUT_BS": case "ANIMATE-IN-BOWLERFIG_BS": case "ANIMATE-IN-QUICKHOWOUT_BS":case "ANIMATE-BS_BATTER_SCORE":
		case "ANIMATE-IN-SIX_DISTANCE":	case "ANIMATE-IN-INFOBAR_ICC": case "ANIMATE-IN-SCOREBUG_ICC": case "ANIMATE-IN-PARTNERSHIP_ICC":
		case "ANIMATE-OUT-SCOREBUG": case "ANIMATE-IN-MATCHID_ICC": case "ANIMATE-IN-FREEHIT_ICC": case "ANIMATE-IN-SCOREBOARD_ICC":
		case "ANIMATE-IN-FREETEXT_ICC": case "ANIMATE-IN-BOUNDARY_ICC":	case "ANIMATE-IN-LINE2FREE_ICC": case "ANIMATE-IN-EXTRAS_ICC":
		case "ANIMATE-IN-ICC_BATSMAN-STATS": case "ANIMATE-IN-TARGET_ICC": case "ANIMATE-IN-EQUATION_ICC": case "ANIMATE-IN-RUNRATE_ICC":
		case "ANIMATE-IN-ICC_TEAM-BOUNDARY": case "ANIMATE-IN-TOSS_ICC": case "ANIMATE-IN-ICC_BOWLER-STATS": case "ANIMATE-IN-TEAMNAME_ICC":
		case "ANIMATE-IN-COMPARISON_ICC": case "ANIMATE-IN-ICC_MATCHSUMMARY": case "ANIMATE-IN-FOUR_ICC": case "ANIMATE-IN-SIX_ICC":
		case "ANIMATE-IN-MILESTONE_ICC": case "ANIMATE-IN-TARGET_WITH_IMG_ICC":	case "ANIMATE-IN-ICC_BALL-SPEED": case "ANIMATE-IN-PLAYERFREETEXT_ICC":
		case "ANIMATE-IN-THIS_OVER_ICC": case "ANIMATE-IN-LINEUP_ICC": case "ANIMATE-IN-LINEUPIMAGE_ICC": case "ANIMATE-IN-ICC_BOWLER-FIG":
		case "ANIMATE-IN-PLAYERNAME_ICC": case "CHANGEON_PLAYER_ICC": case "ANIMATE-IN-REVIEW": case "ANIMATE-IN-TARGETFULL_ICC":
		case "ANIMATE-IN-EQUATIONSHORT_ICC": case "ANIMATE-IN-ICC_WAGON": case "ANIMATE-IN-LINEUPLONG_ICC": case "ANIMATE-IN-ICC_IMAGE16_9":
		case "ANIMATE-IN-ICC_IMAGE4_3": case "ANIMATE-IN-ICC_IMAGELOOP": case "ANIMATE-IN-ICC_WICKET": case "ANIMATE-IN-ICC_WIDE":
		case "ANIMATE-IN-ICC_DUCK": case "ANIMATE-IN-IMAGEDROPDOWN": case "ANIMATE-IN-ICC_QUICKHOWOUT": case "ANIMATE-IN-CANCEL":
		case "ANIMATE-IN-WEATHER_ICC":	case "CHANGEON_VIDEO_ICC": case "ANIMATE-IN-PLAYERVIDEO_ICC": case "ANIMATE-IN-LONGLINEUP_ICC":
		case "ANIMATE-IN-ICC_BALL-DISTANCE": case "ANIMATE-IN-FANTASYDROPDOWN":case "ANIMATE-IN-ICC_CATCH":case "ANIMATE-IN-ICC_FIFTY":case "ANIMATE-IN-ICC_HUNDRED":
		case "ANIMATE-IN-PHASESCORE_ICC": case "ANIMATE-IN-IMG_LINE2FREE_ICC":case "ANIMATE-IN-GROUP_PTSTBLE_ICC":
		case "ANIMATE-IN-ICC_INTRO-STATS":case "ANIMATE-IN-DLS": case "CHANGEON_INTRO_ICC":	case "ANIMATE-IN-EQUATION_WITH_IMG_ICC":case"ANIMATE-IN-GROUP_ICC":
		case"ANIMATE-IN-MATCHID_WITH_IMG_ICC":case"ANIMATE-IN-H2H_ICC": case "ANIMATE-IN-POINTSTABLE":
		case "ANIMATE-HAT_TRICK_BALL":case"ANIMATE-HAT_TRICK":case "ANIMATE-PP1":case "ANIMATE-PP2":case "ANIMATE-PP3": 
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-BOWLER_STYLE":

			switch (session_selected_broadcaster.toUpperCase()) {
			case "BIG_SCREEN_DOAD":
				
				if(whatToProcess.toUpperCase().equalsIgnoreCase("ANIMATE-OUT")) {
					loop_value = 1;
					
				}else if(whatToProcess.toUpperCase().equalsIgnoreCase("CLEAR-ALL")) {
					loop_value = 1;
					//which_graphics_onscreen = "";
					break;
				}
			}
			System.out.println("wtp = " + whatToProcess.toUpperCase());
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-CANCEL":
				current_layer = 5-current_layer;
				break;
			case "ANIMATE-IN-ICC_IMAGELOOP":
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				TimeUnit.SECONDS.sleep(2);
				current_layer = 5-current_layer;
				
				for(int i=2; i<= 14; i++) {
					
					if(loop_value != 0) {
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(current_layer));
//						TimeUnit.SECONDS.sleep(2);
						print_writer.println("LAYER" + (current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
						which_graphics_onscreen = "";
						break;
					}else {
//						current_layer = 5 - current_layer;
//						scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_LLC_2023/Scenes/BG.sum"
//								,"3"));
//						scenes.get(0).scene_load(print_writer, session_selected_broadcaster);
						scenes.get(1).setWhich_layer(String.valueOf(current_layer));
						scenes.get(1).setScene_path(data);
						scenes.get(1).scene_load(print_writer,session_selected_broadcaster);
						
						switch (i) {
						case 2:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "Booking.com 1920x1080_LED" + ".png" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(2);
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							TimeUnit.SECONDS.sleep(1);
							current_layer = 5-current_layer;
							
							break;
						case 3:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "Induslnd Bank 1920x1080_LED" + ".png" + ";");
							TimeUnit.SECONDS.sleep(2);
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");							
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(1);
							current_layer = 5-current_layer;
							break;	
						case 4:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "MasterCard 1920x1080_LED" + ".png" + ";");
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");							
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(1);
							current_layer = 5-current_layer;
							break;
						case 5:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "Aramco Blue 1920x1080_LED" + ".png" + ";");
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(1);
							current_layer = 5-current_layer;
							break;
						case 6:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "Emirates Fly Better" + ".png" + ";");
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							current_layer = 5-current_layer;
							break;
						case 7:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "BIRA" + ".png" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							current_layer = 5-current_layer;
							break;
						case 8:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "Polycab" + ".png" + ";");
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(2);
							current_layer = 5-current_layer;
							break;
						case 9:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "Thums-Up-Logo-(1)" + ".png" + ";");
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(2);
							current_layer = 5-current_layer;
							break;	
						case 10:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "Upstox" + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(2);
							current_layer = 5-current_layer;
							break;
						case 11:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "Nissan" + CricketUtil.PNG_EXTENSION + ";");
							
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(2);
//							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//							TimeUnit.SECONDS.sleep(2);
//							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//							TimeUnit.SECONDS.sleep(1);
							current_layer = 5-current_layer;
							break;
						case 12:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "NIUM  1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
							
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(2);
//							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//							TimeUnit.SECONDS.sleep(2);
//							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//							TimeUnit.SECONDS.sleep(1);
							current_layer = 5-current_layer;
							break;
						case 13:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "ICC SHOP  Static 1.1" + ".jpg" + ";");
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");	
							TimeUnit.SECONDS.sleep(2);
							current_layer = 5-current_layer;
							break;
						case 14:
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
									"16_9" + "\\" + "DP World 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
							TimeUnit.SECONDS.sleep(2);
							current_layer = 5-current_layer;
							break;
						}
					}

				}
				which_graphics_onscreen = "ICC_IMAGELOOP";
				break;
			case "ANIMATE-IN-FANTASYDROPDOWN":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "FANTASYDROPDOWN";
				break;
			case "ANIMATE-IN-LONGLINEUP_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "LONGLINEUP_ICC";
				break;
			case "ANIMATE-IN-POINTSTABLE":
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "POINTSTABLE";
				break;
			case "ANIMATE-IN-WEATHER_ICC":
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "WEATHER_ICC";
				break;
			case "ANIMATE-IN-ICC_QUICKHOWOUT":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
				//print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_QUICKHOWOUT";
				break;
			case "ANIMATE-IN-IMAGEDROPDOWN":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "IMAGEDROPDOWN";
				break;
			case "ANIMATE-IN-ICC_WIDE":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_WIDE";
				break;
			case "ANIMATE-IN-ICC_DUCK":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_DUCK";
				break;
			case "ANIMATE-IN-ICC_CATCH":case "ANIMATE-IN-ICC_FIFTY":case "ANIMATE-IN-ICC_HUNDRED":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
				which_graphics_onscreen = whatToProcess.replace("ANIMATE-IN-", "");
				break;
			case "ANIMATE-IN-ICC_WICKET":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_WICKET";
				break;
			case "ANIMATE-IN-ICC_BALL-DISTANCE":case "ANIMATE-IN-GROUP_PTSTBLE_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = whatToProcess.replace("ANIMATE-IN-", "");;
				break;
			case "ANIMATE-HAT_TRICK_BALL":case"ANIMATE-HAT_TRICK":case "ANIMATE-PP1":case "ANIMATE-PP2":case "ANIMATE-PP3":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
				which_graphics_onscreen = whatToProcess.replace("ANIMATE-", "");
				break;
			case "CHANGEON_PLAYER_ICC":
				count = count + 1;
				
				if(count <= 11) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Change COUNTINUE;");
				}else {
					print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out START;");
					TimeUnit.SECONDS.sleep(4);
//					print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				}
				break;
			case "CHANGEON_INTRO_ICC":
				video_count = video_count + 1;
				
				video_layer = 3-video_layer;
				
				if(video_count == 1) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Change START;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Change COUNTINUE;");
				}
				break;
			case "CHANGEON_VIDEO_ICC":
				video_count = video_count + 1;
				
				video_layer = 3-video_layer;
				if(is_video_onScreen == false) {
					scenes.get(1).setWhich_layer(String.valueOf(video_layer));
					scenes.get(1).setScene_path(data);
					scenes.get(1).scene_load(print_writer,session_selected_broadcaster);
					
					is_video_onScreen = true;
					TimeUnit.SECONDS.sleep(2);
				}
				
				
				if(video_count <= 10) {
					if(team.equalsIgnoreCase("Home")) {
						print_writer.println("LAYER" + video_layer + "*EVEREST*TREEVIEW*Main$Rectangle*FUNCTION*IMAGESEQUENCE2 SET PREFIX " + 
								"D:/EverestCricket/EVEREST_ICC_WorldCup_2023/Videos/Teams/" + match.getSetup().getHomeTeam().getTeamName4() + "/" + 
								match.getSetup().getHomeSquad().get(video_count).getPhoto()  + "/" + ";");
						
						print_writer.println("LAYER" + video_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
						TimeUnit.SECONDS.sleep(2);
					}else if(team.equalsIgnoreCase("Away")) {
						print_writer.println("LAYER" + video_layer + "*EVEREST*TREEVIEW*Main$Rectangle*FUNCTION*IMAGESEQUENCE2 SET PREFIX " + 
								"D:/EverestCricket/EVEREST_ICC_WorldCup_2023/Videos/Teams/" + match.getSetup().getAwayTeam().getTeamName4() + "/" + 
								match.getSetup().getAwaySquad().get(video_count).getPhoto()  + "/" + ";");
						
						print_writer.println("LAYER" + video_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
						TimeUnit.SECONDS.sleep(2);
					}
					
					//print_writer.println("LAYER" + (3-video_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
					TimeUnit.SECONDS.sleep(1);
//					current_layer = 5-current_layer;
				}
				break;
			case "ANIMATE-IN-ICC_IMAGE4_3":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_IMAGE4_3";
				break;
			case "ANIMATE-IN-ICC_IMAGE16_9":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_IMAGE16_9";
				break;
			case "ANIMATE-IN-LINEUPLONG_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "LINEUPLONG_ICC";
				break;
			case "ANIMATE-IN-ICC_WAGON":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_WAGON";
				break;
			case "ANIMATE-IN-EQUATIONSHORT_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "EQUATIONSHORT_ICC";
				break;
			case "ANIMATE-IN-TARGETFULL_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "TARGETFULL_ICC";
				break;
			case "ANIMATE-IN-REVIEW":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "REVIEW";
				break;
			case "ANIMATE-IN-PLAYERNAME_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Change RESET;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "PLAYERNAME_ICC";
				break;
			case "ANIMATE-IN-PLAYERVIDEO_ICC":
				print_writer.println("LAYER" + (3-video_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + video_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (3-video_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "PLAYERVIDEO_ICC";
				break;
			case "ANIMATE-IN-ICC_BOWLER-FIG":				
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_BOWLER-FIG";
				break;
			case "ANIMATE-IN-LINEUPIMAGE_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "LINEUPIMAGE_ICC";
				break;
			case "ANIMATE-IN-LINEUP_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "LINEUP_ICC";
				break;
			case "ANIMATE-IN-THIS_OVER_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "THIS_OVER_ICC";
				break;
			case "ANIMATE-IN-PLAYERFREETEXT_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "PLAYERFREETEXT_ICC";
				break;
			case "ANIMATE-IN-BATSMAN_STYLE":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "BATSMAN_STYLE";
				break;
			case "ANIMATE-IN-BOWLER_STYLE":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "BOWLER_STYLE";
				break;
			case "ANIMATE-IN-TARGET_WITH_IMG_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "TARGET_IMG";
				break;
			case "ANIMATE-IN-EQUATION_WITH_IMG_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "EQUATION_IMG";
				break;
			case"ANIMATE-IN-MATCHID_WITH_IMG_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "MATCHID_IMG";
				break;
			case "ANIMATE-IN-ICC_BALL-SPEED":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "BALL-SPEED";
				break;
			case "ANIMATE-IN-MILESTONE_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "MILESTONE_ICC";
				break;
			case "ANIMATE-IN-FOUR_ICC":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "FOUR_ICC";
				break;
			case "ANIMATE-IN-SIX_ICC":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "SIX_ICC";
				break;
			case "ANIMATE-IN-ICC_MATCHSUMMARY":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_MATCHSUMMARY";
				break;
			case "ANIMATE-IN-COMPARISON_ICC":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "COMPARISON_ICC";
				break;
			case "ANIMATE-IN-TEAMNAME_ICC":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "TEAMNAME_ICC";
				break;
			case"ANIMATE-IN-GROUP_ICC":
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "GROUP_ICC";
				break;
			case "ANIMATE-IN-ICC_BOWLER-STATS":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_BOWLER-STATS";
				break;
			case "ANIMATE-IN-TOSS_ICC":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "TOSS_ICC";
				break;
			case "ANIMATE-IN-PHASESCORE_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
				which_graphics_onscreen = "PHASESCORE_ICC";
				break;
			case "ANIMATE-IN-ICC_TEAM-BOUNDARY":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_TEAM-BOUNDARY";
				break;
			case "ANIMATE-IN-RUNRATE_ICC":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "RUNRATE_ICC";
				break;
			case "ANIMATE-IN-EQUATION_ICC":
//				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
//				TimeUnit.SECONDS.sleep(3);
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "EQUATION_ICC";
				break;
			case"ANIMATE-IN-H2H_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
				which_graphics_onscreen = "H2H_ICC";
				break;
			case "ANIMATE-IN-TARGET_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "TARGET_ICC";
				break;
			case "ANIMATE-IN-ICC_BATSMAN-STATS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "ICC_BATSMAN-STATS";
				break;
			case "ANIMATE-IN-ICC_INTRO-STATS":
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Change SHOW 0.0;");
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
				which_graphics_onscreen = "ICC_INTRO-STATS";
				break;
			case "ANIMATE-IN-EXTRAS_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				//print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "EXTRAS_ICC";
				break;
			case "ANIMATE-IN-IMG_LINE2FREE_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "IMG_LINE2FREE_ICC";
				break;
			case "ANIMATE-IN-LINE2FREE_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "LINE2FREE_ICC";
				break;
			case "ANIMATE-IN-FREETEXT_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "FREETEXT_ICC";
				break;
			case "ANIMATE-IN-BOUNDARY_ICC":	
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "BOUNDARY_ICC";
				break;	
			case "ANIMATE-IN-SCOREBOARD_ICC":
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "SCOREBOARD";
				infobar.setScoreboard_on_screen(true);
				break;
			case "ANIMATE-IN-FREEHIT_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "FREEHIT_ICC";
				break;
			case "ANIMATE-IN-MATCHID_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "MATCHID_ICC";
				break;
			case "ANIMATE-IN-PARTNERSHIP_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "PARTNERSHIP_ICC";
				break;
			case "ANIMATE-IN-SCOREBUG_ICC":
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen1 = "SCOREBUG";
				infobar.setScorebug_on_screen(true);
				break;
			case "ANIMATE-IN-INFOBAR_ICC":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "INFO";
				infobar.setInfobar_on_screen(true);
				break;
			case "ANIMATE-IN-SIX_DISTANCE":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "SIX_DISTANCE";
				break;
			case "ANIMATE-IN-QUICKHOWOUT_BS":
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "QUICKHOWOUT_BS";
				break;
			case "ANIMATE-IN-BOWLERFIG_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "BOWLERFIG_BS";
				break;
			case "ANIMATE-BS_BATTER_SCORE":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "BATTER_BS";
				break;
			case "ANIMATE-IN-HOWOUT_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "HOWOUT_BS";
				break;
			case "ANIMATE-IN-START_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "COUNTDOWN_BS";
				break;
			case "ANIMATE-IN-COUNTDOWN_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "COUNTDOWN_BS";
				break;
			case "ANIMATE-IN-PLAYERMILE_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "PLAYERMILE_BS";
				break;
			case "ANIMATE-IN-L3PLAYERPROFILE":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "PLAYERPROFILE_BALL_BS";
				break;
			case "ANIMATE-IN-LTPLAYERPROFILEBAT":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "PLAYERPROFILE_BS";
				break;	
			case "ANIMATE-IN-EQUATION_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "EQUATION_BS";
				break;
			case "ANIMATE-IN-BOUNDARIES_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "BOUNDARIES_BS";
				break;
			case "ANIMATE-IN-PROJECTED_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "PROJECTED_BS";
				break;
			case "ANIMATE-IN-FREE_BS":
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "FREE_BS";
				break;
			case "ANIMATE-IN-COMPARISON_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "COMPARISON_BS";
				break;
			case "ANIMATE-IN-TARGET_BS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "TARGET_BS";
				break;
			
			case "ANIMATE-IN-OUT_NOT_DECISION":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Cont START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				break;
			case "ANIMATE-IN-DECISION":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "DECISION";
				break;
			case "ANIMATE-IN-DLS":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "DLS";
				break;
			case "ANIMATE-IN-MATCH_IDENT":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				which_graphics_onscreen = "MATCH_IDENT";
				break;
			case "ANIMATE-IN-WICKET":
				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.SECONDS.sleep(2);
//				print_writer.println("LAYER" + (5-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Loop START;");
				which_graphics_onscreen = "MATCH_WICKET";
				break;
			
			case "CLEAR-ALL":
				System.out.println(which_graphics_onscreen);
				if(which_graphics_onscreen.equalsIgnoreCase("ICC_IMAGELOOP")) {
					loop_value = 1;
					print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER3*EVEREST*SINGLE_SCENE CLEAR;");
					which_graphics_onscreen = "";
				}else {
					print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER3*EVEREST*SINGLE_SCENE CLEAR;");
					which_graphics_onscreen = "";
					which_graphics_onscreen1 = "";
				}
				
				break;
			case "ANIMATE-OUT-SCOREBUG":
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out START;");
				which_graphics_onscreen1 = "";
				infobar.setScorebug_on_screen(false);
				break;	
			
			case "ANIMATE-OUT":
				switch(which_graphics_onscreen) {
				case "ICC_IMAGELOOP":
					loop_value = 1;
					if(loop_value == 1) {
						print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
						print_writer.println("LAYER3*EVEREST*SINGLE_SCENE CLEAR;");
					}
					processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(current_layer));
//					TimeUnit.SECONDS.sleep(2);
					print_writer.println("LAYER" + (current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
					which_graphics_onscreen = "";
					
					break;
				}
				switch(which_graphics_onscreen) {
					case "SCOREBUG":
						print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out START;");
						which_graphics_onscreen1 = "";
						infobar.setScorebug_on_screen(false);
						break;
	
					case "COUNTDOWN_BS": case "ICC_DUCK":case "ICC_CATCH":case "ICC_FIFTY":case "ICC_HUNDRED": case "POINTSTABLE":
					case "BATTER_BS":case "COMPARISON_BS":case "TARGET_BS":case "DECISION":case "FANTASYDROPDOWN":
					case "MATCH_IDENT":case "FREE_BS":case "BOUNDARIES_BS":case "PROJECTED_BS":case "EQUATION_BS":
					case "PLAYERPROFILE_BALL_BS":case "PLAYERPROFILE_BS":case "PLAYERMILE_BS":case "HOWOUT_BS":
					case "ICC_WIDE":case "IMAGEDROPDOWN":case "ICC_QUICKHOWOUT":case "PLAYERVIDEO_ICC":case "WEATHER_ICC":
					case "LONGLINEUP_ICC":case "ICC_BALL-DISTANCE":case "BOWLERFIG_BS":case "MATCH_WICKET":case "QUICKHOWOUT_BS":
					case "PARTNERSHIP_ICC":case "MATCHID_ICC":case "FREEHIT_ICC":case "BOUNDARY_ICC":case "FREETEXT_ICC":case "LINE2FREE_ICC":
					case "EXTRAS_ICC":case "ICC_BATSMAN-STATS":case "TARGET_ICC":case "EQUATION_ICC":case "RUNRATE_ICC":case "ICC_TEAM-BOUNDARY":
					case "TOSS_ICC":case "ICC_BOWLER-STATS":case "TEAMNAME_ICC":case "COMPARISON_ICC":case "ICC_MATCHSUMMARY":case"GROUP_PTSTBLE_ICC":
					case "FOUR_ICC":case "SIX_ICC":case "MILESTONE_ICC":case "BALL-SPEED":case "TARGET_IMG":case "PLAYERFREETEXT_ICC":
					case "THIS_OVER_ICC":case "LINEUP_ICC":case "LINEUPIMAGE_ICC":case "ICC_BOWLER-FIG":case "PLAYERNAME_ICC":case "REVIEW":
					case "TARGETFULL_ICC":case "EQUATIONSHORT_ICC":case "ICC_IMAGE4_3":case "ICC_IMAGE16_9":case "LINEUPLONG_ICC":case "ICC_WAGON":
					case "ICC_WICKET":case "SIX_DISTANCE":case "INFO":case "ICC_INTRO-STATS":case "EQUATION_IMG":case"MATCHID_IMG":
					case "GROUP_ICC": case "DLS":case "H2H_ICC":case "PHASESCORE_ICC": case "IMG_LINE2FREE_ICC":case "HAT_TRICK_BALL":case "HAT_TRICK":
					case "BATSMAN_STYLE": case "BOWLER_STYLE":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(current_layer));
						if(whatToProcess.equalsIgnoreCase("INFO")) {
							infobar.setInfobar_on_screen(false);
						}
	//					TimeUnit.SECONDS.sleep(2);
	//					print_writer.println("LAYER" + (current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
						which_graphics_onscreen = "";
						break;
					
				}
			}
		}
		return null;
}
	
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_VIZ": case "NEPAL_T20": case "DOAD_LLC":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*"+ animationName + " " + animationCommand +" \0");
			break;
		case "DOAD_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			
			break;
		}
		
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
	
	public Infobar populateInfobar(Infobar infobar, PrintWriter print_writer,boolean is_this_updating,String scene,MatchAllData match, String broadcaster) throws InterruptedException, IOException 
	{
		infobar = populateInfo(infobar, print_writer, false, match, broadcaster);
		infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, broadcaster);

		return infobar;
	}
	
	public void populateLineupLong(PrintWriter print_writer,boolean is_this_updating, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		switch(session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			int row = 0, captainId = 0;
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			
			Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning).findAny().orElseThrow(()->new RuntimeException("Inning not found"));
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					inning.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomText  BATTING CARD;");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
					inning.getBatting_team().getTeamName4() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamScore " + 
					CricketFunctions.getTeamScore(inning, "-", false) + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamOver " + 
					CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls())+ ";");
			
			if(inning.getBatting_team().getTeamId() == match.getSetup().getHomeTeamId()) {
				for(Player plyr : match.getSetup().getHomeSquad()) {
					if(plyr.getCaptainWicketKeeper() != null && plyr.getCaptainWicketKeeper().equalsIgnoreCase("captain")) {
						captainId = plyr.getPlayerId();
					}
				}
				if(captainId == 0) {
					if(match.getSetup().getHomeSubstitutes() != null && !match.getSetup().getHomeSubstitutes().isEmpty()) {
						for(Player plyr : match.getSetup().getHomeSubstitutes()) {
							if(plyr.getCaptainWicketKeeper() != null && plyr.getCaptainWicketKeeper().equalsIgnoreCase("captain")) {
								captainId = plyr.getPlayerId();
							}
						}
					}
				}
			}else {
				for(Player plyr : match.getSetup().getAwaySquad()) {
					if(plyr.getCaptainWicketKeeper() != null && plyr.getCaptainWicketKeeper().equalsIgnoreCase("captain")) {
						captainId = plyr.getPlayerId();
					}
				}
				if(captainId == 0) {
					if(match.getSetup().getAwaySubstitutes() != null && !match.getSetup().getAwaySubstitutes().isEmpty()) {
						for(Player plyr : match.getSetup().getAwaySubstitutes()) {
							if(plyr.getCaptainWicketKeeper() != null && plyr.getCaptainWicketKeeper().equalsIgnoreCase("captain")) {
								captainId = plyr.getPlayerId();
							}
						}
					}
				}
			}
			Collections.sort(inning.getBattingCard());
			for(BattingCard bc : inning.getBattingCard()) {
				row++;
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
						inning.getBatting_team().getTeamName4() + "\\Left_2048\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				if(bc.getPlayer().getSurname() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
							bc.getPlayer().getFirstname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
							bc.getPlayer().getSurname() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
							bc.getPlayer().getTicker_name() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
							"" + ";");
				}
				if(bc.getPlayerId() == captainId) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
							"1" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
							"0" + ";");
				}
				
				switch(bc.getStatus().toUpperCase()) {
				case CricketUtil.STILL_TO_BAT:
					if(bc.getHowOut() != null) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
								"0" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
								"1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
								bc.getRuns()  + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
								bc.getBalls() + ";");
					}else {
						if(inning.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
									"0" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
									"1" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " DNB ;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " ;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
									"0" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
									"0" + ";");
							if(bc.getPlayer().getRole().equalsIgnoreCase("BATSMAN") || bc.getPlayer().getRole().equalsIgnoreCase("BAT/KEEPER")) {
								if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("RHB")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
											icon_path + "Batsman" + CricketUtil.PNG_EXTENSION + ";");
								}else if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
											icon_path + "Batsman_Lefthand" + CricketUtil.PNG_EXTENSION + ";");
								}
							}else if(bc.getPlayer().getRole().equalsIgnoreCase("BOWLER")) {
								if(bc.getPlayer().getBowlingStyle() == null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
											icon_path + "FastBowler" + CricketUtil.PNG_EXTENSION + ";");
								}else {
									switch(bc.getPlayer().getBowlingStyle()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
												icon_path + "FastBowler" + CricketUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
												icon_path + "SpinBowlerIcon" + CricketUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}else if(bc.getPlayer().getRole().equalsIgnoreCase("ALL-ROUNDER")) {
								if(bc.getPlayer().getBowlingStyle() == null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
											icon_path + "FastBowlerAllrounder" + CricketUtil.PNG_EXTENSION + ";");
								}else {
									switch(bc.getPlayer().getBowlingStyle()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
												icon_path + "FastBowlerAllrounder" + CricketUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
												icon_path + "SpinBowlerAllrounder" + CricketUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}
						}
					}
					break;
				default:
					switch (bc.getStatus().toUpperCase()) {
					case CricketUtil.OUT:
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
								"0" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
								"1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
								bc.getRuns()  + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
								bc.getBalls() + ";");
						break;
					case CricketUtil.NOT_OUT:
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
								"1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
								"1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
								bc.getRuns() +"*" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
								bc.getBalls() + ";");
						break;
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
			
			break;
		}
	}
	
	public void populateLongLineup(PrintWriter print_writer,boolean is_this_updating, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		switch(session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			int row = 0;
			Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning).findAny().orElseThrow(()-> new RuntimeException("Inning not found"));
			
			this.status = CricketUtil.UNSUCCESSFUL;
			for(int i =1; i<= 11; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tWKT" + i + 
						"  " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWRun" + i + 
						"  " + ";");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
					inning.getBowling_team().getTeamName4() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					inning.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			if(inning.getFallsOfWickets() != null) {
				for(FallOfWicket fow : inning.getFallsOfWickets()) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tWKT" + fow.getFowNumber() + " " + 
							fow.getFowNumber() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWRun" + fow.getFowNumber() + " " + 
							fow.getFowRuns() + ";");
				}
			}
			
			if(inning.getBowlingCard() != null && inning.getBowlingCard().size()>=5) {
				for(BowlingCard boc : inning.getBowlingCard()) {
					row++;
					if(row <= 5) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBowlers " + 
								"0" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$SelectNoOfBowlers$StatAll_5*CONTAINER SET ACTIVE 1;");
					}else if(row > 5 && row <= 6) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBowlers " + 
								"1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$SelectNoOfBowlers$StatAll_6*CONTAINER SET ACTIVE 1;");
					}else if(row > 6 && row <= 7) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBowlers " + 
								"2" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$SelectNoOfBowlers$StatAll_7*CONTAINER SET ACTIVE 1;");
					}else if(row > 7 && row <= 8) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBowlers " + 
								"3" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$SelectNoOfBowlers$StatAll_8*CONTAINER SET ACTIVE 1;");
					}else if(row > 8 && row <= 9) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBowlers " + 
								"4" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$SelectNoOfBowlers$StatAll_9*CONTAINER SET ACTIVE 1;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
							inning.getBowling_team().getTeamName4() + "\\Left_2048\\" + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					
					if(boc.getPlayer().getSurname() != null) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								boc.getPlayer().getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
								boc.getPlayer().getSurname() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								boc.getPlayer().getTicker_name() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
								"" + ";");
					}
					switch (boc.getStatus().toUpperCase()) {
					case (CricketUtil.OTHER + CricketUtil.BOWLER):
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
								"0" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
								"1" + ";");
						break;
					case (CricketUtil.LAST + CricketUtil.BOWLER):
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
								"0" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
								"1" + ";");
						break;
					case (CricketUtil.CURRENT + CricketUtil.BOWLER):
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
								"1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
								"1" + ";");
						break;
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
							boc.getWickets() + "-" + boc.getRuns() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
							CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
				}
				this.status = CricketUtil.SUCCESSFUL;
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
			
			
			if(this.status.equalsIgnoreCase(CricketUtil.UNSUCCESSFUL)) {
				print_writer.println("LAYER" + (current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				current_layer = 5- current_layer;
			}
			break;
			
		}
	}
	
	public void populateMatchSummary(PrintWriter print_writer,boolean is_this_updating,int whichInning,MatchAllData match,List<Player> allPlayer, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			int row = 0;
			Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning).findAny().orElseThrow(() -> new RuntimeException("Inning Not found"));
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					inning.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inning.getBatting_team().getTeamName4() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + CricketFunctions.getTeamScore(inning, "-", false) + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversHead " + 
					(inning.getTotalOvers() == 1 && inning.getTotalBalls() == 0 ? "OVER" : "OVERS") + ";");
			
			if(inning.getBattingCard() != null) {
				Collections.sort(inning.getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
				for(BattingCard bc : inning.getBattingCard()) {
					if(row >= 3) break;
					if (bc.getRuns() > 0) {
						row++;
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam"+row+" " + bc.getPlayer().getTicker_name() + ";");
						if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor"+row+" " + bc.getRuns() + "*;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor"+row+" "+ bc.getRuns() + ";");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBall"+ row+" " + bc.getBalls() + ";");
					}
				}
				if(row == 1) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam2 " + " " + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$BottomLine$BatterAll$NameBaseBase"
							+ "*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor2 " + " " + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBall2 " + " " + ";");
				}
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$TopLine$BatterAll$NameBaseBase"
						+ "*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam1 " + " " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor1 " + " " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBall1 " + " " + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$BottomLine$BatterAll$NameBaseBase"
						+ "*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam2 " + " " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor2 " + " " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBall2 " + " " + ";");
			}
			if(inning.getBowlingCard() != null) {
				row =0;
				Collections.sort(inning.getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
				for(BowlingCard boc : inning.getBowlingCard()) {
					if(row >=3) break;
					if (boc.getWickets() > 0) {
						row++;
						
						if(row == 1) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$TopLine$BowlerAll"
									+ "*CONTAINER SET ACTIVE 1;");
						}else if(row == 2) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$BottomLine$BowlerAll"
									+ "*CONTAINER SET ACTIVE 1;");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerNam"+row+" " + boc.getPlayer().getTicker_name() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigur"+row+ " " + boc.getWickets() + "-" + boc.getRuns() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOver" +row+" " + 
								CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
					}else {
						if(row == 0) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$TopLine$BowlerAll"
									+ "*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$BottomLine$BowlerAll"
									+ "*CONTAINER SET ACTIVE 0;");
						}else if(row == 1) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$BottomLine$BowlerAll"
									+ "*CONTAINER SET ACTIVE 0;");
						}
					}
				}
				if(row == 1) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerNam2 " + " " + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$BottomLine$BowlerAll$NameBaseBase"
							+ "*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigur2 " + " " + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOver2 " + " " + ";");
				}
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$TopLine$BowlerAll$NameBaseBase"
						+ "*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerNam1 " + " " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigur1 " + " " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOver1 " + " " + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$BottomLine$BowlerAll$NameBaseBase"
						+ "*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerNam2 " + " " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigur1 " + " " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOver2 " + " " + ";");
			}
			if(whichInning == 1) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "CURRENT RUN RATE : " + inning.getRunRate() + ";");

			}else {
				if(whichInning == 2) {
					if(match.getMatch().getMatchResult() != null) {
						if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
							print_writer.println("LAYER"+current_layer+"*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " 
									+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.SHORT, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
						}
						else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
							print_writer.println("LAYER"+current_layer+"*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "MATCH TIED" + ";");
						}
						else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
							print_writer.println("LAYER"+current_layer+"*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + 
									match.getMatch().getMatchStatus().toUpperCase() + ";");
						}
						else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
							print_writer.println("LAYER"+current_layer+"*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "MATCH TIED - " + 
									match.getMatch().getMatchStatus().toUpperCase() + ";");
						}
						else {
							print_writer.println("LAYER"+current_layer+"*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " 
									+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.SHORT, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
						}
					}
					else {
						print_writer.println("LAYER"+current_layer+"*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " 
								+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.SHORT, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
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
			
			
			break;
		}
	}
	
	public Infobar populateInfo(Infobar infobar, PrintWriter print_writer,boolean is_this_updating,MatchAllData curr_match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			for(Inning inn : curr_match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						if(inn.getBatting_team().getTeamBadge().toUpperCase().toUpperCase().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								inn.getBatting_team().getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
								inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
							CricketFunctions.getTeamScore(inn, "-", false) + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversHead " + 
							(inn.getTotalOvers()==1 && inn.getTotalBalls()==0 ? "OVER" : "OVERS") + ";");
					
					if (inn.getInningNumber() == 1) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "CURRENT RUN RATE " + inn.getRunRate() + ";");
					}else {
						
						if(CricketFunctions.GetTargetData(curr_match).getRemaningRuns() <= 0 || curr_match.getMatch().getInning().get(1).getTotalWickets() >= 10 
								|| CricketFunctions.GetTargetData(curr_match).getRemaningBall() == 0) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "" + 
									CricketFunctions.GenerateMatchSummaryStatus(2, curr_match, CricketUtil.SHORT, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
						}
						else if (CricketFunctions.GetTargetData(curr_match).getRemaningRuns() == 1 && (CricketFunctions.GetTargetData(curr_match).getRemaningBall() <= 0 
					    		|| CricketFunctions.getWicketsLeft(curr_match, curr_match.getMatch().getInning().get(1).getInningNumber()) <= 0)) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "" + 
									"MATCH TIED - WINNER WILL BE DECIDED BY SUPER OVER" + ";");
						}
						else {
							if(CricketFunctions.GetTargetData(curr_match).getRemaningRuns() == 1) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "" + 
										"SCORES ARE LEVEL" + ";");
							}
							else {
								if(curr_match.getSetup().getTargetType() == null || curr_match.getSetup().getTargetType().trim().isEmpty()) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "" + 
											"NEED " + CricketFunctions.GetTargetData(curr_match).getRemaningRuns() 
											+ " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(curr_match).getRemaningRuns()).toUpperCase() + " OFF " 
												+ CricketFunctions.GetTargetData(curr_match).getRemaningBall() 
												+ " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(curr_match).getRemaningBall()).toUpperCase() + ";");
								}else {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "" + 
											"NEED " + CricketFunctions.GetTargetData(curr_match).getRemaningRuns()
											+ " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(curr_match).getRemaningRuns()).toUpperCase() 
												+ " OFF " + CricketFunctions.GetTargetData(curr_match).getRemaningBall()
												+ " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(curr_match).getRemaningBall()).toUpperCase() + " (" + curr_match.getSetup().getTargetType().toUpperCase() + ")" + ";");
								}
							}
						}
					}
				}
			}
		}
		return infobar;
	}

	public Infobar populateVizInfobarMiddle(Infobar infobar, boolean is_this_updating, PrintWriter print_writer,MatchAllData cricketMatch, String broadcaster) throws InterruptedException
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
						
						if(!is_this_updating) {
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
			return infobar;
		}
	public Infobar populateCurrentBatsmen(Infobar infobar, PrintWriter print_writer, MatchAllData match, String broadcaster,List<BattingCard> current_batsmen) throws InterruptedException
		{
			for (Inning inn : match.getMatch().getInning()) {

				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					
					if(current_batsmen != null && current_batsmen.size() >= 1) {
						if (infobar.getLast_batsmen() != null && infobar.getLast_batsmen().size() >= 1) {
							if (infobar.getLast_batsmen().get(0).getPlayerId() != current_batsmen.get(0).getPlayerId()) {
//								processAnimation(print_writer, "Batsman1Out", "START", broadcaster);
								TimeUnit.MILLISECONDS.sleep(800);
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + 
										current_batsmen.get(0).getPlayer().getTicker_name().toUpperCase() + " ;");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore2 " + current_batsmen.get(0).getRuns() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBalls2 " + current_batsmen.get(0).getBalls() + ";");
								
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + 
										current_batsmen.get(0).getPlayer().getTicker_name().toUpperCase() + " ;");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore2 " + current_batsmen.get(0).getRuns() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBalls2 " + current_batsmen.get(0).getBalls() + ";");
								
								if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) { 
									processAnimation(print_writer, "Batsman1_Dehighlight", "SHOW 10.0", broadcaster);
								}else if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									processAnimation(print_writer, "Batsman1_Dehighlight", "SHOW 0.0", broadcaster);
								}
							}
							
							if (infobar.getLast_batsmen().get(1).getPlayerId() != current_batsmen.get(1).getPlayerId()) {
//								processAnimation(print_writer, "Batsman2Out", "START", broadcaster);
								TimeUnit.MILLISECONDS.sleep(800);
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName1 " + 
										current_batsmen.get(1).getPlayer().getTicker_name().toUpperCase() + " ;");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore1 " + current_batsmen.get(1).getRuns() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBalls1 " + current_batsmen.get(1).getBalls() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName1 " + 
										current_batsmen.get(1).getPlayer().getTicker_name().toUpperCase() + " ;");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore1 " + current_batsmen.get(1).getRuns() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBalls1 " + current_batsmen.get(1).getBalls() + ";");
								
								if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) { 
									processAnimation(print_writer, "Batsman2_Dehighlight", "SHOW 10.0", broadcaster);
								}else if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									processAnimation(print_writer, "Batsman2_Dehighlight", "SHOW 0.0", broadcaster);
								}
							}
						}
						if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							if(current_batsmen.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike2 " + "1" + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike2 " + "0" + ";");
							}
						}
						if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							if(current_batsmen.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike1 " + "1" + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike1 " + "0" + ";");
							}
						}
					}
				}
			}
				
			infobar.setLast_batsmen(current_batsmen);
			return infobar;
		}
	public void populateOutNotDecision(PrintWriter print_writer,String decision, String session_selected_broadcaster) throws InterruptedException, IOException 
		{
			switch (session_selected_broadcaster.toUpperCase()) {
			case "ICC_BIGSCREEN_DOAD_SCORING":
				switch (decision.toUpperCase()) {
				case "OUT":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDecisions " + "2" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + " " + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews1 " + "OUT" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In COUNTINUE;");
					break;
				case "NOTOUT":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDecisions " + "0" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + " " + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews1 " + "NOT OUT" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In COUNTINUE;");
					break;
				case "REVERSEDNOTOUT":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDecisions " + "0" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + "DECISION REVERSED" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews1 " + "NOT OUT" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In COUNTINUE;");
					break;
				case "STANDNOTOUT":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDecisions " + "1" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + "DECISION STANDS" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews1 " + "NOT OUT" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In COUNTINUE;");
					break;
				case "REVERSEDOUT":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDecisions " + "2" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + "DECISION REVERSED" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews1 " + "OUT" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In COUNTINUE;");
					break;
				case "STANDOUT":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDecisions " + "3" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + "DECISION STANDS" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews1 " + "OUT" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In COUNTINUE;");
					break;	
				}
				break;
			}
//			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
//			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 60.0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
//			TimeUnit.SECONDS.sleep(1);
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
//			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
		}
	
	public void populateDecision(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			break;
		}
	}
	
	public void populateIdentMatch(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
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
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
			
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
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$BaseTeam1*TEXTURE1 SET TEXTURE_PATH " 
							+ base1_path + match.getMatch().getInning().get(0).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$BaseTeam2*TEXTURE1 SET TEXTURE_PATH " 
							+ base1_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$TeamA*TEXTURE2 SET TEXTURE_PATH " 
							+ text1_path + match.getMatch().getInning().get(0).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$ThirdEmpire$Decision$In$Maiin$Data$TeamB*TEXTURE2 SET TEXTURE_PATH " 
							+ text1_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					
					
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
	
	public void populateMatchID(PrintWriter print_writer,boolean is_this_updating,List<Player> allPlayer,List<Team> allTeams,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			if(match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag1 " + logo_path +
					match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
					match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
			}else if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
			}
			
			if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
			}else if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
			}
			
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " +
					match.getSetup().getMatchIdent().toUpperCase() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " +
					"LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
			
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
	
	private void populateMatchIDWithImgBs(PrintWriter print_writer,boolean is_this_updating, MatchAllData mtch,
			List<Player> allPlayers, List<Team> teams, String session_selected_broadcaster) throws InterruptedException {
		
		if(mtch.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + 
					mtch.getSetup().getHomeTeam().getTeamName4()+ ";");
		}else if(mtch.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + 
					mtch.getSetup().getHomeTeam().getTeamName4()+ ";");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + 
					mtch.getSetup().getHomeTeam().getTeamName4()+ ";");
		}
		
		
		if(mtch.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
					mtch.getSetup().getAwayTeam().getTeamName4()+ ";");
		}else if(mtch.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
					mtch.getSetup().getAwayTeam().getTeamName4()+ ";");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
					mtch.getSetup().getAwayTeam().getTeamName4()+ ";");
		}
		
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
				mtch.getSetup().getMatchIdent().toUpperCase()	+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "" + ";");
		
		
		if(mtch.getSetup().getHomeTeam().getTeamBadge().equalsIgnoreCase("NEP")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag1 " + logo_path +
					mtch.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
		}
		
		
		if(mtch.getSetup().getAwayTeam().getTeamBadge().equalsIgnoreCase("NEP")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag02 1;");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag02 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
					mtch.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
		}
		
		for (Player hs : mtch.getSetup().getHomeSquad()) {
			if (hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
					|| hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
						mtch.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + "Left_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			}
		}
		
		for (Player as : mtch.getSetup().getAwaySquad()) {
			if (as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
					|| as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 " + photo_path + 
						mtch.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + "Right_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				
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
	
	public void populateProjectedBs(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader PROJECTED\nSCORES;");
			
			String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
		    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
		    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
	        }
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 1 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							inn.getBatting_team().getTeamName4() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
							inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + 
							"@"+ proj_score_rate[0] +" (CRR)" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
							proj_score_rate[1] + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + 
							"@" + proj_score_rate[2] +" RPO" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
							proj_score_rate[3] + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + 
							"@" + proj_score_rate[4] +" RPO" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + 
							proj_score_rate[5] + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + 
							"@" + proj_score_rate[6] +" RPO" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + 
							proj_score_rate[7] + ";");
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
	public void populatePlayerProfileBat(PrintWriter print_writer,boolean is_this_updating,String viz_scene, Integer playerId, String Profile,
			String TypeofProfile, List<Tournament> this_series,Statistics stats, CricketService cricketService, MatchAllData match, 
			String session_selected_broadcaster,Configuration config) throws InterruptedException 
	{
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			if(Profile.equalsIgnoreCase("THISSERIES")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "THIS SERIES" + ";");
			}else {
				if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "T20 CAREER" + ";");
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "T20I CAREER" + ";");
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "FIRST-CLASS CAREER" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
							stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + ";");
				}
			}
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam " + inn.getBatting_team().getTeamName3() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.getTeamScore(inn, "-", false) + ";");
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

		Player plyr = getPlayerFromMatchData(playerId, match);
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
				(plyr.getSurname() != null ? plyr.getSurname() : "") + ";");
		
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			
			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getHomeTeam().getTeamName4() + 
						"\\" + "Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				
			} else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ "\\\\" + config.getPrimaryIpAddress() + "\\\\" + local_photo_path + 
						match.getSetup().getHomeTeam().getTeamName4() + 
						"\\" + "Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			}
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getHomeTeam().getTeamName4() + 
//					"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
		}
		else {
			
			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getAwayTeam().getTeamName4() + 
						"\\" + "Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				
			} else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ "\\\\" + config.getPrimaryIpAddress() + "\\\\" + local_photo_path + 
						match.getSetup().getAwayTeam().getTeamName4() + 
						"\\" + "Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			}
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + match.getSetup().getAwayTeam().getTeamName4() + 
//					"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
		}
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			
			if(plyr.getBattingStyle() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + 
						CricketFunctions.getbattingstyle(plyr.getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + " " + ";");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "MATCHES" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "RUNS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "BEST" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "STRIKE RATE" + ";");
			
			if(Profile.equalsIgnoreCase("THISSERIES")) {
				List<BestStats> top_batsman_beststats = new ArrayList<BestStats>();
				for (Tournament tourn : this_series) {
					for (BestStats bs : tourn.getBatsman_best_Stats()) {
						top_batsman_beststats.add(bs);
					}
				}
				Collections.sort(top_batsman_beststats, new CricketFunctions.PlayerBestStatsComparator());
				
				Tournament this_series_data = this_series.stream().filter(ts -> ts.getPlayerId() == playerId).findAny().orElse(null);
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + this_series_data.getMatches() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + this_series_data.getRuns() + ";");
				for (int j = 0; j <= top_batsman_beststats.size() - 1; j++) {
					if (top_batsman_beststats.get(j).getPlayerId() == playerId) {
						if (top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " 
									+ top_batsman_beststats.get(j).getBestEquation() / 2 + ";");
							
						} else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " 
									+ (top_batsman_beststats.get(j).getBestEquation() - 1) / 2 + "*" + ";");
							
						}
						break;
					} else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + "-" + ";");
					}
				}
				if(this_series_data.getBallsFaced() == 0 || this_series_data.getRuns()== 0) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + "-" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + 
							CricketFunctions.generateStrikeRate(this_series_data.getRuns(), this_series_data.getBallsFaced(), 2) + ";");
				}
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + stats.getMatches() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + stats.getRuns() + ";");
				if(stats.getBestScore().equalsIgnoreCase("0")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + "-" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + stats.getBestScore() + ";");
				}
				if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + "-" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + 
							CricketFunctions.generateStrikeRate(stats.getRuns(), stats.getBallsFaced(), 0) + ";");
				}
			}
			break;
		case CricketUtil.BOWLER:
			if(plyr.getBattingStyle() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + 
						CricketFunctions.getbattingstyle(plyr.getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + " " + ";");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "MATCHES" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "WICKETS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "BEST" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "ECONOMY" + ";");
			
			if(Profile.equalsIgnoreCase("THISSERIES")) {
				List<BestStats> top_bowler_beststats = new ArrayList<BestStats>();
				for (Tournament tourn : this_series) {
					for (BestStats bfig : tourn.getBowler_best_Stats()) {
						top_bowler_beststats.add(bfig);
					}
				}
				Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
				
				Tournament this_series_data = this_series.stream().filter(ts -> ts.getPlayerId() == playerId).findAny().orElse(null);
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + this_series_data.getMatches() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + this_series_data.getWickets() + ";");
				for (int j = 0; j <= top_bowler_beststats.size() - 1; j++) {
					if (top_bowler_beststats.get(j).getPlayerId() == playerId) {
						if (top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + 
									((top_bowler_beststats.get(j).getBestEquation() / 1000) + 1) + "-"+ (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + ";");
						} else if (top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + 
									(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-"+ Math.abs(top_bowler_beststats.get(j).getBestEquation()) + ";");
						}
						break;
					} else if (top_bowler_beststats.get(j).getPlayerId() != playerId) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + "-" + ";");
					}
				}
				if(this_series_data.getBallsBowled() == 0 && this_series_data.getRunsConceded() == 0) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + "-" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + CricketFunctions.getEconomy(this_series_data.getRunsConceded(), this_series_data.getBallsBowled(), 1, "-") + ";");
				}
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + stats.getMatches() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + stats.getWickets() + ";");
				if(stats.getBestFigures().equalsIgnoreCase("0")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + "-" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + stats.getBestFigures() + ";");
				}
				if(stats.getBallsBowled() == 0 && stats.getRunsConceded()== 0) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + "-" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + CricketFunctions.getEconomy(stats.getRunsConceded(), stats.getBallsBowled(), 1, "-") + ";");
				}
			}
			break;
		}
		this.status = CricketUtil.SUCCESSFUL;
		
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

	}
	
	public void populateThisOver(PrintWriter print_writer, boolean is_this_updating, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException {
		int playerId = -1;
		for(Inning inn : match.getMatch().getInning()) {
			if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
				for(BowlingCard boc : inn.getBowlingCard()) {
					if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)
							|| boc.getStatus().equalsIgnoreCase(CricketUtil.LAST+CricketUtil.BOWLER)) {
						playerId = boc.getPlayerId();
					}
				}
				String[] this_over = CricketFunctions.getEventsText(CricketUtil.OVER,playerId,",", match.getEventFile().getEvents(),0).split(",");
				
				if(this_over.length <=6) {
					for(int j = 1; j<=6; j++) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall" + j + " " + "" + ";");	
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBalls " + "0" + ";");
				}else if(this_over.length == 7) {
					for(int j = 1; j<=7; j++) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall" + j + " " + "" + ";");	
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBalls " + "1" + ";");
				}else if(this_over.length == 8) {
					for(int j = 1; j<=8; j++) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall" + j + " " + "" + ";");	
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBalls " + "2" + ";");
				}else if(this_over.length == 9) {
					for(int j = 1; j<=9; j++) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall" + j + " " + "" + ";");	
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBalls " + "3" + ";");
				}
				for(int i=0;i < this_over.length;i++) {
					if(this_over[i].toUpperCase().contains("BOUNDARY")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + this_over[i].replace("BOUNDARY", "")+ ";");	
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + this_over[i] + ";");	
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
	public void populatePlayerBatAndBowlStyle(PrintWriter print_writer, int inning, int player_id, String whichType,MatchAllData match,String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			Player plyr = getPlayerFromMatchData(player_id, match);
			
			switch(whichType) {
			case "BAT":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == inning) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName "+
								inn.getBatting_team().getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamNameFull "+
								inn.getBatting_team().getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						
						if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path
									+ inn.getBatting_team().getTeamName4().toUpperCase() + "\\Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							
						} else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ "\\\\" + 
									config.getPrimaryIpAddress() + "\\\\" + local_photo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\\"
									+ "Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}
						
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFull_name() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + CricketFunctions.
								getbattingstyle(plyr.getBattingStyle(),CricketUtil.FULL, true, false).toUpperCase() + ";");
					}
				}
				break;
			case "BOWL":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == inning) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName "+
								inn.getBowling_team().getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamNameFull "+
								inn.getBowling_team().getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								inn.getBowling_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						
						if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path
									+ inn.getBowling_team().getTeamName4().toUpperCase() + "\\Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							
						} else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ "\\\\" + 
									config.getPrimaryIpAddress() + "\\\\" + local_photo_path + inn.getBowling_team().getTeamName4().toUpperCase() + "\\"
									+ "Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}
						
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFull_name() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
								CricketFunctions.getbowlingstyle(plyr.getBowlingStyle()).toUpperCase() + ";");
					}
				}
				break;
			}

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
			
			break;
		}
	}
	
	public void populateWeather(PrintWriter print_writer,boolean is_this_updating,String data1,String data2,String data3,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + data1 + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + data2 + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + data3 + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tVenue " + 
					match.getSetup().getGround().getCity() + ";");
			

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
	
	public void populateFreeHit(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 230.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	private void populateHatTrick(PrintWriter print_writer,boolean is_this_updating,
			String session_selected_broadcaster2) {
		
		
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
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "FREEHIT" + ";");
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
	
	public void populateFour(PrintWriter print_writer,boolean is_this_updating,String scene, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "4" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "0" + ";");
		
		//CricketFunctions.processCricketStats();
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
	
	public void populateSix(PrintWriter print_writer,boolean is_this_updating,String scene, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "6" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "1" + ";");

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
	
	public void populateWicket(PrintWriter print_writer,boolean is_this_updating,String scene, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "WICKET" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "7" + ";");

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
	
	public void populateWide(PrintWriter print_writer,boolean is_this_updating,String scene, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "WIDE" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "0" + ";");

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
	
	public void populateDuck(PrintWriter print_writer,boolean is_this_updating,String scene, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "DUCK" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "5" + ";");

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
	
	public void populateExtras(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					if(inn.getBatting_team().getTeamBadge().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							inn.getBatting_team().getTeamName4() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "BYES" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + inn.getTotalByes() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "LEG BYES" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + inn.getTotalLegByes() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "WIDES" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + inn.getTotalWides() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "NO BALLS" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + inn.getTotalNoBalls() + ";");
					
					if(inn.getTotalPenalties() != 0) {
						int total = 0;
						total = inn.getTotalByes() + inn.getTotalLegByes() + inn.getTotalWides() + inn.getTotalNoBalls() + inn.getTotalPenalties();
						
						//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vExtraOptions " + "1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$StatAll$StatGrp5*CONTAINER SET ACTIVE 1 ;");

						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead5 " + "PENALTIES" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue5 " + inn.getTotalPenalties() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras " + total + ";");

					}else {
						int total = 0;
						total = inn.getTotalByes() + inn.getTotalLegByes() + inn.getTotalWides() + inn.getTotalNoBalls();
						//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras " + total + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$StatAll$StatGrp5*CONTAINER SET ACTIVE 0 ;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras " + total + ";");

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
			
			break;
		}
	}
	
	public void populateReview(PrintWriter print_writer,boolean is_this_updating,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			String text_to_return = "";
			int lineIndex1 = 1;
		    boolean found1 = false;
			BufferedReader br = new BufferedReader(new FileReader(CricketUtil.CRICKET_DIRECTORY + "ICC_Reviews.txt"));
		
		    while( (text_to_return = br.readLine()) != null) {
		        if(lineIndex1 == 1) {
		        	print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue1 " + text_to_return.split(" ")[0] + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + text_to_return.split(" ")[1] + ";");
					
		            found1 = true;
		            break;
		        }
		        lineIndex1++;
		    }
		    if(!found1) {
		    	//System.out.println("Line Not There");
		    }
		    if(match.getSetup().getHomeTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag1 " + logo_path +
						match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}if(match.getSetup().getAwayTeam().getTeamBadge().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
						match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + 
					match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " + 
					match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");

//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews1 " + data1 + ";");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews2 " + data2 + ";");
			
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
	
	public void populateFreeText(PrintWriter print_writer,boolean is_this_updating,String scene, NameSuper ns, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + ns.getFirstname() + ";");
			
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
	
	public void populateline2FreeText(PrintWriter print_writer,boolean is_this_updating,String scene, String data1, String data2, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + data1 + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + data2 + ";");
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 260.0;");
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
	public void populateImgline2FreeText(PrintWriter print_writer,boolean is_this_updating,String scene, String data1, String data2,String data3,List<Team> team, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			if(data1.toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			
			for(Team tm : team) {
				if(tm.getTeamId() == Integer.valueOf(data1)) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							tm.getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				}
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + data2 + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + data3 + ";");
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 260.0;");
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
	
	public void populateTeamBoundary(PrintWriter print_writer,boolean is_this_updating,int inning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "BOUNDARIES" + ";");
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == inning) {
					if(inn.getBatting_team().getTeamName4().equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName4())){
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "FOURS" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + inn.getTotalFours() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "SIXES" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + inn.getTotalSixes() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.getTeamScore(inn, "-", false) + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");
//					if(inn.getInningNumber() == 1) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + " CURRENT RUN RATE : "+inn.getRunRate() + ";");
//
//					}else {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.SHORT,
//								session_selected_broadcaster).toUpperCase() + ";");
//
//					}
					if(inn.getBatting_team().getTeamBadge().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
							inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
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
			
			
			break;
		}
	}
	
	public void populateRunRate(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase("YES") && inn.getInningNumber() == 2) {
					
					if(inn.getBatting_team().getTeamBadge().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName4() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + CricketFunctions.getTeamScore(inn, "-", false) + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "CURRENT RUN RATE" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + inn.getRunRate() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "REQUIRED RUN RATE" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0,
							CricketFunctions.GetTargetData(match).getRemaningBall(), 2, match) + ";");

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.SHORT,
							"|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
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
			
			
			break;
		}
	}
	
	public void populateComparison(PrintWriter print_writer,boolean is_this_updating ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			for (Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					if(inn.getBowling_team().getTeamBadge().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag1 " + logo_path +
								inn.getBowling_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
					if(inn.getBatting_team().getTeamBadge().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 0;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
								inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "AFTER " + 
									CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
					
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + inn.getBowling_team().getTeamName4().toUpperCase() + " WERE" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " + inn.getBatting_team().getTeamName4().toUpperCase() + " ARE" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore1 " + CricketFunctions.compareInningData(match, "-", 1,
													match.getEventFile().getEvents()) + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore2 " + CricketFunctions.compareInningData(match, "-", 2,
													match.getEventFile().getEvents()) + ";");
					
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
			
			
			break;
		}
	}
	
	public void populateToss(PrintWriter print_writer,boolean is_this_updating,MatchAllData session_match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			if(session_match.getSetup().getTossWinningTeam() == session_match.getSetup().getHomeTeamId()) {

				if(session_match.getSetup().getHomeTeam().getTeamBadge().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						session_match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				if(session_match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							session_match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else if(session_match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							session_match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								session_match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");

				}
				

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "WON THE TOSS AND" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "ELECTED TO " + session_match.getSetup().getTossWinningDecision().toUpperCase() + ";");
				

			}else {
				
				if(session_match.getSetup().getAwayTeam().getTeamBadge().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						session_match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				if(session_match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							session_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else if(session_match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							session_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								session_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");

				}
				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
//						session_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "WON THE TOSS AND" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "ELECTED TO " + session_match.getSetup().getTossWinningDecision().toUpperCase() + ";");
				
				
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
			
			
			break;
		}
	}
	
	public void populateTeamName(PrintWriter print_writer,boolean is_this_updating,String name, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			if(name.equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName4())){
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
			}
			if(name.toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
					name.toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
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
	
	private void populateFallOfWickets(PrintWriter print_writer,boolean is_this_updating, String string, int team_id, int player_id, List<Player> allPlayer,List<Team> allTeams,
			MatchAllData match, String session_selected_broadcaster2) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOutQuick's inning is null";
		} else {

			this.status = "STILL";
			
			for (Inning inn : match.getMatch().getInning()) {
//				if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					for (BattingCard bc : inn.getBattingCard()) {
						
						if (bc.getPlayerId() == player_id) {
							
							if(inn.getBatting_team().getTeamBadge().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
									inn.getBatting_team().getTeamName4().toUpperCase() + "\\Right_2048\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							
							if (bc.getPlayer().getSurname() != null) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
										bc.getPlayer().getFirstname() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
										bc.getPlayer().getSurname() + ";");
							} else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
										"" + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
										bc.getPlayer().getFirstname() + ";");
							}
							
							
							if(bc.getHowOut().equalsIgnoreCase("notOut")) {
								this.status = CricketUtil.UNSUCCESSFUL;
								current_layer = 5 - current_layer; 
							}
							
							if(bc.getHowOutText() == null) {
								if(bc.getHowOut()!=null) {
									if(bc.getHowOut().equalsIgnoreCase("timed_out")) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												"timed out" + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													" " + ";");
									}else if(bc.getHowOut().equalsIgnoreCase("retired_hurt")) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												"RETIRED HURT" + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													" " + ";");
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												bc.getHowOut() + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													" " + ";");
									}
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
											" " + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
												" " + ";");
								}
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFow " + 
										" STRIKE RATE   " + bc.getStrikeRate() + ";");
								
								this.status = CricketUtil.SUCCESSFUL;
							}else if (bc.getHowOutText().trim().equalsIgnoreCase("")){
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
											" " + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
											" " + ";");
								this.status = CricketUtil.SUCCESSFUL;
							} else {
								if (bc.getHowOut().equalsIgnoreCase(CricketUtil.LBW)) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
											bc.getHowOutText() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
												" " + ";");
									this.status = CricketUtil.SUCCESSFUL;
								}else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
											bc.getHowOutText() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
												" " + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFow " + 
											" STRIKE RATE   " + bc.getStrikeRate() + ";");
									this.status = CricketUtil.SUCCESSFUL;
								}else if (bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT) || 
											bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)) {
									
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												"run out" + " (impact - " + bc.getHowOutFielder().getTicker_name() + ") "+bc.getHowOutPartTwo() + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													" " + ";");
										this.status = CricketUtil.SUCCESSFUL;
									}else {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													"run out"+" (sub - "+ bc.getHowOutFielder().getTicker_name()+") " + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
														" " + ";");
											this.status = CricketUtil.SUCCESSFUL;
										}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")){
											
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													"run out"+" (sub)" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
														" " + ";");
											this.status = CricketUtil.SUCCESSFUL;
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													bc.getHowOutText() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
														" " + ";");
											this.status = CricketUtil.SUCCESSFUL;
										}
									}

								} else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												"c"+ " (impact - "+bc.getHowOutFielder().getTicker_name()+") " + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
												bc.getHowOutPartTwo() + ";");
										this.status = CricketUtil.SUCCESSFUL;
									}else {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													"c (sub - "+bc.getHowOutFielder().getTicker_name()+") " + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													bc.getHowOutPartTwo() + ";");
											this.status = CricketUtil.SUCCESSFUL;
										}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													"c (sub)" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
														" " + ";");
											this.status = CricketUtil.SUCCESSFUL;
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													bc.getHowOutPartOne() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													bc.getHowOutPartTwo() + ";");
											this.status = CricketUtil.SUCCESSFUL;
										}
										
									}
								} else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)) {
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),
													inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												bc.getHowOutPartOne().replace("(SUB)", "") + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
												bc.getHowOutPartTwo() + ";");
										this.status = CricketUtil.SUCCESSFUL;
									} else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												bc.getHowOutPartOne() + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
												bc.getHowOutPartTwo() + ";");
										this.status = CricketUtil.SUCCESSFUL;
									}
								} else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
											bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
											bc.getHowOutPartTwo() + ";");
									this.status = CricketUtil.SUCCESSFUL;
								}
							}
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreFOW " + 
									bc.getRuns() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsFOW " + 
									bc.getBalls() + ";");
							
							if (inn.getFallsOfWickets().size() > 0) {
								for(FallOfWicket fow : inn.getFallsOfWickets()) {
									if (fow.getFowPlayerID() == player_id) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFow " + 
												"      FALL OF WICKET   " + fow.getFowRuns() + "-" + fow.getFowNumber() + ";");
										break;
									}
								}	
							}
						}
					}
//				}
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
	public void populateQuickHowOut(PrintWriter print_writer,boolean is_this_updating, MatchAllData match) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOutQuick's inning is null";
		} else {

			for (Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					if(inn.getBatting_team().getTeamBadge().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					for (BattingCard bc : inn.getBattingCard()) {
						if (inn.getFallsOfWickets().size() > 0) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFow " + "      FALL OF WICKET   " + 
									inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowRuns() +"-"+ 
									inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowNumber() + ";");
							
							if (inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc
									.getPlayerId()) {
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
										inn.getBatting_team().getTeamName4().toUpperCase() + "\\Right_2048\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								
								if (bc.getPlayer().getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
											bc.getPlayer().getFirstname() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
											bc.getPlayer().getSurname() + ";");
								} else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
											"" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
											bc.getPlayer().getFirstname() + ";");
								}
								
								if (bc.getHowOutText().trim().equalsIgnoreCase("")){
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												" " + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
												" " + ";");
									this.status = CricketUtil.SUCCESSFUL;
								} else {
									if (bc.getHowOut().equalsIgnoreCase(CricketUtil.LBW)) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												bc.getHowOutText() + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													" " + ";");
										this.status = CricketUtil.SUCCESSFUL;
									} else if (bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT) || 
												bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)) {
										
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													"run out" + " (impact - " + bc.getHowOutFielder().getTicker_name() + ") "+bc.getHowOutPartTwo() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
														" " + ";");
											this.status = CricketUtil.SUCCESSFUL;
										}else {
											if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
												
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
														"run out"+" (sub - "+ bc.getHowOutFielder().getTicker_name()+") " + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
															" " + ";");
												this.status = CricketUtil.SUCCESSFUL;
											}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")){
												
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
														"run out"+" (sub)" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
															" " + ";");
												this.status = CricketUtil.SUCCESSFUL;
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
														bc.getHowOutText() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
															" " + ";");
												this.status = CricketUtil.SUCCESSFUL;
											}
										}

									} else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													"c"+ " (impact - "+bc.getHowOutFielder().getTicker_name()+") " + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													bc.getHowOutPartTwo() + ";");
											this.status = CricketUtil.SUCCESSFUL;
										}else {
											if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
														"c (sub - "+bc.getHowOutFielder().getTicker_name()+") " + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
														bc.getHowOutPartTwo() + ";");
												this.status = CricketUtil.SUCCESSFUL;
											}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
														"c (sub)" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
															" " + ";");
												this.status = CricketUtil.SUCCESSFUL;
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
														bc.getHowOutPartOne() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
														bc.getHowOutPartTwo() + ";");
												this.status = CricketUtil.SUCCESSFUL;
											}
											
										}
									} else if (bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)) {
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),
														inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													bc.getHowOutPartOne().replace("(SUB)", "") + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													bc.getHowOutPartTwo() + ";");
											this.status = CricketUtil.SUCCESSFUL;
										} else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
													bc.getHowOutPartOne() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
													bc.getHowOutPartTwo() + ";");
											this.status = CricketUtil.SUCCESSFUL;
										}
									} else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + 
												bc.getHowOutPartOne() + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + 
												bc.getHowOutPartTwo() + ";");
										this.status = CricketUtil.SUCCESSFUL;
									}
								}
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreFOW " + 
										bc.getRuns() + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsFOW " + 
										bc.getBalls() + ";");
							}
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
	
	public void populateTargetFull(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			for (Inning inn : match.getMatch().getInning()) {
				
				if(inn.getInningNumber() == 1) {
					int runs = 0;
					runs = inn.getTotalRuns() + 1;
					if(inn.getBatting_team().getTeamName4().equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName4())) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + runs + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
					
					if (match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)
							&& match.getSetup().getMaxOvers() == 1) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
								(match.getSetup().getMaxOvers() * 6) + " BALLS" + ";");
					} else {

						if (match.getSetup().getTargetOvers() == ""
								|| match.getSetup().getTargetOvers().trim().isEmpty()
										&& match.getSetup().getTargetRuns() == 0) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
									(match.getSetup().getMaxOvers()) + " OVERS" + ";");
						} else {
							if (match.getSetup().getTargetOvers() != "") {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
										match.getSetup().getTargetOvers() + " OVERS" + ";");
							}
							if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
										match.getSetup().getTargetOvers() + " OVERS (VJD)" + ";");

							} else if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
										match.getSetup().getTargetOvers() + " OVERS (DLS)" + ";");
							}
						}
					}
					
				}else if (inn.getInningNumber() == 2) {

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							inn.getBatting_team().getTeamName4().toUpperCase() + ";");
					
					if (match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)
							&& match.getSetup().getMaxOvers() == 1) {
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
								CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
								(match.getSetup().getMaxOvers() * 6) + " BALLS" + ";");
					} else {

						if (match.getSetup().getTargetOvers() == ""
								|| match.getSetup().getTargetOvers().trim().isEmpty()
										&& match.getSetup().getTargetRuns() == 0) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
									CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
									(match.getSetup().getMaxOvers()) + " OVERS" + ";");
						} else {
							if (match.getSetup().getTargetOvers() != "") {
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
										CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
										match.getSetup().getTargetOvers() + " OVERS" + ";");
							}
							if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
										CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
										match.getSetup().getTargetOvers() + " OVERS (VJD)" + ";");

							} else if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {

								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
										CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
										match.getSetup().getTargetOvers() + " OVERS (DLS)" + ";");
							}
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
			break;
		}
	}
	
	public void populateTarget(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "TARGET" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUNS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" + ";");
			
			for (Inning inn : match.getMatch().getInning()) {
				
				if(inn.getInningNumber() == 1) {
					int runs = 0;
					runs = inn.getTotalRuns() + 1;
					if(inn.getBatting_team().getTeamName4().equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName4())) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + runs + ";");
					
					if (match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)
							&& match.getSetup().getMaxOvers() == 1) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
								(match.getSetup().getMaxOvers() * 6) + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" + ";");
					} else {

						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS" + ";");
						
						if (match.getSetup().getTargetOvers() == "" || match.getSetup().getTargetOvers().trim().isEmpty() 
								&& match.getSetup().getTargetRuns() == 0) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
									(match.getSetup().getMaxOvers()) + ";");
						} else {
							if (match.getSetup().getTargetOvers() != "") {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
										match.getSetup().getTargetOvers() + ";");
							}
							if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS (VJD)" + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
										match.getSetup().getTargetOvers() + ";");

							} else if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS (DLS)" + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
										match.getSetup().getTargetOvers() + ";");
							}
						}
					}
					
				}else if (inn.getInningNumber() == 2) {

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					if (match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)
							&& match.getSetup().getMaxOvers() == 1) {
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
								CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
								(match.getSetup().getMaxOvers() * 6) + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" + ";");
					} else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS" + ";");
						if (match.getSetup().getTargetOvers() == ""
								|| match.getSetup().getTargetOvers().trim().isEmpty()
										&& match.getSetup().getTargetRuns() == 0) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
									CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
									(match.getSetup().getMaxOvers()) + ";");
						} else {
							
							if (match.getSetup().getTargetOvers() != "") {
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
										CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
										match.getSetup().getTargetOvers() + ";");
							}
							if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
										CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS (VJD)" + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
										match.getSetup().getTargetOvers() + ";");

							} else if (match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {

								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
										CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS (DLS)" + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
										match.getSetup().getTargetOvers() + ";");
							}
						}
					}
					
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
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras @" + 
							CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2, match) + " RPO;");
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
			
			
			break;
		}
	}
	
	public void populateTargetWithImgBs(PrintWriter print_writer,boolean is_this_updating,MatchAllData curr_match,List<Player>allPlayers,List<Team> teams,
			String session_selected_broadcaster,Configuration config) throws Exception {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUNS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" + ";");
			
			for(Inning inn : curr_match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					if(inn.getInningNumber() == 1) {
						int runs = 0;
						runs = inn.getTotalRuns() + 1;
						
						if(inn.getBatting_team().getTeamBadge().equalsIgnoreCase(curr_match.getSetup().getHomeTeam().getTeamBadge())) {
							if(inn.getBowling_team().getTeamBadge().toUpperCase().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									curr_match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION +";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
									curr_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
							TimeUnit.MILLISECONDS.sleep(200);
						}else {
							if(inn.getBowling_team().getTeamBadge().toUpperCase().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									curr_match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION +";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
									curr_match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
							TimeUnit.MILLISECONDS.sleep(200);
						}
						
						
						//-------------------------------------
						
						for (Player hs : curr_match.getSetup().getHomeSquad()) {
							if (hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
									|| hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
											curr_match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + "Left_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								} else {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + "\\\\" + config.getPrimaryIpAddress()
													+ "\\\\" + local_photo_path
													+ curr_match.getSetup().getHomeTeam().getTeamName4().toUpperCase()
													+ "\\\\" + "Left_2048\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}
							}
						}
						
						for (Player as : curr_match.getSetup().getAwaySquad()) {
							if (as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
									|| as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 " + photo_path + 
											curr_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + "Right_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
								} else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 " + "\\\\" + config.getPrimaryIpAddress()
													+ "\\\\" + local_photo_path
													+ curr_match.getSetup().getAwayTeam().getTeamName4().toUpperCase()
													+ "\\\\" + "Right_2048\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}
							}
						}
						//-------------------------------------
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue1 " + runs + ";");
						
						if (curr_match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)
								&& curr_match.getSetup().getMaxOvers() == 1) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
									(curr_match.getSetup().getMaxOvers() * 6) + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" + ";");
						} else {

							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS" + ";");
							
							if (curr_match.getSetup().getTargetOvers() == ""
									|| curr_match.getSetup().getTargetOvers().trim().isEmpty()
											&& curr_match.getSetup().getTargetRuns() == 0) {
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
										(curr_match.getSetup().getMaxOvers()) + ";");
							} else {
								if (curr_match.getSetup().getTargetOvers() != "") {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
											curr_match.getSetup().getTargetOvers() + ";");
								}
								if (curr_match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS (VJD)" + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
											curr_match.getSetup().getTargetOvers() + ";");

								} else if (curr_match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS (DLS)" + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
											curr_match.getSetup().getTargetOvers() + ";");
								}
							}
						}
						
						break;
					}else if(inn.getInningNumber() == 2) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION +";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								inn.getBatting_team().getTeamName4().toUpperCase() + ";");
						
						TimeUnit.MILLISECONDS.sleep(200);
						
						for (Player hs : curr_match.getSetup().getHomeSquad()) {
							if (hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
									|| hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
											curr_match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + "Left_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								} else {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + "\\\\" + config.getPrimaryIpAddress()
													+ "\\\\" + local_photo_path
													+ curr_match.getSetup().getHomeTeam().getTeamName4().toUpperCase()
													+ "\\\\" + "Left_2048\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}
							}
						}
						
						for (Player as : curr_match.getSetup().getAwaySquad()) {
							if (as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
									|| as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 " + photo_path + 
											curr_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + "Right_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
								} else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 " + "\\\\" + config.getPrimaryIpAddress()
													+ "\\\\" + local_photo_path
													+ curr_match.getSetup().getAwayTeam().getTeamName4().toUpperCase()
													+ "\\\\" + "Right_2048\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}
							}
						}
						
						if (curr_match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)
								&& curr_match.getSetup().getMaxOvers() == 1) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue1 " + 
									CricketFunctions.GetTargetData(curr_match).getTargetRuns() + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
									(curr_match.getSetup().getMaxOvers() * 6) + ";");
						} else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS" + ";");
							if (curr_match.getSetup().getTargetOvers() == ""
									|| curr_match.getSetup().getTargetOvers().trim().isEmpty()
											&& curr_match.getSetup().getTargetRuns() == 0) {
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue1 " + 
										CricketFunctions.GetTargetData(curr_match).getTargetRuns() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
										(curr_match.getSetup().getMaxOvers()) + ";");
							} else {
								
								if (curr_match.getSetup().getTargetOvers() != "") {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue1 " + 
											CricketFunctions.GetTargetData(curr_match).getTargetRuns() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
											curr_match.getSetup().getTargetOvers() + ";");
								}
								if (curr_match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue1 " + 
											CricketFunctions.GetTargetData(curr_match).getTargetRuns() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS (VJD)" + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
											curr_match.getSetup().getTargetOvers() + ";");

								} else if (curr_match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {

									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue1 " + 
											CricketFunctions.GetTargetData(curr_match).getTargetRuns() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "OVERS (DLS)" + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
											curr_match.getSetup().getTargetOvers() + ";");
								}
							}
						}
						break;
					}
				}
			}
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUNS"  +";");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" +";");
			
			
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
	
	public void populateEquation(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String broadcaster)
			throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			if (CricketFunctions.GetTargetData(match).getRemaningRuns() == 0
					|| match.getMatch().getInning().get(1).getTotalWickets() >= 10
					|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
				
				//south africa win by 4 wickets
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				
				if(CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ").length == 6) {
					
					if((CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[0] + " " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[1]).
							equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName1())) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[0] + " " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[1]+ ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + 
							"WIN BY"+ ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[4]+ ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[5]+ ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + 
							"" + ";");
				}else {
					if(CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.SHORT, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[0].
							equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName4())) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.SHORT, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[0] + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + 
							"WIN BY"+ ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.SHORT, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[3]+ ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.SHORT, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[4]+ ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + 
							"" + ";");
				}
			} else {
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "NEED"+ ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
						CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" 
						+ CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + " TO WIN" + ";");

				if (!match.getSetup().getTargetOvers().equalsIgnoreCase("")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + 
							"FROM " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(Integer.valueOf(
									CricketFunctions.GetTargetData(match).getRemaningBall())).toUpperCase() + " (" + match.getSetup().getTargetType().toUpperCase() + ");");
				} else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + 
							"FROM " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(Integer.valueOf(CricketFunctions.GetTargetData(match).getRemaningBall())).toUpperCase() + ";");
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
	
	public void populateEquationShort(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "EQUATION" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
					match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUN" + 
					CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
					CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
			
			if (!match.getSetup().getTargetOvers().equalsIgnoreCase("")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
						CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALL" + 
						CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (" + 
						match.getSetup().getTargetType().toUpperCase() + ");");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras @" + CricketFunctions.
						generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2, match) + " RPO;");
			} else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
						CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALL" + 
						CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras @" + CricketFunctions.
						generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2, match) + " RPO;");
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
			
			
			break;
		}
	}
	
	private void populateEquationWithImgBs(PrintWriter print_writer, boolean is_this_updating, MatchAllData match,
			List<Player> allPlayers, List<Team> teams, String broadcaster,Configuration config) throws InterruptedException {
		
		for (Player hs : match.getSetup().getHomeSquad()) {
			if (hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
					|| hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
				if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + "Left_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				} else {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + "\\\\" + config.getPrimaryIpAddress()
									+ "\\\\" + local_photo_path
									+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase()
									+ "\\\\" + "Left_2048\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}
			}
		}
		for (Player as : match.getSetup().getAwaySquad()) {
			if (as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)
					|| as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
				if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + "Right_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				} else {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 " + "\\\\" + config.getPrimaryIpAddress()
									+ "\\\\" + local_photo_path
									+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase()
									+ "\\\\" + "Right_2048\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}
			}
		}
		
		if (CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10
				|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			
			if(CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ").length == 6) {
				
				if((CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[0] + " " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[1]).
						equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName1())) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[0] + " " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[1]+ ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + 
						"WIN BY"+ ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[4]+ ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[5]+ ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + 
						"" + ";");
			}else {
				
				if(CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[0].
						equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName1())) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[0] + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + 
						"WIN BY"+ ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[3]+ ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase().split(" ")[4]+ ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + 
						"" + ";");
			}
		} else {
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
					match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + 
					"NEED"+ ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + 
					CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + 
					"RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + " TO WIN" + ";");

			if (!match.getSetup().getTargetOvers().equalsIgnoreCase("")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
						CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() 
						+ " (" + match.getSetup().getTargetType().toUpperCase() + ";");
			} else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + CricketFunctions.GetTargetData(match).getRemaningBall() + 
						" BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
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
	public void populateBowlerStats(PrintWriter print_writer,boolean is_this_updating,String string,Integer inning,Integer player_id,List<Player> allPlayer, 
			List<Team> allTeams,MatchAllData match,String session_selected_broadcaster2,Configuration config) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = "STILL";
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == inning) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getPlayerId() == player_id) {
							
							if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										inn.getBowling_team().getTeamName4() + "\\" + "Right_2048\\" + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							} else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\" + 
										config.getPrimaryIpAddress() + "\\\\" + local_photo_path + inn.getBowling_team().getTeamName4() + "\\\\" + "Right_2048\\\\" 
										+ boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + boc.getPlayer().getFirstname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
									(boc.getPlayer().getSurname() != null ? boc.getPlayer().getSurname() : "") + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + 
									(boc.getOvers() == 1 && boc.getBalls()==0 ? "OVER" : "OVERS") + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "WICKET" + CricketFunctions.Plural(boc.getWickets()).toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "RUN" + CricketFunctions.Plural(boc.getRuns()).toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "MAIDEN" + CricketFunctions.Plural(boc.getMaidens()).toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead5 " + "ECONOMY" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
									CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + boc.getWickets() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + boc.getRuns() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + boc.getMaidens() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue5 " + 
									(boc.getEconomyRate() != null ? boc.getEconomyRate() : "-") + ";");
							
							this.status = CricketUtil.SUCCESSFUL;
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
				current_layer = 5-current_layer;
			}
		}
	}
	public void populateBatsmanStats(PrintWriter print_writer,boolean is_this_updating,String string,Integer team_id,Integer player_id,List<Player> allPlayer, 
			List<Team> allTeams,MatchAllData match,String session_selected_broadcaster2,Configuration config) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = "STILL";
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getBattingTeamId() == team_id) {
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
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFirstname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
									(bc.getPlayer().getSurname() != null ? bc.getPlayer().getSurname() : "") + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUN" + CricketFunctions.Plural(bc.getRuns()).toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALL" + CricketFunctions.Plural(bc.getBalls()).toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "FOUR" + CricketFunctions.Plural(bc.getFours()).toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + (bc.getSixes() == 1 ? "SIX" : "SIXES") + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead5 " + "STRIKE RATE" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
									(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT) ? bc.getRuns() + "*" : bc.getRuns()) + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + bc.getBalls() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + bc.getFours() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + bc.getSixes() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue5 " + 
									CricketFunctions.generateStrikeRate(bc.getRuns(), bc.getBalls(), 0) + ";");
							
							this.status = CricketUtil.SUCCESSFUL;
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
				current_layer = 5-current_layer;
			}
		}
	}
	public void populatePartnership(PrintWriter print_writer,boolean is_this_updating,List<Player> allPlayer,List<Team> allTeams, MatchAllData match,
			String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			String Left_Image = "", Right_Image = "";
			
			for (Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag1 " + logo_path +
							inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
							inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					for (BattingCard bc : inn.getBattingCard()) {
						if (bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							if (bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								Left_Image = bc.getPlayer().getPhoto();
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName1 " + 
										bc.getPlayer().getTicker_name() + ";");

								if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 "+ photo_path + 
											inn.getBatting_team().getTeamName4().toUpperCase() + "\\" + "Left_2048\\" + Left_Image + CricketUtil.PNG_EXTENSION + ";");
								} else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 "+ "\\\\" + 
											config.getPrimaryIpAddress() + "\\\\" + local_photo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\\"
											+ "Left_2048\\" + Left_Image + CricketUtil.PNG_EXTENSION + ";");
								}
							}

							if (bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								Right_Image = bc.getPlayer().getPhoto();
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName2 " + 
										bc.getPlayer().getTicker_name() + ";");
								
								if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 "+ photo_path + inn.getBatting_team().getTeamName4().toUpperCase()
											+ "\\" + "Right_2048\\" + Right_Image + CricketUtil.PNG_EXTENSION + ";");
								} else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 "+ "\\\\" + config.getPrimaryIpAddress() + "\\\\" + local_photo_path
											+ inn.getBatting_team().getTeamName4().toUpperCase() + "\\"
											+ "Right_2048\\" + Right_Image + CricketUtil.PNG_EXTENSION + ";");
								}
							}
						}
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns1 " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls1 " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns2 " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls2 " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScorePART " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "*" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsPART " + 
							"OFF " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + " BALLS" + ";");
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
	
	public void populateDlsParScore(PrintWriter print_writer, MatchAllData match,List<DuckWorthLewis> dls) throws InterruptedException, IOException 
	{
		int balls = 0, overs = 0;
		for(Inning inn : match.getMatch().getInning()) {
			if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES) && inn.getInningNumber() == 2) {
				overs = inn.getTotalOvers();
				balls = inn.getTotalBalls();
				if(inn.getBatting_team().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName4() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
						inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.getTeamScore(inn, "-", false) + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + 
						CricketFunctions.OverBalls(overs, balls) + ";");
				
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
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue " + "   " 
				+ this_data_str.get(0) + ";");
		
		if(this_data_str.get(1).toUpperCase().contains("-")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + this_data_str.get(1).split("-")[0].toUpperCase() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo02 " + this_data_str.get(1).split("-")[1].toUpperCase() + ";");
			
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + this_data_str.get(1).toUpperCase() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo02 " + "" + ";");
			
		}
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	private void populatePhaseBy(PrintWriter print_writer, Integer inning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		
		String phase1="",phase2="",phase3="",phase4="",phase5="";
		for(Inning inn: match.getMatch().getInning()) {
			if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
				if(inn.getBatting_team().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " +  logo_path +
						inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
						inn.getBatting_team().getTeamName4() + " ;");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
						CricketFunctions.getTeamScore(inn, "-", false) + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + 
						CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + 
						(inn.getTotalOvers()==1 && inn.getTotalBalls()==0 ? "OVER" : "OVERS") + ";");
				
				phase1 = getPhaseWiseScore(1, 10, inn.getInningNumber(), match.getEventFile().getEvents());
				phase2 = getPhaseWiseScore(11, 20, inn.getInningNumber(), match.getEventFile().getEvents());
				phase3 = getPhaseWiseScore(21, 30, inn.getInningNumber(), match.getEventFile().getEvents());
				phase4 = getPhaseWiseScore(31, 40, inn.getInningNumber(), match.getEventFile().getEvents());
				phase5 = getPhaseWiseScore(41, 50, inn.getInningNumber(), match.getEventFile().getEvents());
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase1 OVERS 1 TO 10;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase2 OVERS 11 TO 20;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase3 OVERS 21 TO 30;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase4 OVERS 31 TO 40;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase5 OVERS 41 TO 50;");
				
				if(!phase5.split(",")[0].contains("0-0")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ "5;");
				}else if(!phase4.split(",")[0].contains("0-0")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ "4;");
				}else if(!phase3.split(",")[0].contains("0-0")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ "3;");
				}else if(!phase2.split(",")[0].contains("0-0")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ "2;");
				}else if(!phase1.split(",")[0].contains("0-0")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ "1;");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns1 " + phase1.split(",")[0].split("-")[0] 
						+ " RUN" + CricketFunctions.Plural(Integer.valueOf(phase1.split(",")[0].split("-")[0])).toUpperCase()+";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls1 " + phase1.split(",")[0].split("-")[1] 
						+ " WICKET" + CricketFunctions.Plural(Integer.valueOf(phase1.split(",")[0].split("-")[1])).toUpperCase()+" ;");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns2 " + phase2.split(",")[0].split("-")[0] 
						+ " RUN" + CricketFunctions.Plural(Integer.valueOf(phase2.split(",")[0].split("-")[0])).toUpperCase()+";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls2 " + phase2.split(",")[0].split("-")[1] 
						+ " WICKET" + CricketFunctions.Plural(Integer.valueOf(phase2.split(",")[0].split("-")[1])).toUpperCase()+" ;");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns3 " + phase3.split(",")[0].split("-")[0] 
						+ " RUN" + CricketFunctions.Plural(Integer.valueOf(phase3.split(",")[0].split("-")[0])).toUpperCase()+";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls3 " + phase3.split(",")[0].split("-")[1] 
						+ " WICKET" + CricketFunctions.Plural(Integer.valueOf(phase3.split(",")[0].split("-")[1])).toUpperCase()+" ;");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns4 " + phase4.split(",")[0].split("-")[0] 
						+ " RUN" + CricketFunctions.Plural(Integer.valueOf(phase2.split(",")[0].split("-")[0])).toUpperCase()+";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls4 " + phase4.split(",")[0].split("-")[1] 
						+ " WICKET" + CricketFunctions.Plural(Integer.valueOf(phase2.split(",")[0].split("-")[1])).toUpperCase()+" ;");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns5 " + phase5.split(",")[0].split("-")[0] 
						+ " RUN" + CricketFunctions.Plural(Integer.valueOf(phase3.split(",")[0].split("-")[0])).toUpperCase()+";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls5 " + phase5.split(",")[0].split("-")[1] 
						+ " WICKET" + CricketFunctions.Plural(Integer.valueOf(phase3.split(",")[0].split("-")[1])).toUpperCase()+" ;");
				
				//POWERPLAY PHASEWISE
//				phase1 = CricketFunctions.getFirstPowerPlayScore(match, inn.getInningNumber(), match.getEventFile().getEvents());
//				phase2 = CricketFunctions.getSecPowerPlayScore(match, inn.getInningNumber(), match.getEventFile().getEvents());
//				phase3 = CricketFunctions.getThirdPowerPlayScore(match, inn.getInningNumber(), match.getEventFile().getEvents());
//				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase1 OVERS " + inn.getFirstPowerplayStartOver() + 
//						" TO " + inn.getFirstPowerplayEndOver() + ";");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase2 OVERS " + inn.getSecondPowerplayStartOver() + 
//						" TO " + inn.getSecondPowerplayEndOver() + ";");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase3 OVERS " + inn.getThirdPowerplayStartOver() + 
//						" TO " + inn.getThirdPowerplayEndOver() + ";");
//				
//				if(inn.getTotalOvers() >= (inn.getFirstPowerplayStartOver()-1) && inn.getTotalOvers() < inn.getFirstPowerplayEndOver()) {
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ "1;");
//				}else if(inn.getTotalOvers() >= (inn.getSecondPowerplayStartOver()-1) && inn.getTotalOvers() < inn.getSecondPowerplayEndOver()) {
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ "2;");
//				}else if(inn.getTotalOvers() >= (inn.getThirdPowerplayStartOver()-1) && inn.getTotalOvers() < inn.getThirdPowerplayEndOver()) {
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ "3;");
//				}
//				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns1 " + phase1.split(",")[0].split("-")[0] 
//						+ " RUN" + CricketFunctions.Plural(Integer.valueOf(phase1.split(",")[0].split("-")[0])).toUpperCase()+";");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls1 " + phase1.split(",")[0].split("-")[1] 
//						+ " WICKET" + CricketFunctions.Plural(Integer.valueOf(phase1.split(",")[0].split("-")[1])).toUpperCase()+" ;");
//				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns2 " + phase2.split(",")[0].split("-")[0] 
//						+ " RUN" + CricketFunctions.Plural(Integer.valueOf(phase2.split(",")[0].split("-")[0])).toUpperCase()+";");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls2 " + phase2.split(",")[0].split("-")[1] 
//						+ " WICKET" + CricketFunctions.Plural(Integer.valueOf(phase2.split(",")[0].split("-")[1])).toUpperCase()+" ;");
//				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns3 " + phase3.split(",")[0].split("-")[0] 
//						+ " RUN" + CricketFunctions.Plural(Integer.valueOf(phase3.split(",")[0].split("-")[0])).toUpperCase()+";");
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls3 " + phase3.split(",")[0].split("-")[1] 
//						+ " WICKET" + CricketFunctions.Plural(Integer.valueOf(phase3.split(",")[0].split("-")[1])).toUpperCase()+" ;");
			}
		}
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 250.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
	}
	
	public void populateSixDistance(PrintWriter print_writer,boolean is_this_updating,String scene, String data, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + data + ";");
			
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
	
	public void populateMileStone(PrintWriter print_writer,boolean is_this_updating,int team_id,String data1,String data2,String data3,int player_id , MatchAllData match,List<Player> allPlayer,
			List<Team> allTeams, String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			
			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
						allTeams.get(allPlayer.get(player_id-1).getTeamId() - 1).getTeamName4() + "\\Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				
			} else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ "\\\\" + config.getPrimaryIpAddress() + "\\\\" + local_photo_path
						+ allTeams.get(allPlayer.get(player_id-1).getTeamId() - 1).getTeamName4() + "\\"
						+ "Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			}
			
			
			if(allPlayer.get(player_id-1).getFirstname() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getFirstname() + ";");
				
				if(allPlayer.get(player_id-1).getSurname() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + allPlayer.get(player_id-1).getSurname() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
				}
			}else {
				if(allPlayer.get(player_id-1).getSurname() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getSurname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getTicker_name() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
				}
			}
			
			if(allTeams.get(allPlayer.get(player_id-1).getTeamId() - 1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					allTeams.get(allPlayer.get(player_id-1).getTeamId() - 1).getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			if(data1 != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue " + data1 + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue " + " " + ";");
			}
			
			if(data2 != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLine1 " + data2 + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLine1 " + " " + ";");
			}

			if(data3 != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLine2 " + data3 + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLine2 " + " " + ";");
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
			
			
			break;
		}
	}
	
	public void populateLineup(PrintWriter print_writer,boolean is_this_updating,int teamID,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			int row_id = 1,row = 0;
			
			if(teamID == match.getSetup().getHomeTeamId()){
				if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}
				
				if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				for(Player hs : match.getSetup().getHomeSquad()) {
					row_id = row_id + 1;
					row = row + 1;
					
					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
									hs.getFull_name() + " (C)" + ";");
					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								hs.getFull_name() + " (C & WK)" + ";");
					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								hs.getFull_name() + " (WK)" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								hs.getFull_name() + ";");
					}
					
					if(hs.getRole().equalsIgnoreCase("BATSMAN")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole" + row + " " + "BATTER" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole" + row + " " + hs.getRole().toUpperCase() + ";");
					}
	
				}
			}else {
				if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}
				
				
				if(match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				for(Player as : match.getSetup().getAwaySquad()) {
					row_id = row_id + 1;
					row = row + 1;
					
					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								as.getFull_name() + " (C)" + ";");
					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								as.getFull_name() + " (C & WK)" + ";");
					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								as.getFull_name() + " (WK)" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								as.getFull_name() + ";");
					}
					
					if(as.getRole().equalsIgnoreCase("BATSMAN")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole" + row + " " + "BATTER" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole" + row + " " + as.getRole().toUpperCase() + ";");
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
			
			
			break;
		}
	}
	
	public void populateLineupImage(PrintWriter print_writer,boolean is_this_updating,int teamID,MatchAllData match, String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			int row = 0;
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomText  PLAYING XI;");

			if(teamID == match.getSetup().getHomeTeamId()){
				
				if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}
				
				
				if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				for(Player hs : match.getSetup().getHomeSquad()) {
					row = row + 1;
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
							"0" + ";");
					
					if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + "Right_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						
					} else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + local_photo_path
								+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\"
								+ "Right_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
					
					
					//tSecondName1
					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
									hs.getFirstname() + ";");
						if(hs.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									hs.getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									"" + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
								"1" + ";");
					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								hs.getFirstname() + ";");
						if(hs.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									hs.getSurname() + " (WK)" + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									  "(WK)" + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
								"1" + ";");
					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								hs.getFirstname() + ";");
						if(hs.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									hs.getSurname() + " (WK)" + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									 "(WK)" + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
								"0" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								hs.getFirstname() + ";");
						if(hs.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									hs.getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									"" + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
								"0" + ";");
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
							"0" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
							"0" + ";");
					
					if(hs.getRole().equalsIgnoreCase("BATSMAN") || hs.getRole().equalsIgnoreCase("BAT/KEEPER")) {
						if(hs.getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
									icon_path + "Batsman" + CricketUtil.PNG_EXTENSION + ";");
						}else if(hs.getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
									icon_path + "Batsman_Lefthand" + CricketUtil.PNG_EXTENSION + ";");
						}
					}else if(hs.getRole().equalsIgnoreCase("BOWLER")) {
						if(hs.getBowlingStyle() == null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
									icon_path + "FastBowler" + CricketUtil.PNG_EXTENSION + ";");
						}else {
							switch(hs.getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
										icon_path + "FastBowler" + CricketUtil.PNG_EXTENSION + ";");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
										icon_path + "SpinBowlerIcon" + CricketUtil.PNG_EXTENSION + ";");
								break;
							}
						}
					}else if(hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(hs.getBowlingStyle() == null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
									icon_path + "FastBowlerAllrounder" + CricketUtil.PNG_EXTENSION + ";");
						}else {
							switch(hs.getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
										icon_path + "FastBowlerAllrounder" + CricketUtil.PNG_EXTENSION + ";");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
										icon_path + "SpinBowlerAllrounder" + CricketUtil.PNG_EXTENSION + ";");
								break;
							}
						}
					}
				}
			}else {
				if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}
				
				
				if(match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				for(Player as : match.getSetup().getAwaySquad()) {
					row = row + 1;
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
							"0" + ";");
					
					if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						
					} else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + "\\\\" + config.getPrimaryIpAddress() + "\\\\" + local_photo_path
								+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\"
								+ "Right_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
					
					
					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
									as.getFirstname() + ";");
						if(as.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									as.getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									"" + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
								"1" + ";");
					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								as.getFirstname()  + ";");
						if(as.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									as.getSurname()+ " (WK)"  + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									"(WK)"  + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
								"1" + ";");
					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								as.getFirstname()  + ";");
						if(as.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									as.getSurname() + " (WK)" + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									"(WK)" + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
								"0" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
								as.getFirstname() + ";");
						if(as.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									as.getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
									"" + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
								"0" + ";");
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
							"0" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
							"0" + ";");
					
					if(as.getRole().equalsIgnoreCase("BATSMAN") || as.getRole().equalsIgnoreCase("BAT/KEEPER")) {
						if(as.getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
									icon_path + "Batsman" + CricketUtil.PNG_EXTENSION + ";");
						}else if(as.getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
									icon_path + "Batsman_Lefthand" + CricketUtil.PNG_EXTENSION + ";");
						}
					}else if(as.getRole().equalsIgnoreCase("BOWLER")) {
						if(as.getBowlingStyle() == null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
									icon_path + "FastBowler" + CricketUtil.PNG_EXTENSION + ";");
						}else {
							switch(as.getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
										icon_path + "FastBowler" + CricketUtil.PNG_EXTENSION + ";");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
										icon_path + "SpinBowlerIcon" + CricketUtil.PNG_EXTENSION + ";");
								break;
							}
						}
					}else if(as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(as.getBowlingStyle() == null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
									icon_path + "FastBowlerAllrounder" + CricketUtil.PNG_EXTENSION + ";");
						}else {
							switch(as.getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
										icon_path + "FastBowlerAllrounder" + CricketUtil.PNG_EXTENSION + ";");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + 
										icon_path + "SpinBowlerAllrounder" + CricketUtil.PNG_EXTENSION + ";");
								break;
							}
						}
					}
					
//					if(as.getRole().equalsIgnoreCase("Batsman")) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + "BATTER" + ";");
//					}else {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole" + row + " " + as.getRole().toUpperCase() + ";");
//					}

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
			
			
			break;
		}
	}
	
	public void populatePointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> groupA, String session_selected_broadcaster,
			MatchAllData match,String grp,List<Team> tm) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			int row_no=0;
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + grp + " - POINTS TABLE" + ";");
			
			for(int i = 0; i <= groupA.size() - 1 ; i++) {
				row_no = row_no + 1;
				
				if(match.getSetup().getHomeTeam().getTeamName1().equalsIgnoreCase(groupA.get(i).getTeamName().toUpperCase())) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighDeHigh0" + row_no + " 1;");
				}else if(match.getSetup().getAwayTeam().getTeamName1().equalsIgnoreCase(groupA.get(i).getTeamName().toUpperCase())) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighDeHigh0" + row_no + " 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighDeHigh0" + row_no + " 0;");

				}
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighDeHigh0 " + grp + " - POINTS TABLE" + ";");

				if(groupA.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vQualify0" + row_no + " 0" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vQualify0" + row_no + " " + "1" + ";");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + 
						groupA.get(i).getTeamName().toUpperCase() + ";");
				for(Team teams : tm) {
					if(teams.getTeamName1().equalsIgnoreCase(groupA.get(i).getTeamName())) {
						
						if(teams.getTeamName1().equalsIgnoreCase("NEPAL")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0" + row_no + " 1" + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0" + row_no + " 0" + ";");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag0" + row_no + " " + logo_path + 
								teams.getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}else if(teams.getTeamName4().equalsIgnoreCase(groupA.get(i).getTeamName())) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0" + row_no + " 0" + ";");

						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag0" + row_no + " " + logo_path + 
								teams.getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
				}
				
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tMTS0" + (row_no) + " " + groupA.get(i).getPlayed() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tW0" + (row_no) + " " + groupA.get(i).getWon() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tL0" + (row_no) + " " + groupA.get(i).getLost() + ";");
				DecimalFormat df = new DecimalFormat("0.00");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNRR0" + (row_no) + " " + df.format(groupA.get(i).getNetRunRate()) + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPT0" + (row_no) + " " + groupA.get(i).getPoints() + ";");

			}
			
//			for(int i = 0; i <= groupB.size() - 1 ; i++) {
//				row_no = row_no + 1;
//				if(groupB.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
//					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsTeamName0" + row_no + " " + 
//							groupB.get(i).getTeamName().toUpperCase() + ";");
//				}else {
//					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsTeamName0" + row_no + " " + "(Q) " + 
//							groupB.get(i).getTeamName().toUpperCase() + ";");
//				}
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "A" + " " + groupB.get(i).getPlayed() + ";");
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "B" + " " + groupB.get(i).getWon() + ";");
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "C" + " " + groupB.get(i).getLost() + ";");
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "D" + " " + groupB.get(i).getNoResult() + ";");
//				DecimalFormat df = new DecimalFormat("0.000");
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "E" + " " + df.format(groupB.get(i).getNetRunRate()) + ";");
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "F" + " " + groupB.get(i).getPoints() + ";");
//
//			}
			
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
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	
	private void populateGroupPointsTable(PrintWriter print_writer, String string, List<LeagueTeam> groupA,
			String session_selected_broadcaster, MatchAllData match, String grp,
			List<Team> tm) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIGSCREEN_DOAD_SCORING":
			int row_no=0;
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader SUPER EIGHT - " + grp + ";");
			
			for(int i = 0; i <= groupA.size() - 1 ; i++) {
				row_no = row_no + 1;
				
				if(match.getSetup().getHomeTeam().getTeamName1().equalsIgnoreCase(groupA.get(i).getTeamName().toUpperCase())) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighDeHigh0" + row_no + " 1;");
				}else if(match.getSetup().getAwayTeam().getTeamName1().equalsIgnoreCase(groupA.get(i).getTeamName().toUpperCase())) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighDeHigh0" + row_no + " 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighDeHigh0" + row_no + " 0;");

				}

				if(groupA.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vQualify0" + row_no + " 0;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vQualify0" + row_no + " " + "1;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + 
						groupA.get(i).getTeamName().toUpperCase() + ";");
				for(Team teams : tm) {
					if(teams.getTeamName1().equalsIgnoreCase(groupA.get(i).getTeamName())) {
						
						if(teams.getTeamName1().equalsIgnoreCase("NEPAL")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0" + row_no + " 1" + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0" + row_no + " 0" + ";");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag0" + row_no + " " + logo_path + 
								teams.getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						
					}else if(teams.getTeamName4().equalsIgnoreCase(groupA.get(i).getTeamName())) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0" + row_no + " 0" + ";");

						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag0" + row_no + " " + logo_path + 
								teams.getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						
					}
				}
				
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tMTS0" + (row_no) + " " + groupA.get(i).getPlayed() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tW0" + (row_no) + " " + groupA.get(i).getWon() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tL0" + (row_no) + " " + groupA.get(i).getLost() + ";");
				DecimalFormat df = new DecimalFormat("0.00");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNRR0" + (row_no) + " " + df.format(groupA.get(i).getNetRunRate()) + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPT0" + (row_no) + " " + groupA.get(i).getPoints() + ";");

			}
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
			this.status = CricketUtil.SUCCESSFUL;
			break;
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