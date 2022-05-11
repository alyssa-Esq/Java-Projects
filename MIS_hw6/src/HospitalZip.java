import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/**
 * Code for HW6
 * @author Alyssa Esquivel
 */
public class HospitalZip {
	private ArrayList<Item> Zips_key;
	private ArrayList<Item> Hospitals_key;

	/**
	 * Complete the method.
	 * This method reads key/value pairs. Given the Scanner object "in", the method should read
	 * the input file. Write a while loop to read the text file attached ("iahospitals.txt"). 
	 * Tip1: You can read two lines in one iteration of the while loop. 
	 * The first and second lines are for hospital name and zip code, respectively. 
	 * Tip2: After reading all data, sort the two array lists of Item objects.
	 * 
	 * @param in: the scanner for reading the input
	 */
	public void read_file(Scanner in) {
		// Two array lists are created.
		Zips_key = new ArrayList<Item>(); // Zipcode is the key in this array list
		Hospitals_key = new ArrayList<Item>(); // Hospital name is the key in this array list
		
		// Step1: read hospital names and zip codes and insert them appropriately to the given array lists. 
		
		while(in.hasNextLine()) {
			String university = in.nextLine();
			String zip = in.nextLine();
			
			Zips_key.add(new Item(zip, university));
			Hospitals_key.add(new Item(university, zip));
		}
		
		
		// Step2: sort the array lists
		Collections.sort(Zips_key);
		Collections.sort(Hospitals_key);
		
	}
	

	/**
	 * Complete this method.
	 * This method looks up an item in the table and returns a hospital for the given zip code.
	 * 
	 * @param zip: zip code to search
	 * @return a hospital name for the given zip, or "No hospital in the zip" if zip does not have a hospital.
	 */
	public String lookup_by_zip(String zip) {
		// do binary search and return an appropriate value using the result of the binary search
		
		for(Item item: Zips_key) {
			if(item.getKey().equalsIgnoreCase(zip)) {
				return item.getValue();
			}
		}
		//this executes only if there wasn't a match
		return "No hospital in the zip";
		
	}

	/**
	 * Complete this method.
	 * This method looks up an item in the table and returns a zip code for the given hospital name.
	 * 
	 * @param hospital: hospital name to search
	 * @return the zip code of the given hospital, or "No such hospital exists" if no such hospital exists.
	 */
	public String lookup_by_hospital(String hospital) {
		// do binary search and return an appropriate value using the result of the binary search
		
		for(Item item: Hospitals_key) {
			if(item.getKey().equals(hospital)) {
				return item.getValue();
			}
		}
		
		//this executes only if there wasn't a match
		return "No such hospital exists";
		
	}
}
