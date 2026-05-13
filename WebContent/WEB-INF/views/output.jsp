<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Output Screen</title>
	
	<script type="text/javascript" src="<c:url value='/webjars/jquery/3.6.0/jquery.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/webjars/bootstrap/5.1.3/js/bootstrap.min.js'/>"></script>
	<script src="<c:url value='/webjars/select2/4.0.13/js/select2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/javascript/index.js'/>"></script>
	
	<link rel="stylesheet" href="<c:url value='/webjars/bootstrap/5.1.3/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<c:url value='/webjars/font-awesome/6.0.0/css/all.css'/>">
	<link rel="stylesheet" href="<c:url value='/webjars/select2/4.0.13/css/select2.min.css'/>">
  
    <style type="text/css">
  body{
     background: url('<c:url value="/resources/Images/bg.png"/>') no-repeat center center fixed;
	 background-size: cover;
	}
  .header-container {
    background-color: #0080FE;
    color: white;
    position: fixed;
 	z-index: 1000;
    padding: 3px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: fixed;
    top: 0px;
    left: 0;
    width: calc(100% - 78px);
    max-height: 60px;
    z-index: 1000; /* Ensure header is above other content */
    margin-left: 34px;
    box-shadow: 10px 4px 4px #9AA2A2;
  }
  .header-container img {
    max-width: 60px;
    max-height: 60px;
    width: calc(100% - 40px);
    margin-right: 10px;
    top: 0;
    bottom: 0;
    left: 0;
    
  }
  .header-container h2 {
	  margin: 0;
	  font-family: 'Arial Black', sans-serif;
	  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
	  font-size:  clamp(1.3em, 2vw, 3.5em); /* scales with screen size */
	  text-transform: uppercase;
	  letter-spacing: 3px;
	  white-space: nowrap;
	  margin-left: auto;
	  text-align: center;
	  margin-right: calc(23% + 3vw); /* responsive margin */
	}
	.header-container h3 {
		font-size:  clamp(0.8em, 2vw, 1em);
	}
  .content {
	  position: relative;
	  z-index: 1; /* must be lower than header */
	  margin-top: 50px; /* enough to sit below the fixed header */
	}
	.mb-0 {
      font-weight: bold;
      font-family: 'Arial Black', sans-serif;
      text-transform: uppercase;
      font-size: 2rem;
      -webkit-background-clip: text;
      color: transparent;
	  text-shadow: 
	    2px 2px 0 #0080FE, 
	    4px 4px 0 black,  
	    2px 2px 0 #000000; 
    }
    .card-body {
	  background: linear-gradient(145deg, #ffffff, #e6e6e6);
	  border-radius: 15px;
	  margin-top: 50px; /* Moves the div 50px down */
	  /* 3D effect */
	  box-shadow: 10px 10px 30px rgba(0, 0, 0, 0.2), 
	              -5px -5px 15px rgba(255, 255, 255, 0.5);
	}
	.custom-toggle {
      display: inline-block;
      font-size: 1.3rem;
      margin-right: 20px;
      position: relative;
	}
	
	.custom-toggle label {
	    display: flex;
	    align-items: center;
	    gap: 10px;
	    cursor: pointer;
	    position: relative;
	    user-select: none;
	}
	
	.custom-toggle i {
	    font-size: 1.4rem;
	    color: #007bff;
	}
	
	.custom-toggle input[type="checkbox"] {
	    opacity: 0;
	    width: 0;
	    height: 0;
	    position: absolute;
	}
	
	.slider {
	    position: relative;
	    width: 50px;
	    height: 25px;
	    background-color: #ccc;
	    border-radius: 25px;
	    transition: 0.4s;
	}
	
	.slider::before {
	    content: "";
	    position: absolute;
	    width: 20px;
	    height: 20px;
	    left: 3px;
	    top: 2.5px;
	    background-color: white;
	    border-radius: 50%;
	    transition: 0.4s;
	}
	
	input:checked + .slider {
	    background-color: #28a745;
	}
	
	input:checked + .slider::before {
	    transform: translateX(25px);
	}
	.col-form-label i {
	    margin-right: 8px;
	    color: #007bff;
	    font-size: 1.2rem;
	    vertical-align: middle;
	}
	/* Base Table Styling */
	.table {
	  width: 100%;
	  border-collapse: collapse;
	  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	  font-size: 1rem;
	  color: #333;
	  background-color: #fff;
	}
	
	/* Header Styling */
	.table thead {
	  background-color: #f8f9fa;
	  border-bottom: 2px solid #dee2e6;
	}
	
	.table thead th {
	  text-align: left;
	  padding: 12px 15px;
	  font-weight: 600;
	  color: #212529;
	}
	
	/* Row & Cell Styling */
	.table tbody td {
	  padding: 12px 15px;
	  border-bottom: 1px solid #dee2e6;
	}
	
	/* Zebra Striping */
	.table tbody tr:nth-child(even) {
	  background-color: #f2f2f2;
	}
	
	/* Hover Effect */
	.table tbody tr:hover {
	  background-color: #e9f5ff;
	}
	
	/* Responsive on smaller screens */
	@media (max-width: 768px) {
	  .table thead {
	    display: none;
	  }
	
	  .table, .table tbody, .table tr, .table td {
	    display: block;
	    width: 100%;
	  }
	
	  .table tr {
	    margin-bottom: 15px;
	    border: 1px solid #ddd;
	    border-radius: 8px;
	    padding: 10px;
	    background-color: #fff;
	  }
	
	  .table td {
	    text-align: right;
	    padding-left: 50%;
	    position: relative;
	  }
	
	  .table td::before {
	    content: attr(data-label);
	    position: absolute;
	    left: 15px;
	    width: 45%;
	    padding-right: 10px;
	    text-align: left;
	    font-weight: bold;
	    color: #6c757d;
	  }
	}
	/* Style for input[type="button"] */
	input[type="button"] ,button,.btn{
	  background-color: #007bff;
	  color: white;
	  padding: 4px 8px;
	  font-size: 1rem;
	  font-weight:bold;
	  text-transform: uppercase;
	  border: none;
	  border-radius: 6px;
	  cursor: pointer;
	  transition: background-color 0.3s ease;
	  margin-right: 10px; /* Add space between buttons */
	 /*  box-shadow: 0 5px 0 #2E8B57, 0 10px 15px rgba(0, 0, 0, 0.1); /* Initial shadow for 3D effect
  	  transition: all 0.2s ease; */
	  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	}
	
	/* Hover effect for input[type="button"] 
	input[type="button"]:hover ,button :hover,.btn :hover{
	  background-color: #0056b3;
	  box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1), 0 3px 0 #2E8B57; 
  	  transform: translateY(-3px); 
	}
	 Active (clicked) button effect 
	button:active, input[type="button"]:active,.btn :active {
	  box-shadow: 0 3px 0 #2E8B57, 0 6px 5px rgba(0, 0, 0, 0.15);
	  transform: translateY(2px);
	}
	Disabled input[type="button"] styling 
	input[type="button"]:disabled ,.btn,
	button:disabled{
	  background-color: #d3d3d3;
	  color: #a1a1a1;
	  cursor: not-allowed;
	  box-shadow: none;
	}*/
	/* General style for the dropdown (select) */
	select {
	  background-color: #ffffff;
	  color: #333;
	  border: 1px solid #ccc;
	  border-radius: 6px;
	  padding: 10px;
	  font-size: 1rem;
	  font-weight:bold;
	  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	  transition: border-color 0.3s ease;
	  cursor: pointer;
	  margin-bottom: 10px; /* Space between dropdowns */
	}
	
	/* Hover effect for the dropdown */
	select:hover {
	  border-color: #007bff; /* Change border color on hover */
	}
	
	/* Focus effect for the dropdown */
	select:focus {
	  border-color: #0056b3; /* Focus border color */
	  outline: none; /* Remove the default outline */
	}
	
	/* Disabled dropdown styling */
	select:disabled {
	  background-color: #f2f2f2;
	  color: #999;
	  cursor: not-allowed;
	}
	
	/* Dropdown container styling (optional) */
	#dropdown-container {
	  display: flex;
	  flex-direction: column;
	  gap: 10px; /* Space between dropdowns */
	  max-width: 300px;
	}
	
	/* Styling for individual options inside the dropdown */
	select option {
	  padding: 10px; /* Padding inside options */
	  font-size: 1rem;
	  font-weight:bold;
	}
	
	/* Optional: Customize the dropdown arrow */
	select::-ms-expand {
	  display: none; /* Hide default dropdown arrow in Internet Explorer */
	}
	
	select {
	  appearance: none; /* Remove default dropdown styling */
	  -webkit-appearance: none;
	  -moz-appearance: none;
	  font-weight:bold;
	  background-position: right center;
	  background-repeat: no-repeat;
	  padding-right: 30px; /* Space for custom arrow */
	}

	#cancel_graphics_btn{
	  background-color: #FA003F;
	}
	/* Basic styling for the h6 element */
	h6 {
	  font-size: 1.3rem;
	  font-weight: 700;
	  color: #ffffff;
	  margin: 10px 0;
	  padding: 10px 20px;
	  background: linear-gradient(to bottom, #77B1D4, #77B1D4); /* Gradient background for WordArt feel */
	  border-left: 4px solid #003d80;
	  border-radius: 6px;
	  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	
	  /* 3D Text Effect */
	  text-shadow: 1px 1px 0 #333, 2px 2px 0 #222, 3px 3px 0 #111;
	
	  /* Box Shadow for 3D block feel */
	  box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);
	
	  /* Transition for hover effect */
	  transition: background 0.3s ease, transform 0.2s ease;
	}
	/* Hover effect for h6 */
	h6:hover {
	  background: linear-gradient(to bottom, #339cff, #0062cc);
	  transform: translateY(-2px);
	  /*background-color: #e2e2e2;  Light background change on hover */
	  cursor: pointer; /* Change cursor to pointer on hover */
	}
	
	/* Focus effect (for accessibility) */
	h6:focus {
	  outline: 3px solid #0056b3; /* Blue outline when focused */
	}
	
	.card-body:hover {
	  transform: translateY(-2px);
	  cursor: pointer;
	}
	#captions_div {
	    background: #f9fafa; /* light neutral background */
	    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
	    font-family: 'Segoe UI', sans-serif;
	    color: #1a1a1a;
	    display: flex;
	    flex-wrap: wrap;
	    gap: 20px;
	    margin: 20px auto;
	}
	
	#captions_div label {
	    flex: 1 1 30%;
	    font-size: 1.4rem;
	    font-weight: 600;
	    color: #1a1a1a;
	}
	
	#captions_div label i {
	    margin-right: 8px;
	    color: #007bff;
	}
	
	/* Highlighted text like scores */
	#inning2_teamScore_lbl {
	    padding: 5px 10px;
	    border-radius: 10px;
	    display: inline-block;
	    font-weight: bold;
	}
	
	/* Green batsman name like "GUPTA*" */
	#inning1_battingcard1_lbl,
	#inning1_battingcard2_lbl {
	    color: #2e7d32;
	    font-weight: bold;
	}
	
	/* Toggle Switches */
	.custom-toggle {
	    display: flex;
	    align-items: center;
	    gap: 10px;
	    margin-top: 10px;
	    font-weight: 500;
	}
	
	.custom-toggle input[type="checkbox"] {
	    appearance: none;
	    width: 40px;
	    height: 20px;
	    background: #ccc;
	    border-radius: 10px;
	    position: relative;
	    outline: none;
	    cursor: pointer;
	    transition: background 0.3s;
	}
	
	.custom-toggle input[type="checkbox"]:checked {
	    background: #4caf50;
	}
	
	.custom-toggle input[type="checkbox"]::before {
	    content: "";
	    position: absolute;
	    top: 2px;
	    left: 3px;
	    width: 16px;
	    height: 16px;
	    background: white;
	    border-radius: 50%;
	    transition: transform 0.3s;
	}
	
	.custom-toggle input[type="checkbox"]:checked::before {
	    transform: translateX(20px);
	}
	.btn.btn-sm {
	  margin-bottom: 10px;    /* Adds vertical gap between buttons */
	}

	
  </style> 
  <script type="text/javascript">
	$(document).on("keydown", function(e){
	  
	  if($('#waiting_modal').hasClass('show')) {
		  e.cancelBubble = true;
		  e.stopImmediatePropagation();
    	  e.preventDefault();
		  return false;
	  }
	  
      var evtobj = window.event? event : e;
      
      switch(e.target.tagName.toLowerCase())
      {
      case "input": case "textarea":
    	 break;
      default:
    	  if(evtobj.key != 'Tab'){
    		  e.preventDefault();
    	  }
    	  
	      var whichKey = '';
		  var validKeyFound = false;
	    
	      if(evtobj.ctrlKey) {
	    	  whichKey = 'Control';
	      }
	      if(evtobj.altKey) {
	    	  if(whichKey) {
	        	  whichKey = whichKey + '_Alt';
	    	  } else {
	        	  whichKey = 'Alt';
	    	  }
	      }
	      if(evtobj.shiftKey) {
	    	  if(whichKey) {
	        	  whichKey = whichKey + '_Shift';
	    	  } else {
	        	  whichKey = 'Shift';
	    	  }
	      }
	      
		  if(evtobj.keyCode) {
	    	  if(whichKey) {
	    		  if(!whichKey.includes(evtobj.key)) {
	            	  whichKey = whichKey + '_' + evtobj.key;
	    		  }
	    	  } else {
	        	  whichKey = evtobj.key;
	    	  }
		  }
		  validKeyFound = false;
		  if (whichKey.includes('_')) {
			  whichKey.split("_").forEach(function (this_key) {
				  switch (this_key) {
				  case 'Control': case 'Shift': case 'Alt':
					break;
				  default:
					validKeyFound = true;
					break;
				  }
			  });
		   } else {
			  if(whichKey != 'Control' && whichKey != 'Alt' && whichKey != 'Shift') {
				  validKeyFound = true;
			  }
		   }
			  
		   if(validKeyFound == true) {
			   console.log('whichKey = ' + whichKey);
			   processUserSelectionData('LOGGER_FORM_KEYPRESS',whichKey);
		   }
	      }
	  });
  
  setInterval(() => {
	  processCricketProcedures('READ-MATCH-AND-POPULATE');		
	}, 1000);
  
 	
  </script>
</head>
<body onload="onPageLoadEvent('OUTPUT')">
<div class="header-container">
    <img src="<c:url value='/resources/Images/Design.jpg'/>" alt="Logo">
    <h2>DESIGN ON A DIME</h2>
    <h3 align="right" style = "text-transform: uppercase;font-weight: bold;">${expiryDate} Days Left &nbsp;</h3>
  </div>
