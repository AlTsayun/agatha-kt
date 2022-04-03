millisecondTime = 1*new Date();

inserted = document.createElement("script");
firstScript = document.getElementsByTagName("script")[0];
inserted.async = 1;
inserted.innerHTML = "runMetrics(window, document, millisecondTime)";
firstScript.parentNode.insertBefore(inserted, firstScript)

console.log(document)
console.log(window)

function runMetrics(window, document, millisecondTime) {
    pageLoadedTimePlugin(window, document, millisecondTime, sendData)
    // $(document).click(function (e) {
    //     console.log(`pageX is ${e.pageX} and pageY ${e.pageY}`);
    // })
}

function sendData(pluginId, data) {
    const url = "http://localhost:8080";
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify({id: pluginId,data: data}));
}

function pageLoadedTimePlugin(window, document, millisecondTime, sendData) {
    $(document).ready(function(){
        sendData("pageLoadedTimePlugin", 1*new Date() - millisecondTime)
    })
}