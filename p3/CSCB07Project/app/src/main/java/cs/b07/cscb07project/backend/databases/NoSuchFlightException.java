package cs.b07.cscb07project.backend.databases;

/**
 * Indicates an attempt to find a Flight using a flight number that doesn't exist in the
 * database.
 * 
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class NoSuchFlightException extends Exception {
  
  /**
   * Serialization.
   */
  private static final long serialVersionUID = -7698585905449263450L;

  /**
   * Constructs a new NoSuchFlightException with null as its detail message.
   */
  public NoSuchFlightException() {
  }
}

