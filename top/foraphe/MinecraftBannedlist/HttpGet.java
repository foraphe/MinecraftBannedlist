package top.foraphe.MinecraftBannedlist;

import com.github.kevinsawicki.http.HttpRequest;

public class HttpGet{
	public static String get(String urlpre,String UUID,String urlsuf) {
		String resp=null;
		try {
			String realurl = urlpre+UUID+urlsuf;
			resp = HttpRequest.get(realurl).body();
		}
		catch(Exception e) {
			return "err";
		}
		return resp;
	}
	public static String getplayers(String url) {
		String resp=null;
		try {
			resp = HttpRequest.get(url).body();
		}
		catch(Exception e) {
			//TODO
		}
		return resp;
	}
}