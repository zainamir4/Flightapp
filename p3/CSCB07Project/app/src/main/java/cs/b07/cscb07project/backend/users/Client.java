package cs.b07.cscb07project.backend.users;

import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.itinerary.FlightItinerary;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A User class that allows user with features like search flights and
 * Itineraries, display sorted itineraries by totalCost and travelTime, view all
 * client information, upload personal/billing information and flight
 * information.
 *
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class Client extends User implements Serializable {

  private static final long serialVersionUID = 2354011759515906949L;
  private String lastName;
  private String firstName;
  private String email;
  private String address;
  private String creditCardNumber;
  private Date expiryDate;
  private ArrayList<FlightItinerary> bookedFlightItineraries;

  /**
   * Creates a Client user with the given information.
   *
   * @param userName
   *            A String user name for the given user (must be unique).
   * @param lastName
   *            The last name of the Client
   * @param firstName
   *            The first name of the Client
   * @param email
   *            The email of the Client
   * @param address
   *            The address of the Client
   * @param creditCardNumber
   *            The credit card number of the Client
   * @param expiryDate
   *            The expire date of the Client's credit card
   */
  public Client(String userName, String lastName, String firstName,
                String email, String address, String creditCardNumber,
                String expiryDate) throws ParseException {
    super(userName, false);
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.address = address;
    this.creditCardNumber = creditCardNumber;
    SimpleDateFormat expiryDateFormat = new SimpleDateFormat(Constants.DAY_DATE_FORMAT, Locale.US);
    this.expiryDate = expiryDateFormat.parse(expiryDate);
    bookedFlightItineraries = new ArrayList<>();
  }

  /**
   * Returns a String Representation of the lastName of the user.
   *
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Changes the lastName of the user to the given String.
   *
   * @param lastName
   *            the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Returns the String Representation of firstName of the user.
   *
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the firstName of the user.
   *
   * @param firstName
   *            the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Returns the String Representation of the user's email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Changes the email address to the given String.
   *
   * @param email
   *            the email to set the current email.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the String Representation of the user's address.
   *
   * @return the address in the string form.
   */
  public String getAddress() {
    return address;
  }

  /**
   * Changes the address to the given string.
   *
   * @param address
   *            the address to set the current address.
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Returns the string representation of the CreditCard.
   *
   * @return the string representation of the creditCardNumber.
   */
  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  /**
   * Changes the creditCardNumber to the given string.
   *
   * @param creditCardNumber
   *            the creditCardNumber to set the current creditCardNumber.
   */
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  /**
   * Returns the string representation of the expiryDate.
   *
   * @return the expiryDate in the format YYYY-MM-DD.
   */
  public String getExpiryDateString() {
    SimpleDateFormat outputFormat = new SimpleDateFormat(Constants.DAY_DATE_FORMAT, Locale.US);
    return outputFormat.format(expiryDate);
  }

  /**
   * Changes the expiryDate to the given string.
   *
   * @param expiryDate
   *            the expiryDate to set the current expiryDate.
   * @throws ParseException
   *             Throws a ParseException if expiryDate is given in format
   *             other than YYYY-MM-DD.
   */
  public void setExpiryDate(String expiryDate) throws ParseException {
    SimpleDateFormat expiryDateFormat = new SimpleDateFormat(Constants.DAY_DATE_FORMAT, Locale.US);
    this.expiryDate = expiryDateFormat.parse(expiryDate);
  }

  @Override
  public String toString() {
    return (getLastName() + "," + getFirstName() + "," + getEmail() + ","
                + getAddress() + "," + getCreditCardNumber() + "," + getExpiryDateString());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime
                 * result
                 + ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result
                 + ((expiryDate == null) ? 0 : expiryDate.hashCode());
    result = prime * result
                 + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result
                 + ((lastName == null) ? 0 : lastName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Client other = (Client) obj;
    if (address == null) {
      if (other.address != null) {
        return false;
      }
    } else if (!address.equals(other.address)) {
      return false;
    }
    if (creditCardNumber == null) {
      if (other.creditCardNumber != null) {
        return false;
      }
    } else if (!creditCardNumber.equals(other.creditCardNumber)) {
      return false;
    }
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }
    if (expiryDate == null) {
      if (other.expiryDate != null) {
        return false;
      }
    } else if (!expiryDate.equals(other.expiryDate)) {
      return false;
    }
    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!firstName.equals(other.firstName)) {
      return false;
    }
    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }
    return true;
  }

  /**
   * Allows the user to book a flight itinerary which adds the itinerary to the user's
   * list of booked itineraries. It also decrease the number of seats available by one
   * on each of the flights that in the itinerary.
   *
   * @param itinerary
   *           A flight itinerary that is to be booked.
   */
  public void bookFlightItinerary(FlightItinerary itinerary) {
    bookedFlightItineraries.add(itinerary);
  }

  /**
   * Returns an ArrayList of the itineraries booked by this client.
   * @return the bookedFlightItineraries An ArrayList of the itineraries booked by this client.
   */
  public ArrayList<FlightItinerary> getBookedFlightItineraries() {
    return bookedFlightItineraries;
  }

}