package fileEncrypter;

public class arrCb {
	public static String arrayJoin(int array[]) {
		String result = "";

		for (int i = 0; i < array.length; i++) {
			result += array[i];
		}
		return result;
	}

	public static String arrayJoin(char array[]) {
		String result = "";

		for (int i = 0; i < array.length; i++) {
			result += array[i];
		}
		return result;
	}

	public static String arrayJoin(long array[]) {
		String result = "";

		for (int i = 0; i < array.length; i++) {
			result += array[i];
		}
		return result;
	}

	public static String arrayJoin(byte array[]) {
		String result = "";

		for (int i = 0; i < array.length; i++) {
			result += array[i];
		}
		return result;
	}

	public static String arrayJoin(String array[], String plus, int messages) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < messages; i++) {

			if (array[i] == null) {
				continue;
			}

			if (plus == "@") {
				result.append(array[i]);
				continue;
			}
			result.append(array[i] + plus);
		}
		return result.toString();
	}

	public static String arrayJoin(byte array[], String plus, int messages) {
		String result = "";

		for (int i = 0; i < messages; i++) {

			if (array[i] == 0) {
				continue;
			}

			if (plus == "@") {
				result += array[i];
				continue;
			}
			result += array[i] + plus;
		}
		return result;
	}

	public static boolean isNum(char var) {
		if (Character.isDigit(var)) {
			return true;
		} else {
			return false;
		}
	}

}
