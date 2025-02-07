document.querySelector('button').onclick = function() {
    const evtSource = new EventSource("/news");
    // Cuando el servidor termina de emitir lanza el error y desconecto
    evtSource.onerror = (err) => { evtSource.close(); }
    // Recibe uno o varios eventos del servidor (sin nombre)
    evtSource.onmessage = (event) => { 
        console.log(event); 
        clearInterval(itv);
        const { texto, number} = JSON.parse(event.data);
        document.querySelector('div#response').innerHTML = `<ul><li>${texto}</li><li>${number}</li></ul>`;
    };
    // Establece el UI loader
    document.querySelector('div#response').innerHTML = 'Cargando respuesta del servidor';
    let counter = 1;
    const itv = setInterval(() => {
        document.querySelector('div#response').innerHTML = 'Cargando respuesta del servidor' + '.'.repeat(counter++);
    },1000);
    
}