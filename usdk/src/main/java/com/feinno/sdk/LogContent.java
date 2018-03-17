package com.feinno.sdk;

import com.feinno.sdk.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

class LogContent {
	public String tag;
	public String content;

  public static LogContent fromJson(String json) throws JSONException{
    JSONObject obj =  JsonUtils.fromJson(json);
    LogContent log = null;
    if(obj != null) {
      log = new LogContent();
      log.tag = obj.getString("tag");
      log.content = obj.getString("content");
    }

    return log;
  }
}
