/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GUI {
    JPanel panel, topPanel;
    JButton add, delete;
    JTable table, resultSetUpdate, table1;
    //JPopupMenu pop;
    JMenuBar menuBar;
    JMenu file;
    DefaultTableModel dtm;
    JMenuItem sortByGame, sortByPercentage, sortByDifficulty, sortByAch;
   
        
    public GUI() throws ClassNotFoundException, SQLException{
        JFrame x = new JFrame();
        //this.setSize(600,600);
        x.setExtendedState(JFrame.MAXIMIZED_BOTH);
        x.setLayout(new BorderLayout());
        
        Container pane = x.getContentPane();
        DBConnection db = new DBConnection();
        panel = new JPanel();
        //menuBar = new JMenuBar();
        //file = new JMenu("File");
        
        //menuBar.add(file);
        
        //sortByGame = new JMenuItem("Sort by Game");
        //sortByPercentage = new JMenuItem("Sort by %");
        //sortByDifficulty = new JMenuItem("Sort by Difficulty");
        //sortByAch = new JMenuItem("Sort by # of Achievements");
        
        //JCheckBoxMenuItem checkAction = new JCheckBoxMenuItem("Check Action");
        //JRadioButtonMenuItem radioAction1 = new JRadioButtonMenuItem("Radio Button1");
        //JRadioButtonMenuItem radioAction2 = new JRadioButtonMenuItem("Radio Button2");
        
        //ButtonGroup bg = new ButtonGroup();
        //bg.add(radioAction1);
        //bg.add(radioAction2);
        
        //file.add(sortByGame);
        //file.add(sortByPercentage);
        //file.add(sortByDifficulty);
        //file.add(sortByAch);
        
        //x.setJMenuBar(menuBar);
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add = new JButton("   ADD   ");
        add.addActionListener(new addButtonListener());
        delete = new JButton("DELETE");
        
        topPanel.add(add);
        topPanel.add(delete);
        pane.add(topPanel, BorderLayout.PAGE_START);

	String headers [] = new String [] {"#","Name","Album", "Studio"};
        
        table1 = new JTable();
        dtm = new DefaultTableModel(0,0);
        dtm.setColumnIdentifiers(headers);
        table1.setModel(dtm);
                
        for(int count =1; count<=db.getRowNumber(); count++){
            System.out.println("count " +count);
            dtm.addRow(new Object[] { count,
                db.getString("select recgroup.gName from album natural join recgroup natural join recstudio offset " + (count-1) + " row", "gName"),
                db.getString("select album.aTitle from album natural join recgroup natural join recstudio  offset "+ (count-1)+" row", "aTitle"),
                db.getString("Select recstudio.sName from album natural join recgroup natural join recstudio offset " + (count-1) + " row", "sName")
            });
        }
        
        //table1.setSize(1000,700);
        table1.setPreferredScrollableViewportSize(table1.getPreferredSize());
        table1.setFillsViewportHeight(true);
        //table1.setExtendedState(JTable.MAXIMIZED_BOTH);
        pane.add(new JScrollPane(table1), BorderLayout.CENTER);
        
        x.setVisible(true);
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   

    }
    
    public void updateTable(JTable t) throws ClassNotFoundException, SQLException{
            DBConnection db = new DBConnection();
            int count = db.getRowNumber();//-1
            //debugging 
           
            System.out.println("COUNT: " + count);
            System.out.println(db.getRowNumber());
            dtm.addRow(new Object[] { count,
                db.getString("select recgroup.gName from album natural join recgroup natural join recstudio offset " + (count) + " row", "gName"),
                db.getString("select album.aTitle from album natural join recgroup natural join recstudio  offset "+ (count)+" row", "aTitle"),
                db.getString("Select recstudio.sName from album natural join recgroup natural join recstudio offset " + (count) + " row", "sName")
            });
        }
    
     class addButtonListener implements ActionListener{
        @Override 
        public void actionPerformed(ActionEvent e){
            DBConnection db = null;
            //try to get connection
            try {
                db = new DBConnection();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            //try to create the frame work for adding
            try{
                //JFrame main= new JFrame("Add some Game");
                JTextField aTitle = new JTextField(10);
                JTextField gName= new JTextField(10);
                JTextField sName= new JTextField(10);
                JTextField aDaterec = new JTextField(10);
                JTextField aLength = new JTextField(10);
                JTextField aSongs = new JTextField(10);
                JTextField gSinger = new JTextField(10);
                JTextField gYrForm = new JTextField(10);
                JTextField gGenre = new JTextField(10);
                JTextField sAddr = new JTextField(10);
                JTextField sOwner = new JTextField(10);
                JTextField sPhone = new JTextField(10);
                
                JPanel myPanel = new JPanel(new BorderLayout(3,3));
                myPanel.setBorder(new EmptyBorder(5,5,5,5));
                
                JPanel labels = new JPanel(new GridLayout(0,1));
                JPanel controls = new JPanel(new GridLayout(0,1));
                myPanel.add(labels, BorderLayout.WEST);
                myPanel.add(controls,BorderLayout.CENTER);
                
                labels.add(new JLabel("Group:"));
                controls.add(gName);
                labels.add(new JLabel("Lead Singer:"));
                controls.add(gSinger);
                labels.add(new JLabel("Year Formed:"));
                controls.add(gYrForm);
                labels.add(new JLabel("Genre:"));
                controls.add(gGenre);
                labels.add(new JLabel("Album Name:"));
                controls.add(aTitle);
                labels.add(new JLabel("Date Recorded:"));
                controls.add(aDaterec);
                labels.add(new JLabel("Length:"));
                controls.add(aLength);
                labels.add(new JLabel("Number of Songs:"));
                controls.add(aSongs);
                labels.add(new JLabel("Studio:"));
                controls.add(sName);
                labels.add(new JLabel("Studio Address:"));
                controls.add(sAddr);
                labels.add(new JLabel("Studio Owner:"));
                controls.add(sOwner);
                labels.add(new JLabel("Studio Phone:"));
                controls.add(sPhone);
                
                int result = JOptionPane.showConfirmDialog(null, myPanel, "Add Info", JOptionPane.OK_CANCEL_OPTION);
                //if user hits ok
                if(result == JOptionPane.OK_OPTION){
                    String sql1 = "insert into recGroup values(?,?,?,?)";
                    String sql2 = "insert into recstudio values(?,?,?,?)";
                    String sql3 = "insert into album values (?,?,?,?,?,?)";
                    String groupName = gName.getText();
                    String groupSinger = gSinger.getText();
                    int groupYrFormed = Integer.parseInt(gYrForm.getText());
                    String groupGenre = gGenre.getText();
                    String albumName = aTitle.getText();
                    String albumDate = aDaterec.getText();
                    String albumLength = aLength.getText();
                    int albumSongs = Integer.parseInt(aSongs.getText());
                    String studioName = sName.getText();
                    String studioAddr = sAddr.getText();
                    String studioOwner = sOwner.getText();
                    String studioPhone = sPhone.getText();
                    //try to add to rec group table
                    try {
                            db.prepareStmtGroup(sql1,groupName, groupSinger, groupYrFormed, groupGenre);
                        } catch (SQLException ex) {
                           System.out.println("db.prepareStmtGroup didnt work "+ ex);
                        }
                    //try to add to rec studio table
                    try{
                        db.prepareStmtStudio(sql2,studioName, studioAddr, studioOwner, studioPhone);
                    } catch (SQLException ex){
                        System.out.println("db.prepareStmtStudio didn't work " + ex);
                    }
                    //try to add to album studio table
                    try{
                        db.prepareStmtAlbum(sql3, albumName, groupName, studioName, albumDate, albumLength, albumSongs);
                    }catch (SQLException ex){
                        System.out.println("db.prepareStmtAlbum didn't work " + ex);
                    }
                }//end if user hits ok
                //JOptionPane.showInputDialog(null, "ADD a game");
                
            }catch (Exception err){
                System.out.println(err);
            }
            System.out.println("You press " +e.getActionCommand());
            try {
                updateTable(table1);
                
                
                
                /*try {
                for(int count =db.getRowNumber(); count<=db.getRowNumber(); count++){
                dtm.addRow(new Object[] { count,
                db.getString("select gName from album ", "gName"),
                db.getString("select aTitle from album ", "aTitle"),
                db.getString("Select sName from album ", "sName")
                //db.getString("select aTitle from album", "aTitle"),
                //db.getString("select sName from recStudio", "sName")
                });
                }
                } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }//end action performed
        
    } //end action listener
    
}
