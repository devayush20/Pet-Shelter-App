package com.example.shelterfortommy;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {
    /** EditText field to enter the pet's name */
    private EditText mNameEditText;

    /** EditText field to enter the pet's breed */
    private EditText mBreedEditText;

    /** EditText field to enter the pet's weight */
    private EditText mWeightEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mGenderSpinner;
    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Log.i("###","entered onPostCreate() inside EditorActivity.java");

        //Find all relevant views that will need input from user
        mNameEditText=(EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText=(EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText=(EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner=(Spinner) findViewById(R.id.spinner_gender);
        Log.i("###","all the views obtained inside onPostCreate() inside EditorActivity.java");
        Log.i("###","calling setUpSpinner() inside onPostCreate() in EditorActivity.java");

        setUpSpinner();

    }
    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setUpSpinner()
    {
        Log.i("###","entered setUpSpinner() in EditorActivity.java");

        //Create adapter for spinner. The list options are from the String array it will use
        //the spinner wll use the default layout
        ArrayAdapter genderSpinnerAdapter=ArrayAdapter.createFromResource(this,R.array.array_gender_options,android.R.layout.simple_dropdown_item_1line);
        //Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        //Set the integer mSelected to the constant values
        Log.i("###","adapter for the Spinner set setUpSpinner() in EditorActivity.java");

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("###","entered onItemSelected() in setUpSpinner() in EditorActivity.java");

                String selection=(String) adapterView.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection))
                {
                    if(selection.equals(getString(R.string.gender_male)))
                    mGender=1;//Form male
                    else
                    if(selection.equals(getString(R.string.gender_female)))
                    mGender=2;//Form male
                    else
                    mGender=0;//Unknown
                }
                Log.i("###","exiting onItemSelected() in EditorActivity.java");
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("###","entered onNothingSelected() in EditorActivity.java");

                mGender=0;//Unknown
                Log.i("###","exiting onNothinSelected()");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("###","entered onCreateOptionMenu() in EditorActivity.java");
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        Log.i("###","Inflated the menu editor in " +
                "onCreateOptionsMenu in EditorActivity.java");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //User clicked on menu option in the app bar overflow menu
        Log.i("###","entered onOptionsItemSelected()" +
                " in EditorActivity.java");
switch(item.getItemId())
{
    //Respond to a click on the "Save" menu option.
    case R.id.action_save:
        //Do nothing for now
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
    Log.i("###","Returning from onOptionsItemSelected() in EditorActivity.java");
        return super.onOptionsItemSelected(item);
    }
}
