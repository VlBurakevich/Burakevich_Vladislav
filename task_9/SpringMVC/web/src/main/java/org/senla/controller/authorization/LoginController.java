package org.senla.controller.authorization;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.LoginDto;
import org.senla.service.AuthorizationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/authorization")
public class LoginController {
    private AuthorizationService authorizationService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "/authorization/login";
    }

    @PostMapping("/login")
    public String processLogin(
            @Valid @ModelAttribute("loginDto") LoginDto loginDto,
            BindingResult bindingResult,
            Model model,
            HttpSession session
    ) {
        if (bindingResult.hasErrors()) {
            return "authorization/login";
        }

        if (!authorizationService.isValidUser(loginDto)) {
            model.addAttribute("authError", "Invalid username or password");
            return "authorization/login";
        }

        session.setAttribute("username", loginDto.getUsername());
        return "redirect:/home";
    }
}
