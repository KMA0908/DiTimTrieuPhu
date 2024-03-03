package com.nhn.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nhn.ditimtrieuphu.App;
import com.nhn.ditimtrieuphu.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BarChartQuestion extends DialogFragment {
    public static String TAG = BarChartQuestion.class.getName();
    private Typeface tfLight;
    private BarChart chart;

    private TextView tvIdea, tvDone;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_chart_question, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tfLight = Typeface.createFromAsset(App.getInstance().getAssets(),"font/OpenSans-Regular.ttf");
        tvIdea = view.findViewById(R.id.tv_idea_audience);
//        tvX.setTextSize(10);
        tvDone = view.findViewById(R.id.tv_close);
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        chart = view.findViewById(R.id.bar_chart_question);
        // chart.setOnChartValueSelectedListener(this);
        chart.getDescription().setEnabled(false);

//        chart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);

        chart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(App.getInstance(), R.layout.custom_maker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
        l.setTextColor(getResources().getColor(R.color.white,null));

        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(getResources().getColor(R.color.white,null));
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter());

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(getResources().getColor(R.color.white,null));
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);
        setUpData();
    }


    private void setUpData() {

        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        int groupCount =  1;
        int startCount = 1;
        int endCount = startCount + groupCount;

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();

        //  float randomMultiplier = 1 * 100000f/1000;
        List<Integer> randomMultiplier = selectRandom();

        for (int i = startCount; i < endCount; i++) {
            values1.add(new BarEntry(i, (float) (randomMultiplier.get(0))));
            values2.add(new BarEntry(i, (float) (randomMultiplier.get(1))));
            values3.add(new BarEntry(i, (float) (randomMultiplier.get(2))));
            values4.add(new BarEntry(i, (float) (randomMultiplier.get(3))));
        }

        BarDataSet set1, set2, set3, set4;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) chart.getData().getDataSetByIndex(2);
            set4 = (BarDataSet) chart.getData().getDataSetByIndex(3);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            set4.setValues(values4);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "Đáp án A");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(values2, "Đáp án B");
            set2.setColor(Color.rgb(164, 228, 251));
            set3 = new BarDataSet(values3, "Đáp án C");
            set3.setColor(Color.rgb(242, 247, 158));
            set4 = new BarDataSet(values4, "Đáp án D");
            set4.setColor(Color.rgb(255, 102, 0));

            BarData data = new BarData(set1, set2, set3, set4);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);

            chart.setData(data);
        }

        // specify the width each bar should have
        chart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        chart.getXAxis().setAxisMinimum(startCount);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        chart.getXAxis().setAxisMaximum(startCount + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(startCount, groupSpace, barSpace);
        chart.invalidate();
    }

    private List<Integer> selectRandom() {
        int targetSum = 100;
        int sum = 0;
        List<Integer> randomNumbers = new ArrayList<>();

        Random random = new Random();
        while (randomNumbers.size() < 3) {
            int randomNumber = random.nextInt(targetSum - sum - 1) + 1;
            randomNumbers.add(randomNumber);
            sum += randomNumber;
        }

        randomNumbers.add(targetSum - sum);
        return randomNumbers;
    }

}