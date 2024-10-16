package by.bsuir.pbz2.util.impl;

import by.bsuir.pbz2.data.ArtworkExhibitionDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.ArtworkExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;
import by.bsuir.pbz2.data.impl.ArtworkExhibitionDaoImpl;
import by.bsuir.pbz2.util.PropertiesManager;

import java.util.List;

public class MainArtworkExhibition {
    public static void main(String[] args) {
        ArtworkExhibitionDao artworkExhibitionDao = getArtworkExhibitionDao();
        System.out.println("==================findAll=======================");
        List<ArtworkExhibition> artworkExhibitions = artworkExhibitionDao.findAll();
        for (ArtworkExhibition artworkExhibition : artworkExhibitions) {
            System.out.println(artworkExhibition.toString());
        }
        System.out.println("==================findById=======================");
        Artwork artwork1 = new Artwork();
        artwork1.setId((long) 1);
        Exhibition exhibition1 = new Exhibition();
        exhibition1.setId((long) 1);
        System.out.println(artworkExhibitionDao.findByExhibitionArtworkId(artwork1, exhibition1));
        System.out.println("================creation=========================");
        Artwork artwork2 = new Artwork();
        artwork2.setId((long) 2);
        artwork2.setTitle("1");
        Exhibition exhibition2 = new Exhibition();
        exhibition2.setId((long) 3);
        exhibition2.setName("1");
        ArtworkExhibition artworkExhibition = new ArtworkExhibition();
        artworkExhibition.setArtworkId(artwork2);
        artworkExhibition.setExhibitionId(exhibition2);
        boolean createdArtworkExhibition = artworkExhibitionDao.create(artworkExhibition);
        System.out.println(createdArtworkExhibition);
        System.out.println(artworkExhibitionDao.findByExhibitionArtworkId(artwork2, exhibition2));
        System.out.println("================deleting=========================");
        Artwork artwork3 = new Artwork();
        artwork3.setId((long) 2);
        Exhibition exhibition3 = new Exhibition();
        exhibition3.setId((long) 3);
        ArtworkExhibition artworkExhibition2 = new ArtworkExhibition();
        artworkExhibition2.setArtworkId(artwork3);
        artworkExhibition2.setExhibitionId(exhibition3);
        System.out.println(artworkExhibitionDao.delete(artworkExhibition2));
    }

    private static ArtworkExhibitionDao getArtworkExhibitionDao() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("src\\main\\resources\\app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        DataSource dataSource = new DataSourceImpl(password, user, url, driver);
        return new ArtworkExhibitionDaoImpl(dataSource);
    }
}
