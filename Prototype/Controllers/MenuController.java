package Controllers;

import Views.MenuView;

public class MenuController {

    private MenuView menuView;

    public MenuController(MenuView menuView){
        this.menuView = menuView;
    }

    public void showLogin(){
        menuView.loginView();
    }



}
