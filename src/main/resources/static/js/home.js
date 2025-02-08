document.querySelector('button').onclick = function() {
    let reqUUID = localStorage.getItem("request-uuid");
    if (!reqUUID) {
        reqUUID = self.crypto.randomUUID();
        localStorage.setItem("request-uuid",reqUUID);
    }
    const evtSource = new EventSource("/news?pid="+reqUUID);
    // Cuando el servidor termina de emitir lanza el error y desconecto
    evtSource.onerror = (err) => { evtSource.close(); }
    // Recibe uno o varios eventos del servidor (sin nombre)
    evtSource.onmessage = (event) => { 
        clearInterval(itv);
        const { texto, number} = JSON.parse(event.data);
        document.querySelector('div#response').innerHTML = `<ul><li>${texto}</li><li>${number}</li></ul>`;
        localStorage.removeItem("request-uuid");
    };
    // Establece el UI loader
    document.querySelector('div#response').innerHTML = 'Cargando respuesta del servidor';
    let counter = 1;
    const itv = setInterval(() => {
        document.querySelector('div#response').innerHTML = 'Cargando respuesta del servidor' + '.'.repeat(counter++);
    },1000);
    
}