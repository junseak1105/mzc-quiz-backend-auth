package com.mzc.quiz.play.controller;

import com.mzc.quiz.play.repository.QuizplayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/quiz-play")
@Log4j2
public class QuizPlayController {

    @Autowired
    private final QuizplayRepository quizplayRepository;

    @PostMapping(value="/room/{Pin}")
    public String create(@PathVariable String Pin){
        quizplayRepository.createRoomDTO(Pin);
        System.out.println("/room/"+Pin);
        return "redirect:/quiz-play/room" + Pin;
    }
}
