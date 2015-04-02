package msse_auctions.pages

import geb.Page

class BidCreatePage extends Page{
    static url = 'bid/create'

    static content = {
        listing { $("#listing") }
        listingLbl { $("#listingLbl") }
        bidder { $("#bidder") }
        bidderLbl { $("#bidderLbl") }
    }
}