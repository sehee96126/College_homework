package WeatherInfo;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		DiameterInfo diameterInfo = new DiameterInfo();
		GetOpenApiData api = new GetOpenApiData();
		WeatherCurrentData nowWeather = new WeatherCurrentData();
		
		int dateNum = 10;
		Scanner scan = new Scanner(System.in);
		String xmlBuf;
		String sbuf, addr;
		String[] diameter = new String[2];
		int ibuf;

		diameterInfo.parseDiameterInfo();
		System.out.println("위 경고문구는 사용된 라이브러리가 현재 자바 버전과 맞지 않아 발생되는 문제로, 버그가 아닙니다.");
		System.out.println("(해당 라이브러리를 수정할 수 없음.)");
		while (true) {

			System.out.println("==============================================================");
			System.out.println("----- < 내일 비와 ? > -----");
			System.out.println("0 : 지역별 현재 날씨");
			System.out.println("1 : 지역별 좀이따 날씨");
			System.out.println("2 : 지역별 내일/모레 날씨");
			System.out.println("보고싶은 정보의 번호를 입력해주세요");
			System.out.print("--> ");
			sbuf = scan.nextLine().replaceAll(" ", "");
			
			if(sbuf.length()> 1) {
				System.out.println("\n잘못된 옵션값 입니다. 올바른 값을 입력해 주십시오");
				printEnter(scan);
				continue;
			}

			try {
				ibuf = Integer.parseInt(sbuf);
				if (ibuf < 0 || ibuf > 2) {
					System.out.println("\n잘못된 옵션값 입니다. 올바른 값을 입력해 주십시오");
					printEnter(scan);
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("\n잘못된 옵션값 입니다. 올바른 값을 입력해 주십시오");
				printEnter(scan);
				continue;
			}

			System.out.println("주소를 입력하세요. ex) 서울시 종로구, 경기도 부천시");
			System.out.print("--> ");
			addr = scan.nextLine();
			diameter = diameterInfo.checkDiameter(addr);

			if (diameter == null || diameter[0].equals("0") || diameter[1].equals("0")) {
				System.out.println("잘못된 주소값입니다.");
				printEnter(scan);
				continue;
			}
			
			if (ibuf == 0) {
				try {
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 0);
					nowWeather.parseXml(xmlBuf);
					
					System.out.println("<현재 기상 조회>");
					System.out.println(
							"=============================================================================================");
					System.out.println("\t시간\t기온(℃)\t습도(%)\t강수량(mm)");
					System.out.println(
							"=============================================================================================");
					
					nowWeather.printNow();
				} catch (IOException e) {
					System.out.println("현재 기상을 조회하는데 실패하였습니다.");
					printEnter(scan);
					continue;
				}
			}
			else if (ibuf == 1) {
				try {
					WeatherForecast fcstWeather = new WeatherForecast(dateNum, 1);
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 1);
					fcstWeather.parseXml(xmlBuf);
					System.out.println("<단기 예보 조회>");
					System.out.println(
							"=============================================================================================");
					System.out.println("\t날짜\t\t시간\t기온(℃)\t습도(%)\t강수량(mm)");
					System.out.println(
							"=============================================================================================");
					fcstWeather.print();
				} catch (IOException e) {
					System.out.println("현재 기상을 조회하는데 실패하였습니다.");
					printEnter(scan);
					continue;
				}
			}
			else if (ibuf == 2) {
				try {
					WeatherForecast fcstWeather = new WeatherForecast(dateNum, 0);
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 2);
					fcstWeather.parseXml(xmlBuf);
					System.out.println("<동네 예보 조회>");
					System.out.println(
							"======================================================================================================================");
					System.out.println("\t날짜\t\t시간\t날씨\t기온(℃)\t습도(%)\t강수량(mm)\t아침 최저기온(℃)\t낮 최고기온(℃)\t강수확률(%)");
					System.out.println(
							"======================================================================================================================");
					fcstWeather.print();
				} catch (IOException e) {
					System.out.println("현재 기상을 조회하는데 실패하였습니다.");
					printEnter(scan);
					continue;
				}
			}
			printEnter(scan);
		}
	}

	private static void printEnter(Scanner scan) {
		System.out.println("Enter를 입력하면 처음으로 돌아갑니다.");
		scan.nextLine();
	}
}
