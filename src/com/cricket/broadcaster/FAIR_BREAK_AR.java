package com.cricket.broadcaster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.service.CricketService;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Configuration;
import com.cricket.model.Fixture;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FAIR_BREAK_AR extends Scene{

	private String status;
	private String slashOrDash = "-",category = "";
	public Infobar infobar = new Infobar();
	public String session_selected_broadcaster = "FAIR_BREAK_AR";
	public String which_graphics_onscreen = "";
	private String logo_path2 = "C:\\Images\\MPL\\Logos\\";
	//private String logo_path = "C:\\Images\\NPL\\Logos\\Outline\\";
	private String logo_path = "C:\\Images\\MPL\\Logos\\";
	private String photo_path = "C:\\Images\\MPL\\Photos\\";
	
	public Inning inning;
	public BattingCard battingCard;
	public List<BattingCard> battingCardList = new ArrayList<BattingCard>();
	
	public FAIR_BREAK_AR() {
		super();
	}

	public FAIR_BREAK_AR(String scene_path, String which_Layer) {
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
		switch (which_graphics_onscreen.toUpperCase()) {
		case "THISOVER_AR":
			populateThisOver(true,print_writer,match,session_selected_broadcaster);
			break;
		case "EQUATION_AR":
			populateEquationAR(true,print_writer, match, session_selected_broadcaster);
			break;
		case "COMPARISON_AR":
			populateComparisonAR(true,print_writer, match, session_selected_broadcaster);
			break;
		case "BOUNDARIES_AR":
			populateBoundariesAR(true,print_writer, match, session_selected_broadcaster);
			break;
		case "PROJECTED_AR":
			populateProjectedAR(true,print_writer, match, session_selected_broadcaster);
			break;	
		}
		//CricketFunctions.getInteractive(match);
		return infobar;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics,Configuration config) throws Exception{
		switch (whatToProcess.toUpperCase()) {
		
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
			}
		case "POPULATE-BOUNDARIES_AR": case "POPULATE-COMPARISON_AR": case "POPULATE-TARGET_AR": case "POPULATE-MATCHID_AR": case "POPULATE-PROJECTED_AR":
		case "POPULATE-FREE_TEXT_AR": case "POPULATE-EQUATION_AR": case "POPULATE-MATCH_ANIMATION_AR": case "POPULATE-THISOVER_AR": case "POPULATE-MATCH_PROMO":
		case "POPULATE-TEAMCELEB_AR": case "POPULATE-PLAYERCELEB": case "POPULATE-MATCH_PROMO_ANIMATION": case "POPULATE-L3-BATMILEDETAILS": case "POPULATE-L3-BOWLERDETAILS":
		case "POPULATE-COUNT_AR": case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-THISPART_AR": case "POPULATE-NEXT_AR": case "POPULATE-L3-OPENER":
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				switch(whatToProcess.toUpperCase()) {
				case"POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-BOTTOM":
					break;
				case "POPULATE-L3-INFOBAR":
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,session_selected_broadcaster);
					break;
				default:
					scenes.get(1).setWhich_layer(String.valueOf("1"));
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,session_selected_broadcaster);
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-L3-OPENER":
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateOpener(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),Integer.valueOf(valueToProcess.split(",")[3]),
							cricketService.getAllPlayer(),match , session_selected_broadcaster);
					break;
				case "POPULATE-L3-BATMILEDETAILS":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateBatMile(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),
							cricketService.getAllPlayer(),match , session_selected_broadcaster);
					break;
				case "POPULATE-L3-BOWLERDETAILS":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateBallMile(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),
							cricketService.getAllPlayer(),match , session_selected_broadcaster);
					break;	
				case "POPULATE-MATCH_PROMO":
					populateMatchPromo(false,print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(),match , session_selected_broadcaster);
					break;
				case "POPULATE-THISOVER_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateThisOver(false,print_writer,match,session_selected_broadcaster);
					break;
				case "POPULATE-NEXT_AR":
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateNextToBat(false,print_writer,match,session_selected_broadcaster);
					break;
				case "POPULATE-MATCH_ANIMATION_AR":
					populateMatchIdAnimationAR(false,print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCH_PROMO_ANIMATION":
					populateMatchPromoAnimationAR(false,print_writer,valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(), match, session_selected_broadcaster);
					break;
				case "POPULATE-EQUATION_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateEquationAR(false,print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-THISPART_AR":
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateThispart(false,print_writer,match,session_selected_broadcaster);
					break;
				case "POPULATE-FF-POSITION_LANDMARK":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populatePlayerInAt(print_writer, match,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(),cricketService.getTeams(), 
							session_selected_broadcaster,config);
					break;
				case "POPULATE-PLAYERCELEB":
					populatePlayerCelebAR(print_writer, match,valueToProcess.split(",")[1],valueToProcess.split(",")[3],valueToProcess.split(",")[2],cricketService.getAllPlayer(),cricketService.getTeams(), 
							session_selected_broadcaster);
					break;
				case "POPULATE-TEAMCELEB_AR":
					System.out.println(valueToProcess);
					populateTeamCelebAR(print_writer, match,valueToProcess.split(",")[1],valueToProcess.split(",")[2],cricketService.getTeams(), session_selected_broadcaster);
					break;
				case "POPULATE-FREE_TEXT_AR":
					populateFreeTextAR(print_writer, match,valueToProcess.split(",")[1], session_selected_broadcaster);
					break;
				case "POPULATE-BOUNDARIES_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateBoundariesAR(false,print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-COMPARISON_AR":
					populateComparisonAR(false,print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGET_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateTargetAR(print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCHID_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateMatchIdAR(false,print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-COUNT_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateCountAR(false,print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-PROJECTED_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateProjectedAR(false,print_writer, match, session_selected_broadcaster);
					break;	
				}
			}
		
		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BOUNDARIES_AR": case "ANIMATE-IN-COMPARISON_AR": case "ANIMATE-IN-TARGET_AR": case "ANIMATE-IN-MATCHID_AR":
		case "ANIMATE-IN-PROJECTED_AR": case "ANIMATE-IN-FREETEXT_AR": case "ANIMATE-IN-EQUATION_AR": case "ANIMATE-IN-MATCH_ANIMATION_AR": case "ANIMATE-IN-THISOVER_AR":
		case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMCELEB_AR": case "ANIMATE-IN-PLAYERCELEB_AR": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-BATMILEDETAILS":
		case "ANIMATE-IN-COUNT_AR": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-THISPART_AR": case "ANIMATE-IN-NEXT_AR": case "ANIMATE-IN-OPENER":
		case "CONTINUE-COUNT_AR":	
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-THISSERIES":
					if(infobar.isInfobar_on_screen() == true) {
						processAnimation(print_writer, "FF_In", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(200);
					}
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "CONTINUE-COUNT_AR":
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In COUNTINUE;");
					break;
				case "ANIMATE-IN-OPENER":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "OPENER";
					break;
				case "ANIMATE-IN-NEXT_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "NEXT_AR";
					break;
				case "ANIMATE-IN-THISPART_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "THISPART_AR";
					break;
				case "ANIMATE-IN-POSITION_LANDMARK":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "POSITION_LANDMARK";
					break;
				case "ANIMATE-IN-COUNT_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "COUNT_AR";
					break;
				case "ANIMATE-IN-BATMILEDETAILS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "BATMILEDETAILS";
					break;
				case "ANIMATE-IN-BOWLERDETAILS":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "BOWLERDETAILS";
					break;	
				case "ANIMATE-IN-PLAYERCELEB_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "PLAYERCELEB_AR";
					break;
				case "ANIMATE-IN-TEAMCELEB_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "TEAMCELEB_AR";
					break;
				case "ANIMATE-IN-THISOVER_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "THISOVER_AR";
					break;
				case "ANIMATE-IN-MATCH_ANIMATION_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCH_ANIMATION_AR";
					break;
				case "ANIMATE-IN-EQUATION_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "EQUATION_AR";
					break;
				case "ANIMATE-IN-FREETEXT_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "FREETEXT_AR";
					break;
				case "ANIMATE-IN-BOUNDARIES_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "BOUNDARIES_AR";
					break;
				case "ANIMATE-IN-COMPARISON_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "COMPARISON_AR";
					break;
				case "ANIMATE-IN-TARGET_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "TARGET_AR";
					break;
				case "ANIMATE-IN-MATCH_PROMO":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCHID_PROMO_AR";
					break;
				case "ANIMATE-IN-MATCHID_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCHID_AR";
					break;
				case "ANIMATE-IN-PROJECTED_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "PROJECTED_AR";
					break;	
				case "CLEAR-ALL":
					print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER3*EVEREST*SINGLE_SCENE CLEAR;");
					which_graphics_onscreen = "";
					break;
				case "ANIMATE-OUT":
					switch(which_graphics_onscreen) {
					case "INFOBAR":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,1);
						which_graphics_onscreen = "";
						infobar.setInfobar_on_screen(false);
						break;
					case "THISOVER_AR": case "TEAMCELEB_AR": case "MATCH_ANIMATION_AR": case "EQUATION_AR":
					case "FREETEXT_AR": case "PROJECTED_AR": case "MATCHID_PROMO_AR": case "MATCHID_AR":
					case "TARGET_AR": case "COMPARISON_AR": case "BOUNDARIES_AR": case "PLAYERCELEB_AR":
					case "BATMILEDETAILS": case "BOWLERDETAILS": case "COUNT_AR": case "POSITION_LANDMARK":
					case "THISPART_AR": case "NEXT_AR": case "OPENER":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,1);
						which_graphics_onscreen = "";
						break;
					}
					break;
				}
			}
		}
		return null;
}

	
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster,int which_layer)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "FAIR_BREAK_AR":
			switch(which_layer) {
			case 1:
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
				
			case 2:
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				//print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");	
				break;
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
			
			if(fix.get(match_number - 1).getCategory().equalsIgnoreCase("MEN")) {
				category = "M";
			}else {
				category = "W";
			}
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
			
			
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path2 + 
							TM.getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
//							TM.getTeamName1() + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path2 + 
							TM.getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
//							TM.getTeamName1() + ";");
					
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo2 " + logo_path2 + 
							TM.getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
//							TM.getTeamName1() + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo2 " + logo_path2 + 
							TM.getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
//							TM.getTeamName1() + ";");
				}
			}
			
			if(match_number < 10) {
				match_name = "MATCH " + match_number;
			}else {
				match_name = fix.get(match_number - 1).getMatchfilename().toUpperCase().substring(1);
			}
			
			if(fix.get(match_number - 1).getCategory().equalsIgnoreCase("MEN")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "ADANI MPL 2025 - " + match_name + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "ADANI MPL 2025 - " + match_name + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "ADANI WMPL 2025 - " + match_name + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "ADANI WMPL 2025 - " + match_name + ";");
			}
			
			
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "TOMORROW AT " + fix.get(match_number - 1).getLocalTime() + ",PUNE" + ";");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "TOMORROW AT " + fix.get(match_number - 1).getLocalTime() + ",PUNE" + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "UP NEXT - LIVE FROM MCA INTERNATIONAL STADIUM, PUNE;");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "UP NEXT - LIVE FROM MCA INTERNATIONAL STADIUM, PUNE;");
			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;
			}	
		}
	}
	
	public void populateBatMile(boolean is_this_updating, PrintWriter print_writer, int inning , int playerId ,List<Player> player,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchPromo's inning is null";
		} else {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + category + "\\" + 
					player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + 
					player.get(playerId - 1).getFull_name() + ";");
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + category + "\\" + 
					player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + 
					player.get(playerId - 1).getFull_name() + ";");
			
			for(BattingCard bc : match.getMatch().getInning().get(inning - 1).getBattingCard()) {
				if(bc.getPlayerId() == playerId) {
					
					if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs " + 
								bc.getRuns() + "*" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + 
								bc.getRuns() + "*" + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs " + 
								bc.getRuns() + "*" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + 
								bc.getRuns() + "*" + ";");
					}else {
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs " + 
								bc.getRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + 
								bc.getRuns() + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs " + 
								bc.getRuns() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + 
								bc.getRuns() + ";");
					}
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls " + 
							bc.getBalls() + " BALLS" + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls " + 
							bc.getBalls() + " BALLS" + ";");
				}
			}
			
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;
			}	
		}
	}
	
	public void populateBallMile(boolean is_this_updating, PrintWriter print_writer, int inning , int playerId ,List<Player> player,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchPromo's inning is null";
		} else {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
					match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
					match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge() + category + "\\" + 
					player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + 
					player.get(playerId - 1).getFull_name() + ";");
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
					match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
					match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge() + category + "\\" + 
					player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + 
					player.get(playerId - 1).getFull_name() + ";");
			
			for(BowlingCard bc : match.getMatch().getInning().get(inning - 1).getBowlingCard()) {
				if(bc.getPlayerId() == playerId) {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs " + 
							bc.getWickets() + "-" + bc.getRuns() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + 
							bc.getWickets() + "-" + bc.getRuns() + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls " + 
							CricketFunctions.OverBalls(bc.getOvers(), bc.getBalls()) + " OVERS" + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs " + 
							bc.getWickets() + "-" + bc.getRuns() + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + 
							bc.getWickets() + "-" + bc.getRuns() + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls " + 
							CricketFunctions.OverBalls(bc.getOvers(), bc.getBalls()) + " OVERS" + ";");
				}
			}
			
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;
			}	
		}
	}
	
	public void populateBoundariesAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + inn.getTotalFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + inn.getTotalSixes() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + inn.getBatting_team().getTeamBadge().toUpperCase() + category  + 
								CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + inn.getTotalFours() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + inn.getTotalSixes() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + inn.getBatting_team().getTeamBadge().toUpperCase() + category  + 
								CricketUtil.PNG_EXTENSION + ";");
					}
				}
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateComparisonAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getSetup().getMatchIdent() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getSetup().getMatchIdent() + ";");

						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase() + ".png" +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + ".png" +";");

						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreA " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreB " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + ";");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + inn.getBowling_team().getTeamName3().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamB " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
						
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + match.getMatch().getInning().get(0).getBatting_team().getTeamBadge().toUpperCase() + ".png" +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + ".png" +";");

						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreA " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + ";");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreB " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + ";");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + inn.getBowling_team().getTeamName3().toUpperCase() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamB " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
						
					}
				}
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateMatchPromoAnimationAR(boolean is_this_updating, PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,
			MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				String match_name="";
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "FROM " + 
						match.getSetup().getVenueName().toUpperCase() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "FROM " + 
						match.getSetup().getVenueName().toUpperCase() + ";");
				
				for(Team TM : team) {
					if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path2 + 
								TM.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
								TM.getTeamName1() + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path2 + 
								TM.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
								TM.getTeamName1() + ";");
						
					}
					if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path2 + 
								TM.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
								TM.getTeamName1() + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path2 + 
								TM.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
								TM.getTeamName1() + ";");
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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + "TOMORROW - " + match_name + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + "TOMORROW - " + match_name + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + "UP NEXT - " + match_name + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + "UP NEXT - " + match_name + ";");
				}
				
				
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateMatchIdAnimationAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path2 + 
						match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path2 + 
						match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
						match.getSetup().getHomeTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path2 + 
						match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path2 + 
						match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
						match.getSetup().getHomeTeam().getTeamName1() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateMatchIdAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path2 + 
						match.getSetup().getHomeTeam().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo2 " + logo_path2 + 
						match.getSetup().getAwayTeam().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
