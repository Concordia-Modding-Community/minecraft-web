const socket = io("http://localhost");

function setGameMode(gameMode) {
    socket.emit("gameMode", gameMode);
}

function setTime() {
    socket.emit("time", parseInt(document.querySelector("#time").value));
}

function setGravity(gravity) {
    socket.emit("gravity", gravity);
}