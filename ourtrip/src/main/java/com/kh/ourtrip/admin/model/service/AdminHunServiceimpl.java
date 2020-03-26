package com.kh.ourtrip.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.ourtrip.admin.model.dao.AdminHunDAO;
import com.kh.ourtrip.common.vo.PageInfo;
import com.kh.ourtrip.member.model.vo.Member;
import com.kh.ourtrip.member.model.vo.ProfileImage;
import com.kh.ourtrip.planner.model.vo.AreaName;
import com.kh.ourtrip.planner.model.vo.Day;
import com.kh.ourtrip.planner.model.vo.Planner;
import com.kh.ourtrip.planner.model.vo.PlannerInfo;

@Service
public class AdminHunServiceimpl implements AdminHunService {
	
	@Autowired
	AdminHunDAO adminHunDAO;
	
	/** 회원수 전체조회용 service
	 * @return listFullCount
	 * @throws Exception
	 */
	@Override
	public int getListFullCount() throws Exception {

		return adminHunDAO.getListFullCount();
	}
	
	/** 회원 전체조회용 service
	 * @param pInf
	 * @return memberList
	 * @throws Exception
	 */
	@Override
	public List<Member> selectFullList(PageInfo pInf) throws Exception {

		return adminHunDAO.selectFUllList(pInf);
	}

	/** 회원 목록 조회용 service
	 * @param map
	 * @return listCount
	 * @throws Exception
	 */
	@Override
	public int getListCount(Map<String, String> map)throws Exception {
		
		return adminHunDAO.getListCount(map);
	}

	/** 회원 목록조회용 service
	 * @param map
	 * @param pInf
	 * @return memberList
	 * @throws Exception
	 */
	@Override
	public List<Member> selectList(Map<String, String> map, PageInfo pInf) throws Exception {

		return adminHunDAO.selectList(map, pInf);
	}

	/** 회원 상세조회용 service
	 * @param no
	 * @return Member
	 * @throws Exception
	 */
	@Override
	public Member detail(int no) throws Exception {
		
		return adminHunDAO.detail(no);
	}


	/** 플래너 넘버 조회용 service
	 * @param no
	 * @return plannerList
	 * @throws Exception
	 */
	@Override
	public List<Integer> plannerList(int no) throws Exception {
		
		return adminHunDAO.plannerList(no);
	}

	/** 플래너 카드 조회용 service
	 * @param plannerList
	 * @param pInf
	 * @return plannerInfo
	 * @throws Exception
	 */
	@Override
	public List<PlannerInfo> plannerInfo(List<Integer> plannerList, PageInfo pInf) throws Exception {
		
		return adminHunDAO.plannerInfo(plannerList,pInf);
	}

	/** 지역조회용 service
	 * @param plannerList
	 * @return plannerArea
	 * @throws Exception
	 */
	@Override
	public List<AreaName> plannerArea(List<Integer> plannerList) throws Exception {
		return adminHunDAO.plannerArea(plannerList);
	}

	/**프로필 이미지 조회용 service
	 * @param no
	 * @return pi
	 * @throws Exception
	 */
	@Override
	public ProfileImage selectProfileImage(int no) throws Exception {
		return adminHunDAO.selectProfileImage(no);
	}

	/**플래너 목록조회용 DAO
	 * @return totalList
	 * @throws Exception
	 */
	@Override
	public List<PlannerInfo> plannerTotal(PageInfo pInf) throws Exception {
		return adminHunDAO.plannerTotal(pInf);
	}

	/**플래너 개수 조회용 service
	 * @return int 
	 * @throws Exception
	 */
	@Override
	public int plannerCount() throws Exception {
		return adminHunDAO.plannerCount();
	}

	/**플래너 위치조회용 DAO
	 * @return list<areaName>
	 * @throws Exception
	 */
	@Override
	public List<AreaName> areaList() throws Exception {
		return adminHunDAO.areaList();
	}

	/**여행일자 조회용 service
	 * @return list<day>
	 * @throws Exception
	 */
	@Override
	public List<Day> dayList() throws Exception {
		
		return adminHunDAO.dayList();
	}

	/** 검색후 플래너 count용 service
	 * @param keyword
	 * @return searchResultcount
	 * @throws Exception
	 */
	@Override
	public List<Integer> resultCount(Map<String, Object> keyword) throws Exception {
		if(keyword.get("startTrip")=="") {
			keyword.put("startTrip", null);
		}if(keyword.get("endTrip") == "") {
			keyword.put("endTrip", null);
		}
			
		return adminHunDAO.resultCount(keyword);
	}

	/** 검색결과 조회 service
	 * @param pInf
	 * @param keyword
	 * @return List searchResult
	 * @throws Exception
	 */
	@Override
	public List<PlannerInfo> searchResult(PageInfo pInf, Map<String, Object> keyword) throws Exception {
		if(keyword.get("startTrip") == "" && keyword.get("endTrip") == "") {
			keyword.put("startTrip", null);
			keyword.put("endTrip", null);
		}
		
		return adminHunDAO.searchResult(pInf, keyword);
	}

	@Override
	public List<AreaName> resultArea(List<Integer> searchResultcount) throws Exception {
		if(searchResultcount.isEmpty()) {
			searchResultcount = null;
		}
		return adminHunDAO.resultArea(searchResultcount);
	}

	/** planner별 검색 날짜 조회용 service
	 * @param searchResultcount
	 * @return List<Day> dayList
	 * @throws Exception
	 */
	@Override
	public List<Day> resultDay(List<Integer> searchResultcount) throws Exception {
		if(searchResultcount.isEmpty()) {
			searchResultcount = null;
		}
		return adminHunDAO.resultDay(searchResultcount);
	}



}
