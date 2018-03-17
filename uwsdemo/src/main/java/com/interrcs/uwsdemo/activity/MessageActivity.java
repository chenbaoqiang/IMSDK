package com.interrcs.uwsdemo.activity;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.feinno.sdk.args.ChatUri;
import com.feinno.sdk.args.TextMessageArg;
import com.feinno.sdk.dapi.RCSMessageManager;
import com.feinno.sdk.enums.ChatType;
import com.feinno.sdk.enums.ContentType;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.adapter.MessageAdapter;
import com.interrcs.uwsdemo.data.MessageContentProvider;
import android.text.TextUtils;

import java.util.Date;
import java.util.UUID;

// 消息列表，并能发送消息
public class MessageActivity extends ListActivity{
    public static final String TAG = "MessageActivity";

    ContentObserver observer;

    Button btnSend;

    String targetId;
    int chattype = ChatType.SINGLE.value();

   @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_message);
       btnSend = (Button) findViewById(R.id.btn_send);

       String gid = getIntent().getStringExtra("groupId");
       if(!TextUtils.isEmpty(gid)) {
           chattype = ChatType.GROUP.value();
           targetId = gid;
       } else {
           targetId = getIntent().getStringExtra("userId");
       }

       //try to get from intent
       chattype = getIntent().getIntExtra("chattype", chattype);
       Log.d(TAG, "targetId:" + targetId + ", chattype:" + chattype);

       setListAdapter(new MessageAdapter(this, null));
       btnSend.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               ChatUri uri =  new ChatUri(ChatType.fromInt(chattype), targetId + "");
               String msgId = UUID.randomUUID().toString();
               TextMessageArg arg = new TextMessageArg(msgId, uri, "message content", true, true, "");
               try {
                   //现将消息保存到数据库
                   ContentValues values = new ContentValues();
                   /*
                           String sql = "CREATE TABLE 'CHAT_MESSAGE' ( " +
                "'_id' INTEGER PRIMARY KEY ," +
                "'IMDN_ID' TEXT NOT NULL ," +
                "'MSG_FROM' TEXT," +
                "'MSG_TO' TEXT," +
                "'RECEIVE_TIME' INTEGER," +
                "'CONTENT' TEXT," +
                "'CHAT_TYPE' INTEGER," +
                "'CONTENT_TYPE' INTEGER," +
                "'STATUS' INTEGER," +
                "'TRANSFER_ID' TEXT," +
                "'PROGRESS' INTEGER," +
                "'FILE_SIZE' INTEGER" +
                ");";
                    */
                   values.put("IMDN_ID", msgId);
                   values.put("MSG_FROM", LoginState.getUserId(getApplicationContext()));
                   values.put("CONTENT", arg.getContent());
                   values.put("MSG_TO", targetId);
                   values.put("CHAT_TYPE", chattype);
                   values.put("CONTENT_TYPE", ContentType.TEXT.value());
                   values.put("STATUS", MessageContentProvider.STATUS_SENDING);
                   values.put("RECEIVE_TIME", (new Date()).getTime());
                   getContentResolver().insert(MessageContentProvider.CONTENT_URI, values);

                   //发送消息
                   RCSMessageManager.sendMessage(LoginState.getStartUser(getApplicationContext()), arg, null);
               } catch (Exception e) {
                   Log.e(TAG, "", e);
               }
           }
       });

       observer = new ContentObserver(new Handler(getMainLooper())) {
           @Override
           public void onChange(boolean selfChange) {
               super.onChange(selfChange);
               // 监测到数据库变化，刷新消息列表
               beginLoadData();
           }
       };
   }


    @Override
    public void onStart() {
        super.onStart();
        // 注册数据库变化观察者
        getContentResolver().registerContentObserver(MessageContentProvider.CONTENT_URI, true, observer);
        beginLoadData();
    }


    private void beginLoadData() {
        AsyncTask<Object, Object, Object> mytask = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] params) {
                Log.d(TAG, "start load db, chattype:" +chattype);

                if(chattype == 128) {
                    return getContentResolver().query(MessageContentProvider.CONTENT_URI, null,
                        "CHAT_TYPE=?", new String[] {chattype+""}, "_id desc" );
                } else {
                    return getContentResolver().query(MessageContentProvider.CONTENT_URI, null,
                        "(MSG_FROM=? or MSG_TO=?) and CHAT_TYPE=?", new String[] {targetId +"", targetId +"", chattype+""},
                        "_id desc" );
                }
            }

            @Override
            protected void onPostExecute(Object o) {
                Cursor cursor = (Cursor) o;
                Log.d(TAG, "cursor count:" + ((Cursor) o).getCount());
                ((MessageAdapter)getListAdapter()).changeCursor(cursor);
            }
        };
        mytask.execute();
    }



    @Override
    public void onStop() {
        super.onStop();
        getContentResolver().unregisterContentObserver(observer);
    }
}
