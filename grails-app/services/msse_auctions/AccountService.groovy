package msse_auctions

class AccountService {

    def getAccountFromJson(def accountInstance, def jsonObject) {
        accountInstance.username = jsonObject.username ?: accountInstance.username
        accountInstance.email = jsonObject.email ?: accountInstance.email
        accountInstance.password = jsonObject.password ?: accountInstance.password
        accountInstance.name = jsonObject.name ?: accountInstance.name
        accountInstance.addressStreet = jsonObject.addressStreet ?: accountInstance.addressStreet
        accountInstance.addressCity = jsonObject.addressCity ?: accountInstance.addressCity
        accountInstance.addressState = jsonObject.addressState ?: accountInstance.addressState
        accountInstance.addressZip = jsonObject.addressZip ?: accountInstance.addressZip
        //does not return anything, the accountInstance values have been updated
    }

    def validateAccount(def accountInstance) {

        //.hasErrors() isn't catching issues with null values
        //  when testing, .hasError() will return false, but then a server error will occur when saving.
        if (accountInstance.hasErrors() ||
                accountInstance?.username == null ||
                accountInstance?.email == null ||
                accountInstance?.password == null ||
                accountInstance?.name == null ||
                accountInstance?.addressStreet == null ||
                accountInstance?.addressCity == null ||
                accountInstance?.addressState == null ||
                accountInstance?.addressZip == null) {
            return 400;
        } else {
            def isNewAccount = accountInstance.id == null
            if(isNewAccount){
                def duplicateAccount = Account.findByUsername(accountInstance.username)
                if(duplicateAccount){
                    return 409
                }
            }

            return isNewAccount ? 201 : 200
        }
    }
}