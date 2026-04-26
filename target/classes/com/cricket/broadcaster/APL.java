package com.cricket.broadcaster;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import com.cricket.containers.BattingCardFF;
import com.cricket.containers.BowlingFF;
import com.cricket.model.ImpactData;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Commentator;
import com.cricket.model.Configuration;
import com.cricket.model.EventFile;
import com.cricket.model.FallOfWicket;
import com.cricket.model.Fixture;
import com.cricket.model.Ground;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.InfobarStats;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.LeagueTeam;
import com.cricket.model.Match;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.OverByOverData;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Playoff;
import com.cricket.model.Pointers;
import com.cricket.model.Performer;
import com.cricket.model.Setup;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APL extends Scene{

	public String broadcaster = "APL"; 
	public String status;
	public String slashOrDash = "-";
	public String logo_path = "IMAGE*/Default/APL/Logos/";
	public String photo_path = "C:\\\\Images\\\\APL\\\\Photos\\\\";
	private String local_photo_path = "\\\\c\\\\Images\\\\APL\\\\Photos\\\\";
	public String icon_path = "IMAGE*/Default/APL/Icons";
	public Infobar infobar = new Infobar(); 
	public BattingCardFF bcf = new BattingCardFF();
	public BowlingFF bocf = new BowlingFF();
	public String which_graphic_on_screen = "", which_director_on_screen = "";
	public boolean lastOverOnScreen = false, powerplay_on_screen = false,isTickerShrinked = false;
	public int over_size = 0;
	
	public APL() {
		super();
	}

	public APL(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Infobar updateInfobar(List<Scene> scenes,List<MatchAllData> tournament_matches, MatchAllData match, PrintWriter print_writer) throws InterruptedException, IOException, CloneNotSupportedException
	{
//		if(infobar.getIdent_section() != null && !infobar.getIdent_section().trim().isEmpty()) {
//			infobar = populateInfobarIdent(infobar,true, scenes.get(0).getScene_path(), 
//					print_writer, match, broadcaster);
//		}else {
//			infobar = populateInfobarTeamScore(infobar,true, print_writer, match, broadcaster);
//			infobar = populateVizInfobarMiddle(infobar, true, print_writer, match, broadcaster);
//			if(infobar.getBottom_right_section() != null && !infobar.getBottom_right_section().trim().isEmpty()) {
//				infobar = populateVizInfobarRight(infobar, true,print_writer,tournament_matches, match, broadcaster);
//				if(CricketFunctions.getCurrentInningCurrentBowler(match) != null) {
//					infobar.setLast_bowler(CricketFunctions.getCurrentInningCurrentBowler(match));
//				}
//			}else {
//				infobar = populateVizInfobarRightTop(infobar, true, print_writer, match, broadcaster);
//				infobar = populateVizInfobarRightBottom(infobar, true, print_writer, match, broadcaster);
//			}
//			if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
//				infobar = populateVizInfobarTop(infobar, true, print_writer, match, broadcaster);
//			}
//		}
		if (CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10
				|| CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
			if(infobar.isInfobar_on_screen() == true) {
				if (infobar.isResult_on_screen() == false) {
					infobar.setIdent_section("RESULT");
//					infobar = populateInfobarIdent(infobar,true, scenes.get(0).getScene_path(), print_writer, match, broadcaster);
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
							+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
											+ match.getSetup().getHomeTeam().getTeamName1() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
											+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
											+ match.getSetup().getAwayTeam().getTeamName1() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + CricketFunctions.
							GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|", broadcaster, false).getTargetOrResult().toUpperCase() + "\0");
					
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-INFOBAR");
					TimeUnit.MILLISECONDS.sleep(200);
					
					AnimateInGraphics(print_writer, "IDENT");
					which_graphic_on_screen = "IDENT";
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setInfobar_on_screen(true);
					infobar.setResult_on_screen(true);
				}
			}
		}else
		if(infobar.isInfobar_on_screen() == true) {
			if(infobar.getIdent_section() != null && !infobar.getIdent_section().trim().isEmpty()) {
				infobar = populateInfobarIdent(infobar,true, scenes.get(0).getScene_path(), 
						print_writer, match, broadcaster);
			}else {
				infobar = populateInfobarTeamScore(infobar,true, print_writer, match, broadcaster);
				infobar = populateVizInfobarMiddle(infobar, true, print_writer, match, broadcaster);
				if(infobar.getBottom_right_section() != null && !infobar.getBottom_right_section().trim().isEmpty()) {
					infobar = populateVizInfobarRight(infobar, true,print_writer,tournament_matches, match, broadcaster);
					if(CricketFunctions.getCurrentInningCurrentBowler(match) != null) {
						infobar.setLast_bowler(CricketFunctions.getCurrentInningCurrentBowler(match));
					}
				}else {
					infobar = populateVizInfobarRightTop(infobar, true, print_writer, match, broadcaster);
					infobar = populateVizInfobarRightBottom(infobar, true, print_writer, match, broadcaster);
				}
				if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
					infobar = populateVizInfobarTop(infobar, true, print_writer, match, broadcaster);
				}
			}
		}
		return infobar;
	}
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,List<Tournament> past_tournament_stats,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics, Configuration config,List<HeadToHeadPlayer> head_to_head) throws InterruptedException, ParseException, JAXBException, IllegalAccessException, InvocationTargetException, IOException, URISyntaxException, CloneNotSupportedException{
		
		switch (whatToProcess) {
		
		
		//scorebug	
		case "ANIMATE-IN-INFOBAR": case "ANIMATE-IN-IDENT": case "ANIMATE-OUT-SECTION2": case "ANIMATE-OUT-SECTION4_N_5":
		case "TICKER_LT_OUT": case "TICKER_LT_IN": case "ANIMATE-SHRINK_IN": case "ANIMATE-SHRINK_OUT":
		case "ANIMATE-OUT": case "ANIMATE-OUT-DIRECTOR": case "CLEAR-ALL": 
		
		//FF
		case "ANIMATE-MINI-BOWLER_VS_ALLBATSMAN": case "ANIMATE-MINI-BATSMAN_VS_ALLBOWLERS": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF":
		case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-FIX_AND_RESULT": case "ANIMATE-IN-PLAYOFFS":
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-TEAM_SQUAD":
		case "ANIMATE-IN-CAPTAINS": case "ANIMATE-IN-TOP_PERFORMER": case "ANIMATE-FF_SUMMARY_GRAPHICS": case "ANIMATE-IN-MOST":
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-PLAYINGXI_SEQUENCE": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-EQUATION":
		case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO":
		case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS":
		case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-SCHEDULE":
		case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FFTHISSERIES_BALL": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-BALL_PERFORMER":
		case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-LANDMARK_BALL":
		
		//LT
		case "ANIMATE-IN-IMPACT": case "ANIMATE-IN-POINTERS": case "ANIMATE-IN-PHASE":
		case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED":
		case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION":
		case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BOWLERSUMMARY":
		case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH": 
		case "ANIMATE-IN-L3MATCH_PROMO": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": 
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-GENERIC": case "ANIMATE-IN-THISSERIES": case "ANIMATE-IN-THISSERIES_BALL":
		case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-LTPLAYERPROFILEBAT":
			
		//Bug
		case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET":
		case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-MULTI_PARTNERSHIP": case "ANIMATE-IN-BUG-TOSS":
		
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET": 
			case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-MULTI_PARTNERSHIP": case "ANIMATE-IN-BUG-TOSS":
			
			case "ANIMATE-IN-FIX_AND_RESULT": case "ANIMATE-IN-PLAYOFFS":
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-FF_STATS":
			case "ANIMATE-IN-CAPTAINS": case "ANIMATE-IN-TOP_PERFORMER": case "ANIMATE-FF_SUMMARY_GRAPHICS": case "ANIMATE-IN-MOST":
			case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-TEAM_SQUAD": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-PLAYINGXI_SEQUENCE": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": 
			case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-LANDMARK_BALL":
			case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": 
			case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM": case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FFTHISSERIES_BALL":
			case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-BAT-PERFORMER": 
			case "ANIMATE-IN-BALL_PERFORMER":
			
			case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-L3MATCHID":
			
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					AnimateInGraphics(print_writer, "FF_IN");
					TimeUnit.MILLISECONDS.sleep(200);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					AnimateInGraphics(print_writer, "FF_IN");
				}
				break;
			case "ANIMATE-SHRINK_IN":
				AnimateInGraphics(print_writer, "LT_IN");
				break;
			case "ANIMATE-SHRINK_OUT":
				AnimateOutGraphics(print_writer, "LT_OUT");
				break;
			case "ANIMATE-IN-IMPACT": case "ANIMATE-IN-PHASE":
			case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS": case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED": 
			case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION":
			case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT":   case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": 
			case "ANIMATE-IN-LTPOWERPLAY": case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH":
			case "ANIMATE-IN-L3MATCH_PROMO": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": 
			case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-GENERIC": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-THISSERIES": case "ANIMATE-IN-THISSERIES_BALL":
			case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-LTPLAYERPROFILEBAT": case "ANIMATE-IN-POINTERS":
			
			case "ANIMATE-MINI-BOWLER_VS_ALLBATSMAN": case "ANIMATE-MINI-BATSMAN_VS_ALLBOWLERS":
			case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF": case "ANIMATE-MINI-BOWLINGCARD": 
//			case "ANIMATE-SHRINK_IN": case "ANIMATE-SHRINK_OUT":
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
						switch(infobar.getLast_top_section().toUpperCase()) {
						case CricketUtil.TOSS:
							processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
							break;
						case "CRR":
							processAnimation(print_writer, "Section2$CurRunRateOut", "START", broadcaster);
							break;
						case "CRR_RRR":
							processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
							break;
						case "FIRST_INNING_SCORE":
							processAnimation(print_writer, "Section2$FistInnScoreOut", "START", broadcaster);
							break;
						case CricketUtil.BOUNDARY:
							processAnimation(print_writer, "Section2$BallsSinceLastBoundaryOut", "START", broadcaster);
							break;
						case "TARGET":
							processAnimation(print_writer, "Section2$DLSTargetOut", "START", broadcaster);
							break;
						case "EXTRAS":
							processAnimation(print_writer, "Section2$ExtrasOut", "START", broadcaster);
							break;
						case "EQUATION":
							processAnimation(print_writer, "Section2$EquationOut", "START", broadcaster);
							break;
						case "PROJECTED":
							processAnimation(print_writer, "Section2$ProjectedOut", "START", broadcaster);
							break;
						case "BOUNDARIES":
							processAnimation(print_writer, "Section2$BoundariesOut", "START", broadcaster);
							break;
						case "PARTNERSHIP":
							processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
							break;
						case "LAST_WICKET":
							processAnimation(print_writer, "Section2$LastWicketOut", "START", broadcaster);
							break;
						case CricketUtil.TIMELINE:
							processAnimation(print_writer, "Section2$TimelineOut", "START", broadcaster);
							break;
						case "STATISTICS":
							processAnimation(print_writer, "Section2$FreeTextSmallOut", "START", broadcaster);
							break;
						}
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
					}
					infobar.setLast_top_section("");infobar.setTop_section("");
					if(!isTickerShrinked) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_In START \0");
//						AnimateInGraphics(print_writer, "LT_IN");
					}
					TimeUnit.MILLISECONDS.sleep(200);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					if(!isTickerShrinked) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_In START \0");
//						AnimateInGraphics(print_writer, "LT_IN");
					}
				}
				break;
			}
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-INFOBAR");
					AnimateInGraphics(print_writer, "IDENT");
					which_graphic_on_screen = "IDENT";
					infobar.setInfobar_on_screen(true);
				}else {
					AnimateInGraphics(print_writer, "IN");
					AnimateInGraphics(print_writer, "IDENT");
					which_graphic_on_screen = "IDENT";
					infobar.setInfobar_on_screen(true);
				}
				
				break;
				
			case "ANIMATE-IN-BALL_PERFORMER":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {	
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
//					print_writer.println("-1 RENDERER*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPartnershipOut START \0");
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					
					//print_writer.println("-1 RENDERER*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					//TimeUnit.MILLISECONDS.sleep(900);
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					bcf.setLast_type("");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					//print_writer.println("-1 RENDERER*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					TimeUnit.SECONDS.sleep(1);
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType*FUNCTION*Omo*vis_con SET 0 \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else {
//					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
				}
				if(bocf.getLast_type() != null && !bocf.getLast_type().trim().isEmpty()) {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						if(bocf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallPerformerOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallPerformerIn START \0");
							bocf.setLast_type(bocf.getType());
						}
					}
				}else {
					bocf.setLast_type(bocf.getType());
					if(bocf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll*ACTIVE SET 1 \0");

						//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Reset START \0");
//						TimeUnit.MILLISECONDS.sleep(900);
						//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
//						TimeUnit.MILLISECONDS.sleep(100);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallPerformerIn START \0");
						//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
						System.out.println("HI");
						bocf.setLast_type(bocf.getType());
					}
				}
				
				which_graphic_on_screen = "BATBALLSUMMARY_BOWLINGCARD_PERFORMER";
				TimeUnit.SECONDS.sleep(1);
				break;
				
			case "ANIMATE-IN-BAT-PERFORMER":
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp$FirstName"
						+ "*GEOM*TEXT SET " + " " + "\0");
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallPerformerOut START \0");
						TimeUnit.MILLISECONDS.sleep(900);
					}
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					bocf.setLast_type("");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.SECONDS.sleep(1);
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 0 \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingRightCardIn START \0");
				}else {
//					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
				}
				
				if(bcf.getLast_type() != null && !bcf.getLast_type().trim().isEmpty()) {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						if(bcf.getType().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$"
									+ "BatPartnershipGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPartnershipOut START \0");
							TimeUnit.MILLISECONDS.sleep(900);
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPartnershipIn START \0");
							bcf.setLast_type(bcf.getType());
						}else if(bcf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$"
									+ "BatPerformerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPartnershipOut START \0");
							TimeUnit.MILLISECONDS.sleep(900);
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPerformerIn START \0");
							bcf.setLast_type(bcf.getType());
						}
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						if(bcf.getType().toUpperCase() == "PARTNERSHIP") {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPartnershipGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPerformerOut START \0");
							TimeUnit.MILLISECONDS.sleep(900);
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPartnershipIn START \0");
							bcf.setLast_type(bcf.getType());
						}else if(bcf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPerformerOut START \0");
							TimeUnit.MILLISECONDS.sleep(900);
							print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPerformerIn START \0");
							bcf.setLast_type(bcf.getType());
						}
					}
				}else {
					bcf.setLast_type(bcf.getType());
					if(bcf.getType().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$"
								+ "BatPartnershipGrp*ACTIVE SET 1 \0");
						//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 0 \0");
						//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Reset START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingRightCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPartnershipIn START \0");
						//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
						
						bcf.setLast_type(bcf.getType());
					}else if(bcf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$"
//								+ "BatPerformerGrp*ACTIVE SET 1 \0");
						//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 0 \0");
						//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Reset START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(600);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingRightCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPerformerIn START \0");
						//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
						bcf.setLast_type(bcf.getType());
					}
				}
				
				which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD_PERFORMER";
			
				TimeUnit.SECONDS.sleep(1);
				break;	
				
			case "ANIMATE-IN-SCORECARD":
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallPerformerOut START \0");
						TimeUnit.MILLISECONDS.sleep(900);
					}
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					bocf.setLast_type("");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP"){
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else {
					AnimateInGraphics(print_writer, "SCORECARD");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD";
				break;
			case "ANIMATE-IN-PLAYOFFS":
				AnimateInGraphics(print_writer, "PLAYOFFS");
				which_graphic_on_screen = "PLAYOFFS";
				break;
			case "ANIMATE-IN-FIX_AND_RESULT":
				AnimateInGraphics(print_writer, "FIX_AND_RESULT");
				which_graphic_on_screen = "FIX_AND_RESULT";
				break;
			case "ANIMATE-MINI-BOWLER_VS_ALLBATSMAN":
				AnimateInGraphics(print_writer, "BOWLER_VS_ALLBATSMAN");
				which_graphic_on_screen = "BOWLER_VS_ALLBATSMAN";
				break;
			case "ANIMATE-MINI-BATSMAN_VS_ALLBOWLERS":
				AnimateInGraphics(print_writer, "BATSMAN_VS_ALLBOWLER");
				which_graphic_on_screen = "BATSMAN_VS_ALLBOWLER";
				break;
			case "ANIMATE-IN-BALLGRIFF":
				AnimateInGraphics(print_writer, "BALLGRIFF");
				which_graphic_on_screen = "BALLGRIFF";
				break;
			case "ANIMATE-IN-BATGRIFF":
				AnimateInGraphics(print_writer, "BATGRIFF");
				which_graphic_on_screen = "BATGRIFF";
				break;
			case "ANIMATE-MINI-BATTINGCARD":
				AnimateInGraphics(print_writer, "MINI_BATTINGCARD");
				which_graphic_on_screen = "MINI_BATTINGCARD";
				break;
			case "ANIMATE-MINI-BOWLINGCARD":
				AnimateInGraphics(print_writer, "MINI_BOWLINGCARD");
				which_graphic_on_screen = "MINI_BOWLINGCARD";
				break;
			case "ANIMATE-IN-BOWLINGCARD":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPartnershipOut START \0");
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					bcf.setLast_type("");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP"){
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else{
					AnimateInGraphics(print_writer, "BOWLINGCARD");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_BOWLINGCARD";
				break;
			case "ANIMATE-IN-PARTNERSHIP":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
				}else {
					AnimateInGraphics(print_writer, "PARTNERSHIP");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_PARTNERSHIP";
				break;
			case "ANIMATE-IN-TIEID-DOUBLE":
				AnimateInGraphics(print_writer, "TIEID-DOUBLE");
				which_graphic_on_screen = "TIEID-DOUBLE";
				break;
			case "ANIMATE-FF_SUMMARY_GRAPHICS":
//				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
				AnimateInGraphics(print_writer, "FF_SUMMARY_GRAPHICS");
				which_graphic_on_screen = "FF_SUMMARY_GRAPHICS";
				break;
			case "ANIMATE-IN-MATCHSUMARRY":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPartnershipOut START \0");
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					bcf.setLast_type("");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallPerformerOut START \0");
						TimeUnit.MILLISECONDS.sleep(900);
					}
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					bocf.setLast_type("");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP"){
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
					;
				}else {
					AnimateInGraphics(print_writer, "MATCHSUMMARY");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_MATCHSUMMARY";
				break;
			case "ANIMATE-IN-BUG-TOSS":
				AnimateInGraphics(print_writer, "BUG-TOSS");
				which_graphic_on_screen = "BUG-TOSS";
				break;
			case "ANIMATE-IN-BUG_HIGHLIGHT":
				AnimateInGraphics(print_writer, "BUG_HIGHLIGHT");
				which_graphic_on_screen = "BUG_HIGHLIGHT";
				break;
			case "ANIMATE-IN-BUGPARTNERSHIP":
				AnimateInGraphics(print_writer, "BUG_PARTNERSHIP");
				which_graphic_on_screen = "BUG_PARTNERSHIP";
				break;
			case "ANIMATE-IN-MULTI_PARTNERSHIP":
				AnimateInGraphics(print_writer, "MULTI_PARTNERSHIP");
				which_graphic_on_screen = "MULTI_PARTNERSHIP";
				break;
			case "ANIMATE-IN-BUG_POWERPLAY":
				AnimateInGraphics(print_writer, "BUG_POWERPLAY");
				which_graphic_on_screen = "BUG_POWERPLAY";
				break;
			case "ANIMATE-IN-BUG-DISMISSAL":
				AnimateInGraphics(print_writer, "BUG-DISMISSAL");
				which_graphic_on_screen = "BUG-DISMISSAL";
				break;
			case "ANIMATE-IN-BUG":
				AnimateInGraphics(print_writer, "BUG");
				which_graphic_on_screen = "BUG";
				break;
			case "ANIMATE-IN-BUG-BOWLER":
				AnimateInGraphics(print_writer, "BUGBOWLER");
				which_graphic_on_screen = "BUGBOWLER";
				break;
			case "ANIMATE-IN-BUG-DB":
				AnimateInGraphics(print_writer, "BUG-DB");
				which_graphic_on_screen = "BUG-DB";
				break;
			case "ANIMATE-IN-IMPACT":
				AnimateInGraphics(print_writer, "IMPACT");
				which_graphic_on_screen = "IMPACT";
				break;
			case "ANIMATE-IN-POINTERS":
				AnimateInGraphics(print_writer, "POINTER");
				which_graphic_on_screen = "POINTER";
				break;
			case "ANIMATE-IN-HOWOUT":
				AnimateInGraphics(print_writer, "HOWOUT");
				which_graphic_on_screen = "HOWOUT";
				break;
			case "ANIMATE-IN-HOWOUT_QUICK":
				AnimateInGraphics(print_writer, "HOWOUT_QUICK");
				which_graphic_on_screen = "HOWOUT_QUICK";
				break;
			case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER":
				AnimateInGraphics(print_writer, "HOWOUT_WITHOUT");
				which_graphic_on_screen = "HOWOUT_WITHOUT";
				break;
			case "ANIMATE-IN-SCHEDULE":
				AnimateInGraphics(print_writer, "SCHEDULE");
				which_graphic_on_screen = "SCHEDULE";
				break;
			case "ANIMATE-IN-NAMESUPER":
				AnimateInGraphics(print_writer, "NAMESUPER");
				which_graphic_on_screen = "NAMESUPER";
				break;
			case "ANIMATE-IN-NAMESUPER-PLAYER":
				AnimateInGraphics(print_writer, "NAMESUPER-PLAYER");
				which_graphic_on_screen = "NAMESUPER-PLAYER";
				break;
			case "ANIMATE-IN-FALLOFWICKET":
				AnimateInGraphics(print_writer, "FALLOFWICKET");
				which_graphic_on_screen = "FALLOFWICKET";
				break;
			case "ANIMATE-IN-TEAMS_LOGO":
				AnimateInGraphics(print_writer, "TEAMS_LOGO");
				which_graphic_on_screen = "TEAMS_LOGO";
				break;
			case "ANIMATE-IN-L3MATCHID":
				AnimateInGraphics(print_writer, "L3MATCHID");
				which_graphic_on_screen = "L3MATCHID";
				break;
			case "ANIMATE-IN-MATCH_PROMO":
				AnimateInGraphics(print_writer, "MATCH_PROMO");
				which_graphic_on_screen = "MATCH_PROMO";
				break;
			case "ANIMATE-IN-L3MATCH_PROMO":
				AnimateInGraphics(print_writer, "L3MATCH_PROMO");
				which_graphic_on_screen = "L3MATCH_PROMO";
				break;
			case "ANIMATE-IN-PREVIOUS_SUMMARY":
//				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
				AnimateInGraphics(print_writer, "PREVIOUS_SUMMARY");
				which_graphic_on_screen = "PREVIOUS_SUMMARY";
				break;
			case "ANIMATE-IN-TARGET":
				AnimateInGraphics(print_writer, "TARGET");
				which_graphic_on_screen = "TARGET";
				break;
			case "ANIMATE-IN-BUGTARGET":
				AnimateInGraphics(print_writer, "BUGTARGET");
				which_graphic_on_screen = "BUGTARGET";
				break;
			case "ANIMATE-IN-COMPARISION":
				AnimateInGraphics(print_writer, "COMPARISION");
				which_graphic_on_screen = "COMPARISION";
				break;
			case "ANIMATE-IN-LTPARTNERSHIP":
				AnimateInGraphics(print_writer, "LTPARTNERSHIP");
				which_graphic_on_screen = "LTPARTNERSHIP";
				break;
			case "ANIMATE-IN-SPLIT":
				AnimateInGraphics(print_writer, "SPLIT");
				which_graphic_on_screen = "SPLIT";
				break;
			case "ANIMATE-IN-BATSMANSTATS":
				AnimateInGraphics(print_writer, "BATSMANSTATS");
				which_graphic_on_screen = "BATSMANSTATS";
				break;
			case "ANIMATE-IN-BOWLERSTATS":
				AnimateInGraphics(print_writer, "BOWLERSTATS");
				which_graphic_on_screen = "BOWLERSTATS";
				break;
			case "ANIMATE-IN-BOWLERSUMMARY":
				AnimateInGraphics(print_writer, "BOWLERSUMMARY");
				which_graphic_on_screen = "BOWLERSUMMARY";
				break;
			case "ANIMATE-IN-PLAYERSUMMARY":
				AnimateInGraphics(print_writer, "PLAYERSUMMARY");
				which_graphic_on_screen = "PLAYERSUMMARY";
				break;
			case "ANIMATE-IN-TEAMSUMMARY":
				AnimateInGraphics(print_writer, "TEAMSUMMARY");
				which_graphic_on_screen = "TEAMSUMMARY";
				break;
			case "ANIMATE-IN-NEXT_TO_BAT":
				AnimateInGraphics(print_writer, "NEXTTOBAT");
				which_graphic_on_screen = "NEXTTOBAT";
				break;
			case "ANIMATE-IN-PHASE":
				AnimateInGraphics(print_writer, "PHASE");
				which_graphic_on_screen = "PHASE";
				break;
			case "ANIMATE-IN-PROJECTED":
				AnimateInGraphics(print_writer, "PROJECTED");
				which_graphic_on_screen = "PROJECTED";
				break;
			case "ANIMATE-IN-BOWLERDETAILS":
				AnimateInGraphics(print_writer, "BOWLERDETAILS");
				which_graphic_on_screen = "BOWLERDETAILS";
				break;
			case "ANIMATE-IN-LTPOWERPLAY":
				AnimateInGraphics(print_writer, "LTPOWERPLAY");
				which_graphic_on_screen = "LTPOWERPLAY";
				break;
			case "ANIMATE-IN-MATCHID":
				AnimateInGraphics(print_writer, "MATCHID");
				which_graphic_on_screen = "MATCHID";
				break;
			case "ANIMATE-IN-L3PLAYERPROFILE":
				AnimateInGraphics(print_writer, "L3PLAYERPROFILE");
				which_graphic_on_screen = "L3PLAYERPROFILE";
				break;
			case "ANIMATE-IN-LTPLAYERPROFILEBAT":
				AnimateInGraphics(print_writer, "LTPLAYERPROFILEBAT");
				which_graphic_on_screen = "LTPLAYERPROFILEBAT";
				break;
			case "ANIMATE-IN-PLAYERPROFILEBALL":
				AnimateInGraphics(print_writer, "PLAYERPROFILEBALL");
				which_graphic_on_screen = "PLAYERPROFILEBALL";
				break;
			case "ANIMATE-IN-THISSERIES_BALL":
				AnimateInGraphics(print_writer, "THISSERIES-BALL");
				which_graphic_on_screen = "THISSERIES-BALL";
				break;
			case "ANIMATE-IN-THISSERIES":
				AnimateInGraphics(print_writer, "THISSERIES");
				which_graphic_on_screen = "THISSERIES";
				break;
			case "ANIMATE-IN-FFTHISSERIES_BALL":
				AnimateInGraphics(print_writer, "FF-THISSERIES_BALL");
				which_graphic_on_screen = "FF-THISSERIES_BALL";
				break;
			case "ANIMATE-IN-FFTHISSERIES":
				AnimateInGraphics(print_writer, "FF-THISSERIES");
				which_graphic_on_screen = "FF-THISSERIES";
				break;
			case "ANIMATE-IN-PLAYERPROFILE":
				AnimateInGraphics(print_writer, "FFPLAYERPROFILE");
				which_graphic_on_screen = "FFPLAYERPROFILE";
				break;
			case "ANIMATE-IN-PLAYERPROFILEBAT":
				AnimateInGraphics(print_writer, "PLAYERPROFILEBAT");
				which_graphic_on_screen = "PLAYERPROFILEBAT";
				break;
			case "ANIMATE-IN-PLAYINGXI_SEQUENCE":
				AnimateInGraphics(print_writer, "PLAYINGXI_SEQUENCE");
				which_graphic_on_screen = "PLAYINGXI_SEQUENCE";
				break;
			case "ANIMATE-IN-PLAYINGXI":
				AnimateInGraphics(print_writer, "TEAMLINEUP");
				which_graphic_on_screen = "TEAMLINEUP";
				break;
			case "ANIMATE-IN-TEAM_SQUAD":
				AnimateInGraphics(print_writer, "TEAM_SQUAD");
				which_graphic_on_screen = "TEAM_SQUAD";
				break;
			case "ANIMATE-IN-DOUBLETEAMS":
				AnimateInGraphics(print_writer, "DOUBLETEAMS");
				which_graphic_on_screen = "DOUBLETEAMS";
				break;
			case "ANIMATE-IN-CAPTAINS":
				AnimateInGraphics(print_writer, "CAPTAINS");
				which_graphic_on_screen = "CAPTAINS";
				break;
			case "ANIMATE-IN-TOP_PERFORMER":
				AnimateInGraphics(print_writer, "TOP_PERFORMER");
				which_graphic_on_screen = "TOP_PERFORMER";
				break;
			case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-LANDMARK_BALL":
				AnimateInGraphics(print_writer, "LANDMARK");
				which_graphic_on_screen = "LANDMARK";
				break;
			case "ANIMATE-IN-EQUATION":
				AnimateInGraphics(print_writer, "EQUATION");
				which_graphic_on_screen = "EQUATION";
				break;
			case "ANIMATE-IN-POSITION_LANDMARK":
				AnimateInGraphics(print_writer, "POSITION_LANDMARK");
				which_graphic_on_screen = "POSITION_LANDMARK";
				break;
			case "ANIMATE-IN-BATSMAN_THIS_MATCH":
				AnimateInGraphics(print_writer, "BATSMAN_THIS_MATCH");
				which_graphic_on_screen = "BATSMAN_THIS_MATCH";
				break;
			case "ANIMATE-IN-BOWLER_THIS_MATCH":
				AnimateInGraphics(print_writer, "BOWLER_THIS_MATCH");
				which_graphic_on_screen = "BOWLER_THIS_MATCH";
				break;
			case "ANIMATE-IN-POINTSTABLE":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPartnershipOut START \0");
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					bcf.setLast_type("");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallPerformerOut START \0");
						TimeUnit.MILLISECONDS.sleep(900);
					}
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut START \0");
					
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
					bocf.setLast_type("");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP"){
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut START \0");
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else {
//					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
					AnimateInGraphics(print_writer, "POINTSTABLE");
				}
				which_graphic_on_screen = "POINTSTABLE";
				break;
			case "ANIMATE-IN-LTPOINTSTABLE":
				AnimateInGraphics(print_writer, "LTPOINTSTABLE");
				which_graphic_on_screen = "LTPOINTSTABLE";
				break;
			case "ANIMATE-IN-BOWLER_STYLE":
				AnimateInGraphics(print_writer, "BOWLER_STYLE");
				which_graphic_on_screen = "BOWLER_STYLE";
				break;
			case "ANIMATE-IN-BATSMAN_STYLE":
				AnimateInGraphics(print_writer, "BATSMAN_STYLE");
				which_graphic_on_screen = "BATSMAN_STYLE";
				break;
			case "ANIMATE-IN-MANHATTAN":
				AnimateInGraphics(print_writer, "MANHATTAN");
				which_graphic_on_screen = "MANHATTAN";
				break;
			case "ANIMATE-IN-WORM":
				AnimateInGraphics(print_writer, "WORM");
				which_graphic_on_screen = "WORM";
				break;
			case "ANIMATE-IN-MOST":
				AnimateInGraphics(print_writer, "MOST");
				which_graphic_on_screen = "MOST";
				break;
			case "ANIMATE-IN-LEADERBOARD":
				AnimateInGraphics(print_writer, "LEADERBOARD");
				which_graphic_on_screen = "LEADERBOARD";
				break;
			case "ANIMATE-IN-FF_STATS":
				AnimateInGraphics(print_writer, "FF_STATS");
				which_graphic_on_screen = "FF_STATS";
				break;
			case "ANIMATE-IN-INFOBAR":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-IDENT");
					AnimateInGraphics(print_writer, "MAIN");
					which_graphic_on_screen = "SCOREBUG";
					infobar.setInfobar_on_screen(true);
					
				}else {
					AnimateInGraphics(print_writer, "SCOREBUG");
					which_graphic_on_screen = "SCOREBUG";
					infobar.setInfobar_on_screen(true);
				}
				
				break;
			case "TICKER_LT_OUT":
				if(!which_graphic_on_screen.isEmpty() && which_graphic_on_screen != "SCOREBUG") {
					AnimateOutGraphics(print_writer, which_graphic_on_screen);
					TimeUnit.SECONDS.sleep(1);
				}
				//populateInfobar(infobar, print_writer, valueToProcess.split(",")[0],match, broadcaster);
				AnimateOutGraphics(print_writer, "FF_OUT");
				TimeUnit.SECONDS.sleep(1);
				which_graphic_on_screen = "SCOREBUG";
				infobar.setInfobar_on_screen(true);
				//AnimateOutGraphics(print_writer, which_graphic_on_screen);
				break;
			case "TICKER_LT_IN":
				AnimateInGraphics(print_writer, "FF_IN");
				TimeUnit.SECONDS.sleep(1);
				infobar.setInfobar_on_screen(false);
				if(which_graphic_on_screen != "SCOREBUG") {
					AnimateOutGraphics(print_writer, which_graphic_on_screen);
				}
				break;		
			case "CLEAR-ALL":
				   print_writer.println("-1 SCENE CLEANUP\0");
	               print_writer.println("-1 IMAGE CLEANUP\0");
	               print_writer.println("-1 GEOM CLEANUP\0");
	               print_writer.println("-1 FONT CLEANUP\0");
	               
	               print_writer.println("-1 IMAGE INFO\0");
	               print_writer.println("-1 RENDERER SET_OBJECT SCENE*" + valueToProcess.split(",")[0] + "\0");
	
	               print_writer.println("-1 RENDERER INITIALIZE\0");
	               print_writer.println("-1 RENDERER*SCENE_DATA INITIALIZE\0");
	               print_writer.println("-1 RENDERER*UPDATE SET 0\0");
	               print_writer.println("-1 RENDERER*STAGE SHOW 0.0\0");
	               
	               print_writer.println("-1 RENDERER*UPDATE SET 1\0");
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/APL/ScoreBug\0");
		           	
	               print_writer.println("-1 RENDERER*FRONT_LAYER INITIALIZE\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 0\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section4In SHOW 0.0 \0");
