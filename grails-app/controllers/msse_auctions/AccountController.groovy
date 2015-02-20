package msse_auctions

import static org.springframework.http.HttpStatus.OK

class AccountController {

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Account.list(params), model:[accountInstanceCount: Account.count()]
    }

    def show(Account accountInstance) {
        respond accountInstance
    }

    def login = {}

    def authenticate = {
        def user = Account.findByUsernameAndPassword(params.username, params.password)
        if(user){
            session.user = user
            redirect(controller: 'Listing', action:"index")
        }else{

            flash.message = "Sorry, ${params.username}. Please try again."

            redirect(action:"login")
        }
    }

    def logout = {
        session.user = null
        redirect(action:"login")
    }

    def create() {
        respond new Account(params)
    }
    def save(Account accountInstance) {
        if (accountInstance.hasErrors()) {
            respond accountInstance.errors, view:'create'
            return
        }else{
            accountInstance.save()
            authenticate()
        }
    }

    def edit(Account accountInstance) {
        respond accountInstance
    }
    def update(Account accountInstance) {
        if (accountInstance == null) {
            notFound()
            return
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
