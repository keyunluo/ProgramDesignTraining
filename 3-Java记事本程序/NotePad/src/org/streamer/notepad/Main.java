package org.streamer.notepad;

import javax.swing.*;

public class Main {

	public static void main(String args[]) {
		//����windows������ʽ
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Windows�۸�
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		new MainFrame();
	}

}
