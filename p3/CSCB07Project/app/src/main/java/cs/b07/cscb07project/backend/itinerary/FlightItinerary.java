package cs.b07.cscb07project.backend.itinerary;

import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.transportation.Flight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A FlightItinerary class that holds all flights from origin to destination.
 * Calculates the total cost of an itinerary and total travel time.
 * 
 * @author Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi and Raphael Alviz
 */
public class FlightItinerary implements Serializable {

  private static final long serialVersionUID = -8502012859363958827L;
  // instance variable to hold all flights in this Itinerary
  private ArrayList<Flight> flights = new ArrayList<>();
  // instance variables
  private String origin;
  private String destination;
  private String departureDateTime;

  /**
   * Allows the ItinineraryGenerator to create a FLightItininerary given a list
   * of Flights. Origin, destination and departureDateTime are set from the
   * list.
   * 
   * @param allFlights
   *          ArrayList of all flights in the itinerary.
   */
  public FlightItinerary(ArrayList<Flight> allFlights) {
    flights = allFlights;
    // set the origin using the flights in the list
    this.origin = flights.get(0).getOrigin();
    this.destination = flights.get(flights.size() - 1).getDestination();
    this.departureDateTime = flights.get(0).getDepartureTimeString();
  }

  /**
   * Allows the creation of a FlightItinerary given only a single flight. Allows
   * for simple creation of FlightItinerary objects when there is a direct flight to the
   * destination.
   * 
   * @param flight
   *          A Flight object for which the FlightItinerary should be created.
   */
  public FlightItinerary(Flight flight) {
    flights.add(flight);
    this.origin = flights.get(0).getOrigin();
    this.destination = flights.get(flights.size() - 1).getDestination();
    this.departureDateTime = flights.get(0).getDepartureTimeString();
  }

  /**
   * Returns the Origin of the Itinerary.
   * 
   * @return A String representing the origin of the first Flight in the FlightItinerary.
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * Returns the Destination of the Itinerary.
   * 
   * @return A String representing the final destination of the FlightItinerary.
   */
  public String getDestination() {
    return destination;
  }
  
  /**
   * Returns a String representation of the total cost of the itinerary.
   * @return A String representing the total cost of the itinerary to exactly two decimal
   *     places.
   */
  public String getTotalCostString() {
    return String.format("%.2f", totalCost());
  }
  
  /**
   * Calculates the total cost of the itinerary.
   * 
   * @return A double representing total cost of all Flights in the itinerary.
   */
  public double totalCost() {
    double totalCost = 0;
    for (Flight flight : flights) {
      // get the cost of each flight
      totalCost += flight.getCost();
    }
    return totalCost;
  }
  
  /**
   * Returns a String representation of the total travel time of the FlightItinerary in the
   * format HH:MM.
   * @return A String representing the total flight time of the itinerary in the format HH:MM.
   */
  public String getTotalTravelTimeString() {
    Date originDepartureTime = flights.get(0).getDepartureTime();
    Date destinationArrivalTime = flights.get(flights.size() - 1).getArrivalTime();
    long travelTime = (destinationArrivalTime.getTime() - originDepartureTime.getTime());
    long durationInHours = TimeUnit.MILLISECONDS.toHours(travelTime);
    long durationInMinutes = (TimeUnit.MILLISECONDS.toMinutes(travelTime)
        - TimeUnit.HOURS.toMinutes(durationInHours));
    return String.format("%02d:%02d", durationInHours, durationInMinutes);
  }
  
  /**
   * Returns the total travel time of the itinerary in milliseconds.
   * @return A long representing the total flight time in milliseconds.
   */
  public long totalTravelTime() {
    Date originDepartureTime = flights.get(0).getDepartureTime();
    Date destinationArrivalTime = flights.get(flights.size() - 1).getArrivalTime();
    return (destinationArrivalTime.getTime() - originDepartureTime.getTime());
  }

  /**
   * Returns the List of all the flights from origin to destination in order.
   * 
   * @return An ordered ArrayList of all Flight objects in the FlightItinerary from origin to
   *     destination.
   */
  public ArrayList<Flight> sequenceOfFlights() {
    return flights;
  }

  @Override
  public String toString() {
    String itineraryString = "";
    for (Flight flight : flights) {
      itineraryString += String.format(Constants.FLIGHT_LISTVIEW_ROW, flight.getAirline(),
              flight.getFlightNumber(), flight.getOrigin(), flight.getDepartureTimeString(),
              flight.getDestination(), flight.getArrivalTimeString(), flight.getNumSeatsForSale(),
              flight.getCostString());
    }
    itineraryString += getTotalCostString() + "\n";
    itineraryString += getTotalTravelTimeString() + "\n";
    return itineraryString;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((departureDateTime == null) ? 0 : departureDateTime.hashCode());
    result = prime * result + ((destination == null) ? 0 : destination.hashCode());
    result = prime * result + ((flights == null) ? 0 : flights.hashCode());
    result = prime * result + ((origin == null) ? 0 : origin.hashCode());
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
    FlightItinerary other = (FlightItinerary) obj;
    if (departureDateTime == null) {
      if (other.departureDateTime != null) {
        return false;
      }
    } else if (!departureDateTime.equals(other.departureDateTime)) {
      return false;
    }
    if (destination == null) {
      if (other.destination != null) {
        return false;
      }
    } else if (!destination.equals(other.destination)) {
      return false;
    }
    if (flights == null) {
      if (other.flights != null) {
        return false;
      }
    } else if (!flights.equals(other.flights)) {
      return false;
    }
    if (origin == null) {
      if (other.origin != null) {
        return false;
      }
    } else if (!origin.equals(other.origin)) {
      return false;
    }
    return true;
  }

}

