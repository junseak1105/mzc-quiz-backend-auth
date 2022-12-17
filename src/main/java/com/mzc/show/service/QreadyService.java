package com.mzc.show.service;

import com.mzc.global.Response.DefaultRes;
import com.mzc.show.entity.Show;

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
