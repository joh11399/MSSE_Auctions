package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.AccountGetPage

class AccountFunctionalSpec extends GebSpec {

    def remote = new RemoteControl()

    def accountId
    def accountDateCreated
    def accountLastUpdated

    void setup() {
        accountId = remote {
            def account = new Account(email: 'functional@test.com', password: 'P@$sW0rD', name: 'Functional Test', addressStreet: 'test street', addressCity: 'test city', addressState: 'MN', addressZip: '12345')
            account.save(failOnError: true)
            account.id
        }
        accountDateCreated = remote {
            Account.findByEmail('functional@test.com').dateCreated
        }
        accountLastUpdated = remote {
            Account.findByEmail('functional@test.com').lastUpdated
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
        email.text() == 'functional@test.com'
        name.text() == 'Functional Test'
        addressStreet.text() == 'test street'
        addressCity.text() == 'test city'
        addressState.text() == 'MN'
        addressZip.text() == '12345'

        Date dateCreatedDate = accountDateCreated
        Date lastUpdatedDate = accountLastUpdated
        dateCreated.text() == dateCreatedDate.format("M/dd/yy h:mm a")
        lastUpdated.text() == lastUpdatedDate.format("M/dd/yy h:mm a")
    }
}