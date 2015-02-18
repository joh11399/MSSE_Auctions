
<%@ page import="msse_auctions.Account" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'account.label', default: 'User')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-user" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list user">

        <g:if test="${accountInstance?.username}">
            <li class="fieldcontain">
                <span id="username-label" class="property-label"><g:message code="account.username.label" default="Username" /></span>

                <span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${accountInstance}" field="username"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.fullName}">
            <li class="fieldcontain">
                <span id="fullName-label" class="property-label"><g:message code="account.fullName.label" default="Full Name" /></span>

                <span class="property-value" aria-labelledby="fullName-label"><g:fieldValue bean="${userInstance}" field="fullName"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.password}">
            <li class="fieldcontain">
                <span id="password-label" class="property-label"><g:message code="account.password.label" default="Password" /></span>

                <span class="property-value" aria-labelledby="password-label"><g:fieldValue bean="${accountInstance}" field="password"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.addressState}">
            <li class="fieldcontain">
                <span id="addressState-label" class="property-label"><g:message code="account.addressState.label" default="Address State" /></span>

                <span class="property-value" aria-labelledby="addressState-label"><g:fieldValue bean="${accountInstance}" field="addressState"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.addressCity}">
            <li class="fieldcontain">
                <span id="addressCity-label" class="property-label"><g:message code="account.addressCity.label" default="Address City" /></span>

                <span class="property-value" aria-labelledby="addressCity-label"><g:fieldValue bean="${accountInstance}" field="addressCity"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.addressStreet}">
            <li class="fieldcontain">
                <span id="addressStreet-label" class="property-label"><g:message code="account.addressStreet.label" default="Address Street" /></span>

                <span class="property-value" aria-labelledby="addressStreet-label"><g:fieldValue bean="${accountInstance}" field="addressStreet"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.addressZip}">
            <li class="fieldcontain">
                <span id="addressZip-label" class="property-label"><g:message code="account.addressZip.label" default="Address Zip" /></span>

                <span class="property-value" aria-labelledby="addressZip-label"><g:fieldValue bean="${accountInstance}" field="addressZip"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.email}">
            <li class="fieldcontain">
                <span id="email-label" class="property-label"><g:message code="account.email.label" default="Email" /></span>

                <span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${accountInstance}" field="email"/></span>

            </li>
        </g:if>

    </ol>
    <g:form url="[resource:accountInstance, action:'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${accountInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
