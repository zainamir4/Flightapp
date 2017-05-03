package cs.b07.cscb07project.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;
import cs.b07.cscb07project.backend.itinerary.FlightItinerary;
import cs.b07.cscb07project.backend.transportation.Flight;
import cs.b07.cscb07project.backend.users.Client;

import java.util.ArrayList;

public class ClientBookFlightActivity extends AppCompatActivity {

  private Flight flight;
  private FlightItinerary itinerary;
  private String username;
  private Client user;
  private String departureDate;
  private String origin;
  private String destination;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_book_flight);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
    user = (Client) FlightBookingApplication.getUser(username);
    departureDate = intent.getStringExtra(Constants.DEPARTURE_DATE);
    origin = intent.getStringExtra(Constants.ORIGIN);
    destination = intent.getStringExtra(Constants.DESTINATION);

    String flightNumber = intent.getStringExtra(Constants.FLIGHT);
    flight = FlightBookingApplication.getFlight(flightNumber);
    itinerary = new FlightItinerary(flight);

    ArrayList<Flight> flights = itinerary.sequenceOfFlights();
    String itineraryInfo = "";
    for (Flight flight : flights) {
      itineraryInfo += String.format(Constants.FLIGHT_ITINERARY_LISTVIEW_ROW, flight.getAirline(),
                                        flight.getFlightNumber(), flight.getOrigin(),
                                        flight.getDepartureTimeString(), flight.getDestination(),
                                        flight.getArrivalTimeString(), flight.getNumSeatsForSale());
    }
    itineraryInfo += String.format(Constants.TOTAL_LISTVIEW_ROW,
                                      itinerary.getTotalTravelTimeString(),
                                      itinerary.getTotalCostString());

    ((TextView) findViewById(R.id.flight_to_book)).setText(itineraryInfo);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), ViewFlightsActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    intent.putExtra(Constants.DEPARTURE_DATE, departureDate);
    intent.putExtra(Constants.ORIGIN, origin);
    intent.putExtra(Constants.DESTINATION, destination);
    startActivity(intent);
    return true;
  }

  /**
   * Books the flight selected by the client. Books flight as an itinerary.
   * @param view The view being used. Given automatically.
   */
  public void bookFlight(View view) {
    // AlertDialog for full flight
    AlertDialog.Builder fullAlert = new AlertDialog.Builder(this);
    fullAlert.setMessage(Constants.NO_SEATS_AVAILABLE);
    fullAlert.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        finish();
      }
    }).setTitle(Constants.FULL_FLIGHT);

    // AlertDialog for successful booking
    AlertDialog.Builder successfulAlert = new AlertDialog.Builder(this);
    successfulAlert.setMessage(Constants.SUCCESSFUL_BOOK_FLIGHT);
    successfulAlert.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        finish();
      }
    }).setTitle(Constants.SUCCESSFUL_BOOKING);

    if (flight.getNumSeatsForSale() < 1) {
      fullAlert.show();
    } else {
      flight.increaseSeatsSold();
      user.bookFlightItinerary(itinerary);
      MainActivity.clientManager.saveToFile();
      MainActivity.manager.saveToFile();
      successfulAlert.show();
      Intent intent = new Intent(getApplicationContext(), ViewFlightsActivity.class);
      intent.putExtra(Constants.USERNAME, username);
      intent.putExtra(Constants.DEPARTURE_DATE, departureDate);
      intent.putExtra(Constants.ORIGIN, origin);
      intent.putExtra(Constants.DESTINATION, destination);
      startActivity(intent);
    }

  }

}
