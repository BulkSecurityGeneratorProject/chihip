'use strict';

angular.module('sample1App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


