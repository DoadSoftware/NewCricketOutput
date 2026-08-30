package com.cricket.broadcaster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputFilter.Config;
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
import com.cricket.model.StatsType;
import com.cricket.model.Team;
import com.cricket.service.CricketService;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Configuration;
import com.cricket.model.FallOfWicket;
import com.cricket.model.Fixture;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.model.OverByOverData;
import com.cricket.model.Player;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.controller.IndexController;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EVEREST_AR_VR extends Scene{

	private String status;
	public int FirstPlayerId;
	public int FirstPlayerId2;
	public String WhichProfile;
	public Player player;
	public Player player2;
	public Team team;
	public Statistics stat;
	public Statistics stat2;
	public StatsType statsType;
	public List<StatsType> statsTypes;
	private String slashOrDash = "-",category = "", data = ""; 
	public Infobar infobar = new Infobar();
	public String which_graphics_onscreen = "";
//	private String logo_path2 = "C:\\Everest_VR_AR\\Logos\\";
//	private String logo_path = "C:\\Everest_VR_AR\\Logos\\";
//	private String base_path = "C:\\Everest_VR_AR\\SPL_Base1\\";
//	private String base_path1 = "C:\\Everest_VR_AR\\SPL_Base1\\";
//	private String base_path2 = "C:\\Everest_VR_AR\\SPL_Base2\\";
//	private String text_path1 = "C:\\Everest_VR_AR\\SPL_Text1\\";
//	private String text_path2 = "C:\\Everest_VR_AR\\SPL_Text2\\";
//	private String photo_path = "C:\\Images\\Everest_VR_AR\\Photos\\";
	
	private String logo_path2 = "C:\\Everest_SPL_VR\\Logos\\";
//	private String logo_path = "C:\\Everest_VR_2026\\Logos\\";
	private String logo_path = "C:\\Everest_VR_AR\\Logos\\";
	private String base_path = "C:\\Everest_SPL_VR\\SPL_Base1\\";
	private String base_path1 = "C:\\Everest_SPL_VR\\SPL_Base1\\";
	private String base_path2 = "C:\\Everest_SPL_VR\\SPL_Base2\\";
	private String text_path1 = "C:\\Everest_SPL_VR\\SPL_Text1\\";
	private String text_path2 = "C:\\Everest_SPL_VR\\SPL_Text2\\";
	private String photo_path = "C:\\Images\\Everest_SPL_VR\\Photos\\";
	
	private String base_path_bp1 = "C:\\Everest_VR_2026\\Textures\\BARODA_Texture\\Base1";
	private String base_path_bp2 = "C:\\Everest_VR_2026\\Textures\\BARODA_Texture\\Base2";
	
//	private String base_path_mh1 = "C:\\Everest_VR_2026\\Textures\\Maharaja\\Base 1\\EVENT";
//	private String base_path_mh2 = "C:\\Everest_VR_2026\\Textures\\Maharaja\\Base 2\\EVENT";
//	private String base_path_mh3 = "C:\\Everest_VR_2026\\Textures\\Maharaja\\Base 3\\EVENT";
	
	private String base_path_mh1 = "C:\\Everest_VR_AR\\Textures\\Base_Texture";
	private String base_path_mh2 = "C:\\Everest_VR_AR\\Textures\\Base_Texture";
	private String base_path_mh3 = "C:\\Everest_VR_AR\\Textures\\Base_Texture";
	//private String base_path2 = "C:\\Everest_SPL_VR\\SPL_Base2\\";
	
	private String base_path_tg1 = "C:\\Everest_VR_2026\\Textures\\TG20\\Base 1\\";
	private String base_path_tg2 = "C:\\Everest_VR_2026\\Textures\\TG20\\Base 2\\";
	private String logo_pathtg = "C:\\Everest_VR_2026\\Logos\\TG20\\";
	private String photo_pathtg = "C:\\Everest_VR_2026\\Photos\\";
	
	public Inning inning;
	public BattingCard battingCard;
	public List<BattingCard> battingCardList = new ArrayList<BattingCard>();
	public List<String> this_data_str = new ArrayList<String>();
	
	public EVEREST_AR_VR() {
		super();
	}

	public EVEREST_AR_VR(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Infobar updateInfobar(Scene scene, MatchAllData match,boolean show_speed, PrintWriter print_writer,Configuration config ,CricketService cricketService) throws InterruptedException, IOException
	{
		switch (which_graphics_onscreen.toUpperCase()) {
		case "PARTNERSHIP":
			populateCurrentPartnership(print_writer, data.split(",")[0], match, config.getBroadcaster(), config);
			break;
		case "COMPARISON_AR":
			populateComparisonAR(true,print_writer, match, config.getBroadcaster());
			break;
		
		case "PROJECTED_AR":
			populateProjectedAR(true,print_writer, match, config.getBroadcaster());
			break;
		case "EQUATION_VR":
			 populateEquationVR(true,print_writer, match, config.getBroadcaster());
			break;
			
		//vr graphics
		case "RUN_VR":
			populateRunVR(print_writer, match, config.getBroadcaster());
			break;
		case "BOUNDARIES_AR":
			populateBoundariesAR(true,print_writer, match, config.getBroadcaster());
			break;
		case "LASTBOUNDARY_AR":
			populateLastBoundary(print_writer, match, config.getBroadcaster());
			break;	
			
		case "COMPARISON_VR":
			populateComparisonVR(true,print_writer, match, config.getBroadcaster());
			break;
		case "EQUATION_AR":
			populateEquationAR(true,print_writer, match, config.getBroadcaster());
			break;
		case "EQUATIONINTARGET_AR":	
			populateEquationTargetImageAR(true,print_writer, match, config.getBroadcaster(),config);
			break;
		case "PROJECTED_VR":
			populateProjectedVR(true,print_writer, match, config.getBroadcaster());
			break;
			
		case "THISOVER_AR":
			populateThisOver(true,print_writer,data.split(",")[1],match,config.getBroadcaster());
			break;
		case "PHASE":
			populatePhase(true,print_writer,Integer.valueOf(data.split(",")[1]),match,config.getBroadcaster(),config);
			break;
		case "LASTXBALLS_VR":
			populateLastXBalls(false,print_writer ,Integer.valueOf(data.split(",")[1]),
					Integer.valueOf(data.split(",")[2]),match , config.getBroadcaster(),config);
			break;
			
		//bat and ball update
		case "BATMILEDETAILS":
			populateBatMile(true,print_writer ,Integer.valueOf(data.split(",")[1]),Integer.valueOf(data.split(",")[2]),
					cricketService.getAllPlayer(),match , config.getBroadcaster(),config);
			break;
		case "BOWLERDETAILS":
			populateBallMile(true,print_writer ,Integer.valueOf(data.split(",")[1]),Integer.valueOf(data.split(",")[2]),
					cricketService.getAllPlayer(),match , config.getBroadcaster(),config);
			break;	
			
			
			
		}
//		CricketFunctions.getInteractive(match);
		return infobar;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches, PrintWriter print_writer, 
			List<Scene> scenes, String valueToProcess, List<Statistics> statistics,Configuration config) throws InterruptedException, ParseException, JAXBException, NumberFormatException, 
				IOException, IllegalAccessException, InvocationTargetException{
		
		//valueToProcess = valueToProcess.replace("Everest_MT20/Scenes", "Everest_Barodaleague_2025/AR_Matt_Scene")
									   
		System.out.println("whatToProcess = " + whatToProcess);
		switch (whatToProcess.toUpperCase()) {
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			switch (config.getBroadcaster().toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();
			}
		
		case "POPULATE-BOUNDARIES_VRR": case "POPULATE-COMPARISON_AR": case "POPULATE-COMPARISON_VR": case "POPULATE-TARGET_AR": 
		case "POPULATE-MATCHID_AR": case "POPULATE-PROJECTED_AR": case "POPULATE-LASTBOUNDARY_AR":
		case "POPULATE-FREE_TEXT_AR": case "POPULATE-THISOVER_VR": case "POPULATE-EQUATION_AR": case "POPULATE-EQUATION_VR": 
		case "POPULATE-MATCH_ANIMATION_AR": case "POPULATE-THISOVER_AR": case "POPULATE-MATCH_PROMO": case "POPULATE-PROJECTED_VR":
		case "POPULATE-TEAMCELEB_AR": case "POPULATE-PLAYERCELEB": case "POPULATE-MATCH_PROMO_ANIMATION": case "POPULATE-L3-BATMILEDETAILS": 
		case "POPULATE-L3-BOWLERDETAILS": case "POPULATE-FOW_AR":
		case "POPULATE-COUNT_AR": case "POPULATE-FF-POSITION_LANDMARK": case "POPULATE-TOSS_AR": case "POPULATE-RUNRATE": 
		case "POPULATE-LT-PARTNERSHIP": case "POPULATE-EQUATIONIMAGE_AR":	
		case "POPULATE-MATCHID_VR": case "POPULATE-MATCHID_ARR":	case "POPULATE-TARGET_VR": case "POPULATE-COUNTDOWN_AR": case "POPULATE-NEXT_AR": 
		case"POPULATE-TOSSFLIP_AR": case "POPULATE-TARGETIMAGE_AR": case "POPULATE-RUN_VR": case "POPULATE-PHASE": 
		case "POPULATE-LASTXBALLS_VR": case "POPULATE-PHASE_VR":
		case "POPULATE-FALLOFWIKETS_VR":	
		case "POPULATE-EVERESTPLAYERPROFILEBAT": case "POPULATE-FF-PLAYERPROFILEBALLL": case "POPULATE-DOUBLEEVERESTPLAYERPROFILEBAT":
		case "POPULATE-EQUATION_ARINTARGET":  case "POPULATE-L3-BUG-TOSS":
			switch (config.getBroadcaster().toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				switch(whatToProcess.toUpperCase()) {
				case"POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-BOTTOM": case"POPULATE-TOSSFLIP_AR":
					break;
				case "POPULATE-L3-INFOBAR":
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,config.getBroadcaster());
					break;
				default:
					System.out.println(valueToProcess);
					scenes.get(1).setWhich_layer(String.valueOf("1"));
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,config.getBroadcaster());
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-LASTXBALLS_VR":
					 data = valueToProcess;
					populateLastXBalls(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),
							Integer.valueOf(valueToProcess.split(",")[2]),match , config.getBroadcaster(),config);
					break;
				case "POPULATE-PHASE_VR":
					 data = valueToProcess;
					populatePhase(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),match , config.getBroadcaster(),config);
					break;
				case "POPULATE-PHASE":
					 data = valueToProcess;
					populatePhase(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),match , config.getBroadcaster(),config);
					break;
				case "POPULATE-FF-PLAYERPROFILEBALLL":
					System.out.println("value" + valueToProcess);
					System.out.println("whatToProcess" + whatToProcess);
					FirstPlayerId = Integer.valueOf(valueToProcess.split(",")[1]);
					WhichProfile = valueToProcess.split(",")[2];
					
					if(FirstPlayerId <= 0 || WhichProfile == null) {
						return "PopulateL3rdPlayerProfile: Player Id NOT found [" + FirstPlayerId + "]";
					}
					
					player = CricketFunctions.getPlayerFromMatchData(FirstPlayerId, match); 
					
					if(player == null) {
						return "PopulateL3rdPlayerProfile: Player Id not found [" + FirstPlayerId + "]";
					}
					
					if(match.getSetup().getHomeTeamId() == player.getTeamId()) {
						team = match.getSetup().getHomeTeam();
					} else if(match.getSetup().getAwayTeamId() == player.getTeamId()) {
						team = match.getSetup().getAwayTeam();
					} 
					
					if(team == null) {
						return "PopulateL3rdPlayerProfile: Team Id not found [" + player.getTeamId() + "]";
					}
					
					
					switch (WhichProfile.toUpperCase()) {
					case "DT20": 
						statsType = cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase("DT20")).findAny().orElse(null);
						break;
					case "IT20":
						statsType = cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase("IT20")).findAny().orElse(null);
						break;
					}
					
					
					
					if(statsType == null) {
						return "PopulateL3rdPlayerProfile: Stats Type not found for profile [" + WhichProfile + "]";
					}
					
					stat = statistics.stream().filter(st -> st.getPlayerID() == FirstPlayerId && statsType.getStatsId() == st.getStatsTypeId()).findAny().orElse(null);
					if(stat == null) {
						return "PopulateL3rdPlayerProfile: Stats not found for Player Id [" + FirstPlayerId + "]";
					}
					
					stat.setStats_type(statsType);
					stat = CricketFunctions.updateTournamentWithH2h(stat, IndexController.headToHead.getH2hPlayer(), match, CricketUtil.FULL);
					
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20) || 
							match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20)) {
						switch (WhichProfile.toUpperCase()) {
						case "DT20":
							match.getSetup().setMatchType(CricketUtil.DT20);
							break;
						}
						stat = CricketFunctions.updateStatisticsWithMatchData(stat, match, CricketUtil.FULL);
						if(WhichProfile.equalsIgnoreCase(CricketUtil.DT20)) {
							match.getSetup().setMatchType(CricketUtil.IT20);
						}
					}
					
					populateballprofileAr(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),match , config.getBroadcaster(),config,cricketService);
					break;
				case "POPULATE-DOUBLEEVERESTPLAYERPROFILEBAT":
					FirstPlayerId = Integer.valueOf(valueToProcess.split(",")[1]);
					FirstPlayerId2 = Integer.valueOf(valueToProcess.split(",")[2]);
					
					WhichProfile = valueToProcess.split(",")[3];
					
					if(FirstPlayerId <= 0 || WhichProfile == null) {
						return "PopulateL3rdPlayerProfile: Player Id NOT found [" + FirstPlayerId + "]";
					}
					
					player = CricketFunctions.getPlayerFromMatchData(FirstPlayerId, match); 
					player2 = CricketFunctions.getPlayerFromMatchData(FirstPlayerId2, match); 
					
					if(player == null) {
						return "PopulateL3rdPlayerProfile: Player Id not found [" + FirstPlayerId + "]";
					}
					
					if(match.getSetup().getHomeTeamId() == player.getTeamId()) {
						team = match.getSetup().getHomeTeam();
					} else if(match.getSetup().getAwayTeamId() == player.getTeamId()) {
						team = match.getSetup().getAwayTeam();
					} 
					
					if(team == null) {
						return "PopulateL3rdPlayerProfile: Team Id not found [" + player.getTeamId() + "]";
					}
					
					
					switch (WhichProfile.toUpperCase()) {
					case "DT20": 
						statsType = cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase("DT20")).findAny().orElse(null);
						break;
					case "IT20":
						statsType = cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase("IT20")).findAny().orElse(null);
						break;
					}
					
					
					
					if(statsType == null) {
						return "PopulateL3rdPlayerProfile: Stats Type not found for profile [" + WhichProfile + "]";
					}
					
					stat = statistics.stream().filter(st -> st.getPlayerID() == FirstPlayerId && statsType.getStatsId() == st.getStatsTypeId()).findAny().orElse(null);
					stat2 = statistics.stream().filter(st -> st.getPlayerID() == FirstPlayerId2 && statsType.getStatsId() == st.getStatsTypeId()).findAny().orElse(null);
					if(stat == null) {
						return "PopulateL3rdPlayerProfile: Stats not found for Player Id [" + FirstPlayerId + "]";
					}
					
					stat.setStats_type(statsType);
					stat = CricketFunctions.updateTournamentWithH2h(stat, IndexController.headToHead.getH2hPlayer(), match, CricketUtil.FULL);
					
					stat2.setStats_type(statsType);
					stat2 = CricketFunctions.updateTournamentWithH2h(stat2, IndexController.headToHead.getH2hPlayer(), match, CricketUtil.FULL);
					
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20) || 
							match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20)) {
						switch (WhichProfile.toUpperCase()) {
						case "DT20":
							match.getSetup().setMatchType(CricketUtil.DT20);
							break;
						}
						stat = CricketFunctions.updateStatisticsWithMatchData(stat, match, CricketUtil.FULL);
						stat2 = CricketFunctions.updateStatisticsWithMatchData(stat2, match, CricketUtil.FULL);
						if(WhichProfile.equalsIgnoreCase(CricketUtil.DT20)) {
							match.getSetup().setMatchType(CricketUtil.IT20);
						}
					}
					
					populatedoublebatprofileAr(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),match , config.getBroadcaster(),config,cricketService);
					break;
				case "POPULATE-EVERESTPLAYERPROFILEBAT":
					System.out.println("value" + valueToProcess);
					System.out.println("whatToProcess" + whatToProcess);
					FirstPlayerId = Integer.valueOf(valueToProcess.split(",")[1]);
					WhichProfile = valueToProcess.split(",")[2];
					
					if(FirstPlayerId <= 0 || WhichProfile == null) {
						return "PopulateL3rdPlayerProfile: Player Id NOT found [" + FirstPlayerId + "]";
					}
					
					player = CricketFunctions.getPlayerFromMatchData(FirstPlayerId, match); 
					
					if(player == null) {
						return "PopulateL3rdPlayerProfile: Player Id not found [" + FirstPlayerId + "]";
					}
					
					if(match.getSetup().getHomeTeamId() == player.getTeamId()) {
						team = match.getSetup().getHomeTeam();
					} else if(match.getSetup().getAwayTeamId() == player.getTeamId()) {
						team = match.getSetup().getAwayTeam();
					} 
					
					if(team == null) {
						return "PopulateL3rdPlayerProfile: Team Id not found [" + player.getTeamId() + "]";
					}
					
					
					switch (WhichProfile.toUpperCase()) {
					case "DT20": 
						statsType = cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase("DT20")).findAny().orElse(null);
						break;
					case "IT20":
						statsType = cricketService.getAllStatsType().stream().filter(st -> st.getStatsShortName().equalsIgnoreCase("IT20")).findAny().orElse(null);
						break;
					}
					
					
					
					if(statsType == null) {
						return "PopulateL3rdPlayerProfile: Stats Type not found for profile [" + WhichProfile + "]";
					}
					
					stat = statistics.stream().filter(st -> st.getPlayerID() == FirstPlayerId && statsType.getStatsId() == st.getStatsTypeId()).findAny().orElse(null);
					if(stat == null) {
						return "PopulateL3rdPlayerProfile: Stats not found for Player Id [" + FirstPlayerId + "]";
					}
					
					stat.setStats_type(statsType);
					stat = CricketFunctions.updateTournamentWithH2h(stat, IndexController.headToHead.getH2hPlayer(), match, CricketUtil.FULL);
					
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.IT20) || 
							match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.DT20)) {
						switch (WhichProfile.toUpperCase()) {
						case "DT20":
							match.getSetup().setMatchType(CricketUtil.DT20);
							break;
						}
						stat = CricketFunctions.updateStatisticsWithMatchData(stat, match, CricketUtil.FULL);
						if(WhichProfile.equalsIgnoreCase(CricketUtil.DT20)) {
							match.getSetup().setMatchType(CricketUtil.IT20);
						}
					}
					
					populatebatprofileAr(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),match , config.getBroadcaster(),config,cricketService);
				break;
				case "POPULATE-L3-BATMILEDETAILS":
					 data = valueToProcess;
					populateBatMile(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),
							cricketService.getAllPlayer(),match , config.getBroadcaster(),config);
					break;
				case "POPULATE-L3-BOWLERDETAILS":
					 data = valueToProcess;
					populateBallMile(false,print_writer ,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),
							cricketService.getAllPlayer(),match , config.getBroadcaster(),config);
					break;
				case "POPULATE-FOW_AR":
					populateFowAR(false,print_writer, match, config.getBroadcaster());
			
					break;	
				case "POPULATE-MATCH_PROMO":
					populateMatchPromo(false,print_writer, valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(),match , config.getBroadcaster());
					break;
				 case "POPULATE-LT-PARTNERSHIP":
					 data = valueToProcess;
					  populateCurrentPartnership(print_writer, valueToProcess.split(",")[0], match, config.getBroadcaster(), config);
						break;
