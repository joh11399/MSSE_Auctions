
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>


<a class="home" href="${createLink(uri: '/listing/create/')}">Create New Listing</a>

<table>
    <thead>
    <tr>

        <g:sortableColumn property="name" title="${message(code: 'listing.name.label', default: 'Name')}" />

        <g:sortableColumn property="begDate" title="${message(code: 'listing.begDate.label', default: 'Beg Date')}" />

        <g:sortableColumn property="endDate" title="${message(code: 'listing.endDate.label', default: 'End Date')}" />

        <g:sortableColumn property="minAmount" title="${message(code: 'listing.minAmount.label', default: 'Min Amount')}" />

        <th><g:message code="listing.owner.label" default="Owner" /></th>

        <th>bid</th>

    </tr>
    </thead>
    <tbody>
    <g:each in="${listingInstanceList}" status="i" var="listingInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

            <td><g:link action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "name")}</g:link>

            <br />

                ${fieldValue(bean: listingInstance, field: "description")}</td>

            <td><g:formatDate date="${listingInstance.begDate}" /></td>

            <td><g:formatDate date="${listingInstance.endDate}" /></td>

            <td>${fieldValue(bean: listingInstance, field: "minAmount")}</td>

            <td><g:link controller="account" action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "owner")}</g:link></td>

            <td><a class="home" href="${createLink(uri: '/bid/create/')}">bid</a></td>

        </tr>
    </g:each>
    </tbody>
</table>


</body>
</html>