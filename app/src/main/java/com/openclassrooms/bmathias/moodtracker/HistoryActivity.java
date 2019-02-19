package com.openclassrooms.bmathias.moodtracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    //String key for SharedPreferences
    private static final String BACKGROUND_KEY = "background";
    private static final String NOTE_KEY = "note";

    //SharedPreferences file used to store user's note and mood/background
    static SharedPreferences mPreferences;
    String sharedPrefFile = "shared preferences";

    // String used to store and retrieve user note and int for image/background
    private String noteText;
    private int moodIndex;

    // String used to create the premade message to share through SMS/mail
    private String moodText;
    private String date;

    List<View> listItemView = Collections.synchronizedList(new ArrayList<View>());

    List<View> listButtonNote = Collections.synchronizedList(new ArrayList<View>());

    /* This array have an extra color in it (default_background), this one will be used to be
     applied by default and when the user didn't open the app on a given day */
    final int[] moodBackground = new int[]{R.color.default_background, R.color.faded_red, R.color.warm_grey,
            R.color.cornflower_blue_65, R.color.light_sage, R.color.banana_yellow};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Add each Relative Layout to listItemView
        layoutList();

        // Add each ImageButton to listButtonNote
        imageButtonList();

        // Retrieve the SharedPreferences needed to build the layouts
        mPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        // Build the UI
        displayHistory();
    }

    /**
     * Handle the BackButton behavior to return to the MainActivity
     */
    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(HistoryActivity.this, MainActivity.class);
        startActivity(mainIntent);
        super.onBackPressed();
    }


    /**
     * Build the UI based on stored SharedPreferences
     */
    private void displayHistory() {

        for (int i = 0; i < 7; i++) {
            if (mPreferences.contains(BACKGROUND_KEY + i)) {
                moodIndex = mPreferences.getInt(BACKGROUND_KEY + i, moodIndex);
                noteText = mPreferences.getString(NOTE_KEY + i, noteText);

                // Since moodBackground[] contains 6 items, we need to increment the int moodIndex
                // we're getting from SharedPreferences (only goes from 0 to 4 in the other activity)
                moodIndex++;

                ViewGroup.LayoutParams layoutParams = listItemView.get(i).getLayoutParams();

                // Getting phone's screen width
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;

                // Setting a background color on a given layout (i) in listItemView
                listItemView.get(i).setBackgroundResource(moodBackground[moodIndex]);

                // Setting the width of this layout, each moodIndex (background color) has
                // a corresponding width
                switch (moodIndex) {

                    case 1:
                        layoutParams.width = width / 5;
                        break;

                    case 2:
                        layoutParams.width = width / 5 * 2;
                        break;

                    case 3:
                        layoutParams.width = width / 5 * 3;
                        break;

                    case 4:
                        layoutParams.width = width / 5 * 4;
                        break;

                    case 5:
                        layoutParams.width = width / 5 * 5;
                        break;
                }

                // If a user note exist, then we set the ImageButton to VISIBLE
                if ((mPreferences.getString(NOTE_KEY + i, noteText) != null) && (!noteText.equals(""))) {
                    listButtonNote.get(i).setVisibility(View.VISIBLE);
                }

                // If a mood is saved for that day, setOnClickListener to the corresponding layout
                if (moodIndex != 0) {
                    listItemView.get(i).setOnClickListener(dialogListener);
                }
            }
        }
    }

    /**
     * onClick method used for the ImageButton. When one is pressed, it will retrieve the assigned
     * key-value pair then display the user note for that day as a Toast
     */
    public void displayMessage(View view) {

        switch (view.getId()) {
            case R.id.imageButton1:
                Toast.makeText(this, mPreferences.getString(NOTE_KEY + 0
                        , noteText), Toast.LENGTH_SHORT).show();
                break;

            case R.id.imageButton2:
                Toast.makeText(this, mPreferences.getString(NOTE_KEY + 1
                        , noteText), Toast.LENGTH_SHORT).show();
                break;

            case R.id.imageButton3:
                Toast.makeText(this, mPreferences.getString(NOTE_KEY + 2
                        , noteText), Toast.LENGTH_SHORT).show();
                break;

            case R.id.imageButton4:
                Toast.makeText(this, mPreferences.getString(NOTE_KEY + 3
                        , noteText), Toast.LENGTH_SHORT).show();
                break;

            case R.id.imageButton5:
                Toast.makeText(this, mPreferences.getString(NOTE_KEY + 4
                        , noteText), Toast.LENGTH_SHORT).show();
                break;

            case R.id.imageButton6:
                Toast.makeText(this, mPreferences.getString(NOTE_KEY + 5
                        , noteText), Toast.LENGTH_SHORT).show();
                break;

            case R.id.imageButton7:
                Toast.makeText(this, mPreferences.getString(NOTE_KEY + 6
                        , noteText), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * This method is used to handle the OnClick behaviour based on the layout ID
     */
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.itemList1:
                    date = getString(R.string.Yesterday);
                    noteText = mPreferences.getString(NOTE_KEY + 0, noteText);
                    moodIndex = mPreferences.getInt(BACKGROUND_KEY + 0, moodIndex);
                    moodDialog();
                    break;

                case R.id.itemList2:
                    date = getString(R.string.Two_days);
                    noteText = mPreferences.getString(NOTE_KEY + 1, noteText);
                    moodIndex = mPreferences.getInt(BACKGROUND_KEY + 1, moodIndex);
                    moodDialog();
                    break;

                case R.id.itemList3:
                    date = getString(R.string.Three_days);
                    noteText = mPreferences.getString(NOTE_KEY + 2, noteText);
                    moodIndex = mPreferences.getInt(BACKGROUND_KEY + 2, moodIndex);
                    moodDialog();
                    break;

                case R.id.itemList4:
                    date = getString(R.string.Four_days);
                    noteText = mPreferences.getString(NOTE_KEY + 3, noteText);
                    moodIndex = mPreferences.getInt(BACKGROUND_KEY + 3, moodIndex);
                    moodDialog();
                    break;

                case R.id.itemList5:
                    date = getString(R.string.Five_days);
                    noteText = mPreferences.getString(NOTE_KEY + 4, noteText);
                    moodIndex = mPreferences.getInt(BACKGROUND_KEY + 4, moodIndex);
                    moodDialog();
                    break;

                case R.id.itemList6:
                    date = getString(R.string.Six_days);
                    noteText = mPreferences.getString(NOTE_KEY + 5, noteText);
                    moodIndex = mPreferences.getInt(BACKGROUND_KEY + 5, moodIndex);
                    moodDialog();
                    break;

                case R.id.itemList7:
                    date = getString(R.string.Seven_days);
                    noteText = mPreferences.getString(NOTE_KEY + 6, noteText);
                    moodIndex = mPreferences.getInt(BACKGROUND_KEY + 6, moodIndex);
                    moodDialog();
                    break;
            }
        }
    };

    /**
     * Build an AlertDialog where the user can choose to send his saved mood through mail, or close
     * the AlertDialog
     */
    private void moodDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Souhaitez-vous envoyer l'humeur de ce jour ?");

        // When this button is pressed, open mail app
        builder.setPositiveButton("Mail", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendMood(true);
            }
        });

        // When this button is pressed, the AlertDialog is closed
        builder.setNeutralButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        // When this button is pressed, open SMS app
        builder.setNegativeButton("SMS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendMood(false);
            }
        });
        builder.show();
    }

    /**
     * Open a mail or sms app and create a premade message based on
     * the layout the user clicked on
     */
    private void sendMood(boolean isMail) {
        String dateMoodText = date + ", j'étais " + moodMessage();

        if (isMail) {
            // If isMail is true, open mail app
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Je te partage mon humeur sur MoodTracker !");
            intent.putExtra(Intent.EXTRA_TEXT, dateMoodText + noteMailText());
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            // If isMail is false, open SMS app without any recipient
            Uri uri = Uri.parse("smsto:");
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "Je te partage mon humeur sur MoodTracker !\n"
                    + dateMoodText + noteMailText());
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    /**
     * Convert the moodIndex to a String describing the mood image
     */
    private String moodMessage() {
        moodIndex++;
        if (moodIndex == 1) {
            moodText = "triste :(";
        } else if (moodIndex == 2) {
            moodText = "déçu :/";
        } else if (moodIndex == 3) {
            moodText = "normal :|";
        } else if (moodIndex == 4) {
            moodText = "content :)";
        } else if (moodIndex == 5) {
            moodText = "très heureux ! :D";
        }
        return moodText;
    }

    /**
     * Build the String related to the note for the mail premade message
     */
    private String noteMailText() {
        if (!noteText.equals("")) {
            return "\nMon commentaire : " + noteText;
        } else {
            return "";
        }
    }

    /**
     * Add each relative layout in listItemView
     */
    private void layoutList() {
        listItemView.add(findViewById(R.id.itemList1));
        listItemView.add(findViewById(R.id.itemList2));
        listItemView.add(findViewById(R.id.itemList3));
        listItemView.add(findViewById(R.id.itemList4));
        listItemView.add(findViewById(R.id.itemList5));
        listItemView.add(findViewById(R.id.itemList6));
        listItemView.add(findViewById(R.id.itemList7));
    }

    /**
     * Add each image button in listButtonNote
     */
    private void imageButtonList() {
        listButtonNote.add(findViewById(R.id.imageButton1));
        listButtonNote.add(findViewById(R.id.imageButton2));
        listButtonNote.add(findViewById(R.id.imageButton3));
        listButtonNote.add(findViewById(R.id.imageButton4));
        listButtonNote.add(findViewById(R.id.imageButton5));
        listButtonNote.add(findViewById(R.id.imageButton6));
        listButtonNote.add(findViewById(R.id.imageButton7));
    }
}
