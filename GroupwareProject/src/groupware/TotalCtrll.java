package miniproject1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



public class TotalCtrll  {

	InterTotalDAO tdao = new TotalDAO();
	
	// 시작메뉴(조상운)
		public void startMenu () {
			
			Scanner sc = new Scanner(System.in);
			MemberLoginDTO mdto = null;
			SuperAdminDTO superdto = null;
			AdminDTO adto = null;
			
			String menuno = "";
			
			do {
				
				System.out.println("---------->>인사관리 프로그램<<------------");
				System.out.println("1.사원로그인   2.인사담당자 로그인   3.프로그램종료");
				System.out.println("--------------------------------------\n");
				System.out.print("▷메뉴번호 선택 : ");
				
				menuno = sc.nextLine();
				
				switch (menuno) {
				case "1":								//사원로그인 선택	
					String memberid="";
					do {
						  System.out.println("\n== >> 사원 로그인 << ==");
		                  System.out.print("▷ 아이디 : ");
		                  memberid = sc.nextLine();
		                  if("boardadmin".equals(memberid)) {
		                	  break;
		                  }//end of if--
		                  try {
		                     Integer.parseInt(memberid);
		                     break;
		                  } catch(NumberFormatException e) {
		                  System.out.println(">> 아이디는 숫자로 입력해주세요! <<");   
		                  }//end of try-catch---
		               } while (true);
		               
		               System.out.print("▷ 비밀번호 : ");
		               String memberpwd = sc.nextLine();// 사원로그인
		               
					
					if(!memberid.equals("boardadmin")) {
						mdto = memberlogin(memberid,memberpwd);// 로그인 해주는 메소드
						
						if(mdto != null ) { // 일반사원 로그인 DTO가 NULL 이 아닌경우
							loginMenu(mdto,sc); // 일반사원으로 로그인 했을 경우 나오는 메뉴
						}//end of if---
					}//end of if----
					
					else if("boardadmin".equals(memberid)) { // 게시판 관리자 로그인
						superdto = boardAdminLogin(memberid,memberpwd);
						
						if(superdto != null) {	// 게시판 관리자 DTO가 NULL 이 아닌경우
							boardadminMenu(superdto, sc); // 게시판 담당자로 로그인 했을때 나오는 메뉴
						}
					}
					break;
				case "2":	//인사담당자 로그인 선택시
					adto = adminLogin(sc);
					if(adto != null ) { // 인사담당자DTO가 null이 아니라면
						AdminloginMenu(adto,sc);	 // 인사담당자로 로그인 했을때 나오는 메뉴메소드 호출
					}
					break;
				case "3":	//프로그램 종료 선택시
					break;

				default:	//메뉴에 없는 번호 선택시
					System.out.println("\n>> 메뉴에 없는 번호입니다!! <<\n");
					break;
				}
			} while (!("3".equals(menuno)));
				System.out.println("\n>> 프로그램이 종료되었습니다 <<");
			
			
		}// end of public void startMenu () -----------------------------------------------
		
		
		
		
		// 인사담당자 로그인(조상운)
		private AdminDTO adminLogin(Scanner sc) {
			
			AdminDTO adto = null;
			
			System.out.println("\n== >> 인사담당자 로그인 << ==");
			System.out.print("▷ 아이디 : ");
			String adminid = sc.nextLine();
			System.out.print("▷ 비밀번호 : ");
			String adminpwd = sc.nextLine();// 사원로그인
			
			if("admin".equals(adminid)) {
				adto = tdao.adminLogin(adminpwd);
			}
			else {
				System.out.println(">> 인사담당자만 로그인해주세요 <<\n");
			}
			if(adto != null) {
				System.out.println(">> 인사담당자 로그인이 성공되었습니다.\n");
			}
			else if(adto == null && "admin".equals(adminid)){
				System.out.println(">> 인사담당자 로그인이 실패되었습니다.\n");
			}
				
			return adto;
		}// end of private AdminDTO adminLogin(Scanner sc)
		
		
		
		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		
		
		
		// 일반사원 로그인중 메뉴화면(조상운) 
		private void loginMenu(MemberLoginDTO mdto, Scanner sc) {
			String menu_no = "";
			
			do {System.out.println("\n-------------- >> "+tdao.empName(mdto)+"님, 반갑습니다! << ------------\n"
						         + "1.출/퇴근 기록        2.급여조회           3.재직증명서 조회\n"
								 + "4.품의서             5.사내게시판         6.내정보조회/수정\n"
				                 + "7.연차/휴가 쓰기       8.로그아웃\n"
				                 + "--------------------------------------------------\n"
				                 + tdao.empName(mdto)+"님 미결재 서류내역 : "+tdao.cnt_doc(mdto)+"건");
				System.out.print("▷ 메뉴번호 선택 :");    
				menu_no = sc.nextLine();    
				switch (menu_no) {
					case "1":	//출근 또는 퇴근기록 
						attendance_rcd(mdto,sc);
						break;
					case "2":   //급여조회
						menu_salary(mdto,sc);
						break;
					case "3":	//재직증명서 조회
						employment_menu(mdto,sc);
						break;
					case "4":	// 품의서
						menu_approval(mdto,sc);	
						break;
					case "5":	// 사내게시판
						if(mdto.getBoard_stop()==0) {	//사내게시판 이용정지여부검사
							go_board(mdto,sc);
						}
						else {							//이용정지당한 회원이라면
							System.out.println("\n >> 게시판이용이 정지된 회원입니다! 사유 : "+mdto.getComments()+"<< \n"
												+" >> 관리자에게 문의하세요 << \n");
						}
						break;
					case "6":	// 내정보조회/수정
						myInfoSearchUpdate(mdto,sc);
						break;
					case "7":	// 연차/휴가 쓰기
						signVacation(mdto, sc);
						break;
					case "8":	// 로그아웃
						mdto = null;
						break;

					default:	// 메뉴에 없는 번호 선택시
						System.out.println("\n>> 메뉴에 없는 번호입니다 !! <<\n");
						break;
				}
			} while (!("8".equals(menu_no)));
			
		}// end of private void loginMenu(MemberLoginDTO mdto, Scanner sc) -----------------
		
		
		
		// 휴가 신청 입력 메소드 
		   private void regiVacation(MemberLoginDTO mdto,Scanner sc) {
		        String startday = "";
		        String endday= "";
		            
		          DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		            
		          Date end_day;
		          Date start_day;
		          int i=0;
		        
		        int fk_empno = mdto.getMemberid();
		           do {// 날짜 확인
		               System.out.println(">> 원하시는 시작 날짜를 입력하세요.[형식:2022-08-04]");
		               startday = sc.nextLine();
		                
		               try { 
		                   df.setLenient(false);
		                    start_day = df.parse(startday);
		                    break;
		                 } catch (Exception e) {
		                    System.out.println("날짜를 다시 입력하세요.[형식:2022-08-04]");
		                 }
		           } while (true);
		      
		           do {// 날짜 확인
		               System.out.println(">> 원하시는 마지막 날짜를 입력하세요.[형식:2022-08-04]");
		               endday = sc.nextLine();
		               
		                try {
		                   df.setLenient(false);
		                    end_day = df.parse(endday);
		                    break;
		                 } catch (Exception e) {
		                    System.out.println("날짜를 다시 입력하세요.");
		                 }
		           } while (true);
		       
		         Calendar c_startday = Calendar.getInstance();
		         c_startday.setTime(start_day);
		      
		         Calendar c_endday = Calendar.getInstance();
		         c_endday.setTime(end_day); 
		         
		         Date now = new Date();
		         
		         Calendar nextyear = Calendar.getInstance();
		         nextyear.add(Calendar.YEAR, 2);
		         
		         int vacation=0;
		         
		       
		         
		         if(start_day.after(now) &&
		              Calendar.SATURDAY != c_startday.get(Calendar.DAY_OF_WEEK) &&
		              Calendar.SUNDAY != c_startday.get(Calendar.DAY_OF_WEEK) &&
		              !( nextyear.after(c_startday.get(Calendar.YEAR))) 
		              ) { // 신청한 날짜가 오늘 이후 일때
		            
		           
		            while (c_startday.before(c_endday) || c_startday.equals(c_endday)) {
		               
		                if ( Calendar.SATURDAY != c_startday.get(Calendar.DAY_OF_WEEK) && 
		                       Calendar.SUNDAY != c_startday.get(Calendar.DAY_OF_WEEK) )
		                 {// 주말빼고 휴가 일자 카운트
		                  
		                   vacation++;
		                } 
		                c_startday.add(Calendar.DATE, 1);
		               
		            }
		           
		            Map<String, String> paraMap = new HashMap<String, String>();
		            paraMap.put("fk_empno", fk_empno+"");
		            paraMap.put("startday", startday);
		            paraMap.put("endday", endday);
		            paraMap.put("vday",vacation+"");
		            
		            
		            i = tdao.existvday(paraMap); // 기존 테이블에 겹치는 날짜인지 확인해주는 메소드
		            
		            if(i == 0) { // 겹치는 날짜가 없다면 실행
		                  tdao.regiVacation(paraMap);
		            }
		            else if(i == -1){
		               System.out.println("휴가 신청날짜가 기존에 있는 휴가날짜와 겹칩니다.");
		            }
		            
		            
		            
		        }
		        else if(!(c_startday.getTime().after(now))) {
		            System.out.println("오늘 이후인 날짜만 신청 가능합니다.");// 오늘 이전 날짜를 입력했을 경우
		        }
		        else if(Calendar.SATURDAY == c_startday.get(Calendar.DAY_OF_WEEK) || Calendar.SUNDAY == c_startday.get(Calendar.DAY_OF_WEEK)) {
		          System.out.println("주말날짜를 시작일로 지정할 수 없습니다.");
		       }
		        else if(nextyear.after(c_startday.get(Calendar.YEAR))) {
		          System.out.println("금년부로 일년후까지만 신청가능합니다.");
		          System.out.println(Calendar.YEAR+1); 
		       }
		}// end of private void regiVacation(MemberLoginDTO mdto)
		
		
		
		
		
		
		
		
		// 사내게시판 첫 메뉴화면(최현우)
		private void go_board(MemberLoginDTO mdto, Scanner sc) {
			
			int page =1;// 내가 보고있는 페이지
			int contentCnt = tdao.contentCnt();	//총 게시글 수를 알아오는 메소드
			int total_page =  (int)Math.ceil(contentCnt/5f);	// 총 게시글수 / 한페이지당 글의 갯수의 올림
			String menu_no="";
			if(total_page==0) {	//총페이지가 0이라면
				total_page=1;
			}
			do {
				List<BoardDTO> boardList = tdao.select_board(page);
				System.out.println("\n\n※공지사항 :"+tdao.showNotice()+"                     총 게시글 수 : "+contentCnt);
				System.out.println("----------------------------------------------------\n"
								 + "  글번호         글제목        닉네임      작성일자      조회수\n"
								 + "----------------------------------------------------");
				for(int i=0; i<boardList.size();i++) {
	                  int commentcnt = boardList.get(i).getCommentcnt();
	                  String subject=boardList.get(i).getSubject();
	                  if(commentcnt!=0) {
	                     subject = subject+"["+commentcnt+"]";
	                  }
	                  
	                  System.out.println(format(String.valueOf(boardList.get(i).getBoardno()),6)
	                		  			+format(subject,17)+format(boardList.get(i).getNickname(),10)
	                		  			+format(boardList.get(i).getRegisterday(),16)
	                		  			+format(String.valueOf(boardList.get(i).getBoardcnt()),6));
                }//end of for --
				System.out.println("----------------------------------------------------");
				
				System.out.println("                   "+page+"/"+total_page+"             ");
				System.out.println(">> [이전] [다음] [조회] [내용보기] [글쓰기] [뒤로가기] <<");   //(글번호로조회,제목으로조회,닉네임으로조회)
				System.out.print("▷ 이동하고자 하는 페이지번호 또는 [업무목록] 을 입력해주세요 : ");
				menu_no = sc.nextLine();
				if(menu_no.trim().isEmpty()) {	// 공백을 입력한 경우
					System.out.println(">> 공백은 입력이 불가합니다!! <<");
					continue;
				}
				
				else {
					int flag = 0;				
					for(int i=0; i<menu_no.length(); i++) { //입력받은 글자의 길이 만큼 검사를 해야한다.
						char ch = menu_no.charAt(i);
							if(Character.isDigit(ch)) {//숫자라면
								flag++;
							}
					}//end of for--
					
					if(flag == menu_no.length() && Integer.parseInt(menu_no)<=total_page&&0<Integer.parseInt(menu_no) ) { //검사해서 나온 숫자의 개수가 글자길이의 수와 같다면,
						page = Integer.parseInt(menu_no);
						continue;
					}
				
					switch (menu_no) {
						case "이전":
							if(page>1) {	//현재 페이지가 1보다 크다면
								page--;
							}
							else {
								System.out.println("\n>> 이전 페이지가 없습니다 !!\n");
							}
							break;
						case "다음":
							if(page<total_page) {	//현재 페이지가 
								page++;
							}
							else {
								System.out.println("\n >> 다음 페이지가 없습니다 !!\n");
							}
							break;
						case "조회":		//민수
							board_search(mdto,sc);
							break;
						
						case "내용보기":	//상운
							viewOneNotice(mdto,sc);
							break;
						case "글쓰기":	//현우
							board_write(mdto,sc);
							break;
						case "뒤로가기":	//현우
							break;
						default:	// 메뉴에 없는 문자 입력시
							System.out.println("\n>> []를 제외한 보기에 있는 메뉴만 입력해주세요!! <<\n");
							break;
						}// end of switch---
					} //end of else   
				}while(!("뒤로가기".equals(menu_no)));
			
		}//end of private void go_board(MemberLoginDTO mdto, Scanner sc)----

		
		


