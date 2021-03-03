package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.impl.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author: 肖震
 * @date: 2021/3/3
 * @since:
 */
@Path("/register")
public class RegisterController implements PageController {
    @Override
    @GET
    @Path("/page")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "register-form.jsp";
    }

    @POST
    @Path("/signin")
    public String doSignin(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        UserService userService = new UserServiceImpl();
        String name = request.getParameter("username");
        String password = request.getParameter("pwd");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        User user = new User(name,password,email,phone);
        userService.register(user);
        return "register-success.jsp";
    }
}
