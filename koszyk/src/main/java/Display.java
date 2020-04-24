import java.awt.*;

public class Display {
    //lista przedmiotów dostępnych w sklepie
    private static String[] items = {"Szczoteczka do zębów", "Sok pomarańczowy", "Zabawka dla dziecka", "Szczotka do włosów", "Szampon"};

    public static String[] getItems(){
        return items;
    }

    //wyświetla sklep
    public void doDisplay(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShopView();
            }
        });
    }

}
