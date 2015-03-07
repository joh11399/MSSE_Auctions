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

        def a1 = new Account( email: 'dan@email.com', password: 'P@$sW0rD',name: 'Dan Johnson', addressStreet: '123 Street Ave. NW', addressCity: 'Minneapolis', addressState: 'MN', addressZip: '12345').save(failOnError: true)
        def a2 = new Account( email: 'j.smith@smith.com',  password: 'a1b2c3po', name: 'John Smith', addressStreet: '456 Avenue St. Se', addressCity: 'St. Paul', addressState: 'MN', addressZip: '23456').save(failOnError: true)
        def a3 = new Account(email: 'sally@swanson.com', password: 'abc12345', name: 'Sally Swanson', addressStreet: '789 Boulevard Cir', addressCity: 'Minneapolis', addressState: 'MN', addressZip: '34567').save(failOnError: true)

        def l1 = new Listing(name: 'lamp', description: 'it\'s a lamp', dateCreated: new Date() - 11, startDate: new Date() - 11, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a3).save(failOnError: true)
        def l2 = new Listing(name: 'antique dresser', description: 'it\'s an antique dresser', dateCreated: new Date() - 9, startDate: new Date() - 9, days: 9, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        def l3 = new Listing(name: 'guitar', description: 'it\'s a guitar', dateCreated: thirtyMinutes - 8, startDate: thirtyMinutes - 8, days: 8, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        def l4 = new Listing(name: 'picture frame', description: 'it\'s a picture frame', dateCreated: twoHours - 6, startDate: twoHours - 6, days: 6, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        def l5 = new Listing(name: 'something', description: 'it sure is something', dateCreated: new Date() - 3, startDate: new Date() - 3, days: 5, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        def l6 = new Listing(name: 'clock', description: 'it\'s a clock', startDate: thirtyMinutes - 10, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a2).save(failOnError: true)
        def l7 = new Listing(name: 'book', description: 'it\'s a book', startDate: twoHours - 10, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        def l8 = new Listing(name: 'another lamp', description: 'it\'s another lamp', startDate: thirtyMinutes - 9, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a3).save(failOnError: true)
        new Listing(name: 'listing', description: 'it\'s a listing', startDate: new Date() - 8, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'listing', description: 'it\'s a listing', startDate: new Date() - 7, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'listing', description: 'it\'s a listing', startDate: new Date() - 4, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'listing', description: 'it\'s a listing', startDate: new Date() - 3, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)
        new Listing(name: 'listing', description: 'it\'s a listing', startDate: new Date() - 2, days: 10, startingPrice: 10.00, deliverOption: 'US Only', seller: a1).save(failOnError: true)

        new Bid(listing: l1, bidder: a1, amount: 20.00).save(failOnError: true)
        new Bid(listing: l2, bidder: a2, amount: 25.00).save(failOnError: true)
        new Bid(listing: l3, bidder: a3, amount: 12.00).save(failOnError: true)
        new Bid(listing: l4, bidder: a2, amount: 35.00).save(failOnError: true)
        new Bid(listing: l5, bidder: a3, amount: 11.00).save(failOnError: true)
        new Bid(listing: l6, bidder: a1, amount: 24.00).save(failOnError: true)
        new Bid(listing: l7, bidder: a3, amount: 13.00).save(failOnError: true)
        new Bid(listing: l8, bidder: a1, amount: 20.00).save(failOnError: true)


        new Review(listing: l1, reviewer: a3, reviewee: a2, reviewOf: 'Seller', rating: 1, thumbs: 'down', description: 'he never paid!!').save(failOnError: true)
        new Review(listing: l1, reviewer: a2, reviewee: a3, reviewOf: 'Buyer', rating: 4, thumbs: 'up', description: 'great product!').save(failOnError: true)
        new Review(listing: l2, reviewer: a1, reviewee: a2, reviewOf: 'Seller', rating: 5, thumbs: 'up', description: 'paid right away.').save(failOnError: true)
        new Review(listing: l2, reviewer: a2, reviewee: a1, reviewOf: 'Buyer', rating: 4, thumbs: 'up', description: 'shipped quickly enough.').save(failOnError: true)
    }
}