//	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section5$BowlingEndIn SHOW 0.0 \0");
//	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section5$EconomyIn SHOW 0.0 \0");
//	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section5$ThisOverIn SHOW 0.0 \0");
	               
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 1\0");
	               
	               print_writer.println("-1 SCENE CLEANUP\0");
	               print_writer.println("-1 IMAGE CLEANUP\0");
	               print_writer.println("-1 GEOM CLEANUP\0");
	               print_writer.println("-1 FONT CLEANUP\0");
	               
	               bocf.setLast_type(null);
	               infobar.setInfobar_on_screen(false);
	               infobar = new Infobar();
	               which_graphic_on_screen = "";
					break;
			case "ANIMATE-OUT":
				switch(which_graphic_on_screen) {
				case "IDENT":
					AnimateOutGraphics(print_writer, "IDENT");
					which_graphic_on_screen = "";
					infobar.setInfobar_on_screen(false);
					break;
				case "BATBALLSUMMARY_BOWLINGCARD_PERFORMER":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_BOWLINGCARD_PERFORMER");
					bocf.setLast_type(null);bocf.setType("");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BATBALLSUMMARY_SCORECARD_PERFORMER":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_SCORECARD_PERFORMER");
					bcf.setLast_type(null);bcf.setType("");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BATBALLSUMMARY_SCORECARD":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_SCORECARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "PLAYOFFS":
					AnimateOutGraphics(print_writer, "PLAYOFFS");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				
				case "FIX_AND_RESULT":
					AnimateOutGraphics(print_writer, "FIX_AND_RESULT");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BATSMAN_VS_ALLBOWLER":
					AnimateOutGraphics(print_writer, "BATSMAN_VS_ALLBOWLER");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLER_VS_ALLBATSMAN":
					AnimateOutGraphics(print_writer, "BOWLER_VS_ALLBATSMAN");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BALLGRIFF":
					AnimateOutGraphics(print_writer, "BALLGRIFF");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BATGRIFF":
					AnimateOutGraphics(print_writer, "BATGRIFF");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "MINI_BATTINGCARD":
					AnimateOutGraphics(print_writer, "MINI_BATTINGCARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "MINI_BOWLINGCARD":
					AnimateOutGraphics(print_writer, "MINI_BOWLINGCARD");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BATBALLSUMMARY_BOWLINGCARD":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_BOWLINGCARD");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "FF_SUMMARY_GRAPHICS":
					AnimateOutGraphics(print_writer, "FF_SUMMARY_GRAPHICS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BATBALLSUMMARY_MATCHSUMMARY":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_MATCHSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BATBALLSUMMARY_PARTNERSHIP":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "TIEID-DOUBLE":
					AnimateOutGraphics(print_writer, "TIEID-DOUBLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG_PARTNERSHIP":
					AnimateOutGraphics(print_writer, "BUG_PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG-TOSS":
					AnimateOutGraphics(print_writer, "BUG-TOSS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG_POWERPLAY":
					AnimateOutGraphics(print_writer, "BUG_POWERPLAY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG_HIGHLIGHT":
					AnimateOutGraphics(print_writer, "BUG_HIGHLIGHT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;	
				case "MULTI_PARTNERSHIP":
					AnimateOutGraphics(print_writer, "MULTI_PARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG-DISMISSAL":
					AnimateOutGraphics(print_writer, "BUG-DISMISSAL");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG":
					AnimateOutGraphics(print_writer, "BUG");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUGBOWLER":
					AnimateOutGraphics(print_writer, "BUGBOWLER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG-DB":
					AnimateOutGraphics(print_writer, "BUG-DB");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "IMPACT":
					AnimateOutGraphics(print_writer, "IMPACT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "POINTER":
					AnimateOutGraphics(print_writer, "POINTER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "HOWOUT":
					AnimateOutGraphics(print_writer, "HOWOUT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "HOWOUT_QUICK":
					AnimateOutGraphics(print_writer, "HOWOUT_QUICK");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "HOWOUT_WITHOUT":
					AnimateOutGraphics(print_writer, "HOWOUT_WITHOUT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "SCHEDULE":
					AnimateOutGraphics(print_writer, "SCHEDULE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "NAMESUPER":
					AnimateOutGraphics(print_writer, "NAMESUPER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "NAMESUPER-PLAYER":
					AnimateOutGraphics(print_writer, "NAMESUPER-PLAYER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "SCOREBUG":
					AnimateOutGraphics(print_writer, "SCOREBUG");
					infobar.setInfobar_on_screen(false);
					infobar = new Infobar();
					break;
				
				case "FALLOFWICKET":
					AnimateOutGraphics(print_writer, "FALLOFWICKET");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "TEAMS_LOGO":
					AnimateOutGraphics(print_writer, "TEAMS_LOGO");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "L3MATCHID":
					AnimateOutGraphics(print_writer, "L3MATCHID");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "PLAYERPROFILEBALL":
					AnimateOutGraphics(print_writer, "PLAYERPROFILEBALL");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "PLAYERPROFILEBAT":
					AnimateOutGraphics(print_writer, "PLAYERPROFILEBAT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;	
				case "PREVIOUS_SUMMARY":
					AnimateOutGraphics(print_writer, "PREVIOUS_SUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "MATCH_PROMO":
					AnimateOutGraphics(print_writer, "MATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "L3MATCH_PROMO":
					AnimateOutGraphics(print_writer, "L3MATCH_PROMO");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "TARGET":
					AnimateOutGraphics(print_writer, "TARGET");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BUGTARGET":
					AnimateOutGraphics(print_writer, "BUGTARGET");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					//resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "COMPARISION":
					AnimateOutGraphics(print_writer, "COMPARISION");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LTPARTNERSHIP":
					AnimateOutGraphics(print_writer, "LTPARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "SPLIT":
					AnimateOutGraphics(print_writer, "SPLIT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BATSMANSTATS":
					AnimateOutGraphics(print_writer, "BATSMANSTATS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLERSTATS":
					AnimateOutGraphics(print_writer, "BOWLERSTATS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLERSUMMARY":
					AnimateOutGraphics(print_writer, "BOWLERSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "PLAYERSUMMARY":
					AnimateOutGraphics(print_writer, "PLAYERSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "TEAMSUMMARY":
					AnimateOutGraphics(print_writer, "TEAMSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "NEXTTOBAT":
					AnimateOutGraphics(print_writer, "NEXTTOBAT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "PHASE":
					AnimateOutGraphics(print_writer, "PHASE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "PROJECTED":
					AnimateOutGraphics(print_writer, "PROJECTED");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLERDETAILS":
					AnimateOutGraphics(print_writer, "BOWLERDETAILS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LTPOWERPLAY":
					AnimateOutGraphics(print_writer, "LTPOWERPLAY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "MATCHID":
					AnimateOutGraphics(print_writer, "MATCHID");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "L3PLAYERPROFILE":
					AnimateOutGraphics(print_writer, "L3PLAYERPROFILE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LTPLAYERPROFILEBAT":
					AnimateOutGraphics(print_writer, "LTPLAYERPROFILEBAT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "THISSERIES":
					AnimateOutGraphics(print_writer, "THISSERIES");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "THISSERIES-BALL":
					AnimateOutGraphics(print_writer, "THISSERIES-BALL");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "FF-THISSERIES_BALL":
					AnimateOutGraphics(print_writer, "FF-THISSERIES_BALL");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "FF-THISSERIES":
					AnimateOutGraphics(print_writer, "FF-THISSERIES");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "FFPLAYERPROFILE":
					AnimateOutGraphics(print_writer, "FFPLAYERPROFILE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "PLAYINGXI_SEQUENCE":
					AnimateOutGraphics(print_writer, "PLAYINGXI_SEQUENCE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"");
					break;
				case "TEAMLINEUP":
					AnimateOutGraphics(print_writer, "TEAMLINEUP");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "TEAM_SQUAD":
					AnimateOutGraphics(print_writer, "TEAM_SQUAD");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "DOUBLETEAMS":
					AnimateOutGraphics(print_writer, "DOUBLETEAMS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "CAPTAINS":
					AnimateOutGraphics(print_writer, "CAPTAINS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "TOP_PERFORMER":
					AnimateOutGraphics(print_writer, "TOP_PERFORMER");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "LANDMARK":
					AnimateOutGraphics(print_writer, "LANDMARK");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "EQUATION":
					AnimateOutGraphics(print_writer, "EQUATION");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "POSITION_LANDMARK":
					AnimateOutGraphics(print_writer, "POSITION_LANDMARK");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BATSMAN_THIS_MATCH":
					AnimateOutGraphics(print_writer, "BATSMAN_THIS_MATCH");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLER_THIS_MATCH":
					AnimateOutGraphics(print_writer, "BOWLER_THIS_MATCH");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "POINTSTABLE":
					AnimateOutGraphics(print_writer, "POINTSTABLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "LTPOINTSTABLE":
					AnimateOutGraphics(print_writer, "LTPOINTSTABLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BOWLER_STYLE":
					AnimateOutGraphics(print_writer, "BOWLER_STYLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BATSMAN_STYLE":
					AnimateOutGraphics(print_writer, "BATSMAN_STYLE");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "MANHATTAN":
					AnimateOutGraphics(print_writer, "MANHATTAN");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "WORM":
					AnimateOutGraphics(print_writer, "WORM");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "MOST":
					AnimateOutGraphics(print_writer, "MOST");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "LEADERBOARD":
					AnimateOutGraphics(print_writer, "LEADERBOARD");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "FF_STATS":
					AnimateOutGraphics(print_writer, "FF_STATS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				}
				break;
			case "ANIMATE-OUT-SECTION2":
				if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
					switch(infobar.getLast_top_section().toUpperCase()) {
					case CricketUtil.TOSS:
						processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
						break;
					case "CRR":
						processAnimation(print_writer, "Section2$CurRunRateOut", "START", broadcaster);
						break;
					case "CRR_RRR":
						processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
						break;
					case "FIRST_INNING_SCORE":
						processAnimation(print_writer, "Section2$FistInnScoreOut", "START", broadcaster);
						break;
					case CricketUtil.BOUNDARY:
						processAnimation(print_writer, "Section2$BallsSinceLastBoundaryOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section2$DLSTargetOut", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section2$ExtrasOut", "START", broadcaster);
						break;
					case "EQUATION":
						processAnimation(print_writer, "Section2$EquationOut", "START", broadcaster);
						break;
					case "PROJECTED":
						processAnimation(print_writer, "Section2$ProjectedOut", "START", broadcaster);
						break;
					case "BOUNDARIES":
						processAnimation(print_writer, "Section2$BoundariesOut", "START", broadcaster);
						break;
					case "PARTNERSHIP":
						processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
						break;
					case "LAST_WICKET":
						processAnimation(print_writer, "Section2$LastWicketOut", "START", broadcaster);
						break;
					case CricketUtil.TIMELINE:
						processAnimation(print_writer, "Section2$TimelineOut", "START", broadcaster);
						break;
					case "STATISTICS":
						processAnimation(print_writer, "Section2$FreeTextSmallOut", "START", broadcaster);
						break;
					}
					processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
				}
				infobar.setLast_top_section("");infobar.setTop_section("");
				break;
			case "ANIMATE-OUT-SECTION4_N_5":
				if(infobar.getLast_bottom_right_section() != null && infobar.getLast_bottom_right_section() != "") {
					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;
					case "TOURNAMENT_FOURS":
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;	
					case "TOURNAMENT_SIXES":
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonOut", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					}
				}
				processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
				processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
				processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
				
				infobar.setBottom_right_bottom_section(CricketUtil.OVER);
				infobar = populateVizInfobarRightBottom(infobar, false,print_writer, match, broadcaster);
				
				infobar.setBottom_right_top_section(CricketUtil.BOWLER);
				infobar = populateVizInfobarRightTop(infobar, false,print_writer, match, broadcaster);
				
				processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
				processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
				processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
				
				infobar.setLast_bottom_right_section("");
				infobar.setBottom_right_section("");
				break;
				
			case "ANIMATE-OUT-DIRECTOR":
				AnimateOutGraphics(print_writer, "DIRECTOR");
				break;
			
			}
			break;
			
		case "BUG_GRAPHICS-OPTIONS": case "BUG_DISMISSAL_GRAPHICS-OPTIONS": case "BUG_BOWLER_GRAPHICS-OPTIONS":
		
		case "HOWOUT_GRAPHICS-OPTIONS": case "BATSMANSTATS_GRAPHICS-OPTIONS": case "BOWLERSTATS_GRAPHICS-OPTIONS": case "NAMESUPER_PLAYER_GRAPHICS-OPTIONS": 
		case "L3PLAYERPROFILE_GRAPHICS-OPTIONS": case "COMPARISION-GRAPHICS-OPTIONS": case "PROJECTED_GRAPHICS-OPTIONS": case "TARGET_GRAPHICS-OPTIONS":
		case "PLAYERSUMMARY_GRAPHICS-OPTIONS": case "HOWOUT_WITHOUT_FIELDER_GRAPHICS-OPTIONS": case "BOWLERDETAILS_GRAPHICS-OPTIONS": 
		case "NEXTTOBAT_GRAPHICS-OPTIONS": case "BOWLERSUMMARY_GRAPHICS-OPTIONS": case "EQUATION_GRAPHICS-OPTIONS": case "BATSMAN_THIS_MATCH_GRAPHICS-OPTIONS":
		case "BOWLER_THIS_MATCH_GRAPHICS-OPTIONS": case "PLAYERS_GRAPHICS-OPTIONS": case "BATSMAN_STYLE_GRAPHICS-OPTIONS": case "RIGHT_GRAPHICS-OPTIONS":
		case "THISSERIES-STATS_GRAPHICS-OPTIONS":
			
		case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "ANIMATE_PLAYINGXI-OPTIONS": case "TOP_GRAPHICS-OPTIONS": case "LANDMARK_GRAPHICS-OPTIONS":
		case "POSITION_LANDMARK_GRAPHICS-OPTIONS": case "FF_THISSERIES-STATS_GRAPHICS-OPTIONS":
			
		case "BOTTOMLEFT_GRAPHICS-OPTIONS": case "BOTTOMRIGHT_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": 
		case "BOTTOM_GRAPHICS-OPTIONS":  
		    	
			return match;
		case "MOST_GRAPHICS-OPTIONS": case "TEAM_SQUAD_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getTeams()).toString();
		case "EXCEL_FF_SUMMARY_GRAPHICS_OPTION":
			return new ObjectMapper().writeValueAsString(CricketFunctions.ReadExcel("C:\\Sports\\Cricket\\Summary.xlsx").keySet()).toString();
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "L3_MATCH-PROMO_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
		case "BUG_DB_GRAPHICS-OPTIONS": case "BUG_DB2_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getBugs()).toString();
		case "LT_POINTERS_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getPointers()).toString();
		case "PROMPT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getInfobarStats()).toString();
		case "LEADERBOARD_GRAPHICS-OPTIONS": case "WICKETS_GRAPHICS-OPTIONS": case "FOURS_GRAPHICS-OPTIONS": case "SIXES_GRAPHICS-OPTIONS":
//			List<Tournament> tourn_stats = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, past_tournament_stats);
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
		
		//scorebug
		case "POPULATE-SIXDIRECTOR": case "POPULATE-FOURDIRECTOR": case "POPULATE-WICKETDIRECTOR": case "POPULATE-FREEHITDIRECTOR": case "POPULATE-POWERPLAY_DIRECTOR":
		case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-IDENT":
		case "POPULATE-INFOBAR-FREE_TEXT": case "POPULATE-INFOBAR-LAST_X_BALLS": case "POPULATE-COMMENTATORS":
			
		//FF
		case "POPULATE-PLAYOFFS": case "POPULATE-MINI-BATSMAN_VS_ALLBOWLERS": case "POPULATE-MINI-BOWLER_VS_ALLBATSMAN": case "POPULATE-FF-BATGRIFF": case "POPULATE-FF-BALLGRIFF":
		case "POPULATE-MINI-BATTINGCARD": case "POPULATE-MINI-BOWLINGCARD": case "POPULATE-FF-FIX_AND_RESULT": case "POPULATE-FF_SUMMARY_GRAPHICS":
		case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-FF-PLAYERPROFILE":
		case "POPULATE-FF-DOUBLETEAMS": case "POPULATE-FF-MATCHID": case "POPULATE-FF-PLAYINGXI": case "POPULATE-FF-PLAYINGXI_SEQUENCE": case "POPULATE-FF-LANDMARK": case "POPULATE-PREVIOUS_SUMMARY":
		case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-POINTS_TABLE": case "POPULATE-MANHATTAN": case "POPULATE-MATCH_PROMO": case "POPULATE-FF-TEAMS_LOGO":
		case "POPULATE-TIEID-DOUBLE": case "POPULATE-MOSTRUNS": case "POPULATE-MOSTWICKETS": case "POPULATE-MOSTFOURS": case "POPULATE-MOSTSIXES": case "POPULATE-HIGHESTSCORE":
		case "POPULATE-WORM": case "POPULATE-FF-SCHEDULE": case "POPULATE-FF-THISSERIES": case "POPULATE-FF-THISSERIES_BALL": case "POPULATE-FF-LEADERBOARD": case "POPULATE-FF-STATS": case "POPULATE-BAT_PERFORMER":
		case "POPULATE-BALL_PERFORMER": case "POPULATE-FF-CAPTAINS": case "POPULATE-FF-TOP_PERFORMER": case "POPULATE-FF-LANDMARK_BALL":
		case "POPULATE-FF-PLAYERPROFILEBALL": case "POPULATE-MOST_RUNS": case "POPULATE-FF-TEAM_SQUAD":
			
		//LT
		case "POPULATE-IMPACT": case "CHANGE_ON-IMPACT": case "POPULATE-L3-POINTERS": case "POPULATE-PHASE":
		case "POPULATE-L3-HOWOUT": case "POPULATE-L3-BATSMANSTATS": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-L3-CAPTAIN-PLAYER": case "POPULATE-LT-PROJECTED":
		case "POPULATE-L3-TARGET": case "POPULATE-L3-TEAMSUMMARY": case "POPULATE-L3-PLAYERSUMMARY": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-L3-FALLOFWICKET":
		case "POPULATE-L3-COMPARISION": case "POPULATE-LT-MATCHID": case "POPULATE-L3-BOWLERSTATS": case "POPULATE-L3-SPLIT": case "POPULATE-LT-PARTNERSHIP":
		case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER":case "POPULATE-L3-BOWLERSUMMARY": case "POPULATE-L3-NEXT_TO_BAT": case "POPULATE-L3-BOWLERDETAILS":
		case "POPULATE-LT-EQUATION": case "POPULATE-L3-BATSMAN_THIS_MATCH": case "POPULATE-L3-BOWLER_THIS_MATCH": case "POPULATE-LTPOINTS_TABLE":
		case "POPULATE-BOWLER_STYLE": case "POPULATE-BATSMAN_STYLE": case "POPULATE-LT-POWERPLAY": case "POPULATE-NEXT_TO_BAT": case "POPULATE-L3MATCH_PROMO":
		case "POPULATE-HOWOUT_QUICK": case "POPULATE-L3-THISSERIES":  case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-L3-THISSERIES_BALL":
			
		//Bug
		case "POPULATE-L3-BUG": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-BUG-DB": case "POPULATE-L3-BUG-BOWLER": case "POPULATE-L3-BUGTARGET":   
		case "POPULATE-BUG_POWERPLAY": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-MULTI_PARTNERSHIP": case "POPULATE-BUGPARTNERSHIP": case "POPULATE-L3-BUG-TOSS":
			
			System.out.println("GFX on screen : "+which_graphic_on_screen+" : what : "+whatToProcess);
			if(which_graphic_on_screen == "SCOREBUG" || which_graphic_on_screen == "IDENT" || which_graphic_on_screen == "IMPACT") {
			}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
					
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
					 
					 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
					 
					 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
					 
					 
					 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
					 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
					 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
					 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
					 
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POINTSTABLE") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
					 
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POINTSTABLE") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
					 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PLAYINGXI_SEQUENCE")) {
			}
			else if(which_graphic_on_screen != "") {
				AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
			}

			switch(whatToProcess.toUpperCase()) {
			case "CHANGE_ON-IMPACT":
			case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-FREE_TEXT": case "POPULATE-INFOBAR-LAST_X_BALLS": case "POPULATE-COMMENTATORS": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-DIRECTOR": case "POPULATE-INFOBAR_IDENT_DATA":
			case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-RIGHT":
				break;
			case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
					break;
				}else {
					scenes.get(0).scene_load(print_writer, broadcaster);
				}
				break;
			default:
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						 
						 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						 
						 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
						 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
						 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
						 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						 
						 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
						 
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POINTSTABLE") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						 
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POINTSTABLE") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER")||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						 which_graphic_on_screen == "PLAYINGXI_SEQUENCE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PLAYINGXI_SEQUENCE")) {
					//AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
				}else {
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,broadcaster);
					print_writer.println("-1 RENDERER*STAGE SHOW 0.0 \0");
				}
				break;
			}
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-POWERPLAY_DIRECTOR":
				if(powerplay_on_screen) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayOut START \0");
					powerplay_on_screen = false;
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START \0");
					powerplay_on_screen = true;
				}
				break;
			case "POPULATE-SIXDIRECTOR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SixIn START \0");
				which_director_on_screen = "SIX";
				break;
			case "POPULATE-FOURDIRECTOR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FourIn START \0");
				which_director_on_screen = "FOUR";
				break;
			case "POPULATE-WICKETDIRECTOR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*WicketIn START \0");
				which_director_on_screen = "WICKET";
				break;
			case "POPULATE-FREEHITDIRECTOR":
				if(which_director_on_screen.equalsIgnoreCase("FREEHIT")) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitOut START \0");
					which_director_on_screen = "";
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitIn START \0");
					which_director_on_screen = "FREEHIT";
				}
				
				break;
			case "CHANGE_ON-IMPACT":
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Change START \0");
				break;
			case "POPULATE-IMPACT":
				populateImpactPlayer(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]) , match, broadcaster, config);
				break;
			case "POPULATE-PLAYOFFS":
				populatePlayoffs(print_writer, valueToProcess.split(",")[0], cricketService.getPlayOff(),cricketService.getTeams(), broadcaster, match);
				break;
			case "POPULATE-FF-FIX_AND_RESULT":
				populateFixturesAndResult(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(), cricketService.getFixtures(), broadcaster, match);
				break;
			case "POPULATE-MOST_RUNS":
				populateMostRunsTeam(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA",false, head_to_head, cricketService, match,past_tournament_stats),
						cricketService.getTeams(),match, broadcaster, config);
				break;
			case "POPULATE-NEXT_TO_BAT":
				populateLTNextToBat(print_writer,valueToProcess.split(",")[0],cricketService.getAllStats(),cricketService.getAllPlayer(),match, broadcaster, config);
				break;
			case "POPULATE-BUGPARTNERSHIP":
				populateBugPartnership(print_writer, valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-BUG-TOSS":
				populateBugToss(print_writer,valueToProcess.split(",")[0],match,broadcaster);
				break;
			case "POPULATE-MULTI_PARTNERSHIP":
				populateBugMultipartnership(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
				break;
			case "POPULATE-LT-BUG_HIGHLIGHT":
				populateBugHighlight(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-BUG_POWERPLAY":
				populateBugPowerPLay(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-FF-LEADERBOARD":
				populateLeaderBoard(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]),
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster, config);
				break;
			case "POPULATE-FF-STATS":
				populateFFstats(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster);
				break;
				
			case "POPULATE-BAT_PERFORMER":
				populateBatPerformer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster, config);
				bcf.setType(valueToProcess.split(",")[2]);
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER"||which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || 
						which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE" || which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP") {	
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$BattingCard*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 BowlingCardIn 0.0 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 SummaryIn 0.0 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PointsTableIn 0.0 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PartnershipAllIn 0.0 PartnershipAllIn$PartnershipOffsetIn 0.0 PartnershipAllIn$DataIn 0.0 PartnershipAllIn$ManDataIn 0.0 \0");
					}
				}
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "POPULATE-BALL_PERFORMER":
				populateBallPerformer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster, config);
				bocf.setType(valueToProcess.split(",")[2]);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE" || which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP") {	
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$BowlingCard*ACTIVE SET " + "0" + "\0");
					  if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 BattingCardIn 0.0 \0");
					  }else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 SummaryIn 0.0 \0");
					  }else if(which_graphic_on_screen == "POINTSTABLE") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 PointsTableIn 0.0 \0");
					  }else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PartnershipAllIn 0.0 PartnershipAllIn$PartnershipOffsetIn 0.0 PartnershipAllIn$DataIn 0.0 PartnershipAllIn$ManDataIn 0.0 \0");
					  }
				}
				TimeUnit.MILLISECONDS.sleep(500);
				break;
				
			case "POPULATE-FF-SCORECARD":
				populateScorecard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, cricketService, broadcaster);
				
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 BowlingCardIn 0.0 BowlingCardOut 0.500 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 SummaryIn 0.0 SummaryOut 0.500 \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PointsTableIn 0.0 PointsTableOut 0.500 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP") {
					  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PartnershipAllIn 0.0 PartnershipAllIn$PartnershipOffsetIn 0.0 PartnershipAllIn$DataIn 0.0 PartnershipAllIn$ManDataIn 0.0 PartnershipAllOut 0.500 \0");
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 \0");
				}
				break;
				
			case "POPULATE-FF-BOWLINGCARD":
				populateBowlingcard(print_writer, valueToProcess.split(",")[0], false, Integer.valueOf(valueToProcess.split(",")[1]), match, cricketService, broadcaster);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 0.0 BattingCardOut 0.500 "
							+ "BowlingCardIn 1.763 BallOffsetIn 1.363 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 0.0 SummaryOut 0.500 "
							+ "BowlingCardIn 1.763 BallOffsetIn 1.363 \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PointsTableIn 0.0 PointsTableOut 0.500 "
							+ "BowlingCardIn 1.763 BallOffsetIn 1.363 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP") {
					  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.363 BallOffsetIn 1.363 PartnershipAllIn 0.0 PartnershipAllIn$PartnershipOffsetIn 0.0 PartnershipAllIn$DataIn 0.0 PartnershipAllIn$ManDataIn 0.0 PartnershipAllOut 0.500 \0");
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.363 BallOffsetIn 1.363 \0");
				}
				
				break;
			case "POPULATE-MINI-BATSMAN_VS_ALLBOWLERS": 
				populateBatsmanVsAllBowlers(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), match, cricketService, broadcaster);
				break;
			case "POPULATE-MINI-BOWLER_VS_ALLBATSMAN":
				populateBowlerVsAllBatsman(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), match, cricketService, broadcaster);
				break;
			case "POPULATE-FF-BATGRIFF": case "POPULATE-FF-BALLGRIFF":
				populateGriff(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]) ,valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]),cricketService, head_to_head, match, broadcaster);
				break;
			case "POPULATE-MINI-BATTINGCARD":
				populateMiniBattingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-MINI-BOWLINGCARD":
				populateMiniBowlingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			
			case "POPULATE-FF-PARTNERSHIP":
				populatePartnership(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), match, cricketService, broadcaster);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 "
							+ "PartnershipAllIn 1.830 PartnershipAllIn$PartnershipOffsetIn 1.830 PartnershipAllIn$DataIn 1.640 PartnershipAllIn$BallOffsetIn 1.830 PartnershipAllIn$ManDataIn 0.931 BattingCardIn 0.0 BattingCardOut 0.500 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 "
							+ "PartnershipAllIn 1.830 PartnershipAllIn$PartnershipOffsetIn 1.830 PartnershipAllIn$DataIn 1.640 PartnershipAllIn$BallOffsetIn 1.830 PartnershipAllIn$ManDataIn 0.931 SummaryIn 0.0 SummaryOut 0.500 \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 "
							+ "PartnershipAllIn 1.830 PartnershipAllIn$PartnershipOffsetIn 1.830 PartnershipAllIn$DataIn 1.640 PartnershipAllIn$BallOffsetIn 1.830 PartnershipAllIn$ManDataIn 0.931 PointsTableIn 0.0 PointsTableOut 0.500 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 "
					  		+ "PartnershipAllIn 1.830 PartnershipAllIn$PartnershipOffsetIn 1.830 PartnershipAllIn$DataIn 1.640 PartnershipAllIn$BallOffsetIn 1.830 PartnershipAllIn$ManDataIn 0.931 BowlingCardIn 0.0 BowlingCardOut 0.500 \0");
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PartnershipAllIn 1.830 "
							+ "PartnershipAllIn$PartnershipOffsetIn 1.830 PartnershipAllIn$DataIn 1.640 PartnershipAllIn$BallOffsetIn 1.830 PartnershipAllIn$ManDataIn 0.931 \0");
				}
				break;
			
			case "POPULATE-FF_SUMMARY_GRAPHICS":
				populateFFSummary(print_writer, valueToProcess.split(",")[0], valueToProcess.substring(valueToProcess.lastIndexOf(",")+1), cricketService.getAllPlayer(), 
						broadcaster);
				break;
				
			case "POPULATE-FF-MATCHSUMMARY":
				populateMatchsummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getVariousTexts(),
						match, broadcaster);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 BattingCardIn 0.0 BattingCardOut 0.500 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 BowlingCardIn 0.0 BowlingCardOut 0.500 \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 PointsTableIn 0.0 PointsTableOut 0.500 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP") {
					  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.363 SummaryOffsetIn 1.363 PartnershipAllIn 0.0 PartnershipAllIn$PartnershipOffsetIn 0.0 PartnershipAllIn$DataIn 0.0 PartnershipAllIn$ManDataIn 0.0  PartnershipAllOut 0.500 \0");
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 \0");
				}
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
				for(Bugs bug : cricketService.getBugs()) {
					  if(bug.getBugId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populateBugsDB(print_writer, valueToProcess.split(",")[0], bug, match, broadcaster);
					  }
					}
					break;
			case "POPULATE-L3-POINTERS":
				for(Pointers point : cricketService.getPointers()) {
					  if(point.getPointersId() == Integer.valueOf(valueToProcess.split(",")[1])) {
						  populatePointers(print_writer, valueToProcess.split(",")[0], point, match, broadcaster);
					  }
					}
					break;
			case "POPULATE-L3-HOWOUT":
				populateHowout(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-HOWOUT_QUICK":
				populateHowoutquick(print_writer, valueToProcess.split(",")[0],match, broadcaster);
				break;
			case "POPULATE-FF-SCHEDULE":
				populateSchedule(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1] , cricketService.getFixtures(),cricketService.getTeams(), cricketService.getVariousTexts(),match, broadcaster);
				break;
			case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER":
				populateHowoutWithoutFielder(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-NAMESUPER":
				for(NameSuper ns : cricketService.getNameSupers()) {
				  if(ns.getNamesuperId() == Integer.valueOf(valueToProcess.split(",")[1])) {
					  populateNameSuper(print_writer, valueToProcess.split(",")[0], ns, match, broadcaster);
				  }
				}
				break;
			case "POPULATE-L3-FALLOFWICKET":
				populateFallofWicket(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-L3-TARGET":
				populateTarget(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-LT-POWERPLAY":
				populateLtPowerPlay(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-BUGTARGET":
				populateBugTarget(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-L3-CAPTAIN-PLAYER":
				populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], 
						Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(), match, broadcaster);
				break;
			case "POPULATE-FF-MATCHID":
				populateMatchId(print_writer,valueToProcess.split(",")[0], match, cricketService.getVariousTexts(), broadcaster);
				break;
			case "POPULATE-MATCH_PROMO":
				populateMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
						cricketService.getFixtures(),match,cricketService.getVariousTexts() , broadcaster);
				break;
			case "POPULATE-L3MATCH_PROMO":
				populateLtMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
						cricketService.getFixtures(),match , broadcaster);
				break;
			case "POPULATE-LT-MATCHID":
				populateLTMatchId(print_writer,valueToProcess.split(",")[0], match, cricketService.getVariousTexts(), broadcaster);
				break;
			case "POPULATE-FF-TEAMS_LOGO":
				populateTeamsLogo(print_writer, valueToProcess.split(",")[0],cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-L3-COMPARISION":
				populateComparision(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-LT-PARTNERSHIP":
				populateLTPartnership(print_writer, valueToProcess.split(",")[0], match, broadcaster, config);
				break;
			case "POPULATE-L3-SPLIT":
				populateSplit(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
				break;
			case "POPULATE-L3-BATSMANSTATS":
				populateBatsmanstats(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster);
				break;
			case "POPULATE-L3-BOWLERSTATS":
				populateBowlerstats(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]), cricketService.getTeams(), match, broadcaster);
				break;
			case "POPULATE-L3-PLAYERSUMMARY":
				populateLtBattingSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-BATSMAN_THIS_MATCH":
				populateLtBatsmanThisMatch(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-BOWLER_THIS_MATCH":
				populateLtBowlerThisMatch(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-BOWLERDETAILS":
				populateLtBowlerSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-BOWLERSUMMARY":
				populateLtBowlerDetails(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
						match, broadcaster);
				break;
			case "POPULATE-L3-TEAMSUMMARY":
				populateTeamSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),match, broadcaster);
				break;
			case "POPULATE-PHASE":
				populatePhaseWise(print_writer,valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-LT-PROJECTED":
				populateProjectedScore(print_writer,valueToProcess, match, broadcaster);
				break;
			case "POPULATE-L3-THISSERIES": case "POPULATE-L3-THISSERIES_BALL":
				Statistics statsSeason1LT = null, statsSeason2LT = null;
				int countLT = 0;
				if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("aplcareer")) {
					for (Statistics stats : cricketService.getAllStats()) {
						if(stats.getStats_type_id() == 9 || stats.getStats_type_id() == 10) {
							if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
								if(stats.getStats_type_id() == 9) {
									countLT++;
									statsSeason1LT = stats;
								}
								if(stats.getStats_type_id() == 10) {
									countLT++;
									statsSeason2LT = stats;
								}

								if(statsSeason1LT == null && countLT == 1) {
									stats.setMatches(statsSeason2LT.getMatches());
									stats.setRuns(statsSeason2LT.getRuns());
									stats.setBalls_faced(statsSeason2LT.getBalls_faced());
									stats.setWickets(statsSeason2LT.getWickets());
									stats.setRuns_conceded(statsSeason2LT.getRuns_conceded());
									stats.setBalls_bowled(statsSeason2LT.getBalls_bowled());
									stats.setBest_score(statsSeason2LT.getBest_score());
									stats.setBest_figures(statsSeason2LT.getBest_figures());
								}else if(statsSeason1LT != null && statsSeason2LT != null && countLT == 2){
									int bestSeason1 = 0, bestSeason2 = 0, bestFigSeason1wkt = 0, bestFigSeason2wkt = 0, bestFigSeason1Runs = 0,bestFigSeason2Runs = 0;
									boolean season1Notout = false, season2Notout = false;
									stats.setMatches(statsSeason1LT.getMatches()+statsSeason2LT.getMatches());
									stats.setRuns(statsSeason1LT.getRuns()+statsSeason2LT.getRuns());
									stats.setBalls_faced(statsSeason1LT.getBalls_faced()+statsSeason2LT.getBalls_faced());
									stats.setWickets(statsSeason1LT.getWickets()+statsSeason2LT.getWickets());
									stats.setRuns_conceded(statsSeason1LT.getRuns_conceded()+statsSeason2LT.getRuns_conceded());
									stats.setBalls_bowled(statsSeason1LT.getBalls_bowled()+statsSeason2LT.getBalls_bowled());
									if(statsSeason1LT.getBest_score().contains("*")) {
										bestSeason1 = Integer.valueOf(statsSeason1LT.getBest_score().replace("*", ""));
										season1Notout = true;
									}else {
										bestSeason1 = Integer.valueOf(statsSeason1LT.getBest_score());
									}
									if(statsSeason2LT.getBest_score().contains("*")) {
										bestSeason2 = Integer.valueOf(statsSeason2LT.getBest_score().replace("*", ""));
										season2Notout = true;
									}else {
										bestSeason2 = Integer.valueOf(statsSeason2LT.getBest_score());
									}
									
									if(statsSeason1LT.getBest_figures().contains("-")) {
										bestFigSeason1wkt = Integer.valueOf(statsSeason1LT.getBest_figures().split("-")[0]);
										bestFigSeason1Runs = Integer.valueOf(statsSeason1LT.getBest_figures().split("-")[1]);
									}
									if(statsSeason2LT.getBest_figures().contains("-")) {
										bestFigSeason2wkt = Integer.valueOf(statsSeason2LT.getBest_figures().split("-")[0]);
										bestFigSeason2Runs = Integer.valueOf(statsSeason2LT.getBest_figures().split("-")[1]);
									}
									if(bestFigSeason1wkt>bestFigSeason2wkt) {
										stats.setBest_figures((bestFigSeason1wkt+"-"+bestFigSeason1Runs));
									}else {
										stats.setBest_figures((bestFigSeason2wkt+"-"+bestFigSeason2Runs));
									}
									
									if(bestSeason1>bestSeason2) {
										if(season1Notout) {
											stats.setBest_score(bestSeason1+"*");
										}else {
											stats.setBest_score(String.valueOf(bestSeason1));
										}
									}else {
										if(season2Notout) {
											stats.setBest_score(bestSeason2+"*");
										}else {
											stats.setBest_score(String.valueOf(bestSeason2));
										}
									}
								}
								
								if(statsSeason1LT == null && countLT == 1 || statsSeason1LT != null && statsSeason2LT != null && countLT == 2) {
									stats = CricketFunctions.updateH2h(stats, head_to_head, match, CricketUtil.FULL);
									stats = CricketFunctions.updateMatchData(stats, match, CricketUtil.FULL);
									switch (whatToProcess.toUpperCase()) {
									case "POPULATE-L3-THISSERIES":
										
										populateThisSeriesBat(print_writer, valueToProcess.split(",")[0],
												Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
												CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, null),
												match, broadcaster,stats, config);
										break;

									case "POPULATE-L3-THISSERIES_BALL":
										populateThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
												Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
												CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, null),
												match, broadcaster,stats, config);
										break;
									}
								}
							}
						}
					}
				}else if(valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("aplseason2")) {
					for (Statistics stats : cricketService.getAllStats()) {
						stats.setStats_type(cricketService.getStatsType(10));
						if (stats.getStats_type_id() == 10) {
							if (stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
								switch (whatToProcess.toUpperCase()) {
								case "POPULATE-L3-THISSERIES":
									populateThisSeriesBat(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													head_to_head, cricketService, match, null), match, broadcaster,stats, config);
									break;

								case "POPULATE-L3-THISSERIES_BALL":
									populateThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													head_to_head, cricketService, match, null),match, broadcaster,stats, config);
									break;
								}
								
							}
						}
					}
				} else if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("aplseason1")) {
					for (Statistics stats : cricketService.getAllStats()) {
						stats.setStats_type(cricketService.getStatsType(9));
						if (stats.getStats_type_id() == 9) {
							if (stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
								switch (whatToProcess.toUpperCase()) {
								case "POPULATE-L3-THISSERIES":
									populateThisSeriesBat(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													head_to_head, cricketService, match, null), match, broadcaster,stats, config);
									break;

								case "POPULATE-L3-THISSERIES_BALL":
									populateThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													head_to_head, cricketService, match, null), match, broadcaster,stats, config);
									break;
								}
								
							}
						}
					}
				} else if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("THISSERIES")) {
//					Statistics stats =null;
					switch (whatToProcess.toUpperCase()) {
					case "POPULATE-L3-THISSERIES":
						populateThisSeriesBat(print_writer, valueToProcess.split(",")[0],
								Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
								CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false,
										head_to_head, cricketService, match, past_tournament_stats), match, broadcaster,null, config);
						break;

					case "POPULATE-L3-THISSERIES_BALL":
//						for(Tournament past : past_tournament_stats) {
//							System.out.println(past.getPlayerId() +" : "+past.getMatches()+" : "+past.getWickets());
//						}
						populateThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
								Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
								CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false,
										head_to_head, cricketService, match, past_tournament_stats), match, broadcaster,null, config);
						break;
					}
				}
			break;
				
			/*case "POPULATE-L3-THISSERIES":
				populateThisSeriesBat(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
						,match, broadcaster);
				break;
			case "POPULATE-L3-THISSERIES_BALL":
				populateThisSeriesBowl(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
						,match, broadcaster);
				break;*/
			case "POPULATE-FF-THISSERIES": case "POPULATE-FF-THISSERIES_BALL":
				Statistics statsSeason1 = null, statsSeason2 = null;
				int count = 0;
						
				if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("aplcareer")) {
					for (Statistics stats : cricketService.getAllStats()) {
						if(stats.getStats_type_id() == 9 || stats.getStats_type_id() == 10) {
							if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
								if(stats.getStats_type_id() == 9) {
									count++;
									statsSeason1 = stats;
								}
								if(stats.getStats_type_id() == 10) {
									count++;
									statsSeason2 = stats;
								}
								if(statsSeason1 == null && count == 1) {
									stats.setMatches(statsSeason1.getMatches());
									stats.setRuns(statsSeason1.getRuns());
									stats.setBalls_faced(statsSeason1.getBalls_faced());
									stats.setWickets(statsSeason1.getWickets());
									stats.setRuns_conceded(statsSeason1.getRuns_conceded());
									stats.setBalls_bowled(statsSeason1.getBalls_bowled());
								}else if(statsSeason1 != null && statsSeason2 != null && count == 2){
									stats.setMatches(statsSeason1.getMatches()+statsSeason2.getMatches());
									stats.setRuns(statsSeason1.getRuns()+statsSeason2.getRuns());
									stats.setBalls_faced(statsSeason1.getBalls_faced()+statsSeason2.getBalls_faced());
									stats.setWickets(statsSeason1.getWickets()+statsSeason2.getWickets());
									stats.setRuns_conceded(statsSeason1.getRuns_conceded()+statsSeason2.getRuns_conceded());
									stats.setBalls_bowled(statsSeason1.getBalls_bowled()+statsSeason2.getBalls_bowled());
								}
								if(statsSeason1 == null && count == 1 || statsSeason1 != null && statsSeason2 != null && count == 2) {
									stats = CricketFunctions.updateH2h(stats, head_to_head, match, CricketUtil.FULL);
									stats = CricketFunctions.updateMatchData(stats, match, CricketUtil.FULL);
									switch (whatToProcess.toUpperCase()) {
									case "POPULATE-FF-THISSERIES":
										populateFFThisSeriesBat(print_writer, valueToProcess.split(",")[0],
												Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
												CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, null),
												match, broadcaster,stats, config);
										break;

									case "POPULATE-FF-THISSERIES_BALL":
										populateFFThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
												Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
												CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, null),
												match, broadcaster,stats, config);
										break;
									}
								}
							}
						}
						
