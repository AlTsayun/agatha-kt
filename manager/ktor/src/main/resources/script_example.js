millisecondTime = Date.now();

inserted = document.createElement("script");
firstScript = document.getElementsByTagName("script")[0];
inserted.async = 1;
inserted.innerHTML = "run(window, document, millisecondTime)";
firstScript.parentNode.insertBefore(inserted, firstScript)

function run(w, d, l) {

    //pageLoadedTime
    (function (window, document, millisecondTime, send) {
        $(document).ready(function () {
            send({l: Date.now() - millisecondTime})
        })
    })(w, d, l, function (d) {
        send("http://localhost:8081", 1, d)
    });

    //visitTime
    (function (window, document, millisecondTime, send) {
        $(window).unload(function () {
            send({l: Date.now() - millisecondTime})
        });
    })(w, d, l, function (d) {
        send("http://localhost:8081", 2, d)
    });

    //device
    (function (window, document, millisecondTime, send) {
        const getDeviceType = () => {
            const ua = navigator.userAgent;
            if (/(tablet|ipad|playbook|silk)|(android(?!.*mobi))/i.test(ua)) {
                return "tablet";
            }
            if (/Mobile|iP(hone|od)|Android|BlackBerry|IEMobile|Kindle|Silk-Accelerated|(hpw|web)OS|Opera M(obi|ini)/.test(ua)) {
                return "mobile";
            }
            return "desktop";
        };
        send({d: getDeviceType()})
    })(w, d, l, function (d) {
        send("http://localhost:8081", 3, d)
    });


}

function send(url, pluginId, data) {
    // const url = "http://localhost:8081";
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify({id: pluginId, t: ~~(Date.now() / 1000), data: data}));
}
