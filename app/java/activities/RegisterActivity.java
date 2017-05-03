package cs.b07.cscb07project.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;
import cs.b07.cscb07project.backend.users.Admin;
import cs.b07.cscb07project.backend.users.Client;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * An activity to register an new Admin or Client object into the system.
 */
public class RegisterActivity extends AppCompatActivity {

  private boolean isAdmin = false;
  /* private ClientManager clientManager;
  private PasswordManager passwordManager;*/

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Intent intent = getIntent();
        clientManager = (ClientManager)intent.getSerializableExtra(MainActivity.CLIENT_KEY);
        passwordManager =  (PasswordManager)intent.getSerializableExtra(MainActivity.PASS_KEY);*/
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(intent);
    return true;
  }

  /**
   * When radio buttons are switched change visible EditTexts to show only the required fields
   * for the type of account to be registered.
   * @param view The view being used. Given automatically.
   */
  public void onRadioButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();

    // Check which radio button was clicked
    switch (view.getId()) {
      case R.id.radio_admin:
        if (checked) {
          isAdmin = true;
          findViewById(R.id.register_first_names).setEnabled(false);
          findViewById(R.id.register_first_names).setVisibility(View.GONE);
          findViewById(R.id.register_last_name).setEnabled(false);
          findViewById(R.id.register_last_name).setVisibility(View.GONE);
          findViewById(R.id.register_email).setEnabled(false);
          findViewById(R.id.register_email).setVisibility(View.GONE);
          findViewById(R.id.register_address).setEnabled(false);
          findViewById(R.id.register_address).setVisibility(View.GONE);
          findViewById(R.id.register_card_number).setEnabled(false);
          findViewById(R.id.register_card_number).setVisibility(View.GONE);
          findViewById(R.id.register_credit_card_expiry_date).setEnabled(false);
          findViewById(R.id.register_credit_card_expiry_date).setVisibility(View.GONE);

        }
        break;
      case R.id.radio_client:
        if (checked) {
          isAdmin = false;
          findViewById(R.id.register_first_names).setEnabled(true);
          findViewById(R.id.register_first_names).setVisibility(View.VISIBLE);
          findViewById(R.id.register_last_name).setEnabled(true);
          findViewById(R.id.register_last_name).setVisibility(View.VISIBLE);
          findViewById(R.id.register_email).setEnabled(true);
          findViewById(R.id.register_email).setVisibility(View.VISIBLE);
          findViewById(R.id.register_address).setEnabled(true);
          findViewById(R.id.register_address).setVisibility(View.VISIBLE);
          findViewById(R.id.register_card_number).setEnabled(true);
          findViewById(R.id.register_card_number).setVisibility(View.VISIBLE);
          findViewById(R.id.register_credit_card_expiry_date).setEnabled(true);
          findViewById(R.id.register_credit_card_expiry_date).setVisibility(View.VISIBLE);
        }
        break;
      default: {
        break;
      }
    }
  }

  /**
   * Takes the input from all the shown fields for registration and creates a new user with the
   * given information.  The information is checked for proper input format and for existing
   * information.
   * @param view The view being used. Given automatically.
   */
  public void register(View view) {
    // Create AlertDialogs for needed errors and invalid inputs by user
    AlertDialog.Builder userExists = new AlertDialog.Builder(this);
    userExists.setMessage(Constants.USERNAME_TAKEN);
    userExists.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    })
        .setTitle(Constants.REGISTRATION_ERROR);

    AlertDialog.Builder emptyBlanks = new AlertDialog.Builder(this);
    emptyBlanks.setMessage(Constants.UNFILLED_FIELDS);
    emptyBlanks.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
      }
    })
            .setTitle(Constants.REGISTRATION_ERROR);

    AlertDialog.Builder invalidDateFormat = new AlertDialog.Builder(this);
    invalidDateFormat.setMessage(Constants.DATE_FORMAT_ERROR);
    invalidDateFormat.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        EditText cardExpiryText = (EditText) findViewById(R.id.register_credit_card_expiry_date);
        cardExpiryText.setError(Constants.DATE_FORMAT_ERROR);
      }
    })
        .setTitle(Constants.REGISTRATION_ERROR);

    // Get EditText fields
    EditText usernameText = (EditText) findViewById(R.id.register_username_field);
    EditText passwordText = (EditText) findViewById(R.id.register_password_field);
    EditText firstnameText = (EditText) findViewById(R.id.register_first_names);
    EditText lastnameText = (EditText) findViewById(R.id.register_last_name);
    EditText emailText = (EditText) findViewById(R.id.register_email);
    EditText addressText = (EditText) findViewById(R.id.register_address);
    EditText cardText = (EditText) findViewById(R.id.register_card_number);
    EditText dateText = (EditText) findViewById(R.id.register_credit_card_expiry_date);
    String username = usernameText.getText().toString();
    String password = passwordText.getText().toString();
    String firstName = ((EditText) findViewById(R.id.register_first_names)).getText().toString();
    String lastName = ((EditText) findViewById(R.id.register_last_name)).getText().toString();
    String email = ((EditText) findViewById(R.id.register_email)).getText().toString();
    String address = ((EditText) findViewById(R.id.register_address)).getText().toString();
    String cardNumber = ((EditText) findViewById(R.id.register_card_number)).getText().toString();
    String expiryDate = ((EditText) findViewById(R.id.register_credit_card_expiry_date)).getText()
            .toString();

    Set<EditText> empty = new HashSet<>(Arrays.asList(usernameText, passwordText, firstnameText,
                                                         lastnameText, emailText,
            addressText, cardText, dateText));

    if (isAdmin) {
      if (usernameText.getText().toString().matches("")
              || passwordText.getText().toString().matches("")) {
        Toast.makeText(this, Constants.UNFILLED_FIELDS, Toast.LENGTH_SHORT).show();
        return;
      }
    } else {
      for (EditText blanks : empty) {
        if (blanks.getText().toString().matches("")) {
          Toast.makeText(this, Constants.UNFILLED_FIELDS, Toast.LENGTH_SHORT).show();
          return;
        }
      }
    }

    // Check if user already exists
    if (FlightBookingApplication.getUser(username) != null) {
      // Display errors for registering with an existing username
      userExists.show();
      usernameText.setError(Constants.USERNAME_TAKEN);
      usernameText.requestFocus();
    } else {
      // Register as Admin
      if (isAdmin) {
        Admin newAdmin = new Admin(username);
        MainActivity.passManager.addUser(newAdmin, password);
        FlightBookingApplication.addUser(newAdmin);
        Intent intent = new Intent(this, MainActivity.class);
        MainActivity.passManager.saveToFile();
        MainActivity.clientManager.saveToFile();
        startActivity(intent);
      } else { // Register as Client

        try {
          Client newClient = new Client(username, lastName, firstName, email, address,
                                           cardNumber, expiryDate);
          MainActivity.passManager.addUser(newClient, password);
          FlightBookingApplication.addUser(newClient);
          Intent intent = new Intent(this, MainActivity.class);
          MainActivity.passManager.saveToFile();
          MainActivity.clientManager.saveToFile();
          startActivity(intent);
        } catch (ParseException e) {
          // Handle invalid input by user
          invalidDateFormat.show();
        }
      }
    }
  }
}
