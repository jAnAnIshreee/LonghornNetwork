import java.util.concurrent.Semaphore;

public class ChatThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;

    private static final Semaphore semaphore = new Semaphore(1);

    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            String formatted = sender.name + " to " + receiver.name + ": " + message;

            // Add to chat history for both sender and receiver
            sender.addChatMessage(formatted);
            receiver.addChatMessage(formatted);

            System.out.println("Chat (Thread-Safe): " + formatted);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Chat interrupted: " + e.getMessage());
        } finally {
            semaphore.release();
        }
    }
}
