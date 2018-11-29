package WeatherInfo;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WeatherForecastData extends WeatherCurrentData {

	// 동네예보
	String S06; // 6시간 신적설 (범주 1mm)
	String SKY; // 하늘 상태 (코드값)
	String TMN; // 아침 최저기온(C)
	String TMX; // 낮 최고기온(C)
	String WAV; // 파고
	String POP; // 강수 확률 (%)

	void print() {
		System.out.println("Temp = " + Temp);
		System.out.println("Rain = " + Rain);
		System.out.println("UUU = " + UUU);
		System.out.println("VVV = " + VVV);
		System.out.println("REH = " + REH);
		System.out.println("PTY = " + PTY);
		System.out.println("VEC = " + VEC);
		System.out.println("WSD = " + WSD);
		System.out.println("S06 = " + S06);
		System.out.println("SKY = " + SKY);
		System.out.println("TMN = " + TMN);
		System.out.println("WAV = " + WAV);
		System.out.println("POP = " + POP);
	}

	int parseXml(String xmlString) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xmlString)));

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("item");
		
			for (int temp = 0; temp < nList.getLength(); temp++) {
				System.out.println(temp + " 번째 데이터");
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					switch (getTagValue("category", eElement)) {
					case "T1H":
					case "T3H":
						this.Temp = getTagValue("fcstValue", eElement);
						break;
					case "RN1":
					case "R06":
						this.Rain = getTagValue("fcstValue", eElement);
						break;
					case "UUU":
						this.UUU = getTagValue("fcstValue", eElement);
						break;
					case "VVV":
						this.VVV = getTagValue("fcstValue", eElement);
						break;
					case "REH":
						this.REH = getTagValue("fcstValue", eElement);
						break;
					case "PTY":
						this.PTY = getTagValue("fcstValue", eElement);
						break;
					case "VEC":
						this.VEC = getTagValue("fcstValue", eElement);
						break;
					case "WSD":
						this.WSD = getTagValue("fcstValue", eElement);
						break;
					case "S06":
						this.S06 = getTagValue("fcstValue", eElement);
						break;
					case "SKY":
						this.SKY = getTagValue("fcstValue", eElement);
						break;
					case "TMN":
						this.TMN = getTagValue("fcstValue", eElement);
						break;
					case "TMX":
						this.TMX = getTagValue("fcstValue", eElement);
						break;
					case "WAV":
						this.WAV = getTagValue("fcstValue", eElement);
						break;
					case "POP":
						this.POP = getTagValue("fcstValue", eElement);
						break;

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
