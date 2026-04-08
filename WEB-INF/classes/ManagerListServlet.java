import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

import activities.db.DBInteraction;
import activities.db.Activity;

public class ManagerListServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("manager") == null) {
            res.sendRedirect(req.getContextPath() + "/managerLogin.jsp");
            return;
        }

        DBInteraction db = null;

        try {
            db = new DBInteraction();

            ArrayList activities = db.listallact();

            req.setAttribute("activities", activities);

            RequestDispatcher rd = req.getRequestDispatcher("managerActivities.jsp");
            rd.forward(req, res);

        } catch (Exception e) {
            req.setAttribute("error", "Error listing activities: " + e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("managerActivities.jsp");
            rd.forward(req, res);
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doGet(req, res);
    }
}