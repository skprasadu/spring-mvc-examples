'use strict';

angular.module('appDynApp')

    .controller('renderAttributesCtrl', ['$http', '$scope', '$location','$window', function ($http, $scope, $location,$window) {
    	
		if($location.search().formid != undefined){
		    //alert($location.search().formid);
			$scope.formid = $location.search().formid;
		}
		if($location.search().dataid != undefined){
			$scope.dataid = $location.search().dataid;
		}
		if($location.search().app_name != undefined){
			$scope.app_name = $location.search().app_name;
		}
    	
        $scope.urlFormData = {};         // JavaScript needs an object to put our form's models into.
            	
        $scope.processForm = function () {
                $http.post( './saveForm?app_name=' +$scope.app_name + '&formid=' + $scope.formid+"&dataid="+$scope.dataid,$scope.urlFormData)
                .success(function (data, status, headers, config) {
                
            		if(data.success == true){
            			alert("Database updated successfully!!!");
            			$window.location.reload();
            		} else {
            			alert("Failed to save the data!, errorDetails=" + data.outcomeList);
            		}
                })
                .error(function(data, status, headers, config, statusText){
                	alert("Failed to save the data!, returned status" + status + " data =" + JSON.stringify(data));
                }
                );
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
