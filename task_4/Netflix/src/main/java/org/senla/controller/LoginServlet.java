package org.senla.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.di.container.BeanFactory;
import org.senla.dto.LoginDto;
import org.senla.service.AuthorizationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/authorization/login")
public class LoginServlet extends HttpServlet {
    private final BeanFactory beanFactory = new BeanFactory();
    private AuthorizationService authorizationService;


    public void init() throws ServletException {
        authorizationService = beanFactory.getBean(AuthorizationService.class);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/authorization/login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data");
            return;
        }

        List<String> errors = new ArrayList<>();

        if (username.isEmpty() || password.isEmpty()) {
            errors.add("Username and password must not be empty.");
        }

        LoginDto loginDto = new LoginDto(username, password);

        if (!authorizationService.login(loginDto)) {
            errors.add("Invalid username or password.");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("username", username);
            request.getRequestDispatcher("/authorization/login.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/home");
    }
}
