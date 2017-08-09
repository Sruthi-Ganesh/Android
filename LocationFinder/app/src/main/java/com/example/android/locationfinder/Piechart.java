package com.example.android.locationfinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Piechart extends AppCompatActivity {

    private String TAG="Pie chart";
    private String[] date={"checked", "late","not checked"};
    private int[] attendanceChecker=new int[3];;
    String initialDate="2017-04-24";
    PieChart pie;
    Set<String> Date = new HashSet<>();
    Set<String> LateDate = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.piechart);
          pie = (PieChart) findViewById(R.id.idPieChart);

        attendanceChecker = getIntent().getIntArrayExtra("size");

            pie.setCenterText("Report");
            pie.setCenterTextSize(25);
            pie.setDescription("Attendance Report from 24/04/2017");
            pie.setRotationEnabled(true);
            pie.setHoleRadius(25);
            pie.setTransparentCircleAlpha(0);
              Date.addAll(getSharedPrefDate());

             LateDate.addAll(getSharedPrefDateLate());



            Log.v("Datess",Date.toString() + LateDate.toString());
            attendanceChecker[2]=daysBetween()-attendanceChecker[2]+1;
            addAttendance();

            pie.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    Log.d(TAG, "onValueSelected: Value select from chart.");
                    Log.d(TAG, "onValueSelected: " + e.toString());
                    Log.d(TAG, "onValueSelected: " + h.toString());

                    int position = e.toString().indexOf("(sum): ");
                    String attendance = e.toString().substring(position + 7);
                    int num= Math.round(h.getX());


                    String dates = date[num];
                    Toast.makeText(Piechart.this, "Status: " + dates + "\n" + "Count:" + attendance +"times" , Toast.LENGTH_LONG).show();

                }
                @Override
                public void onNothingSelected() {

                }
            });
        }
//to draw a pie chart
    public void addAttendance()
    {
        ArrayList<PieEntry> chartData = new ArrayList<>();
        ArrayList<String> displayData = new ArrayList<>();

        for(int i=0;i<attendanceChecker.length;i++)
        {
            if(attendanceChecker[i]!=0)
            chartData.add(new PieEntry(attendanceChecker[i],i));


        }

        for(int i=0;i<date.length;i++)
        {
            displayData.add(i,date[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(chartData, "Attendance checker");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        pieDataSet.setColors(colors);


        Legend legend = pie.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);


        PieData pieData = new PieData(pieDataSet);
        pie.setData(pieData);
        pie.invalidate();
    }
    //to get the Date(the correct time checked in)
    public Set<String> getSharedPrefDate() {

        SharedPreferences shap = this.getSharedPreferences("your activity", Context.MODE_PRIVATE);
        Set<String> Dat = new HashSet<>();
        Dat =  shap.getStringSet("Date", null);
        if(Dat!=null) {

            Log.v("get shared pref Date", Dat.toString());

            return Dat;
        }
        return null;

    }
    //to get the LateDate(the late time checked in)
    public Set<String> getSharedPrefDateLate() {

        SharedPreferences shap = this.getSharedPreferences("your activity1", Context.MODE_PRIVATE);
        Set<String> Dat = new HashSet<>();
        Dat =  shap.getStringSet("LateDate", null);
        if(Dat!=null) {

            Log.v("Late get shared pref", Dat.toString());

            return Dat;
        }
        Log.v("get","No return");
        return null;

    }
    //to calculate the number of days between two dates
    public int daysBetween() {
        try {

            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(initialDate);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);
            calendar2.add(Calendar.DATE, 1);

            date2 = calendar2.getTime();

            DateFormat bf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calobj = Calendar.getInstance();
            Date d = calobj.getTime();

            int noOfDays =  (int) (d.getTime()- date2.getTime())/(1000*24*60*60);
            Log.v("Number of Days",String.valueOf(noOfDays));
            return noOfDays;

        }
        catch (ParseException p)
        {
            Log.e("Error",p.toString());
        }
        return 0;
    }
}
