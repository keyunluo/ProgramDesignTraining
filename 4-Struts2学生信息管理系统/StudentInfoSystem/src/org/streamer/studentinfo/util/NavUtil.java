package org.streamer.studentinfo.util;

public class NavUtil {

	/**
	 * 获取导航条html
	 * @param mainMenu 父菜单
	 * @param subMenu 子菜单
	 * @return
	 */
	public static String getNavgation(String mainMenu, String subMenu) {
		StringBuffer navCode = new StringBuffer();
		navCode.append("当前位置：&nbsp;&nbsp;");
		navCode.append("<a href='" + PropertiesUtil.getValue("projectName") + "/main.jsp'>主页</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='#'>" + mainMenu + "</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='#'>" + subMenu + "</a>");
		return navCode.toString();
	}
}
