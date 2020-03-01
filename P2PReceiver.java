package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Map;

public class P2PReceiver implements Runnable
{
	private Map<String, Long> IPList;
	private DatagramSocket socket;
	P2PReceiver(Map<String, Long> IPList, DatagramSocket socket)
	{
		this.IPList = IPList;
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
                //String message = new String(incomingPacket.getData());
                
                //get peer's IP address
                InetAddress otherIP = incomingPacket.getAddress();
                
                //int port = incomingPacket.getPort();
                //System.out.println("Received IP from peer: " + message);
                //System.out.println("Other peer IP: "+ otherIP.getHostAddress());
                //System.out.println("Other peer port: "+ port);
                
                //update the map with the correct timestamp
                IPList.put(otherIP.toString(), new Long(System.currentTimeMillis()));
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