//				case "POPULATE-THISOVER_AR":
//					populateThisOver(false,print_writer,match,config.getBroadcaster());
//					break;
				case "POPULATE-MATCH_ANIMATION_AR":
					populateMatchIdAnimationAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-MATCH_PROMO_ANIMATION":
					populateMatchPromoAnimationAR(false,print_writer,valueToProcess.split(",")[0] ,Integer.valueOf(valueToProcess.split(",")[1]),cricketService.getTeams(),
							cricketService.getFixtures(), match, config.getBroadcaster());
					break;
				case "POPULATE-EQUATION_AR":
					populateEquationAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-EQUATION_VR":
					populateEquationVR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-EQUATIONIMAGE_AR":
					populateEquationImageVR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-FF-POSITION_LANDMARK":
					System.out.println("valueToProcess" + valueToProcess);
					populatePlayerInAt(print_writer, match,Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]),cricketService.getAllPlayer(),cricketService.getTeams(), 
							config.getBroadcaster(),config);
					break;
				case "POPULATE-NEXT_AR":
					populateNextToBat(false,print_writer,match,config.getBroadcaster(),config,cricketService);
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
				case "POPULATE-THISOVER_VR":
					data = valueToProcess;
					populateThisOver(false,print_writer,valueToProcess.split(",")[1],match,config.getBroadcaster());
					break;
				case "POPULATE-BOUNDARIES_VRR":
					populateBoundariesAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-COMPARISON_AR":
					populateComparisonAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-COMPARISON_VR":
					populateComparisonVR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-TARGET_AR":
					populateTargetAR(print_writer, match, config.getBroadcaster(),config);
					break;
				case "POPULATE-LASTBOUNDARY_AR":
					populateLastBoundary(print_writer, match, config.getBroadcaster());
					break;					
				case "POPULATE-TARGETIMAGE_AR":	
					populateTargetImageAR(print_writer, match, config.getBroadcaster(),config);
					break;
				case "POPULATE-EQUATION_ARINTARGET":
					populateEquationTargetImageAR(true,print_writer, match, config.getBroadcaster(),config);
					break;
				case "POPULATE-TARGET_VR":
					populateTargetVR(print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-RUN_VR":
					populateRunVR(print_writer, match, config.getBroadcaster());
					break;
				 case "POPULATE-COUNTDOWN_AR":
					 populatecountdown(print_writer, match, config.getBroadcaster());
					 break;
				case "POPULATE-TOSS_AR":
					populateTossAR(print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-MATCHID_AR":
					populateMatchIdAR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-MATCHID_VR":	
					populateMatchIdVR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-MATCHID_ARR":
					populateMatchIdARR(false,print_writer, match, config.getBroadcaster());
					break;
				case "POPULATE-L3-BUG-TOSS":
					System.out.println(valueToProcess);
					populatetossVR(false,print_writer, match, config.getBroadcaster(),valueToProcess.split(",")[1],valueToProcess.split(",")[2]);
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
				case "POPULATE-PROJECTED_VR":
					populateProjectedVR(false,print_writer, match, config.getBroadcaster());
					break;	
				case"POPULATE-TOSSFLIP_AR":
			//		populateCoinflip(print_writer);
					break;
				}
			}
		case "ANIMATE-IN-LASTBOUNDARY_AR": case "ANIMATE-IN-BUG-TOSS":
	
		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-BOUNDARIES_AR": case "ANIMATE-IN-COMPARISON_AR": case "ANIMATE-IN-COMPARISON_VR": case "ANIMATE-IN-TARGET_AR": case "ANIMATE-IN-MATCHID_AR":
		case "ANIMATE-IN-PROJECTED_AR": case "ANIMATE-IN-FREETEXT_AR": case "ANIMATE-IN-EQUATION_AR": case "ANIMATE-IN-EQUATION_VR": case "ANIMATE-IN-MATCH_ANIMATION_AR": case "ANIMATE-IN-THISOVER_AR": case "ANIMATE-IN-MATCHID_VR":
		case "ANIMATE-IN-MATCH_PROMO": case "ANIMATE-IN-TEAMCELEB_AR": case "ANIMATE-IN-PLAYERCELEB_AR": case "ANIMATE-IN-BOWLERDETAILS": case "ANIMATE-IN-BATMILEDETAILS":
		case "ANIMATE-IN-COUNT_AR": case "ANIMATE-IN-POSITION_LANDMARK": case "ANIMATE-TOSS_AR": case "ANIMATE-IN-RUNRATE_AR": case "ANIMATE-IN-LTPARTNERSHIP": case "ANIMATE-IN-MATCHID_ARR":
		case "ANIMATE-IN-EQUATIONIMAGE_AR": case "ANIMATE-IN-RUN_VR": case "ANIMATE-IN-PHASE": case "ANIMATE-LASTXBALLS_VR":
		case "ANIMATE-IN-PLAYERPRFOFILE_BATT":	case "ANIMATE-IN-PLAYERPRFOFILE_BALLL": case "ANIMATE-IN-DOUBLEPLAYERPRFOFILE_BATT": case "ANIMATE-IN-EQUATIONIN TARGET_AR":
		case "ANIMATE-IN-PROJECTED_VR":	case "ANIMATE-IN-TARGET_VR": case "ANIMATE-IN-COUNTDOWN_VR": case "ANIMATE-IN-NEXT_AR": case "ANIMATE-IN-PHASE_VR":
		case "ANIMATE-IN-TARGETIMAGE_AR":  case "ANIMATE-IN-FOW_AR":
			switch (config.getBroadcaster().toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-THISSERIES":
					if(infobar.isInfobar_on_screen() == true) {
						processAnimation(print_writer, "FF_In", "START", config.getBroadcaster(),1);
						TimeUnit.MILLISECONDS.sleep(200);
					}
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-LTPARTNERSHIP":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director4", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director4_Copy", "START", config.getBroadcaster(),1);
					which_graphics_onscreen = "PARTNERSHIP";
					break;
					
				case "ANIMATE-IN-POSITION_LANDMARK":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director4", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director5", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "POSITION_LANDMARK";
					break;
				case "ANIMATE-IN-PLAYERPRFOFILE_BATT": case "ANIMATE-IN-PLAYERPRFOFILE_BALLL":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3_Copy", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "PROFILE-BATT";
					break;
				case "ANIMATE-IN-DOUBLEPLAYERPRFOFILE_BATT":	
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director4", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director4_Copy", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "PROFILE-DOUBLEBATT";
					break;
				case "ANIMATE-IN-COUNT_AR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "IN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Star_grp", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Star_Rotation", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Counter", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "COUNT_AR";
					break;
				case "ANIMATE-IN-BATMILEDETAILS":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3_Copy", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "BATMILEDETAILS";
					break;
				case "ANIMATE-IN-BOWLERDETAILS":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3_Copy", "START", config.getBroadcaster(),1);
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
				case "ANIMATE-LASTXBALLS_VR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "LASTXBALLS_VR";
					break;
				case "ANIMATE-IN-PHASE":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "PHASE";
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
				case "ANIMATE-IN-RUN_VR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "RUN_VR";
					break;
				case "ANIMATE-IN-FOW_AR":	
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
					which_graphics_onscreen = "FOW_AR";
					break;	
				case "ANIMATE-IN-EQUATION_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "EQUATION_AR";
					break;
				case "ANIMATE-IN-EQUATION_VR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "IN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Star_Grp", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Flag_Opa", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Star_Rotation", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Flag_Grp", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "EQUATION_VR";
					break;
				case "ANIMATE-IN-EQUATIONIMAGE_AR":	
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "IN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Star_Grp", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Flag_Opa", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Star_Rotation", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Flag_Grp", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "EQUATIONIMAGE_AR";
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
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director5", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director5_Copy", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "COMPARISON_AR";
					break;
				case "ANIMATE-IN-NEXT_AR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "NEXTTOBAT_AR";
					break;
				case "ANIMATE-IN-COMPARISON_VR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "COMPARISON_VR";
					break;
				case "ANIMATE-IN-TARGET_AR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "TARGET_AR";
					break;
				case "ANIMATE-IN-TARGETIMAGE_AR": 
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director5", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director5_Copy", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "TARGETIMAGE_AR";
					break;
				case "ANIMATE-IN-EQUATIONIN TARGET_AR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director5", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director5_Copy", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "EQUATIONINTARGET_AR";
					break;
				case "ANIMATE-IN-TARGET_VR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "TARGET_VR";
					break;
				case "ANIMATE-IN-COUNTDOWN_VR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "IN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Star_grp", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Star_Rotation", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Counter", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "COUNTDOWN_AR";
					
				case "ANIMATE-TOSS_AR":
					processAnimation(print_writer, "All", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Flag", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Left_Stars", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Right_Stars", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1_Copy", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "toss_result", "START", config.getBroadcaster(),1);
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
				case "ANIMATE-IN-LASTBOUNDARY_AR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
					which_graphics_onscreen = "LASTBOUNDARY_AR";
					break;
				case "ANIMATE-IN-MATCHID_AR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director4", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director5", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCHID_AR";
					break;
				case "ANIMATE-IN-BUG-TOSS":	
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "FLAG1", "START", config.getBroadcaster(),1);
					
					processAnimation(print_writer, "FLAG1", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "BUG_TOSS";
				case "ANIMATE-IN-MATCHID_VR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "FLAG1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "FLAG2", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCHID_VR";
					break;
				case "ANIMATE-IN-MATCHID_ARR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director6", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director7", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director8", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "MATCHID_ARR";
					break;
				case "ANIMATE-IN-PROJECTED_AR":
					processAnimation(print_writer, "MAIN", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "LOOP", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director1", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director2", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3", "START", config.getBroadcaster(),1);
					processAnimation(print_writer, "Director3_Copy", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "PROJECTED_AR";
					break;	
				case "ANIMATE-IN-PROJECTED_VR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "PROJECTED_VR";
					break;	
				case "ANIMATE-IN-PHASE_VR":
					processAnimation(print_writer, "In", "START", config.getBroadcaster(),1);
//					print_writer.println("LAYER1*EVEREST*STAGE START;");
					which_graphics_onscreen = "PHASE";
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
					case "THISOVER_AR": case "TEAMCELEB_AR": case "MATCH_ANIMATION_AR": case "EQUATION_AR": case "EQUATIONINTARGET_AR": case "EQUATION_VR": case "EQUATIONIMAGE_AR":
					case "FREETEXT_AR": case "PROJECTED_AR": case "MATCHID_PROMO_AR": case "MATCHID_AR": case "MATCHID_VR": case "LASTXBALLS_VR":
					case "TARGET_AR": case "COMPARISON_AR": case "COMPARISON_VR": case "LASTBOUNDARY_AR": case "BOUNDARIES_AR": case "PLAYERCELEB_AR": case "TARGET_VR":
					case "BATMILEDETAILS": case "BOWLERDETAILS": case "COUNT_AR": case "POSITION_LANDMARK": case "PROFILE-BATT": case "PROFILE-DOUBLEBATT":case "TOSS_AR": case "RUNRATE_AR": case "COUNTDOWN_AR":
					case "MATCHID_ARR":
					case "PARTNERSHIP":	case "PROJECTED_VR": case "NEXTTOBAT_AR": case "BUG_TOSS": case "TARGETIMAGE_AR": case "RUN_VR": case "PHASE": case "FOW_AR":
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

	
	private void populateCoinflip(PrintWriter print_writer, String session_selected_broadcaster) {
		processAnimation(print_writer, "Coin_Flip", "START", session_selected_broadcaster,1);
		
		this.status = CricketUtil.SUCCESSFUL;
		
	}

	private void populatecountdown(PrintWriter print_writer, MatchAllData match, String session_selected_broadcaster) {
		//base colour
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
				match.getSetup().getHomeTeam().getTeamName4() + "_base1" + CricketUtil.PNG_EXTENSION + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
				match.getSetup().getHomeTeam().getTeamName4() + "_base2" + CricketUtil.PNG_EXTENSION + ";");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base1 " + base_path1 +  
				match.getSetup().getAwayTeam().getTeamName4() + "_base1" + CricketUtil.PNG_EXTENSION + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base2 " + base_path2 +  
				match.getSetup().getAwayTeam().getTeamName4() + "_base2" + CricketUtil.PNG_EXTENSION + ";");
		
		this.status = CricketUtil.SUCCESSFUL;
		
	}

	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String session_selected_broadcaster,int which_layer)
	{
		switch(session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
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
				
				this.status = CricketUtil.SUCCESSFUL;
			}	
		}
	}
	
	
	public void populatedoublebatprofileAr(boolean is_this_updating, PrintWriter print_writer, int playerId,int playerID2 ,MatchAllData matchAllData, String broadcaster,Configuration config
			,CricketService cricketService) throws InterruptedException 
	{
//		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag_Grp$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
//				+ "C:/Everest_VR_AR/Flags/"+ match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() +"/0000.png;");
		
		for(Player pl : cricketService.getAllPlayer()) {
			if(pl.getPlayerId() == playerId) {
				for(Team tm : cricketService.getTeams()) {
					if(pl.getTeamId() == tm.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Left_Player_Data " + pl.getFull_name() + ";");
					    
						
						
						if(tm.getTeamBadge().equalsIgnoreCase("WI")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 1;");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 0;");
						}
						
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
								tm.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
								tm.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo1 " + photo_path + tm.getTeamBadge().toUpperCase() + 
								"\\\\CENTER\\\\"	+  pl.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
				}
			}
			
		}
		for(Player ply : cricketService.getAllPlayer()) {
			if(ply.getPlayerId() == playerID2) {
				for(Team tm1 : cricketService.getTeams()) {
					if(ply.getTeamId() == tm1.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Right_Player_Data " + ply.getFull_name() + ";");
	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo2 " + photo_path + tm1.getTeamBadge().toUpperCase() + 
								"\\\\CENTER\\\\" +  ply.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
				}
			}
			
		}
		
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET MATCHES " +stat.getMatches() + " - MATCHES - " + stat2.getMatches() + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RUNS "+ stat.getRuns() + " - RUNS - " + stat2.getRuns()+ ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 50s/100s " + stat.getFifties() + "/" + stat.getHundreds() + " - 50/100 - " + stat2.getFifties() + "/" + stat2.getHundreds() + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET CRNT_PRNERSHP "  + stat.getBestScore()+ " - BEST - " + stat2.getBestScore() + ";");
	}
	
	public void populatebatprofileAr(boolean is_this_updating, PrintWriter print_writer, int playerId ,MatchAllData matchAllData, String broadcaster,Configuration config
			,CricketService cricketService) throws InterruptedException 
	{
//		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag_Grp$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
//				+ "C:/Everest_VR_AR/Flags/"+ match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() +"/0000.png;");
		
		for(Player pl : cricketService.getAllPlayer()) {
			if(pl.getPlayerId() == playerId) {
				for(Team tm : cricketService.getTeams()) {
					if(pl.getTeamId() == tm.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Name " + pl.getFull_name() + ";");
					
						if(tm.getTeamBadge().equalsIgnoreCase("WI")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 1;");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 0;");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
								tm.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
								tm.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + tm.getTeamBadge().toUpperCase() + 
								"\\\\CENTER\\\\"	+  pl.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
					}
				}
			}
			
		}
		
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET MATCHES " + "MATCHES - " + stat.getMatches() + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RUNS_Data " + "RUNS - " + stat.getRuns()+ ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 50/100_Data " + "50/100 - " + stat.getFifties() + "/" + stat.getHundreds() + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Best_Data_colour " + "BEST - " + stat.getBestScore() + ";");
	}
	public void populateballprofileAr(boolean is_this_updating, PrintWriter print_writer, int playerId ,MatchAllData matchAllData, String broadcaster,Configuration config
			,CricketService cricketService) throws InterruptedException 
	{
//		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag_Grp$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
//				+ "C:/Everest_VR_AR/Flags/"+ match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() +"/0000.png;");
		
		for(Player pl : cricketService.getAllPlayer()) {
			if(pl.getPlayerId() == playerId) {
				for(Team tm : cricketService.getTeams()) {
					if(pl.getTeamId() == tm.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Name " + pl.getFull_name() + ";");
					
						if(tm.getTeamBadge().equalsIgnoreCase("WI")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 1;");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 0;");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
								tm.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
								tm.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + tm.getTeamBadge().toUpperCase() + 
								"\\\\CENTER\\\\"	+  pl.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
					}
				}
			}
			
		}
		
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET MATCHES " + "MATCHES - " + stat.getMatches() + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RUNS_Data " + "WKTS - " + stat.getWickets()+ ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 50/100_Data " + "ECON - " + CricketFunctions.getEconomy
				(stat.getRunsConceded(), stat.getBallsBowled(), 2, "") + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Best_Data_colour " + "BEST - " + stat.getBestFigures() + ";");
	}
	public void populatePhase(boolean is_this_updating, PrintWriter print_writer, int inning ,MatchAllData matchAllData, String broadcaster,Configuration config) throws InterruptedException 
	{
		if (matchAllData == null) {
			this.status = "ERROR: Match is null";
		} else if (matchAllData.getMatch().getInning() == null) {
			this.status = "ERROR: MatchPromo's inning is null";
		} else {
			int oneToSixRuns = 0, sevenToFifteenRuns = 0, sixteenToTweentyRuns = 0,oneToSixfWkt = 0, sevenToFifteenWkt = 0, 
				sixteenToTweentyWkt = 0;
			
			String titl = "20", oneToSixfRRR = "",sevenToFifteenRRR="",sixteenToTweentyRRR="",val = "";
			
			if(inning == 1) {
				if(Integer.valueOf(matchAllData.getSetup().getReducedOvers()) > 0) {
					titl = String.valueOf(matchAllData.getSetup().getReducedOvers());
				}
			}else if(inning == 2) {
				if(matchAllData.getSetup().getReducedOvers() != null && !matchAllData.getSetup().getReducedOvers().isEmpty()) {
					if(Integer.valueOf(matchAllData.getSetup().getReducedOvers()) > 0) {
						titl = String.valueOf(matchAllData.getSetup().getReducedOvers());
					}
				}
				
				if(matchAllData.getSetup().getTargetOvers() != null && !matchAllData.getSetup().getTargetOvers().isEmpty()) {
					titl = matchAllData.getSetup().getTargetOvers();
				}
			}
			
			List<OverByOverData> overByOverData = CricketFunctions.getOverByOverData(matchAllData, inning, "MANHATTAN", 
						matchAllData.getEventFile().getEvents());
			for(int i=0; i<2; i++) {
				if(matchAllData.getMatch().getInning().get(i).getInningNumber() == inning) {
					
					for(int j=1; j<=overByOverData.size()-1; j++) {
						if(j>0 && j<=6) {
							oneToSixRuns+= overByOverData.get(j).getOverTotalRuns();
							oneToSixfWkt+=overByOverData.get(j).getOverTotalWickets();
							oneToSixfRRR = "@" + CricketFunctions.generateRunRates(oneToSixRuns, 1, j,2,matchAllData) + " RPO";
						}
						if(j>6 && j<=15) {
							sevenToFifteenRuns+= overByOverData.get(j).getOverTotalRuns();
							sevenToFifteenWkt+=overByOverData.get(j).getOverTotalWickets();
							sevenToFifteenRRR = "@" + CricketFunctions.generateRunRates(sevenToFifteenRuns, 7, j,2,matchAllData) + " RPO";
						}
						if(j>15 && j<=20) {
							sixteenToTweentyRuns+= overByOverData.get(j).getOverTotalRuns();
							sixteenToTweentyWkt+=overByOverData.get(j).getOverTotalWickets();
							sixteenToTweentyRRR = "@" + CricketFunctions.generateRunRates(sixteenToTweentyRuns, 16, j,2,matchAllData) + " RPO";
						}
					}
				}
			}
			
			for(Inning inn : matchAllData.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
							logo_path  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					
					//1st Phase
					if(oneToSixRuns == 0 && oneToSixfWkt == 0) {
						if(Float.valueOf(CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls())) > 0.0) {
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 0-0;");
						}else {
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 -;");
						}
					}else {
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + 
								oneToSixRuns + "-" + oneToSixfWkt + ";");
					}
					
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR1 " + oneToSixfRRR + ";");
					
					//2nd Phase
					if(sevenToFifteenRuns == 0 && sevenToFifteenWkt == 0) {
						if(Float.valueOf(CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls())) > 6.0) {
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 0-0;");
						}else {
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 -;");
						}
					}else {
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + 
								sevenToFifteenRuns + "-" + sevenToFifteenWkt + ";");
					}
					
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR2 " + sevenToFifteenRRR + ";");
					
					//3rd Phase
					if(sixteenToTweentyRuns == 0 && sixteenToTweentyWkt == 0) {
						if(Float.valueOf(CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls())) > 15.0) {
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 0-0;");
						}else {
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 -;");
						}
					}else {
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + 
								sixteenToTweentyRuns + "-" + sixteenToTweentyWkt + ";");
					}
					
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR3 " + sixteenToTweentyRRR + ";");
				}
			}
			
