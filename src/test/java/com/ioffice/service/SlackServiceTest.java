package com.office.service;

import com.office.entity.SlackEntity;
import com.office.exception.SlackException;
import com.slack.api.RequestConfigurator;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.users.UsersLookupByEmailRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.users.UsersLookupByEmailResponse;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SlackServiceTest {

    SlackService slackService = new SlackService();

    @Test
    public void testGetUserByEmail() throws Exception {
        MethodsClient client = Mockito.mock(MethodsClient.class);
        ReflectionTestUtils.setField(slackService, "client", client);
        UsersLookupByEmailResponse user = new UsersLookupByEmailResponse();
        user.setOk(true);
        Mockito.doReturn(user).when(client).usersLookupByEmail((
                RequestConfigurator<UsersLookupByEmailRequest.UsersLookupByEmailRequestBuilder>) Mockito.any());
        slackService.getUserByEmail("token", "email", "appCode");
        Assert.assertTrue(user.isOk());
    }

    @Test
    public void testSendMessage() throws Exception {
        MethodsClient client = Mockito.mock(MethodsClient.class);
        ReflectionTestUtils.setField(slackService, "client", client);
        ChatPostMessageResponse result = new ChatPostMessageResponse();
        result.setOk(true);
        Mockito.doReturn(result).when(client).chatPostMessage((
                RequestConfigurator<ChatPostMessageRequest.ChatPostMessageRequestBuilder>) Mockito.any());
        slackService.sendMessage("oauthToken", "id", "message");
        Assert.assertTrue(result.isOk());
    }
}