package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;

public class ClientActivity extends AppCompatActivity {

  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(intent);
    return true;
  }

  /**
   * A button press action that takes the client to the view client profile activity.
   * @param view The view being used. Given automatically.
   */
  public void viewProfile(View view) {
    Intent intent = new Intent(this, ViewProfileActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * A button press action that takes the client to the search flights activity.
   * @param view The view being used. Given automatically.
   */
  public void searchFlights(View view) {
    Intent intent = new Intent(this, SearchFlightsActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * A button press action that takes the client to the search itineraries activity.
   * @param view The view being used. Given automatically.
   */
  public void searchItineraries(View view) {
    Intent intent = new Intent(this, SearchItinerariesActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * A button press action that takes the client to the view booked itineraries activity.
   * @param view The view being used. Given automatically.
   */
  public void viewBookedItineraries(View view) {
    Intent intent = new Intent(this, ViewBookedClientItinerariesActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }
}
