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
import cs.b07.cscb07project.backend.users.User;

public class  SearchFlightsActivity extends AppCompatActivity {

  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_flights);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    User user = FlightBookingApplication.getUser(username);
    if (user.isAdmin()) {
      Intent intent = new Intent(this, AdminActivity.class);
      intent.putExtra(Constants.USERNAME, username);
      startActivity(intent);
    } else {
      Intent intent = new Intent(this, ClientActivity.class);
      intent.putExtra(Constants.USERNAME, username);
      startActivity(intent);
    }
    return true;
  }

  /**
   * A button press action that takes the given information and uses it to search all available
   * flights.
   * @param view The view being used. Given automatically.
   */
  public void getFlights(View view) {
    Intent intent = new Intent(this, ViewFlightsActivity.class);
    String departureDate = ((EditText) findViewById(R.id.departure_date)).getText().toString();
    String origin = ((EditText) findViewById(R.id.origin)).getText().toString();
    String destination = ((EditText) findViewById(R.id.destination)).getText().toString();
    intent.putExtra(Constants.USERNAME, username);
    intent.putExtra(Constants.DEPARTURE_DATE, departureDate);
    intent.putExtra(Constants.ORIGIN, origin);
    intent.putExtra(Constants.DESTINATION, destination);
    startActivity(intent);
  }
}
