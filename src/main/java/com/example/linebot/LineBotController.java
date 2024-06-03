package com.example.linebot;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class LineBotController {

    private final LineMessagingClient lineMessagingClient;

    public LineBotController(LineMessagingClient lineMessagingClient) {
        this.lineMessagingClient = lineMessagingClient;
    }

    @Value("${line.bot.channel-token}")
    private String channelToken;

    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody FollowEvent followEvent) {
        String replyToken = followEvent.getReplyToken();

        // 發送歡迎消息
        TextMessage welcomeMessage = new TextMessage("歡迎您添加我們的LINE官方帳號！");
        ReplyMessage reply = new ReplyMessage(replyToken, welcomeMessage);

        try {
            lineMessagingClient.replyMessage(reply).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error sending welcome message", e);
        }
    }

    @PostMapping("/webhook")
    public void handleMessageEvent(@RequestBody MessageEvent<TextMessageContent> event) {
        String replyToken = event.getReplyToken();
        String userMessage = event.getMessage().getText();
        TextMessage replyMessage;

        // 自動回覆邏輯
        if (userMessage.contains("訂單狀態")) {
            replyMessage = new TextMessage("您的訂單正在處理中，請稍候查詢。");
        } else if (userMessage.contains("營業時間")) {
            replyMessage = new TextMessage("我們的營業時間是週一至週五，早上9點到下午6點。");
        } else {
            replyMessage = new TextMessage("感謝您的詢問，我們將盡快回覆您。");
        }

        ReplyMessage reply = new ReplyMessage(replyToken, replyMessage);

        try {
            lineMessagingClient.replyMessage(reply).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error sending reply message", e);
        }
    }
}
