package org.streamer.notepad;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

public class EditMenu implements ActionListener {
	MainFrame MF;
	JTextArea text;

	Clipboard clipboard;// ������

	// "����"�����õ��ַ��� ǰ�ַ����ͺ��ַ���
	String before;
	String after;

	java.util.Date dat;

	Find_Replace FR;
	boolean findRepalceFlag;

	public EditMenu(MainFrame frame) {
		MF = frame;
		text = MF.text;
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();// ��ʵ ������
																		// �����Ѿ��õ���ϵͳ���а�Ĺ���

		setdisEnabled();
		addListener();

		text.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {

				getRanks();// ʵʱ������
				setEnabeld();
			}
		});
		
	}

	public void addListener() {
		MF.cancel.addActionListener(this);
		MF.cut.addActionListener(this);
		MF.copy.addActionListener(this);
		MF.paste.addActionListener(this);
		MF.del.addActionListener(this);
		MF.select.addActionListener(this);
		MF.date.addActionListener(this);

		MF.find.addActionListener(this);
		MF.findnext.addActionListener(this);
		MF.replace.addActionListener(this);
		MF.turn.addActionListener(this);

		// �����˵�
		MF.popCancel.addActionListener(this);
		MF.popCut.addActionListener(this);
		MF.popCopy.addActionListener(this);
		MF.popPaste.addActionListener(this);
		MF.popDel.addActionListener(this);
		MF.popSelect.addActionListener(this);
	}

	// ���ݼ������е��ַ����� �ж��Ƿ��"ճ��"
	public boolean isPaste() {
		String str = null;
		try {
			str = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (IOException | UnsupportedFlavorException e) {

			System.out.println("��ǰϵͳ���а����ַ�����");
		}
		return str != null && str != null;
	}

	// "�༭"�˵��Ŀ���������
	public void setdisEnabled() {
		// ��ʼ״̬
		MF.cancel.setEnabled(false);
		MF.cut.setEnabled(false);
		MF.copy.setEnabled(false);
		MF.del.setEnabled(false);
		MF.find.setEnabled(false);
		MF.findnext.setEnabled(false);
		if (!isPaste()) {
			MF.paste.setEnabled(false);
			MF.popPaste.setEnabled(false);
		}
		MF.popCancel.setEnabled(false);
		MF.popCut.setEnabled(false);
		MF.popCopy.setEnabled(false);
		MF.popDel.setEnabled(false);
	}

	public void setEnabeld() {
		MF.cancel.setEnabled(true);

		MF.popCancel.setEnabled(true);

		if (isPaste()) {
			MF.paste.setEnabled(true);
			MF.popPaste.setEnabled(true);
		} else {
			MF.paste.setEnabled(false);
			MF.popPaste.setEnabled(false);
		}
		if (text.getText().length() > 0) {
			MF.find.setEnabled(true);

			if (text.getSelectedText() != null) {
				MF.cut.setEnabled(true);
				MF.copy.setEnabled(true);
				MF.del.setEnabled(true);

				MF.popCut.setEnabled(true);
				MF.popCopy.setEnabled(true);
				MF.popDel.setEnabled(true);

			} else {
				MF.cut.setEnabled(false);
				MF.copy.setEnabled(false);
				MF.del.setEnabled(false);

				MF.popCut.setEnabled(false);
				MF.popCopy.setEnabled(false);
				MF.popDel.setEnabled(false);

			}
		} else {
			MF.find.setEnabled(false);
			MF.findnext.setEnabled(false);
		}

	}

	// ���� �⹦�ܵ�ʵ�� д�ò�̫��
	public void Cancel() {
		after = text.getText();
		text.setText(before); // ����֮ǰ���ı�

		text.selectAll();

		/*
		 * int pos = text.getCaretPosition(); text.insert(before, pos); try {
		 * text.select(pos, text.getLineEndOffset(text.getLineOfOffset(pos))); }
		 * catch (BadLocationException e) { e.printStackTrace(); }
		 */
		String temp = before;
		before = after;
		after = temp;

	}

	// ת��ָ����
	public void Turn() {

		String str = JOptionPane.showInputDialog(MF, "�к�(L):", "ת��ָ����",
				JOptionPane.DEFAULT_OPTION);
		int LineNum = 1;
		try {
			LineNum = Integer.valueOf(str);
		} catch (NumberFormatException e1) {
			System.out.println("��ȷ����");
		}
		int textLine = text.getLineCount();
		if (LineNum > textLine)
			for (int i = textLine; i < LineNum; i++)
				text.append("\r\n");
		else
			try {
				text.setCaretPosition(text.getLineStartOffset(LineNum - 1));
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
	}

	// ��ȡʵʱ������
	public void getRanks() {
		int pos = text.getCaretPosition();
		MF.col = pos - text.getText().substring(0, pos).lastIndexOf("\n");
		
		try {
			MF.row = text.getLineOfOffset(text.getCaretPosition()) + 1;
			MF.ranks.setText("�� " + MF.row + " �� , �� " + MF.col
					+ " ��            ");
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) e.getSource();
		if (source == MF.cancel || source == MF.popCancel) {
			Cancel();
		}
		// JTextComponent�ļ���,����,ճ������ JTextArea��ʵ��
		if (source == MF.cut || source == MF.popCut) {
			text.cut();
		}
		if (source == MF.copy || source == MF.popCopy) {
			text.copy();
		}
		if (source == MF.paste || source == MF.popPaste) {
			text.paste();
		}

		if (source == MF.del || source == MF.popDel) {
			text.replaceSelection(null);
		}
		if (source == MF.select || source == MF.popSelect) {
			text.selectAll();
		}
		if (source == MF.date) {
			dat = new java.util.Date();
			text.append(dat.toString());
		}

		if (source == MF.find || source == MF.replace) {
			if (!findRepalceFlag) {
				FR = new Find_Replace(MF);
				MF.findnext.setEnabled(true); // ��Find_Replaceʵ���� �ſ���
				findRepalceFlag = true;
			} else
				FR.setVisible(true);
		}
		if (source == MF.turn) {
			Turn();

		}
	}

}

