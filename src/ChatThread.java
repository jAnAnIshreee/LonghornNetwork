/**
 * A thread task that simulates sending a chat message between two
 * UniversityStudent objects. Chat history updates are thread-safe
 * to prevent losing messages or having an inconsistent state.
 */
public class ChatThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;

    /**
     * Creates a chat task representing a message being sent from one
     * student to another.
     *
     * @param sender the student sending the message
     * @param receiver the student receiving the message
     * @param message the message content to send
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
    }

    /**
     * Sends a specific message. Implementations use
     * synchronized blocks or thread safe structures to update chat history
     * and log the message for testing and verification.
     */
    @Override
    public void run() {
        // Method signature only
    }
}
