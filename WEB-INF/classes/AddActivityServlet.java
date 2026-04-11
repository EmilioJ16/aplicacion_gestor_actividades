import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import activities.db.DBInteraction;

public class AddActivityServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("manager") == null) {
            res.sendRedirect(req.getContextPath() + "/managerLogin.jsp");
            return;
        }

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String initial = req.getParameter("initial");
        String costStr = req.getParameter("cost");
        String pavname = req.getParameter("pavname");
        String totalStr = req.getParameter("total");
        String occupiedStr = req.getParameter("occupied");

        if (name == null || name.trim().equals("") ||
            description == null || description.trim().equals("") ||
            initial == null || initial.trim().equals("") ||
            costStr == null || costStr.trim().equals("") ||
            pavname == null || pavname.trim().equals("") ||
            totalStr == null || totalStr.trim().equals("") ||
            occupiedStr == null || occupiedStr.trim().equals("")) {

            req.setAttribute("error", "All fields are required.");
            RequestDispatcher rd = req.getRequestDispatcher("addActivity.jsp");
            rd.forward(req, res);
            return;
        }

        DBInteraction db = null;

        try {
            float cost = Float.parseFloat(costStr);
            int total = Integer.parseInt(totalStr);
            int occupied = Integer.parseInt(occupiedStr);

            if (total < 0 || occupied < 0) {
                req.setAttribute("error", "Total places and occupied places must be zero or positive.");
                RequestDispatcher rd = req.getRequestDispatcher("addActivity.jsp");
                rd.forward(req, res);
                return;
            }

            if (cost < 0) {
                req.setAttribute("error", "Cost must be zero or positive.");
                RequestDispatcher rd = req.getRequestDispatcher("addActivity.jsp");
                rd.forward(req, res);
                return;
            }

            db = new DBInteraction();
            db.addact(name, description, initial, cost, pavname, total, occupied);

            res.sendRedirect(req.getContextPath() + "/managerList");

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Cost, total places and occupied places must be numeric.");
            RequestDispatcher rd = req.getRequestDispatcher("addActivity.jsp");
            rd.forward(req, res);
        } catch (Exception e) {
            req.setAttribute("error", "Error adding activity: " + e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("addActivity.jsp");
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

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }
}