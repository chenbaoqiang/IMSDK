package org.keplerproject.luajava;

import com.feinno.sdk.utils.LogUtil;

public class Helper {
	public static Boolean safeEvalLua(LuaState L, String src) {
		try {
			return evalLua(L, src);
		} catch(LuaException e) {
			e.printStackTrace();
		}
		return false;		
	}
	
	public static String errorReason(int error) {
		switch (error) {
		case 4:
			return "Out of memory";
		case 3:
			return "Syntax error";
		case 2:
			return "Runtime error";
		case 1:
			return "Yield error";
		}
		return "Unknown error " + error;
	}

	
	public static Boolean evalLua(LuaState L, String src) throws LuaException {
		int ok = L.LloadString(src);
		if (ok == 0) {
			L.getGlobal("debug");
			L.getField(-1, "traceback");
			L.remove(-2);
			L.insert(-2);
			ok = L.pcall(0, 0, -2);
			if (ok == 0) {				
				L.pop(1);
				return true;
			}
		}
		throw new LuaException(errorReason(ok) + ": " + L.toString(-1));
	}

	public static String evalLua(LuaState L, String moduleName, String funcName, Object... params) throws LuaException {
		L.getGlobal("debug");
		L.getField(-1, "traceback");
		L.remove(-2);

		L.getGlobal(moduleName);
		L.getField(-1, "call");
		L.remove(-2);

		// 参数 "funcname"
		L.pushObjectValue(funcName);
		LogUtil.d("JavaSDK", "param: funcname = " + funcName);
		// 其他参数
		for (Object o : params) {
			if (o != null) {
				LogUtil.d("JavaSDK", "param: " + o.toString());
			} else {
				LogUtil.d("JavaSDK", "param is null!");
			}
			L.pushObjectValue(o);
		}

		int paramsNum = 0;
		if (params != null) {
			paramsNum = params.length;
		}

		// params中不包含固定的"funcname"参数，所以总共的参数个数是 paramsNum+1。 另外，栈中还有函数{funcName}, 所以traceback函数的index为 -(paramsNum + 3)
		int ok = L.pcall(paramsNum + 1, 1, - (paramsNum + 3));
		if (ok == 0) {
			String ret = null;
			if(!L.isNil(-1)) { ret = L.toString(-1); }

			L.pop(L.getTop()); //清空栈

			LogUtil.d("JavaSDK", "ret = " + ret);
			return ret;
		}

		String error = L.toString(-1);
		L.pop(L.getTop()); //清空栈
		throw new LuaException(errorReason(ok) + ": " + error);
	}
	
	private static final String LUAJAVA_LUA_RET = "__luajava_lua_ret";
	public static String evalLuaFunc(LuaState L, String src) throws LuaException {
		String script = LUAJAVA_LUA_RET + " = " + src;
		evalLua(L, script);
		L.getGlobal(LUAJAVA_LUA_RET);
		String ret = null;
		if(!L.isNil(-1)) { ret = L.toString(-1); }
		L.pop(1);
		return ret;
	}

	public static String evalLuaFunc(LuaState L, String moduleName, String funcName, Object... params) throws LuaException {
		return evalLua(L, moduleName, funcName, params);
	}
}