		// 게시판관리자계정 게시판 레이아웃(최현우)
		private void super_go_board(SuperAdminDTO superdto, Scanner sc) {
			MemberLoginDTO mdto = new MemberLoginDTO();
			mdto.setMemberid(superdto.getFk_empno());
			
			int page =1;// 내가 보고있는 페이지
			int contentCnt = tdao.contentCnt();	//총 게시글 수를 알아오는 메소드
			int total_page =  (int)Math.ceil(contentCnt/5f);	// 총 게시글수 / 한페이지당 글의 갯수의 올림
			String menu_no="";
			do {
				if(total_page==0) {
					total_page=1;
				}
				List<BoardDTO> boardList = tdao.select_board(page);
				System.out.println("\n\n※공지사항 :"+tdao.showNotice()+"                     총 게시글 수 : "+contentCnt);
				System.out.println("----------------------------------------------------\n"
								 + "  글번호         글제목        닉네임      작성일자      조회수\n"
								 + "----------------------------------------------------");
				for(int i=0; i<boardList.size();i++) {
	                  int commentcnt = boardList.get(i).getCommentcnt();
	                  String subject=boardList.get(i).getSubject();
	                  if(commentcnt!=0) {
	                     subject = subject+"["+commentcnt+"]";
	                  }
	                  
	                  System.out.println(format(String.valueOf(boardList.get(i).getBoardno()),6)
	                		  			+format(subject,17)+format(boardList.get(i).getNickname(),10)
	                		  			+format(boardList.get(i).getRegisterday(),16)
	                		  			+format(String.valueOf(boardList.get(i).getBoardcnt()),6));
                }//end of for --
				System.out.println("----------------------------------------------------");
				
				System.out.println("                   "+page+"/"+total_page+"             ");
				System.out.println(">> [이전] [다음] [조회] [내용보기]  [뒤로가기] <<");   //(글번호로조회,제목으로조회,닉네임으로조회)
				System.out.print("▷ 이동하고자 하는 페이지번호 또는 [업무목록] 을 입력해주세요 : ");
				menu_no = sc.nextLine();
				if(menu_no.trim().isEmpty()) {	// 공백을 입력한 경우
					System.out.println(">> 공백은 입력이 불가합니다!! <<");
					continue;
				}// 공백을 입력한경우 끝
				
				else {	//공백을 입력하지 않았다면,
					int flag = 0;				
					for(int i=0; i<menu_no.length(); i++) { //입력받은 글자의 길이 만큼 검사를 해야한다.
						char ch = menu_no.charAt(i);
							if(Character.isDigit(ch)) {//숫자라면
								flag++;
							}
					}//end of for--
					
					if(flag == menu_no.length() && Integer.parseInt(menu_no)<=total_page&&0<Integer.parseInt(menu_no) ) { //검사해서 나온 숫자의 개수가 글자길이의 수와 같다면,
						page = Integer.parseInt(menu_no);
						continue;
					}
				
					switch (menu_no) {
						case "이전":
							if(page>1) {	//현재 페이지가 1보다 크다면
								page--;
							}
							else {
								System.out.println("\n>> 이전 페이지가 없습니다 !!\n");
							}
							break;
						case "다음":
							if(page<total_page) {	//현재 페이지가 
								page++;
							}
							else {
								System.out.println("\n >> 다음 페이지가 없습니다 !!\n");
							}
							break;
						case "조회":		//민수
							board_search(mdto,sc);
							break;
						
						case "내용보기":	//상운
							viewOneNotice(mdto,sc);
							break;
						case "뒤로가기":	//현우
							break;
						default:	// 메뉴에 없는 문자 입력시
							System.out.println("\n>> []를 제외한 보기에 있는 메뉴만 입력해주세요!! <<\n");
							break;
						}// end of switch---
					} //end of else   
				}while(!("뒤로가기".equals(menu_no)));
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
		
		
		// 내가 작성한 게시판글 삭제하는 메소드(조상운)
        private int deleteMyNotice(BoardDTO bdto, Scanner sc) {
           int result = 0;
           String boardpwd = "";
           int cnt = 0;
           for(int i=0; i<3; i++) {   // 비밀번호를 틀리는 경우
              System.out.print("게시글 비밀번호를 입력하세요 : ");
              try {
                 boardpwd = sc.nextLine();
                 Integer.parseInt(boardpwd);
                 if(Integer.parseInt(boardpwd) != bdto.getBoardpwd()) {
                    System.out.println("비밀번호를 잘못 입력하셨습니다.");
                    cnt++;
                 }
                 else {
                    break;
                 }
              }
              catch (NumberFormatException e) {
                 cnt++;
                 System.out.println("[경고] 게시판 비밀번호는 정수로만 입력하세요");
              }//end of try-catch--
           }//end of for--
           if(cnt == 3) {
              result = 3;
              return result;
           }
            do {   
                 System.out.print(">> 정말로 글을 삭제하십니까? [Y/N]");
                 String yn = sc.nextLine();
                 
                 if("y".equalsIgnoreCase(yn)) {
                    result = tdao.boardDelete(String.valueOf(bdto.getBoardno()));
                    break;
                 }
                 else if("n".equalsIgnoreCase(yn)) {
                    result = 2;
                    break;
                 }
                 else {
                    System.out.println(">> Y 또는 N 만 입력하세요!! <<");
                 }
              } while (true);
           return result;
        }//end of deleteMyNotice(BoardDTO bdto, Scanner sc)-----
		
		
		
		
		
		
		
     // 글 한개의 글내용을 보여주는 메소드(조상운)
        private void viewOneNotice(MemberLoginDTO mdto, Scanner sc) {
           
              boolean check = true;
             String boardno = "";
              BoardDTO bdto = new BoardDTO();
              String warning = "";
              
              do {
                 System.out.print("▷ 내용을 볼글의 글번호를 입력해주세요 :");
                 boardno = sc.nextLine();
                 try {
                    Integer.parseInt(boardno);
                    break;
           } catch (NumberFormatException e) {
              System.out.println("\n>> 글번호는 정수만 입력하세요 <<\n");
           }
                 
        } while (true);
              bdto = tdao.viewOneNotice(boardno,mdto.getMemberid());
              
              String changeBoardno = "";
              if(bdto != null && bdto.getPre_boardno() != null) {
                 outer:
                 do {
                    
                    System.out.println("\n\n\n\n\n\n\n\n\n"+warning+"\n=====================>> "+ bdto.getBoardno()+"번 글 <<========================");   
                    System.out.println("<글제목>: " + bdto.getSubject());
                    System.out.println("<글내용>\n" + Enter(bdto.getContents()));
                    System.out.println("_________________________________________________________"); 
                    
                    String str1 = "<이전글번호>                                      <다음글번호>";
                    String str =  "[이전글]        [뒤로가기]          [댓글쓰기]         [다음글]";
                    warning = "";
                    
                    if(bdto.getFk_empno() == mdto.getMemberid()) {
                       check = false;
                       str = "[이전글]    [수정]    [뒤로가기]   [댓글쓰기]   [삭제]   [다음글]";
                    }
                 
                    tdao.viewComment(bdto.getBoardno());
                    System.out.println("_________________________________________________________");
                    System.out.println(str1);
                    System.out.println(bdto.getPre_boardno()+"                                            "+bdto.getNext_boardno());
                    System.out.println("<이전글제목>                                      <다음글제목>");
                    System.out.println(bdto.getPre_subject()+"                                        "+bdto.getNext_subject());
                    System.out.println(str);
                    System.out.println("=========================================================");
                    System.out.print("▷ []안에있는 [업무목록]을 입력해주세요 : ");
                    
                    String inputno = sc.nextLine();
                    int result = 0;
                 
                    loop :
                    do {   
                       switch (inputno) {
                    case "이전글":
                          if("없음".equals(String.valueOf(bdto.getPre_boardno()))) { // 이전글번호가 없음이라면
                             warning = ">> 이전글이 없으므로 조회할 수 없습니다. <<";
                             continue outer;
                          }
                       changeBoardno = bdto.getPre_boardno();
                       bdto = tdao.viewOneNotice(String.valueOf(changeBoardno),mdto.getMemberid());
                       continue outer;
                    case "다음글":
                          if("없음".equals(String.valueOf(bdto.getNext_boardno()))) { // 다음글 번호가 없음이라면
                             warning = ">> 다음글이 없으므로 조회할 수 없습니다. <<";
                             continue outer;
                          }
                       changeBoardno = bdto.getNext_boardno();
                       bdto = tdao.viewOneNotice(String.valueOf(changeBoardno),mdto.getMemberid());
                       continue outer;   
                    case "수정":
                       if(check == true && "수정하기".equals(inputno)) { // 수정 목록이 나오지않았는데 수정을 입력하면 막아준다.
                          System.out.println("본인이 작성한 게시물 이외에는 수정이 불가합니다.");
                          break outer;
                       }
                       result = updateMyNotice(bdto,sc);
                       if(result == 1) {
                          System.out.println(">> 글 수정에 성공하였습니다!!");
                          break outer;
                       }
                       else if(result == 2) {
                          System.out.println(">> 글 수정을 취소하셨습니다.");
                          break outer;
                       }
                       else if(result == 3) {
                          System.out.println(">> 비밀번호 3회오류입니다.");
                          break outer;
                          
                       }
                       else {
                          System.out.println(">> 글 수정에 실패하였습니다!!");
                       }
                       break;
                    case "삭제":
                       if(check == true && "삭제".equals(inputno)) { // 삭제 목록이 나오지않았는데 삭제를 입력하면 막아준다.
                          System.out.println("본인이 작성한 게시물 이외에는 삭제가 불가합니다.");
                          break outer;
                       }
                       result = deleteMyNotice(bdto,sc);
                       if(result == 1) {
                          System.out.println(">> 게시물 삭제 성공");
                          break outer;
                       }
                       else if(result == 2) {
                          System.out.println(">> 게시물 삭제를 취소하셨습니다.");
                          break loop;
                       }
                       else if(result == 3) { // 비밀번호 입력 3회 오류일때
                          System.out.println(">> 비밀번호 3회오류입니다. 다시시도하세요.");
                          break loop;
                       }
                       else {
                          System.out.println(">> 게시물 삭제 실패");
                          break loop;
                       }
                    case "댓글쓰기":
                        int i = insertComment(mdto, bdto, sc);
                        if(i==1) {
                           System.out.println(">> 댓글작성 완료");
                           break loop;
                        }
                        else if(i == -1) {
                           System.out.println(">> 시스템 오류로 입력이 불가능합니다.");
                           break loop;
                        }
                        else if(i == 0){
                           System.out.println(">> 댓글작성을 취소하셨습니다.");
                        }
                    case "뒤로가기":
                       break outer;
                    default:
                       System.out.println("\n>> 보기에 존재하는 목록만 입력해주세요 <<\n");
                       break loop;
                       }//end of switch-----
                    } while(true);   
                    
                 } while(true);
              }//end of if---
              else {
                 System.out.println("\n>> 조회하신 글번호가 존재하지 않습니다. <<\n");
              }
       }// end of private void viewOneNotice(int boardno)	
		
		
		
		

		// 인사담당자로 로그인 했을때 나오는 레이아웃(조상운)
		private void AdminloginMenu(AdminDTO adto, Scanner sc) {
			
			
			String menu_no = "";
			outer:
			do {
			
				System.out.println("\n----------- >> [인사담당자] 로그인중 << ------------\n"
						         + "1.인사평가        	2.채용관리      3.사원정보 조회/수정\n"
								 + "4.사원정보 삭제     5.직원등록      6.부서신설\n"
								 + "7.로그아웃\n"
				                 + "--------------------------------------------");
				System.out.print("▷ 메뉴번호 선택 :");    
				menu_no = sc.nextLine();
			
				switch (menu_no) {
					case "1":	// 인사평가(진민지)
						emp_jpr(sc);
						break;
					case "2":	// 채용관리(임선우)
						applicantManage(sc);
						break;
					case "3":	// 사원정보 조회/수정(임선우)
						   
		                  do {
		                     System.out.println("\n------------------- >> 사원정보 조회/수정 << -----------------");
		                       System.out.println("1.전체사원조회      2.사원번호조회  3.특정부서조회    4.인사평가기준 조회\n"
		                                        + "5.입사일자기준 조회  6.수정        7.뒤로가기");
		                  
		                        System.out.print("▷ 메뉴번호 : ");
		                        menu_no = sc.nextLine();
		                        if("1".equals(menu_no) || "2".equals(menu_no) || "3".equals(menu_no) 
		                                           || "4".equals(menu_no) || "5".equals(menu_no)) {
		                           emSearch(menu_no);
		                        }
		                        else if("6".equals(menu_no)) {
		                           emUpdate();
		                        }
		                        else if("7".equals(menu_no)) {
		                         continue outer;
		                        }
		                        else { 
		                           System.out.println("메뉴에 없는 번호입니다.");
		                        }
		                  }while(true);
					case "4":	// 사원정보 삭제(임선우)
						emInfoDelete();
						break;
					case "5":	// 직원등록 (최현우)
						register_emp(sc);
						break;
					case "6":	// 부서신설(최현우)
						new_department(sc);
						break;
					case "7":	// 로그아웃
						break;
					default:	//메뉴에 없는 번호 선택시 
						break;
				}
			
			} while (!("7".equals(menu_no)));
			 
			
		}// end of private void AdminloginMenu(AdminDTO adto, Scanner sc) ----------------------------
		
		
		
		
		

		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		

		// 부서신설 메소드(최현우)
		private void new_department(Scanner sc) {
			String department_name="";
			int dep_manager_no=0;
			int department_tel=0;
			
			do {
				try {
					System.out.print("\n▷등록하실 부서의 이름을 입력해주세요 : ");
					department_name = sc.nextLine();
					System.out.print("\n▷등록하실 부서의 연락처를 입력해주세요 : ");
					department_tel = Integer.parseInt(sc.nextLine()); 
					System.out.print("\n▷등록하실 부서의 부서장 사원번호를 입력해주세요 : ");
					dep_manager_no = Integer.parseInt(sc.nextLine());
					break;
				} catch (NumberFormatException e) {
					System.out.println(">> 숫자만 입력해주세요 !! <<");
				}
			}while(true);
			String department_no = select_department_seq(); 	//부서번호(시퀀스)를 알아와서 변수처리
			
			Map<String,String> paraMap = new HashMap<>();
			paraMap.put("department_tel", String.valueOf(department_tel));
			paraMap.put("department_name", department_name);
			paraMap.put("dep_manager_no", String.valueOf(dep_manager_no));
			paraMap.put("department_no", department_no);
			
			int n = tdao.new_department(paraMap);
			
			if(n==1) {	//신설 성공
				System.out.println("\n>> 부서 등록에 성공하였습니다!! <<\n");
			}
			else if(n==0) {// 신설 실패
				System.out.println("\n>> 부서 등록에 실패하였습니다. <<\n");
			}
			else {	//오류
				
			}
		}//end of new_department(Scanner sc)----




		// 사내게시판에 글작성 메소드(최현우)
		private void board_write(MemberLoginDTO mdto,Scanner sc) { 
			String nickname="";
			String subject ="";
			String contents = "";
			int boardpwd=0;
			System.out.println("\n----------------작성양식-------------------\n"
							 + "닉네임 : 5자이내  글제목: 20자이내  글내용 : 200자이내\n"
						 	 + "글암호 : 4자리 숫자 "
						 	 + "취소하시려면 [0]을 눌러주세요!\n"
						 	   + "----------------------------------------");
			outer:
			do {
				do {
				System.out.print("▷ 닉네임 : ");
					 nickname = sc.nextLine();
					if(nickname.trim().isEmpty()||nickname.length()>=5) {	//공백이 없는 5자이내
						System.out.println(">> 닉네임은 공백이 없는 5글자 이내로 작성해주세요! <<");
					}
				}while(!(!nickname.trim().isEmpty()&&nickname.length()<5)); //정상 입력시 탈출
				if("0".equals(nickname)) {
					break outer;
				}
				do {
				System.out.print("▷ 글제목 : ");
					subject = sc.nextLine();
					if(subject.trim().isEmpty()||subject.length()>=20) { //공백이 없는 20자이내
						System.out.println(">> 글제목은 공백이 없는 20자 이내로 작성해주세요! <<");
					}
				}while(!(!subject.trim().isEmpty()&&subject.length()<20)); //정상 입력시 탈출
				if("0".equals(subject)) {
					break outer;
				}
				do {
				System.out.print("▷ 글내용 : ");
					contents = sc.nextLine();
					if(contents.trim().isEmpty()||contents.length()>=200) {	//공백이 없는 200자이내
						System.out.println(">> 글내용은 공백이 없는 200자 이내로 작성해주세요! <<");
					}
				}while(!(!contents.trim().isEmpty()&&contents.length()<200)); //정상 입력시 탈출
				if("0".equals(contents)) {
					break outer;
				}
				do {
					try {
						System.out.print("▷ 비밀번호 : ");
						boardpwd = Integer.parseInt(sc.nextLine()) ;
						if((boardpwd+"").trim().isEmpty()||(boardpwd+"").length()!=4) {	//공백이 없는 4자이내
							System.out.println(">> 비밀번호는 공백이 없는 숫자로된 4자리이어야 합니다! <<");
						}
						if("0".equals(boardpwd)) {
							break outer;
						}
					}catch(NumberFormatException e) {	//숫자가 아닌 문자를 입력한다면
						System.out.println(">> 비밀번호는 공백이 없는 숫자로된 4자리이어야 합니다! <<");
					}
				}while(!(!((boardpwd+"").trim().isEmpty())&&(boardpwd+"").length()==4)); //정상 입력시 탈출
				
				
				int empno = mdto.getMemberid();
				Map<String,String> paraMap = new HashMap<>();
				
				paraMap.put("nickname", nickname);
				paraMap.put("subject", subject);
				paraMap.put("contents", contents);
				paraMap.put("empno", String.valueOf(empno));
				paraMap.put("boardpwd", String.valueOf(boardpwd));
				int n=0;
				do {
					System.out.print("\n>>작성된 글을 등록하시겠습니까?[Y/N] : \n");
					String yn =sc.nextLine();
					if("y".equalsIgnoreCase(yn)) {
						n = tdao.board_write(paraMap);
						break;
					}
					else if("n".equalsIgnoreCase(yn)) {
						System.out.println("\n>> 글 작성이 취소되었습니다!! <<\n");
						break outer;
					}
					else {
						System.out.println("\n>> Y 또는 N 만 입력해주세요!! <<\n");
					}
				}while(true);//end of do-while----
				
				if(n==1) {	//글 작성에 성공했더라면,
					System.out.print("\n>> 글 작성이 완료되었습니다!! 또 작성하시겠습니까? [Y/N]\n");
					String menu_no = sc.nextLine();
					if("y".equalsIgnoreCase(menu_no)) { // 글 작성을 또 한다고 하면,
						continue;
					}
					else if("n".equalsIgnoreCase(menu_no)) { // 글 작성을 그만한다고 한다면
						break;
					}
					else {
						System.out.println("\n>> Y 또는 N 만 입력해주세요 <<\n");
					}
				}//end of if---
				else if(n==0) {
					System.out.println("\n>> 글 작성이 실패하였습니다 <<\n");
					break;
				}
				else if(n==-1) {
					System.out.println("\n>> 시스템 오류로 인하여 글 작성이 실패하였습니다. <<\n");
					break;
				}
			}while(true);
			
		}// end of board_write()
		
		
		
		
		
		
		

		//품의서 메뉴 메소드(최현우)
		private void menu_approval(MemberLoginDTO mdto, Scanner sc) {
			String menu_no ="";
			outer:
			do {
				System.out.println("\n[1.품의서작성 2.미결재서류조회 3.작성한서류조회 4.결재 0.뒤로가기]");
				System.out.print("▷ 메뉴번호 선택 : ");
				menu_no = sc.nextLine();
				
				switch (menu_no) {
					case "1":	//품의서를 작성선택시
						write_doc(mdto,sc);	//품의서를 작성해주는 메서드 호출
						break;
					case "2":	//미결재서류조회 선택시
						String str_menu_no="";
						int n =view_doc(mdto);	// 내 사원번호에 맞는 미결재서류목록 읽어오기
						if(n==1) {
							do {
								System.out.println("[1.문서번호로 내용조회 0.뒤로가기]");
								System.out.print("▷ 메뉴번호 선택");
								str_menu_no = sc.nextLine();
								switch (str_menu_no) {
									case "1":	//문서번호 조회 선택시
										select_doc(mdto,sc);	//문서번호를 입력받아서 내용 조회하는 메소드
										break;
									case "0":	//뒤로가기 선택시
										break;
									default:	//메뉴에 없는 번호 선택시
										System.out.println("\n>> 메뉴에 없는 번호입니다!! <<\n");
										break;
								}//end of switch---
							}while(!("0".equals(str_menu_no)));
							break;
						}
						else {
							break;
						}
						
					case "3":	// 작성한서류조회 선택시
						me_write_doc(mdto,sc);	//내가 작성한 문서를 조회하는 메소드
						break;
					case "4":	//결재 선택시
						approval_doc(mdto,sc);	//문서번호를 입력받아서 승인해주는 메소드
						break;
						
					case "0":	//뒤로가기 선택시
						break;
					default:	//메뉴에 없는 번호 선택시
						System.out.println("\n >> 메뉴에 없는 번호입니다!! <<");
						break outer;
				}//end of switch---
				
			}while(!("0".equals(menu_no)));
		}//end of private void menu_approval(MemberLoginDTO mdto, Scanner sc)

		
		
	
		
		// 내가쓴 문서를 조회해주는 메소드(최현우)
		private void me_write_doc(MemberLoginDTO mdto, Scanner sc) {
			int empno = mdto.getMemberid();
			List<AppDocumentDTO> docList = new ArrayList<>();
			
			docList = tdao.me_write_doc(empno);
			StringBuilder sb = new StringBuilder();
			if(docList.size()!=0) {	//미결재 서류가 있다면,
				int cnt=0;
				for(int i=0;i<docList.size();i++) {
					cnt++;
					if(cnt==1) {
					System.out.println("\n\n-------------->> "+tdao.empName(mdto)+"이 쓴 미결재 품의서 내역 <<--------------\n"
									 + "문서번호  작성자사번  작성자명   직급   작성일자        제목       결재단계\n"
									 + "-------------------------------------------------------------\n");
					}//end of if--
					sb.append(docList.get(i).getDocumentseq()+"  ");
					sb.append(docList.get(i).getFk_empno()+"  ");
					sb.append(docList.get(i).getEmpname()+"   ");
					sb.append(docList.get(i).getEmpposition()+"  ");
					sb.append(docList.get(i).getRegisterday());
					sb.append(format(docList.get(i).getSubject(),14));
					sb.append(format(String.valueOf(docList.get(i).getLevelno()),12));
					sb.append("\n");
				}//end of for---
				System.out.println(sb);
			System.out.println("-------------------------------------------------------------\n");
			}//end of if--
			else {	//미결재서류목록이 없다면
				System.out.println("\n>> 미결재서류목록이 없습니다 <<");
			}//end of else if---
		
		}//end of me_write_doc(MemberLoginDTO mdto, Scanner sc)



		
		

		// 문서번호를 입력받아서 승인해주는 메소드(최현우)
		private void approval_doc(MemberLoginDTO mdto,Scanner sc) {
			
			int doc_no=0;
			do {
				System.out.print("▷ 문서번호를 입력해주세요");
				try {
					doc_no = Integer.parseInt(sc.nextLine());
				}catch(NumberFormatException e) {
					System.out.println(">> 정수만 입력해주세요!! <<\n");
					continue;
				}//end of try-catch---
				
				
				
				Map<String,String> paraMap = new HashMap<>();
				
				paraMap.put("empno", String.valueOf(mdto.getMemberid()));
				paraMap.put("doc_no", String.valueOf(doc_no));
				
				AppDocumentDTO addto = new AppDocumentDTO();
				addto = tdao.select_doc(paraMap);	// 문서번호를 조회해주는 메소드
				if(addto!=null) {
					System.out.println(addto);			//문서번호를 보여주고,
				}
				else {
					System.out.println(">>"+ doc_no+"번 문서는 없습니다!! <<");
					break;
				}
				System.out.println("▷ 이 문서를 승인하시겠습니까?[Y/N]");
				String menu_no = sc.nextLine();
				
				
				
				if("y".equalsIgnoreCase(menu_no)) {  // 문서 승인을 선택시
					int n = tdao.approval_doc(paraMap);
					if(n==1) {	// 수정 성공이라면
						System.out.println(">> 결재가 완료되었습니다!! <<\n");
					}
					else if(n==0) {	//수정 
						System.out.println(">> 결재가 실패하였습니다 !! <<\n");
					}
					else if(n==-1){	// 데이터베이스에 문제가 있다면
						System.out.println(">> 시스템 오류로 인하여 결재가 실패하였습니다!! \n");
					}
					break;
				}//end of if ---------
				else if("n".equalsIgnoreCase(menu_no)) {
					System.out.println("▷ 불허 사유를 적어주세요 : ");
					String comments = sc.nextLine();
					paraMap.put("comments", comments);
					
					int m = tdao.not_approval_doc(paraMap);
					
					if(m==1) {	// 수정 성공이라면
						System.out.println(">> 불허가 완료되었습니다!! <<\n");
					}
					else if(m==0) {	//수정 
						System.out.println(">> 작업이 실패하였습니다 !! <<\n");
					}
					else if(m==-1){	// 데이터베이스에 문제가 있다면
						System.out.println(">> 시스템 오류로 인하여 작업이 실패하였습니다!! \n");
					}
					break;
				}
			}while(true);
			
			
			
		}//end of approval_doc(Scanner sc)----


		
		

		
		
		
		
		

		//문서번호를 입력받아서 문서내용조회 메서드 생성(최현우)
		private void select_doc(MemberLoginDTO mdto,Scanner sc) {
			
			int doc_no=0;
			System.out.print("▷ 문서번호를 입력해주세요");
			try {
				doc_no = Integer.parseInt(sc.nextLine());
			}catch(NumberFormatException e) {
				System.out.println(">> 숫자만 입력해주세요 !! <<\n");
			}
			Map<String,String> paraMap = new HashMap<>();
			
			paraMap.put("doc_no", String.valueOf(doc_no));
			paraMap.put("empno",String.valueOf(mdto.getMemberid()));
			
			AppDocumentDTO addto = new AppDocumentDTO();
				addto = tdao.select_doc(paraMap);
				if(addto!=null){
					System.out.println(addto.toString());
				}
				else {
					System.out.println(">> 결재하실 서류가 없습니다 <<\n");
				}
			
		}//end of private void approval_doc(int doc_no)
		
		
		
		
		
		
		
		
		//품의서작성 메서드 (최현우)
		private void write_doc(MemberLoginDTO mdto, Scanner sc) {
			int empno = mdto.getMemberid();
			List<Map<String, String>> emplist = tdao.select_doc_approval(empno);//품의서의 결재자목록,문서번호를 얻어오는 메소드
			int doc_seq= tdao.select_doc_seq();	//시퀀스값을 알아오는 메서드
			if(emplist.size()!=0) {	//상사가 있는 사원이라면,
				//현재 로그인된 사원의 결제권자를 계층형쿼리를 통해 알아오고 ,맵에 집어넣고, 맵을 리스트로 만든것을 받아옴.
				// 시퀀스값은 변수값으로 알아온 상태
				String doc_subject="";
				String doc_content="";
				System.out.println("========== >> 품의서 작성 << ============\n"
								 + "※제목은 12자이내, 내용은 100자이내로 작성해주세요");
				do {
					System.out.print("▷ 제목 :");
					doc_subject = sc.nextLine();
					if(doc_subject.length()>30) {	//제목이 30자를 초과한다면
						System.out.println(">> 제목은 12자를 초과할 수 없습니다 <<");
						continue;
					}
					System.out.print("▷ 내용 :");
					doc_content = sc.nextLine();
					if(doc_content.length()>100) {	//내용이 100자를 초과한다면
						System.out.println(">> 내용은 100자를 초과할 수 없습니다 <<");
						continue;
					}
					break;
				}while(true);
				
				do {
					System.out.print("입력된 내용대로 작성하시겠습니까?[Y/N]");
					String menu_no = sc.nextLine();
					if("y".equalsIgnoreCase(menu_no)) {
						break;
					}
					else if("n".equalsIgnoreCase(menu_no)) {
						System.out.println("\n>> 품의서 작성이 취소되었습니다 !! <<\n");
					}
					else {
						System.out.println("\n>> Y 또는 N 만 입력해주세요!! <<\n");
					}
				}while(true);
				
				List<Map<String, String>> paralist = new ArrayList<>();
				for(int i=0;i<emplist.size();i++) {	//입력받은 정보와 결재권자,시퀀스값을 하나의 맵으로 묶어서 넘겨줌.
													//반복횟수는 결재권자의 수 만큼.
						
					Map<String, String> paraMap = new HashMap<>();	//입력받은내용을 파라미터로 넘겨주기 위해서
					paraMap.put("doc_subject", doc_subject);				//제목
					paraMap.put("doc_content", doc_content);				//내용
					paraMap.put("levelno",emplist.get(i).get("levelno"));	//결재단계
					paraMap.put("managerno",emplist.get(i).get("empno"));	//결재권자 사원번호
					paraMap.put("empno",String.valueOf(empno));				//작성자 사원번호
					paraMap.put("doc_seq", String.valueOf(doc_seq));		//문서번호(시퀀스값)
					
					paralist.add(paraMap);	//정보가 담겨있는 Map을 list에 담기
					
				}//end of for---
				int n = tdao.write_doc(paralist);	// 품의서 테이블에 insert 해주는 메서드 호출
				
				if(n==1) {	// 데이터가 정상적으로 입력되었다면,
					System.out.println("\n>> 품의서 작성이 완료되었습니다!! <<\n");
				}
				else {	//데이터 입력에 실패했다면,
					System.out.println("\n>> 시스템 오류로 인하여 데이터 입력이 취소되었습니다 <<\n");
				}
			}//end of if(emplist.size()!=0)-------
			else if(emplist.size()==0){	//상사가 없는 사원이라면,
				System.out.println("\n>> 상사가 없는 사원입니다! <<\n");
			}
			
			
		}//end of private void write_doc(MemberLoginDTO mdto, Scanner sc)
				
		
		
		
		
		
		
		
		
		
		

	      //직원등록을 해주는 메소드(최현우)
	      private void register_emp(Scanner sc) {
	         Date now = new Date();
	         SimpleDateFormat sdfrmt = new SimpleDateFormat("yy");
	         String year = sdfrmt.format(now);
	         
	         do {
	            System.out.println("=========>> 직원 등록 <<=========");
	            try {
	               System.out.print("[취소는 0번]직원명 : ");
	               String empname = sc.nextLine();
	               if("0".equals(empname)) {
	                  break;
	               }
	               
	               System.out.println("※부서코드 목록\n"
	                           + "총무부:01 인사부:02 기획부:03 회계부:04 "
	                           + "경영지원부:05 영업부:06 마케팅부:07  "
	                           + "생산부:08 자재부:09 ");
	                           
	               System.out.print("[취소는 0번]부서코드 : ");
	               String fk_deptno = sc.nextLine();
	               if("0".equals(fk_deptno)) {
	                  break;
	               }
	               if( !(00<Integer.parseInt(fk_deptno)&&Integer.parseInt(fk_deptno)<10) ) {
	                  System.out.println(">> 없는 부서코드입니다! 양식에 맞게 작성해주세요. <<\n");
	                  continue;
	               }
	               
	               System.out.print("[취소는 0번]직급 : ");
	               String empposition = sc.nextLine();
	               if("0".equals(empposition)) {
	                  break;
	               }
	               System.out.println("※단위 : 만원");
	               System.out.print("[취소는 0번]연봉 : ");
	               int salary = Integer.parseInt(sc.nextLine());
	               if(salary==0) {
	                  break;
	               }
	               
	               System.out.print("[취소는 0번](- 없이 입력해주세요)\n "
	                            + "          주민번호 : ");
	               Long jubun = Long.parseLong(sc.nextLine());
	               if(jubun==0) {
	                  break;
	               }
	               System.out.print("직속상사 사번 : ");
	               int managerno = Integer.parseInt(sc.nextLine());
	               if(managerno==0) {
	                  break;
	               }
	               
	               
	               String empno = year+fk_deptno+select_emp_seq();   //사원번호 양식에 맞게 사원번호 생성
	               EmployeesDTO edto = new EmployeesDTO();
	               edto.setEmpno(Integer.parseInt(empno));
	               edto.setEmpname(empname);
	               edto.setFk_deptno(Integer.parseInt(fk_deptno));
	               edto.setEmpposition(empposition);
	               edto.setSalary(salary);
	               edto.setJubun(jubun);
	               edto.setManagerno(managerno);
	               
	               String menu_no="";
	               int n=0;
	               do {
	                  System.out.print("입력하신 정보로 직원을 등록하시겠습니까?[Y/N]");
	                  menu_no = sc.nextLine();
	                  if("y".equalsIgnoreCase(menu_no)) {
	                     n = tdao.register_emp(edto);
	                     break;
	                  }//end of if---
	                  else if("n".equalsIgnoreCase(menu_no)) {
	                     break;
	                  
	                  }//end of else if ---
	                  else {
	                     System.out.println(">> Y 또는 N 만 입력해주세요!! <<");
	                  }//end of else--
	               }while(true);
	               
	               
	               
	               if(n==2) {
	                  System.out.println(">> 등록이 완료되었습니다 !! <<");
	                  System.out.println(">> 등록을 계속하시겠습니까?[Y/N] ");
	                  menu_no = sc.nextLine();
	                  if("y".equalsIgnoreCase(menu_no)) {
	                     continue;
	                  }
	                  else if("n".equalsIgnoreCase(menu_no)) {
	                     break;
	                  }
	                  else {
	                     System.out.println(">> Y 또는 N 만 입력해주세요!! <<");
	                  }
	               }//end of if(등록성공)----
	               else if(n==-1) {
	                  System.out.println(">> 이미 등록되어있는 직원입니다. <<");
	                  break;
	               }
	               else {
	                  System.out.println(">> 등록이 실패하였습니다 !! <<");
	                  break;
	               }
	            }catch(NumberFormatException e) {
	               System.out.println(">> 양식에 맞게 작성해주세요!! <<");
	            }//end of try-catch-------
	         }while(true);// end of do-while
//	      ============!! 사원 테이블에 연락처 컬럼 추가시 주석 풀기 !!=====================
//	         System.out.print("[010-1234-5678 형식으로 작성] 연락처 : ");
//	         String phone = sc.nextLine();
//	         if(phone.trim().isEmpty()) {
//	            System.out.println(">> 연락처는 공백이 없어야 합니다! <<");
//	         }
//	      ============!! 사원 테이블에 연락처 컬럼 추가시 주석 풀기 !!=====================
	         
	         
	         
	      }//end of register_emp(Scanner sc)

	      
	      	
			

		
		

		
		
		
		
		
		
		
		
		//출/퇴근 기록 메소드(최현우)
		private void attendance_rcd(MemberLoginDTO mdto, Scanner sc) {
			Date now = new Date();
			SimpleDateFormat sdfrmt = new SimpleDateFormat("HH시 mm분 ss초");
			String time = sdfrmt.format(now);
			String menu_no = "";
			outer:
			do {
				System.out.println("\n[1.출근 2.퇴근 3.조퇴 4.이번달 출퇴근기록 조회 0.뒤로가기]");
				System.out.print("▷어떤 것을 기록하시겠습니까?");
				menu_no = sc.nextLine();
				
				int n=0;
				
				switch (menu_no) {
					case "1":	// 출근 선택시
						menu_no="";
			 			do {
			 			System.out.print(">> 출근처리를 하시겠습니까?[Y/N] : ");
			 			menu_no = sc.nextLine();
			 			   if("y".equalsIgnoreCase(menu_no)) {
			 				   	break;
			 			   }
			 			   else if("n".equalsIgnoreCase(menu_no)){
			 					continue outer;
			 			   }
			 			   else {
			 					System.out.println(">> Y 또는 N만 선택해주세요!<<");
			 			   }
			 			}while(true);
						
						n = tdao.attendace_rcd(mdto);	// 출근 기록 메소드 호출
						
						if(n == 1) {	//출근 기록이 성공했다라면(n=1이라는건 성공)
							System.out.println("\n>> 출근처리가 완료되었습니다! 현재시각("+time+") << \n");
						}
						else if(n == -1) // 출근을 하루에 두번 찍는다면
							System.out.println("\n>> 오늘 이미 출근하셨습니다. <<\n");
						else if(n == 0) // 알수없는 이유로 출근기록에 실패한다면
							System.out.println("\n>> 시스템 장애로 인하여 출근기록이 불가합니다 <<\n");
						break;
					
					case "2":	//퇴근 선택시
						do {
				 			System.out.print(">> 퇴근처리를 하시겠습니까?[Y/N] : ");
				 			menu_no = sc.nextLine();
				 			   if("y".equalsIgnoreCase(menu_no)) {
				 				   	break;
				 			   }
				 			   else if("n".equalsIgnoreCase(menu_no)){
				 					continue outer;
				 			   }
				 			   else {
				 					System.out.println(">> Y 또는 N만 선택해주세요!<<");
				 			   }
			 			}while(true);
						n = tdao.leave_rcd(mdto);
						
						if(n == 1) {	//퇴근 기록이 성공했다라면(n=1이라는건 성공)
							System.out.println("\n>> 퇴근처리가 완료되었습니다! 현재시각("+time+") <<\n");
						}
						else if(n == 0) // 출근중이 아닐때 퇴근을 찍는다면
							System.out.println("\n>> 출근중이 아니므로 퇴근을 할 수 없습니다!! <<\n");
						else if(n == -1) // 알수없는 이유로 출근기록에 실패한다면
							System.out.println("\n>> 시스템 오류로 인하여 퇴근기록이 불가합니다 <<\n");
						break outer;
					
					
					case "3":	//조퇴 선택시
						do {
				 			System.out.print(">> 조퇴처리를 하시겠습니까?[Y/N] : ");
				 			menu_no = sc.nextLine();
				 			   if("y".equalsIgnoreCase(menu_no)) {
				 				   	break;
				 			   }
				 			   else if("n".equalsIgnoreCase(menu_no)){
				 					continue outer;
				 			   }
				 			   else {
				 					System.out.println("\n>> Y 또는 N만 선택해주세요! <<\n");
				 			   }
			 			}while(true);
						
						n = tdao.early_leave(mdto);
						
						if(n == 1) {	//조퇴 기록이 성공했다라면(n=1이라는건 성공)
							System.out.println("\n>> 조퇴처리가 완료되었습니다! 현재시각("+time+") <<\n");
						}
						else if(n == 0) // 출근중이 아닐때 조퇴을 찍는다면
							System.out.println("\n>> 출근중이 아니므로 조퇴를 할 수 없습니다 <<\n");
						else if(n == -1) // 알수없는 이유로 출근기록에 실패한다면
							System.out.println("\n>> 시스템 오류로 인하여 조퇴기록이 불가합니다 <<\n");
						break outer;
					
					case "4":	//이번달 출퇴근기록 조회선택시
						List<StringBuilder> sblist = new ArrayList<>();
						sblist = tdao.select_attendance_leave(mdto);
						
						if(sblist.size()!=0) {
							for(int i=0;i<sblist.size();i++) {
								System.out.println(sblist.get(i));
							}//end of for-----
						}//end of if---
						else {
							System.out.println("\n>> 이번달 출근내역이 없습니다!! <<");
						}
						break;
					case "0":	//뒤로가기 선택시
						break;
					default:	// 메뉴에 없는 번호 선택시
						System.out.println("\n>> 메뉴에 없는 번호입니다!! <<\n");
						break;
				}// end of switch----
			}while(!("0".equals(menu_no)));
			
			
		}//end of private void attendance_rcd(MemberLoginDTO mdto, Scanner sc)-----
		
		
		
		
		
		
		
		
		
		//나의 사원번호에 맞는 미결재서류목록을 보여주는 메소드(내가결재해야할 서류)(최현우)
		private int view_doc(MemberLoginDTO mdto) {
			
			int empno = mdto.getMemberid();
			List<AppDocumentDTO> docList = new ArrayList<>();
			
			docList = tdao.view_doc(empno);
			StringBuilder sb = new StringBuilder();
			if(docList.size()!=0) {	//미결재 서류가 있다면,
				int cnt=0;
				for(int i=0;i<docList.size();i++) {
					cnt++;
					if(cnt==1) {
					System.out.println("\n\n-------------------->> "+tdao.empName(mdto)+"님 미결재 서류목록 <<------------------\n"
									 + "문서번호  작성자사번  작성자명   직급   작성일자        제목       결재단계\n"
									 + "-------------------------------------------------------------\n");
					}//end of if--
					sb.append(docList.get(i).getDocumentseq()+"  ");
					sb.append(docList.get(i).getFk_empno()+"  ");
					sb.append(docList.get(i).getEmpname()+"   ");
					sb.append(docList.get(i).getEmpposition()+"  ");
					sb.append(docList.get(i).getRegisterday()+"  ");
					sb.append(docList.get(i).getSubject()+"      ");
					sb.append(docList.get(i).getLevelno()+"\n");
				}//end of for---
				System.out.println(sb);
			System.out.println("-------------------------------------------------------------\n"
							 + "                                                     총 "+docList.size()+"건\n");
			return 1;
			}//end of if--
			else {	//미결재서류목록이 없다면
				System.out.println("\n>> 미결재서류목록이 없습니다 <<");
				return 0;
			}
			
		}//end of view_doc(MemberLoginDTO mdto)
		
		
		
		
		
		
		//40글자마다 줄 바꿔서 출력해주는 메소드
		private String Enter(String a) {
			if(40<a.length()&&a.length()<=80) {
				a = a.substring(0,40)+"\n"
					+a.substring(40);
			}//end of if---
			
			if(80<a.length()&&a.length()<=120) {
				a=a.substring(0,40)+"\n"
					+a.substring(40,80)+"\n"
					+a.substring(80);
						
			}//end of if--
			if(120<a.length()&&a.length()<160) {
				a=a.substring(0,40)
					+a.substring(40,80)+"\n"
					+a.substring(80,120)+"\n"
					+a.substring(120);
			}//end of if---
			if(160<a.length()&&a.length()<200) {
				a = a.substring(0,40)+"\n"
					+a.substring(40,80)+"\n"
					+a.substring(80,120)+"\n"
					+a.substring(120,160)+"\n"
					+a.substring(160);
			}//end of if--
			return a;
		}//end of Enter(String a)
		
		
		
		
		
		
		// 사원번호 시퀀스값을 알아오고, 자릿수를 맞춰주는 메소드(최현우)
		private String select_emp_seq() {
			String result="";
			int n = tdao.select_emp_seq();
			result = String.valueOf(n);
			
			for(int i=result.length();i<4;i++) {
				result = "0"+result;
			}//end of for--
			
			return result;
		}//end of select_emp_seq()
		
		
		
		
		
		// 부서번호 시퀀스값을 알아오고, 자릿수를 맞춰주는 메소드(최현우)
		private String select_department_seq() {
			String result="";
			int n = tdao.select_dept_seq();
			result = String.valueOf(n);
			
			for(int i=result.length();i<2;i++) {
				result = "0"+result;
			}//end of for--
			
			return result;
		}//end of select_emp_seq()
				
		
		
		
		
		
		
		
		//글자수를 맞춰주고 가운데정렬 메소드(최현우)
		public String format(String a, int b) {
			String result="";

			int c = (b-a.length())/2;
			int cnt=0;
			for(int i=a.length();i<b;i++) {
				cnt++;
				if(cnt==c) {
					result += a;
				}
				else {
					result +=" ";
				}
			}//end of for--
			return result;
		}//end of select_emp_seq()

		
		
		
		
		
		
		
		
		
		
		// 게시판 관리자 로그인시 나오는 레이아웃(최현우)
		private void boardadminMenu(SuperAdminDTO superdto, Scanner sc) {
			String menu_no="";
			do {
				System.out.println("\n\n---------- >> [게시판관리자] 로그인중 << ----------\n"
						         + "1.사내게시판 가기    2.글 삭제          3.공지사항 등록\n"
								 + "4.회원정지/해제      5.로그아웃\n"
				                 + "--------------------------------------------");
				System.out.print("▷ 메뉴번호 선택 :");
				menu_no = sc.nextLine();
				switch (menu_no) {
					case "1":	// 게시판관리자 사내게시판가기(최현우)
						super_go_board(superdto,sc);
						break;
					case "2":	// 게시판관리자 글 삭제(최현우)
						super_delete_board(superdto,sc);
						break;
					case "3":	// 공지사항 등록
						update_notice(sc);
		               break;
					case "4":	// 회원정지
						member_ban(sc);
						break;
					case "5":	// 로그아웃
						break;
					default:	//메뉴에 없는 번호 선택시
						System.out.println("\n>> 메뉴에 없는 번호입니다!! <<\n");
						break;
				}//end of switch------
			}while(!("5".equalsIgnoreCase(menu_no))); //end of do-while--------
			
			
			
		}// end of private void boardadminMenu(BoardAdminDTO superdto, Scanner sc) ---------------------
		


		//게시판관리자가 글 삭제하는 메소드(최현우)
		private void super_delete_board(SuperAdminDTO superdto, Scanner sc) {
			int n=0;
			int boardno=0;
				outer:
	            do {
	            	try{
						
	            		System.out.print("▷ 삭제할 게시글의 글번호를 입력해주세요 : ");
	            		boardno = Integer.parseInt(sc.nextLine());
					}catch (NumberFormatException e) {
						System.out.println("\n>> 글번호는 숫자만 입력해주세요!! << \n");
						continue;
					}//end of try-catch--
	                do { 
		            	System.out.print(">> 정말로 글을 삭제하십니까? [Y/N]");
		                 String yn = sc.nextLine();
		                 
		                 if("y".equalsIgnoreCase(yn)) {
		                	 n = tdao.super_delete_board(boardno);	//글번호만 가지고 바로 삭제해주는 메소드
		                	 break;
		                 }
		                 else if("n".equalsIgnoreCase(yn)) {
		                	 System.out.println("\n>> 글삭제가 취소되었습니다!! <<\n");
		                	 break outer;
		                 }
		                 else {
		                    System.out.println("\n>> Y 또는 N 만 입력하세요!! <<\n");
		                 }
	                }while(true);	//end of do-while----
	                if(n==1) {	//글삭제가 성공한경우
	                	System.out.println("\n>> "+boardno+"번 글이 삭제완료되었습니다!! <<\n");
	                	break;
	                }//end of if---
	                else if(n==0) {	//글삭제가 성공한경우
	                	System.out.println("\n>> 글삭제가 실패하였습니다. 다시 시도해주세요 <<\n");
	                	break;
	                }//end of if---
	                else if(n==-1) {	//글삭제가 성공한경우
	                	System.out.println("\n>> 글삭제가 취소되었습니다. <<\n");
	                	break;
	                }//end of if---
	           }while(true);
			
			
		}//end of super_delete_board(SuperAdminDTO superdto, Scanner sc)




		//특정 회원 게시판이용정지/해제 메소드(최현우)
		private void member_ban(Scanner sc) {
			String menu_no ="";
			do {
				System.out.println("\n[1.사내게시판이용정지  2.이용정지해제  3.정지회원내역조회 0.뒤로가기]");
				System.out.print("▷");
				menu_no = sc.nextLine();
				switch (menu_no) {
					case "1":	//사내게시판이용정지 선택시
						ban(sc);
						break;
					case "2":	//이용정지해제 선택시
						unban(sc);
						break;
					case "3":	//이용정지회원내역 조회
						select_ban(sc);	
						break;
					case "0":	//뒤로가기 선택시
						break;
					default:	//메뉴에 없는 번호 선택시
						System.out.println("\n>> 메뉴에 없는 번호입니다!! <<\n");
						break;
				}//end of switch---
			}while(!("0".equals(menu_no)));
			
		}//end of member_ban(Scanner sc)------

		
		
		
		
		
		//이용정지회원내역 조회메소드(최현우)
		private void select_ban(Scanner sc) {
			String menu_no="";
			do {
				System.out.print("\n 이용정지회원내역을 조회하시겠습니까?[Y/N]");
				menu_no = sc.nextLine();
				
				if("y".equals(menu_no)) {
					List<MemberLoginDTO> banlist = new ArrayList<>();
					banlist = tdao.select_ban();
					int cnt=0;
					for(int i=0; i<banlist.size();i++) {
						cnt++;
						if(cnt==1) {
							System.out.println("-------------->> 게시판 이용정지회원 내역 << --------------\n"
											+ " 회원아이디\t\t            정지사유\n"
											+ "-----------------------------------------------------");
						}//end of if(한번만 출력할 내용)--
						System.out.println(banlist.get(i).getMemberid()+"\t\t"+
											banlist.get(i).getComments());
					}//end of for--
					break;
				}//end of if--조회한다고 하면 끝
				else if("n".equals(menu_no)) {
					System.out.println("\n>> 조회가 취소되었습니다!! <<\n");
					break;
				}// 조회 취소시
				else {	//메뉴에 없는 번호 입력시
					System.out.println("\n>> Y 또는 N 만 입력해주세요!! <<\n");
				}
			}while(true);
		}//end of select_ban




		//회원이용정지 해제메소드(최현우)
		private void unban(Scanner sc) {
			do {
				try {
					System.out.print("▷ 정지 해제시킬 회원의 아이디를 입력하세요 : ");
					int memberid = Integer.parseInt(sc.nextLine());
					
					int n = tdao.unban(memberid);
					
					if(n==1) {
						System.out.println("\n>>"+memberid+" 회원의 정지가 해제되었습니다 <<\n");
					}
					else if(n==0){
						System.out.println("\n>> 정지해제가 취소되었습니다. <<\n");
					}
					else {
						System.out.println("\n>> 시스템오류로 정지해제가 취소되었습니다. <<\n");
					}
					break;
				} catch (NumberFormatException e) {
					System.out.println("\n>> 숫자만 입력해주세요! <<\n");
				}//end of try-catch
			}while(true);//end of do-while-----

			
			
			
		}//end of reverse_ban(Scanner sc)--




		//회원이용정지메소드(최현우)
		private void ban(Scanner sc) {
			do {
				try {
					System.out.print("▷ 이용정지시킬 회원의 아이디를 입력하세요 : ");
					int memberid = Integer.parseInt(sc.nextLine());
					System.out.println("▷ 정지사유를 20자이내로 적어주세요 : ");
					String comments = sc.nextLine();
					
					Map<String,String> paraMap = new HashMap<>();
					
					paraMap.put("memberid", String.valueOf(memberid));
					paraMap.put("comments", comments);
					int n = tdao.ban(paraMap);
					
					if(n==1) {
						System.out.println("\n>>"+memberid+" 회원의 이용이 정지되었습니다 <<\n");
					}
					else if(n==0){
						System.out.println("\n>> 이용정지가 취소되었습니다. <<\n");
					}
					else {
						System.out.println("\n>> 시스템오류로 이용정지가 취소되었습니다. <<\n");
					}
					break;
				} catch (NumberFormatException e) {
					System.out.println("\n>> 숫자만 입력해주세요! <<\n");
				}//end of try-catch
			}while(true);//end of do-while-----
		}//end of ban(Scanner sc)




		// 공지사항 수정메소드(조상운)
		private void update_notice(Scanner sc) {
			do {
				String notice_contents="";
	            System.out.print("▷ 공지사항 내용 : ");
	             notice_contents = sc.nextLine();
	            int result = tdao.updateNotice(notice_contents);
	            
	            if(result > 0) {
	               System.out.println("\n>> 공지사항 등록에 성공하였습니다!");
	               break;
	            }
	            else {
	               System.out.println("\n>> 공지사항 등록에 실패하였습니다!");
	               break;
	            }
			}while(true);
			
		}//end of update_notice(Scanner sc);


		
		

			
			

		
		
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
        //////////////////////////////////채용관리/////////////////////////////////////////
---------------------------------------------------------------------------------------------------
///////////////////////////////////////////채용관리///////////////////////////////////////////////////
///////////////////////////////////////////채용관리//////////////////////////////////////////////////
---------------------------------------------------------------------------------------------------

*/

// 채용관리메뉴(임선우)
public void applicantManage(Scanner sc) {
  
  ApplicantDTO apdto = new ApplicantDTO();
  String menuNo ="";
  do {
     System.out.println("\n------------------ >> 채용관리 << ----------------\n"
     		         + "1. 입사지원자등록  2.입사지원자조회  3.뒤로가기 \n"   
                      + "-----------------------------------------------");

     
     System.out.print("▷ 메뉴번호 선택: ");
     menuNo = sc.nextLine();
     
     switch (menuNo) {
     case "1": // 입사지원자등록
        appliRegister(apdto,sc);
        break;
        
     case "2": // 입사지원자조회
           do {
              System.out.println("\n------------------- >> 입사지원서조회<< -----------------\n"
                           + "1.올해상반기지원자조회  2.올해하반기지원자조회  3.이번연도지원자현황  \n"
                           + "4.지원번호로 조회     5.뒤로가기");
              System.out.println("-----------------------------------------------------");
              System.out.print("▷ 메뉴번호 : ");
              menuNo = sc.nextLine();
              
              int n_apcNo = 0;
              
                 if("1".equals(menuNo) || "2".equals(menuNo) || "3".equals(menuNo) ) {
                    searchAppliList(menuNo); // 입사지원자조회 
                 }
                 
                 else if("4".equals(menuNo)) { // 지원번호로 조회
                    do {
                       System.out.print("▷ 조회하고자 하는 지원번호 : " );
                       String apcNo = sc.nextLine(); // 지원번호 입력값
                       
                       try {
                          n_apcNo = Integer.parseInt(apcNo);  // 숫자만 가능
                       }catch(NumberFormatException e) {
                             System.out.println(">>[경고] 지원자 조회는 지원번호로만 가능합니다.\n");
                             break;
                          }
                       apdto = tdao.searchAppli(n_apcNo);
                          if(apdto != null) {
                             System.out.println(apdto); // dto에 to_string 오버라이딩 해놨다
                             
                          }
                          
                          else {
                             System.out.println(">>입력하신 지원번호에 해당하는 지원자가 존재하지 않습니다.<<\n");
                          
                          }
                          break;
                    }while(true); // end of do while --------------------------------------
                 }
                 
                 else if("5".equals(menuNo)) {
                    break;
                 }
                 
                 else {
                    System.out.println(">> 메뉴에 없는 번호 입니다. <<");
                 }
                    
          } while (!"5".equals(menuNo)); // 입사지원자 조회 메뉴 do while---------------------------	               
     
     case "3":// 뒤로가기
     	break;
           
     default:
        System.out.println(">> 메뉴에 없는 번호 입니다. ");
        
     }// end of switch-----------------------
  }while(!"3".equals(menuNo));
}// end of private void applicantManage(ApplicantDTO acdto, Scanner sc)--------------






/*
---------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
---------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
---------------------------------------------------------------------------------------------------
*/






//입사지원자등록(임선우)
private void appliRegister(ApplicantDTO apdto, Scanner sc) {
	
	System.out.println("\n------------- >> 형식 예시 << -------------\n"
			  + "1. 지원자명    : 김땡땡 \n"
			  + "2. 희망부서번호 : 9\n"
           + "3. 연락처     : 010-0000-0000\n"
           + "4. 주소      : 서울특별시 마포구\n"
           + "5. 지원일자   : 2022-01-01");

	System.out.println("\n----------- >> 입사지원자 등록 << -----------");
	
	System.out.print("1. 지원자명    : ");
	String applicant = sc.nextLine();
	
	System.out.print("2. 희망부서번호 : ");
	String fk_hope_depno = sc.nextLine();
	
	System.out.print("3. 연락처     : ");
	String mobile = sc.nextLine();
	
	System.out.print("4. 주소      : ");
	String address = sc.nextLine();
	
	System.out.print("5. 지원일자   : ");
	String registerdate = sc.nextLine();
	
	try {// 부서번호를 정수로 입력하지 않은 경우
	   apdto.setFk_hope_depno(Integer.parseInt(fk_hope_depno));
	} catch ( NumberFormatException e) {
	   System.out.println("희망부서는 부서번호로 작성해주세요.");
	}
	
	apdto.setApplicant(applicant);
	apdto.setMobile(mobile);
	apdto.setAddress(address);
	apdto.setRegisterdate(registerdate);
	
	int n = tdao.appliRegister(apdto);
	
	if(n==1) {
	   System.out.println("\n >>> 입사지원자가 성공적으로 등록되었습니다. <<<\n");
	}
	else {
	   System.out.println("\n >>> 입사지원자가 등록이 실패되었습니다. <<<\n");
	}
			
}//end of appliRegister(ApplicantDTO apdto, Scanner sc)






/*
---------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
---------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
---------------------------------------------------------------------------------------------------
*/






// 입사지원자 조회(임선우)
private void searchAppliList(String menuNo) {
	
	if("1".equals(menuNo)){
       System.out.println("\n--------------------------- >> 올해상반기지원자조회 << ---------------------------");
    }
    
    else if("2".equals(menuNo)){
       System.out.println("\n--------------------------- >> 올해하반기지원자조회 << ---------------------------");
    }
    
    else{
       System.out.println("\n---------------------------- >> 이번연도지원자현황 << ---------------------------");
    }
    
    System.out.println("지원번호    지원자명    희망부서번호      연락처            주소               지원일자  ");
    System.out.println("----------------------------------------------------------------------------");
    
    List<ApplicantDTO> searchAppliList = tdao.searchAppliList(menuNo);
    
    StringBuilder sb = new StringBuilder();
    
    if(searchAppliList.size() > 0) {
       for(ApplicantDTO aptdo : searchAppliList) {
          sb.append(aptdo.getApplicantno()+"        "+
                aptdo.getApplicant()+"       "+
                aptdo.getFk_hope_depno()+"          "+
                aptdo.getMobile()+"    "+
                aptdo.getAddress()+"           "+
                aptdo.getRegisterdate()+"\n");
       }// end of for ----------------------------
       
       System.out.println(sb.toString());
    
    }
    
    else {// 지원자가 존재하지 않을 경우
       System.out.println(">> 지원자가 존재하지 않습니다.<< \n");
    }
	
}// end of private void searchAppliList(String menuNo) 






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






 // 사원정보 조회(임선우)
private void emSearch(String menu_no) {
  List<EmployeesDTO> empList = new ArrayList<>();
  Map<String, String> paraMap = new HashMap<>();
  Scanner sc = new Scanner(System.in);
  
  String empno = "";
  String fk_deptno  = "";
  String jrpgrade = "";
  String fromDate = "";
  String toDate = "";
  
  if("2".equals(menu_no)) {
 	do {
     System.out.print("▷ 조회하고 싶은 사원번호를 입력하세요.");
     try {
     	Integer.parseInt(empno = sc.nextLine());
     	paraMap.put("empno",empno);
     	break;
        }catch (NumberFormatException e) {
           System.out.println("[경고] 사원번호는 정수로 입력하세요.");
        }
 	}while (true);
  }
  
  else if("3".equals(menu_no)) {
 	do {
     System.out.print("▷ 부서번호를 입력하세요.");
     try {
     	Integer.parseInt(fk_deptno  = sc.nextLine());
     	paraMap.put("fk_deptno",fk_deptno);
     	break;
     }catch (NumberFormatException e) {
 	 System.out.println("[경고] 부서번호는 정수로 입력하세요.");
    }
	}while (true);
}
  
  else if("4".equals(menu_no)) {
     System.out.print(">> 등급표 <<\n"
     			   + "S => 총점 40점\n"
     			   + "A => 총점 39~30점\n"
     			   + "B => 총점 29~20점\n"
     			   + "C => 총점 19이하\n"
     			   + "▷ 조회하고자 하는 인사평가 등급을 입력하세요 >>");
     jrpgrade = sc.nextLine().toUpperCase(); // 입력한 값을 대문자로 변경
     paraMap.put("jrpgrade",jrpgrade);
  }
  
  else if("5".equals(menu_no)){ 
     System.out.println("▷ 조회하고자 하는 날짜의 시작을 입력하세요. 예) 2022-06");
     fromDate = sc.nextLine();
     
     System.out.println("▷ 조회하고자 하는 날짜의 끝을 입력하세요. 예) 2022-07");
     toDate = sc.nextLine();
     paraMap.put("fromDate",fromDate);
		paraMap.put("toDate",toDate);
  }
  
 	empList = tdao.searchEmp(menu_no,paraMap);
 if(empList.size() > 0) {
    System.out.println("\n----------------------------------------------- >> 사원정보 << ----------------------------------------------------------------------------------");
    System.out.println("사원번호        직급     사원명   부서번호      거주지             연봉       수당          이메일               주민번호            입사일자             사수번호");
    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
        
    for(EmployeesDTO edto :empList) {
       System.out.println(format(edto.getEmpno()+"",10)+
		            		 format(edto.getEmpposition(),12)+
		    				 format(edto.getEmpname(),8)+
							 format(edto.getFk_deptno()+"",5)+
							 format(edto.getAddress()+"",20)+
							 format(edto.getSalary()+"",15)+
							 format(edto.getCommission_pct()+"",8)+
							 format(edto.getEmail()+"",20)+
							 format(edto.getJubun()+"",20)+
							 format(edto.getHiredate(),24)+
							 format(edto.getManagerno()+"",10)); 
        }// end of for--------------------------------------------------------
    }
 else {
     System.out.println("사원이 존재하지 않습니다.");
 }
}//  private void emSearch(String menu_no) -------------






/*
---------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
---------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
---------------------------------------------------------------------------------------------------
*/






// 사원수정(임선우)
private void emUpdate() {
	Scanner sc = new Scanner(System.in);
 EmployeesDTO edto = new EmployeesDTO();
 Map<String, String> paraMap = new HashMap<>();
 String fk_empno ="";
 String empposition = "";
 String fk_deptno= "";
 String salary= "";
 String commission_pct= "";
 String managerno= "";
 int flag = 0;
 do {
    System.out.println("\n >> 사원정보 수정하기 <<");
    System.out.print("▷ 수정 할 사원번호 : ");
    fk_empno = sc.nextLine();
    
    try {
       Integer.parseInt(fk_empno);
       flag++;
       break;
    }catch (NumberFormatException e) {
       System.out.println("[경고] 올바른 사원번호를 입력하세요.");
       break;
    }
 }while(true);
 
 if(flag != 0 && !(fk_empno.trim().isEmpty())) {
 	edto =tdao.empInfo(fk_empno);   
 
       if(edto.getEmpno() != 0) {
	        do {
	        	String yn = "";
	          
	              System.out.println("-------------------- 사원정보 ---------------------\n"
	                             + "1.  사원번호(아이디) : " +edto.getEmpno()+"\n"
	                             + "2.  사원명 		: "+edto.getEmpname()+"\n"
	                             + "3.  부서번호 		: "+edto.getFk_deptno()+"\n"
	                             + "4.  직급 			: "+edto.getEmpposition()+"\n"
	                             + "5.  거주지 		: "+edto.getAddress()+"\n"
	                             + "6.  연봉			: "+edto.getSalary()+"\n"
	                             + "7.  수당 			: "+edto.getCommission_pct()+"\n"
	                             + "8.  이메일 		: "+edto.getEmail()+"\n"
	                             + "9.  주민번호 		: "+edto.getJubun()+"\n"
	                             + "10. 입사일자 		: "+edto.getHiredate()+"\n"
	                             + "11. 사수번호 		: "+edto.getManagerno()+"\n"
	                             
	                             + "---------------------------------------------------\n");
	              System.out.println("▷ 개인정보를 수정하시겠습니까? [Y/N]");
	              yn = sc.nextLine();
	              
	              if("y".equalsIgnoreCase(yn)) {
	                 System.out.println("---------- "+edto.getEmpname()+"("+edto.getEmpno()+")"+"님의 정보수정----------");
	                 
	                 System.out.print("▷ 직급[변경하지 않으려면 엔터] : "); 
	                 empposition = sc.nextLine();
	                 if(empposition != null && empposition.trim().isEmpty()) {
	                    empposition = edto.getEmpposition();
	                 }
	                 System.out.print("▷ 부서번호[변경하지 않으려면 엔터] : "); 
	                 fk_deptno = sc.nextLine();
	                 if(fk_deptno != null && fk_deptno.trim().isEmpty()) {
	                    fk_deptno = String.valueOf(edto.getFk_deptno());
	                 }
	                System.out.print("▷ 급여[변경하지 않으려면 엔터] : "); 
	                salary = sc.nextLine();
	                if(salary != null && salary.trim().isEmpty()) {
	                   salary = String.valueOf(edto.getSalary());
	                }
	                System.out.print("▷ 수당[변경하지 않으려면 엔터] : "); 
	                commission_pct = sc.nextLine();
	                if(commission_pct != null && commission_pct.trim().isEmpty()) {
	                   commission_pct = String.valueOf(edto.getCommission_pct());
	                }
	
	                System.out.print("▷ 사수번호[변경하지 않으려면 엔터] : "); 
	                managerno = sc.nextLine();
	                if(managerno != null && managerno.trim().isEmpty()) {
	                	managerno =String.valueOf(edto.getManagerno()) ;
	                } 
	                
	                 paraMap.put("empposition", empposition);
	                 paraMap.put("fk_deptno", fk_deptno);
	                 paraMap.put("salary", salary);
	                 paraMap.put("commission_pct", commission_pct);
	                 paraMap.put("managerno", managerno);
	                 paraMap.put("empno", edto.getEmpno()+"");
	                 
	                 int result = tdao.updateEmp(paraMap);// 수정 메소드
	                 
	                 if(result == 1) { // 수정을 성공한 경우
	                    System.out.println("성공적으로 수정되었습니다.");
	                 }
	                 else { // 수정을 실패한 경우
	                    System.out.println("죄송합니다. 시스템상 오류로 취소되었습니다.");
	                 }
	                 break;
	              }
	              else if("n".equalsIgnoreCase(yn)) {
	            	  System.out.println("수정을 취소하셨습니다.");
	                 break;
	              }
	              else { 
	                 System.out.println(">> [경고] Y 또는 N 만 입력하세요 <<");
	              }
	           
	        } while(true);
       }
       else {
       	System.out.println(">> 존재하지 않는 사원입니다.");
       }
 }


} // private void emUpdate()-------------------------------






/*
---------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
---------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
---------------------------------------------------------------------------------------------------
*/





	// 사원정보 삭제(임선우)
	private void emInfoDelete() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n >> 사원정보 삭제하기 <<");
        String empno = "";
        int flag = 0;
        int result=0;
            System.out.print("▷ 삭제할 사원번호 : ");
            empno = sc.nextLine();
            
            try {
               Integer.parseInt(empno);
               flag++;
            }catch (NumberFormatException e) {
               System.out.println("[경고] 올바른 사원번호를 입력하세요.");
            }
            
         if(flag != 0 && !(empno.trim().isEmpty())) {
        	result = tdao.emInfoDelete(empno);  
        	
        	 if(result == 1) { // 삭제를 성공한 경우
                 System.out.println("성공적으로 삭제되었습니다.");
             }
        	 else if(result == -1) {// 삭제를 실패한 경우
        		 System.out.println("죄송합니다. 시스템상 오류로 취소되었습니다.");
        	 }
         }
		
	}// end of private void emInfoDelete() ---------------------
	
	
	


	  
      
      
      
      
	      
	      
	      
	      
	  	/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/

		
	      
	      
      
      
	      
	    
	      
