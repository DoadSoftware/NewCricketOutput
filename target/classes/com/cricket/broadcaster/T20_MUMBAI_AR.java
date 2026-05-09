package com.cricket.broadcaster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import jakarta.xml.bind.JAXBException;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.service.CricketService;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Configuration;
import com.cricket.model.Fixture;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.model.Player;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.controller.IndexController;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class T20_MUMBAI_AR extends Scene{

	private String status;
	private String slashOrDash = "-",category = "";
	public Infobar infobar = new Infobar();
	//public String session_selected_broadcaster = "T20_MUMBAI_AR";
	public String which_graphics_onscreen = "";
	private String logo_path2 = "C:\\Images\\TRI_SERIES\\Logos\\";
	private String logo_path = "C:\\Images\\TRI_SERIES\\Logos\\";
	private String base_path = "C:\\Images\\TRI_SERIES\\Base\\";
	private String photo_path = "C:\\Images\\TRI_SERIES\\Photos\\";
	
	public Inning inning;
	public BattingCard battingCard;
	public List<BattingCard> battingCardList = new ArrayList<BattingCard>();
	public List<String> this_data_str = new ArrayList<String>();
	
	public T20_MUMBAI_AR() {
		super();
	}

	public T20_MUMBAI_AR(String scene_path, String which_Layer) {
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
//		switch (which_graphics_onscreen.toUpperCase()) {
//		case "THISOVER_AR":
//			populateThisOver(true,print_writer,match,session_selected_broadcaster);
//			break;
//		case "EQUATION_AR":
//			populateEquationAR(true,print_writer, match, session_selected_broadcaster);
//			break;
//		case "COMPARISON_AR":
//			populateComparisonAR(true,print_writer, match, session_selected_broadcaster);
//			break;
//		case "BOUNDARIES_AR":
//			populateBoundariesAR(true,print_writer, match, session_selected_broadcaster);
//			break;
//		case "PROJECTED_AR":
//			populateProjectedAR(true,print_writer, match, session_selected_broadcaster);
//			break;	
//		}
		//CricketFunctions.getInteractive(match);
		return infobar;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics,Configuration config) throws InterruptedException, ParseException, JAXBException, NumberFormatException, IOException, IllegalAccessException, InvocationTargetException{
		
		//valueToProcess = valueToProcess.replace("Everest_MT20/Scenes", "Everest_Barodaleague_2025/AR_Matt_Scene");
		
		System.out.println("valueToProcess - " + valueToProcess + " whatToProcess - " + whatToProcess);
		
		switch (whatToProcess.toUpperCase()) {
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			switch (config.getBroadcaster().toUpperCase()) {
			case "T20_MUMBAI_AR": case "BARODA_AR":
				return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
			}
		
		case "POPULATE-BOUNDARIES_AR": case "POPULATE-COMPARISON_AR": case "POPULATE-TARGET_AR": case "POPULATE-MATCHID_AR": case "POPULATE-PROJECTED_AR":
		case "POPULATE-FREE_TEXT_AR": case "POPULATE-EQUATION_AR": case "POPULATE-MATCH_ANIMATION_AR": case "POPULATE-THISOVER_AR": case "POPULATE-MATCH_PROMO":
		case "POPULATE-TEAMCELEB_AR": case "POPULATE-PLAYERCELEB": case "POPULATE-MATCH_PROMO_ANIMATION": case "POPULATE-L3-BATMILEDETAILS": case "POPULATE-L3-BOWLERDETAILS":
		case "POPULATE-COUNT_AR": case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-TOSS_AR": case "POPULATE-RUNRATE":
			
			switch (config.getBroadcaster().toUpperCase()) {
			case "T20_MUMBAI_AR": case "BARODA_AR":
				switch(whatToProcess.toUpperCase()) {
				case"POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-BOTTOM":
					break;
				case "POPULATE-L3-INFOBAR":
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,config.getBroadcaster());
					break;
				default:
					scenes.get(1).setWhich_layer(String.valueOf("1"));
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,config.getBroadcaster());
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-L3-BATMILEDETAILS":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateBatMile(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),
							cricketService.getAllPlayer(),match , config.getBroadcaster());
					break;
				case "POPULATE-L3-BOWLERDETAILS":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateBallMile(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),
							cricketService.getAllPlayer(),match , config.getBroadcaster());
					break;	
				case "POPULATE-MATCH_PROMO":
					populateMatchPromo(false,print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(),match , config.getBroadcaster());
					break;
				case "POPULATE-THISOVER_AR":
					populateThisOver(false,print_writer,match,config.getBroadcaster());
					break;
				case "POPULATE-MATCH_ANIMATION_AR":
					populateMatchIdAnimationAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-MATCH_PROMO_ANIMATION":
					populateMatchPromoAnimationAR(false,print_writer,valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(), match, config.getBroadcaster());
					break;
				case "POPULATE-EQUATION_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateEquationAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-FF-POSITION_LANDMARK":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populatePlayerInAt(print_writer, match,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(),cricketService.getTeams(), 
							config.getBroadcaster(),config);
					break;
				case "POPULATE-PLAYERCELEB":
					populatePlayerCelebAR(print_writer, match,valueToProcess.split(",")[1],valueToProcess.split(",")[3],valueToProcess.split(",")[2],cricketService.getAllPlayer(),cricketService.getTeams(), 
							config.getBroadcaster());
					break;
				case "POPULATE-TEAMCELEB_AR":
					System.out.println(valueToProcess);
					populateTeamCelebAR(print_writer, match,valueToProcess.split(",")[1],valueToProcess.split(",")[2],cricketService.getTeams(), config.getBroadcaster());
					break;
				case "POPULATE-FREE_TEXT_AR":
					populateFreeTextAR(print_writer, match,valueToProcess.split(",")[1], config.getBroadcaster());
					break;
				case "POPULATE-BOUNDARIES_AR":
					populateBoundariesAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-COMPARISON_AR":
					populateComparisonAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-TARGET_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateTargetAR(print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-TOSS_AR":
					populateTossAR(print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-MATCHID_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateMatchIdAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-COUNT_AR":
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						category = "M";
					}else {
						category = "W";
					}
					
					populateCountAR(false,print_writer, match, config.getBroadcaster());
					break;
					
				case "POPULATE-RUNRATE":
					populateRunRate(print_writer,false,match, config.getBroadcaster(),valueToProcess);
					break;
				case "POPULATE-PROJECTED_AR":
					populateProjectedAR(false,print_writer, match, config.getBroadcaster());
					break;	
				}
			}
		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BOUNDARIES_AR": case "ANIMATE-IN-COMPARISON_AR": case "ANIMATE-IN-TARGET_AR": case "ANIMATE-IN-MATCHID_AR":
		case "ANIMATE-IN-PROJECTED_AR": case "ANIMATE-IN-FREETEXT_AR": case "ANIMATE-IN-EQUATION_AR": case "ANIMATE-IN-MATCH_ANIMATION_AR": case "ANIMATE-IN-THISOVER_AR":
		case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMCELEB_AR": case "ANIMATE-IN-PLAYERCELEB_AR": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-BATMILEDETAILS":
		case "ANIMATE-IN-COUNT_AR": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-TOSS_AR": case "ANIMATE-IN-RUNRATE_AR":
			
			switch (config.getBroadcaster().toUpperCase()) {
			case "T20_MUMBAI_AR": case "BARODA_AR":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-THISSERIES":
					if(infobar.isInfobar_on_screen() == true) {
						processAnimation(print_writer, "FF_In", "START", config.getBroadcaster(),1);
						TimeUnit.MILLISECONDS.sleep(200);
					}
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-POSITION_LANDMARK":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "POSITION_LANDMARK";
					break;
				case "ANIMATE-IN-COUNT_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "COUNT_AR";
					break;
				case "ANIMATE-IN-BATMILEDETAILS":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "BATMILEDETAILS";
					break;
				case "ANIMATE-IN-BOWLERDETAILS":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "BOWLERDETAILS";
					break;	
				case "ANIMATE-IN-PLAYERCELEB_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "PLAYERCELEB_AR";
					break;
				case "ANIMATE-IN-TEAMCELEB_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "TEAMCELEB_AR";
					break;
				case "ANIMATE-IN-THISOVER_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "THISOVER_AR";
					break;
				case "ANIMATE-IN-MATCH_ANIMATION_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCH_ANIMATION_AR";
					break;
				case "ANIMATE-IN-EQUATION_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "EQUATION_AR";
					break;
				case "ANIMATE-IN-FREETEXT_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "FREETEXT_AR";
					break;
				case "ANIMATE-IN-BOUNDARIES_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "BOUNDARIES_AR";
					break;
				case "ANIMATE-IN-COMPARISON_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "COMPARISON_AR";
					break;
				case "ANIMATE-IN-TARGET_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "TARGET_AR";
					break;
				case "ANIMATE-TOSS_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "TOSS_AR";
					break;
				case "ANIMATE-IN-RUNRATE_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "RUNRATE_AR";
					break;
				case "ANIMATE-IN-MATCH_PROMO":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCHID_PROMO_AR";
					break;
				case "ANIMATE-IN-MATCHID_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCHID_AR";
					break;
				case "ANIMATE-IN-PROJECTED_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
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
						processAnimation(print_writer, "Out", "START", config.getBroadcaster(),1);
						which_graphics_onscreen = "";
						infobar.setInfobar_on_screen(false);
						break;
					case "THISOVER_AR": case "TEAMCELEB_AR": case "MATCH_ANIMATION_AR": case "EQUATION_AR":
					case "FREETEXT_AR": case "PROJECTED_AR": case "MATCHID_PROMO_AR": case "MATCHID_AR":
					case "TARGET_AR": case "COMPARISON_AR": case "BOUNDARIES_AR": case "PLAYERCELEB_AR":
					case "BATMILEDETAILS": case "BOWLERDETAILS": case "COUNT_AR": case "POSITION_LANDMARK": case "TOSS_AR": case "RUNRATE_AR":
						processAnimation(print_writer, "Out", "START", config.getBroadcaster(),1);
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
		case "T20_MUMBAI_AR": case "BARODA_AR":
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
				category = "M\\";
			}else {
				category = "W\\";
			}
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "FROM " + 
					match.getSetup().getVenueName().toUpperCase() + ";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info " + "FROM " + 
					match.getSetup().getVenueName().toUpperCase() + ";");
			
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
				match_name = fix.get(match_number - 1).getMatchfilename().toUpperCase();
			}
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "TOMORROW - " + match_name + ";");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "TOMORROW - " + match_name + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "UP NEXT - " + match_name + ";");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Header " + "UP NEXT - " + match_name + ";");
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
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs " + 
								bc.getRuns() + "*" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + 
								bc.getRuns() + "*" + ";");
					}else {
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Runs " + 
								bc.getRuns() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_FromValue " + 
								bc.getRuns() + ";");
					}
					
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
			case "T20_MUMBAI_AR": case "BARODA_AR":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + inn.getTotalFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + inn.getTotalSixes() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + inn.getBatting_team().getTeamBadge().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + inn.getTotalFours() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + inn.getTotalSixes() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + inn.getBatting_team().getTeamBadge().toUpperCase() + 
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
			case "T20_MUMBAI_AR": case "BARODA_AR":
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor1 " + base_path + 
								"Base1\\\\" + inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor2 " + base_path + 
								"Base2\\\\" + inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_AwayColor1 " + base_path + 
								"Base1\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_AwayColor2 " + base_path + 
								"Base2\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
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
			case "T20_MUMBAI_AR": case "BARODA_AR":
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
			case "T20_MUMBAI_AR": case "BARODA_AR":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeTeamLogo " + logo_path2 + 
						match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_AwayTeamLogo " + logo_path2 + 
						match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
						match.getSetup().getHomeTeam().getTeamName3() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
						match.getSetup().getAwayTeam().getTeamName3() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeTeamLogo " + logo_path2 + 
						match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_AwayTeamLogo " + logo_path2 + 
						match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
						match.getSetup().getHomeTeam().getTeamName3() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
						match.getSetup().getAwayTeam().getTeamName3() + ";");
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
			case "T20_MUMBAI_AR": case "BARODA_AR":
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeam " + 
						match.getSetup().getHomeTeam().getTeamName4() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeam " + 
						match.getSetup().getAwayTeam().getTeamName4() + ";");
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag1_Grp$Flag1*FUNCTION*IMAGESEQUENCE2 SET PATH "
//						+ "D:/EverestCricket/Everest_MT20/Flags/"+ match.getSetup().getHomeTeam().getTeamBadge() +"/F000000.dds;");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag2_Grp$Flag2*FUNCTION*IMAGESEQUENCE2 SET PATH "
//						+ "D:/EverestCricket/Everest_MT20/Flags/"+ match.getSetup().getAwayTeam().getTeamBadge() +"/F000000.dds;");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
						match.getSetup().getHomeTeam().getTeamName1() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
			
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
		case "T20_MUMBAI_AR": case "BARODA_AR":
			
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
		case "T20_MUMBAI_AR": case "BARODA_AR":
			
			inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Inning).findAny().orElse(null);
			
			battingCardList = inning.getBattingCard();
			
			int row_id = 0;
			
			int inAtPosition = 0;
			
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
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
							}
						}
					}else {
						if(bc.getPlayerId() == playerId) {
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
						}
					}
				}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
					inAtPosition++;
					if(bc.getPlayerId() == playerId) {
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + inAtPosition + ";");
					}
				}else {
					
					if(bc.getHowOut() != null) {
						if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
							if(bc.getPlayerId() == playerId) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Value " + bc.getBatterPosition() + ";");
							}
						}else {
							inAtPosition++;
							if(bc.getPlayerId() == playerId) {
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
		case "T20_MUMBAI_AR": case "BARODA_AR":
			 
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
		case "T20_MUMBAI_AR": case "BARODA_AR":
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
			case "T20_MUMBAI_AR": case "BARODA_AR":
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag1_Grp$Flag1*FUNCTION*IMAGESEQUENCE2 SET PATH"
								+ " D:/EverestCricket/Everest_MT20/Flags/"+ inn.getBatting_team().getTeamName4() +"/F000000.dds;");
							
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						//base
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor1 " + base_path + 
								"Base1\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						//
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor2 " + base_path + 
								"Base2\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead01 " + "@"+ proj_score_rate[0] +" (CRR)" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + proj_score_rate[1] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead02 " + "@" + proj_score_rate[2] +" RPO"+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + proj_score_rate[3] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead03 " + "@" + proj_score_rate[4] +" RPO" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + proj_score_rate[5] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead04 " + "@" + proj_score_rate[6] +" RPO" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue04 " + proj_score_rate[7] + ";");
						
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead01 " + "@"+ proj_score_rate[0] +" (CRR)" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + proj_score_rate[1] + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead02 " + "@" + proj_score_rate[2] +" RPO"+ ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + proj_score_rate[3] + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead03 " + "@" + proj_score_rate[4] +" RPO" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + proj_score_rate[5] + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead04 " + "@" + proj_score_rate[6] +" RPO" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue04 " + proj_score_rate[7] + ";");
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
	public void populateTossAR(PrintWriter print_writer,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "T20_MUMBAI_AR":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Inning is null";
			} else {
				
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + 
				match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + " WON TOSS " + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " 
				+ "CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag1_Grp$Flag1*FUNCTION*IMAGESEQUENCE2 SET PATH"
							+ " D:/EverestCricket/Everest_MT20/Flags/"+ match.getSetup().getHomeTeam().getTeamName4() +"/F000000.dds;");
						

				}else {
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + " WON TOSS " + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " 
							+ "CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag1_Grp$Flag1*FUNCTION*IMAGESEQUENCE2 SET PATH"
							+ " D:/EverestCricket/Everest_MT20/Flags/"+ match.getSetup().getAwayTeam().getTeamName4() +"/F000000.dds;");
				}
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		case "BARODA_AR":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Inning is null";
			} else {
				
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + " WON THE TOSS " + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " 
							+ "CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");
				}else {
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + " WON THE TOSS " + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " 
							+ "CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");
				}
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateTargetAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "T20_MUMBAI_AR": case "BARODA_AR":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + "TARGET"  +";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag1_Grp$Flag1*FUNCTION*IMAGESEQUENCE2 SET PATH D:/EverestCricket/Everest_MT20/Flags/"+ 
				match.getMatch().getInning().get(1).getBatting_team().getTeamName4() +"/F000000.dds;");
				
				if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
					if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6  +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALLS"  +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6  +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALLS"  +";");
					}else {
						if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.GetTargetData(match).getTargetOvers() +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "OVERS"  +";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.GetTargetData(match).getTargetOvers() +";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "OVERS"  +";");
							
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALLS"  +";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 +";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALLS"  +";");
							
						}
					}
				}else {
					if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.GetTargetData(match).getTargetOvers() +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "OVERS (" + match.getSetup().getTargetType().toUpperCase() + ")"  +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.GetTargetData(match).getTargetOvers() +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "OVERS (" + match.getSetup().getTargetType().toUpperCase() + ")"  +";");
						
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALLS (" + match.getSetup().getTargetType().toUpperCase() + ")" +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUNS"  +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALLS (" + match.getSetup().getTargetType().toUpperCase() + ")"  +";");
						
					}
				}
				
				//print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info LIVE FROM " + match.getSetup().getVenueName() + ";");
				
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
	
	public void populateRunRate(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster,String valueToProcess) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "T20_MUMBAI_AR": case "BARODA_AR":
		     switch (valueToProcess.split(",")[1]) {
			case "CURRENT RUNRATE":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag1_Grp$Flag1*FUNCTION*IMAGESEQUENCE2 SET PATH D:/EverestCricket/Everest_MT20/Flags/"+ inn.getBatting_team().getTeamName4() +"/F000000.dds;");
					
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "CURRENT RUN RATE" + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + inn.getRunRate() + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor1 " + base_path + 
								"Base1\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						//
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor2 " + base_path + 
								"Base2\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					}
				}
				
				break;
				
			case "REQUIRED RUNRATE":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase("YES") && inn.getInningNumber() == 2) {
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag1_Grp$Flag1*FUNCTION*IMAGESEQUENCE2 SET PATH D:/EverestCricket/Everest_MT20/Flags/"+ inn.getBatting_team().getTeamName4() +"/F000000.dds;");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "REQUIRED RUN RATE" + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " +  CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0,
								CricketFunctions.GetTargetData(match).getRemaningBall(), 2, match)+ ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor1 " + base_path + 
								"Base1\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						//
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor2 " + base_path + 
								"Base2\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
					}
					
				}
				break;

			default:
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase("YES") && inn.getInningNumber() == 2) {
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag1_Grp$Flag1*FUNCTION*IMAGESEQUENCE2 SET PATH D:/EverestCricket/Everest_MT20/Flags/"+ inn.getBatting_team().getTeamName4() +"/F000000.dds;");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead01 " + "CURRENT" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + inn.getRunRate() + ";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead02 " + "REQUIRED" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0,
								CricketFunctions.GetTargetData(match).getRemaningBall(), 2, match) + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor1 " + base_path + 
								"Base1\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						//
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor2 " + base_path + 
								"Base2\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");

					}
				}
				
				
				break;
			}
			break;
			}
			
			
			
		}
	
	public void populateCountAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "T20_MUMBAI_AR": case "BARODA_AR":
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
			case "T20_MUMBAI_AR": case "BARODA_AR":
				
				for(int i=1;i<=9;i++) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ i + " ;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ i + " ;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Base0" + i + " " + ";");
				}
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "THIS OVER" + ";");
				
				Team team = null;
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						team = inn.getBowling_team();
					}
				}
				
				this_data_str = new ArrayList<String>();
				this_data_str.add(String.join(",", 
					    new ArrayList<>(Arrays.asList(IndexController.matchstats.getOverData().getThisOverTxt().split(",")))
				        .stream()
				        .map(s -> s.replace("WIDE", "WD")
				                   .replace("NO_BALL", "NB")
				                   .replace("LEG_BYE", "LB")
				                   .replace("BYE", "B")
				                   .replace("PENALTY", "PN")
				                   .replace("LOG_WICKET", "W")
				                   .replace("WICKET", "W"))
				        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {Collections.reverse(list); return list;}))
				        .toArray(new String[0])));
				
				int totalOverSize = 6;
				
				if(this_data_str.get(this_data_str.size()-1) == null) {
					
				}
				
				if(this_data_str.get(this_data_str.size()-1).split(",").length <= 9) {
					for(int iBall = 0; iBall < this_data_str.get(this_data_str.size()-1).split(",").length; iBall++) {
					
						switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
						case CricketUtil.DOT: case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE: 
						case CricketUtil.FOUR:case CricketUtil.SIX: case "W":
							switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
							case CricketUtil.DOT:
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (iBall+1) + " " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (iBall+1) + " " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + ";");
								break;
							default:
								if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].equalsIgnoreCase("W") || 
										this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("W+")) 
								{
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Base0" + (iBall+1) + " " + base_path + 
											"Base2\\\\" + "EVENT" + CricketUtil.PNG_EXTENSION + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Base0" + (iBall+1) + " " + base_path + 
											"Base2\\\\" + "EVENT" + CricketUtil.PNG_EXTENSION + ";");
								}
								else{
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Base0" + (iBall+1) + " " + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Base0" + (iBall+1) + " " + ";");
								}
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (iBall+1) + " " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (iBall+1) + " " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + ";");
								break;
							}
							break;
						default:
							if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().contains("BOUNDARY")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (iBall+1)  + " " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (iBall+1)  + " " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + ";");
								
							}else if(!this_data_str.get(this_data_str.size()-1).isEmpty()) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (iBall+1)  + " " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase()  + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ (iBall+1)  + " " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase()  + ";");
								
								switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
								case "1B": case "2B": case "3B": case "4B": case "5B": case "6B":
								case "1LB": case "2LB": case "3LB": case "4LB": case "5LB": case "6LB":
									
									break;

								default:
									if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("NB") || 
											this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("WD")) {
										totalOverSize++;
									}
									break;
								}
							}
							
							break;
						}
					}
				}else {
					
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VSelectBalls" + " " + (totalOverSize-1)  + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET VSelectBalls" + " " + (totalOverSize-1)  + ";");
				
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$object$RightData$Side" + WhichSide + "$OverThis$Over"
//						+ "*FUNCTION*Grid*num_col SET " + totalOverSize + "\0", print_writers);
				
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
			case "T20_MUMBAI_AR": case "BARODA_AR":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						
						
					
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Flags$Flag1_Grp$Flag1*FUNCTION*IMAGESEQUENCE2 SET PATH D:/EverestCricket/Everest_MT20/Flags/"+ inn.getBatting_team().getTeamName4() +"/F000000.dds;");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + "@ " + CricketFunctions.generateRunRate(CricketFunctions.
								GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " RPO" +";");
						
						if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getRemaningRuns()+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUN" 
									+ CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  +";");
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.GetTargetData(match).getRemaningBall()+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALL" + 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()  +";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getRemaningRuns()+";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUN" 
									+ CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  +";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.GetTargetData(match).getRemaningBall()+";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALL" + 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()  +";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getRemaningRuns()+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUN" 
									+ CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  +";");
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.GetTargetData(match).getRemaningBall()+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALL" + 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (" + match.getSetup().getTargetType().toUpperCase() + ")"+";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunsValue " + CricketFunctions.GetTargetData(match).getRemaningRuns()+";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + "RUN" 
									+ CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  +";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOversValue " + CricketFunctions.GetTargetData(match).getRemaningBall()+";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "BALL" + 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()+ " (" + match.getSetup().getTargetType().toUpperCase()+ ")"+";");
						}
					}
				}
				
				//print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info LIVE FROM " + match.getSetup().getVenueName() + ";");
				
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
	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic)
	{
		switch(whichGraphic) {
		case "FF_IN":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + "FF_In" + " " + "START" + ";");
			break;
		}	
	}	
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic, String session_selected_broadcaster) throws InterruptedException
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