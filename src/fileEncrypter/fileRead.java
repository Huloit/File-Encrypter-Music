package fileEncrypter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class fileRead {
	public static boolean sizeCheck20m(File file) {
		if (file.length() > Integer.MAX_VALUE / 100) {
			return false;

		} else
			return true;
	}

	public static boolean sizeCheck10m(File file) {
		if (file.length() > Integer.MAX_VALUE / 200) {
			return false;

		} else
			return true;
	}

	public static boolean sizeCheck5m(File file) {
		if (file.length() > Integer.MAX_VALUE / 400) {
			return false;

		} else
			return true;
	}

	public static byte[] read(File file) throws IOException {

		if (fileRead.sizeCheck5m(file) == false) {
			byte[] Bfile = new byte[1];
			Bfile[0] = 0;

			return Bfile;

		}

		FileInputStream inputStream = null;
		ByteArrayOutputStream byteOutStream = null;
		try {
			inputStream = new FileInputStream(file);
			byteOutStream = new ByteArrayOutputStream();

			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = inputStream.read(buf)) != -1) {
				byteOutStream.write(buf, 0, len);
			}

			byte[] fileArray = byteOutStream.toByteArray();
			return fileArray;

		} finally {
			inputStream.close();
			byteOutStream.close();
		}
	}

	static void makefile(FileOutputStream write_file, String dec_message) throws IOException {
		byte[] b = Base64.decodeBase64(dec_message.getBytes());
		write_file.write(b);
		write_file.close();

	}
}
