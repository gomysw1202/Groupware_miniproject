package miniproject1;

public class SuperAdminDTO {

	private String baid ; //게시판관리자 아이디
	private String bapwd; // 게시판관리자 비밀번호
	private int fk_empno; //사원번호
	
	
	public String getBaid() {
		return baid;
	}
	
	public void setBaid(String baid) {
		this.baid = baid;
	}
	
	public String getBapwd() {
		return bapwd;
	}
	
	public void setBapwd(String bapwd) {
		this.bapwd = bapwd;
	}
	
	public int getFk_empno() {
		return fk_empno;
	}
	
	public void setFk_empno(int fk_empno) {
		this.fk_empno = fk_empno;
	}
	
}
