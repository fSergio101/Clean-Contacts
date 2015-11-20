package me.panavtec.cleancontacts.presentation.invoker;

import me.panavtec.cleancontacts.domain.interactors.InteractorError;
import me.panavtec.cleancontacts.domain.interactors.InteractorErrorAction;
import me.panavtec.cleancontacts.presentation.modules.GenericView;

public class GenericErrorAction implements InteractorErrorAction {
  private final GenericView view;

  public GenericErrorAction(GenericView view) {
    this.view = view;
  }

  @Override public void onError(InteractorError error) {
    view.genericError();
  }
}
