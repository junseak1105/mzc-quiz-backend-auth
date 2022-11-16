package com.mzc.quiz.show.Qready.service;

import com.mzc.quiz.show.Qready.entity.Show;

import java.util.List;

public interface QreadyService {
    public void showSave(Show show);

    public List<Show> searchShowByEmail(String Email);
}
