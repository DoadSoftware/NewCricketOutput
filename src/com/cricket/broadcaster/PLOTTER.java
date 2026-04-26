package com.cricket.broadcaster;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.cricket.service.CricketService;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cricket.model.BattingCard;
import com.cricket.model.Configuration;
import com.cricket.model.Inning;
import com.cricket.model.MatchAllData;
import com.cricket.containers.Infobar;
import com.cricket.containers.Pitch;
import com.cricket.containers.Scene;

public class PLOTTER extends Scene{

	private String status;
	private String slashOrDash = "-"; 
	public Infobar infobar = new Infobar();
	public String session_selected_broadcaster = "PLOTTER";
	public String which_graphics_onscreen = "";
	
	public Inning inning;
	public BattingCard battingCard;
	public List<BattingCard> battingCardList = new ArrayList<BattingCard>();
	public List<String> this_data_str = new ArrayList<String>();
	
	public PLOTTER() {
		super();
	}

	public PLOTTER(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Infobar updateInfobar(Scene scene, MatchAllData match,boolean show_speed, PrintWriter print_writer,Configuration config ,CricketService cricketService) 
	{
		switch (which_graphics_onscreen.toUpperCase()) {
			
		}
		return infobar;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, MatchAllData match, PrintWriter print_writer, String valueToProcess, Configuration config, 
			List<Scene> scenes) throws InterruptedException, StreamReadException, DatabindException, IOException {
		
		switch (whatToProcess.toUpperCase()) {
		
		case "POPULATE-SDI_ON": case "POPULATE-SDI_OFF": case "POPULATE-PLOTTER_IN": case "POPULATE-PLOTTER_OUT": case "POPULATE-PLOTTER_STOP":	
		case "LOAD_PLOTTER_SCENE": case "POPULATE-PITCH_MAP":
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-SDI_ON":
				print_writer.println("LAYER1*EVEREST*GLOBAL SDI_OUTPUT ON;");
				break;
			case "POPULATE-SDI_OFF":
				print_writer.println("LAYER1*EVEREST*GLOBAL SDI_OUTPUT OFF;");
				break;
			case "POPULATE-PLOTTER_IN":
				print_writer.println("LAYER1*EVEREST*GLOBAL SDI_OUTPUT ON;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*IN START;");
				break;
			case "POPULATE-PLOTTER_OUT":
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*IN COUNTINUE;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SDI_OUTPUT OFF;");
				break;
			case "POPULATE-PLOTTER_STOP":
				print_writer.println("LAYER1*EVEREST*GLOBAL SDI_OUTPUT OFF;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*IN SHOW 25.0;");
				break;
			case "LOAD_PLOTTER_SCENE":
				scenes.get(0).setScene_path("C:/Plotter/Scene/Plotter.sum");
				scenes.get(0).scene_load(print_writer, session_selected_broadcaster);
				which_graphics_onscreen = "";
				break;
			case "POPULATE-PITCH_MAP":
				if(which_graphics_onscreen.isEmpty()) {
					scenes.get(0).setScene_path("C:/Plotter/PITCH MAP/PitchMapsum.sum");
					scenes.get(0).scene_load(print_writer, session_selected_broadcaster);
					TimeUnit.MILLISECONDS.sleep(1000);
				}

				populatePitchMap(print_writer, valueToProcess, session_selected_broadcaster);
				which_graphics_onscreen = "PITCH_MAP";
				break;
			}
		}
		return null;
	}
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
	
