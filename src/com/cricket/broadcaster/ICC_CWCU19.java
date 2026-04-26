package com.cricket.broadcaster;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBException;
import com.cricket.containers.Infobar;
import com.cricket.containers.Scene;
import com.cricket.model.MatchAllData;
import com.cricket.model.Statistics;
import com.cricket.service.CricketService;

public class ICC_CWCU19 extends Scene{
	
	public String broadcaster = "ICC_CWCU19";
	
	private String status;
	public Infobar infobar = new Infobar();
	
	public String which_graphic_on_screen = "";
	
	public ICC_CWCU19() {
		super();
	}
	
	public ICC_CWCU19(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, CricketService cricketService, List<MatchAllData> tournament_matches,
			List<PrintWriter> print_writer, List<Scene> scenes, String valueToProcess, List<Statistics> statistics) throws InterruptedException, ParseException, JAXBException, NumberFormatException, IOException, IllegalAccessException, InvocationTargetException{
		
		switch(whatToProcess.toUpperCase()) {
		case "POPULATE-F4_BTN": case "POPULATE-F6_BTN": case "POPULATE-F7_BTN": case "POPULATE-F8_BTN": case "POPULATE-F9_BTN": 
		case "POPULATE-F_BTN":	case "POPULATE-S_BTN":	case "POPULATE-W_BTN":	case "POPULATE-Z_BTN":
			
		//BUGS
		case "POPULATE-L3-BUG-TOSS":
			
			switch(whatToProcess.toUpperCase()) {
			case "POPULATE-F4_BTN": case "POPULATE-F6_BTN": case "POPULATE-F7_BTN": case "POPULATE-F8_BTN": case "POPULATE-F9_BTN": 
			case "POPULATE-F_BTN":	case "POPULATE-S_BTN":	case "POPULATE-W_BTN":	case "POPULATE-Z_BTN":	
				break;
			default:
				scenes.get(2).setScene_path(valueToProcess.split(",")[0]);
				scenes.get(2).scene_load(print_writer.get(1),broadcaster);
				print_writer.get(1).println("-1 RENDERER*STAGE SHOW 0.0 \0");
				break;
			}
			
			switch(whatToProcess.toUpperCase()) {
			case "POPULATE-F4_BTN":
				String input = "F4";
//		        byte[] bytes = input.getBytes(StandardCharsets.US_ASCII);
				print_writer.get(0).printf("%s",input);
				break;
			case "POPULATE-F6_BTN":
				String comm2 = "F6";
				print_writer.get(0).printf("%s",valueToProcess);
				break;
			case "POPULATE-F7_BTN":
				String comm1 = "F7";
				print_writer.get(0).printf("%s",comm1);
				break;
			case "POPULATE-F8_BTN":
				print_writer.get(0).print(valueToProcess);
				break;	
			case "POPULATE-F9_BTN":
				print_writer.get(0).printf("%s","F9");
				break;
			case "POPULATE-F_BTN":
				print_writer.get(0).printf("%s","F");
				break;
			case "POPULATE-S_BTN":
				print_writer.get(0).printf("%s","S");
				break;
			case "POPULATE-W_BTN":
				print_writer.get(0).printf("%s","W");
				break;
			case "POPULATE-Z_BTN":
				print_writer.get(0).printf("%s","Z");
				break;
				
			case "POPULATE-L3-BUG-TOSS":
				populateBugToss(print_writer.get(1),valueToProcess.split(",")[0],match,broadcaster);
				break;
			}
			
			break;

		
		case "ANIMATE-OUT":
		case "ANIMATE-IN-BUG-TOSS":
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-BUG-TOSS":
				which_graphic_on_screen = "BUG-TOSS";
				print_writer.get(1).println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
				break;
				
			case "ANIMATE-OUT":
				switch(which_graphic_on_screen) {
				case "BUG-TOSS":
					print_writer.get(1).println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
					which_graphic_on_screen = "";
					break;
				}
				break;
			}
			break;
			
		}
		
		return null;
	}
	
	public void populateBugToss(PrintWriter print_writer,String viz_scene, MatchAllData match, String session_selected_broadcaster) throws InterruptedException
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "ICC_CWCU19":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getMatch().getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				if(match.getSetup().getTossWinningTeam() == match.getSetup().getHomeTeamId()) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$Lt_Position$LINES$1Line$SelectStatValue*FUNCTION*Omo*vis_con SET 0 \0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$Lt_Position$LINES$1Line$SubHeaderGrp$SubHeaderText$txt_StatHead1*GEOM*TEXT SET " +
							match.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Lt_Position$LINES$1Line$SelectStatValue$RightGrp$StatHeadHrp$MaxSize$txt_StatValue*GEOM*TEXT SET " +
							"" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Lt_Position$LINES$1Line$SubHeaderGrp$SubHeaderText$Txt_StatHead2*GEOM*TEXT SET " +
							" WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + "\0");

				}else {
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$Lt_Position$LINES$1Line$SelectStatValue*FUNCTION*Omo*vis_con SET 0 \0");
					
					print_writer.println("-1 RENDERER*TREE*$Main$All$Lt_Position$LINES$1Line$SubHeaderGrp$SubHeaderText$txt_StatHead1*GEOM*TEXT SET " +
							match.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Lt_Position$LINES$1Line$SelectStatValue$RightGrp$StatHeadHrp$MaxSize$txt_StatValue*GEOM*TEXT SET " +
							"" + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$Lt_Position$LINES$1Line$SubHeaderGrp$SubHeaderText$Txt_StatHead2*GEOM*TEXT SET " +
							" WON TOSS & CHOSE TO " + match.getSetup().getTossWinningDecision() + "\0");
					
				}
				
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png In 1.040 \0");
			TimeUnit.MILLISECONDS.sleep(200);
			break;
		}
	}
}