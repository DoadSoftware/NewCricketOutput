package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Configuration;
import com.cricket.model.Event;
import com.cricket.model.EverestBugs;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EVEREST_SPL extends Scene{ 
	
	public String broadcaster = "SPL";
	public String status;
	public String slashOrDash = "-";
	public String photo_path = "C:\\\\Images\\\\SPL\\\\Photos\\\\";
	public String logo_path = "C:\\\\Images\\\\SPL\\\\Logos\\\\";
	public String base_path = "D:\\DOAD_In_House_Everest\\Everest_Cricket\\Everest_Saurashtra_Primier_League\\Textures\\Base\\";
	public String Logo_BW_path = "C:\\\\Images\\\\SPL\\\\Logo_BW\\\\";
	public String Logo_Grey_path = "C:\\\\Images\\\\SPL\\\\Logo_Grey\\\\";
	public String icons_path = "C:\\\\Images\\\\LEGENDS\\\\Icons\\\\";
	public String which_graphic_on_screen = "";
	public static List<BestStats> top_ten_beststat = new ArrayList<BestStats>();
	public EVEREST_SPL() {
		super();
	}

	public EVEREST_SPL(String scene_path, String which_Layer) {
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
			List<HeadToHeadPlayer> headToHead, List<Tournament> past_tournament_stats) throws Exception
	{
		System.out.println(whatToProcess);
		switch (whatToProcess) {
		 case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG":case "ANIMATE-IN-BUG-BOWLER":case "ANIMATE-IN-BUG-DB":
		 case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_PARTNERSHIP":case "ANIMATE-IN-MULTI_PARTNERSHIP":
		 case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-BUG-TOSS":case "ANIMATE-IN-NAMESUPER":case "ANIMATE-IN-NAMESUPER-PLAYER":
		 case "ANIMATE-IN-NAMESUPER_SINGLE": case "ANIMATE-IN-LEADERBOARD":case "ANIMATE-IN-HIGHEST_SCORE":case "ANIMATE-IN-BEST_FIGURES":
			 processAnimation(print_writer, "In", "START", "SPL");
			 which_graphic_on_screen = "BugTARGET";
			 break;
		 case "ANIMATE-OUT":
			 AnimateOutGraphics(print_writer, "MULTI-PARTNERSHIP");
			 TimeUnit.SECONDS.sleep(1);
			 which_graphic_on_screen = "";
			 break;
		 case "CLEAR-ALL":
			 print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
             print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
             print_writer.println("LAYER3*EVEREST*SINGLE_SCENE CLEAR;");
             which_graphic_on_screen = "";
			break;
		case "POPULATE-BUG_POWERPLAY": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-BUG_PARTNERSHIP": case "POPULATE-MULTI_PARTNERSHIP":
		case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-BUG": case "POPULATE-L3-BUG-BOWLER":case "POPULATE-L3-BUG-DB": case "POPULATE-L3-NAMESUPER": 
		case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-L3-NAMESUPER-SINGLE":case "POPULATE-L3-BUG-TOSS":case "POPULATE-L3-TARGET":case "POPULATE-L3-Result":
		case "POPULATE-FF-LEADERBOARD":case "POPULATE-HIGHEST_SCORE":case "POPULATE-BEST_FIGURES":
			if(which_graphic_on_screen != "") {
				AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
				TimeUnit.MILLISECONDS.sleep(200);
				print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");	
			}
			scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
			scenes.get(0).scene_load(print_writer,broadcaster);
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-BUG_POWERPLAY": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-BUG_PARTNERSHIP": 
			case "POPULATE-MULTI_PARTNERSHIP":case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-BUG": 
			case "POPULATE-L3-BUG-BOWLER":case "POPULATE-L3-BUG-DB":case "POPULATE-L3-BUG-TOSS":
			case "POPULATE-L3-TARGET":case "POPULATE-L3-RESULT":				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 ;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A ;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B ;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C ;");
				
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
			case "POPULATE-L3-BUG":
				populateBug(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-BUG-BOWLER":
				populateBugBowler(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-BUG-DB":
				scenes.get(0).setScene_path("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_Saurashtra_Primier_League/Scenes/Bug_DoubleLine.sum");
		        scenes.get(0).scene_load(print_writer,broadcaster);
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
				populateToss(print_writer,valueToProcess.split(",")[0], match, broadcaster);
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
		break;
		case "BUG_GRAPHICS-OPTIONS": case "HOWOUT_GRAPHICS-OPTIONS":
			return match;
		case "NAMESUPER_GRAPHICS-OPTIONS": case "NAMESUPER_GRAPHICS_SINGLELINE-OPTIONS":case "MULTI_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "BUG_DB_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getEverestBugs()).toString();
		case "MOST_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getTeams()).toString();
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
		}
		return null;
		
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
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$Logo*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 ;");
			switch(StatType.toUpperCase()) {
			case "MOST_RUNS_DATA":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "MOST RUNS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
									tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						if (tournament.get(i).getPlayer().getFirstname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else if (tournament.get(i).getPlayer().getSurname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_RUNS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "MOST RUNS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
						  print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
							team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
							tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						  print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						if (tournament.get(i).getPlayer().getFirstname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else if (tournament.get(i).getPlayer().getSurname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + tournament.get(i).getRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_WICKETS":
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "MOST WICKETS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
									tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						
						if (tournament.get(i).getPlayer().getFirstname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else if (tournament.get(i).getPlayer().getSurname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + tournament.get(i).getWickets() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_FOURS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "MOST FOURS " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
									tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						if (tournament.get(i).getPlayer().getFirstname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else if (tournament.get(i).getPlayer().getSurname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + tournament.get(i).getFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
					}
				}
				break;
			case "MOST_SIXES":
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "MOST SIXES " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(tournament.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
									tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						if (tournament.get(i).getPlayer().getFirstname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else if (tournament.get(i).getPlayer().getSurname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + tournament.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " " + tournament.get(i).getPlayer().getSurname() + ";");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " " + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + tournament.get(i).getSixes() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
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
	public void populateLeaderBoardScore(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<BestStats> top_ten_beststat,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_no=0;
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + "TLogo" + 
					CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logos$Left$Logo*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader01 ;");

			switch(StatType.toUpperCase()) {
			case "POPULATE-HIGHEST_SCORE":
				Collections.sort(top_ten_beststat,new CricketFunctions.BatsmanBestStatsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "HIGHEST INDIVIDUAL SCORE" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= top_ten_beststat.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(top_ten_beststat.get(i).getPlayerId() == playerid) {
						  print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
							team.get(top_ten_beststat.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
							top_ten_beststat.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
						  print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						if (top_ten_beststat.get(i).getPlayer().getFirstname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else if (top_ten_beststat.get(i).getPlayer().getSurname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getSurname() + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " v " + team.get(top_ten_beststat.get(i).getOpponentTeam().getTeamId() - 1).getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + top_ten_beststat.get(i).getBestEquation() / 2 +(top_ten_beststat.get(i).getStatus().equalsIgnoreCase("NOT OUT")?"*":"")+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
					}
				}
				break;
			case "POPULATE-BEST_FIGURES":
				Collections.sort(top_ten_beststat,new CricketFunctions.BowlerBestStatsComparator());
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader02 " + "BEST FIGURES " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getSetup().getTournament() + ";");
				for(int i = 0; i <= top_ten_beststat.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(top_ten_beststat.get(i).getPlayerId() == playerid) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
									team.get(top_ten_beststat.get(i).getPlayer().getTeamId() -1 ).getTeamName4() +"\\\\"+ 
									top_ten_beststat.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 1 + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight0" + row_no + " " + 0 + ";");
						}
						
						if (top_ten_beststat.get(i).getPlayer().getFirstname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else if (top_ten_beststat.get(i).getPlayer().getSurname() == null) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " ;");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getFirstname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName0" + row_no + " " + top_ten_beststat.get(i).getPlayer().getSurname() + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row_no + " v " + team.get(top_ten_beststat.get(i).getOpponentTeam().getTeamId() - 1).getTeamName1() + ";");
						
						if(top_ten_beststat.get(i).getBestEquation() % 1000 > 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + ((top_ten_beststat.get(i).getBestEquation() / 1000) +1) 
									+ "-" + (1000 - (top_ten_beststat.get(i).getBestEquation() % 1000)) + ";");

						}
						else if(top_ten_beststat.get(i).getBestEquation() % 1000 < 0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "A " + ((top_ten_beststat.get(i).getBestEquation() / 1000) +1) 
									+ "-" + (top_ten_beststat.get(i).getBestEquation() / 1000) + "-" + Math.abs(top_ten_beststat.get(i).getBestEquation()) + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tData0" + row_no + "B " + " " + ";");
						
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
	

	private void populateTarget(PrintWriter print_writer, String string, MatchAllData match, String broadcaster2) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Target's inning is null";
		} else {
			String result = "";

			if (match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
			    // Get the overs and balls
			    String[] oversParts = CricketFunctions.GetTargetData(match).getTargetOvers().split("\\.");
			    int overs = Integer.parseInt(oversParts[0]);
			    int balls = (oversParts.length > 1) ? Integer.parseInt(oversParts[1]) : 0;

			    int totalBalls = (overs * 6) + balls;

			    if (CricketFunctions.GetTargetData(match).getTargetOvers().equalsIgnoreCase("1")) {
			        result = match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + " NEED " + 
			                CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + totalBalls + " BALLS";
			    } else {
			    	result = match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + " NEED " + 
		                    CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + totalBalls + " BALLS";
			    }
			} else {
			    String[] oversParts = CricketFunctions.GetTargetData(match).getTargetOvers().split("\\.");
			    int overs = Integer.parseInt(oversParts[0]);
			    int balls = (oversParts.length > 1) ? Integer.parseInt(oversParts[1]) : 0;

			    int totalBalls = (overs * 6) + balls;

			    result = match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + " NEED " + 
		                CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + totalBalls + " BALLS (" + 
		                match.getSetup().getTargetType().toUpperCase() + ")";
			}
			if(result.contains(match.getMatch().getInning().get(0).getBatting_team().getTeamName4())) {
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
						+ (match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
							match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
						+ (match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
							match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
			}
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+result+ ";");

		}
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 159.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		this.status = CricketUtil.SUCCESSFUL;
		
}
	private void populateResult(PrintWriter print_writer, String string, MatchAllData match, String broadcaster, String type) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Target's inning is null";
		} else {
			if(type.equalsIgnoreCase("BUG-RESULT")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "+
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.SHORT, "","",true).getTargetOrResult().toUpperCase()+ ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.SHORT, "","",true).getTargetOrResult().toUpperCase()+ ";");
				if(CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.SHORT, "","",true)
						.getTargetOrResult().toUpperCase().contains(match.getMatch().getInning().get(0).getBatting_team().getTeamName4())) {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ (match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
								match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path
							+ (match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase().equals("GS") ? "GSA" : 
								match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase()) + CricketUtil.PNG_EXTENSION + ";");
				}
			}
		}
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 179.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		this.status = CricketUtil.SUCCESSFUL;
		
	}
	private void populateBugTarget(PrintWriter print_writer, String string, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo "+
//						CricketFunctions.GetTargetData(match).getTargetRuns()+ ";");
				String result = "";

				if (match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
				    // Get the overs and balls
				    String[] oversParts = CricketFunctions.GetTargetData(match).getTargetOvers().split("\\.");
				    int overs = Integer.parseInt(oversParts[0]);
				    int balls = (oversParts.length > 1) ? Integer.parseInt(oversParts[1]) : 0;

				    int totalBalls = (overs * 6) + balls;

				    if (CricketFunctions.GetTargetData(match).getTargetOvers().equalsIgnoreCase("1")) {
				        result = match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + " NEED " + 
				                CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + totalBalls + " BALLS";
				    } else {
				    	result = match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + " NEED " + 
			                    CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + totalBalls + " BALLS";
				    }
				} else {
				    String[] oversParts = CricketFunctions.GetTargetData(match).getTargetOvers().split("\\.");
				    int overs = Integer.parseInt(oversParts[0]);
				    int balls = (oversParts.length > 1) ? Integer.parseInt(oversParts[1]) : 0;

				    int totalBalls = (overs * 6) + balls;

				    result = match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + " NEED " + 
			                CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS TO WIN FROM " + totalBalls + " BALLS (" + 
			                match.getSetup().getTargetType().toUpperCase() + ")";
				}
				if(result.contains(match.getMatch().getInning().get(0).getBatting_team().getTeamName4())) {
					
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
	
	public void populateBugPowerPLay(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "SPL":
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
					if (whichInning == 1) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamName1() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
								+ match.getMatch().getInning().get(0).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
								+ match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
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
	public void populateBugDismissal(PrintWriter print_writer, String viz_scene, int whichInning, String statsType,
			int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "SPL":
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

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
													+ bc.getPlayer().getTicker_name().toUpperCase() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
													+ (bc.getHowOutText()== null ? "" : bc.getHowOutText()) + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
													+ bc.getRuns()+(bc.getStatus().equalsIgnoreCase("NOT OUT")?"* ":"") +" ("+bc.getBalls()+")"+ " ;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C "
													+ "" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
											+ inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
								}
							}
							break;
						}
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
	public void populateBugsDB(PrintWriter print_writer, String viz_scene, EverestBugs bug, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 ;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A ;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B ;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C ;");
				
				if(bug.getSponsor()!=null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ bug.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ "TLogo"+ CricketUtil.PNG_EXTENSION + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 ;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A ;");
				if (bug.getText1() != null && bug.getText2() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ bug.getText1().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
							+ bug.getText2().toUpperCase() + ";");
				} else if (bug.getText1() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ bug.getText1().toUpperCase() + ";");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ (bug.getText2()!=null ? bug.getText2().toUpperCase() :"")+ ";");
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
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				if(ns.getSponsor() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
						 + ns.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
				}
				if (ns.getFirstname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
							+ ns.getSurname() + ";");
				} else if (ns.getSurname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
							+ ns.getFirstname() + ";");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
							+ ns.getFirstname() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
							+ns.getSurname() + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
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
	private void populateToss(PrintWriter print_writer, String string, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + " WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + " WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + "" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + "" + ";");	
				
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
	
	public void populateBug(PrintWriter print_writer, String viz_scene, int whichInning, String statsType, int playerId,
			MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				System.out.println(statsType);
				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch (statsType.toUpperCase()) {
						case CricketUtil.BATSMAN:
							for (BattingCard bc : inn.getBattingCard()) {
								if (bc.getPlayerId() == playerId) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
													+ bc.getPlayer().getTicker_name().toUpperCase() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
													+ "FOURS: " + bc.getFours() + "       SIXES: " + bc.getSixes() + "  ;");

									if (bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
														+ bc.getRuns() + "*" +" ("+bc.getBalls()+")"+ ";");
									} else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
														+ bc.getRuns() +" ("+bc.getBalls()+")"+ ";");
									}
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C  "
													+ "" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
											+ inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
								}
							}
							break;
						}
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

	public void populateBugBowler(PrintWriter print_writer, String viz_scene, int whichInning, String statsType,
			int playerId, MatchAllData match, String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "SPL":
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
													+ "ECONOMY: " + boc.getEconomyRate() + ";");

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B  "
													+ boc.getWickets() + slashOrDash + boc.getRuns()+" ("+
													CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ");");

									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C  "
													+ "" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
											+ inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
								}
							}
							break;
						}

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
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				List<Partnership> Partnership ;
				for (Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						Partnership = CricketFunctions.ConcussedPartnership(match.getMatch(), inn.getInningNumber());
						String Left_Batsman = "", Right_Batsman = "";

						for (BattingCard hs : inn.getBattingCard()) {
							if (hs.getPlayerId() == Partnership.get(Partnership.size() - 1).getFirstBatterNo()) {
								Left_Batsman = hs.getPlayer().getTicker_name();
							}
							if (hs.getPlayerId() == Partnership.get(Partnership.size() - 1).getSecondBatterNo()) {
								Right_Batsman = hs.getPlayer().getTicker_name();
							}
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + " & " + Right_Batsman + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
								+ inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C ;");
						
						if(Partnership.get(Partnership.size() - 1).getPartnershipNumber()==0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A PARTNERSHIP" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B ;");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
									+ CricketFunctions.ordinal(Partnership.get(Partnership.size() - 1).getPartnershipNumber()) +" WICKET PARTNERSHIP" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
									+ Partnership.get(Partnership.size() - 1).getTotalRuns() +((Partnership.size() - 1)==Partnership.size()-1? "*":"")+ " ("
									+ Partnership.get(Partnership.size() - 1).getTotalBalls() + ")" + ";");
						}
					}
				}
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
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership inning is null";
			} else {
				List<Partnership> Partnership ;
				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichinning) {
						Partnership = CricketFunctions.ConcussedPartnership(match.getMatch(), inn.getInningNumber());
						String Left_Batsman = "", Right_Batsman = "";

						for (BattingCard hs : inn.getBattingCard()) {
							if (hs.getPlayerId() == Partnership.get(partnership - 1).getFirstBatterNo()) {
								Left_Batsman = hs.getPlayer().getTicker_name();
							}
							if (hs.getPlayerId() == Partnership.get(partnership - 1).getSecondBatterNo()) {
								Right_Batsman = hs.getPlayer().getTicker_name();
							}
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + Left_Batsman + " & " + Right_Batsman + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
								+ inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C ;");
						
						if(Partnership.get(partnership - 1).getPartnershipNumber()==0) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A PARTNERSHIP" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B ;");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A "
									+ CricketFunctions.ordinal(Partnership.get(partnership - 1).getPartnershipNumber()) +" WICKET PARTNERSHIP" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
									+ Partnership.get(partnership - 1).getTotalRuns() +((partnership - 1)==Partnership.size()-1? "*":"")+ " ("
									+ Partnership.get(partnership - 1).getTotalBalls() + ")" + ";");
						}
					}
				}
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
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "HIGHLIGHTS" + ";");

				if (whichInning == 1) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ match.getMatch().getInning().get(0).getBatting_team().getTeamName1().toUpperCase() + ";");
					if (match.getMatch().getInning().get(0).getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
								+ match.getMatch().getInning().get(0).getTotalRuns()+ " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(),
										match.getMatch().getInning().get(0).getTotalBalls()) + ")" + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
								+ match.getMatch().getInning().get(0).getTotalRuns() + "-"
								+ match.getMatch().getInning().get(0).getTotalWickets()  + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(0).getTotalOvers(),
										match.getMatch().getInning().get(0).getTotalBalls()) + ")"+ ";");
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C  ;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ match.getMatch().getInning().get(0).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 "
							+ match.getMatch().getInning().get(1).getBatting_team().getTeamName1().toUpperCase() + ";");
					if (match.getMatch().getInning().get(1).getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
								+ match.getMatch().getInning().get(1).getTotalRuns() +" (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(),
										match.getMatch().getInning().get(1).getTotalBalls()) + ")"+ ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B "
								+ match.getMatch().getInning().get(1).getTotalRuns() + "-"
								+ match.getMatch().getInning().get(1).getTotalWickets() + " (" + CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(),
										match.getMatch().getInning().get(1).getTotalBalls()) + ")" + ";");
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C  ;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				}

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
	private void populateNameSuperSingle(PrintWriter print_writer,NameSuper ns, MatchAllData match,
			String session_selected_broadcaster) throws InterruptedException {
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {

				if(ns.getSponsor() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ "TLogo" + CricketUtil.PNG_EXTENSION + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ ns.getSponsor() + CricketUtil.PNG_EXTENSION + ";");
				}
				if (ns.getFirstname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
							+ ns.getSurname() + ";");
				} else if (ns.getSurname() == null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
							+ ns.getFirstname() + ";");
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
							+ ns.getFirstname() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
							+ns.getSurname() + ";");
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 " + ";");
				
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
		case "SPL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				String Home_or_Away = "";

				if (TeamId == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ match.getSetup().getHomeTeam().getTeamBadge()+ CricketUtil.PNG_EXTENSION + ";");
					
					Home_or_Away = match.getSetup().getHomeTeam().getTeamName1();
					
					for (Player hs : match.getSetup().getHomeSquad()) {
						if (playerId == hs.getPlayerId()) {
							if (hs.getFirstname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
										+ hs.getSurname() + ";");
							} else if (hs.getSurname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
										+ hs.getFirstname() + ";");
							} else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
										+ hs.getFirstname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
										+hs.getSurname() + ";");
							}
						}
						if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + ";");
						}
					}
				} else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path
							+ match.getSetup().getAwayTeam().getTeamBadge()+ CricketUtil.PNG_EXTENSION + ";");
					
					Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
					for (Player as : match.getSetup().getAwaySquad()) {
						if (playerId == as.getPlayerId()) {
							if (as.getFirstname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
										+ as.getSurname() + ";");
							} else if (as.getSurname() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
										+ as.getFirstname() + ";");
							} else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName "
										+ as.getFirstname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName "
										+as.getSurname() + ";");
							}
						}
						if (captainWicketKeeper.toUpperCase().equalsIgnoreCase(CricketUtil.PLAYER)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + ";");
						}
					}
				}
				
				switch (captainWicketKeeper.toUpperCase()) {
				case CricketUtil.CAPTAIN:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
							+ captainWicketKeeper.toUpperCase() + " , " + Home_or_Away + ";");
					break;
				case "PLAYER OF THE MATCH":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
							+ captainWicketKeeper.toUpperCase() + ";");
					break;
				case CricketUtil.WICKET_KEEPER:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
							+ "WICKET KEEPER" + " , " + Home_or_Away + ";");
					break;
				case "CAPTAIN-WICKETKEEPER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 "
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
		case "MULTI-PARTNERSHIP": case "NAMESUPER-PLAYER": case "BUG-DISMISSAL": case "BUG": case "BUG-BOWLER": case "BUG-DB": case "NAMESUPER": 
		case "BUG_HIGHLIGHT": case "BUG_PARTNERSHIP": case "BUG_POWERPLAY":case "NAMESUPER_SINGLE":
			processAnimation(print_writer, "Out", "START", broadcaster);
			TimeUnit.SECONDS.sleep(1);
			break;
		}	
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "SPL":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			
			break;
		}
		
	}
	public static String getPowerPlayScore(MatchAllData match ,Inning inning, int inn_num, List<Event> events) {
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
	public static String RowNumber(int i) {
	    switch (i) {
	        case 1:
	            return "First";
	        case 2:
	            return "Second";
	        case 3:
	            return "Third";
	        case 4:
	            return "Fourth";
	        case 5:
	            return "Fifth";
	        default:
	            return "Invalid";
	    }
	}
}