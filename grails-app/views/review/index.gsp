
<%@ page import="msse_auctions.Review" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'review.label', default: 'Review')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-review" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="list-review" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="description" title="${message(code: 'review.description.label', default: 'description')}" />

            <g:sortableColumn property="reviewer" title="${message(code: 'review.reviewer.label', default: 'reviewer')}" />

            <g:sortableColumn property="reviewee" title="${message(code: 'review.reviewee.label', default: 'reviewee')}" />

            <g:sortableColumn property="revieweeType" title="${message(code: 'review.revieweeType.label', default: 'revieweeType')}" />

            <g:sortableColumn property="rating" title="${message(code: 'review.rating.label', default: 'rating')}" />

            <g:sortableColumn property="thumbs" title="${message(code: 'review.thumbs.label', default: 'thumbs')}" />

            <g:sortableColumn property="listing" title="${message(code: 'review.listing.label', default: 'listing')}" />

        </tr>
        </thead>
        <tbody>
        <g:each in="${reviewInstanceList}" status="i" var="reviewInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show" id="${reviewInstance.id}">${fieldValue(bean: reviewInstance, field: "description")}</g:link></td>

                <td>${fieldValue(bean: reviewInstance, field: "reviewer")}</td>

                <td>${fieldValue(bean: reviewInstance, field: "reviewee")}</td>

                <td>${fieldValue(bean: reviewInstance, field: "revieweeType")}</td>

                <td>${fieldValue(bean: reviewInstance, field: "rating")}</td>

                <td>${fieldValue(bean: reviewInstance, field: "thumbs")}</td>

                <td><g:link controller="listing" action="show" id="${reviewInstance.listing.id}">${fieldValue(bean: reviewInstance, field: "listing")}</g:link></td>


            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${reviewInstanceCount ?: 0}" />
    </div>
</div>
</body>
</html>
