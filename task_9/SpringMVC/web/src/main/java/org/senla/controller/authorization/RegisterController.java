package org.senla.controller.authorization;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.RegisterDto;
import org.senla.service.AuthorizationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/authorization")
public class RegisterController {
    private AuthorizationService authorizationService;

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "/authorization/register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @Valid @ModelAttribute("registerDto") RegisterDto registerDto,
            HttpSession session,
            Model model
    ) {
        List<String> errors = authorizationService.validateRegistration(registerDto);


        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "/authorization/register";
        }

        authorizationService.register(registerDto);
        session.setAttribute("username", registerDto.getUsername());

        return "redirect:/home";
    }

}
