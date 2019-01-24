package com.openclassrooms.bmathias.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    List<View> listItemView = Collections.synchronizedList(new ArrayList<View>());

    List<View> listButtonNote = Collections.synchronizedList(new ArrayList<View>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        layoutList();
        imageButtonList();
    }


    // Add each relative layout into listItemView
    private void layoutList(){
        listItemView.add(findViewById(R.id.itemList1));
        listItemView.add(findViewById(R.id.itemList2));
        listItemView.add(findViewById(R.id.itemList3));
        listItemView.add(findViewById(R.id.itemList4));
        listItemView.add(findViewById(R.id.itemList5));
        listItemView.add(findViewById(R.id.itemList6));
        listItemView.add(findViewById(R.id.itemList7));
    }

    // Add each image button into listButtonNote
    private void imageButtonList(){
        listButtonNote.add(findViewById(R.id.imageButton1));
        listButtonNote.add(findViewById(R.id.imageButton2));
        listButtonNote.add(findViewById(R.id.imageButton3));
        listButtonNote.add(findViewById(R.id.imageButton4));
        listButtonNote.add(findViewById(R.id.imageButton5));
        listButtonNote.add(findViewById(R.id.imageButton6));
        listButtonNote.add(findViewById(R.id.imageButton7));
    }
}