//			if(inning == 1) {
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRR CURRENT RUN RATE: " + matchAllData.getMatch().getInning().get(inning - 1).getRunRate() + ";");
//			}else if(inning == 2) {
//				val = CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(matchAllData).getRemaningRuns(), 0, 
//						CricketFunctions.GetTargetData(matchAllData).getRemaningBall(), 2,matchAllData);
//				
//				double rrr = Double.parseDouble(val);
//				
//				if(rrr > 3.0) {
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRR REQUIRED RUN RATE: " + val + ";");
//				}else {
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRR ;");
//				}
//			}
			
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "SCORES" + ";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader1 " + "BY PHASES" + ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead01 " + "OVERS 1-6" + ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead02 " + "OVERS 7-15"+ ";");
			
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead03 " + "OVERS 16-"+titl + ";");
			
			if(is_this_updating == false) {
				
				this.status = CricketUtil.SUCCESSFUL;
			}	
		}
	}
	
	public void populateLastXBalls(boolean is_this_updating, PrintWriter print_writer, int inning, int xBallsData ,MatchAllData matchAllData, String broadcaster,Configuration config) throws InterruptedException 
	{
		if (matchAllData == null) {
			this.status = "ERROR: Match is null";
		} else if (matchAllData.getMatch().getInning() == null) {
			this.status = "ERROR: MatchPromo's inning is null";
		} else {
			
			this_data_str = new ArrayList<String>();
			this_data_str.add(CricketFunctions.getlastthirtyballsdata(matchAllData, slashOrDash, matchAllData.getEventFile().getEvents(), xBallsData));
			
			for(Inning inn : matchAllData.getMatch().getInning()) {
				if(inn.getInningNumber() == inning) {
					
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 2;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_mh1 + CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_tg2+ inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
					//flags
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + inn.getBatting_team().getTeamBadge().toUpperCase() + 
							CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "LAST " + xBallsData + " BALLS" + ";");
				
				
				}
			}
			
			System.out.println(this_data_str.get(this_data_str.size()-1));
			//fours
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFourHead " + "RUN" +
			CricketFunctions.Plural(Integer.valueOf(this_data_str.get(this_data_str.size()-1).split(slashOrDash)[0])).toUpperCase() +";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + this_data_str.get(this_data_str.size()-1).split(slashOrDash)[0] + ";");
			
			
			//six
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "WICKET" 
			+ CricketFunctions.Plural(Integer.valueOf(this_data_str.get(this_data_str.size()-1).split(slashOrDash)[1])).toUpperCase() +";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + this_data_str.get(this_data_str.size()-1).split(slashOrDash)[1] + ";");
			
			
			
			
			if(is_this_updating == false) {
				
				this.status = CricketUtil.SUCCESSFUL;
			}	
		}
	}
	
	public void populateBatMile(boolean is_this_updating, PrintWriter print_writer, int inning , int playerId ,List<Player> player,MatchAllData match, String broadcaster,Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchPromo's inning is null";
		} else {
			
			//stars
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			//flag
//			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag_Grp$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
//					+ "C:/Everest_VR_AR/Flags/"+ match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() +"/0000.png;");
			
			if(match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge().equalsIgnoreCase("WI")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 1;");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 0;");
			}
			System.out.println("playerId = " + playerId);
			//player name
			for(Player plyr : player) {
				if(plyr.getPlayerId() == playerId) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Name " + 
							plyr.getTicker_name() + ";");
					
					if(match.getSetup().getHomeTeamId() == plyr.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + 
								"\\\\CENTER\\\\"	+  plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
						
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + 
								"\\\\CENTER\\\\"	+ plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Milestone_Data_1 ;");
			//player base
//			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 "  + base_path1 + 
//					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			
			//player image
//			if(match.getSetup().getHomeTeamId() == player.get(playerId - 1).getTeamId()) {
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*group*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + 
//						"\\\\" + player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
//				
//			}else {
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*group*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + 
//						"\\\\" + player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
//			}
			
			
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
//			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Logo1 " + logo_path + 
//					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + category + CricketUtil.PNG_EXTENSION + ";");
//			
//			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
//					match.getMatch().getInning().get(inning - 1).getBatting_team().getTeamBadge() + category + "\\" + 
//					player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
//			
			
			
		
			//data
			for(BattingCard bc : match.getMatch().getInning().get(inning - 1).getBattingCard()) {
				if(bc.getPlayerId() == playerId) {
					
					if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Runs " + 
								bc.getRuns() + "*" + ";");
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Runs " + 
								bc.getRuns() + ";");
					}
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Rest_Data " + "OFF " +
							bc.getBalls() + " BALLS" + ";");
				}
			}
			
			
			if(is_this_updating == false) {
				
				this.status = CricketUtil.SUCCESSFUL;
			}	
		}
	}
	public void populateFowAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "BARODA_AR": case "EVEREST_AR_VR": case "MP_AR":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						
						//logo lgTeam
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
							    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
							    inn.getBatting_team().getTeamBadge().toUpperCase() +
							    CricketUtil.PNG_EXTENSION + ";");
						int row_id= 0 ;
						
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSelector " + "0" + ";");
							

						}
						else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
							for(FallOfWicket fow : inn.getFallsOfWickets()) {								
								if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
									row_id = row_id + 1;
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSelector " + (inn.getFallsOfWickets().size() - 1) + ";");
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET W" + row_id + " "  + fow.getFowRuns() + ";");
									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET S" + row_id + " "  + fow.getFowNumber() + ";");
								}		
							}
						}
					//	print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=50"+ ";");
						if(inn.getFallsOfWickets() != null){
							if((inn.getFallsOfWickets().size()) == 1) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=50"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=50"+ ";");	
							}else if((inn.getFallsOfWickets().size()) == 2) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=50"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=50"+ ";");
							}else if((inn.getFallsOfWickets().size()) == 3) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=50"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=50"+ ";");
							}else if((inn.getFallsOfWickets().size()) == 4) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=55"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=55"+ ";");
							}else if((inn.getFallsOfWickets().size()) == 5) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=60"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=60"+ ";");
							}else if((inn.getFallsOfWickets().size()) == 6) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=55"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=55"+ ";");
							}else if((inn.getFallsOfWickets().size()) == 7) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=53"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=53"+ ";");
							}else if((inn.getFallsOfWickets().size()) == 8) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=50"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=50"+ ";");
							}else if((inn.getFallsOfWickets().size()) == 9) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=58"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=58"+ ";");
							}else if((inn.getFallsOfWickets().size()) == 10) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=60"+ ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Wickets_Group$Score_Group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=60"+ ";");
							}
						}
						
						
					}
				}
				
				
					this.status = CricketUtil.SUCCESSFUL;
				break;
		}
	}
	
	public void populateBallMile(boolean is_this_updating, PrintWriter print_writer, int inning , int playerId ,List<Player> player,MatchAllData match, String broadcaster,Configuration config) throws InterruptedException 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getMatch().getInning() == null) {
			this.status = "ERROR: MatchPromo's inning is null";
		} else {
			//stars
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
					match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
					match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
//			//flag
//			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag_Grp$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
//					+ "C:/Everest_VR_AR/Flags/"+ match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge() +"/0001.png;");
			
			if(match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge().equalsIgnoreCase("WI")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 1;");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 0;");
			}
			//player name
			for(Player plyr : player) {
				if(plyr.getPlayerId() == playerId) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Name " + 
							plyr.getTicker_name() + ";");
					
					if(match.getSetup().getHomeTeamId() == plyr.getTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + 
								"\\\\CENTER\\\\"	+	 plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
						
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + 
								"\\\\CENTER\\\\"	+	plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					}
				}
			}
			
