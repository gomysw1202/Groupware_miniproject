package miniproject1;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;


public class TotalDAO implements InterTotalDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// === 자원반납을 해주는 메소드 === //
		private void close() {
			
			try {
				if(rs != null)     rs.close();
				if(pstmt != null)  pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}// end of private void close() ---------------------------		
			
		
		
		
		
	/*
	---------------------------------------------------------------------------------------------------
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	---------------------------------------------------------------------------------------------------
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	---------------------------------------------------------------------------------------------------
	*/		
			
			
		
		
	
		
	// 인사담당자 로그인(조상운)
	@Override
	public AdminDTO adminLogin(String adminpwd) {
		
	    AdminDTO adto = null;
		
		try {
			conn = MyDBConnection.getConn();
			
			String sql = " select adminid, adminpwd, fk_empno "
					   + " from tbl_admin "
					   + " where adminid = 'admin' and adminpwd = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, adminpwd);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			if(rs.next()) {
				cnt ++;
				if(cnt == 1) {
					adto = new AdminDTO();
					adto.setAdminid(rs.getString("ADMINID"));
					adto.setAdminpwd(rs.getString("ADMINPWD"));
					adto.setFk_empno(rs.getInt("FK_EMPNO"));
					
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		return adto;
		
	}// end of public AdminDTO adminLogin(String adminpwd) -----------------------------------		
	
	
	
	
	
	
	
	@Override
	//결재 승인하기 메소드(최현우)
	public int not_approval_doc(Map<String, String> paraMap) {
		
		int n = 0;
		try {
			conn = MyDBConnection.getConn();
			
			String sql = " update tbl_documents_decision "
						+" set approval = 0, comments= ? "
						+" where fk_doc_no= ? and fk_decision_empno = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("comments"));
			pstmt.setString(2, paraMap.get("doc_no"));
			pstmt.setString(3, paraMap.get("empno"));
			n = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			if(e.getErrorCode()==1) { // 실패한다면
				n=-1;
			}
			else {
				e.printStackTrace();
			}
		} finally {
			close();
		}
		return n;
		
	}//end of not_approval_doc(Map<String, String> paraMap)
	
	
	
	
	
	
	
	// 로그인중인 사원이름가져오는 메소드(조상운)
    public String empName(MemberLoginDTO mdto) {
       String emp_name = "";
       try {
          conn = MyDBConnection.getConn();
          String sql = " select empname "
                   + " from tbl_employees "
                   + " where empno = ? ";
          pstmt = conn.prepareStatement(sql);
          pstmt.setInt(1,mdto.getMemberid());
          rs = pstmt.executeQuery();
          if(rs.next()) {
             emp_name = rs.getString(1);
          }
       } catch (SQLException e) {
          e.printStackTrace();
       } finally {
          close();
       }
       return emp_name;
    }//end of empName---
			
			
			
	/*
	---------------------------------------------------------------------------------------------------
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	---------------------------------------------------------------------------------------------------
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	---------------------------------------------------------------------------------------------------
	*/			
			
			
			
			
			
	// 내정보를 조회하는 메소드
	@Override
	public EmployeesDTO myInfo(int memberid) {
		EmployeesDTO employee = new EmployeesDTO();
		
		try {
			conn = MyDBConnection.getConn();
			
			String sql = " select empno, empname, fk_deptno, empposition, address, salary, commission_pct, email, hiredate "
					   + " from tbl_employees "
					   + " where empno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,memberid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				employee.setEmpno(rs.getInt("EMPNO"));
				employee.setEmpname(rs.getString("EMPNAME"));
				employee.setFk_deptno(rs.getInt("FK_DEPTNO"));
				employee.setEmpposition(rs.getString("EMPPOSITION"));
				employee.setAddress(rs.getString("ADDRESS"));
				employee.setSalary(Integer.parseInt(rs.getString("SALARY")));
				employee.setCommission_pct(Integer.parseInt(rs.getString("COMMISSION_PCT")));
				employee.setEmail(rs.getString("EMAIL"));
				employee.setHiredate(rs.getString("HIREDATE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		
		return employee;
	}// end of public EmployeesDTO myInfo(int memberid) ----------------------------------
			
			
			
	
	
	
	
	/*
	---------------------------------------------------------------------------------------------------
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	---------------------------------------------------------------------------------------------------
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	---------------------------------------------------------------------------------------------------
	*/		
			
			
			
			
			
			
			

	// 내 정보를 수정하는 메소드(조상운)
	   @Override
	   public int updateMyInfo(Map<String, String> paraMap) {
	      int result = 0;
	      
	      try {
	         conn = MyDBConnection.getConn();
	         
	         String sql = " update tbl_employees set empname = ? , address = ?, email = ? "
	                    + " where empno = ? ";
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, paraMap.get("empname"));
	         pstmt.setString(2, paraMap.get("address"));
	         pstmt.setString(3, paraMap.get("email"));
	         pstmt.setString(4, paraMap.get("str_memberid"));
	         
	         result += pstmt.executeUpdate();
	         
	         if(result == 1) {
	            sql = " update tbl_member set memberpwd = ? "
	               + " where memberid = ? ";
	            
	            pstmt = conn.prepareStatement(sql);
	            
	            pstmt.setString(1, paraMap.get("memberpwd"));
	            pstmt.setString(2, paraMap.get("str_memberid"));
	            
	            result += pstmt.executeUpdate();
	         }
	         
	      } catch (SQLException e) {    // SQL의 문제로 에러가 발생하다면
	         result = -1;
	         e.printStackTrace();
	      } finally {
	         close();
	      }
	      
	      return result;
	      
	   }// end of public int updateMyInfo(Map<String, String> paraMap) --------------------------------		
			
			
	
	
		
	/*
	---------------------------------------------------------------------------------------------------
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	---------------------------------------------------------------------------------------------------
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	---------------------------------------------------------------------------------------------------
	*/	
		
		
		
		
		
		
	// 일반사원 로그인하는 메소드(조상운)	
		@Override
		public MemberLoginDTO memberlogin(Map<String, String> paraMap) {
			MemberLoginDTO mdto = null;
			
			try {
				conn = MyDBConnection.getConn();
				
				String sql = " select * from tbl_member where memberid = ? and memberpwd = ? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, paraMap.get("memberid"));
				pstmt.setString(2, paraMap.get("memberpwd"));
				
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				
				if(rs.next()) {
					cnt++;
					if(cnt == 1) {
						mdto = new MemberLoginDTO();
						mdto.setMemberid(rs.getInt("MEMBERID"));
						mdto.setMemberpwd(rs.getString("MEMBERPWD"));
						mdto.setBoard_stop(rs.getInt("BOARD_STOP"));
						mdto.setComments(rs.getString("comments"));
					}
				}//end of if--
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			
			return mdto;
		}// end of public MemberLoginDTO memberlogin(Map<String, String> paraMap) -------------------------
		
		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		
		
		//출근기록을 해주는 메소드(최현우) 
		@Override
		public int attendace_rcd(MemberLoginDTO mdto) {
			int n=0;
			try {
				conn = MyDBConnection.getConn();
				
				String sql = " insert into tbl_attendance_rcd(fk_empno,registerday,attendance_time,leave_time,empname,memo) "
						   + " values((select empno from tbl_employees where empno = ? )"
						   + "        ,to_char(sysdate,'yyyy-mm-dd')"
						   + "        ,to_char(sysdate,'hh:mi:ss')"
						   + "        ,'출근중' "
						   + "        ,(select empname from tbl_employees where empno = ? )"
						   + "        ,'없음' ) ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, mdto.getMemberid());
				pstmt.setInt(2, mdto.getMemberid());
				n = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				if(e.getErrorCode()==1) { // 만약 하루에 출근을 두번찍는다면,
					n=-1;
				}
				else {
					e.printStackTrace();
				}
			} finally {
				close();
			}
			return n;
		}// end of public int attendace_rcd(MemberLoginDTO mdto)-----


		
		
		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/	
			
		
		
		
		
			
			

		// 퇴근기록을 해주는 메소드(최현우)
		@Override
		public int leave_rcd(MemberLoginDTO mdto) {
			int n=0;
			try {
				conn = MyDBConnection.getConn();
				
				String sql =  " update tbl_attendance_rcd "
							+ " set leave_time = to_char(sysdate,'hh:mi:ss') "
							+ " where leave_time ='출근중' and fk_empno = ? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, mdto.getMemberid());
				n = pstmt.executeUpdate();
				
			} catch (SQLException e) { 	// SQL의 문제로 에러가 발생하다면
				n = -1;
			} finally {
				close();
			}
			return n;
			
		}// end of public int leave_rcd(MemberLoginDTO mdto, int n)	
		
		
		
		//조퇴 기록을 해주는 메소드(최현우)
		@Override
		public int early_leave(MemberLoginDTO mdto) {
			int n=0;
			try {
				conn = MyDBConnection.getConn();
				
				String sql =  " update tbl_attendance_rcd "
							+ " set leave_time = to_char(sysdate,'hh:mi:ss'),memo = '조퇴' "
							+ " where leave_time ='출근중' and fk_empno = ? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, mdto.getMemberid());
				n = pstmt.executeUpdate();
				
			} catch (SQLException e) { 	// SQL의 문제로 에러가 발생하다면
				n = -1;
				e.printStackTrace();
			} finally {
				close();
			}
			return n;
		}
		
		

		// 이번달 출퇴근기록 조회메소드(최현우)
		@Override
		public List<StringBuilder> select_attendance_leave(MemberLoginDTO mdto) {
			StringBuilder sb = null;
			List<StringBuilder> sblist = new ArrayList<>();
			try {
				conn = MyDBConnection.getConn();
				
				String sql = " select * from tbl_attendance_rcd "
							+ " where registerday between to_date(to_char(last_day(add_months(to_date(to_char(sysdate,'yyyy-mm-dd')),-1))+1,'yyyy-mm-dd')) "
							+ " and to_date(to_char(last_day(to_date(to_char(sysdate,'yyyy-mm-dd'))),'yyyy-mm-dd')) "
							+ " and fk_empno = ? ";
					
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1,mdto.getMemberid());
				
				rs = pstmt.executeQuery();
				int cnt=0;
				while(rs.next()) {
					cnt++;
					if(cnt==1) {
						sb = new StringBuilder();
						sb.append("------------------------------------------------------\n"
								+ "사원번호     사원명     출근일자     출근시간    퇴근시간      비고\n"
								+ "------------------------------------------------------\n");
					}//end of if----
					sb.append(rs.getInt("FK_EMPNO")+"   ");
					sb.append(rs.getString("EMPNAME")+"  ");
					sb.append(rs.getString("REGISTERDAY")+"  ");
					sb.append(rs.getString("ATTENDANCE_TIME")+"  ");
					sb.append(rs.getString("LEAVE_TIME")+"     ");
					sb.append(rs.getString("MEMO")+"\n");
					
					sblist.add(sb);
				}//end of while------
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			return sblist;
		}//end of select_attendance_leave(MemberLoginDTO mdto)
		
		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		
		
		
		
		// 게시판 관리자 로그인 메소드(조상운)	
		@Override
		public SuperAdminDTO boardAdminLogin(String memberpwd) {
			
			SuperAdminDTO superdto = null;
			
			try {
				conn = MyDBConnection.getConn();
				
				String sql = " select baid, bapwd, fk_empno "
						   + " from tbl_boardadmin "
						   + " where baid = 'boardadmin' and bapwd = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, memberpwd);
				
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				if(rs.next()) {
					cnt ++;
					if(cnt == 1) {
						superdto = new SuperAdminDTO();
						superdto.setBaid(rs.getString("BAID"));
						superdto.setBapwd(rs.getString("BAPWD"));
						superdto.setFk_empno(rs.getInt("FK_EMPNO"));
						
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			
			
			return superdto;
		}// end of SuperAdminDTO boardAdminLogin(String memberpwd) ---------------------------------



		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/	




		// 품의서의 결재자목록을 알아오는 메소드(최현우)
		@Override
		public List<Map<String, String>> select_doc_approval(int empno) {	
			
			List<Map<String, String>> emplist = new ArrayList<>();
			try {
				conn = MyDBConnection.getConn();
				
				String sql =  " select level - 1 as levelno"
							+ "        , empno "
							+ " from tbl_employees "
							+ " where level > 1 "
							+ " start with empno = ? "
							+ " connect by prior managerno = empno ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, empno);		//가져온 empno 값을 위치홀더에 넣기
				rs = pstmt.executeQuery();
				while(rs.next()) {	//결재자 목록을 읽어와서
					Map<String, String> empMap = new HashMap<>(); 
					empMap.put("levelno", String.valueOf(rs.getInt("LEVELNO")));
					empMap.put("empno", rs.getString("EMPNO"));
					emplist.add(empMap);	//리스트에 담기
				}//end of while---
					
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
				close();
			}
			return emplist;
			
		}//end of public void write_doc(Map<String, String> paraMap)
		
		
		
		
		


		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/

		
		

		//문서번호를 알아오는 메소드(최현우)
		@Override
		public int select_doc_seq() {
			int result=0;
			try {
				conn = MyDBConnection.getConn();
				
				String sql =  " select seq_documentno.nextval "
							+ " from dual ";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {	//읽어올 시퀀스값이 있다면
					result = rs.getInt(1);
				}//end of while---
			} catch (SQLException e) {
				e.printStackTrace();	
			} finally {
				close();
			}
			return result;
		}//end of public int select_doc_seq()-------------

		
		
		//부서번호를 알아오는 메소드(최현우)
		@Override
		public int select_dept_seq() {
			int result=0;
			try {
				conn = MyDBConnection.getConn();
				
				String sql =  " select seq_deptno.nextval "
							+ " from dual ";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {	//읽어올 시퀀스값이 있다면
					result = rs.getInt(1);
				}//end of while---
			} catch (SQLException e) {
				e.printStackTrace();	
			} finally {
				close();
			}
			return result;
		}//end of public int select_doc_seq()-------------

		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		

		// 품의서 테이블에 품의서를 넣고(이건 한번만실행), 결정테이블에 결재권자만큼 값을 insert해주는 메소드(트랜잭션) (최현우)
		@Override
		public int write_doc(List<Map<String, String>> paralist) {
			int result=0;
			int n =0;
			try {
				conn = MyDBConnection.getConn();
				conn.setAutoCommit(false);	//트랜잭션 처리를 위해서 autocommit을 false로 전환
				String sql =  " insert into tbl_approval_documents(documentseq, fk_empno, subject, content) "
						+ " values( ?, ?, ?, ?) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paralist.get(0).get("doc_seq"));
				pstmt.setString(2, paralist.get(0).get("empno"));
				pstmt.setString(3, paralist.get(0).get("doc_subject"));
				pstmt.setString(4, paralist.get(0).get("doc_content"));
				n = pstmt.executeUpdate();
				int n1=0;
				if(n == 1){ // 품의서 테이블에 품의서를 insert 하는것이 성공하였다면 아래 실행문 실행
					for(int i=0;i<paralist.size();i++) {
						sql = " insert into tbl_documents_decision(decision_no, fk_doc_no, levelno, fk_decision_empno,approval) "
							+ " values(seq_decision_no.nextval, ?, ?, ?,0) ";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, paralist.get(i).get("doc_seq"));
						pstmt.setString(2, paralist.get(i).get("levelno"));
						pstmt.setString(3, paralist.get(i).get("managerno"));
						n1+=pstmt.executeUpdate();
					}//end of for----
				}// end of if---
				if(n==1&&n1==paralist.size()) {	//위아래 쿼리문이 정상적으로 실행되었다면,
					conn.commit();
					result = 1;
				}
				else {	// 쿼리문중 하나라도 실행이 안되었더라면,
					conn.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.setAutoCommit(true); // 수동 commit 이였던것을 autocommit으로 전환.
				} catch (SQLException e) {}
				close();	//자원반납하는 메서드 호출
			}//end of try-catch----
			return result;
		}

		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		



		@Override
		//게시판을 출력해주는 메소드(최현우, 제목옆에 댓글 임선우)
		public List<BoardDTO> select_board(int page) {
			List<BoardDTO> boardList = new ArrayList<>();
			BoardDTO bdto = null;
				try {
					conn = MyDBConnection.getConn();
					
					String sql =   " select boardno"+
								   " ,case when length(subject) > 16 then substr(subject,1,5)||'..' "
								   + "else subject end as subject "+
								   " ,nickname"+
								   " ,to_char(registerday,'yy-mm-dd hh:mi') registerday"
								   + ",boardcnt"
								   + ",commentcnt "+
								   " from  "+
								   " ( "+
									" select rownum R,boardno,subject,nickname,registerday,boardcnt "+
								    " from tbl_board "+
									" )A "+
									" left join "+
									" ( "+
									" select fk_boardno, count(*) AS commentCnt "+
									" from tbl_comment "+
									" group by fk_boardno "+
									" )B "+
									" on A.boardno = B.fk_boardno "+
									" where R between ?*5-4 and ?*5 "+
									" order by boardno desc ";
							
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1,page);
					pstmt.setInt(2,page);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						bdto = new BoardDTO();
						
						bdto.setBoardno(rs.getInt(1));
						bdto.setSubject(rs.getString(2));
						bdto.setNickname(rs.getString(3));
						bdto.setRegisterday(rs.getString(4));
						bdto.setBoardcnt(rs.getInt(5));
						bdto.setCommentcnt(rs.getInt(6));
						
						//사원번호 읽어오는 것 추가
						
						boardList.add(bdto);
					}//end of while-----
					
				} catch (SQLException e) {
					System.out.println(">> 작성양식에 맞게 썼는지 확인해주세요 <<");
				} finally {
					close();
				}
			
			
			
			return boardList;
			
		}

		

		
		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		
		
		




		@Override
		//총 게시글 수를 알아오는 메소드(최현우)
		public int contentCnt() {
			int result = 0;
			try {
				conn = MyDBConnection.getConn();
				
				String sql = " select count(*) from tbl_board ";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
				}//end of while-----
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			return result;
		}//end of contentCnt()----





		

		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		
		@Override
		//글쓰기 메소드(최현우)
		public int board_write(Map<String, String> paraMap) {
			int n = 0;
			
			try {
				conn = MyDBConnection.getConn();
				
				String sql = "insert into tbl_board(boardno, nickname, subject, contents, boardpwd, fk_empno) "
							+ "values(seq_board_no.nextval, ?, ?, ?, ?, ?) ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, paraMap.get("nickname"));
				pstmt.setString(2, paraMap.get("subject"));
				pstmt.setString(3, paraMap.get("contents"));
				pstmt.setInt(4, Integer.parseInt(paraMap.get("boardpwd")));
				pstmt.setString(5, paraMap.get("empno"));
				
				n = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			} finally {
				close();
			}
			
			return n;
		}//end of board_write(Map<String, String> paraMap)




		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		
		
		

		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		
		
		
		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		
		
		
		


		
		
		
		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/

		
		
		
		
		
		
		
		
		
		//문서번호로 조회 메소드(최현우)
		@Override
		public AppDocumentDTO select_doc(Map<String,String> paraMap) {
			
			AppDocumentDTO addto = null;
			try {
				conn = MyDBConnection.getConn();
				
				String sql = " WITH "
							+ "V1 AS ("
							+ "       select   A.documentseq "
							+ "              , E1.empname "
							+ "              , A.subject "
							+ "              , A.content "
							+ "              , to_char(A.registerday,'yyyy-mm-dd') registerday "
							+ "       from tbl_approval_documents A JOIN tbl_employees E1 "
							+ "       on A.fk_empno = E1.empno "
							+ "       WHERE A.documentseq = ?) "
							+ ",V2 AS ( "
							+ "         select fk_doc_no "
							+ "              , C.fk_decision_empno "
							+ "              , C.approval "
							+ "              , E2.empname "
							+ "         from tbl_documents_decision C JOIN tbl_employees E2 "
							+ "         on C.fk_decision_empno = E2.empno "
							+ "         WHERE C.fK_doc_no = ?) "
							+ "select   V1.documentseq  "
							+ "       , V1.empname "
							+ "       , V1.subject "
							+ "       , V1.content "
							+ "       , V1.registerday "
							+ "from V1 JOIN V2 "
							+ "on V1.documentseq = V2.fk_doc_no "
							+ "where V2.fk_decision_empno = ? and V2.approval = 0"
							+ "order by documentseq desc ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1,Integer.parseInt(paraMap.get("doc_no")));
				pstmt.setInt(2,Integer.parseInt(paraMap.get("doc_no")));
				pstmt.setInt(3,Integer.parseInt(paraMap.get("empno")));
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					addto = new AppDocumentDTO();
					
					addto.setDocumentseq(rs.getInt(1));
					addto.setEmpname(rs.getString(2));
					addto.setSubject(rs.getString(3));
					addto.setContent(rs.getString(4));
					addto.setRegisterday(rs.getString(5));
				}//end of if---
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			
			return addto;
			
		}//end of AppDocumentDTO select_doc(int doc_no)-----

		
		
		
		
		
		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		


		// 문서번호를 입력받아서 승인해주는 메소드(최현우)
		@Override
		public int approval_doc(Map<String, String> paraMap) {
			int n = 0;
			try {
				conn = MyDBConnection.getConn();
				
				String sql = " update tbl_documents_decision "
							+"set approval = 1 "
							+"where fk_doc_no= ? and fk_decision_empno = ? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, paraMap.get("doc_no"));
				pstmt.setString(2, paraMap.get("empno"));
				n = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				if(e.getErrorCode()==1) { // 실패한다면
					n=-1;
				}
				else {
					e.printStackTrace();
				}
			} finally {
				close();
			}
			return n;
			
		}//end of int approval_doc(Map<String, String> paraMap)



		
		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		
		

		//나의 사원번호에 맞는 미결재서류목록을 보여주는 메소드(최현우)
		@Override
		public List<AppDocumentDTO> view_doc(int empno) {
			
			
			List<AppDocumentDTO> docList = new ArrayList<>();
			
			try {
				conn = MyDBConnection.getConn();
				
				String sql = " select V.documentseq "
						+ "         ,V.fk_empno "
						+ "         ,E.empname "
						+ "         ,E.empposition"
						+ "         ,to_char(V.registerday,'yyyy-mm-dd') registerday "
						+ "         ,V.subject "
						+ "         ,V.levelno "
						+ " from "
						+ " tbl_employees E "
						+ " JOIN "
						+ " ( "
						+ " select B.documentseq "
						+ "      ,B.fk_empno "
						+ "      ,B.registerday "
						+ "      ,B.subject "
						+ "      ,B.content "
						+ "      ,A.levelno "
						+ " from tbl_documents_decision A join tbl_approval_documents B "
						+ " on A.fk_doc_no = B.documentseq "
						+ " where A.approval = 0 and A.fk_decision_empno = ? "
						+ " ) V "
						+ " on V.fk_empno = E.empno ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, empno);
//				documentseq,fk_empno,empname,empposition,registerdy,subject,content,levelno
//				1문서번호 2작성자사번 3작성자명 4직급 5작성일자 6제목 7내용 8결재단계
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					AppDocumentDTO addto = new AppDocumentDTO();
						
					addto.setDocumentseq(rs.getInt(1));
					addto.setFk_empno(rs.getInt(2));
					addto.setEmpname(rs.getString(3));
					addto.setEmpposition(rs.getString(4));
					addto.setRegisterday(rs.getString(5));
					addto.setSubject(rs.getString(6));
					addto.setLevelno(rs.getInt(7));
					
					docList.add(addto);
				}//end of if---
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			
			return docList;
		}//end of view_doc(int empno)
			
		
			
			
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
			
			
			
			
			
		//문서번호를 알아오는 메소드(최현우)
		@Override
		public int select_emp_seq() {
			int result=0;
			try {
				conn = MyDBConnection.getConn();
				
				String sql =  " select seq_employees.nextval "
							+ " from dual ";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {	
					result = rs.getInt(1);
				}//end of while---
			} catch (SQLException e) {
				e.printStackTrace();	
			} finally {
				close();
			}
			return result;
		}//end of public int select_doc_seq()-------------


		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		

		//직원등록 메소드(최현우)
		@Override
		public int register_emp(EmployeesDTO edto) {
			int n1 = 0;
			try {
				conn = MyDBConnection.getConn();
				conn.setAutoCommit(false);	//트랜잭션 처리를 위해서 autocommit을 false로 전환
				
				String sql = " insert into tbl_employees(empno,empposition,empname,fk_deptno,salary,commission_pct,jubun,managerno)"
						+ "values(?, ?, ?, ?, ?, 0, ?, ?) ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, edto.getEmpno());
				pstmt.setString(2, edto.getEmpposition());
				pstmt.setString(3, edto.getEmpname());
				pstmt.setInt(4, edto.getFk_deptno());
				pstmt.setInt(5, edto.getSalary());
				pstmt.setLong(6, edto.getJubun());
				pstmt.setInt(7, edto.getManagerno());
				
				n1 = pstmt.executeUpdate();
				
				if(n1==1) {	//직원 테이블에 등록이 성공했다면, 회원 테이블에도 생성해주기.
					
					sql = " insert into tbl_member(memberid,memberpwd) "
						+ " values(?, ?) ";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, edto.getEmpno());
					pstmt.setString(2, "qWer1234$");
					
					n1 += pstmt.executeUpdate();
					if(n1==2) {	//둘다 성공했다면,
						conn.commit();
					}
					else {	//두번째 단계에서 실패했다면,
						conn.rollback();
						System.out.println(">>회원 등록이 실패하였습니다 !!<<");
					}
				}
				else { //첫번째 단계에서 실패했다면,
					conn.rollback();
					System.out.println(">> 직원 등록이 실패하였습니다 !! <<");
				}
				
				
			} catch (SQLException e) {
				if(e.getErrorCode()==1) { // 똑같은 사원을 입력하려고 했다면
					System.out.println(">> 상사 사번은 근무하고있는 사원번호로 입력해주세요! <<");
				}
				else {
					e.printStackTrace();
				}
			} finally {
				try {
					conn.setAutoCommit(true);	// 오토커밋으로 전환
				} catch (SQLException e) {}
				close();
			}
			return n1;
			
		}

		
		
		
		
	            /*
	            ---------------------------------------------------------------------------------------------------
	            ///////////////////////////////////////////////////////////////////////////////////////////////////
	            ///////////////////////////////////////////////////////////////////////////////////////////////////
	            ---------------------------------------------------------------------------------------------------
	            ///////////////////////////////////////////////////////////////////////////////////////////////////
	            ///////////////////////////////////////////////////////////////////////////////////////////////////
	            ---------------------------------------------------------------------------------------------------
	            */
	            
	      
	      



	      /*
	      ---------------------------------------------------------------------------------------------------
	      ///////////////////////////////////////////////////////////////////////////////////////////////////
	      ///////////////////////////////////////////////////////////////////////////////////////////////////
	      ---------------------------------------------------------------------------------------------------
	      ///////////////////////////////////////////////////////////////////////////////////////////////////
	      ///////////////////////////////////////////////////////////////////////////////////////////////////
	      ---------------------------------------------------------------------------------------------------
	      */
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      

	      
	      
	      
	      
	      

	      /*
	      ---------------------------------------------------------------------------------------------------
	      ///////////////////////////////////////////////////////////////////////////////////////////////////
	      ///////////////////////////////////////////////////////////////////////////////////////////////////
	      ---------------------------------------------------------------------------------------------------
	      ///////////////////////////////////////////////////////////////////////////////////////////////////
	      ///////////////////////////////////////////////////////////////////////////////////////////////////
	      ---------------------------------------------------------------------------------------------------
	      */
	      
		
		
		
		
		

	   /*
	   ---------------------------------------------------------------------------------------------------
	   ///////////////////////////////////////////////////////////////////////////////////////////////////
	   ///////////////////////////////////////////////////////////////////////////////////////////////////
	   ---------------------------------------------------------------------------------------------------
	   ///////////////////////////////////////////////////////////////////////////////////////////////////
	   ///////////////////////////////////////////////////////////////////////////////////////////////////
	   ---------------------------------------------------------------------------------------------------
	   */
	   
		
		
		
		
		
		
		
		
			
			// 월급을 조회하는 메소드 // (김민수)
			@Override
			public EmployeesDTO view_salary(MemberLoginDTO mdto) {

				EmployeesDTO edto = new EmployeesDTO();
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " select salary " +
									  " , commission_pct "+
								" from tbl_employees " +
								" where empno = ? ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,mdto.getMemberid());
					
					rs = pstmt.executeQuery();
					if(rs.next()) {
						
						edto.setSalary(rs.getInt("salary"));
						edto.setCommission_pct(rs.getInt("commission_pct"));
					}
					
				}catch(SQLException e) { 
					e.printStackTrace();
				} finally {// 자원반납
					close();
				}
				return edto;
			}// end of public MemberLoginDTO view_salary()


			/*
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			*/

			
			// *** 명세서를 메소드 *** //(김민수) 
			@Override
			public EmployeesDTO issue_salary(MemberLoginDTO mdto) {
				
				EmployeesDTO edto = new EmployeesDTO();
				try {
					conn = MyDBConnection.getConn();
					String sql = " select empno,empname,jubun, department_name, empposition "+ // 주민번호,
								" from tbl_employees E join tbl_department D "+
								" on E.FK_DEPTNO = D.DEPARTMENT_NO "+
								" where empno = ? ";
	   
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,edto.getEmpno());
					
					rs = pstmt.executeQuery();
					if(rs.next()) {
						edto = new EmployeesDTO();
						
						edto.setEmpno(rs.getInt("empno"));
						edto.setEmpname(rs.getString("empname"));
						edto.setJubun(rs.getInt("jubun"));
						edto.setDepartment_name(rs.getString("department_name")); 
						edto.setEmpposition(rs.getString("empposition"));
					   }
					
				}catch(SQLException e) { 
					e.printStackTrace();
				} finally {// 자원반납
					close();

				} 
				return edto;
			}// end of public MemberLoginDTO issue_salary(MemberLoginDTO mdto-----------------


			/*
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			*/



			//*** 재직증명서 조회 메소드 *** // (김민수)
			@Override
			public EmployeesDTO employment_menu(MemberLoginDTO mdto) {
				
				EmployeesDTO edto = null;
				
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " select empname, jubun, address, department_name, empposition, to_char(hiredate,'yyyy-mm-dd')AS hiredate "+
								 " from tbl_employees E join tbl_department D "+
						   	   	" on E.FK_DEPTNO = D.DEPARTMENT_NO "+
							   	" where empno = ?";
		   
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,mdto.getMemberid());
					
					rs = pstmt.executeQuery();
					
						if(rs.next()) {
							edto = new EmployeesDTO();
							
							edto.setEmpname(rs.getString("empname"));
							edto.setJubun(rs.getInt("jubun")); 
							edto.setAddress(rs.getString("address"));
							edto.setEmpposition(rs.getString("empposition"));
							edto.setDepartment_name(rs.getString("department_name"));
							edto.setHiredate(rs.getString("hiredate"));	
						   }

				}catch(SQLException e) { 
					e.printStackTrace();
				} finally {// 자원반납
					close();

				}
				return edto; 
				
			}// end of public void employment_menu(MemberLoginDTO mdto, Scanner sc)---------------


			






			// 게시판에서 조회할 번호를 선택하는 메소드(김민수)
			@Override
			public String board_search(MemberLoginDTO mdto) {
				
				BoardDTO bdto = new BoardDTO();
				
				String result = "";
				
				try {
					conn = MyDBConnection.getConn();
							
					String sql = "select * from tbl_board";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.executeQuery();
					
					if(rs.next()) {
						
						bdto = new BoardDTO();		
						
						bdto.setBoardno(rs.getInt("Boardno"));
						bdto.setContents(rs.getString("Contents"));
						bdto.setNickname(rs.getString("Nickname"));
						bdto.setSubject(rs.getString("Subject"));
					}

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
		            close();
		         }
				return result;
			}// end of public BoardDTO board_search(MemberLoginDTO mdto)------------



			
		
			/*
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			*/
		
			
			
			 // *** 닉네임으로 조회하는 메소드 *** //(김민수)
			@Override
			public List<BoardDTO> nickname_list(String s_nick) {
				
				List<BoardDTO> nickname_list = new ArrayList<BoardDTO>();
				BoardDTO bdto = null;
			
				try {
					conn = MyDBConnection.getConn();
							
					String sql = "select boardno,contents,nickname,subject,"
									+ " to_char(registerday,'yy-mm-dd') registerday "
							   + "from tbl_board "
							   + "where nickname like ? ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%"+s_nick+"%");
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {

						bdto = new BoardDTO();		
						
						bdto.setBoardno(rs.getInt("Boardno"));
						bdto.setContents(rs.getString("Contents"));
						bdto.setNickname(rs.getString("Nickname"));
						bdto.setSubject(rs.getString("Subject"));
						bdto.setRegisterday(rs.getString("Registerday"));
						
						nickname_list.add(bdto);
						}
					

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
		            close();
		         }
				return nickname_list;
			}


			/*
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			*/



			// *** 내용으로 조회하는 메소드 *** //(김민수)
			@Override
			public List<BoardDTO> contents_list(String s_contents) {
				
				List<BoardDTO> contents_list = new ArrayList<BoardDTO>();
				BoardDTO bdto = null;
				
				try {
					conn = MyDBConnection.getConn();
					
					String sql = "select boardno,contents,nickname,subject,"
							+ " to_char(registerday,'yy-mm-dd') registerday "
						    + "from tbl_board "
						    + "where contents like ? ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%" + s_contents + "%");
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						bdto = new BoardDTO();	
						
						bdto.setBoardno(rs.getInt("boardno"));
						bdto.setContents(rs.getString("contents"));
						bdto.setNickname(rs.getString("nickname"));
						bdto.setSubject(rs.getString("subject"));
						bdto.setRegisterday(rs.getString("registerday"));
						
						contents_list.add(bdto);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					close();
				}
				return contents_list;
			} // end of public List<BoardDTO> contents_list(String s_contents)
			
			
			
			
			
			
			
			
			
			
			
			
			// 사원평가 메소드(진민지)
			@Override
			public int emp_review(Map<String, Integer> paraMap) {
				
				int result = 0;
				
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " insert into tbl_jpr(jprno, fk_empno, attendance, commskill, achivement, proskill) "
							   + " values(seq_jprno.nextval, ?, ?, ?, ?, ?) ";	
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, paraMap.get("empno"));
					pstmt.setInt(2, paraMap.get("attendance"));
					pstmt.setInt(3, paraMap.get("commskill"));
					pstmt.setInt(4, paraMap.get("achivement"));
					pstmt.setInt(5, paraMap.get("proskill"));
					
					
					result = pstmt.executeUpdate();
					
				} catch (SQLException e) {
					if(e.getErrorCode()==1) { // 같은 사원을 평가하려고 한다면
						result=-1;
					}
					else {
						e.printStackTrace();
					}
				} finally {
					close();
				}
				

				return result;
			}//end of emp_review(Map<String, String> paraMap)


			
			
			
			
			
			/*
		      ---------------------------------------------------------------------------------------------------
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ---------------------------------------------------------------------------------------------------
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ---------------------------------------------------------------------------------------------------
		      */
			
			


			// 인사평가조회 메소드(진민지)
			@Override
			public JprDTO select_jpr(int fk_empno) {
				
				JprDTO empjpr = new JprDTO();
				
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " select fk_empno, attendance, commskill, achivement, proskill "
							   + " from tbl_jpr where fk_empno =?  "
							   + " order by jprno desc";   
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1,fk_empno);
					
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						empjpr.setFk_empno(rs.getInt("fk_empno"));
						empjpr.setAttendance(rs.getInt("attendance"));
						empjpr.setCommskill(rs.getInt("commskill"));
						empjpr.setAchivement(rs.getInt("achivement"));
						empjpr.setProskill(rs.getInt("proskill"));
						
					}
					
				} catch (SQLException e) { // SQL 오류로 인한 문제일 경우
					e.printStackTrace();
				} finally {
					close();
				}	
				return empjpr;
			} // end of JprDTO select_jpr(int fk_empno)




			
			
		      /*
		      ---------------------------------------------------------------------------------------------------
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ---------------------------------------------------------------------------------------------------
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ---------------------------------------------------------------------------------------------------
		      */
			
			
		
			
			
			
			


			// 글제목으로 게시판 조회하기 메소드 (진민지)
			@Override
			public List<BoardDTO> subject_list(String s_subject) {

				List<BoardDTO> subject_list = new ArrayList<BoardDTO>();
				BoardDTO bdto = null;
			
				try {
					conn = MyDBConnection.getConn();
							
					String sql = " select boardno,contents,nickname,subject"
							   + " , to_char(registerday,'yy-mm-dd') registerday "
							   + " from tbl_board "
							   + " where subject like ? ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%"+s_subject+"%");
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {

						bdto = new BoardDTO();		
						
						bdto.setBoardno(rs.getInt("Boardno"));
						bdto.setContents(rs.getString("Contents"));
						bdto.setNickname(rs.getString("Nickname"));
						bdto.setSubject(rs.getString("Subject"));
						bdto.setRegisterday(rs.getString("Registerday"));
						
						subject_list.add(bdto);
						}
					

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
		            close();
		         }
				return subject_list;
			}//end of List<BoardDTO> subject_list(String s_subject)



			
			
			  /*
		      ---------------------------------------------------------------------------------------------------
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ---------------------------------------------------------------------------------------------------
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ---------------------------------------------------------------------------------------------------
		      */
			  
			
			
			
			

			// 글번호로 게시판 조회하기 메소드 (진민지)
			@Override
			public List<BoardDTO> boardno_list(int boardno) {

				 List<BoardDTO> board_List = new ArrayList<BoardDTO>();
		         BoardDTO bdto = null;
		      
		         try {
		            conn = MyDBConnection.getConn();
		                  
		            String sql = " select boardno, contents, nickname, subject "
		                        + " ,to_char(registerday,'yy-mm-dd') registerday "
		                        + " from tbl_board "
		                        + " where boardno = ? ";
		            
		            pstmt = conn.prepareStatement(sql);
		            pstmt.setInt(1, boardno);
		            
		            rs = pstmt.executeQuery();
		            
		            while(rs.next()) {

		               bdto = new BoardDTO();      
		               
		               bdto.setBoardno(rs.getInt("Boardno"));
		               bdto.setContents(rs.getString("Contents"));
		               bdto.setNickname(rs.getString("Nickname"));
		               bdto.setSubject(rs.getString("Subject"));
		               bdto.setRegisterday(rs.getString("Registerday"));
		               
		               board_List.add(bdto);
		               }
		            

		         } catch (SQLException e) {
		            e.printStackTrace();
		         } finally {
		               close();
		            }
		         return board_List;
		      } //end of List<BoardDTO> boardList()
			
			
			
		
		
		
		
		
		
		
		
		
		
		
		


				
				







			/*
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			*/


	// 글번호로 게시물 1개만 조회하는 메소드(조상운)
			@Override
			public BoardDTO selectOneBoard(String boardno) {
				BoardDTO bdto = null;
				
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " select boardno, subject, nickname, boardpwd, registerday, boardcnt "
							   + " from tbl_board "
							   + " where boardno = ? ";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, boardno);
					
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						bdto = new BoardDTO();
						
						bdto.setBoardno(rs.getInt(1));
						bdto.setSubject(rs.getString(2));
						bdto.setNickname(rs.getString(3));
						bdto.setBoardpwd(rs.getInt(4));
						bdto.setRegisterday(rs.getString(5));
						bdto.setBoardcnt(rs.getInt(6));
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					close();
				}
				
				return bdto;
			}// end of public BoardDTO selectOneBoard(String boardno) --------------------
			




			/*
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			*/


			// 게시글을 삭제하는 메소드(조상운)
	         @Override
	         public int boardDelete(String boardno) {
	            int result = 0;
	            
	            try {
	               conn = MyDBConnection.getConn();
	               
	               String sql =  " delete from tbl_board "
	                        + " where boardno = ? ";
	               
	               pstmt = conn.prepareStatement(sql);
	               
	               pstmt.setString(1, boardno);
	               
	               result = pstmt.executeUpdate();
	               
	            } catch (SQLException e) {    // SQL의 문제로 에러가 발생하다면
	               e.printStackTrace();
	               result = -1;
	            } finally {
	               close();
	            }
	            
	            
	            return result;
	         }



			/*
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			*/


			// 게시물 1개들어가서 글내용 보는 메소드(조회수 1증가) (조상운) 
		      @Override
		      public BoardDTO viewOneNotice(String boardno, int memberid) {
		    	  BoardDTO bdto=null;
		    	  try {
		               conn = MyDBConnection.getConn();
		               String sql = " select pre_boardno"
		                     + " , case when length(pre_subject) > 16 then substr(pre_subject,1,5)||'..' else pre_subject end as pre_subject "
		                     + " , boardno"
		                     + " , subject"
		                     + " , contents"
		                     + " , next_boardno"
		                     + " , case when length(next_subject) > 16 then substr(next_subject,1,5)||'..' else next_subject end as next_subject "
		                     + " , fk_empno"
		                     + " , boardpwd "
		                     + " from "
		                     + " ( "
		                     + "select nvl(to_char(lag(boardno, 1) over(order by boardno desc)),'없음')  as pre_boardno "
		                     + "         , nvl(lag(subject, 1) over(order by boardno desc),'없음')  as pre_subject "
		                     + "         , boardno "
		                     + "         , subject "
		                     + "         , contents "
		                     + "         , nvl(to_char(lead(boardno, 1) over(order by boardno desc)),'없음') as next_boardno "
		                     + "         , nvl(lead(subject, 1) over(order by boardno desc),'없음')  as next_subject "
		                     + "         , fk_empno "
		                     + "         , boardpwd "
		                     + " from tbl_board "
		                     + " order by boardno "
		                     + " ) "
		                     + "where boardno = ? ";
		               
		               pstmt = conn.prepareStatement(sql);
		               pstmt.setString(1, boardno);
		               rs = pstmt.executeQuery();
		               
		               if(rs.next()) {
		            	  bdto = new BoardDTO();
		                  bdto.setPre_boardno(rs.getString(1));
		                  bdto.setPre_subject(rs.getString(2));
		                  bdto.setBoardno(rs.getInt(3));
		                  bdto.setSubject(rs.getString(4));
		                  bdto.setContents(rs.getString(5));
		                  bdto.setNext_boardno(rs.getString(6));
		                  bdto.setNext_subject(rs.getString(7));
		                  bdto.setFk_empno(rs.getInt(8));
		                  bdto.setBoardpwd(rs.getInt(9));
		               }// end of if--
		               if(bdto !=null && bdto.getFk_empno() != memberid) {
		                     sql = " update tbl_board set boardcnt = boardcnt + 1 "
		                        + " where boardno = ?";
		                     pstmt = conn.prepareStatement(sql);
		                     pstmt.setInt(1, bdto.getBoardno());
		                     pstmt.executeUpdate();
		              }//end of if
		            } catch (SQLException e) {
		               e.printStackTrace();
		            } finally {
		               close();
		            }
		         
		         return bdto;
		      }// end of public BoardDTO viewOneNotice(String boardno, int memberid) -----------


		      
		      
		      
	// 댓글을 보여주는 메소드(조상운)
      @Override
      public void viewComment(int boardno) {
         List<CommentDTO> commentList = new ArrayList<>();
         try {
               conn = MyDBConnection.getConn();
               
               String sql = " select comment_contents, nickname, "
               			+ "to_char(registerday,'yy-mm-dd hh:mi') registerday"
                     + " from tbl_comment "
                     + " where fk_boardno = ? ";
               
               pstmt = conn.prepareStatement(sql);
               pstmt.setInt(1, boardno);
               
               rs = pstmt.executeQuery();
               
               while(rs.next()) {
                     CommentDTO cdto = new CommentDTO();
                  cdto.setComment_contents(rs.getString(1)); 
                  cdto.setNickname(rs.getString(2)); 
                  cdto.setRegisterday(rs.getString(3)); 
                  
                  commentList.add(cdto);
               }//end of while-----
               
               int cnt = 0;
               if(commentList.size()>0)
                  for(CommentDTO cdto : commentList) {
                     cnt++;
                     TotalCtrll tctrl = new TotalCtrll();
                     if(cnt == 1) {  
                        System.out.println(tctrl.format(">>댓글<<",56)+"\n"
                                       +tctrl.format("댓글내용          닉네임              작성일자",56)+"\n");
                        System.out.println(tctrl.format("-------------------------------------",56));
                     }
                        System.out.println(tctrl.format(cdto.getComment_contents()+"          "
                        								+cdto.getNickname()+"              "
                        								+cdto.getRegisterday(),56)+"\n");
                      if(cnt == commentList.size()) {
                    	  System.out.println(tctrl.format("-------------------------------------",56));
                      }
                  }//end of for--
               
               else {
                     TotalCtrll a = new TotalCtrll();
                     System.out.println(a.format(">> 댓글없음 <<",58));
               }
               
            } catch (SQLException e) {
               e.printStackTrace();
            } finally {
               close();
            }
         
      }// end of public void viewComment(int boardno)


			
			 /*
		      ---------------------------------------------------------------------------------------------------
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ---------------------------------------------------------------------------------------------------
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ///////////////////////////////////////////////////////////////////////////////////////////////////
		      ---------------------------------------------------------------------------------------------------
		      */



	// 내가 작성한 게시물을 수정하는 메소드(조상운)
			@Override
			public int updateMynotice(Map<String, String> paraMap) {
				int result = 0;
				
				try {
					conn = MyDBConnection.getConn();
					
					String sql =  " update tbl_board "
								+ " set subject = ? , contents =? "
								+ " where boardno = ? ";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, paraMap.get("subject"));
					pstmt.setString(2, paraMap.get("contents"));
					pstmt.setString(3, paraMap.get("str_boardno"));
					
					result = pstmt.executeUpdate();
					
				} catch (SQLException e) { 	// SQL의 문제로 에러가 발생하다면
					e.printStackTrace();
					result = -1;
				} finally {
					close();
				}
				
				
				return result;
			}// end of public int updateMynotice(Map<String, String> paraMap)



		
		
		
		
		
		
			
			
			// 공지사항 수정하는 메소드(조상운)
		      @Override
		      public int updateNotice(String notice_contents) {
		         int result = 0;
		         
		         try {
		            conn = MyDBConnection.getConn();
		            
		            String sql =  " update tbl_notice "
		                     + " set notice_contents = ? ";
		            
		            pstmt = conn.prepareStatement(sql);
		            
		            pstmt.setString(1, notice_contents);
		            
		            result = pstmt.executeUpdate();
		            
		         } catch (SQLException e) {    // SQL의 문제로 에러가 발생하다면
		            e.printStackTrace();
		            result = -1;
		         } finally {
		            close();
		         }
		         
		         
		         return result;
		      }// end of public int updateNotice(String notice_contents)
		      
		      
		      
		      
		      
		      
		      
		      
		   // 공지사항 보여주는 메소드
		      @Override
		      public String showNotice() {
		         String result = "";
		         
		         try {
		            conn = MyDBConnection.getConn();
		            
		            String sql = " select notice_contents from tbl_notice ";
		            
		            pstmt = conn.prepareStatement(sql);
		            
		            rs = pstmt.executeQuery();
		            
		            if(rs.next()) {
		               result = rs.getString(1);
		            }
		            
		         } catch (SQLException e) {
		            e.printStackTrace();
		         } finally {
		            close();
		         }
		         
		         
		         return result;
		      }// end of public String showNotice() -------------------------------------------




		      
		      
		      
		      
		      
		      
			@Override
			// 로그인한 사원이 미결재한서류 개수 메소드(최현우)
			public int cnt_doc(MemberLoginDTO mdto) {
				
				int cnt=0;
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " select count(*) "
								+ " from "
								+ " tbl_employees E "
								+ " JOIN "
								+ " ( "
								+ " select B.documentseq "
								+ "      ,B.fk_empno "
								+ "      ,B.registerday "
								+ "      ,B.subject "
								+ "      ,B.content "
								+ "      ,A.levelno "
								+ " from tbl_documents_decision A join tbl_approval_documents B "
								+ " on A.fk_doc_no = B.documentseq "
								+ " where A.approval = 0 and A.fk_decision_empno = ? "
								+ " ) V "
								+ " on V.fk_empno = E.empno ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, mdto.getMemberid());
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						cnt = rs.getInt(1);
					}//end of if---
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					close();
				}
				
				return cnt;
				
			}





			@Override
			//내가쓴 문서를 조회해주는 메소드(최현우)
			public List<AppDocumentDTO> me_write_doc(int empno) {
				List<AppDocumentDTO> aplist = new ArrayList<>();
				
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " select V.documentseq "
							+ "         ,V.fk_empno "
							+ "         ,E.empname "
							+ "         ,E.empposition"
							+ "         ,to_char(V.registerday,'yyyy-mm-dd') registerday "
							+ "         ,V.subject "
							+ "         ,V.levelno "
							+ " from "
							+ " tbl_employees E "
							+ " JOIN "
							+ " ( "
							+ " select B.documentseq "
							+ "      ,B.fk_empno "
							+ "      ,B.registerday "
							+ "      ,B.subject "
							+ "      ,B.content "
							+ "      ,A.levelno "
							+ " from tbl_documents_decision A join tbl_approval_documents B "
							+ " on A.fk_doc_no = B.documentseq "
							+ " where A.approval = 0 and B.fk_empno = ? "
							+ " ) V "
							+ " on V.fk_empno = E.empno ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, empno);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						AppDocumentDTO addto = new AppDocumentDTO();
							
						addto.setDocumentseq(rs.getInt(1));
						addto.setFk_empno(rs.getInt(2));
						addto.setEmpname(rs.getString(3));
						addto.setEmpposition(rs.getString(4));
						addto.setRegisterday(rs.getString(5));
						addto.setSubject(rs.getString(6));
						addto.setLevelno(rs.getInt(7));
						
						aplist.add(addto);
					}//end of if---
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					close();
				}
				
				return aplist;
				
				
			}//end of List<AppDocumentDTO> me_write_doc(int empno)-----




			@Override
			//부서신설메소드(최현우)
			public int new_department(Map<String, String> paraMap) {
				int n =0;
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " insert into tbl_department(department_no,department_name,department_tel,fk_dep_manager_no) "
								+ "values(?, ?, ?, ?) ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, paraMap.get("department_no"));
					pstmt.setString(2, paraMap.get("department_name"));
					pstmt.setString(3, paraMap.get("department_tel"));
					pstmt.setString(4, paraMap.get("dep_managerno"));
					n = pstmt.executeUpdate();
				} catch (SQLException e) {
					if(e.getErrorCode()==1) { // 이미 존재하는 부서를 입력한 경우
						System.out.println("\n>> 이미 존재하는 부서입니다!! <<\n");
						n=-1;
					}
					else {
						e.printStackTrace();
					}
				} finally {
					close();
				}
				return n;
			}//end of new_department(Map<String, String> paraMap)----





			@Override
			//회원이용정지메소드(최현우)
			public int ban(Map<String,String> paraMap) {
				int n =0;
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " update tbl_member set board_stop = 1,comments = ? "
								+ " where memberid = ?";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, paraMap.get("comments"));
					pstmt.setString(2, paraMap.get("memberid"));
					n = pstmt.executeUpdate();
					
				} catch (SQLException e) {
					if(e.getErrorCode()==1) { // 실패한다면
						n=-1;
					}
					else {
						e.printStackTrace();
					}
				} finally {
					close();
				}
				return n;
			}// end of do-while---





			@Override
			//회원이용정지 해제메소드(최현우)
			public int unban(int memberid) {
				int n =0;
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " update tbl_member set board_stop = 0"
								+ " where memberid = ?";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, memberid);
					n = pstmt.executeUpdate();
					
				} catch (SQLException e) {
					if(e.getErrorCode()==1) { // 실패한다면
						n=-1;
					}
					else {
						e.printStackTrace();
					}
				} finally {
					close();
				}
				return n;
			}//end of unban(int memberid)--





			@Override
			//게시판관리자 글삭제메소드(최현우)
			public int super_delete_board(int boardno) {
				int n =0;
				 try {
			            conn = MyDBConnection.getConn();
			         
			            String sql = " delete from tbl_board "
			                       + " where boardno = ? ";
			            pstmt = conn.prepareStatement(sql);
			            pstmt.setInt(1, boardno); 
			            
			            n = pstmt.executeUpdate();
			         }catch(SQLException e) {
			            e.printStackTrace();
			            n = -1;
			         }finally {
			            close(); // 자원반납하기 
			         }//end of try-catch--------
			         
			         return n;
			}//end of super_delete_board(int boardno)-----------





			@Override
			//이용정지회원내역 조회메소드(최현우)
			public List<MemberLoginDTO> select_ban() {
				MemberLoginDTO mdto = null;
				List<MemberLoginDTO> banlist = new ArrayList<>();
				try {
					conn = MyDBConnection.getConn();
					
					String sql = " select memberid,comments,board_stop "
								+ " from tbl_member "
								+ " where board_stop =1 ";
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					int cnt = 0;
					while(rs.next()) {
						mdto = new MemberLoginDTO();
						mdto.setMemberid(rs.getInt(1));
						mdto.setComments(rs.getString(2));
						mdto.setBoard_stop(rs.getInt(3));
						banlist.add(mdto);
					}//end of while---
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					close();
				}
				return banlist;
			
				
				
			}//end of MemberLoginDTO





			









			
		      
		      
		      
		      
			
		
		
		
		
		
		
		
		
		
		
		/*
			글번호   글제목   작성자명  작성일자   조회수
			----------------------------------
			  1   안녕하세요   최현우  22-07-28  1
			----------------------------------
						  1/1
			총 게시물 갯수:1			  
		*/
		
		
		
			// 댓글작성하는 메소드(조상운)
	         @Override
	         public int insertComment(Map<String, String> paraMap) {
	            
	            int n = 0;
	            
	            try {
	               conn = MyDBConnection.getConn();
	               
	               String sql = "insert into tbl_comment(commentno, nickname, comment_contents, fk_boardno) "
	                        + "values(seq_comment_no.nextval, ?, ?, ?) ";
	               pstmt = conn.prepareStatement(sql);
	               
	               pstmt.setString(1, paraMap.get("nickname"));
	               pstmt.setString(2, paraMap.get("comment_contents"));
	               pstmt.setString(3, paraMap.get("fk_boardno"));
	               
	               n = pstmt.executeUpdate();
	               
	            } catch (SQLException e) {
	               e.printStackTrace();
	               return -1;
	            } finally {
	               close();
	            }
	            
	            return n;
	         }
		
		
		
		
		
		
		
		
	         
	         
	         
	         

	     	
	     	
	     	
	     	
	     	/*
	     	         //////////////////////////////////채용관리/////////////////////////////////////////
	     	---------------------------------------------------------------------------------------------------
	     	///////////////////////////////////////////채용관리///////////////////////////////////////////////////
	     	///////////////////////////////////////////채용관리//////////////////////////////////////////////////
	     	---------------------------------------------------------------------------------------------------

	     	*/
	     	

		 		// 신청한 휴가 날짜가 휴가테이블에 있는 값과 중복되는걸 잡아주는 메소드(조상운)
		         @Override
		         public int existvday(Map<String, String> paraMap) {
		             int result = 0;
		             int flag = 0;
		             Calendar c1 = Calendar.getInstance();
		             Calendar c2 = Calendar.getInstance();
		             Calendar c3 = Calendar.getInstance();
		             Calendar c4 = Calendar.getInstance();
		             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		             
		           try {
		                  df.setLenient(false);
		                  c3.setTime(df.parse(paraMap.get("startday")));
		                  c4.setTime(df.parse(paraMap.get("endday")));
		            } catch (ParseException e1) {
		               System.out.println("입력받은 날짜가 date타입으로 안고쳐짐");
		               e1.printStackTrace();
		            }
		             
		             try {
		                     conn = MyDBConnection.getConn();
		                        
		                     String sql  = " select STARTVDAY, ENDVDAY "
		                                 + " from tbl_vacation "
		                                 + " where fk_empno = ? ";
		                              
		                               pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		                               
		                               pstmt.setString(1, paraMap.get("fk_empno"));
		                               
		                               rs = pstmt.executeQuery();
		                               
		                    
		                        
		                      outer:
		                      while(rs.next() || flag == 0) {
		                                  
		                         String startday = rs.getString(1);
		                         String endday = rs.getString(2);
		                               df.setLenient(false);
		                               c1.setTime(df.parse(startday)); 
		                               c2.setTime(df.parse(endday));
		                      
		                               
		                                
		                              while(c1.before(c2) || c1.equals(c2)) {
		                                 
		                                 if(c1.equals(c3)||c1.equals(c4)) {// 시작날짜가 겹칠수도 있고 끝날짜가 겹칠수도 있으니깐
		                                    flag++;
		                                    break outer;
		                                 }
		                                 c1.add(Calendar.DATE, 1);
		                              }
		                               
		                     }// end of if(result==1) { // 누적 휴가신청일 확인하기
		                     
		                     
		                     
		                      if(flag > 0) {
		                          result = -1;
		                        }
		                    
		                  }catch (SQLException e) { 
		                     
		                  } catch (ParseException e) { 
		                        e.printStackTrace();
		                     }
		             
		                finally {
		                     close();
		                 }
		            
		            return result;
		         }//end of public int existvday(Map<String, String> paraMap)

			
			
			
			
			
			
		         
		         
		         
		         

		     	
		     	
		     	
		     	
		     	/*
		     	         //////////////////////////////////채용관리/////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------
		     	///////////////////////////////////////////채용관리///////////////////////////////////////////////////
		     	///////////////////////////////////////////채용관리//////////////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------

		     	*/
		     	
		     		// 입사지원자 등록하기(인사담당자)(임선우)
		     		@Override
		     		public int appliRegister(ApplicantDTO apdto) {
		     			int result = 0;
		     	         
		     	        try {
		     	            conn = MyDBConnection.getConn();
		     	            
		     	            String sql = " insert into tbl_job_applicant(applicantno, applicant, fk_hope_depno, mobile, address, registerdate) "
		     	                       + " values(seq_applicantNO.nextval, ?, ?, ?, ?, ?) ";
		     	            
		     	            pstmt = conn.prepareStatement(sql);

		     	            pstmt.setString(1, apdto.getApplicant());
		     	            pstmt.setInt(2, apdto.getFk_hope_depno());
		     	            pstmt.setString(3, apdto.getMobile());
		     	            pstmt.setString(4, apdto.getAddress());
		     	            pstmt.setString(5, apdto.getRegisterdate());
		     	            
		     	            result = pstmt.executeUpdate();

		     	         }catch (SQLException e) { 
		     	            
		     	            System.out.println("\n------ 형식 예시 --------\n"
		     	                         + "1.지원자명    : 김땡땡 \n"
		     	                         + "2.희망부서번호 : 9\n"
		     	                         + "3.연락처     : 010-0000-0000\n"
		     	                         + "4.주소      : 서울특별시 마포구\n"
		     	                         + "5.지원일자   : 2022-01-01");
		     	            
		     	         } finally {
		     	            close();
		     	         }
		     	         
		     	         return result;
		     		}// end of public int appliRegister(ApplicantDTO apdto)------------

		     	
		     	
		         
		         
		     	
		         /*
		     	---------------------------------------------------------------------------------------------------
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------
		     	*/
		     	
		         
		         
		         
		         
		     	// 입사지원서조회(올해상반기지원자조회, 올해하반기지원자조회, 이번연도지원자현황)(인사담당자)(임선우)
		     	@Override
		     	public List<ApplicantDTO> searchAppliList(String menuNo) {
		     		
		     		List<ApplicantDTO> searchAppliList = new ArrayList<>();
		     	         
		             try {
		                 conn = MyDBConnection.getConn();
		                 
		                 String sql = " select applicantno, applicant, fk_hope_depno, mobile, address, to_char(registerdate, 'yyyy-mm-dd') AS registerdate "+
		                           " from tbl_job_applicant ";
		                 if("1".equals(menuNo)) {
		                    // 올해상반기지원자조회
		                    sql += " where to_char(registerdate,'yyyy') = to_char(sysdate, 'yyyy') and to_char(registerdate,'mm') between 1 and 6\n  ";
		                 }
		                 else if("2".equals(menuNo)) {
		                    // 올해하반기지원자조회
		                    sql += " where to_char(registerdate,'yyyy') = to_char(sysdate, 'yyyy') and to_char(registerdate,'mm') between 7 and 12\n "; 
		                 }
		                 else if("3".equals(menuNo)) {
		                    // 이번연도지원자조회
		                    sql += " where to_char(registerdate,'yyyy') = extract(year from sysdate)\n "; 
		                 
		                 }
		                 pstmt = conn.prepareStatement(sql);
		                 
		                 rs = pstmt.executeQuery();
		                 
		                 while(rs.next()) {
		                    
		                    ApplicantDTO appli = new ApplicantDTO();
		                    
		                    appli.setApplicantno(rs.getInt(1));
		                    appli.setApplicant(rs.getString(2));
		                    appli.setFk_hope_depno(rs.getInt(3));
		                    appli.setMobile(rs.getString(4));
		                    appli.setAddress(rs.getString(5));
		                    appli.setRegisterdate(rs.getString(6));
		                    
		                    searchAppliList.add(appli);
		                    
		                 }// end of while----------------------------------------------------
		                 
		              } catch (Exception e) {
		                 e.printStackTrace();
		              } finally {
		                 close();
		              }
		              return searchAppliList;
		     	
		     	}// end of public List<ApplicantDTO> searchAppliList(String menuNo)-----
		     	
		     	
		     	
		         
		         
		     	
		         /*
		     	---------------------------------------------------------------------------------------------------
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------
		     	*/
		     	
		         
		         
		         
		         
		     	// 특정 입사지원자조회(인사담당자)(임선우)
		     	@Override
		     	public ApplicantDTO searchAppli(int n_apcNo) {
		     		
		     		 ApplicantDTO appli = null;
		              
		              try {
		                 conn = MyDBConnection.getConn();
		                 
		                 String sql = " select applicantno, applicant, fk_hope_depno, mobile, address, to_char(registerdate, 'yyyy-mm-dd') AS registerdate "+
		                           " from tbl_job_applicant "+
		                           " where applicantno = ? ";
		                       
		                 pstmt = conn.prepareStatement(sql);
		                 pstmt.setInt(1, n_apcNo);
		                 
		              
		                 
		                 rs = pstmt.executeQuery();
		                 
		                 if(rs.next()) {
		                    
		                    appli = new ApplicantDTO();
		                    
		                    appli.setApplicantno(rs.getInt(1));
		                    appli.setApplicant(rs.getString(2));
		                    appli.setFk_hope_depno(rs.getInt(3));
		                    appli.setMobile(rs.getString(4));
		                    appli.setAddress(rs.getString(5));
		                    appli.setRegisterdate(rs.getString(6));
		                    
		                 }// end of if----------------------------------------------------
		                 
		              } catch (Exception e) {
		                 e.printStackTrace();
		              } finally {
		                 close();
		              }
		              return appli;
		     	}// end of public ApplicantDTO searchAppli(int n_apcNo)
		     	
		     	
		     	
		         
		         
		     	
		         /*
		     	---------------------------------------------------------------------------------------------------
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	///////////////////////////////////////////////////////////////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------
		     	*/
		     	
		         
		         
		         
		         
		     			
		     	/*
		         		//////////////////////////////////사원정보/////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------
		     	///////////////////////////////////////////사원정보///////////////////////////////////////////////////
		     	///////////////////////////////////////////사원정보//////////////////////////////////////////////////
		     	---------------------------------------------------------------------------------------------------
		     	 */	
		     	 
		     	 // 전체사원조회-관리자 빼고(인사당담자)(임선우)
		          @Override
		          public List<EmployeesDTO> allEmpSearch() {
		          
		          
		          List<EmployeesDTO> empList = new ArrayList<>();
		          
		          try {
		             conn = MyDBConnection.getConn();
		             
		             String sql = " select EMPNO "
		                   + " ,EMPPOSITION "
		                   + " ,EMPNAME "
		                   + " ,FK_DEPTNO "
		                   + " ,ADDRESS "
		                   + " ,SALARY "
		                   + " ,COMMISSION_PCT "
		                   + " ,EMAIL "
		                   + " ,JUBUN "
		                   + " ,HIREDATE "
		                   + " ,MANAGERNO \n "+
		                       " from tbl_employees"
		                       + " where salary != 0 ";
		             
		             pstmt = conn.prepareStatement(sql);
		             
		             rs = pstmt.executeQuery();
		             
		             while(rs.next()) {
		                EmployeesDTO edto = new EmployeesDTO();
		                
		                edto.setEmpno(rs.getInt(1)); 
		                edto.setEmpposition(rs.getString(2));
		                edto.setEmpname(rs.getString(3));
		                edto.setFk_deptno(rs.getInt(4));
		                edto.setAddress(rs.getString(5));
		                edto.setSalary(rs.getInt(6));
		                edto.setCommission_pct(rs.getFloat(7));
		                edto.setEmail(rs.getString(8));
		                edto.setJubun(rs.getInt(9));
		                edto.setHiredate(rs.getString(10));
		                edto.setManagerno(rs.getInt(11));
		                
		                empList.add(edto);
		                
		             }// end of while---------------------------------------------------
		                         
		          } catch (Exception e) {
		             System.out.println("죄송합니다. 시스템 상 오류로 취소되었습니다.");
		          } finally {
		             close();
		          }
		          return empList;
		       }// end of public List<EmployeesDTO> allEmpSearch() -------------
		      	
		      	
		      	
		          
		          
		      	
		          /*
		      	---------------------------------------------------------------------------------------------------
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	---------------------------------------------------------------------------------------------------
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	---------------------------------------------------------------------------------------------------
		      	*/
		      	
		          
		          
		          
		          
		      	 // 특정사원정보 출력(인사당담자)(임선우)
		          @Override
		          public EmployeesDTO empInfo(String fk_empno) {
		             
		             EmployeesDTO edto = new EmployeesDTO();
		             
		             try {
		            	 conn = MyDBConnection.getConn();
		                
		     	            String sql = " select EMPNO "
		     	                     + " ,EMPPOSITION "
		     	                     + " ,EMPNAME "
		     	                     + " ,FK_DEPTNO "
		     	                     + " ,ADDRESS "
		     	                     + " ,SALARY "
		     	                     + " ,COMMISSION_PCT "
		     	                     + " ,EMAIL "
		     	                     + " ,JUBUN "
		     	                     + " ,HIREDATE "
		     	                     + " ,MANAGERNO \n "
		     	                     + " from tbl_employees "
		     	                     + "where empno = ?";
		                          
		                 pstmt = conn.prepareStatement(sql);
		                 pstmt.setString(1, fk_empno);
		                
		                 rs = pstmt.executeQuery();
		                
		                 if(rs.next()) {
		                   
		     	               edto.setEmpno(rs.getInt(1)); 
		     	               edto.setEmpposition(rs.getString(2));
		     	               edto.setEmpname(rs.getString(3));
		     	               edto.setFk_deptno(rs.getInt(4));
		     	               edto.setAddress(rs.getString(5));
		     	               edto.setSalary(rs.getInt(6));
		     	               edto.setCommission_pct(rs.getFloat(7));
		     	               edto.setEmail(rs.getString(8));
		     	               edto.setJubun(rs.getInt(9));
		     	               edto.setHiredate(rs.getString(10));
		     	               edto.setManagerno(rs.getInt(11));
		                   
		                 }// end of while---------------------------------------------------
		                            
		             } catch (Exception e) {
		                System.out.println("죄송합니다. 시스템상 오류로 취소되었습니다.");
		             } finally {
		                close();
		             }
		             return edto;
		          }// end of public EmployeesDTO empInfo(String fk_empno)
		      	
		      	
		      	
		          
		          
		      	
		          /*
		      	---------------------------------------------------------------------------------------------------
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	---------------------------------------------------------------------------------------------------
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	---------------------------------------------------------------------------------------------------
		      	*/
		      	
		          
		          
		          
		          
		      			
			      //  전체사원조회, 사원번호조회, 특정부서조회, 인사평가기준, 입사일자기준(임선우)
			      @Override
			      public List<EmployeesDTO> searchEmp(String menuNo, Map<String, String> paraMap) {
			    	 int cnt =0;
			         List<EmployeesDTO> empList = new ArrayList<>();
			         Scanner sc = new Scanner(System.in);
			         String empno = paraMap.get("empno");
			         String fk_deptno  = paraMap.get("fk_deptno");
			         String jrpgrade = paraMap.get("jrpgrade");
			         String fromDate = paraMap.get("fromDate");
			         String toDate = paraMap.get("toDate");
			        
			         
			         try {
			            conn = MyDBConnection.getConn();
			            
			            String sql = " select EMPNO "
			                     + " ,EMPPOSITION "
			                     + " ,EMPNAME "
			                     + " ,FK_DEPTNO "
			                     + " ,ADDRESS "
			                     + " ,SALARY "
			                     + " ,COMMISSION_PCT "
			                     + " ,EMAIL "
			                     + " ,JUBUN "
			                     + " ,HIREDATE "
			                     + " ,MANAGERNO \n ";
			               
			            if("1".equals(menuNo)) { // 전체사원조회
			               sql += " from tbl_employees "
			               		+ " where salary != 0 ";// 관리자들 빼기 위해서 넣은 조건 
			               pstmt = conn.prepareStatement(sql);
			            }
			            else if("2".equals(menuNo) && cnt==0) {// 사원번호조회
			               sql += " from tbl_employees "
			                     + "where empno = ?";
			                      
			               pstmt = conn.prepareStatement(sql);
			               pstmt.setString(1, empno);
			            }
			            
			            else if("3".equals(menuNo)) {// 특정부서조회
			               sql += " from tbl_employees " 
			                    +" where fk_deptno = ?";
			               pstmt = conn.prepareStatement(sql);
			               pstmt.setString(1, fk_deptno);
			            }
			            
			            else if("4".equals(menuNo)) {// 인사평가기준
			               sql +=    " ,grade "+
			                     " from "+
			                     " ( "+
			                     " with "+
			                     " A as "+
			                     " ( "+
			                     " select fk_empno , case when (attendance+commskill+achivement+proskill) = 40 then 'S' "+
			                     "                    when (attendance+commskill+achivement+proskill) > 30 then 'A' "+
			                     "                    when (attendance+commskill+achivement+proskill) > 20 then 'B' "+
			                     "                    when (attendance+commskill+achivement+proskill) > 10 then 'C' "+
			                     "                    else 'D' end GRADE\n"+
			                     "from tbl_jpr "+
			                     " ) "+
			                     ", "+
			                     "B as "+
			                     " ( "+
			                     " select * "+
			                     " from tbl_employees "+
			                     " ) "+
			                     " select B.empno, empposition, empname, fk_deptno, address, salary, commission_pct, email, jubun, hiredate, managerno, GRADE "+
			                     " FROM A Join B "+
			                     " ON A.fk_empno = B.empno "+
			                     " )V"
			                     + " where grade = ? ";

			               pstmt = conn.prepareStatement(sql);
			               pstmt.setString(1, jrpgrade);
			            }
		        
				       else {// "5".equals(menuNo) 입사일자기준
				           sql += " from tbl_employees "
				                +" where to_char(hiredate, 'yyyy-mm') between ? and ? and salary != 0 ";
				                
				           pstmt = conn.prepareStatement(sql);
				           pstmt.setString(1, fromDate);
				           pstmt.setString(2, toDate);
				        }
		        
			            rs = pstmt.executeQuery();
			            while(rs.next()) {
				           EmployeesDTO edto = new EmployeesDTO();
				           cnt++;
				           edto.setEmpno(rs.getInt(1)); 
				           edto.setEmpposition(rs.getString(2));
				           edto.setEmpname(rs.getString(3));
				           edto.setFk_deptno(rs.getInt(4));
				           edto.setAddress(rs.getString(5));
				           edto.setSalary(rs.getInt(6));
				           edto.setCommission_pct(rs.getFloat(7));
				           edto.setEmail(rs.getString(8));
				           edto.setJubun(rs.getInt(9));
				           edto.setHiredate(rs.getString(10));
				           edto.setManagerno(rs.getInt(11));
				           
				           empList.add(edto);
				           
			            }// end of while---------------------------------------------------
				     }catch(SQLException e) {
						if(e.getErrorCode() == 1722) {
							System.out.println(">> 올바른 정보를 입력하세요");
						}
						else {
							e.printStackTrace();
						}
				     } catch (Exception e) {
				        e.printStackTrace();
				        System.out.println("죄송합니다. 시스템상 오류로 취소되었습니다.");
				     } finally {
				        close();
				     }
				     return empList;
			      }// end of public List<EmployeesDTO> searchEmp(String menuNo)---------------


					
					
				/*
				---------------------------------------------------------------------------------------------------
				///////////////////////////////////////////////////////////////////////////////////////////////////
				///////////////////////////////////////////////////////////////////////////////////////////////////
				---------------------------------------------------------------------------------------------------
				///////////////////////////////////////////////////////////////////////////////////////////////////
				///////////////////////////////////////////////////////////////////////////////////////////////////
				---------------------------------------------------------------------------------------------------
				*/
					

		          
		          
		          
		          
		      	
		          // 사원정보 수정(인사당담자)(임선우)
		          @Override
		          public int updateEmp(Map<String, String> paraMap) {
		             
		             int result = 0;
		             
		             try {
		                conn = MyDBConnection.getConn();
		                
		                String sql = " update tbl_employees set EMPPOSITION = ?, "
		     		               + " FK_DEPTNO=?, SALARY=?, COMMISSION_PCT=?,"
		     		               + "  MANAGERNO=? "
		     		               + " where empno = ? ";

		                   pstmt = conn.prepareStatement(sql);
		                         
		                   pstmt.setString(1, paraMap.get("empposition"));
		                   pstmt.setString(2, paraMap.get("fk_deptno"));
		                   pstmt.setString(3, paraMap.get("salary"));
		                   pstmt.setString(4, paraMap.get("commission_pct"));
		                   pstmt.setString(5, paraMap.get("managerno"));
		                   pstmt.setString(6, paraMap.get("empno"));
		                   
		                   result = pstmt.executeUpdate();
		                   
		             } catch (SQLException e) {
		                System.out.println("죄송합니다. 시스템상 오류로 취소되었습니다.");
		             } finally {
		                close();
		             }
		             return result;
		          }// public int updateEmp(Map<String, String> paraMap) ---------------------
		      	
		      	
		      	
		          
		          
		      	
		          /*
		      	---------------------------------------------------------------------------------------------------
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	---------------------------------------------------------------------------------------------------
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	///////////////////////////////////////////////////////////////////////////////////////////////////
		      	---------------------------------------------------------------------------------------------------
		      	*/
		      	
		          
		          
		          
		          
		      				
		             // 사원정보 삭제(인사담당자)(임선우)
		     	    @Override
		     	    public int emInfoDelete(String empno) {
		     	    	Scanner sc = new Scanner(System.in);
		     	    	int result = 0;
		     	         try {
		     	            conn = MyDBConnection.getConn();
		     	            
		     	            String sql = " select EMPNO "
		     	                  + " ,EMPPOSITION "
		     	                  + " ,EMPNAME "
		     	                  + " ,FK_DEPTNO "
		     	                  + " ,ADDRESS "
		     	                  + " ,SALARY "
		     	                  + " ,COMMISSION_PCT "
		     	                  + " ,EMAIL "
		     	                  + " ,JUBUN "
		     	                  + " ,HIREDATE "
		     	                  + " ,MANAGERNO \n "+
		     	                      " from tbl_employees \n"+
		     	                      " where empno = ? ";
		     	            
		     	            pstmt = conn.prepareStatement(sql);
		     	            
		     	            pstmt.setString(1, empno);
		     	            
		     	            rs = pstmt.executeQuery();
		     	            
		     	            int cnt = 0;
		     	            if(rs.next()) { 
		     	               cnt ++;
		     	               if(cnt == 1) { // 사원번호에 해당하는 사원이 있을 때
		     	                   System.out.println("\n----------------------------------------------- >> 사원정보 << ----------------------------------------------------------------------------------");
		     	                   System.out.println("사원번호           직급     사원명   부서번호      거주지             연봉       수당          이메일               주민번호            입사일자             사수번호");
		     	                   System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
		     	                  
		     	                  System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t\t"
		     	                		  			+rs.getString(5)+"\t"+rs.getInt(6)+"\t"+rs.getFloat(7)+"\t"+rs.getString(8)+"\t"
		                  		  				+rs.getInt(9)+"\t"+rs.getString(10)+"\t"+rs.getInt(11)+"\n");
		     	                  
		     	                  do {
		     	                	  System.out.println(">> 해당 사원을 정말로 삭제하시겠습니까? [Y/N]");
		     		                  String yn = sc.nextLine();
		     		                  
		     			                  if("y".equalsIgnoreCase(yn)) {
		     			                     sql = " delete tbl_employees\n "
		     			                        + " where empno = ? ";
		     			                     pstmt = conn.prepareStatement(sql);
		     			                     pstmt.setString(1, empno);
		     			                     result = pstmt.executeUpdate();
		     			                     break;
		     			                  }
		     		
		     			                  else if("n".equalsIgnoreCase(yn)) {
		     			                     System.out.println("삭제가 취소되었습니다");
		     			                     break;
		     			                  }
		     			                  else { 
		     				                 System.out.println(">> [경고] Y 또는 N 만 입력하세요 <<\n");
		     					          }
		     				           
		     	                  }while(true);
		     	               }
		     	            }
		     		            else if(cnt == 0) { // 사원번호에 해당하는 사원이 없을 때
		     		               System.out.println(">> 조회하신 사원번호에 해당하는 사원이 없습니다.");
		     		               
		     	            }// end of if(rs.next()) ---------------
		     	         } catch (SQLException e) {
		     	            result = -1;
		     	         } finally {
		     	            close();
		     	         }
		     	         return result;
		     	      }// end of public int emInfoDelete(String empno)

			      	
			      	
			      	
			          
		      
		  	
		      /*
		  	---------------------------------------------------------------------------------------------------
		  	///////////////////////////////////////////////////////////////////////////////////////////////////
		  	///////////////////////////////////////////////////////////////////////////////////////////////////
		  	---------------------------------------------------------------------------------------------------
		  	///////////////////////////////////////////////////////////////////////////////////////////////////
		  	///////////////////////////////////////////////////////////////////////////////////////////////////
		  	---------------------------------------------------------------------------------------------------
		  	*/
		  	
			          
			          
			          
			          
			      				
			  	    
	      //휴가신청(임선우)
	      @Override
	      public void regiVacation(Map<String, String> paraMap) {
	         VacationDTO vdto = new VacationDTO();
	         
	         int sumVday= 0;
	        
	            try {
	               conn = MyDBConnection.getConn();
	               
	               conn.setAutoCommit(false); // 수동커밋 

	               String sql = " insert into tbl_vacation( fk_EMPNO,STARTVDAY,ENDVDAY, VDAY, SEQ_VNO) "
	                        + " values(? , ?, ?, ?, seq_vacationno.nextval) ";
	               
	               pstmt = conn.prepareStatement(sql);
	               
	               pstmt.setString(1, paraMap.get("fk_empno"));
	               pstmt.setString(2, paraMap.get("startday"));
	               pstmt.setString(3, paraMap.get("endday"));
	               pstmt.setString(4, paraMap.get("vday")); 
	               
	               int result = pstmt.executeUpdate();
	               
	               if(result==1) { // 누적 휴가신청일 확인하기 
	                  
	                   sql  = " select STARTVDAY, ENDVDAY, vday, seq_vno"
	                           + " from tbl_vacation "
	                           + " where fk_empno = ? and substr(endvday, 1,4) = extract(year from sysdate) "
	                           + " order by seq_vno ";
	                        
	                         pstmt = conn.prepareStatement(sql);
	                         
	                         pstmt.setString(1, paraMap.get("fk_empno"));
	                         
	                         rs = pstmt.executeQuery();
	                         while(rs.next()) {   
	                            vdto.setStartVday(rs.getString(1)); 
	                            vdto.setEndVday(rs.getString(2)); 
	                            vdto.setvDay(rs.getInt(3)); 
	                            
	                            sumVday += vdto.getvDay();
	                         } 
	                     }
	                         
	                         if(sumVday > 15  ) { // 누적 휴가 신청 일수가 15일이 초과했을 경우
	                            System.out.println("휴가신청 가능한 일수가 초과하였습니다.");
	                            conn.rollback();
	                         }
	                         
	                         else {
	                            System.out.println("휴가신청이 완료되었습니다.");
	                            conn.commit();
	                         }
	               
	            }catch (SQLException e) { 
	            	e.printStackTrace();
	            	if(e.getErrorCode() == 1) {
	            		System.out.println("신청 날짜가 중복되면 안됩니다. ");
	            	}
	            	System.out.println("시스템상 오류로 실패하였습니다.");
	            } finally {
	            	try {
	    				conn.setAutoCommit(true);// 자동 commit 으로 복원시킨다.
	    			} catch (SQLException e) {	}
	               close();
	            }
	 
	      }// public void regiVacation(Map<String, String> paraMap)
	    	
	    	
	    	
	      
	      
	    	
	      /*
	  	---------------------------------------------------------------------------------------------------
	  	///////////////////////////////////////////////////////////////////////////////////////////////////
	  	///////////////////////////////////////////////////////////////////////////////////////////////////
	  	---------------------------------------------------------------------------------------------------
	  	///////////////////////////////////////////////////////////////////////////////////////////////////
	  	///////////////////////////////////////////////////////////////////////////////////////////////////
	  	---------------------------------------------------------------------------------------------------
	  	*/
	  	
	      
	      
	      
	      
	  				
	      // 휴가신청취소(임선우)
	      @Override
	      public int deleteVacation(MemberLoginDTO mdto, String vno) {
	         int empno = mdto.getMemberid();
	         int result = 0;
	         
	         try {
	            conn = MyDBConnection.getConn();
	         
	            String sql = " delete tbl_vacation "
	                     + " where fk_empno = ? and seq_vno = ? ";
	           
	            pstmt = conn.prepareStatement(sql);
	            
	            pstmt.setInt(1, empno); 
	            pstmt.setString(2, vno); 
	            
	            result = pstmt.executeUpdate();
	            // delete 가 성공되어지면 result 에는 1이 들어간다.
	            
	         }catch(SQLException e) {
	         //   e.printStackTrace();
	            result = -1;
	         }finally {
	            close(); // 자원반납하기 
	         }
	         
	         return result;
	         
	      }// end of public int deleteVacation(MemberLoginDTO mdto, String vno)

	  	
	  	
	  	
	      
	      
	  	
	      /*
	  	---------------------------------------------------------------------------------------------------
	  	///////////////////////////////////////////////////////////////////////////////////////////////////
	  	///////////////////////////////////////////////////////////////////////////////////////////////////
	  	---------------------------------------------------------------------------------------------------
	  	///////////////////////////////////////////////////////////////////////////////////////////////////
	  	///////////////////////////////////////////////////////////////////////////////////////////////////
	  	---------------------------------------------------------------------------------------------------
	  	*/
	  	
	      
	      
	      
	      
	  			



	      // 특정 사원 휴가 내역 출력(임선우) 
	      @Override
	      public List<VacationDTO> memvdtoList(MemberLoginDTO mdto) {
	         
	         List<VacationDTO> vdtoList = new ArrayList<>();
	         int empno = mdto.getMemberid();
	         
	         try {
	            conn = MyDBConnection.getConn();
	            
	            String sql = " select STARTVDAY, ENDVDAY,VDAY,SEQ_VNO "
	                     + " from tbl_vacation"
	                       + " where fk_empno = ? and sysdate < to_date(endvday,'yyyy-mm-dd') "
	                       + " order by seq_vno";
	            
	            pstmt = conn.prepareStatement(sql);
	            
	            pstmt.setInt(1, empno); 
	            
	            rs = pstmt.executeQuery();
	            
	            while(rs.next()) {
	               VacationDTO vdto = new VacationDTO();
	               
	                vdto.setStartVday(rs.getString(1)); 
	                vdto.setEndVday(rs.getString(2)); 
	                vdto.setvDay(rs.getInt(3));
	                vdto.setSeq_vno(rs.getInt(4));
	                
	                vdtoList.add(vdto);
	             }// end of while
	            
	         }catch(SQLException e) {
	            e.printStackTrace();
	         }finally {
	            close(); // 자원반납하기 
	         }
	         
	         return vdtoList;
	      }// end of public List<VacationDTO> VacationList(MemberLoginDTO mdto, Scanner sc)


			
			
			/*
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			*/
				
				
				
				


	      // 잔여휴가일수 확인(임선우)
	      @Override
	      public int searchvday(MemberLoginDTO mdto) {
	         int sumVDay = 0;
	         int empno = mdto.getMemberid(); // 로그인된 아이디
	         try {
	            conn = MyDBConnection.getConn();
	            
	            String sql = " select vday"
	                       + " from tbl_vacation"
	                       + " where fk_empno = ? and sysdate < to_date(endvday,'yyyy-mm-dd') "
	                       + " order by seq_vno";
	            
	            pstmt = conn.prepareStatement(sql);
	            
	            pstmt.setInt(1, empno); 
	            
	            rs = pstmt.executeQuery();
	            
	            while(rs.next()) {
	                
	                sumVDay += rs.getInt(1);
	                
	             }// end of while
	            
	         }catch(SQLException e) {
	        	 System.out.println("시스템 오류로 실패하였습니다.");
	        	 e.printStackTrace();
	         }finally {
	            close(); // 자원반납하기 
	         }
	         return sumVDay;
	      }// end of public int searchvday(MemberLoginDTO mdto) -------------------------


			
			
			/*
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			///////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////
			---------------------------------------------------------------------------------------------------
			*/
				
	    

			
			
			
			
			
			
			
			
}
			
    

		
		
		
		
		
		
		
		

