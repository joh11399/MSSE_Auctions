<%@ page import="msse_auctions.Account" %>



<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'email', 'error')} required">
    <label for="email">
        <g:message code="account.email.label" default="Email" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="email" required="" value="${accountInstance?.email}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="account.username.label" default="Username" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="username" required="" value="${accountInstance?.username}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'fullName', 'error')} required">
    <label for="fullName">
        <g:message code="account.fullName.label" default="Full Name" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fullName" required="" value="${accountInstance?.fullName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
    <label for="password">
        <g:message code="account.password.label" default="Password" />
        <span class="required-indicator">*</span>
    </label>
    <g:field type="password" name="password" required="" value="${accountInstance?.password}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'addressStreet', 'error')} required">
    <label for="addressStreet">
        <g:message code="account.addressStreet.label" default="Address Street" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="addressStreet" required="" value="${accountInstance?.addressStreet}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'addressCity', 'error')} required">
    <label for="addressCity">
        <g:message code="account.addressCity.label" default="Address City" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="addressCity" required="" value="${accountInstance?.addressCity}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'addressState', 'error')} required">
    <label for="addressState">
        <g:message code="account.addressState.label" default="Address State" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="addressState" from="${accountInstance.constraints.addressState.inList}" required="" value="${accountInstance?.addressState}" valueMessagePrefix="account.addressState"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'addressZip', 'error')} required">
    <label for="addressZip">
        <g:message code="account.addressZip.label" default="Address Zip" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="addressZip" required="" value="${accountInstance?.addressZip}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'dateCreated', 'error')} required">
    <label for="dateCreated">
        <g:message code="account.dateCreated.label" default="Date Created" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="dateCreated" precision="day"  value="${accountInstance?.dateCreated}"  />
</div>
<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'lastUpdated', 'error')} required">
    <label for="lastUpdated">
        <g:message code="account.lastUpdated.label" default="Last Updated" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="lastUpdated" precision="day"  value="${accountInstance?.lastUpdated}"  />
</div>

