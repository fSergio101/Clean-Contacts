package me.panavtec.cleancontacts.presentation.modules.main;

import java.util.List;
import me.panavtec.cleancontacts.domain.entities.Contact;
import me.panavtec.cleancontacts.domain.interactors.InteractorResult;
import me.panavtec.cleancontacts.domain.interactors.contacts.GetContactsInteractor;
import me.panavtec.cleancontacts.domain.interactors.contacts.GetContactsResponse;
import me.panavtec.cleancontacts.presentation.invoker.GenericErrorAction;
import me.panavtec.cleancontacts.presentation.invoker.InteractorExecution;
import me.panavtec.cleancontacts.presentation.invoker.InteractorInvoker;
import me.panavtec.cleancontacts.presentation.model.PresentationContact;
import me.panavtec.cleancontacts.presentation.model.mapper.base.ListMapper;
import me.panavtec.presentation.Presenter;
import me.panavtec.presentation.common.ThreadSpec;

public class MainPresenter extends Presenter<MainView> {
  private final InteractorInvoker interactorInvoker;
  private final GetContactsInteractor getContactsInteractor;
  private final ListMapper<Contact, PresentationContact> listMapper;

  public MainPresenter(InteractorInvoker interactorInvoker,
      GetContactsInteractor getContactsInteractor,
      final ListMapper<Contact, PresentationContact> listMapper, ThreadSpec mainThreadSpec) {
    super(mainThreadSpec);
    this.interactorInvoker = interactorInvoker;
    this.getContactsInteractor = getContactsInteractor;
    this.listMapper = listMapper;
  }

  @Override public void onViewAttached() {
    getView().initUi();
  }

  public void onResume() {
    refreshContactList();
  }

  public void onRefresh() {
    getView().refreshUi();
    refreshContactList();
  }

  private void refreshContactList() {

    new InteractorExecution<GetContactsResponse>().
        interactor(getContactsInteractor).
        result(new InteractorResult<GetContactsResponse>() {
          @Override public void onResult(GetContactsResponse result) {
            List<PresentationContact> presentationContacts = listMapper.modelToData(result.getResponse());
            getView().refreshContactsList(presentationContacts);
          }
        }).
        genericError(new GenericErrorAction(getView())).
        execute(interactorInvoker);
  }

}