package com.mzc.quiz.play.repository;

import com.mzc.quiz.show.entity.Show;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QplayRepository extends MongoRepository<Show, String> {
    Show findShowById(String Id);
}
