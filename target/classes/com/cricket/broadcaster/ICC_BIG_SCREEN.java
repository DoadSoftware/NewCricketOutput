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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import com.Ae_Third_Party_Xml.AE_Inning;
import com.Ae_Third_Party_Xml.AE_Last_Ball;
import com.Ae_Third_Party_Xml.AE_Over;
import com.Ae_Third_Party_Xml.AE_Player;
import com.Ae_Third_Party_Xml.AE_Player_Info;
import com.Ae_Third_Party_Xml.AE_Team;
import com.Ae_Third_Party_Xml.AE_WagonData;
import com.Ae_Third_Party_Xml.AE_Batsman;
import com.Ae_Third_Party_Xml.AE_Bowler;
import com.Ae_Third_Party_Xml.AE_Combination;
import com.Ae_Third_Party_Xml.AE_Cricket;
import com.Ae_Third_Party_Xml.AE_FallOfWicket;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.DuckWorthLewis;
import com.cricket.model.FantasyImages;
import com.cricket.model.LeagueTable;
import com.cricket.model.LeagueTeam;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.Player;
import com.cricket.model.Sponsor;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ICC_BIG_SCREEN extends Scene{

	private String status;
	private String data;
	public Infobar infobar = new Infobar();
	public String session_selected_broadcaster = "ICC_BIG_SCREEN";
	public String which_graphics_onscreen = "",which_graphics_onscreen1 = "";
	private String logo_path = "C:\\Images\\ICC\\T20_WORLD_CUP_2024\\Logos\\";
	private String photo_path  = "C:\\Images\\ICC\\T20_WORLD_CUP_2024\\Player_Images\\";
	private String sponsor_path  = "C:\\Images\\ICC\\T20_WORLD_CUP_2024\\Sponsor\\";
	private String fantasy_path  = "C:\\Images\\ICC\\T20_WORLD_CUP_2024\\Fantasy\\";
	public String text_path = "IMAGE*/Default/Essentials/Text";
	public String icon_path = "C:\\Images\\ICC\\T20_WORLD_CUP_2024\\Icons\\";
	public String team = "";
	public List<String> this_data_str = new ArrayList<String>();
	public List<DuckWorthLewis> dls;
	public int current_layer = 3,count = 1,loop_value = 0,video_count = 0,video_layer = 1,inning_no = 0;
	public boolean is_video_onScreen = false;
	public ICC_BIG_SCREEN() {
		super();
	}

	public ICC_BIG_SCREEN(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	} 

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Infobar updateInfobar(Scene scene, AE_Cricket match,MatchAllData current_match,boolean show_speed,CricketService cricketService ,PrintWriter print_writer) throws Exception
	{
		if(infobar.isInfobar_on_screen() == true) {
			infobar = populateInfo(infobar, print_writer,true,match, session_selected_broadcaster);
			infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, session_selected_broadcaster);
		}//getScorebug_last_value
		
		if(infobar.isInfobar_on_screen() == true) {
			infobar = populateScorebug(print_writer, true, match, session_selected_broadcaster);
			infobar = populateScorebugChangeOn(print_writer, infobar.getScorebug_last_value(), match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("EQUATION_ICC")) {
			populateEquation(print_writer,true,match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("EQUATIONSHORT_ICC")) {
			populateEquationShort(print_writer,true,match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("PROJECTED_BS")) {
			populateProjected(print_writer,true,match,current_match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("COMPARISON_ICC")) {
			populateComparison(print_writer,true,match, session_selected_broadcaster);
		}
		
		if(which_graphics_onscreen.equalsIgnoreCase("PARTNERSHIP_ICC")) {
			populatePartnership(print_writer,true,cricketService.getAllPlayer(),cricketService.getTeams(),match, session_selected_broadcaster);
		}

		return infobar;
	}
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match,CricketService cricketService, List<MatchAllData> tournament_matches,AE_Cricket third_party_match,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics, AE_Last_Ball last_ball,List<DuckWorthLewis> dls) throws Exception{
		switch (whatToProcess.toUpperCase()) {
		case "FREETEXT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "IMAGEDROPDOWN-ICC_GRAPHICS-OPTIONS":	
			return new ObjectMapper().writeValueAsString(cricketService.getSponsor()).toString();
		case "FANTASYDROPDOWN-ICC_GRAPHICS-OPTIONS":	
			return new ObjectMapper().writeValueAsString(cricketService.getFantasyImages()).toString();
		case "SCOREBUG_CHANGEON_GRAPHICS-OPTIONS":
			int inn_number = 0;
			inn_number = third_party_match.getMatchDetails().getStatus().getCurrentInnings();
			return Integer.toString(inn_number);
			
		case "POPULATE-DECISION": case "POPULATE-OUT_NOT_DECISION": case "POPULATE-MATCH_IDENT": case "POPULATE-INFO": case "POPULATE-TARGET_BS": 
		case "POPULATE-COMPARISON_BS": case "POPULATE-FREE_BS": case "POPULATE-BOUNDARIES_BS": case "POPULATE-PROJECTED_BS": case "POPULATE-EQUATION_BS":
		case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-PLAYERMILE_BS": case "POPULATE-START_BS": case "POPULATE-COUNTDOWN_BS":
		case "POPULATE-HOWOUT_BS": case"POPULATE-BS_HOWOUT":case "POPULATE-BOWLERFIG_BS": case "POPULATE-QUICKHOWOUT_BS": case "POPULATE-SIX_DISTANCE_ICC":
		case "POPULATE-INFOBAR_ICC": case "POPULATE-SCOREBUG_ICC":	case "POPULATE-PARTNERSHIP_ICC": case "POPULATE-MATCHID_ICC":
		case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":case "POPULATE-BS_BATSCORE": case "POPULATE-FREEHIT_ICC": case "POPULATE-SCOREBUG_CHANGEON_ICC":
		case "POPULATE-SCOREBOARD_ICC":	case "POPULATE-FREETEXT_ICC": case "POPULATE-BOUNDARY_ICC": case "POPULATE-LINE2FREE_ICC": case "POPULATE-EXTRAS_ICC":
		case "POPULATE-ICC_BATSMAN-STATS": case "POPULATE-TARGET_ICC": case "POPULATE-EQUATION_ICC": case "POPULATE-RUNRATE_ICC": case "POPULATE-ICC_TEAM-BOUNDARY":
		case "POPULATE-TOSS_ICC": case "POPULATE-ICC_BOWLER-STATS": case "POPULATE-TEAMNAME_ICC": case "POPULATE-COMPARISON_ICC": case "POPULATE-ICC_MATCHSUMMARY":
		case "POPULATE-FOUR_ICC": case "POPULATE-SIX_ICC": case "POPULATE-MILESTONE_ICC": case "POPULATE-PLAYERFREETEXT_ICC": case "POPULATE-ICC_BALL-SPEED":
		case "POPULATE-TARGET_WITH_IMG_ICC": case "POPULATE-THIS_OVER_ICC": case "POPULATE-LINEUP_ICC": case "POPULATE-LINEUPIMAGE_ICC": case "POPULATE-ICC_BOWLER-FIG":
		case "POPULATE-PLAYERNAME_ICC": case "POPULATE-REVIEW_ICC": case "POPULATE-TARGETFULL_ICC": case "POPULATE-EQUATIONSHORT_ICC": case"POPULATE-ICC_WAGON":
		case "POPULATE-LINEUPLONG_ICC": case "POPULATE-ICC_IMAGE16_9": case "POPULATE-ICC_IMAGE4_3": case "POPULATE-ICC_IMAGELOOP": case "POPULATE-WICKET_ICC":
		case "POPULATE-WIDE_ICC": case "POPULATE-DUCK_ICC": case "POPULATE-IMAGEDROPDOWN": case "POPULATE-ICC_QUICKHOWOUT": case "POPULATE-WEATHER_ICC":
		case "POPULATE-PLAYERVIDEO_ICC": case "POPULATE-LONGLINEUP_ICC": case "POPULATE-ICC_BALL-DISTANCE":	case "POPULATE-FANTASYDROPDOWN":
		case "POPULATE-HUNDRED_ICC":case "POPULATE-FIFTY_ICC":case "POPULATE-CATCH_ICC":case"POPULATE-GROUP_ICC": case "POPULATE-DLS": case "POPULATE-BUKH-POINTSTABLE":
		case "POPULATE-ICC_INTRO-STATS":case "POPULATE-H2H_ICC":case "POPULATE-EQUATION_WITH_IMG_ICC":case"POPULATE-MATCHID_WITH_IMG_ICC":
		case "POPULATE-PHASESCORE_ICC": case "POPULATE-IMG_LINE2FREE_ICC":case "POPULATE-HAT_TRICK":case "POPULATE-HAT_TRICK_BALL":case"POPULATE-GROUP_PTSTBLE_ICC":
			switch (session_selected_broadcaster.toUpperCase()) {
			case "ICC_BIG_SCREEN":
				switch(whatToProcess.toUpperCase()) {
				case "POPULATE-OUT_NOT_DECISION": case "POPULATE-START_BS": case "ANIMATE_IN_SPEED_SECOND_BROADCASTER": case "POPULATE-SCOREBUG_CHANGEON_ICC":
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
					
					for(AE_Inning inn : third_party_match.getInning()) {
						if(third_party_match.getInning().size()==inn.getNumber()) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BowlerGrp$Section3$Section3All$BallSpeed$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + 
									inn.getShortName().toLowerCase() +" \0");
						}
					}
					processAnimation(print_writer, "Section3$BallSpeedIn", "START", "ICC_BIG_SCREEN", current_layer);
					break;
				case "POPULATE-FANTASYDROPDOWN":
					for(FantasyImages ns : cricketService.getFantasyImages()) {
						  if(ns.getFantasyId() == Integer.valueOf(valueToProcess.split(",")[1])) {
							  populateFantasy(print_writer, valueToProcess.split(",")[0], ns, match, session_selected_broadcaster);
						  }
						}
					break;
				case "POPULATE-IMAGEDROPDOWN":
					for(Sponsor ns : cricketService.getSponsor()) {
						  if(ns.getSponsorId() == Integer.valueOf(valueToProcess.split(",")[1])) {
							  populateSponsor(print_writer, valueToProcess.split(",")[0], ns, match, session_selected_broadcaster);
						  }
						}
					break;
				case "POPULATE-ICC_IMAGELOOP":
					data = valueToProcess.split(",")[0];
					populateImageAuto(print_writer);
					break;
				case"POPULATE-GROUP_PTSTBLE_ICC":
					LeagueTable Groups = null;
					if(valueToProcess.split(",")[1].equalsIgnoreCase("GROUP 1")) {
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "Super8Group1.xml").exists()) {
							Groups = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "Super8Group1.xml"));
						}
					}else if(valueToProcess.split(",")[1].equalsIgnoreCase("GROUP 2")) {
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "Super8Group2.xml").exists()) {
							Groups = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "Super8Group2.xml"));
						}
					}
					
					populateGroupPointsTable(print_writer, valueToProcess.split(",")[0],Groups.getLeagueTeams(),session_selected_broadcaster,
							match,third_party_match,valueToProcess.split(",")[1],cricketService.getTeams());
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
					}else if(valueToProcess.split(",")[1].equalsIgnoreCase("C")) {
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupC.xml").exists()) {
							group = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupC.xml"));
							groups = "GROUP C";
						}
					}else if(valueToProcess.split(",")[1].equalsIgnoreCase("D")) {
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupD.xml").exists()) {
							group = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
									new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupD.xml"));
							groups = "GROUP D";
						}
					}
					
					populatePointsTable(print_writer, valueToProcess.split(",")[0],group.getLeagueTeams(),session_selected_broadcaster,
							match,third_party_match,groups,cricketService.getTeams());
					break;	
				case "POPULATE-HUNDRED_ICC":case "POPULATE-FIFTY_ICC":case "POPULATE-CATCH_ICC":
					 populateExtraBoundries(print_writer,whatToProcess.replace("POPULATE-", "").replace("_ICC", ""),third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-FREEHIT_ICC":
					populateFreeHit(print_writer,false,third_party_match,match, session_selected_broadcaster);
					break;	
				case "POPULATE-PROJECTED_BS":
					populateProjected(print_writer,false,third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-ICC_QUICKHOWOUT":
					populateQuickHowOut(print_writer,third_party_match,match,cricketService.getAllPlayer(), session_selected_broadcaster);
					break;	
				case "POPULATE-ICC_IMAGE16_9":
					populateImagesixteenNine(print_writer,valueToProcess.split(",")[1],third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-ICC_IMAGE4_3":
					populateImagefourThree(print_writer,valueToProcess.split(",")[1],third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-LONGLINEUP_ICC":
					populateLongLineup(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-LINEUPLONG_ICC":
					populateLineupLong(print_writer,valueToProcess.split(",")[1],third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-ICC_WAGON":
					populateBatsmanWagon(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,third_party_match);
					break;	
				case "POPULATE-THIS_OVER_ICC":
					populateThisOver(print_writer,third_party_match,session_selected_broadcaster);
					break;
				case "POPULATE-TARGET_WITH_IMG_ICC":
					populateTargetWithImgBs(print_writer,third_party_match,cricketService.getAllPlayer(),cricketService.getTeams(),session_selected_broadcaster);
					break;
				case "POPULATE-EQUATION_WITH_IMG_ICC":
					populateEquationWithImgBs(print_writer,false,third_party_match,cricketService.getAllPlayer(),cricketService.getTeams(),session_selected_broadcaster);
					break;
				case"POPULATE-MATCHID_WITH_IMG_ICC":
					populateMatchIDWithImgBs(print_writer,match,third_party_match,cricketService.getAllPlayer(),cricketService.getTeams(),session_selected_broadcaster);
					break;
				case "POPULATE-ICC_BALL-DISTANCE":
					populateBallDistance(print_writer,last_ball, session_selected_broadcaster);
					break;
				case "POPULATE-ICC_BALL-SPEED":
					populateBallSpeed(print_writer,last_ball, session_selected_broadcaster);
					break;
				case "POPULATE-WEATHER_ICC":
					populateWeather(print_writer,valueToProcess.split(",")[1],valueToProcess.split(",")[2],valueToProcess.split(",")[3],
							third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-PLAYERFREETEXT_ICC":
					populatePlayerfreeText(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],valueToProcess.split(",")[3],
							Integer.valueOf(valueToProcess.split(",")[4]),third_party_match,cricketService.getAllPlayer(),cricketService.getTeams(), session_selected_broadcaster);
					break;
				case "POPULATE-MILESTONE_ICC":
					populateMileStone(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],valueToProcess.split(",")[3],
							valueToProcess.split(",")[4],Integer.valueOf(valueToProcess.split(",")[5]),third_party_match,cricketService.getAllPlayer(),cricketService.getTeams(), session_selected_broadcaster);
					break;
				case "POPULATE-ICC_MATCHSUMMARY":
					inning_no = Integer.valueOf(valueToProcess.split(",")[1]);
					populateMatchSummary(print_writer,false,Integer.valueOf(valueToProcess.split(",")[1]),third_party_match,cricketService.getAllPlayer(), session_selected_broadcaster);
					break;
				case "POPULATE-COMPARISON_ICC":
					populateComparison(print_writer,false,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-LINEUPIMAGE_ICC":
					populateLineupImage(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-LINEUP_ICC":
					populateLineup(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-PLAYERNAME_ICC":
					populatePlayerName(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-PLAYERVIDEO_ICC":
					data = valueToProcess.split(",")[0];
					populatePlayerVideo(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-TEAMNAME_ICC":
					populateTeamName(print_writer,valueToProcess.split(",")[1],third_party_match, session_selected_broadcaster);
					break;
				case"POPULATE-GROUP_ICC":
					populateGroup(print_writer,valueToProcess.split(",")[1],third_party_match, session_selected_broadcaster,
							cricketService.getTeams().stream().filter(tm->tm.getTeamGroup().equalsIgnoreCase(valueToProcess.split(",")[1])).collect(Collectors.toList()));
					break;
				case "POPULATE-TOSS_ICC":
					populateToss(print_writer,third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-PHASESCORE_ICC":
					populatePhaseBy(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-ICC_TEAM-BOUNDARY":
					populateTeamBoundary(print_writer,Integer.valueOf(valueToProcess.split(",")[1]),third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-RUNRATE_ICC":
					populateRunRate(print_writer,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-EQUATIONSHORT_ICC":
					populateEquationShort(print_writer,false,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-EQUATION_ICC":
					populateEquation(print_writer,false,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-H2H_ICC":
					populateH2H(print_writer,valueToProcess.split(",")[1],Integer.valueOf(valueToProcess.split(",")[2]),valueToProcess.split(",")[3] ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,third_party_match);					
					break;
				case "POPULATE-HAT_TRICK":case "POPULATE-HAT_TRICK_BALL":
					populateHatTrick(print_writer,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGET_ICC":
					populateTarget(print_writer,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGETFULL_ICC":
					populateTargetFull(print_writer,third_party_match, session_selected_broadcaster);
					break;	
				case "POPULATE-EXTRAS_ICC":
					populateExtras(print_writer,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-REVIEW_ICC":
					populateReview(print_writer,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-LINE2FREE_ICC":
					populateline2FreeText(print_writer,valueToProcess.split(",")[0],valueToProcess.split(",")[1],valueToProcess.split(",")[2] ,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-IMG_LINE2FREE_ICC":
					populateImgline2FreeText(print_writer,valueToProcess.split(",")[0],valueToProcess.split(",")[1],valueToProcess.split(",")[2],
							valueToProcess.split(",")[3] ,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-FREETEXT_ICC":
					for(NameSuper ns : cricketService.getNameSupers()) {
						  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
								populateFreeText(print_writer,valueToProcess.split(",")[0],ns ,third_party_match, session_selected_broadcaster);
						  }
					}
					break;
				case "POPULATE-FOUR_ICC":
					populateFour(print_writer,valueToProcess.split(",")[0],third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-WIDE_ICC":
					populateWide(print_writer,valueToProcess.split(",")[0],third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-DUCK_ICC":
					populateDuck(print_writer,valueToProcess.split(",")[0],third_party_match, session_selected_broadcaster);
					break;	
				case "POPULATE-WICKET_ICC":
					populateWicket(print_writer,valueToProcess.split(",")[0],third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-SIX_ICC":
					populateSix(print_writer,valueToProcess.split(",")[0],third_party_match, session_selected_broadcaster);
					break;	
				case "POPULATE-BOUNDARY_ICC":
					populateBoundary(print_writer,valueToProcess.split(",")[0],valueToProcess.split(",")[1] ,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-SCOREBOARD_ICC":
					populateScoreBoard(infobar, print_writer, valueToProcess,cricketService.getAllPlayer(),third_party_match,session_selected_broadcaster);
					break;	
				case "POPULATE-SCOREBUG_CHANGEON_ICC":
					populateScorebugChangeOn(print_writer, valueToProcess,third_party_match, session_selected_broadcaster);
					break;
//				case "POPULATE-FREEHIT_ICC":
//					//populateFreeHit(print_writer,valueToProcess.split(",")[0],valueToProcess.split(",")[1] ,third_party_match, session_selected_broadcaster);
//					break;
				case "POPULATE-DLS":
					populateDlsParScore(print_writer,third_party_match,dls);
					break;
				case "POPULATE-MATCHID_ICC":
					populateMatchID(print_writer,cricketService.getAllPlayer(),cricketService.getTeams(),third_party_match,match, session_selected_broadcaster);
					break;
				case "POPULATE-SIX_DISTANCE_ICC":
					populateSixDistance(print_writer,valueToProcess.split(",")[0],valueToProcess.split(",")[1] ,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-PARTNERSHIP_ICC":
					populatePartnership(print_writer,false,cricketService.getAllPlayer(),cricketService.getTeams(),third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-SCOREBUG_ICC":
					populateScorebug(print_writer,false,third_party_match, session_selected_broadcaster);
					infobar.setScorebug_last_value("");
					break;
				case "POPULATE-INFOBAR_ICC":
					populateInfobar(infobar, print_writer, valueToProcess,third_party_match, session_selected_broadcaster);
					infobar = populateVizInfobarMiddle(infobar, false, print_writer, third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-BOWLERFIG_BS":
					populateBugBowler(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-HOWOUT_BS":
					populateHowout(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
							Integer.valueOf(valueToProcess.split(",")[3]), third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-START_BS":
					populateCountdown(print_writer,valueToProcess.split(",")[0],third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-COUNTDOWN_BS":
					populateCountdown(print_writer,valueToProcess,third_party_match, session_selected_broadcaster);
					break;
				/*
				* case "POPULATE-PLAYERMILE_BS":
				* populatePlayerMileStoneBs(print_writer,Integer.valueOf(valueToProcess.split(
				* ",")[1]),valueToProcess.split(",")[2],
				* valueToProcess.split(",")[3],third_party_match,
				* session_selected_broadcaster); break;
				*/
				case "POPULATE-L3-PLAYERPROFILEBAT":
					populatePlayerProfileBat(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							valueToProcess.split(",")[3],null,cricketService.getAllPlayer(),third_party_match,match, session_selected_broadcaster);
					break;
					
				case "POPULATE-L3-PLAYERPROFILE":
					populatePlayerProfileBs(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							valueToProcess.split(",")[3],null,cricketService.getAllPlayer(),third_party_match,match, session_selected_broadcaster);
					break;	
					
				case "POPULATE-BS_HOWOUT":
					populateFallOfWickets(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,third_party_match);
				
				
					break;
				
				case "POPULATE-BS_BATSCORE":
					populateBatterScore(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,third_party_match);
					break;
				case "POPULATE-ICC_BOWLER-FIG":
					populateBowlerFig(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							valueToProcess.split(",")[2] ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,third_party_match);
					break;
				case "POPULATE-ICC_BOWLER-STATS":
					populateBowlerStats(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							valueToProcess.split(",")[2] ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,third_party_match);
					break;
				case "POPULATE-ICC_BATSMAN-STATS":
					populateBatsmanStats(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]) ,cricketService.getAllPlayer(),cricketService.getTeams(),match
							, session_selected_broadcaster,third_party_match);
					break;
				case "POPULATE-ICC_INTRO-STATS":
					populatePlayerIntroStats(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1])
							,cricketService.getAllPlayer(),cricketService.getTeams(),match, session_selected_broadcaster,third_party_match);
					break;
				case "POPULATE-TARGET_BS":
					populateTargetBs(print_writer, third_party_match, session_selected_broadcaster);
					break;
//				case "POPULATE-INFO":
//					populateInfo(print_writer,false,third_party_match, session_selected_broadcaster);
//					break;
				case "POPULATE-COMPARISON_BS":
					populateComparisonBs(print_writer,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCH_IDENT":
					populateIdentMatch(print_writer,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-FREE_BS":
					populateFreeBs(print_writer,valueToProcess.split(",")[1],third_party_match, session_selected_broadcaster);
					break;	
				case "POPULATE-DECISION":
					populateDecision(print_writer,third_party_match, session_selected_broadcaster);
					break;
				case "POPULATE-OUT_NOT_DECISION":
					populateOutNotDecision(print_writer,valueToProcess.split(",")[0],third_party_match, session_selected_broadcaster);
					break;
			
				
				}
				//return JSONObject.fromObject(this_doad).toString();
				break;
			}
			break;
		
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
		case "ANIMATE-HAT_TRICK_BALL":case"ANIMATE-HAT_TRICK":

			switch (session_selected_broadcaster.toUpperCase()) {
			case "ICC_BIG_SCREEN":
				
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
			case "ANIMATE-HAT_TRICK_BALL":case"ANIMATE-HAT_TRICK":
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
	private void populateGroupPointsTable(PrintWriter print_writer, String string, List<LeagueTeam> groupA,
			String session_selected_broadcaster, MatchAllData match, AE_Cricket Ae_match, String grp,
			List<Team> tm) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			int row_no=0;
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader SUPER EIGHT - " + grp + ";");
			
			for(int i = 0; i <= groupA.size() - 1 ; i++) {
				row_no = row_no + 1;
				
				if(Ae_match.getMatchDetails().getHomeTeam().getLongName().equalsIgnoreCase(groupA.get(i).getTeamName().toUpperCase())) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighDeHigh0" + row_no + " 1;");
				}else if(Ae_match.getMatchDetails().getAwayTeam().getLongName().equalsIgnoreCase(groupA.get(i).getTeamName().toUpperCase())) {
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
				DecimalFormat df = new DecimalFormat("0.000");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNRR0" + (row_no) + " " + df.format(groupA.get(i).getNetRunRate()) + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPT0" + (row_no) + " " + groupA.get(i).getPoints() + ";");

			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
		
	}

	private void populateHatTrick(PrintWriter print_writer, AE_Cricket third_party_match,
			String session_selected_broadcaster2) {
		
		
	}

	private void populatePhaseBy(PrintWriter print_writer, Integer inning, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException {
			
		Integer phase1Run=0,phase2Run=0,phase3Run=0,phase1Wicket=0,phase2Wicket=0,phase3Wicket=0;
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase1  OVERS 1 TO 6 ;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase2  OVERS 7 TO 15 ;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPhase3  OVERS 16 TO 20 ;");
		for(AE_Inning inn: match.getInning()) {
				
				if(inn.getNumber()==inning) {
					if(inn.getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag "+ " 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag "+ " 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag "+  logo_path +
							inn.getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName "+ 
							inn.getShortName().toUpperCase() + " ;");
					
					if(inn.getNoOfWickets()==10) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS "+inn.getRuns()+ ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS "+ inn.getRuns()+"-"+inn.getNoOfWickets()+ ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOverIS "+inn.getOvers()+ ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOverIS "+inn.getOvers()+ ";");

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader  PHASE SCORES ;");
					
					if(Double.valueOf(inn.getOvers())>=0 && Double.valueOf(inn.getOvers())<6 ) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ " 1;");						
					}else if(Double.valueOf(inn.getOvers())>=6 && Double.valueOf(inn.getOvers())< 16 ) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ " 2;");
					}else if(Double.valueOf(inn.getOvers())>16) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows "+ " 3;");
					}
					for(AE_Over over: inn.getOverData()) {
						if(over.getNo()<=6) {
							phase1Run = phase1Run + over.getRuns();
							if(over.getWicket()!=null && over.getWicket().size()>0) {
								phase1Wicket = phase1Wicket + over.getWicket().size();
							}
						}else if(over.getNo()>6 && over.getNo()<=15) {
							phase2Run = phase2Run + over.getRuns();
							if(over.getWicket()!=null && over.getWicket().size()>0) {
								phase2Wicket = phase2Wicket + over.getWicket().size();
							}
						}else if(over.getNo()>=16) {
							phase3Run = phase3Run + over.getRuns();
							if(over.getWicket()!=null && over.getWicket().size()>0) {
								phase3Wicket = phase3Wicket +over.getWicket().size();
							}
						}
							
						
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns1 "+ phase1Run +" RUN"+ CricketFunctions.Plural(phase1Run).toUpperCase()+";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls1 "+ phase1Wicket +" WICKET"+ CricketFunctions.Plural(phase1Wicket).toUpperCase()+" ;");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns2 "+ phase2Run + " RUN"+CricketFunctions.Plural(phase2Run).toUpperCase()+";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls2 "+ phase2Wicket +" WICKET"+ CricketFunctions.Plural(phase2Wicket).toUpperCase()+";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns3 "+ phase3Run +" RUN"+ CricketFunctions.Plural(phase3Run).toUpperCase()+" ;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls3 "+ phase3Wicket +" WICKET"+ CricketFunctions.Plural(phase3Wicket).toUpperCase()+";");
	
				}
			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
	}

	private void populateMatchIDWithImgBs(PrintWriter print_writer, MatchAllData mtch,  AE_Cricket match,
			List<Player> allPlayers, List<Team> teams, String session_selected_broadcaster) throws InterruptedException {
		
		if(mtch.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + 
					mtch.getSetup().getHomeTeam().getTeamName4()+ ";");
		}else if(mtch.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + 
					mtch.getSetup().getHomeTeam().getTeamName4()+ ";");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + 
					mtch.getSetup().getHomeTeam().getTeamName1()+ ";");
		}
		
		
		if(mtch.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
					mtch.getSetup().getAwayTeam().getTeamName4()+ ";");
		}else if(mtch.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
					mtch.getSetup().getAwayTeam().getTeamName4()+ ";");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
					mtch.getSetup().getAwayTeam().getTeamName1()+ ";");
		}
		
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
				match.getMatchDetails().getStatus().getHeading().toUpperCase()	+ ";");
		
		if(match.getMatchDetails().getHomeTeam().getShortName().equalsIgnoreCase("NEP")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					match.getMatchDetails().getHomeTeam().getShortName() + CricketUtil.PNG_EXTENSION + ";");
		}
		
		
		if(match.getMatchDetails().getAwayTeam().getShortName().equalsIgnoreCase("NEP")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 1;");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
					match.getMatchDetails().getAwayTeam().getShortName() + CricketUtil.PNG_EXTENSION + ";");
		}
		
		
				
		for(AE_Player_Info ae_Player1 : match.getTeam().get(0).getPlayer()) {
			if(ae_Player1.getCaptain() != null && ae_Player1.getCaptain().equalsIgnoreCase("yes")) {
				Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player1.getID().intValue()).findAny().orElse(null);
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
						teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Left_2048\\" + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				TimeUnit.MILLISECONDS.sleep(200);
				
			}
		}
		for(AE_Player_Info ae_Player2 : match.getTeam().get(1).getPlayer()) {
			if(ae_Player2.getCaptain() != null && ae_Player2.getCaptain().equalsIgnoreCase("yes")) {
				Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player2.getID().intValue()).findAny().orElse(null);
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 "+ photo_path + 
						teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Right_2048\\"  + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				TimeUnit.MILLISECONDS.sleep(200);
				
			}
		}
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		
	}

	private void populateGroup(PrintWriter print_writer, String group, AE_Cricket third_party_match,
			String session_selected_broadcaster, List<Team> team) throws InterruptedException {
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " +
				group.toUpperCase()+ ";");
		
			if(!team.isEmpty()) {
				for(int i=1;i<=team.size();i++) {
					if(team.get(i-1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0"+ i +" 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag0"+ i +" 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag0"+ i +" "+ logo_path +
							team.get(i-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
					if(team.get(i-1).getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + i + " " + 
								team.get(i-1).getTeamName4().toUpperCase()+ ";");
					}else if(team.get(i-1).getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + i + " " + 
								team.get(i-1).getTeamName4().toUpperCase()+ ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + i + " " + 
								team.get(i-1).getTeamName1().toUpperCase()+ ";");
					}
				
				}
			
			}	
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}

	private void populateH2H(PrintWriter print_writer, String team, Integer batter_id,String Bowler,
			List<Player> allPlayers,List<Team> teams, MatchAllData match, String session_selected_broadcaster2,
			AE_Cricket third_party_match) throws InterruptedException {
		
		AE_Player_Info Batsman,bowler;
		
		if(third_party_match.getTeam().get(0).getShortName().contentEquals(team)) {
			 Batsman =third_party_match.getTeam().get(0).getPlayer().stream().filter(bat->bat.getID().intValue()==batter_id).findAny().orElse(null);
			 bowler =third_party_match.getTeam().get(1).getPlayer().stream().filter(bat->bat.getFirstName().equalsIgnoreCase(Bowler)||bat.getSurname().equalsIgnoreCase(Bowler)).findAny().orElse(null);
		}else {
			 Batsman =third_party_match.getTeam().get(1).getPlayer().stream().filter(bat->bat.getID().intValue()==batter_id).findAny().orElse(null);
			 bowler =third_party_match.getTeam().get(0).getPlayer().stream().filter(bat->bat.getFirstName().equalsIgnoreCase(Bowler)||bat.getSurname().equalsIgnoreCase(Bowler)).findAny().orElse(null);
		}
		
		Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == Batsman.getID().intValue()).findAny().orElse(null);

		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
				team + "\\" + "Left_2048\\" + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
		TimeUnit.MILLISECONDS.sleep(200);
		
		if(teams.get(this_plyr.getTeamId()-1).getTeamName4() .equalsIgnoreCase("NEP")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
				teams.get(this_plyr.getTeamId()-1).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName1 "+this_plyr.getFull_name()+";");
							
		 
		this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == bowler.getID().intValue()).findAny().orElse(null);
		
		if(teams.get(this_plyr.getTeamId()-1).getTeamName4() .equalsIgnoreCase("NEP")) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 1;");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 0;");
		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
				teams.get(this_plyr.getTeamId()-1).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 "+ photo_path + 
				teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Right_2048\\" + this_plyr.getPhoto()+ CricketUtil.PNG_EXTENSION + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName2 "+this_plyr.getFull_name()+ ";");
		
		
			for(AE_Inning inn: third_party_match.getInning()) {
				
				if(inn.getShortName().equals(team)) {
					if(Batsman!=null && bowler!=null) {
				
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vStatType 0;");

						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1  RUNS ;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 0;");
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2  BALLS ;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2  0;");

						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 STRIKE RATE"+";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 -;");
						
						for(AE_Combination com: inn.getCombination()) {
							if(com.getBatsman()==Batsman.getPosition() && com.getBowler()==bowler.getPosition()) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 "+com.getRuns()+";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 "+com.getBalls()+";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 "+CricketFunctions.generateStrikeRate(com.getRuns(),com.getBalls(),2)+";");

							}
						}
					}
					
				}
			}
		
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
		
	}

	private void populateEquationWithImgBs(PrintWriter print_writer, boolean is_this_updating, AE_Cricket match,
			List<Player> allPlayers, List<Team> teams, String session_selected_broadcaster) throws InterruptedException {
		

		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			int total_balls=0,remaiming_balls=0;
			if(match.getMatchDetails().getTarget() != null) {
				
				if(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().contains(".")) {
					total_balls = (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[0])*6) + 
							(Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[1]));
				}else {
					total_balls = (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6);
				}
				
				remaiming_balls = (total_balls - ((Integer.valueOf(match.getCurrentPosition().getCurrentOversBowled())*6) + 
						Integer.valueOf(match.getCurrentPosition().getCurrentOddBallsBowled())));
				
				for(AE_Inning inn : match.getInning()) {
					if(inn.getNumber().intValue() == 2) {	
						if(is_this_updating == false) {
							if(match.getInning().get(1).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(1).getShortName() + CricketUtil.PNG_EXTENSION + ";");
						}
						for(AE_Player_Info ae_Player1 : match.getTeam().get(0).getPlayer()) {
							if(ae_Player1.getCaptain() != null && ae_Player1.getCaptain().equalsIgnoreCase("yes")) {
								Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player1.getID().intValue()).findAny().orElse(null);
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
										teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Left_2048\\" + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								TimeUnit.MILLISECONDS.sleep(200);
								
							}
						}
						for(AE_Player_Info ae_Player2 : match.getTeam().get(1).getPlayer()) {
							if(ae_Player2.getCaptain() != null && ae_Player2.getCaptain().equalsIgnoreCase("yes")) {
								Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player2.getID().intValue()).findAny().orElse(null);
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 "+ photo_path + 
										teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Right_2048\\"  + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								TimeUnit.MILLISECONDS.sleep(200);
								
							}
						}
						
						if((match.getMatchDetails().getTarget().getTarget()-1) == inn.getRuns().intValue()) {
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getShortName() + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "" + ";");
//							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + match.getMatchDetails().getScheduledOvers().getReducedOvers() + " OVERS" + ";");
							
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTopFreeText " + "SCORES ARE LEVEL" + ";");
							if(match.getInning().get(1).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "NEED" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " TO WIN" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							
						}
						else if(match.getMatchDetails().getTarget().getTarget().intValue() <= inn.getRuns().intValue()) {
							if(match.getInning().get(1).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(1).getShortName() + CricketUtil.PNG_EXTENSION + ";");
							
							if(match.getInning().get(1).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "WIN BY" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + (10 - match.getInning().get(1).getNoOfWickets()) + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "WICKETS" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "" + ";");
							
						}
						else if(inn.getNoOfWickets().intValue() >= 10 || remaiming_balls <= 0) {
							if(match.getInning().get(0).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(0).getShortName() + CricketUtil.PNG_EXTENSION + ";");
							
							if(match.getInning().get(0).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(0).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "WIN BY" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + (match.getMatchDetails().getTarget().getRunsRequired() - 1) + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural((match.getMatchDetails().getTarget().getRunsRequired() - 1)).toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "" + ";");
						}
						else {
							if(match.getInning().get(1).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "NEED" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " TO WIN" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							
						}
					}else if(inn.getNumber().intValue() == 4) {	
						if(is_this_updating == false) {
							if(match.getInning().get(3).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(3).getShortName() + CricketUtil.PNG_EXTENSION + ";");
						}
						for(AE_Player_Info ae_Player1 : match.getTeam().get(0).getPlayer()) {
							if(ae_Player1.getCaptain() != null && ae_Player1.getCaptain().equalsIgnoreCase("yes")) {
								Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player1.getID().intValue()).findAny().orElse(null);
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
										teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Left_2048\\" + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								TimeUnit.MILLISECONDS.sleep(200);
								
							}
						}
						for(AE_Player_Info ae_Player2 : match.getTeam().get(1).getPlayer()) {
							if(ae_Player2.getCaptain() != null && ae_Player2.getCaptain().equalsIgnoreCase("yes")) {
								Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player2.getID().intValue()).findAny().orElse(null);
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 "+ photo_path + 
										teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Right_2048\\"  + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								TimeUnit.MILLISECONDS.sleep(200);
								
							}
						}
						
						if((match.getMatchDetails().getTarget().getTarget()-1) == inn.getRuns().intValue()) {
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getShortName() + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "" + ";");
//							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + match.getMatchDetails().getScheduledOvers().getReducedOvers() + " OVERS" + ";");
							
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTopFreeText " + "SCORES ARE LEVEL" + ";");
							if(match.getInning().get(3).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "NEED" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " TO WIN" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							
						}
						else if(match.getMatchDetails().getTarget().getTarget().intValue() <= inn.getRuns().intValue()) {
							if(match.getInning().get(3).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(3).getShortName() + CricketUtil.PNG_EXTENSION + ";");
							
							if(match.getInning().get(3).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "WON THE" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns SUPER;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "OVER" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "" + ";");
							
						}
						else if(inn.getNoOfWickets().intValue() >= 10 || remaiming_balls <= 0) {
							if(match.getInning().get(2).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(2).getShortName() + CricketUtil.PNG_EXTENSION + ";");
							
							if(match.getInning().get(2).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(0).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "WON THE" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns SUPER;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 OVER;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "" + ";");
						}
						else {
							if(match.getInning().get(3).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "NEED" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " TO WIN" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							
						}
					}
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			break;
		}
		
	}


	private void populatePlayerIntroStats(PrintWriter print_writer, String string, Integer team_id,
			List<Player> allPlayer, List<Team> allTeams, MatchAllData match, String session_selected_broadcaster,
			AE_Cricket third_party_match) throws InterruptedException {
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
						allTeams.get(team_id-1).getTeamName1().toUpperCase() + ";");
			}
			
			
			if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
				allTeams.get(team_id-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			if(team_id == match.getSetup().getHomeTeamId()) {
				for(int i = 0; i <= match.getSetup().getHomeSquad().size() -1 ; i++) {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + (i+1) + " " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4() + "\\Right_2048\\" + match.getSetup().getHomeSquad().get(i).getPhoto() + CricketUtil.PNG_EXTENSION + ";");

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName0" + (i+1) + " " + match.getSetup().getHomeSquad().get(i).getFirstname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName0" + (i+1) + " " + match.getSetup().getHomeSquad().get(i).getSurname() + ";");

				}
			}else {
				for(int i = 0; i <= match.getSetup().getAwaySquad().size() -1 ; i++) {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + (i+1) + " " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4() + "\\Right_2048\\" + match.getSetup().getAwaySquad().get(i).getPhoto() + CricketUtil.PNG_EXTENSION + ";");

					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName0" + (i+1) + " " + match.getSetup().getAwaySquad().get(i).getFirstname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName0" + (i+1) + " " + match.getSetup().getAwaySquad().get(i).getSurname() + ";");

				}
			}
			
			this.status = CricketUtil.SUCCESSFUL;
			
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Change STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 260.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Change SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
		}
		
	}

	public void populateExtraBoundries(PrintWriter print_writer, String text, AE_Cricket third_party_match,
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
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
	}

	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster, int i)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_VIZ":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*"+ animationName + " " + animationCommand +" \0");
			break;
		case "ICC_BIG_SCREEN":
			print_writer.println("LAYER" + (i) + "*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			break;
		}
		
	}

	public void populateDecision(PrintWriter print_writer, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			
			break;
		}
	}
	
	public void populateFreeBs(PrintWriter print_writer,String Type, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
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
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 5;");
				break;
			case "TOSS":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 6;");
				if(match.getMatchDetails().getToss().getWinnerID() == match.getMatchDetails().getHomeTeam().getShortName()) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossTeam " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossTeam " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
				}
				break;
			case "WINNER":
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataSelect 7;");
				if(match.getMatchDetails().getToss().getWinnerID() != null) {
					if(match.getMatchDetails().getToss().getWinnerID()==match.getMatchDetails().getHomeTeam().getShortName()) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossTeam " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossTeam " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
					}
					
					if(match.getInning().get(0).getRuns()>match.getInning().get(1).getRuns()&&match.getInning().get(0).getShortName()==match.getMatchDetails().getHomeTeam().getShortName()&& match.getInning().get(0).getNoOfWickets()<10) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + "BY " + (10-match.getInning().get(0). getNoOfWickets()) + " WICKET" 
								+";");
					}else {
						int res= match.getInning().get(0).getRuns()-match.getInning().get(1).getRuns();
							
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + "BY " + res+ "Runs"+";");
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
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 60.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populateOutNotDecision(PrintWriter print_writer,String decision, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
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
//		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
//		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 60.0;");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
//		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
//		TimeUnit.SECONDS.sleep(1);
//		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
//		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
//		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
	}
	
	public void populatePointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> groupA, String session_selected_broadcaster,
			MatchAllData match, AE_Cricket Ae_match,String grp,List<Team> tm) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			int row_no=0;
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + grp + " - POINTS TABLE" + ";");
			
			for(int i = 0; i <= groupA.size() - 1 ; i++) {
				row_no = row_no + 1;
				
				if(Ae_match.getMatchDetails().getHomeTeam().getLongName().equalsIgnoreCase(groupA.get(i).getTeamName().toUpperCase())) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighDeHigh0" + row_no + " 1;");
				}else if(Ae_match.getMatchDetails().getAwayTeam().getLongName().equalsIgnoreCase(groupA.get(i).getTeamName().toUpperCase())) {
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
				DecimalFormat df = new DecimalFormat("0.000");
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
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	
	public void populateIdentMatch(PrintWriter print_writer,AE_Cricket tp_match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		//tp_match.getInning().get(0).getStatus()
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
					tp_match.getMatchDetails().getHomeTeam().getShortName() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
					tp_match.getMatchDetails().getAwayTeam().getShortName() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + "MATCH 1" + ";");
			
			if(tp_match.getMatchDetails().getGround().getName().toUpperCase().contains("AND")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "LIVE FROM " + 
						tp_match.getMatchDetails().getGround().getName().toUpperCase().replace("AND", "&") + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "LIVE FROM " + 
						tp_match.getMatchDetails().getGround().getName().toUpperCase() + ";");
			}
			
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 185.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
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

	public void populateEquationBs(PrintWriter print_writer,boolean is_this_updating, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning().size() == 0) {
				this.status = "ERROR: Target's inning is null";
			} else {
				
				for(AE_Inning inn : match.getInning()) {
					int balls=0;
					if(inn.getNumber() == match.getInning().size() ) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
								inn.getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						
						if(match.getMatchDetails().getTarget().getTarget()==0 ) {
							if(match.getMatchDetails().getTarget().getOversLeft()== "1") {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
								
								String balls_left=match.getMatchDetails().getTarget().getOversLeft();
								 balls=Integer.parseInt(balls_left.substring(0))*6+Integer.parseInt(balls_left.substring(1));
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + balls + " BALL" + ";");
							}else {
								if(balls >= 100) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore "+ match.getMatchDetails().getTarget().getRunsRequired() + ";");
									
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + match.getMatchDetails().getScheduledOvers().getOriginalOvers() + " OVERS" + ";");
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + match.getMatchDetails().getTarget().getTarget() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " +match.getMatchDetails().getTarget().getOversLeft() +" ;");
								}
							}
						}else {
							if(balls >= 100) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore "+ match.getMatchDetails().getTarget().getRunsRequired() + ";");
								
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + match.getMatchDetails().getScheduledOvers().getOriginalOvers() + " OVERS" + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + match.getMatchDetails().getTarget().getTarget() + ";");
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " +match.getMatchDetails().getTarget().getOversLeft() +" ;");
							}
						}
						
					}
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 185.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
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
	
	public void populateMatchID(PrintWriter print_writer,List<Player> allPlayer,List<Team> allTeams, AE_Cricket third_party_match,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
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
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
					match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
			}else if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " +
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
			}
			
			if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
			}else if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " +
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
			}
			
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " +
					match.getSetup().getMatchIdent().toUpperCase() + ";");
			
		}
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	public void populateThisOver(PrintWriter print_writer, AE_Cricket third_party_match,
			String session_selected_broadcaster) throws Exception {
		
			List<String> ballbyball = new ArrayList<String>();
			
			for(int i=0; i <= (third_party_match.getMatchDetails().getStatus().getOverBallByBall().split(" ").length) - 1;i++) {
				ballbyball.add(third_party_match.getMatchDetails().getStatus().getOverBallByBall().split(" ")[i]);
				//System.out.println(ballbyball);
				//i = i+1;
			}
//			if(!third_party_match.getBalls().isEmpty() && third_party_match.getBalls().size()>0) {
				if(ballbyball.size() <= 6) {
					for(int j = 1; j<7; j++) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall" + j + " " + "" + ";");	
					}
					for(int i=0;i<=ballbyball.size()-1;i++) {
						
						if(ballbyball.get(i).toLowerCase().contains("nb")) {
							if(ballbyball.get(i).contains("0")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + "NB" + ";");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i).toLowerCase() + ";");	
							}
						}else if(ballbyball.get(i).toLowerCase().contains("wd")) {
							if(ballbyball.get(i).contains("0")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + "Wd" + ";");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i).replace("D", "d") + ";");	
							}
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i) + ";");	
						}
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBalls " + "0" + ";");
				}
				else if(ballbyball.size() > 6 && ballbyball.size() <= 7) {
					
					for(int j = 1; j<8; j++) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall" + j + " " + "" + ";");	
					}
					for(int i=0;i<=ballbyball.size()-1;i++) {
						
						if(ballbyball.get(i).toLowerCase().contains("nb")) {
							if(ballbyball.get(i).contains("0")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + "NB" + ";");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i).toLowerCase() + ";");	
							}
						}else if(ballbyball.get(i).toLowerCase().contains("wd")) {
							if(ballbyball.get(i).contains("0")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + "Wd" + ";");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i).replace("D", "d") + ";");	
							}
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i) + ";");	
						}
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBalls " + "1" + ";");
				}
				else if(ballbyball.size() > 7 && ballbyball.size() <= 8) {
					for(int j = 1; j<9; j++) {	
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall" + j + " " + "" + ";");	
					}
					for(int i=0;i<=ballbyball.size()-1;i++) {
						
						if(ballbyball.get(i).toLowerCase().contains("nb")) {
							if(ballbyball.get(i).contains("0")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + "NB" + ";");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i).toLowerCase() + ";");	
							}
						}else if(ballbyball.get(i).toLowerCase().contains("wd")) {
							if(ballbyball.get(i).contains("0")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + "Wd" + ";");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i).replace("D", "d") + ";");	
							}
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i) + ";");	
						}
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBalls " + "2" + ";");
				}	
				else if(ballbyball.size() >8 && ballbyball.size() <= 9) {
					for(int j = 1; j<10; j++) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall" + j + " " + "" + ";");	
					}
					for(int i=0;i<=ballbyball.size()-1;i++) {
						
						if(ballbyball.get(i).toLowerCase().contains("nb")) {
							if(ballbyball.get(i).contains("0")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + "NB" + ";");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i).toLowerCase() + ";");	
							}
						}else if(ballbyball.get(i).toLowerCase().contains("wd")) {
							if(ballbyball.get(i).contains("0")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + "Wd" + ";");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i).replace("D", "d") + ";");	
							}
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBall"+(1+i)+" " + ballbyball.get(i) + ";");	
						}
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vNoOfBalls " + "3" + ";");
				}	