//						if (stats.getStats_type_id() == 9) {
//							if (stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
//								stats = CricketFunctions.updateH2h(stats, head_to_head, match);
//								stats = CricketFunctions.updateMatchData(stats, match);
//								switch (whatToProcess.toUpperCase()) {
//								case "POPULATE-FF-THISSERIES":
//									populateFFThisSeriesBat(print_writer, valueToProcess.split(",")[0],
//											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
//											CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, null),
//											match, broadcaster,stats, config);
//									break;
//
//								case "POPULATE-FF-THISSERIES_BALL":
//									populateFFThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
//											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
//											CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, head_to_head,cricketService, match, null),
//											match, broadcaster,stats, config);
//									break;
//								}
//								
//							}
//						}
					}
				}else if(valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("aplseason2")) {
					for (Statistics stats : cricketService.getAllStats()) {
						stats.setStats_type(cricketService.getStatsType(10));
						if (stats.getStats_type_id() == 10) {
							if (stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
								switch (whatToProcess.toUpperCase()) {
								case "POPULATE-FF-THISSERIES":
									populateFFThisSeriesBat(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													head_to_head, cricketService, match, null),
											match, broadcaster,stats, config);
									break;

								case "POPULATE-FF-THISSERIES_BALL":
									populateFFThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													head_to_head, cricketService, match, null),
											match, broadcaster,stats, config);
									break;
								}
								
							}
						}
					}
				} else if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("aplseason1")) {
					for (Statistics stats : cricketService.getAllStats()) {
						stats.setStats_type(cricketService.getStatsType(9));
						if (stats.getStats_type_id() == 9) {
							if (stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
								switch (whatToProcess.toUpperCase()) {
								case "POPULATE-FF-THISSERIES":
									populateFFThisSeriesBat(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													head_to_head, cricketService, match, null),
											match, broadcaster,stats, config);	
									break;

								case "POPULATE-FF-THISSERIES_BALL":
									populateFFThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													head_to_head, cricketService, match, null),
											match, broadcaster,stats, config);
									break;
								}
								
							}
						}
					}
				} else if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("THISSERIES")) {
					Statistics stats = new Statistics();
					switch (whatToProcess.toUpperCase()) {
					case "POPULATE-FF-THISSERIES":
						populateFFThisSeriesBat(print_writer, valueToProcess.split(",")[0],
								Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
								CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false,
										head_to_head, cricketService, match, past_tournament_stats),
								match, broadcaster,stats, config);
						break;

					case "POPULATE-FF-THISSERIES_BALL":
						populateFFThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
								Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
								CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false,
										head_to_head, cricketService, match, past_tournament_stats),
								match, broadcaster,stats, config);
						break;
					}
				}
				break;
			case "POPULATE-L3-PLAYERPROFILE":
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id().intValue()== Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase("DT20")) {
							stats = CricketFunctions.updateH2h(stats, head_to_head, match, CricketUtil.FULL);
							stats = CricketFunctions.updateMatchData(stats, match, CricketUtil.FULL);
						}
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populateLTPlayerProfile(print_writer,valueToProcess.split(",")[0],
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
						}
					}
				}
				break;
			case "POPULATE-L3-PLAYERPROFILEBAT":
				
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase("DT20")) {
							stats = CricketFunctions.updateH2h(stats, head_to_head, match, CricketUtil.FULL);
							stats = CricketFunctions.updateMatchData(stats, match, CricketUtil.FULL);
						}
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populateLTPlayerProfileBat(print_writer,valueToProcess.split(",")[0],
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYERPROFILE":
				System.out.println(valueToProcess);
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase("DT20")) {
							stats = CricketFunctions.updateH2h(stats, head_to_head, match, CricketUtil.FULL);
							stats = CricketFunctions.updateMatchData(stats, match, CricketUtil.FULL);
						}
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,cricketService.getAllPlayer(),match, broadcaster, config);
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYERPROFILEBALL":
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase("DT20")) {
							stats = CricketFunctions.updateH2h(stats, head_to_head, match, CricketUtil.FULL);
							stats = CricketFunctions.updateMatchData(stats, match, CricketUtil.FULL);
						}
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populatePlayerProfileBall(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,cricketService.getAllPlayer(),match, broadcaster, config);
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYINGXI_SEQUENCE":
				populatePlayingXISequence(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						match, broadcaster, config);
				break;
			case "POPULATE-FF-PLAYINGXI":
				populatePlayingXI(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						match, broadcaster, config);
				break;
			case "POPULATE-FF-CAPTAINS":
				populateCaptains(print_writer,valueToProcess, cricketService.getTeams(),match, broadcaster, config);
				break;
			case "POPULATE-FF-TOP_PERFORMER":
				populateTopPerformer(print_writer, valueToProcess, cricketService.getPerformer(), cricketService.getAllPlayer(), 
						cricketService.getTeams(),cricketService.getVariousTexts(), match, broadcaster, config);
				break;
			case "POPULATE-FF-TEAM_SQUAD":
				populateSquad(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						match, broadcaster);
				break;
			case "POPULATE-FF-DOUBLETEAMS":
				populateDoubleteams(print_writer,valueToProcess, match, broadcaster);
				break;
			case "POPULATE-FF-LANDMARK": case "POPULATE-FF-LANDMARK_BALL":
				populateLandMark(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster, config);
				break;
			case "POPULATE-LT-EQUATION":
				populateLtEquation(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-FF-POSITION_LANDMARK":
				populateFFLandMark(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster, config);
				break;
			case "POPULATE-POINTS_TABLE":
				LeagueTable league_table = null;
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
					league_table = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
							new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
				}
				
				populatePointsTable(print_writer, valueToProcess.split(",")[0],league_table.getLeagueTeams(),broadcaster,match,cricketService.getVariousTexts());
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsOffsetIn 1.330 BattingCardIn 0.0 BattingCardOut 0.500 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsOffsetIn 1.330 BowlingCardIn 0.0 BowlingCardOut 0.500 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsOffsetIn 1.330 SummaryIn 0.0 SummaryOut 0.500 \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_PARTNERSHIP") {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsOffsetIn 1.330 PartnershipAllIn 0.0 PartnershipAllIn$PartnershipOffsetIn 0.0 PartnershipAllIn$DataIn 0.0 PartnershipAllIn$ManDataIn 0.0 PartnershipAllOut 0.500 \0");
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsOffsetIn 1.330 \0");
				}
				break;
			case "POPULATE-LTPOINTS_TABLE":
				LeagueTable league_table_mini = null;
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
					league_table_mini = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
							new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
				}
//				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
//					//league_table = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
//							//new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
//				}
				populateLtPointsTable(print_writer, valueToProcess.split(",")[0], league_table_mini.getLeagueTeams(), cricketService.getTeams(),match,broadcaster);
				break;
			case "POPULATE-BOWLER_STYLE":
				populateBowlerStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(), cricketService.getTeams(), cricketService.getGrounds(), match, broadcaster);
				break;
			case "POPULATE-BATSMAN_STYLE":
				populateBatsmanStyle(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						Integer.valueOf(valueToProcess.split(",")[2]), cricketService.getAllPlayer(), cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-MANHATTAN":
				populateManhattan(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-WORM":
				populateWorm(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-PREVIOUS_SUMMARY":
				MatchAllData cricket_matches = new MatchAllData();
				for(Fixture fx : cricketService.getFixtures()) {
					if(fx.getMatchnumber() == Integer.valueOf(valueToProcess.split(",")[1])) {
						
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + fx.getMatchfilename() + ".json").exists()) {
							
							cricket_matches.setSetup(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + 
									fx.getMatchfilename() + ".json"), Setup.class));
							cricket_matches.setMatch(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + 
									fx.getMatchfilename() + ".json"), Match.class));
						}
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + fx.getMatchfilename() + ".json").exists()) {
							cricket_matches.setEventFile(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + 
									fx.getMatchfilename() + ".json"), EventFile.class));
						}
//						cricket_matches = CricketFunctions.populateMatchVariables(cricketService, CricketFunctions.readOrSaveMatchFile(CricketUtil.READ,
//								CricketUtil.SETUP + "," + CricketUtil.MATCH, cricket_matches,true));	
					}
				}
				populatePreviousSummary(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]), cricketService.getVariousTexts(),
						cricket_matches,cricketService.getFixtures(), match, broadcaster);
				
				break;
			case "POPULATE-TIEID-DOUBLE":
				populateTieIdDouble(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1],cricketService.getFixtures(),cricketService.getTeams(), 
						match, broadcaster);
				break;
			case "POPULATE-L3-INFOBAR":
				
				infobar.setMiddle_section(valueToProcess.split(",")[1]);
				infobar.setBottom_right_top_section(valueToProcess.split(",")[2]);
				infobar.setBottom_right_bottom_section(valueToProcess.split(",")[3]);
				
				populateInfobar(infobar, print_writer, valueToProcess.split(",")[0],match, broadcaster);
				
				switch(infobar.getBottom_right_bottom_section().toUpperCase()){
				case CricketUtil.OVER:
					processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
					break;
				case "ECONOMY":
					processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
					break;
				case "BOWLINGEND": case "LASTOVERRUNS":
					processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
					break;
				}
				infobar.setIdent_section("");
				break;
				//return infobar;
				
			case "POPULATE-INFOBAR-IDENT":
				infobar.setIdent_section(valueToProcess.split(",")[1]);
				populateInfobarIdent(infobar, false, valueToProcess.split(",")[0],print_writer, match, broadcaster);
				//return infobar;
				break;
			case "POPULATE-COMMENTATORS":
				infobar.setTop_section("STATISTICS");
				System.out.println("COMM : "+valueToProcess);
				populateInfobarCommentators(infobar,false,print_writer, Integer.valueOf(valueToProcess.split(",")[0]),Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]), cricketService
						, broadcaster);
                processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseIn", "START", broadcaster);
                processAnimation(print_writer, "Section2$FreeTextSmallIn", "START", broadcaster);
				break;
			case "POPULATE-INFOBAR-LAST_X_BALLS":
				infobar.setTop_section("STATISTICS");
				populateInfobarLastXBalls(infobar,false,print_writer, Integer.valueOf(valueToProcess), match, broadcaster);
                processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseIn", "START", broadcaster);
                processAnimation(print_writer, "Section2$FreeTextSmallIn", "START", broadcaster);
				break;
			case "POPULATE-INFOBAR-FREE_TEXT":
				infobar.setTop_section("STATISTICS");
				 populateInfobarFreeTextInput(infobar,false,print_writer, valueToProcess, match, broadcaster);
                 processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseIn", "START", broadcaster);
                 processAnimation(print_writer, "Section2$FreeTextSmallIn", "START", broadcaster);
				break;
			case "POPULATE-INFOBAR-PROMPT":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						for(InfobarStats ibs : cricketService.getInfobarStats())
	                          if(ibs.getOrder() == Integer.valueOf(valueToProcess)) {
	                        	  infobar.setTop_section("STATISTICS");
	                              populateInfobarFreeText(infobar,false,print_writer, ibs, match, broadcaster);
	                              processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseIn", "START", broadcaster);
	                              processAnimation(print_writer, "Section2$FreeTextSmallIn", "START", broadcaster);
	                          }
	                    }
					}
				}
				break;
					
			case "POPULATE-DIRECTOR":
					populateInfobarDirector(print_writer,valueToProcess,broadcaster);
					break;
			case "POPULATE-INFOBAR_IDENT_DATA":
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					if(infobar.getIdent_section() != null) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoOut START \0");
						TimeUnit.MILLISECONDS.sleep(100);
						infobar.setIdent_section(valueToProcess);
						populateInfobarIdent(infobar, false, valueToProcess,print_writer, match, broadcaster);
						TimeUnit.MILLISECONDS.sleep(100);
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoIn START \0");
					}
				}
				break;

			case "POPULATE-INFOBAR-BOTTOMRIGHT":
				if(infobar.getLast_bottom_right_section() != null 
					&& !infobar.getLast_bottom_right_section().trim().isEmpty()) { // Full section to 2 section change on

					infobar.setBottom_right_bottom_section(valueToProcess);
					infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
					processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
					processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
					processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
					infobar.setBottom_right_top_section(CricketUtil.BOWLER);
					infobar = populateVizInfobarRightTop(infobar, false,print_writer, match, broadcaster);

					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;
					case "TOURNAMENT_FOURS":
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;	
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case "TOURNAMENT_SIXES":
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonOut", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(200);
					processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
					processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
					switch(infobar.getBottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
						break;
					case "BOWLINGEND": case "LASTOVERRUNS": case "THISOVERRUNS":
						processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						break;
					}
				} else if(infobar.getLast_bottom_right_top_section() != null && infobar.getLast_bottom_right_bottom_section() != null 
						&& !infobar.getLast_bottom_right_top_section().trim().isEmpty() 
						&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // Normal change on

					switch(infobar.getLast_bottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
						break;
					case "BOWLINGEND": case "LASTOVERRUNS": case "THISOVERRUNS":
						processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setBottom_right_bottom_section(valueToProcess);
					infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
					switch(infobar.getBottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
						break;
					case "BOWLINGEND": case "LASTOVERRUNS": case "THISOVERRUNS":
						processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						break;
					}
				}
				infobar.setBottom_right_section("");infobar.setLast_bottom_right_section("");
				break;
			
			case "POPULATE-INFOBAR-RIGHT":
				if(infobar.getLast_bottom_right_section() != null 
					&& !infobar.getLast_bottom_right_section().trim().isEmpty()) { // Normal change on
					
					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;
					case "TOURNAMENT_FOURS":
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;	
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case "TOURNAMENT_SIXES":
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonOut", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					}

					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setBottom_right_section(valueToProcess);
					infobar = populateVizInfobarRight(infobar, false,print_writer,tournament_matches, match, broadcaster);
					
					switch (infobar.getBottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallIn", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;
					case "TOURNAMENT_FOURS":
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case "TOURNAMENT_SIXES":
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonIn", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					}
					
				} else if(infobar.getLast_bottom_right_top_section() != null && infobar.getLast_bottom_right_bottom_section() != null 
						&& !infobar.getLast_bottom_right_top_section().trim().isEmpty() 
						&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // 2 section to full section change on

					infobar.setBottom_right_section(valueToProcess);
					infobar = populateVizInfobarRight(infobar, false,print_writer,tournament_matches, match, broadcaster);
					
					processAnimation(print_writer, "ALL_SECTION$Section4Out", "START", broadcaster);
					processAnimation(print_writer, "ALL_SECTION$Section5Out", "START", broadcaster);
					TimeUnit.SECONDS.sleep(1);

					switch (infobar.getBottom_right_section().toUpperCase()) {
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallIn", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;
					case "TOURNAMENT_FOURS":
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;	
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case "TOURNAMENT_SIXES":
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonIn", "START", broadcaster);
						break;
					case "TARGET_2":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					}
					
					processAnimation(print_writer, "ALL_SECTION$Section5In", "SHOW 0.0", broadcaster);
				}
				infobar.setBottom_right_top_section("");infobar.setLast_bottom_right_top_section("");
				infobar.setBottom_right_bottom_section("");infobar.setLast_bottom_right_bottom_section("");
				break;
				
			case "POPULATE-INFOBAR-TOP": 
				if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
					switch(infobar.getLast_top_section().toUpperCase()) {
					case CricketUtil.TOSS:
						processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
						break;
					case "CRR":
						processAnimation(print_writer, "Section2$CurRunRateOut", "START", broadcaster);
						break;
					case "CRR_RRR":
						processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
						break;
					case "FIRST_INNING_SCORE":
						processAnimation(print_writer, "Section2$FistInnScoreOut", "START", broadcaster);
						break;
					case CricketUtil.BOUNDARY:
						processAnimation(print_writer, "Section2$BallsSinceLastBoundaryOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section2$DLSTargetOut", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section2$ExtrasOut", "START", broadcaster);
						break;
					case "EQUATION":
						processAnimation(print_writer, "Section2$EquationOut", "START", broadcaster);
						break;
					case "PROJECTED":
						processAnimation(print_writer, "Section2$ProjectedOut", "START", broadcaster);
						break;
					case "BOUNDARIES":
						processAnimation(print_writer, "Section2$BoundariesOut", "START", broadcaster);
						break;
					case "PARTNERSHIP":
						processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
						break;
					case "LAST_WICKET":
						processAnimation(print_writer, "Section2$LastWicketOut", "START", broadcaster);
						break;
					case CricketUtil.TIMELINE:
						processAnimation(print_writer, "Section2$TimelineOut", "START", broadcaster);
						break;
					case "STATISTICS":
						processAnimation(print_writer, "Section2$FreeTextSmallOut", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setTop_section(valueToProcess);
					populateVizInfobarTop(infobar, false, print_writer, match, broadcaster);
				}else {
					infobar.setTop_section(valueToProcess);
					processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseIn", "START", broadcaster);
					populateVizInfobarTop(infobar, false, print_writer, match, broadcaster);
				}
				break;
			}
			//return JSONObject.fromObject(this_doad).toString();			
			return null;
	}

	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException
	{
		
		switch(whichGraphic) {
		case "RESET":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
			break;
		case "SECTION4":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section4In START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section5In START \0");
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainIn START \0");
			break;
		case "SCORECARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Offset START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "BOWLINGCARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Offset START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "MATCHSUMMARY": case "PREVIOUS_SUMMARY": case "FF_SUMMARY_GRAPHICS":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Offset START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "POINTSTABLE":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Offset START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableIn START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "PARTNERSHIP":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Offset START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllIn START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "SECTION2":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ALL_SECTION$Section2$Section2BaseIn START \0");
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentIn START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoIn START \0");
			break;
		case "IN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "MAIN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainIn START \0");
			break;
		case "FF_IN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_In START \0");
			break;
		case "LT_IN":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_In START \0");
			isTickerShrinked = true;
			break;
		case "SCORE":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_Out START \0");
			break;
		case "BATSMAN_1_HIGHLIGHT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman1Highlight START \0");
			break;
		case "BATSMAN_2_HIGHLIGHT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman2Highlight START \0");
			break;
		case "LEADERBOARD": case "MOST":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "PLAYINGXI_SEQUENCE":
			if(which_graphic_on_screen.equalsIgnoreCase("PLAYINGXI_SEQUENCE")) {
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*DataOut START \0");
				TimeUnit.MILLISECONDS.sleep(300);
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Images_In START \0");
			}else {
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*DataIn START \0");
			}
			break;
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "HOWOUT_QUICK": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "MATCHID":
		case "L3PLAYERPROFILE": case "LTPLAYERPROFILEBAT": case "FFPLAYERPROFILE":	case "TEAMLINEUP": case "DOUBLETEAMS": case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH":
		case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE": case "BOWLER_STYLE": case "BATSMAN_STYLE": case "MANHATTAN": case "HOWOUT_WITHOUT": case "MOSTRUNS": case "MOSTWICKETS":
		case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM":  case "MATCH_PROMO": case "L3MATCH_PROMO": case "TEAMS_LOGO": case "TIEID-DOUBLE":
		case "SCHEDULE": case "MINI_BATTINGCARD": case "MINI_BOWLINGCARD": case "THISSERIES": case "FF-THISSERIES": case "FF-THISSERIES_BALL": case "FF_STATS": case "PLAYERPROFILEBALL":
		case "PLAYERPROFILEBAT": case "BUG_POWERPLAY": case "BUG_HIGHLIGHT": case "BUG_PARTNERSHIP": case "MULTI_PARTNERSHIP": case "BUG-TOSS": case "CAPTAINS": case "TOP_PERFORMER":
		case "THISSERIES-BALL": case "FIX_AND_RESULT": case "PLAYOFFS": case "IMPACT": case "POINTER": case "BATSMAN_VS_ALLBOWLER": case "BOWLER_VS_ALLBATSMAN": case "PHASE": 
		case "BATGRIFF": case "BALLGRIFF": case "TEAM_SQUAD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Loop START \0");
			break;
		/*case "SCOREBUG":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*MainIn START \0");
			
			break;*/
		}	
	}
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic) {
		switch(whichGraphic) {
		case "BATBALLSUMMARY_SCORECARD": case "BATBALLSUMMARY_SCORECARD_PERFORMER":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut CONTINUE \0");
			break;
		case "BATBALLSUMMARY_BOWLINGCARD": case "BATBALLSUMMARY_BOWLINGCARD_PERFORMER":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut CONTINUE \0");
			break;
		case "BATBALLSUMMARY_MATCHSUMMARY": case "PREVIOUS_SUMMARY": case "FF_SUMMARY_GRAPHICS":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut CONTINUE \0");
			break;
		case "POINTSTABLE": 
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PointsTableOut CONTINUE \0");
			break;
		case "BATBALLSUMMARY_PARTNERSHIP":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut$Data2Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipAllOut$DataOut CONTINUE \0");
			break;
		case "ANIMATE-OUT-INFOBAR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainOut START \0");
			break;
		case "ANIMATE-OUT-IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "DIRECTOR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitOut START \0");
			break;
		case "BATSMAN_1_DEHIGHLIGHT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman1Dehighlight START \0");
			break;
		case "BATSMAN_2_DEHIGHLIGHT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Batsman2Dehighlight START \0");
			break;
		case "FF_OUT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_Out START \0");
			break;
		case "LT_OUT":
			if(isTickerShrinked) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_Out START \0");
				isTickerShrinked = false;
			}
			break;
		case "PLAYINGXI_SEQUENCE":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "HOWOUT_QUICK": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "MATCHID":
		case "L3PLAYERPROFILE": case "LTPLAYERPROFILEBAT": case "FFPLAYERPROFILE":	case "TEAMLINEUP": case "DOUBLETEAMS": case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH":
		case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE": case "BOWLER_STYLE": case "BATSMAN_STYLE": case "MANHATTAN": case "HOWOUT_WITHOUT": case "MOSTRUNS": case "MOSTWICKETS":
		case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM": case "PARTNERSHIP":  case "MATCH_PROMO": case "L3MATCH_PROMO": case "TEAMS_LOGO": case "TIEID-DOUBLE":
		case "SCHEDULE": case "MINI_BATTINGCARD": case "MINI_BOWLINGCARD": case "THISSERIES": case "FF-THISSERIES": case "FF-THISSERIES_BALL": case "LEADERBOARD": case "FF_STATS":
		case "PLAYERPROFILEBALL": case "PLAYERPROFILEBAT": case "BUG_POWERPLAY": case "BUG_HIGHLIGHT": case "BUG_PARTNERSHIP": case "MULTI_PARTNERSHIP": case "BUG-TOSS": case "CAPTAINS": case "TOP_PERFORMER":
		case "THISSERIES-BALL": case "FIX_AND_RESULT": case "PLAYOFFS": case "IMPACT": case "POINTER": case "BATSMAN_VS_ALLBOWLER": case "BOWLER_VS_ALLBATSMAN": case "PHASE": 
		case "BATGRIFF": case "BALLGRIFF": case "MOST": case "TEAM_SQUAD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		}
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_VIZ": case "APL":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*"+ animationName + " " + animationCommand +" \0");
			break;
		case "DOAD_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			
			break;
		}
		
	}
	public String resetInfobarAnimation(PrintWriter print_writer,String which_frame) throws InterruptedException {
		
			switch(which_frame.toUpperCase()) {
			case "FF_FRAME":
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "FF_OUT");
					which_graphic_on_screen = "SCOREBUG";
				}
				break;
			case "LT_FRAME":
				if(!isTickerShrinked) {
					if(infobar.isInfobar_on_screen() == true) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_Out START \0");
//						AnimateOutGraphics(print_writer, "LT_OUT");
					}
				}
				which_graphic_on_screen = "SCOREBUG";
				break;
			}
		return "";
	}
	
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
	
	public void populateBatPerformer(PrintWriter print_writer, String viz_scene, int whichInning,String Type,int player, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
				int row_id = 0, omo_num = 0;
				String cont_name= "";
				
				//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp*ACTIVE SET 0 \0");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp$FirstName"
//								+ "*GEOM*TEXT SET " + " " + "\0");
						if(!which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_SCORECARD")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 0 \0");
						}
						//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 0 \0");
	
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$SubHeader*GEOM*TEXT SET " 
								+ match.getSetup().getMatchIdent() + " - " + match.getSetup().getTournament()	+ "\0");
						
						if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path + 
									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$TeamNameGrp$FirstName"
									+ "*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$TeamNameGrp$LastName"
									+ "*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
									+ match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
									+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
						}
						
						Collections.sort(inn.getBattingCard());
						for (BattingCard bc : inn.getBattingCard()) {
							row_id = row_id + 1;
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatWicketPlayerImpact" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatBallPlayerImpact" + row_id + " SET " + "0" + "\0");
							
							switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.STILL_TO_BAT:
							if(bc.getHowOut() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo$LeftPlayerName*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo$LeftPlayerName$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
								
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
								
//								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
//										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*ACTIVE SET " + "1" + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
										row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "retired hurt" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
										row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
								
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
								
//								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
//										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*ACTIVE SET " + "1" + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
										row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "absent hurt" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
										row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + "$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								
								TimeUnit.MILLISECONDS.sleep(2);
							}
							break;
							default:
		
							switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.OUT:
								omo_num = 1;
								cont_name = "$Dehighlight";
								break;
							case CricketUtil.NOT_OUT:
								omo_num = 2;
								cont_name = "$Highlight";
								break;
							}
							
							if(bc.getHowOut() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
								
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
								
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
									+ "$BatRightDataGrp$BatDetailRow" + row_id + "*ACTIVE SET " + "1" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
									+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*ACTIVE SET " + "1" + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
									"$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
									+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
											bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
													bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
										
										}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
													bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
										
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
													bc.getHowOutPartOne() + " (sub - " + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
												+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
												+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									}
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)){
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
											bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + 
											bc.getHowOutPartTwo() + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED) || bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)){
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.BOWLED)){
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne().replace("(SUB)", "") + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
											
										}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne().replace("(SUB)", "") + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
											
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
													+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
												+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
												+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									}
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase("timed_out")) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "timed out" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");	
								}else {
	
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
											bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + 
											bc.getHowOutPartTwo() + "\0");
								}
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BottomInfoGrp$BottomInfoAll$ExtrasGrp$"
							+ "ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					TimeUnit.MILLISECONDS.sleep(2);
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BottomInfoGrp$BottomInfoAll$OversGrp$"
							+ "OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					TimeUnit.MILLISECONDS.sleep(2);
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BottomInfoGrp$BottomInfoAll$"
							+ "TotalScore*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
					TimeUnit.MILLISECONDS.sleep(2);
				}
			}
				
			switch(Type.toUpperCase()) {
			case "PERFORMER":
				//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");

				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(player == bc.getPlayerId()) {
								
								if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatPerfomerImpact" + " SET " + "1" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatPerfomerImpact" + " SET " + "0" + "\0");
								}
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtraPlayerFirstName1" + " SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtraPlayerLastName1" + " SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtraPlayerFirstName1" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtraPlayerLastName1" + " SET " + bc.getPlayer().getFirstname() + "\0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatValue" + " SET " + bc.getStrikeRate() + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp$PlayerVerticalNameGrp*ACTIVE SET 0 \0");

								for (Player hs : match.getSetup().getHomeSquad()) {
									if(hs.getPlayerId() == player) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + photo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + "\\\\\\\\" + 
													config.getPrimaryIpAddress() + "\\\\" + local_photo_path + inn.getBatting_team().getTeamName4().toUpperCase() + 
													"\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										TimeUnit.MILLISECONDS.sleep(2);
									}
								}
								
								for (Player as : match.getSetup().getAwaySquad()) {
									if(as.getPlayerId() == player) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + photo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + "\\\\\\\\" + 
												config.getPrimaryIpAddress() + "\\\\" + local_photo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + 
													as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										TimeUnit.MILLISECONDS.sleep(2);
									}
								}
							}
						}
					}
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$"
						+ "BatPerformerGrp*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 0 \0");
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 "
						+ "BattingRightCardIn 1.180 BatRightOffsetIn 1.180 BatPerformerIn 0.641 \0");
				
				if(which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_SCORECARD")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 1 \0");
				}
				//print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 BatPerformerIn 0.641 BattingRightCardOut 1.300 BatPartnershipOut 0.400 \0");
				break;
			case "PARTNERSHIP":
				//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPartnershipGrp*ACTIVE SET 1 \0");
				
				for(Inning inn : match.getMatch().getInning()) {
					//if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(inn.getInningNumber() == whichInning) {
							String Left_Batsman ="",Right_Batsman="";
							
							Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getTicker_name();
							Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getTicker_name();
							
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + photo_path + 
										inn.getBatting_team().getTeamName4() + "" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).
										getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\"  +inn.getBatting_team().getTeamName4() + "\\" + 
										inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + "\\\\\\\\" + 
										config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
										inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
							TimeUnit.MILLISECONDS.sleep(4);
							
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage2" + " SET " + photo_path + 
										inn.getBatting_team().getTeamName4() + "" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).
										getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\" +inn.getBatting_team().getTeamName4() + "\\" + 
										inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage2" + " SET " + "\\\\\\\\" + 
										config.getPrimaryIpAddress() +  local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
										inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
							TimeUnit.MILLISECONDS.sleep(4);
							
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatPartnershipImpact1" + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatPartnershipImpact1" + " SET " + "0" + "\0");
							}
							
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatPartnershipImpact2" + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatPartnershipImpact2" + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPartnershipRuns" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPartnershipBalls" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine1" + " SET " + Left_Batsman + " / " + Right_Batsman + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
							
							if(inn.getTotalWickets() == 0) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
							}else if(inn.getTotalWickets() == 1) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
							}else if(inn.getTotalWickets() == 2) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
							}
						}
					//}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 "
						+ "BattingRightCardIn 1.180 BatRightOffsetIn 1.180 BatPartnershipIn 1.000 \0");
				break;
			}
				
			//print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 \0");
			
		}
	}
	public void populateBallPerformer(PrintWriter print_writer,String viz_scene, int whichInning,String Type,int player,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateBowlingcard -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBowlingcard -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$FirstName"
					+ "*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp*ACTIVE SET 0 \0");
			if(!which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_BOWLINGCARD")) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType*FUNCTION*Omo*vis_con SET 0 \0");
			}
			//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$business6*TEXTURE*IMAGE SET "+ "IMAGE*/Default/Nepal_T20/Logos/1XBAT" +" \0");
			//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType*FUNCTION*Omo*vis_con SET 0 \0");

			int row_id = 0, omo_num = 0,len=0;
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$"
							+ "FirstName*GEOM*TEXT SET " + " " + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament() + "\0");
					//TimeUnit.MILLISECONDS.sleep(2);
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$FirstName"
								+ "*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$LastName"
								+ "*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
						//TimeUnit.MILLISECONDS.sleep(2);
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$FirstName"
								+ "*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$LastName"
								+ "*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
						//TimeUnit.MILLISECONDS.sleep(2);
					}
					
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
							len=len+1;
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallDataGrp*FUNCTION*Omo*vis_con SET " + 
									len +"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll"
									+ "$BallRightDataGrp*FUNCTION*Omo*vis_con SET " + len +"\0");
						}
						
						
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
								CricketFunctions.GetTargetData(match).getRemaningBall()  == 0) {
							omo_num = 0;
							cont_name = "$Dehighlight";
						}else {
							switch (boc.getStatus().toUpperCase()) {
							case (CricketUtil.OTHER + CricketUtil.BOWLER):
								omo_num = 0;
								cont_name = "$Dehighlight";
								break;
							case (CricketUtil.LAST + CricketUtil.BOWLER):
								omo_num = 0;
								cont_name = "$Dehighlight";
								break;
							case (CricketUtil.CURRENT + CricketUtil.BOWLER):
								omo_num = 1;
								cont_name = "$Highlight";
								break;
							}
						}
						
							row_id = row_id + 1;
							
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallDataGrp$BallRow" + 
									row_id + "$RowAnimation$BallOmo*FUNCTION*Omo*vis_con SET " + omo_num +"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll"
									+ "$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo*FUNCTION*Omo*vis_con SET " + omo_num +"\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallDataGrp$BallRow" + 
									row_id + "$RowAnimation$BallOmo" + cont_name +"$BallPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallDataGrp$BallRow" + 
									row_id + "$RowAnimation$BallOmo" + cont_name +"$ScoreGrp$Figure*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
									"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallOverValue*GEOM*TEXT SET " + 
									CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							
							if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10) || 
									match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
										"$BallDetailRow0" + "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Dots" + "\0");
								
								if(boc.getDots() < 0) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
											"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
											"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getDots() + "\0");
								}
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
										"$BallDetailRow0" + "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Maidens" + "\0");
								
								if(boc.getMaidens() < 0) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
											"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
											"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getMaidens() + "\0");
								}
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
									"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallExtraValue*GEOM*TEXT SET " + 
									(boc.getNoBalls() + boc.getWides()) + "\0");
							
							if(boc.getEconomyRate() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
									"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + 
										boc.getEconomyRate() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
										"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + "-" + "\0");
							}
								
					}
						
					if(inn.getBowlingCard().size()<=7) {
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$FowGrp*ACTIVE SET 0 \0");
							//TimeUnit.MILLISECONDS.sleep(2);
						}
						else{
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$FowGrp*ACTIVE SET 1 \0");
							//TimeUnit.MILLISECONDS.sleep(2);
							for(FallOfWicket fow : inn.getFallsOfWickets()) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								//TimeUnit.MILLISECONDS.sleep(2);
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								//TimeUnit.MILLISECONDS.sleep(2);
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$"+ fow.getFowNumber() +"*GEOM*TEXT SET "+ fow.getFowRuns() + " \0");
								//TimeUnit.MILLISECONDS.sleep(2);
							
							}
							for(int fow_id=inn.getFallsOfWickets().size()+1;fow_id<=10;fow_id++) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow_id + "*ACTIVE SET 0 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow_id + "*ACTIVE SET 0 \0");
								//TimeUnit.MILLISECONDS.sleep(2);
							}		
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + " SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					//TimeUnit.MILLISECONDS.sleep(2);
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtrasValue" + " SET " + inn.getTotalExtras() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallTotalScore" + " SET " + 
							CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				}
			}
			
			switch(Type.toUpperCase()) {
			case "PERFORMER":
				//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");

				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(player == boc.getPlayerId()) {
								if(boc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtraPlayerLastName1" + " SET " + boc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtraPlayerLastName1" + " SET " + "" + "\0");
								}
								
								if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallPerfomerImpact" + " SET " + "1" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallPerfomerImpact" + " SET " + "0" + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtraPlayerFirstName1" + " SET " + boc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallStatHead" + " SET " + "FIGURES" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallStatValue" + " SET " + boc.getWickets() + "/" + boc.getRuns() + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
								//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp$PlayerVerticalNameGrp*ACTIVE SET 0 \0");

								for (Player hs : match.getSetup().getHomeSquad()) {
									if(hs.getPlayerId() == player) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallPlayerImage" + " SET " + photo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\"  +inn.getBowling_team().getTeamName4().toUpperCase() + "\\" + hs.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallPlayerImage" + " SET " + "\\\\\\\\" + 
												config.getPrimaryIpAddress() + local_photo_path + inn.getBowling_team().getTeamName4().toUpperCase() + "\\\\" + 
													hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										TimeUnit.MILLISECONDS.sleep(2);
									}
								}
								
								for (Player as : match.getSetup().getAwaySquad()) {
									if(as.getPlayerId() == player) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallPlayerImage" + " SET " + photo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\"  +inn.getBowling_team().getTeamName4().toUpperCase() + "\\" + as.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallPlayerImage" + " SET " + "\\\\\\\\" + 
												config.getPrimaryIpAddress() +  local_photo_path + inn.getBowling_team().getTeamName4().toUpperCase() + "\\\\" + 
													as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										TimeUnit.MILLISECONDS.sleep(2);
									}
								}
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.363 BallOffsetIn 1.363 BallPerformerIn 0.641 \0");
				break;
			}
		}
	}
	public void populateScorecard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, CricketService cricketService, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			boolean impactInThisInning = false, isReplacePlayerStillToBat = false, isImpactPlayerStillToBat = false, impactPlayerDataFilled = false;
			int row_id = 0, omo_num = 0;
			String cont_name= "", stillToBatImpactName = "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp"
							+ "$FirstName*GEOM*TEXT SET " + " " + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 1 \0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp"
							+ "$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" +
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp"
								+ "$LastName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" +
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp"
								+ "$LastName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					ImpactData[] impactData= CricketFunctions.getImpactPlayerList(match, cricketService);
					Collections.sort(inn.getBattingCard());
					for (BattingCard bc : inn.getBattingCard()) {
						row_id = row_id + 1;
						if(impactData != null) {
							if(impactData[0] != null) {
								if(inn.getBatting_team().getTeamId() == impactData[0].getTeamId()) {
									if(bc.getPlayerId() == impactData[0].getOutPlayerId()) {
										if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
											isReplacePlayerStillToBat = true;
										}
										if(!inn.getBattingCard().stream().filter(btc -> btc.getPlayerId() == impactData[0].getInPlayerId()).findFirst().isPresent()) {
											stillToBatImpactName = cricketService.getAllPlayer().get(impactData[0].getInPlayerId()-1).getTicker_name();
											if(isReplacePlayerStillToBat == true) {
												isImpactPlayerStillToBat = true;
											}
										}
										if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && isImpactPlayerStillToBat == false) {
											row_id--;
											continue;
										}
									}
									
								}
							}
							if(impactData[1] != null) {
								if(inn.getBatting_team().getTeamId() == impactData[1].getTeamId()) {
									if(bc.getPlayerId() == impactData[1].getOutPlayerId()) {
										if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
											isReplacePlayerStillToBat = true;
										}
										if(!inn.getBattingCard().stream().filter(btc -> btc.getPlayerId() == impactData[1].getInPlayerId()).findFirst().isPresent()) {
											stillToBatImpactName = cricketService.getAllPlayer().get(impactData[1].getInPlayerId()-1).getTicker_name();
											if(isReplacePlayerStillToBat == true) {
												isImpactPlayerStillToBat = true;
											}
										}
										if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && isImpactPlayerStillToBat == false) {
											row_id--;
											continue;
										}
									}
								}
							}
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_id + "\0");
						if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							impactInThisInning = true;
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
						}else {
							if(impactInThisInning == false) {
								impactInThisInning = false;
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatWicketPlayerImpact" + row_id + " SET " + "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatBallPlayerImpact" + row_id + " SET " + "0" + "\0");
						
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
						if(bc.getHowOut() == null) {
							if(isImpactPlayerStillToBat) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + "$LeftPlayerName*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + "$LeftPlayerName$BatPlayerName*GEOM*TEXT SET " + stillToBatImpactName + "\0");
								impactPlayerDataFilled = true;
								isImpactPlayerStillToBat = false;
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + "$LeftPlayerName*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + "$LeftPlayerName$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							}
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" +
									row_id + "$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "retired hurt" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
						row_id + 
									"$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "absent hurt" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
						}
						break;
						default:
	
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.OUT:
							omo_num = 1;
							cont_name = "$Dehighlight";
							break;
						case CricketUtil.NOT_OUT:
							omo_num = 2;
							cont_name = "$Highlight";
							break;
						}
						
