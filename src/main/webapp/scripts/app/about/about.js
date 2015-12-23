'use strict';

angular.module('sample1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('about', {
                parent: 'site',
                url: '/about',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/about/about.html',
                        controller: 'AboutController'
                    }
                },
                resolve: {

                }
            });
    });
