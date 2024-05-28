package bral.museum;

import bral.museum.json.ArtObject;
import bral.museum.json.ArtObjects;
import com.andrewoid.ApiKey;
import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    private ApiKey apiKey;
    ArtObjects artObjectsResponse;

    public RijksSearchFrame()
    {
        setTitle("Rijks");
        setSize(1100, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        apiKey = new ApiKey();
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
        currentPage = 1;
        loadResults();
    }



    private void loadResults()
    {

        if (currentQuery == "") {
            Disposable disposable = service.getCollectionByPage(
                            apiKey.get(),
                            currentPage
                    )
                    // tells Rx to request the data on a background Thread
                    .subscribeOn(Schedulers.io())
                    // tells Rx to handle the response on Swing's main Thread
                    .observeOn(SwingSchedulers.edt())
                    //.observeOn(AndroidSchedulers.mainThread()) // Instead use this on Android only
                    .subscribe(
                            (response) -> handleResponse(response),
                            Throwable::printStackTrace);

        } else {
            Disposable disposable = service.searchCollectionByQuery(
                    apiKey.get(),
                    currentQuery,
                    currentPage
                    )
                    // tells Rx to request the data on a background Thread
                    .subscribeOn(Schedulers.io())
                    // tells Rx to handle the response on Swing's main Thread
                    .observeOn(SwingSchedulers.edt())
                    //.observeOn(AndroidSchedulers.mainThread()) // Instead use this on Android only
                    .subscribe(
                            (response) -> handleResponse(response),
                            Throwable::printStackTrace);

        }
    }

    private void handleResponse(ArtObjects response) {
        resultsPanel.removeAll();
        resultsPanel.revalidate();
        resultsPanel.repaint();
        for (ArtObject artObject : response.artObjects)
        {
            JLabel label = new JLabel();
            label.setToolTipText(artObject.title + ", " + artObject.principalOrFirstMaker);
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
