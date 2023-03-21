package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class jdbcTest04 {

	public static void main(String[] args) {
		// bankinfo에 계좌번호 정보를 추가하는 예제
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "PARK98";
			String pass = "java";
			
			conn = DriverManager.getConnection(url, user, pass);
			
			System.out.println("계좌 번호 정보 추가하기...");
			System.out.println("계좌번호 입력 >>");
			String bankNo = sc.next();
			
			System.out.println("은행명 입력 >>");
			String bankName = sc.next();
			
			System.out.println("예금주명 입력 >>");
			String userName = sc.next();
			
			// Statement 객체를 이용하여 데이터 추가하기
//			String sql = "insert into bankinfo (bank_no, bank_name, bank_user_name, bank_date)" 
//					+ "values ('"+bankNo+"', '"+bankName+"', '"+userName+"', sysdate)";
//						
//			System.out.println("SQL문 ==> " + sql);
//			
//			stmt = conn.createStatement();
	
			// select문을 실행할 때는 executeQuery()메서드를 사용하고
			// select문이 아닌 문장(insert, delete, update 등)을 
			// 실행할 때는 executeUpdate()메서드를 사용한다.
			// executeUpdate()메서드의 반환값 ==> 작업에 성공한 레코드 수
			
//			int cnt = stmt.executeUpdate(sql);
			
			
			// PreparedStatement 객체를 이용하여 처리하기
			// SQL문을 작성할 때 SQL문에서 데이터가 들어갈 자리를 물음표(?)로 표시하여 작성한다.
			
			String sql = "insert into bankinfo (bank_no, bank_name, bank_user_name, bank_date)" 
					+ "values (?, ?, ?, sysdate)";
			
			// PreparedStatement객체 생성 ==> 사용할 SQL문을 인수값으로 넘겨준다.
			pstmt = conn.prepareStatement(sql);
			
			// SQL문의 물음표(?)자리에 들어갈 데이터를 셋팅한다.
			// 형식) pstmt.set 자료형 이름(물음표 번호, 셋팅할 데이터)
			pstmt.setString(1, bankNo);
			pstmt.setString(2, bankName);
			pstmt.setString(3, userName);
			
			// 데이터의 셋팅이 완료되면 실행한다.
			// select문일 경우 => executeQuery()메서드 이용
			// select문이 아닐 경우 ==> executeUpdate()메서드 이용
			int cnt = pstmt.executeUpdate();
			
			System.out.println(cnt);
			
			
		
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(stmt!=null) try { stmt.close();} catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try { conn.close();} catch(SQLException e) {}
		}
		
		
		
	}

}
