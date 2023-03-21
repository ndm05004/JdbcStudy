package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// 문제) 사용자로부터 Lprod_id값을 입력받아 입력한 값보다 lprod_id가 큰 자료들을 출력하시오
public class jdbcTest02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("id값 입력");
		int id = sc.nextInt();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "PARK98";
			String pass = "java";
			
			conn = DriverManager.getConnection(url, user, pass);
			
//			StringBuilder builder = new StringBuilder();
//			builder.append("select");
//			builder.append(" * ");
//			builder.append("from ");
//			builder.appcnd("lprod ");
//			builder.append("where ");
//			builder.append(" lprod_id > '"+id+"' ");
//			
//			
//			String sql = builder.toString();
			
			String sql ="select * from lprod where lprod_id > '"+id+"'";
			
			stmt = conn.createStatement();
	
			rs = stmt.executeQuery(sql);
			
			System.out.println(" == SQL문 처리 결과 ==");
			
			while(rs.next()) {
				System.out.println("Lprod_id : " + rs.getInt("lprod_id"));
				System.out.println("Lprod_gu : " + rs.getString(2));
				System.out.println("Lprod_nm : " + rs.getString("LPROD_NM"));
				System.out.println("-------------------------------------------------");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();}catch(SQLException e) {}
			if(stmt!=null) try {stmt.close();}catch(SQLException e) {}
			if(conn!=null) try {conn.close();}catch(SQLException e) {}
		}
		
		sc.close();
	}

}
