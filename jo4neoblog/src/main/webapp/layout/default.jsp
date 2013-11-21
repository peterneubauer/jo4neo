<%@ include file="../header.jsp" %>
<stripes:layout-definition>
  <html>
    <head>
      <title>Java Objects 4 Neo Blog</title>
      <link rel="stylesheet"
            type="text/css"
            href="${pageContext.request.contextPath}/style/default.css"/>
      <stripes:layout-component name="html-head"/>     
    </head>

    <body>
	<div id="page">
    <div id="header">
    
    <div>
    <c:choose>
    <c:when test="${not empty login}">
    logged in as ${login.screenName} | 
    <stripes:link beanclass="action.PostAction">new post</stripes:link> |
    <stripes:link beanclass="action.LogoutAction">logout</stripes:link>
    </c:when>
    <c:when test="${empty login}">
    <stripes:link beanclass="action.LoginAction">sign in</stripes:link>
    </c:when>
    </c:choose>
    </div>
    
    <div>
	  <div style="float:left"><h1><a href="${pageContext.request.contextPath}/">jo4neo Blog</a></h1></div>
	  <div style="text-align:right">
  	    <stripes:form beanclass="action.SearchAction" method="post">
	      <stripes:text name="query"/>
	      <stripes:submit name="search" value="search"/>
        </stripes:form>
	  </div>
	</div>
	
    <div class="br" style="clear:both"> </div>
    </div>
    <div id="content">
    <stripes:layout-component name="contents"/>
    </div>
    </div>
    </body>
  </html>
</stripes:layout-definition>

