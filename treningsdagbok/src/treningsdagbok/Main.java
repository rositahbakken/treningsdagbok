package treningsdagbok;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			ConnOverSsh sshConn = new ConnOverSsh();
			Treningsdagbok treningsdagbok = new Treningsdagbok();
			//1. Get a connection to database
			ConnectionSession connSess = sshConn.openConnection();
			treningsdagbok.myConn = connSess.getConnection();
			treningsdagbok.session = connSess.getSession();
			try{
				treningsdagbok.addWorkout();
			}
			catch (Exception exc){
				exc.printStackTrace();
			}
			finally{
				sshConn.closeConnection(connSess);
			}
		
		}
		catch (Exception exc){
			exc.printStackTrace();
		}

	}
}
