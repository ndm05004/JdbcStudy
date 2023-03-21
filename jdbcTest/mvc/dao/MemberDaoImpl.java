package kr.or.ddit.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.or.ddit.mvc.vo.MemberVO;
import kr.or.ddit.util.DBUtil2;
import kr.or.ddit.util.DBUtil3;

public class MemberDaoImpl implements IMemberDao {
	
	private static MemberDaoImpl memberDao;
	
	private MemberDaoImpl() {
		
	}
	
	public static MemberDaoImpl getInstance() {
		if(memberDao == null) memberDao = new MemberDaoImpl();
		return memberDao;
	}

	@Override
	public int insertMember(MemberVO memVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0; // 반환값이 저장될 변수

		try {
			conn = DBUtil3.getConnection();
			String sql = "insert into mymember (mem_id, mem_pass,"
					+ " mem_name, mem_tel, mem_addr)"
					+ " values(?, ?, ?, ?, ? ) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVo.getMem_id());
			pstmt.setString(2, memVo.getMem_pass());
			pstmt.setString(3, memVo.getMem_name());
			pstmt.setString(4, memVo.getMem_tel());
			pstmt.setString(5, memVo.getMem_addr());

			cnt = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}

		return cnt;
	}

	@Override
	public int deleteMember(String memId) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;

		try {

			conn = DBUtil3.getConnection();

			String sql = "delete from mymember where mem_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);

			cnt = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}

		return cnt;
	}

	@Override
	public int updateMember(MemberVO memVo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;

		try {

			conn = DBUtil3.getConnection();

			String sql = "update mymember set mem_pass = ?, " + " mem_name = ?, mem_tel = ?, mem_addr = ?"
					+ " where mem_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVo.getMem_pass());
			pstmt.setString(2, memVo.getMem_name());
			pstmt.setString(3, memVo.getMem_tel());
			pstmt.setString(4, memVo.getMem_addr());
			pstmt.setString(5, memVo.getMem_id());

			cnt = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}

		return cnt;
	}

	@Override
	public List<MemberVO> getAllMember() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberVO> list = new ArrayList<>();

		try {
			conn = DBUtil2.getConnection();
			String sql = "select * from mymember";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String memId = rs.getString(1);
				String memPass = rs.getString(2);
				String memName = rs.getString(3);
				String memTel = rs.getString(4);
				String memAddr = rs.getString(5);
				list.add(new MemberVO(memId, memPass, memName, memTel, memAddr));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
		return list;
	}

	@Override
	public int getMemberCount(String memId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int count = 0; // 반환값이 저장될 변수 선언

		try {

			conn = DBUtil3.getConnection();

			String sql = "select count(*) cnt from mymember where mem_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("cnt");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
		return count;
	}

	// Map의 정보 key값 : 수정할 컬럼명(filed), 수정할 데이터(data), 검색할회원ID(memId)
	@Override
	public int updateMember2(Map<String, String> paramMap) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement pstmt = null;

		int cnt = 0;

		try {

			conn = DBUtil3.getConnection();

			// 컬럼명을 정할땐 ?가 들어가면 안되고 바로 변수를 넣어주면 된다.
			String sql = "update mymember set " + paramMap.get("field") + " = ? where mem_id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramMap.get("data"));
			pstmt.setString(2, paramMap.get("memid"));

			cnt = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}

		return cnt;
	}

	@Override
	public int updateMember3(Map<String, String> dataMap) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0; // 반환값이 저장될 변수

		try {
			conn = DBUtil3.getConnection();

			String temp = ""; // SQL문의 set 이후에 수정할 컬럼 설정하는 부분이 저장될 변수
			for (String filedName : dataMap.keySet()) {
				if (!"memId".equals(filedName)) {
					// memID는 검색할 ID값에 대한 정보이기 때문에 수정할 컬럼을 설정할 때는 포함하지 않는다.
					if (!"".equals(temp)) {
						temp += ", ";
					}
					temp += filedName + " = '" + dataMap.get(filedName) + "'";
				}
			}
			String sql = "update mymember set " + temp + " where mem_id = ? ";

			pstmt = conn.prepareStatement(sql);

			int num = 1;
			for (String filedName : dataMap.keySet()) {
				if (!"memId".equals(filedName)) {
					pstmt.setString(num++, dataMap.get(filedName));
				}
			}

			pstmt.setString(num, dataMap.get("memId"));

			cnt = pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return cnt;
	}

}
