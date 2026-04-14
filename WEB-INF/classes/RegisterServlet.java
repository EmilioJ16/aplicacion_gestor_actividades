import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URLEncoder;

public class RegisterServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String login = req.getParameter("login");
        String passwd = req.getParameter("passwd");

        if (name == null || name.trim().equals("") ||
            surname == null || surname.trim().equals("") ||
            address == null || address.trim().equals("") ||
            phone == null || phone.trim().equals("") ||
            login == null || login.trim().equals("") ||
            passwd == null || passwd.trim().equals("")) {

            req.setAttribute("error", "Invalid registration data.");
            RequestDispatcher rd = req.getRequestDispatcher("register.jsp");
            rd.forward(req, res);
            return;
        }

        Cookie c1 = new Cookie("reg_name", URLEncoder.encode(name, "UTF-8"));
        Cookie c2 = new Cookie("reg_surname", URLEncoder.encode(surname, "UTF-8"));
        Cookie c3 = new Cookie("reg_address", URLEncoder.encode(address, "UTF-8"));
        Cookie c4 = new Cookie("reg_phone", URLEncoder.encode(phone, "UTF-8"));
        Cookie c5 = new Cookie("reg_login", URLEncoder.encode(login, "UTF-8"));
        Cookie c6 = new Cookie("reg_passwd", URLEncoder.encode(passwd, "UTF-8"));

        c1.setPath("/aplicacion_gestor_actividades");
        c2.setPath("/aplicacion_gestor_actividades");
        c3.setPath("/aplicacion_gestor_actividades");
        c4.setPath("/aplicacion_gestor_actividades");
        c5.setPath("/aplicacion_gestor_actividades");
        c6.setPath("/aplicacion_gestor_actividades");

        c1.setMaxAge(1800);
        c2.setMaxAge(1800);
        c3.setMaxAge(1800);
        c4.setMaxAge(1800);
        c5.setMaxAge(1800);
        c6.setMaxAge(1800);

        res.addCookie(c1);
        res.addCookie(c2);
        res.addCookie(c3);
        res.addCookie(c4);
        res.addCookie(c5);
        res.addCookie(c6);

        req.setAttribute("name", name);
        req.setAttribute("surname", surname);
        req.setAttribute("address", address);
        req.setAttribute("phone", phone);
        req.setAttribute("login", login);
        req.setAttribute("passwd", passwd);

        RequestDispatcher rd = req.getRequestDispatcher("confirmRegister.jsp");
        rd.forward(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }
}