package me.chill.sample.queries;

import com.neovisionaries.i18n.CountryCode;
import me.chill.SpotifyUser;
import me.chill.models.Album;
import me.chill.models.Artist;
import me.chill.models.Paging;
import me.chill.models.Track;

import java.util.List;

public class ArtistQueryDemo {
  public static void main(String[] args) {
    SpotifyUser user = new SpotifyUser("BQCAaTIN0csJXOT2kpwaZtz2k57FIzgZ7PuvwCGTQ7AgAUOmVS__m3OUhh_CpVUEiscPIvb0Aq3hAzglNav1jSP_iE43azjjNugGwKN-1NhQ9ck1Hn7iLB6G_ptTWQwPBWNWdhIq4i8vxmcUApJZHfRXAX0ahYB7OUlqaOiiZLP7v8H4seLG0O1M6zHRkLNHYyGpgkQVSoknM9WxRFsUpmBjOBmlCCJj8LO12rRHOy-PTYV3IOhNK7-wk_uuLnOzWEH-zAUbfpvaGDMuMgfECg");
    Artist artist = user.getSingleArtist("0OdUWJ0sBjDrqHygGUXeCF").build().execute();
    System.out.println(artist);

    Paging<Album> artistAlbums = user.getArtistAlbums("0OdUWJ0sBjDrqHygGUXeCF").build().execute();
    System.out.println(artistAlbums);

    List<Track> topTracks = user.getArtistTopTracks("43ZHCT0cAZBISjO8DG9PnE").market(CountryCode.SG).build().execute();
    System.out.println(topTracks);

    List<Artist> relatedArtist = user.getRelatedArtists("43ZHCT0cAZBISjO8DG9PnE").build().execute();
    System.out.println(relatedArtist);

    List<Artist> artists = user.getSeveralArtists().addId("0oSGxfWSnnOXhD2fKuz2Gy").build().execute();
    System.out.println(artists);
  }
}