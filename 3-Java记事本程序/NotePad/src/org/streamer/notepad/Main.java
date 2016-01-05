package org.streamer.notepad;

import javax.swing.*;

public class Main {

	public static void main(String args[]) {
		//设置windows本地样式
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Windows观感
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		new MainFrame();
	}

}
