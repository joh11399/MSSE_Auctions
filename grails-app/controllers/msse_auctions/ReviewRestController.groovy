package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class ReviewRestController  {

    def springSecurityService
    def ReviewService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT', delete: 'DELETE']

    @SuppressWarnings("GroovyUnusedDeclaration")
    static responseFormats = ['json', 'xml']


    //@Secured(['ROLE_USER'])
    @Secured('permitAll()')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        def reviews = Review.list()
        respond reviews
    }


    @Secured(['ROLE_USER'])
    def show() {
        def account = springSecurityService.currentUser as Account
        Review reviewInstance = Review.findById(params.id)

        if (!reviewInstance) {
            response.status = 404;
            render "Not found"

            /*

            TODO..  is this access restriction necessary?
            index is not protected.  should this be?
            if not, you don't need ROLE_USER or an account variable here


            //only the reviewer and reviewee are authorized to access reviews
        } else if (reviewInstance.reviewer.username != account.username &&
                   reviewInstance.reviewee.username != account.username) {
            response.status = 401;
            render "Not authorized to view Review ID ${reviewInstance.id}"
            */
        } else {
            respond reviewInstance
        }
    }


    @Secured(['ROLE_USER'])
    def save() {
        Review reviewInstance = new Review()
        ReviewService.copyReviewFromSource(request.JSON, reviewInstance)

        if (reviewInstance.hasErrors() ||
            reviewInstance?.listing == null ||
            reviewInstance?.reviewer == null ||
            reviewInstance?.reviewee == null ||
            (reviewInstance?.reviewOf != "Buyer" && reviewInstance?.reviewOf != "Seller" ) ||
            reviewInstance?.rating == null ||
            (reviewInstance?.thumbs != "up" && reviewInstance?.thumbs != "down") ||
            reviewInstance?.description == null) {
            response.status = 400;
            render "Bad request.  The parameters provided caused an error: " + reviewInstance.errors
        } else {
            def account = springSecurityService.currentUser as Account
            if (!ReviewService.isValidReview(account, reviewInstance)) {
                response.status = 400;
                render "Sorry.  You need to be either the seller or the winner of the listing to submit a review."
            }else {
                reviewInstance.save(flush: true, failOnError: true)
                response.status = 201;
                render(contentType: 'text/json') {
                    [
                            'responseText': "Success!  Review ID ${reviewInstance.id} has been created.",
                            'id'    : reviewInstance.id
                    ]
                }
            }
        }
    }

    @Secured(['ROLE_USER'])
    def update() {
        if (!params.id) {
            response.status = 400;
            render "Bad request.  No Review ID provided."
            return
        }

        def account = springSecurityService.currentUser as Account
        def reviewInstance = Review.findById(params.id)

        if (reviewInstance.reviewer.username != account.username) {
            response.status = 401;
            render "Not authorized to update Review ID ${reviewInstance.id}"

        } else {
            def reviewClone = new Review()
            ReviewService.copyReviewFromSource(request.JSON, reviewClone)

            if (reviewClone.hasErrors()) {
                response.status = 400;
                render "Bad request.  The parameters provided caused an error: " + reviewClone.errors
                return
            }
            if (!ReviewService.isValidReview(account, reviewClone)) {
                response.status = 400;
                render "Sorry.  You need to be either the seller or the winner of the listing to update this review."
            } else {
                ReviewService.copyReviewFromSource(reviewClone, reviewInstance)
                reviewInstance.save(failOnError: true)
                render "Success!  Review ID ${reviewInstance.id} has been updated."
            }
        }
    }

    @Secured(['ROLE_USER'])
    def delete() {
        if (!params.id) {
            response.status = 400;
            render "Bad request.  No Review ID provided."
            return
        }

        def reviewInstance = Review.findById(params.id)
        if (!reviewInstance) {
            response.status = 404
            render "Not found"
            return
        }

        def account = springSecurityService.currentUser as Account
        if (reviewInstance.reviewer.username != account.username) {
            response.status = 401;
            render "Not authorized to delete Review ID ${reviewInstance.id}"
        }
        else{
            def reviewId = reviewInstance.id
            reviewInstance.delete(flush: true)
            render "Success!  Review ID ${reviewId} has been deleted."
        }
    }

}