'use strict';

angular.module('sample1App')
    .controller('BlogviewController', function ($scope, Blog, ParseLinks){

              $scope.blogs = [];
              $scope.predicate = 'id';
              $scope.reverse = true;
              $scope.page = 1;
              Blog.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                  $scope.links = ParseLinks.parse(headers('link'));
                  $scope.totalItems = headers('X-Total-Count');
                  $scope.blogs = result;
              });
              //$scope.loadAll = function() {
            //    console.log('We are querying blobs');
            //      Blog.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
            //          $scope.links = ParseLinks.parse(headers('link'));
            //          $scope.totalItems = headers('X-Total-Count');
            //          $scope.blogs = result;
            //      });
            //  };
    });
