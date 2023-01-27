package miniproject1;

public class AppDocumentDTO {
	
	//field 정의
	private int documentseq;	//문서번호
	private int fk_empno;
	private String registerday;
	private String subject;
	private String content;
	private int status;
	
	
	//select용
	private String empname;
    private String empposition;
    private int levelno;
	
	

	
	
	// method 정의
	public int getDocumentseq() {
		return documentseq;
	}
	public void setDocumentseq(int documentseq) {
		this.documentseq = documentseq;
	}
	public int getFk_empno() {
		return fk_empno;
	}
	public void setFk_empno(int fk_empno) {
		this.fk_empno = fk_empno;
	}
	public String getRegisterday() {
		return registerday;
	}
	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	//select 용 method
	
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	
	public String getEmpposition() {
		return empposition;
	}
	public void setEmpposition(String empposition) {
		this.empposition = empposition;
	}
	public int getLevelno() {
		return levelno;
	}
	public void setLevelno(int levelno) {
		this.levelno = levelno;
	}
	
	
	//문서정보 출력 메소드(최현우)
	@Override
	public String toString(){
		
		return "문서번호 : "+documentseq+"\n"
			+"작성자명 : "+empname+"\n"
			+"제목 : "+subject+"\n"
			+"내용 : "+content+"\n"
			+"작성일자 : "+registerday;
		
	}
}
