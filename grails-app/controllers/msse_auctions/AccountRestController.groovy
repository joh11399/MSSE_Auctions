package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class AccountRestController {

    def springSecurityService
    def AccountService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT', delete: 'DELETE']

    @SuppressWarnings("GroovyUnusedDeclaration")
    static responseFormats = ['json', 'xml']

    @Secured('permitAll()')
    def index(Integer max, String q) {
        params.max = Math.min(max ?: 10, 100)

        def accounts = Account.list()
        if(q) {
            accounts = Account.where { name =~ "%${q.toLowerCase()}%" }.list(max: max)
        }

        respond accounts
    }

    @Secured(['ROLE_USER'])
    def show() {
        def account = springSecurityService.currentUser as Account
        Account accountInstance = Account.findById(params.id)

        if(!accountInstance){
            response.status = 404
            render "Not found"
            return
        }
        if (accountInstance.username != account.username) {
            response.status = 401;
            render "Not authorized to view Account ID ${accountInstance.id}"
        } else {
            respond accountInstance
        }
    }


    @Secured('permitAll()')
    def save() {
        Account accountInstance = new Account();
        AccountService.copyAccountFromSource(request.JSON, accountInstance)
        response.status = AccountService.validateAccount(accountInstance)

        if (response.status == 400) {
            render "Bad request.  The parameters provided caused an error: " + accountInstance.errors
        } else if (response.status == 409) {
            render "Duplicate account.  The username and email must be unique.  Error: " + accountInstance.errors
        } else {
            accountInstance.save(flush: true, failOnError: true)
            def r = Role.findByAuthority('ROLE_USER')
            new AccountRole(account: accountInstance, role: r).save(flush: true, failOnError: true)
            render(contentType: 'text/json') {
                [
                        'responseText': "Success!  Account ID ${accountInstance.id} has been created.",
                        'id'    : accountInstance.id
                ]
            }
        }
    }

    @Secured(['ROLE_USER'])
    def update() {
        if (!params.id) {
            response.status = 400;
            render "Bad request.  No Account ID provided."
            return
        }

        def account = springSecurityService.currentUser as Account
        Account accountInstance = Account.findById(params.id)
        //if the user's account doesn't match the accountInstance  return a 401
        if (accountInstance?.username != account?.username) {
            response.status = 401;
            render "Not authorized to update Account ID ${accountInstance.id}"
        } else {
            def accountClone = new Account()
            AccountService.copyAccountFromSource(request.JSON, accountClone)
            response.status = AccountService.validateAccount(accountClone)

            if (response.status == 400) {
                render "Bad request.  The parameters provided caused an error: " + accountClone.errors
                return
            }
            else {
                if (response.status == 409) {
                    //this could be redesigned, but I'll just change it here
                    //if the response is a 409, that is still acceptable for an update
                    // just change it to a 200  (because, by now, all other errors would have been caught)
                    response.status = 200
                }

                AccountService.copyAccountFromSource(accountClone, accountInstance)

                println(accountClone.name)
                println(accountInstance.name)

                accountInstance.save(flush: true, failOnError: true)

                render(contentType: 'text/json') {[
                        'responseText': "Success!  Account ID ${accountInstance.id} has been updated.",
                        'id': accountInstance.id
                ]}
            }
        }
    }

    @Secured(['ROLE_USER'])
    def delete() {
        def account = springSecurityService.currentUser as Account
        if (!params.id) {
            response.status = 400;
            render "Bad request.  No Account ID provided."
            return
        }

        Account accountInstance = Account.findById(params.id)
        if (!accountInstance) {
            response.status = 404
            render "Not found"
            return
        }

        //if the user's account doesn't match the accountInstance  return a 401
        if (accountInstance?.username != account?.username) {
            response.status = 401;
            render "Not authorized to delete Account ID ${accountInstance.id}"
            return
        }
        def accountId = accountInstance.id

        //all foreign key associations must be deleted as well..
        Bid.findByBidder(accountInstance)*.delete(flush: true)
        Review.findByReviewer(accountInstance)*.delete(flush: true)
        Review.findByReviewee(accountInstance)*.delete(flush: true)
        Listing.findBySeller(accountInstance)*.delete(flush: true)
        AccountRole.findByAccount(accountInstance)*.delete(flush: true)

        accountInstance.delete(flush: true)
        response.status = 200
        render "Success!  Account ID ${accountId} has been deleted."
    }
}