//			}	
				
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

	}
	public void populatePartnership(PrintWriter print_writer,boolean is_this_updating,List<Player> allPlayer,List<Team> allTeams, AE_Cricket third_party_match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			for(AE_Inning inn : third_party_match.getInning()) {
				if (third_party_match.getInning().size() == inn.getNumber()) {
					for(Player dbplayers : allPlayer) {
//						System.out.println(inn.getPartnership().get(inn.getPartnership().size() - 1).getBat1ID() + "   " + dbplayers.getAe_Id());
						if(inn.getPartnership().get(inn.getPartnership().size() - 1).getBat1ID() == dbplayers.getAe_Id()) {
							if(allTeams.get(dbplayers.getTeamId()-1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									allTeams.get(dbplayers.getTeamId()-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 "+ photo_path + 
									allTeams.get(dbplayers.getTeamId()-1).getTeamName4() + "\\" + "Left_2048\\" + dbplayers.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName1 " + dbplayers.getTicker_name() + ";");
						}
						
						if(inn.getPartnership().get(inn.getPartnership().size() - 1).getBat2ID() == dbplayers.getAe_Id()) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 "+ photo_path + 
									allTeams.get(dbplayers.getTeamId()-1).getTeamName4() + "\\" + "Right_2048\\" + dbplayers.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName2 " + dbplayers.getTicker_name() + ";");
							
						}
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns1 " + 
							inn.getPartnership().get(inn.getPartnership().size() - 1).getBat1Runs() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls1 " + 
							"(" + inn.getPartnership().get(inn.getPartnership().size() - 1).getBat1Balls() + ")" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns2 " + 
							inn.getPartnership().get(inn.getPartnership().size() - 1).getBat2Runs() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls2 " + 
							"(" + inn.getPartnership().get(inn.getPartnership().size() - 1).getBat2Balls() + ")" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScorePART " + 
							inn.getPartnership().get(inn.getPartnership().size() - 1).getRuns() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsPART " + 
							"OFF " + inn.getPartnership().get(inn.getPartnership().size() - 1).getBalls() + " BALLS" + ";");
				}
			}
		}
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	public Infobar populateScoreBoard(Infobar infobar, PrintWriter print_writer,String scene,List<Player> allPlayer,AE_Cricket match, String broadcaster) throws InterruptedException, IOException 
	{
		infobar = populateScore(infobar, print_writer, false, match, broadcaster);
		infobar = populateScoreBoardMiddle(infobar,false,print_writer,allPlayer,match, broadcaster);
		infobar = populateScoreBoardBottom(infobar,false,print_writer,allPlayer,match, broadcaster);

		return infobar;
	}
	public Infobar populateScore(Infobar infobar, PrintWriter print_writer,boolean is_this_updating, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			for(AE_Inning inn : match.getInning()) {
				if (match.getInning().size() == inn.getNumber()) {
					if(is_this_updating == false) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getShortName().toUpperCase() + ";");
					}
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTime " + new SimpleDateFormat("HH:mm").format(new Date()) + ";");
					if(inn.getNoOfWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + inn.getRuns() + ";");
					}
					else{
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + inn.getRuns() + "-" + inn.getNoOfWickets() + ";");
					}
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "OVERS : " + inn.getOvers() + 
							" (" + match.getMatchDetails().getScheduledOvers().getReducedOvers() + ")" + ";");
					
					if (match.getMatchDetails().getStatus().getCurrentInnings() == 1) {
						if((Integer.valueOf(match.getCurrentPosition().getCurrentOversBowled())*6) + 
								Integer.valueOf(match.getCurrentPosition().getCurrentOddBallsBowled()) <= 30) {
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunRate " + "TOSS " + 
									match.getMatchDetails().getToss().getWinnerID() + ";");
							
						}else if((Integer.valueOf(match.getCurrentPosition().getCurrentOversBowled())*6) + 
								Integer.valueOf(match.getCurrentPosition().getCurrentOddBallsBowled()) > 30) {
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunRate " + "CURRENT RUN RATE " + inn.getRunRate() + ";");
						}
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunRate " + "REQUIRED RUN RATE " + 
								match.getMatchDetails().getTarget().getRequiredRunRate() + ";");
					}
				}
			}
		}
		return infobar;
	}
	public Infobar populateScoreBoardMiddle(Infobar infobar, boolean is_this_updating, PrintWriter print_writer,List<Player> allPlayers,AE_Cricket match, String broadcaster) throws InterruptedException
	{ 
		List<AE_Batsman> current_batsmen = new ArrayList<AE_Batsman>();
			for(AE_Inning inn : match.getInning()) {
				if (match.getInning().size() == inn.getNumber()) {
					for (AE_Batsman bc : inn.getBatsman()) {
						if(inn.getPartnership() != null && inn.getPartnership().size() > 0) {
							if(bc.getId() == inn.getPartnership().get(inn.getPartnership().size() - 1).getBat1ID()) {
								current_batsmen.add(bc);
							} else if(bc.getId() == inn.getPartnership().get(inn.getPartnership().size() - 1).getBat2ID()) {
								current_batsmen.add(bc);
							}
						}
					}
					
					if(infobar.getLast_batsmen() == null || infobar.getLast_batsmen().size() <= 0) {
						infobar.setLast_ae_batsmen(current_batsmen);
					}
					populateCurrentBatsmenScoreBoard(infobar,print_writer,allPlayers,match, broadcaster,current_batsmen);
				}
			}
		//	infobar.setLast_middle_section(CricketUtil.BATSMAN);
			//break;	
	//	}
		return infobar;
	}
	
	public Infobar populateCurrentBatsmenScoreBoard(Infobar infobar, PrintWriter print_writer,List<Player> allPlayers,AE_Cricket match,String broadcaster,List<AE_Batsman> current_batsmen) throws InterruptedException
	{
		for(AE_Inning inn : match.getInning()) {
			
			if (match.getInning().size() == inn.getNumber()) {
				
				if(current_batsmen != null && current_batsmen.size() >= 1) {
					if(infobar.getLast_ae_batsmen() != null && infobar.getLast_ae_batsmen().size() >= 1) {
						if(infobar.getLast_ae_batsmen().get(0).getId() != current_batsmen.get(0).getId()) {
							TimeUnit.MILLISECONDS.sleep(800);
							Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id() == current_batsmen.get(0).getId().intValue()).findAny().orElse(null);
							if(this_plyr != null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + this_plyr.getFull_name() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore2 " + current_batsmen.get(0).getRuns()  + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBalls2 " + current_batsmen.get(0).getBalls() + ";");
							
						}else {
							Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == current_batsmen.get(0).getId().intValue()).findAny().orElse(null);
							if(this_plyr != null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + this_plyr.getFull_name() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore2 " + current_batsmen.get(0).getRuns()  + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBalls2 " + current_batsmen.get(0).getBalls() + ";");
							
						}
						
						if(infobar.getLast_ae_batsmen().get(1).getId() != current_batsmen.get(1).getId()) {
							TimeUnit.MILLISECONDS.sleep(800);
							Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == current_batsmen.get(1).getId().intValue()).findAny().orElse(null);
							if(this_plyr != null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName1 " + this_plyr.getFull_name() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore1 " + current_batsmen.get(1).getRuns()  + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBalls1 " + current_batsmen.get(1).getBalls() + ";");
						}else {
							Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == current_batsmen.get(1).getId().intValue()).findAny().orElse(null);
							if(this_plyr != null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName1 " + this_plyr.getFull_name() + ";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore1 " + current_batsmen.get(1).getRuns()  + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBalls1 " + current_batsmen.get(1).getBalls() + ";");
							
						}
					}
					
					if(current_batsmen.get(0).getHowOut().equalsIgnoreCase("notOut")) {
						if(current_batsmen.get(0).getFacing().equalsIgnoreCase("OnStrike")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike2 " + "1" + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike2 " + "0" + ";");
						}
					}
					if(current_batsmen.get(1).getHowOut().equalsIgnoreCase("notOut")) {
						if(current_batsmen.get(1).getFacing().equalsIgnoreCase("OnStrike")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike1 " + "1" + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike1 " + "0" + ";");
						}
					}
				}
			}
		}
			
		infobar.setLast_ae_batsmen(current_batsmen);
		return infobar;
	}
	public void populateBatsmanWagon(PrintWriter print_writer, String string, int team_id, int player_id, List<Player> allPlayer,List<Team> allTeams,
			MatchAllData match, String session_selected_broadcaster2,AE_Cricket third_party_match) throws Exception {
		int s1=0,s2=0,s3=0,s4=0,s5=0,s6=0;
		for(AE_Team t:third_party_match.getTeam()) {
			for(AE_Player_Info plyr:t.getPlayer()) {
				if(plyr.getID().intValue()==allPlayer.get(player_id-1).getAe_Id().intValue()) {
					if(allTeams.get(team_id-1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							allTeams.get(team_id-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
					if(allPlayer.get(player_id-1).getFirstname() != null) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getFirstname() + ";");
						
						if(allPlayer.get(player_id-1).getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + allPlayer.get(player_id-1).getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
						}
					}else {
						if(allPlayer.get(player_id-1).getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getSurname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
							
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getTicker_name() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
						}
					}
					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getFirstname() + ";");
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + allPlayer.get(player_id-1).getSurname() + ";");
					
					
					for(AE_Inning inn:third_party_match.getInning()) {
						for(AE_Batsman bman:inn.getBatsman())	{	
							if(bman.getId().intValue()==plyr.getID().intValue()) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPScore " + bman.getRuns()+ ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + " OFF " + bman.getBalls() + ";");
								
								if(plyr.getBatStyle().equalsIgnoreCase("RightHand")&& bman.getId().intValue()==plyr.getID().intValue()) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSide1 " + " OFF"+ ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSide2 " + " LEG"+ ";");
										
								}else if(plyr.getBatStyle().equalsIgnoreCase("LeftHand")&& bman.getId().intValue()==plyr.getID().intValue()) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSide2 " + " OFF"+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSide1 " + " LEG"+ ";");	
										
								}
								if(bman.getWagonData()!=null) {
									for(AE_WagonData wd:bman.getWagonData()) {
										if(plyr.getBatStyle().equalsIgnoreCase("RightHand")) {
											
											if(wd.getS().intValue()==1)
												s1=s1+wd.getR().intValue();
											if(wd.getS().intValue()==2)
												s2=s2+wd.getR().intValue();
											if(wd.getS().intValue()==3)	
												s3=s3+wd.getR().intValue();
											if(wd.getS().intValue()==4)
												s4=s4+wd.getR().intValue();
											if(wd.getS().intValue()==5)	
												s5=s5+wd.getR().intValue();
											if(wd.getS().intValue()==6)
												s6=s6+wd.getR().intValue();
											
										}else if(plyr.getBatStyle().equalsIgnoreCase("LeftHand")){
											
											if(wd.getS().intValue()==6)
												s1=s1+wd.getR().intValue();
											if(wd.getS().intValue()==5)
												s2=s2+wd.getR().intValue();
											if(wd.getS().intValue()==4)	
												s3=s3+wd.getR().intValue();
											if(wd.getS().intValue()==3)
												s4=s4+wd.getR().intValue();
											if(wd.getS().intValue()==2)	
												s5=s5+wd.getR().intValue();
											if(wd.getS().intValue()==1)
												s6=s6+wd.getR().intValue();	
										}
										
									}
								}
							}
						}
					}
				}	
			}
		}
		
		for(int i=1;i<7;i++) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns"+i+" " +" "+ ";");	
		}
			
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns1 " +s1+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns2 " + s2+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns3 " + s3+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns4 " + s4+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns5 " + s5+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns6 " + s6+ ";");
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	public Infobar populateScoreBoardBottom(Infobar infobar,boolean is_this_updating, PrintWriter print_writer,List<Player> allPlayers,AE_Cricket match, String broadcaster) throws InterruptedException
	{
		
		for(AE_Inning inn : match.getInning()) {
			if (match.getInning().size() == inn.getNumber()) {
				for(AE_Bowler boc : inn.getBowler()) {
					if(boc.getBowlingNow() != null && boc.getBowlingNow().equalsIgnoreCase("This")) {
						Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id() == boc.getID().intValue()).findAny().orElse(null);
						if(this_plyr != null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + this_plyr.getFull_name() + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerStat " + boc.getOvers() + "-" + boc.getMaidens() + "-" 
								+ boc.getRuns() + "-" + boc.getWickets() + ";");
					}
				}
			}
		}
		
		return infobar;
	}
	public Infobar populateScorebug(PrintWriter print_writer,boolean is_this_updating, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			for(AE_Inning inn : match.getInning()) {
				if (match.getInning().size() == inn.getNumber()) {
					if(is_this_updating == false) {
//						System.out.println("Overs = " + match.getCurrentPosition().getCurrentOversBowled() + "  " + match.getCurrentPosition().getCurrentOddBallsBowled());
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamNameSB " + inn.getShortName().toUpperCase() + ";");
						if(inn.getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
								inn.getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
					if(inn.getNoOfWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreSB " + inn.getRuns() + ";");
					}
					else{
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreSB " + inn.getRuns() + "-" + inn.getNoOfWickets() + ";");
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversSB " + inn.getOvers() + ";");
					if (match.getMatchDetails().getStatus().getCurrentInnings() == 1) {
						if(infobar.getScorebug_last_value() != null && infobar.getScorebug_last_value() != "" && !infobar.getScorebug_last_value().isEmpty()) {
							
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + match.getMatchDetails().getToss().getWinnerID().toUpperCase() + " WON TOSS AND " + match.getMatchDetails().getToss().getDecision().toUpperCase() + ";");
							infobar.setScorebug_last_value("TOSS");
//							if(Integer.valueOf(CricketFunctions.OverBalls(Integer.valueOf(inn.getOvers().split("\\.")[0]), Integer.valueOf(inn.getOvers().split("\\.")[1]))) > 30) {
//								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "CURRENT RUN RATE " + inn.getRunRate() + ";");
//								infobar.setScorebug_last_value("CURRENT_RUN_RATE");
//							}else {
//								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "TOSS " + match.getMatchDetails().getToss().getWinnerID().toUpperCase() + " WON TOSS AND " + match.getMatchDetails().getToss().getDecision().toUpperCase() + ";");
//								infobar.setScorebug_last_value("TOSS");
//							}
						}
						
					}else {
						if(infobar.getScorebug_last_value() != null && infobar.getScorebug_last_value() != "" && !infobar.getScorebug_last_value().isEmpty()) {
							
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + "TARGET " + match.getMatchDetails().getTarget().getTarget() + ";");
							infobar.setScorebug_last_value("TARGET");
						}
					}
				}
			}
		}
		return infobar;
	}
	
	public Infobar populateScorebugChangeOn(PrintWriter print_writer,String value, AE_Cricket match, String broadcaster) throws InterruptedException, IOException 
	{
		switch (value.toUpperCase()) {
		case "TOSS":
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " +  match.getMatchDetails().getToss().getWinnerID().toUpperCase() + " WON TOSS AND " + match.getMatchDetails().getToss().getDecision().toUpperCase() + ";");
			break;
		case "CURRENT_RUN_RATE":
			for(AE_Inning inn : match.getInning()) {
				if (inn.getNumber() == match.getMatchDetails().getStatus().getCurrentInnings()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + "CURRENT RUN RATE " + inn.getRunRate() + ";");
				}
			}
			break;
		case "REQUIRED_RUN_RATE":
			for(AE_Inning inn : match.getInning()) {
				if (inn.getNumber() == match.getMatchDetails().getStatus().getCurrentInnings()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + "REQUIRED RUN RATE " + match.getMatchDetails().getTarget().getRequiredRunRate() + ";");
				}
			}
			break;
		case "TARGET":
			for(AE_Inning inn : match.getInning()) {
				if (inn.getNumber() == match.getMatchDetails().getStatus().getCurrentInnings()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTextSB " + "TARGET " + match.getMatchDetails().getTarget().getTarget() + ";");
				}
			}
			break;	
		}
		infobar.setScorebug_last_value(value);
		return infobar;
	}
	
	public Infobar populateInfobar(Infobar infobar, PrintWriter print_writer,String scene, AE_Cricket match, String broadcaster) throws InterruptedException, IOException 
	{
		infobar = populateInfo(infobar, print_writer, false, match, broadcaster);
		infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, broadcaster);

		return infobar;
	}
	
	public void populateBallSpeed(PrintWriter print_writer,AE_Last_Ball third_party_last_ball_speed, String session_selected_broadcaster) throws InterruptedException, IOException, JAXBException 
	{
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.getSpeed_of_ball_from_ThirdParty(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SPEED_DIRECTORY+
				CricketUtil.Cricket_LAST_BALL_SPEED_THIRDPARTY).getSpeed().get(0).getValues() + ";");     
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

	
	}
	
	public void populateTargetWithImgBs(PrintWriter print_writer,AE_Cricket match,List<Player>allPlayers,List<Team> teams,String session_selected_broadcaster) throws Exception {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			
			for(AE_Inning inn : match.getInning()) {
				
				if(match.getInning().get(match.getInning().size()-1).getNumber() == 1 || match.getInning().get(match.getInning().size()-1).getNumber() == 3) {
					int runs = 0;
					runs = inn.getRuns() + 1;
					
					
					if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
						if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().toUpperCase().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getMatchDetails().getAwayTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION +";");
						TimeUnit.MILLISECONDS.sleep(200);
					}else {
						if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().toUpperCase().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getMatchDetails().getHomeTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION +";");
						TimeUnit.MILLISECONDS.sleep(200);
					}
					
					for(AE_Player_Info ae_Player1 : match.getTeam().get(0).getPlayer()) {
						if(ae_Player1.getCaptain() != null && ae_Player1.getCaptain().equalsIgnoreCase("yes")) {
							Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player1.getID().intValue()).findAny().orElse(null);
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
									teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Left_2048\\" + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							TimeUnit.MILLISECONDS.sleep(200);
							
						}
					}
					for(AE_Player_Info ae_Player2 : match.getTeam().get(1).getPlayer()) {
						if(ae_Player2.getCaptain() != null && ae_Player2.getCaptain().equalsIgnoreCase("yes")) {
							Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player2.getID().intValue()).findAny().orElse(null);
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 "+ photo_path + 
									teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Right_2048\\"  + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							TimeUnit.MILLISECONDS.sleep(200);
							
						}
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue1 " + runs + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
							(Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6) + ";");
					TimeUnit.MILLISECONDS.sleep(200);
					
					break;
				}else if(match.getInning().get(match.getInning().size()-1).getNumber() == 2 || match.getInning().get(match.getInning().size()-1).getNumber() == 4) {
					if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().toUpperCase() + CricketUtil.PNG_EXTENSION +";");
					TimeUnit.MILLISECONDS.sleep(200);
					
					for(AE_Player_Info ae_Player1 : match.getTeam().get(0).getPlayer()) {
						if(ae_Player1.getCaptain() != null && ae_Player1.getCaptain().equalsIgnoreCase("yes")) {
							Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player1.getID().intValue()).findAny().orElse(null);
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage1 " + photo_path + 
									teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Left_2048\\" + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							TimeUnit.MILLISECONDS.sleep(200);
							
						}
					}
					for(AE_Player_Info ae_Player2 : match.getTeam().get(1).getPlayer()) {
						if(ae_Player2.getCaptain() != null && ae_Player2.getCaptain().equalsIgnoreCase("yes")) {
							Player this_plyr = allPlayers.stream().filter(plyr -> plyr.getAe_Id().intValue() == ae_Player2.getID().intValue()).findAny().orElse(null);
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage2 "+ photo_path + 
									teams.get(this_plyr.getTeamId()-1).getTeamName4() + "\\" + "Right_2048\\"  + this_plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							TimeUnit.MILLISECONDS.sleep(200);
							
						}
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue1 " + match.getMatchDetails().getTarget().getTarget() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tValue2 " + 
							(Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6) + ";");
					TimeUnit.MILLISECONDS.sleep(200);
					
					break;
				}
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUNS"  +";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" +";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public Infobar populateInfo(Infobar infobar, PrintWriter print_writer,boolean is_this_updating, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
{
	switch (session_selected_broadcaster.toUpperCase()) {
	case "ICC_BIG_SCREEN":
		for(AE_Inning inn : match.getInning()) {
			if (inn.getNumber() == match.getInning().size()) {
				if(is_this_updating == false) {
					if(inn.getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getShortName().toUpperCase() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
							inn.getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				}
				if(inn.getNoOfWickets() >= 10) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + inn.getRuns() + ";");
				}
				else{
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + inn.getRuns() + "-" + inn.getNoOfWickets() + ";");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOverIS " + inn.getOvers() + ";");
				if (match.getMatchDetails().getStatus().getCurrentInnings() == 1 || match.getMatchDetails().getStatus().getCurrentInnings() == 3) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "CURRENT RUN RATE " + inn.getRunRate() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "" + matchSummary(match,match.getMatchDetails().getStatus().getCurrentInnings()).toUpperCase() + ";");
				}
			}
		}
	}
	return infobar;
}

	public Infobar populateVizInfobarMiddle(Infobar infobar, boolean is_this_updating, PrintWriter print_writer,AE_Cricket match, String broadcaster) throws InterruptedException
	{ 
		List<AE_Batsman> current_batsmen = new ArrayList<AE_Batsman>();
			for(AE_Inning inn : match.getInning()) {
				if (inn.getNumber() == match.getInning().size()) {
					for (AE_Batsman bc : inn.getBatsman()) {
						if(inn.getPartnership() != null && inn.getPartnership().size() > 0) {
							if(bc.getId() == inn.getPartnership().get(inn.getPartnership().size() - 1).getBat1ID()) {
								current_batsmen.add(bc);
							} else if(bc.getId() == inn.getPartnership().get(inn.getPartnership().size() - 1).getBat2ID()) {
								current_batsmen.add(bc);
							}
						}
					}
					
					if(infobar.getLast_batsmen() == null || infobar.getLast_batsmen().size() <= 0) {
						infobar.setLast_ae_batsmen(current_batsmen);
					}
					populateCurrentBatsmen(infobar,print_writer, match, broadcaster,current_batsmen);
					if(!is_this_updating) {
						print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
						print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
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
	public Infobar populateCurrentBatsmen(Infobar infobar, PrintWriter print_writer, AE_Cricket match, String broadcaster,List<AE_Batsman> current_batsmen) throws InterruptedException
	{
		for(AE_Inning inn : match.getInning()) {
			
			if (inn.getNumber() == match.getInning().size()) {
				
				if(current_batsmen != null && current_batsmen.size() >= 1) {
					if(infobar.getLast_ae_batsmen() != null && infobar.getLast_ae_batsmen().size() >= 1) {
						if(infobar.getLast_ae_batsmen().get(0).getId() != current_batsmen.get(0).getId()) {
//							processAnimation(print_writer, "Batsman1Out", "START", broadcaster);
							TimeUnit.MILLISECONDS.sleep(800);
							if(current_batsmen.get(0).getFacing().equals("OnStrike")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName1 " + current_batsmen.get(0).getName().toUpperCase() + "*;");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName1 " + current_batsmen.get(0).getName().toUpperCase() + " ;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore1IS " + current_batsmen.get(0).getRuns() + " (" + 
											current_batsmen.get(0).getBalls() + ")" + ";");
							
						}else {
							if(current_batsmen.get(0).getFacing().equals("OnStrike")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName1 " + current_batsmen.get(0).getName().toUpperCase() + "*;");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName1 " + current_batsmen.get(0).getName().toUpperCase() + " ;");
							}
							//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName1 " + current_batsmen.get(0).getName().toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore1IS " + current_batsmen.get(0).getRuns() + " (" + 
									current_batsmen.get(0).getBalls() + ")" + ";");
							
//							if(current_batsmen.get(0).getHowOut().equalsIgnoreCase(CricketUtil.OUT) 
//									|| current_batsmen.get(0).getHowOut().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) { 
////								processAnimation(print_writer, "Batsman1Dehighlight", "SHOW 0.260", broadcaster);
//							}else if(current_batsmen.get(0).getHowOut().equalsIgnoreCase(CricketUtil.NOT_OUT)){
////								processAnimation(print_writer, "Batsman1Dehighlight", "SHOW 0.0", broadcaster);
//							}
						}
						
						if(infobar.getLast_ae_batsmen().get(1).getId() != current_batsmen.get(1).getId()) {
//							processAnimation(print_writer, "Batsman2Out", "START", broadcaster);
							TimeUnit.MILLISECONDS.sleep(800);
							if(current_batsmen.get(1).getFacing().equals("OnStrike")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + current_batsmen.get(1).getName().toUpperCase() + "*;");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + current_batsmen.get(1).getName().toUpperCase() + " ;");
							}
							//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + current_batsmen.get(1).getName().toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore2IS " + current_batsmen.get(1).getRuns() + " (" + 
									current_batsmen.get(1).getBalls() + ")" + ";");
						}else {
							if(current_batsmen.get(1).getFacing().equals("OnStrike")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + current_batsmen.get(1).getName().toUpperCase() + "*;");	
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + current_batsmen.get(1).getName().toUpperCase() + " ;");
							}
							//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterName2 " + current_batsmen.get(1).getName().toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScore2IS " + current_batsmen.get(1).getRuns() + " (" + 
									current_batsmen.get(1).getBalls() + ")" + ";");
							
//							if(current_batsmen.get(1).getHowOut().equalsIgnoreCase(CricketUtil.OUT) 
//									|| current_batsmen.get(1).getHowOut().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) { 
////								processAnimation(print_writer, "Batsman2Dehighlight", "SHOW 0.260", broadcaster);
//							}else if(current_batsmen.get(0).getHowOut().equalsIgnoreCase(CricketUtil.NOT_OUT)){
////								processAnimation(print_writer, "Batsman2Dehighlight", "SHOW 0.0", broadcaster);
//							}
						}
					}
