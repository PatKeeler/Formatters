/**
 * Created by Pat Keeler on 11/30/2014.
 */


/**
 * Directive to set non-divide focus.
 */
mathApp.directive('otherFocus', function(elem) {
    return function(scope, element){
        element[0].focus();
    };
});

/**
 * Directive to set divide focus.
 */
mathApp.directive('divideFocus', function(elem) {
    return function (scope, element) {
        element[0].focus();
    };
});
