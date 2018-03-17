package com.interrcs.uwsdemo.adapter;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.GroupManager;
import com.feinno.sdk.enums.GroupEventType;
import com.feinno.sdk.result.GroupOpResult;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GroupMemberAdapter extends CursorAdapter {

    public final String TAG = "GroupMemberAdapter";
    private Context mContext;

    public GroupMemberAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View retView = inflater.inflate(R.layout.list_item_group_member, null);
        return retView;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        final int groupId = cursor.getInt(cursor.getColumnIndex("GROUP_ID"));
        final int userId = cursor.getInt(cursor.getColumnIndex("USER_ID"));


        //Just a demo here, of course, we shoud use ViewHolder..
        TextView tvUserId = (TextView) view.findViewById(R.id.tv_user_id);
        TextView tvDisplayName = (TextView) view.findViewById(R.id.tv_local_name);
        TextView tvJoinTime = (TextView) view.findViewById(R.id.tv_join_time);
        TextView tvRole = (TextView) view.findViewById(R.id.tv_role);

        Button btnKick = (Button) view.findViewById(R.id.btn_kick);
        Button btnTransfer = (Button) view.findViewById(R.id.btn_transfer);

        tvUserId.setText("UserId:" + cursor.getInt(cursor.getColumnIndex("USER_ID")));
        tvDisplayName.setText("群内名称:" + cursor.getString(cursor.getColumnIndex("LOCAL_NAME")));
        tvRole.setText("角色:" + cursor.getInt(cursor.getColumnIndex("ROLE")));

        long millsec = cursor.getLong(cursor.getColumnIndex("JOIN_TIME"));
        Date date = new Date(millsec);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        tvJoinTime.setText(sdf.format(date));


        btnKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "com.intercs.uwsdemo.group_remote_" + groupId;
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        context.unregisterReceiver(this);
                        GroupOpResult result = BroadcastActions.getResult(intent);
                        Toast.makeText(context, "删除群成员结果:" + result.errorCode, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter(action));

                try {
                    GroupManager.removeMember(LoginState.getStartUser(context), groupId+"", userId+"", pi);
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        });

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "com.intercs.uwsdemo.group_transfer_" + groupId;
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        context.unregisterReceiver(this);
                        GroupOpResult result = BroadcastActions.getResult(intent);
                        Toast.makeText(context, "转交管理员结果:" + result.errorCode, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter(action));

                try {
                    GroupManager.changeManager(LoginState.getStartUser(context), groupId+"", userId+"", pi);
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        });


    }
}
