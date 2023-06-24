<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
    String child = request.getParameter("child");
    if ("child1".equals(child)) {
        request.getRequestDispatcher("child1.jsp").forward(request, response);
    } else if ("child2".equals(child)) {
        request.getRequestDispatcher("child2.jsp").forward(request, response);
    } else {
        request.getRequestDispatcher("defaultChild.jsp").forward(request, response);
    }
%>