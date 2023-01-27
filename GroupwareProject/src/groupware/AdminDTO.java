package miniproject1;

public class AdminDTO {

	
	private String adminid;  //관리자 아이디
	private String adminpwd; //관리자 비밀번호
	private int fk_empno;    //사원번호
	
	
	public String getAdminid() {
		return adminid;
	}
	
	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}
	
	public String getAdminpwd() {
		return adminpwd;
	}
	
	public void setAdminpwd(String adminpwd) {
		this.adminpwd = adminpwd;
	}
	
	public int getFk_empno() {
		return fk_empno;
	}
	
	public void setFk_empno(int fk_empno) {
		this.fk_empno = fk_empno;
	}
	
	
}
