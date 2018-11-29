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
			System.out.println("���ϴ� ������ ������ �Է��� �ּ���.");
			System.out.println("0 : ���� ��� ��ȸ");
			System.out.println("1 : �� �ܱ� ��翹�� ��ȸ");
			System.out.println("2 : ���� ���� ��ȸ");
			System.out.println("3 : test");
			System.out.print("�Է°� : ");
			sbuf = scan.nextLine();

			try {
				ibuf = Integer.parseInt(sbuf);
				if (ibuf < 0 || ibuf > 3) {
					System.out.println("\n�߸��� �ɼǰ� �Դϴ�. �ùٸ� ���� �Է��� �ֽʽÿ�");
					printEnter(scan);
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("\n�߸��� �ɼǰ� �Դϴ�. �ùٸ� ���� �Է��� �ֽʽÿ�");
				printEnter(scan);
				continue;
			}

			System.out.print("�ּҸ� �Է��ϼ��� ex) ����Ư����  : ");
			addr = scan.nextLine();
			diameter = diameterInfo.checkDiameter(addr);

			if (diameter == null) {
				System.out.println("�߸��� �ּҰ��Դϴ�.");
			}
			
			if (ibuf == 0) {
				try {
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 0);
					nowWeather.parseXml(xmlBuf);
					nowWeather.print();
				} catch (IOException e) {
					System.out.println("���� ����� ��ȸ�ϴµ� �����Ͽ����ϴ�.");
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
					System.out.println("���� ����� ��ȸ�ϴµ� �����Ͽ����ϴ�.");
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
					System.out.println("���� ����� ��ȸ�ϴµ� �����Ͽ����ϴ�.");
					printEnter(scan);
					continue;
				}
			}
			else if (ibuf == 3) {
								
				if (diameter == null) System.out.println("����");
				System.out.println("x ��ǥ : " + diameter[0] + " y ��ǥ : " + diameter[1]);
				
			}
			printEnter(scan);
		}
	}

	public static void printEnter(Scanner scan) {
		System.out.println("Enter�� �Է��ϸ� ó������ ���ư��ϴ�.");
		scan.nextLine();
	}
}
