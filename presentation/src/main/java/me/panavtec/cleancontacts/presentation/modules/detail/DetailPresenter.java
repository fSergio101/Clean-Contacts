package me.panavtec.cleancontacts.presentation.modules.detail;

import me.panavtec.cleancontacts.domain.interactors.InteractorResult;
import me.panavtec.cleancontacts.domain.interactors.contacts.GetContactResponse;
import me.panavtec.cleancontacts.presentation.invoker.GenericErrorAction;
import me.panavtec.cleancontacts.presentation.invoker.InteractorExecution;
import me.panavtec.cleancontacts.presentation.invoker.InteractorInvoker;
import me.panavtec.cleancontacts.presentation.model.mapper.PresentationContactMapper;
import me.panavtec.cleancontacts.domain.interactors.contacts.GetContactInteractor;
import me.panavtec.threaddecoratedview.views.ThreadSpec;
import me.panavtec.threaddecoratedview.views.ViewInjector;

public class DetailPresenter {

  private final String contactMd5;
  private final InteractorInvoker interactorInvoker;
  private final GetContactInteractor getContactInteractor;
  private final PresentationContactMapper presentationContactMapper;
  private final ThreadSpec mainThreadSpec;
  private DetailView view;

  public DetailPresenter(String contactMd5, InteractorInvoker interactorInvoker,
      GetContactInteractor getContactInteractor,
      PresentationContactMapper presentationContactMapper, ThreadSpec mainThreadSpec) {

    this.contactMd5 = contactMd5;
    this.interactorInvoker = interactorInvoker;
    this.getContactInteractor = getContactInteractor;
    this.presentationContactMapper = presentationContactMapper;
    this.mainThreadSpec = mainThreadSpec;

  }

  public void attachView(DetailView view) {
    this.view = ViewInjector.inject(view, mainThreadSpec);
    onViewAttached();
  }

  public void detachView() {
    view = ViewInjector.nullObjectPatternView(view);
  }

  public void onViewAttached() {
    view.initUi();
  }

  public void onResume() {
    obtainContact();
  }

  public void obtainContact() {
    getContactInteractor.setData(contactMd5);

    new InteractorExecution<GetContactResponse>().
        interactor(getContactInteractor).
        result(new InteractorResult<GetContactResponse>() {
          @Override public void onResult(GetContactResponse result) {
            view.showContactData(presentationContactMapper.modelToData(result.getResponse()));
          }
        }).
        genericError(new GenericErrorAction(view)).
        execute(interactorInvoker);
  }
}