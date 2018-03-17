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
import com.feinno.sdk.dapi.v3.BuddyManager;
import com.feinno.sdk.result.v3.BuddyResult;
import com.feinno.uws.BootBroadcastReceiver;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * 好友通知事件 Adapter
 */
public class BuddyEvtAdapter extends CursorAdapter
{

    public final String TAG = "BuddyEvtAdapter";
    private Context mContext;
    public BuddyEvtAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View retView = inflater.inflate(R.layout.list_item_buddy_evt, null);
        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvFromId = (TextView) view.findViewById(R.id.tv_from_id);
        TextView tvToId = (TextView) view.findViewById(R.id.tv_to_id);
        TextView tvFromName = (TextView) view.findViewById(R.id.tv_from_name);
        TextView tvEvent= (TextView) view.findViewById(R.id.tv_event);
        TextView tvTime= (TextView) view.findViewById(R.id.tv_time);
        TextView tvReason= (TextView) view.findViewById(R.id.tv_reason);
        TextView tvRefuseReason= (TextView) view.findViewById(R.id.tv_refuse_reason);
        Button btnAccept = (Button) view.findViewById(R.id.btn_accept);
        Button btnRefuse = (Button) view.findViewById(R.id.btn_refuse);


        final int userId = cursor.getInt(cursor.getColumnIndex("FROM_USER_ID"));
        tvFromId.setText(userId+"");
        tvToId.setText(cursor.getString(cursor.getColumnIndex("TO_USER_ID")));
        tvFromName.setText(cursor.getString(cursor.getColumnIndex("FROM_USER_NAME")));
        int state = cursor.getInt(cursor.getColumnIndex("STATE"));
        if(state == 1) {
            tvEvent.setText("添加请求");
        } else {
            tvEvent.setText("处理结果");
        }
        long millsec = cursor.getLong(cursor.getColumnIndex("TIME"));
        Date date = new Date(millsec);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        tvTime.setText(sdf.format(date));

        tvReason.setText(cursor.getString(cursor.getColumnIndex("REASON")));
        tvRefuseReason.setText(cursor.getString(cursor.getColumnIndex("REFUSE_REASON")));
        tvFromName.setText(cursor.getString(cursor.getColumnIndex("REFUSE_REASON")));

        if (state == 1) {
            btnAccept.setVisibility(View.VISIBLE);
            btnRefuse.setVisibility(View.VISIBLE);
        } else {
            btnAccept.setVisibility(View.GONE);
            btnRefuse.setVisibility(View.GONE);
        }

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRequest(userId, true, "");
            }
        });
    }

    void handleRequest(int userId, boolean accepted, final String reason) {

        String action = "com.intercs.uwsdemo.buddy_handle_"+userId;
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "in pending intent receiver");
                context.unregisterReceiver(this);
                BuddyResult result = BroadcastActions.getResult(intent);
                Log.e(TAG, result.errorCode + "");
            }
        }, new IntentFilter(action));

        if(accepted) {
            try {
                // 同意添加好友请求
                BuddyManager.acceptReq(LoginState.getStartUser(mContext), userId, pi);
            } catch (RemoteException e) {
                Log.e(TAG, "handle error:", e);
            }
        }
        else {
            try {
                // 拒绝添加好友请求
                BuddyManager.refuseReq(LoginState.getStartUser(mContext), userId, reason, pi);
            } catch (RemoteException e) {
                Log.e(TAG, "handle error:", e);
            }
        }
    }
}
