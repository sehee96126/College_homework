package WeatherInfo;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

class WeatherForecast {
	private WeatherCurrentData[] weatherArry;
	private int Type;

	/*
	 * type 0 : 동네 예보 type 1 : 단기 예보
	 */
	WeatherForecast(int size, int type) {
		int si = 0;

		if (type == 0) {
			si = 8 * 3;
			Type = 0;
		} else {
			si = 4;
			Type = 1;
		}
		if (si != 0)
			weatherArry = new WeatherCurrentData[si];
	}

	void print() {
		for (int i = 0; i < weatherArry.length && weatherArry[i] != null; i++) {
			weatherArry[i].print();
		}
	}

	int parseXml(String xmlString) throws NullPointerException {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xmlString)));

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("item");

			int i = 0, temp = 0;

			for (i = 0; i < weatherArry.length && temp < nList.getLength(); i++) {

				if (Type == 1)
					weatherArry[i] = new WeatherCurrentData();
				else if (Type == 0)
					weatherArry[i] = new WeatherForecastData();
				else {
					System.out.println("Type err");
					break;
				}

				Node iNode = nList.item(temp);
				if (iNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) iNode;

					weatherArry[i].fcstDate = GetOpenApiData.getTagValue("fcstDate", eElement);
					weatherArry[i].fcstTime = GetOpenApiData.getTagValue("fcstTime", eElement);
					if (weatherArry[i].fcstTime.equals("0000"))
						weatherArry[i].firstTime = 1;

				}
				for (; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					int ibuf = i;
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						String timeStr = GetOpenApiData.getTagValue("fcstTime", eElement);
						String dateStr = GetOpenApiData.getTagValue("fcstDate", eElement);

						if (weatherArry[i].fcstTime.equals(timeStr) == false
								|| weatherArry[i].fcstDate.equals(dateStr) == false) {
							int breakSign = 0;
							
							for (int j = 0 ; j < i; j++) {
								if(weatherArry[j] == null) {
									break;
								}
								if(weatherArry[j].fcstDate.equals(dateStr) && weatherArry[j].fcstTime.equals(timeStr)) {
									breakSign = 1;
									ibuf = j;
									break;
								}
							}
							if(breakSign == 0)
								break;
						}
						weatherArry[ibuf].parseCategoryData(eElement, 1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
}
