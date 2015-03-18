package msse_auctions

import grails.rest.RestfulController

class AccountRestController extends RestfulController<Account> {

    @SuppressWarnings("GroovyUnusedDeclaration")
    static responseFormats = ['json', 'xml']

    AccountRestController() {
        super(Account)
    }

    def index(Integer max, String q) {
        if (!q) {
            return super.index(max)
        }
        params.max = Math.min(max ?: 10, 100)

        def accounts = Account.where { name =~ "%${q.toLowerCase()}%" }.list(max: max)
        respond accounts, model: [accountCount: accounts.size()]
    }

}
