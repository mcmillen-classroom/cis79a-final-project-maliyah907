package com.maliyahhoward.myfitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int REQUEST_PLACE_CREATE = 1;
    private static final int RESULT_PLACE_EDIT = 2;
    private ListView mListView;
    private ArrayAdapter<Place> mArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);


        ArrayList<Place>myPlaces = new ArrayList<Place>();
        myPlaces.add(new Place("Push Ups","50 push ups"));
        myPlaces.add(new Place("Jumping Jacks","100 jumping jacks"));
        myPlaces.add(new Place("Bicep curls ","10 reps of 10"));
        myPlaces.add(new Place("Leg Curls ","5 reps of 10"));
        myPlaces.add(new Place("Treadmill","Running at 5.0 for 30mins"));


        mArrayAdapter = new PlaceArrayAdapter(this,myPlaces);

        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnCreateContextMenuListener(this);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.main_menu_create)
        {
            launchCreateActivity();
            return true;
        }

        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.context_menu_edit)
        {
            Place place = mArrayAdapter.getItem(info.position);
            launchEditActivity(place, info.position);
            return true;
        }

        if (item.getItemId() == R.id.context_menu_delete)
        {
            Place place = mArrayAdapter.getItem(info.position);
            mArrayAdapter.remove(place);
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(resultCode, resultCode, data);

        if (requestCode == RESULT_OK && requestCode == REQUEST_PLACE_CREATE);
        {
            if (data != null)
            {
                String name = data.getStringExtra("place_name");
                String description = data.getStringExtra("place_description");

                Place added = new Place(name,description);
                mArrayAdapter.add(added);
            }
        }
        if (requestCode == RESULT_OK && requestCode == RESULT_PLACE_EDIT) {
            if (data != null) {
                int position = data.getIntExtra("place_position", -1);
                String name = data.getStringExtra("place_name");
                String description = data.getStringExtra("place_description");

                if (position != -1) {
                    Place place = mArrayAdapter.getItem(position);
                    place.setmName(name);
                    place.setmDescription(description);
                    mArrayAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void launchCreateActivity()
    {
        Intent createIntent = new Intent (this,PlaceCreateActivity.class);
        startActivityForResult(createIntent, REQUEST_PLACE_CREATE);
    }
    private void launchEditActivity(Place place, int position)
    {
        Intent editIntent = new Intent(this, PlaceEditActivity.class);
        editIntent.putExtra("place_position", position);
        editIntent.putExtra("place_name",place.getmName());
        editIntent.putExtra("place_description", place.getmDescription());
        startActivityForResult(editIntent, RESULT_PLACE_EDIT);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        int position = i;
        Place place = mArrayAdapter.getItem(position);
        launchEditActivity(place,position);        
    }

   
}
