package cs.b07.cscb07project.backend.users;

/**
 * A User class that allows user with features like search flights and
 * Itineraries, display sorted itineraries by totalCost and travelTime, view all
 * client information, upload personal/billing information and flight
 * information.
 *
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class Admin extends User {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = -7396076393832964193L;

  /**
   * Creates a new Admin object with given user name and password.
   *
   * @param userName
   *            A String user name for the given user (must be unique).
   */
  public Admin(String userName) {
    super(userName, true);
  }

}
