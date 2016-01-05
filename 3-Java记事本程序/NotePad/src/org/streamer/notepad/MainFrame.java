package org.streamer.notepad;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	JTextArea text;
	JMenuBar menubar;

	// �ļ�(F)
	JMenu file;
	JMenuItem newFile;
	JMenuItem openFile;
	JMenuItem saveFile;
	JMenuItem saveAsFile;
	JMenuItem pageSet;
	JMenuItem print;
	JMenuItem exit;

	// �༭(E)
	JMenu edit;
	JMenuItem cancel;
	JMenuItem cut;
	JMenuItem copy;
	JMenuItem paste;
	JMenuItem del;
	JMenuItem find;
	JMenuItem findnext;
	JMenuItem replace;
	JMenuItem turn;
	JMenuItem select;
	JMenuItem date;

	// ��ʽ(O)
	JMenu from;
	JCheckBoxMenuItem newLine; // ����ѡ��Ĳ˵���
	JMenuItem font;
	JMenuItem color;

	// �鿴(V) & ����(H)
	JMenu view;
	JCheckBoxMenuItem state;

	JMenu help;
	JMenuItem vhelp;
	JMenuItem about;

	// ʵʱ����
	int col;
	int row;
	JLabel ranks;

	JScrollPane ScrollPane;

	// �����˵�
	JPopupMenu popupMenu;
	JMenuItem popCancel;
	JMenuItem popCut;
	JMenuItem popCopy;
	JMenuItem popPaste;
	JMenuItem popDel;
	JMenuItem popSelect;

	public MainFrame() {
		setTitle("���±�");
		setSize(680, 400);
		setLocation(370, 150);
	
		// ͼ��
		Image image = Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("notepad.png"));
		setIconImage(image);

		buildPane();
		setVisible(true);
		Operation();

	}

	public void buildPane() {
		ScrollPane = new JScrollPane();
		ScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		ScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		text = new JTextArea();

		menubar = new JMenuBar();
		ranks = new JLabel("�� " + row + " �� , �� " + col + " ��            ",
				JLabel.RIGHT);
		ScrollPane.setViewportView(text);

		this.getContentPane().add(ScrollPane, BorderLayout.CENTER);
		this.getContentPane().add(ranks, BorderLayout.SOUTH);
		setJMenuBar(menubar);

		// �˵�
		FileMenu();
		EditMenu();
		fromMenu();
		view_helpMenu();
		PopMenu();
	}

	public void Operation() {
		new FileMenu(this);
		new EditMenu(this);
		new FormatMenu(this);
		new HelpMenu(this);
	}

	public void FileMenu() {
		file = new JMenu("�ļ�(F)");
		newFile = new JMenuItem("�½�(N)");
		openFile = new JMenuItem("��(O)...");
		saveFile = new JMenuItem("����(S)");
		saveAsFile = new JMenuItem("���Ϊ(A)...");
		pageSet = new JMenuItem("ҳ������(U)...");
		print = new JMenuItem("��ӡ(P)...");
		exit = new JMenuItem("�˳�(E)");

		// �����ļ��˵�
		file.add(newFile);
		file.add(openFile);
		file.add(openFile);
		file.add(saveFile);
		file.add(saveAsFile);
		file.addSeparator();
		file.add(pageSet);
		file.add(print);
		file.addSeparator();
		file.add(exit);
		

		// ���ò˵���ݼ� ALt+F
		file.setMnemonic('F');

		// ���ò˵������Ͽ�ݼ� ��"Ctrl"����'N'�ȼ� Ctrl+N
		newFile.setAccelerator(KeyStroke
				.getKeyStroke('N', InputEvent.CTRL_MASK));
		openFile.setAccelerator(KeyStroke.getKeyStroke('O',
				InputEvent.CTRL_MASK));
		saveFile.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_MASK));
		print.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK));

		menubar.add(file);

	}

	public void EditMenu() {
		edit = new JMenu("�༭(E)");
		cancel = new JMenuItem("����(U)");
		cut = new JMenuItem("����(T)");
		copy = new JMenuItem("����(C)");
		paste = new JMenuItem("ճ��(P)");
		del = new JMenuItem("ɾ��(L)");
		find = new JMenuItem("����(F)...");
		findnext = new JMenuItem("������һ��(N)...");
		replace = new JMenuItem("�滻(R)...");
		turn = new JMenuItem("ת��(G)...");
		select = new JMenuItem("ȫѡ(A");
		date = new JMenuItem("ʱ��/����(D)");

		// ����༭�˵�
		edit.add(cancel);
		edit.addSeparator();
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(del);
		edit.addSeparator();
		edit.add(find);
		edit.add(findnext);
		edit.add(replace);
		edit.add(turn);
		edit.addSeparator();
		edit.add(select);
		edit.add(date);

		// ���ÿ�ݼ�
		edit.setMnemonic('E');
		cancel.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		del.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
				InputEvent.CTRL_MASK));
		find.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK));
		findnext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,
				InputEvent.CTRL_MASK));
		replace.setAccelerator(KeyStroke
				.getKeyStroke('H', InputEvent.CTRL_MASK));
		turn.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.CTRL_MASK));
		select.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		date.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,
				InputEvent.CTRL_MASK));

		menubar.add(edit);
	}

	public void fromMenu() {
		from = new JMenu("��ʽ(O)");
		newLine = new JCheckBoxMenuItem("�Զ�����(W)");
		font = new JMenuItem("����(F)");
		color = new JMenuItem("��ɫ(C)");

		from.setMnemonic('O');
		from.add(newLine);
		from.add(font);
		from.add(color);

		menubar.add(from);
	}

	public void view_helpMenu() {
		// �鿴�˵�
		view = new JMenu("�鿴(V)");
		state = new JCheckBoxMenuItem("״̬��(S)");
		state.setSelected(true);

		view.add(state);
		view.setMnemonic('V');
		menubar.add(view);

		// �����˵�
		help = new JMenu("����(H)");
		vhelp = new JMenuItem("�鿴����(H)");
		about = new JMenuItem("���ڼ��±�(A)");

		help.add(vhelp);
		help.add(about);
		help.setMnemonic('H');
		menubar.add(help);
	}

	// �����˵�
	public void PopMenu() {
		popupMenu = new JPopupMenu();
		popCancel = new JMenuItem("����(U)");
		popCut = new JMenuItem("����(T)");
		popCopy = new JMenuItem("����(C)");
		popPaste = new JMenuItem("ճ��(P)");
		popDel = new JMenuItem("ɾ��(D)");
		popSelect = new JMenuItem("ȫѡ(A)");

		popupMenu.add(popCancel);
		popupMenu.addSeparator();
		popupMenu.add(popCut);
		popupMenu.add(popCopy);
		popupMenu.add(popPaste);
		popupMenu.add(popDel);
		popupMenu.addSeparator();
		popupMenu.add(popSelect);

		text.setComponentPopupMenu(popupMenu); // ��popupMenuΪ�ı����ĵ����˵�
	}
}