//						if(bc.getHowOut() == null) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
//						}
//						else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
//						}
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
					
						if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*ACTIVE SET 1 \0");
							if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
									System.out.println("part two : "+bc.getHowOutPartTwo());
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "run out" + " (impact - " + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
													print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
														row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
										}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "run out" + " (sub - " + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
													print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
														row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
												row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "run out" + " (sub - " + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
										}
									}else {
										if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")){
											System.out.println("substitute");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "run out" + " (sub)" + "\0");
													print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
														row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
										}
									}
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)){
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)){
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase("timed_out")) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "timed out" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");	
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)){
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "c (impact - "+bc.getHowOutFielder().getTicker_name()+")" + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
											
										}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "c (sub - "+bc.getHowOutFielder().getTicker_name()+")" + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "c (sub - "+ bc.getHowOutFielder().getTicker_name()+")"+ "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
										}
									}else {
										if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")){
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "c (sub)"+ "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
										}
									}
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" +
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
								}
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
								row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
								row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getStatus() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
								row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
						}
					}
				}
				if(impactPlayerDataFilled == false && !stillToBatImpactName.isEmpty()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + (row_id+1) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
							(row_id+1) + "$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + (row_id+1) + " SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
							(row_id+1) + "$RowAnimation$BatOmo" + "$LeftPlayerName*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
							(row_id+1) + "$RowAnimation$BatOmo" + "$LeftPlayerName$BatPlayerName*GEOM*TEXT SET " + stillToBatImpactName + "\0");
					impactPlayerDataFilled = true;
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll"
						+ "$ExtrasGrp$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll"
						+ "$OversGrp$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll"
						+ "$TotalScore*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
			}
		}
			if(impactInThisInning) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$ImpactLegend$Star*ACTIVE SET " + "1" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$ImpactLegend$noname*ACTIVE SET " + "1" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$ImpactLegend$Star*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$ImpactLegend$noname*ACTIVE SET " + "0" + "\0");
			}
			
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 \0");
		
	}
}
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene,boolean is_this_updating, int whichInning,MatchAllData match,CricketService cricketService, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateBowlingcard -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBowlingcard -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
					+ " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType*FUNCTION*Omo*vis_con SET 1 \0");
			int row_id = 0, omo_num = 0;
			String cont_name= "";
			boolean impactInThisInning = false;
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
							+ match.getSetup().getMatchIdent() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament().toUpperCase() + "\0");
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp*FUNCTION*Omo*vis_con SET " + inn.getBowlingCard().size()+"\0");
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						switch (boc.getStatus().toUpperCase()) {
						case (CricketUtil.OTHER + CricketUtil.BOWLER):
							omo_num = 0;
							cont_name = "$Dehighlight";
							break;
						case (CricketUtil.LAST + CricketUtil.BOWLER):
							omo_num = 0;
							cont_name = "$Dehighlight";
							break;
						case (CricketUtil.CURRENT + CricketUtil.BOWLER):
							omo_num = 1;
							cont_name = "$Highlight";
							break;
						}
						
						row_id = row_id + 1;
						
						if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							impactInThisInning = true;
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "1" + "\0");
						}else {
							if(impactInThisInning == false) {
								impactInThisInning = false;
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
								"$RowAnimation$BallOmo*FUNCTION*Omo*vis_con SET " + omo_num +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
								"$RowAnimation$BallOmo" + cont_name +"$BallPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
								"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallOverValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");

						if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10) || 
								match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow0"
									+ "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Dots" + "\0");
							if(boc.getDots() < 0) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getDots() + "\0");
							}
							
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow0"
									+ "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Maidens" + "\0");
							
							if(boc.getMaidens() < 0) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getMaidens() + "\0");
							}
							
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + "$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallExtraValue*GEOM*TEXT SET " + (boc.getNoBalls() + boc.getWides()) + "\0");
						if(boc.getEconomyRate() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
									"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
									"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + "-" + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + "$RowAnimation$BallOmo" + cont_name +"$ScoreGrp$Figure*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
						
					}
					
					if(inn.getBowlingCard().size()<=7) {
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$FowGrp*ACTIVE SET 0 \0");
						}
						else{
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$FowGrp*ACTIVE SET 1 \0");
							for(FallOfWicket fow : inn.getFallsOfWickets()) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$"+ fow.getFowNumber() +"*GEOM*TEXT SET "+ fow.getFowRuns() + " \0");
							
							}
							for(int fow_id=inn.getFallsOfWickets().size()+1;fow_id<=10;fow_id++) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow_id + "*ACTIVE SET 0 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow_id + "*ACTIVE SET 0 \0");
							}		
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + " SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtrasValue" + " SET " + inn.getTotalExtras() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallTotalScore" + " SET " + 
							CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				}
			}
			if(impactInThisInning) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$ImpactLegend$Star*ACTIVE SET " + "1" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$ImpactLegend$noname*ACTIVE SET " + "1" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$ImpactLegend$Star*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$ImpactLegend$noname*ACTIVE SET " + "0" + "\0");
			}
		}
	}
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning,List<VariousText> vt, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateMatchsummary -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateMatchsummary -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
		int row_id = 0, max_Strap = 0, total_inn = 0,bat_impact_count=0,ball_impact_count=0;
			String teamname = "";//,teamname_logo=""; 
			boolean impactBatInThisInning = false, impactBowlInThisInning = false;
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			/*for(int i = 1; i <= 4 ; i++) {
				if(i == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 0 \0");
				}
			}*/

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$Bands$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "SUMMARY" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
			
			for(int i = 1; i <= whichInning ; i++) {

				if(i == 1) {
					row_id = 0;
					max_Strap = 5;
					bat_impact_count=1;
					ball_impact_count=1;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 0 \0");
					
				} else {
					row_id = 5;
					max_Strap = 10;
					bat_impact_count=6;
					ball_impact_count=6;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 1 \0");
				}
				row_id = row_id + 1;
				
				if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {
					teamname = match.getSetup().getHomeTeam().getTeamName1();
				} else {
					teamname = match.getSetup().getAwayTeam().getTeamName1();
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamFirstName*ACTIVE SET " + "0" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamLastName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SumTeamRuns*GEOM*TEXT SET " + CricketFunctions.getTeamScore(match.getMatch().getInning().get(i-1), 
								slashOrDash, false) + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$OversGrp$SumTeamOvers*GEOM*TEXT SET " + CricketFunctions.OverBalls(match.getMatch().getInning().get(i-1).getTotalOvers(),
								match.getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
				
				if(match.getMatch().getInning().get(i-1).getBattingCard() != null) {
					Collections.sort(match.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					
					for(BattingCard bc : match.getMatch().getInning().get(i-1).getBattingCard()) {
						if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							if(bc.getRuns() > 0) {
								row_id = row_id + 1;
								bat_impact_count+=1;
								
								if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									impactBatInThisInning = true;
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + bat_impact_count + 
											"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
								}else {
									if(impactBatInThisInning == false) {
										impactBatInThisInning = false;
									}
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + bat_impact_count + 
											"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + bc.getRuns() + "\0");
								
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
											"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
								} else {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
											"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								
								if(i == 1 && row_id >= 5) {
									break;
								}else if(i == 2 && row_id >= 10) {
									break;
								}
							}
						}
					}
				}

				for(int j = row_id + 1; j <= max_Strap; j++) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + j + "$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
				}
				
				if(i == 1) {
					row_id = 1;
				}
				else {
					row_id = 6;
				}

				if(match.getMatch().getInning().get(i-1).getBowlingCard() != null) {
					
					Collections.sort(match.getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

					for(BowlingCard boc : match.getMatch().getInning().get(i-1).getBowlingCard()) {
						
						if(boc.getWickets() > 0) {
							row_id = row_id + 1;
							ball_impact_count +=1;
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								impactBowlInThisInning = true;
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + ball_impact_count + 
										"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
							}else {
								if(impactBowlInThisInning == false) {
									impactBowlInThisInning = false;
								}
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + ball_impact_count + 
										"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
							}
								
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							
							if(i == 1 && row_id >= 5) {
								break;
							}
							else if(i == 2 && row_id >= 10) {
								break;
							}
						}
						
					}
				}
				if(impactBatInThisInning == true || impactBowlInThisInning == true) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$Star*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$noname*ACTIVE SET " + "1" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$Star*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$noname*ACTIVE SET " + "0" + "\0");
				}
				
				for(int j = row_id + 1; j <= max_Strap; j++) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + j + 
							"$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
				}
			}
			
			for(VariousText vartext : vt) {
				if(vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ vartext.getVariousText() + "\0");
					}else if(vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
						if(match.getMatch().getMatchResult() != null) {
							if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
										+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,false).getTargetOrResult().toUpperCase() + "\0");
							}
							else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
										+ "MATCH TIED" + "\0");
							}
							else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
									 + match.getMatch().getMatchStatus().toUpperCase() + "\0");
							}
							else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
										+ "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
										+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,false).getTargetOrResult().toUpperCase() + "\0");
							}
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,false).getTargetOrResult().toUpperCase() + "\0");
						}
					}
				}
		}
	}
	
	public void populateFFSummary(PrintWriter print_writer, String viz_scene, String ValueToProcess,  List<Player> allPlayer, String broadcaster) throws InterruptedException {
        Map<String, Object> rowData = CricketFunctions.ReadExcel("C:\\Sports\\Cricket\\Summary.xlsx").get(ValueToProcess);
        
        print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$Star*ACTIVE SET " + "0" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$noname*ACTIVE SET " + "0" + "\0");
        
		//Tournament Logo
        print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$Bands$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + "\0");
		
		//Hide The Tag of Batsman and Bowler
		for(int i=2;i<=5;i++) {
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + i + 
					"$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (i+5) + 
					"$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + i + 
					"$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (i+5) + 
					"$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
		}
		
		//Show The Tag of Batsman of Inning1 and Data 
		for(int j=1;j<=Integer.valueOf((rowData.get("FIRST INN BAT/BALL") != null ? rowData.get("FIRST INN BAT/BALL").toString().split(",")[0].trim() : ""));j++) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
					"$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
			
			if(j==1) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT1 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==2) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT2 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==3) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT3 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==4) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BAT4 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}
		}
		
		//Show The Tag of Bowler of Inning1 and Data
		for(int j=1;j<=Integer.valueOf((rowData.get("FIRST INN BAT/BALL") != null ? rowData.get("FIRST INN BAT/BALL").toString().split(",")[1].trim() : ""));j++) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
					"$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
			
			if(j==1) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL1 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==2) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL2 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==3) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL3 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==4) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS") != null ? rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+1) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("FIRST INN BALL4 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}
		}
		
		//Show The Tag of Batsman of Inning2 and Data
		for(int j=1;j<=Integer.valueOf((rowData.get("SECOND INN BAT/BALL") != null ? rowData.get("SECOND INN BAT/BALL").toString().split(",")[0].trim() : ""));j++) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
					"$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
			
			if(j==1) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT1 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==2) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT2 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==3) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT3 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==4) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[1].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BAT4 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}
		}
		
		//Show The Tag of Bowler of Inning2 and Data
		for(int j=1;j<=Integer.valueOf((rowData.get("SECOND INN BAT/BALL") != null ? rowData.get("SECOND INN BAT/BALL").toString().split(",")[1].trim() : ""));j++) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
					"$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
			
			if(j==1) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL1 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==2) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL2 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==3) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL3 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}else if(j==4) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS").toString().split(",")[0].replace("*", "").trim() : "") + "\0");
				
				if((rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS") != null ? rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS").toString().split(",")[0].trim() : "").contains("*")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
							"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS").toString().split(",")[1].trim() : "") + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (j+6) + 
						"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + (rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS") != null ? 
								rowData.get("SECOND INN BALL4 NAME/RUNS/BALLS").toString().split(",")[2].trim() : "") + "\0");
			}
		}
		
		//Header-SubHeader-Footer
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + 
				(rowData.get("MATCHNAME") != null ? rowData.get("MATCHNAME").toString().trim() : "") + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$SubHeader*GEOM*TEXT SET " + 
				(rowData.get("TOURNAMENT") != null ? rowData.get("TOURNAMENT").toString().trim() : "") + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations"
				+ "*GEOM*TEXT SET " + (rowData.get("FOOTER") != null ? rowData.get("FOOTER").toString().trim() : "") + "\0");
		
		//TOSS
		if(Integer.valueOf((rowData.get("TOSS") != null ? rowData.get("TOSS").toString().trim() : "")) == 1) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1$RowAnimation$"
					+ "TeamNameAll$TossCoin*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$"
					+ "TeamNameAll$TossCoin*ACTIVE SET 0 \0");
		}else if(Integer.valueOf((rowData.get("TOSS") != null ? rowData.get("TOSS").toString().trim() : "")) == 2){
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1$RowAnimation$"
					+ "TeamNameAll$TossCoin*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$"
					+ "TeamNameAll$TossCoin*ACTIVE SET 1 \0");
		}
		
		//BOTH TEAMS NAME/RUNS/OVERS
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1$RowAnimation$TeamNameAll$"
				+ "SummaryTeamNameGrp$SumTeamFirstName*ACTIVE SET " + "0" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$TeamNameAll$"
				+ "SummaryTeamNameGrp$SumTeamFirstName*ACTIVE SET " + "0" + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1$RowAnimation$TeamNameAll$"
				+ "SummaryTeamNameGrp$SumTeamLastName*GEOM*TEXT SET " + (rowData.get("FIRST INN NAME/RUNS/OVERS") != null ? 
						rowData.get("FIRST INN NAME/RUNS/OVERS").toString().split(",")[0].trim() : "") + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$TeamNameAll$"
				+ "SummaryTeamNameGrp$SumTeamLastName*GEOM*TEXT SET " + (rowData.get("SECOND INN NAME/RUNS/OVERS") != null ? 
						rowData.get("SECOND INN NAME/RUNS/OVERS").toString().split(",")[0].trim() : "") + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1$RowAnimation$TeamNameAll$"
				+ "SumTeamRuns*GEOM*TEXT SET " + (rowData.get("FIRST INN NAME/RUNS/OVERS") != null ? 
						rowData.get("FIRST INN NAME/RUNS/OVERS").toString().split(",")[1].trim() : "") + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1$RowAnimation$TeamNameAll$"
				+ "OversGrp$SumTeamOvers*GEOM*TEXT SET " + (rowData.get("FIRST INN NAME/RUNS/OVERS") != null ? 
						rowData.get("FIRST INN NAME/RUNS/OVERS").toString().split(",")[2].trim() : "") + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$TeamNameAll$"
				+ "SumTeamRuns*GEOM*TEXT SET " + (rowData.get("SECOND INN NAME/RUNS/OVERS") != null ? 
						rowData.get("SECOND INN NAME/RUNS/OVERS").toString().split(",")[1].trim() : "") + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$TeamNameAll$"
				+ "OversGrp$SumTeamOvers*GEOM*TEXT SET " + (rowData.get("SECOND INN NAME/RUNS/OVERS") != null ? 
						rowData.get("SECOND INN NAME/RUNS/OVERS").toString().split(",")[2].trim() : "") + "\0");
        
       
        print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 \0");
	}
	
	public void populatePartnership(PrintWriter print_writer, String viz_scene,int whichInning,MatchAllData match,CricketService cricketService, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populatePartnership -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populatePartnership -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, omo_num = 0,Top_Score = 50,row_size = 0, count = 0;
			float Mult = 322, ScaleFac1 = 0, ScaleFac2 = 0;
			boolean impactInThisInning = false, isImpactPlayerStillToBat = false, impactPlayerDataFilled = false;
			String cont_name= "",Left_Batsman = "",Right_Batsman="",stillToBatImpactName="";
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$Data$Header$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament() + "\0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {

				//if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPartTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$Header$LastName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PartLeftLogoGrp$PartLeftLogo*TEXTURE*IMAGE SET "
//								+ logo_path + match.getSetup().getHomeTeam().getTeamName4() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPartTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$Header$LastName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					for(int a = 1; a <= inn.getPartnerships().size(); a++){
						ScaleFac1=0;ScaleFac2=0;
						if(inn.getPartnerships().get(a-1).getFirstBatterRuns() > Top_Score) {
							Top_Score = inn.getPartnerships().get(a-1).getFirstBatterRuns();
						}
						if(inn.getPartnerships().get(a-1).getSecondBatterRuns() > Top_Score) {
							Top_Score = inn.getPartnerships().get(a-1).getSecondBatterRuns();
						}
					}
					ImpactData[] impactData = CricketFunctions.getImpactPlayerList(match, cricketService);

					for (Partnership ps : inn.getPartnerships()) {
						
						row_id = row_id + 1;
						Left_Batsman ="" ; Right_Batsman="";
						for (BattingCard bc : inn.getBattingCard()) {
							if(impactData != null) {
								if(impactData[0] != null) {
									if(inn.getBatting_team().getTeamId() == impactData[0].getTeamId()) {
										if(bc.getPlayerId() == impactData[0].getOutPlayerId()) {
											if(!inn.getBattingCard().stream().filter(btc -> btc.getPlayerId() == impactData[0].getInPlayerId()).findFirst().isPresent()) {
												stillToBatImpactName = cricketService.getAllPlayer().get(impactData[0].getInPlayerId()-1).getTicker_name();
												isImpactPlayerStillToBat = true;
											}
											if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && isImpactPlayerStillToBat == false) {
												row_size = (inn.getBattingCard().size() - 1);
												continue;
											}
										}
										
									}
								}
								if(impactData[1] != null) {
									if(inn.getBatting_team().getTeamId() == impactData[1].getTeamId()) {
										if(bc.getPlayerId() == impactData[1].getOutPlayerId()) {
											if(!inn.getBattingCard().stream().filter(btc -> btc.getPlayerId() == impactData[1].getInPlayerId()).findFirst().isPresent()) {
												stillToBatImpactName = cricketService.getAllPlayer().get(impactData[1].getInPlayerId()-1).getTicker_name();
												isImpactPlayerStillToBat = true;
											}
											if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && isImpactPlayerStillToBat == false) {
												row_size = (inn.getBattingCard().size() - 1);
												continue;
											}
										}
									}
								}
							}
							
							if(bc.getPlayerId() == ps.getFirstBatterNo()) {
								Left_Batsman = bc.getPlayer().getTicker_name();
							}
							else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
								Right_Batsman = bc.getPlayer().getTicker_name();
							}
						}
						
						if(inn.getPartnerships().size() >= 10) {
							if(ps.getPartnershipNumber()<=inn.getPartnerships().size()) {
								omo_num = 3;
								cont_name = "Highlight";
							}
						}
						else {
							if(ps.getPartnershipNumber()<inn.getPartnerships().size()) {
								omo_num = 3;
								cont_name = "Highlight";
							}
							else if(ps.getPartnershipNumber() >= inn.getPartnerships().size()) {
								omo_num = 2;
								cont_name = "Dehighlight";
							}
						}
						
						ScaleFac1 = ((ps.getFirstBatterRuns())*(Mult/Top_Score)) ;
						ScaleFac2 = ((ps.getSecondBatterRuns())*(Mult/Top_Score)) ;
						if(inn.getTotalWickets() >= 9) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + inn.getPartnerships().size() + "\0");
						}else {
							if(row_size != 0) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_size + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + inn.getBattingCard().size() + "\0");
							}
						}

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + 
								"$LeftPlayerName*GEOM*TEXT SET " + Left_Batsman + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + 
								"$RightPlayerName*GEOM*TEXT SET " + Right_Batsman + "\0");
						
						if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, ps.getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
							impactInThisInning = true;
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "1" + "\0");
						}else {
							if(impactInThisInning == false) {
								impactInThisInning = false;
							}
								
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "0" + "\0");
						}
						
						if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, ps.getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
							impactInThisInning = true;
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRightImpact" + row_id + " SET " + "1" + "\0");
						}else {
							if(impactInThisInning == false) {
								impactInThisInning = false;
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRightImpact" + row_id + " SET " + "0" + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + 
								"$Bar*FUNCTION*BarValues*Bar_Value__1 SET " + ScaleFac1 + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + 
								"$Bar*FUNCTION*BarValues*Bar_Value__2 SET " + ScaleFac2 + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + 
								"$ScoreGrp$PartnershipRun*GEOM*TEXT SET " + ps.getTotalRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo" + "$" + cont_name + 
								"$ScoreGrp$PartnershipBalls*GEOM*TEXT SET " + ps.getTotalBalls() + "\0");		
					}
					if(inn.getPartnerships().size() >= 10) {
						row_id = row_id + 1;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + " \0");
						
					}
					else {
						for (BattingCard bc : inn.getBattingCard()) {
							if(row_id < inn.getBattingCard().size()) {
								if(row_id == inn.getPartnerships().size()) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "0" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + " \0");
									
//									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$partnershipall$BatDataGrp$Row" + row_id  + 
//											"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + " \0");
									if(match.getMatch().getInning().get(whichInning - 1).getTotalOvers() == match.getSetup().getMaxOvers() || 
											match.getMatch().getInning().get(whichInning - 1).getTotalWickets() >= 10 ) {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + 
												"$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + "DID NOT BAT"+" \0");
//										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$partnershipall$BatDataGrp$Row" + row_id  + 
//												"$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
									}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || 
											CricketFunctions.getWicketsLeft(match,whichInning) <= 0) {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + 
												"$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + "DID NOT BAT"+" \0");
//										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$partnershipall$BatDataGrp$Row" + row_id  + 
//												"$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + 
												"$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + "STILL TO BAT"+" \0");
//										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$partnershipall$BatDataGrp$Row" + row_id  + 
//												"$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "STILL TO BAT" +" \0");
									}
								}
								else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
										impactInThisInning = true;
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "1" + "\0");
									}else {
										if(impactInThisInning == false) {
											impactInThisInning = false;
										}
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "0" + "\0");
									}
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + " \0");
									
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + 
											bc.getPlayer().getTicker_name()+" \0");
									if(impactData[0] != null) {
										if(inn.getBatting_team().getTeamId() == impactData[0].getTeamId()) {
											if(isImpactPlayerStillToBat && impactData[0].getOutPlayerId() == bc.getPlayerId()) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "1" + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + " \0");
												print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + stillToBatImpactName+" \0");
												isImpactPlayerStillToBat = false;
												impactPlayerDataFilled = true;
												
											}
										}
									}
									if(impactData[1] != null) {
										if(inn.getBatting_team().getTeamId() == impactData[1].getTeamId()) {
											if(isImpactPlayerStillToBat && impactData[1].getOutPlayerId() == bc.getPlayerId()) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "1" + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + " \0");
												print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + stillToBatImpactName+" \0");
												isImpactPlayerStillToBat = false;
												impactPlayerDataFilled = true;
											}
										}
									}
								}	
							}
							else {
								break;
							}
						}
						
						if(impactPlayerDataFilled == false && !stillToBatImpactName.isEmpty()) {
							row_id = row_id + 1;
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "1" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText"
									+ "*GEOM*TEXT SET " + stillToBatImpactName+" \0");
							
							impactPlayerDataFilled = true;
						}
					}
					if(impactInThisInning) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$PartnershipAll$ImpactLegend$Star*ACTIVE SET " + "1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$PartnershipAll$ImpactLegend$noname*ACTIVE SET " + "1" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$PartnershipAll$ImpactLegend$Star*ACTIVE SET " + "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$PartnershipAll$ImpactLegend$noname*ACTIVE SET " + "0" + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BottomInfo$BottomInfoGrp$BottomInfoAll$ExtrasGrp$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$PartnershipAll$BottomInfo$BottomInfoGrp$BottomInfoAll$noname$OversGrp$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET " + 
							CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				}
			}
		}
	}
	public void populateTeamsLogo(PrintWriter print_writer,String viz_scene, List<Team> teams ,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		this.status = CricketUtil.SUCCESSFUL;
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
	    
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "TEAMS" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + "" + "\0");
		//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader2" + " SET " + "POOL B" + "\0");
	    
		for(int i=0; i<= teams.size()-1; i++) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo" + (i+1) + " SET " + "IMAGE*/Default/APL/Logos/" + teams.get(i).getTeamName4() + "\0");
			if(teams.get(i).getCaptains() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName0" + (i+1) + " SET " + teams.get(i).getCaptains() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName0" + (i+1) + " SET " + teams.get(i).getTeamName1() + "\0");
			}
			
		}
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.520  \0");
			
	}
	
	public void populateBugPowerPLay(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "APL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "POWERPLAY" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				
				if (whichInning == 1) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + " " + 
							match.getMatch().getInning().get(0).getBatting_team().getTeamName3() + "\0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + " " + 
							match.getMatch().getInning().get(1).getBatting_team().getTeamName3() + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + 
						CricketFunctions.getFirstPowerPlayScore(match,whichInning, match.getEventFile().getEvents()).split(",")[0] + "\0");
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateBugToss(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "APL":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
				
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
							" WON THE TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
							" WON THE TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + "\0");
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBugHighlight(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Powerplay's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			String Value = "";
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "HIGHLIGHTS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
					match.getMatch().getInning().get(whichInning-1).getBatting_team().getTeamName3() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
			
			if (match.getMatch().getInning().get(whichInning-1).getTotalWickets() >= 10) {
				Value = String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalRuns());
			} else {
				Value = String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalRuns()) + "-" + 
						String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalWickets());
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + Value + " (" +
					CricketFunctions.OverBalls(match.getMatch().getInning().get(whichInning-1).getTotalOvers(),
							match.getMatch().getInning().get(whichInning-1).getTotalBalls()) + ")" + "\0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
		TimeUnit.MILLISECONDS.sleep(200);		
	}
	public void populateBugMultipartnership(PrintWriter print_writer, String viz_scene,int whichinning, int partnership, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for (Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == whichinning) {
					String Left_Batsman ="",Right_Batsman="";
					
					Left_Batsman = inn.getPartnerships().get(partnership - 1).getFirstPlayer().getTicker_name();
					Right_Batsman = inn.getPartnerships().get(partnership - 1).getSecondPlayer().getTicker_name();
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "PARTNERSHIP" + "\0");
					
					if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "st WICKET" + "\0");
						
					}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 2) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "nd WICKET" + "\0");
						
					}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 3) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "rd WICKET" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "th WICKET" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + inn.getPartnerships().get(partnership - 1).getTotalRuns() + 
							" (" + inn.getPartnerships().get(partnership - 1).getTotalBalls() + ")" + "\0");
					
					
					for(BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId() == inn.getPartnerships().get(partnership - 1).getFirstBatterNo()) {
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + Left_Batsman + " " + 
										inn.getPartnerships().get(partnership - 1).getFirstBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getFirstBatterBalls() + ")" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + Left_Batsman + " " + 
										inn.getPartnerships().get(partnership - 1).getFirstBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getFirstBatterBalls() + ")" + "\0");
							}
						}
						
						if(bc.getPlayerId() == inn.getPartnerships().get(partnership - 1).getSecondBatterNo()) {
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + Right_Batsman + "  " + 
										inn.getPartnerships().get(partnership - 1).getSecondBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getSecondBatterBalls() + ")" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + Right_Batsman + "  " + 
										inn.getPartnerships().get(partnership - 1).getSecondBatterRuns()+ " (" + inn.getPartnerships().get(partnership - 1).getSecondBatterBalls() + ")" + "\0");
							}
						}
					}
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
		TimeUnit.MILLISECONDS.sleep(200);
	}
	public void populateBugPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					String Left_Batsman ="",Right_Batsman="";
					
					Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getTicker_name();
					Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getTicker_name();
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "CURRENT" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "PARTNERSHIP" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() 
							+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + "\0");
					
					for(BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
										Left_Batsman + " " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns()
										+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ")" + "\0");
							}
						}
						
						if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + 
										Right_Batsman + "  " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns()
										+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ")" + "\0");
							}
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populateBugDismissal(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateBugDismissal -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBugDismissal -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + " " + "\0");

								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								}
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + 
													bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										}
									}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + 
													bc.getHowOutPartOne().replace("(SUB)", "") + " " + bc.getHowOutPartTwo() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									}
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + 
											bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + " " + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + 
											bc.getRuns() + "*" + " (" + bc.getBalls() + ")" + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "" + "\0");

								/*if(bc.getStrikeRate() == null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "S/R : " + "-" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "S/R : " + bc.getStrikeRate() + "\0");
								}*/
								
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
	}
	public void populateBug(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateBug -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBug -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + " " + "\0");
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + bc.getRuns() +"* "+ "(" + bc.getBalls() + ")" + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
								}
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + "4s : " + bc.getFours() + " 6s : " + bc.getSixes() + "\0");

								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "S/R : " + bc.getStrikeRate() + "\0");
							}
						}
						break;
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + "ECON: " + boc.getEconomyRate() + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + " " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info04*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
	}	
	public void populateBugBowler(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Bug's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info03*GEOM*TEXT SET " + 
										boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info04*GEOM*TEXT SET " + 
										CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								
								
//								if(boc.getPlayer().getSurname() != null) {
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + boc.getPlayer().getFirstname() + "\0");
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + boc.getPlayer().getSurname() + "\0");
//								}else {
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + boc.getPlayer().getFirstname() + "\0");
//								}
//								
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
//										CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " Overs" + "\0");
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
//								
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
								
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
	}
	public void populateBugsDB(PrintWriter print_writer,String viz_scene, Bugs bug ,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");

			if(bug.getText1() != null && bug.getText2() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + " " + bug.getText2().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");

			}else if(bug.getText1() != null && bug.getText2() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");

			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText2().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
	}
	
	public void populateImpactPlayer(PrintWriter print_writer, String viz_scene, int playerOutId, int playerInId, MatchAllData match,String session_selected_broadcaster, Configuration config) {
		
		Player outPlayer = CricketFunctions.getPlayerFromMatchData(playerOutId, match);
		Player inPlayer = CricketFunctions.getPlayerFromMatchData(playerInId, match);
		
		
		if (inPlayer.getTeamId() == match.getSetup().getHomeTeamId()) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tHomeTeamRefName" + " SET " + logo_path +match.getSetup().getHomeTeam().getTeamName4().toUpperCase()+ "\0");
			
			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$InPlayer$PlayerImg02*TEXTURE*IMAGE SET " + photo_path +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase()+ "\\" +inPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$OutPlayer$PlayerImg01*TEXTURE*IMAGE SET " + photo_path +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase()+ "\\" +outPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$InPlayer$PlayerImg02*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress()
				+ local_photo_path +match.getSetup().getHomeTeam().getTeamName4().toUpperCase()+ "\\" +inPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$OutPlayer$PlayerImg01*TEXTURE*IMAGE SET " +  "\\\\" + config.getPrimaryIpAddress()
				+ local_photo_path +match.getSetup().getHomeTeam().getTeamName4().toUpperCase()+ "\\" +outPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
			}
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tHomeTeamRefName" + " SET " + logo_path +match.getSetup().getAwayTeam().getTeamName4().toUpperCase()+ "\0");
			if (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
				print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$InPlayer$PlayerImg02*TEXTURE*IMAGE SET " + photo_path +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase()+ "\\" +inPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$OutPlayer$PlayerImg01*TEXTURE*IMAGE SET " + photo_path +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase()+ "\\" +outPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$InPlayer$PlayerImg02*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress()
				+ local_photo_path +match.getSetup().getAwayTeam().getTeamName4().toUpperCase()+ "\\" +inPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$OutPlayer$PlayerImg01*TEXTURE*IMAGE SET " +  "\\\\" + config.getPrimaryIpAddress()
				+ local_photo_path +match.getSetup().getAwayTeam().getTeamName4().toUpperCase()+ "\\" +outPlayer.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
			}
		}
		
		if(outPlayer.getSurname() != null) {
			print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$OutPlayer$FOW*GEOM*TEXT SET "+ outPlayer.getFirstname()+ "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$OutPlayer$LastName*GEOM*TEXT SET "+ outPlayer.getSurname()+ "\0");
		}else {
			print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$OutPlayer$FOW*GEOM*TEXT SET "+ ""+ "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$OutPlayer$LastName*GEOM*TEXT SET "+ outPlayer.getFirstname()+ "\0");
		}
		
		if(inPlayer.getSurname() != null) {
			print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$InPlayer$FOW*GEOM*TEXT SET "+ inPlayer.getFirstname()+ "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$InPlayer$LastName*GEOM*TEXT SET "+ inPlayer.getSurname()+ "\0");
		}else {
			print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$InPlayer$FOW*GEOM*TEXT SET "+ ""+ "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$PlayerNameGrp$InPlayer$LastName*GEOM*TEXT SET "+ inPlayer.getFirstname()+ "\0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.120 \0");
	}
	
	public void populatePlayoffs(PrintWriter print_writer, String viz_scene, List<Playoff> playoffs, List<Team> team,
			String broadcaster, MatchAllData match) throws InterruptedException {
		
		print_writer.println(
				"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "
						+ "IMAGE*/Default/APL/Logos/" + "TLogo" + "\0");
		print_writer.println(
				"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "
						+ "IMAGE*/Default/APL/Logos/" + "TLogo" + "\0");

		print_writer.println(
				"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
		print_writer.println(
				"-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET "
				+ "ROAD TO FINAL" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET "
				+ match.getSetup().getTournament() + "\0");

		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-Header" + " SET "
				+ "SEMI-FINAL 1" + "\0");
//		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-Header" + " SET "
//				+ "ELIMINATOR" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-Header" + " SET "
				+ "SEMI-FINAL 2" + "\0");
//		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-Header" + " SET "
//				+ "QUALIFIER 2" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-Header" + " SET "
				+ "FINAL" + "\0");

		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-RUNNER-Header"
				+ " SET " + "LOSER Q1" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-WINNER-Header"
				+ " SET " + "WINNER ELM" + "\0");

		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamA-Alpha" + " SET "
				+ "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamB-Alpha" + " SET "
				+ "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA-Alpha" + " SET "
				+ "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB-Alpha" + " SET "
				+ "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Alpha" + " SET "
				+ "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Alpha" + " SET "
				+ "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Alpha" + " SET "
				+ "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Alpha" + " SET "
				+ "100" + "\0");

		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamA" + " SET "
				+ playoffs.get(0).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamB" + " SET "
				+ playoffs.get(0).getTeam2().toUpperCase() + "\0");

		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA" + " SET "
				+ playoffs.get(1).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB" + " SET "
				+ playoffs.get(1).getTeam2().toUpperCase() + "\0");

//		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA" + " SET "
//				+ playoffs.get(2).getTeam1().toUpperCase() + "\0");
//		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB" + " SET "
//				+ playoffs.get(2).getTeam2().toUpperCase() + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA" + " SET "
				+ playoffs.get(2).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB" + " SET "
				+ playoffs.get(2).getTeam2().toUpperCase() + "\0");

//		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA" + " SET "
//				+ playoffs.get(3).getTeam1().toUpperCase() + "\0");
//		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB" + " SET "
//				+ playoffs.get(3).getTeam2().toUpperCase() + "\0");

		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
						+ "IMAGE*/Default/APL/TeamColour/0" + " \0");
		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
						+ "IMAGE*/Default/APL/TeamColour/0" + " \0");
		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
						+ "IMAGE*/Default/APL/TeamColour/0" + " \0");
		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
						+ "IMAGE*/Default/APL/TeamColour/0" + " \0");

		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$A$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$B$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_A$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_B$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println(
				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 0 \0");
//		print_writer.println(
//				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 0 \0");
//		print_writer.println(
//				"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 0 \0");

		for (int i = 0; i <= team.size() - 1; i++) {
			if (team.get(i).getTeamName1().equalsIgnoreCase(playoffs.get(0).getTeam1())) {
				print_writer.println(
						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$A$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println(
						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamA-Logo" + " SET "
								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName1().equalsIgnoreCase(playoffs.get(0).getTeam2())) {
				print_writer.println(
						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$B$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println(
						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamB-Logo" + " SET "
								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName1().equalsIgnoreCase(playoffs.get(1).getTeam1())) {
				print_writer.println(
						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_A$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println(
						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-TeamA-Logo" + " SET "
								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName1().equalsIgnoreCase(playoffs.get(1).getTeam2())) {
				print_writer.println(
						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_B$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println(
						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-TeamB-Logo" + " SET "
								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
//			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(2).getTeam1())) {
//				print_writer.println(
//						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamA$TeamLogo*ACTIVE SET 1 \0");
//				print_writer.println(
//						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Logo" + " SET "
//								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
//			}
//			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(2).getTeam2())) {
//				print_writer.println(
//						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamB$TeamLogo*ACTIVE SET 1 \0");
//				print_writer.println(
//						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Logo" + " SET "
//								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
//			}
			
			if (team.get(i).getTeamName1().equalsIgnoreCase(playoffs.get(2).getTeam1())) {
				print_writer.println(
						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println(
						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Logo" + " SET "
								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName1().equalsIgnoreCase(playoffs.get(2).getTeam2())) {
				print_writer.println(
						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println(
						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Logo" + " SET "
								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			
//			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(3).getTeam1())) {
//				print_writer.println(
//						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 1 \0");
//				print_writer.println(
//						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Logo" + " SET "
//								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
//			}
//			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(3).getTeam2())) {
//				print_writer.println(
//						"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 1 \0");
//				print_writer.println(
//						"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Logo" + " SET "
//								+ "IMAGE*/Default/APL/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
//			}
		}

		if (playoffs.get(0).getWinner() != null) {
			print_writer.println(
					"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
							+ "IMAGE*/Default/APL/HeaderBand" + " \0");
			if (playoffs.get(0).getWinner().equalsIgnoreCase(playoffs.get(0).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamB-Alpha"
						+ " SET " + "50" + "\0");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamA-Alpha"
						+ " SET " + "50" + "\0");
			}
		}

		if (playoffs.get(1).getWinner() != null) {
			print_writer.println(
					"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
							+ "IMAGE*/Default/APL/HeaderBand" + " \0");
			if (playoffs.get(1).getWinner().equalsIgnoreCase(playoffs.get(1).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB-Alpha"
						+ " SET " + "50" + "\0");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA-Alpha"
						+ " SET " + "50" + "\0");
			}
		}

		if (playoffs.get(2).getWinner() != null) {
			print_writer.println(
					"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
							+ "IMAGE*/Default/APL/HeaderBand" + " \0");
			if (playoffs.get(2).getWinner().equalsIgnoreCase(playoffs.get(2).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Alpha"
						+ " SET " + "50" + "\0");
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Alpha"
						+ " SET " + "50" + "\0");
			}
		}

//		if (playoffs.get(3).getWinner() != null) {
//			print_writer.println(
//					"-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
//							+ "IMAGE*/Default/APL/HeaderBand" + " \0");
//			if (playoffs.get(3).getWinner().equalsIgnoreCase(playoffs.get(3).getTeam1())) {
//				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Alpha"
//						+ " SET " + "50" + "\0");
//			} else {
//				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Alpha"
//						+ " SET " + "50" + "\0");
//			}
//		}

		print_writer.println(
				"-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 In$ManDataIn 0.931 \0");

	}
	
	public void populateFixturesAndResult(PrintWriter print_writer, String viz_scene, int teamId, List<Team> team, List<Fixture> fixture,
			String broadcaster, MatchAllData match) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			Calendar cal_apl = Calendar.getInstance();
			cal_apl.add(Calendar.DATE, 0);
			int size = 0;
			List<Fixture> fixtureList = new ArrayList<Fixture>();
			Team tm = team.stream().filter(teams->teams.getTeamId()==teamId).findAny().orElse(null);
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$StatHeadGrp*ACTIVE SET 0 \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + tm.getTeamName2() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + tm.getTeamName3() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + "FIXTURES AND RESULTS" + "\0"); 
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "010" + " SET " + "3" + "\0");
			
			for(Fixture fix : fixture) {
				if(fix.getHometeamid() == teamId || fix.getAwayteamid() == teamId) {
					fixtureList.add(fix);
				}
			}
			String cont = "";
			int omo=0;
			
			for(int i=0; i<fixtureList.size(); i++) {
				if(fixtureList.get(i).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal_apl.getTime()))) {
					System.out.println("HIGH");
					omo=1;
					cont="$Highlight";
				}else {
					System.out.println("DEHIGH");
					omo =0;
					cont="$Dehighlight";
					
				}
				if(fixtureList.get(i).getHometeamid() == teamId) {
					size++;
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatHead*GEOM*TEXT SET " + "v "+ team.get(fixtureList.get(i).getAwayteamid()-1).getTeamName3() + "\0");
				}else if(fixtureList.get(i).getAwayteamid() == teamId) {
					size++;
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatHead*GEOM*TEXT SET " + "v "+ team.get(fixtureList.get(i).getHometeamid()-1).getTeamName3() + "\0");
				}
				
				if(fixtureList.get(i).getMargin() != null && !fixtureList.get(i).getMargin().isEmpty()) {
					if(fixtureList.get(i).getWinnerteam() != null && !fixtureList.get(i).getWinnerteam().isEmpty()) {
						if(fixtureList.get(i).getWinnerteam().equalsIgnoreCase(tm.getTeamName1())) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue1*GEOM*TEXT SET " + "WON BY" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue2*GEOM*TEXT SET " + fixtureList.get(i).getMargin() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue1*GEOM*TEXT SET " + "LOST BY" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue2*GEOM*TEXT SET " + fixtureList.get(i).getMargin() + "\0");
						}
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue1*GEOM*TEXT SET " + fixtureList.get(i).getMargin() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue2*GEOM*TEXT SET " + "" + "\0");
					}
				}else {
					
					if(fixtureList.get(i).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal_apl.getTime()))) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue1*GEOM*TEXT SET " + "TODAY" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue2*GEOM*TEXT SET " + "" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue1*GEOM*TEXT SET " + CricketFunctions.ordinal(Integer.valueOf(fixtureList.get(i).getDate().split("-")[0]))
						+ " " + Month.of(Integer.valueOf(fixtureList.get(i).getDate().split("-")[1])) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+cont+"$StatValue2*GEOM*TEXT SET " + "" + "\0");
						
					}
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$RowOmo*FUNCTION*Omo*vis_con SET " + omo + "\0");
//				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "HighlightSelection" + " SET " + omo + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "009" + " SET " + (size-1) + "\0");
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn$DataIn 1.170 \0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}
	public void populateMostRunsTeam(PrintWriter print_writer,String viz_scene,int teamId,String Type,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MostRuns inning is null";
		} else {
			String teamName="";
			int row_no=0;
			Team tm = team.stream().filter(team1 -> team1.getTeamId() == teamId).findAny().orElse(null);
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + tm.getTeamName4() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
					+ logo_path + tm.getTeamName4() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$Data$CapAll*FUNCTION*Omo*vis_con SET " + "0" + "\0");
			for(int i=1; i<=5; i++) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatches"+i + " SET " + "" + "\0");
			}
		
			
			switch(Type.toUpperCase()) {
			case "RUNS":
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + tm.getTeamName1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + tm.getTeamName1()  + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST RUNS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + 
						match.getSetup().getTournament().toUpperCase() + "\0");
				
				
				
				row_no = 0;
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamId() == teamId) {
						row_no = row_no + 1;
						if(row_no < 6) {
							if(row_no == 1) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
											team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\" + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
										config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
											+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
									tournament.get(i).getPlayer().getFull_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
									"STRIKE RATE : "+CricketFunctions.generateStrikeRate(tournament.get(i).getRuns(), tournament.get(i).getBallsFaced(), 0) + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
						}else {
							break;
						}
					}
				}
				this.status = CricketUtil.SUCCESSFUL;
				break;
				
			case "WICKETS":
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + tm.getTeamName1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + tm.getTeamName1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST WICKETS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				row_no = 0;
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamId() == teamId) {
						row_no = row_no + 1;
						if(row_no < 6) {
							if(row_no == 1) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
											team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\" + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
										config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
											+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
									tournament.get(i).getPlayer().getFull_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
									"ECONOMY : "+ CricketFunctions.getEconomy(tournament.get(i).getRunsConceded(), tournament.get(i).getBallsBowled(), 2, "-")+ "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getWickets() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
							
//							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
						}else {
							break;
						}
					}
				}
				this.status = CricketUtil.SUCCESSFUL;
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.180 \0");
		}
	}
	
	
	public void populateLTNextToBat(PrintWriter print_writer,String viz_scene,List<Statistics> stats,List<Player> plyr,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id=0;
			double strike_rate = 0;
		
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + 
							logo_path + inn.getBatting_team().getTeamName4() + "\0");
