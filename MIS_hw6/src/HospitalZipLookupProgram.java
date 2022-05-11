import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

/* The input file has the format

Unitypoint Health Marshalltown
50158
St Anthony Regional Hospital & Nursing Home
51401
Unitypoint Health Keokuk
52632
. . .
*/
public class HospitalZipLookupProgram
{
   public static void main(String[] args) throws IOException
   {
      Scanner in = new Scanner(System.in);
 
      HospitalZip table = new HospitalZip();
      FileReader reader = new FileReader("iahospitals.txt");
      table.read_file(new Scanner(reader));
      
      boolean more = true;
      while (more)
      {  
         System.out.print("Lookup by (Z)ip, (H)ospital, (Q)uit? ");
         String cmd = in.nextLine();
           
         if (cmd.equalsIgnoreCase("Q")) 
            more = false;
         else if (cmd.equalsIgnoreCase("Z"))
         { 
            System.out.print("Enter Zipcode: ");
            String z = in.nextLine();
            System.out.println("Hospital in zip " + z + " = " + table.lookup_by_zip(z) + "\n");
         }
         else if (cmd.equalsIgnoreCase("H"))
         { 
            System.out.print("Enter Hospital: ");
            String hospital = in.nextLine();
            
            System.out.println("Zip of " + hospital + " = " + table.lookup_by_hospital(hospital.toUpperCase())+ "\n");
         }
      }
      in.close();
   }
}