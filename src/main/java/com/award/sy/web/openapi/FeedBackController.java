package com.award.sy.web.openapi;

import com.award.sy.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeedBackController {

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping("/open/feedback")
    @ResponseBody
    public String feedback(@RequestParam String userId,@RequestParam String content){

        feedbackService.addFeedback(Long.parseLong(userId),content);

        return null;
    }
}
