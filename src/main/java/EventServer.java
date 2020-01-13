import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.*;

class EventServer {
    private static final Executor SERVER_EXECUTOR = Executors.newSingleThreadExecutor();
    private static final int PORT = 9999;
    private static final String DELIMITER = ":";
    private static final long EVENT_PERIOD_SECONDS = 1;
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> eventQueue = new ArrayBlockingQueue<>(100);
        SERVER_EXECUTOR.execute(new StreamingServer(eventQueue));
        do {
            String event = generateEvent();
            eventQueue.put(event);
            System.out.println(String.format("Added \"%s\" to queue.", event));
            Thread.sleep(TimeUnit.SECONDS.toMillis(EVENT_PERIOD_SECONDS));
        } while (true);
    }

    private static String generateEvent() {
        int userNumber = random.nextInt(10);
        String event = random.nextBoolean() ? "login" : "purchase";
        // In production use a real schema like JSON or protocol buffers
        return String.format("user-%s", userNumber) + DELIMITER + event;
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
                    System.out.println(String.format("Writing \"%s\" to the socket.", event));
                    out.println(event);
                } while (true);
            } catch (IOException|InterruptedException e) {
                throw new RuntimeException("Server error", e);
            }
        }
    }
}
