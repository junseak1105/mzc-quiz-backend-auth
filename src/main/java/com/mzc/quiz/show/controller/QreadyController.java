package com.mzc.quiz.show.controller;

import com.mzc.global.Response.DefaultRes;
import com.mzc.quiz.show.entity.Show;
import com.mzc.quiz.show.service.QreadyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/show")
@Log4j2
public class QreadyController {

    @Autowired
    QreadyService qreadyService;


    @GetMapping("/List")
    @ApiOperation(value = "ShowList", notes = "ShowList")
    public DefaultRes GetShowList(@RequestParam("email") String email) {
        return qreadyService.getShowList(email);
    }

    @GetMapping("")
    @ApiOperation(value = "Show", notes = "Show")
    public DefaultRes GetShow(@RequestParam("showId") String showId) {
        return qreadyService.getShow(showId);
    }

    @PostMapping("")
    @ApiOperation(value = "CreateShow", notes = "CreateShow")
    public DefaultRes CreateShow(@RequestBody Show show) {
        return qreadyService.createShow(show);
    }

    @DeleteMapping("")
    @ApiOperation(value = "DeleteShow", notes = "DeleteShow")
    public DefaultRes DeleteShow(@RequestParam("showId") String showId) {
        return qreadyService.deleteShow(showId);
    }

}