//			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header " + 
//					player.get(playerId - 1).getTicker_name() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Milestone_Data_1 ;");
			//base colour
//			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 "  + base_path1 + 
//					match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
			
//			if(match.getSetup().getHomeTeamId() == player.get(playerId - 1).getTeamId()) {
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*group*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getSetup().getHomeTeam().getTeamBadge().toUpperCase() + 
//						"\\\\" + player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
//				
//			}else {
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*group*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getSetup().getAwayTeam().getTeamBadge().toUpperCase() + 
//						"\\\\" + player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
//			}
			
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
			
//			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
//					match.getMatch().getInning().get(inning - 1).getBowling_team().getTeamBadge() + category + "\\" + 
//					player.get(playerId - 1).getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			
			for(BowlingCard bc : match.getMatch().getInning().get(inning - 1).getBowlingCard()) {
				if(bc.getPlayerId() == playerId) {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Runs " + 
							bc.getWickets() + "-" + bc.getRuns() + ";");
					
					if(bc.getOvers() == 0 && bc.getBalls() >=0) {
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Rest_Data " + 
								CricketFunctions.OverBalls(bc.getOvers(), bc.getBalls()) + " OVERS" + ";");
					}else if(bc.getOvers() == 1 && bc.getBalls() ==0) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Rest_Data " + 
								CricketFunctions.OverBalls(bc.getOvers(), bc.getBalls()) + " OVER" + ";");
						
					}else if(bc.getOvers() == 1 && bc.getBalls() >0) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Rest_Data " + 
								CricketFunctions.OverBalls(bc.getOvers(), bc.getBalls()) + " OVERS" + ";");
						
					}else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Rest_Data " + 
								CricketFunctions.OverBalls(bc.getOvers(), bc.getBalls()) + " OVERS" + ";");
					}
				}
			}
			
			
			if(is_this_updating == false) {
				
				this.status = CricketUtil.SUCCESSFUL;
			}	
		}
	}
	
	public void populateRunVR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						
							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 2;");
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_tg1+ inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_tg2+ inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
//							//flags
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Logo " + logo_pathtg + inn.getBatting_team().getTeamBadge().toUpperCase() + 
//									CricketUtil.PNG_EXTENSION + ";");
//							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header " + "RUN RATES" + ";");
//						
//						
//						
//						
//						//fours
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Four " + "CURRENT" + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ScoreA " + inn.getRunRate() + ";");
//						//six
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Six " + "REQUIRED" + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ScoreB " + CricketFunctions.generateRunRate(CricketFunctions.
//								GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match)  + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
						//boundries
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "RUN RATES" + ";");
						//fours
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFourHead " + "CURRENT" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + inn.getRunRate() + ";");
						//six
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "REQUIRED" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + 
								CricketFunctions.generateRunRate(CricketFunctions.
								GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + ";");
//						//flags
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + "C:/Everest_VR_AR/Logos/" + inn.getBatting_team().getTeamBadge().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
//						
						//base
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_mh1 + 
								CricketUtil.PNG_EXTENSION + ";");
//						
//						//team base
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_mh1 + 
								CricketUtil.PNG_EXTENSION + ";");
						
					}
				}
				
				this.status = CricketUtil.SUCCESSFUL;
				break;
		}
	}
	
	public void populateBoundariesAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				System.out.println("IndexController.cat"+IndexController.cat);
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						
						
						switch (session_selected_broadcaster.toUpperCase()) {
						
						case "EVEREST_AR_VR":
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_mh1+ CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_mh2+ CricketUtil.PNG_EXTENSION + ";");
							//flags
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + inn.getBatting_team().getTeamBadge().toUpperCase() + 
									CricketUtil.PNG_EXTENSION + ";");
							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path1 + inn.getBatting_team().getTeamBadge() + 
//									CricketUtil.PNG_EXTENSION + ";");
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + text_path1 + inn.getBatting_team().getTeamBadge() + 
//									CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "BOUNDARIES" + ";");
							//fours
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFourHead " + "FOURS" + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + inn.getTotalFours() + ";");
							//six
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "SIXES" + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + inn.getTotalSixes() + ";");
						break;
						
						case "EVEREST_AR_VRS": 
							
							if(is_this_updating == false) {
								
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 2;");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_tg1+ inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_tg2+ inn.getBatting_team().getTeamBadge().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
								//flags
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Logo " + logo_pathtg + inn.getBatting_team().getTeamBadge().toUpperCase() + 
										CricketUtil.PNG_EXTENSION + ";");
								
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header " + "BOUNDARIES" + ";");
							}
							
							
							
							//fours
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Four " + "FOURS" + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ScoreA " + inn.getTotalFours() + ";");
							//six
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Six " + "SIXES" + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ScoreB " + inn.getTotalSixes() + ";");
						break;
							
						case "BARODA_AR":
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_mh1+ CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_mh2+ CricketUtil.PNG_EXTENSION + ";");
							//flags
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + "C:/Everest_VR_2026/Logos/MRlogos/" + inn.getBatting_team().getTeamBadge().toUpperCase() + 
									CricketUtil.PNG_EXTENSION + ";");
							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path1 + inn.getBatting_team().getTeamBadge() + 
//									CricketUtil.PNG_EXTENSION + ";");
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + text_path1 + inn.getBatting_team().getTeamBadge() + 
//									CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "BOUNDARIES" + ";");
							//fours
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFourHead " + "FOURS" + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + inn.getTotalFours() + ";");
							//six
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "SIXES" + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + inn.getTotalSixes() + ";");
						break;
						case "MP_AR":
	
							//flags
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
								    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "BOUNDARIES" + ";");
							//fours
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFourHead " + "FOURS" + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + inn.getTotalFours() + ";");
							//six
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "SIXES" + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + inn.getTotalSixes() + ";");
						break;	
						}	
					}
				}
				
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				
				break;
		}
	}
	
	public void populateComparisonAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						
						System.out.println(match.getSetup().getMatchIdent());
						//header
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header " + match.getSetup().getMatchIdent() + ";");
						//afterthisoverdata
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET AFTER " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
						//home footer
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Home_Footer " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + ";");
						
						
						//away footer
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Away_Footer " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + ";");
						
				
						//batting team flag
						if(inn.getBowling_team().getTeamBadge().equalsIgnoreCase("WI")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor 1;");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor 0;");
						}
						//bowling team flag
						if(inn.getBatting_team().getTeamBadge().equalsIgnoreCase("WI")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor1 1;");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor1 0;");
						}
						
						
						
