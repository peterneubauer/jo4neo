<%@ include file="header.jsp" %>
<stripes:layout-render name="/layout/default.jsp">
<stripes:layout-component name="contents">

Tags:
<c:forEach items="${actionBean.tags}" var="tag" varStatus="loop">
<c:if test="${!loop.first}">, </c:if>
<c:if test="${actionBean.selected == tag.id}">&gt;</c:if>
<a href="${pageContext.request.contextPath}/blog/tags/${tag.id}">${tag.name} (${tag.posts.count})</a>
</c:forEach>

<c:forEach items="${actionBean.posts}" var="row" varStatus="loop">
<c:set var="editable" scope="request" value="${actionBean.editable}"/> 
<c:set var="post" scope="request" value="${row}"/> 
<c:set var="singlePost" scope="request" value="${actionBean.singlePost}"/> 
<c:import url="/views/single_post.jsp"/>
</c:forEach>


</stripes:layout-component>
</stripes:layout-render>
