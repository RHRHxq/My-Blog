//package com.myblog.config;
//
//import com.myblog.controller.JwtTokenController;
//import org.springframework.amqp.core.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//
//
//    @Bean
//    public DirectExchange rejectionNotificationExchange() {
//        return new DirectExchange("rejectionNotification.exchange"); // 交换机名与配置文件一致
//    }
//
//    @Bean
//    public Queue rejectionNotificationQueue() {
//        return new Queue("rejectionNotification.queue"); // 队列名与配置文件一致
//    }
//
//    @Bean
//    public Binding rejectionNotificationBinding(Queue rejectionNotificationQueue, DirectExchange rejectionNotificationExchange) {
//        return BindingBuilder.bind(rejectionNotificationQueue).to(rejectionNotificationExchange).with("rejectionNotification.routing.key"); // 绑定交换机与队列，并指定路由键
//    }
//
//    // 为每个用户创建一个队列，使用用户ID作为队列名
//    public Queue userRejectionQueue(Long userId) {
//        return new Queue("rejectionNotification.user." + userId);
//    }
//
//    // 为每个用户的队列创建绑定
//    public Binding userRejectionBinding(Queue userQueue, DirectExchange rejectionNotificationExchange, Long userId) {
//        return BindingBuilder.bind(userQueue).to(rejectionNotificationExchange).with("rejectionNotification.user." + userId);
//    }
//}
