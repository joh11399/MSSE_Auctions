package msse_auctions.pages

import geb.Page

class ListingIndexPage extends Page {
    static url = 'listing/index'

    static content = {
        listing_timeRemaining { $(".timeRemaining") }
    }
}
class ListingCreatePage extends Page {
    static url = 'listing/create'

    static content = {
        seller { $("#seller") }
        sellerLbl { $("#sellerLbl") }
    }
}