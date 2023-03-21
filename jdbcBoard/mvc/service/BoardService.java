package kr.or.ddit.mvc.service;

import java.util.List;

import kr.or.ddit.mvc.dao.BoardDao;
import kr.or.ddit.mvc.dao.IBoardDao;
import kr.or.ddit.mvc.vo.BoardVO;

public class BoardService implements IBoardService {
	
	private static BoardService boardService;
	
	private IBoardDao boardDao;
	
	private BoardService(){
		boardDao = BoardDao.getInstance();
	}
	
	public static BoardService getInstance() {
		if(boardService==null) boardService = new BoardService();
		return boardService;
	}
	

	@Override
	public int insertBoard(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return boardDao.insertBoard(boardVo);
	}

	@Override
	public BoardVO searchBoard(int boardNo) {
		// TODO Auto-generated method stub
		return boardDao.searchBoard(boardNo);
	}

	@Override
	public int deleteBoard(int boardNo) {
		// TODO Auto-generated method stub
		return boardDao.deleteBoard(boardNo);
	}

	@Override
	public int updateBoard(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BoardVO> allsearchBoard() {
		// TODO Auto-generated method stub
		return boardDao.allsearchBoard();
	}

}
