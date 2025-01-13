package org.senla.controller.authorization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.config.WebConfig;
import org.senla.dto.RegisterDto;
import org.senla.service.AuthorizationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/authorization/register")
public class RegisterServlet extends HttpServlet {
    private AuthorizationService authorizationService;

    @Override
    public void init() throws ServletException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        authorizationService = context.getBean(AuthorizationService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/authorization/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        List<String> errors = authorizationService.validateRegistration(username, email, password, confirmPassword);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/authorization/register.jsp").forward(request, response);
            return;
        }

        RegisterDto registerDto = new RegisterDto(username, email, password);
        authorizationService.register(registerDto);
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
