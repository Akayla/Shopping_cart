import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;

//wyświetla okno z zawartością koszyka lub informacją, jesli jest pusty; pozwala na usunięcie niechcianych przedmiotów
//można połączyć z witryną sklepową poprzez dodanie przycisku kup do layoutu
public class BasketView extends JFrame {

    String[] items = Display.getItems();

    DefaultListModel model = new DefaultListModel();
    JList content = new JList(model);
    JPanel pane = new JPanel(new GridBagLayout());
    JButton delete = new JButton("Usuń");
    JButton exit = new JButton("Lista produktów");
    JLabel label = new JLabel("Koszyk jest pusty!", SwingConstants.CENTER);
    GridBagConstraints c = new GridBagConstraints();
    BasketView window;

    public BasketView() {
        super( "Twój koszyk" );
        window = this;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 350);
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setLayout(new FlowLayout(FlowLayout.CENTER));


        HashMap<Integer, Integer> temp;
        temp = Basket.getContents();

        content.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if(!temp.isEmpty()){
            int index = 0;
            for ( Integer key : temp.keySet() ) {
                String var = items[key] + ", sztuk: " + temp.get(key);
                model.insertElementAt(var, index);
                index++;
            }
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 1;
            pane.add(content, c);

        } else{

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 1;
            pane.add(label, c);
        }


        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,10,0);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(delete, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,10,0);
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(exit, c);

        this.add(pane);

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ShopView();
                }
            });

                window.dispatchEvent(new WindowEvent(
                        window, WindowEvent.WINDOW_CLOSING));
            }

        });


        delete.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(temp.isEmpty()) JOptionPane.showMessageDialog(null, "Koszyk jest pusty!");
                else {
                    if(!content.isSelectionEmpty()){
                        window.basketDelete();
                        JOptionPane.showMessageDialog(null, "Przedmiot usunięty!");
                    } else JOptionPane.showMessageDialog(null, "Nie wybrano przedmiotu do usunięcia!");
                }
            }

        });

        setVisible(true);
    }

    //iteruję po wybranym przedmiocie aż trafię na przecinek, następnie porównuję z listą dostępnych przedmiotów
    //rozwiązanie nie nadaje się do dużego sklepu, ale jest wystarczające na potrzeby tego przykładu
    private void basketDelete(){
        int index = content.getSelectedIndex();
        Object temp = model.getElementAt(index);
        String filter = temp.toString();
        String toToss = "";
        char comma = 44;
        Integer id = -1;

        for(int i = 0; i < filter.length(); i++){
            if(filter.charAt(i) != comma){
                toToss = toToss + filter.charAt(i);
            }
            else break;
        }

        for(int i = 0; i < items.length; i++){
            if(items[i].equals(toToss)){
                id = i;
                break;
            }
        }

        Basket.deleteItem(id);
        model.remove(index);

        int size = model.getSize();

        if (size == 0) {
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 1;
            pane.add(label, c);
        }

    };

}
