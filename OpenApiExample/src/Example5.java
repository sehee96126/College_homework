
/* Java 샘플 코드 */
/* 환율 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;

public class Example5 {
	public static void main(String[] args) throws IOException {
		String jsonString;

		System.out.println("환율");
		StringBuilder urlBuilder = new StringBuilder(
				"https://www.koreaexim.go.kr/site/program/financial/exchangeJSON"); /* URL */
		urlBuilder.append(
				"?" + URLEncoder.encode("authkey", "UTF-8") + "=8tI1TTzZX7cKhFWe92N78m3udPAj4MIi"); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("searchdate", "UTF-8") + "="
				+ URLEncoder.encode("20181122", "UTF-8")); /* ‘15년 12월 1일 발표 */
		urlBuilder.append("&" + URLEncoder.encode("data", "UTF-8") + "="
				+ URLEncoder.encode("AP01", "UTF-8")); /* 06시 발표(정시단위) -매시각 40분 이후 호출 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
		System.out.println("Json parsing");
		
		JSONParser parser = new JSONParser();
		JSONArray jsonObject = new JSONArray();
		try {
			jsonObject = (JSONArray) parser.parse(sb.toString());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			for (int i = 0; i < jsonObject.size(); i++) {
				System.out.println("========================================================" + i + "번째");
				JSONObject tmp = (JSONObject)jsonObject.get(i);
				System.out.println("result : " + tmp.get("result"));
				System.out.println("cur_unit : " + tmp.get("cur_unit"));
				System.out.println("ttb : " + tmp.get("ttb"));
				System.out.println("tts : " + tmp.get("tts"));
				System.out.println("deal_bas_r : " + tmp.get("deal_bas_r"));
				System.out.println("bkpr : " + tmp.get("bkpr"));
				System.out.println("yy_efee_r : " + tmp.get("yy_efee_r"));
				System.out.println("cur_nm : " + tmp.get("cur_nm").toString());
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("----------------------------------------------------------");

	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

}
