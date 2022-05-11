import java.util.Scanner;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Code for HW7
 * This program reads sample HTML files and provides the building information.
 * Place the HTML files under the project folder (not under the src folder).
 * @author Alyssa Esquivel
 */
public class BuildingLookup
{  
	public static void main(String[] args) throws IOException 
	{

		//boolean testOffline = true; // works with the given html files.
		boolean testOffline = false; // works with the website.

		Scanner console = new Scanner(System.in);
		boolean more = true;
		while (more){
			System.out.println("Lookup by B)uilding, or Q)uit");
			String cmd = console.nextLine();

			if (cmd.equalsIgnoreCase("Q")){ // Q option is COMPLETE. Do not modify.
				more = false;
			}else if (cmd.equalsIgnoreCase("B")){
				System.out.println("Enter full building name:");
				String name = console.nextLine(); // stores the user input
				Scanner ins;
				String name2 = name.replace(' ', '+'); // replace space with "+"
				if(testOffline) { // if the program is the offline mode
					String inputFileName = name2 + ".html";
					FileReader inputFile = new FileReader(inputFileName);
					ins = new Scanner(inputFile);
				}else { // if the program is for the online mode
					String pageurl = "https://www.fpm.iastate.edu/maps/buildings/building.asp?building="+name2;
					URL u = new URL(pageurl);
					URLConnection connection = u.openConnection();

					InputStream inStream = connection.getInputStream();
					ins = new Scanner(inStream);
				}
				// Complete B menu below. You do not need to update the lines above.

				String abbr = ""; // store the official abbreviation
				String sqft = ""; // store total square feet
				String address = ""; // store address

				// detect if each line contains the relevant information (abbreviation, sqrt, address)
				// extract and print the information
				// Hint for cleaning up address: str.replace("xx","yy") replaces "xx" in String str with "yy"
				
					while(ins.hasNextLine()){
						 String newLine = ins.nextLine();
						 
						 if(newLine.contains("<b>Official abbreviation: ")){
                             String abbr1 = newLine.replace("<b>Official abbreviation: " , " ");
                             abbr = abbr1.replace("</b>", "");
                                 
                             }
                             else if(newLine.contains("total square feet")){      
                            	 int start_index = newLine.indexOf("<p>") + 3;
                                 int end_index = newLine.indexOf("</p>") - 18;
                                 sqft = newLine.substring(start_index, end_index);
                              
                             }
                             else if(newLine.contains("AMES")){
                            	 address = newLine.replace("<div>"," ").replace("</div>", "");
                             }
                         }	
		                                    	
				System.out.println("Building Name: " + name);
				System.out.println("Offical Abbreviation: " + abbr);
				System.out.println("Total Square Feet: " + sqft + " sqft");
				System.out.println("Address: " + address);
				System.out.println();
				ins.close();

			}else{
				System.out.println("Invalid choice!");
			}
		} // end while
		console.close();
	}
}