package com.nitdroid.clientbme;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;

public class BMEReader {

	private int readInt(byte[] bytes, int index)
	{
	    long l = 0;
	    l |= bytes[4*index+3] & 0xFF;
	    l <<= 8;
	    l |= bytes[4*index+2] & 0xFF;
	    l <<= 8;
	    l |= bytes[4*index+1] & 0xFF;
	    l <<= 8;
	    l |= bytes[4*index] & 0xFF;
		return (int)l;
	}

	public BMEInfo getInfo() {
		BMEInfo info = null;

		LocalSocket s = new LocalSocket();
		try
		{
			s.connect( new LocalSocketAddress("/mnt/initfs/tmp/.bmesrv", LocalSocketAddress.Namespace.FILESYSTEM) );
			OutputStream os = s.getOutputStream();
			DataInputStream is = new DataInputStream(s.getInputStream());
			os.write( new byte[] {0x53, 0x59, 0x4E, 0x43, 0x08, 0x00, 0x00, 0x00, 0x42, 0x4D, 0x65, 0x6E, 0x74, 0x69, 0x74, 0x79} );
			os.flush();

			long length = is.available();
			byte[] bytes = new byte[(int) length];
			is.read(bytes);

			os.write( new byte[] {0x53, 0x59, 0x4E, 0x43, 0x04, 0x00, 0x00, 0x00, 0x03, (byte)0x80, 0x00, 0x00 } );
			os.flush();
			Thread.sleep(100); // how do I poll in Java?
			length = is.available();
			if (length > 128)
				length -= 128;
			bytes = new byte[(int) length];
			is.read(bytes);

			bytes = new byte[128];
			is.read(bytes);
			s.close();

			info = new BMEInfo();
			info.chargerType = readInt(bytes, 2);
			info.chargingTime = readInt(bytes, 5);
			info.batteryMaxLevel = readInt(bytes, 8);
			info.batteryCurLevel = readInt(bytes, 9);
			info.batteryMaxCapacity = readInt(bytes, 12);
			info.batteryCurCapacity = readInt(bytes, 13);
			info.batteryMaxVoltage = readInt(bytes, 14);
			info.batteryCurVoltage = readInt(bytes, 15);
			info.batteryPctLevel = readInt(bytes, 16);
			info.batteryCurrent = readInt(bytes, 18);
			info.batteryTemperature = readInt(bytes, 17);
			info.batteryLastFullCapacity = readInt(bytes, 20);
		}
		catch(IOException e) {
			info = null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return info;
	}
}