//					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$LogoGrp$LogoAll*TEXTURE*IMAGE SET " + logo_path +
//							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					for(int b=1;b<=inn.getBattingCard().size();b++) {
						if(inn.getBattingCard().get(b-1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && 
								inn.getBattingCard().get(b-1).getHowOut() == null) {
							row_id = row_id + 1;
							if(row_id <= 3) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPosition" + row_id + " SET " + b + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_id + " SET " + 
										inn.getBattingCard().get(b-1).getPlayer().getTicker_name() + "\0");
								
								for(Statistics st : stats) {
									if(st.getPlayer_id()==inn.getBattingCard().get(b-1).getPlayerId() && st.getStats_type_id() == 2) {
										if(st.getBalls_faced() == 0 || st.getRuns()== 0) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + "-" + "\0");
										}else {
											strike_rate = st.getRuns() * 100;
											strike_rate = strike_rate/st.getBalls_faced();
											DecimalFormat df = new DecimalFormat("0.0");
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + df.format(strike_rate) + "\0");
										}
									}
								}
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + photo_path + 
											inn.getBatting_team().getTeamName4() + "" + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\" +inn.getBatting_team().getTeamName4() + "\\" + inn.getBattingCard().get(b-1).getPlayer().getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + "\\\\\\\\" + 
											config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + 
											"\\\\" + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
							}
						}
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.494\0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}
	
	public void populatePointers(PrintWriter print_writer,String viz_scene, Pointers Pt ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");

			if(Pt.getTeam() != null){
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + Pt.getTeam() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + "TLogo" + "\0");
			}
			
			if(Pt.getText1() != null && Pt.getText2() != null && Pt.getText3() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + Pt.getHeader() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + Pt.getText1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + Pt.getText2() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo03" + " SET " + Pt.getText3() + "\0");
				
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$1st*ACTIVE SET 1 \0");	
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$2nd*ACTIVE SET 1 \0");
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$3rd*ACTIVE SET 1 \0");

			}else if(Pt.getText1() != null && Pt.getText2() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + Pt.getHeader() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + Pt.getText1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + Pt.getText2() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo03" + " SET " + "" + "\0");
				
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$1st*ACTIVE SET 1 \0");	
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$2nd*ACTIVE SET 1 \0");
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$3rd*ACTIVE SET 0 \0");
				
			}else if(Pt.getText1() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + Pt.getHeader() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + Pt.getText1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo03" + " SET " + "" + "\0");
				
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$1st*ACTIVE SET 1 \0");	
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$2nd*ACTIVE SET 0 \0");
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$3rd*ACTIVE SET 0 \0");
				
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.580 \0");
		}
		
	}
	
	public void populateHowout(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOut's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId()==playerId) {
							
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
							if(bc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
							}
							
							if(bc.getHowOutText() == null) {
								if(bc.getHowOut()!=null) {
									if(bc.getHowOut().equalsIgnoreCase("timed_out")) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "timed out" + "\0");
									}else if(bc.getHowOut().equalsIgnoreCase("retired_hurt")) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "retired hurt" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOut() + "\0");
									}
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + " " + "\0");	
								}							
							}else {
								if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
											  "run out (impact - " + bc.getHowOutFielder().getTicker_name() + ") " + "\0");
									}else {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "run out "+"(sub - "+ bc.getHowOutFielder().getTicker_name()+") " + "\0");
										}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "run out (sub)" + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										}
									}
								}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
												"c "+ "(impact - "+bc.getHowOutFielder().getTicker_name()+") "+ bc.getHowOutPartTwo() + "\0");
									}else {
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)){
											print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
													"c "+ "(sub - "+bc.getHowOutFielder().getTicker_name()+") "+bc.getHowOutPartTwo() + "\0");
										}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")){
											print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
													"c "+ "(sub)" + "\0");
										}else{
											print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										}
									}
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
								}
									
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");							
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");	
		}
	}
	public void populateHowoutquick(PrintWriter print_writer,String viz_scene,MatchAllData match, String broadcaster) 
	{	
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOut's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					
					for (BattingCard bc : inn.getBattingCard()) {
						if(inn.getFallsOfWickets().size() > 0) {
							if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								}
								
								if (bc.getHowOutText().trim().equalsIgnoreCase("")){
									print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + " " + "\0");								
								}else {
									if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
												"run out" + " (impact - " + bc.getHowOutFielder().getTicker_name() + ") " + "\0");
										}else {
											if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "run out "+"(sub - "+ bc.getHowOutFielder().getTicker_name()+") " + "\0");
											}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")){
												print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "run out (sub)" + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											}
										}
									}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
										if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
													"c"+ " (impact - "+bc.getHowOutFielder().getTicker_name()+") "+bc.getHowOutPartTwo() + "\0");
										}else {
											if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "c (sub - "+bc.getHowOutFielder().getTicker_name()+") "+bc.getHowOutPartTwo() + "\0");
											}else if(bc.getHowOutPartTwo().equalsIgnoreCase("substitute")) {
												print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "c (sub)" + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											}
											
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									}						
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");	
							}
						}						
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");	
		}
	}
	public void populateHowoutWithoutFielder(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOut's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$All$Out$LogoGrp$LogoAll$LeftBlueBase*TEXTURE*IMAGE SET " + logo_path + match.getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$All$Out$LogoGrp$LogoAll$LeftBlueBase*TEXTURE*IMAGE SET " + logo_path + match.getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId()==playerId) {
							
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
							if(bc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "4s: " + bc.getFours() + " 6s: " + bc.getSixes() + "\0");								
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + (bc.getBalls() + 1) + "\0");							
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
	}	
	public void populateBatsmanstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerStats's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int total_inn = 0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, playerId,",", match.getEventFile().getEvents()).split(",");
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								if (CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),inn.getInningNumber(), bc.getPlayerId())
										.equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vImpact SET " + "1" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vImpact SET " + "0" + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
										 inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getFirstname() + "\0");
								}								
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "*" + "\0");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
								}
								
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + bc.getBalls() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "0s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");

								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "4s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + bc.getFours() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "6s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + bc.getSixes() + "\0");

								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "S/R" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + bc.getStrikeRate() + "\0");
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.420 \0");
			TimeUnit.MILLISECONDS.sleep(500);	
		}	
	}	
	public void populateBowlerstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId,List<Team> team, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerStats's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int total_inn = 0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
							 inn.getBowling_team().getTeamName4().toUpperCase() + "\0");

					switch(statsType.toUpperCase()) {
					case CricketUtil.BOWLER:
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								
								if (CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),inn.getInningNumber(), boc.getPlayerId())
										.equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vImpact SET " + "1" + "\0");
								} else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON vImpact SET " + "0" + "\0");
								}
								
								if(boc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + boc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + boc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + boc.getPlayer().getFirstname() + "\0");
								}								
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + boc.getWickets()+"-"+boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + " " + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "OVERS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");

								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "DOTS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + boc.getDots() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "ECONOMY" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + boc.getEconomyRate() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "EXTRAS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + (boc.getNoBalls()+boc.getWides()) + "\0");			
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.420 \0");
			TimeUnit.MILLISECONDS.sleep(500);	
		}
	}
		
	public void populateNameSuper(PrintWriter print_writer,String viz_scene, NameSuper ns ,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			if(ns.getSponsor() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + "TLogo" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
						ns.getSponsor() + "\0");
			}
			
			if(ns.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + ns.getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + ns.getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + ns.getFirstname().toUpperCase() + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + ns.getSubLine().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + " " + "\0");
				
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
			TimeUnit.MILLISECONDS.sleep(1200);
				
		}
	}	
	public void populateNameSuperPlayer(PrintWriter print_writer,String viz_scene, String captainWicketKeeper, int playerId, List<Player> Plyrs, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			String Home_or_Away="";
			Player player;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			player = Plyrs.stream().filter(plyr -> plyr.getPlayerId() == playerId).findAny().orElse(null);
			
			if(player.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + player.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + player.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + player.getFirstname() + "\0");
			}
			
			if(player.getTeamId() == match.getSetup().getHomeTeamId()) {
				Home_or_Away = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			}
			else {
				Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + " " + "\0");
			switch(captainWicketKeeper.toUpperCase())
			{
			case CricketUtil.WICKET_KEEPER:
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "WICKET-KEEPER" + ", " + Home_or_Away + "\0");
				break;
			case CricketUtil.CAPTAIN: 
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + ", " + Home_or_Away + "\0");
				break;
			case "PLAYER OF THE TOURNAMENT":
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + "\0");
				break;
			case "PLAYER OF THE SERIES":
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + "\0");
				break;
			case "PLAYER OF THE MATCH":
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + "\0");
				break;
			case CricketUtil.PLAYER:
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + Home_or_Away + "\0");
				break;
			case "CAPTAIN-WICKETKEEPER":
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "CAPTAIN & WICKET-KEEPER" + ", " + Home_or_Away + "\0");
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");	
			TimeUnit.MILLISECONDS.sleep(1200);
		}
	}
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats,List<Player> plyer, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		double strike_rate = 0;
		int omo_num = 0;
		String cont_name = "";
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20-I CAREER" + "\0");
			}else if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + 
						stats.getStats_type().getStats_short_name().toUpperCase() + " CAREER" + "\0");
			}
			

			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");

			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
//					if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\"  +match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + plyr.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//						this.status = CricketUtil.UNSUCCESSFUL;
//					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() 
						+  local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");

			}
			else {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
//					if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\" +match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + plyr.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//						this.status = CricketUtil.UNSUCCESSFUL;
//					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() 
						+  local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}

				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");

			}
			
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				
				cont_name = "$Dehighlight";
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

				if(plyer.get(plyr.getPlayerId()-1).getBattingStyle() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
							CricketFunctions.getbattingstyle(plyer.get(plyr.getPlayerId()-1).getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");
				
				strike_rate = stats.getRuns() * 100;
				strike_rate = strike_rate/stats.getBalls_faced();
				DecimalFormat df = new DecimalFormat("0.0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
				if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
				}
				
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.700 \0");
			

		} 

	}
	public void populatePlayerProfileBall(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats,List<Player> plyer, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException {
		
		int omo_num = 0;
		String cont_name = "";
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20-I CAREER" + "\0");
			}else if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + 
						stats.getStats_type().getStats_short_name().toUpperCase() + " CAREER" + "\0");
			}
			

			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");

			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
//					if(!new File("\\\\" + config.getPrimaryIpAddress() +"\\c\\Images\\APL\\Photos\\" + 
//							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + plyr.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//						this.status = CricketUtil.UNSUCCESSFUL;
//					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() 
						+  local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");

			}
			else {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
//					if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\" +match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + plyr.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//						this.status = CricketUtil.UNSUCCESSFUL;
//					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + config.getPrimaryIpAddress() 
					+  local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}

				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}

				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
				match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");

			}
			
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BOWLER:
				
				cont_name = "$Dehighlight";
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

				
				if(plyer.get(plyr.getPlayerId()-1).getBowlingStyle() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + 
							CricketFunctions.getbowlingstyle(plyer.get(plyr.getPlayerId()-1).getBowlingStyle()).toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + " " + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");
														
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatValue*GEOM*TEXT SET " + CricketFunctions.getEconomy(stats.getRuns_conceded(), stats.getBalls_bowled(), 2, "-") + "\0");
				
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.700 \0");
			

		} 

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
	public void populateLTPlayerProfile(PrintWriter print_writer,String viz_scene,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		//double bowler_strike_rate=0;
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20-I CAREER" + "\0");
			}else if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + 
						stats.getStats_type().getStats_short_name().toUpperCase() + " CAREER" + "\0");
			}
			
			

		Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
			}
			
		}
		else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
			}
		}
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BOWLER:
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECONOMY" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + 
					CricketFunctions.getEconomy(stats.getRuns_conceded(), stats.getBalls_bowled(), 2, "-") + "\0");
			
//			bowler_strike_rate = stats.getBalls_bowled() / stats.getWickets();
//			DecimalFormat df_bs = new DecimalFormat("0.0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
			if(stats.getBest_figures().equalsIgnoreCase("0") || stats.getBest_figures().isEmpty()) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_figures() + "\0");
			}
			break;	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");	
		

		}

	}
	public void populateLTPlayerProfileBat(PrintWriter print_writer,String viz_scene,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		double strike_rate = 0;
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20-I CAREER" + "\0");
			}else if(stats.getStats_type().getStats_short_name().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + 
						stats.getStats_type().getStats_short_name().toUpperCase() + " CAREER" + "\0");
			}
			
			

		Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
			}
		}
		else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
			}
		}
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
			
			
			strike_rate = stats.getRuns() * 100;
			strike_rate = strike_rate/stats.getBalls_faced();
			DecimalFormat df = new DecimalFormat("0.0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "STRIKE RATE" + "\0");
			if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");

			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
			if(stats.getBest_score().equalsIgnoreCase("0")|| stats.getBest_score().isEmpty()) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");

			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_score() + "\0");
			}
			break;	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");	
		

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
	
	public void populateSquad(PrintWriter print_writer,String viz_scene, int TeamId,MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		List<Player> squad = null;
		List<Player> otherSquad = null;
		Team team = null;
		if(TeamId == match.getSetup().getHomeTeamId()) {
			squad = match.getSetup().getHomeSquad();
			otherSquad = match.getSetup().getHomeOtherSquad();
			team = match.getSetup().getHomeTeam();
		}else if(TeamId == match.getSetup().getAwayTeamId()) {
			squad = match.getSetup().getAwaySquad();
			otherSquad = match.getSetup().getAwayOtherSquad();
			team = match.getSetup().getAwayTeam();
		}
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "SQUAD" + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + team.getTeamName1() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$noname$SubHeader*GEOM*TEXT SET " +
				match.getSetup().getTournament().toUpperCase() + "\0");
		
		for(int i = 12; i<=22; i++) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + "*ACTIVE SET 0" + "\0");
		}
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + team.getTeamName4() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "" + "\0");
		
		for(int i=1; i<=squad.size(); i++) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow"+i + " SET " + "1" + "\0");
			
			if(squad.get(i-1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
				if(squad.get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman" + "\0");
				}else if(squad.get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman_Lefthand" + "\0");
				}
			}else if(squad.get(i-1).getRole().equalsIgnoreCase("BAT/KEEPER")) {
				if(squad.get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman" + "\0");
				}else if(squad.get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman_Lefthand" + "\0");
				}
			}else if(squad.get(i-1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
				if(squad.get(i-1).getBowlingStyle() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowler" + "\0");
				}else {
					switch(squad.get(i-1).getBowlingStyle()) {
					case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowler" + "\0");
						break;
					case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "SpinBowlerIcon" + "\0");
						break;
					}
				}
			}else if(squad.get(i-1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
				if(squad.get(i-1).getBowlingStyle() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowlerAllrounder" + "\0");
				}else {
					switch(squad.get(i-1).getBowlingStyle()) {
					case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowlerAllrounder" + "\0");
						break;
					case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "SpinBowlerAllrounder" + "\0");
						break;
					}
				}
			}
			
			
			if(squad.get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
				if(squad.get(i-1).getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + squad.get(i-1).getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + squad.get(i-1).getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + squad.get(i-1).getFirstname() + "\0");
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$CaptainIcon*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$InternationalIcon*ACTIVE SET 0 \0");

			}
			else if(squad.get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
				if(squad.get(i-1).getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + squad.get(i-1).getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + squad.get(i-1).getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + squad.get(i-1).getFirstname() + "\0");
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Keeper" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$CaptainIcon*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$InternationalIcon*ACTIVE SET 0 \0");

			}
			else if(squad.get(i-1).getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
				if(squad.get(i-1).getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + squad.get(i-1).getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + squad.get(i-1).getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + squad.get(i-1).getFirstname() + "\0");
				}
				
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Keeper" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$CaptainIcon*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$InternationalIcon*ACTIVE SET 0 \0");

			}
			else {
				if(squad.get(i-1).getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + squad.get(i-1).getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + squad.get(i-1).getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + squad.get(i-1).getFirstname() + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$CaptainIcon*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + i + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$InternationalIcon*ACTIVE SET 0 \0");
			}
		}
		for(int i = 1; i<otherSquad.size(); i++) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + "*ACTIVE SET 1" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRow"+(i+11) + " SET " + "1" + "\0");
			
			if(otherSquad.get(i-1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
				if(otherSquad.get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman" + "\0");
				}else if(otherSquad.get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman_Lefthand" + "\0");
				}
			}else if(otherSquad.get(i-1).getRole().equalsIgnoreCase("BAT/KEEPER")) {
				if(otherSquad.get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman" + "\0");
				}else if(otherSquad.get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman_Lefthand" + "\0");
				}
			}else if(otherSquad.get(i-1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
				if(otherSquad.get(i-1).getBowlingStyle() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowler" + "\0");
				}else {
					switch(otherSquad.get(i-1).getBowlingStyle()) {
					case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowler" + "\0");
						break;
					case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "SpinBowlerIcon" + "\0");
						break;
					}
				}
			}else if(otherSquad.get(i-1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
				if(otherSquad.get(i-1).getBowlingStyle() == null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
							"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowlerAllrounder" + "\0");
				}else {
					switch(otherSquad.get(i-1).getBowlingStyle()) {
					case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowlerAllrounder" + "\0");
						break;
					case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "SpinBowlerAllrounder" + "\0");
						break;
					}
				}
			}
			if(otherSquad.get(i-1).getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + otherSquad.get(i-1).getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + otherSquad.get(i-1).getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
						"$RowAnimation$RowOmo$Dehighlight$TextAll$NameAll$LastName*GEOM*TEXT SET " + otherSquad.get(i-1).getFirstname() + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
					"$RowAnimation$RowOmo$Dehighlight$TextAll$RoleIcon*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
					"$RowAnimation$RowOmo$Dehighlight$TextAll$CaptainIcon*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + (i+11) + 
					"$RowAnimation$RowOmo$Dehighlight$TextAll$InternationalIcon*ACTIVE SET 0 \0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.590 \0");
	}
	
	public void populateDoubleteams(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateDoubleteams -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateDoubleteams -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			String cont = "";
			int row_id = 0, omo = 0;
			for(int k=2; k<=6; k++) {
				print_writer.println("-1 RENDERER*TREE*$Main$subs$Substutes$Row"+k+"*ACTIVE SET " + "0" + "\0");
			}
			for(int k=2; k<=6; k++) {
				print_writer.println("-1 RENDERER*TREE*$Main$subs$Substutes02$Row"+k+"*ACTIVE SET " + "0" + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " +
					match.getSetup().getMatchIdent() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "TEAMS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$noname$SubHeader*GEOM*TEXT SET " +
					match.getSetup().getTournament().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstitueHead" + " SET " + "SUBSTITUTES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName1" + " SET " + match.getSetup().getHomeTeam().getTeamName1() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName2" + " SET " + match.getSetup().getAwayTeam().getTeamName1() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$subs$Substutes$Row2$InternationalIcon*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$subs$Substutes02$Row2$InternationalIcon*ACTIVE SET " + "0" + "\0");
			
			for(int i = 1; i <= 2 ; i++) {
				if(i == 1) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamNameGrp1$RowAnimation$TeamNameGrp"
						+ "$NameAll$TeamFirstName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamRefName" + " SET " + logo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
							+ logo_path + "TLogo" + "\0");
					
					for(Player hs : match.getSetup().getHomeSquad()) {
						row_id = row_id + 1;
						omo = 0;
						cont = "Dehighlight";
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
								"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo + "\0");

						if(hs.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if(hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman" + "\0");
							}else if(hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman_Lefthand" + "\0");
							}
						}else if(hs.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if(hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman" + "\0");
							}else if(hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Batsman_Lefthand" + "\0");
							}
						}else if(hs.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if(hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowler" + "\0");
							}else {
								switch(hs.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowler" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "SpinBowlerIcon" + "\0");
									break;
								}
							}
						}else if(hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if(hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowlerAllrounder" + "\0");
							}else {
								switch(hs.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "FastBowlerAllrounder" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						
						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Keeper" + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Keeper" + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");
						}
					}
					for (int j = 0; j <= match.getSetup().getHomeSubstitutes().size() - 1; j++) {
						print_writer.println("-1 RENDERER*TREE*$Main$subs$Substutes$Row"+(j+2)+"*ACTIVE SET " + "1" + "\0");
						if(match.getSetup().getHomeSubstitutes().get(j).getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstituteFirstName"+(j+1) + " SET " + match.getSetup().getHomeSubstitutes().get(j).getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstituteLastName"+(j+1) + " SET " + match.getSetup().getHomeSubstitutes().get(j).getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstituteFirstName"+(j+1) + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstituteLastName"+(j+1) + " SET " + match.getSetup().getHomeSubstitutes().get(j).getFirstname() + "\0");
						}
						
						System.out.println("HOME ROLE : "+match.getSetup().getHomeSubstitutes().get(i - 1).getRole()+" : "+match.getSetup().getHomeSubstitutes().get(i - 1).getBowlingStyle()+" : "+match.getSetup().getHomeSubstitutes().get(i - 1).getBattingStyle());
						if (match.getSetup().getHomeSubstitutes().get(j).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (match.getSetup().getHomeSubstitutes().get(j).getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (match.getSetup().getHomeSubstitutes().get(j).getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERE*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (match.getSetup().getHomeSubstitutes().get(j).getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (match.getSetup().getHomeSubstitutes().get(j).getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (match.getSetup().getHomeSubstitutes().get(j).getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (match.getSetup().getHomeSubstitutes().get(j).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (match.getSetup().getHomeSubstitutes().get(j).getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (match.getSetup().getHomeSubstitutes().get(j).getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
											+ " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
											+ " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (match.getSetup().getHomeSubstitutes().get(j).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (match.getSetup().getHomeSubstitutes().get(j).getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (match.getSetup().getHomeSubstitutes().get(j).getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
											+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole" + (j+1)
											+ " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
					}
					
				} else {
					row_id = 0;
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamNameGrp2$RowAnimation$TeamNameGrp$NameAll$TeamFirstName*GEOM*TEXT SET " 
											+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
							+ logo_path + "TLogo" + "\0");

					for(Player as : match.getSetup().getAwaySquad()) {
						row_id = row_id + 1;
						omo = 0;
						cont = "Dehighlight";
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo + "\0");

						if(as.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if(as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman" + "\0");
							}else if(as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(as.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if(as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman" + "\0");
							}else if(as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(as.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if(as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" + "\0");
							}else {
								switch(as.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						}else if(as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if(as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							}else {
								switch(as.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						
						
						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getFirstname() + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getFirstname() + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path+"/" + "Keeper" + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getFirstname() + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path +"/"+ "Keeper" + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getSurname() + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
					}
					for (int j = 0; j <= match.getSetup().getAwaySubstitutes().size() - 1; j++) {
						print_writer.println("-1 RENDERER*TREE*$Main$subs$Substutes02$Row"+(j+2)+"*ACTIVE SET " + "1" + "\0");
						if(match.getSetup().getAwaySubstitutes().get(j).getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstitute2FirstName"+(j+1) + " SET " + match.getSetup().getAwaySubstitutes().get(j).getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstitute2LastName"+(j+1) + " SET " + match.getSetup().getAwaySubstitutes().get(j).getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstitute2FirstName"+(j+1) + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstitute2LastName"+(j+1) + " SET " +match.getSetup().getAwaySubstitutes().get(j).getFirstname() + "\0");
						}
						
						
						if (match.getSetup().getAwaySubstitutes().get(j).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (match.getSetup().getAwaySubstitutes().get(j).getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (match.getSetup().getAwaySubstitutes().get(j).getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERE*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (match.getSetup().getAwaySubstitutes().get(j).getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (match.getSetup().getHomeSubstitutes().get(j).getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (match.getSetup().getAwaySubstitutes().get(j).getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (match.getSetup().getAwaySubstitutes().get(j).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (match.getSetup().getAwaySubstitutes().get(j).getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (match.getSetup().getAwaySubstitutes().get(j).getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
											+ " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
											+ " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (match.getSetup().getAwaySubstitutes().get(j).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (match.getSetup().getAwaySubstitutes().get(j).getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (match.getSetup().getAwaySubstitutes().get(j).getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
											+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstitute2Role" + (j+1)
											+ " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
					}
				}
			}
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + 
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + 
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.590 \0");	
		}
	}
	public void populateCaptains(PrintWriter print_writer,String viz_scene, List<Team> teams,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateDoubleteams -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateDoubleteams -> inning is null");
		} else {
			int row_id=0;
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + match.getSetup().getTournament() + "\0");
			
			for(Team team : teams) {
				row_id = row_id + 1;
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCaptainName0" + row_id + " SET " + 
						team.getCaptains().replace("_", " ") + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName0" + row_id + " SET " + team.getTeamName1() + "\0");
				
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgCaptain" + row_id + " SET " + photo_path + 
							team.getTeamName4() + "\\" + team.getCaptains() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\" +team.getTeamName4() + "\\" + team.getCaptains()+ CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgCaptain" + row_id + " SET " + "\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + 
							team.getTeamName4() + "\\\\" + team.getCaptains() + CricketUtil.PNG_EXTENSION + "\0");
				}
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo" + row_id + " SET " + logo_path + 
						team.getTeamName4() + "\0");

			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.590 \0");	
		}
	}
	public void populateTopPerformer(PrintWriter print_writer, String viz_scene, List<Performer> performer, List<Player> players, List<Team> teams,List<VariousText> variousText, MatchAllData match, 
			String broadcaster, Configuration config) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateDoubleteams -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateDoubleteams -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(VariousText vt : variousText) {
				if(vt.getVariousType().equalsIgnoreCase("KEYPERFORMERHEADER") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + vt.getVariousText() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + vt.getHindiVariousText() + "\0");
				}
				else if(vt.getVariousType().equalsIgnoreCase("KEYPERFORMERHEADER") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + "ANDHRA PREMIER LEAGUE" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "KEY PERFORMER" + "\0");
				}
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + "" + "\0");
			
			for(int i=1;i<=performer.size();i++) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwardName0" + i + " SET " + 
						performer.get(i-1).getPerformer_type() + "\0");
				
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage0" + i + " SET " + photo_path + 
							teams.get(players.get(performer.get(i-1).getPlayerId()-1).getTeamId()-1).getTeamName4() + "\\" + 
							players.get(performer.get(i-1).getPlayerId()-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
//					if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\APL\\Photos\\" + 
//							teams.get(players.get(preformer.get(i-1).getPerformer_Id()-1).getTeamId()-1).getTeamName4() + "\\" + 
//							players.get(preformer.get(i-1).getPlayerName()-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//						this.status = CricketUtil.UNSUCCESSFUL;
//					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage0" + i + " SET " + "\\\\\\" + config.getPrimaryIpAddress() 
						+ local_photo_path + teams.get(players.get(performer.get(i-1).getPlayerId()-1).getTeamId()-1).getTeamName4() + "\\\\" + 
							players.get(performer.get(i-1).getPlayerId()-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tName0" + i + " SET " + 
						players.get(performer.get(i-1).getPlayerId()-1).getTicker_name() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName0" + i + " SET " + 
						teams.get(players.get(performer.get(i-1).getPlayerId()-1).getTeamId()-1).getTeamName1() + "\0");
				
				if(performer.get(i-1).getText1Value() != null && performer.get(i-1).getText2Value() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValueA0" + i + " SET " + performer.get(i-1).getText1Value() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeacdA0" + i + " SET " + performer.get(i-1).getText1Head() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValueB0" + i + " SET " + performer.get(i-1).getText2Value() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeacdB0" + i + " SET " + performer.get(i-1).getText2Head() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValueA0" + i + " SET " + performer.get(i-1).getText1Value() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeacdA0" + i + " SET " + performer.get(i-1).getText1Head() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValueB0" + i + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHeacdB0" + i + " SET " + "" + "\0");
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.500\0");	
		}
	}
	
	public void hideAndShowContainer(String broadcaster, String which_graphics, PrintWriter print_writer) {
		
		switch (which_graphics.toUpperCase()) {
		case "INFOBAR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0" + "\0");
			break;
		}
	}
	
	public Infobar populateInfobarIdent(Infobar infobar, boolean is_this_updating, String viz_scene,PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster)
	{
	    switch(infobar.getIdent_section().toUpperCase()) {
	    case CricketUtil.TOSS:
	    	if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
	    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
						+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
						+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
			}
	    	
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
									+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");

	    	break;
	    
	    case "RESULT":
	    	
	    	for(Inning inn : match.getMatch().getInning()) {
	    		if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
	    			if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " + 
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
							}
							
						}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
								|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " + 
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
							}
						}
						else{
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
								+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
								" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
							
						}
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " + 
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
								
							}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
								
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");	
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");	
									}
								}
							}
							
							else{
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");									
							}
						}
						else {
							if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED - " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
							}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| Double.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), match.getMatch().getInning().get(1).getTotalBalls())) 
									>= Double.valueOf(match.getSetup().getTargetOvers())) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + "MATCH TIED" + "\0");											
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												match.getMatch().getMatchStatus().toUpperCase() + "\0");						
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
							}
							else{
								if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (VJD)" + "\0");
								}
								else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (DLS)" + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
								}
							}
						}
					}
	    		}
	    	}
	    	
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    
	    case "TARGET":
	    	
	    	for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
								+ "TARGET" + " " + CricketFunctions.GetTargetData(match).getTargetRuns() + " (VJD)" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
								+ "TARGET" + " " + CricketFunctions.GetTargetData(match).getTargetRuns() + " (DLS)" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
								+ "TARGET" + " " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
					}
				}
			}
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    
	    case "VENUE":
	    	
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
					+ match.getSetup().getVenueName().toUpperCase() + "\0");
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    
	    case "TOURNAMENT":
	    	
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
					+ match.getSetup().getTournament().toUpperCase() + "\0");
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    case CricketUtil.SUPER_OVER:
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
					+ "SUPER OVER" + "\0");
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    }
	    
		return infobar;
	}
	public Infobar populateInfobar(Infobar infobar, PrintWriter print_writer,String scene, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Infobar's inning is null";
		} else {
			
			infobar = populateInfobarTeamScore(infobar,false, print_writer, match, broadcaster);
			infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, broadcaster);
			infobar = populateVizInfobarRightTop(infobar, false, print_writer, match, broadcaster);
			infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
		}
		return infobar;
	}
	public Infobar populateInfobarTeamScore(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster)
	{
    	
		for(Inning inn : match.getMatch().getInning()) {
			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET "  + "IMAGE*/Default/APL/Logos/" + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET "  + "IMAGE*/Default/APL/Logos/" + 
							inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatTeamName" + " SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
					
					if(match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.DLS) || match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.VJD)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$noname$DLS*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + 
				    			"(" + match.getSetup().getTargetOvers() + ") " + match.getSetup().getTargetType().toUpperCase() + "\0");
				    }else {
					    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + " " + "\0");
				    	if(match.getSetup().getTargetOvers() != null && !match.getSetup().getTargetOvers().isEmpty()) {
				    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$noname$DLS*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + 
					    			"(" + match.getSetup().getTargetOvers() + ") " + "\0");
				    	}
				    }
				}
			
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + " SET " + 
						CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				
			    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamOvers" + " SET " + CricketFunctions.
			    		OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
			    
			    if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && match.getSetup().getMaxOvers() == 1) {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$BatTeamNameAndScoreGrp$noname$PowerPlay*ACTIVE SET 0 \0");
			    }else {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$BatTeamNameAndScoreGrp$noname$PowerPlay*ACTIVE SET 1 \0");
			    	if(!CricketFunctions.processPowerPlay(CricketUtil.MINI,match).isEmpty()) {
						 if(infobar.isPowerplay_on_screen() == true) {
							 break;
				         }
				         else {
				        	 infobar.setPowerplay_on_screen(true);
				        	 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START \0");
				         }
					}
					else {
						if(infobar.isPowerplay_on_screen() == true) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayOut START \0");
							infobar.setPowerplay_on_screen(false);
				         }
					}
			    }
			}
		}
			
		return infobar;
	}
	public Infobar populateVizInfobarMiddle(Infobar infobar, boolean is_this_updating, PrintWriter print_writer,MatchAllData match, String broadcaster)
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
				
				if(current_batsmen != null && current_batsmen.size() >= 2) {
					if (CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),inn.getInningNumber(), current_batsmen.get(0).getPlayerId())
							.equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON vImpactBatsman01 SET " + "1" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON vImpactBatsman01 SET " + "0" + "\0");
					}
					
					if (CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(),inn.getInningNumber(), current_batsmen.get(1).getPlayerId())
							.equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON vImpactBatsman02 SET " + "1" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON vImpactBatsman02 SET " + "0" + "\0");
					}
					
		
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName1 SET " + current_batsmen.get(0).getPlayer().getTicker_name() + "\0");
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanScore1 SET " + current_batsmen.get(0).getRuns() + "\0");
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanBall1 SET " + current_batsmen.get(0).getBalls() + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanName2 SET " + current_batsmen.get(1).getPlayer().getTicker_name() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanScore2 SET " + current_batsmen.get(1).getRuns() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBatsmanBall2 SET " + current_batsmen.get(1).getBalls() + "\0");
					
					if(current_batsmen.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike*FUNCTION*Omo*vis_con SET " + "1" + " \0");
						}
					}
					if(current_batsmen.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike*FUNCTION*Omo*vis_con SET " + "2" + " \0");
						}	
					}
					if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
						processAnimation(print_writer, "Batsman1Dehighlight", "SHOW 0.260", broadcaster);
					} else if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						processAnimation(print_writer, "Batsman1Highlight", "SHOW 0.160", broadcaster);
					}
					if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
						processAnimation(print_writer, "Batsman2Dehighlight", "SHOW 0.260", broadcaster);
					} else if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						processAnimation(print_writer, "Batsman2Highlight", "SHOW 0.160", broadcaster);
					}
				}
			}
		}
			
		infobar.setLast_batsmen(current_batsmen);
		return infobar;
	}
	public Infobar populateVizInfobarRight(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, List<MatchAllData> tourn_matches,MatchAllData match, String broadcaster) throws CloneNotSupportedException 
	{
		
		switch(infobar.getBottom_right_section().toUpperCase()) {
		case CricketUtil.DOT:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDotBallHead" + " SET " + "DOTS THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDotBallCounter" + " SET " + 
					CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, inn.getInningNumber(), 0, ",", match.getEventFile().getEvents()).split(",")[0] + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.DOT);	
			break;
		case "TOURNAMENT_FOURS":
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					String fours = String.valueOf(CricketFunctions.extracttournamentFoursAndSixes("COMBINED_PAST_CURRENT_MATCH_DATA", 
							tourn_matches, match, null).getTournament_fours());
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterHead" + " SET " + "FOURS THIS TOURNAMENT" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterCounter" + " SET " + 
					fours + "\0");
				}
			}
			infobar.setLast_bottom_right_section("TOURNAMENT_FOURS");
			break;
		case CricketUtil.FOUR:
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterHead" + " SET " + "FOURS THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterCounter" + " SET " + inn.getTotalFours() + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.FOUR);
			break;
		case "TOURNAMENT_SIXES":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					String sixes = String.valueOf(CricketFunctions.extracttournamentFoursAndSixes("COMBINED_PAST_CURRENT_MATCH_DATA", 
							tourn_matches, match, null).getTournament_sixes());
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterHead" + " SET " + "SIXES THIS TOURNAMENT" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterCounter" + " SET " + sixes + "\0");
				}
			}
			infobar.setLast_bottom_right_section("TOURNAMENT_SIXES");
			break;
		case CricketUtil.SIX:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterHead" + " SET " + "SIXES THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterCounter" + " SET " + inn.getTotalSixes() + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.SIX);
			break;
		case CricketUtil.COMPARE:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tComparisonTeam" + " SET " + 
							inn.getBowling_team().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tComparisonRuns" + " SET " +
							CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.COMPARE);
			break;
		case "TARGET_2":
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Section4_N_5$Target02$Target$TargetHead*ACTIVE SET 1 \0");
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase(CricketUtil.VJD)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + " (" + CricketUtil.VJD + ")" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase(CricketUtil.DLS)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + " (" + CricketUtil.DLS + ")" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
					}
				}
			}
			infobar.setLast_bottom_right_section("TARGET_2");
			break;
		case "TOURNAMENT-NAME":	

			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Section4_N_5$Target02$Target$TargetHead*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + "APL 2023" + "\0");
					
			infobar.setLast_bottom_right_section("TOURNAMENT-NAME");
			break;
		}		
			
		return infobar;
	}
	public Infobar populateVizInfobarRightTop(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster) throws InterruptedException
	{
		
		switch(infobar.getBottom_right_top_section().toUpperCase()) {
		case CricketUtil.BOWLER:
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BowlerbaseGrp$TopLIne*ACTIVE SET " + "0" + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") 
								|| boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
								processAnimation(print_writer, "ALL_SECTION$Section4In", "CONTINUE", broadcaster);
								processAnimation(print_writer, "ALL_SECTION$Section5In", "CONTINUE", broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vImpactBowler" + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vImpactBowler" + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBowlerName SET " + boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBowlerFigure SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBowlerOvers SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
								processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
								processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
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
	public Infobar populateVizInfobarRightBottom(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster) throws InterruptedException
	{
		switch(infobar.getBottom_right_bottom_section().toUpperCase()) {
//		case CricketUtil.OVER:
//			int Player_id=0;
//			
//			for(Inning inn : match.getMatch().getInning()) {
//				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){						
//					
//					for(BowlingCard boc : inn.getBowlingCard()) {
//						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)) {
//							Player_id = boc.getPlayerId();
//						}
//					}
//					
//					String[] this_over = CricketFunctions.getEventsText(CricketUtil.OVER,Player_id,",", match.getEventFile().getEvents(),0).split(",");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vThisOver" + " SET " + this_over.length + "\0");
//
//					for(int i=0;i < this_over.length;i++) {
//
//						if(this_over[i].toUpperCase().equalsIgnoreCase("WD+W") || this_over[i].toUpperCase().equalsIgnoreCase("W") 
//								|| this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.FOUR) || this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.SIX)) {
//							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
//									+ (i+1) + "*FUNCTION*Omo*vis_con SET 3 \0");
//							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
//						}else if(this_over[i].toUpperCase().equalsIgnoreCase("WD") || this_over[i].toUpperCase().equalsIgnoreCase("NB")
//								 || this_over[i].toUpperCase().contains("B") || this_over[i].toUpperCase().contains("LB") || this_over[i].toUpperCase().contains("Pn")) {
//							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
//									+ (i+1) + "*FUNCTION*Omo*vis_con SET 5 \0");
//							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
//						} else {
//							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
//									+ (i+1) + "*FUNCTION*Omo*vis_con SET 1 \0");
//							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
//						}
//					}
//				}
//			}
//			infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
//			break;
		
		case CricketUtil.OVER:
			
			int Player_id=0;
			over_size = 0;
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){						
					
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)) {
							Player_id = boc.getPlayerId();
						}
					}
					//String over=This_over(match, match.getEventFile().getEvents());
					String[] this_over = CricketFunctions.getEventsText(CricketUtil.OVER,Player_id,",", match.getEventFile().getEvents(),0).split(",");
					over_size = this_over.length;
					
					if(this_over.length==1 && this_over[0] == "") {
						 if(infobar.getLast_bottom_right_top_section() != null && infobar.getLast_bottom_right_bottom_section() != null 
									&& !infobar.getLast_bottom_right_top_section().trim().isEmpty() 
									&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // Normal change on
							 
							 if(infobar.getLast_bottom_right_bottom_section().toUpperCase().equalsIgnoreCase(CricketUtil.OVER)) {
								 switch(infobar.getLast_bottom_right_bottom_section().toUpperCase()){
									case CricketUtil.OVER:
										processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
										break;
								 }
								 TimeUnit.MILLISECONDS.sleep(200);
								 infobar.setBottom_right_bottom_section("LASTOVERRUNS");
								 infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
								 switch(infobar.getBottom_right_bottom_section().toUpperCase()){
									case "LASTOVERRUNS":
										processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
										lastOverOnScreen = true;
										infobar.setLast_bottom_right_bottom_section("LASTOVERRUNS");
										break;
									}
							 }else if(infobar.getLast_bottom_right_bottom_section().toUpperCase().equalsIgnoreCase("THISOVER")){
								 infobar.setBottom_right_bottom_section("LASTOVERRUNS");
								 processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
								 infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
								 TimeUnit.MILLISECONDS.sleep(500);
								 processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
							 }else {
								 if(infobar.getLast_bottom_right_bottom_section().toUpperCase().equalsIgnoreCase("LASTOVERRUNS")) {
									 processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
										TimeUnit.MILLISECONDS.sleep(200);
										processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
										//infobar.setBottom_right_bottom_section(CricketUtil.OVER);
										infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
								 }
							 }
						}
					}else {
						infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
					}
					if(this_over.length<7) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vThisOver" + " SET " + this_over.length + "\0");
						
						for(int i=0;i < this_over.length;i++) {
							if(this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.FOUR) || this_over[i].toUpperCase().equalsIgnoreCase(CricketUtil.SIX) || this_over[i].toUpperCase().contains("BOUNDARY")) {
								if(this_over[i].toUpperCase().contains("BOUNDARY")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i].replace("BOUNDARY", "") + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
											+ (i+1) + "*FUNCTION*Omo*vis_con SET 3 \0");
								}else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
											+ (i+1) + "*FUNCTION*Omo*vis_con SET 1 \0");
								}
							}
							else if(this_over[i].toUpperCase().contains("+W")|| this_over[i].toUpperCase().equalsIgnoreCase("W")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
										+ (i+1) + "*FUNCTION*Omo*vis_con SET 3 \0");
							}
							else if(this_over[i].toUpperCase().contains("WD") || this_over[i].toUpperCase().contains("NB")
									 || this_over[i].toUpperCase().contains("B") || this_over[i].toUpperCase().contains("LB")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
										+ (i+1) + "*FUNCTION*Omo*vis_con SET 5 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
							}
							else if(this_over[i].toUpperCase().contains("PN")) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
										+ (i+1) + "*FUNCTION*Omo*vis_con SET 5 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + 
										this_over[i].replace("PN", "P") + "\0");
							}
							else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
										+ (i+1) + "*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
							}
						}
					}else {
						switch(infobar.getLast_bottom_right_bottom_section().toUpperCase()){
						case CricketUtil.OVER:
							processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
							break;
						}
						 TimeUnit.MILLISECONDS.sleep(200);
						 infobar.setBottom_right_bottom_section("THISOVERRUNS");
						 infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
						 switch(infobar.getBottom_right_bottom_section().toUpperCase()){
							case "THISOVERRUNS":
								processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
								lastOverOnScreen = true;
								infobar.setLast_bottom_right_bottom_section("THISOVERRUNS");
								break;
							}
						}
				}
			}
