package net.slipp.web;

import net.slipp.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

//answer는 항상 question에 종속적이기떄문에 기본 url을 이렇게 설계하는게 좋다.
@RequestMapping("/api/questions/{questionId}/answers")
@RestController //json으로 변환하도록 인식시켜주는 에노테이션
public class ApiAnswerController {

    private AnswerRepository answerRepository;


    private QuestionRepository questionRepository;

    @Autowired
    public ApiAnswerController(AnswerRepository answerRepository, QuestionRepository questionRepository){
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping("")
   // @ResponseBody   //spring mvc가 json으로 변환하도록해주는
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session, Model model){
        if(!HttpSessionUtils.isLoginUser(session))
            return null;

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(loginUser,contents, question);

        return answerRepository.save(answer);  //save는 전달한 인자를 그대로 리턴함

    }
}
