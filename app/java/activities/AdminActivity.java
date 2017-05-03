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

import java.io.File;

public class AdminActivity extends AppCompatActivity {

  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin);
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
   * Uses the managers to upload the flight information from the admin.
   * @param view The view being used. Given automatically.
   */
  public void upload(View view) {
    // Gets the first name from the 1st EditText field.
    EditText fileName = (EditText) findViewById(R.id.flight_file);
    String fileNameText = fileName.getText().toString();

    File userdata = this.getApplicationContext().getDir(Constants.USER_DATA_DIR, MODE_PRIVATE);
    File flightsFile = new File(userdata, fileNameText);

    MainActivity.manager.uploadFlightInfo(flightsFile.getPath());

    Intent intent = new Intent(this, UploadFlightActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * Uses the client manager to upload the clients from admin using EditView.
   * @param view The view being used. Given automatically.
   */
  public void uploadClients(View view) {
    // Gets the first name from the 1st EditText field.
    EditText fileName = (EditText) findViewById(R.id.client_file);
    String fileNameText = fileName.getText().toString();

    File userdata = this.getApplicationContext().getDir(Constants.USER_DATA_DIR, MODE_PRIVATE);
    File clientsFile = new File(userdata, fileNameText);

    MainActivity.clientManager.uploadUserInfo(clientsFile.getPath());

    Intent uploadclient = new Intent(this, UploadClientActivity.class);
    uploadclient.putExtra(Constants.USERNAME, username);
    startActivity(uploadclient);
  }

  /**
   * Allows the password manager to upload the passwords for clients.
   * @param view The view being used. Given automatically.
   */
  public void uploadPass(View view) {
    // Gets the first name from the 1st EditText field.
    EditText fileName = (EditText) findViewById(R.id.password_file);
    String fileNameText = fileName.getText().toString();

    File userdata = this.getApplicationContext().getDir(Constants.USER_DATA_DIR, MODE_PRIVATE);
    File passFile = new File(userdata, fileNameText);

    MainActivity.passManager.uploadPasswordManager(passFile.getPath());

    Intent intent = new Intent(this, UploadPasswordActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * A button press action that takes the admin to the view client activity.
   * @param view The view being used. Given automatically.
   */
  public void viewClient(View view) {
    Intent intent = new Intent(this, AdminViewClientProfile.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * A button press action that takes the admin to the view booked client itinerary activity.
   * @param view The view being used. Given automatically.
   */
  public void viewBookedClientItineraries(View view) {
    Intent intent = new Intent(this, AdminViewBookedClientItineraries.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * A button press action that takes the admin to the add flight activity.
   * @param view The view being used. Given automatically.
   */
  public void addFlight(View view) {
    Intent intent = new Intent(this, AddFlightActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * A button press action taking the admin to the view and edit flight activity.
   * @param view The view being used. Given automatically.
   */
  public void viewFlight(View view) {
    Intent intent = new Intent(this, ViewFlightInfoActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * A button press action taking the admin to the search flight activity.
   * @param view The view being used. Given automatically.
   */
  public void searchFlights(View view) {
    Intent intent = new Intent(this, SearchFlightsActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

  /**
   * A button press action taking the admin to the search itinerary activity.
   * @param view The view being used. Given automatically.
   */
  public void searchItineraries(View view) {
    Intent intent = new Intent(this, SearchItinerariesActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

}
