package com.award.sy.web.openapi;

import com.award.core.util.JsonUtils;
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

    @RequestMapping(value = "/open/feedback",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String feedback(@RequestParam String userId,@RequestParam String content){

        int i = feedbackService.addFeedback(Long.parseLong(userId),content);
        if(0 < i){
           return  JsonUtils.writeJson("提交成功",1);
        }else{
            return  JsonUtils.writeJson(0, 37, "提交失败");
        }
    }
}
