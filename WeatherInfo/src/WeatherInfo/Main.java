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
		System.out.println("�� ������� ���� ���̺귯���� ���� �ڹ� ������ ���� �ʾ� �߻��Ǵ� ������, ���װ� �ƴմϴ�.");
		System.out.println("(�ش� ���̺귯���� ������ �� ����.)");
		while (true) {

			System.out.println("==============================================================");
			System.out.println("----- < ���� ��� ? > -----");
			System.out.println("0 : ������ ���� ����");
			System.out.println("1 : ������ ���̵� ����");
			System.out.println("2 : ������ ����/�� ����");
			System.out.println("������� ������ ��ȣ�� �Է����ּ���");
			System.out.print("--> ");
			sbuf = scan.nextLine().replaceAll(" ", "");
			
			if(sbuf.length()> 1) {
				System.out.println("\n�߸��� �ɼǰ� �Դϴ�. �ùٸ� ���� �Է��� �ֽʽÿ�");
				printEnter(scan);
				continue;
			}

			try {
				ibuf = Integer.parseInt(sbuf);
				if (ibuf < 0 || ibuf > 2) {
					System.out.println("\n�߸��� �ɼǰ� �Դϴ�. �ùٸ� ���� �Է��� �ֽʽÿ�");
					printEnter(scan);
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("\n�߸��� �ɼǰ� �Դϴ�. �ùٸ� ���� �Է��� �ֽʽÿ�");
				printEnter(scan);
				continue;
			}

			System.out.println("�ּҸ� �Է��ϼ���. ex) ����� ���α�, ��⵵ ��õ��");
			System.out.print("--> ");
			addr = scan.nextLine();
			diameter = diameterInfo.checkDiameter(addr);

			if (diameter == null || diameter[0].equals("0") || diameter[1].equals("0")) {
				System.out.println("�߸��� �ּҰ��Դϴ�.");
				printEnter(scan);
				continue;
			}
			
			if (ibuf == 0) {
				try {
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 0);
					nowWeather.parseXml(xmlBuf);
					
					System.out.println("<���� ��� ��ȸ>");
					System.out.println(
							"=============================================================================================");
					System.out.println("\t�ð�\t���(��)\t����(%)\t������(mm)");
					System.out.println(
							"=============================================================================================");
					
					nowWeather.printNow();
				} catch (IOException e) {
					System.out.println("���� ����� ��ȸ�ϴµ� �����Ͽ����ϴ�.");
					printEnter(scan);
					continue;
				}
			}
			else if (ibuf == 1) {
				try {
					WeatherForecast fcstWeather = new WeatherForecast(dateNum, 1);
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 1);
					fcstWeather.parseXml(xmlBuf);
					System.out.println("<�ܱ� ���� ��ȸ>");
					System.out.println(
							"=============================================================================================");
					System.out.println("\t��¥\t\t�ð�\t���(��)\t����(%)\t������(mm)");
					System.out.println(
							"=============================================================================================");
					fcstWeather.print();
				} catch (IOException e) {
					System.out.println("���� ����� ��ȸ�ϴµ� �����Ͽ����ϴ�.");
					printEnter(scan);
					continue;
				}
			}
			else if (ibuf == 2) {
				try {
					WeatherForecast fcstWeather = new WeatherForecast(dateNum, 0);
					xmlBuf = api.getWeatherData(diameter[0], diameter[1], 2);
					fcstWeather.parseXml(xmlBuf);
					System.out.println("<���� ���� ��ȸ>");
					System.out.println(
							"======================================================================================================================");
					System.out.println("\t��¥\t\t�ð�\t����\t���(��)\t����(%)\t������(mm)\t��ħ �������(��)\t�� �ְ���(��)\t����Ȯ��(%)");
					System.out.println(
							"======================================================================================================================");
					fcstWeather.print();
				} catch (IOException e) {
					System.out.println("���� ����� ��ȸ�ϴµ� �����Ͽ����ϴ�.");
					printEnter(scan);
					continue;
				}
			}
			printEnter(scan);
		}
	}

	private static void printEnter(Scanner scan) {
		System.out.println("Enter�� �Է��ϸ� ó������ ���ư��ϴ�.");
		scan.nextLine();
	}
}
