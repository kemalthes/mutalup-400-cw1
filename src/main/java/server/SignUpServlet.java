package server;

import model.User;
import repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "signUp", urlPatterns = "/sign_up")
public class SignUpServlet extends HttpServlet {

    UserRepository repository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("sign_up.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (!login.isBlank() && password.length() > 5 && repository.getUser(login).isEmpty()) {
            repository.setUser(login, password);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", login);
            httpSession.setMaxInactiveInterval(60*60);
            Cookie cookie = new Cookie("user", login);
            cookie.setMaxAge(24 * 60 + 60);
            resp.addCookie(cookie);
            resp.sendRedirect("main.jsp");
        } else {
            resp.sendRedirect("/sign_up");
        }
    }
}