//			infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
			break;
		case "ECONOMY":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
							if(boc.getEconomyRate() == null) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerEconomy" + " SET " + "-" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerEconomy" + " SET " + boc.getEconomyRate() + "\0");
							}
						}
					}
				}
			}
			infobar.setLast_bottom_right_bottom_section("ECONOMY");
			break;
		case "THISOVERRUNS":
			int thisoverBowlNum = -1;
			int thisoverRuns = 0;
			int thisOverBowlerId= 0;
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)) {
							thisOverBowlerId = boc.getPlayerId();
						}
					}
				}
			}
			String[] this_over_data = CricketFunctions.getEventsText(CricketUtil.OVER,thisOverBowlerId,",", match.getEventFile().getEvents(),0).split(",");
			if(this_over_data.length==1 && this_over_data[0] == "") {
				if(infobar.getLast_bottom_right_bottom_section().toUpperCase().equalsIgnoreCase("THISOVERRUNS")){
					processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setBottom_right_bottom_section("LASTOVERRUNS");
					infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
					processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
					infobar.setLast_bottom_right_bottom_section("LASTOVERRUNS");
					break;
				}
			}else {
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						for (BowlingCard boc : inn.getBowlingCard()) {
							switch (boc.getStatus().toUpperCase()) {
							case CricketUtil.CURRENT + CricketUtil.BOWLER:
							case CricketUtil.LAST + CricketUtil.BOWLER:
								if (boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlingEnd" + " SET " + 
											"THIS OVER - " + CricketFunctions.processThisOverRunsCount(boc.getPlayerId(),match.getEventFile().getEvents()).split("-")[0]+ "\0");
								}
								break;
							}
						}
					}
				}
				infobar.setLast_bottom_right_bottom_section("THISOVERRUNS");
			}
				
			break;
		case "LASTOVERRUNS":
			int bowlerNum = -1;
			int totalRuns = 0;
			int bowlId= 0;
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)) {
							bowlId = boc.getPlayerId();
						}
					}
				}
			}
			String[] this_over = CricketFunctions.getEventsText(CricketUtil.OVER,bowlId,",", match.getEventFile().getEvents(),0).split(",");
			if(this_over.length>=1 && this_over[0] != "") {
				if(infobar.getLast_bottom_right_bottom_section().toUpperCase().equalsIgnoreCase("LASTOVERRUNS")){
					processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setBottom_right_bottom_section(CricketUtil.OVER);
					infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
					processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
					break;
				}
			}
			for (int i = match.getEventFile().getEvents().size()-1; i >= 0; i--) {	
				if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.NEW_BATSMAN)) {
					continue;
				}
				if(bowlerNum != -1 && match.getEventFile().getEvents().get(i).getEventBowlerNo() != bowlerNum) {
					break;
				}
				if(match.getEventFile().getEvents().get(i).getEventBowlerNo() == bowlerNum) {
					switch (match.getEventFile().getEvents().get(i).getEventType()) {
					case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  
					case CricketUtil.FIVE : case CricketUtil.DOT:
						totalRuns += match.getEventFile().getEvents().get(i).getEventRuns();
						break;
					case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY:
						totalRuns += match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
						break;
					 case CricketUtil.LOG_WICKET:
						 totalRuns += match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns();
						 break;
					 case CricketUtil.LOG_ANY_BALL:
						 totalRuns += match.getEventFile().getEvents().get(i).getEventRuns();
				          if (match.getEventFile().getEvents().get(i).getEventExtra() != null) {
				        	  totalRuns += match.getEventFile().getEvents().get(i).getEventExtraRuns();
				          }
				          if (match.getEventFile().getEvents().get(i).getEventSubExtra() != null) {
				        	  totalRuns += match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
				          }
						 break;
					}
				}else if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.END_OVER)) {
					bowlerNum = match.getEventFile().getEvents().get(i).getEventBowlerNo();
				}
			}
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlingEnd" + " SET " + 
					"LAST OVER - " + totalRuns + " RUN"+CricketFunctions.Plural(totalRuns).toUpperCase()+ "\0");
			infobar.setLast_bottom_right_bottom_section("LASTOVERRUNS");
				
			break;	
		case "BOWLINGEND":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER) || (boc.getStatus().equalsIgnoreCase(CricketUtil.LAST + CricketUtil.BOWLER))) {
							if(boc.getBowling_end() == 1) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlingEnd" + " SET " + 
										match.getSetup().getGround().getFirst_bowling_end().toUpperCase() + " END"+ "\0");
							}else if(boc.getBowling_end() == 2) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlingEnd" + " SET " + 
										match.getSetup().getGround().getSecond_bowling_end().toUpperCase() + " END" + "\0");
							}
						}
					}
				}
			}
			infobar.setLast_bottom_right_bottom_section("BOWLINGEND");
			break;
		}
		return infobar;
	}
	public Infobar populateVizInfobarTop(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster)
	{		
		if(is_this_updating == false) {
			hideAndShowContainer(broadcaster, "INFOBAR", print_writer);
		}
		
		switch(infobar.getTop_section().toUpperCase()) {
		case "CRR":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR$CurRunRate$CURRHead*GEOM*TEXT SET " + 
							"CURRENT RUN RATE" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR$CurRunRate$CURRValue*GEOM*TEXT SET " + 
							inn.getRunRate() + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 1"+"\0");
				processAnimation(print_writer, "Section2$CurRunRateIn", "START", broadcaster);
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$CurRunRateIn START \0");
			}
			break;
		case "VS_BOWLING_TEAM":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0" + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$BowlingTeamIn START \0");
					}
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp$TeamName2*GEOM*TEXT SET " + 
							inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
				}
			}
			break;
		case "CRR_RRR":
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$CURRHead*GEOM*TEXT SET " + 
							"CURRENT RUN RATE" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$CURRValue*GEOM*TEXT SET " + 
							inn.getRunRate() + "\0");
				}
			}
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$REQRValue*GEOM*TEXT SET " + 
			                       CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + "\0");
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 1"+"\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*CRR_RRRIn START \0");
				processAnimation(print_writer, "Section2$CRR_RRRIn", "START", broadcaster);
			}
			
			break;
		case "EXTRAS":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tExtrasHead" + " SET " + "EXTRAS - " + 
							inn.getTotalExtras() + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tWide" + " SET " + inn.getTotalWides() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNoBalls" + " SET " + inn.getTotalNoBalls() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBye" + " SET " +  inn.getTotalByes() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLegBye" + " SET " + inn.getTotalLegByes() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPenalties" + " SET " + inn.getTotalPenalties() + "\0");
					
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ExtrasIn START \0");
				processAnimation(print_writer, "Section2$ExtrasIn", "START", broadcaster);
			}
			break;
		case "FIRST_INNING_SCORE":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 1) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistInnTeam" + " SET " + 
							inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistInnScore" + " SET " +
							CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistInnOvers" + " SET " + 
							"(" + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ")" + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FistInnScoreIn START \0");
				processAnimation(print_writer, "Section2$FistInnScoreIn", "START", broadcaster);
			}
			break;
		
		case CricketUtil.BOUNDARY:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallSinceLastBoundary" + " SET " + 
							CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber()) + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*BallsSinceLastBoundaryIn START \0");
				processAnimation(print_writer, "Section2$BallsSinceLastBoundaryIn", "START", broadcaster);
			}
			break;
			
		case CricketUtil.TOSS:
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_HEAD*GEOM*TEXT SET " +
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$LeftBlueBase*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Team*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$Saperator*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Result*ACTIVE SET " + "0" + "\0");

			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_HEAD*GEOM*TEXT SET " +
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$LeftBlueBase*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Team*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$Saperator*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Result*ACTIVE SET " + "0" + "\0");
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$TossIn START \0");
				processAnimation(print_writer, "Section2$TossIn", "START", broadcaster);
			}
			break;
		case "TARGET":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + " (VJD)" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + " (DLS)" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLSTargetScore" + " SET " 
								+ CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDlOvers" + " SET " + " " + "\0");
					}
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 1" + "\0");
				processAnimation(print_writer, "Section2$DLSTargetIn", "START", broadcaster);
			}
			break;
		case "EQUATION":
			if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || CricketFunctions.getWicketsLeft(match, 2) <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall()  == 0) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours*ACTIVE SET 0" + "\0");
				
				if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + 
							match.getMatch().getInning().get(1).getBowling_team().getTeamName1().toUpperCase() + " WIN BY SUPER OVER" + "\0");
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,false).getTargetOrResult().toUpperCase() + "\0");
				}
			}
			else{
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours*ACTIVE SET 1" + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "NEED" + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours$1$NEEDRUNS*GEOM*TEXT SET " + 
						CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRunHead" + " SET " + "RUN" +
						CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
				if(!match.getSetup().getTargetOvers().equalsIgnoreCase("")) {
				//if(!match.getTargetOvers().equalsIgnoreCase("") || Double.valueOf(match.getTargetOvers()) > 0) {
					if(match.getSetup().getTargetOvers().contains(".")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
								((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1])-
										(match.getMatch().getInning().get(1).getTotalOvers()*6 + match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
						if(!match.getSetup().getTargetType().equalsIgnoreCase("")) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
									CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1])-
											(match.getMatch().getInning().get(1).getTotalOvers()*6 + match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase()+ " ("+match.getSetup().getTargetType().toUpperCase()+")"  + "\0");
						}else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
									CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1])-
											(match.getMatch().getInning().get(1).getTotalOvers()*6 + match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase()  + "\0");
						}
						
					}else {
						if(!match.getSetup().getTargetType().equalsIgnoreCase("")) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
									CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers())*6)-
										(match.getMatch().getInning().get(1).getTotalOvers()*6 + match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase()+ " ("+match.getSetup().getTargetType().toUpperCase()+")"  + "\0");
						}else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
									CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers())*6)-
										(match.getMatch().getInning().get(1).getTotalOvers()*6 + match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase()  + "\0");
						}
						
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
								((Integer.valueOf(match.getSetup().getTargetOvers())*6) - (match.getMatch().getInning().get(1).getTotalOvers()*6 + match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
						
					}
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
							((match.getSetup().getMaxOvers()*6) - (match.getMatch().getInning().get(1).getTotalOvers()*6 + match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
							CricketFunctions.Plural(((match.getSetup().getMaxOvers()*6) - (match.getMatch().getInning().get(1).getTotalOvers()*6
									+ match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + "\0");
				}
				
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 1"+"\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$EquationIn START \0");
				processAnimation(print_writer, "Section2$EquationIn", "START", broadcaster);
			}

			break;
		case "PROJECTED":
			String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
		    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
		    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
	        }
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedHead1" + " SET " + "@" + proj_score_rate[0] +" (CRR)" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedValue1" + " SET " + proj_score_rate[1] + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedHead2" + " SET " + "@" + proj_score_rate[2] + " RPO" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedValue2" + " SET " + proj_score_rate[3] + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedHead3" + " SET " + "@" + proj_score_rate[4] + " RPO" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedValue3" + " SET " + proj_score_rate[5] + "\0");
			
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$ProjectedIn START \0");
				processAnimation(print_writer, "Section2$ProjectedIn", "START", broadcaster);
			}
			
			break;
		case "BOUNDARIES":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFoursValue" + " SET " + inn.getTotalFours() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixValue" + " SET " + inn.getTotalSixes() + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$BoundariesIn START \0");
				processAnimation(print_writer, "Section2$BoundariesIn", "START", broadcaster);
			}
			
			break;
		case "PARTNERSHIP":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartnershipSmallRuns" + " SET " 
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartnershipSmallBalls" + " SET " 
										+ "(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartFoursValue" + " SET " 
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalFours() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartSixValue" + " SET " 
										+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalSixes() + "\0");
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 1" + "\0");
				//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Section2$PartnershipIn START \0");
				processAnimation(print_writer, "Section2$PartnershipIn", "START", broadcaster);
			}
			
			break;
		case "LAST_WICKET":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
					for(BattingCard bc : inn.getBattingCard()){
						if(inn.getFallsOfWickets().size() > 0){
							if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketPlayerName" + " SET " + bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketBalls" + " SET " + "(" + bc.getBalls() + ")" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketRuns" + " SET " + bc.getRuns() + "\0");
							}
						}								
					}
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 1"+"\0");
				processAnimation(print_writer, "Section2$LastWicketIn", "START", broadcaster);
			}
			
			break;
		case CricketUtil.TIMELINE:
			String this_ball_data="";
			int ball_count=0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(((inn.getTotalOvers()*6) + inn.getTotalBalls()) > 26) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " + "26" + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " 
											+ ((inn.getTotalOvers()*6) + inn.getTotalBalls()) + "\0");
					}
						
					if ((match.getEventFile().getEvents() != null) && (match.getEventFile().getEvents().size() > 0)) {
						  for (int i=match.getEventFile().getEvents().size() - 1; i>=0; i--)
						  {  
							
							switch(match.getEventFile().getEvents().get(i).getEventType()) {
							case CricketUtil.CHANGE_BOWLER: case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
							case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: 
							case CricketUtil.PENALTY: case CricketUtil.LOG_WICKET: case CricketUtil.LOG_ANY_BALL:
								
								switch (match.getEventFile().getEvents().get(i).getEventType())
							    {
							    case CricketUtil.CHANGE_BOWLER:
							    	ball_count = ball_count + 1;
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 0 \0");
									break;
							    case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
							    	ball_count = ball_count + 1;
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 2 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
											match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
									break;
							    case CricketUtil.FOUR: case CricketUtil.SIX: 
							    	ball_count = ball_count + 1;
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
											match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
									break;
							    case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY: 
							    	ball_count = ball_count + 1;
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 5 \0");
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
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
											(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + 
												this_ball_data.toUpperCase() + "\0");
									break;
							    case CricketUtil.LOG_WICKET: 
							    	if(match.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
								    	  break;
								    }else {
								    	ball_count = ball_count + 1;
								    	if (match.getEventFile().getEvents().get(i).getEventRuns() > 0) {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 15 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
													String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns()) + "+W" + "\0");
								    	} else {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 15 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + "W" + "\0");
								    	}
								    }
							      break;
							    case CricketUtil.LOG_ANY_BALL:
							    	ball_count = ball_count + 1;
							    	if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
							    		this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "P";
							    		if(match.getEventFile().getEvents().get(i).getEventRuns() > 0) {
							    			this_ball_data = this_ball_data + "+" + match.getEventFile().getEvents().get(i).getEventRuns();
							    		}
							    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 5 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
							    	}else {
							    		if(!match.getEventFile().getEvents().get(i).getEventExtra().isEmpty()) {
								    		if(match.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.WIDE)){
								    			if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns()+
									    					match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "WD";
								    			}else {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns()) + "WD";
								    			}
								    		}
								    		else if(match.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
							    				if(match.getEventFile().getEvents().get(i).getEventRuns()>0) {
							    					this_ball_data = "NB" + "+" + match.getEventFile().getEvents().get(i).getEventRuns() ;
							    				}else {
							    					this_ball_data = "NB" ;
							    				}
							    			}else {
							    				if(match.getEventFile().getEvents().get(i).getEventRuns()>0) {
							    					this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns());
							    				}
							    			}
								    	}
							    		
							    		if(!match.getEventFile().getEvents().get(i).getEventSubExtra().isEmpty() && match.getEventFile().getEvents().get(i).getEventSubExtraRuns()>0) {
								    		if(!match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
								    			if(this_ball_data.isEmpty()) {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventSubExtraRuns());
								    			}else {
								    				this_ball_data = this_ball_data + "+" + match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
								    			}
								    			if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
									    			this_ball_data = this_ball_data + "NB";
									    		}else if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.LEG_BYE)) {
									    			this_ball_data = this_ball_data + "LB";
									    		}else if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.BYE)) {
									    			this_ball_data = this_ball_data + "B";
									    		}//else if(match.getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
									    			//System.out.println("HELLO8");
									    			//this_ball_data = this_ball_data + "P";
									    		//}
								    		}
							    		}
							    		if (match.getEventFile().getEvents().get(i).getEventHowOut() != null && !match.getEventFile().getEvents().get(i).getEventHowOut().isEmpty()) {
								    		this_ball_data = this_ball_data + "+W";
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 15 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
								    	}else {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 5 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
								    	}
							    	}	
							    }
								break;
							}
								
						    if(ball_count >= 26) {
						    	break;
						    }
						  }
						}
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 1" + "\0");
				processAnimation(print_writer, "Section2$TimelineIn", "START", broadcaster);
			}
			break;
		}
			
		infobar.setLast_top_section(infobar.getTop_section());
		return infobar;
	}
	
	public Infobar populateInfobarCommentators(Infobar infobar, boolean is_this_updating,PrintWriter print_writer, int comm1, int comm2, int comm3, CricketService cricService, String broadcaster) {
		if(is_this_updating == false) {
			hideAndShowContainer(broadcaster, "INFOBAR", print_writer);
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 1" + "\0");
		}
		String comm1Name = "",comm2Name = "",comm3Name = "";
		for(Commentator comm : cricService.getCommentator()) {
			if(comm.getCommentatorId() > 0) {
				if(comm.getCommentatorId() == comm1) {
					comm1Name = comm.getCommentatorName();
				}else if(comm.getCommentatorId() == comm2) {
					comm2Name = comm.getCommentatorName();
				}else if(comm.getCommentatorId() == comm3) {
					comm3Name = comm.getCommentatorName();
				}
			}
		}
		
		if(comm1 > 0 && comm2 > 0 && comm3 > 0) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET "+"COMMENTATORS       "+comm1Name+",  "+comm2Name+",  "+comm3Name+ "\0");
		}else if(comm1 > 0 && comm2 > 0 && comm3 == 0) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET "+"COMMENTATORS       "+comm1Name+",  "+comm2Name+ "\0");
		}else if(comm1 > 0 && comm2 == 0 && comm3 == 0) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET "+"COMMENTATORS       "+comm1Name+ "\0");
		}
		infobar.setLast_top_section(infobar.getTop_section().toUpperCase());
		return infobar;
	}
	
	public Infobar populateInfobarLastXBalls(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, int lastXBalls, MatchAllData match, String broadcaster) {
		if(is_this_updating == false) {
			hideAndShowContainer(broadcaster, "INFOBAR", print_writer);
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 1" + "\0");
		}
		List<String> this_data_str = new ArrayList<String>();
		this_data_str.add(CricketFunctions.getlastthirtyballsdata(match, slashOrDash, match.getEventFile().getEvents(), lastXBalls));
		
		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " +"LAST "+lastXBalls+" BALLS    "+ this_data_str.get(this_data_str.size()-1).split(slashOrDash)[0]+ " RUN"+CricketFunctions.Plural(Integer.valueOf(this_data_str.get(this_data_str.size()-1).split(slashOrDash)[0])).toUpperCase()
				+"   "+this_data_str.get(this_data_str.size()-1).split(slashOrDash)[1]+" WICKET"+CricketFunctions.Plural(Integer.valueOf(this_data_str.get(this_data_str.size()-1).split(slashOrDash)[1])).toUpperCase()+ "\0");
		infobar.setLast_top_section(infobar.getTop_section().toUpperCase());
		return infobar;
	}
	
	
	public Infobar populateInfobarFreeTextInput (Infobar infobar, boolean is_this_updating, PrintWriter print_writer, String freeText, MatchAllData match, String broadcaster) {
		if(is_this_updating == false) {
			hideAndShowContainer(broadcaster, "INFOBAR", print_writer);
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 1" + "\0");
		}
		
		System.out.println("FREE TEZT : "+freeText);
		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " + freeText + "\0");
		
		infobar.setLast_top_section(infobar.getTop_section().toUpperCase());
		return infobar;
	}
	
	public Infobar populateInfobarFreeText(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, InfobarStats ibs, MatchAllData match, String broadcaster)
	{	
		if(is_this_updating == false) {
			hideAndShowContainer(broadcaster, "INFOBAR", print_writer);
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 1" + "\0");
		}

		if(ibs.getText1() != null && ibs.getText2() != null) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " + 
					ibs.getText1() + "-" + ibs.getText2() + "\0");
		}else if(ibs.getText1() != null) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " + ibs.getText1() + "\0");
		}else {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " + ibs.getText2() + "\0");
		}
		
		infobar.setLast_top_section(infobar.getTop_section().toUpperCase());
			
		return infobar;
	}
	public void populateInfobarDirector(PrintWriter print_writer,String Dir_value,String session_selected_broadcaster) {
		
		switch (Dir_value.toUpperCase()) {
		case "FOURS":
			processAnimation(print_writer, "FourIn", "START", session_selected_broadcaster);
			break;

		case "SIXES":
			processAnimation(print_writer, "SixIn", "START", session_selected_broadcaster);
			break;
		
		case "WICKETS":
			processAnimation(print_writer, "WicketIn", "START", session_selected_broadcaster);
			break;

		case "FREE-HIT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitIn START \0");
			break;
		}
	}
	
	public void populateMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, List<VariousText> vt, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET "+ " " + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			
			if(fix.get(match_number-1).getMatchnumber()<10) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ 
						"MATCH "+ fix.get(match_number-1).getMatchnumber() + " \0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ 
						fix.get(match_number - 1).getMatchfilename().toUpperCase() + " \0");
			}
			
			
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ "LIVE FROM "+ 
					fix.get(match_number-1).getVenue() + " \0");
			
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$HomeTeamName_Grp$FirstName*GEOM*TEXT SET " + TM.getTeamName1().toUpperCase() + " \0");
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$AwayLogoGrp$AwayTeamName_Grp$FirstName*GEOM*TEXT SET " + TM.getTeamName1().toUpperCase() + " \0");
				}
			}
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ "TOMORROW" + " \0");
				
				for(VariousText varText : vt) {
					if(varText.getVariousType().equalsIgnoreCase("MATCHPROMOFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ varText.getVariousText() + " \0");
					}
					else if(varText.getVariousType().equalsIgnoreCase("MATCHPROMOFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ "TOMORROW " + 
								"- LIVE FROM " + fix.get(match_number-1).getVenue() + " \0");
					}
				}
				
			}else {
				cal.add(Calendar.DATE, -1);
				if(fix.get(match_number-1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ "UP NEXT" + " \0");
					for(VariousText varText : vt) {
						if(varText.getVariousType().equalsIgnoreCase("MATCHPROMOFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ varText.getVariousText() + " \0");
						}
						else if(varText.getVariousType().equalsIgnoreCase("MATCHPROMOFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + "LIVE FROM " + 
									fix.get(match_number-1).getVenue() + " \0");
						}
					}
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ 
						fix.get(match_number-1).getDate() + " \0");
					
					for(VariousText varText : vt) {
						if(varText.getVariousType().equalsIgnoreCase("MATCHPROMOFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ varText.getVariousText() + " \0");
						}
						else if(varText.getVariousType().equalsIgnoreCase("MATCHPROMOFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + "LIVE FROM " + 
									fix.get(match_number-1).getVenue() + " \0");
						}
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 2.474 \0");
				
		}
	}
	public void populateMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, List<VariousText> vt, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET "+ " " + " \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + 
					match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$HomeTeamName_Grp$FirstName*GEOM*TEXT SET " + 
					match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + 
					match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$AwayLogoGrp$AwayTeamName_Grp$FirstName*GEOM*TEXT SET " + 
					match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ "" + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ 
					match.getSetup().getMatchIdent() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$SubHeader*GEOM*TEXT SET "+ 
					match.getSetup().getTournament().toUpperCase() + " \0");
			
			for(VariousText varText : vt) {
				if(varText.getVariousType().equalsIgnoreCase("MATCHIDFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + 
							varText.getVariousText() + " \0");
				}
				else if(varText.getVariousType().equalsIgnoreCase("MATCHIDFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + 
							"LIVE FROM "+ match.getSetup().getVenueName().toUpperCase() + " \0");
				}
			}
			
			
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 \0");
				
		}
		
	}
	public void populateLTMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, List<VariousText> vt, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + match.getSetup().getMatchIdent() + "\0");
			
			for(VariousText varText : vt) {
				if(varText.getVariousType().equalsIgnoreCase("MATCHIDFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + varText.getVariousText() + "\0");
				}
				else if(varText.getVariousType().equalsIgnoreCase("MATCHIDFOOTER") && varText.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
					if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || CricketFunctions.GetTargetData(match).getRemaningBall()  == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + CricketFunctions.GenerateMatchSummaryStatus(2, 
								match, CricketUtil.FULL, "|",broadcaster,false).getTargetOrResult().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + 
								match.getSetup().getVenueName().toUpperCase() + "\0");
					}
				}
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.120 \0");
				
		}
	}
	public void populateLtMatchPromo(PrintWriter print_writer,String viz_scene, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + TM.getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + TM.getTeamName3().toUpperCase() + "\0");
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + TM.getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + TM.getTeamName3().toUpperCase() + "\0");
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + fix.get(match_number - 1).getVenue().toUpperCase() + "\0");
				if(fix.get(match_number-1).getMatchnumber()<10) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "TOMORROW - " + 
							 "MATCH "+fix.get(match_number - 1).getMatchnumber() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "TOMORROW - " + 
							 fix.get(match_number - 1).getMatchfilename().toUpperCase() + "\0");
				}
				
			}else {
				cal.add(Calendar.DATE, -1);
				if(fix.get(match_number-1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + fix.get(match_number - 1).getVenue().toUpperCase() + "\0");
					if(fix.get(match_number-1).getMatchnumber()<10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "UP NEXT - " + 
								"MATCH "+fix.get(match_number - 1).getMatchnumber() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "UP NEXT - " + 
								fix.get(match_number - 1).getMatchfilename().toUpperCase() + "\0");
					}
					
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + fix.get(match_number - 1).getVenue().toUpperCase() + "\0");
					if(fix.get(match_number - 1).getMatchnumber()<10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + fix.get(match_number - 1).getDate() + " - " + 
								"MATCH "+fix.get(match_number - 1).getMatchnumber() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + fix.get(match_number - 1).getDate() + " - " + 
								fix.get(match_number - 1).getMatchfilename().toUpperCase() + "\0");
					}
					
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.120 \0");
				
		}
	}
	public void populatePlayingXISequence(PrintWriter print_writer,String viz_scene, int TeamId,MatchAllData match, String broadcaster, Configuration config) {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, count = 1;
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
					+ "tTeamFirstName" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
					+ "tTeamLastName" + " SET " + match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader"
					+ " SET " + match.getSetup().getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
					+ "tSubstitueHead" + " SET " + "SUBSTITUTES" + "\0");
			
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
						+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision()+ "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
						+match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}
			
			if(which_graphic_on_screen.equalsIgnoreCase("PLAYINGXI_SEQUENCE")) {
				if(TeamId == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

					for(Player hs : match.getSetup().getHomeSquad()) {
						row_id++;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + 
								"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$Dehighlight$" + 
								"$BottomData$StrikeRate*GEOM*TEXT SET " + " " + " \0");
						
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
//							if(!new File(photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png").exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + hs.getPhoto() + ".png" + "\0");
						}else {
							
//							if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\APL\\Photos\\" + 
//									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + hs.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\" + 
								config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + 
									hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						if (hs.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (hs.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (hs.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (hs.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgRole" + row_id + " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (hs.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println(
											"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole"
													+ row_id + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole"
													+ row_id + " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						

						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp"+row_id+"$Dehighlight$LastName*GEOM*TEXT SET " 
									+ hs.getTicker_name() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$RoleIconGrp*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "1" + " \0");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp"+row_id+"$Dehighlight$LastName*GEOM*TEXT SET " 
									+ hs.getTicker_name()+" (WK)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$RoleIconGrp*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "0" + " \0");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp"+row_id+"$Dehighlight$LastName*GEOM*TEXT SET " 
									+ hs.getTicker_name()+" (C & WK)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$RoleIconGrp*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "0" + " \0");
							
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp"+row_id+"$Dehighlight$LastName*GEOM*TEXT SET " 
									+ hs.getTicker_name() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$RoleIconGrp*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "0" + " \0");
						}
						
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$Substutes*FUNCTION*Grid*num_row SET " + (match.getSetup().getHomeSubstitutes().size() + 1) + " \0");
					for(Player hs : match.getSetup().getHomeSubstitutes()) {
						count++;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$Substutes$Row"+count+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						if (hs.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (hs.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (hs.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (hs.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (hs.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole"
													+ (count-1) + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole"
													+ (count-1) + " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						
						if(hs.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteFirstName"+(count-1) + " SET " + hs.getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteLastName"+(count-1) + " SET " + hs.getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteFirstName"+(count-1) + " SET " +"" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteLastName"+(count-1) + " SET " + hs.getFirstname() + "\0");
						}
					}
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

					for(Player as : match.getSetup().getAwaySquad()) {
						row_id++;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + 
								"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$Dehighlight$" + 
								"$BottomData$StrikeRate*GEOM*TEXT SET " + " " + " \0");
						
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
//							if(!new File(photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png").exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
									match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + as.getPhoto() + ".png" + "\0");
						}else {
							
//							if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\APL\\Photos\\" + 
//									match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + as.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\" + 
								config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + 
									as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						
						if (as.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (as.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (as.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (as.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgRole" + row_id + " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (as.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println(
											"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole"
													+ row_id + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole"
													+ row_id + " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}

						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp"+row_id+"$Dehighlight$LastName*GEOM*TEXT SET " 
									+ as.getTicker_name() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$RoleIconGrp*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "1" + " \0");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp"+row_id+"$Dehighlight$LastName*GEOM*TEXT SET " 
									+ as.getTicker_name()+" (WK)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$RoleIconGrp*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "0" + " \0");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp"+row_id+"$Dehighlight$LastName*GEOM*TEXT SET " 
									+as.getTicker_name()+" (C & WK)" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$RoleIconGrp*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "0" + " \0");
							
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp"+row_id+"$Dehighlight$LastName*GEOM*TEXT SET " 
									+ as.getTicker_name() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$RoleIconGrp*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$ImagesAll$ImageGrp" + row_id + "$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "0" + " \0");
						}
						
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$Substutes*FUNCTION*Grid*num_row SET " + (match.getSetup().getAwaySubstitutes().size() + 1) + " \0");
					for(Player as : match.getSetup().getAwaySubstitutes()) {
						count++;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$Substutes$Row"+count+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						if(as.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteFirstName"+(count-1) + " SET " + as.getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteLastName"+(count-1) + " SET " + as.getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteFirstName"+(count-1) + " SET " +"" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteLastName"+(count-1) + " SET " + as.getFirstname() + "\0");
						}
						if (as.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (as.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (as.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (as.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (as.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole"
													+ (count-1) + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole"
													+ (count-1) + " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png DataOut 0.360 In 2.739 Images_In 1.400 \0");
			}else {
				if(TeamId == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

					for(Player hs : match.getSetup().getHomeSquad()) {
						row_id = row_id + 1;

						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
//							if(!new File(photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png").exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + hs.getPhoto() + ".png" + "\0");
						}else {
							
//							if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\APL\\Photos\\" + 
//									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + hs.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\" + 
								config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + 
									hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						if (hs.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (hs.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (hs.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (hs.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgRole" + row_id + " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (hs.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println(
											"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole"
													+ row_id + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole"
													+ row_id + " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}

						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + hs.getSurname() + "\0");
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + hs.getFirstname() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$CaptainIcon*ACTIVE SET "+ "1" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + hs.getSurname()+" (WK)" + "\0");
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + hs.getFirstname() +" (WK)"+ "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$CaptainIcon*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + hs.getSurname()+" (C & WK)" + "\0");
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + hs.getFirstname() +" (C & WK)"+ "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$CaptainIcon*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						}
						else {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + hs.getSurname() + "\0");
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + hs.getFirstname() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$CaptainIcon*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						}
						
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$Substutes*FUNCTION*Grid*num_row SET " + (match.getSetup().getHomeSubstitutes().size() + 1) + " \0");
					for(Player hs : match.getSetup().getHomeSubstitutes()) {
						count++;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$Substutes$Row"+count+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						if (hs.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (hs.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (hs.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (hs.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (hs.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println(
											"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole"
													+ (count-1) + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole"
													+ (count-1) + " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						
						if(hs.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteFirstName"+(count-1) + " SET " + hs.getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteLastName"+(count-1) + " SET " + hs.getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteFirstName"+(count-1) + " SET " +"" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteLastName"+(count-1) + " SET " + hs.getFirstname() + "\0");
						}
					}
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

					for(Player as : match.getSetup().getAwaySquad()) {
						row_id = row_id + 1;

						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
//							if(!new File(photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png").exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
									match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + as.getPhoto() + ".png" + "\0");
						}else {
							
//							if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\APL\\Photos\\" + 
//									match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\" + as.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\" + 
								config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + 
									as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						if (as.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (as.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (as.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (as.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgRole" + row_id + " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgRole" + row_id + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (as.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println(
											"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole"
													+ row_id + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole"
													+ row_id + " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}

						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + as.getSurname() + "\0");
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + as.getFirstname() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$CaptainIcon*ACTIVE SET "+ "1" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + as.getSurname()+" (WK)" + "\0");
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + as.getFirstname() +" (WK)"+ "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$CaptainIcon*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + as.getSurname()+" (C & WK)" + "\0");
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + as.getFirstname() +" (C & WK)"+ "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$CaptainIcon*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						}
						else {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + as.getSurname() + "\0");
								
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName"+row_id + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" +row_id+ " SET " + as.getFirstname() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$CaptainIcon*ACTIVE SET "+ "0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$TeamAll1$Row"+row_id+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						}
						
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$Substutes*FUNCTION*Grid*num_row SET " + (match.getSetup().getAwaySubstitutes().size() + 1) + " \0");
					for(Player as : match.getSetup().getAwaySubstitutes()) {
						count++;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$Substutes$Row"+count+"$InternationalIcon*ACTIVE SET "+ "0" + " \0");
						if (as.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (as.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (as.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (as.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer
											.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
													+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
										+ "lgSubstituteRole" + (count-1) + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (as.getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println(
											"-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole"
													+ (count-1) + " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSubstituteRole"
													+ (count-1) + " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						if(as.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteFirstName"+(count-1) + " SET " + as.getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteLastName"+(count-1) + " SET " + as.getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteFirstName"+(count-1) + " SET " +"" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "tSubstituteLastName"+(count-1) + " SET " + as.getFirstname() + "\0");
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.739 DataIn 1.600 \0");
			}
		}
	}
	
	public void populatePlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,MatchAllData match, String broadcaster, Configuration config) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0,omo=0,sub_row_id=1;
			String cont = "";
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
					+ " " + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll*ACTIVE SET 0 \0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
					+ match.getSetup().getMatchIdent() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$SubHeader*GEOM*TEXT SET " 
					+ match.getSetup().getTournament().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstitueHead" + " SET " + "SUBSTITUTES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$SubstituteAll*ACTIVE SET 1 \0");
			
			if(TeamId == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

				for(Player hs : match.getSetup().getHomeSquad()) {
					row_id = row_id + 1;
					omo = 0;
					cont = "Dehighlight";
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
							"$BottomData$StrikeRate*GEOM*TEXT SET " + " " + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
							+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + 
							"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET "+ omo + " \0");

					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
//						if(!new File(photo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png").exists()) {
//							this.status = CricketUtil.UNSUCCESSFUL;
//						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + hs.getPhoto() + ".png" + "\0");
					}else {
						
//						if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\APL\\Photos\\" + 
//								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\" + hs.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//							this.status = CricketUtil.UNSUCCESSFUL;
//						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + 
								hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$ImageGrp$PlayerImage*TEXTURE*IMAGE SET "+ photo_path 
										//+ match.getHomeTeam().getTeamName4().toUpperCase() + "\\" + hs.getFirstname() + CricketUtil.PNG_EXTENSION + " \0");

					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ hs.getTicker_name() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 1 + " \0");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ hs.getTicker_name() + " (WK)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ hs.getTicker_name() + " (C & WK)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ hs.getTicker_name() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes*FUNCTION*Grid*num_row SET " + 
						(match.getSetup().getHomeSubstitutes().size() + 1) + " \0");
				for(Player hsub : match.getSetup().getHomeSubstitutes()) {
					sub_row_id = sub_row_id + 1;
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
							"$TextAll$RoleIcon*ACTIVE SET " + "0" + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
							"$TextAll$InternationalIcon*ACTIVE SET " + 0 + " \0");
					if(hsub.getSurname() != null) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
								"$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hsub.getFirstname() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + hsub.getSurname() + " \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
								"$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + hsub.getFirstname() + " \0");
					}
					
				}
			}
			
			else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

				for(Player as : match.getSetup().getAwaySquad()) {
					row_id = row_id + 1;
					omo = 0;
					cont = "Dehighlight";
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
							"$BottomData$StrikeRate*GEOM*TEXT SET " + " " + " \0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
							+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET "+ omo + " \0");
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
					}else {
//						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\"+match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//							this.status = CricketUtil.UNSUCCESSFUL;
//						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + 
								as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$ImageGrp$PlayerImage*TEXTURE*IMAGE SET "+ 
							//photo_path + match.getHomeTeam().getTeamName4().toUpperCase() + "\\" + as.getFirstname() + CricketUtil.PNG_EXTENSION + " \0");

					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ as.getTicker_name() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 1 + " \0");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ as.getTicker_name() + " (WK)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ as.getTicker_name() + " (C & WK)" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " 
								+ as.getTicker_name() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$RoleIconGrp*ACTIVE SET " 
								+ 0 + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$TextAll$Icons$CaptainIcon*ACTIVE SET " 
								+ 0 + " \0");
					}
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes*FUNCTION*Grid*num_row SET " + 
						(match.getSetup().getAwaySubstitutes().size() + 1) + " \0");
				
				for(Player asub : match.getSetup().getAwaySubstitutes()) {
					sub_row_id = sub_row_id + 1;
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
							"$TextAll$RoleIcon*ACTIVE SET " + 0 + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
							"$TextAll$InternationalIcon*ACTIVE SET " + 0 + " \0");
					if(asub.getSurname() != null) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
								"$TextAll$NameAll$FirstName*GEOM*TEXT SET " + asub.getFirstname() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + asub.getSurname() + " \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
								"$TextAll$NameAll$FirstName*GEOM*TEXT SET " + "" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SubstituteAll$Substutes$Row" + sub_row_id + "$SubstutesAnimation$DataText$" + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + asub.getFirstname() + " \0");
					}
				}
				
			}
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BottomInfoGrp$BottomInfo$Equations*GEOM*TEXT SET " +
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BottomInfoGrp$BottomInfo$Equations*GEOM*TEXT SET " +
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$Images_In 1.800 \0");
		
		
	}
	
	public void populateBugTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster)
	{
		switch (broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + " " + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + CricketFunctions.GetTargetData(match).getRemaningBall() + "\0");						
					}
				}
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
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
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
		}
	}
	public void populateLtBatsmanThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{

		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
				for(BattingCard bc : inn.getBattingCard()) {
					if (inn.getInningNumber() == whichInning) {
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
						if(PlayerId == bc.getPlayerId()) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								

							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + " " + "\0");								
							/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
							} else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
			
		}
		
	}
	public void populateLtBowlerThisMatch(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					for (BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getPlayerId()==PlayerId) {
							/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
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
			
		}
	}
	public void populateLtBowlerDetails(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerStats's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
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
				
		}
	}
	public void populateLandMark(PrintWriter print_writer,String viz_scene, int whichInning, String statType, int playerId, MatchAllData match, String session_selected_broadcaster, Configuration config)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			//String Home_or_Away="";

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					switch(statType.toUpperCase()) {
					case "BATSMAN":
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$Mile$Runs$IconOmo*FUNCTION*Omo*vis_con SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Mile$Ball$IconOmo*FUNCTION*Omo*vis_con SET 1 \0");
						
						for(BattingCard bc : inn.getBattingCard()) {
							if(playerId == bc.getPlayerId()) {
								
//								print_writer.println("-1 RENDERER*TREE*$Main$All$NameBands$NameAll$FistName*GEOM*TEXT SET "+ bc.getPlayer().getFirstname() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$Data$Mile$NameBands$NameAll$FistName*GEOM*TEXT SET "+ bc.getPlayer().getFirstname() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$Data$Mile$NameBands$NameAll$LastName*GEOM*TEXT SET "+ bc.getPlayer().getSurname() + " \0");
								if(bc.getStatus().equals(CricketUtil.OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Mile$Runs$Runs*GEOM*TEXT SET "+ bc.getRuns() + " \0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Mile$Runs$Runs*GEOM*TEXT SET "+ bc.getRuns() + "*" + " \0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$Mile$Ball$Balls*GEOM*TEXT SET "+ bc.getBalls() + " \0");
//								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatHead*GEOM*TEXT SET "+ "S/R " + " \0");
//								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ bc.getStrikeRate() + " \0");
								if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Base$Left$LeftLogo$LeftLogo*TEXTURE*IMAGE SET " + logo_path + 
													match.getSetup().getHomeTeam().getTeamName4() + ".png" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$Base$RightGrp$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET " + logo_path + 
											match.getSetup().getHomeTeam().getTeamName4() + ".png" + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Base$Left$LeftLogo$LeftLogo*TEXTURE*IMAGE SET " + logo_path + 
											match.getSetup().getAwayTeam().getTeamName4() + ".png" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$Base$RightGrp$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET " + logo_path + 
											match.getSetup().getAwayTeam().getTeamName4() + ".png" + "\0");
								}
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$PlayerImageGrp$ImageShadow*TEXTURE*IMAGE SET " + 
											photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$PlayerImageGrp$Image*TEXTURE*IMAGE SET " + 
											photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
								}else {
//									if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\"  +bc.getPlayer().getFirstname()+ CricketUtil.PNG_EXTENSION).exists()) {
//										this.status = CricketUtil.UNSUCCESSFUL;
//									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "1"+ " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path +inn.getBatting_team().getTeamName4()+"\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path +inn.getBatting_team().getTeamName4()+"\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									
//									print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$PlayerImageGrp$ImageShadow*TEXTURE*IMAGE SET " + 
//											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path +inn.getBatting_team().getTeamName4()+"\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//									print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$PlayerImageGrp$Image*TEXTURE*IMAGE SET " + 
//											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path  +inn.getBatting_team().getTeamName4()+"\\\\"+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
							}
						}
						
						break;
					case "BOWLER":
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$Mile$Runs$IconOmo*FUNCTION*Omo*vis_con SET 2 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$Mile$Ball$IconOmo*FUNCTION*Omo*vis_con SET 1 \0");
						
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(playerId == boc.getPlayerId()) {
								
//								print_writer.println("-1 RENDERER*TREE*$Main$All$NameGrp$MaxSize$FirstName*GEOM*TEXT SET "+ boc.getPlayer().getFirstname() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$Data$Mile$NameBands$NameAll$FistName*GEOM*TEXT SET "+ boc.getPlayer().getFirstname() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$Data$Mile$NameBands$NameAll$LastName*GEOM*TEXT SET "+ boc.getPlayer().getSurname() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Mile$Runs$Runs*GEOM*TEXT SET "+ boc.getWickets()+"-"+boc.getRuns() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$Mile$Ball$Balls*GEOM*TEXT SET "+ CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " \0");
//								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatHead*GEOM*TEXT SET "+ "ECO " + " \0");
//								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$WithoutBar$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ boc.getEconomyRate() + " \0");
								
								if(inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Base$Left$LeftLogo$LeftLogo*TEXTURE*IMAGE SET " + logo_path + 
											match.getSetup().getHomeTeam().getTeamName4() + ".png" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$Base$RightGrp$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET " + logo_path + 
											match.getSetup().getHomeTeam().getTeamName4() + ".png" + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$Base$Left$LeftLogo$LeftLogo*TEXTURE*IMAGE SET " + logo_path + 
											match.getSetup().getAwayTeam().getTeamName4() + ".png" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$Base$RightGrp$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET " + logo_path + 
											match.getSetup().getAwayTeam().getTeamName4() + ".png" + "\0");
								}
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$PlayerImageGrp$ImageShadow*TEXTURE*IMAGE SET " + 
											photo_path + boc.getPlayer().getFirstname() + ".png" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$PlayerImageGrp$Image*TEXTURE*IMAGE SET " + 
											photo_path + boc.getPlayer().getFirstname() + ".png" + "\0");
								}else {
//									if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\" +boc.getPlayer().getFirstname()+ CricketUtil.PNG_EXTENSION).exists()) {
//										this.status = CricketUtil.UNSUCCESSFUL;
//									}
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "1"+ " SET " +"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path +inn.getBowling_team().getTeamName4()+"\\\\"+ boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path +inn.getBowling_team().getTeamName4()+"\\\\"+ boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									
//									print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$PlayerImageGrp$ImageShadow*TEXTURE*IMAGE SET " + 
//											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path +inn.getBowling_team().getTeamName4()+"\\\\"+ boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//									print_writer.println("-1 RENDERER*TREE*$Main$All$PlayerProfile$PlayerImageGrp$Image*TEXTURE*IMAGE SET " + 
//											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path+inn.getBowling_team().getTeamName4() +"\\\\"+ boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
							
							}
						}
						break;
					}
				}
			}
				
		}
	}
	public void populateFFLandMark(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, MatchAllData match, String session_selected_broadcaster, Configuration config)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			//String Home_or_Away="";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					for(BattingCard bc : inn.getBattingCard()) {
						if(playerId == bc.getPlayerId()) {
							
							print_writer.println("-1 RENDERER*TREE*$Main$All$MaxSize$PlayerName*GEOM*TEXT SET "+ bc.getPlayer().getFirstname() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$text$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ bc.getBatterPosition() + " \0");
							
							if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
												match.getSetup().getHomeTeam().getTeamName1() + ".png" + "\0");
								
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
												match.getSetup().getAwayTeam().getTeamName1() + ".png" + "\0");
							}
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Plaer_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
										photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +bc.getPlayer().getFirstname()+ CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main$All$All_Player_Pic$noname$Plaer_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
										"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
							}
						}
					}
				}
			}
				
		}
	}
	public void populateLtPointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> point_table,List<Team> teams, MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		this.status = CricketUtil.SUCCESSFUL;
		int row_id=0, omo_num = 0;
		DecimalFormat df = new DecimalFormat("0.000");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointHeader1" + " SET " + "" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointHeader2" + " SET " + "STANDINGS" + "\0");
		for(int i = 0; i <= point_table.size()-1; i++) {
			row_id = row_id + 1;
			if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) 
					|| match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
				omo_num = 1;
			}else {
				omo_num = 0;
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vPointsOmo"+row_id + " SET " + omo_num + "\0");
			
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tQualified" + row_id + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tQualified" + row_id + " SET " + "Q" + "\0");
			}
			for(Team tm : teams) {
				if(tm.getTeamName1().equalsIgnoreCase(point_table.get(i).getTeamName()) ||tm.getTeamName4().equalsIgnoreCase(point_table.get(i).getTeamName())) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTeam" + row_id + " SET " + tm.getTeamName3() + "\0");
				}
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayedValue" + row_id + " SET " + point_table.get(i).getPlayed() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tWinValue" + row_id + " SET " + point_table.get(i).getWon() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsValue" + row_id + " SET " +point_table.get(i).getPoints()+ "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNetRunRateValue" + row_id + " SET " + df.format(point_table.get(i).getNetRunRate()) + "\0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 1.260 In$DataIn 1.260 \0");	
	}
	
	public void populatePhaseWise(PrintWriter print_writer,String viz_scene, int inning_number, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: ProjectedScore's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int oneToSixRuns = 0, sevenToFifteenRuns = 0, sixteenToTweentyRuns = 0,oneToSixfWkt = 0, sevenToFifteenWkt = 0, sixteenToTweentyWkt = 0;
			List<OverByOverData> overByOverData = CricketFunctions.getOverByOverData(match, inning_number, "MANHATTAN", match.getEventFile().getEvents());
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " +"PHASE-WISE SCORES" + "\0");
			for(int i=0; i<2; i++) {
				if(match.getMatch().getInning().get(i).getInningNumber() == inning_number) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getMatch().getInning().get(i).getBatting_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getMatch().getInning().get(i).getBatting_team().getTeamName3().toUpperCase() + " \0");
					
					for(int j=1; j<=overByOverData.size()-1; j++) {
						if(j>0 && j<=6) {
							oneToSixRuns+= overByOverData.get(j).getOverTotalRuns();
							oneToSixfWkt+=overByOverData.get(j).getOverTotalWickets();
						}
						if(j>6 && j<=15) {
							sevenToFifteenRuns+= overByOverData.get(j).getOverTotalRuns();
							sevenToFifteenWkt+=overByOverData.get(j).getOverTotalWickets();
						}
						if(j>15 && j<=20) {
							sixteenToTweentyRuns+= overByOverData.get(j).getOverTotalRuns();
							sixteenToTweentyWkt+=overByOverData.get(j).getOverTotalWickets();
						}
					}
				}
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "1-6" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "7-15" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "16-20" + "\0");
			
			if(oneToSixfWkt == 0 && oneToSixRuns == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " +"-"+"\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + oneToSixRuns+"-"+oneToSixfWkt + "\0");
			}
			if(sevenToFifteenWkt == 0 && sevenToFifteenRuns == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " +"-"+ "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + sevenToFifteenRuns+"-"+sevenToFifteenWkt + "\0");
			}
			if(sixteenToTweentyWkt == 0 && sixteenToTweentyRuns == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " +"-"+ "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + sixteenToTweentyRuns +"-"+sixteenToTweentyWkt + "\0");
			}
			
			
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");
			
		}
		
	}
	
	public void populateProjectedScore(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: ProjectedScore's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");
			
			String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
		    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
		    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
	        }
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ inn.getBatting_team().getTeamName3().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " +"PROJECTED SCORES" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "@"+ proj_score_rate[0] +" (CRR)" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + proj_score_rate[1] + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "@" + proj_score_rate[2] +" RPO" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + proj_score_rate[3] + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "@" + proj_score_rate[4] +" RPO" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + proj_score_rate[5] + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");
			
		}
		
	}
	public void populateTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Target's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
				

				//if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					
					//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
							 // " TO WIN FROM " +  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + "\0");
					
					if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(match.getSetup().getMaxOvers()*6 >= 100) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
									 " TO WIN FROM "+  match.getSetup().getMaxOvers() + " OVERS" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
									 " TO WIN FROM "+  match.getSetup().getMaxOvers()*6 + " BALLS" + "\0");
						}
						
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) != 0) {
							if(match.getSetup().getTargetOvers().contains(".")) {
								if((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + 
											CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM " + (Integer.valueOf(match.getSetup().getTargetOvers()
													.split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) + " BALLS" + "\0");
								}
							}else {
								if(Double.valueOf(match.getSetup().getTargetOvers())*6 >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS" + "\0");
								}
							}
						}
						if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
							if(match.getSetup().getTargetOvers().contains(".")) {
								if((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (VJD)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  (Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) + " BALLS (VJD)" + "\0");
								}
							}else {
								if(Double.valueOf(match.getSetup().getTargetOvers())*6 >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (VJD)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS (VJD)" + "\0");
								}
							}
						}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
							if(match.getSetup().getTargetOvers().contains(".")) {
								if((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (DLS)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  (Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) + " BALLS (DLS)" + "\0");
								}
							}else {
								if(Double.valueOf(match.getSetup().getTargetOvers())*6 >= 100) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (DLS)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS (DLS)" + "\0");
								}
							}
						}
					}	
					
				//}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
		
			
	}
	public void populateTeamSummary(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + " " + "\0");								

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, whichInning, 0, ",", match.getEventFile().getEvents()).split(",");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");								
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$noname$FOW*ACTIVE SET " + "0" + "\0");								

						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + 
								CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1A SET " + "0s" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1B SET " + Count[0] + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2B SET " + Count[1] + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3B SET " + Count[2] + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4B SET " + Count[3] + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue5B SET " + inn.getTotalFours() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue6B SET " + inn.getTotalSixes() + "\0");

				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.380 \0");
			
		}
	}
	public void populateLtBattingSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + " " + "\0");								

			for(Inning inn : match.getMatch().getInning()) {
				for(BattingCard bc : inn.getBattingCard()) {
					if (inn.getInningNumber() == whichInning) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

							if(PlayerId == bc.getPlayerId()) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$noname$FOW*GEOM*TEXT SET " + "BATTING SUMMARY" + "\0");								

								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "*" + "\0");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + bc.getBalls() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1A SET " + "0s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1B SET " + Count[0] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2B SET " + Count[1] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3B SET " + Count[2] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4B SET " + Count[3] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue5B SET " + Count[4] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue6B SET " + Count[6] + "\0");
							}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.380 \0");
			
		}
	}
	public void populateLtBowlerSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + " " + "\0");								
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "BOWLING SUMMARY" + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BOWLER,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBowling_team().getTeamName4().toUpperCase() + "\0");

							if(PlayerId == boc.getPlayerId()) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");								
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1A SET " + "0s" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1B SET " + Count[0] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2B SET " + Count[1] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3B SET " + Count[2] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4B SET " + Count[3] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue5B SET " + Count[4] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue6B SET " + Count[6] + "\0");
							}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.380 \0");
			
		}
	}
	public void populateFallofWicket(PrintWriter print_writer,String viz_scene,int whichInning, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");
	
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + CricketFunctions.getTeamScore(inn, slashOrDash, false).trim() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()).trim() + "\0");
					
