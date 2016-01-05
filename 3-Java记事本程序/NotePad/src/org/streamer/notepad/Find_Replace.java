package org.streamer.notepad;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Find_Replace extends JDialog implements ActionListener {
	MainFrame MF;
	JTextArea text;

	JTextField findText;
	JTextField replaceText;

	JButton findNextB;
	JButton findAllB;
	JButton replaceB;
	JButton replaceAllB;
	JButton No;

	JCheckBox Upper_lower;
	JRadioButton up;
	JRadioButton down;

	boolean up_lowFlag;
	int RadioSign; // downΪ0 upΪ1

	int findPos; // �������ı�ʱ ����λ��
	boolean findFlag; // ����Ƿ��ҵ�
	String find;

	public Find_Replace(MainFrame frame) {
		super(frame, "���Һ��滻");
		MF = frame;
		text = MF.text;
		setSize(480, 200);
		setLocation(460, 250);
		buildPane(getContentPane());
		setDisenable();
		setVisible(true);
		addListener();
		up_lowFlag = true;//���ִ�Сд
	}

	public void buildPane(Container pane) {

		Box textBox1 = Box.createHorizontalBox();
		Box textBox2 = Box.createHorizontalBox();

		Box SelectBox = Box.createHorizontalBox();
		Box ButtonBox = Box.createHorizontalBox();

		// �ı������
		findText = new JTextField();
		replaceText = new JTextField();
		JLabel findLabel = new JLabel("��������(N):", JLabel.CENTER);
		JLabel replaceLabel = new JLabel("�滻Ϊ(P):", JLabel.CENTER);

		textBox1.add(Box.createHorizontalStrut(15));
		textBox1.add(findLabel);
		textBox1.add(Box.createHorizontalStrut(5));
		textBox1.add(findText);
		textBox1.add(Box.createHorizontalStrut(15));

		textBox2.add(Box.createHorizontalStrut(15));
		textBox2.add(replaceLabel);
		textBox2.add(Box.createHorizontalStrut(5));
		textBox2.add(replaceText);
		textBox2.add(Box.createHorizontalStrut(15));

		// ѡ������
		Upper_lower = new JCheckBox("���ִ�Сд(C)");
		up = new JRadioButton("����(U)");
		down = new JRadioButton("����(D)");
		down.setSelected(true);
		ButtonGroup group = new ButtonGroup();
		group.add(up);
		group.add(down);

		Box Radiobox = Box.createHorizontalBox();
		Radiobox.setBorder(BorderFactory.createTitledBorder("����"));
		Radiobox.add(down);
		Radiobox.add(Box.createHorizontalStrut(5));
		Radiobox.add(up);

		SelectBox.add(Upper_lower);
		SelectBox.add(Box.createHorizontalStrut(5));
		SelectBox.add(Radiobox);

		// ��ť���
		findNextB = new JButton("������һ��");
		findAllB = new JButton("����ȫ��");
		replaceB = new JButton("�滻");
		replaceAllB = new JButton("ȫ���滻");
		No = new JButton("ȡ��");

		ButtonBox.add(findNextB);
		ButtonBox.add(Box.createHorizontalStrut(5));
		ButtonBox.add(findAllB);
		ButtonBox.add(Box.createHorizontalStrut(5));
		ButtonBox.add(replaceB);
		ButtonBox.add(Box.createHorizontalStrut(5));
		ButtonBox.add(replaceAllB);
		ButtonBox.add(Box.createHorizontalStrut(5));
		ButtonBox.add(No);

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.add(Box.createVerticalStrut(10));
		pane.add(textBox1);
		pane.add(Box.createVerticalStrut(5));
		pane.add(textBox2);
		pane.add(Box.createVerticalStrut(5));
		pane.add(SelectBox);
		pane.add(Box.createVerticalStrut(5));
		pane.add(ButtonBox);
		pane.add(Box.createVerticalStrut(5));

	}

	public void addListener() {
		findNextB.addActionListener(this);
		findAllB.addActionListener(this);
		replaceB.addActionListener(this);
		replaceAllB.addActionListener(this);
		No.addActionListener(this);

		MF.findnext.addActionListener(this);
		// ѡ���
		Upper_lower.addActionListener(this);
		up.addActionListener(this);
		down.addActionListener(this);

		CaretListener CareListen = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				// TODO �Զ����ɵķ������
				setEnable();
			}
		};

		findText.addCaretListener(CareListen);
		replaceText.addCaretListener(CareListen);

	}

	// ��ʼ����ť״̬
	public void setDisenable() {
		findNextB.setEnabled(false);
		findAllB.setEnabled(false);
		replaceB.setEnabled(false);
		replaceAllB.setEnabled(false);

		Upper_lower.setSelected(true);
	}

	// ���ı������ı�ʱ ���ť״̬
	public void setEnable() {
		if (findText.getText().length() > 0) {
			findNextB.setEnabled(true);
			findAllB.setEnabled(true);
			if (replaceText.getText().length() > 0) {
				replaceB.setEnabled(true);
				replaceAllB.setEnabled(true);
			} else {
				replaceB.setEnabled(false);
				replaceAllB.setEnabled(false);
			}
		} else
			setDisenable();

	}

	public void seek() {

		find = findText.getText();
		int length = find.length();
		// ���²���
		if (RadioSign == 0) {
			findFlag = false;
			findPos = text.getCaretPosition();
			for (int pos = findPos; pos < text.getText().length(); pos++) {
				text.select(pos, pos + length); // ���ѡ���ַ�

				String textCopy = text.getSelectedText();
				// ���ִ�Сд up_lowFlag=true ������
			
				if (!up_lowFlag) {
					find = find.toLowerCase();
					textCopy = textCopy.toLowerCase();
				}

				if (find.equals(textCopy)) {
					findPos = text.getCaretPosition();
					// System.out.println("find");
					findFlag = true;
					break;
				}
				if (!findFlag) {
					// System.out.println("No find ");
					text.setCaretPosition(++findPos);  //�������ù���ƶ���ĩλ
				}
			}
		}

		if (RadioSign == 1) {
			// �����ѡ��ʱ �ҵ����ַ��� ��������ƶ��� ʹ�ı����޹��ѡ�еı�ʾ ���� ��һ�β���ʱ ���ù����λ
			if (findFlag) {
				text.setCaretPosition(findPos);
				// System.out.println("up" + findPos);
			}
			findFlag = false;
			findPos = text.getCaretPosition();
			for (int pos = findPos; pos > 0; --pos) {
				text.select(pos - length, pos);

				String textCopy = text.getSelectedText();
				// ���ִ�Сд up_lowFlag=true ������
				if (!up_lowFlag) {
					find = find.toLowerCase();
					textCopy = textCopy.toLowerCase();
				}
				if (find.equals(textCopy)) {
					findPos -= length;
					findFlag = true;
					break;
				}
				if (!findFlag) {
					// System.out.println("No find ");
					text.setCaretPosition(--findPos);
				}
			}
		}

		// ��ʾ�Ի���
		if (!findFlag)
			JOptionPane.showMessageDialog(MF, "�Ҳ��� " + find, "���±�",
					JOptionPane.CANCEL_OPTION);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == Upper_lower) {
			if (Upper_lower.isSelected())
				up_lowFlag = true; // ����
			else
				up_lowFlag = false;// ������
		}

		if (source == down) {
			RadioSign = 0; // ����
		}
		if (source == up) {
			RadioSign = 1;// ����
		}

		if (source == findNextB || source == findAllB) {
			seek();
		}
		if (source == replaceB) {
			seek();
			if (findFlag) {
				text.replaceSelection(replaceText.getText());
			}
		}
		if (source == replaceAllB) {
			text.setCaretPosition(0);
			int before = RadioSign;
			RadioSign = 0;
			while (text.getCaretPosition() < text.getText().length()) {
				seek();
				if (findFlag)
					text.replaceSelection(replaceText.getText());
			}
			RadioSign = before;
		}
		if (source == No) {
			dispose();
		}

		if (source == MF.findnext) {
			if (findText.getText().length() > 0) {
				seek();
			} else
				setVisible(true);
		}
	}
}


