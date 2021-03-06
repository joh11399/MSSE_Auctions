
<%@ page import="msse_auctions.Account" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-account" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="list-account" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <th>${message(code: 'account.username.label', default: 'Username')}</th>

            <th>${message(code: 'account.name.label', default: 'Name')}</th>

            <th>${message(code: 'account.name.label', default: 'Email')}</th>

            <th>${message(code: 'account.addressState.label', default: 'Address State')}</th>

            <th>${message(code: 'account.addressCity.label', default: 'Address City')}</th>

            <th>${message(code: 'account.addressStreet.label', default: 'Address Street')}</th>

            <th>${message(code: 'listing.dateCreated.label', default: 'Date Created')}</th>
            <th>${message(code: 'listing.lastUpdated.label', default: 'Last Updated')}</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${accountInstanceList}" status="i" var="accountInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show" id="${accountInstance.id}">${fieldValue(bean: accountInstance, field: "username")}</g:link></td>

                <td>${fieldValue(bean: accountInstance, field: "name")}</td>

                <td>${fieldValue(bean: accountInstance, field: "email")}</td>

                <td>${fieldValue(bean: accountInstance, field: "addressState")}</td>

                <td>${fieldValue(bean: accountInstance, field: "addressCity")}</td>

                <td>${fieldValue(bean: accountInstance, field: "addressStreet")}</td>

                <td><g:formatDate format="M/dd h:mm a" date="${accountInstance.dateCreated}"/></td>
                <td><g:formatDate format="M/dd h:mm a" date="${accountInstance.lastUpdated}"/></td>


            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${accountInstanceCount ?: 0}" />
    </div>
</div>
</body>
</html>
