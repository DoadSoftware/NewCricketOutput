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
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import com.cricket.containers.BattingCardFF;
import com.cricket.containers.BowlingFF;
import com.cricket.containers.ImpactData;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.BatBallGriff;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Configuration;
import com.cricket.model.Event;
import com.cricket.model.EventFile;
import com.cricket.model.FallOfWicket;
import com.cricket.model.Fixture;
import com.cricket.model.Ground;
import com.cricket.model.HeadToHead;
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
import com.cricket.model.Setup;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PUNJAB_T20 extends Scene{

	public String broadcaster = "PUNJAB_T20"; 
	public String status;
	public String slashOrDash = "-";
	public String logo_path = "IMAGE*/Default/Punjab_Cup_2023/Logos/";
	public String photo_path = "C:\\\\Images\\\\Punjab_Cup_2023\\\\Photos\\\\";
	private String local_photo_path = "\\\\c\\\\Images\\\\Punjab_Cup_2023\\\\Photos\\\\";
	public String icon_path = "IMAGE*/Default/Punjab_Cup_2023/Icons";
	public Infobar infobar = new Infobar();
	public BattingCardFF bcf = new BattingCardFF();
	public BowlingFF bocf = new BowlingFF();
	public String which_graphic_on_screen = "";
	public String which_director_on_screen = "", power_play_on_screen = "";
	public boolean lastOverOnScreen = false;
	
	public PUNJAB_T20() {
		super();
	}

	public PUNJAB_T20(String scene_path, String which_Layer) {
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
//		CricketFunctions.getInteractive(match);
//		System.out.println("hello");
		return infobar;
	}
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches, List<Tournament> past_tournament_stats,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics, Configuration config, List<HeadToHeadPlayer> head_to_head) throws InterruptedException, ParseException, JAXBException, IllegalAccessException, InvocationTargetException, IOException, CloneNotSupportedException, URISyntaxException{
	
		switch (whatToProcess) {
		
		
		//scorebug	
		case "ANIMATE-IN-INFOBAR": case "ANIMATE-IN-IDENT": case "ANIMATE-OUT-SECTION2": case "ANIMATE-OUT-SECTION4_N_5":
		case "TICKER_LT_OUT": case "TICKER_LT_IN": case "ANIMATE-SHRINK_IN": case "ANIMATE-SHRINK_OUT":
		case "ANIMATE-OUT": case "ANIMATE-OUT-DIRECTOR": case "CLEAR-ALL": 
		
		//FF
		case "ANIMATE-IN-FIX_AND_RESULT":
		case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF":
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-DOUBLETEAMS":
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-EQUATION":
		case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO":
		case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS":
		case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-SCHEDULE":
		case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FFTHISSERIES_BALL": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-BALL_PERFORMER":
		case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-PLAYOFFS":
		
		//LT
		case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED":
		case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION":
		case "ANIMATE-IN-L3MATCHID": case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-BOWLERSUMMARY":
		case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH": 
		case "ANIMATE-IN-L3MATCH_PROMO": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": 
		case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-GENERIC": case "ANIMATE-IN-THISSERIES": case "ANIMATE-IN-THISSERIES_BALL":
		case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-LTPLAYERPROFILEBAT": case "ANIMATE-IN-POINTERS":
			
		//Bug
		case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET":
		case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-MULTI_PARTNERSHIP": case "ANIMATE-IN-BUG-TOSS":
		
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-FIX_AND_RESULT":
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-FF_STATS":
			case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": 
			case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": 
			case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE": case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": 
			case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM": case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FFTHISSERIES_BALL":
			case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-BALL_PERFORMER":
			case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-PLAYERPROFILEBAT":
			
			case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-L3MATCHID":
			case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET":
			case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-BUGPARTNERSHIP": case "ANIMATE-IN-MULTI_PARTNERSHIP": case "ANIMATE-IN-BUG-TOSS":
			
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					AnimateInGraphics(print_writer, "FF_IN");
					TimeUnit.MILLISECONDS.sleep(200);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					AnimateInGraphics(print_writer, "FF_IN");
				}
				break;
			case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS": case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED": 
			case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION":
			case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT":   case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": 
			case "ANIMATE-IN-LTPOWERPLAY": case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH":
			case "ANIMATE-IN-L3MATCH_PROMO": case "ANIMATE-IN-LTPOINTSTABLE":	case "ANIMATE-IN-BOWLER_STYLE": case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": 
			case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-GENERIC": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-THISSERIES": case "ANIMATE-IN-THISSERIES_BALL":
			case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-LTPLAYERPROFILEBAT": case "ANIMATE-IN-POINTERS":
			
			case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF":
			case "ANIMATE-SHRINK_IN": case "ANIMATE-SHRINK_OUT":
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
						switch(infobar.getLast_top_section().toUpperCase()) {
						case CricketUtil.TOSS:
							processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "CRR":
							processAnimation(print_writer, "Section2$CurRunRateOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "CRR_RRR":
							processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "FIRST_INNING_SCORE":
							processAnimation(print_writer, "Section2$FistInnScoreOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case CricketUtil.BOUNDARY:
							processAnimation(print_writer, "Section2$BallsSinceLastBoundaryOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "TARGET":
							processAnimation(print_writer, "Section2$DLSTargetOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "EXTRAS":
							processAnimation(print_writer, "Section2$ExtrasOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "EQUATION":
							processAnimation(print_writer, "Section2$EquationOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "PROJECTED":
							processAnimation(print_writer, "Section2$ProjectedOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "BOUNDARIES":
							processAnimation(print_writer, "Section2$BoundariesOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "PARTNERSHIP":
							processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case "LAST_WICKET":
							processAnimation(print_writer, "Section2$LastWicketOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						case CricketUtil.TIMELINE:
							processAnimation(print_writer, "Section6$TimelineOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section6$Section6BaseOut", "START", broadcaster);
							break;
						case "STATISTICS":
							processAnimation(print_writer, "Section2$FreeTextSmallOut", "START", broadcaster);
							processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
							break;
						}
					}
					infobar.setLast_top_section("");infobar.setTop_section("");
					AnimateInGraphics(print_writer, "LT_IN");
					TimeUnit.MILLISECONDS.sleep(200);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					AnimateInGraphics(print_writer, "LT_IN");
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
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					bcf.setLast_type("");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					TimeUnit.SECONDS.sleep(1);
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType*FUNCTION*Omo*vis_con SET 0 \0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
				}
				if(bocf.getLast_type() != null && !bocf.getLast_type().trim().isEmpty()) {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						if(bocf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerIn START \0");
							bocf.setLast_type(bocf.getType());
						}
					}
				}else {
					bocf.setLast_type(bocf.getType());
					if(bocf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(600);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						bocf.setLast_type(bocf.getType());
					}
				}
				
				which_graphic_on_screen = "BATBALLSUMMARY_BOWLINGCARD_PERFORMER";
				TimeUnit.SECONDS.sleep(1);
				break;
				
			case "ANIMATE-IN-BAT-PERFORMER":
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$"
//						+ "TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
					}
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					//TimeUnit.MILLISECONDS.sleep(200);
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					bocf.setLast_type("");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(600);
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 0 \0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
				}
				
				if(bcf.getLast_type() != null && !bcf.getLast_type().trim().isEmpty()) {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						if(bcf.getType().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$"
									+ "BatPartnershipGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipIn START \0");
							bcf.setLast_type(bcf.getType());
						}else if(bcf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$"
									+ "BatPerformerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerIn START \0");
							bcf.setLast_type(bcf.getType());
						}
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						if(bcf.getType().toUpperCase() == "PARTNERSHIP") {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPartnershipGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipIn START \0");
							bcf.setLast_type(bcf.getType());
						}else if(bcf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerIn START \0");
							bcf.setLast_type(bcf.getType());
						}
					}
				}else {
					bcf.setLast_type(bcf.getType());
					if(bcf.getType().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$"
								+ "BatPartnershipGrp*ACTIVE SET 1 \0");
						TimeUnit.MILLISECONDS.sleep(600);
						//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
						
						bcf.setLast_type(bcf.getType());
					}else if(bcf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
						TimeUnit.MILLISECONDS.sleep(200);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerIn START \0");
						TimeUnit.MILLISECONDS.sleep(900);
						//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
						bcf.setLast_type(bcf.getType());
					}
				}
				
				which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD_PERFORMER";
			
				TimeUnit.SECONDS.sleep(1);
				break;
			case "ANIMATE-IN-FIX_AND_RESULT":
				AnimateInGraphics(print_writer, "FIX_AND_RESULT");
				which_graphic_on_screen = "FIX_AND_RESULT";
				break;
			case "ANIMATE-IN-SCORECARD":
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					
					TimeUnit.MILLISECONDS.sleep(900);
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
					bocf.setLast_type("");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
				}else if(which_graphic_on_screen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
				}else {
					AnimateInGraphics(print_writer, "SCORECARD");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD";
				break;
			case "ANIMATE-MINI-BATTINGCARD":
				AnimateInGraphics(print_writer, "MINI_BATTINGCARD");
				which_graphic_on_screen = "MINI_BATTINGCARD";
				break;
			case "ANIMATE-IN-BALLGRIFF":
				AnimateInGraphics(print_writer, "BOWLGRIFF");
				which_graphic_on_screen = "BOWLGRIFF";
				break;
			case "ANIMATE-IN-BATGRIFF":
				AnimateInGraphics(print_writer, "BATGRIFF");
				which_graphic_on_screen = "BATGRIFF";
				break;
			case "ANIMATE-MINI-BOWLINGCARD":
				AnimateInGraphics(print_writer, "MINI_BOWLINGCARD");
				which_graphic_on_screen = "MINI_BOWLINGCARD";
				break;
			case "ANIMATE-IN-BOWLINGCARD":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
					
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					bcf.setLast_type("");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
				}else {
					AnimateInGraphics(print_writer, "BOWLINGCARD");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_BOWLINGCARD";
				break;
			case "ANIMATE-IN-PARTNERSHIP":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					bcf.setLast_type("");
					//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
						TimeUnit.MILLISECONDS.sleep(900);
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
					bocf.setLast_type("");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartIn START \0");
				}else {
					AnimateInGraphics(print_writer, "PARTNERSHIP");
				}
				which_graphic_on_screen = "PARTNERSHIP";
				break;
			case "ANIMATE-IN-TIEID-DOUBLE":
				AnimateInGraphics(print_writer, "TIEID-DOUBLE");
				which_graphic_on_screen = "TIEID-DOUBLE";
				break;
			case "ANIMATE-IN-MATCHSUMARRY":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					bcf.setLast_type("");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
						TimeUnit.MILLISECONDS.sleep(900);
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
					bocf.setLast_type("");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
				}else {
					AnimateInGraphics(print_writer, "MATCHSUMMARY");
				}
				which_graphic_on_screen = "BATBALLSUMMARY_MATCHSUMMARY";
				break;
			case "ANIMATE-IN-PLAYOFFS":
				AnimateInGraphics(print_writer, "PLAYOFFS");
				which_graphic_on_screen = "PLAYOFFS";
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
			case "ANIMATE-IN-THISSERIES":
				AnimateInGraphics(print_writer, "THISSERIES");
				which_graphic_on_screen = "THISSERIES";
				break;
			case "ANIMATE-IN-THISSERIES_BALL":
				AnimateInGraphics(print_writer, "THISSERIES-BALL");
				which_graphic_on_screen = "THISSERIES-BALL";
				break;
			case "ANIMATE-IN-FFTHISSERIES":
				AnimateInGraphics(print_writer, "FF-THISSERIES");
				which_graphic_on_screen = "FF-THISSERIES";
				break;
			case "ANIMATE-IN-FFTHISSERIES_BALL":
				AnimateInGraphics(print_writer, "FF-THISSERIES_BALL");
				which_graphic_on_screen = "FF-THISSERIES_BALL";
				break;
			case "ANIMATE-IN-PLAYERPROFILE":
				AnimateInGraphics(print_writer, "FFPLAYERPROFILE");
				which_graphic_on_screen = "FFPLAYERPROFILE";
				break;
			case "ANIMATE-IN-PLAYERPROFILEBAT":
				AnimateInGraphics(print_writer, "PLAYERPROFILEBAT");
				which_graphic_on_screen = "PLAYERPROFILEBAT";
				break;
			case "ANIMATE-IN-PLAYINGXI":
				AnimateInGraphics(print_writer, "TEAMLINEUP");
				which_graphic_on_screen = "TEAMLINEUP";
				break;
			case "ANIMATE-IN-DOUBLETEAMS":
				AnimateInGraphics(print_writer, "DOUBLETEAMS");
				which_graphic_on_screen = "DOUBLETEAMS";
				break;
			case "ANIMATE-IN-LANDMARK":
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
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(900);
					bcf.setLast_type("");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						TimeUnit.MILLISECONDS.sleep(900);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
						TimeUnit.MILLISECONDS.sleep(900);
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					TimeUnit.MILLISECONDS.sleep(200);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
					bocf.setLast_type("");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "PARTNERSHIP") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartOut START \0");
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PointsTable*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else {
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
			case "ANIMATE-SHRINK_IN":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_LT_In START \0");
				break;
			case "ANIMATE-SHRINK_OUT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_LT_Out START \0");
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
	               print_writer.println("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*" + valueToProcess.split(",")[0] + "\0");
	
	               print_writer.println("-1 RENDERER*BACK_LAYER INITIALIZE\0");
	               print_writer.println("-1 RENDERER*BACK_LAYER*SCENE_DATA INITIALIZE\0");
	               print_writer.println("-1 RENDERER*BACK_LAYER*UPDATE SET 0\0");
	               print_writer.println("-1 RENDERER*BACK_LAYER*STAGE SHOW 0.0\0");
	               
	               print_writer.println("-1 RENDERER*BACK_LAYER*UPDATE SET 1\0");
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/Punjab_Cup_2023/ScoreBug\0");
		           	
	               print_writer.println("-1 RENDERER*FRONT_LAYER INITIALIZE\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 0\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 1\0");
	               
	               print_writer.println("-1 SCENE CLEANUP\0");
	               print_writer.println("-1 IMAGE CLEANUP\0");
	               print_writer.println("-1 GEOM CLEANUP\0");
	               print_writer.println("-1 FONT CLEANUP\0");
	               
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
				case "BOWLGRIFF":
					AnimateOutGraphics(print_writer, "BOWLGRIFF");
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
				case "BATBALLSUMMARY_MATCHSUMMARY":
					AnimateOutGraphics(print_writer, "BATBALLSUMMARY_MATCHSUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "PARTNERSHIP":
					AnimateOutGraphics(print_writer, "PARTNERSHIP");
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
				case "POINTER":
					AnimateOutGraphics(print_writer, "POINTER");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
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
				case "PLAYOFFS":
					AnimateOutGraphics(print_writer, "PLAYOFFS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
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
					resetInfobarAnimation(print_writer,"FF_FRAME");
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
				case "FIX_AND_RESULT":
					AnimateOutGraphics(print_writer, "FIX_AND_RESULT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
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
				case "FF-THISSERIES":
					AnimateOutGraphics(print_writer, "FF-THISSERIES");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "FF-THISSERIES_BALL":
					AnimateOutGraphics(print_writer, "FF-THISSERIES_BALL");
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
				case "TEAMLINEUP":
					AnimateOutGraphics(print_writer, "TEAMLINEUP");
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
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "CRR":
						processAnimation(print_writer, "Section2$CurRunRateOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "CRR_RRR":
						processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "FIRST_INNING_SCORE":
						processAnimation(print_writer, "Section2$FistInnScoreOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case CricketUtil.BOUNDARY:
						processAnimation(print_writer, "Section2$BallsSinceLastBoundaryOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section2$DLSTargetOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section2$ExtrasOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "EQUATION":
						processAnimation(print_writer, "Section2$EquationOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "PROJECTED":
						processAnimation(print_writer, "Section2$ProjectedOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "BOUNDARIES":
						processAnimation(print_writer, "Section2$BoundariesOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "PARTNERSHIP":
						processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case "LAST_WICKET":
						processAnimation(print_writer, "Section2$LastWicketOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					case CricketUtil.TIMELINE:
						processAnimation(print_writer, "Section6$TimelineOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section6$Section6BaseOut", "START", broadcaster);
						break;
					case "STATISTICS":
						processAnimation(print_writer, "Section2$FreeTextSmallOut", "START", broadcaster);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						break;
					}
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
				}
				processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
				processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
				processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
				
				infobar.setBottom_right_section("");
				
				infobar.setBottom_right_bottom_section(CricketUtil.OVER);
				infobar = populateVizInfobarRightBottom(infobar, false,print_writer, match, broadcaster);
				
				infobar.setBottom_right_top_section(CricketUtil.BOWLER);
				infobar = populateVizInfobarRightTop(infobar, false,print_writer, match, broadcaster);
				
				processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
				processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
				processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
				
				infobar.setLast_bottom_right_section("");
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
		case "FIXTURE_AND_RESULT-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getFixtures()).toString();
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "L3_MATCH-PROMO_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
		case "BUG_DB_GRAPHICS-OPTIONS": case "BUG_DB2_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getBugs()).toString();
		case "POINTER_GRAPHICS-OPTIONS": case "LT_POINTERS_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getPointers()).toString();
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
		
		//scorebug
		case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-IDENT":
			
		//FF
		case "POPULATE-FF-FIX_AND_RESULT":
		case "POPULATE-MINI-BATTINGCARD": case "POPULATE-MINI-BOWLINGCARD":
		case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-FF-PLAYERPROFILE":
		case "POPULATE-FF-DOUBLETEAMS": case "POPULATE-FF-MATCHID": case "POPULATE-FF-PLAYINGXI": case "POPULATE-FF-LANDMARK": case "POPULATE-PREVIOUS_SUMMARY":
		case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-POINTS_TABLE": case "POPULATE-MANHATTAN": case "POPULATE-MATCH_PROMO": case "POPULATE-FF-TEAMS_LOGO":
		case "POPULATE-TIEID-DOUBLE": case "POPULATE-MOSTRUNS": case "POPULATE-MOSTWICKETS": case "POPULATE-MOSTFOURS": case "POPULATE-MOSTSIXES": case "POPULATE-HIGHESTSCORE":
		case "POPULATE-WORM": case "POPULATE-FF-SCHEDULE": case "POPULATE-FF-THISSERIES": case "POPULATE-FF-THISSERIES_BALL": case "POPULATE-FF-LEADERBOARD": case "POPULATE-FF-STATS": case "POPULATE-BAT_PERFORMER":
		case "POPULATE-BALL_PERFORMER": 
		case "POPULATE-FF-PLAYERPROFILEBALL": case "POPULATE-PLAYOFFS":
			
		//LT
		case "POPULATE-L3-HOWOUT": case "POPULATE-L3-BATSMANSTATS": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-LT-PROJECTED":
		case "POPULATE-L3-TARGET": case "POPULATE-L3-TEAMSUMMARY": case "POPULATE-L3-PLAYERSUMMARY": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-L3-FALLOFWICKET":
		case "POPULATE-L3-COMPARISION": case "POPULATE-LT-MATCHID": case "POPULATE-L3-BOWLERSTATS": case "POPULATE-L3-SPLIT": case "POPULATE-LT-PARTNERSHIP":
		case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER":case "POPULATE-L3-BOWLERSUMMARY": case "POPULATE-L3-NEXT_TO_BAT": case "POPULATE-L3-BOWLERDETAILS":
		case "POPULATE-LT-EQUATION": case "POPULATE-L3-BATSMAN_THIS_MATCH": case "POPULATE-L3-BOWLER_THIS_MATCH": case "POPULATE-LTPOINTS_TABLE": 
		case "POPULATE-BOWLER_STYLE": case "POPULATE-BATSMAN_STYLE": case "POPULATE-LT-POWERPLAY": case "POPULATE-NEXT_TO_BAT": case "POPULATE-L3MATCH_PROMO":
		case "POPULATE-HOWOUT_QUICK": case "POPULATE-L3-THISSERIES":  case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-L3-POINTERS": case "POPULATE-L3-THISSERIES_BALL":
		case "POPULATE-FF-BATGRIFF": case "POPULATE-FF-BALLGRIFF":
			
		//Bug
		case "POPULATE-L3-BUG": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-BUG-DB": case "POPULATE-L3-BUG-BOWLER": case "POPULATE-L3-BUGTARGET":   
		case "POPULATE-BUG_POWERPLAY": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-MULTI_PARTNERSHIP": case "POPULATE-BUGPARTNERSHIP": case "POPULATE-L3-BUG-TOSS":
			
			
			if(which_graphic_on_screen == "SCOREBUG" || which_graphic_on_screen == "IDENT") {
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
				 
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 
				 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				 
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
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER")) {
			}
			else if(which_graphic_on_screen != "") {
				AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
			}

			switch(whatToProcess.toUpperCase()) {
			case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-DIRECTOR": case "POPULATE-INFOBAR_IDENT_DATA":
			case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-RIGHT": case "POPULATE-SIXDIRECTOR":case "POPULATE-FOURDIRECTOR":case "POPULATE-WICKETDIRECTOR":case "POPULATE-FREEHITDIRECTOR":
			case "POPULATE-POWERPLAY":
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
						 
						 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
						 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
						 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
						 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
						 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
						 which_graphic_on_screen == "PARTNERSHIP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
						 
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
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
						 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER")) {
					//AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
				}else {
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,broadcaster);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE SHOW 0.0 \0");
					//TimeUnit.SECONDS.sleep(2);
				}
				break;
			}
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-FF-FIX_AND_RESULT":
				populateFixturesAndResult(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(), cricketService.getFixtures(), broadcaster, match);
				break;
			case "POPULATE-PLAYOFFS":
				populatePlayoffs(print_writer, valueToProcess.split(",")[0], cricketService.getPlayOff(),
						cricketService.getTeams(), broadcaster, match);
				break;
			case "POPULATE-NEXT_TO_BAT":
				populateLTNextToBat(print_writer,valueToProcess.split(",")[0],cricketService.getAllStats(),cricketService.getAllPlayer(),
						CricketFunctions.extractTournamentStats("PAST_MATCHES_DATA",false, tournament_matches, cricketService,match,null),match, broadcaster, config);
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
						which_graphic_on_screen == "POINTSTABLE" || which_graphic_on_screen == "PARTNERSHIP") {	
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 BowlingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 SummaryOut 0.500 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PointsTableOut 0.500 \0");
					}else if(which_graphic_on_screen == "PARTNERSHIP") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 ParteOut 0.500 \0");
					}
				}
//				TimeUnit.SECONDS.sleep(2);
				break;
			case "POPULATE-BALL_PERFORMER":
				populateBallPerformer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster, config);
				bocf.setType(valueToProcess.split(",")[2]);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE" || which_graphic_on_screen == "PARTNERSHIP") {	
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					  if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 BattingCardOut 0.500 \0");
					  }else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 SummaryOut 0.500 \0");
					  }else if(which_graphic_on_screen == "POINTSTABLE") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 PointsTableOut 0.500 \0");
					  }else if(which_graphic_on_screen == "PARTNERSHIP") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 PartOut 0.500 \0");
					  }else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 BatPerformerOut 0.400 \0");
					  }
				}
				TimeUnit.SECONDS.sleep(2);
				break;
				
			case "POPULATE-FF-SCORECARD":
				populateScorecard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match,cricketService, broadcaster);
				
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE" || which_graphic_on_screen == "PARTNERSHIP") {	
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 BowlingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 SummaryOut 0.500 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PointsTableOut 0.500 \0");
					}else if(which_graphic_on_screen == "PARTNERSHIP") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PartOut 0.500 \0");
					}
				}
				break;
				
			case "POPULATE-FF-BOWLINGCARD":
				populateBowlingcard(print_writer, valueToProcess.split(",")[0], false, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE" || which_graphic_on_screen == "PARTNERSHIP") {	
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 BattingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 SummaryOut 0.500 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 PointsTableOut 0.500 \0");
					}else if(which_graphic_on_screen == "PARTNERSHIP") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 PartOut 0.500 \0");
					}
				}
				break;
			
			case "POPULATE-MINI-BATTINGCARD":
				populateMiniBattingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-MINI-BOWLINGCARD":
				populateMiniBowlingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			
			case "POPULATE-FF-PARTNERSHIP":
				populatePartnership(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || 
						which_graphic_on_screen == "POINTSTABLE" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {	
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PartIn 1.700 PartOffsetIn 1.700 BattingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PartIn 1.630 PartOffsetIn 1.630 BowlingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PartIn 1.630 PartOffsetIn 1.630 PointsTableOut 0.500 \0");
					}else if(which_graphic_on_screen == "PARTNERSHIP") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 PartIn 1.630 PartOffsetIn 1.630 SummaryOut 0.500 \0");
					}
				}
				break;
				
			case "POPULATE-FF-MATCHSUMMARY":
				populateMatchsummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, cricketService.getVariousTexts(), broadcaster);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || 
						which_graphic_on_screen == "POINTSTABLE" || which_graphic_on_screen == "PARTNERSHIP") {	
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 BattingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 BowlingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 PointsTableOut 0.500 \0");
					}else if(which_graphic_on_screen == "PARTNERSHIP") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 PartOut 0.500 \0");
					}
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
				populateSchedule(print_writer, valueToProcess.split(",")[0],cricketService.getFixtures(),cricketService.getTeams(),match, broadcaster);
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
			case "POPULATE-L3-NAMESUPER-PLAYER":
				populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1], 
						Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(), match, broadcaster);
				break;
			case "POPULATE-FF-MATCHID":
				populateMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-MATCH_PROMO":
				populateMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
						cricketService.getFixtures(),match , broadcaster);
				break;
			case "POPULATE-L3MATCH_PROMO":
				populateLtMatchPromo(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
						cricketService.getFixtures(),match , broadcaster);
				break;
			case "POPULATE-LT-MATCHID":
				//System.out.println(valueToProcess.split(",")[0]);
				populateLTMatchId(print_writer,valueToProcess.split(",")[0],cricketService.getVariousTexts(), match, broadcaster);
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
			case "POPULATE-LT-PROJECTED":
				populateProjectedScore(print_writer,valueToProcess, match, broadcaster);
				break;
			case "POPULATE-L3-THISSERIES": case "POPULATE-L3-THISSERIES_BALL":
				if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("PT20CAREER")) {
					for (Statistics stats : cricketService.getAllStats()) {
						stats.setStats_type(cricketService.getStatsType(9));
						if (stats.getStatsTypeId() == 9) {
							if (stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
//								stats = CricketFunctions.updateH2h(stats, head_to_head, match);
//								stats = CricketFunctions.updateMatchData(stats, match);
//								stats = CricketFunctions.updateTournamentDataWithStatsPunjab(stats, tournament_matches, match);
//								stats = CricketFunctions.updateStatisticsWithMatchDataPunjab(stats, match);
								switch (whatToProcess.toUpperCase()) {
								case "POPULATE-L3-THISSERIES":
									populateThisSeriesBat(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA", false,tournament_matches, cricketService, match, past_tournament_stats)
											,match, broadcaster,stats, config);
									break;

								case "POPULATE-L3-THISSERIES_BALL":
									populateThisSeriesBowl(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA", false,tournament_matches, cricketService, match, past_tournament_stats)
										,match, broadcaster, stats, config);
									break;
								}
							}
						}
					}
				} else if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("PT20SEASON1")) {
					for (Statistics stats : cricketService.getAllStats()) {
						stats.setStats_type(cricketService.getStatsType(9));
						if (stats.getStatsTypeId() == 9) {
							if (stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
								switch (whatToProcess.toUpperCase()) {
								case "POPULATE-L3-THISSERIES":
									populateThisSeriesBat(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false,head_to_head, cricketService, match, past_tournament_stats)
											,match, broadcaster,stats, config);
									break;

								case "POPULATE-L3-THISSERIES_BALL":
									populateThisSeriesBowl(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false,head_to_head, cricketService, match, past_tournament_stats)
										,match, broadcaster, stats, config);
									break;
								}
							}
						}
					}
				} else if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("THISSERIES")) {
					Statistics stats = new Statistics();
					switch (whatToProcess.toUpperCase()) {
					case "POPULATE-L3-THISSERIES":
						populateThisSeriesBat(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
								CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA", false,tournament_matches, cricketService, match, past_tournament_stats)
								,match, broadcaster,stats, config);
						break;

					case "POPULATE-L3-THISSERIES_BALL":
						populateThisSeriesBowl(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
								CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA", false,tournament_matches, cricketService, match, past_tournament_stats)
							,match, broadcaster, stats, config);
						break;
					}
				}
				break;
			case "POPULATE-FF-THISSERIES":case "POPULATE-FF-THISSERIES_BALL":
				if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("PT20CAREER")) {
					for (Statistics stats : cricketService.getAllStats()) {
						stats.setStats_type(cricketService.getStatsType(9));
						if (stats.getStatsTypeId() == 9) {
							if (stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
//								stats = CricketFunctions.updateH2h(stats, head_to_head, match);
//								stats = CricketFunctions.updateMatchData(stats, match);
//								stats = CricketFunctions.updateTournamentDataWithStatsPunjab(stats, tournament_matches, match);
//								stats = CricketFunctions.updateStatisticsWithMatchDataPunjab(stats, match);
								switch (whatToProcess.toUpperCase()) {
								case "POPULATE-FF-THISSERIES":
									populateFFThisSeriesBat(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													tournament_matches, cricketService, match, null),
											match, broadcaster,stats, config);
									break;

								case "POPULATE-FF-THISSERIES_BALL":
									populateFFThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
											Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
											CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA", false,
													tournament_matches, cricketService, match, past_tournament_stats),
											match, broadcaster,stats, config);
									break;
								}
								
							}
						}
					}
				} else if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("PT20SEASON1")) {
					for (Statistics stats : cricketService.getAllStats()) {
						stats.setStats_type(cricketService.getStatsType(9));
						if (stats.getStatsTypeId() == 9) {
							if (stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
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
						}
					}
				} else if (valueToProcess.split(",")[2].toUpperCase().equalsIgnoreCase("THISSERIES")) {
					Statistics stats = new Statistics();
					switch (whatToProcess.toUpperCase()) {
					case "POPULATE-FF-THISSERIES":
						populateFFThisSeriesBat(print_writer, valueToProcess.split(",")[0],
								Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
								CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA", false,
										tournament_matches, cricketService, match, past_tournament_stats),
								match, broadcaster,stats, config);
						break;

					case "POPULATE-FF-THISSERIES_BALL":
						populateFFThisSeriesBowl(print_writer, valueToProcess.split(",")[0],
								Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2],
								CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA", false,
										tournament_matches, cricketService, match, past_tournament_stats),
								match, broadcaster,stats, config);
						break;
					}
				}
				break;
			case "POPULATE-L3-PLAYERPROFILE":
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayerID().intValue()== Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
						stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);

						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populateLTPlayerProfile(print_writer,valueToProcess.split(",")[0],
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
						}
					}
				}
				break;
			case "POPULATE-L3-PLAYERPROFILEBAT":
				
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayerID().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						if(valueToProcess.split(",")[2].equalsIgnoreCase("PUNS1")) {
							stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populateLTPlayerProfileBat(print_writer,valueToProcess.split(",")[0],
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
							}
						}else {
							stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
							stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populateLTPlayerProfileBat(print_writer,valueToProcess.split(",")[0],
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
							}
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYERPROFILE":
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayerID() == Integer.valueOf(valueToProcess.split(",")[1])) {
						if(valueToProcess.split(",")[2].equalsIgnoreCase("PUNS1")) {
							stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],
										stats,cricketService.getAllPlayer(),match, broadcaster, config);
							}
						}else {
							stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
							stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],
										stats,cricketService.getAllPlayer(),match, broadcaster, config);
							}
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYERPROFILEBALL":
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayerID().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						if(valueToProcess.split(",")[2].equalsIgnoreCase("PUNS1")) {
							stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populatePlayerProfileBall(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],
										stats,cricketService.getAllPlayer(),match, broadcaster, config);
							}
						}else {
							stats.setStats_type(cricketService.getStatsType(stats.getStatsTypeId()));
							stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
							stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
							if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
								populatePlayerProfileBall(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
										valueToProcess.split(",")[2],valueToProcess.split(",")[3],
										stats,cricketService.getAllPlayer(),match, broadcaster, config);
							}
						}
						
					}
				}
				TimeUnit.SECONDS.sleep(2);
				break;
			case "POPULATE-FF-PLAYINGXI":
				populatePlayingXI(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						match, broadcaster, config);
				break;
			case "POPULATE-FF-DOUBLETEAMS":
				populateDoubleteams(print_writer,valueToProcess, match, broadcaster);
				break;
			case "POPULATE-FF-LANDMARK":
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
				
				populatePointsTable(print_writer, valueToProcess.split(",")[0],league_table.getLeagueTeams(),broadcaster,match, cricketService.getVariousTexts());
				
				break;
			case "POPULATE-FF-BATGRIFF": case "POPULATE-FF-BALLGRIFF":
				populateLtGriff(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]),cricketService, tournament_matches,match,broadcaster);
				break;
			case "POPULATE-LTPOINTS_TABLE":
				LeagueTable league_table1 = null;
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
					league_table1 = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
							new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
				}
				populateLtPointsTable(print_writer, valueToProcess.split(",")[0], league_table1.getLeagueTeams(),cricketService.getTeams(),match,broadcaster);
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
							System.out.println("HELLO");
							
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
				
				
				populatePreviousSummary(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),
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
				
			case "POPULATE-INFOBAR-PROMPT":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						for(InfobarStats ibs : cricketService.getInfobarStats())
	                          if(ibs.getOrder() == Integer.valueOf(valueToProcess)) {
	                        	  if(infobar.getLast_top_section().equalsIgnoreCase(CricketUtil.TIMELINE)) {
	                        		  processAnimation(print_writer, "ALL_SECTION$Section6$Section6BaseOut", "START", broadcaster);
	                        	  }
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
			case "POPULATE-POWERPLAY":
				if(power_play_on_screen.equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayOut START \0");
					power_play_on_screen = "";
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START \0");
					power_play_on_screen = "YES";
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
			case "POPULATE-INFOBAR_IDENT_DATA":
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					if(infobar.getIdent_section() != null) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoOut START \0");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setIdent_section(valueToProcess);
						populateInfobarIdent(infobar, false, valueToProcess,print_writer, match, broadcaster);
						TimeUnit.MILLISECONDS.sleep(500);
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoIn START \0");
					}
				}
				break;

			case "POPULATE-INFOBAR-BOTTOMRIGHT":
				System.out.println("LAST : "+infobar.getLast_bottom_right_section());
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
					case "BOWLINGEND": case "LASTOVERRUNS":
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
					case "BOWLINGEND": case "LASTOVERRUNS":
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
					case "BOWLINGEND": case "LASTOVERRUNS":
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
					TimeUnit.MILLISECONDS.sleep(500);
					switch(infobar.getLast_bottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
						break;
					case "BOWLINGEND": case "LASTOVERRUNS":
						processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
						break;
					}
					//TimeUnit.MILLISECONDS.sleep(300);

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
						processAnimation(print_writer, "Section6$TimelineOut", "START", broadcaster);
						TimeUnit.MILLISECONDS.sleep(200);
						processAnimation(print_writer, "ALL_SECTION$Section6$Section6BaseOut", "START", broadcaster);
						TimeUnit.MILLISECONDS.sleep(200);
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseIn", "START", broadcaster);
						break;
					case "STATISTICS":
						processAnimation(print_writer, "Section2$FreeTextSmallOut", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setTop_section(valueToProcess);
					if(valueToProcess.equalsIgnoreCase(CricketUtil.TIMELINE)){
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseOut", "START", broadcaster);
						TimeUnit.MILLISECONDS.sleep(200);
						processAnimation(print_writer, "ALL_SECTION$Section6$Section6BaseIn", "START", broadcaster);
					}
					populateVizInfobarTop(infobar, false, print_writer, match, broadcaster);
				}else {
					infobar.setTop_section(valueToProcess);
					if(valueToProcess.equalsIgnoreCase(CricketUtil.TIMELINE)){
						processAnimation(print_writer, "ALL_SECTION$Section6$Section6BaseIn", "START", broadcaster);
					}else {
						processAnimation(print_writer, "ALL_SECTION$Section2$Section2BaseIn", "START", broadcaster);
					}
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
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
			break;
		case "BOWLINGCARD":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorIn START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "PARTNERSHIP":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartIn START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "MATCHSUMMARY": case "PREVIOUS_SUMMARY":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "POINTSTABLE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
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
		case "LEADERBOARD":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "POINTER":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "LTPOINTSTABLE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
			break;
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "HOWOUT_QUICK": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "MATCHID":
		case "L3PLAYERPROFILE": case "LTPLAYERPROFILEBAT": case "FFPLAYERPROFILE":	case "TEAMLINEUP": case "DOUBLETEAMS": case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH":
		case "BOWLER_THIS_MATCH": case "BOWLER_STYLE": case "BATSMAN_STYLE": case "MANHATTAN": case "HOWOUT_WITHOUT": case "MOSTRUNS": case "MOSTWICKETS":
		case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM":  case "MATCH_PROMO": case "L3MATCH_PROMO": case "TEAMS_LOGO": case "TIEID-DOUBLE":
		case "SCHEDULE": case "MINI_BATTINGCARD": case "MINI_BOWLINGCARD": case "THISSERIES": case "FF-THISSERIES": case "FF-THISSERIES_BALL": case "FF_STATS": case "PLAYERPROFILEBALL":
		case "PLAYERPROFILEBAT": case "BUG_POWERPLAY": case "BUG_HIGHLIGHT": case "BUG_PARTNERSHIP": case "MULTI_PARTNERSHIP": case "BUG-TOSS": case "THISSERIES-BALL": case "BATGRIFF": case "BOWLGRIFF":
		case "PLAYOFFS": case "FIX_AND_RESULT":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
			break;
		/*case "SCOREBUG":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*MainIn START \0");
			
			break;*/
		}	
	}
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException {
		switch(whichGraphic) {
		case "BATBALLSUMMARY_SCORECARD": case "BATBALLSUMMARY_SCORECARD_PERFORMER":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut CONTINUE \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
			bcf.setLast_type("");
			break;
		case "BATBALLSUMMARY_BOWLINGCARD": case "BATBALLSUMMARY_BOWLINGCARD_PERFORMER":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut CONTINUE \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SponsorOut START \0");
			bocf.setLast_type("");
			break;
		case "BATBALLSUMMARY_MATCHSUMMARY": case "PREVIOUS_SUMMARY":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut CONTINUE \0");
			break;
		case "PARTNERSHIP":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PartOut START \0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
			break;
		case "POINTSTABLE": 
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut CONTINUE \0");
			break;
		case "ANIMATE-OUT-INFOBAR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainOut START \0");
			break;
		case "ANIMATE-OUT-IDENT":
			//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainIn SHOW 0.0 \0");
			//print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentInfoOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "POINTER":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;	
		case "DIRECTOR":
			switch(which_director_on_screen) {
			case "FREEHIT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitOut START \0");
				which_director_on_screen = "";
				break;
			case "FOURS":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FourOut START \0");
				which_director_on_screen = "";
				break;
			case "SIXES":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SixOut START \0");
				which_director_on_screen = "";
				break;
			case "WICKETS":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*WicketOut START \0");
				which_director_on_screen = "";
				break;	
			}
			
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
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_Out START \0");
			break;
		case "LTPOINTSTABLE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
			break;	
		case "PLAYOFFS":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			TimeUnit.MILLISECONDS.sleep(1000);
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out SHOW 0.0 \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In SHOW 0.0 \0");
			break;
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "HOWOUT_QUICK": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "MATCHID":
		case "L3PLAYERPROFILE": case "LTPLAYERPROFILEBAT": case "FFPLAYERPROFILE":	case "TEAMLINEUP": case "DOUBLETEAMS": case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH":
		case "BOWLER_THIS_MATCH": case "BOWLER_STYLE": case "BATSMAN_STYLE": case "MANHATTAN": case "HOWOUT_WITHOUT": case "MOSTRUNS": case "MOSTWICKETS":
		case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "WORM": case "MATCH_PROMO": case "L3MATCH_PROMO": case "TEAMS_LOGO": case "TIEID-DOUBLE":
		case "SCHEDULE": case "MINI_BATTINGCARD": case "MINI_BOWLINGCARD": case "THISSERIES": case "FF-THISSERIES": case "FF-THISSERIES_BALL": case "LEADERBOARD": case "FF_STATS":
		case "PLAYERPROFILEBALL": case "PLAYERPROFILEBAT": case "BUG_POWERPLAY": case "BUG_HIGHLIGHT": case "BUG_PARTNERSHIP": case "MULTI_PARTNERSHIP": case "BUG-TOSS": case "THISSERIES-BALL":
		case "BATGRIFF": case "BOWLGRIFF": case "FIX_AND_RESULT":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out CONTINUE \0");
			break;
		}
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_VIZ": case "PUNJAB_T20":
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
				if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "LT_OUT");
					which_graphic_on_screen = "SCOREBUG";
				}
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

				
				//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp*ACTIVE SET 0 \0");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp$FirstName"
