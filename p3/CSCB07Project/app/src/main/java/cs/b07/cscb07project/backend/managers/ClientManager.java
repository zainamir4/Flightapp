package cs.b07.cscb07project.backend.managers;

import cs.b07.cscb07project.backend.databases.UserDatabase;
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

public class ClientManager implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = -4339255817288201298L;
  private UserDatabase userDatabase;
  private String filePath;

  private static final Logger fLogger =
      Logger.getLogger(ClientManager.class.getPackage().getName());

  /**
   * Creates a new FlightManager for the Flights whose information
   * is stored in file filePath.
   * @throws IOException if filePath cannot be opened/created.
   */
  public ClientManager(File file) throws IOException {
    // get the flightDatabase
    userDatabase = FlightBookingApplication.getUserDatabase();
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
    userDatabase = FlightBookingApplication.getUserDatabase();
    try (
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer)
    ) {
      output.writeObject(userDatabase);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
    }
  }

  /**
   * Given the file path to a .csv file of user data, will read file, create a new User
   * object for each user, and enter Users into the ClientDatabase.
   * @param filePath A String giving the file path to a .csv file of correctly formated
   *     User data.
   */
  public void uploadUserInfo(String filePath) {
    try {
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
        FlightBookingApplication.addClient(email,  lastName, firstName, email, address,
                                              creditCardNumber, expiryDate);
      }
      br.close();
      // change the database
      userDatabase = FlightBookingApplication.getUserDatabase();
    } catch (FileNotFoundException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. File not found.", e);
    } catch (IOException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. I/O error occured while reading"
                                    + " from file.", e);
    }
  }

  private void readFromFile(String path) {
    try (
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer)
    ) {
      //deserialize the Map
      userDatabase = (UserDatabase)input.readObject();
      // change the state of the Database.
      FlightBookingApplication.setUserDatabase(userDatabase);
    } catch (ClassNotFoundException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
    }
  }
}
