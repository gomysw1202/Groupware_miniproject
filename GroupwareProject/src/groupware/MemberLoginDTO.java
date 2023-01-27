package miniproject1;

public class MemberLoginDTO {

	private int memberid;     // 회원아이디
	private String memberpwd; // 회원비밀번호
	private int board_stop;		//정지여부
	private String comments;	//정지사유
	
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getBoard_stop() {
		return board_stop;
	}

	public void setBoard_stop(int board_stop) {
		this.board_stop = board_stop;
	}

	public int getMemberid() {
		return memberid;
	}

	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}

	public String getMemberpwd() {
		return memberpwd;
	}
	
	public void setMemberpwd(String memberpwd) {
		if(memberpwd == null) {
            this.memberpwd = getMemberpwd();
            
        }
		else {
			boolean upperFlag = false;	//대문자인지 기록하는 용도
			boolean lowerFlag = false;	//소문자인지 기록하는 용도
			boolean digitFlag = false;	//숫자인지 기록하는 용도
			boolean specialFlag = false;//특수기호인지 기록하는 용도
			
			int memberpwd_length = memberpwd.length();
			
			if(memberpwd_length <8 || memberpwd_length>15) { //비밀번호의 길이가 8미만 또는 15초과라면
				System.out.println(">> 비밀번호는 대문자,소문자,특수문자가 포함된 8글자에서 15글자이어야 합니다!! <<");
			}
			else { // 비밀번호의 글자수가 8글자 이상 15글자 이하인 경우
					// 암호가 어떤글자로 이루어졌는지 검사를 시도해야한다.
		
					//pwd ==>"qwEr1234$"
					//index ==> 012345678			
				for(int i=0; i<memberpwd_length; i++) { //입력받은 글자의 길이 만큼 검사를 해야한다.
					char ch = memberpwd.charAt(i);
					
					if(Character.isUpperCase(ch)) { // 대문자이라면
						upperFlag = true;
					}
					else if(Character.isLowerCase(ch)) { // 소문자이라면
						lowerFlag = true;
					}
					else if(Character.isDigit(ch)) {//숫자라면
						digitFlag = true;
					}
					else //특수기호라면
						specialFlag = true;
					
				}// end of for-------------------
				if(upperFlag && lowerFlag && digitFlag && specialFlag) {
					this.memberpwd = memberpwd;
				}//end of if ------------------------
				else{
					System.out.println(">> 비밀번호는 대문자,소문자,특수문자가 포함된 8글자에서 15글자이어야 합니다!! <<");				
				}//end of else---------------------------
			}//end of else---		
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}//end of setMemberpwd
	
	
	
	
	
	
	
	
}
