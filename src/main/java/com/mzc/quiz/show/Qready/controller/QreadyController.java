package com.mzc.quiz.show.Qready.controller;

import com.mzc.global.config.DefaultRes;
import com.mzc.quiz.show.Qready.entity.Quiz;
import com.mzc.quiz.show.Qready.entity.Show;
import com.mzc.quiz.show.Qready.entity.ShowInfo;
import com.mzc.quiz.show.Qready.repository.QreadyRepository;
import com.mzc.quiz.show.Qready.request.ShowReq;
import com.mzc.quiz.show.Qready.response.QuizListRes;
import com.mzc.quiz.show.Qready.response.ShowListRes;
import com.mzc.quiz.show.Qready.service.QreadyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/Qready")
@Log4j2
public class QreadyController {

    @Autowired
    QreadyService qreadyService;


    @PostMapping("/save")
    public DefaultRes showSave(@RequestBody Show show){
        qreadyService.showSave(show);
        return DefaultRes.res(200,"success");
    }

    @PostMapping("/showList")
    public DefaultRes getShowList(@RequestBody ShowReq showReq){
        log.info(showReq);
        return qreadyService.searchShowByEmail(showReq.getEmail());
    }

    @PostMapping("/getQuizList")
    public DefaultRes getQuizList(@RequestBody ShowReq showReq){
        log.info(showReq);
        return qreadyService.searchQuiz(showReq.getId(), showReq.getEmail());

    }

    @PostMapping("/showinfo")
    public ShowInfo printShowInfo(@RequestBody ShowInfo showInfo){
        System.out.println(showInfo);
        return showInfo;
    }

    @PostMapping("/quiz")
    public Quiz printQuiz(@RequestBody Quiz quiz){
        return quiz;
    }

    @PostMapping("/show")
    public Show printShow(@RequestBody Show show){
        System.out.println(show);
        return show;
    };

}
