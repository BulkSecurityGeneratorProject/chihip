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
                        templateUrl: 'scripts/app/servicesview/servicesview.html',
                        controller: 'ServicesviewController'
                    }
                },
                resolve: {

                }
            });
    });
