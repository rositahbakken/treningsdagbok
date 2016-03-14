package treningsdagbok;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.sql.Connection;
import treningsdagbok.ConnectionSession;


public class ConnOverSsh {

	public ConnectionSession openConnection() throws SQLException{
		int lport = 52682;
		String rhost = "arctos";
		String host = "login.stud.ntnu.no";
		int rport = 3306;
		String user = "annarbj";
		String password = "";
		String dbuserName = "annarbj_tdt4145";
		String dbpassword = "tivoli";
		String url= "jdbc:mysql://localhost:"+lport+"/annarbj_treningsdagbok";
		String driverName = "com.mysql.jdbc.Driver";
		ConnectionSession connSess= new ConnectionSession();
		Session session = null;
		
		try{
			//Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
			System.out.println("Passord: ");
			Scanner input = new Scanner(System.in);
		    password = input.nextLine();
		    //input.close(); 
		    java.util.Properties config = new java.util.Properties(); 
	        config.put("StrictHostKeyChecking", "no");
	        JSch jsch = new JSch();
	        session=jsch.getSession(user, host, 22);
	        session.setPassword(password);
	        session.setConfig(config);
	        session.connect();
	        password = "";
	        System.out.println("Connected");
	        int assinged_port=session.setPortForwardingL(lport, rhost, rport);
	        System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
	        System.out.println("Port Forwarded");
	        
	       
	        Class.forName(driverName).newInstance();
	        Connection conn = DriverManager.getConnection (url, dbuserName, dbpassword);
	        System.out.println ("Database connection established");
	        System.out.println("DONE\n");
	        
			connSess.setConn(conn);
			connSess.setSession(session);
	    }
		catch(Exception e){
	        e.printStackTrace();
		}
		return connSess;
	}
	
	public void closeConnection(ConnectionSession connSess) throws SQLException{
		Connection conn = connSess.getConnection();
		Session session = connSess.getSession();
        if(conn != null && !conn.isClosed()){
        	System.out.println("\n"+"Closing Database Connection");
            conn.close();
        }
        if(session !=null && session.isConnected()){
            System.out.println("Closing SSH Connection");
            session.disconnect();
        }
    }

}