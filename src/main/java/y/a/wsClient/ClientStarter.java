package y.a.wsClient;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;


public class ClientStarter {
    public static void main( final String[] args ) throws Exception {
    	
    	for (int i = 0; i < 5; i++) {
    		invoke();
    	}
        //keep alive thread
        Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		});
        t.setDaemon(false);
        t.start();
        
        t.join();

    }
    
    private static void invoke() throws DeploymentException, IOException, InterruptedException {
    	
    	
        final String client = UUID.randomUUID().toString().substring( 0, 8 );
  
        final WebSocketContainer container = ContainerProvider.getWebSocketContainer();    
        final String uri = "ws://localhost:8080/hello";  
  
        final Session session = container.connectToServer( MyClientEndpoint.class, URI.create( uri )); 
        {
                //session.getBasicRemote().sendText(client);
                //Thread.sleep( 1000 );
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					session.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}));
        

    	
    }
    
}