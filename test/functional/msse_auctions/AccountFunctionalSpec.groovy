package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.AccountEditPage
import msse_auctions.pages.AccountShowPage
import msse_auctions.pages.LoginPage
import spock.lang.Stepwise

@Stepwise
class AccountFunctionalSpec extends GebSpec {

    def remote = new RemoteControl()

    def accountId
    def accountId2
    def accountDateCreated
    def accountLastUpdated

    void setup() {
        accountId = remote {
            Account.findByUsername('me').id
        }
        accountId2 = remote {
            def account = Account.findByUsername('test')
            account.id
        }
        accountDateCreated = remote {
            Account.findByUsername('me').dateCreated
        }
        accountLastUpdated = remote {
            Account.findByUsername('me').lastUpdated
        }
    }

    def "gets account details"() {
        when:

        to LoginPage
        login('me', 'abcd1234')
        to AccountShowPage, id: accountId

        then:
        email.text() == 'me@test.com'
        name.text() == 'Me Test'
        addressStreet.text() == '123 Street Ave. NW'
        addressCity.text() == 'Minneapolis'
        addressState.text() == 'MN'
        addressZip.text() == '12345'

        Date dateCreatedDate = accountDateCreated
        Date lastUpdatedDate = accountLastUpdated
        dateCreated.text() == dateCreatedDate.format("M/dd/yy h:mm a")
        lastUpdated.text() == lastUpdatedDate.format("M/dd/yy h:mm a")
    }


    def "does not display details for unauthorized account"() {
        when:
        to AccountShowPage, id: accountId2

        then:
        $('body').text().toString().indexOf("Not authorized to view account ${accountId2}") != -1
    }



    def "displays account Edit fields for authorized users"() {
        when:
        to AccountEditPage, id: accountId

        then:
        email.text() == 'me@test.com'
        name.text() == 'Me Test'
        Date dateCreatedDate = accountDateCreated
        Date lastUpdatedDate = accountLastUpdated
        dateCreated.text() == dateCreatedDate.format("M/dd/yy h:mm a")
        lastUpdated.text() == lastUpdatedDate.format("M/dd/yy h:mm a")

        $('body').text().toString().indexOf("Not authorized to view account ${accountId}") == -1
    }

    def "does not display Edit page for unauthorized account"() {
        when:
        to AccountEditPage, id: accountId2

        then:
        $('body').text().toString().indexOf("Not authorized to view account ${accountId2}") != -1
    }

}