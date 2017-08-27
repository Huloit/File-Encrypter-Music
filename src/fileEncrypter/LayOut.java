package fileEncrypter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import javax.swing.*;
import javax.swing.border.*;

import org.apache.commons.codec.binary.Base64;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class LayOut extends JFrame {

	public String enc_key_file;
	public String dec_key_file;
	public String encode_file_name;
	public File music_key_file;
	public File encode_file;
	public FileInputStream hash_file;
	public String enc_algo_name;
	public String dec_algo_name;
	public String enc_state;

	FileDialog enc_file_open;
	FileDialog dec_file_open;
	FileDialog enc_music_open;
	FileDialog dec_music_open;
	FileDialog save_file_name;

	JButton btn_enc_file = new JButton("암호화 파일 설정");
	JButton btn_enc_open = new JButton("암호키 파일 열기");
	JButton btn_enc_encoder = new JButton("암호화");
	JButton btn_enc_music_open = new JButton("음악열기");

	JButton btn_dec_open = new JButton("암호키 파일 열기");
	JButton btn_dec_music_open = new JButton("음악열기");
	JButton btn_dec_decoder = new JButton("복호화");

	JButton p_music = new JButton("음악 재생하기");
	JPanel enc_panel = new JPanel(new BorderLayout());
	JPanel dec_panel = new JPanel(new BorderLayout());
	JPanel dr_panel = new JPanel(new BorderLayout());
	private Container con;

	JTextArea enc_income = new JTextArea("암호화 할 문장을 적어주세요.");
	JTextArea enc_outcome = new JTextArea("암호화 결과 입니다.");
	JScrollPane enc_isp = new JScrollPane(enc_income);
	JScrollPane enc_osp = new JScrollPane(enc_outcome);
	JTextArea temp = new JTextArea();

	JTextArea dec_income = new JTextArea("");
	JTextArea dec_outcome = new JTextArea("복호화 결과 입니다.");
	JScrollPane dec_isp = new JScrollPane(dec_income);
	JScrollPane dec_osp = new JScrollPane(dec_outcome);

	JTextArea enc_file_adr = new JTextArea();
	JTextArea enc_music_adr = new JTextArea();
	JTextArea file_enc_file_adr = new JTextArea();

	JTextArea dec_file_adr = new JTextArea();
	JTextArea dec_music_adr = new JTextArea();

	String in_select_al_en;
	String in_select_en;
	String in_select_al_de;
	String in_select_de;

	Color c = new Color(148, 000, 211);
	Color c2 = new Color(123, 104, 238);
	Border line_border = BorderFactory.createLineBorder(c, 1);
	Border line_border2 = BorderFactory.createLineBorder(c2, 1);
	Border empty_border = BorderFactory.createEmptyBorder(7, 7, 7, 7);

	// UI 관련 전역변수

	public void MadeLayOut() throws FileNotFoundException {

		JFrame frm = new JFrame("File encrypter&Music Made by Dr.boom");
		frm.setBounds(450, 250, 800, 500);

		con = this.getContentPane();
		// UI 창 설정
		enc_file_open = new FileDialog(this, "파일 열기", FileDialog.LOAD);
		dec_file_open = new FileDialog(this, "파일 열기", FileDialog.LOAD);
		enc_music_open = new FileDialog(this, "음악 열기", FileDialog.LOAD);
		enc_music_open.setFile("*.mp3");
		dec_music_open = new FileDialog(this, "음악 열기", FileDialog.LOAD);
		dec_music_open.setFile("*.mp3");
		save_file_name = new FileDialog(this, "파일 저장", FileDialog.SAVE);
		save_file_name.setFile("*");
		// open(파일 열기창) 설정

		btn_enc_file.setEnabled(false);
		dec_income.setEditable(false);
		enc_music_adr.setEditable(false);
		dec_music_adr.setEditable(false);

		WindowListener listen = new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(0); // 창을 닫으면 종료
			}
		};

		String[] algo = { "알고리즘 선택", "Secret" };
		String[] code = { "문장", "파일" };

		JComboBox select_al_en = new JComboBox(algo);
		JComboBox select_en = new JComboBox(code);
		JComboBox select_al_de = new JComboBox(algo);

		select_al_en.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((String) select_al_en.getSelectedItem() == "알고리즘 선택") {
					JOptionPane.showMessageDialog(null, "알고리즘을 선택해 주세요.");
					file_enc_file_adr.setText("");
				} else if ((String) select_al_en.getSelectedItem() == "Secret") {
					enc_algo_name = (String) select_al_en.getSelectedItem();
					select_en.setSelectedIndex(0);
				}

			}

		});
		select_al_de.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((String) select_al_de.getSelectedItem() == "암호화 방식 설정") {
					JOptionPane.showMessageDialog(null, "암호화 방식을 선택해 주세요.");
				} else if ((String) select_al_de.getSelectedItem() == "Secret") {
					dec_algo_name = (String) select_al_de.getSelectedItem();
				} 
			}

		});

		select_en.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if ((String) select_en.getSelectedItem() == "파일") {
					btn_enc_file.setEnabled(true);
					enc_income.setText("파일 암호화 선택" + "\n");
					enc_income.setEnabled(false);
					enc_state = "file";
					// select_al_en.enable(false);
				} else if ((String) select_en.getSelectedItem() == "문장") {
					btn_enc_file.setEnabled(false);
					file_enc_file_adr.setText("");
					enc_income.setText("암호화 할 문장을 적어주세요.");
					enc_income.setEnabled(true);
					enc_state = "comment";
					// select_al_en.enable(true);

				}

			}

		});

		// 버튼 역활 부여 시작
		btn_enc_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enc_file_open.setVisible(true);
				if (enc_file_open.getFile() != null) {
					enc_key_file = enc_file_open.getDirectory() + enc_file_open.getFile();
				}
				enc_file_adr.setText(enc_key_file);
			}
		});

		btn_dec_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dec_file_open.setVisible(true);
				if (dec_file_open.getFile() != null) {
					dec_key_file = dec_file_open.getDirectory() + dec_file_open.getFile();
				}
				dec_file_adr.setText(dec_key_file);

			}
		});

		// 파일 열기 버튼 설정

		btn_enc_music_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enc_music_open.setVisible(true);

				if (enc_music_open.getFile() != null) {
					music_key_file = new File(enc_music_open.getDirectory() + enc_music_open.getFile());
				}
				enc_music_adr.setText(music_key_file.getPath());

			}
		});

		btn_dec_music_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dec_music_open.setVisible(true);

				if (dec_music_open.getFile() != null) {
					music_key_file = new File(dec_music_open.getDirectory() + dec_music_open.getFile());
				}
				dec_music_adr.setText(music_key_file.getPath());
				try {
					dec_income.setText("복호화 할 문장:" + tagManager.tagReader(music_key_file));
				} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
						| InvalidAudioFrameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// 음악파일 열기 설정

		btn_enc_encoder.addActionListener(new ActionListener() {
			byte[] data;
			char[] hash_char;
			String encode_message;
			File temp;

			public void actionPerformed(ActionEvent e) {
				if (select_al_en.getSelectedItem() == "알고리즘 선택") {
					JOptionPane.showMessageDialog(null, "알고리즘을 선택해 주세요.");
				} else if (enc_file_adr.getText() == null) {
					JOptionPane.showMessageDialog(null, "암호키 파일을 선택해 주세요.");
				} else if (enc_music_adr.getText() == null) {
					JOptionPane.showMessageDialog(null, "음악 파일을 선택해 주세요.");
				} else {
					String messages = enc_income.getText();
					if (enc_state == "file") {
						try {
							 if (file_enc_file_adr.getText() == null) {
									JOptionPane.showMessageDialog(null, "음악 파일을 선택해 주세요.");
									return;
								}
							encode_file = new File(file_enc_file_adr.getText());
							data = fileRead.read(encode_file);
							if (data.length == 1) {
								JOptionPane.showMessageDialog(null, "암호화할 파일이 크기 범위에 맞지않습니다. \n (5MB이하)");
								return;
							}
							messages = new String(Base64.encodeBase64(data));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					try {
						encodingAlgorithm algo = new encodingAlgorithm(enc_algo_name);
						music_key_file = new File(enc_music_adr.getText());
						if (fileRead.sizeCheck10m(music_key_file) == false) {
							JOptionPane.showMessageDialog(null, "음악 파일이 크기 범위에 맞지않습니다. \n (10MB이하)");
							return;
						}
						tagManager music_tag = new tagManager(music_key_file);
						enc_key_file = enc_file_adr.getText();
						temp = new File(enc_key_file);
						if (fileRead.sizeCheck5m(temp) == false) {
							JOptionPane.showMessageDialog(null, "암호키 파일이 크기 범위에 맞지않습니다. \n (5MB이하)");
							return;
						}
						hash_file = new FileInputStream(enc_key_file);
						hash_char = hash.fileHash(hash_file);
						String key = arrCb.arrayJoin(hash_char);

						if (enc_state == "file") {
							encode_file = new File(file_enc_file_adr.getText());
							int pos = encode_file.getName().lastIndexOf(".");
							String ext = encode_file.getName().substring(pos + 1);
							byte[] encode_data = new byte[data.length + 4];
							encode_data[0] = '&';
							for (int i = 1; i < 4; i++) {
								encode_data[i] = (byte) ext.charAt(i - 1);
							}
							for (int i = 0; i < data.length; i++) {
								encode_data[i + 4] = data[i];
							}
							encode_message = algo.getIncode(key, "&" + ext + messages);
						} else if (enc_state == "comment") {
							encode_message = algo.getIncode(key, messages);
						}
						music_tag.tagWrite(encode_message);
						enc_outcome.setText("암호화 결과:" + tagManager.tagReader(music_key_file));
						enc_income.setText("암호화한 문장:" + messages);

					}

					catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		// 인코딩

		btn_dec_decoder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String enc_message;
				String dec_message;
				char[] hash_char;
				File temp;

				if (select_al_de.getSelectedItem() == "알고리즘 선택") {
					JOptionPane.showMessageDialog(null, "알고리즘을 선택해 주세요.");
				} else if (dec_file_adr.getText() == null) {
					JOptionPane.showMessageDialog(null, "암호키 파일을 선택해 주세요.");
				} else if (dec_music_adr.getText() == null) {
					JOptionPane.showMessageDialog(null, "음악 파일을 선택해 주세요.");
				} else {
					try {
						encodingAlgorithm algo = new encodingAlgorithm(dec_algo_name);
						dec_key_file = dec_file_adr.getText();
						temp = new File(dec_key_file);
						if (fileRead.sizeCheck5m(temp) == false) {
							JOptionPane.showMessageDialog(null, "암호키 파일이 크기 범위에 맞지않습니다. \n (5MB이하)");
							return;
						}
						hash_file = new FileInputStream(dec_key_file);
						hash_char = hash.fileHash(hash_file);
						String keys = arrCb.arrayJoin(hash_char);
						music_key_file = new File(dec_music_adr.getText());
						if (fileRead.sizeCheck20m(music_key_file) == false) {
							JOptionPane.showMessageDialog(null, "음악 파일이 크기 범위에 맞지않습니다. \n (20MB이하)");
							return;
						}
						enc_message = tagManager.tagReader(music_key_file);
						dec_message = algo.getDecode(keys, enc_message);
						dec_outcome.setText("복호화 된 문장:" + dec_message);

						if (dec_message.charAt(0) == '&') {
							String save_file_path = null;
							int answer = JOptionPane.showConfirmDialog(null,
									"파일 형식으로 복호화 되었습니다.\n파일을 생성 하시겠습니까?(확장자 자동 입력)", "파일 복호화 알림",
									JOptionPane.OK_CANCEL_OPTION);
							if (answer == JOptionPane.YES_OPTION) {
								save_file_name.setVisible(true);
								if (save_file_name.getDirectory() != null) {
									save_file_path = save_file_name.getDirectory() + save_file_name.getFile();

									String remessagedeee = dec_message.substring(4);
									dec_outcome.setText(dec_message);
									FileOutputStream output_file = new FileOutputStream(save_file_path + "."
											+ dec_message.charAt(1) + dec_message.charAt(2) + dec_message.charAt(3));
									fileRead.makefile(output_file, remessagedeee);
									JOptionPane.showMessageDialog(null, "파일이 생성 되었습니다.");
								} else
									JOptionPane.showMessageDialog(null, "잘못된 선택입니다.");
							}
						}

					}

					catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		// 디코딩

		btn_enc_file.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enc_file_open.setVisible(true);
				if (enc_file_open.getFile() != null) {
					encode_file_name = enc_file_open.getDirectory() + enc_file_open.getFile();
					file_enc_file_adr.setText(encode_file_name);
					enc_income.setText("파일 암호화 선택" + "\n" + "파일 이름:" + file_enc_file_adr.getText());

				}

			}
		});

		// 파일 인코딩

		// 버튼 추가 및 버튼에 역활 부여

		frm.addWindowListener(listen);
		// 윈도우 리스너 설정

		enc_file_adr.setBorder(BorderFactory.createCompoundBorder(line_border, empty_border));
		enc_music_adr.setBorder(BorderFactory.createCompoundBorder(line_border, empty_border));
		file_enc_file_adr.setBorder(BorderFactory.createCompoundBorder(line_border, empty_border));

		dec_file_adr.setBorder(BorderFactory.createCompoundBorder(line_border, empty_border));
		dec_music_adr.setBorder(BorderFactory.createCompoundBorder(line_border, empty_border));

		enc_income.setBorder(BorderFactory.createCompoundBorder(line_border2, empty_border));
		enc_outcome.setBorder(BorderFactory.createCompoundBorder(line_border2, empty_border));

		dec_income.setBorder(BorderFactory.createCompoundBorder(line_border2, empty_border));
		dec_outcome.setBorder(BorderFactory.createCompoundBorder(line_border2, empty_border));

		// 버튼 및 레이아웃 배치

		JTabbedPane tab = new JTabbedPane();

		tab.addTab("암호화", enc_panel);
		tab.addTab("복호화", dec_panel);
		// 탭 추가

		this.con.add(tab);
		frm.add("Center", tab);
		frm.setVisible(true);
		// 텝 설정

		enc_panel.setLayout(null);
		enc_file_adr.setBounds(50, 18, 500, 30);
		enc_music_adr.setBounds(50, 54, 500, 30);
		file_enc_file_adr.setBounds(50, 90, 500, 30);
		btn_enc_open.setBounds(600, 18, 150, 30);
		btn_enc_music_open.setBounds(600, 54, 150, 30);
		btn_enc_file.setBounds(600, 90, 150, 30);
		btn_enc_encoder.setBounds(600, 370, 150, 50);
		enc_isp.setBounds(40, 130, 400, 240);
		enc_osp.setBounds(450, 170, 300, 200);
		select_al_en.setBounds(470, 130, 120, 30);
		select_en.setBounds(600, 130, 120, 30);
		// 인코딩 패널 크기 조정

		dec_panel.setLayout(null);
		dec_file_adr.setBounds(50, 18, 500, 30);
		dec_music_adr.setBounds(50, 54, 500, 30);
		btn_dec_open.setBounds(600, 18, 150, 30);
		btn_dec_music_open.setBounds(600, 54, 150, 30);
		dec_isp.setBounds(40, 130, 400, 240);
		dec_osp.setBounds(450, 170, 300, 200);
		btn_dec_decoder.setBounds(600, 370, 150, 50);
		select_al_de.setBounds(470, 130, 120, 30);
		// 디코딩 패널 크기 조정

		enc_panel.add(enc_file_adr);
		enc_panel.add(enc_music_adr);
		enc_panel.add(file_enc_file_adr);
		enc_panel.add(btn_enc_open);
		enc_panel.add(btn_enc_music_open);
		enc_panel.add(btn_enc_encoder);
		enc_panel.add(btn_enc_file);
		enc_panel.add(enc_isp);
		enc_panel.add(enc_osp);
		enc_panel.add(select_al_en);
		enc_panel.add(select_en);
		// 인코딩 패널 설정

		dec_panel.add(dec_file_adr);
		dec_panel.add(dec_music_adr);
		dec_panel.add(btn_dec_open);
		dec_panel.add(btn_dec_music_open);
		dec_panel.add(dec_isp);
		dec_panel.add(dec_osp);
		dec_panel.add(btn_dec_decoder);
		dec_panel.add(select_al_de);

		// 디코딩 패널 설정

	}
}
