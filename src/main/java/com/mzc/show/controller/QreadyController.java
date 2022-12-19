package com.mzc.show.controller;

import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.show.entity.Choice;
import com.mzc.show.entity.Quiz;
import com.mzc.show.entity.Show;
import com.mzc.show.service.QreadyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Deprecated
@RestController
@RequestMapping("/v1/show")
@Log4j2
public class QreadyController {

    @Autowired
    QreadyService qreadyService;


    @GetMapping("/List")
    @ApiOperation(value = "ShowList", notes = "ShowList")
    public ResponseEntity GetShowList(@RequestParam("email") String email) {return qreadyService.getShowList(email);}

    @GetMapping("")
    @ApiOperation(value = "Show", notes = "Show")
    public ResponseEntity GetShow(@RequestParam("showId") String showId) {
        return qreadyService.getShow(showId);
    }

    @PostMapping("")
    @ApiOperation(value = "CreateShow", notes = "CreateShow")
    public ResponseEntity CreateShow(@RequestBody Show show) {
        return qreadyService.createShow(show);
    }

    @DeleteMapping("")
    @ApiOperation(value = "DeleteShow", notes = "DeleteShow")
    public ResponseEntity DeleteShow(@RequestParam("showId") String showId) {
        return qreadyService.deleteShow(showId);
    }

}