  		// 급여조회를 눌렀을때 나오는 메뉴 메소드 (김민수)
		private void menu_salary(MemberLoginDTO mdto,Scanner sc) {
			
		 String menu_no ="";
		 
		 do {
		 System.out.println("\n------- >> 급여조회 << -----------\n"
				 		  + "  1.월급     2.명세서발급    3.뒤로가기\n"
				 		  + "--------------------------------\n");
		
	
		 System.out.print("▷메뉴번호 선택 : ");
		 menu_no = sc.nextLine();
		 
		 	switch (menu_no) {
				case "1":	//월급조회 선택
					view_salary(mdto,sc);
					break;
				case "2":	//명세서발급 선택
					issue_salary(mdto,sc);
					break;
				case "3":	//뒤로가기 선택
					
					break;
		
				default:	//메뉴에 없는 번호 선택
					System.out.println("\n>> 메뉴에 없는 번호입니다! <<\n");
					break;
		 		}
		 }while(!("3".equals(menu_no)));
			
		} // end of menu_salary---
	
				
				
				
				
				
		// *** 월급을 조회하는 메소드(김민수) *** //
		private void view_salary(MemberLoginDTO mdto,Scanner sc) {
		EmployeesDTO edto = new EmployeesDTO();
		edto = tdao.view_salary(mdto);
		String s_yn = "";
		
			System.out.println("\n----- "+ tdao.empName(mdto)+ "님 월급조회 -----");
			System.out.println("기본급\t수당\t합계");
			System.out.println("------------------------");
			int month_salary = edto.getSalary()/12;
			int commission = (int)(edto.getSalary()/12f*edto.getCommission_pct());
			System.out.println( month_salary+"\t"+
								commission+"\t"+
								(month_salary+commission)+"\n");
			
			System.out.println("▷" + tdao.empName(mdto)+"님 이번달 월급은 " +(month_salary+commission)+ "만원입니다.\n");
			
			do {
				System.out.print(" 이전페이지로 이동하시겠습니까? [Y/N] : ");
				s_yn = sc.nextLine();

					if("y".equalsIgnoreCase(s_yn))
						break;
					
					else if("n".equalsIgnoreCase(s_yn));
					
					else {
						System.out.println("\n>> 메뉴에 없는 번호입니다. 다시 입력해주세요!<<\n");
						continue;
					}
				} while (true);
		}//end of private void view_salary(MemberLoginDTO mdto,Scanner sc)----		
		
		
				
		
		
