package com.coin_collection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CoinOperations{
	 Scanner sc = new Scanner(System.in);
	public void addNewCoin(List<Coin> coin, List<String> status) {
		
		System.out.println("\n--- Add New Coin ---");
        
        System.out.print("Country: ");
        String country = sc.nextLine();
        System.out.print("Denomination: ");
        String denomination = sc.nextLine();
        
        System.out.println("Year of Minting: ");
        int year = sc.nextInt();
        
        System.out.println("Current Value: ");
        double currentValue = sc.nextDouble();
        sc.nextLine();
        System.out.println("Acquired Date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        coin.add(new Coin(country, denomination, year, currentValue, LocalDate.parse(dateInput,formatter)));
        status.add("new");
        System.out.println("New Coin Added Successfully!");
       
	}

	public void searchCoins(List<Coin> coin, List<String> status) {
			System.out.println("\n--- CREATE LIST ---");
            System.out.println("1. Create list by Country");
            System.out.println("2. Create list by Year of Minting");
            System.out.println("3. Create list by Current Value (Sorted)");
		    System.out.println("\n--- Search Coins ---");
	        System.out.println("4. Country + Denomination");
	        System.out.println("5. Country + Year of Minting");
	        System.out.println("6. Country + Denomination + Year");
	        System.out.println("7. Acquired Date + Country");
	        System.out.println("Enter your choice: ");
	        int choice = sc.nextInt();
	        List<Coin> result = null;
	        
	        switch(choice) {
	         case 1:{
	        	createListByCountry(coin);
	        	break;
	         }
	         
	         case 2:{
	        	 createListByYearOfMinting(coin);
	        	 break;
	         }
	         
	         case 3:{
	        	 createListByCurrentValue(coin);
	        	 break;
	         }
	         
	         case 4:{
	        	  result = searchByCountryAndDenomination(coin);   
	              break;
	         }
	         
	         case 5:{
	        	  result = searchByCountryAndYearOfMinting(coin);
	        	  break;
	         } 
	         
	         case 6:{
	        	  result = searchByCountryDenominationAndYearofMinting(coin);
		          break;
		     } 
	         
	         case 7:{
			      result = searchByAcquiredDateAndCountry(coin);   	
			      break;
			 }
	         
	         default:
	        	 System.out.println("Invalid Choice!");
	        }
	        
	        if(result!=null) {
	        	if (result.isEmpty()) {
	                System.out.println("No coins found.");
	            } 
	        	for(int i = 0;i<result.size();i++) {
	        		System.out.println((i + 1) + ". " + result.get(i));
	        	}
	        }

	}

	private void createListByCurrentValue(List<Coin> coin) {
		if(coin.isEmpty()) {
			System.out.println("No coins in collection");
			return;
		}
		
		TreeSet<Coin> sortedCoins = null;
		
		 System.out.println("\nSort order:");
		 System.out.println("1. Highest to Lowest");
		 System.out.println("2. Lowest to Highest");
		 System.out.print("Enter choice: ");
		 int Choice = sc.nextInt();
		 
		 if(Choice == 1) {
			 sortedCoins = new TreeSet<>(new Comparator<Coin>() {
				public int compare(Coin c1,Coin c2) {
					int valueCompare = Double.compare(c2.getCurrentValue(),c1.getCurrentValue());
					if(valueCompare!=0) {
						return valueCompare;
					}
					
					int countryCompare = c1.getCountry().compareTo(c2.getCountry());
					if(countryCompare!=0) {
						return countryCompare;
					}
					
					return c1.getDenomination().compareTo(c2.getDenomination());
				}
			});
			System.out.println("Coins sorted by Current Value (Highest to Lowest)");
		 }
		 else if(Choice == 2) {
			 sortedCoins = new TreeSet<>(new Comparator<Coin>() {
					public int compare(Coin c1,Coin c2) {
						int valueCompare = Double.compare(c1.getCurrentValue(),c2.getCurrentValue());
						if(valueCompare!=0) {
							return valueCompare;
						}
						
						int countryCompare = c1.getCountry().compareTo(c2.getCountry());
						if(countryCompare!=0) {
							return countryCompare;
						}
						
						return c1.getDenomination().compareTo(c2.getDenomination());
					}
			});
				System.out.println("Coins sorted by Current Value (Lowest to Highest)");
		 }
		 else {
			 System.out.println("Invalid Choice!");
			 return;
		 }
		 
		 sortedCoins.addAll(coin);
		 System.out.println("========================================");
		    System.out.println("Total coins: " + sortedCoins.size());
		    System.out.println("========================================\n");
		    
		    int count = 1;
		    for(Coin c : sortedCoins) {
		        System.out.println(count + ". " + c);
		        count++;
		    }
		 
	}

	private void createListByYearOfMinting(List<Coin> coin) {
		System.out.print("Enter year of minting: ");
        int yearOfMinting = sc.nextInt();
        
		List<Coin> filteredCoins = new ArrayList<Coin>();
		
		for(Coin c : coin) {
            if(c.getYearOfMinting() == yearOfMinting) {
                filteredCoins.add(c);
            }
        }
		
		displayResults("Coins from " + yearOfMinting, filteredCoins);
	}

	private void createListByCountry(List<Coin> coin) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter country: ");
        String country = sc.nextLine();
        sc.nextLine();
		List<Coin> filteredCoins = new ArrayList<Coin>();
		
		for(Coin c : coin) {
            if(c.getCountry().equalsIgnoreCase(country)) {
                filteredCoins.add(c);
            }
        }
		
		displayResults("Coins from " + country, filteredCoins);
			
	}

	private void displayResults(String country, List<Coin> filteredCoins) {
		 System.out.println("\n========================================");
	     System.out.println(country);
	     System.out.println("========================================");
	     
	     if(filteredCoins.isEmpty()) {
	    	 System.out.println("No coins found!");
	     }
	     else {
	    	 System.out.println("Total coins found: " + filteredCoins.size());
	         System.out.println("========================================\n");
	            
	          int count = 1;
	          for(Coin c : filteredCoins) {
	                System.out.println(count + ". " + c);
	                count++;
	          }
	     }
		
	}

	private List<Coin> searchByAcquiredDateAndCountry(List<Coin> coin) {
		sc.nextLine();
		System.out.print("Enter country: ");
        String country = sc.nextLine();
        sc.nextLine();
		System.out.println("Enter acquired date");
		String input = sc.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate acquiredDate = LocalDate.parse(input,formatter);
		 
		List<Coin> result = coin.stream().filter(c -> c.getAcquiredDate().equals(acquiredDate) && c.getCountry().equalsIgnoreCase(country)).collect(Collectors.toList());
		return result;
	}

	private List<Coin> searchByCountryDenominationAndYearofMinting(List<Coin> coin) {
		sc.nextLine();
		System.out.print("Enter country: ");
        String country = sc.nextLine();
        sc.nextLine();
        System.out.print("Enter denomination: ");
        String denomination = sc.nextLine();
        System.out.print("Enter year of minting: ");
        int yearOfMinting = sc.nextInt();
        List<Coin> result = coin.stream().filter(c -> c.getCountry().equalsIgnoreCase(country) && c.getYearOfMinting() == yearOfMinting && c.getDenomination().equalsIgnoreCase(denomination)).collect(Collectors.toList());        	
        return result;
	}

	private List<Coin> searchByCountryAndYearOfMinting(List<Coin> coin) {
		sc.nextLine();
		System.out.print("Enter country: ");
        String country = sc.nextLine();
        System.out.print("Enter year of minting: ");
        int yearOfMinting = sc.nextInt();
        List<Coin> result = coin.stream().filter(c -> c.getCountry().equalsIgnoreCase(country) && c.getYearOfMinting() == yearOfMinting ).collect(Collectors.toList());        	
        return result;	
	}

	private List<Coin> searchByCountryAndDenomination(List<Coin> coin) {
		sc.nextLine();
		System.out.print("Enter country: ");
        String country = sc.nextLine();
        sc.nextLine();
        System.out.print("Enter denomination: ");
        String denomination = sc.nextLine();
        System.out.println(country+" "+denomination);
        List<Coin> result = coin.stream().filter(c -> c.getCountry().equalsIgnoreCase(country) && c.getDenomination().equalsIgnoreCase(denomination)).collect(Collectors.toList());        	
        return result;		
	}

	public void deleteCoinData(List<Coin> coin,List<String> status) {
		if(coin.isEmpty()) {
			 System.out.println("No coins in collection to delete.");
		     return;
		}
		int choice = -1;
		
			 System.out.println("\n=== DELETE Coin Data ===");
		        System.out.println("1. Delete by Country + Denomination");
		        System.out.println("2. Delete by Country + Denomination + Year");
		        System.out.println("3. Delete all coins from a Country");
		        System.out.println("4. Delete all coins from a Year");
		        System.out.println("0. To Exit");
		        System.out.print("\nEnter choice: ");
		        choice = sc.nextInt();
		        
		        switch(choice) {
		        case 1:{
		        	deleteByCountryAndDenomination(coin, sc,status);
		        	break;
		        }
		        case 2:{
		        	deleteByCountryDenominationYear(coin, sc,status);
		        	break;
		        }
		        case 3:{
		        	 deleteAllByCountry(coin, sc,status);
		        	break;
		        }
		        case 4:{
		        	deleteAllByYear(coin, sc,status);
		        	break;
		        }
		       
		        default:{
		        	System.out.println("Invalid Choice!");
		        }
		       }
		        

		
	}

	private void deleteAllByYear(List<Coin> coin, Scanner sc2,List<String> status) {
		System.out.print("Enter year of minting: ");
        int yearOfMinting = sc2.nextInt();
        System.out.println(yearOfMinting);
        for(int i = 0;i<coin.size();i++) {
        	if(coin.get(i).getYearOfMinting() == yearOfMinting) {
        		status.set(i,"del4");
        	}
        }
        System.out.println("Coin date removed successfully!");
		
	}

	private void deleteAllByCountry(List<Coin> coin, Scanner sc2,List<String> status) {
		sc2.nextLine();
		System.out.print("Enter country: ");
        String country = sc2.nextLine();
        System.out.println(country);
        for(int i = 0;i<coin.size();i++) {
        	if(coin.get(i).getCountry().equalsIgnoreCase(country)) {
        		status.set(i,"del3");
        	}
        }
        System.out.println("Coin date removed successfully!");
		
	}

	private void deleteByCountryDenominationYear(List<Coin> coin, Scanner sc2,List<String> status) {
		sc2.nextLine();
		System.out.print("Enter country: ");
        String country = sc2.nextLine();
        sc2.nextLine();
        System.out.print("Enter denomination: ");
        String denomination = sc2.nextLine();
        System.out.println("Enter year of minting: ");
        int yearOfMinting = sc2.nextInt();
        System.out.println(country+" "+denomination+" "+yearOfMinting);
        for(int i = 0;i<coin.size();i++) {
        	if(coin.get(i).getCountry().equalsIgnoreCase(country) && coin.get(i).getDenomination().equalsIgnoreCase(denomination) && coin.get(i).getYearOfMinting() == yearOfMinting) {
        		status.set(i,"del2");
        	}
        }
        System.out.println("Coin date removed successfully!");
	}

	private void deleteByCountryAndDenomination(List<Coin> coin, Scanner sc2,List<String> status) {
		sc2.nextLine();
		System.out.print("Enter country: ");
        String country = sc2.nextLine();
        sc2.nextLine();
        System.out.print("Enter denomination: ");
        String denomination = sc2.nextLine();
        System.out.println(country+" "+denomination);
        for(int i = 0;i<coin.size();i++) {
        	if(coin.get(i).getCountry().equalsIgnoreCase(country) && coin.get(i).getDenomination().equalsIgnoreCase(denomination)) {
        		status.set(i,"del1");
        		
        	}
        }
        
        System.out.println("Coin date removed successfully!");
		
	}

	public void updateCoinData(List<Coin> coin, List<String> status) {
			sc.nextLine();
			System.out.print("Enter country: ");
	        String country = sc.nextLine();
	        sc.nextLine();
	        System.out.print("Enter denomination: ");
	        String denomination = sc.nextLine();
	        System.out.println("Enter year of minting: ");
	        int yearOfMinting = sc.nextInt();
	        
	        System.out.println(country+" "+denomination+" "+yearOfMinting);
	        
	        for(int i = 0;i<coin.size();i++) {
	        	if(coin.get(i).getCountry().equalsIgnoreCase(country) && coin.get(i).getDenomination().equalsIgnoreCase(denomination) && coin.get(i).getYearOfMinting() == yearOfMinting) {
	        		System.out.println("Enter updated current value: ");
	        		double currentValue = sc.nextDouble();
	        		coin.get(i).setCurrentValue(currentValue);
	        		status.set(i,"up");
	        	}
	        }
	        System.out.println("Coin data updated successfully!");
	}

}
