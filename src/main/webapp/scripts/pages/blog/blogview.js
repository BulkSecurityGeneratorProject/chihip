'use strict';

angular.module('sample1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('blogview', {
                parent: 'site',
                url: '/blogview',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/pages/blog/blogview.html',
                        controller: 'BlogviewController'
                    }
                },
                resolve: {

                }
            });
    });
