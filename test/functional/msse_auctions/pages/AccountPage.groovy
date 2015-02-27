package msse_auctions.pages

import geb.Page

class AccountShowPage extends Page {

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
class AccountEditPage extends Page{
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