package com.example.finalprojectspinninggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class SpinningGame extends AppCompatActivity {

    // User  use this button to spin the arrow
    Button b_spin;

    // once user click spin button the arrow image will spin
    ImageView iv_arrow;

    // identifying two players to display the score points for each players
    TextView player1, player2;

   // positions of the arrow animations on the screen
    int SpinDegrees = 0, negDegree = 0;

    // Score of player 1 and player 2
    int player1Points = 0, player2Points = 0;

    // Used to display dialogue alert if the back button is pressed
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinning_game);

        // Get components from XML
        b_spin = (Button) findViewById(R.id.b_spin);

        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);

        player1 = (TextView) findViewById(R.id.player1);

        player2 = (TextView) findViewById(R.id.player2);


        // set up the spin button to have rotate animation image on the screen when clicked it
        b_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // the animation image will spin 360 degree
                negDegree = SpinDegrees % 360;

                // this generates random degree spinning
                Random random = new Random();

                // Spins the animations  in random position on 360 degree angle
                SpinDegrees = random.nextInt(3600) + 360;

                // image will rotate from 0 degree then spin 360 degree
                // of original orientation
                RotateAnimation rotateArrow = new RotateAnimation(0, SpinDegrees,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

                // run for 3.6 seconds = 360 milliseconds
                rotateArrow.setDuration(3600);

                // Image will stay at final destination after it has moved.
                // the image will start from the original position, not this final position.
                // Without this line of code, the image will immediately reappear at starting position
                // after animation has finished
                rotateArrow.setFillAfter(true);

                // make the image rotate. this also make the image rotate realistically.
                rotateArrow.setInterpolator(new AccelerateDecelerateInterpolator());

                // this line of code ensures that after the moving the image, it will stay there,
                // and continue moving from that position when spin button is clicked.
                rotateArrow.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // deliberately empty
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //display result when animation ends
                        SpinDegrees = SpinDegrees % 360;
                        points(SpinDegrees);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // deliberately empty
                    }
                });


                // show above animation in the image view
                iv_arrow.startAnimation(rotateArrow);

            }
        });
    }

    // This line codes display the scores of the with toast messages
    private void points(int degree) {

        // if the rotate position of the image is < 90 then player 1 gets the point which will
        // display on the screen
        // toast message will appear when Player 1 wins
        if (degree > 270 || degree < 90) {
            player1Points++;
            player1.setText("PLAYER1: " + player1Points);
            Toast.makeText(this, "Player1 Wins!", Toast.LENGTH_SHORT).show();

        // if the rotate position of the image is > 90 then player 2 gets the point which will
        // display on the screen
        // toast message will appear when Player 2 wins
        } else if (degree < 270 && degree > 90) {
            player2Points++;
            player2.setText("PLAYER2: " + player2Points);
            Toast.makeText(this, "Player2 Wins!", Toast.LENGTH_SHORT).show();

        // if the rotate position of the image is in neither degree than
        // then it will make a different toast message
        } else  {
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {

        // Display toast message if user clicks back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(getApplicationContext(),
                    "Pause", Toast.LENGTH_LONG).show();

            // if button is showing, then show pop-up dialog box
            if (b_spin.getVisibility() == View.VISIBLE)
                showQuitDialog();
            return true;
        }
        else
            return  super.onKeyDown(keyCode, e);
    }



    private void showQuitDialog() {

        // Before we can show pop-up dialog box,
        // we must set up some things with a builder
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to quit the running game?");
        builder.setCancelable(false);

        // action to perform if user wants to quit
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(getApplicationContext(),
                        "Game Over", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),
                        "Main Menu", Toast.LENGTH_SHORT).show();
            }
        });

        // action to perform if user does not wants to quit
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(),
                        "Game Continue", Toast.LENGTH_LONG).show();
            }
        });


        // show pop-up dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Game Paused");
        alert.show();
    }
}