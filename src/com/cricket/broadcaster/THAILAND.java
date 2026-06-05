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
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Configuration;
import com.cricket.model.DaySession;
import com.cricket.model.Event;
import com.cricket.model.Player;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.service.CricketService;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.LeagueTeam;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.OverByOverData;
import com.cricket.model.Partnership;
import com.cricket.model.FallOfWicket;
import com.cricket.model.Fixture;
import com.cricket.model.Ground;
import com.cricket.model.InfobarStats;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class THAILAND extends Scene{

	private String status;
	private String slashOrDash = "-";
	public Infobar infobar = new Infobar();
	public String session_selected_broadcaster = "THAILAND";
	public String diectoryPath = "";
	public String which_graphics_onscreen = "";
	private String logo_path = "C:\\Images\\THAILAND\\Logos\\";
	private String sponsor_logo_path = "D:\\EverestCricket\\Everest_Cric2022\\Logos\\Goa_Cricket_Logos\\Sponsors\\";
	private String photo_path = "C:\\Images\\THAILAND\\Photos\\";
	private String local_photo_path = "c\\Images\\THAILAND\\Photos\\";
	
	public THAILAND() {
		super();
	}

	public THAILAND(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Infobar updateInfobar(Scene scene, MatchAllData match,boolean show_speed, PrintWriter print_writer) throws InterruptedException, IOException
	{
		if(infobar.isInfobar_on_screen() == true) {
			infobar = populateInfobarTeamScore(infobar,true, print_writer, match, session_selected_broadcaster);
			infobar = populateInfobarTopLeft(infobar,true, print_writer, match, session_selected_broadcaster);
			infobar = populateInfobarTopRight(infobar,true, print_writer, match, session_selected_broadcaster);
			infobar = populateInfobarBottomLeft(infobar,true, print_writer, match, session_selected_broadcaster);
			infobar = populateInfobarBottomRight(infobar,true, print_writer,  match, session_selected_broadcaster);
			infobar = populateInfobarBottom(infobar,true, print_writer,  match, session_selected_broadcaster);
		}
		CricketFunctions.getInteractive(match,"FULL_WRITE",diectoryPath);
		return infobar;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics, Configuration config, String session_directoryPath) throws InterruptedException, ParseException, JAXBException, NumberFormatException, IOException, IllegalAccessException, InvocationTargetException{
		
		diectoryPath = session_directoryPath;
		
		switch (whatToProcess.toUpperCase()) {
		case "BUG_GRAPHICS-OPTIONS": case "HOWOUT_GRAPHICS-OPTIONS": case "BATSMANSTATS_GRAPHICS-OPTIONS": case "BOWLERSTATS_GRAPHICS-OPTIONS": case "NAMESUPER_PLAYER_GRAPHICS-OPTIONS": 
		case "L3PLAYERPROFILE_GRAPHICS-OPTIONS": case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "BOTTOMLEFT_GRAPHICS-OPTIONS": case "BOTTOMRIGHT_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": 
		case "COMPARISION-GRAPHICS-OPTIONS": case "BOTTOM_GRAPHICS-OPTIONS": case "ANIMATE_PLAYINGXI-OPTIONS": case "PROJECTED_GRAPHICS-OPTIONS": case "TARGET_GRAPHICS-OPTIONS": 
		case "PLAYERSUMMARY_GRAPHICS-OPTIONS": case "BUG_DISMISSAL_GRAPHICS-OPTIONS": case "TOP_GRAPHICS-OPTIONS": case "BUG_BOWLER_GRAPHICS-OPTIONS": case "HOWOUT_WITHOUT_FIELDER_GRAPHICS-OPTIONS":
		case "BOWLERDETAILS_GRAPHICS-OPTIONS":	case "NEXTTOBAT_GRAPHICS-OPTIONS": case "BOWLERSUMMARY_GRAPHICS-OPTIONS": case "LANDMARK_GRAPHICS-OPTIONS": case "EQUATION_GRAPHICS-OPTIONS":
		case "POSITION_LANDMARK_GRAPHICS-OPTIONS": case "BATSMAN_THIS_MATCH_GRAPHICS-OPTIONS": case "BOWLER_THIS_MATCH_GRAPHICS-OPTIONS": case "PLAYERS_GRAPHICS-OPTIONS":
		case "BATSMAN_STYLE_GRAPHICS-OPTIONS": case "GENERIC_GRAPHICS-OPTIONS": case "FF_TARGET_GRAPHICS-OPTIONS": case "HOWOUT_BOTH_GRAPHICS-OPTIONS": case "BATSMANSTATS_BOTH_GRAPHICS-OPTIONS":
		case "THIS_SESSION_GRAPHICS-OPTIONS": case "FF_EQUATION_GRAPHICS-OPTIONS": case "L3PLAYERPROFILEBUKHATIR_GRAPHICS-OPTIONS": case "PLAYERPROFILEBUKHATIR_GRAPHICS-OPTIONS":	
			switch (session_selected_broadcaster.toUpperCase()) {
			case "THAILAND":
				return new ObjectMapper().writeValueAsString(match).toString();
			}
		
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			switch (session_selected_broadcaster.toUpperCase()) {
			case "THAILAND":
				return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
			}
		//case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS":
			//return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
			
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
		
		case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-L3-BUG":  case "POPULATE-L3-HOWOUT":
		case "POPULATE-L3-BATSMANSTATS": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-FF-DOUBLETEAMS": case "POPULATE-L3-INFOBAR": 
		case "POPULATE-FF-LEADERBOARD": case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-FF-MATCHID": case "POPULATE-FF-PLAYINGXI": 
		case "POPULATE-LT-PROJECTED": case "POPULATE-L3-TARGET": case "POPULATE-L3-TEAMSUMMARY": case "POPULATE-L3-PLAYERSUMMARY": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-L3-FALLOFWICKET": 
		case "POPULATE-L3-COMPARISION": case "POPULATE-INFOBAR-PROMPT": case "POPULATE-LT-MATCHID": case "POPULATE-L3-BOWLERSTATS": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-SPLIT":
		case "POPULATE-L3-BUG-DB": case "POPULATE-INFOBAR-TOP": case "POPULATE-L3-BUG-BOWLER": case "POPULATE-L3-BUGTARGET": case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER": case "POPULATE-L3-BOWLERSUMMARY": 
		case "POPULATE-L3-NEXT_TO_BAT": case "POPULATE-L3-BOWLERDETAILS": case "POPULATE-LT-POWERPLAY": case "POPULATE-FF-LANDMARK": case "POPULATE-PREVIOUS_SUMMARY": case "POPULATE-LT-EQUATION": 
		case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-L3-BATSMAN_THIS_MATCH": case "POPULATE-L3-BOWLER_THIS_MATCH": case "POPULATE-POINTS_TABLE": case "POPULATE-LT-PARTNERSHIP":
		case "POPULATE-LTPOINTS_TABLE":	case "POPULATE-BOWLER_STYLE": case "POPULATE-BATSMAN_STYLE": case "POPULATE-MANHATTAN": case "POPULATE-MATCH_PROMO": case "POPULATE-FF-TEAMS_LOGO":
		case "POPULATE-TIEID-DOUBLE": case "POPULATE-L3-GENERIC": case "POPULATE-MOSTRUNS": case "POPULATE-MOSTWICKETS": case "POPULATE-MOSTFOURS": case "POPULATE-MOSTSIXES": case "POPULATE-HIGHESTSCORE":
		case "POPULATE-DIRECTOR": case "POPULATE-FF-TARGET": case "POPULATE-THISOVER": case "POPULATE-HOWOUT_QUICK": case "POPULATE-WORM": case "POPULATE-INFOBAR-TOPRIGHT":
		case "POPULATE-MATCHSTATUS": case "POPULATE-L3-HOWOUT_BOTH": case "POPULATE-L3-BATSMANSTATS_BOTH": case "POPULATE-LT-SPONSOR": case "POPULATE-LT-THIS_SESSION": case "POPULATE-THIS_SESSION":
		case "POPULATE-EQUATION-FF": case "POPULATE-L3-BUG-TOSS": case "POPULATE-L3-PLAYERPROFILE_BUKHATIR": case "POPULATE-FF-PLAYERPROFILE_BUKHATIR": case "POPULATE-HOWOUTWITHOUTFIELDER_QUICK":
		case "POPULATE-BUG_POWERPLAY": case "POPULATE-BUG_PARTNERSHIP": case "POPULATE-L3-THISSERIES": case "POPULATE-FOUR": case "POPULATE-SIX":
			switch (session_selected_broadcaster.toUpperCase()) {
			case "THAILAND":
				switch(whatToProcess.toUpperCase()) {
				case"POPULATE-INFOBAR-BOTTOMLEFT": case"POPULATE-INFOBAR-BOTTOMRIGHT": case"POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-DIRECTOR":
				case "POPULATE-INFOBAR-TOPRIGHT": case "POPULATE-LT-SPONSOR":
					break;
				case "POPULATE-L3-INFOBAR":
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,session_selected_broadcaster);
					break;
				default:
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,session_selected_broadcaster);
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-L3-THISSERIES":
					//System.out.println(valueToProcess);
					populateThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
							,match, session_selected_broadcaster);
					break;
				case "POPULATE-BUG_PARTNERSHIP":
					populateBugPartnership(print_writer, valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-BUG_POWERPLAY":
					populateBugPowerPLay(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BUG-TOSS":
					populateBugToss(print_writer,valueToProcess.split(",")[0],match,session_selected_broadcaster);
					break;
				case "POPULATE-THIS_SESSION":
					populateSession(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),match,session_selected_broadcaster);
					break;
				case "POPULATE-LT-THIS_SESSION":
					populateThisSession(print_writer,valueToProcess.split(",")[0],match,session_selected_broadcaster);
					break;
				case "POPULATE-LT-SPONSOR":
					populateSponsor(print_writer,session_selected_broadcaster);
					break;
				case "POPULATE-THISOVER":
					populateThisOver(print_writer,valueToProcess.split(",")[0],match,session_selected_broadcaster);
					break;
				case "POPULATE-MOSTRUNS":
					populateMostRuns(print_writer, valueToProcess.split(",")[0],
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
							match,session_selected_broadcaster);
					break;
				case "POPULATE-MOSTWICKETS":
					populateMostWickets(print_writer, valueToProcess.split(",")[0],
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
							match,session_selected_broadcaster);
					break;
				case "POPULATE-MOSTFOURS":
					populateMostFours(print_writer, valueToProcess.split(",")[0],
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
							match,session_selected_broadcaster);
					break;
				case "POPULATE-MOSTSIXES":
					populateMostSixes(print_writer, valueToProcess.split(",")[0],
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
							match,session_selected_broadcaster);
					break;
				case "POPULATE-HIGHESTSCORE":
					populateHighestScore(print_writer, valueToProcess.split(",")[0],
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
							match,session_selected_broadcaster);
					break;
				case "POPULATE-FF-SCORECARD":
					System.out.println(config.getPrimaryIpAddress());
					populateScorecard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-BOWLINGCARD":
					populateBowlingcard(print_writer, valueToProcess.split(",")[0], false, Integer.valueOf(valueToProcess.split(",")[1]), match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-PARTNERSHIP":
					//whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					populatePartnership(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), match, session_selected_broadcaster);
					break;
				case "POPULATE-LT-PARTNERSHIP":
					populateCurrentPartnership(print_writer, valueToProcess.split(",")[0], match, session_selected_broadcaster, config);
					break;
				case "POPULATE-FF-MATCHSUMMARY":
					populateMatchsummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), cricketService.getVariousTexts(),match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-TEAMS_LOGO":
					populateTeamsLogo(print_writer, valueToProcess.split(",")[0],cricketService.getTeams(),match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BUG-DISMISSAL":
					populateBugDismissal(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BUG":
					populateBug(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BUG-BOWLER":
					populateBugBowler(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-HOWOUT":
//					CricketFunctions.updateTournamentDataWithStats(null, tournament_matches, match)
					populateHowout(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2]
							, Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-HOWOUT_QUICK":
					populateL3Howout(print_writer, valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-HOWOUTWITHOUTFIELDER_QUICK":
					populateL3HowoutWithoutFielder(print_writer, valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-HOWOUT_BOTH":
					populateHowoutBoth(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER":
					populateHowoutWithoutFielder(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BATSMANSTATS":
					populateBatsmanstats(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BATSMANSTATS_BOTH":
					populateBatsmanstatsBoth(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BOWLERSTATS":
					populateBowlerstats(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]),cricketService.getTeams(), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BUG-DB":
					for(Bugs bug : cricketService.getBugs()) {
						  if(bug.getBugId() == Integer.valueOf(valueToProcess.split(",")[1])) {
							  populateBugsDB(print_writer, valueToProcess.split(",")[0], bug, match, session_selected_broadcaster);
						  }
						}
						break;
				case "POPULATE-L3-NAMESUPER":
					for(NameSuper ns : cricketService.getNameSupers()) {
					  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateNameSuper(print_writer, valueToProcess.split(",")[0], ns, match, session_selected_broadcaster);
					  }
					}
					break;
				case "POPULATE-L3-NAMESUPER-PLAYER":
					populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, session_selected_broadcaster);
					break;
					
				case "POPULATE-FF-PLAYERPROFILE":
					for(Statistics stats : cricketService.getAllStats()) {
						if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
							stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
							stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, session_selected_broadcaster, config);
							}
						}
					}
					break;
				case "POPULATE-FF-PLAYERPROFILE_BUKHATIR":
					for(Statistics stats : cricketService.getAllStats()) {
						if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
							stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
							stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, session_selected_broadcaster,config);
							}
						}
					}
					break;
				case "POPULATE-FF-DOUBLETEAMS":
					populateDoubleteams(print_writer,valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-INFOBAR":
					infobar.setMiddle_section(valueToProcess.split(",")[1]);
					infobar.setTop_right_section(valueToProcess.split(",")[2]);
					infobar.setBottom_left_section(valueToProcess.split(",")[3]);
					infobar.setBottom_right_section(valueToProcess.split(",")[4]);
					
					populateInfobar(infobar, print_writer, valueToProcess.split(",")[0],match, session_selected_broadcaster);
					break;
				case "POPULATE-DIRECTOR":
					populateInfobarDirector(print_writer,valueToProcess,session_selected_broadcaster);
					break;
				case "POPULATE-INFOBAR-BOTTOMLEFT":
					if(infobar.getLast_bottom_right_bottom_section() != null && !infobar.getLast_bottom_right_bottom_section().isEmpty()) {
						infobar.setBottom_left_section(valueToProcess);
						infobar.setBottom_right_section("TOURNAMENT_NAME");
						processAnimation(print_writer, "Section6_Out", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(500);
						populateInfobarBottomLeft(infobar,false,print_writer, match, session_selected_broadcaster);
						populateInfobarBottomRight(infobar,false,print_writer, match, session_selected_broadcaster);
						processAnimation(print_writer, "Section4_In", "START", session_selected_broadcaster,1);
						processAnimation(print_writer, "Section5_In", "START", session_selected_broadcaster,1);
						infobar.setBottom_right_bottom_section("");
						infobar.setLast_bottom_right_bottom_section("");
						
					}else if (infobar.getLast_bottom_left_section() != null && !infobar.getLast_bottom_left_section().isEmpty()) {
						infobar.setBottom_left_section(valueToProcess);
						processAnimation(print_writer, "Section4_Out", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(500);
						populateInfobarBottomLeft(infobar,false,print_writer, match, session_selected_broadcaster);
						processAnimation(print_writer, "Section4_In", "START", session_selected_broadcaster,1);
					}
					break;
				case "POPULATE-INFOBAR-BOTTOMRIGHT":
					if(infobar.getLast_bottom_right_bottom_section() != null && !infobar.getLast_bottom_right_bottom_section().isEmpty()) {
						infobar.setBottom_right_section(valueToProcess);
						infobar.setBottom_left_section("VS_BOWLING_TEAM");
						processAnimation(print_writer, "Section6_Out", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(500);
						populateInfobarBottomLeft(infobar,false,print_writer, match, session_selected_broadcaster);
						populateInfobarBottomRight(infobar,false,print_writer, match, session_selected_broadcaster);
						processAnimation(print_writer, "Section5_In", "START", session_selected_broadcaster,1);
						processAnimation(print_writer, "Section4_In", "START", session_selected_broadcaster,1);
						infobar.setBottom_right_bottom_section("");
						infobar.setLast_bottom_right_bottom_section("");
						
					}else if (infobar.getLast_bottom_right_section() != null && !infobar.getLast_bottom_right_section().isEmpty()) {
						infobar.setBottom_right_section(valueToProcess);
						processAnimation(print_writer, "Section5_Out", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(500);
						populateInfobarBottomRight(infobar,false,print_writer, match, session_selected_broadcaster);
						processAnimation(print_writer, "Section5_In", "START", session_selected_broadcaster,1);
					}
					
					
					break;
				case "POPULATE-INFOBAR-TOPRIGHT":
					populateInfobarTopRight(infobar,false, print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-INFOBAR-PROMPT":
					if(infobar.getLast_bottom_right_bottom_section() != null && !infobar.getLast_bottom_right_bottom_section().isEmpty()) {
						infobar.setBottom_left_section("VS_BOWLING_TEAM");
						processAnimation(print_writer, "Section6_Out", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(500);
						populateInfobarBottomLeft(infobar,false,print_writer, match, session_selected_broadcaster);
						for(InfobarStats ibs : cricketService.getInfobarStats() ) {
							  if(ibs.getOrder() == Integer.valueOf(valueToProcess)) {
								  populateInfobarPrompt(false,print_writer, ibs, match, session_selected_broadcaster);
							  }
						}
						processAnimation(print_writer, "Section4_In", "START", session_selected_broadcaster,1);
						processAnimation(print_writer, "Section5_In", "START", session_selected_broadcaster,1);
						infobar.setBottom_right_bottom_section("");
						infobar.setLast_bottom_right_bottom_section("");
					}else if (infobar.getLast_bottom_right_section() != null && !infobar.getLast_bottom_right_section().isEmpty()) {
						processAnimation(print_writer, "Section5_Out", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(500);
						for(InfobarStats ibs : cricketService.getInfobarStats() ) {
							  if(ibs.getOrder() == Integer.valueOf(valueToProcess)) {
								  populateInfobarPrompt(false,print_writer, ibs, match, session_selected_broadcaster);
							  }
						}
						processAnimation(print_writer, "Section5_In", "START", session_selected_broadcaster,1);
						infobar.setBottom_right_section("STATS");
						infobar.setLast_bottom_right_section("STATS");
					}
					break;
					
				case "POPULATE-INFOBAR-BOTTOM":
					
					if(infobar.getLast_bottom_right_bottom_section() != null && !infobar.getLast_bottom_right_bottom_section().isEmpty()) {
						infobar.setBottom_right_bottom_section(valueToProcess);
						processAnimation(print_writer, "Section6_Out", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(500);
						populateInfobarBottom(infobar,false, print_writer, match, session_selected_broadcaster);
						processAnimation(print_writer, "Section6_In", "START", session_selected_broadcaster,1);
					}else {
						infobar.setBottom_right_bottom_section(valueToProcess);
						processAnimation(print_writer, "Section4_Out", "START", session_selected_broadcaster,1);
						processAnimation(print_writer, "Section5_Out", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(500);
						populateInfobarBottom(infobar,false, print_writer, match, session_selected_broadcaster);
						processAnimation(print_writer, "Section6_In", "START", session_selected_broadcaster,1);
						infobar.setBottom_left_section("");
						infobar.setLast_bottom_left_section("");
						infobar.setBottom_right_section("");
						infobar.setLast_bottom_right_section("");
					}
					

					break;
				case "POPULATE-FF-MATCHID":
					populateMatchId(print_writer,valueToProcess.split(",")[0],cricketService.getFixtures(),cricketService.getVariousTexts(), match, session_selected_broadcaster);
					break;
				case "POPULATE-LT-MATCHID":
					populateLTMatchId(print_writer,valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCH_PROMO":
					populateMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),cricketService.getVariousTexts(),
							cricketService.getFixtures(),cricketService.getGrounds(),match , session_selected_broadcaster);
					break;
				case "POPULATE-FF-PLAYINGXI":
					populatePlayingXI(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							match, session_selected_broadcaster, config);
					break;
				case "POPULATE-FF-LEADERBOARD":
					populateLeaderBoard(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
							CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
							cricketService.getTeams(),match, session_selected_broadcaster, config);
					break;
				case "POPULATE-LT-PROJECTED":
					populateProjectedScore(print_writer,valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-TARGET":
					populateTarget(print_writer,valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-TARGET":
					populateFFTarget(print_writer,valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-EQUATION-FF":
					populateFFEquation(print_writer,valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-LT-EQUATION":
					populateLtEquation(print_writer,valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCHSTATUS":
					populateLtMatchStatus(print_writer,valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-TEAMSUMMARY":
					populateTeamSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-PLAYERSUMMARY":
					populateLtBattingSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
							match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BOWLERDETAILS":
					populateLtBowlerSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
							match, session_selected_broadcaster);
					break;
				case "POPULATE-FOUR":
					populatetournamentFour(print_writer, valueToProcess.split(",")[0],tournament_matches,match, session_selected_broadcaster);
//					System.out.println("Four = " + CricketFunctions.gettournamentFoursAndSixes(tournament_matches, match));
					break;
				case "POPULATE-SIX":
					populatetournamentSix(print_writer, valueToProcess.split(",")[0],tournament_matches,match, session_selected_broadcaster);
//					System.out.println("Four = " + CricketFunctions.gettournamentFoursAndSixes(tournament_matches, match));
					break;	
				case "POPULATE-L3-PLAYERPROFILE_BUKHATIR":
					for(Statistics stats : cricketService.getAllStats()) {
						if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
							stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
							
							stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populateLTPlayerProfile(print_writer,valueToProcess.split(",")[0],
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, session_selected_broadcaster);
							}
						}
					}
					break;
				case "POPULATE-L3-PLAYERPROFILE":
					for(Statistics stats : cricketService.getAllStats()) {
						if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
							stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
							
							stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
							
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populateLTPlayerProfile(print_writer,valueToProcess.split(",")[0],
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, session_selected_broadcaster);
							}
						}
					}
					break;
				case "POPULATE-L3-FALLOFWICKET":
					populateFallofWicket(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-SPLIT":
					populateSplit(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-COMPARISION":
					populateComparision(print_writer,valueToProcess.split(",")[0], match, session_selected_broadcaster);
					break;
				case "POPULATE-BATSMAN_STYLE":
					populateBatsmanStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster);
					break;
				case "POPULATE-BOWLER_STYLE":
					populateBowlerStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(), cricketService.getTeams(), match, session_selected_broadcaster);
					break;
				case "POPULATE-TIEID-DOUBLE":
					populateTieIdDouble(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1],cricketService.getFixtures(),cricketService.getTeams(), match, session_selected_broadcaster);
					break;
				case "POPULATE-POINTS_TABLE":
					LeagueTable groupA = null;
					LeagueTable groupB = null;
					if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupA.xml").exists()) {
						groupA = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
								new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupA.xml"));
					}
					
					if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupB.xml").exists()) {
						groupB = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
								new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "GroupB.xml"));
					}
					
					populatePointsTable(print_writer, valueToProcess.split(",")[0],groupA.getLeagueTeams(),groupB.getLeagueTeams(),session_selected_broadcaster,match);
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
//							new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + file.getName()))));
							for(Fixture fx : cricketService.getFixtures()) {
								//System.out.println(valueToProcess.split(",")[1]);
								if(fx.getMatchnumber() == Integer.valueOf(valueToProcess.split(",")[1])) {
									if(cricket_match.getMatch().getMatchFileName().replace(".xml", "").equalsIgnoreCase(fx.getMatchfilename()) 
											&& cricket_match.getSetup().getHomeTeam().getTeamId() == fx.getHometeamid() 
											&& cricket_match.getSetup().getAwayTeam().getTeamId() == fx.getAwayteamid())
									{
										cricket_matches.add(cricket_match);
									}
								}
							}
					}
					//System.out.println("id-" + Integer.valueOf(valueToProcess.split(",")[1]));
					//System.out.println("Player-" + valueToProcess.split(",")[2]);
					populatePreviousSummary(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricket_matches,cricketService.getFixtures(), 
							match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-GENERIC":
					populateGeneric(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1], match, session_selected_broadcaster);
					break;
				case "POPULATE-MANHATTAN":
					populateManhattan(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, session_selected_broadcaster);
					break;
				case "POPULATE-WORM":
					populateWorm(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, session_selected_broadcaster);
					break;
				}
				//return new ObjectMapper().writeValueAsString(this_doad).toString();
			}
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
		case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-INFOBAR":  
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY":
		case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": 
		case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BUG-BOWLER":
		case "ANIMATE-IN-BUGTARGET": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY":
		case "ANIMATE-IN-LANDMARK":	case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH":
		case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER":
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-GENERIC":
		case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-FF_TARGET":
		case "ANIMATE-IN-THISOVER": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-WORM": case "ANIMATE-IN-MATCHSTATUS": case "ANIMATE-IN-HOWOUT_BOTH":
		case "ANIMATE-IN-BATSMANSTATS_BOTH": case "ANIMATE-IN-SPONSOR": case "ANIMATE-OUT-SPONSOR": case "ANIMATE-IN-THIS_SESSION": case "ANIMATE-IN-SESSION": case "ANIMATE-IN-FF_EQUATION":
		case "ANIMATE-IN-BUG-TOSS": case "TICKER_LT_OUT": case "TICKER_LT_IN": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER_QUICK": case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP":
		case "ANIMATE-IN-THISSERIES": case "ANIMATE-IN-FOUR": case "ANIMATE-IN-SIX":
		
			switch (session_selected_broadcaster.toUpperCase()) {
			case "THAILAND":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
				case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS":
				case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY":
				case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": 
				case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER":
				case "ANIMATE-IN-BUGTARGET": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY": case "ANIMATE-IN-QUICK_HOWOUT":
				case "ANIMATE-IN-LANDMARK":	case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH":
				case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER":
				case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-GENERIC":
				case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-FF_TARGET":
				case "ANIMATE-IN-THISOVER": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-WORM": case "ANIMATE-IN-MINI-BOWLINGCARD": case "ANIMATE-IN-SQUAD":
				case "ANIMATE-IN-FF_THIS-SERIES": case "ANIMATE-IN-MINI-SCORECARD": case "ANIMATE-IN-LT_THIS-SERIES": case "ANIMATE-IN-LTMATCH_PROMO": case "ANIMATE-IN-BUG_POWERPLAY":
				case "ANIMATE-IN-MATCHSTATUS":	case "ANIMATE-IN-HOWOUT_BOTH": case "ANIMATE-IN-BATSMANSTATS_BOTH": case "ANIMATE-IN-THIS_SESSION": case "ANIMATE-IN-SESSION": case "ANIMATE-IN-FF_EQUATION":
				case "ANIMATE-IN-BUG-TOSS":	case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER_QUICK": case "ANIMATE-IN-BUG_PARTNERSHIP": case "ANIMATE-IN-THISSERIES":
					if(infobar.isInfobar_on_screen() == true) {
						processAnimation(print_writer, "FF_In", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(200);
					}
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-SCORECARD":
					//System.out.println("INN 1 SIZE -" + match.getInning().get(0).getBattingCard().size());
					//System.out.println("INN 2 SIZE -" + match.getInning().get(1).getBattingCard().size());
					if(match.getMatch().getInning().get(0).getBattingCard().size() == 12) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP$DataAll$BAtting$Batting_Card$12*CONTAINER SET ACTIVE 1;");
						processAnimation(print_writer, "CON1In", "START", session_selected_broadcaster,2);
					}
					else if(match.getMatch().getInning().get(1).getBattingCard().size() == 12) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP$DataAll$BAtting$Batting_Card$12*CONTAINER SET ACTIVE 1;");
						processAnimation(print_writer, "CON1In", "START", session_selected_broadcaster,2);
					}
					if(match.getMatch().getInning().get(0).getBattingCard().size() == 13) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP$DataAll$BAtting$Batting_Card$12*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP$DataAll$BAtting$Batting_Card$13*CONTAINER SET ACTIVE 1;");
						processAnimation(print_writer, "CON2In", "START", session_selected_broadcaster,2);
					}
					else if(match.getMatch().getInning().get(1).getBattingCard().size() == 13) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP$DataAll$BAtting$Batting_Card$12*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP$DataAll$BAtting$Batting_Card$13*CONTAINER SET ACTIVE 1;");
						processAnimation(print_writer, "CON2In", "START", session_selected_broadcaster,2);
					}
					//processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
					//processAnimation(print_writer, "PointsTableOut", "START", session_selected_broadcaster);
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					processAnimation(print_writer, "BattingCardIn", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");
					//print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "SCORECARD";
					break;
				case "ANIMATE-IN-BOWLINGCARD":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					processAnimation(print_writer, "BowlingCardIn", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");
					//print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "BOWLINGCARD";
					break;
				case "ANIMATE-IN-PARTNERSHIP":
					if(match.getMatch().getInning().get(0).getBattingCard().size() == 12) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$12*CONTAINER SET ACTIVE 1;");
						processAnimation(print_writer, "CON1In", "START", session_selected_broadcaster,2);
					}
					else if(match.getMatch().getInning().get(1).getBattingCard().size() == 12) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$12*CONTAINER SET ACTIVE 1;");
						processAnimation(print_writer, "CON1In", "START", session_selected_broadcaster,2);
					}
					if(match.getMatch().getInning().get(0).getBattingCard().size() == 13) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$13*CONTAINER SET ACTIVE 1;");
						processAnimation(print_writer, "CON2In", "START", session_selected_broadcaster,2);
					}
					else if(match.getMatch().getInning().get(1).getBattingCard().size() == 13) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$13*CONTAINER SET ACTIVE 1;");
						processAnimation(print_writer, "CON2In", "START", session_selected_broadcaster,2);
					}
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "PARTNERSHIP";
					break;
				case "ANIMATE-IN-LTPARTNERSHIP":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "CURRENT_PARTNERSHIP";
					break;
				case "ANIMATE-IN-MATCHSUMARRY":
					//print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*LOOP START;");
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");
					processAnimation(print_writer, "SummaryIn", "START", session_selected_broadcaster,2);
					
					//print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "SUMARRY";
					break;
				case "ANIMATE-IN-SESSION":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "SESSION";
					break;
				case "ANIMATE-IN-TEAMS_LOGO":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "TEAMS_LOGO";
					break;
				case "ANIMATE-IN-SPONSOR":
					processAnimation(print_writer, "Sponsor_In", "START", session_selected_broadcaster,1);
					//which_graphics_onscreen = "SPONSOR";
					break;
				case "ANIMATE-IN-THIS_SESSION":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "THIS_SESSION";
					break;
				case "ANIMATE-IN-BOWLERDETAILS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BOWLERDETAILS";
					break;
				case "ANIMATE-IN-BUG_POWERPLAY":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BUG_POWERPLAY";
					break;
				case "ANIMATE-IN-BUG-TOSS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BUG-TOSS";
					break;
				case "ANIMATE-IN-BUG_PARTNERSHIP":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BUG_PARTNERSHIP";
					break;
				case "ANIMATE-IN-FOUR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "FOUR";
					break;
				case "ANIMATE-IN-SIX":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "SIX";
					break;	
				case "ANIMATE-IN-BUG":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BUG";
					break;
				case "ANIMATE-IN-BUG-DISMISSAL":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BUG-DISMISSAL";
					break;
				case "ANIMATE-IN-BUG-BOWLER":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BUG-BOWLER";
					break;
				case "ANIMATE-IN-HOWOUT":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "HOWOUT";
					break;
				case "ANIMATE-IN-HOWOUT_BOTH":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "HOWOUT_BOTH";
					break;
				case "ANIMATE-IN-HOWOUT_QUICK":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "L3HOWOUT";
					break;
				case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER_QUICK":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "HOWOUT_WITHOUT_FIELDER_QUICK";
					break;
				case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "HOWOUT_WITHOUT_FIELDER";
					break;
				case "ANIMATE-IN-MATCHSTATUS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "MATCHSTATUS";
					break;
				case "ANIMATE-IN-BATSMANSTATS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BATSMANSTATS";
					break;
				case "ANIMATE-IN-BATSMANSTATS_BOTH":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BATSMANSTATS_BOTH";
					break;
				case "ANIMATE-IN-BOWLERSTATS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BOWLERSTATS";
					break;
				case "ANIMATE-IN-BUG-DB":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BUG-DB";
					break;
				case "ANIMATE-IN-NAMESUPER":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "NAMESUPER";
					break;
				case "ANIMATE-IN-NAMESUPER-PLAYER":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "NAMESUPER-PLAYER";
					break;
				case "ANIMATE-IN-PLAYERPROFILE":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "PLAYERPROFILE";
					break;
				case "ANIMATE-IN-DOUBLETEAMS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "DOUBLETEAMS";
					break;
				case "ANIMATE-IN-INFOBAR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
					infobar.setInfobar_on_screen(true);
					which_graphics_onscreen = "INFOBAR";
					break;
				case "ANIMATE-IN-MATCH_PROMO":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCH_PROMO";
					break;
				case "ANIMATE-IN-MATCHID":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCHID";
					break;
				case "ANIMATE-IN-L3MATCHID":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "L3MATCHID";
					break;
				case "ANIMATE-IN-PLAYINGXI":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "PLAYINGXI";
					break;
				case "ANIMATE-IN-LEADERBOARD":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "LEADERBOARD";
					break;
				case "ANIMATE-IN-PROJECTED":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "PROJECTED";
					break;
				case "ANIMATE-IN-TARGET":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "TARGET";
					break;
				case "ANIMATE-IN-FF_TARGET":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "FF_TARGET";
					break;
				case "ANIMATE-IN-FF_EQUATION":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "FF_EQUATION";
					break;
				case "ANIMATE-IN-EQUATION":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "EQUATION";
					break;
				case "ANIMATE-IN-TEAMSUMMARY":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "TEAMSUMMARY";
					break;
				case "ANIMATE-IN-PLAYERSUMMARY":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "PLAYERSUMMARY";
					break;
				case "ANIMATE-IN-L3PLAYERPROFILE":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "L3PLAYERPROFILE";
					break;
				case "ANIMATE-IN-THISSERIES":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "THISSERIES";
					break;
				case "ANIMATE-IN-FALLOFWICKET":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "FALLOFWICKET";
					break;
				case "ANIMATE-IN-SPLIT":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "SPLIT";
					break;
				case "ANIMATE-IN-COMPARISION":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "COMPARISION";
					break;
				case "ANIMATE-IN-BATSMAN_STYLE":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BATSMAN_STYLE";
					break;
				case "ANIMATE-IN-BOWLER_STYLE":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "BOWLER_STYLE";
					break;
				case "ANIMATE-IN-PREVIOUS_SUMMARY":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					processAnimation(print_writer, "SummaryIn", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "PREVIOUS_SUMARRY";
					break;
				case "ANIMATE-IN-TIEID-DOUBLE":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "TIEID-DOUBLE";
					break;
				case "ANIMATE-IN-POINTSTABLE":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					processAnimation(print_writer, "PointsTableIn", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "POINTSTABLE";
					break;
				case "ANIMATE-IN-MOSTRUNS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "MOSTRUNS";
					break;
				case "ANIMATE-IN-MOSTWICKETS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "MOSTWICKETS";
					break;
				case "ANIMATE-IN-MOSTFOURS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "MOSTFOURS";
					break;
				case "ANIMATE-IN-MOSTSIXES":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "MOSTSIXES";
					break;
				case "ANIMATE-IN-HIGHESTSCORE":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "HIGHESTSCORE";
					break;
				case "ANIMATE-IN-GENERIC":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					which_graphics_onscreen = "GENERIC";
					break;
				case "ANIMATE-IN-MANHATTAN":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "MANHATTAN";
					break;
				case "ANIMATE-IN-WORM":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "WORM";
					break;
				case "ANIMATE-IN-THISOVER":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,2);
					print_writer.println("LAYER2*EVEREST*STAGE START;");
					which_graphics_onscreen = "THISOVER";
					break;
				case "TICKER_LT_OUT":
					if(!which_graphics_onscreen.isEmpty() && which_graphics_onscreen != "INFOBAR") {
						AnimateOutGraphics(print_writer, which_graphics_onscreen);
						TimeUnit.SECONDS.sleep(1);
					}
					populateInfobar(infobar, print_writer, valueToProcess.split(",")[0],match, session_selected_broadcaster);
					AnimateOutGraphics(print_writer, "FF_OUT");
					TimeUnit.SECONDS.sleep(1);
					which_graphics_onscreen = "INFOBAR";
					infobar.setInfobar_on_screen(true);
					//AnimateOutGraphics(print_writer, which_graphic_on_screen);
					break;
				case "TICKER_LT_IN":
					AnimateInGraphics(print_writer, "FF_IN");
					TimeUnit.SECONDS.sleep(1);
					if(which_graphics_onscreen != "INFOBAR") {
						AnimateOutGraphics(print_writer, which_graphics_onscreen);
					}
					break;
				case "CLEAR-ALL":
					print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
					which_graphics_onscreen = "";
					break;
				case "ANIMATE-OUT-SPONSOR":
					processAnimation(print_writer, "Sponsor_Out", "START", session_selected_broadcaster,1);
					break;
				case "ANIMATE-OUT":
					switch(which_graphics_onscreen) {
					case "INFOBAR":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,1);
						which_graphics_onscreen = "";
						infobar.setInfobar_on_screen(false);
						break;
					
					case "SCORECARD": 
						processAnimation(print_writer, "BattingCardOut", "START", session_selected_broadcaster,2);
						TimeUnit.SECONDS.sleep(1);
						
						//processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster);
						
						if(infobar.isInfobar_on_screen() == true) {
							processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster,1);
							//processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
							which_graphics_onscreen = "INFOBAR";
						}
						break;
					case "BOWLINGCARD":
						processAnimation(print_writer, "BowlingCardOut", "START", session_selected_broadcaster,2);
						TimeUnit.SECONDS.sleep(1);
						
						//processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster);
						
						if(infobar.isInfobar_on_screen() == true) {
							processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster,1);
							which_graphics_onscreen = "INFOBAR";
						}
						break;
					case "SUMARRY": case "PREVIOUS_SUMARRY":
						processAnimation(print_writer, "SummaryOut", "START", session_selected_broadcaster,2);
						TimeUnit.SECONDS.sleep(1);
						
						//processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster);
						
						if(infobar.isInfobar_on_screen() == true) {
							processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster,1);
							which_graphics_onscreen = "INFOBAR";
						}
						break;
					case "POINTSTABLE":
						processAnimation(print_writer, "PointsTableOut", "START", session_selected_broadcaster,2);
						TimeUnit.SECONDS.sleep(1);
						
						//processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster);
						
						if(infobar.isInfobar_on_screen() == true) {
							processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster,1);
							which_graphics_onscreen = "INFOBAR";
						}
						break;
					case "SIX": case "FOUR":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,2);
						if(infobar.isInfobar_on_screen() == true) {
//							processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster,1);
							which_graphics_onscreen = "INFOBAR";
						}
						
						break;
					case "BUG": case "HOWOUT": case "BATSMANSTATS": case "BOWLERSTATS": case "BUG-DB": case "NAMESUPER": case "NAMESUPER-PLAYER": case "DOUBLETEAMS": 
					case "MATCHID": case "L3MATCHID": case "PLAYINGXI": case "TARGET": case "TEAMSUMMARY": case "EQUATION":case "PLAYERSUMMARY": case "L3PLAYERPROFILE": 
					case "FALLOFWICKET": case "SPLIT": case "COMPARISION": case "BUG-DISMISSAL": case "HOWOUT_WITHOUT_FIELDER": case "BATSMAN_STYLE": case "BUG-BOWLER": 
					case "MATCH_PROMO": case "TEAMS_LOGO": case "BOWLER_STYLE": case "TIEID-DOUBLE": case "GENERIC": case "MOSTRUNS": case "MOSTWICKETS": 
					case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "MANHATTAN": case "PARTNERSHIP": case "PROJECTED": case "FF_TARGET": case "THISOVER":
					case "L3HOWOUT": case "CURRENT_PARTNERSHIP": case "WORM": case "PLAYERPROFILE": case "MATCHSTATUS": case "HOWOUT_BOTH": case "BATSMANSTATS_BOTH":
					case "THIS_SESSION": case "SESSION": case "FF_EQUATION": case "BUG-TOSS": case "BOWLERDETAILS": case "HOWOUT_WITHOUT_FIELDER_QUICK":
					case "BUG_POWERPLAY": case "BUG_PARTNERSHIP": case "LEADERBOARD": case "THISSERIES": 
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,2);
						TimeUnit.SECONDS.sleep(1);
						print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
						
						
						//processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster);
						
						if(infobar.isInfobar_on_screen() == true) {
							processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster,1);
							which_graphics_onscreen = "INFOBAR";
						}
						
						break;
						
