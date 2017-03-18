const Ajax = {
    post : (url,params,success,error) =>{
        if (url.indexOf('?') < 0)
            url = url + "?1=1";

        for (let key in params) {
            if (params[key] == 0 || params[key])
                url += "&"+key +"="+
                    //encodeURI(params[key]);
                    escape (encodeURIComponent(params[key]));
        }
        var xhr = new XMLHttpRequest();
        xhr.open('POST', url);
        xhr.responseType = 'json';


        xhr.onload = function() {
            success(xhr.response);
        };

        xhr.onerror = function() {
            console.log("Oops, error");
        };
        xhr.send();
    }
}
export default Ajax;

