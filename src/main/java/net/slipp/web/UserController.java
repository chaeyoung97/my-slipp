package net.slipp.web;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/logout")
    public String logout(HttpSession session){
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        System.out.println("Logout Success");
        return "redirect:/";
    }
    @GetMapping("/users/loginForm")
    public String loginForm(){
        return "/user/login";
    }

    @PostMapping("/users/login")
    public String login(String userId, String password, HttpSession session){
        User user = userRepository.findByUserId(userId);

        if(user == null){
            System.out.println("Login Failure (id)");
            return "redirect:/users/loginForm";
        }
        if(!user.matchPassword(password)){
            System.out.println("Login Failure (password)");
            return "redirect:/users/loginForm";
        }
        System.out.println("Login Success");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }
    @GetMapping("/users/form")
    public String form(){
        return "/user/form";
    }

    @PostMapping("/users") //사용자추가
    public String create(User user){
        System.out.println(user);
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/users/list") //리스트목록으로 이동
    public String list(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }


   @GetMapping("/users/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session){
       if(!HttpSessionUtils.isLoginUser(session)){
           return "redirect:/users/loginForm";
       }
       User sessionUser = HttpSessionUtils.getUserFromSession(session);
       //if(!sessionUser.matchId(id))){
       //    throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
       //}
        User user = userRepository.findById(sessionUser.getId()).get();
        model.addAttribute("user",user);
        //findOne이 자꾸에러가 나서 찾아보니 메소드명이 findById로 바뀌었다고 한다
       //그래서 findById().get()렇게 get으로 가져와 주어야한다.
        return "/user/updateForm";
    }

    @PutMapping("/users/{id}")
    public String update(@PathVariable Long id, User updateUser, HttpSession session){

        if(!HttpSessionUtils.isLoginUser(session)){
            return "redirect:/users/loginForm";
        }
        User sessionUser =  HttpSessionUtils.getUserFromSession(session);
        User user = userRepository.findById(sessionUser.getId()).get();
        user.update(updateUser);
        userRepository.save(user);
        /*save를 하면 userRepository에서는 어떻게 동작하냐면
        현재 해당하는 id값이 없으면 insert를 하고
        기존에 있는 사용자면 update를 함
        */
        return "redirect:/users/list";
    }
}