//					 case "LEADERBOARD":
//						//processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
//						TimeUnit.SECONDS.sleep(2);
//						if(infobar.isInfobar_on_screen() == true) {
//							processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster,1);
//							which_graphics_onscreen = "INFOBAR";
//						}
//						
//						break;
					}
					break;
				}
				//return new ObjectMapper().writeValueAsString(this_doad).toString();
			}
		}
		return null;
}
	
	public void populateScorecard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Scorecard's inning is null";
			} else {
				int row_id = 0 ;				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP*CONTAINER SET ACTIVE 1;");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$PointsTable_GRP*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBatSponsorImage " + sponsor_logo_path + "SCCL" + 
						CricketUtil.PNG_EXTENSION + ";");
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
//						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getTeamName3().toUpperCase()+ ";");
						
						if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
							if(whichInning == 1 || whichInning == 2) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatInnings " + "1st INNINGS" + ";");
							}else if(whichInning == 3 || whichInning == 4) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatInnings " + "2nd INNINGS" + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + " v " + inn.getBowling_team().getTeamName3().toUpperCase() + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getTeamName3().toUpperCase() + " v " + inn.getBowling_team().getTeamName3().toUpperCase() + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getMatchIdent() + ";");
						}
			
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBatTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");						
						//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBatSponsorImage " + logo_path + "Sponsor\\" + "Dafa_News" + CricketUtil.PNG_EXTENSION + ";");

						Collections.sort(inn.getBattingCard());
						for (BattingCard bc : inn.getBattingCard()) {
						
							row_id = row_id + 1;

							switch (bc.getStatus().toUpperCase()) {
	
							case CricketUtil.OUT:
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$Out*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$NotOut*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$HowOut_GRP*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$Runs*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$Balls*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatPlayerName0" +row_id + " " +bc.getPlayer().getTicker_name() + ";");
								
								if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBatsman12Visibility " + "1" + ";");
								}
								else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.HIT_WICKET)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)){
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutText() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne() + " (sub - " + bc.getHowOutPartTwo().split(" ")[0] + " )" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
									}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
											CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne().replace("(SUB)", "") + " (" + bc.getHowOutPartTwo().split(" ")[0] + " )" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
									
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutText() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
									}
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED) || bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)){
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + " " + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + bc.getHowOutText() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								}
								else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + bc.getHowOutPartTwo() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
									
								}
							break;
	
							case CricketUtil.NOT_OUT:
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$Out*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$NotOut*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatPlayerName0" +row_id + " " +bc.getPlayer().getTicker_name() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "2" + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getStatus() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								
							break;
							
							case CricketUtil.STILL_TO_BAT:
							
								if(bc.getHowOut() == null) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatPlayerName0" + row_id + " " + bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "0" + ";");
								}
								else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$Out*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$NotOut*CONTAINER SET ACTIVE 0;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$HowOut_GRP*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$Runs*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+row_id+"$Balls*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatPlayerName0" +row_id + " " +bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + "retired hurt" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								}
								
								break;
						}
					}
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatExtras " + inn.getTotalExtras() + ";");
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatOvers "+ CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + ";");
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunRate " + inn.getRunRate() + ";");

					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatScore " + inn.getTotalRuns() + ";");
					} else {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatScore " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + ";");
				}
			}
		}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*BattingCardIn SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*BattingCardOut SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*BattingCardOut SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*BattingCardIn SHOW 0.0;");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene,boolean is_this_updating, int whichInning,   MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bowlingcard's inning is null";
			} else {
				//String commands = "";
				int row_id = 0, row = 1, max_Strap = 9,len = 1;
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$PointsTable_GRP*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBallSponsorImage " + sponsor_logo_path + "SCI" + 
						CricketUtil.PNG_EXTENSION + ";");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
