package com.kh.ourtrip.planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kh.ourtrip.member.model.vo.Member;
import com.kh.ourtrip.planner.model.service.PlannerService;
import com.kh.ourtrip.planner.model.vo.Planner;
import com.kh.ourtrip.planner.model.vo.PlannerMember;

@Controller
@SessionAttributes({"msg" , "loginMember"})
@RequestMapping("/planner/*")
public class PlannerController {

	@Autowired
	PlannerService plannerService;

	@RequestMapping("create")
	public String createPlannerform(Member loginMember, Model model) {

		
		if(loginMember == null) {
			model.addAttribute("msg","로그인후 이용해 주세요");
			return "index";
		}else {
			return "planner/createForm";
		}
	}

	@RequestMapping("createPlanner")
	public String createPlanner(Planner planner, Model model) { 

		if (planner.getPlannerPublicYN().equals("on")) {
			planner.setPlannerPublicYN("Y");
		} else {
			planner.setPlannerPublicYN("N");
		}
		
		
		String url = "";
		for(int i = 1; i<16; i++) {
			double random = Math.random();
			int ranDomInt = (int)(random *10); 
			url += ranDomInt;
		}
		
		planner.setPlannerUrl(url);
		
		Member member = (Member)(model.getAttribute("loginMember"));
		
		try {
			int result = plannerService.createPlanner(planner,member);
			
			
			if(result >0) {
				model.addAttribute("플래너 생성이 완료되었습니다");
				return "planner/plannerList";
			}else {
				model.addAttribute("플래너 생성이 실패하였습니다");
				return "planner/plannerForm";
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "플래너 생성과정 중 오류발생");
			return "common/errorPage";
		}

	}

}
