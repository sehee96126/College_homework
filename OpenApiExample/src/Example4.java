import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

//import java.awt.peer.SystemTrayPeer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;

public class Example4 {
	public static void main(String[] args) throws IOException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll"); /*
																																										 * URL
																																										 */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1SfhBEI9zEgtpXNhg5iahzHIAWTGGiFdB%2BVmoXfvzLYLVy8%2BPgv5Hl%2F6yeUU%2FV1HYvOfwChMGTtSI84iAO8yeg%3D%3D"); /*
																															 * Service
																															 * Key
																															 */
		urlBuilder.append(
				"&" + URLEncoder.encode("srchwrd", "UTF-8") + "=" + URLEncoder.encode("송도동", "UTF-8")); /* 검색어 */
		urlBuilder.append("&" + URLEncoder.encode("countPerPage", "UTF-8") + "="
				+ URLEncoder.encode("10", "UTF-8")); /* 페이지당 출력될 개수를 지정(최대50) */
		urlBuilder.append("&" + URLEncoder.encode("currentPage", "UTF-8") + "="
				+ URLEncoder.encode("1", "UTF-8")); /* 출력될 페이지 번호 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		System.out.println(sb.toString());

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(sb.toString())));

			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("newAddressListAreaCdSearchAll");
			System.out.println("-----------------------");
			System.out.println("Node Length: " + nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++) {
				System.out.println(temp + " 번째 데이터");
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					System.out.println("ZIP Code: " + getTagValue("zipNo", eElement));
					System.out.println("lnmAdres: " + getTagValue("lnmAdres", eElement));
					System.out.println("rnAdres: " + getTagValue("rnAdres", eElement));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}
}
