/**
 * Created by Pat Keeler on 11/30/2014.
 */


/**
 * Directive to focus on answer control.
 */
mathApp.directive('focusIf', [function () {
    return function focusIf(scope, element, attr) {
        scope.$watch(attr.focusIf, function (newVal) {
            if (newVal) {
                scope.$evalAsync(function() {
                    element[0].focus();
                });
            }
        });
    }
}]);
