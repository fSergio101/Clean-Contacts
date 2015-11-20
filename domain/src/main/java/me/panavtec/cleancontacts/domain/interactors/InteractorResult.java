package me.panavtec.cleancontacts.domain.interactors;

public interface InteractorResult<T> {
  void onResult(T result);
}
