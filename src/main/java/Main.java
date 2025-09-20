import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        HttpClientImpl httpClient = new HttpClientImpl();
        testGet(httpClient);
        testPost(httpClient);
        testPut(httpClient);
        testDelete(httpClient);
    }

    private static void testGet(HttpClient httpClient) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Map<String, String> request = new HashMap<>();
        System.out.println(httpClient.get("http://localhost:8080/hello", headers, request));
    }

    private static void testPost(HttpClient httpClient) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Map<String, String> body = new HashMap<>();
        body.put("name", "kokichiOma");
        body.put("email", "imlaiaawr@ronpa.com");
        body.put("gender", "male");
        body.put("status", "active");
        System.out.println(httpClient.post("http://localhost:8080/hello", headers, body));
    }

    private static void testPut(HttpClient httpClient) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Map<String, String> body = new HashMap<>();
        body.put("name", "kokichiOma");
        body.put("email", "imlaiawre@ronpa.com");
        body.put("gender", "male");
        body.put("status", "inactive");
        System.out.println(httpClient.put("http://localhost:8080/hello", headers, body));
    }

    private static void testDelete(HttpClient httpClient) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        System.out.println(httpClient.delete("http://localhost:8080/hello", headers, new HashMap<>()));
    }
}
