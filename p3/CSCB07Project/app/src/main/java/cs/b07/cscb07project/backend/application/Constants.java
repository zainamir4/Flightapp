package cs.b07.cscb07project.backend.application;

/**
 * Constants used in the Flight Booking Application.
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class Constants {

  /** The file name for storing passwords. */
  public static final String FILE_PASS = "passwords.txt";

  /** The file name for storing clients. */
  public static final String FILE_CLIENT = "clients.txt";

  /** The directory name for flight booking app. */
  public static final String USER_DATA_DIR = "records_data";

  /** The file name for storing flights. */
  public static final String FILE_NAME = "flights.txt";
  
  /** The minimum allowable time for a stop over. */
  public static final long STOPOVERMIN = 30;
  
  /** The maximum allowable time for a stop over. */
  public static final long STOPOVERMAX = 6;
  
  /** The format in which departure dates will be given. */
  public static final String DAY_DATE_FORMAT = "yyyy-MM-dd";
  
  /** The format in which flight arrival and departure times will be given. */
  public static final String TIME_DATE_FORMAT = "yyyy-MM-dd HH:mm";

  /** Flight ListView row text. */
  public static final String FLIGHT_LISTVIEW_ROW = ("%s     Flight Number: %s\n"
                                                        + "Departing: %s @ %s\nArriving: %s @ %s\n"
                                                        + "Seats: %s     Total Price: $%s");

  /** Flight for Itinerary ListView row text. */
  public static final String FLIGHT_ITINERARY_LISTVIEW_ROW = ("%s     Flight Number: %s\n"
                                                                  + "Departing: %s @ %s\n"
                                                                  + "Arriving: %s @ %s\n"
                                                                  + "Seats: %s\n");

  /** Total cost and time for Itinerary ListView row text. */
  public static final String TOTAL_LISTVIEW_ROW = "Total Time: %s\nTotal Cost: %s";

  /** Text to display when flight is full. */
  public static final String NO_SEATS_AVAILABLE = "No seats available in this flight!";

  /** Text to display when not all blanks filled in by user. */
  public static final String UNFILLED_FIELDS = "Please fill in all the blanks!";

  /** Text to display when booking is successful. */
  public static final String SUCCESSFUL_BOOK_FLIGHT = "You have successfully booked this flight!";

  /** Text to display when given date format is incorrect. */
  public static final String DATE_FORMAT_ERROR = "Format must be 'YYYY-MM-DD'";

  /** Text to display when given date time format is incorrect. */
  public static final String DATE_TIME_FORMAT_ERROR = "Format must be 'YYYY-MM-DD HH:MM'";

  /** Text to display when given login information is incorrect. */
  public static final String INVALID_LOGIN = "Invalid username or password, please try again.";

  /** Text to display when given Username is taken. */
  public static final String USERNAME_TAKEN = "Username taken!";

  /** Text for OK button. */
  public static final String OK = "Ok";

  /** Key for Edit Flight Error. */
  public static final String EDIT_FLIGHT_ERROR = "Edit Flight Error";

  /** Key for Edit Profile Error. */
  public static final String EDIT_ERROR = "Edit Profile Error";

  /** Key for Login Error. */
  public static final String LOGIN_ERROR = "Login Error";

  /** Key for Registration Error. */
  public static final String REGISTRATION_ERROR = "Registration Error";

  /** Key for full flight. */
  public static final String FULL_FLIGHT = "Full Flight";

  /** Key for successful booking. */
  public static final String SUCCESSFUL_BOOKING = "Successful Booking";

  /** Key for username of current user. */
  public static final String USERNAME = "username";

  /** Key for client username. */
  public static final String CLIENT = "client";

  /** Key for departure date. */
  public static final String DEPARTURE_DATE = "departureDate";

  /** Key for origin. */
  public static final String ORIGIN = "origin";

  /** Key for destination. */
  public static final String DESTINATION = "destination";

  /** Key for flight number. */
  public static final String FLIGHT = "flight";

  /** Key for an itinerary. */
  public static final String ITINERARY = "itinerary";
}