package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;

public class AddFlightActivity extends AppCompatActivity {

  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_flight);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
    return true;
  }


  /**
  * Uses the data from EditText to add a Flight to the main flightDatabase.
  * Then saves it using the public managers
  */
  public void addFlight(View view) {
    String flightNumber = ((EditText) findViewById(R.id.flight_number)).getText().toString();
    String departureDateTime = ((EditText) findViewById(R.id.departure_date_time)).getText()
                                   .toString();
    String arrivalDateTime = ((EditText) findViewById(R.id.arrival_date_time)).getText().toString();
    String airline = ((EditText) findViewById(R.id.airline)).getText().toString();
    String origin = ((EditText) findViewById(R.id.origin)).getText().toString();
    String destination = ((EditText) findViewById(R.id.destination)).getText().toString();
    String price = ((EditText) findViewById(R.id.price)).getText().toString();
    String numSeats = ((EditText) findViewById(R.id.num_seats)).getText().toString();
    FlightBookingApplication.addFlight(flightNumber, departureDateTime, arrivalDateTime,
                                          airline, origin, destination, Double.valueOf(price),
                                          Integer.valueOf(numSeats));
    MainActivity.manager.saveToFile();
    Intent intent = new Intent(this, AdminActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }
}
