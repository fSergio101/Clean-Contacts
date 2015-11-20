package me.panavtec.cleancontacts.presentation.invoker;

import java.util.concurrent.Future;
import me.panavtec.cleancontacts.domain.interactors.InteractorResponse;

public interface InteractorInvoker {

  <T extends InteractorResponse> Future<T> execute(InteractorExecution<T> execution);
}
