package treningsdagbok;

import java.sql.Connection;

import com.jcraft.jsch.Session;

public class ConnectionSession {
	
	Connection conn = null;
	Session session = null;
	
	public void setConn(Connection conn){
		this.conn = conn;
	}
	
	public void setSession(Session session){
		this.session = session;
	}
	
	public Session getSession(){
		return session;
	}
	
	public Connection getConnection(){
		return conn;
	}


}
