package me.panavtec.cleancontacts.presentation.modules.detail;

import me.panavtec.cleancontacts.presentation.model.PresentationContact;
import me.panavtec.cleancontacts.presentation.modules.GenericView;
import me.panavtec.presentation.common.views.qualifiers.NoDecorate;
import me.panavtec.presentation.common.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView
public interface DetailView extends GenericView{

  @NoDecorate void initUi();

  void showContactData(PresentationContact contact);

  void showGetContactError();
}
