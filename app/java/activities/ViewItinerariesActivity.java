package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.application.ItinerarySort;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;
import cs.b07.cscb07project.backend.itinerary.FlightItinerary;
import cs.b07.cscb07project.backend.transportation.Flight;
import cs.b07.cscb07project.backend.users.User;

import java.util.ArrayList;

public class ViewItinerariesActivity extends AppCompatActivity implements OnItemClickListener {

  private String username;
  private ArrayList<FlightItinerary> itineraries;
  private ListView itineraryListView;
  private String departureDate;
  private String origin;
  private String destination;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_itineraries);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
    departureDate = intent.getStringExtra(Constants.DEPARTURE_DATE);
    origin = intent.getStringExtra(Constants.ORIGIN);
    destination = intent.getStringExtra(Constants.DESTINATION);

    itineraries = FlightBookingApplication.getItineraries(departureDate, origin, destination);
    ArrayList<String> itinerariesText = getItineraryText(itineraries);

    itineraryListView = (ListView) findViewById(R.id.itinerary_list_view);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview,
                                                         itinerariesText);
    itineraryListView.setAdapter(adapter);
    itineraryListView.setOnItemClickListener(this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), SearchItinerariesActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
    return true;
  }

  @Override
  public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
    FlightItinerary itinerary = itineraries.get(position);
    User user = FlightBookingApplication.getUser(username);
    if (user.isAdmin()) {
      Intent intent = new Intent(this, AdminBookFlightItineraryActivity.class);
      intent.putExtra(Constants.USERNAME, username);
      intent.putExtra(Constants.DEPARTURE_DATE, departureDate);
      intent.putExtra(Constants.ORIGIN, origin);
      intent.putExtra(Constants.DESTINATION, destination);
      intent.putExtra(Constants.ITINERARY, itinerary);
      startActivity(intent);
    } else {
      Intent intent = new Intent(this, ClientBookFlightItineraryActivity.class);
      intent.putExtra(Constants.USERNAME, username);
      intent.putExtra(Constants.DEPARTURE_DATE, departureDate);
      intent.putExtra(Constants.ORIGIN, origin);
      intent.putExtra(Constants.DESTINATION, destination);
      intent.putExtra(Constants.ITINERARY, itinerary);
      startActivity(intent);
    }
  }

  /**
   * Sorts the Itineraries based on which radio button is selected.
   * @param view The view being used. Given automatically.
   */
  public void onRadioButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();

    // Check which radio button was clicked
    switch (view.getId()) {
      case R.id.sort_time:
        if (checked) {
          itineraries = ItinerarySort.sortItineraryTravelTime(itineraries);
          ArrayList<String> itinerariesText = getItineraryText(itineraries);
          ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview,
                                                                     itinerariesText);
          itineraryListView.setAdapter(adapter);
          adapter.notifyDataSetChanged();
        }
        break;
      case R.id.sort_cost:
        if (checked) {
          itineraries = ItinerarySort.sortItineraryCost(itineraries);
          ArrayList<String> itinerariesText = getItineraryText(itineraries);
          ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview,
                                                                     itinerariesText);
          itineraryListView.setAdapter(adapter);
          adapter.notifyDataSetChanged();
        }
        break;
      default: {
        break;
      }
    }
  }

  /**
   * Returns the String representation of the given Flights.
   * @param itineraries An ArrayList of Itineraries to be converted to String.
   * @return An ArrayList of String representations of the given Itineraries.
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
