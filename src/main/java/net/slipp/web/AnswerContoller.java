package net.slipp.web;

import net.slipp.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

//answer는 항상 question에 종속적이기떄문에 기본 url을 이렇게 설계하는게 좋다.
@RequestMapping("/questions/{questionId}/answers")
@Controller
public class AnswerContoller {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session, Model model){
        if(!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(loginUser,contents, question);
        answerRepository.save(answer);

        return String.format("redirect:/questions/%d", questionId);
    }
}
