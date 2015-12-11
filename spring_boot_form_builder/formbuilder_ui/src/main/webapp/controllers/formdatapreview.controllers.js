'use strict';

angular.module('appDynApp')

    .controller('designOfFormPreviewCtrl', ['$http', '$scope', '$location','$window', function ($http, $scope, $location,$window) {
    	
		if($location.search().formid != undefined){
		    //alert($location.search().formid);
			$scope.formid = $location.search().formid;
		}
		if($location.search().app_name != undefined){
			$scope.app_name = $location.search().app_name;
		}
    	
        $scope.urlFormData = {};         // JavaScript needs an object to put our form's models into.
            	
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
