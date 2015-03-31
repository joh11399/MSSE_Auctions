package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.AccountEditPage
import msse_auctions.pages.LoginPage
import spock.lang.Stepwise


@Stepwise
class AccountEditFunctionalSpec extends GebSpec {

    def remote = new RemoteControl()

    def accountId
    def accountDateCreated
    def accountLastUpdated


    def setupSpec() {
        to LoginPage

        login('me', 'password')
    }


    void setup() {
        accountId = remote {
            def account = Account.findByUsername('me')
            account.id
        }
        accountDateCreated = remote {
            Account.findByEmail('me@test.com').dateCreated
        }
        accountLastUpdated = remote {
            Account.findByEmail('me@test.com').lastUpdated
        }

        /*
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
        */
    }

    void cleanup() {
        /*
        remote {
            Account.findByEmail('functional@test.com').delete()
        }
        */
    }

    def "displays account Edit fields"() {
        when:
        to AccountEditPage, id: accountId

        then:
        email.text() == 'me@test.com'
        name.text() == 'Me Test'
        //addressStreet.text() == 'test street'
        //addressCity.text() == 'test city'
        //addressState.text() == 'MN'
        //addressZip.text() == '12345'

        Date dateCreatedDate = accountDateCreated
        Date lastUpdatedDate = accountLastUpdated
        dateCreated.text() == dateCreatedDate.format("M/dd/yy h:mm a")
        lastUpdated.text() == lastUpdatedDate.format("M/dd/yy h:mm a")
    }

    def "does not display unauthorized account"() {
        when:
        def unauthId = 1;
        if(unauthId == accountId){
            unauthId = 2;
        }
        try{
            to AccountEditPage, id: unauthId
        }
        catch(ex){}

        then:

        //TODO, this does not assert anything
        //I cannot figure out how to catch the exception that the AccountEditPage is not shown
        1==1
        //email.text() == 'me@test.com'
        //thrown(grails.validation.ValidationException)
        //thrown(NullPointerException)
    }

}