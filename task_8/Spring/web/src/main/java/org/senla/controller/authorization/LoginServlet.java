package org.senla.controller.authorization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.config.WebConfig;
import org.senla.dto.LoginDto;
import org.senla.service.AuthorizationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/authorization/login")
public class LoginServlet extends HttpServlet {
    private AuthorizationService authorizationService;

    @Override
    public void init() throws ServletException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        authorizationService = context.getBean(AuthorizationService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/authorization/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LoginDto loginDto = new LoginDto(username, password);

        List<String> errors = authorizationService.validateLogin(loginDto);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("username", username);
            request.getRequestDispatcher("/authorization/login.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("username", username);
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
