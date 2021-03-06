package com.kh.ourtrip.member.model.service;

import java.io.File;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ourtrip.common.FileRename;
import com.kh.ourtrip.member.model.dao.MemberDAO;
import com.kh.ourtrip.member.model.vo.Member;
import com.kh.ourtrip.member.model.vo.ProfileImage;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO memberDAO;
	
	// 암호화를 위한 객체 DI(의존성 주입)
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// 메일 전송을 위한 객체 DI
	@Autowired
	private JavaMailSender mailSender;
	
	/** 회원 로그인용 Service
	 * @author yousang
	 * @param member
	 * @return loginMember
	 * @throws Exception
	 */
	@Override
	public Member login(Member member) throws Exception {
		Member loginMember = memberDAO.login(member);
		
		if(loginMember != null &&
			!bCryptPasswordEncoder.matches(member.getMemberPwd(), loginMember.getMemberPwd())) {
			
			loginMember = null;
		}
		
		return loginMember;
	}

	/** 카카오 로그인용 Service
	 * @param member
	 * @param imagePath
	 * @return loginMember
	 * @throws Exception
	 */
	@Override
	public Member kakaoLogin(Member member, String imagePath) throws Exception {
		// 1) ourtrip DB에 회원으로 등록되어있는지 확인
		int result = memberDAO.isMember(member);
				
		// 2) 안되있을 시 회원가입
		if(result == 0) {
			result = memberDAO.signUp(member);
			
			if(result > 0) {
				result = memberDAO.selectMemberNo(member);
				if(imagePath != null)
					result = memberDAO.insertProfileImage(new ProfileImage(imagePath, result));
			}
		}
		
		// 3) 회원 객체 반환
		return memberDAO.socialLogin(member);
	}

	/** 이메일 확인용 Service
	 * @param email
	 * @return result
	 */
	@Override
	public int emailCertify(String email) throws Exception{
		int result = memberDAO.emailCertify(email);

		if (result > 0) { // 이메일이 이미 존재할 경우
			result = 0;
		} else { // 존재하지 않을 경우
			result = (int) (Math.random() * 999999) + 1;

			String setfrom = "khourtrip@gmail.com";

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(email); // 받는사람 이메일
			messageHelper.setSubject("OurTrip 인증번호"); // 메일제목은 생략이 가능하다
			messageHelper.setText(Integer.toString(result)); // 메일 내용

			mailSender.send(message);
		}
		
		return result;
	}

	/** 회원가입용 Service
	 * @param member
	 * @return memberNo
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int signUp(Member member) throws Exception {
		member.setMemberPwd(bCryptPasswordEncoder.encode(member.getMemberPwd()));
		
		int result = memberDAO.signUp(member);
		
		if(result > 0) {
			result = memberDAO.selectMemberNo(member);
		}
		
		return result;
	}

	/** 회원 프로필 사진 등록용 Service
	 * @param pi
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertProfileImage(ProfileImage pi) throws Exception {
		return memberDAO.insertProfileImage(pi);
	}

	/** 회원 프로필 사진 조회용 Service
	 * @param memberNo
	 * @return profileImage
	 * @throws Exception
	 */
	@Override
	public ProfileImage selectProfileImage(int memberNo) throws Exception {
		return memberDAO.selectProfileImage(memberNo);
	}

	/** 회원 닉네임 수정용 Service
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateNickName(Member member) throws Exception {
		return memberDAO.updateNickName(member);
	}

	/** 회원 프로필 사진 삭제용 Service
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteProfileImage(int memberNo) throws Exception {
		int result = 0;
		ProfileImage pi = memberDAO.selectProfileImage(memberNo);
		
		// 회원의 프로필 사진이 존재하는 경우
		if(pi != null) {
			// 프로필 사진을 DB에서 삭제
			result = memberDAO.deleteProfileImage(memberNo);
			
			// DB에서 삭제 성공한 경우
			if(result > 0) {
				
			}
		}

		// 삭제 성공시 서버에서도 해당 이미지 삭제
		return result;
	}

	/** 회원 프로필 사진 수정용 Service
	 * @param memberNo
	 * @param profileImage
	 * @param savePath
	 * @param isDefault
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateProfileImage(int memberNo, MultipartFile profileImage, String savePath, String isDefault) throws Exception {
		int result = 0;
		ProfileImage pi = memberDAO.selectProfileImage(memberNo);
		
		String changeFileName = null;
		if(!profileImage.getOriginalFilename().equals("")) { // 이미지 변경시 rename
			changeFileName = FileRename.rename(profileImage.getOriginalFilename());
		}
		
		File deleteFile = null;
		
		// 회원의 기존 프로필 사진이 존재하는 경우
		if(pi != null) {
			
			// 사진을 변경하지 않은 경우
			if(profileImage.getOriginalFilename().equals("") && isDefault.equals("false")) {
				result = 1;
				
			// 사진을 변경했는데 디폴트 이미지로 변경했을 경우
			}else if(profileImage.getOriginalFilename().equals("") && isDefault.equals("true")) {
				// DB에서 이미지 삭제
				result = memberDAO.deleteProfileImage(memberNo);
				
				if(result > 0) { // 서버에서도 이미지 삭제
					deleteFile = new File(pi.getImagePath());
					deleteFile.delete();
					
				}else throw new Exception("이미지 삭제 중 오류 발생");
				
			// 사진을 변경한 경우
			}else if(!profileImage.getOriginalFilename().equals("")) {
				
				// 서버에 존재하는 기존 이미지를 삭제하기 위해 저장k
				String originFilePath = pi.getImagePath();
				
				// DB에 update하기 위해 경로 값을 변경
				pi.setImagePath(savePath + "/" + changeFileName);
				
				result = memberDAO.updateProfileImage(pi);
				
				// DB에서 수정 성공한 경우 서버에 새로운 이미지 저장
				if(result > 0) profileImage.transferTo(new File(pi.getImagePath()));
				else throw new Exception("이미지 수정 중 오류 발생");
				
				deleteFile = new File(originFilePath);
				deleteFile.delete();
			}
			
		// 회원의 기존 프로필 사진이 존재하지 않는 경우
		}else {
			
			// 사진을 변경한 경우
			if(!profileImage.getOriginalFilename().equals("")) {
				pi = new ProfileImage(savePath + "/" + changeFileName, memberNo);
				
				result = memberDAO.insertProfileImage(pi);
				
				if(result > 0) profileImage.transferTo(new File(pi.getImagePath()));
				
			// 변경하지 않은 경우
			}else result = 1;
		}

		return result;
	}

	/** 회원 비밀번호 변경용 Service
	 * @param member
	 * @param changePwd
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int changePwd(Member member, String changePwd) throws Exception {
		
		// 비밀번호가 일치하지 않을 경우의 초기값
		int result = -1;
		
		// 로그인한 회원의 비밀번호 조회
		String memberPwd = memberDAO.selectMemberPwd(member.getMemberNo());
		
		if(bCryptPasswordEncoder.matches(member.getMemberPwd(), memberPwd)){
			
			member.setMemberPwd(bCryptPasswordEncoder.encode(changePwd));
			
			// 변경 성공시 1, 실패시 0 저장
			result = memberDAO.updatePwd(member);
		}
		
		return result;
	}

	/** 회원탈퇴용 Service
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int secession(Member member) throws Exception {
		
		// 비밀번호가 일치하지 않을 경우의 초기값
		int result = -1;
		
		// 로그인한 회원의 비밀번호 조회
		String memberPwd = memberDAO.selectMemberPwd(member.getMemberNo());
		
		if(bCryptPasswordEncoder.matches(member.getMemberPwd(), memberPwd)){
			
			// 회원 상태값 변경(성공시 1, 실패시 0)
			result = memberDAO.secession(member);
		}
		
		return result;
	}


	/** 회원가입된 이메일인지 확인용 Service
	 * @param email
	 * @return result
	 * @throws Exception
	 */
	@Override
	public int signUpedEmail(String email) throws Exception {
		
		int result = memberDAO.signUpedEmail(email);
		
		if (result > 0) { // 이메일이 존재할 경우

			result = (int) (Math.random() * 999999) + 1;

			String setfrom = "khourtrip@gmail.com";

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(email); // 받는사람 이메일
			messageHelper.setSubject("OurTrip 인증번호"); // 메일제목은 생략이 가능하다
			messageHelper.setText(Integer.toString(result)); // 메일 내용

			mailSender.send(message);
		}
		
		return result;
	}

	/** 회원 비밀번호 찾기용 Service
	 * @param memberEmail
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int findPwd(String memberEmail) throws Exception {
		Member member = memberDAO.selectMember(memberEmail);
		System.out.println("service : " + member);
		int result = 0;
		
		if(member != null) {
			
			String tempPwd = "";
			Random rand = new Random();
			
			int pwdSize = rand.nextInt(3) + 10;
			
			// 10 ~ 12자리의 임시 비밀번호 생성
			for(int i=0; i<pwdSize; i++) {
				int index = rand.nextInt(3);
				
				switch(index) {
				case 0: tempPwd += String.valueOf((char) ((int)(rand.nextInt(26)) + 97)); break; // a-z
				case 1: tempPwd += String.valueOf((char) ((int)(rand.nextInt(26)) + 65)); break; // A-Z
				case 2: tempPwd += String.valueOf(rand.nextInt(10)); break; // 0-9
				}
			}
			
			System.out.println("tempPwd : " + tempPwd);
			
			// 생성한 임시 비밀번호 암호화
			member.setMemberPwd(bCryptPasswordEncoder.encode(tempPwd));
			
			result = memberDAO.updatePwd(member);

			// 비밀번호 변경 성공 시 임시 비밀번호 메일로 전송
			if(result > 0) {
				String setfrom = "khourtrip@gmail.com";
				
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				
				messageHelper.setFrom(setfrom); // 보내는사람 생략하거나 하면 정상작동을 안함
				messageHelper.setTo(memberEmail); // 받는사람 이메일
				messageHelper.setSubject("OurTrip 임시 비밀번호"); // 메일제목은 생략이 가능하다
				messageHelper.setText(tempPwd); // 메일 내용
				
				mailSender.send(message);
			}
		}
		
		return result;
	}
	
	/** 프로필 사진 경로 조회용 Service
	 * @param memberNo
	 * @return profileImagePath
	 * @throws Exception
	 */
	@Override
	public String getProfileImagePath(int memberNo) throws Exception {
		return memberDAO.getProfileImagePath(memberNo);
	}

	/** 네이버 로그인용 Service
	 * @param member
	 * @param imagePath
	 * @return loginMember
	 * @throws Exception
	 */
	@Override
	public Member naverLogin(Member member, String imagePath) throws Exception{
		// 1) ourtrip DB에 회원으로 등록되어있는지 확인
		int result = memberDAO.isMember(member);
				
		// 2) 안되있을 시 회원가입
		if(result == 0) {
			result = memberDAO.signUp(member);
			
			if(result > 0) {
				result = memberDAO.selectMemberNo(member);
				if(imagePath != null)
					result = memberDAO.insertProfileImage(new ProfileImage(imagePath, result));
			}
		}
		
		// 3) 회원 객체 반환
		return memberDAO.socialLogin(member);
	}

}