        // *** 명세서를 메소드 *** //(김민수)
        private void issue_salary(MemberLoginDTO mdto,Scanner sc) {
           Date now = new Date();
           SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM");
           String time = sdfrmt.format(now);
           String s_yn = "";
           EmployeesDTO edto = new EmployeesDTO();
           edto = tdao.issue_salary(mdto);
           
           System.out.println("\n\n_________________________________________________________\n");
           System.out.println("\t\t       급여명세서\t\t\n");
           System.out.println("----------------------------------------------------------");
           System.out.println("\t\t       사원정보\t\t");
           System.out.println("----------------------------------------------------------");
           System.out.println("급여날짜\t  사원번호\t\t성명\t주민번호\t\t부서명\t직책");

           System.out.println(time + "\t  " + edto.getEmpno() + "\t" + edto.getEmpname() + "\t" + edto.getJubun() + "\t"
                 + edto.getDepartment_name() + "\t" + edto.getEmpposition() + "\n\n");

           sdfrmt = new SimpleDateFormat("MM월");
           time = sdfrmt.format(now);
           System.out.println("----------------------------------------------------------");
           System.out.println("\t\t       급 여 \t\t");
           System.out.println("----------------------------------------------------------");
           edto = tdao.view_salary(mdto);
           int month_salary = edto.getSalary() / 12;
           int commission = (int) (edto.getSalary() / 12f * edto.getCommission_pct());
           System.out.println(" 기본급 : " + month_salary + "만원\n" + " 수 당 : " + commission + "\n\n" + "▷ "
                 + edto.getEmpname() + "님 " + time + " 급여는 " + (month_salary + commission) + "만원입니다.\n");
           System.out.println("_________________________________________________________");

           do {
              System.out.print(" 이전페이지로 이동하시겠습니까? [Y/N] : ");
              s_yn = sc.nextLine();

              if ("y".equalsIgnoreCase(s_yn))
                 break;

              else if ("n".equalsIgnoreCase(s_yn));

              else {
                 System.out.println("\n>> 메뉴에 없는 번호입니다. 다시 입력해주세요!<<\n");
                 continue;
              }
           } while (true);
        }// end of private void issue_salary(MemberLoginDTO mdto)-----------------------         
        
        

        			

			
			
			
			


