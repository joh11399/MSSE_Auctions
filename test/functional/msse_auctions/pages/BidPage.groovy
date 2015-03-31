package msse_auctions.pages

import geb.Page

//TODO.. these are unused.........

class BidShowPage extends Page {

    static url = 'account/show'

    static content = {
        name { $("#name") }
        email { $("#email") }
        addressStreet { $("#addressStreet") }
        addressCity { $("#addressCity") }
        addressState { $("#addressState") }
        addressZip { $("#addressZip") }
        dateCreated { $("#dateCreated") }
        lastUpdated { $("#lastUpdated") }
    }
}
class BidEditPage extends Page{
    static url = 'account/show'

    static content = {
        name { $("#name") }
        email { $("#email") }
        addressStreet { $("#addressStreet") }
        addressCity { $("#addressCity") }
        addressState { $("#addressState") }
        addressZip { $("#addressZip") }
        dateCreated { $("#dateCreated") }
        lastUpdated { $("#lastUpdated") }
    }
}