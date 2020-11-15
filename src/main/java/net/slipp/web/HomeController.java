package net.slipp.web;

import net.slipp.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class  HomeController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String Home(Model model){

        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }
}
