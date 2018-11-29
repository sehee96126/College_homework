package WeatherInfo;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		DiameterInfo diameterInfo = new DiameterInfo();
		GetOpenApiData api = new GetOpenApiData();
		WeatherCurrentData nowWeather = new WeatherCurrentData();
		WeatherForecastData fcstWeather = new WeatherForecastData();
		
		Scanner scan = new Scanner(System.in);
		String xmlBuf;
		String sbuf, addr;
		String[] diameter = new String[2];
		int ibuf;

		diameterInfo.parseDiameterInfo();
		diameterInfo.print();
		while (true) {

			System.out.println("==============================================================");
			System.out.println("원하는 정보의 종류를 입력해 주세요.");
			System.out.println("0 : 현재 기상 조회");
			System.out.println("1 : 초 단기 기사예보 조회");
			System.out.println("2 : 동네 예보 조회");
			System.out.println("3 : test");
			System.out.print("입력값 : ");
			sbuf = scan.nextLine();

			try {
				ibuf = Integer.parseInt(sbuf);
				if (ibuf < 0 || ibuf > 3) {
					System.out.println("\n잘못된 옵션값 입니다. 올바른 값을 입력해 주십시오");
					printEnter(scan);
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("\n잘못된 옵션값 입니다. 올바른 값을 입력해 주십시오");
				printEnter(scan);
				continue;
			}

			System.out.print("주소를 입력하세요 ex) 서울특별시  : ");
			addr = scan.nextLine();
			diameter = diameterInfo.checkDiameter(addr);

			if (diameter == null) {
				System.out.println("잘못된 주소값입니다.");
			}
			
			if (ibuf == 0) {
				try {
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 0);
					nowWeather.parseXml(xmlBuf);
					nowWeather.print();
				} catch (IOException e) {
					System.out.println("현재 기상을 조회하는데 실패하였습니다.");
					printEnter(scan);
					continue;
				}
			}
			else if (ibuf == 1) {
				try {
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 1);
					fcstWeather.parseXml(xmlBuf);
					fcstWeather.print();
				} catch (IOException e) {
					System.out.println("현재 기상을 조회하는데 실패하였습니다.");
					printEnter(scan);
					continue;
				}
			}
			else if (ibuf == 2) {
				try {
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 2);
					fcstWeather.parseXml(xmlBuf);
					fcstWeather.print();
				} catch (IOException e) {
					System.out.println("현재 기상을 조회하는데 실패하였습니다.");
					printEnter(scan);
					continue;
				}
			}
			else if (ibuf == 3) {
								
				if (diameter == null) System.out.println("에러");
				System.out.println("x 좌표 : " + diameter[0] + " y 좌표 : " + diameter[1]);
				
			}
			printEnter(scan);
		}
	}

	public static void printEnter(Scanner scan) {
		System.out.println("Enter를 입력하면 처음으로 돌아갑니다.");
		scan.nextLine();
	}
}
