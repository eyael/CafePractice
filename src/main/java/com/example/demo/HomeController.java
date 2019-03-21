package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    CafeRepository cafeRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String index(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("cafe", new Cafe());
        model.addAttribute("cafe", cafeRepository.findAll());

        return "index";
    }
    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("cafe", new Cafe());
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping ("/register")
    public String processRegistrationPage (@Valid
    @ModelAttribute("user") User user, BindingResult result,Model model){
        model.addAttribute("cafe", new Cafe());

        model.addAttribute("user",user);
        if (result.hasErrors())
        {
            return "registration";
        }
        else{
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "index";
    }


    @GetMapping("/add")
    public String cafeForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("cafe",  new Cafe());
        return "cafeform";
    }


    @PostMapping("/process")
    public String processForm(@Valid
                              @ModelAttribute Cafe cafe, BindingResult result,@RequestParam("file") MultipartFile file ) {

        if (file.isEmpty()) {
            return "redirect:/add";
        }
        if (result.hasErrors()){
            return "cafeform";
        }
        cafeRepository.save(cafe);
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            cafe.setPicture(uploadResult.get("url").toString());
           cafeRepository.save(cafe);
        }catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }



    @RequestMapping("/login")
    public String login()
    {
        return "login";

    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        User myuser = ((CustomUserDetails)
        ((UsernamePasswordAuthenticationToken) principal)
                .getPrincipal()).getUser();
         model.addAttribute("myuser",myuser);
        return "secure";
    }
    @RequestMapping("/detail/{id}")
    public String showCafe(@PathVariable("id") long id, Model model){
        model.addAttribute("cafe", cafeRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCafe(@PathVariable("id") long id, Model model){

        model.addAttribute("cafe", cafeRepository.findById(id).get());
        return "cafeform";
    }

    @RequestMapping("/delete/{id}")
    public String delCafe(@PathVariable("id") long id){
        cafeRepository.deleteById(id);
        return "redirect:/";
    }
}


