package cs.b07.cscb07project.backend.application;

import cs.b07.cscb07project.backend.itinerary.FlightItinerary;
import cs.b07.cscb07project.backend.transportation.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * An ItinerarySort class to sort all of the FlightItinerary objects using cost
 * and travel time. Uses the merge sort algorithm to sort the lists of
 * FlightItinerary.
 * 
 * @author Zain Amir, Raphael Alviz, Ian Ferguson, Andy Liang, Johnathan Tsoi
 */
public class ItinerarySort {

  /**
   * Returns a list of flightItineraries sorted by cost of each Itinerary.
   *
   * @param flights
   *          The list to sort by cost
   * @return A sorted list.
   */
  public static ArrayList<Flight> sortFlightCost(ArrayList<Flight> flights) {
    if (flights.size() < 2) {
      return flights;
    } else {
      // separate the list in the middle
      int midpoint = (int) Math.floor(flights.size() / 2);
      // separate them into two different list to sort them
      ArrayList<Flight> listOne = new ArrayList<>(flights.subList(0, midpoint));
      ArrayList<Flight> listTwo = new ArrayList<>(flights.subList(midpoint, flights.size()));
      ArrayList<Flight> sortOne = sortFlightCost(listOne);
      ArrayList<Flight> sortTwo = sortFlightCost(listTwo);
      // private method to sort the lists regarding costs
      return sortFlightList(sortOne, sortTwo);
    }

  }

  /**
   * Returns the sorted list in terms of totalCost.
   *
   * @param sortOne
   *          The first list to compare values with
   * @param sortTwo
   *          The second list to compare values with
   * @return A sorted list
   */
  private static ArrayList<Flight> sortFlightList(ArrayList<Flight> sortOne,
                                                  ArrayList<Flight> sortTwo) {
    // to store the sorted list
    ArrayList<Flight> sortedList = new ArrayList<>();
    // variables to keep track
    int counter = 0;
    int size = 0;
    // loop until one of the lists is empty basically
    // using variables to see when it will be empty
    while (counter < sortOne.size() && size < sortTwo.size()) {
      if ((sortOne.get(counter)).getCost() < (sortTwo.get(size)).getCost()) {
        sortedList.add(sortOne.get(counter));
        counter += 1;
      } else {
        sortedList.add(sortTwo.get(size));
        size += 1;
      }
    }
    // just append the rest of the list depending what was emptied before
    if (counter == sortOne.size()) {
      ArrayList<Flight> sublist = new ArrayList<>(sortTwo.subList(size, sortTwo.size()));
      sortedList.addAll(sublist);
    } else {
      ArrayList<Flight> sublist = new ArrayList<>(sortOne.subList(counter, sortOne.size()));
      sortedList.addAll(sublist);
    }
    return sortedList;
  }

  /**
   * Returns a list of flightItineraries sorted by cost of each Itinerary.
   *
   * @param itineraries
   *          The list to sort by cost
   * @return A sorted list.
   */
  public static ArrayList<FlightItinerary> sortItineraryCost(ArrayList<FlightItinerary>
                                                                 itineraries) {
    if (itineraries.size() < 2) {
      return itineraries;
    } else {
      // separate the list in the middle
      int midpoint = (int) Math.floor(itineraries.size() / 2);
      // separate them into two different list to sort them
      ArrayList<FlightItinerary> listOne = new ArrayList<>(itineraries.subList(0, midpoint));
      ArrayList<FlightItinerary> listTwo = new ArrayList<>(itineraries
                                                                  .subList(midpoint,
                                                                              itineraries.size()));
      ArrayList<FlightItinerary> sortOne = sortItineraryCost(listOne);
      ArrayList<FlightItinerary> sortTwo = sortItineraryCost(listTwo);
      // private method to sort the lists regarding costs
      return sortItineraryList(sortOne, sortTwo);
    }

  }

  /**
   * Returns the sorted list in terms of totalCost.
   * 
   * @param sortOne
   *          The first list to compare values with
   * @param sortTwo
   *          The second list to compare values with
   * @return A sorted list
   */
  private static ArrayList<FlightItinerary> sortItineraryList(ArrayList<FlightItinerary> sortOne,
                                                      ArrayList<FlightItinerary> sortTwo) {
    // to store the sorted list
    ArrayList<FlightItinerary> sortedList = new ArrayList<>();
    // variables to keep track
    int counter = 0;
    int size = 0;
    // loop until one of the lists is empty basically
    // using variables to see when it will be empty
    while (counter < sortOne.size() && size < sortTwo.size()) {
      if ((sortOne.get(counter)).totalCost() < (sortTwo.get(size)).totalCost()) {
        sortedList.add(sortOne.get(counter));
        counter += 1;
      } else {
        sortedList.add(sortTwo.get(size));
        size += 1;
      }
    }
    // just append the rest of the list depending what was emptied before
    if (counter == sortOne.size()) {
      ArrayList<FlightItinerary> sublist = new ArrayList<>(sortTwo.subList(size, sortTwo.size()));
      sortedList.addAll(sublist);
    } else {
      ArrayList<FlightItinerary> sublist = new ArrayList<>(sortOne.subList(counter,
                                                                              sortOne.size()));
      sortedList.addAll(sublist);
    }
    return sortedList;
  }

