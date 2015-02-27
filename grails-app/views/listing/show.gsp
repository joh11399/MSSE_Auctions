
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

        <g:if test="${listingInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="listing.name.label" default="Name" /></span>

                <span class="property-value" aria-labelledby="name-label"><b><g:fieldValue bean="${listingInstance}" field="name"/></b></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.description}">
            <li class="fieldcontain">
                <span id="description-label" class="property-label"><g:message code="listing.description.label" default="Description" /></span>

                <span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${listingInstance}" field="description"/></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.timeRemaining}">
            <li class="fieldcontain">
                <span id="timeRemaining-label" class="property-label"><g:message code="listing.timeRemaining.label" default="Time Remaining" /></span>

                <span class="property-value" aria-labelledby="timeRemaining-label"><g:fieldValue bean="${listingInstance}" field="timeRemaining"/>
                (<g:formatDate format="M/dd h:mm a" date="${listingInstance.endDate}"/>)</span>

            </li>
        </g:if>
        <g:if test="${listingInstance?.highestBidStr}">
            <li class="fieldcontain">
                <span id="highestBidStr-label" class="property-label"><g:message code="listing.highestBidStr.label" default="${listingInstance.timeRemaining=='completed' ? 'Winning Bid':'Highest Bid'}" /></span>

                <span class="property-value" aria-labelledby="highestBidStr-label"><b><g:fieldValue bean="${listingInstance}" field="highestBidStr"/></b>
                    <g:link class="create" controller="bid" action="index" style="margin-left: 15px;" params="[listingID: listingInstance.id]">Bid History</g:link>
                </span>
            </li>
        </g:if>

        <li class="fieldcontain">
            <span id="bid-label" class="property-label"></span>

            <span class="property-value" aria-labelledby="bid-label"><g:link class="create" controller="bid" action="create" params="[listingID: listingInstance.id]" style="display: ${listingInstance.timeRemaining=='completed' ? 'none': 'block' }">bid</g:link></span>

        </li>

        <br/><br/>


        <g:if test="${listingInstance?.seller}">
            <li class="fieldcontain">
                <span id="seller-label" class="property-label"><g:message code="listing.seller.label" default="Seller" /></span>

                <span class="property-value" aria-labelledby="seller-label"><g:link controller="account" action="show" id="${listingInstance?.seller?.id}">${listingInstance?.seller?.encodeAsHTML()}</g:link></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.startDate}">
            <li class="fieldcontain">
                <span id="startDate-label" class="property-label"><g:message code="listing.startDate.label" default="Start Date" /></span>

                <span class="property-value" aria-labelledby="startDate-label"><g:formatDate format="M/dd h:mm a" date="${listingInstance?.startDate}" /></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.days}">
            <li class="fieldcontain">
                <span id="days-label" class="property-label"><g:message code="listing.days.label" default="Days" /></span>

                <span class="property-value" aria-labelledby="days-label"><g:fieldValue bean="${listingInstance}" field="days" /></span>

            </li>
        </g:if>

        <g:if test="${listingInstance?.startingPrice}">
            <li class="fieldcontain">
                <span id="startingPrice-label" class="property-label"><g:message code="listing.startingPrice.label" default="Starting Price" /></span>

                <span class="property-value" aria-labelledby="startingPrice-label"><g:fieldValue bean="${listingInstance}" field="startingPrice"/></span>

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
            <g:link class="edit" action="edit" resource="${listingInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
