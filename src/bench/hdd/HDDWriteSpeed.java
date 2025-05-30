package bench.hdd;

import java.io.*;

import bench.IBenchmark;

public class HDDWriteSpeed implements IBenchmark {
	private boolean lastClean=false;
	private String prefix = "E:\\forFiles\\000-bench\\write-";
	private String suffix = ".dat";
	private int minIndex = 0;
	private int maxIndex = 8;
	private long fileSize = 1024L * 1024 * 256; // 256 MB
	private int bufferSize = 4 * 1024; // 4 KB
	private double resultMBps = 0.0;

	@Override
	public void initialize(Object... params) {
	}

	@Override
	public void warmup() {
	}

	@Override
	public void run() {
		throw new UnsupportedOperationException(
				"Method not implemented. Use run(Object) instead");
	}

	@Override
	public void run(Object... options) {
		FileWriter writer = new FileWriter();
		// either "fs" - fixed size, or "fb" - fixed buffer
		String option = (String) options[0];
		// true/false whether the written files should be deleted at the end
		Boolean clean = (Boolean) options[1];
		lastClean = clean;


		try {
			if (option.equals("fs"))
				writer.streamWriteFixedFileSize(prefix, suffix, minIndex,
						maxIndex, fileSize, clean);
			else if (option.equals("fb"))
				writer.streamWriteFixedBufferSize(prefix, suffix, minIndex,
						maxIndex, bufferSize, clean);
			else
				throw new IllegalArgumentException("Argument "
						+ options[0].toString() + " is undefined");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clean() {
		if(!lastClean) return;
		for(int i=minIndex; i<=maxIndex; i++) {
			java.io.File f=new java.io.File(prefix+ i + suffix);
			if (f.exists()) f.delete();
		}
		
		java.io.File folder= new java.io.File(prefix.substring(0, prefix.lastIndexOf(java.io.File.separator)));
		if (folder.isDirectory() && folder.list().length == 0) {
			folder.delete();
		}
	}
	public void cancel(){

	}

	public String getResult() {
		  return String.format("%.2f MBps", resultMBps);
	}
}
