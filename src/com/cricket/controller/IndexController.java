package com.cricket.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.cricket.archive.Archive;
import com.cricket.broadcaster.EVEREST_APL_T20;
import com.cricket.broadcaster.EVEREST_LEGENDS_90;
import com.cricket.broadcaster.KERALA_T20;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Configuration;
import com.cricket.model.DuckWorthLewis;
import com.cricket.model.EventFile;
import com.cricket.model.FieldersData;
import com.cricket.model.ForeignLanguageData;
import com.cricket.model.Ground;
import com.cricket.model.HeadToHead;
import com.cricket.model.Inning;
import com.cricket.model.Match;
import com.cricket.model.MatchAllData;
import com.cricket.model.MatchStats;
import com.cricket.model.MultiLanguageDatabase;
import com.cricket.model.Player;
import com.cricket.model.Setup;
import com.cricket.model.Statistics;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
//@SessionAttributes(value = {"session_configuration","session_Icc_big_screen", "session_icc_big_screen_doad_scoring","session_llc_big_screen","session_llc",
//	"session_llc_ar","session_kolkata","session_kerala","session_europe","session_selected_broadcaster","session_selected_scenes","expiryDate"})
@SessionAttributes(value = {"session_configuration","session_selected_broadcaster","session_selected_scenes","expiryDate"})
public class IndexController 
{
	@Autowired
	CricketService cricketService;
	
	public static MatchAllData session_match;
	public static MultiLanguageDatabase multiLanguage;
	
	public static Archive session_archive;
//	public static AE_Cricket third_party_session_match;
//	public static AE_Last_Ball third_party_last_ball_speed;
//	public static PLOTTER this_plotter;
//	public static Bukhatir this_bukhatir;
//	public static Arunachal this_arunachal;
//	public static THAILAND this_thailand;
//	public static ACC_NEPAL this_acc_nepal;
//	public static ICC_CWCU19 this_icc_cwc_u19;
//	public static GCPL this_gpcl;
//	public static APL this_apl;
//	public static PPL this_ppl;
//	public static PUNJAB_T20 this_punjab_t20;
//	public static MAHARAJA_T20 this_maharaja_t20;
//	public static FAIR_BREAK this_fairbreak;
//	public static RPL this_rpl;
//	public static RSWS this_rsws;
//	public static FAIR_BREAK_AR this_fairbreak_ar;
//	public static T20_MUMBAI_AR this_ar_t20Mumbai;
//	public static EVEREST_AR_VR this_Everest_AR_VR;
//	public static MPL this_mpl;
//	public static ASSAM this_assam;
//	public static ACC this_acc;
//	public static ICPL this_icpl;
//	public static LCT this_lct;
//	public static NEPAL_T20 this_nepal_t20;
//	public static USPL this_uspl;
//	public static EVEREST_SPL this_spl;
//	public static EVEREST_MPL_T20 everest_mpl_t20;
//	public static EVEREST_BENGAL_T20 bengal_t20;
//	public static ICPL_AR this_icpl_ar;
//	public static EVEREST_NEPAL_T20 everest_nepal_t20;
	public static EVEREST_LEGENDS_90 everest_legends_90;
	public static KERALA_T20 this_kerala_t20;
//	public static EVEREST_PUNJAB_T20 everest_punjab_t20;
	public static EVEREST_APL_T20 everest_apl_t20;
//	public static EVEREST_KCL everest_KCL;
//	public static EVEREST_PPL_T20 everest_ppl_t20;
//	public static EVEREST_KCL_T20 everest_kcl_t20;
//	public static ICC_BIGSCREEN_DOAD_VIZ_SCORING icc_bigscreen_viz_doad;
	public static String expiry_date = "2026-12-31";
	public static String session_selected_second_broadcaster;
	public static String current_date;
	public boolean show_speed = true,show_watermark = true;
	public boolean match_file_change = false;
	public static long time_elapsed = 0;
	public static long last_setup_time_stamp = 0;
	public static long last_match_time_stamp_third_Party = 0;
	public static long last_match_time_stamp = 0;
	public static long plotter_match_time_stamp1=0,plotter_match_time_stamp2=0, plotter_match_time_stamp3=0,plotter_match_time_stamp4=0,
			plotter_match_time_stamp=0,speed_match_time_stamp=0;
	public static String plotterData;
	public boolean Plotter_file_change = false;
	public boolean match_file_change_third_party=false;
	public static MatchStats matchstats ;
	File speedFile = new File("C:\\Sports\\Cricket\\Speed\\SPEED.txt");
	
	List<ForeignLanguageData> foreignLanguage = new ArrayList<ForeignLanguageData>();
	List<MatchAllData> cricket_matches = new ArrayList<MatchAllData>();
	List<Tournament> past_tournament_stats = new ArrayList<Tournament>();
	List<Statistics> session_statistics = new ArrayList<Statistics>();
	public static HeadToHead headToHead = new HeadToHead ();
	List<DuckWorthLewis> session_dls = new ArrayList<DuckWorthLewis>();
	FieldersData fielderFormation = new FieldersData();
	
	public static List<Team> session_team = new ArrayList<>();
	public static List<Ground> session_ground = new ArrayList<>();
	public static List<Player> session_players = new ArrayList<>();
	
	@RequestMapping(value = {"/help"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String HelpPage()  
	{
		return "help";
	}
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model, 
//		@ModelAttribute("session_llc_big_screen") LLC_BigScreen session_llc_big_screen, 
//		@ModelAttribute("session_Icc_big_screen") ICC_BIG_SCREEN session_Icc_big_screen, 
//		@ModelAttribute("session_icc_big_screen_doad_scoring") ICC_BIGSCREEN_DOAD_SCORING session_icc_big_screen_doad_scoring,
//		@ModelAttribute("session_llc") LLC session_llc,
//		@ModelAttribute("session_kolkata") KOLKATA_T20 session_kolkata,
//		@ModelAttribute("session_llc_ar") LLC_AR session_llc_ar,
//		@ModelAttribute("session_kerala") KERALA_T20 session_kerala,
//		@ModelAttribute("session_europe") EUROPE_LEAGUE session_europe,
		@ModelAttribute("session_selected_broadcaster") String session_selected_broadcaster, 
		@ModelAttribute("session_configuration") Configuration session_configuration, 
		@ModelAttribute("session_selected_scenes") List<Scene> session_selected_scenes,
		@ModelAttribute("expiryDate") String expiryDate) 
		throws JAXBException, IOException, ParseException, IllegalAccessException, InvocationTargetException, URISyntaxException, InterruptedException 
	{
		
		if(current_date == null || current_date.isEmpty()) {
			current_date = CricketFunctions.getOnlineCurrentDate();
		}
		
		model.addAttribute("session_viz_scenes", new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.SCENES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".via") && pathname.isFile();
		    }
		}));

		model.addAttribute("match_files", new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
//		        System.out.println("Files name : " + name);
		        return name.endsWith(".json") && pathname.isFile();
		    }
		}));

		model.addAttribute("configuration_files", new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".xml") && pathname.isFile();
		    }
		}));
		
//		File files[] = new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
//			@Override
//		    public boolean accept(File pathname) {
//		        String name = pathname.getName().toLowerCase();
//		        return name.endsWith(".json") && pathname.isFile();
//		    }
//		});
//
//		if(cricket_matches == null || cricket_matches.size()<=0) {
//			cricket_matches = CricketFunctions.getTournamentMatches(files, cricketService);
//		}
		
		if(session_statistics == null || session_statistics.size()<=0) {
			session_statistics = cricketService.getAllStats();
		}
		
		if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + CricketUtil.OUTPUT_XML).exists()) {
			session_configuration = (Configuration)JAXBContext.newInstance(Configuration.class).createUnmarshaller().unmarshal(
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + CricketUtil.OUTPUT_XML));
		} else {
			session_configuration = new Configuration();
			JAXBContext.newInstance(Configuration.class).createMarshaller().marshal(session_configuration, 
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + CricketUtil.OUTPUT_XML));
		}
		
		model.addAttribute("session_configuration",session_configuration);
