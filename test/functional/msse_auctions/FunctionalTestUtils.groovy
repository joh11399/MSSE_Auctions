package msse_auctions

import grails.plugin.remotecontrol.RemoteControl

class FunctionalTestUtils {

    Integer accountId
    Integer listingId
    Integer bidId
    Integer reviewId

    @Delegate HttpUtils httpUtils = new HttpUtils()

    def setupLogIn(def user, def pw){
        httpUtils.doFormPost('j_spring_security_check', [j_username: user, j_password: pw])
    }
    def setupSampleData() {
        def response = setupLogIn('me', 'password')
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

        listingId = remote {
            Listing.findByName('testCompleted').id
        } as Integer

        bidId = remote {
            def listing = Listing.findByName('testCompleted')
            def account = Account.findByUsername('test')
            Bid.findByListingAndBidder(listing, account).id
        } as Integer

        reviewId = remote {
            def listing = Listing.findByName('testCompleted')
            def account = Account.findByUsername('me')
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