//						+ "*GEOM*TEXT SET " + " " + "\0");
						if(!which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_SCORECARD")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 0 \0");
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$SubHeader*GEOM*TEXT SET " 
								+ match.getSetup().getMatchIdent() + " - " + match.getSetup().getTournament()	+ "\0");
						
						if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path + 
									match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$TeamNameGrp$FirstName"
									+ "*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$TeamNameGrp$LastName"
									+ "*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
							//TimeUnit.MILLISECONDS.sleep(2);
						} else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path + 
									match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
									+ match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
									+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
							//TimeUnit.MILLISECONDS.sleep(2);
						}
						
						Collections.sort(inn.getBattingCard());
						for (BattingCard bc : inn.getBattingCard()) {
							row_id = row_id + 1;
							
							if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
							}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatWicketPlayerImpact" + row_id + " SET " + "0" + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatBallPlayerImpact" + row_id + " SET " + "0" + "\0");
							
							switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.STILL_TO_BAT:
							if(bc.getHowOut() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo$LeftPlayerName*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo$LeftPlayerName$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								//TimeUnit.MILLISECONDS.sleep(2);
								
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
										row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "retired hurt" + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								//TimeUnit.MILLISECONDS.sleep(2);
								
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
										row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "absent hurt" + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + "$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
										"$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								
								//TimeUnit.MILLISECONDS.sleep(2);
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
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
								//TimeUnit.MILLISECONDS.sleep(2);
								
							}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
								//TimeUnit.MILLISECONDS.sleep(2);
								
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
									+ "$BatRightDataGrp$BatDetailRow" + row_id + "*ACTIVE SET " + "1" + " \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
									+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*ACTIVE SET " + 
									"1" + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
									"$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll"
									+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								System.out.println(bc.getHowOut());
								if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "retired out" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
												+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
												bc.getHowOutPartOne() + " (sub - " + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
												+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
											CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
												bc.getHowOutPartOne() + " ( " + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
												+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");										
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
												+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
												+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									}
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne().replace("SUB", "Sub") + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
											CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
												row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
												row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									}
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
											bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + 
											bc.getHowOutPartTwo() + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED) || bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.BOWLED)){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
										+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase("timed_out")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "timed out" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");	
								}else {
	
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + 
											bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll" 
											+ "$BatRightDataGrp$BatDetailRow" + row_id + "$RowAnimation$BatOmo$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + 
											bc.getHowOutPartTwo() + "\0");
								}
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							//TimeUnit.MILLISECONDS.sleep(2);
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							//TimeUnit.MILLISECONDS.sleep(2);
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							//TimeUnit.MILLISECONDS.sleep(2);
						}
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BottomInfoGrp$BottomInfoAll$ExtrasGrp$"
							+ "ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					//TimeUnit.MILLISECONDS.sleep(2);
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BottomInfoGrp$BottomInfoAll$OversGrp$"
							+ "OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					//TimeUnit.MILLISECONDS.sleep(2);
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BottomInfoGrp$BottomInfoAll$"
							+ "TotalScore*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				}
			}
				
				
				switch(Type.toUpperCase()) {
				case "PERFORMER":
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");
	
					for(Inning inn : match.getMatch().getInning()) {
						if (inn.getInningNumber() == whichInning) {
							for (BattingCard bc : inn.getBattingCard()) {
								if(player == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtraPlayerFirstName1" + " SET " + bc.getPlayer().getFull_name() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtraPlayerLastName1" + " SET " + " " + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatValue" + " SET " + bc.getStrikeRate() + "\0");
									TimeUnit.MILLISECONDS.sleep(2);
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp$PlayerVerticalNameGrp*ACTIVE SET 0 \0");
	
									for (Player hs : match.getSetup().getHomeSquad()) {
										if(hs.getPlayerId() == player) {
											if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + photo_path + 
														inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
											}else {
												if(!new File("\\\\"+config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + 
														inn.getBatting_team().getTeamName4() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
													this.status = CricketUtil.UNSUCCESSFUL;
												}
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + 
													"\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
														hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}
											TimeUnit.MILLISECONDS.sleep(2);
										}
									}
									
									for (Player as : match.getSetup().getAwaySquad()) {
										if(as.getPlayerId() == player) {
											if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + photo_path + 
														inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
											}else {
												if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + 
														inn.getBatting_team().getTeamName4() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
													this.status = CricketUtil.UNSUCCESSFUL;
												}
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + 
													"\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
														as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}
											TimeUnit.MILLISECONDS.sleep(2);
										}
									}
								}
							}
						}
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatRightDataAll*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$"
							+ "BatPerformerGrp*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 0 \0");
					
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BattingCardIn$BatOffsetIn 1.363 "
							+ "BattingRightCardIn 1.180 BattingRightCardIn$BatRightOffsetIn 1.180 BatPerformerIn 0.641 \0");
					
					if(which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_SCORECARD")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 1 \0");
					}
					break;
				case "PARTNERSHIP":
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPartnershipGrp*ACTIVE SET 1 \0");
					
					for(Inning inn : match.getMatch().getInning()) {
						//if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							if(inn.getInningNumber() == whichInning) {
								String Left_Batsman ="",Right_Batsman="";
								
								Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getTicker_name();
								Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getTicker_name();
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + photo_path + 
											inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + ".png" + "\0");
								}else {
									if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + inn.getBatting_team().getTeamName4() 
											+ "\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage1" + " SET " + "\\\\\\\\" + 
										config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + 
											inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage2" + " SET " + photo_path + 
											inn.getBatting_team().getTeamName4().toUpperCase() + "\\\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + ".png" + "\0");
								}else {
									if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + inn.getBatting_team().getTeamName4() 
											+ "\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPlayerImage2" + " SET " + "\\\\\\\\" + 
										config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
											inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPartnershipRuns" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
								//TimeUnit.MILLISECONDS.sleep(2);
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPartnershipBalls" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
								//TimeUnit.MILLISECONDS.sleep(2);
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine1" + " SET " + Left_Batsman + " / " + Right_Batsman + "\0");
								//TimeUnit.MILLISECONDS.sleep(2);
								
								if(inn.getTotalWickets() == 0) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
									//TimeUnit.MILLISECONDS.sleep(2);
								}else if(inn.getTotalWickets() == 1) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
									//TimeUnit.MILLISECONDS.sleep(2);
								}else if(inn.getTotalWickets() == 2) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
									//TimeUnit.MILLISECONDS.sleep(2);
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
									//TimeUnit.MILLISECONDS.sleep(2);
								}
							}
						//}
					}
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 "
							+ "BattingRightCardIn 1.180 BattingRightCardIn$BatRightOffsetIn 1.180 BatPartnershipIn 1.000 \0");
					break;
				}
			
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$FirstName"
					+ "*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp*ACTIVE SET 0 \0");
			if(!which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_BOWLINGCARD")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType*FUNCTION*Omo*vis_con SET 0 \0");
			}
			//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$business6*TEXTURE*IMAGE SET "+ "IMAGE*/Default/Nepal_T20/Logos/1XBAT" +" \0");
			//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType*FUNCTION*Omo*vis_con SET 0 \0");

			int row_id = 0, omo_num = 0,len=0;
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$"
							+ "FirstName*GEOM*TEXT SET " + " " + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament() + "\0");
					//TimeUnit.MILLISECONDS.sleep(2);
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$FirstName"
								+ "*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$LastName"
								+ "*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
						//TimeUnit.MILLISECONDS.sleep(2);
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$FirstName"
								+ "*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$Header$TeamNameGrp$LastName"
								+ "*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
						//TimeUnit.MILLISECONDS.sleep(2);
					}
					
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
							len=len+1;
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallDataGrp*FUNCTION*Omo*vis_con SET " + 
									len +"\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll"
									+ "$BallRightDataGrp*FUNCTION*Omo*vis_con SET " + len +"\0");
						}
						
						
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
								CricketFunctions.GetTargetData(match).getRemaningBall()  == 0) {
							omo_num = 0;
							cont_name = "$Dehighlight";
						}else {
							if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
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
						}
						if(boc.getPlayerId()==player && inn.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
							omo_num = 1;
							cont_name = "$Highlight";
						}
						
							row_id = row_id + 1;
							
							if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "1" + "\0");
							}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallDataGrp$BallRow" + 
									row_id + "$RowAnimation$BallOmo*FUNCTION*Omo*vis_con SET " + omo_num +"\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll"
									+ "$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo*FUNCTION*Omo*vis_con SET " + omo_num +"\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallDataGrp$BallRow" + 
									row_id + "$RowAnimation$BallOmo" + cont_name +"$BallPlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallDataGrp$BallRow" + 
									row_id + "$RowAnimation$BallOmo" + cont_name +"$ScoreGrp$Figure*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
									"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallOverValue*GEOM*TEXT SET " + 
									CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							
							if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10) || 
									match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
										"$BallDetailRow0" + "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Dots" + "\0");
								
								if(boc.getDots() < 0) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
											"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
											"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getDots() + "\0");
								}
								
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
										"$BallDetailRow0" + "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Maidens" + "\0");
								
								if(boc.getMaidens() < 0) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
											"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
											"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getMaidens() + "\0");
								}
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
									"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallExtraValue*GEOM*TEXT SET " + 
									(boc.getNoBalls() + boc.getWides()) + "\0");
							
							if(boc.getEconomyRate() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
									"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + 
										boc.getEconomyRate() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$BallRightDataAll" + 
										"$BallDetailRow" + row_id + "$RowAnimation$BallDetailOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + "-" + "\0");
							}
								
					}
						
					if(inn.getBowlingCard().size()<=7) {
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$FowGrp*ACTIVE SET 0 \0");
							//TimeUnit.MILLISECONDS.sleep(2);
						}
						else{
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$FowGrp*ACTIVE SET 1 \0");
							//TimeUnit.MILLISECONDS.sleep(2);
							for(FallOfWicket fow : inn.getFallsOfWickets()) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								//TimeUnit.MILLISECONDS.sleep(2);
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								//TimeUnit.MILLISECONDS.sleep(2);
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$"+ fow.getFowNumber() +"*GEOM*TEXT SET "+ fow.getFowRuns() + " \0");
								//TimeUnit.MILLISECONDS.sleep(2);
							
							}
							for(int fow_id=inn.getFallsOfWickets().size()+1;fow_id<=10;fow_id++) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow_id + "*ACTIVE SET 0 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow_id + "*ACTIVE SET 0 \0");
								//TimeUnit.MILLISECONDS.sleep(2);
							}		
						}
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + " SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					//TimeUnit.MILLISECONDS.sleep(2);
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtrasValue" + " SET " + inn.getTotalExtras() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallTotalScore" + " SET " + 
							CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				}
			}
			
			switch(Type.toUpperCase()) {
			case "PERFORMER":
				//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");

				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(player == boc.getPlayerId()) {
								if(boc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtraPlayerLastName1" + " SET " + boc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtraPlayerLastName1" + " SET " + "" + "\0");
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtraPlayerFirstName1" + " SET " + boc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallStatHead" + " SET " + "FIGURES" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallStatValue" + " SET " + boc.getWickets() + "/" + boc.getRuns() + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
								//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp$PlayerVerticalNameGrp*ACTIVE SET 0 \0");

								for (Player hs : match.getSetup().getHomeSquad()) {
									if(hs.getPlayerId() == player) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallPlayerImage" + " SET " + photo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$"
													+ "BallExtraData$BallPerformerGrp$PlayerImageGrp$PlayerImage1A*TEXTURE*IMAGE SET " + photo_path +
													inn.getBowling_team().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + " \0");
										}else {
											if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallPlayerImage" + " SET " + 
												"\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + inn.getBowling_team().getTeamName4() + "\\\\" + 
													hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										TimeUnit.MILLISECONDS.sleep(2);
									}
								}
								
								for (Player as : match.getSetup().getAwaySquad()) {
									if(as.getPlayerId() == player) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallPlayerImage" + " SET " + photo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format1$BallData$"
													+ "BallExtraData$BallPerformerGrp$PlayerImageGrp$PlayerImage1A*TEXTURE*IMAGE SET " + photo_path +
													inn.getBowling_team().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + " \0");
										}else {
											if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallPlayerImage" + " SET " + 
												"\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + inn.getBowling_team().getTeamName4() + "\\\\" + 
													as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										TimeUnit.MILLISECONDS.sleep(2);
									}
								}
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.363 BowlingCardIn$BallOffsetIn 1.363 BallPerformerIn 0.641 \0");
				TimeUnit.MILLISECONDS.sleep(500);	
				break;
			}
		}
	}
	public void populateScorecard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,CricketService cricketService, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, omo_num = 0,gridIfImpactIn = 0, count = 0;
			String cont_name= "";
			ImpactData[] impactArray = new ImpactData[2];
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpactLegend" + " SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BottomInfoGrp$Imapct*FUNCTION*Omo*vis_con SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp"
							+ "$FirstName*GEOM*TEXT SET " + " " + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType*FUNCTION*Omo*vis_con SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp"
							+ "$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp"
								+ "$LastName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$Header$TeamNameGrp"
								+ "$LastName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					for (int i = match.getEventFile().getEvents().size() - 1; i >= 0; i--) {
						
						if(match.getEventFile().getEvents().get(i).getSubstitutionMade() != null && 
								match.getEventFile().getEvents().get(i).getSubstitutionMade().equalsIgnoreCase(CricketUtil.IMPACT)){
							if(count>=2) {
								break;
							}
							ImpactData impactPlayer = new ImpactData();
							if(match.getEventFile().getEvents().get(i).getEventBowlerNo() != 0) {
								impactPlayer.setInBowlPlayerId(match.getEventFile().getEvents().get(i).getEventBowlerNo());
							}
							if(match.getEventFile().getEvents().get(i).getEventBatterNo() != 0) {
								impactPlayer.setInBatPlayerId(match.getEventFile().getEvents().get(i).getEventBatterNo());
							}
							impactPlayer.setOutPlayerId(match.getEventFile().getEvents().get(i).getEventConcussionReplacePlayerId());
							for(Player plyr : cricketService.getAllPlayer()) {
								if(impactPlayer.getOutPlayerId() != 0) {
									if(plyr.getPlayerId() == impactPlayer.getOutPlayerId()) {
										impactPlayer.setTeamId(plyr.getTeamId());
									}
								}
							}
							impactArray[count] = impactPlayer;
							count++;
						}
					}
					
					Collections.sort(inn.getBattingCard());
					for (BattingCard bc : inn.getBattingCard()) {
						row_id = row_id + 1;
						if(impactArray != null) {
							if(impactArray[0] != null) {
								if(inn.getBatting_team().getTeamId() == impactArray[0].getTeamId()) {
									if(bc.getPlayerId() == impactArray[0].getOutPlayerId()) {
										if(!bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
											
										}else {
											row_id--;
											continue;
										}
									}
									
								}
							}
							if(impactArray[1] != null) {
								if(inn.getBatting_team().getTeamId() == impactArray[1].getTeamId()) {
									if(bc.getPlayerId() == impactArray[1].getOutPlayerId()) {
										if(!bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
											
										}else {
											row_id--;
											continue;
										}
									}
								}
							}
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_id + "\0");
						if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
						}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "1" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpact" + row_id + " SET " + "0" + "\0");
						}
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
						if(bc.getHowOut() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$LeftPlayerName*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$LeftPlayerName$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" +
									row_id + "$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "retired hurt" + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "absent hurt" + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + "$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
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
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
//						}
//						else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
//						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
					
						if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*ACTIVE SET 1 \0");
							if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "retired out" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + 
													" "+ " (" + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
											
										}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + 
													" "+ " (" + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
											
										
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + 
													" "+ "(Sub-" + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
													row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
											}
										
										}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
									}
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
														row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne().replace("(SUB)", " ") + "\0");
													print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
														row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
												
											}else if (CricketFunctions.checkConcussedPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
													CricketFunctions.checkConcussedPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
														row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne().replace("(SUB)", " ") + "\0");
													print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
														row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
												
											}else {
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
														row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne().replace("SUB", "Sub") + "\0");
													print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
														row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
													}
											
											}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
												row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
												row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
											}
										}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
											row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
									}
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + "" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase("timed_out")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + "timed out" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");	
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" +
										row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
								}
						}
						else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
								row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
								row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$WicketPlayerName*GEOM*TEXT SET " + bc.getStatus() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow" + 
								row_id + "$RowAnimation$BatOmo" + cont_name + "$HowOutGrp$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
						}
					}
				}
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll"
						+ "$ExtrasGrp$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll"
						+ "$OversGrp$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BottomInfoGrp$BottomInfoAll"
						+ "$TotalScore*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
			}
		}
			
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BattingCardIn 1.363 BattingCardIn$BatOffsetIn 1.363 \0");
		
	}
}
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene,boolean is_this_updating, int whichInning,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateBowlingcard -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBowlingcard -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpactLegend" + " SET " + "1" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
					+ " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType*FUNCTION*Omo*vis_con SET 1 \0");
			int row_id = 0, omo_num = 0,len=0;
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
							+ match.getSetup().getMatchIdent() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$SubHeader*GEOM*TEXT SET " 
							+ match.getSetup().getTournament().toUpperCase() + "\0");
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						
						if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
							len=len+1;
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
									+ "$BallDataGrp*FUNCTION*Omo*vis_con SET " + len + "\0");
						}
						
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
								CricketFunctions.GetTargetData(match).getRemaningBall()  == 0) {
							omo_num = 0;
							cont_name = "$Dehighlight";
						}else {
							if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
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
						}
						
						
						row_id = row_id + 1;
						
						if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "1" + "\0");
						}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "1" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallImpact" + row_id + " SET " + "0" + "\0");
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
								"$RowAnimation$BallOmo*FUNCTION*Omo*vis_con SET " + omo_num +"\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
								"$RowAnimation$BallOmo" + cont_name +"$BallPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
								"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallOverValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");

						if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10) || 
								match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow0"
									+ "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Dots" + "\0");
							if(boc.getDots() < 0) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getDots() + "\0");
							}
							
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow0"
									+ "$RowAnimation$BallDetailData$BallMaidensHead*GEOM*TEXT SET " + "Maidens" + "\0");
							
							if(boc.getMaidens() < 0) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + "0" + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
										"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallMaidensValue*GEOM*TEXT SET " + boc.getMaidens() + "\0");
							}
							
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + "$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallExtraValue*GEOM*TEXT SET " + (boc.getNoBalls() + boc.getWides()) + "\0");
						if(boc.getEconomyRate() != null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
									"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + 
									"$RowAnimation$BallOmo" + cont_name +"$BallDetailData$BallEconomyValue*GEOM*TEXT SET " + "-" + "\0");
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$BallDataGrp$BallRow" + row_id + "$RowAnimation$BallOmo" + cont_name +"$ScoreGrp$Figure*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
						
					}
					if(inn.getBowlingCard().size()<=7) {
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$FowGrp*ACTIVE SET 0 \0");
						}
						else{
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$FowGrp*ACTIVE SET 1 \0");
							for(FallOfWicket fow : inn.getFallsOfWickets()) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow.getFowNumber() + "*ACTIVE SET 1 \0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$"+ fow.getFowNumber() +"*GEOM*TEXT SET "+ fow.getFowRuns() + " \0");
							
							}
							for(int fow_id=inn.getFallsOfWickets().size()+1;fow_id<=10;fow_id++) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData$"
										+ "FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp$" + fow_id + "*ACTIVE SET 0 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BowlingCardAll$BowlingCardType$Format2$BallData"
										+ "$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp$" + fow_id + "*ACTIVE SET 0 \0");
							}		
						}
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + " SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtrasValue" + " SET " + inn.getTotalExtras() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallTotalScore" + " SET " + 
							CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 \0");
			TimeUnit.SECONDS.sleep(2);
		}
	}
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,List<VariousText> vt, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateMatchsummary -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateMatchsummary -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, max_Strap = 0, total_inn = 0,bat_impact_count=0,ball_impact_count=0;
			String teamname = "";//,teamname_logo=""; 
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
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 0 \0");
				}
			}*/
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpactLegend" + " SET " + "1" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$Bands$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			
			for(int i=1; i<=8; i++) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBatImpact" + i + " SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBallImpact" + i + " SET " + "0" + "\0");
			}
			
			for(int i = 1; i <= whichInning ; i++) {

				if(i == 1) {
					row_id = 0;
					max_Strap = 5;
					
					bat_impact_count = 0;
					ball_impact_count = 0;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 0 \0");
					
				} else {
					row_id = 5;
					max_Strap = 10;
					
					bat_impact_count = 4;
					ball_impact_count = 4;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 1 \0");
				}
				row_id = row_id + 1;
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {
					teamname = match.getSetup().getHomeTeam().getTeamName1();
				} else {
					teamname = match.getSetup().getAwayTeam().getTeamName1();
				}
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamFirstName*ACTIVE SET " + "0" + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamLastName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SumTeamRuns*GEOM*TEXT SET " + CricketFunctions.getTeamScore(match.getMatch().getInning().get(i-1), 
								slashOrDash, false) + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$OversGrp$SumTeamOvers*GEOM*TEXT SET " + CricketFunctions.OverBalls(match.getMatch().getInning().get(i-1).getTotalOvers(),
								match.getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
				
				if(match.getMatch().getInning().get(i-1).getBattingCard() != null) {
					Collections.sort(match.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					
					for(BattingCard bc : match.getMatch().getInning().get(i-1).getBattingCard()) {
						if (bc.getRuns() > 0) {
							if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								row_id = row_id + 1;
								bat_impact_count = bat_impact_count + 1;
								
								System.out.println("NAME - " + bc.getPlayer().getTicker_name() + " IMPACT NUMBER : " + bat_impact_count);
								
								if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), i, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBatImpact" + bat_impact_count + " SET " + "1" + "\0");
								}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), i, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBatImpact" + bat_impact_count + " SET " + "1" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBatImpact" + bat_impact_count + " SET " + "0" + "\0");
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + bc.getRuns() + "\0");
								
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
											"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
								} else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
											"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
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
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + j + "$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
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
							ball_impact_count = ball_impact_count + 1;
							
							if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), i, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBallImpact" + ball_impact_count + " SET " + "1" + "\0");
							}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), i, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBallImpact" + ball_impact_count + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBallImpact" + ball_impact_count + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
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
				
				for(int j = row_id + 1; j <= max_Strap; j++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + j + 
							"$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
				}
			}
			
			for (VariousText vartext : vt) {
				if (vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ vartext.getVariousText() + "\0");
				} else if (vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER")&& vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
					if(match.getMatch().getMatchResult() != null) {
						if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
						}
						else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
									+ "MATCH TIED" + "\0");
						}
						else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								 + match.getMatch().getMatchStatus().toUpperCase() + "\0");
						}
						else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
									+ "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
						}
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
								+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
						
						if(match.getSetup().getTargetType() != null) {
							if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
										+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
								
							}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
										+ CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
							}
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 \0");

				
		
		}
	}
	public void populatePartnership(PrintWriter print_writer, String viz_scene,int whichInning,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populatePartnership -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populatePartnership -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, omo_num = 0,Top_Score = 50,gridSizeIfImpactIn = 0, impactInPlayerId = 0, impactOutPlayerId = 0;
			float Mult = 322, ScaleFac1 = 0, ScaleFac2 = 0;
			String cont_name= "",Left_Batsman = "",Right_Batsman="";
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpactLegend" + " SET " + "1" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$Header$SubHeader*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {

				//if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPartTeamLogo" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPartTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
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
					
					for (int i = match.getEventFile().getEvents().size() - 1; i >= 0; i--) {
						if(match.getEventFile().getEvents().get(i).getEventInningNumber() == whichInning) {
							if(match.getEventFile().getEvents().get(i).getSubstitutionMade()!=null && match.getEventFile().getEvents().get(i).getSubstitutionMade().equalsIgnoreCase(CricketUtil.IMPACT)){
								impactInPlayerId = match.getEventFile().getEvents().get(i).getEventBatterNo();
								impactOutPlayerId = match.getEventFile().getEvents().get(i).getEventConcussionReplacePlayerId();
							}
						}
					}

					for (Partnership ps : inn.getPartnerships()) {
						
						row_id = row_id + 1;
						Left_Batsman ="" ; Right_Batsman="";
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == impactOutPlayerId) {
								if(!bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									
								}else {
									continue;
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
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + inn.getPartnerships().size() + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + inn.getBattingCard().size() + "\0");
						}

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo" + "$" + cont_name + "$LeftPlayerName*GEOM*TEXT SET " + Left_Batsman + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo" + "$" + cont_name + "$RightPlayerName*GEOM*TEXT SET " + Right_Batsman + "\0");
						
						if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, ps.getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "1" + "\0");
						}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, ps.getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "1" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vLeftImpact" + row_id + " SET " + "0" + "\0");
						}
						
						if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, ps.getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRightImpact" + row_id + " SET " + "1" + "\0");
						}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, ps.getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRightImpact" + row_id + " SET " + "1" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRightImpact" + row_id + " SET " + "0" + "\0");
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo" + "$" + cont_name + "$Bar*FUNCTION*BarValues*Bar_Value__1 SET " + ScaleFac1 + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo" + "$" + cont_name + "$Bar*FUNCTION*BarValues*Bar_Value__2 SET " + ScaleFac2 + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo" + "$" + cont_name + "$ScoreGrp$PartnershipRun*GEOM*TEXT SET " + ps.getTotalRuns() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo" + "$" + cont_name + "$ScoreGrp$PartnershipBalls*GEOM*TEXT SET " + ps.getTotalBalls() + "\0");		
					}
					if(inn.getPartnerships().size() >= 10) {
						row_id = row_id + 1;
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + " \0");
						
					}
					else {
						for (BattingCard bc : inn.getBattingCard()) {
							if(inn.getTotalWickets() == 9) {
								if(bc.getPlayerId() == impactOutPlayerId) {
									if(!bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
										gridSizeIfImpactIn = 10;
									}else {
										gridSizeIfImpactIn = 10;
										continue;
									}
								}
							}
							if(row_id < inn.getBattingCard().size()) {
								if(row_id == inn.getPartnerships().size()) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "0" + " \0");
									if(match.getMatch().getInning().get(whichInning - 1).getTotalOvers() == match.getSetup().getMaxOvers() || match.getMatch().getInning().get(whichInning - 1).getTotalWickets() >= 10 ) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
									}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || CricketFunctions.getWicketsLeft(match,whichInning) <= 0) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "DID NOT BAT" +" \0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$DidNotBat$DidNotBatText*GEOM*TEXT SET " + "STILL TO BAT" +" \0");
									}
								}
								else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "1" + " \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo$LeftPlayerName$LeftPlayerNameText*GEOM*TEXT SET " + bc.getPlayer().getTicker_name()+" \0");
								}	
							}
							else {
								break;
							}
						}
						if(gridSizeIfImpactIn == 10) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + gridSizeIfImpactIn + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + row_id + "\0");
							
						}
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BottomInfo$BottomInfoGrp$BottomInfoAll$ExtrasGrp$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PartnershipAll$Data$BottomInfo$BottomInfoGrp$BottomInfoAll$noname$OversGrp$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartTotalScore" + " SET " + CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 PartIn 1.700 PartOffsetIn 1.830\0");
			
		}
	}
	public void populateTeamsLogo(PrintWriter print_writer,String viz_scene, List<Team> teams ,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		this.status = CricketUtil.SUCCESSFUL;
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
	    
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + "\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "TEAMS" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + "" + "\0");
		//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader2" + " SET " + "POOL B" + "\0");
	    
		for(int i=0; i<= teams.size()-1; i++) {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo" + (i+1) + " SET " + logo_path + teams.get(i).getTeamName4() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName0" + (i+1) + " SET " + teams.get(i).getTeamName1() + "\0");
		}
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.520  \0");
			
	}
	
	public void populateBugPowerPLay(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		switch (session_selected_broadcaster.toUpperCase()) {
		case "PUNJAB_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Powerplay's inning is null";
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "POWERPLAY" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				
				for (Inning inn : match.getMatch().getInning()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + 
								CricketFunctions.getPowerPlayScore(inn, whichInning, "-", match) + "\0");
					if (whichInning == 1) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + " " + 
								match.getMatch().getInning().get(0).getBatting_team().getTeamName3() + "\0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + " " + 
								match.getMatch().getInning().get(1).getBatting_team().getTeamName3() + "\0");
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");

				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateBugToss(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "PUNJAB_T20":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
				
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
							" WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
							" WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + "\0");
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
				
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "HIGHLIGHTS" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
					match.getMatch().getInning().get(whichInning-1).getBatting_team().getTeamName3() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
			
			
			if (match.getMatch().getInning().get(whichInning-1).getTotalWickets() >= 10) {
				Value = String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalRuns());
			} else {
				Value = String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalRuns()) + " - " + 
						String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalWickets());
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + Value + " (" +
					CricketFunctions.OverBalls(match.getMatch().getInning().get(whichInning-1).getTotalOvers(),
							match.getMatch().getInning().get(whichInning-1).getTotalBalls()) + ")" + "\0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 0.714 \0");
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
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + Left_Batsman + " & " + Right_Batsman + "\0");
					
					if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 1) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET  " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "st WICKET PARTNERSHIP" + "\0");
						
					}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 2) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET  " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "nd WICKET PARTNERSHIP" + "\0");
						
					}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 3) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET  " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "rd WICKET PARTNERSHIP" + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET  " + 
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "th WICKET PARTNERSHIP" + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + inn.getPartnerships().get(partnership - 1).getTotalRuns() + 
							" (" + inn.getPartnerships().get(partnership - 1).getTotalBalls() + ")" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.400 \0");
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
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "CURRENT" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "PARTNERSHIP" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
							Left_Batsman + " " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns()
							+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ")" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() 
							+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + 
							Right_Batsman + "  " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns()
							+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ")" + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.400 \0");
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
								
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + " " + "\0");

								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + "" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								}
								
								if (bc.getHowOutText().trim().equalsIgnoreCase("")){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$"
											+ "Info03*GEOM*TEXT SET " + " " + "\0");						
								}else {
									if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)){
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$"
													+ "Info03*GEOM*TEXT SET " + bc.getHowOutPartOne() + " "+ "Sub (" + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$"
													+ "Info03*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										}
									}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)){
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$"
													+ "Info03*GEOM*TEXT SET " + bc.getHowOutPartOne().replace("SUB", "Sub") + " " + bc.getHowOutPartTwo() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$"
													+ "Info03*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$"
												+ "Info03*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									}							
								}
								
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + 
											bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + 
											bc.getRuns() + "*" + " (" + bc.getBalls() + ")" + "\0");
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "" + "\0");

								/*if(bc.getStrikeRate() == null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "S/R : " + "-" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "S/R : " + bc.getStrikeRate() + "\0");
								}*/
								
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.400 \0");
				
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
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + " " + "\0");
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + bc.getRuns() +"* "+ "(" + bc.getBalls() + ")" + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info04*GEOM*TEXT SET " + bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
								}
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + "" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + "4s : " + bc.getFours() + " 6s : " + bc.getSixes() + "\0");

								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "S/R : " + bc.getStrikeRate() + "\0");
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.400 \0");
				
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
							if(boc.getPlayerId() == playerId) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info03*GEOM*TEXT SET " + 
										boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info04*GEOM*TEXT SET " + 
										CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								
