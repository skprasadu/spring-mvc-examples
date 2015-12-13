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
            .state('formbuilder.listDesignOfForms', {
				url: '/listDesignOfForms?app_name',
				controller:'listDesignOfFormsCtrl',
				templateUrl: 'views/navigation/formbuilder-listDesignOfForms.html'
			})
			.state('formbuilder.renderDesignOfForms', {
				url: '/renderDesignOfForms?app_name&formid',
				controller:'listDesignOfFormCreateCtrl',
				templateUrl: 'views/navigation/formbuilder-designOfFormsCreate.html'
			})
			.state('formbuilder.renderQuickDesignOfForms', {
				url: '/renderQuickDesignOfForms?app_name',
				controller:'renderQuickDesignOfFormsCtrl',
				templateUrl: 'views/navigation/formbuilder-quickDesignOfFormsCreate.html'
			})
			.state('formbuilder.previewDesignOfForms', {
				url: '/previewDesignOfForms?app_name&formid',
				controller:'designOfFormPreviewCtrl',
				templateUrl: 'views/navigation/formbuilder-previewFormData.html'
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
