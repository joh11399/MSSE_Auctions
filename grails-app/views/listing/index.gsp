
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title></title>
</head>

<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" controller="listing" action="create">Create New Listing</g:link></li>
    </ul>
</div>

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

        <th>${message(code: 'listing.startDate.label', default: 'Start Date')}</th>

        <th>${message(code: 'listing.deliverOption.label', default: 'Deliver Option')}</th>

        <th><g:message code="listing.seller.label" default="Seller" /></th>

    </tr>
    </thead>
    <tbody>
    <g:each in="${listingInstanceList}" status="i" var="listingInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

            <td>
                <a href="/MSSE_Auctions/bid/create/?listingID=${listingInstance.id}" class="create bidLink" style="display: ${listingInstance.timeRemaining=='completed' ? 'none': 'block' }">bid</a>
                <a href="/MSSE_Auctions/review/create/?listingID=${listingInstance.id}" class="create rateSellerLink" style="display: ${listingInstance.timeRemaining=='completed' ? 'block': 'none' }; white-space: nowrap;">Rate Seller</a>
                <a href="/MSSE_Auctions/review/create/?listingID=${listingInstance.id}" class="create rateBuyerLink" style="display: ${listingInstance.timeRemaining=='completed' ? 'block': 'none' }; white-space: nowrap;">Rate Buyer</a>
            </td>

            <td><label class="timeRemaining">${listingInstance.timeRemaining}</label><br/>(<g:formatDate format="M/dd h:mm a" date="${listingInstance.endDate}"/>)</td>

            <td style="font-weight: ${listingInstance.timeRemaining=='completed' ? 'bold': 'normal' }" class="highestBidStr">${listingInstance.highestBidStr}</td>

            <td><g:link action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "name")}</g:link>

            <br />

                <label class="description">${fieldValue(bean: listingInstance, field: "description")}</label></td>

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