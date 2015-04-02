<%@ page import="msse_auctions.Account; msse_auctions.Bid" %>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'listing', 'error')} required">
    <label for="listing">
        <g:message code="bid.listing.label" default="Listing" />
    </label>
    <label id="listingLbl"style="text-align: left; cursor: default;">${bidInstance?.listing?.name}</label>
    <input type="hidden" id="listing" name="listing.id" value="${bidInstance?.listing?.id}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'bidder', 'error')} required">
    <label for="bidder">
        <g:message code="bid.bidder.label" default="Bidder" />
    </label>
    <label id="bidderLbl"style="text-align: left; cursor: default;">${bidInstance?.bidder?.name}</label>
    <input type="hidden" id="bidder" name="bidder.id" value="${bidInstance?.bidder?.id}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'amount', 'error')} required">
    <label for="amount">
        <g:message code="bid.amount.label" default="Amount" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="amount" required="" value="${bidInstance?.amount}"/>

</div>


