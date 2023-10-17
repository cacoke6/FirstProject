package com.example.basic.Member.controller;


import com.example.basic.Member.model.Board;
import com.example.basic.Member.model.Owner;
import com.example.basic.Member.repositoty.BoardRepository;
import com.example.basic.Member.repositoty.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    HttpSession session;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    BoardRepository boardRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/signupdate")
    public String signupdate(@ModelAttribute Owner owner, Model model) {

        String email = (String) session.getAttribute("email");

        Optional<Owner> opt = ownerRepository.findByEmail(email);

        model.addAttribute("owner", opt.get());

        return"/auth/signupdate";    }


    @PostMapping("/signupdate")
    public String signupdatePost(@ModelAttribute Owner owner) {
        String newPwd = passwordEncoder.encode(owner.getPwd());
        owner.setPwd(newPwd);
        ownerRepository.save(owner);
        session.invalidate();
        return"redirect:/";
    }

    @GetMapping("/signin")
    public String signin() {
        return "auth/signin";
    }


    @PostMapping("/signin")
    public String signPost(@ModelAttribute Owner owner) {


        String name = owner.getName();

        Optional<Owner> result = ownerRepository.findByName(name);

        boolean isPresent  = result.isPresent();

        if (isPresent) {
            String pwd = result.get().getPwd();
            String email = result.get().getEmail();
            boolean isMatch =
                    passwordEncoder.matches(owner.getPwd(), pwd);

            if (isMatch) {
                session.setAttribute("email", email);
                session.setAttribute("id", id);
                return "redirect:/";
            }
        } else {
            return "redirect:/auth/signin";
        }
        return "redirect:/auth/signin";
    }




    @GetMapping("/signout")
    public String signout() {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }


    @PostMapping("/signup")
    public String signupPost(@ModelAttribute Owner owner) {


        String newPwd = passwordEncoder.encode(owner.getPwd());

        String name = owner.getName().trim();

        Optional<Owner> result = ownerRepository.findByName(name);




        if(name.equals("")) {
            return "redirect:/auth/signup";
        } if (result.isEmpty()) {
            owner.setDate(new Date());
            owner.setPwd(newPwd);
            ownerRepository.save(owner);
            return "redirect:/auth/signin";
        } else {
            return "redirect:/auth/signup";
        }

    }


}