//						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + inn.getBowling_team().getTeamName3().toUpperCase() + ";");
						
						if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
							if(whichInning == 1 || whichInning == 2) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlInnings " + "1st INNINGS" + ";");
							}else if(whichInning == 3 || whichInning == 4) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlInnings " + "2nd INNINGS" + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallSubHeader " + " v " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + inn.getBowling_team().getTeamName3().toUpperCase() + " v " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getMatchIdent() + ";");
						}
						
						
						//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallSubHeader " + "v " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBallTeamLogo " + logo_path + inn.getBowling_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBallSponsorImage " + logo_path + "Sponsor\\" + "Dafa_News" + CricketUtil.PNG_EXTENSION + ";");

						for (BowlingCard boc : inn.getBowlingCard()) {
							
							row_id = row_id + 1;
							row = row + 1;
							
							if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
								len = len + 1;
//								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP$DataAll$BAtting$Bowling_Card$" + len + "*CONTAINER SET ACTIVE 1;");

//								for(int j = len + 1; j <= max_Strap; j++) {
//									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP$DataAll$BAtting$Bowling_Card$" + j + "*CONTAINER SET ACTIVE 1;");
//								}
							}
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*"+ row + "*CONTAINER SET ACTIVE 1;");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallPlayerName0" + row_id + " "  + boc.getPlayer().getTicker_name() + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallStatValue" + row + "A " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) +";");
							if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10) 
									|| match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallStatHeadB " +"DOTS" +";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallStatValue" + row + "B " + boc.getDots() +";");
							}
							else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallStatHeadB " +"MAIDENS" +";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallStatValue" + row + "B " + boc.getMaidens() +";");
							}
							
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallStatValue" + row + "C " + boc.getRuns() +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallStatValue" + row + "D " + boc.getWickets() + ";");
							
							if(boc.getEconomyRate() == null) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallStatValue" + row + "E " + "-" + ";");
							}else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallStatValue" + row + "E " + boc.getEconomyRate() + ";");
							}
							
							
						}
						
						  for(int j = len + 1; j <= max_Strap; j++) { 
							  print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP$DataAll$BAtting$Bowling_Card$"
						  + j + "*CONTAINER SET ACTIVE 0;"); 
						  }
						 
						row_id= 0 ;
						if(inn.getBowlingCard().size()<=8) {
							if(is_this_updating == false) {
								if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP$DataAll$BAtting$Bowling_Card$FOWGRP*CONTAINER SET ACTIVE 0;");
									//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$DataAll$BAtting$Bowling_Card$10*CONTAINER SET ACTIVE 0;");
									//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$DataAll$BAtting$Bowling_Card$11*CONTAINER SET ACTIVE 0;");
								}
								else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
									for(FallOfWicket fow : inn.getFallsOfWickets()) {								
										if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
											row_id = row_id + 1;
											
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP$DataAll$BAtting$Bowling_Card$FOWGRP*CONTAINER SET ACTIVE 1;");
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vWKTS "+ fow.getFowNumber() + ";");
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tWKTRun0" + row_id + " "  + fow.getFowRuns() + ";");
										}		
									}
								}
							}
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP$DataAll$BAtting$Bowling_Card$FOWGRP*CONTAINER SET ACTIVE 0;");
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallExtras " + inn.getTotalExtras() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunRate "+ inn.getRunRate() + ";");
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallScore " + inn.getTotalRuns() + ";");
						}
						else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallScore " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + ";");
						}
					}
				}
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL PREVIEW ON;";
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*BowlingCardIn SHOW 196.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*BowlingCardIn SHOW 196.0;");
				
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*BowlingCardOut SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*BowlingCardOut SHOW 0.0;");
				
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;";
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;";
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*BowlingCardOut SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*BowlingCardOut SHOW 0.0;");
				
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*BowlingCardIn SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*BowlingCardIn SHOW 0.0;");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL PREVIEW OFF;";
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				//print_writer.println("LAYER1*EVEREST*GLOBAL TEMPLATE_SAVE " + " C:/Temp/Bowlingcard.txt" + ";");
				
				//CricketFunctions.whenWriteStringUsingBufferedWritter_thenCorrect(commands);
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populatePartnership(PrintWriter print_writer, String viz_scene, int whichInning,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership's inning is null";
			} else {
				
				int row_id = 0, omo_num = 0,Top_Score = 50;
				float Mult = 100, ScaleFac1 = 0, ScaleFac2 = 0;
				String Left_Batsman = "",Right_Batsman="";
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == whichInning) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + " v " + inn.getBowling_team().getTeamName1().toUpperCase() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getMatchIdent().toUpperCase() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						for(int a = 1; a <= inn.getPartnerships().size(); a++){
							ScaleFac1=0;ScaleFac2=0;
							if(inn.getPartnerships().get(a-1).getFirstBatterRuns() > Top_Score) {
								Top_Score = inn.getPartnerships().get(a-1).getFirstBatterRuns();
							}
							if(inn.getPartnerships().get(a-1).getSecondBatterRuns() > Top_Score) {
								Top_Score = inn.getPartnerships().get(a-1).getSecondBatterRuns();
							}
						}
						
						for(Partnership ps : inn.getPartnerships()) {
							row_id = row_id + 1;
							Left_Batsman="" ; Right_Batsman="";
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId() == ps.getFirstBatterNo()) {
									Left_Batsman = bc.getPlayer().getTicker_name();
								}
								else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
									Right_Batsman = bc.getPlayer().getTicker_name();
								}
							}
							
							if(inn.getPartnerships().size() >= 10) {
								if(ps.getPartnershipNumber()<=inn.getPartnerships().size()) {
									omo_num = 2;
								}
							}
							else {
								if(ps.getPartnershipNumber()<inn.getPartnerships().size()) {
									omo_num = 2;
								}
								else if(ps.getPartnershipNumber() >= inn.getPartnerships().size()) {
									omo_num = 3;
								}
							}
							
							ScaleFac1 = ((ps.getFirstBatterRuns())*(Mult/Top_Score)) ;
							ScaleFac2 = ((ps.getSecondBatterRuns())*(Mult/Top_Score)) ;
							
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectionOmo" + row_id + " " + omo_num + ";");
							
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLeftPlayerName" + row_id + " " + Left_Batsman + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRightPlayerName" + row_id + " " + Right_Batsman + ";");
							
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLeftBar" + row_id + " " + ScaleFac1 + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRightBar" + row_id + " " + ScaleFac2 + ";");
							
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipRun" + row_id + " " + ps.getTotalRuns() + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipBall" + row_id + " " + ps.getTotalBalls() + ";");
								
						}
						
						if(inn.getPartnerships().size() >= 10) {
							row_id = row_id + 1;
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectionOmo" + row_id + " " + "4" + ";");
						}
						else {
							for (BattingCard bc : inn.getBattingCard()) {
								if(row_id < inn.getBattingCard().size()) {
									if(row_id == inn.getPartnerships().size()) {
										row_id = row_id + 1;
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectionOmo" + row_id + " " + "0" + ";");
										if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
											if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.START) ) {
												print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDidNotBat" + row_id + " " + "STILL TO BAT" + ";");
											}else {
												print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDidNotBat" + row_id + " " + "DID NOT BAT" + ";");
											}
										}else {
											if(inn.getTotalOvers() == match.getSetup().getMaxOvers() || inn.getTotalWickets() >= 10 ) {
												print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDidNotBat" + row_id + " " + "DID NOT BAT" + ";");
											}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || CricketFunctions.getWicketsLeft(match,whichInning) <= 0) {
												print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDidNotBat" + row_id + " " + "DID NOT BAT" + ";");
											}else {
												print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDidNotBat" + row_id + " " + "STILL TO BAT" + ";");
											}
										}
									}
									else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
										row_id = row_id + 1;
										//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDidNotBat" + row_id + " " + "DID NOT BAT" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectionOmo" + row_id + " " + "1" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLeftPlayerName" + row_id + " " + bc.getPlayer().getTicker_name() + ";");
									}	
								}
								else {
									break;
								}
							}
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras " + inn.getTotalExtras() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers "+ CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + ";");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunRate " + inn.getRunRate() + ";");

						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						} else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + ";");
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning,List<VariousText> vt ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Match Summary's inning is null";
			} else {
				//String path = "D:\\Everest_Scenes\\Mumbai_Indians\\Textures and Images\\Logos\\" ;
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$PointsTable_GRP*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumTeamALastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumTeamBLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				
				//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getMatchIdent().toUpperCase() +  ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumTeamName " + match.getSetup().getMatchIdent().toUpperCase() + " - SUMMARY" + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumSubHeader " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
						" vs " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBatTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + " " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$HeaderGrp$BigBandGrp$Sponsor*CONTAINER SET ACTIVE 0;");
				//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSumSponsorImage " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");

				int row_id = 0, row = 0, total_inn = 0, max_Strap = 0;
				String teamname = ""; 
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				/*for(int i = 1; i <= whichInning ; i++) {
					if(i == 1) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$SecondInnings*CONTAINER SET ACTIVE 0;");
					}
					else {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$SecondInnings*CONTAINER SET ACTIVE 1;");
					}
				}*/
				
				if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20)||match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.ODI)||
						match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)||match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10)) {
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInnings " + "0" + ";");
				}
				else if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
					if(whichInning == 2) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInnings " + "0" + ";");
					}else if(whichInning == 3) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInnings " + "1" + ";");
					}else if (whichInning == 4) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInnings " + "2" + ";");
					}
					
				}
				
				for(int i = 1; i <= whichInning ; i++) {

					if(whichInning == 2) {
						if(i == 1) {
							row = 0;
							row_id = 0;
							max_Strap = 3;
						}else {
							row = 1;
							row_id = 3;
							max_Strap = 6;
						}
					}else if(whichInning == 3) {
						if(i == 1) {
							row = 0;
							row_id = 0;
							max_Strap = 2;
						} else if(i==2) {
							row = 1;
							row_id = 2;
							max_Strap = 4;
						}else if(i==3) {
							row = 2;
							row_id = 4;
							max_Strap = 6;
						}
					}else if (whichInning == 4) {
						if(i == 1) {
							row = 0;
							row_id = 0;
							max_Strap = 1;
						} else if(i==2) {
							row = 1;
							row_id = 1;
							max_Strap = 2;
						}else if(i==3) {
							row = 2;
							row_id = 2;
							max_Strap = 3;
						}else if(i==4) {
							row = 3;
							row_id = 3;
							max_Strap = 4;
						}
					}
					
					row = row + 1;
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						teamname = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();	
					} else {
						teamname = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
					}
					
					if(!match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
						if(match.getSetup().getTossWinningTeam() == match.getMatch().getInning().get(0).getBattingTeamId()) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHomeToss 1;");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAwayToss 0;");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$FirstInnings$1st$TossGrp$TOss*CONTAINER SET ACTIVE 1;");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$SecondInnings$5$TossGrp$TOss*CONTAINER SET ACTIVE 0;");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnOrToss1 " + "TOSS" + ";");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInningHead2 " + "" + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHomeToss 0;");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAwayToss 1;");
							
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$FirstInnings$1st$TossGrp$TOss*CONTAINER SET ACTIVE 0;");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$SecondInnings$5$TossGrp$TOss*CONTAINER SET ACTIVE 1;");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnOrToss2 " + "TOSS" + ";");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInningHead1 " + "" + ";");
						}
					}
					
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumTeamName0"+ row +" "+ teamname + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumOverNo0"+ row + " " + 
							CricketFunctions.OverBalls(match.getMatch().getInning().get(i-1).getTotalOvers(),match.getMatch().getInning().get(i-1).getTotalBalls()) + ";");
					
					if(match.getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumScore0"+ row + " " + match.getMatch().getInning().get(i-1).getTotalRuns() + ";");
					}
					else {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumScore0"+ row + " " + match.getMatch().getInning().get(i-1).getTotalRuns() 
								+ slashOrDash + match.getMatch().getInning().get(i-1).getTotalWickets() + ";");	
					}
					if(match.getMatch().getInning().get(i-1).getBattingCard() != null) {
						//row_id = 0;
						Collections.sort(match.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
						for(BattingCard bc : match.getMatch().getInning().get(i-1).getBattingCard()) {
							if(bc.getRuns() > 0) {
								if(bc.getBatsmanInningStarted() != null && bc.getBatsmanInningStarted().equalsIgnoreCase(CricketUtil.YES) && bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Batsman0" + row_id + "*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanName0"+ row_id + " " + bc.getPlayer().getTicker_name() + " (rh)" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanRuns0"+ row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanBalls0"+ row_id + " " + bc.getBalls() + ";");
								}
								if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Batsman0"+ row_id + "*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanName0"+ row_id + " " + bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanRuns0"+ row_id + " " + bc.getRuns() + ";");
									
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanNotOut0"+ row_id + " " + "*" + ";");
									} 
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanNotOut0"+ row_id + " " + " " + ";");
									} 
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanBalls0"+ row_id + " " + bc.getBalls() + ";");
									
									if(whichInning == 2) {
										if(i == 1 && row_id >= 3) {
											break;
										}else if(i == 4 && row_id >= 6) {
											break;
										}
									}else if(whichInning == 3) {
										if(i == 1 && row_id >= 2) {
											break;
										}else if(i == 2 && row_id >= 4) {
											break;
										}
										else if(i == 4 && row_id >= 6) {
											break;
										}
									}else if(whichInning == 4) {
										if(i == 1 && row_id >= 1) {
											break;
										}else if(i == 2 && row_id >= 2) {
											break;
										}
										else if(i == 3 && row_id >= 3) {
											break;
										}else if(i == 4 && row_id >= 4) {
											break;
										}
									}
								}
							}
						}
					}

					for(int j = row_id + 1; j <= max_Strap; j++) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Batsman0" + j + "*CONTAINER SET ACTIVE 0;");
					}
					
					
					if(whichInning == 2) {
						if(i == 1) {
							row_id = 0;
						}
						else if(i==2){
							row_id = 3;
						}
					}else if(whichInning == 3) {
						if(i == 1) {
							row_id = 0;
						}
						else if(i==2){
							row_id = 2;
						}
						else if(i==3){
							row_id = 4;
						}
					}else if (whichInning == 4) {
						if(i == 1) {
							row_id = 0;
						}
						else if(i==2){
							row_id = 1;
						}
						else if(i==3){
							row_id = 2;
						}
						else if(i==4){
							row_id = 3;
						}
					}

					if(match.getMatch().getInning().get(i-1).getBowlingCard() != null) {
						//row_id = 0;
						Collections.sort(match.getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

						for(BowlingCard boc : match.getMatch().getInning().get(i-1).getBowlingCard()) {
							if(boc.getWickets() > 0 ) {
								row_id = row_id + 1;
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Bowler0" + row_id + "*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBowlerName0" + row_id + " " +  boc.getPlayer().getTicker_name() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBowlerRuns0" + row_id + " " + boc.getWickets() + slashOrDash + boc.getRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBowlerOver0" + row_id + " " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
								
								if(whichInning == 2) {
									if(i == 1 && row_id >= 3) {
										break;
									}else if(i == 4 && row_id >= 6) {
										break;
									}
								}else if(whichInning == 3) {
									if(i == 1 && row_id >= 2) {
										break;
									}else if(i == 2 && row_id >= 4) {
										break;
									}
									else if(i == 4 && row_id >= 6) {
										break;
									}
								}else if(whichInning == 4) {
									if(i == 1 && row_id >= 1) {
										break;
									}else if(i == 2 && row_id >= 2) {
										break;
									}
									else if(i == 3 && row_id >= 3) {
										break;
									}else if(i == 4 && row_id >= 4) {
										break;
									}
								} 
							}
						}
					}
					
					for(int j = row_id + 1; j <= max_Strap; j++) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBowlerName0" + j + "" + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBowlerRuns0" + j + "" + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBowlerOver0" + j + "" + ";");

						/*if(whichInning == 3) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Main$Third$Thirdnnings$group$10$Fifth$Ball$Bowler0" + j + "*CONTAINER SET ACTIVE 0;");
						}else if(whichInning == 4) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Main$Fourth$Thirdnnings$group$10$Fifth$Ball$Bowler0" + j + "*CONTAINER SET ACTIVE 0;");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Bowler0" + j + "*CONTAINER SET ACTIVE 0;");
						}*/
					}
				}
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + 
						//CricketFunctions.generateMatchSummaryStatus(2, match, CricketUtil.FULL).toUpperCase() + ";");
				
				for(VariousText vartext : vt) {
					if(vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + 
								vartext.getVariousText() + ";");
						}else if(vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
				
							if(match.getMatch().getMatchResult() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + 
											CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
								}
								else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + "MATCH TIED" + ";");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + ";");
								}
								else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + 
											CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
								}
							}
							else {
								if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + 
											CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
								}else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + 
											CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + " (" + 
												match.getSetup().getTargetType().toUpperCase() + ")" + ";");
								}
							}
						}
					}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*SummaryIn SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*SummaryOut SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*SummaryOut SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*SummaryIn SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			
			}
			break;
		}
		
	}
	public void populateTeamsLogo(PrintWriter print_writer,String viz_scene, List<Team> teams ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "TEAMS" +";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
		
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBatTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
		
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getMatchIdent().toUpperCase() + ";");
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getMatchIdent().toUpperCase() + ";");
//
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupATeamLogo01 " + logo_path + teams.get(0).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupATeamLogo02 " + logo_path + teams.get(1).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupATeamLogo03 " + logo_path + teams.get(2).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupBTeamLogo01 " + logo_path + teams.get(3).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupBTeamLogo02 " + logo_path + teams.get(4).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupBTeamLogo03 " + logo_path + teams.get(5).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupCTeamLogo01 " + logo_path + teams.get(6).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupCTeamLogo02 " + logo_path + teams.get(7).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupCTeamLogo03 " + logo_path + teams.get(8).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupDTeamLogo01 " + logo_path + teams.get(9).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupDTeamLogo02 " + logo_path + teams.get(10).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
//		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgGroupDTeamLogo03 " + logo_path + teams.get(11).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		this.status = CricketUtil.SUCCESSFUL;	
	}
	public void populateBugToss(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + " WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");

				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + " WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");

				}
				
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + "" + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + "" + ";");	
					
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBugDismissal(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bc.getPlayer().getTicker_name() + ";");
									if(bc.getHowOutText() == null) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + " " + ";");
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + bc.getHowOutText() + ";");
									}
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + bc.getBalls() + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBug(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "4s:" + bc.getFours()  + " 6s:"  + bc.getSixes() + ";");
									
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + bc.getRuns() + "*" + ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + bc.getRuns() + ";");
									}
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + bc.getBalls() + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}	
	public void populateBugBowler(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BOWLER :
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==playerId) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + boc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + " " + ";");
								
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + boc.getWickets() + slashOrDash + boc.getRuns() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
								}
							}
							break;
						}
							
					}
						
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateBugPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
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
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + " & " + Right_Batsman + ";");
						if(inn.getTotalWickets() == 0) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + ";");
						}else if(inn.getTotalWickets() == 1) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + ";");
						}else if(inn.getTotalWickets() == 2) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + ";");
						}
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ";");
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateHowout(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: HowOut's inning is null";
			} else {
				//String commands = "";
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								//commands = commands + "LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION  + ";";
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION  + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
										CricketUtil.PNG_EXTENSION + ";");
								//commands = commands + "\n" + "LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name().toUpperCase() + ";";
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
								
								//commands = commands + "\n" + "LAYER2*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;";
								print_writer.println("LAYER2*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
								
								//commands = commands + "\n" + "LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns " + bc.getRuns() + ";";
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns " + bc.getRuns() + ";");
								
								//commands = commands + "\n" + "LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls " + bc.getBalls() + ";";
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls " + bc.getBalls() + ";");
								
								if (bc.getHowOutPartOne().trim().equalsIgnoreCase("")){
									
									//commands = commands + "\n" + "LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartTwo() + ";";
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartTwo() + ";");
									
									//commands = commands + "\n" + "LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";";
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
								
								}else {
									if(bc.getHowOut().equalsIgnoreCase(CricketUtil.HIT_WICKET)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartOne() + ";");
										
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
									}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartOne() + " (sub - " + bc.getHowOutPartTwo().split(" ")[0] + " )" + ";");
											
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
										}else {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutText() + ";");
											
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
										}
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartOne() + ";");
										
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + bc.getHowOutPartTwo() + ";");
									}
								}
								
								//commands = commands + "\n" + "LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";";
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";");
								
								//commands = commands + "\n" + "LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";";
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";");
								
								//commands = commands + "\n" + "LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";";
								if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$FOW_GRP*CONTAINER SET ACTIVE 1;");
									for(int i=0;i<=inn.getFallsOfWickets().size()-1;i++) {
										if(inn.getFallsOfWickets().get(i).getFowPlayerID() == playerId) {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWValue " + inn.getFallsOfWickets().get(i).getFowRuns() + 
													slashOrDash + inn.getFallsOfWickets().get(i).getFowNumber() + ";");
										}
									}
								}else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$FOW_GRP*CONTAINER SET ACTIVE 0;");
								}
								//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "S/R" + ";");
								if(bc.getStrikeRate() == null) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + "-" + ";");
								}else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";");
								}
							}
						}
					}
				}
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL PREVIEW ON;";
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;" ; 
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;";
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;";
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL PREVIEW OFF;";
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				//CricketFunctions.whenWriteStringUsingBufferedWritter_thenCorrect(commands);
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateHowoutBoth(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: HowOut's inning is null";
			} else {
				int row = 0 ; 
				for(Inning inn : match.getMatch().getInning()) {
					//for (int i = 1; i <= whichInning; i++) {
					//if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								row = row + 1 ;
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION  + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
										CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
								
								//print_writer.println("LAYER2*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
								
								//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns " + bc.getRuns() + ";");
								
								//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls " + bc.getBalls() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0" + row + " " + bc.getHowOutText() + ";");

								/*if (bc.getHowOutPartOne().trim().equalsIgnoreCase("")){
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0" + row + " " + bc.getHowOutText() + ";");
									
									//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
								
								}else {
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut01 " + bc.getHowOutText() + ";");
									
									//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + bc.getHowOutPartTwo() + ";");
								}*/
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns0" + row + " " + bc.getRuns() + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row + " " + bc.getBalls() + ";");
								
								//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";");
								
								//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";");
								
								/*if(match.getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWValue " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
								}*/
								//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "S/R" + ";");

								//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";");
							}
						}
					//}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
								
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateL3Howout(PrintWriter print_writer,String viz_scene,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: HowOut's inning is null";
			} else {
				
				//String commands = "";
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION  + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						for(BattingCard bc : inn.getBattingCard()) {
							if(inn.getFallsOfWickets().size() > 0) {
								if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
									TimeUnit.MILLISECONDS.sleep(250);
									print_writer.println("LAYER2*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
//									TimeUnit.MILLISECONDS.sleep(200);
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns " + bc.getRuns() + ";");
									TimeUnit.MILLISECONDS.sleep(250);
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls " + bc.getBalls() + ";");
									TimeUnit.MILLISECONDS.sleep(250);
									
									if (bc.getHowOutPartOne().trim().equalsIgnoreCase("")){
										
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartTwo() + ";");
										
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
									
									}else {
										if(bc.getHowOut().equalsIgnoreCase(CricketUtil.HIT_WICKET)) {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartOne() + ";");
											
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
										}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)) {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutText() + ";");
											
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
										}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
											if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartOne() + " (sub - " + bc.getHowOutPartTwo().split(" ")[0] + " )" + ";");
												
												print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
											}else {
												print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutText() + ";");
												
												print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
											}
										}else {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartOne() + ";");
											
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + bc.getHowOutPartTwo() + ";");
										}
									}
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";");
									
									if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$FOW_GRP*CONTAINER SET ACTIVE 1;");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWValue " + 
												inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowRuns() + slashOrDash + 
												inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowNumber() + ";");
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$FOW_GRP*CONTAINER SET ACTIVE 0;");
									}
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "S/R" + ";");
									
									if(bc.getStrikeRate() == null) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + "-" + ";");
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";");
									}
								}
							}
						}
					}
				}
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL PREVIEW ON;";
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;" ; 
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;";
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;";
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				//commands = commands + "\n" + "LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;";
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				//commands = commands + "\n" + "LAYER1*EVEREST*GLOBAL PREVIEW OFF;";
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				//CricketFunctions.whenWriteStringUsingBufferedWritter_thenCorrect(commands);
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateHowoutWithoutFielder(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: HowOut's inning is null";
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION  + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
										CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns " + bc.getRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls " + ( bc.getBalls() + 1 ) + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + " " + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
								
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "FOURS " + ";");
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "SIXES " + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWValue " + "" + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ttFOWHead " + "" + ";");

								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "S/R" + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";");
							}
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateL3HowoutWithoutFielder(PrintWriter print_writer,String viz_scene,MatchAllData match, String session_selected_broadcaster) throws InterruptedException, IOException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: HowOut's inning is null";
			} else {
				
				//String commands = "";
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION  + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						for(BattingCard bc : inn.getBattingCard()) {
							if(inn.getFallsOfWickets().size() > 0) {
								if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRuns " + bc.getRuns() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerBalls " + bc.getBalls() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + " " + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";");
									
									if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$FOW_GRP*CONTAINER SET ACTIVE 1;");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFOWValue " + 
												inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowRuns() + slashOrDash + 
												inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowNumber() + ";");
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$FOW_GRP*CONTAINER SET ACTIVE 0;");
									}
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "S/R" + ";");
									
									if(bc.getStrikeRate() == null) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + "-" + ";");
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";");
									}
								}
							}
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBatsmanstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: PlayerStats's inning is null";
			} else {
				int total_inn = 0;
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
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
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, playerId,",", match.getEventFile().getEvents()).split(",");
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + bc.getPlayer().getFull_name() + ";");
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRuns " + bc.getRuns() + "*" + ";");
									}
									else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRuns " + bc.getRuns() + ";");
									}
									if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
									} else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
									}
									if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)){
										if(inn.getInningNumber() == 1 || inn.getInningNumber() == 2) {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnings " + "1st INNINGS" + ";");
										}else if(inn.getInningNumber() == 3 || inn.getInningNumber() == 4) {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnings " + "2nd INNINGS" + ";");
										}
										//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + bc.getBalls() + ";");
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnings " + "" + ";");
									}
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + bc.getBalls() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + Count[0] + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + bc.getStrikeRate() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + bc.getFours() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + bc.getSixes() + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateBatsmanstatsBoth(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: PlayerStats's inning is null";
			} else {
				int row = 0 ; 
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				for(Inning inn : match.getMatch().getInning()) {
					//for (int i = 1; i <= whichInning; i++) {
					//if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								row = row + 1 ;
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION  + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + bc.getPlayer().getFull_name() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "RUNS" + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "BALLS" + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValueA" + row + " " + bc.getRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValueB" + row + " " + bc.getBalls() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValueC" + row + " " + bc.getStrikeRate() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValueD" + row + " " + bc.getFours() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValueE" + row + " " + bc.getSixes() + ";");
							}
						}
					//}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateBowlerstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId,List<Team> team, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: PlayerStats's inning is null";
			} else {
				int total_inn = 0;
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
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
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + team.get(boc.getPlayer().
											getTeamId() - 1).getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + boc.getPlayer().getFull_name() + ";");
									if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)){
										if(inn.getInningNumber() == 1 || inn.getInningNumber() == 2) {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnings " + "1st INNINGS" + ";");
										}else if(inn.getInningNumber() == 3 || inn.getInningNumber() == 4) {
											print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnings " + "2nd INNINGS" + ";");
										}
										//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + bc.getBalls() + ";");
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnings " + "" + ";");
									}
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "OVERS" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
									if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.ODI)){
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "MAIDENS" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + boc.getMaidens() + ";");
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "DOTS" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + boc.getDots() + ";");
									}
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "RUNS" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + boc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "WICKETS" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + boc.getWickets() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead4 " + "ECON" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + boc.getEconomyRate() + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateBugsDB(PrintWriter print_writer,String viz_scene, Bugs bug ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				if(bug.getText1() != null && bug.getText2() != null) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bug.getText1().toUpperCase() +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + " " +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + " " +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + bug.getText2().toUpperCase() +";");
				}else if(bug.getText1() != null) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bug.getText1().toUpperCase() +";");

					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + " " +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + " " +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + " " +";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bug.getText2().toUpperCase() +";");
					

					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + " " +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + " " +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + " " +";");
					
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateBugPowerPLay(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					print_writer.println(
							"LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "POWERPLAY" + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
							+ CricketFunctions.getPowerPlayScore(inn, whichInning,"-", match) + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + "" + ";");

					if (whichInning == 1) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamName3() + ";");
					} else {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + ";");
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateNameSuper(PrintWriter print_writer,String viz_scene, NameSuper ns ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				if(ns.getSponsor() == null) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "Sponsor\\" + ns.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
				}
				
				if(ns.getFirstname() == null) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + ns.getSurname().toUpperCase() +";");
				}
				else if(ns.getSurname() == null) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + ns.getFirstname().toUpperCase() +";");
				}
				else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + ns.getFirstname().toUpperCase() + " " 
							+ ns.getSurname().toUpperCase() +";");
				}
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + ns.getSubLine().toUpperCase() + ";");
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				//print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				//print_writer.println("LAYER1*EVEREST*STAGE*DIRE CTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}	
	public void populateNameSuperPlayer(PrintWriter print_writer,String viz_scene, int TeamId, String captainWicketKeeper, int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				String Home_or_Away="";
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				if(TeamId == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getHomeTeam().getTeamName4() + 
							CricketUtil.PNG_EXTENSION + ";");
					Home_or_Away = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
					for(Player hs : match.getSetup().getHomeSquad()) {
						if(playerId == hs.getPlayerId()) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + hs.getFull_name() +";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + hs.getSurname() +";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 " + match.getHomeTeam().getFullname() + ";");
							if(captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
							}
						}
						
					}
				}
				else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName4() + 
							CricketUtil.PNG_EXTENSION + ";");
					Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
					for(Player as : match.getSetup().getAwaySquad()) {
						if(playerId == as.getPlayerId()) {
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + as.getFull_name() +";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + as.getSurname() +";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 " + match.getAwayTeam().getFullname() + ";");
							if(captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
							}
						}
					}
				}
				
				switch(captainWicketKeeper.toUpperCase())
				{
				case CricketUtil.CAPTAIN:
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + captainWicketKeeper.toUpperCase() + ", " + Home_or_Away + ";");
					break;
				case "PLAYER OF THE MATCH":
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + captainWicketKeeper.toUpperCase() + ";");
					break;
				case CricketUtil.WICKET_KEEPER:
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + "WICKET KEEPER" + ", " + Home_or_Away + ";");
					break;
				case CricketUtil.PLAYER:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + Home_or_Away + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$MiddleLine*CONTAINER SET ACTIVE 0;");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$Role*CONTAINER SET ACTIVE 0;");
					break;
				case "CAPTAIN-WICKETKEEPER":
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + "CAPTAIN & WICKET KEEPER" + ", " + Home_or_Away + ";");
					break;
				}

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}	
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
			
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
			this.status = CricketUtil.SUCCESSFUL;
			System.out.println("Fours = " + stats.getTournament_fours() + " Sixes = " + stats.getTournament_sixes());
			double strike_rate = 0 , economy_rate=0;
			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + "PLAYER PROFILE" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage01 " + photo_path + match.getSetup().getHomeTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getHomeTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage01 "+ "\\\\"+config.getPrimaryIpAddress()+"\\" + local_photo_path + match.getSetup().getHomeTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
			}
			else {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage01 " + photo_path + match.getSetup().getAwayTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +match.getSetup().getAwayTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage01 " + local_photo_path + match.getSetup().getAwayTeam().getTeamName4()
							+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
				}
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
			}
			
			if(Profile.equalsIgnoreCase("DT20")) {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCareerHead "+ "T20" + " CAREER" + ";");
			}else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCareerHead "+ Profile + " CAREER" + ";");
			}
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge "+ " " + ";");
			
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole01 "+ playerStyle(TypeofProfile.toUpperCase(), plyr.getBattingStyle())+ ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + stats.getRuns()+ ";");
				
				strike_rate = stats.getRuns() * 100;
				strike_rate = strike_rate/stats.getBalls_faced();
				DecimalFormat df = new DecimalFormat("0.0");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursHead " + "S/R" + ";");
				if(stats.getRuns()== 0 && stats.getBalls_faced() == 0) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursValue " + "-" +";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursValue " + df.format(strike_rate) +";");
				}
				
				break;
			case CricketUtil.BOWLER:
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole01 "+ playerStyle(TypeofProfile.toUpperCase(), plyr.getBowlingStyle())+ ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 "+"WICKETS"+";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 "+stats.getWickets() + ";");
				
				economy_rate = stats.getRuns_conceded() / stats.getBalls_bowled();
				DecimalFormat df_b = new DecimalFormat("0.00");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursHead "+"ECONOMY"+";");
				if(stats.getRuns_conceded() == 0 && stats.getBalls_bowled() == 0) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursValue "+ "-" +";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursValue "+ df_b.format(economy_rate) +";");
				}
				
				break;
			}
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tEventName " + stats.getStats_type().getStatsShortName() + ";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

			}
			break;
		}
		
	}
	public void populateLTPlayerProfile(PrintWriter print_writer,String viz_scene,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				System.out.println("hello");
				System.out.println("Fours = " + stats.getTournament_fours() + " Sixes = " + stats.getTournament_sixes());
			double average = 0, economy_rate = 0;
			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + plyr.getFull_name().toUpperCase() + ";");
			}
			else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + plyr.getFull_name().toUpperCase() + ";");
			}
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue "+stats.getMatches()+ ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "RUNS" +";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + stats.getRuns() + ";");
				
