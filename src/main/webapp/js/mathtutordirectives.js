/**
 * Created by Pat Keeler on 11/30/2014.
 */


/**
 * Directive to focus on Add, Subtract, Multiply answer control.
 */
mathApp.directive('focusIfOther', [function () {
    return function focusIf(scope, element, attr) {
        scope.$watch(attr.focusIfOther, function (newVal) {
            if (newVal) {
                scope.$evalAsync(function() {
                    element[0].focus();
                });
            }
        });
    }
}]);


/**
 * Directive to focus on division answer control.
 */
mathApp.directive('focusIfDivide', [function () {
    return function focusIf(scope, element, attr) {
        scope.$watch(attr.focusIfDivide, function (newVal) {
            if (newVal) {
                scope.$evalAsync(function() {
                    element[0].focus();
                });
            }
        });
    }
}]);
