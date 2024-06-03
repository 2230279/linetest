package com.example.linebot;

import com.linecorp.bot.client.LineMessagingClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class LineBotConfig {

    @Value("${line.bot.channel-token}")
    private String channelToken;

    @Bean
    public LineMessagingClient lineMessagingClient() {
        return LineMessagingClient.builder(channelToken).build();
    }
}