//				strike_rate = stats.getRuns() * 100;
				
				if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "AVERAGE" + ";");
					if(stats.getRuns() < 0) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + "-" + ";");
					}else {
						double out = (stats.getInnings() - stats.getNot_out());
						average = stats.getRuns()/out;
						DecimalFormat df = new DecimalFormat("0.00");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + df.format(average) + ";");
					}
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "AVERAGE" + ";");
					if(stats.getRuns() < 0) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + "-" + ";");
					}else {
						double out = (stats.getInnings() - stats.getNot_out());
						average = stats.getRuns()/out;
						DecimalFormat df = new DecimalFormat("0.00");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + df.format(average) + ";");
					}
				}
				

				if(stats.getHundreds() == null &&  stats.getFifties() == null) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "50s/100s" + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + "0/0"+";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "50s/100s" + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + stats.getFifties()+"/"+stats.getHundreds()+";");
				}
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead4 " + "BEST" +";");
				if(stats.getBest_score() == null) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + "-" + ";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + stats.getBest_score() + ";");
				}
				
				
				break;
			case CricketUtil.BOWLER:
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue "+stats.getMatches()+ ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 "+"WICKETS"+";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 "+stats.getWickets() + ";");
				
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 "+ "DOTS"+";");
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 "+ stats.getDotbowled() +";");
				
