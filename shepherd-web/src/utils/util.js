export const guid = () => {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }
    return s4() + '-' + s4() + '-' + s4() + '-' + s4();
};

export const setToLocalstorage = (key, val) => {
    localStorage.setItem(key, JSON.stringify(val));
};

export const fetchFromLocalstorage = (key) => {
    const retrievedObject = localStorage.getItem(key);
    return JSON.parse(retrievedObject);
};