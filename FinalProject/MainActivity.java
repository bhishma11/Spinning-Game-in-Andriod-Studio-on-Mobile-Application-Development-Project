package com.example.finalprojectspinninggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // User can click on this button to show the image
      Button button;

   // Used to display dialogue alert if the back button is pressed
      AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // user components from XML
        button = findViewById(R.id.button);

        // when the button is clicked, hide the button and show the Spinner game
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),SpinningGame.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {

        // Display toast message if user clicks back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(getApplicationContext(),
                    "You pressed back", Toast.LENGTH_LONG).show();

            // if button is showing, then show pop-up dialog box
            if (button.getVisibility() == View.VISIBLE)
                showQuitDialog();

                // Otherwise, show button and go back to Menu page
            else {
                button.setVisibility(View.VISIBLE);
                return true;
            }
            return true;
        }
        else
            return  super.onKeyDown(keyCode, e);
    }


    private void showQuitDialog() {

        // Before we can show pop-up dialog box,
        // we must set up some things with a builder
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to quit the Game?");
        builder.setCancelable(false);

        // action to perform if user wants to quit
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(getApplicationContext(),
                        "Game Is Closed", Toast.LENGTH_LONG).show();
            }
        });

        // action to perform if user does not wants to quit
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(),
                        "Main Menu", Toast.LENGTH_LONG).show();
            }
        });


        // show pop-up dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Warning: Closing the Game App");
        alert.show();
    }
}
