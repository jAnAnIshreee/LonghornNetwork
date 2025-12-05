import java.util.concurrent.Semaphore;

public class FriendRequestThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;

    private static final Semaphore semaphore = new Semaphore(1);

    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();

            // Add sender's name to receiver's friendRequests list
            receiver.addFriendRequest(sender.name);

            System.out.println("FriendRequest (Thread-Safe): " + sender.name + " sent a friend request to " + receiver.name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("FriendRequest interrupted: " + e.getMessage());
        } finally {
            semaphore.release();
        }
    }
}
