package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;
import cs.b07.cscb07project.backend.itinerary.FlightItinerary;
import cs.b07.cscb07project.backend.transportation.Flight;
import cs.b07.cscb07project.backend.users.Client;

import java.util.ArrayList;

public class ViewBookedClientItinerariesActivity extends AppCompatActivity {

  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_booked_client_itineraries);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
    Client client = (Client) FlightBookingApplication.getUser(username);

    ArrayList<FlightItinerary> itineraries = client.getBookedFlightItineraries();
    ArrayList<String> itinerariesText = getItineraryText(itineraries);

    ListView itineraryListView = (ListView) findViewById(R.id.view_booked_itineraries_list_view);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview,
                                                         itinerariesText);
    itineraryListView.setAdapter(adapter);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
    return true;
  }

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
