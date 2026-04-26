package com.cricket.broadcaster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBException;
import com.cricket.model.Statistics;
import com.cricket.service.CricketService;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LLC_AR extends Scene{

	private String status;
	private String slashOrDash = "-";
	public Infobar infobar = new Infobar();
	public String session_selected_broadcaster = "DOAD_AR";
	public String which_graphics_onscreen = "";
	private String logo_path = "C:\\Images\\LLC\\Logos\\";
	
	public LLC_AR() {
		super();
	}

	public LLC_AR(String scene_path, String which_Layer) {
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
			
		}
		//CricketFunctions.getInteractive(match);
		return infobar;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics) throws InterruptedException, ParseException, JAXBException, NumberFormatException, IOException, IllegalAccessException, InvocationTargetException{
		switch (whatToProcess.toUpperCase()) {
		
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_AR":
				return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
			}
		
		case "POPULATE-BOUNDARIES_AR": case "POPULATE-COMPARISON_AR": case "POPULATE-TARGET_AR": case "POPULATE-MATCHID_AR": case "POPULATE-PROJECTED_AR":
		case "POPULATE-FREE_TEXT_AR": case "POPULATE-EQUATION_AR":
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_AR":
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
				case "POPULATE-FREE_TEXT_AR":
					populateFreeTextAR(print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-BOUNDARIES_AR":
					populateBoundariesAR(print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-COMPARISON_AR":
					populateComparisonAR(print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-TARGET_AR":
					populateTargetAR(print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-EQUATION_AR":
					populateEquationAR(print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-MATCHID_AR":
					populateMatchIdAR(print_writer, match, session_selected_broadcaster);
					break;
				case "POPULATE-PROJECTED_AR":
					populateProjectedAR(print_writer, match, session_selected_broadcaster);
					break;	
				}
			}
		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BOUNDARIES_AR": case "ANIMATE-IN-COMPARISON_AR": case "ANIMATE-IN-TARGET_AR": case "ANIMATE-IN-MATCHID_AR":
		case "ANIMATE-IN-PROJECTED_AR": case "ANIMATE-IN-FREETEXT_AR": case "ANIMATE-IN-EQUATION_AR":
		
			switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_AR":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-THISSERIES":
					if(infobar.isInfobar_on_screen() == true) {
						processAnimation(print_writer, "FF_In", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(200);
					}
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-EQUATION_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "EQUATION_AR";
					break;
				case "ANIMATE-IN-FREETEXT_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "FREETEXT_AR";
					break;
				case "ANIMATE-IN-BOUNDARIES_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "BOUNDARIES_AR";
					break;
				case "ANIMATE-IN-COMPARISON_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "COMPARISON_AR";
					break;
				case "ANIMATE-IN-TARGET_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "TARGET_AR";
					break;
				case "ANIMATE-IN-MATCHID_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCHID_AR";
					break;
				case "ANIMATE-IN-PROJECTED_AR":
					processAnimation(print_writer, "In", "START", session_selected_broadcaster,1);
					print_writer.println("LAYER1*EVEREST*STAGE START;");
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
					case "FREETEXT_AR": case "EQUATION_AR": case "PROJECTED_AR": case "MATCHID_AR":
					case "TARGET_AR": case "COMPARISON_AR": case "BOUNDARIES_AR":
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
		case "DOAD_AR":
			switch(which_layer) {
			case 1:
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
//				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
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
	
	public void populateBoundariesAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_AR":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + inn.getTotalFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + inn.getTotalSixes() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 1;");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + inn.getTotalFours() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + inn.getTotalSixes() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
					}
				}
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
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
	
	public void populateComparisonAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_AR":
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getSetup().getMatchIdent() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getSetup().getMatchIdent() + ";");

						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + match.getMatch().getInning().get(0).getBatting_team().getTeamName4().toUpperCase() + ".png" +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + ".png" +";");

						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreA " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreB " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + ";");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + inn.getBowling_team().getTeamName3().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamB " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 1;");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + match.getMatch().getInning().get(0).getBatting_team().getTeamName4().toUpperCase() + ".png" +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + ".png" +";");

						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreA " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + ";");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreB " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + ";");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + inn.getBowling_team().getTeamName3().toUpperCase() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamB " + inn.getBatting_team().getTeamName3().toUpperCase() + ";");
						
					}
				}
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
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
	
	public void populateMatchIdAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_AR":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
						match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
						match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
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
	
	public void populateFreeTextAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_AR":
			String text1_to_return = "",text2_to_return = "";
			
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
		             found2 = true;
		             break;
		        }
		        lineIndex2++;
		    }
		    if(found2 == false) {
		    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader22 " + "" + ";");
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
		    print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 1;");
		    print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
		    
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
	
	public void populateProjectedAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_AR":
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
								inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead01 " + "@"+ proj_score_rate[0] +" (CRR)" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + proj_score_rate[1] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead02 " + "@" + proj_score_rate[2] +" RPO"+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + proj_score_rate[3] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead03 " + "@" + proj_score_rate[4] +" RPO" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + proj_score_rate[5] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead04 " + "@" + proj_score_rate[6] +" RPO" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue04 " + proj_score_rate[7] + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 1;");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
								inn.getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
						
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
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
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
	
	public void populateTargetAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_AR":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + ".png" +";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamName4().toUpperCase() + ".png" +";");

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
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHead " + "REQUIRED RUN RATE " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + CricketFunctions.generateRunRate
						(requiredRuns, 0, requiredBalls, 2,match) + ";");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHead " + "REQUIRED RUN RATE " + ";");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + CricketFunctions.generateRunRate
						(requiredRuns, 0, requiredBalls, 2,match) + ";");
				
				if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
					if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS " +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS " +";");
					}else {
						if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" +";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" +";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS"  +";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS"  +";");
						}
					}
				}else {
					if(Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" + 
						" ("+ match.getSetup().getTargetType().toUpperCase() +")"  +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + CricketFunctions.GetTargetData(match).getTargetOvers() + " OVERS" + 
						" ("+ match.getSetup().getTargetType().toUpperCase() +")"  +";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS" + 
								" ("+ match.getSetup().getTargetType().toUpperCase() +")"  +";");
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) * 6 + " BALLS" + 
								" ("+ match.getSetup().getTargetType().toUpperCase() +")"  +";");
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
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
	public void populateEquationAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_AR":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + 
								inn.getBatting_team().getTeamName1().toUpperCase() + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
								inn.getBatting_team().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
						
						if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
							if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore NEED " + 
										CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + 
										 CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
										 CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
							}else {
								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore NEED " + 
											CricketFunctions.GetTargetData(match).getRemaningRuns()  + " RUN" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + 
											CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall())  + " OVERS" + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore NEED " + 
											CricketFunctions.GetTargetData(match).getRemaningRuns()+ " RUN" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + 
											CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
								}
							}
						}else {
							if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore NEED " + 
										CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + 
										Double.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) + 
										CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + 
										" (" + match.getSetup().getTargetType().toUpperCase() + ") OVERS" + ";");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore NEED " + 
										CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  + ";");
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers OFF " + 
										CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + 
										" ("+ match.getSetup().getTargetType().toUpperCase() +")" + ";");
							}
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
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
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
}