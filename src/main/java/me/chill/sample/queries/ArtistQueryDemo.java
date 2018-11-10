package me.chill.sample.queries;

import com.neovisionaries.i18n.CountryCode;
import me.chill.models.Album;
import me.chill.models.Artist;
import me.chill.models.Paging;
import me.chill.models.Track;

import java.util.List;

import static me.chill.sample.queries.UserStore.user;

public class ArtistQueryDemo {
  public static void main(String[] args) {
    Artist artist = user
      .getSingleArtist("0OdUWJ0sBjDrqHygGUXeCF")
      .build()
      .execute();
    System.out.println(artist);

    Paging<Album> artistAlbums = user
      .getArtistAlbums("0OdUWJ0sBjDrqHygGUXeCF")
      .build()
      .execute();
    artistAlbums.getItems().forEach(System.out::println);

    List<Track> topTracks = user
      .getArtistTopTracks("43ZHCT0cAZBISjO8DG9PnE")
      .market(CountryCode.SG)
      .build()
      .execute();
    System.out.println(topTracks);

    List<Artist> relatedArtist = user
      .getRelatedArtists("43ZHCT0cAZBISjO8DG9PnE")
      .build()
      .execute();
    System.out.println(relatedArtist);

    List<Artist> artists = user
      .getSeveralArtists()
      .addArtists("0oSGxfWSnnOXhD2fKuz2Gy")
      .build()
      .execute();
    System.out.println(artists);
  }
}