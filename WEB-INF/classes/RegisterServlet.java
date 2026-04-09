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

        // Validación mínima
        if (name == null || name.equals("") ||
            surname == null || surname.equals("") ||
            address == null || address.equals("") ||
            phone == null || phone.equals("") ||
            login == null || login.equals("") ||
            passwd == null || passwd.equals("")) {

            throw new ServletException("All registration fields must be filled in");
        }

        // Guardar en varias cookies
        Cookie c1 = new Cookie("reg_name", name);
        Cookie c2 = new Cookie("reg_surname", surname);
        Cookie c3 = new Cookie("reg_address", address);
        Cookie c4 = new Cookie("reg_phone", phone);
        Cookie c5 = new Cookie("reg_login", login);
        Cookie c6 = new Cookie("reg_passwd", passwd);

        c1.setMaxAge(1800);
        c2.setMaxAge(1800);
        c3.setMaxAge(1800);
        c4.setMaxAge(1800);
        c5.setMaxAge(1800);
        c6.setMaxAge(1800);

        res.addCookie(new Cookie("reg_name", URLEncoder.encode(name, "UTF-8")));
        res.addCookie(new Cookie("reg_surname", URLEncoder.encode(surname, "UTF-8")));
        res.addCookie(new Cookie("reg_address", URLEncoder.encode(address, "UTF-8")));
        res.addCookie(new Cookie("reg_phone", URLEncoder.encode(phone, "UTF-8")));
        res.addCookie(new Cookie("reg_login", URLEncoder.encode(login, "UTF-8")));
        res.addCookie(new Cookie("reg_passwd", URLEncoder.encode(passwd, "UTF-8")));

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