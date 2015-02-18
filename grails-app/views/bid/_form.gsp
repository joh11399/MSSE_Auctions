<%@ page import="msse_auctions.Account; msse_auctions.Listing; msse_auctions.Bid" %>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'auction', 'error')} required">
    <label for="auction">
        <g:message code="bid.auction.label" default="Auction" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="auction" name="auction.id" from="${msse_auctions.Listing.list()}" optionKey="id" required="" value="${bidInstance?.auction?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'bidder', 'error')} required">
    <label for="bidder">
        <g:message code="bid.bidder.label" default="Bidder" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="bidder" name="bidder.id" from="${msse_auctions.Account.list()}" optionKey="id" required="" value="${bidInstance?.bidder?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'amount', 'error')} required">
    <label for="amount">
        <g:message code="bid.amount.label" default="Amount" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="amount" required="" value="${bidInstance?.amount}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'bidDate', 'error')} required">
    <label for="bidDate">
        <g:message code="bid.bidDate.label" default="Bid Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="bidDate" precision="day"  value="${bidInstance?.bidDate}"  />

</div>

