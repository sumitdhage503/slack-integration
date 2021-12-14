package com.office.controller;

import com.office.entity.SlackEntity;
import com.office.service.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController("SlackController")
@RequestMapping("/v1")
public class SlackController {
    @Autowired
    private SlackService slackService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping(value = "/send-message")
    public ResponseEntity<String> messageUserUsingSlackAPI (@RequestBody SlackEntity input) throws Exception {
        slackService.postMessage(input, applicationCode);
        return ResponseEntity.ok(input.getMessage());
    }

}