package cs.b07.cscb07project.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;
import cs.b07.cscb07project.backend.transportation.Flight;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ViewFlightInfoActivity extends AppCompatActivity implements OnItemSelectedListener {

  private String username;
  private ArrayList<String> flightNumbers;
  private Flight flight;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_flight_info);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);

    flightNumbers = FlightBookingApplication.getFlightDatabase().getFlights();

    Spinner spinner = (Spinner) findViewById(R.id.view_edit_flight_spinner);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                                                         flightNumbers);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);

    spinner.setOnItemSelectedListener(this);

    if (flightNumbers.isEmpty()) {
      findViewById(R.id.admin_edit_flight).setEnabled(false);
    } else {
      String flightNum = flightNumbers.get(spinner.getFirstVisiblePosition());
      setView(flightNum);
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
    if (flightNumbers.isEmpty()) {
      findViewById(R.id.admin_edit_flight).setEnabled(false);
    } else {
      findViewById(R.id.admin_edit_flight).setEnabled(true);
      String flightNum = (String) parent.getItemAtPosition(pos);
      setView(flightNum);
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    findViewById(R.id.admin_edit_flight).setEnabled(false);
  }

  /**
   * Sets the activity to the non-edit view and displays infromation about hte given flight.
   * @param flightNum The String flight number of the flight to be displayed.
   */
  private void setView(String flightNum) {
    flight = FlightBookingApplication.getFlight(flightNum);

    ((TextView) findViewById(R.id.view_edit_flight_number)).setText(flight.getFlightNumber());
    ((EditText) findViewById(R.id.view_edit_departure_date_time))
        .setText(flight.getDepartureTimeString());
    ((EditText) findViewById(R.id.view_edit_departure_date_time)).setTextColor(Color.DKGRAY);
    findViewById(R.id.view_edit_departure_date_time).setEnabled(false);
    ((EditText) findViewById(R.id.view_edit_arrival_date_time))
        .setText(flight.getArrivalTimeString());
    ((EditText) findViewById(R.id.view_edit_arrival_date_time)).setTextColor(Color.DKGRAY);
    findViewById(R.id.view_edit_arrival_date_time).setEnabled(false);
    ((EditText) findViewById(R.id.view_edit_airline)).setText(flight.getAirline());
    ((EditText) findViewById(R.id.view_edit_airline)).setTextColor(Color.DKGRAY);
    findViewById(R.id.view_edit_airline).setEnabled(false);
    ((EditText) findViewById(R.id.view_edit_origin)).setText(flight.getOrigin());
    ((EditText) findViewById(R.id.view_edit_origin)).setTextColor(Color.DKGRAY);
    findViewById(R.id.view_edit_origin).setEnabled(false);
    ((EditText) findViewById(R.id.view_edit_destination)).setText(flight.getDestination());
    ((EditText) findViewById(R.id.view_edit_destination)).setTextColor(Color.DKGRAY);
    findViewById(R.id.view_edit_destination).setEnabled(false);
    ((EditText) findViewById(R.id.view_edit_price)).setText(flight.getCostString());
    ((EditText) findViewById(R.id.view_edit_price)).setTextColor(Color.DKGRAY);
    findViewById(R.id.view_edit_price).setEnabled(false);
    ((EditText) findViewById(R.id.view_edit_num_seats))
        .setText(String.valueOf(flight.getNumSeats()));
    ((EditText) findViewById(R.id.view_edit_num_seats)).setTextColor(Color.DKGRAY);
    findViewById(R.id.view_edit_num_seats).setEnabled(false);

    findViewById(R.id.admin_edit_flight).setEnabled(true);
    findViewById(R.id.admin_edit_flight).setVisibility(View.VISIBLE);

    findViewById(R.id.admin_save_flight).setEnabled(false);
    findViewById(R.id.admin_save_flight).setVisibility(View.GONE);
  }

  /**
   * A button press action that changes the view into edit mode.
   * @param view The view being used. Given automatically.
   */
  public void adminEditFlight(View view) {
    findViewById(R.id.view_edit_departure_date_time).setEnabled(true);
    findViewById(R.id.view_edit_arrival_date_time).setEnabled(true);
    findViewById(R.id.view_edit_airline).setEnabled(true);
    findViewById(R.id.view_edit_origin).setEnabled(true);
    findViewById(R.id.view_edit_destination).setEnabled(true);
    findViewById(R.id.view_edit_price).setEnabled(true);
    findViewById(R.id.view_edit_num_seats).setEnabled(true);

    findViewById(R.id.admin_edit_flight).setEnabled(false);
    findViewById(R.id.admin_edit_flight).setVisibility(View.GONE);

    findViewById(R.id.admin_save_flight).setEnabled(true);
    findViewById(R.id.admin_save_flight).setVisibility(View.VISIBLE);
  }

  /**
   * A button press action that saves the given flight edits to the app.
   * @param view The view being used. Given automatically.
   */
  public void adminSaveFlight(View view) {
    AlertDialog.Builder invalidDateFormat = new AlertDialog.Builder(this);
    invalidDateFormat.setMessage(Constants.DATE_TIME_FORMAT_ERROR);
    invalidDateFormat.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        EditText cardExpiryText = (EditText) findViewById(R.id.edit_expiry_date);
        cardExpiryText.setError(Constants.DATE_TIME_FORMAT_ERROR);
      }
    }).setTitle(Constants.EDIT_FLIGHT_ERROR);

    EditText departureText = (EditText) findViewById(R.id.view_edit_departure_date_time);
    EditText arrivalText = (EditText) findViewById(R.id.view_edit_arrival_date_time);
    EditText airlineText = (EditText) findViewById(R.id.view_edit_airline);
    EditText originText = (EditText) findViewById(R.id.view_edit_origin);
    EditText destinationText = (EditText) findViewById(R.id.view_edit_destination);
    EditText priceText = (EditText) findViewById(R.id.view_edit_price);
    EditText seatsText = (EditText) findViewById(R.id.view_edit_num_seats);

    Set<EditText> empty = new HashSet<>(Arrays.asList(departureText, arrivalText, airlineText,
                                                         originText, destinationText, priceText,
                                                         seatsText));

    for (EditText blanks : empty) {
      if (blanks.getText().toString().matches("")) {
        Toast.makeText(this, Constants.UNFILLED_FIELDS, Toast.LENGTH_SHORT).show();
        return;
      }
    }

    try {
      flight.setDepartureTime(departureText.getText().toString());
    } catch (ParseException e) {
      invalidDateFormat.show();
    }
    try {
      flight.setArrivalTime(arrivalText.getText().toString());
    } catch (ParseException e) {
      invalidDateFormat.show();
    }
    flight.setAirline(airlineText.getText().toString());
    flight.setOrigin(originText.getText().toString());
    flight.setDestination(destinationText.getText().toString());
    flight.setCost(Double.valueOf(priceText.getText().toString()));
    flight.setTotalSeats(Integer.valueOf(seatsText.getText().toString()));

    MainActivity.manager.saveToFile();
    setView(flight.getFlightNumber());
  }


}
