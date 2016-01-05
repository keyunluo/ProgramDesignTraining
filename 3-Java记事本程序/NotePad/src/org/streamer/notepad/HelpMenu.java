package org.streamer.notepad;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class HelpMenu implements ActionListener {
	MainFrame MF;
	Desktop desktop;

	public HelpMenu(MainFrame frame) {
		MF = frame;
		MF.state.addActionListener(this);
		MF.vhelp.addActionListener(this);
		MF.about.addActionListener(this);
		desktop = Desktop.getDesktop();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		Object source = e.getSource();
		if (source == MF.state) {
			if (MF.state.isSelected()) {
				MF.getContentPane().add(MF.ranks, BorderLayout.SOUTH);
			} else {
				MF.getContentPane().remove(1);
			}
			MF.setVisible(true);
		}

		if (source == MF.vhelp) {// ����API
			try {
				desktop.browse(new URI(
						"http://windows.microsoft.com/zh-cn/windows/notepad-faq#1TC=windows-7"));
			} catch (IOException | URISyntaxException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}

		if (source == MF.about) {
			String message = "           ���ܺ���ʦ�������ר��ѵ����ҵ\n             Java���±�����\n                                             �����  \n                                       2015.12.18";
			JOptionPane.showMessageDialog(MF, message, "����",
					JOptionPane.DEFAULT_OPTION);
		}
	}

}

