package kr.bora.api;

import kr.bora.api.domain.Authority;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class JsonParseTest {

    public static void main(String[] args) throws MalformedURLException, IOException, ParseException {
        String url = "http://localhost:8080/auth/login";
        String result = "";


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST"); // 보내는 타입
        con.setRequestProperty("content-type", "application/json");

        // 데이터
        String param = "{\"email\": \"djd4532\", \"password\" : \"9452\"}";

        // 전송
        OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());

        try {
            osw.write(param);
            osw.flush();

            // 응답
            BufferedReader br = null;

            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            // 닫기
            osw.close();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
