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
import com.feinno.sdk.result.GroupOpResult;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.activity.GroupMemberActivity;
import com.interrcs.uwsdemo.activity.MessageActivity;

import java.util.Random;
import java.util.UUID;


public class GroupAdapter extends CursorAdapter{

    public final String TAG = "GroupAdapter";
    private Context mContext;
    public GroupAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View retView = inflater.inflate(R.layout.list_item_group, null);
        return retView;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        String sql = "CREATE TABLE 'GROUPS' (" +
                "'_id' INTEGER PRIMARY KEY ," +
                "'GROUP_ID' INTEGER ," +
                "'GROUP_NAME' TEXT ," +
                "'GROUP_VERSION' TEXT " +
                ");";

        //Just a demo here, of course, we shoud use ViewHolder..
        TextView tvGroupId = (TextView) view.findViewById(R.id.tv_group_id);
        TextView tvGroupName = (TextView) view.findViewById(R.id.tv_group_name);

        Button btnQuit = (Button) view.findViewById(R.id.btn_quit);
        Button btnInfo = (Button) view.findViewById(R.id.btn_info);
        Button btnGroupMember = (Button) view.findViewById(R.id.btn_group_member);
        Button btnInvite = (Button) view.findViewById(R.id.btn_invite);
        Button btnChangeName = (Button) view.findViewById(R.id.btn_change_name);
        Button btnChangeNickName = (Button) view.findViewById(R.id.btn_change_nickname);
        Button btnMessage = (Button) view.findViewById(R.id.btn_message);
        Button btnDelete = (Button) view.findViewById(R.id.btn_delete);

        final int groupId = cursor.getInt(cursor.getColumnIndex("GROUP_ID"));
        final String groupName = cursor.getString(cursor.getColumnIndex("GROUP_NAME"));
        tvGroupId.setText("群ID：" + groupId);
        tvGroupName.setText("群名称:" + groupName);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "com.intercs.uwsdemo.group_delete" + groupId;
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        context.unregisterReceiver(this);
                        GroupOpResult result = BroadcastActions.getResult(intent);
                        Toast.makeText(context, "删除群结果:" + result.errorCode, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter(action));

                try {
                    GroupManager.deleteGroup(LoginState.getStartUser(context), groupId+"", pi);
                } catch (Exception e) {
                    Log.e(TAG, "" , e);
                }
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "com.intercs.uwsdemo.group_quit" + groupId;
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        context.unregisterReceiver(this);
                        GroupOpResult result = BroadcastActions.getResult(intent);
                        Toast.makeText(context, "群出群结果:" + result.errorCode, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter(action));

                try {
                    GroupManager.quitGroup(LoginState.getStartUser(context), groupId+"", pi);
                } catch (Exception e) {
                    Log.e(TAG, "" , e);
                }
            }
        });


        btnGroupMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupMemberActivity.class);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "com.intercs.uwsdemo.group_change_name_" + groupId;
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        context.unregisterReceiver(this);
                        GroupOpResult result = BroadcastActions.getResult(intent);
                        Toast.makeText(context, "修改群名结果:" +result.errorCode, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter(action));

                try {
                    GroupManager.modifySubject(LoginState.getStartUser(context), groupId+"", "随机群名-"+ UUID.randomUUID().toString(), pi);
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        });

        btnChangeNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "com.intercs.uwsdemo.group_change_nickname_" + groupId;
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        context.unregisterReceiver(this);
                        GroupOpResult result = BroadcastActions.getResult(intent);
                        Toast.makeText(context, "修改群内昵称结果:" +result.errorCode, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter(action));

                try {
                    long r = System.currentTimeMillis() / 100;
                    GroupManager.modifyNickname(LoginState.getStartUser(context), groupId+"", "随机群内昵称-" + r, pi);
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("groupId", groupId+"");
                context.startActivity(intent);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "com.intercs.uwsdemo.group_sub_info_" + groupId;
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        context.unregisterReceiver(this);
                        GroupOpResult result = BroadcastActions.getResult(intent);
                        Toast.makeText(context, "订阅群信息结果:" +result.errorCode, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter(action));

                try {
                    GroupManager.subGroup(LoginState.getStartUser(context), groupId+"", "0", "0", pi);
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        });


        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = "com.intercs.uwsdemo.group_change_invite_" + groupId;
                Intent intent = new Intent(action);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        context.unregisterReceiver(this);
                        GroupOpResult result = BroadcastActions.getResult(intent);
                        Toast.makeText(context, "邀请加入群结果:" +result.errorCode, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter(action));

                try {
                    GroupManager.inviteMember(LoginState.getStartUser(context), groupId +"", "+8615901435218", pi);
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        });
    }

}
