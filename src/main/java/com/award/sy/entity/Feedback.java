package com.award.sy.entity;

import com.award.core.annotation.ID;
import com.award.core.annotation.TableName;
import com.award.core.annotation.TempField;
import com.award.core.beans.Po;

@TableName(name="tb_feedback")
public class Feedback extends Po {
    @ID
    private Long   feedback_id;					//ID

    @TempField
    private String feedbackIdStr;

    private Long user_id1;			//用户

    private String content;

    public Long getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(Long feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getFeedbackIdStr() {
        return feedbackIdStr;
    }

    public void setFeedbackIdStr(String feedbackIdStr) {
        this.feedbackIdStr = feedbackIdStr;
    }

    public Long getUser_id1() {
        return user_id1;
    }

    public void setUser_id1(Long user_id1) {
        this.user_id1 = user_id1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedback_id=" + feedback_id +
                ", feedbackIdStr='" + feedbackIdStr + '\'' +
                ", user_id1=" + user_id1 +
                ", content='" + content + '\'' +
                '}';
    }
}
