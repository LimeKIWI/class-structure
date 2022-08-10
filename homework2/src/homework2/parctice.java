package homework2;

class Car {
	int idNumber; // 차량번호
	int maxPassenger; // 최대승객수
	int curPassanger; // 현재승객수
	int defaultFee; // 기본요금
	int totalFee; // 벌어들인요금
	int oilGage; // 주유량
	int speed; // 현재속도
	String status; // 현재상태

	// 자동차기본세팅
	public Car() {
		this.totalFee = 0;
		this.oilGage = 100;
		this.speed = 0;
	}

	// 최대승객수확인
	public boolean isMaxPassanger(int newPassangerNum) {
		if (maxPassenger > newPassangerNum) {
			return true;
		}
		System.out.println("최대 승객 수 초과!");
		return false;
	}

	// 달릴수 있는 기름양인지 확인
	public boolean isVaildOil(boolean alert) {
		if (oilGage < 10) { // 주유량이 10미만이면
			if (alert)
				System.out.println("!!!주유필요!!!");
		}
		return (oilGage < 10) ? false : true;
	}
	
	// 주유량 출력
	public int getOilGage() {		
		return oilGage;
	}

	// 주유량 변경
	public void changeOilGage(int amount) {
		oilGage = (amount + oilGage < 0) ? 0 : amount + oilGage; // 연료의 최소량은 0 이하로 떨어지지않는다.
	}

}

class Bus extends Car {
	private static int serialCount = 1;	// 고유넘버링

	public Bus() {
		this.idNumber = serialCount;
		Bus.serialCount++;
		this.maxPassenger = 30;
		this.curPassanger = 0;
		this.defaultFee = 1000;
		this.status = "운행중";
	}

	// 승객 탑승시키기
	public void addPassenger(int numOfPass) {
		// 최대승객수보다 많거나 운행중이아니면 탑승시키지않는다.
		if (isMaxPassanger(numOfPass + curPassanger) && status.equals("운행중")) {
			curPassanger += numOfPass; // 현재 승객수를 증가시키고
			totalFee += (numOfPass * defaultFee); // 인원수만큼 요금을 받는다.
			printStatus();
		} else
			System.out.println("탑승할 수 없습니다.\n");
	}

	// 속도바꾸기. 버스만 오일게이지 확인후 속도를 바꿀 수 있고 택시요구사항에는 없길래 버스와 택시를 따로만듬.
	// 택시도 연료량이 10이상일때만 바꾸게 하고싶다면 changeSpeed메소드를 Car클래스로 이동시키고 하위클래스의 메소드를 삭제하면된다.
	public void changeSpeed(int newSpeed) {
		if (isVaildOil(false)) { // 연료량이 10이상일때만 속도변경시킨다.
			speed = (speed + newSpeed < 0) ? 0 : speed + newSpeed; // 속도가 0이하로는 떨어지지 않는다.
			System.out.println("현재 속도 : " + speed);
		}
	}

	// 상태가 변경되면 승객수가 초기화되므로 전부 초기화시킨다.
	public void switchStatus(String str) {
		if (isVaildOil(false)) {
			status = str;
			curPassanger = 0;
		}
	//	printStatus();
	}
	
	// 기름넣기
	public void addOil(int amount) {
		this.changeOilGage(amount);
		if (!isVaildOil(false))
			status = "차고지행";
		printStatus();
	}

	// 차량상태 출력
	public void printStatus() {
		int oilamount = getOilGage();
		System.out.println("버스번호 : " + idNumber);
		if (status.equals("운행중")) {
			System.out.println("상태 : 운행중");
			System.out.println("탑승 승객 수 : " + curPassanger);
			System.out.println("잔여 승객 수 : " + (maxPassenger - curPassanger));
			System.out.println("요금확인 : " + curPassanger * defaultFee);
			System.out.println("누적요금확인 : " + totalFee);
		} else {
			System.out.println("상태 : 차고지행");
		}
		System.out.println("주유량  " + oilamount);
		isVaildOil(true);
		System.out.println();
	}

}