//								if(boc.getPlayer().getSurname() != null) {
//									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + 
//											boc.getPlayer().getFirstname() + "\0");
//									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + 
//											boc.getPlayer().getSurname() + "\0");
//								}else {
//									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + "" + "\0");
//									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + 
//											boc.getPlayer().getFirstname() + "\0");
//								}
								

//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$group$Info05*GEOM*TEXT SET " + "" + "\0");
								
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.400 \0");
				
		}
	}
	public void populateBugsDB(PrintWriter print_writer,String viz_scene, Bugs bug ,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			System.out.println("HELLO");
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");

			if(bug.getText1() != null && bug.getText2() != null && bug.getText3() != null && bug.getText4() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText3() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bug.getText2()  + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + bug.getText4() + "\0");
			}else if(bug.getText1() != null && bug.getText2() != null && bug.getText3() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText3() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bug.getText2() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");

			}else if(bug.getText1() != null && bug.getText2() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + " " + bug.getText2() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");

			}else if(bug.getText1() != null ) {
				System.out.println("HEL");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.400 \0");
				
		}
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
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "" + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002" + " SET " + "ROAD TO FINALS" + "\0"); 
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$StatHeadGrp$StatValue1*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$StatHeadGrp$StatValue2*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$StatHeadGrp*ACTIVE SET 1 \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + "" + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + tm.getTeamName1() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + "RESULTS, "+match.getSetup().getTournament().toUpperCase() + "\0"); 
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "010" + " SET " + "3" + "\0");
			
			for(Fixture fix : fixture) {
				if(fix.getHometeamid() == teamId || fix.getAwayteamid() == teamId) {
					fixtureList.add(fix);
				}
			}
			
			for(int i=0; i<fixtureList.size(); i++) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "HighlightSelection" + " SET " + "0" + "\0");
				if(fixtureList.get(i).getHometeamid() == teamId) {
					size++;
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatHead*GEOM*TEXT SET " + "v "+ team.get(fixtureList.get(i).getAwayteamid()-1).getTeamName1() + "\0");
				}else if(fixtureList.get(i).getAwayteamid() == teamId) {
					size++;
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatHead*GEOM*TEXT SET " + "v "+ team.get(fixtureList.get(i).getHometeamid()-1).getTeamName1() + "\0");
				}
				
				if(fixtureList.get(i).getMargin() != null && !fixtureList.get(i).getMargin().isEmpty()) {
					if(fixtureList.get(i).getWinnerteam() != null && !fixtureList.get(i).getWinnerteam().isEmpty()) {
						if(fixtureList.get(i).getWinnerteam().equalsIgnoreCase(tm.getTeamName1())) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue1*GEOM*TEXT SET " + "WON BY" + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue2*GEOM*TEXT SET " + fixtureList.get(i).getMargin() + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue1*GEOM*TEXT SET " + "LOST BY" + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue2*GEOM*TEXT SET " + fixtureList.get(i).getMargin() + "\0");
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue1*GEOM*TEXT SET " + fixtureList.get(i).getMargin() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue2*GEOM*TEXT SET " + "" + "\0");
					}
				}else {
					
					if(fixtureList.get(i).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal_apl.getTime()))) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue1*GEOM*TEXT SET " + "TODAY" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue2*GEOM*TEXT SET " + "" + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue1*GEOM*TEXT SET " + CricketFunctions.ordinal(Integer.valueOf(fixtureList.get(i).getDate().split("-")[0]))
						+ " " + Month.of(Integer.valueOf(fixtureList.get(i).getDate().split("-")[1])) + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$3ColGrp$Row"+(i+1)+"$Dehighlight$StatValue2*GEOM*TEXT SET " + "" + "\0");
						
					}
				}
			}
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "009" + " SET " + (size-1) + "\0");
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn$DataIn 1.170 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	
	public void populatePlayoffs(PrintWriter print_writer, String viz_scene, List<Playoff> playoffs, List<Team> team,
			String broadcaster, MatchAllData match) throws InterruptedException {
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "
						+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + "TLogo" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "
						+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + "TLogo" + "\0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET "+ "PLAYOFFS" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET "+ match.getSetup().getTournament() + "\0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-Header" + " SET "+ "QUALIFIER 1" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-Header" + " SET "+ "ELIMINATOR" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-Header" + " SET "+ "QUALIFIER 2" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-Header" + " SET "+ "FINAL" + "\0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-RUNNER-Header"+ " SET " + "LOSER Q1" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-WINNER-Header"+ " SET " + "WINNER ELM" + "\0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamA-Alpha" + " SET "+ "100" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamB-Alpha" + " SET "+ "100" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA-Alpha" + " SET "+ "100" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB-Alpha" + " SET "+ "100" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Alpha" + " SET "+ "100" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Alpha" + " SET "+ "100" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Alpha" + " SET "+ "100" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Alpha" + " SET "+ "100" + "\0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamA" + " SET "+ playoffs.get(0).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamB" + " SET "+ playoffs.get(0).getTeam2().toUpperCase() + "\0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA" + " SET "+ playoffs.get(1).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB" + " SET "+ playoffs.get(1).getTeam2().toUpperCase() + "\0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA" + " SET "+ playoffs.get(2).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB" + " SET "+ playoffs.get(2).getTeam2().toUpperCase() + "\0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA" + " SET "+ playoffs.get(3).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB" + " SET "+ playoffs.get(3).getTeam2().toUpperCase() + "\0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
						+ "IMAGE*/Default/Punjab_Cup_2023/TeamColour/0" + " \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
						+ "IMAGE*/Default/Punjab_Cup_2023/TeamColour/0" + " \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
						+ "IMAGE*/Default/Punjab_Cup_2023/TeamColour/0" + " \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
						+ "IMAGE*/Default/Punjab_Cup_2023/TeamColour/0" + " \0");

		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$A$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$B$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_A$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_B$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamA$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamB$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 0 \0");

		for (int i = 0; i <= team.size() - 1; i++) {
			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(0).getTeam1())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$A$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamA-Logo" + " SET "
								+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(0).getTeam2())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$B$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamB-Logo" + " SET "
								+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(1).getTeam1())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_A$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-TeamA-Logo" + " SET "
								+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(1).getTeam2())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_B$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-TeamB-Logo" + " SET "
								+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(2).getTeam1())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamA$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Logo" + " SET "
								+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(2).getTeam2())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamB$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Logo" + " SET "
								+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(3).getTeam1())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Logo" + " SET "
								+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if (team.get(i).getTeamName4().equalsIgnoreCase(playoffs.get(3).getTeam2())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Logo" + " SET "
								+ "IMAGE*/Default/Punjab_Cup_2023/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
		}

		if (playoffs.get(0).getWinner() != null) {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
							+ "IMAGE*/Default/Punjab_Cup_2023/HeaderBand" + " \0");
			if (playoffs.get(0).getWinner().equalsIgnoreCase(playoffs.get(0).getTeam1())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamB-Alpha"
						+ " SET " + "50" + "\0");
			} else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamA-Alpha"
						+ " SET " + "50" + "\0");
			}
		}

		if (playoffs.get(1).getWinner() != null) {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
							+ "IMAGE*/Default/Punjab_Cup_2023/HeaderBand" + " \0");
			if (playoffs.get(1).getWinner().equalsIgnoreCase(playoffs.get(1).getTeam1())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB-Alpha"
						+ " SET " + "50" + "\0");
			} else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA-Alpha"
						+ " SET " + "50" + "\0");
			}
		}

		if (playoffs.get(2).getWinner() != null) {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
							+ "IMAGE*/Default/Punjab_Cup_2023/HeaderBand" + " \0");
			if (playoffs.get(2).getWinner().equalsIgnoreCase(playoffs.get(2).getTeam1())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Alpha"
						+ " SET " + "50" + "\0");
			} else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Alpha"
						+ " SET " + "50" + "\0");
			}
		}

		if (playoffs.get(3).getWinner() != null) {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "
							+ "IMAGE*/Default/Punjab_Cup_2023/HeaderBand" + " \0");
			if (playoffs.get(3).getWinner().equalsIgnoreCase(playoffs.get(3).getTeam1())) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Alpha"
						+ " SET " + "50" + "\0");
			} else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Alpha"
						+ " SET " + "50" + "\0");
			}
		}

		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 In$ManDataIn 0.931 \0");

	}
	
	public void populateLTNextToBat(PrintWriter print_writer,String viz_scene,List<Statistics> stats,List<Player> plyr,List<Tournament> this_series,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id=0;
			double strike_rate = 0;
		
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + 
							logo_path + inn.getBatting_team().getTeamName4() + "\0");
					
					for(int b=1;b<=inn.getBattingCard().size();b++) {
						if(inn.getBattingCard().get(b-1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT) && inn.getBattingCard().get(b-1).getHowOut() == null) {
							row_id = row_id + 1;
							if(row_id <= 3) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPosition" + row_id + " SET " + b + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_id + " SET " + 
										inn.getBattingCard().get(b-1).getPlayer().getTicker_name() + "\0");
								
								
								for(int i = 0; i <= this_series.size() - 1 ; i++) {
									if(this_series.get(i).getPlayerId() == inn.getBattingCard().get(b-1).getPlayerId()) {
										if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + "-" + "\0");
										}else {
											strike_rate = this_series.get(i).getRuns() * 100;
											strike_rate = strike_rate/this_series.get(i).getBallsFaced();
											DecimalFormat df = new DecimalFormat("0.0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + df.format(strike_rate) + "\0");
										}
									}
								}
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + photo_path + 
											inn.getBatting_team().getTeamName4() + "\\\\" + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + inn.getBatting_team().getTeamName4() 
											+ "\\" + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + 
											"\\\\\\\\" + config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
												inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + photo_path + 
