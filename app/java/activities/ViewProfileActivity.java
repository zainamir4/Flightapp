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
import cs.b07.cscb07project.backend.users.Client;

public class ViewProfileActivity extends AppCompatActivity {

  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_profile);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
    Client client = (Client) FlightBookingApplication.getUser(username);

    ((TextView) findViewById(R.id.profile_username)).setText(client.getUserName());
    ((TextView) findViewById(R.id.profile_last_name)).setText(client.getLastName());
    ((TextView) findViewById(R.id.profile_first_names)).setText(client.getFirstName());
    ((TextView) findViewById(R.id.profile_email)).setText(client.getEmail());
    ((TextView) findViewById(R.id.profile_address)).setText(client.getAddress());
    ((TextView) findViewById(R.id.profile_credit_card_number))
        .setText(client.getCreditCardNumber());
    ((TextView) findViewById(R.id.profile_expiry_date)).setText(client.getExpiryDateString());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(this, ClientActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
    return true;
  }

  /**
   * A button push action that allows the client to edit their profile information.
   * @param view The view being used. Given automatically.
   */
  public void editProfile(View view) {
    Intent intent = new Intent(this, EditProfileActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    intent.putExtra(Constants.CLIENT, username);
    startActivity(intent);
  }

}
