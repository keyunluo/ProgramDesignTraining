package org.streamer.notepad;

import javax.swing.*;
import javax.swing.event.*;

import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class FormatMenu implements ActionListener, ListSelectionListener {
	MainFrame MF;
	JTextArea text;

	// ����
	JDialog fontDialog;
	JTextField FontT;
	JTextField Shapes;
	JTextField Size;

	@SuppressWarnings("rawtypes")
	JList listFont;
	@SuppressWarnings("rawtypes")
	JList listShapes;
	@SuppressWarnings("rawtypes")
	JList listSize;

	JTextField demo;
	String demoStr;
	JComboBox scripts;

	JButton Yes;
	JButton No;

	Font font;
	int style;
	int size;
	boolean SizeFlag;
	List<Integer> SizeNum;
	List<String> SizeStr;

	public FormatMenu(MainFrame frame) {
		MF = frame;
		text = MF.text;
		buildDialog();
		addListener();
	}

	// ������ĶԻ���
	public void buildDialog() {
		fontDialog = new JDialog(MF, "����");
		fontDialog.setSize(400, 250);
		fontDialog.setLocation(500, 200);
		init();
		DialogPane(fontDialog.getContentPane());
		// fontDialog.setVisible(true);
	}

	public void init() {
		// ���ϵͳ����
		String[] fontName = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		listFont = new JList<>(fontName);

		// ������
		String[] fontShapes = { "����", "����", "��б", "����  ��б" };
		listShapes = new JList<>(fontShapes);
		String[] fontArray = { "8", "9", "10", "11", "12", "14", "16", "18",
				"20", "22", "24", "26", "28", "36", "48", "72", "����", "С��",
				"һ��", "Сһ", "����", "С��", "����", "С��", "�ĺ�", "С��", "���", "С��",
				"����", "С��", "�ߺ�", "�˺�" };
		listSize = new JList<>(fontArray);
		// �ı���
		listShapes.setSelectedIndex(0);
		listSize.setSelectedIndex(0);

		FontT = new JTextField("����");
		Shapes = new JTextField("����");
		Size = new JTextField("8");
		FontT.setEditable(false);
		Shapes.setEditable(false);

		// ʾ��
		demoStr = "���,���±� ";
		demo = new JTextField(demoStr);
		demo.setBorder(BorderFactory.createTitledBorder("ʾ��"));
		demo.setEditable(false);
		demo.setHorizontalAlignment(JTextField.CENTER); // �ı�����
		font = new Font("Myfont", Font.PLAIN, 18);
		demo.setFont(font);

		Yes = new JButton("ȷ��");
		No = new JButton("ȡ��");

		String[] scriptName = { "����", "Ӣ��", "����" };
		scripts = new JComboBox<>(scriptName);
		// scripts.setBorder(BorderFactory.createTitledBorder("�ű�(R):"));

		// ���������С��Ӧ����ֵ
		Integer[] sizeValue = { 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26,
				28, 36, 48, 72, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 10, 9,
				8, 7, 6, 5 };

		// ������List��
		SizeNum = Arrays.asList(sizeValue);
		SizeStr = Arrays.asList(fontArray);
	}

	public void DialogPane(Container pane) {
		// ����
		Box fontBox = Box.createVerticalBox();
		fontBox.setBorder(BorderFactory.createTitledBorder("����(F):"));
		fontBox.add(FontT);
		fontBox.add(new JScrollPane(listFont));

		// ����
		Box shapesBox = Box.createVerticalBox();
		shapesBox.setBorder(BorderFactory.createTitledBorder("����(Y):"));
		shapesBox.add(Shapes);
		shapesBox.add(new JScrollPane(listShapes));

		// ��С
		Box sizeBox = Box.createVerticalBox();
		sizeBox.setBorder(BorderFactory.createTitledBorder("��С(S):"));
		sizeBox.add(Size);
		sizeBox.add(new JScrollPane(listSize));

		// ��ť
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Yes);
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(No);

		// buttonBox+scripts
		Box groupBox = Box.createVerticalBox();
		groupBox.add(scripts);
		groupBox.add(Box.createVerticalStrut(10));
		groupBox.add(buttonBox);

		// ����Box fontBox+shapeBox+sizeBox
		Box MFontBox = Box.createHorizontalBox();
		MFontBox.add(Box.createHorizontalStrut(5));
		MFontBox.add(fontBox);
		MFontBox.add(Box.createHorizontalStrut(5));
		MFontBox.add(shapesBox);
		MFontBox.add(Box.createHorizontalStrut(5));
		MFontBox.add(sizeBox);
		MFontBox.add(Box.createHorizontalStrut(5));

		// ʾ��Box
		Box demoBox = Box.createHorizontalBox();
		demoBox.add(Box.createHorizontalStrut(5));
		demoBox.add(demo);
		demoBox.add(Box.createHorizontalStrut(60));
		demoBox.add(groupBox);
		demoBox.add(Box.createHorizontalStrut(5));
		// demoBox.setSize(100, 100);

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.add(Box.createVerticalStrut(5));
		pane.add(MFontBox);
		pane.add(demoBox);
		pane.add(Box.createVerticalStrut(5));
	}

	public void addListener() {
		MF.newLine.addActionListener(this);
		MF.color.addActionListener(this);
		MF.font.addActionListener(this);

		listFont.addListSelectionListener(this);
		listShapes.addListSelectionListener(this);
		listSize.addListSelectionListener(this);
		Yes.addActionListener(this);
		No.addActionListener(this);

		scripts.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				switch ((String) e.getItem()) {
				case "����":
					demoStr = "���,���±� ";
					break;
				case "Ӣ��":
					demoStr = "Hello,NotePad";
					break;
				case "����":
					demoStr = "4096";
					break;
				}
				demo.setText(demoStr);
			}
		});

		Size.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				SizeFlag = false;
				size = 18; // �����ʼ��С
				setSize();
				font = new Font(FontT.getText(), style, size);
				demo.setFont(font);
			}
		});
	}

	public void setStyle() {
		Shapes.setText((String) listShapes.getSelectedValue());
		String name = Shapes.getText();
		switch (name) {
		case "����":

			style = Font.PLAIN;
			break;
		case "����":

			style = Font.BOLD;
			break;
		case "��б":

			style = Font.ITALIC;
			break;
		case "����  ��б":
			style = Font.BOLD + Font.ITALIC;
			break;
		}
	}

	public void setSize() {
		if (SizeFlag)
			Size.setText((String) listSize.getSelectedValue());
		String sizeN = Size.getText();
		try {
			size = Integer.valueOf(sizeN);
		} catch (NumberFormatException e1) {
			System.out.println("a");
			for (int i = 16; i < SizeStr.size(); i++) {
				if (sizeN.equals(SizeStr.get(i))) {
					size = SizeNum.get(i);
					break;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		if (source == MF.newLine) {
			if (MF.newLine.isSelected()) {
				MF.getContentPane().remove(1);
				MF.state.setSelected(false);
				MF.state.setEnabled(false);
				text.setLineWrap(true);
			} else {
				MF.getContentPane().add(MF.ranks, BorderLayout.SOUTH);
				MF.state.setSelected(true);
				MF.state.setEnabled(true);
				text.setLineWrap(false);
			}
			MF.setVisible(true);
		}

		if (source == MF.font) {
			fontDialog.setVisible(true);
		}

		if (source == MF.color) {
			Color color = Color.black;
			color = JColorChooser.showDialog(MF, "��ɫ", Color.black);
			text.setForeground(color);
		}
		if (source == Yes) {
			text.setFont(font);
			fontDialog.setVisible(false);
		}
		if (source == No) {
			fontDialog.setVisible(false);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object source = e.getSource();
		size = 18;

		if (source == listFont) {
			FontT.setText((String) listFont.getSelectedValue());
		}
		if (source == listShapes) {
			setStyle();

		}
		if (source == listSize) {
			SizeFlag = true;
			setSize();
		}

		font = new Font(FontT.getText(), style, size);
		demo.setFont(font);
	}
}

