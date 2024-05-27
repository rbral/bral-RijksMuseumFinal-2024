package bral.museum;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class RijksSearchFrame extends JFrame
{
    private JTextField searchField;
    private JButton nextButton;
    private JButton prevButton;
    private int currentPage = 1;
    private String currentQuery = "";
    private RijksService service;

    public RijksSearchFrame()
    {
        setTitle("Rijks");
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        service = new RijksServiceFactory().getService();

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        setContentPane(main);

        JPanel south = new JPanel();
        main.add(south, BorderLayout.SOUTH);
        south.setLayout(new GridLayout(2, 5));

        searchField = new JTextField();
        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");

        main.add(searchField, BorderLayout.CENTER);
        main.add(prevButton, BorderLayout.WEST);
        main.add(nextButton, BorderLayout.EAST);

        searchField.getDocument().addDocumentListener((new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }
        }));
        nextButton.addActionListener(e -> nextPage());
        prevButton.addActionListener(e -> prevPage());
    }

    private void prevPage() {
        if (currentPage > 1) {
            currentPage--;
            loadResults();
        }
    }

    private void nextPage() {
        currentPage++;
        loadResults();
    }

    private void performSearch() {
        currentQuery = searchField.getText();
        currentPage = 1;
        loadResults();

    }

    private void loadResults()
    {
        
    }


}
