package org.streamer.robustregister;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.io.IOException;
import java.text.DecimalFormat;

public class RobustRegister {
	public static void pressKey(Robot robot, int keyvalue) {
		robot.keyPress(keyvalue);
		robot.keyRelease(keyvalue);
		robot.delay(100);
	}
	public static void pressKeyWithCtrl(Robot robot, int keyvalue) {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(keyvalue);
		robot.keyRelease(keyvalue);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(400);
	}
	public static void closeApplication(Robot robot) {

		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_F4);
		//for linux.
		// robot.keyPress(KeyEvent.VK_ALT);
		// robot.keyPress(KeyEvent.VK_W);
		// robot.keyRelease(KeyEvent.VK_ALT);
		// robot.keyRelease(KeyEvent.VK_W);
		robot.keyPress(KeyEvent.VK_N);
		robot.keyRelease(KeyEvent.VK_N);
	}

	public static void fillNamePassword(Robot robot,String number){
		//! 切换到用户名处
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		for (int i=0;i<number.length();i++){
			pressKey(robot, number.charAt(i));
		}
		
		//! 切换到密码处
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		for (int i=0;i<number.length();i++){
			pressKey(robot, number.charAt(i));
		}
		
		robot.delay(200);
		//! 切换到注册处
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		
		robot.delay(200);
		//! 提交
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		robot.delay(2000);	
	}
	

	
	public static void main(String[] args) throws IOException {
		try {
			Robot robot = new Robot();
			String url="http://localhost:8080/StudentInfoSystem/register.jsp";
			String base="1612001";
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+url);
			robot.delay(8000);
			for (int i=1; i<=32;i++){
				
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+url);				
				robot.delay(2000);
				String numString=new DecimalFormat("00").format(i);
				String user=base+numString;
				fillNamePassword(robot, user);
			    System.out.println("成功注册用户: "+user+"  密码: "+user);	
			    pressKeyWithCtrl(robot, KeyEvent.VK_W);
			}
			closeApplication(robot);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
