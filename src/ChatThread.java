import java.util.concurrent.Semaphore;
/**
 * A thread task that simulates sending a chat message between two
 * UniversityStudent objects. Chat history updates are thread-safe
 * to prevent losing messages or having an inconsistent state.
 */
public class ChatThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;


    // Shared static chat log for all threads
    private static final Semaphore semaphore = new Semaphore(1);

    /**
     * Creates a chat task representing a message being sent from one
     * student to another.
     *
     * @param sender the student sending the message
     * @param receiver the student receiving the message
     * @param message the message content to send
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Sends a specific message. Implementations use
     * synchronized blocks or thread safe structures to update chat history
     * and log the message for testing and verification.
     */
    @Override
    public void run() {
        try{
            semaphore.acquire();
            //Simulate sending a chat message
            System.out.println("Chat (Thread-Safe): " + sender.name + " to " + receiver.name + ": " + message);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println("Chat interrupted: " + e.getMessage());
        } finally{
            semaphore.release();
        }
    }
}