//				if(stats.getPlus_3() == null &&  stats.getPlus_5() == null) {
//					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "3WI / 5WI" + ";");
//					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + "0/0"+";");
//				}else {
//					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "3WI / 5WI" + ";");
//					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + stats.getPlus_3()+"/"+stats.getPlus_5()+";");
//				}
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 "+ "AVERAGE"+";");
				if(stats.getRuns_conceded() == 0) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 "+ "-" +";");
				}else {
					average = stats.getRuns_conceded()/stats.getWickets();
					DecimalFormat df_bo = new DecimalFormat("0.00");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 "+ df_bo.format(average) +";");
				}
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 "+"ECON."+";");
				if(stats.getRuns_conceded() == 0 && stats.getBalls_bowled() == 0) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 "+ "-" +";");
				}else {
					economy_rate = stats.getRuns_conceded() / stats.getBalls_bowled();
					DecimalFormat df_b = new DecimalFormat("0.00");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 "+ df_b.format(economy_rate) +";");
				}
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead4 " + "BEST" +";");
				if(stats.getBest_figures() == null) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + "-" + ";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + stats.getBest_figures() + ";");
				}
				
				break;
			}
			if(Profile.equalsIgnoreCase(CricketUtil.ODI)||Profile.equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + Profile + " CAREER" + ";");
			}else if(Profile.equalsIgnoreCase(CricketUtil.DT20)){
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "T20 CAREER" + ";");
			}else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "F-C CAREER" + ";");
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

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
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: DoubleTeam's inning is null";
			} else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBatTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "TEAMS - " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
							" WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
							" WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");
				}
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				int row_id = 0;
				for(int i = 1; i <= 2 ; i++) {
					if(i == 1) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
						
						for(Player hs : match.getSetup().getHomeSquad()) {
							row_id = row_id + 1;
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomePlayerName" + row_id + " " + hs.getFull_name() + ";");
							//System.out.println(hs.getFull_name().toUpperCase());
							if(hs.getCaptainWicketKeeper() != null ) {
								if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id +" "+ "1" + ";");
								}
								else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id +" "+ "2" + ";");
								}
								else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id +" "+ "3" + ";");
								}
								else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id +" "+ "0" + ";");
								}						
							}
						}
					} else {
						row_id = 0;
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
						for(Player as : match.getSetup().getAwaySquad()) {
							row_id = row_id + 1;
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayPlayerName" + row_id + " " + as.getFull_name() + ";");
							if(as.getCaptainWicketKeeper() != null ) {
								if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id +" "+ "1" + ";");
								}
								else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id +" "+ "2" + ";");
								}
								else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id +" "+ "3" + ";");
								}
								else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id +" "+ "0" + ";");
								}						
							}
						}
					}
				}		
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public Infobar populateInfobar(Infobar infobar,PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Infobar's inning is null";
			} else {
				infobar = populateInfobarTeamScore(infobar,false, print_writer, match, session_selected_broadcaster);
				infobar = populateInfobarTopRight(infobar,false, print_writer, match, session_selected_broadcaster);
				infobar = populateInfobarBottomLeft(infobar,false, print_writer, match, session_selected_broadcaster);
				infobar = populateInfobarBottomRight(infobar,false, print_writer, match, session_selected_broadcaster);
				infobar = populateInfobarTopLeft(infobar,false, print_writer, match, session_selected_broadcaster);

				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		return infobar;
		
	}	
	public Infobar populateInfobarTeamScore(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster)
	{
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Start*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + "Round\\" + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Start*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + "Round\\" + inn.getBowling_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Start*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");

						print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Sponsor_In SHOW 0.0 ;");
						//processAnimation(print_writer, "Sponsor_In", "START", session_selected_broadcaster,1);
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET lgBatTeamLogo " + logo_path + "Round\\" + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET lgBallTeamLogo " + logo_path + "Round\\" + inn.getBowling_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					}
					
					
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
						if(match.getSetup().getFollowOn().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "1" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tPowerPlay " + "f/o" + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "0" + ";");
						}
				    }else {
				    	if(!match.getSetup().getTargetOvers().isEmpty() && Double.valueOf(match.getSetup().getTargetOvers()) == 1) {
					    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "0" + ";");
					    }
					    else {
					    	if(CricketFunctions.processPowerPlay(CricketUtil.MINI,match).isEmpty()) {
					    		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "0" + ";");
					    	}else {
					    		if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
					    			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "0" + ";");
					    		}
					    		else if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.ODI)) {
					    			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "1" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tPowerPlay " + 
											CricketFunctions.processPowerPlay(CricketUtil.MINI,match) + ";");
					    		}else {
					    			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "1" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tPowerPlay " + "P" + ";");
					    		}
				    			
					    	}
					    }
				    }
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getTeamName4().toUpperCase() + ";");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTeamScore " + inn.getTotalRuns() + ";");
					}
					else{
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTeamScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTeamOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls())+ ";");
				}
			}
			break;
		}
		return infobar;
	}
	public Infobar populateInfobarTopLeft(Infobar infobar, boolean is_this_updating, PrintWriter print_writer,MatchAllData match, String broadcaster)
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
				if(current_batsmen != null && current_batsmen.size() >= 1) {
					if(current_batsmen.get(0).getPlayerId() != current_batsmen.get(0).getPlayerId()) {
						processAnimation(print_writer, "Batsman1ChangeOut", "START", session_selected_broadcaster,1);
						//TimeUnit.SECONDS.sleep(1);
					}
					if(current_batsmen.get(1).getPlayerId() != current_batsmen.get(1).getPlayerId()) {
						processAnimation(print_writer, "Batsman2ChangeOut", "START", session_selected_broadcaster,1);
						//TimeUnit.SECONDS.sleep(1);
					}
				}
				
				if(current_batsmen != null && current_batsmen.size() >= 2) {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBatsmanName1 " + current_batsmen.get(0).getPlayer().getTicker_name() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBatsmanScore1 " + current_batsmen.get(0).getRuns() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBatsmanBall1 "+ current_batsmen.get(0).getBalls() + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBatsmanName2 " + current_batsmen.get(1).getPlayer().getTicker_name() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBatsmanScore2 " + current_batsmen.get(1).getRuns() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBatsmanBall2 "+ current_batsmen.get(1).getBalls() + ";");
					
					if(current_batsmen.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vOnStrike" + " " + "0" + ";");
						}
					}
					if(current_batsmen.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vOnStrike" + " " + "1" + ";");
						}	
					}
					if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
						processAnimation(print_writer, "Batsman1DeHighlight", "SHOW 250.0", session_selected_broadcaster,1);
					} else if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						processAnimation(print_writer, "Batsman1DeHighlight", "SHOW 0.0", session_selected_broadcaster,1);
					}
					if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
						processAnimation(print_writer, "Batsman2DeHighlight", "SHOW 250.0", session_selected_broadcaster,1);
					} else if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						processAnimation(print_writer, "Batsman2DeHighlight", "SHOW 0.0", session_selected_broadcaster,1);
					}
				}
			}
		}
			
		infobar.setLast_batsmen(current_batsmen);
		return infobar;
	}
	public Infobar populateInfobarTopRight(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, 
			MatchAllData match, String broadcaster) throws InterruptedException
	{
		
		switch(infobar.getTop_right_section().toUpperCase()) {
		case CricketUtil.BOWLER:
			//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BowlerbaseGrp$TopLIne*ACTIVE SET " + "0" + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") 
								|| boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
								processAnimation(print_writer, "BowlerChangeOut", "START", session_selected_broadcaster,1);
								TimeUnit.SECONDS.sleep(1);
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBowlerName " + boc.getPlayer().getTicker_name() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBowlerFigure " + boc.getWickets() + "-" + boc.getRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBowlerOvers " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
								processAnimation(print_writer, "BowlerChangeIn", "START", session_selected_broadcaster,1);
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
	
	/*public String resetAnimation(PrintWriter print_writer,String which_broadcaster, String which_graphic) {
		String status = "";
		
		switch(which_broadcaster) {
		case "THAILAND":
			switch(which_graphic) {
			case "INFOBAR":
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Batsman1ChangeOut SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Batsman1DeHighlight SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Batsman2ChangeOut SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Batsman2DeHighlight SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Batsman1ChangeIn SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Batsman2ChangeIn SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				break;
			}
			break;
		}
		
		return status;
	}
	
	public String processVariousAnimation(PrintWriter print_writer,String which_broadcaster,Match match ,String which_graphic, List<BattingCard> infobar_batsman) throws InterruptedException {
		String status = "";
		
		switch(which_broadcaster) {
		case "THAILAND":
			switch(which_graphic) {
			case "INFOBAR_TOPLEFT":
				List<BattingCard> batsman = new ArrayList<BattingCard>();
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(inn.getPartnerships() != null && inn.getPartnerships().size() > 0) {
								if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
									batsman.add(bc);
								}
								else if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
									batsman.add(bc);
								}
							}
						}
						
						if(infobar_batsman == null || infobar_batsman.size() <= 0) {
							infobar_batsman = batsman;
						}
						
						if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								processAnimation(print_writer, "Batsman1ChangeOut", "START", which_broadcaster);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								processAnimation(print_writer, "Batsman2ChangeOut", "START", which_broadcaster);
							}
						}
						if(batsman != null && batsman.size() >= 1) {
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									processAnimation(print_writer, "Batsman1DeHighlight", "SHOW 250.0", which_broadcaster);
								}
								else if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									processAnimation(print_writer, "Batsman1DeHighlight", "SHOW 0.0", which_broadcaster);
								}
							}
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {	
									processAnimation(print_writer, "Batsman2DeHighlight", "SHOW 250.0", which_broadcaster);
								}
								else if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									processAnimation(print_writer, "Batsman2DeHighlight", "SHOW 0.0", which_broadcaster);
								}
							}
						}
						
						if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								processAnimation(print_writer, "Batsman1ChangeIn", "START", which_broadcaster);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								processAnimation(print_writer, "Batsman2ChangeIn", "START", which_broadcaster);
							}
						}
						TimeUnit.SECONDS.sleep(1);
					}
				}
				break;
			}
			break;
		}
		
		return status;
	}*/
	public Infobar populateInfobarBottomLeft(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			switch(infobar.getBottom_left_section().toUpperCase()) {
			case "WHICH_INNING":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "1" + ";");
				}
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tHowOut " + " "+ ";");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						//System.out.println("Number - " + inn.getInningNumber());
						if(inn.getInningNumber() == 1 || inn.getInningNumber() == 2) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSec4 " + "1st INNINGS" + ";");
						}else if(inn.getInningNumber() == 3 || inn.getInningNumber() == 4) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSec4 " + "2nd INNINGS" + ";");
						}
					}
				}
				break;
			case "DAY_SESSION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "1" + ";");
				}
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tHowOut " + " "+ ";");
				if(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getIsCurrentSession().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSec4 " + 
							"DAY " + match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getDayNumber() + "- SESSION " + 
							match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getSessionNumber() + ";");
				}
				
				break;
			case "VS_BOWLING_TEAM":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "2" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBallTeamName " + 
								inn.getBowling_team().getTeamName4().toUpperCase() + ";");
					}
				}
				break;
			case "CURRENT_RUN_RATE":
				for(Inning inn : match.getMatch().getInning()) {
					if(is_this_updating == false) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "4" + ";");
					}
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tCurrentRunRateText " + "CRR@ " + ";");
						if(inn.getRunRate() != null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tCurrentRunRate " + inn.getRunRate() + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tCurrentRunRate " + "0.0" + ";");
						}
						
					}
				}
				break;
			case"TARGET":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "3" + ";");
				}
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRateText " + "CRR@ " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTargetScore " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
				break;
			case"REQUIRED_RUN_RATE":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "5" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tRequiredRunRateText " + "RRR@ " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tRequiredRunRate " + 
					CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
				break;
			}
			break;
		}
		infobar.setLast_bottom_left_section(infobar.getBottom_left_section().toUpperCase());
		return infobar;
	}
	public Infobar populateInfobarBottomRight(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			switch(infobar.getBottom_right_section().toUpperCase()) {
			case "TOSS_WINNING":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "0" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTossResult " + CricketFunctions.generateTossResult(match, "", "", CricketUtil.SHORT,CricketUtil.CHOSE).toUpperCase() + ";");
				break;
			case "BALL_SINCE":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "BALLS SINCE LAST BOUNDARY - "  + CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber()) + ";");
					}
				}
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				break;	
			case "SUPER_OVER":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "SUPER OVER" + ";");
				break;
			case "TOURNAMENT_NAME":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getSetup().getTournament().toUpperCase() + ";");
				break;
			case "VENUE_NAME":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getSetup().getVenueName().toUpperCase() + ";");
				break;
			case "EQUATION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "3" + ";");
				}
				if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
					if(match.getMatch().getInning().get(1).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
						}
						if(CricketFunctions.GetTeamRunsAhead(2,match) > 0) {

							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + 
									match.getMatch().getInning().get(1).getBatting_team().getTeamName2() + " LEAD BY " + CricketFunctions.GetTeamRunsAhead(2,match) + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTeamRunsAhead(2,match)).toUpperCase() + ";");
						} else if(CricketFunctions.GetTeamRunsAhead(2,match) == 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "SCORES ARE LEVEL" + ";");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + 
									match.getMatch().getInning().get(1).getBatting_team().getTeamName2() + " TRAIL BY " + (-1 * CricketFunctions.GetTeamRunsAhead(2,match)) + " RUN" + CricketFunctions.Plural(-1 * CricketFunctions.GetTeamRunsAhead(2,match)).toUpperCase() + ";");
						}
					}else if(match.getMatch().getInning().get(2).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
						}
						if(CricketFunctions.GetTeamRunsAhead(3,match) > 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + 
									match.getMatch().getInning().get(2).getBatting_team().getTeamName2() + " LEAD BY " + CricketFunctions.GetTeamRunsAhead(3,match) + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTeamRunsAhead(3,match)).toUpperCase() + ";");
						} else if(CricketFunctions.GetTeamRunsAhead(3,match) == 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "SCORES ARE LEVEL" + ";");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + 
									match.getMatch().getInning().get(2).getBatting_team().getTeamName2() + " TRAIL BY " + (-1 * CricketFunctions.GetTeamRunsAhead(3,match)) + " RUN" + CricketFunctions.Plural(-1 * CricketFunctions.GetTeamRunsAhead(3,match)).toUpperCase() + ";");
						}
					}else if(match.getMatch().getInning().get(3).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
					}
				}else {
					if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + CricketFunctions.GenerateMatchSummaryStatus(2, 
								match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
					}else {
						if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "MATCH TIED" + ";");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
									}
								}

							}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) 
							{
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "MATCH TIED" + ";");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
									}
								}
							}
							else{
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "3" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tRuns " + "RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
								if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "OVERS" + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
								}
								
							}
						}else {
							if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
								if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "MATCH TIED" + ";");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}

								}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
										|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) 
								{
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "MATCH TIED" + ";");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}
								}
								else{
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "3" + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tRuns " + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
									if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "OVERS" + ";");
									}else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
									}
								}
							}else if(Double.valueOf(match.getSetup().getTargetOvers()) != 0 && match.getSetup().getTargetRuns() == 0) {
								if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "MATCH TIED" + ";");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}

								}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
										|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) 
								{
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "MATCH TIED" + ";");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}
								}
								else{
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "3" + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tRuns " + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
									if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "OVERS" + ";");
									}else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
									}
								}
							}
							else {
								if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
									if(match.getMatch().getMatchResult() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "MATCH TIED" + ";");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}
									
								}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
										|| Double.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), match.getMatch().getInning().get(1).getTotalBalls())) 
										>= Double.valueOf(match.getSetup().getTargetOvers())) 
								{
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "MATCH TIED" + ";");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}
								}
								else{
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "3" + ";");
									if(match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().isEmpty()) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tRuns " + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
										if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "OVERS" + " (" + match.getSetup().getTargetType().toUpperCase() + ")" + ";");
										}else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (" + match.getSetup().getTargetType().toUpperCase() + ")" + ";");
										}
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tRuns " + "RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase().toUpperCase() + ";");
										if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "OVERS" + ";");
										}else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBalls " + "BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
										}
									}
								}
							}
						}
					}
				}
				break;
			case "LAST_WICKET":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "LAST WICKET : " + CricketFunctions.getLastWicket(match) + ";");
				
				break;
			case "REMAINING_OVERS":
				int daysnumber=0;
				int over_bowled=0,remain_overs=0;
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				daysnumber = match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getDayNumber();
				for(DaySession day_session : match.getMatch().getDaysSessions()) {
					if(daysnumber == day_session.getDayNumber()) {
						over_bowled = over_bowled + day_session.getTotalBalls();
					}
				}
				remain_overs = (90 * 6) - over_bowled;
				if(remain_overs % 6 == 0) {
					remain_overs = remain_overs / 6 ;
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "REMAINING OVERS : " + remain_overs + ";");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "REMAINING OVERS : " + CricketFunctions.OverBalls(0, remain_overs) + ";");
				}
				
				break;
				
			case "FOLLOW_ON":
				int rem_runs = 0;
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				rem_runs = ((match.getMatch().getInning().get(0).getTotalRuns() - match.getSetup().getFollowOnThreshold()) + 1 );
				rem_runs = rem_runs - match.getMatch().getInning().get(1).getTotalRuns();
				if(rem_runs > 0) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "RUNS TO AVOID FOLLOW-ON : " + rem_runs + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "FOLLOW-ON AVOIDED" + ";");
				}
				break;
			
			case"COMPARISION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "2" + ";");
				}
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase("NO")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tCompHead " + "AT THIS STAGE :" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBallTeamName " + match.getMatch().getInning().get(0).getBatting_team().getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tBallTeamScore " + CricketFunctions.compareInningData(match,"/", 1 , match.getEventFile().getEvents()) + ";");
					}
				}
				break;
			}
			break;
		}
		infobar.setLast_bottom_right_section(infobar.getBottom_right_section().toUpperCase());
		return infobar;
	}	
	public void populateInfobarPrompt(boolean is_this_updating, PrintWriter print_writer, InfobarStats ibs, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "6" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				
				if(ibs.getText2() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + ";");
				}else if(ibs.getText1() == null)  {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText2() + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + "-" + ibs.getText2()+ ";");
				}
				infobar.setLast_bottom_right_section("INFOBAR-PROMPT");
			}
			break;
		}
	}	
	public Infobar populateInfobarBottom(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		DecimalFormat df = new DecimalFormat("0.00");
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			switch(infobar.getBottom_right_bottom_section().toUpperCase()) {
			case "EXTRAS":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "3" + ";");
				}
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFreeTextSec6 " + "EXTRAS - " + inn.getTotalExtras() +  " ( NB " + inn.getTotalNoBalls() + ", WD " 
								+ inn.getTotalWides() + ", B " + inn.getTotalByes() + ", LB " + inn.getTotalLegByes() + ", PN "+ inn.getTotalPenalties() + " )" + ";");
					}
				}
				break;
			case "CURRENT_SESSION":
				double dayovers=0;
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
				}
				
				if(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls() % 6 == 0) {
					dayovers = match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls() / 6 ;
				}else {
					dayovers = Double.valueOf(String.valueOf(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls()/6) + 
							"." + String.valueOf(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls()%6)); 
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tSessionHead " + "SESSION " + match.getMatch().getDaysSessions().
						get(match.getMatch().getDaysSessions().size()-1).getSessionNumber() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue1 " + 
						match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalRuns() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue2 " + dayovers + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue3 " + match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalWickets() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue4 " + df.format(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalRuns()/dayovers) + ";");
				
				break;
			case "DAY_PLAY":
				int daynumber=0, dayruns=0, daywickets=0;
				int dayballs=0;
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
				}
				daynumber = match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getDayNumber();
				for(DaySession day_session : match.getMatch().getDaysSessions()) {
					if(daynumber == day_session.getDayNumber()) {
						dayruns = dayruns + day_session.getTotalRuns();
						dayballs = dayballs + day_session.getTotalBalls();
						daywickets = daywickets + day_session.getTotalWickets();
					}
				}
				
				if(dayballs % 6 == 0) {
					dayballs = dayballs / 6 ;
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue2 " + dayballs + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue4 " + df.format(dayruns/dayballs) + ";");
					
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue2 " + Double.valueOf(CricketFunctions.OverBalls(0, dayballs)) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue4 " + df.format(dayruns/Double.valueOf(CricketFunctions.OverBalls(0, dayballs))) + ";");
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tSessionHead " + "DAY " + daynumber + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue1 " + dayruns + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tStatValue3 " + daywickets + ";");
				
				
				break;
			case "TIMELINE":
				String this_ball_data="";
				int ball_count=0;
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
							
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
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vBall" + ball_count + " " + "0" + ";");
										break;
								    case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
								    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vBall" + ball_count + " " + "2" + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTimelineRun"+ ball_count + " " + match.getEventFile().getEvents().get(i).getEventRuns() + ";");
										break;
								    case CricketUtil.FOUR: case CricketUtil.SIX: 
								    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vBall" + ball_count + " " + "3" + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTimelineRun"+ ball_count + " " + match.getEventFile().getEvents().get(i).getEventRuns() + ";");
										break;
								    case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY: 
								    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vBall" + ball_count + " " + "10" + ";");
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
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTimelineRun"+ ball_count + " " + 
												(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + this_ball_data.toUpperCase() + ";");
										break;
								    case CricketUtil.LOG_WICKET: 
								    	if (match.getEventFile().getEvents().get(i).getEventRuns() > 0) {
								    	  print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vBall" + ball_count + " " + "15" + ";");
								    	  print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTimelineRun"+ ball_count + " " + 
								    			  String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns()) + "+W" + ";");
								      } else {
								    	  print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vBall" + ball_count + " " + "15" + ";");
								    	  print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTimelineRun"+ ball_count + " " + "W" + ";");
								      }
								      break;
								    case CricketUtil.LOG_ANY_BALL:
								    	this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns());
								    	
								    	if(match.getEventFile().getEvents().get(i).getEventExtra() != null) {
								    		if(match.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
								    			this_ball_data = this_ball_data + "WD";
								    		}else if(match.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
								    			this_ball_data = this_ball_data + "NB";
								    		}
								    	}
							    		if(match.getEventFile().getEvents().get(i).getEventSubExtra() != null && match.getEventFile().getEvents().get(i).getEventSubExtraRuns()>0) {
							    			this_ball_data = this_ball_data + "+" + match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
							    			if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
							    				this_ball_data = this_ball_data + "WD";
								    		}else if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
								    			this_ball_data = this_ball_data + "NB";
								    		}else if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.LEG_BYE)) {
								    			this_ball_data = this_ball_data + "LB";
								    		}else if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.BYE)) {
								    			this_ball_data = this_ball_data + "B";
								    		}else if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
								    			this_ball_data = this_ball_data + "P";
								    		}
							    		}
								    	if (match.getEventFile().getEvents().get(i).getEventHowOut() != null && !match.getEventFile().getEvents().get(i).getEventHowOut().isEmpty()) {
								    		this_ball_data = this_ball_data + "+W";
								    		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vBall" + ball_count + " " + "14" + ";");
							    			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTimelineRun"+ ball_count + " " + 
							    					this_ball_data + ";");
								    	}else {
								    		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vBall" + ball_count + " " + "5" + ";");
							    			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tTimelineRun"+ ball_count + " " + 
							    					this_ball_data + ";");
								    	}
								    }
									break;
								}
									
							    if(ball_count >= 17) {
							    	break;
							    }
							  }
							}
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "5" + ";");
						}
					}
				}
				break;
			
			case"BOUNDARIES":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "2" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tFoursValue " + inn.getTotalFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tSixValue " + inn.getTotalSixes() + ";");
					}
				}
				break;
			case"PARTNERSHIP":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "1" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tPartnershiplPlayer1Runs " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tPartnershiplPlayer1Balls " + 
								 "(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ")"  + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tPartnershipPlayer2Runs " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tPartnershipPlayer2Balls " + 
								"(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ")"  + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tPartnershipRuns " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tPartnershipBalls " + 
								 "(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")"  + ";");
					}
				}
				break;
			
			case"PROJECTED_SCORE":
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection6Selection " + "0" + ";");
				}
				
			    String[] proj_score_rate = new String[projectedScore(match).size()];
			    for (int i = 0; i < projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = projectedScore(match).get(i);
		        }
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tProjectedHead1 " + "@" + proj_score_rate[0] +" (CRR)" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tProjectedValue1 " + proj_score_rate[1] + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tProjectedHead2 " + "@" + proj_score_rate[2] + " RPO"+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tProjectedValue2 " + proj_score_rate[3] + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tProjectedHead3 " + "@" + proj_score_rate[4] + " RPO" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET tProjectedValue3 " + proj_score_rate[5] + ";");
				break;	
			}
			break;
		}
		infobar.setLast_bottom_right_bottom_section(infobar.getBottom_right_bottom_section().toUpperCase());
		return infobar;
	}
	public void populateInfobarDirector(PrintWriter print_writer,String Dir_value,String session_selected_broadcaster) {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			switch (Dir_value.toUpperCase()) {
			case "FOURS":
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*SixIn STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*SixIn SHOW 0.0;");
				processAnimation(print_writer, "FourIn", "START", session_selected_broadcaster,1);
				break;

			case "SIXES":
				processAnimation(print_writer, "SixIn", "START", session_selected_broadcaster,1);
				break;
			
			case "WICKETS":
				processAnimation(print_writer, "WicketIn", "START", session_selected_broadcaster,1);
				break;

			case "FREE-HIT":
				processAnimation(print_writer, "FreeHitIn", "START", session_selected_broadcaster,1);
				break;
			}
			break;
		}
	}
	public void populateThisSession(PrintWriter print_writer,String viz_sence_path, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		double dayovers=0;
		DecimalFormat df = new DecimalFormat("0.00");
		if(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls() % 6 == 0) {
			dayovers = match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls() / 6 ;
		}else {
			dayovers = Double.valueOf(String.valueOf(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls()/6) + 
					"." + String.valueOf(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls()%6)); 
		}
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + "THIS SESSION" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnings " + " " + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "RUNS" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalRuns() + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "OVERS" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + dayovers + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "WICKETS" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalWickets() + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "RUN RATE" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + df.format(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalRuns()/dayovers) + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead4 " + "" + ";");
		print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + "" + ";");
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		this.status = CricketUtil.SUCCESSFUL;
		
	}
	public void populateSession(PrintWriter print_writer,String viz_sence_path,int day,int session, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		double dayovers=0;
		DecimalFormat df = new DecimalFormat("0.00");
		for(int i =0; i <= match.getMatch().getDaysSessions().size() -1; i++ ) {
			if(match.getMatch().getDaysSessions().get(i).getTotalBalls() % 6 == 0) {
				dayovers = match.getMatch().getDaysSessions().get(i).getTotalBalls() / 6 ;
			}else {
				dayovers = Double.valueOf(String.valueOf(match.getMatch().getDaysSessions().get(i).getTotalBalls()/6) + 
						"." + String.valueOf(match.getMatch().getDaysSessions().get(i).getTotalBalls()%6)); 
			}
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");

			if(match.getMatch().getDaysSessions().get(i).getDayNumber() == day && match.getMatch().getDaysSessions().get(i).getSessionNumber() == session) {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + "DAY " + match.getMatch().getDaysSessions().
						get(i).getDayNumber() + " SESSION " + match.getMatch().getDaysSessions().get(i).getSessionNumber() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnings " + " " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "RUNS" + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + match.getMatch().getDaysSessions().get(i).getTotalRuns() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "OVERS" + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + dayovers + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "WICKETS" + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + match.getMatch().getDaysSessions().get(i).getTotalWickets() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "RUN RATE" + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + df.format(match.getMatch().getDaysSessions().get(i).getTotalRuns()/dayovers) + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead4 " + "" + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + "" + ";");
			}
		}
		//match.getDaysSessions().get(0).get
		

		
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		this.status = CricketUtil.SUCCESSFUL;
		
	}
	public void populateSponsor(PrintWriter print_writer,String session_selected_broadcaster) {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET lgSponsorImage01 " + logo_path + "Sponsor\\" + "Dafa_News" + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET lgSponsorImage02 " + logo_path + "Sponsor\\" + "Dafa_News" + CricketUtil.PNG_EXTENSION + ";");
			break;
		}
	}
	public void populateMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<VariousText> vt,List<Fixture> fix, List<Ground> ground,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				
				if(fix.get(match_number - 1).getTeamgroup() != null) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + fix.get(match_number - 1).getTeamgroup().toUpperCase() 
							+ " - " + fix.get(match_number - 1).getMatchfilename() + ";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + fix.get(match_number - 1).getMatchfilename().toUpperCase() + ";");
				}
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Subheader1*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				for(Team TM : team) {
					if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + TM.getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + TM.getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeam01 " + TM.getTeamName1().toUpperCase() + ";");
					}
					if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + TM.getTeamName4().toUpperCase() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + TM.getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeam01 " + TM.getTeamName1().toUpperCase() + ";");
					}
				}
				
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, +1);
				
				Calendar calto = Calendar.getInstance();
				calto.add(Calendar.DATE, 0);
				
				for(VariousText varioustext : vt) {
					if(varioustext.getVariousType().equalsIgnoreCase("FFMATCHPROMOFOOTER") && varioustext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + varioustext.getVariousText().toUpperCase() +";");
					}else {
						if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "TOMORROW " + "- LIVE FROM " + ground.get(Integer.valueOf(fix.get(match_number - 1).getVenue()) - 1).getFullname().toUpperCase() +";");
						}else if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(calto.getTime()))) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "UP NEXT " + "- LIVE FROM "+ ground.get(Integer.valueOf(fix.get(match_number - 1).getVenue()) - 1).getFullname().toUpperCase() +";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + fix.get(match_number - 1).getDate() + " - LIVE FROM "+ ground.get(Integer.valueOf(fix.get(match_number - 1).getVenue()) - 1).getFullname().toUpperCase()  + ";");
						}
					} 
						
				}
				/*if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "TOMORROW " + "- LIVE FROM "+ match.getVenueName().toUpperCase() +";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "UP NEXT " + "- LIVE FROM "+ match.getVenueName().toUpperCase()  + ";");
				}*/
				
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateMatchId(PrintWriter print_writer,String viz_scene,List<Fixture>fixture,List<VariousText> vt, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				
				for(Fixture fix: fixture) {
					if(fix.getHometeamid() == match.getSetup().getHomeTeamId() && fix.getAwayteamid() == match.getSetup().getAwayTeamId()) {
						if(fix.getTeamgroup() != null) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + fix.getTeamgroup().toUpperCase().toUpperCase() 
									+ " - " + fix.getMatchfilename().toUpperCase() + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + fix.getMatchfilename().toUpperCase() + ";");
						}
					}
				}
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Subheader1*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeam01 " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeam01 " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + "CHALLENGERS" + ";");
				
				/*for(VariousText vartext : vt) {
					if(vartext.getVariousType().equalsIgnoreCase("MATCHIDFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + vartext.getVariousText().toUpperCase() + ";");
					}else {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "LIVE FROM "+ match.getVenueName().toUpperCase() + ";");
					}
				}*/
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "" + ";");
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populateLTMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Data$HomeTeamName$FirstNAme*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamLastName " + match.getSetup().getHomeTeam().getTeamName4() + ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Data$AwayTeamName$FirstNAme*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamLastName " + match.getSetup().getAwayTeam().getTeamName4() + ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 " + match.getSetup().getTournament().toUpperCase() + "-" + 
						match.getSetup().getMatchIdent().toUpperCase() + ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 " + "LIVE FROM "+ match.getSetup().getVenueName().toUpperCase() + ";");
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 106.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	public void populatePlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,   MatchAllData match, String session_selected_broadcaster, Configuration config) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: PlayingXI's inning is null";
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				int row_id = 0;
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				if(TeamId == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + "v " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
							" , " + match.getSetup().getMatchIdent()  + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					for(Player hs : match.getSetup().getHomeSquad()) {
						row_id = row_id + 1;
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$First_Five$Player" + row_id + "_GRP$group$Image1_GRP$Image*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$Second_Six$Player" + row_id + "_GRP$group$Image1_GRP$Image*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$First_Five$Player" + row_id + "_GRP$group$Player Role*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$First_Five$Player" + row_id + "_GRP$group$Player NAME01*CONTAINER SET ACTIVE 1;");
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " " + photo_path + 
									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +match.getSetup().getHomeTeam().getTeamName4().toUpperCase() 
									+ CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION ).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " " +"\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+ local_photo_path + 
									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0"+ row_id + " " + hs.getTicker_name() + ";");
						
						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "1" + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + hs.getRole().toUpperCase() +" (C)" + ";");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "2" + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + hs.getRole().toUpperCase() + " (WK)" + ";");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "3" + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + "(C & WK)" + ";");
						}
						else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "0" + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + hs.getRole().toUpperCase() + ";");
						}
						
					}
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Score_GRP*CONTAINER SET ACTIVE 0;");
				}
				
				else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "v " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
							" , " + match.getSetup().getMatchIdent() + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					for(Player as : match.getSetup().getAwaySquad()) {
						row_id = row_id + 1;
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " " + photo_path + 
									match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() 
									+ CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION ).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " "+ "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH + local_photo_path + 
									match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						}
						
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0"+ row_id + " " + as.getTicker_name() + ";");
						//System.out.println(as.getCaptainWicketKeeper());
						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "1" + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + as.getRole().toUpperCase() +" (C)" + ";");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "2" + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + as.getRole().toUpperCase() + " (WK)" + ";");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "3" + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + "(C & WK)" + ";");
						}
						else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "0" + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + as.getRole().toUpperCase() + ";");
						}
					}
					
				}
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Score_GRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + CricketFunctions.generateTossResult(match, "", "", CricketUtil.SHORT,CricketUtil.CHOSE).toUpperCase() + ";");
				}
				
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			//TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
			
			break;
		}
		
	}
	public void populateProjectedScore(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: ProjectedScore's inning is null";
			} else {
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "@"+ proj_score_rate[0] +" (CRR)" + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + proj_score_rate[1] + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "@" + proj_score_rate[2] +" RPO"+ ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + proj_score_rate[3] + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "@" + proj_score_rate[4] +" RPO" + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + proj_score_rate[5] + ";");
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				//for(Inning inn : match.getInning()) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
							CricketUtil.PNG_EXTENSION + ";");
					//if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +  "TARGET" +";");
						
						if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
							if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
									CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+  Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS"  +";");
							}else {
								if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
										CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+  CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS"  +";");
								}else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
										CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+ Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS"  +";");
								}
							}
						}else {
							if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
									CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+  CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" + " ("+ match.getSetup().getTargetType().toUpperCase() +")"  +";");
							}else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + match.getMatch().getInning().get(1).getBatting_team().getTeamName3().toUpperCase() + " NEED " + 
									CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+  Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS" + " ("+ match.getSetup().getTargetType().toUpperCase() +")" +";");
							}
						}
					//}
				//}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
		
	}
	public void populateFFTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster)throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				for(Inning inn : match.getMatch().getInning()) {
					//if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + "v " + inn.getBowling_team().getTeamName1().toUpperCase() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + inn.getBowling_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						
						
						if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
							if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUNS" + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + (Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6) + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + "BALLS" + ";");
							}else {
								if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUNS" + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + match.getSetup().getMaxOvers() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + "OVERS" + ";");
								}else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUNS" + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + (Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6) + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + "BALLS" + ";");
								}
							}
						}else {
							if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUNS" + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + "OVERS" + " (" + match.getSetup().getTargetType().toUpperCase() + ")" + ";");
							}else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUNS" + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + "BALLS" + " ("+ match.getSetup().getTargetType().toUpperCase() +")" + ";");
							}
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCRRHead " + " " + ";");
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
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCRR " + "REQUIRED RUN RATE : " + CricketFunctions.generateRunRate
								(requiredRuns, 0, requiredBalls, 2,match) + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRRHead " + " " + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRR " + " " + ";");
					//}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
	}
	public void populateFFEquation(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster)throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + "v " + inn.getBowling_team().getTeamName1().toUpperCase() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + inn.getBowling_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						
						
						if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
							if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + "BALL" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
							}else {
								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUN" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + "OVERS" + ";");
								}else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUN" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + "BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
								}
							}
						}else {
							if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + 
									" (" + match.getSetup().getTargetType().toUpperCase() + ")" + ";");
							}else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetRuns " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetBalls " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallsHead " + "BALL" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " ("+ match.getSetup().getTargetType().toUpperCase() +")" + ";");
							}
						}
						
						if(inn.getRunRate() == null) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCRRHead " + " " + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCRR " + "REQUIRED RUN RATE : " + CricketFunctions.generateRunRate
									(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRRHead " + " " + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRR " + " " + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCRR " + inn.getRunRate() + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRR " + CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, 
									CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
						}
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
	}
	public void populateBugTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
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
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: TeamSummary's inning is null";
			} else {
				
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
						String[] Count = CricketFunctions.getScoreTypeData("TEAM", match, whichInning, 0, ",", match.getEventFile().getEvents()).split(",");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						}
						else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + Count[0] + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + Count[1] + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + Count[2] + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + Count[3] + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue5 " + inn.getTotalFours() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue6 " + inn.getTotalSixes() + ";");
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateLtBattingSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: PlayerSummary's inning is null";
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					for(BattingCard bc : inn.getBattingCard()) {
						if (inn.getInningNumber() == whichInning) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
									CricketUtil.PNG_EXTENSION + ";");
							String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
							
							if(PlayerId == bc.getPlayerId()) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + bc.getPlayer().getFull_name()+ ";");
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + bc.getRuns() + "*" + ";");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + bc.getRuns() + ";");
								}
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "BALLS" + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + bc.getBalls() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + Count[0] + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + Count[1] + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + Count[2] + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + Count[3] + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue5 " + bc.getFours() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue6 " + bc.getSixes() + ";");
							}
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateLtBatsmanThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_IN_HOUSE_VIZ":
				if (match == null) {
					this.status = "ERROR: Match is null";
				} else if (match.getMatch().getInning() == null) {
					this.status = "ERROR: PlayerSummary's inning is null";
				} else {
					
					for(Inning inn : match.getMatch().getInning()) {
						for(BattingCard bc : inn.getBattingCard()) {
							if (inn.getInningNumber() == whichInning) {
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
								if(PlayerId == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								

									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + " " + "\0");								
									/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
									} else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
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
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtBowlerThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_IN_HOUSE_VIZ":
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
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
								} else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
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
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtBowlerSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: PlayerSummary's inning is null";
			} else {
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBowling_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						for(BowlingCard boc : inn.getBowlingCard()) {
							String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BOWLER,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
							if(PlayerId == boc.getPlayerId()) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + boc.getPlayer().getFull_name()+ ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + boc.getWickets() + "-" + boc.getRuns() + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "OVERS" + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + Count[0] + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + Count[1] + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + Count[2] + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + Count[3] + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue5 " + Count[4] + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue6 " + Count[6] + ";");
							}
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateLtNextToBat(PrintWriter print_writer, String viz_scene, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
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
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateLtBowlerDetails(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_IN_HOUSE_VIZ":
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
					this.status = CricketUtil.SUCCESSFUL;	
				}
				break;
		}
		
	}
	public void populateFallofWicket(PrintWriter print_writer,String viz_scene,int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: DoubleTeam's inning is null";
			} else {
				int row_id= 0 ;
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						} else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path  + match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						}
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						}
						else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
						}
						
						
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							//System.out.println(inn.getFallsOfWickets());
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$Data*CONTAINER SET ACTIVE 0;");
						}
						else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
							for(FallOfWicket fow : inn.getFallsOfWickets()) {								
								if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
									row_id = row_id + 1;
									//System.out.println("row3="+row_id);
									print_writer.println("LAYER2*EVEREST*TREEVIEW*" + (row_id  + 1) + "*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$Data*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead "+ "WICKET:" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vWKTS "+ fow.getFowNumber() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue" + row_id + " "  + fow.getFowRuns() + ";");
								}		
							}
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
		
		
	}
	public void populateSplit(PrintWriter print_writer,String viz_scene,int whichInning,int splitValue, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 30 || inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 50) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "BALLS PER " + splitValue + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + splitValue + CricketFunctions.Plural(splitValue) + " :" + ";");
						} else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "BALLS PER " + splitValue + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path  + match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + splitValue + CricketFunctions.Plural(splitValue) + " :" + ";");
						}
						
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
						}
						
						String[] Splitballs = new String[getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size()];
					    for (int i = 0; i < getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size(); i++) {
					    	Splitballs[i] = getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).get(i);
					    	
					    	int row_id = i + 1;
					    	
					    	print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$"+(row_id+1)+"*CONTAINER SET ACTIVE 1;");
					    	print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue"+ row_id + " "+ Splitballs[i] + ";");
				        }
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				//TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}	
	public void populateComparision(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead1 " + inn.getBowling_team().getTeamName1().toUpperCase() + " WERE " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead2 " + inn.getBatting_team().getTeamName1().toUpperCase() + " ARE " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + ";");						
					}
					if(match.getMatch().getInning().get(0).getTotalWickets() >= 10) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + match.getMatch().getInning().get(0).getTotalRuns() 
								+ "(" + CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(), match.getMatch().getInning().get(0).getTotalBalls()) + ")" + ";");
					}else {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + match.getMatch().getInning().get(0).getTotalRuns() + "-" + match.getMatch().getInning().get(0).getTotalWickets() + ";");
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateCurrentPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership's inning is null";
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				String Left_Batsman ="",Right_Batsman="";
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + " v " + inn.getBowling_team().getTeamName1().toUpperCase() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getMatchIdent().toUpperCase() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						
						for(Player hs : match.getSetup().getHomeSquad()) {
							if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								Left_Batsman = hs.getTicker_name();
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage01 " + photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION ).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage01 " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}
								
							}
							if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								Right_Batsman = hs.getTicker_name();
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage02 " + photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage02 " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}	
							}
						}
						
						for(Player as : match.getSetup().getAwaySquad()) {
							if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								Left_Batsman = as.getTicker_name();
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage01 " + photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +  match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage01 " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}			
							}
							if(as.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								Right_Batsman = as.getTicker_name();
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage02 " + photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +  match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION ).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage02 " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
											CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								}
									
							}
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName02 " + Right_Batsman + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerContriRuns01 " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerContriBalls01 " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerContriRuns02 " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerContriBalls02 " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursValue " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalFours() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixesValue " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalSixes() + ";");
						
						if(inn.getTotalWickets() == 0) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + (inn.getTotalWickets()+1) + "st WICKET PARTNERSHIP" + ";");
						}else if(inn.getTotalWickets() == 1) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + (inn.getTotalWickets()+1) + "nd WICKET PARTNERSHIP" + ";");
						}else if (inn.getTotalWickets() == 2) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + (inn.getTotalWickets()+1) + "rd WICKET PARTNERSHIP" + ";");
						}
						else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + (inn.getTotalWickets()+1) + "th WICKET PARTNERSHIP" + ";");
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				
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
	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_no=0;
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			
			switch(StatType.toUpperCase()) {
			case "MOST_RUNS_DATA":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "MOST RUNS " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
							
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + " " + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_RUNS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "MOST RUNS " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamLastName " + "MOST " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamLastName " + "RUNS " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + tournament.get(i).getRuns() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_WICKETS":
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "MOST WICKETS " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamLastName " + "MOST " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamLastName " + "WICKETS " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
							
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + tournament.get(i).getWickets() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_FOURS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "MOST FOURS " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamLastName " + "MOST " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamLastName " + "FOURS " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION ).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
							
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + tournament.get(i).getFours() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_SIXES":
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "MOST SIXES " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamLastName " + "MOST " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamLastName " + "SIXES " + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION ).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
							
						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0"  + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + tournament.get(i).getSixes() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;
					
		}
	}	
	public void populateLandMark(PrintWriter print_writer,String viz_scene, int whichInning, String statType, int playerId, MatchAllData match, String session_selected_broadcaster, Configuration config)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
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
												photo_path + bc.getPlayer().getFirstname() + CricketUtil.PNG_EXTENSION + "\0");
									}else {
										if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +bc.getPlayer().getFirstname() + CricketUtil.PNG_EXTENSION).exists()) {
											this.status = CricketUtil.UNSUCCESSFUL;
										}
										print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Player_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
												"\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path + bc.getPlayer().getFirstname() + CricketUtil.PNG_EXTENSION + "\0");
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
									print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Player_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
											photo_path + boc.getPlayer().getFirstname() + ".png" + "\0");
								
								}
							}
							break;
						}
					}
				}
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}
	public void populateFFLandMark(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, MatchAllData match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
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
								print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Plaer_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
										photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
							}
						}
					}
				}
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}
	public void populateLtEquation(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Equation's inning is null";
			} else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +  "EQUATION" +";");
						
						if(match.getMatch().getMatchResult() != null) {
							if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
								if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + 
											CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
								}else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + 
											CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + 
											" ("+ match.getSetup().getTargetType().toUpperCase() + ")" + ";");
								}
							}
							else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + "MATCH TIED" + ";");
							}
							else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + ";");
							}
							else {
								if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + 
											CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
								}else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + 
											CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + 
											" ("+ match.getSetup().getTargetType().toUpperCase() + ")" + ";");
								}
							}
						}
						else {
							if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + 
										CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
							}else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + 
										CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + 
										" ("+ match.getSetup().getTargetType().toUpperCase() + ")" + ";");
							}
						}
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
		
	}
	public void populateLtMatchStatus(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Equation's inning is null";
			} else{
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +  "EQUATION" +";");

				if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
					if(match.getMatch().getInning().get(1).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(CricketFunctions.GetTeamRunsAhead(2,match) > 0) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " +  match.getMatch().getInning().get(1).getBatting_team().getTeamName2() + " LEAD BY" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " +  "" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " +  CricketFunctions.GetTeamRunsAhead(2,match) +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + 
									      " RUN" + CricketFunctions.Plural(CricketFunctions.GetTeamRunsAhead(2,match)).toUpperCase() + ";");
						} else if(CricketFunctions.GetTeamRunsAhead(2,match) == 0) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " +  match.getMatch().getInning().get(1).getBatting_team().getTeamName2() +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " +  "" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " +  "" +";");

							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "SCORES ARE LEVEL" + ";");
						} else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " +  match.getMatch().getInning().get(1).getBatting_team().getTeamName2()+ " TRAIL BY" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " +  (-1 * CricketFunctions.GetTeamRunsAhead(2,match)) +";");

							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + 
									     " RUN" + CricketFunctions.Plural(-1 * CricketFunctions.GetTeamRunsAhead(2,match)).toUpperCase() + ";");
						}
					}else if(match.getMatch().getInning().get(2).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(CricketFunctions.GetTeamRunsAhead(3,match) > 0) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " +  match.getMatch().getInning().get(2).getBatting_team().getTeamName2() + " LEAD BY" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " +  "" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " +  CricketFunctions.GetTeamRunsAhead(3,match) +";");

							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + 
									     " RUN" + CricketFunctions.Plural(CricketFunctions.GetTeamRunsAhead(3,match)).toUpperCase() + ";");
						} else if(CricketFunctions.GetTeamRunsAhead(3,match) == 0) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " +  match.getMatch().getInning().get(2).getBatting_team().getTeamName2() +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " +  "" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " +  "" +";");

							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + "SCORES ARE LEVEL" + ";");
						} else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " +  match.getMatch().getInning().get(2).getBatting_team().getTeamName2() + " TRAIL BY" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "" +";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " +  (-1 * CricketFunctions.GetTeamRunsAhead(3,match)) +";");

							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + 
									    " RUN" + CricketFunctions.Plural(-1 * CricketFunctions.GetTeamRunsAhead(3,match)).toUpperCase() + ";");
						}
					}else if(match.getMatch().getInning().get(3).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " +  "EQUATION" +";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " +  "" +";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " +  "" +";");

						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsHead " + match.getMatch().getMatchStatus().toUpperCase() + ";");
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
		
	}
	public void populatePointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> groupA,List<LeagueTeam> groupB, String session_selected_broadcaster,MatchAllData match) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$PointsTable_GRP*CONTAINER SET ACTIVE 1;");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPool01 " + "GROUP A" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPool02 " + "GROUP B" + ";");
			
			int row_no=0;
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsTeamName " + "POINTS TABLE" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
			//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatHeadE " + "POINTS" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatHeadF " + "NRR" + ";");

			for(int i = 0; i <= groupA.size() - 1 ; i++) {
				row_no = row_no + 1;
				if(groupA.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsTeamName0" + row_no + " " + 
							groupA.get(i).getTeamName().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsTeamName0" + row_no + " " + "(Q) " + 
							groupA.get(i).getTeamName().toUpperCase() + ";");
				}
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "A" + " " + groupA.get(i).getPlayed() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "B" + " " + groupA.get(i).getWon() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "C" + " " + groupA.get(i).getLost() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "D" + " " + groupA.get(i).getNoResult() + ";");
				DecimalFormat df = new DecimalFormat("0.000");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "F" + " " + df.format(groupA.get(i).getNetRunRate()) + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "E" + " " + groupA.get(i).getPoints() + ";");

			}
			
			for(int i = 0; i <= groupB.size() - 1 ; i++) {
				row_no = row_no + 1;
				if(groupB.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsTeamName0" + row_no + " " + 
							groupB.get(i).getTeamName().toUpperCase() + ";");
				}else {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsTeamName0" + row_no + " " + "(Q) " + 
							groupB.get(i).getTeamName().toUpperCase() + ";");
				}
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "A" + " " + groupB.get(i).getPlayed() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "B" + " " + groupB.get(i).getWon() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "C" + " " + groupB.get(i).getLost() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "D" + " " + groupB.get(i).getNoResult() + ";");
				DecimalFormat df = new DecimalFormat("0.000");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "F" + " " + df.format(groupB.get(i).getNetRunRate()) + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointsStatValue" + (row_no + 1) + "E" + " " + groupB.get(i).getPoints() + ";");

			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*PointsTableIn SHOW 125.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*PointsTableOut SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*PointsTableOut SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*PointsTableIn SHOW 0.0;");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;
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
			//System.out.println(match.getHomeTeam().getShortname().toUpperCase());
			//System.out.println(match.getAwayTeam().getShortname().toUpperCase());
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

			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "A " + point_table.get(i).getTeamName().toUpperCase() + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "B " + point_table.get(i).getPlayed() + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "C " + point_table.get(i).getWon() + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "D " + point_table.get(i).getLost() + ";");
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_id + "H " + point_table.get(i).getPoints() + ";");

		}
		this.status = CricketUtil.SUCCESSFUL;	
	}
	public void populateBowlerStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId,List<Player> plyr, List<Team> team, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bowler Style's inning is null";
			} else {
				for(Player plr : plyr) {
					if(plr.getPlayerId() ==playerId) {

						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + team.get(plr.getTeamId() - 1).
								getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plr.getFull_name().toUpperCase() +";");
						if(plr.getBowlingStyle() != null) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + CricketFunctions.getbowlingstyle(plr.getBowlingStyle()).toUpperCase() + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + " " + ";");
						}
					}
				}
				/*
				 * print_writer.
				 * println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " +
				 * logo_path + team.get(plyr.get(playerId - 1).getTeamId() - 1). getTeamName4()
				 * + CricketUtil.PNG_EXTENSION + ";");
				 * 
				 * print_writer.
				 * println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +
				 * plyr.get(playerId - 1).getFull_name().toUpperCase() +";");
				 * 
				 * if(plyr.get(playerId - 1).getBowlingStyle() != null) { print_writer.
				 * println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " +
				 * CricketFunctions.getbowlingstyle(plyr.get(playerId -
				 * 1).getBowlingStyle()).toUpperCase() + ";"); }else { print_writer.
				 * println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " +
				 * " " + ";"); }
				 */
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}	
	public void populateTieIdDouble(PrintWriter print_writer,String viz_sence_path,String day,List<Fixture> fix,List<Team>team,MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			int row_id = 1;
			String Date = "";
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament().toUpperCase() +";");
			Calendar cal = Calendar.getInstance();
			
			if(day.toUpperCase().equalsIgnoreCase("TODAY")) {
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "TODAY MATCHES " +";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + "TODAY" +";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + "TODAY" +";");

			}
			else if(day.toUpperCase().equalsIgnoreCase("TOMORROW")) {
				cal.add(Calendar.DATE, +1);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "TOMORROW MATCHES " +";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + "TOMORROW" +";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + "TOMORROW" +";");

			}
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			//System.out.println(day.compareTo(fix.get(0).getDate()) + 1); // want it to check which day match is this
			for(int i = 0; i <= fix.size()-1; i++) {
				if(fix.get(i).getDate().equalsIgnoreCase(Date)) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo0" +row_id + " " + logo_path + team.get(fix.get(i).getHometeamid()-1).getTeamName4().toUpperCase() 
							 + CricketUtil.PNG_EXTENSION +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeam0" +row_id + " " + team.get(fix.get(i).getHometeamid()-1).getTeamName1().toUpperCase() +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tMatch" + row_id + " " + "MATCH " + fix.get(i).getMatchnumber() +";");
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo0" +row_id+ " " + logo_path + team.get(fix.get(i).getAwayteamid()-1).getTeamName4().toUpperCase() 
							 + CricketUtil.PNG_EXTENSION +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeam0" +row_id+ " " + team.get(fix.get(i).getAwayteamid()-1).getTeamName1().toUpperCase() +";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tMatch" + row_id + " " + "MATCH " +fix.get(i).getMatchnumber() +";");
				 row_id = row_id +1;
				}
			}
			//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "LIVE FROM "+ match.getVenueName().toUpperCase() +";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "" + ";");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 70.0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
		this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	public void populatePreviousSummary(PrintWriter print_writer, String viz_scene, int match_number,List<MatchAllData> mtch,List<Fixture> fix, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Match Summary's inning is null";
			} else {
				for(int j = 0; j <= mtch.size() - 1; j++) {
					//String path = "D:\\Everest_Scenes\\Mumbai_Indians\\Textures and Images\\Logos\\" ;
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumTeamName " + mtch.get(j).getSetup().getMatchIdent().toUpperCase() + 
							" - SUMMARY" +  ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + " SUMMARY" + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumSubHeader " + mtch.get(j).getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
							" vs " + mtch.get(j).getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBatTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + "" + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$HeaderGrp$BigBandGrp$Sponsor*CONTAINER SET ACTIVE 0;");
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BattingCard_GRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BowlingCard_GRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$PointsTable_GRP*CONTAINER SET ACTIVE 0;");
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumTeamALastName " + mtch.get(j).getSetup().getMatchIdent().toUpperCase() + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumTeamBLastName " + mtch.get(j).getSetup().getMatchIdent().toUpperCase() + ";");
					
					int row_id = 0, row = 0, max_Strap = 0;
					String teamname = ""; 
					
//					for(int i = 1; i <= 2 ; i++) {
//						if(i == 1) {
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$SecondInnings*CONTAINER SET ACTIVE 0;");
//						}
//						else {
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$SecondInnings*CONTAINER SET ACTIVE 1;");
//						}
//					}
					
					if(mtch.get(j).getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20)||mtch.get(j).getSetup().getMatchType().equalsIgnoreCase(CricketUtil.ODI)||
							mtch.get(j).getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)||mtch.get(j).getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10)) {
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInnings " + "0" + ";");
					}
