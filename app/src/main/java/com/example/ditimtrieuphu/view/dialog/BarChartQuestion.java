package com.example.ditimtrieuphu.view.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.ditimtrieuphu.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;

public class BarChartQuestion extends DialogFragment {
    private BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_question_audience, container);
        barChart = view.findViewById(R.id.bar_chart_question);
        BarDataSet barDataSet = new BarDataSet(dataValue(),"Ý kiến khán giả trường quay");
        BarDataSet set1, set2, set3, set4;
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();

        float randomMultiplier = 100;

        for (int i = 0; i < 100; i++) {
            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
        }

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

        barChart.setData(data);
        barChart.getBarData().setBarWidth(0.2f);

        // restrict the x-axis range
        barChart.getXAxis().setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(100, 100) * 10);
        barChart.groupBars(100, 100, 100);
        barChart.invalidate();
//        CloseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        return view;
    }

    private ArrayList<BarEntry> dataValue() {
        ArrayList<BarEntry> dataValues = new ArrayList<>();
        dataValues.add(new BarEntry(1,5));
        dataValues.add(new BarEntry(2,15));
        dataValues.add(new BarEntry(3,20));
        dataValues.add(new BarEntry(4,60));
        return dataValues;
    }
}
