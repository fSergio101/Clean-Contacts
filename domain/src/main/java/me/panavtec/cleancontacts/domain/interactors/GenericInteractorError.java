package me.panavtec.cleancontacts.domain.interactors;

public class GenericInteractorError implements InteractorError {

  private Throwable cause;

  public GenericInteractorError() {}

  public GenericInteractorError(Throwable cause) {
    this.cause = cause;
  }

  public Throwable getCause() {
    return cause;
  }
}
