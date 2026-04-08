package activities.db;

import java.sql.*;
import java.util.ArrayList;
import java.security.MessageDigest;


public class DBInteraction {

	//private static final String dblogin = "";
	private static final String dbpasswd = "0796";

	private static final String PEPPER = "DATJEE";
    private static final String HASH_ALG = "SHA-256";
    
	Query q;
	Connection con;

	//Constructor that connects to the Database
	public DBInteraction () throws SQLException {
		String url="jdbc:mysql://localhost/sporting_manager";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }
        try {
            System.out.println("Trying to connect...");
            con = DriverManager.getConnection (url, "root", dbpasswd);
            System.out.println("Connected!");
		}
		catch(SQLException ex) {
            System.err.print("SQLException: ");
            System.err.println(ex.getMessage());
        }
        q=new Query(con);
	}    

	//Method to close the database Connection
	public void close()throws Exception{
        q.close();
		con.close();
	}

	private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02x", bytes[i]));
        }
        return sb.toString();
    }

    private static String hashPassword(String plainPassword) throws Exception {
        MessageDigest md = MessageDigest.getInstance(HASH_ALG);
        String input = plainPassword + PEPPER;
        byte[] digest = md.digest(input.getBytes("UTF-8"));
        return toHex(digest);
    }

	//Method to add a new user to the CLIENTS table
	public void addusr(String login, String pwd, String name, String surname, String address, String phone)
					throws Exception{
		String hashed = hashPassword(pwd);
        String addusr="INSERT INTO CLIENTS VALUES ('"+login+"','"+hashed+"','"+name+"','"+surname+"','"+address+"','"+phone+"')";
	    q.doUpdate(addusr);
	}

	// This method returns 'true' in case there exists a row in the CLIENTS table with the login and password passed as parameters
	// If there is no such row exists then it returns 'false'	
	public boolean authentication(String login, String pwd)throws Exception{
		String list="SELECT PASSWD FROM CLIENTS WHERE LOGIN='"+login+"'";
        String storedHash = null;

        ResultSet rs=q.doSelect(list);
        if (rs.next()){
            storedHash = rs.getString(1);
        }
        if (storedHash == null){
            return false;
        }

        String inputHash = hashPassword(pwd);
        return storedHash.equals(inputHash);
	}
	
	//This method deletes the user whose login is passed as a parameter from the CLIENTS table.
	public void delusr(String login) throws Exception{
		String delusr="DELETE FROM CLIENTS WHERE LOGIN='"+login+"'";
		q.doUpdate(delusr);
	}

	//This method adds a new activity in the ACTIVITIES table using the data passed as parameters
	public void addact( String name, String description, String initial, float price, String pav_name, int total, int occ)throws Exception{
		String addactivity="INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES) VALUES ('"+name+"','"+description+"','"+initial+"','"+price+"','"+pav_name+"','"+total+"','"+occ+"')";
		q.doUpdate(addactivity);
	}
	
	//This method deletes the row whose id is passed as a parameter from the ACTIVITIES table.   
	public void delact(int id) throws Exception{
		String delact="DELETE FROM ACTIVITIES WHERE ID='"+id+"'";
		q.doUpdate(delact);
	}

	//This method adds a new pavillion in the PAVILLIONS table using the data passed as parameters
	public void addpav(String pavname, String pavlocation) throws Exception{
		String addpavillion="INSERT INTO PAVILLIONS VALUES ('"+pavname+"','"+pavlocation+"')";
		q.doUpdate(addpavillion);
	}

	//This method deletes the pavillion whose name is passed as a parameter from the PAVILLIONS table.
	public void delpav(String pavname) throws Exception{
		String delpav="DELETE FROM PAVILLIONS WHERE PAVILLION='"+pavname+"'";
		q.doUpdate(delpav);
	}

	//This method requests the execution of an SQL sentence for listing all the clients
	//and retrieves all the information for each client, storing each client as an element
	//of an array. Each element contains an object of the type Client
	public ArrayList listallusr() throws Exception{
		ArrayList data = new ArrayList();
		String selection="SELECT * FROM CLIENTS";
		ResultSet rs=q.doSelect(selection);
		while (rs.next()) {                     
		   String login = rs.getString(1);
		   String password = rs.getString(2);
           String name = rs.getString(3);
		   String surname = rs.getString(4);
           String address = rs.getString(5);
		   String phone = rs.getString(6);
           data.add(new Client(login, password, name, surname, address, phone));
	    }
		return (data);	
	}

	//This method is common to all the listing activities operations
	//It requests the execution of a SQL sentence for listing activities depending on some criterion
	//and retrieves all the information for each activity, storing each activity as an element
	//of an ArrayList (of Activity objects)
	public ArrayList listactivities(String selection) throws Exception{
		ArrayList data = new ArrayList();
		ResultSet rs=q.doSelect(selection);
		while (rs.next()) {                     
		    int id = rs.getInt(1);
			String name = rs.getString(2);
			String description = rs.getString(3);
			String initial = rs.getString(4);
			float cost = rs.getFloat(5);
			String pavname = rs.getString(6);
			int total = rs.getInt(7);
			int occupied = rs.getInt(8);
			data.add(new Activity (id, name, description, initial, cost, pavname, total, occupied));
		}
		return (data);
	}

	//This method builds an SQL sentence for listing all the activities
	public ArrayList listallact() throws Exception{
		String selection="SELECT * FROM ACTIVITIES";
		ArrayList data = this.listactivities(selection);
		return (data);	
	}

      // This method builds an SQL sentence for listing the activities that have free places
	public ArrayList listactfreeplaces() throws Exception{
		String selection="SELECT * FROM ACTIVITIES WHERE ACTIVITIES.TOTAL_PLACES > ACTIVITIES.OCCUPIED_PLACES";
		ArrayList data = this.listactivities(selection);
		return (data);
	}

	// This method builds an SQL sentence for listing the activities that cost less than a certain amount
	public ArrayList listactprice(float price) throws Exception{
		String selection="SELECT * FROM ACTIVITIES, PAVILLIONS WHERE ACTIVITIES.COST <="+price+"AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";
		ArrayList data = this.listactivities(selection);
		return (data); 
	}

	// This method builds an SQL sentence for listing the activities that take place in a certain pavillion
	public ArrayList listactpav(String namepav) throws Exception{
		String selection="SELECT * FROM ACTIVITIES, PAVILLIONS WHERE ACTIVITIES.PAVILLION_NAME='"+namepav+"'AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";
		ArrayList data = this.listactivities(selection);
		return (data);
	}

	// This method builds an SQL sentence for listing the activities that have a given name (should be only one!)
	public ArrayList listactname(String nameact) throws Exception{
		String selection="SELECT * FROM ACTIVITIES, PAVILLIONS WHERE ACTIVITIES.NAME='"+nameact+"'AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";
		ArrayList data = this.listactivities(selection);
		return (data);
	}

	// This method builds an SQL sentence for listing the activities to which a specific client is subscribed
	public ArrayList listactusr(String login) throws Exception{
		String selection="SELECT ID, NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES FROM REGISTRATIONS, ACTIVITIES, PAVILLIONS WHERE REGISTRATIONS.CLIENT_LOGIN='"+login+"' AND REGISTRATIONS.ACTIVITY_ID = ACTIVITIES.ID AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";
		ArrayList data = this.listactivities(selection);
		return (data);
	}

	//This method requests the execution of a SQL sentence for listing all the pavillions
	//and it retrieves all the information for each pavillion, storing each pavillion as an element
	//of an array. Each element contains an object of the type pavillion
	public ArrayList listallpav() throws Exception{
		ArrayList data = new ArrayList();
		String selection="SELECT * FROM PAVILLIONS";
		ResultSet rs=q.doSelect(selection);
		while (rs.next()) {                     
		    String name = rs.getString(1);
			String location = rs.getString(2);
			data.add(new Pavillion(name, location));
       }
	   return (data);
	}

	//This method subscribes a client to a specific activity
      //(after checking that the activity has free places)
	public void regactivity(String login, String id) throws Exception{
		String regactivity="INSERT INTO REGISTRATIONS VALUES ('"+login+"','"+id+"')";
		q.doUpdate(regactivity);
	}

	//This method unsubscribes a client from a specific activity.
	//(after checking that the client is indeed subscribed to the activity).
	public void unregactivity(String login, String id) throws Exception{
		String unregactivity="DELETE FROM REGISTRATIONS WHERE REGISTRATIONS.CLIENT_LOGIN='"+login+"'AND REGISTRATIONS.ACTIVITY_ID="+id;
		q.doUpdate(unregactivity);
	}

	// 8) List activities whose NAME or DESCRIPTION contains a text
	public ArrayList listacttext(String text) throws Exception {
		String selection =
				"SELECT * FROM ACTIVITIES, PAVILLIONS " +
				"WHERE (ACTIVITIES.NAME LIKE '%" + text + "%' " +
				"OR ACTIVITIES.DESCRIPTION LIKE '%" + text + "%') " +
				"AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";
		return this.listactivities(selection);
	}

	// 8) Pavillions where user subscribed to at least 'minActivities'
	public ArrayList listpavminactivities(String login, int minActivities) throws Exception {
		ArrayList data = new ArrayList();

		String selection =
				"SELECT ACTIVITIES.PAVILLION_NAME " +
				"FROM REGISTRATIONS, ACTIVITIES " +
				"WHERE REGISTRATIONS.CLIENT_LOGIN='" + login + "' " +
				"AND REGISTRATIONS.ACTIVITY_ID = ACTIVITIES.ID " +
				"GROUP BY ACTIVITIES.PAVILLION_NAME " +
				"HAVING COUNT(*) >= " + minActivities;

		ResultSet rs = q.doSelect(selection);
		while (rs.next()) {
			data.add(rs.getString(1));
		}
		return data;
	}

	// 9) Añadir la misma actividad varias veces con diferentes fechas de inicio
	public void addactmulti(String name, String description, ArrayList startDates, float price,
							String pav_name, int total, int occ) throws Exception {
		for (int i = 0; i < startDates.size(); i++) {
			String initial = (String) startDates.get(i);
			this.addact(name, description, initial, price, pav_name, total, occ);
		}
	}

	// 9) Listar todos los usuarios suscritos a actividades de alta demanda (>80% ocupación)
	public ArrayList listhighdemandusers() throws Exception {
		ArrayList data = new ArrayList();

		String selection =
				"SELECT DISTINCT CLIENTS.LOGIN, CLIENTS.PASSWD, CLIENTS.NAME, CLIENTS.SURNAME, CLIENTS.ADDRESS, CLIENTS.PHONE " +
				"FROM CLIENTS, REGISTRATIONS, ACTIVITIES " +
				"WHERE CLIENTS.LOGIN = REGISTRATIONS.CLIENT_LOGIN " +
				"AND REGISTRATIONS.ACTIVITY_ID = ACTIVITIES.ID " +
				"AND (ACTIVITIES.OCCUPIED_PLACES * 1.0 / ACTIVITIES.TOTAL_PLACES) > 0.8";

		ResultSet rs = q.doSelect(selection);
		while (rs.next()) {
			String login = rs.getString(1);
			String password = rs.getString(2);
			String name = rs.getString(3);
			String surname = rs.getString(4);
			String address = rs.getString(5);
			String phone = rs.getString(6);
			data.add(new Client(login, password, name, surname, address, phone));
		}
		return data;
	}

	public void updateact(int id, String name, String description, String initial,
                      float price, String pav_name, int total, int occ) throws Exception {
		
		String updateactivity =
			"UPDATE ACTIVITIES SET " +
			"NAME='" + name + "', " +
			"DESCRIPTION='" + description + "', " +
			"START_DATE='" + initial + "', " +
			"COST=" + price + ", " +
			"PAVILLION_NAME='" + pav_name + "', " +
			"TOTAL_PLACES=" + total + ", " +
			"OCCUPIED_PLACES=" + occ + " " +
			"WHERE ID=" + id;
		q.doUpdate(updateactivity);
}

}