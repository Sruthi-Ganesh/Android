package com.example.android.onlineordering;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by SruthiGanesh on 4/12/2017.
 */

public class orderingPage extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlayout);
    }
    private String mItemName;
    private double mItemPrice;
    private int resourceId=NO_IMAGE;
    private static final int NO_IMAGE =-1;
    public orderingPage(String defaultname,double defaultprice)
    {
    mItemName=defaultname;
    mItemPrice=defaultprice;
}
    public orderingPage(String defaultWord,double miwokWord,int resourceId)
    {
        mItemName=defaultWord;
        mItemPrice=miwokWord;
        this.resourceId = resourceId;
    }
    /**

     * @return mItemPrice
     */
    public double getDefaultPrice()
    {
        return mItemPrice;
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

     * @return mItemName
     */
    public String getDefaultName() {
        return mItemName;
    }
}