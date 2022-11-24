package com.mzc.quiz.show.Qready.service;

import com.mzc.global.config.DefaultRes;
import com.mzc.quiz.show.Qready.entity.Quiz;
import com.mzc.quiz.show.Qready.entity.Show;
import com.mzc.quiz.show.Qready.entity.QuizInfo;
import org.bson.types.ObjectId;

import java.util.List;

public interface QreadyService {

    DefaultRes  getShowList(String email);
    DefaultRes  getShow(String showId);
    DefaultRes  createShow(Show show);
    DefaultRes  deleteShow(String showId);

//    public DefaultRes showSave(Show show);
//
//    public DefaultRes getShowById(String id);
//
//
//    public DefaultRes searchShowByEmail(String email);
//
//    public DefaultRes searchQuiz(String id, String email);

}
