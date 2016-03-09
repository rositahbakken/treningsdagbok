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


public class ConnOverSsh {

	public static void main(String[] args) throws SQLException{
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
		Connection conn = null;
		Session session = null;
		
		try{
			//Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
			System.out.println("Passord: ");
			Scanner scanIn = new Scanner(System.in);
		    password = scanIn.nextLine();
		    scanIn.close(); 
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
	        
	        //mysql database connectivity
	        Class.forName(driverName).newInstance();
	        conn = DriverManager.getConnection (url, dbuserName, dbpassword);
	        System.out.println ("Database connection established");
	        System.out.println("DONE\n");
	        
	        Statement myStmt = conn.createStatement();
			//3. Execute SQL query
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM Aktivitet");
			//4. Process the result set
			System.out.println("Aktiviteter i tabellen: ");
			while (myRs.next()) {
				System.out.println(myRs.getString("A_Navn"));
				}
			
	    }
		catch(Exception e){
	        e.printStackTrace();
		}
		finally{
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
}