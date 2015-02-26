package msse_auctions

import spock.lang.Specification

class AccountIntegrationSpec extends Specification {

    //def controller = new AccountController()

    void "unsuccessfully create an account:  duplicate email"() {
        given:
        new Account(email: 'accountIntegration@test.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        when:
        new Account(email: 'accountIntegration@test.com', password: 'danjohnson1',  name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

        then:
        thrown(grails.validation.ValidationException)
    }


    /*

    //TODO  this doesn't work.....

    package msse_auctions

    import grails.test.mixin.TestFor
    import spock.lang.Specification

    @TestFor(AccountController)
    class AccountShowSpec extends Specification {

        def "show account details"() {
            given:
            def newAccount = new Account(email: 'accountShow@test.com', password: 'danjohnson1', name: 'Dan Johnson', addressStreet: '123', addressCity: '456', addressState: 'MN', addressZip: '54321').save(failOnError: true)

            when:
            controller.show(newAccount)

            then:
            //verify the accountInstance has the correct parameters
            model.accountInstance.email == 'accountShow@test.com'
            model.accountInstance.password == 'danjohnson1'
            model.accountInstance.name == 'Dan Johnson'
            model.accountInstance.addressStreet == '123'
            model.accountInstance.addressCity == '456'
            model.accountInstance.addressState == 'MN'
            model.accountInstance.addressZip == '54321'
        }
    }*/


}
