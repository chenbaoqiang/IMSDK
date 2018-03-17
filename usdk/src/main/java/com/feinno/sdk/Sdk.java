package com.feinno.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;

import com.feinno.sdk.api.AbstractSdkState;
import com.feinno.sdk.session.MessageSession;
import com.feinno.sdk.utils.JsonUtils;
import com.feinno.sdk.utils.LogUtil;

import org.json.JSONObject;
import org.keplerproject.luajava.Helper;
import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Sdk {
    protected final static String TAG = "JavaSDK";

	private static String[] LUA_DIRECTORIES = {"libs", "sip", "sdk", "msrp", "mqtt"};

	public static final String LOG_MODULE_NAME = "nlog";
	public static final String NOTIFICATION_MODULE_NAME = "notification";
	public static final String CALLBACK_MODULE_NAME = "callback";
	public static final String LISTENER_MODULE_NAME = "listeners";

    private static final String JAVA_FUNCTION_SDK_STATE_CHANGE = "sdkstate";
    private static final String JAVA_FUNCTION_CONNECTION_STATE_CHANGE = "connectionstate";

    private static int currentPortNumber;
    private static WeakReference<HashMap<String, byte[]>> zipFileLoadCache = null;

    private Sdk() {}

	static {
		System.loadLibrary("luajava");
	}

    /**
     * 使用默认配置添加一个SDK用户，被叫收到的服务器事件通过广播发送
     * @param context 上下文，参见 android.content.Context
     * @return 一个SdkState对象
     */
    public static SdkState newSdkState(Context context) {
        LogUtil.i(TAG, "newSdkState with context");
        return newSdkState(context, null, null);
    }

    /**
     * 添加一个SDK用户
     * @param context 上下文，参见 android.content.Context
     * @param cp 被叫所有的业务逻辑处理入口
     * @return 一个SdkState对象
     */
    public static SdkState newSdkState(Context context, IListenerProvider cp) {
        LogUtil.i(TAG, "newSdkState with context and cp");
        return newSdkState(context, null, cp);
    }

    /**
     * 添加一个SDK用户
     * @param context 上下文，参见 android.content.Context
     * @param conf SDK配置信息
     * @param cp 被叫所有的业务逻辑处理入口
     * @return 一个SdkState对象
     */
    public static SdkState newSdkState(Context context, SdkConf conf, IListenerProvider cp) {
        LogUtil.i(TAG, "newSdkState with context, conf and cp");
        SdkState sdkState = new SdkState(context, conf, cp);
        try {
            sdkState.init();
        } catch (LuaException e) {
            LogUtil.e(TAG, e);
        }

        return sdkState;
    }

	private static byte[] readAll(InputStream input) throws Exception {
        return readAll(input, true);
    }
    private static byte[] readAll(InputStream input, boolean autoClose) throws Exception {
          ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
        if(autoClose) {
            input.close();
        }
		return output.toByteArray();
	}

	private static boolean tryloadlua(LuaState L, AssetManager am, String name, Context ctx){
		try {
            if(am == null){
                File f = new File(ctx.getFilesDir() + "/"  + name);
                LogUtil.d(TAG, "try to open file:" + f.getPath().toString());
                if(f.exists()) {
                    FileInputStream fis = new FileInputStream(f);
                    byte[] bytes = readAll(fis);
                    L.LloadBuffer(bytes, name);
                    LogUtil.d(TAG, "loaded lib from file dir:" + name);
                    return true;
                }
                return false;
            } else if(tryloadZipfile(L, am, name)) {
                LogUtil.d(TAG, "load lib from zipfile:" + name);
                return true;
            }else{
                InputStream is = am.open(name);
                byte[] bytes = readAll(is);
                L.LloadBuffer(bytes, name);
                LogUtil.d(TAG, "loaded libs:" + name);
                return true;
            }
		} catch(Exception e1) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			e1.printStackTrace(new PrintStream(os));
			L.pushString("Cannot load module " + name + ":\n" + os.toString());
			return false;
		}
	}
    private static boolean tryloadZipfile(LuaState L, AssetManager am, String name) {
        try {

            HashMap<String, byte[]> cache = zipFileLoadCache == null ? null : zipFileLoadCache.get();
            if(cache == null){
                java.util.zip.ZipInputStream zipIs = new java.util.zip.ZipInputStream(am.open("libsdk.zip"));
                LogUtil.d(TAG, "load libzip file begin");
                cache = new HashMap<>();
                java.util.zip.ZipEntry ze = null;

                while ((ze = zipIs.getNextEntry()) != null) {
                    byte[] bytes = readAll(zipIs, false);
                    zipIs.closeEntry();
                    cache.put(ze.getName(), bytes);
                }
                zipIs.close();
                LogUtil.d(TAG, "load libzip file end");
                zipFileLoadCache = new WeakReference<>(cache);
            }

            byte[] bytes = cache.get(name);
            if(bytes != null) {
                L.LloadBuffer(bytes, name);
                return true;
            }else{
                return false;
            }
        }catch (IOException ex){
            return false;
        }catch (Exception ex){
            LogUtil.e(TAG, "tryloadZipfile", ex);
            return false;
        }
    }

    private interface ILogFunction {
        void log(String tag, String val);
    }

    protected static final class LogJavaFunction extends JavaFunction {
		private String mName;
		private ILogFunction fn;

		public LogJavaFunction(LuaState L) {
			super(L);
		}

		public LogJavaFunction(LuaState L, String name, ILogFunction fn) {
			super(L);
			this.fn = fn;
			this.mName = name;
		}

		@Override
		public int execute() throws LuaException {
			try {
				String json = params();
				LogContent log = LogContent.fromJson(json);

				if(log != null) {
					String tag = "";
					String content  = "";
					if(!TextUtils.isEmpty(log.tag)) {
						tag = log.tag;
					}
					if(!TextUtils.isEmpty(log.content)) {
						content = log.content;
					}

					fn.log(tag, content);
				}
			} catch(Exception ex) {
				LogUtil.e(TAG, "NLOG ERROR", ex);
			}

			return 0;
		}

		public boolean regiser() throws LuaException {
			if (TextUtils.isEmpty(mName)) {
				return false;
			}

			register(LOG_MODULE_NAME + "." + this.mName);
			return true;
		}
	}

    /**
     * 此类中保存了SDK运行过程中的所有状态。在多用户情况下，每个用户都应对应一个SdkState对象。
     */
    public static final class SdkState extends AbstractSdkState {
        private LuaState L;
        private StateId stateId = null;
        private Context mContext;
        private CallbackManager cbManager;
        private ListenerManager lsManager;
        private IListenerProvider cbProvider;
        private SdkConf conf;
        private SdkAlarm sdkAlarm;
        private Thread sdkThread;
        private ConcurrentLinkedQueue<SdkTask> taskQueue = new ConcurrentLinkedQueue<SdkTask>();
        protected ConcurrentHashMap<Integer, Object> callbacks = new ConcurrentHashMap<Integer, Object>();
        protected ConcurrentHashMap<String, DeliveredCallbackEntry> deliveredCallbacks
                = new ConcurrentHashMap<String, DeliveredCallbackEntry>();

        //protected MessageCBDispatcher cbDispatcher;

		protected boolean connected = false;
		//protected int login = LoginStates.DEREGISTERED.value();

        private LuaStepInterruptor luaStepInterruptor;
        private long mStepCount = 0;

        protected SdkState(Context context, SdkConf conf, IListenerProvider cp) {
            LogUtil.i(TAG, "new SdkState");

            mContext = context;
            cbProvider = cp;
            L = LuaStateFactory.newLuaState();
            stateId = new StateId(mContext);
            this.conf = conf;
            //cbDispatcher = new MessageCBDispatcher(context, this);

            SdkUpdate.check(context);
        }

        protected void init() throws LuaException {
            LogUtil.i(TAG, "SdkState init");
            L.openLibs();
            JavaFunction assetLoader = registerMyLoader();
            registerNewStateId();
            setupLoaders(assetLoader);

            Helper.evalLua(L, LOG_MODULE_NAME + " = " + LOG_MODULE_NAME + " or {}");
            Helper.evalLua(L, NOTIFICATION_MODULE_NAME + " = " + NOTIFICATION_MODULE_NAME + " or {}");
            registerLogFunctions();
			registerNotificationFunctions();

            sdkAlarm = new SdkAlarm(SdkState.this, mContext);
            sdkAlarm.registerFunctions();

            //init the global callback:
            Helper.evalLua(L, CALLBACK_MODULE_NAME + "={}");
            Helper.evalLua(L, LISTENER_MODULE_NAME + "={}");
            Helper.evalLua(L, "require'sdk_main'");

            cbManager = new CallbackManager(SdkState.this, mContext);
            lsManager = new ListenerManager(SdkState.this, mContext, cbProvider);

            cbManager.registerCallbacks();
            lsManager.setAllListener();

            String outputPath = conf.getStorage();
            if (TextUtils.isEmpty(outputPath)) {
                outputPath = getOutputFilePath();
            }

            String sysPath = conf.getSysPath();
            if (TextUtils.isEmpty(sysPath)) {
                sysPath = getOutputFilePath();
            }
            SdkApi.init(this, conf.getImei(), conf.getImsi(), conf.getTerminalVendor(), conf.getTerminalMode(),
                    "Android", conf.getTerminalSwVersion(), conf.getClientVendor(), conf.getClientVersion(),
                    outputPath,sysPath,conf.getAppId(),conf.getNumber());

            try {
                luaStepInterruptor = new LuaStepInterruptor(this);
                luaStepInterruptor.Init();
            } catch (Exception e) {
                LogUtil.e(TAG, e);
            }

            // TODO: read from conf
//            if (conf != null) {
//                String storage = conf.getStorage();
//                if (TextUtils.isEmpty(storage)) {
//                    storage = getOutputFilePath() + File.separator + "%s";
//                }
//                SdkApi.setConf(this, null, 0, null, storage, null, null, null, null);
//            } else {
                String storage = getOutputFilePath() + File.separator + "%s";
                SdkApi.setConf(this, null, 0, null, storage, null, null, null, null);
//            }

            sdkThread = new Thread(bgFunc);
            sdkThread.setName("Sdk-Core");
            sdkThread.setPriority(Thread.MAX_PRIORITY);
            sdkThread.setDaemon(true);
            sdkThread.start();
        }

        @Override
        protected LuaState getLuaState() {
            return L;
        }

        /**
         * 在处理具体业务逻辑之前需调用此方法
         * @throws {@link com.feinno.sdk.SdkException}
         */
        @Override
        public void start() throws SdkException {
            LogUtil.d(TAG, "sdk state start");
            clearThread.start();

            try {
                SdkApi.start(this, false);
            } catch (Exception e) {
                LogUtil.e(TAG, e);
                throw new SdkException(e);
            }
        }

        static String getOutputFilePath() throws SdkException {
			try {
				if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
					File sdcard = Environment.getExternalStorageDirectory();
					if (sdcard == null) {
						// TODO: get another directory
						throw new SdkException("There is no external storage in this phone");
					}
					File storageDirectory = new File(sdcard, "URCS");
					if (!storageDirectory.exists()) {
						if (!storageDirectory.mkdir()) {
							throw new SdkException("failed to create directory");
						}
					}
					return storageDirectory.getAbsolutePath();
				} else {
					throw new SdkException("External storage is not mounted READ/WRITE.");
				}
			} catch (Exception e) {
				throw new SdkException(e);
			}
        }

        public void startNrepl(int port)throws SdkException {
		    if(port <= 0){
			    port = 8080;
		    }
		    try {
			    SdkApi.startnrepl(this, port);
		    } catch (Exception e){
			    LogUtil.e(TAG, e);
		    }
	    }

        public SdkConf getConf() {
            return conf;
        }

		public boolean isConnected() {
			return connected;
		}

