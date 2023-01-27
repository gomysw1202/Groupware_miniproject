package miniproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDBConnection {

	// field 
	// --> static 변수 첫번째로 호출(작동) <-- //
	private static Connection conn = null;
	
	// --> static 초기화 블럭 두번째로 호출(작동) <-- //
	static {
		// 중요한 사실은 static 초기화 블럭은 해당 클래스가 객체로 생성되기전에 먼저 실행되어지며,
	    // 딱 1번만 호출되어지고 다음번에 새로운 객체(인스턴스)를 매번 생성하더라도
	    // static 초기화 블럭은 호출이 안되어진다.
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@211.238.142.25:1521:xe", "mini_orauser4", "aclass");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} // end of static 초기화 블럭 --------------------------------------------------------
	
	
/*	
	== 두번째,
	   생성자의 접근제한자를 private 으로 지정하여, 외부에서 절대로 인스턴스를 생성하지 못하도록 막아버린다.
*/	
	private MyDBConnection() {}
	
	
	
/*	
	== 세번째,
	   static 메소드를 생성하여 외부에서 해당 클래스의 객체를 사용할 수 있도록 해준다.
*/	
	public static Connection getConn() {
		return conn;
	}
	
	
	// method
	// === Connection conn 객체 자원 반납하기 === //
	public static void closeConnection() {
		
			try {
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}// end of public static void closeConnection()
	
	
	
}














