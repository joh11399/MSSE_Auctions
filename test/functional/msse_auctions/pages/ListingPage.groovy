package msse_auctions.pages

import geb.Page

class ListingIndexPage extends Page {

    static url = 'listing/index'

    static content = {
        listing_timeRemaining { $(".timeRemaining") }
    }
}