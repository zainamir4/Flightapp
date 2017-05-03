package cs.b07.cscb07project.backend.io;

import cs.b07.cscb07project.backend.databases.FlightDatabase;
import cs.b07.cscb07project.backend.databases.UserDatabase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

/**
 * A class responsible for reading input from file and storing it in the Flight Booking
 * Application.
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class LoadDataFromFile {

  /**
   * Given the file path to a .csv file of user data, will read file, create a new User
   * object for each user, and enter Users into the ClientDatabase.
   * @param filePath A String giving the file path to a .csv file of correctly formated
   *     User data.
   * @throws FileNotFoundException Throws a FileNotFoundException if given filePath cannot be
   *     found.
   * @throws IOException Throws a IOException if I/O error occurs while reading from file.
   * @throws ParseException Throws a ParseException if credit card expiry date is not given
   *     in the YYYY-MM-DD format.
   */
  public static void uploadUserInfo(UserDatabase clientDatabase, String filePath) throws
      IOException, ParseException {
    BufferedReader br = new BufferedReader(new FileReader(filePath));
    String line;
    // Loop through each line in the CSV file
    while ((line = br.readLine()) != null && !line.isEmpty()) {
      String[] fields = line.split(",");
      String lastName = fields[0];
      String firstName = fields[1];
      String email = fields[2];
      String address = fields[3];
      String creditCardNumber = fields[4];
      String expiryDate = fields[5];

      // Create a new Client that gets added to the HashMap
      clientDatabase.addClient(email,  lastName, firstName, email, address,
          creditCardNumber, expiryDate);
    }
    br.close();
  }
  
  
  /**
   * Given the file path to a .csv file of flight data, will read file, create a new Flight
   * object for each Flight, and enter each Flight into the FlightDatabase.
   * @param fileName A String giving the file path to a .csv file of correctly formated
   *     Flight data.
   * @throws FileNotFoundException Throws a FileNotFoundException if given filePath cannot be
   *     found.
   * @throws IOException Throws a IOException if I/O error occurs while reading from file.
   * @throws ParseException Throws a ParseException if departure date (YYYY-MM-DD HH:MM),
   *     arrival date (YYYY-MM-DD HH:MM), or flight (YYYY-MM-DD) cost are not given in correct
   *     format.
   */
  public static void uploadFlightInfo(FlightDatabase flightDatabase, String fileName) throws
      IOException, ParseException {
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    String line;
    // Loop through the CSV file
    while ((line = br.readLine()) != null && !line.isEmpty()) {
      String[] fields = line.split(",");
      String flightNumber = fields[0];
      String departureTime = fields[1];
      String arrivalTime = fields[2];
      String airline = fields[3];
      String origin = fields[4];
      String destination = fields[5];
      double price = Double.parseDouble(fields[6]);
      int numSeats = Integer.parseInt(fields[7]);

      // Create a new Flight that gets added to the ArrayList
      flightDatabase.addFlight(flightNumber, departureTime, arrivalTime, airline, origin,
              destination, price, numSeats);
    }
    br.close();
  }
  
}
