import axios from 'axios'

const request = class {
    constructor(url, arg) {
        this.url = url;
        this.arg = arg;
    }

    static modepost() {
        return new Promise((resolve,reject)=>{
            axios.post()
        })
    }
}