package com.itmy.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class RestConfig {

    @Bean
    public CloseableHttpClient httpClient() throws Exception {
        SSLContext context = new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        return HttpClients.custom()
                .setSSLSocketFactory(factory)
                .build();
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() throws Exception {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient());
        factory.setConnectionRequestTimeout(3000);
        factory.setReadTimeout(30000);
        factory.setConnectTimeout(30000);
        return factory;
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {
        return new RestTemplate(httpRequestFactory());
    }
}
