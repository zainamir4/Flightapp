package cs.b07.cscb07project.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;
import cs.b07.cscb07project.backend.users.Client;
import cs.b07.cscb07project.backend.users.User;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EditProfileActivity extends AppCompatActivity {

  private String username;
  private String clientUsername;
  private Client client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);
    clientUsername = intent.getStringExtra(Constants.CLIENT);
    client = (Client) FlightBookingApplication.getUser(clientUsername);


    ((TextView) findViewById(R.id.edit_username)).setText(client.getUserName());
    ((EditText) findViewById(R.id.edit_last_name)).setText(client.getLastName());
    ((EditText) findViewById(R.id.edit_first_names)).setText(client.getFirstName());
    ((EditText) findViewById(R.id.edit_email)).setText(client.getEmail());
    ((EditText) findViewById(R.id.edit_address)).setText(client.getAddress());
    ((EditText) findViewById(R.id.edit_credit_card_number)).setText(client.getCreditCardNumber());
    ((EditText) findViewById(R.id.edit_expiry_date)).setText(client.getExpiryDateString());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    User user = FlightBookingApplication.getUser(username);
    if (user.isAdmin()) {
      Intent intent = new Intent(this, ViewProfileActivity.class);
      intent.putExtra(Constants.USERNAME, username);
      intent.putExtra(Constants.CLIENT, clientUsername);
      startActivity(intent);
    } else {
      Intent intent = new Intent(this, ViewProfileActivity.class);
      intent.putExtra(Constants.USERNAME, username);
      startActivity(intent);
    }
    return true;
  }

  /**
   * Saves the edits to the clients profile.
   * @param view The view being used. Given automatically.
   */
  public void saveProfile(View view) {

    AlertDialog.Builder invalidDateFormat = new AlertDialog.Builder(this);
    invalidDateFormat.setMessage(Constants.DATE_FORMAT_ERROR);
    invalidDateFormat.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        EditText cardExpiryText = (EditText) findViewById(R.id.edit_expiry_date);
        cardExpiryText.setError(Constants.DATE_FORMAT_ERROR);
      }
    })
            .setTitle(Constants.EDIT_ERROR);

    EditText firstNameText = (EditText) findViewById(R.id.edit_first_names);
    EditText lastNameText = (EditText) findViewById(R.id.edit_last_name);
    EditText emailText = (EditText) findViewById(R.id.edit_email);
    EditText addressText = (EditText) findViewById(R.id.edit_address);
    EditText cardText = (EditText) findViewById(R.id.edit_credit_card_number);
    EditText dateText = (EditText) findViewById(R.id.edit_expiry_date);

    Set<EditText> empty = new HashSet<>(Arrays.asList(firstNameText, lastNameText, emailText,
            addressText, cardText, dateText));

    for (EditText blanks : empty) {
      if (blanks.getText().toString().matches("")) {
        Toast.makeText(this, Constants.UNFILLED_FIELDS, Toast.LENGTH_SHORT).show();
        return;
      }
    }

    try {
      client.setFirstName(((EditText) findViewById(R.id.edit_first_names)).getText().toString());
      client.setLastName(((EditText) findViewById(R.id.edit_last_name)).getText().toString());
      client.setEmail(((EditText) findViewById(R.id.edit_email)).getText().toString());
      client.setAddress(((EditText) findViewById(R.id.edit_address)).getText().toString());
      client.setCreditCardNumber(((EditText) findViewById(R.id.edit_credit_card_number)).getText()
                                     .toString());
      client.setExpiryDate(dateText.getText().toString());
      MainActivity.clientManager.saveToFile();
      User user = FlightBookingApplication.getUser(username);
      if (user.isAdmin()) {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        intent.putExtra(Constants.USERNAME, username);
        intent.putExtra(Constants.CLIENT, clientUsername);
        startActivity(intent);
      } else {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        intent.putExtra(Constants.USERNAME, username);
        startActivity(intent);
      }
    } catch (ParseException e) {
      invalidDateFormat.show();
    }

  }
}
