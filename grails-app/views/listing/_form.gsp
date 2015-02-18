<%@ page import="msse_auctions.Listing" %>



<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'owner', 'error')} required">
    <label for="owner">
        <g:message code="listing.owner.label" default="Owner" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="owner" name="owner.id" from="${msse_auctions.Account.list()}" optionKey="id" required="" value="${listingInstance?.owner?.id}" class="many-to-one"/>

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

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'endDate', 'error')} required">
    <label for="endDate">
        <g:message code="listing.endDate.label" default="End Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="endDate" precision="day"  value="${listingInstance?.endDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'minAmount', 'error')} required">
    <label for="minAmount">
        <g:message code="listing.minAmount.label" default="Min Amount" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="minAmount" required="" value="${listingInstance?.minAmount}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'buyAmount', 'error')} required">
    <label for="buyAmount">
        <g:message code="listing.buyAmount.label" default="Buy Amount" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="buyAmount" required="" value="${listingInstance?.buyAmount}"/>

</div>

