package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.AccountShowPage
import msse_auctions.pages.LoginPage
import spock.lang.Stepwise

@Stepwise
class AccountFunctionalSpec extends GebSpec {

    def remote = new RemoteControl()

    def accountId
    def accountDateCreated
    def accountLastUpdated

    void setup() {
        accountId = remote {
            /*def account = new Account(username: 'functional', email: 'functional@test.com', password: 'password', name: 'Functional Test', addressStreet: 'test street', addressCity: 'test city', addressState: 'MN', addressZip: '12345')
            account.save(flush: true, failOnError: true)
            account.id*/
            Account.findByUsername('me').id
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

    void cleanup() {
        remote {
            /*
            def account = Account.findById(accountId)
            AccountRole.findByAccount(account).delete()
            Account.findByEmail('functional@test.com').delete()
             */
        }
    }

    def "gets account details"() {
        when:

        to LoginPage
        login('me', 'password')
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
}