package cs.b07.cscb07project.backend.managers;

import cs.b07.cscb07project.backend.users.User;

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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A database used to verify usernames and passwords.
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class PasswordManager implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = 3609098958223080939L;
  
  private HashMap<String, String> passwordDatabase;
  private String filePath;
  
  private static final Logger fLogger = Logger.getLogger(PasswordManager.class.getPackage()
                                                             .getName());
  
  
  /**
   * Creates a new PassManager for the users whose information
   * is stored in file filePath.
   * @throws IOException if filePath cannot be opened/created.
   */
  public PasswordManager(File file) throws IOException {
    passwordDatabase = new HashMap<>();
    filePath = file.getPath();
    // Populates the record list using stored data, if it exists.
    if (file.exists()) {
      readFromFile(filePath);
    } else {
      file.createNewFile();
    }
  }

  /**
   * Writes the passwords to file outputStream.
   */
  public void saveToFile() {
    try (
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer)
    ) {
      output.writeObject(passwordDatabase);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
    }
  }

  /**
   * Given the file path to a .csv file of user usernames and passwords, will read file,
   * and enter usernames and passwords into the PasswordManager.
   * @param filePath A String giving the file path to a .csv file of correctly formated
   *     User data.
   */
  public void uploadPasswordManager(String filePath) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String line;
      // Loop through each line in the CSV file
      while ((line = br.readLine()) != null && !line.isEmpty()) {
        String[] fields = line.split(",");
        String username = fields[0];
        String password = fields[1];

        addUser(username, password);
      }
      br.close();
    } catch (FileNotFoundException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. File not found.", e);
    } catch (IOException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. I/O error occured while reading"
                                    + " from file.", e);
    }
  }

  /**
   * Adds a username and password pair to the PasswordDatabase using the given username as
   * the key.
   * @param username A String representation of the users username.
   * @param password A String representation of the users password.
   */
  public void addUser(String username, String password) {
    passwordDatabase.put(username, password);
  }
  
  /**
   * Adds a username and password pair to the PasswordDatabase using the given username as
   * the key.
   * @param user A User object to be added to the PasswordDatabase.
   * @param password A String representation of the users password.
   */
  public void addUser(User user, String password) {
    passwordDatabase.put(user.getUserName(), password);
  }
  
  /**
   * Returns true if and only if the given password is an exact match to the password
   * associated with the given username.
   * @param username A String representing the username to be verified.
   * @param password A String representing the password to be checked against the given
   *     username.
   * @return true if and only if the given password is an exact match to the password
   *     associated with the given username. (Case sensitive).
   */
  public boolean verifyPassword(String username, String password) {
    boolean isValid = false;
    if (passwordDatabase.get(username).equals(password)) {
      isValid = true;
    }
    return isValid;
  }
  
  @SuppressWarnings("unchecked")
  private void readFromFile(String path) {
    try (
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer)
    ) {
      //deserialize the Map
      passwordDatabase = (HashMap<String, String>)input.readObject();
    } catch (ClassNotFoundException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
    }
  }
  
}
