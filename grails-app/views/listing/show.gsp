
<%@ page import="msse_auctions.Auction" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'auction.label', default: 'Auction')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-auction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-auction" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list auction">

        <g:if test="${listingInstance?.owner}">
            <li class="fieldcontain">
                <span id="owner-label" class="property-label"><g:message code="listing.owner.label" default="Owner" /></span>

                <span class="property-value" aria-labelledby="owner-label"><g:link controller="account" action="show" id="${listingInstance?.owner?.id}">${listingInstance?.owner?.encodeAsHTML()}</g:link></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="listing.name.label" default="Name" /></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${listingInstance}" field="name"/></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.description}">
            <li class="fieldcontain">
                <span id="description-label" class="property-label"><g:message code="listing.description.label" default="Description" /></span>

                <span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${listingInstance}" field="description"/></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.begDate}">
            <li class="fieldcontain">
                <span id="begDate-label" class="property-label"><g:message code="listing.begDate.label" default="Beg Date" /></span>

                <span class="property-value" aria-labelledby="begDate-label"><g:formatDate date="${listingInstance?.begDate}" /></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.endDate}">
            <li class="fieldcontain">
                <span id="endDate-label" class="property-label"><g:message code="listing.endDate.label" default="End Date" /></span>

                <span class="property-value" aria-labelledby="endDate-label"><g:formatDate date="${listingInstance?.endDate}" /></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.minAmount}">
            <li class="fieldcontain">
                <span id="minAmount-label" class="property-label"><g:message code="listing.minAmount.label" default="Min Amount" /></span>

                <span class="property-value" aria-labelledby="minAmount-label"><g:fieldValue bean="${listingInstance}" field="minAmount"/></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.buyAmount}">
            <li class="fieldcontain">
                <span id="buyAmount-label" class="property-label"><g:message code="listing.buyAmount.label" default="Buy Amount" /></span>

                <span class="property-value" aria-labelledby="buyAmount-label"><g:fieldValue bean="${listingInstance}" field="buyAmount"/></span>

            </li>
        </g:if>

    </ol>
    <g:form url="[resource:listingInstance, action:'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${auctionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