//					else if(mtch.get(j).getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
//						if(whichInning == 2) {
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInnings " + "0" + ";");
//						}else if(whichInning == 3) {
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInnings " + "1" + ";");
//						}else if (whichInning == 4) {
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInnings " + "2" + ";");
//						}
//						
//					}
					
					for(int i = 1; i <= 2 ; i++) {

						if(i == 1) {
							row = 0;
							row_id = 0;
							max_Strap = 3;
						} else {
							row = 1;
							row_id = 3;
							max_Strap = 6;
						}
						row = row + 1;
						if(mtch.get(j).getMatch().getInning().get(i-1).getBattingTeamId() == mtch.get(j).getSetup().getHomeTeamId()) {
							teamname = mtch.get(j).getSetup().getHomeTeam().getTeamName1().toUpperCase();	
						} else {
							teamname = mtch.get(j).getSetup().getAwayTeam().getTeamName1().toUpperCase();
						}
						
						if(mtch.get(j).getSetup().getTossWinningTeam() == mtch.get(j).getMatch().getInning().get(0).getBattingTeamId()) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHomeToss 1;");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAwayToss 0;");
							
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Main$First_two$FirstInnings$1st$InningsGrp*CONTAINER SET ACTIVE 1;");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Main$First_two$SecondInnings$5$InningsGrp*CONTAINER SET ACTIVE 0;");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnOrToss1 " + "TOSS" + ";");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInningHead2 " + "" + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHomeToss 0;");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAwayToss 1;");
							
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Main$First_two$FirstInnings$1st$InningsGrp*CONTAINER SET ACTIVE 0;");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Main$First_two$SecondInnings$5$InningsGrp*CONTAINER SET ACTIVE 1;");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInnOrToss2 " + "TOSS" + ";");
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInningHead1 " + "" + ";");
						}
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumTeamName0"+ row +" "+ teamname + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumOverNo0"+ row + " " + 
								CricketFunctions.OverBalls(mtch.get(j).getMatch().getInning().get(i-1).getTotalOvers(),mtch.get(j).getMatch().getInning().get(i-1).getTotalBalls()) + ";");
						
						if(mtch.get(j).getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumScore0"+ row + " " + mtch.get(j).getMatch().getInning().get(i-1).getTotalRuns() + ";");
						}
						else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumScore0"+ row + " " + mtch.get(j).getMatch().getInning().get(i-1).getTotalRuns() + slashOrDash + mtch.get(j).getMatch().getInning().get(i-1).getTotalWickets() + ";");	
						}
						if(mtch.get(j).getMatch().getInning().get(i-1).getBattingCard() != null) {
							//row_id = 0;
							Collections.sort(mtch.get(j).getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
							for(BattingCard bc : mtch.get(j).getMatch().getInning().get(i-1).getBattingCard()) {
								if(bc.getBatsmanInningStarted() != null && bc.getBatsmanInningStarted().equalsIgnoreCase(CricketUtil.YES) && bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Batsman0"+ row_id + "*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanName0"+ row_id + " " + bc.getPlayer().getTicker_name() + " (rh)" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanRuns0"+ row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanBalls0"+ row_id + " " + bc.getBalls() + ";");
								}
								if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Batsman0"+ row_id + "*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanName0"+ row_id + " " + bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanRuns0"+ row_id + " " + bc.getRuns() + ";");
									
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanNotOut0"+ row_id + " " + "*" + ";");
										
									}
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanNotOut0"+ row_id + " " + " " + ";");
										
									} 
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBatsmanBalls0"+ row_id + " " + bc.getBalls() + ";");
									
									if(i == 1 && row_id >= 3) {
										break;
									}else if(i == 4 && row_id >= 6) {
										break;
									}
								}
							}
						}

						for(int k = row_id + 1; k <= max_Strap; k++) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Batsman0"+ k + "*CONTAINER SET ACTIVE 0;");
						}
						
						if(i == 1) {
							row_id = 0;
						}
						else {
							row_id = 3;
						}

						if(mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard() != null) {
							//row_id = 0;
							Collections.sort(mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

							for(BowlingCard boc : mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard()) {
								
								row_id = row_id + 1;
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Bowler0"+ row_id + "*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBowlerName0" + row_id + " " +  boc.getPlayer().getTicker_name() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBowlerRuns0" + row_id + " " + boc.getWickets() + slashOrDash + boc.getRuns() + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumBowlerOver0" + row_id + " " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
								
								if(i == 1 && row_id >= 3) {
									break;
								}
								else if(i == 4 && row_id >= 6) {
									break;
								}
							}
						}
						
						for(int k = row_id + 1; k <= max_Strap; k++) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$Summary_GRP$DataAll$BAtting$Summary_Card$Bowler0"+ k + "*CONTAINER SET ACTIVE 0;");
						}
					}
					
					if(mtch.get(j).getMatch().getMatchResult() != null) {
						if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + "MATCH TIED" + ";");
						}
						else if(mtch.get(j).getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + "MATCH TIED - " + mtch.get(j).getMatch().getMatchStatus().toUpperCase() + ";");
						}
						else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + mtch.get(j).getMatch().getMatchStatus().toUpperCase() + ";");
						}
					}
					else {
						if(mtch.get(j).getSetup().getTargetType() == "") {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + 
									mtch.get(j).getMatch().getMatchStatus().toUpperCase() + ";");
						}
						else if(mtch.get(j).getSetup().getTargetType() != null) {
							if(mtch.get(j).getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + 
										mtch.get(j).getMatch().getMatchStatus().toUpperCase() + " (VJD)" + ";");
							}
							else if(mtch.get(j).getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSumResult " + 
										mtch.get(j).getMatch().getMatchStatus().toUpperCase() + " (DLS)" + ";");
							}
						}
						//.trim().equalsIgnoreCase("")
						
					}
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*SummaryIn SHOW 196.0;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*SummaryOut SHOW 0.0;");
					print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
					print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
					TimeUnit.SECONDS.sleep(1);
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*SummaryOut SHOW 0.0;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
					print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*SummaryIn SHOW 0.0;");
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				}
			}
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}
	public BowlingCard populateVizInfobarBottomRight(boolean is_this_updating, PrintWriter print_writer, String BottomRightStats, MatchAllData match, String session_selected_broadcaster,BowlingCard last_bowler) throws InterruptedException
	{
		BowlingCard bowler = new BowlingCard();
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			switch(BottomRightStats.toUpperCase()) {
			case "BOWLER":
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") || boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
								bowler = boc;
							}
						}
					}
				}
				if(last_bowler == null) {
					last_bowler = bowler;
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section3$BowlerOut START \0");
						TimeUnit.SECONDS.sleep(1);
					}
				}
				
				if(bowler != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BowlerPos$BowlerName*GEOM*TEXT SET " + 
											bowler.getPlayer().getSurname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BowlerPos$FigureGrp$Figure*GEOM*TEXT SET " + 
											bowler.getWickets() + "-" + bowler.getRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$ScoreBugAll$All$AllSections$Section3$BowlerGrp1$BowlerPos$FigureGrp$Overs*GEOM*TEXT SET " + 
											CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + "\0");
					
					//processAnimation(print_writer, "BowlerChangeIn", "START", session_selected_broadcaster);
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section3$BowlerIn START \0");
						TimeUnit.SECONDS.sleep(1);
					}
				}	
				/*if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section3$Base3In START \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlerIn START \0");
					TimeUnit.SECONDS.sleep(1);
				}*/
				break;
				
			}
			break;
		}
		return bowler;
	}
	public List<BattingCard> populateVizInfobarBottomLeft(boolean is_this_updating, PrintWriter print_writer, String BottomLeftStats, MatchAllData match, String session_selected_broadcaster,List<BattingCard> infobar_batsman) throws InterruptedException
	{
		List<BattingCard> batsman = new ArrayList<BattingCard>();
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			switch (BottomLeftStats.toUpperCase()) {
			case CricketUtil.BATSMAN:
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for (BattingCard bc : inn.getBattingCard()) {
							
							if(inn.getPartnerships() != null && inn.getPartnerships().size() > 0) {
								if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
									batsman.add(bc);
								}
								else if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
									batsman.add(bc);
								}
							}
						}
						
						
						if(infobar_batsman == null || infobar_batsman.size() <= 0) {
							infobar_batsman = batsman;
						}
						
						if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2-4$Section2$Batsman1Out START \0");
								TimeUnit.SECONDS.sleep(1);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2-4$Section2$Batsman2Out START \0");
								TimeUnit.SECONDS.sleep(1);
							}
						}
						if(batsman != null && batsman.size() >= 1) {
							print_writer.println("-1 RENDERER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanPos1$BatsmanGrp1$BatsmanName01*GEOM*TEXT SET " + 
													batsman.get(0).getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanPos1$BatsmanGrp1$ScoreGrp1$Score01*GEOM*TEXT SET " + 
													batsman.get(0).getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanPos1$BatsmanGrp1$ScoreGrp1$Ball01*GEOM*TEXT SET " + 
													batsman.get(0).getBalls() + "\0");
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Dehighlight START \0");
								}
								else if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Highlight START \0");

								}
							}
							print_writer.println("-1 RENDERER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanGrp2$BatsmanName02*GEOM*TEXT SET " + 
													batsman.get(1).getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanGrp2$ScoreGrp1$Score02*GEOM*TEXT SET " + 
													batsman.get(1).getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$ScoreBugAll$All$AllSections$Section2$BatsmanAll$BatsmanGrp2$ScoreGrp1$Ball02*GEOM*TEXT SET " + 
													batsman.get(1).getBalls() + "\0");
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								
								if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {	
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman2Dehighlight START \0");
									
								}
								else if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman2Highlight START \0");

								}
							}
							if(batsman.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(batsman.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike$Batman1Strike*ACTIVE SET 1 \0");
								}
							}
							if(batsman.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(batsman.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike$Batman2Strike*ACTIVE SET 1 \0");
								}	
							}
						}
						
						if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								
								print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2-4$Section2$Batsman1In START \0");
								TimeUnit.SECONDS.sleep(1);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								
								print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2-4$Section2$Batsman2In START \0");
								TimeUnit.SECONDS.sleep(1);
							}
						}
						if(is_this_updating == false) {
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1In START \0");
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman2In START \0");
							TimeUnit.SECONDS.sleep(1);
						}
					}
				}
				break;
			}
			break;
		}
		return batsman;
	}
	public void populateVizInfobarTop(boolean is_this_updating, PrintWriter print_writer, String TopStats, MatchAllData match, String session_selected_broadcaster)
	{
		//System.out.println("TopStats " + TopStats);
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
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
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp$TeamName2*GEOM*TEXT SET " + 
								inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
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
											CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase()  + "\0");
					
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
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster,int which_layer)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "THAILAND":
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
	public void populateBatsmanStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, List<Player> plyr, List<Team> team, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Equation's inning is null";
			} else {
				for(Player plr : plyr) {
					if(plr.getPlayerId() ==playerId) {

						//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + sponsor_logo_path + "IOCL" + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + team.get(plr.getTeamId()-1).getTeamName4() + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plr.getFull_name().toUpperCase() +";");
						if(plr.getBattingStyle() != null) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + CricketFunctions.getbattingstyle(plr.getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + ";");
						}else {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + " " + ";");
						}
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
	}
	public void populateGeneric(PrintWriter print_writer,String viz_scene,String Stats, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Generic's inning is null";
			} else {
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				switch(Stats.toUpperCase()) {
				case "BOUNDARIES":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("v*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
							}
							
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "BOUNDARIES : " + inn.getTotalFours() + " FOURS " + inn.getTotalSixes() + " SIXES" + ";");
						}
					}
					break;
				case "CURRENT_SESSION":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("v*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
							}
							double dayovers=0;
							if(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls() % 6 == 0) {
								dayovers = match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls() / 6 ;
							}else {
								dayovers = Double.valueOf(String.valueOf(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls()/6) + 
										"." + String.valueOf(match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalBalls()%6)); 
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + " DAY " + match.getMatch().getDaysSessions().
									get(match.getMatch().getDaysSessions().size()-1).getDayNumber() + " SESSION " + match.getMatch().getDaysSessions().
									get(match.getMatch().getDaysSessions().size()-1).getSessionNumber() + "  RUNS- " 
									+ match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalRuns() + "  OVERS- " + dayovers + "  WKT- " 
									+ match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalWickets() + " RUN RATE- " + (match.getMatch().getDaysSessions().get(match.getMatch().getDaysSessions().size()-1).getTotalRuns()/dayovers) + ";");
						}
					}
					break;
				case "LEAD_TRAIL":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("v*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
							}
							
							if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
								if(match.getMatch().getInning().get(1).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
									if(CricketFunctions.GetTeamRunsAhead(2,match) > 0) {
										//print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + "MATCH TIED" + ";");

										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
												match.getMatch().getInning().get(1).getBatting_team().getTeamName2() + " LEAD BY " + CricketFunctions.GetTeamRunsAhead(2,match) + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTeamRunsAhead(2,match)).toUpperCase() + ";");
									} else if(CricketFunctions.GetTeamRunsAhead(2,match) == 0) {
										//print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");

										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "SCORES ARE LEVEL" + ";");
									} else {
										//print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");

										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
												match.getMatch().getInning().get(1).getBatting_team().getTeamName2() + " TRAIL BY " + (-1 * CricketFunctions.GetTeamRunsAhead(2,match)) + " RUN" + CricketFunctions.Plural(-1 * CricketFunctions.GetTeamRunsAhead(2,match)).toUpperCase() + ";");
									}
								}else if(match.getMatch().getInning().get(2).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
									if(CricketFunctions.GetTeamRunsAhead(3,match) > 0) {
										//print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
												match.getMatch().getInning().get(2).getBatting_team().getTeamName2() + " LEAD BY " + CricketFunctions.GetTeamRunsAhead(3,match) + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTeamRunsAhead(3,match)).toUpperCase() + ";");
									} else if(CricketFunctions.GetTeamRunsAhead(3,match) == 0) {
										//print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "SCORES ARE LEVEL" + ";");
									} else {
										//print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
												match.getMatch().getInning().get(2).getBatting_team().getTeamName2() + " TRAIL BY " + (-1 * CricketFunctions.GetTeamRunsAhead(3,match)) + " RUN" + CricketFunctions.Plural(-1 * CricketFunctions.GetTeamRunsAhead(3,match)).toUpperCase() + ";");
									}
								}else if(match.getMatch().getInning().get(3).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
									//print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + match.getMatch().getMatchStatus().toUpperCase() + ";");
								}
							}
							//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "BOUNDARIES : " + inn.getTotalFours() + " FOURS " + inn.getTotalSixes() + " SIXES" + ";");
						}
					}
					break;
				case "RUNS_BALLS":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
								
								
							}
							if(match.getMatch().getMatchResult() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
											CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
								}
								else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "MATCH TIED" + ";");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + ";");
								}
								else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
											CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
									//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + match.getMatchStatus().toUpperCase() + ";");
								}
							}
							else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
										CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + ";");
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + match.getMatchStatus().toUpperCase() + ";");
								
								if(match.getSetup().getTargetType() != null) {
									if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
												CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + " (VJD)" + ";");
									}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")){
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + 
												CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",session_selected_broadcaster,true).getTargetOrResult().toUpperCase() + " (DLS)" + ";");
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
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "CURRENT PARTNERSHIP : "   
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + " RUNS OFF "  
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + " BALLS" + ";");
						}
					}
					break;
				case "CURRENT_RUN_RATE":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}	
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "CURRENT RUN RATE : " + inn.getRunRate() + ";");
						}
					}
					break;
				case "REQUIRED_RUN_RATE":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}	
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "REQUIRED RUN RATE : " + 
												CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
						}
					}
					break;
				case "COMPARISION":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}	
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "AT THIS STAGE : " + match.getMatch().getInning().get(0).getBatting_team().getTeamName1().toUpperCase() + 
												 " " + CricketFunctions.compareInningData(match,"-", 1 , match.getEventFile().getEvents()) + ";");
						}
					}
					break;
				case "LAST_WICKET":
					for(Inning inn : match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
							}
							else{
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS"+ ";");
							
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
																"(" + bc.getBalls() + ")" + ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
												"(" + bc.getBalls() + ")"+ ";");
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName02 " + 
																bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun02 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall02 " + 
																"(" + bc.getBalls() + ")"+ ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName01 " + 
												bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRun01 " + 
																bc.getRuns() + ";");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall01 " + 
												"(" + bc.getBalls() + ")" + ";");
									}
								}
							}
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "LAST WICKET : " + CricketFunctions.getLastWicket(match) + ";");
						}
					}
					break;
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
		
	}
	public void populateManhattan(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if(whichInning == 0) {
				this.status = "ERROR: Inning is null";
			}else {
				
				int maxRuns = 0,runsIncr = 0;
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
						CricketUtil.PNG_EXTENSION + ";");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + "v " + inn.getBowling_team().getTeamName1().toUpperCase() + ";");
					}
				}
				
				List<String> overByOverRuns = new ArrayList<String>();
				
				for(OverByOverData Over : CricketFunctions.getOverByOverData(match,whichInning,"MANHATTAN",match.getEventFile().getEvents())) {
					//System.out.println("Runs : " + Over.getOverTotalRuns());
					overByOverRuns.add(String.valueOf(Over.getOverTotalRuns()));
				}
				String cumm_runs = String.join("#", overByOverRuns); // Store Per Overs Runs
				//System.out.println("Runs : " + cumm_runs);
				
				for(String str : cumm_runs.split("#"))   // For Find MaxRun
				{
					if(maxRuns < Integer.valueOf(str))
					{
						maxRuns = Integer.valueOf(str);	
					}
					while (maxRuns % 4 != 0) {     // 5 label in y-axis
						maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
					}
				}
				
				for(int i =0; i < 4;i++) {
					runsIncr = maxRuns / 4; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36	
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TxtScore" + (4 - i) + " " + runsIncr*(i+1) + ";");
				}
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Manhatten$Main$Graph$Bars*GEOMETRY*BARS SET MAX_HEIGHT_VALUE " + maxRuns + ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Manhatten$Main$Graph$Bars*GEOMETRY*BARS SET DATA " + cumm_runs.replaceFirst("0#", "") + ";");
				
				for(int j=0; j <= match.getSetup().getMaxOvers(); j++) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Manhatten$Main$Wickets$Over_" + j +"*CONTAINER SET ACTIVE 0;");
					
					if(j < CricketFunctions.getOverByOverData(match,whichInning,"MANHATTAN", match.getEventFile().getEvents()).size()) {
						//System.out.println("Runs : " + j + " : " + CricketFunctions.getOverByOverData(match, whichInning, match.getEvents()).get(j).getOverTotalRuns());
						//System.out.println("Wickets : " + j + " : " + CricketFunctions.getOverByOverData(match,whichInning, match.getEvents()).get(j).getOverTotalWickets());
//						System.out.println("Wickets = " + CricketFunctions.getOverByOverData(match,whichInning,"MANHATTAN", match.getEvents()).get(j).getOverTotalWickets());
						if(j <= 50) {
							if(CricketFunctions.getOverByOverData(match,whichInning,"MANHATTAN", match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Over_" + (j-1) + "*FUNCTION*TAG_POSITION SET MAX_VALUE " + maxRuns + ";");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Over_" + (j-1) + " " + 
										CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN", match.getEventFile().getEvents()).get(j).getOverTotalRuns() + ";");
								
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Manhatten$Main$Wickets$Over_" + (j-1) +"*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Over"+ (j-1) + " " + (Integer.valueOf(
										CricketFunctions.getOverByOverData(match,whichInning,"MANHATTAN", match.getEventFile().getEvents()).get(j).getOverTotalWickets()) - 1) + ";");
							}else {
								if(j==0) {
									
								}else {
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Manhatten$Main$Wickets$Over_" + (j-1) +"*CONTAINER SET ACTIVE 0;");
								}
							}
						}
						
					}
				}
				
				/*for(int j=0; j<=CricketFunctions.getOverByOverData(match,whichInning,"MANHATTAN", match.getEvents()).size()-1;j++) {
					//System.out.println("Runs : " + j + " : " + CricketFunctions.getOverByOverData(match, whichInning, match.getEvents()).get(j).getOverTotalRuns());
					//System.out.println("Wickets : " + j + " : " + CricketFunctions.getOverByOverData(match,whichInning, match.getEvents()).get(j).getOverTotalWickets());
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Manhatten$Main$Wickets$Over_" + j +"*CONTAINER SET ACTIVE 0;");
					if(CricketFunctions.getOverByOverData(match,whichInning,"MANHATTAN", match.getEvents()).get(j).getOverTotalWickets() > 0) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Over_" + j + "*FUNCTION*TAG_POSITION SET MAX_VALUE " + maxRuns + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Over_" + j + " " + 
								CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN", match.getEvents()).get(j).getOverTotalRuns() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Manhatten$Main$Wickets$Over_" + j +"*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Over"+ j + " " + (Integer.valueOf(
								CricketFunctions.getOverByOverData(match,whichInning,"MANHATTAN", match.getEvents()).get(j).getOverTotalWickets()) - 1) + ";");
					}else {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Manhatten$Main$Wickets$Over_" + j +"*CONTAINER SET ACTIVE 0;");
					}
				}*/	
			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
			
	}
	public void populateMostRuns(PrintWriter print_writer,String viz_scene,List<Tournament> tournament,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			int row_no=0;
			
			Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "MOST RUNS" + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + "MOST" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + "RUNS" + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows " + "3" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "PLAYER" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "MATCHES" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "RUNS" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 " + "S/R" + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead05 " + "4s / 6s" + ";");*/
			
			/*for(Tournament ts : tournament) {
				System.out.println("Name -" + ts.getPlayer().getFull_name().toUpperCase() + " - Matches -" + ts.getMatches() + " - Runs -" + ts.getRuns());
			}*/
			
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				if(tournament.get(i).getRuns() > 0) {
					if(row_no < 10) {
						row_no = row_no + 1;
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + row_no + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A" + " " + tournament.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B" + " " + tournament.get(i).getMatches() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "C" + " " + tournament.get(i).getRuns() + ";");
						if(tournament.get(i).getBallsFaced() >= 1) {
							DecimalFormat df = new DecimalFormat("0.0");

							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "D" + " " + 
									df.format((100 * (double)tournament.get(i).getRuns()) / (double)tournament.get(i).getBallsFaced()) + ";");
						}
					}else {
						break;
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateMostWickets(PrintWriter print_writer,String viz_scene,List<Tournament> tournament_boc,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			int row_no=0;
			
			Collections.sort(tournament_boc,new CricketFunctions.BowlerWicketsComparator());
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "MOST WICKETS" + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + "MOST" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + "WICKETS" + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows " + "3" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "PLAYER" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "MATCHES" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "WICKETS" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 " + "ECON." + ";");
			
			for(int i = 0; i <= tournament_boc.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament.get(i).getPlayer().getFull_name().toUpperCase() + " - Runs -" + tournament.get(i).getRuns());
				if(tournament_boc.get(i).getWickets() > 0) {
					if(row_no < 10) {
						row_no = row_no + 1;
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + row_no + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A" + " " + tournament_boc.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B" + " " + tournament_boc.get(i).getMatches() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "C" + " " + tournament_boc.get(i).getWickets() + ";");
						if(tournament_boc.get(i).getBallsBowled() >= 1) {
							
							DecimalFormat df_b = new DecimalFormat("0.00");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "D" + " " + 
									df_b.format(((double)tournament_boc.get(i).getRunsConceded() / (double)tournament_boc.get(i).getBallsBowled())*6) + ";");
						}
					}else {
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "D" + " " + "-" + ";");
						break;
					}
				}
				
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateMostFours(PrintWriter print_writer,String viz_scene,List<Tournament> tournament_fours,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			int row_no=0;
			
			//Collections.sort(tournament_fours);
			Collections.sort(tournament_fours,new CricketFunctions.BatsmanFoursComparator());
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "MOST FOURS" + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows " + "3" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "PLAYER" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "MATCHES" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "FOURS" + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 " + "ECON." + ";");
			
			for(int i = 0; i <= tournament_fours.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament_fours.get(i).getPlayer().getFull_name().toUpperCase() + " - Fours -" + tournament_fours.get(i).getFours());
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + row_no + ";");
				if(tournament_fours.get(i).getFours() > 0) {
					if(row_no < 10) {
						row_no = row_no + 1;
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A" + " " + tournament_fours.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B" + " " + tournament_fours.get(i).getMatches() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "C" + " " + tournament_fours.get(i).getFours() + ";");
					}	
				}
				
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateMostSixes(PrintWriter print_writer,String viz_scene,List<Tournament> tournament_sixes,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			int row_no=0;
			
			//Collections.sort(tournament_sixes);
			Collections.sort(tournament_sixes,new CricketFunctions.BatsmanSixesComparator());
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "MOST SIXES" + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows " + "3" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "PLAYER" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "MATCHES" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "SIXES" + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 " + "ECON." + ";");
			
			for(int i = 0; i <= tournament_sixes.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament_sixes.get(i).getPlayer().getFull_name().toUpperCase() + " - Sixes -" + tournament_sixes.get(i).getSixes());
				if(tournament_sixes.get(i).getSixes() > 0) {
					if(row_no < 10) {
						row_no = row_no + 1;
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCoumms " + row_no + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A" + " " + tournament_sixes.get(i).getPlayer().getFull_name() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B" + " " + tournament_sixes.get(i).getMatches() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "C" + " " + tournament_sixes.get(i).getSixes() + ";");
					}
				}
					
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateHighestScore(PrintWriter print_writer,String viz_scene,List<Tournament> tournament_high_score,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			int row_no = 0;
			List<BestStats> top_ten_beststats = new ArrayList<BestStats>();
			for(Tournament tourn : tournament_high_score) {
				for(BestStats bs : tourn.getBatsman_best_Stats()) {
					top_ten_beststats.add(bs);
				}
			}
			
			Collections.sort(top_ten_beststats, new CricketFunctions.PlayerBestStatsComparator());

			/*for(BestStats Top_ten_bs : top_ten_beststats) {
				System.out.println("Best Stats : " + Top_ten_bs);
			}*/
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + "HIGHEST INDIVIDUAL SCORE" + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getSetup().getMatchIdent().toUpperCase() + ";");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vRows " + "4" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "PLAYER" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "SCORE" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "BALLS"+ ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 " + "OPPONENT TEAM" + ";");
			
			for(int i = 0; i <= top_ten_beststats.size() - 1 ; i++) {
				if(row_no < 10) {
					row_no = row_no + 1;

					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A" + " " + top_ten_beststats.get(i).getPlayer().getFull_name() + ";");
					if(top_ten_beststats.get(i).getBestEquation() % 2 == 0) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B" + " " + top_ten_beststats.get(i).getBestEquation() / 2 + ";");
					}else {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B" + " " + 
								top_ten_beststats.get(i).getBestEquation() / 2  + "*" + ";");
					}
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "C" + " " + top_ten_beststats.get(i).getBalls() + ";");
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "D" + " " + 
							top_ten_beststats.get(i).getOpponentTeam().getTeamName4().toUpperCase() + ";");
					
				}	
			}
			
			print_writer.println("LAYER2*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER2*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateThisOver(PrintWriter print_writer,String viz_scene,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "THAILAND":
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "THIS OVER" + ";");
				for(Inning inn : match.getMatch().getInning()) {
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBowling_team().getTeamName4() + 
							CricketUtil.PNG_EXTENSION + ";");
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") || boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + boc.getPlayer().getFull_name() + ";");
								//System.out.println("SIZE :" + CricketFunctions.getEventsText(CricketUtil.OVER,",", match.getEvents(),0).length());
								
								String arr[] = CricketFunctions.getEventsText(CricketUtil.OVER,0,",", match.getEventFile().getEvents(),0).split(",");
								for(int i=0;i < CricketFunctions.getEventsText(CricketUtil.OVER,0,",", match.getEventFile().getEvents(),0).split(",").length;i++) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$Data$group$"+ (i+2) +"*CONTAINER SET ACTIVE 1;");
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue"+ (i+1)+ " " + arr[i] + ";");
//										System.out.println("Over : " + (i+1) + " - " + arr[i]);
								}
							}
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;
				break;
		}
	}
	public void populateWorm(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if(whichInning == 0) {
				this.status = "ERROR: Inning is null";
			}else {
				
				int maxRuns = 0,runsIncr = 0,wicketcountHome=0,wicketcountAway=0;
				//long lngth = 0;
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 "
						+ "" + ";");
				
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 "
						+ match.getSetup().getMatchIdent().toUpperCase() + ";");
				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader "
						+ match.getSetup().getTournament() + ";");
								
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + match.getMatch().getInning().get(0).getBatting_team().getTeamName2() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamScore " + match.getMatch().getInning().get(0).getTotalRuns() + "-" + match.getMatch().getInning().get(0).getTotalWickets() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamOvers " + CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(), match.getMatch().getInning().get(0).getTotalBalls()) + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + match.getMatch().getInning().get(1).getBatting_team().getTeamName2() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamScore " + match.getMatch().getInning().get(1).getTotalRuns() + "-" + match.getMatch().getInning().get(1).getTotalWickets() + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamOvers " + CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), match.getMatch().getInning().get(1).getTotalBalls()) + ";");
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
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TxtScore" + (4 - i) + " " + runsIncr*(i+1) + ";");
					}
					
					if(inn_count == 1) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Linechart*GEOMETRY*LINE_CHART SET MAX_VALUE " + maxRuns + ";");

						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Linechart*GEOMETRY*LINE_CHART SET DATA " + cumm_runs + ";");
						
						for(int j=0; j<=CricketFunctions.getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).size()-1;j++) {
							if(match.getMatch().getInning().get(inn_count-1).getTotalWickets() > 0) {
								if(CricketFunctions.getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
									wicketcountHome = wicketcountHome + 1;
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets*CONTAINER SET ACTIVE 1;");

									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets$Group" + (wicketcountHome - 1) + "$Group" + (wicketcountHome - 1) + "_XPos*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets$Group" + (wicketcountHome - 1) + "$Group" + (wicketcountHome - 1) 
											+ "_XPos*FUNCTION*TAG_POSITION SET MAX_VALUE " + match.getSetup().getMaxOvers() + ";");

									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHomeWickets " + (wicketcountHome-1) + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets$Group" + (wicketcountHome - 1) + "*FUNCTION*TAG_POSITION SET MAX_VALUE " + maxRuns + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET yHomePos" + (wicketcountHome-1) + " " + 
											CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalRuns() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET xHomePos" + (wicketcountHome - 1) + " " + (j - 0.5) + ";");
									
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selHomeGroup"+ (wicketcountHome - 1) + " " + (CricketFunctions.
											getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalWickets()-1) + ";");
									
