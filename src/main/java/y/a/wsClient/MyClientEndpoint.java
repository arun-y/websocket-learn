package y.a.wsClient;

import java.io.IOException;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint public class MyClientEndpoint {
    private static final Logger log = Logger.getLogger( 
        MyClientEndpoint.class.getName() );
    
    private Session session;
 
    @OnOpen
    public void onOpen( final Session session ) throws IOException, EncodeException  {
    	this.session = session;
        session.getBasicRemote().sendText( "Arun:" + session );
    }

    @OnMessage
    public void onMessage( final String message ) {
        log.info( String.format( "Received message '%s'",
            message) );
        if (message.endsWith("quit")) {
        	synchronized (session) {
        		
			}
        }
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
    	synchronized (session) {
    		log.warning("session closed");
    		notifyAll();
		}
    }
    
}