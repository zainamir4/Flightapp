package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.backend.application.Constants;

public class UploadPasswordActivity extends AppCompatActivity {

  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload_password);
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
   * A button press action that save the given password information to the app.
   * @param view The view being used. Given automatically.
   */
  public void saveToFile(View view) {
    // Save the information to the file
    MainActivity.passManager.saveToFile();
    Intent intent = new Intent(this, AdminActivity.class);
    intent.putExtra(Constants.USERNAME, username);
    startActivity(intent);
  }

}
