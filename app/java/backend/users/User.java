package cs.b07.cscb07project.backend.users;

import java.io.Serializable;

/**
 * An abstract class representing basic functionality common to all users of the
 * Flight Booking Application.
 *
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public abstract class User implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = -7298361757696444194L;
  private String userName;
  private boolean isAdmin;

  /**
   * Returns true if and only if this user is an admin.
   *
   * @return true if and only if this user is an admin.
   */
  public boolean isAdmin() {
    return this.isAdmin;
  }

  /**
   * Creates a new user object with the given user name and password.
   *
   * @param userName
   *            A String user name for the given user (must be unique).
   */
  public User(String userName, boolean isAdmin) {
    this.userName = userName;
    this.isAdmin = isAdmin;
  }

  /**
   * Returns the user name of this user object.
   *
   * @return A String of the user name for this user.
   */
  public String getUserName() {
    return userName;
  }

}
