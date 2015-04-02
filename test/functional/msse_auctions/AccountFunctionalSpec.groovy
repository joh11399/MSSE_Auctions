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
            /*def account = new Account(username: 'functional', email: 'functional@test.com', password: 'abcd1234', name: 'Functional Test', addressStreet: 'test street', addressCity: 'test city', addressState: 'MN', addressZip: '12345')
            account.save(flush: true, failOnError: true)
            account.id*/
            Account.findByUsername('me').id
        }
        accountId2 = remote {
            def account = Account.findByUsername('test')
            account.id
        }
        accountDateCreated = remote {
            Account.findByUsername('me').dateCreated
            //Account.findByEmail('functional@test.com').dateCreated
        }
        accountLastUpdated = remote {
            Account.findByUsername('me').lastUpdated
            //Account.findByEmail('functional@test.com').lastUpdated
        }
    }

    def "gets account details"() {
        when:

        to LoginPage
        login('me', 'abcd1234')
        to AccountShowPage, id: accountId

        /*
        if(!AccountRole.findByAccount(account)) {
            println('=======================')
            println('=======================')
            println(account)

            def r = Role.findByAuthority('ROLE_USER')

            println(r.authority)

            new AccountRole(account: account, role: r).save(flush: true, failOnError: true)

            println(AccountRole.findByAccount(account))
            println('=======================')
            println('=======================')
            println(account.id)
            println(account)
        }*/

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