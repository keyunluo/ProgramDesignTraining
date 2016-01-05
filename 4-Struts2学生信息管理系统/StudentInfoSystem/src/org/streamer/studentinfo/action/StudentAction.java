package org.streamer.studentinfo.action;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.streamer.studentinfo.dao.ClassDao;
import org.streamer.studentinfo.dao.DataDicDao;
import org.streamer.studentinfo.dao.GradeDao;
import org.streamer.studentinfo.dao.StudentDao;
import org.streamer.studentinfo.model.Class;
import org.streamer.studentinfo.model.DataDic;
import org.streamer.studentinfo.model.Grade;
import org.streamer.studentinfo.model.PageBean;
import org.streamer.studentinfo.model.Student;
import org.streamer.studentinfo.util.DateUtil;
import org.streamer.studentinfo.util.DbUtil;
import org.streamer.studentinfo.util.NavUtil;
import org.streamer.studentinfo.util.PageUtil;
import org.streamer.studentinfo.util.PropertiesUtil;
import org.streamer.studentinfo.util.ResponseUtil;
import org.streamer.studentinfo.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 学生控制器
 * <result name="success">/main.jsp</result>
	<result name="save" type="redirectAction">student!list</result>
 * @author jia
 * 
 */
public class StudentAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private DbUtil dbUtil = new DbUtil();
	private StudentDao studentDao = new StudentDao();
	private DataDicDao dataDicDao = new DataDicDao();
	private GradeDao gradeDao = new GradeDao();
	private ClassDao classDao = new ClassDao();
	private List<Student> studentList = new ArrayList<Student>();
	private String mainPage;
	private String navCode;
	private List<DataDic> s_sexDataDicList;
	private List<DataDic> s_nationDataDicList;
	private List<DataDic> sexDataDicList;
	private List<DataDic> nationDataDicList;
	private List<DataDic> zzmmDataDicList;
	private List<Class> classList;
	private List<Grade> s_gradeList;
	private List<Class> s_classList;
	private Student s_student;
	private String page;
	private int total;
	private String pageCode;
	private Student student;
	private String studentId;
	//上传使用
	private File stuPic;
	private String stuPicFileName;

	public String list() {
		HttpSession session = request.getSession();
		
		//没传页码
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		
		//搜索了
		if (s_student != null) {
			session.setAttribute("s_student", s_student);
		} else {//没有搜索
			Object o = session.getAttribute("s_student");
			if (o != null) {
				s_student = (Student) o;
			} else {
				s_student = new Student();//没搜索创建一个没有条件对象
			}
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			studentList = studentDao.studentList(con, s_student, pageBean);

			navCode = NavUtil.getNavgation("学生信息管理", "学生维护");
			total = studentDao.studentCount(con, s_student);
			pageCode = PageUtil.genPagation(request.getContextPath() + "/student!list", total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			DataDic s_dataDic = new DataDic();
			
			s_dataDic.setDdTypeName("性别");
			s_sexDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);//性别下拉框

			s_dataDic.setDdTypeName("名族");
			s_nationDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);//民族下拉框

			s_gradeList = gradeDao.gradeList(con);//年级 下拉框

			if (s_student != null && s_student.getGradeId() != -1) {
				Class s_class = new Class();
				s_class.setGradeId(s_student.getGradeId());
				s_classList = classDao.classList(con, s_class);//年级下所有的班级
			}

			mainPage = "student/studentList.jsp";
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

	//学生详细信息
	public String show() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			student = studentDao.getStudentById(con, studentId);
			navCode = NavUtil.getNavgation("学生信息管理", "学生详细信息");
			mainPage = "student/studentShow.jsp";
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

	public String preSave() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if (StringUtil.isNotEmpty(studentId)) {
				student = studentDao.getStudentById(con, studentId);
			}
			DataDic s_dataDic = new DataDic();
			
			//准备下拉框数据
			s_dataDic.setDdTypeName("性别");
			sexDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);
			s_dataDic.setDdTypeName("名族");
			nationDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);
			s_dataDic.setDdTypeName("政治面貌");
			zzmmDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);
			classList = classDao.classList(con, null);//所有班级
			
			if (StringUtil.isNotEmpty(studentId)) {
				navCode = NavUtil.getNavgation("学生信息管理", "学生修改");
			} else {
				navCode = NavUtil.getNavgation("学生信息管理", "学生添加");
			}
			mainPage = "student/studentSave.jsp";
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

	//保存 上传
	public String save() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if (stuPic != null) {
				String imageName = DateUtil.getCurrentDateStr();
				String realPath = ServletActionContext.getServletContext().getRealPath("/userImage");
				String imageFile = imageName + "." + stuPicFileName.split("\\.")[1];
				File saveFile = new File(realPath, imageFile);
				FileUtils.copyFile(stuPic, saveFile);
				student.setStuPic(imageFile);
			} else if (StringUtil.isEmpty(student.getStuPic())) {
				student.setStuPic("");
			}
			if (StringUtil.isNotEmpty(studentId)) {
				student.setStudentId(studentId);
				studentDao.studentUpdate(con, student);
			} else {
				studentDao.studentAdd(con, student);
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
		return "save";
	}

	public String delete() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject resultJson = new JSONObject();
			studentDao.studentDelete(con, studentId);
			resultJson.put("success", true);
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

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public File getStuPic() {
		return stuPic;
	}

	public void setStuPic(File stuPic) {
		this.stuPic = stuPic;
	}

	public String getStuPicFileName() {
		return stuPicFileName;
	}

	public void setStuPicFileName(String stuPicFileName) {
		this.stuPicFileName = stuPicFileName;
	}

	public List<DataDic> getZzmmDataDicList() {
		return zzmmDataDicList;
	}

	public void setZzmmDataDicList(List<DataDic> zzmmDataDicList) {
		this.zzmmDataDicList = zzmmDataDicList;
	}

	public List<DataDic> getSexDataDicList() {
		return sexDataDicList;
	}

	public void setSexDataDicList(List<DataDic> sexDataDicList) {
		this.sexDataDicList = sexDataDicList;
	}

	public List<DataDic> getNationDataDicList() {
		return nationDataDicList;
	}

	public void setNationDataDicList(List<DataDic> nationDataDicList) {
		this.nationDataDicList = nationDataDicList;
	}

	public List<Class> getClassList() {
		return classList;
	}

	public void setClassList(List<Class> classList) {
		this.classList = classList;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Student getS_student() {
		return s_student;
	}

	public void setS_student(Student s_student) {
		this.s_student = s_student;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
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

	public List<DataDic> getS_sexDataDicList() {
		return s_sexDataDicList;
	}

	public void setS_sexDataDicList(List<DataDic> s_sexDataDicList) {
		this.s_sexDataDicList = s_sexDataDicList;
	}

	public List<DataDic> getS_nationDataDicList() {
		return s_nationDataDicList;
	}

	public void setS_nationDataDicList(List<DataDic> s_nationDataDicList) {
		this.s_nationDataDicList = s_nationDataDicList;
	}

	public List<Grade> getS_gradeList() {
		return s_gradeList;
	}

	public void setS_gradeList(List<Grade> s_gradeList) {
		this.s_gradeList = s_gradeList;
	}

	public List<Class> getS_classList() {
		return s_classList;
	}

	public void setS_classList(List<Class> s_classList) {
		this.s_classList = s_classList;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

}