//										inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								
							}
						}
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.494\0");
			TimeUnit.MILLISECONDS.sleep(200);	
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId()==playerId) {
							
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
							if(bc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
							}
							
							if(bc.getHowOutText() == null) {
								if(bc.getHowOut()!=null) {
									if(bc.getHowOut().equalsIgnoreCase("timed_out")) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "timed out" + "\0");
									}else if(bc.getHowOut().equalsIgnoreCase("retired_hurt")) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "retired hurt" + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOut() + "\0");
									}
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + " " + "\0");	
								}
															
							}else {
								if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)){
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
												bc.getHowOutPartOne() + " "+ "Sub (" + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									}
								}
								else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)){
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
												bc.getHowOutPartOne().replace("SUB", "Sub") + " " + bc.getHowOutPartTwo() + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									}
								}
								else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
								}
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");							
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					
					for (BattingCard bc : inn.getBattingCard()) {
						if(inn.getFallsOfWickets().size() > 0) {
							if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								}
								
								if (bc.getHowOutText().trim().equalsIgnoreCase("")){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + " " + "\0");								
								}else {
									if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)){
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
													bc.getHowOutPartOne() + " "+ "Sub (" + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										}
									}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)){
										if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + 
													bc.getHowOutPartOne().replace("SUB", "Sub") + " " + bc.getHowOutPartTwo() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									}								
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");	
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$LogoGrp$LogoAll$LeftBlueBase*TEXTURE*IMAGE SET " + logo_path + match.getHomeTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$LogoGrp$LogoAll$LeftBlueBase*TEXTURE*IMAGE SET " + logo_path + match.getAwayTeam().getTeamName4().toUpperCase() + CricketUtil.PNG_EXTENSION + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId()==playerId) {
							
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
							if(bc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "4s: " + bc.getFours() + " 6s: " + bc.getSixes() + "\0");								
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + (bc.getBalls() + 1) + "\0");							
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, playerId,",", match.getEventFile().getEvents()).split(",");
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
										 inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getFirstname() + "\0");
								}								
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "*" + "\0");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
								}
								
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + bc.getBalls() + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "0s" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");

								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "4s" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + bc.getFours() + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "6s" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + bc.getSixes() + "\0");

								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "S/R" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + bc.getStrikeRate() + "\0");
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
			int total_inn = 0;
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
							 inn.getBowling_team().getTeamName4().toUpperCase() + "\0");

					switch(statsType.toUpperCase()) {
					case CricketUtil.BOWLER:
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								
								if(boc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + boc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + boc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + boc.getPlayer().getFirstname() + "\0");
								}								
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + " " + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "Overs" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");

								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "Dots" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + boc.getDots() + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "Extras" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + (boc.getNoBalls() + boc.getWides()) + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "Economy" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + boc.getEconomyRate() + "\0");			
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			if(ns.getSponsor() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
						ns.getSponsor() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + "TLogo" + "\0");
			}
			
			if(ns.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + ns.getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + ns.getSurname().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + ns.getFirstname().toUpperCase() + "\0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + ns.getSubLine().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp*ACTIVE SET 0" + "\0");
				
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
				
		}
	}	
	public void populateNameSuperPlayer(PrintWriter print_writer,String viz_scene, String captainWicketKeeper, int playerId, List<Player> Plyrs, 
			MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			String Home_or_Away="";
			Player player;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			player = Plyrs.stream().filter(plyr -> plyr.getPlayerId() == playerId).findAny().orElse(null);
			
			if(player.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + 
						player.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
						player.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
						player.getFirstname() + "\0");
			}
			
			if(player.getTeamId() == match.getSetup().getHomeTeamId()) {
				Home_or_Away = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			}
			else {
				Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path +
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + " " + "\0");
			
			switch(captainWicketKeeper.toUpperCase())
			{
			case CricketUtil.WICKET_KEEPER:
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "WICKET-KEEPER" + ", " + Home_or_Away + "\0");
				break;
			case CricketUtil.CAPTAIN: 
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + ", " + Home_or_Away + "\0");
				break;
			case "PLAYER OF THE MATCH":
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + "\0");
				break;
			case "PLAYER OF THE TOURNAMENT":
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + "\0");
				break;
			case "PLAYER OF THE SERIES":
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + captainWicketKeeper.toUpperCase() + "\0");
				break;
			case CricketUtil.PLAYER:
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + Home_or_Away + "\0");
				break;
			case "CAPTAIN-WICKETKEEPER":
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + "CAPTAIN & WICKET-KEEPER" + ", " + Home_or_Away + "\0");
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");	
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
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("PUNS1")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "SHER E PUNJAB SEASON 1" + "\0");
			}else
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("IPL")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "IPL CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("TS")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SERIES" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");

			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				
				cont_name = "$Dehighlight";
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

				Player plyr = getPlayerFromMatchData(stats.getPlayerID(), match);
				
				if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\Punjab_Cup_2023\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4() + 
									"\\" + plyr.getPhoto()  + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + 
								plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					if(plyr.getSurname() != null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");

				}
				else {
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + match.getSetup().getAwayTeam().getTeamName4() 
								+ "\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + 
								plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}

					if(plyr.getSurname() != null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");

				}
				
				if(plyer.get(plyr.getPlayerId()-1).getBattingStyle() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
							CricketFunctions.getbattingstyle(plyer.get(plyr.getPlayerId()-1).getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
				}
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");

				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");
				
				strike_rate = stats.getRuns() * 100;
				strike_rate = strike_rate/stats.getBallsFaced();
				DecimalFormat df = new DecimalFormat("0.0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
						"$StatGrpAll$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
				if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
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
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "T20I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("IPL")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "IPL CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			if(Profile.toUpperCase().equalsIgnoreCase("THIS_SERIES")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SERIES" + "\0");
			}
			
			Player plyr = getPlayerFromMatchData(stats.getPlayerID(), match);
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");
			cont_name = "$Dehighlight";
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\c\\Images\\Punjab_Cup_2023\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4() 
							+ "\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
						config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + 
							plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");

			}
			else {
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					
					if(!new File("\\\\"+config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + match.getSetup().getAwayTeam().getTeamName4() + 
							"\\" + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + 
								plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}

				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}

				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");

			}
			
			if(plyer.get(plyr.getPlayerId()-1).getBowlingStyle() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + 
						CricketFunctions.getbowlingstyle(plyer.get(plyr.getPlayerId()-1).getBowlingStyle().toUpperCase())+ "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + " " + "\0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + "$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");
													
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
					"$StatGrpAll$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
					"$StatGrpAll$StatValue*GEOM*TEXT SET " + CricketFunctions.getEconomy(stats.getRunsConceded(), stats.getBallsBowled(), 2, slashOrDash) + "\0");
			
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
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("IPL")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "IPL CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			

		Player plyr = getPlayerFromMatchData(stats.getPlayerID(), match);
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
			}
			
		}
		else {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
			}
		}
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BOWLER:
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + 
					CricketFunctions.getEconomy(stats.getRunsConceded(), stats.getBallsBowled(), 2, "-") + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
			
			if(stats.getBestFigures().equalsIgnoreCase("0")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
						stats.getBestFigures() + "\0");
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
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "T20I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("IPL")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "IPL CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FC")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "FIRST-CLASS CAREER" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			

		Player plyr = getPlayerFromMatchData(stats.getPlayerID(), match);
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
			}
		}
		else {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + plyr.getFirstname() + "\0");
			}
		}
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
			
			strike_rate = stats.getRuns() * 100;
			strike_rate = strike_rate/stats.getBallsFaced();
			DecimalFormat df = new DecimalFormat("0.0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "S/R" + "\0");
			if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");

			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
			if(stats.getBestScore().equalsIgnoreCase("0")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBestScore() + "\0");
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
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpactLegend" + " SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstituteHead" + " SET " + "SUBSTITUTES" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " +
					match.getSetup().getMatchIdent() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "TEAMS" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$Header$noname$SubHeader*GEOM*TEXT SET " +
					match.getSetup().getTournament().toUpperCase() + "\0");
			
			for(int i = 1; i <= 2 ; i++) {
				if(i == 1) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamNameGrp1$RowAnimation$TeamNameGrp"
						+ "$NameAll$TeamFirstName*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$noname$TeamNameGrp1$TeamFirstName*GEOM*TEXT SET " 
							+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName1" + " SET " + " " + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamRefName" + " SET " + logo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
							+ logo_path + "TLogo" + "\0");
					
					for(Player hs : match.getSetup().getHomeSquad()) {
						row_id = row_id + 1;
						omo = 0;
						cont = "Dehighlight";
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
								"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo + "\0");

						if(hs.getRole().equalsIgnoreCase("BATSMAN")) {
							if(hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman" + "\0");
							}else if(hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(hs.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if(hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman" + "\0");
							}else if(hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(hs.getRole().equalsIgnoreCase("BOWLER")) {
							if(hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" + "\0");
							}else {
								switch(hs.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						}else if(hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if(hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							}else {
								switch(hs.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						
						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFull_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + "" + "\0");
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFull_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + "" + "\0");
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Keeper" + "\0");

						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFull_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + "" + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Keeper" + "\0");

						}
						else {
							if(hs.getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + hs.getFull_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + "" + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");
						}
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamsAll$TeamAll1_Subs"
							+ "$TeamAll1*FUNCTION*Grid*num_row SET "+ match.getSetup().getHomeSubstitutes().size() + " \0");

					for (int j = 0; j <= match.getSetup().getHomeSubstitutes().size() - 1; j++) {
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamsAll$noname$RowA"+(j+1)+"$Dehighlight$CaptainIcon$*ACTIVE SET " + "0" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamsAll$noname$RowA"+(j+1)+"$Dehighlight$InternationalIcon$*ACTIVE SET " + "0" + " \0");
						
						if (match.getSetup().getHomeSubstitutes().get(i - 1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (match.getSetup().getHomeSubstitutes().get(i - 1).getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (match.getSetup().getHomeSubstitutes().get(i - 1).getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (match.getSetup().getHomeSubstitutes().get(i - 1).getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (match.getSetup().getHomeSubstitutes().get(i - 1).getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (match.getSetup().getHomeSubstitutes().get(i - 1).getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (match.getSetup().getHomeSubstitutes().get(i - 1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (match.getSetup().getHomeSubstitutes().get(i - 1).getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (match.getSetup().getHomeSubstitutes().get(i - 1).getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
											+ " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
											+ " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (match.getSetup().getHomeSubstitutes().get(i - 1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (match.getSetup().getHomeSubstitutes().get(i - 1).getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (match.getSetup().getHomeSubstitutes().get(i - 1).getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
											+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
											+ " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
								+ "tTeam1SubsLastName" + (j + 1) + " SET "+ match.getSetup().getHomeSubstitutes().get(j).getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
								+ "tTeam1SubsFirstName" + (j + 1) + " SET " + "" + "\0");

					}
					
				} else {
					row_id = 0;
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamNameGrp2$RowAnimation$TeamNameGrp$NameAll$TeamFirstName*GEOM*TEXT SET " 
											+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2_Subs$TeamNameGrp2$TeamFirstName*GEOM*TEXT SET " 
							+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName2" + " SET " + " " + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
							+ logo_path + "TLogo" + "\0");

					for(Player as : match.getSetup().getAwaySquad()) {
						row_id = row_id + 1;
						omo = 0;
						cont = "Dehighlight";
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo + "\0");

						if(as.getRole().equalsIgnoreCase("BATSMAN")) {
							if(as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman" + "\0");
							}else if(as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(as.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if(as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman" + "\0");
							}else if(as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(as.getRole().equalsIgnoreCase("BOWLER")) {
							if(as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" + "\0");
							}else {
								switch(as.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						}else if(as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if(as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
										"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							}else {
								switch(as.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
											"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						
						
						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFull_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + "" + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFull_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + "" + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Keeper" + "\0");

						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFull_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + "" + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Keeper" + "\0");

						}
						else {
							if(as.getSurname() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFirstname() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$FirstName*GEOM*TEXT SET " + as.getFull_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
										+ "$RowAnimation$RowOmo$" + cont + "$TextAll$NameAll$LastName*GEOM*TEXT SET " + "" + "\0");
							}
							
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$RoleIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id 
									+ "$RowAnimation$RowOmo$" + cont + "$TextAll$InternationalIcon*ACTIVE SET 0 \0");

						}
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamsAll$TeamAll2_Subs"
							+ "$TeamAll2*FUNCTION*Grid*num_row SET "+ match.getSetup().getAwaySubstitutes().size() + " \0");

					for (int j = 0; j <= match.getSetup().getAwaySubstitutes().size() - 1; j++) {
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamsAll$TeamAll2_Subs$RowB"+(j+1)+"$Dehighlight$CaptainIcon$*ACTIVE SET " + "0" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamsAll$TeamAll2_Subs$RowB"+(j+1)+"$Dehighlight$InternationalIcon$*ACTIVE SET " + "0" + " \0");
						
						if (match.getSetup().getAwaySubstitutes().get(i - 1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
							if (match.getSetup().getAwaySubstitutes().get(i - 1).getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (match.getSetup().getAwaySubstitutes().get(i - 1).getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (match.getSetup().getAwaySubstitutes().get(i - 1).getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if (match.getSetup().getAwaySubstitutes().get(i - 1).getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman" + "\0");

							} else if (match.getSetup().getAwaySubstitutes().get(i - 1).getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						} else if (match.getSetup().getAwaySubstitutes().get(i - 1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
							if (match.getSetup().getAwaySubstitutes().get(i - 1).getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowler" + "\0");
							} else {
								switch (match.getSetup().getAwaySubstitutes().get(i - 1).getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
											+ " SET " + icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
											+ " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						} else if (match.getSetup().getAwaySubstitutes().get(i - 1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if (match.getSetup().getAwaySubstitutes().get(i - 1).getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
							} else {
								switch (match.getSetup().getAwaySubstitutes().get(i - 1).getBowlingStyle()) {
								case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
											+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2SubsRole" + (j+1)
											+ " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
								+ "tTeam2SubsLastName" + (j + 1) + " SET "+ match.getSetup().getAwaySubstitutes().get(j).getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
								+ "tTeam2SubsFirstName" + (j + 1) + " SET " + "" + "\0");

					}
				}
			}
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + 
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + 
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.590 \0");	
		}
	}
	
	public void hideAndShowContainer(String broadcaster, String which_graphics, PrintWriter print_writer) {
		switch (which_graphics.toUpperCase()) {
		case "INFOBAR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline*ACTIVE SET 0" + "\0");
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
		this.status = CricketUtil.SUCCESSFUL;
	    switch(infobar.getIdent_section().toUpperCase()) {
	    case CricketUtil.SUPER_OVER:
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET "  + " SUPER OVER " + "\0");
	    	
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
									+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    case CricketUtil.TOSS:
	    	if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
	    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
						+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
						+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
			}
	    	
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
									+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");

	    	break;
	    
	    case "RESULT":
	    	for(Inning inn : match.getMatch().getInning()) {
	    		if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
	    			if(match.getMatch().getMatchResult() != null) {
	    				if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
	    					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase()+ "\0");
	    				}
	    				else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
	    					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
									+  "MATCH TIED" + "\0");
	    				}
	    				else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
	    					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
									+  match.getMatch().getMatchStatus().toUpperCase()   + "\0");
	    				}
	    				else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
	    					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
									+ "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase()  + "\0");
	    				}
	    				else {
	    					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
									+ CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
	    				}
	    			}
	    			else {
	    				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
								+ CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
	    				if(match.getSetup().getTargetType() != null) {
	    					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
	    						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
	    								+ CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
	    						
	    					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
	    						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
	    								+ CricketFunctions.GenerateMatchSummaryStatus(inn.getInningNumber(), match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
	    					}
	    				}
	    			}
	    		}
	    	}
	    	
	    	
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
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
					+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    
	    case "VENUE":
	    	
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
					+ match.getSetup().getVenueName().toUpperCase() + "\0");
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " 
									+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
	    	break;
	    
	    case "TOURNAMENT":
	    	
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " 
					+ match.getSetup().getTournament().toUpperCase() + "\0");
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " 
					+ logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " 
									+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " 
									+ logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
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
			this.status = CricketUtil.SUCCESSFUL;
			infobar = populateInfobarTeamScore(infobar,false, print_writer, match, broadcaster);
			infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, broadcaster);
			infobar = populateVizInfobarRightTop(infobar, false, print_writer, match, broadcaster);
			infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
		}
		return infobar;
	}
	public Infobar populateInfobarTeamScore(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster)
	{
		this.status = CricketUtil.SUCCESSFUL;
	    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + " " + "\0");
    	
		for(Inning inn : match.getMatch().getInning()) {
			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET "  + logo_path + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET "  + logo_path + 
							inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatTeamName" + " SET " + 
							inn.getBatting_team().getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatTeamLastName" + " SET " + 
							inn.getBatting_team().getTeamName3().toUpperCase() + "\0");
					
				}
				
				if(match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().trim().isEmpty() && match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.DLS) ||
						match.getSetup().getTargetType() != null && !match.getSetup().getTargetType().trim().isEmpty() && match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.VJD)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$noname$DLS*ACTIVE SET 1 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$noname" + "$DLS*GEOM*TEXT SET " + 
//							"(" + match.getSetup().getTargetOvers() + ") " + match.getSetup().getTargetType().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + 
			    			"(" + match.getSetup().getTargetOvers() + ") " + match.getSetup().getTargetType().toUpperCase() + "\0");
			    }else if(match.getSetup().getTargetOvers() != null && !match.getSetup().getTargetOvers().trim().isEmpty()) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$noname$DLS*ACTIVE SET 1 \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$noname" + "$DLS*GEOM*TEXT SET " + 
//							"(" + match.getSetup().getTargetOvers() + ") " + match.getSetup().getTargetType().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + 
			    			"(" + match.getSetup().getTargetOvers() + ") " + "\0");
			    }else {
			    	//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDLS" + " SET " + " " + "\0");
			    }
			    
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + " SET " + 
						CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
				
			    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamOvers" + " SET " + CricketFunctions.
			    		OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
			    if(!match.getSetup().getMatchType().isEmpty() && match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$BatTeamNameAndScoreGrp$noname$PowerPlay*ACTIVE SET 0 \0");
			    }else if(!match.getSetup().getTargetOvers().isEmpty() && Double.valueOf(match.getSetup().getTargetOvers()) == 1) {
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
		this.status = CricketUtil.SUCCESSFUL;
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
		this.status = CricketUtil.SUCCESSFUL;
		for(Inning inn : match.getMatch().getInning()) {
			
			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				
				if(current_batsmen != null && current_batsmen.size() >= 2) {
		
					if (CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(),inn.getInningNumber(), current_batsmen.get(0).getPlayerId())
							.equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman1$Impact*FUNCTION*Omo*vis_con SET "
										+ "1" + "\0");
					} else if (CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(),
							inn.getInningNumber(), current_batsmen.get(0).getPlayerId())
							.equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman1$Impact*FUNCTION*Omo*vis_con SET "
										+ "1" + "\0");
					} else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman1$Impact*FUNCTION*Omo*vis_con SET "
										+ "0" + "\0");
					}
					
					if (CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(),inn.getInningNumber(), current_batsmen.get(1).getPlayerId())
							.equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman2$Impact*FUNCTION*Omo*vis_con SET "
										+ "1" + "\0");
					} else if (CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(),
							inn.getInningNumber(), current_batsmen.get(1).getPlayerId())
							.equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman2$Impact*FUNCTION*Omo*vis_con SET "
										+ "1" + "\0");
					} else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman2$Impact*FUNCTION*Omo*vis_con SET "
										+ "0" + "\0");
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
	public Infobar populateVizInfobarRight(Infobar infobar, boolean is_this_updating, PrintWriter print_writer,List<MatchAllData> tourn_matches, MatchAllData match, String broadcaster) throws CloneNotSupportedException 
	{
		this.status = CricketUtil.SUCCESSFUL;
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
		case CricketUtil.FOUR:
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterHead" + " SET " + "FOURS THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterCounter" + " SET " + inn.getTotalFours() + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.FOUR);
			break;
//		case "TOURNAMENT_FOURS":
//			String fours = String.valueOf(CricketFunctions.extracttournamentFoursAndSixes("COMBINED_PAST_CURRENT_MATCH_DATA", 
//					tourn_matches, match, null).getTournament_fours());
//			for(Inning inn : match.getMatch().getInning()) {
//				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterHead" + " SET " + "FOURS THIS TOURNAMENT" + "\0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterCounter" + " SET " + fours + "\0");
//				}
//			}
//			infobar.setLast_bottom_right_section("TOURNAMENT_FOURS");
//			break;	
		case CricketUtil.SIX:
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterHead" + " SET " + "SIXES THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterCounter" + " SET " + inn.getTotalSixes() + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.SIX);
			break;
//		case "TOURNAMENT_SIXES":
//			String sixes = String.valueOf(CricketFunctions.extracttournamentFoursAndSixes("COMBINED_PAST_CURRENT_MATCH_DATA", 
//					tourn_matches, match, null).getTournament_sixes());
//			for(Inning inn : match.getMatch().getInning()) {
//				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterHead" + " SET " + "SIXES THIS TOURNAMENT" + "\0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterCounter" + " SET " + 
//					sixes + "\0");
//				}
//			}
//			infobar.setLast_bottom_right_section("TOURNAMENT_SIXES");
//			break;	
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
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + "PUNJAB T20" + "\0");
					
			infobar.setLast_bottom_right_section("TOURNAMENT-NAME");
			break;
		}		
			
		return infobar;
	}
	public Infobar populateVizInfobarRightTop(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster) throws InterruptedException
	{
		this.status = CricketUtil.SUCCESSFUL;
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
							System.out.println("BOWLER "+boc.getPlayerId());
							if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vImpactBowler" + " SET " + "1" + "\0");
							}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
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
		this.status = CricketUtil.SUCCESSFUL;
		switch(infobar.getBottom_right_bottom_section().toUpperCase()) {
		case CricketUtil.OVER:
			
			int Player_id=0;
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){						
					
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
							Player_id = boc.getPlayerId();
						}
					}
					String[] this_over = CricketFunctions.getEventsText(CricketUtil.OVER,Player_id,",", match.getEventFile().getEvents(),0).split(",");
					
					if(infobar.getBottom_right_section() == "") {
						System.out.println("HI");
						if(this_over.length==1 && this_over[0] == "") {
							 if(infobar.getLast_bottom_right_top_section() != null && infobar.getLast_bottom_right_bottom_section() != null 
										&& !infobar.getLast_bottom_right_top_section().trim().isEmpty() 
										&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // Normal change on
								 System.out.println("infobar.getLast_bottom_right_bottom_section() : "+infobar.getLast_bottom_right_bottom_section());
								 if(infobar.getLast_bottom_right_bottom_section().toUpperCase().equalsIgnoreCase(CricketUtil.OVER)) {
									 switch(infobar.getLast_bottom_right_bottom_section().toUpperCase()){
										case CricketUtil.OVER:
											processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
											infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
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
								 }else {
									 if(infobar.getLast_bottom_right_bottom_section().toUpperCase().equalsIgnoreCase("LASTOVERRUNS")) {
										 processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
											TimeUnit.MILLISECONDS.sleep(200);
											processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
											//infobar.setBottom_right_bottom_section(CricketUtil.OVER);
											infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
									 }
								 }
							}else {
								infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
							}
						}else {
							System.out.println("HELLO");
							infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
						}
					}
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
						}else if(this_over[i].toUpperCase().contains("+W")|| this_over[i].toUpperCase().equalsIgnoreCase("W")) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
									+ (i+1) + "*FUNCTION*Omo*vis_con SET 3 \0");
						}else if(this_over[i].toUpperCase().contains("WD") || this_over[i].toUpperCase().contains("NB")
								 || this_over[i].toUpperCase().contains("B") || this_over[i].toUpperCase().contains("LB") || this_over[i].toUpperCase().contains("PN")) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
									+ (i+1) + "*FUNCTION*Omo*vis_con SET 5 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
						} else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" 
									+ (i+1) + "*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tThisOverRun" + (i+1) + " SET " + this_over[i] + "\0");
						}
					}
				}
			}
