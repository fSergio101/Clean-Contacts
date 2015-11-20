package me.panavtec.cleancontacts.domain.interactors.contacts;

import me.panavtec.cleancontacts.domain.interactors.Interactor;
import me.panavtec.cleancontacts.domain.interactors.contacts.exceptions.ObtainContactException;
import me.panavtec.cleancontacts.domain.repository.ContactsRepository;

public class GetContactInteractor implements Interactor<GetContactResponse> {

  private ContactsRepository repository;
  private String contactMd5;

  public GetContactInteractor(ContactsRepository repository) {
    this.repository = repository;
  }

  public void setData(String contactMd5) {
    this.contactMd5 = contactMd5;
  }

  @Override public GetContactResponse call() throws ObtainContactException {
    GetContactResponse getContactResponse = new GetContactResponse();
    getContactResponse.setResponse(repository.obtain(contactMd5));
    return getContactResponse;
  }
}
