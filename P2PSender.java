package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class P2PSender implements Runnable
{
	private Map<String, Long> IPList;
	private DatagramSocket socket;
	private Random timer;
	P2PSender(Map<String, Long> IPList, DatagramSocket socket)
	{
		this.IPList = IPList;
		this.socket = socket;
		this.timer = new Random();
	}
	
	public void run()
	{
		try
		{
			InetAddress myIP = InetAddress.getByName("localhost");
			Iterator<String> IPs = IPList.keySet().iterator();
			while(IPs.hasNext())
			{
				String current = IPs.next();
				InetAddress destIP = InetAddress.getByName(current);
				String sentence = myIP.toString();
				byte[] data = sentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, destIP, 9876);
				socket.send(sendPacket);
				System.out.println("IP sent to " + current);
			}
			Thread.sleep(timer.nextInt(30000));
			while(true)
			{
				IPs = IPList.keySet().iterator();
				while(IPs.hasNext())
				{
					String current = IPs.next();
					if(IPList.get(current) >= 30000 + System.currentTimeMillis())
					{
						IPList.remove(current);
					}
					else
					{
						InetAddress destIP = InetAddress.getByName(current);
						String sentence = myIP.toString();
						byte[] data = sentence.getBytes();
						DatagramPacket sendPacket = new DatagramPacket(data, data.length, destIP, 9876);
						socket.send(sendPacket);
						System.out.println("IP sent to " + current);
					}
				}
				Thread.sleep(timer.nextInt(30000));
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
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
	}
}
