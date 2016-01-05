package org.streamer.studentinfo.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.streamer.studentinfo.dao.UserDao;
import org.streamer.studentinfo.model.User;
import org.streamer.studentinfo.util.DbUtil;
import org.streamer.studentinfo.util.NavUtil;
import org.streamer.studentinfo.util.ResponseUtil;
import org.streamer.studentinfo.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <result name="error">/login.jsp</result>
	<result name="logOut" type="redirect">/login.jsp</result>
	<result name="success">/main.jsp</result>
 * @author jia
 *
 */
public class UserAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 558001015548444750L;
	private HttpServletRequest request;
	private User user;
	private String error;
	private String imageCode;
	private int userId;
	private String password;
	private String mainPage;
	private String navCode;
	private DbUtil dbUtil = new DbUtil();
	private UserDao userDao = new UserDao();

	public String login() {
		HttpSession session = request.getSession();
		if (StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())) {
			error = "用户名或密码为空！";
			return ERROR;
		}
		if (StringUtil.isEmpty(imageCode)) {
			error = "验证码为空！";
			return ERROR;
		}
		if (!imageCode.equals(session.getAttribute("sRand"))) {
			error = "验证码错误！";
			return ERROR;
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			User currentUser = userDao.login(con, user);
			if (currentUser == null) {
				error = "用户名或密码错误！";
				return ERROR;
			} else {
				session.setAttribute("currentUser", currentUser);//set session
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String register(){
		if (StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())) {
			error = "用户名或密码为空！";
			return "register";
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			boolean currentUser = userDao.register(con, user);
			if (currentUser) {
      		  return "login";
			} 
			else {
				return "register";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "register";
	}
	
	public String preSave() {
		navCode = NavUtil.getNavgation("系统管理", "修改密码");
		mainPage = "user/modifyPassword.jsp";
		return SUCCESS;
	}

	public String save() {
		User user = new User();
		user.setUserId(userId);
		user.setPassword(password);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int updateNums = userDao.modifyPassword(con, user);
			JSONObject resultJson = new JSONObject();
			if (updateNums > 0) {
				resultJson.put("success", true);
			} else {
				resultJson.put("errorMsg", "修改密码失败");
			}
			ResponseUtil.write(resultJson, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String logOut() {
		HttpSession session = request.getSession();
		session.removeAttribute("currentUser");
		return "logOut";
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getNavCode() {
		return navCode;
	}

	public void setNavCode(String navCode) {
		this.navCode = navCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

}
