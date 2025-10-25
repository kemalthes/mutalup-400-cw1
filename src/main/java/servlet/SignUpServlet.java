package servlet;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "signUp", urlPatterns = "/sign_up")
@MultipartConfig(maxFileSize = 5*1024*1024, maxRequestSize = 10*1024*1024)
public class SignUpServlet extends HttpServlet {

    UserService service = new UserService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("sign_up.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        if (!login.isBlank() && password.length() > 5 && !login.contains(" ") && service.getByLogin(login) == null) {
            String path = service.saveImage(req.getPart("file"));
            service.save(new User(null, name, lastname, login, PasswordUtil.encrypt(password), path));
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", login);
            httpSession.setMaxInactiveInterval(60 * 60);
            Cookie cookie = new Cookie("user", login);
            cookie.setMaxAge(24 * 60 * 60);
            resp.addCookie(cookie);
            req.setAttribute("user", login);
            req.setAttribute("sessionId", httpSession.getId());
            req.setAttribute("cookieUser", cookie.getValue());
            req.getRequestDispatcher("main.ftl").forward(req, resp);
        } else {
            resp.sendRedirect("/sign_up");
        }
    }
}
