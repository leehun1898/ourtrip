package com.kh.ourtrip.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.kh.ourtrip.member.model.service.MemberService;
import com.kh.ourtrip.member.model.vo.Member;

@SessionAttributes({"loginMember", "msg"})
@Controller
@RequestMapping(value="/member/*")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="loginForm")
	public String loginForm() {
		return "member/login";
	}
	
	@RequestMapping(value="login")
	public String login(Member member, Model model) {
		
		try {
			Member loginMember = memberService.login(member);
			
			String path = null;
			if(loginMember != null) {
				model.addAttribute("loginMember", loginMember);
				path = "redirect:/";
			}else {
				model.addAttribute("msg", "이메일, 비밀번호를 확인해주세요");
				path = "redirect:/loginForm";
			}
			
			return path;
			
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "로그인 과정 중 오류 발생");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="logout")
	public String logout(SessionStatus session) {
		session.setComplete();
		
		return "redirect:/";
	}
}
