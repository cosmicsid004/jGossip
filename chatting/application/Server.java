package chatting.application;

import javax.swing.*; //comes under extended java package
import javax.swing.border.EmptyBorder;
import java.awt.*; //Color class under it
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;

public class Server implements ActionListener {

    JTextField text; //declaring it globally so that we can use it other class to retrieve values in it
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame(); //we are doing this because in main method we can not call non-static method (i:e validate()) therefore now we will call all its methods by f.(method)
    static DataOutputStream dout;//declaring it globally so that we can use it different methods

    Server() { //constructor

        f.setLayout(null); //to make my own layout

        JPanel p1 = new JPanel(); //to add/divide something on the frame
        p1.setBackground(Color.GRAY); //setting background color of panel p1
        p1.setLayout(null);
        p1.setBounds(0,0,450,70); //specifying where it appears on the frame
        f.add(p1); //to set a component on the frame

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT); //Scaling sown the large image
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25); //it only works when setLayout is set to null
        p1.add(back); //adding the image over panel p1

        back.addMouseListener(new MouseAdapter() { //adding a Mouse clicking event on 'back'
            @Override
            public void mouseClicked(MouseEvent e) { //overriding Mouse clicked event to specify what I want it to do when clicked
                System.exit(0); //to close the whole programme
            }
        });

        //to set profile image
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/matching-anime-profile-pictures-1080-x-1080-dqzp116tagzpabw3-3730336476.jpg"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT); //Scaling sown the large image
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50); //it only works when setLayout is set to null
        p1.add(profile); //adding the image over panel p1

        //to set video call icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT); //Scaling sown the large image
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30); //it only works when setLayout is set to null
        p1.add(video); //adding the image over panel p1

        //to set call icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT); //Scaling sown the large image
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,30,30); //it only works when setLayout is set to null
        p1.add(phone); //adding the image over panel p1

        //to set more option icon
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT); //Scaling sown the large image
        ImageIcon i15 = new ImageIcon(i14);
        JLabel more = new JLabel(i15);
        more.setBounds(410,20,10,25); //it only works when setLayout is set to null
        p1.add(more); //adding the image over panel p1

        //to add name Label
        JLabel name = new JLabel("Cutie");
        name.setBounds(110,15,100,18);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        //activity status
        JLabel status = new JLabel("Active....");
        status.setBounds(110,35,100,18);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);

        //the area where all the message are shown
        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        //place where we will write our message
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        f.add(text);

        //send button
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(Color.GRAY);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 24));
        f.add(send);

        f.setSize(450,700); //inside JFrame (setting its size)
        f.setUndecorated(true); //to remove the top close,minimise buttons
        f.setLocation(200,50); //setting where the frame will appear
        f.getContentPane().setBackground(Color.WHITE); //getContentPane():to select whole frame

        f.setVisible(true); //by default frame visibility is false so we are making it true. Frame always appear from origin (left-top)
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //an abstract method inside ActionListener which we need to override
        try {//using try block as we are using server here so it's possible to encounter some error.Also, JAVA won't let us run code without this try block.
            String out = text.getText();
            System.out.println(out);

            JLabel output = new JLabel(out);

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(10));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();//to print error if occurred
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Server(); //anonymous object

        try {
            ServerSocket skt = new ServerSocket(6001);
            while (true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel((new BorderLayout()));
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();//to print error if occurred
        }
    }

}
