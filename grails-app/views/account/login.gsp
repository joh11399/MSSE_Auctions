
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<g:form action="authenticate" >
    <fieldset class="form">
username<br/>
<g:textField name="username" value="${accountInstance?.username}"/>
<br/><br/>
password<br/>
<g:field type="password" name="password" value="${accountInstance?.password}"/>
<br/><br/>
    </fieldset>
    <fieldset class="buttons">
<g:submitButton name="login" class="save" value="Login" />
    </fieldset>
</g:form>


<a class="home" href="${createLink(uri: '/account/create/')}">Create Account</a>

</body>
</html>