package com.interrcs.uwsdemo.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.feinno.sdk.args.FetchFileMessageArg;
import com.feinno.sdk.dapi.RCSMessageManager;
import com.feinno.sdk.enums.ContentType;
import com.interrcs.uwsdemo.LoginState;
import com.interrcs.uwsdemo.R;
import com.interrcs.uwsdemo.data.MessageContentProvider;

import java.io.File;
import java.util.Date;

/**
 * 消息列表Adapter
 */
public class MessageAdapter extends CursorAdapter {
    public static final String TAG = "MessageAdapter";
    public MessageAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View retView = inflater.inflate(R.layout.list_item_message, null);
        return retView;
    }

       /*
        android:id="@+id/tv_from"/>
 android:id="@+id/tv_to"/>
 android:id="@+id/tv_imdn_id"/>
 android:id="@+id/tv_content"/>
 android:id="@+id/tv_file_path"/>
 android:id="@+id/tv_progress"/>

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
                    TextView tvFrom;

    */

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final String messageId = cursor.getString(cursor.getColumnIndex("IMDN_ID"));
        final int fileSize = cursor.getInt(cursor.getColumnIndex("FILE_SIZE"));
        final String transferId = cursor.getString(cursor.getColumnIndex("TRANSFER_ID"));
        final int progress = cursor.getInt(cursor.getColumnIndex("PROGRESS"));

        TextView tvTo = (TextView) view.findViewById(R.id.tv_to);
        TextView tvFrom = (TextView) view.findViewById(R.id.tv_from);
        TextView tvImdnId = (TextView) view.findViewById(R.id.tv_imdn_id);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        TextView tvTime = (TextView) view.findViewById(R.id.tv_time);
        TextView tvProgress = (TextView) view.findViewById(R.id.tv_progress);
        TextView tvStatus = (TextView) view.findViewById(R.id.tv_status);
        TextView tvCtt = (TextView) view.findViewById(R.id.tv_content_type);
        TextView tvTransfer = (TextView) view.findViewById(R.id.tv_transfer);

        Button btnDownload = (Button)view.findViewById(R.id.btn_download);

        tvFrom.setText("发送者:" + cursor.getString(cursor.getColumnIndex("MSG_FROM")));
        tvTo.setText("接受者"+cursor.getString(cursor.getColumnIndex("MSG_TO")));
        tvImdnId.setText("消息ID："+ messageId);
        tvContent.setText("消息内容/文件路径:"+cursor.getString(cursor.getColumnIndex("CONTENT")));
        tvTime.setText("发送时间:" + new Date(cursor.getLong(cursor.getColumnIndex("RECEIVE_TIME"))).toString());
        tvProgress.setText("进度:"+ progress);
        tvStatus.setText("消息状态:"+cursor.getString(cursor.getColumnIndex("STATUS")));
        tvCtt.setText("消息类型:"+cursor.getString(cursor.getColumnIndex("CONTENT_TYPE")));
        tvTransfer.setText("TranferId:"+ transferId);

        int ctt = cursor.getInt(cursor.getColumnIndex("CONTENT_TYPE"));
        if(ctt == ContentType.TEXT.value()) {
            btnDownload.setVisibility(View.GONE);
        }else {
            btnDownload.setVisibility(View.VISIBLE);
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择文件路径：
                    //建议：在生产环境中使用其他路径
                    File dir = Environment.getExternalStorageDirectory();
                    String filePath = dir.getPath() +"/"+ messageId;

                    //刷新数据库中的文件路径:
                    ContentValues values = new ContentValues();
                    values.put("CONTENT", filePath);
                    context.getContentResolver().update(MessageContentProvider.CONTENT_URI, values, "IMDN_ID=?", new String[] {messageId});

                    //调用SDK开始下载
                    FetchFileMessageArg arg = new FetchFileMessageArg(messageId, transferId, filePath, progress, fileSize);
                    try {
                        RCSMessageManager.fetchFile(LoginState.getStartUser(context), arg, null);
                    } catch (Exception e) {
                        Log.e(TAG, "fetch file error", e);
                    }
                }
            });
        }
    }
}
