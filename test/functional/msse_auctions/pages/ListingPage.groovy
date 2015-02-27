package msse_auctions.pages

import geb.Page

class ListingIndexPage extends Page {

    static url = 'listing/index'

    static content = {
        listing_0_bidLink { $("#listing_0_bidLink") }
        listing_0_rateSellerLink { $("#listing_0_rateSellerLink") }
        listing_0_rateBuyerLink { $("#listing_0_rateBuyerLink") }
        listing_0_timeRemaining { $("#listing_0_timeRemaining") }

        listing_1_bidLink { $("#listing_1_bidLink") }
        listing_1_rateSellerLink { $("#listing_1_rateSellerLink") }
        listing_1_rateBuyerLink { $("#listing_1_rateBuyerLink") }
        listing_1_timeRemaining { $("#listing_1_timeRemaining") }

        listing_2_bidLink { $("#listing_2_bidLink") }
        listing_2_rateSellerLink { $("#listing_2_rateSellerLink") }
        listing_2_rateBuyerLink { $("#listing_2_rateBuyerLink") }
        listing_2_timeRemaining { $("#listing_2_timeRemaining") }
    }
}