		   // *** 재직증명서 조회 메소드 ***(김민수)

	         private void employment_menu(MemberLoginDTO mdto, Scanner sc) {

	            Date now = new Date();
	            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy년 MM월 dd일");
	            String time = sdfrmt.format(now);
	            String s_yn = "";

	            EmployeesDTO edto = new EmployeesDTO();
	            edto = tdao.employment_menu(mdto);
	            System.out.println("\n\n\n______________________________________________");
	            System.out.println("\t\t재직증명서\t\n");
	            System.out.println("----------------------------------------------");
	            System.out.println("                  [인적사항]");
	            System.out.println("----------------------------------------------");
	            System.out.println("성명\t주민번호\t\t   주소\t\t부서");
	            System.out.println("----------------------------------------------");

	            System.out.println(edto.getEmpname() + "\t" + edto.getJubun() + "\t" + edto.getAddress() + "\t"
	                  + edto.getDepartment_name() + "\n");

	            System.out.println("----------------------------------------------");
	            System.out.println("                  "
	            		+ "[재직사항]");
	            System.out.println("----------------------------------------------");
	            System.out.println("      부서명          직위         재직기간");

	            System.out.println("      "+edto.getDepartment_name() + "          " + edto.getEmpposition() + "         "
	                           + edto.getHiredate());

	            System.out.println("\n");
	            System.out.println("\n");
	            System.out.println("    위의 기재사항이 사실과 다름없음을  증명합니다.");
	            System.out.println("  \t    " + time);
	            System.out.println("______________________________________________\n");

	            do {
	               System.out.print(" 이전페이지로 이동하시겠습니까? [Y/N] : ");
	               s_yn = sc.nextLine();

	               if ("y".equalsIgnoreCase(s_yn))
	                  break;

	               else if ("n".equalsIgnoreCase(s_yn))
	                  continue;

	               else {
	                  System.out.println("\n>> 메뉴에 없는 번호입니다. 다시 입력해주세요!<<\n");
	                  continue;
	               }
	            } while (true);
	         }// end of private void employment_menu(MemberLoginDTO mdto, Scanner
	            // sc)----------------
	      
	      	      
	      
	      
		
		
		
		
		

