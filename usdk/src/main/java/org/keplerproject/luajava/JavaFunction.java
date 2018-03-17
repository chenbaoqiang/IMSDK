/*
 * $Id: JavaFunction.java,v 1.6 2006/12/22 14:06:40 thiago Exp $
 * Copyright (C) 2003-2007 Kepler Project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.keplerproject.luajava;

/**
 * JavaFunction is a class that can be used to implement a Lua function in Java.
 * JavaFunction is an abstract class, so in order to use it you must extend this 
 * class and implement the <code>execute</code> method. This <code>execute</code> 
 * method is the method that will be called when you call the function from Lua.
 * To register the JavaFunction in Lua use the method <code>register(String name)</code>.
 */


/* LJ: 20140812
* This JavaFunction does not work well in coroutine, 
*  because that we cannot get the actual argument of the JavaFunction, what we got is just a lua thread.
*   So here comes a workaround:
*   
	  //  1. Assume that we need a JavaFunction called `jfunc` which has two arguments int and string
	  //  2. we set two global variables in the luastate, one is called `jfunc` another one is called __jfunc_obj
	  //  3. in lua, we call jfunc(a_number, a_string), this function do two things:
	  //	a) save `a_number`, `a_string` in a global table, called __luajava_args, after this, the table looks like :{a_number, a_string}
	  //	b) call `__jfunc_obj`
	  //  5. in `__jfunc_obj`,  it calls JavaFunction.execute method
	  //  6. in this method, we pop the arguments from __luajava_args, java world get the `a_number` and `a_string`, 
	  // 	after pop, the __luajava_args looks like: {}
	  //  7. the arguments got from `__luajava_args`, whose value could be saved in the JavaFunction class, and can be visited by the 
	  //	method `getParam`
*/	  

public abstract class JavaFunction
{
	public static final String TAG = "JavaFunction";
	public static String LUA_JAVA_ARGS = "__luajava_args";
	public static String LUA_JAVA_RET = "__luajava_ret";
	public static String LUA_JAVA_TMP = "__luajava_tmp";
	
	/**
	 * This is the state in which this function will exist.
	 */
	protected LuaState L;
	
	/**
	 * This method is called from Lua. Any parameters can be taken with
	 * <code>getParam</code>. A reference to the JavaFunctionWrapper itself is
	 * always the first parameter received. Values passed back as results
	 * of the function must be pushed onto the stack.
	 * @return The number of values pushed onto the stack.
	 */
	public abstract int execute() throws LuaException;
	
	/**
	 * Constructor that receives a LuaState.
	 * @param L LuaState object associated with this JavaFunction object
	 */
	public JavaFunction(LuaState L)
	{
		this.L = L;
	}

	public String params()
	{
		L.getGlobal(LUA_JAVA_ARGS); // {{...}} 
		L.rawGetI(-1, 1);  // {...}
		L.rawGetI(-1, 1);  //  ...
		String ret = null;
		if(!L.isNil(-1)) { ret = L.toString(-1); }
		L.pop(3);
		return ret;
	}
	
	//return stack idx to the lua world
	public void _return(){
		L.setGlobal(LUA_JAVA_TMP);
		Helper.safeEvalLua(this.L, "table.insert(" + LUA_JAVA_RET + ", 1,"  + LUA_JAVA_TMP + ")");
	}
	
	public void _return(int i) {
		L.pushNumber((double)i);
		L.setGlobal(LUA_JAVA_TMP);
		Helper.safeEvalLua(this.L, "table.insert(" + LUA_JAVA_RET + ", 1,"  + LUA_JAVA_TMP + ")");
	}

    public void _return(String str) {
        L.pushString(str);
        L.setGlobal(LUA_JAVA_TMP);
        Helper.safeEvalLua(this.L, "table.insert(" + LUA_JAVA_RET + ", 1,"  + LUA_JAVA_TMP + ")");
    }

	/**
	 * Register a JavaFunction with a given name. This method registers in a
	 * global variable the JavaFunction specified.  
	 * 
	 * @param name name of the function.
	 */
	public void register(String name) throws LuaException
	{
	  synchronized (L)
	  {
		  	String lua = "function javafunc(...)   \r\n";
		  	lua += 		 "   local oldargsn = #__luajava_args \r\n";
		  	lua += 		 "   local oldretn = #__luajava_ret \r\n";
		  	lua += 		 "   table.insert(__luajava_args, 1, {...}) \r\n";
		  	lua += 		 "   __luajava_func_javafunc() \r\n";
		  	lua += 		 "   if oldargsn < #__luajava_args then table.remove(__luajava_args, 1) end \r\n";
		  	lua += 		 "   if oldretn >= #__luajava_ret then return nil else \r\nreturn table.remove(__luajava_ret, 1) end	  \r\n";
		  	lua += 		 "end	  \r\n";
		  	
		  	lua = lua.replace("__luajava_func_javafunc", "__luajava_func_" +  name.replace(".", "_"));
		  	lua = lua.replaceAll("javafunc", name);
		  	Helper.evalLua(this.L, lua);
		  	String n = "__luajava_func_" +name.replace(".", "_");
			L.pushJavaFunction(this);
			L.setGlobal(n);

	  }
	}
}
