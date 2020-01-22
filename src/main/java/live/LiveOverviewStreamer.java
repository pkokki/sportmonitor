package live;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LiveOverviewStreamer {
    private static final int PORT = 9999;
    private static final BlockingQueue<String> eventQueue = new ArrayBlockingQueue<>(100);
    private static final Executor SERVER_EXECUTOR = Executors.newSingleThreadExecutor();

    static void start() {
        SERVER_EXECUTOR.execute(new StreamingServer(eventQueue));
        System.out.println("LiveOverviewStreamer started.");
    }

    static void put(String event) throws InterruptedException {
        eventQueue.put(event);
        //System.out.println(String.format("Queued \"%s\".", event));
    }

    private static class StreamingServer implements Runnable {
        private final BlockingQueue<String> eventQueue;

        StreamingServer(BlockingQueue<String> eventQueue) {
            this.eventQueue = eventQueue;
        }

        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                do {
                    String event = eventQueue.take();
                    //System.out.println(String.format("Sent \"%s\" to the socket.", event));
                    out.println(event);
                } while (true);
            } catch (IOException |InterruptedException e) {
                throw new RuntimeException("Server error", e);
            }
        }
    }
}