class Texi extends Car {
	private static int serialCount = 1; // 고유넘버링
	String destination; // 목적지이름
	int destDistance; // 목적지까지거리
	int additionalFee; // 추가요금
	int defaultDistance; // 기본거리
	int defaultFee; // 기본요금

	public Texi() {
		this.idNumber = serialCount;
		Texi.serialCount++;
		this.maxPassenger = 4;
		this.curPassanger = 0;
		this.defaultFee = 3000;
		this.defaultDistance = 1;
		this.additionalFee = 1000;
		this.status = "일반";
	}

	// 승객 탑승시키기
	public void addPassenger(int numOfPass, String dest, int distantence) {
		// 최대승객수보다 많거나 일반상태가아니면 탑승시키지않는다.
		if (isMaxPassanger(numOfPass) && status.equals("일반")) {
			curPassanger += numOfPass; // 현재 승객수를 증가시키고
			destDistance = distantence;
			destination = dest;
			status = "운행중"; 
			printStatus();
		} else
			System.out.println("탑승할 수 없습니다.\n");
	}

	// 속도바꾸기. 버스만 오일게이지 확인후 속도를 바꿀 수 있고 택시요구사항에는 없길래 버스와 택시를 따로만듬.
	// 택시도 연료량이 10이상일때만 바꾸게 하고싶다면 changeSpeed메소드를 Car클래스로 이동시키고 하위클래스의 메소드를 삭제하면된다.
	public void changeSpeed(int newSpeed) {
		speed = (speed + newSpeed < 0) ? 0 : speed + newSpeed; // 속도가 0이하로는 떨어지지 않는다.
		System.out.println("현재 속도 : " + speed);
	}

	// 추가요금계산
	public int getAdditionalPay() {
		int dist = destDistance - defaultDistance;
		if (dist > 0) {
			return defaultFee + (dist * additionalFee);
		}
		return defaultFee;
	}

	// 결제하기
	public void pay() {
		totalFee += getAdditionalPay();
		curPassanger = 0;
		run();
	}

	// 기름넣기
	public void addOil(int amount) {
		this.changeOilGage(amount);
		if(!isVaildOil(false))
			status = "운행불가";
		//printStatus();
	}

	// 운행시작
	public void run() {
		if (isVaildOil(false)) 
			status = "일반";
		else
			status = "운행불가";
		curPassanger = 0;
		printStatus();
	}

	
	// 차량상태 출력
	public void printStatus() {
		int oilamount = getOilGage();
		System.out.println("택시번호 : " + idNumber);
		if (status.equals("운행불가")) {
			System.out.println("상태 : 운행불가");
		} else if (status.equals("운행중")) {
			System.out.println("상태 : 운행중");
			System.out.println("탑승 승객 수 : " + curPassanger);
			System.out.println("잔여 승객 수 : " + (maxPassenger - curPassanger));
			System.out.println("기본요금확인 : " + defaultFee);
			System.out.println("목적지 : " + destination);
			System.out.println("목적지까지 거리 : " + destDistance + "km");
			System.out.println("지불할 요금 : " + getAdditionalPay());
		} else {
			System.out.println("상태 : 일반");
		}
		System.out.println("누적 요금 : " + totalFee);
		System.out.println("주유량  " + oilamount);
		isVaildOil(true);
		System.out.println();

	}

}

public class parctice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bus bus1 = new Bus();
		Bus bus2 = new Bus();
		bus1.printStatus();
		bus2.printStatus();
		
		bus1.addPassenger(2);
		bus1.addOil(-50);
		bus1.switchStatus("차고지행");
		bus1.addOil(10);
		bus1.switchStatus("운행중");
		bus1.addPassenger(45);
		bus1.addPassenger(5);
		bus1.addOil(-55);
						
		Texi t1 = new Texi();
		Texi t2 = new Texi();
		t1.printStatus();
		t2.printStatus();
		
		t1.addPassenger(2, "서울역", 2);
		t1.addOil(-80);
		t1.pay();
		t1.addPassenger(5, "구로디지털단지역", 12);
		t1.addPassenger(3, "구로디지털단지역", 12);
		t1.addOil(-20);
		t1.pay();

		
	}

}
