package net.slipp.web;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.Result;
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

    private Result valid(HttpSession session, Question question){
        if(!HttpSessionUtils.isLoginUser(session)){
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if(!question.isSameWriter(loginUser)){
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

    private boolean hasPermission(HttpSession session, Question question){
        if(!HttpSessionUtils.isLoginUser(session)){
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if(!question.isSameWriter(loginUser)){
            throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가가능합니다.");
        }
        return true;
    }
    @GetMapping("/form")
    public String form(HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session)){
            return "redirect:/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
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
        Question question = questionRepository.findById(id).get();
        Result result = valid(session, question);
        if(!result.isVaild()){
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login"; //여기 원래 redirect:/users/loginForm으로 되어있었는데 이제 에러메세지를 담은 모델을 던져줘야 하는 것이기 때문에 무스타치 템플릿으로 이동하는것으로 수정해주었다.
        }
        model.addAttribute("question",question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, String title, String contents, HttpSession session, Model model){
        Question question = questionRepository.findById(id).get();
        Result result = valid(session, question);
        if(!result.isVaild()){
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login"; //여기 원래 redirect:/users/loginForm으로 되어있었는데 이제 에러메세지를 담은 모델을 던져줘야 하는 것이기 때문에 무스타치 템플릿으로 이동하는것으로 수정해주었다.
        }
        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

   @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session, Model model){
       Question question = questionRepository.findById(id).get();
       Result result = valid(session, question);
       if(!result.isVaild()){
           model.addAttribute("errorMessage", result.getErrorMessage());
           return "/user/login"; //여기 원래 redirect:/users/loginForm으로 되어있었는데 이제 에러메세지를 담은 모델을 던져줘야 하는 것이기 때문에 무스타치 템플릿으로 이동하는것으로 수정해주었다.
       }
       questionRepository.delete(question);
       return "redirect:/";
   }
}
