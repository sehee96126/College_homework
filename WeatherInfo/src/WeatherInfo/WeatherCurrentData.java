package WeatherInfo;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

class WeatherCurrentData {
	// 초단기 실황
	protected String Temp = "-"; // 1시간 기온
	protected String Rain = "-"; // 1시간 강수량
	protected String REH = "-"; // 습도
	protected String fcstDate;
	protected String fcstTime;
	protected int firstTime;

	void copyClass(WeatherCurrentData classData) {
		Temp = classData.Temp;
		Rain = classData.Rain;
		REH = classData.REH;
		fcstDate = classData.fcstDate;
		fcstTime = classData.fcstTime;
	}

	void printNow() {
		System.out.println("\t현재\t -\t" + Temp + "\t" + REH + "\t" + Rain);
	}

	void print() {
		if (firstTime == 1)
			System.out.println("--------------------------------------------------------------");
		//if (Temp.equals("-") == false || Rain.equals("-") == false || REH.equals("-") == false)
			System.out.println("\t" + fcstDate + "\t" + fcstTime + "\t-\t" + Temp + "\t" + REH + "\t" + Rain);
	}

	void parseCategoryData(Element eElement, int type) {
		String key = null;
		if (type == 0)
			key = "obsrValue";
		else
			key = "fcstValue";

		switch (GetOpenApiData.getTagValue("category", eElement)) {
		case "T1H":
		case "T3H":
			this.Temp = GetOpenApiData.getTagValue(key, eElement);
			break;
		case "RN1":
		case "R06":
			this.Rain = GetOpenApiData.getTagValue(key, eElement);
			break;
		case "REH":
			this.REH = GetOpenApiData.getTagValue(key, eElement);
			if (this.REH == null)
				this.REH = "-";
			break;
		default:
			break;
		}
	}

	int parseXml(String xmlString) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xmlString)));

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					parseCategoryData(eElement, 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

}
