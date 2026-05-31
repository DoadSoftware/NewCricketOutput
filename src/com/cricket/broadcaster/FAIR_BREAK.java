package com.cricket.broadcaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
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
import com.cricket.containers.BattingCardFF;
import com.cricket.containers.BowlingFF;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Configuration;
import com.cricket.model.EventFile;
import com.cricket.model.FallOfWicket;
import com.cricket.model.Fixture;
import com.cricket.model.Ground;
import com.cricket.model.InfobarStats;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.LeagueTeam;
import com.cricket.model.MatchAllData;
import com.cricket.model.NameSuper;
import com.cricket.model.OverByOverData;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Playoff;
import com.cricket.model.Season;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FAIR_BREAK extends Scene{

	public String broadcaster = "FAIR_BREAK";
	public String status;
	public String slashOrDash = "-";
	public String logo_path = "IMAGE*/Default/FairBreak/Logos/";
	public String outline_path = "IMAGE*/Default/FairBreak/Logos/Outline/";
	public String team_color = "IMAGE*/Default/FairBreak/TeamColour/";
	public String manhattan_path = "IMAGE*/Default/Essentials/Manhattan";
	public String base_path = "IMAGE*/Default/Essentials/Base";
	public String text_path = "IMAGE*/Default/Essentials/Text";
	public String icon_path = "IMAGE*/Default/FairBreak/Icons";
	public String flag_path = "IMAGE*/Default/FairBreak/Flags/";
	public String team_color_path = "IMAGE*/Default/Essentials/Team";
	public String Toutline_color_path = "IMAGE*/Default/Essentials/TeamOutline";
	public String photo_path = "C:\\\\Images\\\\FAIRBREAK\\\\Photos\\\\";
	private String local_photo_path = "\\\\c\\\\Images\\\\FAIRBREAK\\\\Photos\\\\";
	public String icons_path = "C:\\\\Images\\\\NEPAL_T20\\\\Icons\\\\";
	
	public String centre_path = "\\\\CENTRE\\\\";
	public String left_path = "\\\\LEFT\\\\";
	
	public String last_speed = "";
	public Infobar infobar = new Infobar(); 
	public String which_graphic_on_screen = "";
	public String which_director_on_screen = "";
	public BattingCardFF bcf = new BattingCardFF();
	public BowlingFF bocf = new BowlingFF();
	
	public FAIR_BREAK() {
		super();
	}

	public FAIR_BREAK(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Infobar updateInfobar(List<Scene> scenes, MatchAllData match,List<MatchAllData> tournament_matches,boolean show_speed, PrintWriter print_writer) throws InterruptedException, IOException
	{
		if(infobar.isInfobar_on_screen() == true) {
			if(infobar.getIdent_section() != null && !infobar.getIdent_section().trim().isEmpty()) {
				infobar = populateInfobarIdent(infobar,true, scenes.get(0).getScene_path(), print_writer, match, broadcaster);
			}else {
				infobar = populateInfobarTeamScore(infobar,true, print_writer, match, broadcaster);
				infobar = populateVizInfobarMiddle(infobar, true, print_writer, match, broadcaster);
				
				if(infobar.getBottom_right_section() != null && !infobar.getBottom_right_section().trim().isEmpty()) {
					infobar = populateVizInfobarRight(infobar, true,print_writer, match,tournament_matches, broadcaster);
					if(CricketFunctions.getCurrentInningCurrentBowler(match) != null) {
						infobar.setLast_bowler(CricketFunctions.getCurrentInningCurrentBowler(match));
					}
				}else {
					infobar = populateVizInfobarRightTop(infobar, true, print_writer, match, broadcaster);
					infobar = populateVizInfobarRightBottom(infobar, true, print_writer, match, broadcaster);
				}
				
				if(infobar.getFull_section() != null && !infobar.getFull_section().trim().isEmpty()) {
					infobar = populateSection5(infobar,true,print_writer,0,"0",match,broadcaster);
				}
				if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
					infobar = populateVizInfobarTop(infobar, true, print_writer, match, broadcaster);
				}
			}

			if(infobar.getLast_speed_value() == null || infobar.getLast_speed_value().isEmpty() || !infobar.getLast_speed_value().equalsIgnoreCase(match.getMatch().getCurrent_speed())) {
				speed(print_writer, match);
			}
			showWinner(infobar,print_writer, match);
		}
		return infobar;
	}
	
	public void speed(PrintWriter print_writer,MatchAllData match) throws IOException, InterruptedException {
		if(match.getMatch().getCurrent_speed() != null && !match.getMatch().getCurrent_speed().isEmpty()) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BowlerGrp$Section3$Section3All$BallSpeed$img_Text$txt_Data1*GEOM*TEXT SET " + 
					"BALL SPEED - " + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BowlerGrp$Section3$Section3All$BallSpeed$img_Text$txt_Data2*GEOM*TEXT SET " + 
					match.getMatch().getCurrent_speed() + " KPH" + "\0");	
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$BowlerGrp$Section3$Section3All$BallSpeed$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + 
							inn.getBowling_team().getTeamName3().toLowerCase() +" \0");
				}
			}
			processAnimation(print_writer, "Section3$BallSpeedIn", "START", broadcaster);
			infobar.setLast_speed_value(match.getMatch().getCurrent_speed());
		}
	}

	public void showWinner(Infobar infobar,PrintWriter print_writer,MatchAllData match) throws InterruptedException
	{
		if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
				CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
			
			if(infobar.getLast_middle_section() != null && 
					!infobar.getLast_middle_section().trim().isEmpty()) {
				
				switch (infobar.getLast_middle_section().toUpperCase()) {
				case "EQUATION":
					processAnimation(print_writer, "Section3$EquationOut", "START", broadcaster);
					break;
				case "PROJECTED":
					processAnimation(print_writer, "Section3$ProjectedOut", "START", broadcaster);
					break;
				case "LAST_BOUNDARY":
					processAnimation(print_writer, "Section3$BallsSinceLastBoundaryOut", "START", broadcaster);
					break;
				case "BOUNDARIES":
					processAnimation(print_writer, "Section3$BoundariesOut", "START", broadcaster);
					break;
				case "LAST_WICKET":
					processAnimation(print_writer, "Section3$LastWicketOut", "START", broadcaster);
					break;
				case "TOURNAMENT-NAME":
					processAnimation(print_writer, "Section3$FreeTextSmallOut", "START", broadcaster);
					break;
				case "BATSMAN":
					processAnimation(print_writer, "Section3$Section3In", "START", broadcaster);
					break;	
				}
				
				TimeUnit.MILLISECONDS.sleep(200);
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
				processAnimation(print_writer, "Section3$FreeTextSmallIn", "START", broadcaster);
				infobar.setLast_middle_section(null);
				infobar.setMiddle_section(null);
				
			}
		}
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics, Configuration config) throws InterruptedException, ParseException, JAXBException, 
		IllegalAccessException, InvocationTargetException, IOException{
	
		switch (whatToProcess) {
		case "ANIMATE-IN-INFOBAR": case "ANIMATE-IN-IDENT": case "ANIMATE-OUT-DIRECTOR": case "ANIMATE-OUT-SPONSOR": case "TICKER_LT_OUT": case "TICKER_LT_IN": 
		case "ANIMATE-OUT-SECTION5": case "ANIMATE-OUT-SECTION2": case "ANIMATE-OUT-SECTION4": case "ANIMATE-OUT-SECTION4_N_5": case "ANIMATE-OUT": case "CLEAR-ALL": 
		case "ANIMATE-SHRINK_IN": case "ANIMATE-SHRINK_OUT": case "CHANGE_ON": case "CHANGE_ON_BOWLINGCARD":
		
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": 
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-PLAYINGXI_PHOTOS": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-POSITION_LANDMARK":
		case "ANIMATE-IN-POINTSTABLE": case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE":
		case "ANIMATE-IN-MOSTRUNS": case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM":
		case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-BALL_PERFORMER":
		case "ANIMATE-IN-MOST": case "ANIMATE-IN-FF_TARGET": case "ANIMATE-IN-PLAYINGXI_SUBS5": case "ANIMATE-IN-PLAYING_CHANGE_ON1": case "ANIMATE-IN-PLAYING_CHANGE_ON2": case "ANIMATE-IN-PLAYING_CHANGE_ON3":
		case "ANIMATE-IN-TORNAMENT_PLAYER":
			
		case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF":
		case "ANIMATE-IN-LOF_SCORECARD": case "ANIMATE-IN-LOF_BOWLINGCARD":
			
		case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL":  case "ANIMATE-IN-BUG-BOWLER":  case "ANIMATE-IN-BUGTARGET": case "ANIMATE-IN-BUGPARTNERSHIP":
		case "ANIMATE-IN-BUG-TOSS": case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP":
			
		case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": 
		case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID":
		case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY":
		case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH": case "ANIMATE-IN-L3MATCH_PROMO":case "ANIMATE-IN-LTPOINTSTABLE": 
		case "ANIMATE-IN-BOWLER_STYLE":  case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-IN-BATSMAN_STYLE":  case "ANIMATE-IN-GENERIC": case "ANIMATE-IN-HOWOUT_QUICK": 
		case "ANIMATE-IN-THISSERIES":case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-LINEUP":case "ANIMATE-IN-LTMANHATTAN": case "ANIMATE-OUT-POWERPLAY":
		case "ANIMATE-IN-PLAYOFFS":  case "ANIMATE-IN-INN_BUILDER": case "ANIMATE-IN-LT_SEASON": case "ANIMATE-IN-BALLSINCE": case "ANIMATE-IN-LT_BOWLERSPEED": case "ANIMATE-IN-PARTNERSHIP_LT":
		case "ANIMATE-IN-LT_PLAYINGXI": case "ANIMATE-IN-PHASE":
			
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS":
			case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-PLAYINGXI_PHOTOS": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-LANDMARK": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-IN-POINTSTABLE": 
			case "ANIMATE-IN-MANHATTAN": case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMS_LOGO": case "ANIMATE-IN-PREVIOUS_SUMMARY": case "ANIMATE-IN-TIEID-DOUBLE":case "ANIMATE-IN-MOSTRUNS":
			case "ANIMATE-IN-MOSTWICKETS": case "ANIMATE-IN-MOSTFOURS": case "ANIMATE-IN-MOSTSIXES": case "ANIMATE-IN-HIGHESTSCORE": case "ANIMATE-IN-WORM":case "ANIMATE-IN-LTPARTNERSHIP":
			case "ANIMATE-IN-SCHEDULE": case "ANIMATE-IN-FFTHISSERIES": case "ANIMATE-IN-FF_STATS": case "ANIMATE-IN-PLAYERPROFILEBALL": case "ANIMATE-IN-BAT-PERFORMER": case "ANIMATE-IN-TORNAMENT_PLAYER":
			case "ANIMATE-IN-INNING_SUMMARY": case "ANIMATE-IN-PLAYOFFS": case "ANIMATE-IN-BALL_PERFORMER": case "ANIMATE-IN-MOST": case "ANIMATE-IN-PLAYINGXI_SUBS5": case "ANIMATE-IN-FF_TARGET": 
			
			case "ANIMATE-IN-BUG": case "ANIMATE-IN-BUG-DB": case "ANIMATE-IN-BUG-DISMISSAL": case "ANIMATE-IN-BUG-BOWLER": case "ANIMATE-IN-BUGTARGET":case "ANIMATE-IN-BUGPARTNERSHIP":
			case "ANIMATE-IN-BUG-TOSS": case "ANIMATE-IN-BUG_POWERPLAY": case "ANIMATE-IN-BUG_HIGHLIGHT": case "ANIMATE-IN-MULTI_PARTNERSHIP": case "ANIMATE-IN-L3MATCH_PROMO": case "ANIMATE-IN-L3MATCHID": 
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().trim().isEmpty()) {
						switch(infobar.getLast_full_section().toUpperCase()) {
	  					case "TIMELINE":
	  						processAnimation(print_writer, "Top_Section$TimeLineOut", "START", broadcaster);
	  						break;
	  					case "FREETEXT":
	  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
	  						break;
	  					case "BONUS":
	  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
	  						break;
	  					case "EXTRAS":
	  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
	  						break;	
	  					}
						processAnimation(print_writer, "Top_Section$TopBaseOut", "START", broadcaster);
					}
					infobar.setLast_full_section("");infobar.setFull_section("");
					TimeUnit.MILLISECONDS.sleep(200);
					AnimateInGraphics(print_writer, "FF_IN");
					TimeUnit.MILLISECONDS.sleep(500);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					AnimateInGraphics(print_writer, "FF_IN");
				}
				break;
			case "ANIMATE-MINI-BATTINGCARD": case "ANIMATE-MINI-BOWLINGCARD": case "ANIMATE-IN-BATGRIFF": case "ANIMATE-IN-BALLGRIFF":
			case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-BATSMANSTATS": case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": 
			case "ANIMATE-IN-TEAMSUMMARY": case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION":
			case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT":   case "ANIMATE-IN-BOWLERSUMMARY": case "ANIMATE-IN-NEXT_TO_BAT": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-LTPOWERPLAY":
			case "ANIMATE-IN-EQUATION": case "ANIMATE-IN-BATSMAN_THIS_MATCH": case "ANIMATE-IN-BOWLER_THIS_MATCH": case "ANIMATE-IN-LTPOINTSTABLE":case "ANIMATE-IN-BOWLER_STYLE": 
			case "ANIMATE-IN-HOWOUT_WITHOUT_FIELDER": case "ANIMATE-IN-BATSMAN_STYLE": case "ANIMATE-IN-GENERIC": case "ANIMATE-IN-HOWOUT_QUICK": case "ANIMATE-IN-THISSERIES":
			case "ANIMATE-IN-PLAYERPROFILEBAT": case "ANIMATE-IN-LINEUP": case "ANIMATE-IN-INN_BUILDER": case "ANIMATE-IN-LT_SEASON": case "ANIMATE-IN-BALLSINCE":
			case "ANIMATE-IN-PARTNERSHIP_LT": //case "ANIMATE-IN-LTMANHATTAN": 
			case "ANIMATE-IN-LT_PLAYINGXI": case "ANIMATE-IN-LOF_SCORECARD": case "ANIMATE-IN-LOF_BOWLINGCARD": case "ANIMATE-IN-PHASE":
				
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "SCOREBUG") {
					if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().trim().isEmpty()) {
						switch(infobar.getLast_full_section().toUpperCase()) {
	  					case "TIMELINE":
	  						processAnimation(print_writer, "Top_Section$TimeLineOut", "START", broadcaster);
	  						break;
	  					case "FREETEXT":
	  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
	  						break;
	  					case "BONUS":
	  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
	  						break;
	  					case "EXTRAS":
	  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
	  						break;	
	  					}
						processAnimation(print_writer, "Top_Section$TopBaseOut", "START", broadcaster);
					}
					infobar.setLast_full_section("");infobar.setFull_section("");
					TimeUnit.MILLISECONDS.sleep(200);
					AnimateInGraphics(print_writer, "LT_IN");
					TimeUnit.MILLISECONDS.sleep(500);
				}else if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateOutGraphics(print_writer, "IDENT");
					AnimateInGraphics(print_writer, "LT_IN");
				}
				break;
			}
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-TORNAMENT_PLAYER":
				AnimateInGraphics(print_writer, "TORNAMENT_PLAYER");
				which_graphic_on_screen = "TORNAMENT_PLAYER";
				break;
			case "ANIMATE-IN-PHASE":
				AnimateInGraphics(print_writer, "PHASE");
				which_graphic_on_screen = "PHASE";
				break;
			case "ANIMATE-IN-LOF_BOWLINGCARD":
				AnimateInGraphics(print_writer, "LOF_BOWLINGCARD");
				which_graphic_on_screen = "LOF_BOWLINGCARD";
				break;
			case "ANIMATE-IN-LOF_SCORECARD":
				AnimateInGraphics(print_writer, "LOF_SCORECARD");
				which_graphic_on_screen = "LOF_SCORECARD";
				break;
			case "ANIMATE-IN-PLAYING_CHANGE_ON1":
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*DataOut START \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Images_In START \0");
				which_director_on_screen = "IMAGES";
				break;
			case "ANIMATE-IN-PLAYING_CHANGE_ON2":
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Team$Change CONTINUE \0");
				break;
			case "ANIMATE-IN-PLAYING_CHANGE_ON3":
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Team$Change CONTINUE \0");
				break;
			case "ANIMATE-IN-BUG-TOSS":
				AnimateInGraphics(print_writer, "BUG-TOSS");
				which_graphic_on_screen = "BUG-TOSS";
				break;
			case "ANIMATE-IN-BUG_POWERPLAY":
				AnimateInGraphics(print_writer, "BUG_POWERPLAY");
				which_graphic_on_screen = "BUG_POWERPLAY";
				break;
			case "ANIMATE-IN-MULTI_PARTNERSHIP":
				AnimateInGraphics(print_writer, "MULTI_PARTNERSHIP");
				which_graphic_on_screen = "MULTI_PARTNERSHIP";
				break;
			case "ANIMATE-IN-BUG_HIGHLIGHT":
				AnimateInGraphics(print_writer, "BUG_HIGHLIGHT");
				which_graphic_on_screen = "BUG_HIGHLIGHT";
				break;
			case "ANIMATE-IN-NEXT_TO_BAT":
				AnimateInGraphics(print_writer, "NEXT_TO_BAT");
				which_graphic_on_screen = "NEXT_TO_BAT";
				break;
			case "ANIMATE-IN-LT_BOWLERSPEED":
				AnimateInGraphics(print_writer, "LT_BOWLERSPEED");
				which_graphic_on_screen = "LT_BOWLERSPEED";
				break;
			case "ANIMATE-IN-BALLSINCE":
				AnimateInGraphics(print_writer, "BALLSINCE");
				which_graphic_on_screen = "BALLSINCE";
				break;
			case "ANIMATE-IN-LT_SEASON":
				AnimateInGraphics(print_writer, "LT_SEASON");
				which_graphic_on_screen = "LT_SEASON";
				break;
			case "ANIMATE-IN-INN_BUILDER":
				AnimateInGraphics(print_writer, "INN_BUILDER");
				which_graphic_on_screen = "INN_BUILDER";
				break;
			case "ANIMATE-IN-MOST":
				AnimateInGraphics(print_writer, "MOST");
				which_graphic_on_screen = "MOST";
				break;
			case "ANIMATE-IN-BALLGRIFF":
				AnimateInGraphics(print_writer, "BALLGRIFF");
				which_graphic_on_screen = "BALLGRIFF";
				break;
			case "ANIMATE-IN-BATGRIFF":
				AnimateInGraphics(print_writer, "BATGRIFF");
				which_graphic_on_screen = "BATGRIFF";
				break;
			case "ANIMATE-IN-LINEUP":
				AnimateInGraphics(print_writer, "LINEUP");
				which_graphic_on_screen = "LINEUP";
				break;
			case "ANIMATE-IN-BUGPARTNERSHIP":
				AnimateInGraphics(print_writer, "BUGPARTNERSHIP");
				which_graphic_on_screen = "BUGPARTNERSHIP";
				break;
			case "ANIMATE-IN-IDENT":
				if(infobar.isInfobar_on_screen() == true && which_graphic_on_screen == "IDENT") {
					AnimateInGraphics(print_writer, "IDENT");
					which_graphic_on_screen = "IDENT";
					infobar.setInfobar_on_screen(true);
					
				}else if(infobar.isInfobar_on_screen() == true) {
					AnimateOutGraphics(print_writer, "ANIMATE-OUT-INFOBAR");
					TimeUnit.MILLISECONDS.sleep(400);
					processAnimation(print_writer, "MainIn", "SHOW 0.0", broadcaster);
					TimeUnit.MILLISECONDS.sleep(400);
					AnimateInGraphics(print_writer, "IDENT");
					TimeUnit.MILLISECONDS.sleep(200);
					which_graphic_on_screen = "IDENT";
					infobar.setInfobar_on_screen(true);
					
				}else {
//					AnimateInGraphics(print_writer, "RESET");
					TimeUnit.MILLISECONDS.sleep(200);
					AnimateInGraphics(print_writer, "IDENT");
					TimeUnit.MILLISECONDS.sleep(200);
					which_graphic_on_screen = "IDENT";
					infobar.setInfobar_on_screen(true);
				}
				break;
			case "ANIMATE-IN-BALL_PERFORMER":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type() != null) {
						if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
//							TimeUnit.MILLISECONDS.sleep(600);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
						}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
//							TimeUnit.MILLISECONDS.sleep(600);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
//						TimeUnit.MILLISECONDS.sleep(600);
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
//					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
					TimeUnit.SECONDS.sleep(1);
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
				}
				
				if(bocf.getLast_type() != null) {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						if(bocf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerIn START \0");
							bocf.setLast_type(bocf.getType());
						}else if(bocf.getType().toUpperCase().equalsIgnoreCase("BALLSTATUS")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
							bocf.setLast_type(bocf.getType());
						}
					}else if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("BALLSTATUS")) {
						if(bocf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerIn START \0");
							bocf.setLast_type(bocf.getType());
						}else if(bocf.getType().toUpperCase().equalsIgnoreCase("BALLSTATUS")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
							TimeUnit.SECONDS.sleep(1);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
							bocf.setLast_type(bocf.getType());
						}
					}
				}else {
					
					bocf.setLast_type(bocf.getType());
					if(bocf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
//						TimeUnit.MILLISECONDS.sleep(600);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerIn START \0");
						TimeUnit.SECONDS.sleep(1);
						bocf.setLast_type(bocf.getType());
					}else if(bocf.getType().toUpperCase().equalsIgnoreCase("BALLSTATUS")) {
//						TimeUnit.MILLISECONDS.sleep(600);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
						bocf.setLast_type(bocf.getType());
					}
				}
				
				bcf.setLast_type(null);
				which_graphic_on_screen = "BATBALLSUMMARY_BOWLINGCARD_PERFORMER";
				
				break;
			case "ANIMATE-IN-BAT-PERFORMER":
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type() != null) {
						if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
//							TimeUnit.MILLISECONDS.sleep(200);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
//							TimeUnit.MILLISECONDS.sleep(200);
						}else if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("BALLSTATUS")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
//							TimeUnit.MILLISECONDS.sleep(200);
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
//						TimeUnit.MILLISECONDS.sleep(200);
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
//					TimeUnit.MILLISECONDS.sleep(200);
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard*ACTIVE SET " + "1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
					which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD_PERFORMER";
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
					which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD_PERFORMER";
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
					which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD_PERFORMER";
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
//					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
//					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD_PERFORMER";
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD_PERFORMER";
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
//					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD_PERFORMER";
				}
				
				if(bcf.getLast_type() != null) {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						if(bcf.getType().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
							TimeUnit.MILLISECONDS.sleep(600);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipIn START \0");
							bcf.setLast_type(bcf.getType());
						}else if(bcf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
							TimeUnit.MILLISECONDS.sleep(600);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerIn START \0");
							bcf.setLast_type(bcf.getType());
						}
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						if(bcf.getType().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
							TimeUnit.MILLISECONDS.sleep(600);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipIn START \0");
							bcf.setLast_type(bcf.getType());
						}else if(bcf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
							TimeUnit.MILLISECONDS.sleep(600);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerIn START \0");
							bcf.setLast_type(bcf.getType());
						}
					}
				}else {
					bcf.setLast_type(bcf.getType());
					if(bcf.getType().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp*ACTIVE SET 1 \0");
//						TimeUnit.MILLISECONDS.sleep(600);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipIn START \0");
						bcf.setLast_type(bcf.getType());
					}else if(bcf.getType().toUpperCase().equalsIgnoreCase("PERFORMER")) {
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");
//						TimeUnit.MILLISECONDS.sleep(400);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerIn START \0");
						bcf.setLast_type(bcf.getType());
					}
				}
				which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD_PERFORMER";
				TimeUnit.SECONDS.sleep(1);
				break;
			case "ANIMATE-IN-SCORECARD":
				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type() != null) {
						if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
//							TimeUnit.MILLISECONDS.sleep(200);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
//							TimeUnit.MILLISECONDS.sleep(200);
						}else if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("BALLSTATUS")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
//							TimeUnit.MILLISECONDS.sleep(200);
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
//						TimeUnit.MILLISECONDS.sleep(200);
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
//						TimeUnit.MILLISECONDS.sleep(600);
					}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
//						TimeUnit.MILLISECONDS.sleep(600);
					}
//					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
				}else {
					AnimateInGraphics(print_writer, "SCORECARD");
				}
				
				which_graphic_on_screen = "BATBALLSUMMARY_SCORECARD";
				bocf.setLast_type(null);
				bcf.setLast_type(null);
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
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type() != null) {
						if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
//							TimeUnit.MILLISECONDS.sleep(600);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
//							TimeUnit.MILLISECONDS.sleep(600);
						}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
//							TimeUnit.MILLISECONDS.sleep(600);
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
//							TimeUnit.MILLISECONDS.sleep(600);
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardOut START \0");
//						TimeUnit.MILLISECONDS.sleep(600);
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
//						TimeUnit.MILLISECONDS.sleep(200);
					}else if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("BALLSTATUS")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
//						TimeUnit.MILLISECONDS.sleep(200);
					}
//					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
				}else {
					AnimateInGraphics(print_writer, "BOWLINGCARD");
				}
				bcf.setLast_type(null);
				bocf.setLast_type(null);
				which_graphic_on_screen = "BATBALLSUMMARY_BOWLINGCARD";
				break;
			case "ANIMATE-IN-PARTNERSHIP":
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCard_Out START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Partnership_In START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCard_Out START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Partnership_In START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Summary_Out START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Partnership_In START \0");
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
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type() != null) {
						if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
							TimeUnit.MILLISECONDS.sleep(600);
						}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
							TimeUnit.MILLISECONDS.sleep(600);
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatRightCardOut START \0");
						TimeUnit.MILLISECONDS.sleep(600);
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type() != null) {
						if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
							TimeUnit.MILLISECONDS.sleep(200);
						}else if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("BALLSTATUS")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
							TimeUnit.MILLISECONDS.sleep(200);
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
						TimeUnit.MILLISECONDS.sleep(200);
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
				}else if(which_graphic_on_screen == "POINTSTABLE") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
				}else {
					AnimateInGraphics(print_writer, "MATCHSUMMARY");
				}
				bcf.setLast_type(null);
				bocf.setLast_type(null);
				which_graphic_on_screen = "BATBALLSUMMARY_MATCHSUMMARY";
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
			case "ANIMATE-IN-PARTNERSHIP_LT":
				AnimateInGraphics(print_writer, "PARTNERSHIP_LT");
				which_graphic_on_screen = "PARTNERSHIP_LT";
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
			case "ANIMATE-IN-PLAYERPROFILEBALL":
				AnimateInGraphics(print_writer, "PLAYERPROFILEBALL");
				which_graphic_on_screen = "PLAYERPROFILEBALL";
				break;
			case "ANIMATE-IN-THISSERIES":
				AnimateInGraphics(print_writer, "THISSERIES");
				which_graphic_on_screen = "THISSERIES";
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
			case "ANIMATE-IN-LT_PLAYINGXI":
				AnimateInGraphics(print_writer, "LT_PLAYINGXI");
				which_graphic_on_screen = "LT_PLAYINGXI";
				break;
			case "ANIMATE-IN-PLAYINGXI":
				AnimateInGraphics(print_writer, "TEAMLINEUP");
				which_graphic_on_screen = "TEAMLINEUP";
				which_director_on_screen = "DATA";
				break;
			case "ANIMATE-IN-PLAYINGXI_PHOTOS":
				AnimateInGraphics(print_writer, "TEAMLINEUP_PHOTOS");
				which_graphic_on_screen = "TEAMLINEUP_PHOTOS";
				break;
			case "ANIMATE-IN-PLAYINGXI_SUBS5":
				AnimateInGraphics(print_writer, "TEAMLINEUP_SUBS");
				which_graphic_on_screen = "TEAMLINEUP_SUBS";
				break;
			case "ANIMATE-IN-DOUBLETEAMS":
				AnimateInGraphics(print_writer, "DOUBLETEAMS");
				which_graphic_on_screen = "DOUBLETEAMS";
				break;
			case "ANIMATE-IN-FF_TARGET":
				AnimateInGraphics(print_writer, "FFTARGET");
				which_graphic_on_screen = "FFTARGET";
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
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER") {
					if(bcf.getLast_type() != null) {
						if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PARTNERSHIP")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPartnershipOut START \0");
							TimeUnit.MILLISECONDS.sleep(600);
						}else if(bcf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatPerformerOut START \0");
							TimeUnit.MILLISECONDS.sleep(600);
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BatRightCardOut START \0");
						TimeUnit.MILLISECONDS.sleep(600);
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER") {
					if(bocf.getLast_type() != null) {
						if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("PERFORMER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BallPerformerOut START \0");
							TimeUnit.MILLISECONDS.sleep(200);
						}else if(bocf.getLast_type().toUpperCase().equalsIgnoreCase("BALLSTATUS")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
							TimeUnit.MILLISECONDS.sleep(200);
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardOut START \0");
						TimeUnit.MILLISECONDS.sleep(200);
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryOut START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
				}else {
					AnimateInGraphics(print_writer, "POINTSTABLE");
				}
				bcf.setLast_type(null);
				bocf.setLast_type(null);
				
				which_graphic_on_screen = "POINTSTABLE";
				break;
			case "ANIMATE-IN-PLAYOFFS":
				AnimateInGraphics(print_writer, "PLAYOFFS");
				which_graphic_on_screen = "PLAYOFFS";
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
			case "ANIMATE-IN-INNING_SUMMARY":
				AnimateInGraphics(print_writer, "INNING_SUMMARY");
				which_graphic_on_screen = "INNING_SUMMARY";
				break;
			case "ANIMATE-IN-LTMANHATTAN":
				if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().trim().isEmpty()) {
					switch(infobar.getLast_full_section().toUpperCase()) {
  					case "TIMELINE":
  						processAnimation(print_writer, "Top_Section$TimeLineOut", "START", broadcaster);
  						break;
  					case "FREETEXT":
  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
  						break;
  					case "BONUS":
  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
  						break;
  					case "EXTRAS":
  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
  						break;	
  					}
					processAnimation(print_writer, "Top_Section$TopBaseOut", "START", broadcaster);
				}
				infobar.setLast_full_section("");infobar.setFull_section("");
				TimeUnit.MILLISECONDS.sleep(200);
				AnimateInGraphics(print_writer, "LT-MANHATTAN");
				which_graphic_on_screen = "LT-MANHATTAN";
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
					processAnimation(print_writer, "IdentOut", "SHOW 0.360", broadcaster);
					processAnimation(print_writer, "Section5$FreeTextOut", "SHOW 0.340", broadcaster);
					processAnimation(print_writer, "Section5$TimeLineIn", "SHOW 0.0", broadcaster);
					AnimateInGraphics(print_writer, "SCOREBUG");
					which_graphic_on_screen = "SCOREBUG";
					infobar.setInfobar_on_screen(true);
				}
				break;
			case "ANIMATE-SHRINK_IN":
				AnimateInGraphics(print_writer, "LT_IN");
				break;
			case "ANIMATE-SHRINK_OUT":
				AnimateOutGraphics(print_writer, "LT_OUT");
				break;
			case "CHANGE_ON":
				print_writer.println("-1 RENDERER PREVIEW SCENE*/Default/FairBreak/MiniBatting C:/Temp/Preview.jpg In 0.900 In$BatDataIn 0.772 Change_Out 0.500 Change_In 0.701 \0");
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Change_Out START \0");
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Change_In START \0");
				break;
			case "CHANGE_ON_BOWLINGCARD":
				print_writer.println("-1 RENDERER PREVIEW SCENE*/Default/FairBreak/MiniBowling C:/Temp/Preview.jpg In 0.900 In$BatDataIn 0.772 Change_Out 0.500 Change_In 0.701 \0");
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Change_Out START \0");
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Change_In START \0");
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
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/ScoreBug\0");
		           	
	               print_writer.println("-1 RENDERER*FRONT_LAYER INITIALIZE\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 0\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0\0");
	               print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
	               
	               print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 1\0");
	               
	               print_writer.println("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*/Default/FullFrames\0");
		           	
	               print_writer.println("-1 RENDERER*BACK_LAYER INITIALIZE\0");
	               print_writer.println("-1 RENDERER*BACK_LAYER*SCENE_DATA INITIALIZE\0");
	               print_writer.println("-1 RENDERER*BACK_LAYER*UPDATE SET 0\0");
	               print_writer.println("-1 RENDERER*BACK_LAYER*STAGE SHOW 0.0\0");
	               print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Reset START \0");
	               
	               print_writer.println("-1 RENDERER*BACK_LAYER*UPDATE SET 1\0");
	               
	               print_writer.println("-1 SCENE CLEANUP\0");
	               print_writer.println("-1 IMAGE CLEANUP\0");
	               print_writer.println("-1 GEOM CLEANUP\0");
	               print_writer.println("-1 FONT CLEANUP\0");
	               
	               infobar.setInfobar_on_screen(false);
	               infobar = new Infobar();
	               which_graphic_on_screen = "";
					break;
			case "ANIMATE-OUT":
				//System.out.println(which_graphic_on_screen);
				switch(which_graphic_on_screen) {
				case "TORNAMENT_PLAYER":
					AnimateOutGraphics(print_writer, "TORNAMENT_PLAYER");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "PHASE":
					AnimateOutGraphics(print_writer, "PHASE");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LT_PLAYINGXI":
					AnimateOutGraphics(print_writer, "LT_PLAYINGXI");
					which_graphic_on_screen = "";
					TimeUnit.MILLISECONDS.sleep(900);
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LOF_BOWLINGCARD":
					AnimateOutGraphics(print_writer, "LOF_BOWLINGCARD");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LOF_SCORECARD":
					AnimateOutGraphics(print_writer, "LOF_SCORECARD");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BUG-TOSS":
					AnimateOutGraphics(print_writer, "BUG-TOSS");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG_POWERPLAY":
					AnimateOutGraphics(print_writer, "BUG_POWERPLAY");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "MULTI_PARTNERSHIP":
					AnimateOutGraphics(print_writer, "MULTI_PARTNERSHIP");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "BUG_HIGHLIGHT":
					AnimateOutGraphics(print_writer, "BUG_HIGHLIGHT");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "NEXT_TO_BAT":
					AnimateOutGraphics(print_writer, "NEXT_TO_BAT");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LT_BOWLERSPEED":
					AnimateOutGraphics(print_writer, "LT_BOWLERSPEED");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BALLSINCE":
					AnimateOutGraphics(print_writer, "BALLSINCE");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LT_SEASON":
					AnimateOutGraphics(print_writer, "LT_SEASON");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "INN_BUILDER":
					AnimateOutGraphics(print_writer, "INN_BUILDER");
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BALLGRIFF":
					AnimateOutGraphics(print_writer, "BALLGRIFF");
					which_graphic_on_screen = "SCOREBUG";
					//which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BATGRIFF":
					AnimateOutGraphics(print_writer, "BATGRIFF");
					which_graphic_on_screen = "SCOREBUG";
					//which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "LINEUP":
					AnimateOutGraphics(print_writer, "LINEUP");
					//which_graphic_on_screen = "SCOREBUG";
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "BUGPARTNERSHIP":
					AnimateOutGraphics(print_writer, "BUGPARTNERSHIP");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
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
				case "MINI_BATTINGCARD":
					AnimateOutGraphics(print_writer, "MINI_BATTINGCARD");
					TimeUnit.SECONDS.sleep(1);
					which_graphic_on_screen = "SCOREBUG";

					resetInfobarAnimation(print_writer,"LT_FRAME");
					break;
				case "MINI_BOWLINGCARD":
					AnimateOutGraphics(print_writer, "MINI_BOWLINGCARD");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "SCOREBUG";
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
					TimeUnit.SECONDS.sleep(2);
					
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
					TimeUnit.MILLISECONDS.sleep(200);
					if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().trim().isEmpty()) {
	  					processAnimation(print_writer, "Section5$TimeLineIn", "SHOW 0.0", broadcaster);
	  					processAnimation(print_writer, "Section5$FreeTextIn", "SHOW 0.0", broadcaster);	
	  				}
					TimeUnit.MILLISECONDS.sleep(200);
					if(infobar.getLast_bottom_right_section() != null && !infobar.getLast_bottom_right_section().trim().isEmpty()) {
						processAnimation(print_writer, "Section4_N_5$ComparisonIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section4_N_5$DotBallIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section4_N_5$BallsSinceIn", "SHOW 0.0", broadcaster);
							
					}
					TimeUnit.MILLISECONDS.sleep(500);
					if(infobar.getLast_middle_section() != null && !infobar.getLast_middle_section().trim().isEmpty()) {
						processAnimation(print_writer, "Section3$EquationIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section3$ProjectedIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section3$BallsSinceIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section3$BoundariesIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section3$LastWicketIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section3$FreeTextSmallIn", "SHOW 0.0", broadcaster);
							
					}
					TimeUnit.MILLISECONDS.sleep(500);
					if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
						processAnimation(print_writer, "Section2$TossIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section2$CurRunRateIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section2$CRR_RRRIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section2$NextInIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section2$TargetIn", "SHOW 0.0", broadcaster);
						processAnimation(print_writer, "Section2$PartnershipIn", "SHOW 0.0", broadcaster);
					}
					TimeUnit.MILLISECONDS.sleep(500);
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitOut START \0");
					infobar.setLast_full_section("");infobar.setFull_section("");
					infobar.setLast_bottom_right_section("");infobar.setBottom_right_section("");
					infobar.setLast_bottom_right_bottom_section("");infobar.setBottom_right_bottom_section("");
					infobar.setLast_top_section("");infobar.setTop_section("");
					TimeUnit.MILLISECONDS.sleep(500);
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
				case "PARTNERSHIP_LT":
					AnimateOutGraphics(print_writer, "PARTNERSHIP_LT");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"LT_FRAME");
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
				case "THISSERIES":
					AnimateOutGraphics(print_writer, "THISSERIES");
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
				case "TEAMLINEUP_PHOTOS":
					AnimateOutGraphics(print_writer, "TEAMLINEUP_PHOTOS");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "TEAMLINEUP_SUBS":
					AnimateOutGraphics(print_writer, "TEAMLINEUP_SUBS");
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
				case "FFTARGET":
					AnimateOutGraphics(print_writer, "FFTARGET");
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
				case "PLAYOFFS":
					AnimateOutGraphics(print_writer, "PLAYOFFS");
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
				case "INNING_SUMMARY":
					AnimateOutGraphics(print_writer, "INNING_SUMMARY");
					TimeUnit.SECONDS.sleep(1);
					
					which_graphic_on_screen = "";
					resetInfobarAnimation(print_writer,"FF_FRAME");
					break;
				case "LT-MANHATTAN":
					AnimateOutGraphics(print_writer, "LT-MANHATTAN");
					TimeUnit.SECONDS.sleep(1);
					if(infobar.isInfobar_on_screen() == true) {
						which_graphic_on_screen = "SCOREBUG";
					}else {
						which_graphic_on_screen = "";
					}
//					resetInfobarAnimation(print_writer,"LT_FRAME");
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
			case "ANIMATE-OUT-SECTION4_N_5":
				if(infobar.getLast_bottom_right_section() != null && 
				!infobar.getLast_bottom_right_section().trim().isEmpty()) { // section4 to bottomright section

					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonOut", "START", broadcaster);
						break;
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					case "TOURNAMENT_SIX":
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case "TOURNAMENT_FOUR":
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;	
					}
					infobar.setBottom_right_bottom_section("BOWLINGEND");
					infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
					infobar.setBottom_right_top_section(CricketUtil.BOWLER);
					infobar = populateVizInfobarRightTop(infobar, false,print_writer, match, broadcaster);
					
					TimeUnit.MILLISECONDS.sleep(600);
					processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
					processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
					switch(infobar.getBottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						if(CricketFunctions.getEventsText(CricketUtil.OVER,infobar.getPlayer_id(),",", match.getEventFile().getEvents(),0).split(",").length <= 9) {
							processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
						}else {
							processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						}
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
						break;
					case "BOWLINGEND":
						processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section5$ExtrasIn", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(600);
				}
				break;
			case "ANIMATE-OUT-SECTION4":
				if(infobar.getLast_middle_section() != null && !infobar.getLast_middle_section().trim().isEmpty()) {
					switch (infobar.getLast_middle_section().toUpperCase()) {
					case CricketUtil.BATSMAN:
						processAnimation(print_writer, "Section3$Section3In", "START", broadcaster);
						break;
					case "EQUATION":
						processAnimation(print_writer, "Section3$EquationOut", "START", broadcaster);
						break;
					case "PROJECTED":
						processAnimation(print_writer, "Section3$ProjectedOut", "START", broadcaster);
						break;
					case "LAST_WICKET":
						processAnimation(print_writer, "Section3$LastWicketOut", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section3$FreeTextSmallOut", "START", broadcaster);
						break;
					case "BOUNDARIES":
						processAnimation(print_writer, "Section3$BoundariesOut", "START", broadcaster);
						break;
					case "LAST_BOUNDARY":
						processAnimation(print_writer, "Section3$BallsSinceLastBoundaryOut", "START", broadcaster);
						break;
					}
				}
				TimeUnit.MILLISECONDS.sleep(500);
				
				infobar.setMiddle_section(CricketUtil.BATSMAN);
				infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, broadcaster);
				break;
				
			case "ANIMATE-OUT-SECTION5":
				if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().trim().isEmpty()) {
  					switch(infobar.getLast_full_section().toUpperCase()) {
  					case "TIMELINE":
  						processAnimation(print_writer, "Top_Section$TimeLineOut", "START", broadcaster);
  						break;
  					case "FREETEXT":
  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
  						break;
  					case "BONUS":
  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
  						break;
  					case "EXTRAS":
  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
  						break;	
  					}
  				}
				processAnimation(print_writer, "Top_Section$TopBaseOut", "START", broadcaster);
				TimeUnit.MILLISECONDS.sleep(500);
				
				infobar.setLast_full_section("");infobar.setFull_section("");
				break;
			
			case "ANIMATE-OUT-SPONSOR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SponsorsOut START \0");
				if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
					switch(infobar.getLast_top_section().toUpperCase()) {
					case CricketUtil.TOSS:
						processAnimation(print_writer, "Section2$TossIn", "START", broadcaster);
						break;
					case "CRR":
						processAnimation(print_writer, "Section2$RunRateIn", "START", broadcaster);
						break;
					case "RRR":
						processAnimation(print_writer, "Section2$ReqRunRateIn", "START", broadcaster);
						break;
					case "NEXT_TO_BAT":
						processAnimation(print_writer, "Section2$NextInIn", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section2$TargetIn", "START", broadcaster);
						break;
					case "PARTNERSHIP":
						processAnimation(print_writer, "Section2$PartnershipIn", "START", broadcaster);
						break;
					
					}	
				}
				break;
				
			case "ANIMATE-OUT-DIRECTOR":
				AnimateOutGraphics(print_writer, "DIRECTOR");
//				if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
//					switch(infobar.getLast_top_section().toUpperCase()) {
//					case CricketUtil.TOSS:
//						processAnimation(print_writer, "Section2$TossIn", "START", broadcaster);
//						break;
//					case "CRR":
//						processAnimation(print_writer, "Section2$RunRateIn", "START", broadcaster);
//						break;
//					case "RRR":
//						processAnimation(print_writer, "Section2$ReqRunRateIn", "START", broadcaster);
//						break;
//					case "NEXT_TO_BAT":
//						processAnimation(print_writer, "Section2$NextInIn", "START", broadcaster);
//						break;
//					case "TARGET":
//						processAnimation(print_writer, "Section2$TargetIn", "START", broadcaster);
//						break;
//					case "PARTNERSHIP":
//						processAnimation(print_writer, "Section2$PartnershipIn", "START", broadcaster);
//						break;
//					}	
//				}
				break;
			case "ANIMATE-OUT-POWERPLAY":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayOut START \0");
				break;
			}
			break;
			
		case "BUG_GRAPHICS-OPTIONS": case "HOWOUT_GRAPHICS-OPTIONS": case "BATSMANSTATS_GRAPHICS-OPTIONS": case "BOWLERSTATS_GRAPHICS-OPTIONS": case "NAMESUPER_PLAYER_GRAPHICS-OPTIONS": 
		case "L3PLAYERPROFILE_GRAPHICS-OPTIONS": case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "BOTTOMLEFT_GRAPHICS-OPTIONS": case "BOTTOMRIGHT_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": 
		case "COMPARISION-GRAPHICS-OPTIONS": case "BOTTOM_GRAPHICS-OPTIONS": case "ANIMATE_PLAYINGXI-OPTIONS": case "PROJECTED_GRAPHICS-OPTIONS": case "TARGET_GRAPHICS-OPTIONS": 
		case "PLAYERSUMMARY_GRAPHICS-OPTIONS": case "BUG_DISMISSAL_GRAPHICS-OPTIONS": case "TOP_GRAPHICS-OPTIONS": case "BUG_BOWLER_GRAPHICS-OPTIONS": case "HOWOUT_WITHOUT_FIELDER_GRAPHICS-OPTIONS":
		case "BOWLERDETAILS_GRAPHICS-OPTIONS":	case "NEXTTOBAT_GRAPHICS-OPTIONS": case "BOWLERSUMMARY_GRAPHICS-OPTIONS": case "LANDMARK_GRAPHICS-OPTIONS": case "EQUATION_GRAPHICS-OPTIONS":
		case "POSITION_LANDMARK_GRAPHICS-OPTIONS": case "BATSMAN_THIS_MATCH_GRAPHICS-OPTIONS": case "BOWLER_THIS_MATCH_GRAPHICS-OPTIONS": case "PLAYERS_GRAPHICS-OPTIONS":
		case "BATSMAN_STYLE_GRAPHICS-OPTIONS": case "RIGHT_GRAPHICS-OPTIONS": case "THISSERIES-STATS_GRAPHICS-OPTIONS": case "FF_THISSERIES-STATS_GRAPHICS-OPTIONS":
		case "L3SEASONPROFILE_GRAPHICS-OPTIONS": case "BUG_MULTI_GRAPHICS-OPTIONS":
			return match;
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "L3_MATCH-PROMO_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
		case "BUG_DB_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getBugs()).toString();
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
		
		//Infobar
		case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-DIRECTOR": case "POPULATE-INFOBAR-SECTION5": 
		case "POPULATE-INFOBAR-RIGHT": case "POPULATE-SPONSOR": case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR_RIGHT_LASTXOVER": case "POPULATE-INFOBAR-IDENT":
		case "POPULATE-INFOBAR-MIDDLE":	
			
		//FF	
		case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-FF-PLAYERPROFILE": 
		case "POPULATE-FF-DOUBLETEAMS":  case "POPULATE-FF-MATCHID": case "POPULATE-FF-PLAYINGXI": case "POPULATE-FF-PLAYINGXI_PHOTOS": case "POPULATE-LT-PARTNERSHIP": case "POPULATE-FF-LANDMARK":
		case "POPULATE-PREVIOUS_SUMMARY": case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-POINTS_TABLE": case "POPULATE-MANHATTAN": case "POPULATE-MATCH_PROMO": 
		case "POPULATE-FF-TEAMS_LOGO": case "POPULATE-TIEID-DOUBLE": case "POPULATE-MOSTRUNS": case "POPULATE-MOSTWICKETS": case "POPULATE-MOSTFOURS": case "POPULATE-MOSTSIXES": 
		case "POPULATE-HIGHESTSCORE": case "POPULATE-WORM": case "POPULATE-FF-SCHEDULE": case "POPULATE-FF-LEADERBOARD": case "POPULATE-FF-STATS": case "POPULATE-FF-PLAYERPROFILEBALL": 
		case "POPULATE-BAT_PERFORMER": case "POPULATE-INNING_SUMMARY": case "POPULATE-PLAYOFFS": case "POPULATE-BALL_PERFORMER": case "POPULATE-MOST_RUNS":case "POPULATE-FF-TARGET": 
		case "POPULATE-FF-PLAYINGXI_SUBS5": case "POPULATE-PLAYING_CHANGE_ON1":	case "POPULATE-PLAYING_CHANGE_ON2": case "POPULATE-PLAYING_CHANGE_ON3": case "POPULATE-LOF_SCORECARD":
		case "POPULATE-LOF_BOWLINGCARD": case "POPULATE-TORNAMENT_PLAYER":
		
		//BUG
		case "POPULATE-L3-BUG": case "POPULATE-BUG_POWERPLAY": case "POPULATE-LT-BUG_HIGHLIGHT": case "POPULATE-MULTI_PARTNERSHIP": case "POPULATE-L3-BUG-DISMISSAL":
		case "POPULATE-L3-BUG-DB": case "POPULATE-L3-BUG-BOWLER": case "POPULATE-L3-BUGTARGET": case "POPULATE-BUGPARTNERSHIP": case "POPULATE-L3-BUG-TOSS":
			
		//POP-UP
		case "POPULATE-MINI-BATTINGCARD": case "POPULATE-MINI-BOWLINGCARD": case "POPULATE-FF-BATGRIFF": case "POPULATE-FF-BALLGRIFF":
		
		//LT'S
		case "POPULATE-L3-HOWOUT":case "POPULATE-L3-BATSMANSTATS": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-LT-PROJECTED": 
		case "POPULATE-L3-TARGET": case "POPULATE-L3-TEAMSUMMARY": case "POPULATE-L3-PLAYERSUMMARY": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-L3-FALLOFWICKET": 
		case "POPULATE-L3-COMPARISION": case "POPULATE-LT-MATCHID": case "POPULATE-L3-BOWLERSTATS": case "POPULATE-L3-SPLIT": case "POPULATE-L3-HOWOUT_WITHOUT_FIELDER":
		case "POPULATE-L3-BOWLERSUMMARY": case "POPULATE-L3-BOWLERDETAILS": case "POPULATE-LT-POWERPLAY": case "POPULATE-LT-EQUATION":  case "POPULATE-L3-BATSMAN_THIS_MATCH": 
		case "POPULATE-L3-BOWLER_THIS_MATCH":   case "POPULATE-LTPOINTS_TABLE":	case "POPULATE-BOWLER_STYLE": case "POPULATE-BATSMAN_STYLE": case "POPULATE-L3MATCH_PROMO": 
		case "POPULATE-HOWOUT_QUICK":  case "POPULATE-L3-THISSERIES": case "POPULATE-FF-THISSERIES":  case "POPULATE-L3-PLAYERPROFILEBAT": case "POPULATE-LT-LINEUP": 
		case "POPULATE-LT-MANHATTAN":case "POPULATE-BT-POWERPLAY": case "POPULATE-INN_BUILDER": case "POPULATE-LT_SEASON": case "POPULATE-LT_BOWLERSPEED": 
		case "POPULATE-BALLSINCE": case "POPULATE-NEXT_TO_BAT": case "POPULATE-PARTNERSHIP_LT": case "POPULATE-LT-PLAYINGXI": case "POPULATE-PHASE":

			if(which_graphic_on_screen == "SCOREBUG" || which_graphic_on_screen == "IDENT") {
			}else if(which_graphic_on_screen == "TEAMLINEUP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-PLAYING_CHANGE_ON1")||
					which_graphic_on_screen == "TEAMLINEUP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-PLAYING_CHANGE_ON2")||
					which_graphic_on_screen == "TEAMLINEUP" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-PLAYING_CHANGE_ON3")){
				
			}else if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				 
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				 
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				 
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE")) {
			}
			else if(which_graphic_on_screen != "") {
				AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
			}

			switch(whatToProcess.toUpperCase()) {
			case "POPULATE-INFOBAR-TOP": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-DIRECTOR": case "POPULATE-INFOBAR-SECTION5":
			case "POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-RIGHT": case "POPULATE-BT-POWERPLAY": case "POPULATE-INFOBAR_RIGHT_LASTXOVER":
			case "POPULATE-PLAYING_CHANGE_ON1":	case "POPULATE-PLAYING_CHANGE_ON2": case "POPULATE-PLAYING_CHANGE_ON3": case "POPULATE-SPONSOR":
			case "POPULATE-INFOBAR-MIDDLE":	
				break;
			case "POPULATE-L3-INFOBAR": case "POPULATE-INFOBAR-IDENT":
				if(infobar.isInfobar_on_screen() == true) {
					break;
				}else {
					//scenes.get(0).scene_load(print_writer, broadcaster);
				}
				break;
			case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY":case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-TORNAMENT_PLAYER":
			case "POPULATE-FF-PLAYERPROFILEBALL": case "POPULATE-FF-DOUBLETEAMS": case "POPULATE-FF-MATCHID": case "POPULATE-FF-PLAYINGXI": case "POPULATE-FF-PLAYINGXI_PHOTOS": case "POPULATE-LT-PARTNERSHIP":
			case "POPULATE-PREVIOUS_SUMMARY":  case "POPULATE-POINTS_TABLE":case "POPULATE-MANHATTAN": case "POPULATE-MATCH_PROMO": case "POPULATE-FF-TEAMS_LOGO": 
			case "POPULATE-TIEID-DOUBLE": case "POPULATE-MOSTRUNS": case "POPULATE-MOSTWICKETS": case "POPULATE-MOSTFOURS": case "POPULATE-MOSTSIXES": case "POPULATE-HIGHESTSCORE": 
			case "POPULATE-WORM": case "POPULATE-FF-SCHEDULE": case "POPULATE-FF-THISSERIES": case "POPULATE-FF-LEADERBOARD": case "POPULATE-FF-STATS": case "POPULATE-BAT_PERFORMER": 
			case "POPULATE-INNING_SUMMARY": case "POPULATE-PLAYOFFS": case "POPULATE-BALL_PERFORMER":case "POPULATE-MOST_RUNS":case "POPULATE-FF-TARGET": case "POPULATE-FF-PLAYINGXI_SUBS5":
			case "POPULATE-LT-PLAYINGXI":
				
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				 
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				 
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "POINTSTABLE" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE") ||
				 
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-SCORECARD") || 
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-MATCHSUMMARY") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-BOWLINGCARD") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-FF-PARTNERSHIP") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BAT_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-BALL_PERFORMER") ||
				 which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER" && whatToProcess.toUpperCase().equalsIgnoreCase("POPULATE-POINTS_TABLE")) {
				//AnimateOutGraphics(print_writer, which_graphic_on_screen.toUpperCase());
			}else {
				scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
				scenes.get(1).scene_load(print_writer,broadcaster);
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE SHOW 0.0 \0");
			}
				
				break;
			default:
				scenes.get(2).setScene_path(valueToProcess.split(",")[0]);
				scenes.get(2).scene_load(print_writer,broadcaster);
				print_writer.println("-1 RENDERER*STAGE SHOW 0.0 \0");
				break;
			}
			
			switch(whatToProcess.toUpperCase()) {
			case "POPULATE-PHASE":
				populatePhaseWise(print_writer,valueToProcess.split(",")[0],match,broadcaster);
				break;
			case "POPULATE-PLAYING_CHANGE_ON1":	
				print_writer.println("-1 RENDERER PREVIEW SCENE*/Default/FairBreak/TeamLineUpImage C:/Temp/Preview.jpg In 3.543 DataIn 0.000 Images_In 1.400 \0");
				break;
			case "POPULATE-PLAYING_CHANGE_ON2":
				print_writer.println("-1 RENDERER PREVIEW SCENE*/Default/FullFrames C:/Temp/Preview.jpg In 4.000 Team$Change 4.220 \0");
				break;
			case "POPULATE-PLAYING_CHANGE_ON3":
				print_writer.println("-1 RENDERER PREVIEW SCENE*/Default/FullFrames C:/Temp/Preview.jpg In 4.000 Team$Change 8.040 \0");
				break;
			case "POPULATE-L3-BUG-TOSS":
				populateBugToss(print_writer,valueToProcess.split(",")[0],match,broadcaster);
				break;
			case "POPULATE-NEXT_TO_BAT":
				populateLTNextToBat(print_writer,valueToProcess.split(",")[0],cricketService.getAllStats(),cricketService.getAllPlayer(),match, broadcaster, config);
				break;
			case "POPULATE-BALLSINCE":
				populateLTBallSince(print_writer,valueToProcess.split(",")[0],match, broadcaster);
				break;
			case "POPULATE-FF-TARGET":
				populateFFTarget(print_writer,valueToProcess.split(",")[0],match,broadcaster, config);
				break;
			case "POPULATE-LT_BOWLERSPEED":
				populateLTBowlerSpeed(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[3]),match, broadcaster);
				break;
			case "POPULATE-BUG_POWERPLAY":
				populateBugPowerPLay(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-MULTI_PARTNERSHIP":
				populateBugMultipartnership(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
				break;
			case "POPULATE-LT-BUG_HIGHLIGHT":
				populateBugHighlight(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-LT_SEASON":
				populateLTSeasonProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]).intValue(),
						valueToProcess.split(",")[2],valueToProcess.split(",")[3],tournament_matches,cricketService,cricketService.getSeasons(),cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-MOST_RUNS":
				populateMostRuns(print_writer, valueToProcess.split(",")[0], valueToProcess.split(",")[1],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null),
						cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-FF-BALLGRIFF":
				populateBallGriff(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[3]),tournament_matches,cricketService.getAllPlayer(),
						cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-FF-BATGRIFF":
				populateBatGriff(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[3]),tournament_matches,cricketService.getAllPlayer(), 
						cricketService.getTeams(),match, broadcaster);
				break;
			case "POPULATE-BT-POWERPLAY":
				populateInfobarPowerPlay(print_writer,valueToProcess,broadcaster);
				break;
			case "POPULATE-BUGPARTNERSHIP":
				populateBugPartnership(print_writer, valueToProcess.split(",")[0],match, broadcaster);
				break;
			case "POPULATE-LT-LINEUP":
				//System.out.println("SCENE " + valueToProcess.split(",")[0] + " Inning "  + valueToProcess.split(",")[1] + " ICON_DATA " + valueToProcess.split(",")[2]);
				populateLineup(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						cricketService,cricketService.getTeams(),cricketService.getAllPlayer(),match, broadcaster, config);
				
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
			case "POPULATE-BALL_PERFORMER":
				populateBallPerformer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster, config);
				bocf.setType(valueToProcess.split(",")[2]);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE") {	
					print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					  if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 BattingCardOut 0.500 \0");
					  }else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 SummaryOut 0.500 \0");
					  }else if(which_graphic_on_screen == "POINTSTABLE") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 PointsTableOut 0.500 \0");
					  }
				}
				TimeUnit.SECONDS.sleep(2);
				break;
			case "POPULATE-BAT_PERFORMER":
				populateBatPerformer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], Integer.valueOf(valueToProcess.split(",")[3]), match, broadcaster, config);
				bcf.setType(valueToProcess.split(",")[2]);
//				if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD_PERFORMER"||which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
//						which_graphic_on_screen == "POINTSTABLE") {	
//					//print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
//					if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
//						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 BowlingCardOut 0.500 \0");
//					}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
//						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 SummaryOut 0.500 \0");
//					}else if(which_graphic_on_screen == "POINTSTABLE") {
//						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PointsTableOut 0.500 \0");
//					}
//				}
				TimeUnit.SECONDS.sleep(2);
				break;
			case "POPULATE-LOF_BOWLINGCARD":
				populatelofBowlingcard(print_writer, valueToProcess.split(",")[0], false, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster, config);
				break;
			case "POPULATE-LOF_SCORECARD":
				populatelofScorecard(print_writer, valueToProcess.split(",")[0],false, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster, config);
				break;
			case "POPULATE-FF-SCORECARD":
				populateScorecard(print_writer, valueToProcess.split(",")[0],false, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				
				/*if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE") {	
					print_writer.println("-1 RENDERER*TREE*$Main$All$BattingCard*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 BowlingCardOut 0.500 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 SummaryOut 0.500 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.363 BatOffsetIn 1.363 PointsTableOut 0.500 \0");
					}
				}
				TimeUnit.SECONDS.sleep(2);*/
				break;
				
			case "POPULATE-FF-BOWLINGCARD":
				populateBowlingcard(print_writer, valueToProcess.split(",")[0], false, Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY" || 
						which_graphic_on_screen == "POINTSTABLE") {	
					//print_writer.println("-1 RENDERER*TREE*$Main$All$BowlingCard*ACTIVE SET " + "0" + "\0");
					
					  /*if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 BattingCardOut 0.500 \0");
					  }else if(which_graphic_on_screen == "BATBALLSUMMARY_MATCHSUMMARY") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 SummaryOut 0.500 \0");
					  }else if(which_graphic_on_screen == "POINTSTABLE") {
						  print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.100 BowlingCardIn 1.763 BallOffsetIn 1.363 PointsTableOut 0.500 \0");
					  }*/
					 
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
				break;
				
			case "POPULATE-FF-MATCHSUMMARY":
				populateMatchsummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2], match, broadcaster,cricketService.getVariousTexts(), config);
				if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD_PERFORMER" || which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD" || which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD" || 
						which_graphic_on_screen == "POINTSTABLE") {	
					//print_writer.println("-1 RENDERER*TREE*$Main$All$Summary*ACTIVE SET " + "0" + "\0");
					if(which_graphic_on_screen == "BATBALLSUMMARY_SCORECARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.600 Summary_In 1.900 BattingCardOut 1.020 \0");
					}else if(which_graphic_on_screen == "BATBALLSUMMARY_BOWLINGCARD") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.600 Summary_In 1.900 BowlingCardOut 1.020 \0");
					}else if(which_graphic_on_screen == "POINTSTABLE") {
						print_writer.println("-1 RENDERER PREVIEW SCENE*" + valueToProcess.split(",")[0] + " C:/Temp/Preview.jpg In 3.600 Summary_In 1.900 PointsTableOut 1.020 \0");
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
				populateTarget(print_writer,valueToProcess.split(",")[0], match, broadcaster, config);
				break;
			case "POPULATE-LT-POWERPLAY":
				populateLtPowerPlay(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-BUGTARGET":
				populateBugTarget(print_writer,valueToProcess.split(",")[0], match, broadcaster);
				break;
			case "POPULATE-L3-NAMESUPER-PLAYER":
				populateNameSuperPlayer(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
						Integer.valueOf(valueToProcess.split(",")[3]),cricketService.getAllPlayer(), match, broadcaster);
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
				populateLTMatchId(print_writer,valueToProcess.split(",")[0], match, broadcaster);
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
			case "POPULATE-PARTNERSHIP_LT":
				populatePartnershipLt(print_writer, valueToProcess.split(",")[0], match, broadcaster, config);
				break;
			case "POPULATE-INN_BUILDER":
				populateInnBuilder(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
						Integer.valueOf(valueToProcess.split(",")[2]), match, broadcaster);
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
			case "POPULATE-TORNAMENT_PLAYER":
				populatePlayerOfTheTournament(print_writer,valueToProcess.split(",")[0],cricketService.getAllPlayer(),
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
						,match, broadcaster, config);
				break;
			case "POPULATE-L3-THISSERIES":
				populateThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
						,match, broadcaster, config);
				break;
			case "POPULATE-FF-THISSERIES":
				populateFFThisSeries(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
						CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)
						,match, broadcaster);
				break;
			case "POPULATE-L3-PLAYERPROFILE":
				//System.out.println("valueToProcess = " + valueToProcess);
				for(Statistics stats : cricketService.getAllStats()) {
					//System.out.println("player id = " + stats.getPlayer_id());
					if(stats.getPlayer_id().intValue()== Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						//System.out.println("Match Found");
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
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
					if(stats.getPlayer_id().intValue() == Integer.valueOf(valueToProcess.split(",")[1]).intValue()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populateLTPlayerProfileBat(print_writer,valueToProcess.split(",")[0],
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,match, broadcaster);
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYERPROFILE":					
				for(Statistics stats : cricketService.getAllStats()) {
					if(stats.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[1])) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						
						//System.out.println("1." + stats.getStats_type().getStats_short_name() + " 2." + valueToProcess.split(",")[2]);
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
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
						stats = CricketFunctions.updateTournamentDataWithStats(stats, tournament_matches, match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, match, CricketUtil.FULL);
						if(stats.getStats_type().getStatsShortName().equalsIgnoreCase(valueToProcess.split(",")[2])) {
							populatePlayerProfileBall(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
									valueToProcess.split(",")[2],valueToProcess.split(",")[3],stats,cricketService.getAllPlayer(),match, broadcaster, config);
						}
					}
				}
				break;
			case "POPULATE-FF-PLAYINGXI_PHOTOS":
				populatePlayingXIPhotos(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getAllPlayer(),
						match, broadcaster, config);
				break;
			case "POPULATE-LT-PLAYINGXI":
				populateLtPlayingXI(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],cricketService.getAllPlayer(),
						match, broadcaster, config);
				break;
			case "POPULATE-FF-PLAYINGXI":
				populatePlayingXI(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getAllPlayer(),
						match, broadcaster, config);
				break;
			case "POPULATE-FF-PLAYINGXI_SUBS5":
				populatePlayingXISubs(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getAllPlayer(),
						match, broadcaster, config);
				break;
			case "POPULATE-FF-DOUBLETEAMS":
				populateDoubleteams(print_writer,valueToProcess,cricketService.getAllPlayer(), match, broadcaster);
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
				
				populatePointsTable(print_writer, valueToProcess.split(",")[0],league_table.getLeagueTeams(),cricketService.getTeams(),broadcaster,match,cricketService.getVariousTexts());
				break;
			case "POPULATE-PLAYOFFS":
				populatePlayoffs(print_writer, valueToProcess.split(",")[0],cricketService.getPlayOff(),cricketService.getTeams(),broadcaster,match);
				break;
			case "POPULATE-LTPOINTS_TABLE":
				LeagueTable league1_table = null;
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML).exists()) {
					league1_table = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
							new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + CricketUtil.LEAGUETABLE_XML));
				}
				populateLtPointsTable(print_writer, valueToProcess.split(",")[0], league1_table.getLeagueTeams(),match,broadcaster);
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
			case "POPULATE-INNING_SUMMARY":
				populateInningSummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]),
						cricketService.getTeams(), match, broadcaster, config);
				break;
			case "POPULATE-LT-MANHATTAN":
				populateLtManhattan(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-WORM":
				populateWorm(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), match, broadcaster);
				break;
			case "POPULATE-PREVIOUS_SUMMARY":
				List<MatchAllData> cricket_matches = new ArrayList<MatchAllData>();
				cricket_matches.clear();
				MatchAllData cricket_match = new MatchAllData();
				EventFile cricket_event = new EventFile();
				
				for(File file :  new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
					@Override
				    public boolean accept(File pathname) {
				        String name = pathname.getName().toLowerCase();
				        return name.endsWith(".xml") && pathname.isFile();
				    }
				})) {
//					cricket_match = (CricketFunctions.populateMatchVariables(cricketService,(MatchAllData) JAXBContext.newInstance(MatchAllData.class).createUnmarshaller().unmarshal(
//						new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + file.getName()))));
						for(Fixture fx : cricketService.getFixtures()) {
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
				populatePreviousSummary(print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],cricket_matches,cricketService.getFixtures(), 
						match, broadcaster, config);
				break;
			case "POPULATE-TIEID-DOUBLE":
				populateTieIdDouble(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1],cricketService.getFixtures(),cricketService.getTeams(), 
						match, broadcaster);
				break;
			case "POPULATE-L3-INFOBAR":
				
				infobar.setMiddle_section(valueToProcess.split(",")[1]);
				infobar.setBottom_right_top_section(valueToProcess.split(",")[2]);
				infobar.setTop_section(valueToProcess.split(",")[3]);
				infobar.setBottom_right_bottom_section(valueToProcess.split(",")[4]);
				
				populateInfobar(infobar, print_writer, valueToProcess.split(",")[0],match, broadcaster);
				
				processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
				processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
				processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
				processAnimation(print_writer, "Section5$ExtrasOut", "START", broadcaster);
				
				switch(infobar.getTop_section().toUpperCase()) {
				case "TARGET":
					processAnimation(print_writer, "Section2$TargetIn", "START", broadcaster);
					break;
				case CricketUtil.TOSS:
					processAnimation(print_writer, "Section2$TossIn", "START", broadcaster);
					break;
				case "CRR":
					processAnimation(print_writer, "Section2$CurRunRateIn", "START", broadcaster);
					break;
				case "RRR":
					processAnimation(print_writer, "Section2$CRR_RRRIn", "START", broadcaster);
					break;
				case "PARTNERSHIP":
					processAnimation(print_writer, "Section2$PartnershipIn", "START", broadcaster);
					break;
				case "SUPER_OVER":
					processAnimation(print_writer, "Section2$TossIn", "START", broadcaster);
					break;	
				}
				
				switch(infobar.getBottom_right_bottom_section().toUpperCase()){
				case CricketUtil.OVER:
					if(CricketFunctions.getEventsText(CricketUtil.OVER,infobar.getPlayer_id(),",", match.getEventFile().getEvents(),0).split(",").length <= 9) {
						processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
					}else {
						processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
					}
					break;
				case "ECONOMY":
					processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
					break;
				case "BOWLINGEND":
					processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
					break;
				}
				processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
				processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
				infobar.setIdent_section("");
				break;
				
			case "POPULATE-INFOBAR-IDENT":
				infobar.setIdent_section(valueToProcess.split(",")[1]);
				populateInfobarIdent(infobar, false, valueToProcess.split(",")[0],print_writer, match, broadcaster);
				break;
				
			case "POPULATE-INFOBAR_RIGHT_LASTXOVER":
				
				if(infobar.getLast_bottom_right_section() != null 
					&& !infobar.getLast_bottom_right_section().trim().isEmpty()) {
					
					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case "EQUATION":
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
							CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
							processAnimation(print_writer, "Section4$FreeTextOut", "START", broadcaster);
						}else {
							processAnimation(print_writer, "Section4$EquationOut", "START", broadcaster);
						}
						break;
					case "PROJECTED":
						processAnimation(print_writer, "Section4$ProjectedScoreOut", "START", broadcaster);
						break;
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4$AtThisStageOut", "START", broadcaster);
						break;
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4$DotBallsOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4$TournamentFoursOut", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4$TournamentSixesOut", "START", broadcaster);
						break;
					case CricketUtil.BOUNDARY:
						processAnimation(print_writer, "Section4$BallsSinceOut", "START", broadcaster);
						break;
					case "BOUNDARIES":
						processAnimation(print_writer, "Section4$InningsBoundariesOut", "START", broadcaster);
						break;
					case "LAST_WICKET":
						processAnimation(print_writer, "Section4$LastWicketOut", "START", broadcaster);
						break;
					case "TOURNAMENT-NAME":
						processAnimation(print_writer, "Section4$FreeTextOut", "START", broadcaster);
						break;
					case "STATISTICS":
						processAnimation(print_writer, "Section4$FreeTextOut", "START", broadcaster);
						break;
					case "LASTXOVERS":
						processAnimation(print_writer, "Section4$LastXBallsOut", "START", broadcaster);
						break;
					}
					
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setBottom_right_section("LASTXOVERS");
					populateInfobarLastxOver(infobar,false,print_writer,Integer.valueOf(valueToProcess), match, broadcaster);
					
					processAnimation(print_writer, "Section4$LastXBallsIn", "START", broadcaster);

				}else if(infobar.getLast_bottom_right_bottom_section() != null 
					&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // Normal change on
					
					processAnimation(print_writer, "Section4$Section4In", "START", broadcaster);
			
					TimeUnit.MILLISECONDS.sleep(200);
					infobar.setBottom_right_section("LASTXOVERS");
					populateInfobarLastxOver(infobar,false,print_writer, Integer.valueOf(valueToProcess), match, broadcaster);
					
					processAnimation(print_writer, "Section4$LastXBallsIn", "START", broadcaster);
					
				}
				break;
			case "POPULATE-INFOBAR-PROMPT":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						for(InfobarStats ibs : cricketService.getInfobarStats())
	                          if(ibs.getOrder() == Integer.valueOf(valueToProcess)) {
	                        	  if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().trim().isEmpty()) {
	                  					switch(infobar.getLast_full_section().toUpperCase()) {
	                  					case "TIMELINE":
	                  						processAnimation(print_writer, "Section5$TimeLineOut", "START", broadcaster);
	                  						break;
	                  					case "FREETEXT":
	                  						processAnimation(print_writer, "Section5$FreeTextOut", "START", broadcaster);
	                  						break;
	                  					}
	                  					
	                  					TimeUnit.MILLISECONDS.sleep(500);
	                					processAnimation(print_writer, "Section5$Section5Out", "START", broadcaster);
	                					
	                					TimeUnit.MILLISECONDS.sleep(500);
	                					processAnimation(print_writer, "Section4$Section4In", "START", broadcaster);
	                  					
	                  					TimeUnit.MILLISECONDS.sleep(500);
	                  					infobar.setBottom_right_section("STATISTICS");
	                  					populateInfobarFreeText(infobar,false,print_writer, ibs, match, broadcaster);
	                  					
	                  					processAnimation(print_writer, "Section4$FreeTextIn", "START", broadcaster);
	                  				}else if(infobar.getLast_bottom_right_section() != null 
	                  					&& !infobar.getLast_bottom_right_section().trim().isEmpty()) {
	                  					
	                  					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
	                  					case "EQUATION":
	                  						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
	                  							CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
	                  							processAnimation(print_writer, "Section4$FreeTextOut", "START", broadcaster);
	                  						}else {
	                  							processAnimation(print_writer, "Section4$EquationOut", "START", broadcaster);
	                  						}
	                  						break;
	                  					case "PROJECTED":
	                  						processAnimation(print_writer, "Section4$ProjectedScoreOut", "START", broadcaster);
	                  						break;
	                  					case CricketUtil.COMPARE:
	                  						processAnimation(print_writer, "Section4$AtThisStageOut", "START", broadcaster);
	                  						break;
	                  					case CricketUtil.DOT:
	                  						processAnimation(print_writer, "Section4$DotBallsOut", "START", broadcaster);
	                  						break;
	                  					case CricketUtil.FOUR:
	                  						processAnimation(print_writer, "Section4$TournamentFoursOut", "START", broadcaster);
	                  						break;
	                  					case CricketUtil.SIX:
	                  						processAnimation(print_writer, "Section4$TournamentSixesOut", "START", broadcaster);
	                  						break;
	                  					case CricketUtil.BOUNDARY:
	                  						processAnimation(print_writer, "Section4$BallsSinceOut", "START", broadcaster);
	                  						break;
	                  					case "BOUNDARIES":
	                  						processAnimation(print_writer, "Section4$InningsBoundariesOut", "START", broadcaster);
	                  						break;
	                  					case "LAST_WICKET":
	                  						processAnimation(print_writer, "Section4$LastWicketOut", "START", broadcaster);
	                  						break;
	                  					case "TOURNAMENT-NAME":
	                  						processAnimation(print_writer, "Section4$FreeTextOut", "START", broadcaster);
	                  						break;
	                  					case "STATISTICS":
	                  						processAnimation(print_writer, "Section4$FreeTextOut", "START", broadcaster);
	                  						break;
	                  					}
	                  					
	                  					TimeUnit.MILLISECONDS.sleep(500);
	                  					infobar.setFull_section("STATISTICS");
	                  					populateInfobarFreeText(infobar,false,print_writer, ibs, match, broadcaster);
	                  					
	                  					processAnimation(print_writer, "Section4$FreeTextIn", "START", broadcaster);
	
	                  				}else if(infobar.getLast_bottom_right_bottom_section() != null 
	                  					&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // Normal change on
	                  					
	                  					processAnimation(print_writer, "Section4$Section4In", "START", broadcaster);
	                  					TimeUnit.MILLISECONDS.sleep(500);
	                					processAnimation(print_writer, "BowlerOut", "START", broadcaster);
	                					TimeUnit.MILLISECONDS.sleep(500);
	                					switch(infobar.getLast_bottom_right_bottom_section().toUpperCase()){
	                					case CricketUtil.OVER:
	                						if(CricketFunctions.getEventsText(CricketUtil.OVER,infobar.getPlayer_id(),",", match.getEventFile().getEvents(),0).split(",").length <= 9) {
		                						processAnimation(print_writer, "Section3$ThisOverIn", "START", broadcaster);
		                					}else {
		                						processAnimation(print_writer, "Section3$FreeTextIn", "START", broadcaster);
		                					}
	                						break;
	                					case "ECONOMY":
	                						processAnimation(print_writer, "Section3$EconomyOut", "START", broadcaster);
	                						break;
	                					case "BOWLINGEND":
	                						processAnimation(print_writer, "Section3$BowlingEndOut", "START", broadcaster);
	                						break;
	                					case "EXTRAS":
	                						processAnimation(print_writer, "Section3$ExtrasOut", "START", broadcaster);
	                						break;
	                					}
	    	                  			
	                  					TimeUnit.MILLISECONDS.sleep(500);
	                  					infobar.setBottom_right_section("STATISTICS");
	                  					populateInfobarFreeText(infobar,false,print_writer, ibs, match, broadcaster);
	                  					
	                  					processAnimation(print_writer, "Section4$FreeTextIn", "START", broadcaster);
	                  					
	                  					infobar.setLast_full_section("");infobar.setFull_section("");
	                  					infobar.setLast_bottom_right_bottom_section(""); infobar.setBottom_right_bottom_section("");
	                  					infobar.setLast_bottom_right_top_section(""); infobar.setBottom_right_top_section("");
	                  				}
	                          }
	                    }
					}
				break;
			case "POPULATE-DIRECTOR":
				populateInfobarDirector(print_writer,valueToProcess,broadcaster);
				break;
			case "POPULATE-SPONSOR":
				populateInfobarSponsor(print_writer,valueToProcess,broadcaster);
				break;
			case "POPULATE-INFOBAR-SECTION5":
				if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().trim().isEmpty()) {
					switch(infobar.getLast_full_section().toUpperCase()) {
  					case "TIMELINE":
  						processAnimation(print_writer, "Top_Section$TimeLineOut", "START", broadcaster);
  						break;
  					case "FREETEXT":
  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
  						break;
  					case "BONUS":
  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
  						break;
  					case "EXTRAS":
  						processAnimation(print_writer, "Top_Section$FreeTextOut", "START", broadcaster);
  						break;	
  					}
					
					TimeUnit.MILLISECONDS.sleep(500);
					infobar.setFull_section(valueToProcess.split(",")[0]);
					infobar = populateSection5(infobar,false,print_writer,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],match,broadcaster);
					processAnimation(print_writer, "Top_Section$TopBaseIn", "START", broadcaster);
//					TimeUnit.MILLISECONDS.sleep(500);
					switch (infobar.getFull_section().toUpperCase()) {
					case "TIMELINE":
						processAnimation(print_writer, "Top_Section$TimeLineIn", "START", broadcaster);
						break;
					case "FREETEXT":
						processAnimation(print_writer, "Top_Section$FreeTextIn", "START", broadcaster);
						break;
					case "BONUS":
  						processAnimation(print_writer, "Top_Section$FreeTextIn", "START", broadcaster);
  						break;
					case "EXTRAS":
  						processAnimation(print_writer, "Top_Section$FreeTextIn", "START", broadcaster);
  						break;	
					}
				}else {
					infobar.setFull_section(valueToProcess.split(",")[0]);
					infobar = populateSection5(infobar,false,print_writer,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],match,broadcaster);
					processAnimation(print_writer, "Top_Section$TopBaseIn", "START", broadcaster);
//					TimeUnit.MILLISECONDS.sleep(500);
					switch (infobar.getFull_section().toUpperCase()) {
					case "TIMELINE":
						processAnimation(print_writer, "Top_Section$TimeLineIn", "START", broadcaster);
						break;
					case "FREETEXT":
						processAnimation(print_writer, "Top_Section$FreeTextIn", "START", broadcaster);
						break;
					case "BONUS":
  						processAnimation(print_writer, "Top_Section$FreeTextIn", "START", broadcaster);
  						break;
					case "EXTRAS":
  						processAnimation(print_writer, "Top_Section$FreeTextIn", "START", broadcaster);
  						break;	
					}
				}
				break;

			case "POPULATE-INFOBAR-BOTTOMRIGHT":
				if(infobar.getLast_bottom_right_section() != null && 
						!infobar.getLast_bottom_right_section().trim().isEmpty()) { // section4 to bottomright section

					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonOut", "START", broadcaster);
						break;
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					case "TOURNAMENT_SIX":
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case "TOURNAMENT_FOUR":
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;	
					}
					
					infobar.setBottom_right_bottom_section(valueToProcess);
					infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
//					processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
//					processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
//					processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
//					processAnimation(print_writer, "Section5$ExtrasOut", "START", broadcaster);
					infobar.setBottom_right_top_section(CricketUtil.BOWLER);
					infobar = populateVizInfobarRightTop(infobar, false,print_writer, match, broadcaster);
					
					TimeUnit.MILLISECONDS.sleep(600);
					processAnimation(print_writer, "ALL_SECTION$Section4In", "START", broadcaster);
					processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
					switch(infobar.getBottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						if(CricketFunctions.getEventsText(CricketUtil.OVER,infobar.getPlayer_id(),",", match.getEventFile().getEvents(),0).split(",").length <= 9) {
							processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
						}else {
							processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						}
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
						break;
					case "BOWLINGEND":
						processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section5$ExtrasIn", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(600);
//					processAnimation(print_writer, "Section4$Section4Out", "START", broadcaster);
					
				} else if(infobar.getLast_bottom_right_top_section() != null && infobar.getLast_bottom_right_bottom_section() != null 
						&& !infobar.getLast_bottom_right_top_section().trim().isEmpty() 
						&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // Normal change on

					switch(infobar.getLast_bottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						if(CricketFunctions.getEventsText(CricketUtil.OVER,infobar.getPlayer_id(),",", match.getEventFile().getEvents(),0).split(",").length <= 9) {
							processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
						}else {
							processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
						}
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
						break;
					case "BOWLINGEND":
						processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section5$ExtrasOut", "START", broadcaster);
						break;
					}
					TimeUnit.MILLISECONDS.sleep(500);
					infobar.setBottom_right_bottom_section(valueToProcess);
					infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
					processAnimation(print_writer, "ALL_SECTION$Section5In", "START", broadcaster);
					switch(infobar.getBottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						if(CricketFunctions.getEventsText(CricketUtil.OVER,infobar.getPlayer_id(),",", match.getEventFile().getEvents(),0).split(",").length <= 9) {
							processAnimation(print_writer, "Section5$ThisOverIn", "START", broadcaster);
						}else {
							processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						}
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyIn", "START", broadcaster);
						break;
					case "BOWLINGEND":
						processAnimation(print_writer, "Section5$BowlingEndIn", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section5$ExtrasIn", "START", broadcaster);
						break;
					}
				} 
				infobar.setBottom_right_section("");infobar.setLast_bottom_right_section("");
				break;
			case "POPULATE-INFOBAR-MIDDLE":
				if(infobar.getLast_middle_section() != null 
				&& !infobar.getLast_middle_section().trim().isEmpty()) {
					switch(infobar.getLast_middle_section().toUpperCase()) {
  					case CricketUtil.BATSMAN:
  						processAnimation(print_writer, "Section3$Section3In", "START", broadcaster);
  						break;
  					case "EQUATION":
  						processAnimation(print_writer, "Section3$EquationOut", "START", broadcaster);
//  						processAnimation(print_writer, "Section3$EquationOut", "SHOW 0.0", broadcaster);
  						break;
  					case "PROJECTED":
  						processAnimation(print_writer, "Section3$ProjectedOut", "START", broadcaster);
//  						processAnimation(print_writer, "Section3$ProjectedOut", "SHOW 0.0", broadcaster);
  						break;
  					case "LAST_WICKET":
  						processAnimation(print_writer, "Section3$LastWicketOut", "START", broadcaster);
  						break;
  					case "TOURNAMENT-NAME":
  						processAnimation(print_writer, "Section3$FreeTextSmallOut", "START", broadcaster);
  						break;
  					case "BOUNDARIES":
  						processAnimation(print_writer, "Section3$BoundariesOut", "START", broadcaster);
  						break;
  					case "LAST_BOUNDARY":
  						processAnimation(print_writer, "Section3$BallsSinceLastBoundaryOut", "START", broadcaster);
  						break;
  					case "RESULT":
  						processAnimation(print_writer, "Section3$FreeTextSmallOut", "START", broadcaster);
  						break;	
  					}
					TimeUnit.MILLISECONDS.sleep(500);
					infobar.setMiddle_section(valueToProcess);
					infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, broadcaster);
					
					TimeUnit.MILLISECONDS.sleep(500);
					switch(infobar.getMiddle_section().toUpperCase()) {
  					case CricketUtil.BATSMAN:
//  						processAnimation(print_writer, "Section3$Section3Out", "START", broadcaster);
  						break;
  					case "EQUATION":
  						processAnimation(print_writer, "Section3$EquationIn", "START", broadcaster);
  						break;
  					case "PROJECTED":
  						processAnimation(print_writer, "Section3$ProjectedIn", "START", broadcaster);
  						break;
  					case "LAST_WICKET":
  						processAnimation(print_writer, "Section3$LastWicketIn", "START", broadcaster);
  						break;
  					case "TOURNAMENT-NAME":
  						processAnimation(print_writer, "Section3$FreeTextSmallIn", "START", broadcaster);
  						break;
  					case "BOUNDARIES":
  						processAnimation(print_writer, "Section3$BoundariesIn", "START", broadcaster);
  						break;
  					case "LAST_BOUNDARY":
  						processAnimation(print_writer, "Section3$BallsSinceLastBoundaryIn", "START", broadcaster);
  						break;	
  					}
				}else {
					TimeUnit.MILLISECONDS.sleep(500);
					infobar.setMiddle_section(valueToProcess);
					processAnimation(print_writer, "Section3$FreeTextSmallIn", "SHOW 0.0", broadcaster);
					infobar = populateVizInfobarMiddle(infobar, false, print_writer, match, broadcaster);
					
					TimeUnit.MILLISECONDS.sleep(500);
					
					switch(infobar.getMiddle_section().toUpperCase()) {
  					case CricketUtil.BATSMAN:
//  						processAnimation(print_writer, "Section3$Section3Out", "START", broadcaster);
  						break;
  					case "EQUATION":
  						processAnimation(print_writer, "Section3$EquationIn", "START", broadcaster);
  						break;
  					case "PROJECTED":
  						processAnimation(print_writer, "Section3$ProjectedIn", "START", broadcaster);
  						break;
  					case "LAST_WICKET":
  						processAnimation(print_writer, "Section3$LastWicketIn", "START", broadcaster);
  						break;
  					case "TOURNAMENT-NAME":
  						processAnimation(print_writer, "Section3$FreeTextSmallIn", "START", broadcaster);
  						break;
  					case "BOUNDARIES":
  						processAnimation(print_writer, "Section3$BoundariesIn", "START", broadcaster);
  						break;
  					case "LAST_BOUNDARY":
  						processAnimation(print_writer, "Section3$BallsSinceLastBoundaryIn", "START", broadcaster);
  						break;	
  					}
				}
				break;
			case "POPULATE-INFOBAR-RIGHT":
				if(infobar.getLast_bottom_right_bottom_section() != null 
					&& !infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) { // Normal change on
					
					processAnimation(print_writer, "Section4$Section4In", "START", broadcaster);
					TimeUnit.MILLISECONDS.sleep(500);
					processAnimation(print_writer, "ALL_SECTION$Section4Out", "START", broadcaster);
//					TimeUnit.MILLISECONDS.sleep(500);
					switch(infobar.getLast_bottom_right_bottom_section().toUpperCase()){
					case CricketUtil.OVER:
						if(CricketFunctions.getEventsText(CricketUtil.OVER,infobar.getPlayer_id(),",", match.getEventFile().getEvents(),0).split(",").length <= 9) {
							processAnimation(print_writer, "Section5$ThisOverOut", "START", broadcaster);
						}else {
							processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
						}
						break;
					case "ECONOMY":
						processAnimation(print_writer, "Section5$EconomyOut", "START", broadcaster);
						break;
					case "BOWLINGEND":
						processAnimation(print_writer, "Section5$BowlingEndOut", "START", broadcaster);
						break;
					case "EXTRAS":
						processAnimation(print_writer, "Section5$ExtrasOut", "START", broadcaster);
						break;
					}
					
					TimeUnit.MILLISECONDS.sleep(500);
					
					infobar.setBottom_right_section(valueToProcess);
					infobar = populateVizInfobarRight(infobar, false,print_writer, match,tournament_matches, broadcaster);
					
					TimeUnit.MILLISECONDS.sleep(500);
					
					switch (infobar.getBottom_right_section().toUpperCase()) {
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonIn", "START", broadcaster);
						break;
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallIn", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					case "TOURNAMENT_SIX":
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case "TOURNAMENT_FOUR":
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;	
					}
				}else if(infobar.getLast_bottom_right_section() != null && 
						!infobar.getLast_bottom_right_section().trim().isEmpty()) {
					
					switch (infobar.getLast_bottom_right_section().toUpperCase()) {
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonOut", "START", broadcaster);
						break;
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallOut", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section4_N_5$TargetOut", "START", broadcaster);
						break;
					case "TOURNAMENT_SIX":
						processAnimation(print_writer, "Section4_N_5$SixCounterOut", "START", broadcaster);
						break;
					case "TOURNAMENT_FOUR":
						processAnimation(print_writer, "Section4_N_5$FourCounterOut", "START", broadcaster);
						break;	
					}
					
					TimeUnit.MILLISECONDS.sleep(500);
					
					infobar.setBottom_right_section(valueToProcess);
					infobar = populateVizInfobarRight(infobar, false,print_writer, match,tournament_matches, broadcaster);
					
					TimeUnit.MILLISECONDS.sleep(500);
					
					switch (infobar.getBottom_right_section().toUpperCase()) {
					case CricketUtil.COMPARE:
						processAnimation(print_writer, "Section4_N_5$ComparisonIn", "START", broadcaster);
						break;
					case CricketUtil.DOT:
						processAnimation(print_writer, "Section4_N_5$DotBallIn", "START", broadcaster);
						break;
					case CricketUtil.FOUR:
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;
					case CricketUtil.SIX:
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section4_N_5$TargetIn", "START", broadcaster);
						break;
					case "TOURNAMENT_SIX":
						processAnimation(print_writer, "Section4_N_5$SixCounterIn", "START", broadcaster);
						break;
					case "TOURNAMENT_FOUR":
						processAnimation(print_writer, "Section4_N_5$FourCounterIn", "START", broadcaster);
						break;	
					}
				}
				infobar.setBottom_right_bottom_section("");infobar.setLast_bottom_right_bottom_section("");
				infobar.setBottom_right_top_section("");infobar.setLast_bottom_right_top_section("");
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
					case "RRR":
						processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
						break;
					case "NEXT_TO_BAT":
						processAnimation(print_writer, "Section2$NextInOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section2$TargetOut", "START", broadcaster);
						break;
					case "PARTNERSHIP":
						processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
						break;
					case "SUPER_OVER":
						processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
						break;	
					
					}
					TimeUnit.MILLISECONDS.sleep(400);
					infobar.setTop_section(valueToProcess);
					populateVizInfobarTop(infobar, false, print_writer, match, broadcaster);
					
					switch(infobar.getTop_section().toUpperCase()) {
					case CricketUtil.TOSS:
						processAnimation(print_writer, "Section2$TossIn", "START", broadcaster);
						break;
					case "CRR":
						processAnimation(print_writer, "Section2$CurRunRateIn", "START", broadcaster);
						break;
					case "RRR":
						processAnimation(print_writer, "Section2$CRR_RRRIn", "START", broadcaster);
						break;
					case "NEXT_TO_BAT":
						processAnimation(print_writer, "Section2$NextInIn", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section2$TargetIn", "START", broadcaster);
						break;
					case "PARTNERSHIP":
						processAnimation(print_writer, "Section2$PartnershipIn", "START", broadcaster);
						break;
					case "SUPER_OVER":
						processAnimation(print_writer, "Section2$TossIn", "START", broadcaster);
						break;	
					}
					
					
				}else {
					infobar.setTop_section(valueToProcess);
					populateVizInfobarTop(infobar, false, print_writer, match, broadcaster);
					
					switch(infobar.getTop_section().toUpperCase()) {
					case CricketUtil.TOSS:
						processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
						break;
					case "CRR":
						processAnimation(print_writer, "Section2$CurRunRateOut", "START", broadcaster);
						break;
					case "RRR":
						processAnimation(print_writer, "Section2$CRR_RRROut", "START", broadcaster);
						break;
					case "NEXT_TO_BAT":
						processAnimation(print_writer, "Section2$NextInOut", "START", broadcaster);
						break;
					case "TARGET":
						processAnimation(print_writer, "Section2$TargetOut", "START", broadcaster);
						break;
					case "PARTNERSHIP":
						processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
						break;
					case "SUPER_OVER":
						processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
						break;	
					}
				}
				break;
			}
				
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
		case "PHASE":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainIn START \0");
			break;
		case "LOF_SCORECARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;
		case "LOF_BOWLINGCARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;
		case "SCORECARD":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCardIn START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingRightCardIn START \0");
			break;
		case "BOWLINGCARD":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCardIn START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingRightCardIn START \0");
			break;
		case "MATCHSUMMARY": case "PREVIOUS_SUMMARY":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*SummaryIn START \0");
			break;
		case "PARTNERSHIP":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "POINTSTABLE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*PointsTableIn START \0");
			break;
		case "WORM":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Worm_In START \0");
			break;
		case "MANHATTAN":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			//print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Manhattan_In START \0");
			break;
		case "LT_PLAYINGXI":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "TEAMLINEUP_PHOTOS":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Images_In START \0");
			break;
		case "TEAMLINEUP":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*DataIn START \0");
			break;
		case "TEAMLINEUP_SUBS":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Team$Team1_In$TeamEssentials_In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Team$Team1_In$Type4_In START \0");
			break;
		case "LEADERBOARD":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Top_In START \0");
			break;	
		case "MATCHID": case "MATCH_PROMO":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "DOUBLETEAMS": 
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "FFTARGET":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Target_In START \0");
			break;
		case "INNING_SUMMARY":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*InningSummary_In START \0");
			break;
		case "LTPARTNERSHIP":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "FFPLAYERPROFILE":	case "PLAYERPROFILEBALL": case "TORNAMENT_PLAYER":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "TIEID-DOUBLE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*In START \0");
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentIn START \0");
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

		case "MINI_BATTINGCARD": case "MINI_BOWLINGCARD": case "BATGRIFF": case "BALLGRIFF":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*StarLoop START \0");
			break;
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "BUG-TOSS": case "BUGPARTNERSHIP": case "BUG_POWERPLAY": case "BUG_HIGHLIGHT": case "MULTI_PARTNERSHIP":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;
		case "HOWOUT": case "HOWOUT_QUICK": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET":

		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS": case "LINEUP": case "NEXT_TO_BAT": case "BOWLERSUMMARY":
		case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS": case "LTPOWERPLAY": case "L3PLAYERPROFILE": case "EQUATION": case "POSITION_LANDMARK":
		case "BATSMAN_THIS_MATCH": case "MOST": case "INN_BUILDER": case "LT_SEASON": case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE": case "BOWLER_STYLE": case "BATSMAN_STYLE": 
		case "HOWOUT_WITHOUT":  case "BALLSINCE": case "L3MATCH_PROMO": case "THISSERIES": case "LT-MANHATTAN": case "LT_BOWLERSPEED": case "PLAYERPROFILEBAT": case "LANDMARK":case "MOSTRUNS":
		case "MOSTWICKETS": case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "TEAMS_LOGO": case "SCHEDULE": case "FF-THISSERIES":
		case "FF_STATS": case "PLAYOFFS": case "PARTNERSHIP_LT":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;	
		}	
	}
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic) throws IOException {
		switch(whichGraphic) {
		case "LOF_BOWLINGCARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "LOF_SCORECARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "PHASE":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "BATBALLSUMMARY_SCORECARD_PERFORMER":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCard_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "BATBALLSUMMARY_SCORECARD":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BattingCard_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "BATBALLSUMMARY_BOWLINGCARD_PERFORMER":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCard_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "BATBALLSUMMARY_BOWLINGCARD":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*BowlingCard_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "BATBALLSUMMARY_MATCHSUMMARY": case "PREVIOUS_SUMMARY":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Summary_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "POINTSTABLE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Points_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "PARTNERSHIP":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Partnership_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "WORM":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Worm_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "MANHATTAN":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Manhattan_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "LT_PLAYINGXI":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;	
		case "TEAMLINEUP_PHOTOS":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*DataIn START \0");
			break;
		case "TEAMLINEUP": case "TEAMLINEUP_SUBS":
			if(which_director_on_screen.equalsIgnoreCase("DATA")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*DataOut START \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
				which_director_on_screen = "";
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Images_Out START \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
				which_director_on_screen = "";
			}
//			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Team$Team1_Out START \0");
			break;
		case "LEADERBOARD":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Top_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;	
		case "MATCHID": case "MATCH_PROMO":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Ident_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "DOUBLETEAMS":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Teams_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "INNING_SUMMARY": 
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*InningSummary_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "FFTARGET":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Target_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "LTPARTNERSHIP":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*CurrentPart_Out START \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "FFPLAYERPROFILE":	case "PLAYERPROFILEBALL": case "TORNAMENT_PLAYER":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "ANIMATE-OUT-INFOBAR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "ANIMATE-OUT-IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*IdentOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*MainOut START \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "DIRECTOR":
			switch(which_director_on_screen.toUpperCase()) {
			case "FOUR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FourOut START \0");
				which_director_on_screen = "";
				break;
			case "SIX":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SixOut START \0");
				which_director_on_screen = "";
				break;
			case "FREE_HIT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitOut START \0");
				which_director_on_screen = "";
				break;
			case "WICKET":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*WicketOut START \0");
				which_director_on_screen = "";
				break;	
			}
			break;
		case "FF_OUT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FF_Out START \0");
			break;
		case "LT_OUT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_Out START \0");
			break;
		case "IDENT_TOP":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*InfoBarOut START \0");
			break;	
		case "MINI_BATTINGCARD": case "MINI_BOWLINGCARD": case "BATGRIFF": case "BALLGRIFF":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "BUG-TOSS": case "BUGPARTNERSHIP": case "BUG_POWERPLAY": case "BUG_HIGHLIGHT": case "MULTI_PARTNERSHIP":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "TIEID-DOUBLE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Out START \0");
			break;
		case "LT-MANHATTAN":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		case "HOWOUT": case "HOWOUT_QUICK": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET": case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "SPLIT": 
		case "BATSMANSTATS": case "BOWLERSTATS": case "LINEUP": case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT": case "PROJECTED": case "BOWLERDETAILS":
		case "LTPOWERPLAY": case "L3PLAYERPROFILE": case "LANDMARK": case "EQUATION": case "POSITION_LANDMARK": case "BATSMAN_THIS_MATCH": case "BOWLER_THIS_MATCH": case "LTPOINTSTABLE":
		case "BOWLER_STYLE": case "BATSMAN_STYLE": case "HOWOUT_WITHOUT": case "MOSTRUNS": case "MOSTWICKETS": case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE":
		case "L3MATCH_PROMO": case "TEAMS_LOGO": case "LT_SEASON": case "PLAYERPROFILEBAT": case "LT_BOWLERSPEED": case "SCHEDULE": case "THISSERIES": case "FF-THISSERIES":
		case "FF_STATS": case "PLAYOFFS":  case "MOST": case "INN_BUILDER": case "BALLSINCE": case "NEXT_TO_BAT": case "PARTNERSHIP_LT":	
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		}
		CricketFunctions.deletePreview();
	}
	public static Statistics updateTournamentDataWithStats(Statistics stat,List<MatchAllData> tournament_matches,MatchAllData currentMatch) 
	{
		boolean player_found = false;
		for(MatchAllData match : tournament_matches) {
			if(!match.getMatch().getMatchFileName().equalsIgnoreCase(currentMatch.getMatch().getMatchFileName())) {
				if(stat.getStats_type().getStatsShortName().equalsIgnoreCase("PR")) {
					for(Inning inn : match.getMatch().getInning()) {
						for(BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == stat.getPlayer_id()) {
								player_found = true;
								if(bc.getBatsmanInningStarted() != null && bc.getBatsmanInningStarted().equalsIgnoreCase(CricketUtil.YES)) {
									stat.setInnings(stat.getInnings() + 1);
								}
								stat.setRuns(stat.getRuns() + bc.getRuns());
								stat.setFours(stat.getFours() + bc.getFours());
								stat.setSixes(stat.getSixes() + bc.getSixes());
								stat.setBalls_faced(stat.getBalls_faced() + bc.getBalls());
								
								if(bc.getRuns() < 50 && bc.getRuns() >= 30) {
									stat.setThirties(stat.getThirties() + 1);
								}else if(bc.getRuns() < 100 && bc.getRuns() >= 50) {
									stat.setFifties(stat.getFifties() + 1);
								}else if(bc.getRuns() >= 100){
									stat.setHundreds(stat.getHundreds() + 1);
								}
								
							}
						}
						if(inn.getBowlingCard() != null && inn.getBowlingCard().size()>0) {
							for(BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId() == stat.getPlayer_id()) {
									stat.setWickets(stat.getWickets() + boc.getWickets());
									stat.setRuns_conceded(stat.getRuns_conceded() + boc.getRuns());
									stat.setBalls_bowled(stat.getBalls_bowled() + (boc.getOvers()*6 + boc.getBalls()));
									stat.setDotbowled(stat.getDotbowled() + boc.getDots());
									if(boc.getWickets() < 5 && boc.getWickets() >= 3) {
										stat.setPlus_3(stat.getPlus_3() + 1);
									}	
									else if(boc.getWickets() >= 5){
										stat.setPlus_5(stat.getPlus_5() + 1);
									}
								}
							}							
						}
					}
					player_found = false;
					for(Player hs : match.getSetup().getHomeSquad()) {
						if(stat.getPlayer_id() == hs.getPlayerId()) {
							player_found = true;
						}
					}
					for(Player as : match.getSetup().getAwaySquad()) {
						if(stat.getPlayer_id() == as.getPlayerId()) {
							player_found = true;
						}
					}
					if(player_found == true){
						stat.setMatches(stat.getMatches() + 1);
					}
				}
			}
		}
		return stat;
	}
	
	public static Statistics updateStatisticsWithMatchData(Statistics stat, MatchAllData match)
	{
		boolean player_found = false;
		
		if(stat.getStats_type().getStatsShortName().equalsIgnoreCase("PR")) {
			stat.setTournament_fours(stat.getTournament_fours() + match.getMatch().getInning().get(0).getTotalFours());
			stat.setTournament_fours(stat.getTournament_fours() + match.getMatch().getInning().get(1).getTotalFours());
			for(Inning inn : match.getMatch().getInning()) {
				for(BattingCard bc : inn.getBattingCard()) {
					if(bc.getPlayerId() == stat.getPlayer_id()) {
						player_found = true;
						if(bc.getBatsmanInningStarted() == null) {
						}
						else if(bc.getBatsmanInningStarted().equalsIgnoreCase(CricketUtil.YES)) {
							stat.setInnings(stat.getInnings() + 1);
						}
						
						stat.setRuns(stat.getRuns() + bc.getRuns());
						stat.setFours(stat.getFours() + bc.getFours());
						stat.setSixes(stat.getSixes() + bc.getSixes());
						stat.setBalls_faced(stat.getBalls_faced() + bc.getBalls());
				
						if(bc.getRuns() < 50 && bc.getRuns() >= 30) {
							stat.setThirties(stat.getThirties() + 1);
						}else if(bc.getRuns() < 100 && bc.getRuns() >= 50) {
							stat.setFifties(stat.getFifties() + 1);
						}else if(bc.getRuns() >= 100){
							stat.setHundreds(stat.getHundreds() + 1);
						}
					}
				}
				if(inn.getBowlingCard() != null && inn.getBowlingCard().size()>0) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getPlayerId() == stat.getPlayer_id()) {
							player_found = true;
							stat.setWickets(stat.getWickets() + boc.getWickets());
							stat.setRuns_conceded(stat.getRuns_conceded() + boc.getRuns());
							stat.setBalls_bowled(stat.getBalls_bowled() + (boc.getOvers()*6 + boc.getBalls()));
							stat.setDotbowled(stat.getDotbowled() + boc.getDots());
							//System.out.println(boc.getWickets());
							if(boc.getWickets() >= 3 && boc.getWickets() < 5) {
								stat.setPlus_3(stat.getPlus_3() + 1);
							}else if(boc.getWickets() >= 5){
								stat.setPlus_5(stat.getPlus_5() + 1);
							}
						}
					}							
				}
			}
			player_found = false;
			for(Player hs : match.getSetup().getHomeSquad()) {
				if(stat.getPlayer_id() == hs.getPlayerId()) {
					player_found = true;
				}
			}
			for(Player as : match.getSetup().getAwaySquad()) {
				if(stat.getPlayer_id() == as.getPlayerId()) {
					player_found = true;
				}
			}
			if(player_found == true){
				stat.setMatches(stat.getMatches() + 1);
			}
		}
		return stat;
	}
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_VIZ": case "NEPAL_T20": case "FAIR_BREAK":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*"+ animationName + " " + animationCommand +" \0");
			break;
		case "DOAD_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			
			break;
		}
		
	}
	public String resetInfobarAnimation(PrintWriter print_writer,String which_frame) throws InterruptedException, IOException {
		
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
	public void Preview(PrintWriter print_writer,String viz_scene,String previous_gfx, String current_gfx) throws InterruptedException
	{
		String previewAnim = "";
		if(previous_gfx == "")
		{
			switch(current_gfx){
			case "SCORECARD":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 4.000 BattingCard_In 2.880 \0");
				TimeUnit.MILLISECONDS.sleep(200);
				break;
			case "BOWLINGCARD":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 4.000 BowlingCard_In 2.880 \0");
				TimeUnit.MILLISECONDS.sleep(200);
				break;
			case "MATCHSUMMARY":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 4.000 Summary_In 1.900 \0");
				TimeUnit.MILLISECONDS.sleep(200);
				break;
			case "TEAMLINEUP": case "TEAMLINEUP_SUBS":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 4.000 Team$Team1_In 3.420 Team$Team1_In$TeamEssentials_In 1.700 "
						+ "Team$Team1_In$Type2_In 1.800 Team$Team1_In$Type4_In 3.420 \0");
				TimeUnit.MILLISECONDS.sleep(200);
				break;
			}		
		} else {
			switch(previous_gfx){
			case "BATBALLSUMMARY_SCORECARD":
				previewAnim = "BattingCardOut 1.020";
				break;
			case "BATBALLSUMMARY_BOWLINGCARD":
				previewAnim = "BowlingCardOut 1.020";
				break;
			case "BATBALLSUMMARY_MATCHSUMMARY":
				previewAnim = "SummaryOut 1.100";
				break;
			}
			switch(current_gfx){
			case "SCORECARD":
				previewAnim = previewAnim + " BattingCard_In 2.880";
				break;
			case "BOWLINGCARD":
				previewAnim = previewAnim + " BowlingCard_In 2.880";
				break;
			case "MATCHSUMMARY":
				previewAnim = previewAnim + " Summary_In 1.900";
				break;
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg " + previewAnim + " \0");
		}
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
			if(!which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_SCORECARD") && !which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_SCORECARD_PERFORMER")) {
				populateScorecard(print_writer, viz_scene,true, whichInning, match, broadcaster);
			}
			
			switch(Type.toUpperCase()) {
			case "PERFORMER":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(player == bc.getPlayerId()) {
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtraPlayerFirstName1" + " SET " + 
										" " + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtraPlayerLastName1" + " SET " + 
										bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatValue" + " SET " + 
										bc.getStrikeRate() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatPerformerFlag" + " SET " + 
										flag_path + bc.getPlayer().getNationality() + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPerformerGrp$PlayerImageGrp$PlayerImage1A"
											+ "*TEXTURE*IMAGE SET "+ photo_path + inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPerformerGrp$PlayerImageGrp$PlayerImage1"
											+ "*TEXTURE*IMAGE SET "+ photo_path + inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" +inn.getBatting_team().getTeamName3().toUpperCase()
											+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPerformerGrp$PlayerImageGrp$PlayerImage1A"
											+ "*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPerformerGrp$PlayerImageGrp$PlayerImage1"
											+ "*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" + inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
								}
							}
						}
					}
				}
				
				if(!which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_MATCHSUMMARY") || !which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_BOWLINGCARD") 
						|| !which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_BOWLINGCARD_PERFORMER")) {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.400 BattingCardIn$BatLogoIn 1.400 BattingCardIn$BatOffsetIn 1.400 BatPerformerIn 0.641 BatPartnershipIn 0.000 BattingRightCardIn$BatRightOffset 1.180 BattingRightCardIn 1.180 \0");
					TimeUnit.MILLISECONDS.sleep(200);
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.400 BattingCardIn$BatLogoIn 1.400 BattingCardIn$BatOffsetIn 1.400 BatPerformerIn 0.641 BatPartnershipIn 0.000 BattingRightCardIn$BatRightOffset 1.180 BattingRightCardIn 1.180 "
							+ "BallPerformerIn 0.000 BowlingCardIn 0.000 BowlingCardIn$BallOffsetIn 0.000 BowlingRightCardIn 0.000 BowlingRightCardIn$BallRightOffset 0.000 SummaryIn 0.000 SummaryOffsetIn 0.000 PointsTableIn 0.000 PointsOffsetIn 0.000 \0");
					TimeUnit.MILLISECONDS.sleep(200);
				}
				
				
				break;
			case "PARTNERSHIP":
				for(Inning inn : match.getMatch().getInning()) {
					String Batsman_Name1 = "",Batsman_Name2 = "";
					//if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(inn.getInningNumber() == whichInning) {
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
									Batsman_Name1 = bc.getPlayer().getTicker_name();
									if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp$ImageGrp$PlayerImageGrp1"
												+ "$PlayerImage1A*TEXTURE*IMAGE SET "+ photo_path + 
												inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp$ImageGrp$PlayerImageGrp1"
												+ "$PlayerImage1*TEXTURE*IMAGE SET "+ photo_path + 
												inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
									}else {
										if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" +inn.getBatting_team().getTeamName3().toUpperCase()
												+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
											this.status = CricketUtil.UNSUCCESSFUL;
										}
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp$ImageGrp$PlayerImageGrp1"
												+ "$PlayerImage1A*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
												inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp$ImageGrp$PlayerImageGrp1"
												+ "$PlayerImage1*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
												inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
									}
									
								}
								
								if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
									Batsman_Name2 = bc.getPlayer().getTicker_name();
									if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp$ImageGrp$PlayerImageGrp2"
												+ "$PlayerImage2A*TEXTURE*IMAGE SET "+ photo_path + 
												inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp$ImageGrp$PlayerImageGrp2"
												+ "$PlayerImage2*TEXTURE*IMAGE SET "+ photo_path + 
												inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
									}else {
										if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" +inn.getBatting_team().getTeamName3().toUpperCase()
												+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
											this.status = CricketUtil.UNSUCCESSFUL;
										}
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp$ImageGrp$PlayerImageGrp2"
												+ "$PlayerImage2A*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
												inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp$ImageGrp$PlayerImageGrp2"
												+ "$PlayerImage2*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
												inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
									}
								}
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine1" + " SET " + 
									Batsman_Name1 + "/" + Batsman_Name2 + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPartnershipRuns" + " SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPartnershipBalls" + " SET " + 
									inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
							
							if(inn.getTotalWickets() == 0) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + 
										(inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
							}else if(inn.getTotalWickets() == 1) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + 
										(inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
							}else if(inn.getTotalWickets() == 2) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + 
										(inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatStatHeadLine2" + " SET " + 
										(inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
								TimeUnit.MILLISECONDS.sleep(2);
							}
						}
					//}
				}
				
				if(!which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_MATCHSUMMARY") || !which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_BOWLINGCARD") 
						|| !which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_BOWLINGCARD_PERFORMER")) {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.400 BattingCardIn$BatLogoIn 1.400 BattingCardIn$BatOffsetIn 1.400 BatPartnershipIn 1.000 BatPerformerIn 0.000 BattingRightCardIn$BatRightOffset 1.180 BattingRightCardIn 1.180 \0");
					TimeUnit.MILLISECONDS.sleep(200);
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.400 BattingCardIn$BatLogoIn 1.400 BattingCardIn$BatOffsetIn 1.400 BatPartnershipIn 1.000 BatPerformerIn 0.000 BattingRightCardIn$BatRightOffset 1.180 BattingRightCardIn 1.180 "
							+ "SummaryIn 0.000 SummaryOffsetIn 0.000 BowlingRightCardIn 0.000 BallPerformerIn 0.000 BowlingCardIn 0.000 BallOffsetIn 0.000 PointsTableIn 0.000 PointsOffsetIn 0.000 \0");
					TimeUnit.MILLISECONDS.sleep(200);
				}
				
				break;
			case "BATTING_DATA":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.400 BattingCardIn$BatLogoIn 1.400 BattingCardIn$BatOffsetIn 1.400 BattingRightCardIn 1.180 BatPartnershipIn 0.000 BatPerformerIn 0.000 "
						+ "BallPerformerIn 0.000 BowlingCardIn 0.000 BowlingCardIn$BallOffsetIn 0.000 BowlingRightCardIn 0.000 BowlingRightCardIn$BallRightOffset 0.000 SummaryIn 0.000 SummaryOffsetIn 0.000 \0");
				break;
			}
			
	}
}
	public void populatelofScorecard(PrintWriter print_writer, String viz_scene,boolean is_this_updating, int whichInning, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0, omo_num = 0,row = 0;
			String cont_name = "",cont = "";
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + 
					match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + 
					"" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$FirstSix$BatRow1*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$FirstSix$BatRow2*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$FirstSix$BatRow3*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$FirstSix$BatRow4*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$FirstSix$BatRow5*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$FirstSix$BatRow6*ACTIVE SET 0\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$SecondSix$BatRow7*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$SecondSix$BatRow8*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$SecondSix$BatRow9*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$SecondSix$BatRow10*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$SecondSix$BatRow11*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$SecondSix$BatRow12*ACTIVE SET 0\0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$business6*TEXTURE*IMAGE SET "+ "IMAGE*/Default/Nepal_T20/Logos/1XBAT" +" \0");
					
					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$TeamLogo$Rectangle*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + 
								match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$TeamLogo$Rectangle*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					}
					
					Collections.sort(inn.getBattingCard());
					for (BattingCard bc : inn.getBattingCard()) {
						row_id = row_id + 1;
						if(row_id <= 6) {
							cont = "$FirstSix";
						}else {
							cont = "$SecondSix";
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$SecondSix*FUNCTION*Omo*vis_con SET " + row + " \0");
							row = row + 1;
							
						}
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
						if(bc.getHowOut() == null) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BatRow"
									+ row_id + "*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BatRow" + 
									row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
							
							for(int b=1;b<=inn.getBattingCard().size();b++) {
								if(inn.getBattingCard().get(b-1).getPlayerId() == bc.getPlayerId()) {
									if(inn.getBattingCard().get(b-1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInAtValue" + row_id + " SET " + 
												b + "\0");
									}
								}
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
									bc.getPlayer().getTicker_name() + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + photo_path + 
										inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" +inn.getBatting_team().getTeamName3().toUpperCase()
										+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
							
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BatRow"
									+ row_id + "*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BatRow" + 
									row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
									bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
									"retired hurt" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
									bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
									String.valueOf(bc.getBalls()) + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + photo_path + 
										inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" +inn.getBatting_team().getTeamName3().toUpperCase()
										+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path  + 
										inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
							
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BatRow"
									+ row_id + "*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BatRow" + 
									row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
									bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
									"absent hurt" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
									bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
									String.valueOf(bc.getBalls()) + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + photo_path +
										inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" +inn.getBatting_team().getTeamName3().toUpperCase()
										+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +
										inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
							
							
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
							//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow"+
									//row_id+"$BatOmo*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
						}
						else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
							//print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow"+row_id+
									//"$BatOmo*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BatRow"
								+ row_id + "*ACTIVE SET 1 \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatFlag" + row_id + " SET " + 
								flag_path + bc.getPlayer().getNationality().toUpperCase() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BatRow" + 
								row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
								bc.getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
								bc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
								String.valueOf(bc.getBalls()) + "\0");
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + photo_path +
									inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" +inn.getBatting_team().getTeamName3().toUpperCase()
									+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +
									inn.getBatting_team().getTeamName3().toUpperCase()+ centre_path + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						
						
						
						if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
							if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
										bc.getHowOutPartOne() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
										" " + "\0");
								
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
													bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
													" " + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
													bc.getHowOutPartOne() + " (sub - " + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
													" " + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
												bc.getHowOutText() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
												" " + "\0");
										
									}
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											bc.getHowOutPartTwo() + "\0");
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											" " + "\0");
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase("timed_out")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											"timed out" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											" " + "\0");
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											" " + "\0");
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
													bc.getHowOutPartOne().replace("(SUB)", "") + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
													bc.getHowOutPartTwo() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
													bc.getHowOutPartOne() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
													bc.getHowOutPartTwo() + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
												bc.getHowOutPartOne() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
												bc.getHowOutPartTwo() + "\0");
									}
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											bc.getHowOutPartTwo() + "\0");
									
								}
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
									bc.getStatus() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
									" " + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
						}
					}
				}
			}
		}
			TimeUnit.MILLISECONDS.sleep(100);
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.900 In$BatDataIn 0.772 \0");
			}
			//			Preview(print_writer, viz_scene, which_graphic_on_screen, "SCORECARD");
		  
		
	}
}
	
	public void populateScorecard(PrintWriter print_writer, String viz_scene,boolean is_this_updating, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateScorecard -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateScorecard -> Scorecard's inning is null");
		} else {

			int row_id = 0, omo_num = 0;
			String cont_name = "";
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + 
					match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + 
					match.getSetup().getTournament().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPartnershipGrp*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 0 \0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$RightGrp$business6*TEXTURE*IMAGE SET "+ "IMAGE*/Default/Nepal_T20/Logos/1XBAT" +" \0");
					
					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatTeamLogoInGrp1$LogoInAllGrp$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamLogoInGrp2$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamIcon1*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + 
								match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatTeamLogoInGrp1$LogoInAllGrp$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamLogoInGrp2$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamIcon1*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					}
					
					Collections.sort(inn.getBattingCard());
					for (BattingCard bc : inn.getBattingCard()) {
						row_id = row_id + 1;
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatDataGrp*FUNCTION*Grid*num_row SET " + row_id + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatRightDataAll$BatRightDataGrp*FUNCTION*Grid*num_row SET " + row_id + " \0");
//						if(CricketFunctions.checkImpactPlayer(match.getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard$BattingDataAll$Select_Type$Normal$BatGrp$BatRow" + row_id + 
//									"$RowOut$RowAni$BatData$Select_Star*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//						}else if(CricketFunctions.checkImpactPlayerBowler(match.getEvents(), whichInning, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard$BattingDataAll$Select_Type$Normal$BatGrp$BatRow" + row_id + 
//									"$RowOut$RowAni$BatData$Select_Star*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//						}else {
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BattingCard$BattingDataAll$Select_Type$Normal$BatGrp$BatRow" + row_id + 
//									"$RowOut$RowAni$BatData$Select_Star*FUNCTION*Omo*vis_con SET " + "0" +" \0");
//						}
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatFlag" + row_id + " SET " + 
									flag_path + bc.getPlayer().getNationality().toUpperCase() + "\0");
						if(bc.getHowOut() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
									row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 0 \0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
									bc.getPlayer().getTicker_name() + "\0");
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
									row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
									bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
									"retired hurt" + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
									bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
									String.valueOf(bc.getBalls()) + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
									" " + "\0");
							
						}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatDataGrp$BatRow" + 
									row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
									row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
									bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
									"absent hurt" + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
									bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
									String.valueOf(bc.getBalls()) + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
									" " + "\0");
							
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
							//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
							//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow"+
									//row_id+"$BatOmo*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
						}
						else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
							//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + inn.getBattingCard().size() + "\0");
							
							//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format2$BatData$BatDataGrp$BatRow"+row_id+
									//"$BatOmo*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatFlag" + row_id + " SET " + 
								flag_path + bc.getPlayer().getNationality().toUpperCase() + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatDataGrp$BatRow" + 
								row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatRightDataAll$BatRightDataGrp$BatDetailRow" + 
								row_id + "$RowAnimation$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + 
								bc.getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + 
								bc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + 
								String.valueOf(bc.getBalls()) + "\0");
						
						
						if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
							if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
										bc.getHowOutPartOne() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
										" " + "\0");
								
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
													bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
													" " + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
													bc.getHowOutPartOne() + " (sub - " + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
													" " + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
												bc.getHowOutText() + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
												" " + "\0");
										
									}
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											bc.getHowOutPartTwo() + "\0");
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)){
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											" " + "\0");
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase("timed_out")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											"timed out" + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											" " + "\0");
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											" " + "\0");
									
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)) {
									if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
													bc.getHowOutPartOne().replace("(SUB)", "") + "\0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
													bc.getHowOutPartTwo() + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
													bc.getHowOutPartOne() + "\0");
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
													bc.getHowOutPartTwo() + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
												bc.getHowOutPartOne() + "\0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
												bc.getHowOutPartTwo() + "\0");
									}
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
											bc.getHowOutPartOne() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
											bc.getHowOutPartTwo() + "\0");
									
								}
						}
						else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatWicketPlayerName" + row_id + " SET " + 
									bc.getStatus() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBallPlayerName" + row_id + " SET " + 
									" " + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
						}
					}
				}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtraHead" + " SET " + 
							"Extras" + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatExtrasValue" + " SET " + 
							inn.getTotalExtras() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatOverHead" + " SET " + 
							"Overs" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatOversValue" + " SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
				
				
				if(inn.getTotalWickets() >= 10) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatTotalScore" + " SET " + 
							inn.getTotalRuns() + "\0");
				} else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatTotalScore" + " SET " + 
							inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
				}
			}
		}
			TimeUnit.MILLISECONDS.sleep(100);
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 BattingCardIn 1.400 BattingCardIn$BatLogoIn 1.400 BattingCardIn$BatOffsetIn 1.400 BattingRightCardIn 1.180 BattingRightCardIn$BatRightOffset 1.180 BatPartnershipIn 0.000 BatPerformerIn 0.000 "
						+ "SummaryIn 0.000 SummaryOffsetIn 0.000 BallPerformerIn 0.000 BowlingCardIn 0.000 BowlingCardIn$BallOffsetIn 0.000 BowlingRightCardIn 0.000 BowlingRightCardIn$BallRightOffset 0.000 PointsTableIn 0.000 PointsOffsetIn 0.000 \0");
			}
			//			Preview(print_writer, viz_scene, which_graphic_on_screen, "SCORECARD");
		  
		
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
			if(!which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_BOWLINGCARD") && !which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_BOWLINGCARD_PERFORMER")) {
				populateBowlingcard(print_writer, viz_scene, true, whichInning, match, broadcaster);
			}
			
			switch(Type.toUpperCase()) {
			case "PERFORMER":
				//print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$AllDataGrp$AllDataGrp$BattingCardAll$BattingCardType$Format1$BatData$BatExtraData$BatPerformerGrp*ACTIVE SET 1 \0");
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(player == boc.getPlayerId()) {
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallExtraData$BallPerformerGrp$PlayerImageGrp$PlayerImage1A"
											+ "*TEXTURE*IMAGE SET "+ photo_path + inn.getBowling_team().getTeamName3().toUpperCase()+ centre_path + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallExtraData$BallPerformerGrp$PlayerImageGrp$PlayerImage1"
											+ "*TEXTURE*IMAGE SET "+ photo_path + inn.getBowling_team().getTeamName3().toUpperCase()+ centre_path + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" +inn.getBowling_team().getTeamName3().toUpperCase()
											+ centre_path + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallExtraData$BallPerformerGrp$PlayerImageGrp$PlayerImage1A"
											+ "*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBowling_team().getTeamName3().toUpperCase()+ centre_path + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallExtraData$BallPerformerGrp$PlayerImageGrp$PlayerImage1"
											+ "*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBowling_team().getTeamName3().toUpperCase()+ centre_path + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
								}
								
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtraPlayerFirstName1" + " SET " + 
										"" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtraPlayerLastName1" + " SET " + 
										boc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallStatHead" + " SET " + 
										"FIGURES" + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallStatValue" + " SET " + 
										boc.getWickets() + "-" + boc.getRuns() + "\0");
								
								TimeUnit.MILLISECONDS.sleep(2);
							}
						}
					}
				}
				if(!which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_MATCHSUMMARY") || !which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_SCORECARD") 
						|| !which_graphic_on_screen.equalsIgnoreCase("BATBALLSUMMARY_SCORECARD_PERFORMER")) {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 BowlingCardIn 1.400 BowlingCardIn$BallOffsetIn 1.363 BowlingRightCardIn 1.052 BowlingRightCardIn$BallRightOffset 1.052 BallPerformerIn 0.641 \0");
					TimeUnit.MILLISECONDS.sleep(200);
				}else {
					print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 BowlingCardIn 1.400 BowlingCardIn$BallOffsetIn 1.363 BowlingRightCardIn 1.052 BowlingRightCardIn$BallRightOffset 1.052 BallPerformerIn 0.641 SummaryIn 0.000 SummaryOffsetIn 0.000 "
							+ "BatPartnershipIn 0.000 BattingRightCardIn 0.000 BatPerformerIn 0.000 BattingCardIn 0.000 BattingCardIn$BatOffsetIn 0.000 PointsTableIn 0.000 PointsOffsetIn 0.000 \0");
					TimeUnit.MILLISECONDS.sleep(200);
				}
				
				break;
			case "BOWLING_FIGURE":
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 4.000 BallCard$BowlingCard_In 2.880 \0");
				TimeUnit.MILLISECONDS.sleep(200);
				break;
			}
		}
	}
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene,boolean is_this_updating, int whichInning,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateBowlingcard -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBowlingcard -> inning is null");
		} else {
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamFirstName" + " SET " + 
					match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallSubHeader" + " SET " + 
					match.getSetup().getTournament().toUpperCase() + "\0");
			
			int row_id = 0, len=0,omo_num =0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamLastName" + " SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallTeamLogoInGrp1$LogoInAllGrp$BallTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallTeamLogoInGrp2$BallTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallTeamIcon1*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallBowlingTeamLastName" + " SET " + 
								match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallTeamLogoInGrp1$LogoInAllGrp$BallTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallTeamLogoInGrp2$BallTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallTeamIcon1*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					}
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
							len=len+1;
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallDataGrp*FUNCTION*Omo*vis_con SET " + 
									len + " \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallRightDataAll$BallRightDataGrp*FUNCTION*Omo*vis_con SET " + 
									len + " \0");
						}
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
						
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallDataGrp$BallRow" + row_id + "$RowAnimation$BallOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallDataGrp$BallRow" + row_id + "*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallRightDataAll$BallRightDataGrp$BallDetailRow" + row_id + 
									"$RowAnimation$BallDetailOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallRightDataAll$BallRightDataGrp$BallDetailRow" + row_id + "*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallFlag" + row_id + " SET " + 
									flag_path + boc.getPlayer().getNationality().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_id + " SET " + 
									boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallFiguresValue" + row_id + " SET " + 
									boc.getWickets() + "-" + boc.getRuns() + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + row_id + " SET " + 
									CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");
							
							//TimeUnit.MILLISECONDS.sleep(2);

							if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.D10) 
									|| match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallMaidensHead" + " SET " + 
										"Dots" + "\0");
								if(boc.getDots() < 0) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallMaidensValue" + row_id + " SET " + 
											"0" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallMaidensValue" + row_id + " SET " + 
											boc.getDots() + "\0");
								}
								//TimeUnit.MILLISECONDS.sleep(2);
								
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallMaidensHead" + " SET " + 
										"Maidens" + "\0");
								if(boc.getMaidens() < 0) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallMaidensValue" + row_id + " SET " + 
											"0" + "\0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallMaidensValue" + row_id + " SET " + 
											boc.getMaidens() + "\0");
								}
								//TimeUnit.MILLISECONDS.sleep(2);
							}
							
							int data = boc.getWides() + boc.getNoBalls();
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtrasValue" + row_id + " SET " + data + "\0");

							
							if(boc.getEconomyRate() != null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallEconomyValue" + row_id + " SET " + 
										boc.getEconomyRate() + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallEconomyValue" + row_id + " SET " + 
										"-" + "\0");
							}
					}
					
//					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard$BallDataAll$Select_Type$Normal$FOW_Grp$FOW$RowOut$RowAni$FOW_Data$"
//							+ "img_Text2$txt_Title*GEOM*TEXT SET "+ "FALL OF WICKETS" + " \0");
					
					if(inn.getBowlingCard().size()<=7) {
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$FowGrp*ACTIVE SET 0 \0");
							//TimeUnit.MILLISECONDS.sleep(2);
						}
						else{
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$FowGrp*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$FowGrp$FOW2$RowAnimation$BallOmo$Dehighlight$TextGrp*FUNCTION*Grid*num_col SET " + inn.getTotalWickets() + " \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$FowGrp$FOW3$RowAnimation$BallOmo$Highlight$FOW_ValueGrp*FUNCTION*Grid*num_col SET " + inn.getTotalWickets() + " \0");
							
							
							for(FallOfWicket fow : inn.getFallsOfWickets()) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallFowValue" + fow.getFowNumber() + " SET " + 
										fow.getFowRuns() + "\0");
								//TimeUnit.MILLISECONDS.sleep(2);
							
							}	
						}
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$BowlingCard$BallDataAll$Select_Type$Normal$FOW_Grp*ACTIVE SET 0 \0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallOversValue" + " SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallExtrasValue" + " SET " + 
							inn.getTotalExtras() + "\0");
			
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallTotalScore" + " SET " + 
								inn.getTotalRuns() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallTotalScore" + " SET " + 
								inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 BowlingCardIn 1.400 BowlingCardIn$BallOffsetIn 1.363 BowlingRightCardIn 1.052 BowlingRightCardIn$BallRightOffset 1.052 BallPerformerIn 0.000 SummaryIn 0.000 SummaryOffsetIn 0.000 "
						+ "BatPartnershipIn 0.000 BattingRightCardIn 0.000 BatPerformerIn 0.000 BattingCardIn 0.000 BattingCardIn$BatOffsetIn 0.000 PointsTableIn 0.000 PointsOffsetIn 0.000 \0");
			}
//			Preview(print_writer, viz_scene, which_graphic_on_screen, "BOWLINGCARD");
		}
	}
	public void populatelofBowlingcard(PrintWriter print_writer,String viz_scene,boolean is_this_updating, int whichInning,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populateBowlingcard -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBowlingcard -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + 
					match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatSubHeader" + " SET " + 
					"" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Fistt6$BallRow1*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Fistt6$BallRow2*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Fistt6$BallRow3*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Fistt6$BallRow4*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Fistt6$BallRow5*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Fistt6$BallRow6*ACTIVE SET 0\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Second_6$BallRow7*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Second_6$BallRow8*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Second_6$BallRow9*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Second_6$BallRow10*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Second_6$BallRow11*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Second_6$BallRow12*ACTIVE SET 0\0");
			
			int row_id = 0, len=0,omo_num =0,row = 0;
			String cont = "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$TeamLogo$Rectangle*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$TeamLogo$TeamColour*TEXTURE*IMAGE SET " + team_color + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + 
								match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$TeamLogo$Rectangle*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$TeamLogo$TeamColour*TEXTURE*IMAGE SET " + team_color + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					}
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
							len=len+1;
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallDataGrp*FUNCTION*Omo*vis_con SET " + 
//									len + " \0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallRightDataAll$BallRightDataGrp*FUNCTION*Omo*vis_con SET " + 
//									len + " \0");
						}
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
							
							if(row_id <= 6) {
								cont = "$Fistt6";
							}else {
								cont = "$Second_6";
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$Second_6*FUNCTION*Omo*vis_con SET " + row + " \0");
								row = row + 1;
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BallRow"
									+ row_id + "*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData" + cont + "$BallRow" + 
									row_id + "$RowAnimation$BallOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallDataGrp$BallRow" + row_id + "$RowAnimation$BallOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallDataGrp$BallRow" + row_id + "*ACTIVE SET 1 \0");
//							
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallRightDataAll$BallRightDataGrp$BallDetailRow" + row_id + 
//									"$RowAnimation$BallDetailOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$BowlingCardAll$BallData$BallRightDataAll$BallRightDataGrp$BallDetailRow" + row_id + "*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_id + " SET " + 
									boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerFigures" + row_id + " SET " + 
									boc.getWickets() + "-" + boc.getRuns() + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerOvers" + row_id + " SET " + 
									CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + photo_path +
										inn.getBowling_team().getTeamName3().toUpperCase()+ centre_path + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+ "c\\Images\\FAIRBREAK\\Photos\\" +inn.getBowling_team().getTeamName3().toUpperCase()
										+ centre_path + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +
										inn.getBowling_team().getTeamName3().toUpperCase()+ centre_path + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
							
					}
				}
			}
			if(is_this_updating == false) {
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.900 In$BatDataIn 0.772 \0");
			}
//			Preview(print_writer, viz_scene, which_graphic_on_screen, "BOWLINGCARD");
		}
	}
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning,String Type, MatchAllData match, String broadcaster,List<VariousText> vt, Configuration config) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateMatchsummary -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateMatchsummary -> inning is null");
		} else {
			this.status = CricketUtil.SUCCESSFUL;
		int row_id = 0, max_Strap = 0, total_inn = 0,omo = 0;
			String teamname = "",teamlogoname="",Player_photo=""; 
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumHeader1" + " SET " + 
					"SUMMARY" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumHeader2" + " SET " + 
					match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + 
					match.getSetup().getTournament().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryTeamLogo1" + " SET " + logo_path + 
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryTeamLogo2" + " SET " + logo_path + 
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
			
			for(int i = 1; i <= whichInning ; i++) {

				if(i == 1) {
					row_id = 0;
					max_Strap = 3;
					omo = 1;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1" + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1" + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
					
				} else {
					row_id = 2;
					max_Strap = 6;
					omo = 4;
					
					if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getTossWinningTeam()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow4" + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow4" + 
								"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
					}
				}
				
				if(match.getMatch().getInning().get(i-1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {
					teamname = match.getSetup().getHomeTeam().getTeamName1();
					teamlogoname = match.getSetup().getHomeTeam().getTeamName3();
					for(Player hs : match.getSetup().getHomeSquad()) {
						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)||hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							Player_photo =  hs.getPhoto();
						}
					}
				} else {
					teamname = match.getSetup().getAwayTeam().getTeamName1();
					teamlogoname = match.getSetup().getAwayTeam().getTeamName3();
					for(Player as : match.getSetup().getAwaySquad()) {
						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)||as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							Player_photo =  as.getPhoto();
						}
					}
				}
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTeamFirstName" + i + " SET " + 
						"" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTeamLastName" + i + " SET " + 
						teamname.toUpperCase() + "\0");
				
				if(match.getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTotalScore" + i + " SET " + 
							match.getMatch().getInning().get(i-1).getTotalRuns() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTotalScore" + i + " SET " + 
							match.getMatch().getInning().get(i-1).getTotalRuns() + slashOrDash 
							+ String.valueOf(match.getMatch().getInning().get(i-1).getTotalWickets()) + "\0");
				}
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumOvers" + i + " SET " + 
						CricketFunctions.OverBalls(match.getMatch().getInning().get(i-1).getTotalOvers(),match.getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
				
				if(match.getMatch().getInning().get(i-1).getBattingCard() != null) {
					Collections.sort(match.getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					
					for(BattingCard bc : match.getMatch().getInning().get(i-1).getBattingCard()) {
						if(bc.getRuns() > 0) {
							if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								row_id = row_id + 1;
								omo = omo + 1;
								
//								if(CricketFunctions.checkImpactPlayer(match.getEvents(), i, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary$SummaryDataAll$Inning" + i + "$Row" + row_id + 
//											"$RowOut$RowAni$RowData$Left$Select_Star*FUNCTION*Omo*vis_con SET " + "1" +"\0");
//									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$SummaryAll$Summary$SummaryDataAll$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//								}else if(CricketFunctions.checkImpactPlayerBowler(match.getEvents(), i, bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary$SummaryDataAll$Inning" + i + "$Row" + row_id + 
//											"$RowOut$RowAni$RowData$Left$Select_Star*FUNCTION*Omo*vis_con SET " + "1" +"\0");
//									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$SummaryAll$Summary$SummaryDataAll$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//								}else {
//									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary$SummaryDataAll$Inning" + i + "$Row" + row_id + 
//											"$RowOut$RowAni$RowData$Left$Select_Star*FUNCTION*Omo*vis_con SET " + "0" +"\0");
//								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + omo + "$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
								
								if(Type.toUpperCase().equalsIgnoreCase("WITHOUT_PHOTO")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 0 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow3$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 0 \0");
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow5$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 0 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 0 \0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 1 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow3$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 1 \0");
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow5$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 1 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 1 \0");
								}
								
								for(Player hs : match.getSetup().getHomeSquad()) {
									if(bc.getPlayerId() == hs.getPlayerId()) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + row_id + " SET " + 
													photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + row_id + " SET " + 
													"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										
									}
								}
								
								for(Player as : match.getSetup().getAwaySquad()) {
									if(bc.getPlayerId() == as.getPlayerId()) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + row_id + " SET " + 
													photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + row_id + " SET " + 
													"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										
									}
								}
								
//								for(int k=1;k<=match.getHomeSquad().size();k++) {
//									if(bc.getPlayerId() == match.getHomeSquad().get(i-1).getPlayerId()) {
//										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + i + " SET " + 
//												photo_path + match.getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//									}
//								}
//								
//								for(int k=1;k<=match.getAwaySquad().size();k++) {
//									if(bc.getPlayerId() == match.getAwaySquad().get(i-1).getPlayerId()) {
//										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + i + " SET " + 
//												photo_path + match.getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
//									}
//								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanFlag" + row_id + " SET " + 
										flag_path + bc.getPlayer().getNationality().toUpperCase() + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanName" + row_id + " SET " + 
										bc.getPlayer().getTicker_name() + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanName" + row_id + " SET " + 
										bc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerRuns" + row_id + " SET " + 
										bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerBalls" + row_id + " SET " + 
										String.valueOf(bc.getBalls()) + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanBoundary" + row_id + " SET " + 
										bc.getFours() + "/" + bc.getSixes() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanStrikeRate" + row_id + " SET " + 
										bc.getStrikeRate() + "\0");
								
								
								TimeUnit.MILLISECONDS.sleep(2);
								
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + omo + "$RowAnimation$BatsmanGrp"
											+ "$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
								} else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + omo + "$RowAnimation$BatsmanGrp"
											+ "$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
								}
								
								TimeUnit.MILLISECONDS.sleep(2);
								
								if(i == 1 && row_id >= 2) {
									break;
								}else if(i == 2 && row_id >= 4) {
									break;
								}
							}
						}
					}
				}

				for(int j = omo+1; j <= max_Strap; j++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + j + "$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
				}
				
				if(i == 1) {
					row_id = 0;
					omo = 1;
				}
				else {
					row_id = 2;
					omo = 4;
				}

				if(match.getMatch().getInning().get(i-1).getBowlingCard() != null) {
					
					Collections.sort(match.getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

					for(BowlingCard boc : match.getMatch().getInning().get(i-1).getBowlingCard()) {
						
						if(boc.getWickets() > 0) {
							row_id = row_id + 1;
							omo = omo + 1;
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + omo + "$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
							
							if(Type.toUpperCase().equalsIgnoreCase("WITHOUT_PHOTO")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 0 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow3$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 0 \0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow5$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 0 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 0 \0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow3$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 1 \0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow5$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 1 \0");
							}
							
							for(Player hs : match.getSetup().getHomeSquad()) {
								if(boc.getPlayerId() == hs.getPlayerId()) {
									if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage" + row_id + " SET " + 
												photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									}else {
										if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
											this.status = CricketUtil.UNSUCCESSFUL;
										}
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage" + row_id + " SET " + 
												"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									}
									
								}
							}
							
							for(Player as : match.getSetup().getAwaySquad()) {
								if(boc.getPlayerId() == as.getPlayerId()) {
									if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage" + row_id + " SET " + 
												photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									}else {
										if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
											this.status = CricketUtil.UNSUCCESSFUL;
										}
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage" + row_id + " SET " + 
												"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
									}
									
								}
							}
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerFlag" + row_id + " SET " + 
									flag_path + boc.getPlayer().getNationality().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerName" + row_id + " SET " + 
									boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerFigures" + row_id + " SET " + 
									boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerOvers" + row_id + " SET " + 
									CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerEconomy" + row_id + " SET " + 
									boc.getEconomyRate() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerDots" + row_id + " SET " + 
									boc.getDots() + "\0");
							
//							if(CricketFunctions.checkImpactPlayerBowler(match.getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary$SummaryDataAll$Inning" + i + "$Row" + row_id + 
//										"$RowOut$RowAni$RowData$Right$Select_Star*FUNCTION*Omo*vis_con SET " + "1" +"\0");
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$SummaryAll$Summary$SummaryDataAll$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//							}else if(CricketFunctions.checkImpactPlayer(match.getEvents(), whichInning, boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary$SummaryDataAll$Inning" + i + "$Row" + row_id + 
//										"$RowOut$RowAni$RowData$Right$Select_Star*FUNCTION*Omo*vis_con SET " + "1" +"\0");
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$SummaryAll$Summary$SummaryDataAll$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//							}else {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Summary$SummaryDataAll$Inning" + i + "$Row" + row_id + 
//										"$RowOut$RowAni$RowData$Right$Select_Star*FUNCTION*Omo*vis_con SET " + "0" +"\0");
//							}
							
							
							if(i == 1 && row_id >= 2) {
								break;
							}
							else if(i == 2 && row_id >= 4) {
								break;
							}
						}
						
					}
				}
				for(int j = omo+1; j <= max_Strap; j++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + j + "$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
				}
			}
			
			for(VariousText vartext : vt) {
				if(vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
							vartext.getVariousText() + "\0");
					}else if(vartext.getVariousType().equalsIgnoreCase("MATCHSUMMARYFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
					if(match.getMatch().getMatchResult() != null) {
						if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
									CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
						}
						else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
									"MATCH TIED" + "\0");
						}
						else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
									match.getMatch().getMatchStatus().toUpperCase() + "\0");
						}
						else if(match.getMatch().getMatchResult().split(",")[2].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
									"MATCH TIED - " + CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
									CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
						}
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
								CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
						
						if(match.getSetup().getTargetType() != null) {
							if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
										CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
								
							}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
										CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
							}
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 SummaryIn 1.400 SummaryOffsetIn 0.842 BatPartnershipIn 0.000 "
					+ "BattingRightCardIn 0.000 BatPerformerIn 0.000 BattingCardIn 0.000 BattingCardIn$BatOffsetIn 0.000 BallPerformerIn 0.000 BowlingCardIn 0.000 BowlingCardIn$BallOffsetIn 0.000 BowlingRightCardIn 0.000 BowlingRightCardIn$BallRightOffset 0.000 PointsTableIn 0.000 PointsOffsetIn 0.000 \0");	
//			Preview(print_writer, viz_scene, which_graphic_on_screen, "MATCHSUMMARY");
		}
	}
	public void populatePartnership(PrintWriter print_writer, String viz_scene,int whichInning,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			System.out.println("ERROR: populatePartnership -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populatePartnership -> inning is null");
		} else {
			
			int row_id = 0, omo_num = 0,Top_Score = 50;
			float Mult = 322, ScaleFac1 = 0, ScaleFac2 = 0;
			String cont_name= "",Left_Batsman = "",Right_Batsman="";

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + 
					"PARTNERSHIPS" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + 
					match.getSetup().getMatchIdent() + "\0");
			
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {

				//if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					if (inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$TeamLogoInGrp1$LogoInAllGrp$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatTeamIcon1*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatTeamIcon3*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + 
								match.getSetup().getHomeTeam().getTeamName1() + "\0");
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$TeamLogoInGrp1$LogoInAllGrp$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatTeamIcon1*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatTeamIcon2*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatTeamIcon3*TEXTURE*IMAGE SET " + logo_path + 
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + 
								match.getSetup().getAwayTeam().getTeamName1() + "\0");
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

					for (Partnership ps : inn.getPartnerships()) {
						
						row_id = row_id + 1;
						Left_Batsman ="" ; Right_Batsman="";
						for (BattingCard bc : inn.getBattingCard()) {
							
//							if(CricketFunctions.checkImpactPlayer(match.getEvents(), whichInning, ps.getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$PartnershipDataAll$PartGrp$PartRow" + row_id + 
//										"$RowOut$RowAni$PartData$Select_Star1*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//							}else if(CricketFunctions.checkImpactPlayerBowler(match.getEvents(), whichInning, ps.getFirstBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$PartnershipDataAll$PartGrp$PartRow" + row_id + 
//										"$RowOut$RowAni$PartData$Select_Star1*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//							}else {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$PartnershipDataAll$PartGrp$PartRow" + row_id + 
//										"$RowOut$RowAni$PartData$Select_Star1*FUNCTION*Omo*vis_con SET " + "0" +" \0");
//							}
//							
//							if(CricketFunctions.checkImpactPlayer(match.getEvents(), whichInning, ps.getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$PartnershipDataAll$PartGrp$PartRow" + row_id + 
//										"$RowOut$RowAni$PartData$Select_Star2*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//							}else if(CricketFunctions.checkImpactPlayerBowler(match.getEvents(), whichInning, ps.getSecondBatterNo()).equalsIgnoreCase(CricketUtil.YES)) {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$PartnershipDataAll$PartGrp$PartRow" + row_id + 
//										"$RowOut$RowAni$PartData$Select_Star2*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$ImpactOut$ImpactIn$Select_Impact*FUNCTION*Omo*vis_con SET " + "1" +" \0");
//							}else {
//								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$PartnershipDataAll$PartGrp$PartRow" + row_id + 
//										"$RowOut$RowAni$PartData$Select_Star2*FUNCTION*Omo*vis_con SET " + "0" +" \0");
//							}
							
							if(bc.getPlayerId() == ps.getFirstBatterNo()) {
								Left_Batsman = bc.getPlayer().getTicker_name().toUpperCase();
							}
							else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
								Right_Batsman = bc.getPlayer().getTicker_name().toUpperCase();
							}
						}
						
						if(inn.getPartnerships().size() >= 10) {
							if(ps.getPartnershipNumber()<=inn.getPartnerships().size()) {
								omo_num = 2;
								cont_name = "$Dehighlight";
							}
						}
						else {
							if(ps.getPartnershipNumber()<inn.getPartnerships().size()) {
								omo_num = 2;
								cont_name = "$Dehighlight";
							}
							else if(ps.getPartnershipNumber() >= inn.getPartnerships().size()) {
								omo_num = 3;
								cont_name = "$Highlight";
							}
						}
						
						ScaleFac1 = ((ps.getFirstBatterRuns())*(Mult/Top_Score)) ;
						ScaleFac2 = ((ps.getSecondBatterRuns())*(Mult/Top_Score)) ;
						/*if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$PartnershipDataAll$PartGrp*FUNCTION*Grid*num_row SET " + (inn.getBattingCard().size() - 1) + " \0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + (inn.getBattingCard().size() - 1) + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PartnershipCard$PartnershipDataAll$PartGrp*FUNCTION*Grid*num_row SET " + inn.getBattingCard().size() + " \0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vRows" + " SET " + inn.getBattingCard().size() + "\0");
						}*/
						
						if(inn.getTotalWickets() >= 9) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatDataGrp*FUNCTION*Grid*num_row SET " + inn.getPartnerships().size() + " \0");
						}else{
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatDataGrp*FUNCTION*Grid*num_row SET " + inn.getBattingCard().size() + " \0");
						}
						


						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftPlayerName" + row_id + " SET " + 
								Left_Batsman + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRightPlayerName" + row_id + " SET " + 
								Right_Batsman + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartnershipRun" + row_id + " SET " + 
								ps.getTotalRuns() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartnershipBall" + row_id + " SET " + 
								ps.getTotalBalls() + "\0");
						
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo" + cont_name + "$Bar*FUNCTION*BarValues*Bar_Value__1 SET " + ScaleFac1 + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo" + cont_name + "$Bar*FUNCTION*BarValues*Bar_Value__2 SET " + ScaleFac2 + "\0");
						
							
					}
					if(inn.getPartnerships().size() >= 10) {
						row_id = row_id + 1;
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatDataGrp$Row" + row_id  + 
								"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET 4 \0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$PartnershipAll$Data$BatDataGrp$Row" + row_id  + "$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + "4" + " \0");
					}
					else {
						for (BattingCard bc : inn.getBattingCard()) {
							if(row_id < inn.getBattingCard().size()) {
								if(row_id == inn.getPartnerships().size()) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatDataGrp$Row" + row_id  + 
											"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET 0 \0");
									
									if(match.getMatch().getInning().get(whichInning - 1).getTotalOvers() == match.getSetup().getMaxOvers() 
											|| match.getMatch().getInning().get(whichInning - 1).getTotalWickets() >= 10 ) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDidNotBat" + row_id + " SET " + 
												"DID NOT BAT" + "\0");
									}else if(match.getSetup().getTargetOvers() != null && !match.getSetup().getTargetOvers().isEmpty()) {
										if( match.getMatch().getInning().get(whichInning - 1).getTotalOvers() == Integer.valueOf(match.getSetup().getTargetOvers()) 
												|| match.getMatch().getInning().get(whichInning - 1).getTotalWickets() >= 10) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDidNotBat" + row_id + " SET " + 
													"DID NOT BAT" + "\0");
										}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 
												|| CricketFunctions.getWicketsLeft(match,whichInning) <= 0) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDidNotBat" + row_id + " SET " + 
													"DID NOT BAT" + "\0");
										}else {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDidNotBat" + row_id + " SET " + 
													"STILL TO BAT" + "\0");
										}
									}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() <= 0 || CricketFunctions.GetTargetData(match).getRemaningBall() <= 0 || CricketFunctions.getWicketsLeft(match,whichInning) <= 0) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDidNotBat" + row_id + " SET " + 
												"DID NOT BAT" + "\0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDidNotBat" + row_id + " SET " + 
												"STILL TO BAT" + "\0");
									}
								}
								else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatDataGrp$Row" + row_id  + 
											"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET 1 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLeftPlayerName" + row_id + " SET " + 
											bc.getPlayer().getTicker_name() + "\0");
								}	
							}
							else {
								break;
							}
						}
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tExtrasValue" + " SET " + 
							inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOversValue" + " SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					
					
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET " + 
								inn.getTotalRuns() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTotalScore" + " SET " + 
								inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataIn 3.063 \0");
			
		}
	}
	public void populateTeamsLogo(PrintWriter print_writer,String viz_scene, List<Team> teams ,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$Left$LeaftBaseGrp$LeftLogo$LeftLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET "
				+ logo_path + "TLogo" + CricketUtil.PNG_EXTENSION + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + match.getSetup().getTournament() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "TEAMS" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getVenueName().toUpperCase() + "\0");
		/*
		 * print_writer.
		 * println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " +
		 * "tSubHeader1" + " SET " + "POOL A" + "\0"); print_writer.
		 * println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " +
		 * "tSubHeader2" + " SET " + "POOL B" + "\0");
		 */
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo1" + " SET " + "IMAGE*/Default/Nepal_T20/Logos/" + "BSK" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName01" + " SET " + "BIRATNAGAR SUPERKINGS" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo2" + " SET " + "IMAGE*/Default/Nepal_T20/Logos/" + "PA" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName02" + " SET " + "POKHARA AVENGERS" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo3" + " SET " + "IMAGE*/Default/Nepal_T20/Logos/" + "LAS" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName03" + " SET " + "LUMBINI ALL STARS" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo4" + " SET " + "IMAGE*/Default/Nepal_T20/Logos/" + "JR" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName04" + " SET " + "JANAKPUR ROYALS" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo5" + " SET " + "IMAGE*/Default/Nepal_T20/Logos/" + "FWU" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName05" + " SET " + "FAR WEST UNITED" + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgLogo6" + " SET " + "IMAGE*/Default/Nepal_T20/Logos/" + "KK" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName06" + " SET " + "KATHMANDU KNIGHTS" + "\0");
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 2.474 In$DataIn 1.520  \0");
			
	}
	public void populateBugDismissal(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateBugDismissal -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBugDismissal -> inning is null");
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getFirstname() + "\0");
								}
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
													bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bc.getHowOutText() + "\0");
											
										}
									}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + 
													bc.getHowOutPartOne().replace("(SUB)", "") + " " + bc.getHowOutPartTwo() + "\0");
											
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bc.getHowOutText() + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bc.getHowOutText() + "\0");
									}
									
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
									
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "S/R : " + bc.getStrikeRate() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
								}
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.714 \0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}
	public void populateBugPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
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
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "CURRENT PARTNERSHIP" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + Left_Batsman + " " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns()
							+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ")" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() 
							+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + Right_Batsman + "  " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns()
							+ " (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ")" + "\0");
					
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.714 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populateBugToss(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "FAIR_BREAK":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + " WON TOSS & CHOSE TO " 
							+ match.getSetup().getTossWinningDecision() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");

				}else {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + " WON TOSS & CHOSE TO " 
							+ match.getSetup().getTossWinningDecision() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
					
				}
				
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.714 \0");
			TimeUnit.MILLISECONDS.sleep(200);
			break;
		}
	}
	public void populateBug(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateBug -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateBug -> inning is null");
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bc.getRuns() +"* "+ "(" + bc.getBalls() + ")" + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
								}
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bc.getPlayer().getFirstname() + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "4s : " + bc.getFours() + " 6s : " + bc.getSixes() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "S/R : " + bc.getStrikeRate() + "\0");
								
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.714 \0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}	
	public void populateBugBowler(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: BugBowler's inning is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$Bug_ALL$Select*FUNCTION*Omo*vis_con SET 0 \0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + boc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.714 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populateBugPowerPLay(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Powerplay's inning is null";
		} else {
			
			for (Inning inn : match.getMatch().getInning()) {
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "POWERPLAY" + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "   " +
						match.getMatch().getInning().get(whichInning-1).getBatting_team().getTeamName1().toUpperCase() + " : " +
						CricketFunctions.getPowerPlayScore(inn, whichInning, "-", match) + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
				
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.714 \0");
		TimeUnit.MILLISECONDS.sleep(200);
	}
	public void populateBugHighlight(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Powerplay's inning is null";
		} else {
			
			String Value = "";
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "HIGHLIGHTS" + "\0");
			
			if (match.getMatch().getInning().get(whichInning-1).getTotalWickets() >= 10) {
				Value = String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalRuns());
			} else {
				Value = String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalRuns()) + " - " 
						+ String.valueOf(match.getMatch().getInning().get(whichInning-1).getTotalWickets());
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "   " + 
					match.getMatch().getInning().get(whichInning-1).getBatting_team().getTeamName1().toUpperCase() + " : " + Value + " (" + 
					CricketFunctions.OverBalls(match.getMatch().getInning().get(whichInning-1).getTotalOvers(),match.getMatch().getInning().get(whichInning-1).getTotalBalls()) + ")" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
			
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.714 \0");
		TimeUnit.MILLISECONDS.sleep(200);		
	}
	public void populateBugMultipartnership(PrintWriter print_writer, String viz_scene,int whichinning, int partnership, MatchAllData match,String session_selected_broadcaster) throws InterruptedException {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership inning is null";
		} else {
			
			for (Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == whichinning) {
					String Left_Batsman ="",Right_Batsman="";
					
					for (Player hs : match.getSetup().getHomeSquad()) {
						if(hs.getPlayerId() == inn.getPartnerships().get(partnership - 1).getFirstBatterNo()) {
							Left_Batsman = hs.getTicker_name();
						}
						if(hs.getPlayerId() == inn.getPartnerships().get(partnership - 1).getSecondBatterNo()) {
							Right_Batsman = hs.getTicker_name();
						}
					}
					for (Player as : match.getSetup().getAwaySquad()) {
						if(as.getPlayerId() == inn.getPartnerships().get(partnership - 1).getFirstBatterNo()) {
							Left_Batsman = as.getTicker_name();
						}
						if(as.getPlayerId() == inn.getPartnerships().get(partnership - 1).getSecondBatterNo()) {
							Right_Batsman = as.getTicker_name();
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + Left_Batsman + " & " + Right_Batsman + "\0");
					
					if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "   " +
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "st WICKET PARTNERSHIP" + "\0");
						
					}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 2) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "   " +
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "nd WICKET PARTNERSHIP" + "\0");
						
					}else if(inn.getPartnerships().get(partnership - 1).getPartnershipNumber() == 3) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "   " +
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "rd WICKET PARTNERSHIP" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "   " +
								(inn.getPartnerships().get(partnership - 1).getPartnershipNumber()) + "th WICKET PARTNERSHIP" + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + 
							inn.getPartnerships().get(partnership - 1).getTotalRuns() + " (" + inn.getPartnerships().get(partnership - 1).getTotalBalls() + ")" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + " " + "\0");
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.714 \0");
		TimeUnit.MILLISECONDS.sleep(200);
	}
	
	public void populateHowout(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOut's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId()==playerId) {
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
									bc.getPlayer().getNationality().toUpperCase() + "\0");
							
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
							if(bc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + bc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getFirstname() + "\0");
							}
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + "retired hurt" + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + "absent hurt" + "\0");
								}
							}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								if(bc.getHowOutPartOne().trim() == "") {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");								
								}else {
									if(bc.getHowOut().equalsIgnoreCase(CricketUtil.LBW)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");
									}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + 
													bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");
										}
									}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), whichInning, bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " +bc.getHowOutPartOne().replace("(SUB)", "") + 
													" " + bc.getHowOutPartTwo() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");
									}
								}
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + bc.getBalls() + "\0");
													
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 2.000\0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populateHowoutquick(PrintWriter print_writer,String viz_scene,MatchAllData match, String broadcaster) throws InterruptedException 
	{	
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOutQuick's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
					}
					
					for (BattingCard bc : inn.getBattingCard()) {
						if(inn.getFallsOfWickets().size() > 0) {
							if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getTeamName1() + CricketUtil.PNG_EXTENSION  + ";");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
										bc.getPlayer().getNationality().toUpperCase() + "\0");
								
								if(bc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + bc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getFirstname() + "\0");
								}
								
								if(bc.getHowOutPartOne().trim() == "") {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");								
								}else {
									if(bc.getHowOut().equalsIgnoreCase(CricketUtil.LBW)) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");
									}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + 
													bc.getHowOutPartOne() + " (" + bc.getHowOutPartTwo().split(" ")[0] + ")" + "\0");
											
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");
										}
									}else if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
										if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES) || 
												CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getHowOutFielderId()).equalsIgnoreCase(CricketUtil.YES)) {
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " +bc.getHowOutPartOne().replace("(SUB)", "") + 
													" " + bc.getHowOutPartTwo() + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutText() + "\0");
										}
									}else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + bc.getHowOutPartOne() + " " + 
												bc.getHowOutPartTwo() + "\0");
									}
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + bc.getBalls() + "\0");	
							}
						}						
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 2.000\0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populateHowoutWithoutFielder(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: HowOutWithoutFielder's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId()==playerId) {
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
									bc.getPlayer().getNationality().toUpperCase() + "\0");
							
							if(bc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + bc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + bc.getPlayer().getFirstname() + "\0");
							}

							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHowOut" + " SET " + 
									"FOURS : " + bc.getFours() + "  SIXES : "+ bc.getSixes() + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + (bc.getBalls() + 1) + "\0");							
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 2.000\0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}	
	public void populateBatsmanstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: BatsmanStats's inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, playerId,",", match.getEventFile().getEvents()).split(",");
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
										inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
										inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
										inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
										bc.getPlayer().getNationality().toUpperCase() + "\0");
								
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
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "FOURS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + bc.getFours() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "SIXES" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + bc.getSixes() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "STRIKE RATE" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + bc.getStrikeRate() + "\0");
								
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.857 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}	
	}	
	public void populateBowlerstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId,List<Team> team, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: BowlerStats's inning is null";
		} else {
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
					
					switch(statsType.toUpperCase()) {
					case CricketUtil.BOWLER:
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
										boc.getPlayer().getNationality().toUpperCase() + "\0");
								
								if(boc.getPlayer().getSurname() != null) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + boc.getPlayer().getFirstname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + boc.getPlayer().getSurname() + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + boc.getPlayer().getFirstname() + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "OVERS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + "DOTS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + "RUNS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + "WKTS" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5A" + " SET " + "ECON." + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + boc.getDots() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + boc.getWickets() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5B" + " SET " + boc.getEconomyRate() + "\0");
										
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428 \0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}
	public void populateLTBowlerSpeed(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: BowlerStats's inning is null";
		} else {
			int total_inn = 0,sum =0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select*FUNCTION*Omo*vis_con SET 15 \0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$LLC_LogoGrp$img_Badges*TEXTURE*IMAGE SET " + logo_path +
							inn.getBowling_team().getTeamName3().toLowerCase() + "\0");

					print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base1*TEXTURE*IMAGE SET " + base_path + "1/" + 
							inn.getBowling_team().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
							inn.getBowling_team().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select$OneTeam$TopBand$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
							inn.getBowling_team().getTeamName3().toLowerCase() +" \0");
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getPlayerId()==playerId) {
							
							if(boc.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$AvgSpeed$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + boc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$AvgSpeed$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + boc.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$AvgSpeed$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$AvgSpeed$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + boc.getPlayer().getFirstname() + "\0");
							}								
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$AvgSpeed$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data2A*GEOM*TEXT SET " + 
										CricketFunctions.speedData(match, whichInning, playerId).get(CricketFunctions.speedData(match, whichInning, playerId).size() - 1) + " Kph" + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$AvgSpeed$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
										CricketFunctions.speedData(match, whichInning, playerId).get(0) + " Kph" + "\0");
							
							for (int i = 0; i < CricketFunctions.speedData(match, whichInning, playerId).size(); i++) {
					            sum += CricketFunctions.speedData(match, whichInning, playerId).get(i);
					 
					    	}
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$AvgSpeed$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data3A*GEOM*TEXT SET " + "AVERAGE BALL" + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$AvgSpeed$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
									(sum/CricketFunctions.speedData(match, whichInning, playerId).size()) + " Kph" + "\0");	
						}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg LT$LTLogoIn 2.000 LT$LTBaseIn 2.000 LT$LTDataIn 2.000\0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}
	public void populateBugsDB(PrintWriter print_writer,String viz_scene, Bugs bug ,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			if(bug.getText1() != null && bug.getText2() != null && bug.getText3() != null) {
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText3() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bug.getText2() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");

			}else if(bug.getText1() != null && bug.getText2() == null && bug.getText3() == null) {
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");

			}else if(bug.getText1() != null && bug.getText2() != null && bug.getText3() == null) {
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + "  " + bug.getText2() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");

			}else if(bug.getText1() != null && bug.getText2() == null && bug.getText3() != null) {
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + bug.getText1() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText3() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");

			}else if(bug.getText1() == null && bug.getText2() != null && bug.getText3() != null) {
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo1" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo2" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo3" + " SET " + bug.getText3() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo4" + " SET " + bug.getText2() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo5" + " SET " + "" + "\0");
				
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 0.714 \0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}
	public void populateLTBallSince(PrintWriter print_writer,String viz_scene,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select*FUNCTION*Omo*vis_con SET 14 \0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$LLC_LogoGrp$img_Badges*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$LLC_LogoGrp$Basegrp$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
							inn.getBatting_team().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base1*TEXTURE*IMAGE SET " + base_path + "1/" + 
							inn.getBatting_team().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
							inn.getBatting_team().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select$OneTeam$TopBand$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
							inn.getBatting_team().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$TimeSince$BottomGrp$ScoreGrp$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + 
							inn.getBatting_team().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$TimeSince$BottomGrp$RestDataGrp$Visible$Rest$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$TimeSince$BottomGrp$RestDataGrp$Visible$Rest$img_Text2$txt_Ball*GEOM*TEXT SET " + 
							CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber()) + "\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$TimeSince$BottomGrp$RestDataGrp$Visible$txt_Data1*GEOM*TEXT SET " + "BALL" + 
							CricketFunctions.Plural(Integer.valueOf(CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber())))+ "\0");
					}
			}
			print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$TimeSince$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + "BALL SINCE LAST BOUNDARY" + "\0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg LT$LTLogoIn 2.000 LT$LTBaseIn 2.000 LT$LTDataIn 2.000\0");
			TimeUnit.MILLISECONDS.sleep(200);
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
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$noname$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					for(int b=1;b<=inn.getBattingCard().size();b++) {
						if(inn.getBattingCard().get(b-1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							row_id = row_id + 1;
							if(row_id <= 3) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPosition" + row_id + " SET " + b + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_id + " SET " + 
										inn.getBattingCard().get(b-1).getPlayer().getTicker_name() + "\0");
								
								for(Statistics st : stats) {
									if(st.getPlayer_id()==inn.getBattingCard().get(b-1).getPlayerId() && st.getStats_type_id() == 3) {
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
											inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
											inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + inn.getBattingCard().get(b-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
							}
						}
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.494\0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}
	public void populateNameSuper(PrintWriter print_writer,String viz_scene, NameSuper ns ,MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vFlag" + " SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					"fair_break" + "\0");
			if(ns.getSponsor() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
						"fair_break" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
						"fair_break" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
						ns.getSponsor() + "\0");
			}
			
			if(ns.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + 
						ns.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + 
						ns.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + 
						"" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + 
						ns.getFirstname() + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
					ns.getSubLine().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428\0");
			TimeUnit.MILLISECONDS.sleep(200);	
		}
	}	
	public void populateNameSuperPlayer(PrintWriter print_writer,String viz_scene, int TeamId, String captainWicketKeeper, int playerId, List<Player> plyr, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			String Home_or_Away="";
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vFlag" + " SET " + "1" + "\0");
			
			if(TeamId == match.getSetup().getHomeTeamId()) {
				for(Player hs : match.getSetup().getHomeSquad()) {
					if(playerId == hs.getPlayerId()) {
						Home_or_Away = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
						
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
								hs.getNationality().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
					
						if(hs.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + hs.getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + hs.getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + hs.getFirstname() + "\0");
						}
					}
				}
				for(Player hos : match.getSetup().getHomeOtherSquad()) {
					if(playerId == hos.getPlayerId()) {
						Home_or_Away = match.getSetup().getHomeTeam().getTeamName1().toUpperCase();
						
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
								hos.getNationality().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "" + "\0");
						
						if(hos.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + hos.getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + hos.getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + hos.getFirstname() + "\0");
						}
					}
				}
			}
			else {
				for(Player as : match.getSetup().getAwaySquad()) {
					if(playerId == as.getPlayerId()) {
						Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
						
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
								as.getNationality().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "" + "\0");
						
						if(as.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + as.getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + as.getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + as.getFirstname() + "\0");
						}
					}
				}
				for(Player aos : match.getSetup().getAwayOtherSquad()) {
					if(playerId == aos.getPlayerId()) {
						Home_or_Away = match.getSetup().getAwayTeam().getTeamName1().toUpperCase();
						
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
								aos.getNationality().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "" + "\0");
						
						if(aos.getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + aos.getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + aos.getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + aos.getFirstname() + "\0");
						}
					}
				}
			}
			
//			print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET " + " " + "\0");
			TimeUnit.MILLISECONDS.sleep(4);
			switch(captainWicketKeeper.toUpperCase())
			{
			case CricketUtil.CAPTAIN:
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						captainWicketKeeper.toUpperCase() + ", " + Home_or_Away + "\0");
				break;
			case CricketUtil.WICKET_KEEPER:
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						"WICKET KEEPER" + ", " + Home_or_Away + "\0");
				break;	
			case "PLAYER OF THE MATCH":
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						captainWicketKeeper.toUpperCase() + "\0");
				break;
			case "PLAYER OF THE TOURNAMENT":
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						captainWicketKeeper.toUpperCase() + "\0");
				break;
			case "PLAYER OF THE SERIES":
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						captainWicketKeeper.toUpperCase() + "\0");
				break;	
			case CricketUtil.PLAYER:
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						Home_or_Away + "\0");
				break;
			case "CAPTAIN-WICKETKEEPER":
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						"CAPTAIN & WICKETKEEPER" + ", " + Home_or_Away + "\0");
				break;
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428\0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats,List<Player> plyer, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			double strike_rate = 0;
			
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCareer" + " SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCareer" + " SET " + "T20I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FBS1")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCareer" + " SET " + "FAIRBREAK INVITATIONAL 2022" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCareer" + " SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			
			if(plyr.getInstagramHandle() == null && plyr.getTwitterHandle() == null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia*ACTIVE SET 0 \0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia*ACTIVE SET 1 \0");
				
				if(plyr.getInstagramHandle() == null && plyr.getTwitterHandle() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Instagram*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Twitter*ACTIVE SET 1 \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTwitterText" + " SET " + plyr.getTwitterHandle() + "\0");
					
				}else if(plyr.getInstagramHandle() != null && plyr.getTwitterHandle() == null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Instagram*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Twitter*ACTIVE SET 0 \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInstagramText" + " SET " + plyr.getInstagramHandle() + "\0");
					
				}else if(plyr.getInstagramHandle() != null && plyr.getTwitterHandle() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Instagram*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Twitter*ACTIVE SET 1 \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTwitterText" + " SET " + plyr.getTwitterHandle() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInstagramText" + " SET " + plyr.getInstagramHandle() + "\0");
				}
			}
			
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getHomeTeam().getTeamName1() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$NameBands$INDIA*TEXTURE*IMAGE SET "+ flag_path + 
						plyr.getNationality() + " \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}
		
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAgeValue" + " SET " + plyr.getAge() + "\0");
				
				if(plyer.get(plyr.getPlayerId()-1).getBattingStyle() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
							CricketFunctions.getbattingstyle(plyer.get(plyr.getPlayerId()-1).getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
				}
				
				
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getAwayTeam().getTeamName1() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$NameBands$INDIA*TEXTURE*IMAGE SET "+ flag_path + 
						plyr.getNationality() + " \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
				
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}
		
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAgeValue" + " SET " + plyr.getAge() + "\0");
				
				if(plyer.get(plyr.getPlayerId()-1).getBattingStyle() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
							CricketFunctions.getbattingstyle(plyer.get(plyr.getPlayerId()-1).getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
				}	
			}
			
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BATSMAN:
				
				if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20) ||
					stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FBS1")) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
					
					if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "50s" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + stats.getFifties() + "\0");
						
					}else {
						strike_rate = stats.getRuns() * 100;
						strike_rate = strike_rate/stats.getBalls_faced();
						DecimalFormat df = new DecimalFormat("0.0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "STRIKE RATE" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
					}
					
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.ODI)) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
					
					if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "50s" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + stats.getFifties() + "\0");
						
					}else {
						strike_rate = stats.getRuns() * 100;
						strike_rate = strike_rate/stats.getBalls_faced();
						DecimalFormat df = new DecimalFormat("0.0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "STRIKE RATE" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
					}
				}
				break;	
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 DataIn 3.000 LogoIn 2.100 \0");
			TimeUnit.MILLISECONDS.sleep(200);

		} 
	}
	public void populatePlayerProfileBall(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats,List<Player> plyer, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			double economy_rate=0;
			
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCareer" + " SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCareer" + " SET " + "T20I CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FBS1")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCareer" + " SET " + "FAIRBREAK INVITATIONAL 2022" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCareer" + " SET " + 
						stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			
			if(plyr.getInstagramHandle() == null && plyr.getTwitterHandle() == null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia*ACTIVE SET 0 \0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia*ACTIVE SET 1 \0");
				
				if(plyr.getInstagramHandle() == null && plyr.getTwitterHandle() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Instagram*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Twitter*ACTIVE SET 1 \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTwitterText" + " SET " + plyr.getTwitterHandle() + "\0");
					
				}else if(plyr.getInstagramHandle() != null && plyr.getTwitterHandle() == null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Instagram*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Twitter*ACTIVE SET 0 \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInstagramText" + " SET " + plyr.getInstagramHandle() + "\0");
					
				}else if(plyr.getInstagramHandle() != null && plyr.getTwitterHandle() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Instagram*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Twitter*ACTIVE SET 1 \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTwitterText" + " SET " + plyr.getTwitterHandle() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInstagramText" + " SET " + plyr.getInstagramHandle() + "\0");
				}
			}
			
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getHomeTeam().getTeamName1() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$NameBands$INDIA*TEXTURE*IMAGE SET "+ flag_path + 
						plyr.getNationality() + " \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}
		
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAgeValue" + " SET " + plyr.getAge() + "\0");
				
				if(plyer.get(plyr.getPlayerId()-1).getBowlingStyle() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
							CricketFunctions.getbowlingstyle(plyer.get(plyr.getPlayerId()-1).getBowlingStyle()).toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
				}
				
				
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getAwayTeam().getTeamName1() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$NameBands$INDIA*TEXTURE*IMAGE SET "+ flag_path + 
						plyr.getNationality() + " \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
				
				if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}else {
					if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
						this.status = CricketUtil.UNSUCCESSFUL;
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + 
							centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
				}
				
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + plyr.getFirstname() + "\0");
				}
		
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAgeValue" + " SET " + plyr.getAge() + "\0");
				
				if(plyer.get(plyr.getPlayerId()-1).getBowlingStyle() != null) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + 
							CricketFunctions.getbowlingstyle(plyer.get(plyr.getPlayerId()-1).getBowlingStyle()).toUpperCase() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + " " + "\0");
				}
			}
			
			switch(TypeofProfile.toUpperCase()) {
			case CricketUtil.BOWLER:
				
				if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20) ||
					stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FBS1")) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
					
					if(stats.getRuns_conceded() == 0 && stats.getBalls_bowled() == 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECONOMY" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						
					}else {
						economy_rate = stats.getRuns_conceded() / stats.getBalls_bowled();
						economy_rate = economy_rate * 6;
						DecimalFormat df = new DecimalFormat("0.00");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECONOMY" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
					}
					
				}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.ODI)) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
					
					if(stats.getRuns_conceded() == 0 && stats.getBalls_bowled() == 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECONOMY" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						
					}else {
						economy_rate = stats.getRuns_conceded() / stats.getBalls_bowled();
						economy_rate = economy_rate * 6;
						DecimalFormat df = new DecimalFormat("0.00");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECONOMY" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
					}
				}
				break;	
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 DataIn 3.000 LogoIn 2.100 \0");
			TimeUnit.MILLISECONDS.sleep(200);
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
	public void populateLTSeasonProfile(PrintWriter print_writer,String viz_scene,int PlayerID,String Profile,String TypeofProfile,List<MatchAllData> tournament_matches,
			CricketService cricketService,List<Season> season,List<Team> team, MatchAllData match, String broadcaster) throws InterruptedException, IOException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			
			double strike_rate = 0,economy_rate=0;
			int k=0;

			print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select*FUNCTION*Omo*vis_con SET 11 \0");
			
			switch(Profile.toUpperCase()) {
			case "SEASON1":
				print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$txt_Data1*GEOM*TEXT SET " + "SEASON 1" + "\0");
				break;
			case "SEASON2":
				print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$txt_Data1*GEOM*TEXT SET " + "SEASON 2" + "\0");
				break;
			case "SEASON3":
				print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$txt_Data1*GEOM*TEXT SET " + "SEASON 3" + "\0");
				break;
				
			}
			
			switch(Profile.toUpperCase()) {
			case "SEASON1": case "SEASON2": case "SEASON3":
				switch(TypeofProfile.toUpperCase()) {
				case "BATSMAN":
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data1A*GEOM*TEXT SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data1A*GEOM*TEXT SET " + "RUNS" + "\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data1A*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");

					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data1A*GEOM*TEXT SET "+ "4s/6s"+"\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
							"-" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data1A*GEOM*TEXT SET "+ "BEST"+"\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
							"-" + "\0");
					
					
					for(Tournament tournament : CricketFunctions.extractSeasonStats(Profile, tournament_matches, cricketService, match, null, season)) {
						if(tournament.getPlayerId() == PlayerID) {
							print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$LLC_LogoGrp$img_Badges*TEXTURE*IMAGE SET " + logo_path +
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase()  + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$LLC_LogoGrp$Basegrp$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base1*TEXTURE*IMAGE SET " + base_path + "1/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select$OneTeam$TopBand$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							
							if(tournament.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + tournament.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + tournament.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + tournament.getPlayer().getFirstname() + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data1A*GEOM*TEXT SET " + "MATCHES" + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data2A*GEOM*TEXT SET " + tournament.getMatches() + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data1A*GEOM*TEXT SET " + "RUNS" + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data2A*GEOM*TEXT SET " + tournament.getRuns() + "\0");
							
							strike_rate = tournament.getRuns() * 100;
							strike_rate = strike_rate/tournament.getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data1A*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
							if(tournament.getBallsFaced() == 0 || tournament.getRuns()== 0) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data1A*GEOM*TEXT SET "+ "4s/6s"+"\0");
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
							tournament.getFours() + "/" + tournament.getSixes() + "\0");
							
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data1A*GEOM*TEXT SET " + "BEST" + "\0");

							List<BestStats> top_ten_beststats = new ArrayList<BestStats>();
							for(Tournament tourn : CricketFunctions.extractSeasonStats(Profile, tournament_matches, cricketService, match, null, season)) {
								if(tourn.getPlayerId() == PlayerID) {
									for(BestStats bs : tourn.getBatsman_best_Stats()) {
										top_ten_beststats.add(bs);
									}
								}
							}
							
							Collections.sort(top_ten_beststats, new CricketFunctions.PlayerBestStatsComparator());
							
							if(top_ten_beststats.get(0).getBestEquation() % 2 == 0) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + top_ten_beststats.get(0).getBestEquation() / 2 + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " 
														+ top_ten_beststats.get(0).getBestEquation() / 2  + "*" + "\0");
							}
						}
					}
					
					break;
				case "BOWLER":
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data1A*GEOM*TEXT SET " + "MATCHES" + "\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data1A*GEOM*TEXT SET " + "WICKETS" + "\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data1A*GEOM*TEXT SET " + "ECON" + "\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");

					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data1A*GEOM*TEXT SET "+ "STRIKE RATE"+"\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
							"-" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data1A*GEOM*TEXT SET "+ "BEST"+"\0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
							"-" + "\0");
					
					
					
					for(Tournament tournament : CricketFunctions.extractSeasonStats(Profile, tournament_matches, cricketService, match, null, season)) {
						
						if(tournament.getPlayerId() == PlayerID) {
							print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$LLC_LogoGrp$img_Badges*TEXTURE*IMAGE SET " + logo_path +
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase()  + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$LLC_LogoGrp$Basegrp$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base1*TEXTURE*IMAGE SET " + base_path + "1/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select$OneTeam$TopBand$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + 
									team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
							if(tournament.getPlayer().getSurname() != null) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + tournament.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + tournament.getPlayer().getSurname() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + tournament.getPlayer().getFirstname() + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data1A*GEOM*TEXT SET " + "MATCHES" + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data2A*GEOM*TEXT SET " + tournament.getMatches() + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data1A*GEOM*TEXT SET " + "WICKETS" + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data2A*GEOM*TEXT SET " + tournament.getWickets() + "\0");
							
							economy_rate = tournament.getRunsConceded() / tournament.getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data1A*GEOM*TEXT SET " + "ECON" + "\0");

							if(tournament.getRunsConceded() == 0 && tournament.getBallsBowled() == 0) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
							}
							
							DecimalFormat df_s = new DecimalFormat("0.00");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data1A*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
							if(tournament.getWickets() == 0 || tournament.getBallsBowled() == 0) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data2A*GEOM*TEXT SET " + df_s.format(tournament.getBallsBowled()/tournament.getWickets()) + "\0");
							}
							
							List<BestStats> top_bowler_beststats = new ArrayList<BestStats>();
							for(Tournament tourn : CricketFunctions.extractSeasonStats(Profile, tournament_matches, cricketService, match, null, season)) {
								
								for(BestStats bfig : tourn.getBowler_best_Stats()) {
									top_bowler_beststats.add(bfig);
								}
							}
							
							Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
							
							for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data1A*GEOM*TEXT SET " + "BEST" + "\0");
								if(top_bowler_beststats.get(j).getPlayerId() == PlayerID) {
									if(k == 0) {
										k += 1;
										if(top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
											print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
													((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
										}
										else {
											print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
													(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
										}
										break;
									}
								}else if(top_bowler_beststats.get(j).getPlayerId() != PlayerID) {
									print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
											"-" + "\0");
								}
							}
						}
					}
					break;	
				}
				break;
			case "ALLSEASON":
				switch(TypeofProfile.toUpperCase()) {
				case "BATSMAN":
				for(Tournament tournament : CricketFunctions.extractTournamentStats(Profile,false, tournament_matches, cricketService, match, null)) {
					if(tournament.getPlayerId() == PlayerID) {
						print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$LLC_LogoGrp$img_Badges*TEXTURE*IMAGE SET " + logo_path +
								team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase()  + "\0");
						print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$LLC_LogoGrp$Basegrp$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
								team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base1*TEXTURE*IMAGE SET " + base_path + "1/" + 
								team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$OneTeam$LTLogoGRP$LogoIn$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
								team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select$OneTeam$TopBand$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
								team.get(tournament.getPlayer().getTeamId() - 1 ).getTeamName3().toLowerCase() +" \0");
						
						if(tournament.getPlayer().getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + tournament.getPlayer().getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + tournament.getPlayer().getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + tournament.getPlayer().getFirstname() + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data1A*GEOM*TEXT SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data2A*GEOM*TEXT SET " + tournament.getMatches() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data1A*GEOM*TEXT SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data2A*GEOM*TEXT SET " + tournament.getRuns() + "\0");
						
						strike_rate = tournament.getRuns() * 100;
						strike_rate = strike_rate/tournament.getBallsFaced();
						DecimalFormat df = new DecimalFormat("0.0");
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data1A*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
						if(tournament.getBallsFaced() == 0 || tournament.getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + df.format(strike_rate) + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data1A*GEOM*TEXT SET "+ "4s/6s"+"\0");
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
						tournament.getFours() + "/" + tournament.getSixes() + "\0");
						
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data1A*GEOM*TEXT SET " + "BEST" + "\0");

						List<BestStats> top_ten_beststats = new ArrayList<BestStats>();
						for(Tournament tourn : CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)) {
							if(tourn.getPlayerId() == PlayerID) {
								for(BestStats bs : tourn.getBatsman_best_Stats()) {
									top_ten_beststats.add(bs);
								}
							}
						}
						
						Collections.sort(top_ten_beststats, new CricketFunctions.PlayerBestStatsComparator());
						
						if(top_ten_beststats.get(0).getBestEquation() % 2 == 0) {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + top_ten_beststats.get(0).getBestEquation() / 2 + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " 
													+ top_ten_beststats.get(0).getBestEquation() / 2  + "*" + "\0");
						}
					}
				}
				
				break;
			case "BOWLER":
				for(Tournament tournament : CricketFunctions.extractTournamentStats(Profile,false, tournament_matches, cricketService, match, null)) {
					if(tournament.getPlayerId() == PlayerID) {
						
						if(tournament.getPlayer().getSurname() != null) {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + tournament.getPlayer().getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + tournament.getPlayer().getSurname() + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header1*GEOM*TEXT SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$TopGrp$HeaderGrp$img_Text1$txt_Header2*GEOM*TEXT SET " + tournament.getPlayer().getFirstname() + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data1A*GEOM*TEXT SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$1$img_Text$txt_Data2A*GEOM*TEXT SET " + tournament.getMatches() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data1A*GEOM*TEXT SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$2$img_Text1$txt_Data2A*GEOM*TEXT SET " + tournament.getWickets() + "\0");
						
						economy_rate = tournament.getRunsConceded() / tournament.getBallsBowled();
						economy_rate = economy_rate * 6;
						DecimalFormat df_b = new DecimalFormat("0.00");
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data1A*GEOM*TEXT SET " + "ECON" + "\0");

						if(tournament.getRunsConceded() == 0 && tournament.getBallsBowled() == 0) {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$3$img_Text1$txt_Data2A*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
						}
						
						DecimalFormat df_s = new DecimalFormat("0.00");
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data1A*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
						if(tournament.getWickets() == 0 || tournament.getBallsBowled() == 0) {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data2A*GEOM*TEXT SET " + "-" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$4$img_Text1$txt_Data2A*GEOM*TEXT SET " + df_s.format(tournament.getBallsBowled()/tournament.getWickets()) + "\0");
						}
						
						List<BestStats> top_bowler_beststats = new ArrayList<BestStats>();
						for(Tournament tourn : CricketFunctions.extractTournamentStats("COMBINED_PAST_CURRENT_MATCH_DATA",false, tournament_matches, cricketService,match,null)) {
							
							for(BestStats bfig : tourn.getBowler_best_Stats()) {
								top_bowler_beststats.add(bfig);
							}
						}
						
						Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
						
						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data1A*GEOM*TEXT SET " + "BEST" + "\0");
							if(top_bowler_beststats.get(j).getPlayerId() == PlayerID) {
								if(k == 0) {
									k += 1;
									if(top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
										print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
												((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000)) + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
												(top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation()) + "\0");
									}
									break;
								}
							}else if(top_bowler_beststats.get(j).getPlayerId() != PlayerID) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$PlayerProfile$BottomGrp$RestDataGrp$Visible$RestData$Data$5$img_Text1$txt_Data2A*GEOM*TEXT SET " + 
										"-" + "\0");
							}
						}
					}
				}
				break;	
			}
				break;
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg LT$LTLogoIn 2.000 LT$LTBaseIn 2.000 LT$LTDataIn 2.000\0");
			TimeUnit.MILLISECONDS.sleep(200);
		}

	}
	public void populateLTPlayerProfile(PrintWriter print_writer,String viz_scene,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		double economy_rate=0;
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "T20 CAREER" + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "T20I CAREER"  + "\0");
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FBS1")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "FAIRBREAK INVITATIONAL 2022" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
			}
			
			Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
			if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {

				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
						plyr.getNationality().toUpperCase() + "\0");
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.getFirstname() + "\0");
				}
				
			}else {

				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
						plyr.getNationality().toUpperCase() + "\0");
				
				if(plyr.getSurname() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.getSurname() + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.getFirstname() + "\0");
				}
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECONOMY" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BOWLER:
			
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20) ||
					stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FBS1")) {
				
				if(stats.getMatches() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
				}
				
				if(stats.getWickets() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
				}
				
				if(stats.getRuns_conceded() == 0 && stats.getBalls_bowled() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
				}else {
					economy_rate = stats.getRuns_conceded() / stats.getBalls_bowled();
					economy_rate = economy_rate * 6;
					DecimalFormat df_bo = new DecimalFormat("0.00");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df_bo.format(economy_rate) + "\0");
				}
				
				if(stats.getBest_figures().equalsIgnoreCase("0")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_figures() + "\0");
				}
				
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.ODI)) {
				if(stats.getMatches() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
				}
				
				if(stats.getWickets() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getWickets() + "\0");
				}
				
				if(stats.getRuns_conceded() == 0 && stats.getBalls_bowled() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
				}else {
					economy_rate = stats.getRuns_conceded() / stats.getBalls_bowled();
					economy_rate = economy_rate * 6;
					DecimalFormat df_bo = new DecimalFormat("0.00");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df_bo.format(economy_rate) + "\0");
				}
				
				if(stats.getBest_figures().equalsIgnoreCase("0")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_figures() + "\0");
				}
			}
			
			break;	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428\0");
		TimeUnit.MILLISECONDS.sleep(200);
		}

	}
	public void populateLTPlayerProfileBat(PrintWriter print_writer,String viz_scene,String Profile,String TypeofProfile,Statistics stats, MatchAllData match, String session_selected_broadcaster) throws InterruptedException 
	{
		double strike_rate = 0;
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
		if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.DT20)) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "T20 CAREER" + "\0");
		}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20)) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "T20I CAREER"  + "\0");
		}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FBS1")) {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "FAIRBREAK INVITATIONAL 2022" + "\0");
		}else {
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + 
					stats.getStats_type().getStatsShortName().toUpperCase() + " CAREER" + "\0");
		}
		
		Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
		if(plyr.getTeamId() == match.getSetup().getHomeTeamId()) {

			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
					plyr.getNationality().toUpperCase() + "\0");
			
			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.getFirstname() + "\0");
			}
			
		}else {

			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
					plyr.getNationality().toUpperCase() + "\0");
			
			if(plyr.getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.getFirstname() + "\0");
			}
		}
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "STRIKE RATE" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
		
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			
			if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.IT20) ||
					stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase("FBS1")) {
				
				if(stats.getMatches() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
				}
				
				if(stats.getRuns() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
				}
				
				if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "50s" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + stats.getFifties() + "\0");
				}else {
					strike_rate = stats.getRuns() * 100;
					strike_rate = strike_rate/stats.getBalls_faced();
					DecimalFormat df = new DecimalFormat("0.0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "S/R" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
				}
				
				if(stats.getBest_score().equalsIgnoreCase("0")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_score() + "\0");
				}
				
			}else if(stats.getStats_type().getStatsShortName().toUpperCase().equalsIgnoreCase(CricketUtil.ODI)) {
				if(stats.getMatches() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + stats.getMatches() + "\0");
				}
				
				if(stats.getRuns() == 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + stats.getRuns() + "\0");
				}
				
				if(stats.getBalls_faced() == 0 || stats.getRuns()== 0) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
				}else {
					strike_rate = stats.getRuns() * 100;
					strike_rate = strike_rate/stats.getBalls_faced();
					DecimalFormat df = new DecimalFormat("0.0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
				}
				
				if(stats.getBest_score().equalsIgnoreCase("0")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + "-" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4" + " SET " + stats.getBest_score() + "\0");
				}
			}
			break;	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428\0");
		TimeUnit.MILLISECONDS.sleep(200);
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
	public void populateDoubleteams(PrintWriter print_writer,String viz_scene,List<Player> plyr, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			System.out.println("ERROR: populateDoubleteams -> Match is null");
		} else if (match.getMatch().getInning() == null) {
			System.out.println("ERROR: populateDoubleteams -> inning is null");
		} else {
			
			int row_id = 0;
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + 
					"TEAMS" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + 
					match.getSetup().getMatchIdent() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + 
					match.getSetup().getTournament().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo1" + " SET " + 
					logo_path + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll1$TeamNameGrp1$RowAnimation$TeamNameGrp$HomeTeamLogo$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
//					match.getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo2" + " SET " + 
					logo_path + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll2$TeamNameGrp2$RowAnimation$TeamNameGrp$AwayTeamLogo$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
//					match.getAwayTeam().getTeamName3().toLowerCase() + "\0");
			
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName1() + " ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}
			
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getAwayTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName1() + " ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}
			
			TimeUnit.MILLISECONDS.sleep(2);
			for(int i = 1; i <= 2 ; i++) {
				if(i == 1) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName1" + " SET " + 
							match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName1" + " SET " + 
							"" + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll1$TeamNameGrp1$RowAnimation$TeamNameGrp$HomeTeamLogo$LogoMasked"
							+ "$TeamLogo1*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll1$TeamNameGrp1$RowAnimation$TeamNameGrp$HomeTeamLogo$LogoMasked"
							+ "$TeamColour*TEXTURE*IMAGE SET " + team_color + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					
					TimeUnit.MILLISECONDS.sleep(2);
					for(Player hs : match.getSetup().getHomeSquad()) {
						row_id = row_id + 1;
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTeam1Row" + row_id + " SET " + 
								"1" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatDataGrp$Row" + row_id  + 
//								"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeam1FirstName" + row_id + " SET " + 
								hs.getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeam1LastName" + row_id + " SET " + 
								"" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Flag" + row_id + " SET " + 
								flag_path + hs.getNationality() + "\0");
						if(hs.getOverseasPlayer() == 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$InternationalIcon*ACTIVE SET 0 \0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$InternationalIcon*ACTIVE SET 1 \0");
						}
						
						
						
						if(hs.getRole().equalsIgnoreCase("BATSMAN")) {
							if(hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
										icon_path + "/" + "Batsman" + "\0");
							}else if(hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
										icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(hs.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if(hs.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
										icon_path + "/" + "Batsman" + "\0");
							}else if(hs.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
										icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(hs.getRole().equalsIgnoreCase("BOWLER")) {
							if(hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
										icon_path + "/" + "FastBowler" + "\0");
							}else {
								switch(hs.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
											icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
											icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						}else if(hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if(hs.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
										icon_path + "/" + "FastBowlerAllrounder" + "\0");
							}else {
								switch(hs.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
											icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
											icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						
						if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTeam1Captain" + row_id + " SET " + 
									icon_path + "/" + "CaptainIcon" + "\0");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
									icon_path + "/" + "Keeper" + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTeam1Captain" + row_id + " SET " + 
									icon_path + "/" + "CaptainIcon" + "\0");
						}
						else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam1Role" + row_id + " SET " + 
									icon_path + "/" + "Keeper" + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll1$TeamAll1$RowA" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$CaptainIcon*ACTIVE SET 0 \0");
						}
					}
				} else {
					row_id = 0;
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName2" + " SET " + 
							match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName2" + " SET " + 
							"" + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll2$TeamNameGrp2$RowAnimation$TeamNameGrp$AwayTeamLogo$LogoMasked"
							+ "$TeamLogo1*TEXTURE*IMAGE SET " + logo_path + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll2$TeamNameGrp2$RowAnimation$TeamNameGrp$AwayTeamLogo$LogoMasked"
							+ "$TeamColour*TEXTURE*IMAGE SET " + team_color + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					
					TimeUnit.MILLISECONDS.sleep(2);
					for(Player as : match.getSetup().getAwaySquad()) {
						row_id = row_id + 1;
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTeam2Row" + row_id + " SET " + 
								"1" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeam2FirstName" + row_id + " SET " + 
								as.getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeam2LastName" + row_id + " SET " + 
								"" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Flag" + row_id + " SET " + 
								flag_path + as.getNationality() + "\0");
						if(as.getOverseasPlayer() == 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$InternationalIcon*ACTIVE SET 0 \0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$InternationalIcon*ACTIVE SET 1 \0");
						}
						
						if(as.getRole().equalsIgnoreCase("BATSMAN")) {
							if(as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
										icon_path + "/" + "Batsman" + "\0");
							}else if(as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
										icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(as.getRole().equalsIgnoreCase("BAT/KEEPER")) {
							if(as.getBattingStyle().equalsIgnoreCase("RHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
										icon_path + "/" + "Batsman" + "\0");
							}else if(as.getBattingStyle().equalsIgnoreCase("LHB")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
										icon_path + "/" + "Batsman_Lefthand" + "\0");
							}
						}else if(as.getRole().equalsIgnoreCase("BOWLER")) {
							if(as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
										icon_path + "/" + "FastBowler" + "\0");
							}else {
								switch(as.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
											icon_path + "/" + "FastBowler" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
											icon_path + "/" + "SpinBowlerIcon" + "\0");
									break;
								}
							}
						}else if(as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
							if(as.getBowlingStyle() == null) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
										icon_path + "/" + "FastBowlerAllrounder" + "\0");
							}else {
								switch(as.getBowlingStyle()) {
								case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
											icon_path + "/" + "FastBowlerAllrounder" + "\0");
									break;
								case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
											icon_path + "/" + "SpinBowlerAllrounder" + "\0");
									break;
								}
							}
						}
						
						if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTeam2Captain" + row_id + " SET " + 
									icon_path + "/" + "CaptainIcon" + "\0");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$CaptainIcon*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTeam2Captain" + row_id + " SET " + 
									icon_path + "/" + "CaptainIcon" + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
									icon_path + "/" + "Keeper" + "\0");
						}
						else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$CaptainIcon*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeam2Role" + row_id + " SET " + 
									icon_path + "/" + "Keeper" + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$TeamsAll$TeamDataData$TeamAll2$TeamAll2$RowB" + row_id + 
									"$RowAnimation$RowOmo$Highlight$TextAll$CaptainIcon*ACTIVE SET 0 \0");
						}
					}
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataIn 2.890 \0");
			TimeUnit.SECONDS.sleep(2);
		}
	}
	
	public Infobar populateInfobarIdent(Infobar infobar, boolean is_this_updating, String viz_scene,PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		if(is_this_updating == false) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName1" + " SET " + 
					match.getSetup().getHomeTeam().getTeamName1() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName2" + " SET " + 
					match.getSetup().getAwayTeam().getTeamName1() + "\0");
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo1" + " SET " + logo_path + 
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MatchIdent$DataAll$HomeLogoGrp$noname*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgIdentTeamLogo2" + " SET " + logo_path + 
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$MatchIdent$DataAll$AwayLogoGrp$noname*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
		}
		
		switch(infobar.getIdent_section().toUpperCase()) {
		case CricketUtil.TOSS:
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
						" ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
						" ELECTED TO " + match.getSetup().getTossWinningDecision().toUpperCase() + "\0");
			}
			break;
		case "VENUE":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
					match.getSetup().getVenueName().toUpperCase() + "\0");
	    	break;
	    case "TOURNAMENT":
	    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
	    			match.getSetup().getTournament() + "\0");
	    	break;
	    case "TARGET":
	    	for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
								"TARGET " + CricketFunctions.GetTargetData(match).getTargetRuns() + " (VJD)" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
								"TARGET " + CricketFunctions.GetTargetData(match).getTargetRuns() + " (DLS)" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
								"TARGET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
					}
				}
			}
	    	break;
	    case "RESULT":
	    	for(Inning inn : match.getMatch().getInning()) {
	    		if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
	    			if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											"MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											"MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
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
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											"MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											"MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
							}
						}
						else{
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
									inn.getBatting_team().getTeamName1().toUpperCase() 
									+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
									" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
						}
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												"MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												"MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
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
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												"MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												"MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
							}
							
							else{
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
										inn.getBatting_team().getTeamName1().toUpperCase() 
										+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
										" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");								
							}
						}
						else {
							if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												"MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												"MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
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
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												"MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												"MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
												match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
							}
							else{
								if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											inn.getBatting_team().getTeamName1().toUpperCase() 
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (VJD)" + "\0");
								}
								else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											inn.getBatting_team().getTeamName1().toUpperCase() 
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (DLS)" + "\0");	
								}
								else {
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
											inn.getBatting_team().getTeamName1().toUpperCase() 
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + "\0");
								}
							}
						}
					}
	    		}
	    	}
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
			infobar = populateVizInfobarTop(infobar, false, print_writer, match, broadcaster);
			infobar = populateVizInfobarRightTop(infobar, false, print_writer, match, broadcaster);
			infobar = populateVizInfobarRightBottom(infobar, false, print_writer, match, broadcaster);
			
			if(match.getMatch().getCurrent_speed() != null && !match.getMatch().getCurrent_speed().isEmpty()) {
				infobar.setLast_speed_value(match.getMatch().getCurrent_speed());
			}
		}
		return infobar;
	}
	public Infobar populateInfobarTeamScore(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster)
	{
		for(Inning inn : match.getMatch().getInning()) {
			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatTeamName" + " SET " + 
							inn.getBatting_team().getTeamName1() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallTeamName" + " SET " + 
							inn.getBowling_team().getTeamName1() + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatTeamLogo" + " SET " + logo_path + 
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1$LogoGrp$noname*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBallTeamLogo" + " SET " + logo_path + 
							inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section4_5_All$Sectio4_5$base$LogoGrp$noname*TEXTURE*IMAGE SET " + team_color +
							inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
				}
			    
				if(inn.getTotalWickets() >= 10) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + " SET " + 
							inn.getTotalRuns() + "\0");
				}else{
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + " SET " + 
							inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + "\0");
				}
			    
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamOvers" + " SET " + 
						CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
				
			    if(match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.DLS) || match.getSetup().getTargetType().equalsIgnoreCase(CricketUtil.VJD)) {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamOvers" + " SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " (" + match.getSetup().getTargetOvers() + ") " + "\0");
			    }else if(match.getSetup().getTargetOvers() != null && !match.getSetup().getTargetOvers().isEmpty()) {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamOvers" + " SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " (" + match.getSetup().getTargetOvers() + ") " + "\0");
			    }
			    else {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamOvers" + " SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
			    }
			    
			    if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && match.getSetup().getMaxOvers() == 1) {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1$DataAll$Section1$BattingTeamData$PowerPlay$Prompt-Bold*ACTIVE SET 0 \0");
			    }else {
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1$DataAll$Section1$BattingTeamData$PowerPlay$Prompt-Bold*GEOM*TEXT SET P \0");
			    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1$DataAll$Section1$BattingTeamData$PowerPlay$Prompt-Bold*ACTIVE SET 1 \0");
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
	public Infobar populateVizInfobarMiddle(Infobar infobar, boolean is_this_updating, PrintWriter print_writer,MatchAllData match, String broadcaster) throws InterruptedException
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
					
					if(infobar.getLast_batsmen() == null || infobar.getLast_batsmen().size() <= 0) {
						infobar.setLast_batsmen(current_batsmen);
					}
					populateCurrentBatsmen(infobar,print_writer, match, broadcaster,current_batsmen);
					
					if(is_this_updating == false) {
						processAnimation(print_writer, "Section3Out", "START", broadcaster);
						processAnimation(print_writer, "Batsman1In", "START", broadcaster);
						processAnimation(print_writer, "Batsman2In", "START", broadcaster);
					}
				}
			}
			infobar.setLast_middle_section(CricketUtil.BATSMAN);
			break;
		case "EQUATION":
			if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0 || match.getMatch().getInning().get(1).getTotalWickets() >= 10 || CricketFunctions.GetTargetData(match).getRemaningBall() == 0) {
//				processAnimation(print_writer, "Section4$EquationOut", "START", broadcaster);
////				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section4$Equation*ACTIVE SET 0" + "\0");	
//				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section4$FreeText$txt_Head*GEOM*TEXT SET " + 
//							CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL).toUpperCase() + "\0");
//				processAnimation(print_writer, "Section4$FreeTextIn", "START", broadcaster);
////				processAnimation(print_writer, "Section4$FreeTextIn", "SHOW 0.500", broadcaster);
//				infobar.setBottom_right_section("");
//				showWinner(infobar, print_writer, match);
			}else{
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section4$Equation*ACTIVE SET 1" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRun" + " SET " + 
						CricketFunctions.GetTargetData(match).getRemaningRuns() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedRunHead" + " SET " + 
						"RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\0");

				if(!match.getSetup().getTargetOvers().equalsIgnoreCase("")) {
					if(match.getSetup().getTargetOvers().contains(".")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
								((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1])-
										(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
						
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + 
								"BALL" + CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[0]) * 6) + Integer.valueOf(match.getSetup().getTargetOvers().split("\\.")[1])-
										(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + "\0");
						
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
								((Integer.valueOf(match.getSetup().getTargetOvers())*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + 
								"BALL" + CricketFunctions.Plural(((Integer.valueOf(match.getSetup().getTargetOvers())*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls()))).
								toUpperCase() + "\0");
					}
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBall" + " SET " + 
							((match.getSetup().getMaxOvers()*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls())) + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNeedBallHead" + " SET " + 
							"BALL" + CricketFunctions.Plural(((match.getSetup().getMaxOvers()*6)-(match.getMatch().getInning().get(1).getTotalOvers()*6+match.getMatch().getInning().get(1).getTotalBalls()))).toUpperCase() + "\0");
					
				}
			}
			infobar.setLast_middle_section("EQUATION");
			break;
		case "LAST_BOUNDARY":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallSinceLastBoundary" + " SET " + 
							CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber()) + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section3$BattingGrp$DataGrpAll$Section3_OtherInfo$BallsSinceLastBoundary$BallSinceLastBoundary$Prompt-Medium*GEOM*TEXT SET " + 
							"SINCE LAST \nBOUNDARY" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section3$BattingGrp$DataGrpAll$Section3_OtherInfo$BallsSinceLastBoundary$BallSinceLastBoundary$Balls$Prompt-Medium*GEOM*TEXT SET " + 
							"BALL" + CricketFunctions.Plural(Integer.valueOf(CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber()))).toUpperCase() + "\0");
				}
			}
			infobar.setLast_middle_section("LAST_BOUNDARY");
			break;
		case "PROJECTED":
			String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
		    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
		    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
	        }
		    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedHead1" + " SET " + 
		    		"@CRR (" + proj_score_rate[0] +")" + "\0");
		    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedValue1" + " SET " + 
		    		proj_score_rate[1] + "\0");
		    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedHead2" + " SET " + 
		    		"@" + proj_score_rate[2] + "\0");
		    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedValue2" + " SET " + 
		    		proj_score_rate[3] + "\0");
		    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedHead3" + " SET " + 
		    		"@" + proj_score_rate[4] + "\0");
		    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tProjectedValue3" + " SET " + 
		    		proj_score_rate[5] + "\0");
		    
			infobar.setLast_middle_section(CricketUtil.PROJECTED);
			break;
		case "BOUNDARIES":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFoursValue" + " SET " + 
							inn.getTotalFours() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixValue" + " SET " + 
							inn.getTotalSixes() + "\0");
				}
			}
			
			infobar.setLast_middle_section("BOUNDARIES");
			break;
		case "LAST_WICKET":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
					for(BattingCard bc : inn.getBattingCard()){
						if(inn.getFallsOfWickets() != null && !inn.getFallsOfWickets().isEmpty()) {
							if(inn.getFallsOfWickets().size() > 0){
								if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section2-3-4-5$Section3$BattingGrp$DataGrpAll$Section3_OtherInfo$LastWicket*ACTIVE SET 1 \0");
									
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketPlayerName" + " SET " + 
											bc.getPlayer().getTicker_name() + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketRuns" + " SET " + 
											bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastWicketBalls" + " SET " + 
											"(" + bc.getBalls() + ")" + "\0");
								}
							}
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section2-3-4-5$Section3$BattingGrp$DataGrpAll$Section3_OtherInfo$LastWicket*ACTIVE SET 0 \0");
						}
					}
				}
			}
			infobar.setLast_middle_section("LAST_WICKET");
			break;
		case "TOURNAMENT-NAME":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeTextSmallText" + " SET " + 
					match.getSetup().getTournament() + "\0");
			
			infobar.setLast_middle_section("TOURNAMENT-NAME");
			break;	
		}
		return infobar;
	}
	public Infobar populateCurrentBatsmen(Infobar infobar, PrintWriter print_writer, MatchAllData match, String broadcaster,List<BattingCard> current_batsmen) throws InterruptedException
	{
		for(Inning inn : match.getMatch().getInning()) {
			
			if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				
				if(current_batsmen != null && current_batsmen.size() >= 1) {
					if(infobar.getLast_batsmen() != null && infobar.getLast_batsmen().size() >= 1) {
						if(infobar.getLast_batsmen().get(0).getPlayerId() != current_batsmen.get(0).getPlayerId()) {
							processAnimation(print_writer, "Batsman1Out", "START", broadcaster);
							TimeUnit.MILLISECONDS.sleep(800);
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName1" + " SET " + 
									current_batsmen.get(0).getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore1" + " SET " + 
									current_batsmen.get(0).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall1" + " SET " + 
									current_batsmen.get(0).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatsmanFlag1" + " SET " + 
									flag_path + current_batsmen.get(0).getPlayer().getNationality() + "\0");
							
							
							processAnimation(print_writer, "Batsman1In", "START", broadcaster);
							processAnimation(print_writer, "Batsman1Highlight", "SHOW 0.160", broadcaster);
						}else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName1" + " SET " + 
									current_batsmen.get(0).getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore1" + " SET " + 
									current_batsmen.get(0).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall1" + " SET " + 
									current_batsmen.get(0).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatsmanFlag1" + " SET " + 
									flag_path + current_batsmen.get(0).getPlayer().getNationality() + "\0");
							
							if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT) 
									|| current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) { 
								processAnimation(print_writer, "Batsman1Dehighlight", "SHOW 0.260", broadcaster);
							}else if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
								processAnimation(print_writer, "Batsman1Dehighlight", "SHOW 0.0", broadcaster);
							}
						}
						
						if(infobar.getLast_batsmen().get(1).getPlayerId() != current_batsmen.get(1).getPlayerId()) {
							processAnimation(print_writer, "Batsman2Out", "START", broadcaster);
							TimeUnit.MILLISECONDS.sleep(800);
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName2" + " SET " + 
									current_batsmen.get(1).getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore2" + " SET " + 
									current_batsmen.get(1).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall2" + " SET " + 
									current_batsmen.get(1).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatsmanFlag2" + " SET " + 
									flag_path + current_batsmen.get(1).getPlayer().getNationality() + "\0");
							
							processAnimation(print_writer, "Batsman2In", "START", broadcaster);
							processAnimation(print_writer, "Batsman2Highlight", "SHOW 0.160", broadcaster);
						}else {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanName2" + " SET " + 
									current_batsmen.get(1).getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanScore2" + " SET " + 
									current_batsmen.get(1).getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatsmanBall2" + " SET " + 
									current_batsmen.get(1).getBalls() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBatsmanFlag2" + " SET " + 
									flag_path + current_batsmen.get(1).getPlayer().getNationality() + "\0");
							
							if(current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT) 
									|| current_batsmen.get(1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) { 
								processAnimation(print_writer, "Batsman2Dehighlight", "SHOW 0.260", broadcaster);
							}else if(current_batsmen.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
								processAnimation(print_writer, "Batsman2Dehighlight", "SHOW 0.0", broadcaster);
							}
						}
					}
					if(current_batsmen.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section3$BattingGrp$DataGrpAll$MainBatsmanGrp$OnStrike*FUNCTION*Omo*vis_con SET 0 \0");
						}
					}
					if(current_batsmen.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						if(current_batsmen.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section3$BattingGrp$DataGrpAll$MainBatsmanGrp$OnStrike*FUNCTION*Omo*vis_con SET 1 \0");
						}	
					}
				}
			}
		}
			
		infobar.setLast_batsmen(current_batsmen);
		return infobar;
	}
	public Infobar populateVizInfobarRight(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match,List<MatchAllData> tourn_matches, String broadcaster) throws InterruptedException 
	{
		switch(infobar.getBottom_right_section().toUpperCase()) {
		
		case CricketUtil.DOT:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDotBallHead" + " SET " + 
							"DOTS THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tDotBallCounter" + " SET " + 
							CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, inn.getInningNumber(), 0, ",", match.getEventFile().getEvents()).split(",")[0] + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.DOT);	
			break;
		case CricketUtil.FOUR:
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterHead" + " SET " + 
							"FOURS THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterCounter" + " SET " + 
							inn.getTotalFours() + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.FOUR);
			break;
		case CricketUtil.SIX:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterHead" + " SET " + 
							"SIXES THIS INNINGS" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterCounter" + " SET " + 
							inn.getTotalSixes() + "\0");
				}
			}
			infobar.setLast_bottom_right_section(CricketUtil.SIX);
			break;
		case "TOURNAMENT_FOUR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterHead" + " SET " + 
					"FOURS THIS TOURNAMENT" + "\0");
			
//			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFourCounterCounter" + " SET " + 
//					CricketFunctions.gettournamentFoursAndSixes(tourn_matches, match).split(",")[0] + "\0");
			
			infobar.setLast_bottom_right_section("TOURNAMENT_FOUR");
			break;
		case "TOURNAMENT_SIX":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterHead" + " SET " + 
					"SIXES THIS TOURNAMENT" + "\0");
//			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixCounterCounter" + " SET " + 
//					CricketFunctions.gettournamentFoursAndSixes(tourn_matches, match).split(",")[1] + "\0");
			
			infobar.setLast_bottom_right_section("TOURNAMENT_SIX");
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
		case "TARGET":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section4_5_All$Section4_N_5$Target02$Target$TargetHead*GEOM*TEXT SET " + 
					"" + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTargetScore" + " SET " + 
							CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
				}
			}
			infobar.setLast_bottom_right_section("TARGET");
			break;	
		}		
			
		return infobar;
	}
	public Infobar populateVizInfobarRightTop(Infobar infobar,boolean is_this_updating, PrintWriter print_writer, 
			MatchAllData match, String broadcaster) throws InterruptedException
	{
		
		switch(infobar.getBottom_right_top_section().toUpperCase()) {
		case CricketUtil.BOWLER:
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") 
								|| boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
								processAnimation(print_writer, "Section4Out", "START", broadcaster); // bowler out
								processAnimation(print_writer, "Section5Out", "START", broadcaster); // this over out
								TimeUnit.SECONDS.sleep(1);
							}
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerName" + " SET " + 
									boc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerFigure" + " SET " + 
									boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerOvers" + " SET " + 
									CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgBowlerFlag" + " SET " + 
									flag_path + boc.getPlayer().getNationality() + "\0");
							
							if(infobar.getLast_bowler() == null || infobar.getLast_bowler().getPlayerId() != boc.getPlayerId()) {
								processAnimation(print_writer, "Section4In", "START", broadcaster);
								if(infobar.getLast_bottom_right_bottom_section() != null  && 
										!infobar.getLast_bottom_right_bottom_section().trim().isEmpty()) {
									if(infobar.getLast_bottom_right_bottom_section().equalsIgnoreCase(CricketUtil.OVER)) {
										processAnimation(print_writer, "ALL_SECTION$Section5$ThisOverIn", "SHOW 0.320", broadcaster);
										processAnimation(print_writer, "ALL_SECTION$Section5$BowlingEndIn", "SHOW 0.0", broadcaster);
									}
								}
								processAnimation(print_writer, "Section5In", "START", broadcaster);
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
	public Infobar populateVizInfobarRightBottom(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, MatchAllData match, String broadcaster)
	{
		switch(infobar.getBottom_right_bottom_section().toUpperCase()) {
		case CricketUtil.OVER:
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){	
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)) {
							infobar.setPlayer_id(boc.getPlayerId());
						}
					}
					String[] this_over = CricketFunctions.getEventsText(CricketUtil.OVER,infobar.getPlayer_id(),",", match.getEventFile().getEvents(),0).split(",");
					
					if(Integer.valueOf(CricketFunctions.processThisOverRunsCount(infobar.getPlayer_id(),match.getEventFile().getEvents())) > 0 || inn.getTotalBalls() > 0) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER*FUNCTION*Omo*vis_con SET " + 
											this_over.length + " \0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER*FUNCTION*Omo*vis_con SET " + 
								0 + " \0");
						}
						
					for(int i=0;i < this_over.length;i++) {
						if(this_over.length <= 9) {
							switch (this_over[i]) {
							case CricketUtil.DOT: case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE:
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" + (i+1) + 
											"*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tThisOverRun" + (i+1) + " SET " + 
										this_over[i] + "\0");
								break;
							case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.LOG_WICKET:
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" + (i+1) + 
										"*FUNCTION*Omo*vis_con SET 2 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tThisOverRun" + (i+1) + " SET " + 
										this_over[i] + "\0");
								break;	

							default:
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section4_5_All$Sectio4_5$Section5$ThisOver$ThisOver$THISOVER$Ball" + (i+1) + 
										"*FUNCTION*Omo*vis_con SET 4 \0");
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tThisOverRun" + (i+1) + " SET " + 
										this_over[i] + "\0");
								break;
							}
						}else {
							processAnimation(print_writer, "ALL_SECTION$Section5$ThisOverIn", "SHOW 0.0", broadcaster);
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlingEnd" + " SET " + 
									"THIS OVER : " + CricketFunctions.processThisOverRunsCount(infobar.getPlayer_id(),match.getEventFile().getEvents()) + "\0");
							processAnimation(print_writer, "ALL_SECTION$Section5$BowlingEndIn", "SHOW 0.360", broadcaster);
						}
					}
				}
			}
			infobar.setLast_bottom_right_bottom_section(CricketUtil.OVER);
			break;
		case "ECONOMY":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section2-3-4-5$Section4_5_All$Sectio4_5$Section5$Economy$EcoGrp$Prompt-Regular*GEOM*TEXT SET " + 
							"ECONOMY : " + "\0");
					
					for(BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
							if(boc.getEconomyRate() == null) {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerEconomy" + " SET " + 
										"-" + "\0");
							}else {
								print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBowlerEconomy" + " SET " + 
										boc.getEconomyRate() + "\0");
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
										match.getSetup().getGround().getFirst_bowling_end().toUpperCase() + " END" + "\0");
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
		switch(infobar.getTop_section().toUpperCase()) {
		case "CRR":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					if(inn.getRunRate() == null) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCurrentRunRate" + " SET " + 
								"-" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCurrentRunRate" + " SET " + 
								inn.getRunRate() + "\0");
					}
					
				}
			}
			break;
		case "RRR":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRequiredRunRateText" + " SET " + 
					"REQ RR" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRequiredRunRate" + " SET " + 
					CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match).trim() + "\0");
			break;
		case "NEXT_TO_BAT":
			int first_batsman=0,second_batsman=0,next_value=0;
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					for(int i=0;i<=inn.getBattingCard().size()-1;i++) {
						if(inn.getBattingCard().get(i).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							if(inn.getBattingCard().get(i).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
								first_batsman = inn.getBattingCard().get(i).getBatterPosition();
							}else {
								second_batsman = inn.getBattingCard().get(i).getBatterPosition();
							}
						}
					}
					if(first_batsman > second_batsman) {
						next_value = first_batsman + 1;
					}else if(first_batsman < second_batsman){
						next_value = second_batsman + 1;
					}
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section2$StatGRP$NextIn$Datagrp$txt_Data1*GEOM*TEXT SET " + 
							inn.getBattingCard().get(next_value-1).getPlayer().getTicker_name() + "\0");
				}
			}
			break;
		case CricketUtil.TOSS:
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1$Bands$Section2Base$Section2_Small$Toss$TossGrp$TOSS_HEAD*GEOM*TEXT SET " + 
					"TOSS" + "\0");
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTossTeam" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName3() + "\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTossTeam" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName3() + "\0");
			}
			break;
		case "TARGET":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTarget" + " SET " + 
								CricketFunctions.GetTargetData(match).getTargetRuns() + " (VJD)" + "\0");
					}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTarget" + " SET " + 
								CricketFunctions.GetTargetData(match).getTargetRuns() + " (DLS)" + "\0");
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTarget" + " SET " + 
								CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
					}
				}
			}
			break;
		case "PARTNERSHIP":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartnershipSmallRuns" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPartnershipSmallBalls" + " SET " + 
							" (" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")" + "\0");
				}
			}
			break;
		case "SUPER_OVER":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1$Bands$Section2Base$Section2_Small$Toss$TossGrp$TOSS_HEAD*GEOM*TEXT SET " + 
					"SUPER" + "\0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTossTeam" + " SET " + 
					"OVER" + "\0");
			break;	
		}
			
		infobar.setLast_top_section(infobar.getTop_section());
		return infobar;
	}
	public Infobar populateInfobarFreeText(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, InfobarStats ibs, MatchAllData match, String broadcaster)
	{	

		if(ibs.getText1() != null && ibs.getText2() != null) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section4$FreeText$txt_Head*GEOM*TEXT SET " + ibs.getText1() + "-" + ibs.getText2() + "\0");
		}else if(ibs.getText1() != null) {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section4$FreeText$txt_Head*GEOM*TEXT SET " + ibs.getText1() + "\0");
		}else {
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section4$FreeText$txt_Head*GEOM*TEXT SET " + ibs.getText2() + "\0");
		}
		
		infobar.setLast_bottom_right_section("STATISTICS");
		return infobar;
	}
	public void populateInfobarDirector(PrintWriter print_writer,String Dir_value,String session_selected_broadcaster) throws InterruptedException {
		
		switch (Dir_value.toUpperCase()) {
		case "FOURS":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FourIn START \0");
			which_director_on_screen = "FOUR";
			break;

		case "SIXES":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SixIn START \0");
			which_director_on_screen = "SIX";
			break;
			
		case "WICKET":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*WicketIn START \0");
			which_director_on_screen = "WICKET";
			break;	

		case "FREE-HIT":
//			if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
//				switch(infobar.getLast_top_section().toUpperCase()) {
//				case CricketUtil.TOSS:
//					processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
//					break;
//				case "CRR":
//					processAnimation(print_writer, "Section2$RunRateOut", "START", broadcaster);
//					break;
//				case "RRR":
//					processAnimation(print_writer, "Section2$ReqRunRateOut", "START", broadcaster);
//					break;
//				case "NEXT_TO_BAT":
//					processAnimation(print_writer, "Section2$NextInOut", "START", broadcaster);
//					break;
//				case "TARGET":
//					processAnimation(print_writer, "Section2$TargetOut", "START", broadcaster);
//					break;
//				case "PARTNERSHIP":
//					processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
//					break;
//				
//				}
//				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitIn START \0");	
//			}
//			infobar.setDirecter_section("FREE-HIT");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*FreeHitIn START \0");
			which_director_on_screen = "FREE_HIT";
			break;
		}
	}
	public void populateInfobarSponsor(PrintWriter print_writer,String Sponsor_value,String session_selected_broadcaster) throws InterruptedException {
		
		if(infobar.getLast_top_section() != null && !infobar.getLast_top_section().trim().isEmpty()) {
			switch(infobar.getLast_top_section().toUpperCase()) {
			case CricketUtil.TOSS:
				processAnimation(print_writer, "Section2$TossOut", "START", broadcaster);
				break;
			case "CRR":
				processAnimation(print_writer, "Section2$RunRateOut", "START", broadcaster);
				break;
			case "RRR":
				processAnimation(print_writer, "Section2$ReqRunRateOut", "START", broadcaster);
				break;
			case "NEXT_TO_BAT":
				processAnimation(print_writer, "Section2$NextInOut", "START", broadcaster);
				break;
			case "TARGET":
				processAnimation(print_writer, "Section2$TargetOut", "START", broadcaster);
				break;
			case "PARTNERSHIP":
				processAnimation(print_writer, "Section2$PartnershipOut", "START", broadcaster);
				break;
			
			}
		}
		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section2$StatGRP$noname$Sponsors$img_Spnsor*TEXTURE*IMAGE SET " + "IMAGE*/Default/Essentials/Spnsor/" + Sponsor_value +" \0");
		print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*SponsorsIn START \0");
	}
	public void populateInfobarPowerPlay(PrintWriter print_writer,String Dir_value,String session_selected_broadcaster) {
		
		switch (Dir_value.toUpperCase()) {
		case "POWERPLAY":
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$Section1$DataAll$Section1$BattingTeamData$PowerPlay$Prompt-Bold*GEOM*TEXT SET P \0");
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PowerPlayIn START \0");
			break;
		}
	}
	public Infobar populateInfobarLastxOver(Infobar infobar, boolean is_this_updating, PrintWriter print_writer, int value,MatchAllData match, String broadcaster) {
		
		//print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section4$FreeText$txt_Head*GEOM*TEXT SET " + ibs.getText2() + "\0");
		
		System.out.println(CricketFunctions.getlastthirtyballsdata(match, broadcaster, match.getEventFile().getEvents(), value));
		
		infobar.setLast_bottom_right_section(infobar.getBottom_right_section());
		return infobar;
	}
	public Infobar populateSection5(Infobar infobar,boolean is_this_updating,PrintWriter print_writer,int Line_value,String over,MatchAllData match,String broadcaster) throws IOException {
		
		switch(infobar.getFull_section().toUpperCase()) {
		case "EXTRAS":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					if(inn.getTotalPenalties() == 0) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeText" + " SET " + 
			    				"EXTRAS - " + inn.getTotalExtras() + " (WD-" + inn.getTotalWides() + ",NB-" + inn.getTotalNoBalls() + ",LB-" + inn.getTotalLegByes() + ",B-" + inn.getTotalByes() + ")" + "\0");
						
					}else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeText" + " SET " + 
			    				"EXTRAS - " + inn.getTotalExtras() + " (WD-" + inn.getTotalWides() + ",NB-" + inn.getTotalNoBalls() + ",LB-" + inn.getTotalLegByes() + ",B-" + inn.getTotalByes() + 
								",P-" + inn.getTotalPenalties() + ")" + "\0");
					}
				}
			}
			break;
		case "BONUS":
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeText" + " SET " + 
			    				"BONUS POINT : " + inn.getBatting_team().getTeamName1() + " NEED " + CricketFunctions.compareData(match, 1, match.getEventFile().getEvents(), Integer.valueOf(over)) + " RUNS IN " + over + " OVERS" + "\0");
					}
				}
			}
			break;
		case "FREETEXT":
			String text_to_return = "";
			int lineIndex1 = 1;
		    boolean found1 = false;
			BufferedReader br = new BufferedReader(new FileReader(CricketUtil.CRICKET_DIRECTORY + CricketUtil.INFOBAR_FREE_TXT));
		
		    while( (text_to_return = br.readLine()) != null) {
		        if(lineIndex1 == Line_value) {
		        	if(Line_value == 1) {
		        		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeText" + " SET " + 
		        				text_to_return + "\0");
					}else if(Line_value == 2) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFreeText" + " SET " + 
		        				text_to_return + "\0");
					}
		             found1 = true;
		             break;
		        }
		        lineIndex1++;
		    }
		    if(!found1) {
		    	//System.out.println("Line Not There");
		    }
			
			break;
		case "TIMELINE":
			String this_ball_data="";
			int ball_count=0;
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					
//					if(is_this_updating == false) {
//						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section5$TimeLine$ThisOver$ThisOverAll$BallGrp23*ACTIVE SET 0" + "\0");
//						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section5$TimeLine$ThisOver$ThisOverAll$BallGrp24*ACTIVE SET 0" + "\0");
//						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$All$Section5$TimeLine$ThisOver$ThisOverAll$BallGrp25*ACTIVE SET 0" + "\0");
//					}
					
					if(((inn.getTotalOvers()*6) + inn.getTotalBalls()) > 33) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$TopSection$TimeLine$TimeLine$THISOVER*FUNCTION*Omo*vis_con SET " + "33" + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$TopSection$TimeLine$TimeLine$THISOVER*FUNCTION*Omo*vis_con SET " + 
									((inn.getTotalOvers()*6) + inn.getTotalBalls()) + " \0");
					}
						
					if ((match.getEventFile().getEvents() != null) && (match.getEventFile().getEvents().size() > 0)) {
						  for (int i=match.getEventFile().getEvents().size() - 1; i>=0; i--)
						  {  
							
							switch(match.getEventFile().getEvents().get(i).getEventType()) {
							case CricketUtil.CHANGE_BOWLER: case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
							case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: 
							case CricketUtil.PENALTY: case CricketUtil.LOG_ANY_BALL: case CricketUtil.LOG_WICKET:
								ball_count = ball_count + 1;
								switch (match.getEventFile().getEvents().get(i).getEventType())
							    {
							    case CricketUtil.CHANGE_BOWLER:
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeLineBall" + ball_count + " SET " + 
							    			"0" + "\0");
//							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$TopSection$TimeLine$TimeLine$THISOVER$Ball" 
//											+ ball_count + "*FUNCTION*Omo*vis_con SET 0 \0");
									break;
							    case CricketUtil.DOT: case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE :
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeLineBall" + ball_count + " SET " + 
							    			"1" + "\0");
//							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$TopSection$TimeLine$TimeLine$THISOVER$Ball" 
//											+ ball_count + "*FUNCTION*Omo*vis_con SET 1 \0");
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimeLineRun" + ball_count + " SET " + 
							    			match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
							    	break;
							    case CricketUtil.FOUR: case CricketUtil.SIX:
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeLineBall" + ball_count + " SET " + 
							    			"2" + "\0");
//							    	print_writer.println("-1 RRENDERER*FRONT_LAYER*TREE*$Main$AllSection$TopSection$TimeLine$TimeLine$THISOVER$Ball" 
//											+ ball_count + "*FUNCTION*Omo*vis_con SET 2 \0");
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimeLineRun" + ball_count + " SET " + 
							    			match.getEventFile().getEvents().get(i).getEventRuns() + "\0");
									break;
							    case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY: 
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeLineBall" + ball_count + " SET " + 
							    			"4" + "\0");
//							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$TopSection$TimeLine$TimeLine$THISOVER$Ball" 
//											+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
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
							    	
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimeLineRun" + ball_count + " SET " + 
							    			(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + 
						    				this_ball_data.toUpperCase() + "\0");
									break;
							    case CricketUtil.LOG_WICKET:
							    	if(match.getEventFile().getEvents().get(i).getEventHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
							    		ball_count = ball_count - 1;
							    		break;
							    	}else {
//							    		ball_count = ball_count + 1;
							    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeLineBall" + ball_count + " SET " + 
								    			"3" + "\0");
//							    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$TopSection$TimeLine$TimeLine$THISOVER$Ball" 
//												+ ball_count + "*FUNCTION*Omo*vis_con SET 3 \0");
								    	
								    	if (match.getEventFile().getEvents().get(i).getEventRuns() > 0) {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimeLineRun" + ball_count + " SET " + 
								    				String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns()) + "+W" + "\0");
								    	} else {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimeLineRun" + ball_count + " SET " + 
								    				"W" + "\0");
								      }
							    	}
							      break;
							    case CricketUtil.LOG_ANY_BALL:
							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vTimeLineBall" + ball_count + " SET " + 
							    			"4" + "\0");
//							    	print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main$AllSection$TopSection$TimeLine$TimeLine$THISOVER$Ball" 
//											+ ball_count + "*FUNCTION*Omo*vis_con SET 4 \0");
							    	if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.PENALTY)) {
							    		this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "Pn";
							    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimeLineRun" + ball_count + " SET " + 
							    				this_ball_data + "\0");
							    	}else {
							    		if(match.getEventFile().getEvents().get(i).getEventExtra() != null) {
								    		if(match.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.WIDE)){
								    			if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE)) {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns()+
									    					match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "WD";
								    			}else {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns()) + "WD";
								    			}
								    		}
								    		else if(match.getEventFile().getEvents().get(i).getEventExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
								    			if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.NO_BALL) && match.getEventFile().getEvents().get(i).getEventWasABoundary() != null 
								    					&& match.getEventFile().getEvents().get(i).getEventWasABoundary().equalsIgnoreCase(CricketUtil.YES)) {
								    				
								    				this_ball_data = "NB + " + String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns());
								    				
								    			}else {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns() + match.getEventFile().getEvents().get(i).getEventExtraRuns()+
									    					match.getEventFile().getEvents().get(i).getEventSubExtraRuns()) + "NB";
								    			}
								    			
							    			}else {
							    				if(match.getEventFile().getEvents().get(i).getEventRuns()>0) {
							    					this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventRuns());
							    				}
							    			}
								    	}
							    		
							    		if(match.getEventFile().getEvents().get(i).getEventSubExtra() != null && match.getEventFile().getEvents().get(i).getEventSubExtraRuns()>0) {
								    		if(!match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.WIDE) && 
								    				!match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.NO_BALL)) {
								    			if(this_ball_data.isEmpty()) {
								    				this_ball_data = String.valueOf(match.getEventFile().getEvents().get(i).getEventSubExtraRuns());
								    			}else {
								    				this_ball_data = this_ball_data + "+" + match.getEventFile().getEvents().get(i).getEventSubExtraRuns();
								    			}
								    			if(match.getEventFile().getEvents().get(i).getEventSubExtra().equalsIgnoreCase(CricketUtil.LEG_BYE)) {
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
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimeLineRun" + ball_count + " SET " + 
								    				this_ball_data  + "\0");
								    	}else {
								    		print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTimeLineRun" + ball_count + " SET " + 
								    				this_ball_data + "\0");
								    	}
							    	}	
							    }
								break;
							}
								
						    if(ball_count >= 33) {
						    	break;
						    }
						  }
						}
				}
			}
			break;
		}
		infobar.setLast_full_section(infobar.getFull_section().toUpperCase());
		return infobar;
	}
	
	public void populateMatchPromo(PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchPromo's inning is null";
		} else {
			
			String match_name="";
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + 
					match.getSetup().getTournament() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "FROM " + match.getSetup().getVenueName().toUpperCase() + "\0");
			
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$Worm$TeamsAll$TeamDataData$logos$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET " + logo_path + 
							TM.getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo1" + " SET " + 
							logo_path + TM.getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$Worm$TeamsAll$noname$TeamDataData$logos$HomeLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
							TM.getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + " SET " + 
							TM.getTeamName1().toUpperCase() + "\0");
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$Worm$TeamsAll$noname$TeamDataData$logos$AwayLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
							TM.getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$Worm$TeamsAll$TeamDataData$logos$AwayLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET " + logo_path + 
							TM.getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo2" + " SET " + 
							logo_path + TM.getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + " SET " + 
							TM.getTeamName1().toUpperCase() + "\0");
				}
			}
			
			if(match_number < 10) {
				match_name = "MATCH " + match_number;
			}else {
				match_name = fix.get(match_number - 1).getMatchfilename().toUpperCase();
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + match_name + "\0");
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			if(fix.get(match_number - 1).getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + 
						"TOMORROW" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + 
						"UP NEXT" + " - " + fix.get(match_number - 1).getGmtTime() + "\0");
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.jpg In 3.543 In$DataIn 2.850 \0");
			TimeUnit.SECONDS.sleep(2);
				
		}
	}
	public void populateMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + 
					"" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + 
					match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + 
					match.getSetup().getTournament() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + " SET " + 
					match.getSetup().getHomeTeam().getTeamName1() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + " SET " + 
					match.getSetup().getAwayTeam().getTeamName1() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo1" + " SET " + 
					logo_path + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$Worm$TeamsAll$noname$TeamDataData$logos$HomeLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo2" + " SET " + 
					logo_path + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$Worm$TeamsAll$noname$TeamDataData$logos$AwayLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$Worm$TeamsAll$TeamDataData$logos$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET " + logo_path + 
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$BG_ALL$All$Worm$TeamsAll$TeamDataData$logos$AwayLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET " + logo_path + 
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
					"LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataIn 2.850 \0");
				
		}
		
	}
	public void populateLTMatchId(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					"fair_break" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + match.getSetup().getMatchIdent() + "\0");
			
			if(match.getMatch().getMatchResult() == null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						"LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.314\0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populateLtMatchPromo(PrintWriter print_writer,String viz_scene, int match_number ,List<Team> team,List<Fixture> fix,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			
			String match_name="";
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					"fair_break" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + " " + "\0");
			
			for(Team TM : team) {
				if(fix.get(match_number - 1).getHometeamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path + TM.getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color + TM.getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
					
				}
				if(fix.get(match_number - 1).getAwayteamid() == TM.getTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path + TM.getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color + TM.getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + " SET " + TM.getTeamName1().toUpperCase() + "\0");
					
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
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "FROM " + match.getSetup().getVenueName().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "TOMORROW - " + match_name  + "\0");
				
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "FROM " + match.getSetup().getVenueName().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "UP NEXT - " + match_name + "\0");
			
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.314\0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populatePlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,List<Player> plyr,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + 
					match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + 
					match.getSetup().getTournament() + "\0");
			
			if(TeamId == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				
				for(int i=1;i<=match.getSetup().getHomeSquad().size();i++) {
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + i + " SET " + 
								photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + i + " SET " + 
								"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + i + " SET " + 
							match.getSetup().getHomeSquad().get(i-1).getFull_name() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + i + " SET " + 
							"" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamFlag" + i + " SET " + 
							flag_path + "/" + match.getSetup().getHomeSquad().get(i-1).getNationality() + "\0");
					
					if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						if(match.getSetup().getHomeSquad().get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman" + "\0");
							
						}else if(match.getSetup().getHomeSquad().get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					}else if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase("BAT/KEEPER")) {
						if(match.getSetup().getHomeSquad().get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman" + "\0");
							
						}else if(match.getSetup().getHomeSquad().get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					}else if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "FastBowler" + "\0");
						}else {
							switch(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "FastBowler" + "\0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "SpinBowlerIcon" + "\0");
								break;
							}
						}
					}else if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "FastBowlerAllrounder" + "\0");
						}else {
							switch(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "FastBowlerAllrounder" + "\0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "SpinBowlerAllrounder" + "\0");
								break;
							}
						}
					}
					
					
					if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$LineUpData$TeamAll1$Row" + i + 
								"$RowAnimation$TextAll$CaptainIcon*ACTIVE SET "+ "1" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain" + i + " SET " + 
								icon_path + "/" + "CaptainIcon" + "\0");
					}
					else if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$LineUpData$TeamAll1$Row" + i + 
								"$RowAnimation$TextAll$CaptainIcon*ACTIVE SET "+ "1" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
								icon_path + "/" + "Keeper" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain" + i + " SET " + 
								icon_path + "/" + "CaptainIcon" + "\0");
					}
					else if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$LineUpData$TeamAll1$Row" + i + 
								"$RowAnimation$TextAll$CaptainIcon*ACTIVE SET "+ "0" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
								icon_path + "/" + "Keeper" + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$LineUpData$TeamAll1$Row" + i + 
								"$RowAnimation$TextAll$CaptainIcon*ACTIVE SET "+ "0" + " \0");
					}
				}
			}else {
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
				
				
				for(int i=1;i<=match.getSetup().getAwaySquad().size();i++) {
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + i + " SET " + 
								photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + i + " SET " + 
								"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + i + " SET " + 
							match.getSetup().getAwaySquad().get(i-1).getFull_name() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + i + " SET " + 
							"" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamFlag" + i + " SET " + 
							flag_path + "/" + match.getSetup().getAwaySquad().get(i-1).getNationality() + "\0");
					
					if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman" + "\0");
							
						}else if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					}else if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase("BAT/KEEPER")) {
						if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman" + "\0");
							
						}else if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					}else if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "FastBowler" + "\0");
						}else {
							switch(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "FastBowler" + "\0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "SpinBowlerIcon" + "\0");
								break;
							}
						}
					}else if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "FastBowlerAllrounder" + "\0");
						}else {
							switch(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "FastBowlerAllrounder" + "\0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "SpinBowlerAllrounder" + "\0");
								break;
							}
						}
					}
					
					if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$LineUpData$TeamAll1$Row" + i + 
								"$RowAnimation$TextAll$CaptainIcon*ACTIVE SET "+ "1" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain" + i + " SET " + 
								icon_path + "/" + "CaptainIcon" + "\0");
					}
					else if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$LineUpData$TeamAll1$Row" + i + 
								"$RowAnimation$TextAll$CaptainIcon*ACTIVE SET "+ "1" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain" + i + " SET " + 
								icon_path + "/" + "CaptainIcon" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
								icon_path + "/" + "Keeper" + "\0");
					}
					else if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$LineUpData$TeamAll1$Row" + i + 
								"$RowAnimation$TextAll$CaptainIcon*ACTIVE SET "+ "0" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
								icon_path + "/" + "Keeper" + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$LineUpData$TeamAll1$Row" + i + 
								"$RowAnimation$TextAll$CaptainIcon*ACTIVE SET "+ "0" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + i + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "0" + " \0");
					}
				}
			}
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 DataIn 2.440 \0");
//		Preview(print_writer, viz_scene, which_graphic_on_screen, "TEAMLINEUP");
	}
	
	public void populateLtPlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,String Type,List<Player> plyr,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			int row_id = 0;
			switch(Type.toUpperCase()) {
			case "ROLE":
				if(TeamId == match.getSetup().getHomeTeamId()) {
					
					for(int i=1;i<=match.getSetup().getHomeSquad().size();i++) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET "+ logo_path + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET "+ team_color + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$noname$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + i + " SET " + 
								match.getSetup().getHomeSquad().get(i-1).getTicker_name() + "\0");
						if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + i + " SET " + 
									"WICKETKEEPER" + "\0");
						}else if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + i + " SET " + 
									"WICKETKEEPER" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + i + " SET " + 
									match.getSetup().getHomeSquad().get(i-1).getRole().toUpperCase() + "\0");
						}
						
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + i + " SET " + 
									photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + i + " SET " + 
									"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
					}
				}else {
					
					
					for(int i=1;i<=match.getSetup().getAwaySquad().size();i++) {
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET "+ logo_path + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET "+ team_color + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$noname$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + i + " SET " + 
									photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + i + " SET " + 
									"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + i + " SET " + 
								match.getSetup().getAwaySquad().get(i-1).getTicker_name() + "\0");
						
						if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + i + " SET " + 
									"WICKETKEEPER" + "\0");
						}else if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + i + " SET " + 
									"WICKETKEEPER" + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + i + " SET " + 
									match.getSetup().getAwaySquad().get(i-1).getRole().toUpperCase() + "\0");
						}
					}
				}
				break;
			case "BATTINGCARD":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getBattingTeamId() == TeamId) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET "+ team_color + 
								inn.getBatting_team().getTeamName3().toLowerCase() + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET "+ logo_path + 
								inn.getBatting_team().getTeamName3().toLowerCase() + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All_Grp$PositionY$noname$TeamColour*TEXTURE*IMAGE SET " + team_color +
								inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
						Collections.sort(inn.getBattingCard());
						for(BattingCard bc : inn.getBattingCard()) {
							row_id = row_id + 1;
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + 
											photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + plyr.get(bc.getPlayerId()-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + plyr.get(bc.getPlayerId()-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + 
											"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + plyr.get(bc.getPlayerId()-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_id + " SET " + 
										plyr.get(bc.getPlayerId()-1).getTicker_name() + "\0");
								
								if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.START)) {
									for(int b=1;b<=inn.getBattingCard().size();b++) {
										if(inn.getBattingCard().get(b-1).getPlayerId() == bc.getPlayerId()) {
											if(inn.getBattingCard().get(b-1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + 
														"IN AT " + b + "\0");
											}
										}
									}
								}else if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + 
											"DNB" + "\0");
								}
								
							}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT) || bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + 
											photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + plyr.get(bc.getPlayerId()-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + plyr.get(bc.getPlayerId()-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage" + row_id + " SET " + 
											"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + plyr.get(bc.getPlayerId()-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_id + " SET " + 
										plyr.get(bc.getPlayerId()-1).getTicker_name() + "\0");
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + 
											bc.getRuns() + "*" + " (" + bc.getBalls() + ")" + "\0");
								}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStrikeRate" + row_id + " SET " + 
											bc.getRuns() + " (" + bc.getBalls() + ")" + "\0");
								}
							}
						}
					}
				}
				break;
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.714 DataIn 2.440 \0");
//		Preview(print_writer, viz_scene, which_graphic_on_screen, "TEAMLINEUP");
	}
	
	public void populatePlayingXIPhotos(PrintWriter print_writer,String viz_scene, int TeamId,List<Player> plyr,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + 
					match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + 
					match.getSetup().getTournament() + "\0");
			
			if(TeamId == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				
				for(int i=1;i<=match.getSetup().getHomeSquad().size();i++) {
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + i + " SET " + 
								photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + i + " SET " + 
								"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + i + " SET " + 
							match.getSetup().getHomeSquad().get(i-1).getFull_name() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + i + " SET " + 
							"" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamFlag" + i + " SET " + 
							flag_path + "/" + match.getSetup().getHomeSquad().get(i-1).getNationality() + "\0");
					
					if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						if(match.getSetup().getHomeSquad().get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman" + "\0");
							
						}else if(match.getSetup().getHomeSquad().get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					}else if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "FastBowler" + "\0");
						}else {
							switch(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "FastBowler" + "\0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "SpinBowlerIcon" + "\0");
								break;
							}
						}
					}else if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "FastBowlerAllrounder" + "\0");
						}else {
							switch(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "FastBowlerAllrounder" + "\0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "SpinBowlerAllrounder" + "\0");
								break;
							}
						}
					}
					
					if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + i + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "1" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain" + i + " SET " + 
								icon_path + "/" + "CaptainIcon" + "\0");
					}
					else if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
								icon_path + "/" + "Keeper" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain" + i + " SET " + 
								icon_path + "/" + "CaptainIcon" + "\0");
					}
					else if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
								icon_path + "/" + "Keeper" + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + i + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "0" + " \0");
					}
				}
			}else {
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
				
				
				for(int i=1;i<=match.getSetup().getAwaySquad().size();i++) {
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + i + " SET " + 
								photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + i + " SET " + 
								"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + i + " SET " + 
							match.getSetup().getAwaySquad().get(i-1).getFull_name() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFirstName" + i + " SET " + 
							"" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamFlag" + i + " SET " + 
							flag_path + "/" + match.getSetup().getAwaySquad().get(i-1).getNationality() + "\0");
					
					if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman" + "\0");
							
						}else if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					}else if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase("BAT/KEEPER")) {
						if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman" + "\0");
							
						}else if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "Batsman_Lefthand" + "\0");
						}
					}else if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "FastBowler" + "\0");
						}else {
							switch(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "FastBowler" + "\0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "SpinBowlerIcon" + "\0");
								break;
							}
						}
					}else if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
									icon_path + "/" + "FastBowlerAllrounder" + "\0");
						}else {
							switch(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "FastBowlerAllrounder" + "\0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
										icon_path + "/" + "SpinBowlerAllrounder" + "\0");
								break;
							}
						}
					}
					
					if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + i + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "1" + " \0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain" + i + " SET " + 
								icon_path + "/" + "CaptainIcon" + "\0");
					}
					else if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vCaptain" + i + " SET " + 
								icon_path + "/" + "CaptainIcon" + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
								icon_path + "/" + "Keeper" + "\0");
					}
					else if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgRole" + i + " SET " + 
								icon_path + "/" + "Keeper" + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$TeamAll$ImagesAll$ImageGrp" + i + 
								"$RowAnimation$RowOmo$Dehighlight$TextAll$Icons$CaptainIcon*ACTIVE SET "+ "0" + " \0");
					}
				}
			}
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + " WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + "\0");
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataIn 3.420 \0");
//		Preview(print_writer, viz_scene, which_graphic_on_screen, "TEAMLINEUP");
	}
	public void populatePlayingXISubs(PrintWriter print_writer,String viz_scene, int TeamId,List<Player> plyr,MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			//int row_id = 0,omo=0;
			//String cont = "";
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$Sponsor*ACTIVE SET 1 \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$BatTeamHeaderGrp$img_Text1$txt_SubHead1*GEOM*TEXT SET " + match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$BatTeamHeaderGrp$img_Text1$txt_SubHead2*GEOM*TEXT SET " + match.getSetup().getTournament().toUpperCase()	+ "\0");
			
			if(TeamId == match.getSetup().getHomeTeamId()) {
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamBageGrp$img_Badges*TEXTURE*IMAGE SET " + logo_path + 
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamBageGrp$Select_BadgeType$img_Badges*TEXTURE*IMAGE SET " + logo_path + 
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamBageGrp$LogoBase$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$BatTeamHeaderGrp$img_Text1$txt_TeamName*GEOM*TEXT SET " + 
						match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType*FUNCTION*Omo*vis_con SET "+ "4" + " \0");
				
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$Select_Substitues*FUNCTION*Omo*vis_con SET "+ 
						(match.getSetup().getHomeSubstitutes().size()-1) + " \0");
				for(int j=1;j<=match.getSetup().getHomeSubstitutes().size();j++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$SubstitutesIn$Select_Substitues$SubPlayer" + j + 
							"$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$SubstitutesIn$Select_Substitues$SubPlayer" + j + 
							"$img_Text2$txt_PlayerName*GEOM*TEXT SET " + match.getSetup().getHomeSubstitutes().get(j-1).getFull_name() + "\0");
				}
				
				for(int i=1;i<=match.getSetup().getHomeSquad().size();i++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$NameGrp$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$ImageGrp$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$ImageGrp$LogoBase$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + "$PlayerOut$ImageGrp$img_Player"
								+ "*TEXTURE*IMAGE SET " + photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\\" + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION +" \0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\\" + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + "$PlayerOut$ImageGrp$img_Player"
								+ "*TEXTURE*IMAGE SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\\" + match.getSetup().getHomeSquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION +" \0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$NameGrp$img_Text1$txt_PlayerName*GEOM*TEXT SET " + match.getSetup().getHomeSquad().get(i-1).getFull_name() + "\0");
					
					if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						if(match.getSetup().getHomeSquad().get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
									"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman" +" \0");
							
						}else if(match.getSetup().getHomeSquad().get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
									"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "LeftHandBatsman" +" \0");
						}
					}else if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
									"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" +" \0");
						}else {
							switch(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
										"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" +" \0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
										"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerIcon" +" \0");
								break;
							}
						}
					}else if(match.getSetup().getHomeSquad().get(i-1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
									"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" +" \0");
						}else {
							switch(match.getSetup().getHomeSquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
										"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" +" \0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
										"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerAllrounder" +" \0");
								break;
							}
						}
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType$CaptainiconGrp$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					
					if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
					}
					else if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "KeeperAllrounder" +" \0");
					}
					else if(match.getSetup().getHomeSquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "KeeperAllrounder" +" \0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
					}
				}
			}else {
				
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamBageGrp$img_Badges*TEXTURE*IMAGE SET " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamBageGrp$Select_BadgeType$img_Badges*TEXTURE*IMAGE SET " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamBageGrp$LogoBase$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$BatTeamHeaderGrp$img_Text1$txt_TeamName*GEOM*TEXT SET " + 
						match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType*FUNCTION*Omo*vis_con SET "+ "4" + " \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$Select_Substitues*FUNCTION*Omo*vis_con SET "+ 
						(match.getSetup().getAwaySubstitutes().size()-1) + " \0");
				
				for(int j=1;j<=match.getSetup().getAwaySubstitutes().size();j++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$SubstitutesIn$Select_Substitues$SubPlayer" + j + 
							"$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$SubstitutesIn$Select_Substitues$SubPlayer" + j + 
							"$img_Text2$txt_PlayerName*GEOM*TEXT SET " + match.getSetup().getAwaySubstitutes().get(j-1).getFull_name() + "\0");
				}
				
				for(int i=1;i<=match.getSetup().getAwaySquad().size();i++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$NameGrp$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$ImageGrp$img_Text*TEXTURE*IMAGE SET " + text_path + "/" + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$ImageGrp$LogoBase$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + "$PlayerOut$ImageGrp$img_Player"
								+ "*TEXTURE*IMAGE SET " + photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\\" + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION +" \0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\\" + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + "$PlayerOut$ImageGrp$img_Player"
								+ "*TEXTURE*IMAGE SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\\" + match.getSetup().getAwaySquad().get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION +" \0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$NameGrp$img_Text1$txt_PlayerName*GEOM*TEXT SET " + match.getSetup().getAwaySquad().get(i-1).getFull_name() + "\0");
			
					if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("RHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
									"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "Batsman" +" \0");
						}else if(match.getSetup().getAwaySquad().get(i-1).getBattingStyle().equalsIgnoreCase("LHB")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
									"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "LeftHandBatsman" +" \0");
						}
					}else if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
									"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" +" \0");
						}else {
							switch(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
										"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowler" +" \0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
										"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerIcon" +" \0");
								break;
							}
						}
					}else if(match.getSetup().getAwaySquad().get(i-1).getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
									"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" +" \0");
						}else {
							switch(match.getSetup().getAwaySquad().get(i-1).getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
										"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "FastBowlerAllrounder" +" \0");
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
										"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "SpinBowlerAllrounder" +" \0");
								break;
							}
						}
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
							"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType$CaptainiconGrp$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					
					if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
					}
					else if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "KeeperAllrounder" +" \0");
					}
					else if(match.getSetup().getAwaySquad().get(i-1).getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$img_Player$RoleiconGrp$img_RoleIcon*TEXTURE*IMAGE SET " + icon_path + "/" + "KeeperAllrounder" +" \0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$Team1Out$Select_LineUpType$Type4$All11$PlayerGrp" + i + 
								"$PlayerOut$ImageGrp$LogoBase$SelectCaptainType*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
					}
				}
			}
			if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$BottomInfoAll$BottomInfoOut$BottomInfoIn$img_Text1$txt_Info*GEOM*TEXT SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + 
						" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$TeamGrp1$TeamDataAll1$BottomInfoAll$BottomInfoOut$BottomInfoIn$img_Text1$txt_Info*GEOM*TEXT SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + 
						" WON THE TOSS & ELECTED TO " + match.getSetup().getTossWinningDecision() + " \0");
			}
		}
		//print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 4.000 Team$Team1_In 3.420 \0");
		Preview(print_writer, viz_scene, which_graphic_on_screen, "TEAMLINEUP_SUBS");
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
			this.status = "ERROR: BatsmanThisMatch's inning is null";
		} else {
			
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
			this.status = "ERROR: BowlerThisMatch's inning is null";
		} else {
			
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
						for(BattingCard bc : inn.getBattingCard()) {
							if(playerId == bc.getPlayerId()) {
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tLastName SET " + bc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$Mile$Data$ValueGrp$Runs$IconOmo*FUNCTION*Omo*vis_con SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tFistName SET " + bc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tLastName SET " + bc.getPlayer().getSurname() + "\0");
								
								if(bc.getStatus().equals(CricketUtil.OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tRuns SET " + bc.getRuns() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tRuns SET " + bc.getRuns() + "*" + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBalls SET " + bc.getBalls() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
										inn.getBatting_team().getTeamName4() + "\0");
								
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
											inn.getBatting_team().getTeamName4() + "\\\\" + bc.getPlayer().getPhoto() + ".png" + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName4() + "\\\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
											inn.getBatting_team().getTeamName4() + "\\\\" + bc.getPlayer().getPhoto() + ".png" + "\0");
								}
							}
						}
						
						break;
					case "BOWLER":
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(playerId == boc.getPlayerId()) {
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tLastName SET " + boc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$Mile$Data$ValueGrp$Runs$IconOmo*FUNCTION*Omo*vis_con SET 2 \0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tFistName SET " + boc.getPlayer().getFirstname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tLastName SET " + boc.getPlayer().getSurname() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tRuns SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON tBalls SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " Overs" + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
										inn.getBowling_team().getTeamName4() + "\0");
								if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + 
											inn.getBowling_team().getTeamName4() + "\\\\" + boc.getPlayer().getPhoto() + ".png" + "\0");
								}else {
									if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBowling_team().getTeamName4() + "\\\\" + boc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
										this.status = CricketUtil.UNSUCCESSFUL;
									}
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
											inn.getBowling_team().getTeamName4() + "\\\\" + boc.getPlayer().getPhoto() + ".png" + "\0");
								}
							}
						}
						break;
					}
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 In$DataIn 1.660 In$Data1In 1.700 \0");
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
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + bc.getPlayer().getFirstname() + CricketUtil.PNG_EXTENSION).exists()) {
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
	public void populateLtPointsTable(PrintWriter print_writer,String viz_sence_path,List<LeagueTeam> point_table, MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		int row_id=0;
		String cont = "";
		for(int i = 0; i <= point_table.size()-1; i++) {
			row_id = row_id + 1;
			
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tQualified" + row_id + " SET " + "" + "\0");
			}
			else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tQualified" + row_id + " SET " + "Q" + "\0");
			}
			if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().contains(point_table.get(i).getTeamName().toUpperCase())  
					|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().contains(point_table.get(i).getTeamName().toUpperCase())) {
				cont = "$Highlight";
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$PointDataAll$PointsDataGrp$PointRow" + row_id +
						"$RowAnimation$RowOmo$Highlight*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$PointDataAll$PointsDataGrp$PointRow" + row_id + 
						"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET 1 \0");
			}
			else {
				cont = "$Dehighlight";
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$PointDataAll$PointsDataGrp$PointRow" + row_id +
						"$RowAnimation$RowOmo$Highlight*ACTIVE SET 0 \0");
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$PointDataAll$PointsDataGrp$PointRow" + row_id + 
						"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET 0 \0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTeam" + row_id + " SET " + point_table.get(i).getTeamName() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayedValue" + row_id + " SET " + point_table.get(i).getPlayed() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tWinValue" + row_id + " SET " + point_table.get(i).getWon() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLossValue" + row_id + " SET " + point_table.get(i).getLost() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNoResultValue" + row_id + " SET " + point_table.get(i).getNoResult() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$PointDataAll$PointsDataGrp$PointRow" + row_id +
					"$RowAnimation$RowOmo" + cont + "$TextAll$TextGrp$PointsValue*ACTIVE SET 1 \0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsValue" + row_id + " SET " + point_table.get(i).getPoints() + "\0");

		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.jpg In 0.860 \0");
		TimeUnit.MILLISECONDS.sleep(200);
	}
	
	public void populateProjectedScore(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
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
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "@"+ proj_score_rate[0] +" (CRR)" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + proj_score_rate[1] + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "@" + proj_score_rate[2] +" RPO" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + proj_score_rate[3] + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "@" + proj_score_rate[4] +" RPO" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + proj_score_rate[5] + "\0");
					
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
		
	}
	public void populateTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Target's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(Inning inn : match.getMatch().getInning()) {
				
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
						"fair_break" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
					
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
				
				if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "TARGET - VJD" + "\0");
				}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "TARGET - DLS" + "\0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "TARGET" + "\0");
				}
				
				if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + match.getSetup().getMaxOvers()*6 + "\0");
					
				}else {
					if(Double.valueOf(match.getSetup().getTargetOvers()) != 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + Integer.valueOf(match.getSetup().getTargetOvers())*6 + "\0");
					}
				}	
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.314 \0");
		TimeUnit.MILLISECONDS.sleep(200);
	}
	public void populateFFTarget(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster, Configuration config)throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Target's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Teams$Sponsor*ACTIVE SET 0 \0");	
			for(Inning inn : match.getMatch().getInning()) {
				
				for(Player hs : match.getSetup().getHomeSquad()) {
					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)||hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$PlayerImageOut$PlayerImageGrp1$img_Player*TEXTURE*IMAGE SET "+ photo_path 
									+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$PlayerImageOut$PlayerImageGrp1$img_Player*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path 
									+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
						}
						
					}
				}
				for(Player as : match.getSetup().getAwaySquad()) {
					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)||as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$PlayerImageOut$PlayerImageGrp2$img_Player*TEXTURE*IMAGE SET "+ photo_path 
									+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$PlayerImageOut$PlayerImageGrp2$img_Player*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path 
									+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
						}
						
					}
				}
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TeamBageGrp1$img_Badges*TEXTURE*IMAGE SET " + logo_path + 
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TeamBageGrp1$Select_BadgeType$img_Badges*TEXTURE*IMAGE SET " + logo_path + 
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TeamBageGrp1$LogoBase$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
						match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TeamBageGrp2$img_Badges*TEXTURE*IMAGE SET " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TeamBageGrp2$Select_BadgeType$img_Badges*TEXTURE*IMAGE SET " + logo_path + 
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TeamBageGrp2$LogoBase$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
						match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetHeaderGrp$img_Text1$HeaderGrp$Maxsize$txt_TeamName*GEOM*TEXT SET " + match.getSetup().getTournament() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetHeaderGrp$img_Text1$HeaderGrp$Maxsize$txt_SubHead1*GEOM*TEXT SET " + match.getSetup().getMatchIdent() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetHeaderGrp$img_Text1$HeaderGrp$Maxsize$txt_SubHead2*GEOM*TEXT SET " + " " + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1*TEXTURE*IMAGE SET " + base_path + "2/" + 
						inn.getBatting_team().getTeamName3().toLowerCase() +" \0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$SubHeadGrp$Out$In$img_Text1$txt_TeamName*GEOM*TEXT SET " + 
						inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
				
					if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
						if(CricketFunctions.GetTargetData(match).getTargetOvers() == "1") {
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_RunsText*GEOM*TEXT SET " + "RUNS" + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$OversGrp$Out$In$img_Text1$txt_Overs*GEOM*TEXT SET " + 
									(Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6) + " BALLS" + "\0");
							
						}else {
							if(Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_RunsText*GEOM*TEXT SET " + "RUNS" + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$OversGrp$Out$In$img_Text1$txt_Overs*GEOM*TEXT SET " + 
										match.getSetup().getMaxOvers() + " OVERS" + "\0");
							}else {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_RunsText*GEOM*TEXT SET " + "RUNS" + "\0");
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$OversGrp$Out$In$img_Text1$txt_Overs*GEOM*TEXT SET " + 
										(Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6) + " BALLS" + "\0");
							}
						}
					}else {
						if(Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6 >= 100) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_RunsText*GEOM*TEXT SET " + "RUNS" + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$OversGrp$Out$In$img_Text1$txt_Overs*GEOM*TEXT SET " + 
									(Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6) + " OVERS" + " ("+ match.getSetup().getTargetType().toUpperCase() +")" + "\0");
						}else {
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(match).getTargetRuns() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$ScoreGrp$Out$In$img_Text1$txt_RunsText*GEOM*TEXT SET " + "RUNS" + "\0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$OversGrp$Out$In$img_Text1$txt_Overs*GEOM*TEXT SET " + 
									(Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers())*6) + " BALLS" + " ("+ match.getSetup().getTargetType().toUpperCase() +")" + "\0");
						}
					}
					
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
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$Target$TargetDataAll$RunRateGrp$Out$In$img_Text1$txt_Overs*GEOM*TEXT SET " + 
							"@ " + CricketFunctions.generateRunRate(requiredRuns, 0, requiredBalls, 2,match) + " RPO" + "\0");
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 4.000 Target_In 1.700 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
			
	}
	public void populateTeamSummary(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$noname$LT_Flag*FUNCTION*Omo*vis_con SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					"fair_break" + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM,match, whichInning, 0,"-", match.getEventFile().getEvents()).split("-");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + 
							inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					
					
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + "*" + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "0s" + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + Count[1] + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + Count[2] + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + Count[3] + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5B" + " SET " + Count[4] + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue6B" + " SET " + Count[6] + "\0");
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.857 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populateLtBattingSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$noname$LT_Flag*FUNCTION*Omo*vis_con SET 1 \0");
			for(Inning inn : match.getMatch().getInning()) {
				for(BattingCard bc : inn.getBattingCard()) {
					if (inn.getInningNumber() == whichInning) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BATSMAN,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
						
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
						
							if(PlayerId == bc.getPlayerId()) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + bc.getPlayer().getNationality() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + bc.getPlayer().getFull_name() + "\0");
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "*" + "\0");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + bc.getRuns() + "\0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + bc.getBalls() + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "0s" + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + Count[1] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + Count[2] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + Count[3] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5B" + " SET " + Count[4] + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue6B" + " SET " + Count[6] + "\0");
								
							}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.857 \0");
			TimeUnit.MILLISECONDS.sleep(200);
			
		}
	}
	public void populateLtBowlerSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: PlayerSummary's inning is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$noname$LT_Flag*FUNCTION*Omo*vis_con SET 1 \0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					for(BowlingCard boc : inn.getBowlingCard()) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BOWLER,match, whichInning, PlayerId,"-", match.getEventFile().getEvents()).split("-");
						
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
						
						if(PlayerId == boc.getPlayerId()) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + boc.getPlayer().getNationality() + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + boc.getPlayer().getFull_name() + "\0");
							
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + boc.getWickets() + "-" + boc.getRuns()  + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + "0s" + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + Count[0] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + Count[1] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + Count[2] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + Count[3] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue5B" + " SET " + Count[4] + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue6B" + " SET " + Count[6] + "\0");
							
						}	
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.857 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populateFallofWicket(PrintWriter print_writer,String viz_scene,int whichInning, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: inning is null";
		} else {
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					
					if(inn.getTotalWickets() >=10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
	
					if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatCols" + " SET " + inn.getFallsOfWickets().size() + "\0");
						
						for(FallOfWicket fow : inn.getFallsOfWickets()) {								
							if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
								for(int fow_id=1;fow_id<=10;fow_id++) {
									if(fow_id <= inn.getFallsOfWickets().size()) {
										
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow.getFowNumber() + "A" + " SET " + 
												fow.getFowNumber() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + fow.getFowNumber() + "B" + " SET " + 
												fow.getFowRuns() + "\0");
									}
								}	
							}		
						}
					}
					
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.857\0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	
	}
	public void populateSplit(PrintWriter print_writer,String viz_scene,int whichInning,int splitValue, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
		
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					if(inn.getTotalWickets() >=10) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tScore" + " SET " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tOvers" + " SET " + 
							CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
					
					if (inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 30 || inn.getBowlingTeamId() == match.getSetup().getHomeTeamId() && splitValue == 50) {
						if(splitValue == 30) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "BALLS PER THIRTY" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead01" + " SET " + "THIRTY" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "BALLS PER FIFTY" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead01" + " SET " + "FIFTY" + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
						
					} else {
						if(splitValue == 30) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "BALLS PER THIRTY" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead01" + " SET " + "THIRTY" + "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + "BALLS PER FIFTY" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead01" + " SET " + "FIFTY" + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					}

					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatCols" + " SET " + 
							CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size() + "\0");
					
				    for (int i = 0; i < CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size(); i++) {
				    	int row_id = i + 1;
				    	for(int split_id=1;split_id<=6;split_id++) {
					    	if(split_id <= CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).size()) {
					    		
					    		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + row_id + "B" + " SET " + 
					    				CricketFunctions.getSplit(whichInning, splitValue,match,match.getEventFile().getEvents()).get(i) + "\0");
								
					    	}
				    	}
			        }
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.857\0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
		
	}	
	public void populateComparision(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Comparision's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					"fair_break" + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBowling_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + " SET " + inn.getBowling_team().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamScore" + " SET " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamScore" + " SET " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + "\0");
					
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.314 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}	
	}
	public void populateLTPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			String Left_Batsman ="",Right_Batsman="",Left_Image="",Right_Image="";
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + 
					"PARTNERSHIP" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + 
					match.getSetup().getMatchIdent() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + 
					match.getSetup().getTournament() + "\0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + 
							logo_path + inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							if(bc.getPlayerId()==inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								Left_Batsman = bc.getPlayer().getFull_name();
								Left_Image = bc.getPlayer().getPhoto();
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag1" + " SET " + 
										flag_path + bc.getPlayer().getNationality().toUpperCase() + "\0");
							}
							
							if(bc.getPlayerId()==inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								Right_Batsman = bc.getPlayer().getFull_name();
								Right_Image = bc.getPlayer().getPhoto();
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag2" + " SET " + 
										flag_path + bc.getPlayer().getNationality().toUpperCase() + "\0");
							}
						}
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName1" + " SET " + 
							Left_Batsman + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName2" + " SET " + 
							Right_Batsman + "\0");
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + 
								photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Left_Image + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Left_Image + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + 
								"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Left_Image + CricketUtil.PNG_EXTENSION + "\0");
					}
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + 
								photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Right_Image + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Right_Image + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage2" + " SET " + 
								"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path  + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Right_Image + CricketUtil.PNG_EXTENSION + "\0");
					}


					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionRuns1" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionBalls1" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionRuns2" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionBalls2" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFoursValue" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalFours() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSixesValue" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalSixes() + "\0");
					
					if(inn.getTotalWickets() == 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
								(inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 1) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
								(inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 2) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
								(inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
								(inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP" + "\0");
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataIn 3.100 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populatePartnershipLt(PrintWriter print_writer, String viz_scene, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			String Left_Batsman ="",Right_Batsman="",Left_Image="",Right_Image="";
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$HomeLogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$noname$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$AwayLogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							if(bc.getPlayerId()==inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
								Left_Batsman = bc.getPlayer().getFull_name();
								Left_Image = bc.getPlayer().getPhoto();
								
							}
							if(bc.getPlayerId()==inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
								Right_Batsman = bc.getPlayer().getFull_name();
								Right_Image = bc.getPlayer().getPhoto();
								
							}
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName" + " SET " + Left_Batsman + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName" + " SET " + Right_Batsman + "\0");
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage01" + " SET " + photo_path + 
								inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Left_Image + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Left_Image + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage01" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
								inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Left_Image + CricketUtil.PNG_EXTENSION + "\0");
					}
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage02" + " SET " + photo_path + 
								inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Right_Image + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Right_Image + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage02" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
								inn.getBatting_team().getTeamName3().toUpperCase() + centre_path + Right_Image + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBalls" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionRuns1" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionBall1" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionRuns2" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerContributionBall2" + " SET " + 
							inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + "\0");
					
					
					if(inn.getTotalWickets() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + + (inn.getTotalWickets() + 1) + "st WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 1) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + + (inn.getTotalWickets() + 1) + "nd WICKET PARTNERSHIP" + "\0");
					}else if(inn.getTotalWickets() == 2) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + + (inn.getTotalWickets() + 1) + "rd WICKET PARTNERSHIP" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + + (inn.getTotalWickets() + 1) + "th WICKET PARTNERSHIP"  + "\0");
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.314 \0");
			TimeUnit.MILLISECONDS.sleep(200);
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
			int row_no=0,omo_num=0;
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "" + "\0");
			
			switch(StatType.toUpperCase()) {
			case "MOST_RUNS_DATA":
				
				//Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$TopScorer$TopHeaderGrp$img_Text1$HeaderGrp$Maxsize$txt_SubHead2*GEOM*TEXT SET " + "MOST RUNS " + "\0");
				
				/*for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + 1 + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
									team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" + 
									tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
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
				}*/
				break;
			case "MOST_RUNS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST RUNS" + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no <= 5) {
						if(tournament.get(i).getPlayerId() == playerid) {
							omo_num = 1;
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
							
						}else {
							omo_num = 0;
							
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + omo_num + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + row_no + " SET " + 
								flag_path + tournament.get(i).getPlayer().getNationality() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + 
								tournament.get(i).getRuns() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + "" + "\0");
						
					}
				}
				break;
			case "MOST_WICKETS":
				
				Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST WICKETS" + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							omo_num = 1;
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							omo_num = 0;
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + omo_num + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + row_no + " SET " + 
								flag_path + tournament.get(i).getPlayer().getNationality() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + 
								tournament.get(i).getWickets() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + "" + "\0");
					}
				}
				break;
			case "MOST_FOURS":
				
				Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST FOURS" + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							omo_num = 1;
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							omo_num = 0;
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + omo_num + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + row_no + " SET " + 
								flag_path + tournament.get(i).getPlayer().getNationality() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + 
								tournament.get(i).getFours() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + "" + "\0");
					}
				}
				break;
			case "MOST_SIXES":
				Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST SIXES" + "\0");
				
				for(int i = 0; i <= tournament.size() - 1 ; i++) {
					row_no = row_no + 1;
					if(row_no < 6) {	
						if(tournament.get(i).getPlayerId() == playerid) {
							omo_num = 1;
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
										tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPlayerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
										team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName3().toUpperCase() + centre_path + 
											tournament.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
							}
						}else {
							omo_num = 0;
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vHighlight" + row_no + " SET " + omo_num + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + row_no + " SET " + 
								flag_path + tournament.get(i).getPlayer().getNationality() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
								tournament.get(i).getPlayer().getFull_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
								team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + 
								tournament.get(i).getSixes() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + "" + "\0");
					}
				}
				break;
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 4.000 Top_In 2.020 \0");
			
		}
	}
	public void populateMostRuns(PrintWriter print_writer,String viz_scene,String StatType,List<Tournament> tournament,List<Team> team,MatchAllData match, String broadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Leaderboard inning is null";
		} else {
			
			int row_no=0;

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/Nepal_T20/Logos/TLogo\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDEsign$RightGrp$Bands$RightTeamLogos$RightLogo$RightLogo*TEXTURE*IMAGE SET IMAGE*/Default/Nepal_T20/Logos/TLogo\0");

			Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "MOST RUNS " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament() + "\0");
			for(int i = 0; i <= tournament.size() - 1 ; i++) {
				if(team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName2().equalsIgnoreCase(StatType.toUpperCase())) {
					row_no = row_no + 1;
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerName" + row_no + " SET " + 
							tournament.get(i).getPlayer().getFull_name() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_no + " SET " + 
							team.get(tournament.get(i).getPlayer().getTeamId() - 1).getTeamName1() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerRuns" + row_no + " SET " + tournament.get(i).getRuns() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerBalls" + row_no + " SET " + " " + "\0");
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$LeaderBoard$BottomInfoGrp*ACTIVE SET 0 \0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 2.474 In$DataIn 1.180 \0");
			TimeUnit.SECONDS.sleep(2);
		}
	}
	public void populateLtEquation(PrintWriter print_writer,String viz_scene, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
		
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					"fair_break" + "\0");
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader" + " SET " + inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					
					if(match.getSetup().getTargetOvers() == null || match.getSetup().getTargetOvers().trim().isEmpty() && match.getSetup().getTargetRuns() == 0) {
						if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
							}
							
						}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
								|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
							
							if(match.getMatch().getMatchStatus() != null) {
								if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
								}
								else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
								}
							}
						}
						
						else{
							if (CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
								if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + " (VJD)" + "\0");
								}
								else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
												+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
												" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + " (DLS)" + "\0");
								}
								else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " 
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+  CricketFunctions.OverBalls(0,CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + "\0");
								}
								
							}else {
								if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET "  
											+ " NEED " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + 
											" TO WIN FROM "+ CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (VJD)" + "\0");
								}
								else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
										//System.out.println(match.getTargetType());
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
					}else {
						if(Double.valueOf(match.getSetup().getTargetOvers()) == 1 && match.getSetup().getTargetRuns() == 0) {
							if(CricketFunctions.GetTargetData(match).getRemaningRuns() == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
								
							}else if(CricketFunctions.GetTargetData(match).getRemaningRuns() > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| match.getMatch().getInning().get(1).getTotalOvers() >= match.getSetup().getMaxOvers()) {
								
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");	
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
							if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) == 0) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
									}
								}
							}else if((match.getSetup().getTargetRuns() - match.getMatch().getInning().get(1).getTotalRuns()) > 0 && match.getMatch().getInning().get(1).getTotalWickets() >= 10 
									|| Double.valueOf(CricketFunctions.OverBalls(match.getMatch().getInning().get(1).getTotalOvers(), match.getMatch().getInning().get(1).getTotalBalls())) 
									>= Double.valueOf(match.getSetup().getTargetOvers())) {
								if(match.getMatch().getMatchStatus() != null) {
									if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");											
									}
									else if(match.getMatch().getMatchResult().split(",")[1].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");						
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
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
										//System.out.println(match.getTargetType());
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
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428 \0");
		TimeUnit.MILLISECONDS.sleep(200);
	}	
	public void populatePointsTable(PrintWriter print_writer,String viz_scene,List<LeagueTeam> point_table, List<Team> teams, String broadcaster,MatchAllData match,List<VariousText> vt) throws InterruptedException 
	{
		int row_id=0,omo_num = 0;
		DecimalFormat df = new DecimalFormat("0.000");
		
		//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead1" + " SET " + "GROUP - " + point_table.get(0).getPool().trim() + "\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPointsTeamLogo1" + " SET " + logo_path + 
				match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgPointsTeamLogo2" + " SET " + logo_path + 
				match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryTeamLogo1" + " SET " + logo_path + 
				match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryTeamLogo2" + " SET " + logo_path + 
				match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$All$PointsTable$BottomInfo*ACTIVE SET 0 \0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead1" + " SET " + 
				"" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableHead2" + " SET " + 
				"POINTS TABLE" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTableSubHeader" + " SET " + 
				match.getSetup().getTournament().toUpperCase() + "\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayedHead" + " SET " + 
				"P" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tWonHead" + " SET " + 
				"W" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLossHead" + " SET " + 
				"L" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTieHead" + " SET " + 
				"T/NR" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNrHead" + " SET " + 
				"BP" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBonusPointsHead" + " SET " + 
				"PTS" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsHead" + " SET " + 
				"" + "\0");
		for(VariousText vartext : vt) {
			if(vartext.getVariousType().equalsIgnoreCase("POINTSTABLEFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsInfo" + " SET " + 
						vartext.getVariousText() + "\0");
			}else if(vartext.getVariousType().equalsIgnoreCase("POINTSTABLEFOOTER") && vartext.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsInfo" + " SET " + 
						"TOP FOUR TEAMS QUALIFY FOR THE SEMI-FINALS" + "\0");
			}
		}
		
		for(int i = 0; i <= point_table.size() - 1 ; i++) {
			row_id = row_id + 1;
			
			if(match.getSetup().getHomeTeam().getTeamName1().toUpperCase().contains(point_table.get(i).getTeamName().toUpperCase())  
					|| match.getSetup().getAwayTeam().getTeamName1().toUpperCase().contains(point_table.get(i).getTeamName().toUpperCase())) {
				omo_num = 1;
			}else {
				omo_num = 0;
			}
			
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$PointsTableAll$PointsTeamLogoInGrp1$LogoInAllGrp$SummaryTeamLogo1" + 
//					"*TEXTURE*IMAGE SET " + logo_path + point_table.get(i).getTeamName().toLowerCase() +" \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$PointsTableAll$PointsData$PointsDataGrp$PointRow" + row_id + "$RowAnimation$RowOmo"
					+ "*FUNCTION*Omo*vis_con SET "+ omo_num + "\0");
			
			if(point_table.get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tQualified" + row_id + " SET " + 
						"" + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tQualified" + row_id + " SET " + 
						"Q" + "\0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsTeam" + row_id + " SET " + 
					point_table.get(i).getTeamName().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayedValue" + row_id + " SET " + 
					point_table.get(i).getPlayed() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tWinValue" + row_id + " SET " + 
					point_table.get(i).getWon() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLossValue" + row_id + " SET " + 
					point_table.get(i).getLost() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTieValue" + row_id + " SET " + 
					point_table.get(i).getNoResult() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tNoResultValue" + row_id + " SET " + 
					point_table.get(i).getCount() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBonusPointsValue" + row_id + " SET " + 
					point_table.get(i).getPoints() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPointsValue" + row_id + " SET " + 
					"" + "\0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 PointsTableIn 1.400 PointsTableIn$PointsOffsetIn 0.930 BatPartnershipIn 0.000 "
				+ "BattingRightCardIn 0.000 BatPerformerIn 0.000 BattingCardIn 0.000 BattingCardIn$BatOffsetIn 0.000 BallPerformerIn 0.000 BowlingCardIn 0.000 BowlingCardIn$BallOffsetIn 0.000 "
				+ "BowlingRightCardIn 0.000 BowlingRightCardIn$BallRightOffset 0.000 SummaryIn 0.000 SummaryOffsetIn 0.000 \0");
		
//		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 PointsTableIn 1.400 PointsTableIn$PointsOffsetIn 0.930 \0");
		
	}
	public void populatePlayoffs(PrintWriter print_writer,String viz_scene,List<Playoff> playoffs,List<Team> team, String broadcaster,MatchAllData match) throws InterruptedException 
	{
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + " " + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + "PLAYOFFS" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + match.getSetup().getTournament() + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-Header" + " SET " + "QUALIFIER 1" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-Header" + " SET " + "ELIMINATOR" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-Header" + " SET " + "QUALIFIER 2" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-Header" + " SET " + "THE FINAL" + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-RUNNER-Header" + " SET " + "RUNNER-UP Q1" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-WINNER-Header" + " SET " + "WINNER ELM" + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamA-Alpha" + " SET " + "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamB-Alpha" + " SET " + "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA-Alpha" + " SET " + "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB-Alpha" + " SET " + "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Alpha" + " SET " + "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Alpha" + " SET " + "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Alpha" + " SET " + "100" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Alpha" + " SET " + "100" + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamA" + " SET " + playoffs.get(0).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamB" + " SET " + playoffs.get(0).getTeam2().toUpperCase() + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA" + " SET " + playoffs.get(1).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB" + " SET " + playoffs.get(1).getTeam2().toUpperCase() + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA" + " SET " + playoffs.get(2).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB" + " SET " + playoffs.get(2).getTeam2().toUpperCase() + "\0");
	
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA" + " SET " + playoffs.get(3).getTeam1().toUpperCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB" + " SET " + playoffs.get(3).getTeam2().toUpperCase() + "\0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "+ "IMAGE*/Default/GPCL/TeamColour/0" +" \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "+ "IMAGE*/Default/GPCL/TeamColour/0" +" \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "+ "IMAGE*/Default/GPCL/TeamColour/0" +" \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "+ "IMAGE*/Default/GPCL/TeamColour/0" +" \0");
		
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$A$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$B$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_A$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_B$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamA$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamB$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 0 \0");
		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 0 \0");
		
		
		for(int i=0;i<team.size()-1;i++) {
			if(team.get(i).getTeamName3().equalsIgnoreCase(playoffs.get(0).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$A$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamA-Logo" + " SET " + 
						"IMAGE*/Default/Nepal_T20/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if(team.get(i).getTeamName3().equalsIgnoreCase(playoffs.get(0).getTeam2())) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$B$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-Q1-TeamB-Logo" + " SET " + 
						"IMAGE*/Default/Nepal_T20/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if(team.get(i).getTeamName3().equalsIgnoreCase(playoffs.get(1).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_A$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-TeamA-Logo" + " SET " + 
						"IMAGE*/Default/Nepal_T20/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if(team.get(i).getTeamName3().equalsIgnoreCase(playoffs.get(1).getTeam2())) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$E1_B$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-E1-TeamB-Logo" + " SET " + 
						"IMAGE*/Default/Nepal_T20/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if(team.get(i).getTeamName3().equalsIgnoreCase(playoffs.get(2).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamA$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Logo" + " SET " + 
						"IMAGE*/Default/Nepal_T20/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if(team.get(i).getTeamName3().equalsIgnoreCase(playoffs.get(2).getTeam2())) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$Q2_TeamB$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Logo" + " SET " + 
						"IMAGE*/Default/Nepal_T20/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if(team.get(i).getTeamName3().equalsIgnoreCase(playoffs.get(3).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamA$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Logo" + " SET " + 
						"IMAGE*/Default/Nepal_T20/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
			if(team.get(i).getTeamName3().equalsIgnoreCase(playoffs.get(3).getTeam2())) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$F_TeamB$TeamLogo*ACTIVE SET 1 \0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Logo" + " SET " + 
						"IMAGE*/Default/Nepal_T20/Logos/" + team.get(i).getTeamName4().toUpperCase() + "\0");
			}
		}
		
		if(playoffs.get(0).getWinner() != null) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "+ "IMAGE*/Default/FairBreak/HeaderBand" +" \0");
			if(playoffs.get(0).getWinner().equalsIgnoreCase(playoffs.get(0).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamB-Alpha" + " SET " + "50" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "001-TeamA-Alpha" + " SET " + "50" + "\0");
			}
		}
		
		if(playoffs.get(1).getWinner() != null) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$E1$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "+ "IMAGE*/Default/FairBreak/HeaderBand" +" \0");
			if(playoffs.get(1).getWinner().equalsIgnoreCase(playoffs.get(1).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamB-Alpha" + " SET " + "50" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "002-EL-TeamA-Alpha" + " SET " + "50" + "\0");
			}
		}
		
		if(playoffs.get(2).getWinner() != null) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Q2$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "+ "IMAGE*/Default/FairBreak/HeaderBand" +" \0");
			if(playoffs.get(2).getWinner().equalsIgnoreCase(playoffs.get(2).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamB-Alpha" + " SET " + "50" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "003-Q2-TeamA-Alpha" + " SET " + "50" + "\0");
			}
		}
		
		if(playoffs.get(3).getWinner() != null) {
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PartnershipAll$Data$PlayOffs$noname$Final$Ani$TimeGrp$Noggi*TEXTURE*IMAGE SET "+ "IMAGE*/Default/FairBreak/HeaderBand" +" \0");
			if(playoffs.get(3).getWinner().equalsIgnoreCase(playoffs.get(3).getTeam1())) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamB-Alpha" + " SET " + "50" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "004-F-TeamA-Alpha" + " SET " + "50" + "\0");
			}
		}
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 In$ManDataIn 0.931 \0");
		
	}
	
	public void populateBowlerStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId,List<Player> plyr, List<Team> team,List<Ground> ground, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vFlag" + " SET " + "1" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName3().toLowerCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
					plyr.get(playerId - 1).getNationality().toUpperCase() + "\0");
			
			if(plyr.get(playerId - 1).getBowlingStyle() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						CricketFunctions.getbowlingstyle(plyr.get(playerId - 1).getBowlingStyle()).toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "" + "\0");
			}
			
			if(plyr.get(playerId - 1).getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428\0");
			TimeUnit.MILLISECONDS.sleep(200);
		}	
	}	
	public void populateTieIdDouble(PrintWriter print_writer,String viz_sence_path,String day,List<Fixture> fix,List<Team>team,MatchAllData match, String selectedbroadcaster) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			int row_id = 1,row = 1;
			String Date = "";
			Calendar cal = Calendar.getInstance();
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + "" + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + "" + "\0");
			
			if(day.toUpperCase().equalsIgnoreCase("TODAY")) {
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "TODAY " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + "LIVE FROM " + match.getSetup().getVenueName() + "\0");
			}
			else if(day.toUpperCase().equalsIgnoreCase("TOMORROW")) {
				cal.add(Calendar.DATE, +1);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "TOMORROW " + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + "FROM " + match.getSetup().getVenueName() + "\0");
			}else if(day.toUpperCase().equalsIgnoreCase("DAY_AFTER_TOMORROW")) {
				cal.add(Calendar.DATE, +2);
				Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + Date + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + "FROM " + 
				match.getSetup().getVenueName() + "\0");
			}
			
			for(int i = 0; i <= fix.size()-1; i++) {
				if(fix.get(i).getDate().equalsIgnoreCase(Date)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$TeamsAll$TeamDataData$Match" + row_id + "_Grp$HomeLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							team.get(fix.get(i).getHometeamid()-1).getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$TeamsAll$TeamDataData$Match" + row_id + "_Grp$HomeLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
							team.get(fix.get(i).getHometeamid()-1).getTeamName3().toLowerCase() + "\0");
							
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHomeTeamName0" + row_id + " SET " + 
							team.get(fix.get(i).getHometeamid()-1).getTeamName1().toUpperCase() + "\0");
					
					if(day.toUpperCase().equalsIgnoreCase("TODAY")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfoMatch" + row + " SET " + 
								fix.get(i).getMatchfilename() + "\0");
					}
					else if(day.toUpperCase().equalsIgnoreCase("TOMORROW") || day.toUpperCase().equalsIgnoreCase("DAY_AFTER_TOMORROW")) {
						if(fix.get(i).getGmtTime() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfoMatch" + row + " SET " + 
									fix.get(i).getMatchfilename() + "\0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfoMatch" + row + " SET " + 
									fix.get(i).getMatchfilename() + " - " + fix.get(i).getGmtTime() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$TeamsAll$TeamDataData$Match" + row_id + "_Grp$AwayLogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
							team.get(fix.get(i).getAwayteamid()-1).getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$TeamsAll$TeamDataData$Match" + row_id + "_Grp$AwayLogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
							team.get(fix.get(i).getAwayteamid()-1).getTeamName3().toLowerCase() + "\0");

					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAwayTeamName0" + row_id + " SET " + 
							team.get(fix.get(i).getAwayteamid()-1).getTeamName1().toUpperCase() + "\0");

					row_id = row_id +1;
					row = row +1;
				}
			}
			
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_sence_path + " C:/Temp/Preview.jpg In 4.220 DataIn 2.200 \0");
			
		}
		
	}
	public void populatePreviousSummary(PrintWriter print_writer, String viz_scene, int match_number,String Type,List<MatchAllData> mtch,List<Fixture> fix, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Match Summary's inning is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			for(int j = 0; j <= mtch.size() - 1; j++) {
				int row_id = 0, max_Strap = 0,omo=0;
				String teamname = "",teamlogoname="",Player_photo=""; 
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumHeader1" + " SET " + 
						"SUMMARY" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumHeader2" + " SET " + 
						mtch.get(j).getSetup().getMatchIdent() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + 
						mtch.get(j).getSetup().getTournament().toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryTeamLogo1" + " SET " + logo_path + 
						mtch.get(j).getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryTeamLogo2" + " SET " + logo_path + 
						mtch.get(j).getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
				
				
				
				for(int i = 1; i <= 2 ; i++) {
					if(i == 1) {
						row_id = 0;
						max_Strap = 3;
						omo = 1;
						
						if(mtch.get(j).getMatch().getInning().get(i-1).getBattingTeamId() == mtch.get(j).getSetup().getTossWinningTeam()) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1" + 
									"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1" + 
									"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
						}
						
					} else {
						row_id = 2;
						max_Strap = 6;
						omo = 4;
						
						if(mtch.get(j).getMatch().getInning().get(i-1).getBattingTeamId() == mtch.get(j).getSetup().getTossWinningTeam()) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow4" + 
									"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 1 \0");
						}else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow4" + 
									"$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
						}
					}
					
					if(mtch.get(j).getMatch().getInning().get(i-1).getBattingTeamId() == mtch.get(j).getSetup().getHomeTeamId()) {
						teamname = mtch.get(j).getSetup().getHomeTeam().getTeamName1();
						teamlogoname = mtch.get(j).getSetup().getHomeTeam().getTeamName3();
						for(Player hs : mtch.get(j).getSetup().getHomeSquad()) {
							if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)||hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								Player_photo =  hs.getPhoto();
							}
						}
					} else {
						teamname = mtch.get(j).getSetup().getAwayTeam().getTeamName1();
						teamlogoname = mtch.get(j).getSetup().getAwayTeam().getTeamName3();
						for(Player as : mtch.get(j).getSetup().getAwaySquad()) {
							if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)||as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								Player_photo =  as.getPhoto();
							}
						}
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTeamFirstName" + i + " SET " + 
							"" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTeamLastName" + i + " SET " + 
							teamname.toUpperCase() + "\0");
					
					if(mtch.get(j).getMatch().getInning().get(i-1).getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTotalScore" + i + " SET " + 
								mtch.get(j).getMatch().getInning().get(i-1).getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTotalScore" + i + " SET " + 
								mtch.get(j).getMatch().getInning().get(i-1).getTotalRuns() + slashOrDash + String.valueOf(mtch.get(j).getMatch().getInning().get(i-1).getTotalWickets()) + "\0");
					}
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumOvers" + i + " SET " + 
							CricketFunctions.OverBalls(mtch.get(j).getMatch().getInning().get(i-1).getTotalOvers(),mtch.get(j).getMatch().getInning().get(i-1).getTotalBalls()) + "\0");
					
					
					if(mtch.get(j).getMatch().getInning().get(i-1).getBattingCard() != null) {
						Collections.sort(mtch.get(j).getMatch().getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
						
						for(BattingCard bc : mtch.get(j).getMatch().getInning().get(i-1).getBattingCard()) {
							if(bc.getRuns() > 0) {
								if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									omo = omo + 1;
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + omo + 
											"$RowAnimation$BatsmanGrp*ACTIVE SET 1 \0");
									
									if(Type.toUpperCase().equalsIgnoreCase("WITHOUT_PHOTO")) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 0 \0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow3$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 0 \0");
										
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow5$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 0 \0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 0 \0");
									}else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 1 \0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow3$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 1 \0");
										
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow5$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 1 \0");
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$BatsmanGrp$PlayerImage1*ACTIVE SET 1 \0");
									}
									
									for(Player hs : mtch.get(j).getSetup().getHomeSquad()) {
										if(bc.getPlayerId() == hs.getPlayerId()) {
											if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + row_id + " SET " + 
														photo_path + mtch.get(j).getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}else {
												if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + mtch.get(j).getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
													this.status = CricketUtil.UNSUCCESSFUL;
												}
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + row_id + " SET " + 
														"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + mtch.get(j).getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}
											
										}
									}
									
									for(Player as : mtch.get(j).getSetup().getAwaySquad()) {
										if(bc.getPlayerId() == as.getPlayerId()) {
											if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + row_id + " SET " + 
														photo_path + mtch.get(j).getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}else {
												if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + mtch.get(j).getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
													this.status = CricketUtil.UNSUCCESSFUL;
												}
												print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage" + row_id + " SET " + 
														"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + mtch.get(j).getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
											}
											
										}
									}
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanFlag" + row_id + " SET " + 
											flag_path + bc.getPlayer().getNationality().toUpperCase() + "\0");
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanName" + row_id + " SET " + 
											bc.getPlayer().getTicker_name() + "\0");
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanName" + row_id + " SET " + 
											bc.getPlayer().getTicker_name() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerRuns" + row_id + " SET " + 
											bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerBalls" + row_id + " SET " + 
											String.valueOf(bc.getBalls()) + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanBoundary" + row_id + " SET " + 
											bc.getFours() + "/" + bc.getSixes() + "\0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanStrikeRate" + row_id + " SET " + 
											bc.getStrikeRate() + "\0");
									
									
									TimeUnit.MILLISECONDS.sleep(2);
									
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + omo + "$RowAnimation$BatsmanGrp"
												+ "$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
									} else {
										print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + omo + "$RowAnimation$BatsmanGrp"
												+ "$TextAll$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
									}
									
									TimeUnit.MILLISECONDS.sleep(2);
									
									if(i == 1 && row_id >= 2) {
										break;
									}else if(i == 2 && row_id >= 4) {
										break;
									}
								}
							}
						}
					}
					
					for(int k = omo+1; k <= max_Strap; k++) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + k + "$RowAnimation$BatsmanGrp*ACTIVE SET 0 \0");
					}
					
					if(i == 1) {
						row_id = 0;
						omo = 1;
					}
					else {
						row_id = 2;
						omo = 4;
					}
					
					if(mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard() != null) {
						
						Collections.sort(mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

						for(BowlingCard boc : mtch.get(j).getMatch().getInning().get(i-1).getBowlingCard()) {
							
							if(boc.getWickets() > 0) {
								row_id = row_id + 1;
								omo = omo + 1;
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + omo + 
										"$RowAnimation$BowlerGrp*ACTIVE SET 1 \0");
								
								if(Type.toUpperCase().equalsIgnoreCase("WITHOUT_PHOTO")) {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 0 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow3$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 0 \0");
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow5$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 0 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 0 \0");
								}else {
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 1 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow3$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 1 \0");
									
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow5$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 1 \0");
									print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow6$RowAnimation$BowlerGrp$PlayerImage2*ACTIVE SET 1 \0");
								}
								
								for(Player hs : mtch.get(j).getSetup().getHomeSquad()) {
									if(boc.getPlayerId() == hs.getPlayerId()) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage" + row_id + " SET " + 
													photo_path + mtch.get(j).getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + mtch.get(j).getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage" + row_id + " SET " + 
													"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + mtch.get(j).getSetup().getHomeTeam().getTeamName3().toUpperCase() + centre_path + hs.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										
									}
								}
								
								for(Player as : mtch.get(j).getSetup().getAwaySquad()) {
									if(boc.getPlayerId() == as.getPlayerId()) {
										if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage" + row_id + " SET " + 
													photo_path + mtch.get(j).getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}else {
											if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + mtch.get(j).getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
												this.status = CricketUtil.UNSUCCESSFUL;
											}
											print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage" + row_id + " SET " + 
													"\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + mtch.get(j).getSetup().getAwayTeam().getTeamName3().toUpperCase() + centre_path + as.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
										}
										
									}
								}
								
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerFlag" + row_id + " SET " + 
										flag_path + boc.getPlayer().getNationality().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerName" + row_id + " SET " + 
										boc.getPlayer().getTicker_name() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerFigures" + row_id + " SET " + 
										boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerOvers" + row_id + " SET " + 
										CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerEconomy" + row_id + " SET " + 
										boc.getEconomyRate() + "\0");
								print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerDots" + row_id + " SET " + 
										boc.getDots() + "\0");
								
								if(i == 1 && row_id >= 2) {
									break;
								}
								else if(i == 2 && row_id >= 4) {
									break;
								}
							}
						}
					}
					
					for(int k = omo+1; k <= max_Strap; k++) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow" + k + "$RowAnimation$BowlerGrp*ACTIVE SET 0 \0");
					}
				}
				
				
				if(mtch.get(j).getMatch().getMatchResult() != null) {
					if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
								CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					}
					else if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
								"MATCH TIED" + "\0");
					}
					else if(mtch.get(j).getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
								mtch.get(j).getMatch().getMatchStatus().toUpperCase() + "\0");
					}
					else if(mtch.get(j).getMatch().getMatchResult().split(",")[2].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
								"MATCH TIED - " + CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
								CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					}
				}
				else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
							CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					
					if(mtch.get(j).getSetup().getTargetType() != null) {
						if(mtch.get(j).getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
									CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
							
						}else if(mtch.get(j).getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
									CricketFunctions.GenerateMatchSummaryStatus(2, mtch.get(j), CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
						}
					}
				}
				
				TimeUnit.MILLISECONDS.sleep(100);
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.100 SummaryIn 1.400 SummaryOffsetIn 0.842 BatPartnershipIn 0.000 "
						+ "BattingRightCardIn 0.000 BatPerformerIn 0.000 BattingCardIn 0.000 BattingCardIn$BatOffsetIn 0.000 BallPerformerIn 0.000 BowlingCardIn 0.000 BowlingCardIn$BallOffsetIn 0.000 BowlingRightCardIn 0.000 BowlingRightCardIn$BallRightOffset 0.000 \0");	
				//			Preview(print_writer, viz_scene, which_graphic_on_screen, "SCORECARD");
			}	
		}
	}
	public void populateBatsmanStyle(PrintWriter print_writer,String viz_scene, int whichInning, int playerId, List<Player> plyr, List<Team> team, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vFlag" + " SET " + "1" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					team.get(plyr.get(playerId - 1).getTeamId() - 1).getTeamName3().toLowerCase() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo01" + " SET " + "" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
					plyr.get(playerId - 1).getNationality().toUpperCase() + "\0");
			
			if(plyr.get(playerId - 1).getBattingStyle() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + 
						CricketFunctions.getbattingstyle(plyr.get(playerId - 1).getBattingStyle(), CricketUtil.FULL, false, false).toUpperCase() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo02" + " SET " + "" + "\0");
			}
			
			if(plyr.get(playerId - 1).getSurname() != null) {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + "" + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " + plyr.get(playerId - 1).getFirstname() + "\0");
			}
			
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428\0");
		TimeUnit.MILLISECONDS.sleep(200);
	}
	public void populateManhattan(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		}else if(whichInning == 0) {
			this.status = "ERROR: Inning is null";
		}else {
			
			int maxRuns = 0,runsIncr = 0;
			double lngth = 0;
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamFirstName" + " SET " + match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHeader" + " SET " + 
					  match.getSetup().getTournament().toUpperCase() + "\0");
			
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamLastName" + " SET " + 
						inn.getBatting_team().getTeamName1().toUpperCase() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path + 
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatTeamIcon3*TEXTURE*IMAGE SET " + outline_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$BatTeamIcon2*TEXTURE*IMAGE SET " + outline_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$LogoInAllGrp$BatTeamLogo$BatTeamIcon*TEXTURE*IMAGE SET " + outline_path +
							inn.getBatting_team().getTeamName3().toLowerCase() + "\0");
					
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
				
			for(int i = 0; i < 5;i++) {
				runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + (5 - i) + " SET " + runsIncr*(i+1) + "\0");
			}
			
			for(int j = 1; j <= match.getSetup().getMaxOvers(); j++) {
//				if(j <= match.getInning().get(whichInning - 1).getFirstPowerplayEndOver()) {
//					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBarColour" + j + " SET " + "P1" + "\0");
//				}
//				else {
//					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBarColour" + j + " SET " + "NP" + "\0");
//				}
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vWkt" + (j) + " SET " + "0" + "\0");
				
				if(j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size()) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Man20$BarGrp$BarAll*FUNCTION*Grid*num_col SET " + (j) + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Man20$BarGrp$Wickets*FUNCTION*Grid*num_col SET " + (j) + "\0");
					
					lngth = ((35 * Integer.valueOf(
							CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns())) / maxRuns);
					
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$Manhattan$BarGrp$BarAll$Bar" + (j) + "*ACTIVE SET 1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$Man20$BarGrp$BarAll*FUNCTION*BarValues*Bar_Value__" + (j) + " SET " + lngth + "\0");
				
					if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vWkt" + (j) + " SET " + Integer.valueOf(
								CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + "\0");
					}
				}
			}
			
			
			if(whichInning == 1) {
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
			    
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
						"PROJECTED SCORE @CRR (" + proj_score_rate[0] +") : " + proj_score_rate[1] + "\0");
			}else {
				if(match.getMatch().getMatchResult() != null) {
					if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
								CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					}
					else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("DRAWN")){
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED" + "\0");
					}
					else if(match.getMatch().getMatchResult().toUpperCase().equalsIgnoreCase("ABANDONED")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + match.getMatch().getMatchStatus().toUpperCase() + "\0");
					}
					else if(match.getMatch().getMatchResult().split(",")[2].toUpperCase().equalsIgnoreCase("SUPER_OVER")){
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "MATCH TIED - " + 
								CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
								CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					}
				}
				else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
							CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
					
					if(match.getSetup().getTargetType() != null) {
						if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
									CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
							
						}else if(match.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
									CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
						}
					}
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.960 \0");
		TimeUnit.SECONDS.sleep(2);
			
	}
	public void populateLtManhattan(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		}else if(whichInning == 0) {
			this.status = "ERROR: Inning is null";
		}else {
			
			int maxRuns = 0,runsIncr = 0;
			double lngth = 0;
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					match.getMatch().getInning().get(whichInning - 1).getBatting_team().getTeamName3().toLowerCase() + "\0");
			
			for(int j = 1; j <= match.getSetup().getMaxOvers(); j++) {
				if(j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size())  {
					//if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEvents()).get(j).getInningNumber() == whichInning) {
						if(Integer.valueOf(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns()) > maxRuns){
							maxRuns = Integer.valueOf(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns()); // 33 runs came off 34th over
						}
						
					 	while (maxRuns % 3 != 0) {     // 3 label in y-axis
					 		maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 3. maxRuns = 35
						}
					//}
				}
			}
			
			
			for(int i =0; i < 3;i++) {
				runsIncr = maxRuns / 3; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tRuns" + (3 - i) + " SET " + runsIncr*(i+1) + "\0");
			}
			
			for(int j = 1; j <= match.getSetup().getMaxOvers(); j++) {
				
				print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vWkt" + (j) + " SET " + "0" + "\0");
				
				if(j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size()) {
//					System.out.println(j + "= " + CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEvents()).get(j).getOverTotalRuns());
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$Mann$Man20$BarGrp$BarAll*FUNCTION*Grid*num_col SET " + (j) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$Mann$Man20$BarGrp$Wickets*FUNCTION*Grid*num_col SET " + (j) + "\0");
//					*BarValues*BarMax SET
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$All$Mann$Man20$BarGrp$BarAll*FUNCTION*BarValues*BarMax SET " + "35.5" + "\0");
					lngth = ((25 *Integer.valueOf(
							CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns())) / maxRuns); // 32 is max value of each bar
//					lngth = (CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEvents()).get(j).getOverTotalRuns()/25);
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$All$Mann$Man20$BarGrp$BarAll*FUNCTION*BarValues*Bar_Value__" + (j) + " SET " + lngth + "\0");
					
//					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBar" + (j) + " SET " + lngth + "\0");
					
					if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vWkt" + (j) + " SET " + Integer.valueOf(
								CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + "\0");
					}
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.120 \0");		
		TimeUnit.SECONDS.sleep(1);
	}
	
	public void populateInningSummary(PrintWriter print_writer,String viz_scene, int whichInning,List<Team> team, MatchAllData match, String broadcaster, Configuration config) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		}else if(whichInning == 0) {
			this.status = "ERROR: Inning is null";
		}else {
			this.status = CricketUtil.SUCCESSFUL;
			int maxRuns = 0,runsIncr = 0;
			long lngth = 0;
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + 
					"INNINGS SUMMARY" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + 
					match.getSetup().getMatchIdent().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + 
					match.getSetup().getTournament().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow1$RowAnimation$TeamNameAll$TossCoin*ACTIVE SET 0 \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTeamLastName1" + " SET " + 
					match.getMatch().getInning().get(whichInning-1).getBatting_team().getTeamName1() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTeamFirstName1" + " SET " + 
					"" + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryTeamLogo1" + " SET " + logo_path + 
					match.getMatch().getInning().get(whichInning-1).getBatting_team().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryTeamLogo2" + " SET " + logo_path + 
					match.getMatch().getInning().get(whichInning-1).getBowling_team().getTeamName3().toLowerCase() + "\0");
			if(match.getMatch().getInning().get(whichInning-1).getTotalWickets() >= 10) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTotalScore1" + " SET " + 
						match.getMatch().getInning().get(whichInning-1).getTotalRuns() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumTotalScore1" + " SET " + 
						match.getMatch().getInning().get(whichInning-1).getTotalRuns() + "-" + match.getMatch().getInning().get(whichInning-1).getTotalWickets() + "\0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumOvers1" + " SET " + 
					CricketFunctions.OverBalls(match.getMatch().getInning().get(whichInning-1).getTotalOvers(), match.getMatch().getInning().get(whichInning-1).getTotalBalls()) + "\0");
			
			if(whichInning == 1 && match.getMatch().getInning().get(0).getInningStatus().equalsIgnoreCase(CricketUtil.START)) {
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
			    
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
						"PROJECTED SCORE @CRR (" + proj_score_rate[0] +") : " + proj_score_rate[1] + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tEquation" + " SET " + 
						CricketFunctions.GenerateMatchSummaryStatus(2, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
			}
			
			
			if(match.getMatch().getInning().get(whichInning-1).getTotalWickets() > 0) {
				if(match.getMatch().getInning().get(whichInning-1).getBattingCard() != null) {
					Collections.sort(match.getMatch().getInning().get(whichInning-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					
					if(!match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
						if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage1" + " SET " + photo_path + 
									team.get(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
									match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}else {
							if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +team.get(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
									match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
								this.status = CricketUtil.UNSUCCESSFUL;
							}
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
									team.get(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
									match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryBatsmanFlag1" + " SET " + 
								flag_path + match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getNationality() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanName1" + " SET " + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerRuns1" + " SET " + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getRuns() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanBoundary1" + " SET " + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getFours() + "/" + match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getSixes() + "\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanStrikeRate1" + " SET " + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getStrikeRate() + "\0");
						TimeUnit.MILLISECONDS.sleep(2);
						
						if(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*GEOM*TEXT SET " + "*" + "\0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumNotOutStar1" + " SET " + 
//									"*" + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
						} else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*GEOM*TEXT SET " + "" + "\0");
//							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumNotOutStar1" + " SET " + 
//									"" + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerBalls1" + " SET " + 
								String.valueOf(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getBalls()) + "\0");
						TimeUnit.MILLISECONDS.sleep(2);
						
					}
				}
				
				if(match.getMatch().getInning().get(whichInning-1).getBowlingCard() != null) {
					Collections.sort(match.getMatch().getInning().get(whichInning-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage1" + " SET " + photo_path + 
								team.get(match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
								match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +team.get(match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
								match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
								team.get(match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
								match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryBowlerFlag1" + " SET " + 
							flag_path + match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getPlayer().getNationality() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerName1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getPlayer().getTicker_name() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerEconomy1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getEconomyRate() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerDots1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getDots() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerFigures1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getWickets() + slashOrDash + match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$TextAll$EconomyGrp$EconomyHead*GEOM*TEXT SET " + "ECON:" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$TextAll$DotsGrp$DotsHead*GEOM*TEXT SET " + "DOTS:" + "\0");
					TimeUnit.MILLISECONDS.sleep(2);
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerOvers1" + " SET " + 
							CricketFunctions.OverBalls(match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getOvers(), match.getMatch().getInning().get(whichInning-1).getBowlingCard().get(0).getBalls()) + "\0");
					
					TimeUnit.MILLISECONDS.sleep(2);
				}
			}else {
				if(match.getMatch().getInning().get(whichInning-1).getBattingCard() != null) {
					Collections.sort(match.getMatch().getInning().get(whichInning-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage1" + " SET " + photo_path + 
								team.get(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +team.get(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBatsmanImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
								team.get(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryBatsmanFlag1" + " SET " + 
							flag_path + match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getNationality() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanName1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getPlayer().getTicker_name() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerRuns1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getRuns() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanBoundary1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getFours() + "/" + match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getSixes() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBatsmanStrikeRate1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getStrikeRate() + "\0");
					
					TimeUnit.MILLISECONDS.sleep(2);
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerBalls1" + " SET " + 
							String.valueOf(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getBalls()) + "\0");
					
					TimeUnit.MILLISECONDS.sleep(2);
					if(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*GEOM*TEXT SET " + "*" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumNotOutStar1" + " SET " + 
//								"*" + "\0");
						TimeUnit.MILLISECONDS.sleep(2);
					} else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BatsmanGrp$TextAll$ScoreGrp$NotOutStar*GEOM*TEXT SET " + "" + "\0");
//						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumNotOutStar1" + " SET " + 
//								"" + "\0");
						TimeUnit.MILLISECONDS.sleep(2);
					}
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage1" + " SET " + photo_path + 
								team.get(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path +team.get(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSumBowlerImage1" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + 
								team.get(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getPlayer().getTeamId() - 1).getTeamName3()  + centre_path + 
								match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgSummaryBowlerFlag1" + " SET " + 
							flag_path + match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getPlayer().getNationality() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerName1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getPlayer().getTicker_name() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerDots1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getFours() + "/" + match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getSixes() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerFigures1" + " SET " + 
							match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getStrikeRate() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$TextAll$EconomyGrp$EconomyHead*GEOM*TEXT SET " + "4s/6s:" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$BatDataGrp$SumRow2$RowAnimation$BowlerGrp$TextAll$DotsGrp$DotsHead*GEOM*TEXT SET " + "S/R:" + "\0");
					TimeUnit.MILLISECONDS.sleep(2);
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumPlayerOvers1" + " SET " + 
							String.valueOf(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getBalls()) + "\0");
					
						TimeUnit.MILLISECONDS.sleep(2);
						if(match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerEconomy1" + " SET " + 
									match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getRuns() + "*" + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
						} else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumBowlerEconomy1" + " SET " + 
									match.getMatch().getInning().get(whichInning-1).getBattingCard().get(1).getRuns() + "\0");
							TimeUnit.MILLISECONDS.sleep(2);
						}
					
					TimeUnit.MILLISECONDS.sleep(2);
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
		 		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$Manhattan$PlayerNameGrp$Row" + (5 - i) + "$RowAni$Runs*GEOM*TEXT SET " + runsIncr*(i+1) + "\0");
			}
			
			for(int j = 0; j <= match.getSetup().getMaxOvers(); j++) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$Manhattan$BarGrp$BarAll$Bar" + (j) + "*ACTIVE SET 0" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$Manhattan$BarGrp$Wickets$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
					
				if(j < CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).size()) {
					lngth = ((35 *Integer.valueOf(
							CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalRuns())) / maxRuns); // 32 is max value of each bar
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$Manhattan$BarGrp$BarAll$Bar" + (j) + "*ACTIVE SET 1" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$Manhattan$BarGrp$BarAll*FUNCTION*BarValues*Bar_Value__" + (j) + " SET " + lngth + "\0");
				
					if(CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$Manhattan$BarGrp$Wickets$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + Integer.valueOf(
								CricketFunctions.getOverByOverData(match, whichInning,"MANHATTAN" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
					}
					else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$Manhattan$BarGrp$Wickets$Wkt" + (j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
					}
				
				}
				else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$SummaryAll$SummaryData$Manhattan$BarGrp$BarAll$Bar" + (j) + "*ACTIVE SET 0" + "\0");
				}
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 4.000 In$DataIn 4.000 \0");
		TimeUnit.SECONDS.sleep(2);
			
	}
	
	public void populateWorm(PrintWriter print_writer,String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		}else if(whichInning == 0) {
			this.status = "ERROR: Inning is null";
		}
		else {
			
			String teamname = "";
			int maxRuns = 0,runsIncr = 0,row_id = 0;
			double Lngth = 0;
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + 
					"" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + 
					"COMPARISON" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + 
					match.getSetup().getMatchIdent() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + 
					CricketFunctions.GenerateMatchSummaryStatus(whichInning, match, CricketUtil.FULL, "|",broadcaster,true).getTargetOrResult().toUpperCase() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo1" + " SET " + 
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo2" + " SET " + 
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Row2$RowAnimation$TeamGrp1$Band*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Row2$RowAnimation$TeamGrp2$Band*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + " \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph1*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + " \0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph2*TEXTURE*IMAGE SET " + team_color +
					match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + " \0");
			
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
					teamname = match.getSetup().getHomeTeam().getTeamName2().toUpperCase();
					
				} else {
					teamname = match.getSetup().getAwayTeam().getTeamName2().toUpperCase();
				}
				
				for(int k = 0; k < 5; k++) {           // For Y-Axis Value 
					runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36	
				 	print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$PlayerNameGrp$Row" + (5 - k) + 
				 			"$RowAni$Runs*GEOM*TEXT SET " + runsIncr *  (k + 1) + "\0");
				}
				
				row_id = row_id + 1;
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + row_id + " SET " + 
						teamname + "\0");
				
				if(match.getMatch().getInning().get(inn_count-1).getTotalWickets() >= 10 ) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + row_id + " SET " + 
							match.getMatch().getInning().get(inn_count-1).getTotalRuns() + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamScore" + row_id + " SET " + 
							match.getMatch().getInning().get(inn_count-1).getTotalRuns() + "-" + match.getMatch().getInning().get(inn_count-1).getTotalWickets() + "\0");
				}
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamOvers" + row_id + " SET " + 
						CricketFunctions.OverBalls(match.getMatch().getInning().get(inn_count-1).getTotalOvers(), match.getMatch().getInning().get(inn_count-1).getTotalBalls()) + "\0");
				
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXFit SET 1 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXFit SET 1 \0");

//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXOffset SET -12.0 \0");
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXOffset SET -12.0 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataYOffset SET 4.0 \0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataYOffset SET 4.0 \0");
				
//				Lngth =  (80.62 / maxRuns); // 100 is max value of each bar
				Lngth =  (119.6 / maxRuns);
//				System.out.println("maxRuns = " + maxRuns);
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph1*GEOM*DataXScale" + " SET " + "0.98" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph2*GEOM*DataXScale" + " SET " + "0.98" + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vDataScaleY" + " SET " + Lngth + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph" + inn_count + 
						"*GEOM*DataY SET " + cumm_runs.replaceFirst("0,0,", "") + " \0");
//				System.out.println(cumm_runs);
//				System.out.println(cumm_runs.replaceFirst("0,0,", ""));
				if(inn_count == 1) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Row2$RowAnimation$TeamGrp2*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 0 \0");
				}
				else {						
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Row2$RowAnimation$TeamGrp2*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph2*ACTIVE SET 1 \0");
				}
				
				for (int j = 1; j <= match.getSetup().getMaxOvers(); j++) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph" + inn_count +  "$Wkt" +
							(j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
					
					if(j < CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).size()) {
						if(CricketFunctions.getOverByOverData(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets() > 0) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph" + inn_count +  "$Wkt" +
									(j) + "*FUNCTION*Omo*vis_con SET " + Integer.valueOf(CricketFunctions.getOverByOverData
											(match, inn_count,"WORM" ,match.getEventFile().getEvents()).get(j).getOverTotalWickets()) + " \0");
						}
						else {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$worm$worm$Man20$InningsGraphGrp$InningsGraph" + inn_count +  "$Wkt" +
									(j) + "*FUNCTION*Omo*vis_con SET " + "0" + " \0");
						}
					}
				}
			}	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.640 In$DataIn 3.640 \0");
		TimeUnit.SECONDS.sleep(2);
			
	}
	public void populateSchedule(PrintWriter print_writer,String viz_scene,List<Fixture> fixture,List<Team> team,MatchAllData match ,String broadcaster) throws ParseException {
		
		int row_id = 0,omo_num=0;
		String Date = "",cont_name="";
		Calendar cal = Calendar.getInstance();
		Date =  new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader1" + " SET " + " " +"\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tHeader2" + " SET " + "SCHEDULE" + "\0");
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSumSubHeader" + " SET " + match.getSetup().getTournament() + "\0");
		
		for(int i=0;i < fixture.size();i++ ) {
			row_id = row_id + 1;
			if(fixture.get(i).getDate().equalsIgnoreCase(Date)) {
				omo_num=1;
				cont_name="$Highlight";		
			}else {
				omo_num=0;
				cont_name="$Dehighlight";
			}

			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
			if(fixture.get(i).getMatchnumber() % 2 == 0) {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
						"$RowAnimation$RowOmo" + cont_name + "$DateAll$TimeOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
						"$RowAnimation$RowOmo" + cont_name + "$DateAll$TimeOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
			}
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo" + cont_name + "$DateAll$DateText*GEOM*TEXT SET " + fixture.get(i).getDate() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo" + cont_name + "$TextAll$NameAll$TeamName1*GEOM*TEXT SET " + team.get(fixture.get(i).getHometeamid()-1).getTeamName1().toUpperCase() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
					"$RowAnimation$RowOmo" + cont_name + "$TextAll$NameAll$TeamName2*GEOM*TEXT SET " + team.get(fixture.get(i).getAwayteamid()-1).getTeamName1().toUpperCase() + "\0");
			
			if(fixture.get(i).getWinnerteam() != null) {
				if(fixture.get(i).getWinnerteam().toUpperCase().equalsIgnoreCase(team.get(fixture.get(i).getHometeamid()-1).getTeamName4().toUpperCase())) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
							"$RowAnimation$RowOmo" + cont_name + "$TextAll$ResultOmo*FUNCTION*Omo*vis_con SET " + "1" + "\0");
				}else if(fixture.get(i).getWinnerteam().toUpperCase().equalsIgnoreCase(team.get(fixture.get(i).getAwayteamid()-1).getTeamName4().toUpperCase())) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
							"$RowAnimation$RowOmo" + cont_name + "$TextAll$ResultOmo*FUNCTION*Omo*vis_con SET " + "2" + "\0");
				}
			}	
			else {
				print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$TeamLineup_Both$TeamsAll$TeamDataData$TeamAll1$TeamAll1$Row" + row_id + 
						"$RowAnimation$RowOmo" + cont_name + "$TextAll$ResultOmo*FUNCTION*Omo*vis_con SET " + "0" + "\0");
			}
		}
		
		print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInfo" + " SET " + "TOP FOUR TEAMS QUALIFY FOR THE SEMIS" + "\0");
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 2.474 In$DataIn 1.750 \0");
		
	}
	
	public void populateMiniBattingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: mini batting card inning is null";
		} else {

			int row_id = 0, omo_num = 0,batting_size=0;
			String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBatting_team().getTeamName1() + "\0");
					
					Collections.sort(inn.getBattingCard());
					for (BattingCard bc : inn.getBattingCard()) {
						
						row_id = row_id + 1;
						
						switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.OUT:
								omo_num = 0;
								cont_name = "$Dehighlight";
								batting_size = batting_size + 1;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
								break;
							case CricketUtil.NOT_OUT:
								omo_num = 1;
								cont_name = "$Highlight";
								batting_size = batting_size + 1;
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + batting_size + "\0");
								break;
							}
						
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_id + " SET " + String.valueOf(omo_num) + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_id + " SET " + bc.getPlayer().getTicker_name() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_id + " SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_id + " SET " + String.valueOf(bc.getBalls()) + "\0");
							
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id
										+ "$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_id
										+ "$RowAnimation$BatOmo" + cont_name + "$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
							}
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.180 In$BatDataIn 1.180 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
	}
	public void populateMiniBowlingcard(PrintWriter print_writer, String viz_scene, int whichInning, MatchAllData match, String broadcaster) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: Bowlingcard's inning is null";
		} else {
			
			int row_id = 0, omo_num = 0,i=0;
			//String cont_name= "";
			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + inn.getBowling_team().getTeamName1() + "\0");
					
					for (BowlingCard boc : inn.getBowlingCard()) {
						if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
							i=i+1;
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + i + "\0");
						}
						switch (boc.getStatus().toUpperCase()) {
						case (CricketUtil.OTHER + CricketUtil.BOWLER):
							omo_num = 0;
							//cont_name = "$Dehighlight";
							break;
						case (CricketUtil.LAST + CricketUtil.BOWLER):
							omo_num = 0;
							//cont_name = "$Dehighlight";
							break;
						case (CricketUtil.CURRENT + CricketUtil.BOWLER):
							omo_num = 1;
							//cont_name = "$Highlight";
							break;
						}
						
						row_id = row_id + 1;
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_id + " SET " + String.valueOf(omo_num) + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_id + " SET " + boc.getPlayer().getTicker_name() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerFigure" + row_id + " SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerOvers" + row_id + " SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + "\0");
						
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.180 In$BatDataIn 1.180 \0");
			TimeUnit.MILLISECONDS.sleep(200);
		}
			
	}
	public void populateThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster, Configuration config) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
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
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tSubHead" + " SET " + 
					"THIS SERIES" + "\0");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getHomeTeam().getTeamName3().toLowerCase() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
								this_series.get(i).getPlayer().getNationality().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
								match.getSetup().getAwayTeam().getTeamName3().toLowerCase() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgFlag" + " SET " + flag_path + 
								this_series.get(i).getPlayer().getNationality().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerFirstName" + " SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerLastName" + " SET " +
							this_series.get(i).getPlayer().getSurname() + "\0");
					
					
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getRuns() + "\0");
						
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "S/R" + "\0");			
						if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							strike_rate = this_series.get(i).getRuns() * 100;
							strike_rate = strike_rate/this_series.get(i).getBallsFaced();
							DecimalFormat df = new DecimalFormat("0.0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
						}
						 
						for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
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
					case CricketUtil.BOWLER:
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getMatches() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getWickets() + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "ECON." + "\0");
						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
						}else {
							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df = new DecimalFormat("0.00");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(economy_rate) + "\0");
						}
						
						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead4" + " SET " + "BEST" + "\0");
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
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428\0");
		}
	}
	
	public void populatePlayerOfTheTournament(PrintWriter print_writer, String viz_scene,List<Player> plyer,List<Tournament> this_series,MatchAllData match, String broadcaster, Configuration config) {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			this.status = CricketUtil.SUCCESSFUL;
			double strike_rate = 0;
		
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tCareer" + " SET " + "PLAYER OF" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tPlayerHand" + " SET " + "THE TOURNAMENT" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$NameBands$INDIA*ACTIVE SET 0\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET 0\0");
//			Player plyr = getPlayerFromMatchData(59, match);
			for(Player plyr : plyer) {
				if(plyr.getPlayerId() == 59) {
					if(plyr.getInstagramHandle() == null && plyr.getTwitterHandle() == null) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia*ACTIVE SET 0 \0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia*ACTIVE SET 1 \0");
						
						if(plyr.getInstagramHandle() == null && plyr.getTwitterHandle() != null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Instagram*ACTIVE SET 0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Twitter*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTwitterText" + " SET " + plyr.getTwitterHandle() + "\0");
							
						}else if(plyr.getInstagramHandle() != null && plyr.getTwitterHandle() == null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Instagram*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Twitter*ACTIVE SET 0 \0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInstagramText" + " SET " + plyr.getInstagramHandle() + "\0");
							
						}else if(plyr.getInstagramHandle() != null && plyr.getTwitterHandle() != null) {
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Instagram*ACTIVE SET 1 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$SocialMedia$Twitter*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTwitterText" + " SET " + plyr.getTwitterHandle() + "\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tInstagramText" + " SET " + plyr.getInstagramHandle() + "\0");
						}
					}
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + photo_path + "FALCONS" + 
								centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + "FALCONS" + 
								centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "\\\\\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + "FALCONS" + 
								centre_path + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
					}
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tAgeValue" + " SET " + "" + "\0");
				}
			}
			
			
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamName" + " SET " + "FALCONS" + "\0");
			
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main$AllGrp$All$AllDataGrp$Data$NameBands$INDIA*TEXTURE*IMAGE SET "+ flag_path + 
//					plyr.getNationality() + " \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + logo_path +
				"falcons" + "\0");
			
			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == 59) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + 
							this_series.get(i).getPlayer().getFirstname() + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " +
							this_series.get(i).getPlayer().getSurname() + "\0");
					
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead1" + " SET " + "RUNS" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1" + " SET " + this_series.get(i).getRuns() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead2" + " SET " + "WICKETS" + "\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2" + " SET " + this_series.get(i).getWickets() + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatHead3" + " SET " + "BAT S/R" + "\0");			
					if(this_series.get(i).getBallsFaced() == 0 || this_series.get(i).getRuns()== 0) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + "-" + "\0");
					}else {
						strike_rate = this_series.get(i).getRuns() * 100;
						strike_rate = strike_rate/this_series.get(i).getBallsFaced();
						DecimalFormat df = new DecimalFormat("0.0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3" + " SET " + df.format(strike_rate) + "\0");
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.660 DataIn 3.000 LogoIn 2.100 \0");
		}
	}
	public void populateFFThisSeries(PrintWriter print_writer, String viz_scene,int Playerid,String TypeofProfile,List<Tournament> this_series,MatchAllData match, String broadcaster) throws InterruptedException {
		
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {

			double strike_rate = 0 , economy_rate=0;
			int omo_num = 0;
			String cont_name = "";
			
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$Career*GEOM*TEXT SET " + "THIS SERIES" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$HowOut*ACTIVE SET " + "0" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$SocialMedia*ACTIVE SET " + "0" + "\0");

			for(int i = 0; i <= this_series.size() - 1 ; i++) {
				if(this_series.get(i).getPlayerId() == Playerid) {
					if(this_series.get(i).getPlayer().getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "C:\\\\Images\\\\NEPAL_T20\\\\Photos\\\\" 
								+ match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET "+ "IMAGE*/Default/Nepal_T20/Logos/" + 
								match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
								match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgImage" + " SET " + "C:\\\\Images\\\\NEPAL_T20\\\\Photos\\\\" 
								+ match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\\\\" + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/Nepal_T20/Logos/" + 
								match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$Teamame*GEOM*TEXT SET " + 
									match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$FistName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$NameBands$NameAll$LastName*GEOM*TEXT SET " + 
							this_series.get(i).getPlayer().getSurname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tFistName" + " SET " + 
							this_series.get(i).getPlayer().getFirstname().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tLastName" + " SET " + 
							this_series.get(i).getPlayer().getSurname().toUpperCase() + "\0");
					
					switch(TypeofProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						
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
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "RUNS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getRuns() + "\0");
						
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "STRIKE RATE" + "\0");
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
					case CricketUtil.BOWLER:
						
						cont_name = "$Dehighlight";
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo*FUNCTION*Omo*vis_con SET " + omo_num + " \0");

						if(this_series.get(i).getPlayer().getBowlingStyle() != null) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + 
									CricketFunctions.getbowlingstyle(this_series.get(i).getPlayer().getBowlingStyle().toUpperCase())+ "\0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$HandAndAge$PlayerHand*GEOM*TEXT SET " + " " + "\0");
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "MATCHES" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row1$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0");

						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "WICKETS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row2$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatValue*GEOM*TEXT SET " + this_series.get(i).getWickets() + "\0");
																
						
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
								"$StatGrpAll$StatHead*GEOM*TEXT SET " + "ECONOMY" + "\0");
						if(this_series.get(i).getBallsBowled() == 0 || this_series.get(i).getRunsConceded() == 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + "-" + "\0");
						}else {
							economy_rate = (this_series.get(i).getRunsConceded()*1.00) /this_series.get(i).getBallsBowled();
							economy_rate = economy_rate * 6;
							DecimalFormat df_b = new DecimalFormat("0.00");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$PlayerProfile$Data$ProfileData$RowAll$Row3$RowAnimation$RowOmo" + cont_name + 
									"$StatGrpAll$StatValue*GEOM*TEXT SET " + df_b.format(economy_rate) + "\0");
						}
						break;
					}
					
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataIn 1.700 \0");
			

			}
	}
	
	public void populateFFstats(PrintWriter print_writer,String viz_scene,String StatType,List<Tournament> tournament,List<Team> team ,MatchAllData match, String broadcaster) {
		
		int row_no=0;
		int omo_num = 0;
		String cont_name = "";
		switch(StatType.toUpperCase()) {
		case "MOST_RUNS":
			//int omo_num = 0;
			//String cont_name = "";
			Collections.sort(tournament,new CricketFunctions.BatsmenMostRunComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");

			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/NEPAL_T20/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "RUNS" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament() + "\0");
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
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
		case "MOST_WICKETS":
			
			Collections.sort(tournament,new CricketFunctions.BowlerWicketsComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/NEPAL_T20/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "WICKETS" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament() + "\0");
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
			 							+ cont_name + "$StatHead*GEOM*TEXT SET " + tournament.get(i).getPlayer().getFull_name().toUpperCase() + "\0");
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
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
		case "MOST_FOURS":
			Collections.sort(tournament,new CricketFunctions.BatsmanFoursComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/NEPAL_T20/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "FOURS" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament() + "\0");
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
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
			break;
			
		case "MOST_SIXES":
			Collections.sort(tournament,new CricketFunctions.BatsmanSixesComparator());
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + " " + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/NEPAL_T20/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "MOST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "SIXES" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament() + "\0");
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
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			
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
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "lgTeamLogo" + " SET " + "IMAGE*/Default/NEPAL_T20/Logos/" + "TLogo" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$FirstName*GEOM*TEXT SET " + "HIGHEST" + "\0");
	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$TeamNameGrp$LastName*GEOM*TEXT SET " + "INDIVIDUAL SCORE" + "\0");

	 		print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$Partnership$Data$Header$SubHeader*GEOM*TEXT SET " + match.getSetup().getTournament() + "\0");
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
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 3.543 In$DataaaIn 1.356 In$PartDataIn 1.170 In$PartDataIn$DataIn 1.170 \0");
			break;
		}
	}
	public void populateLineup(PrintWriter print_writer,String viz_scene,int team_id,String icon_data,CricketService cricketService,List<Team> team,List<Player> plyr,MatchAllData match, 
			String broadcaster, Configuration config) throws InterruptedException {
		
		int row_id = 0;
		this.status = CricketUtil.SUCCESSFUL;
		print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select*FUNCTION*Omo*vis_con SET 2 \0");
		print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select*FUNCTION*Omo*vis_con SET 4 \0");
		print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select*FUNCTION*Omo*vis_con SET 4 \0");
		
		print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$LineUp$LTLogoGRP$LogoIn$LLC_LogoGrp$Basegrp$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
				team.get(team_id - 1).getTeamName3().toLowerCase() +" \0");
		print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$LineUp$LTLogoGRP$LogoIn$LLC_LogoGrp$img_Badges*TEXTURE*IMAGE SET " + logo_path +
				team.get(team_id - 1).getTeamName3().toLowerCase() + "\0");
		print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$LineUp$LTLogoGRP$LogoIn$img_Base1*TEXTURE*IMAGE SET " + base_path + "1/" + 
				team.get(team_id - 1).getTeamName3().toLowerCase() +" \0");
		print_writer.println("-1 RENDERER*TREE*$LT$ALL_LT_LOGOGRP$Select$LineUp$LTLogoGRP$LogoIn$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
				team.get(team_id - 1).getTeamName3().toLowerCase() +" \0");
		print_writer.println("-1 RENDERER*TREE*$LT$All$BaseAll$Select$LineUp$TopBand$img_Base2*TEXTURE*IMAGE SET " + base_path + "2/" + 
				team.get(team_id - 1).getTeamName3().toLowerCase() +" \0");
	
		switch(icon_data.toUpperCase()) {
		case "ICON":
			if(team_id == match.getSetup().getHomeTeamId()) {
				for(Player hs : match.getSetup().getHomeSquad()) {
					row_id = row_id + 1;
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$Pattern$img_Base2*TEXTURE*IMAGE SET " + base_path + 
							"2/" + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Base2*TEXTURE*IMAGE SET " + base_path + 
							"2/" + match.getSetup().getHomeTeam().getTeamName3().toLowerCase() +" \0");
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Player*TEXTURE*IMAGE SET "+ photo_path 
								+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Player*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path 
								+ match.getSetup().getHomeTeam().getTeamName3().toUpperCase() + "\\" + hs.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$Details$img_Text$txt_Name*GEOM*TEXT SET "+ 
							hs.getTicker_name() + " \0");
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
							"$Dataall$Select_Star*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
					
					if(hs.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ CricketUtil.BATSMAN + " \0");
					}else if(hs.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ CricketUtil.BOWLER + " \0");
					}else if(hs.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ "ALL-ROUNDER" + " \0");
					}
					
					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ "KEEPER" + " \0");
					}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ "KEEPER" + " \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
					}	
				}
			}else if(team_id == match.getSetup().getAwayTeamId()) {
				for(Player as : match.getSetup().getAwaySquad()) {
					row_id = row_id + 1;
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$Pattern$img_Base2*TEXTURE*IMAGE SET " + base_path + 
							"2/" + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Base2*TEXTURE*IMAGE SET " + base_path + 
							"2/" + match.getSetup().getAwayTeam().getTeamName3().toLowerCase() +" \0");
					if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Player*TEXTURE*IMAGE SET "+ photo_path 
								+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
					}else {
						if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
							this.status = CricketUtil.UNSUCCESSFUL;
						}
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Player*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path 
								+ match.getSetup().getAwayTeam().getTeamName3().toUpperCase() + "\\" + as.getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
					}
					
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$Details$img_Text$txt_Name*GEOM*TEXT SET "+ 
							as.getTicker_name() + " \0");
					
					print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
							"$Dataall$Select_Star*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
					
					if(as.getRole().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ CricketUtil.BATSMAN + " \0");
					}else if(as.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ CricketUtil.BOWLER + " \0");
					}else if(as.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ "ALL-ROUNDER" + " \0");
					}
					
					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ "KEEPER" + " \0");
					}else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ "KEEPER" + " \0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
								"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
					}
				}
			}
			break;
		case "BATTING_CARD":
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getBattingTeamId() == team_id) {
					Collections.sort(inn.getBattingCard());
					for(BattingCard bc : inn.getBattingCard()) {
						row_id = row_id + 1;
						
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$Pattern$img_Base2*TEXTURE*IMAGE SET " + base_path + 
								"2/" + inn.getBatting_team().getTeamName3().toLowerCase() +" \0");
						print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Base2*TEXTURE*IMAGE SET " + base_path + 
								"2/" + inn.getBatting_team().getTeamName3().toLowerCase() +" \0");
						
						if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
							for(Player hs : match.getSetup().getHomeSquad()) {
								if(hs.getPlayerId() == bc.getPlayerId()) {
									if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
										print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
												"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
									}else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
										print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
												"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
												"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
									}
								}
							}
						}else if(inn.getBattingTeamId() == match.getSetup().getAwayTeamId()) {
							for(Player as : match.getSetup().getAwaySquad()) {
								if(as.getPlayerId() == bc.getPlayerId()) {
									if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
										print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
												"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
									}else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
										print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
												"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
									}else {
										print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
												"$Dataall$Details$SelectCaptain*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
									}
								}
							}
						}
						
						if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Player*TEXTURE*IMAGE SET "+ photo_path 
										+ inn.getBatting_team().getTeamName3().toUpperCase() + "\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + "\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Player*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path 
										+ inn.getBatting_team().getTeamName3().toUpperCase() + "\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$Details$img_Text$txt_Name*GEOM*TEXT SET "+ 
									bc.getPlayer().getTicker_name() + " \0");
							
							if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.START)) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
										"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ "IN AT " + row_id + " \0");
							}else if(inn.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
										"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ "DNB" + " \0");
							}
							
						}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT) || bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
							if(config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST")) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Player*TEXTURE*IMAGE SET "+ photo_path 
										+ inn.getBatting_team().getTeamName3().toUpperCase() + "\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
							}else {
								if(!new File("\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path + inn.getBatting_team().getTeamName3().toUpperCase() + "\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION).exists()) {
									this.status = CricketUtil.UNSUCCESSFUL;
								}
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$img_Player*TEXTURE*IMAGE SET "+ "\\\\"+config.getPrimaryIpAddress()+"\\\\"+local_photo_path 
										+ inn.getBatting_team().getTeamName3().toUpperCase() + "\\" + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + " \0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + "$Dataall$Details$img_Text$txt_Name*GEOM*TEXT SET "+ 
									bc.getPlayer().getTicker_name() + " \0");
							
							
							if(CricketFunctions.checkImpactPlayer(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
										"$Dataall$Select_Star*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
							}else if(CricketFunctions.checkImpactPlayerBowler(match.getEventFile().getEvents(), inn.getInningNumber(), bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
										"$Dataall$Select_Star*FUNCTION*Omo*vis_con SET "+ "1" + " \0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
										"$Dataall$Select_Star*FUNCTION*Omo*vis_con SET "+ "0" + " \0");
							}
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
										"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ bc.getRuns() + "* (" + bc.getBalls() + ")" + " \0");
							}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								print_writer.println("-1 RENDERER*TREE*$LT$All$DataAll$Select$LineUp$BottomGrp$LineUp_ALL$Player" + row_id + 
										"$Dataall$Details$txt_Role*GEOM*TEXT SET "+ bc.getRuns() + " (" + bc.getBalls() + ")" + " \0");
							}
							
						}
					}
				}
			}
			
			break;
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg LT$LTLogoIn 2.000 LT$LTBaseIn 2.000 LT$LTDataIn 2.000\0");
		TimeUnit.SECONDS.sleep(1);
	}
	
	public void populateBatGriff(PrintWriter print_writer,String viz_scene,int whichinning, int PlayerId,List<MatchAllData> all_matches,List<Player> plyr,List<Team> team,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException 
	{
		int row_no = 0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "FAIR_BREAK":
			
			boolean player_check = false;
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + 
					plyr.get(PlayerId - 1).getFirstname() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + 
					plyr.get(PlayerId - 1).getSurname()  + "\0");
			
			for(MatchAllData mtch : all_matches) {
				player_check = false;
				if(!mtch.getMatch().getMatchFileName().equalsIgnoreCase(match.getMatch().getMatchFileName())) {
					System.out.println(mtch.getMatch().getMatchFileName());
					if(plyr.get(PlayerId - 1).getTeamId() == mtch.getSetup().getHomeTeamId() || plyr.get(PlayerId - 1).getTeamId() == mtch.getSetup().getAwayTeamId()) {
						for(Inning inn : mtch.getMatch().getInning())
						{
							
								if(inn.getBattingCard() != null && inn.getBattingCard().size() > 0) {
									
									for(BattingCard bc : inn.getBattingCard())
									{
										if(bc.getPlayerId() == PlayerId) {
//											bat_griff_data.add(bc);
											player_check = true;
											row_no = row_no + 1;
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_no + "\0");
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_no + " SET " + "0" + "\0");
											
											print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_no + " SET " + 
													"v " + team.get(inn.getBowlingTeamId() - 1).getTeamName1().toUpperCase() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
													+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
											if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_no + " SET " + "" + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_no + " SET " + "DNB" + "\0");
												
											}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_no + " SET " + bc.getRuns() + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_no + " SET " + bc.getBalls() + "\0");
											}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
												print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
														+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_no + " SET " + bc.getRuns() + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_no + " SET " + bc.getBalls() + "\0");
												
											}
											
											break;
										}
									}
								}
						}	
						if(player_check != true) {
							row_no = row_no + 1;
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_no + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_no + " SET " + "0" + "\0");
							
							if(plyr.get(PlayerId - 1).getTeamId() == mtch.getSetup().getHomeTeamId()) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_no + " SET " + 
										"v " + mtch.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_no + " SET " + 
										"v " + mtch.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_no + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_no + " SET " + "DNP" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
									+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
						}
					}
				}
			}
			
			if(plyr.get(PlayerId - 1).getTeamId() == match.getSetup().getHomeTeamId() || plyr.get(PlayerId - 1).getTeamId() == match.getSetup().getAwayTeamId()) {
				for(Inning inn : match.getMatch().getInning())
				{
					if(inn.getBattingCard() != null && inn.getBattingCard().size() > 0) {
						
						for(BattingCard bc : inn.getBattingCard())
						{
							if(bc.getPlayerId() == PlayerId) {
//									bat_griff_data.add(bc);
								player_check = true;
								row_no = row_no + 1;
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_no + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_no + " SET " + "0" + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_no + " SET " + 
										"v " + team.get(inn.getBowlingTeamId() - 1).getTeamName1().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
										+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_no + " SET " + "" + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_no + " SET " + "DNB" + "\0");
									
								}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_no + " SET " + bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_no + " SET " + bc.getBalls() + "\0");
								}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
											+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_no + " SET " + bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_no + " SET " + bc.getBalls() + "\0");
									
								}
								
								break;
							}
						}
					}
				}	
				if(player_check != true) {
					row_no = row_no + 1;
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_no + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatOmo" + row_no + " SET " + "0" + "\0");
					
					if(plyr.get(PlayerId - 1).getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_no + " SET " + 
								"v " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerName" + row_no + " SET " + 
								"v " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerRuns" + row_no + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatPlayerBalls" + row_no + " SET " + "DNP" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
							+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
			}
			
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.180 In$BatDataIn 1.180 \0");
			TimeUnit.MILLISECONDS.sleep(200);
			break;
		}
	}
	public void populateBallGriff(PrintWriter print_writer,String viz_scene,int whichinning, int PlayerId,List<MatchAllData> all_matches,List<Player> plyr,List<Team> team,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException 
	{
		int row_no = 0;
		switch (session_selected_broadcaster.toUpperCase()) {
		case "FAIR_BREAK":
			
			boolean player_check = false;
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamFirstName" + " SET " + 
					plyr.get(PlayerId - 1).getFirstname() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBatBattingTeamLastName" + " SET " + 
					plyr.get(PlayerId - 1).getSurname()  + "\0");
			
			for(MatchAllData mtch : all_matches) {
				player_check = false;
				if(!mtch.getMatch().getMatchFileName().equalsIgnoreCase(match.getMatch().getMatchFileName())) {
					if(plyr.get(PlayerId - 1).getTeamId() == mtch.getSetup().getHomeTeamId() || plyr.get(PlayerId - 1).getTeamId() == mtch.getSetup().getAwayTeamId()) {
						for(Inning inn : mtch.getMatch().getInning())
						{
							if(inn.getBowlingCard() != null && inn.getBowlingCard().size() > 0) {
								
								for(BowlingCard boc : inn.getBowlingCard())
								{
									if(boc.getPlayerId() == PlayerId) {
//										bat_griff_data.add(bc);
										player_check = true;
										row_no = row_no + 1;
										
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_no + "\0");
										
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_no + " SET " + "0" + "\0");
										
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_no + " SET " + 
												"v " + team.get(inn.getBattingTeamId() - 1).getTeamName1().toUpperCase() + "\0");
									
//										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
//												+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerFigure" + row_no + " SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerOvers" + row_no + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
											
										
										
										break;
									}
								}
							}
						}
						for(Inning inn : mtch.getMatch().getInning())
						{
							if(player_check != true) {
								for(BattingCard bc : inn.getBattingCard())
								{
									if(bc.getPlayerId() == PlayerId) {
										player_check = true;
										row_no = row_no + 1;
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_no + " SET " + 
												"v " + team.get(inn.getBowlingTeamId() - 1).getTeamName1().toUpperCase() + "\0");
									
//										print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
//												+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerFigure" + row_no + " SET " + "" + "\0");
										print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerOvers" + row_no + " SET " + "DNB" + "\0");
									}
								}
							}
						}
						
						
						if(player_check != true) {
							row_no = row_no + 1;
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_no + "\0");
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_no + " SET " + "0" + "\0");
							
							if(plyr.get(PlayerId - 1).getTeamId() == mtch.getSetup().getHomeTeamId()) {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_no + " SET " + 
										"v " + mtch.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_no + " SET " + 
										"v " + mtch.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerFigure" + row_no + " SET " + "" + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerOvers" + row_no + " SET " + "DNP" + "\0");
//							print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
//									+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
						}
					}
				}
			}
			
			
			
			if(plyr.get(PlayerId - 1).getTeamId() == match.getSetup().getHomeTeamId() || plyr.get(PlayerId - 1).getTeamId() == match.getSetup().getAwayTeamId()) {
				for(Inning inn : match.getMatch().getInning())
				{
					if(inn.getBowlingCard() != null && inn.getBowlingCard().size() > 0) {
						
						for(BowlingCard boc : inn.getBowlingCard())
						{
							if(boc.getPlayerId() == PlayerId) {
//								bat_griff_data.add(bc);
								player_check = true;
								row_no = row_no + 1;
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_no + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_no + " SET " + "0" + "\0");
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_no + " SET " + 
										"v " + team.get(inn.getBattingTeamId() - 1).getTeamName1().toUpperCase() + "\0");
							
//								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
//										+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerFigure" + row_no + " SET " + boc.getWickets() + "-" + boc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerOvers" + row_no + " SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
									
								
								
								break;
							}
						}
					}
				}
				for(Inning inn : match.getMatch().getInning())
				{
					if(player_check != true) {
						for(BattingCard bc : inn.getBattingCard())
						{
							if(bc.getPlayerId() == PlayerId) {
								player_check = true;
								row_no = row_no + 1;
								
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_no + " SET " + 
										"v " + team.get(inn.getBowlingTeamId() - 1).getTeamName1().toUpperCase() + "\0");
							
//								print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
//										+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerFigure" + row_no + " SET " + "" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerOvers" + row_no + " SET " + "DNB" + "\0");
							}
						}
					}
				}
				
				if(player_check != true) {
					row_no = row_no + 1;
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBatRows" + " SET " + row_no + "\0");
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "vBallOmo" + row_no + " SET " + "0" + "\0");
					
					if(plyr.get(PlayerId - 1).getTeamId() == match.getSetup().getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_no + " SET " + 
								"v " + match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerName" + row_no + " SET " + 
								"v " + match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerFigure" + row_no + " SET " + "" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tBallPlayerOvers" + row_no + " SET " + "DNP" + "\0");
//					print_writer.println("-1 RENDERER*TREE*$Main$AllGrp$All$AllDataGrp$BattingCardAll$BatData$BatData$BatDataGrp$BatRow" + row_no
//							+ "$RowAnimation$BatOmo$Dehighlight$ScoreGrp$NotOutStar*ACTIVE SET 0 \0");
				}
			}
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.180 In$BatDataIn 1.180 \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	public void populateInnBuilder(PrintWriter print_writer,String viz_scene,int whichInning,int playerId, MatchAllData match, String broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ " " + " \0");

			for(Inning inn : match.getMatch().getInning()) {
				if (inn.getInningNumber() == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tTeamRefName" + " SET " + "IMAGE*/Default/Nepal_T20/Logos/" + 
							inn.getBatting_team().getTeamName4().toUpperCase() + "\0");
					for(BattingCard bc : inn.getBattingCard()) {
						if(playerId == bc.getPlayerId()) {
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Runs*GEOM*TEXT SET "+ bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$ScoreGrp$noname$Balls*GEOM*TEXT SET "+ bc.getBalls() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET "+ bc.getPlayer().getTicker_name() + "\0");
						}
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$PlayerNameGrp$TeamNameGrp$noname$FOW*GEOM*TEXT SET " + "BALLS PER 20 RUNS" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$HeadValue1$Dehiglight$StatHead1*GEOM*TEXT SET "+ 20 + CricketFunctions.Plural(20) + " \0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$LT$All$Out$FOW$BottomLine$noname*ACTIVE SET 0" + "\0");
					
				   /* for (int i = 0; i < CricketFunctions.getPlayerSplit(whichInning,playerId, 20,match,match.getEvents()).size(); i++) {
				    	
				    	int row_id = i + 1;
				    	for(int split_id=1;split_id<=6;split_id++) {
					    	if(split_id <= CricketFunctions.getPlayerSplit(whichInning,playerId, 20,match,match.getEvents()).size()) {
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
										CricketFunctions.getPlayerSplit(whichInning,playerId, 20,match,match.getEvents()).get(i) + "\0");
					    	}
					    	else {
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + split_id + "A" + " SET " + " " + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue" + split_id + "B" + " SET " + " " + "\0");
					    	}
				    	}
			        }*/
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.600 \0");
		}
	}
	
	public void populatePhaseWise(PrintWriter print_writer,String viz_scene,MatchAllData match ,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "FAIR_BREAK":
			
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$LogoMasked$TeamLogo1*TEXTURE*IMAGE SET " + logo_path +
					"fair_break" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$LogoGrp$LogoMasked$TeamColour*TEXTURE*IMAGE SET " + team_color +
					"fair_break" + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$TeamColour*TEXTURE*IMAGE SET " + team_color +
					"fair_break" + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2A" + " SET " + 
					CricketFunctions.getFirstPowerPlayScore(match, 1, match.getEventFile().getEvents()) + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3A" + " SET " + 
					CricketFunctions.getSecPowerPlayScore(match, 1, match.getEventFile().getEvents()) + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4A" + " SET " + 
					CricketFunctions.getThirdPowerPlayScore(match, 1, match.getEventFile().getEvents()) + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue2B" + " SET " + 
					CricketFunctions.getFirstPowerPlayScore(match, 2, match.getEventFile().getEvents()) + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue3B" + " SET " + 
					CricketFunctions.getSecPowerPlayScore(match, 2, match.getEventFile().getEvents()) + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue4B" + " SET " + 
					CricketFunctions.getThirdPowerPlayScore(match, 2, match.getEventFile().getEvents()) + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1A" + " SET " + 
					match.getMatch().getInning().get(0).getBatting_team().getTeamName1() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main*FUNCTION*ControlObject*in SET ON " + "tStatValue1B" + " SET " + 
					match.getMatch().getInning().get(1).getBatting_team().getTeamName1() + "\0");
			
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.jpg In 1.428 \0");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
}

	