//					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + 
//							CricketFunctions.getTeamScore(inn, slashOrDash, false).trim() + " \0");
//
//					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET "+ CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()).trim() + " \0");
	
					if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$FOW$BottomLine$noname*ACTIVE SET 0" + " \0");
					}
					else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
						for(FallOfWicket fow : inn.getFallsOfWickets()) {								
							if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$FOW$BottomLine$noname*ACTIVE SET 1" + " \0");
								for(int fow_id=1;fow_id<=10;fow_id++) {
									if(fow_id <= inn.getFallsOfWickets().size()) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow.getFowNumber() + "A" + " SET " + fow.getFowNumber() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow.getFowNumber() + "B" + " SET " + fow.getFowRuns() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow_id + "A" + " SET " + " " + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow_id + "B" + " SET " + " " + "\0");
									}
								}	
							}		
						}
					}
					
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.600 \0");
			
		}
	
	}
	public void populateSplit(PrintWriter print_writer,String viz_scene,int whichInning,int splitValue, MatchAllData match, String broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					if(inn.getTotalWickets() >=10) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET "+ inn.getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET "+ inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");

					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 30 || inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 50) {
						if(splitValue == 30) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER THIRTY" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER FIFTY" + "\0");
						}

						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$HeadValue1$Dehiglight$StatHead1*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + " \0");
					
					} else {
						if(splitValue == 30) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER THIRTY" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER FIFTY" + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$HeadValue1$Dehiglight$StatHead1*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + "\0");
						
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$noname*ACTIVE SET 0" + "\0");
					
				    for (int i = 0; i < CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size(); i++) {
				    	
				    	int row_id = i + 1;
				    	for(int split_id=1;split_id<=6;split_id++) {
					    	if(split_id <= CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size()) {
					    		print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$noname*ACTIVE SET 1" + "\0");
					    		if(row_id==1) {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"st"+ "\0");
					    		}else if(row_id==2){
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"nd"+ "\0");
					    		}else if(row_id==3) {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"rd"+ "\0");
					    		}else {
					    			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"th"+ "\0");
					    		}
							
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "B" + " SET " + 
										CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).get(i) + "\0");
					    	}
					    	else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + split_id + "A" + " SET " + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + split_id + "B" + " SET " + " " + "\0");
					    	}
				    	}
			        }
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.600 \0");
				
		}
		
	}	
	public void populateComparision(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Target's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamRefName" + " SET " + logo_path + inn.getBowling_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + inn.getBowling_team().getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + inn.getBowling_team().getTeamName3().toUpperCase()  + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + inn.getBatting_team().getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
					
					if(inn.getTotalOvers() == 1 && inn.getTotalBalls() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "AFTER " + 
								CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVER" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "AFTER " + 
								CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamScore" + " SET " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamScore" + " SET " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + "\0");

				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.120 \0");
			
		}	
	}
	public void populateLTPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String broadcaster, Configuration config) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			boolean impactInThisInning = false;
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					
					String Left_Batsman ="",Right_Batsman="";
					
					Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getFull_name();
					Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getFull_name();
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
							inn.getBatting_team().getTeamName4() + "" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() 
							+ CricketUtil.PNG_EXTENSION + "\0");
					}else {
//						if(!new File("\\\\" + config.getPrimaryIpAddress() +"\\c\\Images\\APL\\Photos\\" + inn.getBatting_team().getTeamName4() + 
//							"\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//							this.status = CricketUtil.UNSUCCESSFUL;
//						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
								inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + photo_path + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() 
							+ CricketUtil.PNG_EXTENSION + "\0");
					}else {
//						if(!new File("\\\\" + config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\"+ inn.getBatting_team().getTeamName4() + 
//							"\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//							this.status = CricketUtil.UNSUCCESSFUL;
//						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
								inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
						impactInThisInning = true;
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vImpact1" + " SET " + "1" + "\0");
					}else {
						if(impactInThisInning == false) {
							impactInThisInning = false;
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vImpact1" + " SET " + "0" + "\0");
					}
					
					if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
						impactInThisInning = true;
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vImpact2" + " SET " + "1" + "\0");
					}else {
						if(impactInThisInning == false) {
							impactInThisInning = false;
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vImpact2" + " SET " + "0" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
							+ inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$PartnershipData$Runs$Alignment$PartnershipScore*GEOM*TEXT SET " 
											+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$PartnershipData$Balls$Alignment$PartnershipBalls*GEOM*TEXT SET " 
											+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row1$RowAnimation$RowData$PlayerName1*GEOM*TEXT SET " + Left_Batsman + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row1$RowAnimation$RowData$PlayerName2*GEOM*TEXT SET " + Right_Batsman + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore1$PlayerContributionRuns1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore1$PlayerContributionBalls1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore2$PartnershipRun*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore2$PartnershipBalls*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row3$RowAnimation$Highlight$Alignment$Fours$PlayerBalls1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalFours() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row3$RowAnimation$Highlight$Alignment$Sixes$PlayerBalls1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalSixes() + "\0");

					if(inn.getTotalWickets() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
												+ (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
												+ (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 2) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
												+ (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
												+ (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
					}
				}
			}
			if(impactInThisInning) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$ImpactLegendAll$Star*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$ImpactLegendAll$noname*ACTIVE SET 1 \0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$ImpactLegendAll$Star*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$ImpactLegendAll$noname*ACTIVE SET 0 \0");
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$PartDataIn 1.800 In$PartDataIn$DataIn 1.800 \0");
			
		}
	}
	
	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene,String StatType,int playerid,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster, Configuration config) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_no=0;

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$Data$CapAll*FUNCTION*Omo*vis_con SET " + "0" + "\0");
			for(int i=1; i<=5; i++) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatches"+i + " SET " + "" + "\0");
			}
			
			switch(StatType.toUpperCase()) {
			case "MOST_RUNS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST RUNS " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + 
						match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {
						
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
//								if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\APL\\Photos\\"+ 
//										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "" + 
//										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//									this.status = CricketUtil.UNSUCCESSFUL;
//								}
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
										+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			case "MOST_WICKETS":
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST WICKETS " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
//								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\"+team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "" + 
//										tournament.get(i).getPlayer().getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//									this.status = CricketUtil.UNSUCCESSFUL;
//								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
										+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getWickets() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			case "MOST_FOURS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST FOURS " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
//								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\" +team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\" + 
//										tournament.get(i).getPlayer().getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//									this.status = CricketUtil.UNSUCCESSFUL;
//								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
										+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getFours() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			case "MOST_SIXES":
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST SIXES " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
//								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\APL\\Photos\\" +team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "" + 
//										tournament.get(i).getPlayer().getPhoto()+ CricketUtil.PNG_EXTENSION).exists()) {
//									this.status = CricketUtil.UNSUCCESSFUL;
//								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
										+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getSixes() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.180 \0");
			
		}
	}
	
	public void populateLtEquation(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + " " + "\0");
			
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					
					if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
							if(match.getMatch().getMatchStatus() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo" + "\0");
									}
								} else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase()  +"\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo" + "\0");
									}
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase()+"\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo" + "\0");
									}
								}
							}
							
						}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
								|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
							
							if(match.getMatch().getMatchStatus() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo" + "\0");
									}
								} else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " +
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo" + "\0");
									}
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " +
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo" + "\0");
									}
								}
								
							}
						}
						
						else{
							if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET "  
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
							}
							
						}
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									} else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									}
								}
								
							}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
								
								if(match.getMatch().getMatchStatus() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									} else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									}
								}
							}
							
							else{
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");									
							}
						}
						else {
							if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) <= 0) {
								if(match.getMatch().getMatchStatus() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									} else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									}
								}
							}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| Double.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), match.getMatch().getInning().get(1).getTotalBalls())) 
									>= Double.valueOf(match.getSetup().getTargetOvers())) {
								if(match.getMatch().getMatchStatus() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									} else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo" + "\0");
										}
									}
								}
							}
							else{
								if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
												+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
												" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (VJD)" + "\0");
								}
								else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET "  
												+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
												" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (DLS)" + "\0");
								}
								else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
												+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
												" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
								}
							}
						}
					}
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
		
	}	
	public void populatePointsTable(PrintWriter print_writer,String viz_scene,List<LeagueTeam> point_table, String broadcaster,MatchAllData match,List<VariousText> variousText) throws InterruptedException 
	{
		this.status = CricketUtil.SUCCESSFUL;
		int row_id=0,omo_num = 0;
		String cont_name = "";
		DecimalFormat df = new DecimalFormat("0.000");
		
		print_writer.println("-1 RENDERER*TREE*$Main$All$partnershipall*ACTIVE SET " + "0" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PartLeftLogoGrp$PartLeftLogo*ACTIVE SET 1 \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$PartLeftLogoGrp$PartLeftLogoGrp$PartLeftLogo*ACTIVE SET 1 \0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead1" + " SET " + " " + "\0");
		
		//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead1" + " SET " + "GROUP - " + point_table.get(0).getPool().trim() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead2" + " SET " + "POINTS TABLE" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
		
		for(VariousText vartxt : variousText) {
			if(vartxt.getVariousType().equalsIgnoreCase("POINTSTABLEFOOTER") && vartxt.getUseThis().equalsIgnoreCase("YES")) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$BottomInfoGrp$RowAnimation$PointsInfo*GEOM*TEXT SET "+ 
						vartxt.getVariousText() + " \0");
			}else if(vartxt.getVariousType().equalsIgnoreCase("POINTSTABLEFOOTER") && vartxt.getUseThis().equalsIgnoreCase("NO")) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$BottomInfoGrp$RowAnimation$PointsInfo*GEOM*TEXT SET "+ 
						"TOP FOUR TEAMS QUALIFIES FOR THE SEMI-FINALS" + " \0");
			}
		}

		for(int i = 0; i <= point_table.size() - 1 ; i++) {
			row_id = row_id + 1;
			
			if(match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) 
					|| match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
				omo_num = 1;
				cont_name = "$Highlight";
			}else {
				omo_num = 0;
				cont_name = "$Dehighlight";
			}
			//System.out.println(point_table.get(i).getTeamName().toUpperCase());
			//System.out.println(omo_num);
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + 
					"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
			
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tQualified" + row_id + " SET " + "" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tQualified" + row_id + " SET " + "Q" + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
					+ cont_name + "$TextAll$Rank*GEOM*TEXT SET "+ (i+1) + "." + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$PoinTeamName*GEOM*TEXT SET "+ point_table.get(i).getTeamName().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$PlayedValue*GEOM*TEXT SET "+ point_table.get(i).getPlayed() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$WinValue*GEOM*TEXT SET "+ point_table.get(i).getWon() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$LossValue*GEOM*TEXT SET "+ point_table.get(i).getLost() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$NrValue*GEOM*TEXT SET "+ point_table.get(i).getNoResult() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$PointsValue*GEOM*TEXT SET "+ point_table.get(i).getPoints() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$NRRValue*GEOM*TEXT SET "+ df.format(point_table.get(i).getNetRunRate()) + " \0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.330 PointsOffsetIn 1.330 \0");
		
	}
	public void populateBowlerStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId,List<Player> plyr, List<Team> team,List<Ground> ground, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			//String Home_or_Away="";
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName4().toUpperCase() + "\0");
			
			if(plyr.get(playerId - 1).getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + team.get(plyr.get(playerId-1).getTeamId()-1).getTeamName1() + "\0");
			
			if(plyr.get(playerId - 1).getBowlingStyle() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + 
						CricketFunctions.getbowlingstyle(plyr.get(playerId - 1).getBowlingStyle()).toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp*ACTIVE SET 1" + "\0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
			TimeUnit.MILLISECONDS.sleep(1200);	
		}
			
	}	
	public void populateTieIdDouble(PrintWriter print_writer,String viz_sence_path,String day,List<Fixture> fix,List<Team>team,MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 1;
			String Date = "";
			Calendar cal = Calendar.getInstance();
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + "" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + match.getSetup().getTournament() + "\0");
			if(day.toUpperCase().equalsIgnoreCase("TODAY")) {
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "TODAY'S MATCHES " + "\0");
			}
			else if(day.toUpperCase().equalsIgnoreCase("TOMORROW")) {
				cal.add(Calendar.DATE, +1);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "TOMORROW'S MATCHES " + "\0");
				
			}else if(day.toUpperCase().equalsIgnoreCase("DAY_AFTER_TOMORROW")) {
				cal.add(Calendar.DATE, +2);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + Date + "\0");
			}
			
			for(int i = 0; i <= fix.size()-1; i++) {
				if(fix.get(i).getDate().equalsIgnoreCase(Date)) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + row_id + " SET " + logo_path + team.get(fix.get(i).getHometeamid()-1).getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + row_id + " SET " + team.get(fix.get(i).getHometeamid()-1).getTeamName1().toUpperCase() + "\0");
					if(fix.get(i).getMatchnumber()<10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchNumberAndTime" + row_id + " SET " + 
								"MATCH "+ fix.get(i).getMatchnumber() + " AT " + fix.get(i).getLocalTime() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchNumberAndTime" + row_id + " SET " + 
								fix.get(i).getMatchfilename() + " AT " + fix.get(i).getLocalTime() + "\0");
					}
					
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + row_id + " SET " + logo_path + team.get(fix.get(i).getAwayteamid()-1).getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + row_id + " SET " + team.get(fix.get(i).getAwayteamid()-1).getTeamName1().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
							"LIVE FROM " + fix.get(i).getVenue() + "\0");

					row_id = row_id +1;
				}
			}
			
			
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.100 \0");
			
		}
		
	}
	public void populatePreviousSummary(PrintWriter print_writer, String viz_scene, int match_number, List<VariousText> vt, MatchAllData mtch,List<Fixture> fix, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Match Summary's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, max_Strap = 0,bat_impact_count=0,ball_impact_count=0;
			String teamname = "";
			boolean impactBatInThisInning = false, impactBowlInThisInning = false;
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$Bands$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			

			for(int i = 1; i <= 2 ; i++) {

				if(i == 1) {
					row_id = 0;
					max_Strap = 5;
					ball_impact_count = 1;
					bat_impact_count = 1;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 0 \0");
					
				} else {
					row_id = 5;
					max_Strap = 10;
					ball_impact_count = 6;
					bat_impact_count = 6;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 1 \0");
				}
				row_id = row_id + 1;
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " +
						mtch.getSetup().getMatchIdent() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "SUMMARY" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$SubHeader*GEOM*TEXT SET " +
						mtch.getSetup().getTournament().toUpperCase() + "\0");
				
				if(mtch.getMatch().getInning().get(i-1).getBattingTeamId() == mtch.getSetup().getHomeTeamId()) {
					teamname = mtch.getSetup().getHomeTeam().getTeamName1();
					//teamname_logo  = match.getHomeTeam().getTeamName4();
				} else {
					teamname = mtch.getSetup().getAwayTeam().getTeamName1();
					//teamname_logo = match.getAwayTeam().getTeamName4();
				}
				
				//print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Rectangle*TEXTURE*IMAGE SET " + logo_path + 
						//teamname_logo + CricketUtil.PNG_EXTENSION + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamFirstName*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamLastName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
				
				if(mtch.getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id +
						"$RowAnimation$TeamNameAll$SumTeamRuns*GEOM*TEXT SET " + mtch.getMatch().getInning().get(i-1).getTotalRuns() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id +
						"$RowAnimation$TeamNameAll$SumTeamRuns*GEOM*TEXT SET " + mtch.getMatch().getInning().get(i-1).getTotalRuns() + slashOrDash + 
						String.valueOf(mtch.getMatch().getInning().get(i-1).getTotalWickets()) + "\0");
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
					"$RowAnimation$TeamNameAll$OversGrp$SumTeamOvers*GEOM*TEXT SET " + CricketFunctions.OverBalls(mtch.getMatch().getInning().get(i-1).getTotalOvers(),
								mtch.getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
				if(mtch.getMatch().getInning().get(i-1).getBattingCard() != null) {
					Collections.sort(mtch.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					
					for(BattingCard bc : mtch.getMatch().getInning().get(i-1).getBattingCard()) {
						if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							row_id = row_id + 1;
							bat_impact_count = bat_impact_count + 1;
							
							if(CricketFunctions.isImpactPlayer(mtch.getEventFile().getEvents(), 2, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								impactBatInThisInning = true;
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + bat_impact_count + 
										"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBatsmanImpact" + (row_id-1) + " SET " + "1" + "\0");
							}else {
								if(impactBatInThisInning == false) {
									impactBatInThisInning = false;
								}
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + bat_impact_count + 
										"$RowAnimation$BatsmanGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBatsmanImpact" + (row_id-1) + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + bc.getRuns() + "\0");
							
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
							} else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
							if(i == 1 && row_id >= 5) {
								break;
							}else if(i == 2 && row_id >= 10) {
								break;
							}
						}
					}
				}

				for(int k = row_id + 1; k <= max_Strap; k++) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + k + "$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
				}
				
				if(i == 1) {
					row_id = 1;
				}
				else {
					row_id = 6;
				}

				if(mtch.getMatch().getInning().get(i-1).getBowlingCard() != null) {
					
					Collections.sort(mtch.getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

					for(BowlingCard boc : mtch.getMatch().getInning().get(i-1).getBowlingCard()) {
						
						if(boc.getWickets() > 0) {
							row_id = row_id + 1;
							ball_impact_count = ball_impact_count + 1;
							
							if(CricketFunctions.isImpactPlayer(mtch.getEventFile().getEvents(), 2, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								impactBowlInThisInning = false;
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + ball_impact_count + 
										"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "1" + " \0");
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBowlerImpact" + (row_id-1) + " SET " + "1" + "\0");
							}else {
								if(impactBowlInThisInning == false) {
									impactBowlInThisInning = false;
								}
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + ball_impact_count + 
										"$RowAnimation$BowlerGrp$TextAll$Impact*FUNCTION*Omo*vis_con SET " + "0" + " \0");
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBowlerImpact" + (row_id-1) + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerBall*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							
							if(i == 1 && row_id >= 5) {
								break;
							}
							else if(i == 2 && row_id >= 10) {
								break;
							}
						}
					}
				}
				if(impactBatInThisInning == true || impactBowlInThisInning == true) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$Star*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$noname*ACTIVE SET " + "1" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$Star*ACTIVE SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$ImpactLegend$noname*ACTIVE SET " + "0" + "\0");
				}
				
				for(int k = row_id + 1; k <= max_Strap; k++) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + k + "$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
				}
			}
			if(mtch.getMatch().getMatchResult() != null) {
				if(mtch.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch, CricketUtil.FULL, "|",broadcaster, false).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
				}
				else if(mtch.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ "MATCH TIED" + "\0");
				}
				else if(mtch.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ mtch.getMatch().getMatchStatus().toUpperCase() + "\0");
				}
				else if(mtch.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ "MATCH TIED - " + CricketFunctions.GenerateMatchSummaryStatus(2, mtch, CricketUtil.FULL,"|",broadcaster, false).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch, CricketUtil.FULL, "|",broadcaster, false).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
				}
			}
			else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
						+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch, CricketUtil.FULL, "|",broadcaster, false).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 \0");
				
		}
	}
	public void populateBatsmanStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, List<Player> plyr, List<Team> team, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName4().toUpperCase() + "\0");

			if(plyr.get(playerId - 1).getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
			}
			
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + team.get(plyr.get(playerId-1).getTeamId()-1).getTeamName1() + "\0");
			
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + team.get(plyr.get(playerId - 1).getTeamId() - 1).
					//getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
			
			if(plyr.get(playerId - 1).getBattingStyle() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + 
						CricketFunctions.getbattingstyle(plyr.get(playerId - 1).getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp*ACTIVE SET 1" + "\0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");	
		TimeUnit.MILLISECONDS.sleep(1200);	
	}
	public void populateManhattan(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		}else if(whichInning == 0) {
			this.status = "ERROR: Inning is null";
		}else {
			this.status = CricketUtil.SUCCESSFUL;
			int maxRuns = 0,runsIncr = 0;
			long lngth = 0;
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ " " + " \0");				
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ match.getSetup().getMatchIdent() + " \0");				

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ inn.getBatting_team().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tExtrasValue" + " SET "  + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOversValue" + " SET "  + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET "  + 
							CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				}
			}
			
			for (int j = 0; j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size(); j++) {
				if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getInningNumber() == whichInning) {
					if(Integer.valueOf(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns()) > maxRuns){
						maxRuns = Integer.valueOf(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns()); // 33 runs came off 34th over
					}
					
				 	while (maxRuns % 5 != 0) {     // 5 label in y-axis
				 		maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
					}
				}
			}
				
			for(int i =0; i < 5;i++) {
				runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36
		 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$lines$PlayerNameGrp$Row" + (5 - i) + "$RowAni$Runs*GEOM*TEXT SET " + runsIncr*(i+1) + "\0");
			}
			
			for(int j = 1; j <= match.getSetup().getMaxOvers(); j++) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll$Bar" + j + "*ACTIVE SET 0" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$Wickets$Wkt" + j + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");

				//if(CricketFunctions.getOverByOverData(match, whichInning,match.getEvents()).get(j).getInningNumber() == whichInning) {
					
				if(j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size()) {
					lngth = ((35 *Integer.valueOf(
							CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns())) / maxRuns); // 32 is max value of each bar
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll$Bar" + j + "*ACTIVE SET 1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll*FUNCTION*BarValues*Bar_Value__" + j + " SET " + lngth + "\0");
				
					if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$Wickets$Wkt" + j + "*FUNCTION*Omo*vis_con SET " + Integer.valueOf(
								CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$Wickets$Wkt" + j + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
					}
				
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll$Bar" + j + "*ACTIVE SET 0" + "\0");
				}
					
				//}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.700 In$BallOffsetIn 1.830 In$ManDataIn 2.700 In$ManDataIn$DataIn 1.576 \0");
		
			
	}
	public void populateWorm(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		}else if(whichInning == 0) {
			this.status = "ERROR: Inning is null";
		}
		else {
			this.status = CricketUtil.SUCCESSFUL;
			String teamname = "";
			int maxRuns = 0,runsIncr = 0,row_id = 0;
			double Lngth = 0;
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ " " + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getMatchIdent() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ 
					CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster, false).getTargetOrResult().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + "TLogo" + "\0");

