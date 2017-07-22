package com.bftv.fui.clienta;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bftv.fui.thirdparty.VoiceFeedback;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by Less on 17/7/22.
 */

public class MiddleActivity extends Activity{

    private ListView mListview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);

        mListview = (ListView)findViewById(R.id.listview);



        VoiceFeedback voiceFeedback = getIntent().getParcelableExtra("test");

        mListview.setAdapter(new MiddleBaseAdapter(voiceFeedback.listMiddleData,LayoutInflater.from(this)));

    }



    static class MiddleBaseAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<VoiceFeedback.MiddleData> AppList;

        public MiddleBaseAdapter(List<VoiceFeedback.MiddleData> installedAppList, LayoutInflater Inflater) {
            mInflater = Inflater;
            AppList = installedAppList;
        }

        @Override
        public int getCount() {
            return AppList.size();
        }

        @Override
        public Object getItem(int position) {
            return AppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.item_middle, parent, false);
            ViewHolder viewholder = new ViewHolder();
            viewholder.btn = (Button) convertView.findViewById(R.id.btn);
            VoiceFeedback.MiddleData item = (VoiceFeedback.MiddleData) getItem(position);
            viewholder.btn.setText(item.middleName);
            convertView.setTag(viewholder);

            return convertView;
        }

    }

    public static class ViewHolder {
        private Button btn;
    }
}

