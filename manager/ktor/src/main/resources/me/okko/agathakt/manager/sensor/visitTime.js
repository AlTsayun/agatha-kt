$(window).unload(function () {
    send({t: Math.floor((Date.now() - millisecondTime) / 1000)})
});