package kr.or.ddit.basic.singleton;

public class SingletonTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// MySingleton test1 = new Mysingleton() // 외부에서 new 명령으로 생성 불가 
		
		MySingleton test2 = MySingleton.getInstance();
		MySingleton test3 = MySingleton.getInstance();
		
		System.out.println("test2 ==> " + test2.toString());
		System.out.println("test3 ==> " + test3.toString());
		
		System.out.println(test2 == test3);
		System.out.println(test2.equals(test3));
		
		test2.displayTest();
	}

}
