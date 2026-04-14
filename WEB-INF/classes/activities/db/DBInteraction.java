package activities.db;

import java.sql.*;
import java.util.ArrayList;
import java.security.MessageDigest;

public class DBInteraction {

    private static final String dbpasswd = "0796";

    private static final String PEPPER = "DATJEE";
    private static final String HASH_ALG = "SHA-256";

    private Connection con;

    // Constructor that connects to the Database
    public DBInteraction() throws SQLException {
        String url = "jdbc:mysql://localhost/sporting_manager";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Could not load MySQL JDBC driver.", e);
        }

        try {
            System.out.println("Trying to connect...");
            con = DriverManager.getConnection(url, "root", dbpasswd);
            System.out.println("Connected!");
        } catch (SQLException e) {
            throw new SQLException("Error connecting to database.", e);
        }
    }

    // Method to close the database Connection
    public void close() throws Exception {
        if (con != null && !con.isClosed()) {
            con.close();
        }
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

    // Method to add a new user to the CLIENTS table
    public void addusr(String login, String pwd, String name, String surname, String address, String phone)
            throws Exception {
        String sql = "INSERT INTO CLIENTS (LOGIN, PASSWD, NAME, SURNAME, ADDRESS, PHONE) VALUES (?, ?, ?, ?, ?, ?)";
        String hashed = hashPassword(pwd);

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, hashed);
            ps.setString(3, name);
            ps.setString(4, surname);
            ps.setString(5, address);
            ps.setString(6, phone);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Database error while adding user.", e);
        }
    }

    // This method returns true if login and password are correct
    public boolean authentication(String login, String pwd) throws Exception {
        String sql = "SELECT PASSWD FROM CLIENTS WHERE LOGIN = ?";
        String storedHash = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    storedHash = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Database error while checking user authentication.", e);
        }

        if (storedHash == null) {
            return false;
        }

        String inputHash = hashPassword(pwd);
        return storedHash.equals(inputHash);
    }

    // This method deletes the user whose login is passed as a parameter
    public void delusr(String login) throws Exception {
        String sql = "DELETE FROM CLIENTS WHERE LOGIN = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Database error while deleting user.", e);
        }
    }

    // This method adds a new activity in the ACTIVITIES table
    public void addact(String name, String description, String initial, float price, String pav_name, int total, int occ)
            throws Exception {
        String sql = "INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, initial);
            ps.setFloat(4, price);
            ps.setString(5, pav_name);
            ps.setInt(6, total);
            ps.setInt(7, occ);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Database error while adding activity.", e);
        }
    }

    // This method updates an activity
    public void updateact(int id, String name, String description, String initial,
                          float price, String pav_name, int total, int occ) throws Exception {
        String sql = "UPDATE ACTIVITIES SET NAME = ?, DESCRIPTION = ?, START_DATE = ?, COST = ?, PAVILLION_NAME = ?, TOTAL_PLACES = ?, OCCUPIED_PLACES = ? WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, initial);
            ps.setFloat(4, price);
            ps.setString(5, pav_name);
            ps.setInt(6, total);
            ps.setInt(7, occ);
            ps.setInt(8, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Database error while editing activity.", e);
        }
    }

    // This method deletes the row whose id is passed as a parameter
    public void delact(int id) throws Exception {
        String sql = "DELETE FROM ACTIVITIES WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Database error while deleting activity.", e);
        }
    }

    // This method adds a new pavillion
    public void addpav(String pavname, String pavlocation) throws Exception {
        String sql = "INSERT INTO PAVILLIONS (PAVILLION, LOCATION) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pavname);
            ps.setString(2, pavlocation);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Database error while adding pavillion.", e);
        }
    }

    // This method deletes a pavillion
    public void delpav(String pavname) throws Exception {
        String sql = "DELETE FROM PAVILLIONS WHERE PAVILLION = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pavname);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Database error while deleting pavillion.", e);
        }
    }

    // List all users
    public ArrayList listallusr() throws Exception {
        ArrayList data = new ArrayList();
        String sql = "SELECT * FROM CLIENTS";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String login = rs.getString(1);
                String password = rs.getString(2);
                String name = rs.getString(3);
                String surname = rs.getString(4);
                String address = rs.getString(5);
                String phone = rs.getString(6);
                data.add(new Client(login, password, name, surname, address, phone));
            }
        } catch (SQLException e) {
            throw new Exception("Database error while listing users.", e);
        }

        return data;
    }

    // Helper method to transform a ResultSet into an ArrayList of Activity
    private ArrayList readActivities(ResultSet rs) throws Exception {
        ArrayList data = new ArrayList();

        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String description = rs.getString(3);
            String initial = rs.getString(4);
            float cost = rs.getFloat(5);
            String pavname = rs.getString(6);
            int total = rs.getInt(7);
            int occupied = rs.getInt(8);

            data.add(new Activity(id, name, description, initial, cost, pavname, total, occupied));
        }

        return data;
    }

    // List all activities
    public ArrayList listallact() throws Exception {
        String sql = "SELECT * FROM ACTIVITIES";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return readActivities(rs);
        } catch (SQLException e) {
            throw new Exception("Database error while listing all activities.", e);
        }
    }

    // List activities with free places
    public ArrayList listactfreeplaces() throws Exception {
        String sql = "SELECT * FROM ACTIVITIES WHERE TOTAL_PLACES > OCCUPIED_PLACES";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return readActivities(rs);
        } catch (SQLException e) {
            throw new Exception("Database error while listing activities with free places.", e);
        }
    }

    // List activities with cost <= price
    public ArrayList listactprice(float price) throws Exception {
        String sql = "SELECT ACTIVITIES.* " +
                    "FROM ACTIVITIES, PAVILLIONS " +
                    "WHERE ACTIVITIES.COST <= ? " +
                    "AND ACTIVITIES.TOTAL_PLACES > ACTIVITIES.OCCUPIED_PLACES " +
                    "AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setFloat(1, price);

            try (ResultSet rs = ps.executeQuery()) {
                return readActivities(rs);
            }
        } catch (SQLException e) {
            throw new Exception("Database error while listing activities by price.", e);
        }
    }

    // List activities in a specific pavillion
    public ArrayList listactpav(String namepav) throws Exception {
        String sql = "SELECT ACTIVITIES.* " +
                     "FROM ACTIVITIES, PAVILLIONS " +
                     "WHERE ACTIVITIES.PAVILLION_NAME = ? " +
                     "AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, namepav);

            try (ResultSet rs = ps.executeQuery()) {
                return readActivities(rs);
            }
        } catch (SQLException e) {
            throw new Exception("Database error while listing activities by pavillion.", e);
        }
    }

    // List activities with a given name
    public ArrayList listactname(String nameact) throws Exception {
        String sql = "SELECT ACTIVITIES.* " +
                     "FROM ACTIVITIES, PAVILLIONS " +
                     "WHERE ACTIVITIES.NAME = ? " +
                     "AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nameact);

            try (ResultSet rs = ps.executeQuery()) {
                return readActivities(rs);
            }
        } catch (SQLException e) {
            throw new Exception("Database error while listing activities by name.", e);
        }
    }

    // List activities subscribed by a specific client
    public ArrayList listactusr(String login) throws Exception {
        String sql = "SELECT ID, NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES " +
                     "FROM REGISTRATIONS, ACTIVITIES, PAVILLIONS " +
                     "WHERE REGISTRATIONS.CLIENT_LOGIN = ? " +
                     "AND REGISTRATIONS.ACTIVITY_ID = ACTIVITIES.ID " +
                     "AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                return readActivities(rs);
            }
        } catch (SQLException e) {
            throw new Exception("Database error while listing user activities.", e);
        }
    }

    // List all pavillions
    public ArrayList listallpav() throws Exception {
        ArrayList data = new ArrayList();
        String sql = "SELECT * FROM PAVILLIONS";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString(1);
                String location = rs.getString(2);
                data.add(new Pavillion(name, location));
            }
        } catch (SQLException e) {
            throw new Exception("Database error while listing pavillions.", e);
        }

        return data;
    }

    // Subscribe a client to an activity
    public void regactivity(String login, String id) throws Exception {
        String sql = "INSERT INTO REGISTRATIONS (CLIENT_LOGIN, ACTIVITY_ID) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setInt(2, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Database error while registering to activity.", e);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid activity ID.");
        }
    }

    // Unsubscribe a client from an activity
    public void unregactivity(String login, String id) throws Exception {
        String sql = "DELETE FROM REGISTRATIONS WHERE CLIENT_LOGIN = ? AND ACTIVITY_ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setInt(2, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Database error while unregistering from activity.", e);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid activity ID.");
        }
    }

    // List activities whose name or description contains a text
    public ArrayList listacttext(String text) throws Exception {
        String sql = "SELECT ACTIVITIES.* " +
                     "FROM ACTIVITIES, PAVILLIONS " +
                     "WHERE (ACTIVITIES.NAME LIKE ? OR ACTIVITIES.DESCRIPTION LIKE ?) " +
                     "AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String pattern = "%" + text + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                return readActivities(rs);
            }
        } catch (SQLException e) {
            throw new Exception("Database error while searching activities by text.", e);
        }
    }

    // Pavillions where user subscribed to at least minActivities
    public ArrayList listpavminactivities(String login, int minActivities) throws Exception {
        ArrayList data = new ArrayList();

        String sql = "SELECT ACTIVITIES.PAVILLION_NAME " +
                     "FROM REGISTRATIONS, ACTIVITIES " +
                     "WHERE REGISTRATIONS.CLIENT_LOGIN = ? " +
                     "AND REGISTRATIONS.ACTIVITY_ID = ACTIVITIES.ID " +
                     "GROUP BY ACTIVITIES.PAVILLION_NAME " +
                     "HAVING COUNT(*) >= ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setInt(2, minActivities);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Database error while listing pavillions by minimum activities.", e);
        }

        return data;
    }

    // Add the same activity several times with different start dates
    public void addactmulti(String name, String description, ArrayList startDates, float price,
                            String pav_name, int total, int occ) throws Exception {
        for (int i = 0; i < startDates.size(); i++) {
            String initial = (String) startDates.get(i);
            this.addact(name, description, initial, price, pav_name, total, occ);
        }
    }

    // List users subscribed to high-demand activities
    public ArrayList listhighdemandusers() throws Exception {
        ArrayList data = new ArrayList();

        String sql = "SELECT DISTINCT CLIENTS.LOGIN, CLIENTS.PASSWD, CLIENTS.NAME, CLIENTS.SURNAME, CLIENTS.ADDRESS, CLIENTS.PHONE " +
                     "FROM CLIENTS, REGISTRATIONS, ACTIVITIES " +
                     "WHERE CLIENTS.LOGIN = REGISTRATIONS.CLIENT_LOGIN " +
                     "AND REGISTRATIONS.ACTIVITY_ID = ACTIVITIES.ID " +
                     "AND (ACTIVITIES.OCCUPIED_PLACES * 1.0 / ACTIVITIES.TOTAL_PLACES) > 0.8";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String login = rs.getString(1);
                String password = rs.getString(2);
                String name = rs.getString(3);
                String surname = rs.getString(4);
                String address = rs.getString(5);
                String phone = rs.getString(6);
                data.add(new Client(login, password, name, surname, address, phone));
            }
        } catch (SQLException e) {
            throw new Exception("Database error while listing high-demand users.", e);
        }

        return data;
    }

    public ArrayList listactfreepav(String namepav) throws Exception {
        String sql = "SELECT ACTIVITIES.* " +
                    "FROM ACTIVITIES, PAVILLIONS " +
                    "WHERE ACTIVITIES.PAVILLION_NAME = ? " +
                    "AND ACTIVITIES.TOTAL_PLACES > ACTIVITIES.OCCUPIED_PLACES " +
                    "AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, namepav);

            try (ResultSet rs = ps.executeQuery()) {
                return readActivities(rs);
            }
        } catch (SQLException e) {
            throw new Exception("Database error while listing activities with free places in a pavillion.", e);
        }
    }
}