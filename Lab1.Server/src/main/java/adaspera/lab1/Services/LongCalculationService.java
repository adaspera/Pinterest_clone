package adaspera.lab1.Services;

import jakarta.ejb.AsyncResult;
import jakarta.ejb.Asynchronous;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.validation.constraints.Future;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class LongCalculationService implements Serializable {

    private CompletableFuture<Integer> future = new CompletableFuture<>();

    public void startLongCalculation(int input) {
        future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return input * input;
        });
    }

    public boolean isDone() {
        return future.isDone();
    }

    public int getResult() {
        return future.join();
    }
}