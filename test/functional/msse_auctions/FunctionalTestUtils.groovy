package msse_auctions

import grails.plugin.remotecontrol.RemoteControl

class FunctionalTestUtils {

    Integer accountId
    Integer accountId2
    def accountTest1 = [id: 0, username: '', password: '']
    def accountTest2 = [id: 0, username: '', password: '']

    Integer listingCompletedId
    Integer listingOpenId
    Integer bidId
    Integer reviewId

    @Delegate HttpUtils httpUtils = new HttpUtils()

    def setupLogIn(def user, def pw){
        httpUtils.doFormPost('j_spring_security_check', [j_username: user, j_password: pw])
    }
    def setupLogOut(def user){
        httpUtils.doFormPost('j_spring_security_logout', [j_username: user])
    }
    def setupSampleData() {
        def response = setupLogIn('me', 'abcd1234')
        assert response.status == 302
        assert response.statusText == 'Found'

        def remote = new RemoteControl()
        /*
        remote {
            return
        }*/

        accountId = remote {
            Account.findByUsername('me').id
        } as Integer
        accountId2 = remote {
            Account.findByUsername('test').id
        } as Integer

        accountTest1.id = remote { Account.findByUsername('me').id } as Integer
        accountTest1.username = remote { Account.findByUsername('me').username } as String
        accountTest1.password = "abcd1234"//remote { Account.findByUsername('me').password } as String
        accountTest2.id = remote { Account.findByUsername('test').id } as Integer
        accountTest2.username = remote { Account.findByUsername('test').username } as String
        accountTest2.password = "abcd1234"//remote { Account.findByUsername('test').password } as String

        listingCompletedId = remote {
            Listing.findByName('testCompleted').id
        } as Integer

        listingOpenId = remote {
            Listing.findByName('testOpen').id
        } as Integer

        bidId = remote {
            def listing = Listing.findByName('testCompleted')
            def account = Account.findByUsername('test')
            Bid.findByListingAndBidder(listing, account).id
        } as Integer

        reviewId = remote {
            def listing = Listing.findByName('testCompleted')
            def account = Account.findByUsername('test')
            Review.findByListingAndReviewer(listing, account).id
        } as Integer
    }

    def cleanupSampleData() {
        /*
        def remote = new RemoteControl()
        remote {
            def review = Review.findById(reviewId)
            if(review){
                review.delete()
            }
            def bid = Bid.findById(bidId)
            if(bid){
                bid.delete()
            }
            def listing = Listing.findById(listingId)
            if(listing){
                listing.delete()
            }
            def accountRole = AccountRole.findById(accountRoleId)
            if(accountRole){
                accountRole.delete()
            }
            def account = Account.findByUsername('test')
            if(account){
                account.delete()
            }
        }
        */
    }
}