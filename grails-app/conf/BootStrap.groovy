import grails.util.Environment
import groovy.time.TimeCategory
import msse_auctions.Listing
import msse_auctions.Bid
import msse_auctions.Account
import msse_auctions.Review

class BootStrap {

    def init = { servletContext ->
        environments{

            if(Environment != Environment.PRODUCTION) {
                addH2items()
            }

            development {

            }

            test{

            }
        }
    }
    def destroy = {
    }

    def addH2items(){
        def thirtyMinutes = new Date()
        def twoHours = new Date()
        use( TimeCategory ) {
            thirtyMinutes = thirtyMinutes + 30.minutes
            twoHours = twoHours + 2.hours
        }

        def a1 = new Account( email: 'dan1@dan.com', password: 'P@$sW0rD',name: 'Dan Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        def a2 = new Account( email: 'dan2@dan.com',  password: 'a1b2c3po', name: 'Dan Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
        def a3 = new Account(email: 'dan3@dan.com', password: 'abc12345', name: 'Heather Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)

        def l1 = new Listing(name: 'lamp', description: 'it\'s a lamp', dateCreated: new Date() - 11, startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a3).save(failOnError: true)
        def l2 = new Listing(name: 'antique dresser', description: 'it\'s an antique dresser', dateCreated: new Date() - 9, startDate: new Date() - 9, days: 9, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        def l3 = new Listing(name: 'clock', description: 'it\'s a clock', dateCreated: thirtyMinutes - 8, startDate: thirtyMinutes - 8, days: 8, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        def l4 = new Listing(name: 'guitar', description: 'it\'s a guitar', dateCreated: twoHours - 6, startDate: twoHours - 6, days: 6, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        def l5 = new Listing(name: 'picture frame', description: 'it\'s a picture frame', dateCreated: new Date() - 3, startDate: new Date() - 3, days: 5, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'test4', description: 'it\'s a test', startDate: new Date() - 1, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'test5', description: 'it\'s a test', startDate: new Date() - 1, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'test6', description: 'it\'s a test', startDate: new Date() - 1, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'test7', description: 'it\'s a test', startDate: new Date() - 1, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'test8', description: 'it\'s a test', startDate: new Date() - 1, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'test9', description: 'it\'s a test', startDate: new Date() - 1, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'test10', description: 'it\'s a test', startDate: new Date() - 1, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'test11', description: 'it\'s a test', startDate: new Date() - 1, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)

        new Bid(listing: l1, bidder: a1, amount: 20.00).save(failOnError: true)
        new Bid(listing: l2, bidder: a2, amount: 25.00).save(failOnError: true)
        new Bid(listing: l3, bidder: a1, amount: 12.00).save(failOnError: true)

        new Review(listing: l1, reviewer: a1, reviewee: a2, reviewOf: 'Buyer', rating: 2, thumbs: 'down', description: 'he never paid!!').save(failOnError: true)
        new Review(listing: l1, reviewer: a2, reviewee: a1, reviewOf: 'Seller', rating: 4, thumbs: 'up', description: 'great product!').save(failOnError: true)
    }
}

