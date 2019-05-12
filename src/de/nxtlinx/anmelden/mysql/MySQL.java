package de.nxtlinx.anmelden.mysql;

import de.nxtlinx.anmelden.Anmelden;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL {

    private MySQLFile mySQLFile = Anmelden.getPlugin().getMySQLFile();

    private String host = "";
    private String databse = "";
    private  String user = "";
    private String password = "";

    private Connection connection;

    public MySQL(String host,String database,String user,String password){
        this.host = host;
        this.databse = database;
        this.user = user;
        this.password = password;

        connect();
    }

    public MySQL(String host,String database,String user){
        this.host = host;
        this.databse = database;
        this.user = user;

        connect();
    }

    public MySQL(){

        this.host = mySQLFile.getHost();
        this.databse = mySQLFile.getDatabase();
        this.user = mySQLFile.getUser();
        this.password = mySQLFile.getPassword();

        connect();
    }

    public void connect(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+databse+"?autoReconnect=true",user,password);
            Bukkit.getConsoleSender().sendMessage(Anmelden.PREFIX+"§aEs wurde eine Verbindung hergestellt.");
            createTable();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage( Anmelden.PREFIX+"§4Es gab einen Fehler!");
            // e.printStackTrace();
        }

    }

    public void disconnect(){
        try {
            if (connection != null){
                connection.close();
                Bukkit.getConsoleSender().sendMessage(Anmelden.PREFIX+"§aDie Verbindung wurde getrennt!");
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void createTable(){
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS anmelden (uuid VARCHAR(150), playername VARCHAR(150), id INT(16), rang VARCHAR(150), password VARCHAR(150), username VARCHAR(150))").executeUpdate();
            Bukkit.getConsoleSender().sendMessage(Anmelden.PREFIX+"§aEs wurde eine Tabelle erstellt.");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(Anmelden.PREFIX+"§4Es gab einen Fehler bei der erstellung der Tabelle!");
            // e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected(){
        return (connection != null);
    }

    public void update(String qry){

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePREPARE(String qry){

        try {
            PreparedStatement st = connection.prepareStatement(qry);
            st.executeUpdate(qry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult(String qry){
        try {
            PreparedStatement st = connection.prepareStatement(qry);
            return st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet query(String qry){
        ResultSet rs = null;
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
