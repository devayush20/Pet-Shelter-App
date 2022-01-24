package com.example.shelterfortommy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.shelterfortommy.data.PetDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    /**Database helper that will provide us access to the database */
    private PetDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        Log.i("###","entered onCreate()  in CatalogActivity");

        //Set FAB to open EditorActivity
        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Log.i("###","entered onClick() inside Listener for FAB in CatalogActivity");

                Intent intent=new Intent(CatalogActivity.this,EditorActivity.class);
                startActivity(intent);
                Log.i("###","activity editor started exiting onClick Listener for FAB in CatalogActivity");

            }
        });
        Log.i("###","exiting onCreate() inside Catalog activity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu options from the res/menu./menu_catalog.xml file.
        //This adds menu items to the app bar.
        Log.i("###","entered onCreateOptionsMenu  in CatalogActivity");

        getMenuInflater().inflate(R.menu.menu_catalog,menu);
        Log.i("###","exiting onCreateOptionsMenu  in CatalogActivity" +
                "after inflating menu catalog");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //When user clicks on a menu option in the app bar overflow menu
        Log.i("###","entered onOptionsItemSelected() in CatalogActivity");
        switch(item.getItemId())
        {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                //No need to do anything
                return true;
                //Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                //Do nothing
                return true;
        }
        Log.i("###","exiting onOptionsItemSelected() in CatalogActivity");

        return super.onOptionsItemSelected(item);
    }
}
