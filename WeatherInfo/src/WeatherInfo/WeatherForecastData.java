package WeatherInfo;

import org.w3c.dom.Element;

class WeatherForecastData extends WeatherCurrentData {

	// 동네예보
	private String SKY = "-"; // 하늘 상태 (코드값)
	private String TMN = "-"; // 아침 최저기온(C)
	private String TMX = "-"; // 낮 최고기온(C)
	private String POP = "-"; // 강수 확률 (%)

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
				this.SKY = "맑음";
			else if(buf.equals("2"))
				this.SKY = "구름 조금";
			else if(buf.equals("3"))
				this.SKY = "구름 많음";
			else if(buf.equals("4"))
				this.SKY = "흐림";
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
