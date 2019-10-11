package cn.hasfun.framework.java11;

import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Testjava11 {

    @Test
    public void test() throws Exception {

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
               // .proxy(ProxySelector.of(new InetSocketAddress("www.baidu.com", 80)))
                //.authenticator(Authenticator.getDefault())
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "text/html")
                //.POST(HttpRequest.BodyPublishers.ofString("", StandardCharsets.UTF_8))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());


        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);

        System.in.read();
    }
}
