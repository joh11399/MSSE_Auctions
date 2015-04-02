package msse_auctions

class AccountService {

    def copyAccountFromSource(def src, Account dest) {
        //if the source does not have a value, attempt to use the existing destination value
        dest.username = src?.username ?: dest.username
        dest.email = src?.email ?: dest.email
        dest.password = src?.password ?: dest.password
        dest.name = src?.name ?: dest.name
        dest.addressStreet = src?.addressStreet ?: dest.addressStreet
        dest.addressCity = src?.addressCity ?: dest.addressCity
        dest.addressState = src?.addressState ?: dest.addressState
        dest.addressZip = src?.addressZip ?: dest.addressZip
        //does not return anything, the dest values have been updated
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