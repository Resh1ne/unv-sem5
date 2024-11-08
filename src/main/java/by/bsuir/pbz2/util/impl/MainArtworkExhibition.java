package by.bsuir.pbz2.util.impl;

import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.dao.ArtworkDao;
import by.bsuir.pbz2.data.dao.ArtworkExhibitionDao;
import by.bsuir.pbz2.data.dao.ExhibitionDao;
import by.bsuir.pbz2.data.dao.impl.ArtworkDaoImpl;
import by.bsuir.pbz2.data.dao.impl.ArtworkExhibitionDaoImpl;
import by.bsuir.pbz2.data.dao.impl.ExhibitionDaoImpl;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.ArtworkExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;
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
        ArtworkDao artworkDao = getArtworkDao();
        Artwork artwork2 = artworkDao.findById(3L);

        ExhibitionDao exhibitionDao = getExhibitionDao();
        Exhibition exhibition2 = exhibitionDao.findById(4L);

        ArtworkExhibition artworkExhibition = new ArtworkExhibition();
        artworkExhibition.setArtworkId(artwork2);
        artworkExhibition.setExhibitionId(exhibition2);
        artworkExhibitionDao.create(artworkExhibition);
        System.out.println(artworkExhibitionDao.findByExhibitionArtworkId(artwork2, exhibition2));
//        System.out.println("================deleting=========================");
//        ArtworkDao artworkDao = getArtworkDao();
//        Artwork artwork3 = artworkDao.findById(1L);
//        ExhibitionDao exhibitionDao = getExhibitionDao();
//        Exhibition exhibition3 = exhibitionDao.findById(4L);
//        ArtworkExhibition artworkExhibition2 = new ArtworkExhibition();
//        artworkExhibition2.setArtworkId(artwork3);
//        artworkExhibition2.setExhibitionId(exhibition3);
//        System.out.println(artworkExhibitionDao.delete(artworkExhibition2));
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

    private static ArtworkDao getArtworkDao() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("src\\main\\resources\\app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        DataSource dataSource = new DataSourceImpl(password, user, url, driver);
        return new ArtworkDaoImpl(dataSource);
    }

    private static ExhibitionDao getExhibitionDao() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("src\\main\\resources\\app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        DataSource dataSource = new DataSourceImpl(password, user, url, driver);
        return new ExhibitionDaoImpl(dataSource);
    }
}
