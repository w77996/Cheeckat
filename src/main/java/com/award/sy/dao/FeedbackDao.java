package com.award.sy.dao;

import com.award.core.dao.impl.DaoImpl;
import com.award.sy.entity.Feedback;
import org.springframework.stereotype.Repository;

@Repository("feedbackDao")
public class FeedbackDao extends DaoImpl<Feedback,Long> {
}
