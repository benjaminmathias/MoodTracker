package com.openclassrooms.bmathias.moodtracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // String used to store user note
    private String noteText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * onClick method used to trigger different behavior based on a view id
     */
    public void onClick(View view) {

        switch (view.getId()) {

            /* Open an AlertDialog when the user press this button, where he will write a note
               related to his mood */
            case R.id.add_comment_button:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Commentaire");

                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // When this button is pressed, the user note is stored into a String
                builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        noteText = input.getText().toString();
                        Log.d("MainActivity", "note : " + noteText);
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
}