//						match.getSetup().getHomeTeam().getTeamName1() + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
//						match.getSetup().getAwayTeam().getTeamName1() + ";");
				if(category.equalsIgnoreCase("M")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header ADANI MPL 2025 - " + match.getSetup().getMatchIdent() + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header ADANI MPL 2025 - " + match.getSetup().getMatchIdent() + ";");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header ADANI WMPL 2025 - " + match.getSetup().getMatchIdent() + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header ADANI WMPL 2025 - " + match.getSetup().getMatchIdent() + ";");
				}
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "LIVE FROM MCA INTERNATIONAL STADIUM, PUNE" + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path2 + 
						match.getSetup().getHomeTeam().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo2 " + logo_path2 + 
						match.getSetup().getAwayTeam().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
//						match.getSetup().getHomeTeam().getTeamName1() + ";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
//						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
					print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
					print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
					TimeUnit.SECONDS.sleep(1);
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populatePlayerCelebAR(PrintWriter print_writer,MatchAllData match,String data ,String data2,String data3,List<Player> plyr,List<Team> tm,
			String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "FAIR_BREAK_AR":
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$RestData*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Select$Logos*CONTAINER SET ACTIVE 1;");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + data2 +";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + data2 +";");
			
			for(Team tem : tm) {
				if(tem.getTeamId() == Integer.valueOf(data3)) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamlogo " + logo_path2 + tem.getTeamBadge() + ".png" +";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamlogo " + logo_path2 + tem.getTeamBadge() + ".png" +";");
				}
			}
			
			for(Player plr : plyr) {
				if(plr.getPlayerId() == Integer.valueOf(data)) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName " + plr.getTicker_name() +";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName " + plr.getTicker_name() +";");
				}
			}
			
		    print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populatePlayerInAt(PrintWriter print_writer,MatchAllData match,int Inning ,int playerId,List<Player> plyr,List<Team> tm,
			String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "FAIR_BREAK_AR":
			
			inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Inning).findAny().orElse(null);
			
			battingCardList = inning.getBattingCard();
			
			int row_id = 0;
			
			int inAtPosition = 0;
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
					match.getMatch().getInning().get(Inning - 1).getBatting_team().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
					match.getMatch().getInning().get(Inning - 1).getBatting_team().getTeamBadge() + category + "\\" + 
					plyr.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + 
					plyr.get(playerId - 1).getFull_name() + ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
					match.getMatch().getInning().get(Inning - 1).getBatting_team().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
					match.getMatch().getInning().get(Inning - 1).getBatting_team().getTeamBadge() + category + "\\" + 
					plyr.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + 
					plyr.get(playerId - 1).getFull_name() + ";");
			
			Collections.sort(inning.getBattingCard());
			for (BattingCard bc : inning.getBattingCard()) {
				row_id = row_id + 1;
				
				if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
					inAtPosition++;
					if(bc.getHowOut() != null) {
						if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
							if(bc.getPlayerId() == playerId) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
								
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
							}
						}
					}else {
						if(bc.getPlayerId() == playerId) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
						}
					}
				}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
					inAtPosition++;
					if(bc.getPlayerId() == playerId) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
					}
				}else {
					
					if(bc.getHowOut() != null) {
						if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
							if(bc.getPlayerId() == playerId) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + bc.getBatterPosition() + ";");
								
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + bc.getBatterPosition() + ";");
							}
						}else {
							inAtPosition++;
							if(bc.getPlayerId() == playerId) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
								
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
							}
						}
					}
				}
			}
			
			
		    print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateTeamCelebAR(PrintWriter print_writer,MatchAllData match,String data ,String data2,List<Team> tm,String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "FAIR_BREAK_AR":
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$RestData*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Select$Logos*CONTAINER SET ACTIVE 1;");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + data2 +";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + data2 +";");
			
			for(Team tem : tm) {
				if(tem.getTeamId() == Integer.valueOf(data2)) {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName " + tem.getTeamName1() +";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName " + tem.getTeamName1() +";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamlogo " + logo_path2 + tem.getTeamBadge() + ".png" +";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamlogo " + logo_path2 + tem.getTeamBadge() + ".png" +";");
				}
			}
			
		    print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateFreeTextAR(PrintWriter print_writer,MatchAllData match,String data,String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "FAIR_BREAK_AR":
			String text1_to_return = "",text2_to_return = "";
			
			switch (data) {
			case "NO_LOGO":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 0;");
				break;

			default:
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
						data.toUpperCase() + ".png" +";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
						data.toUpperCase() + ".png" +";");
				break;
			}
			
			BufferedReader br = new BufferedReader(new FileReader(CricketUtil.CRICKET_DIRECTORY + CricketUtil.AR_FREE_TXT));
			
			int lineIndex1 = 1,lineIndex2 = 1;
		    boolean found1 = false,found2 = false;
		    while( (text1_to_return = br.readLine()) != null) {
		        if(lineIndex1 == 1) {
		        	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + text1_to_return + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + text1_to_return + ";");
		             found1 = true;
		             break;
		        }
		        lineIndex1++;
		    }
		    if(found1 == false) {
		    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "" + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "" + ";");
		    	System.out.println("Line Not There");
		    }
		    
		    while( (text2_to_return = br.readLine()) != null) {
		        if(lineIndex2 == 1) {
		        	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader22 " + text2_to_return + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader2 " + text2_to_return + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader22 " + text2_to_return + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader2 " + text2_to_return + ";");
		             found2 = true;
		             break;
		        }
		        lineIndex2++;
		    }
		    if(found2 == false) {
		    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader22 " + "" + ";");
		    	print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader22 " + "" + ";");
		    	
		    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader2 " + "" + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader2 " + "" + ";");
		    	System.out.println("Line Not There");
		    }
