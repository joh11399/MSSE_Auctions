
<%@ page import="msse_auctions.Listing" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'listing.label', default: 'Listing')}" />
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

        <g:if test="${listingInstance?.seller}">
            <li class="fieldcontain">
                <span id="seller-label" class="property-label"><g:message code="listing.seller.label" default="Owner" /></span>

                <span class="property-value" aria-labelledby="seller-label"><g:link controller="account" action="show" id="${listingInstance?.seller?.id}">${listingInstance?.seller?.encodeAsHTML()}</g:link></span>

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

        <g:if test="${listingInstance?.days}">
            <li class="fieldcontain">
                <span id="days-label" class="property-label"><g:message code="listing.days.label" default="Days" /></span>

                <span class="property-value" aria-labelledby="days-label"><g:fieldValue bean="${listingInstance}" field="days" /></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.minAmount}">
            <li class="fieldcontain">
                <span id="minAmount-label" class="property-label"><g:message code="listing.minAmount.label" default="Min Amount" /></span>

                <span class="property-value" aria-labelledby="minAmount-label"><g:fieldValue bean="${listingInstance}" field="minAmount"/></span>

            </li>
        </g:if>
        <g:if test="${listingInstance?.deliverOption}">
            <li class="fieldcontain">
                <span id="deliverOption-label" class="property-label"><g:message code="listing.deliverOption.label" default="Deliver Option" /></span>

                <span class="property-value" aria-labelledby="deliverOption-label"><g:fieldValue bean="${listingInstance}" field="deliverOption"/></span>

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
