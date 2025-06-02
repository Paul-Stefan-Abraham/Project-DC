package bench.hdd;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import timing.Timer;

public class FileWriter {

	private static final int MIN_BUFFER_SIZE = 1024; // KB
	private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 32; // MB
	private static final long MIN_FILE_SIZE = 1024 * 1024; // MB
	private static final long MAX_FILE_SIZE = 1024 * 1024 * 512; // MB
	private final Timer timer = new Timer();
	private final Random rand = new Random();
	private double benchScore;


	public void streamWriteFixedFileSize(String filePrefix, String fileSuffix,
			int minIndex, int maxIndex, long fileSize, boolean clean)
			throws IOException {

		System.out.println("Stream write benchmark with fixed file size");
		int currentBufferSize = MIN_BUFFER_SIZE;
		String fileName;
		int fileIndex = 0;
		benchScore = 0;

		while (currentBufferSize <= MAX_BUFFER_SIZE
				&& fileIndex <= maxIndex - minIndex) {
			fileName = filePrefix + fileIndex + fileSuffix;
			writeFile(fileName, currentBufferSize, fileSize, clean);
			currentBufferSize=currentBufferSize*2;
			fileIndex++;
		}

		benchScore /= (maxIndex - minIndex + 1);

		int index = filePrefix.indexOf(":\\");
		if (index > 0) {
			String partition = filePrefix.substring(0, index);
			System.out.println("File write score on partition " + partition + ": " +
					String.format("%.2f", benchScore) + " MB/sec");
		} else {
			System.out.println("File write score: " + String.format("%.2f", benchScore) + " MB/sec");
		}
	}


	public void streamWriteFixedBufferSize(String filePrefix, String fileSuffix,
			int minIndex, int maxIndex, int bufferSize, boolean clean)
			throws IOException {

		System.out.println("Stream write benchmark with fixed buffer size");
		long currentFileSize = MIN_FILE_SIZE;
		int fileIndex = 0;
		benchScore=0;
		
		while (currentFileSize <= MAX_FILE_SIZE
				&& fileIndex <= maxIndex - minIndex) {
			String fileName = filePrefix + fileIndex + fileSuffix;
			writeFile(fileName, bufferSize, currentFileSize, clean);
			currentFileSize *= 2;
			fileIndex++;
		}

		benchScore /= (maxIndex - minIndex + 1);

		int index = filePrefix.indexOf(":\\");
		if (index > 0) {
			String partition = filePrefix.substring(0, index);
			System.out.println("File write score on partition " + partition + ": " +
					String.format("%.2f", benchScore) + " MB/sec");
		} else {
			System.out.println("File write score: " + String.format("%.2f", benchScore) + " MB/sec");
		}
	}


	private void writeFile(String fileName, int bufferSize,
			long fileSize, boolean clean) throws IOException {

		File folderPath = new File(fileName.substring(0,
				fileName.lastIndexOf(File.separator)));

		// create folder path to benchmark output
		if (!folderPath.isDirectory())
			folderPath.mkdirs();

		final File file = new File(fileName);
		// create stream writer with given buffer size
		final BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), bufferSize);

		byte[] buffer = new byte[bufferSize];
		int i = 0;
		long toWrite = fileSize / bufferSize;

		timer.start();
		while (i < toWrite) {
			// generate random content to write
			rand.nextBytes(buffer);

			outputStream.write(buffer);
			i++;
		}		
		printStats(fileName, fileSize, bufferSize);

		outputStream.close();
		if(clean) {
			file.delete();
		}
	}

	private void printStats(String fileName, long totalBytes, int bufferSize) {
		final long time = timer.stop();
		
		NumberFormat nf = new DecimalFormat("#.00");
		double seconds = time/1e9; // calculated from timer's 'time'
		double megabytes = totalBytes / (1024.0 * 1024); //
		double rate =megabytes/seconds; // calculated from the previous two variables
		System.out.println("Done writing " + totalBytes + " bytes to file: "
				+ fileName + " in " + nf.format(seconds) + " ms ("
				+ nf.format(rate) + "MB/sec)" + " with a buffer size of "
				+ bufferSize / 1024 + " kB");

		// actual score is write speed (MB/s)
		benchScore += rate;
	}
}