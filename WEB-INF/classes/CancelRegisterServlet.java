import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CancelRegisterServlet extends HttpServlet {

    private void clearCookie(HttpServletResponse res, String name) {
        Cookie c = new Cookie(name, "");
        c.setPath("/aplicacion_gestor_actividades");
        c.setMaxAge(0);
        res.addCookie(c);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        clearCookie(res, "reg_name");
        clearCookie(res, "reg_surname");
        clearCookie(res, "reg_address");
        clearCookie(res, "reg_phone");
        clearCookie(res, "reg_login");
        clearCookie(res, "reg_passwd");

        res.sendRedirect("index.html");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doGet(req, res);
    }
}