package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Map;

public class InstantGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_graph);


        LineChart instantLineChart = findViewById(R.id.instant_temp_graph);
        ArrayList<String> xAXES   = new ArrayList<>();
        ArrayList<Entry> yAXESSin = new ArrayList<>();
        ArrayList<Entry> yAXESCos = new ArrayList<>();
        double x = 0;
        int numDataPoints = 1000;
        for (int i=0; i<numDataPoints; ++i) {
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x += 0.1;
            yAXESSin.add(new Entry(sinFunction, i));
            yAXESCos.add(new Entry(cosFunction, i));
            xAXES.add(i, String.valueOf(x));
        }
        String[] xax = new String[xAXES.size()];
        for (int i=0; i<xAXES.size(); ++i)
            xax[i] = xAXES.get(i);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();
        LineDataSet lineDataSet1 = new LineDataSet(yAXESSin, "sin");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.RED);

        LineDataSet lineDataSet2 = new LineDataSet(yAXESCos, "cos");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.BLUE);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        Toast.makeText(this, "last", Toast.LENGTH_LONG).show();
        instantLineChart.setData(new LineData(lineDataSets));


//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
//        graph.addSeries(series);
    }
}