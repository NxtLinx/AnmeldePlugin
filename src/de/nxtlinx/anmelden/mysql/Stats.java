package de.nxtlinx.anmelden.mysql;

import de.nxtlinx.anmelden.Anmelden;

import javax.crypto.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Stats {

    private static MySQL mySQL = Anmelden.getPlugin().getMySQL();

    public static boolean playerExists(UUID uuid){
        try {
            ResultSet rs = mySQL.query("SELECT * FROM anmelden WHERE uuid= '"+uuid.toString()+"'");
            if (rs.next()){
                return rs.getString("uuid") != null;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createPlayer(UUID uuid, String playername, int id,String rang,String password,String username){
        if (!playerExists(uuid)){
            mySQL.update("INSERT INTO anmelden (uuid,playername,id,rang,password,username) VALUES ('"+uuid.toString()+"','"+playername+"','"+id+"','"+rang+"','"+password+"','"+username+"');");
        }
    }

    public static void createPlayerSave(UUID uuid, String playername, int id,String rang,String password,String username){
        if (!playerExists(uuid)){
            mySQL.update("INSERT INTO anmelden (uuid,playername,id,rang,password,username) VALUES ('"+uuid.toString()+"','"+playername+"','"+id+"','"+rang+"',AES_ENCRYPT('"+password+"','AES'),'"+username+"');");
        }
    }

    public static boolean usernameExists(String username){
        try {
            ResultSet rs = mySQL.query("SELECT * FROM anmelden WHERE username= '"+username+"'");
            if (rs.next()){
                return rs.getString("anmelden") != null;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getPassword(UUID uuid){
        if (playerExists(uuid)) {
            try {
                ResultSet rs = mySQL.query("SELECT password FROM anmelden WHERE uuid = '"+uuid.toString()+"'");
                if ((!rs.next()) || (rs.getString("password")) == null);
                return rs.getString("password");
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static String getPasswordSave(UUID uuid){
        if (playerExists(uuid)) {
            try {
                ResultSet rs = mySQL.query("SELECT AES_DECRYPT(password, 'AES') FROM anmelden WHERE uuid = '"+uuid.toString()+"'");
                if ((!rs.next()) || (rs.getString("password")) == null);
                return rs.getString("password");
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static void setPassword(UUID uuid,String password){
        if (playerExists(uuid)) {
                mySQL.update("UPDATE anmelden SET password = '"+password+"' WHERE uuid = '"+uuid.toString()+"';");
            }
    }

    public static void setPasswordSave(UUID uuid,String password){
        if (playerExists(uuid)) {
            mySQL.update("UPDATE anmelden SET password = AES_ENCRYPT('"+password+"','AES') WHERE uuid = '"+uuid.toString()+"';");
        }
    }




    }

