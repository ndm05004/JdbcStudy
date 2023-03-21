package kr.or.ddit.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import kr.or.ddit.mvc.service.IMemberService;
import kr.or.ddit.mvc.service.MemberServiceImpl;
import kr.or.ddit.mvc.vo.MemberVO;

public class MemberController {

	private Scanner sc;
	private IMemberService service; // Service객체 변수 선언
	
	
	

	public MemberController() {
		sc = new Scanner(System.in);
		service = MemberServiceImpl.getInstance();
	}

	public static void main(String[] args) {

		new MemberController().startMember();

	}

	public void startMember() {

		while (true) {
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
				updateMember2();
				break;

			case 6:
				displayAllMember();
				break;

			case 0:
				System.out.println("작업을 마칩니다...");
				return;

			default:
				System.out.println("작업 번호를 잘못 입력했습니다. 다시 입력하세요");
			}

		}

	}

//회원 정보를 수정하는 메서드 ==> 원하는 항목을 선택해서 수정하기
	private void updateMember2() {

		System.out.println();
		System.out.println("수정할 회원 정보를 입력하세요...");
		System.out.print("회원ID >> ");
		String id = sc.next();

		int count = service.getMemberCount(id);
		if (count == 0) {
			System.out.println(id + "는(은) 없는 회원ID 입니다...");
			System.out.println("수정 작업을 마칩니다...");
			return;
		}

		int num;
		String updateField = null;
		String updateFieldTitle = null;
		do {
			System.out.println();
			System.out.println(" 수정할 항목을 선택하세요...");
			System.out.println(" 1.비밀번호  2.회원이름  3.전화번호  4.회원주소");
			System.out.println("--------------------------------------");
			System.out.print("수정 항목 입력 >> ");
			num = sc.nextInt();

			switch (num) {
			case 1:
				updateField = "mem_pass";
				updateFieldTitle = "비밀번호";
				break;
			case 2:
				updateField = "mem_name";
				updateFieldTitle = "회원이름";
				break;
			case 3:
				updateField = "mem_tel";
				updateFieldTitle = "전화번호";
				break;
			case 4:
				updateField = "mem_addr";
				updateFieldTitle = "회원주소";
				break;
			default:
				System.out.println("수정 항목을 잘못 선택했습니다.");
				System.out.println("다시 선택하세요...");
			}

		} while (num < 1 || num > 4);
		sc.nextLine();
		
		System.out.println("수정할 " + updateFieldTitle + "를 입력하세요");
		String data = sc.next();

		// Map의 정보 key값 : 수정할 컬럼명(filed), 수정할 데이터(data), 검색할회원ID(memId)
		Map<String, String> paramMap = new HashMap<>();

		paramMap.put("filed", updateField);
		paramMap.put("data", data);
		paramMap.put("memid", id);

		int cnt = service.updateMember2(paramMap);

		if (cnt > 0) {
			System.out.println(updateFieldTitle + " 회원 정보 수정 완료!!!");
		} else {
			System.out.println(updateFieldTitle + " 수 정 실 패");
		}
	}

	private void displayAllMember() {

		System.out.println();
		System.out.println("----------------------------------------------------------");
		System.out.println(" ID         비밀번호          이름         전화번호        주소");
		System.out.println("----------------------------------------------------------");

		List<MemberVO> list = service.getAllMember();

		if (list == null || list.size() == 0) {
			System.out.println("등록된 회원 정보가 하나도 없습니다....");
		} else {
			for (MemberVO memberVO : list) {
				System.out.println(memberVO);
			}
		}
		System.out.println("----------------------------------------------------------");
		System.out.println("출력 끝...");
	}

	private void updateMember() {

		System.out.println();
		System.out.println("수정할 회원 정보를 입력하세요...");
		System.out.print("회원ID >> ");
		String id = sc.next();

		int count = service.getMemberCount(id);
		if (count == 0) {
			System.out.println(id + "는(은) 없는 회원ID 입니다...");
			System.out.println("수정 작업을 마칩니다....");
			return;
		}

		System.out.println();
		System.out.println("새로운 비밀번호 >> ");
		String pass = sc.next();

		System.out.println("새로운 회원이름 >> ");
		String name = sc.next();

		System.out.println("새로운 전화번호 >> ");
		String tel = sc.next();

		sc.nextLine();
		System.out.println("새로운 주소 >> ");
		String addr = sc.nextLine();

		int cnt = service.updateMember(new MemberVO(id, pass, name, tel, addr));

		if (cnt > 0) {
			System.out.println(id + " 회원 정보 수정 완료!!!");
		} else {
			System.out.println(id + " 수 정 실 패");
		}

	}

	private void deleteMember() {

		System.out.println();
		System.out.println("삭제할 회원 정보를 입력하세요...");
		System.out.println("회원ID >> ");
		String id = sc.next();

		int count = service.deleteMember(id);

		if (count > 0) {
			System.out.println("회원ID가 " + id + "인 회원 정보 삭제 성공!!!");
		} else {
			System.out.println(id + "회원은 없는 회원이거나 삭제 작업에 실패했습니다...");
		}
	}

	// 자료를 추가하는 메서드
	private void insertMember() {

		System.out.println();
		System.out.println("추가할 회원 정보를 입력하세요...");

		String id = null;
		int count = 0;

		do {
			System.out.println("회원ID >> ");
			id = sc.next();
			// 중복되면 다시 입력
			count = service.getMemberCount(id);
			if (count > 0) {
				System.out.println(id + "은(는) 이미 등록된 회원 ID입니다.");
				System.out.println("다른 회원 ID를 입력하세요.");
			}

		} while (count > 0);

		System.out.println("비밀번호 >> ");
		String pass = sc.next();

		System.out.println("회원이름 >> ");
		String name = sc.next();

		System.out.println("전화번호 >> ");
		String tel = sc.next();

		sc.nextLine(); // 버퍼 비우기

		System.out.println("회원주소 >> ");
		String addr = sc.nextLine();

		MemberVO memVo = new MemberVO(id, pass, name, tel, addr);

		int cnt = service.insertMember(memVo);

		if (cnt > 0) {
			System.out.println(id + " 회원 정보 추가 완료!!!");
		} else {
			System.out.println(id + " 회원 정보 추가 실패~~~");
		}
	}

	private int selectMenu() {

		System.out.println();
		System.out.println("----------------------- ");
		System.out.println("     1. 자 료  추 가      ");
		System.out.println("     2. 자 료  삭 제      ");
		System.out.println("     3. 자 료  수 정 (전체 수정) ");
		System.out.println("     4. 자 료  수 정 (선택한 항목 수정) ");
		System.out.println("     5. 전 체  자 료 출 력  ");
		System.out.println("     0. 작 업  끝       ");
		System.out.println("-------------------------");
		System.out.println("작업 선택 >> ");

		return sc.nextInt();
	}

}
