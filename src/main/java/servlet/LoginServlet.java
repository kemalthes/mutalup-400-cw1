package servlet;

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

@WebServlet(name = "index", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final UserRepository repository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> userWrapper = repository.getByName(login);
        if (userWrapper.isPresent() && userWrapper.get().password().equals(password)) {
            System.out.println("dsfsdjgndrejngdfjgndjfgndfkjgnjdfng");
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", login);
            httpSession.setMaxInactiveInterval(60*60);
            Cookie cookie = new Cookie("user", login);
            cookie.setMaxAge(24 * 60 + 60);
            resp.addCookie(cookie);
            req.setAttribute("user", login);
            req.setAttribute("sessionId", httpSession.getId());
            req.setAttribute("cookieUser", cookie.getValue());
            req.getRequestDispatcher("main.ftl").forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }
}
