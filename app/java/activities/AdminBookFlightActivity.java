package cs.b07.cscb07project.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;
import cs.b07.cscb07project.backend.itinerary.FlightItinerary;
import cs.b07.cscb07project.backend.transportation.Flight;
import cs.b07.cscb07project.backend.users.Client;

import java.util.ArrayList;

public class AdminBookFlightActivity extends AppCompatActivity implements OnItemSelectedListener {

  private Flight flight;
  private FlightItinerary itinerary;
  private String username;
  private ArrayList<String> clientNames;
  private Client client;
  private String departureDate;
  private String origin;
  private String destination;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_book_flight);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
    departureDate = intent.getStringExtra(Constants.DEPARTURE_DATE);
    origin = intent.getStringExtra(Constants.ORIGIN);
    destination = intent.getStringExtra(Constants.DESTINATION);

    clientNames = FlightBookingApplication.getUserDatabase().getClients();
    if (clientNames.isEmpty()) {
      findViewById(R.id.admin_book_flight).setEnabled(false);
    }

    Spinner spinner = (Spinner) findViewById(R.id.flight_booking_client_spinner);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                                                                      .simple_spinner_item,
                                                               clientNames);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);

    spinner.setOnItemSelectedListener(this);

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

    ((TextView) findViewById(R.id.admin_flight_to_book)).setText(itineraryInfo);
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

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    if (clientNames.isEmpty()) {
      findViewById(R.id.admin_book_flight).setEnabled(false);
    } else {
      findViewById(R.id.admin_book_flight).setEnabled(true);
      String clientUsername = (String) parent.getItemAtPosition(pos);
      client = (Client) FlightBookingApplication.getUser(clientUsername);
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    findViewById(R.id.admin_book_flight).setEnabled(false);
  }

  /**
   * Books the flight of the selected client.
   * @param view The view being used. Given automatically.
   */
  public void bookClientFlight(View view) {
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
      client.bookFlightItinerary(itinerary);
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
