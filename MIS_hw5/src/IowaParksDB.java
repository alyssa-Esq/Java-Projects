import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;

/**
 * Code for HW5
 * This class process iowaparks.csv
 * @author Alyssa Esquivel
 */
public class IowaParksDB {
public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException{
		
	    SimpleDataSource.init("database.properties");
	    
	    try(Connection conn = SimpleDataSource.getConnection();Statement stat = conn.createStatement()) 
	    {
	    	try{
	           stat.execute("DROP TABLE Parks"); // if the table exists, drop it. 
	                                             // This makes sure that you do not have the existing table
	        }
	        catch(SQLException e){
	              // Catch exception if table doesn't exist yet. 
	        	  // But, do nothing (it was thrown because the table does not exist, and you will create one in the next line).
	        }  
	    	stat.execute("CREATE TABLE Parks (FeatureID VARCHAR(7), FeatureName VARCHAR(30), Elevation DECIMAL(8,2), USGScity VARCHAR(20), Lat Decimal(9,7), Lng Decimal(9,7))");
	    
	    	Scanner in = new Scanner(new File("iowaparks.csv")); // Reading "iowaparks.csv"
	    	in.nextLine(); // skip header (column names)
            while (in.hasNextLine()){ // reading the csv file line by line
				
				String line = in.nextLine();
				String[] linewords = line.split(","); // CSV file separates values by commas. The line is partitioned into multiple values (each value from one cell)
				
				String featid = linewords[0];
				String featname = linewords[1];
				String elev = linewords[2];
				String city = linewords[3];
				String lat = linewords[4];
				String lng = linewords[5];

				// Prepare the SQL statement in String format to insert the current house
				String query = "INSERT INTO Parks VALUES ('" + featid + "','" + featname + "'," + elev + ",'" + city + "'," + lat + "," + lng + ")";
				stat.execute(query);
			}

            in.close();
	    	
            // The lines above read the CSV file and create Parks table.
            // The lines above are complete. You do not need to change.
            
            System.out.println("##############################################################");
    		System.out.println("########             Iowa Parks DB Program            ########");
    		System.out.println("##############################################################");
            
            Scanner console = new Scanner(System.in);
            boolean continueProgram = true; // this value becomes false when the user selects Q option
            while(continueProgram){
          	  System.out.println("Select from the following options");
          	  System.out.println("(Q) Quit");
          	  System.out.println("(A) Add a park");
          	  System.out.println("(C) Calculate average elevation");
          	  System.out.println("(I) Search by FeatureID");
          	  System.out.println("(N) Print a subset of the schools based on name");
          	  String select = console.next();
          	  if(select.equals("Q")){
          		  continueProgram = false;
          	  }else if(select.equals("A")){ // Complete A option
          		  System.out.println("[Message] Use \"_\" to replace space.\n");
          		  System.out.print("ID: ");
          		  String id = "'" + console.next() + "'"; // read it as one word using "_"
          		  id = id.replace("_"," "); // replace "_" with " "
          		  System.out.print("Name: ");
          		  String name = "'" + console.next() + "'"; // read it as one word using "_"
          		  name = name.replace("_"," "); // replace "_" with " "
          		  System.out.print("elevation: ");
          		  String elev = console.next(); 
          		  System.out.print("city: ");
          		  String city = "'" + console.next() + "'"; // read it as one word using "_"
          		  city = city.replace("_"," "); // replace "_" with " "
          		  System.out.print("lat: ");
        		  String lat = console.next(); 
        		  System.out.print("lng: ");
        		  String lng = console.next(); 
        		  // HINT: Note that the String variables already includes quotes. Hence, you do not need to add quotes when preparing your query.
        		  
        		  //Parks.insertData(id, name, elev, city, lat, lng);
        		  String query = "INSERT INTO Parks VALUES(' " + id + "'," + elev + ",'" + city + "'," + lat + "," + lng + " ')";
        		  stat.execute(query);
        		  
       	    	 	
          	  }else if(select.equals("C")){ // Complete C option
          		  
          		  String query = "SELECT AVG(Elevation) FROM Parks";
          		  ResultSet result = stat.executeQuery(query);
          		  result.next();
        		  System.out.println("Average elevation of all parks: " + result.getString(1));


          	  }else if(select.equals("I")){ // Complete I option
        		  System.out.print("Enter FeatureID:");
        		  String featid = console.next();
        		  
        		  //searches by featureID
        		  /**
        		  String query = "SELECT * FROM Parks where id = 'featid'";
        		  ResultSet result = stat.execute(query);
        		  
        		  System.out.println(result.getString(1), result.getString(2), result.getString(3), result.getString(4));
        		  **/
        		  String query = "SELECT * FROM Parks WHERE id = featid";
        		  ResultSet result = stat.execute(query);
        		  
        		  while(result.next()) {
        			  System.out.println(result.getString(1), result.getString(2), result.getString(3), result.getString(4));
        		  }

        		  
        	  }else if(select.equals("N")){ // Complete N option
  				  System.out.print("Enter pattern:");
        		  String pattern = console.next();
        		  
        		  /**
        		  //prints a subset of the parks based on name
        		  String query = "SELECT * FROM Parks WHERE FeatureName LIKE ?";
        		  PreparedStatement stat = conn.prepareStatement(query);
        		  stat.setString(1, pattern);
        		  ResultSet result = stat.executeQuery(query);
        		  
        		  while(result.next()) {
        			  for(int i = 0; i > "FeatureName".length(); i++) {
        				  
        			  }
        		  }
        		  **/
        		  
        		  String query =  "SELECT * FROM Parks WHERE name LIKE pattern";
        		  ResultSet result = stat.executeQuery();
        		  
        		  while(result.next()) {
        			  System.out.println(result.getString(1), result.getString(2), result.getString(3), result.getString(4));
        		  }
        		  
        		  
      	      }else { // COMPLETE. No need to change. If not Q,A,C,I, or N, then print error message.
      	    	  System.out.println("Incorrect menu. Select again.");
      	      }
          	  
          	  System.out.println();
          	  System.out.println();
            }
            
            console.close();
	    }
		
		
	}

}