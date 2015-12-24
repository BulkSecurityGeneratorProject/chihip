'use strict';

angular.module('sample1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('services', {
                parent: 'site',
                url: '/services',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/pages/services/services.html',
                        controller: 'ServicesController'
                    }
                },
                resolve: {

                }
            });
    });
