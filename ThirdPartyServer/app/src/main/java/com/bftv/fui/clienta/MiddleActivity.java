package com.bftv.fui.clienta;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.bftv.fui.intent.AppIntentManager;
import com.bftv.fui.thirdparty.BindAidlManager;
import com.bftv.fui.thirdparty.VoiceFeedback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author by Less on 17/7/22.
 */

public class MiddleActivity extends Activity{

    private ListView mListview;

    private MiddleBaseAdapter middleBaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);

        mListview = (ListView)findViewById(R.id.listview);

        final VoiceFeedback voiceFeedback = getIntent().getParcelableExtra("test");

        middleBaseAdapter = new MiddleBaseAdapter(voiceFeedback.listMiddleData,LayoutInflater.from(this));
        mListview.setAdapter(middleBaseAdapter);

        Toast.makeText(this,"第一次进入长度:"+voiceFeedback.listMiddleData.size(),Toast.LENGTH_SHORT).show();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("type","middle_cmd");
                hashMap.put("content","下一页");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(hashMap.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                BindAidlManager.getInstance().dealMessage(MiddleActivity.this,"com.bftv.fui.test","下一页",jsonObject.toString(), new BindAidlManager.OnBindAidlListener() {
                    @Override
                    public void onSuccess(final VoiceFeedback voiceFeedback) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(voiceFeedback != null){
                                    Toast.makeText(MiddleActivity.this,voiceFeedback.feedback+"长度:"+voiceFeedback.listMiddleData.size(),Toast.LENGTH_SHORT).show();
                                    middleBaseAdapter.setList(voiceFeedback.listMiddleData);
                                    middleBaseAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        });


        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoiceFeedback.MiddleData.AllIntent allIntent = voiceFeedback.listMiddleData.get(0).allIntent;
                AppIntentManager.getInstance().setIntent(MiddleActivity.this,true,allIntent.action_name,
                        allIntent.package_name,allIntent.activity_name,allIntent.type,allIntent.parameter,allIntent.flag,null,null);
            }
        });

    }



    static class MiddleBaseAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<VoiceFeedback.MiddleData> AppList;

        public MiddleBaseAdapter(List<VoiceFeedback.MiddleData> installedAppList, LayoutInflater Inflater) {
            mInflater = Inflater;
            AppList = installedAppList;
        }

        public void setList(List<VoiceFeedback.MiddleData> AppList){
            this.AppList = AppList;
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

