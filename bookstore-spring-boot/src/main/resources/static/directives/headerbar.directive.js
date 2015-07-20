'use strict';

angular.module('appDoCFD')

.directive('doCfdHeaderBar', function() {
    return {
        restrict: 'E',
        scope: {
            selected: '@selected'
        },
        link: function(scope, element, attrs) {

        },
        templateUrl: 'views/headerbar/headerbar.html'
    }
});