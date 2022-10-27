package com.ISOUR.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.ISOUR.Common.Common;
import com.ISOUR.DAO.MemberDAO;




@WebServlet("/MemberRegServlet")
public class MemberRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Common.corsResSet(response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Common.corsResSet(response);
		StringBuffer sb = Common.reqStringBuff(request);
		JSONObject jsonObj = Common.getJsonObj(sb);
		String getId = (String)jsonObj.get("id");
		String getPwd = (String)jsonObj.get("pwd");
		String getName = (String)jsonObj.get("name");
		String getMail = (String)jsonObj.get("mail");
		
		MemberDAO dao = new MemberDAO();
		boolean rstComplete = dao.memberRegister(getId, getPwd, getName, getMail);
		
		PrintWriter out = response.getWriter();
		JSONObject resJson = new JSONObject();
		
		System.out.println("여기까지 와라....Reg");
		
		if(rstComplete) resJson.put("result", "OK");
		else resJson.put("result", "NOK");
		out.print(resJson);
	}
}