//			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp1$Band*MATERIAL*COLOR SET 1.0 0.227450980392 0.0549019607843 \0");
//			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2$Band*MATERIAL*COLOR SET 1.0 0.827450980392 0.0 \0");
			
			List<String> overByOverRuns = new ArrayList<String>();
			for(int inn_count = 1; inn_count <= whichInning; inn_count++)
			{
				overByOverRuns.clear();
				for(OverByOverData Over : CricketFunctions.getOverByOverData(match,inn_count ,"WORM" ,match.getEventFile().getEvents())) {
					overByOverRuns.add(String.valueOf(Over.getOverTotalRuns()));
				}
				//System.out.println(overByOverRuns);
				String cumm_runs = String.valueOf(0) + "," + String.join(",", overByOverRuns); // Store Per Overs Runs
				
				
				if(match.getMatch().getInning().get(0).getTotalRuns() > match.getMatch().getInning().get(1).getTotalRuns()) {
					maxRuns = match.getMatch().getInning().get(0).getTotalRuns();
				}
				else {
					maxRuns = match.getMatch().getInning().get(1).getTotalRuns();
				}
				if(maxRuns % 5 == 0) {
					maxRuns = maxRuns + 1;
				}
				while (maxRuns % 5 != 0) {     // 5 label in y-axis
					maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
				}
				
				if(match.getMatch().getInning().get(inn_count-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {            
					teamname = match.getSetup().getHomeTeam().getTeamName4().toUpperCase();
					
				} else {
					teamname = match.getSetup().getAwayTeam().getTeamName4().toUpperCase();
				}
				
				for(int k = 0; k < 5; k++) {           // For Y-Axis Value 
					runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36	
				 	print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$group$PlayerNameGrp$Row" + (5 - k) + 
				 			"$RowAni$Runs*GEOM*TEXT SET " + runsIncr *  (k + 1) + "\0");
				}
				
				row_id = row_id + 1;
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + 
						"$TextAll$TeamName*GEOM*TEXT SET "+ teamname + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Score*GEOM*TEXT SET "+ 
									CricketFunctions.getTeamScore(match.getMatch().getInning().get(inn_count-1), slashOrDash, false) + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Overs*GEOM*TEXT SET "+ 
									CricketFunctions.OverBalls(match.getMatch().getInning().get(inn_count-1).getTotalOvers(), match.getMatch().getInning().get(inn_count-1).getTotalBalls()) + " \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXFit SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXFit SET 1 \0");

				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataYOffset SET 1.0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataYOffset SET 1.0 \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXOffset SET 1.5 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXOffset SET 1.5 \0");
				
				Lngth =  (80.62 / maxRuns); // 100 is max value of each bar
				
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXScale" + " SET " + "1" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXScale" + " SET " + "1" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vDataScaleY" + " SET " + Lngth + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
						"*GEOM*DataY SET " + cumm_runs.replaceFirst("0,", "") + " \0");
				//System.out.println(cumm_runs);
				if(inn_count == 1) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 0 \0");
				}
				else {						
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 1 \0");
				}
				
				for (int j = 1; j <= match.getSetup().getMaxOvers(); j++) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$Wkt" + 
							(j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
					//System.out.println("j = " + j);
					if(j < CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).size()) {
						if(CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
									"$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + Integer.valueOf(CricketFunctions.getOverByOverData
											(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
									"$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
						}
					}
				}
			}	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.780 \0");
		
			
	}
	public void populateSchedule(PrintWriter print_writer,String viz_scene,String whichSchedule, List<Fixture> fixture,List<Team> team, List<VariousText> variousText,MatchAllData match ,String broadcaster) throws ParseException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: bat vs all bowler card inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 0);
			int row_id = 0, omo = 0, todayMatchCount = 0;
			String cont = "";
			
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " +"" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " +"SCHEDULE" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " +match.getSetup().getTournament().toUpperCase() + "\0");
			
			for(VariousText vartext : variousText) {
				if(vartext.getVariousType().equalsIgnoreCase("SCHEDULEFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$BottomInfoGrp$Equations*GEOM*TEXT SET " + vartext.getVariousText() + "\0");
				}else if(vartext.getVariousType().equalsIgnoreCase("SCHEDULEFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$BottomInfoGrp$Equations*GEOM*TEXT SET " + "UPCOMING MATCHES" + "\0");
				}
			}
			
			switch (whichSchedule.toUpperCase()) {
			case "FIRSTSEVEN":
				row_id = 0;
				for(int i=0; i<=6; i++) {
					row_id++;
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TimeStampGrp*ACTIVE SET " + "0" + "\0");
					if(fixture.get(i).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
						todayMatchCount++;
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+"$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
						cont = "$Dehighlight";
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$TimeOmo*FUNCTION*Omo*vis_con SET "+ "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$DateText*GEOM*TEXT SET " + "TODAY" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+"$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
						cont = "$Highlight";
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$TimeOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$DateText*GEOM*TEXT SET " + CricketFunctions.ordinal(Integer.valueOf(fixture.get(i).getDate().split("-")[0]))
						+ " " + Month.of(Integer.valueOf(fixture.get(i).getDate().split("-")[1])) + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeam"+row_id + " SET " + team.get(fixture.get(i).getHometeamid()-1).getTeamName1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeam"+row_id + " SET " + team.get(fixture.get(i).getAwayteamid()-1).getTeamName1() + "\0");
					if(fixture.get(i).getMargin() != null && !fixture.get(i).getMargin().isEmpty()) {
						if(fixture.get(i).getWinnerteam() != null && !fixture.get(i).getWinnerteam().isEmpty()) {
							if(fixture.get(i).getWinnerteam().equalsIgnoreCase(team.get(fixture.get(i).getHometeamid()-1).getTeamName1())) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "2" + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
						}
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
					}
					
				}
				if(todayMatchCount == 2) {
					
				}else if(todayMatchCount == 1) {
					
				}
				break;

			case "SECONDSEVEN":
				row_id = 0;
				for(int i=7; i<=14; i++) {
					row_id++;
					if(fixture.get(i).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+"$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
						cont = "$Dehighlight";
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$TimeOmo*FUNCTION*Omo*vis_con SET "+ "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$DateText*GEOM*TEXT SET " + "TODAY" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+"$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
						cont = "$Highlight";
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$TimeOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$DateText*GEOM*TEXT SET " + CricketFunctions.ordinal(Integer.valueOf(fixture.get(i).getDate().split("-")[0]))
						+ " " + Month.of(Integer.valueOf(fixture.get(i).getDate().split("-")[1])) + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeam"+row_id + " SET " + team.get(fixture.get(i).getHometeamid()-1).getTeamName1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeam"+row_id + " SET " + team.get(fixture.get(i).getAwayteamid()-1).getTeamName1() + "\0");
					if(fixture.get(i).getMargin() != null && !fixture.get(i).getMargin().isEmpty()) {
						if(fixture.get(i).getWinnerteam() != null && !fixture.get(i).getWinnerteam().isEmpty()) {
							if(fixture.get(i).getWinnerteam().equalsIgnoreCase(team.get(fixture.get(i).getHometeamid()-1).getTeamName1())) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "2" + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
						}
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
					}
				}
				break;
			case "THIRDSEVEN":
				row_id = 0;
				for(int i=15; i<=21; i++) {
					row_id++;
					if(fixture.get(i).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+"$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
						cont = "$Dehighlight";
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$TimeOmo*FUNCTION*Omo*vis_con SET "+ "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$DateText*GEOM*TEXT SET " + "TODAY" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+"$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
						cont = "$Highlight";
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$TimeOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$DateText*GEOM*TEXT SET " + CricketFunctions.ordinal(Integer.valueOf(fixture.get(i).getDate().split("-")[0]))
						+ " " + Month.of(Integer.valueOf(fixture.get(i).getDate().split("-")[1])) + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeam"+row_id + " SET " + team.get(fixture.get(i).getHometeamid()-1).getTeamName1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeam"+row_id + " SET " + team.get(fixture.get(i).getAwayteamid()-1).getTeamName1() + "\0");
					if(fixture.get(i).getMargin() != null && !fixture.get(i).getMargin().isEmpty()) {
						if(fixture.get(i).getWinnerteam() != null && !fixture.get(i).getWinnerteam().isEmpty()) {
							if(fixture.get(i).getWinnerteam().equalsIgnoreCase(team.get(fixture.get(i).getHometeamid()-1).getTeamName1())) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "2" + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
						}
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$TeamAll1$Row"+row_id+cont+"$ResultOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
					}
				}
				break;
			}
		}
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.750 \0");
		
	}
	
	public void populateBatsmanVsAllBowlers(PrintWriter print_writer, String viz_scene, int whichInning, int batter_id, MatchAllData match, CricketService cricketService, String broadcaster) {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: bat vs all bowler card inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0;
			List<Player> playerList = cricketService.getAllPlayer();
			List<Team> teamList = cricketService.getTeams();
			
			Player player = playerList.stream().filter(plyr -> plyr.getPlayerId() == batter_id).findAny().orElse(null);
			Team team = teamList.stream().filter(tm->tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
			
			ArrayList<BestStats> batter_data = CricketFunctions.getBatsmanRunsVsAllBowlers(batter_id, whichInning, playerList, match);
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
			
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + "v ALL BOWLERS" + "\0");
			
			for(int i=0; i<2; i++) {
				for(BattingCard bc : match.getMatch().getInning().get(i).getBattingCard()) {
					if(bc.getPlayerId() == batter_id) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " +  
								player.getTicker_name()+"  "+bc.getRuns()+" ("+bc.getBalls()+")" + "\0");
						break;
					}
				}
			}
			
			for(BestStats bs : batter_data) {
				row_id++;
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + "0" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
						bs.getPlayer().getTicker_name() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
						bs.getRuns() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
						bs.getBalls() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batter_data.size() + "\0");
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.580 In$DataIn 1.580 \0");
		}
	}
	
	public void populateBowlerVsAllBatsman(PrintWriter print_writer, String viz_scene, int whichInning, int bowler_id, MatchAllData match, CricketService cricketService, String broadcaster) {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: bowl vs all batter inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0;
			List<Player> playerList = cricketService.getAllPlayer();
			Player player = playerList.stream().filter(plyr -> plyr.getPlayerId() == bowler_id).findAny().orElse(null);
			
			ArrayList<BestStats> bowler_data = CricketFunctions.getBowlerVsAllBat(bowler_id, whichInning, playerList, match);
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
			
			for(int i=0; i<2; i++) {
				if(match.getMatch().getInning().get(i).getBowlingCard() != null) {
					for(BowlingCard boc : match.getMatch().getInning().get(i).getBowlingCard()) {
						if(boc.getPlayerId() == bowler_id) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamFirstName" + " SET " + player.getTicker_name() +
									"  "+boc.getWickets()+"-"+boc.getRuns() +" ("+CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls())+")"+ "\0");
							break;
						}
					}
				}
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamLastName" + " SET " + "v ALL BATSMEN" + "\0");

			for(BestStats bs : bowler_data) {
				row_id++;
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_id + " SET " + "0" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_id + " SET " + 
						bs.getPlayer().getTicker_name() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallFiguresValue" + row_id + " SET " + 
						bs.getRuns() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + row_id + " SET " + 
						bs.getBalls() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
			}
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallRows" + " SET " + bowler_data.size() + "\0");
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.432 In$DataIn 1.432 \0");
		}
	}
	
	public void populateGriff(PrintWriter print_writer, String viz_scene, int whichInning, String whichProfile, int playerId, CricketService cricketService, List<HeadToHeadPlayer> headToHead, MatchAllData match, String broadcaster) throws InterruptedException, IOException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: mini batting card inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			boolean playerFound = false;
			int count = 0, row_id=0;
			String MatchName = "";
			
			Inning inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning).findAny().orElse(null);
			Player player = cricketService.getAllPlayer().stream().filter(plyr ->plyr.getPlayerId() == playerId).findAny().orElse(null);
			Team team = cricketService.getTeams().stream().filter(tm->tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
			
			switch (whichProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				playerFound = false;
				count = 0;
				row_id = 0;
				MatchName = "";
				if(player.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + player.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + player.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + player.getFirstname() + "\0");
				}
				for(HeadToHeadPlayer h2h : headToHead) {
					if(h2h.getPlayerId() == playerId && h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
						row_id++;
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_id + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
						MatchName = h2h.getMatchFileName();
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + "v "+h2h.getOpponentTeam().getTeamName3() + "\0");
						if(h2h.getInningStarted().contains("Y")) {
							if(h2h.getDismissed().contains("N")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " +h2h.getRuns()+"*" + "\0");
							}else if(h2h.getDismissed().contains("Y")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " +h2h.getRuns() + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " +h2h.getBallsFaced() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " +"" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " +"DNB" + "\0");
						}
						count = 0;
					}else if(h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
						if(!MatchName.equalsIgnoreCase(h2h.getMatchFileName()) && count <= 11) {
							MatchName = h2h.getMatchFileName();
							count = 1;
						}else if(count == 11) {
								row_id++;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_id + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + "0" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + "v "+h2h.getOpponentTeam().getTeamName3() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " +"" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " +"DNP" + "\0");
								count = 0;
						}else {
							count++;
						}
					}
				}
				for(BattingCard bc : inning.getBattingCard()) {
					if(bc.getPlayerId() == playerId) {
						row_id++;
						playerFound = true;
						if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_id + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + "1" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + "v "+inning.getBowling_team().getTeamName3() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " +"" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " +"DNB" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_id + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + "1" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + "v "+inning.getBowling_team().getTeamName3() + "\0");
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " +bc.getRuns() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " +bc.getRuns()+"*" + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " +bc.getBalls() + "\0");
						}
					}
				}
				if(!playerFound) {
					row_id++;
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_id + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_id + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + "v "+inning.getBowling_team().getTeamName3() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " +"" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " +"DNP" + "\0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.580 In$DataIn 1.580 \0");
				break;

			case CricketUtil.BOWLER:
				playerFound = false;
				count = 0;
				row_id = 0;
				MatchName = "";
				if(player.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamFirstName" + " SET " + player.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamLastName" + " SET " + player.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamFirstName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamLastName" + " SET " + player.getFirstname() + "\0");
				}
				for(HeadToHeadPlayer h2h : headToHead) {
					if(h2h.getPlayerId() == playerId && h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
						row_id++;
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallRows" + " SET " + row_id + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_id + " SET " + "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
						MatchName = h2h.getMatchFileName();
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_id + " SET " + "v "+h2h.getOpponentTeam().getTeamName3() + "\0");
						
						if(h2h.getBallsBowled() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallFiguresValue" + row_id + " SET " +"DNB" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + row_id + " SET " +"" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallFiguresValue" + row_id + " SET " +h2h.getWickets() +"-"+h2h.getRunsConceded()  + "\0");
							if(h2h.getBallsBowled()%6 == 0) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + row_id + " SET " + (h2h.getBallsBowled()/6) + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + row_id + " SET " + (h2h.getBallsBowled()/6)+"."+h2h.getBallsBowled()%6 + "\0");
							}
						}
						count = 0;
					}else if(h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
						if(count == 11) {
							row_id++;
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallRows" + " SET " + row_id + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_id + " SET " + "v "+h2h.getOpponentTeam().getTeamName3() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallFiguresValue" + row_id + " SET " +"DNP" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + row_id + " SET " +"" + "\0");
							count = 0;
						}else if(!MatchName.equalsIgnoreCase(h2h.getMatchFileName()) && count < 11) {
							MatchName = h2h.getMatchFileName();
							count = 1;
						}else {
							if(count==10) {
								count=0;
							}
							count++;
						}
					}
				}
				
				boolean playerIsInBoc = false;
				for(BowlingCard boc : inning.getBowlingCard()) {
					if(boc.getPlayerId() == playerId) {
						playerIsInBoc = true;
						row_id++;
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallRows" + " SET " + row_id + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_id + " SET " + "1" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_id + " SET " + "v "+inning.getBatting_team().getTeamName3() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallFiguresValue" + row_id + " SET " +boc.getWickets()+"-"+ boc.getRuns()+ "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + row_id + " SET " +CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
						break;
					}else {
						playerIsInBoc = false;
					}
				}
				if(!playerIsInBoc) {
					row_id++;
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallRows" + " SET " + row_id + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_id + " SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_id + " SET " + "v "+inning.getBatting_team().getTeamName3() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallFiguresValue" + row_id + " SET " +"DNB" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + row_id + " SET " +"" + "\0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.432 In$DataIn 1.432 \0");
				break;
			}
			TimeUnit.MILLISECONDS.sleep(500);
		}
	}
	
	public void populateMiniBattingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: mini batting card inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, omo_num = 0,batting_size=0;
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + inn.getBatting_team().getTeamName2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBatting_team().getTeamName3() + "\0");

					
					Collections.sort(inn.getBattingCard());
					
					for (BattingCard bc : inn.getBattingCard()) {
						
						row_id = row_id + 1;
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
							if(bc.getHowOut() != null) {
								if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
									//System.out.println(bc.getStatus());
									batting_size+=1;
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + "0" + "\0");
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
											bc.getPlayer().getTicker_name() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
											bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
											String.valueOf(bc.getBalls()) + "\0");
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
									}
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
									batting_size+=1;
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + "0" + "\0");
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
											bc.getPlayer().getTicker_name() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
											bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
											String.valueOf(bc.getBalls()) + "\0");
									if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
									}
								}
							}
							break;
						default:
							switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.OUT:
								omo_num = 0;
								batting_size = batting_size + 1;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
								break;
							case CricketUtil.NOT_OUT:
								omo_num = 1;
								batting_size = batting_size + 1;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
								break;
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + omo_num + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
									bc.getPlayer().getTicker_name() + "\0");
							
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
										bc.getRuns() + "\0");
							}else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)){
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
										bc.getRuns() + "\0");
								
//								if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
//											bc.getRuns() + "*" + "\0");
//								}else {
//									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
//											bc.getRuns() + "\0");
//								}
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
									String.valueOf(bc.getBalls()) + "\0");
							
							if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
							}
						}
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.580 In$DataIn 1.580 \0");
			
		}
	}
	public void populateMiniBowlingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Bowlingcard's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, omo_num = 0;
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamFirstName" + " SET " + inn.getBowling_team().getTeamName2() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamLastName" + " SET " + inn.getBowling_team().getTeamName3() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallRows" + " SET " + inn.getBowlingCard().size() + "\0");
					
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						switch (boc.getStatus().toUpperCase()) {
						case (CricketUtil.OTHER + CricketUtil.BOWLER):
							omo_num = 0;
							break;
						case (CricketUtil.LAST + CricketUtil.BOWLER):
							omo_num = 0;
							break;
						case (CricketUtil.CURRENT + CricketUtil.BOWLER):
							omo_num = 1;
							break;
						}
						
						row_id = row_id + 1;
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_id + " SET " + omo_num + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_id + " SET " + 
								boc.getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallFiguresValue" + row_id + " SET " + 
								boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + row_id + " SET " + 
								CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");
						
						if(CricketFunctions.isImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "1" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
						}
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.432 In$DataIn 1.432 \0");
			
		}
			
	}
	public void populateThisSeriesBat(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster, Statistics stats, Configuration config) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			double strike_rate = 0;
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
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "STRIKE RATE" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getSurname() + "\0");
					switch (TypeofProfile.toUpperCase()) {
					case "APLCAREER":
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "APL CAREER" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
						if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBalls_faced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
						}
						
						for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
							
							if(top_batsman_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									k += 1;
									if(top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
										if(stats.getBest_score().contains("*")) {
											if(Integer.valueOf(stats.getBest_score().replace("*", ""))>(top_batsman_beststats.get(j).getBestEquation()/2)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_score() + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + top_batsman_beststats.get(j).getBestEquation()/2 + "\0");
											}
										}else {
											if(Integer.valueOf(stats.getBest_score())>(top_batsman_beststats.get(j).getBestEquation()/2)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_score() + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + top_batsman_beststats.get(j).getBestEquation()/2 + "\0");
											}
										}
									}else {
										if(stats.getBest_score().contains("*")) {
											if(Integer.valueOf(stats.getBest_score().replace("*", ""))>(top_batsman_beststats.get(j).getBestEquation()/2)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_score()+ "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
														(top_batsman_beststats.get(j).getBestEquation()-1)/2 + "*" + "\0");
											}
										}else {
											if(Integer.valueOf(stats.getBest_score())>(top_batsman_beststats.get(j).getBestEquation()/2)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
														stats.getBest_score() + "\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
														(top_batsman_beststats.get(j).getBestEquation()-1) / 2 + "*" + "\0");
											}
										}
									}
									break;
								}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}
						}
						break;
					case "APLSEASON1": case "APLSEASON2":
						if(TypeofProfile.equalsIgnoreCase("APLSEASON1")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "APL SEASON 1" + "\0");
						}else if(TypeofProfile.equalsIgnoreCase("APLSEASON2")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "APL SEASON 2" + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
						if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBalls_faced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
						}
						if(stats.getBest_score() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
									stats.getBest_score() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
									"-" + "\0");
						}
						break;
					case "THISSERIES":
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "THIS SEASON" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getRuns() + "\0");
						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							strike_rate = this_series.get(i).getRuns() * 100;
							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
						}
						for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
							
							if(top_batsman_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									k += 1;
									if(top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												top_batsman_beststats.get(j).getBestEquation()/2 + "\0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												(top_batsman_beststats.get(j).getBestEquation()-1) / 2 + "*" + "\0");
									}
									break;
								}
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");	
			
		}
	}
	
	public void populateThisSeriesBowl(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster, Statistics stats, Configuration config) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			double economy_rate=0;
			int k=0;
			
			System.out.println("IN THIS SERIES BOWL");
			
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
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON." + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getSurname() + "\0");
					
					switch (TypeofProfile.toUpperCase()) {
					case "APLCAREER":
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "APL CAREER" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
						if(stats.getBalls_bowled() == 0 || stats.getRuns_conceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRuns_conceded()*1.00) /stats.getBalls_bowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df = new DecimalFormat("0.00");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
						}
						boolean playerFound = false;
						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
							if(top_bowler_beststats.get(j).getPlayerId() == Playerid) {
								playerFound = true;
								if(k == 0) {
									k += 1;
									System.out.println(top_bowler_beststats.get(j).getBestEquation() % 1000);
									if(top_bowler_beststats.get(j).getBestEquation() > 0) {
										if(top_bowler_beststats.get(j).getBestEquation() % 1000 >= 0) {
											if(stats.getBest_figures().contains("-")) {
												if(Integer.valueOf(stats.getBest_figures().split("-")[0])>((top_bowler_beststats.get(j).getBestEquation() / 1000) +1)){
													print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_figures().split("-")[0]+"-" +stats.getBest_figures().split("-")[1]+ "\0");
												}else if(Integer.valueOf(stats.getBest_figures().split("-")[1]) == (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000))) {
													System.out.println(stats.getBest_figures().split("-")[1]+" : "+(1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)));
													if(Integer.valueOf(stats.getBest_figures().split("-")[1]) > (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000))) {
														print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
																stats.getBest_figures().split("-")[0]+"-"+stats.getBest_figures().split("-")[1] + "\0");
													}else {
														print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
																((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
													}
												}else {
													System.out.println("HEY");
													print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
															((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
												}
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
											}
											
										}
										else if(top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
											System.out.println("HELLO2");
											if(stats.getBest_figures().contains("-")) {
												if(Integer.valueOf(stats.getBest_figures().split("-")[0])>((top_bowler_beststats.get(j).getBestEquation() / 1000))){
													print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_figures().split("-")[0]+"-"+stats.getBest_figures().split("-")[1] + "\0");
												}else if(Integer.valueOf(stats.getBest_figures().split("-")[0]) == ((top_bowler_beststats.get(j).getBestEquation() / 1000))) {
													if(Integer.valueOf(stats.getBest_figures().split("-")[1]) > Math.abs(top_bowler_beststats.get(j).getBestEquation())) {
														print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
																stats.getBest_figures().split("-")[0]+"-"+stats.getBest_figures().split("-")[1] + "\0");
													}else {
														print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
																((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
													}
												}else {
													print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
															(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
												}
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
											}
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_figures().split("-")[0]+"-" +stats.getBest_figures().split("-")[1]+ "\0");
									}
									break;
								}
								break;
							}
						}
						if(playerFound == false) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
						}
						
//						if(stats.getBest_figures() != null) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_figures() + "\0");
//						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
//						}
						break;
					case "APLSEASON1": case "APLSEASON2":
						if(TypeofProfile.equalsIgnoreCase("APLSEASON1")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "APL SEASON 1" + "\0");
						}else if(TypeofProfile.equalsIgnoreCase("APLSEASON2")) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "APL SEASON 2" + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
						if(stats.getBalls_bowled() == 0 || stats.getRuns_conceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRuns_conceded()*1.00) /stats.getBalls_bowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df = new DecimalFormat("0.00");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
						}
						if(stats.getBest_figures() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_figures() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
						}
						break;
					case "THISSERIES":
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "THIS SEASON" + "\0");
						System.out.println("MATCHES : "+this_series.get(i).getMatches()+" : "+this_series.get(i).getWickets());
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getWickets() + "\0");
						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df = new DecimalFormat("0.00");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
						}
						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
							if(top_bowler_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									k += 1;
									if(top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
									}
									else if(top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
									}
									break;
								}
							}else if(top_bowler_beststats.get(j).getPlayerId() != Playerid) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
							}
						}
						break;
					}	
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");	
		}
	}
	
//	public void populateThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster) {
//		
//		if (match == null) {
//			this.status = "ERROR: Match is null";
//		} else {
//			
//			double strike_rate = 0 , economy_rate=0;
//			int k=0;
//		
//			List<BestStats> top_batsman_beststats = new ArrayList<BestStats>();
//			List<BestStats> top_bowler_beststats = new ArrayList<BestStats>();
//			for(Tournament tourn : this_series) {
//				for(BestStats bs : tourn.getBatsman_best_Stats()) {
//					top_batsman_beststats.add(bs);
//				}
//				for(BestStats bfig : tourn.getBowler_best_Stats()) {
//					top_bowler_beststats.add(bfig);
//				}
//			}
//			
//			Collections.sort(top_batsman_beststats, new CricketFunctions.PlayerBestStatsComparator());
//			Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
//			
//			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
//			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "THIS SERIES" + "\0");
//			
//			for(int i = 0; i <= this_series.size() - 1 ; i++) {
//				if(this_series.get(i).getPlayerId() == Playerid) {
//					
//					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
//							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
//					}else {
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/APL/Logos/" + 
//							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
//					}
//					
//					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + 
//							this_series.get(i).getPlayer().getFirstname() + "\0");
//					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
//							this_series.get(i).getPlayer().getSurname() + "\0");
//					
//					
//					switch(TypeofProfile.toUpperCase()) {
//					case CricketUtil.BATSMAN:
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
//
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getRuns() + "\0");
//						
//						
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "S/R" + "\0");			
//						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
//						}else {
//							strike_rate = this_series.get(i).getRuns() * 100;
//							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
//							DecimalFormat df = new DecimalFormat("0.0");
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
//						}
//						 
//						for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
//							if(top_batsman_beststats.get(j).getPlayerId() == Playerid) {
//								if(k == 0) {
//									k += 1;
//									if(top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
//										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
//												top_batsman_beststats.get(j).getBestEquation()/2 + "\0");
//									}else {
//										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
//												(top_batsman_beststats.get(j).getBestEquation()-1) / 2 + "*" + "\0");
//									}
//									break;
//								}
//							}else {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
//							}
//						}
//						break;
//					case CricketUtil.BOWLER:
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
//
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getWickets() + "\0");
//						
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON." + "\0");
//						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
//						}else {
//							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
//							economy_rate = economy_rate * 6;
//							DecimalFormat df = new DecimalFormat("0.00");
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
//						}
//						
//						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
//							if(top_bowler_beststats.get(j).getPlayerId() == Playerid) {
//								if(k == 0) {
//									k += 1;
//									if(top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
//										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
//												((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
//									}
//									else if(top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
//										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
//												(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
//									}
//									break;
//								}
//							}else if(top_bowler_beststats.get(j).getPlayerId() != Playerid) {
//								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
//							}
//						}
//						break;
//					}
//					
//				}
//
//			}
//			
//			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");	
//			
//
//		}
//	}
//	public void populateFFThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster) throws InterruptedException {
//		
//		if (match == null) {
//			this.status = "ERROR: Match is null";
//		} else {
//			this.status = CricketUtil.SUCCESSFUL;
//			double strike_rate = 0 , economy_rate=0;
//			int omo_num = 0;
//			String cont_name = "";
//			
//			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
//			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SERIES" + "\0");
//			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
//			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");
//
//			for(int i = 0; i <= this_series.size() - 1 ; i++) {
//				if(this_series.get(i).getPlayerId() == Playerid) {
//					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "C:\\\\Images\\\\APL\\\\Photos\\\\" 
//								+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ "IMAGE*/Default/APL/Logos/" + 
//								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
//								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
//					}else {
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "C:\\\\Images\\\\APL\\\\Photos\\\\" 
//								+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + 
//								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
//									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
//					}
//					
//					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + 
//							this_series.get(i).getPlayer().getFirstname() + "\0");
//					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
//							this_series.get(i).getPlayer().getSurname() + "\0");
//					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + 
//							this_series.get(i).getPlayer().getFirstname() + "\0");
//					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
//							this_series.get(i).getPlayer().getSurname() + "\0");
//					
//					switch(TypeofProfile.toUpperCase()) {
//					case CricketUtil.BATSMAN:
//						
//						cont_name = "$Dehighlight";
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//
//						
//						if(this_series.get(i).getPlayer().getBattingStyle() != null) {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
//									CricketFunctions.getbattingstyle(this_series.get(i).getPlayer().getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
//						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
//						}
//						
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
//
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getRuns() + "\0");
//						
//						
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
//						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
//							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
//						}else {
//							strike_rate = this_series.get(i).getRuns() * 100;
//							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
//							DecimalFormat df = new DecimalFormat("0.0");
//							
//							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
//						}
//						
//						break;
//					case CricketUtil.BOWLER:
//						
//						cont_name = "$Dehighlight";
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//
//						if(this_series.get(i).getPlayer().getBowlingStyle() != null) {
//							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + 
//									CricketFunctions.getbowlingstyle(this_series.get(i).getPlayer().getBowlingStyle().toUpperCase())+ "\0");
//						}else {
//							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + " " + "\0");
//						}
//						
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
//
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getWickets() + "\0");
//																
//						
//						
//						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
//						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
//							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
//						}else {
//							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
//							economy_rate = economy_rate * 6;
//							DecimalFormat df_b = new DecimalFormat("0.00");
//							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
//						}
//						break;
//					}
//					
//				}
//			}
//			
//			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.700 \0");
//			
//
//			}
//	}
	
	public void populateFFThisSeriesBat(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster, Statistics stats, Configuration config) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			double strike_rate = 0 , economy_rate=0;
			int omo_num = 0;
			String cont_name = "";
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
									+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
//							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\APL\\Photos\\" +match.getSetup().getHomeTeam().getTeamName4() + 
//									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + 
										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					}else {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
									+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
//							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\APL\\Photos\\" +match.getSetup().getAwayTeam().getTeamName4() + 
//									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + 
										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					
					if(this_series.get(i).getPlayer().getSurname() != null) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
								this_series.get(i).getPlayer().getSurname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
								this_series.get(i).getPlayer().getSurname() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
								"" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
								"" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					
					cont_name = "$Dehighlight";
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

					
					if(this_series.get(i).getPlayer().getBattingStyle() != null) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
								CricketFunctions.getbattingstyle(this_series.get(i).getPlayer().getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
					
					switch (TypeofProfile.toUpperCase()) {
					case "APLCAREER":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "APL CAREER" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");
						
						if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBalls_faced();
							DecimalFormat df = new DecimalFormat("0.0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
						}
						break;
					case "APLSEASON1": case "APLSEASON2":
						if(TypeofProfile.equalsIgnoreCase("APLSEASON1")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "APL SEASON 1" + "\0");
						}else if(TypeofProfile.equalsIgnoreCase("APLSEASON2")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "APL SEASON 2" + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");
						
						if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBalls_faced();
							DecimalFormat df = new DecimalFormat("0.0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
						}
						break;
					case "THISSERIES":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SEASON" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getRuns() + "\0");
						
						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							strike_rate = this_series.get(i).getRuns() * 100;
							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
						}
						break;
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.700 \0");
			

			}
	}
	
	public void populateFFThisSeriesBowl(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster, Statistics stats, Configuration config) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			double strike_rate = 0 , economy_rate=0;
			int omo_num = 0;
			String cont_name = "";
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");

			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
									+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
//							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\APL\\Photos\\" +match.getSetup().getHomeTeam().getTeamName4() + 
//									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + 
										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					}else {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
									+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
//							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\APL\\Photos\\" +match.getSetup().getAwayTeam().getTeamName4() + 
//									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + 
										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					
					if(this_series.get(i).getPlayer().getSurname() != null) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
								this_series.get(i).getPlayer().getSurname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
								this_series.get(i).getPlayer().getSurname() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
								"" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
								"" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					
					cont_name = "$Dehighlight";
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

					
					if(this_series.get(i).getPlayer().getBattingStyle() != null) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
								CricketFunctions.getbowlingstyle(this_series.get(i).getPlayer().getBowlingStyle().toUpperCase()) + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
					
					switch (TypeofProfile.toUpperCase()) {
					case "APLCAREER":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "APL CAREER" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");
						
						if(stats.getBalls_bowled() == 0 || stats.getRuns_conceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRuns_conceded() * 1.00) / stats.getBalls_bowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
						}
						break;
					case "APLSEASON1": case "APLSEASON2":
						if(TypeofProfile.equalsIgnoreCase("APLSEASON1")){
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "APL SEASON 1" + "\0");
						}else if(TypeofProfile.equalsIgnoreCase("APLSEASON2")) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "APL SEASON 2" + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");
						
						if(stats.getBalls_bowled() == 0 || stats.getRuns_conceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRuns_conceded() * 1.00) / stats.getBalls_bowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
						}
						break;
					case "THISSERIES":
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SEASON" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getWickets() + "\0");
						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (this_series.get(i).getRunsConceded() * 1.00) / this_series.get(i).getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
						}
						break;
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.700 \0");
			

			}
	}
	
	public void populateFFstats(PrintWriter print_writer,String viz_scene,String StatType,List<Tournament> tournament,List<Team> team ,MatchAllData match, String broadcaster) {
		this.status = CricketUtil.SUCCESSFUL;
		int row_no=0;
		int omo_num = 0;
		String cont_name = "";
		switch(StatType.toUpperCase()) {
		case "MOST_RUNS":
			//int omo_num = 0;
			//String cont_name = "";
			Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "RUNS" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "RUNS" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "S/R" + "\0");
			
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament.get(i).getPlayer().getFull_name().toUpperCase() + " - Runs -" + tournament.get(i).getRuns());
				if(row_no < 10) {
					row_no = row_no + 1;
					for(Inning inn : match.getMatch().getInning()) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(tournament.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(bc.getPlayer().getFull_name().toUpperCase())) {
								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}
					
					
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getRuns() + "\0");
			 		
					if(tournament.get(i).getBallsFaced() >= 1) {
						DecimalFormat df = new DecimalFormat("0.0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 					+ cont_name + "$StatValueGrp$StatValue3*GEOM*TEXT SET " + df.format((100 * (double)tournament.get(i).getRuns()) / (double)tournament.get(i).getBallsFaced()) + "\0");
					}
				}else {
					break;
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
		case "MOST_WICKETS":
			
			Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "WICKETS" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "WICKETS" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "ECONOMY" + "\0");
			
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament.get(i).getPlayer().getFull_name().toUpperCase() + " - Runs -" + tournament.get(i).getRuns());
				if(row_no < 10) {
					row_no = row_no + 1;
					for(Inning inn : match.getMatch().getInning()) {
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(tournament.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(boc.getPlayer().getFull_name().toUpperCase())) {
								switch (boc.getStatus().toUpperCase()) {
								case CricketUtil.OTHER + CricketUtil.BOWLER: case CricketUtil.LAST + CricketUtil.BOWLER:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.CURRENT + CricketUtil.BOWLER:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}
					
					
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getWickets() + "\0");
			 		
					if(tournament.get(i).getBallsBowled() >= 1) {
						
						DecimalFormat df_b = new DecimalFormat("0.00");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 			+ cont_name + "$StatValueGrp$StatValue3*GEOM*TEXT SET " + df_b.format(((double)tournament.get(i).getRunsConceded() / (double)tournament.get(i).getBallsBowled())*6) + "\0");
					}
				}else {
					break;
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
		case "MOST_FOURS":
			Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "FOURS" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "3" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "FOURS" + "\0");
			
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament.get(i).getPlayer().getFull_name().toUpperCase() + " - Runs -" + tournament.get(i).getRuns());
				if(row_no < 10) {
					row_no = row_no + 1;
					for(Inning inn : match.getMatch().getInning()) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(tournament.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(bc.getPlayer().getFull_name().toUpperCase())) {
								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}
					
					
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getFours() + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
			
		case "MOST_SIXES":
			Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "SIXES" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "3" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "SIXES" + "\0");
			
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				//System.out.println("Name -" + tournament.get(i).getPlayer().getFull_name().toUpperCase() + " - Runs -" + tournament.get(i).getRuns());
				if(row_no < 10) {
					row_no = row_no + 1;
					for(Inning inn : match.getMatch().getInning()) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(tournament.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(bc.getPlayer().getFull_name().toUpperCase())) {
								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}
					
					
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getFours() + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
		case "HIGHEST_SCORE":
			List<BestStats> top_ten_beststat = new ArrayList<BestStats>();
			for(Tournament tourn : tournament) {
				for(BestStats bs : tourn.getBatsman_best_Stats()) {
					top_ten_beststat.add(bs);
				}
			}
			
			Collections.sort(top_ten_beststat, new CricketFunctions.PlayerBestStatsComparator());

			for(BestStats Top_ten_bs : top_ten_beststat) {
				System.out.println("Best Stats : " + Top_ten_bs);
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/APL/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "HIGHEST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "INDIVIDUAL SCORE" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "SCORE" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "BALLS" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "OPPONENT" + "\0");
			
			for(int i = 0; i <= top_ten_beststat.size() - 1 ; i++) {
				if(row_no < 10) {
					row_no = row_no + 1;
					
					for(Inning inn : match.getMatch().getInning()) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(top_ten_beststat.get(i).getPlayer().getFull_name().toUpperCase().equalsIgnoreCase(bc.getPlayer().getFull_name().toUpperCase())) {
								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 0;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 1;
									cont_name = "$Highlight";
									break;
								}
							}
						}
					}

			 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 							+ cont_name + "$StatHead*GEOM*TEXT SET " + top_ten_beststat.get(i).getPlayer().getFull_name() + "\0");
					if(top_ten_beststat.get(i).getBestEquation() % 2 == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
									+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + top_ten_beststat.get(i).getBestEquation() / 2 + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
									+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + top_ten_beststat.get(i).getBestEquation() / 2 + "*" + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
								+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + top_ten_beststat.get(i).getBalls() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
		 					+ cont_name + "$StatValueGrp$StatValue3*GEOM*TEXT SET " + top_ten_beststat.get(i).getOpponentTeam().getTeamName3().toUpperCase() + "\0");
					
				}	
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			break;
		}
	}
	
}

	