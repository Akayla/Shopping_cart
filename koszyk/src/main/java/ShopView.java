import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;

//wyświetla prosty "sklep" i pozwala na dodanie wybranej liczby przedmiotów do koszyka zakupowego
public class ShopView extends JFrame {

    String[] items = Display.getItems();

    DefaultListModel model = new DefaultListModel();
    JList content = new JList(model);
    JPanel pane = new JPanel(new GridBagLayout());
    JPanel extra = new JPanel(new GridBagLayout());
    JButton addTo = new JButton("Dodaj do koszyka");
    JButton exit = new JButton("Twój koszyk");
    JButton plus = new JButton("+");
    JButton minus = new JButton("-");
    String count = "0";
    Integer a = 0;
    JLabel amount = new JLabel(count);
    JLabel label = new JLabel("Liczba sztuk: ");
    ShopView window;

    public ShopView() {
        super( "Lista produktów" );
        window = this;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 350);
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setLayout(new FlowLayout(FlowLayout.CENTER));


        for (int i = 0; i < items.length; i++) {
            String var = items[i];
            model.insertElementAt(var, i);
        }

        content.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(content, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,10,0);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(addTo, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,10,0);
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(exit, c);

        GridBagConstraints d = new GridBagConstraints();
        d.fill = GridBagConstraints.HORIZONTAL;
        d.insets = new Insets(10,0,10,5);
        d.gridwidth = 1;
        d.gridx = 0;
        d.gridy = 0;
        extra.add(label, d);

        d.fill = GridBagConstraints.HORIZONTAL;
        d.insets = new Insets(10,0,10,15);
        d.gridwidth = 1;
        d.gridx = 1;
        d.gridy = 0;
        extra.add(amount, d);

        d.fill = GridBagConstraints.HORIZONTAL;
        d.insets = new Insets(10,0,10,5);
        d.gridwidth = 1;
        d.gridx = 2;
        d.gridy = 0;
        extra.add(plus, d);

        d.fill = GridBagConstraints.HORIZONTAL;
        d.insets = new Insets(10,0,10,0);
        d.gridwidth = 1;
        d.gridx = 3;
        d.gridy = 0;
        extra.add(minus, d);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,10,0);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(extra, c);

        this.add(pane);

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new BasketView();
                    }
                });
                window.dispatchEvent(new WindowEvent(
                        window, WindowEvent.WINDOW_CLOSING));
            }

        });

        plus.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                a++;
                count = a.toString();
                amount.setText(count);
            }

        });


        minus.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(a > 0){
                    a--;
                    count = a.toString();
                    amount.setText(count);
                }
            }

        });


        addTo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(!content.isSelectionEmpty())
                    window.basketAdd();
                else JOptionPane.showMessageDialog(null, "Wybierz przedmiot do dodania!");
            }

        });

        setVisible(true);
    }

    //dodaje przedmioty do koszyka i resetuje licznik
    private void basketAdd() {
        if(a > 0){
            HashMap<Integer, Integer> temp = Basket.getContents();
            int index = content.getSelectedIndex();

            if(temp.get(index) != null){
                int inBasket = temp.get(index);
                inBasket = inBasket + a;
                Basket.addToBasket(index, inBasket);
            }
            else {
                Basket.setContents(index, a);
            }

            a = 0;
            count = "0";
            amount.setText(count);

            JOptionPane.showMessageDialog(null, "Przedmiot dodany!");
        }
        else JOptionPane.showMessageDialog(null, "Nie wybrano liczby przedmiotów!");

    }
}
