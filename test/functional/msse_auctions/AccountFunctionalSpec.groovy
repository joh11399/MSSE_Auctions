package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.AccountGetPage

class AccountFunctionalSpec extends GebSpec {

    def remote = new RemoteControl()

    def accountId

    void setup() {
        accountId = remote {
            def account = new Account(email: 'functional@test.com', password: 'P@$sW0rD', name: 'Functional Test', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd')
            account.save(failOnError: true)
            account.id
        }
    }

    void cleanup() {
        remote {
            Account.findByEmail('functional@test.com').delete()
        }
    }

    def "gets account details"() {
        when:
        to AccountGetPage, id: accountId

        then:
        email.value() == 'functional@test.com'
        name.value() == 'Functional Test'
    }
}