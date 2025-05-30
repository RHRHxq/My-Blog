package com.myblog.config;

import com.myblog.mapper.ArticleMapper;
import com.myblog.service.ArticleService;
import com.myblog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Autowired
    private ArticleService  articleService;
    @Autowired
    private CommentService  commentService;


    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void syncArticleDataToDatabase() {
        // 同步文章访问量
        articleService.updateArticleViews();


        // 同步文章点赞量
        articleService.updateArticleLikes();

        commentService.updateCommentLikes();
    }
}
