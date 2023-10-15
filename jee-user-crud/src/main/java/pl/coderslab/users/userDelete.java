package pl.coderslab.users;

import pl.coderslab.DAO.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/user/delete")

public class userDelete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserDao userDao = new UserDao();
        userDao.delete(Integer.parseInt(id));
        resp.sendRedirect(req.getContextPath() + "/user/list");
    }
}