//			File free_file = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.AR_FREE_TXT);
//			if(free_file.exists() == true) {
//				text1_to_return = Files.newBufferedReader(Paths.get(CricketUtil.CRICKET_DIRECTORY 
//						+ CricketUtil.AR_FREE_TXT), StandardCharsets.UTF_8).lines().limit(1).collect(Collectors.toList()).get(0);
////				if(Files.newBufferedReader(Paths.get(CricketUtil.CRICKET_DIRECTORY 
////						+ CricketUtil.AR_FREE_TXT), StandardCharsets.UTF_8).lines().skip(1).limit(1).collect(Collectors.toList()).get(0).trim() != "") {
////					text2_to_return = Files.newBufferedReader(Paths.get(CricketUtil.CRICKET_DIRECTORY 
////							+ CricketUtil.AR_FREE_TXT), StandardCharsets.UTF_8).lines().skip(1).limit(1).collect(Collectors.toList()).get(0);
////				}
//			}
		    //print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
		    
		    print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateProjectedAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
								inn.getBatting_team().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls3 " + "@"+ proj_score_rate[0] +" (CRR)" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue3 " + proj_score_rate[1] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls2 " + "@" + proj_score_rate[2] +" RPO"+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue2 " + proj_score_rate[3] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls1 " + "@" + proj_score_rate[4] +" RPO" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue1 " + proj_score_rate[5] + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead04 " + "@" + proj_score_rate[6] +" RPO" + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue04 " + proj_score_rate[7] + ";");
						
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
								inn.getBatting_team().getTeamBadge() + category  + CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls3 " + "@"+ proj_score_rate[0] +" (CRR)" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue3 " + proj_score_rate[1] + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls2 " + "@" + proj_score_rate[2] +" RPO"+ ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue2 " + proj_score_rate[3] + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls1 " + "@" + proj_score_rate[4] +" RPO" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue1 " + proj_score_rate[5] + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead04 " + "@" + proj_score_rate[6] +" RPO" + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue04 " + proj_score_rate[7] + ";");
					}
				}
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateTargetAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + category + ".png" +";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + category + ".png" +";");

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
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
						CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2,match) + ";");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
						CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2,match) + ";");
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHead " + "REQUIRED RUN RATE " + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2,match) + ";");
//				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHead " + "REQUIRED RUN RATE " + ";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + CricketFunctions.generateRunRate
//						(requiredRuns, 0, requiredBalls, 2,match) + ";");
				
				if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
					if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 +";");
	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALLS;");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALLS;");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 +";");
					}else {
						if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getTargetOvers() +";");
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getTargetOvers() + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + ";");
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALLS;");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALLS;");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSctxt_NeedValueore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvtxt_FromValueers " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + ";");
						}
					}
				}else {
					if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getTargetOvers() +";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
								CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2,match) + " ("+ match.getSetup().getTargetType().toUpperCase() +")" + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
								CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2,match) + " ("+ match.getSetup().getTargetType().toUpperCase() +")" + ";");
						
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getTargetOvers()  +";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 +";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALLS;");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALLS;");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
								CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2,match) + " ("+ match.getSetup().getTargetType().toUpperCase() +")" + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
								CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2,match) + " ("+ match.getSetup().getTargetType().toUpperCase() +")" + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 +";");
					}
				}
				
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateCountAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path2 + 
						match.getSetup().getHomeTeam().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo2 " + logo_path2 + 
						match.getSetup().getAwayTeam().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
