import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecursiveListerFrame extends JFrame {
    JPanel mainPnl, displayPnl, controlPnl, titlePnl;

    JLabel titleLbl;
    JScrollPane pane;
    JTextArea textArea;

    JButton quitBtn, searchBtn;


    public RecursiveListerFrame()
    {
        setTitle("Recursive Directory Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        setSize(750, 600);

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        add(mainPnl);
        createDisplayPanel();
        createControlPanel();
        createTitle();
        setVisible(true);
    }

    public void createTitle()
    {
        titlePnl = new JPanel();
        titleLbl = new JLabel("File Lister");
        titleLbl.setVerticalTextPosition(JLabel.BOTTOM);
        titleLbl.setHorizontalTextPosition(JLabel.CENTER);
        titlePnl.add(titleLbl);
        mainPnl.add(titlePnl, BorderLayout.NORTH);
    }
    private void createDisplayPanel()

    {
        displayPnl = new JPanel();

        textArea = new JTextArea(40, 60);
        pane = new JScrollPane(textArea);

        textArea.setEditable(false);

        displayPnl.add(pane);
        mainPnl.add(displayPnl, BorderLayout.CENTER);
    }


    private void createControlPanel() {
        controlPnl = new JPanel();
        controlPnl.setLayout(new GridLayout(1, 2));

        searchBtn = new JButton("Search");
        quitBtn = new JButton("Quit");

        controlPnl.add(searchBtn);
        controlPnl.add(quitBtn);

        mainPnl.add(controlPnl, BorderLayout.SOUTH);


        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Choose A Directory: ");

                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);


                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File chosenDir = chooser.getSelectedFile();
                    textArea.setText("Chosen Directory:   " + chosenDir + "\n\n");
                    textArea.append("Directory and Sub Directories: ." +"\n\n\n");


                    //list the file names + list directory names and sub files and directory names
                    listFiles(chosenDir);

                } else
                    textArea.append("File not found! Try Again!");
            }
        });

    }




    private void listFiles(File directory) {
        // Get the files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            // Iterate over the files
            for (File file : files) {
                if (file.isDirectory()) {
                    listFiles(file);
                } else {
                    // If the file is a file, append its path to the JTextArea
                    textArea.append(file.getPath() + "\n");
                }
            }
        }
    }


    }

