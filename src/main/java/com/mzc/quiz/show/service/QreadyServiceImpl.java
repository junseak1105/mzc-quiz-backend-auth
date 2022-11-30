package com.mzc.quiz.show.service;

import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.quiz.show.entity.Show;
import com.mzc.quiz.show.repository.QreadyRepository;
import com.mzc.quiz.show.response.ShowListRes;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class QreadyServiceImpl implements QreadyService {

    private final MongoOperations operations;
    @Autowired
    QreadyRepository qreadyRepository;

    public QreadyServiceImpl(MongoOperations operations) {
        this.operations = operations;
    }

    @Override
    @Transactional
    public DefaultRes getShowList(String email) {
        List<Show> shows = qreadyRepository.findShowByQuizInfo_Email(email);
        // Show의 QuizData를 제외한 나머지 데이터만 가져옴
        List<ShowListRes> showListRes = new ArrayList<>();
        for (Show show : shows) {
            ShowListRes res = new ShowListRes();
            res.setId(show.getId());
            res.setQuizInfo(show.getQuizInfo());
            showListRes.add(res);
        }
        return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, showListRes);
    }

    @Override
    @Transactional
    public DefaultRes getShow(String showId) {
        Show show = qreadyRepository.findShowById(showId);
        return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, show);
    }

    @Override
    @Transactional
    public DefaultRes createShow(Show show) {
        Show savedShow = qreadyRepository.save(show);
        return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, savedShow);
    }

    @Override
    @Transactional
    public DefaultRes deleteShow(String showId) {
        qreadyRepository.deleteShowById(showId);
        return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS);
    }


}