  /**
   * Returns a list of flightItineraries sorted by travel time of each
   * Itinerary.
   *
   * @param itineraries
   *          the list to be sorted by travel time.
   * @return the sorted list.
   */
  public static ArrayList<Flight> sortFlightTravelTime(ArrayList<Flight> itineraries) {
    if (itineraries.size() < 2) {
      return itineraries;
    } else {
      // separate the list in the middle
      int midpoint = (int) Math.floor(itineraries.size() / 2);
      // separate them into two different lists to sort them
      ArrayList<Flight> listOne = new ArrayList<>(itineraries.subList(0, midpoint));
      ArrayList<Flight> listTwo = new ArrayList<>(itineraries.subList(midpoint,
                                                                         itineraries.size()));
      ArrayList<Flight> sortOne = sortFlightTravelTime(listOne);
      ArrayList<Flight> sortTwo = sortFlightTravelTime(listTwo);
      // private method to sort the lists
      return sortFlightListTravel(sortOne, sortTwo);
    }

  }

  /**
   * Returns the sorted list in terms of TravelTime.
   *
   * @param sortOne
   *          the first list to compare values with
   * @param sortTwo
   *          the second list to compare values with
   * @return the sorted list
   */
  private static ArrayList<Flight> sortFlightListTravel(ArrayList<Flight> sortOne,
                                                        ArrayList<Flight> sortTwo) {
    // to store the sorted list
    ArrayList<Flight> sortedList = new ArrayList<>();
    int counter = 0;
    int size = 0;
    while (counter < sortOne.size() && size < sortTwo.size()) {
      if ((sortOne.get(counter)).getTravelTime() < (sortTwo.get(size)).getTravelTime()) {
        sortedList.add(sortOne.get(counter));
        counter += 1;
      } else {
        sortedList.add(sortTwo.get(size));
        size += 1;
      }
    }
    if (counter == sortOne.size()) {
      List<Flight> sublist = sortTwo.subList(size, sortTwo.size());
      sortedList.addAll(sublist);
    } else {
      List<Flight> sublist = sortOne.subList(counter, sortOne.size());
      sortedList.addAll(sublist);
    }
    return sortedList;
  }

  /**
   * Returns a list of flightItineraries sorted by travel time of each
   * Itinerary.
   * 
   * @param itineraries
   *          the list to be sorted by travel time.
   * @return the sorted list.
   */
  public static ArrayList<FlightItinerary> sortItineraryTravelTime(
          ArrayList<FlightItinerary> itineraries) {
    if (itineraries.size() < 2) {
      return itineraries;
    } else {
      // separate the list in the middle
      int midpoint = (int) Math.floor(itineraries.size() / 2);
      // separate them into two different lists to sort them
      ArrayList<FlightItinerary> listOne = new ArrayList<>(itineraries.subList(0, midpoint));
      ArrayList<FlightItinerary> listTwo = new ArrayList<>(itineraries
                                                                  .subList(midpoint,
                                                                              itineraries.size()));
      ArrayList<FlightItinerary> sortOne = sortItineraryTravelTime(listOne);
      ArrayList<FlightItinerary> sortTwo = sortItineraryTravelTime(listTwo);
      // private method to sort the lists
      return sortItineraryListTravel(sortOne, sortTwo);
    }

  }

  /**
   * Returns the sorted list in terms of TravelTime.
   * 
   * @param sortOne
   *          the first list to compare values with
   * @param sortTwo
   *          the second list to compare values with
   * @return the sorted list
   */
  private static ArrayList<FlightItinerary> sortItineraryListTravel(
          ArrayList<FlightItinerary> sortOne, ArrayList<FlightItinerary> sortTwo) {
    // to store the sorted list
    ArrayList<FlightItinerary> sortedList = new ArrayList<>();
    int counter = 0;
    int size = 0;
    while (counter < sortOne.size() && size < sortTwo.size()) {
      if ((sortOne.get(counter)).totalTravelTime() < (sortTwo.get(size)).totalTravelTime()) {
        sortedList.add(sortOne.get(counter));
        counter += 1;
      } else {
        sortedList.add(sortTwo.get(size));
        size += 1;
      }
    }
    if (counter == sortOne.size()) {
      ArrayList<FlightItinerary> sublist = new ArrayList<>(sortTwo.subList(size, sortTwo.size()));
      sortedList.addAll(sublist);
    } else {
      ArrayList<FlightItinerary> sublist = new ArrayList<>(sortOne.subList(counter,
                                                                              sortOne.size()));
      sortedList.addAll(sublist);
    }
    return sortedList;
  }

}
