package com.spring.practice.cheatsheetmaker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.practice.cheatsheetmaker.dto.RegisterationRequest;
import com.spring.practice.cheatsheetmaker.exception.UsernameAlreadyExistsException;
import com.spring.practice.cheatsheetmaker.model.CheatSheet;
import com.spring.practice.cheatsheetmaker.model.User;
import com.spring.practice.cheatsheetmaker.service.CheatSheetDataService;
import com.spring.practice.cheatsheetmaker.service.UserDataService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/")
public class WebController {

  @Autowired
  private CheatSheetDataService cheatSheetDataService;

  @Autowired
  private UserDataService userDataService;

  @GetMapping("/")
  public String index(Model model) {
    final List<CheatSheet> cheatSheets = cheatSheetDataService.getAllCheatSheets();
    model.addAttribute("cheatSheets", cheatSheets);
    model.addAttribute("user", new User());
    return "index";
  }

  // profile only shows that user's sheets.
  @GetMapping("/profile")
  public String profile(Model model) {
    UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final List<CheatSheet> cheatSheets = cheatSheetDataService.getAllCheatSheetsByUsername(user.getUsername());
    model.addAttribute("cheatSheets", cheatSheets);
    return "profile";
  }

  @PostMapping("/register")
  public String register(HttpServletRequest request, User user) {
    
    // Register User if username is available.
    try {
      userDataService.insertUser(new RegisterationRequest(user));
    } catch (UsernameAlreadyExistsException e) {
      return "redirect:/";
    }

    // Login after Registeration.
    try {
        request.login(user.getUsername(), user.getPassword());
      } catch (ServletException e) {
        log.info("Error while login " + e.toString());
    }
    return "redirect:/";
  }

  @GetMapping("/test")
  public String test() {
    userDataService.test();
    return "redirect:/";
  }
}
