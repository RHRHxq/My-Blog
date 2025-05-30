package com.myblog.config;

import com.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTaskConfig {
    @Autowired
    private ArticleService articleService;

    // 每天凌晨2点执行清理任务
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredRecycleBinArticles() {
        articleService.cleanExpiredRecycleBinArticles();
    }
}