//			infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
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
					case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
						totalRuns += match.getEventFile().getEvents().get(i).getEventRuns();
						break;
					case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY:
						totalRuns += match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
						break;
					 case CricketUtil.LOG_WICKET:
						 totalRuns += match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns();
						 break;
					 case CricketUtil.LOG_ANY_BALL:
						 totalRuns += match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns() 
						 + match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
						 break;
					}
//					totalRuns+=match.getEventFile().getEvents().get(i).getEventRuns()+match.getEventFile().getEvents().get(i).getEventExtraRuns() 
//								+ match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
				}else if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.END_OVER)) {
					bowlerNum = match.getEventFile().getEvents().get(i).getEventBowlerNo();
				}
			}
			if(totalRuns == 1) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlingEnd" + " SET " + 
						"LAST OVER - " + totalRuns + " RUN"+ "\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlingEnd" + " SET " + 
						"LAST OVER - " + totalRuns + " RUNS"+ "\0");
			}
			
			infobar.setLast_bottom_right_bottom_section("LASTOVERRUNS");
				
			break;
		case "ECONOMY":
//			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Economy$Prompt-Regular*GEOM*TEXT SET " + "ECONOMY" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerEconomy" + " SET " + "" + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER) || 
									boc.getStatus().equalsIgnoreCase(CricketUtil.LAST + CricketUtil.BOWLER)) {
							if(boc.getEconomyRate() == null) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Economy$Prompt-Regular*GEOM*TEXT SET " + "ECONOMY - " + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Economy$Prompt-Regular*GEOM*TEXT SET " + "ECONOMY - " 
										+ boc.getEconomyRate() + "\0");
							}
						}
					}
				}
			}
			infobar.setLast_bottom_right_bottom_section("ECONOMY");
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
		this.status = CricketUtil.SUCCESSFUL;
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
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras$noname$noname$Wide"
							+ "$WERE*GEOM*TEXT SET " + "WD" + "\0");
					
					
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
			if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 1 || CricketFunctions.getWicketsLeft(match, 2) <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall()  == 0) {
				
				if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 1) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours*ACTIVE SET 1" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours$1$NEEDRUNS*GEOM*TEXT SET " + 
							CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRunHead" + " SET " + "RUN" +
							CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
							((match.getSetup().getMaxOvers()*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
							CricketFunctions.Plural(((match.getSetup().getMaxOvers()*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6
									+match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + "\0");
					
//					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + "SCORE'S ARE LEVEL" + "\0");
				}
				else if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || CricketFunctions.getWicketsLeft(match, 2) <= 0 || 
						CricketFunctions.GetTargetData(match).getRemaningBall()  == 0) {
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours*ACTIVE SET 0" + "\0");
					
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + 
								match.getMatch().getInning().get(1).getBowling_team().getTeamName1().toUpperCase() + " WIN BY SUPER OVER" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedHead" + " SET " + 
								CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					}
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
					if(match.getSetup().getTargetOvers().contains(".")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
								((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1])-
										(match.getMatch().getInning().get(1).getTotalOvers() * 6 + match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
						
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
							CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1])-
								(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + " (" + match.getSetup().getTargetType().toUpperCase()+")" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
								((Integer.valueOf(match.getSetup().getTargetOvers())*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
							CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers())*6)-(match.getMatch().getInning().get(1).getTotalOvers() * 
								6 + match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + " (" + match.getSetup().getTargetType().toUpperCase()+ ")" + "\0");
					}
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
							((match.getSetup().getMaxOvers()*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + "BALL" +
							CricketFunctions.Plural(((match.getSetup().getMaxOvers()*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6
									+match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + "\0");
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
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketPlayerName" + " SET " + 
										bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketBalls" + " SET " + 
										"(" + bc.getBalls() + ")" + "\0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketRuns" + " SET " + 
										bc.getRuns() + "\0");
								
								if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)){
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + 
												bc.getHowOutPartOne() + " "+ "Sub (" + bc.getHowOutFielder().getTicker_name() + ")" + "\0");
									}else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + 
												bc.getHowOutText() + "\0");
									}
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)){
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + 
												bc.getHowOutPartOne().replace("SUB", "Sub") + " " + bc.getHowOutPartTwo() + "\0");
									}else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + 
												bc.getHowOutText() + "\0");
									}
								}
								else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + 
											bc.getHowOutText() + "\0");
								}
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
					if(((inn.getTotalOvers()*6) + inn.getTotalBalls()) > 22) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeline" + " SET " + "22" + "\0");
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
								
								if(match.getEventFile().getEvents().get(i).getEventType().equalsIgnoreCase(CricketUtil.LOG_WICKET)) {
									if(!match.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT) && 
							    			!match.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
										
										ball_count = ball_count + 1;
									}
								}else {
									ball_count = ball_count + 1;
								}
								
								switch (match.getEventFile().getEvents().get(i).getEventType())
							    {
							    case CricketUtil.CHANGE_BOWLER:
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 0 \0");
									break;
							    case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
											+ ball_count + "*FUNCTION*Omo*vis_con SET 2 \0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
											match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
									break;
							    case CricketUtil.FOUR: case CricketUtil.SIX:
							    	if(match.getEventFile().getEvents().get(i).getEventWasABoundary() != null && 
							    		match.getEventFile().getEvents().get(i).getEventWasABoundary().equalsIgnoreCase(CricketUtil.YES)) {
							    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
							    	}else {
							    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 2 \0");
							    	}
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
											match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
									break;
							    case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY: 
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
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
						    		int runs = match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns();

							    	if(runs>1) {
							    		this_ball_data=String.valueOf(runs) + this_ball_data.toUpperCase();
							    	}else {
							    		this_ball_data= this_ball_data.toUpperCase();
							    	}
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
											this_ball_data + "\0");
									break;
							    case CricketUtil.LOG_WICKET:
							    	if(!match.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT) && 
							    			!match.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
							    		
							    		if (match.getEventFile().getEvents().get(i).getEventRuns() > 0) {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 15 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + 
													String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns()) + "+W" + "\0");
								    	} else {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 15 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + "W" + "\0");
								    	}
							    	}
							    	break;
							    case CricketUtil.LOG_ANY_BALL:
							    	if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
							    		this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "PN";
							    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
												+ ball_count + "*FUNCTION*Omo*vis_con SET 5 \0");
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
							    	}else {
							    		if(match.getEventFile().getEvents().get(i).getEventExtra() != null && !match.getEventFile().getEvents().get(i).getEventExtra().isEmpty()) {
								    		if(match.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.WIDE)){
								    			if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
								    				if((match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns()+
									    					match.getEventFile().getEvents().get(i).getEventSubExtraRuns())>1) {
								    					this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns()+
										    					match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "WD";
								    				}else {
								    					this_ball_data = "WD";
								    				}
								    				
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
							    		
							    		if(match.getEventFile().getEvents().get(i).getEventSubExtra() != null && !match.getEventFile().getEvents().get(i).getEventSubExtra().isEmpty()) {
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
									    			//this_ball_data = this_ball_data + "Pn";
									    		//}
								    		}
							    		}
							    		if (match.getEventFile().getEvents().get(i).getEventHowOut() != null && !match.getEventFile().getEvents().get(i).getEventHowOut().isEmpty()) {
								    		this_ball_data = this_ball_data + "+W";
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 15 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
								    	}else {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline$Timeline$noname$Timeline$Ball" 
													+ ball_count + "*FUNCTION*Omo*vis_con SET 5 \0");
											print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimelineRun" + ball_count + " SET " + this_ball_data + "\0");
								    	}
							    	}
							    	break;
							    }
								break;
							}
								
						    if(ball_count >= 22) {
						    	break;
						    }
						  }
						}
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1_2$Section6$Timeline*ACTIVE SET 1" + "\0");
				processAnimation(print_writer, "Section6$TimelineIn", "START", broadcaster);
			}
			break;
		}
			
		infobar.setLast_top_section(infobar.getTop_section());
		return infobar;
	}
	public Infobar populateInfobarFreeText(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, InfobarStats ibs, MatchAllData match, String broadcaster)
	{	
		this.status = CricketUtil.SUCCESSFUL;
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
		this.status = CricketUtil.SUCCESSFUL;
		switch (Dir_value.toUpperCase()) {
		case "FOURS":
			processAnimation(print_writer, "FourIn", "START", session_selected_broadcaster);
			which_director_on_screen = "FOURS";
			break;

		case "SIXES":
			processAnimation(print_writer, "SixIn", "START", session_selected_broadcaster);
			which_director_on_screen = "SIXES";
			break;
		
		case "WICKETS":
			processAnimation(print_writer, "WicketIn", "START", session_selected_broadcaster);
			which_director_on_screen = "WICKETS";
			break;

		case "FREE-HIT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitIn START \0");
			which_director_on_screen = "FREEHIT";
			break;
		}
	}
	
	public void populateMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET "+ " " + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
			
			
			if(fix.get(match_number - 1).getMatchnumber()<10) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ 
						"MATCH "+fix.get(match_number - 1).getMatchnumber() + " \0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ 
						fix.get(match_number - 1).getMatchfilename().toUpperCase() + " \0");
			}
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ "LIVE FROM "+ 
					fix.get(match_number-1).getVenue().toUpperCase() +" AT " +fix.get(match_number-1).getLocalTime() + " \0");
			
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$HomeTeamName_Grp$FirstName*GEOM*TEXT SET " + TM.getTeamName1().toUpperCase() + " \0");
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + logo_path + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$AwayLogoGrp$AwayTeamName_Grp$FirstName*GEOM*TEXT SET " + TM.getTeamName1().toUpperCase() + " \0");
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ "TOMORROW" + " \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + 
						"LIVE FROM " + fix.get(match_number-1).getVenue().toUpperCase() +" AT " +fix.get(match_number-1).getLocalTime() + " \0");
			}else {
				cal.add(Calendar.DATE, -1);
				if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ "UP NEXT" + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + "LIVE FROM " + 
							fix.get(match_number-1).getVenue().toUpperCase()+" AT " +fix.get(match_number-1).getLocalTime() + " \0");
				}else {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "
							+ fix.get(match_number-1).getDate() + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " + "LIVE FROM " + 
							fix.get(match_number-1).getVenue().toUpperCase()+" AT " +fix.get(match_number-1).getLocalTime() + " \0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 2.474 \0");
				
		}
	}
	public void populateMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET "+ " " + " \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo" + " SET " + logo_path + 
					match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$HomeLogoGrp$HomeTeamName_Grp$FirstName*GEOM*TEXT SET " + 
					match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo" + " SET " + logo_path + 
					match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$logos$AwayLogoGrp$AwayTeamName_Grp$FirstName*GEOM*TEXT SET " + 
					match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ "" + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ 
					match.getSetup().getMatchIdent() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$Header$SubHeader*GEOM*TEXT SET "+ 
					match.getSetup().getTournament().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$MatchId$TeamsAll$TeamData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ 
					"LIVE FROM "+ match.getSetup().getVenueName().toUpperCase() + " \0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 \0");
				
		}
		
	}
	public void populateLTMatchId(PrintWriter print_writer,String viz_scene, List<VariousText> various_test,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + match.getSetup().getHomeTeam().getTeamName2().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + match.getSetup().getAwayTeam().getTeamName2().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + match.getSetup().getMatchIdent() + "\0");
			
			for(VariousText vtext : various_test) {
				if(vtext.getVariousType().equalsIgnoreCase("LT_MATCH_ID") && vtext.getUseThis().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
							vtext.getVariousText() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + 
							match.getSetup().getVenueName().toUpperCase() + "\0");
				}
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamRefName" + " SET " + logo_path + 
					match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamRefName" + " SET " + logo_path + 
					match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

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
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamRefName" + " SET " + logo_path + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + TM.getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + TM.getTeamName3().toUpperCase() + "\0");
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamRefName" + " SET " + logo_path + TM.getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + TM.getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + TM.getTeamName3().toUpperCase() + "\0");
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + fix.get(match_number-1).getVenue()+ "\0");
				if(fix.get(match_number - 1).getMatchnumber()<10) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "TOMORROW - " + 
							"MATCH "+fix.get(match_number - 1).getMatchnumber() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "TOMORROW - " + 
							 fix.get(match_number - 1).getMatchfilename().toUpperCase() + "\0");
				}
			}else {
				cal.add(Calendar.DATE, -1);
				if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + 
							fix.get(match_number-1).getVenue() + "\0");
					if(fix.get(match_number-1).getMatchnumber()<10) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "UP NEXT - " + 
							"MATCH "+	fix.get(match_number - 1).getMatchnumber() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "UP NEXT - " + 
								fix.get(match_number - 1).getMatchfilename().toUpperCase() + "\0");
					}
					
				}else {

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "LIVE FROM " + 
							fix.get(match_number-1).getVenue() + "\0");
					if(fix.get(match_number-1).getMatchnumber()<10) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + 
								fix.get(match_number-1).getDate() + " - MATCH " + fix.get(match_number - 1).getMatchnumber() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + 
								fix.get(match_number-1).getDate() + " - " + fix.get(match_number - 1).getMatchfilename().toUpperCase() + "\0");
					}
					
				}
				
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.120 \0");
				
		}
	}
	public void populatePlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,MatchAllData match, String broadcaster, Configuration config) throws IOException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0,omo=0;
			String cont = "";
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpactLegend" + " SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubstituteHead" + " SET " + "SUBSTITUTES" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
					+ " " + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
					+ match.getSetup().getMatchIdent() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$SubHeader*GEOM*TEXT SET " 
					+ match.getSetup().getTournament().toUpperCase() + " \0");
			
			if(TeamId == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");

				for(Player hs : match.getSetup().getHomeSquad()) {
					row_id = row_id + 1;
					omo = 0;
					cont = "Dehighlight";
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$BottomData$StrikeRate*GEOM*TEXT SET " 
											+ " " + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
							+ match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET "+ omo + " \0");
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getPhoto() + ".png" + "\0");
					}else {
						
						if(!new File("\\\\"+config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + match.getSetup().getHomeTeam().getTeamName4() + 
								"\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\" + 
								config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getHomeTeam().getTeamName4() + "\\\\" + 
									hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$ImageGrp$PlayerImage*TEXTURE*IMAGE SET "+ photo_path 
										//+ match.getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + hs.getFirstname() + CricketUtil.PNG_EXTENSION + " \0");

					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$RoleIconGrp*ACTIVE SET " + 0 + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$CaptainIcon*ACTIVE SET " + 1 + " \0");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getTicker_name() + " (WK)" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$RoleIconGrp*ACTIVE SET " + 0 + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$CaptainIcon*ACTIVE SET " + 0 + " \0");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getTicker_name() + " (WK)" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$RoleIconGrp*ACTIVE SET " + 0 + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$CaptainIcon*ACTIVE SET " + 1 + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + hs.getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$RoleIconGrp*ACTIVE SET " + 0 + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$CaptainIcon*ACTIVE SET " + 0 + " \0");
					}
				}
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$subs$TeamAll1*FUNCTION*Grid*num_row SET "+ match.getSetup().getHomeSubstitutes().size() + " \0");

				for (int j = 0; j <= match.getSetup().getHomeSubstitutes().size() - 1; j++) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$subs$TeamAll1$RowA"+(j+1)+"$Dehighlight$CaptainIcon$*ACTIVE SET " + "0" + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$subs$TeamAll1$RowA"+(j+1)+"$Dehighlight$InternationalIcon$*ACTIVE SET " + "0" + " \0");
					
					if (match.getSetup().getHomeSubstitutes().get(j).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						if (match.getSetup().getHomeSubstitutes().get(j).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "Batsman" + "\0");

						} else if (match.getSetup().getHomeSubstitutes().get(j).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					} else if (match.getSetup().getHomeSubstitutes().get(j).getRole().equalsIgnoreCase("BAT/KEEPER")) {
						if (match.getSetup().getHomeSubstitutes().get(j).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "Batsman" + "\0");

						} else if (match.getSetup().getHomeSubstitutes().get(j).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					} else if (match.getSetup().getHomeSubstitutes().get(j).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if (match.getSetup().getHomeSubstitutes().get(j).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "FastBowler" + "\0");
						} else {
							switch (match.getSetup().getHomeSubstitutes().get(j).getBowlingStyle()) {
							case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowler" + "\0");
								break;
							case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
								break;
							}
						}
					} else if (match.getSetup().getHomeSubstitutes().get(j).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if (match.getSetup().getHomeSubstitutes().get(j).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
						} else {
							switch (match.getSetup().getHomeSubstitutes().get(j).getBowlingStyle()) {
							case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
								break;
							case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
								break;
							}
						}
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
							+ "tTeam1SubsLastName" + (j + 1) + " SET "+ match.getSetup().getHomeSubstitutes().get(j).getFull_name() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
							+ "tTeam1SubsFirstName" + (j + 1) + " SET " + "" + "\0");

				}
			}
			
			else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");

				for(Player as : match.getSetup().getAwaySquad()) {
					row_id = row_id + 1;
					omo = 0;
					cont = "Dehighlight";
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
							"$BottomData$StrikeRate*GEOM*TEXT SET " + " " + " \0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
							+ match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET "+ omo + " \0");

					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + photo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + as.getPhoto() + ".png" + "\0");
					}else {
						
						if(!new File("\\\\"+config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + match.getSetup().getAwayTeam().getTeamName4() + 
								"\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage"+ row_id + " SET " + "\\\\\\\\" + 
								config.getPrimaryIpAddress() + local_photo_path + match.getSetup().getAwayTeam().getTeamName4() + "\\\\" + 
								as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + "$ImageGrp$PlayerImage*TEXTURE*IMAGE SET "+ 
							//photo_path + match.getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + as.getFirstname() + CricketUtil.PNG_EXTENSION + " \0");

					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$RoleIconGrp*ACTIVE SET " + 0 + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$CaptainIcon*ACTIVE SET " + 1 + " \0");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getTicker_name() + " (WK)" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$RoleIconGrp*ACTIVE SET " + 0 + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$CaptainIcon*ACTIVE SET " + 0 + " \0");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getTicker_name() + " (WK)" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$RoleIconGrp*ACTIVE SET " + 0 + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$CaptainIcon*ACTIVE SET " + 1 + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$NameAll$LastName*GEOM*TEXT SET " + as.getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$RoleIconGrp*ACTIVE SET " + 0 + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + row_id + "$RowAnimation$RowOmo$" + cont + 
								"$TextAll$Icons$CaptainIcon*ACTIVE SET " + 0 + " \0");
					}
				}
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$subs$TeamAll1*FUNCTION*Grid*num_row SET "+ match.getSetup().getAwaySubstitutes().size() + " \0");

				for (int j = 0; j <= match.getSetup().getAwaySubstitutes().size() - 1; j++) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$subs$TeamAll1$RowA"+(j+1)+"$Dehighlight$CaptainIcon$*ACTIVE SET " + "0" + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$subs$TeamAll1$RowA"+(j+1)+"$Dehighlight$InternationalIcon$*ACTIVE SET " + "0" + " \0");
					
					if (match.getSetup().getAwaySubstitutes().get(j).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						if (match.getSetup().getAwaySubstitutes().get(j).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "Batsman" + "\0");

						} else if (match.getSetup().getAwaySubstitutes().get(j).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					} else if (match.getSetup().getAwaySubstitutes().get(j).getRole().equalsIgnoreCase("BAT/KEEPER")) {
						if (match.getSetup().getAwaySubstitutes().get(j).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "Batsman" + "\0");

						} else if (match.getSetup().getAwaySubstitutes().get(j).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					} else if (match.getSetup().getAwaySubstitutes().get(j).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if (match.getSetup().getAwaySubstitutes().get(j).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "FastBowler" + "\0");
						} else {
							switch (match.getSetup().getAwaySubstitutes().get(j).getBowlingStyle()) {
							case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowler" + "\0");
								break;
							case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "SpinBowlerIcon" + "\0");
								break;
							}
						}
					} else if (match.getSetup().getAwaySubstitutes().get(j).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if (match.getSetup().getAwaySubstitutes().get(j).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
									+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
						} else {
							switch (match.getSetup().getAwaySubstitutes().get(j).getBowlingStyle()) {
							case "RF":case "RFM":case "RMF":case "RM":case "RSM":case "LF":case "LFM":case "LMF":case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "FastBowlerAllrounder" + "\0");
								break;
							case "ROB":case "RLB":case "LSL":case "WSL":case "LCH":case "RLG":case "WSR":case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1SubsRole" + (j+1)
										+ " SET " + icon_path + "/" + "SpinBowlerAllrounder" + "\0");
								break;
							}
						}
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
							+ "tTeam1SubsLastName" + (j + 1) + " SET "+ match.getSetup().getAwaySubstitutes().get(j).getFull_name() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
							+ "tTeam1SubsFirstName" + (j + 1) + " SET " + "" + "\0");

				}
			}
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BottomInfoGrp$BottomInfo$Equations*GEOM*TEXT SET " +
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BottomInfoGrp$BottomInfo$Equations*GEOM*TEXT SET " +
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$Images_In 1.800 \0");
		
		
	}
	
	public void populateThisSeriesBat(PrintWriter print_writer, String viz_scene,int Playerid, String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster, Statistics stats, Configuration config) {
		
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
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
			
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					
					if(this_series.get(i).getPlayer().getSurname() != null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
								this_series.get(i).getPlayer().getSurname() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
								"" + "\0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "S/R" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
					switch (TypeofProfile.toUpperCase()) {
					case "PT20CAREER":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "SHER-E-PUNJAB T20 CAREER" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
						
						if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
						}
						if(stats.getBestScore() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
									"-" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
									stats.getBestScore() + "\0");
						}
						break;
					case "PT20SEASON1":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "SHER-E-PUNJAB SEASON 1" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
						
						if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
						}
						if(stats.getBestScore() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
									"-" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
									stats.getBestScore() + "\0");
						}
						break;
					case "THISSERIES":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "THIS SEASON" + "\0");
						System.out.println(this_series.get(i).getInnings());
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getRuns() + "\0");
						
						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							strike_rate = this_series.get(i).getRuns() * 100;
							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
						}
						for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
							
							if(top_batsman_beststats.get(j).getPlayerId() == Playerid) {
								if(k == 0) {
									k += 1;
									if(top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												top_batsman_beststats.get(j).getBestEquation()/2 + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
												(top_batsman_beststats.get(j).getBestEquation()-1) / 2 + "*" + "\0");
									}
									break;
								}
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
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
		
		System.out.println("TypeofProfile.toUpperCase() : "+TypeofProfile.toUpperCase());
		
		Collections.sort(top_batsman_beststats, new CricketFunctions.PlayerBestStatsComparator());
		Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
		
		
		for(int i = 0; i <= this_series.size() - 1 ; i++) {
			if(this_series.get(i).getPlayerId() == Playerid) {
				
				if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
						match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
				}
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + 
						this_series.get(i).getPlayer().getFirstname() + "\0");
				
				if(this_series.get(i).getPlayer().getSurname() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
							"" + "\0");
				}
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON." + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
				
				switch (TypeofProfile.toUpperCase()) {
				case "PT20CAREER":
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "SHER-E-PUNJAB T20 CAREER" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
					if(stats.getBallsBowled() == 0 || stats.getRunsConceded() == 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
					}else {
						economy_rate = (stats.getRunsConceded()*1.00) /stats.getBallsBowled();
						economy_rate = economy_rate * 6;
						DecimalFormat df = new DecimalFormat("0.00");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
					}
					if(stats.getBestFigures() != null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
								stats.getBestFigures() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
								"-" + "\0");
					}
					break;
				case "PT20SEASON1":
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "SHER-E-PUNJAB SEASON 1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
					if(stats.getBestFigures() != null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
								stats.getBestFigures() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
								"-" + "\0");
					}
					break;
				case "THISSERIES":
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "THIS SEASON" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getWickets() + "\0");
					if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
					}else {
						economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
						economy_rate = economy_rate * 6;
						DecimalFormat df = new DecimalFormat("0.00");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
					}
					for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
						
						if(top_bowler_beststats.get(j).getPlayerId() == Playerid) {
							if(k == 0) {
								k += 1;
								if(top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
											((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
								}
								else if(top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
											(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
								}
								break;
							}
						}else if(top_bowler_beststats.get(j).getPlayerId() != Playerid) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
						}
					}
					break;
				}
			}

		}
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 \0");	
		

	}
}
	
	public void populateBugTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster)
	{
		this.status = CricketUtil.SUCCESSFUL;
		switch (broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + " " + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + CricketFunctions.GetTargetData(match).getRemaningBall() + "\0");						
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
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
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
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$All_Name$SubHead*GEOM*TEXT SET " + " " + "\0");								

							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + " " + "\0");								
							/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
							} else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getTeamName1() + CricketUtil.PNG_EXTENSION + ";");
							}*/
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead02*GEOM*TEXT SET " + "RUNS" + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead03*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + "BALLS" + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1c*GEOM*TEXT SET " + bc.getBalls() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + "S/R" + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + "FOURS" + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + bc.getFours() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " + "SIXES" + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " + bc.getSixes() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue5$StatValue1B*GEOM*TEXT SET " + " " + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue5$StatValue1B*GEOM*TEXT SET " + " " + "\0");
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
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead02*GEOM*TEXT SET " + boc.getOvers() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1A*GEOM*TEXT SET " + "DOTS" + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + boc.getDots() + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + boc.getRuns() + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getWickets() + "\0");								
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
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
			int total_inn = 0;
			this.status = CricketUtil.SUCCESSFUL;
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
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead02*GEOM*TEXT SET " + boc.getOvers() + "\0");								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + boc.getDots() + "\0");								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + boc.getRuns() + "\0");								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getWickets() + "\0");								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
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
						for(BattingCard bc : inn.getBattingCard()) {
							if(playerId == bc.getPlayerId()) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vIcon" + " SET " + "1" + "\0");
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + bc.getPlayer().getFirstname()+ "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + "" + "\0");
								}
								if(bc.getStatus().equals(CricketUtil.OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + bc.getRuns() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + bc.getRuns()+ "*" + "\0");
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + bc.getBalls() + "\0");
								if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgTeamLogo" + " SET " +
											logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgTeamLogo" + " SET " +
											logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
								}
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgImage" + " SET " +
											photo_path + inn.getBatting_team().getTeamName4().toUpperCase()+"\\" + bc.getPlayer().getPhoto() + ".png" + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress() + local_photo_path +  inn.getBatting_team().getTeamName4().toUpperCase()+"\\"+
											bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgImage" + " SET " +
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + inn.getBatting_team().getTeamName4().toUpperCase()+ "\\\\" + bc.getPlayer().getPhoto() + ".png" + "\0");
								}
							}
						}
						
						break;
					case "BOWLER":
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(playerId == boc.getPlayerId()) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vIcon" + " SET " + "2" + "\0");
								if(boc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + boc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + boc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + boc.getPlayer().getFirstname()+ "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + "" + "\0");
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + boc.getWickets() +"-"+ boc.getRuns() + "\0");
								
								if(Double.valueOf(CricketFunctions.getOvers(boc.getOvers(), boc.getBalls()))<=1) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " +CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls())  + " OVER" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " +CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " OVERS" + "\0");
								}
								
								if(inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgTeamLogo" + " SET " +
											logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgTeamLogo" + " SET " +
											logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
								}
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgImage" + " SET " +
											photo_path + inn.getBowling_team().getTeamName4().toUpperCase()+"\\" + boc.getPlayer().getPhoto() + ".png" + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress() + local_photo_path +  inn.getBowling_team().getTeamName4().toUpperCase()+"\\"+
											boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "+ "lgImage" + " SET " +
											"\\\\\\\\"+config.getPrimaryIpAddress()+local_photo_path + inn.getBowling_team().getTeamName4().toUpperCase()+ "\\\\" + boc.getPlayer().getPhoto() + ".png" + "\0");
								}
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.660 In$Data1In 1.700 \0");
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
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$MaxSize$PlayerName*GEOM*TEXT SET "+ bc.getPlayer().getFirstname() + " \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$text$Row1$RowAnimation$noname$StatValue*GEOM*TEXT SET "+ bc.getBatterPosition() + " \0");
							
							if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
												match.getSetup().getHomeTeam().getTeamName1() + ".png" + "\0");
								
							}
							else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$TeamBadge*TEXTURE*IMAGE SET " + logo_path + 
												match.getSetup().getAwayTeam().getTeamName1() + ".png" + "\0");
							}
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$All_Player_Pic$noname$Plaer_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
										photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +bc.getPlayer().getFirstname() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$All_Player_Pic$noname$Plaer_Pic$PlayerImage*TEXTURE*IMAGE SET " + 
										"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + bc.getPlayer().getFirstname() + ".png" + "\0");
							}
						}
					}
				}
			}
				
		}
	}
	public void populateLtGriff(PrintWriter print_writer, String viz_scene_path, String profile, int playerId, CricketService cricketService,List<MatchAllData> tournament_matches,MatchAllData match, String selectedBroadcaster) {
		Player player = cricketService.getAllPlayer().stream().filter(plyr ->plyr.getPlayerId() == playerId).findAny().orElse(null);
		List<BatBallGriff> griff = new ArrayList<BatBallGriff>();
		String cont_name = "";
		int omo_num = 0;
		int row = 0;
		this.status = CricketUtil.SUCCESSFUL;
		switch (profile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			griff = CricketFunctions.getBatBallGriffData(CricketUtil.BATSMAN,playerId, cricketService.getTeams(), tournament_matches, match);
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
			
			if(player.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + player.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + player.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + player.getFirstname() + "\0");
			}
			for(BatBallGriff grif : griff) {
				row++;
				
				if(griff.get(row-1).getMatchNumber().equalsIgnoreCase(match.getMatch().getMatchFileName().replace(".json", ""))){
					cont_name = "$Highlight";
					omo_num= 1;
				}else {
					cont_name = "$Dehighlight";
					omo_num= 0;
				}
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$BatRow"+row+cont_name+"$Impact*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
						"$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
						"$RowAnimation$BatOmo"+cont_name+"$BatPlayerName*GEOM*TEXT SET " + "v " + grif.getOpponentTeam().getTeamName3() + "\0");
				
				
				if(griff.get(row-1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
					if(griff.get(row-1).getHow_out() != null && !griff.get(row-1).getHow_out().trim().isEmpty() && 
							griff.get(row-1).getHow_out().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
								"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Runs*GEOM*TEXT SET " + griff.get(row-1).getRuns() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
								"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Balls*GEOM*TEXT SET " + griff.get(row-1).getBallsFaced() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
								"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Runs*GEOM*TEXT SET " + "" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
								"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Balls*GEOM*TEXT SET " + "DNB" + "\0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}else if(griff.get(row-1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Runs*GEOM*TEXT SET " + griff.get(row-1).getRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Balls*GEOM*TEXT SET " + griff.get(row-1).getBallsFaced() + "\0");
				}else if(griff.get(row-1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Runs*GEOM*TEXT SET " + griff.get(row-1).getRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Balls*GEOM*TEXT SET " + griff.get(row-1).getBallsFaced() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Runs*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo"+cont_name+"$ScoreGrp$Balls*GEOM*TEXT SET " + "DNP" + "\0");
				}
			}
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + griff.size() + "\0");
			break;

		case CricketUtil.BOWLER:
			griff = CricketFunctions.getBatBallGriffData(CricketUtil.BOWLER,playerId, cricketService.getTeams(), tournament_matches, match);
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
			
			if(player.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + player.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + player.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + ""+ "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + player.getFirstname() + "\0");
			}
			
			for(BatBallGriff grif : griff) {
				row++;
				if(griff.get(row-1).getMatchNumber().equalsIgnoreCase(match.getMatch().getMatchFileName().replace(".json", ""))){
					cont_name = "$Highlight";
					omo_num= 1;
				}else {
					cont_name = "$Dehighlight";
					omo_num= 0;
				}
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$BatRow"+row+cont_name+"$Impact*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + 
						row + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
						"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET  "+"v " + grif.getOpponentTeam().getTeamName3() + "\0");
				if(griff.get(row-1).getStatus().equalsIgnoreCase("DNB")) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + "DNB"+ "\0");
				}else if(griff.get(row-1).getStatus().equalsIgnoreCase("BALL")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + grif.getWickets() + slashOrDash + grif.getRunsConceded() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + grif.getOversBowled()+ "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row + 
							"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + "DNP"+ "\0");
				}
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + griff.size() + "\0");
			break;
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/Preview.png In 1.180 BatDataIn 1.180 \0");
	}
	public void populateLtPointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> point_table,List<Team> team, MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		this.status = CricketUtil.SUCCESSFUL;
		int row_id=0,omo_num = 0;
		String cont_name = "";
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "POINTS TABLE" + " \0");
		
		for(int i = 0; i <= point_table.size() - 1 ; i++) {
			row_id = row_id + 1;
			
			if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())  
					|| match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) 
					|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
				omo_num = 1;
				cont_name = "$Highlight";
			}else {
				omo_num = 0;
				cont_name = "$Dehighlight";
			}
			//System.out.println(point_table.get(i).getTeamName().toUpperCase());
			//System.out.println(omo_num);
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + 
					"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
			
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + 
						"$RowAnimation$Qualified*ACTIVE SET 0 \0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + 
						"$RowAnimation$Qualified*ACTIVE SET 1 \0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
					+ cont_name + "$TextAll$Rank*GEOM*TEXT SET "+ (i+1) + "." + " \0");
			
			for(Team tm : team) {
				if(tm.getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
							+ cont_name + "$TextAll$PoinTeamName*GEOM*TEXT SET "+ tm.getTeamName3().toUpperCase() + " \0");
				}
			}
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
					+ cont_name + "$TextAll$TextGrp$PlayedValue*ACTIVE SET "+ "1" + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$PlayedValue*GEOM*TEXT SET "+ point_table.get(i).getPlayed() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
					+ cont_name + "$TextAll$TextGrp$WinValue*ACTIVE SET "+ "1" + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$WinValue*GEOM*TEXT SET "+ point_table.get(i).getWon() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow0$RowAnimation$TeamNameAll" 
					 + "$TextGrp$PointsHead*GEOM*TEXT SET "+ "L" + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
					+ cont_name + "$TextAll$TextGrp$PointsValue*ACTIVE SET "+ "1" + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$PointsValue*GEOM*TEXT SET "+ point_table.get(i).getLost() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow0$RowAnimation$TeamNameAll" 
				 + "$TextGrp$NRRHead*GEOM*TEXT SET "+ "PTS" + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
					+ cont_name + "$TextAll$TextGrp$NRRValue*ACTIVE SET "+ "1" + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$BattingCardAll$BatData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$NRRValue*GEOM*TEXT SET "+ point_table.get(i).getPoints() + " \0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 0.496 BatDataIn 0.350 PointsTableIn 0.930 PointsOffsetIn 0.930 \0");
			
	}
	
	public void populateProjectedScore(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: ProjectedScore's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");
			
			String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
		    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
		    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
	        }
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ inn.getBatting_team().getTeamName3().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " +"PROJECTED SCORES" + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "@"+ proj_score_rate[0] +" (CRR)" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + proj_score_rate[1] + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "@" + proj_score_rate[2] +" RPO" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + proj_score_rate[3] + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "@" + proj_score_rate[4] +" RPO" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + proj_score_rate[5] + "\0");
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
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
							 // " TO WIN FROM " +  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + "\0");
					
					if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(match.getSetup().getMaxOvers()*6 >= 100) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
									 " TO WIN FROM "+  match.getSetup().getMaxOvers() + " OVERS" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
									 " TO WIN FROM "+  match.getSetup().getMaxOvers()*6 + " BALLS" + "\0");
						}
						
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) != 0) {
							if(match.getSetup().getTargetOvers().contains(".")) {
								if((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) >= 100) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + 
											CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + " TO WIN FROM " + (Integer.valueOf(match.getSetup().getTargetOvers()
													.split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) + " BALLS" + "\0");
								}
							}else {
								if(Double.valueOf(match.getSetup().getTargetOvers())*6 >= 100) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS" + "\0");
								}
							}
						}
						if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
							if(match.getSetup().getTargetOvers().contains(".")) {
								if((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) >= 100) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (VJD)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  (Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) + " BALLS (VJD)" + "\0");
								}
							}else {
								if(Double.valueOf(match.getSetup().getTargetOvers())*6 >= 100) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (VJD)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Integer.valueOf(match.getSetup().getTargetOvers())*6 + " BALLS (VJD)" + "\0");
								}
							}
						}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
							if(match.getSetup().getTargetOvers().contains(".")) {
								if((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) >= 100) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (DLS)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  (Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1]) + " BALLS (DLS)" + "\0");
								}
							}else {
								if(Double.valueOf(match.getSetup().getTargetOvers())*6 >= 100) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
											 " TO WIN FROM "+  Double.valueOf(match.getSetup().getTargetOvers()) + " OVERS (DLS)" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + " NEED " + CricketFunctions.GetTargetData(match).getTargetRuns() + " RUNS" + 
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + " " + "\0");								

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, whichInning, 0, ",", match.getEventFile().getEvents()).split(",");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");								
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$noname$FOW*ACTIVE SET " + "0" + "\0");								

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1A SET " + "0s" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1B SET " + Count[0] + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2B SET " + Count[1] + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3B SET " + Count[2] + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4B SET " + Count[3] + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue5B SET " + inn.getTotalFours() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue6B SET " + inn.getTotalSixes() + "\0");

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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + " " + "\0");								

			for(Inning inn : match.getMatch().getInning()) {
				for(BattingCard bc : inn.getBattingCard()) {
					if (inn.getInningNumber() == whichInning) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

							if(PlayerId == bc.getPlayerId()) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$noname$FOW*GEOM*TEXT SET " + "BATTING SUMMARY" + "\0");								

								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "*" + "\0");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + bc.getBalls() + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1A SET " + "0s" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1B SET " + Count[0] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2B SET " + Count[1] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3B SET " + Count[2] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4B SET " + Count[3] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue5B SET " + Count[4] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue6B SET " + Count[6] + "\0");
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
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + " " + "\0");								
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "BOWLING SUMMARY" + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BOWLER,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + inn.getBowling_team().getTeamName4().toUpperCase() + "\0");

							if(PlayerId == boc.getPlayerId()) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");								
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1A SET " + "0s" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue1B SET " + Count[0] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue2B SET " + Count[1] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue3B SET " + Count[2] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue4B SET " + Count[3] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue5B SET " + Count[4] + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON tStatValue6B SET " + Count[6] + "\0");
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");
	
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET "+ CricketFunctions.getTeamScore(inn, slashOrDash, false) + " \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET "+ CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " \0");
	
					if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$FOW$BottomLine$noname*ACTIVE SET 0" + " \0");
					}
					else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
						for(FallOfWicket fow : inn.getFallsOfWickets()) {								
							if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$FOW$BottomLine$noname*ACTIVE SET 1" + " \0");
								for(int fow_id=1;fow_id<=10;fow_id++) {
									if(fow_id <= inn.getFallsOfWickets().size()) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow.getFowNumber() + "A" + " SET " + fow.getFowNumber() + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow.getFowNumber() + "B" + " SET " + fow.getFowRuns() + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow_id + "A" + " SET " + " " + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow_id + "B" + " SET " + " " + "\0");
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET " + 
							CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");

					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 30 || inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 50) {
						if(splitValue == 30) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER THIRTY" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER FIFTY" + "\0");
						}

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$HeadValue1$Dehiglight$StatHead1*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + " \0");
					
					} else {
						if(splitValue == 30) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER THIRTY" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER FIFTY" + "\0");
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$HeadValue1$Dehiglight$StatHead1*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + "\0");
						
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$noname*ACTIVE SET 0" + "\0");
					
				    for (int i = 0; i < CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size(); i++) {
				    	
				    	int row_id = i + 1;
				    	for(int split_id=1;split_id<=6;split_id++) {
					    	if(split_id <= CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size()) {
					    		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$noname*ACTIVE SET 1" + "\0");
					    		if(row_id==1) {
					    			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"st"+ "\0");
					    		}else if(row_id==2){
					    			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"nd"+ "\0");
					    		}else if(row_id==3) {
					    			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"rd"+ "\0");
					    		}else {
					    			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "A" + " SET " + row_id +"th"+ "\0");
					    		}
							
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "B" + " SET " + 
										CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).get(i) + "\0");
					    	}
					    	else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + split_id + "A" + " SET " + " " + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + split_id + "B" + " SET " + " " + "\0");
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");

			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamRefName" + " SET " + logo_path + inn.getBowling_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamFirstName" + " SET " + inn.getBowling_team().getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamLastName" + " SET " + inn.getBowling_team().getTeamName3().toUpperCase()  + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamRefName" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamFirstName" + " SET " + inn.getBatting_team().getTeamName2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamLastName" + " SET " + inn.getBatting_team().getTeamName3().toUpperCase() + "\0");

					if(inn.getTotalOvers() == 1 && inn.getTotalBalls() == 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "AFTER " + 
								CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVER" + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "AFTER " + 
								CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamScore" + " SET " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamScore" + " SET " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + "\0");

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
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					
					String Left_Batsman ="",Right_Batsman="";
					
					Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getFull_name();
					Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getFull_name();
					
					if (CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$Partnership$Row1$Impact*ACTIVE SET 1 \0");
					} else if (CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(),inn.getInningNumber(), inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$Row1$Impact*ACTIVE SET 1 \0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$Partnership$Row1$Impact*ACTIVE SET 0 \0");
					}
					if (CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$Partnership$Row1$Impact2*ACTIVE SET 1 \0");
					} else if (CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(),inn.getInningNumber(), inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$Row1$Impact2*ACTIVE SET 1 \0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$Partnership$Row1$Impact2*ACTIVE SET 0 \0");
					}
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
								inn.getBatting_team().getTeamName4() + "\\\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + ".png" + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + inn.getBatting_team().getTeamName4() + "\\" + 
								inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
								inn.getPartnerships().get(inn.getPartnerships().size()-1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + photo_path + 
								inn.getBatting_team().getTeamName4() + "\\\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + ".png" + "\0");
					}else {
						if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + inn.getBatting_team().getTeamName4() + 
								"\\" + inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + "\\\\\\\\" + 
							config.getPrimaryIpAddress() + local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + 
								inn.getPartnerships().get(inn.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " 
							+ inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " 
							+ "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
					+ "" + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$PartnershipData$Runs$Alignment$PartnershipScore*GEOM*TEXT SET " 
											+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$PartnershipData$Balls$Alignment$PartnershipBalls*GEOM*TEXT SET " 
											+ inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row1$RowAnimation$RowData$PlayerName1*GEOM*TEXT SET " + Left_Batsman + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row1$RowAnimation$RowData$PlayerName2*GEOM*TEXT SET " + Right_Batsman + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore1$PlayerContributionRuns1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore1$PlayerContributionBalls1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore2$PartnershipRun*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row2$RowAnimation$RowData$PlayerScore2$PartnershipBalls*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row3$RowAnimation$Highlight$Alignment$Fours$PlayerBalls1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalFours() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BatDataGrp$Row3$RowAnimation$Highlight$Alignment$Sixes$PlayerBalls1*GEOM*TEXT SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalSixes() + "\0");

					if(inn.getTotalWickets() == 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
//												+ (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 1) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
//												+ (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 2) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
//												+ (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " 
								+ (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " 
//												+ (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
					}
				}
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

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			
			switch(StatType.toUpperCase()) {
			case "MOST_RUNS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST RUNS " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {
						
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
										+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getRuns() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			case "MOST_WICKETS":
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST WICKETS " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + team.get(tournament.get(i).getPlayer().
										getTeamId() - 1).getTeamName4() + "\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
										+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getWickets() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			case "MOST_FOURS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST FOURS " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + team.get(tournament.get(i).getPlayer().
										getTeamId() - 1).getTeamName4() + "\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
										+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								
							}
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getFours() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
					}
				}
				break;
			case "MOST_SIXES":
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST SIXES " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\" + config.getPrimaryIpAddress() + "\\c\\Images\\Punjab_Cup_2023\\Photos\\" + team.get(tournament.get(i).getPlayer().
										getTeamId() - 1).getTeamName4() + "\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
										+ "\\\\" + tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 0 + "\0");
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getSixes() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + " " + "\0");
			
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					
					if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
							if(match.getMatch().getMatchStatus() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo"+ "\0");
									}
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " +
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo"+ "\0");
									}
								}
								else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " +
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo"+ "\0");
									}
								}
							}
							
						}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
								|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo"+ "\0");
									}
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " +
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo"+ "\0");
									}
								}
								else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " +
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
									if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
									}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
												"TLogo"+ "\0");
									}
								}
							}
						}
						
						else{
							if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET "  
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
							}
							
						}
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " +
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
								}
								
							}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
								
								if(match.getMatch().getMatchStatus() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
								}
							}
							
							else{
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");									
							}
						}
						else {
							if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
								}
							}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| Double.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), match.getMatch().getInning().get(1).getTotalBalls())) 
									>= Double.valueOf(match.getSetup().getTargetOvers())) {
								if(match.getMatch().getMatchStatus() != null) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + "RESULT" + "\0");
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");	
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
									else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
										if(match.getMatch().getMatchStatus().contains(inn.getBatting_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
										}else if(match.getMatch().getMatchStatus().contains(inn.getBowling_team().getTeamName1().toUpperCase())) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													inn.getBowling_team().getTeamName4().toUpperCase() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
													"TLogo"+ "\0");
										}
									}
								}
							}
							else{
								if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
												+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
												" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (VJD)" + "\0");
								}
								else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET "  
												+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
												" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (DLS)" + "\0");
								}
								else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
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
	public void populatePointsTable(PrintWriter print_writer,String viz_scene,List<LeagueTeam> point_table, String broadcaster,MatchAllData match, List<VariousText> vt) throws InterruptedException 
	{
		this.status = CricketUtil.SUCCESSFUL;
		int row_id=0,omo_num = 0;
		String cont_name = "";
		DecimalFormat df = new DecimalFormat("0.000");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpactLegend" + " SET " + "0" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead1" + " SET " + " " + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$PointsLeftLogoGrp*ACTIVE SET 1 \0");
		//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead1" + " SET " + "GROUP - " + point_table.get(0).getPool().trim() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead2" + " SET " + "POINTS TABLE" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$BottomInfoGrp$RowAnimation$PointsInfo*GEOM*TEXT SET "+ 
				" " + " \0");

		for (VariousText vartext : vt) {
			if (vartext.getVariousType().equalsIgnoreCase("POINTSTABLEFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
						+ "tPointsInfo" + " SET " + vartext.getVariousText() + "\0");
			} else if (vartext.getVariousType().equalsIgnoreCase("POINTSTABLEFOOTER")&& vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON "
						+ "tPointsInfo" + " SET " + "TOP FOUR TEAMS TO QUALIFY FOR PLAY-OFFS" + "\0");
			}
		}
		
		for(int i = 0; i <= point_table.size() - 1 ; i++) {
			row_id = row_id + 1;
			
			if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())  
					|| match.getSetup().getHomeTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase()) 
					|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())
					|| match.getSetup().getAwayTeam().getTeamName4().toUpperCase().equalsIgnoreCase(point_table.get(i).getTeamName().toUpperCase())) {
				omo_num = 1;
				cont_name = "$Highlight";
			}else {
				omo_num = 0;
				cont_name = "$Dehighlight";
			}
			//System.out.println(point_table.get(i).getTeamName().toUpperCase());
			//System.out.println(omo_num);
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + 
					"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
			
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$PointsTableAll$PointsDataGrp$PointRow" + row_id + 
						"$Qualified*ACTIVE SET 0 \0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$PointsTableAll$PointsDataGrp$PointRow" + row_id + 
						"$Qualified*ACTIVE SET 1 \0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
					+ cont_name + "$TextAll$Rank*GEOM*TEXT SET "+ (i+1) + "." + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$PoinTeamName*GEOM*TEXT SET "+ point_table.get(i).getTeamName().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$PlayedValue*GEOM*TEXT SET "+ point_table.get(i).getPlayed() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$WinValue*GEOM*TEXT SET "+ point_table.get(i).getWon() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$LossValue*GEOM*TEXT SET "+ point_table.get(i).getLost() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow0$RowAnimation$TeamNameAll" 
									+ "$TextGrp$NrHead*GEOM*TEXT SET "+ "T/NR" + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$NrValue*GEOM*TEXT SET "+ point_table.get(i).getNoResult() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow0$RowAnimation$TeamNameAll" 
									+ "$TextGrp$PointsHead*GEOM*TEXT SET "+ "PTS" + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$PointsValue*GEOM*TEXT SET "+ point_table.get(i).getPoints() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo" 
									+ cont_name + "$TextAll$TextGrp$NRRValue*GEOM*TEXT SET "+ df.format(point_table.get(i).getNetRunRate()) + " \0");
		}
		
		if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || 
				which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || which_graphic_on_screen == "PARTNERSHIP") {	
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PointsTable*ACTIVE SET " + "0" + "\0");
			if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.370 PointsOffsetIn 1.370 BattingCardOut 0.500 \0");
			}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.370 PointsOffsetIn 1.370 BowlingCardOut 0.500 \0");
			}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.370 PointsOffsetIn 1.370 SummaryOut 0.500 \0");
			}else if(which_graphic_on_screen == "PARTNERSHIP") {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 PointsTableIn 1.370 PointsOffsetIn 1.370 PartOut 0.500 \0");
			}
		}else{
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 PointsTableIn 0.930 PointsOffsetIn 0.930 \0");
		}
	}
	public void populateBowlerStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId,List<Player> plyr, List<Team> team,List<Ground> ground, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			//String Home_or_Away="";
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName4().toUpperCase() + "\0");
			
			if(plyr.get(playerId - 1).getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
			}
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + team.get(plyr.get(playerId-1).getTeamId()-1).getTeamName1() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp*ACTIVE SET 1\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname*ACTIVE SET 1\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*ACTIVE SET 1\0");
			if(plyr.get(playerId - 1).getBowlingStyle() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + 
						CricketFunctions.getbowlingstyle(plyr.get(playerId - 1).getBowlingStyle()).toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");
			TimeUnit.MILLISECONDS.sleep(1000);	
		}
			
	}	
	public void populateTieIdDouble(PrintWriter print_writer,String viz_sence_path,String day,List<Fixture> fix,List<Team>team,MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 1;
			String Date = "",newDate = "";
			Calendar cal = Calendar.getInstance();
			
			String[] dateSuffix = {
					"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
					
					"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
					
					"th", "st", "nd", "rd", "th", "th", "th", "th", "th","th",
					
					"th", "st"
			};
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + match.getSetup().getTournament() + "\0");
			if(day.toUpperCase().equalsIgnoreCase("TODAY")) {
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "TODAY'S MATCHES " + "\0");
			}
			else if(day.toUpperCase().equalsIgnoreCase("TOMORROW")) {
				cal.add(Calendar.DATE, + 1);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "TOMORROW'S MATCHES " + "\0");
			}else if(day.toUpperCase().equalsIgnoreCase("DAY_AFTER_TOMORROW")) {
				cal.add(Calendar.DATE, + 2);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				
				newDate = Date.split("-")[0];
				if(Integer.valueOf(newDate) < 10) {
					newDate = newDate.replaceFirst("0", "");
				}
				System.out.println(newDate);
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + newDate +
						dateSuffix[Integer.valueOf(newDate)] + " " +Month.of(Integer.valueOf(Date.split("-")[1])) + "\0");
			}
			for(int i = 0; i <= fix.size()-1; i++) {
				if(fix.get(i).getDate().equalsIgnoreCase(Date)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgHomeTeamLogo0" + row_id + " SET " + logo_path + team.get(fix.get(i).getHometeamid()-1).getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName0" + row_id + " SET " + team.get(fix.get(i).getHometeamid()-1).getTeamName1().toUpperCase() + "\0");
					
					if(day.toUpperCase().equalsIgnoreCase("TODAY")) {
						if(fix.get(i).getMatchnumber()<10) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchNumber0" + row_id + " SET " + 
									"MATCH "+ fix.get(i).getMatchnumber() + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchNumber0" + row_id + " SET " + 
									fix.get(i).getMatchfilename()+ "\0");
						}
					}
					else if(day.toUpperCase().equalsIgnoreCase("TOMORROW")) {
						if(fix.get(i).getMatchnumber()<10) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchNumber0" + row_id + " SET " + 
									"MATCH "+fix.get(i).getMatchnumber() + " - " + fix.get(i).getLocalTime() + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchNumber0" + row_id + " SET " + 
									fix.get(i).getMatchfilename() + " - " + fix.get(i).getLocalTime() + "\0");
						}
						
					}else if(day.toUpperCase().equalsIgnoreCase("DAY_AFTER_TOMORROW")) {
						if(fix.get(i).getMatchnumber()<10) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchNumber0" + row_id + " SET " + 
									"MATCH "+fix.get(i).getMatchnumber() + " - " + fix.get(i).getLocalTime() + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tMatchNumber0" + row_id + " SET " + 
									fix.get(i).getMatchfilename() + " - " + fix.get(i).getLocalTime() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgAwayTeamLogo0" + row_id + " SET " + logo_path + team.get(fix.get(i).getAwayteamid()-1).getTeamName4().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName0" + row_id + " SET " + team.get(fix.get(i).getAwayteamid()-1).getTeamName1().toUpperCase() + "\0");

					row_id = row_id +1;
				}
			}
			
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.png In 3.100 \0");
			
		}
		
	}
	public void populatePreviousSummary(PrintWriter print_writer, String viz_scene, int match_number,MatchAllData mtch,List<Fixture> fix, 
			MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Match Summary's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			
			int row_id = 0, max_Strap = 0,bat_impact_count=0,ball_impact_count=0;
			String teamname = "";
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatImpactLegend" + " SET " + "1" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$Left$LeaftBaseGrp$AllLeftTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$Bands$RightTeamLogos$SummaryLeftLogoGrp$SummaryLeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			
			for(int i = 1; i <= 2 ; i++) {

				if(i == 1) {
					row_id = 0;
					max_Strap = 5;
					bat_impact_count = 0;
					ball_impact_count = 0;
					if(mtch.getMatch().getInning().get(i-1).getBattingTeamId() == mtch.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 0 \0");
					
				} else {
					row_id = 5;
					max_Strap = 10;
					bat_impact_count = 4;
					ball_impact_count = 4;
					
					if(mtch.getMatch().getInning().get(i-1).getBattingTeamId() == mtch.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + (row_id + 1) + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow7*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow8*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow9*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow10*ACTIVE SET 1 \0");
				}
				row_id = row_id + 1;
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " +
						mtch.getSetup().getMatchIdent() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$Header$SubHeader*GEOM*TEXT SET " +
						mtch.getSetup().getTournament().toUpperCase() + "\0");
				
				if(mtch.getMatch().getInning().get(i-1).getBattingTeamId() == mtch.getSetup().getHomeTeamId()) {
					teamname = mtch.getSetup().getHomeTeam().getTeamName1();
					//teamname_logo  = match.getHomeTeam().getTeamName4();
				} else {
					teamname = mtch.getSetup().getAwayTeam().getTeamName1();
					//teamname_logo = match.getAwayTeam().getTeamName4();
				}
				
				//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Rectangle*TEXTURE*IMAGE SET " + logo_path + 
						//teamname_logo + CricketUtil.PNG_EXTENSION + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamFirstName*ACTIVE SET " + "0" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
						"$RowAnimation$TeamNameAll$SummaryTeamNameGrp$SumTeamLastName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id +
						"$RowAnimation$TeamNameAll$SumTeamRuns*GEOM*TEXT SET " + CricketFunctions.getTeamScore(mtch.getMatch().getInning().get(i-1), 
								slashOrDash, false) + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
					"$RowAnimation$TeamNameAll$OversGrp$SumTeamOvers*GEOM*TEXT SET " + CricketFunctions.OverBalls(mtch.getMatch().getInning().get(i-1).getTotalOvers(),
								mtch.getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
				
				if(mtch.getMatch().getInning().get(i-1).getBattingCard() != null) {
					Collections.sort(mtch.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					
					for(BattingCard bc : mtch.getMatch().getInning().get(i-1).getBattingCard()) {
						if (bc.getRuns() > 0) {
							if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								row_id = row_id + 1;
								bat_impact_count = bat_impact_count + 1;
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
								
								if(CricketFunctions.checkImpactPlayer(mtch.getEventFile().getEvents(), i, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBatImpact" + bat_impact_count + " SET " + "1" + "\0");
								}else if(CricketFunctions.checkImpactPlayerBowler(mtch.getEventFile().getEvents(), i, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBatImpact" + bat_impact_count + " SET " + "1" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBatImpact" + bat_impact_count + " SET " + "0" + "\0");
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
										"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + bc.getRuns() + "\0");
								
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
											"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
								} else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
											"$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
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

				for(int k = row_id + 1; k <= max_Strap; k++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + k + "$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
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
							
							if(CricketFunctions.checkImpactPlayer(mtch.getEventFile().getEvents(), i, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBallImpact" + ball_impact_count + " SET " + "1" + "\0");
							}else if(CricketFunctions.checkImpactPlayerBowler(mtch.getEventFile().getEvents(), i, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBallImpact" + ball_impact_count + " SET " + "1" + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vSumBallImpact" + ball_impact_count + " SET " + "0" + "\0");
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$SumPlayerName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
									"$RowAnimation$BowlerGrp$TextAll$ScoreGrp$BatPlayerRun*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + row_id + 
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
				
				for(int k = row_id + 1; k <= max_Strap; k++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + k + "$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
				}
			}
			if(mtch.getMatch().getMatchResult() != null) {
				if(mtch.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
				}
				else if(mtch.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ "MATCH TIED" + "\0");
				}
				else if(mtch.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ mtch.getMatch().getMatchStatus().toUpperCase() + "\0");
				}
				else if(mtch.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ "MATCH TIED - " + CricketFunctions.GenerateMatchSummaryStatus(2, mtch, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
				}
				else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
				}
			}
			else {
				
				if(mtch.getSetup().getTargetType() == "") {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + "\0");
				}
				else if(mtch.getSetup().getTargetType() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$SummaryAll$SummaryData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET " 
							+ CricketFunctions.GenerateMatchSummaryStatus(2, mtch, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().replace("win", "won").toUpperCase() + " (VJD)" + "\0");
				}
			}
			TimeUnit.MILLISECONDS.sleep(200);	
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.100 SummaryIn 1.370 SummaryOffsetIn 1.370 \0");
		}
	}
	public void populateBatsmanStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, List<Player> plyr, List<Team> team, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName4().toUpperCase() + "\0");

			if(plyr.get(playerId - 1).getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
			}
			
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + team.get(plyr.get(playerId-1).getTeamId()-1).getTeamName1() + "\0");
			
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + team.get(plyr.get(playerId - 1).getTeamId() - 1).
					//getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp*ACTIVE SET 1\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname*ACTIVE SET 1\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*ACTIVE SET 1\0");
			if(plyr.get(playerId - 1).getBattingStyle() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + 
						CricketFunctions.getbattingstyle(plyr.get(playerId - 1).getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			}
			
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.000 \0");	
		TimeUnit.MILLISECONDS.sleep(999);	
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
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ " " + " \0");				
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + inn.getBatting_team().getTeamName4().toUpperCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ match.getSetup().getMatchIdent() + " \0");				

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ inn.getBatting_team().getTeamName1().toUpperCase() + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tExtrasValue" + " SET "  + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOversValue" + " SET "  + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET "  + CricketFunctions.getTeamScore(inn, slashOrDash, false) + "\0");
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
		 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$lines$PlayerNameGrp$Row" + (5 - i) + "$RowAni$Runs*GEOM*TEXT SET " + runsIncr*(i+1) + "\0");
			}
			
			for(int j = 1; j <= match.getSetup().getMaxOvers(); j++) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll$Bar" + j + "*ACTIVE SET 0" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$Wickets$Wkt" + j + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");

				//if(CricketFunctions.getOverByOverData(match, whichInning,match.getEvents()).get(j).getInningNumber() == whichInning) {
					
				if(j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size()) {
					lngth = ((35 *Integer.valueOf(
							CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns())) / maxRuns); // 32 is max value of each bar
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll$Bar" + j + "*ACTIVE SET 1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll*FUNCTION*BarValues*Bar_Value__" + j + " SET " + lngth + "\0");
				
					if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$Wickets$Wkt" + j + "*FUNCTION*Omo*vis_con SET " + Integer.valueOf(
								CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$Wickets$Wkt" + j + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
					}
				
				}
				else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Manhattan$Data$Man20$BarGrp$BarAll$Bar" + j + "*ACTIVE SET 0" + "\0");
				}
					
				//}
			}
		}
		//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In SHOW 2.700 \0");
		//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out SHOW 0.0 \0");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.700 BallOffsetIn 1.830 ManDataIn 2.700 DataIn 1.576\0");
		
			
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
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$Header$TeamNameGrp$FirstName*GEOM*TEXT SET "+ " " + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$Header$TeamNameGrp$LastName*GEOM*TEXT SET "+ match.getSetup().getMatchIdent() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$BottomInfoGrp$RowAnimation$Equations*GEOM*TEXT SET "+ 
					CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$TeamsAll$TeamDataData$Header$SubHeader*GEOM*TEXT SET "+ match.getSetup().getTournament().toUpperCase() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
					+ logo_path + "TLogo" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo" + "\0");

