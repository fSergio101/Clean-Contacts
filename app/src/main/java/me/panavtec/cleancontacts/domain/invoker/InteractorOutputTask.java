package me.panavtec.cleancontacts.domain.invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import me.panavtec.cleancontacts.domain.interactors.GenericInteractorError;
import me.panavtec.cleancontacts.domain.interactors.InteractorError;
import me.panavtec.cleancontacts.domain.interactors.InteractorErrorAction;
import me.panavtec.cleancontacts.domain.interactors.InteractorResponse;
import me.panavtec.cleancontacts.domain.interactors.InteractorResult;

public class InteractorOutputTask<T extends InteractorResponse> extends FutureTask<T>
    implements PriorizableInteractor {

  private final InteractorResult<T> output;
  private Map<Class<? extends InteractorError>, InteractorErrorAction> errorActions =
      new HashMap<>();
  private final InteractorErrorAction genericErrorAction;
  private final int priority;
  private final String description;

  public InteractorOutputTask(Callable<T> callable, int priority,
      InteractorResult<T> output, Map<Class<? extends InteractorError>,
      InteractorErrorAction> errorActions,
      InteractorErrorAction genericErrorAction) {
    super(callable);
    this.output = output;
    this.priority = priority;
    this.errorActions = errorActions;
    this.genericErrorAction = genericErrorAction;
    this.description = callable.getClass().toString();
  }

  @Override protected void done() {
    super.done();

    try {
      T response = get();

      //get nos dará la respuesta tras la ejecucion del metodo call del interactor correspondiente
      //si no hay errorres se llamara al onresult y acabara la ejecucion, si hay algun error se deberia
      //recorrer el map y llamar al error de la clase correspondiente o al generico en su defecto


      //get nos dará la respuesta tras la ejecucion del metodo call del interactor correspondiente
      //si no hay errorres se llamara al onresult y acabara la ejecucion, si hay algun error se deberia
      //recorrer el map y llamar al error de la clase correspondiente o al generico en su defecto

      if (response.hasError()) {
        errorAction(response);
      } else {
        output.onResult(response);
      }
    } catch (Exception e) {
      executeErrorAction(new GenericInteractorError(e));
    }
  }

  private <T extends InteractorResponse> void errorAction(T response) {
    InteractorError error = response.getError();
    InteractorErrorAction errorAction = errorActions.get(error.getClass());
    if (errorAction != null) {
      errorAction.onError(error);
    } else {
      executeErrorAction(new GenericInteractorError());
    }
  }

  private <T extends InteractorResponse> void executeErrorAction(
      GenericInteractorError genericInteractorError) {
    if (genericErrorAction != null) {
      genericErrorAction.onError(genericInteractorError);
    }
  }

  public int getPriority() {
    return priority;
  }

  @Override public String getDescription() {
    return description;
  }
}

//if (response.hasError()){
//    for (Class<?> error :errorActions.keySet()){
//    if (response.getError().getClass().equals(error)){
//    errorActions.get(error).onError(response.getError());
//    }
//    }
//    }else{
//    output.onResult(response);
//    }
//
//    } catch (Exception e) {
//    unhandledException(e);
//    }
//    }
//
//private void unhandledException(Throwable cause) {
//    GenericInteractorError genericInteractorError = new GenericInteractorError(cause);
//    genericErrorAction.onError(genericInteractorError);
//
//    }