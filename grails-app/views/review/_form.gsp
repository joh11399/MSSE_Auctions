<%@ page import="msse_auctions.Review" %>



<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'listing', 'error')} required">
    <label for="listing">
        <g:message code="review.listing.label" default="Listing" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="listing" name="listing.id" from="${msse_auctions.Listing.list()}" optionKey="id" required="" value="${reviewInstance?.listing?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'reviewer', 'error')} required">
    <label for="reviewer">
        <g:message code="review.reviewer.label" default="Reviewer" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="reviewer" name="reviewer.id" from="${msse_auctions.Account.list()}" optionKey="id" required="" value="${reviewInstance?.reviewer?.id}" class="many-to-one"/>
</div>
<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'reviewee', 'error')} required">
    <label for="reviewee">
        <g:message code="review.reviewee.label" default="Reviewee" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="reviewee" name="reviewee.id" from="${msse_auctions.Account.list()}" optionKey="id" required="" value="${reviewInstance?.reviewee?.id}" class="many-to-one"/>
</div>
<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'reviewOf', 'error')} required">
    <label for="reviewOf">
        <g:message code="review.reviewOf.label" default="Review of" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="reviewOf" from="${reviewInstance.constraints.reviewOf.inList}" required="" value="${reviewInstance?.reviewOf}" valueMessagePrefix="review.reviewOf"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'rating', 'error')} required">
    <label for="rating">
        <g:message code="review.rating.label" default="Rating" />
        <span class="required-indicator">*</span>
    </label>
    <select name="rating"><option>5</option><option>4</option><option>3</option><option>2</option><option>1</option><option>0</option></select>

</div>

<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'thumbs', 'error')} required">
    <label for="thumbs">
        <g:message code="review.thumbs.label" default="Thumbs" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="thumbs" from="${reviewInstance.constraints.thumbs.inList}" required="" value="${reviewInstance?.thumbs}" valueMessagePrefix="review.thumbs"/>
</div>


<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'description', 'error')} required">
    <label for="description">
        <g:message code="review.description.label" default="Description" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="description" required="" value="${reviewInstance?.description}"/>
</div>
