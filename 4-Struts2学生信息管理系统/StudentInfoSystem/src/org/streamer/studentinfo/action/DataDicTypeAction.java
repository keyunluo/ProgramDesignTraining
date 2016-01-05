package org.streamer.studentinfo.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.streamer.studentinfo.dao.DataDicDao;
import org.streamer.studentinfo.dao.DataDicTypeDao;
import org.streamer.studentinfo.model.DataDicType;
import org.streamer.studentinfo.util.DbUtil;
import org.streamer.studentinfo.util.NavUtil;
import org.streamer.studentinfo.util.ResponseUtil;
import org.streamer.studentinfo.util.StringUtil;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class DataDicTypeAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DbUtil dbUtil = new DbUtil();
	private DataDicTypeDao dataDicTypeDao = new DataDicTypeDao();
	private DataDicDao dataDicDao = new DataDicDao();
	private List<DataDicType> dataDicTypeList = new ArrayList<DataDicType>();

	private String mainPage;
	private String navCode;
	private String ddTypeId;
	private DataDicType dataDicType;

	public List<DataDicType> getDataDicTypeList() {
		return dataDicTypeList;
	}

	public void setDataDicTypeList(List<DataDicType> dataDicTypeList) {
		this.dataDicTypeList = dataDicTypeList;
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

	public String getDdTypeId() {
		return ddTypeId;
	}

	public void setDdTypeId(String ddTypeId) {
		this.ddTypeId = ddTypeId;
	}

	public DataDicType getDataDicType() {
		return dataDicType;
	}

	public void setDataDicType(DataDicType dataDicType) {
		this.dataDicType = dataDicType;
	}

	public String list() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			dataDicTypeList = dataDicTypeDao.dataDicTypeList(con);
			navCode = NavUtil.getNavgation("系统管理", "数据字典类别维护");
			mainPage = "dataDicType/dataDicTypeList.jsp";
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

	public String preSave() {
		if (StringUtil.isNotEmpty(ddTypeId)) {
			Connection con = null;
			try {
				con = dbUtil.getCon();
				dataDicType = dataDicTypeDao.getDataDicTypeById(con, ddTypeId);
				navCode = NavUtil.getNavgation("系统管理", "数据字典类别修改");
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
		} else {
			navCode = NavUtil.getNavgation("系统管理", "数据字典类别添加");
		}
		mainPage = "dataDicType/dataDicTypeSave.jsp";
		return SUCCESS;
	}

	public String save() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if (StringUtil.isNotEmpty(ddTypeId)) {
				dataDicType.setDdTypeId(Integer.parseInt(ddTypeId));
				dataDicTypeDao.dataDicTypeUpdate(con, dataDicType);
			} else {
				dataDicTypeDao.dataDicTypeAdd(con, dataDicType);
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
		return "typeSave";
	}

	public String delete() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject resultJson = new JSONObject();
			boolean exist = dataDicDao.existDataDicByTypeId(con, ddTypeId);
			if (exist) {
				resultJson.put("error", "数据字典类别下面有数据，不能删除！");
			} else {
				dataDicTypeDao.dataDicTypeDelete(con, ddTypeId);
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

}
