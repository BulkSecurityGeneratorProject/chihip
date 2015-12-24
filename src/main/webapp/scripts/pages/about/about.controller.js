'use strict';

angular.module('sample1App')
    .controller('Aboutcontroller', function ($scope, Principal) {
        Principal.identity().then(function(aboutview) {
            $scope.aboutview = aboutview;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });
