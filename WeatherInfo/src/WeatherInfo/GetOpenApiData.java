package WeatherInfo;

import java.io.BufferedReader;
import java.io.IOException;

/* Java 샘플 코드 */
/* 초단기 실황 조회 */

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetOpenApiData {
	static final String weatherKey = "=R0wjLVu6A6uqBAgcRyvO%2F79tWTISM%2FoRd8dNHL21DdYR5fjg386BONfz6XioYiHjX5IlrCQ38QoSTj7%2BgvqnSQ%3D%3D";
	
	/* 0 : 초단기 실황 조회
	 * 1 : 동네 예보 조회 */
	static final String weatherUrlString[] = {"http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib",
			"http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastTimeData",
			"http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData"};
	
	String date;
	int time[] = new int[3];


	public void checkDate() {
		int time_buf;
		LocalDateTime curDateTime = LocalDateTime.now();

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

		date = curDateTime.format(dateFormatter);
		time_buf = Integer.parseInt(curDateTime.format(timeFormatter));
		
		time[0] = ((time_buf-40)/100) * 100;
		time[1] = ((time_buf-45)/100) * 100 + 30;
		time[2] = (time_buf/800) * 300 - 100;
		if(time[2]<=0) time[2] = 2300;
	}
	
	void updateValue(String nx, String ny) throws IOException {
		this.checkDate();
		this.getWeatherData(nx, ny, 0);
	}

	String getWeatherData(String nx, String ny, int type) throws IOException {
		this.checkDate();
		StringBuilder urlBuilder = new StringBuilder(weatherUrlString[type]); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + weatherKey); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "="
				+ URLEncoder.encode(date, "UTF-8")); /* ‘15년 12월 1일 발표 */
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "="
				+ URLEncoder.encode(String.format("%04d", time[type]), "UTF-8")); /* 06시 발표(정시단위) -매시각 40분 이후 호출 */
		urlBuilder.append(
				"&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /* 예보지점의 X 좌표값 */
		urlBuilder.append(
				"&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /* 예보지점의 Y 좌표값 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("100", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append(
				"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지 번호 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "="
				+ URLEncoder.encode("xml", "UTF-8")); /* xml(기본값), json */
		URL url = new URL(urlBuilder.toString());
		//System.out.println(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		//System.out.println("Response code: " + conn.getResponseCode());
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
		
		//System.out.println(sb.toString());	
		return sb.toString();
	}
	
}