<form:form name="output_form" autocomplete="off" action="POST">
<div class="content py-5">
  <div class="container">
	<div class="row">
     <div class="col-auto col-lg-12">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
          <div class="card-body">
          
          	<!-- <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="cancel_match_setup_btn" id="cancel_match_setup_btn" onclick="processUserSelection(this)">
		  		<i class="fas fa-window-close"></i> Back</button>
	         </div> -->
	         <!-- <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
	         	<h5 class="mb-0" id="expiry_message">Software Expire on: ${expiryDate}</h5>
	         </div> -->
	         
	         
			  <div id="select_graphic_options_div" style="display:none;">
			  </div>
			  <div id="lastxball_div" style="display:none;">
			  </div>
			  <div id = "stats_div" class="row form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			  	<div id = "home_stats_div" class="col"></div>
				<div id = "away_stats_div" class = "col"></div>
			  </div>
			  <div id="captions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			  	<!--  <label class="col-sm-4 col-form-label text-left">${licence_expiry_message} </label> -->

			<label class="col-sm-4 col-form-label text-left">
			    <i class="fas fa-file-video"></i> <b>Match:</b> ${session_match.match.matchFileName}</label>
			
			<label class="col-sm-4 col-form-label text-left">
			    <i class="fas fa-broadcast-tower"></i> <b>Broadcaster:</b> ${session_configuration.broadcaster.replace("_"," ")}
			</label>
			
			<label class="col-sm-4 col-form-label text-left">
			    <i class="fas fa-broadcast-tower"></i> <b>2nd Broadcaster:</b> ${session_configuration.secondaryBroadcaster.replace("_"," ")}
			</label>
			
			<label id="selected_inning" class="col-sm-4 col-form-label text-left" style="font-weight: bolder;">Selected Inning:</label>
			<label id="inning1_teamScore_lbl" class="col-sm-4 col-form-label text-left">-</label>			
			<label id="inning2_teamScore_lbl" class="col-sm-4 col-form-label text-left"> -</label>
			<label id="inning1_battingcard1_lbl" class="col-sm-4 col-form-label text-left">
			    <img id="batter1_img" src="<c:url value="/resources/Images/batter.png" />" alt="Batter" style="width:50px; height:50px; vertical-align:middle; margin-right:5px;">
			    <span id="batter1_text">-</span>
			</label>
			
			<label id="inning1_battingcard2_lbl" class="col-sm-4 col-form-label text-left">
			    <img id="batter2_img" src="<c:url value="/resources/Images/batter.png" />" alt="Batter" style="width:50px; height:50px; vertical-align:middle; margin-right:5px;">
			    <span id="batter2_text">-</span>
			</label>
			
			<label id="inning1_bowlingcard_lbl" class="col-sm-4 col-form-label text-left">
			    <img id="bowler_img" src="<c:url value="/resources/Images/bowler.png" />" alt="Bowler" style="width:50px; height:50px; vertical-align:middle; margin-right:5px;">
			    <span id="bowler_text">-</span>
			</label>
			   <!-- <c:if test="${(session_selected_broadcaster == 'DOAD_LLC')}">
			    <label id="speed_lbl" class="col-sm-4 col-form-label text-left">Show Speed: YES</label>
			    </c:if>
  				
  				<div class="left">
  				 <c:if test="${(session_selected_second_broadcaster == 'DOAD_LLC')}">
  					<label>SPEED </label>
  					<input type = "text" name = "speedtext" id="speedtext"/>
  					<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  			name="animate_in_speed" id="animate_in_speed" onclick="processUserSelection(this)"> Animate-In Speed </button>
  				 </c:if>
  				</div>-->
  				
  				<div class="left" style = "margin-top: 5px;">
  				
  				<c:if test="${(session_selected_broadcaster  != ' ')}">
  					<c:if test="${(session_selected_broadcaster != 'ICC_BIG_SCREEN') || (session_selected_broadcaster != '')}">
  					
			  		<!-- <button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="animateout_graphic_btn" id="animateout_graphic_btn" onclick="processUserSelection(this)"> AnimateOut (-) </button> -->
  					</c:if>
  					<c:if test="${(session_selected_broadcaster != 'PLOTTER')}">
  					<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="headToHead_file" id="headToHead_file" onclick="processUserSelection(this)"> Head To Head </button>
			  		
			  		<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="clearall_graphic_btn" id="clearall_graphic_btn" onclick="processUserSelection(this)"> Clear All (SpaceBar) </button>
  					</c:if>
  					
  				</c:if>
  				
  				<c:if test="${(session_selected_broadcaster == 'PLOTTER')}">
	  				<label id="sdi_on" class="col-sm-4 col-form-label text-left" style="font-weight: bolder;">SDI ON - F1</label>
					<label id="sdi_off" class="col-sm-4 col-form-label text-left" style="font-weight: bolder;">SDI OFF - F2</label>
					<label id="in" class="col-sm-4 col-form-label text-left" style="font-weight: bolder;">GRAPHIC IN - F3</label>
					<label id="out" class="col-sm-4 col-form-label text-left" style="font-weight: bolder;">GRAPHIC OUT - F4</label>
					<label id="stop" class="col-sm-4 col-form-label text-left" style="font-weight: bolder;">STOP POINT - F5</label>
					<label id="load_plotter" class="col-sm-4 col-form-label text-left" style="font-weight: bolder;">LOAD PLOTTER - F6</label>
					<label id="load_pitchMap" class="col-sm-4 col-form-label text-left" style="font-weight: bolder;">PITCH MAP - F7</label>
  				</c:if>
  				
  				<c:if test="${(session_selected_broadcaster == 'MAHARAJA_T20')}">
	  				<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
						name="field_plotter_icc_graphic_btn" id="field_plotter_icc_graphic_btn" onclick="processUserSelection(this)"> Plotter </button>
					<br>
					AUDIO ON-OFF <input type="checkbox" id="audioOnOrOff" name="audioOnOrOff" value="true" onclick="processUserSelection(this)" checked>
  				</c:if>
  				
  				<c:if test="${(session_selected_broadcaster == 'MPL')}">
  				<button style="background-color:#ffeb2b;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="animateoutfreehit_graphic_btn" id="animateoutfreehit_graphic_btn" onclick="processUserSelection(this)"> AnimateOut FREEHIT </button>
			  	
			  	<button style="background-color:orange;color:white;margin:3px;" class="btn btn-sm" type="button"
			  		name="orangecap_honour_btn" id="orangecap_honour_btn" onclick="processUserSelection(this)"> Orange cap honour</button>
			  		
			  	<button style="background-color:purple;color:white;margin:3px;" class="btn btn-sm" type="button"
			  		name="purplecap_honour_btn" id="purplecap_honour_btn" onclick="processUserSelection(this)"> Purple cap honour</button>
			  	
			  	<button style="background-color:purple;color:white;margin:3px;" class="btn btn-sm" type="button"
			  		name="pre_sum_btn" id="pre_sum_btn" onclick="processUserSelection(this)"> Previous Summary</button>	
			  	
			  	<button style="background-color:purple;color:white;margin:3px;" class="btn btn-sm" type="button"
			  		name="mission_btn" id="mission_btn" onclick="processUserSelection(this)"> Mission</button>		
			  		
  				</c:if>
  				<c:if test="${(session_selected_broadcaster == 'EVEREST_BENGAL_T20')}">
  				<button style="background-color:#FFA500;color:#000000;" class="btn btn-sm" type="button"
			  		name="boundaries_graphic_btn" id="boundaries_graphic_btn" onclick="processUserSelection(this)"> Boundaries</button>
  				<button style="background-color:#FFA500;color:#000000;" class="btn btn-sm" type="button"
			  		name="Wicket_graphic_btn" id="Wicket_graphic_btn" onclick="processUserSelection(this)"> Team Wickets</button>
			  		
			  	<button style="background-color:#FFA500;color:#000000;" class="btn btn-sm" type="button"
			  		name="Review_graphic_btn" id="Review_graphic_btn" onclick="processUserSelection(this)"> Team Review</button>	
  	
  				<button style="background-color:#FFA500;color:#000000;" class="btn btn-sm" type="button"
			  		name="superover_graphic_btn" id="superover_graphic_btn" onclick="processUserSelection(this)"> Super Over Explainer </button>
			  		
			  	<button style="background-color:#FFA500;color:#000000;" class="btn btn-sm" type="button"
			  		name="split_db_graphic_btn" id="split_db_graphic_btn" onclick="processUserSelection(this)"> SPLIT </button>	
			  		
			  	<button style="background-color:#FFA500;color:#000000;" class="btn btn-sm" type="button"
			  		name="tournamentrules_graphic_btn" id="tournamentrules_graphic_btn" onclick="processUserSelection(this)"> Tournament Rules </button>
			  		
			  	<button style="background-color:#FFA500;color:#000000;" class="btn btn-sm" type="button"
			  		name="Promo_graphic_btn" id="Promo_graphic_btn" onclick="processUserSelection(this)"> Promo </button>	
  				</c:if>
  				<c:if test="${(session_selected_broadcaster == 'ICC_BIG_SCREEN') || (session_selected_broadcaster == 'ICC_BIGSCREEN_DOAD_SCORING') || 
  								(session_selected_broadcaster == 'ICC_BIGSCREEN_DOAD_VIZ_SCORING')}">
  				<!-- <button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="animateoutscorebug_icc_graphic_btn" id="animateoutscorebug_icc_graphic_btn" onclick="processUserSelection(this)"> AnimateOut Scorebug </button>
  				<br><br>
  				 -->
  				<div>
  					<label>SPEED </label>
  					<input type = "text" name = "speedtext" id="speedtext"/>
  					<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  			name="animate_in_speed" id="animate_in_speed" onclick="processUserSelection(this)"> Animate-In Speed </button>
  				 </div> 
			  <br>
			  	<!-- <div class="left">
			  	<button style="background-color:#FFFF00;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="scorebug_icc_graphic_btn" id="scorebug_icc_graphic_btn" onclick="processUserSelection(this)"> ScoreBug (F12) </button>	
			  	<button style="background-color:#FFFF00;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="scorebug_changeon_icc_graphic_btn" id="scorebug_changeon_icc_graphic_btn" onclick="processUserSelection(this)"> ScoreBug ChangeOn (ALT + 2)</button>
			  	</div>-->
			  	<div class="left">	
			  	<button style="background-color:#008000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="decision_graphic_btn" id="decision_graphic_btn" onclick="processUserSelection(this)"> Decision Pending (N) </button>
			  	<button style="background-color:#008000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="decision_out_not_graphic_btn" id="decision_out_not_graphic_btn" onclick="processUserSelection(this)"> Decision Out/NotOut (SHIFT + N) </button>
			  		
			  	<br>	
			  	<button style="background-color:#fcff33;color:#080801; margin:3px; font-weight: bold; text-shadow: 1px 1px 2px #aaa; box-shadow: 2px 2px 5px rgba(0,0,0,0.2);" class="btn btn-sm" type="button"
				        name="PP1_icc_graphic_btn" id ="PP1_icc_graphic_btn" onclick="processUserSelection(this)"> PP1 </button>    
				<c:if test="${(session_match.setup.matchType == 'ODI')}">
			  		<button style="background-color:#9aee12;color:#080801; margin:3px; font-weight: bold; text-shadow: 1px 1px 2px #aaa; box-shadow: 2px 2px 5px rgba(0,0,0,0.2);" class="btn btn-sm" type="button"
				        	name="PP2_icc_graphic_btn" id ="PP2_icc_graphic_btn" onclick="processUserSelection(this)"> PP2 </button>    
					<button style="background-color:#12eec6;color:#080801; margin:3px; font-weight: bold; text-shadow: 1px 1px 2px #aaa; box-shadow: 2px 2px 5px rgba(0,0,0,0.2);" class="btn btn-sm" type="button"
				        	name="PP3_icc_graphic_btn" id ="PP3_icc_graphic_btn" onclick="processUserSelection(this)"> PP3 </button>
			  	</c:if>
			  	</div>
			  	<div class="left">
			  	
			  	<button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
		  			name="freehit_icc_graphic_btn" id="freehit_icc_graphic_btn" onclick="processUserSelection(this)"> FreeHit (Z) </button>
			  	<button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
		  			name="four_icc_graphic_btn" id="four_icc_graphic_btn" onclick="processUserSelection(this)"> Four (X) </button>
		  		<button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
		  			name="six_icc_graphic_btn" id="six_icc_graphic_btn" onclick="processUserSelection(this)"> Six (C) </button>
		  		<button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="wicket_icc_graphic_btn" id="wicket_icc_graphic_btn" onclick="processUserSelection(this)"> Wicket (V) </button>
			  	 <button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="wide_icc_graphic_btn" id="wide_icc_graphic_btn" onclick="processUserSelection(this)"> Wide (H) </button> 
			  	<button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="duck_icc_graphic_btn" id="duck_icc_graphic_btn" onclick="processUserSelection(this)"> Duck (G)</button>
			  	<button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button" 
			  		name="HatTrick_icc_graphic_btn" id="HatTrick_icc_graphic_btn" onclick="processUserSelection(this)"> Hat Trick </button>
			   <button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button" 
			  		name="HatTrickBall_icc_graphic_btn" id="HatTrickBall_icc_graphic_btn" onclick="processUserSelection(this)"> Hat Trick Ball </button>		
			  	<!-- 
					
			  	<button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="hundred_icc_graphic_btn" id="hundred_icc_graphic_btn" onclick="processUserSelection(this)"> 100  </button>
			  	<button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="fifty_icc_graphic_btn" id="fifty_icc_graphic_btn" onclick="processUserSelection(this)"> 50  </button>
			  	<button style="background-color:#FF0000;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button" 
			  		name="catch_icc_graphic_btn" id="catch_icc_graphic_btn" onclick="processUserSelection(this)"> catch </button>
			  	 -->
			   	
			   	</div>
			  	
			  	
			  	<div class="left">
			  	<!--<button style="background-color:#FFC0CB;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="image4_3_icc_graphic_btn" id="image4_3_icc_graphic_btn" onclick="processUserSelection(this)"> Image 4*3 (CTRL + I) </button>
			  	<button style="background-color:#FFC0CB;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="image16_9_icc_graphic_btn" id="image16_9_icc_graphic_btn" onclick="processUserSelection(this)"> Image 16*9 (I) </button>
			  	<button style="background-color:#FFC0CB;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="imageloop_icc_graphic_btn" id="imageloop_icc_graphic_btn" onclick="processUserSelection(this)"> Image Loop (SHIFT + I) </button>
			  	<button style="background-color:#FFC0CB;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="imagedrop_icc_graphic_btn" id="imagedrop_icc_graphic_btn" onclick="processUserSelection(this)"> Image DropDown (U) </button>
		 		<button style="background-color:#FFC0CB;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="fantasydrop_icc_graphic_btn" id="fantasydrop_icc_graphic_btn" onclick="processUserSelection(this)"> Fantasy DropDown (CTRL + U) </button>	-->
			  	</div> 
			  	<div class="left">
			    <button style= "background-color:#FFA500;color:#000000; margin:3px;"  class="btn btn-sm" type="button"
			  		name="lineuplong_icc_graphic_btn" id="lineuplong_icc_graphic_btn" onclick="processUserSelection(this)"> BattingCard (F1) </button>
			  	<button style= "background-color:#FFA500;color:#000000; margin:3px;"  class="btn btn-sm" type="button"
			  		name="bowlingcard_icc_graphic_btn" id="bowlingcard_icc_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (Y) </button>		 
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="thisover_icc_graphic_btn" id="thisover_icc_graphic_btn" onclick="processUserSelection(this)"> This Over (O) </button>
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
		  			name="matchsummary_icc_graphic_btn" id="matchsummary_icc_graphic_btn" onclick="processUserSelection(this)"> Match Summary (P) </button>
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="extras_icc_graphic_btn" id="extras_icc_graphic_btn" onclick="processUserSelection(this)"> Extras (F2) </button>
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="infobar_icc_graphic_btn" id="infobar_icc_graphic_btn" onclick="processUserSelection(this)"> Inning Stats (F3) </button>
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="equation_icc_graphic_btn" id="equation_icc_graphic_btn" onclick="processUserSelection(this)"> EQUATION FULL (E) </button>
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="targetfull_icc_graphic_btn" id="targetfull_icc_graphic_btn" onclick="processUserSelection(this)"> TARGET FULL (D) </button>			
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="review_icc_graphic_btn" id="review_icc_graphic_btn" onclick="processUserSelection(this)"> Reviews Rem (F4) </button>
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="Fall_of_Wickets_graphic_btn" id="Fall_of_Wickets_graphic_btn" onclick="processUserSelection(this)"> HOW OUT (F6) </button> 
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="partnership_icc_graphic_btn" id="partnership_icc_graphic_btn" onclick="processUserSelection(this)"> P'SHIP w IMG (CURRENT) (SHIFT + K) </button> 
			  <!--	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="batter_icc_graphic_btn" id="batter_icc_graphic_btn" onclick="processUserSelection(this)"> Batter Score (SHIFT + F1) </button>
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlerfig_icc_graphic_btn" id="bowlerfig_icc_graphic_btn" onclick="processUserSelection(this)"> Bowler Fig (SHIFT + F2) </button>	-->
	 		  	 	
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="target_icc_graphic_btn" id="target_icc_graphic_btn" onclick="processUserSelection(this)"> TARGET RUN n BALL (SHIFT + D) </button> 
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="target_with_img_icc_bs_graphic_btn" id="target_with_img_icc_bs_graphic_btn" onclick="processUserSelection(this)"> Target With Img (CTRL + D) </button>
			  	
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="equation_with_img_icc_bs_graphic_btn" id="equation_with_img_icc_bs_graphic_btn" onclick="processUserSelection(this)"> EQUATION With Img </button>	
			    <button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="equationshort_icc_graphic_btn" id="equationshort_icc_graphic_btn" onclick="processUserSelection(this)"> EQUATION RUN n BALL (SHIFT + E) </button>

			   	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="runrate_icc_graphic_btn" id="runrate_icc_graphic_btn" onclick="processUserSelection(this)"> Run Rates (R) </button>
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="comp_icc_graphic_btn" id="comp_icc_graphic_btn" onclick="processUserSelection(this)"> Comparison (CTRL + F3) </button>
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="Phase_comp_icc_graphic_btn" id="Phase_comp_icc_graphic_btn" onclick="processUserSelection(this)"> Phase Comparison </button>	
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name=projected_icc_score_graphic_btn id="projected_icc_score_graphic_btn" onclick="processUserSelection(this)"> Projected (CTRL + A) </button>
			  		 
			  	<button style="background-color:#FFA500;color:#000000; margin:3px;" class="btn btn-sm" type="button"
			  		name="quickhowout_icc_graphic_btn" id="quickhowout_icc_graphic_btn" onclick="processUserSelection(this)"> Quick HowOut (CTRL + F6) </button> 
			  	</div>
			  	<div class="left">	
			  	 <button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="lineup_icc_graphic_btn" id="lineup_icc_graphic_btn" onclick="processUserSelection(this)"> LineUp Without Image (CTRL + F7) </button>
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="lineupimage_icc_graphic_btn" id="lineupimage_icc_graphic_btn" onclick="processUserSelection(this)"> LineUp Image (CTRL + F8) </button>
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="teamname_icc_graphic_btn" id="teamname_icc_graphic_btn" onclick="processUserSelection(this)"> TEAM FLAG (F) </button>
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
		  			name="toss_icc_graphic_btn" id="toss_icc_graphic_btn" onclick="processUserSelection(this)"> Toss (T) </button>
		  			
		  		<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="matchid_icc_graphic_btn" id="matchid_icc_graphic_btn" onclick="processUserSelection(this)"> MatchIdent (M) </button>
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="match_id_with_img" id="match_id_with_img" onclick="processUserSelection(this)"> MatchIdent with Player Img </button>	
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
		  			name="teamBoundary_icc_graphic_btn" id="teamBoundary_icc_graphic_btn" onclick="processUserSelection(this)"> Team Boundaries (B) </button>
		  		<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="freetext_icc_graphic_btn" id="freetext_icc_graphic_btn" onclick="processUserSelection(this)"> FreeText 1 LINE (K) </button> 
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="freetext2LINE_icc_graphic_btn" id="freetext2LINE_icc_graphic_btn" onclick="processUserSelection(this)"> FreeText 2 LINE (L) </button>
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="img_freetext2LINE_icc_graphic_btn" id="img_freetext2LINE_icc_graphic_btn" onclick="processUserSelection(this)"> Team flag with FreeText 2 LINE </button>		
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="six_distance_icc_graphic_btn" id="six_distance_icc_graphic_btn" onclick="processUserSelection(this)"> Six Distance (J)</button>
			  		
			  	<!-- <button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="six_distance_auto_icc_graphic_btn" id="six_distance_auto_icc_graphic_btn" onclick="processUserSelection(this)"> Six Distance (Automated) (CTRL + J)</button>-->
			  		
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="batsmanstats_icc_graphic_btn" id="batsmanstats_icc_graphic_btn" onclick="processUserSelection(this)"> Batsman Stats (F5) </button>
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlerstats_icc_graphic_btn" id="bowlerstats_icc_graphic_btn" onclick="processUserSelection(this)"> Bowler Stats (F9) </button>
			  <!--	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="plyerfreetext_icc_graphic_btn" id="plyerfreetext_icc_graphic_btn" onclick="processUserSelection(this)"> Player FREE TEXT w IMG (F8) </button> -->
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="parscore_icc_graphic_btn" id="parscore_icc_graphic_btn" onclick="processUserSelection(this)"> DLS Par Score (Alt M) </button>
			   	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="phaseBy_graphic_btn" id="phaseBy_graphic_btn" onclick="processUserSelection(this)"> PHASE SCORE </button>
			  		
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)"> Batting Style </button>
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)"> Bowling Style </button>
			  		
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="milestone_icc_graphic_btn" id="milestone_icc_graphic_btn" onclick="processUserSelection(this)"> Player Milestone w IMG (F7) </button> 
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="playerprofile_icc_graphic_btn" id="playerprofile_icc_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile Bat (F11) </button>
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="playerprofileball_icc_graphic_btn" id="playerprofileball_icc_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile Ball (F10) </button>
			  	<!-- <button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="ball_speed_icc_graphic_btn" id="ball_speed_icc_graphic_btn" onclick="processUserSelection(this)"> Last Ball Speed (S) </button> -->
			  	
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="weather_icc_graphic_btn" id="weather_icc_graphic_btn" onclick="processUserSelection(this)"> Weather (Q) </button>
			  				
			  	<!--<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="teamgroup_icc_graphic_btn" id="teamgroup_icc_graphic_btn" onclick="processUserSelection(this)"> GROUP TEAM  </button>
			  	
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="playerintro_icc_graphic_btn" id ="playerintro_icc_graphic_btn" onclick="processUserSelection(this)"> Player Intro </button> -->
			  	   
			    <!--<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="plyervideo_icc_graphic_btn" id="plyervideo_icc_graphic_btn" onclick="processUserSelection(this)"> player Video</button>					
			  	 
			  	 <button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="Wagon_icc_graphic_btn" id="Wagon_icc_graphic_btn" onclick="processUserSelection(this)"> Wagon Wheel (W) </button>
			     <button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="playerh2h_graphic_btn" id="playerh2h_graphic_btn" onclick="processUserSelection(this)"> Player Head To Head </button> -->
			  		
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="GroupPoints_icc_graphic_btn" id="GroupPoints_icc_graphic_btn" onclick="processUserSelection(this)"> Group Points Table </button>	
			  	<!--  <button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="points_icc_graphic_btn" id="points_icc_graphic_btn" onclick="processUserSelection(this)"> Points Table </button> -->
			  	<button style="background-color:#0000FF;color:#FEFEFE; margin:3px;" class="btn btn-sm" type="button"
			  		name="result_icc_graphic_btn" id="result_icc_graphic_btn" onclick="processUserSelection(this)"> Result </button>
			  	</div>

			  	</c:if>
			  	
  				
			  	<c:if test="${(session_selected_broadcaster == 'GPCL') || (session_selected_broadcaster == 'APL') || (session_selected_broadcaster == 'PUNJAB_T20')}">
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="animate_out_graphic_btn" id="animate_out_graphic_btn" onclick="processUserSelection(this)"> AnimateOut Infobar-Top (Ctrl+9)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="animateout_info-right_graphic_btn" id="animateout_info-right_graphic_btn" onclick="processUserSelection(this)"> AnimateOut Infobar-Right (Ctrl+0)</button>
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'NEPAL_T20')}">
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="animate_out_graphic_btn" id="animate_out_graphic_btn" onclick="processUserSelection(this)"> AnimateOut Infobar-Top (Ctrl+9)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="animateout_info-right_graphic_btn" id="animateout_info-right_graphic_btn" onclick="processUserSelection(this)"> AnimateOut Infobar-Right (Ctrl+0)</button>
			  	</c:if>	
			  	<c:if test="${(session_selected_broadcaster == 'EVEREST_BENGAL_T20')}">
			  		<h6>
			  		<br>
			  		FULL FRAMES:- CONTROL + SHIFT + X<br>
			  		LOWER THIRDS :- CONTROL + SHIFT + E<br>
			  		</h6>								
			  	</c:if>
			  	<c:if test="${(session_selected_broadcaster == 'BIG_SCREEN')}">
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="decision_graphic_btn" id="decision_graphic_btn" onclick="processUserSelection(this)"> Decision Pending </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="decision_out_not_graphic_btn" id="decision_out_not_graphic_btn" onclick="processUserSelection(this)"> Decision Out/NotOut </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchId_graphic_btn" id="matchId_graphic_btn" onclick="processUserSelection(this)"> Match Ident (M)</button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Info_graphic_btn" id="Info_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_bs_graphic_btn" id="target_bs_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparison_bs_graphic_btn" id="comparison_bs_graphic_btn" onclick="processUserSelection(this)"> Comparison (Ctrl+F3)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="projected_bs_score_graphic_btn" id="projected_bs_score_graphic_btn" onclick="processUserSelection(this)"> Projected (Alt+A)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="boundaries_bs_score_graphic_btn" id="boundaries_bs_score_graphic_btn" onclick="processUserSelection(this)"> Boundaries </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="free_bs_score_graphic_btn" id="free_bs_score_graphic_btn" onclick="processUserSelection(this)"> Free Text </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_bs_graphic_btn" id="equation_bs_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofile_bs_graphic_btn" id="playerprofile_bs_graphic_btn" onclick="processUserSelection(this)"> Player Profile Bat </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofileball_bs_graphic_btn" id="playerprofileball_bs_graphic_btn" onclick="processUserSelection(this)"> Player Profile Ball</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playermilestone_bs_graphic_btn" id="playermilestone_bs_graphic_btn" onclick="processUserSelection(this)"> MileStone</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="countdown_bs_graphic_btn" id="countdown_bs_graphic_btn" onclick="processUserSelection(this)"> CountDown</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_bs_graphic_btn" id="howout_bs_graphic_btn" onclick="processUserSelection(this)"> How Out (F6)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="quickhowout_bs_graphic_btn" id="quickhowout_bs_graphic_btn" onclick="processUserSelection(this)"> Quick HowOut (Ctrl+F6)</button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlerfig_bs_graphic_btn" id="bowlerfig_bs_graphic_btn" onclick="processUserSelection(this)"> Bowler Figure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="result_bs_graphic_btn" id="result_bs_graphic_btn" onclick="processUserSelection(this)"> Match Result</button>										
			  	</c:if>
	
			  	<c:if test="${(session_selected_broadcaster == 'DOAD_AR')}">
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="boundaries_ar_score_graphic_btn" id="boundaries_ar_score_graphic_btn" onclick="processUserSelection(this)"> Boundaries </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparison_ar_score_graphic_btn" id="comparison_ar_score_graphic_btn" onclick="processUserSelection(this)"> Comparison </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_ar_score_graphic_btn" id="equation_ar_score_graphic_btn" onclick="processUserSelection(this)"> Equation </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_ar_score_graphic_btn" id="target_ar_score_graphic_btn" onclick="processUserSelection(this)"> Target </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchId_ar_score_graphic_btn" id="matchId_ar_score_graphic_btn" onclick="processUserSelection(this)"> Match Ident </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="projected_ar_score_graphic_btn" id="projected_ar_score_graphic_btn" onclick="processUserSelection(this)"> Projected </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="freetext_ar_graphic_btn" id="freetext_ar_graphic_btn" onclick="processUserSelection(this)"> Free Text </button>	
			  							
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'T20_MUMBAI_AR')}" >
			  	
			  	<!-- start -->
			  	
			  	<!--   <hr style="margin:15px 0; border:1px solid #ccc;">
                <p style="font-weight:bold; color:#2E008B; margin-bottom:8px;">AR Graphic Buttons</p>
                 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchId_ar_score_graphic_btn" id="matchId_ar_score_graphic_btn" onclick="processUserSelection(this)"> Match Ident </button>	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="toss_ar_graphic_btn" id="toss_ar_graphic_btn" onclick="processUserSelection(this)"> Toss </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="positionlandmark_graphic_btn" id="positionlandmark_graphic_btn" onclick="processUserSelection(this)"> Batsman In AT</button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Next_ar_graphic_btn" id="Next_ar_graphic_btn" onclick="processUserSelection(this)"> Next TO Bat </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="tosscoin_ar_graphic_btn" id="tosscoin_ar_graphic_btn" onclick="processUserSelection(this)"> Toss Coin Flip </button> 	
			  	
			  	
			   	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_ar_score_graphic_btn" id="target_ar_score_graphic_btn" onclick="processUserSelection(this)"> Target </button> 
			  		
			  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="targetimage_ar_score_graphic_btn" id="targetimage_ar_score_graphic_btn" onclick="processUserSelection(this)"> Target Image </button>	
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="projected_ar_score_graphic_btn" id="projected_ar_score_graphic_btn" onclick="processUserSelection(this)"> Projected </button> 
			  	
			  	
			  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  	 	name="equation_vr_score_graphic_btn" id="equation_vr_score_graphic_btn" onclick="processUserSelection(this)"> Equation </button> 
			  	
			  	
			  	 <button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership </button>	
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparison_ar_score_graphic_btn" id="comparison_ar_score_graphic_btn" onclick="processUserSelection(this)"> Comparison </button> 
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Batmile_ar_graphic_btn" id="Batmile_ar_graphic_btn" onclick="processUserSelection(this)"> Bat MileStone </button>	
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Ballmile_ar_graphic_btn" id="Ballmile_ar_graphic_btn" onclick="processUserSelection(this)"> Ball MileStone </button>
			  		
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
					  		name="playerprofileeverest_graphic_btn" id="playerprofileeverest_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat</button>
			  		
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
					  		name="playerprofileballeverest_graphic_btn" id="playerprofileballeverest_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball</button> 
				
				 <button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
					  		name="doubleplayerprofileballeverest_graphic_btn" id="doubleplayerprofileballeverest_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Double</button> 	  				
			  	
			  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equationintargetimage_ar_score_graphic_btn" id="equationintargetimage_ar_score_graphic_btn" onclick="processUserSelection(this)"> Equation </button> -->
			  	
			  	
			  	
			  	
			  	
			  	<!--   <hr style="margin:15px 0; border:1px solid #ccc;">
                <p style="font-weight:bold; color:#2E008B; margin-bottom:8px;">VR Graphic Buttons</p>
                
                <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="boundaries_vr_score_graphic_btn" id="boundaries_vr_score_graphic_btn" onclick="processUserSelection(this)"> Boundaries </button>
                
                <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="projected_vr_score_graphic_btn" id="projected_vr_score_graphic_btn" onclick="processUserSelection(this)"> Projected </button>
			  	
                <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparison_vr_score_graphic_btn" id="comparison_vr_score_graphic_btn" onclick="processUserSelection(this)"> Comparison VR </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="freetext_ar_graphic_btn" id="freetext_ar_graphic_btn" onclick="processUserSelection(this)"> Free Text </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_vr_score_graphic_btn" id="target_vr_score_graphic_btn" onclick="processUserSelection(this)"> Target VR </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="thisover_vr_graphic_btn" id="thisover_vr_graphic_btn" onclick="processUserSelection(this)"> This Over </button>
			  			
			  	
			  		
			    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_ar_score_graphic_btn" id="equation_ar_score_graphic_btn" onclick="processUserSelection(this)"> Equation </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  	name="matchId_vr_score_graphic_btn" id="matchId_vr_score_graphic_btn" onclick="processUserSelection(this)"> Match Ident </button> -->
				 
			  		
			  	
			  <!-- <hr style="margin:15px 0; border:1px solid #ccc;">
                <p style="font-we ight:bold; color:#2E008B; margin-bottom:8px;">AR New Added Buttons</p>
                
                	
			  	  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equationimage_ar_score_graphic_btn" id="equationimage_ar_score_graphic_btn" onclick="processUserSelection(this)"> Equation Image </button>		
                
               <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="phase_graphic_btn" id="phase_graphic_btn" onclick="processUserSelection(this)"> Phase (Ctrl+H)</button>  
                
                 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="countdown_graphic_btn" id="countdown_graphic_btn" onclick="processUserSelection(this)"> Count Down </button> -->
			  
			 
	        	 <hr style="margin:15px 0; border:1px solid #ccc;">
                <p style="font-weight:bold; color:#2E008B; margin-bottom:8px;">VR Graphic Buttons</p>	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="toss_ar_graphic_btn" id="toss_ar_graphic_btn" onclick="processUserSelection(this)"> Toss </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="boundaries_ar_score_graphic_btn" id="boundaries_ar_score_graphic_btn" onclick="processUserSelection(this)"> Boundaries </button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparison_ar_score_graphic_btn" id="comparison_ar_score_graphic_btn" onclick="processUserSelection(this)"> Comparison </button> 
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_ar_score_graphic_btn" id="equation_ar_score_graphic_btn" onclick="processUserSelection(this)"> Equation </button>
		    	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket </button>
			  	
			  	
			  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lastwicket_ar_graphic_btn" id="lastwicket_ar_graphic_btn" onclick="processUserSelection(this)"> Last Wickets </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="previous_over_graphic_btn" id="previous_over_graphic_btn" onclick="processUserSelection(this)"> Previous Over </button>
			  	
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="projected_ar_score_graphic_btn" id="projected_ar_score_graphic_btn" onclick="processUserSelection(this)"> Projected </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bug_partnership_graphic_btn" id="bug_partnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership</button>
			       
			  	
			  		
			  		
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playercelebration_ar_graphic_btn" id="playercelebration_ar_graphic_btn" onclick="processUserSelection(this)"> Player Celebration </button> 
			  		
			  		
			  <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="freetext_ar_graphic_btn" id="freetext_ar_graphic_btn" onclick="processUserSelection(this)"> Free Text </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchId_vr_score_graphic_btn" id="matchId_vr_score_graphic_btn" onclick="processUserSelection(this)"> Match Ident </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="rate_vr_score_graphic_btn" id="rate_vr_score_graphic_btn" onclick="processUserSelection(this)"> Run rate </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="phase_graphic_btn" id="phase_graphic_btn" onclick="processUserSelection(this)"> Phase </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="LastXBalls_graphic_btn" id="LastXBalls_graphic_btn" onclick="processUserSelection(this)"> Last X Balls </button>				
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchId_animation_score_graphic_btn" id="matchId_animation_score_graphic_btn" onclick="processUserSelection(this)"> Match Ident Animation </button>
			  	
			  	
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchId_ar_score_graphic_btn" id="matchId_ar_score_graphic_btn" onclick="processUserSelection(this)"> Match Ident </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="runrate_ar_score_graphic_btn" id="runrate_ar_score_graphic_btn" onclick="processUserSelection(this)"> Run Rates</button> 
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="teamcelebration_ar_graphic_btn" id="teamcelebration_ar_graphic_btn" onclick="processUserSelection(this)"> Team Celebration </button>
			  				
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Batmile_ar_graphic_btn" id="Batmile_ar_graphic_btn" onclick="processUserSelection(this)"> Bat MileStone </button>	
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Ballmile_ar_graphic_btn" id="Ballmile_ar_graphic_btn" onclick="processUserSelection(this)"> Ball MileStone </button>	
			  				
			  	
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> FF Match Promo  </button>
		  		
		  		<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="match_promo_animation_graphic_btn" id="match_promo_animation_graphic_btn" onclick="processUserSelection(this)"> FF Match Promo Animation </button> 	
			  	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="count_ar_score_graphic_btn" id="count_ar_score_graphic_btn" onclick="processUserSelection(this)"> CountDown </button>	 -->
			  	 					
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'EVEREST_AR_VR')  || (session_selected_broadcaster == 'BARODA_AR')}" >
			  	
			  	 <hr style="margin:15px 0; border:1px solid #ccc;">
                <p style="font-weight:bold; color:#2E008B; margin-bottom:8px;">VR Graphic Buttons</p>
                
                <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="boundaries_vr_score_graphic_btn" id="boundaries_vr_score_graphic_btn" onclick="processUserSelection(this)"> Boundaries </button>
                
                <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="projected_vr_score_graphic_btn" id="projected_vr_score_graphic_btn" onclick="processUserSelection(this)"> Projected </button>
			  	
                <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparison_vr_score_graphic_btn" id="comparison_vr_score_graphic_btn" onclick="processUserSelection(this)"> Comparison VR </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="freetext_ar_graphic_btn" id="freetext_ar_graphic_btn" onclick="processUserSelection(this)"> Free Text </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_vr_score_graphic_btn" id="target_vr_score_graphic_btn" onclick="processUserSelection(this)"> Target VR </button>
			  	
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="thisover_vr_graphic_btn" id="thisover_vr_graphic_btn" onclick="processUserSelection(this)"> This Over </button> -->
			  			
			 <!--  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket </button> -->
			  		
			    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_ar_score_graphic_btn" id="equation_ar_score_graphic_btn" onclick="processUserSelection(this)"> Equation </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  	name="matchId_vr_score_graphic_btn" id="matchId_vr_score_graphic_btn" onclick="processUserSelection(this)"> Match Ident </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lastboundary_ar_score_graphic_btn" id="lastboundary_ar_score_graphic_btn" onclick="processUserSelection(this)"> Ball Since last Boundary </button>		
				 				
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'ICPL_AR')}">
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="default_ar_score_graphic_btn" id="default_ar_score_graphic_btn" onclick="processUserSelection(this)"> Default In </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="defaultout_ar_score_graphic_btn" id="defaultout_ar_score_graphic_btn" onclick="processUserSelection(this)"> Default Out </button>	
			  	
			  	
			  	<div style="margin-bottom:10px;">
 					   <h3 style="font-weight:bold; color:#2E008B; margin-bottom:12px;">
       						 Score Graphics Controls
  					  </h3>
				</div>
               	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="boundaries_ar_score_graphic_btn" id="boundaries_ar_score_graphic_btn" onclick="processUserSelection(this)"> Boundaries </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="projected_ar_score_graphic_btn" id="projected_ar_score_graphic_btn" onclick="processUserSelection(this)"> Projected </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="thisover_ar_graphic_btn" id="thisover_ar_graphic_btn" onclick="processUserSelection(this)"> This Over </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparison_ar_score_graphic_btn" id="comparison_ar_score_graphic_btn" onclick="processUserSelection(this)"> Comparison </button>	
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_ar_score_graphic_btn" id="target_ar_score_graphic_btn" onclick="processUserSelection(this)"> Target </button>
			  		
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="freetext_ar_graphic_btn" id="freetext_ar_graphic_btn" onclick="processUserSelection(this)"> Free Text </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_ar_score_graphic_btn" id="equation_ar_score_graphic_btn" onclick="processUserSelection(this)"> Equation </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lastthirtyball_ar_score_graphic_btn" id="lastthirtyball_ar_score_graphic_btn" onclick="processUserSelection(this)"> Last 30 Balls </button>		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lastboundary_ar_score_graphic_btn" id="lastboundary_ar_score_graphic_btn" onclick="processUserSelection(this)"> Ball Since last Boundary </button>		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="fow_ar_score_graphic_btn" id="fow_ar_score_graphic_btn" onclick="processUserSelection(this)"> FOW </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="res_ar_score_graphic_btn" id="res_ar_score_graphic_btn" onclick="processUserSelection(this)"> Result </button>		
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchId_ar_score_graphic_btn" id="matchId_ar_score_graphic_btn" onclick="processUserSelection(this)"> Match Ident </button>
			  	<div style="margin-bottom:10px;">
 					   <h3 style="font-weight:bold; color:#2E008B; margin-bottom:12px;">
       						Drone
  					  </h3>
				</div>	
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_drone_score_graphic_btn" id="equation_drone_score_graphic_btn" onclick="processUserSelection(this)"> Equation Drone </button>
			  	
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_drone_score_graphic_btn" id="target_drone_score_graphic_btn" onclick="processUserSelection(this)"> Target Drone </button>
			  		
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparison_drone_score_graphic_btn" id="comparison_drone_score_graphic_btn" onclick="processUserSelection(this)"> Comparison </button>
			  	    
			  	    	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lastthirtyball_dronefar_score_graphic_btn" id="lastthirtyball_dronefar_score_graphic_btn" onclick="processUserSelection(this)"> Last 30 Balls Far </button>
			  		
			  			<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lastthirtyball_dronenear_score_graphic_btn" id="lastthirtyball_dronenear_score_graphic_btn" onclick="processUserSelection(this)"> Last 30 Balls Near </button>
			  			
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'EVEREST_NEPAL_T20')}">
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_partnership_graphic_btn" id="bug_partnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="multi_partnership_graphic_btn" id="multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)"> NameSuper </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)"> NameSuper-Player </button>
			   <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)"> Worm </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="most_graphic_btn" id="most_graphic_btn" onclick="processUserSelection(this)"> Most Runs/Wickets in Teams </button> 
			  	</c:if>	
			  <c:if test="${session_selected_broadcaster == 'EVEREST_LEGENDS_90' || session_selected_broadcaster == 'SPL'}">
			    <!-- Bug Section -->
			     <h4>BUGS</h4>
			    <div class="bug-section">
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal </button> 
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bug_partnership_graphic_btn" id="bug_partnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="multi_partnership_graphic_btn" id="multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss </button>
			        
			         <c:if test="${session_selected_broadcaster != 'SPL'}">
			         <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="bugTarget_graphic_btn" id="bugTarget_graphic_btn" onclick="processUserSelection(this)"> BUG TARGET </button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="result_bug_graphic_btn" id="result_bug_graphic_btn" onclick="processUserSelection(this)"> BUG Result</button>
			        </c:if>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)"> NameSuper </button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)"> NameSuper-Player </button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="namesuper_graphic_singleline_btn" id="namesuper_graphic_singleline_btn" onclick="processUserSelection(this)"> NameSuper Single Line </button> 
			    </div>
				<c:if test="${session_selected_broadcaster != 'SPL'}">
				
			    <hr style="border: 1px solid #ccc;"/>
				<h4>LT</h4>
			    <!-- LT Section -->
			    <div class="lt-section">
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="ltmatchid_graphic_btn" id="ltmatchid_graphic_btn" onclick="processUserSelection(this)"> LT MatchID</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="ltmatch_promo_graphic_btn" id="ltmatch_promo_graphic_btn" onclick="processUserSelection(this)"> Lt Match Promos</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="bugLt_graphic_btn" id="bugLt_graphic_btn" onclick="processUserSelection(this)"> LT TARGET </button> 
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="result_lt_graphic_btn" id="result_lt_graphic_btn" onclick="processUserSelection(this)"> LT Result</button> 
			 <!--        <button style="background-color:#FFA500;color:#000000;" class="btn btn-sm mb-2" type="button" 
			            name="timeOut_lt_graphic_btn" id="timeOut_lt_graphic_btn" onclick="processUserSelection(this)"> LT TimeOut</button> 
			         <button style="background-color:#FFA500;color:#000000;" class="btn btn-sm mb-2" type="button" 
			            name="powerplay_lt_graphic_btn" id="powerplay_lt_graphic_btn" onclick="processUserSelection(this)"> LT PowerPlay</button>  -->
			    </div>
			
			    <hr style="border: 1px solid #ccc;"/>
			
			    <!-- FF Section -->
			    <h4>FF</h4>
			    <div class="ff-section">
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID </button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> Match Promo  </button>               
			        <!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button" 
			            name="FF_Excel_keyPlayer_graphic_btn" id="FF_Excel_keyPlayer_graphic_btn" onclick="processUserSelection(this)"> FF KEY PLAYER </button> 
			        <button style="background-color:#FFA500;color:#000000;" class="btn btn-sm mb-2" type="button" 
			            name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo </button> 
			        <button style="background-color:#FFA500;color:#000000;" class="btn btn-sm" type="button"
			  			name="tournamentrules_graphic_btn" id="tournamentrules_graphic_btn" onclick="processUserSelection(this)"> Tournament Rules </button>
			        <button style="background-color:#FFA500;color:#000000;" class="btn btn-sm" type="button"
			  			name="superover_graphic_btn" id="superover_graphic_btn" onclick="processUserSelection(this)"> Super Over Explainer </button> -->
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)">LeaderBoard</button>	
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="highestScore_graphic_btn" id="highestScore_graphic_btn" onclick="processUserSelection(this)">Highest Individual Score</button>	
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="BestFigures_graphic_btn" id="BestFigures_graphic_btn" onclick="processUserSelection(this)">Best Figures</button>	
			  	
			    </div>
				</c:if>
			
			</c:if>


			  	<c:if test="${(session_selected_broadcaster == 'EVEREST_PUNJAB_T20') || (session_selected_broadcaster == 'EVEREST_APL_T20') 
			  		|| (session_selected_broadcaster == 'EVEREST_MPL_T20') || (session_selected_broadcaster == 'EVEREST_PPL_T20') 
			  		|| (session_selected_broadcaster == 'EVEREST_KCL_T20')}">
			  		
			  	<div class = "mt-2">
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_partnership_graphic_btn" id="bug_partnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="multi_partnership_graphic_btn" id="multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="result_bug_graphic_btn" id="result_bug_graphic_btn" onclick="processUserSelection(this)"> BUG Result</button>
			            
			    <c:if test="${(session_selected_broadcaster == 'EVEREST_MPL_T20')}"> 
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> BUG TOSS</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="bug_target_graphic_btn" id="bug_target_graphic_btn" onclick="processUserSelection(this)"> BUG TARGET</button>
			        <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="Lt_Pointers_graphic_btn" id="Lt_Pointers_graphic_btn" onclick="processUserSelection(this)"> LT-Pointers</button>
			  			
			  		<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  			name="ltmatch_promo_graphic_btn" id="ltmatch_promo_graphic_btn" onclick="processUserSelection(this)"> Lt Match Promo</button>
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  				name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> Match Promo  </button>
		  				
		  			<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="phaseWise_Comp_graphic_btn" id="phaseWise_Comp_graphic_btn" onclick="processUserSelection(this)"> Phase Comp</button>
			  	</c:if>
			  	
			  	<!--<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm mb-2" type="button"
			            name="clip_graphic_btn" id="clip_graphic_btn" onclick="processUserSelection(this)"> Clip</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="FF_Play_graphic_btn" id="FF_Play_graphic_btn" onclick="processUserSelection(this)"> FF Contest</button>-->
			  		
			  	<c:if test="${(session_selected_broadcaster == 'EVEREST_PPL_T20')}"> 
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="tournament_batting_graphic_btn" id="tournament_batting_graphic_btn" onclick="processUserSelection(this)"> Tournament Batting</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="tournament_bowling_graphic_btn" id="tournament_bowling_graphic_btn" onclick="processUserSelection(this)"> Tournament Bowling</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="tournament_team_graphic_btn" id="tournament_team_graphic_btn" onclick="processUserSelection(this)"> Tournament Team1</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="team_tournament_graphic_btn" id="team_tournament_graphic_btn" onclick="processUserSelection(this)"> Tournament Team2</button>		
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'EVEREST_APL_T20')}"> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="split_db_graphic_btn" id="split_db_graphic_btn" onclick="processUserSelection(this)"> SPLIT </button>	
			  	</c:if>
			  	
			  	</div>
			  	<div class = "mt-2">
					<!-- <button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="namesuper_graphic_singleline_btn" id="namesuper_graphic_singleline_btn" onclick="processUserSelection(this)"> NameSuper Single Line(j) </button>-->  	
			  		<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)"> NameSuper</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)"> NameSuper-Player Home</button>
				  	
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="namesuper_player_away_graphic_btn" id="namesuper_player_away_graphic_btn" onclick="processUserSelection(this)"> NameSuper-Player Away</button>
				  	
				  	
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltmatchid_graphic_btn" id="ltmatchid_graphic_btn" onclick="processUserSelection(this)"> LT MatchID</button>
			  	</div>
			  	<div class = "mt-2">
			  		<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  			name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID </button>
			  		
			  		<c:if test="${(session_selected_broadcaster == 'EVEREST_APL_T20')}"> 
			  		
				  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
					  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>
				  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
					  	name="most_graphic_btn" id="most_graphic_btn" onclick="processUserSelection(this)"> Most Runs/Wickets in Teams(Z) </button>
					
					<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
					  	name="LT_Excel_graphic_btn" id="LT_Excel_graphic_btn" onclick="processUserSelection(this)"> LT EXCEL(Ctrl+Shift+E) </button>
					  	
					<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
					  	name="FF_Excel_graphic_btn" id="FF_Excel_graphic_btn" onclick="processUserSelection(this)"> FF EXCEL(Ctrl+Shift+X) </button>  	  	 
					  		
				  	</c:if>
				  	
				  	
					  <c:if test="${(session_selected_broadcaster == 'EVEREST_PUNJAB_T20')}"> 
			  			<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
				  			name="ffthis_series_stats_graphic_btn" id="ffthis_series_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series BAT (Shift+P)</button>
				  		<button style="background-color:#ffb6c1;color:#000000;;" class="btn btn-sm" type="button"
					  		name="ffthis_series_balls_stats_graphic_btn" id="ffthis_series_balls_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Ball (Shift+Q)</button>
					  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
					  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>
					  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
					  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button>
					  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
				  			name="captain_graphic_btn" id="captain_graphic_btn" onclick="processUserSelection(this)"> Captains </button>
				  		 <button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
				  			name="marquee_graphic_btn" id="marquee_graphic_btn" onclick="processUserSelection(this)"> Marquee </button>
			  		 </c:if>
			  		 
			  		 <button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  			name="rules_graphic_btn" id="rules_graphic_btn" onclick="processUserSelection(this)"> Rules </button>
					 <button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="matchsummaryexcel_graphic_btn" id="matchsummaryexcel_graphic_btn" onclick="processUserSelection(this)"> Match Summary Excel</button>
			  	
			  	</div>
			  	
			  		
			  	<!--<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="final_match_summary_graphic_btn" id="final_match_summary_graphic_btn" onclick="processUserSelection(this)"> Final Match Summary </button>
			  			
			  
				
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="last_year_standing_btn" id="last_year_standing_btn" onclick="processUserSelection(this)"> Last Year Standing </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="schedule_btn" id="schedule_btn" onclick="processUserSelection(this)"> Schedule </button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ff_rows_captains_btn" id="ff_rows_captains_btn" onclick="processUserSelection(this)"> FF Rows Captains </button> -->		  		
				<!--  <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile (Ctrl + D) </button> --> 		
			  	</c:if>
			  	<c:if test="${(session_selected_broadcaster == 'ASSAM')}">
			  	<div class="left">
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar </button>
				<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_top_graphic_btn" id="infobar_top_graphic_btn" onclick="processUserSelection(this)"> Infobar Top </button> -->
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-left_graphic_btn" id="infobar_bottom-left_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Left </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_bottom_graphic_btn" id="infobar_bottom_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right </button>
			    <button style="background-color:#2E008B;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard </button> 
			    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams </button>
			  <!--	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> PlayingXI </button>	
		 	   
		 	      <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_bb_graphic_btn" id="bug_bb_graphic_btn" onclick="processUserSelection(this)"> Bug-Batsman_Bowler </button> -->
		 	    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)"> LtHow Out </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)"> LtHow Out Without Fielder </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatsmanStats </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerStats </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)"> NameSuper </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)"> NameSuper-Player </button>
			  	
			<!--   	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID </button> --> 
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> Match Promo  </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ff_target_graphic_btn" id="ff_target_graphic_btn" onclick="processUserSelection(this)"> FF Target </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparision </button>	
			    
			 <!--  <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile </button> -->
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="l3playerprofilebukhatir_graphic_btn" id="l3playerprofilebukhatir_graphic_btn" onclick="processUserSelection(this)"> L3PlayerProfile </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team Summary </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)"> LtBatting Summary </button>
			  	
			  <!--  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split </button>-->
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)"> Batsman Style </button>
			    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)"> Bowler Style </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)"> Previous Match Summary </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="lt_tieid_double_graphic_btn" id="lt_tieid_double_graphic_btn" onclick="processUserSelection(this)"> Match ID Double  </button>
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)"> Points Table </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="generic_lt_graphic_btn" id="generic_lt_graphic_btn" onclick="processUserSelection(this)"> Generic_LT </button>
			   	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="most_runs_graphic_btn" id="most_runs_graphic_btn" onclick="processUserSelection(this)"> Top Runs Score </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="most_wickets_graphic_btn" id="most_wickets_graphic_btn" onclick="processUserSelection(this)"> Top Wickets </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="most_fours_graphic_btn" id="most_fours_graphic_btn" onclick="processUserSelection(this)"> Top Fours </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="most_sixes_graphic_btn" id="most_sixes_graphic_btn" onclick="processUserSelection(this)"> Top Sixes </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="highest_runs_graphic_btn" id="highest_runs_graphic_btn" onclick="processUserSelection(this)"> Highest Individual Score </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FF Partnership </button> 
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)"> Manhattan </button> 
			  		
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lt_thisover_graphic_btn" id="lt_thisover_graphic_btn" onclick="processUserSelection(this)"> Lt ThisOver </button> -->
			  </div>
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'ACC')}">
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="shrinkin_graphic_btn" id="shrinkin_graphic_btn" onclick="processUserSelection(this)"> TickerOut (PageDown)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="shrinkout_graphic_btn" id="shrinkout_graphic_btn" onclick="processUserSelection(this)"> TickerIn (PageUp)</button>
				
		  		<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
		  		name="powerplay_graphic_btn" id="powerplay_graphic_btn" onclick="processUserSelection(this)"> Infobar PowerPlay ([)</button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="ident_graphic_btn" id="ident_graphic_btn" onclick="processUserSelection(this)"> Infobar Ident (Ctrl+F12)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_ident_data" id="infobar_ident_data" onclick="processUserSelection(this)"> Ident Data (Shift+F12)</button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-left_graphic_btn" id="infobar_bottom-left_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Left (Alt+1)</button>
			  	
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Right (Alt+8) </button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_right_graphic_btn" id="infobar_right_graphic_btn" onclick="processUserSelection(this)">Infobar Bottom-Right (Alt+7) </button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="director_graphic_btn" id="director_graphic_btn" onclick="processUserSelection(this)"> Infobar Director (9)</button>
			  		
			  	<!--  <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="dls_graphic_btn" id="dls_graphic_btn" onclick="processUserSelection(this)"> DLS (A)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="dls_equation_graphic_btn" id="dls_equation_graphic_btn" onclick="processUserSelection(this)"> DLS EQUATION (Shift+A)</button>-->
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo (Alt+Shift+Z)</button>
			  	
			  	<!-- <button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="lt_lineup_graphic_btn" id="lt_lineup_graphic_btn" onclick="processUserSelection(this)"> Lt LineUp (Ctrl+Shift+O)</button>	
			  	 -->
			  	<!--  <button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="fixtures_graphic_btn" id="fixtures_graphic_btn" onclick="processUserSelection(this)"> Group Fixtures</button> 
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="team_fixtures_graphic_btn" id="team_fixtures_graphic_btn" onclick="processUserSelection(this)"> Team Fixtures (F3)</button>-->
			  	
			  	<!-- <button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="team_squad_graphic_btn" id="team_squad_graphic_btn" onclick="processUserSelection(this)"> Team Squad (Alt+Z)</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="inningbuilder_graphic_btn" id="inningbuilder_graphic_btn" onclick="processUserSelection(this)"> Inning Builder (Ctrl+I)</button>	
				  	 -->
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift+F2)</button>	
			  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button> 
			    <button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)">Previous Match Summary (Shift+F11)</button>

			  	 <button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID (Ctrl+Shift+M)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  			name="ltmatch_promo_graphic_btn" id="ltmatch_promo_graphic_btn" onclick="processUserSelection(this)"> Lt Match Promo (Ctrl+Shift+L)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> FF Match Promo (Ctrl+M)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> PlayingXI (Shift+T)</button>
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button>
			  		
			  				
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (Shift+F6)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
			    <button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
			  		
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Ctrl+Y)</button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss (Alt+P)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugpartnership_graphic_btn" id="bugpartnership_graphic_btn" onclick="processUserSelection(this)"> Bug-Partnership (Ctrl+K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="multi_partnership_graphic_btn" id="multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership (Shift+F4)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight (H)</button>
				  			
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F9)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
			  		
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button>

				<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileball_graphic_btn" id="l3playerprofileball_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Ball (F11)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileBat_graphic_btn" id="l3playerprofileBat_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Bat (F7)</button>
			  		
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="nextbat_graphic_btn" id="nextbat_graphic_btn" onclick="processUserSelection(this)">Next To Bat (Ctrl+Shift+B)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="lt_tieid_double_graphic_btn" id="lt_tieid_double_graphic_btn" onclick="processUserSelection(this)"> Match ID Double (Ctrl+Shift+D)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffthis_series_stats_graphic_btn" id="ffthis_series_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Stats Bat(Shift+P)</button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffthis_series_balls_stats_graphic_btn" id="ffthis_series_balls_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Ball Stats (Shift+Q)</button>
			  					
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats BAT(Ctrl+S)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_ball_graphic_btn" id="this_series_stats_ball_graphic_btn" onclick="processUserSelection(this)"> This Series Stats Ball (Ctrl+F)</button>	
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparison (Ctrl+F3)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)">Points Table (P)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff (Alt+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff (Alt+F2)</button>
			  	
			  	<!--<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Lt_Pointers_graphic_btn" id="Lt_Pointers_graphic_btn" onclick="processUserSelection(this)"> LT-Pointers (Shift+O)</button>
			  	  <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="FF_Pointers_graphic_btn" id="FF_Pointers_graphic_btn" onclick="processUserSelection(this)"> FF-Pointers</button>-->
			  		
			  		
			  	</c:if>
			  	
				<c:if test="${(session_selected_broadcaster == 'ICPL')}">
				
			  		<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="powerplay_graphic_btn" id="powerplay_graphic_btn" onclick="processUserSelection(this)"> Infobar PowerPlay ([)</button>
					<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="ident_graphic_btn" id="ident_graphic_btn" onclick="processUserSelection(this)"> Infobar Ident (Ctrl+F12)</button>
					<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
					<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_bottom-left_graphic_btn" id="infobar_bottom-left_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Left (Alt+1)</button>
				  	
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right (Alt+7)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_right_graphic_btn" id="infobar_right_graphic_btn" onclick="processUserSelection(this)"> Infobar Right (Alt+8)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="director_graphic_btn" id="director_graphic_btn" onclick="processUserSelection(this)"> Infobar Director (9)</button>
				  	
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift+F2)</button>	
			  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button> 
			    <button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)">Previous Match Summary (Shift+F11)</button>

			  	 <button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
			  	<!-- <button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID </button> -->
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> Match Promo (Ctrl+M)</button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> PlayingXI (Shift+T)</button>
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button>
			  		
			  				
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (shift+F6)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
			    <button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
			  		
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Ctrl+L)</button>	
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F9)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile (Ctrl+D) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="l3playerprofile_graphic_btn" id="l3playerprofile_graphic_btn" onclick="processUserSelection(this)"> L3PlayerProfile (F11) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats (Ctrl+S)</button>
			  		
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparison (Ctrl+F3)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)">Points Table (P)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff (Alt+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff (Alt+F2)</button>
			  		
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'LCT')}">
				
			  		<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="powerplay_graphic_btn" id="powerplay_graphic_btn" onclick="processUserSelection(this)"> Infobar PowerPlay ([)</button>
					<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="ident_graphic_btn" id="ident_graphic_btn" onclick="processUserSelection(this)"> Infobar Ident (Ctrl+F12)</button>
					<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
					<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_bottom-left_graphic_btn" id="infobar_bottom-left_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Left (Alt+1)</button>
				  	
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right (Alt+7)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_right_graphic_btn" id="infobar_right_graphic_btn" onclick="processUserSelection(this)"> Infobar Right (Alt+8)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="director_graphic_btn" id="director_graphic_btn" onclick="processUserSelection(this)"> Infobar Director (9)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="four_graphic_btn" id="four_graphic_btn" onclick="processUserSelection(this)"> Tournament Four (5)</button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="six_graphic_btn" id="six_graphic_btn" onclick="processUserSelection(this)"> Tournament Six (6)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift+F2)</button>	
			  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button> 
			    <button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2) </button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)">Previous Match Summary (Shift+F11)</button>

			  	 <button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
			  	<!-- <button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID </button> -->
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> Match Promo (Ctrl+M)</button>
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="lt_tieid_double_graphic_btn" id="lt_tieid_double_graphic_btn" onclick="processUserSelection(this)"> Match ID Double (Ctrl+Shift+D) </button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> PlayingXI (Shift+T)</button>
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo (Alt+Shift+Z)</button>
			  	<button style="background-color:#ffeb2b;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="schedule_graphic_btn" id="schedule_graphic_btn" onclick="processUserSelection(this)"> Schedule </button>	
			  				
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (Shift+F6)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
			    <button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
			  		
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Ctrl+Y)</button>	
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F9)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile (Ctrl+D)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="l3playerprofile_graphic_btn" id="l3playerprofile_graphic_btn" onclick="processUserSelection(this)"> L3PlayerProfile (F11)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats (Ctrl+S)</button>
			  		
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparison (Ctrl+F3)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)">Points Table (P)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff (Alt+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff (Alt+F2)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> Most (X)</button>	
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'GPCL') || (session_selected_broadcaster == 'APL') || (session_selected_broadcaster == 'PUNJAB_T20')}">
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="ident_graphic_btn" id="ident_graphic_btn" onclick="processUserSelection(this)"> Infobar Ident (Ctrl+F12) </button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_top_graphic_btn" id="infobar_top_graphic_btn" onclick="processUserSelection(this)"> Infobar Top (Ctrl+5)</button>	
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right (Alt+7)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_right_graphic_btn" id="infobar_right_graphic_btn" onclick="processUserSelection(this)"> Infobar Right (Alt+8)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="director_graphic_btn" id="director_graphic_btn" onclick="processUserSelection(this)"> Infobar Director (9)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_ident_data" id="infobar_ident_data" onclick="processUserSelection(this)"> Ident Data (Shift+F12)</button>
			  	
			  	</c:if>
			  	
			  	<!--<c:if test="${(session_selected_broadcaster == 'ACC_NEPAL')}">-->
			  	<!-- <div class="left" style = "margin-top: 5px; font-size: 21px;">
			  		Ticker and It's Change On's
				</div> -->
				
				<!--<div class="left" style = "margin-top: 5px; font-size: 21px;">-->
				  		
			  	<!-- <button style="background-color:#ffeb2b;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
				<button style="background-color:#ffeb2b;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-left_graphic_btn" id="infobar_bottom-left_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Left (Alt+5)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="infobar_bottom_graphic_btn" id="infobar_bottom_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom (Alt+6)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right (Alt+3)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="director_graphic_btn" id="director_graphic_btn" onclick="processUserSelection(this)"> Infobar Director (Alt+4)</button>
			  	 -->
				<!--</div>-->
				
			  	
			  	<!-- <div class="left" style = "margin-top: 5px; font-size: 21px;">
			  	
			  	<div class="left" style = "margin-top: 5px; font-size: 21px;">
			  		All Bugs
				</div>
				
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="drone_graphic_btn" id="drone_graphic_btn" onclick="processUserSelection(this)"> Drone Bug (`)</button> 
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (F)</button> 
		 	    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug Powerplay (Ctrl+L)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss (T)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_partnership_graphic_btn" id="bug_partnership_graphic_btn" onclick="processUserSelection(this)"> Bug-Partnership (Ctrl+K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight (H)</button>
			  			
				</div> -->
				
				
				<!-- <div class="left" style = "margin-top: 5px; font-size: 21px;">
				
				<div class="left" style = "margin-top: 5px; font-size: 21px;">
			  		All Full Frames
				</div>
				
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)"> Points Table (P)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)"> Previous Match Summary (Shift+F11)</button>
			  		
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="ff_target_graphic_btn" id="ff_target_graphic_btn" onclick="processUserSelection(this)"> FF Target (Shift+D)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="ff_equation_graphic_btn" id="ff_equation_graphic_btn" onclick="processUserSelection(this)"> FF Equation </button>	
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (X)</button>	
			  		
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> FF Match Promo (Ctrl+M)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> PlayingXI (Ctrl+F8)</button>
			  		
			  	<!-- <button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button> -->
			  	
			  	<!--<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="most_runs_graphic_btn" id="most_runs_graphic_btn" onclick="processUserSelection(this)"> Most Runs (Z)</button>
			  	 <button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="most_wickets_graphic_btn" id="most_wickets_graphic_btn" onclick="processUserSelection(this)"> Top Wickets (X)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="most_fours_graphic_btn" id="most_fours_graphic_btn" onclick="processUserSelection(this)"> Top Fours (C)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="most_sixes_graphic_btn" id="most_sixes_graphic_btn" onclick="processUserSelection(this)"> Top Sixes (V)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>	-->	
				<!--  </div> -->
				
				<!-- <div class="left" style = "margin-top: 5px; font-size: 21px;">
				
				<div class="left" style = "margin-top: 5px; font-size: 21px;">
			  		All LT's
				</div>
				
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="dls_graphic_btn" id="dls_graphic_btn" onclick="processUserSelection(this)"> DLS (A)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="dls_equation_graphic_btn" id="dls_equation_graphic_btn" onclick="processUserSelection(this)"> DLS EQUATION (Shift+A)</button>
			  		
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="four_graphic_btn" id="four_graphic_btn" onclick="processUserSelection(this)"> This Match Four (Ctrl+Q)</button> 
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="six_graphic_btn" id="six_graphic_btn" onclick="processUserSelection(this)"> This Match Six (Shift+Q)</button> 
			  	
			  	
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID (N)</button>
			  		
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
			  	
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
			    <button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>
			  			
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
			  		
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (Shift+F6)</button>
			  		
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (S)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparision (Ctrl+F3)</button>
			  	
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F9)</button>
			  	
			  	<!-- <button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="l3playerprofileball_graphic_btn" id="l3playerprofileball_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Ball (F11)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="l3playerprofileBat_graphic_btn" id="l3playerprofileBat_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Bat (F7)</button> -->
			  		
			  	<!--<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
			  		
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="generic_lt_graphic_btn" id="generic_lt_graphic_btn" onclick="processUserSelection(this)"> Generic_LT (Ctrl+Shift+Q)</button>
			  	
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats (Ctrl+S)</button>
			  		-->
				<!--  </div> -->
			  		
			  	<!--  <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="highest_runs_graphic_btn" id="highest_runs_graphic_btn" onclick="processUserSelection(this)"> Highest Individual Score </button> -->
			  	<!--</c:if>-->
			  	
			  	<c:if test="${(session_selected_broadcaster == 'FAIR_BREAK')}">
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="shrinkin_graphic_btn" id="shrinkin_graphic_btn" onclick="processUserSelection(this)"> Shrink TickerIn </button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="shrinkout_graphic_btn" id="shrinkout_graphic_btn" onclick="processUserSelection(this)"> Shrink TickerOut </button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="powerplay_graphic_btn" id="powerplay_graphic_btn" onclick="processUserSelection(this)"> Infobar PowerPlay ([) </button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="ident_graphic_btn" id="ident_graphic_btn" onclick="processUserSelection(this)"> Infobar Ident (Ctrl+F12) </button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12) </button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_top_graphic_btn" id="infobar_top_graphic_btn" onclick="processUserSelection(this)"> Infobar Left (Ctrl+6)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_middle_graphic_btn" id="infobar_middle_graphic_btn" onclick="processUserSelection(this)"> Infobar Middle (Alt+2)</button>		
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right (Alt+7)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_right_graphic_btn" id="infobar_right_graphic_btn" onclick="processUserSelection(this)"> Infobar Right (Alt+8)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_section5_btn" id="infobar_section5_btn" onclick="processUserSelection(this)"> Infobar Top (Ctrl+5)</button>	
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="director_graphic_btn" id="director_graphic_btn" onclick="processUserSelection(this)"> Infobar Director (9)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="bat_performer_graphic_btn" id="bat_performer_graphic_btn" onclick="processUserSelection(this)"> Bat Performer (Ctrl+Shift+F1)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="ball_performer_graphic_btn" id="ball_performer_graphic_btn" onclick="processUserSelection(this)"> Ball Performer (Ctrl+Shift+F2)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)"> Points Table (P)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)"> Previous Match Summary (Shift+F11)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> FF Match Promo (Ctrl+M) </button>
			  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button>			
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> TeamLineUp (Shift+T)</button>
			  <!--	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_photo_graphic_btn" id="playingxi_photo_graphic_btn" onclick="processUserSelection(this)"> PhotoLineUp </button>	
			  	  -->
			  	
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="lt_tieid_double_graphic_btn" id="lt_tieid_double_graphic_btn" onclick="processUserSelection(this)"> Match ID Double (Ctrl+Shift+D) </button>
		  			
		  		<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (k)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugpartnership_graphic_btn" id="bugpartnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership (Ctrl+K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Ctrl+Y)</button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_multi_partnership_graphic_btn" id="bug_multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership (Shift+F4)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight (H)</button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss (Alt+P)</button>
				  
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID (Ctrl+Shift+M)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  			name="ltmatch_promo_graphic_btn" id="ltmatch_promo_graphic_btn" onclick="processUserSelection(this)"> Lt Match Promo (Ctrl+Shift+L)</button>
			  		
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
			  		
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (Shift+F6)</button>
			  	
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
			    <button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>	
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileball_graphic_btn" id="l3playerprofileball_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Ball (F11)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileBat_graphic_btn" id="l3playerprofileBat_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Bat (F7)</button>
			  		
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
			  	
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F9)</button>
			  	
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="lt_manhattan_graphic_btn" id="lt_manhattan_graphic_btn" onclick="processUserSelection(this)">Lt Manhattan (Ctrl+Shift+F10)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="nextbat_graphic_btn" id="nextbat_graphic_btn" onclick="processUserSelection(this)">Next To Bat (Ctrl+Shift+B)</button>
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparision (Ctrl+F3)</button>		
			  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="partnershiplt_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Partnership (Shift+K)</button>
			  		
			  	 <button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="inning_summary_graphic_btn" id="inning_summary_graphic_btn" onclick="processUserSelection(this)">Inning Summary (Ctrl+Shift+I)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>

			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift+F2)</button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>	
				  		
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff (Alt+F1)</button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff (Alt+F2)</button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats (Ctrl+S)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="player_of_tournament_graphic_btn" id="player_of_tournament_graphic_btn" onclick="processUserSelection(this)"> Player Of the Tournament (R)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lof_batting_graphic_btn" id="lof_batting_graphic_btn" onclick="processUserSelection(this)"> LOF Batting (Alt+Shift+F1)</button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lof_bowling_graphic_btn" id="lof_bowling_graphic_btn" onclick="processUserSelection(this)"> LOF Bowling (Alt+Shift+F2)</button>		
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ltplayingxi_graphic_btn" id="ltplayingxi_graphic_btn" onclick="processUserSelection(this)"> LtPlayingXI (Ctrl+Shift+O)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="phase_graphic_btn" id="phase_graphic_btn" onclick="processUserSelection(this)"> Phase (Ctrl+H)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> LtPoints Table (Alt+F7)</button>		
			  	</c:if>
			  	
			  	
			  	<c:if test="${(session_selected_broadcaster == 'RPL')}">
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="shrinkin_graphic_btn" id="shrinkin_graphic_btn" onclick="processUserSelection(this)"> Shrink TickerIn (Page-Up)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="shrinkout_graphic_btn" id="shrinkout_graphic_btn" onclick="processUserSelection(this)"> Shrink TickerOut (Page-Down)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="powerplay_graphic_btn" id="powerplay_graphic_btn" onclick="processUserSelection(this)"> Infobar PowerPlay ([)</button> 
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="ident_graphic_btn" id="ident_graphic_btn" onclick="processUserSelection(this)"> Infobar Ident (Ctrl+F12)</button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_top_graphic_btn" id="infobar_top_graphic_btn" onclick="processUserSelection(this)"> Infobar Left (Ctrl+6)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_middle_graphic_btn" id="infobar_middle_graphic_btn" onclick="processUserSelection(this)"> Infobar Middle (Alt+2)</button>
			  				
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Top-Right (Alt+7)</button>
			  	<!-- <button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_right_graphic_btn" id="infobar_right_graphic_btn" onclick="processUserSelection(this)"> Infobar Right </button> -->
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_section5_btn" id="infobar_section5_btn" onclick="processUserSelection(this)"> Infobar Top (Ctrl+5)</button>	
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="director_graphic_btn" id="director_graphic_btn" onclick="processUserSelection(this)"> Infobar Director (9)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>	
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift+F2)</button>
			  		
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="bat_performer_graphic_btn" id="bat_performer_graphic_btn" onclick="processUserSelection(this)"> Bat Performer (Ctrl+Shift+F1)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="ball_performer_graphic_btn" id="ball_performer_graphic_btn" onclick="processUserSelection(this)"> Ball Performer (Ctrl+Shift+F2)</button>
			  		
			  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button>			
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> TeamLineUp (Shift+T)</button>
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo (Alt+Shift+Z)</button>
			  			
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID (Ctrl+Shift+M)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  			name="ltmatch_promo_graphic_btn" id="ltmatch_promo_graphic_btn" onclick="processUserSelection(this)"> Lt Match Promo (Ctrl+Shift+L)</button>
			  	
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
			  		
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (Shift+F6)</button>			
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
			    <button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F6)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
			  		
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileball_graphic_btn" id="l3playerprofileball_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Ball (F11)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileBat_graphic_btn" id="l3playerprofileBat_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Bat (F7)</button>
			  		
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ff_target_graphic_btn" id="ff_target_graphic_btn" onclick="processUserSelection(this)"> FF Target (Shift+D)</button> 
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparision (Ctrl+F3)</button>	
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> FF Match Promo (Ctrl+M)</button>
		  		<!-- <button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="all_match_graphic_btn" id="all_match_graphic_btn" onclick="processUserSelection(this)"> FF Match (M)</button>	-->
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="lt_tieid_double_graphic_btn" id="lt_tieid_double_graphic_btn" onclick="processUserSelection(this)"> Match ID Double (Ctrl+Shift+D)</button>
		  			
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Ctrl+Y)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss (Alt+P)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight (H)</button>
			  		
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
			  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>	
			  		
			  <button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>	 
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)"> Points Table (P)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)"> Previous Match Summary (Shift+F11)</button>
			  		
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats (Ctrl+S)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffthis_series_stats_graphic_btn" id="ffthis_series_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Stats (Shift+P)</button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>
				  		
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="four_graphic_btn" id="four_graphic_btn" onclick="processUserSelection(this)"> Tournament Four (5)</button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="six_graphic_btn" id="six_graphic_btn" onclick="processUserSelection(this)"> Tournament Six (6)</button> 
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="doubleId_graphic_btn" id="doubleId_graphic_btn" onclick="processUserSelection(this)"> Double ID Sept 3rd</button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> Mini-Points Table (Alt+F7)</button> 
				<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>
			  		
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="playoffs_graphic_btn" id="playoffs_graphic_btn" onclick="processUserSelection(this)">PlayOffs (Ctrl+Shift+K)</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff (Alt+F1)</button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff (Alt+F2)</button>
			  						
			  	<!-- 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugpartnership_graphic_btn" id="bugpartnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_multi_partnership_graphic_btn" id="bug_multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership</button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_photo_graphic_btn" id="playingxi_photo_graphic_btn" onclick="processUserSelection(this)"> PhotoLineUp </button>	
			  	
				 
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="lt_manhattan_graphic_btn" id="lt_manhattan_graphic_btn" onclick="processUserSelection(this)">Lt Manhattan</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="nextbat_graphic_btn" id="nextbat_graphic_btn" onclick="processUserSelection(this)">Next To Bat</button>
			  		
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="inning_summary_graphic_btn" id="inning_summary_graphic_btn" onclick="processUserSelection(this)">Inning Summary</button>
			  	 		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="player_of_tournament_graphic_btn" id="player_of_tournament_graphic_btn" onclick="processUserSelection(this)"> Player Of the Tournament </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lof_batting_graphic_btn" id="lof_batting_graphic_btn" onclick="processUserSelection(this)"> LOF Batting </button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lof_bowling_graphic_btn" id="lof_bowling_graphic_btn" onclick="processUserSelection(this)"> LOF Bowling </button>		
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ltplayingxi_graphic_btn" id="ltplayingxi_graphic_btn" onclick="processUserSelection(this)"> LtPlayingXI </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="phase_graphic_btn" id="phase_graphic_btn" onclick="processUserSelection(this)"> Phase </button>
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'USPL')}">
			  	
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_top_animate_out_btn" id="infobar_top_animate_out_btn" onclick="processUserSelection(this)"> Infobar Top Animate Out </button>
				<br><b><h4>	Ticker and It's Change On's</h4></b>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="shrinkin_graphic_btn" id="shrinkin_graphic_btn" onclick="processUserSelection(this)"> Shrink TickerIn (Page-Up)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="shrinkout_graphic_btn" id="shrinkout_graphic_btn" onclick="processUserSelection(this)"> Shrink TickerOut (Page-Down)</button>
			  		
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="powerplay_graphic_btn" id="powerplay_graphic_btn" onclick="processUserSelection(this)"> Infobar PowerPlay (Ctrl+1)</button>
			  	<!--  <button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowler_section_graphic_btn" id="bowler_section_graphic_btn" onclick="processUserSelection(this)"> Infobar Bowler Section</button>-->
			  		
			  	<div class="left" style = "margin-bottom: 10px">
			  		<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="ident_graphic_btn" id="ident_graphic_btn" onclick="processUserSelection(this)"> Infobar Ident (Ctrl+F12)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_ident_data" id="infobar_ident_data" onclick="processUserSelection(this)"> Ident Data (Shift+F12)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_bottom_graphic_btn" id="infobar_bottom_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom (Ctrl+7)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right (Alt+7)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_top_graphic_btn" id="infobar_top_graphic_btn" onclick="processUserSelection(this)"> Infobar Top (Ctrl+5)</button>
				  	
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_four_director_btn" id="infobar_four_director_btn" onclick="processUserSelection(this)"> Four (F)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_six_director_btn" id="infobar_six_director_btn" onclick="processUserSelection(this)"> Six (S)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_wickets_director_btn" id="infobar_wickets_director_btn" onclick="processUserSelection(this)"> Wicket (W)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_freehit_director_btn" id="infobar_freehit_director_btn" onclick="processUserSelection(this)"> Free-Hit (I)</button>
			  	</div>
			  	
			  	<div class="left" style = "margin-bottom: 10px">
			  	<b><h4>FullFrames</h4></b>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> FF Match Promo (Ctrl+M)</button>
			  			
			  		<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button>
				   	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="bat_performer_graphic_btn" id="bat_performer_graphic_btn" onclick="processUserSelection(this)"> Bat Performer (Ctrl+Shift+F1)</button>
			  			
			  		<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="ball_performer_graphic_btn" id="ball_performer_graphic_btn" onclick="processUserSelection(this)"> Ball Performer (Ctrl+Shift+F2)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)"> Points Table (P)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="ff_target_graphic_btn" id="ff_target_graphic_btn" onclick="processUserSelection(this)"> FF Target (Shift+D)</button> 	
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button>	
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> TeamLineUp (Shift+T)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>	 
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  			name="lt_tieid_double_graphic_btn" id="lt_tieid_double_graphic_btn" onclick="processUserSelection(this)"> Match ID Double (Ctrl+Shift+D)</button>
			  		<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="ffthis_series_stats_graphic_btn" id="ffthis_series_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Bats Stats (Shift+P)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="ffthis_series_balls_stats_graphic_btn" id="ffthis_series_balls_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Ball Stats (Shift+Q)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
					  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>
					  		
					<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)"> Previous Match Summary (Shift+F11)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
	
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>
			  	</div>
			  		
			  	
		  		<div class="left" style = "margin-bottom: 10px">
		  		<b><h4>LT's</h4></b>
		  			<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID (Ctrl+Shift+M)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="ltmatch_promo_graphic_btn" id="ltmatch_promo_graphic_btn" onclick="processUserSelection(this)"> Lt Match Promo (Ctrl+Shift+L)</button>
		  		
		  			<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
					<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (Shift+F6)</button>	
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparision (Ctrl+F3)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
				    <button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>
				  		
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U)</button>
				  		
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>	
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F6)</button>
				  		
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="generic_lt_graphic_btn" id="generic_lt_graphic_btn" onclick="processUserSelection(this)"> Generic_LT (Ctrl+Shift+Q)</button>
			  			
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="l3partnership_graphic_btn" id="l3partnership_graphic_btn" onclick="processUserSelection(this)"> LT Partnership (Alt+O)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="l3playerprofileball_graphic_btn" id="l3playerprofileball_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Ball (F11)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="l3playerprofileBat_graphic_btn" id="l3playerprofileBat_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Bat (F7)</button>
				  		
				  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Lt_Pointers_graphic_btn" id="Lt_Pointers_graphic_btn" onclick="processUserSelection(this)"> LT-Pointers (Shift+O)</button> -->
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
				  		name="nextbat_graphic_btn" id="nextbat_graphic_btn" onclick="processUserSelection(this)">Next To Bat (Ctrl+Shift+B)</button>
				  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  			name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats (Ctrl+S)</button>
		  		</div>
		  		
			  		
			  	<div class="left" style = "margin-bottom: 10px">
			  	<b><h4>Mini's</h4></b>
		  			<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift+F1)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift+F2)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> Mini-Points Table (Alt+F7)</button> 
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff (Alt+F1)</button>
					<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff (Alt+F2)</button>
			  	</div>
			  	
			  	<div class="left" style = "margin-bottom: 10px">
			  	<b><h4>Bugs</h4></b>
		  			<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (K)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
					  	name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Ctrl+Y)</button>
					<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss (Alt+P)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight (H)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bugpartnership_graphic_btn" id="bugpartnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership (Ctrl+K)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_multi_partnership_graphic_btn" id="bug_multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership (Shift+F4)</button>
			  	</div>
			  		
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="dls_equation_graphic_btn" id="dls_equation_graphic_btn" onclick="processUserSelection(this)"> DLS EQUATION</button>
				  		
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="four_graphic_btn" id="four_graphic_btn" onclick="processUserSelection(this)"> Tournament Four (Ctrl+Q)</button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="six_graphic_btn" id="six_graphic_btn" onclick="processUserSelection(this)"> Tournament Six (Shift+Q)</button> 
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> Mini-Points Table (Ctrl+P)</button> 
			  		
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="playoffs_graphic_btn" id="playoffs_graphic_btn" onclick="processUserSelection(this)">PlayOffs</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff </button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff </button>
			  						
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_photo_graphic_btn" id="playingxi_photo_graphic_btn" onclick="processUserSelection(this)"> PhotoLineUp </button>	
			  	
				 
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="lt_manhattan_graphic_btn" id="lt_manhattan_graphic_btn" onclick="processUserSelection(this)">Lt Manhattan</button>
			  		
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="inning_summary_graphic_btn" id="inning_summary_graphic_btn" onclick="processUserSelection(this)">Inning Summary</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm</button>
				  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="player_of_tournament_graphic_btn" id="player_of_tournament_graphic_btn" onclick="processUserSelection(this)"> Player Of the Tournament </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lof_batting_graphic_btn" id="lof_batting_graphic_btn" onclick="processUserSelection(this)"> LOF Batting </button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lof_bowling_graphic_btn" id="lof_bowling_graphic_btn" onclick="processUserSelection(this)"> LOF Bowling </button>		
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ltplayingxi_graphic_btn" id="ltplayingxi_graphic_btn" onclick="processUserSelection(this)"> LtPlayingXI </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="phase_graphic_btn" id="phase_graphic_btn" onclick="processUserSelection(this)"> Phase </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> LtPoints Table </button> -->		
			  	</c:if>
			  	
			  	
			  	<%-- <c:if test="${(session_selected_broadcaster == 'RSWS')}">
			  	
			  	<div class="left" style = "margin-bottom: 10px">
			  	<b><h4>InfoBar</h4></b>
				  	
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="powerplay_graphic_btn" id="powerplay_graphic_btn" onclick="processUserSelection(this)"> Infobar PowerPlay ([)</button> 
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="ident_graphic_btn" id="ident_graphic_btn" onclick="processUserSelection(this)"> Infobar Ident (Ctrl+F12)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_ident_data" id="infobar_ident_data" onclick="processUserSelection(this)"> Ident Data (Shift+F12)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_middle_graphic_btn" id="infobar_middle_graphic_btn" onclick="processUserSelection(this)"> Infobar Left (Ctrl+6)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_bottom_graphic_btn" id="infobar_bottom_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom (Ctrl+7)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right (Alt+7)</button>
				  		
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_four_director_btn" id="infobar_four_director_btn" onclick="processUserSelection(this)"> Four (F)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_six_director_btn" id="infobar_six_director_btn" onclick="processUserSelection(this)"> Six (S)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_wickets_director_btn" id="infobar_wickets_director_btn" onclick="processUserSelection(this)"> Wicket (W)</button>
				  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
				  		name="infobar_freehit_director_btn" id="infobar_freehit_director_btn" onclick="processUserSelection(this)"> Free-Hit (I)</button>
				  	
			  	</div>
			  	
			  	<!-- 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift + F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift + F2)</button>
			  		
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo (Shift+T)</button> -->
			  		
			  		
			  	<div class="left" style = "margin-bottom: 10px">
			  	<b><h4>FullFrames</h4></b>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
				  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button>
					<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bat_performer_graphic_btn" id="bat_performer_graphic_btn" onclick="processUserSelection(this)"> Bat Performer (Ctrl+Shift+F1)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ball_performer_graphic_btn" id="ball_performer_graphic_btn" onclick="processUserSelection(this)"> Ball Performer (Ctrl+Shift+F2)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)"> Previous Match Summary (Shift+F11)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)"> Points Table (P)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>	 
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ff_target_graphic_btn" id="ff_target_graphic_btn" onclick="processUserSelection(this)"> FF Target (Shift+D)</button> 	
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> FF Match Promo (Ctrl+M)</button>
			  			
			  		<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button>			
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> TeamLineUp (Shift+T)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
					  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>		
				  		
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
				  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>
				  	
			  	</div>
			  	
			  	<div class="left" style = "margin-bottom: 10px">
			  	<b><h4>LT</h4></b>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID (Ctrl+Shift+M)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  			name="ltmatch_promo_graphic_btn" id="ltmatch_promo_graphic_btn" onclick="processUserSelection(this)"> Lt Match Promo (Ctrl+Shift+L)</button>
				  			
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
				  		
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (Shift+F6)</button>			
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
				    <button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>
				  		
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>	
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F9)</button>
				  	<!-- <button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="dls_equation_graphic_btn" id="dls_equation_graphic_btn" onclick="processUserSelection(this)"> DLS EQUATION</button> -->
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="l3partnership_graphic_btn" id="l3partnership_graphic_btn" onclick="processUserSelection(this)"> LT Partnership (Alt+O)</button>
				  		
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="l3playerprofileball_graphic_btn" id="l3playerprofileball_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Ball (F11)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="l3playerprofileBat_graphic_btn" id="l3playerprofileBat_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Bat (F7)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparision (Ctrl+F3)</button>	
				  	
				    <button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
				  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
				  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
				  	
			  	</div>
			  	
			  	<div class="left" style = "margin-bottom: 10px">
			  	<b><h4>Bugs</h4></b>
				  	
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button>
				   	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>	
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (K)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss (ALt+P)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
					  	name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Ctrl+Y)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight (H)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bugpartnership_graphic_btn" id="bugpartnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership (Ctrl+K)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_multi_partnership_graphic_btn" id="bug_multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership (Shift+F4)</button>
				  	
			  	</div>
	
			  	<!-- <button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="lt_tieid_double_graphic_btn" id="lt_tieid_double_graphic_btn" onclick="processUserSelection(this)"> Match ID Double (Shift+M)</button>
		  			
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
		
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats (Ctrl+S)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffthis_series_stats_graphic_btn" id="ffthis_series_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Stats (Ctrl+G)</button>	
			  			
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="four_graphic_btn" id="four_graphic_btn" onclick="processUserSelection(this)"> Tournament Four (Ctrl+Q)</button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="six_graphic_btn" id="six_graphic_btn" onclick="processUserSelection(this)"> Tournament Six (Shift+Q)</button> 
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="doubleId_graphic_btn" id="doubleId_graphic_btn" onclick="processUserSelection(this)"> Double ID Sept 3rd</button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> Mini-Points Table (Ctrl+P)</button> 
			  		
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="playoffs_graphic_btn" id="playoffs_graphic_btn" onclick="processUserSelection(this)">PlayOffs</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff </button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff </button> 
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_photo_graphic_btn" id="playingxi_photo_graphic_btn" onclick="processUserSelection(this)"> PhotoLineUp </button>	
			  	
				 
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="lt_manhattan_graphic_btn" id="lt_manhattan_graphic_btn" onclick="processUserSelection(this)">Lt Manhattan</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="nextbat_graphic_btn" id="nextbat_graphic_btn" onclick="processUserSelection(this)">Next To Bat</button>
			  		
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="inning_summary_graphic_btn" id="inning_summary_graphic_btn" onclick="processUserSelection(this)">Inning Summary</button>
			  			
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="player_of_tournament_graphic_btn" id="player_of_tournament_graphic_btn" onclick="processUserSelection(this)"> Player Of the Tournament </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lof_batting_graphic_btn" id="lof_batting_graphic_btn" onclick="processUserSelection(this)"> LOF Batting </button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lof_bowling_graphic_btn" id="lof_bowling_graphic_btn" onclick="processUserSelection(this)"> LOF Bowling </button>		
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ltplayingxi_graphic_btn" id="ltplayingxi_graphic_btn" onclick="processUserSelection(this)"> LtPlayingXI </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="phase_graphic_btn" id="phase_graphic_btn" onclick="processUserSelection(this)"> Phase </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> LtPoints Table </button> -->		
			  	</c:if>
			  	 --%>
			  	<c:if test="${(session_selected_broadcaster == 'NEPAL_T20')}">
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="powerplay_graphic_btn" id="powerplay_graphic_btn" onclick="processUserSelection(this)"> Infobar PowerPlay ([)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="ident_graphic_btn" id="ident_graphic_btn" onclick="processUserSelection(this)"> Infobar Ident (Ctrl+F12)</button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_top_graphic_btn" id="infobar_top_graphic_btn" onclick="processUserSelection(this)"> Infobar Top (Ctrl+5)</button>	
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right (Alt+7)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="infobar_right_graphic_btn" id="infobar_right_graphic_btn" onclick="processUserSelection(this)"> Infobar Right (Alt+8)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="director_graphic_btn" id="director_graphic_btn" onclick="processUserSelection(this)"> Infobar Director (9)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="bat_performer_graphic_btn" id="bat_performer_graphic_btn" onclick="processUserSelection(this)"> Bat Performer (Ctrl+Shift+F1)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="ball_performer_graphic_btn" id="ball_performer_graphic_btn" onclick="processUserSelection(this)"> Ball Performer (Ctrl+Shift+F2)</button>
			  	</c:if>
			  	</div>
			  	
			  	<div class="left" style = "margin-top: 10px;">
			  	<c:if test="${(session_selected_broadcaster == 'KERALA_T20')}">
			  	<button style="background-color:#ffeb2b;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="animateoutfreehit_graphic_btn" id="animateoutfreehit_graphic_btn" onclick="processUserSelection(this)"> AnimateOut FREEHIT </button>
			  	<button style="background-color:#ffeb2b;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="animateout_section5_btn" id="animateout_section5_btn" onclick="processUserSelection(this)"> AnimateOut Full Section </button>	
			  	
			  	WATERMARK ON-OFF <input type="checkbox" id="watermarkOnOrOff" name="watermarkOnOrOff" value="true" onclick="processUserSelection(this)" checked>		
			  	</c:if>
			  	<c:if test="${(session_selected_broadcaster == 'DOAD_LLC')}">
			  	
			  	<br>
					SPEED ON-OFF <input type="checkbox" id="speedOnOrOff" name="speedOnOrOff" value="true" onclick="processUserSelection(this)" checked>
					AUDIO ON-OFF <input type="checkbox" id="audioOnOrOff" name="audioOnOrOff" value="true" onclick="processUserSelection(this)" checked>
			  	</c:if>
			  	</div>
  				
  				
  				<div class="left">
  				<c:if test="${(session_selected_broadcaster == 'APL') || (session_selected_broadcaster == 'PUNJAB_T20')}">
  				
  				<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="bat_performer_graphic_btn" id="bat_performer_graphic_btn" onclick="processUserSelection(this)"> Bat Performer (Ctrl+Shift+F1)</button>
			  	<button style="background-color:#ffeb2b;color:#000000;" class="btn btn-sm" type="button"
			  		name="ball_performer_graphic_btn" id="ball_performer_graphic_btn" onclick="processUserSelection(this)"> Ball Performer (Ctrl+Shift+F2)</button>
  				
			  	<button style="background-color:#6a33f7;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button> 
			    <button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)">Previous Match Summary (Shift+F11)</button>

			  	 <button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID (Control+Shift+M)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  			name="ltmatch_promo_graphic_btn" id="ltmatch_promo_graphic_btn" onclick="processUserSelection(this)"> Lt Match Promo (Control+Shift+L)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
			  	<button style="background-color:#ffe2db;color:#000000;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> FF Match Promo (Ctrl+M)</button>
			  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> PlayingXI (Shift+T)</button>
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button>
			  	<!-- <button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="captains_graphic_btn" id="captains_graphic_btn" onclick="processUserSelection(this)"> Captains</button>
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="top_performer_graphic_btn" id="top_performer_graphic_btn" onclick="processUserSelection(this)"> Top Performer</button> -->
  				</c:if>
			  	</div>
			  	
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_MPL_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'BUKHATIR')}">
  				<c:if test="${(session_selected_broadcaster != 'APL')}">
  				<c:if test="${(session_selected_broadcaster != 'PUNJAB_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'THAILAND')}">
  				<c:if test="${(session_selected_broadcaster != 'ASSAM')}">
  				<c:if test="${(session_selected_broadcaster != 'DOAD-VIZ-MULTI')}">
  				<c:if test="${(session_selected_broadcaster != 'EVEREST_NEPAL_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'EVEREST_LEGENDS_90')}">
  				<c:if test="${(session_selected_broadcaster != 'SPL')}">
  				<c:if test="${(session_selected_broadcaster != 'EVEREST_PUNJAB_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'EVEREST_APL_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'EVEREST_PPL_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'EVEREST_KCL_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'BIG_SCREEN')}">
  				<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_SCORING')}">
  				<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_VIZ_SCORING')}">
  				<c:if test="${(session_selected_broadcaster != 'ICC_BIG_SCREEN')}">
  				<c:if test="${(session_selected_broadcaster != 'DOAD_AR')}">
  				<c:if test="${(session_selected_broadcaster != ' ')}">
  				<c:if test="${(session_selected_broadcaster != 'MAHARAJA_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'PLOTTER')}">
  				<c:if test="${(session_selected_broadcaster != 'ACC')}">
  				<c:if test="${(session_selected_broadcaster != 'ICPL')}">
  				<c:if test="${(session_selected_broadcaster != 'ICPL_AR')}">
  				<c:if test="${(session_selected_broadcaster != 'LCT')}">
  				<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK')}">
  				<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK_AR')}">
  				<c:if test="${(session_selected_broadcaster != 'T20_MUMBAI_AR')}">
  				<c:if test="${(session_selected_broadcaster != 'BARODA_AR')}">
  				<c:if test="${(session_selected_broadcaster != 'ACC_NEPAL')}">
  				<c:if test="${(session_selected_broadcaster != 'ICC_CWCU19')}">
  				<c:if test="${(session_selected_broadcaster != 'RPL')}">
  				<c:if test="${(session_selected_broadcaster != 'RSWS')}">
  				<c:if test="${(session_selected_broadcaster != 'USPL')}">
  				<c:if test="${(session_selected_broadcaster != 'MPL')}">
  				<c:if test="${(session_selected_broadcaster != 'PPL')}">
  				<c:if test="${(session_selected_broadcaster != 'KERALA_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'EUROPE_LEAGUE')}">
  				<c:if test="${(session_selected_broadcaster != 'EVEREST_BENGAL_T20')}">
  				<c:if test="${(session_selected_broadcaster != 'ARUNACHAL')}">
  				<c:if test="${(session_selected_broadcaster != 'DOAD_LLC')}">
  				<c:if test="${(session_selected_broadcaster != 'EVEREST_AR_VR')}">
  				<div class="left" style = "margin-top: 5px; font-size: 21px;">
			  	Mini's
				</div>
				
  				<div class="left" style = "margin-top: 10px;">
  				<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift+F2)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> Mini Points Table (Alt+F7)</button>
			  	</div>
			  	
			  	<div class="left" style = "margin-top: 5px; font-size: 21px;">
			  	FullFrames
				</div>
				
			  	<div class="left" style = "margin-top: 10px;">	
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bat_performer_graphic_btn" id="bat_performer_graphic_btn" onclick="processUserSelection(this)"> Bat Performer (Ctrl+Shift+F1)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="ball_performer_graphic_btn" id="ball_performer_graphic_btn" onclick="processUserSelection(this)"> Ball Performer (Ctrl+Shift+F2)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button> 
			    <button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)">Previous Match Summary (Shift+F11)</button>

			  	 <button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
			  	<!-- <button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3SeasonProfile_graphic_btn" id="l3SeasonProfile_graphic_btn" onclick="processUserSelection(this)">Season Profile</button> 
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3BowlerSpeed_graphic_btn" id="l3BowlerSpeed_graphic_btn" onclick="processUserSelection(this)">Bowler Speed</button> -->
			  	
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> Match Promo (Ctrl+M)</button>	
			  	 <!-- <button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> PlayingXI With ChangeOn </button> -->
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> PlayingXI </button> 
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="playingxi_sub5_graphic_btn" id="playingxi_sub5_graphic_btn" onclick="processUserSelection(this)"> PlayingXI (Alt+8)</button>-->
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7)</button> 
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="ff_target_graphic_btn" id="ff_target_graphic_btn" onclick="processUserSelection(this)"> FF Target (Shift+D)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>
			  	<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
				  	name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>
		  		<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px" class="btn btn-sm" type="button"
	  				name="ffthis_series_stats_graphic_btn" id="ffthis_series_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Stats (Shift+P)</button>
	  			<button style="background-color:#6a33f7;color:#FEFEFE;margin:3px" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)"> Points Table (P)</button>		
			  	</div>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
  				</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	<div class="left">
			  	
				  	<c:if test="${(session_selected_broadcaster == 'GPCL') || (session_selected_broadcaster == 'APL') || (session_selected_broadcaster == 'PUNJAB_T20')}">
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo (Alt+Shift+Z)</button>
			  	</c:if>
			  	
			  	<%-- <c:if test="${(session_selected_broadcaster == 'FAIR_BREAK')}">
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo (Alt+Shift+Z)</button>
			  	</c:if> --%>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'NEPAL_T20')}">
			  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo (Alt+Shift+Z)</button>
			  	</c:if>
			  	</div>
			  	
			  	<div class="left">
			  	<c:if test="${(session_selected_broadcaster == 'APL') || (session_selected_broadcaster == 'PUNJAB_T20')}">
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (Shift+F6)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
			    <button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  	<button style="background-color:#ff6347;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift+F1) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift+F2) </button>	
			  	</c:if>
			  	</div>
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_MPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'BUKHATIR')}">
			  	<c:if test="${(session_selected_broadcaster != 'APL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'THAILAND')}">
			  	<c:if test="${(session_selected_broadcaster != 'ASSAM')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD-VIZ-MULTI')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_NEPAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_LEGENDS_90')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_APL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_KCL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'SPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_VIZ_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD_AR')}">
			  	<c:if test="${(session_selected_broadcaster != ' ')}">
			  	<c:if test="${(session_selected_broadcaster != 'MAHARAJA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'PLOTTER')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'LCT')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'T20_MUMBAI_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'BARODA_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC_NEPAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_BENGAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_CWCU19')}">
			  	<c:if test="${(session_selected_broadcaster != 'RPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'RSWS')}">
			  	<c:if test="${(session_selected_broadcaster != 'USPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'MPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'KERALA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EUROPE_LEAGUE')}">
			  	<c:if test="${(session_selected_broadcaster != 'ARUNACHAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD_LLC')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_AR_VR')}">
			  	
			  	<div class="left" style = "margin-top: 5px; font-size: 21px;">
			  	ALL LT's
				</div>
				
			  	<div class="left" style = "margin-top: 10px;">
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="ltplayingxi_graphic_btn" id="ltplayingxi_graphic_btn" onclick="processUserSelection(this)"> LtPlayingXI (Ctrl+Shift+O)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID (Ctrl+Shift+M)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)">How Out (F6)</button>
			  	<!--  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_both_graphic_btn" id="howout_both_graphic_btn" onclick="processUserSelection(this)"> LtHow Out Both </button>	
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="l3match_promo_graphic_btn" id="l3match_promo_graphic_btn" onclick="processUserSelection(this)"> L3Match Promo </button> -->
			  	<!-- <button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="nextbat_graphic_btn" id="nextbat_graphic_btn" onclick="processUserSelection(this)">Next To Bat</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="lt_lineup_graphic_btn" id="lt_lineup_graphic_btn" onclick="processUserSelection(this)"> Lt LineUp (Ctrl+Shift+O)</button>  -->	
			  	<!-- <button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ballsince_graphic_btn" id="ballsince_graphic_btn" onclick="processUserSelection(this)">Ball Since</button> -->
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)">How Out Quick (Ctrl+F6)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)">How Out W/O Fielder (Shift+F6)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)">Batsman Style (Ctrl+F5)</button>
			    <button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)">Bowler Style (Ctrl+F9)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F9)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
			  		
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="l3playerprofileball_graphic_btn" id="l3playerprofileball_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Ball (F11)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="l3playerprofileBat_graphic_btn" id="l3playerprofileBat_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile Bat (F7)</button>	
			  	
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button>
			  	  
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparison (Ctrl+F3)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U)</button>
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
					name="field_plotter_icc_graphic_btn" id="field_plotter_icc_graphic_btn" onclick="processUserSelection(this)"> Plotter </button>
				<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> LT This Series Stats (Ctrl+S)</button>
			  	<!--<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="partnershiplt_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Partnership (ALT+O)</button>	-->
				<!-- <button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3BowlerSpeed_graphic_btn" id="l3BowlerSpeed_graphic_btn" onclick="processUserSelection(this)">Bowler Speed</button> 
			  	<button style="background-color:#bcb88a;color:#000000;margin:3px;" class="btn btn-sm" type="button"
			  		name="this_partnership_graphic_btn" id="this_partnership_graphic_btn" onclick="processUserSelection(this)"> LT Current Partnership (AlT+O)</button>-->
			  	<!--<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="dls_equation_graphic_btn" id="dls_equation_graphic_btn" onclick="processUserSelection(this)"> DLS EQUATION (Alt+D)</button>		
				 <button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3BowlerSpeed_graphic_btn" id="l3BowlerSpeed_graphic_btn" onclick="processUserSelection(this)">Bowler Speed</button> -->	
				<c:if test="${(session_selected_broadcaster == 'NEPAL_T20')}">
			  		<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff (Alt+F1)</button>
				  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
				  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff (Alt+F2)</button>
				  	<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
				  		name="inningbuilder_graphic_btn" id="inningbuilder_graphic_btn" onclick="processUserSelection(this)"> Inning Builder </button>
				  			
			  		<button style="background-color:#bcb88a;color:#000000;" class="btn btn-sm" type="button"
			  			name="landmark_graphic_btn" id="landmark_graphic_btn" onclick="processUserSelection(this)"> MileStone </button>
			  	</c:if>
			  			
			  	</div>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	<div class="left">
			  	
			  	<c:if test="${(session_selected_broadcaster == 'APL') || (session_selected_broadcaster == 'PUNJAB_T20')}">
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugpartnership_graphic_btn" id="bugpartnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership (Ctrl+K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Control_y)</button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="multi_partnership_graphic_btn" id="multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership (Shift+F4)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight (H)</button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss (Alt+P)</button>	
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)">Batsman-0s,1s,2s (Shift+F5)</button>
			  	<button style="background-color:#bbb477;color:#000000;" class="btn btn-sm" type="button"
			  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)">Bowler-0s,1s,2s (Shift+F9)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)">NameSuper-DB (F10)</button>
			  	<button style="background-color:#ffd700;color:#000000;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)">NameSuper-Player (F8)</button>
			  		
			  	 <button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileBat_graphic_btn" id="l3playerprofileBat_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile BAT (F7)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileball_graphic_btn" id="l3playerprofileball_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile BOWL (F11)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats BAT (Ctrl+S)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_ball_graphic_btn" id="this_series_stats_ball_graphic_btn" onclick="processUserSelection(this)"> This Series Stats Ball (Ctrl+F)</button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="nextbat_graphic_btn" id="nextbat_graphic_btn" onclick="processUserSelection(this)">Next To Bat (Control+Shift+B)</button>
			<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffthis_series_stats_graphic_btn" id="ffthis_series_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Stats </button> 
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="pointer_graphic_btn" id="pointer_graphic_btn" onclick="processUserSelection(this)"> Pointer </button>	-->	
			  	</c:if>
			  	</div>
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_MPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'BUKHATIR')}">
			  	<c:if test="${(session_selected_broadcaster != 'APL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'THAILAND')}">
			  	<c:if test="${(session_selected_broadcaster != 'ASSAM')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD-VIZ-MULTI')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_NEPAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_LEGENDS_90')}">
			  	<c:if test="${(session_selected_broadcaster != 'SPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_APL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_KCL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_VIZ_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'MAHARAJA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'PLOTTER')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_BENGAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'LCT')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'T20_MUMBAI_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'BARODA_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC_NEPAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_CWCU19')}">
			  	<c:if test="${(session_selected_broadcaster != 'RPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'RSWS')}">
			  	<c:if test="${(session_selected_broadcaster != 'USPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'MPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'KERALA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EUROPE_LEAGUE')}">
			  	<c:if test="${(session_selected_broadcaster != 'ARUNACHAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD_LLC')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_AR_VR')}">
			  	<div class="left" style = "margin-top: 5px; font-size: 21px;">
			  	Bat Speed
				</div>
				
			  	<div class="left" style = "margin-top: 10px;">
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bat_power_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)">Bat Power</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bat_speed_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)">Bat Speed</button>
			  	</div>
			
			  	<div class="left" style = "margin-top: 5px; font-size: 21px;">
			  	ALL Bugs
				</div>
				
			  	<div class="left" style = "margin-top: 10px;">
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bugpartnership_graphic_btn" id="bugpartnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership (Ctrl+K)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
				  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Ctrl+Y)</button>
				<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="multi_partnership_graphic_btn" id="multi_partnership_graphic_btn" onclick="processUserSelection(this)"> Multi Partnership (Shift+F4)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bug_highlight_graphic_btn" id="bug_highlight_graphic_btn" onclick="processUserSelection(this)"> Bug Highlight (H)</button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;margin:3px;" class="btn btn-sm" type="button"
			  		name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss (Alt+P)</button>	
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats </button> -->
			  	</div>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
				</c:if>	
				</c:if>
				</c:if>
				</c:if>
				</c:if>
				</c:if>
				</c:if>
				<div class="left">
			  	<c:if test="${(session_selected_broadcaster == 'APL') || (session_selected_broadcaster == 'PUNJAB_T20')}">
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="lt_tieid_double_graphic_btn" id="lt_tieid_double_graphic_btn" onclick="processUserSelection(this)"> Match ID Double (Ctrl+Shift+D)</button>	
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff </button> -->
			  	</c:if>
			  	</div>
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_MPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'BUKHATIR')}">
			  	<c:if test="${(session_selected_broadcaster != 'APL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'THAILAND')}">
			  	<c:if test="${(session_selected_broadcaster != 'ASSAM')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD-VIZ-MULTI')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_NEPAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_LEGENDS_90')}">
			  	<c:if test="${(session_selected_broadcaster != 'SPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_APL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_KCL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD_LLC')}">
			  	<c:if test="${(session_selected_broadcaster != 'KERALA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EUROPE_LEAGUE')}">
			  	<c:if test="${(session_selected_broadcaster != 'BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_VIZ_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD_AR')}">
			  	<c:if test="${(session_selected_broadcaster != ' ')}">
			  	<c:if test="${(session_selected_broadcaster != 'MAHARAJA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'PLOTTER')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'LCT')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'T20_MUMBAI_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'BARODA_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC_NEPAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_CWCU19')}">
			  	<c:if test="${(session_selected_broadcaster != 'RPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'RSWS')}">
			  	<c:if test="${(session_selected_broadcaster != 'USPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'NEPAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'MPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_BENGAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'ARUNACHAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_AR_VR')}">
			  	<div class="left" style = "margin-top: 10px;">
			  	
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Bat (Ctrl+D)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="playerprofileball_graphic_btn" id="playerprofileball_graphic_btn" onclick="processUserSelection(this)">PlayerProfile Ball (Ctrl+E)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileBat_graphic_btn" id="l3playerprofileBat_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile BAT (F7)</button>
			  	<button style="background-color:#ffb6c1;color:#000000;" class="btn btn-sm" type="button"
			  		name="l3playerprofileball_graphic_btn" id="l3playerprofileball_graphic_btn" onclick="processUserSelection(this)">L3PlayerProfile BOWL (F11)</button>
			  		
			  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-battingcard_graphic_btn" id="mini-battingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Batting-Card (Shift+F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="mini-bowlingcard_graphic_btn" id="mini-bowlingcard_graphic_btn" onclick="processUserSelection(this)"> Mini Bowling-Card (Shift+F2)</button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats (Ctrl+S)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffthis_series_stats_graphic_btn" id="ffthis_series_stats_graphic_btn" onclick="processUserSelection(this)"> FF-This Series Stats (Shift+P)</button>
				  <!--	<button style="background-color:#ffeb2b;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="schedule_graphic_btn" id="schedule_graphic_btn" onclick="processUserSelection(this)"> Schedule </button> 
			  		-->
			  		
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff (Alt+F1)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff ()</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="inningbuilder_graphic_btn" id="inningbuilder_graphic_btn" onclick="processUserSelection(this)"> Inning Builder (Ctrl+I)</button>	
				</c:if>
				</c:if>
				</c:if>
				</c:if>
				</c:if>
				</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	<div class="left">
			  	
			  	<c:if test="${(session_selected_broadcaster == 'APL') || (session_selected_broadcaster == 'PUNJAB_T20')}">
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3)</button>
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D)</button> 
			  	<button style="background-color:#2f4f4f;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparison (Ctrl+F3)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12)</button>
			  	<button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)">Points Table (P)</button>
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> LtPoints Table (Alt+F7) </button>-->	
			  	</c:if>
			  	</div>
			  	
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_MPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'BUKHATIR')}">
			  	<c:if test="${(session_selected_broadcaster != 'APL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'THAILAND')}">
			  	<c:if test="${(session_selected_broadcaster != 'ASSAM')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD-VIZ-MULTI')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_NEPAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_LEGENDS_90')}">
			  	<c:if test="${(session_selected_broadcaster != 'SPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_APL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_KCL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_VIZ_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD_AR')}">
			  	<c:if test="${(session_selected_broadcaster != ' ')}">
			  	<c:if test="${(session_selected_broadcaster != 'MAHARAJA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'PLOTTER')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'LCT')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'T20_MUMBAI_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'BARODA_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC_NEPAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_CWCU19')}">
			  	<c:if test="${(session_selected_broadcaster != 'RPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'RSWS')}">
			  	<c:if test="${(session_selected_broadcaster != 'USPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'MPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_BENGAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'KERALA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EUROPE_LEAGUE')}">
			  	<c:if test="${(session_selected_broadcaster != 'ARUNACHAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_AR_VR')}">
			  	<div class="left">
			  	<!-- <button style="background-color:#32cd32;color:#000000;" class="btn btn-sm" type="button"
			  		name="generic_lt_graphic_btn" id="generic_lt_graphic_btn" onclick="processUserSelection(this)">Generic_LT</button> -->
			  		
			  	<!-- <button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)">Points Table</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="Griff_graphic_btn" id="Griff_graphic_btn" onclick="processUserSelection(this)"> Bat Griff </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="BallGriff_graphic_btn" id="BallGriff_graphic_btn" onclick="processUserSelection(this)"> Ball Griff </button>
				<button style="background-color:#ffeb2b;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ff-stats_graphic_btn" id="ff-stats_graphic_btn" onclick="processUserSelection(this)"> FF-Stats </button>  --> 			
			  	</div>
			  	
			  	<div class="left">
			  	
				<!-- <button style="background-color:#c76739;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)">Bug BatsmanScore</button> 
		 	    <button style="background-color:#c76739;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)">Bug-Dismissal </button>
			  	<button style="background-color:#c76739;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)">Bug BowlerFigure</button>
			  	<button style="background-color:#c76739;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)">Bug DB</button>
			  	
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="playoffs_graphic_btn" id="playoffs_graphic_btn" onclick="processUserSelection(this)">PlayOffs</button> -->
			  		
			  	</div>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	<div class="left">
				
				<c:if test="${(session_selected_broadcaster == 'APL') || (session_selected_broadcaster == 'PUNJAB_T20')}">
			  	 <button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>	
			  	</c:if>
			  	</div>
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_MPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'BUKHATIR')}">
			  	<c:if test="${(session_selected_broadcaster != 'APL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'THAILAND')}">
			  	<c:if test="${(session_selected_broadcaster != 'ASSAM')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD-VIZ-MULTI')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_NEPAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_LEGENDS_90')}">
			  	<c:if test="${(session_selected_broadcaster != 'SPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PUNJAB_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_APL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_PPL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_KCL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIGSCREEN_DOAD_VIZ_SCORING')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_BIG_SCREEN')}">
			  	<c:if test="${(session_selected_broadcaster != 'DOAD_AR')}">
			  	<c:if test="${(session_selected_broadcaster != ' ')}">
			  	<c:if test="${(session_selected_broadcaster != 'MAHARAJA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'PLOTTER')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICPL_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'LCT')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK')}">
			  	<c:if test="${(session_selected_broadcaster != 'FAIR_BREAK_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'T20_MUMBAI_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'BARODA_AR')}">
			  	<c:if test="${(session_selected_broadcaster != 'ACC_NEPAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'ICC_CWCU19')}">
			  	<c:if test="${(session_selected_broadcaster != 'RPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'RSWS')}">
			  	<c:if test="${(session_selected_broadcaster != 'USPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'MPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'PPL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_BENGAL_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'KERALA_T20')}">
			  	<c:if test="${(session_selected_broadcaster != 'EUROPE_LEAGUE')}">
			  	<c:if test="${(session_selected_broadcaster != 'ARUNACHAL')}">
			  	<c:if test="${(session_selected_broadcaster != 'EVEREST_AR_VR')}">
			  	<div class="left">
			  	 <!-- <button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="inning_summary_graphic_btn" id="inning_summary_graphic_btn" onclick="processUserSelection(this)">Inning Summary</button> -->
			  	<!--  <button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="lt_manhattan_graphic_btn" id="lt_manhattan_graphic_btn" onclick="processUserSelection(this)">Lt Manhattan</button>-->
			  		
			  	

			  </div>
			    </c:if>
			    </c:if>
			    </c:if>
			    </c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
			  	</c:if>
				<div class="left">
				
			  	<c:if test="${(session_selected_broadcaster == 'ACC')}">
			  		<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>
			  	<!--  <button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="richeis_graphic_btn" id="richeis_graphic_btn" onclick="processUserSelection(this)">Richeis (Alt+F5)</button> -->
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>
				<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="most_graphic_btn" id="most_graphic_btn" onclick="processUserSelection(this)"> Most Runs/Wickets in Teams </button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="most_leader_graphic_btn" id="most_leader_graphic_btn" onclick="processUserSelection(this)"> Most Runs/Wickets in Teams Leaderboard </button>
				
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="phase_graphic_btn" id="phase_graphic_btn" onclick="processUserSelection(this)"> Phase By Score (Ctrl+H)</button> -->
				 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="positionlandmark_graphic_btn" id="positionlandmark_graphic_btn" onclick="processUserSelection(this)"> Batsman In AT (Ctrl+B)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> LtPoints Table (Alt+F7)</button>
				  	
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'ICPL')}">
			  		<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
			  	 <!-- <button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="inning_summary_graphic_btn" id="inning_summary_graphic_btn" onclick="processUserSelection(this)">Inning Summary</button> -->
			  	<!--<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="lt_manhattan_graphic_btn" id="lt_manhattan_graphic_btn" onclick="processUserSelection(this)">Lt Manhattan</button> -->
			  		
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (X)</button>
				  		
			  		<!--<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="squad_graphic_btn" id="squad_graphic_btn" onclick="processUserSelection(this)"> Squad </button>
				  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay</button> -->

				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="positionlandmark_graphic_btn" id="positionlandmark_graphic_btn" onclick="processUserSelection(this)"> Batsman In AT (Ctrl+B)</button>
				  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> LtPoints Table (Ctrl+P)</button>
				  	
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'LCT')}">
			  		<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)">Manhattan (Ctrl+F10)</button>
			  	 <!-- <button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="inning_summary_graphic_btn" id="inning_summary_graphic_btn" onclick="processUserSelection(this)">Inning Summary (Ctrl+Shift+I)</button> -->
			  	<!--<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="lt_manhattan_graphic_btn" id="lt_manhattan_graphic_btn" onclick="processUserSelection(this)">Lt Manhattan (Ctrl+Shift+F10)</button> -->
			  		
			  	<button style="background-color:#40e0d0;color:#000000;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)">Worm (Shift+F10)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard (Shift+L)</button>
				  		
		  		<!--<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="squad_graphic_btn" id="squad_graphic_btn" onclick="processUserSelection(this)"> Squad (Alt+Z)</button>
			  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug PowerPlay (Ctrl+Y)</button> -->

			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="positionlandmark_graphic_btn" id="positionlandmark_graphic_btn" onclick="processUserSelection(this)"> Batsman In AT (Ctrl+B)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ltpointstable_graphic_btn" id="ltpointstable_graphic_btn" onclick="processUserSelection(this)"> LtPoints Table (Alt+F7)</button>
				  	
			  	</c:if>

			  	<c:if test="${(session_selected_broadcaster == 'NEPAL_T20')}">
			  		<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="positionlandmark_graphic_btn" id="positionlandmark_graphic_btn" onclick="processUserSelection(this)"> Batsman In AT (Ctrl+B)</button> -->
				  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  			name="positionlandmark_graphic_btn" id="positionlandmark_graphic_btn" onclick="processUserSelection(this)"> FFLandMark </button> -->
			  			
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="landmark_graphic_btn" id="landmark_graphic_btn" onclick="processUserSelection(this)"> MileStone </button>
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugpartnership_graphic_btn" id="bugpartnership_graphic_btn" onclick="processUserSelection(this)"> Bug Partnership (Ctrl+Y)</button>
			  	</c:if>
			  </div>
  				
  				
				<div class="left">
				
				
			  	<c:if test="${(session_selected_broadcaster == 'BUKHATIR' || session_selected_broadcaster == 'THAILAND' || session_selected_broadcaster == 'ARUNACHALA')}">
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar(F12) </button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-left_graphic_btn" id="infobar_bottom-left_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Left(Alt+1) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_bottom_graphic_btn" id="infobar_bottom_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom(Ctrl+7) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_bottom-right_graphic_btn" id="infobar_bottom-right_graphic_btn" onclick="processUserSelection(this)"> Infobar Bottom-Right(Alt+7) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="director_graphic_btn" id="director_graphic_btn" onclick="processUserSelection(this)"> Infobar Director (9)</button>
			  	
			  	<c:if test="${(session_match.setup.matchType == 'TEST')}">
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="sponsor_graphic_btn" id="sponsor_graphic_btn" onclick="processUserSelection(this)"> Sponsor (])</button>
			  	</c:if>
			  	
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_sponsor_in" id="infobar_sponsor_in" onclick="processUserSelection(this)"> Infobar Sponsor-In </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_sponsor_out" id="infobar_sponsor_out" onclick="processUserSelection(this)"> Infobar Sponsor-Out </button> -->
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="directorIN_graphic_btn" id="directorIN_graphic_btn" onclick="processUserSelection(this)"> Infobar SponsorIN </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="directorOUT_graphic_btn" id="directorOUT_graphic_btn" onclick="processUserSelection(this)"> Infobar SponsorOut </button> -->
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_toss_graphic_btn" id="bug_toss_graphic_btn" onclick="processUserSelection(this)"> Bug-Toss (Alt+P)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_partnership_graphic_btn" id="bug_partnership_graphic_btn" onclick="processUserSelection(this)"> Bug-Partnership (Ctrl+k) </button>
				</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'DOAD-VIZ-MULTI')}">
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar (F12)</button>
			  	<button style="background-color:#6a33f7;color:#000000;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2)</button>
			  	<button style="background-color:#6a33f7;color:#000000;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11)</button>
			  	<button style="background-color:#e0ffff;color:#000000;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4)</button>
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (G)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
				</c:if>
				
			  	
			  	<c:if test="${(session_selected_broadcaster == 'BUKHATIR' || session_selected_broadcaster == 'THAILAND' || session_selected_broadcaster == 'ARUNACHALA')}">
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchid_graphic_btn" id="matchid_graphic_btn" onclick="processUserSelection(this)"> MatchID (M)</button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="batsmanstats_graphic_btn" id="batsmanstats_graphic_btn" onclick="processUserSelection(this)"> BatThisMatch (F5)</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlerstats_graphic_btn" id="bowlerstats_graphic_btn" onclick="processUserSelection(this)"> BowlerThisMatch (F9)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="teams_logo_graphic_btn" id="teams_logo_graphic_btn" onclick="processUserSelection(this)"> Teams Logo (Alt+Shift+Z)</button>
			  	<!--<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="dls_graphic_btn" id="dls_graphic_btn" onclick="processUserSelection(this)"> DLS (A)</button> -->
			  		
			  	<c:if test="${(session_match.setup.matchType == 'TEST')}">
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchstatus_graphic_btn" id="matchstatus_graphic_btn" onclick="processUserSelection(this)"> Match Status </button>
			  	</c:if>
			  <!--	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchstatus_graphic_btn" id="matchstatus_graphic_btn" onclick="processUserSelection(this)"> Match Status </button> -->
			  	</c:if>
			  	
			  	
			  	
			  	<c:if test="${(session_selected_broadcaster == 'BUKHATIR' || session_selected_broadcaster == 'ARUNACHALA')}">
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ff_target_graphic_btn" id="ff_target_graphic_btn" onclick="processUserSelection(this)"> FF Target (Shift+D) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ff_equation_graphic_btn" id="ff_equation_graphic_btn" onclick="processUserSelection(this)"> FF Equation (Ctrl_F11) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="l3playerprofilebukhatir_graphic_btn" id="l3playerprofilebukhatir_graphic_btn" onclick="processUserSelection(this)"> L3PlayerProfile (F7) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofilebukhatir_graphic_btn" id="playerprofilebukhatir_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile (Ctrl+D)</button> 
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster == 'THAILAND')}">
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="four_graphic_btn" id="four_graphic_btn" onclick="processUserSelection(this)"> Tournament Four (5) </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="six_graphic_btn" id="six_graphic_btn" onclick="processUserSelection(this)"> Tournament Six (6) </button> 
			  <!--	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ff_target_graphic_btn" id="ff_target_graphic_btn" onclick="processUserSelection(this)"> FF Target </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ff_equation_graphic_btn" id="ff_equation_graphic_btn" onclick="processUserSelection(this)"> FF Equation </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofilebukhatir_graphic_btn" id="playerprofilebukhatir_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="l3playerprofilebukhatir_graphic_btn" id="l3playerprofilebukhatir_graphic_btn" onclick="processUserSelection(this)"> L3PlayerProfile </button> -->
			  	
			  	</c:if>
			  	
			  	<c:if test="${(session_selected_broadcaster != 'DOAD-VIZ-MULTI')}">
			  	<c:if test="${(session_selected_broadcaster == 'BUKHATIR' || session_selected_broadcaster == 'THAILAND' || session_selected_broadcaster == 'ARUNACHALA')}">
			  	<button style="background-color:#2E008B;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard (F1)</button> 
			    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard (F2) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary (Ctrl+F11) </button> 
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams (Ctrl+F7) </button>
			  		
			  	
				  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="equation_graphic_btn" id="equation_graphic_btn" onclick="processUserSelection(this)"> Equation (E) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="fallofwicket_graphic_btn" id="fallofwicket_graphic_btn" onclick="processUserSelection(this)"> FallOfWicket (Shift+F3) </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="projected_score_graphic_btn" id="projected_score_graphic_btn" onclick="processUserSelection(this)"> Projected Score (Ctrl+A) </button>	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="comparision_graphic_btn" id="comparision_graphic_btn" onclick="processUserSelection(this)"> Comparison (Ctrl+F3) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="teamsummary_graphic_btn" id="teamsummary_graphic_btn" onclick="processUserSelection(this)"> Team-0s,1s,2s (Alt+F12) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playersummary_graphic_btn" id="playersummary_graphic_btn" onclick="processUserSelection(this)"> Batsman-0s,1s,2s (Shift+F5) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlersummary_graphic_btn" id="bowlersummary_graphic_btn" onclick="processUserSelection(this)"> Bowler-0s,1s,2s (Shift+F9) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="split_graphic_btn" id="split_graphic_btn" onclick="processUserSelection(this)"> 30-50-Split (U) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="batsmanstyle_graphic_btn" id="batsmanstyle_graphic_btn" onclick="processUserSelection(this)"> Batsman Style (Ctrl+F5) </button>
			    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlerstyle_graphic_btn" id="bowlerstyle_graphic_btn" onclick="processUserSelection(this)"> Bowler Style (Ctrl+F9) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="previous_summary_graphic_btn" id="previous_summary_graphic_btn" onclick="processUserSelection(this)"> Previous Match Summary (Shift+F11) </button>
		 
			  <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="lt_tieid_double_graphic_btn" id="lt_tieid_double_graphic_btn" onclick="processUserSelection(this)"> Match ID Double (Ctrl+Shift+D)</button>
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="generic_lt_graphic_btn" id="generic_lt_graphic_btn" onclick="processUserSelection(this)"> Generic_LT (Ctrl+Shift+Q) </button>
			  		 
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ltpartnership_graphic_btn" id="ltpartnership_graphic_btn" onclick="processUserSelection(this)"> Current Partnership (Shift+K)</button>
			  		
			  <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="ffpartnership_graphic_btn" id="ffpartnership_graphic_btn" onclick="processUserSelection(this)"> FFPartnership (F4) </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playingxi_graphic_btn" id="playingxi_graphic_btn" onclick="processUserSelection(this)"> PlayingXI (Shift+T) </button>	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug BatsmanScore (Y)</button> 
		 	    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_dismissal_graphic_btn" id="bug_dismissal_graphic_btn" onclick="processUserSelection(this)"> Bug-Dismissal (Shift+O) </button> 
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bugbowler_graphic_btn" id="bugbowler_graphic_btn" onclick="processUserSelection(this)"> Bug BowlerFigure (g)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_db_graphic_btn" id="bug_db_graphic_btn" onclick="processUserSelection(this)"> Bug DataBase (k)</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_powerplay_graphic_btn" id="bug_powerplay_graphic_btn" onclick="processUserSelection(this)"> Bug Powerplay (Ctrl+Y)</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)"> LtHow Out (F6) </button>
			  		
			  	<c:if test="${(session_match.setup.matchType == 'TEST')}">
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_both_graphic_btn" id="howout_both_graphic_btn" onclick="processUserSelection(this)"> LtHow Out Both (Alt+F6)</button>
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="batsmanstats_both_graphic_btn" id="batsmanstats_both_graphic_btn" onclick="processUserSelection(this)"> BatsmanStats Both (Shift+A)</button>
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_session_graphic_btn" id="this_session_graphic_btn" onclick="processUserSelection(this)"> This Session (Shift+U)</button>
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="session_graphic_btn" id="session_graphic_btn" onclick="processUserSelection(this)"> Session (Ctrl+J)</button> 
			  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="l3match_promo_graphic_btn" id="l3match_promo_graphic_btn" onclick="processUserSelection(this)"> L3Match Promo (Ctrl+Shift+L)</button>
			  	</c:if>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="quick_howout_graphic_btn" id="quick_howout_graphic_btn" onclick="processUserSelection(this)"> Lt HowOut Quick (Ctrl+F6) </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howoutwithoutfielder_graphic_btn" id="howoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)"> LtHow Out Without Fielder (Shift+F6) </button> 
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="quickhowoutwithoutfielder_graphic_btn" id="quickhowoutwithoutfielder_graphic_btn" onclick="processUserSelection(this)"> Lt Quick HowOut Without Fielder </button>-->
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="namesuper_graphic_btn" id="namesuper_graphic_btn" onclick="processUserSelection(this)"> NameSuper-DB (F10) </button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="namesuper_player_graphic_btn" id="namesuper_player_graphic_btn" onclick="processUserSelection(this)"> NameSuper-Player (F8) </button>
			  		
			 	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="l3matchid_graphic_btn" id="l3matchid_graphic_btn" onclick="processUserSelection(this)"> L3MatchID (Ctrl+Shift+M)</button> --> 
			  	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="match_promo_graphic_btn" id="match_promo_graphic_btn" onclick="processUserSelection(this)"> Match Promo (Ctrl+M)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="target_graphic_btn" id="target_graphic_btn" onclick="processUserSelection(this)"> Target (D) </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="pointstable_graphic_btn" id="pointstable_graphic_btn" onclick="processUserSelection(this)"> Points Table (P)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="manhattan_graphic_btn" id="manhattan_graphic_btn" onclick="processUserSelection(this)"> Manhattan (Ctrl+F10)</button>
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="worm_graphic_btn" id="worm_graphic_btn" onclick="processUserSelection(this)"> Worm (Shift+F10) </button> --> 
			  	</c:if>
			  	</c:if>
			  	
			  		
			  	<c:if test="${(session_selected_broadcaster == 'BUKHATIR' || session_selected_broadcaster == 'THAILAND' || session_selected_broadcaster == 'ARUNACHALA')}">
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
				  		name="leaderboard_graphic_btn" id="leaderboard_graphic_btn" onclick="processUserSelection(this)"> LeaderBoard </button> -->
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="most_runs_graphic_btn" id="most_runs_graphic_btn" onclick="processUserSelection(this)"> Most Runs (Z)</button>
			  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="most_wickets_graphic_btn" id="most_wickets_graphic_btn" onclick="processUserSelection(this)"> Top Wickets (X)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="this_series_stats_graphic_btn" id="this_series_stats_graphic_btn" onclick="processUserSelection(this)"> This Series Stats (Ctrl+S)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="most_fours_graphic_btn" id="most_fours_graphic_btn" onclick="processUserSelection(this)"> Top Fours (C)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="most_sixes_graphic_btn" id="most_sixes_graphic_btn" onclick="processUserSelection(this)"> Top Sixes (V)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="highest_runs_graphic_btn" id="highest_runs_graphic_btn" onclick="processUserSelection(this)"> Highest Individual Score (Ctrl+Z)</button>
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="winner_graphic_btn" id="winner_graphic_btn" onclick="processUserSelection(this)"> Winner</button>
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="lt_thisover_graphic_btn" id="lt_thisover_graphic_btn" onclick="processUserSelection(this)"> Lt ThisOver </button> -->
			  	</c:if>
			  
			  	
			  </div>
			  </div>
			  </div>
	       </div>
	    </div>
       </div>
    </div>
  </div>
<!-- <input type="hidden" id="which_inning" name="which_inning" value="${which_inning}"/> -->
<input type="hidden" name="selected_broadcaster" id="selected_broadcaster" value="${session_selected_broadcaster}"/>
<input type="hidden" id="which_keypress" name="which_keypress" value="${which_keypress}"/>
<input type="hidden" name="selected_second_broadcaster" id="selected_second_broadcaster" value="${session_selected_second_broadcaster}"/>
<input type="hidden" name="selected_which_layer" id="selected_which_layer" value="${selected_layer}"/>
<input type="hidden" name="selected_which_scene" id="selected_which_scene" value="${selected_scene}"/>
<input type="hidden" name="selected_match_max_overs" id="selected_match_max_overs" value="${session_match.setup.maxOvers}"/>
<input type="hidden" id="matchFileTimeStamp" name="matchFileTimeStamp" value="${session_match.setup.matchFileTimeStamp}"></input>
</form:form>
<script type="text/javascript">
    var helpPageOpened = false, helpWindow = null; 
    document.addEventListener('keydown', function(event) {
        if (event.ctrlKey && event.shiftKey && event.key === 'H') {
            event.preventDefault();           
            var helpPageUrl = '<c:url value="/Help"/>';
            if (!helpPageOpened || (helpWindow && helpWindow.closed)) {
                helpWindow = window.open(helpPageUrl, '_blank'); 
                helpPageOpened = true; 
                if (helpWindow) {
                    helpWindow.onbeforeunload = function() {
                        helpPageOpened = false; 
                    };
                }
            } else {
                helpWindow.focus();
                helpWindow.location.reload();
            }
        }
    });
</script>
</body>
</html>