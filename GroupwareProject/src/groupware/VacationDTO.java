package miniproject1;

public class VacationDTO {
   
   private int empno;
   private String startVday;
   private String endVday;
   private int vDay;
   private int seq_vno;
   

   protected int getEmpno() {
      return empno;
   }
   protected void setEmpno(int empno) {
      this.empno = empno;
   }
   protected String getStartVday() {
      return startVday;
   }
   protected void setStartVday(String startVday) {
      this.startVday = startVday;
   }
   protected String getEndVday() {
      return endVday;
   }
   protected void setEndVday(String endVday) {
      this.endVday = endVday;
   }
   protected int getvDay() {
      return vDay;
   }
   protected void setvDay(int vDay) {
      this.vDay = vDay;
   }
   protected int getSeq_vno() {
      return seq_vno;
   }
   protected void setSeq_vno(int seq_vno) {
      this.seq_vno = seq_vno;
   }


   
   
}