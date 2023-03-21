package kr.or.ddit.mvc.controller;

import java.util.List;
import java.util.Scanner;

import kr.or.ddit.mvc.service.BoardService;
import kr.or.ddit.mvc.service.IBoardService;
import kr.or.ddit.mvc.vo.BoardVO;


public class BoardController {
	
	private Scanner sc;
	private IBoardService service;
	
	public BoardController() {
		sc = new Scanner(System.in);
		service = BoardService.getInstance();
	}
	
	public static void main(String[] args) {
		new BoardController().startBoard();
	}
	
	public void startBoard() {
		
		while(true) {
			System.out.println("-------------------------------------------------------------");
			System.out.println(" No	        제 목            작성자 	조회수   ");         
			System.out.println("-------------------------------------------------------------");
			
			List<BoardVO> list = service.allsearchBoard();

			if (list == null || list.size() == 0) {
				System.out.println("등록된 회원 정보가 하나도 없습니다....");
			} else {
				for (BoardVO boardVO : list) {
					System.out.println(boardVO.getBoardNo() +" 	      "+ boardVO.getBoardTitle() + "	     " +
							 boardVO.getBoardTitle() + " 	 " +  boardVO.getBoardCnt());
				}
			}
			
			
			System.out.println("-------------------------------------------------------------");
			
			System.out.println("메뉴 : 1. 새글작성     2. 게시글보기    3. 검색    0. 작업끝");
			System.out.println("작업선택 >> ");
			int select = sc.nextInt();
			
			switch (select) {
			case 1:
				
				System.out.println("새글 작성하기");
				System.out.println("-----------------------------------");
				
				System.out.print("제 목 :");
				String title = sc.next();
				System.out.print("작성자 :");
				String author = sc.next();
				System.out.print("내 용 :");
				String content = sc.next();
				
				int cnt =service.insertBoard(new BoardVO(title, author,content));
				
				if(cnt > 0) {
					System.out.println("새글이 추가 되었습니다.");
				} else {
					System.out.println("추 가 실 패");
				}
				
				break;
			case 2:
				System.out.println("보기를 원하는 게시물 번호 입력 >>");
				int boardNo = sc.nextInt();
				
				BoardVO boardVO = service.searchBoard(boardNo);
				System.out.println("-----------------------------------");
				System.out.println("- 제  목 : " + boardVO.getBoardTitle());
				System.out.println("- 작성자 : " + boardVO.getBoardWriter() );
				System.out.println("- 내  용 : " + boardVO.getBoardContent());
				System.out.println("- 작성일 : " + boardVO.getBoardDate());
				System.out.println("- 조회수 : " + boardVO.getBoardCnt());
				System.out.println("-----------------------------------");
				
				System.out.println("메뉴 : 1. 수정    2. 삭제    3. 리스트로 가기");
				int menu = sc.nextInt();
				
				switch (menu) {
				case 1:
					service.updateBoard(boardVO);
					break;
				case 2:
					service.deleteBoard(boardNo);
					break;
				case 3:
					return;

				default:
					break;
				}
				
				
				break;
			case 3:
				
				break;
			case 0:
				System.out.println("시스템 종료");
				return;
				
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
			
		}
	}

}
