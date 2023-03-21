package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// 문제) Lprod_id값을 2개로 입력 받아서 두 값 중 작은값부터 큰값 사이의 자료들을 출력하시오.
public class jdbcTest03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("첫번째 Id값 입력");
		int idValue1 = sc.nextInt();
		System.out.println("두번째 Id값 입력");
		int idValue2 = sc.nextInt();
		
		if(idValue1 > idValue2) {
			int temp =0;
			temp = idValue1;
			idValue1 = idValue2;
			idValue2 = temp;
		}
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int max = Math.max(idValue1, idValue2);
		int min = Math.min(idValue1, idValue2);
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "PARK98";
			String pass = "java";
			
			conn = DriverManager.getConnection(url, user, pass);
			
			String sql = "select * "
					+ "from lprod "
					+ "where lprod_id >= '"+min+"' "
							+ "AND lprod_id <= '"+max+"'";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			System.out.println(" == SQL문 처리 결과 ==");
			
			while(rs.next()) {
				System.out.println("Lprod_id : " + rs.getInt("lprod_id"));
				System.out.println("Lprod_gu : " + rs.getString(2));
				System.out.println("Lprod_nm : " + rs.getString(3));
				System.out.println("------------------------------------------");
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		} finally {
			if(rs!=null) try {rs.close();}catch(SQLException e) {}
			if(stmt!=null) try {stmt.close();}catch(SQLException e) {}
			if(conn!=null) try {conn.close();}catch(SQLException e) {}
		}
		
		sc.close();
	}

}
