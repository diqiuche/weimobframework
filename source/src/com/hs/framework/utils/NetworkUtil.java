package com.hs.framework.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.hs.framework.common.NetStatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午2:11:16
 * @email hunter.v.wang@gmail.com
 *
 */
public class NetworkUtil {
	
	/**
	 * get net working state WIFI or MOBLIE
	 * @param context
	 * @return
	 */
	public static NetStatus getNetState(Context context){
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable() && (info.getType() == ConnectivityManager.TYPE_WIFI)) {
			return NetStatus.WIFI;
		} 
		if (info != null && info.isAvailable() && (info.getType() == ConnectivityManager.TYPE_MOBILE)) {
			return NetStatus.MOBILE;
		} 
		return NetStatus.ERROR;
	}
	
	/**
	 * get net status : online or offline
	 * @param context
	 * @return
	 */
	public static boolean isNetWorking(Context context){
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} 
		return false;
	}

	/**
	 * get device ip address 
	 * @param context
	 * @return
	 */
	public static String getIp(Context context)
	{
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			if(info.getType() == ConnectivityManager.TYPE_MOBILE)return mobileIp();
			if(info.getType() == ConnectivityManager.TYPE_WIFI)return wifiIp(context);
		} 
		return null;
	}
	
	/**
	 * when device is working on MOBILE
	 * @return
	 */
	public static String mobileIp()
	{
		 try
		 {  
	            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)  
	            {  
	               NetworkInterface intf = en.nextElement();  
	               for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)  
	               {  
	                   InetAddress inetAddress = enumIpAddr.nextElement();  
	                   if (!inetAddress.isLoopbackAddress())  
	                   {  
	                       return inetAddress.getHostAddress().toString();  
	                   }  
	               }  
	           }  
	        }  
	        catch (SocketException ex)  
	        {  
	        }  
		 return null;
	}
	
	/**
	 * when device is working on WIFI
	 * @param context
	 * @return
	 */
	public static String wifiIp(Context context)
	{
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
        int i = wifiInfo.getIpAddress();   
        return (i & 0xFF ) + "." +  ((i >> 8 ) & 0xFF) + "." +  ((i >> 16 ) & 0xFF) + "." + ( i >> 24 & 0xFF);
	}

}
