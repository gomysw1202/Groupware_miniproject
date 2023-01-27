package miniproject1;

public class BoardDTO {
	//field 정의
	private int boardno;      
	private String nickname;  
	private String subject;      
	private String contents;     
	private int boardpwd;    
	private String registerday;
	private int boardcnt;
	private int fk_empno;
	
	// select용
	private int commentcnt;
	
	private String pre_boardno; 
	private String pre_subject; 
	private String next_boardno; 
	private String next_subject;
		
	
	
	//method 정의
	public int getFk_empno() {
		return fk_empno;
	}
	public void setFk_empno(int fk_empno) {
		this.fk_empno = fk_empno;
	}
	public int getBoardno() {
		return boardno;
	}
	public void setBoardno(int boardno) {
		this.boardno = boardno;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getBoardpwd() {
		return boardpwd;
	}
	public void setBoardpwd(int boardpwd) {
		this.boardpwd = boardpwd;
	}
	public String getRegisterday() {
		return registerday;
	}
	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}
	public int getBoardcnt() {
		return boardcnt;
	}
	public void setBoardcnt(int boardcnt) {
		this.boardcnt = boardcnt;
	}     
	
	
	
	
	
	//select 용
	public int getCommentcnt() {
		return commentcnt;
	}
	public void setCommentcnt(int commentcnt) {
		this.commentcnt = commentcnt;
	}

	
	
	
	public String getPre_boardno() {
		return pre_boardno;
	}
	public void setPre_boardno(String pre_boardno) {
		this.pre_boardno = pre_boardno;
	}
	public String getPre_subject() {
		return pre_subject;
	}
	public void setPre_subject(String pre_subject) {
		this.pre_subject = pre_subject;
	}
	public String getNext_boardno() {
		return next_boardno;
	}
	public void setNext_boardno(String next_boardno) {
		this.next_boardno = next_boardno;
	}
	public String getNext_subject() {
		return next_subject;
	}
	public void setNext_subject(String next_subject) {
		this.next_subject = next_subject;
	}

	
	
}
