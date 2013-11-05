angular.module("acsshowcase", [ "acsshowcase.service" ]).config(
		function($routeProvider) {
			$routeProvider.when('/ehrNumOne', {
				templateUrl : 'views/ehrNumOne/ehrNumOne.html',
				controller : EhrNumOneController
			}).when('/books', {
				templateUrl : 'views/books/list.html',
				controller : BookListController
			}).when('/books/:bookId', {
				templateUrl : 'views/books/detail.html',
				controller : BookDetailController
			});
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
		
		
		/*
		 * $http({ method: 'post', url: 'api/ehrNumOne/parseC32', data: payload,
		 * headers: {'Content-Type': 'application/x-www-form-urlencoded'} }).
		 */

		var config = {
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
			}
		};
		$http.post('api/ehrNumOne/parseC32', payload, config).success(
				function(data, status) {
					$scope.ehrNumOne.selectedPatient.greenCcd = data;
					toastr.success("Parse C32 Succeeded");
				}).error(function(data, status) {
			$scope.status = status;
			toastr.error("Parse C32 Failed");
		});
	};
}

function BookListController($scope, Book) {
	$scope.books = Book.query();

	$scope.deleteBook = function(book) {
		book.$delete(function() {
			$scope.books.splice($scope.books.indexOf(book), 1);
			toastr.success("Deleted");
		});
	};
}

function BookDetailController($scope, $routeParams, $location, Book) {
	var bookId = $routeParams.bookId;

	if (bookId === 'new') {
		$scope.book = new Book();
	} else {
		$scope.book = Book.get({
			bookId : bookId
		});
	}

	$scope.save = function() {
		if ($scope.book.isNew()) {
			$scope.book.$save(function(book, headers) {
				toastr.success("Created");
				var location = headers('Location');
				var id = location.substring(location.lastIndexOf('/') + 1);
				$location.path('/books/' + id);
			});
		} else {
			$scope.book.$update(function() {
				toastr.success("Updated");
			});
		}
	};
}
