package com.admin.tbot;

import com.admin.tbot.services.botApi.BotApiService;
import com.admin.tbot.configuration.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class TbotApplication {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        final BotApiService bot = context.getBean(BotApiService.class);
        context.close();
        if (bot.registerWebHook()) {
            SpringApplication.run(TbotApplication.class, args);
        } else {
            System.out.println("Failed to register WebHook address");
        }
    }

}