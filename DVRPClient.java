import java.io.*;
import java.util.*;
import javax.swing.*;
import java.lang.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class DVRPClient {
  static int graph[][];
  static int nh[][];
  static int rtab[][];
  static int v;
  static int e;
  static int sn = 1;
  public static void main(String args[]) throws IOException {

    JTextField t1, t2;
    JLabel l1, l2;
    JButton jb;
    JFrame f = new JFrame("Distance Vector Routing Table Calculator");
    l1 = new JLabel("Enter no of vertices:");
    l1.setBounds(90, 50, 250, 25);
    t1 = new JTextField();
    t1.setBounds(90, 75, 200, 25);
    l2 = new JLabel("Enter no of Edges:");
    l2.setBounds(90, 120, 250, 25);
    t2 = new JTextField();
    t2.setBounds(90, 145, 200, 25);
    jb = new JButton("Calculate");
    jb.setBounds(1000, 300, 100, 25);
    JLabel h2 = new JLabel("Network Topology Table: ");
    JTextArea ta = new JTextArea();
    JScrollPane TAS = new JScrollPane(ta);
    JLabel h1 = new JLabel("Routing Tables Display Area");
    h1.setBounds(90, 280, 500, 100);
    TAS.setBounds(90, 350, 600, 400);
    h2.setBounds(820, 50, 200, 25);

    JTable table = new JTable();
    Object[] columns = {
      "Source Vertex",
      "Destination Vertex",
      "Link Cost"
    };
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(columns);
    table.setModel(model);

    table.setBackground(Color.WHITE);
    table.setForeground(Color.GRAY);
    Font font = new Font("", 1, 22);
    table.setFont(font);
    table.setRowHeight(20);

    JLabel l3 = new JLabel(" Enter Source Vertex:");
    l3.setBounds(420, 50, 250, 25);
    JTextField src = new JTextField();
    src.setBounds(420, 75, 200, 25);

    JLabel l4 = new JLabel("Enter Destination Vertex:");
    l4.setBounds(420, 120, 200, 25);
    JTextField dest = new JTextField();
    dest.setBounds(420, 145, 200, 25);

    JLabel l5 = new JLabel("Enter Link Cost:");
    l5.setBounds(420, 190, 200, 25);
    JTextField ct = new JTextField();
    ct.setBounds(420, 215, 200, 25);

    JButton addbtn = new JButton("Add Row");
    JButton delbtn = new JButton("Delete Row");

    addbtn.setBounds(420, 270, 90, 25);
    delbtn.setBounds(520, 270, 100, 25);

    JScrollPane pane = new JScrollPane(table);
    pane.setBounds(820, 80, 500, 200);
    f.add(l1);
    f.add(l2);
    f.add(l3);
    f.add(l4);
    f.add(l5);
    f.add(h1);
    f.add(h2);
    f.add(TAS);
    f.add(t1);
    f.add(t2);
    f.add(jb);
    f.add(pane);
    f.add(src);
    f.add(dest);
    f.add(ct);
    f.add(addbtn);
    f.add(delbtn);

    Object row[] = new Object[3];

    addbtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ace) {

        row[0] = src.getText();
        row[1] = dest.getText();
        row[2] = ct.getText();
        model.addRow(row);
      }
    });

    delbtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae2) {
        int i = table.getSelectedRow();
        if (i >= 0) {
          model.removeRow(i);
        } else {
          System.out.println("Delete Error");
        }
      }
    });

    table.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent me) {
        int i = table.getSelectedRow();
        src.setText(model.getValueAt(i, 0).toString());
        dest.setText(model.getValueAt(i, 1).toString());
        ct.setText(model.getValueAt(i, 2).toString());
      }
    });

    jb.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae3) {
        String s1 = t1.getText();
        String s2 = t2.getText();
        v = Integer.parseInt(s1);
        e = Integer.parseInt(s2);
        graph = new int[v][v];
        nh = new int[v][v];
        rtab = new int[v][v];
        for (int i = 0; i < v; i++)
          for (int j = 0; j < v; j++) {
            if (i == j)
              graph[i][j] = 0;
            else
              graph[i][j] = 9999;
          }
        try {
          int x = 0, y = 0;
          for (int i = 0; i < e; i++) {
            Object ob1 = table.getModel().getValueAt(x, y);
            int s = Integer.parseInt(ob1.toString());
            s--;
            Object ob2 = table.getModel().getValueAt(x, y + 1);
            int d = Integer.parseInt(ob2.toString());
            d--;
            Object ob3 = table.getModel().getValueAt(x, y + 2);
            int c = Integer.parseInt(ob3.toString());
            x = x + 1;
            graph[s][d] = c;
            graph[d][s] = c;
          }
          Socket s = new Socket("localhost", 5000);
          System.out.println("Connection established with server..");
          DataOutputStream dout = new DataOutputStream(s.getOutputStream());
          dout.writeUTF(t1.getText());
          DataOutputStream dout1 = new DataOutputStream(s.getOutputStream());
          dout1.writeUTF(t2.getText());
          ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
          os.writeObject(graph);
          ObjectInputStream is1 = new ObjectInputStream(s.getInputStream());
          rtab = (int[][]) is1.readObject();
          ObjectInputStream is2 = new ObjectInputStream(s.getInputStream());
          nh = (int[][]) is2.readObject();
          s.close();
        } catch (Exception e) {
          System.out.println(e);
        }
        String sb = new String();
        sb = display_rt();
        ta.setText(sb);
      }
    });
    f.setSize(1500, 1000);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
}
  static String display_rt() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < v; i++) {
      str.append("ROUTING TABLE FOR ROUTER" + (i + 1) + "\n");
      str.append("\n" + "Nodes" + "\t" + "Distance" + "\t" + "Next Hop/ Via" + "\n");
      for (int j = 0; j < v; j++) {
        str.append("" + (j + 1) + "\t" + rtab[i][j] + "\t" + (nh[i][j] + 1) + "\n");
      }
      str.append("\n");
    }
    return str.toString();
  }
}
