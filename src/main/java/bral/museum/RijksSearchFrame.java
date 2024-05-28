package bral.museum;

import bral.museum.json.ArtObject;
import bral.museum.json.ArtObjects;
import com.andrewoid.ApiKey;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

public class RijksSearchFrame extends JFrame
{
    private JTextField searchField;
    private JButton nextButton;
    private JButton prevButton;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JPanel resultsPanel;
    private int currentPage = 1;
    private String currentQuery = "";
    private RijksService service;

    public RijksSearchFrame()
    {
        setTitle("Rijks");
        setSize(1100, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        service = new RijksServiceFactory().getService();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        searchField = new JTextField();
        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");

        controlPanel.add(prevButton, BorderLayout.WEST);
        controlPanel.add(searchField, BorderLayout.CENTER);
        controlPanel.add(nextButton, BorderLayout.EAST);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(2, 5, 10, 10));

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(resultsPanel), BorderLayout.CENTER);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });
        nextButton.addActionListener(e -> nextPage());
        prevButton.addActionListener(e -> prevPage());

        loadResults();
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
        //currentPage = 1;
        loadResults();

    }

    private void loadResults()
    {
        ApiKey apiKey = new ApiKey();
        ArtObjects artObjectsResponse;
        resultsPanel.removeAll();
        if(currentQuery.isEmpty())
        {
            artObjectsResponse = service.getCollectionByPage(
                    apiKey.get(),
                    currentPage
            ).blockingGet();
        } else {
            artObjectsResponse = service.searchCollectionByQuery(
                    apiKey.get(),
                    currentQuery,
                    currentPage
            ).blockingGet();
        }

        for (ArtObject artObject : artObjectsResponse.artObjects)
        {
            JLabel label = new JLabel();
            try
            {
                URL url = new URL(artObject.webImage.url);
                Image image = ImageIO.read(url);
                Image scaledImage = image.getScaledInstance(200, -1, Image.SCALE_DEFAULT);
                ImageIcon imageIcon = new ImageIcon(scaledImage);
                label.setIcon(imageIcon);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            resultsPanel.add(label);
        }
        repaint();


    }


}
