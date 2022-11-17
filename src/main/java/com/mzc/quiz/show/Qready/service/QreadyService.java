package com.mzc.quiz.show.Qready.service;

import com.mzc.quiz.show.Qready.entity.Quiz;
import com.mzc.quiz.show.Qready.entity.Show;
import com.mzc.quiz.show.Qready.response.QuizListRes;
import org.bson.types.ObjectId;

import java.util.List;

public interface QreadyService {
    public void showSave(Show show);

    public List<Show> searchShowByEmail(String email);

    public QuizListRes searchQuiz(String id, String email);
}
