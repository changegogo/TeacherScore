package com.edu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.edu.bean.Investigation;
import com.edu.bean.SelectBean;
import com.edu.bean.SelectJsonBean;
import com.edu.util.DataBaseOperaUtil;
import com.edu.util.JsonUtil;

import net.sf.json.JSONObject;

@WebServlet(name = "selectExportTableServlet", urlPatterns = "/selectExportTableServlet")
public class SelectExportTableServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("UTF-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setContentType("application/json;charset=utf-8");
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		if (!(username == null || "".equals(username))) {
			String jsonString = req.getParameter("name");
			PrintWriter pw = resp.getWriter();
			SelectJsonBean selectJsonBean = new SelectJsonBean();
			try {
				SelectBean selectBean = JsonUtil.getJsonSelectJson(jsonString);
				List<Investigation> selectInfo = DataBaseOperaUtil.selectExportData(selectBean);
				selectJsonBean.setCode(200);
				if (selectInfo.size() > 0) {
					selectJsonBean.setMsg("success");
					// �޸���ʼ����
					String startData = selectBean.getStartDate().replace("-", "/");
					String endData = selectBean.getEndDate().replace("-", "/");
					selectJsonBean.setStartEndData(startData+"-"+endData);
					selectJsonBean.setSelectInfo(selectInfo);
				} else {
					selectJsonBean.setMsg("��ѯû�н��!");
					selectJsonBean.setSelectInfo(new ArrayList<Investigation>());
				}
				JSONObject jsonObject = JSONObject.fromObject(selectJsonBean);
				pw.print(jsonObject.toString());
			} catch (Exception e) {
				selectJsonBean.setCode(100);
				selectJsonBean.setMsg("fail");
				JSONObject jsonObject = JSONObject.fromObject(selectJsonBean);
				pw.print(jsonObject.toString());
				e.printStackTrace();
			} finally {
				pw.close();
			}
		} else {
			resp.sendRedirect("Login.html");
		}
	}

}
