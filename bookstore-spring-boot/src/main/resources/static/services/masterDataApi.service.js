'use strict';

angular.module('appDoCFD')

.factory('masterDataAPI', ['$q', '$http', 'masterDataURL', function($q, $http, masterDataURL) {
    return new function() {

        this.getData = function() {
            var deferred = $q.defer();

            $http.get(masterDataURL).
                success(function(data, status, headers, config) {
                    deferred.resolve(data);
                }).
                error(function(data, status, headers, config) {
                    deferred.reject([data,status,headers,config]);
                });

            return deferred.promise;
        }

    };
}]);