package cs.b07.cscb07project.backend.driver;

import cs.b07.cscb07project.backend.itinerary.FlightItinerary;
import cs.b07.cscb07project.backend.transportation.Flight;

/** A Driver used for autotesting the project backend. */
public class Driver {

  /**
   * Uploads client information to the application from the file at the
   * given path.
   * @param path the path to an input csv file of client information with
   *     lines in the format:
   *     LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
   *     (the ExpiryDate is stored in the format YYYY-MM-DD)
   */
  public static void uploadClientInfo(String path) {
    FlightBookingApplication.uploadClients(path);
  }

  /**
   * Uploads flight information to the application from the file at the
   * given path.
   * @param path the path to an input csv file of flight information with
   *     lines in the format:
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination,Price,NumSeats
   *     (the dates are in the format YYYY-MM-DD HH:MM; the price has exactly two
   *     decimal places; the number of seats is a non-negative integer)
   */
  public static void uploadFlightInfo(String path) {
    FlightBookingApplication.uploadFlightInfo(path);
  }

  /**
   * Returns the information stored for the client with the given email.
   * @param email the email address of a client
   * @return the information stored for the client with the given email
   *     in this format:
   *     LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
   *     (the ExpiryDate is stored in the format YYYY-MM-DD)
   */
  public static String getClient(String email) {
    return FlightBookingApplication.getUser(email).toString();
  }

  /**
   * Returns all flights that depart from origin and arrive at destination on
   * the given date.
   * @param date a departure date (in the format YYYY-MM-DD)
   * @param origin a flight origin
   * @param destination a flight destination
   * @return the flights that depart from origin and arrive at destination
   *     on the given date formatted with one flight per line in exactly this
   *     format:
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination,Price
   *     (the departure and arrival date and time are in the format
   *     YYYY-MM-DD HH:MM; the price has exactly two decimal places)
   */
  public static String getFlights(String date, String origin, String destination) {
    String allFlights = "";
    for (Flight nextFlight: FlightBookingApplication.getFlights(date, origin, destination)) {
      allFlights += nextFlight.toString();
    }
    return allFlights;
  }

  /**
   * Returns all itineraries that depart from origin and arrive at
   * destination on the given date. See handout for detailed description
   * of a valid itinerary.
   * Every flight in an itinerary must have at least one seat
   * available for sale. That is, the itinerary must be bookable.
   *
   * @param date a departure date (in the format YYYY-MM-DD)
   * @param origin a flight original
   * @param destination a flight destination
   * @return itineraries that satisfy the requirements in the handout.
   *     Each itinerary in the output should contain one line per flight,
   *     in the format:
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places)
   *     followed by total duration (on its own line, in format HH:MM).
   */
  public static String getItineraries(String date, String origin, String destination) {
    String allItineraries = "";
    for (FlightItinerary next: FlightBookingApplication.getItineraries(date,origin,destination)) {
      allItineraries += next.toString();
    }
    return allItineraries;
  }

  /**
   * Returns the same itineraries as getItineraries produces, but sorted according
   * to total itinerary cost, in non-decreasing order.
   * @param date a departure date (in the format YYYY-MM-DD)
   * @param origin a flight original
   * @param destination a flight destination
   * @return itineraries that satisfy the requirements in the handout.
   *     Each itinerary in the output should contain one line per flight,
   *     in the format:
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places)
   *     followed by total duration (on its own line, in format HH:MM).
   */
  public static String getItinerariesSortedByCost(String date, String origin,
                                                  String destination) {
    return FlightBookingApplication.getItinerariesSortedByCost(date,origin,destination);
  }

  /**
   * Returns the same itineraries as getItineraries produces, but sorted according
   * to total itinerary travel time, in non-decreasing order.
   * @param date a departure date (in the format YYYY-MM-DD)
   * @param origin a flight original
   * @param destination a flight destination
   * @return itineraries that satisfy the requirements in the handout.
   *     Each itinerary in the output should contain one line per flight,
   *     in the format:
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places),
   *     followed by total duration (on its own line, in format HH:MM).
   */
  public static String getItinerariesSortedByTime(String date, String origin,
                                                  String destination) {
    return FlightBookingApplication.getItinerariesSortedByTime(date, origin, destination);
  }
}