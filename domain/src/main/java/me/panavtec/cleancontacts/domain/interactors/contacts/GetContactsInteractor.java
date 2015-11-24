package me.panavtec.cleancontacts.domain.interactors.contacts;

import me.panavtec.cleancontacts.domain.interactors.Interactor;
import me.panavtec.cleancontacts.domain.interactors.contacts.exceptions.RetrieveContactsError;
import me.panavtec.cleancontacts.domain.interactors.contacts.exceptions.RetrieveContactsException;
import me.panavtec.cleancontacts.domain.repository.ContactsRepository;

public class GetContactsInteractor implements Interactor<GetContactsResponse> {

  private ContactsRepository repository;

  public GetContactsInteractor(ContactsRepository repository) {
    this.repository = repository;
  }

  @Override public GetContactsResponse call() throws RetrieveContactsException {
    GetContactsResponse getContactsResponse = new GetContactsResponse();
    getContactsResponse.setResponse(repository.obtainContacts());
    //getContactsResponse.setError(new RetrieveContactsError());
    //set business errors if proceed
    return getContactsResponse;
  }
}
