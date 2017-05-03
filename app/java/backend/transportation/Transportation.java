package cs.b07.cscb07project.backend.transportation;

import cs.b07.cscb07project.backend.application.Constants;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * An abstract class representing a object used for Transportation. Each object possesses an
 * arrival time in the format YYYY-MM-DD HH:MM, a departure time in the format YYYY-MM-DD,
 * a origin from which it departs, a destination to which it arrives, and a total cost of
 * using the Transportation. The object will also calculate its total travel time based on
 * the given departure and arrival times.
 * @author Raphael Alviz, Zain Amir, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public abstract class Transportation implements Serializable {

  private static final long serialVersionUID = 4542302622039770097L;
  private Date arrivalTime;
  private Date departureTime;
  private String destination;
  private String origin;
  private double cost;

  /**
   * Creates a new Transportation object with an arrival time in the format YYYY-MM-DD HH:MM,
   * a departure time in the format YYYY-MM-DD, a origin from which it departs, a destination
   * to which it arrives, and a total cost of using the Transportation.
   * @param departureTime A String representing the departure time of the Transportation in
   *     the format YYYY-MM-DD HH:MM.
   * @param arrivalTime A String representing the arrival time of the Transportation in
   *     the format YYYY-MM-DD HH:MM.
   * @param origin A String representing the origin from which the Transportation departs.
   * @param destination A String representing the destination to which the Transportation
   *     arrives.
   * @param cost A double representing the total cost of using the Transportation. Should have
   *     exactly two decimal places.
   * @throws ParseException Throws a ParseException if departureTime or arrivalTime is given
   *     in format other than YYYY-MM-DD HH:MM.
   */
  public Transportation(String departureTime, String arrivalTime, String origin,
                        String destination, double cost) throws ParseException {
    SimpleDateFormat inputFormat = new SimpleDateFormat(Constants.TIME_DATE_FORMAT, Locale.US);
    this.departureTime = inputFormat.parse(departureTime);
    this.arrivalTime = inputFormat.parse(arrivalTime);
    this.origin = origin;
    this.destination = destination;
    this.cost = cost;
  }
  
  /**
   * Returns a double representing the cost of the Transportation to exactly two decimal places.
   * @return A double representing the cost of using the Transportation, has exactly two decimal
   *     places.
   */
  public double getCost() {
    return cost;
  }
  
  /**
   * Returns a double representing the cost of the Transportation to exactly two decimal places.
   * @return A double representing the cost of using the Transportation, has exactly two decimal
   *     places.
   */
  public String getCostString() {
    return String.format("%.2f", cost);
  }
  
  /**
   * Changes the cost of the transportation to the value of the double given. The double should
   * have exactly two decimal places.
   * @param cost The double to which the cost of using the Transportation will be changed.
   *     Should have exactly two decimal places.
   */
  public void setCost(double cost) {
    this.cost = cost;
  }
  
  /**
   * Returns a String representation of the arrival time of the Transportation.
   * @return A String representation of the arrival time of the Transportation in the format
   *     YYYY-MM-DD.
   */
  public Date getArrivalTime() {
    return arrivalTime;
  }
  
  /**
   * Returns a String representation of the arrival time of the Transportation.
   * @return A String representation of the arrival time of the Transportation in the format
   *     YYYY-MM-DD.
   */
  public String getArrivalTimeString() {
    SimpleDateFormat outputFormat = new SimpleDateFormat(Constants.TIME_DATE_FORMAT, Locale.US);
    return outputFormat.format(arrivalTime);
  }

  /**
   * Changes the arrival time of the Transportation object to String given.
   * @param arrivalTime The String representation of the arrival time to which the
   *     Transportations arrival time will be changed, in the format YYYY-MM-DD HH:MM.
   * @throws ParseException Throws a ParseException if arrivalTime is given in format other
   *     than YYYY-MM-DD HH:MM.
   */
  public void setArrivalTime(String arrivalTime) throws ParseException {
    SimpleDateFormat inputFormat = new SimpleDateFormat(Constants.TIME_DATE_FORMAT, Locale.US);
    this.arrivalTime = inputFormat.parse(arrivalTime);
  }
  
  /**
   * Returns a String representation of the departure time of the Transportation.
   * @return A String representation of the departure time of the Transportation in the format
   *     YYYY-MM-DD HH:MM.
   */
  public Date getDepartureTime() {
    return departureTime;
  }
  
  /**
   * Returns a String representation of the departure time of the Transportation.
   * @return A String representation of the departure time of the Transportation in the format
   *     YYYY-MM-DD HH:MM.
   */
  public String getDepartureTimeString() {
    SimpleDateFormat outputFormat = new SimpleDateFormat(Constants.TIME_DATE_FORMAT, Locale.US);
    return outputFormat.format(departureTime);
  }

  /**
   * Changes the departure time of the Transportation object to String given.
   * @param departureTime The String representation of the departure time to which the
   *     Transportations departure time will be changed, in the format YYYY-MM-DD HH:MM.
   * @throws ParseException Throws a ParseException if departureTime is given in format other
   *     than YYYY-MM-DD HH:MM.
   */
  public void setDepartureTime(String departureTime) throws ParseException {
    SimpleDateFormat inputFormat = new SimpleDateFormat(Constants.TIME_DATE_FORMAT, Locale.US);
    this.departureTime = inputFormat.parse(departureTime);
  }

  /**
   * Returns a String representation of the destination of the Transportation object.
   * @return A String representing the destination of the Transportation object.
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Changes the destination of the Transportation object to String given.
   * @param destination The String to which the destination of the Transportation object
   *     should be changed.
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Returns a String representation of the origin that the Transportation object departs from.
   * @return A String representing the origin of the Transportation object.
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * Changes the origin of the Transportation object to String given.
   * @param origin The String to which the origin of the Transportation object
   *     should be changed.
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  /**
   * Returns the difference between the arrival time and departure time of the Transportation
   * object in milliseconds.
   * @return A long representing the total travel time of the Transportation object in
   *     milliseconds.
   */
  public long getTravelTime() {
    return (arrivalTime.getTime() - departureTime.getTime());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((arrivalTime == null) ? 0 : arrivalTime.hashCode());
    long temp;
    temp = Double.doubleToLongBits(cost);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((departureTime == null) ? 0 : departureTime.hashCode());
    result = prime * result + ((destination == null) ? 0 : destination.hashCode());
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
    Transportation other = (Transportation) obj;
    if (arrivalTime == null) {
      if (other.arrivalTime != null) {
        return false;
      }
    } else if (!arrivalTime.equals(other.arrivalTime)) {
      return false;
    }
    if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost)) {
      return false;
    }
    if (departureTime == null) {
      if (other.departureTime != null) {
        return false;
      }
    } else if (!departureTime.equals(other.departureTime)) {
      return false;
    }
    if (destination == null) {
      if (other.destination != null) {
        return false;
      }
    } else if (!destination.equals(other.destination)) {
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
