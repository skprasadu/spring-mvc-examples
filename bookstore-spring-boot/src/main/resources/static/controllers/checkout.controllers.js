'use strict';

angular.module('appDoCFD')

    .controller('checkoutCtrl', ['$scope', '$http', 'saveFormDataSvc', function($scope, $http, saveFormDataSvc) {

        $scope.messages = [];
    	$scope.status={};
		$scope.status.message="";
        $http.get('./attributeList').success(function (data, status, headers, config) {
            $scope.messages = data;
        }).error(function (data, status, headers, config) {
             $scope.status.message="Can't retrieve messages list!";
			 $scope.$emit('updateErrorStatus',$scope.status);
        });

        $scope.deleteAttribute = function(id) {
            $http.delete('./attributeDelete/' + id).success(function (data, status, headers, config) {
            	$scope.status.message="Deleted Successfully";
 				$scope.$emit('updateSaveStatus',$scope.status);
                $scope.messages = $scope.messages.filter(function(message) {
                        return message.id != id;
                    }
                );
               
            }).error(function (data, status, headers, config) {
            	 $scope.status.message="Error occurred";
				 $scope.$emit('updateErrorStatus',$scope.status);
            });
        };
    }])
	