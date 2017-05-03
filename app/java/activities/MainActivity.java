package cs.b07.cscb07project.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;
import cs.b07.cscb07project.backend.driver.FlightBookingApplication;
import cs.b07.cscb07project.backend.managers.ClientManager;
import cs.b07.cscb07project.backend.managers.FlightManager;
import cs.b07.cscb07project.backend.managers.PasswordManager;
import cs.b07.cscb07project.backend.users.User;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  // Public Managers that keep the internal storage up to date
  public static PasswordManager passManager;
  public static ClientManager clientManager;
  public static FlightManager manager;

  /**
   * Upon Creation it reads from the internal storage files if they exist.
   * This loads the saved up data for every launch
   * @param savedInstanceState The saved instance to be loaded. Given automatically.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    File userdata = this.getApplicationContext().getDir(Constants.USER_DATA_DIR, MODE_PRIVATE);
    File passwordsFile = new File(userdata, Constants.FILE_PASS);
    File clientsFile = new File(userdata, Constants.FILE_CLIENT);
    File flightsFile = new File(userdata, Constants.FILE_NAME);

    try {
      passManager = new PasswordManager(passwordsFile);
      clientManager = new ClientManager(clientsFile);
      manager = new FlightManager(flightsFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Check inputted login information from the user (username and password) and see if
   * the user is in the database.  If it is then check the password.  Upon valid username and
   * password direct the user to the next activity depending on the type of account (Admin or
   * Client).
   * @param view The view being used. Given automatically.
   */
  public void logIn(View view) {
    // By default assume invalid login
    boolean loginSuccessful = false;

    // Create AlertDialog for incorrect login credentials
    AlertDialog.Builder invalidLogin = new AlertDialog.Builder(this);
    invalidLogin.setMessage(Constants.INVALID_LOGIN);
    invalidLogin.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    })
        .setTitle(Constants.LOGIN_ERROR);

    // Gets the username and password from the EditText fields.
    EditText usernameText = (EditText) findViewById(R.id.username_field);
    EditText passwordText = (EditText) findViewById(R.id.password_field);
    String username = usernameText.getText().toString();
    String password = passwordText.getText().toString();

    // Check if entered user exists and then check password if the user exists
    if (FlightBookingApplication.getUser(username) != null) {
      if (passManager.verifyPassword(username, password)) {
        loginSuccessful = true; // Username and password match the database
      }
    }

    // Check if login is successful
    if (loginSuccessful) {
      User user = FlightBookingApplication.getUser(username);
      // Check if the user is an admin or client
      if (user.isAdmin()) {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra(Constants.USERNAME, username);
        startActivity(intent);
      } else {
        Intent intent = new Intent(this, ClientActivity.class);
        intent.putExtra(Constants.USERNAME, username);
        startActivity(intent);
      }
    } else {
      invalidLogin.show(); // Invalid login alert
    }
  }

  /**
   * Open the register activity
   * @param view The view being used. Given automatically.
   */
  public void register(View view) {
    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }
}
