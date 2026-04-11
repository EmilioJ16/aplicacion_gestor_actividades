import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ManagerLoginServlet extends HttpServlet {

    private static final String MANAGER_PASSWORD = "admin123";

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");

        String password = req.getParameter("password");

        if (password != null && password.equals(MANAGER_PASSWORD)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("manager", "yes");

            res.sendRedirect(req.getContextPath() + "/manager.jsp");
            return;
        } else {
            req.setAttribute("error", "Incorrect manager password.");
            RequestDispatcher rd = req.getRequestDispatcher("managerLogin.jsp");
            rd.forward(req, res);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }
}