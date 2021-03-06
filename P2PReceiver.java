package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class P2PReceiver implements Runnable
{
	private P2P peer;
	private DatagramSocket socket;
	P2PReceiver(DatagramSocket socket, P2P peer)
	{
		this.peer = peer;
		this.socket = socket;
	}
	
	public void run()
	{
		try
		{
			byte[] incomingData = new byte[1024];

            while(true) 
            {
            	// receive data from another peer
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                socket.receive(incomingPacket);
                
                //get peer's IP address and output it
                InetAddress otherIP = incomingPacket.getAddress();
                System.out.println("IP address received from " + otherIP.toString().substring(1));
                
                //update the map with the correct timestamp
                peer.addToMap(otherIP.toString().substring(1), new Long(System.currentTimeMillis()));
            }
		}
		catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException i) 
        {
            i.printStackTrace();
        } 
	}
}
