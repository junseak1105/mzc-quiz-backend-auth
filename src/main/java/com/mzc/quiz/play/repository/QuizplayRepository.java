package com.mzc.quiz.play.repository;

import com.mzc.quiz.play.model.QuizPinDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class QuizplayRepository {
    private Map<String, QuizPinDTO> quizPinDTOMap;

    @PostConstruct
    private void init(){
        quizPinDTOMap = new LinkedHashMap<>();
    }

    public QuizPinDTO createRoomDTO(String name){
        QuizPinDTO pin = QuizPinDTO.create(name);
        quizPinDTOMap.put(pin.getPinNum(), pin);

        return pin;
    }
}
