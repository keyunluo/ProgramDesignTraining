package org.streamer.studentinfo.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.streamer.studentinfo.dao.ClassDao;
import org.streamer.studentinfo.dao.GradeDao;
import org.streamer.studentinfo.dao.StudentDao;
import org.streamer.studentinfo.model.Class;
import org.streamer.studentinfo.model.Grade;
import org.streamer.studentinfo.util.DbUtil;
import org.streamer.studentinfo.util.NavUtil;
import org.streamer.studentinfo.util.ResponseUtil;
import org.streamer.studentinfo.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

public class ClassAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DbUtil dbUtil = new DbUtil();
	private ClassDao classDao = new ClassDao();
	private GradeDao gradeDao = new GradeDao();
	private StudentDao studentDao = new StudentDao();
	private List<Class> classList = new ArrayList<Class>();
	private List<Grade> gradeList = new ArrayList<Grade>();
	private String mainPage;
	private String navCode;

	private String classId;
	private Class c;

	private String s_gradeId;

	public List<Class> getClassList() {
		return classList;
	}

	public void setClassList(List<Class> classList) {
		this.classList = classList;
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

	public List<Grade> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Class getC() {
		return c;
	}

	public void setC(Class c) {
		this.c = c;
	}

	public String getS_gradeId() {
		return s_gradeId;
	}

	public void setS_gradeId(String s_gradeId) {
		this.s_gradeId = s_gradeId;
	}

	public String list() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			classList = classDao.classList(con, null);
			navCode = NavUtil.getNavgation("班级信息管理", "班级维护");
			mainPage = "class/classList.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String delete() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject resultJson = new JSONObject();
			boolean exist = studentDao.existStudentByClassId(con, classId);
			if (exist) {
				resultJson.put("error", "班级下面有学生，不能删除！");
			} else {
				classDao.classDelete(con, classId);
				resultJson.put("success", true);
			}
			ResponseUtil.write(resultJson, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public String preSave() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			gradeList = gradeDao.gradeList(con);
			if (StringUtil.isNotEmpty(classId)) {
				c = classDao.getClassById(con, classId);
				navCode = NavUtil.getNavgation("系统管理", "班级修改");
			} else {
				navCode = NavUtil.getNavgation("系统管理", "班级添加");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mainPage = "class/classSave.jsp";
		return SUCCESS;
	}

	public String save() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if (StringUtil.isNotEmpty(classId)) {
				c.setClassId(Integer.parseInt(classId));
				classDao.classUpdate(con, c);
			} else {
				classDao.classAdd(con, c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.getCon();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "save";
	}

	public String getClassByGradeId() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			Class s_class = new Class();
			s_class.setGradeId(Integer.parseInt(s_gradeId));
			List<Class> classList = classDao.classList(con, s_class);
			JSONArray jsonArray = JSONArray.fromObject(classList);
			ResponseUtil.write(jsonArray, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
