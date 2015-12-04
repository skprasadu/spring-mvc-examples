'use strict';

angular.module('appDynApp', ['ui.router', 'ngSanitize', 'ui.bootstrap', 'ngTagsInput', 'angularTreeview', 'dynform', 'ngGrid'])

	.config(['$stateProvider','$urlRouterProvider',
	function($stateProvider, $urlRouterProvider) {
	
		$urlRouterProvider.otherwise('/formbuilder');
		$urlRouterProvider.when('/formbuilder', '/formbuilder/listAttributes');
		
        $stateProvider
            
			.state('formbuilder', {
				url:'/formbuilder',
				controller:'masterDataCtrl',
				templateUrl:'views/navigation/formbuilder.html'                            
            })
			.state('formbuilder.listAttributes', {
				url: '/listAttributes?app_name&formid',
				controller:'listAttributesCtrl',
				templateUrl: 'views/navigation/formbuilder-listFormData.html'
			})
			.state('formbuilder.renderAttributes', {
				url: '/renderAttributes?app_name&formid&dataid',
				controller:'renderAttributesCtrl',
				templateUrl: 'views/navigation/formbuilder-renderFormData.html'
			})			
    }]);
