package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * Created by SruthiGanesh on 5/18/2017.
 */

public class PetContract {
    private PetContract()
    {}
    public static final class PetEntry implements BaseColumns {
        public final static String _ID =BaseColumns._ID;
        public final static String TABLE_NAME="pets";
        public static String COLUMN_PET_NAME ="name";
        public static String COLUMN_PET_BREED="breed";
        public static String COLUMN_PET_GENDER="gender";
        public static String COLUMN_PET_WEIGHT="weight";

        public static final int GENDER_UNKNOWN=0;
        public static final int GENDER_MALE=1;
        public static final int GENDER_FEMALE=2;

    }
}
