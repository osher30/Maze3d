package io;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
	private InputStream in;
	
	public MyDecompressorInputStream(InputStream in) {
		this.in = in;
	}

	@Override
	public int read() throws IOException {		
		return in.read();
	}
	
	@Override
	public int read(byte[] arr) throws IOException {
		int k = 0;
		while (k < arr.length) {
			byte temp = (byte) in.read();
			int count = temp & 0xFF;
			byte b = (byte) in.read();
			
			for (int j = 0; j < count; j++) {
				if(k < arr.length)
				arr[k++] = b;
			}
		}
		return arr.length;		
	}
}