//		model.addAttribute("session_llc_big_screen",session_llc_big_screen);
//		model.addAttribute("session_icc_big_screen_doad_scoring", session_icc_big_screen_doad_scoring);
//		model.addAttribute("session_Icc_big_screen",session_Icc_big_screen);
//		model.addAttribute("session_llc",session_llc);
//		model.addAttribute("session_kerala",session_kerala);
//		model.addAttribute("session_europe",session_europe);
//		model.addAttribute("session_llc_ar",session_llc_ar);
//		model.addAttribute("session_kolkata",session_kolkata);
		model.addAttribute("session_selected_scenes",session_selected_scenes);
		
		return "initialise";
	}

	@RequestMapping(value = {"/output"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String outputPage(ModelMap model,
			@ModelAttribute("session_configuration") Configuration session_configuration,
//			@ModelAttribute("session_llc_big_screen") LLC_BigScreen session_llc_big_screen, 
//			@ModelAttribute("session_Icc_big_screen") ICC_BIG_SCREEN session_Icc_big_screen, 
//			@ModelAttribute("session_icc_big_screen_doad_scoring") ICC_BIGSCREEN_DOAD_SCORING session_icc_big_screen_doad_scoring,
//			@ModelAttribute("session_llc") LLC session_llc,
//			@ModelAttribute("session_kerala") KERALA_T20 session_kerala,
//			@ModelAttribute("session_europe") EUROPE_LEAGUE session_europe,
//			@ModelAttribute("session_kolkata") KOLKATA_T20 session_kolkata,
//			@ModelAttribute("session_llc_ar") LLC_AR session_llc_ar, 
			@ModelAttribute("session_selected_scenes") List<Scene> session_selected_scenes,
			@ModelAttribute("session_selected_broadcaster") String session_selected_broadcaster,
			@ModelAttribute("expiryDate") String expiryDate,
			@RequestParam(value = "configuration_file_name", required = false, defaultValue = "") String configuration_file_name,
			@RequestParam(value = "select_broadcaster", required = false, defaultValue = "") String select_broadcaster,
			@RequestParam(value = "select_second_broadcaster", required = false, defaultValue = "") String select_second_broadcaster,
			@RequestParam(value = "which_layer", required = false, defaultValue = "") String which_layer,
			@RequestParam(value = "which_scene", required = false, defaultValue = "") String which_scene,
			@RequestParam(value = "select_cricket_matches", required = false, defaultValue = "") String selectedMatch,
			@RequestParam(value = "qtIPAddress", required = false, defaultValue = "") String qtIPAddress,
			@RequestParam(value = "qtPortNumber", required = false, defaultValue = "") int qtPortNumber,
			@RequestParam(value = "qtSceneName", required = false, defaultValue = "") String qtScene,
			@RequestParam(value = "qtLanguage", required = false, defaultValue = "") String qtLanguage,
			@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddress,
			@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") int vizPortNumber,
			@RequestParam(value = "vizSceneName", required = false, defaultValue = "") String vizScene,
			@RequestParam(value = "vizLanguage", required = false, defaultValue = "") String vizLanguage,
			@RequestParam(value = "vizSecondaryIPAddress", required = false, defaultValue = "") String vizSecondaryIPAddress,
			@RequestParam(value = "vizSecondaryPortNumber", required = false, defaultValue = "") int vizSecondaryPortNumber,
			@RequestParam(value = "vizSecondaryScene", required = false, defaultValue = "") String vizSecondaryScene,
			@RequestParam(value = "vizSecondaryLanguage", required = false, defaultValue = "") String vizSecondaryLanguage,
			@RequestParam(value = "vizTertiaryIPAddress", required = false, defaultValue = "") String vizTertiaryIPAddress,
			@RequestParam(value = "vizTertiaryPortNumber", required = false, defaultValue = "") int vizTertiaryPortNumber,
			@RequestParam(value = "vizTertiaryScene", required = false, defaultValue = "") String vizTertiaryScene,
			@RequestParam(value = "vizTertiaryLanguage", required = false, defaultValue = "") String vizTertiaryLanguage,
			@RequestParam(value = "Category", required = false, defaultValue = "") String Category) 
					throws UnknownHostException, IllegalAccessException, InvocationTargetException, ParseException, 
					IOException, InterruptedException, JAXBException, URISyntaxException
	{
		if(current_date == null || current_date.isEmpty()) {
			
			model.addAttribute("error_message","You must be connected to the internet online");
			return "error";
		
		} else if(new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date).before(new SimpleDateFormat("yyyy-MM-dd").parse(current_date))) {
			
			model.addAttribute("error_message","This software has expired");
			return "error";
			
		}else {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			LocalDate date1 = LocalDate.parse(current_date, dtf);
			LocalDate date2 = LocalDate.parse(expiry_date, dtf);
			
			long daysBetween = ChronoUnit.DAYS.between(date1, date2);
			
			expiryDate = String.valueOf(daysBetween);
			session_selected_broadcaster = select_broadcaster;
			session_selected_second_broadcaster = select_second_broadcaster;

//			speed_match_time_stamp = new File("C:\\Sports\\Cricket\\Speed\\SPEED.txt").lastModified();
//			plotter_match_time_stamp = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + "FielderFormation.json").lastModified();
//			plotter_match_time_stamp1 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + "FielderFormation_1.json").lastModified();
//			plotter_match_time_stamp2 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + "FielderFormation_2.json").lastModified();
//			plotter_match_time_stamp3 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + "FielderFormation_3.json").lastModified();
//			plotter_match_time_stamp4 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + "FielderFormation_4.json").lastModified();
//			
			last_match_time_stamp = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + selectedMatch).lastModified();
			last_setup_time_stamp = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + selectedMatch).lastModified();
			
			session_configuration = new Configuration(selectedMatch, select_broadcaster, select_second_broadcaster,
					vizIPAddress, vizPortNumber, vizLanguage, qtIPAddress, qtPortNumber, null, vizSecondaryIPAddress,
					vizSecondaryPortNumber, vizSecondaryLanguage, null, null,"",Category,"");
			
			session_configuration.setCategory(Category);
			JAXBContext.newInstance(Configuration.class).createMarshaller().marshal(session_configuration, 
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + configuration_file_name));
			
			session_selected_scenes.clear();
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case "PLOTTER":
				session_selected_scenes.add(new Scene("C:/Plotter/Scene/Plotter.sum","1")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				break;
			case "DOAD_AR":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Matt_Scenes/Boundaries.sum", "1")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				break;
			case "EUROPE_LEAGUE":
				session_selected_scenes.add(new Scene("C:/EuropeLeague/E_League_V2.sum", "1")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				break;	
			case "FAIR_BREAK_AR":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_AR/Scenes/Ident.sum", "1")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				break;
			case "T20_MUMBAI_AR":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_Barodaleague_2025/AR_Matt_Scene/MatchID_Animation_MT20.sum", "1")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				break;
			case "EVEREST_AR_VR":
				session_selected_scenes.add(new Scene("D:/Everest_VR_AR/Scenes/MATCH _ID.sum","1")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				break;
			case "ICPL_AR":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_NPL_2024/MattScenes/Boundaries.sum", "1")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				break;	
			case "BIG_SCREEN":
				session_selected_scenes.add(new Scene("D:\\DOAD_In_House_Everest\\Everest_Cricket\\Everest_LLC_Franchise_2023\\Big_Screen\\BG.sum", "3")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
				break;
			case "ICC_BIG_SCREEN":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ICC_Women_WorldCup/Scenes/CameraSet.sum","1")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ICC_Women_WorldCup/Scenes/BG.sum","4"));
				break;
			case "ICC_BIGSCREEN_DOAD_SCORING":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ICC_ChampionsTrophy/Scenes/CameraSet.sum","1")); // Front layer
				session_selected_scenes.add(new Scene("","2"));
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ICC_ChampionsTrophy/Scenes/BG.sum","4"));
				break;
			case "BUKHATIR":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/Scorebug_Test.sum", which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				break;
			case "ARUNACHAL":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_Cric2022/Scenes/Scorebug_Test.sum", which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				break;	
			case "THAILAND":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/Scorebug_Test.sum", which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				break;
			case "ACC_NEPAL":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_Cric2022/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				break;
			case "ASSAM":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;
			case "EVEREST_NEPAL_T20":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/Scorebug.sum"
						,which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;
			case "EVEREST_LEGENDS_90":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_Legends_90/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;
			case "SPL":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_Saurashtra_Primier_League/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;
			case "EVEREST_MPL_T20":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_MPL_2024/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;
			case "EVEREST_BENGAL_T20":
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;
			case "EVEREST_APL_T20":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2023/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;
			case "EVEREST_KCL":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_KCL/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;
			case "EVEREST_PPL_T20":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_PPL2023/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;	
			case "EVEREST_KCL_T20":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_KCL/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;	
			case "RSWS":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_RSWS_2023/Scenes RSWS/SB.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_RSWS_2023/Scenes RSWS/LTs.sum","MIDDLE_LAYER"));
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_RSWS_2023/Scenes RSWS/FF.sum","THIRD_LAYER"));
				break;
			case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
				session_selected_scenes.add(new Scene("/Default/gfx_BigScreen",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				break;
			case "GPCL": case "ACC": case "DOAD_VIZ":case "DOAD-VIZ-MULTI": case "NEPAL_T20": case "DOAD_LLC": case "ICPL": case "LCT": case "FAIR_BREAK":
			case "APL":	case "PUNJAB_T20": case "MAHARAJA_T20": case "RPL": case "USPL": case "ICC_CWCU19":case "MPL": case "KOLKATA_T20": case "PPL":
			case "KERALA_T20":	
				if(session_selected_broadcaster.equalsIgnoreCase("APL")) {
					session_selected_scenes.add(new Scene("/Default/APL/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("USPL")) {
					session_selected_scenes.add(new Scene("/Default/Scorebug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("/Default/FullFrames","BACK_LAYER")); // Back layer
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("MPL")) {
//					session_selected_scenes.add(new Scene("/Default/MPL/ScoreBug_MPL",which_layer)); // Front layer
//					session_selected_scenes.add(new Scene("/Default/FullFrames","BACK_LAYER")); // Back layer
//					session_selected_scenes.add(new Scene("/Default/LT_All","MIDDLE_LAYER"));
					session_selected_scenes.add(new Scene("/Default/MPL/ScoreBug_MPL",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","BACK_LAYER"));
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("ACC")) {
					session_selected_scenes.add(new Scene("/Default/ACC/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("MAHARAJA_T20")) {
					session_selected_scenes.add(new Scene("/Default/MaharajaT20_2024/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("ICPL")) {
					session_selected_scenes.add(new Scene("/Default/ICPL2023/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("LCT")) {
					session_selected_scenes.add(new Scene("/Default/LCT_2023/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("GPCL")) {
					session_selected_scenes.add(new Scene("/Default/GPCL/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("PUNJAB_T20")) {
					session_selected_scenes.add(new Scene("/Default/Punjab_Cup_2023/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","BACK_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("FAIR_BREAK")) {
					session_selected_scenes.add(new Scene("/Default/FairBreak/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("/Default/FairBreak/BatBallSummary","BACK_LAYER")); // Back layer
				}else if(session_selected_broadcaster.equalsIgnoreCase("DOAD-VIZ-MULTI")) {
					session_selected_scenes.add(new Scene("/Default/DOAD_In_House/ScoreBug",which_layer)); // Front layer
				}else if(session_selected_broadcaster.equalsIgnoreCase("NEPAL_T20")) {
					session_selected_scenes.add(new Scene("/Default/Nepal_T20/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("DOAD_LLC")) {
					session_selected_scenes.add(new Scene("/Default/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("/Default/FullFrames","BACK_LAYER")); // Back layer
					session_selected_scenes.add(new Scene("/Default/LT_All","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("KERALA_T20")) {
					session_selected_scenes.add(new Scene("/Default/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("/Default/FullFrames","BACK_LAYER")); // Back layer
					session_selected_scenes.add(new Scene("/Default/LT_All","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("KOLKATA_T20")) {
					session_selected_scenes.add(new Scene("/Default/Infobar",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("/Default/FullFrames","BACK_LAYER")); // Back layer
					session_selected_scenes.add(new Scene("/Default/LT_All","MIDDLE_LAYER"));
				}else if(session_selected_broadcaster.equalsIgnoreCase("RPL")) {
					session_selected_scenes.add(new Scene("/Default/ScoreBug_new",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("/Default/FullFrames","BACK_LAYER")); // Back layer
					session_selected_scenes.add(new Scene("/Default/LT_ALL","MIDDLE_LAYER")); // Back layer
				}else if(session_selected_broadcaster.equalsIgnoreCase("ICC_CWCU19")) {
					session_selected_scenes.add(new Scene("/Default/ScoreBug_new","FRONT_LAYER")); // Front layer
					session_selected_scenes.add(new Scene("/Default/FullFrames","BACK_LAYER")); // Back layer
					session_selected_scenes.add(new Scene("/Default/LT_ALL","MIDDLE_LAYER")); // Back layer
				}else if(session_selected_broadcaster.equalsIgnoreCase("PPL")) {
					session_selected_scenes.add(new Scene("/Default/PPL/ScoreBug",which_layer)); // Front layer
					session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				}
				
				switch (session_selected_broadcaster) {
				case "DOAD-VIZ-MULTI":
					multiLanguage = new MultiLanguageDatabase();
					multiLanguage.players = cricketService.getAllPlayer();
					multiLanguage.team = cricketService.getTeams();	
					multiLanguage.dictionary = cricketService.getDictionary();	
					multiLanguage.venue = cricketService.getVenues();
					break;
				}
				break;
			case "EVEREST_PUNJAB_T20":
				session_selected_scenes.add(new Scene("D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_PT20_2023/Scenes/Scorebug.sum",which_layer)); // Front layer
				session_selected_scenes.add(new Scene("","FRONT_LAYER"));
				break;	
			}
			
			if(!vizIPAddress.trim().isEmpty()) {
				switch (session_selected_broadcaster.toUpperCase()) {
				case "PLOTTER":
					//this_plotter = new PLOTTER();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "ICC_CWCU19":
//					this_icc_cwc_u19 = new ICC_CWCU19();
//					this_icc_cwc_u19.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(1), session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(1), session_selected_broadcaster);
					session_selected_scenes.get(2).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(1), session_selected_broadcaster);
					break;
				case "FAIR_BREAK_AR":
//					this_fairbreak_ar = new FAIR_BREAK_AR();
//					this_fairbreak_ar.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "T20_MUMBAI_AR":
//					this_ar_t20Mumbai = new T20_MUMBAI_AR();
//					this_ar_t20Mumbai.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_AR_VR":
//					this_Everest_AR_VR = new EVEREST_AR_VR();
//					this_Everest_AR_VR.infobar = new Infobar();
					System.out.println("COming inside controller");
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EUROPE_LEAGUE":
//					session_europe = new EUROPE_LEAGUE();
//					session_europe.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "DOAD_AR":
//					session_llc_ar = new LLC_AR();
//					session_llc_ar.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "ICPL_AR":
//					this_icpl_ar = new ICPL_AR();
//					this_icpl_ar.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;	
				case "BIG_SCREEN":
//					session_llc_big_screen = new LLC_BigScreen();
//					session_llc_big_screen.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "ICC_BIG_SCREEN":
//					session_Icc_big_screen = new ICC_BIG_SCREEN();
//					session_Icc_big_screen.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					//session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(2).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
 					break;
				case "ICC_BIGSCREEN_DOAD_SCORING":
//					session_icc_big_screen_doad_scoring = new ICC_BIGSCREEN_DOAD_SCORING();
//					session_icc_big_screen_doad_scoring.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					//session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(2).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
 					break;
				case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
//					icc_bigscreen_viz_doad = new ICC_BIGSCREEN_DOAD_VIZ_SCORING();
//					icc_bigscreen_viz_doad.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
//					icc_bigscreen_viz_doad.AnimateLogo(CricketFunctions.processPrintWriter(session_configuration), 1);
					break;
				case "BUKHATIR":
//					this_bukhatir = new Bukhatir();
//					this_bukhatir.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "ARUNACHAL":
//					this_arunachal = new Arunachal();
//					this_arunachal.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;	
				case "THAILAND":
//					this_thailand = new THAILAND();
//					this_thailand.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "RSWS":
//					this_rsws = new RSWS();
//					this_rsws.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(2).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					
//					this_rsws.infobar.setInfobar_loaded(true);
//					this_rsws.infobar.setLt_loaded(true);
//					this_rsws.infobar.setFf_loaded(true);
//					this_rsws.infobar.setFf2_loaded(false);
					break;	
				case "USPL":
//					this_uspl = new USPL();
//					this_uspl.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
//					this_uspl.infobar.setLt_loaded(false);
					break;

				case "ACC_NEPAL":
//					this_acc_nepal = new ACC_NEPAL();
//					this_acc_nepal.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "ASSAM":
//					this_assam = new ASSAM();
//					this_assam.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_NEPAL_T20":
//					everest_nepal_t20 = new EVEREST_NEPAL_T20();
//					everest_nepal_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_LEGENDS_90":
					everest_legends_90 = new EVEREST_LEGENDS_90();
					everest_legends_90.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "SPL":
//					this_spl = new EVEREST_SPL();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_MPL_T20":
//					everest_mpl_t20 = new EVEREST_MPL_T20();
//					everest_mpl_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_BENGAL_T20":
//					bengal_t20 = new EVEREST_BENGAL_T20();
//					bengal_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_PUNJAB_T20":
//					everest_punjab_t20 = new EVEREST_PUNJAB_T20();
//					everest_punjab_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_APL_T20":
					everest_apl_t20 = new EVEREST_APL_T20();
					everest_apl_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_KCL":
//					everest_KCL = new EVEREST_KCL();
//					everest_KCL.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_PPL_T20":
//					everest_ppl_t20 = new EVEREST_PPL_T20();
//					everest_ppl_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "EVEREST_KCL_T20":
//					everest_kcl_t20 = new EVEREST_KCL_T20();
//					everest_kcl_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "GPCL":
//					this_gpcl = new GCPL();
//					this_gpcl.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "APL":
//					this_apl = new APL();
//					this_apl.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "PPL":
//					this_ppl = new PPL();
//					this_ppl.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "PUNJAB_T20":
//					this_punjab_t20 = new PUNJAB_T20();
//					this_punjab_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;	
				case "FAIR_BREAK":
//					this_fairbreak = new FAIR_BREAK();
//					this_fairbreak.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "RPL":
//					this_rpl = new RPL();
//					this_rpl.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(2).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;	
				case "MAHARAJA_T20":
//					this_maharaja_t20 = new MAHARAJA_T20();
//					this_maharaja_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "ACC":
//					this_acc = new ACC();
//					this_acc.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "ICPL":
//					this_icpl = new ICPL();
//					this_icpl.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "LCT":
//					this_lct = new LCT();
//					this_lct.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;	
				case "NEPAL_T20":
//					this_nepal_t20 = new NEPAL_T20();
//					this_nepal_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "DOAD_LLC":
//					session_llc = new LLC();
//					session_llc.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(2).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;
				case "KERALA_T20":
//					session_kerala = new KERALA_T20();
//					session_kerala.infobar = new Infobar();
					
					this_kerala_t20 = new KERALA_T20();
					this_kerala_t20.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(2).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;	
				case "KOLKATA_T20":
//					session_kolkata = new KOLKATA_T20();
//					session_kolkata.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(2).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;	
				case "MPL":
//					this_mpl = new MPL();
//					this_mpl.infobar = new Infobar();
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					session_selected_scenes.get(2).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_broadcaster);
					break;	
				}
			}

			if(!qtIPAddress.trim().isEmpty()) {
				switch (select_second_broadcaster) {
				case "Quidich":
					session_selected_scenes.get(0).scene_load(CricketFunctions.processPrintWriter(session_configuration).get(0), select_second_broadcaster);
					break;
				}
			}
			model.addAttribute("manual_files", new File(CricketUtil.CRICKET_SERVER_DIRECTORY + "Manual/Data/").listFiles(new FileFilter() {
				@Override
			    public boolean accept(File pathname) {
			        String name = pathname.getName().toLowerCase();
			        return name.endsWith(".json") && pathname.isFile();
			    }
			}));
			
			JAXBContext.newInstance(Configuration.class).createMarshaller().marshal(session_configuration, 
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + configuration_file_name));
			
			session_match = new MatchAllData();
			if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + 
					selectedMatch).exists()) {
				session_match.setSetup(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + 
						selectedMatch), Setup.class));
				session_match.setMatch(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + 
						selectedMatch), Match.class));
			}
			if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + 
					selectedMatch).exists()) {
				session_match.setEventFile(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + 
						selectedMatch), EventFile.class));
			}
			session_match.getMatch().setMatchFileName(selectedMatch);
			
			session_team =  cricketService.getTeams();
			session_ground =  cricketService.getGrounds();
			session_players = cricketService.getAllPlayer();
			
			session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ, 
					CricketUtil.SETUP + "," + CricketUtil.MATCH + "," + CricketUtil.EVENT, session_match, session_configuration), 
					session_players, session_team, session_ground);			
			
			session_match.getSetup().setMatchFileTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
			
			if(new File(CricketUtil.CRICKET_DIRECTORY + "ParScores BB.html").exists()) {
				session_dls = CricketFunctions.populateDuckWorthLewis(session_match);
			}
			matchstats = CricketFunctions.getAllEvents(session_match ,session_selected_broadcaster, session_match.getEventFile().getEvents());

			headToHead = CricketFunctions.extractHeadToHead(session_match, cricketService);
			past_tournament_stats = CricketFunctions.extractTournamentData("PAST_MATCHES_DATA", false, headToHead.getH2hPlayer(), cricketService, session_match, null);
			
			switch(session_selected_broadcaster) {
				case "ICC_BIG_SCREEN":
//					if(third_party_session_match!=null &&third_party_session_match.getInning() != null) {
//						model.addAttribute("which_keypress", third_party_session_match.getInning().stream().filter(inn -> inn.getNumber() == third_party_session_match.getInning().size())
//								.findAny().orElse(null).getNumber());
//					} else {
//						model.addAttribute("which_keypress", "1");
//					}
//						if(third_party_session_match!=null &&third_party_session_match.getInning() != null) {
//							for(AE_Inning inn : third_party_session_match.getInning()) {
//								if(inn.getNumber() == third_party_session_match.getInning().size()) {
//									model.addAttribute("current_inning", inn.getNumber());
//									model.addAttribute("curr_team_total", inn.getShortName() + "-" + inn.getRuns() + "-" + inn.getNoOfWickets() 
//										+ " (" + inn.getOvers() + ")");
//								}
//						}
//					}
					break;
				default:
					if(session_match.getMatch().getInning() != null) {
						model.addAttribute("which_keypress", session_match.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning()
								.equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null).getInningNumber());
					} else {
						model.addAttribute("which_keypress", "1");
					}
					
					for(Inning inn : session_match.getMatch().getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							model.addAttribute("current_inning", inn.getInningNumber());
							model.addAttribute("curr_team_total", inn.getBatting_team().getTeamName3() + "-" + inn.getTotalRuns() + "-" + inn.getTotalWickets() 
								+ " (" + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ")");
							for(BattingCard bc : inn.getBattingCard()) {
								if(bc.getOnStrike() != null) {
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)){
										model.addAttribute("curr_player", bc.getPlayer().getTicker_name().toUpperCase() + "* " + bc.getRuns() + "(" + bc.getBalls() + ")" );
									}
									if(bc.getOnStrike().equalsIgnoreCase(CricketUtil.NO)) {
										model.addAttribute("curr_player2", bc.getPlayer().getTicker_name().toUpperCase() + " " + bc.getRuns() + "(" + bc.getBalls() + ")" );
									}
								}
							}
							if(inn.getBowlingCard() != null) {
								for(BowlingCard boc : inn.getBowlingCard()) {
									if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
										model.addAttribute("curr_bowler", boc.getPlayer().getTicker_name().toUpperCase() + ": " + boc.getWickets() + "-" + boc.getRuns() 
										+ "(" + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ")");
									}else if(boc.getStatus().equalsIgnoreCase(CricketUtil.LAST + CricketUtil.BOWLER)) {
										model.addAttribute("curr_bowler", boc.getPlayer().getTicker_name().toUpperCase() + ": " + boc.getWickets() + "-" + boc.getRuns() 
										+ "(" + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ")");
									}
								} 
							}
						}
					}
					break;
				}
			
			
			//CricketFunctions.getBatsmanRunsVsAllBowlers(1,cricketService.getAllPlayer(),session_match);
			//CricketFunctions.runSinceLastWicket(session_match);
			
			model.addAttribute("session_match", session_match);
			model.addAttribute("session_configuration", session_configuration);
			model.addAttribute("session_selected_broadcaster", session_selected_broadcaster);
			model.addAttribute("expiryDate", expiryDate);
			model.addAttribute("session_selected_second_broadcaster", session_selected_second_broadcaster);
//			model.addAttribute("session_llc_big_screen",session_llc_big_screen);
//			model.addAttribute("session_Icc_big_screen",session_Icc_big_screen);
//			model.addAttribute("session_icc_big_screen_doad_scoring", session_icc_big_screen_doad_scoring);
//			model.addAttribute("session_llc",session_llc);
//			model.addAttribute("session_kerala",session_kerala);
//			model.addAttribute("session_europe",session_europe);
//			model.addAttribute("session_kolkata",session_kolkata);
//			model.addAttribute("session_llc_ar",session_llc_ar);
			model.addAttribute("session_selected_scenes",session_selected_scenes);
			
			return "output";
		}
	}

	@RequestMapping(value = {"/processCricketProcedures.html"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processCricketProcedures(
//		@ModelAttribute("session_llc_big_screen") LLC_BigScreen session_llc_big_screen, 
//		@ModelAttribute("session_Icc_big_screen") ICC_BIG_SCREEN session_Icc_big_screen,
//		@ModelAttribute("session_icc_big_screen_doad_scoring") ICC_BIGSCREEN_DOAD_SCORING session_icc_big_screen_doad_scoring,
//		@ModelAttribute("session_llc") LLC session_llc,
//		@ModelAttribute("session_kerala") KERALA_T20 session_kerala,
//		@ModelAttribute("session_europe") EUROPE_LEAGUE session_europe,
//		@ModelAttribute("session_kolkata") KOLKATA_T20 session_kolkata,
//		@ModelAttribute("session_llc_ar") LLC_AR session_llc_ar, 
		@ModelAttribute("session_configuration") Configuration session_configuration,
		@ModelAttribute("session_selected_broadcaster") String session_selected_broadcaster,
		@ModelAttribute("session_selected_second_broadcaster") String session_selected_second_broadcaster,
		@ModelAttribute("session_selected_scenes") List<Scene> session_selected_scenes,
		@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
		@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess) 
					throws Exception 
	{
		
		switch (whatToProcess.toUpperCase()) {
		case "GET-CONFIG-DATA":

			session_configuration = (Configuration)JAXBContext.newInstance(Configuration.class).createUnmarshaller().unmarshal(
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + valueToProcess));
			
			return new ObjectMapper().writeValueAsString(session_configuration).toString();
			
		case "ANIMATE_IN_SPEED_SECOND_BROADCASTER":
			switch (session_configuration.getSecondaryBroadcaster()) {
			case "DOAD_LLC":
//				return (String) session_llc_big_screen.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(1), session_selected_scenes, valueToProcess, session_statistics);
			case "MPL":
//				return (String) everest_mpl_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(1), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
			case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
//				return (String) icc_bigscreen_viz_doad.ProcessGraphicOption(whatToProcess, session_match, cricketService, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics, past_tournament_stats, headToHead.getH2hPlayer(),session_configuration,session_dls);	
			}
			
			switch (session_configuration.getBroadcaster()) {
			case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
//				return (String) icc_bigscreen_viz_doad.ProcessGraphicOption(whatToProcess, session_match, cricketService, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics, past_tournament_stats, headToHead.getH2hPlayer(),session_configuration,session_dls);	
			}
			
		case "HEAD_TO_HEAD_FILE":
			CricketFunctions.exportMatchData(session_match);
			
			return new ObjectMapper().writeValueAsString(session_match).toString();
			
		case "RE_READ_DATA":
//			File files[] = new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
//				@Override
//			    public boolean accept(File pathname) {
//			        String name = pathname.getName().toLowerCase();
//			        return name.endsWith(".json") && pathname.isFile();
//			    }
//			});
//			
//			cricket_matches = CricketFunctions.getTournamentMatches(files, cricketService);
			headToHead = CricketFunctions.extractHeadToHead(session_match, cricketService);
			session_statistics = cricketService.getAllStats();
			past_tournament_stats = CricketFunctions.extractTournamentData("PAST_MATCHES_DATA", false, headToHead.getH2hPlayer(), 
					cricketService, session_match, null);
			matchstats = CricketFunctions.getAllEvents(session_match ,session_selected_broadcaster, session_match.getEventFile().getEvents());
			 
			if(new File(CricketUtil.CRICKET_DIRECTORY + "ParScores BB.html").exists()) {
				session_dls = CricketFunctions.populateDuckWorthLewis(session_match);
			}
			switch (session_selected_second_broadcaster) {
			case "DOAD_LLC":
//				return (String) session_llc.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics,plotterData, session_configuration, past_tournament_stats, headToHead.getH2hPlayer());
			case "KERALA_T20":
				return (String) this_kerala_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration), 
						session_selected_scenes, valueToProcess, session_statistics,plotterData, headToHead.getH2hPlayer(), session_configuration);	
			case "KOLKATA_T20":
//				return (String) session_kolkata.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics,plotterData, session_configuration);	
			case "Quidich":
//				return (String) session_llc.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics,plotterData, session_configuration, past_tournament_stats, headToHead.getH2hPlayer());
			}
			
			return new ObjectMapper().writeValueAsString(null).toString();
		case "TURN_ON_OR_OFF_SPEED":
			
			if(valueToProcess.equalsIgnoreCase("TRUE")) {
				show_speed = true;
			}else {
				show_speed = false;
			}
			return String.valueOf(show_speed);
			
		case "SHOW_SPEED":
			
			if(show_speed == true) {
				show_speed = false;
			}else {
				show_speed = true;
			}
			
			return String.valueOf(show_speed);
			
		case "TURN_ON_OR_OFF_WATERMARK":
			if(valueToProcess.equalsIgnoreCase("TRUE")) {
				CricketFunctions.processPrintWriter(session_configuration).get(0).println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Watermrk*ACTIVE SET 1" + "\0");
				show_watermark = true;
			}else {
				CricketFunctions.processPrintWriter(session_configuration).get(0).println("-1 RENDERER*FRONT_LAYER*TREE*$Main$Watermrk*ACTIVE SET 0" + "\0");
				show_watermark = false;
			}
			return String.valueOf(show_watermark);
			
		case "PITCH_MAP_GRAPHICS-OPTIONS":
			File folder = new File(CricketUtil.CRICKET_DIRECTORY + "PitchMap\\");
			File[] jsonFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

			List<String> fileName = new ArrayList<>();

			if (jsonFiles != null) {
			    for (File f : jsonFiles) {
			        fileName.add(f.getName());
			    }
			}
			return new ObjectMapper().writeValueAsString(fileName).toString();
			
		case "L3PLAYERPROFILEBAT-OPTIONS": case "STATS-OPTIONS": 
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllStats(cricketService)).toString();
		case "COMMENTATORS_GRAPHICS_OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getCommentator()).toString();
		case "FIXTURE_AND_RESULT-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getTeams()).toString();
		case "HOWOUT_GRAPHICS-OPTIONS": case "BATTER-ICC_GRAPHICS-OPTIONS": case "PLAYERPROFILE-ICC_GRAPHICS-OPTIONS": case "LINEUP-ICC_GRAPHICS-OPTIONS":
		case "PLAYERPROFILEBALL-ICC_GRAPHICS-OPTIONS": case "MILESTONE_GRAPHICS-OPTIONS": case "PLAYERFREETEXT_GRAPHICS-OPTIONS": case "LINEUPIMAGE-ICC_GRAPHICS-OPTIONS":
		case "PLAYERNAME-ICC_GRAPHICS-OPTIONS":	case "WAGON-ICC_GRAPHICS-OPTIONS": case "PLAYERVIDEO-ICC_GRAPHICS-OPTIONS": case "BATSMANSTATS-ICC_GRAPHICS-OPTIONS":
		case "PLAYERINTRO-ICC_GRAPHICS-OPTIONS": case "MULTI_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(session_match).toString();
		case "BOWLERSTATS-ICC_GRAPHICS-OPTIONS": case "TEAMNAME-ICC_GRAPHICS-OPTIONS":case "PLAYERH2H-ICC_GRAPHICS-OPTIONS": case "BOWLERFIG-ICC_GRAPHICS-OPTIONS": 
		case "LINEUPLONG-ICC_GRAPHICS-OPTIONS": case "LONGLINEUP-ICC_GRAPHICS-OPTIONS":	 case "IMG_FREETEXT2LINE_GRAPHIC-OPTIONS":
			switch (session_selected_broadcaster) {
			case "ICC_BIG_SCREEN":
//				return new ObjectMapper().writeValueAsString(third_party_session_match).toString();
			case "ICC_BIGSCREEN_DOAD_SCORING": case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
				return new ObjectMapper().writeValueAsString(session_match).toString();
			}
		case "SCOREBUG_CHANGEON_GRAPHICS-OPTIONS": case "IMAGEDROPDOWN-ICC_GRAPHICS-OPTIONS": case "FANTASYDROPDOWN-ICC_GRAPHICS-OPTIONS":
//			return (String) session_Icc_big_screen.ProcessGraphicOption(whatToProcess, session_match,cricketService, cricket_matches,third_party_session_match, 
//					CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,third_party_last_ball_speed,session_dls);
		case "FREETEXT_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(cricketService.getNameSupers()).toString();

		case "PERFORMANCE_BUG_DB_GRAPHICS-OPTIONS": case "HIGHEST_RUNS_OPTIONS": case "BEST_FIG_OPTIONS":case "LEADERBOARD_TEAM_GRAPHICS-OPTIONS": case "NAMESUPER_GRAPHICS-OPTIONS": 
		case "L3_MATCH-PROMO_GRAPHICS-OPTIONS": case "BUG_DB_GRAPHICS-OPTIONS": case "MOST_GRAPHICS-OPTIONS": case "MOST1_GRAPHICS-OPTIONS": case "MOST1_WICKETS_GRAPHICS-OPTIONS": 
		case "MOST_LEADERBOARD_GRAPHICS-OPTIONS": case "LEADERBOARD_GRAPHICS-OPTIONS": case "FF-LEADERBOARD-FANTASY-OPTIONS": case "WICKETS_GRAPHICS-OPTIONS": case "FOURS_GRAPHICS-OPTIONS": 
		case "SIXES_GRAPHICS-OPTIONS": case "HIGHEST_SCORE_GRAPHICS-OPTIONS": case "BEST_FIG_GRAPHICS-OPTIONS": case "SPLIT_GRAPHICS-OPTIONS": case "BUG_DB2_GRAPHICS-OPTIONS": 
		case "POPULATE-LASTX": case "HOWOUT_BOTH_GRAPHICS-OPTIONS": case "BATSMANSTATS_BOTH_GRAPHICS-OPTIONS": case "THIS_SESSION_GRAPHICS-OPTIONS": case "LT_POINTERS_GRAPHICS-OPTIONS": 
		case "FF_POINTERS_GRAPHICS-OPTIONS": case "POINTER_GRAPHICS-OPTIONS": case "MATCH_GRAPHICS-OPTIONS": case "TICKER_BOWLER_GRAPHICS-OPTIONS": case "L3PLAYERPROFILEBUKHATIR_GRAPHICS-OPTIONS":
		case "PLAYERPROFILEBUKHATIR_GRAPHICS-OPTIONS":	case "NAMESUPER_GRAPHICS_SINGLELINE-OPTIONS": case "POSITION_LANDMARK-OPTIONS": case "EXCEL_FF_GRAPHICS_OPTION": case "EXCEL_LT_GRAPHICS_OPTION": 
		case "EXCEL_FF_SUMMARY_GRAPHICS_OPTION": case "MOST_TOP5_TEAM_GRAPHICS-OPTIONS": case "EXCEL_FF_KEY_PLAYER_GRAPHICS_OPTION": case "HEIGHEST_INDIVIDUAL_SCORE_GRAPHICS-OPTIONS": 
		case "TEAM_WICKETS_GRAPHICS-OPTIONS": case "TEAM_LEADERBOARD_GRAPHICS-OPTIONS": case "TEAM_FOURS_GRAPHICS-OPTIONS": case "TEAM_SIXES_GRAPHICS-OPTIONS": case "BEST_FIGURES_GRAPHICS-OPTIONS":
			switch (session_selected_broadcaster.toUpperCase()) {
//			case "ICC_BIG_SCREEN":
//				return (String) session_Icc_big_screen.ProcessGraphicOption(whatToProcess, session_match,cricketService, cricket_matches,third_party_session_match, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,third_party_last_ball_speed,session_dls);
//			case "FAIR_BREAK_AR":
//				return (String) this_fairbreak_ar.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,session_configuration);
//			case "T20_MUMBAI_AR":
//				return (String) this_ar_t20Mumbai.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,session_configuration);
//			case "EVEREST_AR_VR":
//				return (String) this_Everest_AR_VR.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,session_configuration);
//			
//			case "DOAD_AR":
//				return (String) session_llc_ar.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics);
//			case "ICPL_AR":
//				return (String) this_icpl_ar.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics);	
//			case "DOAD_LLC":
//				return (String) session_llc.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,
//						CricketFunctions.processPrintWriter(session_configuration), session_selected_scenes, valueToProcess, session_statistics,plotterData, session_configuration, past_tournament_stats, headToHead.getH2hPlayer());
			case "KERALA_T20":
				return (String) this_kerala_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats,
						CricketFunctions.processPrintWriter(session_configuration), session_selected_scenes, valueToProcess, session_statistics,plotterData, headToHead.getH2hPlayer(), session_configuration);	
//			case "KOLKATA_T20":
//				return (String) session_kolkata.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration), session_selected_scenes, valueToProcess, session_statistics,plotterData, session_configuration);	
//			case "BUKHATIR":
//				return (String) this_bukhatir.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//			case "ARUNACHAL":
//				return (String) this_arunachal.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,past_tournament_stats,headToHead.getH2hPlayer(), session_configuration);
//			case "THAILAND":
//				return (String) this_thailand.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//			case "RSWS":
//				return (String) this_rsws.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, headToHead.getH2hPlayer(),session_statistics, session_configuration);	
//			case "USPL":
//				return (String) this_uspl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, session_configuration);	
//			case "ACC_NEPAL":
//				return (String) this_acc_nepal.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats,
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,headToHead.getH2hPlayer(), session_configuration);
//			case "ASSAM":
//				return (String) this_assam.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, session_configuration);	
//			case "EVEREST_NEPAL_T20":
//				return (String) everest_nepal_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics);
			case "EVEREST_LEGENDS_90":
				return (String) everest_legends_90.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//			case "SPL":
//				return (String) this_spl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//			case "EVEREST_MPL_T20":
//				return (String) everest_mpl_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//			
//			case "EVEREST_BENGAL_T20":
//				return (String) bengal_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//			case "EVEREST_PUNJAB_T20":
//				return (String) everest_punjab_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration);
			case "EVEREST_APL_T20":
				return (String) everest_apl_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
						session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//			case "EVEREST_KCL":
//				return (String) everest_KCL.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//			case "EVEREST_PPL_T20":
//				return (String) everest_ppl_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration);	
//			case "EVEREST_KCL_T20":
//				return (String) everest_kcl_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration);	
////			case "DOAD_EVEREST":
////				return (String) this_doad.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
////						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics);
//			case "GPCL":
//				return (String) this_gpcl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//			case "APL":
//				return (String) this_apl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer());
//			case "PPL":
//				return (String) this_ppl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),plotterData);
//			case "PUNJAB_T20":
//				return (String) this_punjab_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer());
//			case "FAIR_BREAK":
//				return (String) this_fairbreak.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//			case "MPL":
//				return (String) this_mpl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer());
//			case "RPL":
//				return (String) this_rpl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats,
//						CricketFunctions.processPrintWriter(session_configuration).get(0),session_selected_scenes, valueToProcess, session_statistics, session_configuration);	
////			case "DOAD-VIZ-MULTI":
////				return (String) this_multi.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration), 
////						session_selected_scenes, valueToProcess, session_statistics,session_configuration,multiLanguage,foreignLanguage);
//			case "MAHARAJA_T20":
//				return (String) this_maharaja_t20.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches,past_tournament_stats, 
//						session_selected_scenes, session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration, headToHead.getH2hPlayer(),plotterData);
//			case "ACC":
//				return (String) this_acc.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration);
//			case "ICPL":
//				return (String) this_icpl.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration);	
//			case "LCT":
//				return (String) this_lct.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration);		
//			case "NEPAL_T20":
//				return (String) this_nepal_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics,session_configuration);	
			}
		case "PROMPT_GRAPHICS-OPTIONS": case "TEAM_FIXTURES_GRAPHICS-OPTIONS": case "TEAM_SQUAD_GRAPHICS-OPTIONS":
			switch (session_selected_broadcaster) {
//			case "BUKHATIR":
//				return (String) this_bukhatir.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//			case "ARUNACHAL":
//				return (String) this_arunachal.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,past_tournament_stats, headToHead.getH2hPlayer(),session_configuration);
//			case "THAILAND":
//				return (String) this_thailand.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//			case "RSWS":
//				return (String) this_rsws.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess,headToHead.getH2hPlayer(), session_statistics, session_configuration);
//			case "USPL":
//				return (String) this_uspl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats,  
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//			case "ACC_NEPAL":
//				return (String) this_acc_nepal.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats,
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,headToHead.getH2hPlayer(), session_configuration);
//			case "ASSAM":
//				return (String) this_assam.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, session_configuration);	
//			case "MAHARAJA_T20":
//				return (String) this_maharaja_t20.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches,past_tournament_stats, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0),session_configuration, headToHead.getH2hPlayer(),plotterData);
//			case "ACC":
//				return (String) this_acc.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration);
//			case "ICPL":
//				return (String) this_icpl.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration);	
//			case "LCT":
//				return (String) this_lct.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration);		
//			case "GPCL":
//				return (String) this_gpcl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//			case "APL":
//				return (String) this_apl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer());
//			case "PPL":
//				return (String) this_ppl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),plotterData);
//			case "PUNJAB_T20":
//				return (String) this_punjab_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer());
//			case "FAIR_BREAK":
//				return (String) this_fairbreak.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//			case "MPL":
//				return (String) this_mpl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer());
//			case "RPL":
//				return (String) this_rpl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats,
//						CricketFunctions.processPrintWriter(session_configuration).get(0),session_selected_scenes, valueToProcess, session_statistics, session_configuration);	
//			case "NEPAL_T20":
//				return (String) this_nepal_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics,session_configuration);
//			case "DOAD_LLC":
//				return (String) session_llc.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,
//						CricketFunctions.processPrintWriter(session_configuration),session_selected_scenes, valueToProcess, session_statistics,plotterData, session_configuration, past_tournament_stats, headToHead.getH2hPlayer());
			case "KERALA_T20":
				return (String) this_kerala_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats,
						CricketFunctions.processPrintWriter(session_configuration),session_selected_scenes, valueToProcess, session_statistics,plotterData, headToHead.getH2hPlayer(), session_configuration);	
//			case "KOLKATA_T20":
//				return (String) session_kolkata.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,
//						CricketFunctions.processPrintWriter(session_configuration),session_selected_scenes, valueToProcess, session_statistics,plotterData, session_configuration);	
//			case "DOAD_AR":
//				return (String) session_llc_ar.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics);	
//			case "FAIR_BREAK_AR":
//				return (String) this_fairbreak_ar.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,session_configuration);	
//			case "T20_MUMBAI_AR":
//				return (String) this_ar_t20Mumbai.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,session_configuration);
//			case "EVEREST_AR_VR":
//				return (String) this_Everest_AR_VR.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,session_configuration);
//			
//			case "ICPL_AR":
//				return (String) this_icpl_ar.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics);		
			}
		case "MATCH-PROMO_GRAPHICS-OPTIONS": case "PREVIOUS_SUMMARY_GRAPHICS-OPTIONS": case "LT-TIEID-DOUBLE_GRAPHICS-OPTIONS": case "LTMATCH-PROMO_GRAPHICS-OPTIONS": 
		case "PLAYOFF_GRAPHICS-OPTIONS": case "MATCH-PROMO_ANIMATION_GRAPHICS-OPTIONS":
			return new ObjectMapper().writeValueAsString(CricketFunctions.processAllFixtures(cricketService)).toString();
		case "READ-MATCH-AND-POPULATE":
			if(show_speed == true ) {
				if (speedFile.exists()) {
					long currentTimestamp = speedFile.lastModified();
				    if (speed_match_time_stamp == 0) {
				        speed_match_time_stamp = currentTimestamp; // Set the initial value if uninitialized
				    }

				    // Use a tolerance for comparison
				    if (Math.abs(speed_match_time_stamp - currentTimestamp) > 100) {
//				        session_llc.speed(CricketFunctions.processPrintWriter(session_configuration).get(0), session_match);
				        speed_match_time_stamp = currentTimestamp; // Update to the new timestamp
				    }
				} else {
				    System.out.println("File does not exist.");
				}
			}else if(show_speed == false) {
				if (speedFile.exists()) {
					long currentTimestamp = speedFile.lastModified();
				    if (speed_match_time_stamp == 0) {
				        speed_match_time_stamp = currentTimestamp; // Set the initial value if uninitialized
				    }

				    if (Math.abs(speed_match_time_stamp - currentTimestamp) > 100) {
				        speed_match_time_stamp = currentTimestamp; // Update to the new timestamp
				    } else {
				        System.out.println("No modification detected.");
				    }
				}
			}
			
			
			if(new File("C:\\Sports\\Cricket\\Fielder\\Fielder_Text\\" + 
		            "FieldPlotter.txt").exists()) {
				
				String data = new String(Files.readAllBytes(Paths.get("C:\\Sports\\Cricket\\Fielder\\Fielder_Text\\" + 
			            "FieldPlotter.txt")));
		        // Split the content by lines and print each line separately
		        String[] lines = data.split("\n");
		        
		        plotterData = lines[0].trim();
		        
		        if(lines.length > 0) {
					if(lines[1].trim().equalsIgnoreCase("true")) {
						fielderFormation = CricketFunctions.getFielderFormation(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim());
						
						//System.out.println("BeforeCheckBox = " + fielderFormation.isCheckbox());
						if(fielderFormation.isCheckbox() == true) {
							if(lines[0].trim().equalsIgnoreCase("FielderFormation.JSON")) {
								if(plotter_match_time_stamp != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
									plotter_match_time_stamp = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
									Plotter_file_change = true;
								}
							}else if(lines[0].trim().equalsIgnoreCase("FielderFormation_1.JSON")) {
								if(plotter_match_time_stamp1 != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
									plotter_match_time_stamp1 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
									Plotter_file_change = true;
								}
							}else if(lines[0].trim().equalsIgnoreCase("FielderFormation_2.JSON")) {
								if(plotter_match_time_stamp2 != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
									plotter_match_time_stamp2 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
									Plotter_file_change = true;
								}
							}else if(lines[0].trim().equalsIgnoreCase("FielderFormation_3.JSON")) {
								if(plotter_match_time_stamp3 != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
									plotter_match_time_stamp3 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
									Plotter_file_change = true;
								}
							}else if(lines[0].trim().equalsIgnoreCase("FielderFormation_4.JSON")) {
								if(plotter_match_time_stamp4 != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
									plotter_match_time_stamp4 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
									Plotter_file_change = true;
								}
							}
						}
					}else if(lines[1].trim().equalsIgnoreCase("false")) {
						
					}
				}
			}
			
			match_file_change = false;
			switch(session_selected_broadcaster) {
//			case "ICC_BIG_SCREEN":
//				if(last_match_time_stamp_third_Party != new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.Cricket_THIRDPARTY).lastModified()) {					
//					third_party_session_match = CricketFunctions.getDataFromThirdParty(CricketUtil.CRICKET_DIRECTORY + 
//							CricketUtil.Cricket_THIRDPARTY);
//					match_file_change_third_party = true;
//				}
//				
//				if(match_file_change_third_party == true) {
//					if(CricketFunctions.processPrintWriter(session_configuration) != null &&
//							CricketFunctions.processPrintWriter(session_configuration).size() > 0) {	
//						session_Icc_big_screen.updateInfobar(session_selected_scenes.get(0), third_party_session_match,session_match,show_speed,cricketService, 
//								CricketFunctions.processPrintWriter(session_configuration).get(0));
//					}
//					last_match_time_stamp_third_Party = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.Cricket_THIRDPARTY).lastModified();
//					match_file_change_third_party = false;
//				}
//				return new ObjectMapper().writeValueAsString(third_party_session_match).toString();
				
			default:
				if(last_match_time_stamp != new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY 
						+ session_match.getMatch().getMatchFileName()).lastModified()) {
					session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ, CricketUtil.SETUP + "," 
							+ CricketUtil.MATCH + "," + CricketUtil.EVENT, session_match, session_configuration), session_players, session_team, session_ground);
					last_match_time_stamp = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY 
							+ session_match.getMatch().getMatchFileName()).lastModified();
					matchstats = CricketFunctions.getAllEvents(session_match ,session_selected_broadcaster, session_match.getEventFile().getEvents());

					match_file_change = true;
				}
				
				switch (session_selected_broadcaster) {
				case "EUROPE_LEAGUE":
					if(last_match_time_stamp == new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY 
							+ session_match.getMatch().getMatchFileName()).lastModified()) {
//						LocalTime time = LocalTime.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");

//				        String currentTime = time.format(formatter);
				        
//				        CricketFunctions.processPrintWriter(session_configuration).get(0).println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Clock " + currentTime + ";");
					}
					break;
				}
			}
			//-----------------------------------------------

			if(Plotter_file_change == true) {
				switch (session_selected_broadcaster) {
				case "DOAD_LLC":
//					session_llc.updateFieldPlotter(session_selected_scenes, session_match,cricket_matches,show_speed, 
//							CricketFunctions.processPrintWriter(session_configuration),plotterData);
					Plotter_file_change = false;
					break;
				case "KERALA_T20":
					this_kerala_t20.updateFieldPlotter(session_selected_scenes, session_match,cricket_matches,show_speed, 
							CricketFunctions.processPrintWriter(session_configuration),plotterData);
					Plotter_file_change = false;
					break;	
				case "MAHARAJA_T20":
//					this_maharaja_t20.updateFieldPlotter(session_selected_scenes, session_match,cricket_matches,show_speed, 
//							CricketFunctions.processPrintWriter(session_configuration),plotterData);
					Plotter_file_change = false;
					break;	
				}
			}
			//-----------------------------------------------
			
			session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ, CricketUtil.SETUP + "," 
					+ CricketUtil.MATCH + "," + CricketUtil.EVENT, session_match, session_configuration), session_players, session_team, session_ground);
			match_file_change = true;
			if(match_file_change == true) {
				session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ, CricketUtil.SETUP + "," 
						+ CricketUtil.MATCH + "," + CricketUtil.EVENT, session_match, session_configuration), session_players, session_team, session_ground);
				matchstats = CricketFunctions.getAllEvents(session_match ,session_selected_broadcaster, session_match.getEventFile().getEvents());
				 
				switch (session_selected_broadcaster) {
				case "EVEREST_KCL":
//					everest_KCL.updateInfobar(session_selected_scenes, session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "BIG_SCREEN":
//					session_llc_big_screen.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "ICC_BIGSCREEN_DOAD_SCORING":
//					session_icc_big_screen_doad_scoring.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, 
//							CricketFunctions.processPrintWriter(session_configuration).get(0),cricketService,session_configuration);
					break;
				case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
//					icc_bigscreen_viz_doad.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, 
//							CricketFunctions.processPrintWriter(session_configuration),cricketService,session_configuration,session_dls);
					break;
//				case "ICC_BIG_SCREEN":
//					session_Icc_big_screen.updateInfobar(session_selected_scenes.get(0), third_party_session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
//					break;	
				case "FAIR_BREAK_AR":
//					this_fairbreak_ar.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "T20_MUMBAI_AR":
//					this_ar_t20Mumbai.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "EVEREST_AR_VR":
//					this_Everest_AR_VR.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0),session_configuration,cricketService);
					break;
				
				case "DOAD_AR":
//					session_llc_ar.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "ICPL_AR":
//					this_icpl_ar.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;	
				case "BUKHATIR":
//					this_bukhatir.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "ARUNACHAL":
//					this_arunachal.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;	
				case "THAILAND":
//					this_thailand.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "RSWS":
//					this_rsws.updateInfobar(session_selected_scenes, session_match,cricket_matches,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;	
				case "USPL":
//					this_uspl.updateInfobar(session_selected_scenes, session_match,cricket_matches,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "ACC_NEPAL":
//					this_acc_nepal.updateInfobar(session_selected_scenes.get(0), session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "ASSAM":
//					this_assam.updateInfobar(session_selected_scenes.get(0), session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;	
//				case "DOAD_EVEREST":
//					this_doad.updateInfobar(session_selected_scenes.get(0), session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
//					break;
				case "GPCL":
//					this_gpcl.updateInfobar(session_selected_scenes, session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "APL":
//					this_apl.updateInfobar(session_selected_scenes,cricket_matches,session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "PPL":
//					this_ppl.updateInfobar(session_selected_scenes,cricket_matches,session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "PUNJAB_T20":
//					this_punjab_t20.updateInfobar(session_selected_scenes,cricket_matches, session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "FAIR_BREAK":
//					this_fairbreak.updateInfobar(session_selected_scenes, session_match,cricket_matches,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "MPL":
//					this_mpl.updateInfobar(session_selected_scenes, session_match,cricket_matches,show_speed, CricketFunctions.processPrintWriter(session_configuration));
					break;
				case "RPL":
//					this_rpl.updateInfobar(session_selected_scenes, session_match,cricket_matches,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;	
//				case "DOAD-VIZ-MULTI":
//					this_multi.updateInfobar(session_selected_scenes, session_match, CricketFunctions.processPrintWriter(session_configuration),session_configuration,multiLanguage,foreignLanguage);
//					break;
				case "MAHARAJA_T20":
//					this_maharaja_t20.updateInfobar(session_selected_scenes, session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "ACC":
//					this_acc.updateInfobar(session_selected_scenes, session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "ICPL":
//					this_icpl.updateInfobar(session_selected_scenes, session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "LCT":
//					this_lct.updateInfobar(session_selected_scenes, session_match, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;	
				case "NEPAL_T20":
//					this_nepal_t20.updateInfobar(session_selected_scenes, session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration).get(0));
					break;
				case "DOAD_LLC":
//					session_llc.updateInfobar(session_selected_scenes, session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration));
					break;
				case "EUROPE_LEAGUE":
//					session_europe.updateInfobar(session_selected_scenes, session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration));
					break;
				case "KERALA_T20":
					this_kerala_t20.updateInfobar(session_selected_scenes, session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration));
					break;	
				case "KOLKATA_T20":
//					session_kolkata.updateInfobar(session_selected_scenes, session_match,show_speed, CricketFunctions.processPrintWriter(session_configuration),1);
					break;	
				}
				last_match_time_stamp = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY 
						+ session_match.getMatch().getMatchFileName()).lastModified();
				last_setup_time_stamp = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY 
						+ session_match.getMatch().getMatchFileName()).lastModified();
				match_file_change = false;
				return new ObjectMapper().writeValueAsString(session_match).toString();
				
			} else {
				
				return new ObjectMapper().writeValueAsString(session_match).toString();
			
			}
			
		default:
			switch (session_selected_broadcaster) {
//			case "PLOTTER":
//				this_plotter.ProcessGraphicOption(whatToProcess, session_match,CricketFunctions.processPrintWriter(session_configuration).get(0), valueToProcess, 
//						session_configuration, session_selected_scenes);
//				break;
//			case "FAIR_BREAK_AR":
//				this_fairbreak_ar.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,session_configuration);
//				break;
//			case "T20_MUMBAI_AR":
//				this_ar_t20Mumbai.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,session_configuration);
//				break;
//			case "EVEREST_AR_VR":
//				this_Everest_AR_VR.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,session_configuration);
//				break;
//			
//			case "DOAD_AR":
//				session_llc_ar.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics);
//				break;
//			case "ICPL_AR":
//				this_icpl_ar.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics);
//				break;	
//			case "BIG_SCREEN":
//				session_llc_big_screen.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics);
//				break;
//			case "ICC_BIGSCREEN_DOAD_SCORING":
//				session_icc_big_screen_doad_scoring.ProcessGraphicOption(whatToProcess, session_match, cricketService, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, 
//						past_tournament_stats, headToHead.getH2hPlayer(),session_configuration,session_dls);
//				return new ObjectMapper().writeValueAsString(session_icc_big_screen_doad_scoring).toString();
//			case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
//				icc_bigscreen_viz_doad.ProcessGraphicOption(whatToProcess, session_match, cricketService, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics, past_tournament_stats, headToHead.getH2hPlayer(),session_configuration,session_dls);
//				return new ObjectMapper().writeValueAsString(icc_bigscreen_viz_doad).toString();
//			
//			case "ICC_BIG_SCREEN":
//				session_Icc_big_screen.ProcessGraphicOption(whatToProcess, session_match,cricketService, cricket_matches,third_party_session_match, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,
//						third_party_last_ball_speed,session_dls);
//				return new ObjectMapper().writeValueAsString(session_Icc_big_screen).toString();
//			case "BUKHATIR":
//				this_bukhatir.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//				return new ObjectMapper().writeValueAsString(this_bukhatir).toString();
//			case "ARUNACHAL":
//				this_arunachal.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics,past_tournament_stats, headToHead.getH2hPlayer(),session_configuration);
//				return new ObjectMapper().writeValueAsString(this_arunachal).toString();	
//			case "THAILAND":
//				this_thailand.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//				return new ObjectMapper().writeValueAsString(this_thailand).toString();
//			case "RSWS":
//				this_rsws.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess,headToHead.getH2hPlayer(), session_statistics, session_configuration);
////				break;
//				return new ObjectMapper().writeValueAsString(this_rsws).toString();
//			case "USPL":
//				this_uspl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//				
//				return new ObjectMapper().writeValueAsString(this_uspl).toString();
//			case "ACC_NEPAL":
//				this_acc_nepal.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats,
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics,headToHead.getH2hPlayer(), session_configuration);
//				return new ObjectMapper().writeValueAsString(this_acc_nepal).toString();
//			case "ICC_CWCU19":
//				this_icc_cwc_u19.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration), session_selected_scenes, valueToProcess, session_statistics);
//				return new ObjectMapper().writeValueAsString(this_icc_cwc_u19).toString();
//			case "ASSAM":
//				this_assam.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, valueToProcess, session_statistics, session_configuration);
//				return new ObjectMapper().writeValueAsString(this_assam).toString();
//			case "EVEREST_NEPAL_T20":
//				everest_nepal_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics);
//				return new ObjectMapper().writeValueAsString(everest_nepal_t20).toString();
			case "EVEREST_LEGENDS_90":
				everest_legends_90.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
				return new ObjectMapper().writeValueAsString(everest_legends_90).toString();