//									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selGroup"+ wicketcount + " " + CricketFunctions.
//											getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(j).getOverTotalWickets() + ";");
//									System.out.println("Over : " + CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEvents()).get(j).getOverTotalWickets()
//											+ " - Runs : " + CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEvents()).get(j).getOverTotalRuns());
								}
							}else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$HomeGraph1$Wickets*CONTAINER SET ACTIVE 0;");
							}
						}
					}
					if(inn_count == 2) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Linechart*GEOMETRY*LINE_CHART SET MAX_VALUE " + maxRuns + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Linechart*GEOMETRY*LINE_CHART SET DATA " + cumm_runs + ";");
						
						for(int j=0; j<=CricketFunctions.getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).size()-1;j++) {
							if(match.getMatch().getInning().get(inn_count-1).getTotalWickets() > 0) {
								if(CricketFunctions.getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
									wicketcountAway = wicketcountAway + 1;
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets*CONTAINER SET ACTIVE 1;");

									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets$Group" + (wicketcountAway - 1) + "$Group" + (wicketcountAway-1) + "_XPos*CONTAINER SET ACTIVE 1;");

									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets$Group" + (wicketcountAway - 1) + "$Group" + (wicketcountAway-1) 
											+ "_XPos*FUNCTION*TAG_POSITION SET MAX_VALUE " + match.getSetup().getMaxOvers() + ";");

									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAwayWickets " + (wicketcountAway-1) + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets$Group" + (wicketcountAway - 1) + "*FUNCTION*TAG_POSITION SET MAX_VALUE " + maxRuns + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET yAwayPos" + (wicketcountAway -1) + " " + 
											CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalRuns() + ";");
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET xAwayPos" + (wicketcountAway - 1) + " " + (j - 0.5) + ";");
									
									
									print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selAwayGroup"+ (wicketcountAway - 1) + " " + (CricketFunctions.
											getOverByOverData(match,inn_count,"WORM", match.getEventFile().getEvents()).get(j).getOverTotalWickets()-1) + ";");
									
