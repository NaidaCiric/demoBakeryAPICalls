import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class demoAPICallsMain {
       public static void main(String[] args) throws JSONException, IOException {
           JSONArray jArr = GETRequest.readJsonFromUrl("http://localhost:3000/processes");
           System.out.println(jArr);

           final String json_data = "{\n" + "\"processes\": \"Test Title\"}";
           URL url = new URL("http://localhost:3000/processes");
           POSTRequest(url, json_data);
}

    public static void POSTRequest(URL url, String json_data) throws IOException {
        System.out.println(json_data);
        HttpURLConnection postConnection = (HttpURLConnection) url.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");

        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(json_data.getBytes());
        os.flush();
        os.close();

        int responseCode = postConnection.getResponseCode();
        System.out.println("POST Response Code :  " + responseCode);
        System.out.println("POST Response Message : " + postConnection.getResponseMessage());

        if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST DIDN'T WORK");
        }
    }
}


