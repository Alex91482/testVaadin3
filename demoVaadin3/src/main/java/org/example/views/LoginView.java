package org.example.views;


import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.example.MyUI;
import org.example.entity.MyAccount;
import org.example.util.jdbc.dao.MyAccountDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginView extends VerticalLayout implements View {

    private final Logger logger = LoggerFactory.getLogger(LoginView.class);

    public LoginView() {
        demoPanel(this);
    }

    private void demoPanel(VerticalLayout verticalLayout){ //форма для входа

        Label label1 = new Label("Enter login and password");

        TextField tf1 = new TextField("User Name"); //создаем поле для ввода
        tf1.setIcon(VaadinIcons.USER); //вешаем значек
        tf1.setRequiredIndicatorVisible(true);

        PasswordField passwordField = new PasswordField("Password"); //поле для пароля
        passwordField.setIcon(VaadinIcons.LOCK);
        passwordField.setRequiredIndicatorVisible(true);

        Button button = new Button("OK"); //кнопка для подтверждения
        button.addClickListener(event -> {
            try {
                if (!tf1.getValue().equals("") && !passwordField.getValue().equals("")) {
                    //если логин и пароль не пустые то делаем запрос в бд
                    MyAccount myAccount = new MyAccountDAOImpl().findMyAccount(tf1.getValue(), passwordField.getValue());
                    if (myAccount.getUserName() != null && !myAccount.getUserName().equals("")) {
                        //проверяем поле username
                        ((MyUI)UI.getCurrent()).setLoggedInUser(myAccount.getId()); //добавляем id авторизованного пользователя
                        this.getUI().getNavigator().navigateTo("grid");
                    } else {
                        label1.setValue("User " + tf1.getValue() + " is not found");
                    }
                } else {
                    label1.setValue("Username and Password fields must be filled");
                }
            }catch (Exception e){
               logger.error(e.getMessage());
            }
        });

        verticalLayout.addComponents(label1,tf1,passwordField,button); //заварачивам все в панель
        verticalLayout.setComponentAlignment(label1, Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(tf1, Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
    }
}