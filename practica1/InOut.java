import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by GERARD on 11/03/2016.
 */
public class InOut extends JFrame{
    private JLabel seedlbl;
    private JLabel userlbl;
    private JLabel serverslbl;
    private JLabel maxreqlbl;
    private JTextField seedtxt;
    private JTextField userstxt;
    private JTextField serverstxt;
    private JTextField maxreqtxt;
    private JLabel maxreplbl;
    private JTextField maxreptxt;
    private JButton sendbtn;
    private JComboBox<String> results;
    private JPanel panel;

    public InOut() {
        setContentPane(panel);
        sendbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                results.removeAllItems();
                int seed = Integer.parseInt(seedtxt.getText());
                int servers = Integer.parseInt(serverstxt.getText());
                int users = Integer.parseInt(userstxt.getText());
                int maxrep = Integer.parseInt(maxreptxt.getText());
                int maxreq = Integer.parseInt(maxreqtxt.getText());
                Vector<Integer> v;
                Practica1Board p = new Practica1Board(users,maxreq,servers,maxrep,seed);
                v = p.solIni1(p);
                for (int i = 0; i < v.size(); ++i) results.addItem("Request " + i + " a servidor: " + v.get(i) + "\n");
            }
        });
        setVisible(true);
    }
}
