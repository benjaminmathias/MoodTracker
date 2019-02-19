package com.openclassrooms.bmathias.moodtracker;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // float value used to track user vertical finger movement
    private float initialY;
    // int value that represent the minimum distance needed for the movement to be tracked
    static final int MIN_DISTANCE = 350;

    // String used to store user note and int for image/background
    private String noteText = "";
    private int moodIndex;

    //SharedPreferences file used to store user's note and mood/background
    static SharedPreferences mPreferences;
    String sharedPrefFile = "shared preferences";

    //String key for SharedPreferences
    private final String MOOD_KEY = "mood";
    private final String BACKGROUND_KEY = "background";
    private final String NOTE_KEY = "note";

    // Array of drawable containing mood images
    final int[] moodImageList = new int[]{R.drawable.smiley_sad, R.drawable.smiley_disappointed,
            R.drawable.smiley_normal, R.drawable.smiley_happy, R.drawable.smiley_super_happy};

    // Array of color used to set the background of the layout
    final int[] moodBackground = new int[]{R.color.faded_red, R.color.warm_grey,
            R.color.cornflower_blue_65, R.color.light_sage, R.color.banana_yellow};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an AlarmManager object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // Set the alarm to start at midnight
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Prevent the alarm from getting executed the first time the user launch the app
        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DATE, 1);
        }

        // Repeat that alarm every day at the same hour
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        // Retrieve SharedPreferences to display the saved mood and note, if there's none
        // display the default value
        getData();

        // Set the image ressource to the ImageView
        displayMood(moodImageList);

        // Set the background color of the layout
        displayBackground(moodBackground);
    }

    /**
     * Values are saved in the onPause method, when the activity is in the background
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    /**
     * finish() is called on this method to allow the UI to be refreshed when the alarm is triggered
     * if the user didn't force closed the app
     */
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    /**
     * Store the mood, background and note in SharedPreferences
     */
    protected void saveData() {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(MOOD_KEY, moodIndex);
        preferencesEditor.putInt(BACKGROUND_KEY, moodIndex);
        preferencesEditor.putString(NOTE_KEY, noteText);
        preferencesEditor.apply();
    }

    /**
     * Get the stored SharedPreferences values, if there's none, set to the default value
     */
    protected void getData() {
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        moodIndex = mPreferences.getInt(MOOD_KEY, 3);
        moodIndex = mPreferences.getInt(BACKGROUND_KEY, 3);
        noteText = mPreferences.getString(NOTE_KEY, "");
    }

    /**
     * onClick method used to trigger different behavior based on a view id
     */
    public void onClick(View view) {

        switch (view.getId()) {

            // Open an AlertDialog when the user press this button, where he will write a note
            // related to his mood
            case R.id.add_comment_button:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Ajoutez un commentaire !");
                if (!noteText.equals("")) {
                    builder.setMessage("Commentaire enregistré : " + (mPreferences.getString(NOTE_KEY, noteText)));
                }

                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // When this button is pressed, the user note is stored into a String and saved
                builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        noteText = input.getText().toString();
                        Log.d("MainActivity", "note : " + noteText);
                        saveData();
                    }
                });

                // When this button is pressed, the AlertDialog is closed and nothing is saved
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
                break;

            // This button is used to go to the HistoryActivity
            case R.id.view_history_button:
                Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
                break;

            default:
                break;
        }
    }

    /**
     * Track finger movement
     * @param event The MotionEvent that correspond to the user finger movement on the screen
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        switch (action) {

            // When the user put his finger on the screen and move it around
            case MotionEvent.ACTION_DOWN:
                initialY = event.getY();
                break;

            // When the user remove his finger
            case MotionEvent.ACTION_UP:
                float finalY = event.getY();
                float deltaY = finalY - initialY;

                if (Math.abs(deltaY) > MIN_DISTANCE) {

                    // If the user user swipe down
                    if (initialY < finalY) {
                        if (moodIndex > 0) {
                            moodIndex--;
                            saveData();
                        }
                        displayMood(moodImageList);
                        displayBackground(moodBackground);
                    // If the user swipe up
                    } else {
                        if (moodIndex < 4) {
                            moodIndex++;
                            saveData();
                        }
                        displayMood(moodImageList);
                        displayBackground(moodBackground);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * This method is used to display the mood image
     */
    private void displayMood(int[] moodImageList) {
        ImageView moodImageView = this.findViewById(R.id.mood_image_view);
        moodImageView.setImageResource(moodImageList[moodIndex]);
    }

    /**
     * This method is used to set the background color
     */
    private void displayBackground(int[] moodBackground) {
        ConstraintLayout constraintLayout = this.findViewById(R.id.constraintLayout);
        constraintLayout.setBackgroundResource(moodBackground[moodIndex]);
    }
}


