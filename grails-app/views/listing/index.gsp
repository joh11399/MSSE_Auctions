
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title></title>
</head>

<body>


<a class="home" href="${createLink(uri: '/listing/create/')}">Create New Listing</a>

<div style="margin: 35px auto; padding: 6px 10px; width: 400px; height: 70px; border: 1px solid #777; background-color: #eaeaea;">
<g:form controller="listing" action="index">
    Search by Description
    <g:textField name="searchDescription" value="${searchDescription}"></g:textField><br><br>
    <g:checkBox name="completedListingsCheckbox" checked="${completedListingsCheckboxChecked}" />Show Completed Listings
    <g:submitButton name="submit" value="refresh" style="float: right;" />
</g:form>
</div>

<table>
    <thead>
    <tr>
        <th style="width: 25px;"></th>

        <th>Time Remaining</th>

        <th>Highest Bid</th>

        <th>Name & Description</th>

        <g:sortableColumn property="startDate" title="${message(code: 'listing.startDate.label', default: 'Start Date')}" />

        <g:sortableColumn property="deliverOption" title="${message(code: 'listing.deliverOption.label', default: 'Deliver Option')}" />

        <th><g:message code="listing.seller.label" default="Seller" /></th>


    </tr>
    </thead>
    <tbody>
    <g:each in="${listingInstanceList}" status="i" var="listingInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

            <td>
                <g:link class="create" controller="bid" action="create" params="[listingID: listingInstance.id]" style="display: ${listingInstance.timeRemaining=='completed' ? 'none': 'block' }">bid</g:link>
                <g:link class="create" controller="review" action="create" params="[listingID: listingInstance.id]" style="display: ${listingInstance.timeRemaining=='completed' ? 'block': 'none' }; white-space: nowrap;">Rate Seller</g:link>
                <g:link class="create" controller="review" action="create" params="[listingID: listingInstance.id]" style="display: ${listingInstance.timeRemaining=='completed' ? 'block': 'none' }; white-space: nowrap;">Rate Buyer</g:link>
            </td>

            <td>${listingInstance.timeRemaining}<br/>(<g:formatDate format="M/dd h:mm a" date="${listingInstance.endDate}"/>)</td>

            <td style="font-weight: ${listingInstance.timeRemaining=='completed' ? 'bold': 'normal' }">
            ${listingInstance.timeRemaining=='completed' ? 'Winner: ': '' }${listingInstance.highestBidID}</td>

            <td><g:link action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "name")}</g:link>

            <br />

                ${fieldValue(bean: listingInstance, field: "description")}</td>

            <td><g:formatDate format="M/dd h:mm a" date="${listingInstance.startDate}" /></td>

            <td>${fieldValue(bean: listingInstance, field: "deliverOption")}</td>

            <td><g:link controller="account" action="show" id="${listingInstance.seller.id}">${fieldValue(bean: listingInstance, field: "seller")}</g:link></td>


        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="${listingInstanceCount ?: 0}" />
</div>


</body>
</html>