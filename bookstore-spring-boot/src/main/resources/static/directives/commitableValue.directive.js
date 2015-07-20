'use strict';

angular.module('appDoCFD')

.directive('commitableValue', function(){
	return {
      scope: true,
	  require: 'ngModel',
	   link: function($scope, $elm, $attrs, ngModel) {
        //ngModel.$setViewValue('hi');
      }
    }
	
  });
