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
public class QreadyServiceImpl implements QreadyService{

    private final MongoOperations operations;
    @Autowired
    QreadyRepository qreadyRepository;

    public QreadyServiceImpl(MongoOperations operations) {
        this.operations = operations;
    }

    @Override
    @Transactional
    public DefaultRes getShowList(String email) {
        try{
            List<Show> shows = qreadyRepository.findShowByQuizInfo_Email(email);
            // Show의 QuizData를 제외한 나머지 데이터만 가져옴
            List<ShowListRes> showListRes = new ArrayList<>();
            for (Show show : shows) {
                ShowListRes res  = new ShowListRes();
                res.setId(show.getId());
                res.setQuizInfo(show.getQuizInfo());
                showListRes.add(res);
            }
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, showListRes);
        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public DefaultRes getShow(String showId) {
        System.out.println("showId : " + showId);
        try {
            Show show = qreadyRepository.findShowById(showId);
            System.out.println("show : " + show);
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, show);
        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public DefaultRes createShow(Show show) {
        System.out.println(show);
        try {
            Show savedShow = qreadyRepository.save(show);
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, savedShow);
        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public DefaultRes deleteShow(String showId) {
        try {
            qreadyRepository.deleteShowById(showId);
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS);
        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.BAD_REQUEST);
        }
    }


}
