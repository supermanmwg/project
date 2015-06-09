package com.example.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

public class UtilsGUI {

	/**
	 * Create a file to store the result of a download.
	 * 
	 * @param context
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static File getTemporaryFile(final Context context, final String url)
			throws IOException {
		return new File(Environment.getExternalStorageDirectory(),
				Base64.encodeToString(url.getBytes(), Base64.NO_WRAP) + ".png");
		
	}

	/**
	 * Copy the contents of an InputStream into an OutputStream.
	 * 
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 */
	private static int copy(final InputStream in, final OutputStream out)
			throws IOException {
		final int BUFFER_LENGTH = 1024;
		final byte[] buffer = new byte[BUFFER_LENGTH];
		int totalRead = 0;
		int read = 0;

		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
			totalRead += read;
		}

		return totalRead;
	}

	/**
	 * Download the requested image and return the local file path.
	 * 
	 * @param context
	 * @param url
	 * @return
	 */
	public static String downloadImage(final Context context, final String url) {
		try {
			final File file = getTemporaryFile(context, url);

			final InputStream in = (InputStream) new URL(url).getContent();
			final OutputStream out = new FileOutputStream(file);

			copy(in, out);
			in.close();
			out.close();
			return file.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
