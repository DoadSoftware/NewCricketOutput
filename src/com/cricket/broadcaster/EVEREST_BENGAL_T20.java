package com.cricket.broadcaster;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Configuration;
import com.cricket.model.Event;
import com.cricket.model.HeadToHead;
import com.cricket.model.HeadToHeadPlayer;
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

public class EVEREST_BENGAL_T20 extends Scene{
	public String broadcaster = "EVEREST_BENGAL_T20";
	public String status;
	public String slashOrDash = "-";
	public String logo_path_ns = "C:\\\\Images\\\\BENGAL\\\\Logos\\\\";
	public String photo_path = "C:\\\\Images\\\\BENGAL\\\\Photos\\\\";
	private String local_photo_path = "c\\\\Images\\\\BENGAL\\\\Photos\\\\";
	private String kpi_photo_path = "\\C:\\Images\\BENGAL\\Photos\\KPI\\";
	private String Sponsors = "\\\\C:\\\\Images\\\\BENGAL\\\\Sponsors\\\\";
	public String logo_path = "C:\\\\Images\\\\BENGAL\\\\Icons\\\\";
	public Infobar infobar = new Infobar(); 
	public String which_graphic_on_screen = "";
	
	public EVEREST_BENGAL_T20() {
		super();
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public EVEREST_BENGAL_T20(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService,
			List<MatchAllData> cricket_matches, PrintWriter printWriter, List<Scene> session_selected_scenes,
			String valueToProcess, List<Statistics> session_statistics, Configuration session_configuration,
			List<HeadToHeadPlayer> headToHead, List<Tournament> past_tournament_stats) throws InterruptedException, NumberFormatException, IOException, CloneNotSupportedException {
		
		switch(whatToProcess) {
		case "POPULATE-FF_GRAPHICS": case "POPULATE-LT_GRAPHICS": case "POPULATE-FF-MATCHID": case "POPULATE-L3-NAMESUPER-SINGLE": case "POPULATE-L3-NAMESUPER": 
		case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-LTMATCH_IDENT": case "POPULATE-BUG_POWERPLAY": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-FF-LEADERBOARD":
		case "POPULATE-MOST_RUNS": case "POPULATE-MULTI_PARTNERSHIP": case "POPULATE-BUG_PARTNERSHIP": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-BUG":
		case "POPULATE-SUPER_OVER": case "POPULATE-TOURNAMENT_RULES":case"POPULATE-BOUNDARIES": case "POPULATE-WICKETS": case "POPULATE-REVIEW": case "POPULATE-L3-SPLIT-DB":
		case "POPULATE-L3-BUG-BOWLER":	case "POPULATE-L3-BUG-DB":case "POPULATE-FAIRPLAY":case"POPULATE-FANTASY11":case "POPULATE-L3-BUG-TOSS":case "POPULATE-L3-TARGET":
		case "POPULATE-PROMO":	
			if(whatToProcess.equalsIgnoreCase("POPULATE-L3-SPLIT-DB")) {
				
				for(Split split : cricketService.getSplit()) {
				  if(split.getSplitId() == Integer.valueOf(valueToProcess.split(",")[1])) {
					  if(split.getText3() != null && !split.getText3().trim().isEmpty()) {
						  if(whatToProcess.replace("POPULATE-", "").replace("LT-", "").replace("L3-", "")!= which_graphic_on_screen) {
								session_selected_scenes.get(0).setScene_path("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_BenagalT20/Scenes/Three_Bugs.sum");
								session_selected_scenes.get(0).scene_load(printWriter,broadcaster);	
							}
					  }else {
						  if(whatToProcess.replace("POPULATE-", "").replace("LT-", "").replace("L3-", "")!= which_graphic_on_screen) {
								session_selected_scenes.get(0).setScene_path("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_BenagalT20/Scenes/Two_Bugs.sum");
								session_selected_scenes.get(0).scene_load(printWriter,broadcaster);	
							}
					  }
				  }
				}
				
			}else {
				if(whatToProcess.replace("POPULATE-", "").replace("LT-", "").replace("L3-", "")!= which_graphic_on_screen) {
					session_selected_scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					session_selected_scenes.get(0).scene_load(printWriter,broadcaster);	
				}
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
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "L3_MATCH-PROMO_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
		case "BUG_DB_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getBugs()).toString();
		case "SPLIT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getSplit()).toString();	
		case "BUG_DB2_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getBugs()).toString();	
		case "PROMPT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getInfobarStats()).toString();
		case "LEADERBOARD_GRAPHICS-OPTIONS": case "WICKETS_GRAPHICS-OPTIONS": case "FOURS_GRAPHICS-OPTIONS": case "SIXES_GRAPHICS-OPTIONS":
			List<Tournament> tourn_stats = CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, cricket_matches, cricketService, match,null);
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
			 
		}
		
		switch(whatToProcess) {
			case "POPULATE-SUPER_OVER": case "POPULATE-TOURNAMENT_RULES":
				populateRules(printWriter, broadcaster);
				break;
			case "POPULATE-PROMO":
				populatePromo(printWriter,match, broadcaster);
				break;
			case "POPULATE-MULTI_PARTNERSHIP":
				populateBugMultipartnership(printWriter, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
				break;
			case"POPULATE-BOUNDARIES":
				populateBugBoundaries(printWriter, Integer.valueOf(valueToProcess.split(",")[1]),match, broadcaster);
				break;
			case "POPULATE-BUG_PARTNERSHIP":
				populateBugPartnership(printWriter, valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-BUG-DISMISSAL":
				populateBugDismissal(printWriter, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-WICKETS":
				populateBugWickets(printWriter, Integer.valueOf(valueToProcess.split(",")[1]),match, broadcaster,session_configuration);
				break;
			case "POPULATE-REVIEW":
				populateBugReview(printWriter, Integer.valueOf(valueToProcess.split(",")[1]),match, broadcaster,session_configuration);
				break;	
			case "POPULATE-L3-BUG":
				populateBug(printWriter, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-BUG-BOWLER":
				populateBugBowler(printWriter, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-BUG-DB":
				for(Bugs bug : cricketService.getBugs()) {
					  if(bug.getBugId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateBugsDB(printWriter, valueToProcess.split(",")[0], bug, match, broadcaster);
					  }
					}
					break;	
			case "POPULATE-L3-SPLIT-DB":
				for(Split split : cricketService.getSplit()) {
					  if(split.getSplitId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateSplitDB(printWriter, valueToProcess.split(",")[0], split, match, broadcaster);
					  }
					}
					break;	
			case "POPULATE-L3-BUG-TOSS":
				populateToss(printWriter,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-TARGET":
				populateTarget(printWriter,valueToProcess.split(",")[0], match, broadcaster);
				break;
			 case "POPULATE-FF_GRAPHICS":
				 populateFF(whatToProcess,valueToProcess.substring(valueToProcess.lastIndexOf(",")+1),printWriter,cricketService.getAllPlayer(),session_configuration);
				 break;
			 case "POPULATE-LT_GRAPHICS":
				 populateLT(whatToProcess,valueToProcess.substring(valueToProcess.lastIndexOf(",")+1),printWriter,session_configuration);
				 break;
			 case "POPULATE-FF-MATCHID":
					populateMatchId(printWriter,valueToProcess.split(",")[0], match, broadcaster);
				 break;
			 case "POPULATE-FAIRPLAY":
				 populateFairplay(printWriter,session_configuration);
				 break;
			 case"POPULATE-FANTASY11":
				 populateFantasy(printWriter,session_configuration);
				 break;
			case "POPULATE-L3-NAMESUPER-SINGLE":
				for(NameSuper ns : cricketService.getNameSupers()) {
					  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateNameSuperSingle(printWriter, ns, match, broadcaster);
					  }
					}
				break;
			case "POPULATE-L3-NAMESUPER": 
				for(NameSuper ns : cricketService.getNameSupers()) {
				  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
					  populateNameSuper(printWriter, valueToProcess.split(",")[0], ns, match, broadcaster);
				  }
				}
				break;
			case "POPULATE-L3-NAMESUPER-PLAYER":
				populateNameSuperPlayer(printWriter, match, broadcaster, Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]));
				break;
			case "POPULATE-FF-LEADERBOARD":
				populateLeaderBoard(printWriter, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, cricket_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster, session_configuration);
				//	CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false,headToHead, cricketService, match, past_tournament_stats),
				break;
			case "POPULATE-MOST_RUNS":
				populateMostRunsTeam(printWriter, valueToProcess.split(",")[0], valueToProcess.split(",")[1],valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, cricket_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-LTMATCH_IDENT":
				populateLtMatchId(printWriter,valueToProcess.split(",")[0], match, broadcaster,cricketService.getVariousTexts());
				break;
			case "POPULATE-BUG_POWERPLAY":
				populatePowerplay(printWriter,Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-LT-BUG_HIGHLIGHT":
				populateHighlight(printWriter,Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			 case "EXCEL_FF_GRAPHICS_OPTION":
					return new ObjectMapper().writeValueAsString(CricketFunctions.ReadExcel(CricketUtil.FF_EXCEL).keySet()).toString();
			 case "EXCEL_LT_GRAPHICS_OPTION":
					return new ObjectMapper().writeValueAsString(CricketFunctions.ReadExcel(CricketUtil.LT_EXCEL).keySet()).toString();

//..................ANIMATE
			 case "ANIMATE-IN-PROMO":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "PROMO";
					break;
			 case "ANIMATE-IN-SUPER_OVER":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "SUPER_OVER";
				break;
			 case "ANIMATE-IN-TOURNAMENT_RULES":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "TOURNAMENT_RULES";
				break;	
			 case "ANIMATE-IN-BUG":
				processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "BUG";
				break;
			case "ANIMATE-IN-BUG-DISMISSAL":
				processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "BUG-DISMISSAL";
				break;
			case "ANIMATE-IN-BUG_PARTNERSHIP":
				processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "BUG_PARTNERSHIP";
				break;
			case "ANIMATE-BOUNDARIES":
				processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "BOUNDARIES";
				break;
			case "ANIMATE-FAIRPLAY":
				processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "FAIRPLAY";
				break;
			case"ANIMATE-FANTASY11":
				processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "FANTASY11";
				break;
			case "ANIMATE-IN-BUG-DB":
				processAnimation(printWriter, "In","START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "BUG-DB";
				break;
			case "ANIMATE-IN-SPLIT-DB":
				processAnimation(printWriter, "In","START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "SPLIT-DB";
				break;
			case "ANIMATE-IN-BUG-BOWLER":
				processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "BUG-BOWLER";
				break;
			case "ANIMATE-IN-MULTI_PARTNERSHIP":
				processAnimation(printWriter, "In","START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "MULTI-PARTNERSHIP";
				break;
			 case "ANIMATE-FF_GRAPHICS":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "FF_GRAPHICS";
				 break;
			 case "ANIMATE-IN-MOST":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "MOST";
				 break;
			 case "ANIMATE-IN-MATCHID":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
				which_graphic_on_screen = "MATCH_IDENT";
				 break;
			 case "ANIMATE-IN-NAMESUPER_SINGLE":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "NAMESUPER_SINGLE";
				 break;
			 case "ANIMATE-IN-NAMESUPER":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "NAMESUPER";
				 break;
			 case "ANIMATE-IN-LTMATCH_IDENT":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "L3MATCHID";
				 break;
			 case "ANIMATE-IN-BUG_HIGHLIGHT":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "BUG_HIGHLIGHT";
				 break;
			 case "ANIMATE-IN-NAMESUPER-PLAYER":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "NAMESUPER-PLAYER";
				 break;
			 case "ANIMATE-IN-BUG_POWERPLAY":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "BUG_POWERPLAY";
				 break;
			 case "ANIMATE-IN-BUG-TOSS": case"ANIMATE-IN-WICKET": case"ANIMATE-IN-REVIEW":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = whatToProcess.replace("ANIMATE-IN-", "");
				 break;
			 case "ANIMATE-IN-TARGET":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "TARGET";
				 break;
			 case "ANIMATE-IN-LEADERBOARD":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "LEADERBOARD_MOST";
				 break;
			 case "ANIMATE-LT_GRAPHICS":
				 processAnimation(printWriter, "In", "START", "EVEREST_BENGAL_T20");
					which_graphic_on_screen = "LT_GRAPHICS";
				 break;
			 case "CLEAR-ALL":
				 printWriter.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");		               
		         which_graphic_on_screen = "";
				break;
			 case "ANIMATE-OUT":
				 switch(which_graphic_on_screen) {
				 	case "PROMO":
				 		AnimateOutGraphics(printWriter, "PROMO");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
				 		break;
				 	case "SUPER_OVER":
				 		AnimateOutGraphics(printWriter, "SUPER_OVER");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
				 	case "TOURNAMENT_RULES":
				 		AnimateOutGraphics(printWriter, "TOURNAMENT_RULES");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;	
				 	case "LT_GRAPHICS":
				 		AnimateOutGraphics(printWriter, "LT_GRAPHICS");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
				 	case "BUG":
				 		AnimateOutGraphics(printWriter, "BUG");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
				 	case "BUG-DISMISSAL":
				 		AnimateOutGraphics(printWriter, "BUG-DISMISSAL");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
				 	case "BUG-DB":
				 		AnimateOutGraphics(printWriter, "BUG-DB");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
				 	case "SPLIT-DB":
				 		AnimateOutGraphics(printWriter, "SPLIT-DB");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;	
				 	case "BUG_PARTNERSHIP":
				 		AnimateOutGraphics(printWriter, "BUG_PARTNERSHIP");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
				 	case "BOUNDARIES":
				 		AnimateOutGraphics(printWriter, "BOUNDARIES");
				 		TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
				 	case "FAIRPLAY":
				 		AnimateOutGraphics(printWriter, "FAIRPLAY");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
				 		break;
				 	case "FANTASY11":
				 		AnimateOutGraphics(printWriter, "FANTASY11");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
				 		break;
				 	case "BUG-BOWLER":
				 		AnimateOutGraphics(printWriter, "BUG-BOWLER");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
				 	case "MULTI-PARTNERSHIP":
				 		AnimateOutGraphics(printWriter, "MULTI-PARTNERSHIP");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					 case "MOST":
						 AnimateOutGraphics(printWriter, "MOST");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
				 	case "LEADERBOARD_MOST":
						AnimateOutGraphics(printWriter, "LEADERBOARD_MOST");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
						
					case "FF_GRAPHICS":
						AnimateOutGraphics(printWriter, "FF_GRAPHICS");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "MATCH_IDENT":
						AnimateOutGraphics(printWriter, "MATCH_IDENT");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "NAMESUPER_SINGLE":
						AnimateOutGraphics(printWriter, "NAMESUPER_SINGLE");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "BUG_POWERPLAY":
						AnimateOutGraphics(printWriter, "BUG_POWERPLAY");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "TARGET":
						AnimateOutGraphics(printWriter, "TARGET");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "BUG-TOSS":
						AnimateOutGraphics(printWriter, "BUG-TOSS");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "NAMESUPER-PLAYER":
						AnimateOutGraphics(printWriter, "NAMESUPER-PLAYER");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "BUG_HIGHLIGHT":
						AnimateOutGraphics(printWriter, "BUG_HIGHLIGHT");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "L3MATCHID":
						AnimateOutGraphics(printWriter, "L3MATCHID");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "NAMESUPER":
						AnimateOutGraphics(printWriter, "NAMESUPER");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "WICKET":
						AnimateOutGraphics(printWriter, "WICKET");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;
					case "REVIEW":
						AnimateOutGraphics(printWriter, "REVIEW");
						TimeUnit.SECONDS.sleep(1);
						which_graphic_on_screen = "";
						break;	
				 	}
				 break;
			}
     	return null;
	}
	
	
	
	private void populateBugWickets(PrintWriter print_writer, Integer teamId, MatchAllData match, String broadcaster,Configuration config) throws InterruptedException {
		if(match.getSetup().getHomeTeamId()==teamId) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+ match.getSetup().getHomeTeam().getTeamName1()+ ";");

		}else {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+match.getSetup().getAwayTeam().getTeamName1()+ ";");

		}
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "+ " WICKETS"+ ";");
		if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + Sponsors.replace("\\\\C:", "C:") +"UTKARSH" + CricketUtil.PNG_EXTENSION + ";");
        } else {
        	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + "\\\\" + config.getPrimaryIpAddress() + CricketUtil.DOUBLE_BACKSLASH + Sponsors.replace("C:", "c") +"UTKARSH"+ CricketUtil.PNG_EXTENSION + ";");
        }

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
		 
		
	}
	private void populateBugReview(PrintWriter print_writer, Integer teamId, MatchAllData match, String broadcaster,Configuration config) throws InterruptedException {
		if(match.getSetup().getHomeTeamId()==teamId) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+ match.getSetup().getHomeTeam().getTeamName1()+ ";");

		}else {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+match.getSetup().getAwayTeam().getTeamName1()+ ";");

		}
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "+ " REVIEW"+ ";");
		if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + Sponsors.replace("\\\\C:", "C:") +"UTKARSH" + CricketUtil.PNG_EXTENSION + ";");
        } else {
        	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgSponsor " + "\\\\" + config.getPrimaryIpAddress() + CricketUtil.DOUBLE_BACKSLASH + Sponsors.replace("C:", "c") +"UTKARSH"+ CricketUtil.PNG_EXTENSION + ";");
        }

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
		 
		
	}
	private void AnimateOutGraphics(PrintWriter printWriter, String whichGraphic) throws InterruptedException {
		switch(whichGraphic) {
		case "FF_GRAPHICS": case "MATCH_IDENT": case "NAMESUPER": case "L3MATCHID": case "BUG_HIGHLIGHT": case "NAMESUPER-PLAYER": case "BUG_POWERPLAY":
		case "NAMESUPER_SINGLE": case "LEADERBOARD_MOST": case "MOST": case "BUG": case "BUG-DISMISSAL": case "BUG_PARTNERSHIP": case "BUG-BOWLER": 
		case "MULTI-PARTNERSHIP": case "BUG-DB": case "LT_GRAPHICS":case "FAIRPLAY":case "FANTASY11":case"TARGET":case"BUG-TOSS":
		case "TOURNAMENT_RULES": case "SUPER_OVER":case "BOUNDARIES":case"WICKET": case"REVIEW": case "SPLIT-DB": case "PROMO":
			processAnimation(printWriter, "Out", "START", broadcaster);
			TimeUnit.SECONDS.sleep(1);
			break;
		}	
	}
	private void processAnimation(PrintWriter printWriter, String animationName, String animationCommand, String which_broadcaster) {
		switch(which_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			break;
		}	
	}
	public void populateBugsDB(PrintWriter print_writer, String viz_scene, Bugs bug, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				if (bug.getText1() != null && bug.getText2() != null && bug.getText3() != null && bug.getText4() != null) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
									+ bug.getText1() + "       " + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
									+ bug.getText2() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
									+ bug.getText3() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
									+ bug.getText4() + ";");
				} else if (bug.getText1() != null && bug.getText2() != null && bug.getText3() != null) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
									+ bug.getText1() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "+ bug.getText3() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "+ bug.getText2() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
									+ "" + ";");
				} else if(bug.getText1() != null && bug.getText2() != null) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
									+ bug.getText1() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
									+ bug.getText2() + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
									+ "" + ";");
					print_writer
							.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
									+ "" + ";");
				}else if(bug.getText1() != null) {
					print_writer.println(
							"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
									+ bug.getText1() + ";");
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
	public void populateSplitDB(PrintWriter print_writer, String viz_scene, Split Split, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				if (Split.getText1() != null && Split.getText2() != null && Split.getText3() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo01 "+ Split.getText1() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo02 "+ Split.getText2() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo03 "+ Split.getText3() + ";");
				} else if(Split.getText1() != null && Split.getText2() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo01 "+ Split.getText1() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo02 "+ Split.getText2() + ";");
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
	private void populateBugBoundaries(PrintWriter print_writer, Integer inn_num, MatchAllData match,
			String broadcaster) throws InterruptedException {
			Inning inn= match.getMatch().getInning().get((inn_num-1));	
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "+inn.getBatting_team().getTeamName1()+";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "+"FOURS: "+inn.getTotalFours()+ ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "+"SIXES "+inn.getTotalSixes()+ "       ;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B  ;");

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
			 

	}
	private void populateToss(PrintWriter print_writer, String string, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				if(match.getSetup().getHomeTeamId()==match.getSetup().getTossWinningTeam()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "
							+ match.getSetup().getHomeTeam().getTeamName4()+ " WON TOSS ;");			
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "
							+ match.getSetup().getAwayTeam().getTeamName4()+ " WON TOSS ;");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "+"CHOSE TO "+match.getSetup().getTossWinningDecision()+ ";");

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
					
				if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
					if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails " + " NEED " + 
								CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+  Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS"  +";");
						
					}else {
						if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase()+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "  
									+ " NEED " +CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+  CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS"  +";");
		
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase()+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "  
									+ " NEED " +CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+ Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS"   +";");
	
							}
					}
				}else {
					if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase()+";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "  
								+ " NEED " +CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+  CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" + " ("+ match.getSetup().getTargetType().toUpperCase() +")"  +";");
						}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase()+";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "  
								+ " NEED " +CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM "+  Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS" + " ("+ match.getSetup().getTargetType().toUpperCase() +")" +";");


					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 109.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Infobar*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			
	}
	public void populateBugDismissal(PrintWriter print_writer, String viz_scene, int whichInning, String statsType,
			int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
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
													+ bc.getPlayer().getFull_name() + "       " + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
													+ bc.getHowOutText() + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
													+ "  " + bc.getRuns() + " (" + bc.getBalls() + ")" + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
													+ "" + ";");
								}
							}
							break;
						}
					}
				}

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 40.0;");
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
		case "EVEREST_BENGAL_T20":
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
													+ bc.getPlayer().getFull_name() + "       " + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
													+ "4s : " + bc.getFours() + " 6s : " + bc.getSixes() + ";");

									if (bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println(
												"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
														+ "  " + bc.getRuns() + "* (" + bc.getBalls() + ")" + ";");
									} else {
										print_writer.println(
												"LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
														+ "  " + bc.getRuns() + " (" + bc.getBalls() + ")" + ";");
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
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 40.0;");
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
		case "EVEREST_BENGAL_T20":
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
													+ boc.getPlayer().getFull_name() + "       " + ";");
									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
													+ CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " Overs" + ";");

									print_writer
											.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
													+ boc.getWickets() + slashOrDash + boc.getRuns() + ";");
									print_writer
									.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
											+ "" + ";");
									
								}
							}
							break;
						}

					}

				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 40.0;");
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
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
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
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						String Left_Batsman ="",Right_Batsman="";
						
						for (BattingCard hs : inn.getBattingCard()) {
							if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								if(hs.getStatus()!=null &&  !hs.getStatus().isEmpty() && hs.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									Left_Batsman = hs.getPlayer().getTicker_name();
								}else {
									Left_Batsman = hs.getPlayer().getTicker_name();
								}
								
							}
							if(hs.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								if(hs.getStatus()!=null &&  !hs.getStatus().isEmpty() && hs.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									Right_Batsman = hs.getPlayer().getTicker_name();
								}else {
									Right_Batsman = hs.getPlayer().getTicker_name();
								}
							}

						}
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + " & " + Right_Batsman + ";");
						
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
	public void populateRules(PrintWriter print_writer,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 190.0;");
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
	public void populatePromo(PrintWriter printWriter,MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
					+ "FINAL" + ";");
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
					+ "BENGAL PRO T20 LEAGUE 2024" + ";");
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$LeftLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
					+ "Videos/Logos/Left/MALDA/000000.dds;");
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Right$RightLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
					+ "Videos/Logos/Right/MURSHIDABAD/000000.dds;");
			
