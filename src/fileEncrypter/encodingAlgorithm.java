package fileEncrypter;


public class encodingAlgorithm {
	String Algoname = "";
	String remessage = "";

	public encodingAlgorithm(String name) {
		Algoname = name;

	}

	public static int[] readkey(String keys, String messages) {

		int intkey[] = new int[keys.length()];
		if (keys.length() > 0 && messages.length() > 0) {
			for (int i = 0; i < keys.length(); i++) {
				if (arrCb.isNum(keys.charAt(i))) {
					intkey[i] = Integer.parseInt(Character.toString(keys.charAt(i)));

				} else {
					intkey[i] = (int) keys.charAt(i);
				}
			}
		}
		return intkey;
	}

	public String getIncode(String key, String messages) throws Exception {
		switch (Algoname) {

		case "Secret":
			SecretIncoder(key, messages);

			break;

		default:
			remessage = "-1";
			break;

		}
		return remessage;
	}

	public String getDecode(String key, String messages) throws Exception {
		switch (Algoname) {
		
		case "Secret":
			SecretDecoder(key, messages);

			break;

		default:
			remessage = "-1";

		}
		return remessage;
	}

	

	public void SecretIncoder(String key, String messages) {
		int intkey[];
		intkey = encodingAlgorithm.readkey(key, messages);
		int temp;
		int i, j, k = 0;
		int l = 0;
		int count = 0;
		int count3 = 0;
		int msg[][];
		int arrkey[] = new int[intkey.length];
		int orgintkey[] = new int[intkey.length];

		for (i = 0; i < intkey.length; i++) {
			orgintkey[i] = intkey[i];
		}

		for (i = 0; i < arrkey.length; i++) {
			arrkey[i] = -1;
		}

		// 배열 선언
		if (messages.length() % intkey.length == 0) {
			msg = new int[intkey.length][(int) messages.length() / intkey.length];
		} else {
			msg = new int[intkey.length][(int) messages.length() / intkey.length + 1];
		}
		int nmsg[][] = new int[msg.length][msg[0].length];

		String msgs[] = new String[msg.length * msg[0].length];

		// 배열 초기화
		for (i = 0; i < msg.length; i++) {
			for (j = 0; j < msg[0].length; j++) {
				msg[i][j] = '#';
				nmsg[i][j] = '#';
			}
		}

		// 배열 값 저장
		for (i = 0; i < msg.length; i++) {
			for (j = 0; j < msg[0].length; j++) {
				if (k == messages.length()) {
					count = 1;
					break;
				}
				msg[i][j] = (int) messages.charAt(k);
				k++;
			}
			if (count == 1)
				break;
		}

		// 암호키 정렬
		///////////////////////////////////
		// 암호키 중에서 서로다른 값 찾기
		count3 = 0;
		for (i = 0; i < intkey.length; i++) {
			count = 0;
			for (j = 0; j < i; j++) {
				if (intkey[i] != arrkey[j]) {
					count++;
				}
			}
			if (count == j) {
				arrkey[count3] = intkey[i];
				count3++;
			}
		}

		// 암호키 내림차순 정렬
		for (i = 0; i < arrkey.length; i++) {
			for (j = i + 1; j < arrkey.length; j++) {
				if (arrkey[i] < arrkey[j]) {
					temp = arrkey[i];
					arrkey[i] = arrkey[j];
					arrkey[j] = temp;
				}
			}
		}
		////////////////////////////////////

		// 배열 행 정렬
		///////////////////////////////////
		for (i = 0; i < count3; i++) {
			for (j = 0; j < intkey.length; j++) {
				if (arrkey[i] == intkey[j]) {
					for (k = 0; k < msg[0].length; k++) {
						nmsg[l][k] = msg[j][k];
					}
					l++;
				}
			}
		}
		////////////////////////////////////

		// 배열 합치기
		k = 0;
		for (i = 0; i < msg.length; i++) {
			for (j = 0; j < msg[0].length; j++) {
				nmsg[i][j] += orgintkey[i];
				msgs[k] = String.valueOf(nmsg[i][j]);
				k++;
			}
		}

		remessage = arrCb.arrayJoin(msgs, "#", nmsg.length * nmsg[0].length);
	}

	public void SecretDecoder(String key, String messages) {
		int intkey[];
		intkey = encodingAlgorithm.readkey(key, messages);
		int i, j, k = 0;
		int count = 0;
		int temp;
		int msg[][];
		int arrkey[] = new int[intkey.length];
		int orgintkey[] = new int[intkey.length];
		for (i = 0; i < intkey.length; i++) {
			orgintkey[i] = intkey[i];
		}
		for (i = 0; i < messages.length(); i++) {
			if (messages.charAt(i) == '#') {
				count++;
			}
		}

		if (count % intkey.length == 0) {
			msg = new int[intkey.length][(int) count / intkey.length];
		} else {
			msg = new int[intkey.length][(int) count / intkey.length + 1];
		}
		char nmsg[][] = new char[msg.length][msg[0].length];

		String msgs[] = new String[msg.length * msg[0].length];
		for (i = 0; i < msgs.length; i++) {
			msgs[i] = "";
		}
		String rmsgs[] = new String[msg.length * msg[0].length];

		for (i = 0; i < intkey.length; i++) {
			for (j = i + 1; j < intkey.length; j++) {
				if (intkey[i] < intkey[j]) {
					temp = intkey[i];
					intkey[i] = intkey[j];
					intkey[j] = temp;
				}
			}
		}

		i = 0;
		k = 0;
		while (true) {
			if (k == messages.length()) {
				break;
			} else if (arrCb.isNum(messages.charAt(k))) {
				msgs[i] += String.valueOf(messages.charAt(k));
				k++;
			} else {
				k++;
				i++;
			}

		}

		k = 0;
		for (i = 0; i < msg.length; i++) {
			for (j = 0; j < msg[0].length; j++) {
				if (k == msgs.length) {
					count = 1;
					break;
				}
				msg[i][j] = Integer.parseInt(msgs[k]);
				msg[i][j] -= orgintkey[i];
				k++;

			}
			if (count == 1)
				break;
		}

		for (i = 0; i < intkey.length; i++) {
			for (j = 0; j < intkey.length; j++) {
				if (orgintkey[i] == intkey[j]) {
					arrkey[i] = j;
					intkey[j] = 70000;
					break;
				}
			}
		}

		for (i = 0; i < nmsg.length; i++) {
			for (j = 0; j < nmsg[0].length; j++) {
				if (msg[arrkey[i]][j] != 35) {
					nmsg[i][j] = (char) msg[arrkey[i]][j];
				} else
					nmsg[i][j] = ' ';
			}
		}

		k = 0;
		for (i = 0; i < msg.length; i++) {
			for (j = 0; j < msg[0].length; j++) {
				rmsgs[k] = String.valueOf(nmsg[i][j]);
				k++;
			}
		}

		remessage = arrCb.arrayJoin(rmsgs, "@", nmsg.length * nmsg[0].length);

	}
}
