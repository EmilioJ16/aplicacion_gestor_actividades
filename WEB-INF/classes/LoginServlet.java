import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import activities.db.*;

public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");

        String login = req.getParameter("login");
        String passwd = req.getParameter("passwd");

        HttpSession session = req.getSession(true);

        Integer attempts = (Integer) session.getAttribute("loginAttempts");
        if (attempts == null) {
            attempts = 0;
        }

        DBInteraction db = null;

        try {
            db = new DBInteraction();

            System.out.println("DEBUG login recibido: " + login);

            if (login == null || login.trim().equals("") || passwd == null || passwd.trim().equals("")) {
                req.setAttribute("error", "Login and password are required.");
                RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
                rd.forward(req, res);
            return;
            }

            boolean ok = db.authentication(login, passwd);
            System.out.println("DEBUG authentication = " + ok);

            if (ok) {
                
                session.setAttribute("userLogin", login);
                session.setAttribute("loginAttempts", 0);

                System.out.println("DEBUG sesion creada para: " + login);

                res.sendRedirect(req.getContextPath() + "/search.jsp");
                return;
            } else {
                attempts = attempts + 1;
                session.setAttribute("loginAttempts", attempts);

                System.out.println("DEBUG intento fallido numero: " + attempts);

                if (attempts >= 3) {
                    session.invalidate();
                    res.sendRedirect(req.getContextPath() + "/index.html");
                    return;
                } else {
                    req.setAttribute("error", "Incorrect login or password. Attempt " + attempts + " of 3.");
                    RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
                    rd.forward(req, res);
                    return;
                }
            }
        }
        catch (Exception e) {
            throw new ServletException(e);
        }
        finally {
            try {
                if (db != null) db.close();
            } catch (Exception e) { }
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }
}