//			case "SPL":
//				this_spl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//				return new ObjectMapper().writeValueAsString(this_spl).toString();
//			case "EVEREST_MPL_T20":
//				everest_mpl_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//				return new ObjectMapper().writeValueAsString(everest_mpl_t20).toString();
//			case "EVEREST_BENGAL_T20":
//				bengal_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),past_tournament_stats);
//				return new ObjectMapper().writeValueAsString(bengal_t20).toString();
//				
//			case "EVEREST_PUNJAB_T20":
//				everest_punjab_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration);
//				return new ObjectMapper().writeValueAsString(everest_punjab_t20).toString();
			case "EVEREST_APL_T20":
				everest_apl_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
						valueToProcess, session_statistics, session_configuration);
				break;
//			case "EVEREST_KCL":
//				everest_KCL.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration);
//				break;
//			case "EVEREST_PPL_T20":
//				everest_ppl_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration);
//				break;
//			case "EVEREST_KCL_T20":
//				everest_kcl_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration);
//				break;
//			case "GPCL":
//				this_gpcl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration);
//				return new ObjectMapper().writeValueAsString(this_gpcl).toString();
//			case "APL":
//				this_apl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer());
//				return new ObjectMapper().writeValueAsString(this_apl).toString();
//			case "PPL":
//				this_ppl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer(),plotterData);
//				return new ObjectMapper().writeValueAsString(this_ppl).toString();
//			case "PUNJAB_T20":
//				this_punjab_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), 
//						session_selected_scenes, valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer());
//				return new ObjectMapper().writeValueAsString(this_punjab_t20).toString();
//			case "MAHARAJA_T20":
//				this_maharaja_t20.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches,past_tournament_stats, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0),session_configuration, headToHead.getH2hPlayer(),plotterData);
//				return new ObjectMapper().writeValueAsString(this_maharaja_t20).toString();	
//			case "FAIR_BREAK":
//				this_fairbreak.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration);
//				return new ObjectMapper().writeValueAsString(this_fairbreak).toString();
//			case "MPL":
//				this_mpl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration), session_selected_scenes, 
//						valueToProcess, session_statistics, session_configuration,headToHead.getH2hPlayer());
//				return new ObjectMapper().writeValueAsString(this_mpl).toString();
//			case "RPL":
//				this_rpl.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats,
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes,valueToProcess, session_statistics, session_configuration);
//				break;	
//			case "ACC":
//				this_acc.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration);
//				return new ObjectMapper().writeValueAsString(this_acc).toString();
//			case "ICPL":
//				this_icpl.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration);
//				return new ObjectMapper().writeValueAsString(this_icpl).toString();
//			case "LCT":
//				this_lct.processGraphics(whatToProcess, valueToProcess, session_match, cricket_matches, session_selected_scenes, 
//						session_statistics, cricketService, CricketFunctions.processPrintWriter(session_configuration).get(0), session_configuration);
//				return new ObjectMapper().writeValueAsString(this_lct).toString();	
//			case "NEPAL_T20":
//				this_nepal_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, 
//						CricketFunctions.processPrintWriter(session_configuration).get(0), session_selected_scenes, 
//						valueToProcess, session_statistics,session_configuration);
//				break;
//			case "DOAD_LLC":
//				session_llc.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration),
//						session_selected_scenes, valueToProcess, session_statistics,plotterData, session_configuration, past_tournament_stats, headToHead.getH2hPlayer());
//				return new ObjectMapper().writeValueAsString(session_llc).toString();
			case "KERALA_T20":
				this_kerala_t20.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration), 
						session_selected_scenes, valueToProcess, session_statistics,plotterData, headToHead.getH2hPlayer(), session_configuration);
				return new ObjectMapper().writeValueAsString(this_kerala_t20).toString();
