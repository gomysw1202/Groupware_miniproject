package miniproject1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public interface InterTotalDAO {


		// 퇴근기록 메소드(최현우)
		int leave_rcd(MemberLoginDTO mdto);

		// 출근기록 메소드(최현우)
		int attendace_rcd(MemberLoginDTO mdto);
		
		// 조퇴기록 메소드(최현우)
		int early_leave(MemberLoginDTO mdto);
		
		// 작성된 품의서를 데이터에 넣어주는 메소드(최현우)
		int write_doc(List<Map<String, String>> paralist);
		
		// 품의서의 결재자목록,문서번호를 얻어오는 메소드(최현우)
		List<Map<String, String>> select_doc_approval(int empno);
		
		// 문서번호를 알아오는 메소드(최현우)
		int select_doc_seq();
		
		//게시판을 출력해주는 메소드(최현우)
		List<BoardDTO> select_board(int page);
		
		//총 게시글 수를 알아오는 메소드(최현우)
		int contentCnt();
		
		//게시판에 글 삽입 메소드(최현우)
		int board_write(Map<String, String> paraMap);
		
		//문서번호로 조회 메서드 생성(최현우)
		AppDocumentDTO select_doc(Map<String,String> paraMap);
		
		// 문서번호를 입력받아서 승인해주는 메소드(최현우)
		int approval_doc(Map<String, String> paraMap);

		//나의 사원번호에 맞는 미결재서류목록을 보여주는 메소드(최현우)
		List<AppDocumentDTO> view_doc(int empno);
		
		// 사원번호 시퀀스값 알아오는 메소드(최현우)
		int select_emp_seq();
		
		// 직원등록 메소드(최현우)
		int register_emp(EmployeesDTO edto);
		
		// 로그인한 사원이 미결재한서류 개수 메소드(최현우)
		int cnt_doc(MemberLoginDTO mdto);
	      
		//내가쓴 문서를 조회해주는 메소드(최현우)
		List<AppDocumentDTO> me_write_doc(int empno);
		
		// 부서신설 메소드(최현우)
		int new_department(Map<String, String> paraMap);
		
		// 부서번호(시퀀스)를 알아오는 메소드(최현우)
		int select_dept_seq();
	      
		// 이번달 출퇴근기록 조회메소드(최현우)
		List<StringBuilder> select_attendance_leave(MemberLoginDTO mdto);
		
		// 결재미승인하기 메소드(최현우)
		int not_approval_doc(Map<String, String> paraMap);
		
		// 회원이용정지메소드(최현우)
		int ban(Map<String,String> paraMap);
	
		//회원 이용정지 해제 메소드(최현우)
		int unban(int memberid);
		// 게시판관리자 글삭제(최현우)
		int super_delete_board(int boardno);
		
		// 이용정지회원내역조회(최현우)
		List<MemberLoginDTO> select_ban();
					
		
//	      ===================================================
	      
		
		 // *** 사원의 월급을 보여주는 메소드 *** // (김민수)
	      public EmployeesDTO view_salary(MemberLoginDTO mdto);
	      			
	      // *** 명세서를 메소드 *** // (김민수)
	      public EmployeesDTO issue_salary(MemberLoginDTO mdto);
	      		
	      //*** 재직증명서 발급 메소드 *** // (김민수)
	      public EmployeesDTO employment_menu(MemberLoginDTO mdto);
	      		

	      // 게시판에서 조회할 번호를 선택하는 메소드(김민수)
	      public String board_search(MemberLoginDTO mdto);
	      		
	       // *** 닉네임으로 조회하는 메소드 *** //(김민수)
	       List<BoardDTO> nickname_list(String s_nick);
	           			
	           	
	      // *** 내용으로 조회하는 메소드 *** //(김민수)
	      List<BoardDTO> contents_list(String s_contents);
	        	  		
	      
	      
	      
//	      =============================================================
	      		
	      // 특정 입사지원자조회(인사담당자)(임선우)
	      ApplicantDTO searchAppli(int n_apcNo);
	      
	      // 입사지원자 등록하기(인사담당자)(임선우)
	      int appliRegister(ApplicantDTO apdto);
	      
	      // 입사지원서조회(올해상반기지원자조회, 올해하반기지원자조회, 이번연도지원자현황)(인사담당자)(임선우)
	      List<ApplicantDTO> searchAppliList(String menuNo);

	      
	      // 전체사원조회-관리자 빼고(인사당담자)(임선우)
	       List<EmployeesDTO> allEmpSearch();
	         
	       // 특정사원정보 출력(인사당담자)(임선우)
	       EmployeesDTO empInfo(String fk_empno); 
	         
	       // 전체사원조회, 사원번호조회, 특정부서조회, 인사평가기준, 입사일자기준(인사당담자)(임선우)
	       List<EmployeesDTO> searchEmp(String menuNo, Map<String, String> paraMap);
	         
	       // 사원정보 수정(인사당담자)(임선우)
	       int updateEmp(Map<String, String> paraMap);
	         
	       // 사원정보 삭제(인사담당자)(임선우)
	       int emInfoDelete(String empno);
	       
	       int existvday(Map<String, String> paraMap);
	       

	       // 휴가신청(사원)(임선우)
	       void regiVacation(Map<String, String> paraMap);
	         
	       // 휴가신청취소(사원)(임선우)
	       int deleteVacation(MemberLoginDTO mdto, String vno);
	         
	       // 특정사원 휴가내역 출력(사원)(임선우)
	       List<VacationDTO> memvdtoList(MemberLoginDTO mdto);
	         
	       // 잔여휴가일수 확인(사원)(임선우)
	       int searchvday(MemberLoginDTO mdto);
	       
	      
//	      ======================================================================
	      
	      
	      	// 사원평가 메소드(진민지)
			int emp_review(Map<String, Integer> paraMap);

			// 인사평가 조회 메소드(진민지)
			JprDTO select_jpr(int fk_empno);


			//글번호로 게시판 조회하는 메소드(진민지)
			List<BoardDTO> boardno_list(int boardno);


			//글제목으로 게시판 조회하는 메소드(진민지)
			List<BoardDTO> subject_list(String subject);

			
//			==================================================================
			
			
		    // 인사담당자 로그인(조상운)
			AdminDTO adminLogin(String adminpwd); 
			
			// 내 정보 조회(조상운)
			EmployeesDTO myInfo(int memberid);

			// 내 정보를 수정하는 메소드(조상운)
			int updateMyInfo(Map<String, String> paraMap);	
			
			// 일반사원 로그인 (조상운)
			MemberLoginDTO memberlogin(Map<String, String> paraMap);

			
			// 사원이름 가져오는 메소드(조상운)
	        String empName(MemberLoginDTO mdto);
			
			
			// 공지사항 등록 메소드(조상운)
			int updateNotice(String notice_contents);

			// 공지사항 조회 메소드(조상운)
			String showNotice();

			// 게시글 삭제 메소드(조상운)
			int boardDelete(String boardno);

			// 글번호로 게시물 조회하는 메소드(조상운) 
			BoardDTO selectOneBoard(String boardno);

			// 게시글 1개 들어가서 글내용 보는 메소드(조상운)
			BoardDTO viewOneNotice(String boardno, int memberid);

			// 게시글 1개에 대한 댓글을 보여주는 메소드(조상운)
			void viewComment(int boardno);

			// 내가 작성한 게시물의 내용을 수정하는 메소드(조상운)
			int updateMynotice(Map<String, String> paraMap);
			
			// 게시판관리자 로그인 (조상운)
			SuperAdminDTO boardAdminLogin(String memberpwd);
			
			//댓글작성하는메소드(조상운)
			int insertComment(Map<String, String> paraMap);

			
			

			
			
			
			
	      
	      
		
}
