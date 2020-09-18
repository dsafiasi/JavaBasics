package ServletDemo;
import bean.School;
import bean.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@javax.servlet.annotation.WebServlet(urlPatterns = "/user")
public class WebServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        School school = new School("No.1 middle school","101 sounth street");
        User user = new User(1L,"Bob",school);
        req.setAttribute("user",user);
        req.getRequestDispatcher("/WEB-INF/helloJ.jsp").forward(req,resp);
    }
}