//									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selGroup"+ wicketcount + " " + CricketFunctions.
//											getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(j).getOverTotalWickets() + ";");
//									System.out.println("Over : " + CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEvents()).get(j).getOverTotalWickets()
//											+ " - Runs : " + CricketFunctions.getOverByOverData(match, inn_count,"WORM", match.getEvents()).get(j).getOverTotalRuns());
								}
							}else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$All$WormGrp$Worms$Main$AwayGraph2$Wickets*CONTAINER SET ACTIVE 0;");
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
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 140;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
//		switch (session_selected_broadcaster.toUpperCase()) {
//		case "THAILAND":
//			if (match == null) {
//				this.status = "ERROR: Match is null";
//			} else if(whichInning == 0) {
//				this.status = "ERROR: Inning is null";
//			}else {
//				
//				int maxRuns = 0,runsIncr = 0,wicketcount=0;
//				//long lngth = 0;
//				
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getMatchIdent().toUpperCase() + ";");
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName " + match.getMatchIdent().toUpperCase() + ";");
//				
//				for(Inning inn : match.getInning()) {
//					if (inn.getInningNumber() == whichInning) {
//						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName4() + 
//								CricketUtil.PNG_EXTENSION + ";");
//						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getTeamName1().toUpperCase() + ";");
//					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "v " + inn.getBowling_team().getTeamName1().toUpperCase() + ";");
//					}
//				}
//				
//				List<String> overByOverRuns = new ArrayList<String>();
//				
//				for(int i=0; i<(CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).size()-1);i++) {
//					overByOverRuns.add(String.valueOf(CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(i).getOverTotalRuns()));
//					//System.out.println("Runs : " + i + " - " + String.valueOf(CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(i).getOverTotalRuns()));
//				}
//				
//				String cumm_runs = String.valueOf("0") + "_" + String.join("_", overByOverRuns); // Store Per Overs Runs
//				//System.out.println("Runs : " + cumm_runs);
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Worms$Graph$Linechart*GEOMETRY*LINE_CHART SET DATA " + cumm_runs + ";");
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
//					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TxtScore" + (4 - i) + " " + runsIncr*(i+1) + ";");
//				}
//				
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$DataAll$BAtting$Worms$Graph$Linechart*GEOMETRY*LINE_CHART SET MAX_VALUE " + maxRuns + ";");
//				
//				for(int j=0; j<=CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).size()-1;j++) {
//					if(match.getInning().get(whichInning-1).getTotalWickets() > 0) {
//						if(CricketFunctions.getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(j).getOverTotalWickets() > 0) {
//							wicketcount = wicketcount + 1;
//							
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vWickets " + wicketcount + ";");
//							
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Group" + wicketcount + "*FUNCTION*TAG_POSITION SET MAX_VALUE " + maxRuns + ";");
//							
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET yPos" + wicketcount + " " + 
//									CricketFunctions.getOverByOverData(match, whichInning,"WORM", match.getEvents()).get(j).getOverTotalRuns() + ";");
//							
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET xPos" + wicketcount + " " + j + ";");
//							
//							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET selGroup"+ wicketcount + " " + CricketFunctions.
//									getOverByOverData(match,whichInning,"WORM", match.getEvents()).get(j).getOverTotalWickets() + ";");
//							System.out.println("Over : " + CricketFunctions.getOverByOverData(match, whichInning,"WORM", match.getEvents()).get(j).getOverNumber()
//									+ " - Runs : " + CricketFunctions.getOverByOverData(match, whichInning,"WORM", match.getEvents()).get(j).getOverTotalRuns());
//						}
//					}
//				}	
//			}
//			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
//			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
//			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
//			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 197.0;");
//			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
//			TimeUnit.SECONDS.sleep(1);
//			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
//			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
//			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
//			
//			this.status = CricketUtil.SUCCESSFUL;
//			break;
//		}
			
	}
	public void populateThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,
			MatchAllData match, String broadcaster) throws InterruptedException {
		
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
			
			//print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
			
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "THIS SERIES" + ";");
			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTLogo " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
					}else {
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
					}
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + 
							this_series.get(i).getPlayer().getFull_name().toUpperCase() + ";");
					
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead4 " + "" + ";");
					print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + "" + ";");
					
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "MATCHES" + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + this_series.get(i).getMatches() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "RUNS" + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + this_series.get(i).getRuns() + ";");
						
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "S/R" + ";");			
						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + "-" + ";");
						}else {
							strike_rate = this_series.get(i).getRuns() * 100;
							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + df.format(strike_rate) + ";");
						}
						 
						for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "BEST" + ";");
							if(top_batsman_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									k += 1;
									if(top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + top_batsman_beststats.get(j).getBestEquation()/2 + ";");
									}else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + (top_batsman_beststats.get(j).getBestEquation()-1) / 2 + ";");
									}
									break;
								}
							}else {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + "-" + ";");
							}
						}
						break;
					case CricketUtil.BOWLER:
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "MATCHES" + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + this_series.get(i).getMatches() + ";");
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "WICKETS" + ";");
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + this_series.get(i).getWickets() + ";");
						
						
						print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "ECON." + ";");
						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + "-" + ";");
						}else {
							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df = new DecimalFormat("0.00");
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + df.format(economy_rate) + ";");
						}
						
						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
							print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "BEST" + ";");
							if(top_bowler_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									k += 1;
									if(top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + 
												((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + ";");
									}
									else {
										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + 
												(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + ";");
									}
									break;
								}
							}else if(top_bowler_beststats.get(j).getPlayerId() != Playerid) {
								print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + "-" + ";");
							}
						}
						break;
					}
					
				}

			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 196.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	public void populatetournamentFour(PrintWriter print_writer, String viz_scene,List<MatchAllData> tourn_matches,MatchAllData match, String broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + CricketFunctions.gettournamentFoursAndSixes(tourn_matches, match).split(",")[0] + ";");
//			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + match.getTournament().toUpperCase() + ";");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populatetournamentSix(PrintWriter print_writer, String viz_scene,List<MatchAllData> tourn_matches,MatchAllData match, String broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "THAILAND":
			//print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + CricketFunctions.gettournamentFoursAndSixes(tourn_matches, match).split(",")[1] + ";");
//			print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + match.getTournament().toUpperCase() + ";");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 32.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
			this.status = CricketUtil.SUCCESSFUL;
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
	public List<String> projectedScore(MatchAllData match) {
		//rates= 6,8,10 or 8,10,12
		List<String> proj_score = new ArrayList<String>();
		String  PS_Curr="", PS_1 = "",PS_2 = "",RR_1 = "",RR_2 = "",CRR = "";
		int Balls_val = 0;

		if(!match.getSetup().getTargetOvers().isEmpty() && Double.valueOf(match.getSetup().getTargetOvers()) > 0) {
			if(match.getSetup().getTargetOvers().contains(".")) {
				Balls_val = (Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]);
			}else {
				Balls_val = Integer.valueOf(match.getSetup().getTargetOvers()) * 6;
			}
			//Over_val = match.getTargetOvers();
		}else {
			Balls_val = match.getSetup().getMaxOvers()*6;
		}
		
		int remaining_balls = (Balls_val - (match.getMatch().getInning().get(0).getTotalOvers()*6 + match.getMatch().getInning().get(0).getTotalBalls()));
		double value = (remaining_balls * Double.valueOf(match.getMatch().getInning().get(0).getRunRate()));
		value  = value/6;
		
		PS_Curr = String.valueOf(Math.round(((value + match.getMatch().getInning().get(0).getTotalRuns()))));
		CRR = match.getMatch().getInning().get(0).getRunRate();
		
		proj_score.add(CRR);
		proj_score.add(String.valueOf(PS_Curr));
		
		String[] arr = (match.getMatch().getInning().get(0).getRunRate().split("\\."));
	    double[] intArr= new double[2];
	    intArr[0]=Integer.parseInt(arr[0]);
	  
		for(int i=2;i<=4;i=i+2) {
			if(i==2) {
				value = (remaining_balls * (intArr[0] + i));
				value = value / 6;
				PS_1 = String.valueOf(Math.round(value + match.getMatch().getInning().get(0).getTotalRuns()));
				RR_1 = String.valueOf(((int)intArr[0] + i));
				
				proj_score.add(RR_1);
				proj_score.add(PS_1);
			}
			else if(i==4) {
				value = (remaining_balls * (intArr[0] + i));
				value = value / 6;
				PS_2 = String.valueOf(Math.round(value + match.getMatch().getInning().get(0).getTotalRuns()));
				RR_2 = String.valueOf(((int)intArr[0] + i));
				
				proj_score.add(RR_2);
				proj_score.add(PS_2);
			}
		}
	    //System.out.println(String.valueOf(PS_Curr));
		return proj_score ;
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
				powerplay_over = CricketFunctions.getBallCountStartAndEndRange(match, inn).get(1);
			}
			if((events != null) && (events.size() > 0)) {
				for(Event evnt : events) {
					if(evnt.getEventInningNumber() == inn_num) {
						int Event_overs = ((evnt.getEventOverNo()*6)+evnt.getEventBallNo());
						if((Event_overs) <= (powerplay_over)) {
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