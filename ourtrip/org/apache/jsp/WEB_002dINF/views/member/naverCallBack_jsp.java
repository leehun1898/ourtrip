/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.50
 * Generated at: 2020-03-27 07:53:22 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class naverCallBack_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<script type=\"text/javascript\" src=\"https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js\" charset=\"utf-8\"></script>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<title>Naver Login Callback</title>\r\n");
      out.write("<style>\r\n");
      out.write(".wrap-loading{\r\n");
      out.write("    position: fixed;\r\n");
      out.write("    left:0;\r\n");
      out.write("    right:0;\r\n");
      out.write("    top:0;\r\n");
      out.write("    bottom:0;\r\n");
      out.write("    z-index: 10;\r\n");
      out.write("    /* not in ie */\r\n");
      out.write("    background:  rgba(0,0,0,0.2); \r\n");
      out.write("    /* ie */\r\n");
      out.write("    filter: progid:DXImageTransform.Microsoft.Gradient(startColorstr='#20000000',endColorstr='#20000000');\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".wrap-loading div{ /*로딩이미지*/\r\n");
      out.write("    position: fixed;\r\n");
      out.write("    top:50%;\r\n");
      out.write("    left:50%;\r\n");
      out.write("    margin-left: -21px;\r\n");
      out.write("    margin-top: -21px;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"wrap-loading\" id=\"loading\">\r\n");
      out.write("        <div><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/images/loadingBar.gif\"/></div>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("\t<script src=\"https://code.jquery.com/jquery-3.4.1.min.js\"\r\n");
      out.write("\t\tintegrity=\"sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=\"\r\n");
      out.write("\t\tcrossorigin=\"anonymous\"></script>\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\tvar naverLogin = new naver.LoginWithNaverId(\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tclientId: \"cajbBEXn_EXigNoRN2Oc\",\r\n");
      out.write("    \t\t\tcallbackUrl: \"http://localhost:8080/ourtrip/member/naverCallBack\",\r\n");
      out.write("\t\t\t\tisPopup: true,\r\n");
      out.write("\t\t\t\tcallbackHandle: true\r\n");
      out.write("\t\t\t\t/* callback 페이지가 분리되었을 경우에 callback 페이지에서는 callback처리를 해줄수 있도록 설정합니다. */\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* (3) 네아로 로그인 정보를 초기화하기 위하여 init을 호출 */\r\n");
      out.write("\t\tnaverLogin.init();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* (4) Callback의 처리. 정상적으로 Callback 처리가 완료될 경우 main page로 redirect(또는 Popup close) */\r\n");
      out.write("\t\twindow.addEventListener('load', function () {\r\n");
      out.write("\t\t\tnaverLogin.getLoginStatus(function (status) {\r\n");
      out.write("\t\t\t\tif (status) {\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t/* (5) 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 */\r\n");
      out.write("\t\t\t\t\tvar email = naverLogin.user.getEmail();\r\n");
      out.write("\t\t\t\t\tif( email == undefined || email == null) {\r\n");
      out.write("\t\t\t\t\t\talert(\"이메일은 필수정보입니다. 정보제공을 동의해주세요.\");\r\n");
      out.write("\t\t\t\t\t\t/* (5-1) 사용자 정보 재동의를 위하여 다시 네아로 동의페이지로 이동함 */\r\n");
      out.write("\t\t\t\t\t\tnaverLogin.reprompt();\r\n");
      out.write("\t\t\t\t\t\treturn;\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t$.ajax({\r\n");
      out.write("\t                    \turl : \"naverLogin\",\r\n");
      out.write("\t                    \ttype : \"POST\",\r\n");
      out.write("\t                    \tdata : {memberEmail : naverLogin.user.email,\r\n");
      out.write("\t                    \t\t\tmemberPwd : naverLogin.user.id,\r\n");
      out.write("\t                    \t\t\tmemberNickName : naverLogin.user.nickname,\r\n");
      out.write("\t                    \t\t\timagePath : naverLogin.user.profile_image},\r\n");
      out.write("\t                    \tsuccess : function(result){\r\n");
      out.write("\t                    \t\tif(result == \"success\"){\r\n");
      out.write("\t                    \t\t\tlocation.href = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${detailUrl}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\";\r\n");
      out.write("\t                    \t\t}else{\r\n");
      out.write("\t                    \t\t\talert(\"네이버 로그인에 실패하였습니다.\\n\" + e);\r\n");
      out.write("\t                    \t\t\tlocation.href = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/member/loginForm\";\r\n");
      out.write("\t                    \t\t}\r\n");
      out.write("\t                    \t},\r\n");
      out.write("\t                    \terror : function(e){\r\n");
      out.write("\t                    \t\talert(\"네이버 로그인에 실패하였습니다.\\n\" + e);\r\n");
      out.write("\t                    \t\tlocation.href = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/member/loginForm\";\r\n");
      out.write("\t                    \t},\r\n");
      out.write("\t                    });\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\talert(\"callback 처리에 실패하였습니다.\");\r\n");
      out.write("\t\t\t\t\tlocation.href = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/member/loginForm\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
