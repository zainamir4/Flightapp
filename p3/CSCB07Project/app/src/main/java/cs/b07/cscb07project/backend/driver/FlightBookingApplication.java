package cs.b07.cscb07project.backend.driver;

import cs.b07.cscb07project.backend.application.ItineraryGenerator;
import cs.b07.cscb07project.backend.application.ItinerarySort;
import cs.b07.cscb07project.backend.databases.FlightDatabase;
import cs.b07.cscb07project.backend.databases.NoSuchFlightException;
import cs.b07.cscb07project.backend.databases.NoSuchUserNameException;
import cs.b07.cscb07project.backend.databases.UserDatabase;
import cs.b07.cscb07project.backend.io.LoadDataFromFile;
import cs.b07.cscb07project.backend.itinerary.FlightItinerary;
import cs.b07.cscb07project.backend.transportation.Flight;
import cs.b07.cscb07project.backend.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that implements the basic functionality of the Flight Booking Application.
 * 1. At creation it initializes the instance variables used to store the
 *     current state of the Flight Booking Application.
 * 2. Allows user classes to access methods and data structures related to Flight and
 *     FlightItinerary objects.
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class FlightBookingApplication {
  
  private static FlightDatabase flightDatabase = new FlightDatabase();
  
  private static UserDatabase userDatabase = new UserDatabase();

  private static final Logger fLogger =
      Logger.getLogger(FlightBookingApplication.class.getPackage().getName());

  
  /**
   * Returns a String representation of the Client with the given user name.
   * @param userName A String representing the user name of the Client to be returned.
   * @return A String representation of the Client who's user name was given.
   */
  public static User getUser(String userName) {
    User user = null;
    try {
      user = userDatabase.getUser(userName);
    } catch (NoSuchUserNameException e) {
      fLogger.log(Level.SEVERE, "Cannot retrieve user data. No user with given user name found"
          + " in database.", e);
    }
    return user;
  }
  
  /**
   * Adds a Flight to be stored in the FlightDatabase. If Flight with given flight number
   * is already in the FlightDatabase then new Flight will override previous entry with given
   * flight number.
   * @param departureTime The departure time of the Flight to be created.
   * @param arrivalTime The arrival time of the Flight to be created.
   * @param origin The name of the city from which the Flight originates.
   * @param destination The name of the destination city of the Flight.
   * @param cost The cost of the Flight.
   * @param flightNumber The unique flight number associated with the Flight
   * @param airline The name of the airline of the Flight to be created.
   * @param numSeats An int representing the number of seats available for sale on this Flight.
   */
  public static void addFlight(String flightNumber, String departureTime, String arrivalTime,
      String airline, String origin, String destination, double cost, int numSeats) {
    try {
      flightDatabase.addFlight(flightNumber, departureTime, arrivalTime, airline, origin,
          destination, cost, numSeats);
    } catch (ParseException e) {
      fLogger.log(Level.SEVERE, "Cannot add flight to database. Date/Time given in format"
          + " other than YYYY-MM-DD HH:MM.", e);
    }
  }
  
  /**
   * Creates a new client object and adds it to the user database.
   * @param userName A String username for the client.
   * @param lastName A String last name for the client.
   * @param firstName A String first name for the client.
   * @param email A String email for the client.
   * @param address A String address for the client.
   * @param creditCardNumber A String credit card number for the client.
   * @param expiryDate A String expiry date for the client's credit card.
   */
  public static void addClient(String userName, String lastName, String firstName,
      String email, String address, String creditCardNumber, String expiryDate) {
    try {
      userDatabase.addClient(userName, lastName, firstName, email, address, creditCardNumber,
              expiryDate);
    } catch (ParseException e) {
      fLogger.log(Level.SEVERE, "Cannot add Client to database. Date/Time given in format"
                                    + " other than YYYY-MM-DD", e);
    }
  }
  
  /**
   * Returns the Flight with the given flight number.
   * @param flightNumber A String representing the flight number of the Flight to be returned.
   * @return The Flight who's flight number was given.
   */
  public static Flight getFlight(String flightNumber) {
    Flight flight = null;
    try {
      flight = flightDatabase.getFlight(flightNumber);
    } catch (NoSuchFlightException e) {
      fLogger.log(Level.SEVERE, "Cannot retrieve flight data. No flight with given flight"
          + " number found in database.", e);
    }
    return flight;
  }
  
  /**
   * Returns a String representation of all flights that depart from origin and arrive at
   * destination on the given date.
   * @param date A String representing the date on which the Flights will depart given in the
   *     YYYY-MM-DD format.
   * @param origin A String representing the origin from which the Flights depart.
   * @param destination A String representing the destination of the Flights
   * @return the flights that depart from origin and arrive at destination on
   *         the given date formatted with one flight per line in exactly this
   *         format:
   *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *         ,Price (the dates are in the format YYYY-MM-DD; the price has
   *         exactly two decimal places).
   */
  public static ArrayList<Flight> getFlights(String date, String origin, String destination) {
    ArrayList<Flight> flights = new ArrayList<>();
    try {
      flights = ItineraryGenerator.getFlights(flightDatabase, date, origin,
              destination);
    } catch (ParseException e) {
      fLogger.log(Level.SEVERE, "Cannot perform task because of an incorrect date format.", e);
    }
    return flights;
  }
  
  /** Returns all itineraries that depart from origin and arrive at destination
  * on the given date. With no more than 6 hours stop over time between the arrival and
  * departure of a Flight.
  * @param date A String representing a departure date (in the format YYYY-MM-DD).
  * @param origin A String representing the origin of the itineraries.
  * @param destination A String representing the final destination of the itineraries.
  * @return itineraries that depart from origin and arrive at destination on
  *         the given date with stop overs at or under 6 hours. Each itinerary
  *         in the output should contain one line per flight, in the format:
  *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
  *         followed by total price (on its own line, exactly two decimal
  *         places), followed by total duration (on its own line, in format
  *         HH:MM).
  */
  public static ArrayList<FlightItinerary> getItineraries(String date, String origin,
                                                          String destination) {
    ArrayList<FlightItinerary> itineraries = new ArrayList<>();
    try {
      itineraries = ItineraryGenerator.getItineraries(flightDatabase, date, origin,
              destination);
    } catch (ParseException e) {
      fLogger.log(Level.SEVERE, "Cannot perform task because of an inccorect date format.", e);
    }
    return itineraries;
  }
  
  /**
   * Returns all itineraries that depart from origin and arrive at destination
   * on the given date in non-decreasing order by total cost. With no more than 6 hours
   * stop over time between the arrival and departure of a Flight.
   * @param date A String representing a departure date (in the format YYYY-MM-DD).
   * @param origin A String representing the origin of the itineraries.
   * @param destination A String representing the final destination of the itineraries.
   * @return itineraries (sorted in non-decreasing order of total itinerary
   *         cost) that depart from origin and arrive at destination on the
   *         given date with stop overs at or under 6 hours. Each itinerary in
   *         the output should contain one line per flight, in the format:
   *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *         followed by total price (on its own line, exactly two decimal
   *         places), followed by total duration (on its own line, in format
   *         HH:MM).
   */
  public static String getItinerariesSortedByCost(String date, String origin,
      String destination) {
    ArrayList<FlightItinerary> itineraries = new ArrayList<>();
    try {
      itineraries = ItineraryGenerator.getItineraries(flightDatabase, date, origin,
          destination);
      itineraries = ItinerarySort.sortItineraryCost(itineraries);
    } catch (ParseException e) {
      fLogger.log(Level.SEVERE, "Cannot perform task because of an inccorect date format.", e);
    }
    String itineraryString = "";
    for (FlightItinerary itinerary : itineraries) {
      itineraryString += itinerary.toString();
    }
    return itineraryString;
  }
  
  /**
   * Returns all itineraries that depart from origin and arrive at destination
   * on the given date in non-decreasing order by total travel time. With no more than
   * 6 hours stop over time between the arrival and departure of a Flight.
   * @param date A String representing a departure date (in the format YYYY-MM-DD).
   * @param origin A String representing the origin of the itineraries.
   * @param destination A String representing the final destination of the itineraries.
   * @return A String of itineraries (sorted in non-decreasing order of travel itinerary
   *         travel time) that depart from origin and arrive at destination on
   *         the given date with stopovers at or under 6 hours. Each itinerary
   *         in the output should contain one line per flight, in the format:
   *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *         followed by total price (on its own line, exactly two decimal
   *         places), followed by total duration (on its own line, in format
   *         HH:MM).
   */
  public static String getItinerariesSortedByTime(String date, String origin,
      String destination) {
    ArrayList<FlightItinerary> itineraries = new ArrayList<>();
    try {
      itineraries = ItineraryGenerator.getItineraries(flightDatabase, date, origin,
          destination);
      itineraries = ItinerarySort.sortItineraryTravelTime(itineraries);
    } catch (ParseException e) {
      fLogger.log(Level.SEVERE, "Cannot perform task because of an inccorect date format.", e);
    }
    String itineraryString = "";
    for (FlightItinerary itinerary : itineraries) {
      itineraryString += itinerary.toString();
    }
    return itineraryString;
  }

  /**
   * Adds the User to the Database.
   * @param user A user object to add to the user database.
   */
  public static void addUser(User user) {
    userDatabase.addUser(user);
  }

  /**
   * Returns the main flightDatabase.
   * @return the FlightDatabase object.
   */
  public static FlightDatabase getFlightDatabase() {
    return flightDatabase;
  }

  /**
   * Sets the main Database to the newly created Database.
   * @param newFlightDatabase to change the previous database.
   */
  public static void setFlightDatabase(FlightDatabase newFlightDatabase) {
    flightDatabase = newFlightDatabase;
  }

  /**
   * Returns the main userDatabase that we use for our records.
   * @return the UserDatabase
   */
  public static UserDatabase getUserDatabase() {
    return userDatabase;
  }

  /**
   * Sets the main Database to the new given Database.
   * @param newUserDatabase to change the previous userDatabase to.
   */
  public static void setUserDatabase(UserDatabase newUserDatabase) {
    userDatabase = newUserDatabase;
  }

  /**
  * Uploads client information to the clientDatabase from the file at the given path.
  * @param path A String giving the path to an input csv file of client information with
  *          lines in the format:
  *          LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate (the
  *          ExpiryDate is stored in the format YYYY-MM-DD)
  */
  public static void uploadClients(String path) {
    try {
      LoadDataFromFile.uploadUserInfo(userDatabase, path);
    } catch (FileNotFoundException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. File not found.", e);
    } catch (IOException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. I/O error occured while reading"
              + " from file.", e);
    } catch (ParseException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. Date given in format other than"
              + " YYYY-MM-DD.", e);
    }
  }

  /**
  * Uploads flight information to the FlightDatabase from the file at the given path.
  * @param filePath A String giving the path to an input csv file of flight information with
  *          lines in the format:
  *          Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
  *          Destination,Price (the dates are in the format YYYY-MM-DD; the
  *          price has exactly two decimal places)
  */
  public static void uploadFlightInfo(String filePath) {
    try {
      LoadDataFromFile.uploadFlightInfo(flightDatabase, filePath);
    } catch (FileNotFoundException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. File not found.", e);
    } catch (IOException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. I/O error occured while reading"
              + " from file.", e);
    } catch (ParseException e) {
      fLogger.log(Level.SEVERE, "Cannot perform upload. Date given in format other than"
              + " YYYY-MM-DD.", e);
    }
  }

  
}
