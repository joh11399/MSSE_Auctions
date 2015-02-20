<%@ page import="msse_auctions.Listing" %>



<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'seller', 'error')} required">
    <label for="seller">
        <g:message code="listing.seller.label" default="Seller" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="seller" name="seller.id" from="${msse_auctions.Account.list()}" optionKey="id" required="" value="${listingInstance?.seller?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="listing.name.label" default="Name" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" required="" value="${listingInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'description', 'error')} required">
    <label for="description">
        <g:message code="listing.description.label" default="Description" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="description" required="" value="${listingInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'begDate', 'error')} required">
    <label for="begDate">
        <g:message code="listing.begDate.label" default="Beg Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="begDate" precision="day"  value="${listingInstance?.begDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'days', 'error')} required">
    <label for="days">
        <g:message code="listing.days.label" default="Days" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="days" precision="day"  value="${listingInstance?.days}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'minAmount', 'error')} required">
    <label for="minAmount">
        <g:message code="listing.minAmount.label" default="Min Amount" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="minAmount" required="" value="${listingInstance?.minAmount}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'deliverOption', 'error')} required">
    <label for="deliverOption">
        <g:message code="listing.deliverOption.label" default="Deliver Option" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="deliverOption" from="${listingInstance.constraints.deliverOption.inList}" required="" value="${listingInstance?.deliverOption}" valueMessagePrefix="account.addressState"/>
</div>