//						//stars colour
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
								inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
								inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base1 " + base_path1 +  
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base2 " + base_path2 +  
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
						
					
					}
				}
				
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateComparisonVR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						switch (session_selected_broadcaster.toUpperCase()) {
						
						case "MP_AR":
							if(is_this_updating == false) {	
							//flags
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team1Base1 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBowling_team().getTeamBadge() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team1Base2 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBowling_team().getTeamBadge() +
								    CricketUtil.PNG_EXTENSION + ";");	
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + 
								    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBowling_team().getTeamBadge() +
								    CricketUtil.PNG_EXTENSION + ";");
							//bating
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team2Base1 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team2Base2 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge() +
								    CricketUtil.PNG_EXTENSION + ";");	
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + 
								    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge() +
								    CricketUtil.PNG_EXTENSION + ";");
							}
							break;
						
						case "EVEREST_AR_VR":
							if(is_this_updating == false) {
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team1Base1 " + base_path_mh1+ 
//										CricketUtil.PNG_EXTENSION + ";");
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_mh2 + 
//										CricketUtil.PNG_EXTENSION + ";");
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team2Base1 " + base_path_bp1  + 
//										CricketUtil.PNG_EXTENSION + ";");
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team2Base2 " + base_path_bp2  + 
//										CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");  
							
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team1Base1 " + base_path_mh1 +
										CricketUtil.PNG_EXTENSION + ";");
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET HOME_TEXT1 " + text_path1 + inn.getBowling_team().getTeamBadge() + 
//										CricketUtil.PNG_EXTENSION + ";");
								
								
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team2Base1 " + base_path_mh1 +
										CricketUtil.PNG_EXTENSION + ";");
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET AWAY_TEXT1 " + text_path1 + inn.getBatting_team().getTeamBadge() + 
//										CricketUtil.PNG_EXTENSION + ";");
								
							
							}
							
							break;
							
						case "EVEREST_AR_VRS": 
							
							if(is_this_updating == false) {	
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 6;");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_tg1+ 
										inn.getBowling_team().getTeamBadge()	+ CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_tg2 + 
										inn.getBowling_team().getTeamBadge()+ 
										CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Logo " + logo_pathtg + inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
								
								
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ABase1 " + base_path_tg1+ 
										inn.getBatting_team().getTeamBadge()	+ CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ABase2 " + base_path_tg2 + 
										inn.getBatting_team().getTeamBadge()+ 
										CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ALogo " + logo_pathtg + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
								}
							
							break;
						case "BARODA_AR":
							if(is_this_updating == false) {
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_bp1+ 
										CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_bp2 + 
										CricketUtil.PNG_EXTENSION + ";");
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team2Base1 " + base_path_bp1  + 
//										CricketUtil.PNG_EXTENSION + ";");
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team2Base2 " + base_path_bp2  + 
//										CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + inn.getBowling_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");  
							
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team1Base1 " + base_path1 + inn.getBowling_team().getTeamBadge() + 
										CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET HOME_TEXT1 " + text_path1 + inn.getBowling_team().getTeamBadge() + 
										CricketUtil.PNG_EXTENSION + ";");
								
								
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team2Base1 " + base_path1 + inn.getBatting_team().getTeamBadge() + 
										CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET AWAY_TEXT1 " + text_path1 + inn.getBatting_team().getTeamBadge() + 
										CricketUtil.PNG_EXTENSION + ";");
								
							
							}
							
							break;
						}
						//team one
						//batting team flag
						
//						if(inn.getBowling_team().getTeamBadge().equalsIgnoreCase("WI")) {
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor 1;");
//						}else {
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor 0;");
//						}
						//home footer
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreA " + CricketFunctions.compareInningData(match, "-", 1, match.getEventFile().getEvents()) + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + inn.getBowling_team().getTeamName3() + ";");
						
						
						//team2
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScoreB " + CricketFunctions.compareInningData(match, "-", 2, match.getEventFile().getEvents()) + ";");
 
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamB " + inn.getBatting_team().getTeamName3() + ";");
//						if(inn.getBatting_team().getTeamBadge().equalsIgnoreCase("WI")) {
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor1 1;");
//						}else {
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor1 0;");
//						}
//						//header
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getSetup().getMatchIdent() + ";");
						//afterthisoverdata
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
						//bowling team flag
						
						
						
//						//stars colour
	//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
	//								match.getSetup().getHomeTeam().getTeamName4() + "_base1" + CricketUtil.PNG_EXTENSION + ";");
	//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
	//								match.getSetup().getHomeTeam().getTeamName4() + "_base2" + CricketUtil.PNG_EXTENSION + ";");
	//						
	//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base1 " + base_path1 +  
	//								match.getSetup().getAwayTeam().getTeamName4() + "_base1" + CricketUtil.PNG_EXTENSION + ";");
	//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base2 " + base_path2 +  
	//								match.getSetup().getAwayTeam().getTeamName4() + "_base2" + CricketUtil.PNG_EXTENSION + ";");
	//						
					}
				}
				
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateMatchPromoAnimationAR(boolean is_this_updating, PrintWriter print_writer,String viz_sence_path, int match_number ,List<Team> team,List<Fixture> fix,
			MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
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
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateMatchIdAnimationAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
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
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateMatchIdAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeam " + 
//						match.getSetup().getHomeTeam().getTeamName4() + ";");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeam " + 
//						match.getSetup().getAwayTeam().getTeamName4() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flags$Home_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
						+ "C:/Everest_VR_AR/Flags/"+ match.getSetup().getHomeTeam().getTeamBadge() +"/0000.png;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flags$Away_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
						+ "C:/Everest_VR_AR/Flags/"+ match.getSetup().getAwayTeam().getTeamBadge() +"/0000.png;");
				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
//						match.getSetup().getHomeTeam().getTeamName1() + ";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
//						match.getSetup().getAwayTeam().getTeamName1() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET T_Header " + match.getSetup().getMatchIdent() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET T_Footer " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
						match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
						match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base1 " + base_path1 +  
						match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base2 " + base_path2 +  
						match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
			
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	public void populateMatchIdVR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				switch (session_selected_broadcaster.toUpperCase()) {
				case "MP_AR":
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Data_Selector 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 1;");
					
					
					//flags
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + 
						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\"  + 
						    "TLogo" +
						    CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + 
						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + 
						    "TLogo" +
						    CricketUtil.PNG_EXTENSION + ";");	
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + 
						    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
						    match.getSetup().getHomeTeam().getTeamBadge() +
						    CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + 
						    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
						    match.getSetup().getAwayTeam().getTeamBadge() +
						    CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
							match.getSetup().getHomeTeam().getTeamName2() +  "\r\n" +  match.getSetup().getHomeTeam().getTeamName3()+ ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
							match.getSetup().getAwayTeam().getTeamName2()  + "\r\n" +   match.getSetup().getAwayTeam().getTeamName3()+ ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead "  +  match.getSetup().getMatchIdent() + ";");
					
					break;
				case "EVEREST_AR_VR":
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Data_Selector 1;");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_mh1+ CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_bp2+ CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
							match.getSetup().getHomeTeam().getTeamName1() + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
							match.getSetup().getAwayTeam().getTeamName1() + ";");
					

					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
							match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
							match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead "  +  match.getSetup().getMatchIdent() + ";");
					
					break;	
				case "EVEREST_AR_VRS": 
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 0;");
		
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header "  +  match.getSetup().getMatchIdent() + ";");
					
					
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET HomeName " + 
//							match.getSetup().getHomeTeam().getTeamName2() +  "\r\n" +  match.getSetup().getHomeTeam().getTeamName3()+ ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET AwayName " + 
//							match.getSetup().getAwayTeam().getTeamName2()  + "\r\n" +   match.getSetup().getAwayTeam().getTeamName3()+ ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET HomeName " + 
						    match.getSetup().getHomeTeam().getTeamName3()+ ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET AwayName " + 
							    match.getSetup().getAwayTeam().getTeamName3()+ ";");

					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET HomeLogo " + logo_pathtg + 
							match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET AwayLogo " + logo_pathtg + 
							match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					
					break;
				case "BARODA_AR":
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Data_Selector 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_bp1+ CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_bp2+ CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
							match.getSetup().getHomeTeam().getTeamName2() +  "\r\n" +  match.getSetup().getHomeTeam().getTeamName3()+ ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
							match.getSetup().getAwayTeam().getTeamName2()  + "\r\n" +   match.getSetup().getAwayTeam().getTeamName3()+ ";");
					

					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
							match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
							match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					break;
					}
				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
//						match.getSetup().getHomeTeam().getTeamName1() + ";");
				
				
					
				
					
				
				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHead " + 
//						match.getSetup().getMatchIdent() + ";");
				
				
				
				//footer
				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Footer " + "LIVE FROM " + match.getSetup().getVenueName().toUpperCase() + ";");
				
				//logo
				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
//						match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
//						match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Logos$Home_Team_Logo*FUNCTION*IMAGESEQUENCE2 SET PATH "
//						+ "C:/Everest_VR_AR/Flags/"+ match.getSetup().getHomeTeam().getTeamBadge() +"/0000.png;");
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Logos$Away_Team_Logo*FUNCTION*IMAGESEQUENCE2 SET PATH "
//						+ "C:/Everest_VR_AR/Flags/"+ match.getSetup().getAwayTeam().getTeamBadge() +"/0000.png;");
				
			
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateMatchIdARR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				switch (session_selected_broadcaster.toUpperCase()) {
				
				case "EVEREST_AR_VR": 

					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Home_Team_Name " + 
						    match.getSetup().getHomeTeam().getTeamName3()+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Away_Team_Name " + 
							    match.getSetup().getAwayTeam().getTeamName3()+ ";");

					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Home_Team_Logo " + logo_pathtg + 
							match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Away_Team_Logo " + logo_pathtg + 
							match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header_text " + 
						    "FINAL" + ";");
					break;
				}
				
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populatetossVR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster,String teamid,String val) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				//team name
				
				switch (session_selected_broadcaster.toUpperCase()) {
				case "MP_AR":
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Data_Selector 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 1;");

					//flags
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + 
						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + 
						    "TLogo" +
						    CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + 
						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + 
						    "TLogo" +
						    CricketUtil.PNG_EXTENSION + ";");	
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + 
						    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
						    match.getSetup().getHomeTeam().getTeamBadge() +
						    CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + 
						    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
						    match.getSetup().getAwayTeam().getTeamBadge() +
						    CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
							match.getSetup().getHomeTeam().getTeamName2() +  "\r\n" +  match.getSetup().getHomeTeam().getTeamName3()+ ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
							match.getSetup().getAwayTeam().getTeamName2()  + "\r\n" +   match.getSetup().getAwayTeam().getTeamName3()+ ";");
					break;
				case "EVEREST_AR_VR":
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Data_Selector 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_bp1+ CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_bp2+ CricketUtil.PNG_EXTENSION + ";");

					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
							match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
							match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
							match.getSetup().getHomeTeam().getTeamName2() +  "\r\n" +  match.getSetup().getHomeTeam().getTeamName3()+ ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
							match.getSetup().getAwayTeam().getTeamName2()  + "\r\n" +   match.getSetup().getAwayTeam().getTeamName3()+ ";");
					break;	
				case "EVEREST_AR_VRS": 
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 0;");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header "  +  match.getSetup().getMatchIdent() + ";");
					
					
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET HomeName " + 
//							match.getSetup().getHomeTeam().getTeamName2() +  "\r\n" +  match.getSetup().getHomeTeam().getTeamName3()+ ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET AwayName " + 
//							match.getSetup().getAwayTeam().getTeamName2()  + "\r\n" +   match.getSetup().getAwayTeam().getTeamName3()+ ";");

					
					
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET HomeName " + 
							  match.getSetup().getHomeTeam().getTeamName3()+ ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET AwayName " + 
							   match.getSetup().getAwayTeam().getTeamName3()+ ";");
					
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET HomeLogo " + logo_pathtg + 
							match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET AwayLogo " + logo_pathtg + 
							match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					break;
				case "BARODA_AR":
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Data_Selector 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_bp1+ CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_bp2+ CricketUtil.PNG_EXTENSION + ";");

					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeam " + logo_path + 
							match.getSetup().getHomeTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeam " + logo_path + 
							match.getSetup().getAwayTeam().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + 
							match.getSetup().getHomeTeam().getTeamName2() +  "\r\n" +  match.getSetup().getHomeTeam().getTeamName3()+ ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + 
							match.getSetup().getAwayTeam().getTeamName2()  + "\r\n" +   match.getSetup().getAwayTeam().getTeamName3()+ ";");
					break;
					}
				
				
				String data = "",teamName = "";
				if(match.getSetup().getHomeTeamId()== Integer.valueOf(teamid)) {
					data = match.getSetup().getHomeTeam().getTeamName3() + " WON THE TOSS AND" + " "+  val;
					teamName = match.getSetup().getHomeTeam().getTeamBadge();
				}else {
					data = match.getSetup().getAwayTeam().getTeamName3() + " WON THE TOSS AND" + " "+ val;
					teamName = match.getSetup().getAwayTeam().getTeamBadge();
				}
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header "  +  match.getSetup().getMatchIdent() + ";");

				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Footer " + data+ ";");
				
		
			
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	
	public void populatePlayerCelebAR(PrintWriter print_writer,MatchAllData match,String data ,String data2,String data3,List<Player> plyr,List<Team> tm,
			String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
			
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + data2 +";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText " + data2 +";");
			