		// *** 게시판메뉴에 들어가는 메소드 *** //
			private void board_search(MemberLoginDTO mdto, Scanner sc) {
				String menu_no="";
				do {
				    System.out.println("\n------ >> 조회할 번호를 선택해주세요 << ------\n "
									+ "1. 글번호로 조회    2. 글제목으로 조회          \n "
									+ "3.닉네임으로 조회    4.내용으로 조회   5.뒤로가기  \n"
									+ "----------------------------------------\n ");
				    System.out.print("▷ 메뉴번호 선택 : ");
					menu_no = sc.nextLine();			
					switch (menu_no) {
						case "1":  // 글번호로 조회 (진민지)
							search_boardno(mdto,sc);
							break;
						case "2":  // 글제목으로 조회 (진민지)
							search_subject(mdto,sc);
							break;
						case "3":  // 닉네임으로 조회 (김민수)
							search_nickname(mdto,sc);
							break;
						case "4":  // 내용으로 조회 (김민수)
							search_contents(mdto,sc);
							break;
						case "5":
							break;
						default:
							System.out.println("\n>> 메뉴에 없는 번호입니다. 다시 입력해주세요!<< \n");
							break;
						}//end of switch----
					} while (!("5").equals(menu_no));
				
			} // end of private void board_search(MemberLoginDTO mdto, Scanner sc)


		

			
			
			
	     
		
			// *** 닉네임으로 조회하는 메소드 *** //(김민수)
			   private void search_nickname(MemberLoginDTO mdto, Scanner sc) {
			               
			         System.out.println(" >> 조회할 닉네임을 입력해주세요 : ");
			         String s_nick = sc.nextLine();
			         
			         List<BoardDTO> nickname_list = new ArrayList<BoardDTO>();
			         nickname_list = tdao.nickname_list(s_nick);
			               
			            if(nickname_list.size()!=0) {
			               int cnt=0;
			               StringBuilder sb = new StringBuilder();
			               for(int i=0; i<nickname_list.size() ;i++) {
			                  cnt++;
			                  if(cnt==1) {
			               System.out.println("------------------------------------\n"+
			                        "글번호\t글제목\t닉네임\t작성일자\n"+
			                        "------------------------------------");
			               } //end of if ----
			                  sb.append(nickname_list.get(i).getBoardno()+"\t");
			                  sb.append(nickname_list.get(i).getSubject()+"\t");
			                  sb.append(nickname_list.get(i).getNickname()+"\t");
			                  sb.append(nickname_list.get(i).getRegisterday()+"\n");
			            }//end of for---
			               System.out.println(sb);
			               viewOneNotice(mdto,sc);   //내용보기 메소드 호출
			            }// end of if-------------
			               else if(nickname_list.size() == 0){
			                  System.out.println(">> 일치하는 자료가 없습니다 <<");

			      }// end of else if-------------


			   }// end of private void search_nickname(MemberLoginDTO mdto, Scanner
			      // sc)--------------
			
			

			
			
		
			

