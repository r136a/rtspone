package com.example.rtpsone;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.rtp.AudioCodec;
import android.net.rtp.AudioGroup;
import android.net.rtp.AudioStream;
import android.net.rtp.RtpStream;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class MainActivity extends Activity {

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
  audStream();

 }
public static byte[] getLocalIPAddress () {
    byte ip[]=null;
       try {
           for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
               NetworkInterface intf = (NetworkInterface) en.nextElement();
               for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                   InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                   if (!inetAddress.isLoopbackAddress()) {
                    ip= inetAddress.getAddress();
                   }
               }
           }
       } catch (SocketException ex) {
           Log.i("SocketException ", ex.toString());
       }
       return ip;
       
 }
public void audStream(){
	  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  StrictMode.setThreadPolicy(policy);
	  try {   
	      AudioManager audio =  (AudioManager) getSystemService(Context.AUDIO_SERVICE); 
	      audio.setMode(AudioManager.MODE_IN_COMMUNICATION);
	      AudioGroup audioGroup = new AudioGroup();
	      audioGroup.setMode(AudioGroup.MODE_NORMAL);        
	      AudioStream audioStream = new AudioStream(InetAddress.getByAddress(getLocalIPAddress ()));
	      audioStream.setCodec(AudioCodec.PCMU);
	      audioStream.setMode(RtpStream.MODE_NORMAL);
	      audioStream.associate(InetAddress.getByAddress(new byte[] {(byte)192, (byte)168, (byte)1, (byte)100 }), 22222);
	      audioStream.join(audioGroup);
	     
	   
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
}
 

}
