<%@ include file="header.jsp" %>
<stripes:layout-render name="/layout/default.jsp">

<stripes:layout-component name="html-head">
</stripes:layout-component>

<stripes:layout-component name="contents">
<stripes:form beanclass="action.PostAction" method="post">
<stripes:errors/>
Post Title:<br/>
<stripes:text name="post.title" class="text" size="80"/><br/>
Content:<br/>
<stripes:textarea name="post.content" class="text" rows="15" cols="60" /><br/>
Tags (comma separated):<br/>
<stripes:text name="tag" class="text" size="30"/><br/>

<stripes:submit name="save" value="Post" />

<stripes:hidden name="post" value="${post.id}"/>
</stripes:form>

</stripes:layout-component>
</stripes:layout-render>