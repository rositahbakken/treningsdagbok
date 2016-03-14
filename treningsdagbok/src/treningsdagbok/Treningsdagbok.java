package treningsdagbok;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.jcraft.jsch.Session;

import treningsdagbok.ConnOverSsh;

public class Treningsdagbok {
	
	static Connection myConn = null;
	static Session session = null;
	
	
	
	public void addWorkout() throws SQLException{
		
		Scanner input = new Scanner(System.in);
		System.out.print("Skriv inn dato på formen 'ÅÅÅÅ-MM-DD': ");
		if (!input.hasNext()){
			
		}
		String s_dato = input.nextLine();
		Date dato = java.sql.Date.valueOf(s_dato);
		//Typen til dato må kanskje endres her
		
		System.out.print("Skriv inn starttidspunkt på formen 'TT:MM:SS': ");
		String s_tidspunkt = input.nextLine(); //Typen til time må kanskje endres her
		Time tidspunkt = java.sql.Time.valueOf(s_tidspunkt);
		
		System.out.print("Skriv inn varighet i antall minutter: ");
		Integer t_varighet = input.nextInt();
		
		System.out.print("Skriv inn et notat til økten: ");
		input.nextLine();
		
		
		String notat = input.nextLine();
		
		System.out.print("Skriv inn formen du er i på en skala fra 1(dårlig) til 10(utmerket): ");
		Integer form = input.nextInt();
		
		System.out.print("Skriv inn prestasjonen din fra denne treningsøkta på en skala fra 1(dårlig) til 10(utmerket): ");
		Integer prestasjon = input.nextInt();
		String sql = "INSERT INTO Trening(dato, tidspunkt, t_varighet, notat, form, prestasjon)"
				+ "VALUES('"+dato+"','"+tidspunkt+"','"+t_varighet+"','"+notat+"','"+form+"','"+prestasjon+"')";
		
		Statement myStmt = myConn.createStatement();
		myStmt.executeUpdate(sql);
		
		System.out.println("Hvilke øvelser gjorde du? Skriv exit når du er ferdig.");
		String ovelse;
		do {
			ovelse = input.nextLine();
			if (ovelse.toLowerCase().equals("armhevinger")){
				System.out.print("Skriv inn antall sett: ");
				Integer sett = input.nextInt();
				input.nextLine();
				System.out.print("Skriv inn antall repetisjoner per sett: ");
				Integer repetisjoner = input.nextInt();
				input.nextLine();
				System.out.print("Skriv inn belastning i kg: ");
				String belastning = input.nextLine();
				belastning+="kg";
				String beskrivelse = ovelse.toLowerCase()+"-"+belastning+"-"+sett+"x"+repetisjoner;
				myStmt = myConn.createStatement();
				ResultSet rs = myStmt.executeQuery("SELECT COUNT(*) FROM Ovelse");
				rs.next();
				Integer ovelseID = rs.getInt("COUNT(*)")+1;
				ResultSet rs2 = myStmt.executeQuery("SELECT * FROM Ovelse");
				Boolean finnesAllerede = false;
				while (rs2.next()){
					if (rs2.getString("O_Beskrivelse").equals(beskrivelse)){
						System.out.println("Øvelsen finnes i systemet");
						finnesAllerede = true;
						break;
					}	
				}
				if (!finnesAllerede){
					String sqlovelse = "INSERT INTO Ovelse(OvelseID, O_Beskrivelse, Belastning, Repetisjoner, Antall_sett, Km, Varighet, A_navn)"
							+ "VALUES('"+ovelseID+"','"+beskrivelse+"','"+belastning+"','"+repetisjoner+"','"+sett+"','"+0+"','"+0+"','"+ovelse+"')";
					myStmt.executeUpdate(sqlovelse);
				}
				
	
				
			}
		} while (!ovelse.equals("exit"));
		System.out.println("programmet er avsluttet");
		input.close();
			
	}
	
	public void getWorkout(){
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:65429/annarbj_treningsdagbok", "annarbj_tdt4145", "tivoli");
			//2. Create statement
			Statement myStmt = myConn.createStatement();
			//3. Execute SQL query
			ResultSet myRs = myStmt.executeQuery("select * from Trening");
			//4. Process the result set
			
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}

}