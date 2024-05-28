package bral.museum;

import bral.museum.json.ArtObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ImageFrame extends JFrame
{
    public ImageFrame(ArtObject artObject)
    {
        setTitle(artObject.title + " by " + artObject.principalOrFirstMaker);
        setSize(900, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            URL url = new URL(artObject.webImage.url);
            Image image = ImageIO.read(url);
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(imageIcon);

            JScrollPane scrollPane = new JScrollPane(imageLabel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            add(scrollPane, BorderLayout.CENTER);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
