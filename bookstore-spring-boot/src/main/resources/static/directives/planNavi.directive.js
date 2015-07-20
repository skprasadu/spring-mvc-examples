'use strict';

angular.module('appDoCFD')

.directive('doCfdPlanNavi', function() {
    return {
        restrict: 'E',
        scope: {
            selected: '@selected'
        },
        link: function(scope, element, attrs) {

        },
        templateUrl: 'views/navigation/planNavi.html'
    }
});