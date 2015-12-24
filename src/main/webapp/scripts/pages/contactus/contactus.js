'use strict';

angular.module('sample1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contactus', {
                parent: 'site',
                url: '/contactus',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/pages/contactus/contactus.html',
                        controller: 'ContactusController'
                    }
                },
                resolve: {

                }
            });
    });
