package cs.b07.cscb07project.backend.managers;

import cs.b07.cscb07project.backend.databases.FlightDatabase;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manager for the Flight Records.
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class FlightManager implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = -1189902772765080933L;
  private FlightDatabase flightDatabase;
  private String filePath;

  private static final Logger fLogger =
      Logger.getLogger(FlightManager.class.getPackage().getName());

  /**
   * Creates a new FlightManager for the Flights whose information
   * is stored in file filePath.
   * @throws IOException if filePath cannot be opened/created.
   */
  public FlightManager(File file) throws IOException {
    // get the flightDatabase
    flightDatabase = FlightBookingApplication.getFlightDatabase();
    filePath = file.getPath();
    // Populates the record list using stored data, if it exists.
    if (file.exists()) {
      readFromFile(filePath);
    } else {
      file.createNewFile();
    }
  }

  /**
   * Writes the flights to file outputStream.
   */
  public void saveToFile() {
    try (
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer)
    ) {
      output.writeObject(flightDatabase);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
    }
  }

  /**
   * Given the file path to a .csv file of flight data, will read file, create a new Flight
   * object for each Flight, and enter each Flight into the FlightDatabase.
   * @param fileName A String giving the file path to a .csv file of correctly formated
   *     Flight data.
   */
  public void uploadFlightInfo(String fileName) {
    try {
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
        FlightBookingApplication.addFlight(flightNumber, departureTime, arrivalTime, airline,
                                              origin, destination, price, numSeats);
      }
      br.close();
      // change the database
      flightDatabase = FlightBookingApplication.getFlightDatabase();
    } catch (FileNotFoundException ex) {
      fLogger.log(Level.SEVERE, "Cannot Perform the task b/c no file found", ex);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot Perform the task ", ex);
    }

  }

  private void readFromFile(String path) {
    try (
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer)
    ) {
      //deserialize the Map
      flightDatabase = (FlightDatabase)input.readObject();
      // change the state of the Database.
      FlightBookingApplication.setFlightDatabase(flightDatabase);
    } catch (ClassNotFoundException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
    }
  }
}
