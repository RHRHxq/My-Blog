package com.myblog.listener;

import com.myblog.entity.ReviewNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReviewNotificationListener {
//    @RabbitListener(queues = "#{'rejectionNotification.user.' + T(java.lang.String).valueOf(#userId)}")
//    public void receiveNotification(ReviewNotification notification) {
//        log.info("收到审核拒绝通知：文章ID={}, 用户ID={}", notification.getArticleId(), notification.getUserId());
//        // 处理通知，例如发送邮件或短信给作者
//    }
}
