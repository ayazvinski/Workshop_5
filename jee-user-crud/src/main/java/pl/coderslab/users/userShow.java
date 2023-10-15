package pl.coderslab.users;

import pl.coderslab.DAO.User;
import pl.coderslab.DAO.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/user/show")

public class userShow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserDao userDao = new UserDao();
        User user = userDao.read(Integer.parseInt(id));
        req.setAttribute("user",user);
        getServletContext().getRequestDispatcher("/showUser.jsp")
                .forward(req, resp);
    }
}
