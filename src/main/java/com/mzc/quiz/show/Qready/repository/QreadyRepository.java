package com.mzc.quiz.show.Qready.repository;

import com.mzc.quiz.show.Qready.entity.Quiz;
import com.mzc.quiz.show.Qready.entity.Show;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QreadyRepository extends MongoRepository<Show, String> {

    List<Show> findShowByShowInfo_Email(String email);

    Show findShowById(String Id);

//    Quiz findShowByIdAndShowInfo_Email(String id, String email);

}
