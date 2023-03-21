package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;

/*
  	LPROD테이블에 새로운 데이터 추가하기
  	
  	lprod_gu와 lprod_nm은 직접 입력 받아서 처리하고,
  	lprod_id와 현재의 lprod_id중에서 제일 큰 값보다 1 크게 한다.
  	
  	입력받은 lprod_gu가 이미 등록되어 있으면 다시 입력 받아서 처리한다.
*/

public class JdbcTest05 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			
//			String url = "jdbc:oracle:thin:@localhost:1521:xe";
//			String user = "PARK98";
//			String pass = "java";
			
			conn = DBUtil.getConnection();
			
			System.out.println("정보 추가하기");
			
			System.out.println("lprod_gu 입력 >>");
			String gu = sc.next();
			
			String sql1= "select count(*) from lprod where lprod_gu = ?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, gu);
			
			rs =pstmt.executeQuery();
			
			if(rs.next())
				// 데이터가 하나일 땐 if문으로 여러개일땐 while문을 사용한다.
			if(rs.getInt(1)==0) {
					
				System.out.println("lprod_nm 입력 >>");
				String nm = sc.next();
					
				String sql = "insert into lprod (lprod_id, lprod_gu, lprod_nm)"
							+ "values ( (select max(lprod_id)+1 from lprod), ?, ?)";
					
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, gu);
				pstmt.setString(2, nm);
					
				int cnt = pstmt.executeUpdate();	
					
				if(cnt > 0) {
					System.out.println("등록 성공");
				}else {
					System.out.println("등록 실패");						
				}
					
				}else {
					System.out.println("중복된 gu입니다.");
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