//			for(Team tem : tm) {
//				if(tem.getTeamId() == Integer.valueOf(data3)) {
//					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamlogo " + logo_path2 + tem.getTeamBadge() + ".png" +";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamlogo " + logo_path2 + tem.getTeamBadge() + ".png" +";");
//				}
//			}
			
			for(Player plr : plyr) {
				if(plr.getPlayerId() == Integer.valueOf(data)) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName " + plr.getTicker_name() +";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tName " + plr.getTicker_name() +";");
				}
			}
			
		    
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	
	public void populateNextToBat(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster,Configuration config,CricketService cricketService) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				int row_id = 0;
				String strike_rate = "";
				//header
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header " + "NEXT TO BAT" +";");
				
				//bas
				for (Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						
						//stars
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");	
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 "  + base_path1 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");	
						
						
						for (int b = 1; b <= inn.getBattingCard().size(); b++) {
							if (inn.getBattingCard().get(b - 1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT))
							{
								row_id = row_id + 1;
								if (row_id <= 3) {
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player" + row_id + " " + photo_path + inn.getBatting_team().getTeamName4() + 
											"\\\\CENTER\\\\" + inn.getBattingCard().get(b - 1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
									
									for(Statistics st:cricketService.getAllStats())
									{
										if(st.getPlayerID() == inn.getBattingCard().get(b - 1).getPlayerId()) {
											
											if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.ODI)) {
												if(st.getStatsTypeId() == 1) {
													
													if(CricketFunctions.getAverage(st.getInnings(), st.getNotOut(), st.getRuns(), 2, "-").equalsIgnoreCase("0.00")) {
														strike_rate = " --";
													}else {
														strike_rate = CricketFunctions.getAverage(st.getInnings(), st.getNotOut(), st.getRuns(), 2, "-");
													}
												}
											}else {
												if(st.getStatsTypeId() == 3) {					
													strike_rate = CricketFunctions.generateStrikeRate(st.getRuns(), st.getBallsFaced(), 0);
													System.out.println(strike_rate);
													if(strike_rate.isEmpty()) {
														strike_rate = " --";
													}
												}
											}
										}
									}
									
									//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info" + row_id + "A " + b + ";");
									
									if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.ODI)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info" + row_id + " " + 
												inn.getBattingCard().get(b - 1).getPlayer().getTicker_name() +  " \n AVG:" + strike_rate + ";");
									}else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info" + row_id + " " + "IN AT "+ b +
											"\n"+	inn.getBattingCard().get(b - 1).getPlayer().getTicker_name() +  " \n S/R:" + strike_rate + ";");
									}
								}
							}
						}

					}
				}
				
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	
	public void populatePlayerInAt(PrintWriter print_writer,MatchAllData match,int Inning ,int playerId,List<Player> plyr,List<Team> tm,
			String session_selected_broadcaster,Configuration config) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
			
			inning = match.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Inning).findAny().orElse(null);
			
			battingCardList = inning.getBattingCard();
			
			int row_id = 0;
			
			int inAtPosition = 0;
			
			for(Player pl: plyr) {
				if(pl.getPlayerId() == playerId) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET PLayer_Image " + photo_pathtg  + match.getMatch().getInning().get(Inning - 1).getBatting_team().getTeamBadge()  
							+ "\\" + pl.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
					
					System.out.println(inning.getTotalWickets());
					
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Name " + 
							pl.getTicker_name() + ";");
//					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Data_Colour " + 
//							pl.getTicker_name() + ";");
				}
			}
//		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 "  + base_path1 + match.getMatch().getInning().get(Inning - 1).getBatting_team().getTeamBadge() 
//			 + CricketUtil.PNG_EXTENSION + ";");
//			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag_Grp$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
//					+ "C:/Everest_VR_AR/Flags/"+ match.getMatch().getInning().get(Inning - 1).getBatting_team().getTeamBadge() +"/0000.png;");
			
			
		
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET IN_AT " + 
					"IN AT" + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET In_At_Position " + (inning.getTotalWickets() + 1)
					+ ";");
			
//			Collections.sort(inning.getBattingCard());
//			for (BattingCard bc : inning.getBattingCard()) {
//				row_id = row_id + 1;
//				
//				if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
//					inAtPosition++;
//					if(bc.getHowOut() != null) {
//						if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
//							if(bc.getPlayerId() == playerId) {
//								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET In_At_Position " + inAtPosition + ";");
//							}
//						}
//					}else {
//						if(bc.getPlayerId() == playerId) {
//							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET In_At_Position " + inAtPosition + ";");
//						}
//					}
//				}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
//					inAtPosition++;
//					if(bc.getPlayerId() == playerId) {
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET In_At_Position " + inAtPosition + ";");
//					}
//				}else {
//					
//					if(bc.getHowOut() != null) {
//						if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
//							if(bc.getPlayerId() == playerId) {
//								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET In_At_Position " + bc.getBatterPosition() + ";");
//							}
//						}else {
//							inAtPosition++;
//							if(bc.getPlayerId() == playerId) {
//								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET In_At_Position " + inAtPosition + ";");
//							}
//						}
//					}
//				}
//			}
			
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	
	public void populateTeamCelebAR(PrintWriter print_writer,MatchAllData match,String data ,String data2,List<Team> tm,String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
			
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
			
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}
	}
	
	/*
	 * public void populateFreeTextAR(PrintWriter print_writer,MatchAllData
	 * match,String data,String session_selected_broadcaster) throws
	 * InterruptedException, IOException { switch
	 * (session_selected_broadcaster.toUpperCase()) { case "EVEREST_AR_VR": case
	 * "BARODA_AR": case "MP_AR": String text1_to_return = "",text2_to_return = "";
	 * 
	 * // switch (data) { // case "NO_LOGO": // print_writer.
	 * println("LAYER1*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 0;"); //
	 * print_writer.
	 * println("LAYER3*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 0;"); //
	 * break; // // default: // print_writer.
	 * println("LAYER1*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 1;"); //
	 * print_writer.
	 * println("LAYER3*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 1;"); // //
	 * print_writer.
	 * println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " +
	 * logo_path + // data.toUpperCase() + ".png" +";"); // print_writer.
	 * println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " +
	 * logo_path + // data.toUpperCase() + ".png" +";"); // break; // }
	 * print_writer.
	 * println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 7;"
	 * );
	 * 
	 * print_writer.
	 * println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Logo " +
	 * logo_pathtg + data.toUpperCase() + ".png" +";"); print_writer.
	 * println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Logo " +
	 * logo_pathtg + data.toUpperCase() + ".png" +";");
	 * 
	 * BufferedReader br = new BufferedReader(new
	 * FileReader(CricketUtil.CRICKET_DIRECTORY + CricketUtil.AR_FREE_TXT));
	 * 
	 * String line1 = br.readLine(); String line2 = br.readLine();
	 * 
	 * br.close();
	 * 
	 * if (line1 != null) { print_writer.
	 * println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header " +
	 * line1.toUpperCase() + ";"); print_writer.
	 * println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header " +
	 * line1.toUpperCase() + ";"); } else { print_writer.
	 * println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header ;");
	 * print_writer.
	 * println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header ;");
	 * System.out.println("File is empty"); }
	 * 
	 * if (line2 != null) { print_writer.
	 * println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header2 " +
	 * line2.toUpperCase() + ";"); print_writer.
	 * println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header2 " +
	 * line2.toUpperCase() + ";"); } else { print_writer.
	 * println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header2 ;");
	 * print_writer.
	 * println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header2 ;");
	 * System.out.println("Only 1 line in file"); } this.status =
	 * CricketUtil.SUCCESSFUL; break; } }
	 */
	
	public void populateFreeTextAR(PrintWriter print_writer,MatchAllData match,String data,String session_selected_broadcaster) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
			String text1_to_return = "",text2_to_return = "";
			
//			switch (data) {
//			case "NO_LOGO":
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 0;");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 0;");
//				break;
//
//			default:
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 1;");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 1;");
//				
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
//						data.toUpperCase() + ".png" +";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
//						data.toUpperCase() + ".png" +";");
//				break;
//			}
		//	print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 7;");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
					data.toUpperCase() + ".png" +";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
					data.toUpperCase() + ".png" +";");
			
			BufferedReader br = new BufferedReader(new FileReader(CricketUtil.CRICKET_DIRECTORY + CricketUtil.AR_FREE_TXT));

			String line1 = br.readLine();
			String line2 = br.readLine(); 

			br.close();

			if (line1 != null) {
			    print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + line1.toUpperCase() + ";");
			    print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + line1.toUpperCase() + ";");
			} else {
			    print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader ;");
			    print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader ;");
			    System.out.println("File is empty");
			}

			if (line2 != null) {
			    print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader2 " + line2.toUpperCase() + ";");
			    print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader2 " + line2.toUpperCase() + ";");
			} else {
			    print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader2 ;");
			    print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader2 ;");
			    System.out.println("Only 1 line in file");
			}
			this.status = CricketUtil.SUCCESSFUL;
			break;
	}
	}
	
	public void populateProjectedAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
				for(Inning inn : match.getMatch().getInning()) {
					
					
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						
						//base colour
						
						//header
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header " + "PROJECTED SCORES" + ";");
						//flag
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flags$Home_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
								+ "C:/Everest_VR_AR/Flags/"+ inn.getBatting_team().getTeamName4() +"/0000.png;");
						
						if(inn.getBatting_team().getTeamBadge().equalsIgnoreCase("WI")) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 1;");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 0;");
						}
						//stars colour
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						//projected score part
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET CRR " + "CRR("+ proj_score_rate[0] +") @ "+ proj_score_rate[1] + " RPO"+ ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET CRR " + proj_score_rate[1] + " @CRR (" +  proj_score_rate[0] + ")" +  ";");
						
						
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Projected_1 " + proj_score_rate[2] +" @ "+ "RPO " + proj_score_rate[3]  + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Projected_1 " + proj_score_rate[3] + " @ " + proj_score_rate[2] + " RPO" + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Projected_2 "  + proj_score_rate[5] + " @ " + proj_score_rate[4] + " RPO" + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");	
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
//								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
//						//base
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor1 " + base_path + 
//								"Base1\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
//						//
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_HomeColor2 " + base_path + 
//								"Base2\\\\" + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead01 " + "@"+ proj_score_rate[0] +" (CRR)" + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + proj_score_rate[1] + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead02 " + "@" + proj_score_rate[2] +" RPO"+ ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + proj_score_rate[3] + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead03 " + "@" + proj_score_rate[4] +" RPO" + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + proj_score_rate[5] + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead04 " + "@" + proj_score_rate[6] +" RPO" + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue04 " + proj_score_rate[7] + ";");
						
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
						
	
						
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead01 " + "@"+ proj_score_rate[0] +" (CRR)" + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + proj_score_rate[1] + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead02 " + "@" + proj_score_rate[2] +" RPO"+ ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + proj_score_rate[3] + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead03 " + "@" + proj_score_rate[4] +" RPO" + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + proj_score_rate[5] + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead04 " + "@" + proj_score_rate[6] +" RPO" + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue04 " + proj_score_rate[7] + ";");
					}
				}
				
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	public void populateProjectedVR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				String[] proj_score_rate = new String[CricketFunctions.projectedScore(match).size()];
			    for (int i = 0; i < CricketFunctions.projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = CricketFunctions.projectedScore(match).get(i);
		        }
				for(Inning inn : match.getMatch().getInning()) {
					
					
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						
						if(is_this_updating == false) {
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 1;");
						}
						
						//header
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "PROJECTED" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader1 " + "SCORES" + ";");

						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead01 " + "@CRR (" + proj_score_rate[0] + ")" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + proj_score_rate[1] + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead02 " + "@" + proj_score_rate[2] +" RPO"+ ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + proj_score_rate[3] + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStarHead03 " + "@" + proj_score_rate[4] +" RPO" + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + proj_score_rate[5] + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET StatHead4 " + "@" + proj_score_rate[6] +" RPO" + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET StatValue4 " + proj_score_rate[7] + ";");
						
						
						switch (session_selected_broadcaster.toUpperCase()) {
						case "EVEREST_AR_VRS": 
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Logo " + 
									logo_pathtg  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						break;
						case "EVEREST_AR_VR":
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_mh1  + CricketUtil.PNG_EXTENSION + ";");
//								//
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base2 " + base_path_bp2 + CricketUtil.PNG_EXTENSION + ";");
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base3 " + base_path_bp2 + CricketUtil.PNG_EXTENSION + ";");	
								//logo
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
								logo_path  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						break;
						case "BARODA_AR":
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 " + base_path_bp1  + CricketUtil.PNG_EXTENSION + ";");
//								//
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base2 " + base_path_bp2 + CricketUtil.PNG_EXTENSION + ";");
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base3 " + base_path_bp2 + CricketUtil.PNG_EXTENSION + ";");	
								//logo
								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
								logo_path  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						break;
						
						case "MP_AR":
							
							//flags
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base2 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base3 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");	
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
								    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");

						break;
						
						}
					}
				}
				
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateCurrentPartnership(PrintWriter print_writer, String viz_scene, MatchAllData match, String session_selected_broadcaster, Configuration config) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Partnership's inning is null";
			} else {
				this.status = CricketUtil.SUCCESSFUL;
				String Left_Batsman ="",Right_Batsman="";
		
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						//player1 part
						Left_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getTicker_name();
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Left_Player_Data " + Left_Batsman + ";");
						Right_Batsman = inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getTicker_name();
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Right_Player_Data " + Right_Batsman + ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 "  + base_path1 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						//Stars
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						//right stars
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base1 "  + base_path1 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base2 "  + base_path2 + 
								inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						//flag
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flags$Home_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
								+ "C:/Everest_VR_AR/Flags/"+ inn.getBatting_team().getTeamName4() +"/0001.png;");
						
						System.out.println("CURRENT /n PARTNERSHIP");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET CRNT_PRNERSHP " + "CURRENT \n PARTNERSHIP" + ";");
						//runs and ball part
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RUNS " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns()+ "*" + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RUNS_COLOUR " + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + "(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")"+ ";");
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo1 " + photo_path + inn.getBatting_team().getTeamName4().toUpperCase() + 
								"\\\\CENTER\\\\" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo2 " + photo_path + inn.getBatting_team().getTeamName4().toUpperCase() + 
								"\\\\CENTER\\\\" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
					}
		        }
				
			}
			break;
		}
	}
	public void populateTossAR(PrintWriter print_writer,MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Inning is null";
			} else {
				
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET T_Footer " + 
				match.getSetup().getHomeTeam().getTeamName4().toUpperCase() + " WON TOSS " +"CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");
					
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flags$Home_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
							+ "C:/Everest_VR_AR/Flags/"+ match.getSetup().getHomeTeam().getTeamBadge() +"/0000.png;");	
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flags$Away_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
							+ "C:/Everest_VR_AR/Flags/"+ match.getSetup().getAwayTeam().getTeamBadge() +"/0000.png;");	
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
							match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
							match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base1 " + base_path1 +  
							match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base2 " + base_path2 +  
							match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");

				}else {
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flags$Home_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
							+ "C:/Everest_VR_AR/Flags/"+ match.getSetup().getAwayTeam().getTeamBadge() +"/0000.png;");	
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flags$Away_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
							+ "C:/Everest_VR_AR/Flags/"+ match.getSetup().getHomeTeam().getTeamBadge() +"/0000.png;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET T_Footer " + 
							match.getSetup().getAwayTeam().getTeamName4().toUpperCase() + " WON TOSS " + "CHOSE TO " + match.getSetup().getTossWinningDecision() + ";");

								
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
								match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
								match.getSetup().getAwayTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");			
								
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base1 " + base_path1 +  
								match.getSetup().getHomeTeam().getTeamName4()  + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Away_base2 " + base_path2 +  
								match.getSetup().getHomeTeam().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
								
	
				}
				
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET T_Header " + match.getSetup().getMatchIdent() + ";");

				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	
	public void populateLastBoundary(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "BARODA_AR": case "MP_AR": case "EVEREST_AR_VR":
			
			for(Inning inn : match.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 7;");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + "BALLS SINCE"+ ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore "
							+ (CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, match.getEventFile().getEvents(),inn.getInningNumber())) + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header2 " + "LAST BOUNDARY" + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + "LAST BOUNDARY" + ";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
							inn.getBatting_team().getTeamBadge() + ".png" +";");
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 " + 
						    base_path_mh1 +
						    CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base2 " + 
