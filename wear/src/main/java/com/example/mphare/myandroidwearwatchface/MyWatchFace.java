package com.example.mphare.myandroidwearwatchface;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyWatchFace extends Activity
{

  private TextView mTextView;
  private TextView mTime, mBattery;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_watch_face);
    final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
    stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener()
    {

      @Override
      public void onLayoutInflated(WatchViewStub stub)
      {
        mTextView = (TextView) stub.findViewById(R.id.text);
        mTime = (TextView) stub.findViewById(R.id.watch_battery);
        mTimeInfoReceiver.onReceive(MyWatchFace.this, registerReceiver(null, INTENT_FILTER));
        registerReceiver(mTimeInfoReceiver, INTENT_FILTER);

      }

    });
  }

  @Override protected void onDestroy()
  {
    super.onDestroy();
    ungegisterReceiver(mTimeInfoReceiver);
  }

  private final static IntentFilter INTENT_FILTER;

  static
  {
    INTENT_FILTER = new IntentFilter();
    INTENT_FILTER.addAction(Intent.ACTION_TIME_TICK);
    INTENT_FILTER.addAction(Intent.ACTION_TIME_CHANGED);
    INTENT_FILTER.addAction(Intent.ACTION_TIMEZONE_CHANGED);
  }

  private final String TIME_FORMAT_DISPLAYED = "kk:mm a";

  private BroadcastReceiver mTimeInfoReceiver = new BroadcastReceiver()
  {
    @Override public void onReceive(Context arg0, Intent intent)
    {
      mTime.setText(new SimpleDateFormat(TIME_FORMAT_DISPLAYED).format(Calendar.getInstance().getTime()));

    }
  };
}
