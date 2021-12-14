package com.office.service;

import com.office.entity.SlackEntity;
import com.office.exception.SlackException;
import com.mindscapehq.raygun4java.core.RaygunClient;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.users.UsersLookupByEmailResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SlackService {
    @Value("raygun.api.key")
    private String raygunKey;

    private Logger logger = LoggerFactory.getLogger("logger_name");
    private RaygunClient raygunClient = new RaygunClient(raygunKey);
    private MethodsClient client = Slack.getInstance().methods();

    public ChatPostMessageResponse postMessage(SlackEntity input, String applicationCode) throws Exception {
        ChatPostMessageResponse result = null;
        try {
            String oauthToken = "oauthToken"
            UsersLookupByEmailResponse user = this.getUserByEmail(oauthToken, input.getEmail(), applicationCode);
            result = sendMessage(oauthToken, user.getUser().getId(), input.getMessage());
        } catch(Exception e) {
            raygunClient.send(new Exception("Error occurred while sending message - " + e.getMessage()));
            throw new SlackException("Error occurred while sending message - " + e.getMessage());
        }
        return result;
    }

    public ChatPostMessageResponse sendMessage(String oauthToken, String id, String message) throws Exception {
        ChatPostMessageResponse result = client.chatPostMessage(r -> r
                .token(oauthToken)
                .channel(id)
                .text(message)
        );
        if(!result.isOk()) {
            throw new SlackException("Error occurred while sending message");
        }
        logger.info("Send message - result {}", result);
        return result;
    }

    public UsersLookupByEmailResponse getUserByEmail(String oauthToken, String email, String applicationCode) throws Exception {
        UsersLookupByEmailResponse user = client.usersLookupByEmail(r -> r
                .email(email)
                .token(oauthToken));
        if(!user.isOk()) {
            raygunClient.send(new Exception("User not found"));
            throw new SlackException("User not found");
        }
        return user;
    }
}