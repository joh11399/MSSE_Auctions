package msse_auctions.pages

import geb.Page

class LoginPage extends Page {

    static url = 'login/auth'

    static content = {
        username { $("#username") }
        password { $("#password ") }
        submit { $("#submit") }
    }

    def login(String user, String passwd) {
        username.value(user)
        password.value(passwd)
        submit.click()
    }
}