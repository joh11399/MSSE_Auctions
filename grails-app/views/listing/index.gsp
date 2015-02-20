
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title></title>
</head>

<body>


<a class="home" href="${createLink(uri: '/listing/create/')}">Create New Listing</a>

<div style="float: right;">
<g:form controller="listing" action="index">
    <g:checkBox name="completedListingsCheckbox" checked="${completedListingsCheckboxChecked}" />Completed Listings <g:submitButton name="submit" value="refresh" />
</g:form>
</div>

<table>
    <thead>
    <tr>
        <th style="width: 25px;"></th>

        <g:sortableColumn property="name" title="${message(code: 'listing.name.label', default: 'time remaining')}" />
        <g:sortableColumn property="name" title="${message(code: 'listing.name.label', default: 'highest bid')}" />

        <g:sortableColumn property="name" title="${message(code: 'listing.name.label', default: 'Name')}" />

        <g:sortableColumn property="begDate" title="${message(code: 'listing.begDate.label', default: 'Beg Date')}" />

        <g:sortableColumn property="deliverOption" title="${message(code: 'listing.deliverOption.label', default: 'Deliver Option')}" />

        <th><g:message code="listing.seller.label" default="Seller" /></th>


    </tr>
    </thead>
    <tbody>
    <g:each in="${listingInstanceList}" status="i" var="listingInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

            <td><g:link class="create" controller="bid" action="create" params="[listingID: listingInstance.id]" style="display: ${completedListingsCheckboxChecked ? 'none': 'block' }">bid</g:link></td>}

            <td>${listingInstance.timeRemaining}<br/>(<g:formatDate format="M/dd h:mm a" date="${listingInstance.endDate}"/>)</td>

            <td>${listingInstance.highestBidID}</td>

            <td><g:link action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "name")}</g:link>

            <br />

                ${fieldValue(bean: listingInstance, field: "description")}</td>

            <td><g:formatDate format="M/dd h:mm a" date="${listingInstance.begDate}" /></td>

            <td>${fieldValue(bean: listingInstance, field: "deliverOption")}</td>

            <td><g:link controller="account" action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "seller")}</g:link></td>


        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="${listingInstanceCount ?: 0}" />
</div>


</body>
</html>