		   // *** 내용으로 조회하는 메소드 *** //(김민수)
		   private void search_contents(MemberLoginDTO mdto, Scanner sc) {
		      System.out.println(" >> 조회할 글내용을 입력해주세요 : ");
		      String s_contents = sc.nextLine();

		      List<BoardDTO> contents_list = new ArrayList<BoardDTO>();
		      contents_list = tdao.contents_list(s_contents);

		      if (contents_list.size() != 0) {
		         int cnt = 0;
		         StringBuilder sb = new StringBuilder();
		         for (int i = 0; i < contents_list.size(); i++) {
		            cnt++;
		            if (cnt == 1) {
		               System.out.println("------------------------------------\n" + "글번호\t글제목\t닉네임\t작성일자\n"
		                     + "------------------------------------");
		            } // end of if---

		            sb.append(contents_list.get(i).getBoardno() + "\t");
		            sb.append(contents_list.get(i).getSubject() + "\t");
		            sb.append(contents_list.get(i).getNickname() + "\t");
		            sb.append(contents_list.get(i).getRegisterday() + "\n");
		         } // end of for
		         System.out.println(sb); // 읽어온 값 print
		         viewOneNotice(mdto, sc); // 내용보기 메소드 호출
		      } // end of if-------------- 읽어올항목이있다면 끝
		      else if (contents_list.size() == 0) { // 읽어온 항목이 없다면
		         System.out.println(">> 일치하는 자료가 없습니다 <<");
		      } // end of else if----

		   }// end of private void search_contents(Scanner sc)

			   

			   

		
		
 	  	
 	  	
 	  	
 	  	
 	  	
 	  	
		
	 	  	
	 	 /*
 		---------------------------------------------------------------------------------------------------
 		///////////////////////////////////////////////////////////////////////////////////////////////////
 		///////////////////////////////////////////////////////////////////////////////////////////////////
 		---------------------------------------------------------------------------------------------------
 		///////////////////////////////////////////////////////////////////////////////////////////////////
 		///////////////////////////////////////////////////////////////////////////////////////////////////
 		---------------------------------------------------------------------------------------------------
 		*/
 	    
		// 사원평가 메소드(진민지)
	 		private void emp_review(Scanner sc) {	 			
			
	 		String menu_no = "";
	 			
	           do {
	              
	              try {               
	                 System.out.println("▷ 사원번호를 입력하세요 :");
	                 System.out.print(">> ");
	                 int fk_empno = Integer.parseInt(sc.nextLine());
	                 
	                 System.out.print(" 근태 점수 : [10점 만점] ");
	                 int attendance = Integer.parseInt(sc.nextLine());
	                 
	                 
	                 System.out.print(" 커뮤니케이션능력 점수 : [10점 만점] ");
	                 int commskill = Integer.parseInt(sc.nextLine());
	                 
	                 
	                 System.out.print(" 업무달성능력 점수 : [10점 만점] ");
	                 int achivement = Integer.parseInt(sc.nextLine());
	                 
	                 
	                 System.out.print(" 직무에 대한 전문성 점수 : [10점 만점] ");
	                 int proskill = Integer.parseInt(sc.nextLine());
	                 
	                 Map<String,Integer> paraMap = new HashMap<>();
	                          
	                 paraMap.put("empno", fk_empno);
	                 paraMap.put("attendance", attendance);
	                 paraMap.put("commskill", commskill);
	                 paraMap.put("achivement", achivement);
	                 paraMap.put("proskill", proskill);
	                                               
	                 
	                 int n = tdao.emp_review(paraMap);	                	                 
	                 
	                 if(n==1) {   // 데이터 삽입에 성공했을 시                  
	                    System.out.println(">> 사원평가가 완료되었습니다 <<");   	                                      
	                 }//end of if 
	                 menu_no = ynMethod(sc);
	              }//end of try          	              
	              catch (NumberFormatException e) {
	                 System.out.println(">> 입력 오류로 평가에 실패하셨습니다. <<");   	   
	                 menu_no = ynMethod(sc);
	              }                	             	      
	             
	           }while(!"n".equalsIgnoreCase(menu_no));
	              
	              
	              
	           }//end of emp_review(Scanner sc)



	 		
	 		
	 		
	 		
	 		
	 		
	 		/*
	 		---------------------------------------------------------------------------------------------------
	 		///////////////////////////////////////////////////////////////////////////////////////////////////
	 		///////////////////////////////////////////////////////////////////////////////////////////////////
	 		---------------------------------------------------------------------------------------------------
	 		///////////////////////////////////////////////////////////////////////////////////////////////////
	 		///////////////////////////////////////////////////////////////////////////////////////////////////
	 		---------------------------------------------------------------------------------------------------
	 		*/
	 		
	 		
	 	// 게시판 글번호로 조회하기 메소드 (진민지)  
	 	    private void search_boardno(MemberLoginDTO mdto, Scanner sc) {
	 		 
	 			int boardno= 0;
	 			String menu_no = "";
	 					
	 			do {
	 				try {
	 					System.out.println(">> 조회하고 싶은 글번호를 입력하세요 : ");
	 					boardno = Integer.parseInt(sc.nextLine());
	 				} catch (NumberFormatException e) {
	 					System.out.println(">> 숫자만 입렵하세요 <<");
	 					continue;
	 				}//end of try-catch-----
	 				
	 				
	 					System.out.println("----------------------------------------\n"+
	 									   "글번호\t글제목\t글내용\t닉네임\t작성일자\n"+
	 									   "------------------------------------------");
	 					StringBuilder sb = new StringBuilder();
	 					List<BoardDTO> boardno_list = new ArrayList<BoardDTO>();
	 					boardno_list = tdao.boardno_list(boardno);
	 					
	 					if(boardno_list.size()!=0) {
	 						for(int i=0;i<boardno_list.size();i++) {
	 							sb.append(boardno_list.get(i).getBoardno()+"\t");
	 							sb.append(boardno_list.get(i).getSubject()+"\t");
	 							sb.append(boardno_list.get(i).getContents()+"\t");
	 							sb.append(boardno_list.get(i).getNickname()+"\t");
	 							sb.append(boardno_list.get(i).getRegisterday()+"\n");
	 						}//end of for---
	 					   System.out.println(sb);
	 					   
	 					menu_no = ynMethod(sc);
	 					   
	 					   
	 					}//end of if ----
	 				else  {
	 					System.out.println(" >> 글번호 "+ boardno+ "번으로 작성된 글이 없습니다 << \n");
	 					
	 					menu_no = ynMethod(sc);
	 				 }//end of else-- 
	 				
	 			} while(!("n".equalsIgnoreCase(menu_no))); //end of while---
	 			
	 			
	 		}// end of search_boardno(MemberLoginDTO mdto, Scanner sc)
	 		

	 	    
	 	    
	 	    
	 	    
	 	    
	 	    /*
	   		---------------------------------------------------------------------------------------------------
	   		///////////////////////////////////////////////////////////////////////////////////////////////////
	   		///////////////////////////////////////////////////////////////////////////////////////////////////
	   		---------------------------------------------------------------------------------------------------
	   		///////////////////////////////////////////////////////////////////////////////////////////////////
	   		///////////////////////////////////////////////////////////////////////////////////////////////////
	   		---------------------------------------------------------------------------------------------------
	 	  	*/
	 	    
	 	    
	 	    // 인사평가 메소드(진민지)
		 	   private void emp_jpr(Scanner sc) {
		 	    	 
		 		    String menu_no = "";
		 		   
		 	    	 do	{
		 	    	 System.out.println(">> 원하시는 인사평가 항목을 선택해주세요 << ");
		 	    	 System.out.println("[1. 사원평가  2. 인사평가조회  3. 뒤로가기]");
		 	    	 System.out.print(">> ");
		 	    	 
		 	    	 menu_no = sc.nextLine();
		 	    	 
		 	    	 switch (menu_no) { 
		 	    	 
		 				case "1":	//사원평가 선택시
		 					
		 					emp_review(sc);
		 					break;
		 					
		 				case "2":	//인사평가조회 선택시
		 					
		 					select_jpr(sc);
		 					break;
		 					
		 				case "3":	// 뒤로가기
		 		
		 					break;
		 		
		 				default: // 메뉴에 없는 번호 선택시 
		 					System.out.println("\n>> 메뉴에 없는 번호입니다!! <<\n");
		 					break;
		 	    	 	}	 
		 	    	 }while(!("3".equals(menu_no)) ); // 3. 뒤로가기 선택할 경우
		 		 }//end of emp_jpr(Scanner sc)

	 	


	 	    // yn메소드 (진민지)
	 		private String ynMethod(Scanner sc) { 
	 			String menu_no="";
	 			do {
	 			System.out.print(">> 계속하시겠습니까?[Y/N] : ");
	 			menu_no = sc.nextLine();
	 		
	 			
	 			   if("y".equalsIgnoreCase(menu_no)) {
	 				   	break;
	 			   }
	 			   else if("n".equalsIgnoreCase(menu_no)){
	 					break;
	 			   }
	 			   else {
	 					System.out.println(">> Y 또는 N만 선택해주세요!<<");
	 					
	 			   }
	 		   }while(true);	//end of do-while-------
	 			
	 		   return menu_no;
	 		}// end of ynMethod(Scanner sc);

	 		
	 		
	 		
	 		/*
	   		---------------------------------------------------------------------------------------------------
	   		///////////////////////////////////////////////////////////////////////////////////////////////////
	   		///////////////////////////////////////////////////////////////////////////////////////////////////
	   		---------------------------------------------------------------------------------------------------
	   		///////////////////////////////////////////////////////////////////////////////////////////////////
	   		///////////////////////////////////////////////////////////////////////////////////////////////////
	   		---------------------------------------------------------------------------------------------------
	 	  	*/

	 		
	 		
	 		
	 		// 게시판 글제목으로 조회하기 메소드 (진민지)
	 		private void search_subject(MemberLoginDTO mdto, Scanner sc) { 			

	 			String menu_no = "";
	 			String subject= "";
	 			
	 			int flag = 0;
	 			
	 			do {				 					 				
	 				System.out.println(">> 조회하고 싶은 글제목을 입력하세요");
	 				subject = sc.nextLine();	
	 				
	 				System.out.println("------------------------------------\n"+
	 							   "글번호\t글제목\t글내용\t닉네임\t작성일자\n"+
	 						       "------------------------------------------");
					StringBuilder sb = new StringBuilder();
					List<BoardDTO> subject_list = new ArrayList<BoardDTO>();
					subject_list = tdao.subject_list(subject);					
					
					if(subject_list.size()!=0) {
						for(int i=0;i<subject_list.size();i++) {
							sb.append(subject_list.get(i).getBoardno()+"\t");
							sb.append(subject_list.get(i).getSubject()+"\t");
							sb.append(subject_list.get(i).getContents()+"\t");
							sb.append(subject_list.get(i).getNickname()+"\t");
							sb.append(subject_list.get(i).getRegisterday()+"\n");
						}//end of for---
					   System.out.println(sb);
			
					menu_no = ynMethod(sc); // 조회를 계속하고자 할 경우
					
	 				}//end of if---	 				
	 				
	 		 else  {
	 				System.out.println(">> "+subject+" (으)로 작성한 글이 없습니다 <<\n");
	 				
	 				menu_no = ynMethod(sc);
	 		 	}// end of else---
	 				
	 		} while(!("n".equalsIgnoreCase(menu_no))); //end of while---
	 			
	 			
	 	}// end of search_subject(MemberLoginDTO mdto, Scanner sc)  

	 		
	 		

	 		
	 		
	    	 // 인사평가조회 메소드 (진민지)
	     	 private void select_jpr(Scanner sc) {	    	 
	 		 
	 		 JprDTO jdto = new JprDTO();
	 		 String menu_no = "";
		
			do {
			try {	    		
				
   				System.out.println("▷ 원하시는 사원의 사원번호를 입력하세요: ");
	    			System.out.print(">> ");
	    			
   				int empno = Integer.parseInt(sc.nextLine()); 
	        	    jdto = tdao.select_jpr(empno);
	        	    
	        	    System.out.println(">>-----------------원하시는 사원의 인사평가표입니다----------------------<< ");
	        	    System.out.println("----------------------------------------------------------------------------");
	        	    System.out.println("사원번호      근태     커뮤니케이션능력   업무달성능력    직무에대한전문성\n"+
				        	    	  jdto.getFk_empno()+"\t\t"+ 						        	    
				        	    	  jdto.getAttendance()+"\t\t"+ 
			        	    		  jdto.getCommskill()+"\t\t"+ 
			        	    		  jdto.getAchivement()+"\t\t"+ 
			        	    		  jdto.getProskill());		 
	        	   System.out.println("-----------------------------------------------------------------------------");
	        	   
	        	  menu_no = ynMethod(sc);
	 	        	   
	    			} catch (NumberFormatException e) { // 숫자 이외의 것을 입력했을 경우
	    				System.out.println(">> 올바른 번호를 입력해주세요 !! <<");		    				
	    			} catch (NullPointerException e) {
	    				System.out.println("입력하신 사원에 대한 정보가 없습니다.!");
	    			} //end of try-catch-----		    	    					
	    			
	    			}while(!"n".equalsIgnoreCase(menu_no));
	 	  } // end of select_jpr(Scanner sc)
	 			
	     	 
		
 		/*
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		///////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		---------------------------------------------------------------------------------------------------
		*/
		
		
		

			
			
			
			
