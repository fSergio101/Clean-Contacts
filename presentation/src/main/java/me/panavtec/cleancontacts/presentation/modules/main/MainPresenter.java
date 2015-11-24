package me.panavtec.cleancontacts.presentation.modules.main;

import java.util.List;
import me.panavtec.cleancontacts.domain.entities.Contact;
import me.panavtec.cleancontacts.domain.interactors.InteractorResult;
import me.panavtec.cleancontacts.domain.interactors.contacts.GetContactsInteractor;
import me.panavtec.cleancontacts.domain.interactors.contacts.GetContactsResponse;
import me.panavtec.cleancontacts.domain.interactors.contacts.exceptions.RetrieveContactsError;
import me.panavtec.cleancontacts.presentation.invoker.GenericErrorAction;
import me.panavtec.cleancontacts.presentation.invoker.InteractorExecution;
import me.panavtec.cleancontacts.presentation.invoker.InteractorInvoker;
import me.panavtec.cleancontacts.presentation.model.PresentationContact;
import me.panavtec.cleancontacts.presentation.model.mapper.base.ListMapper;
import me.panavtec.cleancontacts.presentation.modules.detail.DetailView;
import me.panavtec.threaddecoratedview.views.ThreadSpec;
import me.panavtec.threaddecoratedview.views.ViewInjector;

public class MainPresenter {
  private final InteractorInvoker interactorInvoker;
  private final GetContactsInteractor getContactsInteractor;
  private final ListMapper<Contact, PresentationContact> listMapper;
  private final ThreadSpec mainThreadSpec;
  private MainView view;

  public MainPresenter(InteractorInvoker interactorInvoker,
      GetContactsInteractor getContactsInteractor,
      final ListMapper<Contact, PresentationContact> listMapper, ThreadSpec mainThreadSpec) {

    this.interactorInvoker = interactorInvoker;
    this.getContactsInteractor = getContactsInteractor;
    this.listMapper = listMapper;
    this.mainThreadSpec = mainThreadSpec;
  }

  public void attachView(MainView view) {
    this.view = ViewInjector.inject(view, mainThreadSpec);
    onViewAttached();
  }

  public void onViewAttached() {
    view.initUi();
  }

  public void detachView() {
    view = ViewInjector.nullObjectPatternView(view);
  }

  public void onResume() {
    refreshContactList();
  }

  public void onRefresh() {
    view.refreshUi();
    refreshContactList();
  }

  private void refreshContactList() {

    new InteractorExecution<GetContactsResponse>().
        interactor(getContactsInteractor).
        result(new InteractorResult<GetContactsResponse>() {
          @Override public void onResult(GetContactsResponse result) {
            List<PresentationContact> presentationContacts = listMapper.modelToData(result.getResponse());
            view.refreshContactsList(presentationContacts);
          }
        }).error(RetrieveContactsError.class, new RetrieveContactsErrorAction(view)).
        genericError(new GenericErrorAction(view)).
        execute(interactorInvoker);
  }

}