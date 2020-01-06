package step3;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
       DatagramSocket ds=new DatagramSocket(10086);
       List<HashMap<String,String>>  clientlist=new ArrayList<HashMap<String,String>>();  

      while(true)
      {
       //接收数据
       byte[]  bytes=new byte[1024];
       DatagramPacket  dp=new DatagramPacket(bytes,1024);
       ds.receive(dp);
       
       //test
       String tmp=new String(dp.getData(),0,dp.getLength());
       System.out.println("信息："+tmp);
       
       String ip=dp.getAddress().getHostAddress();
       String port=dp.getPort()+"";
       
       boolean flag=true;
       //判断客户端是否新来
       for(HashMap<String,String> map:clientlist)
        {
    	   if (map.get("ip").equals(ip) &&  map.get("port").equals(port))
    		{
    		    flag=false;
    		    break;
    		}
        }
       
       if  (flag==true)
       {
    	   HashMap<String,String>  map=new HashMap<String,String>();
    	   map.put("ip", ip);
    	   map.put("port", port);
    	   clientlist.add(map);
       }
       
       
       for(HashMap<String,String> map:clientlist)
       {
   	       DatagramPacket dp2=new DatagramPacket(bytes,0,dp.getLength(),InetAddress.getByName(map.get("ip")),Integer.parseInt(map.get("port")));
   	       ds.send(dp2);
       }       
      }
	}
}