//			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
//					match.getSetup().getHomeTeam().getTeamName1() + ";");
//			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
//					match.getSetup().getAwayTeam().getTeamName1() + ";");
			printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "UP NEXT - 6:30 PM - EDEN GARDENS, KOLKATA "
					+ ";");
			
			printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 190.0;");
			printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateBugMultipartnership(PrintWriter print_writer, String viz_scene,int whichinning, int partnership, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == whichinning) {
						String Left_Batsman ="",Right_Batsman="";
						
						for (BattingCard hs : inn.getBattingCard()) {
							if(hs.getPlayerId() == inn.getPartnerships().get(partnership - 1).getFirstBatterNo()) {
								if(hs.getStatus()!=null &&  !hs.getStatus().isEmpty() &&hs.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									Left_Batsman = hs.getPlayer().getTicker_name();
								}else {
									Left_Batsman = hs.getPlayer().getTicker_name();
								}
								
							}
							if(hs.getPlayerId() == inn.getPartnerships().get(partnership - 1).getSecondBatterNo()) {
								if(hs.getStatus()!=null &&  !hs.getStatus().isEmpty() && hs.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									Right_Batsman = hs.getPlayer().getTicker_name();
								}else {
									Right_Batsman = hs.getPlayer().getTicker_name();
								}
							}
						 }
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + Left_Batsman + " " + 
								inn.getPartnerships().get(partnership - 1).getFirstBatterRuns() + "(" + 
								inn.getPartnerships().get(partnership - 1).getFirstBatterBalls() + ")" + ";");
						if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 1) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "st WICKET PARTNERSHIP" + ";");
						}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 2) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "nd WICKET PARTNERSHIP" + ";");
						}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 3) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "rd WICKET PARTNERSHIP" + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + (inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "th WICKET PARTNERSHIP" + ";");
						}
						if(inn.getPartnerships().get(inn.getPartnerships().size() - 1).getPartnershipNumber()==partnership) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
									inn.getPartnerships().get(partnership - 1).getTotalRuns() + "* (" +  inn.getPartnerships().get(partnership - 1).getTotalBalls() + ")" + ";");
							
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + 
									inn.getPartnerships().get(partnership - 1).getTotalRuns() + " (" +  inn.getPartnerships().get(partnership - 1).getTotalBalls() + ")" + ";");
							
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + 
								Right_Batsman + " " + 
								inn.getPartnerships().get(partnership - 1).getSecondBatterRuns() + "(" + 
							   inn.getPartnerships().get(partnership - 1).getSecondBatterBalls() + ")" + ";");
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
	private void populateLT(String whatToProcess, String ValueToProcess, PrintWriter printWriter, Configuration config) throws InterruptedException {
        Map<String, Object> rowData =  CricketFunctions.ReadExcel(CricketUtil.LT_EXCEL).get(ValueToProcess);
        System.out.println(rowData.toString());
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
    	        (rowData.get("HEADER") != null ? rowData.get("HEADER").toString().trim() : "") + ";");
        
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Right$RightLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
				+ "Videos/Logos/Right/" + (rowData.get("LOGO") != null ? rowData.get("LOGO").toString().trim() : "") +"/000000.dds;");
		
        int cols = rowData.get("COLS") != null ? (rowData.get("COLS") instanceof Number ? ((Number) rowData.get("COLS")).intValue() : Integer.parseInt(rowData.get("COLS").toString().trim())) : 0;
        int rows = rowData.get("ROWS") != null ? (rowData.get("ROWS") instanceof Number ? ((Number) rowData.get("ROWS")).intValue() : Integer.parseInt(rowData.get("ROWS").toString().trim())) : 0;
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectRows " + cols + " ;");
        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (rows - 1) + " ;");

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
	private void populateFantasy(PrintWriter printWriter, Configuration config) throws InterruptedException {
		Map<String, Object> rowData = CricketFunctions.Read_Excel(CricketUtil.FANTASY);
		if (rowData == null) {
            System.err.println("No header data found.");
            return;
        }
		printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCap " + "0" + ";");

			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + kpi_photo_path +
	            		(rowData.get("Photo_Name") != null ? rowData.get("Photo_Name").toString().trim() : "") + CricketUtil.PNG_EXTENSION + ";");
	        } else {
//	            if (!new File("\\\\" + config.getPrimaryIpAddress() + CricketUtil.DOUBLE_BACKSLASH + kpi_photo_path + 
//	            		(rowData.get("Photo_Name") != null ? rowData.get("Photo_Name").toString().trim() : "") + CricketUtil.PNG_EXTENSION).exists()) {
//	                this.status = CricketUtil.UNSUCCESSFUL;
//	            }
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\" + config.getPrimaryIpAddress() + CricketUtil.DOUBLE_BACKSLASH + kpi_photo_path.replace("C:", "c") +
	            		 (rowData.get("Photo_Name") != null ? rowData.get("Photo_Name").toString().trim() : "") + CricketUtil.PNG_EXTENSION + ";");
	        }
		   printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " +
                   (rowData.get("Header") != null ? rowData.get("Header").toString().trim() : "") + ";");
           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " +
                   (rowData.get("Sub-header") != null ? rowData.get("Sub-header").toString().trim() : "") + ";");

           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBigText  ;");
           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (rowData.size() - 5) + " ;");
           // Header of table
