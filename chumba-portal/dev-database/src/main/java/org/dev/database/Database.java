package org.dev.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.Server;

/**
 */
public class Database 
{
    public static void main(String[] args) throws SQLException
    {
        Server server = Server.createTcpServer().start();

        try {

            // init schema
            Connection conn = DriverManager.getConnection("jdbc:h2:tcp://127.0.0.1/~/test", "sa", "");
            String query1 = "CREATE TABLE IF NOT EXISTS USER (" + "ID UUID PRIMARY KEY, " + "FIRST_NAME VARCHAR(25), "
                    + "LAST_NAME VARCHAR(25), " + "PHONE VARCHAR(15) " + ")";
            String query2 = "CREATE TABLE IF NOT EXISTS CREDENTIAL (ID UUID PRIMARY KEY, USERNAME VARCHAR(16), PASSWORD VARCHAR(16), ACTIVE BOOL )";
            conn.createStatement().executeUpdate(query1);
            conn.createStatement().executeUpdate(query2);
            conn.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("[Database] Press ENTER to exit:");
            String input = br.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Stopping database.");
        server.stop();
    }
}
