package com.coin_collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Coin> coin = new ArrayList<Coin>();
		List<String> status = new ArrayList<String>();
		CoinOperations op = new CoinOperations();
		
		loadDatabase(coin,status);
		int choice = -1;
		do {
			System.out.println("\n1. Add New Coin\n2. Display All Coins\n3. Search Coins\n4. Add Bulk Data\n5. Delete Coin Data\n6. Update Coin Data\n0. To Exit\nEnter your choice: ");
			choice = sc.nextInt();
			
			switch(choice) {
			case 1:{
				op.addNewCoin(coin,status);
				break;
			 }
			
			case 2:{
				for(Coin c:coin) {
					System.out.println(c);
				}
				break;
			 }
			
			case 3:{
				op.searchCoins(coin,status);
				break;
			 }
			
			case 4:{
				try {
					File f = new File("coin.csv");
					Scanner sc1 = new Scanner(f);
					
					while(sc1.hasNext()){
						String line = sc1.nextLine();
						String value[] = line.split(",");
						String country = value[0];
						String denomination = value[1];
						int yearOfMinting = Integer.parseInt(value[2]);
						double currentValue = Double.parseDouble(value[3]);
						LocalDate acquiredDate = LocalDate.parse(value[4]);
						coin.add(new Coin(country,denomination,yearOfMinting,currentValue,acquiredDate));
						status.add("new");
					}
				} catch (FileNotFoundException e) {	
					e.printStackTrace();
				}
				
					break;
			 }
			
			case 5:{
				op.deleteCoinData(coin,status);
				break;
			 }
			
			case 6:{
				op.updateCoinData(coin,status);
				break;
			}
			
			case 0:{
				writeDatabase(coin,status);
				System.out.println("Thank You!");
				break;
			}
			
			default:{
				System.out.println("Invalid Choice!");
			}		
		  }
			
		}while(choice!=0);
		sc.close();
	}

	private static void loadDatabase(List<Coin> coin, List<String> status) {
		try {
			// Step 1: Load Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Step 2: Connect with database
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/operation","root","WAP10@iq");
			
			String query = "select * from coin";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String country = rs.getString("country");
				String denomination =rs.getString("denomination");
				int yearOfMinting = rs.getInt("yearOfMinting");
				double currentValue = rs.getDouble("currentValue");
				LocalDate acquiredDate = LocalDate.parse(rs.getString("acquiredDate"));	
				coin.add(new Coin(country,denomination,yearOfMinting,currentValue,acquiredDate));
				status.add("old");
			}
			con.close();
	  } catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	  }
		
	}
	
	private static void writeDatabase(List<Coin> coin,List<String> status) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/operation";
			String user = "root";
			String pwd = "WAP10@iq";
			Connection con = DriverManager.getConnection(url,user,pwd);
			String query;
								
			for(int i = 0;i<coin.size();i++) {
				Coin c = coin.get(i);
				if(status.get(i).equals("new")) {
					query = "insert into coin values(?,?,?,?,?)";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1,c.getCountry());
					ps.setString(2,c.getDenomination());
					ps.setInt(3,c.getYearOfMinting());
					ps.setDouble(4,c.getCurrentValue());
					ps.setString(5, c.getAcquiredDate().toString());
					ps.executeUpdate();	
				}
				
				if(status.get(i).equals("up")) {
					query = "update coin set currentValue = ? where country = ? and denomination = ? and yearOfMinting = ?";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setDouble(1,c.getCurrentValue());
					ps.setString(2,c.getCountry());
					ps.setString(3,c.getDenomination());
					ps.setInt(4,c.getYearOfMinting());
					ps.executeUpdate();	
				}
				
				if(status.get(i).equals("del1")) {
					query = "delete from coin where country = ? and denomination = ?";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1,c.getCountry());
					ps.setString(2,c.getDenomination());
					coin.remove(i);
					status.remove(i);
					ps.executeUpdate();	
				}
				else if(status.get(i).equals("del2")) {
					query = "delete from coin where country = ? and denomination = ? and yearOfMinting = ?";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1,c.getCountry());
					ps.setString(2,c.getDenomination());
					ps.setInt(3, c.getYearOfMinting());
					System.out.println(c.getCountry()+" "+c.getDenomination()+" "+c.getYearOfMinting()+" removed!");
					coin.remove(i);
					status.remove(i);
					ps.executeUpdate();			
				}else if(status.get(i).equals("del3")) {
					query = "delete from coin where country = ?";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1,c.getCountry());
					coin.remove(i);
					status.remove(i);
					ps.executeUpdate();			
				}else if(status.get(i).equals("del4")) {
					query = "delete from coin where yearOfMinting = ?";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setInt(1,c.getYearOfMinting());
					coin.remove(i);
					status.remove(i);
					ps.executeUpdate();			
				}
				
				
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

}
