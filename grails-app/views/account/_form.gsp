<%@ page import="msse_auctions.Account" %>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="account.username.label" default="Username" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField id="username" name="username" required="" value="${accountInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'email', 'error')} required">
    <label for="email">
        <g:message code="account.email.label" default="Email" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField id="email" name="email" required="" value="${accountInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="account.name.label" default="Name" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField id="name" name="name" required="" value="${accountInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'password', 'error')} required">
    <label for="password">
        <g:message code="account.password.label" default="Password" />
        <span class="required-indicator">*</span>
    </label>
    <g:field id="password" type="password" name="password" required="" value="${accountInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'addressStreet', 'error')} required">
    <label for="addressStreet">
        <g:message code="account.addressStreet.label" default="Address Street" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField id="addressStreet" name="addressStreet" required="" value="${accountInstance?.addressStreet}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'addressCity', 'error')} required">
    <label for="addressCity">
        <g:message code="account.addressCity.label" default="Address City" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField id="addressCity" name="addressCity" required="" value="${accountInstance?.addressCity}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'addressState', 'error')} required">
    <label for="addressState">
        <g:message code="account.addressState.label" default="Address State" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="addressState" name="addressState" from="${accountInstance.constraints.addressState.inList}" required="" value="${accountInstance?.addressState}" valueMessagePrefix="account.addressState"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'addressZip', 'error')} required">
    <label for="addressZip">
        <g:message code="account.addressZip.label" default="Address Zip" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField id="addressZip" name="addressZip" required="" value="${accountInstance?.addressZip}"/>
</div>


<g:if test="${accountInstance?.dateCreated}">
        <span id="dateCreated-label" class="property-label"><g:message code="account.dateCreated.label" default="Date Created" /></span><br/>
        <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate format="M/dd/yy h:mm a" date="${accountInstance?.dateCreated}" /></span><br/><br/>
</g:if>

<g:if test="${accountInstance?.lastUpdated}">
        <span id="lastUpdated-label" class="property-label"><g:message code="account.lastUpdated.label" default="Last Updated" /></span><br/>
        <span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate format="M/dd/yy h:mm a" date="${accountInstance?.lastUpdated}" /></span>
</g:if>
<!--
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
-->
