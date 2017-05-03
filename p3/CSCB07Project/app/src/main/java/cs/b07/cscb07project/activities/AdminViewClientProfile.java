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
import cs.b07.cscb07project.backend.users.Client;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AdminViewClientProfile extends AppCompatActivity implements OnItemSelectedListener {

  private String username;
  private ArrayList<String> clientNames;
  private Client client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_view_client_profile);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    username = intent.getStringExtra(Constants.USERNAME);

    clientNames = FlightBookingApplication.getUserDatabase().getClients();

    Spinner spinner = (Spinner) findViewById(R.id.view_edit_client_spinner);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                                                                .simple_spinner_item,
                                                         clientNames);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);

    spinner.setOnItemSelectedListener(this);

    if (clientNames.isEmpty()) {
      findViewById(R.id.admin_edit_profile).setEnabled(false);
    } else {
      String clientUsername = clientNames.get(spinner.getFirstVisiblePosition());
      setView(clientUsername);
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
    if (clientNames.isEmpty()) {
      findViewById(R.id.admin_edit_profile).setEnabled(false);
    } else {
      findViewById(R.id.admin_edit_profile).setEnabled(true);
      String clientUsername = (String) parent.getItemAtPosition(pos);
      setView(clientUsername);
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    findViewById(R.id.admin_edit_profile).setEnabled(false);
  }

  /**
   * Sets the text fields in the activity to display uneditable information of the given client.
   * @param clientUsername A string representation of a client's username.
   */
  private void setView(String clientUsername) {
    client = (Client) FlightBookingApplication.getUser(clientUsername);
    ((TextView) findViewById(R.id.admin_profile_username)).setText(client.getUserName());
    ((TextView) findViewById(R.id.admin_profile_username)).setTextColor(Color.DKGRAY);
    ((EditText) findViewById(R.id.admin_profile_last_name)).setText(client.getLastName());
    ((EditText) findViewById(R.id.admin_profile_last_name)).setTextColor(Color.DKGRAY);
    ((EditText) findViewById(R.id.admin_profile_first_names)).setText(client.getFirstName());
    ((EditText) findViewById(R.id.admin_profile_first_names)).setTextColor(Color.DKGRAY);
    ((EditText) findViewById(R.id.admin_profile_email)).setText(client.getEmail());
    ((EditText) findViewById(R.id.admin_profile_email)).setTextColor(Color.DKGRAY);
    ((EditText) findViewById(R.id.admin_profile_address)).setText(client.getAddress());
    ((EditText) findViewById(R.id.admin_profile_address)).setTextColor(Color.DKGRAY);
    ((EditText) findViewById(R.id.admin_profile_credit_card_number))
        .setText(client.getCreditCardNumber());
    ((EditText) findViewById(R.id.admin_profile_credit_card_number)).setTextColor(Color.DKGRAY);
    ((EditText) findViewById(R.id.admin_profile_expiry_date)).setText(client.getExpiryDateString());
    ((EditText) findViewById(R.id.admin_profile_expiry_date)).setTextColor(Color.DKGRAY);
    findViewById(R.id.admin_profile_last_name).setEnabled(false);
    findViewById(R.id.admin_profile_last_name).setEnabled(false);
    findViewById(R.id.admin_profile_first_names).setEnabled(false);
    findViewById(R.id.admin_profile_email).setEnabled(false);
    findViewById(R.id.admin_profile_address).setEnabled(false);
    findViewById(R.id.admin_profile_credit_card_number).setEnabled(false);
    findViewById(R.id.admin_profile_expiry_date).setEnabled(false);
    findViewById(R.id.admin_edit_profile).setEnabled(true);
    findViewById(R.id.admin_edit_profile).setVisibility(View.VISIBLE);
    findViewById(R.id.admin_save_profile).setEnabled(false);
    findViewById(R.id.admin_save_profile).setVisibility(View.GONE);
  }

  /**
   * A button press action that makes user text fields editable.
   * @param view The view being used. Given automatically.
   */
  public void adminEditProfile(View view) {
    findViewById(R.id.admin_profile_last_name).setEnabled(true);
    findViewById(R.id.admin_profile_first_names).setEnabled(true);
    findViewById(R.id.admin_profile_email).setEnabled(true);
    findViewById(R.id.admin_profile_address).setEnabled(true);
    findViewById(R.id.admin_profile_credit_card_number).setEnabled(true);
    findViewById(R.id.admin_profile_expiry_date).setEnabled(true);
    findViewById(R.id.admin_edit_profile).setEnabled(false);
    findViewById(R.id.admin_edit_profile).setVisibility(View.GONE);
    findViewById(R.id.admin_save_profile).setEnabled(true);
    findViewById(R.id.admin_save_profile).setVisibility(View.VISIBLE);
  }

  /**
   * A button press action that saves the given edits to the client profile.
   * @param view The view being used. Given automatically.
   */
  public void adminSaveProfile(View view) {
    AlertDialog.Builder invalidDateFormat = new AlertDialog.Builder(this);
    invalidDateFormat.setMessage(Constants.DATE_FORMAT_ERROR);
    invalidDateFormat.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        EditText cardExpiryText = (EditText) findViewById(R.id.edit_expiry_date);
        cardExpiryText.setError(Constants.DATE_FORMAT_ERROR);
      }
    }).setTitle(Constants.EDIT_ERROR);

    EditText lastNameText = (EditText) findViewById(R.id.admin_profile_last_name);
    EditText firstNameText = (EditText) findViewById(R.id.admin_profile_first_names);
    EditText emailText = (EditText) findViewById(R.id.admin_profile_email);
    EditText addressText = (EditText) findViewById(R.id.admin_profile_address);
    EditText cardText = (EditText) findViewById(R.id.admin_profile_credit_card_number);
    EditText dateText = (EditText) findViewById(R.id.admin_profile_expiry_date);

    Set<EditText> empty = new HashSet<>(Arrays.asList(firstNameText, lastNameText, emailText,
                                                         addressText, cardText, dateText));

    for (EditText blanks : empty) {
      if (blanks.getText().toString().matches("")) {
        Toast.makeText(this, Constants.UNFILLED_FIELDS, Toast.LENGTH_SHORT).show();
        return;
      }
    }

    client.setLastName(lastNameText.getText().toString());
    client.setFirstName(firstNameText.getText().toString());
    client.setEmail(emailText.getText().toString());
    client.setAddress(addressText.getText().toString());
    client.setCreditCardNumber(cardText.getText().toString());
    try {
      client.setExpiryDate(dateText.getText().toString());
    } catch (ParseException e) {
      invalidDateFormat.show();
    }
    MainActivity.clientManager.saveToFile();
    setView(client.getUserName());
  }

}
