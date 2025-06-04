package com.myblog.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
    private RestHighLevelClient client;
    @Bean(destroyMethod = "close")
    public RestHighLevelClient elasticsearchClient() {
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.209.130", 9200, "http")) // 协议保留"http"
        );
        return client;
    }
}
