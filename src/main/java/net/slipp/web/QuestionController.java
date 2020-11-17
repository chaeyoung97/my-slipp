package net.slipp.web;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session)){
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session))
            return "/users/loginForm";
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(sessionUser, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model){
        Question question = questionRepository.findById(id).get();
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session)){
            return "/users/loginForm";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).get();
        if(!question.isSameWriter(loginUser)){
            return "/users/loginForm";
        }
        model.addAttribute("question",question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, String title, String contents, HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session)){
            return "/users/loginForm";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).get();
        if(!question.isSameWriter(loginUser)){
            return "/users/loginForm";
        }
        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }
   @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        questionRepository.deleteById(id);
        return "redirect:/";
   }
}
