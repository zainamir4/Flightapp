package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;
import cs.b07.cscb07project.backend.itinerary.FlightItinerary;
import cs.b07.cscb07project.backend.transportation.Flight;
import cs.b07.cscb07project.backend.users.Client;

import java.util.ArrayList;

public class AdminViewBookedClientItineraries extends AppCompatActivity
    implements OnItemSelectedListener {

  private String username;
  private ArrayList<String> clientNames;
  private ArrayList<FlightItinerary> itineraries;
  private ListView itineraryListView;
  private Client client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_view_booked_client_itineraries);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);

    clientNames = FlightBookingApplication.getUserDatabase().getClients();

    Spinner spinner = (Spinner) findViewById(R.id.admin_view_booked_client_itineraries_spinner);
    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout
                                                                       .simple_spinner_item,
                                                                clientNames);
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(spinnerAdapter);

    spinner.setOnItemSelectedListener(this);

    if (!clientNames.isEmpty()) {
      String clientUsername = clientNames.get(spinner.getFirstVisiblePosition());
      client = (Client) FlightBookingApplication.getUser(clientUsername);
      itineraries = client.getBookedFlightItineraries();
      ArrayList<String> itinerariesText = getItineraryText(itineraries);
      itineraryListView = (ListView) findViewById(R.id.admin_booked_client_itineraries_list_view);
      ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.activity_listview,
                                                           itinerariesText);
      itineraryListView.setAdapter(listAdapter);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
    return true;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    if (!clientNames.isEmpty()) {
      String clientUsername = (String) parent.getItemAtPosition(pos);
      client = (Client) FlightBookingApplication.getUser(clientUsername);
      itineraries = client.getBookedFlightItineraries();
      ArrayList<String> itinerariesText = getItineraryText(itineraries);
      ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.activity_listview,
                                                           itinerariesText);
      itineraryListView.setAdapter(listAdapter);
      listAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
  }

  /**
   * Given an ArrayList of itineraries, returns an ArrayList of string representations of the
   * given itineraries.
   * @param itineraries An array list of itineraries to be converted to string.
   * @return An ArrayList of String representations of flight itineraries.
   */
  private ArrayList<String> getItineraryText(ArrayList<FlightItinerary> itineraries) {
    ArrayList<String> itineraryText = new ArrayList<>();
    for (FlightItinerary itinerary : itineraries) {
      ArrayList<Flight> flights = itinerary.sequenceOfFlights();
      String itineraryString = "";
      for (Flight flight : flights) {
        itineraryString += String.format(Constants.FLIGHT_ITINERARY_LISTVIEW_ROW,
                                            flight.getAirline(), flight.getFlightNumber(),
                                            flight.getOrigin(), flight.getDepartureTimeString(),
                                            flight.getDestination(), flight.getArrivalTimeString(),
                                            flight.getNumSeatsForSale());
      }
      itineraryString += String.format(Constants.TOTAL_LISTVIEW_ROW,
                                          itinerary.getTotalTravelTimeString(),
                                          itinerary.getTotalCostString());
      itineraryText.add(itineraryString);
    }
    return itineraryText;
  }

}