//						match.getSetup().getHomeTeam().getTeamName1() + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
//						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path2 + 
						match.getSetup().getHomeTeam().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo2 " + logo_path2 + 
						match.getSetup().getAwayTeam().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
//						match.getSetup().getHomeTeam().getTeamName1() + ";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
//						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
					print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
					print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
					TimeUnit.SECONDS.sleep(1);
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	public void populateThisOver(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				
				String overData = "";
				for(int i=1;i<=6;i++) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ i + " ;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ i + " ;");
				}
				
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "THIS OVER" + ";");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + inn.getBowling_team().getTeamBadge() + category  + 
								CricketUtil.PNG_EXTENSION + ";");
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") || boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
								//System.out.println("SIZE :" + CricketFunctions.getEventsText(CricketUtil.OVER,",", match.getEvents(),0).length());
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info "
										+ boc.getPlayer().getFull_name() + ";");
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + inn.getBowling_team().getTeamBadge() + category + "\\"
										+ boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								
								String arr[] = CricketFunctions.getEventsText(CricketUtil.OVER,boc.getPlayerId() ,",", match.getEventFile().getEvents(),0).split(",");
								for(int i=0;i < CricketFunctions.getEventsText(CricketUtil.OVER,boc.getPlayerId(),",", match.getEventFile().getEvents(),0).split(",").length;i++) {
//										print_writer.println("LAYER2*EVEREST*TREEVIEW*Main$BigBandGrp$Data$group$"+ (i+2) +"*CONTAINER SET ACTIVE 1;");
									switch (arr[i]) {
									case "4BOUNDARY":
										
										overData = overData + " " + 4;
//										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (i+1)+ " 4;");
//										
//										print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (i+1)+ " 4;");
//											System.out.println("Over : " + (i+1) + " - " + arr[i]);
										break;
									case "6BOUNDARY":
										
										overData = overData + " " + 6;
										
//										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (i+1)+ " 6;");
//										
//										print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (i+1)+ " 6;");
//											System.out.println("Over : " + (i+1) + " - " + arr[i]);
										break;	

									default:
										
										overData = overData + " " + arr[i];
										
//										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (i+1)+ " " + arr[i] + ";");
//										
//										print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (i+1)+ " " + arr[i] + ";");
//											System.out.println("Over : " + (i+1) + " - " + arr[i]);
										break;
									}
								}
							}
							
							if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "THIS OVER" + ";");
							}else if(boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "LAST OVER" + ";");
							}
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + overData + ";");
					}
				}
				
				if(is_this_updating == false) {
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
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateNextToBat(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				int row_id = 0;
				
				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + inn.getBatting_team().getTeamBadge() + category
								+ CricketUtil.PNG_EXTENSION + ";");
						
						for (int b = 1; b <= inn.getBattingCard().size(); b++) {
							if (inn.getBattingCard().get(b - 1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								row_id = row_id + 1;
								if (row_id <= 3) {
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player" + row_id + " " + photo_path + 
											inn.getBatting_team().getTeamBadge() + category + "\\" + 
											inn.getBattingCard().get(b - 1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value0" + row_id + " " + b + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info" + row_id + " " + 
											inn.getBattingCard().get(b - 1).getPlayer().getTicker_name() + ";");
									
								}
							}
						}

					}
				}
				
				if(is_this_updating == false) {
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
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateThispart(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws Exception 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
			
				List<Partnership> Partnership ;
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls1 PARTNERSHIP;");
				
				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + inn.getBatting_team().getTeamBadge() + category  + 
								CricketUtil.PNG_EXTENSION + ";");
						
						Partnership = CricketFunctions.ConcussedPartnership(match.getMatch(), inn.getInningNumber());
						
						String Left_Batsman = "", Right_Batsman = "";

						for (BattingCard hs : inn.getBattingCard()) {
							if (hs.getPlayerId() == Partnership.get(Partnership.size() - 1).getFirstBatterNo()) {
								Left_Batsman = hs.getPlayer().getTicker_name();
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player1 " + photo_path + inn.getBatting_team().getTeamBadge() + category + "\\"  + 
										hs.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
								
							}
							if (hs.getPlayerId() == Partnership.get(Partnership.size() - 1).getSecondBatterNo()) {
								Right_Batsman = hs.getPlayer().getTicker_name();
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player2 " + photo_path + inn.getBatting_team().getTeamBadge() + category + "\\"  + 
										hs.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");
							}
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs1 "
								+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "* (" + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue1 "
								+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "* (" + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info "
								+ Left_Batsman + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info2 "
								+ Right_Batsman + ";");
						
					}
				}
				
