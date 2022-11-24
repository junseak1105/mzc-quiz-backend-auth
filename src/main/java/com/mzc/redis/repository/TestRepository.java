package com.mzc.redis.repository;

import com.mzc.redis.model.QuizMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends CrudRepository<QuizMessage, List> {

}