 		// 자신이 작성한 게시글을 수정하는 메소드(조상운)
        private int updateMyNotice(BoardDTO bdto, Scanner sc) {
           int result = 0;
           String subject = "";
           String contents = "";
           String boardpwd = "";
           int cnt = 0;
           
           Map<String, String> paraMap = new HashMap<>();
           
            // 비밀번호를 틀리는 경우
           for(int i=0; i<3; i++) {
              System.out.print("게시글 비밀번호를 입력하세요 : ");
              try {
                 boardpwd = sc.nextLine();
                 Integer.parseInt(boardpwd);
                 if(!boardpwd.equals(String.valueOf(bdto.getBoardpwd()))) {
                    System.out.println("비밀번호를 잘못 입력하셨습니다.");
                    cnt++;
                 }
                 else if(boardpwd.equals(String.valueOf(bdto.getBoardpwd()))){
                    break;
                 }
              }
              catch (NumberFormatException e) {
                 cnt++;
                 System.out.println("[경고] 게시판 비밀번호는 정수로만 입력하세요");
              }
           }
           if(cnt == 3) {
              result = 3;
              return result;
           }
              do {
                 System.out.print("1. 글제목[변경하지 않으려면 엔터를 눌러주세요] : ");
                 subject = sc.nextLine();
                 if(subject.trim().isEmpty()) { // 그냥 엔터 또는 공백만으로 입력한 경우
                    subject = bdto.getSubject();
                    break;
                 }
                 else if(subject.length() > 15) { // 엄청나게 많은 글자를 입력하는 경우
                    System.out.println(">> [경고] 글제목은 최대 15글자 이내이어야 합니다.");
                 }
                 else
                    break;
                 
              } while (true);
              
              ////////////////////////////////////////////////////////////////////
              do {
                 System.out.print("2. 글내용[변경하지 않으려면 엔터를 눌러주세요] : ");
                 contents = sc.nextLine();
                 if(contents.trim().isEmpty()) { // 그냥 엔터 또는 공백만으로 입력한 경우
                    contents = bdto.getContents();
                    break;
                 }
                 else if(contents.length() > 200) { // 엄청나게 많은 글자를 입력하는 경우
                    System.out.println(">> [경고] 글내용은 최대 200글자 이내이어야 합니다.");
                 }
                 else
                    break;
                    
                 
              } while (true);
              ////////////////////////////////////////////////////////////////////
           
           String yn = "";
           do {
              System.out.print(">> 정말로 글을 수정하십니까? [Y/N]");
              yn = sc.nextLine();
              
              if("y".equalsIgnoreCase(yn)) {
                 String str_boardno = String.valueOf(bdto.getBoardno());
                 paraMap.put("subject", subject);
                 paraMap.put("contents", contents);
                 paraMap.put("str_boardno", str_boardno);
                 result = tdao.updateMynotice(paraMap);
                 break;
              }
              else if("n".equalsIgnoreCase(yn)) {
                 result = 2;
                 return result;
              }
              else {
                 System.out.println(">> Y 또는 N 만 입력하세요!! <<");
              }
           } while (true);
           
           
           return result;
        }// end of private int updateMyNotice(BoardDTO bdto, Scanner sc)
	         
	      
		
		
		
		
		
		
		// 내 정보조회 수정 화면(조상운)
		private void myInfoSearchUpdate(MemberLoginDTO mdto, Scanner sc) {
	         
	         Map<String, String> paraMap = new HashMap<>();
	         EmployeesDTO edto = new EmployeesDTO();
	         String yn = "";
	         int result = 0;
	         
	         edto = tdao.myInfo(mdto.getMemberid());
	         
	         do {
	            if(edto != null) {
	               System.out.println("---------- 나의 개인정보 ----------\n"
	                              + "1. 사원번호(아이디) : " +edto.getEmpno()+"\n"
	                              + "2. 사원명 : "+edto.getEmpname()+"\n"
	                              + "3. 부서번호 : "+edto.getFk_deptno()+"\n"
	                              + "4. 직급 : "+edto.getEmpposition()+"\n"
	                               + "5. 거주지 : "+edto.getAddress()+"\n"
	                              + "6. 연봉 : "+edto.getSalary()+"\n"
	                              + "7. 수당 : "+edto.getCommission_pct()+"\n"
	                              + "8. 이메일 : "+edto.getEmail()+"\n"
	                              + "9. 입사일자 : "+edto.getHiredate()+"\n"
	                              + "---------------------------------\n");
	               System.out.println("▷ 개인정보를 수정하시겠습니까? [Y/N]");
	               yn = sc.nextLine();
	               
	               if("y".equalsIgnoreCase(yn)) {
	                  break;
	               }
	               else if("n".equalsIgnoreCase(yn)) {
	                     break;
	               }
	               else {
	                  System.out.println(">> [경고] Y 또는 N 만 입력하세요 <<");
	               }
	            }
	         } while(true);   
	         
	               if("y".equalsIgnoreCase(yn)) {
	                  String empname = "";
	                  String address = "";
	                  String email = "";
	                  String memberpwd = "";
	                  
	                  System.out.println("\n---------- 나의 개인정보 수정----------");
	                  do {
	                     System.out.print("▷ 이름[변경하지 않으려면 엔터] : "); 
	                     empname = sc.nextLine();
	                     if(empname != null && empname.trim().isEmpty()) {
	                        empname = edto.getEmpname();
	                        break;
	                     }
	                     else if(empname.length()>6) {
	                        System.out.println("[경고] 이름은 6글자 이하만 입력가능합니다.");
	                     }
	                     else 
	                        break;
	                  } while (true);
	                  
	                  do {
	                     System.out.print("▷ 거주지[변경하지 않으려면 엔터] : ");     
	                       address = sc.nextLine();
	                       if(address != null && address.trim().isEmpty()) {
	                             address = edto.getAddress();
	                             break;
	                     }
	                       else if(address.length()>50) {
	                        System.out.println("[경고] 주소는 50글자 이하만 입력가능합니다.");
	                     }
	                     else 
	                        break;
	                  } while (true);
	                    
	                    do {
	                          System.out.print("▷ 이메일[변경하지 않으려면 엔터] : ");
	                     email = sc.nextLine();
	                     if(email != null && email.trim().isEmpty()) {
	                        email = edto.getEmail();
	                        break;
	                     }
	                     else if(address.length()>30) {
	                        System.out.println("[경고] 이메일은 30글자 이하만 입력가능합니다.");
	                     }
	                     else 
	                        break;
	                  } while (true);
	                  String pwdback=mdto.getMemberpwd();
	                  do {
	                     System.out.print("▷ 비밀번호[변경하지 않으려면 엔터] : ");
	                     memberpwd = sc.nextLine();
	                     if(memberpwd.trim().isEmpty()) {
	                    	 mdto.setMemberpwd(pwdback);
	                    	 
	                        break;
	                     }
	                     
	                     else if(memberpwd != null && memberpwd.equals(mdto.getMemberpwd())) { // 기존비밀번호와 수정할 비밀번호가 같다면 
	                        System.out.println(">> 기존 비밀번호와 동일합니다. <<");
	                     }
	                     else if(memberpwd.length()>20) {
	                        System.out.println("[경고] 비밀번호는 20글자 이하만 입력가능합니다.");
	                     }
	                     else 
                    	 mdto.setMemberpwd(memberpwd);
	                     if(!(pwdback.equals(mdto.getMemberpwd()))) {
	                    	 break;
	                     }
	                    } while(true);
	                  do {
	                     System.out.print(">> 정말 수정하시겠습니까?[Y/N]");
	                     yn = sc.nextLine();
	                     
	                     if("y".equalsIgnoreCase(yn)) {
	                        String str_memberid = String.valueOf(mdto.getMemberid());
	                        paraMap.put("empname", empname);
	                        paraMap.put("address", address);
	                        paraMap.put("email", email);
	                        paraMap.put("memberpwd",mdto.getMemberpwd());
	                        paraMap.put("str_memberid", str_memberid);
	                        
	                        result = tdao.updateMyInfo(paraMap);
	                        break;
	                     }
	                     else if("n".equalsIgnoreCase(yn)) {
	                        System.out.println(">> 개인정보 수정을 취소하셨습니다 <<");
	                        mdto.setMemberpwd(pwdback);
	                        break;
	                     }
	                     else {
	                        System.out.println(">> [경고] Y 또는 N 만 입력하세요 <<");
	                     }
	                  } while(true);
	               }//end of if------
	            if(result == 2 || result == 1) {
	               System.out.println(">> 개인정보 수정에 성공하였습니다.");
	            }
	            else if(result == -1) {
	               System.out.println(">> 개인정보 수정에 실패하였습니다.");
	            }
	         
	      }// end of myInfoSearchUpdate(int memberid, Scanner sc) ----------------------------
		
		
	      

	      
	    // 일반사원 로그인 메소드(조상운)
		private MemberLoginDTO memberlogin(String memberid, String memberpwd) {
			
			Map<String, String> paraMap = new HashMap<>();
			MemberLoginDTO mdto = null;
			
			if(!"admin".equals(memberid)) {
				paraMap.put("memberid", memberid);
				paraMap.put("memberpwd", memberpwd);
				mdto = tdao.memberlogin(paraMap);
			}
			else if("admin".equals(memberid)){
				System.out.println("\n>> 사원로그인 전용입니다!! <<\n");
			}
			
			if(mdto != null) {
				System.out.println("\n>> 로그인 성공!!\n");
			}
			else if(mdto == null ) {
				System.out.println("\n>> 로그인 실패!!\n");
			}
			
			return mdto;
		}// end of private MemberLoginDTO memberlogin(String memberid, String memberpwd) -----------------------
	
		
			
			
			
			
			
		// 게시판 관리자 로그인(조상운)
		private SuperAdminDTO boardAdminLogin(String memberid, String memberpwd) {
			
			SuperAdminDTO superdto = null;
			
			superdto = tdao.boardAdminLogin(memberpwd); // 게시판관리자 로그인
			
			
			if(superdto != null) {
				System.out.println("\n>> 게시판관리자로 로그인하였습니다 <<\n");
			}
			else if(superdto == null && !memberid.equals("admin")) {
				System.out.println("\n>> 로그인 실패!! <<\n");
			}
			
			return superdto;
		} // end of private SuperAdminDTO boardAdminLogin(String memberid, String memberpwd) -----------------------

		
	


//댓글 작성하는 메소드 (조상운)
private int insertComment(MemberLoginDTO mdto, BoardDTO bdto, Scanner sc) {
   int result = 0;
   
   
   String nickname="";
   String comment_contents = "";
   System.out.println("\n----------------작성양식-------------------\n"
                + "닉네임 : 5자이내               글내용 : 100자이내\n"
                 + "------------------------------------------");
   outer:
   do {
      do {
      System.out.print("▷ 닉네임 : ");
          nickname = sc.nextLine();
         if(nickname.trim().isEmpty()||nickname.length()>=5) {   //공백이 없는 5자이내
            System.out.println(">> 닉네임은 공백이 없는 5글자 이내로 작성해주세요! <<");
         }
      }while(!(!nickname.trim().isEmpty()&&nickname.length()<5)); //정상 입력시 탈출

      
      do {
      System.out.print("▷ 댓글내용 : ");
         comment_contents = sc.nextLine();
         if(comment_contents.trim().isEmpty()||comment_contents.length()>=100) {   //공백이 없는 200자이내
            System.out.println(">> 댓글내용은 공백이 없는 100자 이내로 작성해주세요! <<");
         }
      }while(!(!comment_contents.trim().isEmpty()&&comment_contents.length()<100)); //정상 입력시 탈출
      
      
      
      
      int fk_boardno = bdto.getBoardno();
      Map<String,String> paraMap = new HashMap<>();
      
      paraMap.put("nickname", nickname);
      paraMap.put("comment_contents", comment_contents);
      paraMap.put("fk_boardno",String.valueOf(fk_boardno));
      int n=0;
      do {
         System.out.print(">>작성된 글을 등록하시겠습니까?[Y/N] : ");
         String yn =sc.nextLine();
         if("y".equalsIgnoreCase(yn)) {
            n = tdao.insertComment(paraMap);
            break;
         }
         else if("n".equalsIgnoreCase(yn)) {
            System.out.println(">> 글 작성이 취소되었습니다!! <<\n");
            break outer;
         }
         else {
            System.out.println(">> Y 또는 N 만 입력해주세요!! <<");
         }
      }while(true);//end of do-while----
      
      if(n==1) {   //글 작성에 성공했더라면,
         System.out.print(">> 글 작성이 완료되었습니다!! 또 작성하시겠습니까? [Y/N]");
         String menu_no = sc.nextLine();
         if("y".equalsIgnoreCase(menu_no)) { // 글 작성을 또 한다고 하면,
            continue;
         }
         else if("n".equalsIgnoreCase(menu_no)) { // 글 작성을 그만한다고 한다면
            break;
         }
         else {
            System.out.println(">> Y 또는 N 만 입력해주세요 <<");
         }
      }//end of if---
      else if(n==0) {
         System.out.println(">> 글 작성이 실패하였습니다 <<");
      }
      else if(n==-1) {
         System.out.println(">> 시스템 오류로 인하여 글 작성이 실패하였습니다. <<");
      }
   }while(true);
   
   
   return result;

} // private int insertComment(MemberLoginDTO mdto, BoardDTO bdto, Scanner sc)




//연차/휴가 신청(임선우)
private void signVacation(MemberLoginDTO mdto, Scanner sc) {
  List<VacationDTO> vdtoList = tdao.memvdtoList(mdto);
  String menuno="";
  
  do {
     System.out.println("-------- >> "+tdao.empName(mdto)+"님, 반갑습니다! << --------");
     System.out.println("1.휴가신청  2.휴가신청취소  3.휴가신청내역조회  4.종료");
     System.out.println("----------------------------------------------------");
     
     System.out.println("▷ 메뉴번호 : ");
     menuno= sc.nextLine();
     switch (menuno) {
        case "1":// 휴가신청
           regiVacation(mdto, sc);
        break;
      
      case "2":// 휴가신청취소
           vdtoList = tdao.memvdtoList(mdto);
           
           if(vdtoList.size() > 0) {
              System.out.println("---------- 휴가 신청 내역 --------\n"
                               + "휴가번호   시작날짜    끝날짜    휴가일수\n"
                               + "------------------------------");
              for(VacationDTO vdto : vdtoList) {
                 
                 System.out.println(vdto.getSeq_vno()+"  "+
                               vdto.getStartVday()+"  "+
                               vdto.getEndVday()+"  "+
                               vdto.getvDay()+"\n");

              }// end of for ----------------------------
           }
           
           System.out.println(">> 취소할 휴가번호를 입력하세요.");
           String vno = sc.nextLine();
           
           int result = tdao.deleteVacation(mdto,vno);
           if(result==1) {
              System.out.println(">> 휴가 삭제 성공 << \n");
           }
           
           else if(result==-1) {
              System.out.println(">> 시스템 오류로 삭제가 실패되었습니다. <<");
           }
           break;
           
       case "3": // 휴가신청 내역조회
           
           vdtoList = tdao.memvdtoList(mdto);
           
           if(vdtoList.size() > 0) {
              System.out.println("---------- 휴가 신청 내역 --------\n"
                           + "휴가번호   시작날짜    끝날짜    휴가일수\n"
                               + "------------------------------");
              for(VacationDTO vdto : vdtoList) {
                 
                 System.out.println(vdto.getSeq_vno()+"  "+
                               vdto.getStartVday()+"  "+
                               vdto.getEndVday()+"  "+
                               vdto.getvDay()+"\n");

              }// end of for ----------------------------
           }
           int sumVDay = tdao.searchvday(mdto);
           
           System.out.println(">> "+mdto.getMemberid()+"님의 잔여휴가일 : " + (15-sumVDay));
           
           break;
           
        case "4":// 종료
           break;

        default:
           System.out.println("메뉴에 없는 번호 입니다.");
           break;
     }// end of switch
  }while(!"4".equals(menuno));

}// end of private void signVacation(MemberLoginDTO mdto, Scanner sc)---------------------



}




