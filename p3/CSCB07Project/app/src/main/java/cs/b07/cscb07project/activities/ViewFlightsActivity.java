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
import cs.b07.cscb07project.backend.transportation.Flight;
import cs.b07.cscb07project.backend.users.User;

import java.util.ArrayList;

public class ViewFlightsActivity extends AppCompatActivity implements OnItemClickListener {

  private String username;
  private String departureDate;
  private String origin;
  private String destination;
  private ArrayList<Flight> flights;
  private ListView flightListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_flights);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
    departureDate = intent.getStringExtra(Constants.DEPARTURE_DATE);
    origin = intent.getStringExtra(Constants.ORIGIN);
    destination = intent.getStringExtra(Constants.DESTINATION);

    flights = FlightBookingApplication.getFlights(departureDate, origin, destination);
    ArrayList<String> flightsText = getFlightText(flights);

    flightListView = (ListView) findViewById(R.id.flight_list_view);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview,
                                                         flightsText);
    flightListView.setAdapter(adapter);
    flightListView.setOnItemClickListener(this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), SearchFlightsActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    intent.putExtra(Constants.DEPARTURE_DATE, departureDate);
    intent.putExtra(Constants.ORIGIN, origin);
    intent.putExtra(Constants.DESTINATION, destination);
    startActivity(intent);
    return true;
  }

  @Override
  public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
    Flight flight = flights.get(position);
    User user = FlightBookingApplication.getUser(username);
    if (user.isAdmin()) {
      Intent intent = new Intent(this, AdminBookFlightActivity.class);
      intent.putExtra(Constants.USERNAME, username);
      intent.putExtra(Constants.DEPARTURE_DATE, departureDate);
      intent.putExtra(Constants.ORIGIN, origin);
      intent.putExtra(Constants.DESTINATION, destination);
      intent.putExtra(Constants.FLIGHT, flight.getFlightNumber());
      startActivity(intent);
    } else {
      Intent intent = new Intent(this, ClientBookFlightActivity.class);
      intent.putExtra(Constants.USERNAME, username);
      intent.putExtra(Constants.DEPARTURE_DATE, departureDate);
      intent.putExtra(Constants.ORIGIN, origin);
      intent.putExtra(Constants.DESTINATION, destination);
      intent.putExtra(Constants.FLIGHT, flight.getFlightNumber());
      startActivity(intent);
    }
  }

  /**
   * Sorts the Flights based on which radio button is selected.
   * @param view The view being used. Given automatically.
   */
  public void onRadioButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();

    // Check which radio button was clicked
    switch (view.getId()) {
      case R.id.sort_time:
        if (checked) {
          flights = ItinerarySort.sortFlightTravelTime(flights);
          ArrayList<String> flightsText = getFlightText(flights);
          ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview,
                                                                     flightsText);
          flightListView.setAdapter(adapter);
          adapter.notifyDataSetChanged();
        }
        break;
      case R.id.sort_cost:
        if (checked) {
          flights = ItinerarySort.sortFlightCost(flights);
          ArrayList<String> flightsText = getFlightText(flights);
          ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview,
                                                                     flightsText);
          flightListView.setAdapter(adapter);
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
   * @param flights An ArrayList of Flights to be converted to String.
   * @return An ArrayList of String representations of the given Flights.
   */
  private ArrayList<String> getFlightText(ArrayList<Flight> flights) {
    ArrayList<String> flightsText = new ArrayList<>();
    for (Flight flight : flights) {
      String flightText = String.format(Constants.FLIGHT_LISTVIEW_ROW, flight.getAirline(),
                                           flight.getFlightNumber(), flight.getOrigin(),
                                           flight.getDepartureTimeString(), flight.getDestination(),
                                           flight.getArrivalTimeString(),
                                           flight.getNumSeatsForSale(), flight.getCostString());
      flightsText.add(flightText);
    }
    return flightsText;
  }

}
