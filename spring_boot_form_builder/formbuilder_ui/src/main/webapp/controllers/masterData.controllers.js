'use strict';

angular.module('appDynApp')

    .controller('masterDataCtrl', ['$scope', '$http', '$rootScope','$timeout',
			function($scope, $http, $rootScope, $timeout) {

		$scope.applicationName = 'vendor_management';
		$scope.applicationDisplayName = '';
        $http.get('./getApplicationDisplayName/' + $scope.applicationName).success(function (data, status, headers, config) {
            $scope.applicationDisplayName = data;
        }).error(function (data, status, headers, config) {
             $scope.status.message="Can't retrieve messages list!";
			 $scope.$emit('updateErrorStatus',$scope.status);
        });

        $scope.menuEntries = [];
        $http.get('./getFormList/' + $scope.applicationName).success(function (data, status, headers, config) {
            $scope.menuEntries = data;
        }).error(function (data, status, headers, config) {
             $scope.status.message="Can't retrieve messages list!";
			 $scope.$emit('updateErrorStatus',$scope.status);
        });
        
        $scope.isDisabled = true;
        $http.get('./disableDesigner').success(function (data, status, headers, config) {
        	$scope.isDisabled = data;
        });
        
    }]);
	