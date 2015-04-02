package msse_auctions.pages

import geb.Page

class ReviewCreatePage extends Page {

    static url = 'review/create'

    static content = {
        submitBtn { $(".save") }
        listingLbl { $("#listingLbl") }
        listing { $("#listing") }
        reviewerLbl { $("#reviewerLbl") }
        reviewer { $("#reviewer") }
        revieweeLbl { $("#revieweeLbl") }
        reviewee { $("#reviewee") }
        reviewOfLbl { $("#reviewOfLbl") }
        reviewOf { $("#reviewOf") }

        rating { $("#rating") }
        thumbs { $("#thumbs") }
        description { $("#description") }
    }
}
