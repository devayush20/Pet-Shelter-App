package com.example.shelterfortommy;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.shelterfortommy.data.PetContract.PetEntry;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {
    /**
     * EditText field to enter the pet's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mBreedEditText;

    /**
     * EditText field to enter the pet's weight
     */
    private EditText mWeightEditText;

    /**
     * EditText field to enter the pet's gender
     */
    private Spinner mGenderSpinner;
    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;// to store the gender

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Log.i("###", "entered onPostCreate() inside EditorActivity.java");

        //Find all relevant views that will need input from user
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        Log.i("###", "all the views obtained inside onPostCreate() inside EditorActivity.java");
        Log.i("###", "calling setUpSpinner() inside onPostCreate() in EditorActivity.java");

        setUpSpinner();

    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setUpSpinner() {
        Log.i("###", "entered setUpSpinner() in EditorActivity.java");

        //Create adapter for spinner. The list options are from the String array it will use
        //the spinner wll use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options, android.R.layout.simple_dropdown_item_1line);
        //Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        //Set the integer mSelected to the constant values
        Log.i("###", "adapter for the Spinner set setUpSpinner() in EditorActivity.java");

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("###", "entered onItemSelected() in setUpSpinner() in EditorActivity.java");

                String selection = (String) adapterView.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male)))
                        mGender = PetEntry.GENDER_MALE;//Form male
                    else if (selection.equals(getString(R.string.gender_female)))
                        mGender = PetEntry.GENDER_FEMALE;//Form female
                    else
                        mGender = PetEntry.GENDER_UNKNOWN;//Unknown
                }//PetContract.PetEntry not required because the class is imported
                Log.i("###", "exiting onItemSelected() in EditorActivity.java");
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("###", "entered onNothingSelected() in EditorActivity.java");

                mGender = 0;//Unknown
                Log.i("###", "exiting onNothinSelected()");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("###", "entered onCreateOptionMenu() in EditorActivity.java");
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        Log.i("###", "Inflated the menu editor in " +
                "onCreateOptionsMenu in EditorActivity.java");
        return true;
    }

    private boolean insertPet() {
        Log.i("###", "entered the insertPet() method in EditorActivity()");
        //Read the inputs from the input fields
        //Note that the Gender value was stored in mGender variable
        String nameString = mNameEditText.getText().toString().trim();//checked
        String breedString = mBreedEditText.getText().toString().trim();//check not required
        String weightString = mWeightEditText.getText().toString().trim();//checked
        /*
        Problem with EditText
        Although we have set the default values for the entries and according to our expectation,
        the name should be null if nothing is entered.
        But, since we are using EditText, it automatically sets null inputs as "Empty Strings s="" "
        That's why we need to handle the cases accordingly.
         */
        int weight;
        if (weightString.equals(""))
            weight = 0;
        else
            weight = Integer.parseInt(weightString);
        //Create a database helper object.


        //Create the ContentValues object where column names are the key,
        //and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, nameString);
        values.put(PetEntry.COLUMN_PET_BREED, breedString);
        values.put(PetEntry.COLUMN_PET_GENDER, mGender);
        values.put(PetEntry.COLUMN_PET_WEIGHT, weight);

        //long newRowID=db.insert(PetEntry.TABLE_NAME,null,values);
        //return value of db.insert is int therfore check if -1 or not
        Uri newUri = getContentResolver().insert(PetEntry.Content_URI, values);
        //Return value is uri therefore check if null or not
        if (newUri == null)/*newRowID==-1*/ {
            //If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error while saving the pet data!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            //Toast.makeText(this,"Pet saved with row ID: "+newRowID,Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Details saved", Toast.LENGTH_LONG).show();

        }
        Log.i("###", "Exiting the insertPet() method of EditorActivity class");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //User clicked on menu option in the app bar overflow menu
        Log.i("###", "entered onOptionsItemSelected()" +
                " in EditorActivity.java");
        switch (item.getItemId()) {

            //Respond to a click on the "Save" menu option.
            case R.id.action_save:
                //When Save icon (Tick) is clicked, display a toast message
                //and return back to the main catalog screen.
                if (insertPet())
                    //to go back to the catalog screen, call the finish() method.
                    finish();
                Log.i("###", "called the finish() method in EditorActivity()");

                //Display a toast message whether the operating was successful or not.
                return true;
            //Respond to a click on the "Delete" menu option.
            case R.id.action_delete:
                //Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        Log.i("###", "Returning from onOptionsItemSelected() in EditorActivity.java");
        return super.onOptionsItemSelected(item);
    }
}