//							base_path_tg2 + "EVENT" + 
//						    CricketUtil.PNG_EXTENSION + ";");
					
			switch (session_selected_broadcaster.toUpperCase()) {	
				case "MP_AR":
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 " + 
						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
						    inn.getBatting_team().getTeamBadge().toUpperCase() +
						    CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base2 " + 
						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + IndexController.cat + "\\\\" + 
						    inn.getBowling_team().getTeamBadge().toUpperCase() +
						    CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
						    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
						    inn.getBatting_team().getTeamBadge().toUpperCase() +
						    CricketUtil.PNG_EXTENSION + ";");

				break;	
				case "BARODA_AR":
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
							inn.getBowling_team().getTeamBadge() + ".png" +";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path1 + inn.getBatting_team().getTeamBadge() + 
//							CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + text_path1 + inn.getBatting_team().getTeamBadge() + 
//							CricketUtil.PNG_EXTENSION + ";");
				break;	
					}
				}
			}
//			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header2 " + "LAST BOUNDARY" + ";");
			break;
		}
		
		
		
	}
	public void populateTargetAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster ,Configuration config) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				//team name 
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team_Name " + match.getMatch().getInning().get(1).getBatting_team().getTeamName2()  +";");
				//target
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TARGET " + "TARGET"  +";");
				
				//star base
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				//flag
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flags$Home_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
						+ "C:/Everest_VR_AR/Flags/"+ match.getMatch().getInning().get(1).getBatting_team().getTeamName4() +"/0000.png;");
				

//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
//						+ CricketUtil.PNG_EXTENSION + ";");
//				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RUNS " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
				
				//print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info LIVE FROM " + match.getSetup().getVenueName() + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
				
				
				this.status = CricketUtil.SUCCESSFUL;
				break;
		}
	}
	
	public void populateEquationTargetImageAR(boolean is_this_updating,PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster ,Configuration config) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				//team name 
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team_Name " + match.getMatch().getInning().get(1).getBatting_team().getTeamName2()  +";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 "  + base_path1 + 
						 match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				//target
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TARGET " + "TARGET"  +";");
				
				//star base
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				//flag
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
//						+ "C:/Everest_VR_AR/Flags/"+ match.getMatch().getInning().get(1).getBatting_team().getTeamName4() +"/0000.png;");
				
				if(match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().equalsIgnoreCase("WI")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 1;");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 0;");
				}
				//playerimmage
				if(match.getMatch().getInning().get(1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {
					for(Player py:match.getSetup().getHomeSquad()) {
						if(py.getCaptainWicketKeeper() !=null) {
							if(py.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) ||
									py.getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() +  
										"\\\\CENTER\\\\" + py.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
							}
						}
					}
				}else {
					for(Player py:match.getSetup().getAwaySquad()) {
						if(py.getCaptainWicketKeeper() !=null) {
							if(py.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) ||
									py.getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() +  
										"\\\\CENTER\\\\" + py.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
							}
						}
					}
					
				}
				

//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
//						+ CricketUtil.PNG_EXTENSION + ";");
//				
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET NEED " + "NEED" + "\n" + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN"+ 
						CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + "\n" + "OFF" + "\n" + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
						CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() +";");
				//print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info LIVE FROM " + match.getSetup().getVenueName() + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
				this.status = CricketUtil.SUCCESSFUL;
				break;
		}
	}
	
	public void populateTargetVR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 5;");
//				//target
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header TARGET;");
//				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ScoreA " + 
//						CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
//				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Overs " +  "OFF " +
//						Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) + " OVERS" +  ";");
//				
//				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RRHead " + "@" + CricketFunctions.generateRunRate(CricketFunctions.
//						GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) +";");
//				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RR " + "RUNS PER OVER" +";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Logo " + logo_pathtg + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + 
//						CricketUtil.PNG_EXTENSION + ";");
				
				
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 2;");
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_mh1 + CricketUtil.PNG_EXTENSION + ";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_tg2+ match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				//flags
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + 
						CricketUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "TARGET" + ";");
			
			
			
			//fours
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFourHead " + "RUNS" + ";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
			//six
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "OVERS" + ";");
			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) + ";");
//				}
				switch (session_selected_broadcaster.toUpperCase()) {
				 case "BARODA_AR":
					 print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + 
								CricketUtil.PNG_EXTENSION + ";");
					 break;
				 case "MP_AR":
					 print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
							    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
							    match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() +
							    CricketUtil.PNG_EXTENSION + ";");
					 
					 print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 " + 
							    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
							    match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() +
							    CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 " + 
							    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + IndexController.cat + "\\\\" + 
							    match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().toUpperCase() +
							    CricketUtil.PNG_EXTENSION + ";");
					 break;
				}
				this.status = CricketUtil.SUCCESSFUL;
				break;
		}
	}
	
	public void populateRunRate(PrintWriter print_writer,boolean is_this_updating, MatchAllData match, String session_selected_broadcaster,String valueToProcess) throws InterruptedException, IOException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
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
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
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
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	public void populateThisOver(boolean is_this_updating, PrintWriter print_writer,String data,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				
				
				switch (session_selected_broadcaster.toUpperCase()) {
				
				case "EVEREST_AR_VRS": 
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selctor 0;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_1 " + base_path_mh1 + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_2 " + base_path_mh2 + CricketUtil.PNG_EXTENSION + ";");
				break;
				case "EVEREST_AR_VR":
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selctor 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_bp1+ CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_bp2+ CricketUtil.PNG_EXTENSION + ";");
					
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path1 + inn.getBatting_team().getTeamBadge() + 
//							CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + text_path1 + inn.getBatting_team().getTeamBadge() + 
//							CricketUtil.PNG_EXTENSION + ";");
				break;
				case "BARODA_AR":
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selctor 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_bp1+ CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_bp2+ CricketUtil.PNG_EXTENSION + ";");
					
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path1 + inn.getBatting_team().getTeamBadge() + 
//							CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + text_path1 + inn.getBatting_team().getTeamBadge() + 
//							CricketUtil.PNG_EXTENSION + ";");
				break;
				case "MP_AR":
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selctor 1;");
					//flags
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + 
						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + 
						    "TLogo"+
						    CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + 
						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + 
						    "TLogo"+
						    CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base2 " + 
//						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
//						    inn.getBatting_team().getTeamBadge().toUpperCase() +
//						    CricketUtil.PNG_EXTENSION + ";");
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base3 " + 
//						    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
//						    inn.getBatting_team().getTeamBadge().toUpperCase() +
//						    CricketUtil.PNG_EXTENSION + ";");	
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
//						    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
//						    inn.getBatting_team().getTeamBadge().toUpperCase() +
//						    CricketUtil.PNG_EXTENSION + ";");

				break;
				}
				
				
				switch (data) {
				case "NO_LOGO":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 0;");
					break;

				default:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$Logo*CONTAINER SET ACTIVE 1;");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
							data.toUpperCase() + CricketUtil.PNG_EXTENSION +";");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + 
							data.toUpperCase() + CricketUtil.PNG_EXTENSION +";");
					break;
				}
				
				for(int i=1;i<=9;i++) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ i + " ;");
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0"+ i + " ;");
				}
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "THIS OVER" + ";");
				
				Team team = null;
				
				for(Inning inn : match.getMatch().getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						team = inn.getBowling_team();
					}
				}
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path  +
//						 D.getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "+ base_path1 + data.toUpperCase() + CricketUtil.PNG_EXTENSION +  " ;");
				//base colour
				
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
//								if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].equalsIgnoreCase("W") || 
//										this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("W+")) 
//								{
//									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Base0" + (iBall+1) + " " + base_path + 
//											"Base2\\\\" + "EVENT" + CricketUtil.PNG_EXTENSION + ";");
//									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Base0" + (iBall+1) + " " + base_path + 
//											"Base2\\\\" + "EVENT" + CricketUtil.PNG_EXTENSION + ";");
//								}
//								else{
//									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Base0" + (iBall+1) + " " + ";");
//									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lg_Base0" + (iBall+1) + " " + ";");
//								}
								
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
//				
//				if((totalOverSize-1) == 5) {
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$DATA_GRP*FUNCTION_SET_PROP*GRID_ARRANGE distCol=155"+ ";");
//				}else if((totalOverSize-1) == 6) {
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$DATA_GRP*FUNCTION_SET_PROP*GRID_ARRANGE distCol=155"+ ";");
//				}else if((totalOverSize-1) == 7) {
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$DATA_GRP*FUNCTION_SET_PROP*GRID_ARRANGE distCol=134"+ ";");
//				}else if((totalOverSize-1) == 8) {
//					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$DATA_GRP*FUNCTION_SET_PROP*GRID_ARRANGE distCol=112"+ ";");
//				}
//				
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$object$RightData$Side" + WhichSide + "$OverThis$Over"
//						+ "*FUNCTION*Grid*num_col SET " + totalOverSize + "\0", print_writers);
				
				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$group*FUNCTION_SET_PROP*GRID_ARRANGE distCol=120"+ ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$group*FUNCTION_SET_PROP*GRID_ARRANGE numberCol=9;"+ ";");
				this.status = CricketUtil.SUCCESSFUL;
				break;
		}
	}
	public void populateTargetImageAR(PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster ,Configuration config) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				//team name 
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team_Name " + match.getMatch().getInning().get(1).getBatting_team().getTeamName2()  +";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Base1 "  + base_path1 + 
						 match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
				//target
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TARGET " + "TARGET"  +";");
				
				//star base
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 "  + base_path1 + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 "  + base_path2 + 
						match.getMatch().getInning().get(1).getBatting_team().getTeamName4() + CricketUtil.PNG_EXTENSION + ";");
				//flag
//				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
//						+ "C:/Everest_VR_AR/Flags/"+ match.getMatch().getInning().get(1).getBatting_team().getTeamName4() +"/0000.png;");
				
				if(match.getMatch().getInning().get(1).getBatting_team().getTeamBadge().equalsIgnoreCase("WI")) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 1;");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselector 0;");
				}
				//playerimmage
				if(match.getMatch().getInning().get(1).getBattingTeamId() == match.getSetup().getHomeTeamId()) {
					for(Player py:match.getSetup().getHomeSquad()) {
						if(py.getCaptainWicketKeeper() !=null) {
							if(py.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) ||
									py.getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() +  
										"\\\\CENTER\\\\" + py.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
							}
						}
					}
				}else {
					for(Player py:match.getSetup().getAwaySquad()) {
						if(py.getCaptainWicketKeeper() !=null) {
							if(py.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) ||
									py.getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamName4() +  
										"\\\\CENTER\\\\" + py.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
							}
						}
					}
					
				}
				

