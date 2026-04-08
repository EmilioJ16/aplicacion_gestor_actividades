import java.io.*;
import java.util.*;
import java.awt.*;
import javax.servlet.*;
import javax.servlet.http.*;
import activities.db.*;

public class list extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("GET Request. No Form Data Posted");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        String type;
	    String text;
	    String order;
		String startDate;
        String endDate;
		
        //Call for a reference for data base access
		ArrayList data = new ArrayList();
	    //Retrieve the parameters from the form
		type=req.getParameter("type");
	    text=req.getParameter("text1");
		startDate = req.getParameter("startDate");
        endDate = req.getParameter("endDate");

	  try{
		DBInteraction db=new DBInteraction();
		//Depending on the user selected option, calls the properly method of the User object
	    if (type.equals("all_activities")){
	        data=db.listallact(); 
	    }
	    else if (type.equals("all_pavillions")){
		    data=db.listallpav(); 
	    }
		else if (type.equals("free_places")){
			data=db.listactfreeplaces();
		}
		else if (type.equals("cost")){
			float price=Float.parseFloat(text);
			data=db.listactprice(price);
		}
		else if (type.equals("text")){
			data = db.listacttext(text);
		}
		else if (type.equals("activity")) {
    		data = db.listactname(text);
		}
        else {
        	data = db.listactpav(text);
        }

        //Depending on if we list pavillions or activities, the rendering will be different
		if (type.equals("all_pavillions")){
		    RequestDispatcher layout=req.getRequestDispatcher("layoutpav.jsp");
		    layout.include(req, res);
			for(int i=0;i<data.size();i++) {
			    Pavillion p = (Pavillion)data.get(i);
			    String name = p.getname();
			    String location = p.getlocation();
		        RequestDispatcher outpav=req.getRequestDispatcher("outpav.jsp?name="+name+"&location="+location);
		        outpav.include(req, res);
			}
	    }
        else {
		    RequestDispatcher layout=req.getRequestDispatcher("layoutact.jsp");
		    layout.include(req, res);
			for(int i=0;i<data.size();i++) {
			    Activity a = (Activity)data.get(i);
			    int id = a.getid();
			    String name = a.getname();
			    String description = a.getdescription();
			    String initial = a.getinitial();
			    Float cost = a.getcost();
			    String pavname = a.getpavname();
			    int total = a.gettotal();
			    int occupied = a.getoccupied();
				RequestDispatcher outact=req.getRequestDispatcher("outact.jsp?id="+id+"&name="+name+"&description="+description+"&initial="+initial+"&cost="+cost+"&pavname="+pavname+"&total="+total+"&occupied="+occupied);
		        outact.include(req, res);			
	        }
		}
	    RequestDispatcher end=req.getRequestDispatcher("end.jsp");
	    end.include(req, res);
	    db.close();
	  } //end try
	  catch (Exception e){  
		throw new ServletException(e);
	  }
	}//doPost end
}//class end

// Desde: webapps\aplicacion_gestor_actividades\WEB-INF\classes 
// Compilar: javac -cp ".;..\lib\mysql-connector-java-8.0.28.jar" -d ..\bin list.java INNECESARIO
// javac -cp ".;..\lib\mysql-connector-java-8.0.28.jar;C:\Program Files\Apache Software Foundation\Tomcat 9.0\lib\servlet-api.jar" LoginServlet.java LogoutServlet.java list.java .\ConfirmRegisterServlet.java .\list.java .\activities\db\DBInteraction.java