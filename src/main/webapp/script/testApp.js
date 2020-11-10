angular.module('bqTestModule', [])
    .controller('FetchController', ['$scope', '$http',
        function ($scope, $http) {

            fetchData();

            function fetchData() {
                $http({method: 'GET', url: 'api/load-data'}).then(function (response) {
					$scope.allMembers = response.data;
					$scope.members = [];
					$scope.states = ['All'];
					$scope.state = 'All';
					for (state in $scope.allMembers) {						
						$scope.states.push(state);
					}
					$scope.changeState();
                }, function (reason) {
                    console.log('error ' + reason)
                });
            }

			$scope.changeState = function() {
				$scope.members = [];
				if($scope.state == 'All') {
					for (state in $scope.allMembers) {
						$scope.members.push(...$scope.allMembers[state]);
					}
				} else {
					$scope.members = $scope.allMembers[$scope.state];
				}
				$scope.members = $scope.members.sort((a, b) => a.id - b.id);
			}

        }]);