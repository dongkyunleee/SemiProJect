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



@WebServlet("/MemberCheck")
public class MemberCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
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
		
		System.out.println("회원가입체크" + getId);
		
		MemberDAO dao = new MemberDAO();
		boolean isNotReg = dao.regIdCheck(getId);  // isNotReg = true 가입되어있지 않다. 
		
		PrintWriter out = response.getWriter();
        JSONObject resJson = new JSONObject();
        if(isNotReg) resJson.put("result", "가입체크 OK");
        else resJson.put("result", "가입체크 NOK");
        out.print(resJson);	
	}
	
}

























