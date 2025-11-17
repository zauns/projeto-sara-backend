package sara.emprega.msusers.util.user_concurrency;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sara.emprega.msusers.util.user_concurrency.abstractions.UserOperation;
import sara.emprega.msusers.util.user_concurrency.strategy.UserUpdateContext;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

@Service
public class UserProcessor {

    private final Queue<UserOperation> toProcess = new ConcurrentLinkedDeque<>();
    int nCpus = Runtime.getRuntime().availableProcessors();
    double ioWaitRatio = 0.8;
    int idealPermits = (int) (nCpus * (1 + (ioWaitRatio / (1 - ioWaitRatio))));
    
    private final Executor ioBoundExecutor;
    private final Semaphore semaphore = new Semaphore(idealPermits);
    UserUpdateContext concurrencyContext;

    public UserProcessor(@Qualifier("ioBound") Executor ioBoundExecutor, UserUpdateContext concurrencyContext) {
        this.ioBoundExecutor = ioBoundExecutor;
        this.concurrencyContext = concurrencyContext;
    }

    public void addToQueue(UserOperation operation) {
        toProcess.add(operation);
    }

    @Scheduled(fixedRate = 100)
    public void process(){
        while (!toProcess.isEmpty()) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                //logg
                return;
            }
            UserOperation userOperation = toProcess.poll();
            if (userOperation == null) {
                semaphore.release();
                continue;
            }
            ioBoundExecutor.execute(() -> {
                try {
                    System.out.println("concorrency" + userOperation.getAction() +   userOperation.getMail());
                    concurrencyContext.execute(userOperation);
                } catch (Exception e) {
                    // Substitua por um logger e uma "Dead Letter Queue" (DLQ)
                    // e.printStackTrace();
                    //logger.error("Falha ao processar operação: {}", userOperation, e);
                    //deadLetterQueue.add(userOperation);
                } finally {
                    semaphore.release();
                }
            });
        }
    }
}
