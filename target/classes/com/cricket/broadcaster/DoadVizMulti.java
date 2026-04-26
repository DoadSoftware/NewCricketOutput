//package com.cricket.broadcaster;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.UnknownHostException;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import javax.xml.bind.JAXBException;
//
//import com.cricket.containers.Infobar;
//import com.cricket.containers.Scene;
//import com.cricket.model.Configuration;
//import com.cricket.model.Dictionary;
//import com.cricket.model.ForeignLanguageData;
//import com.cricket.model.Match;
//import com.cricket.model.MatchAllData;
//import com.cricket.model.MultiLanguageDatabase;
//import com.cricket.model.Statistics;
//import com.cricket.model.Team;
//import com.cricket.service.CricketService;
//import com.cricket.util.CricketFunctions;
//import com.cricket.util.CricketUtil;
//
//public class DoadVizMulti extends Scene{
//
//	public String broadcaster = "DOAD-VIZ-MULTI";
//	public String status; 
//	public String slashOrDash = "-";
//	public Infobar infobar = new Infobar(); 
//	public String which_graphic_on_screen = "";
//	public List <ForeignLanguageData> foreignLanguageData;
//	public String logo_path = "C:\\\\Images\\\\MULTI\\\\Logos\\\\";
//	public int which_side = 1;
//	
//	public DoadVizMulti() {
//		super();
//	}
//
//	public DoadVizMulti(String scene_path, String which_Layer) {
//		super(scene_path, which_Layer);
//	}
//	
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public Infobar updateInfobar(List<Scene> scenes, MatchAllData match, List<PrintWriter> print_writers,Configuration config,
//			MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList) throws InterruptedException
//	{
//		if(infobar.isInfobar_on_screen() == true) {
////			infobar = populateInfobarTeamScore(infobar,true, print_writers, match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
////			infobar = populateVizInfobarMiddle(infobar, true, print_writers, match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
////			infobar = populateVizInfobarRightTop(infobar, true, print_writers, match, broadcaster, config, multilanguagedata, foreignLanguageDataList);
//		}
//		return infobar;
//	}
//	
//	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
//			List<PrintWriter> print_writers, List<Scene> scenes, String valueToProcess, List<Statistics> statistics,Configuration config,
//			MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList) throws InterruptedException, ParseException, JAXBException, UnknownHostException, IOException
//	{
//		switch (whatToProcess) {
//		case "ANIMATE-IN-INFOBAR": case "ANIMATE-IN-BUG": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-BUG-BOWLER":
//		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH":
//		case "ANIMATE-IN-BATSMANSTATS": case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-MATCHSUMARRY":
//		case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-SCORECARD":
//			switch (whatToProcess.toUpperCase()) {
//			case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-BATSMANSTATS": 
//			case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-SCORECARD":
//				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
//					AnimateInGraphics(print_writers, "LT_IN",config);
//					//TimeUnit.MILLISECONDS.sleep(200);
//					TimeUnit.SECONDS.sleep(1);
//				}
//				break;
//			}
//			switch (whatToProcess.toUpperCase()) {
//			case "ANIMATE-IN-SCORECARD":
//				if(which_graphic_on_screen == "") {
//					AnimateInGraphics(print_writers, "BATBALLSUMMARY_SCORECARD",config);
//				}else {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*FF_Change START",print_writers,config);
//					TimeUnit.SECONDS.sleep(7);
//					populateScorecard(print_writers, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster,cricketService.getDictionary(),config,multilanguagedata,foreignLanguageDataList,1);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*FF_Change SHOW 0.0",print_writers,config);
//				}
//				
////				if(which_graphic_on_screen == "PARTNERSHIP") {
//////					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
//////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*PartnershipOut START",print_writers,config);
////				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BattingCardIn START",print_writers,config);
//////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
////
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BattingCardIn START",print_writers,config);
////				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BowlingCardOut START",print_writers,config);
//////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
////
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BattingCardIn START",print_writers,config);
////				}else {
////					AnimateInGraphics(print_writers, "BATBALLSUMMARY_SCORECARD",config);
////				}
//				
//				which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD";
//				break;
//			case "ANIMATE-IN-PARTNERSHIP":
//				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
////					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*PartnershipIn START",print_writers,config);
//				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*SummaryOut START",print_writers,config);
////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
//
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*PartnershipIn START",print_writers,config);
//				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BowlingCardOut START",print_writers,config);
////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
//
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*PartnershipIn START",print_writers,config);
//				}else {
//					AnimateInGraphics(print_writers, "PARTNERSHIP",config);
//				}
//				
//				which_graphic_on_screen = "PARTNERSHIP";
//				break;
//			case "ANIMATE-IN-MATCHSUMARRY":
//				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
////					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
////					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*SummaryIn START",print_writers,config);
//				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BowlingCardOut START",print_writers,config);
////					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*SummaryIn START",print_writers,config);
//				}else if(which_graphic_on_screen == "PARTNERSHIP") {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*PartnershipOut START",print_writers,config);
////					print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*SummaryIn START",print_writers,config);
//				}else {
//					AnimateInGraphics(print_writers, "MATCHSUMMARY",config);
//				}
//				which_graphic_on_screen = "BATBALLSUMMARY_MATCHSUMMARY";
//				break;
//			case "ANIMATE-IN-BOWLINGCARD":
//				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
////					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BowlingCardIn START",print_writers,config);
//				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*SummaryOut START",print_writers,config);
////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
//
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BowlingCardIn START",print_writers,config);
//				}else if(which_graphic_on_screen == "PARTNERSHIP") {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*PartnershipIn START",print_writers,config);
////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
//
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BowlingCardIn START",print_writers,config);
//				}else {
//					AnimateInGraphics(print_writers, "BOWLINGCARD",config);
//				}
//				
//				which_graphic_on_screen = "BATBALLSUMMARY_BOWLINGCARD";
//				break;
//			case "ANIMATE-IN-BOWLERSTATS":
//				AnimateInGraphics(print_writers, "BOWLERSTATS",config);
//				which_graphic_on_screen = "BOWLERSTATS";
//				break;
//			case "ANIMATE-IN-BATSMANSTATS":
//				AnimateInGraphics(print_writers, "BATSMANSTATS",config);
//				which_graphic_on_screen = "BATSMANSTATS";
//				break;
//			case "ANIMATE-IN-BUG-BOWLER":
//				AnimateInGraphics(print_writers, "BUG-BOWLER",config);
//				which_graphic_on_screen = "BUG-BOWLER";
//				break;
//			case "ANIMATE-IN-BUG":
//				AnimateInGraphics(print_writers, "BUG",config);
//				which_graphic_on_screen = "BUG";
//				break;
//			case "ANIMATE-IN-MATCHID":
//				AnimateInGraphics(print_writers, "MATCHID",config);
//				which_graphic_on_screen = "MATCHID";
//				break;
//			case "ANIMATE-IN-BATSMAN_THIS_MATCH":
//				AnimateInGraphics(print_writers, "BATSMAN_THIS_MATCH",config);
//				which_graphic_on_screen = "BATSMAN_THIS_MATCH";
//				break;
//			case "ANIMATE-IN-INFOBAR":
//				if(infobar.isInfobar_on_screen() == true) {
//					AnimateOutGraphics(print_writers, "ANIMATE-OUT-IDENT",config);
//					AnimateInGraphics(print_writers, "MAIN",config);
//					which_graphic_on_screen = "SCOREBUG";
//					infobar.setInfobar_on_screen(true);
//					
//				}else {
//					AnimateInGraphics(print_writers, "SCOREBUG",config);
//					which_graphic_on_screen = "SCOREBUG";
//					infobar.setInfobar_on_screen(true);
//				}
//				
//				break;
//			case "CLEAR-ALL":
//				CricketFunctions.DoadWriteSameCommandToEachViz("IMAGE INFO", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER SET_OBJECT SCENE*" + valueToProcess.split(",")[0], print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*SCENE_DATA INITIALIZE", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE SHOW 0.0", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("SCENE CLEANUP", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("IMAGE CLEANUP", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("GEOM CLEANUP", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("FONT CLEANUP", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/DOAD_In_House/ScoreBug", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE SHOW 0.0", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("SCENE CLEANUP", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("IMAGE CLEANUP", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("GEOM CLEANUP", print_writers,config);
//				CricketFunctions.DoadWriteSameCommandToEachViz("FONT CLEANUP", print_writers,config);
//				
//				which_graphic_on_screen = "";
//	            infobar = new Infobar();
//	            infobar.setInfobar_on_screen(false);
//				break;	
//					
//			case "ANIMATE-OUT":
//				switch(which_graphic_on_screen) {
//				case "BATBALLSUMMARY_SCORECARD":
//					AnimateOutGraphics(print_writers, "BATBALLSUMMARY_SCORECARD",config);
//					TimeUnit.SECONDS.sleep(1);
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				case "PARTNERSHIP":
//					AnimateOutGraphics(print_writers, "PARTNERSHIP",config);
//					TimeUnit.SECONDS.sleep(1);
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				case "BATBALLSUMMARY_MATCHSUMMARY":
//					AnimateOutGraphics(print_writers, "BATBALLSUMMARY_MATCHSUMMARY",config);
//					TimeUnit.SECONDS.sleep(1);
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				case "BATBALLSUMMARY_BOWLINGCARD":
//					AnimateOutGraphics(print_writers, "BATBALLSUMMARY_BOWLINGCARD",config);
//					TimeUnit.SECONDS.sleep(1);
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				case "BOWLERSTATS":
//					AnimateOutGraphics(print_writers, "BOWLERSTATS",config);
//					TimeUnit.SECONDS.sleep(1);
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				case "BATSMANSTATS":
//					AnimateOutGraphics(print_writers, "BATSMANSTATS",config);
//					TimeUnit.SECONDS.sleep(1);
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				case "BUG-BOWLER":
//					AnimateOutGraphics(print_writers, "BUG-BOWLER",config);
//					TimeUnit.SECONDS.sleep(1);
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				case "BUG":
//					AnimateOutGraphics(print_writers, "BUG",config);
//					TimeUnit.SECONDS.sleep(1);
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				case "SCOREBUG":
//					AnimateOutGraphics(print_writers, "SCOREBUG",config);
//					infobar.setInfobar_on_screen(false);
//					infobar = new Infobar();
//					which_graphic_on_screen = "";
//					break;
//				case "MATCHID":
//					AnimateOutGraphics(print_writers, "MATCHID",config);
//					TimeUnit.SECONDS.sleep(1);
//					
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				case "BATSMAN_THIS_MATCH":
//					AnimateOutGraphics(print_writers, "BATSMAN_THIS_MATCH",config);
//					TimeUnit.SECONDS.sleep(1);
//					
//					which_graphic_on_screen = "";
//					resetInfobarAnimation(print_writers,"LT_FRAME",config);
//					break;
//				}
//				break;
//			
//			}
//			break;
//		
//			case "POPULATE-L3-BUG": case "POPULATE-L3-INFOBAR": case "POPULATE-FF-MATCHID": case "POPULATE-L3-BUG-BOWLER": case "POPULATE-L3-BATSMANSTATS":
//			case "POPULATE-L3-BOWLERSTATS": case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-FF-PARTNERSHIP":
//				if(which_graphic_on_screen == "SCOREBUG") {
//					
//				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
//						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
//						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
//						
//						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
//						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
//						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
//						 
//						 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
//						 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
//						 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
//						 
//						 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
//						 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
//						 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD")) {
//					}
//				else if(which_graphic_on_screen != "") {
//					AnimateOutGraphics(print_writers, which_graphic_on_screen.toUpperCase(),config);
//				}
//			
//			switch(whatToProcess.toUpperCase()) {
//			case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-DIRECTOR": 
//			case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-RIGHT":
//				break;
//			case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-IDENT":
//				if(infobar.isInfobar_on_screen() == true) {
//					break;
//				}else {
//					scenes.get(0).scene_load(print_writers, broadcaster);
//				}
//				break;
//			default:
//				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
//				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
//				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
//				
//				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
//				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
//				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
//				 
//				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
//				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
//				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
//				 
//				 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
//				 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
//				 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD")) {
//				//AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
//			}else {
//				scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
//				scenes.get(1).scene_load(print_writers,broadcaster);
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE SHOW 0.0",print_writers,config);
//			}
//				
//				break;
//			}
//			switch (whatToProcess.toUpperCase()) {
//			case "POPULATE-FF-PARTNERSHIP":
////				populatePartnership(print_writers,Integer.valueOf(valueToProcess.split(",")[1]), match,valueToProcess.split(",")[0],config,multilanguagedata,foreignLanguageDataList,
////						cricketService.getTeams(),cricketService.getDictionary());
//				break;
//			case "POPULATE-FF-MATCHSUMMARY":
////				populateMatchsummary(print_writers, Integer.valueOf(valueToProcess.split(",")[1]), match, valueToProcess.split(",")[0],config,multilanguagedata,foreignLanguageDataList,
////						cricketService.getTeams(),cricketService.getDictionary());
//				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || 
//						which_graphic_on_screen == "POINTSTABLE") {	
//					//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
//					if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 BattingCardOut 0.500", print_writers,config);
//
//					}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 BowlingCardOut 0.500", print_writers,config);
//
//					}else if(which_graphic_on_screen == "PARTNERSHIP") {
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 PointsTableOut 0.500", print_writers,config);
//
//					}
//				}
//				break;
//			case "POPULATE-FF-BOWLINGCARD":
//				if(which_graphic_on_screen == "") {
//					which_side = 1;
//					populateBowlingcard(print_writers, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster,cricketService.getDictionary(),config,multilanguagedata,foreignLanguageDataList,
//							which_side);
//				}else {
//					which_side = 2;
//					populateBowlingcard(print_writers, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster,cricketService.getDictionary(),config,multilanguagedata,foreignLanguageDataList,
//							which_side);
//				}
//				
////				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
////						which_graphic_on_screen == "POINTSTABLE") {	
//////					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
////					
////					  if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 BattingCardOut 0.500", print_writers,config);
////
////					  }else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 SummaryOut 0.500", print_writers,config);
////
////					  }else if(which_graphic_on_screen == "PARTNERSHIP") {
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 PointsTableOut 0.500", print_writers,config);
////					  }
////					 
////				}
//				break;
//			case "POPULATE-FF-SCORECARD":
//				if(which_graphic_on_screen == "") {
//					which_side = 1;
//					populateScorecard(print_writers, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster,cricketService.getDictionary(),config,multilanguagedata,foreignLanguageDataList,which_side);
//				}else {
//					which_side = 2;
//					populateScorecard(print_writers, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster,cricketService.getDictionary(),config,multilanguagedata,foreignLanguageDataList,which_side);
//				}
//				TimeUnit.SECONDS.sleep(2);
//				break;
//			case "POPULATE-L3-BOWLERSTATS":
////				populateBowlerstats(print_writers, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
////						Integer.valueOf(valueToProcess.split(",")[3]), cricketService.getTeams(), match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
//				break;
//			case "POPULATE-L3-BATSMANSTATS":
////				populateBatsmanstats(print_writers, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
////						Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
//				break;
//			case "POPULATE-L3-BUG-BOWLER":
////				populateBugBowler(print_writers, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
////						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
//				break;
//			case "POPULATE-L3-BUG":
////				populateBug(print_writers, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
////						valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
//				break;
//			case "POPULATE-L3-BATSMAN_THIS_MATCH":
////				populateLtBatsmanThisMatch(print_writers, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
////						match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
//				break;
//			case "POPULATE-FF-MATCHID":
////				populateMatchId(print_writers,valueToProcess.split(",")[0], match,cricketService.getTeams(),cricketService.getVenues(),cricketService.getDictionary(), broadcaster,config,multilanguagedata,foreignLanguageDataList);
//				break;
//				
//			case "POPULATE-L3-INFOBAR":
//				
//				infobar.setMiddle_section(valueToProcess.split(",")[1]);
//				infobar.setBottom_right_top_section(valueToProcess.split(",")[2]);
//				
////				populateInfobar(infobar, print_writers, valueToProcess.split(",")[0],match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
//				infobar.setIdent_section("");
//				break;
//				//return infobar;
//			}
//		}
//			//return JSONObject.fromObject(this_doad).toString();			
//			return null;
//	}
//	public String resetInfobarAnimation(List<PrintWriter> print_writers,String which_frame,Configuration config) throws InterruptedException {
//	
//		switch(which_frame.toUpperCase()) {
//		case "FF_FRAME":
//			if(infobar.isInfobar_on_screen() == true) {
//				AnimateOutGraphics(print_writers, "FF_OUT",config);
//				which_graphic_on_screen = "SCOREBUG";
//			}
//			break;
//		case "LT_FRAME":
//			if(infobar.isInfobar_on_screen() == true) {
//				//TimeUnit.SECONDS.sleep(1);
//				AnimateOutGraphics(print_writers, "Lt_Out",config);
//				which_graphic_on_screen = "SCOREBUG";
//			}
//			break;
//		}
//	return "";
//}
//	
//	public void AnimateInGraphics(List<PrintWriter> print_writers, String whichGraphic, Configuration config) throws InterruptedException
//	{
//		
//		switch(whichGraphic) {
//		case "BATBALLSUMMARY_SCORECARD":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*In START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*FF_In START",print_writers,config);
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BattingCardIn START",print_writers,config);
//			break;
//		case "RESET":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START",print_writers,config);
//			break;
//		
//		case "MATCHID":	case "BATSMAN_THIS_MATCH": case "BUG": case "BUG-BOWLER": case "BATSMANSTATS": case "BOWLERSTATS":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*In START",print_writers,config);
//			TimeUnit.SECONDS.sleep(1);
//			break;
//			
//		case "SCOREBUG":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ScoreBugIn START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*AllSections$Section7All$Sec4And5_Out START",print_writers,config);
//			TimeUnit.SECONDS.sleep(1);
//
//			break;
//		case "MATCHSUMMARY":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*In START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*SummaryIn START",print_writers,config);
//			break;
//		case "BOWLINGCARD":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*In START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BowlingCardIn START",print_writers,config);
//			break;
//		case "PARTNERSHIP":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*In START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*PartnershipIn START",print_writers,config);
//			break;
//		case "LT_IN":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Lt_In START",print_writers,config);
//			break;
//		}	
//	}
//	public void AnimateOutGraphics(List<PrintWriter> print_writers, String whichGraphic , Configuration config) throws InterruptedException {
//		switch(whichGraphic) {
//		
//		case "MATCHID":	case "BATSMAN_THIS_MATCH": case "BUG": case "BUG-BOWLER": case "BATSMANSTATS": case "BOWLERSTATS":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*Out START",print_writers,config);
//			TimeUnit.SECONDS.sleep(1);
//			break;
//		case "BATBALLSUMMARY_SCORECARD":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*Out START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BattingCardOut CONTINUE",print_writers,config);
//			break;
//		case "BATBALLSUMMARY_MATCHSUMMARY":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*Out START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*SummaryOut START",print_writers,config);
//			break;	
//		case "BATBALLSUMMARY_BOWLINGCARD":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*Out START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*BowlingCardOut START",print_writers,config);
//			break;
//		case "PARTNERSHIP":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*Out START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*STAGE*DIRECTOR*PartnershipOut START",print_writers,config);
//			break;
//		case "SCOREBUG":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ScoreBugOut START",print_writers,config);
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START",print_writers,config);
//			break;
//		case "Lt_Out":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Lt_Out START",print_writers,config);
//			break;	
//		}
//	}
//	public void processAnimation(List<PrintWriter> print_writers, String animationName,String animationCommand, String which_broadcaster, Configuration config) throws InterruptedException
//	{
//		switch(which_broadcaster.toUpperCase()) {
//		case "DOAD_VIZ": case "DOAD-VIZ-MULTI":
//			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*"+ animationName + " " + animationCommand +" ",print_writers,config);
//			TimeUnit.SECONDS.sleep(1);
//			break;
//		case "DOAD_EVEREST":
//			CricketFunctions.DoadWriteSameCommandToEachViz("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";",print_writers,config);
//			
//			break;
//		}
//		
//	}
//
//	public void populateBowlingcard(List<PrintWriter> print_writers,String viz_scene,boolean is_this_updating, int whichInning,Match match, String broadcaster,
//			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList,List<Team>team,List<Dictionary>dict) throws InterruptedException 
//	{
//		if (match == null) {
//			this.status = "ERROR: Match is null";
//		} else if (match.getInning() == null) {
//			this.status = "ERROR: Bowlingcard's inning is null";
//		} else {
//			foreignLanguageData = new ArrayList<ForeignLanguageData>();
//			
//			
//			foreignLanguageData.setHindiText(whichInning, null);
//			foreignLanguageData.setHindiText("3");
//			foreignLanguageData.setTamilText("6");
//			foreignLanguageData.setTeluguText("7");
//			
//			CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", 
//					config, broadcaster, print_writers, foreignLanguageData);
//			
//			for(Dictionary dicti : dict) {
//				
//				if(match.getTournament().toUpperCase().equalsIgnoreCase(dicti.getEnglishSentence())) {
//					foreignLanguageData.setEnglishText(dicti.getEnglishSentence());
//					foreignLanguageData.setHindiText(dicti.getHindiSentence());
//					foreignLanguageData.setTamilText(dicti.getTamilSentence());
//					foreignLanguageData.setTeluguText(dicti.getTeluguSentence());
//					
//					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tEventName SET ", config, broadcaster, print_writers,foreignLanguageData);
//				}else {
//					foreignLanguageData.setEnglishText("");
//					foreignLanguageData.setHindiText("");
//					foreignLanguageData.setTamilText("");
//					foreignLanguageData.setTeluguText("");
//					
//					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tEventName SET ", config, broadcaster, print_writers,foreignLanguageData);
//				}
//				
//				if(match.getMatchIdent().toUpperCase().equalsIgnoreCase(dicti.getEnglishSentence())) {
//					foreignLanguageData.setEnglishText(dicti.getEnglishSentence());
//					foreignLanguageData.setHindiText(dicti.getHindiSentence());
//					foreignLanguageData.setTamilText(dicti.getTamilSentence());
//					foreignLanguageData.setTeluguText(dicti.getTeluguSentence());
//					
//					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallMatchId SET ", config, broadcaster, print_writers,foreignLanguageData);
//				}else {
//					foreignLanguageData.setEnglishText("");
//					foreignLanguageData.setHindiText("");
//					foreignLanguageData.setTamilText("");
//					foreignLanguageData.setTeluguText("");
//					
//					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallMatchId SET ", config, broadcaster, print_writers,foreignLanguageData);
//				}
//			}
//			
//			int row_id = 0; 
//			for(Inning inn : match.getInning()) {
//				if (inn.getInningNumber() == whichInning) {
//
//					for(Team tm : team) {
//						if (inn.getBowlingTeamId() == match.getHomeTeamId()) {
//							if(match.getHomeTeamId() == tm.getTeamId()) {
//								
//								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata, 
//										match.getHomeTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
//								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallBowlingTeamName SET ", config, broadcaster, print_writers, foreignLanguageData);
//							}
//							
//							if(match.getAwayTeamId() == tm.getTeamId()) {
//								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata,
//										match.getAwayTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
//								CricketFunctions.DoadWriteVariousLanguageTextToEachViz(" RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallBattingTeamName SET ", config, broadcaster, print_writers, foreignLanguageData);
//							}
//						}else {
//							if(match.getAwayTeamId() == tm.getTeamId()) {
//								
//								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata, 
//										match.getAwayTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
//								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallBowlingTeamName SET ", config, broadcaster, print_writers, foreignLanguageData);
//							}
//							
//							if(match.getHomeTeamId() == tm.getTeamId()) {
//								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata, 
//										match.getHomeTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
//								CricketFunctions.DoadWriteVariousLanguageTextToEachViz(" RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallBattingTeamName SET ", config, broadcaster, print_writers, foreignLanguageData);
//							}
//						}
//						
//					}
//
//					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main$BowlingData$DataAllGrp*FUNCTION*Omo*vis_con SET " + inn.getBowlingCard().size(), 
//							config, broadcaster, print_writers, foreignLanguageData);
//					
//					for (BowlingCard boc : inn.getBowlingCard()) {
//						row_id = row_id + 1;
//					
//						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
//								boc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
//						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallPlayerName" + row_id + " SET ", config, broadcaster, print_writers, foreignLanguageData);
//						
//						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
//								"EXTRAS", "", null, 0, 0, foreignLanguageDataList);
//						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallExtrasHead SET ", config, broadcaster, print_writers, foreignLanguageData);
//						
//						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
//								"OVERS", "", null, 0, 0, foreignLanguageDataList);
//						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallOverHead SET ", config, broadcaster, print_writers, foreignLanguageData);
//						
//						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
//								"DOTS", "", null, 0, 0, foreignLanguageDataList);
//						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallMaidensHead SET ", config, broadcaster, print_writers, foreignLanguageData);
//						
//						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
//								"ECONOMY", "", null, 0, 0, foreignLanguageDataList);
//						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallEconomyHead SET ", config, broadcaster, print_writers, foreignLanguageData);
//						
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallOverValue" + row_id + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()), print_writers);
//						
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallPlayerRuns" + row_id + " SET " + boc.getWickets() + slashOrDash + boc.getRuns(), print_writers);
//						
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallMaidenValue" + row_id + " SET " + boc.getDots(), print_writers);
//						
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallExtraValue" + row_id + " SET " + String.valueOf(boc.getWides() + boc.getNoBalls()), print_writers);
//						
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallEconomyValue" + row_id + " SET " + boc.getEconomyRate(), print_writers);
//						
//					}
//					
//					if(inn.getBowlingCard().size()<=8) {
//						if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
//							for(FallOfWicket fow : inn.getFallsOfWickets()) {								
//								if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
//									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallFowValue" + fow.getFowNumber() + " SET " + fow.getFowNumber() + slashOrDash + fow.getFowRuns(), print_writers);
//									
//									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$group*Active SET 0", print_writers);
//									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$BestHead$Language1*GEOM*TEXT SET  ", print_writers);
//									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$BestValue*GEOM*TEXT SET  ", print_writers);
//									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$BestOver$Language1*GEOM*TEXT SET  ", print_writers);
//									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$PlayerFirstName$Language1*GEOM*TEXT SET  ", print_writers);
//									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$PlayerLastName$Language1*GEOM*TEXT SET  ", print_writers);
//									
//									for(int value=10; inn.getTotalWickets() < value;value--) {
//										if(value < 6) {
//											CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallFowValue" + value + " SET " + " ", print_writers);
//										}
//										else {
//											CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallFowValue" + value + " SET " + " ", print_writers);
//										}
//										
//									}	
//								}		
//							}
//						}
//					}
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallBestText SET " + "", print_writers);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallBestOver SET " + "", print_writers);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallPlayerFirstName SET " + "", print_writers);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallPlayerLastName SET " + "", print_writers);
//
//					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
//							"FALL OF WICKET", "", null, 0, 0, foreignLanguageDataList);
//					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallFallOfWickets SET ", config, broadcaster, print_writers, foreignLanguageData);
//					
//					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
//							"EXTRAS", "", null, 0, 0, foreignLanguageDataList);
//					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallExtrasHead SET ", config, broadcaster, print_writers, foreignLanguageData);
//					
//					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
//							"OVERS", "", null, 0, 0, foreignLanguageDataList);
//					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallOversHead SET ", config, broadcaster, print_writers, foreignLanguageData);
//					
////					if(match.getInning().get(whichInning-1).getBowlingCard() != null) {
////						Collections.sort(match.getInning().get(whichInning-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
//					
////						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$InninSummary$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + 
////								match.getInning().get(whichInning-1).getBowlingCard().get(0).getPlayer().getTicker_name() + "\0");
////						
////						
////						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$InninSummary$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + 
////								match.getInning().get(whichInning-1).getBowlingCard().get(0).getWickets() + slashOrDash + match.getInning().get(whichInning-1).getBowlingCard().get(0).getRuns() + "\0");
////						TimeUnit.MILLISECONDS.sleep(2);
////						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$InninSummary$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + 
////								CricketFunctions.OverBalls(match.getInning().get(whichInning-1).getBowlingCard().get(0).getOvers(), match.getInning().get(whichInning-1).getBowlingCard().get(0).getBalls()) + "\0");
////						TimeUnit.MILLISECONDS.sleep(2);
////					}
//					
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallExtrasValue SET " + inn.getTotalExtras(), print_writers);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallOversValue SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()), print_writers);
//					if(inn.getTotalWickets() >= 10) {
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallTotalScore SET " + inn.getTotalRuns(), print_writers);
//					}
//					else {
//						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallTotalScore SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()), print_writers);
//					}
//				}
//			}
//		}
//	}
////	public void populateMatchsummary(List<PrintWriter> print_writers, int whichInning, Match match, String viz_scene_path,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList,List<Team>team,List<Dictionary>dict)
////	{
////		if (match == null) {
////			this.status = "ERROR: Match is null";
////		} else if (match.getInning() == null) {
////			this.status = "ERROR: Match Summary's inning is null";
////		} else {
////			
////			int row_id = 0, max_Strap = 0, total_inn = 0,row = 0;
////			String teamname = ""; 
////			
////			foreignLanguageData = new ForeignLanguageData();
////			
////			foreignLanguageData.setEnglishText("1");
////			foreignLanguageData.setHindiText("3");
////			foreignLanguageData.setTamilText("6");
////			foreignLanguageData.setTeluguText("7");
////			
////			CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", 
////					config, broadcaster, print_writers, foreignLanguageData);
////			
////			foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////					"MATCH SUMMARY", "", null, 0, 0, foreignLanguageDataList);
////			CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tSummaryHeader SET ", config, broadcaster, print_writers, foreignLanguageData);
////			
////			for(Dictionary dicti : dict) {
////				
////				if(match.getTournament().toUpperCase().equalsIgnoreCase(dicti.getEnglishSentence())) {
////					foreignLanguageData.setEnglishText(dicti.getEnglishSentence());
////					foreignLanguageData.setHindiText(dicti.getHindiSentence());
////					foreignLanguageData.setTamilText(dicti.getTamilSentence());
////					foreignLanguageData.setTeluguText(dicti.getTeluguSentence());
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tEventName SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}else {
////					foreignLanguageData.setEnglishText("");
////					foreignLanguageData.setHindiText("");
////					foreignLanguageData.setTamilText("");
////					foreignLanguageData.setTeluguText("");
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tEventName SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}
////				
////				if(match.getMatchIdent().toUpperCase().equalsIgnoreCase(dicti.getEnglishSentence())) {
////					foreignLanguageData.setEnglishText(dicti.getEnglishSentence());
////					foreignLanguageData.setHindiText(dicti.getHindiSentence());
////					foreignLanguageData.setTamilText(dicti.getTamilSentence());
////					foreignLanguageData.setTeluguText(dicti.getTeluguSentence());
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tSumMatchId SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}else {
////					foreignLanguageData.setEnglishText("");
////					foreignLanguageData.setHindiText("");
////					foreignLanguageData.setTamilText("");
////					foreignLanguageData.setTeluguText("");
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tSumMatchId SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}
////			}
////			for(Inning inn : match.getInning()) {
////				if(inn.getInningStatus() != null) {
////					total_inn = total_inn + 1;
////				}
////			}
////			
////			if(total_inn > 0 && whichInning > total_inn) {
////				whichInning = total_inn;
////			}
////			
////			if(whichInning == 1) {
////				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$DataAll*FUNCTION*Omo*vis_con SET 1", print_writers);
////			}else if(whichInning == 2) {
////				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$DataAll*FUNCTION*Omo*vis_con SET 2", print_writers);
////			}else if(whichInning == 3) {
////				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$DataAll*FUNCTION*Omo*vis_con SET 3", print_writers);
////			}else if(whichInning == 4) {
////				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$DataAll*FUNCTION*Omo*vis_con SET 4", print_writers);
////			}
////			
////			for(int i = 1; i <= 4 ; i++) {
////				if(i == whichInning) {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 1", print_writers);
////				} else {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 0", print_writers);
////				}
////			}
////			
////			for(int i = 1; i <= whichInning ; i++) {
////
////				if(i == 1) {
////					row = 0;
////					row_id = 0;
////					max_Strap = 5;
////				} else {
////					row_id = 5;
////					max_Strap = 10;
////				}
////				row_id = row_id + 1;
////				row = row + 1;
////				
////				//Toss
////				if(match.getTossWinningTeam() == match.getInning().get(i-1).getBattingTeamId()) {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$DataAll$" + whichInning + "Innings$Row" + row_id + "$Toss*FUNCTION*Omo*vis_con SET 1", print_writers);
////				}
////				else {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$DataAll$" + whichInning + "Innings$Row" + row_id + "$Toss*FUNCTION*Omo*vis_con SET 0", print_writers);
////				}
////				
////				if(match.getInning().get(i-1).getBattingTeamId() == match.getHomeTeamId()) {
////					teamname = match.getHomeTeam().getTeamName1();	
////				} else {
////					teamname = match.getAwayTeam().getTeamName1();
////				}
////				
////				foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata, 
////						teamname, "", null, 0, 0, foreignLanguageDataList);
////				CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tSumTeamName" + row + " SET ", config, broadcaster, print_writers, foreignLanguageData);
////				
//////				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$TeamName1*GEOM*TEXT SET " + teamname, print_writers);
//////				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$Innings1*GEOM*TEXT SET " +
//////						"Over " + CricketFunctions.OverBalls(match.getInning().get(i-1).getTotalOvers(),match.getInning().get(i-1).getTotalBalls()), print_writers);
////				
////				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInnings" + row + " SET " +
////					  CricketFunctions.OverBalls(match.getInning().get(i-1).getTotalOvers(),match.getInning().get(i-1).getTotalBalls()), print_writers);
////				System.out.println("i = " + i);
////				System.out.println("overs = " + CricketFunctions.OverBalls(match.getInning().get(i-1).getTotalOvers(),match.getInning().get(i-1).getTotalBalls()));
////				if(match.getInning().get(i-1).getTotalWickets() >= 10) {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$TotalGrp$TotalScore*GEOM*TEXT SET " + match.getInning().get(i-1).getTotalRuns(), print_writers);
////				}
////				else {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$TotalGrp$TotalScore*GEOM*TEXT SET " + 
////							match.getInning().get(i-1).getTotalRuns() + slashOrDash + String.valueOf(match.getInning().get(i-1).getTotalWickets()), print_writers);	
////				}
////				if(match.getInning().get(i-1).getBattingCard() != null) {
////					
////					Collections.sort(match.getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
////					for(BattingCard bc : match.getInning().get(i-1).getBattingCard()) {
////						if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
////							row_id = row_id + 1;
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$LeftPlayer*ACTIVE SET 1", print_writers);
////							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////										bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tSumBatsmanName" + (row_id - 1) + " SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
//////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$LeftPlayer$LeftText$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "*", print_writers);
////							} else {
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////										bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tSumBatsmanName" + (row_id - 1) + " SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
//////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$LeftPlayer$LeftText$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname(), print_writers);
////							}
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$LeftPlayer$LeftText$PlayerRuns*GEOM*TEXT SET " + bc.getRuns(), print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$LeftPlayer$LeftText$PlayerBalls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()), print_writers);
////							
////							if(i == 1 && row_id >= 5) {
////								break;
////							}else if(i == 2 && row_id >= 10) {
////								break;
////							}
////						}
////					}
////				}
////
////				for(int j = row_id + 1; j <= max_Strap; j++) {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row"+ j + "$LeftPlayer*ACTIVE SET 0", print_writers);
////				}
////				
////				if(i == 1) {
////					row_id = 1;
////				}
////				else {
////					row_id = 6;
////				}
////
////				if(match.getInning().get(i-1).getBowlingCard() != null) {
////					
////					Collections.sort(match.getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
////
////					for(BowlingCard boc : match.getInning().get(i-1).getBowlingCard()) {
////						
////						row_id = row_id + 1;
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer*ACTIVE SET 1", print_writers);
////						
////						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////								boc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tSumBowlerName" + (row_id - 1) + " SET ", config, broadcaster, print_writers, foreignLanguageData);
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer$RightText$PlayerFigures*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns(), print_writers);
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer$RightText$PlayerBalls*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()), print_writers);
////						
////						if(i == 1 && row_id >= 5) {
////							break;
////						}
////						else if(i == 2 && row_id >= 10) {
////							break;
////						}
////					}
////				}
////				
////				for(int j = row_id + 1; j <= max_Strap; j++) {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + j + "$RightPlayer*ACTIVE SET 0", print_writers);
////				}
////			}
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$SummaryData$BottomInfoPosition$InfoTextAll$InfoText*GEOM*TEXT SET " + CricketFunctions.generateMatchSummaryStatus(whichInning, match, "FULL"), print_writers);
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/matchsummary.png In 1.400 SummaryIn 2.400", print_writers);
////
//////			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
//////			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
////
////			this.status = "SUCCESS";	
////		
////		}
////	}
//	public void populateScorecard(List<PrintWriter> print_writers, String viz_scene, int whichInning, MatchAllData match, String broadcaster,List<Dictionary>dict,Configuration config,
//			MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList,int whichside) throws InterruptedException 
//	{
//		if (match == null) {
//			System.out.println("ERROR: populateScorecard -> Match is null");
//		} else if (match.getMatch().getInning() == null) {
//			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
//		} else {
//
//			CricketFunctions.generateMatchResultForeignLanguage(match,CricketUtil.TEAMNAME_4, multilanguagedata);
//			
//			foreignLanguageData = new ArrayList<ForeignLanguageData>();
//			
//			String which_language = "";
//			
//			if(whichside == 1) {
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "*FUNCTION*Omo*vis_con SET 1", print_writers,config);
//			}else {
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "*FUNCTION*Omo*vis_con SET 1", print_writers,config);
//			}
//			
//			for(int i = 0; i < print_writers.size(); i++) {
//
//				switch (i) {
//				case 0:
//					which_language = config.getPrimaryLanguage();
//					break;
//				case 1:
//					which_language = config.getSecondaryLanguage();
//					break;
//				case 2:
//					which_language = config.getTertiaryLanguage();
//					break;
//				}
//				
//				
//				if(which_language.equalsIgnoreCase("ENGLISH")) {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$HeaderText*FUNCTION*Omo*vis_con SET 0", print_writers,config);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$SubHeadGrp*FUNCTION*Omo*vis_con SET 0", print_writers,config);
//				}else if(which_language.equalsIgnoreCase("HINDI")) {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$HeaderText*FUNCTION*Omo*vis_con SET 1", print_writers,config);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$SubHeadGrp*FUNCTION*Omo*vis_con SET 1", print_writers,config);
//				}else if(which_language.equalsIgnoreCase("TAMIL")) {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$HeaderText*FUNCTION*Omo*vis_con SET 2", print_writers,config);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$SubHeadGrp*FUNCTION*Omo*vis_con SET 2", print_writers,config);
//				}
//				
//				foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", multilanguagedata, 
//						match.getHomeTeam().getTeamName1(), "", null, 0, foreignLanguageDataList);
//				CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$HeaderText$English$txt_Header*GEOM*TEXT SET ", config, broadcaster, print_writers, foreignLanguageData);
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$HeaderText$English$txt_Header*ACTIVE SET 1", print_writers,config);
//				
//				foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", multilanguagedata, 
//						match.getAwayTeam().getTeamName1(), "", null, 0, foreignLanguageDataList);
//				CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$SubHeadGrp$English$txt_SubHead*GEOM*TEXT SET ", config, broadcaster, print_writers, foreignLanguageData);
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$SubHeadGrp$English$txt_SubHead*ACTIVE SET 1", print_writers,config);
//			}
//	}
//}
//	public void populateBowlingcard(List<PrintWriter> print_writers, String viz_scene, int whichInning, MatchAllData match, String broadcaster,List<Dictionary>dict,Configuration config,
//			MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList,int whichside) throws InterruptedException 
//	{
//		if (match == null) {
//			System.out.println("ERROR: populateScorecard -> Match is null");
//		} else if (match.getMatch().getInning() == null) {
//			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
//		} else {
//
//			int row_id = 0, omo_num = 0;
//			CricketFunctions.generateMatchResultForeignLanguage(match,CricketUtil.TEAMNAME_4, multilanguagedata);
//			
//			foreignLanguageData = new ArrayList<ForeignLanguageData>();
//			
//			String which_language = "";
//			
//			if(whichside == 1) {
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "*FUNCTION*Omo*vis_con SET 1", print_writers,config);
//			}else {
//				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "*FUNCTION*Omo*vis_con SET 1", print_writers,config);
//			}
//			
//			for(int i = 0; i < print_writers.size(); i++) {
//
//				switch (i) {
//				case 0:
//					which_language = config.getPrimaryLanguage();
//					break;
//				case 1:
//					which_language = config.getSecondaryLanguage();
//					break;
//				case 2:
//					which_language = config.getTertiaryLanguage();
//					break;
//				}
//				
//				
//				if(which_language.equalsIgnoreCase("ENGLISH")) {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$HeaderText*FUNCTION*Omo*vis_con SET 0", print_writers,config);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$SubHeadGrp*FUNCTION*Omo*vis_con SET 0", print_writers,config);
//				}else if(which_language.equalsIgnoreCase("HINDI")) {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$HeaderText*FUNCTION*Omo*vis_con SET 1", print_writers,config);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$SubHeadGrp*FUNCTION*Omo*vis_con SET 1", print_writers,config);
//				}else if(which_language.equalsIgnoreCase("TAMIL")) {
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$HeaderText*FUNCTION*Omo*vis_con SET 2", print_writers,config);
//					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$SubHeadGrp*FUNCTION*Omo*vis_con SET 2", print_writers,config);
//				}
//				
//				foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", multilanguagedata, 
//						match.getHomeTeam().getTeamName1(), "", null, 0, foreignLanguageDataList);
//				CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$HeaderText$txt_Header*GEOM*TEXT SET ", config, broadcaster, print_writers, foreignLanguageData);
//				
//				foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", multilanguagedata, 
//						match.getAwayTeam().getTeamName1(), "", null, 0, foreignLanguageDataList);
//				CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main$All$Side" + whichside + "$DoubleMatchId$AllData$SubHeadGrp$txt_SubHead*GEOM*TEXT SET ", config, broadcaster, print_writers, foreignLanguageData);
//				
//			}
//	}
//}
////	public void populatePartnership(List<PrintWriter> print_writers, int whichInning, Match match, String viz_scene_path,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList,List<Team>team,List<Dictionary>dict) 
////	{
////		if (match == null) {
////			this.status = "ERROR: Match is null";
////		} else if (match.getInning() == null) {
////			this.status = "ERROR: Partnership's inning is null";
////		} else {
////			
////			int row_id = 0, omo_num = 0,Top_Score = 50;
////			float Mult = 21, ScaleFac1 = 0, ScaleFac2 = 0;
////			String cont_name= "",Left_Batsman = "",Right_Batsman="";
////
////			foreignLanguageData = new ForeignLanguageData();
////			
////			foreignLanguageData.setEnglishText("1");
////			foreignLanguageData.setHindiText("3");
////			foreignLanguageData.setTamilText("6");
////			foreignLanguageData.setTeluguText("7");
////			
////			CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", 
////					config, broadcaster, print_writers, foreignLanguageData);
////			
////			
////			for(Dictionary dicti : dict) {
////				
////				if(match.getTournament().toUpperCase().equalsIgnoreCase(dicti.getEnglishSentence())) {
////					foreignLanguageData.setEnglishText(dicti.getEnglishSentence());
////					foreignLanguageData.setHindiText(dicti.getHindiSentence());
////					foreignLanguageData.setTamilText(dicti.getTamilSentence());
////					foreignLanguageData.setTeluguText(dicti.getTeluguSentence());
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tEventName SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}else {
////					foreignLanguageData.setEnglishText("");
////					foreignLanguageData.setHindiText("");
////					foreignLanguageData.setTamilText("");
////					foreignLanguageData.setTeluguText("");
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tEventName SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}
////				
////				if(match.getMatchIdent().toUpperCase().equalsIgnoreCase(dicti.getEnglishSentence())) {
////					foreignLanguageData.setEnglishText(dicti.getEnglishSentence());
////					foreignLanguageData.setHindiText(dicti.getHindiSentence());
////					foreignLanguageData.setTamilText(dicti.getTamilSentence());
////					foreignLanguageData.setTeluguText(dicti.getTeluguSentence());
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartMatchId SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}else {
////					foreignLanguageData.setEnglishText("");
////					foreignLanguageData.setHindiText("");
////					foreignLanguageData.setTamilText("");
////					foreignLanguageData.setTeluguText("");
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartMatchId SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}
////			}
////
////			for(Inning inn : match.getInning()) {
////				if (inn.getInningNumber() == whichInning) {
////
////					if (inn.getBattingTeamId() == match.getHomeTeamId()) {
////						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata, 
////								match.getHomeTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
////						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartBattingTeamName SET ", config, broadcaster, print_writers, foreignLanguageData);
////						
////						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata, 
////								match.getAwayTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
////						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartBowlingTeamName SET ", config, broadcaster, print_writers, foreignLanguageData);
////					} else {
////						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata, 
////								match.getHomeTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
////						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartBowlingTeamName SET ", config, broadcaster, print_writers, foreignLanguageData);
////						
////						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata, 
////								match.getAwayTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
////						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartBattingTeamName SET ", config, broadcaster, print_writers, foreignLanguageData);
////					}
////					
////					for(int a = 1; a <= inn.getPartnerships().size(); a++){
////						ScaleFac1=0;ScaleFac2=0;
////						
////						if(inn.getPartnerships().get(a-1).getFirstBatterRuns() > Top_Score) {
////							Top_Score = inn.getPartnerships().get(a-1).getFirstBatterRuns();
////						}
////						
////						if(inn.getPartnerships().get(a-1).getSecondBatterRuns() > Top_Score) {
////							Top_Score = inn.getPartnerships().get(a-1).getSecondBatterRuns();
////						}
////						
////					}
////
////					for (Partnership ps : inn.getPartnerships()) {
////						
////						row_id = row_id + 1;
////						Left_Batsman ="" ; Right_Batsman="";
////						for (BattingCard bc : inn.getBattingCard()) {
////							if(bc.getPlayerId() == ps.getFirstBatterNo()) {
////								Left_Batsman = bc.getPlayer().getFull_name();
////							}
////							else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
////								Right_Batsman = bc.getPlayer().getFull_name();
////							}
////						}
////						
////						if(inn.getPartnerships().size() >= 10) {
////							if(ps.getPartnershipNumber()<=inn.getPartnerships().size()) {
////								omo_num = 4;
////								cont_name = "Highlight";
////							}
////						}
////						else {
////							if(ps.getPartnershipNumber()<inn.getPartnerships().size()) {
////								omo_num = 4;
////								cont_name = "Highlight";
////							}
////							else if(ps.getPartnershipNumber() >= inn.getPartnerships().size()) {
////								omo_num = 3;
////								cont_name = "Dehighlight";
////							}
////						}
////						
////						
////						ScaleFac1 = ((ps.getFirstBatterRuns())*(Mult/Top_Score)) ;
////						ScaleFac2 = ((ps.getSecondBatterRuns())*(Mult/Top_Score)) ;
////
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num), print_writers);
////
////						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////								Left_Batsman, "", null, 0, 0, foreignLanguageDataList);
////						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tLeftPlayerName" + row_id + " SET ", config, broadcaster, print_writers, foreignLanguageData);
////						
////						foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////								Right_Batsman, "", null, 0, 0, foreignLanguageDataList);
////						CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tRightPlayerName" + row_id + " SET ", config, broadcaster, print_writers, foreignLanguageData);
////						
//////						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$LeftPlayeName*GEOM*TEXT SET " + Left_Batsman + "\0");
//////						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$RightPlayeName*GEOM*TEXT SET " + Right_Batsman + "\0");
////						
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$" + cont_name + "$BarGrp*FUNCTION*BarValues*Bar_Value__1 SET " + ScaleFac1, print_writers);
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$" + cont_name + "$BarGrp*FUNCTION*BarValues*Bar_Value__2 SET " + ScaleFac2, print_writers);
////						
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$" + cont_name + "$ScoreGrp$PlayerRuns*GEOM*TEXT SET " + ps.getTotalRuns(), print_writers);
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$" + cont_name + "$ScoreGrp$PlayerBalls*GEOM*TEXT SET " + ps.getTotalBalls(), print_writers);
////
////					}
////					if(inn.getPartnerships().size() >= 10) {
////						row_id = row_id + 1; 
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET 0", print_writers);
////					}
////					else {
////						for (BattingCard bc : inn.getBattingCard()) {
////							if(row_id < 11) {
////								if(row_id == inn.getPartnerships().size()) {
////									row_id = row_id + 1;
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET 1", print_writers);
////								}
////								else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
////									row_id = row_id + 1;
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET 2", print_writers);
////									
////									foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////											bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////									CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tLeftPlayerName" + row_id + " SET ", config, broadcaster, print_writers, foreignLanguageData);
////									
//////									print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$StiilToBatPlayerGrp$LeftPlayeName*GEOM*TEXT SET " + bc.getPlayer().getSurname()+" \0");
////								}	
////							}
////							else break;
////						}
////					}
////					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////							"STILL TO BAT", "", null, 0, 0, foreignLanguageDataList);
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStIllToBatHead SET ", config, broadcaster, print_writers, foreignLanguageData);
////					
////					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////							"EXTRAS", "", null, 0, 0, foreignLanguageDataList);
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartExtrasHead SET ", config, broadcaster, print_writers, foreignLanguageData);
////					
////					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////							"OVERS", "", null, 0, 0, foreignLanguageDataList);
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartOversHead SET ", config, broadcaster, print_writers, foreignLanguageData);
////					
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartExtrasValue SET " + inn.getTotalExtras(), print_writers);
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartOversValue SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()), print_writers);
////					if(inn.getTotalWickets() >= 10) {
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartTotalScore SET " + inn.getTotalRuns(), print_writers);
////					}
////					else {
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPartTotalScore SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()), print_writers);
////					}
////				}
////			}
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/partnership.png In 1.400 PartnershipIn 2.800", print_writers);
////
//////			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
//////			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipIn START \0");
////			this.status = "SUCCESS";
////		}
////	}
////	public void populateMatchId(List<PrintWriter> print_writers,String viz_scene, Match match,List<Team>team,List<Venue>vnu,List<Dictionary>dict, String broadcaster,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList)
////	{
////		if (match == null) {
////			this.status = "ERROR: Match is null";
////		} else if (match.getInning() == null) {
////			this.status = "ERROR: MatchId's inning is null";
////		} else {
////			foreignLanguageData = new ForeignLanguageData();
////			
////			foreignLanguageData.setEnglishText("1");
////			foreignLanguageData.setHindiText("3");
////			foreignLanguageData.setTamilText("6");
////			foreignLanguageData.setTeluguText("7");
////			
////			CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", 
////					config, broadcaster, print_writers, foreignLanguageData);
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgTeamLogo1 SET IMAGE*/Default/DOAD_In_House/" 
////					+ match.getHomeTeam().getTeamName4().toUpperCase(), print_writers);
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgTeamLogo2 SET IMAGE*/Default/DOAD_In_House/" 
////					+ match.getAwayTeam().getTeamName4().toUpperCase(), print_writers);
////
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgTeamLogo1a SET IMAGE*/Default/DOAD_In_House/" 
////					+ match.getHomeTeam().getTeamName4().toUpperCase(), print_writers);
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgTeamLogo2a SET IMAGE*/Default/DOAD_In_House/" 
////					+ match.getAwayTeam().getTeamName4().toUpperCase(), print_writers);
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$TopPart$SubHeaderGrp*ACTIVE SET 1", print_writers);
////			for(Dictionary dicti : dict) {
////				
////				if(match.getTournament().toUpperCase().equalsIgnoreCase(dicti.getEnglishSentence())) {
////					foreignLanguageData.setEnglishText(dicti.getEnglishSentence());
////					foreignLanguageData.setHindiText(dicti.getHindiSentence());
////					foreignLanguageData.setTamilText(dicti.getTamilSentence());
////					foreignLanguageData.setTeluguText(dicti.getTeluguSentence());
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tEventName SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}else {
////					foreignLanguageData.setEnglishText("");
////					foreignLanguageData.setHindiText("");
////					foreignLanguageData.setTamilText("");
////					foreignLanguageData.setTeluguText("");
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tEventName SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}
////			}
////
////			for(Team tm : team) {
////				if(match.getHomeTeamId() == tm.getTeamId()) {
////					
////					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata, 
////							match.getHomeTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tTeamName1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////				}
////				
////				if(match.getAwayTeamId() == tm.getTeamId()) {
////					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.TEAM, "", config, multilanguagedata,
////							match.getAwayTeam().getTeamName1(), "", null, 0, 0, foreignLanguageDataList);
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz(" RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tTeamName2 SET ", config, broadcaster, print_writers, foreignLanguageData);
////				}
////			}
////			for(Venue ven : vnu) {
////				
////				if(match.getVenueName().equalsIgnoreCase(ven.getVenueEnglishText())) {
////					foreignLanguageData.setEnglishText(ven.getVenueEnglishText());
////					foreignLanguageData.setHindiText(ven.getVenueHindiText());
////					foreignLanguageData.setTamilText(ven.getVenueTamilText());
////					foreignLanguageData.setTeluguText(ven.getVenueTeluguText());
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInfo SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}else {
////					foreignLanguageData.setEnglishText("");
////					foreignLanguageData.setHindiText("");
////					foreignLanguageData.setTamilText("");
////					foreignLanguageData.setTeluguText("");
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInfo SET ", config, broadcaster, print_writers,foreignLanguageData);
////				}
////			}
////		}
////		
////	}
////	public Infobar populateInfobar(Infobar infobar, List<PrintWriter> print_writers,String scene, Match match, String broadcaster,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList) throws InterruptedException 
////	{
////		if (match == null) {
////			this.status = "ERROR: Match is null";
////		} else if (match.getInning() == null) {
////			this.status = "ERROR: Infobar's inning is null";
////		} else {
////			
////			infobar = populateInfobarTeamScore(infobar,false, print_writers, match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
////			infobar = populateVizInfobarMiddle(infobar, false, print_writers, match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
////			infobar = populateVizInfobarRightTop(infobar, false, print_writers, match, broadcaster,config,multilanguagedata,foreignLanguageDataList);
////			//infobar = populateVizInfobarRightBottom(infobar, false, print_writers, match, broadcaster);
////		}
////		return infobar;
////	}
////	public Infobar populateInfobarTeamScore(Infobar infobar, boolean is_this_updating, List<PrintWriter> print_writers, Match match, String broadcaster, Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList) throws InterruptedException
////	{
////	    CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + " " + "",print_writers);
////    	
////		for(Inning inn : match.getInning()) {
////			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
////				if(is_this_updating == false) {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgSbTeamLogo1A SET " + "IMAGE*/Default/DOAD_In_House/" + inn.getBatting_team().getTeamName4().toUpperCase(), print_writers);
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgSbTeamLogo2A SET " + "IMAGE*/Default/DOAD_In_House/" + inn.getBowling_team().getTeamName4().toUpperCase(),  print_writers);
////					
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgSbTeamLogo1 SET " + "IMAGE*/Default/DOAD_In_House/" + inn.getBatting_team().getTeamName4().toUpperCase(), print_writers);
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgSbTeamLogo2 SET " + "IMAGE*/Default/DOAD_In_House/" + inn.getBowling_team().getTeamName4().toUpperCase(),  print_writers);
////					
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatTeamName SET " + inn.getBatting_team().getTeamName4().toUpperCase(), print_writers);
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBallTeamName SET " + inn.getBowling_team().getTeamName4().toUpperCase(), print_writers);
////					
////					foreignLanguageData = new ForeignLanguageData();
////					
////					foreignLanguageData.setEnglishText("1");
////					foreignLanguageData.setHindiText("3");
////					foreignLanguageData.setTamilText("6");
////					foreignLanguageData.setTeluguText("7");
////					
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", config, broadcaster, print_writers, foreignLanguageData);
////					
////				    foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////							"1st INNINGS", "", null, 0, 0, foreignLanguageDataList);
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tFreeText1A SET ", config, 
////							broadcaster, print_writers, foreignLanguageData);
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*AllSections$Section1All$Sec1FreeText1A_In START",print_writers);
////					TimeUnit.SECONDS.sleep(1);
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tFreeText6A SET CRR " + inn.getRunRate(), print_writers);
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*STAGE*DIRECTOR*AllSections$Section6All$Sec6FreeText6A_In START",print_writers);
////					TimeUnit.SECONDS.sleep(1);
////				}
////				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tFreeText6A SET CRR " + inn.getRunRate(), print_writers);
////				if(inn.getTotalWickets() >= 10) {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tScore SET " + inn.getTotalRuns(), print_writers);
////				}
////				else{
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tScore SET " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets(), print_writers);
////				}
////				CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tOvers SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()), print_writers);
////			    
////			    if(match.getTargetType().equalsIgnoreCase(CricketUtil.DLS) || match.getTargetType().equalsIgnoreCase(CricketUtil.VJD)) {
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tDlOvers SET " 
////							+ "(" + match.getTargetOvers() + ") " + match.getTargetType().toUpperCase(), print_writers);
////			    }else {
////			    	CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tDlOvers SET " 
////							+ "", print_writers);
////			    }
////			}
////		}
////			
////		return infobar;
////	}
////	
////	public Infobar populateVizInfobarMiddle(Infobar infobar, boolean is_this_updating, List<PrintWriter> print_writers,Match match, String broadcaster,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList)
////	{ 
////		List<BattingCard> current_batsmen = new ArrayList<BattingCard>();
////		switch (infobar.getMiddle_section().toUpperCase()) {
////		case CricketUtil.BATSMAN:
////			for(Inning inn : match.getInning()) {
////				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
////					for (BattingCard bc : inn.getBattingCard()) {
////						if(inn.getPartnerships() != null && inn.getPartnerships().size() > 0) {
////							if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
////								current_batsmen.add(bc);
////							} else if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
////								current_batsmen.add(bc);
////							}
////						}
////					}
////					
////					populateCurrentBatsmen(infobar,print_writers, match, broadcaster,current_batsmen,config,multilanguagedata,foreignLanguageDataList);
////					
////					if(current_batsmen != null && current_batsmen.size() >= 1) {
////						infobar.setLast_batsmen(current_batsmen);
////					}
////				}
////			}
////			break;
////		}
////		return infobar;
////	}
////	public Infobar populateCurrentBatsmen(Infobar infobar, List<PrintWriter> print_writers, Match match, String broadcaster,List<BattingCard> current_batsmen,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList)
////	{
////		foreignLanguageData = new ForeignLanguageData();
////		
////		foreignLanguageData.setEnglishText("1");
////		foreignLanguageData.setHindiText("3");
////		foreignLanguageData.setTamilText("6");
////		foreignLanguageData.setTeluguText("7");
////		
////		CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", config, broadcaster, print_writers, foreignLanguageData);
////		
////		for(Inning inn : match.getInning()) {
////			
////			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
////				
////				if(current_batsmen != null && current_batsmen.size() >= 2) {
////		
////					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////							current_batsmen.get(0).getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////					
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPlayerRuns1 SET " + current_batsmen.get(0).getRuns(), print_writers);
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPlayerBalls1 SET " + current_batsmen.get(0).getBalls(), print_writers);
////
////					foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////							current_batsmen.get(1).getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////					CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName2 SET ", config, broadcaster, print_writers, foreignLanguageData);
////					
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPlayerRuns2 SET " + current_batsmen.get(1).getRuns(), print_writers);
////					CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPlayerBalls2 SET " + current_batsmen.get(1).getBalls(), print_writers);
////					
////					if(current_batsmen.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
////						if(current_batsmen.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main$All$ScoreBugAll$TopLine$TopDataGrp$Section1nad2$Section2All$BatsmanGrp$BatsmanAll$BatsmanGrp1$StrikerMark*FUNCTION*Omo*vis_con SET 1", print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main$All$ScoreBugAll$TopLine$TopDataGrp$Section1nad2$Section2All$BatsmanGrp$BatsmanAll$BatsmanGrp2$StrikerMark*FUNCTION*Omo*vis_con SET 0", print_writers);
////						}
////					}
////					if(current_batsmen.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
////						if(current_batsmen.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main$All$ScoreBugAll$TopLine$TopDataGrp$Section1nad2$Section2All$BatsmanGrp$BatsmanAll$BatsmanGrp1$StrikerMark*FUNCTION*Omo*vis_con SET 0", print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main$All$ScoreBugAll$TopLine$TopDataGrp$Section1nad2$Section2All$BatsmanGrp$BatsmanAll$BatsmanGrp2$StrikerMark*FUNCTION*Omo*vis_con SET 1", print_writers);
////						}	
////					}
////					if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanOut1 SET 50", print_writers);
////					} else if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanOut1 SET 100", print_writers);
////					}
////					if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanOut2 SET 50", print_writers);
////					} else if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
////						CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanOut2 SET 100", print_writers);
////					}
////				}
////			}
////		}
////		infobar.setLast_batsmen(current_batsmen);
////		return infobar;
////	}
////	public Infobar populateVizInfobarRightTop(Infobar infobar,boolean is_this_updating, List<PrintWriter> print_writers, 
////			Match match, String broadcaster,Configuration config,MultiLanguageDatabase multilanguagedata,
////			List<ForeignLanguageData> foreignLanguageDataList) throws InterruptedException
////	{
////		switch(infobar.getBottom_right_top_section().toUpperCase()) {
////		case CricketUtil.BOWLER:
////			//CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BowlerbaseGrp$TopLIne*ACTIVE SET " + "0" + "");
////			for(Inning inn : match.getInning()) {
////				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
////					for(BowlingCard boc : inn.getBowlingCard()) {
////						if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") 
////								|| boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
//////							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
//////								processAnimation(print_writers, "ALL_SECTION$Section4In", "CONTINUE", broadcaster);
//////								processAnimation(print_writers, "ALL_SECTION$Section5In", "CONTINUE", broadcaster);
//////								TimeUnit.SECONDS.sleep(1);
//////							}
////							foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////									boc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////							CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBowlerName SET ", config, broadcaster, print_writers, foreignLanguageData);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPlayerFigures SET " + boc.getWickets() + slashOrDash + boc.getRuns(), print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPlayerOvers SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()), print_writers);
////
//////							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
//////								processAnimation(print_writers, "ALL_SECTION$Section4In", "START", broadcaster);
//////								processAnimation(print_writers, "ALL_SECTION$Section5In", "START", broadcaster);
//////							}
////							infobar.setLast_bowler(boc);
////							infobar.setLast_bottom_right_top_section(CricketUtil.BOWLER);
////						}
////					}
////				}
////			}
////			break;	
////		}
////		return infobar;
////		}
////	
////	public void populateLtBatsmanThisMatch(List<PrintWriter> print_writers, String viz_scene,int whichInning, int PlayerId, 
////			Match match, String session_selected_broadcaster, Configuration config, MultiLanguageDatabase multilanguagedata,
////			List<ForeignLanguageData> foreignLanguageDataList) 
////	{
////
////		if (match == null) {
////			this.status = "ERROR: Match is null";
////		} else if (match.getInning() == null) {
////			this.status = "ERROR: PlayerSummary's inning is null";
////		} else {
////			
////			List<String> str = new ArrayList<String>();
////			for(Inning inn : match.getInning()) {
////				for(BattingCard bc : inn.getBattingCard()) {
////					if (inn.getInningNumber() == whichInning) {
////						//print_writers.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
////						if(PlayerId == bc.getPlayerId()) {
////							//CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "");								
////							//CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "");								
////
////							foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////									bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////							CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////							
////							//CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "");								
////							//CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + " " + "");								
////							/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
////								print_writers.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
////							} else {
////								print_writers.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
////							}*/
////							str.add("1");
////							foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////									bc.getPlayer().getFull_name(), "", str, 0, 0, foreignLanguageDataList);
////							CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////							
////							
////							foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////									bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////							CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////							
////							
////							foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////									bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////							CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////							
////							
////							foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////									bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////							CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////							
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead02*GEOM*TEXT SET " + "RUNS" + "",print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead03*GEOM*TEXT SET " + bc.getRuns() + "",print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + "BALLS" + "",print_writers);								
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1c*GEOM*TEXT SET " + bc.getBalls() + "",print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + "S/R" + "",print_writers);								
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + bc.getStrikeRate() + "",print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + "FOURS" + "",print_writers);								
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + bc.getFours() + "",print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " + "SIXES" + "",print_writers);								
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " + bc.getSixes() + "",print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue5$StatValue1B*GEOM*TEXT SET " + " " + "",print_writers);
////							CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue5$StatValue1B*GEOM*TEXT SET " + " " + "",print_writers);
////						}
////					}
////				}
////			}
////		}
////	}
////	public void populateBug(List<PrintWriter> print_writers,String viz_scene, int whichInning, String statsType, int playerId, Match match, String broadcaster,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList) throws InterruptedException
////	{
////		if (match == null) {
////			System.out.println("ERROR: populateBug -> Match is null");
////		} else if (match.getInning() == null) {
////			System.out.println("ERROR: populateBug -> inning is null");
////		} else {
////			
////			foreignLanguageData = new ForeignLanguageData();
////			
////			foreignLanguageData.setEnglishText("1");
////			foreignLanguageData.setHindiText("3");
////			foreignLanguageData.setTamilText("6");
////			foreignLanguageData.setTeluguText("7");
////			
////			CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", config, broadcaster, print_writers, foreignLanguageData);
////			
////			for(Inning inn : match.getInning()) {
////				if (inn.getInningNumber() == whichInning) {
////					switch(statsType.toUpperCase()) {
////					case CricketUtil.BATSMAN :
////						for (BattingCard bc : inn.getBattingCard()) {
////							if(bc.getPlayerId()==playerId) {
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + " " + "",print_writers);
////								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInfo3 SET " + bc.getRuns() +"* "+ "(" + bc.getBalls() + ")", print_writers);
////								}
////								else {
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInfo3 SET " + bc.getRuns() + " (" + bc.getBalls() + ")", print_writers);
////								}
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////										bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInfo1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
//////								if(bc.getPlayer().getSurname() != null) {
//////									foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
//////											bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
//////									CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName1 SET ", config, broadcaster, print_writers, foreignLanguageData);
//////									
//////									//CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "");
//////									//CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getSurname().toUpperCase() + "");
//////								}else {
//////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + "" + "");
//////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getFirstname().toUpperCase() + "");
//////								}
////								
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInfo2 SET " + "4s : " + bc.getFours() + " 6s : " + bc.getSixes(), print_writers);
////							}
////						}
////						break;
////					}
////				}
////			}
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.040 ",print_writers);
////		}
////	}
////	public void populateBugBowler(List<PrintWriter> print_writers,String viz_scene, int whichInning, String statsType, int playerId, Match match, String broadcaster,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList) throws InterruptedException
////	{
////		if (match == null) {
////			this.status = "ERROR: Match is null";
////		} else if (match.getInning() == null) {
////			this.status = "ERROR: Bug's inning is null";
////		} else {
////			foreignLanguageData = new ForeignLanguageData();
////			
////			foreignLanguageData.setEnglishText("1");
////			foreignLanguageData.setHindiText("3");
////			foreignLanguageData.setTamilText("6");
////			foreignLanguageData.setTeluguText("7");
////			
////			CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", config, broadcaster, print_writers, foreignLanguageData);
////
////			for(Inning inn : match.getInning()) {
////				if (inn.getInningNumber() == whichInning) {
////					switch(statsType.toUpperCase()) {
////					case "BOWLER":
////						for (BowlingCard boc : inn.getBowlingCard()) {
////							if(boc.getPlayerId()==playerId) {
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + " " + "",print_writers);
////
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////										boc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInfo1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInfo2 SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()), print_writers);
////								
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tInfo3 SET " + boc.getWickets() + slashOrDash + boc.getRuns(), print_writers);
////								
//////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
//////										"OVERS", "", null, 0, 0, foreignLanguageDataList);
//////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatHead1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////							}
////						}
////						break;
////					}
////				}
////			}
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.040 ",print_writers);				
////		}
////	}
////	public void populateBatsmanstats(List<PrintWriter> print_writers,String viz_scene, int whichInning, String statsType, int playerId, Match match, String broadcaster,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList) throws InterruptedException
////	{
////		if (match == null) {
////			this.status = "ERROR: Match is null";
////		} else if (match.getInning() == null) {
////			this.status = "ERROR: PlayerStats's inning is null";
////		} else {
////			int total_inn = 0;
////			
////			for(Inning inn : match.getInning()) {
////				if(inn.getInningStatus() != null) {
////					total_inn = total_inn + 1;
////				}
////			}
////			
////			if(total_inn > 0 && whichInning > total_inn) {
////				whichInning = total_inn;
////			}
////			
////			foreignLanguageData = new ForeignLanguageData();
////			
////			foreignLanguageData.setEnglishText("1");
////			foreignLanguageData.setHindiText("3");
////			foreignLanguageData.setTamilText("6");
////			foreignLanguageData.setTeluguText("7");
////			
////			CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", config, broadcaster, print_writers, foreignLanguageData);
////
////			for(Inning inn : match.getInning()) {
////				if (inn.getInningNumber() == whichInning) {
////					String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, playerId,",", match.getEvents()).split(",");
////					switch(statsType.toUpperCase()) {
////					case CricketUtil.BATSMAN :
////						for (BattingCard bc : inn.getBattingCard()) {
////							if(bc.getPlayerId()==playerId) {
////								
////								/*
////								 * print_writer.
////								 * println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " +
////								 * "tTeamRefName" + " SET " + logo_path +
////								 * inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
////								 */
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////										bc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPlayerName SET ", config, broadcaster, print_writers, foreignLanguageData);
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgTeamLogo1 SET IMAGE*/Default/DOAD_In_House/" + inn.getBatting_team().getTeamName4().toUpperCase(), print_writers);
////				
////								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tRuns SET " + bc.getRuns() + "*", print_writers);
////								}
////								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tRuns SET " + bc.getRuns(), print_writers);
////								}
////								
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBalls SET " + bc.getBalls(), print_writers);
////
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1 SET " + bc.getFours(), print_writers);
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2 SET " + bc.getSixes(), print_writers);
////								if(bc.getStrikeRate() == null) {
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3 SET " + "-", print_writers);
////								}else {
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3 SET " + bc.getStrikeRate(), print_writers);
////								}
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4 SET " + Count[0], print_writers);
////								
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////										"FOURS", "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatHead1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////										"SIXES", "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatHead2 SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////										"STRIKE RATE", "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatHead3 SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////										"DOTS", "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatHead4 SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
////
////								
////							}
////						}
////						break;
////					}
////				}
////			}
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.500",print_writers);								
////		}	
////	}
////	public void populateBowlerstats(List<PrintWriter> print_writers,String viz_scene, int whichInning, String statsType, int playerId,List<Team> team, Match match, String broadcaster,
////			Configuration config,MultiLanguageDatabase multilanguagedata,List<ForeignLanguageData> foreignLanguageDataList) throws InterruptedException
////	{
////		if (match == null) {
////			this.status = "ERROR: Match is null";
////		} else if (match.getInning() == null) {
////			this.status = "ERROR: PlayerStats's inning is null";
////		} else {
////			int total_inn = 0;
////			
////			for(Inning inn : match.getInning()) {
////				if(inn.getInningStatus() != null) {
////					total_inn = total_inn + 1;
////				}
////			}
////			
////			if(total_inn > 0 && whichInning > total_inn) {
////				whichInning = total_inn;
////			}
////			
////			foreignLanguageData = new ForeignLanguageData();
////			
////			foreignLanguageData.setEnglishText("1");
////			foreignLanguageData.setHindiText("3");
////			foreignLanguageData.setTamilText("6");
////			foreignLanguageData.setTeluguText("7");
////			
////			CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vLanguageSelection SET ", config, broadcaster, print_writers, foreignLanguageData);
////			
////			for(Inning inn : match.getInning()) {
////				if (inn.getInningNumber() == whichInning) {
//////					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
//////							 inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
////
////					switch(statsType.toUpperCase()) {
////					case CricketUtil.BOWLER:
////						for (BowlingCard boc : inn.getBowlingCard()) {
////							if(boc.getPlayerId()==playerId) {
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON lgTeamLogo1 SET IMAGE*/Default/DOAD_In_House/" + inn.getBatting_team().getTeamName4().toUpperCase(), print_writers);
////
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.PLAYER, "", config, multilanguagedata, 
////										boc.getPlayer().getFull_name(), "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tPlayerName SET ", config, broadcaster, print_writers, foreignLanguageData);								
////								
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tRuns SET " + boc.getWickets(), print_writers);
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBalls SET " + boc.getRuns(), print_writers);
////
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1 SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()), print_writers);
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2 SET " + boc.getDots(), print_writers);
////								CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3 SET " + (boc.getNoBalls() + boc.getWides()), print_writers);
////								if(boc.getEconomyRate() == null) {
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4 SET " + "-", print_writers);
////								}else {
////									CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4 SET " + boc.getEconomyRate(), print_writers);
////								}
////								
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////										"OVERS", "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatHead1 SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////										"DOTS", "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatHead2 SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////										"EXTRAS", "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatHead3 SET ", config, broadcaster, print_writers, foreignLanguageData);
////								
////								foreignLanguageData = CricketFunctions.AssembleMultiLanguageData(CricketUtil.DICTIONARY, "", config, multilanguagedata, 
////										"ECONOMY", "", null, 0, 0, foreignLanguageDataList);
////								CricketFunctions.DoadWriteVariousLanguageTextToEachViz("RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatHead4 SET ", config, broadcaster, print_writers, foreignLanguageData);			
////							}
////						}
////						break;
////					}
////				}
////			}
////			CricketFunctions.DoadWriteSameCommandToEachViz("RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.500",print_writers);				
////		}
////	}
//}
//
//	