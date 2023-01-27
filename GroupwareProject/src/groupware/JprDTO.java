package miniproject1;

public class JprDTO { // 인사평가 
	
	// field 
	private int fk_empno;         // 사원번호
	private int attendance;   // 근태
	private int commskill;    // 커뮤니케이션능력
	private int achivement;  // 업무달성능력
	private int proskill;      //직무에대한전문성
	
	
	// getter, setter
	public int getFk_empno() {
		return fk_empno;
	}
	public void setFk_empno(int fk_empno) {
		this.fk_empno = fk_empno;
	}
	public int getAttendance() {
		return attendance;
	}
	public int getCommskill() {
		return commskill;
	}
	public int getAchivement() {
		return achivement;
	}
	public int getProskill() {
		return proskill;
	}
	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}
	public void setCommskill(int commskill) {
		this.commskill = commskill;
	}
	public void setAchivement(int achivement) {
		this.achivement = achivement;
	}
	public void setProskill(int proskill) {
		this.proskill = proskill;
	}
	
}