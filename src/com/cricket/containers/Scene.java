package com.cricket.containers;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Scene {
	
	private String scene_path;
	private String broadcaster;
	private String which_layer;
	
	public Scene() {
		super();
	}

	public Scene(String scene_path, String which_layer) {
		super();
		this.scene_path = scene_path;
		this.which_layer = which_layer;
	}
	
	public String getScene_path() {
		return scene_path;
	}

	public void setScene_path(String scene_path) {
		this.scene_path = scene_path;
	}
	
	public String getBroadcaster() {
		return broadcaster;
	}

	public void setBroadcaster(String broadcaster) {
		this.broadcaster = broadcaster;
	}

	public String getWhich_layer() {
		return which_layer;
	}

	public void setWhich_layer(String which_layer) {
		this.which_layer = which_layer;
	}
	
	
	@Override
	public String toString() {
		return "Scene [scene_path=" + scene_path + ", broadcaster=" + broadcaster + ", which_layer=" + which_layer
				+ "]";
	}

	public void scene_load(List<PrintWriter> print_writers, String broadcaster) throws InterruptedException
	{
		switch (broadcaster.toUpperCase()) {
		case "DOAD-VIZ-MULTI":
			for(PrintWriter print_writer : print_writers) {
				switch(this.which_layer.toUpperCase()) {
				case "FRONT_LAYER":
					print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*" + this.scene_path + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0");
					TimeUnit.MILLISECONDS.sleep(500);
					break;
				case "MIDDLE_LAYER":
					print_writer.println("-1 RENDERER SET_OBJECT SCENE*" + this.scene_path + "\0");
					print_writer.println("-1 RENDERER*SCENE_DATA INITIALIZE \0");
					TimeUnit.MILLISECONDS.sleep(500);
					break;
				}
			}
			break;
		}
	}	
	public void scene_load(PrintWriter print_writer, String broadcaster) throws InterruptedException
	{
		switch (broadcaster.toUpperCase()) {
		case "DOAD_VIZ": case "GPCL": case "ACC": case "DOAD-VIZ-MULTI": case "NEPAL_T20": case "DOAD_LLC": case "ICPL": case "LCT": 
		case "FAIR_BREAK":case "MPL": case "APL": case "MAHARAJA_T20": case "RPL": case "USPL": case "ICC_CWCU19": case "KOLKATA_T20":
		case "PPL": case "KERALA_T20": case "ICC_BIGSCREEN_DOAD_VIZ_SCORING":
			switch(this.which_layer.toUpperCase()) {
			case "FRONT_LAYER":
				print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*" + this.scene_path + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "MIDDLE_LAYER":
				print_writer.println("-1 RENDERER SET_OBJECT SCENE*" + this.scene_path + "\0");
				print_writer.println("-1 RENDERER*SCENE_DATA INITIALIZE \0");
				break;
			case "BACK_LAYER":
				print_writer.println("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*" + this.scene_path + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*SCENE_DATA INITIALIZE \0");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			}
			
			break;
		case "PUNJAB_T20":
			switch(this.which_layer.toUpperCase()) {
			case "FRONT_LAYER":
				print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*" + this.scene_path + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "BACK_LAYER":
				print_writer.println("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*" + this.scene_path + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*SCENE_DATA INITIALIZE \0");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			}
			break;
			
		case "BIG_SCREEN":
			switch(this.which_layer.toUpperCase()) {
			case "1":
				print_writer.println("LAYER1*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "2":
				print_writer.println("LAYER2*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "3":
				print_writer.println("LAYER3*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				
//				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
//				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;	
			}
			break;
		case "ICC_BIG_SCREEN": case "ICC_BIGSCREEN_DOAD_SCORING":
			switch(this.which_layer.toUpperCase()) {
			case "1":
				print_writer.println("LAYER1*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "2":
				print_writer.println("LAYER2*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "3":
				print_writer.println("LAYER3*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "4":
				print_writer.println("LAYER4*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			}
			break;
		case "DOAD_AR": case "ICPL_AR": case "FAIR_BREAK_AR": case "T20_MUMBAI_AR": case "EVEREST_AR_VR": case "PLOTTER": 
		case "EUROPE_LEAGUE": case "BARODA_AR": case "MP_AR":
			switch(this.which_layer.toUpperCase()) {
			case "1":
				print_writer.println("LAYER1*EVEREST*SCENE LOAD " + this.scene_path + ";");
	//			print_writer.println("LAYER3*EVEREST*SCENE LOAD " + this.scene_path + ";");
				
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "2":
				print_writer.println("LAYER2*EVEREST*SCENE LOAD " + this.scene_path + ";");
	//			print_writer.println("LAYER3*EVEREST*SCENE LOAD " + this.scene_path + ";");
	//			
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			}
			break;	
		case "RSWS":	
			switch(this.which_layer.toUpperCase()) {
			case "FRONT_LAYER":
				print_writer.println("LAYER1*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				TimeUnit.MILLISECONDS.sleep(500);
				print_writer.println("LAYER1*EVEREST*SCENE LAYER_RENDER 0;");
				break;
			case "MIDDLE_LAYER":
				print_writer.println("LAYER2*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				TimeUnit.MILLISECONDS.sleep(500);
				print_writer.println("LAYER2*EVEREST*SCENE LAYER_RENDER 0;");
				break;
			case "THIRD_LAYER":
				print_writer.println("LAYER3*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				TimeUnit.MILLISECONDS.sleep(500);
				print_writer.println("LAYER3*EVEREST*SCENE LAYER_RENDER 0;");
				break;
				
			}
			break;
		case "BUKHATIR": case "ASSAM": case "EVEREST_NEPAL_T20": case "THAILAND": case "ACC_NEPAL": case "EVEREST_PUNJAB_T20": case "EVEREST_APL_T20":
		 case "EVEREST_MPL_T20":case "EVEREST_BENGAL_T20": case "EVEREST_PPL_T20": case "EVEREST_KCL_T20": case "EVEREST_KCL": case "ARUNACHAL":
		 case "EVEREST_LEGENDS_90":	case "SPL":		
			switch(this.which_layer.toUpperCase()) {
			case "FRONT_LAYER":
				print_writer.println("LAYER1*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(1000);
				
				print_writer.println("LAYER6*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				break;
			case "MIDDLE_LAYER":
				print_writer.println("LAYER2*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			}
			break;
		}
	}
}
