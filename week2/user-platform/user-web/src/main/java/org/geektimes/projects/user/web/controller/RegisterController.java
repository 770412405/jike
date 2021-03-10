package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.impl.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
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
    @Resource(name = "bean/userService")
    private UserService userService;

    @Override
    @GET
    @Path("/page")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "register-form.jsp";
    }

    @POST
    @Path("/signin")
    public String doSignin(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        userService.saveUser(getUser(request));
        return "register-success.jsp";
    }

    private User getUser(HttpServletRequest request){
        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setName(request.getParameter("username"));
        user.setPassword(request.getParameter("pwd"));
        user.setPhoneNumber(request.getParameter("phone"));
        return user;
    }
}
