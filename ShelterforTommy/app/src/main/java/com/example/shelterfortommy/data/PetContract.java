package com.example.shelterfortommy.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class PetContract {
    /**
     * Let’s start with the Content Authority
     * which is used to help identify the Content
     * Provider which we’d setup before in the
     * AndroidManifest tag:
     * <p>
     * <provider
     * android:name=”.data.PetProvider”
     * android:authorities=”com.example.android.pets”
     * android:exported=”false” />
     * In PetContract.java, we set this up as a
     * string constant whose value is the same as
     * that from the AndroidManifest:
     */
    public static final String CONTENT_AUTHORITY = "com.example.pets";
    /**
     * BASE_CONTENT_URI
     * Next, we concatenate the CONTENT_AUTHORITY
     * constant with the scheme “content://”
     * we will create the BASE_CONTENT_URI which
     * will be shared by every URI associated with
     * PetContract:
     * <p>
     * "content://" + CONTENT_AUTHORITY
     * To make this a usable URI, we use the parse
     * method which takes in a URI string and
     * returns a Uri.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * PATH_TableName
     * This constants stores the path for each of the
     * tables which will be appended to the base content
     * URI.
     */
    public static final String PATH_PETS = "pets";

    private PetContract() {

    }

    public static final class PetEntry implements BaseColumns {
        /**
         * Complete CONTENT_URI
         * Lastly, inside each of the Entry classes
         * in the contract, we create a full URI for
         * the class as a constant called CONTENT_URI.
         * The Uri.withAppendedPath() method appends
         * the BASE_CONTENT_URI (which contains the
         * scheme and the content authority) to the
         * path segment.
         */
        public static final Uri Content_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);


        public static final String TABLE_NAME = "Pets";
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

    }
}