//			case "EUROPE_LEAGUE":
//				session_europe.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches,past_tournament_stats, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics,plotterData, headToHead.getH2hPlayer(), session_configuration);
//				return new ObjectMapper().writeValueAsString(session_europe).toString();	
//			case "KOLKATA_T20":
//				session_kolkata.ProcessGraphicOption(whatToProcess, session_match, cricketService, cricket_matches, CricketFunctions.processPrintWriter(session_configuration), 
//						session_selected_scenes, valueToProcess, session_statistics,plotterData, session_configuration);
//				return new ObjectMapper().writeValueAsString(session_kolkata).toString();	
			}
			return new ObjectMapper().writeValueAsString(session_match).toString();
		}
	}
//	@ModelAttribute("session_llc_big_screen")
//	public LLC_BigScreen session_llc_big_screen(){
//		return new LLC_BigScreen();
//	}
//	@ModelAttribute("session_Icc_big_screen")
//	public ICC_BIG_SCREEN session_Icc_big_screen(){
//		return new ICC_BIG_SCREEN();
//	}
//	@ModelAttribute("session_icc_big_screen_doad_scoring")
//	public ICC_BIGSCREEN_DOAD_SCORING session_icc_big_screen_doad_scoring(){
//		return new ICC_BIGSCREEN_DOAD_SCORING();
//	}
//	@ModelAttribute("session_llc")
//	public LLC session_llc(){
//		return new LLC();
//	}
//	@ModelAttribute("session_kerala")
//	public KERALA_T20 session_kerala(){
//		return new KERALA_T20();
//	}
//	@ModelAttribute("session_europe")
//	public EUROPE_LEAGUE session_europe(){
//		return new EUROPE_LEAGUE();
//	}
//	@ModelAttribute("session_kolkata")
//	public KOLKATA_T20 session_kolkata(){
//		return new KOLKATA_T20();
//	}
//	@ModelAttribute("session_llc_ar")
//	public LLC_AR session_llc_ar(){
//		return new LLC_AR();
//	}
	@ModelAttribute("session_configuration")
	public Configuration session_configuration(){
		return new Configuration();
	} 
	@ModelAttribute("session_selected_scenes")
	public List<Scene> session_selected_scenes(){
		return new ArrayList<Scene>();
	}
	@ModelAttribute("session_selected_broadcaster")
	public String session_selected_broadcaster(){
		return new String();
	}
	@ModelAttribute("expiryDate")
	public String expiryDate(){
		return new String();
	}
}