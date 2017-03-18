// import 'whatwg-fetch';



const AjaxFetch = {
    post  :(url,params)=>{
        if (url.indexOf('?') < 0)
            url = url + "?1=1";

        for (let key in params) {
            if (params[key] == 0 || params[key])
                url += "&"+key +"="+encodeURI(params[key]);
        }

        function status(response) {
            if (response.status >= 200 && response.status < 300) {
                return Promise.resolve(response)
            } else {
                return Promise.reject(new Error(response.statusText))
            }
        }

        function json(response) {
            console.log("response="+response);
            try {
                return response.json()
            } catch (e) {
                return response
            }

        }


        return fetch(url, {
            method: 'POST',
            headers : {'Content-Type': 'application/x-www-form-urlencoded'},
            // headers: {
            //     'Content-Type': 'application/json'
            // },
            //body: JSON.stringify(params)
            //body : formData
            body : {}
        }).then(status)
            .then(json);
    }
}

export default AjaxFetch;