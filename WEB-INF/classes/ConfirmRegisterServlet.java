import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URLDecoder;
import activities.db.*;


public class ConfirmRegisterServlet extends HttpServlet {

    private String getCookieValue(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieName)) {
                    try {
                        return URLDecoder.decode(cookies[i].getValue(), "UTF-8");
                    } catch (Exception e) {
                        return cookies[i].getValue();
                    }
                }
            }
        }
        return "";
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        String answer = req.getParameter("answer");

        if (answer.equals("no")) {
            RequestDispatcher rd = req.getRequestDispatcher("register.jsp");
            rd.forward(req, res);
            return;
        }

        if (answer.equals("yes")) {
            String name = getCookieValue(req, "reg_name");
            String surname = getCookieValue(req, "reg_surname");
            String address = getCookieValue(req, "reg_address");
            String phone = getCookieValue(req, "reg_phone");
            String login = getCookieValue(req, "reg_login");
            String passwd = getCookieValue(req, "reg_passwd");

            DBInteraction db = null;
            try {
                db = new DBInteraction();
                db.addusr(login, passwd, name, surname, address, phone);

                req.setAttribute("login", login);

                RequestDispatcher rd = req.getRequestDispatcher("registerOK.jsp");
                rd.forward(req, res);
                return;
            }
            catch (Exception e) {
                throw new ServletException(e);
            }
            finally {
                try {
                    if (db != null) db.close();
                }
                catch (Exception e) { }
            }
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }
}