//           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " +
//                   (rowData.get("Title_head_1") != null ? rowData.get("Title_head_1").toString().trim() : "") + ";");
//           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " +
//                   (rowData.get("Title_head_2") != null ? rowData.get("Title_head_2").toString().trim() : "") + ";");

           // Body of table
           for (int i = 1; i <= (rowData.size() - 5); i++) { 
        	   printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + i + " 0" + ";");
        	   printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + i + " " + ";");
        	   printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "B " + ";");
               printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + i + " " + (rowData.get("Playername_" + i) != null ? rowData.get("Playername_" + i).toString().trim() : "") + ";");
               printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "A " + ( rowData.get("PTS_" + i) != null ? rowData.get("PTS_" + i).toString().trim() : "") + ";");
               printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "B ;");
               printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + i +" "+ ( rowData.get("Team_" + i) != null ? rowData.get("Team_" + i).toString().trim() : "")+" ;");


          }
		  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 150.0;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		  printWriter.
		  println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		  printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		  TimeUnit.SECONDS.sleep(1);
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
	}
	private void populateFairplay(PrintWriter printWriter, Configuration config) throws InterruptedException {
		Map<String, Object> rowData = CricketFunctions.Read_Excel(CricketUtil.FAIRPLAY);
		if (rowData == null) {
            System.err.println("No header data found.");
            return;
        }

		   printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " +
                   (rowData.get("Header") != null ? rowData.get("Header").toString().trim() : "") + ";");
           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " +
                   (rowData.get("Sub-header") != null ? rowData.get("Sub-header").toString().trim() : "") + ";");

           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBigText  ;");

           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (rowData.size() - 4) + " ;");

           // Header of table
           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " +
                   (rowData.get("Title_head_1") != null ? rowData.get("Title_head_1").toString().trim() : "") + ";");
           printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " +
                   (rowData.get("Title_head_2") != null ? rowData.get("Title_head_2").toString().trim() : "") + ";");

           // Body of table
           for (int i = 1; i <= (rowData.size() - 4); i++) { 
       
               printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + i + " " + (rowData.get("Teamname_" + i) != null ? rowData.get("Teamname_" + i).toString().trim() : "") + ";");
               printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "A " + ( rowData.get("PTS_" + i) != null ? rowData.get("PTS_" + i).toString().trim() : "") + ";");
           }
		  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 150.0;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		  printWriter.
		  println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
		  printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		  TimeUnit.SECONDS.sleep(1);
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
	}
	private void populateFF(String whatToProcess, String ValueToProcess, PrintWriter printWriter, List<Player> allPlayer, Configuration config) throws InterruptedException {
	        Map<String, Object> rowData = CricketFunctions.ReadExcel(CricketUtil.FF_EXCEL).get(ValueToProcess);
	
	        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
	    	        (rowData.get("HEADER") != null ? rowData.get("HEADER").toString().trim() : "") + ";");
	        
	        if ((rowData.get("IS NAME?") != null ? rowData.get("IS NAME?").toString().trim() : "").toString().equalsIgnoreCase("Y")) {
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " +  (rowData.get("SUB HEADER") != null ? rowData.get("SUB HEADER").toString().trim() : "") + ";");
	        } else {
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead  ;");
	        }
	        
            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBigText  ;");

	        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$LeftLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
					+ "Videos/Logos/Left/" + (rowData.get("LOGO") != null ? rowData.get("LOGO").toString().trim() : "") +"/000000.dds;");
			
			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + kpi_photo_path +
	            		(rowData.get("PHOTO") != null ? rowData.get("PHOTO").toString().trim() : "") + CricketUtil.PNG_EXTENSION + ";");
	        } else {
//	            if (!new File("\\\\" + config.getPrimaryIpAddress() + CricketUtil.DOUBLE_BACKSLASH + kpi_photo_path + 
//	            		(rowData.get("PHOTO") != null ? rowData.get("PHOTO").toString().trim() : "") + CricketUtil.PNG_EXTENSION).exists()) {
//	                this.status = CricketUtil.UNSUCCESSFUL;
//	            }
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\" + config.getPrimaryIpAddress() + CricketUtil.DOUBLE_BACKSLASH + kpi_photo_path.replace("C:", "c") +
	            		CricketUtil.DOUBLE_BACKSLASH + (rowData.get("PHOTO") != null ? rowData.get("PHOTO").toString().trim() : "") + CricketUtil.PNG_EXTENSION + ";");
	        }
			
			int cols = rowData.get("COLS") != null ? (rowData.get("COLS") instanceof Number ? ((Number) rowData.get("COLS")).intValue() : Integer.parseInt(rowData.get("COLS").toString().trim())) : 0;
            int rows = rowData.get("ROWS") != null ? (rowData.get("ROWS") instanceof Number ? ((Number) rowData.get("ROWS")).intValue() : Integer.parseInt(rowData.get("ROWS").toString().trim())) : 0;
            
	        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectRows " + Math.abs(2 - cols) + " ;");
	        printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (rows - 1) + " ;");
	
	        // Header of table
	        for (int j = 1; j <= cols; j++) {
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead0" + j + " " + 
	            		(rowData.get("H" + j) != null ? rowData.get("H" + j).toString().trim() : "") + ";");
	        }
	
	        // Body of table
	        for (int i = 1; i <= rows; i++) {
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + i + " " + (rowData.get("R" + i + ".1") != null ? rowData.get("R" + i + ".1").toString().trim() : "") + ";");
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "A " + (rowData.get("R" + i + ".2") != null ? rowData.get("R" + i + ".2").toString().trim() : "") + ";");
	            printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + i + "B " + (rowData.get("R" + i + ".3") != null ? rowData.get("R" + i + ".3").toString().trim() : "") + ";");
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
	private void populateMatchId(PrintWriter printWriter, String string, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
						+ match.getSetup().getMatchIdent().toUpperCase() + ";");
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
						+ match.getSetup().getTournament() + ";");
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$LeftLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
						+ "Videos/Logos/Left/" + replaceName(match.getSetup().getHomeTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Right$RightLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
						+ "Videos/Logos/Right/" + replaceName(match.getSetup().getAwayTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
				
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
						match.getSetup().getHomeTeam().getTeamName1() + ";");
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
						match.getSetup().getAwayTeam().getTeamName1() + ";");
				printWriter.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo " + "LIVE FROM "
						+ match.getSetup().getVenueName().toUpperCase() + ";");
				
				  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 150.0;");
				  //printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  printWriter.
				  println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				  printWriter.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				  TimeUnit.SECONDS.sleep(1);
				  //printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				  printWriter.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				  printWriter.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				 

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}

	}
	private void populateLtMatchId(PrintWriter print_writer, String string, MatchAllData match,
			String session_selected_broadcaster,List<VariousText> vartxt) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$LeftLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
						+ "Videos/Logos/Left/" + replaceName(match.getSetup().getHomeTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Right$RightLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
						+ "Videos/Logos/Right/" + replaceName(match.getSetup().getAwayTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
				
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
	private void populateHighlight(PrintWriter print_writer,int whichInning,  MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				if (whichInning == 1) {
					
					if (match.getMatch().getInning().get(0).getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamName4().toUpperCase() + " - " + 
								match.getMatch().getInning().get(0).getTotalRuns() + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(),
										match.getMatch().getInning().get(0).getTotalBalls()) + ")" + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamName4().toUpperCase() + " - " + 
								match.getMatch().getInning().get(0).getTotalRuns() + "-"
								+ match.getMatch().getInning().get(0).getTotalWickets() + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(),
										match.getMatch().getInning().get(0).getTotalBalls()) + ")" + ";");
					}
				} else {
					if (match.getMatch().getInning().get(1).getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + " - " + 
								match.getMatch().getInning().get(1).getTotalRuns() + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(),
										match.getMatch().getInning().get(1).getTotalBalls()) + ")" + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + " - " + 
								match.getMatch().getInning().get(1).getTotalRuns() + "-"
								+ match.getMatch().getInning().get(1).getTotalWickets() + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(),
										match.getMatch().getInning().get(1).getTotalBalls()) + ")" + ";");
					}
					
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 65.0;");
				//print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
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
	private void populatePowerplay(PrintWriter print_writer,int whichInning,  MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				for (Inning inn : match.getMatch().getInning()) {
					if (whichInning == 1) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamName4() + "  "
								+ getPowerPlayScore(match,inn, whichInning, match.getEventFile().getEvents()) + ";");
					} else if (whichInning == 2) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDetails "
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + "  "
								+ getPowerPlayScore(match,inn, whichInning, match.getEventFile().getEvents()) + ";");
					}
				}
				
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
	private void populateNameSuper(PrintWriter print_writer, String string,NameSuper ns, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				if(ns.getSponsor() != null) {
					if(ns.getSponsor().equalsIgnoreCase(CricketUtil.HOME)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
								+ "Videos/Lt_Logos/" + replaceName(match.getSetup().getHomeTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
					}else if(ns.getSponsor().equalsIgnoreCase(CricketUtil.AWAY)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
								+ "Videos/Lt_Logos/" + replaceName(match.getSetup().getAwayTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
								+ "Videos/Lt_Logos/EVENT/000000.dds;");
					}
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
							+ "Videos/Lt_Logos/EVENT/000000.dds;");
				}
				
				if (ns.getFirstname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
							+ "" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
							+ ns.getSurname() + ";");
				}else if (ns.getSurname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
							+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
							+ ns.getFirstname()+ ";");
				}else if (ns.getFirstname() != null && ns.getSurname() != null)  {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
							+ns.getFirstname() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
							+  ns.getSurname() + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
						+ ns.getSubLine().toUpperCase() + ";");
				
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
	
	private void populateNameSuperPlayer(PrintWriter print_writer, MatchAllData match,
			String session_selected_broadcaster,int TeamId,String captainWicketKeeper, int playerId) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				String Home_or_Away = "";

				if (TeamId == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
							+ "Videos/Lt_Logos/" + replaceName(match.getSetup().getHomeTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
					Home_or_Away = match.getSetup().getHomeTeam().getTeamName3();
					for (Player hs : match.getSetup().getHomeSquad()) {
						if (playerId == hs.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
									+ hs.getFirstname() + ";");
							if (hs.getSurname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
										+ "" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
										+ hs.getSurname() + ";");
							}
							
							if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
										+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + ";");
							}
						}

					}
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
							+ "Videos/Lt_Logos/" + replaceName(match.getSetup().getAwayTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
					Home_or_Away = match.getSetup().getAwayTeam().getTeamName3().toUpperCase();
					for (Player as : match.getSetup().getAwaySquad()) {
						if (playerId == as.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
									+ as.getFirstname() + ";");
							if (as.getSurname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
										+ "" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
										+ as.getSurname() + ";");
							}
							if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBottomInfo "
										+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + ";");
							}
						}
					}
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
	private void populateNameSuperSingle(PrintWriter print_writer,NameSuper ns, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_BENGAL_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				if(ns.getSponsor() != null) {
					if(ns.getSponsor().equalsIgnoreCase(CricketUtil.HOME)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
								+ "Videos/Lt_Logos/" + replaceName(match.getSetup().getHomeTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
					}else if(ns.getSponsor().equalsIgnoreCase(CricketUtil.AWAY)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
								+ "Videos/Lt_Logos/" + replaceName(match.getSetup().getAwayTeam().getTeamName1()).toUpperCase() +"/000000.dds;");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
								+ "Videos/Lt_Logos/EVENT/000000.dds;");
					}
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
							+ "Videos/Lt_Logos/EVENT/000000.dds;");
				}
				
				if (ns.getFirstname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
							+ "" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
							+ ns.getSurname() + ";");
				} else if (ns.getSurname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
							+ ns.getFirstname() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
							+ "" + ";");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "
							+ ns.getFirstname() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
							+ ns.getSurname() + ";");
				}				
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
	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_no=0;
			switch(StatType.toUpperCase()) {
			case "MOST_RUNS_DATA":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
		        List<Tournament> top5Batsmen = tournament.subList(0, Math.min(5, tournament.size()));
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST RUNS" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= top5Batsmen.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(top5Batsmen.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
										team.get(top5Batsmen.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										top5Batsmen.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}else {
//								if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4()
//										+ CricketUtil.DOUBLE_BACKSLASH + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//									this.status = CricketUtil.UNSUCCESSFUL;
//								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
										team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								System.out.println();
							}
							
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight0" + row_no + " SET " + 0 + "\0");
						}
						System.out.println("i "+i+"  "+top5Batsmen.get(i).getPlayer().getTicker_name());
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_no + " " + top5Batsmen.get(i).getPlayer().getFull_name() + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row_no + " " + team.get(top5Batsmen.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "A " + team.get(top5Batsmen.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + row_no + "B " + " " + ";");
						
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (top5Batsmen.size()-1) + ";");

				break;
			case "MOST_RUNS":
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCap " + "1" + ";");
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				top5Batsmen = tournament.subList(0, Math.min(5, tournament.size()));
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "TIGER CAP BATTER" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (top5Batsmen.size()-1) + ";");
				Tournament tn = top5Batsmen.stream().filter(tm->tm.getPlayerId()==playerid).findAny().orElse(null);

				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
								team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
								tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}else {
//						if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4()
//								+ CricketUtil.DOUBLE_BACKSLASH + tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//							this.status = CricketUtil.UNSUCCESSFUL;
//						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
								team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
								tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
					int index=0;
					for(int i = 0; i <= top5Batsmen.size() - 1 ; i++) { 
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "A " +  top5Batsmen.get(i).getRuns()+ ";");

						if(top5Batsmen.get(i).getPlayerId() == playerid) {
							index = i+1;
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "A " +  top5Batsmen.get(i).getRuns()+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "B ;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) +" "+ team.get(top5Batsmen.get(i).getPlayer().getTeamId() - 1).getTeamName1()+" ;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + (i+1) + " " + top5Batsmen.get(i).getPlayer().getFull_name()+ ";");	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + (i+1)  + " 0" + "\0");
				
					}
					
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + index  + " 1" + "\0");

				break;
			case "MOST_WICKETS":
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCap " + "2" + ";");
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "TIGER CAP BOWLER" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				
				top5Batsmen = tournament.subList(0, Math.min(5, tournament.size()));
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (top5Batsmen.size()-1) + ";");
				tn = top5Batsmen.stream().filter(tm->tm.getPlayerId()==playerid).findAny().orElse(null);


					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
								team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
								tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}else {
//						if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4()
//								+ CricketUtil.DOUBLE_BACKSLASH + tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//							this.status = CricketUtil.UNSUCCESSFUL;
//						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
								team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
								tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
					index=0;
					
					for(int i = 0; i <= top5Batsmen.size() - 1 ; i++) { 
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "A " +  top5Batsmen.get(i).getWickets()+ ";");

						if(top5Batsmen.get(i).getPlayerId() == playerid) {
							index = i+1;
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "A " +  top5Batsmen.get(i).getWickets()+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "B ;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) +" "+ team.get(top5Batsmen.get(i).getPlayer().getTeamId() - 1).getTeamName1()+" ;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + (i+1) + " " + top5Batsmen.get(i).getPlayer().getFull_name()+ ";");	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + (i+1)  + " 0" + "\0");
				
					}
					
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + index  + " 1" + "\0");

				break;
			case "MOST_FOURS":
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCap " + "0" + ";");
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST FOURS" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				top5Batsmen = tournament.subList(0, Math.min(5, tournament.size()));
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (top5Batsmen.size()-1) + ";");
				tn = top5Batsmen.stream().filter(tm->tm.getPlayerId()==playerid).findAny().orElse(null);


					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
								team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
								tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}else {
//						if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4()
//								+ CricketUtil.DOUBLE_BACKSLASH + tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//							this.status = CricketUtil.UNSUCCESSFUL;
//						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
								team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
								tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
					index=0;
					
					for(int i = 0; i <= top5Batsmen.size() - 1 ; i++) { 
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "A " +  top5Batsmen.get(i).getFours()+ ";");
						if(top5Batsmen.get(i).getPlayerId() == playerid) {
							index = i+1;
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "B ;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) +" "+ team.get(top5Batsmen.get(i).getPlayer().getTeamId() - 1).getTeamName1()+" ;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + (i+1) + " " + top5Batsmen.get(i).getPlayer().getFull_name()+ ";");	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + (i+1)  + " 0" + "\0");
				
					}
					
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + index  + " 1" + "\0");

				break;
			case "MOST_SIXES":

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vCap " + "0" + ";");
				
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "MOST SIXES" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + match.getSetup().getTournament() + ";");
				top5Batsmen = tournament.subList(0, Math.min(5, tournament.size()));
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (top5Batsmen.size()-1) + ";");
				tn = top5Batsmen.stream().filter(tm->tm.getPlayerId()==playerid).findAny().orElse(null);


					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
								team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
								tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}else {
//						if(!new File("\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path +team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4()
//								+ CricketUtil.DOUBLE_BACKSLASH + tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//							this.status = CricketUtil.UNSUCCESSFUL;
//						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + "\\\\"+config.getPrimaryIpAddress()+CricketUtil.DOUBLE_BACKSLASH+local_photo_path  + 
								team.get(tn.getPlayer().getTeamId() -1 ).getTeamName4() + CricketUtil.DOUBLE_BACKSLASH + 
								tn.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
					index=0;
					
					for(int i = 0; i <= top5Batsmen.size() - 1 ; i++) { 
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02" + (i+1) + "A " +  top5Batsmen.get(i).getSixes()+ ";");
						if(top5Batsmen.get(i).getPlayerId() == playerid) {
							index = i+1;
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + (i+1) + "B ;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) +" "+ team.get(top5Batsmen.get(i).getPlayer().getTeamId() - 1).getTeamName1()+" ;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + (i+1) + " " + top5Batsmen.get(i).getPlayer().getFull_name()+ ";");	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + (i+1)  + " 0" + "\0");
						
					}
					
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + index  + " 1" + "\0");

				break;
			}
			TimeUnit.SECONDS.sleep(2);
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$LeftLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
					+ "Videos/Lt_Logos/EVENT/000000.dds;");
			
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
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
						+ "MOST RUNS" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
						+ match.getSetup().getTournament() + ";");
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectRows 4;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + row_no + ";");

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$LeftLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
						+ "Videos/Lt_Logos/EVENT/000000.dds;");
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 PLAYERS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 MATCHES;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 RUNS;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 S/R;");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1().equalsIgnoreCase(StatType.toUpperCase())) {
						if(tournament.get(i).getRuns() > 0) {
							row_no = row_no + 1;
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
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + (row_no - 1) + ";");

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
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader "
						+ "MOST WICKETS" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead "
						+ match.getSetup().getTournament() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$LeftLogo*FUNCTION*IMAGESEQUENCE2 SET PATH D:/Everest_Cricket/Everest_BenagalT20/"
						+ "Videos/Lt_Logos/EVENT/000000.dds;");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectRows 4;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + row + ";");
				
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectColumm " + row + ";");
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
	public static String replaceName(String name) {
	    switch (name) {
	        case "ADAMAS HOWRAH WARRIORS":
	            return "HOWRAH";
	        case "HARBOUR DIAMONDS":
	            return "HARBOUR";
	        case "LUX SHYAM KOLKATA TIGERS":
	            return "KOLKATA";
	        case "MURSHIDABAD KINGS":
	            return "MURSHIDABAD";
	        case "MURSHIDABAD KUEENS":
	            return "MURSHIDABAD";    
	        case "SHRACHI RARH TIGERS":
	            return "RARH";
	        case "RASHMI MEDINIPUR WIZARDS":
	            return "MEDINIPUR";
	        case "SERVOTECH SILIGURI STRIKERS":
	            return "SILIGURI";
	        case "SOBISCO SMASHERS MALDA":
	            return "MALDA";
	        default:
	            return null;
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