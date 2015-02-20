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

                def thirtyMinutes = new Date()
                def twoHours = new Date()
                use( TimeCategory ) {
                    thirtyMinutes = thirtyMinutes + 30.minutes
                    twoHours = twoHours + 2.hours
                }

                def u1 = new Account(username: 'joh11399', password: 'P@$sW0rD', email: 'dan@dan.com', fullName: 'Dan Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
                def u2 = new Account(username: 'dan', password: '1q', email: 'dan@dan.com', fullName: 'Dan Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)
                def u3 = new Account(username: 'haj11399', password: 'a12345', email: 'dan@dan.com', fullName: 'Heather Johnson', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)

                def a1 = new Listing(name: 'first', description: 'first auction', dateCreated: new Date() - 11, begDate: new Date() - 11, days: 10, minAmount: 10.00, deliverOption: 'US Only', seller: u3).save(failOnError: true)
                def a2 = new Listing(name: 'second', description: 'second auction', dateCreated: new Date() - 9, begDate: new Date() - 9, days: 9, minAmount: 10.00, deliverOption: 'US Only', seller: u1).save(failOnError: true)
                def a3 = new Listing(name: 'lamp', description: 'lamp', dateCreated: thirtyMinutes - 8, begDate: thirtyMinutes - 8, days: 8, minAmount: 10.00, deliverOption: 'US Only', seller: u1).save(failOnError: true)
                def a4 = new Listing(name: 'guitar', description: 'guitar', dateCreated: twoHours - 6, begDate: twoHours - 6, days: 6, minAmount: 10.00, deliverOption: 'US Only', seller: u1).save(failOnError: true)
                def a5 = new Listing(name: 'picture frame', description: 'picture frame', dateCreated: new Date() - 3, begDate: new Date() - 3, days: 5, minAmount: 10.00, deliverOption: 'US Only', seller: u1).save(failOnError: true)

                new Bid(listing: a1, bidder: u1, amount: 20.00).save(failOnError: true)
                new Bid(listing: a2, bidder: u2, amount: 25.00).save(failOnError: true)
                new Bid(listing: a3, bidder: u1, amount: 12.00).save(failOnError: true)

                new Review(listing: a1, reviewer: u1, reviewee: u2, revieweeType: 'Buyer', rating: 2, thumbs: 'down', description: 'he never paid!!').save(failOnError: true)
                new Review(listing: a1, reviewer: u2, reviewee: u1, revieweeType: 'Seller', rating: 4, thumbs: 'up', description: 'great product!').save(failOnError: true)
            }




            development {

/*
                a1.addToBids(b1)
                u1.addToBids(b1)
*/



                if (!Account.count()) {


                    //new User(username: 'Haj', password: 'ppppP@$sW*rD', fullName: 'Heather Johnson', email: 'd', addressStreet: 'd', addressCity: 'd', addressState: 'MN', addressZip: 'd').save(failOnError: true)

                    /*
                    [
                            'Dan Johnson': ['Blue Monday', 'Temptation', 'True Faith'],
                            'Heather Johnson': ['One More Time', 'Lucky', 'Around the World']
                    ].each { def name, def tracks ->
                        def user = new User(name: name)
                        user.save(failOnError: true)
                        tracks.each { def track ->
                            new Song(artist: artist, title: track).save(failOnError: true)
                        }
                        log.info("Saved artist ${artist.name} (${artist.id})")
                    }
                    */
                }

                if (!Listing.count()) {

/*
                    println('added auction test1')
                    new Auction(id: 'test2', description: 'here, two..', endDate: Date.parse('MM/dd/yyyy', '04/01/2015'), user: User.findByUsername('Haj'), billingType: 'Hourly').save(failOnError: true)
                    println('added auction test2')
                    new Auction(id: 'test3', description: 'ok, hereTHREE', endDate: Date.parse('MM/dd/yyyy', '05/01/2015'), user: User.findByUsername('joh11399'), billingType: 'Hourly').save(failOnError: true)
                    println('added auction test3')*/
                }

                if (!Bid.count()) {
                }
            }

            test{

            }
        }
    }
    def destroy = {
    }
}