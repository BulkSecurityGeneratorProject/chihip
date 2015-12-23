'use strict';

angular.module('sample1App')
    .factory('Blog', function ($resource, DateUtils) {
        return $resource('api/blogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.publishon = DateUtils.convertLocaleDateFromServer(data.publishon);
                    data.created = DateUtils.convertLocaleDateFromServer(data.created);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.publishon = DateUtils.convertLocaleDateToServer(data.publishon);
                    data.created = DateUtils.convertLocaleDateToServer(data.created);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.publishon = DateUtils.convertLocaleDateToServer(data.publishon);
                    data.created = DateUtils.convertLocaleDateToServer(data.created);
                    return angular.toJson(data);
                }
            }
        });
    });
