<%@ page import="msse_auctions.Bid" %>



<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'listing', 'error')} required">
    <label for="listing">
        <g:message code="bid.listing.label" default="Listing" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="listing" name="listing.id" from="${msse_auctions.Listing.list()}" optionKey="id" required="" value="${bidInstance?.listing?.id}" class="many-to-one"/>

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


