
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
    </ul>
</div>


<div style="margin: 35px auto; padding: 6px 10px; width: 400px; height: 70px; border: 1px solid #777; background-color: #eaeaea;">
    <g:form controller="review" action="index">
        Search by Buyer or Seller
        <g:textField name="searchBuyerSeller" value="${searchBuyerSeller}"></g:textField><br><br>
        <g:submitButton name="submit" value="refresh" style="float: right;" />
    </g:form>
</div>

<div id="list-review" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <th>${message(code: 'review.reviewee.label', default: 'reviewee')}</th>
            <th>${message(code: 'review.description.label', default: 'description')}</th>
            <th>${message(code: 'review.rating.label', default: 'rating')}</th>
            <th>${message(code: 'review.thumbs.label', default: 'thumbs')}</th>
            <th>${message(code: 'review.listing.label', default: 'listing')}</th>
            <th>${message(code: 'review.reviewer.label', default: 'reviewer')}</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${reviewInstanceList}" status="i" var="reviewInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td>${fieldValue(bean: reviewInstance, field: "reviewee")} (${fieldValue(bean: reviewInstance, field: "reviewOf")})</td>

                <td>${fieldValue(bean: reviewInstance, field: "description")}</td>

                <td>${fieldValue(bean: reviewInstance, field: "rating")}</td>

                <td>${fieldValue(bean: reviewInstance, field: "thumbs")}</td>

                <td><g:link controller="listing" action="show" id="${reviewInstance.listing.id}">${fieldValue(bean: reviewInstance, field: "listing")}</g:link></td>

                <td>${fieldValue(bean: reviewInstance, field: "reviewer")}</td>

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
