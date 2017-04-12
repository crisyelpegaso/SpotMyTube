package org.crisyelpegaso.spotmytube.org.crisyelpegaso.spotmytube;

import java.util.List;

import org.crisyelpegaso.spotmytube.model.TubePlaylistItem;
import org.crisyelpegaso.spotmytube.service.SpotService;
import org.crisyelpegaso.spotmytube.service.TubeService;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.authentication.ClientCredentialsGrantRequest;
import com.wrapper.spotify.models.ClientCredentials;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public final static String MY_USER_ID = "11101929981";
	final static String TEST_TUBE_PLAYLIST_ID = "PL46C5B67034209CD2";
    final static String TEST_ACCESS_TOKEN = "BQAUaomkWicJZ9uLyIvPO22m86Tvx8kl0j53-_gRMqYUrhZChPRXAhfPUkaHPNdBf88Fv_88W8bTfwfx-iax3evD95XYfOgB4aSIlFi2FNIdxHcHzsbOIQfN-5hOZGdRiqRMvjwIS4P-mf80v0WtEUJasojauqfdWushnFq3zfCp56wtSg51P8W7GwO1";
    
	public static void main( String[] args ) throws Exception {
//    	if (args.length < 1){
//    		throw new Exception("playlistId parameter needed!");
//    	}
    	
        //final String playlistId = args[0];

        final Api api = Api.builder()
        		  .clientId("9cf9c73e2d284289919c2c62e63ee231")
        		  .clientSecret("b604c127bc3c46df97a6dbf88051dc53")
        		  .redirectURI("http://spotmytube.com/callback")
        		  .build();
        

        final ClientCredentialsGrantRequest request = api.clientCredentialsGrant().build();
        final SettableFuture<ClientCredentials> responseFuture = request.getAsync();

        /* Add callbacks to handle success and failure */
        Futures.addCallback(responseFuture, new FutureCallback<ClientCredentials>() {
          public void onSuccess(ClientCredentials clientCredentials) {
            /* The tokens were retrieved successfully! */
            System.out.println("Successfully retrieved an access token! " + clientCredentials.getAccessToken());
            System.out.println("The access token expires in " + clientCredentials.getExpiresIn() + " seconds");

            /* Set access token on the Api object so that it's used going forward */
            api.setAccessToken(clientCredentials.getAccessToken());

            api.setAccessToken(TEST_ACCESS_TOKEN);
            
            List<TubePlaylistItem> tubeItems = new TubeService().getPlaylist(TEST_TUBE_PLAYLIST_ID);
            new SpotService(api).createPlaylistWithTubeItem(tubeItems, TEST_TUBE_PLAYLIST_ID);
          }

          public void onFailure(Throwable throwable) {
            /* An error occurred while getting the access token. This is probably caused by the client id or
             * client secret is invalid. */
          }
        });
        
    }
}
