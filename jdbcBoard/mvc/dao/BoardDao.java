package kr.or.ddit.mvc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.mvc.vo.BoardVO;
import kr.or.ddit.util.DBUtil3;

public class BoardDao implements IBoardDao {

	private static BoardDao boardDao;
	
	private BoardDao() {
	}
	
	public static BoardDao getInstance() {
		if(boardDao==null) boardDao = new BoardDao();
		return boardDao;
	}
	
	@Override
	public int insertBoard(BoardVO boardVo) {
		Connection conn =null;
		PreparedStatement pstmt=null;
		int cnt =0;
		
		try {
			
			conn = DBUtil3.getConnection();
			
			StringBuilder builder = new StringBuilder();
			builder.append("INSERT INTO jdbc_board ( ");
			builder.append("    board_no, ");
			builder.append("    board_title, ");
			builder.append("    board_writer, ");
			builder.append("    board_date, ");
			builder.append("    board_content ");
			builder.append(" ) VALUES ( ");
			builder.append("    board_seq.NEXTVAL, ");
			builder.append("    ?, ");
			builder.append("    ?, ");
			builder.append("    SYSDATE, ");
			builder.append("    ? ");
			builder.append(") ");
			
			String sql = builder.toString();
			
			pstmt= conn.prepareStatement(sql);
			
			pstmt.setString(1, boardVo.getBoardTitle());
			pstmt.setString(2, boardVo.getBoardWriter());
			pstmt.setString(3, boardVo.getBoardContent());
			
			cnt = pstmt.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
			if (conn != null) try {conn.close();} catch (SQLException e) {}
		}
		
		return cnt;
	}

	@Override
	public BoardVO searchBoard(int selectBoardNo) {
		
		Connection conn = null;
		Statement stmt=null;
		ResultSet rs = null;
		BoardVO borad = null;
		try {
			conn = DBUtil3.getConnection();
			
			StringBuilder builder = new StringBuilder();
			builder.append(" SELECT ");
			builder.append("    * ");
			builder.append(" FROM ");
			builder.append("    jdbc_board ");
			builder.append(" WHERE ");
			builder.append("    board_no =" + selectBoardNo);
			
			String sql = builder.toString();
			stmt = conn.createStatement();

			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				int boardNo = rs.getInt(1);
				String boardTitle = rs.getString(2);
				String boardWriter = rs.getString(3);
				Date boardDate = rs.getDate(4);
				int boardCnt = rs.getInt(5);
				String boardContent = rs.getString(6);
				borad = new BoardVO(boardNo, boardTitle, boardWriter, boardDate, boardCnt, boardContent);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException e) {}
			if (stmt != null) try {stmt.close();} catch (SQLException e) {}
			if (conn != null) try {conn.close();} catch (SQLException e) {}
		}
		
		return borad;
	}

	@Override
	public int deleteBoard(int boardNo) {
		Connection conn =null;
		PreparedStatement pstmt=null;
		int cnt =0;
		
		try {
			conn = DBUtil3.getConnection();
			
			StringBuilder builder = new StringBuilder();
			builder.append("DELETE ");
			builder.append(" FROM ");
			builder.append("    jdbc_board ");
			builder.append(" WHERE ");
			builder.append("    board_no =" + boardNo);
			
			String sql = builder.toString();
			
			pstmt = conn.prepareStatement(sql);
			cnt = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
			if (conn != null) try {conn.close();} catch (SQLException e) {}
		}
		
		return cnt;
	}

	@Override
	public int updateBoard(BoardVO boardVo) {
		
		return 0;
	}

	@Override
	public List<BoardVO> allsearchBoard() {
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		List<BoardVO> boradlist = new ArrayList<>();
		try {
			conn = DBUtil3.getConnection();
			
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT ");
			builder.append("    * ");
			builder.append(" FROM ");
			builder.append("    jdbc_board ");
			
			String sql = builder.toString();
			pstmt = conn.prepareStatement(sql);
						
			rs = pstmt.executeQuery(sql);
			
			while(rs.next()) {
				int boardNo = rs.getInt(1);
				String boardTitle = rs.getString(2);
				String boardWriter = rs.getString(3);
				Date boardDate = rs.getDate(4);
				int boardCnt = rs.getInt(5);
				String boardContent = rs.getString(6);
				boradlist.add(new BoardVO(boardNo, boardTitle, boardWriter, boardDate, boardCnt, boardContent));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException e) {}
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
			if (conn != null) try {conn.close();} catch (SQLException e) {}
		}
		
		return boradlist;
	}
}
