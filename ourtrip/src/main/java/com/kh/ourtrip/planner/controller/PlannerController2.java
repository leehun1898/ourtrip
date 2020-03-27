package com.kh.ourtrip.planner.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.ourtrip.planner.model.service.PlannerService2;
import com.kh.ourtrip.planner.model.vo.ChattingLogView;
import com.kh.ourtrip.planner.model.vo.Day;
import com.kh.ourtrip.planner.model.vo.Planner;
import com.kh.ourtrip.planner.model.vo.PlannerMemberView;
import com.kh.ourtrip.planner.model.vo.PlannerView;
import com.kh.ourtrip.planner.model.vo.Schedule;

@Controller
@SessionAttributes({"loginMember","msg","profilePath"})
@RequestMapping("/planner1/*")
public class PlannerController2 {
	
	@Autowired
	PlannerService2 plannerService;
	
	@RequestMapping("testInput")
	public String chattingRoom() {
		return "planner/chattingRoom";
	}
	
	@RequestMapping("editplanner")
	public String chattingForm(Model model, Integer no, RedirectAttributes rdAttr) {
		
		JSONObject jsonObj = null;
		JSONParser jsonParser = new JSONParser();
		JSONArray chatArray = new JSONArray();
		JSONArray joinUserArray = new JSONArray();
		Planner selectedPlanner = null;
		System.out.println("editplanner");
		try {
			
			// PlannerView에 데이터 통째로 얻어와서 분리작업
			boolean inputPvVal = true; 
			int plannerNo = -1;
			String plannerTitle = null;
			String plannerPwd = null;
			int plannerCost = -1;
			Date plannerCreateDT = null;
			Date plannerModifyDT = null;
			Date plannerStartDT = null;
			String plannerPublicYN = null;
			String plannerDeleteYN = null;
			String plannerExpiry = null;
			int plannerCount = -1;
			String plannerUrl = null;
			int groupCode = -1;
			
			List<PlannerView> selectPlannerView = plannerService.selectPlannerView(no);
			System.out.println(selectPlannerView);
			Map<Integer,List<PlannerView>> dateMap = new HashMap<Integer,List<PlannerView>>();
			for(PlannerView pv : selectPlannerView) {
				if(inputPvVal) {
					plannerTitle = pv.getPlannerTitle();
					plannerPwd = pv.getPlannerPwd();
					plannerCost = pv.getPlannerCost();
					plannerCreateDT = pv.getPlannerCreateDT();
					plannerModifyDT = pv.getPlannerModifyDT();
					plannerStartDT = pv.getPlannerStartDT();
					plannerPublicYN = pv.getPlannerPublicYN();
					plannerDeleteYN = pv.getPlannerDeleteYN();
					plannerExpiry = pv.getPlannerExpiry();
					plannerCount = pv.getPlannerCount();
					plannerUrl = pv.getPlannerUrl();
					groupCode = pv.getGroupCode();
				}
				
				if(dateMap.containsKey(pv.getDateNo())) {
					dateMap.get(pv.getDateNo()).add(pv);
				}else {
					List<PlannerView> pvList = new ArrayList<PlannerView>();
					pvList.add(pv);
					dateMap.put(pv.getDateNo(), pvList);
				}
			}
			List<Day> dayList = new ArrayList<Day>();
			Iterator iter = dateMap.entrySet().iterator();
			while (iter.hasNext()) {
				int dateNo = -1;
				int tripDate = -1;
				List<Schedule> scheduleList = new ArrayList<Schedule>();
			    Entry entry = (Entry) iter.next();
			    for(PlannerView pv : (List<PlannerView>)(entry.getValue())) {
			    	if(dateNo == -1) {
			    		dateNo = pv.getDateNo();
			    	}
			    	if(tripDate == -1) {
			    		tripDate = pv.getTripDate();
			    	}
					if(plannerNo == -1) {
						plannerNo = pv.getPlannerNo();
					}
			    	Schedule schedule = new Schedule(
			    			pv.getScheduleNo(), pv.getScheduleTitle(), pv.getScheduleCost(),
			    			pv.getScheduleTime(), pv.getScheduleMemo(), pv.getScheduleLocationNM(),
			    			pv.getScheduleLat(),pv.getScheduleLng(),pv.getDateNo());
			    	scheduleList.add(schedule);
			    }
			    Day oneDay = new Day(dateNo,tripDate,no,scheduleList);
			    dayList.add(oneDay);
			    
			}
			selectedPlanner = new Planner(no, plannerTitle, plannerPwd, plannerCost, 
					plannerCreateDT, plannerModifyDT, plannerStartDT, plannerPublicYN, plannerDeleteYN, 
					plannerExpiry, plannerCount, plannerUrl, groupCode, dayList);
			jsonObj = (JSONObject) jsonParser.parse(selectedPlanner.toJsonString());
			
			// 채팅내역 얻어와서 jsonString으로 변환
			List<ChattingLogView> chatList = null;
			chatList = plannerService.selectChatList(no);
			for(ChattingLogView cl : chatList) {
				jsonObj = (JSONObject) jsonParser.parse(cl.toJsonString());
				chatArray.add(jsonObj);
			}
			
			// 참여중인 유저 목록과 권한 전달
			List<PlannerMemberView> pmList = plannerService.selectPlannerMemeberListUsePlannerNo(no);
			
			for(PlannerMemberView tpm : pmList) {
				jsonObj = (JSONObject) jsonParser.parse(tpm.toJsonString());
				joinUserArray.add(jsonObj);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("plannerInfo", selectedPlanner.toJsonString());
		model.addAttribute("plannerTitle", selectedPlanner.getPlannerTitle());
		model.addAttribute("chatList", chatArray);
		model.addAttribute("joinUserArray",joinUserArray);
		System.out.println("editplannerend");
		
		return "planner/editPlanner";
	}
	
	@RequestMapping("mobileView")
	public String mobileView(Model model, Integer no, RedirectAttributes rdAttr, String userId) {
		
		JSONObject jsonObj = null;
		JSONParser jsonParser = new JSONParser();
		JSONArray locationArray = new JSONArray();
		JSONArray tempArray = new JSONArray();
		Planner selectedPlanner = null;
		System.out.println("mobileView");
		try {
			
			// PlannerView에 데이터 통째로 얻어와서 분리작업
			boolean inputPvVal = true; 
			int plannerNo = -1;
			String plannerTitle = null;
			String plannerPwd = null;
			int plannerCost = -1;
			Date plannerCreateDT = null;
			Date plannerModifyDT = null;
			Date plannerStartDT = null;
			String plannerPublicYN = null;
			String plannerDeleteYN = null;
			String plannerExpiry = null;
			int plannerCount = -1;
			String plannerUrl = null;
			int groupCode = -1;
			
			List<PlannerView> selectPlannerView = plannerService.selectPlannerView(no);
			System.out.println(selectPlannerView);
			Map<Integer,List<PlannerView>> dateMap = new HashMap<Integer,List<PlannerView>>();
			for(PlannerView pv : selectPlannerView) {
				if(inputPvVal) {
					plannerTitle = pv.getPlannerTitle();
					plannerPwd = pv.getPlannerPwd();
					plannerCost = pv.getPlannerCost();
					plannerCreateDT = pv.getPlannerCreateDT();
					plannerModifyDT = pv.getPlannerModifyDT();
					plannerStartDT = pv.getPlannerStartDT();
					plannerPublicYN = pv.getPlannerPublicYN();
					plannerDeleteYN = pv.getPlannerDeleteYN();
					plannerExpiry = pv.getPlannerExpiry();
					plannerCount = pv.getPlannerCount();
					plannerUrl = pv.getPlannerUrl();
					groupCode = pv.getGroupCode();
					inputPvVal = false;
				}

				if(dateMap.containsKey(pv.getDateNo())) {
					dateMap.get(pv.getDateNo()).add(pv);
				}else {
					List<PlannerView> pvList = new ArrayList<PlannerView>();
					pvList.add(pv);
					dateMap.put(pv.getDateNo(), pvList);
				}
			}
			List<Day> dayList = new ArrayList<Day>();
			Iterator iter = dateMap.entrySet().iterator();
			while (iter.hasNext()) {
				int dateNo = -1;
				int tripDate = -1;				
				
				tempArray = new JSONArray();
				
				List<Schedule> scheduleList = new ArrayList<Schedule>();
			    Entry entry = (Entry) iter.next();
			    for(PlannerView pv : (List<PlannerView>)(entry.getValue())) {
			    	if(dateNo == -1) {
			    		dateNo = pv.getDateNo();
			    	}
			    	if(tripDate == -1) {
			    		tripDate = pv.getTripDate();
			    	}
					if(plannerNo == -1) {
						plannerNo = pv.getPlannerNo();
					}
			    	Schedule schedule = new Schedule(
			    			pv.getScheduleNo(), pv.getScheduleTitle(), pv.getScheduleCost(),
			    			pv.getScheduleTime(), pv.getScheduleMemo(), pv.getScheduleLocationNM(),
			    			pv.getScheduleLat(),pv.getScheduleLng(),pv.getDateNo());
			    	scheduleList.add(schedule);
			    	String scheduleLocation = "{";
					scheduleLocation += "\"sno\":\"" + pv.getScheduleNo() ;
					scheduleLocation += "\",\"location\":\"" + pv.getScheduleLocationNM() ;
					scheduleLocation += "\",\"lat\":\"" + pv.getScheduleLat() ;
					scheduleLocation += "\",\"lng\":\"" + pv.getScheduleLng() ;
					scheduleLocation += "\"}";
					jsonObj = (JSONObject) jsonParser.parse(scheduleLocation);
					tempArray.add(jsonObj);
			    }
			    Day oneDay = new Day(dateNo,tripDate,no,scheduleList);
			    dayList.add(oneDay);
	
			    jsonObj = (JSONObject) jsonParser.parse("{\"dno\":\"" + dateNo + "\"}");
			    jsonObj.put("schedules", tempArray);
			    locationArray.add(jsonObj);
			    
			}
			selectedPlanner = new Planner(no, plannerTitle, plannerPwd, plannerCost, 
					plannerCreateDT, plannerModifyDT, plannerStartDT, plannerPublicYN, plannerDeleteYN, 
					plannerExpiry, plannerCount, plannerUrl, groupCode, dayList);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("plannerInfoJson", selectedPlanner.toJsonString());
		model.addAttribute("plannerInfo", selectedPlanner);
		model.addAttribute("locationArray", locationArray);
		System.out.println("mobileViewEnd");
		
		return "planner/plannerView";
	}
}
