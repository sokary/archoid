package cn.sokary.archs.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class AppNetwork {

    public static String getData(String path){

        HttpURLConnection conn = null;
        URL url;
        String result="no";
        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("POST");
            if (conn.getResponseCode() == 200) {
                result = new String(StreamTool.read(conn.getInputStream()));
            }

        } catch (Exception e) {
            result="no";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static String getData(Uri uri){

        HttpURLConnection conn = null;
        URL url;
        String result="no";
        try {
            url = new URL(uri.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            if (conn.getResponseCode() == 200) {
                result = new String(StreamTool.read(conn.getInputStream()));
            }else{
                throw new Exception("no");
            }

        } catch (Exception e) {
            result="stop";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static String postData(Uri uri, String data){
        HttpURLConnection conn = null;
        URL url;
        String result="connectError";
        try {
            url = new URL(uri.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.connect();
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(data);
            osw.flush();
            osw.close();

            result = new String(StreamTool.read(conn.getInputStream()));

        } catch (Exception e) {
            result = "ConnectionError";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static boolean postImage(Uri uri, String imagePath, boolean isImageDelete){

        boolean result = false;
        HttpURLConnection conn=null;
        byte[] buf=new byte[1024];

        URL url;
        InputStream in;

        try {
            url = new URL(uri.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            if(isImageDelete==false){
                in =new FileInputStream((new File( imagePath )));
                DataOutputStream osw = new DataOutputStream(conn.getOutputStream());
                while( (in.read(buf)) > 0){
                    osw.write(buf);
                }
                in.close();
                osw.flush();
                osw.close();
            }

            int responseCode=conn.getResponseCode();
            if (responseCode == 200) {
                result=true;
            }else{
                throw new Exception( String.valueOf(responseCode) + "-----"+ ( new String(StreamTool.read(conn.getInputStream())) )  );
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static boolean isOnline(Context context) {
        boolean result = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        result = netInfo != null && netInfo.isConnectedOrConnecting();
        return result;
    }

    public static String getMacAddress(Context context){
        try{
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());
            String stringMac = "";
            for(NetworkInterface networkInterface : networkInterfaceList)
            {
                if(networkInterface.getName().equalsIgnoreCase("wlon0"));
                {
                    for(int i = 0 ;i <networkInterface.getHardwareAddress().length; i++){
                        String stringMacByte = Integer.toHexString(networkInterface.getHardwareAddress()[i]& 0xFF);
                        if(stringMacByte.length() == 1)
                        {
                            stringMacByte = "0" +stringMacByte;
                        }
                        stringMac = stringMac + stringMacByte.toUpperCase(Locale.getDefault()) + ":";
                    }
                    break;
                }
            }
            return stringMac;
        }catch (SocketException e)
        {
            e.printStackTrace();
        }
        return  "0";
    }

//    public static String getMacAddress(){
//        WifiManager manager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
//        WifiInfo info = manager.getConnectionInfo();
//        return info.getMacAddress();
//    }

    public static class StreamTool {

        public static byte[] read(InputStream inStream) throws Exception {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            return outStream.toByteArray();
        }

        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

    }

}
