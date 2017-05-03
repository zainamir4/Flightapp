package cs.b07.cscb07project.backend.application;

import cs.b07.cscb07project.backend.databases.FlightDatabase;
import cs.b07.cscb07project.backend.itinerary.FlightItinerary;
import cs.b07.cscb07project.backend.transportation.Flight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * A class that implements the search algorithms of the application.
 * Responsible for:
 * 1) Generating all valid Flights from an origin to a given destination on a given departure
 * date.
 * 2) Generating a sequence of all valid FlightItinerary objects from a given origin to a given
 * destination on a given departure date.
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class ItineraryGenerator {
  
  /**
   * Returns an ArrayList of Flights departing on the given date, from the given origin
   * to the given destination
   * @param database The FlightDatabase object containing the Flights to be searched.
   * @param date A String YYYY-MM-DD representing the departure date of Flights to be
   *     returned.
   * @param origin A String representing the origin city of the Flights to be returned.
   * @param destination A String representing the destination city of the Flights to be
   *     returned.
   * @return An ArrayList of Flights from origin to destination departing on date.
   * @throws ParseException Throws if date is given in format other than YYYY-MM-DD.
   */
  public static ArrayList<Flight> getFlights(FlightDatabase database, String date,
      String origin, String destination) throws ParseException {
    Set<Flight> flights = database.getDepartingFlights(origin);
    ArrayList<Flight> departingFlights = new ArrayList<>();
    // If no Flights leave from this origin return an empty list.
    if (flights == null) {
      return departingFlights;
    } else {
      SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DAY_DATE_FORMAT, Locale.US);
      Date userDepartingDate = dateFormat.parse(date);
      for (Flight flight : flights) {
        Date flightDepartingDate = dateFormat.parse(flight.getDepartureTimeString());
        // If the Flight leaves on the given date and goes to the given destination return
        // that Flight.
        if (userDepartingDate.equals(flightDepartingDate)
            && flight.getDestination().equals(destination)) {
          departingFlights.add(flight);
        }
      }
      return departingFlights;
    }    
  }
  
  /**
   * Returns an ArrayList of valid FlightItinerary objects from a given origin to a given
   * destination, departing on a given departure date, maximum layover time specified in
   * Constants class.
   * @param database The FlgihtDatabase containing all Flight objects to search for valid
   *     flight paths.
   * @param date A String (YYYY-MM-DD) representing the departure departure date for the
   *     FlightItineraries.
   * @param origin A String representing the origin city for the FlightItineraries.
   * @param destination A String representing the destination city for the FlightItineraries.
   * @return An ArrayList of FlightItinerary objects from the given origin to the given
   *     destination departing on the given departure date.
   * @throws ParseException Throws a ParseException if date is given in format other than
   *     YYYY-MM-DD.
   */
  public static ArrayList<FlightItinerary> getItineraries(FlightDatabase database, String date,
      String origin, String destination) throws ParseException {
    // Get all flights departing from the given origin.
    Set<Flight> flights = database.getDepartingFlights(origin);
    ArrayList<FlightItinerary> validFlightItineraries = new ArrayList<>();
    // If no Flights depart from that origin return a empty list of FlightItineraries.
    if (flights == null) {
      return validFlightItineraries;
    } else {
      // Parse the given date from a string into a Date object, using predefined format.
      SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DAY_DATE_FORMAT, Locale.US);
      Date userDepartingDate = dateFormat.parse(date);
      // Maintain an ArrayList of visited origins to prevent including cycles.
      ArrayList<String> visitedOrigins = new ArrayList<>();
      visitedOrigins.add(origin);
      // For each flight departing from the given origin.
      for (Flight flight : flights) {
        // Parse the departing date of the Flight into a Date object using predefined format
        // for comparison.
        Date flightDepartingDate = dateFormat.parse(flight.getDepartureTimeString());
        // Check if the flight is departing on the given departure date.
        if (userDepartingDate.equals(flightDepartingDate)) {
          // If it is a direct Flight from origin to destination, create a new FlightItinerary
          // for that Flight and add it to the list of valid FlightItineraries.
          if (flight.getDestination().equalsIgnoreCase(destination)) {
            FlightItinerary newItinerary = new FlightItinerary(flight);
            validFlightItineraries.add(newItinerary);
          } else {
            // If it is not a direct flight, add the flight to a list of potential flight paths
            // and call flightPathSearch to generate any valid FlightItineraries from the
            // given origin to destination including this flight.
            ArrayList<Flight> flightPath = new ArrayList<>();
            flightPath.add(flight);
            // Add all valid FlightItineraries found to the ArrayList of valid
            // FlightItineraries.
            validFlightItineraries.addAll(flightPathSearch(database, flightPath,
                visitedOrigins, destination));
          }
        }
      }
      return validFlightItineraries;
    }
  }
  
  /**
   * Returns an ArrayList of valid FlightItinerary objects starting at the given flight path
   * and continuing to the given destination, without cycles.
   * @param database The FlgihtDatabase containing all Flight objects to search for valid
   *     flight paths.
   * @param flightPath An ArrayList of Flight objects making up the prefix of the flight path
   *     being explored, flightPathSearch will search starting at the destination of the last
   *     Flight in the ArrayList and using the arrival time of that Flight as the basis for
   *     calculating stop over time and checking valid Flights.
   * @param visitedOrigins An ArrayList of Strings representing Flight origins which have
   *     been visited on the given flight path.
   * @param destination A String representing the destination city for the FlightItineraries.
   * @return An ArrayList of FlightItinerary objects made up of all valid flight paths
   *     comprising the given flight path to the given destination without cycles.
   */
  private static ArrayList<FlightItinerary> flightPathSearch(FlightDatabase database,
      ArrayList<Flight> flightPath, ArrayList<String> visitedOrigins, String destination) {
    // Adds origin of the last flight in the flight path to the list of visited origins, used
    // to prevent cycles.
    visitedOrigins.add(flightPath.get(flightPath.size() - 1).getOrigin());
    // Set the new origin as the destination of the last flight in the flight path.
    String newOrigin = flightPath.get(flightPath.size() - 1).getDestination();
    // Get all flights departing from the new origin.
    Set<Flight> flights = database.getDepartingFlights(newOrigin);
    ArrayList<FlightItinerary> validFlightItineraries = new ArrayList<>();
    // If no Flights depart from that origin return a empty list of FlightItineraries.
    if (flights == null) {
      return validFlightItineraries;
    } else {
      // Set the arrival time of the last flight on the flight path as a Date object.
      Date arrivalTime = flightPath.get(flightPath.size() - 1).getArrivalTime();
      // For each Flight departing from the new origin.
      for (Flight flight : flights) {
        // Set the departure time of that Flight as a Date object.
        Date departureTime = flight.getDepartureTime();
        // If the current Flight departs after the last Flight on the flight path arrives, and
        // if the stop over between Flights is less than 6 hours, and if the destination of the
        // current Flight has not yet been visited.
        if (((departureTime.getTime() - arrivalTime.getTime())
                >= TimeUnit.MINUTES.toMillis(Constants.STOPOVERMIN))
            && ((departureTime.getTime() - arrivalTime.getTime())
                <= TimeUnit.HOURS.toMillis(Constants.STOPOVERMAX))
            && !visitedOrigins.contains(flight.getDestination())) {
          @SuppressWarnings("unchecked")
          ArrayList<Flight> newFlightPath = (ArrayList<Flight>) flightPath.clone();
          // If it is a direct Flight from the last Flight in the flight path to destination,
          // create a new FlightItinerary for that Flight and add it to the list of valid
          // FlightItineraries.
          if (flight.getDestination().equalsIgnoreCase(destination)) {
            newFlightPath.add(flight);
            FlightItinerary newItinerary = new FlightItinerary(newFlightPath);
            validFlightItineraries.add(newItinerary);
          } else {
            // If it is not a direct Flight, add the Flight to the list of potential flight
            // paths and call flightPathSearch to generate any valid FlightItineraries from the
            // given flight path to the given destination including this flight.
            newFlightPath.add(flight);
            // Add all valid FlightItineraries found to the ArrayList of valid
            // FlightItineraries.
            validFlightItineraries.addAll(flightPathSearch(database, newFlightPath,
                visitedOrigins, destination));
          }
        }
      }
      return validFlightItineraries;
    }
  }
}