//					if(current_batsmen.get(0).getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
//						if(current_batsmen.get(0).get().equalsIgnoreCase(CricketUtil.YES)) {
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vStrike " + "0" + ";");
//						}
//					}
//					if(current_batsmen.get(1).getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
//						if(current_batsmen.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vStrike " + "1" + ";");
//						}	
//					}
				}
			}
		}
			
		infobar.setLast_ae_batsmen(current_batsmen);
		return infobar;
	}
	
	public void populateBatsmanStats(PrintWriter print_writer, String string, Integer team_id, Integer player_id,
			List<Player> allPlayer, List<Team> allTeams, MatchAllData match, String session_selected_broadcaster2,
			AE_Cricket third_party_match) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = "STILL";
			for(int i=0;i<=third_party_match.getInning().size()-1;i++) {
				if(third_party_match.getInning().get(i).getShortName().equalsIgnoreCase(allTeams.get(team_id-1).getTeamName4())) {
					if(third_party_match.getInning().get(i).getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						allTeams.get(team_id-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					for(AE_Batsman bc : third_party_match.getInning().get(i).getBatsman()) {
						if(bc.getId().intValue() == allPlayer.get(player_id-1).getAe_Id().intValue()) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
									allTeams.get(team_id-1).getTeamName4() + "\\Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							
							if(allPlayer.get(player_id-1).getFirstname() != null) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getFirstname() + ";");
								
								if(allPlayer.get(player_id-1).getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + allPlayer.get(player_id-1).getSurname() + ";");
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}else {
								if(allPlayer.get(player_id-1).getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getSurname() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
									
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getTicker_name() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}
							
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +allPlayer.get(player_id-1).getFirstname() + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + allPlayer.get(player_id-1).getSurname() + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUNS" + ";");
							if(bc.getHowOut() != null) {
								if(bc.getHowOut().equalsIgnoreCase("notOut")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + bc.getRuns() + "*" + ";");
								}
								else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + bc.getRuns() + ";");
								}
								this.status = CricketUtil.SUCCESSFUL;
							}
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALLS" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + bc.getBalls() + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "FOURS" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + bc.getFours() + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "SIXES" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + bc.getSixes() + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead5 " + "STRIKE RATE" + ";");
							
							if(bc.getStrikeRate() == null) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue5 " + "-" + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue5 " + bc.getStrikeRate() + ";");
							}
							
							this.status = CricketUtil.SUCCESSFUL;
						}
					}
				}
			}
			
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			if(this.status.equalsIgnoreCase("STILL")) {
				print_writer.println("LAYER" + (current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				current_layer = 5-current_layer;
			}
		}
	}
	public void populateBowlerStats(PrintWriter print_writer, String string, Integer inning, String player_id,
			List<Player> allPlayer, List<Team> allTeams, MatchAllData match, String session_selected_broadcaster2,
			AE_Cricket third_party_match) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {			
			for(int i=0;i<=third_party_match.getInning().size()-1;i++) {
//				if(third_party_match.getInning().get(i).getShortName().equalsIgnoreCase(allTeams.get(team_id-1).getTeamName4())) {
					
					for(AE_Bowler boc : third_party_match.getInning().get(i).getBowler()) {
						if(boc.getName().equalsIgnoreCase(player_id)) {
							for(Player plyr : allPlayer) {
								if(boc.getID().intValue() == plyr.getAe_Id().intValue()) {
									if(allTeams.get(plyr.getTeamId()-1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
									}
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
											allTeams.get(plyr.getTeamId()-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
											allTeams.get(plyr.getTeamId()-1).getTeamName4().toUpperCase() + "\\Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
									if(plyr.getFirstname() != null) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
										
										if(plyr.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
										}
									}else {
										if(plyr.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getSurname() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
											
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getTicker_name() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
										}
									}
									
									
//									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
//									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "OVERS" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + boc.getOvers() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "RUNS" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + boc.getRuns() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "WICKETS" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + boc.getWickets() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "DOTS" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + boc.getDotBalls() + ";");
									
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead5 " + "ECONOMY" + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue5 " + boc.getEconomy() + ";");
									
									break;
								}
							}
						}
					}
				//}
			}
			
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateBowlerFig(PrintWriter print_writer, String string, Integer inning, String player_id,
			List<Player> allPlayer, List<Team> allTeams, MatchAllData match, String session_selected_broadcaster2,
			AE_Cricket third_party_match) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {			
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "" + ";");

			
			for(int i=0;i<=third_party_match.getInning().size()-1;i++) {
//				if(third_party_match.getInning().get(i).getShortName().equalsIgnoreCase(allTeams.get(team_id-1).getTeamName4())) {
//					
					for(AE_Bowler boc : third_party_match.getInning().get(i).getBowler()) {
						if(boc.getName().equalsIgnoreCase(player_id)) {
							for(Player plyr : allPlayer) {
								if(boc.getID().intValue() == plyr.getAe_Id().intValue()) {
									if(allTeams.get(plyr.getTeamId()-1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
									}
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
											allTeams.get(plyr.getTeamId()-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
											allTeams.get(plyr.getTeamId()-1).getTeamName4().toUpperCase() + "\\" + "Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
									if(plyr.getFirstname() != null) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
										
										if(plyr.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
										}
									}else {
										if(plyr.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getSurname() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
											
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getTicker_name() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
										}
									}
									
//									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
//									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPScore " + boc.getWickets() + "-" + boc.getRuns() + ";");

									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + boc.getOvers() + " OVER" + 
											CricketFunctions.Plural(Integer.valueOf(boc.getOvers())).toUpperCase() + ";");
									
									break;
								}
							}
						}
					}
				//}
			}
			
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	public void populateBatterScore(PrintWriter print_writer, String string, Integer team_id, Integer player_id,
			List<Player> allPlayer, List<Team> allTeams, MatchAllData match, String session_selected_broadcaster2,
			AE_Cricket third_party_match) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = "STILL";
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "" + ";");	

			for(int i=0;i<=third_party_match.getInning().size()-1;i++) {
				if(third_party_match.getInning().get(i).getShortName().equalsIgnoreCase(allTeams.get(team_id-1).getTeamName4())) {
					if(allTeams.get(team_id-1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						allTeams.get(team_id-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					for(AE_Batsman bc : third_party_match.getInning().get(i).getBatsman()) {
						if(bc.getId().intValue() == allPlayer.get(player_id-1).getAe_Id().intValue()) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
									allTeams.get(team_id-1).getTeamName4() + "\\" + "Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							
							if(allPlayer.get(player_id-1).getFirstname() != null) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getFirstname() + ";");
								
								if(allPlayer.get(player_id-1).getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + allPlayer.get(player_id-1).getSurname() + ";");
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}else {
								if(allPlayer.get(player_id-1).getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getSurname() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
									
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getTicker_name() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getFirstname() + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + allPlayer.get(player_id-1).getSurname() + ";");
							
							if(bc.getHowOut() != null) {
								if(bc.getHowOut().equalsIgnoreCase("notOut")) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPScore " + bc.getRuns() +"*"+ ";");
								}
								else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPScore " + bc.getRuns() + ";");	
								}
								this.status = CricketUtil.SUCCESSFUL;
							}
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " +"OFF " + bc.getBalls()  + " BALLS"+ ";");
							
						
						}
						
					}
					
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			if(this.status.equalsIgnoreCase("STILL")) {
				print_writer.println("LAYER" + (current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				current_layer = 5- current_layer;
			}
		}
	}

	public void populateBoundary(PrintWriter print_writer,String scene, String data, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (data.toUpperCase()) {
		case "FOUR":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "4" + ";");
			break;
		case "SIX":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "6" + ";");
			break;	
		}
	
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateDlsParScore(PrintWriter print_writer, AE_Cricket match,List<DuckWorthLewis> dls) throws InterruptedException, IOException 
	{
		int balls = 0, overs = 0;
		
		for(AE_Inning inn : match.getInning()) {
			if(match.getMatchDetails().getStatus().getCurrentInnings() == inn.getNumber()) {
				overs = Integer.valueOf(match.getCurrentPosition().getCurrentOversBowled());
				balls = Integer.valueOf(match.getCurrentPosition().getCurrentOddBallsBowled());
			}
			
			if (inn.getNumber() == match.getInning().size()) {
				if(inn.getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getShortName().toUpperCase() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
						inn.getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				if(inn.getNoOfWickets() >= 10) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + inn.getRuns() + ";");
				}
				else{
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + inn.getRuns() + "-" + inn.getNoOfWickets() + ";");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOverIS " + inn.getOvers() + ";");
				
			}
		}
		this_data_str = new ArrayList<String>();
		for(int i = 0; i<= dls.size() -1;i++) {
			if(dls.get(i).getOver_left().split("\\.")[0].equalsIgnoreCase(String.valueOf(overs))) {
				for(int j=0;j<6;j++) {
					if(balls == j) {
						this_data_str.add(CricketFunctions.populateDuckWorthLewisAe(match).get(i+j).getWkts_down());
						break;
					}
				}
				break;
			}
		}
		
		this_data_str.add(CricketFunctions.populateDlsAe(match, CricketUtil.FULL, Integer.valueOf(this_data_str.get(0))));
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue " + this_data_str.get(0) + ";");
		
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
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateFreeHit(PrintWriter print_writer,String scene, String data, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
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
	
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateFour(PrintWriter print_writer,String scene, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "4" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "0" + ";");
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateSix(PrintWriter print_writer,String scene, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "6" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "1" + ";");

		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateWicket(PrintWriter print_writer,String scene, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "WICKET" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "7" + ";");

		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateWide(PrintWriter print_writer,String scene, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "WIDE" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "0" + ";");

		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateDuck(PrintWriter print_writer,String scene, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + "DUCK" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect " + "5" + ";");

		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	public void populateFreeText(PrintWriter print_writer,String scene, NameSuper ns, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + ns.getFirstname() + ";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populateline2FreeText(PrintWriter print_writer,String scene, String data1, String data2, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + data1 + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + data2 + ";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 260.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public void populateImgline2FreeText(PrintWriter print_writer,String scene, String data1, String data2,String data3, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			
			if(data1.toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					data1.toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + data2 + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + data3 + ";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 260.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public void populateReview(PrintWriter print_writer, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			String text_to_return = "";
			int lineIndex1 = 1;
		    boolean found1 = false;
			BufferedReader br = new BufferedReader(new FileReader(CricketUtil.CRICKET_DIRECTORY + "ICC_Reviews.txt"));
		
		    while( (text_to_return = br.readLine()) != null) {
		        if(lineIndex1 == 1) {
		        	print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews1 " + text_to_return.split(" ")[0] + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews2 " + text_to_return.split(" ")[1] + ";");
					
		            found1 = true;
		            break;
		        }
		        lineIndex1++;
		    }
		    if(!found1) {
		    	//System.out.println("Line Not There");
		    }
		    if(match.getMatchDetails().getHomeTeam().getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						match.getMatchDetails().getHomeTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}if(match.getMatchDetails().getAwayTeam().getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
						match.getMatchDetails().getAwayTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + 
					match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " + 
					match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");

//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews1 " + data1 + ";");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tReviews2 " + data2 + ";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public void populateEquationShort(PrintWriter print_writer,boolean is_this_updating, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			int total_balls=0,remaiming_balls=0;
			if(match.getMatchDetails().getTarget() != null) {
				
				if(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().contains(".")) {
					total_balls = (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[0])*6) + 
							(Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[1]));
				}else {
					total_balls = (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6);
				}
				
				remaiming_balls = (total_balls - ((Integer.valueOf(match.getCurrentPosition().getCurrentOversBowled())*6) + 
						Integer.valueOf(match.getCurrentPosition().getCurrentOddBallsBowled())));
			}
			
			for(AE_Inning inn : match.getInning()) {
				if(inn.getNumber() == 2 || inn.getNumber() == 4) {
					if(is_this_updating == false) {
						if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().toUpperCase().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getStatus().getCurrentBattingTeamShortName() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + ";");

//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + remaiming_balls + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "@ " + match.getMatchDetails().getTarget().getRequiredRunRate() + " RPO;");

				}
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + "EQUATION" + ";");

			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			
			break;
		}
	}
	public void populateTarget(PrintWriter print_writer, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			for(AE_Inning inn : match.getInning()) {
				if(inn.getNumber() == 1) {
					int runs = 0;
					runs = inn.getRuns() + 1;
					if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					
					if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
						if(match.getMatchDetails().getAwayTeam().getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getMatchDetails().getAwayTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getShortName() + ";");
					}else {
						if(match.getMatchDetails().getHomeTeam().getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getMatchDetails().getHomeTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getShortName() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + runs + ";");
					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6) + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo  REQUIRED RUN RATE : " +  new DecimalFormat("#.00").format(Double.valueOf((runs/Double.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers()))))+ ";");

				}else if(inn.getNumber() == 2) {
					if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getStatus().getCurrentBattingTeamShortName() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + match.getMatchDetails().getTarget().getTarget() + ";");
					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6) + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo  REQUIRED RUN RATE : " + match.getMatchDetails().getTarget().getOriginalRunRate()+ ";");

				}
			}

			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populateTargetFull(PrintWriter print_writer, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			for(AE_Inning inn : match.getInning()) {
				if(inn.getNumber() == 1 || inn.getNumber() == 3) {
					int runs = 0;
					runs = inn.getRuns() + 1;
					if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
						if(match.getMatchDetails().getAwayTeam().getShortName().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getMatchDetails().getAwayTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
					}else {
						if(match.getMatchDetails().getHomeTeam().getShortName().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getMatchDetails().getHomeTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + runs + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())) + " OVERS" + ";");
				}else if(inn.getNumber() == 2 || inn.getNumber() == 4) {
					if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					if(match.getMatchDetails().getStatus().getCurrentBattingTeamShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + match.getMatchDetails().getTarget().getTarget() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
					
					if((Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())) > 1) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
								(Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())) + " OVERS" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + 
								(Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers()))*6 + " BALLS" + ";");
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populateComparison(PrintWriter print_writer,boolean is_this_updating ,AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			if(match.getMatchDetails().getComparison().getTeam1Name().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						match.getMatchDetails().getComparison().getTeam1Name().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}
			if(match.getMatchDetails().getComparison().getTeam2Name().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag2 " + logo_path +
						match.getMatchDetails().getComparison().getTeam2Name().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}
			
			
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "AFTER " + match.getMatchDetails().getComparison().getCompOvers() + " OVERS" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName1 " + match.getMatchDetails().getComparison().getTeam1Name().toUpperCase() + " WERE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName2 " + match.getMatchDetails().getComparison().getTeam2Name().toUpperCase() + " ARE" + ";");
			
			if(match.getMatchDetails().getComparison().getTeam1Score().split("-")[1].equalsIgnoreCase("10")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore1 " + match.getMatchDetails().getComparison().getTeam1Score().split("-")[0] + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore1 " + match.getMatchDetails().getComparison().getTeam1Score() + ";");
			}
			
			if(match.getMatchDetails().getComparison().getTeam2Score().split("-")[1].equalsIgnoreCase("10")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore2 " + match.getMatchDetails().getComparison().getTeam2Score().split("-")[0] + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore2 " + match.getMatchDetails().getComparison().getTeam2Score() + ";");
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			
			break;
		}
	}
	
	public void populateToss(PrintWriter print_writer, AE_Cricket match,MatchAllData session_match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			
			if(match.getMatchDetails().getToss().getWinnerID().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			if(session_match.getSetup().getTossWinningTeam() == session_match.getSetup().getHomeTeamId()) {

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						session_match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				if(session_match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							session_match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else if(session_match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							session_match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								session_match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");

				}
				

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "WON THE TOSS AND" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "ELECTED TO " + session_match.getSetup().getTossWinningDecision().toUpperCase() + ";");
				

			}else {
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						session_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				if(session_match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							session_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else if(session_match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							session_match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
								session_match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");

				}
				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
//						session_match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");

				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "WON THE TOSS AND" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "ELECTED TO " + session_match.getSetup().getTossWinningDecision().toUpperCase() + ";");
				
				
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populateEquation(PrintWriter print_writer,boolean is_this_updating, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			int total_balls=0,remaiming_balls=0;
			if(match.getMatchDetails().getTarget() != null) {
				
				if(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().contains(".")) {
					total_balls = (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[0])*6) + 
							(Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[1]));
				}else {
					total_balls = (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6);
				}
				
				remaiming_balls = (total_balls - ((Integer.valueOf(match.getCurrentPosition().getCurrentOversBowled())*6) + 
						Integer.valueOf(match.getCurrentPosition().getCurrentOddBallsBowled())));
				
				
				for(AE_Inning inn : match.getInning()) {
					if(inn.getNumber().intValue() == 2) {
						if(is_this_updating == false) {
							if(match.getInning().get(1).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(1).getShortName() + CricketUtil.PNG_EXTENSION + ";");
						}
						if((match.getMatchDetails().getTarget().getTarget()-1) == inn.getRuns().intValue()) {
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getShortName() + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "" + ";");
//							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUNS TO WIN" + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + match.getMatchDetails().getScheduledOvers().getReducedOvers() + " OVERS" + ";");
							
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTopFreeText " + "SCORES ARE LEVEL" + ";");
							if(match.getInning().get(1).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "NEED" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " TO WIN" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							
						}
						else if(match.getMatchDetails().getTarget().getTarget().intValue() <= inn.getRuns().intValue()) {
							if(match.getInning().get(1).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(1).getShortName() + CricketUtil.PNG_EXTENSION + ";");
							
							if(match.getInning().get(1).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "WIN BY" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + (10 - match.getInning().get(1).getNoOfWickets()) + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "WICKETS" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "" + ";");
							
						}
						else if(inn.getNoOfWickets().intValue() >= 10 || remaiming_balls <= 0) {
							if(match.getInning().get(0).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(0).getShortName() + CricketUtil.PNG_EXTENSION + ";");
							
							if(match.getInning().get(0).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(0).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "WIN BY" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + (match.getMatchDetails().getTarget().getRunsRequired() - 1) + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural((match.getMatchDetails().getTarget().getRunsRequired() - 1)).toUpperCase() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "" + ";");
						}
						else {
							
							if(match.getInning().get(1).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "NEED" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " TO WIN" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							
						}
					}else if(inn.getNumber().intValue() == 4) {
						if(is_this_updating == false) {
							if(match.getInning().get(3).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(3).getShortName() + CricketUtil.PNG_EXTENSION + ";");
						}
						if((match.getMatchDetails().getTarget().getTarget()-1) == inn.getRuns().intValue()) {
							if(match.getInning().get(3).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "NEED" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " TO WIN" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							
						}
						else if(match.getMatchDetails().getTarget().getTarget().intValue() <= inn.getRuns().intValue()) {
							if(match.getInning().get(3).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(3).getShortName() + CricketUtil.PNG_EXTENSION + ";");
							
							if(match.getInning().get(3).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "WON THE" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "SUPER" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "OVER" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "" + ";");
							
						}
						else if(inn.getNoOfWickets().intValue() >= 10 || remaiming_balls <= 0) {
							if(match.getInning().get(2).getShortName().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									match.getInning().get(2).getShortName() + CricketUtil.PNG_EXTENSION + ";");
							
							if(match.getInning().get(2).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(0).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "WON THE" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "SUPER" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "OVER" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "" + ";");
						}
						else {
							
							if(match.getInning().get(3).getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
							}
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedText " + "NEED" + ";");
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + match.getMatchDetails().getTarget().getRunsRequired() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText1 " + "RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " TO WIN" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText2 " + "FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							
						}
					}
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			break;
		}
	}
	
	public void populateRunRate(PrintWriter print_writer, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "CURRENT RUN RATE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + match.getMatchDetails().getTarget().getCurrentRunRate() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "REQUIRED RUN RATE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + match.getMatchDetails().getTarget().getRequiredRunRate() + ";");

			
			int total_balls=0,remaiming_balls=0;
			if(match.getMatchDetails().getTarget() != null) {
				
				if(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().contains(".")) {
					total_balls = (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[0])*6) + 
							(Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[1]));
				}else {
					total_balls = (Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6);
				}
				
				remaiming_balls = (total_balls - ((Integer.valueOf(match.getCurrentPosition().getCurrentOversBowled())*6) + 
						Integer.valueOf(match.getCurrentPosition().getCurrentOddBallsBowled())));
				
				for(AE_Inning inn : match.getInning()) {
					if(inn.getNumber().intValue() == 2 && match.getMatchDetails().getStatus().getCurrentInnings() == 2) {
						
						if(match.getInning().get(1).getShortName().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getInning().get(1).getShortName() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(1).getShortName() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + match.getInning().get(1).getRuns() + "-" + match.getInning().get(1).getNoOfWickets() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOverIS " + match.getInning().get(1).getOvers() + ";");

						
						
						if((match.getMatchDetails().getTarget().getTarget()-1) == inn.getRuns().intValue()) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "SCORES ARE LEVEL" + ";");
						}
						else if(match.getMatchDetails().getTarget().getTarget().intValue() <= inn.getRuns().intValue()) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + match.getInning().get(1).getShortName().toUpperCase() + 
									" WIN BY " + (10 - match.getInning().get(1).getNoOfWickets()) + " WICKETS" + ";");
						}
						else if(inn.getNoOfWickets().intValue() >= 10 || remaiming_balls <= 0) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + match.getInning().get(0).getShortName().toUpperCase() + 
									" WIN BY " + (match.getMatchDetails().getTarget().getRunsRequired() - 1) + " RUN" + CricketFunctions.Plural((match.getMatchDetails().getTarget().getRunsRequired() - 1)).toUpperCase() + ";");
						}
						else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "NEED " + 
								match.getMatchDetails().getTarget().getRunsRequired() + " RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
						}
					}else if(inn.getNumber().intValue() == 4 && match.getMatchDetails().getStatus().getCurrentInnings() == 4) {
						
						if(match.getInning().get(3).getShortName().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
								match.getInning().get(3).getShortName() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getInning().get(3).getShortName().toUpperCase() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + match.getInning().get(3).getRuns() + "-" + match.getInning().get(3).getNoOfWickets() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOverIS " + match.getInning().get(3).getOvers() + ";");

						
						if((match.getMatchDetails().getTarget().getTarget()-1) == inn.getRuns().intValue()) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "SCORES ARE LEVEL" + ";");
						}
						else if(match.getMatchDetails().getTarget().getTarget().intValue() <= inn.getRuns().intValue()) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + match.getInning().get(3).getShortName().toUpperCase() + 
									" WON THE SUPER OVER" + ";");
						}
						else if(inn.getNoOfWickets().intValue() >= 10 || remaiming_balls <= 0) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + match.getInning().get(2).getShortName().toUpperCase() + 
									" WON THE SUPER OVER" + ";");
						}
						else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "NEED " + 
								match.getMatchDetails().getTarget().getRunsRequired() + " RUN" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + " FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
						}
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public void populateExtras(PrintWriter print_writer, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			for(AE_Inning inn : match.getInning()) {
				if (inn.getNumber() == match.getInning().size()) {
					if(inn.getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							inn.getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
					//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vExtraOptions " + "0" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "BYES" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + inn.getByes() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "LEG BYES" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + inn.getLegByes() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "WIDES" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + inn.getWides() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "NO BALLS" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + inn.getNoBalls() + ";");
					
					if(inn.getPenalties() != 0) {
						int total = 0;
						total = inn.getByes() + inn.getLegByes() + inn.getWides() + inn.getNoBalls() + inn.getPenalties();
						
						//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vExtraOptions " + "1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$StatAll$StatGrp5*CONTAINER SET ACTIVE 1 ;");

						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead5 " + "PENALTIES" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue5 " + inn.getPenalties() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras " + total + ";");

					}else {
						int total = 0;
						total = inn.getByes() + inn.getLegByes() + inn.getWides() + inn.getNoBalls();
						//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras " + total + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$StatAll$StatGrp5*CONTAINER SET ACTIVE 0 ;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras " + total + ";");

					}
					
					
				}
			}
			
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populateBallDistance(PrintWriter print_writer,AE_Last_Ball third_party_last_ball_speed, String session_selected_broadcaster) throws InterruptedException, IOException, JAXBException 
	{
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreDIST " + CricketFunctions.getDistance_of_ball_from_ThirdParty(CricketUtil.CRICKET_DIRECTORY + CricketUtil.DISTANCE_DIRECTORY +
				"LastSixDistance.xml").getDistance().get(0).getValues() + ";");
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

	
	}
	
	public void populateSixDistance(PrintWriter print_writer,String scene, String data, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreDIST " + data + ";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	private void populateFallOfWickets(PrintWriter print_writer, String string, int team_id, int player_id, List<Player> allPlayer,List<Team> allTeams,
			MatchAllData match, String session_selected_broadcaster2,AE_Cricket third_party_match) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {			
			
			String Fielder_Name="",Bowler_Name="";
			this.status = "STILL";
			for(int i=0;i<=third_party_match.getInning().size()-1;i++) {
				if(third_party_match.getInning().get(i).getShortName().equalsIgnoreCase(allTeams.get(team_id-1).getTeamName4())) {
					if(allTeams.get(team_id-1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						allTeams.get(team_id-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					for(AE_Batsman bc : third_party_match.getInning().get(i).getBatsman()) {
						if(bc.getId().intValue() == allPlayer.get(player_id-1).getAe_Id().intValue()) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
									allTeams.get(team_id-1).getTeamName4() + "\\" + "Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							
							if(allPlayer.get(player_id-1).getFirstname() != null) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getFirstname() + ";");
								
								if(allPlayer.get(player_id-1).getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + allPlayer.get(player_id-1).getSurname() + ";");
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}else {
								if(allPlayer.get(player_id-1).getSurname() != null) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getSurname() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
									
								}else {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + allPlayer.get(player_id-1).getTicker_name() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
								}
							}
							
							
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +allPlayer.get(player_id-1).getFirstname() + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + allPlayer.get(player_id-1).getSurname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreFOW " + bc.getRuns() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsFOW " + bc.getBalls() + ";");
							
							for(Player plyr : allPlayer) {
								if(bc.getFielderID()!=null && bc.getFielderID().intValue() == plyr.getAe_Id()) {
									Fielder_Name = plyr.getTicker_name();
								}
								
								if(bc.getBowlerID()!= null && bc.getBowlerID().intValue() == plyr.getAe_Id()) {
									Bowler_Name = plyr.getTicker_name();
								}
							}
							
							if(bc.getHowOut() != null) {
								switch(bc.getHowOut()) {
									case "caught":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"c " +" "+ Fielder_Name+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + "b " + Bowler_Name+ ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"bowled":
										print_writer.print("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +" "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"b " +  Bowler_Name+ ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"caughtAndBowled":
										print_writer.print("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +" "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"c&b " +  Bowler_Name+ ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"legBefore":
										print_writer.print("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"   "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +" lbw  " +"  b " + Bowler_Name+ ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"stumped":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"st " +" "+ Fielder_Name+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + "b " + Bowler_Name+ ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"runOut":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"   "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"run out" +" "+ Fielder_Name+ ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"hitWicket":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"hit Wicket " +" "+ Fielder_Name+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + "b " + Bowler_Name+ ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"handledTheBall":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"b " + Bowler_Name+ ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"hitBallTwice":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"hit Ball Twice" + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"obstructedTheField":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + " obstructed the field " + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"timedOut":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"Timed Out" + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"retiredHurt":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"retired hurt" + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"mankad":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"mankad" + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"retiredSubstituted":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"retiredSubstituted" + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;	
									case"absentHurt":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"absent hurt" + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"concussed":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"concussed" + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"retired":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"retired" + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;
									case"caughtSub":
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + bc.getFullDismissalDescription().split(" b ")[0] + ";");
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + "b " + Bowler_Name + ";");
										this.status = CricketUtil.SUCCESSFUL;
										break;	
										
								}
								
								if(bc.getHowOut().equalsIgnoreCase("notOut")) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
							}
							
							
							//}
						}
						
					}
					TimeUnit.MILLISECONDS.sleep(500);
					if(third_party_match.getInning().get(i).getFallOfWicket() != null) {
						for(AE_FallOfWicket fow : third_party_match.getInning().get(i).getFallOfWicket()) {
							if(fow.getID().intValue() == allPlayer.get(player_id-1).getAe_Id().intValue()) {	
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFow " + 
										" FALL OF WICKET " + fow.getScore()+"-"+ fow.getWicket()+ ";");
								
							}
						}
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			if(this.status.equalsIgnoreCase("STILL") || this.status.equalsIgnoreCase(CricketUtil.UNSUCCESSFUL)) {
				print_writer.println("LAYER" + (current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				current_layer = 5-current_layer;
				
			}

		}
	}

	public void populateTeamName(PrintWriter print_writer,String name, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			if(name.equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())){
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getLongName().toUpperCase() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getLongName().toUpperCase() + ";");
			}
			if(name.toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
					name.toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populatePlayerName(PrintWriter print_writer,int teamID, AE_Cricket third_party,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			int row_id = 1,row = 0;
			count = 1;
			if(teamID == match.getSetup().getHomeTeamId()){
				if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
							match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
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
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\Right_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					
					if(hs.getFirstname() != null) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + row + " " + hs.getFirstname() + ";");
						
						if(hs.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + row + " " + hs.getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + row + " " + " " + ";");
						}
					}else {
						if(hs.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + row + " " + hs.getSurname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + " " + ";");
							
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + row + " " + hs.getTicker_name() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + row + "  " + ";");
						}
					}
					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + row + " " + 
