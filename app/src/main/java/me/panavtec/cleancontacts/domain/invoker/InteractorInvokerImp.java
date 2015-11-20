package me.panavtec.cleancontacts.domain.invoker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import me.panavtec.cleancontacts.domain.interactors.Interactor;
import me.panavtec.cleancontacts.presentation.invoker.InteractorExecution;
import me.panavtec.cleancontacts.domain.interactors.InteractorResponse;
import me.panavtec.cleancontacts.domain.interactors.InteractorResult;
import me.panavtec.cleancontacts.presentation.invoker.InteractorInvoker;

public class InteractorInvokerImp implements InteractorInvoker {

  private ExecutorService executor;

  public InteractorInvokerImp(ExecutorService executor,
      Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
    this.executor = executor;
  }

  @Override
  public <T extends InteractorResponse> Future<T> execute(InteractorExecution<T> execution) {

    Interactor interactor = execution.getInteractor();
    int priority = execution.getPriority();
    InteractorResult output = execution.getInteractorResult();

    return (Future<T>) executor.submit(new InteractorOutputTask<>(interactor, priority, output, execution.getErrorActions(), execution.getGenericErrorAction()));
  }
}