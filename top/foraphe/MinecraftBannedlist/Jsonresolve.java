package top.foraphe.MinecraftBannedlist;

import org.json.*;

public class Jsonresolve{
	public static int getbanned(String json) {
		JSONObject cur = new JSONObject(json);
		boolean banned;
		try {
			JSONObject tmp = cur.getJSONObject("data");
			banned=tmp.getBoolean("banned");
		}
		catch(JSONException e) {
			throw(e);
		}
		if(banned==true)return 1;
		return 0;
	}
	public static String[] getUUID(String json,int count) {
		String[] ret= new String[count];
		JSONArray playerJson = null;
		try {
			playerJson = new JSONArray(json);
			for(int i=0;i<count;i++) {
				JSONObject cur = playerJson.getJSONObject(i);
				//System.out.println(cur.toString());
				ret[i] = cur.getString("uuid");
			}
		}
		catch(JSONException jse) {
			//TODO;
		}
		return ret;
	}
	public static String[] getID(String json,int count) {
		String[] ret= new String[count+1];
		JSONArray playerJson = null;
		try {
			playerJson = new JSONArray(json);
			for(int i=0;i<count;i++) {
				JSONObject cur = playerJson.getJSONObject(i);
				//System.out.println(cur.toString());
				ret[i] = cur.getString("playername");
			}
		}
		catch(JSONException jse) {
			//TODO;
		}
		return ret;
	}
	public static int totPlayerCount(String json) {
		JSONArray arr = null;
		try {
			arr = new JSONArray(json);
		}
		catch(JSONException jse) {
			//TODO;
		}
		int len=arr.length();
		return len;
	}
}
