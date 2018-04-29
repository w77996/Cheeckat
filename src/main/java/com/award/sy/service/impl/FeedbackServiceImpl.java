package com.award.sy.service.impl;

import com.award.sy.common.DateUtil;
import com.award.sy.dao.FeedbackDao;
import com.award.sy.entity.Feedback;
import com.award.sy.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;
    @Override
    public int addFeedback(long userId, String content) {
        Feedback feedback = new Feedback();
        feedback.setUser_id1(userId);
        feedback.setContent(content);
        feedback.setCreate_time(DateUtil.getNowTime());
        return feedbackDao.addLocal(feedback);
    }
}
