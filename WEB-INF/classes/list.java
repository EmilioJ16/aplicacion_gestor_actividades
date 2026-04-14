import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import activities.db.*;

public class list extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("GET Request. No Form Data Posted");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");

        String type;
        String text;
        String startDate;
        String endDate;

        ArrayList data = new ArrayList();

        type = req.getParameter("type");
        text = req.getParameter("text1");
        startDate = req.getParameter("startDate");
        endDate = req.getParameter("endDate");
         DBInteraction db = null;
        try {
            db = new DBInteraction();

            if ("all_activities".equals(type)) {
                data = db.listallact();
            }
            else if ("all_pavillions".equals(type)) {
                data = db.listallpav();
            }
            else if ("free_places".equals(type)) {
                data = db.listactfreeplaces();
            }
            else if ("cost".equals(type)) {
                float price = Float.parseFloat(text);
                data = db.listactprice(price);
            }
            else if ("free_pav".equals(type)) {
                data = db.listactfreepav(text);
            }
            else if ("text".equals(type)) {
                data = db.listacttext(text);
            }
            else if ("activity".equals(type)) {
                data = db.listactname(text);
            }
            else {
                data = new ArrayList();
            }

            if ("all_pavillions".equals(type)) {
                RequestDispatcher layout = req.getRequestDispatcher("layoutpav.jsp");
                layout.include(req, res);

                for (int i = 0; i < data.size(); i++) {
                    Pavillion p = (Pavillion) data.get(i);

                    req.setAttribute("name", p.getname());
                    req.setAttribute("location", p.getlocation());

                    RequestDispatcher outpav = req.getRequestDispatcher("outpav.jsp");
                    outpav.include(req, res);
                }
            }
            else {
                RequestDispatcher layout = req.getRequestDispatcher("layoutact.jsp");
                layout.include(req, res);

                for (int i = 0; i < data.size(); i++) {
                    Activity a = (Activity) data.get(i);

                    req.setAttribute("id", Integer.valueOf(a.getid()));
                    req.setAttribute("name", a.getname());
                    req.setAttribute("description", a.getdescription());
                    req.setAttribute("initial", a.getinitial());
                    req.setAttribute("cost", Float.valueOf(a.getcost()));
                    req.setAttribute("pavname", a.getpavname());
                    req.setAttribute("total", Integer.valueOf(a.gettotal()));
                    req.setAttribute("occupied", Integer.valueOf(a.getoccupied()));

                    RequestDispatcher outact = req.getRequestDispatcher("outact.jsp");
                    outact.include(req, res);
                }
            }

            RequestDispatcher end = req.getRequestDispatcher("end.jsp");
            end.include(req, res);

            db.close();
        }
        catch (Exception e) {
            throw new ServletException(e);
        }

        finally {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                }
            }
        }
    }
}