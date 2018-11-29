/* Java ���� �ڵ� */
/* ���� �͹̼� �ü� ���� ��Ȳ*/

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class Example6 {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.airport.kr/openapi/service/FacilitiesInformation/getFacilitesInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=UxejGKv0FYHAIMo5IYPDLxhvRtxKALahS%2FGnyXKjWh%2FPhfbii%2BzvpRE3npQ0uC0k3LOVo%2BMemMmRkt0Sow2FQw%3D%3D"); /*Service Key*/
       // urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=UxejGKv0FYHAIMo5IYPDLxhvRtxKALahS%2FGnyXKjWh%2FPhfbii%2BzvpRE3npQ0uC0k3LOVo%2BMemMmRkt0Sow2FQw%3D%3D" + URLEncoder.encode("-", "UTF-8")); /*�������������п��� ���� ����Ű*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*��������ȣ*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*�� ������ ��� ��*/
        urlBuilder.append("&" + URLEncoder.encode("lang","UTF-8") + "=" + URLEncoder.encode("K", "UTF-8")); /*K: ����,E: ����,J: �Ϲ�,C: �߹�*/
        urlBuilder.append("&" + URLEncoder.encode("lcduty","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*Y:�鼼����,N:�Ϲ�����*/
        urlBuilder.append("&" + URLEncoder.encode("facilitynm","UTF-8") + "=" + URLEncoder.encode("[�Ե��鼼��] ������", "UTF-8")); /*�ü��� �� �����*/
        URL url = new URL(urlBuilder.toString());
        System.out.println(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/xml");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
		}
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}