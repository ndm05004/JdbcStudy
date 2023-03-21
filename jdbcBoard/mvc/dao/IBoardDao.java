package kr.or.ddit.mvc.dao;

import java.util.List;

import kr.or.ddit.mvc.vo.BoardVO;

public interface IBoardDao {
	
	/**
	 * 게시글을 입력하는 메서드
	 * 
	 * @param boardVo
	 * @return
	 */
	public int insertBoard(BoardVO boardVo);
	
	
	/**
	 * 선택한 게시글을 자세하게 보는 메서드
	 * @param boardNo
	 * @return
	 */
	public BoardVO searchBoard(int boardNo);
	
	
	/**
	 * 선택한 게시글을 삭제하는 메서드
	 * @param boardVo
	 * @return
	 */
	public int deleteBoard(int boardNo);
	
	
	/**
	 * 선택한 게시글을 업데이트 하는 메서드
	 * @param boardVo
	 * @return
	 */
	public int updateBoard(BoardVO boardVo);
	
	
	/**
	 * 전체 게시글을 조회하는 메서드
	 * @return
	 */
	public List<BoardVO> allsearchBoard();
	

}