//				print_writer.println("LAYER2*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "THIS OVER" + ";");
				
				if(is_this_updating == false) {
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
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateEquationAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "FAIR_BREAK_AR":
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
								match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + category + ".png" +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
								match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() + category + ".png" +";");
						
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + "EQUATION" +";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + "EQUATION" +";");

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
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
								CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
								CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
						
						
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHead " + "REQUIRED RUN RATE " + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + 
//								CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
//						
//						
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHead " + "REQUIRED RUN RATE " + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + 
//								CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
						
						if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED;");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + match.getMatch().getMatchStatus().toUpperCase() + ";");
									}
								}
								
							}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
								
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED;");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + match.getMatch().getMatchStatus().toUpperCase() + ";");
									}
								}
							}
							
							else{
								if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
									
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
									
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
									
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
								}
								
							}
						}else {
							if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
								if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED;");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}
									
								}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
										|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
									
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED;");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}
								}
								
								else{
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
									
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");								
								}
							}
							else {
								if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED;");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}
								}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
										|| match.getMatch().getInning().get(1).getTotalOvers() >= Double.valueOf(match.getSetup().getTargetOvers())) {
									if(match.getMatch().getMatchStatus() != null) {
										if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info MATCH TIED;");
										}
										else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
										else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + match.getMatch().getMatchStatus().toUpperCase() + ";");
										}
									}
								}
								else{
									if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
										if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
												
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
													CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " (VJD)" + ";");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
													CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " (VJD)" + ";");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
																						
										}else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall()+ ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
													CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
													CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
													CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " (VJD)" + ";");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
													CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " (VJD)" + ";");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
										}
									}
									else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
													CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " (DLS)" + ";");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
													CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " (DLS)" + ";");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
											
										}else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
													CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
													CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
													CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " (DLS)" + ";");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
													CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " (DLS)" + ";");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
										}
									}
									else {
										if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls OVERS;");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
											
										}else {
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
											
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
													CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()+ ";");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls BALL" + 
													CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()+ ";");
											
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_NeedValue " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
											print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + CricketFunctions.GetTargetData(match).getRemaningBall()+ ";");
										}
									}
								}
							}
						}
					}
				}
				
				
				if(match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().isEmpty()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
							CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match)  + 
							" (" + match.getSetup().getTargetType().toUpperCase() + ")" + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info REQUIRED RUN RATE " + 
							CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + 
							" (" + match.getSetup().getTargetType().toUpperCase() + ")" + ";");
				}
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Out STOP;");
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
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
	
	public void populateOpener(boolean is_this_updating, PrintWriter print_writer, int inning , int player1Id,int player2Id ,List<Player> player,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchPromo's inning is null";
		} else {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + category  + 
					CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player1 " + photo_path + 
					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + category + "\\"  + 
					player.get(player1Id - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player2 " + photo_path + 
					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + category + "\\"  + 
					player.get(player2Id - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info "
					+ player.get(player1Id - 1).getFull_name() + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info2 "
					+ player.get(player2Id - 1).getFull_name() + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs1;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue1;");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Balls1 OPENERS;");
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 12.0;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				TimeUnit.SECONDS.sleep(1);
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				this.status = CricketUtil.SUCCESSFUL;
			}	
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
			processAnimation(print_writer, "Out", "START", session_selected_broadcaster,1);
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
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
}