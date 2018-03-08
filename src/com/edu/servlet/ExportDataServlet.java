package com.edu.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.edu.bean.Investigation;
import com.edu.bean.SelectBean;
import com.edu.util.DataBaseOperaUtil;
import com.edu.util.ExportUtils;
import com.edu.util.JsonUtil;

@WebServlet(name = "exportDataServlet", urlPatterns = "/exportDataServlet")
public class ExportDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("UTF-8");
		String jsonString = req.getParameter("name");
		SelectBean selectBean = JsonUtil.getJsonSelectJson(jsonString);
		try {
			List<Investigation> exportList = DataBaseOperaUtil.selectExportData(selectBean);
			// ���������ʼ����
			for (Investigation investigation : exportList) {
				String startData = selectBean.getStartDate().replace("-", "/");
				String endData = selectBean.getEndDate().replace("-", "/");
				investigation.setStartEndData(startData+"-"+endData);
			}
			String[] titles = new String[] { "У��", "��ʦ����", "��ɫ", "רҵ", "ƽ����","��ʼ����", "��ʦǩ��" };
			expressTeacher(req, resp, titles, exportList, selectBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ������ʦ��
	 * 
	 * @param resp
	 * @param resp
	 * @param headName
	 * @param exportList
	 * @throws IOException
	 */
	private void expressTeacher(HttpServletRequest req, HttpServletResponse resp, String[] headName,
			List<Investigation> exportList, SelectBean selectBean) throws IOException {
		SimpleDateFormat ss = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = ss.format(new Date());

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheets = wb.createSheet("sheet0"); // ����excel��������

		HSSFCellStyle headerStyle = (HSSFCellStyle) wb.createCellStyle();// ����������ʽ
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ���ô�ֱ����
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����ˮƽ����
		HSSFFont headerFont = (HSSFFont) wb.createFont(); // ����������ʽ
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // ����Ӵ�
		headerFont.setFontName("Times New Roman"); // ������������
		headerFont.setFontHeightInPoints((short) 12); // ���������С
		headerStyle.setFont(headerFont); // Ϊ������ʽ����������ʽ

		// ---------------��һ����ʽ-----------------
		HSSFCellStyle cell_Style = wb.createCellStyle();// ����������ʽ
		cell_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ�������
		cell_Style.setWrapText(true); // ����Ϊ�Զ�����
		HSSFFont cell_Font = wb.createFont();
		cell_Font.setFontName("����");
		cell_Font.setFontHeightInPoints((short) 10);
		cell_Style.setFont(cell_Font);
		cell_Style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
		cell_Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
		cell_Style.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
		cell_Style.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�

		// ------------�ڶ�����ʽ--------------------------
		HSSFCellStyle cell_Style_new = wb.createCellStyle();// ����������ʽ
		cell_Style_new.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style_new.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ�������
		cell_Style_new.setWrapText(true); // ����Ϊ�Զ�����
		cell_Style_new.setFont(cell_Font);
		cell_Style_new.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
		cell_Style_new.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
		cell_Style_new.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
		cell_Style_new.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�

		// ------------��������ʽ--------------------------
		HSSFCellStyle cell_Style_red = wb.createCellStyle();// ����������ʽ
		cell_Style_red.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style_red.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ�������
		cell_Style_red.setWrapText(true); // ����Ϊ�Զ�����
		cell_Style_red.setFont(cell_Font);
		cell_Style_red.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
		cell_Style_red.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
		cell_Style_red.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
		cell_Style_red.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�

		String Column[] = {"sch_Name", "tea_Name", "role_Level", "cus_Name","startEndData", "average"};// ��id
		ExportUtils.outputHeaders(headName, sheets, headerStyle);// ���ɱ�ͷ
		ExportUtils.outputColumn(Column, exportList, sheets, 1, cell_Style, cell_Style_new, cell_Style_red);// �����б�����

		// ----------------------------�������Ĳ���-----------------------------
		String path = req.getRealPath("/xlsx");
		String fileName = null;
		if ("".equals(selectBean.getLargeArea()) || "��ѡ��".equals(selectBean.getLargeArea())) {
			fileName = "ȫ��-" + selectBean.getRole_Level() + "-" + dateString + "-����ȵ�������";
		} else if ("".equals(selectBean.getSchName()) || "��ѡ��".equals(selectBean.getSchName())) {
			fileName = selectBean.getLargeArea() + "-" + selectBean.getRole_Level() + "-" + dateString + "-����ȵ�������";
		} else {
			fileName = selectBean.getLargeArea() + "-" + selectBean.getSchName() + "-" + selectBean.getRole_Level()
					+ "-" + dateString + "-����ȵ�������";
		}

		String filePath = path + "/" + fileName + ".xls";
		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		fos.flush();
		fos.close();

		PrintWriter pw = resp.getWriter();// ��Ӧ����������
		pw.println(fileName + ".xls");
		pw.flush();
		pw.close();

	}


}
