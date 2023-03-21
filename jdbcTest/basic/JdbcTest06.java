package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;

/*
    회원을 관리하는 프로그램을 작성하시오.
    ( MYMEMBER 테이블 이용)
    
    아래의 메뉴를 모두 구현하시오. (CRUD기능 구현하기)
    메뉴 예시)
    -------------
    1. 자료 추가      insert (C)
    2. 자료 삭제      delete (D)
    3. 자료 수정      upate  (U)
    4. 전체 자료 출력  select (S)
    0. 작업 끝
    --------------
	
	조건)
	1) 자료 추가에서 '회원ID'는 중복되지 않는다. (중복되면 다시 입력 받는다.)
	2) 자료 삭제는 '회원ID'를 입력받아서 처리한다.
	3) 자료 수정에서 '회원ID'는 변경되지 않는다.

*/


public class JdbcTest06 {
	
	Scanner sc = new Scanner(System.in);
	Connection conn = null;

	
	public JdbcTest06() {
		
		conn = DBUtil.getConnection();
	}
	
	
	public static void main(String[] args) {
	
		new JdbcTest06().start();
		
		
	}
	
	public void start() {
		
		while(true) {
			int select = selectMenu();
			
			switch (select) {
			case 1:
				insert();
				break;
				
			case 2:
				delete();
				break;
				
			case 3:
				
				break;
				
			case 4:
				
				break;
				
			case 0:
				break;

			default:
				break;
			}
			
		}

	}

	private void delete() {
		// TODO Auto-generated method stub
		
		System.out.println("삭제할 ID를 입력하세요");
		
	}


	private void insert() {
		// TODO Auto-generated method stub
		
		System.out.println(" 추가할 자료를 입력하세요 ");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String memId = "";
		try {
			do {
				System.out.println("id를 입력하세요");
				memId = sc.next();
				
				String sql1 = "select count(*) from mymember where mem_id = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, memId);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					if(rs.getInt(1)!=0) {
						System.out.println("다시 입력하세요");
					}else {
						break;
					}
				}
			}
			while(rs.getInt(1)!=0);
			
			System.out.println("비밀번호를 입력하세요");
			String memPass = sc.next();
			System.out.println("이름을 입력하세요");
			String memName = sc.next();
			System.out.println("전화번호를 입력하세요");
			String memTel= sc.next();
			System.out.println("주소를 입력하세요");
			String memAddr =sc.next();
			
			
			String sql2 = "insert into mymember (mem_id, mem_pass, mem_name, mem_tel, mem_addr)"
					+ "value(?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql2);
			
			pstmt.setString(1, memId);
			pstmt.setString(2, memPass);
			pstmt.setString(3, memName);
			pstmt.setString(4, memTel);
			pstmt.setString(5, memAddr);
			
			int cnt = pstmt.executeUpdate();
			
			check(cnt);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void check(int cnt) {
		if(cnt > 0) {
			System.out.println("성공");
		} else {
			System.out.println("실패");
		}
	}


	private int selectMenu() {
		
		System.out.println("-------------------- ");
		System.out.println("     1. 자료 추가      ");
		System.out.println("     2. 자료 삭제      ");
		System.out.println("     3. 자료 수정      ");
		System.out.println("     4. 전체 자료 출력  ");
		System.out.println("     0. 작업 끝       ");
		System.out.println("---------------------");
		
		return sc.nextInt();
	}
	
	
}
