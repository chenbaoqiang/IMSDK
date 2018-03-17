package com.feinno.sdk;

import android.content.Context;

import com.feinno.sdk.utils.LogUtil;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileFilter;

class SdkUpdate {
    private static final String TAG = "SdkUpdate";

    //public static Object SYNCROOT = new Object();

    public synchronized static void check(Context ctx) {
        LogUtil.d(TAG, "do check");
        File dir = ctx.getFilesDir();
        File[] fs = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                LogUtil.d(TAG, "pathname:" + pathname);
                return pathname.getName().startsWith("libsdk") &&
                        pathname.isFile();
            }
        });

        String existPath = ctx.getFilesDir() + "/libsdk";
        String existBakPath = existPath + "-bak";

        LogUtil.d(TAG, existBakPath + ", " + existPath);

        if(fs !=null && fs.length > 0) {
            LogUtil.d(TAG, "update file found, try to update the sdk");
            try {
                LogUtil.d(TAG, "got zip file:" + fs[0].getPath().toString());
                ZipFile zf = new ZipFile(fs[0].getPath().toString());
                if(!zf.isValidZipFile()) {
                    LogUtil.e(TAG, "not a vaild zip file");
                }
                //TODO: check the file's MD5 hash


                LogUtil.d(TAG, "try backup the old libsdk");
                // move the old sdk dir
                File oldsdk = new File(existPath);
                File bak = new File(existBakPath);
                if(oldsdk.exists()) {
                    LogUtil.d(TAG, "do backup");
                    oldsdk.renameTo(bak);
                }

                LogUtil.d(TAG, "extract new sdk");
                //extract the new sdk
                zf.extractAll(ctx.getFilesDir().toString());

                //remove the bak
                LogUtil.d(TAG, "try to remove the backup file");
                bak = new File(existBakPath);
                if(bak.exists()) {
                    LogUtil.d(TAG, "remove bak");
                    deleteRecursive(bak);
                }

                //remove the update file
                LogUtil.d(TAG, "remove the update file");
                deleteRecursive(fs[0]);

                LogUtil.e(TAG, "sdk updated successfully!");
            }
             catch (ZipException e) {
                 LogUtil.e(TAG, e);

                 File bak = new File(existBakPath);
                 if(bak.exists()){
                     bak.renameTo(new File(existPath));
                 }
            }
        }

    }

    static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }
}
