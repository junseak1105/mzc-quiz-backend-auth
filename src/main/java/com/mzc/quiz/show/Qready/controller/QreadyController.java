package com.mzc.quiz.show.Qready.controller;

import com.mzc.quiz.show.Qready.entity.Quiz;
import com.mzc.quiz.show.Qready.entity.Show;
import com.mzc.quiz.show.Qready.entity.ShowInfo;
import com.mzc.quiz.show.Qready.repository.QreadyRepository;
import com.mzc.quiz.show.Qready.request.ShowReq;
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
    public void showSave(@RequestBody Show show){
        qreadyService.showSave(show);
    }

    @PostMapping("/showList")
    public List<ShowListRes> getShowList(@RequestBody ShowReq showReq){
        log.info(showReq);
        List<Show> shows = qreadyService.searchShowByEmail(showReq.getEmail());

        List<ShowListRes> showListRes = new ArrayList<>();

        for (Show show : shows) {
            ShowListRes res  = new ShowListRes();
            res.set_id(show.get_id());
            res.setShowInfo(show.getShowInfo());
            showListRes.add(res);
        }

        return showListRes;

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
