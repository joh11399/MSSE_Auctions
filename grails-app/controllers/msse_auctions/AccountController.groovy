package msse_auctions

class AccountController {

    def index() {
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


            //TODO: remove this....
            /////////////////////flash.message = "Sorry, ${params.username}. Please try again."


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
}