	public void populatePitchMap(PrintWriter print_writer, String fileName, String session_selected_broadcaster) throws InterruptedException, StreamReadException, 
	DatabindException, IOException {
		String data = "";
		Pitch pitch = new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + "PitchMap\\"  + fileName), Pitch.class);
		if(pitch.getLengthDisplay() != null) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Right_Data " + (pitch.getLengthDisplay().get(0).getValues().size()-1) + ";");
			if(pitch.getLengthDisplay().get(0).getValues().size() == 1) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET GREY_Data " +  pitch.getLengthDisplay().get(0).getValues().get(0).getValue() + ";");
			}else if(pitch.getLengthDisplay().get(0).getValues().size() == 2) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET GREY_Data " +  pitch.getLengthDisplay().get(0).getValues().get(0).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Green_Data "+  pitch.getLengthDisplay().get(0).getValues().get(1).getValue() + ";");
			}else if(pitch.getLengthDisplay().get(0).getValues().size() == 3) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET GREY_Data " +  pitch.getLengthDisplay().get(0).getValues().get(0).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Green_Data "+  pitch.getLengthDisplay().get(0).getValues().get(1).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Yellow_Data "+ pitch.getLengthDisplay().get(0).getValues().get(2).getValue() + ";");
			}else if(pitch.getLengthDisplay().get(0).getValues().size() == 4) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET GREY_Data " +  pitch.getLengthDisplay().get(0).getValues().get(0).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Green_Data "+  pitch.getLengthDisplay().get(0).getValues().get(1).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Yellow_Data "+ pitch.getLengthDisplay().get(0).getValues().get(2).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Orange_Data " + pitch.getLengthDisplay().get(0).getValues().get(3).getValue() + ";");
			}else if(pitch.getLengthDisplay().get(0).getValues().size() == 5) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET GREY_Data " +  pitch.getLengthDisplay().get(0).getValues().get(0).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Green_Data "+  pitch.getLengthDisplay().get(0).getValues().get(1).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Yellow_Data "+ pitch.getLengthDisplay().get(0).getValues().get(2).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Orange_Data " + pitch.getLengthDisplay().get(0).getValues().get(3).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Red_Data " + pitch.getLengthDisplay().get(0).getValues().get(4).getValue() + ";");
			}else if(pitch.getLengthDisplay().get(0).getValues().size() == 6) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET GREY_Data " +  pitch.getLengthDisplay().get(0).getValues().get(0).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Green_Data "+  pitch.getLengthDisplay().get(0).getValues().get(1).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Yellow_Data "+ pitch.getLengthDisplay().get(0).getValues().get(2).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Orange_Data " + pitch.getLengthDisplay().get(0).getValues().get(3).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Red_Data " + pitch.getLengthDisplay().get(0).getValues().get(4).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Purple_Data " + pitch.getLengthDisplay().get(0).getValues().get(5).getValue() + ";");
			}else if(pitch.getLengthDisplay().get(0).getValues().size() == 7) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET GREY_Data " +  pitch.getLengthDisplay().get(0).getValues().get(0).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Green_Data "+  pitch.getLengthDisplay().get(0).getValues().get(1).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Yellow_Data "+ pitch.getLengthDisplay().get(0).getValues().get(2).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Orange_Data " + pitch.getLengthDisplay().get(0).getValues().get(3).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Red_Data " + pitch.getLengthDisplay().get(0).getValues().get(4).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Purple_Data " + pitch.getLengthDisplay().get(0).getValues().get(5).getValue() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Blue_Data " + pitch.getLengthDisplay().get(0).getValues().get(6).getValue() + ";");
			}
		}
		if(pitch.getLegend() != null) {
			for(int j=1;j<=pitch.getLegend().size();j++) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Designs$BALL_TYPE_" + j + "*MATERIAL*COLOR SET DIFFUSE " 
						+ pitch.getLegend().get(j-1).getColorCode().replace(",", " ") + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Legend$Balls$Ball_" + j + "*MATERIAL*COLOR SET DIFFUSE " 
						+ pitch.getLegend().get(j-1).getColorCode().replace(",", " ") + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Ball_Details_" + j + " " + pitch.getLegend().get(j-1).getLabel() + ";");	
			}
			
			if(pitch.getLegend().size() == 2) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET LEGEND_DATA 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET LEGEND_BALL 1;");
			}else if(pitch.getLegend().size() == 3) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET LEGEND_DATA 2;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET LEGEND_BALL 2;");
			}else if(pitch.getLegend().size() == 4) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET LEGEND_DATA 3;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 2;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET LEGEND_BALL 3;");
			}else if(pitch.getLegend().size() == 5) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET LEGEND_DATA 4;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET Base_Selector 3;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET LEGEND_BALL 4;");
			}
		}
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET strike_selector 0;");
		
		if(pitch.getTitle().contains(" v ")) {
			if(pitch.getTitle().split(" v ")[1].equalsIgnoreCase("RHB")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET strike_selector 1;");
			}else if(pitch.getTitle().split(" v ")[1].equalsIgnoreCase("LHB")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET strike_selector 2;");
			}
		}else if(pitch.getTitle().contains(" vs ")) {
			if(pitch.getTitle().split(" vs ")[1].equalsIgnoreCase("RHB")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET strike_selector 1;");
			}else if(pitch.getTitle().split(" vs ")[1].equalsIgnoreCase("LHB")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET strike_selector 2;");
			}
		}
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET LEGEND_HEADER 1;");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET t_HEADER " + pitch.getTitle() + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET t_subHEADER " + pitch.getSubTitle() + ";");
		
//		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Right_Data*CONTAINER SET ACTIVE 0;");
		
		for(int i=0;i<=pitch.getBallData().size()-1;i++) {
			if(i==0) {
				data = data + pitch.getBallData().get(i).getLengthX_CM() + ":" + pitch.getBallData().get(i).getLengthY_CM() + ":0:"; 
			}else {
				data = data + "#" + pitch.getBallData().get(i).getLengthX_CM() + ":" + pitch.getBallData().get(i).getLengthY_CM() + ":0:"; 
			}
			
			if(pitch.getLegend() != null) {
				for(int j=0;j<=pitch.getLegend().size()-1;j++) {
					if(pitch.getBallData().get(i).getColorCode().equalsIgnoreCase(pitch.getLegend().get(j).getColorCode())) {
						data = data + j;
					}
				}
			}
		}
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$group*FUNCTION_SET_PROP*CLONER stdstrData=" + data + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$group*FUNCTION_SET_PROP*CLONER ParamBuildScene=1;");
		
		TimeUnit.MILLISECONDS.sleep(500);
		print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Director1 START;");
	}
}