package msse_auctions

import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize

import static org.springframework.http.HttpStatus.OK

class AccountController {

    def springSecurityService

    @Secured('permitAll()')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        respond Account.list(params), model:[accountInstanceCount: Account.count()]
    }

    @Secured(['ROLE_USER'])
    def show(Account accountInstance) {
//TODO  this is a good opportunity for a service   (you have several of these)....
        def account = springSecurityService.currentUser as Account
        if(accountInstance.username!=account.username) {
            flash.message = 'Not authorized to view account ' + accountInstance.id
            redirect(action: 'index')
        }else{
            def reviewList = Review.findByReviewee( accountInstance )

            respond accountInstance, model:[reviewList: reviewList]
        }
    }

    @Secured('permitAll()')
    def create() {
        respond new Account(params)
    }
    @Secured('permitAll()')
    def save(Account accountInstance) {
        if (accountInstance.hasErrors()) {
            respond accountInstance.errors, view:'create'
        }else{
            if(accountInstance.validatePasswordComplexity(accountInstance.password)) {
                accountInstance.save(flush: true, failOnError: true)
                new AccountRole(account: accountInstance, role: Role.findByAuthority('ROLE_USER')).save(flush: true, failOnError: true)
                redirect(action: 'index')
            }
            else{
                flash.message = 'Invalid password.  Passwords must be between 8-16 characters, containing a number and a letter.'
                redirect(action: 'create', model: [accountInstance: accountInstance])
            }
        }
    }
    /*
    TODO  remove this....
    @Secured(closure = {
        def account = request.requestURI.substring(request.requestURI.lastIndexOf('/')+1)

        println(account)
        //println(params)
        println(request.getParameterMap())
        println(authentication.principal.username)
        println(authentication.principal)

        authentication.principal.username == account
    })
    @PreAuthorize('isAuthenticated() and principal?.username == #accountInstance.username')
    */
    @Secured(['ROLE_USER'])
    def edit(Account accountInstance) {
        def account = springSecurityService.currentUser as Account
        if(accountInstance.username!=account.username) {
            flash.message = 'Not authorized to edit account ' + accountInstance.id
            redirect(action: 'index')
        }else{
            respond accountInstance
        }
    }

    @Secured(['ROLE_USER'])
    def update(Account accountInstance) {
        def account = springSecurityService.currentUser as Account
        if(accountInstance.username!=account.username) {
            flash.message = 'Not authorized to edit account ' + accountInstance.id
            redirect(action: 'index')
            return
        }else{
            respond accountInstance
        }

        if (accountInstance.hasErrors()) {
            respond accountInstance.errors, view:'edit'
            return
        }

        accountInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Account.label', default: 'Account'), accountInstance.id])
                redirect accountInstance
            }
            '*'{ respond accountInstance, [status: OK] }
        }
    }
}
