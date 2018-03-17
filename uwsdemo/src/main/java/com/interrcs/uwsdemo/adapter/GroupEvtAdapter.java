package com.interrcs.uwsdemo.adapter;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.GroupManager;
import com.feinno.sdk.dapi.v3.BuddyManager;
import com.feinno.sdk.enums.GroupEventType;
import com.feinno.sdk.result.GroupOpResult;
import com.feinno.sdk.result.v3.BuddyResult;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GroupEvtAdapter extends CursorAdapter{

    public final String TAG = "GroupEvtAdapter";
    private Context mContext;
    public GroupEvtAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View retView = inflater.inflate(R.layout.list_item_group_evt, null);
        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String sql = "CREATE TABLE 'GROUP_EVENT' (" +
                "'_id' INTEGER PRIMARY KEY ," +
                "'SOURCE_USER' INTEGER ," +
                "'SOURCE_USER_NICKNAME' TEXT ," +
                "'TIME' INTEGER ," +
                "'EVENT' INTEGER ," +
                "'GROUP_ID' INTEGER " +
                "'HANDLE_RESULT' INTEGER " +
                ");";

        //Just a demo here, of course, we shoud use ViewHolder..
        TextView tvFromId = (TextView) view.findViewById(R.id.tv_from_id);
        TextView tvFromName = (TextView) view.findViewById(R.id.tv_from_name);
        TextView tvEvent= (TextView) view.findViewById(R.id.tv_event);
        TextView tvTime= (TextView) view.findViewById(R.id.tv_time);
        Button btnAccept = (Button) view.findViewById(R.id.btn_accept);
        Button btnRefuse = (Button) view.findViewById(R.id.btn_refuse);

        //set to gone first
        tvFromId.setVisibility(View.GONE);
        tvFromName.setVisibility(View.GONE);
        btnAccept.setVisibility(View.GONE);
        btnRefuse.setVisibility(View.GONE);


        int event = cursor.getInt(cursor.getColumnIndex("EVENT"));
        if(event == GroupEventType.INVITED.value()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int handleResult = cursor.getInt(cursor.getColumnIndex("HANDLE_RESULT"));
            final int groupId = cursor.getInt(cursor.getColumnIndex("GROUP_ID"));
            final int inviter = cursor.getInt(cursor.getColumnIndex("SOURCE_USER"));

            String ev = "事件:被邀请加入群";
            tvFromId.setVisibility(View.VISIBLE);
            tvFromName.setVisibility(View.VISIBLE);
            if(handleResult == 0) {
                btnAccept.setVisibility(View.VISIBLE);
                btnRefuse.setVisibility(View.VISIBLE);

                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleRequest(groupId, inviter, true);
                    }
                });

                btnRefuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleRequest(groupId, inviter, false);
                    }
                });
            }
            else {
                if(handleResult == 1) {
                    ev+=",已同意";
                }
                else {
                    ev+=", 已拒绝";
                }
            }
            tvEvent.setText(ev);


            final int userId = cursor.getInt(cursor.getColumnIndex("SOURCE_USER"));
            tvFromId.setText("邀请人:" + userId);
            tvFromName.setText("邀请人昵称:" + cursor.getString(cursor.getColumnIndex("SOURCE_USER_NICKNAME")));



        }
        else if (event == GroupEventType.BOOTED.value()) {
            tvEvent.setText("事件:被提出群");
        }
        else if (event == GroupEventType.TRANSFER.value()) {
            tvEvent.setText("事件:被提升为管理员");
        }
        else if (event == GroupEventType.DISMISSED.value()) {
            tvEvent.setText("事件:群解散");
        }
        else if (event == GroupEventType.CONFIRMED.value()) {
            tvEvent.setText("事件:已经同意加入");
        }


        long millsec = cursor.getLong(cursor.getColumnIndex("TIME"));
        Date date = new Date(millsec);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        tvTime.setText(sdf.format(date));
    }

    void handleRequest(int groupId, int inviter, boolean accepted) {
        String action = "com.intercs.uwsdemo.group_join_" + groupId;
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                GroupOpResult result = BroadcastActions.getResult(intent);

            }
        }, new IntentFilter(action));

        if (accepted) {
            try {
                //加入群
                GroupManager.joinGroup(LoginState.getStartUser(mContext), groupId + "", inviter+"", pi);
            } catch (Exception e) {
                Log.e(TAG, "handle error:", e);
            }
        }
    }
}
