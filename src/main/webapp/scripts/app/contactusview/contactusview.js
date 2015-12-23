'use strict';

angular.module('sample1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contactusview', {
                parent: 'site',
                url: '/contactus',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/contactusview/contactusview.html',
                        controller: 'ContactusviewController'
                    }
                },
                resolve: {

                }
            });
    });
