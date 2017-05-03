package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;

public class UploadFlightActivity extends AppCompatActivity {

  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload_flight);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);

    String content = FlightBookingApplication.getFlightDatabase().toString();
    TextView view = (TextView) findViewById(R.id.display_flights);
    view.setText(content);

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
    return true;
  }

  /**
   * A button press action that save the given flight information to the app.
   * @param view The view being used. Given automatically.
   */
  public void saveToFile(View view) {
    // Save the information to the file
    MainActivity.manager.saveToFile();
    Intent intent = new Intent(this, AdminActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

}
