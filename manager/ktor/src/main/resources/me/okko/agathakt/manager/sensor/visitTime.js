$(window).unload(function () {
    send({l: Date.now() - millisecondTime})
});