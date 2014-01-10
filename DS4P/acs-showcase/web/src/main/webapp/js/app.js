var acsShowCase = angular.module("acsshowcase", [ "acsshowcase.service" ]);
acsShowCase.config(
		function($routeProvider) {
			$routeProvider.when('/ehrNumOne', {
				templateUrl : 'views/ehrNumOne/ehrNumOne.html',
				controller : EhrNumOneController
			}).otherwise({ redirectTo: '/'});
		});

// TODO: Using $http is really a bad idea, should be encapsulated in service
// layer
function EhrNumOneController($scope, $http) {
	$http({
		method : 'GET',
		url : 'api/ehrNumOne'
	}).success(function(data, status) {
		$scope.status = status;
		$scope.ehrNumOne = data;

		toastr.success("Initialization Succeeded");
	}).error(function(data, status) {
		$scope.ehrNumOne = data || "Request failed";
		$scope.status = status;
		toastr.error("Initialization Failed");
	});

	$scope.getC32 = function(patient) {
		// Reset first
		delete patient.c32Xml;
		delete patient.greenCcd;
		
		$scope.ehrNumOne.selectedPatient = patient;
		
		$http({
			method : 'GET',
			url : 'api/ehrNumOne/c32/' + patient.id
		}).success(function(data, status) {
			$scope.ehrNumOne.selectedPatient.c32Xml = data;
			$scope.show = "";
			toastr.success("Get C32 Succeeded");
		}).error(function(data, status) {
			$scope.status = status;
			toastr.error("Get C32 Failed");
		});
	};

	$scope.parseC32 = function() {
		var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
		var payload = $.param({
			c32Xml : c32Xml
		});

		var config = {
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
			}
		};
		$http.post('api/ehrNumOne/parseC32', payload, config).success(
				function(data, status) {
					$scope.ehrNumOne.selectedPatient.greenCcd = data;
					$scope.ehrNumOne.selectedPatient.pixAddMsg = "";
					$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
					$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
					$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
					$scope.ehrNumOne.selectedPatient.hl7v3Xml = "";
					$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
					$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
					$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
					
					toastr.success("Parse C32 Succeeded");
				}).error(function(data, status) {
			$scope.status = status;
			toastr.error("Parse C32 Failed");
		});
	};	

	// Transforms C32.xml into pixadd
	$scope.c32ToHL7 = function(opName) {
		var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
		var payload = $.param({
			c32Xml : c32Xml
		});

		var config = {
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
			}
		};
		
		var opUrl = 'api/pixMgr/tansformHl7Add';
		if(opName == "update"){
			opUrl = 'api/pixMgr/tansformHl7Update';
		} else if(opName == "query"){
			opUrl = 'api/pixMgr/tansformHl7Query';
		}
		
		$http.post(opUrl, payload, config).success(
				function(data, status) {
					$scope.ehrNumOne.selectedPatient.hl7v3Xml = data;
					$scope.ehrNumOne.selectedPatient.greenCcd= "";
					$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
					$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
					$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
					$scope.ehrNumOne.selectedPatient.pixAddMsg = "";		
					$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
					$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
					$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
					toastr.success("Transform C32 Succeeded");
				}).error(function(data, status) {
					$scope.status = status;
					toastr.error("Transform C32 Failed");
				});
		};	
		
		// Add patient info from REM to Open EMPI
		$scope.addPix = function() {
			var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
			var payload = $.param({
				c32Xml : c32Xml
			});
	
			var config = {
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
				}
			};
	
			$http.post('api/pixMgr/pixAdd', payload, config).success(
					function(data, status) {
						$scope.ehrNumOne.selectedPatient.pixAddMsg = data;
						$scope.ehrNumOne.selectedPatient.greenCcd = "";
						$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
						$scope.ehrNumOne.selectedPatient.hl7v3Xml = "";
						$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
						if (data.indexOf("Failure!") !== -1) {
							toastr.error("PixAdd Operation Failed");
							$scope.ehrNumOne.selectedPatient.isError = true;
						} else {
							toastr.success("PixAdd Operation Succeeded");
							$scope.ehrNumOne.selectedPatient.isError = false;
						}
					}).error(function(data, status) {
				$scope.status = status;
				toastr.error("PixAdd Operation Failed");
				$scope.ehrNumOne.selectedPatient.isError = true;
			});
		};			
		
		// Update patient info from REM to Open EMPI
		$scope.updatePix = function() {
			var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
			var payload = $.param({
				c32Xml : c32Xml
			});
	
			var config = {
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
				}
			};
	
			$http.post('api/pixMgr/pixUpdate', payload, config).success(
					function(data, status) {
						$scope.ehrNumOne.selectedPatient.pixUpdateMsg = data;
						$scope.ehrNumOne.selectedPatient.greenCcd = "";
						$scope.ehrNumOne.selectedPatient.pixAddMsg = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
						$scope.ehrNumOne.selectedPatient.hl7v3Xml = "";
						$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
						if (data.indexOf("Failure!") !== -1) {
							toastr.error("PixUpdate Operation Failed");
							$scope.ehrNumOne.selectedPatient.isError = true;
						} else {
							toastr.success("PixUpdate Operation Succeeded");
							$scope.ehrNumOne.selectedPatient.isError = false;
						}
					}).error(function(data, status) {
				$scope.status = status;
				toastr.error("PixUpdate Operation Failed");
				$scope.ehrNumOne.selectedPatient.isError = true;
			});
		};
		
		// Query patient info from REM to Open EMPI
		$scope.queryPix = function() {
			var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
			var payload = $.param({
				c32Xml : c32Xml
			});
	
			var config = {
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
				}
			};
	
			$http.post('api/pixMgr/pixQuery', payload, config).success(
					function(data, status) {
						$scope.ehrNumOne.selectedPatient.pixManagerBean = data;
						$scope.ehrNumOne.selectedPatient.pixAddMsg = "";
						$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
						$scope.ehrNumOne.selectedPatient.greenCcd = "";
						var queryBeanMsg = $scope.ehrNumOne.selectedPatient.pixManagerBean.queryMessage;
						$scope.ehrNumOne.selectedPatient.hl7v3Xml = "";
						$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
						if (queryBeanMsg.indexOf("Failure!") !== -1) {
							toastr.error("PixQuery Operation Failed");
							$scope.ehrNumOne.selectedPatient.isError = true;
						} else {
							toastr.success("PixQuery Operation Succeeded");
							$scope.ehrNumOne.selectedPatient.isError = false;
						}
					}).error(function(data, status) {
				$scope.status = status;
				toastr.error("PixQuery Operation Failed");
				$scope.ehrNumOne.selectedPatient.isError = true;
			});
		};
		
		// Add patient info from OpenEMPI to XDS.b
		$scope.addXdsb = function() {
			var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
			var payload = $.param({
				c32Xml : c32Xml
			});
	
			var config = {
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
				}
			};
	
			$http.post('api/xdsb/regAdd', payload, config).success(
					function(data, status) {
						$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = data;
						$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
						$scope.ehrNumOne.selectedPatient.greenCcd = "";
						$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
						$scope.ehrNumOne.selectedPatient.hl7v3Xml = "";
						if (data.acknowledgement[0].typeCode.code =="CE") {
							toastr.error("XdsbRegAdd Operation Failed");
							$scope.ehrNumOne.selectedPatient.isError = true;
						} else {
							toastr.success("XdsbRegAdd Operation Succeeded");
							$scope.ehrNumOne.selectedPatient.isError = false;
						}
					}).error(function(data, status) {
				$scope.status = status;
				toastr.error("XdsbRegAdd Operation Failed");
				$scope.ehrNumOne.selectedPatient.isError = true;
			});
		};

		// Update patient info from OpenEMPI to XDS.b
		$scope.updateXdsb = function() {
			var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
			var payload = $.param({
				c32Xml : c32Xml
			});
	
			var config = {
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
				}
			};
	
			$http.post('api/xdsb/regUpdate', payload, config).success(
					function(data, status) {
						$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = data;
						$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
						$scope.ehrNumOne.selectedPatient.greenCcd = "";
						$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
						$scope.ehrNumOne.selectedPatient.hl7v3Xml = "";
						if (data.acknowledgement[0].typeCode.code =="CE") {
							toastr.error("XdsbRegUpdate Operation Failed");
							$scope.ehrNumOne.selectedPatient.isError = true;
						} else {
							toastr.success("XdsbRegUpdate Operation Succeeded");
							$scope.ehrNumOne.selectedPatient.isError = false;
						}
					}).error(function(data, status) {
				$scope.status = status;
				toastr.error("XdsbRegUpdate Operation Failed");
				$scope.ehrNumOne.selectedPatient.isError = true;
			});
		};

		// Provide and Register clinical document from REM to XDS.b
		$scope.addXdsbRepo = function() {
			var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
			var payload = $.param({
				c32Xml : c32Xml
			});
	
			var config = {
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
				}
			};
	
			$http.post('api/xdsb/repProvide', payload, config).success(
					function(data, status) {
						$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = data;
						$scope.ehrNumOne.selectedPatient.greenCcd = "";
						$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
						$scope.ehrNumOne.selectedPatient.hl7v3Xml = "";
						if (data.status == "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success") {
							toastr.success("XdsbRepoProvideAndRegister Operation Succeeded");
							$scope.ehrNumOne.selectedPatient.isError = false;
						} else {
							toastr.error("XdsbRepProvide Operation Failed");
							$scope.ehrNumOne.selectedPatient.isError = true;
						}
					}).error(function(data, status) {
				$scope.status = status;
				toastr.error("XdsbRepoProvideAndRegister Operation Failed");
				$scope.ehrNumOne.selectedPatient.isError = true;
			});
		};
		
		// Transforms C32.xml into pixadd
		$scope.c32ToHL7Add = function() {
			var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
			var payload = $.param({
				c32Xml : c32Xml
			});

			var config = {
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
				}
			};
			
			var opUrl = 'api/pixMgr/tansformHl7Add';
		
			$http.post(opUrl, payload, config).success(
					function(data, status) {
						$scope.ehrNumOne.selectedPatient.hl7v3Xml = data;
						$scope.ehrNumOne.selectedPatient.greenCcd= "";
						$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
						$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
						$scope.ehrNumOne.selectedPatient.pixAddMsg = "";		
						$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
						$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
						toastr.success("Transform C32 Succeeded");
					}).error(function(data, status) {
						$scope.status = status;
						toastr.error("Transform C32 Failed");
					});
			};	
			
			// Transforms C32.xml into pixadd
			$scope.c32ToHL7Update = function() {
				var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
				var payload = $.param({
					c32Xml : c32Xml
				});

				var config = {
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
					}
				};
				
				var opUrl = 'api/pixMgr/tansformHl7Update';
				
				$http.post(opUrl, payload, config).success(
						function(data, status) {
							$scope.ehrNumOne.selectedPatient.hl7v3Xml = data;
							$scope.ehrNumOne.selectedPatient.greenCcd= "";
							$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
							$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
							$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
							$scope.ehrNumOne.selectedPatient.pixAddMsg = "";		
							$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
							$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
							$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
							toastr.success("Transform C32 Succeeded");
						}).error(function(data, status) {
							$scope.status = status;
							toastr.error("Transform C32 Failed");
						});
				};	
				
				// Transforms C32.xml into pixadd
				$scope.c32ToHL7Query = function() {
					var c32Xml = $scope.ehrNumOne.selectedPatient.c32Xml;
					var payload = $.param({
						c32Xml : c32Xml
					});

					var config = {
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
						}
					};
					
					var opUrl = 'api/pixMgr/tansformHl7Query';
				
					$http.post(opUrl, payload, config).success(
							function(data, status) {
								$scope.ehrNumOne.selectedPatient.hl7v3Xml = data;
								$scope.ehrNumOne.selectedPatient.greenCcd= "";
								$scope.ehrNumOne.selectedPatient.pixUpdateMsg = "";
								$scope.ehrNumOne.selectedPatient.pixManagerBean = "";
								$scope.ehrNumOne.selectedPatient.pixManagerBean.queryIdMap = null;
								$scope.ehrNumOne.selectedPatient.pixAddMsg = "";
								$scope.ehrNumOne.selectedPatient.xdsbRegAddMsg = null;
								$scope.ehrNumOne.selectedPatient.xdsbRegUpdateMsg = null;
								$scope.ehrNumOne.selectedPatient.xdsbRepoProvideMsg = null;
								toastr.success("Transform C32 Succeeded");
							}).error(function(data, status) {
								$scope.status = status;
								toastr.error("Transform C32 Failed");
							});
					};					
			
}

