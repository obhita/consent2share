angular.module("acsshowcase.service", ["ngResource"]).
    factory('Book', function ($resource) {
        var bt = $resource('api/books/:bookId', {bookId: '@id'},
            {update: {method: 'PUT'}});
        bt.prototype.isNew = function(){
            return (typeof(this.id) === 'undefined');
        };
        return bt;
    });
