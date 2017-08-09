package com.example.android.miwok;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by SruthiGanesh on 1/26/2017.
 */
public class Word implements Parcelable {
   private String mDefaultTranslation;
    private String mMiwokTranslation;
   private int resourceId=NO_IMAGE;
    //public boolean mchecked=false;
    private static final int NO_IMAGE =-1;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mDefaultTranslation);
        out.writeString(mMiwokTranslation);
        out.writeInt(resourceId);


    }
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Word> CREATOR
            = new Parcelable.Creator<Word>() {
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    private Word(Parcel in) {
        mDefaultTranslation = in.readString();
        mMiwokTranslation=in.readString();
        resourceId=in.readInt();
    }

        public Word(String defaultWord,String miwokWord)
    {
        mDefaultTranslation=defaultWord;
        mMiwokTranslation=miwokWord;

    }
    public Word(String defaultWord,String miwokWord,int resourceId)
    {
        mDefaultTranslation=defaultWord;
        mMiwokTranslation=miwokWord;
        this.resourceId = resourceId;
    }
   /*public void isChecked()
   {
       mchecked=true;
   }*/
    /**

     * @return mMiwokTranslation
     */
    public String getMiwokTranslation()
    {
       return mMiwokTranslation;
    }
    public int getResourceId()
    {
        return resourceId;
    }
public boolean HasImage()
{
    return (resourceId!=NO_IMAGE);
}
    /**

     * @return mDefaultTranslation
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }
}
