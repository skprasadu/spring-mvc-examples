'use strict';

angular.module('appDoCFD')

.factory('saveFormDataSvc', function(){

	//The data store to be sent to server
	var boeingData = {};
	var isDataSaved = false;

	return {

		
		getData: function(){
			return boeingData;
		},

		saveData: function ($scope, form, dataToBeSaved) {
			console.log('dataToBeSaved: '+JSON.stringify(dataToBeSaved));
			
			$scope.$broadcast('show-errors-check-validity');

			if(form.$invalid){
				console.log("No new data saved. Form invalid: "+form.$name);
				return;
			}else{
				boeingData = dataToBeSaved;
				isDataSaved = true;
				//console.log(boeingData);
				//console.log("Stored Data: "+JSON.stringify(boeingData));
			}
		},

		resetData: function() {
			boeingData = {};
		},

		postData: function (data) {
			$http.post('someUrl', JSON.stringify(data)).success(function(){
        		console.log(data);
    		});
		}
		
	};
});