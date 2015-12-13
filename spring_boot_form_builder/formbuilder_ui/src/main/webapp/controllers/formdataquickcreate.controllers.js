'use strict';

angular.module('appDynApp')

    .controller('renderQuickDesignOfFormsCtrl', ['$http', '$scope', '$location','$window', function ($http, $scope, $location,$window) {
    	
		if($location.search().app_name != undefined){
			$scope.app_name = $location.search().app_name;
		}
    	
        $scope.urlFormData = {};         // JavaScript needs an object to put our form's models into.
            	
        $scope.processForm = function () {
            $http.post( './saveQuickDesignOfForm?app_name=' +$scope.app_name,$scope.urlFormData)
            .success(function (data, status, headers, config) {
            	alert("Database updated successfully!!!");
            })
            .error(function(data, status, headers, config, statusText){
            	alert("Failed to save the data!, returned status" + status + " data =" + JSON.stringify(data));
            });
        };
    }])
    .filter('pretty', function() {
        return function (input) {
            var temp;
            try {
                temp = angular.fromJson(input);
            }
            catch (e) {
                temp = input;
            }

            return angular.toJson(temp, true);
        };
    });
