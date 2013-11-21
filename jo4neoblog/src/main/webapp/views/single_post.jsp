<%@ include file="/header.jsp" %>

<div class="entry">
<h2><a href="${pageContext.request.contextPath}/blog/home/${post.id}">${post.title}</a></h2>
 
<span>${post.createdAt} : </span>
${post.content}<br/>
- ${post.author.screenName} ${post.commentsCount} comments | tags :
<c:forEach items="${post.tags}" var="tag" varStatus="loop">
<c:if test="${!loop.first}">, </c:if>${tag.name}</c:forEach>
<br/>
<c:if test="${editable}">
<stripes:link beanclass="action.PostAction">
<stripes:param name="post" value="${post.id}"/>
Edit this post</stripes:link>
</c:if>

<c:if test="${singlePost}">
<c:forEach items="${post.comments}" var="comment">${comment.content}<br/></c:forEach>
</c:if>
</div>
<c:if test="${singlePost}">
<stripes:form beanclass="action.CommentAction" method="post">
Comments:<br/>
<stripes:textarea name="comment.content" rows="5" cols="60"/><br/>
<stripes:hidden name="selected" value="${post.id}"/>
<stripes:submit name="comment" value="comment"/>
</stripes:form>
</c:if>