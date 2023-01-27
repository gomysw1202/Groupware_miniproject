package miniproject1;

public class ApplicantDTO {
	//field 정의
	private int applicantno;      // 지원번호
    private String applicant;      // 지원자명
    private int fk_hope_depno;    // 희망부서번호
    private String mobile;         // 연락처
    private String address;        // 주소
    private String registerdate;   // 지원일자
   
   
   //method 정의
   protected int getApplicantno() {
      return applicantno;
   }
   
   protected void setApplicantno(int applicantno) {
      this.applicantno = applicantno;
   }
   
   protected String getApplicant() {
      return applicant;
   }
   
   protected void setApplicant(String applicant) {
      this.applicant = applicant;
   }
   
   protected int getFk_hope_depno() {
      return fk_hope_depno;
   }
   
   protected void setFk_hope_depno(int fk_hope_depno) {
      this.fk_hope_depno = fk_hope_depno;
   }
   
   protected String getMobile() {
      return mobile;
   }
   
   protected void setMobile(String mobile) {
      this.mobile = mobile;
   }
   
   protected String getAddress() {
      return address;
   }
   
   protected void setAddress(String address) {
      this.address = address;
   }
   
   protected String getRegisterdate() {
      return registerdate;
   }
   
   protected void setRegisterdate(String registerdate) {
      this.registerdate = registerdate;
   }
   
   @Override
   public String toString() {
      
      return "\n--------———----——------ >> 지원자 정보<< -----——-—----——-------\n"
          + "1.지원번호    : " + applicantno+"\n"
          + "2.지원자명    : " + applicant+"\n"
          + "3.희망부서번호 : " + fk_hope_depno+"\n"
          + "4.연락처       : " + mobile+"\n"
          + "5.주소      : " + address+"\n"
          + "6.지원일자   : " + registerdate;
   
   }
   
	
	
	
	
}
