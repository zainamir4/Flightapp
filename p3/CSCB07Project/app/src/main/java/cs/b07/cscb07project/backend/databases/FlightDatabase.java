package cs.b07.cscb07project.backend.databases;

import cs.b07.cscb07project.backend.transportation.Flight;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A database containing Flight objects. Allows adding and removing of Flight objects and
 * as well as retrieval of specific Flights by origin or flight number.
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class FlightDatabase implements Serializable {
  
  private static final long serialVersionUID = -6781132619699892648L;
  // Both data structures contain pointers to the same flight objects.
  // HashMap mapping origin to TreeSets of Flights from that origin, forming adjacency list
  // of a directed graph.
  private Map<String, Set<Flight>> flightPathDatabase;
  // HashMap mapping flight numbers to the corresponding Flights for quick access and
  // modification of individual Flights.
  private Map<String, Flight> flightDatabase;

  /**
   * Creates a new empty FlightDatabase.
   */
  public FlightDatabase() {
    flightPathDatabase = new HashMap<>();
    flightDatabase = new HashMap<>();
  }
  
  /**
   * Adds a Flight to be stored in the FlightDatabase. If Flight with given flight number
   * is already in the FlightDatabase then new Flight will override previous entry with given
   * flight number.
   * @param flight The Flight to be added to the FlightDatabase.
   */
  public void addFlight(Flight flight) {
    // Do not allow Flights with the same flight number, remove duplicate and override with
    // new Flight.
    if (flightDatabase.containsKey(flight.getFlightNumber())) {
      this.removeFlight(flight.getFlightNumber());
    }
    // Add the Flight to the flightDatabase with flight number as its key.
    flightDatabase.put(flight.getFlightNumber(), flight);
    
    String origin = flight.getOrigin();
    String destination = flight.getDestination();
    // Check if flights are already leaving from the given Flights origin.
    if (flightPathDatabase.containsKey(origin)) {
      flightPathDatabase.get(origin).add(flight);
    } else {
      // If not create new HashSet of Flights from that origin.
      flightPathDatabase.put(origin, new HashSet<Flight>());
      flightPathDatabase.get(origin).add(flight);
    }
    // Check if destination is in the HashMap, if not add it to the adjacency list with
    // empty set of flights leaving from it.
    if (!flightPathDatabase.containsKey(destination)) {
      flightPathDatabase.put(destination, new HashSet<Flight>());
    }
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
   * @throws ParseException Throws ParseException if flight data is not in YYYY-MM-DD HH:MM
   *     format.
   */
  public void addFlight(String flightNumber, String departureTime, String arrivalTime,
      String airline, String origin, String destination, double cost, int numSeats)
          throws ParseException {
    Flight flight = new Flight(flightNumber, departureTime, arrivalTime, airline,  origin,
        destination, cost, numSeats);
    this.addFlight(flight);
  }
  
  /**
   * Removes the given Flight from the FlightDatabase, if the Flight is not in the
   * FlightDatabase then removeFlight does nothing.
   * @param flightNumber The flight number of the Flight to be removed from the 
   *     FlightDatabase.
   */
  public void removeFlight(String flightNumber) {
    if (flightDatabase.containsKey(flightNumber)) {
      Flight flight = flightDatabase.remove(flightNumber);
      flightPathDatabase.get(flight.getOrigin()).remove(flight);
    }
  }
  
  /**
   * Returns the Flight object corresponding to the given flight number. Returns null if no
   * Flight with the given number exists in the FlightDatabase
   * @param flightNumber The flight number of the Flight to be returned.
   * @return The Flight object with the given flight number, or null if no such Flight object
   *     exists in the FlightDatabase.
   * @throws NoSuchFlightException Throws a NoSuchFlightException if given flight number is
   *     not found in the FlightDatabase.
   */
  public Flight getFlight(String flightNumber) throws NoSuchFlightException {
    if (!flightDatabase.containsKey(flightNumber)) {
      throw new NoSuchFlightException();
    } else {
      return flightDatabase.get(flightNumber);
    }
  }

  /**
   * Returns a set of all Flights departing from a given origin city, or null if no Flights
   * depart from that city.
   * @param origin The name of the origin city for which departing Flights should be returned.
   * @return A set of all Flights departing from the given origin city, or null if no Flights
   *     depart from that city..
   */
  public Set<Flight> getDepartingFlights(String origin) {
    return flightPathDatabase.get(origin);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String flightString = "";
    for (Flight flight : flightDatabase.values()) {
      flightString += (flight.toString() + "\n");
    }
    return flightString;
  }

  /**
   * Returns an arraylist of all flight numbers in flight database.
   *
   * @return An arraylist of all flight numbers in flight database.
   */
  public ArrayList<String> getFlights() {
    Collection<Flight> flightsData = flightDatabase.values();
    ArrayList<String> flightNumbers = new ArrayList<>();
    for (Flight flight : flightsData) {
      flightNumbers.add(flight.getFlightNumber());
    }
    return flightNumbers;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((flightDatabase == null) ? 0 : flightDatabase.hashCode());
    result = prime * result + ((flightPathDatabase == null) ? 0 : flightPathDatabase.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
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
    FlightDatabase other = (FlightDatabase) obj;
    if (flightDatabase == null) {
      if (other.flightDatabase != null) {
        return false;
      }
    } else if (!flightDatabase.equals(other.flightDatabase)) {
      return false;
    }
    if (flightPathDatabase == null) {
      if (other.flightPathDatabase != null) {
        return false;
      }
    } else if (!flightPathDatabase.equals(other.flightPathDatabase)) {
      return false;
    }
    return true;
  }
}
