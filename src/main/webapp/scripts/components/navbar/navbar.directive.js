'use strict';

angular.module('sample1App')
    .directive('doug',['$location', function(location) {
        return {
            restrict: 'A',
            link: function(scope, element) {
                var $ul = $(element);
                //$ul.addClass("nav nav-tabs");
                $ul.addClass("sf-menu");
                $ul.superfish();
                var $tabs = $ul.children();
                var tabMap = {};
                $tabs.each(function() {
                  var $li = $(this);
                  //Substring 1 to remove the # at the beginning (because location.path() below does not return the #)
                  tabMap[$li.find('a').attr('href').substring(1)] = $li;
                });

                scope.location = location;
                scope.$watch('location.path()', function(newPath) {
                    $tabs.removeClass("current");
                    tabMap[newPath].addClass("current");
                });
            }
        };
    }]);
