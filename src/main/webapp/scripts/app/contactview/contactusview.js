'use strict';

angular.module('sample1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('servicesview', {
                parent: 'site',
                url: '/services',
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
