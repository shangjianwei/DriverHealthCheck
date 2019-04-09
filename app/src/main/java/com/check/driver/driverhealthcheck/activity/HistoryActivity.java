package com.check.driver.driverhealthcheck.activity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.check.driver.driverhealthcheck.R;
import com.check.driver.driverhealthcheck.base.BaseActivity;
import com.check.driver.driverhealthcheck.bean.MessageBean;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM;

public class HistoryActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private LineChart chart;

    private RadioGroup radioGroup;
    private ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initListener();
        showData();
//        setData(45, 180);
        radioGroup.check(R.id.rb_temp);
        getData();
    }

    private List<MessageBean> list;

    private void getData() {
        LitePal.order("time").findAsync(MessageBean.class).listen(new FindMultiCallback<MessageBean>() {
            @Override
            public void onFinish(List<MessageBean> listA) {
                list = listA;
                setData(listA);
                chart.invalidate();
            }
        });
    }


    private void showData() {
        chart.setBackgroundColor(Color.WHITE);

        // disable description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // set listeners

        chart.setDrawGridBackground(false);

        // create marker to display box when values are selected


        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        chart.setPinchZoom(true);

    }

    private double max = 0, min = 100000;

    private void setData(List<MessageBean> list) {
        if (list == null || list.size() < 2) {
            return;
        }
        ArrayList<Entry> valueH = new ArrayList<>();
        ArrayList<Entry> valueL = new ArrayList<>();
        max = 0;
        min = 1000000;

        for (int i = 0; i < list.size(); i++) {
            if (type == 4) {
                MessageBean messageBean = list.get(i);
                double bpL = messageBean.getBp_L();
                double bpH = messageBean.getBp_H();
                max = bpL > max ? bpL : max;
                max = bpH > max ? bpH : max;
                min = bpL > min ? min : bpL;
                min = bpH > min ? min : bpH;
                valueH.add(new Entry(i, (float) bpH));
                valueL.add(new Entry(i, (float) bpL));
            } else {
                getItemValue(i, list.get(i), valueH);
            }

        }
        double center = (max - min) * 2 / 10;
        max = max + center;
        min = min - center;

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum((float) max);
            yAxis.setAxisMinimum((float) min);
        }


        {   // // Create Limit Lines // //
            LimitLine llXAxis = new LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);

            LimitLine ll1 = new LimitLine(150f, "Upper Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);


            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);
            xAxis.setPosition(BOTTOM);

            // add limit lines
            yAxis.addLimitLine(ll1);

            //xAxis.addLimitLine(llXAxis);
        }


        LineDataSet set1;
        LineDataSet set2;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set1.setValues(valueH);
            if (type == 4) {
                set2.setValues(valueL);
            } else {
                set2.setValues(valueH);
            }
            set1.notifyDataSetChanged();

            set2.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(valueH, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }
            // create a dataset and give it a type
            if (type == 4) {
                set2 = new LineDataSet(valueL, "DataSet 2");
            } else {
                set2 = new LineDataSet(valueH, "DataSet 2");
            }


            set2.setDrawIcons(false);

            // draw dashed line
            set2.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.RED);
            set2.setDrawCircles(false);
            set2.setDrawValues(false);
            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            // line thickness and point size
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);

            // draw points as solid circles
            set2.setDrawCircleHole(false);

            // customize legend entry
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);

            // text size of values
            set2.setValueTextSize(9f);

            // draw selection line as dashed
            set2.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set2.setDrawFilled(false);
            set2.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

//            // set color of filled area
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);
//                set2.setFillDrawable(drawable);
//            } else {
//                set1.setFillColor(Color.BLACK);
//                set2.setFillColor(Color.BLACK);
//            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets
            dataSets.add(set2); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }

        if (type == 4) {
            chart.getData().getDataSetByIndex(1).setVisible(true);
        } else {
            chart.getData().getDataSetByIndex(1).setVisible(false);
        }
    }

    private void getItemValue(int i, MessageBean messageBean, ArrayList<Entry> values) {
        switch (type) {
            case 0: {
                double temp = messageBean.getTem();
                max = temp > max ? temp : max;
                min = temp > min ? min : temp;
                values.add(new Entry(i, (float) temp));
            }
            break;
            case 1: {
                double temp = messageBean.getWet();
                max = temp > max ? temp : max;
                min = temp > min ? min : temp;
                values.add(new Entry(i, (float) temp));
            }

            break;
            case 2: {
                double temp = messageBean.getCo();
                max = temp > max ? temp : max;
                min = temp > min ? min : temp;
                values.add(new Entry(i, (float) temp));
            }
            break;
            case 3: {
                double temp = messageBean.getHeart();
                max = temp > max ? temp : max;
                min = temp > min ? min : temp;
                values.add(new Entry(i, (float) temp));
            }
            break;
            case 5: {
                double temp = messageBean.getBf();
                max = temp > max ? temp : max;
                min = temp > min ? min : temp;
                values.add(new Entry(i, (float) temp));
            }
            break;
        }
    }


    private void initListener() {
        ivBack.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        chart = findViewById(R.id.lc_historical_data);
        radioGroup = findViewById(R.id.radioGroup);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }

    }

    private int type = 0;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radbtn = (RadioButton) findViewById(checkedId);
        switch (radbtn.getId()) {
            case R.id.rb_temp:
//                showToast("温度");
                type = 0;

                break;
            case R.id.rb_hum:
//                showToast("湿度");
                type = 1;
                break;
            case R.id.rb_CO:
//                showToast("一氧化碳浓度");
                type = 2;
                break;
            case R.id.rb_heart_rate:
//                showToast("心率");
                type = 3;
                break;
            case R.id.rb_blood_pressure:
//                showToast("血压");
                type = 4;
                break;
            case R.id.rb_blood_fat:
//                showToast("血脂");
                type = 5;
                break;
        }
        setData(list);
        chart.invalidate();
    }
}
