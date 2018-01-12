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

/**
 * 查询数据结果的页面
 * 
 * @author Poppy
 *
 */
@WebServlet(name = "selectServlet", urlPatterns = "/selectServlet")
public class SelectServlet extends HttpServlet {
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
			List<Investigation> selectInfo;
			try {
				if (jsonString != null) {
					SelectBean selectBean = JsonUtil.getJsonSelectJson(jsonString);
					if (selectBean.getLargeArea().equals("产品部")) {
						selectInfo = DataBaseOperaUtil.getSelectInfo2(selectBean);
					} else {
						selectInfo = DataBaseOperaUtil.getSelectInfo1(selectBean);
					}
					selectJsonBean.setCode(200);
					if (selectInfo.size() > 0) {
						selectJsonBean.setMsg("success");
						selectJsonBean.setSelectInfo(selectInfo);
					} else {
						selectJsonBean.setMsg("查询没有结果!");
						selectJsonBean.setSelectInfo(new ArrayList<Investigation>());
					}
					JSONObject jsonObject = JSONObject.fromObject(selectJsonBean);
					pw.print(jsonObject.toString());
				} else {
					selectJsonBean.setCode(100);
					selectJsonBean.setMsg("加载出错，F5刷新重试!");
					JSONObject jsonObject = JSONObject.fromObject(selectJsonBean);
					pw.print(jsonObject.toString());
				}
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