//		public int getLoginStatus() {
//			return login;
//		}

        private Runnable bgFunc = new Runnable() {
            @Override
            public void run() {
                long errors = 0;
                while(true) {
                    try {
                        if(errors > 20){
                          // 无网络情况下，下面step可能会select失败 Exception(具体场景未能确认复现)
                          // 大量异常，耗电, 暂先控制一下
                          Thread.sleep(2000);
                        }

                        tryTrimMemory();

                        if (!luaStepInterruptor.isconnected()) {
                          SdkApi.step(SdkState.this, 1);
                          Thread.sleep(50);
                        }else {
                          SdkApi.step(SdkState.this, 5 * 60);
                        }
                        mStepCount ++;


                        while(true) {
                            SdkTask st = taskQueue.poll();
                            if(st != null) {
                                LogUtil.d(TAG, "get task from queue("+ taskQueue.hashCode() + "):" + st.s);
                                st.run();
                            } else {
                                break;
                            }
                        }
                      errors = 0;
                        // afterStep();
                    } catch (LuaException e) {
                        errors ++;
                        LogUtil.e(TAG, e);
                    } catch (InterruptedException e) {
                        LogUtil.e(TAG, e);
                    }
                }
            }
        };

        private long mLastJavaForceGCTime = 0;
        private long mLastJavaFoceGCStepCount = 0;
        private long mLastLuaForceGCime = 0;
        private long mLastLuaFoceGCStepCount = 0;
        private long mJavaMinMemUsage = 0;

        private void tryTrimMemory() throws LuaException {
            if(mStepCount % 10 != 0) {
                return;
            }
            long now = System.currentTimeMillis();
            //max 1min/GC && 50step/GC
            if(now - mLastLuaForceGCime >= 60*1000 && mStepCount - mLastLuaFoceGCStepCount >= 50 ) {
                mLastLuaForceGCime = now;
                mLastLuaFoceGCStepCount = mStepCount;
                LogUtil.d(TAG, "lua start gc..");
                Helper.evalLua(L, "collectgarbage()");
                LogUtil.d(TAG, "lua gc finished..");
            }

            int cmem = getMemUsage();
            if(mJavaMinMemUsage == 0){
                mJavaMinMemUsage = cmem;
            }
            //max 1min/GC && (inc 3M || 100step/GC)
            mJavaMinMemUsage = Math.min(mJavaMinMemUsage, cmem);
            if(now - mLastJavaForceGCTime >= 60*1000 &&
                    (cmem - mJavaMinMemUsage >= 3 || mStepCount - mLastJavaFoceGCStepCount >= 100)) {
                mLastJavaForceGCTime = now;
                mLastJavaFoceGCStepCount = mStepCount;
                LogUtil.d(TAG, "java start gc..");
                System.runFinalization();
                System.gc();
                LogUtil.d(TAG, "java gc finished..");
            }
        }

        private int getMemUsage(){
            Runtime runtime = Runtime.getRuntime();
            return (int)((runtime.totalMemory() - runtime.freeMemory()) / 1048576L);
        }

        protected void addTask(SdkTask t) {
            if(t == null) {
                return;
            }

            taskQueue.add(t);

            if(t.cookie != null) {
                callbacks.put(t.id, t.cookie);
            }
            luaStepInterruptor.Singal(isSdkThread());
        }

        protected String runTask(SdkTask t) throws SdkException {
            return runTask(t, 1000);
        }

        protected String runTask(SdkTask t, long timeout) throws SdkException {
            if(isSdkThread()){
                throw new SdkException("runTask in SdkCore Thread forbidden");
            }

            addTask(t);

            synchronized (t){
                if(!t.isCompleted){
                    try {
                        t.wait(timeout);
                        if(!t.isCompleted)
                            throw new SdkException("runTask Timeout");
                    } catch(InterruptedException ex) {
                        throw new SdkException(ex);
                    }
                }
            }

            return t.result;
        }

        private boolean isSdkThread() {
            return sdkThread != null && sdkThread.getId() == Thread.currentThread().getId();
        }

        private void registerLogFunctions() throws LuaException {
            LogJavaFunction function;

            Helper.evalLua(L, LOG_MODULE_NAME + ".level=" + LogUtil.getLogLevel());
            if(LogUtil.getLogLevel() <= 1) {
                function = new LogJavaFunction(L, "v", new ILogFunction() {
                    @Override
                    public void log(String tag, String val) {
                        LogUtil.v(tag, val);
                    }
                });
                function.regiser();
            }

            if(LogUtil.getLogLevel() <= 2) {
                function = new LogJavaFunction(L, "d", new ILogFunction() {
                    @Override
                    public void log(String tag, String val) {
                        LogUtil.i(tag, val);
                    }
                });
                function.regiser();
            }

            if(LogUtil.getLogLevel() <= 3) {
                function = new LogJavaFunction(L, "i", new ILogFunction() {
                    @Override
                    public void log(String tag, String val) {
                        LogUtil.i(tag, val);
                    }
                });
                function.regiser();
            }

            if(LogUtil.getLogLevel() <= 4) {
                function = new LogJavaFunction(L, "w", new ILogFunction() {
                    @Override
                    public void log(String tag, String val) {
                        LogUtil.w(tag, val);
                    }
                });
                function.regiser();
            }

            if(LogUtil.getLogLevel() <= 5) {
                function = new LogJavaFunction(L, "e", new ILogFunction() {
                    @Override
                    public void log(String tag, String val) {
                        LogUtil.e(tag, val);
                    }
                });
                function.regiser();
            }
        }

        private JavaFunction registerMyLoader() throws LuaException {
            JavaFunction assetLoader = new JavaFunction(L) {
                @Override
                public int execute() throws LuaException {
                    String name = this.params();
                    LogUtil.d(TAG, "in myloader execute(), the params():" + name);
                    if(name == null || name.length() <= 0) {
                        return 0;
                    }

                    try {
                        AssetManager am = mContext.getAssets();
                        for (String d : LUA_DIRECTORIES) {

                            if(tryloadlua(L, null,
                                    "libsdk/" + d + "/" + name.replaceAll("\\.", "/") + ".o",
                                    mContext)) {
                                this._return();
                                return 0;
                            }

                            if (tryloadlua(L, am,
                                    "libsdk/" + d + "/" + name.replaceAll("\\.", "/") + ".o",
                                    mContext)) {
                                this._return();
                                return 0;
                            }

                            if (tryloadlua(L, am, d + "/" + name.replaceAll("\\.", "/") + ".lua",
                                    mContext)) {
                                this._return();
                                return 0;
                            }

                        }
                        return 0;
                    } catch (Exception ex) {
                        LogUtil.e(TAG, "load file error", ex);
                    }

                    return 0;
                }
            };
            assetLoader.register("_myloader");
            return assetLoader;
        }

        private void setupLoaders(JavaFunction assetLoader)
                throws LuaException {
            L.getGlobal("package");            			// package
            LogUtil.d(TAG, L.getTop() + "");
            LogUtil.i(TAG, L.typeName(L.type(-1)));

            L.getField(-1, "cpath");            		// package cpath
            //TODO: is this absolute path safe?
            String customCPath = "/data/data/" + mContext.getPackageName() +"/lib/lib?.so";
            L.pushString(";" + customCPath);    		// package cpath custom
            L.concat(2);                       			// package cpathCustom
            L.setField(-2, "cpath");            		// package
            L.pop(1);
            LogUtil.i(TAG, "insertJavaFunctionLoader");
            Helper.evalLua(L, "table.insert(package.loaders, 3, _myloader)");
        }

        private void registerNewStateId() throws LuaException {
            JavaFunction ns = new JavaFunction(L) {
                @Override
                public int execute() throws LuaException {
                    _return(newStateId());
                    return 0;
                }
            };
            ns.register("newStateId");
        }

        protected int newStateId() {
            return stateId.getId();
        }

		private void registerNotificationFunctions() throws LuaException {
			JavaFunction sdkState = new JavaFunction(L) {
				@Override
				public int execute() throws LuaException {
					String params = params();
					try {
						int state = Integer.parseInt(params);
						if (state > 0) {
							notifySdkStateChange(state);
						}
					} catch (Exception e) {
						LogUtil.e(TAG, e);
					}
					return 0;
				}
			};
			sdkState.register(NOTIFICATION_MODULE_NAME + "." + JAVA_FUNCTION_SDK_STATE_CHANGE);

			JavaFunction connectionState = new JavaFunction(L) {
				@Override
				public int execute() throws LuaException {
					String params = params();
					try {
						int state = Integer.parseInt(params);
						if (state > 0) {
							connected = state == StateManager.CONNECTION_STATE_CONNECTED;
							notifyConnectionStateChange(state);
						}
					} catch (Exception e) {
						LogUtil.e(TAG, e);
					}
					return 0;
				}
			};
			connectionState.register(NOTIFICATION_MODULE_NAME + "." + JAVA_FUNCTION_CONNECTION_STATE_CHANGE);
		}

		protected void notifySdkStateChange(int state) {
			LogUtil.i(TAG, "notifySdkStateChange state = " + state);
			Intent intent = new Intent(StateManager.ACTION_SDK_STATE);
			Bundle bundle = new Bundle();
			bundle.putInt(StateManager.EXTRA_SDK_STATE, state);
			intent.putExtras(bundle);
			mContext.sendBroadcast(intent);
		}

		protected void notifyConnectionStateChange(int state) {
			Intent intent = new Intent(StateManager.ACTION_CONNECTION_STATE);
			Bundle bundle = new Bundle();
			bundle.putInt(StateManager.EXTRA_CONNECTION_STATE, state);
			intent.putExtras(bundle);
			mContext.sendBroadcast(intent);
		}

		protected void notifyLoginStateChange(int state) {
			Intent intent = new Intent(StateManager.ACTION_USER_STATE);
			Bundle bundle = new Bundle();
			bundle.putInt(StateManager.EXTRA_USER_STATE, state);
			if (conf != null) {
				bundle.putString(StateManager.EXTRA_USER_NUMBER, conf.getNumber());
			}
			intent.putExtras(bundle);
			mContext.sendBroadcast(intent);
		}

        public void stop() {
            if(sdkAlarm != null) {
                sdkAlarm.stop();
            }

            clearThreadRunning = false;
            deliveredCallbacks.clear();
            // cbDispatcher.clearQueue();
            callbacks.clear();
        }

        public void putDeliveredCallback(String id, Callback callback) {
//            if (TextUtils.isEmpty(id) || callback == null) {
//                return;
//            }
//
//            deliveredCallbacks.put(id, new DeliveredCallbackEntry(callback));
        }

        private boolean clearThreadRunning = true;
        private Thread clearThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(clearThreadRunning) {
                    try {
                        Thread.sleep(1000 * 60);
                        cleanseCallback(1000 * 60 * 10);
                    } catch (InterruptedException e) {
                        LogUtil.e(TAG, e);
                    }
                }
            }
        });

        private void cleanseCallback(long limit) {
            ConcurrentHashMap<String, DeliveredCallbackEntry> map = new ConcurrentHashMap<>();
            Set<ConcurrentHashMap.Entry<String, DeliveredCallbackEntry>> set = deliveredCallbacks.entrySet();
            long now = System.currentTimeMillis();

            for (ConcurrentHashMap.Entry<String, DeliveredCallbackEntry> entry : set) {
                if (now - entry.getValue().time < limit) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }

            deliveredCallbacks.clear();
            deliveredCallbacks = map;
        }

