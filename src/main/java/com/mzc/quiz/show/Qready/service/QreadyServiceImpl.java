package com.mzc.quiz.show.Qready.service;

import com.mzc.quiz.show.Qready.entity.Quiz;
import com.mzc.quiz.show.Qready.entity.Show;
import com.mzc.quiz.show.Qready.repository.QreadyRepository;
import com.mzc.quiz.show.Qready.response.QuizListRes;
import com.mzc.quiz.show.Qready.response.ShowListRes;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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
    public List<Show> searchShowByEmail(String email) {
        log.info("search  Show  By Email  ::  "+ email);
        return qreadyRepository.findShowByShowInfo_Email(email);
    }

    @Override
    public QuizListRes searchQuiz(String id, String email) {
        log.info("id : "+id +", email : "+email);

        Show show = qreadyRepository.findShowById(id);

        QuizListRes quizListRes = new QuizListRes();

        List<Quiz> quiz = show.getQuizList();
        quizListRes.setId(id);
        quizListRes.setShowInfo(show.getShowInfo());
        quizListRes.setQuizList(quiz);

        return quizListRes;
    }
}
