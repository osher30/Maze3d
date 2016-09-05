package io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
	
	private OutputStream out;
	private int counter;
	private int prevByte;
	
	public MyCompressorOutputStream(OutputStream out) {
		this.out = out;
		this.counter = 0;
		this.prevByte = 0;
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		super.write(b);
	}
	
	
	public void write(int b){
		
	}

}
