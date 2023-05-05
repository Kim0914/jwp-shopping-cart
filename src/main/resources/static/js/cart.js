const currentUrl = window.location.href;
const url = new URL(currentUrl);
const protocol = url.protocol;
const hostname = url.hostname;
const port = url.port;
const currentServerUrl = protocol + "//" + hostname + ":" + port;

const addCartItem = (itemId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.post(currentServerUrl + '/carts/' + itemId, {}, {
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        alert(error.response.data.message)
        console.error(error);
    });
}

const removeCartItem = (id) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.delete(currentServerUrl + '/carts/' + id, {
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
