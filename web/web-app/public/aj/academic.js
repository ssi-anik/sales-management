function AcademicController($scope, $http) {

	$scope.editAcademic_term = {};

	$http.get('academic_term').success(function (data, status, headers, config) {
		$scope.academic_term = data;

		console.log($scope.academic_term);

	});

	// $scope.status = function () {
	// 	var count = 0;
	// 	angular.forEach($scope.academic, function (academic) {
	// 		count += academic.status ? 1 : 0;
	// 	});
	// 	return count;
	// };

	// $scope.save = function () {
	// 	$scope.academic.push($scope.academic);
	// 	$http.post('academic', $scope.academic);
	// 	$scope.academic = {};
	// };

	// $scope.edit = function (academic) {
	// 	$scope.academic = academic;
	// };

	// $scope.delete = function (academic) {
	// 	angular.forEach($scope.academic, function (pr, key) {
	// 		if (pr.id == academic.id) {
	// 			$scope.academic.splice(key, 1);
	// 		}
	// 	});
	// 	$http.post('academic/delete', academic);
	// };

	// $scope.reset = function () {
	// 	$scope.academic = {};
	// };

	// $scope.publishChange = function (academic) {
	// 	$http.post('academic', academic);
	// };

}