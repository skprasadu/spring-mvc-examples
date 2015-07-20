'use strict';

angular.module('appDoCFD')

.directive('showErrors', function(){
	// Runs during compile
	return {
		// name: '',
		// priority: 1,
		// terminal: true,
		// scope: {}, // {} = isolate, true = child, false/undefined = no change
		// controller: function($scope, $element, $attrs, $transclude) {},
		require: '^form', // Array = multiple requires, ? = optional, ^ = check parent elements
		restrict: 'A', // E = Element, A = Attribute, C = Class, M = Comment
		// template: '',
		// templateUrl: '',
		// replace: true,
		// transclude: true,
		// compile: function(tElement, tAttrs, function transclude(function(scope, cloneLinkingFn){ return function linking(scope, elm, attrs){}})),
		link: function(scope, iElm, iAttrs, formCtrl) {

			var inputEl = iElm[0].querySelector("[name]");
			// console.log(inputEl);
			
			var inputNgEl = angular.element(inputEl);
			// console.log(inputNgEl);

			var inputName = inputNgEl.attr('name');

			inputNgEl.bind('change', function () {
				iElm.toggleClass('has-error', formCtrl[inputName].$invalid);
				// inputEl.appendChild(document.createElement('div'));
			});



			scope.$on('show-errors-check-validity', function () {
				iElm.toggleClass('has-error', formCtrl[inputName].$invalid);
				// inputNgEl.append('<p class="help-block" ><span class="glyphicon glyphicon-exclamation-sign" /> Please select service </p>', formCtrl[inputName].$invalid);
			});

			
		}
	}
})


.directive('errorMessage', function(){
	// Runs during compile
	return {
		// name: '',
		// priority: 1,
		// terminal: true,
		// scope: {}, // {} = isolate, true = child, false/undefined = no change
		// controller: function($scope, $element, $attrs, $transclude) {},
		// require: 'ngModel', // Array = multiple requires, ? = optional, ^ = check parent elements
		 restrict: 'E', // E = Element, A = Attribute, C = Class, M = Comment
		// template: '',
		templateUrl: 'views/generic/error.html',
		scope: {
            vm: '@vm'
        },
		// replace: true,
		// transclude: true,
		// compile: function(tElement, tAttrs, function transclude(function(scope, cloneLinkingFn){ return function linking(scope, elm, attrs){}})),
		link: function($scope, iElm, iAttrs, controller) {
			
		}
	}
});