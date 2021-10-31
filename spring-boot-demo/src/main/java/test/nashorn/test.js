print("lllllllll")
gm.id=888
gm.name='haha'
var fun1 = function(name) {
    print('Hi there from Javascript, ' + name);
    return "greetings from javascript";
};

var fun2 = function (object) {
    print("JS Class Definition: " + object + "--" + Object.prototype.toString.call(object));
};