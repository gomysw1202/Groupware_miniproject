package miniproject1;

public class CommentDTO {
	
	private int commentno;               // 댓글번호
	   private String comment_contents;    // 댓글내용
	   private int fk_boardno;            // 댓글이 달려있는 글번호
	   private String nickname;            // 별명
	   private String registerday;         // 등록일자
	   
	   
	   
	   
	   public int getCommentno() {
	      return commentno;
	   }
	   public void setCommentno(int commentno) {
	      this.commentno = commentno;
	   }
	   public String getComment_contents() {
	      return comment_contents;
	   }
	   public void setComment_contents(String comment_contents) {
	      this.comment_contents = comment_contents;
	   }
	   public int getFk_boardno() {
	      return fk_boardno;
	   }
	   public void setFk_boardno(int fk_boardno) {
	      this.fk_boardno = fk_boardno;
	   }
	   public String getNickname() {
	      return nickname;
	   }
	   public void setNickname(String nickname) {
	      this.nickname = nickname;
	   }
	   public String getRegisterday() {
	      return registerday;
	   }
	   public void setRegisterday(String registerday) {
	      this.registerday = registerday;
	   }
	
	
	
	
	
}
