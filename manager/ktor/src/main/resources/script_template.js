millisecondTime = Date.now();

inserted = document.createElement("script");
firstScript = document.getElementsByTagName("script")[0];
inserted.async = 1;
inserted.innerHTML = "run(window, document, millisecondTime)";
firstScript.parentNode.insertBefore(inserted, firstScript)

function run(w,d,l) {
    /*{sensorsCode}*/
}

function send(pluginId, data) {
    const url = "http://localhost:8081";
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify({ id: pluginId, t: ~~(Date.now() / 1000), data: data }));
}
