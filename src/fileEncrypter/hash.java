// Made by Team Dr.Boom //

package fileEncrypter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;

public class hash {
	public static char[] fileHash(FileInputStream file) throws Exception {
		char[] hash_code = null;
		int read = 0;
		int read_size = 16384; // 16KB
		byte[] buffer = new byte[read_size];
		try {
			MessageDigest hash_val = MessageDigest.getInstance("MD5");
			for (read = 0; read != -1; read = file.read(buffer)) {
				hash_val.update(buffer, 0, read);
			}
			byte[] hash = new byte[hash_val.getDigestLength()];
			hash = hash_val.digest();

			StringBuffer temp = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				temp.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
			}

			hash_code = new char[temp.length()];
			for (int i = 0; i < temp.length(); i++) {
				hash_code[i] = temp.charAt(i);
			}
			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return hash_code;
	}
}
