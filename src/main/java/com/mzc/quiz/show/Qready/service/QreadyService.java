package com.mzc.quiz.show.Qready.service;

import com.mzc.global.config.DefaultRes;
import com.mzc.quiz.show.Qready.entity.Show;

public interface QreadyService {
    public void showSave(Show show);

    public DefaultRes searchShowByEmail(String email);

    public DefaultRes searchQuiz(String id, String email);
}