//							hs.getFirstname() + ";");
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + row + " " + 
//							hs.getSurname() + ";");
//					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
//									hs.getFull_name() + " (C)" + ";");
//					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
//								hs.getFull_name() + " (C & WK)" + ";");
//					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
//								hs.getFull_name() + " (WK)" + ";");
//					}else {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
//								hs.getFull_name() + ";");
//					}
//					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole" + row + " " + hs.getRole().toUpperCase() + ";");
	
				}
			}else {
				
				if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
							match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
				}
				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
//						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
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
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\Right_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					
					if(as.getFirstname() != null) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + row + " " + as.getFirstname() + ";");
						
						if(as.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + row + " " + as.getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + row + "  " + ";");
						}
					}else {
						if(as.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + row + " " + as.getSurname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + row + " " + " " + ";");
							
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + row + " " + as.getTicker_name() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + row + "  " + ";");
						}
					}
					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName" + row + " " + 
//							as.getFirstname() + ";");
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName" + row + " " + 
//							as.getSurname() + ";");
					
//					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
//								as.getFull_name() + " (C)" + ";");
//					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
//								as.getFull_name() + " (C & WK)" + ";");
//					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
//								as.getFull_name() + " (WK)" + ";");
//					}else {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
//								as.getFull_name() + ";");
//					}
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole" + row + " " + as.getRole().toUpperCase() + ";");

				}
			}
