package com.openclassrooms.bmathias.moodtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
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

        layoutList();
        imageButtonList();

        // Retrieve the SharedPreferences needed to build the layouts
        mPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        displayHistory();
    }


    // Since finish() is called in the onStop method we need to override this method to go to the
    // previous activity (WIP)
    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(HistoryActivity.this, MainActivity.class);
        startActivity(mainIntent);
        super.onBackPressed();
    }

    // ! WIP : finish is called on this method to allow the alarm to work
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    // onClick method used for the ImageButton. When one is pressed, it will retrieve the assigned
    // key-value pair then display the user note for that day as a Toast
    public void onClick(View view) {

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

    // This method is used to build the layouts
    private void displayHistory() {

        for (int i = 0; i < 7; i++) {
            if (mPreferences.contains(BACKGROUND_KEY + i)) {
                moodIndex = mPreferences.getInt(BACKGROUND_KEY + i, moodIndex);
                noteText = mPreferences.getString(NOTE_KEY + i, noteText);

                /* Since moodBackground[] contains 6 items, we need to increment the int moodIndex
                we're getting from SharedPreferences (only goes from 0 to 4 in the other activity) */
                moodIndex++;

                ViewGroup.LayoutParams layoutParams = listItemView.get(i).getLayoutParams();

                // Getting phone's screen width
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;

                // Setting a background color on a given layout (i) in listItemView
                listItemView.get(i).setBackgroundResource(moodBackground[moodIndex]);

                /* Setting the width of this layout, each moodIndex (background color) has
                a corresponding width */
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
            }
        }
    }

    // Add each relative layout in listItemView
    private void layoutList() {
        listItemView.add(findViewById(R.id.itemList1));
        listItemView.add(findViewById(R.id.itemList2));
        listItemView.add(findViewById(R.id.itemList3));
        listItemView.add(findViewById(R.id.itemList4));
        listItemView.add(findViewById(R.id.itemList5));
        listItemView.add(findViewById(R.id.itemList6));
        listItemView.add(findViewById(R.id.itemList7));
    }

    // Add each image button in listButtonNote
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
