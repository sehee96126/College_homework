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
	// �ʴܱ� ��Ȳ
	protected String Temp; // 1�ð� ���
	protected String Rain; // 1�ð� ������
	protected String UUU; // ǰ�� (��������)
	protected String VVV; // ǳ�� (���ϼ���)
	protected String REH; // ����
	protected String PTY; // ���� ���� (��Ʈ��)
	protected String VEC; // ǳ��
	protected String WSD; // ǳ��

	void print() {
		System.out.println("Temp = " + Temp);
		System.out.println("Rain = " + Rain);
		System.out.println("UUU = " + UUU);
		System.out.println("VVV = " + VVV);
		System.out.println("REH = " + REH);
		System.out.println("PTY = " + PTY);
		System.out.println("VEC = " + VEC);
		System.out.println("WSD = " + WSD);
	}

	int parseXml(String xmlString) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xmlString)));

			doc.getDocumentElement().normalize();
			//System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("item");
			//System.out.println("-----------------------");
			//System.out.println("Node Length: " + nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++) {
			//	System.out.println(temp + " ��° ������");
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

			/*		System.out.println("baseDate: " + getTagValue("baseDate", eElement));
					System.out.println("baseTime: " + getTagValue("baseTime", eElement));
					System.out.println("category: " + getTagValue("category", eElement));
					System.out.println("nx: " + getTagValue("nx", eElement));
					System.out.println("ny: " + getTagValue("ny", eElement));
					System.out.println("obsrValue: " + getTagValue("obsrValue", eElement));
					// System.out.println("obsrValue: " + getTagValue("obsrValue", eElement));
			*/		switch (getTagValue("category", eElement)) {
					case "T1H":
					case "T3H":
						this.Temp = getTagValue("obsrValue", eElement);
						break;
					case "RN1":
					case "R06":
						this.Rain = getTagValue("obsrValue", eElement);
						break;
					case "UUU":
						this.UUU = getTagValue("obsrValue", eElement);
						break;
					case "VVV":
						this.VVV = getTagValue("obsrValue", eElement);
						break;
					case "REH":
						this.REH = getTagValue("obsrValue", eElement);
						break;
					case "PTY":
						this.PTY = getTagValue("obsrValue", eElement);
						break;
					case "VEC":
						this.VEC = getTagValue("obsrValue", eElement);
						break;
					case "WSD":
						this.WSD = getTagValue("obsrValue", eElement);
						break;
					/*
					 * case "S06": break; case "SKY": // �ϴ� ���� (�ڵ尪) case "TMN": // ��ħ �������(C) case
					 * "TMX": // �� �ְ���(C) case "WAV": // �İ� case "POP": // ���� Ȯ�� (%) break;
					 */
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	protected static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}
}
