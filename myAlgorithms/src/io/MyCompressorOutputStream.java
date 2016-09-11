package io;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	
	public MyCompressorOutputStream(OutputStream out) {
		this.out = out;
	}
	/**
	 * export the number to a file or screen
	 */
	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}
	
	/**
	 * getting a array of bytes, compress an organ by checking and count each byte  
	 */
	@Override
	public void write(byte[] arr) throws IOException {
		byte currByte = arr[0];
		int count = 1;
		
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] != currByte) {
				while (count >= 255) {
					out.write(255);
					out.write(currByte);
					count -= 255;
				}
				out.write(count);
				out.write(currByte);
				currByte = arr[i];
				count = 1;
			}
			else {
				count++;
			}
		}
		out.write(count);
		out.write(currByte);
		
	}

}