//        private void afterStep() {
//            cbDispatcher.checkTimeout();
//        }

//        public void onRegisterStateChange(LoginSession session) {
//            try {
//                LogUtil.i(TAG, "on registered state changed, state:" + session.code);
//                if (session.code == LoginStates.LoginSuccess.value()) {
//                    cbDispatcher.sendPendingQueue();
//                }
//            }
//            catch (Exception ex) {
//                LogUtil.e(TAG, ex);
//            }
//        }

    }

    public static class DeliveredCallbackEntry {
        public Callback<MessageSession> callback;
        public long time;

        public DeliveredCallbackEntry(Callback<MessageSession> callback) {
            this.callback = callback;
            this.time = System.currentTimeMillis();
        }
    }



  public static class LuaStepInterruptor {
    private volatile LocalSocket udsServer = null;
    private volatile LocalSocket udsClient = null;
    private final String uds_path = "LuaInterruptor_"
        + Integer.toString((new Random(System.currentTimeMillis())).nextInt(1000000));

    private volatile Socket tcpClient = null;
    private int tcpPort = 8091;

    private Thread signalThread = null;
    private Handler signalHandler = null;
    private final byte[] hello = "hello\r\n".getBytes();
    private SdkState sdkState = null;

    public LuaStepInterruptor(SdkState state) {
        this.sdkState = state;
    }

    public void Init() throws Exception {
      //port不能使用重复
      if (currentPortNumber > 0)  {
          tcpPort = currentPortNumber + 1;
          currentPortNumber = tcpPort;
      } else {
          currentPortNumber = tcpPort;
      }
      listen(sdkState);

      // 只的init的时候建立连接，认为进程内socket不会断开，不做重连
      // 对于可能不适配设备，连接失败回退到 1s 的timeout
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            connect();
          }catch(Exception ex) {
            LogUtil.e(TAG, ex.toString());
          }
        }
      });
      thread.start();

      int wait = 0;
      while(!isconnected()) {
        if(++wait > 10)
          break;
        SdkApi.step(sdkState, 0.1);
      }

      if(!isconnected()){
        LogUtil.e(TAG, "interruptor connect maybe failed!!");
      } else {
        LogUtil.e(TAG, "interruptor connect ok!");
      }

      HandlerThread th = new HandlerThread("Signal Thread");
      th.setPriority(Thread.MAX_PRIORITY);
      th.start();
      signalHandler = new Handler(th.getLooper()){
          public void handleMessage(Message msg) {
              doSignal();
          }
      };
    }

    private void listen(SdkState state) throws Exception {
      int fd = 0;
      if(Build.VERSION.SDK_INT >= 19) {
        fd = uds_bind(state);
      }

      SdkApi.startInterruptor(state, tcpPort, fd);
    }

    @TargetApi(19)
    private int uds_bind(SdkState state) throws Exception {
      Field field = FileDescriptor.class.getDeclaredField("descriptor");
      field.setAccessible(true);
      udsServer  = new LocalSocket(LocalSocket.SOCKET_STREAM);
      LocalSocketAddress addr = new LocalSocketAddress(uds_path);
      udsServer.bind(addr);
      return field.getInt(udsServer.getFileDescriptor());
    }

    private void Singal(Boolean async){
      if(signalHandler == null){
        LogUtil.e(TAG, "Singal failed, LuaState not init!!");
      }else {
       // write fd时可能 block, 可能造成 SdkThread 线程将死锁
       // 1. SdkThread 执行时, 异步完成通知
       // 2. 其他线程直接执行，减少线程同步延时; 也不用担心Block:
       // 首先是有write buffer，再者如果一直block说明整个LuaState已经挂了
       if(async)
           signalHandler.sendEmptyMessage(0);
       else
           doSignal();
      }
    }

    private boolean doSignal(){
      if(Build.VERSION.SDK_INT >= 19 && udsClient != null){
        return doSingal19();
      }else if(Build.VERSION.SDK_INT < 19 && tcpClient != null){
        try {
          OutputStream os =  tcpClient.getOutputStream();
          os.write(hello);
          return true;
        }catch (Exception ex) {
          LogUtil.e(TAG, ex);
          tcpClient = null;
        }
      }else {
        LogUtil.i(TAG, "interruptor client not connected");
      }
      return false;
    }

    @TargetApi(19)
    private boolean doSingal19(){
      try {
        OutputStream os = udsClient.getOutputStream();
        os.write(hello);
        return true;
      } catch(Exception ex) {
        LogUtil.e(TAG, ex);
        udsClient = null;
      }
      return false;
    }

    private void connect() throws Exception {
      if(Build.VERSION.SDK_INT >= 19) {
        connect19();
      } else {
        Socket c = new Socket();
        c.connect(new InetSocketAddress("127.0.0.1", tcpPort));
        tcpClient = c;
      }
    }

    @TargetApi(19)
    private void connect19() throws Exception{
      LocalSocket c = new LocalSocket(LocalSocket.SOCKET_STREAM);
      LocalSocketAddress addr = new LocalSocketAddress(uds_path);
      c.connect(addr);
      udsClient = c;
    }

    public boolean isconnected() {
      if(Build.VERSION.SDK_INT >= 19) {
        return isconnected19();
      }
      else {
        return tcpClient != null;
      }
    }

    @TargetApi(19)
    private boolean isconnected19() {
      return udsClient != null;
    }
  }

    private static final int WRITE_MODE = Context.MODE_MULTI_PROCESS;
    private static final String FILE_NAME = "ngcc";
}
