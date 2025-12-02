import java.util.concurrent.Semaphore;
/**
 * A thread task that simulates sending a friend request between two
 * UniversityStudent objects. Thread-safe operations are used when
 * modifying friend lists or shared state.
 */
public class FriendRequestThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;

   //Static semaphore to ensure thread-safe friend request operations
    private static final Semaphore semaphore = new Semaphore(1);
    /**
     * Creates a task to send a friend request from one student to another.
     *
     * @param sender the student sending the friend request
     * @param receiver the student receiving the friend request
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Completes the friend request operation. Ensures
     * thread safety when updating shared resources such as friend lists and
     * logs the interaction for verification.
     */
    @Override
    public void run() {
        try{
            semaphore.acquire();
            //simulate sending a friend request
            System.out.println("FriendRequest (Thread-Safe): " + sender.name + " sent a friend request to " + receiver.name);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println("FriendRequest interrupted: " + e.getMessage());
        } finally{
            semaphore.release();
        }
    }

}
