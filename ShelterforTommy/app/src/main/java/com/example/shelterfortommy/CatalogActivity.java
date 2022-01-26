package com.example.shelterfortommy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shelterfortommy.data.PetContract.PetEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    /**
     * Database helper that will provide us access to the database
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        Log.i("###", "entered onCreate()  in CatalogActivity");

        //Set FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("###", "entered onClick() inside Listener for FAB in CatalogActivity");

                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
                Log.i("###", "activity editor started exiting onClick Listener for FAB in CatalogActivity");

            }
        });


        Log.i("###", "exiting onCreate() inside Catalog activity");

    }

    /**
     * Following method will be invoked when the method
     * finish() is called in the Editor Activity.
     */

    @Override
    protected void onStart() {
        Log.i("###", "Entered the onStart() method");
        super.onStart();
        displayDatabaseInfo();
        Log.i("###", "exiting the onStart() method of Catalog activity");
    }

    /**
     * Temporary helper method to display information in the onscreen TextView
     * abou the state of the pets datbase
     */
    private void displayDatabaseInfo() {
        Log.i("###", "entered displayDatabaseInfo() inside Catalog activity");


        //Concrete Implementation-define the projection array
        //and obtain a cursor object
        String[] projection = {PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT};

        //Obtain the cursor using ContentProvider PetProvider query() method
        //Remember the activity will call the Content Resolver method query()
        Cursor cursor = getContentResolver().query(PetEntry.Content_URI, projection, null,
                null, null);
        Log.i("###", "Cursor object obtained in displayDatabaseInfo()");

        try {
            //Display the number of rows in the cursor (which reflects the number
            //of rows in the pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            // displayView.setText(getString(R.string.number_of_rows_in_pets_table_message)+cursor.getCount());

            /*
            Create a header in the Text that looks like this-
            The pets table contains the <number of rows in Cursor> pets.
            _id-name-breed-gender-weight

            In the while loop, iterate through the rows of the cursor and display
            the information from each column in this order.
             */
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(PetEntry._ID + " - " +
                    PetEntry.COLUMN_PET_NAME + " - " +
                    PetEntry.COLUMN_PET_BREED + " - " +
                    PetEntry.COLUMN_PET_GENDER + " - " +
                    PetEntry.COLUMN_PET_WEIGHT + "\n");
            //Figure out the index of each column.
            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);
            //Iterate through all the returned rows in the Cursor.
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);

                //Display the values from each column of the current row in the
                //cursor in the TextView
                displayView.append("\n" + currentID + "-" + currentName + "-" + currentBreed + "-" + currentGender + "-" + currentWeight);
            }
            Log.i("###", "no. of rows displayed in catalog activity.");
        } finally {
            //Always close the cursor when you're done reading from it.
            //This releases all the resources makes it invalid.
            cursor.close();
            Log.i("###", "Cursor closed!");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu options from the res/menu./menu_catalog.xml file.
        //This adds menu items to the app bar.
        Log.i("###", "entered onCreateOptionsMenu  in CatalogActivity");

        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        Log.i("###", "exiting onCreateOptionsMenu  in CatalogActivity" +
                "after inflating menu catalog");

        return true;
    }

    private void insertPet() {
        Log.i("###", "Entered insertPet() method in CatalogActivity class");

        /*Create a ContentValues object where column names are the keys,
        and pet's attributes are the values.
         */
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        //long newRowId=db.insert(PetEntry.TABLE_NAME,null,values);

        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(PetEntry.Content_URI, values);
        Log.i("###", "invoked insert() method in insertPet() method in CatalogActivity class, uri obtained: " + newUri.toString());
        Log.i("###", "Returning from insert() in Catalog Activity");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //When user clicks on a menu option in the app bar overflow menu
        Log.i("###", "entered onOptionsItemSelected() in CatalogActivity");
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                //We will take some action when user clicks insert dummy data menu option
                insertPet();
                displayDatabaseInfo();//for testing purpose
                return true;
            //Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                //Do nothing
                return true;
        }
        Log.i("###", "exiting onOptionsItemSelected() in CatalogActivity");

        return super.onOptionsItemSelected(item);
    }
}
