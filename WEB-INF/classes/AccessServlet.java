import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AccessServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("userLogin") != null) {
            res.sendRedirect(req.getContextPath() + "/search.jsp");
        } else {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doGet(req, res);
    }
}