//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp1$Band*MATERIAL*COLOR SET 1.0 0.227450980392 0.0549019607843 \0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2$Band*MATERIAL*COLOR SET 1.0 0.827450980392 0.0 \0");
			
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
				 	print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$group$PlayerNameGrp$Row" + (5 - k) + 
				 			"$RowAni$Runs*GEOM*TEXT SET " + runsIncr *  (k + 1) + "\0");
				}
				
				row_id = row_id + 1;
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + 
						"$TextAll$TeamName*GEOM*TEXT SET "+ teamname + " \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$"
						+ "Score*GEOM*TEXT SET " + CricketFunctions.getTeamScore(match.getMatch().getInning().get(inn_count-1), slashOrDash, false) + " \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp" + row_id + "$TextAll$ScoreGrp$Overs*GEOM*TEXT SET "+ 
						CricketFunctions.OverBalls(match.getMatch().getInning().get(inn_count-1).getTotalOvers(), match.getMatch().getInning().get(inn_count-1).getTotalBalls()) + " \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXFit SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXFit SET 1 \0");

				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataYOffset SET 1.0 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataYOffset SET 1.0 \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXOffset SET 1.5 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXOffset SET 1.5 \0");
				
				Lngth =  (80.62 / maxRuns); // 100 is max value of each bar
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXScale" + " SET " + "1" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXScale" + " SET " + "1" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vDataScaleY" + " SET " + Lngth + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
						"*GEOM*DataY SET " + cumm_runs.replaceFirst("0,", "") + " \0");
				//System.out.println(cumm_runs);
				if(inn_count == 1) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 0 \0");
				}
				else {						
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Row2$RowAnimation$TeamGrp2*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 1 \0");
				}
				
				for (int j = 1; j <= match.getSetup().getMaxOvers(); j++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + "$Wkt" + 
							(j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
					//System.out.println("j = " + j);
					if(j < CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).size()) {
						if(CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
									"$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + Integer.valueOf(CricketFunctions.getOverByOverData
											(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
						}
						else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$WormAll$WormAll$WormGrp$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
									"$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
						}
					}
				}
			}	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.780 \0");
		
			
	}
	public void populateSchedule(PrintWriter print_writer,String viz_scene,List<Fixture> fixture,List<Team> team,MatchAllData match ,String broadcaster) throws ParseException {
		this.status = CricketUtil.SUCCESSFUL;
		int row_id = 0,omo_num=0;
		String Date = "",cont_name="";
		Calendar cal = Calendar.getInstance();
		Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + " " +"\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "SCHEDULE" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + match.getSetup().getTournament().toUpperCase() + "\0");
		
		for(int i=0;i < fixture.size();i++ ) {
			row_id = row_id + 1;
			if(fixture.get(i).getDate().equalsIgnoreCase(Date)) {
				omo_num=1;
				cont_name="$Highlight";		
			}else {
				omo_num=0;
				cont_name="$Dehighlight";
			}

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
			if(fixture.get(i).getMatchnumber() % 2 == 0) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
						"$RowAnimation$RowOmo" + cont_name + "$DateAll$TimeOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
						"$RowAnimation$RowOmo" + cont_name + "$DateAll$TimeOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo" + cont_name + "$DateAll$DateText*GEOM*TEXT SET " + fixture.get(i).getDate() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo" + cont_name + "$TextAll$NameAll$TeamName1*GEOM*TEXT SET " + team.get(fixture.get(i).getHometeamid()-1).getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo" + cont_name + "$TextAll$NameAll$TeamName2*GEOM*TEXT SET " + team.get(fixture.get(i).getAwayteamid()-1).getTeamName1().toUpperCase() + "\0");
			
			if(fixture.get(i).getWinnerteam() != null) {
				if(fixture.get(i).getWinnerteam().toUpperCase().equalsIgnoreCase(team.get(fixture.get(i).getHometeamid()-1).getTeamName4().toUpperCase())) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
							"$RowAnimation$RowOmo" + cont_name + "$TextAll$ResultOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
				}else if(fixture.get(i).getWinnerteam().toUpperCase().equalsIgnoreCase(team.get(fixture.get(i).getAwayteamid()-1).getTeamName4().toUpperCase())) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
							"$RowAnimation$RowOmo" + cont_name + "$TextAll$ResultOmo*FUNCTION*Omo*vis_con SET " + "2" + "\0");
				}
			}	
			else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
						"$RowAnimation$RowOmo" + cont_name + "$TextAll$ResultOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
			}
		}
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "TOP FOUR TEAMS QUALIFY FOR THE SEMIS" + "\0");
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 2.474 In$DataIn 1.750 \0");
		
	}
	
	public void populateMiniBattingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: mini batting card inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, omo_num = 0,batting_size=0;
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + inn.getBatting_team().getTeamName2() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBatting_team().getTeamName3() + "\0");

					
					Collections.sort(inn.getBattingCard());
					
					for (BattingCard bc : inn.getBattingCard()) {
						
						row_id = row_id + 1;
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
							if(bc.getHowOut() != null) {
								if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
									batting_size+=1;
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
									batting_size+=1;
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo$Dehighlight$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
											"$RowAnimation$BatOmo$Dehighlight$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								}
							}
							break;
						default:
							switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.OUT:
								omo_num = 0;
								cont_name = "$Dehighlight";
								batting_size = batting_size + 1;
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
								break;
							case CricketUtil.NOT_OUT:
								omo_num = 1;
								cont_name = "$Highlight";
								batting_size = batting_size + 1;
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
								break;
							}
							if (CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$BatRow"+row_id+cont_name+"$Impact*ACTIVE SET 1 \0");
							} else if (CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(),whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$BatRow"+row_id+cont_name+"$Impact*ACTIVE SET 1 \0");
							} else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$BatRow"+row_id+cont_name+"$Impact*ACTIVE SET 0 \0");
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo$Dehighlight$ScoreGrp*ACTIVE SET 1 \0");
							
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
									"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
						}
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 BatDataIn 1.180 \0");
			
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
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + "" + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + inn.getBowling_team().getTeamName2() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBowling_team().getTeamName3() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBowlingCard().size() + "\0");
					
					
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
						if (CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$BatRow"+row_id+cont_name+"$Impact*ACTIVE SET 1 \0");
						} else if (CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(),whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$BatRow"+row_id+cont_name+"$Impact*ACTIVE SET 1 \0");
						} else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$BatData$BatRow"+row_id+cont_name+"$Impact*ACTIVE SET 0 \0");
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + 
								row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");
	
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$BatPlayerName*GEOM*TEXT SET  " + boc.getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Runs*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id + 
								"$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");

					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.180 BatDataIn 1.180 \0");
			
		}
			
	}
