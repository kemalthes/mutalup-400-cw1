import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class HttpClientImpl implements HttpClient {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        try {
            URL curUrl = new URI(buildURL(url, params)).toURL();
            HttpURLConnection connection = (HttpURLConnection) curUrl.openConnection();
            connection.setRequestMethod("GET");
            setHeaders(headers, connection);
            return getResponse(connection);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL curUrl = new URI(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) curUrl.openConnection();
            connection.setRequestMethod("POST");
            setHeaders(headers, connection);
            writeData(data, connection);
            return getResponse(connection);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String put(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL curUrl = new URI(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) curUrl.openConnection();
            connection.setRequestMethod("PUT");
            setHeaders(headers, connection);
            writeData(data, connection);
            return getResponse(connection);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String delete(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL curUrl = new URI(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) curUrl.openConnection();
            connection.setRequestMethod("DELETE");
            setHeaders(headers, connection);
            writeData(data, connection);
            return getResponse(connection);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildURL(String url, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder(url);
        if (!params.isEmpty()) {
            urlBuilder.append("?");
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (urlBuilder.lastIndexOf("&") == urlBuilder.length() - 1) {
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        return urlBuilder.toString();
    }

    private void writeData(Map<String, String> data, HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        String jsonBody = mapper.writeValueAsString(data);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonBody.getBytes());
            os.flush();
        }
    }

    private void setHeaders(Map<String, String> headers, HttpURLConnection connection) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        ObjectNode response = mapper.createObjectNode();
        StringBuilder responseBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String input;
            while ((input = reader.readLine()) != null) {
                responseBody.append(input);
            }
        }
        Integer statusCode = connection.getResponseCode();
        String statusMessage = connection.getResponseMessage();
        response.put("statusCode", statusCode);
        response.put("statusMessage", statusMessage);
        response.put("body", responseBody.toString());
        return response.toString();
    }
}