//				print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET img_Player " + photo_path + 
//						+ CricketUtil.PNG_EXTENSION + ";");

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET NEED " + CricketFunctions.GetTargetData(match).getTargetRuns()  +";");
				
				
				//print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info LIVE FROM " + match.getSetup().getVenueName() + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				
				this.status = CricketUtil.SUCCESSFUL;
				break;
		}
	}
	
	public void populateEquationAR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				
				for(Inning inn : match.getMatch().getInning()) {
					if(is_this_updating == false) {
						switch (session_selected_broadcaster.toUpperCase()) {
						 
						case "EVEREST_AR_VR": case "BARODA_AR":
							
//							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 5;");
//							//target
//				//			print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header TARGET;");
//							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ScoreA " + 
//									CricketFunctions.GetTargetData(match).getTargetRuns() + ";");
//							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Overs " +  "OFF " +
//									Integer.valueOf(CricketFunctions.GetTargetData(match).getTargetOvers()) + " OVERS" +  ";");
//							
//							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RRHead " + "@" + CricketFunctions.generateRunRate(CricketFunctions.
//									GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) +";");
//							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RR " + "RUNS PER OVER" +";");
							
							//fro here
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Gfx_Selector 2;");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_mh1 + CricketUtil.PNG_EXTENSION + ";");
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_tg2+ match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
							//flags
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + 
									CricketUtil.PNG_EXTENSION + ";");
							
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "NEED" + ";");
						
						
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path + inn.getBatting_team().getTeamBadge().toUpperCase() + 
//									CricketUtil.PNG_EXTENSION + ";");
							break;
						case "MP_AR":
							
							//flags
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base2\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + 
								    "C:\\\\Everest_VR_2026\\\\Textures\\\\MPPL\\\\Base1\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");	
							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + 
								    "C:\\\\Everest_VR_2026\\\\Logos\\\\" + IndexController.cat + "\\\\" + 
								    inn.getBatting_team().getTeamBadge().toUpperCase() +
								    CricketUtil.PNG_EXTENSION + ";");

						break;	
						}	
					
					}
					
					switch (session_selected_broadcaster.toUpperCase()) {
					
					case "EVEREST_AR_VR": 
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_mh1+ CricketUtil.PNG_EXTENSION + ";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_mh2+ CricketUtil.PNG_EXTENSION + ";");
						
					break;
						
					case "BARODA_AR":
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path_bp1+ CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base2 " + base_path_bp2+ CricketUtil.PNG_EXTENSION + ";");
						

					break;
					}
					
					print_writer.println("LAYER3*EVEREST*TREEVIEW*Main$NEED*CONTAINER SET ACTIVE 0;");
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						
						
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFourHead " + "RUN" + 
						CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()  + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
						//six
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "BALL" + CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + Integer.valueOf(CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
						
						//header
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamA " + inn.getBatting_team().getTeamName1() +" NEED" +";");
						//flag
						
					//	print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Header " + inn.getBatting_team().getTeamName3() + " NEED" + ";");
						//fours
						
						//six
						
						//flags
						/*
						 * print_writer.
						 * println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " +
						 * "C:/Everest_VR_AR/Logos/" +
						 * inn.getBatting_team().getTeamBadge().toUpperCase() +
						 * CricketUtil.PNG_EXTENSION + ";");
						 */
						
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path1  +
//								 match.getMatch().getInning().get(1).getBatting_team().getTeamBadge()  + CricketUtil.PNG_EXTENSION + ";");
						
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + "C:/Everest_VR_AR/Logos/" + inn.getBatting_team().getTeamBadge().toUpperCase() + 
//								CricketUtil.PNG_EXTENSION + ";");
//						if(inn.getBatting_team().getTeamBadge().equalsIgnoreCase("WI")) {
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor 1;");
//						}else {
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Tflagselctor 0;");
//						}
						
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + logo_path  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
					    //base
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base1 " + base_path1  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						
						
						//data part
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeed " + "NEED" +";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFrom " + "OFF" +";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRRHead " + "REQUIRED RUN RATE" +";");
//						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRR " + "@ " + CricketFunctions.generateRunRate(CricketFunctions.
//								GetTargetData(match).getRemaningRuns(), 0, CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " RUNS PER OVER " +";");
						
//						if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
//							
//							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RR " + CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(match).getRemaningRuns(), 0, 
//									CricketFunctions.GetTargetData(match).getRemaningBall(), 2,match) + " RUNS PER OVERS" + ";");
//							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET RRHead " + "" + ";");	
//							
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ScoreA " +CricketFunctions.GetTargetData(match).getRemaningRuns()+ " RUN" + 
//									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()
//									 + ";");
////							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + 
////									CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
//						//	print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFrom " + "OFF" + ";");
////							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN"+ 
////									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
//							switch (match.getSetup().getMatchType()) {
//							case CricketUtil.ODI:
//								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
//							//		print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "OVERS" + ";");
//									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Overs " +  "OFF " + CricketFunctions.OverBalls(0, 
//											CricketFunctions.GetTargetData(match).getRemaningBall())+ " OVERS"  + ";");
//									
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(0, 
////											CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
//								}else {
//								//	print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "BALL" + 
//								//			CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
//									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Overs " +  "OFF " +
//											CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
//													CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()+ ";");
//									
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
////											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()  +";");
//								}
//								break;
//							default:
////								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "BALL" + 
////										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Overs "  + "OFF " + CricketFunctions.GetTargetData(match).getRemaningBall()+ " BALL" + 
//										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
//								
//								
////								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "OVERS" + ";");
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + 
////											CricketFunctions.OverBalls(0, 
////													CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
////									
//////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(0, 
//////											CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
////								}else {
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "BALL" + 
////											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + ";");
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
////									
//////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
//////											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()  +";");
////								}
//								
//								break;
//							}
//							
//						}else {
//							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET ScoreA " +CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN" + 
//									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
////							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFour " + 
////									CricketFunctions.GetTargetData(match).getRemaningRuns() + ";");
//							
////							print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN"+ 
////									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
//							switch (match.getSetup().getMatchType()) {
//							case CricketUtil.ODI:
//								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
//									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Overs " + "OFF " + " OVERS " +CricketFunctions.OverBalls(0, 
//											CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + CricketFunctions.OverBalls(0, 
////											CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
//									
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(0, 
////											CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
//								}else {
//									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Overs "  + "OFF"+ " BALL" + 
//											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()+ " (" + match.getSetup().getTargetType().toUpperCase() +
//											 CricketFunctions.GetTargetData(match).getRemaningBall() + ")" + ";");
//							//		print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
//									
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
////											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()+ " (" + match.getSetup().getTargetType().toUpperCase()+ ")"+";");
//										
//								}
//								break;
//							default:
//								print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Overs " + "OFF" + " BALL" + 
//										CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (" + match.getSetup().getTargetType().toUpperCase()+ ")"
//										+ CricketFunctions.GetTargetData(match).getRemaningBall()+ ";");
//							//	print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
//								
//								
////								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "OVERS" + ";");
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + CricketFunctions.OverBalls(0, 
////											CricketFunctions.GetTargetData(match).getRemaningBall()) + ";");
////									
//////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(0, 
//////											CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
////								}else {
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixHead " + "BALL" + 
////											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (" + match.getSetup().getTargetType().toUpperCase()+ ")" + ";");
////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSix " + CricketFunctions.GetTargetData(match).getRemaningBall() + ";");
////									
//////									print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
//////											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase() + " (" + match.getSetup().getTargetType().toUpperCase()+ ")"+";");
////								}
//								
//								break;
//							}
//						}
					}
				}
				
				
				
				//print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info LIVE FROM " + match.getSetup().getVenueName() + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	public void populateEquationVR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						System.out.println("inside method again");
						//flag
						print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeam " + "C:/Everest_VR_AR/Logos/" + inn.getBatting_team().getTeamBadge().toUpperCase() + 
								CricketUtil.PNG_EXTENSION + ";");
						
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag_Grp$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
//								+ "C:/Everest_VR_AR/Flags/"+ inn.getBatting_team().getTeamBadge() +"/0001.png;");
						//team name
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team_Name " + inn.getBatting_team().getTeamName1() + " NEED" + ";");
					    //base
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 " + base_path1  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 " + base_path2  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
							for(Player py:match.getSetup().getHomeSquad()) {
								if(py.getCaptainWicketKeeper() !=null) {
									if(py.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) ||
											py.getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() +  
												"\\\\CENTER\\\\" + py.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
									}
								}
							}
						}
						else {
						for(Player py:match.getSetup().getAwaySquad()) {
							if(py.getCaptainWicketKeeper() !=null) {
								if(py.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) ||
										py.getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() +  
											"\\\\CENTER\\\\" + py.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
								}
							}
						}
						
					}
						
						if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TARGET " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN"+ 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOff " + "OFF" +";");
							switch (match.getSetup().getMatchType()) {
							case CricketUtil.ODI:
								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.OverBalls(0, 
											CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()  +";");
								}
								break;
							default:
								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.OverBalls(0, 
											CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()  +";");
								}
								break;
							}
						}else {
							switch (match.getSetup().getMatchType()) {
							case CricketUtil.ODI:
								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.OverBalls(0, 
											CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()+ " (" + match.getSetup().getTargetType().toUpperCase()+ ")"+";");
								}
								break;
							default:
								if(CricketFunctions.GetTargetData(match).getRemaningBall() >= 100) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.OverBalls(0, 
											CricketFunctions.GetTargetData(match).getRemaningBall()) + " OVERS" + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
											CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()+ " (" + match.getSetup().getTargetType().toUpperCase()+ ")"+";");
								}
								
								break;
							}
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TARGET " + CricketFunctions.GetTargetData(match).getRemaningRuns() 
									+ " RUN"+ 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOff " + "OFF" +";");
							
								
						}
					}
				}
				
				//print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info LIVE FROM " + match.getSetup().getVenueName() + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				if(is_this_updating == false) {
					
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
	}
	
	public void populateEquationImageVR(boolean is_this_updating, PrintWriter print_writer,MatchAllData match,String session_selected_broadcaster) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "EVEREST_AR_VR": case "BARODA_AR": case "MP_AR":
				for(Inning inn : match.getMatch().getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						System.out.println("inside method again");
						//flag
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Team_Flag_Grp$Team_Flag*FUNCTION*IMAGESEQUENCE2 SET PATH "
								+ "C:/Everest_VR_AR/Flags/"+ inn.getBatting_team().getTeamBadge() +"/0001.png;");
						//team name
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Team_Name " + inn.getBatting_team().getTeamName1() + " NEED" + ";");
					    //base
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base1 " + base_path1  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
//						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET I_Home_base2 " + base_path2  + inn.getBatting_team().getTeamBadge() + CricketUtil.PNG_EXTENSION + ";");
						if(inn.getBattingTeamId() == match.getSetup().getHomeTeamId()) {
							for(Player py:match.getSetup().getHomeSquad()) {
								if(py.getCaptainWicketKeeper() !=null) {
									if(py.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) ||
											py.getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() +  
												"\\\\CENTER\\\\" + py.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
									}
								}
							}
						}
						else {
						for(Player py:match.getSetup().getAwaySquad()) {
							if(py.getCaptainWicketKeeper() !=null) {
								if(py.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN) ||
										py.getCaptainWicketKeeper().equalsIgnoreCase("captain_wicket_keeper")) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Player_Photo " + photo_path + match.getMatch().getInning().get(1).getBatting_team().getTeamBadge() +  
											"\\\\CENTER\\\\" + py.getPhoto() + CricketUtil.PNG_EXTENSION + ";");	
								}
							}
						}
						
					}
						
						if(match.getSetup().getTargetType() == null || match.getSetup().getTargetType().trim().isEmpty()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TARGET " + CricketFunctions.GetTargetData(match).getRemaningRuns() + " RUN"+ 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOff " + "OFF" +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()  +";");
						}else {
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET TARGET " + CricketFunctions.GetTargetData(match).getRemaningRuns() 
									+ " RUN"+ 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningRuns()).toUpperCase()+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOff " + "OFF" +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET BALLS " + CricketFunctions.GetTargetData(match).getRemaningBall() + " BALL" + 
									CricketFunctions.Plural(CricketFunctions.GetTargetData(match).getRemaningBall()).toUpperCase()+ " (" + match.getSetup().getTargetType().toUpperCase()+ ")"+";");
								
						}
					}
				}
				
				//print_writer.println("LAYER3*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET txt_Info LIVE FROM " + match.getSetup().getVenueName() + ";");
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*CONTAINER SET ACTIVE 0;");
				if(is_this_updating == false) {
					
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
		case "INFOBAR":
			//processAnimation(print_writer, "Out", "START", config.getBroadcaster(),1);
			break;
		
		case "SCORECARD": 
		//	processAnimation(print_writer, "BattingCardOut", "START", config.getBroadcaster(),2);
			TimeUnit.SECONDS.sleep(1);
			break;
		case "BOWLINGCARD":
		//(print_writer, "BowlingCardOut", "START", config.getBroadcaster(),2);
			TimeUnit.SECONDS.sleep(1);
			break;
		case "SUMARRY": case "PREVIOUS_SUMARRY":
		//	processAnimation(print_writer, "SummaryOut", "START", config.getBroadcaster(),2);
			TimeUnit.SECONDS.sleep(1);
			break;
		case "POINTSTABLE":
	//		processAnimation(print_writer, "PointsTableOut", "START", config.getBroadcaster(),2);
			TimeUnit.SECONDS.sleep(1);
			break;
		
		case "BUG": case "HOWOUT": case "BATSMANSTATS": case "BOWLERSTATS": case "BUG-DB": case "NAMESUPER": case "NAMESUPER-PLAYER": case "DOUBLETEAMS": 
		case "MATCHID": case "L3MATCHID": case "PLAYINGXI": case "TARGET": case "TEAMSUMMARY": case "EQUATION":case "PLAYERSUMMARY": case "L3PLAYERPROFILE": 
		case "FALLOFWICKET": case "SPLIT": case "COMPARISION": case "BUG-DISMISSAL": case "HOWOUT_WITHOUT_FIELDER": case "BATSMAN_STYLE": case "BUG-BOWLER": 
		case "MATCH_PROMO": case "TEAMS_LOGO": case "BOWLER_STYLE": case "TIEID-DOUBLE": case "GENERIC": case "MOSTRUNS": case "MOSTWICKETS": 
		case "MOSTFOURS": case "MOSTSIXES": case "HIGHESTSCORE": case "MANHATTAN": case "PARTNERSHIP": case "PROJECTED": case "FF_TARGET": case "THISOVER":
		case "L3HOWOUT": case "CURRENT_PARTNERSHIP": case "WORM": case "PLAYERPROFILE": case "MATCHSTATUS": case "HOWOUT_BOTH": case "BATSMANSTATS_BOTH":
		case "THIS_SESSION": case "SESSION": case "FF_EQUATION": case "BUG-TOSS": case "BOWLERDETAILS":
		//	processAnimation(print_writer, "Out", "START",String session_selected_broadcaster,1);
			TimeUnit.SECONDS.sleep(1);
			break;
			
		 case "LEADERBOARD":
		//	processAnimation(print_writer, "FF_Out", "START", session_selected_broadcaster,1);
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