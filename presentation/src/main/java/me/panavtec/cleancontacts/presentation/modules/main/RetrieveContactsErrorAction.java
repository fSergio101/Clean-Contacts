package me.panavtec.cleancontacts.presentation.modules.main;

import me.panavtec.cleancontacts.domain.interactors.InteractorError;
import me.panavtec.cleancontacts.domain.interactors.InteractorErrorAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 23/11/15.
 */
public class RetrieveContactsErrorAction implements InteractorErrorAction {

  private MainView mainView;

  public RetrieveContactsErrorAction(MainView mainView) {
    this.mainView = mainView;
  }

  @Override public void onError(InteractorError error) {
    mainView.showGetContactsError();
  }
}
