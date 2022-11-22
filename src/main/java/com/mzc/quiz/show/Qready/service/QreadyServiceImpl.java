package com.mzc.quiz.show.Qready.service;

import com.mzc.global.config.DefaultRes;
import com.mzc.global.config.ResponseMessages;
import com.mzc.global.config.StatusCode;
import com.mzc.quiz.show.Qready.entity.Quiz;
import com.mzc.quiz.show.Qready.entity.Show;
import com.mzc.quiz.show.Qready.repository.QreadyRepository;
import com.mzc.quiz.show.Qready.response.QuizListRes;
import com.mzc.quiz.show.Qready.response.ShowListRes;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class QreadyServiceImpl implements QreadyService{

//    @Autowired
//    QreadyRepository qreadyRepository;
    private final MongoOperations operations;
    @Autowired
    QreadyRepository qreadyRepository;

    public QreadyServiceImpl(MongoOperations operations) {
        this.operations = operations;
    }

    @Override
    @Transactional
    public void showSave(Show show) {
        log.info("Transaction Start");
        qreadyRepository.save(show);
//        operations.insert();
    }

    @Override
    @Transactional
    public DefaultRes searchShowByEmail(String email) {
        log.info("search  Show  By Email  ::  "+ email);
        List<Show> shows = qreadyRepository.findShowByShowInfo_Email(email);
        List<ShowListRes> showListRes = new ArrayList<>();
        for (Show show : shows) {
            ShowListRes res  = new ShowListRes();
            res.setId(show.getId());
            res.setQuizInfo(show.getShowInfo());
            showListRes.add(res);
        }
        return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS,showListRes);


    }

    @Override
    public DefaultRes searchQuiz(String id, String email) {
        log.info("id : "+id +", email : "+email);

        Show show = qreadyRepository.findShowById(id);

        QuizListRes quizListRes = new QuizListRes();

        List<Quiz> quiz = show.getQuizList();
        quizListRes.setId(id);
        quizListRes.setShowInfo(show.getShowInfo());
        quizListRes.setQuizList(quiz);

        return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS,quizListRes);
    }
}
