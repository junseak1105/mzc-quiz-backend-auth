package com.mzc.quiz.show.Qready.service;

import com.mzc.quiz.show.Qready.entity.Show;
import com.mzc.quiz.show.Qready.repository.QreadyRepository;
import lombok.extern.log4j.Log4j2;
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
    public List<Show> searchShowByEmail(String Email) {
        log.info("search  Show  By Email  ::  "+ Email);
        return qreadyRepository.findShowByShowInfo_Email(Email);
    }
}
