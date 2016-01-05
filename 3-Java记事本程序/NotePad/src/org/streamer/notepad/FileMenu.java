package org.streamer.notepad;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileMenu implements ActionListener {

	MainFrame MF;
	JFileChooser Chooser;
	JTextArea text;
	JMenuItem openfile;

	// ����"��"���ļ��� ��"����"�Ͳ��赯���Ի���
	boolean openFlag;
	// int endOffSet; ���ı�������ƫ�����ж��ı��Ƿ�ı� ��׼ȷ ��before after����

	String before;
	String after;

	public FileMenu(MainFrame frame) {
		MF = frame;
		text = frame.text;
		before = "";
		Chooser = new JFileChooser();

		// �ļ����͹�����
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"�ı��ĵ�(*.txt)", "txt", "TXT");
		// ȥ"��������"��ѡ��
		// Chooser.setAcceptAllFileFilterUsed(false);
		Chooser.setFileFilter(filter);
		Chooser.setSelectedFile(new File("*.txt")); // ����Ĭ���ļ�

		// ע�������
		frame.newFile.addActionListener(this);
		frame.openFile.addActionListener(this);
		frame.saveFile.addActionListener(this);
		frame.saveAsFile.addActionListener(this);
		frame.pageSet.addActionListener(this);
		frame.print.addActionListener(this);
		frame.exit.addActionListener(this);

		MF.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}

	public void open() {
		if (JFileChooser.APPROVE_OPTION == Chooser.showOpenDialog(MF)) {
			File f = Chooser.getSelectedFile();
			// path = Chooser.getSelectedFile().getAbsolutePath();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f));
				String s = "";
				String line = reader.readLine();
				while (line != null) {
					s += line;
					s += "\n";
					line = reader.readLine();
				}
				text.setText(s);
				reader.close();

				openFlag = true; // �Ѵ��ļ�
				// endOffSet = text.getLineEndOffset(text.getLineCount() - 1);
				// // ��õ�ǰ���ı��Ĺ������ƫ����

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	// "����"��"���Ϊ" ��openFlag����
	public void save() {

		String path = null; // �洢�ļ���·��
		int n = 0; // n��ʾ �����ļ�����Ի���ʱ ���°�ť��ֵ ȷ��-1 ȡ��-0
		if (openFlag)
			path = Chooser.getSelectedFile().getAbsolutePath(); // �Ѵ��ļ��ľ���·��
		else {
			n = Chooser.showSaveDialog(MF);
			path = Chooser.getSelectedFile().getAbsolutePath(); // ��·��
		}
		if (n != 1) {
			File f = new File(path);
			// System.out.println(path);
			try {
				// һ��һ�еش洢�ĵ���ָ���ļ�
				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
				text.append("\0"); // ���ļ�β��ӿո� ��ֹ�ı�ȱʧ�ʹ����ȡ�����ڵ�λ��
				for (int i = 0; i < text.getLineCount(); i++) {
					/*
					 * System.out.println(text.getLineStartOffset(i) + "  " +
					 * text.getLineEndOffset(i));
					 */
					String str = text.getText(
							text.getLineStartOffset(i),
							text.getLineEndOffset(i)
									- text.getLineStartOffset(i) - 1)
							+ "\r\n";
					writer.write(str);

				}
				writer.close();
			} catch (IOException | BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void print() {
		String message = "��������ִ�����ӡ�йص�����(����ҳ�����û��ӡһ���ĵ�)֮ǰ��\n�������Ѿ���װ��ӡ�����������ڰ�װ��ӡ����?";
		int n = JOptionPane.showConfirmDialog(MF, message, "�Ի�-���±�",
				JOptionPane.OK_OPTION);
		if (n == 0) {
			String warn = "�޷���' ��Ӵ�ӡ�� ', ���غ�̨����������û������";
			JOptionPane.showMessageDialog(MF, warn, "��ӡ��",
					JOptionPane.CANCEL_OPTION);
		}
	}

	public void exit() {
		try {
			int n = 0;
			if (text.getText().length() > 0) {
				// ��֮ǰ���ı����ƫ�����Ƚ� �жϵ�ǰ�ı����ƫ�����Ƿ�ı� ������Ʋ�̫��
				// if (text.getLineEndOffset(text.getLineCount() - 1) !=
				// endOffSet)
				// ��after before �ж�
				after = text.getText();
				if (!before.equals(after)) {
					n = JOptionPane.showConfirmDialog(MF, "�Ƿ񱣴������ı�", "���±�", 0);
					if (n == 0)
						if (openFlag) // "����"
							save();
						else { // "���Ϊ"
							openFlag = false;
							save();
						}
				}
			}
			if (n != -1) // ����"��"���� 1 "��" 0
				System.exit(0);
		} catch (HeadlessException e1) {

			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == MF.newFile) {
			if (text.getText().length() > 0) {
				int n = 0;
				if (n == JOptionPane
						.showConfirmDialog(MF, "�Ƿ񱣴������ı�", "���±�", 0))
					save();
			}
			text.setText(null);

			before = "";
			openFlag = false;
			// endOffSet = 0;
			// ��"����"������
			MF.cancel.setEnabled(false);
			MF.popCancel.setEnabled(false);
		}

		if (source == MF.openFile) {
			if (text.getText().length() > 0) {
				int n = 0;
				if (n == JOptionPane
						.showConfirmDialog(MF, "�Ƿ񱣴������ı�", "���±�", 0))
					save();
			}
			open();
			before = text.getText();
		}

		if (source == MF.saveFile) {
			if (text.getText().length() > 0) {
				save();
				before = text.getText();
			}
		}

		if (source == MF.saveAsFile) {
			if (text.getText().length() > 0) {
				openFlag = false;
				save();
			}
		}

		if (source == MF.pageSet) {
			print();
		}

		if (source == MF.print) {
			print();
		}

		if (source == MF.exit) {
			exit();
		}
	}

}
