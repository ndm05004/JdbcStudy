package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.Map;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.util.DBUtil2;
import kr.or.ddit.util.DBUtil3;

public class JdbcTest06Solution {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Scanner sc = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new JdbcTest06Solution().startMember();
	}
	
	
	// 자원을 반납하는 메서드
	private void disConnect() {
		if(rs!=null) try { rs.close();}catch(SQLException e) {}
		if(pstmt!=null) try { pstmt.close();}catch(SQLException e) {}
		if(conn!=null) try { conn.close();}catch(SQLException e) {}		
	}

	
	public void startMember() {
		
		
		while(true) {
			int select = selectMenu();
			
			switch (select) {
			case 1:
				insertMember();
				break;
				
			case 2:
				deleteMember();
				break;
				
			case 3:
				updateMember();
				break;
				
			case 4:
				displayAllMember();
				break;
				
			case 5:
				updateMember2();
				break;		
				
			case 6:
				updateMember3(); 
				break;		

			case 0:
				System.out.println("작업을 마칩니다...");
				return;

			default:
				System.out.println("작업 번호를 잘못 입력했습니다. 다시 입력하세요");
			}
			
		}
		
	}
	
	// 회원 정보를 수정하는 메서드 ==> 입력한 항목만 수정하기
	private void updateMember3() {
		
	      System.out.println();
	      System.out.println("수정할 회원 정보를 입력하세요...");
	      System.out.print("회원ID >> ");
	      String id = sc.next();
	      
	      int count = getMemberCount(id);
	      if(count==0) {
	         System.out.println(id + "는(은) 없는 회원ID 입니다...");
	         System.out.println("수정 작업을 마칩니다....");
	         return;
	      }
	      // key : 수정할 컬럼명, value값: 수정할 데이터 값
	      // 수정할 데이터 값이 있을 때만 Map에 추가된다.
	      Map<String, String> dataMap = new HashMap<>();
	      
	      System.out.println();
	      sc.nextLine(); // 버퍼 비우기
	      
	      System.out.println("새로운 비밀번호 >> ");
	      String newPass = sc.nextLine().trim();
	      if(!"".equals(newPass)) {
	    	  dataMap.put("mem_pass", newPass);	    	  
	      }
	      
	      System.out.println("새로운 회원이름 >> ");
	      String newName = sc.nextLine().trim();
	      if(!"".equals(newName)) {
	    	  dataMap.put("mem_name", newName);
	      }
	      
	      System.out.println("새로운 전화번호 >> ");
	      String newTel = sc.nextLine().trim();
	      if(!"".equals(newTel)) {
	    	  dataMap.put("mem_tel", newTel);
	      }
	      
	      System.out.println("새로운 주소 >> ");
	      String newAddr = sc.nextLine().trim();
	      if(!"".equals(newAddr)) {
	    	  dataMap.put("mem_addr", newAddr);
	      }
	      
	      try {
	         conn = DBUtil.getConnection();
	         
	         String temp =""; // SQL문의 set 이후에 수정할 컬럼 설정하는 부분이 저장될 변수
	         for (String fileName : dataMap.keySet()) {
				if(!"".equals(temp)) {
					temp += ", ";
				}
	        	 temp += fileName + " = '" + dataMap.get(fileName)+"'";
				
	         }
	         
	         String sql = "update mymember set " + temp+ " where mem_id = ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, id);
	         
	         int cnt = pstmt.executeUpdate();
	         
	         if(cnt>0) {
	            System.out.println(id + " 회원 정보 수정 완료!!!");
	         }else {
	            System.out.println(id + " 수 정 실 패");
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }finally {
	         disConnect();
	      }	
	}


	// 회원 정보를 수정하는 메서드 ==> 원하는 항목을 선택해서 수정하기
	private void updateMember2() {
		
	    System.out.println();
	    System.out.println("수정할 회원 정보를 입력하세요...");
	    System.out.print("회원ID >> ");
	    String id = sc.next();
	      
	    int count = getMemberCount(id);
	      if(count==0) {
	         System.out.println(id + "는(은) 없는 회원ID 입니다...");
	         System.out.println("수정 작업을 마칩니다....");
	         return;
	      }
	      
		System.out.println();
		System.out.println("수정할 항목을 선택하세요");
		System.out.println("1. 비밀번호, 2.이름, 3.전화번호, 4.주소");
		int select = sc.nextInt();
		
		String changeItem="";
		String change="";
		String updateFiledTitle="";
		
		try {
			conn = DBUtil.getConnection();
		
			switch (select) {
			case 1:
				changeItem = "mem_pass";
				updateFiledTitle = "비밀번호";
				
				break;
			case 2:
				changeItem = "mem_name";
				updateFiledTitle = "이름";
				break;
			case 3:
				changeItem = "mem_tel";
				updateFiledTitle = "전화번호";
				break;
			case 4:
				changeItem = "mem_addr";
				updateFiledTitle = "주소";
				break;
					
			default:
				System.out.println("없는 항목입니다.");
				break;
			}
			
			
			System.out.println("수정할 " + updateFiledTitle + "를 입력하세요");
			change = sc.next();
			
			// 컬럼명을 정할땐 ?가 들어가면 안되고 바로 변수를 넣어주면 된다.
			String sql ="update mymember set "+changeItem+" = ? where mem_id = ?";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, change);
			pstmt.setString(2, id);
	         
	         int cnt = pstmt.executeUpdate();
	         
	         if(cnt>0) {
	            System.out.println(changeItem + " 회원 정보 수정 완료!!!");
	         }else {
	            System.out.println(changeItem + " 수 정 실 패");
	         }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnect();
		}
	}


	private void updateMember() {
	      System.out.println();
	      System.out.println("수정할 회원 정보를 입력하세요...");
	      System.out.print("회원ID >> ");
	      String id = sc.next();
	      
	      int count = getMemberCount(id);
	      if(count==0) {
	         System.out.println(id + "는(은) 없는 회원ID 입니다...");
	         System.out.println("수정 작업을 마칩니다....");
	         return;
	      }
	      
	      System.out.println();
	      System.out.println("새로운 비밀번호 >> ");
	      String newPass = sc.next();

	      System.out.println("새로운 회원이름 >> ");
	      String newName = sc.next();
	      
	      System.out.println("새로운 전화번호 >> ");
	      String newTel = sc.next();
	      
	      sc.nextLine();
	      System.out.println("새로운 주소 >> ");
	      String newAddr = sc.nextLine();
	      
	      try {
	         conn = DBUtil3.getConnection();
	         
	         String sql = "update mymember set mem_pass = ?, "
	               + " mem_name = ?, mem_tel = ?, mem_addr = ?"
	               + " where mem_id = ?";
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, newPass);
	         pstmt.setString(2, newName);
	         pstmt.setString(3, newTel);
	         pstmt.setString(4, newAddr);
	         pstmt.setString(5, id);
	         
	         int cnt = pstmt.executeUpdate();
	         
	         if(cnt>0) {
	            System.out.println(id + " 회원 정보 수정 완료!!!");
	         }else {
	            System.out.println(id + " 수 정 실 패");
	         }
	      } catch (SQLException e) {
	         // TODO: handle exception
	      }finally {
	         disConnect();
	      }
	   }


	// 전체 회원 정보를 출력하는 메서드
	private void displayAllMember() {
		
		System.out.println();
		System.out.println("----------------------------------------------------------");
		System.out.println(" ID         비밀번호          이름         전화번호        주소");
		System.out.println("----------------------------------------------------------");
		
		try {	
			conn = DBUtil2.getConnection();
			String sql = "select * from mymember";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			int cnt =0;
			
			while(rs.next()) {
				cnt++;
				String memId = rs.getString("mem_id");
				String memPass = rs.getString(2);
				String memName = rs.getString(3);
				String memTel = rs.getString(4);
				String memAddr = rs.getString(5);
				System.out.println(memId + "\t" + memPass + "\t" + memName + "\t" + memTel + "\t" + memAddr);
				System.out.println("----------------------------------------------------------");
			}
			
			if(cnt ==0) {
				System.out.println("등록된 회원 정보가 하나도 없습니다....");
			}
			

		} catch (Exception e) {
			// TODO: handle exception
		}

		
	}


	private void deleteMember() {
		
		System.out.println();
		System.out.println("삭제할 회원 정보를 입력하세요...");
		System.out.println("회원ID >> ");
		String id = sc.next();
		
		try {
			
			conn = DBUtil.getConnection();
			
			String sql="delete from mymember where mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println("회원ID가 " + id + "인 회원 정보 삭제 성공!!!");
			}else {
				System.out.println(id + "회원은 없는 회원이거나 삭제 작업에 실패했습니다...");				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect();
		}
		
		
	}

	// 회원 ID를 매개변수로 받아서 해당 회원ID의 개수를 반환하는 메서드
	private int getMemberCount(String memId) {
		
		int count =0;
		
		try {
			
			conn = DBUtil.getConnection();
			
			String sql = "select count(*) cnt from mymember where mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("cnt");
			}

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnect();
		}

		return count;
		
	}
	
	

	private void insertMember() {
		
		System.out.println();
		System.out.println("추가할 회원 정보를 입력하세요...");
		
		String id = null;
		int count = 0;
		
		do {
			System.out.println("회원ID >> ");
			id = sc.next();
			// 중복되면 다시 입력
			count = getMemberCount(id);
			if(count > 0) {
				System.out.println(id + "은(는) 이미 등록된 회원 ID입니다.");
				System.out.println("다른 회원 ID를 입력하세요.");
			}
			
		} while (count >0);

		System.out.println("비밀번호 >> ");
		String pass = sc.next();
		
		System.out.println("회원이름 >> ");
		String name = sc.next();
		
		System.out.println("전화번호 >> ");
		String tel = sc.next();
		
		sc.nextLine(); // 버퍼 비우기
		
		System.out.println("회원주소 >> ");
		String addr = sc.nextLine();
		
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into mymember (mem_id, mem_pass, mem_name, mem_tel, mem_addr)"
					+ " values(?, ?, ?, ?, ? ) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			pstmt.setString(3, name);
			pstmt.setString(4, tel);
			pstmt.setString(5, addr);

			int cnt = pstmt.executeUpdate();
			
			if(cnt>0) {
				System.out.println(id + " 회원 정보 추가 완료!!!");
			}else {
				System.out.println(id + " 회원 정보 추가 실패~~~");
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			disConnect();
		}
		
		
	}


	private int selectMenu() {
		
		System.out.println();
		System.out.println("----------------------- ");
		System.out.println("     1. 자 료  추 가      ");
		System.out.println("     2. 자 료  삭 제      ");
		System.out.println("     3. 자 료  수 정 (전체 수정)     ");
		System.out.println("     4. 전 체  자 료 출 력  ");
		System.out.println("     5. 자 료  수 정 2 (선택수정) ");
		System.out.println("     6. 자 료  수 정 3 (입력항목만수정) ");
		System.out.println("     0. 작 업  끝       ");
		System.out.println("-------------------------");
		System.out.println("작업 선택 >> ");

		return sc.nextInt();
	}
	
}
