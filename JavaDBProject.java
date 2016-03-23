package javadbproject;
import java.sql.*;
import java.util.Scanner;

public class JavaDBProject {
   //Connect to DB
   static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";  
   static final String DB_URL = "jdbc:derby://localhost:1527/DBRecordingGroup";
   static final String USER = "sergio";
   static final String PASS = "sergio";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      
      //Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      
      System.out.println("----------------");
      System.out.println("|              |");
      System.out.println("|    ALBUM     |");
      System.out.println("|    GROUP     |");
      System.out.println("|  STUDIO DB   |");
      System.out.println("|              |");
      System.out.println("----------------");
      
      //Initialize our querying variable
      boolean querying = true;
     
      try (Scanner scan = new Scanner(System.in)) {
           while(querying)
           {
               System.out.println("----------------------------------------------------------");
               System.out.println("MENU");
               System.out.println("1. List all album titles");
               System.out.println("2. List all the data for an album");
               System.out.println("3. Insert new ablum");
               System.out.println("4. Insert new studio");
               System.out.println("5. Remove an album");
               System.out.println("6. Exit");
               System.out.println();
               
               System.out.print("Choice: ");
               //Get the input;
               String input = scan.nextLine();
               
               //Try to get the value
               int answer;
               try
               {
                   answer = Integer.valueOf(input);
               }
               catch(Exception e)
               {
                   answer = -1;
               }
               //check input
               if(answer == -1 || answer < 1 || answer > 6){
                   System.out.println("Enter a number 1-6");
               }else if(answer == 1){
                   //Get all albulm titles, execute the sql
                   String sql = "SELECT atitle FROM album";
                   //Loop through result set
                   int count = 0;
                   try (ResultSet rs = stmt.executeQuery(sql)) {
                       //Simply loop through the result set for the albulms
                       System.out.println("Album Titles:");
                       while(rs.next()){
                           //Retrieve by column name
                           String title = rs.getString("atitle");
                           
                           //Display values
                           //System.out.print(title);
                           count++;
                           System.out.print(String.format("%-2d. %-30s", count,title));
                           
                           if((count%5) == 0){
                               System.out.println();
                           }
                           
                       }
                       
                       //Close the result set
                       rs.close();
                   }
                    catch(Exception e) {
                          //Handle errors for Class.forName
                          e.printStackTrace();

                          //Inform user of error
                          System.out.println("Error! Could Not get result from the Database");
                     }
               }else if(answer == 2){
                   System.out.println("------------------------------------------");
                   System.out.println("DATA FOR SPECIFIC ALBUM");
                   //List all the data for a specified album
                   System.out.print("Enter the title of the album:");
                   
                   //Get the albulm title
                   input = scan.nextLine();
                   
                   //Query the DB
                   String sql = "SELECT * FROM album WHERE LOWER(atitle) LIKE LOWER('%" + input + "%')";
                   ResultSet rs = stmt.executeQuery(sql);
                   
                   if(rs.next())
                   {
                       //Retrieve by column name
                       String title = rs.getString("atitle");
                       String group = rs.getString("gname");
                       String studioName = rs.getString("sname");
                       String date = rs.getString("adaterec");
                       String length = rs.getString("alength");
                       String numSongs = rs.getString("anumofsongs");
                       
                       //Display values
                       System.out.print("Title: ");
                       System.out.println(title);
                       System.out.print("Group: ");
                       System.out.println(group);
                       System.out.print("Studio: ");
                       System.out.println(studioName);
                       System.out.print("Date Recorded: ");
                       System.out.println(date);
                       System.out.print("Length: ");
                       System.out.println(length);
                       System.out.print("Number Of Songs: ");
                       System.out.println(numSongs);
                   }
                   else
                   {
                       System.out.println("That album does not exist...");
                   }
                   
                   rs.close();
                   System.out.println("\nPress Enter to Continue");
                   System.in.read();
               }
               else if(answer == 3)
               {
                   System.out.println("--------------------------------------------");
                   System.out.println("INSERT NEW ALBUM INTO DATABASE\n");
                   //Insert a new albulm
                   String title = "";
                   String groupName = "";
                   String studioName = "";
                   String dateRec = "";
                   String aLength = "";
                   int numSongs = 0;
                   
                   //Get the albulm title
                   System.out.print("Enter a name for the album: ");
                   title = scan.nextLine();
                   
                   //Please select an artist for the album
                   boolean getGroup = true;
                   while(getGroup)
                   {
                       System.out.println("Choose a recording group for " +title + "!");
                       String sql = "SELECT gname FROM recgroup";
                       //Simply loop through the result set for the albulms
                       try (ResultSet rs = stmt.executeQuery(sql)) {
                           //Simply loop through the result set for the albulms
                           System.out.println("Recording Groups:");
                           int count = 0;
                           while(rs.next()){
                               //Retrieve by column name
                               String groups = rs.getString("gname");
                               count++;
                               //Display values
                               System.out.print(String.format("%-2d. %-30s",count,groups));
                               if((count%5)==0){
                                   System.out.println();
                               }
                           }
                           System.out.println();
                           //Close the result set
                           rs.close();
                       }
                       catch(Exception e) {
                          //Handle errors for Class.forName
                          e.printStackTrace();

                          //Inform user of error
                          System.out.println("Error! Could Not get result from the Database");
                        }
                       
                       //Get the input
                       System.out.print("Recording Group Choice: ");
                       groupName = scan.nextLine();
                       
                       //Check if it is a valid group
                       //Query the DB
                       String sqlCheck = "SELECT gname FROM recgroup WHERE LOWER(gname) LIKE LOWER('%" + groupName + "%')";
                       try (ResultSet rsCheck = stmt.executeQuery(sqlCheck)) {
                           if(rsCheck.next())
                           {
                               //Good it exists!
                               getGroup = false;
                           }
                           else
                           {
                               //Not a valid group
                               System.out.println("Group does not exist, please select an available group");
                               System.out.println();
                           }
                           rsCheck.close();
                       }
                        catch(Exception e) {
                          //Handle errors for Class.forName
                          e.printStackTrace();

                          //Inform user of error
                          System.out.println("Error! Could Not get result from the Database");
                        }
                   }
                   
                   
                   //Please select an artist for the album
                   boolean getStudio = true;
                   while(getStudio)
                   {
                       System.out.println("\nChoose Recording Studio for "+title+"!\n");
                       String sql = "SELECT sname FROM recstudio";
                       //Simply loop through the result set for the albulms
                       try (ResultSet rs = stmt.executeQuery(sql)) {
                           //Simply loop through the result set for the albulms
                           int count = 0;
                           System.out.println("Recording Studios:");
                           while(rs.next()){
                               //Retrieve by column name
                               String studios = rs.getString("sname");
                               count++;
                               
                               //Display values
                               System.out.print(String.format("%-2d. %-30s",count, studios));
                               if(count%5==0){
                                   System.out.println();
                               }
                           }
                           //Close the result set
                           System.out.println();
                           rs.close();
                       }
                        catch(Exception e) {
                          //Handle errors for Class.forName
                          e.printStackTrace();

                          //Inform user of error
                          System.out.println("Error! Could Not get result from the Database");
                        }
                       
                       //Get the input
                       System.out.print("\nRecording Studio Choice: ");
                       studioName = scan.nextLine();
                       
                       //Check if it is a valid group
                       //Query the DB
                       String sqlCheck = "SELECT sname FROM recstudio WHERE LOWER(sname) LIKE LOWER('%" + studioName + "%')";
                       try (ResultSet rsCheck = stmt.executeQuery(sqlCheck)) {
                           if(rsCheck.next())
                           {
                               //Good it exists!
                               getStudio = false;
                           }
                           else
                           {
                               //Not a valid group
                               System.out.println("Studio does not exist, please select an available group");
                               System.out.println();
                           }
                           //Close the result set
                           rsCheck.close();
                       }
                        catch(Exception e) {
                          //Handle errors for Class.forName
                          e.printStackTrace();

                          //Inform user of error
                          System.out.println("Error! Could Not get result from the Database");
                        }
                   }
                   
                   //Set the Date Recorded
                   boolean getDate = true;
                   while(getDate)
                   {
                       //Tell user to enter a valid date
                       System.out.print("Enter "+title+" recording date (Format: yyyy-mm-dd): ");
                       
                       //Get the input
                       dateRec = scan.nextLine();
                       
                       if(dateRec.length() == 10 &&
                               dateRec.charAt(4) == '-' &&
                               dateRec.charAt(7) == '-')
                       {
                           //Format is correct, continue on!
                           getDate = false;
                       }
                       else
                       {
                           //they entered the wrong format
                           System.out.println("Incorrect Format! Please enter the correct format for the date");
                           System.out.println();
                       }
                   }
                   
                   //Length Of the Albulm
                   System.out.print("Length of "+title+": ");
                   aLength = scan.nextLine();
                   
                   //Number Of Songs
                   boolean isInt = false;
                   while(!isInt)
                   {
                       System.out.print("Number of songs in "+title+": ");
                       numSongs = scan.nextInt();
                       
                       //Try to get the value
                       //may throw exception if not a number
                       int inputLength;
                       try
                       {
                           inputLength = numSongs;
                       }
                       catch(Exception e)
                       {
                           inputLength = -1;
                       }
                       
                       //Execute the function and error check
                       //Is it an integer
                       if(inputLength < 0)
                       {
                           System.out.println("That is not a valid number");
                       }
                       else {
                           //Continue on!
                           isInt = true;
                       }
                   }
                   
                   //Insert values into the database
                   System.out.println("\nPutting "+title+" into database...");
                   
                   //Create the sql
                   String sql = "INSERT INTO album VALUES "
                           + "('" + title + "', "
                           + "'" + groupName + "', "
                           + "'" + studioName + "', "
                           + "'" + dateRec + "', "
                           + "'" + aLength + "', "
                            + numSongs + ")";
                   
                   //Execute the sql
                   stmt.execute(sql);
                   
                   //Finished!
                   //stmt.close();
                   System.out.println("\nNow in Database! Press enter to continue: ");
                   System.in.read();
                   
               }
               else if(answer == 4)
               {
                   //Insert a new studio
                   String sname = "";
                   String saddress = "";
                   String sowner = "";
                   String sphone = "";
                   
                   //Get the studio title
                   System.out.print("Enter a name for the studio: ");
                   sname = scan.nextLine();
                   
                   //Get the studio title
                   System.out.print("Enter an address for "+sname+": ");
                   saddress = scan.nextLine();
                   
                   //Get the studio title
                   System.out.print("Enter an owner for "+sname+": ");
                   sowner = scan.nextLine();
                   
                   //Set the studio phone
                   System.out.print("Enter a phone number for "+sname+": ");
                   sphone = scan.nextLine();
                   
                   //Insert values into the database
                   System.out.println("Putting into database...");
                   
                   //Create the sql
                   String sql = "INSERT INTO recstudio VALUES "
                           + "('" + sname + "', "
                           + "'" + saddress + "', "
                           + "'" + sowner + "', "
                           + "'" + sphone + "')";
                   
                   //Execute the sql
                   stmt.execute(sql);
                   
                   //Finished!
                   //System.out.println("Success!");
                   
                   
                   
                   
                   //Now ask if they would like to switch albulms published by another studio
                   //Please select an artist for the album
                   boolean getStudio = true;
                   String studioName;
                   while(getStudio)
                   {
                       System.out.println("Enter a recording studio to update their albums. Enter 'q' to opt out.");
                       String sqlStudio = "SELECT sname FROM recstudio";
                        int count =0;
                       //Simply loop through the result set for the albulms
                       try (ResultSet rs = stmt.executeQuery(sqlStudio)) {
                           //Simply loop through the result set for the albulms
                           System.out.println("\nRecording Studios:");
                          
                           while(rs.next()){
                               //Retrieve by column name
                               //Do not display the newly created studio
                              
                               if(!sname.equalsIgnoreCase(rs.getString("sname")))
                               {
                                   String studios = rs.getString("sname");
                                   //Display values
                                   count++;
                                   System.out.print(String.format("%-2d. %-30s",count, studios));
                                   if(count%5==0){
                                       System.out.println();
                                   }
                               }
                           }
                           //Close the result set
                           rs.close();
                       }
                        catch(Exception e) {
                          //Handle errors for Class.forName
                          e.printStackTrace();

                          //Inform user of error
                          System.out.println("Error! Could Not get result from the Database");
                        }
                       
                       //Get the input
                       System.out.print("\n\nYour Choice: ");
                       studioName = scan.nextLine();
                       
                       //Check if it is a valid group
                       //Query the DB
                       String sqlCheck = "SELECT sname FROM recstudio WHERE LOWER(sname) LIKE LOWER('%" + studioName + "%')";
                       try (ResultSet rsCheck = stmt.executeQuery(sqlCheck)) {
                           if(rsCheck.next() && !sname.equalsIgnoreCase(studioName))
                           {
                               //Good it exists!
                               //Switch the albulms from the studio to this one!
                               String sqlSwitch= "UPDATE album SET sname = '" + sname + "' WHERE LOWER(sname) LIKE LOWER('%" + studioName + "%')";
                               stmt.executeUpdate(sqlSwitch);
                               
                               //Close the result set and exit the loop
                               System.out.println("Albums have been switched from " + studioName + " into the new studio " + sname +"!");
                               getStudio = false;
                           }
                           else if (studioName.equalsIgnoreCase("q"))
                           {
                               //They chose not to have the albums switched over
                               System.out.println("Switching albulms to this studio will not be performed!");
                               getStudio = false;
                           }
                           else
                           {
                               //Not a valid group
                               System.out.println("Studio does not exist, please select an available group");
                               System.out.println();
                           }
                           
                           //Close the result set
                           rsCheck.close();
                       
                       }
                       catch(Exception e) {
                          //Handle errors for Class.forName
                          e.printStackTrace();

                          //Inform user of error
                          System.out.println("Error! Could Not get result from the Database");
                        }
                   }
                   System.in.read();
               }
               else if(answer == 5)
               {
                   //Delete an albulm
                   System.out.print("You want to delete an album! ");
                   String sql = "SELECT atitle FROM album";
                   //Loop through result set
                   int count = 0;
                   try (ResultSet rs = stmt.executeQuery(sql)) {
                       //Simply loop through the result set for the albulms
                       System.out.println("\nAlbum Titles:");
                       while(rs.next()){
                           //Retrieve by column name
                           String title = rs.getString("atitle");
                           
                           //Display values
                           //System.out.print(title);
                           count++;
                           System.out.print(String.format("%-2d. %-30s", count,title));
                           
                           if((count%5) == 0){
                               System.out.println();
                           }
                           
                       }
                       
                       //Close the result set
                       rs.close();
                   }
                    catch(Exception e) {
                          //Handle errors for Class.forName
                          e.printStackTrace();

                          //Inform user of error
                          System.out.println("Error! Could Not get result from the Database");
                     }
                   System.out.print("\n\nYour choice: ");
                   //Get the albulm title
                   input = scan.nextLine();
                   
                   //Query the DB
                   sql = "SELECT * FROM album WHERE LOWER(atitle) LIKE LOWER('%" + input + "%')";
                   ResultSet rs = stmt.executeQuery(sql);
                   
                   if(rs.next())
                   {
                       //Delete the albulm
                       //Create the sql
                       String sqlDel = "DELETE FROM album WHERE LOWER(atitle) LIKE LOWER('%" + input + "%')";
                       
                       //Execute the sql
                       stmt.execute(sqlDel);
                       
                       System.out.println(input+" deleted!");
                       System.out.println();
                   }
                   else
                   {
                       System.out.println(input+" does not exist...");
                   }
                   rs.close();
                   System.in.read();
                   
               }
               else if(answer == 6)
               {
                   //Quit
                   querying = false;
               }
               
               //Spacing
               System.out.println();
           }
           
           //Finish the program
       }
       catch(Exception e) {
      
            //Inform user of error
            System.out.println("Error! Scanner could not be Created!");
       }
   }catch(SQLException se){
      
      //Inform user of error
       System.out.println("Error connecting to the database!");
   }catch(ClassNotFoundException e){
      
       //Inform user of error
       System.out.println("Error!");
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         
         //Inform user of error
         System.out.println("Error closing connection to the database!");
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
    
    }
}