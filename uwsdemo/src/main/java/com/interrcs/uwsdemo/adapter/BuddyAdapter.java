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
import android.widget.Toast;
import com.feinno.sdk.BroadcastActions;
import com.feinno.sdk.dapi.CapsManager;
import com.feinno.sdk.dapi.v3.BuddyManager;
import com.feinno.sdk.enums.ErrorCode;
import com.feinno.sdk.result.CapsResult;
import com.feinno.sdk.result.v3.BuddyResult;
import com.feinno.sdk.utils.JsonUtils;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.UWSReceiverService;
import com.interrcs.uwsdemo.activity.MessageActivity;
import com.interrcs.uwsdemo.activity.UserActivity;

import java.util.UUID;

/**
 * 好友列表 Adapter
 */
public class BuddyAdapter extends CursorAdapter{

    public static final String TAG = "BuddyAdapter";
    private Context mContext;
    public BuddyAdapter(Context context, Cursor c) {
        super(context, c);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View retView = inflater.inflate(R.layout.list_item_buddy, null);
        return retView;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView tvUserId = (TextView) view.findViewById(R.id.tv_user_id);
        TextView tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        TextView tvLocalName = (TextView) view.findViewById(R.id.tv_local_name);
        TextView tvNickName = (TextView) view.findViewById(R.id.tv_nickname);
        TextView tvImpresa = (TextView) view.findViewById(R.id.tv_impresa);
        TextView tvPVersion = (TextView) view.findViewById(R.id.tv_portrait_version);
        TextView tvPPath = (TextView) view.findViewById(R.id.tv_portrait_path);
        final TextView tvCaps = (TextView) view.findViewById(R.id.tv_caps);

        Button btnProfile = (Button) view.findViewById(R.id.btn_profile);
        Button btnDelete = (Button) view.findViewById(R.id.btn_delete);
        Button btnMemo = (Button) view.findViewById(R.id.btn_memo);
        Button btnMessage = (Button) view.findViewById(R.id.btn_message);
        Button btnDownload = (Button) view.findViewById(R.id.btn_portrait);
        Button btnCaps = (Button) view.findViewById(R.id.btn_caps);

        final String uid = cursor.getString(cursor.getColumnIndex("USER_ID"));
        String userName = cursor.getString(cursor.getColumnIndex("USER_NAME"));
        String localName = cursor.getString(cursor.getColumnIndex("LOCAL_NAME"));
        String nickName = cursor.getString(cursor.getColumnIndex("NICK_NAME"));
        String portraitPath = cursor.getString(cursor.getColumnIndex("PORTRAIT_PATH"));
        int portraitVersion = cursor.getInt(cursor.getColumnIndex("PORTRAIT_VERSION"));
        String impresa = cursor.getString(cursor.getColumnIndex("IMPRESA"));

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查看好友资料
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("userId", uid);
                context.startActivity(intent);
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 下载头像
                UWSReceiverService.downloadPortrait(context, Integer.parseInt(uid));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除好友
                deleteBuddy(uid);
            }
        });

        btnMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 备注好友
                memoBuddy(uid);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入好友聊天界面
                Intent intent = new Intent(context, MessageActivity.class);
                Log.i(TAG, "put userId:" + uid);
                intent.putExtra("userId", uid);
                context.startActivity(intent);
            }
        });

        btnCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取好友能力
                getCaps(uid, tvCaps);
            }
        });


        tvUserId.setText(uid);
        tvUserName.setText(userName);
        tvNickName.setText(nickName);
        tvLocalName.setText(localName);
        tvPPath.setText(portraitPath);
        tvPVersion.setText(portraitVersion+"");
        tvImpresa.setText(impresa);
    }

    // 获取用户能力
    private void getCaps(String userId, final TextView tvCaps) {
        String action = "getCaps";
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(mContext,
                UUID.randomUUID().hashCode(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT );

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mContext.unregisterReceiver(this);
                CapsResult result = BroadcastActions.getResult(intent);
                tvCaps.setText(JsonUtils.toJson(result));
            }
        };
        mContext.registerReceiver(receiver, new IntentFilter(action));

        try {
            CapsManager.getCap(LoginState.getStartUser(mContext), userId, pi);
        } catch (RemoteException e) {
            Log.e(TAG, "getCap error", e);
        }

    }

    // 给好友添加备注名称
    private void memoBuddy(String userId) {

        String action = "memoBuddy";
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(mContext,
                UUID.randomUUID().hashCode(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT );

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mContext.unregisterReceiver(this);
                BuddyResult result = BroadcastActions.getResult(intent);
                if(result.errorCode == ErrorCode.OK.value()) {
                    Toast.makeText(context, "备注成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "备注失败:"+ result.errorCode +result.errorExtra, Toast.LENGTH_SHORT).show();
                }
            }
        };
        mContext.registerReceiver(receiver, new IntentFilter(action));

        try {
            BuddyManager.memoBuddy(LoginState.getStartUser(mContext), Integer.parseInt(userId), "备注名称", pi);
        } catch (RemoteException e) {
            Log.e(TAG, "memoBuddyError", e);
        }


    }

    // 删除好友
    private void deleteBuddy(String userId) {

        String action = "deleteBuddy";
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(mContext,
                UUID.randomUUID().hashCode(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT );

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mContext.unregisterReceiver(this);
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            }
        };
        mContext.registerReceiver(receiver, new IntentFilter(action));

        try {
            BuddyManager.deleteBuddy(LoginState.getStartUser(mContext), Integer.parseInt(userId), pi);
        } catch (RemoteException e) {
            Log.e(TAG, "deleteBuddy error", e);
        }

    }
}
