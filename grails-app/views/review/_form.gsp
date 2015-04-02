<%@ page import="msse_auctions.Account; msse_auctions.Review" %>



<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'listing', 'error')} required">
    <label for="listing">
        <g:message code="review.listing.label" default="Listing" />
    </label>
    <label id="listingLbl" style="text-align: left; cursor: default;">${reviewInstance?.listing?.name}</label>
    <input type="hidden" id="listing" name="listing.id" value="${reviewInstance?.listing?.id}" >
</div>

<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'reviewer', 'error')} required">
    <label for="reviewer">
        <g:message code="review.reviewer.label" default="Reviewer" />
    </label>
    <label id="reviewerLbl" style="text-align: left; cursor: default;">${reviewInstance?.reviewer?.name}</label>
    <input type="hidden" id="reviewer" name="reviewer.id" value="${reviewInstance?.reviewer?.id}" >
</div>
<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'reviewee', 'error')} required">
    <label for="reviewee">
        <g:message code="review.reviewee.label" default="Reviewee" />
    </label>
    <label id="revieweeLbl" style="text-align: left; cursor: default;">${reviewInstance?.reviewee?.name}</label>
    <input type="hidden" id="reviewee" name="reviewee.id" value="${reviewInstance?.reviewee?.id}" >
</div>
<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'reviewOf', 'error')} required">
    <label for="reviewOf">
        <g:message code="review.reviewOf.label" default="Review of" />
    </label>
    <label id="reviewOfLbl" style="text-align: left; cursor: default;">${reviewInstance?.reviewOf}</label>
    <input type="hidden" id="reviewOf" name="reviewOf" value="${reviewInstance?.reviewOf}" >
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