//			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
//			TimeUnit.SECONDS.sleep(1);
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populatePlayerVideo(PrintWriter print_writer,int teamID, AE_Cricket third_party,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			video_count = 0;
			if(teamID == match.getSetup().getHomeTeamId()){
				team = "Home";
				print_writer.println("LAYER" + video_layer + "*EVEREST*TREEVIEW*Main$Rectangle*FUNCTION*IMAGESEQUENCE2 SET PREFIX " + 
						"D:/EverestCricket/EVEREST_ICC_WorldCup_2023/Videos/Teams/" + match.getSetup().getHomeTeam().getTeamName4() + "/" + 
						match.getSetup().getHomeSquad().get(video_count).getPhoto()  + "/" + ";");
			}else {
				team = "Away";
				print_writer.println("LAYER" + video_layer + "*EVEREST*TREEVIEW*Main$Rectangle*FUNCTION*IMAGESEQUENCE2 SET PREFIX " + 
						"D:/EverestCricket/EVEREST_ICC_WorldCup_2023/Videos/Teams/" + match.getSetup().getAwayTeam().getTeamName4() + "/" + 
						match.getSetup().getAwaySquad().get(video_count).getPhoto()  + "/" + ";");
			}
//			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
//			TimeUnit.SECONDS.sleep(1);
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populateLineup(PrintWriter print_writer,int teamID, AE_Cricket third_party,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
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
							match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
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
								hs.getFull_name() + ";");
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
							match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
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
								as.getFull_name() + ";");
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
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	
	public void populateLineupImage(PrintWriter print_writer,int teamID, AE_Cricket third_party,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
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
							match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
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
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + "Left_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
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
							match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
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
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
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
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public void populateProjected(PrintWriter print_writer,boolean is_this_updating ,AE_Cricket third_party, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead PROJECTED SCORES;");
		
		for(AE_Inning inn : third_party.getInning()) {
			if (third_party.getInning().size() == inn.getNumber()) {
				if(inn.getNumber() == 1) {
					if(inn.getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					System.out.println(inn.getShortName().toUpperCase() );
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
							inn.getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + 
							"@" + inn.getRunRate() + " (CRR)" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
							third_party.getMatchDetails().getProjection().getProjectionAtCurrent() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + 
							"@6 RPO" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + 
							third_party.getMatchDetails().getProjection().getProjectionAt6PerOver() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + 
							"@7 RPO" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + 
							third_party.getMatchDetails().getProjection().getProjectionAt7PerOver() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + 
							"@8 RPO" + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + 
							third_party.getMatchDetails().getProjection().getProjectionAt8PerOver() + ";");
				}
			}
		}
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	
	public void populateFreeHit(PrintWriter print_writer,boolean is_this_updating ,AE_Cricket third_party, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		if(is_this_updating == false) {
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 230.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	public void populateQuickHowOut(PrintWriter print_writer, AE_Cricket third_party, MatchAllData match,List<Player> allPlayer,
			String session_selected_broadcaster) throws InterruptedException, IOException {
		
		String Fielder_Name="",Bowler_Name="";
		
		for(AE_Inning inn : third_party.getInning()) {
			if (third_party.getInning().size() == inn.getNumber()) {
				if(inn.getFallOfWicket() != null && inn.getFallOfWicket().size() > 0) {
					for(AE_Batsman bc : inn.getBatsman()) {
						if(bc.getId().intValue() == inn.getFallOfWicket().get(inn.getFallOfWicket().size() - 1).getID().intValue()) {
							if(inn.getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
							}
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
									inn.getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
							for(Player dbplayers : allPlayer) {
								if(bc.getId().intValue() == dbplayers.getAe_Id().intValue()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
											inn.getShortName().toUpperCase() + "\\Right_2048\\" + dbplayers.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
									if(dbplayers.getFirstname() != null) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + dbplayers.getFirstname() + ";");
										
										if(dbplayers.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + dbplayers.getSurname() + ";");
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
										}
									}else {
										if(dbplayers.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + dbplayers.getSurname() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
											
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + dbplayers.getTicker_name() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
										}
									}
									
//									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + dbplayers.getFirstname() + ";");
//									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + dbplayers.getSurname() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreFOW " + bc.getRuns() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsFOW " + bc.getBalls() + ";");

									for(Player plyr : allPlayer) {
										if(bc.getFielderID()!=null && bc.getFielderID().intValue() == plyr.getAe_Id()) {
											Fielder_Name = plyr.getTicker_name();
										}
										
										if(bc.getBowlerID()!= null && bc.getBowlerID().intValue() == plyr.getAe_Id()) {
											Bowler_Name = plyr.getTicker_name();
										}
									}
									
									if(bc.getHowOut() != null) {
										switch(bc.getHowOut()) {
											case "caught":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"c " +" "+ Fielder_Name+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + "b " + Bowler_Name+ ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"bowled":
												print_writer.print("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +" "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"b " +  Bowler_Name+ ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"caughtAndBowled":
												print_writer.print("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +" "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"c&b " +  Bowler_Name+ ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"legBefore":
												print_writer.print("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"   "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +" lbw  " +"  b " + Bowler_Name+ ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"stumped":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"st " +" "+ Fielder_Name+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + "b " + Bowler_Name+ ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"runOut":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"   "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"run out" +" "+ Fielder_Name+ ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"hitWicket":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"hit Wicket " +" "+ Fielder_Name+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + "b " + Bowler_Name+ ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"handledTheBall":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"b " + Bowler_Name+ ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"hitBallTwice":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"hit Ball Twice" + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"obstructedTheField":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + " obstructed the field " + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"timedOut":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"Timed Out" + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"retiredHurt":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"retired hurt" + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"mankad":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"mankad" + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"retiredSubstituted":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"retiredSubstituted" + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;	
											case"absentHurt":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"absent hurt" + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"concussed":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"concussed" + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"retired":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " +"  "+ ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +"retired" + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;
											case"caughtSub":
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFielderName " + bc.getFullDismissalDescription().split(" b ")[0] + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + "b " + Bowler_Name + ";");
												this.status = CricketUtil.SUCCESSFUL;
												break;	
												
										}
									}
								
									if(inn.getFallOfWicket() != null) {
										for(AE_FallOfWicket fow : inn.getFallOfWicket()) {
											if(fow.getID().intValue() == dbplayers.getAe_Id().intValue()) {	
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFow " + 
														" FALL OF WICKET " + fow.getScore()+"-"+ fow.getWicket()+ ";");
												
											}
										}
									}
								}
							}	
						}
					}
				}
			}
		}
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateImagefourThree(PrintWriter print_writer, String teamName, AE_Cricket third_party, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		switch (teamName.toUpperCase()) {
		case "ICC1":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "ICC SHOP  Static 1.1" + ".jpg" + ";");
			break;
		case "ICC2":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "ICC SHOP  Static 1.2 copy" + ".jpg" + ";");
			break;
		case "ICC3":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "ICC SHOP Static 1.2" + ".jpg" + ";");
			break;
		case "ROYAL_STAG":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Royal Stag" + ".jpg" + ";");
			break;
		case "JACOB":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Jacobs Greek Static - 1920x1080 px" + ".jpg" + ";");
			break;
		case "THUMSUP":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Thums-Up-Logo-(1)" + ".png" + ";");
			break;	
		case "ARAMCO_BLUE":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Aramco Blue 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "ARAMCO_GREEN":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Aramco Green 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "BOOKING":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Booking.com 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "DP_WORLD":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "DP World 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "INDUSLAND":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Induslnd Bank 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "MASTER_CARD":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "MasterCard 1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "MRF_ZAPPER":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "MRF Zapper C1 Replay Screen Static  1536 X 1152" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "MRF_ZLF":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "MRF ZLX Replay Screen 1536 X 1152 Static" + ".jpg" + ";");
			break;
		case "NIUM":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "NIUM  1536x1152_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "UPSTOCK":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Replay Screen_1536X1152" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "POLYCAB":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "REPLAY-SCREEN-1536-x-1152-px" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "NISSAN":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Nissan 4x3" + ".png" + ";");
			break;
		case "EMIRATES":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"4_3" + "\\" + "Emirates Fly Better 4x3" + ".png" + ";");
			break;	
		}
		
		
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	public void populateImagesixteenNine(PrintWriter print_writer, String teamName, AE_Cricket third_party, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		switch (teamName.toUpperCase()) {
		case "NISSAN":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Nissan" + ".png" + ";");
			break;
		case "EMIRATES":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Emirates Fly Better" + ".png" + ";");
			break;
		case "BIRA":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "BIRA" + ".png" + ";");
			break;	
		case "ICC1":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "ICC SHOP  Static 1.1" + ".jpg" + ";");
			break;
		case "ICC2":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "ICC SHOP  Static 1.2 copy" + ".jpg" + ";");
			break;
		case "ICC3":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "ICC SHOP Static 1.2" + ".jpg" + ";");
			break;
		case "ROYAL_STAG":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Royal Stag" + ".jpg" + ";");
			break;
		case "JACOB":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Jacobs Greek Static - 1920x1080 px" + ".jpg" + ";");
			break;
		case "THUMSUP":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Thums-Up-Logo-(1)" + ".png" + ";");
			break;	
		case "ARAMCO_BLUE":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Aramco Blue 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "ARAMCO_GREEN":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Aramco Green 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "BOOKING":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Booking.com 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "DP_WORLD":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "DP World 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "INDUSLAND":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Induslnd Bank 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "MASTER_CARD":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "MasterCard 1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "MRF_ZAPPER":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "MRF Zapper C1 Replay Screen Static  1920 X 1080" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "MRF_ZLF":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "MRF ZLX Replay Screen 1920 X 1080 Static" + ".jpg" + ";");
			break;
		case "NIUM":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "NIUM  1920x1080_LED" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "UPSTOCK":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "Replay Screen_1920x1080" + CricketUtil.PNG_EXTENSION + ";");
			break;
		case "POLYCAB":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + "REPLAY-SCREEN-1920-x-1080-px" + CricketUtil.PNG_EXTENSION + ";");
			break;	
		}
		
		
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}
	
	public void populateImageAuto(PrintWriter print_writer) throws InterruptedException, IOException {
		
		loop_value = 0;
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
				"16_9" + "\\" + "MRF ZLX Replay Screen 1920 X 1080 Static" + ".jpg" + ";");
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
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
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + sponsor_path + 
					"16_9" + "\\" + ns.getImagename() + ";");
				
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
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
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgImage " + fantasy_path + 
					 ns.getImagename() + ";");
				
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateLineupLong(PrintWriter print_writer, String teamName, AE_Cricket third_party, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		switch(session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			int row = 0;
			boolean player_found = false;
			if(teamName.toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					teamName.toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomText  BATTING CARD;");
			if(teamName.equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName4())) {
				
				if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
				}
				
				for(int i=0; i<=third_party.getInning().size()-1; i++) {
					if(teamName.equalsIgnoreCase(third_party.getInning().get(i).getShortName())) {
						if(third_party.getInning().get(i).getNoOfWickets() >= 10) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamScore " + 
									third_party.getInning().get(i).getRuns() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamScore " + 
									third_party.getInning().get(i).getRuns() + "-" + third_party.getInning().get(i).getNoOfWickets() + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamOver " + 
								third_party.getInning().get(i).getOvers() + ";");
						for(AE_Batsman bat : third_party.getInning().get(i).getBatsman()) {
//							System.out.println("ID = " + i + "  = " + bat.getId().intValue());
							player_found = false;
							for(Player hs : match.getSetup().getHomeSquad()) {
								if(hs.getAe_Id().intValue()==bat.getId().intValue()) {
									player_found = true;
									row++;
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
											match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
									if(hs.getFirstname() != null) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
												hs.getFirstname() + ";");
										if(hs.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
													hs.getSurname() + ";");
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
													" " + ";");
										}
									}else {
										if(hs.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
													hs.getSurname() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
													" " + ";");
											
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
													hs.getTicker_name() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
													" " + ";");
										}
									}
									
									
