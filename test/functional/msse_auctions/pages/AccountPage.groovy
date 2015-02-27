package msse_auctions.pages

import geb.Page

class AccountGetPage extends Page {

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