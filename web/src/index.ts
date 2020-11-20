import express from 'express';
import { Server } from 'http';
import socketio from 'socket.io';

const HOST: string = process.env.HOST || '0.0.0.0';
const PORT: number = parseInt(process.env.PORT) || 3000;
const app = express();
const server = new Server(app);
export const io = new socketio(server);

server.listen(PORT, HOST);

console.log(`Express running on http://localhost:${PORT}.`)

app.set('view engine', 'ejs');

app.set('etag', false);

app.use('/public', express.static('public'));

app.use((req, res, next) => {
    res.set('Cache-Control', 'no-store');
    next();
});

app.get('/', (_, res) => {
    res.render('index');
});

io.on('connection', (socket: socketio.Socket) => {
    socket.on("gameMode", (gameMode: string) => {
        socket.broadcast.emit("gameMode", gameMode);
    });

    socket.on("time", (time: number) => {
        socket.broadcast.emit("time", time);
    });

    socket.on("gravity", (gravity: boolean) => {
        socket.broadcast.emit("gravity", gravity);
    });
});