//									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
//											hs.getFirstname() + ";");
//									if(hs.getSurname()!=null) {
//										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
//												hs.getSurname() + ";");
//									}else {
//										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
//												' ' + ";");
//									}
									
									if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
												"1" + ";");
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
												"0" + ";");
									}
									
									if(bat.getHowOut()!=null) {
										if(StringUtils.equalsIgnoreCase(bat.getHowOut(),"notOut")) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
													"0" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
													"1" + ";");
											
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
													bat.getRuns() + "*" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
													bat.getBalls() + ";");
											break;
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
													"1" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
													"1" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
													bat.getRuns() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
													bat.getBalls() + ";");
											break;
										}
									}else {
										if(bat.getHowOut()== null && third_party.getInning().get(i).getStatus().equalsIgnoreCase("ended")) {
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
											break;
										}
										
									}
									
								}
							}
							
							if(player_found == false) {
								for(Player hs : match.getSetup().getHomeOtherSquad()) {
									if(hs.getAe_Id().intValue()==bat.getId().intValue()) {
										row++;
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
												match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
										
										if(hs.getFirstname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
													hs.getFirstname() + ";");
											if(hs.getSurname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														hs.getSurname() + ";");
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
											}
										}else {
											if(hs.getSurname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														hs.getSurname() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
												
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														hs.getTicker_name() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
											}
										}
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
												"0" + ";");
										if(hs.getCaptainWicketKeeper()!= null) {
											if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
														"1" + ";");
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
														"0" + ";");
											}
										}
										if(bat.getHowOut()!=null) {
											if(StringUtils.equalsIgnoreCase(bat.getHowOut(),"notOut")) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
														"0" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
														"1" + ";");
												
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
														bat.getRuns() + "*" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
														bat.getBalls() + ";");
												break;
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
														"1" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
														"1" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
														bat.getRuns() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
														bat.getBalls() + ";");
												break;
											}
										}else {
											
											if(third_party.getInning().get(i).getStatus().equalsIgnoreCase("ended")) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
														"0" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
														"1" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " DNB ;");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " ;");
											}
											else {
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
											break;
										}
									}
									}
								}
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
							match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
				}
				
				for(int i=0; i<=third_party.getInning().size()-1; i++) {
					if(teamName.equalsIgnoreCase(third_party.getInning().get(i).getShortName())) {
						if(third_party.getInning().get(i).getNoOfWickets() >= 10) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamScore " + 
									third_party.getInning().get(i).getRuns() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamScore " + 
									third_party.getInning().get(i).getRuns() + "-" + third_party.getInning().get(i).getNoOfWickets() + ";");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamOver " + 
								third_party.getInning().get(i).getOvers() + ";");
						for(AE_Batsman bat : third_party.getInning().get(i).getBatsman()) {
							player_found = false;
							for(Player as : match.getSetup().getAwaySquad()) {
								if(as.getAe_Id().intValue()==bat.getId().intValue()) {
									row++;
									player_found = true;
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
											match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
									if(as.getFirstname() != null) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
												as.getFirstname() + ";");
										if(as.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
													as.getSurname() + ";");
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
													" " + ";");
										}
									}else {
										if(as.getSurname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
													as.getSurname() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
													" " + ";");
											
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
													as.getTicker_name() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
													" " + ";");
										}
									}
									
									if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
												"1" + ";");
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
												"0" + ";");
									}
									
									if(bat.getHowOut()!=null) {
										if(StringUtils.equalsIgnoreCase(bat.getHowOut(),"notOut")) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
													"0" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
													"1" + ";");
											
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
													bat.getRuns() + "*" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
													bat.getBalls() + ";");
											break;
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
													"1" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
													"1" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
													bat.getRuns() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
													bat.getBalls() + ";");
											break;
										}
									}else {
										if(third_party.getInning().get(i).getStatus().equalsIgnoreCase("ended")) {
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
										}
									
										break;
									}
									
								}
							}
							
							if(player_found == false) {
								for(Player as : match.getSetup().getAwayOtherSquad()) {
									if(as.getAe_Id().intValue()==bat.getId().intValue()) {
										row++;
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
												match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
										
										if(as.getFirstname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
													as.getFirstname() + ";");
											if(as.getSurname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														as.getSurname() + ";");
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
											}
										}else {
											if(as.getSurname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														as.getSurname() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
												
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														as.getTicker_name() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
											}
										}
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
												"0" + ";");
										if(as.getCaptainWicketKeeper() != null) {
											if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
														"1" + ";");
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCaptain" + row + " " + 
														"0" + ";");
											}
										}
										
										if(bat.getHowOut()!=null) {
											if(StringUtils.equalsIgnoreCase(bat.getHowOut(),"notOut")) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
														"0" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
														"1" + ";");
												
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
														bat.getRuns() + "*" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
														bat.getBalls() + ";");
												break;
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
														"1" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
														"1" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
														bat.getRuns() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
														bat.getBalls() + ";");
												break;
											}
										}else {
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
											break;
										}
										
									}
								}
							}
						}
					}
				}
			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			break;
		}
	}
	
	public void populateLongLineup(PrintWriter print_writer, int inning, AE_Cricket third_party, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		switch(session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			int row = 0;
			boolean player_found = false;
			
			this.status = CricketUtil.UNSUCCESSFUL;
			for(int i =1; i<= 11; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tWKT" + i + 
						"  " + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWRun" + i + 
						"  " + ";");
			}
			
			if(third_party.getInning().get(inning - 1).getShortName().equalsIgnoreCase(match.getSetup().getHomeTeam().getTeamName4())) {
				
				if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else if(match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
				}
				
				
				if(third_party.getMatchDetails().getAwayTeam().getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						third_party.getMatchDetails().getAwayTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				for(int i=0; i<=third_party.getInning().size()-1; i++) {
					if(third_party.getInning().get(i).getNumber() == inning) {
						if(third_party.getInning().get(i).getBowler() != null && third_party.getInning().get(i).getBowler().size() >= 5) {
							if(third_party.getInning().get(i).getFallOfWicket() != null) {
								for(AE_FallOfWicket fow : third_party.getInning().get(i).getFallOfWicket()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tWKT" + fow.getWicket() + " " + 
											fow.getWicket() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWRun" + fow.getWicket() + " " + 
											fow.getScore() + ";");
								}
							}
							for(AE_Bowler boc : third_party.getInning().get(i).getBowler()) {
//								System.out.println("ID = " + i + "  = " + bat.getId().intValue());
								player_found = false;
								for(Player as : match.getSetup().getAwaySquad()) {
									if(as.getAe_Id().intValue() == boc.getID().intValue()) {
										player_found = true;
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
												match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
										
										if(as.getFirstname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
													as.getFirstname() + ";");
											if(as.getSurname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														as.getSurname() + ";");
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
											}
										}else {
											if(as.getSurname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														as.getSurname() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
												
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														as.getTicker_name() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
											}
										}
										
										if(boc.getBowlingNow() != null && boc.getBowlingNow().equalsIgnoreCase("This")) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
													"0" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
													"1" + ";");
											
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
													boc.getWickets() + "-" + boc.getRuns() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
													boc.getOvers() + ";");
											
											break;
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
													"1" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
													"1" + ";");
											
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
													boc.getWickets() + "-" + boc.getRuns() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
													boc.getOvers() + ";");
											
											break;
										}
										
									}
								}
								
								if(player_found == false) {
									for(Player as : match.getSetup().getAwayOtherSquad()) {
										if(as.getAe_Id().intValue()==boc.getID().intValue()) {
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
													match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
											
											if(as.getFirstname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														as.getFirstname() + ";");
												if(as.getSurname() != null) {
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
															as.getSurname() + ";");
												}else {
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
															" " + ";");
												}
											}else {
												if(as.getSurname() != null) {
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
															as.getSurname() + ";");
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
															" " + ";");
													
												}else {
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
															as.getTicker_name() + ";");
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
															" " + ";");
												}
											}
											
											if(boc.getBowlingNow() != null && boc.getBowlingNow().equalsIgnoreCase("This")) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
														"0" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
														"1" + ";");
												
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
														boc.getWickets() + "-" + boc.getRuns() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
														boc.getOvers() + ";");
												
												break;
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
														"1" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
														"1" + ";");
												
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
														boc.getWickets() + "-" + boc.getRuns() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
														boc.getOvers() + ";");
												
												break;
											}
										}
									}
								}
							}
							this.status = CricketUtil.SUCCESSFUL;
						}else {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
					}
				}
				
			}else {
				if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + 
							match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
				}
				
				
				if(third_party.getMatchDetails().getHomeTeam().getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						third_party.getMatchDetails().getHomeTeam().getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				for(int i=0; i<=third_party.getInning().size()-1; i++) {
					if(third_party.getInning().get(i).getNumber() == inning) {
						if(third_party.getInning().get(i).getBowler() != null && third_party.getInning().get(i).getBowler().size() >= 5) {
							if(third_party.getInning().get(i).getFallOfWicket() != null) {
								for(AE_FallOfWicket fow : third_party.getInning().get(i).getFallOfWicket()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tWKT" + fow.getWicket() + " " + 
											fow.getWicket() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWRun" + fow.getWicket() + " " + 
											fow.getScore() + ";");
								}
							}
							for(AE_Bowler boc : third_party.getInning().get(i).getBowler()) {
								player_found = false;
								for(Player hs : match.getSetup().getHomeSquad()) {
									if(hs.getAe_Id().intValue()==boc.getID().intValue()) {
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
										
										player_found = true;
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage" + row + " " + photo_path + 
												match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
										
										if(hs.getFirstname() != null) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
													hs.getFirstname() + ";");
											if(hs.getSurname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														hs.getSurname() + ";");
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
											}
										}else {
											if(hs.getSurname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														hs.getSurname() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
												
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														hs.getTicker_name() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
														" " + ";");
											}
										}
										
										if(boc.getBowlingNow() != null && boc.getBowlingNow().equalsIgnoreCase("This")) {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
													"0" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
													"1" + ";");
											
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
													boc.getWickets() + "-" + boc.getRuns() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
													boc.getOvers() + ";");
											
											break;
										}else {
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
													"1" + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
													"1" + ";");
											
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
													boc.getWickets() + "-" + boc.getRuns() + ";");
											print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
													boc.getOvers() + ";");
											
											break;
										}
									}
								}
								
								if(player_found == false) {
									for(Player hs : match.getSetup().getHomeOtherSquad()) {
										if(hs.getAe_Id().intValue()==boc.getID().intValue()) {
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
													match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\Left_2048\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
											
											if(hs.getFirstname() != null) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
														hs.getFirstname() + ";");
												if(hs.getSurname() != null) {
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
															hs.getSurname() + ";");
												}else {
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
															" " + ";");
												}
											}else {
												if(hs.getSurname() != null) {
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
															hs.getSurname() + ";");
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
															" " + ";");
													
												}else {
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName" + row + " " + 
															hs.getTicker_name() + ";");
													print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSecondName" + row + " " + 
															" " + ";");
												}
											}
											
											if(boc.getBowlingNow() != null && boc.getBowlingNow().equalsIgnoreCase("This")) {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
														"0" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
														"1" + ";");
												
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
														boc.getWickets() + "-" + boc.getRuns() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
														boc.getOvers() + ";");
												
												break;
											}else {
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlght_Dehighligh" + row + " " + 
														"1" + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBottomInfo" + row + " " + 
														"1" + ";");
												
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns" + row + " " + 
														boc.getWickets() + "-" + boc.getRuns() + ";");
												print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls" + row + " " + 
														boc.getOvers() + ";");
												
												break;
											}
											
										}
									}
								}
							}
							this.status = CricketUtil.SUCCESSFUL;
						}else {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
					}
				}
			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			if(this.status.equalsIgnoreCase(CricketUtil.UNSUCCESSFUL)) {
				print_writer.println("LAYER" + (current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
				current_layer = 5- current_layer;
			}
			break;
			
		}
	}
	
	public void populateMatchSummary(PrintWriter print_writer,boolean is_this_updating,int inning, AE_Cricket third_party_match,List<Player> allPlayer, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			
			int total_balls=0,remaiming_balls=0;
			if(third_party_match.getMatchDetails().getTarget() != null) {
				
				if(third_party_match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().contains(".")) {
					total_balls = (Integer.valueOf(third_party_match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[0])*6) + 
							(Integer.valueOf(third_party_match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers().split("\\.")[1]));
				}else {
					total_balls = (Integer.valueOf(third_party_match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6);
				}
				
				remaiming_balls = (total_balls - ((Integer.valueOf(third_party_match.getCurrentPosition().getCurrentOversBowled())*6) + 
						Integer.valueOf(third_party_match.getCurrentPosition().getCurrentOddBallsBowled())));
			}
			for(int i=0;i<=third_party_match.getInning().size()-1;i++) {
				if(third_party_match.getInning().get(i).getNumber() == inning) {
					if(third_party_match.getInning().get(i).getTopBowler() != null) {
						if(third_party_match.getInning().get(i).getTopBowler().size() == 2) {
							for(Player dbplayers : allPlayer) {
								if(third_party_match.getInning().get(i).getTopBowler().get(0).getID().intValue() == dbplayers.getAe_Id().intValue()) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerNam1 " + dbplayers.getTicker_name() + ";");
								}
								
								if(third_party_match.getInning().get(i).getTopBowler().get(1).getID().intValue() == dbplayers.getAe_Id().intValue()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerNam2 " + dbplayers.getTicker_name() + ";");
									
								}
							}
							
							for(AE_Bowler boc : third_party_match.getInning().get(i).getBowler()) {
								if(third_party_match.getInning().get(i).getTopBowler().get(0).getID().intValue() == boc.getID().intValue()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigur1 " + boc.getWickets() + "-" + boc.getRuns() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOver1 " + boc.getOvers() + ";");
								}
								
								if(third_party_match.getInning().get(i).getTopBowler().get(1).getID().intValue() == boc.getID().intValue()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigur2 " + boc.getWickets() + "-" + boc.getRuns() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOver2 " + boc.getOvers() + ";");
								}
							}
						}else if(third_party_match.getInning().get(i).getTopBowler().size() == 1) {
							for(Player dbplayers : allPlayer) {
								if(third_party_match.getInning().get(i).getTopBowler().get(0).getID().intValue() == dbplayers.getAe_Id().intValue()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerNam1 " + dbplayers.getTicker_name() + ";");
								}
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerNam2 " + " " + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$BottomLine$BowlerAll$NameBaseBase"
										+ "*CONTAINER SET ACTIVE 0;");
							}
							
							for(AE_Bowler boc : third_party_match.getInning().get(i).getBowler()) {
								if(third_party_match.getInning().get(i).getTopBowler().get(0).getID().intValue() == boc.getID().intValue()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigur1 " + boc.getWickets() + "-" + boc.getRuns() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOver1 " + boc.getOvers() + ";");
								}
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigur2 " + " " + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOver2 " + " " + ";");
							}
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
					
					int player1_id=0, player2_id=0;
					if(third_party_match.getInning().get(i).getTopPlayer() != null) {
						if(third_party_match.getInning().get(i).getTopPlayer().size() == 2) {
							for(Player dbplayers : allPlayer) {
								if(third_party_match.getInning().get(i).getTopPlayer().get(0).getID().intValue() == dbplayers.getAe_Id().intValue()) {
									if(third_party_match.getInning().get(i).getBatsman().stream().filter(bman->bman.getId().intValue()==
											dbplayers.getAe_Id().intValue()).findAny().orElse(null).getHowOut().equalsIgnoreCase("notOut")) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam1 " + dbplayers.getTicker_name() + " ;");
										
										player1_id=dbplayers.getAe_Id().intValue();
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam1 " + dbplayers.getTicker_name() + " ;");
									}
								}
								
								if(third_party_match.getInning().get(i).getTopPlayer().get(1).getID().intValue() == dbplayers.getAe_Id().intValue()) {
									
									if(third_party_match.getInning().get(i).getBatsman().stream().filter(bman->bman.getId().intValue()==
											dbplayers.getAe_Id().intValue()).findAny().orElse(null).getHowOut().equalsIgnoreCase("notOut")) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam2 " + dbplayers.getTicker_name() + " ;");
										
										player2_id=dbplayers.getAe_Id().intValue();
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam2 " + dbplayers.getTicker_name() + " ;");
									}									
								}
							}
							
							for(AE_Batsman bc : third_party_match.getInning().get(i).getBatsman()) {
								if(third_party_match.getInning().get(i).getTopPlayer().get(0).getID().intValue() == bc.getId().intValue()) {
									if(player1_id >0) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor1 " + bc.getRuns() + " *;");
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor1 " + bc.getRuns() + ";");
									}
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBall1 " + bc.getBalls() + ";");
								}
								
								if(third_party_match.getInning().get(i).getTopPlayer().get(1).getID().intValue() == bc.getId().intValue()) {
									if(player2_id >0) {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor2 " + bc.getRuns() + " *;");
									}else {
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor2 " + bc.getRuns() + ";");
									}
									//print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor2 " + bc.getRuns() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBall2 " + bc.getBalls() + ";");
								}
							}
						}else if(third_party_match.getInning().get(i).getTopPlayer().size() == 1) {
							for(Player dbplayers : allPlayer) {
								if(third_party_match.getInning().get(i).getTopPlayer().get(0).getID().intValue() == dbplayers.getAe_Id().intValue()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam1 " + dbplayers.getTicker_name() + ";");
								}
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterNam2 " + " " + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$DataAll$BottomLine$BatterAll$NameBaseBase"
										+ "*CONTAINER SET ACTIVE 0;");
							}
							
							for(AE_Batsman bc : third_party_match.getInning().get(i).getBatsman()) {
								if(third_party_match.getInning().get(i).getTopBowler().get(0).getID().intValue() == bc.getId().intValue()) {
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor1 " + bc.getRuns() + ";");
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBall1 " + bc.getBalls() + ";");
								}
								
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterScor2 " + " " + ";");
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatterBall2 " + " " + ";");
							}
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
					if(third_party_match.getInning().get(i).getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
							third_party_match.getInning().get(i).getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + third_party_match.getInning().get(i).getShortName().toUpperCase() + ";");
					if(third_party_match.getInning().get(i).getNoOfWickets() >= 10) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + third_party_match.getInning().get(i).getRuns() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + third_party_match.getInning().get(i).getRuns() + "-" + third_party_match.getInning().get(i).getNoOfWickets() + ";");
					}
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOverIS " + third_party_match.getInning().get(i).getOvers() + ";");
					
					if(inning == 1 || inning == 3) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "CURRENT RUN RATE : " + third_party_match.getInning().get(i).getRunRate() + ";");

					}else {
						if(inning == 2) {
							if((third_party_match.getMatchDetails().getTarget().getTarget()-1) == third_party_match.getInning().get(1).getRuns().intValue()) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "SCORES ARE LEVEL" + ";");
							}
							else if(third_party_match.getMatchDetails().getTarget().getTarget().intValue() <= third_party_match.getInning().get(1).getRuns().intValue()) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + third_party_match.getInning().get(1).getShortName() + 
											" WIN BY " + (10 - third_party_match.getInning().get(1).getNoOfWickets()) + " WICKETS" + ";");
							}
							else if(third_party_match.getInning().get(1).getNoOfWickets().intValue() >= 10 || remaiming_balls <= 0) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + third_party_match.getInning().get(0).getShortName() + 
											" WIN BY " + (third_party_match.getMatchDetails().getTarget().getRunsRequired() - 1) + " RUNS" + ";");
							}
							else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + third_party_match.getInning().get(1).getShortName() + 
											" NEED " + third_party_match.getMatchDetails().getTarget().getRunsRequired() + " RUN" + CricketFunctions.Plural(third_party_match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + 
											" TO WIN FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							}
						}else {
							if((third_party_match.getMatchDetails().getTarget().getTarget()-1) == third_party_match.getInning().get(3).getRuns().intValue()) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "SCORES ARE LEVEL" + ";");
							}
							else if(third_party_match.getMatchDetails().getTarget().getTarget().intValue() <= third_party_match.getInning().get(3).getRuns().intValue()) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + third_party_match.getInning().get(3).getShortName() + 
											" WON THE SUPER OVER" + ";");
							}
							else if(third_party_match.getInning().get(3).getNoOfWickets().intValue() >= 10 || remaiming_balls <= 0) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + third_party_match.getInning().get(2).getShortName() + 
											" WON THE SUPER OVER" + ";");
							}
							else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + third_party_match.getInning().get(3).getShortName() + 
											" NEED " + third_party_match.getMatchDetails().getTarget().getRunsRequired() + " RUN" + CricketFunctions.Plural(third_party_match.getMatchDetails().getTarget().getRunsRequired()).toUpperCase() + 
											" TO WIN FROM " + remaiming_balls + " BALL" + CricketFunctions.Plural(remaiming_balls).toUpperCase() + ";");
							}
						}
					}
				}
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			}
			
			
			break;
		}
	}
	
	public void populateMileStone(PrintWriter print_writer,int team_id,String data1,String data2,String data3,int player_id , AE_Cricket third_party_match,List<Player> allPlayer,List<Team> allTeams, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			
			for(AE_Team team : third_party_match.getTeam()) {
				for(int j = 0; j<= team.getPlayer().size() - 1; j++) {
					if(team.getPlayer().get(j).getID().intValue() == allPlayer.get(player_id-1).getAe_Id().intValue()) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
								allTeams.get(team_id-1).getTeamName4() + "\\Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						
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
						
						break;
					}
				}
			}
			if(allTeams.get(team_id-1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					allTeams.get(team_id-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
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
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public void populatePlayerfreeText(PrintWriter print_writer,int team_id,String data1,String data2,int player_id , AE_Cricket third_party_match,List<Player> allPlayer,List<Team> allTeams, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":

			if(allTeams.get(team_id-1).getTeamName1().toUpperCase().equalsIgnoreCase("PAPUA NEW GUINEA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName "+
						allTeams.get(team_id-1).getTeamName4().toUpperCase()+ ";");
			}else if(allTeams.get(team_id-1).getTeamName1().toUpperCase().equalsIgnoreCase("UNITED STATES OF AMERICA")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName "+
						allTeams.get(team_id-1).getTeamName4().toUpperCase()+ ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName "+
						allTeams.get(team_id-1).getTeamName1().toUpperCase()+ ";");
			}

			for(AE_Team team : third_party_match.getTeam()) {
				for(int j = 0; j<= team.getPlayer().size() - 1; j++) {
					if(team.getPlayer().get(j).getID().intValue() == allPlayer.get(player_id-1).getAe_Id().intValue()) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
								allTeams.get(team_id-1).getTeamName4() + "\\Right_2048\\" + allPlayer.get(player_id-1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " +allPlayer.get(player_id-1).getFull_name() + ";");
						
						break;
					}
				}
			}
			if(allTeams.get(team_id-1).getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
					allTeams.get(team_id-1).getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			
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
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public void populateWeather(PrintWriter print_writer,String data1,String data2,String data3, AE_Cricket third_party_match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + data1 + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + data2 + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + data3 + ";");
			
			if(third_party_match.getMatchDetails().getGround().getName().toUpperCase().contains("AND")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tVenue " + 
						third_party_match.getMatchDetails().getGround().getName().toUpperCase().replace("AND", "&") + ";");

			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tVenue " + 
						third_party_match.getMatchDetails().getGround().getName().toUpperCase() + ";");

			}
			

			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public void populateTeamBoundary(PrintWriter print_writer,int inning, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "BOUNDARIES" + ";");
			for(AE_Inning inn : match.getInning()) {
					if(inn.getNumber() == inning) {
						if(inn.getShortName().equalsIgnoreCase(match.getMatchDetails().getHomeTeam().getShortName())){
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getHomeTeam().getShortName().toUpperCase() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getMatchDetails().getAwayTeam().getShortName().toUpperCase() + ";");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "FOURS" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + inn.getfours() + ";");
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "SIXES" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + inn.getSixes() + ";");
						if(inn.getNoOfWickets()==10) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + inn.getRuns() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreIS " + inn.getRuns() +"-"+inn.getNoOfWickets()+ ";");
						}

						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOverIS " + inn.getOvers() + ";");
						if(inn.getNumber()==1 || inn.getNumber()==3) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + " CURRENT RUN RATE : "+inn.getRunRate() + ";");
	
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + matchSummary(match,inn.getNumber()).toUpperCase() + ";");

						}
						if(inn.getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
						}
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path + 
								inn.getShortName().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					}
				}
			
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}

	private void populateTargetBs(PrintWriter print_writer, AE_Cricket match,
			String session_selected_broadcaster2) throws Exception {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
			if(match.getInning().get(1).getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + match.getInning().get(1).getShortName().toUpperCase() + ".png" +";");

			int requiredRuns = match.getMatchDetails().getTarget().getRunsRequired();
			
			if(match.getMatchDetails().getTarget().getTarget()!= 0) {
				requiredRuns = match.getMatchDetails().getTarget().getTarget();
			}
			
			if(requiredRuns <= 0) {
				requiredRuns = 0;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHeadTarget " + "REQUIRED RUN RATE " + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRTarget " + match.getMatchDetails().getTarget().getRequiredRunRate()+";");

			if(match.getMatchDetails().getTarget().getTarget() == null || match.getMatchDetails().getTarget().getTarget()==0) {
				if(match.getMatchDetails().getTarget().getOversLeft() == "1") {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + match.getMatchDetails().getTarget().getTarget()  +";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + Integer.valueOf(match.getMatchDetails().getTarget().getOversLeft())* 6 + " BALLS " +";");
				}else {
					if(Double.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6 >= 100) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + match.getMatchDetails().getTarget().getTarget()  +";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers() + " OVERS" +";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + match.getMatchDetails().getTarget().getTarget()  +";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + Integer.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())* 6 + " BALLS"  +";");
					}
				}
			}else {
				if(Double.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())*6 >= 100) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + match.getMatchDetails().getTarget().getTarget()  +";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers()+ " OVERS" + 
					";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreTarget " + match.getMatchDetails().getTarget().getTarget()  +";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversTarget " + Double.valueOf(match.getMatchDetails().getScheduledOvers().getSecondInningsTargetOvers())* 6 + " BALLS" + 
							 ";");
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}

	private void populateBugBowler(PrintWriter print_writer, String string, Integer valueOf, String string2,
			Integer valueOf2, AE_Cricket third_party_match, String session_selected_broadcaster2) {
		// TODO Auto-generated method stub
		
	}

	private void populateHowout(PrintWriter print_writer, String string, Integer valueOf, String string2,
			Integer valueOf2, AE_Cricket third_party_match, String session_selected_broadcaster2) {
		// TODO Auto-generated method stub
		
	}
	
	public void populatePlayerProfileBs(PrintWriter print_writer,String viz_scene,int player_id,String Profile,String TypeofProfile,
			Statistics stats,List<Player> plyer,AE_Cricket third_party_match, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		double average = 0;
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			if(Profile.equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "T20 CAREER" + ";");
			}else if(Profile.equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "T20I CAREER" + ";");
			}else if(Profile.equalsIgnoreCase("FC")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "FIRST-CLASS CAREER" + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + Profile + " CAREER" + ";");
			}

			Player plyr = getPlayerFromMatchData(player_id, match);
			
			if(plyr.getFirstname() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
				
				if(plyr.getSurname() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
				}
			}else {
				if(plyr.getSurname() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getSurname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
					
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getTicker_name() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
				}
			}
			
			
			
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}
			else {
				if(match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				if(match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}
			
			for(AE_Player plyr_data : third_party_match.getPlayer()) {
				if(plyr_data.getID().intValue() == plyer.get(player_id-1).getAe_Id().intValue()) {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "MATCHES" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + plyr_data.getMatches() + ";");
	
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "WICKETS" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + plyr_data.getWickets() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "AVERAGE" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + plyr_data.getBowlAverage() + ";");
	
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "BEST" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + plyr_data.getBestBowl() + ";");
					break;
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

		}

	}
	
	public void populatePlayerProfileBat(PrintWriter print_writer,String viz_scene,int player_id,String Profile,String TypeofProfile,
			Statistics stats,List<Player> plyer,AE_Cricket third_party_match, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		double strike_rate =0;
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			if(Profile.equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "T20 CAREER" + ";");
			}else if(Profile.equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "T20I CAREER" + ";");
			}else if(Profile.equalsIgnoreCase("FC")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "FIRST-CLASS CAREER" + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + Profile + " CAREER" + ";");
			}

			Player plyr = getPlayerFromMatchData(player_id, match);
			
			if(plyr.getFirstname() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
				
				if(plyr.getSurname() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
				}
			}else {
				if(plyr.getSurname() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getSurname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
					
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getTicker_name() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + " " + ";");
				}
			}
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
			
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}
			else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage "+ photo_path + 
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\Right_2048\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				if(match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase("NEP")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
				}
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgFlag " + logo_path +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			}
			
			for(AE_Player plyr_data : third_party_match.getPlayer()) {
				if(plyr_data.getID().intValue() == plyer.get(player_id-1).getAe_Id().intValue()) {
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead1 " + "MATCHES" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue1 " + plyr_data.getMatches() + ";");
	
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead2 " + "RUNS" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue2 " + plyr_data.getRuns() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead3 " + "STRIKE RATE" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue3 " + plyr_data.getBatStrikeRate() + ";");
	
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead4 " + "BEST" + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue4 " + plyr_data.getBestBat() + ";");
					break;
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
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

	public void populateCountdown(PrintWriter print_writer, String data, AE_Cricket third_party_match,
			String session_selected_broadcaster) {
	switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
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
	
	
	public void populateComparisonBs(PrintWriter print_writer, AE_Cricket match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_BIG_SCREEN":
					
			if(match.getMatchDetails().getHomeTeam().getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag 0;");
			}
			
			if(match.getMatchDetails().getAwayTeam().getShortName().toUpperCase().equalsIgnoreCase("NEP")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectFlag2 0;");
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + match.getMatchDetails().getHomeTeam().getShortName().toUpperCase() + ".png" +";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + match.getMatchDetails().getAwayTeam().getShortName().toUpperCase() + ".png" +";");

			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "AFTER " + match.getMatchDetails().getComparison().getCompOvers() + " OVERS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreA " +  match.getMatchDetails().getComparison().getTeam1Score() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreB " + match.getMatchDetails().getComparison().getTeam2Score() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " +match.getMatchDetails().getComparison().getTeam1Name() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamB " + match.getMatchDetails().getComparison().getTeam2Name() + ";");
			
		
	
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 200.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			break;
		}
	}
	public static String  matchSummary(AE_Cricket match , int inn_num) {
	String matchSummaryStatus = "",SplitSummaryText = "",batTeamNm="",bowlTeamNm="";
	
	
	AE_Inning inning= match.getInning().stream().filter(inn->inn.getNumber()==match.getMatchDetails().getStatus().getCurrentInnings()).findAny().orElse(null);
		if(inning!=null) {
			
			if(match.getMatchDetails().getHomeTeam().getShortName().equalsIgnoreCase(inning.getShortName())) {
				batTeamNm = (match.getMatchDetails().getHomeTeam().getLongName());
				bowlTeamNm = (match.getMatchDetails().getAwayTeam().getLongName());
	
			}else if(match.getMatchDetails().getAwayTeam().getShortName().equalsIgnoreCase(inning.getShortName())) {
				batTeamNm = (match.getMatchDetails().getAwayTeam().getLongName());
				bowlTeamNm = (match.getMatchDetails().getHomeTeam().getLongName());
			}
			
			if ((match.getMatchDetails().getTarget().getRunsRequired() > 0) && (getRequiredBalls(match) > 0) 
		    		&& (10-inning.getNoOfWickets()) > 0) {
	
		    	matchSummaryStatus = batTeamNm + " need " + match.getMatchDetails().getTarget().getRunsRequired() + 
			        	" run" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired()) + " to win from ";
		    	if (getRequiredBalls(match) > 120) {
		    		matchSummaryStatus = matchSummaryStatus + CricketFunctions.OverBalls(0,getRequiredBalls(match)) + " overs";
				} else {
					matchSummaryStatus = matchSummaryStatus + getRequiredBalls(match) + 
							" ball" + CricketFunctions.Plural(getRequiredBalls(match));
				}
		    } else if (match.getMatchDetails().getTarget().getRunsRequired() <= 0)
		    {
		    	if(inn_num == 2) {
		    		if(SplitSummaryText.isEmpty()) {
						matchSummaryStatus = batTeamNm + " win " + " by " +(10-inning.getNoOfWickets())+ 
					    		" wicket" + CricketFunctions.Plural((10-inning.getNoOfWickets()));
					} else {
						matchSummaryStatus = batTeamNm + " win " + SplitSummaryText 
							+ "by " +(10-inning.getNoOfWickets()) + " wicket" 
							+ CricketFunctions.Plural((10-inning.getNoOfWickets()));
					}
				}else {
					if(SplitSummaryText.isEmpty()) {
				    	matchSummaryStatus =  batTeamNm + " won " + " the super over";
					} else {
				    	matchSummaryStatus =  batTeamNm + " won " + SplitSummaryText + "the super over";
					}
				}
		    } else if (match.getMatchDetails().getTarget().getRunsRequired() == 1 && (getRequiredBalls(match) <= 0 
		    		|| (10-inning.getNoOfWickets()) <= 0)) {
		    	if(SplitSummaryText.isEmpty()) {
			    	matchSummaryStatus = "Match tied - winner will be decided by super over";
				} else {
			    	matchSummaryStatus = "Match tied" + SplitSummaryText + "winner will be decided by super over";
				}
		    }
		    else {
		    	
		    	if(inn_num == 2) {
		    		if(SplitSummaryText.isEmpty()) {
				    	matchSummaryStatus =  bowlTeamNm + " win by " + (match.getMatchDetails().getTarget().getRunsRequired() - 1) + 
				    		" run" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired() - 1);
					} else {
				    	matchSummaryStatus =  bowlTeamNm + " win " + SplitSummaryText + "by " + (match.getMatchDetails().getTarget().getRunsRequired() - 1) + 
				    		" run" + CricketFunctions.Plural(match.getMatchDetails().getTarget().getRunsRequired() - 1);
					}
		    	}else {
		    		if(SplitSummaryText.isEmpty()) {
				    	matchSummaryStatus =  bowlTeamNm + " won " + " the super over";
					} else {
				    	matchSummaryStatus =  bowlTeamNm + " won " + SplitSummaryText + "the super over";
					}
		    	}
		    }
		    
		}
	
	
		return matchSummaryStatus;
	
}
public static int getRequiredBalls(AE_Cricket match) {
	int balls=0;
	if(match.getMatchDetails().getTarget().getOversLeft().contains(".")) {
		balls = (Integer.valueOf(match.getMatchDetails().getTarget().getOversLeft().trim().split("\\.")[0])*6)+
				Integer.valueOf(match.getMatchDetails().getTarget().getOversLeft().trim().split("\\.")[1]);
	}else {
		balls = Integer.valueOf(match.getMatchDetails().getTarget().getOversLeft().trim())*6;
	}
	return balls;
	
}
}