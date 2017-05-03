package cs.b07.cscb07project.backend.transportation;

import java.io.Serializable;
import java.text.ParseException;

/**
 * A class representing a flight between an origin and a destination on a particular date.
 * Each Flight must have a unique flight number.
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class Flight extends Transportation implements Serializable {
  // Private instance variables.
  private static final long serialVersionUID = -5290186165067329550L;
  private String flightNumber;
  private String airline;
  private int numSeats;
  private int seatsSold;

  /**
   * Creates a new Flight object with a unique flight number, and a given departureTime,
   * arrivalTime, airline, origin, destination and cost. Will calculate total travel time.
   * @param flightNumber A String representing the unique flight number assigned to this
   *     Flight.
   * @param departureTime A String representing the departure time of the Flight, must be in
   *     the format YYYY-MM-DD HH:MM.
   * @param arrivalTime A String representing the arrival time of the Flight, must be in
   *     the format YYYY-MM-DD HH:MM.
   * @param airline A String representing the name of the airline operating the Flight.
   * @param origin A String representing the origin city that the Flight departs from.
   * @param destination A String representing the destination city that the Flight arrives
   *     at.
   * @param cost A double representing the total cost of the Flight. The cost has exactly two
   *     decimal places.
   * @param numSeats An int representing the the total number of available seats that are
   *     available for sale on that Flight.
   * @throws ParseException Throws a ParseException if the departureTime or arrivalTime are
   *     incorrectly formatted.
   */
  public Flight(String flightNumber, String departureTime, String arrivalTime, String airline,
                String origin, String destination, double cost, int numSeats)
      throws ParseException {
    super(departureTime, arrivalTime, origin, destination, cost);
    this.flightNumber = flightNumber;
    this.airline = airline;
    this.numSeats = numSeats;
    this.seatsSold = 0;
  }

  /**
   * Returns a String representing the unique Flight number assigned to the Flight.
   * @return A String representing the unique Flight number assigned to the Flight.
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  /**
   * Returns a String representing the name of the airline operating the Flight.
   * @return A String representing the name of the airline operating the Flight.
   */
  public String getAirline() {
    return airline;
  }

  /**
   * Changes the name of the airline operating the Flight to the given String.
   * @param airline A String representing the name of the airline operating the Flight.
   */
  public void setAirline(String airline) {
    this.airline = airline;
  }
  
  /**
   * Sets the total number of seats on the flight.
   * @param numSeats An int representing the number of seats on the Flight.
   */
  public void setTotalSeats(int numSeats) {
    this.numSeats = numSeats;
  }

  /**
   * Returns the number of seats on the Flight.
   * @return An int representing the number of seats on the Flight.
   */
  public int getNumSeats() {
    return this.numSeats;
  }

  /**
   * Returns the number of seats still unbooked on the Flight.
   * @return An int representing the number of seats remaining on the Flight (not booked).
   */
  public int getNumSeatsForSale() {
    return (this.numSeats - this.seatsSold);
  }

  /**
   * Increments the number of seats sold on the flight by 1.
   */
  public void increaseSeatsSold() {
    seatsSold++;
  }

  @Override
  public String toString() {
    return (getFlightNumber() + "," + getDepartureTimeString() + "," + getArrivalTimeString()
                + "," + getAirline() + "," + getOrigin() + "," + getDestination() + ","
                + getCostString());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((airline == null) ? 0 : airline.hashCode());
    result = prime * result + ((flightNumber == null) ? 0 : flightNumber.hashCode());
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
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Flight other = (Flight) obj;
    if (airline == null) {
      if (other.airline != null) {
        return false;
      }
    } else if (!airline.equals(other.airline)) {
      return false;
    }
    if (flightNumber == null) {
      if (other.flightNumber != null) {
        return false;
      }
    } else if (!flightNumber.equals(other.flightNumber)) {
      return false;
    }
    return true;
  }

}