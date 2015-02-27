
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

<g:form url="[resource:accountInstance, action:'delete']" method="DELETE">
    <fieldset class="buttons">
        <g:link class="edit" action="edit" resource="${accountInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
</g:form>

<div id="show-user" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list user">

        <g:if test="${accountInstance?.email}">
            <li class="fieldcontain">
                <span id="email-label" class="property-label"><g:message code="account.email.label" default="Email" /></span>

                <span id="email" class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${accountInstance}" field="email"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="account.name.label" default="Name" /></span>

                <span id="name" class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${accountInstance}" field="name"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.addressStreet}">
            <li class="fieldcontain">
                <span id="addressStreet-label" class="property-label"><g:message code="account.addressStreet.label" default="Address Street" /></span>

                <span id="addressStreet" class="property-value" aria-labelledby="addressStreet-label"><g:fieldValue bean="${accountInstance}" field="addressStreet"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.addressCity}">
            <li class="fieldcontain">
                <span id="addressCity-label" class="property-label"><g:message code="account.addressCity.label" default="Address City" /></span>

                <span id="addressCity" class="property-value" aria-labelledby="addressCity-label"><g:fieldValue bean="${accountInstance}" field="addressCity"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.addressState}">
            <li class="fieldcontain">
                <span id="addressState-label" class="property-label"><g:message code="account.addressState.label" default="Address State" /></span>

                <span id="addressState" class="property-value" aria-labelledby="addressState-label"><g:fieldValue bean="${accountInstance}" field="addressState"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.addressZip}">
            <li class="fieldcontain">
                <span id="addressZip-label" class="property-label"><g:message code="account.addressZip.label" default="Address Zip" /></span>

                <span id="addressZip" class="property-value" aria-labelledby="addressZip-label"><g:fieldValue bean="${accountInstance}" field="addressZip"/></span>

            </li>
        </g:if>

        <g:if test="${accountInstance?.dateCreated}">
            <li class="fieldcontain">
                <span id="dateCreated-label" class="property-label"><g:message code="account.dateCreated.label" default="Date Created" /></span>
                <span id="dateCreated" class="property-value" aria-labelledby="dateCreated-label"><g:formatDate format="M/dd/yy h:mm a" date="${accountInstance?.dateCreated}" /></span>
            </li>
        </g:if>

        <g:if test="${accountInstance?.lastUpdated}">
            <li class="fieldcontain">
                <span id="lastUpdated-label" class="property-label"><g:message code="account.lastUpdated.label" default="Last Updated" /></span>
                <span id="lastUpdated" class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate format="M/dd/yy h:mm a" date="${accountInstance?.lastUpdated}" /></span>
            </li>
        </g:if>

    </ol>


    <br/>
    <h1>Reviews for ${accountInstance.name}</h1>
    <div id="list-review" class="content scaffold-list" role="main">
        <table>
            <thead>
            <tr>
                <g:sortableColumn property="reviewee" title="${message(code: 'review.reviewee.label', default: 'reviewee')}" />

                <g:sortableColumn property="description" title="${message(code: 'review.description.label', default: 'description')}" />

                <g:sortableColumn property="rating" title="${message(code: 'review.rating.label', default: 'rating')}" />

                <g:sortableColumn property="thumbs" title="${message(code: 'review.thumbs.label', default: 'thumbs')}" />

                <g:sortableColumn property="listing" title="${message(code: 'review.listing.label', default: 'listing')}" />

                <g:sortableColumn property="reviewer" title="${message(code: 'review.reviewer.label', default: 'reviewer')}" />
            </tr>
            </thead>
            <tbody>
            <g:each in="${reviewList}" status="i" var="reviewInstance">
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
        <br/>

</div>
</body>
</html>
