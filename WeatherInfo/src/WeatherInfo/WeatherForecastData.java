package WeatherInfo;

import org.w3c.dom.Element;

class WeatherForecastData extends WeatherCurrentData {

	// ���׿���
	private String SKY = "-"; // �ϴ� ���� (�ڵ尪)
	private String TMN = "-"; // ��ħ �������(C)
	private String TMX = "-"; // �� �ְ���(C)
	private String POP = "-"; // ���� Ȯ�� (%)

	void print() {
		if (firstTime == 1)
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------");
		System.out.println("\t" + fcstDate + "\t" + fcstTime + "\t" + SKY + "\t" + Temp + "\t" + REH + "\t" + Rain
				+ "\t\t" + TMN + "\t\t" + TMX + "\t\t" + POP);

	}

	void parseCategoryData(Element eElement, int fake) {
		switch (GetOpenApiData.getTagValue("category", eElement)) {
		case "T1H":
		case "T3H":
			this.Temp = GetOpenApiData.getTagValue("fcstValue", eElement);
			break;
		case "RN1":
		case "R06":
			this.Rain = GetOpenApiData.getTagValue("fcstValue", eElement);
			break;
		case "REH":
			this.REH = GetOpenApiData.getTagValue("fcstValue", eElement);
			break;
		case "SKY":
			String buf;
			buf = GetOpenApiData.getTagValue("fcstValue", eElement);
			if(buf.equals("1"))
				this.SKY = "����";
			else if(buf.equals("2"))
				this.SKY = "���� ����";
			else if(buf.equals("3"))
				this.SKY = "���� ����";
			else if(buf.equals("4"))
				this.SKY = "�帲";
			else
				this.SKY = "-";
			break;
		case "TMN":
			this.TMN = GetOpenApiData.getTagValue("fcstValue", eElement);
			break;
		case "TMX":
			this.TMX = GetOpenApiData.getTagValue("fcstValue", eElement);
			break;
		case "POP":
			this.POP = GetOpenApiData.getTagValue("fcstValue", eElement);
			break;
		default:
			break;
		}

	}

}
