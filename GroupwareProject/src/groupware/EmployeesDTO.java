package miniproject1;

public class EmployeesDTO {

	// field 
	private int empno;             // 사원번호
	private String empposition;    // 직급
	private String empname;        // 사원명
	private int fk_deptno;         // 부서번호
	private String address;        // 주소
	private int salary;            // 연봉
	private float commission_pct;  // 수당
	private String email;          // 이메일
	private long jubun;			   // 주민번호
	private int managerno;		   // 직속상사 사원번호
	private String hiredate;	   // 입사일자
	
	
	//select용
	
	private String Department_name;

	
	

	public String getDepartment_name() {
		return Department_name;
	}

	public void setDepartment_name(String department_name) {
		Department_name = department_name;
	}

	// getter, setter
	public int getManagerno() {
		return managerno;
	}
	
	public void setManagerno(int managerno) {
		this.managerno = managerno;
	}
	public int getEmpno() {
		return empno;
	}
	
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	
	public String getEmpposition() {
		return empposition;
	}
	
	public void setEmpposition(String empposition) {
		this.empposition = empposition;
	}
	
	public String getEmpname() {
		return empname;
	}
	
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	
	public int getFk_deptno() {
		return fk_deptno;
	}
	
	public void setFk_deptno(int fk_deptno) {
		this.fk_deptno = fk_deptno;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getSalary() {
		return salary;
	}
	
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public float getCommission_pct() {
		return commission_pct;
	}
	
	public void setCommission_pct(float commission_pct) {
		this.commission_pct = commission_pct;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public long getJubun() {
		return jubun;
	}

	public void setJubun(long jubun) {
		this.jubun = jubun;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