//	public void populateThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster) {
//		
//		if (match == null) {
//			this.status = "ERROR: Match is null";
//		} else {
//			System.out.println("PLAYER ID "+Playerid);
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
//			System.out.println("HEL"+this_series.get(0).getBatsman_best_Stats().get(0).getPlayerId());
//			
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + " " + "\0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + "THIS SERIES" + "\0");
//			
//			for(int i = 0; i <= this_series.size() - 1 ; i++) {
//				if(this_series.get(i).getPlayerId() == Playerid) {
//					
//					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
//							match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
//					}else {
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + 
//							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
//					}
//					
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + 
//							this_series.get(i).getPlayer().getFirstname() + "\0");
//					
//					if(this_series.get(i).getPlayer().getSurname() != null) {
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
//								this_series.get(i).getPlayer().getSurname() + "\0");
//					}else {
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$LT02$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + 
//								"" + "\0");
//					}
//					
//					switch(TypeofProfile.toUpperCase()) {
//					case CricketUtil.BATSMAN:
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
//
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getRuns() + "\0");
//						
//						
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "S/R" + "\0");			
//						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
//						}else {
//							strike_rate = this_series.get(i).getRuns() * 100;
//							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
//							DecimalFormat df = new DecimalFormat("0.0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
//						}
//						 
//						for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
//							if(top_batsman_beststats.get(j).getPlayerId() == Playerid) {
//								if(k == 0) {
//									k += 1;
//									if(top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
//										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
//												top_batsman_beststats.get(j).getBestEquation()/2 + "\0");
//									}else {
//										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
//												(top_batsman_beststats.get(j).getBestEquation()-1) / 2 + "*" + "\0");
//									}
//									break;
//								}
//							}else {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
//							}
//						}
//						break;
//					case CricketUtil.BOWLER:
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");
//
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getWickets() + "\0");
//						
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON." + "\0");
//						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
//						}else {
//							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
//							economy_rate = economy_rate * 6;
//							DecimalFormat df = new DecimalFormat("0.00");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
//						}
//						
//						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
//							if(top_bowler_beststats.get(j).getPlayerId() == Playerid) {
//								if(k == 0) {
//									k += 1;
//									if(top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
//										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
//												((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
//									}
//									else if(top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
//										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + 
//												(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
//									}
//									break;
//								}
//							}else if(top_bowler_beststats.get(j).getPlayerId() != Playerid) {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
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
	
	public void populateFFThisSeriesBat(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster, Statistics stats, Configuration config) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			double strike_rate = 0 , economy_rate=0;
			int omo_num = 0;
			String cont_name = "";
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
									+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\Punjab_Cup_2023\\Photos\\" +match.getSetup().getHomeTeam().getTeamName4() + 
									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + 
										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					}else {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
									+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\Punjab_Cup_2023\\Photos\\" +match.getSetup().getAwayTeam().getTeamName4() + 
									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + 
										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					
					if(this_series.get(i).getPlayer().getSurname() != null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
								this_series.get(i).getPlayer().getSurname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
								this_series.get(i).getPlayer().getSurname() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
								"" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
								"" + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					
					cont_name = "$Dehighlight";
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

					
					if(this_series.get(i).getPlayer().getBattingStyle() != null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
								CricketFunctions.getbattingstyle(this_series.get(i).getPlayer().getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
					
					switch (TypeofProfile.toUpperCase()) {
					case "PT20CAREER":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "SHER-E-PUNJAB T20 CAREER" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");
						
						if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
						}
						break;
					case "PT20SEASON1":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "SHER-E-PUNJAB SEASON 1" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getRuns() + "\0");
						
						if(stats.getBallsFaced() == 0 || stats.getRuns()== 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							strike_rate = stats.getRuns() * 100;
							strike_rate = strike_rate/stats.getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
						}
						break;
					case "THISSERIES":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SEASON" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getRuns() + "\0");
						
						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							strike_rate = this_series.get(i).getRuns() * 100;
							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
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
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");

			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
									+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\Punjab_Cup_2023\\Photos\\" +match.getSetup().getHomeTeam().getTeamName4() + 
									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + 
										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					}else {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
									+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\Punjab_Cup_2023\\Photos\\" +match.getSetup().getAwayTeam().getTeamName4() + 
									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + 
										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					
					if(this_series.get(i).getPlayer().getSurname() != null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
								this_series.get(i).getPlayer().getSurname() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
								this_series.get(i).getPlayer().getSurname() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
								"" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
								"" + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					
					cont_name = "$Dehighlight";
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

					
					if(this_series.get(i).getPlayer().getBattingStyle() != null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
								CricketFunctions.getbowlingstyle(this_series.get(i).getPlayer().getBowlingStyle().toUpperCase()) + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
							"$StatGrpAll$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
					
					switch (TypeofProfile.toUpperCase()) {
					case "PT20CAREER":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "SHER-E-PUNJAB T20 CAREER" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");
						
						if(stats.getBallsBowled() == 0 || stats.getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRunsConceded() * 1.00) / stats.getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
						}
						break;
					case "PT20SEASON1":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "SHER-E-PUNJAB SEASON 1" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getMatches() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + stats.getWickets() + "\0");
						
						if(stats.getBallsBowled() == 0 || stats.getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (stats.getRunsConceded() * 1.00) / stats.getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
						}
						break;
					case "THISSERIES":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SEASON" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getWickets() + "\0");
						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (this_series.get(i).getRunsConceded() * 1.00) / this_series.get(i).getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
						}
						break;
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataIn 1.700 \0");
			

			}
	}

//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SERIES" + "\0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");
//
//			for(int i = 0; i <= this_series.size() - 1 ; i++) {
//				if(this_series.get(i).getPlayerId() == Playerid) {
//					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
//						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
//									+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//						}else {
//							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\Punjab_Cup_2023\\Photos\\" +match.getSetup().getHomeTeam().getTeamName4() + 
//									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
//									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + 
//										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//						}
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ logo_path + 
//								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
//								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
//					}else {
//						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path 
//									+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//						}else {
//							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "\\c\\Images\\Punjab_Cup_2023\\Photos\\" +match.getSetup().getAwayTeam().getTeamName4() + 
//									"\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
//								this.status = CricketUtil.UNSUCCESSFUL;
//							}
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\" + 
//									config.getPrimaryIpAddress() + local_photo_path  + match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + 
//										this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//						}
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
//								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
//									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
//					}
//					
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + 
//							this_series.get(i).getPlayer().getFirstname() + "\0");
//					
//					if(this_series.get(i).getPlayer().getSurname() != null) {
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
//								this_series.get(i).getPlayer().getSurname() + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
//								this_series.get(i).getPlayer().getSurname() + "\0");
//					}else {
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
//								"" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
//								"" + "\0");
//					}
//					
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + 
//							this_series.get(i).getPlayer().getFirstname() + "\0");
//					
//					
//					switch(TypeofProfile.toUpperCase()) {
//					case CricketUtil.BATSMAN:
//						
//						cont_name = "$Dehighlight";
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//
//						
//						if(this_series.get(i).getPlayer().getBattingStyle() != null) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
//									CricketFunctions.getbattingstyle(this_series.get(i).getPlayer().getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
//						}else {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
//						}
//						
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
//
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getRuns() + "\0");
//						
//						
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
//						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
//						}else {
//							strike_rate = this_series.get(i).getRuns() * 100;
//							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
//							DecimalFormat df = new DecimalFormat("0.0");
//							
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
//						}
//						
//						break;
//					case CricketUtil.BOWLER:
//						
//						cont_name = "$Dehighlight";
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//
//						if(this_series.get(i).getPlayer().getBowlingStyle() != null) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + 
//									CricketFunctions.getbowlingstyle(this_series.get(i).getPlayer().getBowlingStyle().toUpperCase())+ "\0");
//						}else {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + " " + "\0");
//						}
//						
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");
//
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getWickets() + "\0");
//																
//						
//						
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
//						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
//									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
//						}else {
//							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
//							economy_rate = economy_rate * 6;
//							DecimalFormat df_b = new DecimalFormat("0.00");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
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
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "RUNS" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "RUNS" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "S/R" + "\0");
			
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
					
					
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getRuns() + "\0");
			 		
					if(tournament.get(i).getBallsFaced() >= 1) {
						DecimalFormat df = new DecimalFormat("0.0");

						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
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
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "WICKETS" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "WICKETS" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "ECONOMY" + "\0");
			
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
					
					
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getWickets() + "\0");
			 		
					if(tournament.get(i).getBallsBowled() >= 1) {
						
						DecimalFormat df_b = new DecimalFormat("0.00");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
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
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "FOURS" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "3" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "FOURS" + "\0");
			
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
					
					
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + tournament.get(i).getFours() + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
			
		case "MOST_SIXES":
			Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "SIXES" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "3" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "MATCHES" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "SIXES" + "\0");
			
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
					
					
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name() + "\0");
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 										+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + tournament.get(i).getMatches() + "\0");
			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$3ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
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
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "HIGHEST" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "INDIVIDUAL SCORE" + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase() + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$BottomInfoGrp$BottomInfoAll$TotalScoreGrp$TotalScore*GEOM*TEXT SET " + " " + "\0");

	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col*FUNCTION*Omo*vis_con SET " + "9" + "\0");


	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatHead*GEOM*TEXT SET " + "PLAYER" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue1*GEOM*TEXT SET " + "SCORE" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue2*GEOM*TEXT SET " + "BALLS" + "\0");
	 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$StatHeadGrp$StatValueGrp$StatValue3*GEOM*TEXT SET " + "OPPONENT" + "\0");
			
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

			 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
 							+ cont_name + "$StatHead*GEOM*TEXT SET " + top_ten_beststat.get(i).getPlayer().getFull_name() + "\0");
					if(top_ten_beststat.get(i).getBestEquation() % 2 == 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
									+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + top_ten_beststat.get(i).getBestEquation() / 2 + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
									+ cont_name + "$StatValueGrp$StatValue1*GEOM*TEXT SET " + top_ten_beststat.get(i).getBestEquation() / 2 + "*" + "\0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
								+ cont_name + "$StatValueGrp$StatValue2*GEOM*TEXT SET " + top_ten_beststat.get(i).getBalls() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$Partnership$Data$FF_ROWCOL$4ColGrp$4Col$Row" + row_no + "$RowAni$RowOmo" 
		 					+ cont_name + "$StatValueGrp$StatValue3*GEOM*TEXT SET " + top_ten_beststat.get(i).getOpponentTeam().getTeamName3().toUpperCase() + "\0");
					
				}	
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			break;
		}
	}
	public void populatePointers(PrintWriter print_writer,String viz_scene, Pointers Pt ,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "PUNJAB_T20":
			if (match == null) {
				System.out.println("ERROR: populateScorecard -> Match is null"); // DJ change all 'this.status' to 'system printout'
			} else if (match.getMatch().getInning() == null) {
				System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");

				if(Pt.getTeam() != null){
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + Pt.getTeam() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + logo_path + "TLogo" + "\0");
				}
				
				if(Pt.getText1() != null && Pt.getText2() != null && Pt.getText3() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + Pt.getHeader() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + Pt.getText1() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + Pt.getText2() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo03" + " SET " + Pt.getText3() + "\0");
					
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$1st*ACTIVE SET 1 \0");	
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$2nd*ACTIVE SET 1 \0");
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$3rd*ACTIVE SET 1 \0");

				}else if(Pt.getText1() != null && Pt.getText2() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + Pt.getHeader() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + Pt.getText1() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + Pt.getText2() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo03" + " SET " + "" + "\0");
					
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$1st*ACTIVE SET 1 \0");	
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$2nd*ACTIVE SET 1 \0");
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$3rd*ACTIVE SET 0 \0");
					
				}else if(Pt.getText1() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + Pt.getHeader() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + Pt.getText1() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo03" + " SET " + "" + "\0");
					
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$1st*ACTIVE SET 1 \0");	
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$2nd*ACTIVE SET 0 \0");
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*TREE*$Main$All$DataAll$Data$3rd*ACTIVE SET 0 \0");
					
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.580 \0");
				//this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public static String This_over(MatchAllData matchData ,List<Event> events) {
		String This_over="";
		int bowlerid =0;
		if((events != null) && (events.size() > 0)) {
			for (int i = events.size() - 1;  i>= 0; i--) {
				if(events.get(i).getEventInningNumber() == matchData.getMatch().getInning().stream().filter(in -> in.getIsCurrentInning()
						.equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null).getInningNumber()) {
					
					if (matchData.getEventFile().getEvents().get(i).getEventInningNumber() 
							== matchData.getMatch().getInning().stream().filter(in -> in.getIsCurrentInning()
									.equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null).getInningNumber()) {
						
						 bowlerid = matchData.getMatch().getInning().stream().filter(in -> in.getIsCurrentInning()
						        .equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null).getBowlingCard().stream()
						        .filter(bowlingCard -> bowlingCard.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + 
						        		CricketUtil.BOWLER)).findAny().orElse(null).getPlayerId();
						
						if(bowlerid !=0) {
							if((matchData.getEventFile().getEvents().get(i).getEventBowlerNo() == bowlerid)) {
								switch (matchData.getEventFile().getEvents().get(i).getEventType())
								 {
								    case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
								    case CricketUtil.FOUR: case CricketUtil.SIX: 
								      This_over = This_over + String.valueOf(matchData.getEventFile().getEvents().get(i).getEventRuns() + " ");
								      break;
								    case CricketUtil.NO_BALL:
								    		 This_over = This_over + matchData.getEventFile().getEvents().get(i).getEventType() + " ";
								    		 break;
								    case CricketUtil.BYE: case CricketUtil.LEG_BYE:
								    	if(matchData.getEventFile().getEvents().get(i).getEventRuns() > 1) {
								    		This_over = This_over + String.valueOf(matchData.getEventFile().getEvents().get(i).getEventRuns() +
										    		  + matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns()) 
								    		+ matchData.getEventFile().getEvents().get(i).getEventType() + " ";
								    		
								    	}else {
								    		This_over = This_over + matchData.getEventFile().getEvents().get(i).getEventType() + " ";
								    	}
								    		 
								    	break;		 
								    case CricketUtil.WIDE:
								    		if(matchData.getEventFile().getEvents().get(i).getEventSubExtra() != null) {
								    			This_over = This_over + matchData.getEventFile().getEvents().get(i).getEventType() + "+" 
								    					+ matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns() + " ";
								    			
								    		}else {
								    			This_over = This_over + matchData.getEventFile().getEvents().get(i).getEventType() + " ";
								    		}
								    		 
								    		 break;
								    case CricketUtil.PENALTY:								    	
							    		 This_over = This_over + String.valueOf(matchData.getEventFile().getEvents().get(i).getEventRuns() +
									    		  + matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "+" 
							    				 + matchData.getEventFile().getEvents().get(i).getEventType() + " ";
							    		 
							    		 break;		 
								    case CricketUtil.LOG_ANY_BALL:
								    	if(matchData.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.BYE) || 
								    			 matchData.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.LEG_BYE)) {
								    		 
								    		 if(!matchData.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
								    			 if(matchData.getEventFile().getEvents().get(i).getEventRuns()+ matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns()>0) {
								    				 This_over = String.valueOf(matchData.getEventFile().getEvents().get(i).getEventRuns()
												    		  + matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + matchData.getEventFile().getEvents().get(i).getEventSubExtra() +" ";
									    		 }else {
									    			 This_over =matchData.getEventFile().getEvents().get(i).getEventSubExtra()+" "; 
									    		 }
								    		 }else {
								    			 if(matchData.getEventFile().getEvents().get(i).getEventRuns()+ matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns()>0) {
								    				 This_over = String.valueOf(matchData.getEventFile().getEvents().get(i).getEventRuns()
												    		  + matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + matchData.getEventFile().getEvents().get(i).getEventExtra()+" ";
									    		 }else {
									    			 This_over = matchData.getEventFile().getEvents().get(i).getEventSubExtra()+" "; 
									    		 }
								    		 }
								    		 
								    		 
								    	}else{
								    		if(matchData.getEventFile().getEvents().get(i).getEventRuns()+ matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns()>0) {
								    			if(matchData.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
								    				 This_over ="P" + String.valueOf(matchData.getEventFile().getEvents().get(i).getEventRuns()
												    		  + matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns())+" ";
								    			}else if(matchData.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.WIDE) 
								    					&& matchData.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)){
								    				 This_over =String.valueOf(matchData.getEventFile().getEvents().get(i).getEventExtraRuns()
												    		  + matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "wd ";
								    			}else {
								    				 This_over = String.valueOf(matchData.getEventFile().getEvents().get(i).getEventRuns()
												    		  + matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns())+"+"+matchData.getEventFile().getEvents().get(i).getEventExtra()+" ";
								    			}
									    		
								    		}else {
								    			 This_over =matchData.getEventFile().getEvents().get(i).getEventExtra()+" ";
								    		}								    		
								    	}
								    	if (matchData.getEventFile().getEvents().get(i).getEventHowOut() != null
								    	&& !matchData.getEventFile().getEvents().get(i).getEventHowOut().isEmpty()) {
								    		if(matchData.getEventFile().getEvents().get(i).getEventExtra()!=null
								    		&& !matchData.getEventFile().getEvents().get(i).getEventExtra().isEmpty()) {
								    			This_over = This_over + ",w ";
								    		}else {
								    			This_over = This_over + "w ";
								    		}
								    	}
								      	break;


								    case CricketUtil.LOG_WICKET:
								    	if(matchData.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)||
								    			matchData.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.ABSENT_HURT)||
								    			matchData.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
								    		continue;
								    	}else {
						            		 if (matchData.getEventFile().getEvents().get(i).getEventRuns() > 0) {
										    	  This_over = This_over + String.valueOf(matchData.getEventFile().getEvents().get(i).getEventRuns() + matchData.getEventFile().getEvents().get(i).getEventExtraRuns()
											    		  + matchData.getEventFile().getEvents().get(i).getEventSubExtraRuns()) 
										    	  + matchData.getEventFile().getEvents().get(i).getEventType() + " ";
										    	 } else {
										    	  This_over = This_over + matchData.getEventFile().getEvents().get(i).getEventType() + " ";
										      }
					                        	
								    	}		
							    		 break;
								 }
							}else if(matchData.getEventFile().getEvents().get(i).getEventBowlerNo() != bowlerid 
									&& matchData.getEventFile().getEvents().get(i).getEventBowlerNo() !=0){
									break;
							}
						}else {
							This_over=CricketUtil.END_OVER;
						}
					}
				}
			}
		}
		if (This_over != null && !This_over.isEmpty() && (This_over.contains("WIDE") || This_over.contains("NO_BALL") ||
				This_over.contains("LEG_BYE") || This_over.contains("BYE") || This_over.contains("PENALTY") ||
				This_over.contains("LOG_WICKET") || This_over.contains("WICKET"))) {
			This_over = This_over.replace("WIDE", "wd")
	                .replace("NO_BALL", "nb")
	                .replace("LEG_BYE", "lb")
	                .replace("BYE", "b")
	                .replace("PENALTY", "P")
	                .replace("LOG_WICKET", "w")
	                .replace("WICKET", "w");
			
			String[] This_overArr = This_over.split(" "); 
			//Collections.reverse(Arrays.asList(This_overArr));
		    StringBuilder result = new StringBuilder();
		        
		    for (int i = 0; i < This_overArr.length; i++) {
	            result.append(This_overArr[i]);
	            if (i < This_overArr.length - 1) {
	                result.append(" ");
	            }
		     }
		  This_over = result.toString();
	    }
		 
		return This_over;
	}
}

	