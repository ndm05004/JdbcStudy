package kr.or.ddit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

// JDBC 드라이버를 로딩하고 Connection객체를 생성해서 반환하는 메서드로
// 구성된 class 만들기
// ( dbinfo.properties 파일의 내용으로 설정하는 방법 )

// ResourceBundle객체 이용하기

public class DBUtil3{
	
	private static ResourceBundle bundle;
	
	static {
		
		bundle = ResourceBundle.getBundle("kr.or.ddit.config.dbinfo"); // 객체 생성
		
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(bundle.getString("driver"));

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			
			String url = bundle.getString("url");
			String user = bundle.getString("user");
			String pass = bundle.getString("pass");
			
			return DriverManager.getConnection(url, user, pass);
			
		} catch (SQLException e) {
			System.out.println("DB연결 실패!!!");
			return null;
		}
	}
	
}

